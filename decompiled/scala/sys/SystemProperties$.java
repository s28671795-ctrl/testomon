/*
 * Decompiled with CFR 0.152.
 */
package scala.sys;

import scala.Function0;
import scala.sys.BooleanProp;
import scala.sys.BooleanProp$;
import scala.sys.SystemProperties;

public final class SystemProperties$ {
    public static final SystemProperties$ MODULE$ = new SystemProperties$();
    private static BooleanProp headless;
    private static BooleanProp preferIPv4Stack;
    private static BooleanProp preferIPv6Addresses;
    private static BooleanProp noTraceSuppression;
    private static volatile byte bitmap$0;

    public synchronized <T> T exclusively(Function0<T> body2) {
        return body2.apply();
    }

    public SystemProperties$ systemPropertiesToCompanion(SystemProperties p) {
        return this;
    }

    private final String HeadlessKey() {
        return "java.awt.headless";
    }

    private final String PreferIPv4StackKey() {
        return "java.net.preferIPv4Stack";
    }

    private final String PreferIPv6AddressesKey() {
        return "java.net.preferIPv6Addresses";
    }

    private final String NoTraceSuppressionKey() {
        return "scala.control.noTraceSuppression";
    }

    public String help(String key) {
        switch (key == null ? 0 : key.hashCode()) {
            case -301073227: {
                if (!"java.awt.headless".equals(key)) break;
                return "system should not utilize a display device";
            }
            case -239081610: {
                if (!"java.net.preferIPv4Stack".equals(key)) break;
                return "system should prefer IPv4 sockets";
            }
            case 1220942952: {
                if (!"scala.control.noTraceSuppression".equals(key)) break;
                return "scala should not suppress any stack trace creation";
            }
            case 1842371534: {
                if (!"java.net.preferIPv6Addresses".equals(key)) break;
                return "system should prefer IPv6 addresses";
            }
        }
        return "";
    }

    private BooleanProp headless$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 1) == 0) {
                headless = BooleanProp$.MODULE$.keyExists("java.awt.headless");
                bitmap$0 = (byte)(bitmap$0 | 1);
            }
        }
        return headless;
    }

    public BooleanProp headless() {
        if ((byte)(bitmap$0 & 1) == 0) {
            return this.headless$lzycompute();
        }
        return headless;
    }

    private BooleanProp preferIPv4Stack$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 2) == 0) {
                preferIPv4Stack = BooleanProp$.MODULE$.keyExists("java.net.preferIPv4Stack");
                bitmap$0 = (byte)(bitmap$0 | 2);
            }
        }
        return preferIPv4Stack;
    }

    public BooleanProp preferIPv4Stack() {
        if ((byte)(bitmap$0 & 2) == 0) {
            return this.preferIPv4Stack$lzycompute();
        }
        return preferIPv4Stack;
    }

    private BooleanProp preferIPv6Addresses$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 4) == 0) {
                preferIPv6Addresses = BooleanProp$.MODULE$.keyExists("java.net.preferIPv6Addresses");
                bitmap$0 = (byte)(bitmap$0 | 4);
            }
        }
        return preferIPv6Addresses;
    }

    public BooleanProp preferIPv6Addresses() {
        if ((byte)(bitmap$0 & 4) == 0) {
            return this.preferIPv6Addresses$lzycompute();
        }
        return preferIPv6Addresses;
    }

    private BooleanProp noTraceSuppression$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 8) == 0) {
                noTraceSuppression = BooleanProp$.MODULE$.valueIsTrue("scala.control.noTraceSuppression");
                bitmap$0 = (byte)(bitmap$0 | 8);
            }
        }
        return noTraceSuppression;
    }

    public BooleanProp noTraceSuppression() {
        if ((byte)(bitmap$0 & 8) == 0) {
            return this.noTraceSuppression$lzycompute();
        }
        return noTraceSuppression;
    }

    private SystemProperties$() {
    }
}

