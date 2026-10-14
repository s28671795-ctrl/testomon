/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import io.javalin.util.Util;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0014\u0010\n\u001a\u00020\u000b*\u00020\u00072\u0006\u0010\f\u001a\u00020\tH\u0002\u00a8\u0006\r"}, d2={"Lio/javalin/http/util/ETagGenerator;", "", "()V", "tryWriteEtagAndClose", "", "generatorEnabled", "ctx", "Lio/javalin/http/Context;", "resultStream", "Ljava/io/InputStream;", "closeWith304", "", "inputStream", "javalin"})
@SourceDebugExtension(value={"SMAP\nETagGenerator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ETagGenerator.kt\nio/javalin/http/util/ETagGenerator\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,41:1\n1#2:42\n*E\n"})
public final class ETagGenerator {
    @NotNull
    public static final ETagGenerator INSTANCE = new ETagGenerator();

    private ETagGenerator() {
    }

    public final boolean tryWriteEtagAndClose(boolean generatorEnabled, @NotNull Context ctx, @NotNull InputStream resultStream) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(resultStream, "resultStream");
        String serverEtag = ctx.res().getHeader("ETag");
        String clientEtag = ctx.req().getHeader("If-None-Match");
        if (serverEtag != null && Intrinsics.areEqual(serverEtag, clientEtag)) {
            this.closeWith304(ctx, resultStream);
            return true;
        }
        if (serverEtag == null && generatorEnabled && ctx.method() == HandlerType.GET && resultStream instanceof ByteArrayInputStream) {
            String generatedEtag = Util.INSTANCE.getChecksumAndReset((ByteArrayInputStream)resultStream);
            ctx.header("ETag", generatedEtag);
            if (Intrinsics.areEqual(generatedEtag, clientEtag)) {
                this.closeWith304(ctx, resultStream);
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void closeWith304(Context $this$closeWith304, InputStream inputStream2) {
        Closeable closeable = inputStream2;
        Throwable throwable = null;
        try {
            InputStream it = (InputStream)closeable;
            boolean bl = false;
            Context context = $this$closeWith304.status(HttpStatus.NOT_MODIFIED);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }
}

