/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lio/javalin/config/SizeUnit;", "", "multiplier", "", "(Ljava/lang/String;II)V", "getMultiplier", "()I", "BYTES", "KB", "MB", "GB", "javalin"})
public final class SizeUnit
extends Enum<SizeUnit> {
    private final int multiplier;
    public static final /* enum */ SizeUnit BYTES = new SizeUnit(1);
    public static final /* enum */ SizeUnit KB = new SizeUnit(1024);
    public static final /* enum */ SizeUnit MB = new SizeUnit(0x100000);
    public static final /* enum */ SizeUnit GB = new SizeUnit(0x40000000);
    private static final /* synthetic */ SizeUnit[] $VALUES;

    private SizeUnit(int multiplier) {
        this.multiplier = multiplier;
    }

    public final int getMultiplier() {
        return this.multiplier;
    }

    public static SizeUnit[] values() {
        return (SizeUnit[])$VALUES.clone();
    }

    public static SizeUnit valueOf(String value) {
        return Enum.valueOf(SizeUnit.class, value);
    }

    static {
        $VALUES = sizeUnitArray = new SizeUnit[]{SizeUnit.BYTES, SizeUnit.KB, SizeUnit.MB, SizeUnit.GB};
    }
}

