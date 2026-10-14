/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.impl.DSL;

public enum DatePart {
    YEAR("year"),
    MONTH("month"),
    DAY("day"),
    HOUR("hour"),
    MINUTE("minute"),
    SECOND("second"),
    MILLISECOND("millisecond"),
    MICROSECOND("microsecond"),
    NANOSECOND("nanosecond"),
    MILLENNIUM("millennium"),
    CENTURY("century"),
    DECADE("decade"),
    EPOCH("epoch"),
    QUARTER("quarter"),
    WEEK("week"),
    DAY_OF_YEAR("day_of_year"),
    DAY_OF_WEEK("day_of_week"),
    ISO_DAY_OF_WEEK("iso_day_of_week"),
    TIMEZONE("timezone"),
    TIMEZONE_HOUR("timezone_hour"),
    TIMEZONE_MINUTE("timezone_minute");

    private final String sql;
    private final Keyword keyword;
    private final Name name;

    private DatePart(String sql) {
        this.sql = sql;
        this.keyword = DSL.keyword(sql);
        this.name = DSL.unquotedName(sql);
    }

    public final String toSQL() {
        return this.sql;
    }

    public final Keyword toKeyword() {
        return this.keyword;
    }

    public final Name toName() {
        return this.name;
    }
}

