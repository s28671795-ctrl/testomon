/*
 * Decompiled with CFR 0.152.
 */
package org.h2.api;

public enum IntervalQualifier {
    YEAR,
    MONTH,
    DAY,
    HOUR,
    MINUTE,
    SECOND,
    YEAR_TO_MONTH,
    DAY_TO_HOUR,
    DAY_TO_MINUTE,
    DAY_TO_SECOND,
    HOUR_TO_MINUTE,
    HOUR_TO_SECOND,
    MINUTE_TO_SECOND;

    private final String string = this.name().replace('_', ' ').intern();

    public static IntervalQualifier valueOf(int n) {
        switch (n) {
            case 0: {
                return YEAR;
            }
            case 1: {
                return MONTH;
            }
            case 2: {
                return DAY;
            }
            case 3: {
                return HOUR;
            }
            case 4: {
                return MINUTE;
            }
            case 5: {
                return SECOND;
            }
            case 6: {
                return YEAR_TO_MONTH;
            }
            case 7: {
                return DAY_TO_HOUR;
            }
            case 8: {
                return DAY_TO_MINUTE;
            }
            case 9: {
                return DAY_TO_SECOND;
            }
            case 10: {
                return HOUR_TO_MINUTE;
            }
            case 11: {
                return HOUR_TO_SECOND;
            }
            case 12: {
                return MINUTE_TO_SECOND;
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean isYearMonth() {
        return this == YEAR || this == MONTH || this == YEAR_TO_MONTH;
    }

    public boolean isDayTime() {
        return !this.isYearMonth();
    }

    public boolean hasYears() {
        return this == YEAR || this == YEAR_TO_MONTH;
    }

    public boolean hasMonths() {
        return this == MONTH || this == YEAR_TO_MONTH;
    }

    public boolean hasDays() {
        switch (this) {
            case DAY: 
            case DAY_TO_HOUR: 
            case DAY_TO_MINUTE: 
            case DAY_TO_SECOND: {
                return true;
            }
        }
        return false;
    }

    public boolean hasHours() {
        switch (this) {
            case DAY_TO_HOUR: 
            case DAY_TO_MINUTE: 
            case DAY_TO_SECOND: 
            case HOUR: 
            case HOUR_TO_MINUTE: 
            case HOUR_TO_SECOND: {
                return true;
            }
        }
        return false;
    }

    public boolean hasMinutes() {
        switch (this) {
            case DAY_TO_MINUTE: 
            case DAY_TO_SECOND: 
            case HOUR_TO_MINUTE: 
            case HOUR_TO_SECOND: 
            case MINUTE: 
            case MINUTE_TO_SECOND: {
                return true;
            }
        }
        return false;
    }

    public boolean hasSeconds() {
        switch (this) {
            case DAY_TO_SECOND: 
            case HOUR_TO_SECOND: 
            case MINUTE_TO_SECOND: 
            case SECOND: {
                return true;
            }
        }
        return false;
    }

    public boolean hasMultipleFields() {
        return this.ordinal() > 5;
    }

    public String toString() {
        return this.string;
    }

    public String getTypeName(int n, int n2) {
        return this.getTypeName(new StringBuilder(), n, n2, false).toString();
    }

    public StringBuilder getTypeName(StringBuilder stringBuilder, int n, int n2, boolean bl) {
        if (!bl) {
            stringBuilder.append("INTERVAL ");
        }
        switch (this) {
            case DAY: 
            case HOUR: 
            case MINUTE: 
            case YEAR: 
            case MONTH: {
                stringBuilder.append(this.string);
                if (n <= 0) break;
                stringBuilder.append('(').append(n).append(')');
                break;
            }
            case SECOND: {
                stringBuilder.append(this.string);
                if (n <= 0 && n2 < 0) break;
                stringBuilder.append('(').append(n > 0 ? n : 2);
                if (n2 >= 0) {
                    stringBuilder.append(", ").append(n2);
                }
                stringBuilder.append(')');
                break;
            }
            case YEAR_TO_MONTH: {
                stringBuilder.append("YEAR");
                if (n > 0) {
                    stringBuilder.append('(').append(n).append(')');
                }
                stringBuilder.append(" TO MONTH");
                break;
            }
            case DAY_TO_HOUR: {
                stringBuilder.append("DAY");
                if (n > 0) {
                    stringBuilder.append('(').append(n).append(')');
                }
                stringBuilder.append(" TO HOUR");
                break;
            }
            case DAY_TO_MINUTE: {
                stringBuilder.append("DAY");
                if (n > 0) {
                    stringBuilder.append('(').append(n).append(')');
                }
                stringBuilder.append(" TO MINUTE");
                break;
            }
            case DAY_TO_SECOND: {
                stringBuilder.append("DAY");
                if (n > 0) {
                    stringBuilder.append('(').append(n).append(')');
                }
                stringBuilder.append(" TO SECOND");
                if (n2 < 0) break;
                stringBuilder.append('(').append(n2).append(')');
                break;
            }
            case HOUR_TO_MINUTE: {
                stringBuilder.append("HOUR");
                if (n > 0) {
                    stringBuilder.append('(').append(n).append(')');
                }
                stringBuilder.append(" TO MINUTE");
                break;
            }
            case HOUR_TO_SECOND: {
                stringBuilder.append("HOUR");
                if (n > 0) {
                    stringBuilder.append('(').append(n).append(')');
                }
                stringBuilder.append(" TO SECOND");
                if (n2 < 0) break;
                stringBuilder.append('(').append(n2).append(')');
                break;
            }
            case MINUTE_TO_SECOND: {
                stringBuilder.append("MINUTE");
                if (n > 0) {
                    stringBuilder.append('(').append(n).append(')');
                }
                stringBuilder.append(" TO SECOND");
                if (n2 < 0) break;
                stringBuilder.append('(').append(n2).append(')');
            }
        }
        return stringBuilder;
    }
}

