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

@Root(name="SSEAlgorithm")
@Convert(value=SseAlgorithmConverter.class)
public enum SseAlgorithm {
    AES256("AES256"),
    AWS_KMS("aws:kms");

    private final String value;

    private SseAlgorithm(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static SseAlgorithm fromString(String sseAlgorithmString) {
        for (SseAlgorithm sa : SseAlgorithm.values()) {
            if (!sseAlgorithmString.equals(sa.value)) continue;
            return sa;
        }
        throw new IllegalArgumentException("unknown SSE algorithm '" + sseAlgorithmString + "'");
    }

    public static class SseAlgorithmConverter
    implements Converter<SseAlgorithm> {
        @Override
        public SseAlgorithm read(InputNode node) throws Exception {
            return SseAlgorithm.fromString(node.getValue());
        }

        @Override
        public void write(OutputNode node, SseAlgorithm sseAlgorithm) throws Exception {
            node.setValue(sseAlgorithm.toString());
        }
    }
}

