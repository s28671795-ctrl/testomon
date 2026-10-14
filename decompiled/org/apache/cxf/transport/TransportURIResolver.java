/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.resource.ExtendedURIResolver;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.MessageObserver;
import org.xml.sax.InputSource;

public class TransportURIResolver
extends ExtendedURIResolver {
    static final Logger LOG = LogUtils.getL7dLogger(TransportURIResolver.class);
    private static final Set<String> DEFAULT_URI_RESOLVER_HANDLES = new HashSet<String>();
    protected Bus bus;

    public TransportURIResolver(Bus b) {
        this.bus = b;
    }

    @Override
    public InputSource resolve(String curUri, String baseUri) {
        URI base;
        curUri = curUri.replace(" ", "%20");
        InputSource is = null;
        try {
            if (baseUri == null) {
                base = new URI(curUri);
            } else {
                base = new URI(baseUri);
                base = base.resolve(curUri);
            }
        }
        catch (URISyntaxException use) {
            base = null;
            LOG.log(Level.FINEST, "Could not resolve curUri " + curUri, use);
        }
        try {
            if (base == null || DEFAULT_URI_RESOLVER_HANDLES.contains(base.getScheme())) {
                is = super.resolve(curUri, baseUri);
            }
        }
        catch (Exception ex) {
            LOG.log(Level.FINEST, "Default URI handlers could not resolve " + baseUri + " " + curUri, ex);
        }
        if (is == null && base != null && base.getScheme() != null && !DEFAULT_URI_RESOLVER_HANDLES.contains(base.getScheme())) {
            try {
                ConduitInitiatorManager mgr = this.bus.getExtension(ConduitInitiatorManager.class);
                ConduitInitiator ci = null;
                if ("http".equals(base.getScheme()) || "https".equals(base.getScheme())) {
                    ci = mgr.getConduitInitiator("http://cxf.apache.org/transports/http");
                }
                if (ci == null) {
                    ci = mgr.getConduitInitiatorForUri(base.toString());
                }
                if (ci != null) {
                    EndpointInfo info = new EndpointInfo();
                    info.setName(new QName("http://cxf.apache.org", "TransportURIResolver"));
                    info.setAddress(base.toString());
                    final Conduit c = ci.getConduit(info, this.bus);
                    MessageImpl message = new MessageImpl();
                    ExchangeImpl exch = new ExchangeImpl();
                    message.setExchange(exch);
                    message.put("org.apache.cxf.request.method", "GET");
                    c.setMessageObserver(new MessageObserver(){

                        @Override
                        public void onMessage(Message message) {
                            LoadingByteArrayOutputStream bout = new LoadingByteArrayOutputStream();
                            try {
                                IOUtils.copy(message.getContent(InputStream.class), bout);
                                message.getExchange().put(InputStream.class, bout.createInputStream());
                                c.close(message);
                            }
                            catch (IOException iOException) {
                                // empty catch block
                            }
                        }
                    });
                    c.prepare(message);
                    c.close(message);
                    InputStream ins = exch.get(InputStream.class);
                    this.resourceOpened.add(ins);
                    InputSource src = new InputSource(ins);
                    String u = (String)message.get("transport.retransmit.url");
                    if (u == null) {
                        u = base.toString();
                    }
                    src.setPublicId(u);
                    src.setSystemId(u);
                    this.lastestImportUri = u;
                    this.currentResolver.unresolve();
                    return src;
                }
            }
            catch (Exception e) {
                LOG.log(Level.FINEST, "Conduit initiator could not resolve " + baseUri + " " + curUri, e);
            }
        }
        if (!(is != null || base != null && base.getScheme() != null && DEFAULT_URI_RESOLVER_HANDLES.contains(base.getScheme()))) {
            is = super.resolve(curUri, baseUri);
        }
        return is;
    }

    static {
        DEFAULT_URI_RESOLVER_HANDLES.add("file");
        DEFAULT_URI_RESOLVER_HANDLES.add("classpath");
        DEFAULT_URI_RESOLVER_HANDLES.add("wsjar");
        DEFAULT_URI_RESOLVER_HANDLES.add("jar");
        DEFAULT_URI_RESOLVER_HANDLES.add("zip");
    }
}

