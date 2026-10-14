/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.filter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.spi.ContentEncoder;

@Priority(value=3000)
public final class EncodingFilter
implements ContainerResponseFilter {
    private static final String IDENTITY_ENCODING = "identity";
    @Inject
    private InjectionManager injectionManager;
    private volatile SortedSet<String> supportedEncodings = null;

    @SafeVarargs
    public static void enableFor(ResourceConfig rc, Class<? extends ContentEncoder> ... encoders) {
        rc.registerClasses(encoders).registerClasses(EncodingFilter.class);
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        if (!response.hasEntity()) {
            return;
        }
        List varyHeader = (List)response.getStringHeaders().get("Vary");
        if (varyHeader == null || !varyHeader.contains("Accept-Encoding")) {
            response.getHeaders().add("Vary", "Accept-Encoding");
        }
        if (response.getHeaders().getFirst("Content-Encoding") != null) {
            return;
        }
        List acceptEncoding = (List)request.getHeaders().get("Accept-Encoding");
        if (acceptEncoding == null || acceptEncoding.isEmpty()) {
            return;
        }
        ArrayList<ContentEncoding> encodings = new ArrayList<ContentEncoding>();
        for (String input : acceptEncoding) {
            if (input.isEmpty()) continue;
            String[] tokens = input.split(",");
            for (String token : tokens) {
                try {
                    ContentEncoding encoding = ContentEncoding.fromString(token);
                    encodings.add(encoding);
                }
                catch (ParseException e) {
                    Logger.getLogger(EncodingFilter.class.getName()).log(Level.WARNING, e.getLocalizedMessage(), e);
                }
            }
        }
        Collections.sort(encodings);
        encodings.add(new ContentEncoding(IDENTITY_ENCODING, -1));
        TreeSet<String> acceptedEncodings = new TreeSet<String>(this.getSupportedEncodings());
        boolean anyRemaining = false;
        String contentEncoding = null;
        for (ContentEncoding encoding : encodings) {
            if (encoding.q == 0) {
                if ("*".equals(encoding.name)) break;
                acceptedEncodings.remove(encoding.name);
                continue;
            }
            if ("*".equals(encoding.name)) {
                anyRemaining = true;
                continue;
            }
            if (!acceptedEncodings.contains(encoding.name)) continue;
            contentEncoding = encoding.name;
            break;
        }
        if (contentEncoding == null) {
            if (anyRemaining && !acceptedEncodings.isEmpty()) {
                contentEncoding = (String)acceptedEncodings.first();
            } else {
                throw new NotAcceptableException();
            }
        }
        if (!IDENTITY_ENCODING.equals(contentEncoding)) {
            response.getHeaders().putSingle("Content-Encoding", contentEncoding);
        }
    }

    SortedSet<String> getSupportedEncodings() {
        if (this.supportedEncodings == null) {
            TreeSet<String> se = new TreeSet<String>();
            List encoders = this.injectionManager.getAllInstances((Type)((Object)ContentEncoder.class));
            for (ContentEncoder encoder : encoders) {
                se.addAll(encoder.getSupportedEncodings());
            }
            se.add(IDENTITY_ENCODING);
            this.supportedEncodings = se;
        }
        return this.supportedEncodings;
    }

    private static class ContentEncoding
    implements Comparable<ContentEncoding> {
        public final String name;
        public final int q;

        public ContentEncoding(String encoding, int q) {
            this.name = encoding;
            this.q = q;
        }

        public static ContentEncoding fromString(String input) throws ParseException {
            HttpHeaderReader reader = HttpHeaderReader.newInstance(input);
            reader.hasNext();
            return new ContentEncoding(reader.nextToken().toString(), HttpHeaderReader.readQualityFactorParameter(reader));
        }

        public int hashCode() {
            return 41 * this.name.hashCode() + this.q;
        }

        public boolean equals(Object obj) {
            return obj == this || obj != null && obj instanceof ContentEncoding && this.name.equals(((ContentEncoding)obj).name) && this.q == ((ContentEncoding)obj).q;
        }

        @Override
        public int compareTo(ContentEncoding o) {
            return Integer.compare(o.q, this.q);
        }
    }
}

