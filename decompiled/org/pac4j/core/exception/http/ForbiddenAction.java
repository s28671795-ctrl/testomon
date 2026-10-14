/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.WithContentAction;
import org.pac4j.core.util.CommonHelper;

public class ForbiddenAction
extends HttpAction
implements WithContentAction {
    @Deprecated
    public static final ForbiddenAction INSTANCE = new ForbiddenAction();
    private static final long serialVersionUID = 6661068865264199225L;
    private String content = "";

    public ForbiddenAction() {
        super(403);
    }

    @Override
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "code", this.code, "content", this.content);
    }
}

