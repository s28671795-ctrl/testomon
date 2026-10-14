/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.xmldsig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.security.sts.provider.model.xmldsig.TransformType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TransformsType", propOrder={"transform"})
public class TransformsType {
    @XmlElement(name="Transform", required=true)
    protected List<TransformType> transform;

    public List<TransformType> getTransform() {
        if (this.transform == null) {
            this.transform = new ArrayList<TransformType>();
        }
        return this.transform;
    }
}

