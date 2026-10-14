/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.languageFeature;
import scala.languageFeature$dynamics$;
import scala.languageFeature$existentials$;
import scala.languageFeature$higherKinds$;
import scala.languageFeature$implicitConversions$;
import scala.languageFeature$postfixOps$;
import scala.languageFeature$reflectiveCalls$;

public final class language$ {
    public static final language$ MODULE$ = new language$();
    private static languageFeature.dynamics dynamics;
    private static languageFeature.postfixOps postfixOps;
    private static languageFeature.reflectiveCalls reflectiveCalls;
    private static languageFeature.implicitConversions implicitConversions;
    private static languageFeature.higherKinds higherKinds;
    private static languageFeature.existentials existentials;
    private static volatile byte bitmap$0;

    private languageFeature.dynamics dynamics$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 1) == 0) {
                dynamics = languageFeature$dynamics$.MODULE$;
                bitmap$0 = (byte)(bitmap$0 | 1);
            }
        }
        return dynamics;
    }

    public languageFeature.dynamics dynamics() {
        if ((byte)(bitmap$0 & 1) == 0) {
            return this.dynamics$lzycompute();
        }
        return dynamics;
    }

    private languageFeature.postfixOps postfixOps$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 2) == 0) {
                postfixOps = languageFeature$postfixOps$.MODULE$;
                bitmap$0 = (byte)(bitmap$0 | 2);
            }
        }
        return postfixOps;
    }

    public languageFeature.postfixOps postfixOps() {
        if ((byte)(bitmap$0 & 2) == 0) {
            return this.postfixOps$lzycompute();
        }
        return postfixOps;
    }

    private languageFeature.reflectiveCalls reflectiveCalls$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 4) == 0) {
                reflectiveCalls = languageFeature$reflectiveCalls$.MODULE$;
                bitmap$0 = (byte)(bitmap$0 | 4);
            }
        }
        return reflectiveCalls;
    }

    public languageFeature.reflectiveCalls reflectiveCalls() {
        if ((byte)(bitmap$0 & 4) == 0) {
            return this.reflectiveCalls$lzycompute();
        }
        return reflectiveCalls;
    }

    private languageFeature.implicitConversions implicitConversions$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 8) == 0) {
                implicitConversions = languageFeature$implicitConversions$.MODULE$;
                bitmap$0 = (byte)(bitmap$0 | 8);
            }
        }
        return implicitConversions;
    }

    public languageFeature.implicitConversions implicitConversions() {
        if ((byte)(bitmap$0 & 8) == 0) {
            return this.implicitConversions$lzycompute();
        }
        return implicitConversions;
    }

    private languageFeature.higherKinds higherKinds$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 0x10) == 0) {
                higherKinds = languageFeature$higherKinds$.MODULE$;
                bitmap$0 = (byte)(bitmap$0 | 0x10);
            }
        }
        return higherKinds;
    }

    public languageFeature.higherKinds higherKinds() {
        if ((byte)(bitmap$0 & 0x10) == 0) {
            return this.higherKinds$lzycompute();
        }
        return higherKinds;
    }

    private languageFeature.existentials existentials$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 0x20) == 0) {
                existentials = languageFeature$existentials$.MODULE$;
                bitmap$0 = (byte)(bitmap$0 | 0x20);
            }
        }
        return existentials;
    }

    public languageFeature.existentials existentials() {
        if ((byte)(bitmap$0 & 0x20) == 0) {
            return this.existentials$lzycompute();
        }
        return existentials;
    }

    private language$() {
    }
}

