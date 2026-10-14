/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.ComplianceViolation;
import org.eclipse.jetty.http.HostPortHttpField;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpHeaderValue;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnection;
import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.server.HttpTransport;
import org.eclipse.jetty.util.NanoTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpChannelOverHttp
extends HttpChannel
implements HttpParser.RequestHandler,
ComplianceViolation.Listener {
    private static final Logger LOG = LoggerFactory.getLogger(HttpChannelOverHttp.class);
    private static final HttpField PREAMBLE_UPGRADE_H2C = new HttpField(HttpHeader.UPGRADE, "h2c");
    private static final HttpInput.Content EOF = new HttpInput.EofContent();
    private final HttpConnection _httpConnection;
    private final RequestBuilder _requestBuilder = new RequestBuilder();
    private MetaData.Request _metadata;
    private HttpField _connection;
    private HttpField _upgrade = null;
    private boolean _delayedForContent;
    private boolean _unknownExpectation = false;
    private boolean _expect100Continue = false;
    private boolean _expect102Processing = false;
    private List<String> _complianceViolations;
    private HttpFields.Mutable _trailers;
    private HttpInput.Content _content;
    private boolean _servletUpgrade;

    public HttpChannelOverHttp(HttpConnection httpConnection, Connector connector, HttpConfiguration config, EndPoint endPoint, HttpTransport transport) {
        super(connector, config, endPoint, transport);
        this._httpConnection = httpConnection;
    }

    @Override
    public void abort(Throwable failure) {
        super.abort(failure);
        this._httpConnection.getGenerator().setPersistent(false);
    }

    @Override
    public boolean needContent() {
        if (this._content != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("needContent has content immediately available: {}", (Object)this._content);
            }
            return true;
        }
        this.parseAndFillForContent();
        if (this._content != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("needContent has content after parseAndFillForContent: {}", (Object)this._content);
            }
            return true;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("needContent has no content");
        }
        this._httpConnection.asyncReadFillInterested();
        return false;
    }

    @Override
    public HttpInput.Content produceContent() {
        HttpInput.Content result;
        if (this._content == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("produceContent has no content, parsing and filling");
            }
            this.parseAndFillForContent();
        }
        if ((result = this._content) != null && !result.isSpecial()) {
            HttpInput.Content content = this._content = result.isEof() ? EOF : null;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("produceContent produced {}", (Object)result);
        }
        return result;
    }

    private void parseAndFillForContent() {
        try {
            this._httpConnection.parseAndFillForContent();
        }
        catch (Throwable x) {
            this._content = new HttpInput.ErrorContent(x);
        }
    }

    @Override
    public boolean failAllContent(Throwable failure) {
        HttpInput.Content c;
        if (LOG.isDebugEnabled()) {
            LOG.debug("failing all content with {} {}", (Object)failure, (Object)this);
        }
        if (this._content != null) {
            if (this._content.isSpecial()) {
                return this._content.isEof();
            }
            this._content.failed(failure);
            HttpInput.Content content = this._content = this._content.isEof() ? EOF : null;
            if (this._content == EOF) {
                return true;
            }
        }
        do {
            if ((c = this.produceContent()) == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("failed all content, EOF was not reached");
                }
                return false;
            }
            if (c.isSpecial()) {
                this._content = c;
                boolean atEof = c.isEof();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("failed all content, EOF = {}", (Object)atEof);
                }
                return atEof;
            }
            c.skip(c.remaining());
            c.failed(failure);
        } while (!c.isEof());
        this._content = EOF;
        return true;
    }

    @Override
    public void badMessage(BadMessageException failure) {
        this._httpConnection.getGenerator().setPersistent(false);
        try {
            if (this._metadata == null) {
                this._metadata = this._requestBuilder.build();
            }
            this.onRequest(this._metadata);
            this.markEarlyEOF();
        }
        catch (Exception e) {
            LOG.trace("IGNORED", e);
        }
        this.onBadMessage(failure);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean content(ByteBuffer buffer) {
        HttpInput.Content content = this._httpConnection.newContent(buffer);
        if (this._content != null) {
            if (!this._content.isSpecial()) throw new AssertionError((Object)("Cannot overwrite exiting content " + this._content + " with " + content));
            content.failed(this._content.getError());
            return true;
        } else {
            this._content = content;
            this.onContent(this._content);
            this._delayedForContent = false;
        }
        return true;
    }

    @Override
    public boolean contentComplete() {
        boolean handle2 = this.onContentComplete() || this._delayedForContent;
        this._delayedForContent = false;
        return handle2;
    }

    @Override
    public void continue100(int available) throws IOException {
        if (this.isExpecting100Continue()) {
            this._expect100Continue = false;
            if (available == 0) {
                if (this.getResponse().isCommitted()) {
                    throw new IOException("Committed before 100 Continues");
                }
                boolean committed = this.sendResponse(HttpGenerator.CONTINUE_100_INFO, null, false);
                if (!committed) {
                    throw new IOException("Concurrent commit while trying to send 100-Continue");
                }
            }
        }
    }

    @Override
    public void earlyEOF() {
        this._httpConnection.getGenerator().setPersistent(false);
        if (this._metadata == null) {
            this._httpConnection.close();
        } else {
            this.markEarlyEOF();
            if (this._delayedForContent) {
                this._delayedForContent = false;
                this.handle();
            }
        }
    }

    private void markEarlyEOF() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("received early EOF, content = {}", (Object)this._content);
        }
        if (this._servletUpgrade) {
            if (this._content != null) {
                this._content.succeeded();
            }
            this._content = EOF;
        } else {
            EofException failure = new EofException("Early EOF");
            if (this._content != null) {
                this._content.failed(failure);
            }
            this._content = new HttpInput.ErrorContent(failure);
        }
    }

    @Override
    protected boolean eof() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("received EOF, content = {}", (Object)this._content);
        }
        if (this._content == null) {
            this._content = EOF;
        } else {
            HttpInput.Content c = this._content;
            this._content = new HttpInput.WrappingContent(c, true);
        }
        return false;
    }

    @Override
    public boolean failed(Throwable x) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("failed {}, content = {}", (Object)x, (Object)this._content);
        }
        Throwable error = null;
        if (this._content != null && this._content.isSpecial()) {
            error = this._content.getError();
        }
        if (error != null && error != x) {
            error.addSuppressed(x);
        } else {
            if (this._content != null) {
                this._content.failed(x);
            }
            this._content = new HttpInput.ErrorContent(x);
        }
        return this.getRequest().getHttpInput().onContentProducible();
    }

    @Override
    public EndPoint getTunnellingEndPoint() {
        return this.getEndPoint();
    }

    @Override
    public boolean headerComplete() {
        boolean persistent;
        this._requestBuilder.beginNanoTime(this._httpConnection.getBeginNanoTime());
        this._metadata = this._requestBuilder.build();
        this.onRequest(this._metadata);
        if (this._complianceViolations != null && !this._complianceViolations.isEmpty()) {
            this.getRequest().setAttribute("org.eclipse.jetty.http.compliance.violations", this._complianceViolations);
            this._complianceViolations = null;
        }
        switch (this._metadata.getHttpVersion()) {
            case HTTP_0_9: {
                persistent = false;
                break;
            }
            case HTTP_1_0: {
                persistent = this.getHttpConfiguration().isPersistentConnectionsEnabled() ? (this._connection != null ? (this._connection.contains(HttpHeaderValue.KEEP_ALIVE.asString()) ? true : this._requestBuilder.getFields().contains(HttpHeader.CONNECTION, HttpHeaderValue.KEEP_ALIVE.asString())) : false) : false;
                if (!persistent) {
                    persistent = HttpMethod.CONNECT.is(this._metadata.getMethod());
                }
                if (!persistent) break;
                this.getResponse().getHttpFields().add(HttpHeader.CONNECTION, HttpHeaderValue.KEEP_ALIVE);
                break;
            }
            case HTTP_1_1: {
                if (this._unknownExpectation) {
                    this.badMessage(new BadMessageException(417));
                    return false;
                }
                persistent = this.getHttpConfiguration().isPersistentConnectionsEnabled() ? (this._connection != null ? (this._connection.contains(HttpHeaderValue.CLOSE.asString()) ? false : !this._requestBuilder.getFields().contains(HttpHeader.CONNECTION, HttpHeaderValue.CLOSE.asString())) : true) : false;
                if (!persistent) {
                    persistent = HttpMethod.CONNECT.is(this._metadata.getMethod());
                }
                if (!persistent) {
                    this.getResponse().getHttpFields().add(HttpHeader.CONNECTION, HttpHeaderValue.CLOSE);
                }
                if (this._upgrade == null || !this.upgrade()) break;
                return true;
            }
            case HTTP_2: {
                this._upgrade = PREAMBLE_UPGRADE_H2C;
                if (HttpMethod.PRI.is(this._metadata.getMethod()) && "*".equals(this._metadata.getURI().getPath()) && this._requestBuilder.getFields().size() == 0 && this.upgrade()) {
                    return true;
                }
                this.badMessage(new BadMessageException(426));
                this._httpConnection.getParser().close();
                return false;
            }
            default: {
                throw new IllegalStateException("unsupported version " + this._metadata.getHttpVersion());
            }
        }
        if (!persistent) {
            this._httpConnection.getGenerator().setPersistent(false);
        }
        this._delayedForContent = this.getHttpConfiguration().isDelayDispatchUntilContent() && (this._httpConnection.getParser().getContentLength() > 0L || this._httpConnection.getParser().isChunking()) && !this.isExpecting100Continue() && !this.isCommitted() && this._httpConnection.isRequestBufferEmpty();
        return !this._delayedForContent;
    }

    @Override
    public boolean isExpecting100Continue() {
        return this._expect100Continue;
    }

    @Override
    public boolean isExpecting102Processing() {
        return this._expect102Processing;
    }

    @Override
    public boolean isTunnellingSupported() {
        return true;
    }

    @Override
    public boolean isUseOutputDirectByteBuffers() {
        return this._httpConnection.isUseOutputDirectByteBuffers();
    }

    @Override
    public boolean messageComplete() {
        if (this._trailers != null) {
            this.onTrailers(this._trailers);
        }
        return this.onRequestComplete();
    }

    @Override
    public void onComplianceViolation(ComplianceViolation.Mode mode, ComplianceViolation violation, String details) {
        if (this._httpConnection.isRecordHttpComplianceViolations()) {
            if (this._complianceViolations == null) {
                this._complianceViolations = new ArrayList<String>();
            }
            String record = String.format("%s (see %s) in mode %s for %s in %s", violation.getDescription(), violation.getURL(), mode, details, this.getHttpTransport());
            this._complianceViolations.add(record);
            if (LOG.isDebugEnabled()) {
                LOG.debug(record);
            }
        }
    }

    @Override
    public void parsedHeader(HttpField field2) {
        HttpHeader header = field2.getHeader();
        String value = field2.getValue();
        if (header != null) {
            switch (header) {
                case CONNECTION: {
                    this._connection = field2;
                    break;
                }
                case HOST: {
                    if (field2 instanceof HostPortHttpField || value == null || value.isEmpty()) break;
                    field2 = new HostPortHttpField(value);
                    break;
                }
                case EXPECT: {
                    if (HttpHeaderValue.parseCsvIndex(value, t2 -> {
                        switch (t2) {
                            case CONTINUE: {
                                this._expect100Continue = true;
                                return true;
                            }
                            case PROCESSING: {
                                this._expect102Processing = true;
                                return true;
                            }
                        }
                        return false;
                    }, s2 -> false)) break;
                    this._unknownExpectation = true;
                    this._expect100Continue = false;
                    this._expect102Processing = false;
                    break;
                }
                case UPGRADE: {
                    this._upgrade = field2;
                    break;
                }
            }
        }
        this._requestBuilder.getFields().add(field2);
    }

    @Override
    public void parsedTrailer(HttpField field2) {
        if (this._trailers == null) {
            this._trailers = HttpFields.build();
        }
        this._trailers.add(field2);
    }

    @Override
    public void recycle() {
        super.recycle();
        this._unknownExpectation = false;
        this._expect100Continue = false;
        this._expect102Processing = false;
        this._connection = null;
        this._upgrade = null;
        this._trailers = null;
        this._metadata = null;
        if (this._content != null && !this._content.isSpecial()) {
            throw new AssertionError((Object)("unconsumed content: " + this._content));
        }
        this._content = null;
        this._servletUpgrade = false;
    }

    public void servletUpgrade() {
        if (!(this._content == null || this._content.isSpecial() && this._content.isEof())) {
            throw new IllegalStateException("Cannot perform servlet upgrade with unconsumed content");
        }
        this._content = null;
        this._servletUpgrade = true;
        this._httpConnection.getParser().servletUpgrade();
    }

    @Override
    public void startRequest(String method2, String uri, HttpVersion version) {
        this._requestBuilder.request(method2, uri, version);
        this._unknownExpectation = false;
        this._expect100Continue = false;
        this._expect102Processing = false;
    }

    @Override
    protected boolean checkAndPrepareUpgrade() {
        return false;
    }

    @Override
    protected void handleException(Throwable x) {
        this._httpConnection.getGenerator().setPersistent(false);
        super.handleException(x);
    }

    private boolean upgrade() throws BadMessageException {
        boolean isUpgradedH2C;
        if (LOG.isDebugEnabled()) {
            LOG.debug("upgrade {} {}", (Object)this, (Object)this._upgrade);
        }
        boolean bl = isUpgradedH2C = this._upgrade == PREAMBLE_UPGRADE_H2C;
        if (!(isUpgradedH2C || this._connection != null && this._connection.contains("upgrade"))) {
            throw new BadMessageException(400);
        }
        ConnectionFactory.Upgrading factory2 = this.getConnector().getConnectionFactories().stream().filter(f -> f instanceof ConnectionFactory.Upgrading).map(ConnectionFactory.Upgrading.class::cast).filter(f -> f.getProtocols().contains(this._upgrade.getValue())).findAny().orElse(null);
        if (factory2 == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("No factory for {} in {}", (Object)this._upgrade, (Object)this.getConnector());
            }
            return false;
        }
        HttpFields.Mutable response101 = HttpFields.build();
        Connection upgradeConnection = factory2.upgradeConnection(this.getConnector(), this.getEndPoint(), this._metadata, response101);
        if (upgradeConnection == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Upgrade ignored for {} by {}", (Object)this._upgrade, (Object)factory2);
            }
            return false;
        }
        try {
            if (!isUpgradedH2C) {
                this.sendResponse(new MetaData.Response(HttpVersion.HTTP_1_1, 101, response101, 0L), null, true);
            }
        }
        catch (IOException e) {
            throw new BadMessageException(500, null, e);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Upgrade from {} to {}", (Object)this.getEndPoint().getConnection(), (Object)upgradeConnection);
        }
        this.getRequest().setAttribute(HttpTransport.UPGRADE_CONNECTION_ATTRIBUTE, upgradeConnection);
        this.getHttpTransport().onCompleted();
        return true;
    }

    boolean onIdleTimeout(Throwable timeout2) {
        if (this._delayedForContent) {
            this._delayedForContent = false;
            this.doOnIdleTimeout(timeout2);
            this.execute(this);
            return false;
        }
        return true;
    }

    private void doOnIdleTimeout(Throwable x) {
        boolean waitingForContent;
        boolean neverDispatched = this.getState().isIdle();
        boolean bl = waitingForContent = this._content == null || this._content.remaining() == 0;
        if (!(!waitingForContent && !neverDispatched || this._content != null && this._content.isSpecial())) {
            x.addSuppressed(new Throwable("HttpInput idle timeout"));
            this._content = new HttpInput.ErrorContent(x);
        }
    }

    private static class RequestBuilder {
        private final HttpFields.Mutable _fieldsBuilder = HttpFields.build();
        private final HttpURI.Mutable _uriBuilder = HttpURI.build();
        private String _method;
        private HttpVersion _version;
        private long _beginNanoTime = Long.MIN_VALUE;

        private RequestBuilder() {
        }

        public String method() {
            return this._method;
        }

        public void request(String method2, String uri, HttpVersion version) {
            this._method = method2;
            this._uriBuilder.uri(method2, uri);
            this._version = version;
            this._fieldsBuilder.clear();
        }

        public void beginNanoTime(long nanoTime) {
            if (nanoTime == Long.MIN_VALUE) {
                ++nanoTime;
            }
            this._beginNanoTime = nanoTime;
        }

        public HttpFields.Mutable getFields() {
            return this._fieldsBuilder;
        }

        public MetaData.Request build() {
            long nanoTime = this._beginNanoTime == Long.MIN_VALUE ? NanoTime.now() : this._beginNanoTime;
            return new MetaData.Request(nanoTime, this._method, this._uriBuilder, this._version, this._fieldsBuilder);
        }

        public HttpVersion version() {
            return this._version;
        }
    }
}

