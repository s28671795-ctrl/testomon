/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jetty.server.MultiPartParser;
import org.eclipse.jetty.server.MultiParts;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.ByteArrayOutputStream2;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiPartFormInputStream {
    private static final Logger LOG = LoggerFactory.getLogger(MultiPartFormInputStream.class);
    private final AutoLock _lock = new AutoLock();
    private final MultiMap<Part> _parts = new MultiMap();
    private final EnumSet<MultiParts.NonCompliance> _nonComplianceWarnings = EnumSet.noneOf(MultiParts.NonCompliance.class);
    private final InputStream _in;
    private final MultipartConfigElement _config;
    private final File _contextTmpDir;
    private final String _contentType;
    private final int _maxParts;
    private int _numParts;
    private volatile Throwable _err;
    private volatile Path _tmpDir;
    private volatile boolean _deleteOnExit;
    private volatile boolean _writeFilesWithFilenames;
    private volatile int _bufferSize = 16384;
    private State state = State.UNPARSED;

    public EnumSet<MultiParts.NonCompliance> getNonComplianceWarnings() {
        return this._nonComplianceWarnings;
    }

    public MultiPartFormInputStream(InputStream in, String contentType, MultipartConfigElement config, File contextTmpDir) {
        this(in, contentType, config, contextTmpDir, 1000);
    }

    public MultiPartFormInputStream(InputStream in, String contentType, MultipartConfigElement config, File contextTmpDir, int maxParts) {
        this._contentType = contentType;
        if (this._contentType == null || !this._contentType.startsWith("multipart/form-data")) {
            throw new IllegalArgumentException("content type is not multipart/form-data");
        }
        this._contextTmpDir = contextTmpDir != null ? contextTmpDir : new File(System.getProperty("java.io.tmpdir"));
        this._config = config != null ? config : new MultipartConfigElement(this._contextTmpDir.getAbsolutePath());
        this._maxParts = maxParts;
        if (in instanceof ServletInputStream && ((ServletInputStream)in).isFinished()) {
            this._in = null;
            this.state = State.PARSED;
            return;
        }
        this._in = new BufferedInputStream(in);
    }

    @Deprecated
    public boolean isEmpty() {
        if (this._parts.isEmpty()) {
            return true;
        }
        for (List partList : this._parts.values()) {
            if (partList.isEmpty()) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void deleteParts() {
        try (AutoLock l = this._lock.lock();){
            switch (this.state) {
                case DELETED: 
                case DELETING: {
                    return;
                }
                case PARSING: {
                    this.state = State.DELETING;
                    return;
                }
                case UNPARSED: {
                    this.state = State.DELETED;
                    return;
                }
                case PARSED: {
                    this.state = State.DELETED;
                    break;
                }
            }
        }
        this.delete();
    }

    private void delete() {
        MultiException err = null;
        for (List parts : this._parts.values()) {
            for (Part p : parts) {
                try {
                    ((MultiPart)p).cleanUp();
                }
                catch (Exception e) {
                    if (err == null) {
                        err = new MultiException();
                    }
                    err.add(e);
                }
            }
        }
        this._parts.clear();
        if (err != null) {
            err.ifExceptionThrowRuntime();
        }
    }

    public Collection<Part> getParts() throws IOException {
        this.parse();
        this.throwIfError();
        return this._parts.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Part getPart(String name) throws IOException {
        this.parse();
        this.throwIfError();
        return this._parts.getValue(name, 0);
    }

    protected void throwIfError() throws IOException {
        if (this._err != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("MultiPart parsing failure ", this._err);
            }
            this._err.addSuppressed(new Throwable());
            if (this._err instanceof IOException) {
                throw (IOException)this._err;
            }
            if (this._err instanceof IllegalStateException) {
                throw (IllegalStateException)this._err;
            }
            throw new IllegalStateException(this._err);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    protected void parse() {
        l = this._lock.lock();
        try {
            switch (1.$SwitchMap$org$eclipse$jetty$server$MultiPartFormInputStream$State[this.state.ordinal()]) {
                case 4: {
                    this.state = State.PARSING;
                    ** break;
lbl7:
                    // 1 sources

                    break;
                }
                case 5: {
                    return;
                }
                default: {
                    this._err = new IOException(this.state.name());
                    return;
                }
            }
        }
        finally {
            if (l != null) {
                l.close();
            }
        }
        parser = null;
        try {
            block91: {
                if (StringUtil.isBlank(this._config.getLocation())) {
                    this._tmpDir = this._contextTmpDir.toPath();
                } else {
                    location = FileSystems.getDefault().getPath(this._config.getLocation(), new String[0]);
                    v0 = this._tmpDir = location.isAbsolute() != false ? location : this._contextTmpDir.toPath().resolve(location);
                }
                if (!Files.exists(this._tmpDir, new LinkOption[0])) {
                    Files.createDirectories(this._tmpDir, new FileAttribute[0]);
                }
                contentTypeBoundary = "";
                bstart = this._contentType.indexOf("boundary=");
                if (bstart >= 0) {
                    bend = this._contentType.indexOf(";", bstart);
                    bend = bend < 0 ? this._contentType.length() : bend;
                    contentTypeBoundary = QuotedStringTokenizer.unquote(MultiPartFormInputStream.value(this._contentType.substring(bstart, bend)).trim());
                }
                parser = new MultiPartParser(new Handler(), contentTypeBoundary);
                data = new byte[this._bufferSize];
                total = 0L;
                while (true) {
                    l = this._lock.lock();
                    try {
                        if (this.state != State.PARSING) {
                            this._err = new IOException(this.state.name());
                            return;
                        }
                    }
                    finally {
                        if (l != null) {
                            l.close();
                        }
                    }
                    len = this._in.read(data);
                    if (len > 0) {
                        if (this._config.getMaxRequestSize() > 0L && (total += (long)len) > this._config.getMaxRequestSize()) {
                            this._err = new IllegalStateException("Request exceeds maxRequestSize (" + this._config.getMaxRequestSize() + ")");
                            return;
                        }
                        buffer = BufferUtil.toBuffer(data);
                        buffer.limit(len);
                        if (!parser.parse(buffer, false)) {
                            if (!buffer.hasRemaining()) continue;
                            throw new IllegalStateException("Buffer did not fully consume");
                        }
                        break block91;
                    }
                    if (len == -1) break;
                }
                parser.parse(BufferUtil.EMPTY_BUFFER, true);
            }
            if (this._err != null) {
                return;
            }
            if (parser.getState() != MultiPartParser.State.END) {
                this._err = parser.getState() == MultiPartParser.State.PREAMBLE ? new IOException("Missing initial multi part boundary") : new IOException("Incomplete Multipart");
            }
            if (MultiPartFormInputStream.LOG.isDebugEnabled()) {
                MultiPartFormInputStream.LOG.debug("Parsing Complete {} err={}", (Object)parser, (Object)this._err);
            }
        }
        catch (Throwable e) {
            this._err = e;
            if (parser != null) {
                parser.parse(BufferUtil.EMPTY_BUFFER, true);
            }
        }
        finally {
            cleanup = false;
            l = this._lock.lock();
            try {
                switch (1.$SwitchMap$org$eclipse$jetty$server$MultiPartFormInputStream$State[this.state.ordinal()]) {
                    case 3: {
                        this.state = State.PARSED;
                        ** break;
lbl87:
                        // 1 sources

                        break;
                    }
                    case 2: {
                        this.state = State.DELETED;
                        cleanup = true;
                        ** break;
lbl92:
                        // 1 sources

                        break;
                    }
                    default: {
                        this._err = new IllegalStateException(this.state.name());
                        break;
                    }
                }
            }
            finally {
                if (l != null) {
                    l.close();
                }
            }
            if (cleanup) {
                this.delete();
            }
        }
    }

    @Deprecated
    public void setDeleteOnExit(boolean deleteOnExit) {
    }

    public void setWriteFilesWithFilenames(boolean writeFilesWithFilenames) {
        this._writeFilesWithFilenames = writeFilesWithFilenames;
    }

    public boolean isWriteFilesWithFilenames() {
        return this._writeFilesWithFilenames;
    }

    @Deprecated
    public boolean isDeleteOnExit() {
        return false;
    }

    private static String value(String nameEqualsValue) {
        int idx = nameEqualsValue.indexOf(61);
        String value = nameEqualsValue.substring(idx + 1).trim();
        return QuotedStringTokenizer.unquoteOnly(value);
    }

    private static String filenameValue(String nameEqualsValue) {
        int idx = nameEqualsValue.indexOf(61);
        String value = nameEqualsValue.substring(idx + 1).trim();
        if (value.matches(".??[a-z,A-Z]\\:\\\\[^\\\\].*")) {
            char last;
            char first = value.charAt(0);
            if (first == '\"' || first == '\'') {
                value = value.substring(1);
            }
            if ((last = value.charAt(value.length() - 1)) == '\"' || last == '\'') {
                value = value.substring(0, value.length() - 1);
            }
            return value;
        }
        return QuotedStringTokenizer.unquoteOnly(value, true);
    }

    public int getBufferSize() {
        return this._bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this._bufferSize = bufferSize;
    }

    private static enum State {
        UNPARSED,
        PARSING,
        PARSED,
        DELETING,
        DELETED;

    }

    public class MultiPart
    implements Part {
        protected String _name;
        protected String _filename;
        protected File _file;
        protected OutputStream _out;
        protected ByteArrayOutputStream2 _bout;
        protected String _contentType;
        protected MultiMap<String> _headers;
        protected long _size = 0L;
        protected boolean _temporary = true;

        public MultiPart(String name, String filename) {
            this._name = name;
            this._filename = filename;
        }

        public String toString() {
            return String.format("Part{n=%s,fn=%s,ct=%s,s=%d,tmp=%b,file=%s}", this._name, this._filename, this._contentType, this._size, this._temporary, this._file);
        }

        protected void setContentType(String contentType) {
            this._contentType = contentType;
        }

        protected void open() throws IOException {
            if (MultiPartFormInputStream.this.isWriteFilesWithFilenames() && this._filename != null && !this._filename.trim().isEmpty()) {
                this.createFile();
            } else {
                this._bout = new ByteArrayOutputStream2();
                this._out = this._bout;
            }
        }

        protected void close() throws IOException {
            this._out.close();
        }

        protected void write(int b) throws IOException {
            if (MultiPartFormInputStream.this._config.getMaxFileSize() > 0L && this._size + 1L > MultiPartFormInputStream.this._config.getMaxFileSize()) {
                throw new IllegalStateException("Multipart Mime part " + this._name + " exceeds max filesize");
            }
            if (MultiPartFormInputStream.this._config.getFileSizeThreshold() > 0 && this._size + 1L > (long)MultiPartFormInputStream.this._config.getFileSizeThreshold() && this._file == null) {
                this.createFile();
            }
            this._out.write(b);
            ++this._size;
        }

        protected void write(byte[] bytes, int offset, int length) throws IOException {
            if (MultiPartFormInputStream.this._config.getMaxFileSize() > 0L && this._size + (long)length > MultiPartFormInputStream.this._config.getMaxFileSize()) {
                throw new IllegalStateException("Multipart Mime part " + this._name + " exceeds max filesize");
            }
            if (MultiPartFormInputStream.this._config.getFileSizeThreshold() > 0 && this._size + (long)length > (long)MultiPartFormInputStream.this._config.getFileSizeThreshold() && this._file == null) {
                this.createFile();
            }
            this._out.write(bytes, offset, length);
            this._size += (long)length;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void write(String fileName) throws IOException {
            block10: {
                Path p = Path.of(fileName, new String[0]);
                if (!p.isAbsolute()) {
                    p = MultiPartFormInputStream.this._tmpDir.resolve(p);
                }
                if (this._file == null) {
                    this._temporary = false;
                    this._file = Files.createFile(p, new FileAttribute[0]).toFile();
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(this._file));){
                        this._bout.writeTo(bos);
                        bos.flush();
                        break block10;
                    }
                    finally {
                        this._bout = null;
                    }
                }
                this._temporary = false;
                Path src = this._file.toPath();
                Files.move(src, p, StandardCopyOption.REPLACE_EXISTING);
                this._file = p.toFile();
            }
        }

        protected void createFile() throws IOException {
            Path parent = MultiPartFormInputStream.this._tmpDir;
            Path tempFile = Files.createTempFile(parent, "MultiPart", "", new FileAttribute[0]);
            this._file = tempFile.toFile();
            OutputStream fos = Files.newOutputStream(tempFile, StandardOpenOption.WRITE);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            if (this._size > 0L && this._out != null) {
                this._out.flush();
                this._bout.writeTo(bos);
                this._out.close();
            }
            this._bout = null;
            this._out = bos;
        }

        protected void setHeaders(MultiMap<String> headers) {
            this._headers = headers;
        }

        @Override
        public String getContentType() {
            return this._contentType;
        }

        @Override
        public String getHeader(String name) {
            if (name == null) {
                return null;
            }
            return this._headers.getValue(StringUtil.asciiToLowerCase(name), 0);
        }

        @Override
        public Collection<String> getHeaderNames() {
            return this._headers.keySet();
        }

        @Override
        public Collection<String> getHeaders(String name) {
            List<String> headers = this._headers.getValues(name);
            return headers == null ? Collections.emptyList() : headers;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (this._file != null) {
                return new BufferedInputStream(new FileInputStream(this._file));
            }
            return new ByteArrayInputStream(this._bout.getBuf(), 0, this._bout.size());
        }

        @Override
        public String getSubmittedFileName() {
            return this.getContentDispositionFilename();
        }

        public byte[] getBytes() {
            if (this._bout != null) {
                return this._bout.toByteArray();
            }
            return null;
        }

        @Override
        public String getName() {
            return this._name;
        }

        @Override
        public long getSize() {
            return this._size;
        }

        @Override
        public void delete() throws IOException {
            if (this._file != null && this._file.exists() && !this._file.delete()) {
                throw new IOException("Could Not Delete File");
            }
        }

        public void cleanUp() throws IOException {
            if (this._temporary) {
                this.delete();
            }
        }

        public File getFile() {
            return this._file;
        }

        public String getContentDispositionFilename() {
            return this._filename;
        }
    }

    class Handler
    implements MultiPartParser.Handler {
        private MultiPart _part = null;
        private String contentDisposition = null;
        private String contentType = null;
        private MultiMap<String> headers = new MultiMap();

        Handler() {
        }

        @Override
        public boolean messageComplete() {
            return true;
        }

        @Override
        public void parsedField(String key, String value) {
            this.headers.put(StringUtil.asciiToLowerCase(key), value);
            if (key.equalsIgnoreCase("content-disposition")) {
                this.contentDisposition = value;
            } else if (key.equalsIgnoreCase("content-type")) {
                this.contentType = value;
            }
            if (key.equalsIgnoreCase("content-transfer-encoding") && !"8bit".equalsIgnoreCase(value) && !"binary".equalsIgnoreCase(value)) {
                MultiPartFormInputStream.this._nonComplianceWarnings.add(MultiParts.NonCompliance.TRANSFER_ENCODING);
            }
        }

        @Override
        public boolean headerComplete() {
            if (LOG.isDebugEnabled()) {
                LOG.debug("headerComplete {}", (Object)this);
            }
            try {
                boolean formData = false;
                if (this.contentDisposition == null) {
                    throw new IOException("Missing content-disposition");
                }
                QuotedStringTokenizer tok = new QuotedStringTokenizer(this.contentDisposition, ";", false, true);
                String name = null;
                String filename = null;
                while (tok.hasMoreTokens()) {
                    String t2 = tok.nextToken().trim();
                    String tl = StringUtil.asciiToLowerCase(t2);
                    if (tl.startsWith("form-data")) {
                        formData = true;
                        continue;
                    }
                    if (tl.startsWith("name=")) {
                        name = MultiPartFormInputStream.value(t2);
                        continue;
                    }
                    if (!tl.startsWith("filename=")) continue;
                    filename = MultiPartFormInputStream.filenameValue(t2);
                }
                if (!formData) {
                    throw new IOException("Part not form-data");
                }
                if (name == null) {
                    throw new IOException("No name in part");
                }
                this._part = new MultiPart(name, filename);
                this._part.setHeaders(this.headers);
                this._part.setContentType(this.contentType);
                MultiPartFormInputStream.this._parts.add(name, this._part);
                try {
                    this._part.open();
                }
                catch (IOException e) {
                    MultiPartFormInputStream.this._err = e;
                    return true;
                }
            }
            catch (Exception e) {
                MultiPartFormInputStream.this._err = e;
                return true;
            }
            return false;
        }

        @Override
        public boolean content(ByteBuffer buffer, boolean last) {
            if (this._part == null) {
                return false;
            }
            if (BufferUtil.hasContent(buffer)) {
                try {
                    this._part.write(buffer.array(), buffer.arrayOffset() + buffer.position(), buffer.remaining());
                }
                catch (IOException e) {
                    MultiPartFormInputStream.this._err = e;
                    return true;
                }
            }
            if (last) {
                try {
                    this._part.close();
                }
                catch (IOException e) {
                    MultiPartFormInputStream.this._err = e;
                    return true;
                }
            }
            return false;
        }

        @Override
        public void startPart() {
            this.reset();
            ++MultiPartFormInputStream.this._numParts;
            if (MultiPartFormInputStream.this._maxParts >= 0 && MultiPartFormInputStream.this._numParts > MultiPartFormInputStream.this._maxParts) {
                throw new IllegalStateException(String.format("Form with too many parts [%d > %d]", MultiPartFormInputStream.this._numParts, MultiPartFormInputStream.this._maxParts));
            }
        }

        @Override
        public void earlyEOF() {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Early EOF {}", (Object)MultiPartFormInputStream.this);
            }
            try {
                if (this._part != null) {
                    this._part.close();
                }
            }
            catch (IOException e) {
                LOG.warn("part could not be closed", e);
            }
        }

        public void reset() {
            this._part = null;
            this.contentDisposition = null;
            this.contentType = null;
            this.headers = new MultiMap();
        }
    }
}

