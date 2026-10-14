/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.cxf.common.util.StreamPrinter;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.helpers.FileUtils;

public class Compiler {
    private long maxMemory = Runtime.getRuntime().maxMemory();
    private boolean verbose;
    private String target;
    private String outputDir;
    private String classPath;
    private String encoding;
    private boolean forceFork = Boolean.getBoolean(Compiler.class.getName() + "-fork");
    private File classpathTmpFile;
    private List<String> errors = new LinkedList<String>();
    private List<String> warnings = new LinkedList<String>();

    public List<String> getErrors() {
        return this.errors;
    }

    public List<String> getWarnings() {
        return this.warnings;
    }

    public void setMaxMemory(long l) {
        this.maxMemory = l;
    }

    public void setVerbose(boolean b) {
        this.verbose = b;
    }

    public void setTarget(String s2) {
        this.target = s2;
    }

    public void setOutputDir(File s2) {
        this.outputDir = s2 != null ? s2.getAbsolutePath().replace(File.pathSeparatorChar, '/') : null;
    }

    public void setOutputDir(String s2) {
        this.outputDir = s2.replace(File.pathSeparatorChar, '/');
    }

    public void setClassPath(String s2) {
        this.classPath = StringUtils.isEmpty(s2) ? null : s2;
    }

    private String getSystemClassPath() {
        String javaClasspath = SystemPropertyAction.getProperty("java.class.path");
        if (!StringUtils.isEmpty(javaClasspath)) {
            String[] toks;
            ArrayList<String> correctedEntries = new ArrayList<String>();
            for (String tok : toks = javaClasspath.split(File.pathSeparator)) {
                if (tok.matches(".*\\s+.*")) {
                    correctedEntries.add("\"" + tok + "\"");
                    continue;
                }
                correctedEntries.add(tok);
            }
            return String.join((CharSequence)File.pathSeparator, correctedEntries);
        }
        return javaClasspath;
    }

    protected void addArgs(List<String> list) {
        if (!StringUtils.isEmpty(this.encoding)) {
            list.add("-encoding");
            list.add(this.encoding);
        }
        if (!StringUtils.isEmpty(this.target)) {
            list.add("-target");
            list.add(this.target);
            list.add("-source");
            list.add(this.target);
        }
        if (!StringUtils.isEmpty(this.outputDir)) {
            list.add("-d");
            list.add(this.outputDir);
        }
        if (StringUtils.isEmpty(this.classPath)) {
            boolean classpathSetted;
            String javaClasspath = this.getSystemClassPath();
            boolean bl = classpathSetted = !StringUtils.isEmpty(javaClasspath);
            if (!classpathSetted) {
                File f = new File(this.getClass().getClassLoader().getResource(".").getFile());
                if ((f = new File(f, "../lib")).exists() && f.isDirectory()) {
                    list.add("-extdirs");
                    list.add(f.toString());
                }
            } else {
                list.add("-classpath");
                list.add(javaClasspath);
            }
        } else {
            list.add("-classpath");
            list.add(this.classPath);
        }
    }

    public boolean compileFiles(File[] files) {
        ArrayList<String> f = new ArrayList<String>(files.length);
        for (File file : files) {
            f.add(file.getAbsolutePath());
        }
        return this.compileFiles(f.toArray(new String[0]));
    }

    public boolean compileFiles(List<File> files) {
        ArrayList<String> f = new ArrayList<String>(files.size());
        for (File file : files) {
            f.add(file.getAbsolutePath());
        }
        return this.compileFiles(f.toArray(new String[0]));
    }

    public boolean compileFiles(String[] files) {
        String endorsed = SystemPropertyAction.getProperty("java.endorsed.dirs");
        if (!this.forceFork) {
            return this.useJava6Compiler(files);
        }
        ArrayList<String> list = new ArrayList<String>();
        String fsep = File.separator;
        String javacstr = "javac";
        String platformjavacname = "javac";
        if (SystemPropertyAction.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
            platformjavacname = "javac.exe";
        }
        if (new File(SystemPropertyAction.getProperty("java.home") + fsep + platformjavacname).exists()) {
            javacstr = SystemPropertyAction.getProperty("java.home") + fsep + platformjavacname;
        } else if (new File(SystemPropertyAction.getProperty("java.home") + fsep + ".." + fsep + "bin" + fsep + platformjavacname).exists()) {
            javacstr = SystemPropertyAction.getProperty("java.home") + fsep + ".." + fsep + "bin" + fsep + platformjavacname;
        } else if (new File(SystemPropertyAction.getProperty("java.home") + fsep + "bin" + fsep + platformjavacname).exists()) {
            javacstr = SystemPropertyAction.getProperty("java.home") + fsep + "bin" + fsep + platformjavacname;
        }
        list.add(javacstr);
        if (!StringUtils.isEmpty(endorsed)) {
            list.add("-endorseddirs");
            list.add(endorsed);
        }
        list.add("-J-Xmx" + this.maxMemory);
        this.addArgs(list);
        int classpathIdx = list.indexOf("-classpath");
        String classpath = (String)list.get(classpathIdx + 1);
        this.checkLongClasspath(classpath, list, classpathIdx);
        int idx = list.size();
        Collections.addAll(list, files);
        return this.internalCompile(list.toArray(new String[0]), idx);
    }

    protected boolean useJava6Compiler(String[] files) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("No compiler detected, make sure you are running on top of a JDK instead of a JRE.");
        }
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> fileList = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(files));
        return this.internalJava6Compile(compiler, this.wrapJavaFileManager(fileManager), this.setupDiagnosticListener(), fileList);
    }

    protected JavaFileManager wrapJavaFileManager(StandardJavaFileManager standardJavaFileManger) {
        return standardJavaFileManger;
    }

    protected DiagnosticListener<JavaFileObject> setupDiagnosticListener() {
        return new DiagnosticListener<JavaFileObject>(){

            @Override
            public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
                switch (diagnostic.getKind()) {
                    case ERROR: {
                        Compiler.this.errors.add(diagnostic.toString());
                        if (!Compiler.this.verbose) break;
                        System.err.println(diagnostic.toString());
                        break;
                    }
                    case WARNING: 
                    case MANDATORY_WARNING: {
                        Compiler.this.warnings.add(diagnostic.toString());
                        if (!Compiler.this.verbose) break;
                        System.err.println(diagnostic.toString());
                        break;
                    }
                }
            }
        };
    }

    protected boolean internalJava6Compile(JavaCompiler compiler, JavaFileManager fileManager, DiagnosticListener<JavaFileObject> listener, Iterable<? extends JavaFileObject> fileList) {
        ArrayList<String> args2 = new ArrayList<String>();
        this.addArgs(args2);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, listener, args2, null, fileList);
        Boolean ret = task.call();
        try {
            fileManager.close();
        }
        catch (IOException e) {
            System.err.print("[ERROR] IOException during compiling.");
            e.printStackTrace();
        }
        return ret;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean internalCompile(String[] args2, int sourceFileIndex) {
        Process p = null;
        String[] cmdArray = null;
        File tmpFile = null;
        try {
            if (this.isLongCommandLines(args2) && sourceFileIndex >= 0) {
                tmpFile = FileUtils.createTempFile("cxf-compiler", null);
                try (PrintWriter out = new PrintWriter(new FileWriter(tmpFile));){
                    for (int i = sourceFileIndex; i < args2.length; ++i) {
                        if (args2[i].indexOf(32) > -1) {
                            args2[i] = args2[i].replace(File.separatorChar, '/');
                            if (args2[i].indexOf("package-info.java") > -1 && SystemPropertyAction.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
                                out.println('\"' + args2[i].replaceAll("/", "\\\\\\\\") + '\"');
                                continue;
                            }
                            out.println('\"' + args2[i] + '\"');
                            continue;
                        }
                        out.println(args2[i]);
                    }
                    out.flush();
                }
                cmdArray = new String[sourceFileIndex + 1];
                System.arraycopy(args2, 0, cmdArray, 0, sourceFileIndex);
                cmdArray[sourceFileIndex] = "@" + tmpFile;
            } else {
                cmdArray = new String[args2.length];
                System.arraycopy(args2, 0, cmdArray, 0, args2.length);
            }
            if (SystemPropertyAction.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
                for (int i = 0; i < cmdArray.length; ++i) {
                    if (cmdArray[i].indexOf("package-info") != -1) continue;
                    cmdArray[i] = cmdArray[i].replace('\\', '/');
                }
            }
            if ((p = Runtime.getRuntime().exec(cmdArray)).getErrorStream() != null) {
                StreamPrinter errorStreamPrinter = new StreamPrinter(p.getErrorStream(), "", System.out);
                errorStreamPrinter.start();
            }
            if (p.getInputStream() != null) {
                StreamPrinter infoStreamPrinter = new StreamPrinter(p.getInputStream(), "[INFO]", System.out);
                infoStreamPrinter.start();
            }
            boolean infoStreamPrinter = p.waitFor() == 0;
            return infoStreamPrinter;
        }
        catch (SecurityException e) {
            System.err.println("[ERROR] SecurityException during exec() of compiler \"" + args2[0] + "\".");
        }
        catch (InterruptedException e) {
        }
        catch (IOException e) {
            System.err.print("[ERROR] IOException during exec() of compiler \"" + args2[0] + "\"");
            System.err.println(". Check your path environment variable.");
        }
        finally {
            if (tmpFile != null && tmpFile.exists()) {
                FileUtils.delete(tmpFile);
            }
            if (this.classpathTmpFile != null && this.classpathTmpFile.exists()) {
                FileUtils.delete(this.classpathTmpFile);
            }
        }
        return false;
    }

    private boolean isLongCommandLines(String[] args2) {
        StringBuilder strBuffer = new StringBuilder();
        for (int i = 0; i < args2.length; ++i) {
            strBuffer.append(args2[i]);
        }
        return strBuffer.length() > 4096;
    }

    private boolean isLongClasspath(String classpath) {
        return classpath.length() > 2048;
    }

    private void checkLongClasspath(String classpath, List<String> list, int classpathIdx) {
        if (this.isLongClasspath(classpath)) {
            try {
                this.classpathTmpFile = FileUtils.createTempFile("cxf-compiler-classpath", null);
                try (PrintWriter out = new PrintWriter(new FileWriter(this.classpathTmpFile));){
                    out.println(classpath);
                    out.flush();
                }
                list.set(classpathIdx + 1, "@" + this.classpathTmpFile);
            }
            catch (IOException e) {
                System.err.print("[ERROR] can't write long classpath to @argfile");
            }
        }
    }

    public void setEncoding(String string) {
        this.encoding = string;
    }
}

