/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.exception.http.WithContentAction;
import org.pac4j.core.util.CommonHelper;

public class OkAction
extends RedirectionAction
implements WithContentAction {
    private static final long serialVersionUID = -8842651379112280831L;
    private final String content;

    public OkAction(String content) {
        super(200);
        this.content = content;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "code", this.code, "content", this.content);
    }
}

