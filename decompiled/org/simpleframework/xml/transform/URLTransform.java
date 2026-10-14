/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.net.URL;
import org.simpleframework.xml.transform.Transform;

class URLTransform
implements Transform<URL> {
    URLTransform() {
    }

    @Override
    public URL read(String target) throws Exception {
        return new URL(target);
    }

    @Override
    public String write(URL target) throws Exception {
        return target.toString();
    }
}

