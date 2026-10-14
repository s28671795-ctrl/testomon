/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.taskdefs.compilers.DefaultCompilerAdapter
 *  org.apache.tools.ant.types.Path
 */
package org.codehaus.janino;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.tools.ant.taskdefs.compilers.DefaultCompilerAdapter;
import org.apache.tools.ant.types.Path;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Compiler;

public class AntCompilerAdapter
extends DefaultCompilerAdapter {
    public boolean execute() {
        boolean debugVars;
        boolean debugLines;
        boolean debugSource;
        File[] sourceFiles = this.compileList;
        File destinationDirectory = this.destDir;
        File[] sourcePath = AntCompilerAdapter.pathToFiles(this.compileSourcepath != null ? this.compileSourcepath : this.src);
        File[] classPath = AntCompilerAdapter.pathToFiles(this.compileClasspath, new File[]{new File(".")});
        File[] extDirs = AntCompilerAdapter.pathToFiles(this.extdirs);
        File[] bootClassPath = AntCompilerAdapter.pathToFiles(this.bootclasspath);
        Charset encoding2 = Charset.forName(this.encoding);
        boolean verbose = this.verbose;
        if (!this.debug) {
            debugSource = false;
            debugLines = false;
            debugVars = false;
        } else {
            String debugLevel = this.attributes.getDebugLevel();
            if (debugLevel == null) {
                debugSource = true;
                debugLines = true;
                debugVars = false;
            } else {
                debugSource = debugLevel.contains("source");
                debugLines = debugLevel.contains("lines");
                debugVars = debugLevel.contains("vars");
            }
        }
        try {
            Compiler compiler = new Compiler();
            compiler.setSourcePath(sourcePath);
            compiler.setClassPath(classPath);
            compiler.setExtensionDirectories(extDirs);
            compiler.setBootClassPath(bootClassPath);
            compiler.setDestinationDirectory(destinationDirectory, false);
            compiler.setEncoding(encoding2);
            compiler.setVerbose(verbose);
            compiler.setDebugSource(debugSource);
            compiler.setDebugLines(debugLines);
            compiler.setDebugVars(debugVars);
            compiler.compile(sourceFiles);
        }
        catch (CompileException e) {
            System.out.println(e.getMessage());
            return false;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static File[] pathToFiles(@Nullable Path path) {
        if (path == null) {
            return new File[0];
        }
        String[] fileNames = path.list();
        File[] files = new File[fileNames.length];
        for (int i = 0; i < fileNames.length; ++i) {
            files[i] = new File(fileNames[i]);
        }
        return files;
    }

    private static File[] pathToFiles(@Nullable Path path, File[] defaultValue) {
        if (path == null) {
            return defaultValue;
        }
        File[] result = AntCompilerAdapter.pathToFiles(path);
        assert (result != null);
        return result;
    }
}

