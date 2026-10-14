/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelParseException;

public interface ModelReader {
    public static final String IS_STRICT = "org.apache.maven.model.io.isStrict";
    public static final String INPUT_SOURCE = "org.apache.maven.model.io.inputSource";

    public Model read(File var1, Map<String, ?> var2) throws IOException, ModelParseException;

    public Model read(Reader var1, Map<String, ?> var2) throws IOException, ModelParseException;

    public Model read(InputStream var1, Map<String, ?> var2) throws IOException, ModelParseException;
}

