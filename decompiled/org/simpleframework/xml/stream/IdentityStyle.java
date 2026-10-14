/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Style;

class IdentityStyle
implements Style {
    IdentityStyle() {
    }

    @Override
    public String getAttribute(String name) {
        return name;
    }

    @Override
    public String getElement(String name) {
        return name;
    }
}

