/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.path;

import org.apache.maven.model.path.UrlNormalizer;
import org.codehaus.plexus.component.annotations.Component;

@Component(role=UrlNormalizer.class)
public class DefaultUrlNormalizer
implements UrlNormalizer {
    @Override
    public String normalize(String url2) {
        String result = url2;
        if (result != null) {
            int idx;
            while ((idx = result.indexOf("/../")) > 0) {
                int parent;
                for (parent = idx - 1; parent >= 0 && result.charAt(parent) == '/'; --parent) {
                }
                if ((parent = result.lastIndexOf(47, parent)) < 0) break;
                result = result.substring(0, parent) + result.substring(idx + 3);
            }
        }
        return result;
    }
}

