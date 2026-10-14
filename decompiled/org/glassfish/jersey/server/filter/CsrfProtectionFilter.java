/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Priority;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

@Priority(value=1000)
public class CsrfProtectionFilter
implements ContainerRequestFilter {
    public static final String HEADER_NAME = "X-Requested-By";
    private static final Set<String> METHODS_TO_IGNORE;

    @Override
    public void filter(ContainerRequestContext rc) throws IOException {
        if (!METHODS_TO_IGNORE.contains(rc.getMethod()) && !rc.getHeaders().containsKey(HEADER_NAME)) {
            throw new BadRequestException();
        }
    }

    static {
        HashSet<String> mti = new HashSet<String>();
        mti.add("GET");
        mti.add("OPTIONS");
        mti.add("HEAD");
        METHODS_TO_IGNORE = Collections.unmodifiableSet(mti);
    }
}

