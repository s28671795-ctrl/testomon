/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityRoleFilter
implements MetadataFilter {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(EntityRoleFilter.class);
    @Nonnull
    @NonnullElements
    private List<QName> roleWhiteList = new ArrayList<QName>();
    private boolean removeRolelessEntityDescriptors;
    private boolean removeEmptyEntitiesDescriptors;
    @Nonnull
    private final QName extRoleDescriptor;

    public EntityRoleFilter(@Nullable List<QName> keptRoles) {
        if (keptRoles != null) {
            this.roleWhiteList.addAll(keptRoles);
        }
        this.roleWhiteList = Collections.unmodifiableList(this.roleWhiteList);
        this.removeRolelessEntityDescriptors = true;
        this.removeEmptyEntitiesDescriptors = true;
        this.extRoleDescriptor = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RoleDescriptor");
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<QName> getRoleWhiteList() {
        return this.roleWhiteList;
    }

    public boolean getRemoveRolelessEntityDescriptors() {
        return this.removeRolelessEntityDescriptors;
    }

    public void setRemoveRolelessEntityDescriptors(boolean remove) {
        this.removeRolelessEntityDescriptors = remove;
    }

    public boolean getRemoveEmptyEntitiesDescriptors() {
        return this.removeEmptyEntitiesDescriptors;
    }

    public void setRemoveEmptyEntitiesDescriptors(boolean remove) {
        this.removeEmptyEntitiesDescriptors = remove;
    }

    @Override
    @Nullable
    public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
        if (metadata == null) {
            return null;
        }
        if (metadata instanceof EntitiesDescriptor) {
            this.filterEntitiesDescriptor((EntitiesDescriptor)metadata);
        } else {
            this.filterEntityDescriptor((EntityDescriptor)metadata);
        }
        return metadata;
    }

    protected void filterEntitiesDescriptor(@Nonnull EntitiesDescriptor descriptor) throws FilterException {
        List<EntitiesDescriptor> entitiesDescriptors;
        List<EntityDescriptor> entityDescriptors = descriptor.getEntityDescriptors();
        if (entityDescriptors != null && !entityDescriptors.isEmpty()) {
            ArrayList<EntityDescriptor> emptyEntityDescriptors = new ArrayList<EntityDescriptor>();
            for (EntityDescriptor entityDescriptor : entityDescriptors) {
                List<RoleDescriptor> entityRoles;
                this.filterEntityDescriptor(entityDescriptor);
                if (!this.getRemoveRolelessEntityDescriptors() || (entityRoles = entityDescriptor.getRoleDescriptors()) != null && !entityRoles.isEmpty()) continue;
                this.log.trace("Filtering out entity descriptor {} from entity group {}", (Object)entityDescriptor.getEntityID(), (Object)descriptor.getName());
                emptyEntityDescriptors.add(entityDescriptor);
            }
            entityDescriptors.removeAll(emptyEntityDescriptors);
        }
        if ((entitiesDescriptors = descriptor.getEntitiesDescriptors()) != null && !entitiesDescriptors.isEmpty()) {
            ArrayList<EntitiesDescriptor> emptyEntitiesDescriptors = new ArrayList<EntitiesDescriptor>();
            for (EntitiesDescriptor entitiesDescriptor : entitiesDescriptors) {
                this.filterEntitiesDescriptor(entitiesDescriptor);
                if (!this.getRemoveEmptyEntitiesDescriptors() || entitiesDescriptor.getEntityDescriptors() != null && !entitiesDescriptor.getEntityDescriptors().isEmpty() || entitiesDescriptor.getEntitiesDescriptors() != null && !entitiesDescriptor.getEntitiesDescriptors().isEmpty()) continue;
                this.log.trace("Filtering out entity descriptor {} from entity group {}", (Object)entitiesDescriptor.getName(), (Object)descriptor.getName());
                emptyEntitiesDescriptors.add(entitiesDescriptor);
            }
            entitiesDescriptors.removeAll(emptyEntitiesDescriptors);
        }
    }

    protected void filterEntityDescriptor(@Nonnull EntityDescriptor descriptor) throws FilterException {
        List<RoleDescriptor> roles = descriptor.getRoleDescriptors();
        if (roles != null && !roles.isEmpty()) {
            Iterator<RoleDescriptor> rolesItr = roles.iterator();
            while (rolesItr.hasNext()) {
                QName roleName = this.getRoleName(rolesItr.next());
                if (this.roleWhiteList.contains(roleName)) continue;
                this.log.trace("Filtering out role {} from entity {}", (Object)roleName, (Object)descriptor.getEntityID());
                rolesItr.remove();
            }
        }
    }

    protected QName getRoleName(@Nonnull RoleDescriptor role) throws FilterException {
        QName roleName = role.getElementQName();
        if (this.extRoleDescriptor.equals(roleName) && (roleName = role.getSchemaType()) == null) {
            throw new FilterException("Role descriptor element was " + this.extRoleDescriptor + " but did not contain a schema type.  This is illegal.");
        }
        return roleName;
    }
}

