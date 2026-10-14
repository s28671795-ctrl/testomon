/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.util.Util;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u00a8\u0006\u0004"}, d2={"registerOptionalModule", "Lcom/fasterxml/jackson/databind/ObjectMapper;", "classString", "", "javalin"})
public final class JavalinJacksonKt {
    private static final ObjectMapper registerOptionalModule(ObjectMapper $this$registerOptionalModule, String classString) {
        if (Util.INSTANCE.classExists(classString)) {
            Object obj = Class.forName(classString).getConstructor(new Class[0]).newInstance(new Object[0]);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.fasterxml.jackson.databind.Module");
            $this$registerOptionalModule.registerModule((Module)obj);
        }
        return $this$registerOptionalModule;
    }

    public static final /* synthetic */ ObjectMapper access$registerOptionalModule(ObjectMapper $receiver, String classString) {
        return JavalinJacksonKt.registerOptionalModule($receiver, classString);
    }
}

