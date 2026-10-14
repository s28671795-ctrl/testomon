/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.io.dns.RecordType;
import akka.io.dns.RecordType$;
import akka.io.dns.internal.package$;
import akka.util.ByteIterator;
import akka.util.ByteStringBuilder;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;

public final class RecordTypeSerializer$ {
    public static final RecordTypeSerializer$ MODULE$ = new RecordTypeSerializer$();

    public void write(ByteStringBuilder out, RecordType value) {
        out.putShort(value.code(), package$.MODULE$.networkByteOrder());
    }

    public RecordType parse(ByteIterator it) {
        short id = it.getShort(package$.MODULE$.networkByteOrder());
        RecordType recordType = RecordType$.MODULE$.apply(id);
        RecordType recordType2 = OptionVal$Some$.MODULE$.unapply(recordType);
        if (!OptionVal$.MODULE$.isEmpty$extension(recordType2)) {
            RecordType t2 = OptionVal$.MODULE$.get$extension(recordType2);
            return t2;
        }
        throw new IllegalArgumentException(new StringBuilder(31).append("Illegal id [").append(id).append("] for DnsRecordType").toString());
    }

    private RecordTypeSerializer$() {
    }
}

