/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="Stats", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class Stats {
    @Element(name="BytesScanned")
    private long bytesScanned = -1L;
    @Element(name="BytesProcessed")
    private long bytesProcessed = -1L;
    @Element(name="BytesReturned")
    private long bytesReturned = -1L;

    public long bytesScanned() {
        return this.bytesScanned;
    }

    public long bytesProcessed() {
        return this.bytesProcessed;
    }

    public long bytesReturned() {
        return this.bytesReturned;
    }
}

