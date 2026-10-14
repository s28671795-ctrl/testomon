/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.http.HttpAction;

public abstract class RedirectionAction
extends HttpAction {
    private static final long serialVersionUID = -4985071484085124623L;

    protected RedirectionAction(int code) {
        super(code);
    }
}

