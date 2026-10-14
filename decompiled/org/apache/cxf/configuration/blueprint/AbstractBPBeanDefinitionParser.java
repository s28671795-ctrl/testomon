/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.ComponentDefinitionRegistry
 *  org.apache.aries.blueprint.ParserContext
 *  org.apache.aries.blueprint.PassThroughMetadata
 *  org.apache.aries.blueprint.mutable.MutableBeanMetadata
 *  org.apache.aries.blueprint.mutable.MutableCollectionMetadata
 *  org.apache.aries.blueprint.mutable.MutablePassThroughMetadata
 *  org.apache.aries.blueprint.mutable.MutableRefMetadata
 *  org.apache.aries.blueprint.mutable.MutableValueMetadata
 *  org.osgi.service.blueprint.reflect.CollectionMetadata
 *  org.osgi.service.blueprint.reflect.ComponentMetadata
 *  org.osgi.service.blueprint.reflect.MapMetadata
 *  org.osgi.service.blueprint.reflect.Metadata
 *  org.osgi.service.blueprint.reflect.RefMetadata
 *  org.osgi.service.blueprint.reflect.Target
 *  org.osgi.service.blueprint.reflect.ValueMetadata
 */
package org.apache.cxf.configuration.blueprint;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.aries.blueprint.ComponentDefinitionRegistry;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.PassThroughMetadata;
import org.apache.aries.blueprint.mutable.MutableBeanMetadata;
import org.apache.aries.blueprint.mutable.MutableCollectionMetadata;
import org.apache.aries.blueprint.mutable.MutablePassThroughMetadata;
import org.apache.aries.blueprint.mutable.MutableRefMetadata;
import org.apache.aries.blueprint.mutable.MutableValueMetadata;
import org.apache.cxf.bus.blueprint.BlueprintBus;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.blueprint.InterceptorTypeConverter;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.osgi.service.blueprint.reflect.CollectionMetadata;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.MapMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.osgi.service.blueprint.reflect.RefMetadata;
import org.osgi.service.blueprint.reflect.Target;
import org.osgi.service.blueprint.reflect.ValueMetadata;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public abstract class AbstractBPBeanDefinitionParser {
    private static final String XMLNS_BLUEPRINT = "http://www.osgi.org/xmlns/blueprint/v1.0.0";
    private static final String COMPONENT_ID = "component-id";
    private JAXBContext jaxbContext;
    private Set<Class<?>> jaxbClasses;

    protected boolean hasBusProperty() {
        return false;
    }

    public Metadata createValue(ParserContext context, QName qName) {
        MutableBeanMetadata v = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        v.setRuntimeClass(QName.class);
        v.addArgument((Metadata)AbstractBPBeanDefinitionParser.createValue(context, qName.getNamespaceURI()), null, 0);
        v.addArgument((Metadata)AbstractBPBeanDefinitionParser.createValue(context, qName.getLocalPart()), null, 1);
        return v;
    }

    protected Metadata parseListData(ParserContext context, ComponentMetadata enclosingComponent, Element element) {
        MutableCollectionMetadata m4 = (MutableCollectionMetadata)context.parseElement(CollectionMetadata.class, enclosingComponent, element);
        m4.setCollectionClass(List.class);
        return m4;
    }

    protected Metadata parseMapData(ParserContext context, ComponentMetadata enclosingComponent, Element element) {
        return (Metadata)context.parseElement(MapMetadata.class, enclosingComponent, element);
    }

    protected void setFirstChildAsProperty(Element element, ParserContext ctx, MutableBeanMetadata bean, String propertyName) {
        Element first = DOMUtils.getFirstElement(element);
        if (first == null) {
            throw new IllegalStateException(propertyName + " property must have child elements!");
        }
        if (first.getNamespaceURI().equals(XMLNS_BLUEPRINT)) {
            String name = first.getLocalName();
            if ("ref".equals(name)) {
                String id = first.getAttribute(COMPONENT_ID);
                if (id == null) {
                    throw new IllegalStateException("<ref> elements must have a \"component-id\" attribute!");
                }
                bean.addProperty(propertyName, (Metadata)AbstractBPBeanDefinitionParser.createRef(ctx, id));
            } else {
                bean.addProperty(propertyName, (Metadata)ctx.parseElement(Metadata.class, (ComponentMetadata)bean, first));
            }
        } else {
            bean.addProperty(propertyName, (Metadata)ctx.parseElement(Metadata.class, (ComponentMetadata)bean, first));
        }
    }

    public QName parseQName(Element element, String t2) {
        String ns;
        String pre;
        String local;
        int colIdx;
        String t1 = t2;
        if (t1.startsWith("{")) {
            int i = t1.indexOf(125);
            if (i == -1) {
                throw new RuntimeException("Namespace bracket '{' must having a closing bracket '}'.");
            }
            t1 = t1.substring(i + 1);
        }
        if ((colIdx = t1.indexOf(58)) == -1) {
            local = t1;
            pre = "";
            ns = DOMUtils.getNamespace(element, "");
        } else {
            pre = t1.substring(0, colIdx);
            local = t1.substring(colIdx + 1);
            ns = DOMUtils.getNamespace(element, pre);
        }
        return new QName(ns, local, pre);
    }

    protected boolean parseAttributes(Element element, ParserContext ctx, MutableBeanMetadata bean) {
        NamedNodeMap atts = element.getAttributes();
        boolean setBus = false;
        for (int i = 0; i < atts.getLength(); ++i) {
            String prefix;
            Attr node = (Attr)atts.item(i);
            String val = node.getValue();
            String pre = node.getPrefix();
            String name = node.getLocalName();
            if (this.isNamespace(name, prefix = node.getPrefix())) continue;
            if ("createdFromAPI".equals(name) || "abstract".equals(name)) {
                bean.setScope("prototype");
                continue;
            }
            if ("depends-on".equals(name)) {
                bean.addDependsOn(val);
                continue;
            }
            if ("name".equals(name)) {
                this.processNameAttribute(element, ctx, bean, val);
                continue;
            }
            if ("bus".equals(name)) {
                this.processBusAttribute(element, ctx, bean, val);
                continue;
            }
            if ("id".equals(name) || !this.isAttribute(pre, name)) continue;
            this.mapAttribute(bean, element, name, val, ctx);
        }
        return setBus;
    }

    protected void processBusAttribute(Element element, ParserContext ctx, MutableBeanMetadata bean, String val) {
        if (this.hasBusProperty()) {
            bean.addProperty("bus", (Metadata)this.getBusRef(ctx, val));
        } else {
            bean.addArgument((Metadata)this.getBusRef(ctx, val), null, 0);
        }
    }

    protected void processNameAttribute(Element element, ParserContext ctx, MutableBeanMetadata bean, String val) {
    }

    protected void mapAttribute(MutableBeanMetadata bean, Element e, String name, String val, ParserContext context) {
        this.mapToProperty(bean, name, val, context);
    }

    protected boolean isAttribute(String pre, String name) {
        return !"xmlns".equals(name) && (pre == null || !"xmlns".equals(pre)) && !"abstract".equals(name) && !"lazy-init".equals(name) && !"id".equals(name);
    }

    protected boolean isNamespace(String name, String prefix) {
        return "xmlns".equals(prefix) || prefix == null && "xmlns".equals(name);
    }

    protected void mapElement(ParserContext ctx, MutableBeanMetadata bean, Element el, String name) {
    }

    protected void mapToProperty(MutableBeanMetadata bean, String propertyName, String val, ParserContext context) {
        if ("id".equals(propertyName)) {
            return;
        }
        if (!StringUtils.isEmpty(val)) {
            if (val.startsWith("#")) {
                bean.addProperty(propertyName, (Metadata)AbstractBPBeanDefinitionParser.createRef(context, val.substring(1)));
            } else {
                bean.addProperty(propertyName, (Metadata)AbstractBPBeanDefinitionParser.createValue(context, val));
            }
        }
    }

    public static ValueMetadata createValue(ParserContext context, String value) {
        MutableValueMetadata v = (MutableValueMetadata)context.createMetadata(MutableValueMetadata.class);
        v.setStringValue(value);
        return v;
    }

    public static RefMetadata createRef(ParserContext context, String value) {
        MutableRefMetadata r = (MutableRefMetadata)context.createMetadata(MutableRefMetadata.class);
        r.setComponentId(value);
        return r;
    }

    public static PassThroughMetadata createPassThrough(ParserContext context, Object value) {
        MutablePassThroughMetadata v = (MutablePassThroughMetadata)context.createMetadata(MutablePassThroughMetadata.class);
        v.setObject(value);
        return v;
    }

    public static MutableBeanMetadata createObjectOfClass(ParserContext context, String value) {
        MutableBeanMetadata v = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
        v.setClassName(value);
        return v;
    }

    protected MutableBeanMetadata getBus(ParserContext context, String name) {
        ComponentDefinitionRegistry cdr = context.getComponentDefinitionRegistry();
        ComponentMetadata meta = cdr.getComponentDefinition("blueprintBundle");
        if (!cdr.containsComponentDefinition(InterceptorTypeConverter.class.getName())) {
            MutablePassThroughMetadata md = (MutablePassThroughMetadata)context.createMetadata(MutablePassThroughMetadata.class);
            md.setObject((Object)new InterceptorTypeConverter());
            md.setId(InterceptorTypeConverter.class.getName());
            context.getComponentDefinitionRegistry().registerTypeConverter((Target)md);
        }
        if (!cdr.containsComponentDefinition(name)) {
            MutableBeanMetadata bus = (MutableBeanMetadata)context.createMetadata(MutableBeanMetadata.class);
            bus.setId(name);
            bus.setRuntimeClass(BlueprintBus.class);
            if (meta != null) {
                bus.addProperty("bundleContext", (Metadata)AbstractBPBeanDefinitionParser.createRef(context, "blueprintBundleContext"));
            }
            bus.addProperty("blueprintContainer", (Metadata)AbstractBPBeanDefinitionParser.createRef(context, "blueprintContainer"));
            bus.setDestroyMethod("shutdown");
            bus.setInitMethod("initialize");
            context.getComponentDefinitionRegistry().registerComponentDefinition((ComponentMetadata)bus);
            return bus;
        }
        return (MutableBeanMetadata)cdr.getComponentDefinition(name);
    }

    protected RefMetadata getBusRef(ParserContext context, String name) {
        if ("cxf".equals(name)) {
            this.getBus(context, name);
        }
        return AbstractBPBeanDefinitionParser.createRef(context, name);
    }

    protected void parseChildElements(Element element, ParserContext ctx, MutableBeanMetadata bean) {
        Element el = DOMUtils.getFirstElement(element);
        while (el != null) {
            String name = el.getLocalName();
            this.mapElement(ctx, bean, el, name);
            el = DOMUtils.getNextElement(el);
        }
    }

    protected void mapElementToJaxbProperty(ParserContext ctx, MutableBeanMetadata bean, Element parent, QName name, String propertyName, Class<?> c) {
        Element data = DOMUtils.getFirstChildWithName(parent, name);
        if (data == null) {
            return;
        }
        this.mapElementToJaxbProperty(ctx, bean, data, propertyName, c);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void mapElementToJaxbProperty(ParserContext ctx, MutableBeanMetadata bean, Element data, String propertyName, Class<?> c) {
        block7: {
            try {
                XMLStreamWriter xmlWriter = null;
                Unmarshaller u = null;
                try {
                    StringWriter writer = new StringWriter();
                    xmlWriter = StaxUtils.createXMLStreamWriter(writer);
                    StaxUtils.copy(data, xmlWriter);
                    xmlWriter.flush();
                    MutableBeanMetadata factory2 = (MutableBeanMetadata)ctx.createMetadata(MutableBeanMetadata.class);
                    factory2.setClassName(c.getName());
                    factory2.setFactoryComponent((Target)AbstractBPBeanDefinitionParser.createPassThrough(ctx, new JAXBBeanFactory(this.getContext(c), c)));
                    factory2.setFactoryMethod("createJAXBBean");
                    factory2.addArgument((Metadata)AbstractBPBeanDefinitionParser.createValue(ctx, writer.toString()), String.class.getName(), 0);
                    bean.addProperty(propertyName, (Metadata)factory2);
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
                            MutablePassThroughMetadata value = (MutablePassThroughMetadata)ctx.createMetadata(MutablePassThroughMetadata.class);
                            value.setObject(obj);
                            bean.addProperty(propertyName, (Metadata)value);
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

    protected synchronized JAXBContext getContext(Class<?> cls) {
        if (this.jaxbContext == null || this.jaxbClasses == null || !this.jaxbClasses.contains(cls)) {
            try {
                HashSet tmp = new HashSet();
                if (this.jaxbClasses != null) {
                    tmp.addAll(this.jaxbClasses);
                }
                JAXBContextCache.addPackage(tmp, PackageUtils.getPackageName(cls), cls == null ? this.getClass().getClassLoader() : cls.getClassLoader());
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
                this.jaxbClasses = ccs.getClasses();
                this.jaxbContext = ccs.getContext();
            }
            catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return this.jaxbContext;
    }

    public static class JAXBBeanFactory {
        final JAXBContext ctx;
        final Class<?> cls;

        public JAXBBeanFactory(JAXBContext c, Class<?> c2) {
            this.ctx = c;
            this.cls = c2;
        }

        public Object createJAXBBean(String v) {
            XMLStreamReader reader = StaxUtils.createXMLStreamReader(new StringReader(v));
            try {
                JAXBElement<?> el = JAXBUtils.unmarshall(this.ctx, reader, this.cls);
                if (el != null) {
                    Object obj = el.getValue();
                    return obj;
                }
                Object var4_6 = null;
                return var4_6;
            }
            catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            finally {
                try {
                    reader.close();
                }
                catch (XMLStreamException xMLStreamException) {}
            }
        }
    }
}

