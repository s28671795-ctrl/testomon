/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritePendingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpChannelState;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.SharedBlockingCallback;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpOutput
extends ServletOutputStream
implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(HttpOutput.class);
    private static final ThreadLocal<CharsetEncoder> _encoder = new ThreadLocal();
    private final HttpChannel _channel;
    private final HttpChannelState _channelState;
    private final SharedBlockingCallback _writeBlocker;
    private ApiState _apiState = ApiState.BLOCKING;
    private State _state = State.OPEN;
    private boolean _softClose = false;
    private Interceptor _interceptor;
    private long _written;
    private long _flushed;
    private long _firstByteNanoTime = -1L;
    private ByteBuffer _aggregate;
    private int _bufferSize;
    private int _commitSize;
    private WriteListener _writeListener;
    private volatile Throwable _onError;
    private Callback _closedCallback;

    public HttpOutput(HttpChannel channel) {
        this._channel = channel;
        this._channelState = channel.getState();
        this._interceptor = channel;
        this._writeBlocker = new WriteBlocker(channel);
        HttpConfiguration config = channel.getHttpConfiguration();
        this._bufferSize = config.getOutputBufferSize();
        this._commitSize = config.getOutputAggregationSize();
        if (this._commitSize > this._bufferSize) {
            LOG.warn("OutputAggregationSize {} exceeds bufferSize {}", (Object)this._commitSize, (Object)this._bufferSize);
            this._commitSize = this._bufferSize;
        }
    }

    public HttpChannel getHttpChannel() {
        return this._channel;
    }

    public Interceptor getInterceptor() {
        return this._interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this._interceptor = interceptor;
    }

    public boolean isWritten() {
        return this._written > 0L;
    }

    public long getWritten() {
        return this._written;
    }

    public void reopen() {
        try (AutoLock l = this._channelState.lock();){
            this._softClose = false;
        }
    }

    protected SharedBlockingCallback.Blocker acquireWriteBlockingCallback() throws IOException {
        return this._writeBlocker.acquire();
    }

    private void channelWrite(ByteBuffer content, boolean complete) throws IOException {
        try (SharedBlockingCallback.Blocker blocker = this._writeBlocker.acquire();){
            this.channelWrite(content, complete, blocker);
            blocker.block();
        }
    }

    private void channelWrite(ByteBuffer content, boolean last, Callback callback) {
        if (this._firstByteNanoTime == -1L) {
            long minDataRate = this.getHttpChannel().getHttpConfiguration().getMinResponseDataRate();
            this._firstByteNanoTime = minDataRate > 0L ? NanoTime.now() : Long.MAX_VALUE;
        }
        this._interceptor.write(content, last, callback);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onWriteComplete(boolean last, Throwable failure) {
        String state = null;
        boolean wake = false;
        Callback closedCallback = null;
        ByteBuffer closeContent = null;
        try (AutoLock l = this._channelState.lock();){
            if (LOG.isDebugEnabled()) {
                state = this.stateString();
            }
            if (last || failure != null) {
                this._state = State.CLOSED;
                closedCallback = this._closedCallback;
                this._closedCallback = null;
                this.releaseBuffer(failure);
                wake = this.updateApiState(failure);
            } else if (this._state == State.CLOSE) {
                this._state = State.CLOSING;
                closeContent = BufferUtil.hasContent(this._aggregate) ? this._aggregate : BufferUtil.EMPTY_BUFFER;
            } else {
                wake = this.updateApiState(null);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("onWriteComplete({},{}) {}->{} c={} cb={} w={}", last, failure, state, this.stateString(), BufferUtil.toDetailString(closeContent), closedCallback, wake);
        }
        try {
            if (failure != null) {
                this._channel.abort(failure);
            }
            if (closedCallback != null) {
                if (failure == null) {
                    closedCallback.succeeded();
                } else {
                    closedCallback.failed(failure);
                }
            } else if (closeContent != null) {
                this.channelWrite(closeContent, true, new WriteCompleteCB());
            }
        }
        finally {
            if (wake) {
                this._channel.execute(this._channel);
            }
        }
    }

    private boolean updateApiState(Throwable failure) {
        boolean wake = false;
        switch (this._apiState) {
            case BLOCKED: {
                this._apiState = ApiState.BLOCKING;
                break;
            }
            case PENDING: {
                this._apiState = ApiState.ASYNC;
                if (failure == null) break;
                this._onError = failure;
                wake = this._channelState.onWritePossible();
                break;
            }
            case UNREADY: {
                this._apiState = ApiState.READY;
                if (failure != null) {
                    this._onError = failure;
                }
                wake = this._channelState.onWritePossible();
                break;
            }
            default: {
                if (this._state == State.CLOSED) break;
                throw new IllegalStateException(this.stateString());
            }
        }
        return wake;
    }

    private int maximizeAggregateSpace() {
        if (this._aggregate == null) {
            return this.getBufferSize();
        }
        BufferUtil.compact(this._aggregate);
        return BufferUtil.space(this._aggregate);
    }

    public void softClose() {
        try (AutoLock l = this._channelState.lock();){
            this._softClose = true;
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void complete(Callback callback) {
        block27: {
            succeeded = false;
            error /* !! */  = null;
            content = null;
            l = this._channelState.lock();
            try {
                switch (2.$SwitchMap$org$eclipse$jetty$server$HttpOutput$ApiState[this._apiState.ordinal()]) {
                    case 3: {
                        error /* !! */  = new CancellationException("Completed whilst write unready");
                        break;
                    }
                    case 2: {
                        if (this._channel.getResponse().isContentComplete(this._written)) break;
                        error /* !! */  = new CancellationException("Completed whilst write pending");
                        break;
                    }
                    case 1: {
                        error /* !! */  = new CancellationException("Completed whilst write blocked");
                        break;
                    }
                }
                if (error /* !! */  != null) {
                    this._channel.abort(error /* !! */ );
                    this._writeBlocker.fail(error /* !! */ );
                    this._state = State.CLOSED;
                    break block27;
                }
                switch (2.$SwitchMap$org$eclipse$jetty$server$HttpOutput$State[this._state.ordinal()]) {
                    case 1: {
                        succeeded = true;
                        ** break;
lbl27:
                        // 1 sources

                        break;
                    }
                    case 2: 
                    case 3: {
                        this._closedCallback = Callback.combine(this._closedCallback, callback);
                        ** break;
lbl31:
                        // 1 sources

                        break;
                    }
                    case 4: {
                        if (this._onError != null) {
                            error /* !! */  = this._onError;
                            ** break;
lbl36:
                            // 1 sources

                            break;
                        }
                        this._closedCallback = Callback.combine(this._closedCallback, callback);
                        switch (2.$SwitchMap$org$eclipse$jetty$server$HttpOutput$ApiState[this._apiState.ordinal()]) {
                            case 4: {
                                this._apiState = ApiState.BLOCKED;
                                this._state = State.CLOSING;
                                content = BufferUtil.hasContent(this._aggregate) != false ? this._aggregate : BufferUtil.EMPTY_BUFFER;
                                ** break;
lbl44:
                                // 1 sources

                                break block27;
                            }
                            case 5: 
                            case 6: {
                                this._apiState = ApiState.PENDING;
                                this._state = State.CLOSING;
                                content = BufferUtil.hasContent(this._aggregate) != false ? this._aggregate : BufferUtil.EMPTY_BUFFER;
                                ** break;
lbl50:
                                // 1 sources

                                break block27;
                            }
                            case 2: 
                            case 3: {
                                this._softClose = true;
                                this._state = State.CLOSE;
                                ** break;
lbl55:
                                // 1 sources

                                break block27;
                            }
                        }
                        throw new IllegalStateException();
                    }
                    ** default:
lbl58:
                    // 1 sources

                    break;
                }
            }
            finally {
                if (l != null) {
                    l.close();
                }
            }
        }
        if (HttpOutput.LOG.isDebugEnabled()) {
            HttpOutput.LOG.debug("complete({}) {} s={} e={}, c={}", new Object[]{callback, this.stateString(), succeeded, error /* !! */ , BufferUtil.toDetailString(content)});
        }
        if (succeeded) {
            callback.succeeded();
            return;
        }
        if (error /* !! */  != null) {
            callback.failed(error /* !! */ );
            return;
        }
        if (content != null) {
            this.channelWrite(content, true, new WriteCompleteCB());
        }
    }

    public void completed(Throwable failure) {
        try (AutoLock l = this._channelState.lock();){
            this._state = State.CLOSED;
            this.releaseBuffer(failure);
        }
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void close() throws IOException {
        block43: {
            content = null;
            blocker = null;
            l = this._channelState.lock();
            try {
                if (this._onError != null) {
                    if (this._onError instanceof IOException) {
                        throw (IOException)this._onError;
                    }
                    if (this._onError instanceof RuntimeException) {
                        throw (RuntimeException)this._onError;
                    }
                    if (this._onError instanceof Error) {
                        throw (Error)this._onError;
                    }
                    throw new IOException(this._onError);
                }
                switch (2.$SwitchMap$org$eclipse$jetty$server$HttpOutput$State[this._state.ordinal()]) {
                    case 1: {
                        ** break;
lbl16:
                        // 1 sources

                        break;
                    }
                    case 2: 
                    case 3: {
                        switch (2.$SwitchMap$org$eclipse$jetty$server$HttpOutput$ApiState[this._apiState.ordinal()]) {
                            case 1: 
                            case 4: {
                                blocker = this._writeBlocker.acquire();
                                this._closedCallback = Callback.combine(this._closedCallback, blocker);
                                ** break;
lbl23:
                                // 1 sources

                                break block43;
                            }
                        }
                        ** break;
lbl25:
                        // 1 sources

                        break;
                    }
                    case 4: {
                        switch (2.$SwitchMap$org$eclipse$jetty$server$HttpOutput$ApiState[this._apiState.ordinal()]) {
                            case 4: {
                                this._apiState = ApiState.BLOCKED;
                                this._state = State.CLOSING;
                                blocker = this._writeBlocker.acquire();
                                content = BufferUtil.hasContent(this._aggregate) != false ? this._aggregate : BufferUtil.EMPTY_BUFFER;
                                ** break;
                            }
                            case 1: {
                                this._softClose = true;
                                this._state = State.CLOSE;
                                blocker = this._writeBlocker.acquire();
                                this._closedCallback = Callback.combine(this._closedCallback, blocker);
                                ** break;
                            }
                            case 5: 
                            case 6: {
                                this._apiState = ApiState.PENDING;
                                this._state = State.CLOSING;
                                content = BufferUtil.hasContent(this._aggregate) != false ? this._aggregate : BufferUtil.EMPTY_BUFFER;
                                ** break;
                            }
                            case 2: 
                            case 3: {
                                this._softClose = true;
                                this._state = State.CLOSE;
                            }
lbl48:
                            // 5 sources

                            ** default:
lbl49:
                            // 1 sources

                            break block43;
                        }
                    }
                    ** default:
lbl51:
                    // 1 sources

                    break;
                }
            }
            finally {
                if (l != null) {
                    l.close();
                }
            }
        }
        if (HttpOutput.LOG.isDebugEnabled()) {
            HttpOutput.LOG.debug("close() {} c={} b={}", new Object[]{this.stateString(), BufferUtil.toDetailString(content), blocker});
        }
        if (content == null) {
            if (blocker == null) {
                return;
            }
            b = blocker;
            try {
                b.block();
            }
            finally {
                if (b != null) {
                    b.close();
                }
            }
        }
        if (blocker == null) {
            this.channelWrite(content, true, new WriteCompleteCB());
        } else {
            try {
                b = blocker;
                try {
                    this.channelWrite(content, true, blocker);
                    b.block();
                    this.onWriteComplete(true, null);
                }
                finally {
                    if (b != null) {
                        b.close();
                    }
                }
            }
            catch (Throwable t) {
                this.onWriteComplete(true, t);
                throw t;
            }
        }
    }

    public ByteBuffer getBuffer() {
        try (AutoLock l = this._channelState.lock();){
            ByteBuffer byteBuffer = this.acquireBuffer();
            return byteBuffer;
        }
    }

    private ByteBuffer acquireBuffer() {
        if (this._aggregate == null) {
            this._aggregate = this._channel.getByteBufferPool().acquire(this.getBufferSize(), this._channel.isUseOutputDirectByteBuffers());
        }
        return this._aggregate;
    }

    private void releaseBuffer(Throwable failure) {
        if (this._aggregate != null) {
            ByteBufferPool bufferPool = this._channel.getConnector().getByteBufferPool();
            if (failure == null) {
                bufferPool.release(this._aggregate);
            } else {
                bufferPool.remove(this._aggregate);
            }
            this._aggregate = null;
        }
    }

    public boolean isClosed() {
        try (AutoLock l = this._channelState.lock();){
            boolean bl = this._softClose || this._state != State.OPEN;
            return bl;
        }
    }

    public boolean isAsync() {
        try (AutoLock l = this._channelState.lock();){
            switch (this._apiState) {
                case PENDING: 
                case UNREADY: 
                case ASYNC: 
                case READY: {
                    boolean bl = true;
                    return bl;
                }
            }
            boolean bl = false;
            return bl;
        }
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void flush() throws IOException {
        content = null;
        l = this._channelState.lock();
        try {
            switch (2.$SwitchMap$org$eclipse$jetty$server$HttpOutput$State[this._state.ordinal()]) {
                case 1: 
                case 3: {
                    return;
                }
            }
            switch (2.$SwitchMap$org$eclipse$jetty$server$HttpOutput$ApiState[this._apiState.ordinal()]) {
                case 4: {
                    this._apiState = ApiState.BLOCKED;
                    content = BufferUtil.hasContent(this._aggregate) != false ? this._aggregate : BufferUtil.EMPTY_BUFFER;
                    ** break;
lbl12:
                    // 1 sources

                    break;
                }
                case 2: 
                case 5: {
                    throw new IllegalStateException("isReady() not called: " + this.stateString());
                }
                case 6: {
                    this._apiState = ApiState.PENDING;
                    ** break;
lbl18:
                    // 1 sources

                    break;
                }
                case 3: {
                    throw new WritePendingException();
                }
                default: {
                    throw new IllegalStateException(this.stateString());
                }
            }
        }
        finally {
            if (l != null) {
                l.close();
            }
        }
        if (content == null) {
            new AsyncFlush(false).iterate();
        } else {
            try {
                this.channelWrite(content, false);
                this.onWriteComplete(false, null);
            }
            catch (Throwable t) {
                this.onWriteComplete(false, t);
                throw t;
            }
        }
    }

    private void checkWritable() throws EofException {
        if (this._softClose) {
            throw new EofException("Closed");
        }
        switch (this._state) {
            case CLOSED: 
            case CLOSING: {
                throw new EofException("Closed");
            }
        }
        if (this._onError != null) {
            throw new EofException(this._onError);
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        boolean async;
        boolean aggregate;
        boolean last;
        if (LOG.isDebugEnabled()) {
            LOG.debug("write(array {})", (Object)BufferUtil.toDetailString(ByteBuffer.wrap(b, off, len)));
        }
        try (AutoLock l = this._channelState.lock();){
            boolean flush;
            this.checkWritable();
            long written = this._written + (long)len;
            int space = this.maximizeAggregateSpace();
            last = this._channel.getResponse().isAllContentWritten(written);
            aggregate = len <= this._commitSize && (!last || BufferUtil.hasContent(this._aggregate) && len <= space);
            boolean bl = flush = last || !aggregate || len >= space;
            if (last && this._state == State.OPEN) {
                this._state = State.CLOSING;
            }
            switch (this._apiState) {
                case BLOCKING: {
                    this._apiState = flush ? ApiState.BLOCKED : ApiState.BLOCKING;
                    async = false;
                    break;
                }
                case ASYNC: {
                    throw new IllegalStateException("isReady() not called: " + this.stateString());
                }
                case READY: {
                    async = true;
                    this._apiState = flush ? ApiState.PENDING : ApiState.ASYNC;
                    break;
                }
                case PENDING: 
                case UNREADY: {
                    throw new WritePendingException();
                }
                default: {
                    throw new IllegalStateException(this.stateString());
                }
            }
            this._written = written;
            if (aggregate) {
                this.acquireBuffer();
                int filled = BufferUtil.fill(this._aggregate, b, off, len);
                if (!flush) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("write(array) {} aggregated !flush {}", (Object)this.stateString(), (Object)BufferUtil.toDetailString(this._aggregate));
                    }
                    return;
                }
                off += filled;
                len -= filled;
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("write(array) {} last={} agg={} flush=true async={}, len={} {}", this.stateString(), last, aggregate, async, len, BufferUtil.toDetailString(this._aggregate));
        }
        if (async) {
            new AsyncWrite(b, off, len, last).iterate();
            return;
        }
        try {
            boolean complete = false;
            if (BufferUtil.hasContent(this._aggregate)) {
                complete = last && len == 0;
                this.channelWrite(this._aggregate, complete);
                if (len > 0 && !last && len <= this._commitSize && len <= this.maximizeAggregateSpace()) {
                    BufferUtil.append(this._aggregate, b, off, len);
                    this.onWriteComplete(false, null);
                    return;
                }
            }
            if (len > 0) {
                ByteBuffer view = ByteBuffer.wrap(b, off, len);
                while (len > this.getBufferSize()) {
                    int p = view.position();
                    int l = p + this.getBufferSize();
                    view.limit(l);
                    this.channelWrite(view, false);
                    view.limit(p + len);
                    view.position(l);
                    len -= this.getBufferSize();
                }
                this.channelWrite(view, last);
            } else if (last && !complete) {
                this.channelWrite(BufferUtil.EMPTY_BUFFER, true);
            }
            this.onWriteComplete(last, null);
        }
        catch (Throwable t2) {
            this.onWriteComplete(last, t2);
            throw t2;
        }
    }

    public void write(ByteBuffer buffer) throws IOException {
        boolean async;
        boolean flush;
        boolean last;
        int len = BufferUtil.length(buffer);
        try (AutoLock l = this._channelState.lock();){
            this.checkWritable();
            long written = this._written + (long)len;
            last = this._channel.getResponse().isAllContentWritten(written);
            boolean bl = flush = last || len > 0 || BufferUtil.hasContent(this._aggregate);
            if (last && this._state == State.OPEN) {
                this._state = State.CLOSING;
            }
            switch (this._apiState) {
                case BLOCKING: {
                    async = false;
                    this._apiState = flush ? ApiState.BLOCKED : ApiState.BLOCKING;
                    break;
                }
                case ASYNC: {
                    throw new IllegalStateException("isReady() not called: " + this.stateString());
                }
                case READY: {
                    async = true;
                    this._apiState = flush ? ApiState.PENDING : ApiState.ASYNC;
                    break;
                }
                case PENDING: 
                case UNREADY: {
                    throw new WritePendingException();
                }
                default: {
                    throw new IllegalStateException(this.stateString());
                }
            }
            this._written = written;
        }
        if (!flush) {
            return;
        }
        if (async) {
            new AsyncWrite(buffer, last).iterate();
        } else {
            try {
                boolean complete = false;
                if (BufferUtil.hasContent(this._aggregate)) {
                    complete = last && len == 0;
                    this.channelWrite(this._aggregate, complete);
                }
                if (len > 0) {
                    this.channelWrite(buffer, last);
                } else if (last && !complete) {
                    this.channelWrite(BufferUtil.EMPTY_BUFFER, true);
                }
                this.onWriteComplete(last, null);
            }
            catch (Throwable t2) {
                this.onWriteComplete(last, t2);
                throw t2;
            }
        }
    }

    @Override
    public void write(int b) throws IOException {
        boolean flush;
        boolean last;
        boolean async = false;
        try (AutoLock l = this._channelState.lock();){
            this.checkWritable();
            long written = this._written + 1L;
            int space = this.maximizeAggregateSpace();
            last = this._channel.getResponse().isAllContentWritten(written);
            boolean bl = flush = last || space == 1;
            if (last && this._state == State.OPEN) {
                this._state = State.CLOSING;
            }
            switch (this._apiState) {
                case BLOCKING: {
                    this._apiState = flush ? ApiState.BLOCKED : ApiState.BLOCKING;
                    break;
                }
                case ASYNC: {
                    throw new IllegalStateException("isReady() not called: " + this.stateString());
                }
                case READY: {
                    async = true;
                    this._apiState = flush ? ApiState.PENDING : ApiState.ASYNC;
                    break;
                }
                case PENDING: 
                case UNREADY: {
                    throw new WritePendingException();
                }
                default: {
                    throw new IllegalStateException(this.stateString());
                }
            }
            this._written = written;
            this.acquireBuffer();
            BufferUtil.append(this._aggregate, (byte)b);
        }
        if (!flush) {
            return;
        }
        if (async) {
            new AsyncFlush(last).iterate();
        } else {
            try {
                this.channelWrite(this._aggregate, last);
                this.onWriteComplete(last, null);
            }
            catch (Throwable t2) {
                this.onWriteComplete(last, t2);
                throw t2;
            }
        }
    }

    @Override
    public void print(String s2) throws IOException {
        this.print(s2, false);
    }

    @Override
    public void println(String s2) throws IOException {
        this.print(s2, true);
    }

    private void print(String s2, boolean eoln) throws IOException {
        if (this.isClosed()) {
            throw new IOException("Closed");
        }
        s2 = String.valueOf(s2);
        String charset = this._channel.getResponse().getCharacterEncoding();
        CharsetEncoder encoder = _encoder.get();
        if (encoder == null || !encoder.charset().name().equalsIgnoreCase(charset)) {
            encoder = Charset.forName(charset).newEncoder();
            encoder.onMalformedInput(CodingErrorAction.REPLACE);
            encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
            _encoder.set(encoder);
        } else {
            encoder.reset();
        }
        CharBuffer in = CharBuffer.wrap(s2);
        CharBuffer crlf = eoln ? CharBuffer.wrap("\r\n") : null;
        ByteBuffer out = this.getHttpChannel().getByteBufferPool().acquire((int)(1.0f + (float)(s2.length() + 2) * encoder.averageBytesPerChar()), false);
        BufferUtil.flipToFill(out);
        while (true) {
            CoderResult result;
            if (in.hasRemaining()) {
                result = encoder.encode(in, out, crlf == null);
                if (result.isUnderflow()) {
                    if (crlf != null) continue;
                    break;
                }
            } else {
                if (crlf == null || !crlf.hasRemaining()) break;
                result = encoder.encode(crlf, out, true);
                if (result.isUnderflow()) {
                    if (encoder.flush(out).isUnderflow()) break;
                    result.throwException();
                    break;
                }
            }
            if (result.isOverflow()) {
                BufferUtil.flipToFlush(out, 0);
                ByteBuffer bigger = BufferUtil.ensureCapacity(out, out.capacity() + s2.length() + 2);
                this.getHttpChannel().getByteBufferPool().release(out);
                BufferUtil.flipToFill(bigger);
                out = bigger;
                continue;
            }
            result.throwException();
        }
        BufferUtil.flipToFlush(out, 0);
        this.write(out.array(), out.arrayOffset(), out.remaining());
        this.getHttpChannel().getByteBufferPool().release(out);
    }

    public void sendContent(ByteBuffer content) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("sendContent({})", (Object)BufferUtil.toDetailString(content));
        }
        this._written += (long)content.remaining();
        this.channelWrite(content, true);
    }

    public void sendContent(InputStream in) throws IOException {
        try (SharedBlockingCallback.Blocker blocker = this._writeBlocker.acquire();){
            new InputStreamWritingCB(in, blocker).iterate();
            blocker.block();
        }
    }

    public void sendContent(ReadableByteChannel in) throws IOException {
        try (SharedBlockingCallback.Blocker blocker = this._writeBlocker.acquire();){
            new ReadableByteChannelWritingCB(in, blocker).iterate();
            blocker.block();
        }
    }

    public void sendContent(HttpContent content) throws IOException {
        try (SharedBlockingCallback.Blocker blocker = this._writeBlocker.acquire();){
            this.sendContent(content, (Callback)blocker);
            blocker.block();
        }
    }

    public void sendContent(ByteBuffer content, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("sendContent(buffer={},{})", (Object)BufferUtil.toDetailString(content), (Object)callback);
        }
        if (this.prepareSendContent(content.remaining(), callback)) {
            this.channelWrite(content, true, new Callback.Nested(callback){

                @Override
                public void succeeded() {
                    HttpOutput.this.onWriteComplete(true, null);
                    super.succeeded();
                }

                @Override
                public void failed(Throwable x) {
                    HttpOutput.this.onWriteComplete(true, x);
                    super.failed(x);
                }
            });
        }
    }

    public void sendContent(InputStream in, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("sendContent(stream={},{})", (Object)in, (Object)callback);
        }
        if (this.prepareSendContent(0, callback)) {
            new InputStreamWritingCB(in, callback).iterate();
        }
    }

    public void sendContent(ReadableByteChannel in, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("sendContent(channel={},{})", (Object)in, (Object)callback);
        }
        if (this.prepareSendContent(0, callback)) {
            new ReadableByteChannelWritingCB(in, callback).iterate();
        }
    }

    private boolean prepareSendContent(int len, Callback callback) {
        try (AutoLock l = this._channelState.lock();){
            if (BufferUtil.hasContent(this._aggregate)) {
                callback.failed(new IOException("cannot sendContent() after write()"));
                boolean bl = false;
                return bl;
            }
            if (this._channel.isCommitted()) {
                callback.failed(new IOException("cannot sendContent(), output already committed"));
                boolean bl = false;
                return bl;
            }
            switch (this._state) {
                case CLOSED: 
                case CLOSING: {
                    callback.failed(new EofException("Closed"));
                    boolean bl = false;
                    return bl;
                }
            }
            this._state = State.CLOSING;
            if (this._onError != null) {
                callback.failed(this._onError);
                boolean bl = false;
                return bl;
            }
            if (this._apiState != ApiState.BLOCKING) {
                throw new IllegalStateException(this.stateString());
            }
            this._apiState = ApiState.PENDING;
            if (len > 0) {
                this._written += (long)len;
            }
            boolean bl = true;
            return bl;
        }
    }

    public void sendContent(HttpContent httpContent, Callback callback) {
        InputStream in;
        block10: {
            ReadableByteChannel rbc;
            block9: {
                ByteBuffer buffer;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("sendContent(http={},{})", (Object)httpContent, (Object)callback);
                }
                ByteBuffer byteBuffer = buffer = this._channel.isUseOutputDirectByteBuffers() ? httpContent.getDirectBuffer() : null;
                if (buffer == null) {
                    buffer = httpContent.getIndirectBuffer();
                }
                if (buffer != null) {
                    this.sendContent(buffer, callback);
                    return;
                }
                rbc = null;
                try {
                    rbc = httpContent.getReadableByteChannel();
                }
                catch (Throwable x) {
                    if (!LOG.isDebugEnabled()) break block9;
                    LOG.debug("Unable to access ReadableByteChannel for content {}", (Object)httpContent, (Object)x);
                }
            }
            if (rbc != null) {
                this.sendContent(rbc, callback);
                return;
            }
            in = null;
            try {
                in = httpContent.getInputStream();
            }
            catch (Throwable x) {
                if (!LOG.isDebugEnabled()) break block10;
                LOG.debug("Unable to access InputStream for content {}", (Object)httpContent, (Object)x);
            }
        }
        if (in != null) {
            this.sendContent(in, callback);
            return;
        }
        IllegalArgumentException cause = new IllegalArgumentException("unknown content for " + httpContent);
        this._channel.abort(cause);
        callback.failed(cause);
    }

    public int getBufferSize() {
        return this._bufferSize;
    }

    public void setBufferSize(int size) {
        this._bufferSize = size;
        this._commitSize = size;
    }

    public void onFlushed(long bytes) throws IOException {
        if (this._firstByteNanoTime == -1L || this._firstByteNanoTime == Long.MAX_VALUE) {
            return;
        }
        long minDataRate = this.getHttpChannel().getHttpConfiguration().getMinResponseDataRate();
        this._flushed += bytes;
        long elapsed = NanoTime.since(this._firstByteNanoTime);
        long minFlushed = minDataRate * TimeUnit.NANOSECONDS.toMillis(elapsed) / TimeUnit.SECONDS.toMillis(1L);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Flushed bytes min/actual {}/{}", (Object)minFlushed, (Object)this._flushed);
        }
        if (this._flushed < minFlushed) {
            IOException ioe = new IOException(String.format("Response content data rate < %d B/s", minDataRate));
            this._channel.abort(ioe);
            throw ioe;
        }
    }

    public void recycle() {
        try (AutoLock l = this._channelState.lock();){
            this._state = State.OPEN;
            this._apiState = ApiState.BLOCKING;
            this._softClose = true;
            this._interceptor = this._channel;
            HttpConfiguration config = this._channel.getHttpConfiguration();
            this._bufferSize = config.getOutputBufferSize();
            this._commitSize = config.getOutputAggregationSize();
            if (this._commitSize > this._bufferSize) {
                this._commitSize = this._bufferSize;
            }
            this.releaseBuffer(null);
            this._written = 0L;
            this._writeListener = null;
            this._onError = null;
            this._firstByteNanoTime = -1L;
            this._flushed = 0L;
            this._closedCallback = null;
        }
    }

    public void resetBuffer() {
        try (AutoLock l = this._channelState.lock();){
            this._interceptor.resetBuffer();
            if (BufferUtil.hasContent(this._aggregate)) {
                BufferUtil.clear(this._aggregate);
            }
            this._written = 0L;
        }
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        boolean wake;
        if (!this._channel.getState().isAsync()) {
            throw new IllegalStateException("!ASYNC: " + this.stateString());
        }
        try (AutoLock l = this._channelState.lock();){
            if (this._apiState != ApiState.BLOCKING) {
                throw new IllegalStateException("!OPEN" + this.stateString());
            }
            this._apiState = ApiState.READY;
            this._writeListener = writeListener;
            wake = this._channel.getState().onWritePossible();
        }
        if (wake) {
            this._channel.execute(this._channel);
        }
    }

    @Override
    public boolean isReady() {
        try (AutoLock l = this._channelState.lock();){
            switch (this._apiState) {
                case BLOCKING: 
                case READY: {
                    boolean bl = true;
                    return bl;
                }
                case ASYNC: {
                    this._apiState = ApiState.READY;
                    boolean bl = true;
                    return bl;
                }
                case PENDING: {
                    this._apiState = ApiState.UNREADY;
                    boolean bl = false;
                    return bl;
                }
                case BLOCKED: 
                case UNREADY: {
                    boolean bl = false;
                    return bl;
                }
            }
            throw new IllegalStateException(this.stateString());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Throwable error = null;
        try (AutoLock l = this._channelState.lock();){
            if (this._onError != null) {
                error = this._onError;
                this._onError = null;
            }
        }
        try {
            if (error == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("onWritePossible");
                }
                this._writeListener.onWritePossible();
                return;
            }
        }
        catch (Throwable t2) {
            error = t2;
        }
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("onError", error);
            }
            this._writeListener.onError(error);
        }
        catch (Throwable t3) {
            if (LOG.isDebugEnabled()) {
                t3.addSuppressed(error);
                LOG.debug("Failed in call onError on {}", (Object)this._writeListener, (Object)t3);
            }
        }
        finally {
            IO.close(this);
        }
    }

    private String stateString() {
        return String.format("s=%s,api=%s,sc=%b,e=%s", new Object[]{this._state, this._apiState, this._softClose, this._onError});
    }

    public String toString() {
        try (AutoLock l = this._channelState.lock();){
            String string = String.format("%s@%x{%s}", this.getClass().getSimpleName(), this.hashCode(), this.stateString());
            return string;
        }
    }

    static enum ApiState {
        BLOCKING,
        BLOCKED,
        ASYNC,
        READY,
        PENDING,
        UNREADY;

    }

    static enum State {
        OPEN,
        CLOSE,
        CLOSING,
        CLOSED;

    }

    public static interface Interceptor {
        public void write(ByteBuffer var1, boolean var2, Callback var3);

        public Interceptor getNextInterceptor();

        default public void resetBuffer() throws IllegalStateException {
            Interceptor next = this.getNextInterceptor();
            if (next != null) {
                next.resetBuffer();
            }
        }
    }

    private static class WriteBlocker
    extends SharedBlockingCallback {
        private final HttpChannel _channel;

        private WriteBlocker(HttpChannel channel) {
            this._channel = channel;
        }
    }

    private class WriteCompleteCB
    implements Callback {
        private WriteCompleteCB() {
        }

        @Override
        public void succeeded() {
            HttpOutput.this.onWriteComplete(true, null);
        }

        @Override
        public void failed(Throwable x) {
            HttpOutput.this.onWriteComplete(true, x);
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return Invocable.InvocationType.NON_BLOCKING;
        }
    }

    private class AsyncFlush
    extends ChannelWriteCB {
        private volatile boolean _flushed;

        private AsyncFlush(boolean last) {
            super(last);
        }

        @Override
        protected IteratingCallback.Action process() throws Exception {
            if (BufferUtil.hasContent(HttpOutput.this._aggregate)) {
                this._flushed = true;
                HttpOutput.this.channelWrite(HttpOutput.this._aggregate, false, this);
                return IteratingCallback.Action.SCHEDULED;
            }
            if (!this._flushed) {
                this._flushed = true;
                HttpOutput.this.channelWrite(BufferUtil.EMPTY_BUFFER, false, this);
                return IteratingCallback.Action.SCHEDULED;
            }
            return IteratingCallback.Action.SUCCEEDED;
        }
    }

    private class AsyncWrite
    extends ChannelWriteCB {
        private final ByteBuffer _buffer;
        private final ByteBuffer _slice;
        private final int _len;
        private boolean _completed;

        private AsyncWrite(byte[] b, int off, int len, boolean last) {
            super(last);
            this._buffer = ByteBuffer.wrap(b, off, len);
            this._len = len;
            this._slice = this._len < HttpOutput.this.getBufferSize() ? null : this._buffer.duplicate();
        }

        private AsyncWrite(ByteBuffer buffer, boolean last) {
            super(last);
            this._buffer = buffer;
            this._len = buffer.remaining();
            this._slice = this._buffer.isDirect() || this._len < HttpOutput.this.getBufferSize() ? null : this._buffer.duplicate();
        }

        @Override
        protected IteratingCallback.Action process() throws Exception {
            if (BufferUtil.hasContent(HttpOutput.this._aggregate)) {
                this._completed = this._len == 0;
                HttpOutput.this.channelWrite(HttpOutput.this._aggregate, this._last && this._completed, this);
                return IteratingCallback.Action.SCHEDULED;
            }
            if (!this._last && HttpOutput.this._aggregate != null && this._len < HttpOutput.this.maximizeAggregateSpace() && this._len < HttpOutput.this._commitSize) {
                int position = BufferUtil.flipToFill(HttpOutput.this._aggregate);
                BufferUtil.put(this._buffer, HttpOutput.this._aggregate);
                BufferUtil.flipToFlush(HttpOutput.this._aggregate, position);
                return IteratingCallback.Action.SUCCEEDED;
            }
            if (this._buffer.hasRemaining()) {
                if (this._slice == null) {
                    this._completed = true;
                    HttpOutput.this.channelWrite(this._buffer, this._last, this);
                    return IteratingCallback.Action.SCHEDULED;
                }
                int p = this._buffer.position();
                int l = Math.min(HttpOutput.this.getBufferSize(), this._buffer.remaining());
                int pl = p + l;
                this._slice.limit(pl);
                this._buffer.position(pl);
                this._slice.position(p);
                this._completed = !this._buffer.hasRemaining();
                HttpOutput.this.channelWrite(this._slice, this._last && this._completed, this);
                return IteratingCallback.Action.SCHEDULED;
            }
            if (this._last && !this._completed) {
                this._completed = true;
                HttpOutput.this.channelWrite(BufferUtil.EMPTY_BUFFER, true, this);
                return IteratingCallback.Action.SCHEDULED;
            }
            if (LOG.isDebugEnabled() && this._completed) {
                LOG.debug("EOF of {}", (Object)this);
            }
            return IteratingCallback.Action.SUCCEEDED;
        }
    }

    private class InputStreamWritingCB
    extends NestedChannelWriteCB {
        private final InputStream _in;
        private final ByteBuffer _buffer;
        private boolean _eof;
        private boolean _closed;

        private InputStreamWritingCB(InputStream in, Callback callback) {
            super(callback, true);
            this._in = in;
            this._buffer = HttpOutput.this._channel.getByteBufferPool().acquire(HttpOutput.this.getBufferSize(), false);
        }

        @Override
        protected IteratingCallback.Action process() throws Exception {
            if (this._eof) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("EOF of {}", (Object)this);
                }
                if (!this._closed) {
                    this._closed = true;
                    HttpOutput.this._channel.getByteBufferPool().release(this._buffer);
                    IO.close(this._in);
                }
                return IteratingCallback.Action.SUCCEEDED;
            }
            int len = 0;
            while (len < this._buffer.capacity() && !this._eof) {
                int r = this._in.read(this._buffer.array(), this._buffer.arrayOffset() + len, this._buffer.capacity() - len);
                if (r < 0) {
                    this._eof = true;
                    continue;
                }
                len += r;
            }
            this._buffer.position(0);
            this._buffer.limit(len);
            HttpOutput.this._written += (long)len;
            HttpOutput.this.channelWrite(this._buffer, this._eof, this);
            return IteratingCallback.Action.SCHEDULED;
        }

        @Override
        public void onCompleteFailure(Throwable x) {
            try {
                HttpOutput.this._channel.getByteBufferPool().release(this._buffer);
            }
            finally {
                super.onCompleteFailure(x);
            }
        }
    }

    private class ReadableByteChannelWritingCB
    extends NestedChannelWriteCB {
        private final ReadableByteChannel _in;
        private final ByteBuffer _buffer;
        private boolean _eof;
        private boolean _closed;

        private ReadableByteChannelWritingCB(ReadableByteChannel in, Callback callback) {
            super(callback, true);
            this._in = in;
            this._buffer = HttpOutput.this._channel.getByteBufferPool().acquire(HttpOutput.this.getBufferSize(), HttpOutput.this._channel.isUseOutputDirectByteBuffers());
        }

        @Override
        protected IteratingCallback.Action process() throws Exception {
            if (this._eof) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("EOF of {}", (Object)this);
                }
                if (!this._closed) {
                    this._closed = true;
                    HttpOutput.this._channel.getByteBufferPool().release(this._buffer);
                    IO.close(this._in);
                }
                return IteratingCallback.Action.SUCCEEDED;
            }
            BufferUtil.clearToFill(this._buffer);
            while (this._buffer.hasRemaining() && !this._eof) {
                this._eof = this._in.read(this._buffer) < 0;
            }
            BufferUtil.flipToFlush(this._buffer, 0);
            HttpOutput.this._written += (long)this._buffer.remaining();
            HttpOutput.this.channelWrite(this._buffer, this._eof, this);
            return IteratingCallback.Action.SCHEDULED;
        }

        @Override
        public void onCompleteFailure(Throwable x) {
            HttpOutput.this._channel.getByteBufferPool().release(this._buffer);
            IO.close(this._in);
            super.onCompleteFailure(x);
        }
    }

    private abstract class NestedChannelWriteCB
    extends ChannelWriteCB {
        private final Callback _callback;

        private NestedChannelWriteCB(Callback callback, boolean last) {
            super(last);
            this._callback = callback;
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return this._callback.getInvocationType();
        }

        @Override
        protected void onCompleteSuccess() {
            try {
                super.onCompleteSuccess();
            }
            finally {
                this._callback.succeeded();
            }
        }

        @Override
        public void onCompleteFailure(Throwable e) {
            try {
                super.onCompleteFailure(e);
            }
            catch (Throwable t2) {
                if (t2 != e) {
                    e.addSuppressed(t2);
                }
            }
            finally {
                this._callback.failed(e);
            }
        }
    }

    private abstract class ChannelWriteCB
    extends IteratingCallback {
        final boolean _last;

        private ChannelWriteCB(boolean last) {
            this._last = last;
        }

        @Override
        public Invocable.InvocationType getInvocationType() {
            return Invocable.InvocationType.NON_BLOCKING;
        }

        @Override
        protected void onCompleteSuccess() {
            HttpOutput.this.onWriteComplete(this._last, null);
        }

        @Override
        public void onCompleteFailure(Throwable e) {
            HttpOutput.this.onWriteComplete(this._last, e);
        }
    }
}

