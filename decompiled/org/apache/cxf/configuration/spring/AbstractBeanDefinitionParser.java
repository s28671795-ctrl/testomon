/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser
 *  org.springframework.beans.factory.xml.ParserContext
 */
package org.apache.cxf.configuration.spring;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.spring.BusWiringType;
import org.apache.cxf.configuration.spring.JAXBBeanFactory;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class AbstractBeanDefinitionParser
extends AbstractSingleBeanDefinitionParser {
    public static final String WIRE_BUS_ATTRIBUTE = AbstractBeanDefinitionParser.class.getName() + ".wireBus";
    public static final String WIRE_BUS_NAME = AbstractBeanDefinitionParser.class.getName() + ".wireBusName";
    public static final String WIRE_BUS_CREATE = AbstractBeanDefinitionParser.class.getName() + ".wireBusCreate";
    public static final String WIRE_BUS_HANDLER = "org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor";
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractBeanDefinitionParser.class);
    private Class<?> beanClass;
    private JAXBContext context;
    private Set<Class<?>> classes;

    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        boolean setBus = this.parseAttributes(element, ctx, bean);
        if (!setBus && this.hasBusProperty()) {
            this.addBusWiringAttribute(bean, BusWiringType.PROPERTY);
        }
        this.parseChildElements(element, ctx, bean);
    }

    protected boolean parseAttributes(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        NamedNodeMap atts = element.getAttributes();
        boolean setBus = false;
        for (int i = 0; i < atts.getLength(); ++i) {
            Attr node = (Attr)atts.item(i);
            setBus |= this.parseAttribute(element, node, ctx, bean);
        }
        return setBus;
    }

    protected boolean parseAttribute(Element element, Attr node, ParserContext ctx, BeanDefinitionBuilder bean) {
        String prefix;
        String val = node.getValue();
        String pre = node.getPrefix();
        String name = node.getLocalName();
        if (this.isNamespace(name, prefix = node.getPrefix())) {
            return false;
        }
        if ("createdFromAPI".equals(name)) {
            bean.setAbstract(true);
        } else if ("abstract".equals(name)) {
            bean.setAbstract(true);
        } else if ("depends-on".equals(name)) {
            bean.addDependsOn(val);
        } else if ("name".equals(name)) {
            this.processNameAttribute(element, ctx, bean, val);
        } else {
            if ("bus".equals(name)) {
                return this.processBusAttribute(element, ctx, bean, val);
            }
            if (!"id".equals(name) && this.isAttribute(pre, name)) {
                this.mapAttribute(bean, element, name, val);
            }
        }
        return false;
    }

    protected boolean processBusAttribute(Element element, ParserContext ctx, BeanDefinitionBuilder bean, String val) {
        if (val != null && val.trim().length() > 0) {
            if (ctx.getRegistry().containsBeanDefinition(val)) {
                bean.addPropertyReference("bus", val);
            } else {
                this.addBusWiringAttribute(bean, BusWiringType.PROPERTY, val, ctx);
            }
            return true;
        }
        return false;
    }

    protected void processNameAttribute(Element element, ParserContext ctx, BeanDefinitionBuilder bean, String val) {
    }

    private boolean isNamespace(String name, String prefix) {
        return "xmlns".equals(prefix) || prefix == null && "xmlns".equals(name);
    }

    protected void parseChildElements(Element element, ParserContext ctx, BeanDefinitionBuilder bean) {
        Element el = DOMUtils.getFirstElement(element);
        while (el != null) {
            String name = el.getLocalName();
            this.mapElement(ctx, bean, el, name);
            el = DOMUtils.getNextElement(el);
        }
    }

    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    protected Class<?> getBeanClass(Element e) {
        return this.beanClass;
    }

    protected void mapAttribute(BeanDefinitionBuilder bean, Element e, String name, String val) {
        this.mapAttribute(bean, name, val);
    }

    protected void mapAttribute(BeanDefinitionBuilder bean, String name, String val) {
        this.mapToProperty(bean, name, val);
    }

    protected void mapElement(ParserContext ctx, BeanDefinitionBuilder bean, Element e, String name) {
    }

    protected String resolveId(Element elem, AbstractBeanDefinition definition, ParserContext ctx) {
        String id = this.getIdOrName(elem);
        String createdFromAPI = elem.getAttribute("createdFromAPI");
        if (null == id || id.isEmpty()) {
            return super.resolveId(elem, definition, ctx);
        }
        if (createdFromAPI != null && Boolean.parseBoolean(createdFromAPI)) {
            return id + this.getSuffix();
        }
        return id;
    }

    protected boolean hasBusProperty() {
        return false;
    }

    protected String getSuffix() {
        return "";
    }

    protected void setFirstChildAsProperty(Element element, ParserContext ctx, BeanDefinitionBuilder bean, String propertyName) {
        Element first = this.getFirstChild(element);
        if (first == null) {
            throw new IllegalStateException(propertyName + " property must have child elements!");
        }
        if (first.getNamespaceURI().equals("http://www.springframework.org/schema/beans")) {
            String name = first.getLocalName();
            if ("ref".equals(name)) {
                String id = first.getAttribute("bean");
                if (id == null) {
                    throw new IllegalStateException("<ref> elements must have a \"bean\" attribute!");
                }
                bean.addPropertyReference(propertyName, id);
                return;
            }
            if ("bean".equals(name)) {
                BeanDefinitionHolder bdh = ctx.getDelegate().parseBeanDefinitionElement(first);
                BeanDefinition child = bdh.getBeanDefinition();
                bean.addPropertyValue(propertyName, (Object)child);
                return;
            }
            throw new UnsupportedOperationException("Elements with the name " + name + " are not currently supported as sub elements of " + element.getLocalName());
        }
        BeanDefinition child = ctx.getDelegate().parseCustomElement(first, (BeanDefinition)bean.getBeanDefinition());
        bean.addPropertyValue(propertyName, (Object)child);
    }

    protected Element getFirstChild(Element element) {
        return DOMUtils.getFirstElement(element);
    }

    protected void addBusWiringAttribute(BeanDefinitionBuilder bean, BusWiringType type) {
        this.addBusWiringAttribute(bean, type, null, null);
    }

    protected void addBusWiringAttribute(BeanDefinitionBuilder bean, BusWiringType type, String busName, ParserContext ctx) {
        LOG.fine("Adding " + WIRE_BUS_ATTRIBUTE + " attribute " + (Object)((Object)type) + " to bean " + bean);
        bean.getRawBeanDefinition().setAttribute(WIRE_BUS_ATTRIBUTE, (Object)type);
        if (!StringUtils.isEmpty(busName)) {
            bean.getRawBeanDefinition().setAttribute(WIRE_BUS_NAME, (Object)(busName.charAt(0) == '#' ? busName.substring(1) : busName));
        }
        if (ctx != null && !ctx.getRegistry().containsBeanDefinition(WIRE_BUS_HANDLER)) {
            BeanDefinitionBuilder b = BeanDefinitionBuilder.rootBeanDefinition((String)WIRE_BUS_HANDLER);
            ctx.getRegistry().registerBeanDefinition(WIRE_BUS_HANDLER, (BeanDefinition)b.getBeanDefinition());
        }
    }

    protected void mapElementToJaxbProperty(Element parent, BeanDefinitionBuilder bean, QName name, String propertyName) {
        this.mapElementToJaxbProperty(parent, bean, name, propertyName, null);
    }

    protected void mapElementToJaxbProperty(Element parent, BeanDefinitionBuilder bean, QName name, String propertyName, Class<?> c) {
        Element data = null;
        for (Node node = parent.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() != 1 || !name.getLocalPart().equals(node.getLocalName()) || !name.getNamespaceURI().equals(node.getNamespaceURI())) continue;
            data = (Element)node;
            break;
        }
        if (data == null) {
            return;
        }
        this.mapElementToJaxbProperty(data, bean, propertyName, c);
    }

    private synchronized JAXBContext getContext(Class<?> cls) {
        if (this.context == null || this.classes == null || !this.classes.contains(cls)) {
            try {
                HashSet tmp = new HashSet();
                if (this.classes != null) {
                    tmp.addAll(this.classes);
                }
                JAXBContextCache.addPackage(tmp, this.getJaxbPackage(), cls == null ? ((Object)((Object)this)).getClass().getClassLoader() : cls.getClassLoader());
                if (cls != null) {
                    boolean hasOf = false;
                    for (Class clazz : tmp) {
                        if (clazz.getPackage() != cls.getPackage() || !"ObjectFactory".equals(clazz.getSimpleName())) continue;
                        hasOf = true;
                    }
                    if (!hasOf) {
                        tmp.add(cls);
                    }
                }
                JAXBContextCache.scanPackages(tmp);
                JAXBContextCache.CachedContextAndSchemas ccs = JAXBContextCache.getCachedContextAndSchemas(tmp, null, null, null, false);
                this.classes = ccs.getClasses();
                this.context = ccs.getContext();
            }
            catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return this.context;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void mapElementToJaxbProperty(Element data, BeanDefinitionBuilder bean, String propertyName, Class<?> c) {
        block7: {
            try {
                XMLStreamWriter xmlWriter = null;
                Unmarshaller u = null;
                try {
                    StringWriter writer = new StringWriter();
                    xmlWriter = StaxUtils.createXMLStreamWriter(writer);
                    StaxUtils.copy(data, xmlWriter);
                    xmlWriter.flush();
                    BeanDefinitionBuilder jaxbbean = BeanDefinitionBuilder.rootBeanDefinition(JAXBBeanFactory.class);
                    jaxbbean.getRawBeanDefinition().setFactoryMethodName("createJAXBBean");
                    jaxbbean.addConstructorArgValue((Object)this.getContext(c));
                    jaxbbean.addConstructorArgValue((Object)writer.toString());
                    jaxbbean.addConstructorArgValue(c);
                    bean.addPropertyValue(propertyName, (Object)jaxbbean.getBeanDefinition());
                }
                catch (Exception ex) {
                    block8: {
                        try {
                            u = this.getContext(c).createUnmarshaller();
                            u.setEventHandler(null);
                            JAXBElement<?> obj = c != null ? u.unmarshal(data, c) : u.unmarshal(data);
                            if (obj instanceof JAXBElement) {
                                JAXBElement<?> el = obj;
                                obj = el.getValue();
                            }
                            if (obj == null) break block8;
                            bean.addPropertyValue(propertyName, obj);
                        }
                        catch (Throwable throwable) {
                            StaxUtils.close(xmlWriter);
                            JAXBUtils.closeUnmarshaller(u);
                            throw throwable;
                        }
                    }
                    StaxUtils.close(xmlWriter);
                    JAXBUtils.closeUnmarshaller(u);
                    break block7;
                }
                StaxUtils.close(xmlWriter);
                JAXBUtils.closeUnmarshaller(u);
            }
            catch (JAXBException e) {
                throw new RuntimeException("Could not parse configuration.", e);
            }
        }
    }

    public void mapElementToJaxbPropertyFactory(Element data, BeanDefinitionBuilder bean, String propertyName, Class<?> type, Class<?> factory2, String method2, Object ... args2) {
        bean.addPropertyValue(propertyName, (Object)this.mapElementToJaxbBean(data, factory2, null, type, method2, args2));
    }

    public AbstractBeanDefinition mapElementToJaxbBean(Element data, Class<?> cls, Class<?> factory2, String method2, Object ... args2) {
        return this.mapElementToJaxbBean(data, cls, factory2, cls, method2, args2);
    }

    public AbstractBeanDefinition mapElementToJaxbBean(Element data, Class<?> cls, Class<?> factory2, Class<?> jaxbClass, String method2, Object ... args2) {
        StringWriter writer = new StringWriter();
        XMLStreamWriter xmlWriter = StaxUtils.createXMLStreamWriter(writer);
        try {
            StaxUtils.copy(data, xmlWriter);
            xmlWriter.flush();
        }
        catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        finally {
            StaxUtils.close(xmlWriter);
        }
        BeanDefinitionBuilder jaxbbean = BeanDefinitionBuilder.rootBeanDefinition(cls);
        if (factory2 != null) {
            jaxbbean.getRawBeanDefinition().setFactoryBeanName(factory2.getName());
        }
        jaxbbean.getRawBeanDefinition().setFactoryMethodName(method2);
        jaxbbean.addConstructorArgValue((Object)writer.toString());
        jaxbbean.addConstructorArgValue((Object)this.getContext(jaxbClass));
        if (args2 != null) {
            for (Object o : args2) {
                jaxbbean.addConstructorArgValue(o);
            }
        }
        return jaxbbean.getBeanDefinition();
    }

    protected static <T> T unmarshalFactoryString(String s2, JAXBContext ctx, Class<T> cls) {
        StringReader reader = new StringReader(s2);
        XMLStreamReader data = StaxUtils.createXMLStreamReader(reader);
        Unmarshaller u = null;
        try {
            u = ctx.createUnmarshaller();
            JAXBElement<T> obj = u.unmarshal(data, cls);
            T t2 = cls.cast(obj.getValue());
            return t2;
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                StaxUtils.close(data);
            }
            catch (XMLStreamException ex) {
                throw new RuntimeException(ex);
            }
            JAXBUtils.closeUnmarshaller(u);
        }
    }

    protected String getJaxbPackage() {
        return "";
    }

    protected void mapToProperty(BeanDefinitionBuilder bean, String propertyName, String val) {
        if ("id".equals(propertyName)) {
            return;
        }
        if (!StringUtils.isEmpty(val)) {
            if (val.startsWith("#") && !val.startsWith("#{")) {
                bean.addPropertyReference(propertyName, val.substring(1));
            } else {
                bean.addPropertyValue(propertyName, (Object)val);
            }
        }
    }

    protected boolean isAttribute(String pre, String name) {
        return !"xmlns".equals(name) && (pre == null || !"xmlns".equals(pre)) && !"abstract".equals(name) && !"lazy-init".equals(name) && !"id".equals(name);
    }

    protected QName parseQName(Element element, String t2) {
        String ns;
        String pre;
        String local;
        int colIdx;
        if (t2.startsWith("{")) {
            int i = t2.indexOf(125);
            if (i == -1) {
                throw new RuntimeException("Namespace bracket '{' must having a closing bracket '}'.");
            }
            t2 = t2.substring(i + 1);
        }
        if ((colIdx = t2.indexOf(58)) == -1) {
            local = t2;
            pre = "";
            ns = DOMUtils.getNamespace(element, "");
        } else {
            pre = t2.substring(0, colIdx);
            local = t2.substring(colIdx + 1);
            ns = DOMUtils.getNamespace(element, pre);
        }
        return new QName(ns, local, pre);
    }

    protected String getIdOrName(Element elem) {
        StringTokenizer st;
        String names;
        String id = elem.getAttribute("id");
        if ((null == id || "".equals(id)) && null != (names = elem.getAttribute("name")) && (st = new StringTokenizer(names, ",; ")).countTokens() > 0) {
            id = st.nextToken();
        }
        return id;
    }
}

