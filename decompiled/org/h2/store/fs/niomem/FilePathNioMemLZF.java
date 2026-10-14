/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.niomem;

import org.h2.store.fs.niomem.FilePathNioMem;

public class FilePathNioMemLZF
extends FilePathNioMem {
    @Override
    boolean compressed() {
        return true;
    }

    @Override
    public FilePathNioMem getPath(String string) {
        if (!string.startsWith(this.getScheme())) {
            throw new IllegalArgumentException(string + " doesn't start with " + this.getScheme());
        }
        int n = string.indexOf(58);
        int n2 = string.lastIndexOf(58);
        FilePathNioMemLZF filePathNioMemLZF = new FilePathNioMemLZF();
        if (n != -1 && n != n2) {
            filePathNioMemLZF.compressLaterCachePercent = Float.parseFloat(string.substring(n + 1, n2));
        }
        filePathNioMemLZF.name = FilePathNioMemLZF.getCanonicalPath(string);
        return filePathNioMemLZF;
    }

    @Override
    protected boolean isRoot() {
        return this.name.lastIndexOf(58) == this.name.length() - 1;
    }

    @Override
    public String getScheme() {
        return "nioMemLZF";
    }
}

