/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.claims;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.logging.LogUtils;

public class Claim
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 5730726672368086795L;
    private static final Logger LOG = LogUtils.getL7dLogger(Claim.class);
    private String claimType;
    private boolean optional;
    private List<Object> values = new ArrayList<Object>(1);

    public Claim() {
    }

    public Claim(Claim claim) {
        if (claim == null) {
            throw new IllegalArgumentException("Claim cannot be null");
        }
        this.claimType = claim.getClaimType();
        this.optional = claim.isOptional();
        this.values.addAll(claim.getValues());
    }

    public String getClaimType() {
        return this.claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public void setClaimType(URI claimType) {
        this.claimType = claimType.toString();
    }

    public boolean isOptional() {
        return this.optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public void setValues(List<Object> values2) {
        this.values.clear();
        this.values.addAll(values2);
    }

    public void addValue(Object s2) {
        this.values.add(s2);
    }

    public List<Object> getValues() {
        return this.values;
    }

    public void serialize(XMLStreamWriter writer, String prefix, String namespace) throws XMLStreamException {
        String localname = "ClaimType";
        if (!this.values.isEmpty()) {
            localname = "ClaimValue";
        }
        writer.writeStartElement(prefix, localname, namespace);
        writer.writeAttribute(null, "Uri", this.claimType);
        if (this.optional) {
            writer.writeAttribute(null, "Optional", "true");
        }
        if (!this.values.isEmpty()) {
            for (Object value : this.values) {
                if (value instanceof String) {
                    writer.writeStartElement(prefix, "Value", namespace);
                    writer.writeCharacters((String)value);
                    writer.writeEndElement();
                    continue;
                }
                LOG.warning("Only a ClaimValue String can be serialized");
            }
        }
        writer.writeEndElement();
    }

    public Claim clone() {
        try {
            super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new Claim(this);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.claimType == null ? 0 : this.claimType.hashCode());
        result = 31 * result + (this.optional ? 1231 : 1237);
        result = 31 * result + (this.values == null ? 0 : this.values.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Claim)) {
            return false;
        }
        Claim other = (Claim)obj;
        if (this.claimType == null ? other.claimType != null : !this.claimType.equals(other.claimType)) {
            return false;
        }
        if (this.optional != other.optional) {
            return false;
        }
        return !(this.values == null ? other.values != null : !this.values.equals(other.values));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        builder.append("Claim [values=");
        builder.append(this.values);
        builder.append(", claimType=");
        builder.append(this.claimType);
        builder.append(", optional=");
        builder.append(this.optional);
        builder.append(']');
        return builder.toString();
    }
}

