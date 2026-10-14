/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.server.web.PageParser;
import org.h2.server.web.WebApp;
import org.h2.server.web.WebServer;
import org.h2.util.IOUtils;
import org.h2.util.NetUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.util.Utils21;

class WebThread
extends WebApp
implements Runnable {
    private static final byte[] RN = new byte[]{13, 10};
    private static final byte[] RNRN = new byte[]{13, 10, 13, 10};
    protected OutputStream output;
    protected final Socket socket;
    private final Thread thread;
    private InputStream input;
    private String host;
    private int dataLength;
    private String ifModifiedSince;

    WebThread(Socket socket, WebServer webServer) {
        super(webServer);
        this.socket = socket;
        this.thread = webServer.virtualThreads ? Utils21.newVirtualThread(this) : new Thread(this);
        this.thread.setName("H2 Console thread");
    }

    void start() {
        this.thread.start();
    }

    void join(int n) throws InterruptedException {
        this.thread.join(n);
    }

    void stopNow() {
        this.stop = true;
        try {
            this.socket.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private String getAllowedFile(String string) {
        if (!this.allow()) {
            return "notAllowed.jsp";
        }
        if (string.length() == 0) {
            return "index.do";
        }
        if (string.charAt(0) == '?') {
            return "index.do" + string;
        }
        return string;
    }

    @Override
    public void run() {
        try {
            this.input = new BufferedInputStream(this.socket.getInputStream());
            this.output = new BufferedOutputStream(this.socket.getOutputStream());
            while (!this.stop && this.process()) {
            }
        }
        catch (Exception exception) {
            DbException.traceThrowable(exception);
        }
        IOUtils.closeSilently(this.output);
        IOUtils.closeSilently(this.input);
        try {
            this.socket.close();
        }
        catch (IOException iOException) {
        }
        finally {
            this.server.remove(this);
        }
    }

    private boolean process() throws IOException {
        Object object;
        String string;
        String string2 = this.readHeaderLine();
        boolean bl = string2.startsWith("GET ");
        if (!bl && !string2.startsWith("POST ") || !string2.endsWith(" HTTP/1.1")) {
            this.writeSimple("HTTP/1.1 400 Bad Request", "Bad request");
            return false;
        }
        String string3 = StringUtils.trimSubstring(string2, bl ? 4 : 5, string2.length() - 9);
        if (string3.isEmpty() || string3.charAt(0) != '/') {
            this.writeSimple("HTTP/1.1 400 Bad Request", "Bad request");
            return false;
        }
        this.attributes = new Properties();
        boolean bl2 = this.parseHeader();
        if (!this.checkHost(this.host)) {
            return false;
        }
        string3 = string3.substring(1);
        this.trace(string2 + ": " + string3);
        string3 = this.getAllowedFile(string3);
        int n = string3.indexOf(63);
        this.session = null;
        String string4 = null;
        if (n >= 0) {
            string = string3.substring(n + 1);
            this.parseAttributes(string);
            object = this.attributes.getProperty("jsessionid");
            string4 = this.attributes.getProperty("key");
            string3 = string3.substring(0, n);
            this.session = this.server.getSession((String)object);
        }
        this.parseBodyAttributes();
        string3 = this.processRequest(string3, new NetworkConnectionInfo(NetUtils.ipToShortForm(new StringBuilder(this.server.getSSL() ? "https://" : "http://"), this.socket.getLocalAddress().getAddress(), true).append(':').append(this.socket.getLocalPort()).toString(), this.socket.getInetAddress().getAddress(), this.socket.getPort(), null));
        if (string3.length() == 0) {
            return true;
        }
        if (this.cache && this.ifModifiedSince != null && this.ifModifiedSince.equals(this.server.getStartDateTime())) {
            this.writeSimple("HTTP/1.1 304 Not Modified", (byte[])null);
            return bl2;
        }
        object = this.server.getFile(string3);
        if (object == null) {
            this.writeSimple("HTTP/1.1 404 Not Found", "File not found: " + string3);
            return bl2;
        }
        if (this.session != null && string3.endsWith(".jsp")) {
            Iterator iterator2;
            if (string4 != null) {
                this.session.put("key", string4);
            }
            String string5 = new String((byte[])object, StandardCharsets.UTF_8);
            if (SysProperties.CONSOLE_STREAM && (iterator2 = (Iterator)this.session.map.remove("chunks")) != null) {
                string = "HTTP/1.1 200 OK\r\n";
                string = string + "Content-Type: " + this.mimeType + "\r\n";
                string = string + "Cache-Control: no-cache\r\n";
                string = string + "Transfer-Encoding: chunked\r\n";
                string = string + "\r\n";
                this.trace(string);
                this.output.write(string.getBytes(StandardCharsets.ISO_8859_1));
                while (iterator2.hasNext()) {
                    String string6 = (String)iterator2.next();
                    object = (string6 = PageParser.parse(string6, this.session.map)).getBytes(StandardCharsets.UTF_8);
                    if (((Object)object).length == 0) continue;
                    this.output.write(Integer.toHexString(((Object)object).length).getBytes(StandardCharsets.ISO_8859_1));
                    this.output.write(RN);
                    this.output.write((byte[])object);
                    this.output.write(RN);
                    this.output.flush();
                }
                this.output.write(48);
                this.output.write(RNRN);
                this.output.flush();
                return bl2;
            }
            string5 = PageParser.parse(string5, this.session.map);
            object = string5.getBytes(StandardCharsets.UTF_8);
        }
        string = "HTTP/1.1 200 OK\r\n";
        string = string + "Content-Type: " + this.mimeType + "\r\n";
        if (!this.cache) {
            string = string + "Cache-Control: no-cache\r\n";
        } else {
            string = string + "Cache-Control: max-age=10\r\n";
            string = string + "Last-Modified: " + this.server.getStartDateTime() + "\r\n";
        }
        string = string + "Content-Length: " + ((Object)object).length + "\r\n";
        string = string + "\r\n";
        this.trace(string);
        this.output.write(string.getBytes(StandardCharsets.ISO_8859_1));
        this.output.write((byte[])object);
        this.output.flush();
        return bl2;
    }

    private void writeSimple(String string, String string2) throws IOException {
        this.writeSimple(string, string2 != null ? string2.getBytes(StandardCharsets.UTF_8) : null);
    }

    private void writeSimple(String string, byte[] byArray) throws IOException {
        this.trace(string);
        this.output.write(string.getBytes(StandardCharsets.ISO_8859_1));
        if (byArray != null) {
            this.output.write(RN);
            String string2 = "Content-Length: " + byArray.length;
            this.trace(string2);
            this.output.write(string2.getBytes(StandardCharsets.ISO_8859_1));
            this.output.write(RNRN);
            this.output.write(byArray);
        } else {
            this.output.write(RNRN);
        }
        this.output.flush();
    }

    private boolean checkHost(String string) throws IOException {
        if (string == null) {
            this.writeSimple("HTTP/1.1 400 Bad Request", "Bad request");
            return false;
        }
        int n = string.indexOf(58);
        if (n >= 0) {
            string = string.substring(0, n);
        }
        if (string.isEmpty()) {
            return false;
        }
        if ((string = StringUtils.toLowerEnglish(string)).equals(this.server.getHost()) || string.equals("localhost") || string.equals("127.0.0.1")) {
            return true;
        }
        String string2 = this.server.getExternalNames();
        if (string2 != null && !string2.isEmpty()) {
            for (String string3 : string2.split(",")) {
                if (!string.equals(string3.trim())) continue;
                return true;
            }
        }
        this.writeSimple("HTTP/1.1 404 Not Found", "Host " + string + " not found");
        return false;
    }

    private String readHeaderLine() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int n;
            if ((n = this.input.read()) == -1) {
                throw new IOException("Unexpected EOF");
            }
            if (n == 13) {
                if (this.input.read() != 10) continue;
                return stringBuilder.length() > 0 ? stringBuilder.toString() : null;
            }
            if (n == 10) {
                return stringBuilder.length() > 0 ? stringBuilder.toString() : null;
            }
            stringBuilder.append((char)n);
        }
    }

    private void parseBodyAttributes() throws IOException {
        if (this.dataLength > 0) {
            byte[] byArray = Utils.newBytes(this.dataLength);
            for (int i = 0; i < this.dataLength; i += this.input.read(byArray, i, this.dataLength - i)) {
            }
            String string = new String(byArray, StandardCharsets.UTF_8);
            this.parseAttributes(string);
        }
    }

    private void parseAttributes(String string) {
        int n;
        this.trace("data=" + string);
        while (string != null && (n = string.indexOf(61)) >= 0) {
            String string2;
            String string3 = string.substring(0, n);
            if ((n = (string = string.substring(n + 1)).indexOf(38)) >= 0) {
                string2 = string.substring(0, n);
                string = string.substring(n + 1);
            } else {
                string2 = string;
            }
            String string4 = StringUtils.urlDecode(string2);
            this.attributes.put(string3, string4);
        }
        this.trace(this.attributes.toString());
    }

    private boolean parseHeader() throws IOException {
        String string;
        boolean bl = false;
        this.trace("parseHeader");
        int n = 0;
        this.host = null;
        this.ifModifiedSince = null;
        boolean bl2 = false;
        block0: while ((string = this.readHeaderLine()) != null) {
            this.trace(" " + string);
            String string2 = StringUtils.toLowerEnglish(string);
            if (string2.startsWith("host")) {
                this.host = WebThread.getHeaderLineValue(string);
                continue;
            }
            if (string2.startsWith("if-modified-since")) {
                this.ifModifiedSince = WebThread.getHeaderLineValue(string);
                continue;
            }
            if (string2.startsWith("connection")) {
                String string3 = WebThread.getHeaderLineValue(string);
                if (!"keep-alive".equals(string3)) continue;
                bl = true;
                continue;
            }
            if (string2.startsWith("content-type")) {
                String string4 = WebThread.getHeaderLineValue(string);
                if (!string4.startsWith("multipart/form-data")) continue;
                bl2 = true;
                continue;
            }
            if (string2.startsWith("content-length")) {
                n = Integer.parseInt(WebThread.getHeaderLineValue(string));
                this.trace("len=" + n);
                continue;
            }
            if (string2.startsWith("user-agent")) {
                boolean bl3 = string2.contains("webkit/");
                if (!bl3 || this.session == null) continue;
                this.session.put("frame-border", "1");
                this.session.put("frameset-border", "2");
                continue;
            }
            if (string2.startsWith("accept-language")) {
                Locale locale = this.session == null ? null : this.session.locale;
                if (locale != null) continue;
                String string5 = WebThread.getHeaderLineValue(string);
                StringTokenizer stringTokenizer = new StringTokenizer(string5, ",;");
                while (stringTokenizer.hasMoreTokens()) {
                    String string6 = stringTokenizer.nextToken();
                    if (string6.startsWith("q=") || !this.server.supportsLanguage(string6)) continue;
                    int n2 = string6.indexOf(45);
                    if (n2 >= 0) {
                        String string7 = string6.substring(0, n2);
                        String string8 = string6.substring(n2 + 1);
                        locale = new Locale(string7, string8);
                    } else {
                        locale = new Locale(string6, "");
                    }
                    this.headerLanguage = locale.getLanguage();
                    if (this.session == null) continue block0;
                    this.session.locale = locale;
                    this.session.put("language", this.headerLanguage);
                    this.server.readTranslations(this.session, this.headerLanguage);
                    continue block0;
                }
                continue;
            }
            if (!StringUtils.isWhitespaceOrEmpty(string)) continue;
            break;
        }
        this.dataLength = 0;
        if (!bl2 && n > 0) {
            this.dataLength = n;
        }
        return bl;
    }

    private static String getHeaderLineValue(String string) {
        return StringUtils.trimSubstring(string, string.indexOf(58) + 1);
    }

    @Override
    protected String adminShutdown() {
        this.stopNow();
        return super.adminShutdown();
    }

    private boolean allow() {
        if (this.server.getAllowOthers()) {
            return true;
        }
        try {
            return NetUtils.isLocalAddress(this.socket);
        }
        catch (UnknownHostException unknownHostException) {
            this.server.traceError(unknownHostException);
            return false;
        }
    }

    private void trace(String string) {
        this.server.trace(string);
    }
}

