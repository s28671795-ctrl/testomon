/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util.xml;

import java.io.IOException;
import java.io.OutputStream;
import org.w3c.dom.Document;

public interface XmlWriter {
    public void write(Document var1, OutputStream var2) throws IOException;
}

