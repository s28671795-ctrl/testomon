/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObject;

public interface NameIdentifierGenerator<NameIdType extends SAMLObject> {
    @Nullable
    public NameIdType generate(@Nonnull ProfileRequestContext var1, @Nonnull @NotEmpty String var2) throws SAMLException;
}

