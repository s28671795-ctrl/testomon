/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.MappingMatch;
import org.eclipse.jetty.http.pathmap.MatchedPath;
import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.http.pathmap.ServletPathSpec;

public class ServletPathMapping
implements HttpServletMapping {
    private final MappingMatch _mappingMatch;
    private final String _matchValue;
    private final String _pattern;
    private final String _servletName;
    private final String _servletPath;
    private final String _pathInfo;

    public ServletPathMapping(PathSpec pathSpec, String servletName, String pathInContext, MatchedPath matchedPath) {
        String string = this._servletName = servletName == null ? "" : servletName;
        if (pathSpec == null) {
            this._pattern = null;
            this._mappingMatch = null;
            this._matchValue = "";
            this._servletPath = pathInContext;
            this._pathInfo = null;
            return;
        }
        if (pathInContext == null) {
            this._pattern = pathSpec.getDeclaration();
            this._mappingMatch = null;
            this._matchValue = "";
            this._servletPath = "";
            this._pathInfo = null;
            return;
        }
        if (!(pathSpec instanceof ServletPathSpec)) {
            this._pattern = pathSpec.getDeclaration();
            this._mappingMatch = null;
            if (matchedPath != null) {
                this._servletPath = matchedPath.getPathMatch();
                this._pathInfo = matchedPath.getPathInfo();
            } else {
                this._servletPath = pathInContext;
                this._pathInfo = null;
            }
            this._matchValue = this._servletPath.substring(this._servletPath.charAt(0) == '/' ? 1 : 0);
            return;
        }
        this._pattern = pathSpec.getDeclaration();
        switch (pathSpec.getGroup()) {
            case ROOT: {
                this._mappingMatch = MappingMatch.CONTEXT_ROOT;
                this._matchValue = "";
                this._servletPath = "";
                this._pathInfo = "/";
                break;
            }
            case DEFAULT: {
                this._mappingMatch = MappingMatch.DEFAULT;
                this._matchValue = "";
                this._servletPath = pathInContext;
                this._pathInfo = null;
                break;
            }
            case EXACT: {
                this._mappingMatch = MappingMatch.EXACT;
                this._matchValue = this._pattern.startsWith("/") ? this._pattern.substring(1) : this._pattern;
                this._servletPath = this._pattern;
                this._pathInfo = null;
                break;
            }
            case PREFIX_GLOB: {
                this._mappingMatch = MappingMatch.PATH;
                this._servletPath = pathSpec.getPrefix();
                this._matchValue = this._servletPath.startsWith("/") ? this._servletPath.substring(1) : this._servletPath;
                this._pathInfo = matchedPath != null ? matchedPath.getPathInfo() : null;
                break;
            }
            case SUFFIX_GLOB: {
                this._mappingMatch = MappingMatch.EXTENSION;
                int dot = pathInContext.lastIndexOf(46);
                this._matchValue = pathInContext.substring(pathInContext.startsWith("/") ? 1 : 0, dot);
                this._servletPath = pathInContext;
                this._pathInfo = null;
                break;
            }
            default: {
                throw new IllegalStateException("ServletPathSpec of type MIDDLE_GLOB");
            }
        }
    }

    public ServletPathMapping(PathSpec pathSpec, String servletName, String pathInContext) {
        this(pathSpec, servletName, pathInContext, null);
    }

    @Override
    public String getMatchValue() {
        return this._matchValue;
    }

    @Override
    public String getPattern() {
        return this._pattern;
    }

    @Override
    public String getServletName() {
        return this._servletName;
    }

    @Override
    public MappingMatch getMappingMatch() {
        return this._mappingMatch;
    }

    public String getServletPath() {
        return this._servletPath;
    }

    public String getPathInfo() {
        return this._pathInfo;
    }

    public String toString() {
        return "ServletPathMapping{matchValue=" + this._matchValue + ", pattern=" + this._pattern + ", servletName=" + this._servletName + ", mappingMatch=" + this._mappingMatch + ", servletPath=" + this._servletPath + ", pathInfo=" + this._pathInfo + "}";
    }
}

