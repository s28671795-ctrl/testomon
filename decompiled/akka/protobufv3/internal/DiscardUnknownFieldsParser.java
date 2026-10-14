/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.AbstractParser;
import akka.protobufv3.internal.CodedInputStream;
import akka.protobufv3.internal.ExtensionRegistryLite;
import akka.protobufv3.internal.InvalidProtocolBufferException;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.Parser;

public final class DiscardUnknownFieldsParser {
    public static final <T extends Message> Parser<T> wrap(final Parser<T> parser) {
        return new AbstractParser<T>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public T parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                try {
                    input.discardUnknownFields();
                    Message message = (Message)parser.parsePartialFrom(input, extensionRegistry);
                    return message;
                }
                finally {
                    input.unsetDiscardUnknownFields();
                }
            }
        };
    }

    private DiscardUnknownFieldsParser() {
    }
}

