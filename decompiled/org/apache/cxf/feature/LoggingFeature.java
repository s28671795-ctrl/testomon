/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature;

import org.apache.cxf.Bus;
import org.apache.cxf.annotations.Logging;
import org.apache.cxf.annotations.Provider;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

@NoJSR250Annotations
@Deprecated
@Provider(value=Provider.Type.Feature)
public class LoggingFeature
extends DelegatingFeature<Portable> {
    public LoggingFeature() {
        super(new Portable());
    }

    public LoggingFeature(int lim) {
        super(new Portable(lim));
    }

    public LoggingFeature(String in, String out) {
        super(new Portable(in, out));
    }

    public LoggingFeature(String in, String out, int lim) {
        super(new Portable(in, out, lim));
    }

    public LoggingFeature(String in, String out, int lim, boolean p) {
        super(new Portable(in, out, lim, p));
    }

    public LoggingFeature(String in, String out, int lim, boolean p, boolean showBinary) {
        super(new Portable(in, out, lim, p, showBinary));
    }

    public LoggingFeature(Logging annotation) {
        super(new Portable(annotation));
    }

    public void setLimit(int lim) {
        ((Portable)this.delegate).setLimit(lim);
    }

    public int getLimit() {
        return ((Portable)this.delegate).getLimit();
    }

    public boolean isPrettyLogging() {
        return ((Portable)this.delegate).isPrettyLogging();
    }

    public void setPrettyLogging(boolean prettyLogging) {
        ((Portable)this.delegate).setPrettyLogging(prettyLogging);
    }

    public static class Portable
    implements AbstractPortableFeature {
        private static final int DEFAULT_LIMIT = 49152;
        private static final LoggingInInterceptor IN = new LoggingInInterceptor(49152);
        private static final LoggingOutInterceptor OUT = new LoggingOutInterceptor(49152);
        String inLocation;
        String outLocation;
        boolean prettyLogging;
        boolean showBinary;
        int limit = 49152;

        public Portable() {
        }

        public Portable(int lim) {
            this.limit = lim;
        }

        public Portable(String in, String out) {
            this.inLocation = in;
            this.outLocation = out;
        }

        public Portable(String in, String out, int lim) {
            this.inLocation = in;
            this.outLocation = out;
            this.limit = lim;
        }

        public Portable(String in, String out, int lim, boolean p) {
            this.inLocation = in;
            this.outLocation = out;
            this.limit = lim;
            this.prettyLogging = p;
        }

        public Portable(String in, String out, int lim, boolean p, boolean showBinary) {
            this(in, out, lim, p);
            this.showBinary = showBinary;
        }

        public Portable(Logging annotation) {
            this.inLocation = annotation.inLocation();
            this.outLocation = annotation.outLocation();
            this.limit = annotation.limit();
            this.prettyLogging = annotation.pretty();
            this.showBinary = annotation.showBinary();
        }

        @Override
        public void doInitializeProvider(InterceptorProvider provider, Bus bus) {
            if (this.limit == 49152 && this.inLocation == null && this.outLocation == null && !this.prettyLogging) {
                provider.getInInterceptors().add(IN);
                provider.getInFaultInterceptors().add(IN);
                provider.getOutInterceptors().add(OUT);
                provider.getOutFaultInterceptors().add(OUT);
            } else {
                LoggingInInterceptor in = new LoggingInInterceptor(this.limit);
                in.setOutputLocation(this.inLocation);
                in.setPrettyLogging(this.prettyLogging);
                in.setShowBinaryContent(this.showBinary);
                LoggingOutInterceptor out = new LoggingOutInterceptor(this.limit);
                out.setOutputLocation(this.outLocation);
                out.setPrettyLogging(this.prettyLogging);
                out.setShowBinaryContent(this.showBinary);
                provider.getInInterceptors().add(in);
                provider.getInFaultInterceptors().add(in);
                provider.getOutInterceptors().add(out);
                provider.getOutFaultInterceptors().add(out);
            }
        }

        public void setLimit(int lim) {
            this.limit = lim;
        }

        public int getLimit() {
            return this.limit;
        }

        public boolean isPrettyLogging() {
            return this.prettyLogging;
        }

        public void setPrettyLogging(boolean prettyLogging) {
            this.prettyLogging = prettyLogging;
        }
    }
}

