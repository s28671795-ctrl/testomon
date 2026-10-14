/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.AbsoluteFocus;
import org.onvif.ver10.schema.ContinuousFocus;
import org.onvif.ver10.schema.RelativeFocus;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FocusMove", propOrder={"absolute", "relative", "continuous"})
public class FocusMove {
    @XmlElement(name="Absolute")
    protected AbsoluteFocus absolute;
    @XmlElement(name="Relative")
    protected RelativeFocus relative;
    @XmlElement(name="Continuous")
    protected ContinuousFocus continuous;

    public AbsoluteFocus getAbsolute() {
        return this.absolute;
    }

    public void setAbsolute(AbsoluteFocus value) {
        this.absolute = value;
    }

    public RelativeFocus getRelative() {
        return this.relative;
    }

    public void setRelative(RelativeFocus value) {
        this.relative = value;
    }

    public ContinuousFocus getContinuous() {
        return this.continuous;
    }

    public void setContinuous(ContinuousFocus value) {
        this.continuous = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

