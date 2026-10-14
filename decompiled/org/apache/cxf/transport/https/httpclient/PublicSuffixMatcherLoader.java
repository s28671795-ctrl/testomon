/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https.httpclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.transport.https.httpclient.PublicSuffixList;
import org.apache.cxf.transport.https.httpclient.PublicSuffixListParser;
import org.apache.cxf.transport.https.httpclient.PublicSuffixMatcher;

public final class PublicSuffixMatcherLoader {
    private static final Logger LOG = LogUtils.getL7dLogger(PublicSuffixMatcherLoader.class);
    private static volatile PublicSuffixMatcher defaultInstance;

    private PublicSuffixMatcherLoader() {
    }

    private static PublicSuffixMatcher load(InputStream in) throws IOException {
        List<PublicSuffixList> lists = new PublicSuffixListParser().parseByType(new InputStreamReader(in, StandardCharsets.UTF_8));
        return new PublicSuffixMatcher(lists);
    }

    public static PublicSuffixMatcher load(URL url2) throws IOException {
        if (url2 == null) {
            throw new IllegalArgumentException("URL is null");
        }
        try (InputStream in = url2.openStream();){
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(in);
            return publicSuffixMatcher;
        }
    }

    public static PublicSuffixMatcher load(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File is null");
        }
        try (InputStream in = Files.newInputStream(file.toPath(), new OpenOption[0]);){
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(in);
            return publicSuffixMatcher;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static PublicSuffixMatcher getDefault() {
        if (defaultInstance != null) return defaultInstance;
        Class<PublicSuffixMatcherLoader> clazz = PublicSuffixMatcherLoader.class;
        synchronized (PublicSuffixMatcherLoader.class) {
            if (defaultInstance != null) return defaultInstance;
            URL url2 = PublicSuffixMatcherLoader.class.getResource("/mozilla/public-suffix-list.txt");
            if (url2 != null) {
                try {
                    defaultInstance = PublicSuffixMatcherLoader.load(url2);
                }
                catch (IOException ex) {
                    if (!LOG.isLoggable(Level.WARNING)) return defaultInstance;
                    LOG.log(Level.WARNING, "Failure loading public suffix list from default resource", ex);
                }
            } else {
                defaultInstance = new PublicSuffixMatcher(Arrays.asList("com"), null);
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return defaultInstance;
        }
    }
}

