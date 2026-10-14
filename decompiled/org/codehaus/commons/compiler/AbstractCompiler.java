/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.ErrorHandler;
import org.codehaus.commons.compiler.ICompiler;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.compiler.util.Disassembler;
import org.codehaus.commons.compiler.util.StringUtil;
import org.codehaus.commons.compiler.util.SystemProperties;
import org.codehaus.commons.compiler.util.resource.DirectoryResourceCreator;
import org.codehaus.commons.compiler.util.resource.DirectoryResourceFinder;
import org.codehaus.commons.compiler.util.resource.FileResource;
import org.codehaus.commons.compiler.util.resource.PathResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceCreator;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;

public abstract class AbstractCompiler
implements ICompiler {
    private static final boolean disassembleClassFilesToStdout = SystemProperties.getBooleanClassProperty(AbstractCompiler.class, "disassembleClassFilesToStdout");
    private static final Pattern disassembleClassNames = Pattern.compile(SystemProperties.getClassProperty(AbstractCompiler.class, "disassembleClassNames", ".*"));
    protected ResourceFinder sourceFinder = ResourceFinder.EMPTY_RESOURCE_FINDER;
    protected ResourceFinder classFileFinder = ICompiler.FIND_NEXT_TO_SOURCE_FILE;
    protected ResourceCreator classFileCreator = ICompiler.CREATE_NEXT_TO_SOURCE_FILE;
    public Charset sourceCharset = Charset.defaultCharset();
    protected boolean debugSource;
    protected boolean debugLines;
    protected boolean debugVars;
    protected int sourceVersion = -1;
    protected int targetVersion = -1;
    protected File[] extensionDirectories = StringUtil.parsePath(System.getProperty("java.ext.dirs", ""));
    protected File[] classPath = StringUtil.parsePath(System.getProperty("java.class.path"));
    @Nullable
    protected File[] bootClassPath = StringUtil.parseOptionalPath(System.getProperty("sun.boot.class.path"));
    @Nullable
    protected ErrorHandler compileErrorHandler;
    @Nullable
    protected WarningHandler warningHandler;

    @Override
    public void setSourceFinder(ResourceFinder sourceFinder) {
        this.sourceFinder = sourceFinder;
    }

    @Override
    public final void setClassFileFinder(ResourceFinder destination, boolean rebuild) {
        this.setClassFileFinder(rebuild ? ResourceFinder.EMPTY_RESOURCE_FINDER : destination);
    }

    @Override
    public void setClassFileFinder(ResourceFinder classFileFinder) {
        this.classFileFinder = classFileFinder;
    }

    @Override
    public final void setClassFileCreator(ResourceCreator classFileCreator) {
        if (disassembleClassFilesToStdout) {
            final ResourceCreator delegate = classFileCreator;
            classFileCreator = new ResourceCreator(){

                @Override
                public OutputStream createResource(String resourceName) throws IOException {
                    final OutputStream delegateOs = delegate.createResource(resourceName);
                    assert (resourceName.endsWith(".class"));
                    String className = resourceName.substring(0, resourceName.length() - 6).replace('/', '.');
                    if (disassembleClassNames.matcher(className).matches()) {
                        return new ByteArrayOutputStream(){

                            @Override
                            public void close() throws IOException {
                                byte[] ba = this.toByteArray();
                                Disassembler.disassembleToStdout(ba);
                                delegateOs.write(ba);
                                delegateOs.close();
                            }
                        };
                    }
                    return delegateOs;
                }

                @Override
                public boolean deleteResource(String resourceName) {
                    return delegate.deleteResource(resourceName);
                }
            };
        }
        this.classFileCreator = classFileCreator;
    }

    @Override
    public final boolean compile(File[] sourceFiles) throws CompileException, IOException {
        Resource[] sourceFileResources = new Resource[sourceFiles.length];
        for (int i = 0; i < sourceFiles.length; ++i) {
            sourceFileResources[i] = new FileResource(sourceFiles[i]);
        }
        this.compile(sourceFileResources);
        return true;
    }

    @Override
    public final void setEncoding(Charset encoding) {
        this.setSourceCharset(encoding);
    }

    @Override
    public void setSourceCharset(Charset charset) {
        this.sourceCharset = charset;
    }

    @Override
    public final void setCharacterEncoding(@Nullable String characterEncoding2) {
        this.setSourceCharset(characterEncoding2 == null ? Charset.defaultCharset() : Charset.forName(characterEncoding2));
    }

    @Override
    public void setDebugLines(boolean value) {
        this.debugLines = value;
    }

    @Override
    public void setDebugVars(boolean value) {
        this.debugVars = value;
    }

    @Override
    public void setDebugSource(boolean value) {
        this.debugSource = value;
    }

    @Override
    public void setSourceVersion(int version) {
        this.sourceVersion = version;
    }

    @Override
    public void setTargetVersion(int version) {
        this.targetVersion = version;
    }

    @Override
    public void setSourcePath(File[] directoriesAndArchives) {
        this.setSourceFinder(new PathResourceFinder(directoriesAndArchives));
    }

    @Override
    public void setBootClassPath(File[] directoriesAndArchives) {
        this.bootClassPath = directoriesAndArchives;
    }

    @Override
    public void setExtensionDirectories(File[] directories) {
        this.extensionDirectories = directories;
    }

    @Override
    public void setClassPath(File[] directoriesAndArchives) {
        this.classPath = directoriesAndArchives;
    }

    @Override
    public final void setDestinationDirectory(@Nullable File destinationDirectory, boolean rebuild) {
        if (destinationDirectory == ICompiler.NO_DESTINATION_DIRECTORY) {
            this.setClassFileCreator(ICompiler.CREATE_NEXT_TO_SOURCE_FILE);
            this.setClassFileFinder(ICompiler.FIND_NEXT_TO_SOURCE_FILE, rebuild);
        } else {
            assert (destinationDirectory != null);
            this.setClassFileCreator(new DirectoryResourceCreator(destinationDirectory));
            this.setClassFileFinder(new DirectoryResourceFinder(destinationDirectory), rebuild);
        }
    }

    @Override
    public void setCompileErrorHandler(@Nullable ErrorHandler compileErrorHandler) {
        this.compileErrorHandler = compileErrorHandler;
    }

    @Override
    public void setWarningHandler(@Nullable WarningHandler warningHandler) {
        this.warningHandler = warningHandler;
    }
}

