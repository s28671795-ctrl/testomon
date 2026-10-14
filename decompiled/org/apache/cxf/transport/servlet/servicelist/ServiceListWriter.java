/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.servlet.servicelist;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.cxf.transport.AbstractDestination;

public interface ServiceListWriter {
    public String getContentType();

    public void writeServiceList(PrintWriter var1, String var2, AbstractDestination[] var3, AbstractDestination[] var4) throws IOException;
}

