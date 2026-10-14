/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.staticfiles;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lio/javalin/http/staticfiles/Location;", "", "(Ljava/lang/String;I)V", "CLASSPATH", "EXTERNAL", "javalin"})
public final class Location
extends Enum<Location> {
    public static final /* enum */ Location CLASSPATH = new Location();
    public static final /* enum */ Location EXTERNAL = new Location();
    private static final /* synthetic */ Location[] $VALUES;

    public static Location[] values() {
        return (Location[])$VALUES.clone();
    }

    public static Location valueOf(String value) {
        return Enum.valueOf(Location.class, value);
    }

    static {
        $VALUES = locationArray = new Location[]{Location.CLASSPATH, Location.EXTERNAL};
    }
}

