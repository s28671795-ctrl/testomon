/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.ResultCode;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.ResponseControl;

public class VirtualListViewResponseControl
extends AbstractControl
implements ResponseControl {
    public static final String OID = "2.16.840.1.113730.3.4.10";
    private static final int HASH_CODE_SEED = 10709;
    private int targetPosition;
    private int contentCount;
    private ResultCode viewResult;
    private byte[] contextID;

    public VirtualListViewResponseControl() {
        super(OID);
    }

    public VirtualListViewResponseControl(boolean critical) {
        super(OID, critical);
    }

    public VirtualListViewResponseControl(int position, int count, ResultCode code, byte[] context) {
        this(position, count, code, context, false);
    }

    public VirtualListViewResponseControl(int position, int count, ResultCode code, byte[] context, boolean critical) {
        super(OID, critical);
        this.setTargetPosition(position);
        this.setContentCount(count);
        this.setViewResult(code);
        this.setContextID(context);
    }

    public int getTargetPosition() {
        return this.targetPosition;
    }

    public void setTargetPosition(int position) {
        this.targetPosition = position;
    }

    public int getContentCount() {
        return this.contentCount;
    }

    public void setContentCount(int count) {
        this.contentCount = count;
    }

    public ResultCode getViewResult() {
        return this.viewResult;
    }

    public void setViewResult(ResultCode code) {
        this.viewResult = code;
    }

    public byte[] getContextID() {
        return this.contextID;
    }

    public void setContextID(byte[] id) {
        this.contextID = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof VirtualListViewResponseControl && super.equals(o)) {
            VirtualListViewResponseControl v = (VirtualListViewResponseControl)o;
            return LdapUtils.areEqual(this.targetPosition, v.targetPosition) && LdapUtils.areEqual(this.contentCount, v.contentCount) && LdapUtils.areEqual((Object)this.viewResult, (Object)v.viewResult) && LdapUtils.areEqual(this.contextID, v.contextID);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10709, new Object[]{this.getOID(), this.getCriticality(), this.targetPosition, this.contentCount, this.viewResult, this.contextID});
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "targetPosition=" + this.targetPosition + ", " + "contentCount=" + this.contentCount + ", " + "viewResult=" + (Object)((Object)this.viewResult) + ", " + "contextID=" + LdapUtils.base64Encode(this.contextID) + "]";
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(TargetPositionHandler.PATH, new TargetPositionHandler(this));
        parser.registerHandler(ContentCountHandler.PATH, new ContentCountHandler(this));
        parser.registerHandler(ViewResultHandler.PATH, new ViewResultHandler(this));
        parser.registerHandler(ContextIDHandler.PATH, new ContextIDHandler(this));
        parser.parse(encoded);
    }

    private static class ContextIDHandler
    extends AbstractParseHandler<VirtualListViewResponseControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[3]");

        ContextIDHandler(VirtualListViewResponseControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((VirtualListViewResponseControl)this.getObject()).setContextID(cookie);
            }
        }
    }

    private static class ViewResultHandler
    extends AbstractParseHandler<VirtualListViewResponseControl> {
        public static final DERPath PATH = new DERPath("/SEQ/ENUM[2]");

        ViewResultHandler(VirtualListViewResponseControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            int resultValue = IntegerType.decode(encoded).intValue();
            ResultCode rc = ResultCode.valueOf(resultValue);
            if (rc == null) {
                throw new IllegalArgumentException("Unknown result code " + resultValue);
            }
            ((VirtualListViewResponseControl)this.getObject()).setViewResult(rc);
        }
    }

    private static class ContentCountHandler
    extends AbstractParseHandler<VirtualListViewResponseControl> {
        public static final DERPath PATH = new DERPath("/SEQ/INT[1]");

        ContentCountHandler(VirtualListViewResponseControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((VirtualListViewResponseControl)this.getObject()).setContentCount(IntegerType.decode(encoded).intValue());
        }
    }

    private static class TargetPositionHandler
    extends AbstractParseHandler<VirtualListViewResponseControl> {
        public static final DERPath PATH = new DERPath("/SEQ/INT[0]");

        TargetPositionHandler(VirtualListViewResponseControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((VirtualListViewResponseControl)this.getObject()).setTargetPosition(IntegerType.decode(encoded).intValue());
        }
    }
}

