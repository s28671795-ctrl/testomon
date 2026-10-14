/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;

public interface KeyInfoProvider {
    @Nullable
    public Collection<Credential> process(@Nonnull KeyInfoCredentialResolver var1, @Nonnull XMLObject var2, @Nullable CriteriaSet var3, @Nonnull KeyInfoResolutionContext var4) throws SecurityException;

    public boolean handles(@Nonnull XMLObject var1);
}

