/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a-\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u0001*\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0002\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\u0006"}, d2={"findByPath", "T", "", "", "requestPath", "(Ljava/util/Map;Ljava/lang/String;)Ljava/lang/Object;", "javalin"})
@SourceDebugExtension(value={"SMAP\nSinglePageHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SinglePageHandler.kt\nio/javalin/http/SinglePageHandlerKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,61:1\n1#2:62\n*E\n"})
public final class SinglePageHandlerKt {
    private static final <T> T findByPath(Map<String, ? extends T> $this$findByPath, String requestPath) {
        T t2;
        Object v0;
        block3: {
            Iterable iterable = $this$findByPath.keySet();
            for (Object t3 : iterable) {
                String it = (String)t3;
                boolean bl = false;
                if (!StringsKt.startsWith$default(requestPath, it, false, 2, null)) continue;
                v0 = t3;
                break block3;
            }
            v0 = null;
        }
        String string = v0;
        if (string != null) {
            String it = string;
            boolean bl = false;
            T t4 = $this$findByPath.get(it);
            t2 = t4;
            Intrinsics.checkNotNull(t4);
        } else {
            t2 = null;
        }
        return t2;
    }

    public static final /* synthetic */ Object access$findByPath(Map $receiver, String requestPath) {
        return SinglePageHandlerKt.findByPath($receiver, requestPath);
    }
}

