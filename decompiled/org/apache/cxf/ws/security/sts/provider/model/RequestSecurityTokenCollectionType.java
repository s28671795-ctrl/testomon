/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RequestSecurityTokenCollectionType", propOrder={"requestSecurityToken"})
public class RequestSecurityTokenCollectionType {
    @XmlElement(name="RequestSecurityToken", required=true)
    protected List<RequestSecurityTokenType> requestSecurityToken;

    public List<RequestSecurityTokenType> getRequestSecurityToken() {
        if (this.requestSecurityToken == null) {
            this.requestSecurityToken = new ArrayList<RequestSecurityTokenType>();
        }
        return this.requestSecurityToken;
    }
}

