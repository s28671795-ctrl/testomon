/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NoContentException;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.message.internal.AbstractMessageReaderWriterProvider;
import org.glassfish.jersey.message.internal.MessageBodyProcessingException;

@Produces(value={"text/plain"})
@Consumes(value={"text/plain"})
@Singleton
final class BasicTypesMessageProvider
extends AbstractMessageReaderWriterProvider<Object> {
    BasicTypesMessageProvider() {
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return this.canProcess(type);
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        String entityString = BasicTypesMessageProvider.readFromAsString(entityStream, mediaType);
        if (entityString.isEmpty()) {
            throw new NoContentException(LocalizationMessages.ERROR_READING_ENTITY_MISSING());
        }
        PrimitiveTypes primitiveType = PrimitiveTypes.forType(type);
        if (primitiveType != null) {
            return primitiveType.convert(entityString);
        }
        Constructor constructor = AccessController.doPrivileged(ReflectionHelper.getStringConstructorPA(type));
        if (constructor != null) {
            try {
                return type.cast(constructor.newInstance(entityString));
            }
            catch (Exception e) {
                throw new MessageBodyProcessingException(LocalizationMessages.ERROR_ENTITY_PROVIDER_BASICTYPES_CONSTRUCTOR(type));
            }
        }
        if (AtomicInteger.class.isAssignableFrom(type)) {
            return new AtomicInteger((Integer)PrimitiveTypes.INTEGER.convert(entityString));
        }
        if (AtomicLong.class.isAssignableFrom(type)) {
            return new AtomicLong((Long)PrimitiveTypes.LONG.convert(entityString));
        }
        throw new MessageBodyProcessingException(LocalizationMessages.ERROR_ENTITY_PROVIDER_BASICTYPES_UNKWNOWN(type));
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return this.canProcess(type);
    }

    private boolean canProcess(Class<?> type) {
        if (PrimitiveTypes.forType(type) != null) {
            return true;
        }
        if (Number.class.isAssignableFrom(type)) {
            Constructor constructor = AccessController.doPrivileged(ReflectionHelper.getStringConstructorPA(type));
            if (constructor != null) {
                return true;
            }
            if (AtomicInteger.class.isAssignableFrom(type) || AtomicLong.class.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long getSize(Object t2, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return t2.toString().length();
    }

    @Override
    public void writeTo(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        BasicTypesMessageProvider.writeToAsString(o.toString(), entityStream, mediaType);
    }

    private static enum PrimitiveTypes {
        BYTE((Class)Byte.class, (Class)Byte.TYPE){

            @Override
            public Object convert(String s2) {
                return Byte.valueOf(s2);
            }
        }
        ,
        SHORT((Class)Short.class, (Class)Short.TYPE){

            @Override
            public Object convert(String s2) {
                return Short.valueOf(s2);
            }
        }
        ,
        INTEGER((Class)Integer.class, (Class)Integer.TYPE){

            @Override
            public Object convert(String s2) {
                return Integer.valueOf(s2);
            }
        }
        ,
        LONG((Class)Long.class, (Class)Long.TYPE){

            @Override
            public Object convert(String s2) {
                return Long.valueOf(s2);
            }
        }
        ,
        FLOAT((Class)Float.class, (Class)Float.TYPE){

            @Override
            public Object convert(String s2) {
                return Float.valueOf(s2);
            }
        }
        ,
        DOUBLE((Class)Double.class, (Class)Double.TYPE){

            @Override
            public Object convert(String s2) {
                return Double.valueOf(s2);
            }
        }
        ,
        BOOLEAN((Class)Boolean.class, (Class)Boolean.TYPE){

            @Override
            public Object convert(String s2) {
                return Boolean.valueOf(s2);
            }
        }
        ,
        CHAR((Class)Character.class, (Class)Character.TYPE){

            @Override
            public Object convert(String s2) {
                if (s2.length() != 1) {
                    throw new MessageBodyProcessingException(LocalizationMessages.ERROR_ENTITY_PROVIDER_BASICTYPES_CHARACTER_MORECHARS());
                }
                return Character.valueOf(s2.charAt(0));
            }
        };

        private final Class<?> wrapper;
        private final Class<?> primitive;

        public static PrimitiveTypes forType(Class<?> type) {
            for (PrimitiveTypes primitive : PrimitiveTypes.values()) {
                if (!primitive.supports(type)) continue;
                return primitive;
            }
            return null;
        }

        private PrimitiveTypes(Class<?> wrapper, Class<?> primitive) {
            this.wrapper = wrapper;
            this.primitive = primitive;
        }

        public abstract Object convert(String var1);

        public boolean supports(Class<?> type) {
            return type == this.wrapper || type == this.primitive;
        }
    }
}

