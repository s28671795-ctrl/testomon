/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import java.io.IOException;

public interface WebConnection
extends AutoCloseable {
    public ServletInputStream getInputStream() throws IOException;

    public ServletOutputStream getOutputStream() throws IOException;
}

