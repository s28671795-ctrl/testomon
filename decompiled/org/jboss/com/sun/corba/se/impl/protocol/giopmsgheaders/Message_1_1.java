/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.nio.ByteBuffer;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public class Message_1_1
extends MessageBase {
    static final int UPPER_THREE_BYTES_OF_INT_MASK = 255;
    private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.protocol");
    int magic = 0;
    GIOPVersion GIOP_version = null;
    byte flags = 0;
    byte message_type = 0;
    int message_size = 0;

    Message_1_1() {
    }

    Message_1_1(int _magic, GIOPVersion _GIOP_version, byte _flags, byte _message_type, int _message_size) {
        this.magic = _magic;
        this.GIOP_version = _GIOP_version;
        this.flags = _flags;
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
        return (this.flags & 1) == 1;
    }

    @Override
    public boolean moreFragmentsToFollow() {
        return (this.flags & 2) == 2;
    }

    public void setThreadPoolToUse(int poolToUse) {
        int tmpFlags = poolToUse << 2;
        tmpFlags &= 0xFF;
        this.flags = (byte)(tmpFlags |= this.flags);
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
        switch (this.message_type) {
            case 2: 
            case 5: 
            case 6: {
                throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
            }
            case 3: 
            case 4: {
                if (!this.GIOP_version.equals(GIOPVersion.V1_1)) break;
                throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
            }
        }
        if (this.GIOP_version.equals(GIOPVersion.V1_1)) {
            return new FragmentMessage_1_1(this);
        }
        if (this.GIOP_version.equals(GIOPVersion.V1_2)) {
            return new FragmentMessage_1_2(this);
        }
        throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
    }

    @Override
    public void read(InputStream istream) {
    }

    @Override
    public void write(OutputStream ostream) {
        ostream.write_long(this.magic);
        Message_1_1.nullCheck(this.GIOP_version);
        this.GIOP_version.write(ostream);
        ostream.write_octet(this.flags);
        ostream.write_octet(this.message_type);
        ostream.write_ulong(this.message_size);
    }
}

