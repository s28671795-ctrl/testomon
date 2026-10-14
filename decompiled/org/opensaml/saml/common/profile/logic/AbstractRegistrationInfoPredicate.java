/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;

public abstract class AbstractRegistrationInfoPredicate
implements Predicate<EntityDescriptor> {
    private boolean matchIfMetadataSilent;

    public void setMatchIfMetadataSilent(boolean flag) {
        this.matchIfMetadataSilent = flag;
    }

    public boolean getMatchIfMetadataSilent() {
        return this.matchIfMetadataSilent;
    }

    @Nullable
    protected RegistrationInfo getRegistrationInfo(@Nullable EntityDescriptor entity) {
        if (null == entity) {
            return null;
        }
        Extensions extensions = entity.getExtensions();
        if (null != extensions) {
            for (XMLObject object : extensions.getUnknownXMLObjects(RegistrationInfo.DEFAULT_ELEMENT_NAME)) {
                if (!(object instanceof RegistrationInfo)) continue;
                return (RegistrationInfo)object;
            }
        }
        for (EntitiesDescriptor group = (EntitiesDescriptor)entity.getParent(); null != group; group = (EntitiesDescriptor)group.getParent()) {
            extensions = group.getExtensions();
            if (null == extensions) continue;
            for (XMLObject object : extensions.getUnknownXMLObjects(RegistrationInfo.DEFAULT_ELEMENT_NAME)) {
                if (!(object instanceof RegistrationInfo)) continue;
                return (RegistrationInfo)object;
            }
        }
        return null;
    }

    @Override
    public boolean apply(@Nullable EntityDescriptor input) {
        RegistrationInfo info = this.getRegistrationInfo(input);
        if (info != null) {
            return this.doApply(info);
        }
        return this.matchIfMetadataSilent;
    }

    protected abstract boolean doApply(@Nonnull RegistrationInfo var1);
}

