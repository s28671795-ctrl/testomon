/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.io.Serializable;

public class Mapping
implements Serializable {
    private static final long serialVersionUID = 4598721541118599293L;
    private String namespaceURI;
    private int namespaceHash;
    private String prefix;
    private int prefixHash;

    public Mapping(String namespaceURI, String prefix) {
        this.setPrefix(prefix);
        this.setNamespaceURI(namespaceURI);
    }

    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    public int getNamespaceHash() {
        return this.namespaceHash;
    }

    public void setNamespaceURI(String namespaceURI) {
        this.namespaceURI = namespaceURI;
        this.namespaceHash = namespaceURI.hashCode();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public int getPrefixHash() {
        return this.prefixHash;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        this.prefixHash = prefix.hashCode();
    }
}

