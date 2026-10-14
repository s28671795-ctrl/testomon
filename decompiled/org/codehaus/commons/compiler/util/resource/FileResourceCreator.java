/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.codehaus.commons.compiler.util.resource.ResourceCreator;

public abstract class FileResourceCreator
implements ResourceCreator {
    @Override
    public final OutputStream createResource(String resourceName) throws IOException {
        File file = this.getFile(resourceName);
        File dir = file.getParentFile();
        if (dir != null && !dir.isDirectory() && !dir.mkdirs()) {
            throw new IOException("Cannot create directory for class file \"" + file + "\"");
        }
        return new FileOutputStream(file);
    }

    @Override
    public final boolean deleteResource(String resourceName) {
        return this.getFile(resourceName).delete();
    }

    protected abstract File getFile(String var1);
}

