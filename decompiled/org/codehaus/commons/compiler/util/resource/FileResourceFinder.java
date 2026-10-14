/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.File;
import org.codehaus.commons.compiler.util.resource.FileResource;
import org.codehaus.commons.compiler.util.resource.ListableResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.nullanalysis.Nullable;

public abstract class FileResourceFinder
extends ListableResourceFinder {
    @Override
    @Nullable
    public final Resource findResource(String resourceName) {
        File file = this.findResourceAsFile(resourceName);
        if (file == null) {
            return null;
        }
        return new FileResource(file);
    }

    @Nullable
    protected abstract File findResourceAsFile(String var1);
}

