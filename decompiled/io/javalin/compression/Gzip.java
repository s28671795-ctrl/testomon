/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.compression;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SourceDebugExtension;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lio/javalin/compression/Gzip;", "", "level", "", "(I)V", "getLevel", "()I", "javalin"})
@SourceDebugExtension(value={"SMAP\nGzip.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Gzip.kt\nio/javalin/compression/Gzip\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,10:1\n1#2:11\n*E\n"})
public final class Gzip {
    private final int level;

    public Gzip(int level) {
        this.level = level;
        int n = this.level;
        if (!(0 <= n ? n < 10 : false)) {
            boolean bl = false;
            String string = "Valid range for parameter level is 0 to 9";
            throw new IllegalArgumentException(string.toString());
        }
    }

    public /* synthetic */ Gzip(int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            n = 6;
        }
        this(n);
    }

    public final int getLevel() {
        return this.level;
    }

    public Gzip() {
        this(0, 1, null);
    }
}

