/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lio/javalin/http/servlet/SubmitOrder;", "", "(Ljava/lang/String;I)V", "FIRST", "LAST", "javalin"})
public final class SubmitOrder
extends Enum<SubmitOrder> {
    public static final /* enum */ SubmitOrder FIRST = new SubmitOrder();
    public static final /* enum */ SubmitOrder LAST = new SubmitOrder();
    private static final /* synthetic */ SubmitOrder[] $VALUES;

    public static SubmitOrder[] values() {
        return (SubmitOrder[])$VALUES.clone();
    }

    public static SubmitOrder valueOf(String value) {
        return Enum.valueOf(SubmitOrder.class, value);
    }

    static {
        $VALUES = submitOrderArray = new SubmitOrder[]{SubmitOrder.FIRST, SubmitOrder.LAST};
    }
}

