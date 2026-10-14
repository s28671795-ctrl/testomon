/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.activation.DataHandler;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;

public class AttachmentSerializer {
    private static final String DEFAULT_MULTIPART_TYPE = "multipart/related";
    private String contentTransferEncoding = "binary";
    private Message message;
    private String bodyBoundary;
    private OutputStream out;
    private String encoding;
    private String multipartType;
    private Map<String, List<String>> rootHeaders = Collections.emptyMap();
    private boolean xop = true;
    private boolean writeOptionalTypeParameters = true;

    public AttachmentSerializer(Message messageParam) {
        this.message = messageParam;
    }

    public AttachmentSerializer(Message messageParam, String multipartType, boolean writeOptionalTypeParameters, Map<String, List<String>> headers) {
        this.message = messageParam;
        this.multipartType = multipartType;
        this.writeOptionalTypeParameters = writeOptionalTypeParameters;
        this.rootHeaders = headers;
    }

    public void writeProlog() throws IOException {
        boolean xopOrMultipartRelated;
        this.bodyBoundary = AttachmentUtil.getUniqueBoundaryValue();
        String bodyCt = (String)this.message.get("Content-Type");
        String bodyCtParams = null;
        String bodyCtParamsEscaped = null;
        if (bodyCt.indexOf(59) != -1) {
            int pos = bodyCt.indexOf(59);
            bodyCtParams = bodyCt.substring(pos);
            bodyCtParamsEscaped = AttachmentSerializer.escapeQuotes(bodyCtParams);
            bodyCt = bodyCt.substring(0, pos);
        }
        String requestMimeType = this.multipartType == null ? DEFAULT_MULTIPART_TYPE : this.multipartType;
        StringBuilder ct = new StringBuilder(32);
        ct.append(requestMimeType);
        boolean bl = xopOrMultipartRelated = this.xop || DEFAULT_MULTIPART_TYPE.equalsIgnoreCase(requestMimeType) || DEFAULT_MULTIPART_TYPE.startsWith(requestMimeType);
        if (xopOrMultipartRelated && requestMimeType.indexOf("type=") == -1) {
            if (this.xop) {
                ct.append("; type=\"application/xop+xml\"");
            } else {
                ct.append("; type=\"").append(bodyCt).append('\"');
            }
        }
        ct.append("; boundary=\"").append(this.bodyBoundary).append('\"');
        String rootContentId = this.getHeaderValue("Content-ID", "root.message@cxf.apache.org");
        if (xopOrMultipartRelated) {
            ct.append("; start=\"<").append(AttachmentSerializer.checkAngleBrackets(rootContentId)).append(">\"");
        }
        if (this.writeOptionalTypeParameters || this.xop) {
            ct.append("; start-info=\"").append(bodyCt);
            if (bodyCtParamsEscaped != null) {
                ct.append(bodyCtParamsEscaped);
            }
            ct.append('\"');
        }
        this.message.put("Content-Type", ct.toString());
        this.out = this.message.getContent(OutputStream.class);
        this.encoding = (String)this.message.get(Message.ENCODING);
        if (this.encoding == null) {
            this.encoding = StandardCharsets.UTF_8.name();
        }
        StringWriter writer = new StringWriter();
        writer.write("\r\n");
        writer.write("--");
        writer.write(this.bodyBoundary);
        StringBuilder mimeBodyCt = new StringBuilder();
        String bodyType = this.getHeaderValue("Content-Type", null);
        if (bodyType == null) {
            mimeBodyCt.append(this.xop ? "application/xop+xml" : bodyCt).append("; charset=").append(this.encoding);
            if (this.xop) {
                mimeBodyCt.append("; type=\"").append(bodyCt);
                if (bodyCtParamsEscaped != null) {
                    mimeBodyCt.append(bodyCtParamsEscaped);
                }
                mimeBodyCt.append('\"');
            } else if (bodyCtParams != null) {
                mimeBodyCt.append(bodyCtParams);
            }
        } else {
            mimeBodyCt.append(bodyType);
        }
        this.writeHeaders(mimeBodyCt.toString(), rootContentId, this.rootHeaders, writer);
        this.out.write(writer.getBuffer().toString().getBytes(this.encoding));
    }

    private static String escapeQuotes(String s2) {
        return s2.indexOf(34) != 0 ? s2.replace("\"", "\\\"") : s2;
    }

    public void setContentTransferEncoding(String cte) {
        this.contentTransferEncoding = cte;
    }

    private String getHeaderValue(String name, String defaultValue) {
        List<String> value = this.rootHeaders.get(name);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.size(); ++i) {
            sb.append(value.get(i));
            if (i + 1 >= value.size()) continue;
            sb.append(',');
        }
        return sb.toString();
    }

    private void writeHeaders(String contentType, String attachmentId, Map<String, List<String>> headers, Writer writer) throws IOException {
        writer.write("\r\nContent-Type: ");
        writer.write(contentType);
        writer.write("\r\nContent-Transfer-Encoding: " + this.contentTransferEncoding + "\r\n");
        if (attachmentId != null) {
            attachmentId = AttachmentSerializer.checkAngleBrackets(attachmentId);
            writer.write("Content-ID: <");
            writer.write(URLDecoder.decode(attachmentId, StandardCharsets.UTF_8.name()));
            writer.write(">\r\n");
        }
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String name = entry.getKey();
            if ("Content-Type".equalsIgnoreCase(name) || "Content-ID".equalsIgnoreCase(name) || "Content-Transfer-Encoding".equalsIgnoreCase(name)) continue;
            writer.write(name);
            writer.write(": ");
            List<String> values2 = entry.getValue();
            for (int i = 0; i < values2.size(); ++i) {
                writer.write(values2.get(i));
                if (i + 1 >= values2.size()) continue;
                writer.write(",");
            }
            writer.write("\r\n");
        }
        writer.write("\r\n");
    }

    private static String checkAngleBrackets(String value) {
        if (value.charAt(0) == '<' && value.charAt(value.length() - 1) == '>') {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    public void writeAttachments() throws IOException {
        if (this.message.getAttachments() != null) {
            for (Attachment a : this.message.getAttachments()) {
                StringWriter writer = new StringWriter();
                writer.write("\r\n--");
                writer.write(this.bodyBoundary);
                Map<String, List<String>> headers = null;
                Iterator<String> it = a.getHeaderNames();
                if (it.hasNext()) {
                    headers = new LinkedHashMap();
                    while (it.hasNext()) {
                        String key = it.next();
                        headers.put(key, Collections.singletonList(a.getHeader(key)));
                    }
                } else {
                    headers = Collections.emptyMap();
                }
                DataHandler handler = a.getDataHandler();
                handler.setCommandMap(AttachmentUtil.getCommandMap());
                this.writeHeaders(handler.getContentType(), a.getId(), headers, writer);
                this.out.write(writer.getBuffer().toString().getBytes(this.encoding));
                if ("base64".equals(this.contentTransferEncoding)) {
                    InputStream inputStream2 = handler.getInputStream();
                    Throwable throwable = null;
                    try {
                        this.encodeBase64(inputStream2, this.out, 4096);
                        continue;
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (inputStream2 == null) continue;
                        if (throwable != null) {
                            try {
                                inputStream2.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        inputStream2.close();
                        continue;
                    }
                }
                handler.writeTo(this.out);
            }
        }
        StringWriter writer = new StringWriter();
        writer.write("\r\n--");
        writer.write(this.bodyBoundary);
        writer.write("--");
        this.out.write(writer.getBuffer().toString().getBytes(this.encoding));
        this.out.flush();
    }

    private int encodeBase64(InputStream input, OutputStream output, int bufferSize) throws IOException {
        int avail = input.available();
        if (avail > 262143) {
            avail = 262143;
        }
        if (avail > bufferSize) {
            bufferSize = avail;
        }
        byte[] buffer = new byte[bufferSize];
        int n = input.read(buffer);
        int total = 0;
        while (-1 != n) {
            int left;
            if (n == 0) {
                throw new IOException("0 bytes read in violation of InputStream.read(byte[])");
            }
            if ((n -= (left = n % 3)) > 0) {
                Base64Utility.encodeAndStream(buffer, 0, n, output);
                total += n;
            }
            if (left != 0) {
                for (int x = 0; x < left; ++x) {
                    buffer[x] = buffer[n + x];
                }
                n = input.read(buffer, left, buffer.length - left);
                if (n != -1) continue;
                Base64Utility.encodeAndStream(buffer, 0, left, output);
                total += left;
                continue;
            }
            n = input.read(buffer);
        }
        return total;
    }

    public boolean isXop() {
        return this.xop;
    }

    public void setXop(boolean xop) {
        this.xop = xop;
    }
}

