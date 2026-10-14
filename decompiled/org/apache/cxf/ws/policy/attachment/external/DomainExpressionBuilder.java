/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.external;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.attachment.external.DomainExpression;
import org.w3c.dom.Element;

public interface DomainExpressionBuilder {
    public DomainExpression build(Element var1);

    public Collection<QName> getDomainExpressionTypes();
}

