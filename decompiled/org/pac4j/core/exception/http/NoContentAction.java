/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.WithContentAction;

public class NoContentAction
extends HttpAction
implements WithContentAction {
    public static final NoContentAction INSTANCE = new NoContentAction();
    private static final long serialVersionUID = 7441277744489210027L;

    protected NoContentAction() {
        super(204);
    }

    @Override
    public String getContent() {
        return "";
    }
}

