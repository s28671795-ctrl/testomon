/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.external;

import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.Resource;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.extension.BusExtension;
import org.apache.cxf.extension.RegistryImpl;
import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.attachment.external.DomainExpression;
import org.apache.cxf.ws.policy.attachment.external.DomainExpressionBuilder;
import org.w3c.dom.Element;

@NoJSR250Annotations(unlessNull={"bus"})
public class DomainExpressionBuilderRegistry
extends RegistryImpl<QName, DomainExpressionBuilder>
implements BusExtension {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AssertionBuilderRegistry.class);
    private boolean dynamicLoaded;
    private Bus bus;

    public DomainExpressionBuilderRegistry() {
        super(null);
    }

    public DomainExpressionBuilderRegistry(Bus b) {
        super(null);
        this.setBus(b);
    }

    public DomainExpressionBuilderRegistry(Map<QName, DomainExpressionBuilder> builders) {
        super(builders);
    }

    @Resource
    public final void setBus(Bus b) {
        this.bus = b;
        if (b != null) {
            b.setExtension(this, DomainExpressionBuilderRegistry.class);
        }
    }

    @Override
    public Class<?> getRegistrationType() {
        return DomainExpressionBuilderRegistry.class;
    }

    @Override
    protected synchronized void loadDynamic() {
        if (!this.dynamicLoaded && this.bus != null) {
            this.dynamicLoaded = true;
            ConfiguredBeanLocator c = this.bus.getExtension(ConfiguredBeanLocator.class);
            if (c != null) {
                for (DomainExpressionBuilder b : c.getBeansOfType(DomainExpressionBuilder.class)) {
                    for (QName q : b.getDomainExpressionTypes()) {
                        this.register(q, b);
                    }
                }
            }
        }
    }

    public DomainExpression build(Element element) {
        this.loadDynamic();
        QName qname = new QName(element.getNamespaceURI(), element.getLocalName());
        DomainExpressionBuilder builder = (DomainExpressionBuilder)this.get(qname);
        if (null == builder) {
            throw new PolicyException(new Message("NO_DOMAINEXPRESSIONBUILDER_EXC", BUNDLE, qname.toString()));
        }
        return builder.build(element);
    }
}

