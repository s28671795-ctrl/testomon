/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import org.apache.maven.model.Model;

public interface ModelWriter {
    public void write(File var1, Map<String, Object> var2, Model var3) throws IOException;

    public void write(Writer var1, Map<String, Object> var2, Model var3) throws IOException;

    public void write(OutputStream var1, Map<String, Object> var2, Model var3) throws IOException;
}

