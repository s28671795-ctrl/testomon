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

@Root(name="Status")
@Convert(value=StatusConverter.class)
public enum Status {
    DISABLED("Disabled"),
    ENABLED("Enabled");

    private final String value;

    private Status(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static Status fromString(String statusString) {
        for (Status status : Status.values()) {
            if (!statusString.equals(status.value)) continue;
            return status;
        }
        throw new IllegalArgumentException("Unknown status '" + statusString + "'");
    }

    public static class StatusConverter
    implements Converter<Status> {
        @Override
        public Status read(InputNode node) throws Exception {
            return Status.fromString(node.getValue());
        }

        @Override
        public void write(OutputNode node, Status status) throws Exception {
            node.setValue(status.toString());
        }
    }
}

