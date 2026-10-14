/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api;

public interface WriteCallback {
    public static final WriteCallback NOOP = new WriteCallback(){};

    default public void writeFailed(Throwable x) {
    }

    default public void writeSuccess() {
    }

    @Deprecated
    public static class Adaptor
    implements WriteCallback {
        @Override
        public void writeFailed(Throwable x) {
        }

        @Override
        public void writeSuccess() {
        }
    }
}

