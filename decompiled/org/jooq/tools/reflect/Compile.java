/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.reflect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.jooq.tools.reflect.CompileOptions;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;

class Compile {
    Compile() {
    }

    static Class<?> compile(String className, String content, CompileOptions compileOptions) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        ClassLoader cl = lookup.lookupClass().getClassLoader();
        try {
            return cl.loadClass(className);
        }
        catch (ClassNotFoundException ignore) {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            try {
                ClassFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
                ArrayList<CharSequenceJavaFileObject> files = new ArrayList<CharSequenceJavaFileObject>();
                files.add(new CharSequenceJavaFileObject(className, content));
                StringWriter out = new StringWriter();
                ArrayList<String> options = new ArrayList<String>(compileOptions.options);
                if (!options.contains("-classpath")) {
                    StringBuilder classpath = new StringBuilder();
                    String separator = System.getProperty("path.separator");
                    String cp = System.getProperty("java.class.path");
                    String mp = System.getProperty("jdk.module.path");
                    if (cp != null && !"".equals(cp)) {
                        classpath.append(cp);
                    }
                    if (mp != null && !"".equals(mp)) {
                        classpath.append(mp);
                    }
                    if (cl instanceof URLClassLoader) {
                        for (URL url2 : ((URLClassLoader)cl).getURLs()) {
                            if (classpath.length() > 0) {
                                classpath.append(separator);
                            }
                            if (!"file".equals(url2.getProtocol())) continue;
                            classpath.append(new File(url2.toURI()));
                        }
                    }
                    options.addAll(Arrays.asList("-classpath", classpath.toString()));
                }
                JavaCompiler.CompilationTask task = compiler.getTask(out, fileManager, null, options, null, files);
                if (!compileOptions.processors.isEmpty()) {
                    task.setProcessors(compileOptions.processors);
                }
                task.call();
                if (fileManager.isEmpty()) {
                    throw new ReflectException("Compilation error: " + String.valueOf(out));
                }
                Class<?> result = null;
                if (Reflect.CACHED_LOOKUP_CONSTRUCTOR != null) {
                    result = fileManager.loadAndReturnMainClass(className, (name, bytes) -> (Class)Reflect.on(cl).call("defineClass", name, bytes, 0, ((byte[])bytes).length).get());
                } else {
                    Class caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(s2 -> ((StackWalker.StackFrame)s2.skip(2L).findFirst().get()).getDeclaringClass());
                    if (className.startsWith(caller.getPackageName() + ".") && Character.isUpperCase(className.charAt(caller.getPackageName().length() + 1))) {
                        MethodHandles.Lookup privateLookup = MethodHandles.privateLookupIn(caller, lookup);
                        result = fileManager.loadAndReturnMainClass(className, (name, bytes) -> privateLookup.defineClass((byte[])bytes));
                    } else {
                        ByteArrayClassLoader c = new ByteArrayClassLoader(fileManager.classes());
                        result = fileManager.loadAndReturnMainClass(className, (name, bytes) -> c.loadClass((String)name));
                    }
                }
                return result;
            }
            catch (ReflectException e) {
                throw e;
            }
            catch (Exception e) {
                throw new ReflectException("Error while compiling " + className, e);
            }
        }
    }

    static final class ClassFileManager
    extends ForwardingJavaFileManager<StandardJavaFileManager> {
        private final Map<String, JavaFileObject> fileObjectMap = new HashMap<String, JavaFileObject>();
        private Map<String, byte[]> classes;

        ClassFileManager(StandardJavaFileManager standardManager) {
            super(standardManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            JavaFileObject result = new JavaFileObject(className, kind);
            this.fileObjectMap.put(className, result);
            return result;
        }

        boolean isEmpty() {
            return this.fileObjectMap.isEmpty();
        }

        Map<String, byte[]> classes() {
            if (this.classes == null) {
                this.classes = new HashMap<String, byte[]>();
                for (Map.Entry<String, JavaFileObject> entry : this.fileObjectMap.entrySet()) {
                    this.classes.put(entry.getKey(), entry.getValue().getBytes());
                }
            }
            return this.classes;
        }

        Class<?> loadAndReturnMainClass(String mainClassName, ThrowingBiFunction<String, byte[], Class<?>> definer) throws Exception {
            Class<?> result = null;
            for (Map.Entry<String, byte[]> entry : this.classes().entrySet()) {
                Class<?> c = definer.apply(entry.getKey(), entry.getValue());
                if (!mainClassName.equals(entry.getKey())) continue;
                result = c;
            }
            return result;
        }
    }

    static final class CharSequenceJavaFileObject
    extends SimpleJavaFileObject {
        final CharSequence content;

        public CharSequenceJavaFileObject(String className, CharSequence content) {
            super(URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
            this.content = content;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return this.content;
        }
    }

    @FunctionalInterface
    static interface ThrowingBiFunction<T, U, R> {
        public R apply(T var1, U var2) throws Exception;
    }

    static final class ByteArrayClassLoader
    extends ClassLoader {
        private final Map<String, byte[]> classes;

        ByteArrayClassLoader(Map<String, byte[]> classes) {
            super(ByteArrayClassLoader.class.getClassLoader());
            this.classes = classes;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] bytes = this.classes.get(name);
            if (bytes == null) {
                return super.findClass(name);
            }
            return this.defineClass(name, bytes, 0, bytes.length);
        }
    }

    static final class JavaFileObject
    extends SimpleJavaFileObject {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        JavaFileObject(String name, JavaFileObject.Kind kind) {
            super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
        }

        byte[] getBytes() {
            return this.os.toByteArray();
        }

        @Override
        public OutputStream openOutputStream() {
            return this.os;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return new String(this.os.toByteArray(), StandardCharsets.UTF_8);
        }
    }
}

