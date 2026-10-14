/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.criterion.EndpointCriterion;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.StartsWithLocationCriterion;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;
import org.opensaml.saml.metadata.resolver.index.impl.MetadataIndexSupport;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointMetadataIndex
implements MetadataIndex {
    private Logger log = LoggerFactory.getLogger(EndpointMetadataIndex.class);
    @Nonnull
    private Predicate<Endpoint> endpointSelectionPredicate;

    public EndpointMetadataIndex() {
        this.endpointSelectionPredicate = Predicates.alwaysTrue();
    }

    public EndpointMetadataIndex(@Nonnull Predicate<Endpoint> endpointPredicate) {
        this.endpointSelectionPredicate = Constraint.isNotNull(endpointPredicate, "Endpoint selection predicate may not be null");
    }

    @Override
    @Nullable
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<MetadataIndexKey> generateKeys(@Nonnull EntityDescriptor descriptor) {
        Constraint.isNotNull(descriptor, "EntityDescriptor was null");
        HashSet<MetadataIndexKey> result = new HashSet<MetadataIndexKey>();
        for (RoleDescriptor role : descriptor.getRoleDescriptors()) {
            QName roleType = role.getSchemaType();
            if (roleType == null) {
                roleType = role.getElementQName();
            }
            for (Endpoint endpoint : role.getEndpoints()) {
                String responseLocation;
                QName endpointType = endpoint.getSchemaType();
                if (endpointType == null) {
                    endpointType = endpoint.getElementQName();
                }
                if (!this.endpointSelectionPredicate.apply(endpoint)) continue;
                String location = StringSupport.trimOrNull(endpoint.getLocation());
                if (location != null) {
                    this.log.trace("Indexing Endpoint: role '{}', endpoint type '{}', location '{}'", roleType, endpointType, location);
                    result.add(new EndpointMetadataIndexKey(roleType, endpointType, location, false));
                }
                if ((responseLocation = StringSupport.trimOrNull(endpoint.getResponseLocation())) == null) continue;
                this.log.trace("Indexing response Endpoint - role '{}', endpoint type '{}', response location '{}'", roleType, endpointType, responseLocation);
                result.add(new EndpointMetadataIndexKey(roleType, endpointType, responseLocation, true));
            }
        }
        return result;
    }

    @Override
    @Nullable
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<MetadataIndexKey> generateKeys(@Nonnull CriteriaSet criteriaSet) {
        Constraint.isNotNull(criteriaSet, "CriteriaSet was null");
        EntityRoleCriterion roleCrit = criteriaSet.get(EntityRoleCriterion.class);
        EndpointCriterion endpointCrit = criteriaSet.get(EndpointCriterion.class);
        if (roleCrit != null && endpointCrit != null) {
            HashSet<MetadataIndexKey> result = new HashSet<MetadataIndexKey>();
            result.addAll(this.processCriteria(criteriaSet, roleCrit.getRole(), (Endpoint)endpointCrit.getEndpoint()));
            return result;
        }
        return null;
    }

    @Nonnull
    private Set<MetadataIndexKey> processCriteria(@Nonnull CriteriaSet criteriaSet, @Nonnull QName roleType, @Nonnull Endpoint endpoint) {
        String responseLocation;
        String location;
        HashSet<MetadataIndexKey> result = new HashSet<MetadataIndexKey>();
        QName endpointType = endpoint.getSchemaType();
        if (endpointType == null) {
            endpointType = endpoint.getElementQName();
        }
        if ((location = StringSupport.trimOrNull(endpoint.getLocation())) != null) {
            for (String variant : this.processLocation(criteriaSet, location)) {
                result.add(new EndpointMetadataIndexKey(roleType, endpointType, variant, false));
            }
        }
        if ((responseLocation = StringSupport.trimOrNull(endpoint.getResponseLocation())) != null) {
            for (String variant : this.processLocation(criteriaSet, responseLocation)) {
                result.add(new EndpointMetadataIndexKey(roleType, endpointType, variant, true));
            }
        }
        return result;
    }

    @Nonnull
    private Set<String> processLocation(@Nonnull CriteriaSet criteriaSet, @Nonnull String location) {
        boolean generateStartsWithVariants = false;
        StartsWithLocationCriterion startsWithCrit = criteriaSet.get(StartsWithLocationCriterion.class);
        if (startsWithCrit != null) {
            generateStartsWithVariants = startsWithCrit.isMatchStartsWith();
        }
        if (generateStartsWithVariants) {
            this.log.trace("Saw indication to produce path-trimmed key variants for startsWith eval from '{}'", (Object)location);
            HashSet<String> result = new HashSet<String>();
            result.add(location);
            this.log.trace("Produced value '{}'", (Object)location);
            try {
                String currentURL = null;
                URLBuilder urlBuilder = new URLBuilder(location);
                String currentPath = MetadataIndexSupport.trimURLPathSegment(urlBuilder.getPath());
                while (currentPath != null) {
                    urlBuilder.setPath(currentPath);
                    currentURL = urlBuilder.buildURL();
                    result.add(currentURL);
                    this.log.trace("Produced value '{}'", (Object)currentURL);
                    currentPath = MetadataIndexSupport.trimURLPathSegment(urlBuilder.getPath());
                }
                urlBuilder.setPath(null);
                currentURL = urlBuilder.buildURL();
                result.add(currentURL);
                this.log.trace("Produced value '{}'", (Object)currentURL);
            }
            catch (MalformedURLException e) {
                this.log.warn("Could not parse URL '{}', will not generate path segment variants", (Object)location, (Object)e);
            }
            return result;
        }
        return Collections.singleton(location);
    }

    protected static class EndpointMetadataIndexKey
    implements MetadataIndexKey {
        private final Logger log = LoggerFactory.getLogger(EndpointMetadataIndexKey.class);
        @Nonnull
        private final QName role;
        @Nonnull
        private final QName endpoint;
        @Nonnull
        private final String location;
        private final boolean response;
        @Nonnull
        private String canonicalizedLocation;
        private boolean isCanonicalizedLowerCase;

        public EndpointMetadataIndexKey(@Nonnull QName roleType, @Nonnull QName endpointType, @Nonnull @NotEmpty String endpointLocation, boolean isResponse) {
            this.role = Constraint.isNotNull(roleType, "SAML role cannot be null");
            this.endpoint = Constraint.isNotNull(endpointType, "SAML endpoint type cannot be null");
            this.location = Constraint.isNotNull(StringSupport.trimOrNull(endpointLocation), "SAML role cannot be null or empty");
            this.response = isResponse;
            try {
                this.canonicalizedLocation = MetadataIndexSupport.canonicalizeLocationURI(this.location);
            }
            catch (MalformedURLException e) {
                this.log.warn("Input location '{}' was a malformed URL, switching to lower case strategy", (Object)this.location, (Object)e);
                this.canonicalizedLocation = this.location.toLowerCase();
                this.isCanonicalizedLowerCase = true;
            }
        }

        @Nonnull
        public QName getRoleType() {
            return this.role;
        }

        @Nonnull
        public QName getEndpointType() {
            return this.endpoint;
        }

        @Nonnull
        public String getLocation() {
            return this.location;
        }

        public boolean isResponse() {
            return this.response;
        }

        @Nonnull
        public String getCanonicalizedLocation() {
            return this.canonicalizedLocation;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("role", this.role).add("endpoint", this.endpoint).add("location", this.location).add("isResponse", this.response).add("canonicalizedLocation", this.canonicalizedLocation).add("isCanonicalizedLowerCase", this.isCanonicalizedLowerCase).toString();
        }

        public int hashCode() {
            return Objects.hash(this.getRoleType(), this.getEndpointType(), this.getCanonicalizedLocation(), this.isResponse());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof EndpointMetadataIndexKey) {
                EndpointMetadataIndexKey other = (EndpointMetadataIndexKey)obj;
                String thisLocation = this.canonicalizedLocation;
                String otherLocation = other.canonicalizedLocation;
                if (this.isCanonicalizedLowerCase != other.isCanonicalizedLowerCase) {
                    if (this.isCanonicalizedLowerCase) {
                        otherLocation = other.location.toLowerCase();
                    } else {
                        thisLocation = this.location.toLowerCase();
                    }
                }
                return this.role.equals(other.role) && this.endpoint.equals(other.endpoint) && thisLocation.equals(otherLocation) && this.response == other.response;
            }
            return false;
        }
    }

    public static class DefaultEndpointSelectionPredicate
    implements Predicate<Endpoint> {
        @Nonnull
        private Map<QName, Set<QName>> endpointTypes;

        public DefaultEndpointSelectionPredicate() {
            this.endpointTypes = Collections.emptyMap();
        }

        public DefaultEndpointSelectionPredicate(@Nonnull Map<QName, Set<QName>> indexableTypes) {
            this.endpointTypes = Constraint.isNotNull(indexableTypes, "Indexable endpoint types map was null");
        }

        @Override
        public boolean apply(@Nullable Endpoint endpoint) {
            Set<QName> indexableEndpoints;
            QName endpointType;
            if (endpoint == null) {
                return false;
            }
            RoleDescriptor role = (RoleDescriptor)endpoint.getParent();
            if (role == null) {
                return false;
            }
            QName roleType = role.getSchemaType();
            if (roleType == null) {
                roleType = role.getElementQName();
            }
            if ((endpointType = endpoint.getSchemaType()) == null) {
                endpointType = endpoint.getElementQName();
            }
            return (indexableEndpoints = this.endpointTypes.get(roleType)) != null && indexableEndpoints.contains(endpointType);
        }
    }
}

