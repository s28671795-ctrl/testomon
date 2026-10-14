/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.search.attribute;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.InvalidConfigurationException;
import net.sf.ehcache.search.attribute.AttributeExtractor;
import net.sf.ehcache.search.attribute.AttributeExtractorException;

public class ReflectionAttributeExtractor
implements AttributeExtractor {
    private static final String ELEMENT = "element";
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private final Part[] parts;
    private final StartType start;

    public ReflectionAttributeExtractor(String expression) throws InvalidConfigurationException {
        if (expression == null) {
            throw new NullPointerException();
        }
        String trimmed = expression.trim();
        if (trimmed.length() == 0) {
            throw new InvalidConfigurationException("empty expression");
        }
        String[] tokens = trimmed.split("\\.");
        if (tokens.length == 0) {
            throw new InvalidConfigurationException("Invalid attribute expression: " + trimmed);
        }
        String startToken = tokens[0];
        if (startToken.equalsIgnoreCase(ELEMENT)) {
            this.start = StartType.ELEMENT;
        } else if (startToken.equalsIgnoreCase(KEY)) {
            this.start = StartType.KEY;
        } else if (startToken.equalsIgnoreCase(VALUE)) {
            this.start = StartType.VALUE;
        } else {
            throw new InvalidConfigurationException("Expression must start with either \"element\", \"key\" or \"value\": " + trimmed);
        }
        this.parts = ReflectionAttributeExtractor.parseExpression(tokens, trimmed);
    }

    @Override
    public Object attributeFor(Element e, String attributeName) throws AttributeExtractorException {
        Object startObject;
        switch (this.start) {
            case ELEMENT: {
                startObject = e;
                break;
            }
            case KEY: {
                startObject = e.getObjectKey();
                break;
            }
            case VALUE: {
                startObject = e.getObjectValue();
                break;
            }
            default: {
                throw new AssertionError((Object)this.start.name());
            }
        }
        Object rv = startObject;
        for (Part part : this.parts) {
            rv = part.eval(rv);
        }
        return rv;
    }

    private static Part[] parseExpression(String[] tokens, String expression) {
        Part[] parts = new Part[tokens.length - 1];
        for (int i = 1; i < tokens.length; ++i) {
            String token = tokens[i];
            boolean method2 = false;
            if (token.endsWith("()")) {
                method2 = true;
                token = token.substring(0, token.length() - 2);
            }
            ReflectionAttributeExtractor.verifyToken(token, expression);
            parts[i - 1] = method2 ? new MethodPart(token) : new FieldPart(token);
        }
        return parts;
    }

    private static void verifyToken(String token, String expression) {
        if (token.length() == 0) {
            throw new InvalidConfigurationException("Empty element in expression: " + expression);
        }
        for (int i = 0; i < token.length(); ++i) {
            char c = token.charAt(i);
            if (!(i == 0 ? !Character.isJavaIdentifierStart(c) : !Character.isJavaIdentifierPart(c))) continue;
            throw new InvalidConfigurationException("Invalid element (" + token + ") in expression: " + expression);
        }
    }

    private static class MethodPart
    implements Part {
        private final String methodName;
        private volatile transient MethodRef cache;

        public MethodPart(String method2) {
            this.methodName = method2;
        }

        @Override
        public Object eval(Object target) {
            if (target == null) {
                throw new AttributeExtractorException("null reference encountered trying to call " + this.methodName + "()");
            }
            Class<?> c = target.getClass();
            MethodRef ref = this.cache;
            if (ref == null || ref.target != c) {
                while (true) {
                    try {
                        Method method2 = c.getDeclaredMethod(this.methodName, new Class[0]);
                        method2.setAccessible(true);
                        this.cache = ref = new MethodRef(target.getClass(), method2);
                    }
                    catch (NoSuchMethodException e) {
                        if ((c = c.getSuperclass()) != null) continue;
                        throw new AttributeExtractorException("No such method named \"" + this.methodName + "\" present on instance of " + target.getClass());
                    }
                    catch (Exception e) {
                        throw new AttributeExtractorException(e);
                    }
                    break;
                }
            }
            try {
                return ref.method.invoke(target, new Object[0]);
            }
            catch (InvocationTargetException e) {
                throw new AttributeExtractorException(e.getTargetException());
            }
            catch (Exception e) {
                throw new AttributeExtractorException(e);
            }
        }
    }

    private static class MethodRef {
        private final Method method;
        private final Class target;

        MethodRef(Class target, Method method2) {
            this.target = target;
            this.method = method2;
        }
    }

    private static class FieldRef {
        private final Class target;
        private final Field field;

        FieldRef(Class target, Field field2) {
            this.target = target;
            this.field = field2;
        }
    }

    private static class FieldPart
    implements Part {
        private final String fieldName;
        private volatile transient FieldRef cache;

        public FieldPart(String field2) {
            this.fieldName = field2;
        }

        @Override
        public Object eval(Object target) {
            if (target == null) {
                throw new AttributeExtractorException("null reference encountered trying to read field " + this.fieldName);
            }
            Class<?> c = target.getClass();
            FieldRef ref = this.cache;
            if (ref == null || ref.target != c) {
                while (true) {
                    try {
                        Field field2 = c.getDeclaredField(this.fieldName);
                        field2.setAccessible(true);
                        this.cache = ref = new FieldRef(target.getClass(), field2);
                    }
                    catch (NoSuchFieldException e) {
                        if ((c = c.getSuperclass()) != null) continue;
                        throw new AttributeExtractorException("No such field named \"" + this.fieldName + "\" present in instance of " + target.getClass());
                    }
                    catch (Exception e) {
                        throw new AttributeExtractorException(e);
                    }
                    break;
                }
            }
            try {
                return ref.field.get(target);
            }
            catch (Exception e) {
                throw new AttributeExtractorException(e);
            }
        }
    }

    private static interface Part
    extends Serializable {
        public Object eval(Object var1);
    }

    private static enum StartType {
        ELEMENT,
        VALUE,
        KEY;

    }
}

