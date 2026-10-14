/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.IOException;
import org.apache.activemq.wireformat.WireFormat;

public interface MarshallAware {
    public void beforeMarshall(WireFormat var1) throws IOException;

    public void afterMarshall(WireFormat var1) throws IOException;

    public void beforeUnmarshall(WireFormat var1) throws IOException;

    public void afterUnmarshall(WireFormat var1) throws IOException;
}

