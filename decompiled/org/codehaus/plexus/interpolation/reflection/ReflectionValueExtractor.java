/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.reflection;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.codehaus.plexus.interpolation.reflection.ClassMap;
import org.codehaus.plexus.interpolation.util.StringUtils;

public class ReflectionValueExtractor {
    private static final Class<?>[] CLASS_ARGS = new Class[0];
    private static final Object[] OBJECT_ARGS = new Object[0];
    private static final Map<Class<?>, WeakReference<ClassMap>> classMaps = new WeakHashMap();
    static final int EOF = -1;
    static final char PROPERTY_START = '.';
    static final char INDEXED_START = '[';
    static final char INDEXED_END = ']';
    static final char MAPPED_START = '(';
    static final char MAPPED_END = ')';

    private ReflectionValueExtractor() {
    }

    public static Object evaluate(String expression, Object root) throws Exception {
        return ReflectionValueExtractor.evaluate(expression, root, true);
    }

    public static Object evaluate(String expression, Object root, boolean trimRootToken) throws Exception {
        Tokenizer tokenizer;
        boolean hasDots;
        Object value = root;
        if (expression == null || "".equals(expression.trim()) || !Character.isJavaIdentifierStart(expression.charAt(0))) {
            return null;
        }
        boolean bl = hasDots = expression.indexOf(46) >= 0;
        if (trimRootToken && hasDots) {
            tokenizer = new Tokenizer(expression);
            tokenizer.nextPropertyName();
            if (tokenizer.getPosition() == -1) {
                return null;
            }
        } else {
            tokenizer = new Tokenizer("." + expression);
        }
        int propertyPosition = tokenizer.getPosition();
        block5: while (value != null && tokenizer.peekChar() != -1) {
            switch (tokenizer.skipChar()) {
                case 91: {
                    value = ReflectionValueExtractor.getIndexedValue(expression, propertyPosition, tokenizer.getPosition(), value, tokenizer.nextToken(']'));
                    continue block5;
                }
                case 40: {
                    value = ReflectionValueExtractor.getMappedValue(expression, propertyPosition, tokenizer.getPosition(), value, tokenizer.nextToken(')'));
                    continue block5;
                }
                case 46: {
                    propertyPosition = tokenizer.getPosition();
                    value = ReflectionValueExtractor.getPropertyValue(value, tokenizer.nextPropertyName());
                    continue block5;
                }
            }
            return null;
        }
        return value;
    }

    private static Object getMappedValue(String expression, int from, int to, Object value, String key) throws Exception {
        if (value == null || key == null) {
            return null;
        }
        if (value instanceof Map) {
            Object[] localParams = new Object[]{key};
            ClassMap classMap = ReflectionValueExtractor.getClassMap(value.getClass());
            Method method2 = classMap.findMethod("get", localParams);
            return method2.invoke(value, localParams);
        }
        String message = String.format("The token '%s' at position '%d' refers to a java.util.Map, but the value seems is an instance of '%s'", expression.subSequence(from, to), from, value.getClass());
        throw new Exception(message);
    }

    private static Object getIndexedValue(String expression, int from, int to, Object value, String indexStr) throws Exception {
        try {
            int index = Integer.parseInt(indexStr);
            if (value.getClass().isArray()) {
                return Array.get(value, index);
            }
            if (value instanceof List) {
                ClassMap classMap = ReflectionValueExtractor.getClassMap(value.getClass());
                Object[] localParams = new Object[]{index};
                Method method2 = classMap.findMethod("get", localParams);
                return method2.invoke(value, localParams);
            }
        }
        catch (NumberFormatException e) {
            return null;
        }
        catch (InvocationTargetException e) {
            if (e.getCause() instanceof IndexOutOfBoundsException) {
                return null;
            }
            throw e;
        }
        String message = String.format("The token '%s' at position '%d' refers to a java.util.List or an array, but the value seems is an instance of '%s'", expression.subSequence(from, to), from, value.getClass());
        throw new Exception(message);
    }

    private static Object getPropertyValue(Object value, String property) throws Exception {
        if (value == null || property == null) {
            return null;
        }
        ClassMap classMap = ReflectionValueExtractor.getClassMap(value.getClass());
        String methodBase = StringUtils.capitalizeFirstLetter(property);
        String methodName = "get" + methodBase;
        Method method2 = classMap.findMethod(methodName, CLASS_ARGS);
        if (method2 == null) {
            methodName = "is" + methodBase;
            method2 = classMap.findMethod(methodName, CLASS_ARGS);
        }
        if (method2 == null) {
            return null;
        }
        return method2.invoke(value, OBJECT_ARGS);
    }

    private static ClassMap getClassMap(Class<?> clazz) {
        ClassMap classMap;
        WeakReference<ClassMap> softRef = classMaps.get(clazz);
        if (softRef == null || (classMap = (ClassMap)softRef.get()) == null) {
            classMap = new ClassMap(clazz);
            classMaps.put(clazz, new WeakReference<ClassMap>(classMap));
        }
        return classMap;
    }

    static class Tokenizer {
        final String expression;
        int idx;

        public Tokenizer(String expression) {
            this.expression = expression;
        }

        public int peekChar() {
            return this.idx < this.expression.length() ? (int)this.expression.charAt(this.idx) : -1;
        }

        public int skipChar() {
            return this.idx < this.expression.length() ? (int)this.expression.charAt(this.idx++) : -1;
        }

        public String nextToken(char delimiter) {
            int start2 = this.idx;
            while (this.idx < this.expression.length() && delimiter != this.expression.charAt(this.idx)) {
                ++this.idx;
            }
            if (this.idx <= start2 || this.idx >= this.expression.length()) {
                return null;
            }
            return this.expression.substring(start2, this.idx++);
        }

        public String nextPropertyName() {
            int start2 = this.idx;
            while (this.idx < this.expression.length() && Character.isJavaIdentifierPart(this.expression.charAt(this.idx))) {
                ++this.idx;
            }
            if (this.idx <= start2 || this.idx > this.expression.length()) {
                return null;
            }
            return this.expression.substring(start2, this.idx);
        }

        public int getPosition() {
            return this.idx < this.expression.length() ? this.idx : -1;
        }

        public String toString() {
            return this.idx < this.expression.length() ? this.expression.substring(this.idx) : "<EOF>";
        }
    }
}

