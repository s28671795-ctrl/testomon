/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.est.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.est.ESTClient;
import org.bouncycastle.est.ESTClientSourceProvider;
import org.bouncycastle.est.ESTException;
import org.bouncycastle.est.ESTRequest;
import org.bouncycastle.est.ESTRequestBuilder;
import org.bouncycastle.est.ESTResponse;
import org.bouncycastle.est.Source;
import org.bouncycastle.util.Properties;

class DefaultESTClient
implements ESTClient {
    private static final Charset utf8 = Charset.forName("UTF-8");
    private static byte[] CRLF = new byte[]{13, 10};
    private final ESTClientSourceProvider sslSocketProvider;

    public DefaultESTClient(ESTClientSourceProvider eSTClientSourceProvider) {
        this.sslSocketProvider = eSTClientSourceProvider;
    }

    private static void writeLine(OutputStream outputStream2, String string) throws IOException {
        outputStream2.write(string.getBytes());
        outputStream2.write(CRLF);
    }

    @Override
    public ESTResponse doRequest(ESTRequest eSTRequest) throws IOException {
        ESTResponse eSTResponse = null;
        ESTRequest eSTRequest2 = eSTRequest;
        int n = 15;
        while ((eSTRequest2 = this.redirectURL(eSTResponse = this.performRequest(eSTRequest2))) != null && --n > 0) {
        }
        if (n == 0) {
            throw new ESTException("Too many redirects..");
        }
        return eSTResponse;
    }

    protected ESTRequest redirectURL(ESTResponse eSTResponse) throws IOException {
        ESTRequest eSTRequest = null;
        if (eSTResponse.getStatusCode() >= 300 && eSTResponse.getStatusCode() <= 399) {
            switch (eSTResponse.getStatusCode()) {
                case 301: 
                case 302: 
                case 303: 
                case 306: 
                case 307: {
                    String string = eSTResponse.getHeader("Location");
                    if ("".equals(string)) {
                        throw new ESTException("Redirect status type: " + eSTResponse.getStatusCode() + " but no location header");
                    }
                    ESTRequestBuilder eSTRequestBuilder = new ESTRequestBuilder(eSTResponse.getOriginalRequest());
                    if (string.startsWith("http")) {
                        eSTRequest = eSTRequestBuilder.withURL(new URL(string)).build();
                        break;
                    }
                    URL uRL = eSTResponse.getOriginalRequest().getURL();
                    eSTRequest = eSTRequestBuilder.withURL(new URL(uRL.getProtocol(), uRL.getHost(), uRL.getPort(), string)).build();
                    break;
                }
                default: {
                    throw new ESTException("Client does not handle http status code: " + eSTResponse.getStatusCode());
                }
            }
        }
        if (eSTRequest != null) {
            eSTResponse.close();
        }
        return eSTRequest;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ESTResponse performRequest(ESTRequest eSTRequest) throws IOException {
        ESTResponse eSTResponse = null;
        Source source2 = null;
        try {
            Object object;
            URL uRL;
            source2 = this.sslSocketProvider.makeSource(eSTRequest.getURL().getHost(), eSTRequest.getURL().getPort());
            if (eSTRequest.getListener() != null) {
                eSTRequest = eSTRequest.getListener().onConnection(source2, eSTRequest);
            }
            OutputStream outputStream2 = null;
            Set<String> set = Properties.asKeySet("org.bouncycastle.debug.est");
            outputStream2 = set.contains("output") || set.contains("all") ? new PrintingOutputStream(source2.getOutputStream()) : source2.getOutputStream();
            String string = eSTRequest.getURL().getPath() + (eSTRequest.getURL().getQuery() != null ? eSTRequest.getURL().getQuery() : "");
            ESTRequestBuilder eSTRequestBuilder = new ESTRequestBuilder(eSTRequest);
            Map<String, String[]> map = eSTRequest.getHeaders();
            if (!map.containsKey("Connection")) {
                eSTRequestBuilder.addHeader("Connection", "close");
            }
            if ((uRL = eSTRequest.getURL()).getPort() > -1) {
                eSTRequestBuilder.setHeader("Host", String.format("%s:%d", uRL.getHost(), uRL.getPort()));
            } else {
                eSTRequestBuilder.setHeader("Host", uRL.getHost());
            }
            ESTRequest eSTRequest2 = eSTRequestBuilder.build();
            DefaultESTClient.writeLine(outputStream2, eSTRequest2.getMethod() + " " + string + " HTTP/1.1");
            for (Map.Entry<String, String[]> entry : eSTRequest2.getHeaders().entrySet()) {
                String[] stringArray = entry.getValue();
                for (int i = 0; i != stringArray.length; ++i) {
                    DefaultESTClient.writeLine(outputStream2, entry.getKey() + ": " + stringArray[i]);
                }
            }
            outputStream2.write(CRLF);
            outputStream2.flush();
            eSTRequest2.writeData(outputStream2);
            outputStream2.flush();
            if (eSTRequest2.getHijacker() != null) {
                eSTResponse = eSTRequest2.getHijacker().hijack(eSTRequest2, source2);
                object = eSTResponse;
                return object;
            }
            eSTResponse = new ESTResponse(eSTRequest2, source2);
            object = eSTResponse;
            return object;
        }
        finally {
            if (source2 != null && eSTResponse == null) {
                source2.close();
            }
        }
    }

    private static class PrintingOutputStream
    extends OutputStream {
        private final OutputStream tgt;

        public PrintingOutputStream(OutputStream outputStream2) {
            this.tgt = outputStream2;
        }

        @Override
        public void write(int n) throws IOException {
            System.out.print(String.valueOf((char)n));
            this.tgt.write(n);
        }
    }
}

