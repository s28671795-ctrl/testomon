/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml.bean;

public class ActionBean {
    private String actionNamespace;
    private String contents;

    public ActionBean() {
    }

    public ActionBean(String actionNamespace, String contents) {
        this.actionNamespace = actionNamespace;
        this.contents = contents;
    }

    public String getActionNamespace() {
        return this.actionNamespace;
    }

    public void setActionNamespace(String actionNamespace) {
        this.actionNamespace = actionNamespace;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionBean)) {
            return false;
        }
        ActionBean that = (ActionBean)o;
        if (this.contents == null && that.contents != null) {
            return false;
        }
        if (this.contents != null && !this.contents.equals(that.contents)) {
            return false;
        }
        if (this.actionNamespace == null && that.actionNamespace != null) {
            return false;
        }
        return this.actionNamespace == null || this.actionNamespace.equals(that.actionNamespace);
    }

    public int hashCode() {
        int result = 0;
        if (this.contents != null) {
            result = 31 * result + this.contents.hashCode();
        }
        if (this.actionNamespace != null) {
            result = 31 * result + this.actionNamespace.hashCode();
        }
        return result;
    }
}

