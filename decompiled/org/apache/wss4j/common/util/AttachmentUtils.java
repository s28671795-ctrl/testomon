/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.mail.internet.MimeUtility;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.encryption.XMLCipherUtil;
import org.apache.xml.security.stax.impl.util.MultiInputStream;
import org.apache.xml.security.utils.JavaUtils;

public final class AttachmentUtils {
    public static final String MIME_HEADER_CONTENT_DESCRIPTION = "Content-Description";
    public static final String MIME_HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String MIME_HEADER_CONTENT_ID = "Content-ID";
    public static final String MIME_HEADER_CONTENT_LOCATION = "Content-Location";
    public static final String MIME_HEADER_CONTENT_TYPE = "Content-Type";
    public static final char DOUBLE_QUOTE = '\"';
    public static final char SINGLE_QUOTE = '\'';
    public static final char LEFT_PARENTHESIS = '(';
    public static final char RIGHT_PARENTHESIS = ')';
    public static final char CARRIAGE_RETURN = '\r';
    public static final char LINEFEED = '\n';
    public static final char SPACE = ' ';
    public static final char HTAB = '\t';
    public static final char EQUAL = '=';
    public static final char ASTERISK = '*';
    public static final char SEMICOLON = ';';
    public static final char BACKSLASH = '\\';
    public static final String PARAM_CHARSET = "charset";
    public static final String PARAM_CREATION_DATE = "creation-date";
    public static final String PARAM_FILENAME = "filename";
    public static final String PARAM_MODIFICATION_DATE = "modification-date";
    public static final String PARAM_PADDING = "padding";
    public static final String PARAM_READ_DATE = "read-date";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_TYPE = "type";
    public static final Set<String> ALL_PARAMS = new HashSet<String>();

    private AttachmentUtils() {
    }

    public static void canonizeMimeHeaders(OutputStream os, Map<String, String> headers) throws IOException {
        TreeMap<String, String> sortedHeaders = new TreeMap<String, String>();
        for (Map.Entry<String, String> next : headers.entrySet()) {
            String name = next.getKey();
            String value = next.getValue();
            if (MIME_HEADER_CONTENT_DESCRIPTION.equalsIgnoreCase(name)) {
                sortedHeaders.put(MIME_HEADER_CONTENT_DESCRIPTION, AttachmentUtils.uncomment(MimeUtility.decodeText(MimeUtility.unfold(value))));
                continue;
            }
            if (MIME_HEADER_CONTENT_DISPOSITION.equalsIgnoreCase(name)) {
                sortedHeaders.put(MIME_HEADER_CONTENT_DISPOSITION, AttachmentUtils.decodeRfc2184(AttachmentUtils.uncomment(AttachmentUtils.unfoldWhitespace(MimeUtility.unfold(value)))));
                continue;
            }
            if (MIME_HEADER_CONTENT_ID.equalsIgnoreCase(name)) {
                sortedHeaders.put(MIME_HEADER_CONTENT_ID, AttachmentUtils.uncomment(AttachmentUtils.unfoldWhitespace(MimeUtility.unfold(value))));
                continue;
            }
            if (MIME_HEADER_CONTENT_LOCATION.equalsIgnoreCase(name)) {
                sortedHeaders.put(MIME_HEADER_CONTENT_LOCATION, AttachmentUtils.uncomment(AttachmentUtils.unfoldWhitespace(MimeUtility.unfold(value))));
                continue;
            }
            if (!MIME_HEADER_CONTENT_TYPE.equalsIgnoreCase(name)) continue;
            sortedHeaders.put(MIME_HEADER_CONTENT_TYPE, AttachmentUtils.decodeRfc2184(AttachmentUtils.uncomment(AttachmentUtils.unfoldWhitespace(MimeUtility.unfold(value)))));
        }
        if (!sortedHeaders.containsKey(MIME_HEADER_CONTENT_TYPE)) {
            sortedHeaders.put(MIME_HEADER_CONTENT_TYPE, "text/plain;charset=\"us-ascii\"");
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        for (Map.Entry next : sortedHeaders.entrySet()) {
            String name = (String)next.getKey();
            String value = (String)next.getValue();
            outputStreamWriter.write(name);
            outputStreamWriter.write(58);
            outputStreamWriter.write(value);
            if (value.endsWith("\r\n")) continue;
            outputStreamWriter.write("\r\n");
        }
        outputStreamWriter.flush();
    }

    public static String unfoldWhitespace(String text) {
        char[] chars;
        int count = 0;
        for (char character : chars = text.toCharArray()) {
            if (' ' != character && '\t' != character) break;
            ++count;
        }
        return text.substring(count, chars.length);
    }

    public static String unfold(String text) {
        int length = text.length();
        if (length < 3) {
            return text;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length - 2; ++i) {
            char ch1 = text.charAt(i);
            char ch2 = text.charAt(i + 1);
            char ch3 = text.charAt(i + 2);
            if ('\r' == ch1 && '\n' == ch2 && (' ' == ch3 || '\t' == ch3)) {
                if ((i += 2) < length - 3) continue;
                ++i;
                while (i < length) {
                    stringBuilder.append(text.charAt(i));
                    ++i;
                }
                continue;
            }
            stringBuilder.append(ch1);
            if (i != length - 3) continue;
            stringBuilder.append(ch2);
            stringBuilder.append(ch3);
        }
        return stringBuilder.toString();
    }

    public static String decodeRfc2184(String text) throws UnsupportedEncodingException {
        if (!text.contains(";")) {
            return text;
        }
        String[] params = text.split(";");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(params[0].toLowerCase());
        TreeMap<String, String> paramMap = new TreeMap<String, String>();
        String parameterName = null;
        String parameterValue = null;
        String charset = "us-ascii";
        for (int i = 1; i < params.length; ++i) {
            String param2 = params[i];
            int index = param2.indexOf(61);
            String pName = param2.substring(0, index).trim().toLowerCase();
            String pValue = param2.substring(index + 1).trim();
            int idx = pName.lastIndexOf(42);
            if (idx == pName.length() - 1) {
                pName = pName.substring(0, pName.length() - 1);
                int charsetIdx = pValue.indexOf(39);
                if (charsetIdx >= 0) {
                    charset = pValue.substring(0, charsetIdx);
                }
                pValue = pValue.substring(pValue.lastIndexOf(39) + 1);
                pValue = URLDecoder.decode(pValue, MimeUtility.javaCharset(charset));
            }
            if ((idx = pName.lastIndexOf(42)) >= 0) {
                String pn = pName.substring(0, idx).trim();
                if (pn.equals(parameterName)) {
                    parameterValue = AttachmentUtils.concatParamValues(parameterValue, pValue);
                    continue;
                }
                if (parameterName == null) {
                    parameterName = pn;
                    parameterValue = pValue;
                    continue;
                }
                if (ALL_PARAMS.contains(parameterName)) {
                    parameterValue = parameterValue.toLowerCase();
                }
                paramMap.put(parameterName, AttachmentUtils.unquoteInnerText(AttachmentUtils.quote(parameterValue)));
                continue;
            }
            if (parameterName != null) {
                if (ALL_PARAMS.contains(parameterName)) {
                    parameterValue = parameterValue.toLowerCase();
                }
                paramMap.put(parameterName, AttachmentUtils.unquoteInnerText(AttachmentUtils.quote(parameterValue)));
                parameterName = null;
                parameterValue = null;
            }
            if (ALL_PARAMS.contains(pName)) {
                pValue = pValue.toLowerCase();
            }
            paramMap.put(pName, AttachmentUtils.unquoteInnerText(AttachmentUtils.quote(pValue)));
        }
        if (parameterName != null) {
            if (ALL_PARAMS.contains(parameterName)) {
                parameterValue = parameterValue.toLowerCase();
            }
            paramMap.put(parameterName, AttachmentUtils.unquoteInnerText(AttachmentUtils.quote(parameterValue)));
        }
        for (Map.Entry next : paramMap.entrySet()) {
            stringBuilder.append(';');
            stringBuilder.append((String)next.getKey());
            stringBuilder.append('=');
            stringBuilder.append((String)next.getValue());
        }
        return stringBuilder.toString();
    }

    public static String concatParamValues(String a, String b) {
        if ('\"' == a.charAt(a.length() - 1)) {
            a = a.substring(0, a.length() - 1);
        }
        if ('\"' == b.charAt(0)) {
            b = b.substring(1);
        }
        return a + b;
    }

    public static String quote(String text) {
        char startChar = text.charAt(0);
        char endChar = text.charAt(text.length() - 1);
        if ('\"' == startChar && '\"' == endChar) {
            return text;
        }
        if ('\"' != startChar && '\"' != endChar) {
            return '\"' + text + '\"';
        }
        if ('\"' != startChar) {
            return '\"' + text;
        }
        return text + '\"';
    }

    public static String unquoteInnerText(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = text.length();
        for (int i = 0; i < length - 1; ++i) {
            char c = text.charAt(i);
            char c1 = text.charAt(i + 1);
            if (i == 0 && '\"' == c) {
                stringBuilder.append(c);
                continue;
            }
            if ('\\' == c && ('\"' == c1 || '\\' == c1)) {
                if (i != 0 && i != length - 2) {
                    stringBuilder.append(c);
                }
                stringBuilder.append(c1);
                ++i;
                continue;
            }
            if ('\"' == c) {
                stringBuilder.append('\\');
                stringBuilder.append(c);
                continue;
            }
            if ('\\' == c) {
                stringBuilder.append(c1);
                ++i;
                continue;
            }
            stringBuilder.append(c);
            if (i != length - 2 || '\"' != c1) continue;
            stringBuilder.append(c1);
        }
        return stringBuilder.toString();
    }

    public static String uncomment(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        int inComment = 0;
        int length = text.length();
        block0: for (int i = 0; i < length; ++i) {
            char ch = text.charAt(i);
            if ('\"' == ch) {
                stringBuilder.append(ch);
                ++i;
                while (i < length) {
                    ch = text.charAt(i);
                    stringBuilder.append(ch);
                    if ('\"' == ch) continue block0;
                    ++i;
                }
            }
            if ('(' == ch) {
                ++inComment;
                ++i;
                while (i < length) {
                    ch = text.charAt(i);
                    if ('(' == ch) {
                        ++inComment;
                    }
                    if (')' == ch && --inComment == 0) continue block0;
                    ++i;
                }
            }
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    public static void readAndReplaceEncryptedAttachmentHeaders(Map<String, String> headers, InputStream attachmentInputStream) throws IOException, WSSecurityException {
        int ch;
        ArrayList<String> headerLines = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        boolean cr = false;
        int lineLength = 0;
        while ((ch = attachmentInputStream.read()) != -1) {
            if (ch == 13) {
                cr = true;
            } else if (ch == 10 && cr) {
                cr = false;
                if (lineLength == 1 && stringBuilder.charAt(0) == '\r') break;
                if (headerLines.size() > 100) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
                }
                headerLines.add(stringBuilder.substring(0, stringBuilder.length() - 1));
                lineLength = 0;
                stringBuilder.delete(0, stringBuilder.length());
                continue;
            }
            if (++lineLength >= 1000) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            stringBuilder.append((char)ch);
        }
        for (int i = 0; i < headerLines.size(); ++i) {
            String s2 = (String)headerLines.get(i);
            int idx = s2.indexOf(58);
            if (idx == -1) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            headers.put(s2.substring(0, idx), s2.substring(idx + 1));
        }
    }

    public static InputStream setupAttachmentDecryptionStream(final String encAlgo, final Cipher cipher, final Key key, InputStream inputStream2) throws WSSecurityException {
        CipherInputStream cipherInputStream = new CipherInputStream(inputStream2, cipher){
            private boolean firstRead;
            {
                super(arg0, arg1);
                this.firstRead = true;
            }

            private void initCipher() throws IOException {
                int ivLen = JCEMapper.getIVLengthFromURI(encAlgo) / 8;
                byte[] ivBytes = new byte[ivLen];
                for (int read = this.in.read(ivBytes, 0, ivLen); read != ivLen; read += this.in.read(ivBytes, read, ivLen - read)) {
                }
                AlgorithmParameterSpec paramSpec = XMLCipherUtil.constructBlockCipherParameters(encAlgo, ivBytes, AttachmentUtils.class);
                try {
                    cipher.init(2, key, paramSpec);
                }
                catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
                    throw new IOException(e);
                }
            }

            @Override
            public int read() throws IOException {
                if (this.firstRead) {
                    this.initCipher();
                    this.firstRead = false;
                }
                return super.read();
            }

            @Override
            public int read(byte[] bytes) throws IOException {
                if (this.firstRead) {
                    this.initCipher();
                    this.firstRead = false;
                }
                return super.read(bytes);
            }

            @Override
            public int read(byte[] bytes, int i, int i2) throws IOException {
                if (this.firstRead) {
                    this.initCipher();
                    this.firstRead = false;
                }
                return super.read(bytes, i, i2);
            }

            @Override
            public long skip(long l) throws IOException {
                if (this.firstRead) {
                    this.initCipher();
                    this.firstRead = false;
                }
                return super.skip(l);
            }

            @Override
            public int available() throws IOException {
                if (this.firstRead) {
                    this.initCipher();
                    this.firstRead = false;
                }
                return super.available();
            }
        };
        return cipherInputStream;
    }

    public static InputStream setupAttachmentEncryptionStream(Cipher cipher, boolean complete, Attachment attachment, Map<String, String> headers) throws WSSecurityException {
        InputStream attachmentInputStream;
        block15: {
            if (complete) {
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)byteArrayOutputStream, StandardCharsets.US_ASCII);
                    Iterator<Map.Entry<String, String>> iterator2 = headers.entrySet().iterator();
                    while (iterator2.hasNext()) {
                        Map.Entry<String, String> next = iterator2.next();
                        String key = next.getKey();
                        String value = next.getValue();
                        if (!MIME_HEADER_CONTENT_DESCRIPTION.equals(key) && !MIME_HEADER_CONTENT_DISPOSITION.equals(key) && !MIME_HEADER_CONTENT_ID.equals(key) && !MIME_HEADER_CONTENT_LOCATION.equals(key) && !MIME_HEADER_CONTENT_TYPE.equals(key)) continue;
                        iterator2.remove();
                        outputStreamWriter.write(key);
                        outputStreamWriter.write(58);
                        outputStreamWriter.write(value);
                        outputStreamWriter.write("\r\n");
                    }
                    outputStreamWriter.write("\r\n");
                    outputStreamWriter.close();
                    attachmentInputStream = new MultiInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), attachment.getSourceStream());
                    break block15;
                }
                catch (IOException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, (Exception)e);
                }
            }
            attachmentInputStream = attachment.getSourceStream();
        }
        ByteArrayInputStream ivInputStream = new ByteArrayInputStream(cipher.getIV());
        CipherInputStream cipherInputStream = new CipherInputStream(attachmentInputStream, cipher);
        return new MultiInputStream(ivInputStream, cipherInputStream);
    }

    public static byte[] getBytesFromAttachment(String xopUri, CallbackHandler attachmentCallbackHandler, boolean removeAttachments) throws WSSecurityException {
        if (attachmentCallbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
        }
        String attachmentId = AttachmentUtils.getAttachmentId(xopUri);
        AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
        attachmentRequestCallback.setAttachmentId(attachmentId);
        attachmentRequestCallback.setRemoveAttachments(removeAttachments);
        try {
            attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
            List<Attachment> attachments = attachmentRequestCallback.getAttachments();
            if (attachments == null || attachments.isEmpty() || !attachmentId.equals(attachments.get(0).getId())) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"Attachment not found: " + xopUri});
            }
            Attachment attachment = attachments.get(0);
            InputStream inputStream2 = attachment.getSourceStream();
            return JavaUtils.getBytesFromStream(inputStream2);
        }
        catch (IOException | UnsupportedCallbackException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, e);
        }
    }

    public static String getAttachmentId(String xopUri) throws WSSecurityException {
        try {
            return URLDecoder.decode(xopUri.substring("cid:".length()), StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"Attachment ID cannot be decoded: " + xopUri});
        }
    }

    static {
        ALL_PARAMS.add(PARAM_CHARSET);
        ALL_PARAMS.add(PARAM_CREATION_DATE);
        ALL_PARAMS.add(PARAM_FILENAME);
        ALL_PARAMS.add(PARAM_MODIFICATION_DATE);
        ALL_PARAMS.add(PARAM_PADDING);
        ALL_PARAMS.add(PARAM_READ_DATE);
        ALL_PARAMS.add(PARAM_SIZE);
        ALL_PARAMS.add(PARAM_TYPE);
    }
}

