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

@Root(name="CannedAcl")
@Convert(value=CannedAclConverter.class)
public enum CannedAcl {
    PRIVATE("private"),
    PUBLIC_READ("public-read"),
    PUBLIC_READ_WRITE("public-read-write"),
    AUTHENTICATED_READ("authenticated-read"),
    AWS_EXEC_READ("aws-exec-read"),
    BUCKET_OWNER_READ("bucket-owner-read"),
    BUCKET_OWNER_FULL_CONTROL("bucket-owner-full-control");

    private final String value;

    private CannedAcl(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static CannedAcl fromString(String cannedAclString) {
        for (CannedAcl cannedAcl : CannedAcl.values()) {
            if (!cannedAclString.equals(cannedAcl.value)) continue;
            return cannedAcl;
        }
        throw new IllegalArgumentException("Unknown canned ACL '" + cannedAclString + "'");
    }

    public static class CannedAclConverter
    implements Converter<CannedAcl> {
        @Override
        public CannedAcl read(InputNode node) throws Exception {
            return CannedAcl.fromString(node.getValue());
        }

        @Override
        public void write(OutputNode node, CannedAcl cannedAcl) throws Exception {
            node.setValue(cannedAcl.toString());
        }
    }
}

