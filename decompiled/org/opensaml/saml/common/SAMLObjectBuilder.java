/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.saml.common.SAMLObject;

public interface SAMLObjectBuilder<SAMLObjectType extends SAMLObject>
extends XMLObjectBuilder<SAMLObjectType> {
    @Nonnull
    public SAMLObjectType buildObject();
}

