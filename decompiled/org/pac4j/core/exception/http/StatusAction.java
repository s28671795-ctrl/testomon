/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.exception.http.WithContentAction;
import org.pac4j.core.util.CommonHelper;

public class StatusAction
extends HttpAction
implements WithContentAction {
    private static final long serialVersionUID = -1512800910066851787L;
    private String content = "";

    public StatusAction(int code) {
        super(code);
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

