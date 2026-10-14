/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.matching.matcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.matching.matcher.Matcher;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathMatcher
implements Matcher {
    private static final Logger logger = LoggerFactory.getLogger(PathMatcher.class);
    private final Set<String> includedPaths = new HashSet<String>();
    private final Set<String> excludedPaths = new HashSet<String>();
    private final Set<Pattern> excludedPatterns = new HashSet<Pattern>();
    private static boolean warnedRegexp;
    private static boolean warnedInclude;

    public PathMatcher() {
    }

    @Deprecated
    public PathMatcher(String regexpPath) {
        logger.warn("Use `new PathMatcher().excludeRegex(regex)` for clarity instead of `new PathMatcher(regex)`");
        this.excludeRegex(regexpPath);
    }

    public PathMatcher excludePath(String path) {
        PathMatcher.validatePath(path);
        this.excludedPaths.add(path);
        return this;
    }

    public PathMatcher excludePaths(String ... paths) {
        if (paths != null && paths.length > 0) {
            for (String path : paths) {
                this.excludePath(path);
            }
        }
        return this;
    }

    public PathMatcher includePath(String path) {
        this.warnInclude();
        PathMatcher.validatePath(path);
        this.includedPaths.add(path);
        return this;
    }

    public PathMatcher includePaths(String ... paths) {
        if (paths != null && paths.length > 0) {
            for (String path : paths) {
                this.includePath(path);
            }
        }
        return this;
    }

    public PathMatcher excludeBranch(String path) {
        this.warnRegexp();
        PathMatcher.validatePath(path);
        this.excludedPatterns.add(Pattern.compile("^" + path + "(/.*)?$"));
        return this;
    }

    public PathMatcher excludeRegex(String regex2) {
        this.warnRegexp();
        CommonHelper.assertNotBlank("regex", regex2);
        if (!regex2.startsWith("^") || !regex2.endsWith("$")) {
            throw new TechnicalException("Your regular expression: '" + regex2 + "' must start with a ^ and end with a $ to define a full path matching");
        }
        this.excludedPatterns.add(Pattern.compile(regex2));
        return this;
    }

    protected void warnRegexp() {
        if (!warnedRegexp) {
            logger.warn("Be careful when using the 'excludeBranch' or 'excludeRegex' methods. They use regular expressions and their definitions may be error prone. You could exclude more URLs than expected.");
            warnedRegexp = true;
        }
    }

    protected void warnInclude() {
        if (!warnedInclude) {
            logger.warn("Be careful when using the 'includePath' or 'includePaths' methods. The security will only apply on these paths. It could not be secure enough.");
            warnedInclude = true;
        }
    }

    @Override
    public boolean matches(WebContext context, SessionStore sessionStore) {
        return this.matches(context.getPath());
    }

    boolean matches(String requestPath) {
        logger.debug("request path to match: {}", (Object)requestPath);
        if (!this.includedPaths.isEmpty()) {
            for (String path : this.includedPaths) {
                if (requestPath == null || !requestPath.startsWith(path)) continue;
                return true;
            }
            return false;
        }
        if (this.excludedPaths.contains(requestPath)) {
            return false;
        }
        for (Pattern pattern : this.excludedPatterns) {
            if (!pattern.matcher(requestPath).matches()) continue;
            return false;
        }
        return true;
    }

    public Set<String> getExcludedPaths() {
        return this.excludedPaths;
    }

    public Set<Pattern> getExcludedPatterns() {
        return this.excludedPatterns;
    }

    public void setExcludedPaths(Collection<String> paths) {
        this.excludedPaths.clear();
        paths.forEach(this::excludePath);
    }

    public void setExcludedPatterns(Collection<String> regularExpressions) {
        this.excludedPatterns.clear();
        regularExpressions.forEach(this::excludeRegex);
    }

    public void setExcludedPath(String path) {
        this.excludedPaths.clear();
        this.excludePath(path);
    }

    public void setExcludedPattern(String regularExpression) {
        this.excludedPatterns.clear();
        this.excludeRegex(regularExpression);
    }

    private static void validatePath(String path) {
        CommonHelper.assertNotBlank("path", path);
        if (!path.startsWith("/")) {
            throw new TechnicalException("Excluded path must begin with a /");
        }
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "includedPaths", this.includedPaths, "excludedPaths", this.excludedPaths, "excludedPatterns", this.excludedPatterns);
    }
}

