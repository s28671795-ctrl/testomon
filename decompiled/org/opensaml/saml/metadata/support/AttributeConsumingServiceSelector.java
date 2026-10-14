/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.support;

import java.util.List;
import org.opensaml.saml.ext.saml2mdquery.AttributeQueryDescriptorType;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeConsumingServiceSelector {
    private Logger log = LoggerFactory.getLogger(AttributeConsumingServiceSelector.class);
    private Integer index;
    private RoleDescriptor roleDescriptor;
    private boolean onBadIndexUseDefault;

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer requestedIndex) {
        this.index = requestedIndex;
    }

    public RoleDescriptor getRoleDescriptor() {
        return this.roleDescriptor;
    }

    public void setRoleDescriptor(RoleDescriptor descriptor) {
        this.roleDescriptor = descriptor;
    }

    public void setOnBadIndexUseDefault(boolean flag) {
        this.onBadIndexUseDefault = flag;
    }

    public boolean isOnBadIndexUseDefault() {
        return this.onBadIndexUseDefault;
    }

    public AttributeConsumingService selectService() {
        List<AttributeConsumingService> candidates = this.getCandidates();
        if (candidates == null || candidates.isEmpty()) {
            this.log.debug("AttributeConsumingService candidate list was empty, can not select service");
            return null;
        }
        this.log.debug("AttributeConsumingService index was specified: {}", (Object)(this.index != null ? 1 : 0));
        AttributeConsumingService acs = null;
        if (this.index != null) {
            acs = this.selectByIndex(candidates);
            if (acs == null && this.isOnBadIndexUseDefault()) {
                acs = this.selectDefault(candidates);
            }
        } else {
            return this.selectDefault(candidates);
        }
        return acs;
    }

    protected List<AttributeConsumingService> getCandidates() {
        if (this.roleDescriptor == null) {
            this.log.debug("RoleDescriptor was not supplied, unable to select AttributeConsumingService");
            return null;
        }
        if (this.roleDescriptor instanceof SPSSODescriptor) {
            this.log.debug("Resolving AttributeConsumingService candidates from SPSSODescriptor");
            return ((SPSSODescriptor)this.roleDescriptor).getAttributeConsumingServices();
        }
        if (this.roleDescriptor instanceof AttributeQueryDescriptorType) {
            this.log.debug("Resolving AttributeConsumingService candidates from AttributeQueryDescriptorType");
            return ((AttributeQueryDescriptorType)this.roleDescriptor).getAttributeConsumingServices();
        }
        this.log.debug("Unable to resolve service candidates, role descriptor was of an unsupported type: {}", (Object)this.roleDescriptor.getClass().getName());
        return null;
    }

    private AttributeConsumingService selectByIndex(List<AttributeConsumingService> candidates) {
        this.log.debug("Selecting AttributeConsumingService by index");
        for (AttributeConsumingService attribCS : candidates) {
            if (this.index == null || this.index.intValue() != attribCS.getIndex()) continue;
            this.log.debug("Selected AttributeConsumingService with index: {}", (Object)this.index);
            return attribCS;
        }
        this.log.debug("A service index of '{}' was specified, but was not found in metadata", (Object)this.index);
        return null;
    }

    private AttributeConsumingService selectDefault(List<AttributeConsumingService> candidates) {
        this.log.debug("Selecting default AttributeConsumingService");
        AttributeConsumingService firstNoDefault = null;
        for (AttributeConsumingService attribCS : candidates) {
            if (attribCS.isDefault().booleanValue()) {
                this.log.debug("Selected AttributeConsumingService with explicit isDefault of true");
                return attribCS;
            }
            if (firstNoDefault != null || attribCS.isDefaultXSBoolean() != null) continue;
            firstNoDefault = attribCS;
        }
        if (firstNoDefault != null) {
            this.log.debug("Selected first AttributeConsumingService with no explicit isDefault");
            return firstNoDefault;
        }
        this.log.debug("Selected first AttributeConsumingService with explicit isDefault of false");
        return candidates.get(0);
    }
}

