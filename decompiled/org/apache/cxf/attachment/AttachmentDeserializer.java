/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataSource;
import org.apache.cxf.attachment.AttachmentDataSource;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.attachment.DelegatingInputStream;
import org.apache.cxf.attachment.HeaderSizeExceededException;
import org.apache.cxf.attachment.LazyAttachmentCollection;
import org.apache.cxf.attachment.MimeBodyPartInputStream;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;

public class AttachmentDeserializer {
    public static final String ATTACHMENT_PART_HEADERS = AttachmentDeserializer.class.getName() + ".headers";
    public static final String ATTACHMENT_DIRECTORY = "attachment-directory";
    public static final String ATTACHMENT_MEMORY_THRESHOLD = "attachment-memory-threshold";
    public static final String ATTACHMENT_MAX_SIZE = "attachment-max-size";
    public static final String ATTACHMENT_MAX_COUNT = "attachment-max-count";
    public static final String ATTACHMENT_MAX_HEADER_SIZE = "attachment-max-header-size";
    public static final int DEFAULT_MAX_HEADER_SIZE = SystemPropertyAction.getInteger("org.apache.cxf.attachment-max-header-size", 300);
    public static final int THRESHOLD = 102400;
    private static final Pattern CONTENT_TYPE_BOUNDARY_PATTERN = Pattern.compile("boundary=\"?([^\";]*)");
    private static final Pattern INPUT_STREAM_BOUNDARY_PATTERN = Pattern.compile("^--(\\S*)$", 8);
    private static final Logger LOG = LogUtils.getL7dLogger(AttachmentDeserializer.class);
    private static final int PUSHBACK_AMOUNT = 2048;
    private boolean lazyLoading = true;
    private PushbackInputStream stream;
    private int createCount;
    private int closedCount;
    private boolean closed;
    private byte[] boundary;
    private String contentType;
    private LazyAttachmentCollection attachments;
    private Message message;
    private InputStream body;
    private Set<DelegatingInputStream> loaded = new HashSet<DelegatingInputStream>();
    private List<String> supportedTypes;
    private int maxHeaderLength = DEFAULT_MAX_HEADER_SIZE;

    public AttachmentDeserializer(Message message) {
        this(message, Collections.singletonList("multipart/related"));
    }

    public AttachmentDeserializer(Message message, List<String> supportedTypes) {
        this.message = message;
        this.supportedTypes = supportedTypes;
        this.maxHeaderLength = MessageUtils.getContextualInteger(message, ATTACHMENT_MAX_HEADER_SIZE, DEFAULT_MAX_HEADER_SIZE);
    }

    public void initializeAttachments() throws IOException {
        this.initializeRootMessage();
        Object maxCountProperty = this.message.getContextualProperty(ATTACHMENT_MAX_COUNT);
        int maxCount = 50;
        if (maxCountProperty != null) {
            maxCount = maxCountProperty instanceof Integer ? (Integer)maxCountProperty : Integer.parseInt((String)maxCountProperty);
        }
        this.attachments = new LazyAttachmentCollection(this, maxCount);
        this.message.setAttachments(this.attachments);
    }

    protected void initializeRootMessage() throws IOException {
        this.contentType = (String)this.message.get("Content-Type");
        if (this.contentType == null) {
            throw new IllegalStateException("Content-Type can not be empty!");
        }
        if (this.message.getContent(InputStream.class) == null) {
            throw new IllegalStateException("An InputStream must be provided!");
        }
        if (AttachmentUtil.isTypeSupported(this.contentType.toLowerCase(), this.supportedTypes)) {
            MimeBodyPartInputStream mmps;
            InputStream ins;
            String cs;
            String boundaryString = this.findBoundaryFromContentType(this.contentType);
            if (null == boundaryString) {
                boundaryString = this.findBoundaryFromInputStream();
            }
            if (null == boundaryString) {
                throw new IOException("Couldn't determine the boundary from the message!");
            }
            this.boundary = boundaryString.getBytes("utf-8");
            this.stream = new PushbackInputStream(this.message.getContent(InputStream.class), 2048);
            if (!AttachmentDeserializer.readTillFirstBoundary(this.stream, this.boundary)) {
                throw new IOException("Couldn't find MIME boundary: " + boundaryString);
            }
            Map<String, List<String>> ih = this.loadPartHeaders(this.stream);
            this.message.put(ATTACHMENT_PART_HEADERS, ih);
            String val = AttachmentUtil.getHeader(ih, "Content-Type", "; ");
            if (!StringUtils.isEmpty(val) && !StringUtils.isEmpty(cs = HttpHeaderHelper.findCharset(val))) {
                this.message.put(Message.ENCODING, HttpHeaderHelper.mapCharset(cs));
            }
            if ((ins = AttachmentUtil.decode(mmps = new MimeBodyPartInputStream(this.stream, this.boundary, 2048), val = AttachmentUtil.getHeader(ih, "Content-Transfer-Encoding"))) != mmps) {
                ih.remove("Content-Transfer-Encoding");
            }
            this.body = new DelegatingInputStream(ins, this);
            ++this.createCount;
            this.message.setContent(InputStream.class, this.body);
        }
    }

    private String findBoundaryFromContentType(String ct) throws IOException {
        Matcher m4 = CONTENT_TYPE_BOUNDARY_PATTERN.matcher(ct);
        return m4.find() ? "--" + m4.group(1) : null;
    }

    private String findBoundaryFromInputStream() throws IOException {
        int i;
        InputStream is = this.message.getContent(InputStream.class);
        PushbackInputStream in = new PushbackInputStream(is, 4096);
        byte[] buf = new byte[2048];
        int len = i = in.read(buf);
        while (i > 0 && len < buf.length) {
            i = in.read(buf, len, buf.length - len);
            if (i <= 0) continue;
            len += i;
        }
        String msg = IOUtils.newStringFromBytes(buf, 0, len);
        in.unread(buf, 0, len);
        this.message.setContent(InputStream.class, in);
        Matcher m4 = INPUT_STREAM_BOUNDARY_PATTERN.matcher(msg);
        return m4.find() ? "--" + m4.group(1) : null;
    }

    public AttachmentImpl readNext() throws IOException {
        this.cacheStreamedAttachments();
        if (this.closed) {
            return null;
        }
        int v = this.stream.read();
        if (v == -1) {
            return null;
        }
        this.stream.unread(v);
        Map<String, List<String>> headers = this.loadPartHeaders(this.stream);
        return (AttachmentImpl)this.createAttachment(headers);
    }

    private void cacheStreamedAttachments() throws IOException {
        if (this.body instanceof DelegatingInputStream && !((DelegatingInputStream)this.body).isClosed()) {
            this.cache((DelegatingInputStream)this.body);
        }
        ArrayList<Attachment> atts = new ArrayList<Attachment>(this.attachments.getLoadedAttachments());
        for (Attachment a : atts) {
            DataSource s2 = a.getDataHandler().getDataSource();
            if (s2 instanceof AttachmentDataSource) {
                AttachmentDataSource ads = (AttachmentDataSource)s2;
                if (ads.isCached()) continue;
                ads.cache(this.message);
                continue;
            }
            if (!(s2.getInputStream() instanceof DelegatingInputStream)) continue;
            this.cache((DelegatingInputStream)s2.getInputStream());
        }
    }

    private void cache(DelegatingInputStream input) throws IOException {
        if (this.loaded.contains(input)) {
            return;
        }
        this.loaded.add(input);
        InputStream origIn = input.getInputStream();
        try (CachedOutputStream out = new CachedOutputStream();){
            AttachmentUtil.setStreamedAttachmentProperties(this.message, out);
            IOUtils.copy(input, out);
            input.setInputStream(out.getInputStream());
            origIn.close();
        }
    }

    private static boolean readTillFirstBoundary(PushbackInputStream pushbackInStream, byte[] boundary) throws IOException {
        int value = pushbackInStream.read();
        pushbackInStream.unread(value);
        while (value != -1) {
            int boundaryIndex;
            value = pushbackInStream.read();
            if ((byte)value != boundary[0]) continue;
            for (boundaryIndex = 0; value != -1 && boundaryIndex < boundary.length && (byte)value == boundary[boundaryIndex]; ++boundaryIndex) {
                value = pushbackInStream.read();
                if (value != -1) continue;
                throw new IOException("Unexpected End while searching for first Mime Boundary");
            }
            if (boundaryIndex != boundary.length) continue;
            if (value == 13) {
                pushbackInStream.read();
            }
            return true;
        }
        return false;
    }

    private Attachment createAttachment(Map<String, List<String>> headers) throws IOException {
        DelegatingInputStream partStream = new DelegatingInputStream(new MimeBodyPartInputStream(this.stream, this.boundary, 2048), this);
        ++this.createCount;
        return AttachmentUtil.createAttachment(partStream, headers);
    }

    public boolean isLazyLoading() {
        return this.lazyLoading;
    }

    public void setLazyLoading(boolean lazyLoading) {
        this.lazyLoading = lazyLoading;
    }

    public void markClosed(DelegatingInputStream delegatingInputStream) throws IOException {
        ++this.closedCount;
        if (this.closedCount == this.createCount && !this.attachments.hasNext(false)) {
            int x = this.stream.read();
            while (x != -1) {
                x = this.stream.read();
            }
            this.stream.close();
            this.closed = true;
        }
    }

    public boolean hasNext() throws IOException {
        this.cacheStreamedAttachments();
        if (this.closed) {
            return false;
        }
        int v = this.stream.read();
        if (v == -1) {
            return false;
        }
        this.stream.unread(v);
        return true;
    }

    private Map<String, List<String>> loadPartHeaders(InputStream in) throws IOException {
        StringBuilder buffer = new StringBuilder(128);
        StringBuilder b = new StringBuilder(128);
        TreeMap<String, List<String>> heads = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
        while (this.readLine(in, b)) {
            char c = b.charAt(0);
            if (c == ' ' || c == '\t') {
                if (buffer.length() == 0) continue;
                buffer.append("\r\n");
                buffer.append((CharSequence)b);
                continue;
            }
            if (buffer.length() > 0) {
                this.addHeaderLine(heads, buffer);
                buffer.setLength(0);
            }
            buffer.append((CharSequence)b);
        }
        if (buffer.length() > 0) {
            this.addHeaderLine(heads, buffer);
        }
        return heads;
    }

    private boolean readLine(InputStream in, StringBuilder buffer) throws IOException {
        int c;
        if (buffer.length() != 0) {
            buffer.setLength(0);
        }
        while ((c = in.read()) != -1 && c != 10) {
            if (c == 13) continue;
            buffer.append((char)c);
            if (buffer.length() <= this.maxHeaderLength) continue;
            LOG.fine("The attachment header size has exceeded the configured parameter: " + this.maxHeaderLength);
            throw new HeaderSizeExceededException();
        }
        return buffer.length() != 0;
    }

    private void addHeaderLine(Map<String, List<String>> heads, StringBuilder line) {
        String name;
        int size = line.length();
        if (size == 0) {
            return;
        }
        int separator = line.indexOf(":");
        String value = "";
        if (separator == -1) {
            name = line.toString().trim();
        } else {
            char ch;
            name = line.substring(0, separator);
            ++separator;
            while (separator < size && ((ch = line.charAt(separator)) == ' ' || ch == '\t' || ch == '\r' || ch == '\n')) {
                ++separator;
            }
            value = line.substring(separator);
        }
        List<String> v = heads.get(name);
        if (v == null) {
            v = new ArrayList<String>(1);
            heads.put(name, v);
        }
        v.add(value);
    }
}

