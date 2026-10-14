/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.PrefixConverter;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(name="And")
public class AndOperator {
    @Element(name="Prefix", required=false)
    @Convert(value=PrefixConverter.class)
    private String prefix;
    @ElementMap(attribute=false, entry="Tag", inline=true, key="Key", value="Value", required=false)
    private Map<String, String> tags;

    public AndOperator(@Nullable @Element(name="Prefix", required=false) String prefix, @Nullable @ElementMap(attribute=false, entry="Tag", inline=true, key="Key", value="Value", required=false) Map<String, String> tags) {
        if (prefix == null && tags == null) {
            throw new IllegalArgumentException("At least Prefix or Tags must be set");
        }
        if (tags != null) {
            for (String key : tags.keySet()) {
                if (!key.isEmpty()) continue;
                throw new IllegalArgumentException("Tags must not contain empty key");
            }
        }
        this.prefix = prefix;
        this.tags = tags != null ? Collections.unmodifiableMap(tags) : null;
    }

    public String prefix() {
        return this.prefix;
    }

    public Map<String, String> tags() {
        return this.tags;
    }
}

