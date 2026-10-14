/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.codehaus.commons.compiler.util.resource.LocatableResource;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;

public class ZipFileResourceFinder
extends ResourceFinder {
    private final ZipFile zipFile;

    public ZipFileResourceFinder(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    public final String toString() {
        return "zip:" + this.zipFile.getName();
    }

    @Override
    @Nullable
    public final Resource findResource(final String resourceName) {
        final ZipEntry ze = this.zipFile.getEntry(resourceName);
        if (ze == null) {
            return null;
        }
        return new LocatableResource(){

            @Override
            public URL getLocation() throws IOException {
                return new URL("jar", null, "file:" + ZipFileResourceFinder.this.zipFile.getName() + "!" + resourceName);
            }

            @Override
            public InputStream open() throws IOException {
                return ZipFileResourceFinder.this.zipFile.getInputStream(ze);
            }

            @Override
            public String getFileName() {
                return ZipFileResourceFinder.this.zipFile.getName() + ':' + resourceName;
            }

            @Override
            public long lastModified() {
                long l = ze.getTime();
                return l == -1L ? 0L : l;
            }

            public String toString() {
                return this.getFileName();
            }
        };
    }
}

