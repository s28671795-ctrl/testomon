/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import java.util.ArrayList;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;
import org.ldaptive.control.ResponseControl;

public class SessionTrackingControl
extends AbstractControl
implements RequestControl,
ResponseControl {
    public static final String OID = "1.3.6.1.4.1.21008.108.63.1";
    public static final String RADIUS_ACCT_OID = "1.3.6.1.4.1.21008.108.63.1.1";
    public static final String RADIUS_ACCT_MULTI_OID = "1.3.6.1.4.1.21008.108.63.1.2";
    public static final String USERNAME_ACCT_OID = "1.3.6.1.4.1.21008.108.63.1.3";
    private static final int HASH_CODE_SEED = 7027;
    private String sessionSourceIp;
    private String sessionSourceName;
    private String formatOID;
    private String sessionTrackingIdentifier;

    public SessionTrackingControl() {
        super(OID);
    }

    public SessionTrackingControl(boolean critical) {
        super(OID, critical);
    }

    public SessionTrackingControl(String sourceIP, String sourceName, String oid, String trackingIdentifier) {
        this(sourceIP, sourceName, oid, trackingIdentifier, false);
    }

    public SessionTrackingControl(String sourceIP, String sourceName, String oid, String trackingIdentifier, boolean critical) {
        super(OID, critical);
        this.setSessionSourceIp(sourceIP);
        this.setSessionSourceName(sourceName);
        this.setFormatOID(oid);
        this.setSessionTrackingIdentifier(trackingIdentifier);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public String getSessionSourceIp() {
        return this.sessionSourceIp;
    }

    public void setSessionSourceIp(String s2) {
        this.sessionSourceIp = s2;
    }

    public String getSessionSourceName() {
        return this.sessionSourceName;
    }

    public void setSessionSourceName(String s2) {
        this.sessionSourceName = s2;
    }

    public String getFormatOID() {
        return this.formatOID;
    }

    public void setFormatOID(String s2) {
        this.formatOID = s2;
    }

    public String getSessionTrackingIdentifier() {
        return this.sessionTrackingIdentifier;
    }

    public void setSessionTrackingIdentifier(String s2) {
        this.sessionTrackingIdentifier = s2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SessionTrackingControl && super.equals(o)) {
            SessionTrackingControl v = (SessionTrackingControl)o;
            return LdapUtils.areEqual(this.sessionSourceIp, v.sessionSourceIp) && LdapUtils.areEqual(this.sessionSourceName, v.sessionSourceName) && LdapUtils.areEqual(this.formatOID, v.formatOID) && LdapUtils.areEqual(this.sessionTrackingIdentifier, v.sessionTrackingIdentifier);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(7027, this.getOID(), this.getCriticality(), this.sessionSourceIp, this.sessionSourceName, this.formatOID, this.sessionTrackingIdentifier);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "sessionSourceIp=" + this.sessionSourceIp + ", " + "sessionSourceName=" + this.sessionSourceName + ", " + "formatOID=" + this.formatOID + ", " + "sessionTrackingIdentifier=" + this.sessionTrackingIdentifier + "]";
    }

    @Override
    public byte[] encode() {
        ArrayList<OctetStringType> l = new ArrayList<OctetStringType>();
        l.add(new OctetStringType(this.getSessionSourceIp()));
        l.add(new OctetStringType(this.getSessionSourceName()));
        l.add(new OctetStringType(this.getFormatOID()));
        l.add(new OctetStringType(this.getSessionTrackingIdentifier()));
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, l.toArray(new DEREncoder[0]));
        return se.encode();
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(SourceIpHandler.PATH, new SourceIpHandler(this));
        parser.registerHandler(SourceNameHandler.PATH, new SourceNameHandler(this));
        parser.registerHandler(FormatOIDHandler.PATH, new FormatOIDHandler(this));
        parser.registerHandler(TrackingIdentifierHandler.PATH, new TrackingIdentifierHandler(this));
        parser.parse(encoded);
    }

    private static class TrackingIdentifierHandler
    extends AbstractParseHandler<SessionTrackingControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[3]");

        TrackingIdentifierHandler(SessionTrackingControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SessionTrackingControl)this.getObject()).setSessionTrackingIdentifier(OctetStringType.decode(encoded));
        }
    }

    private static class FormatOIDHandler
    extends AbstractParseHandler<SessionTrackingControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[2]");

        FormatOIDHandler(SessionTrackingControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SessionTrackingControl)this.getObject()).setFormatOID(OctetStringType.decode(encoded));
        }
    }

    private static class SourceNameHandler
    extends AbstractParseHandler<SessionTrackingControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[1]");

        SourceNameHandler(SessionTrackingControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SessionTrackingControl)this.getObject()).setSessionSourceName(OctetStringType.decode(encoded));
        }
    }

    private static class SourceIpHandler
    extends AbstractParseHandler<SessionTrackingControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[0]");

        SourceIpHandler(SessionTrackingControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SessionTrackingControl)this.getObject()).setSessionSourceIp(OctetStringType.decode(encoded));
        }
    }
}

