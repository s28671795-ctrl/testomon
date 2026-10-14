/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad.control;

import java.util.HashMap;
import java.util.Map;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.RequestControl;
import org.ldaptive.control.ResponseControl;

public class GetStatsControl
extends AbstractControl
implements RequestControl,
ResponseControl {
    public static final String OID = "1.2.840.113556.1.4.970";
    private static final int HASH_CODE_SEED = 929;
    private static final DERPath THREAD_COUNT_PATH = new DERPath("/SEQ/INT[1]");
    private static final DERPath CALL_TIME_PATH = new DERPath("/SEQ/INT[3]");
    private static final DERPath ENTRIES_RETURNED_PATH = new DERPath("/SEQ/INT[5]");
    private static final DERPath ENTRIES_VISITED_PATH = new DERPath("/SEQ/INT[7]");
    private static final DERPath FILTER_PATH = new DERPath("/SEQ/OCTSTR[9]");
    private static final DERPath INDEX_PATH = new DERPath("/SEQ/OCTSTR[11]");
    private static final DERPath PAGES_REFERENCED_PATH = new DERPath("/SEQ/INT[13]");
    private static final DERPath PAGES_READ_PATH = new DERPath("/SEQ/INT[15]");
    private static final DERPath PAGES_PREREAD_PATH = new DERPath("/SEQ/INT[17]");
    private static final DERPath PAGES_DIRTIED_PATH = new DERPath("/SEQ/INT[19]");
    private static final DERPath PAGES_REDIRTIED_PATH = new DERPath("/SEQ/INT[21]");
    private static final DERPath LOG_RECORD_COUNT_PATH = new DERPath("/SEQ/INT[23]");
    private static final DERPath LOG_RECORD_BYTES_PATH = new DERPath("/SEQ/INT[25]");
    private final Map<String, Object> statistics = new HashMap<String, Object>();

    public GetStatsControl() {
        super(OID);
    }

    public GetStatsControl(boolean critical) {
        super(OID, critical);
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    public Map<String, Object> getStatistics() {
        return this.statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof GetStatsControl && super.equals(o)) {
            GetStatsControl v = (GetStatsControl)o;
            return LdapUtils.areEqual(this.statistics, v.statistics);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(929, this.getOID(), this.getCriticality(), this.statistics);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "statistics=" + this.statistics + "]";
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(THREAD_COUNT_PATH, new IntegerHandler(this, "threadCount"));
        parser.registerHandler(CALL_TIME_PATH, new IntegerHandler(this, "callTime"));
        parser.registerHandler(ENTRIES_RETURNED_PATH, new IntegerHandler(this, "entriesReturned"));
        parser.registerHandler(ENTRIES_VISITED_PATH, new IntegerHandler(this, "entriesVisited"));
        parser.registerHandler(FILTER_PATH, new StringHandler(this, "filter"));
        parser.registerHandler(INDEX_PATH, new StringHandler(this, "index"));
        parser.registerHandler(PAGES_REFERENCED_PATH, new IntegerHandler(this, "pagesReferenced"));
        parser.registerHandler(PAGES_READ_PATH, new IntegerHandler(this, "pagesRead"));
        parser.registerHandler(PAGES_PREREAD_PATH, new IntegerHandler(this, "pagesPreread"));
        parser.registerHandler(PAGES_DIRTIED_PATH, new IntegerHandler(this, "pagesDirtied"));
        parser.registerHandler(PAGES_REDIRTIED_PATH, new IntegerHandler(this, "pagesRedirtied"));
        parser.registerHandler(LOG_RECORD_COUNT_PATH, new IntegerHandler(this, "logRecordCount"));
        parser.registerHandler(LOG_RECORD_BYTES_PATH, new IntegerHandler(this, "logRecordBytes"));
        parser.parse(encoded);
    }

    private static class StringHandler
    extends AbstractParseHandler<GetStatsControl> {
        private final String statName;

        StringHandler(GetStatsControl control, String name) {
            super(control);
            this.statName = name;
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((GetStatsControl)this.getObject()).getStatistics().put(this.statName, OctetStringType.decode(encoded).trim());
        }
    }

    private static class IntegerHandler
    extends AbstractParseHandler<GetStatsControl> {
        private final String statName;

        IntegerHandler(GetStatsControl control, String name) {
            super(control);
            this.statName = name;
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((GetStatsControl)this.getObject()).getStatistics().put(this.statName, IntegerType.decode(encoded).intValue());
        }
    }
}

