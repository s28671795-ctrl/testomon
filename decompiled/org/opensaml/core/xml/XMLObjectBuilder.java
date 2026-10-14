/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.w3c.dom.Element;

public interface XMLObjectBuilder<XMLObjectType extends XMLObject> {
    @Nonnull
    public XMLObjectType buildObject(@Nonnull QName var1);

    @Nonnull
    public XMLObjectType buildObject(@Nonnull QName var1, @Nullable QName var2);

    @Nonnull
    public XMLObjectType buildObject(@Nullable String var1, @Nonnull String var2, @Nullable String var3);

    @Nonnull
    public XMLObjectType buildObject(@Nullable String var1, @Nonnull String var2, @Nullable String var3, @Nullable QName var4);

    @Nonnull
    public XMLObjectType buildObject(@Nonnull Element var1);
}

