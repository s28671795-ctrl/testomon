/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.MessageInfo;
import akka.protobufv3.internal.MessageLite;
import akka.protobufv3.internal.ProtoSyntax;

final class RawMessageInfo
implements MessageInfo {
    private final MessageLite defaultInstance;
    private final String info;
    private final Object[] objects;
    private final int flags;

    RawMessageInfo(MessageLite defaultInstance, String info, Object[] objects) {
        char value;
        this.defaultInstance = defaultInstance;
        this.info = info;
        this.objects = objects;
        try {
            value = info.charAt(0);
        }
        catch (StringIndexOutOfBoundsException e) {
            char[] infoChars = info.toCharArray();
            info = new String(infoChars);
            try {
                value = info.charAt(0);
            }
            catch (StringIndexOutOfBoundsException e2) {
                try {
                    char[] infoChars2 = new char[info.length()];
                    info.getChars(0, info.length(), infoChars2, 0);
                    info = new String(infoChars2);
                    value = info.charAt(0);
                }
                catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e3) {
                    throw new IllegalStateException(String.format("Failed parsing '%s' with charArray.length of %d", info, infoChars.length), e3);
                }
            }
        }
        int position = 1;
        if (value < '\ud800') {
            this.flags = value;
        } else {
            int result = value & 0x1FFF;
            int shift = 13;
            while ((value = info.charAt(position++)) >= '\ud800') {
                result |= (value & 0x1FFF) << shift;
                shift += 13;
            }
            this.flags = result | value << shift;
        }
    }

    String getStringInfo() {
        return this.info;
    }

    Object[] getObjects() {
        return this.objects;
    }

    @Override
    public MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }

    @Override
    public ProtoSyntax getSyntax() {
        return (this.flags & 1) == 1 ? ProtoSyntax.PROTO2 : ProtoSyntax.PROTO3;
    }

    @Override
    public boolean isMessageSetWireFormat() {
        return (this.flags & 2) == 2;
    }
}

