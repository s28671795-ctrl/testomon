/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.serializer.AbstractSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaSerializer
extends AbstractSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JavaSerializer.class);
    private Set<String> trustedPackages = new HashSet<String>();
    private Set<Class<?>> trustedClasses;

    public JavaSerializer() {
        this.trustedPackages.addAll(Arrays.asList("java.", "javax.", "[Ljava.lang.String", "org.pac4j.", "[Lorg.pac4j.", "com.github.scribejava.", "org.opensaml.", "com.nimbusds.", "[Lcom.nimbusds.", "org.joda.", "net.minidev.json.", "org.bson.types.", "[Ljava.lang.StackTraceElement", "[B"));
        this.trustedClasses = new HashSet();
    }

    @Override
    protected byte[] internalSerializeToBytes(Object o) {
        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);){
            oos.writeObject(o);
            oos.flush();
            bytes = baos.toByteArray();
        }
        catch (IOException e) {
            logger.warn("cannot Java serialize object", e);
        }
        return bytes;
    }

    @Override
    protected Serializable internalDeserializeFromBytes(byte[] bytes) {
        Serializable o = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             RestrictedObjectInputStream ois = new RestrictedObjectInputStream(bais, this.trustedPackages, this.trustedClasses);){
            o = (Serializable)ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            logger.warn("cannot Java deserialize object", e);
        }
        return o;
    }

    public Set<String> getTrustedPackages() {
        return Collections.unmodifiableSet(this.trustedPackages);
    }

    public Set<Class<?>> getTrustedClasses() {
        return Collections.unmodifiableSet(this.trustedClasses);
    }

    public void addTrustedPackages(Collection<String> trustedPackages) {
        this.trustedPackages.addAll(trustedPackages);
    }

    public void addTrustedPackage(String trustedPackage) {
        this.trustedPackages.add(trustedPackage);
    }

    public void clearTrustedPackages() {
        this.trustedPackages.clear();
    }

    public void addTrustedClasses(Collection<Class<?>> trustedClasses) {
        this.trustedClasses.addAll(trustedClasses);
    }

    public void addTrustedClass(Class<?> trustedClass) {
        this.trustedClasses.add(trustedClass);
    }

    public void clearTrustedClasses() {
        this.trustedClasses.clear();
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "trustedPackages", this.trustedPackages, "trustedClasses", this.trustedClasses);
    }

    private static class RestrictedObjectInputStream
    extends ObjectInputStream {
        private final Set<String> trustedPackages;
        private final Map<String, Class<?>> trustedClasses;

        private RestrictedObjectInputStream(InputStream in, Set<String> trustedPackages, Set<Class<?>> trustedClasses) throws IOException {
            super(in);
            this.trustedPackages = trustedPackages;
            this.trustedClasses = trustedClasses.stream().collect(Collectors.toMap(Class::getName, Function.identity()));
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            String qualifiedClassName = desc.getName();
            Class<?> clazz = this.trustedClasses.get(qualifiedClassName);
            if (Objects.nonNull(clazz)) {
                return clazz;
            }
            if (this.trustedPackages.stream().anyMatch(qualifiedClassName::startsWith)) {
                return super.resolveClass(desc);
            }
            throw new ClassNotFoundException("Wont resolve untrusted class: " + qualifiedClassName);
        }

        @Override
        protected Class<?> resolveProxyClass(String[] interfaces) throws ClassNotFoundException {
            throw new ClassNotFoundException("Wont resolve proxy classes at all.");
        }
    }
}

