/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.ElementTypesAreNonnullByDefault;
import com.google.common.io.LineProcessor;
import com.google.common.io.ParametricNullness;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
public final class Resources {
    private Resources() {
    }

    public static ByteSource asByteSource(URL url2) {
        return new UrlByteSource(url2);
    }

    public static CharSource asCharSource(URL url2, Charset charset) {
        return Resources.asByteSource(url2).asCharSource(charset);
    }

    public static byte[] toByteArray(URL url2) throws IOException {
        return Resources.asByteSource(url2).read();
    }

    public static String toString(URL url2, Charset charset) throws IOException {
        return Resources.asCharSource(url2, charset).read();
    }

    @ParametricNullness
    @CanIgnoreReturnValue
    public static <T> T readLines(URL url2, Charset charset, LineProcessor<T> callback) throws IOException {
        return Resources.asCharSource(url2, charset).readLines(callback);
    }

    public static List<String> readLines(URL url2, Charset charset) throws IOException {
        return Resources.readLines(url2, charset, new LineProcessor<List<String>>(){
            final List<String> result = Lists.newArrayList();

            @Override
            public boolean processLine(String line) {
                this.result.add(line);
                return true;
            }

            @Override
            public List<String> getResult() {
                return this.result;
            }
        });
    }

    public static void copy(URL from, OutputStream to) throws IOException {
        Resources.asByteSource(from).copyTo(to);
    }

    @CanIgnoreReturnValue
    public static URL getResource(String resourceName) {
        ClassLoader loader = MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader());
        URL url2 = loader.getResource(resourceName);
        Preconditions.checkArgument(url2 != null, "resource %s not found.", (Object)resourceName);
        return url2;
    }

    @CanIgnoreReturnValue
    public static URL getResource(Class<?> contextClass, String resourceName) {
        URL url2 = contextClass.getResource(resourceName);
        Preconditions.checkArgument(url2 != null, "resource %s relative to %s not found.", (Object)resourceName, (Object)contextClass.getName());
        return url2;
    }

    private static final class UrlByteSource
    extends ByteSource {
        private final URL url;

        private UrlByteSource(URL url2) {
            this.url = Preconditions.checkNotNull(url2);
        }

        @Override
        public InputStream openStream() throws IOException {
            return this.url.openStream();
        }

        public String toString() {
            return "Resources.asByteSource(" + this.url + ")";
        }
    }
}

