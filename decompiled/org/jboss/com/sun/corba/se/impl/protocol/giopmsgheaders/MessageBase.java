/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.jboss.com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import org.jboss.com.sun.corba.se.impl.encoding.CDRInputStream_1_0;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBClassLoader;
import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.protocol.AddressingDispositionException;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.IORAddressingInfo;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.Message_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_0;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_1;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_2;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.TargetAddress;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPFactories;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfile;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
import org.jboss.com.sun.corba.se.spi.ior.iiop.RequestPartitioningComponent;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.servicecontext.ServiceContexts;
import org.jboss.com.sun.corba.se.spi.transport.CorbaConnection;
import org.jboss.com.sun.corba.se.spi.transport.ReadTimeouts;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Principal;
import org.omg.CORBA.SystemException;
import org.omg.IOP.TaggedProfile;

public abstract class MessageBase
implements Message {
    public byte[] giopHeader;
    private ByteBuffer byteBuffer;
    private int threadPoolToUse;
    byte encodingVersion = 0;
    private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.protocol");

    public static String typeToString(int type) {
        return MessageBase.typeToString((byte)type);
    }

    public static String typeToString(byte type) {
        String result = type + "/";
        switch (type) {
            case 0: {
                result = result + "GIOPRequest";
                break;
            }
            case 1: {
                result = result + "GIOPReply";
                break;
            }
            case 2: {
                result = result + "GIOPCancelRequest";
                break;
            }
            case 3: {
                result = result + "GIOPLocateRequest";
                break;
            }
            case 4: {
                result = result + "GIOPLocateReply";
                break;
            }
            case 5: {
                result = result + "GIOPCloseConnection";
                break;
            }
            case 6: {
                result = result + "GIOPMessageError";
                break;
            }
            case 7: {
                result = result + "GIOPFragment";
                break;
            }
            default: {
                result = result + "Unknown";
            }
        }
        return result;
    }

    public static MessageBase readGIOPMessage(ORB orb, CorbaConnection connection) {
        MessageBase msg = MessageBase.readGIOPHeader(orb, connection);
        msg = (MessageBase)MessageBase.readGIOPBody(orb, connection, msg);
        return msg;
    }

    public static MessageBase readGIOPHeader(ORB orb, CorbaConnection connection) {
        int b4;
        int b3;
        int b2;
        int b1;
        int magic;
        MessageBase msg = null;
        ReadTimeouts readTimeouts = orb.getORBData().getTransportTCPReadTimeouts();
        ByteBuffer buf = null;
        try {
            buf = connection.read(12, 0, 12, (long)readTimeouts.get_max_giop_header_time_to_wait());
        }
        catch (IOException e) {
            throw wrapper.ioexceptionWhenReadingConnection(e);
        }
        if (orb.giopDebugFlag) {
            MessageBase.dprint(".readGIOPHeader: " + MessageBase.typeToString(buf.get(7)));
            MessageBase.dprint(".readGIOPHeader: GIOP header is: ");
            ByteBuffer viewBuffer = buf.asReadOnlyBuffer();
            viewBuffer.position(0).limit(12);
            ByteBufferWithInfo bbwi = new ByteBufferWithInfo((org.omg.CORBA.ORB)orb, viewBuffer);
            bbwi.buflen = 12;
            CDRInputStream_1_0.printBuffer(bbwi);
        }
        if ((magic = (b1 = buf.get(0) << 24 & 0xFF000000) | (b2 = buf.get(1) << 16 & 0xFF0000) | (b3 = buf.get(2) << 8 & 0xFF00) | (b4 = buf.get(3) << 0 & 0xFF)) != 1195986768) {
            throw wrapper.giopMagicError(CompletionStatus.COMPLETED_MAYBE);
        }
        byte requestEncodingVersion = 0;
        if (buf.get(4) == 13 && buf.get(5) <= 1 && buf.get(5) > 0 && orb.getORBData().isJavaSerializationEnabled()) {
            requestEncodingVersion = buf.get(5);
            buf.put(4, (byte)1);
            buf.put(5, (byte)2);
        }
        GIOPVersion orbVersion = orb.getORBData().getGIOPVersion();
        if (orb.giopDebugFlag) {
            MessageBase.dprint(".readGIOPHeader: Message GIOP version: " + buf.get(4) + '.' + buf.get(5));
            MessageBase.dprint(".readGIOPHeader: ORB Max GIOP Version: " + orbVersion);
        }
        if ((buf.get(4) > orbVersion.getMajor() || buf.get(4) == orbVersion.getMajor() && buf.get(5) > orbVersion.getMinor()) && buf.get(7) != 6) {
            throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
        }
        MessageBase.AreFragmentsAllowed(buf.get(4), buf.get(5), buf.get(6), buf.get(7));
        switch (buf.get(7)) {
            case 0: {
                if (orb.giopDebugFlag) {
                    MessageBase.dprint(".readGIOPHeader: creating RequestMessage");
                }
                if (buf.get(4) == 1 && buf.get(5) == 0) {
                    msg = new RequestMessage_1_0(orb);
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 1) {
                    msg = new RequestMessage_1_1(orb);
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 2) {
                    msg = new RequestMessage_1_2(orb);
                    break;
                }
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
            case 3: {
                if (orb.giopDebugFlag) {
                    MessageBase.dprint(".readGIOPHeader: creating LocateRequestMessage");
                }
                if (buf.get(4) == 1 && buf.get(5) == 0) {
                    msg = new LocateRequestMessage_1_0(orb);
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 1) {
                    msg = new LocateRequestMessage_1_1(orb);
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 2) {
                    msg = new LocateRequestMessage_1_2(orb);
                    break;
                }
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
            case 2: {
                if (orb.giopDebugFlag) {
                    MessageBase.dprint(".readGIOPHeader: creating CancelRequestMessage");
                }
                if (buf.get(4) == 1 && buf.get(5) == 0) {
                    msg = new CancelRequestMessage_1_0();
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 1) {
                    msg = new CancelRequestMessage_1_1();
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 2) {
                    msg = new CancelRequestMessage_1_2();
                    break;
                }
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
            case 1: {
                if (orb.giopDebugFlag) {
                    MessageBase.dprint(".readGIOPHeader: creating ReplyMessage");
                }
                if (buf.get(4) == 1 && buf.get(5) == 0) {
                    msg = new ReplyMessage_1_0(orb);
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 1) {
                    msg = new ReplyMessage_1_1(orb);
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 2) {
                    msg = new ReplyMessage_1_2(orb);
                    break;
                }
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
            case 4: {
                if (orb.giopDebugFlag) {
                    MessageBase.dprint(".readGIOPHeader: creating LocateReplyMessage");
                }
                if (buf.get(4) == 1 && buf.get(5) == 0) {
                    msg = new LocateReplyMessage_1_0(orb);
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 1) {
                    msg = new LocateReplyMessage_1_1(orb);
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 2) {
                    msg = new LocateReplyMessage_1_2(orb);
                    break;
                }
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
            case 5: 
            case 6: {
                if (orb.giopDebugFlag) {
                    MessageBase.dprint(".readGIOPHeader: creating Message for CloseConnection or MessageError");
                }
                if (buf.get(4) == 1 && buf.get(5) == 0) {
                    msg = new Message_1_0();
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 1) {
                    msg = new Message_1_1();
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 2) {
                    msg = new Message_1_1();
                    break;
                }
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
            case 7: {
                if (orb.giopDebugFlag) {
                    MessageBase.dprint(".readGIOPHeader: creating FragmentMessage");
                }
                if (buf.get(4) == 1 && buf.get(5) == 0) break;
                if (buf.get(4) == 1 && buf.get(5) == 1) {
                    msg = new FragmentMessage_1_1();
                    break;
                }
                if (buf.get(4) == 1 && buf.get(5) == 2) {
                    msg = new FragmentMessage_1_2();
                    break;
                }
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
            default: {
                if (orb.giopDebugFlag) {
                    MessageBase.dprint(".readGIOPHeader: UNKNOWN MESSAGE TYPE: " + buf.get(7));
                }
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
        }
        if (buf.get(4) == 1 && buf.get(5) == 0) {
            Message_1_0 msg10 = (Message_1_0)msg;
            msg10.magic = magic;
            msg10.GIOP_version = new GIOPVersion(buf.get(4), buf.get(5));
            msg10.byte_order = buf.get(6) == 1;
            msg.threadPoolToUse = 0;
            msg10.message_type = buf.get(7);
            msg10.message_size = MessageBase.readSize(buf.get(8), buf.get(9), buf.get(10), buf.get(11), msg10.isLittleEndian()) + 12;
        } else {
            Message_1_1 msg11 = (Message_1_1)msg;
            msg11.magic = magic;
            msg11.GIOP_version = new GIOPVersion(buf.get(4), buf.get(5));
            msg11.flags = (byte)(buf.get(6) & 3);
            msg.threadPoolToUse = buf.get(6) >>> 2 & 0x3F;
            msg11.message_type = buf.get(7);
            msg11.message_size = MessageBase.readSize(buf.get(8), buf.get(9), buf.get(10), buf.get(11), msg11.isLittleEndian()) + 12;
        }
        if (orb.giopDebugFlag) {
            MessageBase.dprint(".readGIOPHeader: header construction complete.");
            ByteBuffer viewBuf = buf.asReadOnlyBuffer();
            byte[] msgBuf = new byte[12];
            viewBuf.position(0).limit(12);
            viewBuf.get(msgBuf, 0, msgBuf.length);
            msg.giopHeader = msgBuf;
        }
        msg.setByteBuffer(buf);
        msg.setEncodingVersion(requestEncodingVersion);
        return msg;
    }

    public static Message readGIOPBody(ORB orb, CorbaConnection connection, Message msg) {
        ReadTimeouts readTimeouts = orb.getORBData().getTransportTCPReadTimeouts();
        ByteBuffer buf = msg.getByteBuffer();
        buf.position(12);
        int msgSizeMinusHeader = msg.getSize() - 12;
        try {
            buf = connection.read(buf, 12, msgSizeMinusHeader, (long)readTimeouts.get_max_time_to_wait());
        }
        catch (IOException e) {
            throw wrapper.ioexceptionWhenReadingConnection(e);
        }
        msg.setByteBuffer(buf);
        if (orb.giopDebugFlag) {
            MessageBase.dprint(".readGIOPBody: received message:");
            ByteBuffer viewBuffer = buf.asReadOnlyBuffer();
            viewBuffer.position(0).limit(msg.getSize());
            ByteBufferWithInfo bbwi = new ByteBufferWithInfo((org.omg.CORBA.ORB)orb, viewBuffer);
            CDRInputStream_1_0.printBuffer(bbwi);
        }
        return msg;
    }

    private static RequestMessage createRequest(ORB orb, GIOPVersion gv, byte encodingVersion, int request_id, boolean response_expected, byte[] object_key, String operation, ServiceContexts service_contexts, Principal requesting_principal) {
        if (gv.equals(GIOPVersion.V1_0)) {
            return new RequestMessage_1_0(orb, service_contexts, request_id, response_expected, object_key, operation, requesting_principal);
        }
        if (gv.equals(GIOPVersion.V1_1)) {
            return new RequestMessage_1_1(orb, service_contexts, request_id, response_expected, new byte[]{0, 0, 0}, object_key, operation, requesting_principal);
        }
        if (gv.equals(GIOPVersion.V1_2)) {
            byte response_flags = 3;
            response_flags = response_expected ? (byte)3 : 0;
            TargetAddress target = new TargetAddress();
            target.object_key(object_key);
            RequestMessage_1_2 msg = new RequestMessage_1_2(orb, request_id, response_flags, new byte[]{0, 0, 0}, target, operation, service_contexts);
            msg.setEncodingVersion(encodingVersion);
            return msg;
        }
        throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
    }

    public static RequestMessage createRequest(ORB orb, GIOPVersion gv, byte encodingVersion, int request_id, boolean response_expected, IOR ior, short addrDisp, String operation, ServiceContexts service_contexts, Principal requesting_principal) {
        RequestMessage requestMessage = null;
        IIOPProfile profile = ior.getProfile();
        if (addrDisp == 0) {
            profile = ior.getProfile();
            ObjectKey objKey = profile.getObjectKey();
            byte[] object_key = objKey.getBytes(orb);
            requestMessage = MessageBase.createRequest(orb, gv, encodingVersion, request_id, response_expected, object_key, operation, service_contexts, requesting_principal);
        } else {
            if (!gv.equals(GIOPVersion.V1_2)) {
                throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
            }
            byte response_flags = 3;
            response_flags = response_expected ? (byte)3 : 0;
            TargetAddress target = new TargetAddress();
            if (addrDisp == 1) {
                profile = ior.getProfile();
                target.profile(profile.getIOPProfile());
            } else if (addrDisp == 2) {
                IORAddressingInfo iorInfo = new IORAddressingInfo(0, ior.getIOPIOR());
                target.ior(iorInfo);
            } else {
                throw wrapper.illegalTargetAddressDisposition(CompletionStatus.COMPLETED_NO);
            }
            requestMessage = new RequestMessage_1_2(orb, request_id, response_flags, new byte[]{0, 0, 0}, target, operation, service_contexts);
            requestMessage.setEncodingVersion(encodingVersion);
        }
        if (gv.supportsIORIIOPProfileComponents()) {
            int poolToUse = 0;
            IIOPProfileTemplate temp = (IIOPProfileTemplate)profile.getTaggedProfileTemplate();
            Iterator<Object> iter = temp.iteratorById(1398099457);
            if (iter.hasNext()) {
                poolToUse = ((RequestPartitioningComponent)iter.next()).getRequestPartitioningId();
            }
            if (poolToUse < 0 || poolToUse > 63) {
                throw wrapper.invalidRequestPartitioningId(new Integer(poolToUse), new Integer(0), new Integer(63));
            }
            requestMessage.setThreadPoolToUse(poolToUse);
        }
        return requestMessage;
    }

    public static ReplyMessage createReply(ORB orb, GIOPVersion gv, byte encodingVersion, int request_id, int reply_status, ServiceContexts service_contexts, IOR ior) {
        if (gv.equals(GIOPVersion.V1_0)) {
            return new ReplyMessage_1_0(orb, service_contexts, request_id, reply_status, ior);
        }
        if (gv.equals(GIOPVersion.V1_1)) {
            return new ReplyMessage_1_1(orb, service_contexts, request_id, reply_status, ior);
        }
        if (gv.equals(GIOPVersion.V1_2)) {
            ReplyMessage_1_2 msg = new ReplyMessage_1_2(orb, request_id, reply_status, service_contexts, ior);
            msg.setEncodingVersion(encodingVersion);
            return msg;
        }
        throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
    }

    public static LocateRequestMessage createLocateRequest(ORB orb, GIOPVersion gv, byte encodingVersion, int request_id, byte[] object_key) {
        if (gv.equals(GIOPVersion.V1_0)) {
            return new LocateRequestMessage_1_0(orb, request_id, object_key);
        }
        if (gv.equals(GIOPVersion.V1_1)) {
            return new LocateRequestMessage_1_1(orb, request_id, object_key);
        }
        if (gv.equals(GIOPVersion.V1_2)) {
            TargetAddress target = new TargetAddress();
            target.object_key(object_key);
            LocateRequestMessage_1_2 msg = new LocateRequestMessage_1_2(orb, request_id, target);
            msg.setEncodingVersion(encodingVersion);
            return msg;
        }
        throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
    }

    public static LocateReplyMessage createLocateReply(ORB orb, GIOPVersion gv, byte encodingVersion, int request_id, int locate_status, IOR ior) {
        if (gv.equals(GIOPVersion.V1_0)) {
            return new LocateReplyMessage_1_0(orb, request_id, locate_status, ior);
        }
        if (gv.equals(GIOPVersion.V1_1)) {
            return new LocateReplyMessage_1_1(orb, request_id, locate_status, ior);
        }
        if (gv.equals(GIOPVersion.V1_2)) {
            LocateReplyMessage_1_2 msg = new LocateReplyMessage_1_2(orb, request_id, locate_status, ior);
            msg.setEncodingVersion(encodingVersion);
            return msg;
        }
        throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
    }

    public static CancelRequestMessage createCancelRequest(GIOPVersion gv, int request_id) {
        if (gv.equals(GIOPVersion.V1_0)) {
            return new CancelRequestMessage_1_0(request_id);
        }
        if (gv.equals(GIOPVersion.V1_1)) {
            return new CancelRequestMessage_1_1(request_id);
        }
        if (gv.equals(GIOPVersion.V1_2)) {
            return new CancelRequestMessage_1_2(request_id);
        }
        throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
    }

    public static Message createCloseConnection(GIOPVersion gv) {
        if (gv.equals(GIOPVersion.V1_0)) {
            return new Message_1_0(1195986768, false, 5, 0);
        }
        if (gv.equals(GIOPVersion.V1_1)) {
            return new Message_1_1(1195986768, GIOPVersion.V1_1, 0, 5, 0);
        }
        if (gv.equals(GIOPVersion.V1_2)) {
            return new Message_1_1(1195986768, GIOPVersion.V1_2, 0, 5, 0);
        }
        throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
    }

    public static Message createMessageError(GIOPVersion gv) {
        if (gv.equals(GIOPVersion.V1_0)) {
            return new Message_1_0(1195986768, false, 6, 0);
        }
        if (gv.equals(GIOPVersion.V1_1)) {
            return new Message_1_1(1195986768, GIOPVersion.V1_1, 0, 6, 0);
        }
        if (gv.equals(GIOPVersion.V1_2)) {
            return new Message_1_1(1195986768, GIOPVersion.V1_2, 0, 6, 0);
        }
        throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
    }

    public static FragmentMessage createFragmentMessage(GIOPVersion gv) {
        return null;
    }

    public static int getRequestId(Message msg) {
        switch (msg.getType()) {
            case 0: {
                return ((RequestMessage)msg).getRequestId();
            }
            case 1: {
                return ((ReplyMessage)msg).getRequestId();
            }
            case 3: {
                return ((LocateRequestMessage)msg).getRequestId();
            }
            case 4: {
                return ((LocateReplyMessage)msg).getRequestId();
            }
            case 2: {
                return ((CancelRequestMessage)msg).getRequestId();
            }
            case 7: {
                return ((FragmentMessage)msg).getRequestId();
            }
        }
        throw wrapper.illegalGiopMsgType(CompletionStatus.COMPLETED_MAYBE);
    }

    public static void setFlag(ByteBuffer byteBuffer, int flag) {
        byte b = byteBuffer.get(6);
        b = (byte)(b | flag);
        byteBuffer.put(6, b);
    }

    public static void clearFlag(byte[] buf, int flag) {
        buf[6] = (byte)(buf[6] & (0xFF ^ flag));
    }

    private static void AreFragmentsAllowed(byte major, byte minor, byte flag, byte msgType) {
        if (major == 1 && minor == 0 && msgType == 7) {
            throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
        }
        if ((flag & 2) == 2) {
            switch (msgType) {
                case 2: 
                case 5: 
                case 6: {
                    throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
                }
                case 3: 
                case 4: {
                    if (major != 1 || minor != 1) break;
                    throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
                }
            }
        }
    }

    static ObjectKey extractObjectKey(byte[] objKey, ORB orb) {
        try {
            ObjectKey objectKey;
            if (objKey != null && (objectKey = orb.getObjectKeyFactory().create(objKey)) != null) {
                return objectKey;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        throw wrapper.invalidObjectKey();
    }

    static ObjectKey extractObjectKey(TargetAddress target, ORB orb) {
        short orbTargetAddrPref = orb.getORBData().getGIOPTargetAddressPreference();
        short reqAddrDisp = target.discriminator();
        switch (orbTargetAddrPref) {
            case 0: {
                if (reqAddrDisp == 0) break;
                throw new AddressingDispositionException(0);
            }
            case 1: {
                if (reqAddrDisp == 1) break;
                throw new AddressingDispositionException(1);
            }
            case 2: {
                if (reqAddrDisp == 2) break;
                throw new AddressingDispositionException(2);
            }
            case 3: {
                break;
            }
            default: {
                throw wrapper.orbTargetAddrPreferenceInExtractObjectkeyInvalid();
            }
        }
        try {
            switch (reqAddrDisp) {
                case 0: {
                    byte[] objKey = target.object_key();
                    if (objKey == null) break;
                    ObjectKey objectKey = orb.getObjectKeyFactory().create(objKey);
                    if (objectKey != null) {
                        return objectKey;
                    }
                    break;
                }
                case 1: {
                    IIOPProfile iiopProfile = null;
                    TaggedProfile profile = target.profile();
                    if (profile == null) break;
                    iiopProfile = IIOPFactories.makeIIOPProfile(orb, profile);
                    ObjectKey objectKey = iiopProfile.getObjectKey();
                    if (objectKey != null) {
                        return objectKey;
                    }
                    break;
                }
                case 2: {
                    IORAddressingInfo iorInfo = target.ior();
                    if (iorInfo == null) break;
                    TaggedProfile profile = iorInfo.ior.profiles[iorInfo.selected_profile_index];
                    IIOPProfile iiopProfile = IIOPFactories.makeIIOPProfile(orb, profile);
                    ObjectKey objectKey = iiopProfile.getObjectKey();
                    if (objectKey != null) {
                        return objectKey;
                    }
                    break;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        throw wrapper.invalidObjectKey();
    }

    private static int readSize(byte b1, byte b2, byte b3, byte b4, boolean littleEndian) {
        int a4;
        int a3;
        int a2;
        int a1;
        if (!littleEndian) {
            a1 = b1 << 24 & 0xFF000000;
            a2 = b2 << 16 & 0xFF0000;
            a3 = b3 << 8 & 0xFF00;
            a4 = b4 << 0 & 0xFF;
        } else {
            a1 = b4 << 24 & 0xFF000000;
            a2 = b3 << 16 & 0xFF0000;
            a3 = b2 << 8 & 0xFF00;
            a4 = b1 << 0 & 0xFF;
        }
        return a1 | a2 | a3 | a4;
    }

    static void nullCheck(Object obj) {
        if (obj == null) {
            throw wrapper.nullNotAllowed();
        }
    }

    static SystemException getSystemException(String exClassName, int minorCode, CompletionStatus completionStatus, String message, ORBUtilSystemException wrapper) {
        SystemException sysEx = null;
        try {
            Class<?> clazz = ORBClassLoader.loadClass(exClassName);
            if (message == null) {
                sysEx = (SystemException)clazz.newInstance();
            } else {
                Class[] types = new Class[]{String.class};
                Constructor<?> constructor = clazz.getConstructor(types);
                Object[] args2 = new Object[]{message};
                sysEx = (SystemException)constructor.newInstance(args2);
            }
        }
        catch (Exception someEx) {
            throw wrapper.badSystemExceptionInReply(CompletionStatus.COMPLETED_MAYBE, someEx);
        }
        sysEx.minor = minorCode;
        sysEx.completed = completionStatus;
        return sysEx;
    }

    @Override
    public void callback(MessageHandler handler) throws IOException {
        handler.handleInput(this);
    }

    @Override
    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    @Override
    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    @Override
    public int getThreadPoolToUse() {
        return this.threadPoolToUse;
    }

    @Override
    public byte getEncodingVersion() {
        return this.encodingVersion;
    }

    @Override
    public void setEncodingVersion(byte version) {
        this.encodingVersion = version;
    }

    private static void dprint(String msg) {
        ORBUtility.dprint("MessageBase", msg);
    }
}

