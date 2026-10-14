/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.event;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2={"Lio/javalin/event/JavalinEvent;", "", "(Ljava/lang/String;I)V", "SERVER_STARTING", "SERVER_STARTED", "SERVER_START_FAILED", "SERVER_STOP_FAILED", "SERVER_STOPPING", "SERVER_STOPPED", "javalin"})
public final class JavalinEvent
extends Enum<JavalinEvent> {
    public static final /* enum */ JavalinEvent SERVER_STARTING = new JavalinEvent();
    public static final /* enum */ JavalinEvent SERVER_STARTED = new JavalinEvent();
    public static final /* enum */ JavalinEvent SERVER_START_FAILED = new JavalinEvent();
    public static final /* enum */ JavalinEvent SERVER_STOP_FAILED = new JavalinEvent();
    public static final /* enum */ JavalinEvent SERVER_STOPPING = new JavalinEvent();
    public static final /* enum */ JavalinEvent SERVER_STOPPED = new JavalinEvent();
    private static final /* synthetic */ JavalinEvent[] $VALUES;

    public static JavalinEvent[] values() {
        return (JavalinEvent[])$VALUES.clone();
    }

    public static JavalinEvent valueOf(String value) {
        return Enum.valueOf(JavalinEvent.class, value);
    }

    static {
        $VALUES = javalinEventArray = new JavalinEvent[]{JavalinEvent.SERVER_STARTING, JavalinEvent.SERVER_STARTED, JavalinEvent.SERVER_START_FAILED, JavalinEvent.SERVER_STOP_FAILED, JavalinEvent.SERVER_STOPPING, JavalinEvent.SERVER_STOPPED};
    }
}

