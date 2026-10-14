/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import java.util.Objects;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class Namespace {
    private String namespaceURI;
    private String namespacePrefix;
    private String nsStr;

    public Namespace(@Nullable String uri, @Nullable String prefix) {
        this.namespaceURI = StringSupport.trimOrNull(uri);
        this.namespacePrefix = StringSupport.trimOrNull(prefix);
        this.nsStr = null;
    }

    public String getNamespacePrefix() {
        return this.namespacePrefix;
    }

    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    public String toString() {
        if (this.nsStr == null) {
            this.constructStringRepresentation();
        }
        return this.nsStr;
    }

    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + this.toString().hashCode();
        return hash;
    }

    public boolean equals(Object obj) {
        Namespace otherNamespace;
        if (obj == this) {
            return true;
        }
        return obj instanceof Namespace && Objects.equals((otherNamespace = (Namespace)obj).getNamespaceURI(), this.getNamespaceURI()) && Objects.equals(otherNamespace.getNamespacePrefix(), this.getNamespacePrefix());
    }

    protected void constructStringRepresentation() {
        StringBuffer stringRep = new StringBuffer();
        stringRep.append("xmlns");
        if (this.namespacePrefix != null) {
            stringRep.append(":");
            stringRep.append(this.namespacePrefix);
        }
        stringRep.append("=\"");
        if (this.namespaceURI != null) {
            stringRep.append(this.namespaceURI);
        }
        stringRep.append("\"");
        this.nsStr = stringRep.toString();
    }
}

