/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.PersistentSearchChangeType;
import org.ldaptive.control.ResponseControl;

public class EntryChangeNotificationControl
extends AbstractControl
implements ResponseControl {
    public static final String OID = "2.16.840.1.113730.3.4.7";
    private static final int HASH_CODE_SEED = 773;
    private PersistentSearchChangeType changeType;
    private String previousDn;
    private long changeNumber = -1L;

    public EntryChangeNotificationControl() {
        super(OID);
    }

    public EntryChangeNotificationControl(boolean critical) {
        super(OID, critical);
    }

    public EntryChangeNotificationControl(PersistentSearchChangeType type) {
        this(type, false);
    }

    public EntryChangeNotificationControl(PersistentSearchChangeType type, boolean critical) {
        super(OID, critical);
        this.setChangeType(type);
    }

    public EntryChangeNotificationControl(PersistentSearchChangeType type, String dn, long number) {
        this(type, dn, number, false);
    }

    public EntryChangeNotificationControl(PersistentSearchChangeType type, String dn, long number, boolean critical) {
        super(OID, critical);
        this.setChangeType(type);
        this.setPreviousDn(dn);
        this.setChangeNumber(number);
    }

    public PersistentSearchChangeType getChangeType() {
        return this.changeType;
    }

    public void setChangeType(PersistentSearchChangeType type) {
        this.changeType = type;
    }

    public String getPreviousDn() {
        return this.previousDn;
    }

    public void setPreviousDn(String dn) {
        this.previousDn = dn;
    }

    public long getChangeNumber() {
        return this.changeNumber;
    }

    public void setChangeNumber(long number) {
        this.changeNumber = number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof EntryChangeNotificationControl && super.equals(o)) {
            EntryChangeNotificationControl v = (EntryChangeNotificationControl)o;
            return LdapUtils.areEqual((Object)this.changeType, (Object)v.changeType) && LdapUtils.areEqual(this.previousDn, v.previousDn) && LdapUtils.areEqual(this.changeNumber, v.changeNumber);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(773, new Object[]{this.getOID(), this.getCriticality(), this.changeType, this.previousDn, this.changeNumber});
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "changeType=" + (Object)((Object)this.changeType) + ", " + "previousDn=" + this.previousDn + ", " + "changeNumber=" + this.changeNumber + "]";
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(ChangeTypeHandler.PATH, new ChangeTypeHandler(this));
        parser.registerHandler(PreviousDnHandler.PATH, new PreviousDnHandler(this));
        parser.registerHandler(ChangeNumberHandler.PATH, new ChangeNumberHandler(this));
        parser.parse(encoded);
    }

    private static class ChangeNumberHandler
    extends AbstractParseHandler<EntryChangeNotificationControl> {
        public static final DERPath PATH = new DERPath("/SEQ/INT[2]");

        ChangeNumberHandler(EntryChangeNotificationControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((EntryChangeNotificationControl)this.getObject()).setChangeNumber(IntegerType.decode(encoded).intValue());
        }
    }

    private static class PreviousDnHandler
    extends AbstractParseHandler<EntryChangeNotificationControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[1]");

        PreviousDnHandler(EntryChangeNotificationControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((EntryChangeNotificationControl)this.getObject()).setPreviousDn(OctetStringType.decode(encoded));
        }
    }

    private static class ChangeTypeHandler
    extends AbstractParseHandler<EntryChangeNotificationControl> {
        public static final DERPath PATH = new DERPath("/SEQ/ENUM[0]");

        ChangeTypeHandler(EntryChangeNotificationControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            int typeValue = IntegerType.decode(encoded).intValue();
            PersistentSearchChangeType ct = PersistentSearchChangeType.valueOf(typeValue);
            if (ct == null) {
                throw new IllegalArgumentException("Unknown change type code " + typeValue);
            }
            ((EntryChangeNotificationControl)this.getObject()).setChangeType(ct);
        }
    }
}

