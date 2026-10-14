/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.WriteListener;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public abstract class ServletOutputStream
extends OutputStream {
    private static final String LSTRING_FILE = "jakarta.servlet.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle("jakarta.servlet.LocalStrings");

    protected ServletOutputStream() {
    }

    public void print(String s2) throws IOException {
        if (s2 == null) {
            s2 = "null";
        }
        int len = s2.length();
        byte[] out = new byte[len];
        for (int i = 0; i < len; ++i) {
            char c = s2.charAt(i);
            if ((c & 0xFF00) != 0) {
                String errMsg = lStrings.getString("err.not_iso8859_1");
                Object[] errArgs = new Object[]{Character.valueOf(c)};
                errMsg = MessageFormat.format(errMsg, errArgs);
                throw new CharConversionException(errMsg);
            }
            out[i] = (byte)(0xFF & c);
        }
        this.write(out, 0, len);
    }

    public void print(boolean b) throws IOException {
        this.print(lStrings.getString(b ? "value.true" : "value.false"));
    }

    public void print(char c) throws IOException {
        this.print(String.valueOf(c));
    }

    public void print(int i) throws IOException {
        this.print(String.valueOf(i));
    }

    public void print(long l) throws IOException {
        this.print(String.valueOf(l));
    }

    public void print(float f) throws IOException {
        this.print(String.valueOf(f));
    }

    public void print(double d) throws IOException {
        this.print(String.valueOf(d));
    }

    public void println() throws IOException {
        this.print("\r\n");
    }

    public void println(String s2) throws IOException {
        this.print((String)(s2 == null ? "null\r\n" : s2 + "\r\n"));
    }

    public void println(boolean b) throws IOException {
        this.println(lStrings.getString(b ? "value.true" : "value.false"));
    }

    public void println(char c) throws IOException {
        this.println(String.valueOf(c));
    }

    public void println(int i) throws IOException {
        this.println(String.valueOf(i));
    }

    public void println(long l) throws IOException {
        this.println(String.valueOf(l));
    }

    public void println(float f) throws IOException {
        this.println(String.valueOf(f));
    }

    public void println(double d) throws IOException {
        this.println(String.valueOf(d));
    }

    public abstract boolean isReady();

    public abstract void setWriteListener(WriteListener var1);
}

