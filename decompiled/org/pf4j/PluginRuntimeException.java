/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import org.pf4j.util.StringUtils;

public class PluginRuntimeException
extends RuntimeException {
    public PluginRuntimeException() {
    }

    public PluginRuntimeException(String message) {
        super(message);
    }

    public PluginRuntimeException(Throwable cause) {
        super(cause);
    }

    public PluginRuntimeException(Throwable cause, String message, Object ... args2) {
        super(StringUtils.format(message, args2), cause);
    }

    public PluginRuntimeException(String message, Object ... args2) {
        super(StringUtils.format(message, args2));
    }
}

