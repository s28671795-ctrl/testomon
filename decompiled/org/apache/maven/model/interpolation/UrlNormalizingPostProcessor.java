/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.interpolation;

import java.util.HashSet;
import java.util.Set;
import org.apache.maven.model.path.UrlNormalizer;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;

class UrlNormalizingPostProcessor
implements InterpolationPostProcessor {
    private static final Set<String> URL_EXPRESSIONS;
    private UrlNormalizer normalizer;

    UrlNormalizingPostProcessor(UrlNormalizer normalizer) {
        this.normalizer = normalizer;
    }

    @Override
    public Object execute(String expression, Object value) {
        if (value != null && URL_EXPRESSIONS.contains(expression)) {
            return this.normalizer.normalize(value.toString());
        }
        return null;
    }

    static {
        HashSet<String> expressions = new HashSet<String>();
        expressions.add("project.url");
        expressions.add("project.scm.url");
        expressions.add("project.scm.connection");
        expressions.add("project.scm.developerConnection");
        expressions.add("project.distributionManagement.site.url");
        URL_EXPRESSIONS = expressions;
    }
}

