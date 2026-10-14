/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.builder.jaxb;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.ws.policy.builder.jaxb.JaxbAssertion;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Constants;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.neethi.builders.xml.XMLPrimitiveAssertionBuilder;
import org.w3c.dom.Element;

public class JaxbAssertionBuilder<T>
implements AssertionBuilder<Element> {
    private static final Logger LOG = LogUtils.getL7dLogger(JaxbAssertionBuilder.class);
    private Collection<QName> supportedTypes;
    private Class<T> type;
    private JAXBContext context;
    private Set<Class<?>> classes;

    public JaxbAssertionBuilder(QName qn) throws JAXBException, ClassNotFoundException {
        this(JAXBUtils.namespaceURIToPackage(qn.getNamespaceURI()) + "." + JAXBUtils.nameToIdentifier(qn.getLocalPart(), JAXBUtils.IdentifierType.CLASS), qn);
    }

    public JaxbAssertionBuilder(String className, QName qn) throws JAXBException, ClassNotFoundException {
        this(ClassLoaderUtils.loadClass(className, JaxbAssertionBuilder.class), qn);
    }

    public JaxbAssertionBuilder(Class<T> type, QName qn) throws JAXBException {
        this.type = type;
        this.supportedTypes = Collections.singletonList(qn);
    }

    private synchronized JAXBContext getContext() throws JAXBException {
        if (this.context == null || this.classes == null) {
            JAXBContextCache.CachedContextAndSchemas ccs = JAXBContextCache.getCachedContextAndSchemas(this.type);
            this.classes = ccs.getClasses();
            this.context = ccs.getContext();
        }
        return this.context;
    }

    protected Unmarshaller getUnmarshaller() {
        try {
            Unmarshaller um = this.getContext().createUnmarshaller();
            um.setEventHandler(null);
            return um;
        }
        catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) {
        QName name = new QName(element.getNamespaceURI(), element.getLocalName());
        JaxbAssertion<T> assertion = this.buildAssertion();
        assertion.setName(name);
        assertion.setOptional(XMLPrimitiveAssertionBuilder.isOptional(element));
        assertion.setIgnorable(XMLPrimitiveAssertionBuilder.isIgnorable(element));
        assertion.setData(this.getData(element));
        return assertion;
    }

    @Override
    public QName[] getKnownElements() {
        return this.supportedTypes.toArray(new QName[0]);
    }

    protected JaxbAssertion<T> buildAssertion() {
        return new JaxbAssertion();
    }

    protected boolean getOptionality(Element element) {
        boolean optional = false;
        String value = element.getAttributeNS(Constants.Q_ELEM_OPTIONAL_ATTR.getNamespaceURI(), Constants.Q_ELEM_OPTIONAL_ATTR.getLocalPart());
        if (Boolean.valueOf(value).booleanValue()) {
            optional = true;
        }
        return optional;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected T getData(Element element) {
        Object obj = null;
        Unmarshaller um = this.getUnmarshaller();
        try {
            obj = um.unmarshal(element);
        }
        catch (JAXBException ex) {
            LogUtils.log(LOG, Level.SEVERE, "UNMARSHAL_ELEMENT_EXC", ex);
        }
        finally {
            JAXBUtils.closeUnmarshaller(um);
        }
        if (obj instanceof JAXBElement) {
            JAXBElement el = (JAXBElement)obj;
            obj = el.getValue();
        }
        if (null != obj && LOG.isLoggable(Level.FINE)) {
            LOG.fine("Unmarshaled element into object of type: " + obj.getClass().getName() + "    value: " + obj);
        }
        return (T)obj;
    }
}

