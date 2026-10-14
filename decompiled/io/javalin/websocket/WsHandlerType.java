/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lio/javalin/websocket/WsHandlerType;", "", "(Ljava/lang/String;I)V", "WS_BEFORE", "WEBSOCKET", "WS_AFTER", "javalin"})
public final class WsHandlerType
extends Enum<WsHandlerType> {
    public static final /* enum */ WsHandlerType WS_BEFORE = new WsHandlerType();
    public static final /* enum */ WsHandlerType WEBSOCKET = new WsHandlerType();
    public static final /* enum */ WsHandlerType WS_AFTER = new WsHandlerType();
    private static final /* synthetic */ WsHandlerType[] $VALUES;

    public static WsHandlerType[] values() {
        return (WsHandlerType[])$VALUES.clone();
    }

    public static WsHandlerType valueOf(String value) {
        return Enum.valueOf(WsHandlerType.class, value);
    }

    static {
        $VALUES = wsHandlerTypeArray = new WsHandlerType[]{WsHandlerType.WS_BEFORE, WsHandlerType.WEBSOCKET, WsHandlerType.WS_AFTER};
    }
}

