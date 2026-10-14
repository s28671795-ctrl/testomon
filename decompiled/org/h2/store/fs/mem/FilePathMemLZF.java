/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.mem;

import org.h2.store.fs.mem.FilePathMem;

public class FilePathMemLZF
extends FilePathMem {
    @Override
    public FilePathMem getPath(String string) {
        FilePathMemLZF filePathMemLZF = new FilePathMemLZF();
        filePathMemLZF.name = FilePathMemLZF.getCanonicalPath(string);
        return filePathMemLZF;
    }

    @Override
    boolean compressed() {
        return true;
    }

    @Override
    public String getScheme() {
        return "memLZF";
    }
}

