/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.ErrorHandler;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.compiler.util.resource.ListableResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceCreator;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;

public interface ICompiler {
    @Nullable
    public static final File NO_DESTINATION_DIRECTORY = null;
    public static final ResourceFinder FIND_NEXT_TO_SOURCE_FILE = new ListableResourceFinder(){

        @Override
        @Nullable
        public Resource findResource(String resourceName) {
            throw new UnsupportedOperationException("FIND_NEXT_TO_SOUJRCE_FILE");
        }

        @Override
        @Nullable
        public Iterable<Resource> list(String resourceNamePrefix, boolean recurse) {
            return Collections.emptyList();
        }

        public String toString() {
            return "FIND_NEXT_TO_SOUJRCE_FILE";
        }
    };
    public static final ResourceCreator CREATE_NEXT_TO_SOURCE_FILE = new ResourceCreator(){

        @Override
        public boolean deleteResource(String resourceName) {
            throw new UnsupportedOperationException("CREATE_NEXT_TO_SOURCE_FILE");
        }

        @Override
        public OutputStream createResource(String resourceName) {
            throw new UnsupportedOperationException("CREATE_NEXT_TO_SOURCE_FILE");
        }

        public String toString() {
            return "CREATE_NEXT_TO_SOURCE_FILE";
        }
    };

    public void setEncoding(Charset var1);

    public void setSourceCharset(Charset var1);

    @Deprecated
    public void setCharacterEncoding(@Nullable String var1);

    public void setDebugLines(boolean var1);

    public void setDebugVars(boolean var1);

    public void setDebugSource(boolean var1);

    public void setSourceVersion(int var1);

    public void setTargetVersion(int var1);

    public void setSourceFinder(ResourceFinder var1);

    public void setSourcePath(File[] var1);

    public void setBootClassPath(File[] var1);

    public void setExtensionDirectories(File[] var1);

    public void setClassPath(File[] var1);

    public void setDestinationDirectory(@Nullable File var1, boolean var2);

    public void setClassFileFinder(ResourceFinder var1, boolean var2);

    public void setClassFileFinder(ResourceFinder var1);

    public void setClassFileCreator(ResourceCreator var1);

    public void setVerbose(boolean var1);

    public boolean compile(File[] var1) throws CompileException, IOException;

    public void compile(Resource[] var1) throws CompileException, IOException;

    public void setCompileErrorHandler(@Nullable ErrorHandler var1);

    public void setWarningHandler(WarningHandler var1);
}

