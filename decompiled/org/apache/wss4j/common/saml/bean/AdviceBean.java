/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;

public class AdviceBean {
    private List<String> idReferences = new ArrayList<String>();
    private List<String> uriReferences = new ArrayList<String>();
    private List<Element> assertions = new ArrayList<Element>();

    public List<String> getIdReferences() {
        return this.idReferences;
    }

    public List<String> getUriReferences() {
        return this.uriReferences;
    }

    public List<Element> getAssertions() {
        return this.assertions;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdviceBean)) {
            return false;
        }
        AdviceBean that = (AdviceBean)o;
        if (this.idReferences == null && that.idReferences != null) {
            return false;
        }
        if (this.idReferences != null && !this.idReferences.equals(that.idReferences)) {
            return false;
        }
        if (this.uriReferences == null && that.uriReferences != null) {
            return false;
        }
        if (this.uriReferences != null && !this.uriReferences.equals(that.uriReferences)) {
            return false;
        }
        if (this.assertions == null && that.assertions != null) {
            return false;
        }
        return this.assertions == null || this.assertions.equals(that.assertions);
    }

    public int hashCode() {
        int result = 0;
        if (this.idReferences != null) {
            result = 31 * result + this.idReferences.hashCode();
        }
        if (this.uriReferences != null) {
            result = 31 * result + this.uriReferences.hashCode();
        }
        if (this.assertions != null) {
            result = 31 * result + this.assertions.hashCode();
        }
        return result;
    }
}

