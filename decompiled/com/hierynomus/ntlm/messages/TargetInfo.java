/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.ntlm.messages;

import com.hierynomus.msdtyp.FileTime;
import com.hierynomus.msdtyp.MsDataTypes;
import com.hierynomus.ntlm.messages.AvId;
import com.hierynomus.protocol.commons.Charsets;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.protocol.commons.buffer.Endian;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetInfo {
    private static final Logger logger = LoggerFactory.getLogger(TargetInfo.class);
    private Map<AvId, Object> targetInfo = new HashMap<AvId, Object>();

    TargetInfo() {
    }

    TargetInfo readFrom(Buffer.PlainBuffer buffer) throws Buffer.BufferException {
        AvId avId;
        block8: while (true) {
            int l = buffer.readUInt16();
            avId = EnumWithValue.EnumUtils.valueOf(l, AvId.class, null);
            logger.trace("NTLM channel contains {}({}) TargetInfo", (Object)avId, (Object)l);
            int avLen = buffer.readUInt16();
            switch (avId) {
                case MsvAvEOL: {
                    return this;
                }
                case MsvAvNbComputerName: 
                case MsvAvNdDomainName: 
                case MsvAvDnsComputerName: 
                case MsvAvDnsDomainName: 
                case MsvAvDnsTreeName: 
                case MsvAvTargetName: {
                    this.targetInfo.put(avId, buffer.readString(Charsets.UTF_16LE, avLen / 2));
                    continue block8;
                }
                case MsvAvFlags: {
                    this.targetInfo.put(avId, buffer.readUInt32(Endian.LE));
                    continue block8;
                }
                case MsvAvTimestamp: {
                    this.targetInfo.put(avId, MsDataTypes.readFileTime(buffer));
                    continue block8;
                }
                case MsvAvSingleHost: {
                    continue block8;
                }
                case MsvChannelBindings: {
                    continue block8;
                }
            }
            break;
        }
        throw new IllegalStateException("Encountered unhandled AvId: " + avId);
    }

    public void writeTo(Buffer.PlainBuffer buffer) {
        block6: for (AvId key : this.targetInfo.keySet()) {
            buffer.putUInt16((int)key.getValue());
            switch (key) {
                case MsvAvNbComputerName: 
                case MsvAvNdDomainName: 
                case MsvAvDnsComputerName: 
                case MsvAvDnsDomainName: 
                case MsvAvDnsTreeName: 
                case MsvAvTargetName: {
                    String val = this.getAvPairString(key);
                    buffer.putUInt16(val.length() * 2);
                    buffer.putString(val, Charsets.UTF_16LE);
                    continue block6;
                }
                case MsvAvFlags: {
                    buffer.putUInt16(4);
                    buffer.putUInt32(((Integer)this.getAvPairObject(key)).intValue(), Endian.LE);
                    continue block6;
                }
                case MsvAvTimestamp: {
                    buffer.putUInt16(8);
                    FileTime ft = (FileTime)this.getAvPairObject(key);
                    MsDataTypes.putFileTime(ft, buffer);
                    continue block6;
                }
                case MsvAvSingleHost: 
                case MsvChannelBindings: {
                    continue block6;
                }
            }
            throw new IllegalStateException("Encountered unhandled AvId: " + key);
        }
        buffer.putUInt16((int)AvId.MsvAvEOL.getValue());
        buffer.putUInt16(0);
    }

    public TargetInfo copy() {
        TargetInfo c = new TargetInfo();
        c.targetInfo = new HashMap<AvId, Object>(this.targetInfo);
        return c;
    }

    public Object getAvPairObject(AvId key) {
        return this.targetInfo.get(key);
    }

    public void putAvPairString(AvId key, String value) {
        this.targetInfo.put(key, value);
    }

    public String getAvPairString(AvId key) {
        Object obj = this.targetInfo.get(key);
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }
}

