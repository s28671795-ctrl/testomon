/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.ObjectStreamException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.IDLEntity;

public final class DayOfTheWeek
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    private int value = -1;
    public static final int _Monday = 0;
    public static final DayOfTheWeek Monday = new DayOfTheWeek(0);
    public static final int _Tuesday = 1;
    public static final DayOfTheWeek Tuesday = new DayOfTheWeek(1);
    public static final int _Wednesday = 2;
    public static final DayOfTheWeek Wednesday = new DayOfTheWeek(2);
    public static final int _Thursday = 3;
    public static final DayOfTheWeek Thursday = new DayOfTheWeek(3);
    public static final int _Friday = 4;
    public static final DayOfTheWeek Friday = new DayOfTheWeek(4);
    public static final int _Saturday = 5;
    public static final DayOfTheWeek Saturday = new DayOfTheWeek(5);
    public static final int _Sunday = 6;
    public static final DayOfTheWeek Sunday = new DayOfTheWeek(6);

    public int value() {
        return this.value;
    }

    public static DayOfTheWeek from_int(int value) {
        switch (value) {
            case 0: {
                return Monday;
            }
            case 1: {
                return Tuesday;
            }
            case 2: {
                return Wednesday;
            }
            case 3: {
                return Thursday;
            }
            case 4: {
                return Friday;
            }
            case 5: {
                return Saturday;
            }
            case 6: {
                return Sunday;
            }
        }
        throw new BAD_PARAM();
    }

    public String toString() {
        switch (this.value) {
            case 0: {
                return "Monday";
            }
            case 1: {
                return "Tuesday";
            }
            case 2: {
                return "Wednesday";
            }
            case 3: {
                return "Thursday";
            }
            case 4: {
                return "Friday";
            }
            case 5: {
                return "Saturday";
            }
            case 6: {
                return "Sunday";
            }
        }
        throw new BAD_PARAM();
    }

    protected DayOfTheWeek(int i) {
        this.value = i;
    }

    Object readResolve() throws ObjectStreamException {
        return DayOfTheWeek.from_int(this.value());
    }
}

