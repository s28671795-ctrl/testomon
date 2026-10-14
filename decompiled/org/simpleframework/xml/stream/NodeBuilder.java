/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import org.simpleframework.xml.stream.EventReader;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeReader;
import org.simpleframework.xml.stream.NodeWriter;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.Provider;
import org.simpleframework.xml.stream.ProviderFactory;

public final class NodeBuilder {
    private static Provider PROVIDER = ProviderFactory.getInstance();

    public static InputNode read(InputStream source2) throws Exception {
        return NodeBuilder.read(PROVIDER.provide(source2));
    }

    public static InputNode read(Reader source2) throws Exception {
        return NodeBuilder.read(PROVIDER.provide(source2));
    }

    private static InputNode read(EventReader source2) throws Exception {
        return new NodeReader(source2).readRoot();
    }

    public static OutputNode write(Writer result) throws Exception {
        return NodeBuilder.write(result, new Format());
    }

    public static OutputNode write(Writer result, Format format) throws Exception {
        return new NodeWriter(result, format).writeRoot();
    }
}

