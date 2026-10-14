/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import java.io.InputStream;
import net.lingala.zip4j.model.FileHeader;

public abstract class SplitFileInputStream
extends InputStream {
    public abstract void prepareExtractionForFileHeader(FileHeader var1) throws IOException;
}

