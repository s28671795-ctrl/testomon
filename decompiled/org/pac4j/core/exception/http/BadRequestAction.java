/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.WithContentAction;
import org.pac4j.core.util.CommonHelper;

public class BadRequestAction
extends HttpAction
implements WithContentAction {
    @Deprecated
    public static final BadRequestAction INSTANCE = new BadRequestAction();
    private static final long serialVersionUID = 9190468211708168035L;
    private String content = "";

    public BadRequestAction() {
        super(400);
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

