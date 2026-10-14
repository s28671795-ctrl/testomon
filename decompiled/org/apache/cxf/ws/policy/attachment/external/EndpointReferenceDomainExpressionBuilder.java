/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.external;

import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.attachment.external.DomainExpression;
import org.apache.cxf.ws.policy.attachment.external.DomainExpressionBuilder;
import org.apache.cxf.ws.policy.attachment.external.EndpointReferenceDomainExpression;
import org.w3c.dom.Element;

public class EndpointReferenceDomainExpressionBuilder
implements DomainExpressionBuilder {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(EndpointReferenceDomainExpressionBuilder.class);
    private static final Collection<QName> SUPPORTED_TYPES = Collections.singletonList(new QName("http://www.w3.org/2005/08/addressing", "EndpointReference"));
    private JAXBContext context;

    @Override
    public Collection<QName> getDomainExpressionTypes() {
        return SUPPORTED_TYPES;
    }

    @Override
    public DomainExpression build(Element e) {
        Object obj = null;
        try {
            obj = JAXBUtils.unmarshall(this.createJAXBContext(), e);
        }
        catch (JAXBException ex) {
            throw new PolicyException(new Message("EPR_DOMAIN_EXPRESSION_BUILD_EXC", BUNDLE, (Object[])null), (Throwable)ex);
        }
        if (obj instanceof JAXBElement) {
            JAXBElement el = (JAXBElement)obj;
            obj = el.getValue();
        }
        EndpointReferenceDomainExpression eprde = new EndpointReferenceDomainExpression();
        eprde.setEndpointReference((EndpointReferenceType)obj);
        return eprde;
    }

    protected synchronized JAXBContext createJAXBContext() {
        if (this.context == null) {
            try {
                Class<EndpointReferenceType> clz = EndpointReferenceType.class;
                String pkg = PackageUtils.getPackageName(clz);
                this.context = JAXBContext.newInstance(pkg, clz.getClassLoader());
            }
            catch (JAXBException ex) {
                throw new PolicyException(new Message("EPR_DOMAIN_EXPRESSION_BUILDER_INIT_EXC", BUNDLE, (Object[])null), (Throwable)ex);
            }
        }
        return this.context;
    }
}

