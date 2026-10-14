/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

@Root(name="Metadata")
@Convert(value=MetadataConverter.class)
public class Metadata {
    Map<String, String> map;

    public Metadata() {
    }

    public Metadata(Map<String, String> map) {
        this.map = Collections.unmodifiableMap(map);
    }

    public Map<String, String> get() {
        return this.map;
    }

    public static class MetadataConverter
    implements Converter<Metadata> {
        @Override
        public Metadata read(InputNode node) throws Exception {
            InputNode childNode;
            HashMap<String, String> map = new HashMap<String, String>();
            while ((childNode = node.getNext()) != null) {
                map.put(childNode.getName(), childNode.getValue());
            }
            if (map.size() > 0) {
                return new Metadata(map);
            }
            return null;
        }

        @Override
        public void write(OutputNode node, Metadata metadata) throws Exception {
            for (Map.Entry<String, String> entry : metadata.get().entrySet()) {
                OutputNode childNode = node.getChild(entry.getKey());
                childNode.setValue(entry.getValue());
            }
            node.commit();
        }
    }
}

