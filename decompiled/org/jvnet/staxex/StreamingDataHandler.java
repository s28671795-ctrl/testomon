/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.staxex;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.activation.DataHandler;
import javax.activation.DataSource;

public abstract class StreamingDataHandler
extends DataHandler
implements Closeable {
    private String hrefCid;

    public StreamingDataHandler(Object o, String s2) {
        super(o, s2);
    }

    public StreamingDataHandler(URL url2) {
        super(url2);
    }

    public StreamingDataHandler(DataSource dataSource) {
        super(dataSource);
    }

    public abstract InputStream readOnce() throws IOException;

    public abstract void moveTo(File var1) throws IOException;

    public abstract void close() throws IOException;

    public String getHrefCid() {
        return this.hrefCid;
    }

    public void setHrefCid(String cid) {
        this.hrefCid = cid;
    }
}

