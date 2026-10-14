/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http_jetty;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CopyingOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http_jetty.JettyHTTPHandler;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngine;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.apache.cxf.transport.http_jetty.ServerEngine;
import org.apache.cxf.transport.http_jetty.continuations.JettyContinuationProvider;
import org.apache.cxf.transport.https.CertConstraintsJaxBUtils;
import org.apache.cxf.transport.servlet.ServletDestination;
import org.apache.cxf.transports.http.configuration.HTTPServerPolicy;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpConnection;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Request;

public class JettyHTTPDestination
extends ServletDestination {
    private static final Logger LOG = LogUtils.getL7dLogger(JettyHTTPDestination.class);
    protected JettyHTTPServerEngine engine;
    protected JettyHTTPServerEngineFactory serverEngineFactory;
    protected JettyHTTPHandler handler;
    protected ServletContext servletContext;
    protected URL nurl;
    protected ClassLoader loader;
    private boolean configFinalized;

    public JettyHTTPDestination(Bus bus, DestinationRegistry registry, EndpointInfo ei, JettyHTTPServerEngineFactory serverEngineFactory) throws IOException {
        this(bus, registry, ei, serverEngineFactory == null ? null : new URL(JettyHTTPDestination.getAddressValue(ei, true).getAddress()), serverEngineFactory);
    }

    protected JettyHTTPDestination(Bus bus, DestinationRegistry registry, EndpointInfo ei, URL nurl, JettyHTTPServerEngineFactory serverEngineFactory) throws IOException {
        super(bus, registry, ei, JettyHTTPDestination.getAddressValue(ei, true).getAddress(), true);
        this.serverEngineFactory = serverEngineFactory;
        this.nurl = nurl;
        this.loader = bus.getExtension(ClassLoader.class);
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    public void setServletContext(ServletContext sc) {
        this.servletContext = sc;
    }

    protected void retrieveEngine() throws GeneralSecurityException, IOException {
        CertificateConstraintsType constraints;
        if (this.serverEngineFactory == null) {
            return;
        }
        this.engine = this.serverEngineFactory.retrieveJettyHTTPServerEngine(this.nurl.getPort());
        if (this.engine == null) {
            this.engine = this.serverEngineFactory.createJettyHTTPServerEngine(this.nurl.getHost(), this.nurl.getPort(), this.nurl.getProtocol());
        }
        assert (this.engine != null);
        TLSServerParameters serverParameters = this.engine.getTlsServerParameters();
        if (serverParameters != null && serverParameters.getCertConstraints() != null && (constraints = serverParameters.getCertConstraints()) != null) {
            this.certConstraints = CertConstraintsJaxBUtils.createCertConstraints(constraints);
        }
        if (!this.nurl.getProtocol().equals(this.engine.getProtocol())) {
            throw new IllegalStateException("Port " + this.engine.getPort() + " is configured with wrong protocol \"" + this.engine.getProtocol() + "\" for \"" + this.nurl + "\"");
        }
    }

    @Override
    public void finalizeConfig() {
        assert (!this.configFinalized);
        try {
            this.retrieveEngine();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        this.configFinalized = true;
    }

    protected String getAddress(EndpointInfo endpointInfo) {
        return endpointInfo.getAddress();
    }

    @Override
    protected void activate() {
        super.activate();
        LOG.log(Level.FINE, "Activating receipt of incoming messages");
        if (this.engine != null) {
            this.handler = this.createJettyHTTPHandler(this, this.contextMatchOnExact());
            this.engine.addServant(this.nurl, this.handler);
        }
    }

    protected JettyHTTPHandler createJettyHTTPHandler(JettyHTTPDestination jhd, boolean cmExact) {
        return new JettyHTTPHandler(jhd, cmExact);
    }

    @Override
    protected void deactivate() {
        super.deactivate();
        LOG.log(Level.FINE, "Deactivating receipt of incoming messages");
        if (this.engine != null) {
            this.engine.removeServant(this.nurl);
        }
        this.handler = null;
    }

    protected String getBasePathForFullAddress(String addr) {
        try {
            return new URL(addr).getPath();
        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doService(this.servletContext, req, resp);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doService(ServletContext context, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (context == null) {
            context = this.servletContext;
        }
        Request baseRequest = req instanceof Request ? (Request)req : this.getCurrentRequest();
        HTTPServerPolicy sp2 = this.getServer();
        if (sp2.isSetRedirectURL()) {
            resp.sendRedirect(sp2.getRedirectURL());
            resp.flushBuffer();
            baseRequest.setHandled(true);
            return;
        }
        ClassLoaderUtils.ClassLoaderHolder origLoader = null;
        Bus origBus = BusFactory.getAndSetThreadDefaultBus(this.bus);
        try {
            if (this.loader != null) {
                origLoader = ClassLoaderUtils.setThreadContextClassloader(this.loader);
            }
            this.invoke(null, context, req, resp);
        }
        finally {
            if (origBus != this.bus) {
                BusFactory.setThreadDefaultBus(origBus);
            }
            if (origLoader != null) {
                origLoader.reset();
            }
        }
    }

    @Override
    protected void invokeComplete(ServletContext context, HttpServletRequest req, HttpServletResponse resp, Message m4) throws IOException {
        Request baseRequest;
        resp.flushBuffer();
        Request request = baseRequest = req instanceof Request ? (Request)req : this.getCurrentRequest();
        if (baseRequest != null) {
            baseRequest.setHandled(true);
        }
        super.invokeComplete(context, req, resp, m4);
    }

    @Override
    protected OutputStream flushHeaders(Message outMessage, boolean getStream) throws IOException {
        OutputStream out = super.flushHeaders(outMessage, getStream);
        return this.wrapOutput(out);
    }

    private OutputStream wrapOutput(OutputStream out) {
        try {
            if (out instanceof HttpOutput) {
                out = new JettyOutputStream((HttpOutput)out);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return out;
    }

    public ServerEngine getEngine() {
        return this.engine;
    }

    @Override
    protected Message retrieveFromContinuation(HttpServletRequest req) {
        return (Message)req.getAttribute("cxf.continuation.message");
    }

    @Override
    protected void setupContinuation(Message inMessage, HttpServletRequest req, HttpServletResponse resp) {
        if (this.engine != null && this.engine.getContinuationsEnabled()) {
            super.setupContinuation(inMessage, req, resp);
            if (!inMessage.containsKey(ContinuationProvider.class.getName())) {
                inMessage.put(ContinuationProvider.class.getName(), new JettyContinuationProvider(req, resp, inMessage));
            }
        }
    }

    private Request getCurrentRequest() {
        try {
            HttpConnection con = HttpConnection.getCurrentConnection();
            HttpChannel channel = con.getHttpChannel();
            return channel.getRequest();
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    static class CountingInputStream
    extends FilterInputStream {
        int count;

        CountingInputStream(InputStream in) {
            super(in);
        }

        public int getCount() {
            return this.count;
        }

        @Override
        public int read() throws IOException {
            int i = super.read();
            if (i != -1) {
                ++this.count;
            }
            return i;
        }

        @Override
        public int read(byte[] b) throws IOException {
            int i = super.read(b);
            if (i != -1) {
                this.count += i;
            }
            return i;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int i = super.read(b, off, len);
            if (i != -1) {
                this.count += i;
            }
            return i;
        }
    }

    static class JettyOutputStream
    extends FilterOutputStream
    implements CopyingOutputStream {
        final HttpOutput out;
        boolean written;

        JettyOutputStream(HttpOutput o) {
            super(o);
            this.out = o;
        }

        private boolean sendContent(Class<?> type, InputStream c) throws IOException {
            try {
                this.out.sendContent(c);
            }
            catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        public int copyFrom(InputStream in) throws IOException {
            if (this.written) {
                return IOUtils.copy(in, this.out);
            }
            CountingInputStream c = new CountingInputStream(in);
            if (!this.sendContent(InputStream.class, c) && !this.sendContent(Object.class, c)) {
                IOUtils.copy(c, this.out);
            }
            return c.getCount();
        }

        @Override
        public void write(int b) throws IOException {
            this.written = true;
            this.out.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            this.written = true;
            this.out.write(b, off, len);
        }

        @Override
        public void close() throws IOException {
            this.out.close();
        }
    }
}

