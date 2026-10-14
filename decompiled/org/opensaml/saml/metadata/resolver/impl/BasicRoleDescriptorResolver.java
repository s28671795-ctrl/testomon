/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.RoleDescriptorResolver;
import org.opensaml.saml.saml2.common.SAML2Support;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class BasicRoleDescriptorResolver
extends AbstractIdentifiedInitializableComponent
implements RoleDescriptorResolver {
    private Logger log = LoggerFactory.getLogger(BasicRoleDescriptorResolver.class);
    private boolean requireValidMetadata;
    private MetadataResolver entityDescriptorResolver;

    public BasicRoleDescriptorResolver(@Nonnull MetadataResolver mdResolver) {
        this.entityDescriptorResolver = Constraint.isNotNull(mdResolver, "Resolver for EntityDescriptors may not be null");
        this.setId(UUID.randomUUID().toString());
    }

    @Override
    public boolean isRequireValidMetadata() {
        return this.requireValidMetadata;
    }

    @Override
    public void setRequireValidMetadata(boolean require) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.requireValidMetadata = require;
    }

    @Override
    @Nullable
    public RoleDescriptor resolveSingle(CriteriaSet criteria) throws ResolverException {
        Iterator<RoleDescriptor> iterator2;
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        Iterable<RoleDescriptor> iterable = this.resolve(criteria);
        if (iterable != null && (iterator2 = iterable.iterator()) != null && iterator2.hasNext()) {
            return iterator2.next();
        }
        return null;
    }

    @Override
    @Nonnull
    public Iterable<RoleDescriptor> resolve(CriteriaSet criteria) throws ResolverException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        EntityIdCriterion entityIdCriterion = criteria.get(EntityIdCriterion.class);
        EntityRoleCriterion entityRoleCriterion = criteria.get(EntityRoleCriterion.class);
        ProtocolCriterion protocolCriterion = criteria.get(ProtocolCriterion.class);
        if (entityIdCriterion == null || Strings.isNullOrEmpty(entityIdCriterion.getEntityId())) {
            throw new ResolverException("Entity Id was not supplied in criteria set");
        }
        if (entityRoleCriterion == null || entityRoleCriterion.getRole() == null) {
            throw new ResolverException("Entity role was not supplied in criteria set");
        }
        if (protocolCriterion != null) {
            RoleDescriptor role = this.getRole(entityIdCriterion.getEntityId(), entityRoleCriterion.getRole(), protocolCriterion.getProtocol());
            if (role != null) {
                return Collections.singletonList(role);
            }
            return Collections.emptyList();
        }
        return this.getRole(entityIdCriterion.getEntityId(), entityRoleCriterion.getRole());
    }

    @Nonnull
    @NonnullElements
    protected List<RoleDescriptor> getRole(@Nullable String entityID, @Nullable QName roleName) throws ResolverException {
        if (Strings.isNullOrEmpty(entityID)) {
            this.log.debug("EntityDescriptor entityID was null or empty, skipping search for roles");
            return Collections.emptyList();
        }
        if (roleName == null) {
            this.log.debug("Role descriptor name was null, skipping search for roles");
            return Collections.emptyList();
        }
        List<RoleDescriptor> roleDescriptors = this.doGetRole(entityID, roleName);
        if (roleDescriptors == null || roleDescriptors.isEmpty()) {
            this.log.debug("Entity descriptor {} did not contain any {} roles", (Object)entityID, (Object)roleName);
            return Collections.emptyList();
        }
        Iterator<RoleDescriptor> roleDescItr = roleDescriptors.iterator();
        while (roleDescItr.hasNext()) {
            if (this.isValid(roleDescItr.next())) continue;
            this.log.debug("Metadata document contained a role of type {} for entity {}, but it was invalid", (Object)roleName, (Object)entityID);
            roleDescItr.remove();
        }
        if (roleDescriptors.isEmpty()) {
            this.log.debug("Entity descriptor {} did not contain any valid {} roles", (Object)entityID, (Object)roleName);
        }
        return roleDescriptors;
    }

    @Nonnull
    @NonnullElements
    protected List<RoleDescriptor> doGetRole(@Nullable String entityID, @Nullable QName roleName) throws ResolverException {
        EntityDescriptor entity = this.doGetEntityDescriptor(entityID);
        if (entity == null) {
            this.log.debug("Metadata document did not contain a descriptor for entity {}", (Object)entityID);
            return Collections.emptyList();
        }
        List<RoleDescriptor> descriptors = entity.getRoleDescriptors(roleName);
        if (descriptors != null && !descriptors.isEmpty()) {
            return new ArrayList<RoleDescriptor>(descriptors);
        }
        return Collections.emptyList();
    }

    protected EntityDescriptor doGetEntityDescriptor(String entityID) throws ResolverException {
        return (EntityDescriptor)this.entityDescriptorResolver.resolveSingle(new CriteriaSet(new EntityIdCriterion(entityID)));
    }

    @Nullable
    protected RoleDescriptor getRole(@Nullable String entityID, @Nullable QName roleName, @Nullable String supportedProtocol) throws ResolverException {
        if (Strings.isNullOrEmpty(entityID)) {
            this.log.debug("EntityDescriptor entityID was null or empty, skipping search for role");
            return null;
        }
        if (roleName == null) {
            this.log.debug("Role descriptor name was null, skipping search for role");
            return null;
        }
        if (Strings.isNullOrEmpty(supportedProtocol)) {
            this.log.debug("Supported protocol was null, skipping search for role.");
            return null;
        }
        RoleDescriptor role = this.doGetRole(entityID, roleName, supportedProtocol);
        if (role == null) {
            this.log.debug("Metadata document does not contain a role of type {} supporting protocol {} for entity {}", roleName, supportedProtocol, entityID);
            return null;
        }
        if (!this.isValid(role)) {
            this.log.debug("Metadata document contained a role of type {} supporting protocol {} for entity {}, but it was not longer valid", roleName, supportedProtocol, entityID);
            return null;
        }
        return role;
    }

    protected RoleDescriptor doGetRole(String entityID, QName roleName, String supportedProtocol) throws ResolverException {
        List<RoleDescriptor> roles = this.doGetRole(entityID, roleName);
        if (roles == null || roles.isEmpty()) {
            this.log.debug("Metadata document did not contain any role descriptors of type {} for entity {}", (Object)roleName, (Object)entityID);
            return null;
        }
        Iterator<RoleDescriptor> rolesItr = roles.iterator();
        RoleDescriptor role = null;
        while (rolesItr.hasNext()) {
            role = rolesItr.next();
            if (role == null || !role.isSupportedProtocol(supportedProtocol)) continue;
            return role;
        }
        return null;
    }

    protected boolean isValid(XMLObject descriptor) {
        if (descriptor == null) {
            return false;
        }
        if (!this.isRequireValidMetadata()) {
            return true;
        }
        return SAML2Support.isValid(descriptor);
    }
}

