/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.net;

import ch.qos.logback.core.net.AutoFlushingObjectWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectWriterFactory {
    public AutoFlushingObjectWriter newAutoFlushingObjectWriter(OutputStream outputStream2) throws IOException {
        return new AutoFlushingObjectWriter(new ObjectOutputStream(outputStream2), 70);
    }
}

