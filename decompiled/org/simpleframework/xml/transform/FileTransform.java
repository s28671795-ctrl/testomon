/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.io.File;
import org.simpleframework.xml.transform.Transform;

class FileTransform
implements Transform<File> {
    FileTransform() {
    }

    @Override
    public File read(String path) {
        return new File(path);
    }

    @Override
    public String write(File path) {
        return path.getPath();
    }
}

