/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.File;
import org.codehaus.commons.compiler.util.resource.FileResourceCreator;

public class DirectoryResourceCreator
extends FileResourceCreator {
    private final File destinationDirectory;

    public DirectoryResourceCreator(File destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    @Override
    protected final File getFile(String resourceName) {
        return new File(this.destinationDirectory, resourceName.replace('/', File.separatorChar));
    }
}

