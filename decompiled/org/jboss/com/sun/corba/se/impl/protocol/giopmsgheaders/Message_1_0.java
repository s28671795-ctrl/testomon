/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public class Message_1_0
extends MessageBase {
    private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.protocol");
    int magic = 0;
    GIOPVersion GIOP_version = null;
    boolean byte_order = false;
    byte message_type = 0;
    int message_size = 0;

    Message_1_0() {
    }

    Message_1_0(int _magic, boolean _byte_order, byte _message_type, int _message_size) {
        this.magic = _magic;
        this.GIOP_version = GIOPVersion.V1_0;
        this.byte_order = _byte_order;
        this.message_type = _message_type;
        this.message_size = _message_size;
    }

    @Override
    public GIOPVersion getGIOPVersion() {
        return this.GIOP_version;
    }

    @Override
    public int getType() {
        return this.message_type;
    }

    @Override
    public int getSize() {
        return this.message_size;
    }

    @Override
    public boolean isLittleEndian() {
        return this.byte_order;
    }

    @Override
    public boolean moreFragmentsToFollow() {
        return false;
    }

    @Override
    public void setSize(ByteBuffer byteBuffer, int size) {
        this.message_size = size;
        int patch = size - 12;
        if (!this.isLittleEndian()) {
            byteBuffer.put(8, (byte)(patch >>> 24 & 0xFF));
            byteBuffer.put(9, (byte)(patch >>> 16 & 0xFF));
            byteBuffer.put(10, (byte)(patch >>> 8 & 0xFF));
            byteBuffer.put(11, (byte)(patch >>> 0 & 0xFF));
        } else {
            byteBuffer.put(8, (byte)(patch >>> 0 & 0xFF));
            byteBuffer.put(9, (byte)(patch >>> 8 & 0xFF));
            byteBuffer.put(10, (byte)(patch >>> 16 & 0xFF));
            byteBuffer.put(11, (byte)(patch >>> 24 & 0xFF));
        }
    }

    @Override
    public FragmentMessage createFragmentMessage() {
        throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
    }

    @Override
    public void read(InputStream istream) {
    }

    @Override
    public void write(OutputStream ostream) {
        ostream.write_long(this.magic);
        Message_1_0.nullCheck(this.GIOP_version);
        this.GIOP_version.write(ostream);
        ostream.write_boolean(this.byte_order);
        ostream.write_octet(this.message_type);
        ostream.write_ulong(this.message_size);
    }
}

