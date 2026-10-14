/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

class EmptyMatcher
implements Matcher {
    EmptyMatcher() {
    }

    @Override
    public Transform match(Class type) throws Exception {
        return null;
    }
}

