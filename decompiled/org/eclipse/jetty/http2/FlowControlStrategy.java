/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2;

import org.eclipse.jetty.http2.ISession;
import org.eclipse.jetty.http2.IStream;
import org.eclipse.jetty.http2.frames.WindowUpdateFrame;

public interface FlowControlStrategy {
    public static final int DEFAULT_WINDOW_SIZE = 65535;

    public void onStreamCreated(IStream var1);

    public void onStreamDestroyed(IStream var1);

    public void updateInitialStreamWindow(ISession var1, int var2, boolean var3);

    public void onWindowUpdate(ISession var1, IStream var2, WindowUpdateFrame var3);

    public void onDataReceived(ISession var1, IStream var2, int var3);

    public void onDataConsumed(ISession var1, IStream var2, int var3);

    public void windowUpdate(ISession var1, IStream var2, WindowUpdateFrame var3);

    public void onDataSending(IStream var1, int var2);

    public void onDataSent(IStream var1, int var2);

    public static interface Factory {
        public FlowControlStrategy newFlowControlStrategy();
    }
}

