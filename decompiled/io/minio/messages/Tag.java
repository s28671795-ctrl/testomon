/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Tag")
public class Tag {
    @Element(name="Key")
    private String key;
    @Element(name="Value")
    private String value;

    public Tag(@Nonnull @Element(name="Key") String key, @Nonnull @Element(name="Value") String value) {
        Objects.requireNonNull(key, "Key must not be null");
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Key must not be empty");
        }
        this.key = key;
        this.value = Objects.requireNonNull(value, "Value must not be null");
    }

    public String key() {
        return this.key;
    }

    public String value() {
        return this.value;
    }
}

