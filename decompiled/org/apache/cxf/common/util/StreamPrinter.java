/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

class StreamPrinter
extends Thread {
    private InputStream is;
    private String msg;
    private OutputStream os;

    StreamPrinter(InputStream stream, String type, OutputStream redirect2) {
        this.is = stream;
        this.msg = type;
        this.os = redirect2;
    }

    @Override
    public void run() {
        try {
            PrintWriter pw = null;
            if (this.os != null) {
                pw = new PrintWriter(this.os);
            }
            InputStreamReader isr = new InputStreamReader(this.is);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                if (pw != null) {
                    pw.println(this.msg + " " + line);
                }
                line = br.readLine();
            }
            if (pw != null) {
                pw.flush();
            }
        }
        catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}

