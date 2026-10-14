/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.interpolation;

import java.io.File;
import java.util.Collection;
import java.util.List;
import org.apache.maven.model.path.PathTranslator;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;
import org.codehaus.plexus.interpolation.util.ValueSourceUtils;

class PathTranslatingPostProcessor
implements InterpolationPostProcessor {
    private final Collection<String> unprefixedPathKeys;
    private final File projectDir;
    private final PathTranslator pathTranslator;
    private final List<String> expressionPrefixes;

    PathTranslatingPostProcessor(List<String> expressionPrefixes, Collection<String> unprefixedPathKeys, File projectDir, PathTranslator pathTranslator) {
        this.expressionPrefixes = expressionPrefixes;
        this.unprefixedPathKeys = unprefixedPathKeys;
        this.projectDir = projectDir;
        this.pathTranslator = pathTranslator;
    }

    @Override
    public Object execute(String expression, Object value) {
        if (value != null && this.unprefixedPathKeys.contains(expression = ValueSourceUtils.trimPrefix(expression, this.expressionPrefixes, true))) {
            return this.pathTranslator.alignToBaseDirectory(String.valueOf(value), this.projectDir);
        }
        return null;
    }
}

