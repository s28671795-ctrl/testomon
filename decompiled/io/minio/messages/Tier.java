/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

@Root(name="Tier")
@Convert(value=TierConverter.class)
public enum Tier {
    STANDARD("Standard"),
    BULK("Bulk"),
    EXPEDITED("Expedited");

    private final String value;

    private Tier(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static Tier fromString(String tierString) {
        for (Tier tier : Tier.values()) {
            if (!tierString.equals(tier.value)) continue;
            return tier;
        }
        throw new IllegalArgumentException("Unknown tier '" + tierString + "'");
    }

    public static class TierConverter
    implements Converter<Tier> {
        @Override
        public Tier read(InputNode node) throws Exception {
            return Tier.fromString(node.getValue());
        }

        @Override
        public void write(OutputNode node, Tier tier) throws Exception {
            node.setValue(tier.toString());
        }
    }
}

