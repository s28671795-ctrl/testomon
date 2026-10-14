/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.ws.policy.spring;

import java.util.ArrayList;
import org.apache.cxf.configuration.spring.AbstractBeanDefinitionParser;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.policy.WSPolicyFeature;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class PolicyFeatureBeanDefinitionParser
extends AbstractBeanDefinitionParser {
    @Override
    protected void parseChildElements(Element e, ParserContext ctx, BeanDefinitionBuilder bean) {
        ArrayList<Element> ps = new ArrayList<Element>();
        ArrayList<Element> prs = new ArrayList<Element>();
        Element elem = DOMUtils.getFirstElement(e);
        while (elem != null) {
            if ("Policy".equals(elem.getLocalName())) {
                ps.add(elem);
            } else if ("PolicyReference".equals(elem.getLocalName())) {
                prs.add(elem);
            }
            elem = DOMUtils.getNextElement(elem);
        }
        bean.addPropertyValue("policyElements", ps);
        bean.addPropertyValue("policyReferenceElements", prs);
        super.parseChildElements(e, ctx, bean);
    }

    @Override
    protected void mapElement(ParserContext ctx, BeanDefinitionBuilder bean, Element e, String name) {
        if ("alternativeSelector".equals(name)) {
            this.setFirstChildAsProperty(e, ctx, bean, name);
        }
    }

    @Override
    protected Class<?> getBeanClass(Element el) {
        return WSPolicyFeature.class;
    }
}

