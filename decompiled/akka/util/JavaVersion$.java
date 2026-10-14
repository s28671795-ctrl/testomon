/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import scala.Predef$;
import scala.collection.StringOps$;

@InternalApi
public final class JavaVersion$ {
    public static final JavaVersion$ MODULE$ = new JavaVersion$();
    private static final int majorVersion;

    static {
        String[] version = StringOps$.MODULE$.split$extension(Predef$.MODULE$.augmentString(System.getProperty("java.specification.version")), '.');
        String string = version[0];
        String string2 = "1";
        String majorString = !(string != null ? !string.equals(string2) : string2 != null) ? version[1] : version[0];
        majorVersion = StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(majorString));
    }

    public int majorVersion() {
        return majorVersion;
    }

    private JavaVersion$() {
    }
}

