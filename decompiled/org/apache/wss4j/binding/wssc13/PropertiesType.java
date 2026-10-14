/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wssc13;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.wss4j.binding.wssc.AbstractPropertiesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PropertiesType", propOrder={"any"})
public class PropertiesType
extends AbstractPropertiesType {
    @XmlAnyElement(lax=true)
    private List<Object> any;

    @Override
    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }
}

