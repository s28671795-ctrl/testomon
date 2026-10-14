/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.WithContentAction;
import org.pac4j.core.util.CommonHelper;

public class UnauthorizedAction
extends HttpAction
implements WithContentAction {
    @Deprecated
    public static final UnauthorizedAction INSTANCE = new UnauthorizedAction();
    private static final long serialVersionUID = -7291712846651414978L;
    private String content = "";

    public UnauthorizedAction() {
        super(401);
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

