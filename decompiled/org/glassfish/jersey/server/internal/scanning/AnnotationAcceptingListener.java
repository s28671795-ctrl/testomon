/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.scanning;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import jersey.repackaged.org.objectweb.asm.AnnotationVisitor;
import jersey.repackaged.org.objectweb.asm.Attribute;
import jersey.repackaged.org.objectweb.asm.ClassReader;
import jersey.repackaged.org.objectweb.asm.ClassVisitor;
import jersey.repackaged.org.objectweb.asm.FieldVisitor;
import jersey.repackaged.org.objectweb.asm.MethodVisitor;
import jersey.repackaged.org.objectweb.asm.ModuleVisitor;
import jersey.repackaged.org.objectweb.asm.RecordComponentVisitor;
import jersey.repackaged.org.objectweb.asm.TypePath;
import org.glassfish.jersey.internal.OsgiRegistry;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.scanning.ResourceProcessor;

public final class AnnotationAcceptingListener
implements ResourceProcessor {
    private final ClassLoader classloader;
    private final Set<Class<?>> classes;
    private final Set<String> annotations;
    private final AnnotatedClassVisitor classVisitor;

    public static AnnotationAcceptingListener newJaxrsResourceAndProviderListener() {
        return new AnnotationAcceptingListener(Path.class, Provider.class);
    }

    public static AnnotationAcceptingListener newJaxrsResourceAndProviderListener(ClassLoader classLoader) {
        return new AnnotationAcceptingListener(classLoader, Path.class, Provider.class);
    }

    public AnnotationAcceptingListener(Class<? extends Annotation> ... annotations) {
        this(AccessController.doPrivileged(ReflectionHelper.getContextClassLoaderPA()), annotations);
    }

    public AnnotationAcceptingListener(ClassLoader classloader, Class<? extends Annotation> ... annotations) {
        this.classloader = classloader;
        this.classes = new LinkedHashSet();
        this.annotations = this.getAnnotationSet(annotations);
        this.classVisitor = new AnnotatedClassVisitor();
    }

    public Set<Class<?>> getAnnotatedClasses() {
        return this.classes;
    }

    private Set<String> getAnnotationSet(Class<? extends Annotation> ... annotations) {
        HashSet<String> a = new HashSet<String>();
        for (Class<? extends Annotation> c : annotations) {
            a.add("L" + c.getName().replaceAll("\\.", "/") + ";");
        }
        return a;
    }

    @Override
    public boolean accept(String name) {
        return name != null && !name.isEmpty() && name.endsWith(".class");
    }

    @Override
    public void process(String name, InputStream in) throws IOException {
        new ClassReaderWrapper(in).accept(this.classVisitor, 0);
    }

    private static class ClassReaderWrapper {
        private static final Logger LOGGER = Logger.getLogger(ClassReader.class.getName());
        private static final int WARN_VERSION = 60;
        private static final int INPUT_STREAM_DATA_CHUNK_SIZE = 4096;
        private final byte[] b;

        private ClassReaderWrapper(InputStream inputStream2) throws IOException {
            this.b = ClassReaderWrapper.readStream(inputStream2);
        }

        private void accept(ClassVisitor classVisitor, int parsingOptions) {
            int originalVersion = ClassReaderWrapper.getMajorVersion(this.b);
            if (originalVersion == 61) {
                ClassReaderWrapper.setMajorVersion(60, this.b);
                LOGGER.warning("Unsupported class file major version " + originalVersion);
            }
            ClassReader classReader = new ClassReader(this.b);
            ClassReaderWrapper.setMajorVersion(originalVersion, this.b);
            classReader.accept(classVisitor, parsingOptions);
        }

        private static void setMajorVersion(int majorVersion, byte[] b) {
            b[6] = (byte)(majorVersion >>> 8);
            b[7] = (byte)majorVersion;
        }

        private static int getMajorVersion(byte[] b) {
            return (b[6] & 0xFF) << 8 | b[7] & 0xFF;
        }

        /*
         * Loose catch block
         */
        private static byte[] readStream(InputStream inputStream2) throws IOException {
            if (inputStream2 == null) {
                throw new IOException("Class not found");
            }
            try {
                try (ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();){
                    int bytesRead;
                    byte[] data = new byte[4096];
                    while ((bytesRead = inputStream2.read(data, 0, data.length)) != -1) {
                        outputStream2.write(data, 0, bytesRead);
                    }
                    outputStream2.flush();
                    byte[] byArray = outputStream2.toByteArray();
                    return byArray;
                }
                {
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
            }
            finally {
                inputStream2.close();
            }
        }
    }

    private final class AnnotatedClassVisitor
    extends ClassVisitor {
        private String className;
        private boolean isScoped;
        private boolean isAnnotated;

        private AnnotatedClassVisitor() {
            super(589824);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.className = name;
            this.isScoped = (access & 1) != 0;
            this.isAnnotated = false;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            this.isAnnotated |= AnnotationAcceptingListener.this.annotations.contains(desc);
            return null;
        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            if (this.className.equals(name)) {
                this.isScoped = (access & 1) != 0;
                this.isScoped &= (access & 8) == 8;
            }
        }

        @Override
        public void visitEnd() {
            if (this.isScoped && this.isAnnotated) {
                AnnotationAcceptingListener.this.classes.add(this.getClassForName(this.className.replaceAll("/", ".")));
            }
        }

        @Override
        public void visitOuterClass(String string, String string0, String string1) {
        }

        @Override
        public FieldVisitor visitField(int i, String string, String string0, String string1, Object object) {
            return null;
        }

        @Override
        public void visitSource(String string, String string0) {
        }

        @Override
        public void visitAttribute(Attribute attribute) {
        }

        @Override
        public MethodVisitor visitMethod(int i, String string, String string0, String string1, String[] string2) {
            return null;
        }

        @Override
        public ModuleVisitor visitModule(String name, int access, String version) {
            return null;
        }

        @Override
        public void visitNestHost(String nestHost) {
        }

        @Override
        public void visitNestMember(String nestMember) {
        }

        @Override
        public void visitPermittedSubclass(String permittedSubclass) {
        }

        @Override
        public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature) {
            return null;
        }

        @Override
        public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
            return null;
        }

        private Class getClassForName(String className) {
            try {
                OsgiRegistry osgiRegistry = ReflectionHelper.getOsgiRegistryInstance();
                if (osgiRegistry != null) {
                    return osgiRegistry.classForNameWithException(className);
                }
                return AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA(className, AnnotationAcceptingListener.this.classloader));
            }
            catch (ClassNotFoundException ex) {
                throw new RuntimeException(LocalizationMessages.ERROR_SCANNING_CLASS_NOT_FOUND(className), ex);
            }
            catch (PrivilegedActionException pae) {
                Throwable cause = pae.getCause();
                if (cause instanceof ClassNotFoundException) {
                    throw new RuntimeException(LocalizationMessages.ERROR_SCANNING_CLASS_NOT_FOUND(className), cause);
                }
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                }
                throw new RuntimeException(cause);
            }
        }
    }
}

