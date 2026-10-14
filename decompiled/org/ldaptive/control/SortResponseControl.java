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
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.ResponseControl;

public class SortResponseControl
extends AbstractControl
implements ResponseControl {
    public static final String OID = "1.2.840.113556.1.4.474";
    private static final int HASH_CODE_SEED = 733;
    private ResultCode sortResult;
    private String attributeName;

    public SortResponseControl() {
        super(OID);
    }

    public SortResponseControl(boolean critical) {
        super(OID, critical);
    }

    public SortResponseControl(ResultCode code, boolean critical) {
        super(OID, critical);
        this.setSortResult(code);
    }

    public SortResponseControl(ResultCode code, String attrName, boolean critical) {
        super(OID, critical);
        this.setSortResult(code);
        this.setAttributeName(attrName);
    }

    public ResultCode getSortResult() {
        return this.sortResult;
    }

    public void setSortResult(ResultCode code) {
        this.sortResult = code;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public void setAttributeName(String name) {
        this.attributeName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SortResponseControl && super.equals(o)) {
            SortResponseControl v = (SortResponseControl)o;
            return LdapUtils.areEqual((Object)this.sortResult, (Object)v.sortResult) && LdapUtils.areEqual(this.attributeName, v.attributeName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(733, new Object[]{this.getOID(), this.getCriticality(), this.sortResult, this.attributeName});
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "sortResult=" + (Object)((Object)this.sortResult) + ", " + "attributeName=" + this.attributeName + "]";
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(SortResultHandler.PATH, new SortResultHandler(this));
        parser.registerHandler(AttributeTypeHandler.PATH, new AttributeTypeHandler(this));
        parser.parse(encoded);
    }

    private static class AttributeTypeHandler
    extends AbstractParseHandler<SortResponseControl> {
        public static final DERPath PATH = new DERPath("/SEQ/CTX(1)");

        AttributeTypeHandler(SortResponseControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SortResponseControl)this.getObject()).setAttributeName(OctetStringType.decode(encoded));
        }
    }

    private static class SortResultHandler
    extends AbstractParseHandler<SortResponseControl> {
        public static final DERPath PATH = new DERPath("/SEQ/ENUM[0]");

        SortResultHandler(SortResponseControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            int resultValue = IntegerType.decode(encoded).intValue();
            ResultCode rc = ResultCode.valueOf(resultValue);
            if (rc == null) {
                throw new IllegalArgumentException("Unknown result code " + resultValue);
            }
            ((SortResponseControl)this.getObject()).setSortResult(rc);
        }
    }
}

