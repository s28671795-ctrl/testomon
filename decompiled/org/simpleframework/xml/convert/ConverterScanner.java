/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.ConvertException;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.convert.ConverterFactory;
import org.simpleframework.xml.convert.ScannerBuilder;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Value;

class ConverterScanner {
    private final ConverterFactory factory = new ConverterFactory();
    private final ScannerBuilder builder = new ScannerBuilder();

    public Converter getConverter(Type type, Value value) throws Exception {
        Class real = this.getType(type, value);
        Convert convert = this.getConvert(type, real);
        if (convert != null) {
            return this.factory.getInstance(convert);
        }
        return null;
    }

    public Converter getConverter(Type type, Object value) throws Exception {
        Class real = this.getType(type, value);
        Convert convert = this.getConvert(type, real);
        if (convert != null) {
            return this.factory.getInstance(convert);
        }
        return null;
    }

    private Convert getConvert(Type type, Class real) throws Exception {
        Convert convert = this.getConvert(type);
        if (convert == null) {
            return this.getConvert(real);
        }
        return convert;
    }

    private Convert getConvert(Type type) throws Exception {
        Element element;
        Convert convert = type.getAnnotation(Convert.class);
        if (convert != null && (element = type.getAnnotation(Element.class)) == null) {
            throw new ConvertException("Element annotation required for %s", type);
        }
        return convert;
    }

    private Convert getConvert(Class real) throws Exception {
        Root root;
        Convert convert = this.getAnnotation(real, Convert.class);
        if (convert != null && (root = this.getAnnotation(real, Root.class)) == null) {
            throw new ConvertException("Root annotation required for %s", real);
        }
        return convert;
    }

    private <T extends Annotation> T getAnnotation(Class<?> type, Class<T> label) {
        return this.builder.build(type).scan(label);
    }

    private Class getType(Type type, Value value) {
        Class real = type.getType();
        if (value != null) {
            return value.getType();
        }
        return real;
    }

    private Class getType(Type type, Object value) {
        Class real = type.getType();
        if (value != null) {
            return value.getClass();
        }
        return real;
    }
}

