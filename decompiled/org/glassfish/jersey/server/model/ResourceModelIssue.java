/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import org.glassfish.jersey.Severity;

public final class ResourceModelIssue {
    private final Object source;
    private final String message;
    private final Severity severity;

    public ResourceModelIssue(Object source2, String message) {
        this(source2, message, Severity.WARNING);
    }

    public ResourceModelIssue(Object source2, String message, Severity severity) {
        this.source = source2;
        this.message = message;
        this.severity = severity;
    }

    public String getMessage() {
        return this.message;
    }

    public Severity getSeverity() {
        return this.severity;
    }

    public Object getSource() {
        return this.source;
    }

    public String toString() {
        return "[" + (Object)((Object)this.severity) + "] " + this.message + "; source='" + this.source + '\'';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ResourceModelIssue that = (ResourceModelIssue)o;
        if (this.message != null ? !this.message.equals(that.message) : that.message != null) {
            return false;
        }
        if (this.severity != that.severity) {
            return false;
        }
        return !(this.source != null ? !this.source.equals(that.source) : that.source != null);
    }

    public int hashCode() {
        int result = this.source != null ? this.source.hashCode() : 0;
        result = 31 * result + (this.message != null ? this.message.hashCode() : 0);
        result = 31 * result + (this.severity != null ? this.severity.hashCode() : 0);
        return result;
    }
}

