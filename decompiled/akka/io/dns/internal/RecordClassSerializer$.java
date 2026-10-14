/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.dns.RecordClass;
import akka.io.dns.RecordClass$;
import akka.io.dns.internal.package$;
import akka.util.ByteIterator;
import akka.util.ByteStringBuilder;

@InternalApi
public final class RecordClassSerializer$ {
    public static final RecordClassSerializer$ MODULE$ = new RecordClassSerializer$();

    public RecordClass parse(ByteIterator it) {
        short s2 = it.getShort(package$.MODULE$.networkByteOrder());
        switch (s2) {
            case 1: {
                return RecordClass$.MODULE$.IN();
            }
            case 2: {
                return RecordClass$.MODULE$.CS();
            }
            case 3: {
                return RecordClass$.MODULE$.CH();
            }
            case 255: {
                return RecordClass$.MODULE$.WILDCARD();
            }
        }
        throw new RuntimeException(new StringBuilder(24).append("Unexpected record class ").append(s2).toString());
    }

    public void write(ByteStringBuilder out, RecordClass c) {
        out.putShort(c.code(), package$.MODULE$.networkByteOrder());
    }

    private RecordClassSerializer$() {
    }
}

