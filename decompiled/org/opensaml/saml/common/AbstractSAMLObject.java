/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.core.xml.AbstractXMLObject;

public abstract class AbstractSAMLObject
extends AbstractXMLObject {
    protected AbstractSAMLObject(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Nullable
    protected DateTime prepareForAssignment(@Nullable DateTime oldValue, @Nullable DateTime newValue) {
        DateTime utcValue = null;
        if (newValue != null) {
            utcValue = newValue.withZone(DateTimeZone.UTC);
        }
        return super.prepareForAssignment(oldValue, utcValue);
    }
}

