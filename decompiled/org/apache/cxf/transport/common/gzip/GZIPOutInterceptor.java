/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.common.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.MessageSenderInterceptor;
import org.apache.cxf.io.AbstractThresholdOutputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class GZIPOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final Pattern ZERO_Q = Pattern.compile(";\\s*q=0(?:\\.0+)?$");
    public static final Pattern ENCODINGS = Pattern.compile("[,\\s]*,\\s*");
    public static final String ORIGINAL_OUTPUT_STREAM_KEY = GZIPOutInterceptor.class.getName() + ".originalOutputStream";
    public static final String USE_GZIP_KEY = GZIPOutInterceptor.class.getName() + ".useGzip";
    public static final String GZIP_ENCODING_KEY = GZIPOutInterceptor.class.getName() + ".gzipEncoding";
    public static final String SOAP_JMS_CONTENTENCODING = "SOAPJMS_contentEncoding";
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(GZIPOutInterceptor.class);
    private static final Logger LOG = LogUtils.getL7dLogger(GZIPOutInterceptor.class);
    private int threshold = 1024;
    private boolean force;
    private Set<String> supportedPayloadContentTypes;

    public GZIPOutInterceptor() {
        super("prepare-send");
        this.addAfter(MessageSenderInterceptor.class.getName());
    }

    public GZIPOutInterceptor(int threshold) {
        super("prepare-send");
        this.addAfter(MessageSenderInterceptor.class.getName());
        this.threshold = threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return this.threshold;
    }

    @Override
    public void handleMessage(Message message) {
        UseGzip use = this.gzipPermitted(message);
        if (use != UseGzip.NO) {
            OutputStream os = message.getContent(OutputStream.class);
            if (os == null) {
                return;
            }
            message.put(ORIGINAL_OUTPUT_STREAM_KEY, os);
            message.put(USE_GZIP_KEY, use);
            GZipThresholdOutputStream cs = new GZipThresholdOutputStream(this.threshold, os, use == UseGzip.FORCE, message);
            message.setContent(OutputStream.class, cs);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public UseGzip gzipPermitted(Message message) {
        UseGzip permitted = UseGzip.NO;
        if (this.supportedPayloadContentTypes != null && message.containsKey("Content-Type") && !this.supportedPayloadContentTypes.contains(message.get("Content-Type"))) {
            return permitted;
        }
        if (MessageUtils.isRequestor(message)) {
            LOG.fine("Requestor role, so gzip enabled");
            Object o = message.getContextualProperty(USE_GZIP_KEY);
            permitted = o instanceof UseGzip ? (UseGzip)((Object)o) : (o instanceof String ? UseGzip.valueOf((String)o) : (this.force ? UseGzip.YES : UseGzip.NO));
            message.put(GZIP_ENCODING_KEY, "gzip");
            GZIPOutInterceptor.addHeader(message, "Accept-Encoding", "gzip;q=1.0, identity; q=0.5, *;q=0");
        } else {
            LOG.fine("Response role, checking accept-encoding");
            Exchange exchange = message.getExchange();
            Message request = exchange.getInMessage();
            Map<String, List<String>> requestHeaders = CastUtils.cast((Map)request.get(Message.PROTOCOL_HEADERS));
            if (requestHeaders != null) {
                List<String> acceptEncodingHeader = CastUtils.cast(HttpHeaderHelper.getHeader(requestHeaders, "Accept-Encoding"));
                List jmsEncodingHeader = CastUtils.cast(requestHeaders.get(SOAP_JMS_CONTENTENCODING));
                if (jmsEncodingHeader != null && jmsEncodingHeader.contains("gzip")) {
                    permitted = UseGzip.YES;
                    message.put(GZIP_ENCODING_KEY, "gzip");
                }
                if (acceptEncodingHeader != null) {
                    boolean xGzipEnabled;
                    if (LOG.isLoggable(Level.FINE)) {
                        LOG.fine("Accept-Encoding header: " + acceptEncodingHeader);
                    }
                    ArrayList<String> zeros = new ArrayList<String>(3);
                    ArrayList<String> nonZeros = new ArrayList<String>(3);
                    for (String headerLine : acceptEncodingHeader) {
                        String[] encodings;
                        for (String enc : encodings = ENCODINGS.split(headerLine.trim())) {
                            Matcher m4 = ZERO_Q.matcher(enc);
                            if (m4.find()) {
                                zeros.add(enc.substring(0, m4.start()));
                                continue;
                            }
                            if (enc.indexOf(59) >= 0) {
                                nonZeros.add(enc.substring(0, enc.indexOf(59)));
                                continue;
                            }
                            nonZeros.add(enc);
                        }
                    }
                    boolean identityEnabled = !zeros.contains("identity") && (!zeros.contains("*") || nonZeros.contains("identity"));
                    boolean gzipEnabled = nonZeros.contains("gzip") || nonZeros.contains("*") && !zeros.contains("gzip");
                    boolean bl = xGzipEnabled = nonZeros.contains("x-gzip") || nonZeros.contains("*") && !zeros.contains("x-gzip");
                    if (identityEnabled && !gzipEnabled && !xGzipEnabled) {
                        permitted = UseGzip.NO;
                    } else if (identityEnabled && gzipEnabled) {
                        permitted = UseGzip.YES;
                        message.put(GZIP_ENCODING_KEY, "gzip");
                    } else if (identityEnabled && xGzipEnabled) {
                        permitted = UseGzip.YES;
                        message.put(GZIP_ENCODING_KEY, "x-gzip");
                    } else if (!identityEnabled && gzipEnabled) {
                        permitted = UseGzip.FORCE;
                        message.put(GZIP_ENCODING_KEY, "gzip");
                    } else {
                        if (identityEnabled || !xGzipEnabled) throw new Fault(new org.apache.cxf.common.i18n.Message("NO_SUPPORTED_ENCODING", BUNDLE, new Object[0]));
                        permitted = UseGzip.FORCE;
                        message.put(GZIP_ENCODING_KEY, "x-gzip");
                    }
                } else {
                    LOG.fine("No accept-encoding header");
                }
            }
        }
        if (!LOG.isLoggable(Level.FINE)) return permitted;
        LOG.fine("gzip permitted: " + (Object)((Object)permitted));
        return permitted;
    }

    private static void addHeader(Message message, String name, String value) {
        List header;
        Map protocolHeaders = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
        if (protocolHeaders == null) {
            protocolHeaders = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            message.put(Message.PROTOCOL_HEADERS, protocolHeaders);
        }
        if ((header = CastUtils.cast((List)protocolHeaders.get(name))) == null) {
            header = new ArrayList();
            protocolHeaders.put(name, header);
        }
        if (header.isEmpty()) {
            header.add(value);
        } else {
            header.set(0, (String)header.get(0) + "," + value);
        }
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public Set<String> getSupportedPayloadContentTypes() {
        return this.supportedPayloadContentTypes;
    }

    public void setSupportedPayloadContentTypes(Set<String> supportedPayloadContentTypes) {
        this.supportedPayloadContentTypes = supportedPayloadContentTypes;
    }

    static class GZipThresholdOutputStream
    extends AbstractThresholdOutputStream {
        Message message;

        GZipThresholdOutputStream(int t2, OutputStream orig, boolean force, Message msg) {
            super(t2);
            this.wrappedStream = orig;
            this.message = msg;
            if (force) {
                this.setupGZip();
            }
        }

        private void setupGZip() {
        }

        @Override
        public void thresholdNotReached() {
            LOG.fine("Message is smaller than compression threshold, not compressing.");
        }

        @Override
        public void thresholdReached() throws IOException {
            LOG.fine("Compressing message.");
            String enc = (String)this.message.get(GZIP_ENCODING_KEY);
            GZIPOutInterceptor.addHeader(this.message, "Content-Encoding", enc);
            if (!Boolean.TRUE.equals(this.message.get("org.apache.cxf.client"))) {
                GZIPOutInterceptor.addHeader(this.message, "Vary", "Accept-Encoding");
            }
            GZIPOutputStream zipOutput = new GZIPOutputStream(this.wrappedStream);
            this.wrappedStream = zipOutput;
        }
    }

    public static enum UseGzip {
        NO,
        YES,
        FORCE;

    }
}

