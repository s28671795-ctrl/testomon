/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception.http;

import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.exception.http.WithLocationAction;
import org.pac4j.core.util.CommonHelper;

public class FoundAction
extends RedirectionAction
implements WithLocationAction {
    private static final long serialVersionUID = 5155686595276189592L;
    private final String location;

    public FoundAction(String location) {
        super(302);
        this.location = location;
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "code", this.code, "location", this.location);
    }
}

