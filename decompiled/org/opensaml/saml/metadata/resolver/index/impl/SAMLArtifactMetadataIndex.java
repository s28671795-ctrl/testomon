/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.binding.artifact.SAMLArtifact;
import org.opensaml.saml.common.binding.artifact.SAMLSourceIDArtifact;
import org.opensaml.saml.common.binding.artifact.SAMLSourceLocationArtifact;
import org.opensaml.saml.criterion.ArtifactCriterion;
import org.opensaml.saml.ext.saml1md.SourceID;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;
import org.opensaml.saml.metadata.resolver.index.impl.MetadataIndexSupport;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLArtifactMetadataIndex
implements MetadataIndex {
    private List<Function<EntityDescriptor, Set<MetadataIndexKey>>> indexingFunctions;

    public SAMLArtifactMetadataIndex() {
        this(Lists.newArrayList(new EntityIDToSHA1SourceIDIndexingFunction(), new SourceIDExtensionIndexingFunction(), new SourceLocationIndexingFunction()));
    }

    public SAMLArtifactMetadataIndex(@Nonnull List<Function<EntityDescriptor, Set<MetadataIndexKey>>> descriptorIndexingFunctions) {
        this.indexingFunctions = new ArrayList(Collections2.filter(Constraint.isNotNull(descriptorIndexingFunctions, "EntityDescriptor indexing functions list may not be null"), Predicates.notNull()));
        Constraint.isNotEmpty(this.indexingFunctions, "EntityDescriptor indexing functions list may not be empty");
    }

    @Override
    @Nullable
    public Set<MetadataIndexKey> generateKeys(@Nonnull EntityDescriptor descriptor) {
        Constraint.isNotNull(descriptor, "EntityDescriptor was null");
        HashSet<MetadataIndexKey> results = new HashSet<MetadataIndexKey>();
        for (Function<EntityDescriptor, Set<MetadataIndexKey>> indexingFunction : this.indexingFunctions) {
            Set<MetadataIndexKey> result = indexingFunction.apply(descriptor);
            if (result == null) continue;
            results.addAll(result);
        }
        return results;
    }

    @Override
    @Nullable
    public Set<MetadataIndexKey> generateKeys(@Nonnull CriteriaSet criteriaSet) {
        Constraint.isNotNull(criteriaSet, "CriteriaSet was null");
        ArtifactCriterion artifactCrit = criteriaSet.get(ArtifactCriterion.class);
        if (artifactCrit != null) {
            LazySet<MetadataIndexKey> results = new LazySet<MetadataIndexKey>();
            SAMLArtifact artifact = artifactCrit.getArtifact();
            if (artifact instanceof SAMLSourceIDArtifact) {
                results.add(new ArtifactSourceIDMetadataIndexKey(((SAMLSourceIDArtifact)artifact).getSourceID()));
            }
            if (artifact instanceof SAMLSourceLocationArtifact) {
                results.add(new ArtifactSourceLocationMetadataIndexKey(((SAMLSourceLocationArtifact)artifact).getSourceLocation()));
            }
            return results;
        }
        return null;
    }

    protected static class ArtifactSourceLocationMetadataIndexKey
    implements MetadataIndexKey {
        private Logger log = LoggerFactory.getLogger(ArtifactSourceLocationMetadataIndexKey.class);
        @Nonnull
        private final String location;
        @Nonnull
        private String canonicalizedLocation;
        private boolean isCanonicalizedLowerCase;

        public ArtifactSourceLocationMetadataIndexKey(@Nonnull @NotEmpty String sourceLocation) {
            this.location = Constraint.isNotNull(StringSupport.trimOrNull(sourceLocation), "SAML artifact source location cannot be null or empty");
            try {
                this.canonicalizedLocation = MetadataIndexSupport.canonicalizeLocationURI(this.location);
            }
            catch (MalformedURLException e) {
                this.log.warn("Input source location '{}' was a malformed URL, switching to lower case strategy", (Object)this.location, (Object)e);
                this.canonicalizedLocation = this.location.toLowerCase();
                this.isCanonicalizedLowerCase = true;
            }
        }

        @Nonnull
        public String getLocation() {
            return this.location;
        }

        @Nonnull
        public String getCanonicalizedLocation() {
            return this.canonicalizedLocation;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("location", this.location).add("canonicalizedLocation", this.canonicalizedLocation).add("isCanonicalizedLowerCase", this.isCanonicalizedLowerCase).toString();
        }

        public int hashCode() {
            return this.canonicalizedLocation.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ArtifactSourceLocationMetadataIndexKey) {
                ArtifactSourceLocationMetadataIndexKey other = (ArtifactSourceLocationMetadataIndexKey)obj;
                if (this.isCanonicalizedLowerCase == other.isCanonicalizedLowerCase) {
                    return this.canonicalizedLocation.equals(other.canonicalizedLocation);
                }
                if (this.isCanonicalizedLowerCase) {
                    return this.canonicalizedLocation.equals(other.location.toLowerCase());
                }
                return other.canonicalizedLocation.equals(this.location.toLowerCase());
            }
            return false;
        }
    }

    protected static class ArtifactSourceIDMetadataIndexKey
    implements MetadataIndexKey {
        @Nonnull
        @NotEmpty
        private final byte[] sourceID;

        public ArtifactSourceIDMetadataIndexKey(@Nonnull @NotEmpty byte[] newSourceID) {
            this.sourceID = Constraint.isNotNull(newSourceID, "SourceID cannot be null");
            Constraint.isGreaterThan(0L, this.sourceID.length, "SourceID length must be greater than zero");
        }

        @Nonnull
        @NotEmpty
        public byte[] getSourceID() {
            return this.sourceID;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("sourceID", new String(Hex.encodeHex(this.sourceID, true))).toString();
        }

        public int hashCode() {
            return Arrays.hashCode(this.sourceID);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ArtifactSourceIDMetadataIndexKey) {
                return Arrays.equals(this.sourceID, ((ArtifactSourceIDMetadataIndexKey)obj).getSourceID());
            }
            return false;
        }
    }

    public static class SourceLocationIndexingFunction
    implements Function<EntityDescriptor, Set<MetadataIndexKey>> {
        private Logger log = LoggerFactory.getLogger(SourceLocationIndexingFunction.class);

        @Override
        public Set<MetadataIndexKey> apply(@Nonnull EntityDescriptor descriptor) {
            if (descriptor == null) {
                return null;
            }
            LazySet<MetadataIndexKey> results = new LazySet<MetadataIndexKey>();
            for (RoleDescriptor roleDescriptor : descriptor.getRoleDescriptors()) {
                List<ArtifactResolutionService> arsList;
                if (!(roleDescriptor instanceof SSODescriptor) || (arsList = ((SSODescriptor)roleDescriptor).getArtifactResolutionServices()) == null || arsList.isEmpty()) continue;
                QName role = descriptor.getSchemaType() != null ? roleDescriptor.getSchemaType() : roleDescriptor.getElementQName();
                this.log.trace("Processing ArtifactResolutionService locations for entityID '{}' with role '{}'", (Object)descriptor.getEntityID(), (Object)role);
                for (ArtifactResolutionService ars : arsList) {
                    ArtifactSourceLocationMetadataIndexKey key = new ArtifactSourceLocationMetadataIndexKey(ars.getLocation());
                    this.log.trace("For entityID '{}' produced artifact source location index key: {}", (Object)descriptor.getEntityID(), (Object)key);
                    results.add(key);
                }
            }
            return results;
        }
    }

    public static class SourceIDExtensionIndexingFunction
    implements Function<EntityDescriptor, Set<MetadataIndexKey>> {
        private Logger log = LoggerFactory.getLogger(SourceIDExtensionIndexingFunction.class);

        @Override
        public Set<MetadataIndexKey> apply(@Nonnull EntityDescriptor descriptor) {
            if (descriptor == null) {
                return null;
            }
            LazySet<MetadataIndexKey> results = new LazySet<MetadataIndexKey>();
            for (RoleDescriptor roleDescriptor : descriptor.getRoleDescriptors()) {
                List<XMLObject> children;
                Extensions extensions = roleDescriptor.getExtensions();
                if (extensions == null || (children = extensions.getUnknownXMLObjects(SourceID.DEFAULT_ELEMENT_NAME)) == null || children.isEmpty()) continue;
                QName role = descriptor.getSchemaType() != null ? roleDescriptor.getSchemaType() : roleDescriptor.getElementQName();
                this.log.trace("Processing SourceID extensions for entityID '{}' with role '{}'", (Object)descriptor.getEntityID(), (Object)role);
                for (XMLObject child : children) {
                    SourceID extSourceID = (SourceID)child;
                    String extSourceIDHex = StringSupport.trimOrNull(extSourceID.getValue());
                    if (extSourceIDHex == null) continue;
                    try {
                        byte[] sourceID = Hex.decodeHex(extSourceIDHex.toCharArray());
                        ArtifactSourceIDMetadataIndexKey key = new ArtifactSourceIDMetadataIndexKey(sourceID);
                        this.log.trace("For SourceID extension value '{}' produced index key: {}", (Object)extSourceIDHex, (Object)key);
                        results.add(key);
                    }
                    catch (DecoderException e) {
                        this.log.warn("Error decoding hexidecimal SourceID extension value '{}' for indexing", (Object)extSourceIDHex, (Object)e);
                    }
                }
            }
            return results;
        }
    }

    public static class EntityIDToSHA1SourceIDIndexingFunction
    implements Function<EntityDescriptor, Set<MetadataIndexKey>> {
        private Logger log = LoggerFactory.getLogger(EntityIDToSHA1SourceIDIndexingFunction.class);

        @Override
        public Set<MetadataIndexKey> apply(@Nonnull EntityDescriptor descriptor) {
            if (descriptor == null) {
                return null;
            }
            String entityID = StringSupport.trimOrNull(descriptor.getEntityID());
            if (entityID == null) {
                return null;
            }
            try {
                MessageDigest sha1Digester = MessageDigest.getInstance("SHA-1");
                byte[] sourceID = sha1Digester.digest(entityID.getBytes("UTF-8"));
                ArtifactSourceIDMetadataIndexKey key = new ArtifactSourceIDMetadataIndexKey(sourceID);
                this.log.trace("For entityID '{}' produced artifact SourceID index key: {}", (Object)entityID, (Object)key);
                return Collections.singleton(key);
            }
            catch (NoSuchAlgorithmException e) {
                this.log.error("Digest algorithm '{}' was invalid for encoding artifact SourceID", (Object)"SHA-1", (Object)e);
                return null;
            }
            catch (UnsupportedEncodingException e) {
                this.log.error("UTF-8 was unsupported for encoding artifact SourceID!");
                return null;
            }
        }
    }
}

