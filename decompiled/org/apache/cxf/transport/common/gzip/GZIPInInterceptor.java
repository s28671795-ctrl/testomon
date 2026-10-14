/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.common.gzip;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.interceptor.AttachmentInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.transport.common.gzip.GZIPOutInterceptor;

public class GZIPInInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(GZIPInInterceptor.class);
    private static final Logger LOG = LogUtils.getL7dLogger(GZIPInInterceptor.class);

    public GZIPInInterceptor() {
        super("receive");
        this.addBefore(AttachmentInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) {
        if (this.isGET(message)) {
            return;
        }
        Map<String, List<String>> protocolHeaders = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
        if (protocolHeaders != null) {
            List<String> contentEncoding = HttpHeaderHelper.getHeader(protocolHeaders, "Content-Encoding");
            if (contentEncoding == null) {
                contentEncoding = protocolHeaders.get("SOAPJMS_contentEncoding");
            }
            if (contentEncoding != null && (contentEncoding.contains("gzip") || contentEncoding.contains("x-gzip"))) {
                try {
                    LOG.fine("Uncompressing response");
                    InputStream is = message.getContent(InputStream.class);
                    if (is == null) {
                        return;
                    }
                    GZIPInputStream zipInput = new GZIPInputStream(is);
                    message.setContent(InputStream.class, zipInput);
                    for (String key : protocolHeaders.keySet()) {
                        if (!"Content-Encoding".equalsIgnoreCase(key)) continue;
                        protocolHeaders.remove(key);
                        break;
                    }
                    if (this.isRequestor(message)) {
                        Endpoint ep = message.getExchange().getEndpoint();
                        ep.put(GZIPOutInterceptor.USE_GZIP_KEY, GZIPOutInterceptor.UseGzip.YES);
                    }
                }
                catch (IOException ex) {
                    throw new Fault(new org.apache.cxf.common.i18n.Message("COULD_NOT_UNZIP", BUNDLE, new Object[0]), (Throwable)ex);
                }
            }
        }
    }
}

