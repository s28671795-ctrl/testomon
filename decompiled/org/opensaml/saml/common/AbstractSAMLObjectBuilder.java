/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SAMLObjectBuilder;

public abstract class AbstractSAMLObjectBuilder<SAMLObjectType extends SAMLObject>
extends AbstractXMLObjectBuilder<SAMLObjectType>
implements SAMLObjectBuilder<SAMLObjectType> {
    @Override
    @Nonnull
    public abstract SAMLObjectType buildObject();
}

