/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.profile.NameIdentifierGenerator;

public interface FormatSpecificNameIdentifierGenerator<NameIdType extends SAMLObject>
extends NameIdentifierGenerator<NameIdType> {
    @Nonnull
    @NotEmpty
    public String getFormat();
}

