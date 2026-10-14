/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.converters;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.namespace.QName;
import org.apache.neethi.builders.converters.Converter;
import org.apache.neethi.builders.converters.DOMToDOMConverter;
import org.apache.neethi.builders.converters.DOMToStaxConverter;
import org.apache.neethi.builders.converters.StaxToDOMConverter;
import org.apache.neethi.builders.converters.StaxToStaxConverter;

public class ConverterRegistry {
    private static final boolean HAS_AXIOM;
    private List<ConverterKey> registeredConverters = new CopyOnWriteArrayList<ConverterKey>();

    public ConverterRegistry() {
        this.registerConverter(new DOMToDOMConverter());
        this.registerConverter(new StaxToStaxConverter());
        if (HAS_AXIOM) {
            this.registerConverter("org.apache.neethi.builders.converters.OMToOMConverter");
        }
        this.registerConverter(new StaxToDOMConverter());
        this.registerConverter(new DOMToStaxConverter());
        if (HAS_AXIOM) {
            this.registerConverter("org.apache.neethi.builders.converters.DOMToOMConverter");
            this.registerConverter("org.apache.neethi.builders.converters.OMToDOMConverter");
            this.registerConverter("org.apache.neethi.builders.converters.StaxToOMConverter");
            this.registerConverter("org.apache.neethi.builders.converters.OMToStaxConverter");
        }
    }

    private void registerConverter(String name) {
        try {
            Converter c = (Converter)Class.forName(name, true, Converter.class.getClassLoader()).newInstance();
            this.registerConverter(c);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static ConverterKey createConverterKey(Converter<?, ?> converter, Class<?> c) {
        Class<?>[] interfaces = c.getInterfaces();
        for (int x = 0; x < interfaces.length; ++x) {
            if (interfaces[x] != Converter.class) continue;
            ParameterizedType pt = (ParameterizedType)c.getGenericInterfaces()[x];
            ConverterKey key = new ConverterKey();
            key.src = (Class)pt.getActualTypeArguments()[0];
            key.target = (Class)pt.getActualTypeArguments()[1];
            key.converter = converter;
            return key;
        }
        if (c.getSuperclass() != null) {
            return ConverterRegistry.createConverterKey(converter, c.getSuperclass());
        }
        return null;
    }

    public final void registerConverter(Converter<?, ?> converter) {
        ConverterKey key = ConverterRegistry.createConverterKey(converter, converter.getClass());
        this.registeredConverters.add(key);
    }

    public QName findQName(Object element) {
        ConverterKey key = this.findCompatibleConverter(element);
        try {
            return (QName)key.converter.getClass().getMethod("getQName", key.src).invoke(key.converter, element);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<QName, String> getAttributes(Object element) {
        ConverterKey key = this.findCompatibleConverter(element);
        try {
            return (Map)key.converter.getClass().getMethod("getAttributes", key.src).invoke(key.converter, element);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Iterator<Object> getChildElements(Object element) {
        ConverterKey key = this.findCompatibleConverter(element);
        try {
            return (Iterator)key.converter.getClass().getMethod("getChildren", key.src).invoke(key.converter, element);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <S, T> T convert(S src, Class<T> target) {
        for (ConverterKey ent : this.registeredConverters) {
            if (!ent.src.isInstance(src) || !ent.target.isAssignableFrom(target)) continue;
            Converter<?, ?> cv = ent.converter;
            return (T)cv.convert(src);
        }
        throw new RuntimeException("Could not find a converter to convert from " + src.getClass() + " to " + target);
    }

    private ConverterKey findCompatibleConverter(Object element) {
        for (ConverterKey ent : this.registeredConverters) {
            if (!ent.src.isInstance(element)) continue;
            return ent;
        }
        throw new RuntimeException("Could not find a converter to handle " + element.getClass());
    }

    static {
        boolean hasAxiom = true;
        try {
            Class.forName("org.apache.axiom.om.OMElement", true, Converter.class.getClassLoader());
        }
        catch (Throwable e) {
            hasAxiom = false;
        }
        HAS_AXIOM = hasAxiom;
    }

    private static class ConverterKey {
        Class<?> src;
        Class<?> target;
        Converter<?, ?> converter;

        private ConverterKey() {
        }
    }
}

