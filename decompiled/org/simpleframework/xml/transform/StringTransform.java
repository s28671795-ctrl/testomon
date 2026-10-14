/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Transform;

class StringTransform
implements Transform<String> {
    StringTransform() {
    }

    @Override
    public String read(String value) {
        return value;
    }

    @Override
    public String write(String value) {
        return value;
    }
}

