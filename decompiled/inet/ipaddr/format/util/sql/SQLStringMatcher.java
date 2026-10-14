/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util.sql;

import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.IPAddressPartConfiguredString;
import inet.ipaddr.format.util.IPAddressStringWriter;
import inet.ipaddr.format.util.sql.IPAddressSQLTranslator;

public class SQLStringMatcher<T extends IPAddressStringDivisionSeries, P extends IPAddressStringWriter<T>, S extends IPAddressPartConfiguredString<T, P>> {
    protected final S networkString;
    private final boolean isEntireAddress;
    private final IPAddressSQLTranslator translator;

    public SQLStringMatcher(S networkString, boolean isEntireAddress, IPAddressSQLTranslator translator) {
        this.networkString = networkString;
        this.translator = translator;
        this.isEntireAddress = isEntireAddress;
        translator.setNetwork(((IPAddressPartConfiguredString)networkString).getString());
    }

    public StringBuilder getSQLCondition(StringBuilder builder, String columnName) {
        String string = ((IPAddressPartConfiguredString)this.networkString).getString();
        if (this.isEntireAddress) {
            this.matchString(builder, columnName, string);
        } else {
            this.matchSubString(builder, columnName, ((IPAddressPartConfiguredString)this.networkString).getTrailingSegmentSeparator(), ((IPAddressPartConfiguredString)this.networkString).getTrailingSeparatorCount() + 1, string);
        }
        return builder;
    }

    protected void matchString(StringBuilder builder, String expression, String match) {
        this.translator.matchString(builder, expression, match);
    }

    protected void matchSubString(StringBuilder builder, String expression, char separator, int separatorCount, String match) {
        this.translator.matchSubString(builder, expression, separator, separatorCount, match);
    }

    protected void matchSeparatorCount(StringBuilder builder, String expression, char separator, int separatorCount) {
        this.translator.matchSeparatorCount(builder, expression, separator, separatorCount);
    }

    protected void boundSeparatorCount(StringBuilder builder, String expression, char separator, int separatorCount) {
        this.translator.boundSeparatorCount(builder, expression, separator, separatorCount);
    }

    public String toString() {
        return this.getSQLCondition(new StringBuilder(), "COLUMN").toString();
    }
}

