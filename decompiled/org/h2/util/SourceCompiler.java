/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.util.IOUtils;
import org.h2.util.StringUtils;
import org.h2.util.Task;
import org.h2.util.Utils;
import org.h2.util.Utils10;

public class SourceCompiler {
    static final JavaCompiler JAVA_COMPILER;
    private static final Class<?> JAVAC_SUN;
    private static final String COMPILE_DIR;
    final HashMap<String, String> sources = new HashMap();
    final HashMap<String, Class<?>> compiled = new HashMap();
    final Map<String, CompiledScript> compiledScripts = new HashMap<String, CompiledScript>();
    boolean useJavaSystemCompiler = SysProperties.JAVA_SYSTEM_COMPILER;

    public void setSource(String string, String string2) {
        this.sources.put(string, string2);
        this.compiled.clear();
    }

    public void setJavaSystemCompiler(boolean bl) {
        this.useJavaSystemCompiler = bl;
    }

    public Class<?> getClass(String string) throws ClassNotFoundException {
        Class<?> clazz = this.compiled.get(string);
        if (clazz != null) {
            return clazz;
        }
        String string2 = this.sources.get(string);
        if (SourceCompiler.isGroovySource(string2)) {
            Class<?> clazz2 = GroovyCompiler.parseClass(string2, string);
            this.compiled.put(string, clazz2);
            return clazz2;
        }
        ClassLoader classLoader = new ClassLoader(this.getClass().getClassLoader()){

            @Override
            public Class<?> findClass(String string) throws ClassNotFoundException {
                Class<?> clazz = SourceCompiler.this.compiled.get(string);
                if (clazz == null) {
                    byte[] byArray;
                    String string2;
                    String string3 = SourceCompiler.this.sources.get(string);
                    String string4 = null;
                    int n = string.lastIndexOf(46);
                    if (n >= 0) {
                        string4 = string.substring(0, n);
                        string2 = string.substring(n + 1);
                    } else {
                        string2 = string;
                    }
                    String string5 = SourceCompiler.getCompleteSourceCode(string4, string2, string3);
                    clazz = JAVA_COMPILER != null && SourceCompiler.this.useJavaSystemCompiler ? SourceCompiler.this.javaxToolsJavac(string4, string2, string5) : ((byArray = SourceCompiler.this.javacCompile(string4, string2, string5)) == null ? this.findSystemClass(string) : this.defineClass(string, byArray, 0, byArray.length));
                    SourceCompiler.this.compiled.put(string, clazz);
                }
                return clazz;
            }
        };
        return classLoader.loadClass(string);
    }

    private static boolean isGroovySource(String string) {
        return string.startsWith("//groovy") || string.startsWith("@groovy");
    }

    private static boolean isJavascriptSource(String string) {
        return string.startsWith("//javascript");
    }

    private static boolean isRubySource(String string) {
        return string.startsWith("#ruby");
    }

    public static boolean isJavaxScriptSource(String string) {
        return SourceCompiler.isJavascriptSource(string) || SourceCompiler.isRubySource(string);
    }

    public CompiledScript getCompiledScript(String string2) throws ScriptException {
        CompiledScript compiledScript = this.compiledScripts.get(string2);
        if (compiledScript == null) {
            String string3;
            String string4 = this.sources.get(string2);
            if (SourceCompiler.isJavascriptSource(string4)) {
                string3 = "javascript";
            } else if (SourceCompiler.isRubySource(string4)) {
                string3 = "ruby";
            } else {
                throw new IllegalStateException("Unknown language for " + string4);
            }
            ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName(string3);
            if (scriptEngine.getClass().getName().equals("com.oracle.truffle.js.scriptengine.GraalJSScriptEngine")) {
                Bindings bindings = scriptEngine.getBindings(100);
                bindings.put("polyglot.js.allowHostAccess", (Object)true);
                bindings.put("polyglot.js.allowHostClassLookup", string -> true);
            }
            compiledScript = ((Compilable)((Object)scriptEngine)).compile(string4);
            this.compiledScripts.put(string2, compiledScript);
        }
        return compiledScript;
    }

    public Method getMethod(String string) throws ClassNotFoundException {
        Method[] methodArray;
        Class<?> clazz = this.getClass(string);
        for (Method method2 : methodArray = clazz.getDeclaredMethods()) {
            String string2;
            int n = method2.getModifiers();
            if (!Modifier.isPublic(n) || !Modifier.isStatic(n) || (string2 = method2.getName()).startsWith("_") || method2.getName().equals("main")) continue;
            return method2;
        }
        return null;
    }

    byte[] javacCompile(String string, String string2, String string3) {
        Path path = Paths.get(COMPILE_DIR, new String[0]);
        if (string != null) {
            path = path.resolve(string.replace('.', '/'));
            try {
                Files.createDirectories(path, new FileAttribute[0]);
            }
            catch (Exception exception) {
                throw DbException.convert(exception);
            }
        }
        Path path2 = path.resolve(string2 + ".java");
        Path path3 = path.resolve(string2 + ".class");
        try {
            Files.write(path2, string3.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
            Files.deleteIfExists(path3);
            if (JAVAC_SUN != null) {
                SourceCompiler.javacSun(path2);
            } else {
                SourceCompiler.javacProcess(path2);
            }
            byte[] byArray = Files.readAllBytes(path3);
            return byArray;
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
        finally {
            try {
                Files.deleteIfExists(path2);
            }
            catch (IOException iOException) {}
            try {
                Files.deleteIfExists(path3);
            }
            catch (IOException iOException) {}
        }
    }

    static String getCompleteSourceCode(String string, String string2, String string3) {
        if (string3.startsWith("package ")) {
            return string3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (string != null) {
            stringBuilder.append("package ").append(string).append(";\n");
        }
        int n = string3.indexOf("@CODE");
        String string4 = "import java.util.*;\nimport java.math.*;\nimport java.sql.*;\n";
        if (n >= 0) {
            string4 = string3.substring(0, n);
            string3 = string3.substring("@CODE".length() + n);
        }
        stringBuilder.append(string4);
        stringBuilder.append("public class ").append(string2).append(" {\n    public static ").append(string3).append("\n}\n");
        return stringBuilder.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Class<?> javaxToolsJavac(String string, String string2, String string3) {
        String string4 = string + "." + string2;
        StringWriter stringWriter = new StringWriter();
        try (ClassFileManager classFileManager = new ClassFileManager(JAVA_COMPILER.getStandardFileManager(null, null, null));){
            boolean bl;
            ArrayList<StringJavaFileObject> arrayList = new ArrayList<StringJavaFileObject>();
            arrayList.add(new StringJavaFileObject(string4, string3));
            Object object = JAVA_COMPILER;
            synchronized (object) {
                bl = JAVA_COMPILER.getTask(stringWriter, classFileManager, null, null, null, arrayList).call();
            }
            object = stringWriter.toString();
            SourceCompiler.handleSyntaxError((String)object, bl ? 0 : 1);
            Class<?> clazz = classFileManager.getClassLoader(null).loadClass(string4);
            return clazz;
        }
        catch (IOException | ClassNotFoundException exception) {
            throw DbException.convert(exception);
        }
    }

    private static void javacProcess(Path path) {
        SourceCompiler.exec("javac", "-sourcepath", COMPILE_DIR, "-d", COMPILE_DIR, "-encoding", "UTF-8", path.toAbsolutePath().toString());
    }

    private static int exec(String ... stringArray) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
            processBuilder.environment().remove("JAVA_TOOL_OPTIONS");
            processBuilder.command(stringArray);
            Process process = processBuilder.start();
            SourceCompiler.copyInThread(process.getInputStream(), byteArrayOutputStream);
            SourceCompiler.copyInThread(process.getErrorStream(), byteArrayOutputStream);
            process.waitFor();
            String string = Utils10.byteArrayOutputStreamToString(byteArrayOutputStream, StandardCharsets.UTF_8);
            SourceCompiler.handleSyntaxError(string, process.exitValue());
            return process.exitValue();
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
    }

    private static void copyInThread(final InputStream inputStream2, final OutputStream outputStream2) {
        new Task(){

            @Override
            public void call() throws IOException {
                IOUtils.copy(inputStream2, outputStream2);
            }
        }.execute();
    }

    private static synchronized void javacSun(Path path) {
        PrintStream printStream = System.err;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            System.setErr(new PrintStream((OutputStream)byteArrayOutputStream, false, "UTF-8"));
            Method method2 = JAVAC_SUN.getMethod("compile", String[].class);
            Object obj = JAVAC_SUN.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            Integer n = (Integer)method2.invoke(obj, new Object[]{new String[]{"-sourcepath", COMPILE_DIR, "-d", COMPILE_DIR, "-encoding", "UTF-8", path.toAbsolutePath().toString()}});
            String string = Utils10.byteArrayOutputStreamToString(byteArrayOutputStream, StandardCharsets.UTF_8);
            SourceCompiler.handleSyntaxError(string, n);
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
        finally {
            System.setErr(printStream);
        }
    }

    private static void handleSyntaxError(String string, int n) {
        if (0 == n) {
            return;
        }
        boolean bl = false;
        BufferedReader bufferedReader = new BufferedReader(new StringReader(string));
        try {
            String string2;
            while ((string2 = bufferedReader.readLine()) != null) {
                if (string2.endsWith("warning") || string2.endsWith("warnings") || string2.startsWith("Note:") || string2.startsWith("warning:")) continue;
                bl = true;
                break;
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (bl) {
            string = StringUtils.replaceAll(string, COMPILE_DIR, "");
            throw DbException.get(42000, string);
        }
    }

    static {
        Class<?> clazz;
        JavaCompiler javaCompiler;
        COMPILE_DIR = Utils.getProperty("java.io.tmpdir", ".");
        try {
            javaCompiler = ToolProvider.getSystemJavaCompiler();
        }
        catch (Exception exception) {
            javaCompiler = null;
        }
        JAVA_COMPILER = javaCompiler;
        try {
            clazz = Class.forName("com.sun.tools.javac.Main");
        }
        catch (Exception exception) {
            clazz = null;
        }
        JAVAC_SUN = clazz;
    }

    static class ClassFileManager
    extends ForwardingJavaFileManager<StandardJavaFileManager> {
        Map<String, JavaClassObject> classObjectsByName = new HashMap<String, JavaClassObject>();
        private SecureClassLoader classLoader = new SecureClassLoader(){

            @Override
            protected Class<?> findClass(String string) throws ClassNotFoundException {
                byte[] byArray = classObjectsByName.get(string).getBytes();
                return super.defineClass(string, byArray, 0, byArray.length);
            }
        };

        public ClassFileManager(StandardJavaFileManager standardJavaFileManager) {
            super(standardJavaFileManager);
        }

        @Override
        public ClassLoader getClassLoader(JavaFileManager.Location location) {
            return this.classLoader;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String string, JavaFileObject.Kind kind, FileObject fileObject) throws IOException {
            JavaClassObject javaClassObject = new JavaClassObject(string, kind);
            this.classObjectsByName.put(string, javaClassObject);
            return javaClassObject;
        }
    }

    static class JavaClassObject
    extends SimpleJavaFileObject {
        private final ByteArrayOutputStream out = new ByteArrayOutputStream();

        public JavaClassObject(String string, JavaFileObject.Kind kind) {
            super(URI.create("string:///" + string.replace('.', '/') + kind.extension), kind);
        }

        public byte[] getBytes() {
            return this.out.toByteArray();
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return this.out;
        }
    }

    static class StringJavaFileObject
    extends SimpleJavaFileObject {
        private final String sourceCode;

        public StringJavaFileObject(String string, String string2) {
            super(URI.create("string:///" + string.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
            this.sourceCode = string2;
        }

        @Override
        public CharSequence getCharContent(boolean bl) {
            return this.sourceCode;
        }
    }

    private static final class GroovyCompiler {
        private static final Object LOADER;
        private static final Throwable INIT_FAIL_EXCEPTION;

        private GroovyCompiler() {
        }

        public static Class<?> parseClass(String string, String string2) {
            if (LOADER == null) {
                throw new RuntimeException("Compile fail: no Groovy jar in the classpath", INIT_FAIL_EXCEPTION);
            }
            try {
                Object object = Utils.newInstance("groovy.lang.GroovyCodeSource", string, string2 + ".groovy", "UTF-8");
                Utils.callMethod(object, "setCachable", false);
                return (Class)Utils.callMethod(LOADER, "parseClass", object);
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        static {
            Object object = null;
            Exception exception = null;
            try {
                Class<?> clazz = Class.forName("org.codehaus.groovy.control.customizers.ImportCustomizer");
                Object object2 = Utils.newInstance("org.codehaus.groovy.control.customizers.ImportCustomizer", new Object[0]);
                String[] stringArray = new String[]{"java.sql.Connection", "java.sql.Types", "java.sql.ResultSet", "groovy.sql.Sql", "org.h2.tools.SimpleResultSet"};
                Utils.callMethod(object2, "addImports", new Object[]{stringArray});
                Object object3 = Array.newInstance(clazz, 1);
                Array.set(object3, 0, object2);
                Object object4 = Utils.newInstance("org.codehaus.groovy.control.CompilerConfiguration", new Object[0]);
                Utils.callMethod(object4, "addCompilationCustomizers", object3);
                ClassLoader classLoader = GroovyCompiler.class.getClassLoader();
                object = Utils.newInstance("groovy.lang.GroovyClassLoader", classLoader, object4);
            }
            catch (Exception exception2) {
                exception = exception2;
            }
            LOADER = object;
            INIT_FAIL_EXCEPTION = exception;
        }
    }
}

