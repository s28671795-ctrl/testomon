/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.util.Iterator;
import javax.activation.DataHandler;

public interface Attachment {
    public DataHandler getDataHandler();

    public String getId();

    public String getHeader(String var1);

    public Iterator<String> getHeaderNames();

    public boolean isXOP();
}

