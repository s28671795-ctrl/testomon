/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.AndOperator;
import io.minio.messages.PrefixConverter;
import io.minio.messages.Tag;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(name="Filter")
public class RuleFilter {
    @Element(name="And", required=false)
    private AndOperator andOperator;
    @Element(name="Prefix", required=false)
    @Convert(value=PrefixConverter.class)
    private String prefix;
    @Element(name="Tag", required=false)
    private Tag tag;

    public RuleFilter(@Nullable @Element(name="And", required=false) AndOperator andOperator, @Nullable @Element(name="Prefix", required=false) String prefix, @Nullable @Element(name="Tag", required=false) Tag tag) {
        if (!(andOperator != null ^ prefix != null ^ tag != null)) {
            throw new IllegalArgumentException("Only one of And, Prefix or Tag must be set");
        }
        this.andOperator = andOperator;
        this.prefix = prefix;
        this.tag = tag;
    }

    public RuleFilter(@Nonnull AndOperator andOperator) {
        this.andOperator = Objects.requireNonNull(andOperator, "And operator must not be null");
    }

    public RuleFilter(@Nonnull String prefix) {
        this.prefix = Objects.requireNonNull(prefix, "Prefix must not be null");
    }

    public RuleFilter(@Nonnull Tag tag) {
        this.tag = Objects.requireNonNull(tag, "Tag must not be null");
    }

    public AndOperator andOperator() {
        return this.andOperator;
    }

    public String prefix() {
        return this.prefix;
    }

    public Tag tag() {
        return this.tag;
    }
}

