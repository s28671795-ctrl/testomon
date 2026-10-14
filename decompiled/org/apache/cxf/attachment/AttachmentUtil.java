/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.activation.CommandInfo;
import javax.activation.CommandMap;
import javax.activation.DataContentHandler;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.activation.URLDataSource;
import org.apache.cxf.attachment.AttachmentDataSource;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.attachment.Base64DecoderStream;
import org.apache.cxf.attachment.ByteDataSource;
import org.apache.cxf.attachment.ContentDisposition;
import org.apache.cxf.attachment.ImageDataContentHandler;
import org.apache.cxf.attachment.LazyAttachmentCollection;
import org.apache.cxf.attachment.LazyDataSource;
import org.apache.cxf.attachment.QuotedPrintableDecoderStream;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.FileUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;

public final class AttachmentUtil {
    public static final String BODY_ATTACHMENT_ID = "root.message@cxf.apache.org";
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final String ATT_UUID = UUID.randomUUID().toString();
    private static final Random BOUND_RANDOM = new Random();
    private static final CommandMap DEFAULT_COMMAND_MAP = CommandMap.getDefaultCommandMap();
    private static final MailcapCommandMap COMMAND_MAP = new EnhancedMailcapCommandMap();

    private AttachmentUtil() {
    }

    public static CommandMap getCommandMap() {
        return COMMAND_MAP;
    }

    public static boolean isMtomEnabled(Message message) {
        return MessageUtils.getContextualBoolean(message, "mtom-enabled", false);
    }

    public static void setStreamedAttachmentProperties(Message message, CachedOutputStream bos) throws IOException {
        Object threshold;
        Object directory = message.getContextualProperty("attachment-directory");
        if (directory != null) {
            if (directory instanceof File) {
                bos.setOutputDir((File)directory);
            } else {
                bos.setOutputDir(new File((String)directory));
            }
        }
        if ((threshold = message.getContextualProperty("attachment-memory-threshold")) != null) {
            if (threshold instanceof Long) {
                bos.setThreshold((Long)threshold);
            } else {
                bos.setThreshold(Long.parseLong((String)threshold));
            }
        } else if (!CachedOutputStream.isThresholdSysPropSet()) {
            bos.setThreshold(102400L);
        }
        Object maxSize = message.getContextualProperty("attachment-max-size");
        if (maxSize != null) {
            if (maxSize instanceof Long) {
                bos.setMaxSize((Long)maxSize);
            } else {
                bos.setMaxSize(Long.parseLong((String)maxSize));
            }
        }
    }

    public static String createContentID(String ns) throws UnsupportedEncodingException {
        String cid = "cxf.apache.org";
        if (ns != null && !ns.isEmpty()) {
            try {
                URI uri = new URI(ns);
                String host2 = uri.getHost();
                cid = host2 != null ? host2 : ns;
            }
            catch (Exception e) {
                cid = ns;
            }
        }
        return ATT_UUID + '-' + Integer.toString(COUNTER.incrementAndGet()) + '@' + URLEncoder.encode(cid, StandardCharsets.UTF_8.name());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getUniqueBoundaryValue() {
        Random random = BOUND_RANDOM;
        synchronized (random) {
            long mostSigBits = BOUND_RANDOM.nextLong();
            long leastSigBits = BOUND_RANDOM.nextLong();
        }
        mostSigBits &= 0xFFFFFFFFFFFF0FFFL;
        leastSigBits &= 0x3FFFFFFFFFFFFFFFL;
        UUID result = new UUID(mostSigBits |= 0x4000L, leastSigBits |= Long.MIN_VALUE);
        return "uuid:" + result.toString();
    }

    public static String getAttachmentPartHeader(Attachment att) {
        String id;
        StringBuilder buffer = new StringBuilder(200);
        buffer.append(HttpHeaderHelper.getHeaderKey("Content-Type") + ": " + att.getDataHandler().getContentType() + ";\r\n");
        if (att.isXOP()) {
            buffer.append("Content-Transfer-Encoding: binary\r\n");
        }
        if ((id = att.getId()).charAt(0) == '<') {
            id = id.substring(1, id.length() - 1);
        }
        buffer.append("Content-ID: <" + id + ">\r\n\r\n");
        return buffer.toString();
    }

    public static Map<String, DataHandler> getDHMap(Collection<Attachment> attachments) {
        Map<Object, Object> dataHandlers = null;
        if (attachments != null) {
            dataHandlers = attachments instanceof LazyAttachmentCollection ? ((LazyAttachmentCollection)attachments).createDataHandlerMap() : new DHMap(attachments);
        }
        return dataHandlers == null ? new LinkedHashMap() : dataHandlers;
    }

    public static String cleanContentId(String id) {
        if (id != null) {
            if (id.startsWith("<")) {
                id = id.substring(1, id.length() - 1);
            }
            if (id.startsWith("cid:")) {
                id = id.substring(4);
            }
            try {
                id = URLDecoder.decode(id, StandardCharsets.UTF_8.name());
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        if (id == null) {
            id = BODY_ATTACHMENT_ID;
        }
        return id;
    }

    static String getHeaderValue(List<String> v) {
        if (v != null && !v.isEmpty()) {
            return v.get(0);
        }
        return null;
    }

    static String getHeaderValue(List<String> v, String delim) {
        if (v != null && !v.isEmpty()) {
            return String.join((CharSequence)delim, v);
        }
        return null;
    }

    static String getHeader(Map<String, List<String>> headers, String h2) {
        return AttachmentUtil.getHeaderValue(headers.get(h2));
    }

    static String getHeader(Map<String, List<String>> headers, String h2, String delim) {
        return AttachmentUtil.getHeaderValue(headers.get(h2), delim);
    }

    public static Attachment createAttachment(InputStream stream, Map<String, List<String>> headers) throws IOException {
        InputStream ins;
        String id = AttachmentUtil.cleanContentId(AttachmentUtil.getHeader(headers, "Content-ID"));
        AttachmentImpl att = new AttachmentImpl(id);
        String ct = AttachmentUtil.getHeader(headers, "Content-Type");
        String cd2 = AttachmentUtil.getHeader(headers, "Content-Disposition");
        String fileName = AttachmentUtil.getContentDispositionFileName(cd2);
        String encoding = null;
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            String name = e.getKey();
            if ("Content-Transfer-Encoding".equalsIgnoreCase(name) && "binary".equalsIgnoreCase(encoding = AttachmentUtil.getHeader(headers, name))) {
                att.setXOP(true);
            }
            att.setHeader(name, AttachmentUtil.getHeaderValue(e.getValue()));
        }
        if (encoding == null) {
            encoding = "binary";
        }
        if ((ins = AttachmentUtil.decode(stream, encoding)) != stream) {
            headers.remove("Content-Transfer-Encoding");
        }
        AttachmentDataSource source2 = new AttachmentDataSource(ct, ins);
        if (!StringUtils.isEmpty(fileName)) {
            source2.setName(FileUtils.stripPath(fileName));
        }
        att.setDataHandler(new DataHandler(source2));
        return att;
    }

    static String getContentDispositionFileName(String cd2) {
        if (StringUtils.isEmpty(cd2)) {
            return null;
        }
        ContentDisposition c = new ContentDisposition(cd2);
        String s2 = c.getParameter("filename");
        if (s2 == null) {
            s2 = c.getParameter("name");
        }
        return s2;
    }

    public static InputStream decode(InputStream in, String encoding) throws IOException {
        if (encoding == null) {
            return in;
        }
        if ("binary".equals(encoding = encoding.toLowerCase()) || "7bit".equals(encoding) || "8bit".equals(encoding)) {
            return in;
        }
        if ("base64".equals(encoding)) {
            return new Base64DecoderStream(in);
        }
        if ("quoted-printable".equals(encoding)) {
            return new QuotedPrintableDecoderStream(in);
        }
        throw new IOException("Unknown encoding " + encoding);
    }

    public static boolean isTypeSupported(String contentType, List<String> types) {
        if (contentType == null) {
            return false;
        }
        contentType = contentType.toLowerCase();
        for (String s2 : types) {
            if (contentType.indexOf(s2) == -1) continue;
            return true;
        }
        return false;
    }

    public static Attachment createMtomAttachment(boolean isXop, String mimeType, String elementNS, byte[] data, int offset, int length, int threshold) {
        String id;
        if (!isXop || length <= threshold) {
            return null;
        }
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        ByteDataSource source2 = new ByteDataSource(data, offset, length);
        source2.setContentType(mimeType);
        DataHandler handler = new DataHandler(source2);
        try {
            id = AttachmentUtil.createContentID(elementNS);
        }
        catch (UnsupportedEncodingException e) {
            throw new Fault(e);
        }
        AttachmentImpl att = new AttachmentImpl(id, handler);
        att.setXOP(isXop);
        return att;
    }

    public static Attachment createMtomAttachmentFromDH(boolean isXop, DataHandler handler, String elementNS, int threshold) {
        String id;
        Object file;
        if (!isXop) {
            return null;
        }
        try {
            DataSource ds = handler.getDataSource();
            if (ds instanceof FileDataSource) {
                FileDataSource fds = (FileDataSource)ds;
                file = fds.getFile();
                if (((File)file).length() < (long)threshold) {
                    return null;
                }
            } else if (ds.getClass().getName().endsWith("ObjectDataSource")) {
                Object o = handler.getContent();
                if (o instanceof String && ((String)o).length() < threshold) {
                    return null;
                }
                if (o instanceof byte[] && ((byte[])o).length < threshold) {
                    return null;
                }
            }
        }
        catch (IOException ds) {
            // empty catch block
        }
        try {
            id = AttachmentUtil.createContentID(elementNS);
        }
        catch (UnsupportedEncodingException e) {
            throw new Fault(e);
        }
        AttachmentImpl att = new AttachmentImpl(id, handler);
        if (!StringUtils.isEmpty(handler.getName())) {
            file = handler.getName();
            File f = new File((String)file);
            if (f.exists() && f.isFile()) {
                file = f.getName();
            }
            att.setHeader("Content-Disposition", "attachment;name=\"" + (String)file + "\"");
        }
        att.setXOP(isXop);
        return att;
    }

    public static DataSource getAttachmentDataSource(String contentId, Collection<Attachment> atts) {
        if (contentId.startsWith("cid:")) {
            try {
                contentId = URLDecoder.decode(contentId.substring(4), StandardCharsets.UTF_8.name());
            }
            catch (UnsupportedEncodingException ue) {
                contentId = contentId.substring(4);
            }
            return AttachmentUtil.loadDataSource(contentId, atts);
        }
        if (contentId.indexOf("://") == -1) {
            return AttachmentUtil.loadDataSource(contentId, atts);
        }
        try {
            return new URLDataSource(new URL(contentId));
        }
        catch (MalformedURLException e) {
            throw new Fault(e);
        }
    }

    private static DataSource loadDataSource(String contentId, Collection<Attachment> atts) {
        return new LazyDataSource(contentId, atts);
    }

    static {
        COMMAND_MAP.addMailcap("image/*;;x-java-content-handler=" + ImageDataContentHandler.class.getName());
    }

    static class DHMap
    extends AbstractMap<String, DataHandler> {
        final Collection<Attachment> list;

        DHMap(Collection<Attachment> l) {
            this.list = l;
        }

        @Override
        public Set<Map.Entry<String, DataHandler>> entrySet() {
            return new AbstractSet<Map.Entry<String, DataHandler>>(){

                @Override
                public Iterator<Map.Entry<String, DataHandler>> iterator() {
                    final Iterator<Attachment> it = list.iterator();
                    return new Iterator<Map.Entry<String, DataHandler>>(){

                        @Override
                        public boolean hasNext() {
                            return it.hasNext();
                        }

                        @Override
                        public Map.Entry<String, DataHandler> next() {
                            final Attachment a = (Attachment)it.next();
                            return new Map.Entry<String, DataHandler>(){

                                @Override
                                public String getKey() {
                                    return a.getId();
                                }

                                @Override
                                public DataHandler getValue() {
                                    return a.getDataHandler();
                                }

                                @Override
                                public DataHandler setValue(DataHandler value) {
                                    return null;
                                }
                            };
                        }

                        @Override
                        public void remove() {
                            it.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return list.size();
                }
            };
        }

        @Override
        public DataHandler put(String key, DataHandler value) {
            Iterator<Attachment> i = this.list.iterator();
            DataHandler ret = null;
            while (i.hasNext()) {
                Attachment a = i.next();
                if (!a.getId().equals(key)) continue;
                i.remove();
                ret = a.getDataHandler();
                break;
            }
            this.list.add(new AttachmentImpl(key, value));
            return ret;
        }
    }

    static final class EnhancedMailcapCommandMap
    extends MailcapCommandMap {
        EnhancedMailcapCommandMap() {
        }

        @Override
        public synchronized DataContentHandler createDataContentHandler(String mimeType) {
            DataContentHandler dch = super.createDataContentHandler(mimeType);
            if (dch == null) {
                dch = DEFAULT_COMMAND_MAP.createDataContentHandler(mimeType);
            }
            return dch;
        }

        @Override
        public DataContentHandler createDataContentHandler(String mimeType, DataSource ds) {
            DataContentHandler dch = super.createDataContentHandler(mimeType);
            if (dch == null) {
                dch = DEFAULT_COMMAND_MAP.createDataContentHandler(mimeType, ds);
            }
            return dch;
        }

        @Override
        public synchronized CommandInfo[] getAllCommands(String mimeType) {
            CommandInfo[] commands = super.getAllCommands(mimeType);
            CommandInfo[] defaultCommands = DEFAULT_COMMAND_MAP.getAllCommands(mimeType);
            ArrayList<CommandInfo> cmdList = new ArrayList<CommandInfo>(Arrays.asList(commands));
            for (CommandInfo defCmdInfo : defaultCommands) {
                String defCmdName = defCmdInfo.getCommandName();
                boolean cmdNameExist = false;
                for (CommandInfo cmdInfo : commands) {
                    if (!cmdInfo.getCommandName().equals(defCmdName)) continue;
                    cmdNameExist = true;
                    break;
                }
                if (cmdNameExist) continue;
                cmdList.add(defCmdInfo);
            }
            CommandInfo[] allCommandArray = new CommandInfo[]{};
            return cmdList.toArray(allCommandArray);
        }

        @Override
        public synchronized CommandInfo getCommand(String mimeType, String cmdName) {
            CommandInfo cmdInfo = super.getCommand(mimeType, cmdName);
            if (cmdInfo == null) {
                cmdInfo = DEFAULT_COMMAND_MAP.getCommand(mimeType, cmdName);
            }
            return cmdInfo;
        }

        @Override
        public synchronized String[] getMimeTypes() {
            String[] mimeTypes = super.getMimeTypes();
            String[] defMimeTypes = DEFAULT_COMMAND_MAP.getMimeTypes();
            HashSet mimeTypeSet = new HashSet();
            Collections.addAll(mimeTypeSet, mimeTypes);
            Collections.addAll(mimeTypeSet, defMimeTypes);
            String[] mimeArray = new String[]{};
            return mimeTypeSet.toArray(mimeArray);
        }
    }
}

