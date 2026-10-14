/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RequestSecurityTokenResponseCollectionType", propOrder={"requestSecurityTokenResponse"})
public class RequestSecurityTokenResponseCollectionType {
    @XmlElement(name="RequestSecurityTokenResponse", required=true)
    protected List<RequestSecurityTokenResponseType> requestSecurityTokenResponse;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<RequestSecurityTokenResponseType> getRequestSecurityTokenResponse() {
        if (this.requestSecurityTokenResponse == null) {
            this.requestSecurityTokenResponse = new ArrayList<RequestSecurityTokenResponseType>();
        }
        return this.requestSecurityTokenResponse;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

