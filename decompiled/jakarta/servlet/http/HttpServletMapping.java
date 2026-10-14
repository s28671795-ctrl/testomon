/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.http.MappingMatch;

public interface HttpServletMapping {
    public String getMatchValue();

    public String getPattern();

    public String getServletName();

    public MappingMatch getMappingMatch();
}

