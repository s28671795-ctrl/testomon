/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.filter;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.internal.util.Tokenizer;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.LocalizationMessages;

@PreMatching
@Priority(value=3050)
public final class HttpMethodOverrideFilter
implements ContainerRequestFilter {
    final int config;

    public static void enableFor(ResourceConfig rc, Source ... sources) {
        rc.registerClasses(HttpMethodOverrideFilter.class);
        rc.property("jersey.config.server.httpMethodOverride", sources);
    }

    public HttpMethodOverrideFilter(@Context Configuration rc) {
        this(HttpMethodOverrideFilter.parseConfig(rc.getProperty("jersey.config.server.httpMethodOverride")));
    }

    public HttpMethodOverrideFilter(Source ... sources) {
        int c = 0;
        for (Source cf : sources) {
            if (cf == null) continue;
            c |= cf.getFlag();
        }
        if (c == 0) {
            c = 3;
        }
        this.config = c;
    }

    private static Source[] parseConfig(Object config) {
        String[] stringValues;
        if (config == null) {
            return new Source[0];
        }
        if (config instanceof Source[]) {
            return (Source[])config;
        }
        if (config instanceof Source) {
            return new Source[]{(Source)((Object)config)};
        }
        if (config instanceof String) {
            stringValues = Tokenizer.tokenize((String)config, " ,;\n");
        } else if (config instanceof String[]) {
            stringValues = Tokenizer.tokenize((String[])config, " ,;\n");
        } else {
            return new Source[0];
        }
        Source[] result = new Source[stringValues.length];
        for (int i = 0; i < stringValues.length; ++i) {
            try {
                result[i] = Source.valueOf(stringValues[i]);
                continue;
            }
            catch (IllegalArgumentException e) {
                Logger.getLogger(HttpMethodOverrideFilter.class.getName()).log(Level.WARNING, LocalizationMessages.INVALID_CONFIG_PROPERTY_VALUE("jersey.config.server.httpMethodOverride", stringValues[i]));
            }
        }
        return result;
    }

    private String getParamValue(Source source2, MultivaluedMap<String, String> paramsMap, String paramName) {
        String value;
        String string = value = source2.isPresentIn(this.config) ? paramsMap.getFirst(paramName) : null;
        if (value == null) {
            return null;
        }
        return (value = value.trim()).length() == 0 ? null : value.toUpperCase(Locale.ROOT);
    }

    @Override
    public void filter(ContainerRequestContext request) {
        String override;
        if (!request.getMethod().equalsIgnoreCase("POST")) {
            return;
        }
        String header = this.getParamValue(Source.HEADER, request.getHeaders(), "X-HTTP-Method-Override");
        String query = this.getParamValue(Source.QUERY, request.getUriInfo().getQueryParameters(), "_method");
        if (header == null) {
            override = query;
        } else {
            override = header;
            if (query != null && !query.equals(header)) {
                throw new BadRequestException();
            }
        }
        if (override != null) {
            request.setMethod(override);
            if (override.equals("GET") && request.getMediaType() != null && MediaType.APPLICATION_FORM_URLENCODED_TYPE.getType().equals(request.getMediaType().getType())) {
                UriBuilder ub = request.getUriInfo().getRequestUriBuilder();
                Form f = ((ContainerRequest)request).readEntity(Form.class);
                for (Map.Entry param2 : f.asMap().entrySet()) {
                    ub.queryParam((String)param2.getKey(), ((List)param2.getValue()).toArray());
                }
                request.setRequestUri(request.getUriInfo().getBaseUri(), ub.build(new Object[0]));
            }
        }
    }

    public static enum Source {
        HEADER(1),
        QUERY(2);

        private final int flag;

        private Source(int flag) {
            this.flag = flag;
        }

        public int getFlag() {
            return this.flag;
        }

        public boolean isPresentIn(int config) {
            return (config & this.flag) == this.flag;
        }
    }
}

