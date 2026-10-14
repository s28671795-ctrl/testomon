/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MotionInCells", propOrder={"any"})
public class MotionInCells {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="Columns", required=true)
    protected BigInteger columns;
    @XmlAttribute(name="Rows", required=true)
    protected BigInteger rows;
    @XmlAttribute(name="Cells", required=true)
    protected byte[] cells;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public BigInteger getColumns() {
        return this.columns;
    }

    public void setColumns(BigInteger value) {
        this.columns = value;
    }

    public BigInteger getRows() {
        return this.rows;
    }

    public void setRows(BigInteger value) {
        this.rows = value;
    }

    public byte[] getCells() {
        return this.cells;
    }

    public void setCells(byte[] value) {
        this.cells = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

