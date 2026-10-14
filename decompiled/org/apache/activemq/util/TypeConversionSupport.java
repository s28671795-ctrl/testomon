/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.command.ActiveMQDestination;
import org.fusesource.hawtbuf.UTF8Buffer;

public final class TypeConversionSupport {
    private static final Converter IDENTITY_CONVERTER = new Converter(){

        @Override
        public Object convert(Object value) {
            return value;
        }
    };
    private static final Map<ConversionKey, Converter> CONVERSION_MAP = new HashMap<ConversionKey, Converter>();

    private TypeConversionSupport() {
    }

    public static Object convert(Object value, Class<?> to) {
        if (value == null) {
            if (Boolean.TYPE.isAssignableFrom(to)) {
                return Boolean.FALSE;
            }
            return null;
        }
        if (to.isInstance(value)) {
            return to.cast(value);
        }
        Converter c = TypeConversionSupport.lookupConverter(value.getClass(), to);
        if (c != null) {
            return c.convert(value);
        }
        return null;
    }

    public static Converter lookupConverter(Class<?> from, Class<?> to) {
        if (from.isPrimitive()) {
            from = TypeConversionSupport.convertPrimitiveTypeToWrapperType(from);
        }
        if (to.isPrimitive()) {
            to = TypeConversionSupport.convertPrimitiveTypeToWrapperType(to);
        }
        if (from.equals(to)) {
            return IDENTITY_CONVERTER;
        }
        return CONVERSION_MAP.get(new ConversionKey(from, to));
    }

    private static Class<?> convertPrimitiveTypeToWrapperType(Class<?> type) {
        Class<Object> rc = type;
        if (type.isPrimitive()) {
            if (type == Integer.TYPE) {
                rc = Integer.class;
            } else if (type == Long.TYPE) {
                rc = Long.class;
            } else if (type == Double.TYPE) {
                rc = Double.class;
            } else if (type == Float.TYPE) {
                rc = Float.class;
            } else if (type == Short.TYPE) {
                rc = Short.class;
            } else if (type == Byte.TYPE) {
                rc = Byte.class;
            } else if (type == Boolean.TYPE) {
                rc = Boolean.class;
            }
        }
        return rc;
    }

    static {
        Converter toStringConverter = new Converter(){

            @Override
            public Object convert(Object value) {
                return value.toString();
            }
        };
        CONVERSION_MAP.put(new ConversionKey(Boolean.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(Byte.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(Short.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(Integer.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(Long.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(Float.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(Double.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(UTF8Buffer.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(URI.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(BigInteger.class, String.class), toStringConverter);
        CONVERSION_MAP.put(new ConversionKey(String.class, Boolean.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return Boolean.valueOf((String)value);
            }
        });
        CONVERSION_MAP.put(new ConversionKey(String.class, Byte.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return Byte.valueOf((String)value);
            }
        });
        CONVERSION_MAP.put(new ConversionKey(String.class, Short.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return Short.valueOf((String)value);
            }
        });
        CONVERSION_MAP.put(new ConversionKey(String.class, Integer.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return Integer.valueOf((String)value);
            }
        });
        CONVERSION_MAP.put(new ConversionKey(String.class, Long.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return Long.valueOf((String)value);
            }
        });
        CONVERSION_MAP.put(new ConversionKey(String.class, Float.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return Float.valueOf((String)value);
            }
        });
        CONVERSION_MAP.put(new ConversionKey(String.class, Double.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return Double.valueOf((String)value);
            }
        });
        Converter longConverter = new Converter(){

            @Override
            public Object convert(Object value) {
                return ((Number)value).longValue();
            }
        };
        CONVERSION_MAP.put(new ConversionKey(Byte.class, Long.class), longConverter);
        CONVERSION_MAP.put(new ConversionKey(Short.class, Long.class), longConverter);
        CONVERSION_MAP.put(new ConversionKey(Integer.class, Long.class), longConverter);
        CONVERSION_MAP.put(new ConversionKey(Date.class, Long.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return ((Date)value).getTime();
            }
        });
        Converter intConverter = new Converter(){

            @Override
            public Object convert(Object value) {
                return ((Number)value).intValue();
            }
        };
        CONVERSION_MAP.put(new ConversionKey(Byte.class, Integer.class), intConverter);
        CONVERSION_MAP.put(new ConversionKey(Short.class, Integer.class), intConverter);
        CONVERSION_MAP.put(new ConversionKey(Byte.class, Short.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return ((Number)value).shortValue();
            }
        });
        CONVERSION_MAP.put(new ConversionKey(Float.class, Double.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return new Double(((Number)value).doubleValue());
            }
        });
        CONVERSION_MAP.put(new ConversionKey(String.class, ActiveMQDestination.class), new Converter(){

            @Override
            public Object convert(Object value) {
                return ActiveMQDestination.createDestination((String)value, (byte)1);
            }
        });
        CONVERSION_MAP.put(new ConversionKey(String.class, URI.class), new Converter(){

            @Override
            public Object convert(Object value) {
                String text = value.toString();
                try {
                    return new URI(text);
                }
                catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static interface Converter {
        public Object convert(Object var1);
    }

    private static class ConversionKey {
        final Class<?> from;
        final Class<?> to;
        final int hashCode;

        public ConversionKey(Class<?> from, Class<?> to) {
            this.from = from;
            this.to = to;
            this.hashCode = from.hashCode() ^ to.hashCode() << 1;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            ConversionKey other = (ConversionKey)obj;
            if (this.from == null ? other.from != null : !this.from.equals(other.from)) {
                return false;
            }
            return !(this.to == null ? other.to != null : !this.to.equals(other.to));
        }

        public int hashCode() {
            return this.hashCode;
        }
    }
}

