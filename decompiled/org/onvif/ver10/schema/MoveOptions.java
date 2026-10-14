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
import org.onvif.ver10.schema.AbsoluteFocusOptions;
import org.onvif.ver10.schema.ContinuousFocusOptions;
import org.onvif.ver10.schema.RelativeFocusOptions;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MoveOptions", propOrder={"absolute", "relative", "continuous"})
public class MoveOptions {
    @XmlElement(name="Absolute")
    protected AbsoluteFocusOptions absolute;
    @XmlElement(name="Relative")
    protected RelativeFocusOptions relative;
    @XmlElement(name="Continuous")
    protected ContinuousFocusOptions continuous;

    public AbsoluteFocusOptions getAbsolute() {
        return this.absolute;
    }

    public void setAbsolute(AbsoluteFocusOptions value) {
        this.absolute = value;
    }

    public RelativeFocusOptions getRelative() {
        return this.relative;
    }

    public void setRelative(RelativeFocusOptions value) {
        this.relative = value;
    }

    public ContinuousFocusOptions getContinuous() {
        return this.continuous;
    }

    public void setContinuous(ContinuousFocusOptions value) {
        this.continuous = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

