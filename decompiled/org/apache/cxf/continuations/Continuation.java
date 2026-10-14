/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.continuations;

public interface Continuation {
    public boolean suspend(long var1);

    public void resume();

    public void reset();

    public boolean isNew();

    public boolean isPending();

    public boolean isResumed();

    public boolean isTimeout();

    public Object getObject();

    public void setObject(Object var1);

    public boolean isReadyForWrite();
}

