/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.continuation;

import java.util.EventListener;
import org.eclipse.jetty.continuation.Continuation;

@Deprecated
public interface ContinuationListener
extends EventListener {
    public void onComplete(Continuation var1);

    public void onTimeout(Continuation var1);
}

