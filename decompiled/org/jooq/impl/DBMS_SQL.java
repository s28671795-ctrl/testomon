/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.Collection;
import org.jooq.Pro;
import org.jooq.Schema;
import org.jooq.impl.ArrayRecordImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.PackageImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SchemaImpl;

@Pro
final class DBMS_SQL {
    static final Schema SYS = new SchemaImpl(DSL.unquotedName("SYS"));

    DBMS_SQL() {
    }

    static final class TIMESTAMP_WITH_TIME_ZONE_TABLE
    extends ArrayRecordImpl<OffsetDateTime> {
        TIMESTAMP_WITH_TIME_ZONE_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "TIMESTAMP_WITH_TIME_ZONE_TABLE", SQLDataType.TIMESTAMPWITHTIMEZONE);
        }

        TIMESTAMP_WITH_TIME_ZONE_TABLE(OffsetDateTime ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        TIMESTAMP_WITH_TIME_ZONE_TABLE(Collection<? extends OffsetDateTime> collection) {
            this();
            this.addAll(collection);
        }
    }

    static final class TIME_WITH_TIME_ZONE_TABLE
    extends ArrayRecordImpl<OffsetTime> {
        TIME_WITH_TIME_ZONE_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "TIME_WITH_TIME_ZONE_TABLE", SQLDataType.TIMEWITHTIMEZONE);
        }

        TIME_WITH_TIME_ZONE_TABLE(OffsetTime ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        TIME_WITH_TIME_ZONE_TABLE(Collection<? extends OffsetTime> collection) {
            this();
            this.addAll(collection);
        }
    }

    static final class TIMESTAMP_TABLE
    extends ArrayRecordImpl<Timestamp> {
        TIMESTAMP_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "TIMESTAMP_TABLE", SQLDataType.TIMESTAMP);
        }

        TIMESTAMP_TABLE(Timestamp ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        TIMESTAMP_TABLE(Collection<? extends Timestamp> collection) {
            this();
            this.addAll(collection);
        }
    }

    static final class TIME_TABLE
    extends ArrayRecordImpl<Time> {
        TIME_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "TIME_TABLE", SQLDataType.TIME);
        }

        TIME_TABLE(Time ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        TIME_TABLE(Collection<? extends Time> collection) {
            this();
            this.addAll(collection);
        }
    }

    static final class DATE_TABLE
    extends ArrayRecordImpl<Date> {
        DATE_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "DATE_TABLE", SQLDataType.DATE);
        }

        DATE_TABLE(Date ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        DATE_TABLE(Collection<? extends Date> collection) {
            this();
            this.addAll(collection);
        }
    }

    static final class CLOB_TABLE
    extends ArrayRecordImpl<String> {
        CLOB_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "CLOB_TABLE", SQLDataType.CLOB);
        }

        CLOB_TABLE(String ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        CLOB_TABLE(Collection<? extends String> collection) {
            this();
            this.addAll(collection);
        }
    }

    static final class BLOB_TABLE
    extends ArrayRecordImpl<byte[]> {
        BLOB_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "BLOB_TABLE", SQLDataType.BLOB);
        }

        BLOB_TABLE(byte[] ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        BLOB_TABLE(Collection<? extends byte[]> collection) {
            this();
            this.addAll(collection);
        }
    }

    static final class VARCHAR2_TABLE
    extends ArrayRecordImpl<String> {
        VARCHAR2_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "VARCHAR2_TABLE", SQLDataType.VARCHAR);
        }

        VARCHAR2_TABLE(String ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        VARCHAR2_TABLE(Collection<? extends String> collection) {
            this();
            this.addAll(collection);
        }
    }

    static final class NUMBER_TABLE
    extends ArrayRecordImpl<Integer> {
        NUMBER_TABLE() {
            super(SYS, new PackageImpl("DBMS_SQL", SYS), "NUMBER_TABLE", SQLDataType.INTEGER);
        }

        NUMBER_TABLE(Integer ... array) {
            this();
            if (array != null) {
                this.addAll(Arrays.asList(array));
            }
        }

        NUMBER_TABLE(Collection<? extends Integer> collection) {
            this();
            this.addAll(collection);
        }
    }
}

