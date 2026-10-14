/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public class PrefixConverter
implements Converter<String> {
    @Override
    public String read(InputNode node) throws Exception {
        String value = node.getValue();
        return value != null ? value : "";
    }

    @Override
    public void write(OutputNode node, String prefix) throws Exception {
        node.setValue(prefix);
    }
}

