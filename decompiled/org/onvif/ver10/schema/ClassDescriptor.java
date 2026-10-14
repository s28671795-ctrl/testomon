/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ClassDescriptorExtension;
import org.onvif.ver10.schema.ClassType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ClassDescriptor", propOrder={"classCandidate", "extension"})
public class ClassDescriptor {
    @XmlElement(name="ClassCandidate")
    protected List<ClassCandidate> classCandidate;
    @XmlElement(name="Extension")
    protected ClassDescriptorExtension extension;

    public List<ClassCandidate> getClassCandidate() {
        if (this.classCandidate == null) {
            this.classCandidate = new ArrayList<ClassCandidate>();
        }
        return this.classCandidate;
    }

    public ClassDescriptorExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ClassDescriptorExtension value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"type", "likelihood"})
    public static class ClassCandidate {
        @XmlElement(name="Type", required=true)
        @XmlSchemaType(name="string")
        protected ClassType type;
        @XmlElement(name="Likelihood")
        protected float likelihood;

        public ClassType getType() {
            return this.type;
        }

        public void setType(ClassType value) {
            this.type = value;
        }

        public float getLikelihood() {
            return this.likelihood;
        }

        public void setLikelihood(float value) {
            this.likelihood = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

