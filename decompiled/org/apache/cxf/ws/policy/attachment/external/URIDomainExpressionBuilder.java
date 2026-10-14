/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.external;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.attachment.external.DomainExpression;
import org.apache.cxf.ws.policy.attachment.external.DomainExpressionBuilder;
import org.apache.cxf.ws.policy.attachment.external.URIDomainExpression;
import org.w3c.dom.Element;

public class URIDomainExpressionBuilder
implements DomainExpressionBuilder {
    private static final Collection<QName> SUPPORTED_TYPES = Collections.unmodifiableList(Arrays.asList(new QName("http://www.w3.org/ns/ws-policy", "URI"), new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "URI")));

    @Override
    public DomainExpression build(Element paramElement) {
        return new URIDomainExpression(paramElement.getTextContent());
    }

    @Override
    public Collection<QName> getDomainExpressionTypes() {
        return SUPPORTED_TYPES;
    }
}

