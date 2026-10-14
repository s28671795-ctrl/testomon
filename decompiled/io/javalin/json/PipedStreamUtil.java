/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.json;

import io.javalin.json.PipedStreamUtil;
import io.javalin.util.ConcurrencyUtilKt;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ExecutorService;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000f"}, d2={"Lio/javalin/json/PipedStreamUtil;", "", "()V", "executorService", "Ljava/util/concurrent/ExecutorService;", "getExecutorService", "()Ljava/util/concurrent/ExecutorService;", "executorService$delegate", "Lkotlin/Lazy;", "getInputStream", "Ljava/io/InputStream;", "userCallback", "Lkotlin/Function1;", "Ljava/io/PipedOutputStream;", "", "javalin"})
public final class PipedStreamUtil {
    @NotNull
    public static final PipedStreamUtil INSTANCE = new PipedStreamUtil();
    @NotNull
    private static final Lazy executorService$delegate = ConcurrencyUtilKt.javalinLazy$default(null, executorService.2.INSTANCE, 1, null);

    private PipedStreamUtil() {
    }

    private final ExecutorService getExecutorService() {
        Lazy lazy = executorService$delegate;
        return (ExecutorService)lazy.getValue();
    }

    @NotNull
    public final InputStream getInputStream(@NotNull Function1<? super PipedOutputStream, Unit> userCallback) {
        Intrinsics.checkNotNullParameter(userCallback, "userCallback");
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream2 = new PipedInputStream(pipedOutputStream){
            @Nullable
            private Exception exception;

            @Nullable
            public final Exception getException() {
                return this.exception;
            }

            public final void setException(@Nullable Exception exception) {
                this.exception = exception;
            }

            public void close() {
                Exception exception = this.exception;
                if (exception != null) {
                    Exception it = exception;
                    boolean bl = false;
                    throw it;
                }
                super.close();
            }
        };
        this.getExecutorService().execute(() -> PipedStreamUtil.getInputStream$lambda$0(userCallback, pipedOutputStream, pipedInputStream2));
        return pipedInputStream2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static final void getInputStream$lambda$0(Function1 $userCallback, PipedOutputStream $pipedOutputStream, getInputStream.pipedInputStream.1 $pipedInputStream) {
        Intrinsics.checkNotNullParameter($userCallback, "$userCallback");
        Intrinsics.checkNotNullParameter($pipedOutputStream, "$pipedOutputStream");
        Intrinsics.checkNotNullParameter($pipedInputStream, "$pipedInputStream");
        try {
            $userCallback.invoke($pipedOutputStream);
        }
        catch (Exception userException) {
            $pipedInputStream.setException(userException);
        }
        finally {
            $pipedOutputStream.close();
        }
    }
}

