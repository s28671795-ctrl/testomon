/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.compression.CompressedOutputStream;
import io.javalin.compression.CompressionStrategy;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import io.javalin.http.servlet.JavalinServletContext;
import io.javalin.http.servlet.JavalinServletContextKt;
import io.javalin.http.servlet.Task;
import io.javalin.http.util.CookieStore;
import io.javalin.routing.HandlerEntry;
import io.javalin.util.ConcurrencyUtilKt;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00b0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u00c5\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0011\u0012\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\u0010\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0011\u0012\u0016\b\u0002\u0010\u001c\u001a\u0010\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u001e\u0018\u00010\u001d\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 \u00a2\u0006\u0002\u0010!J\u001b\u0010R\u001a\u0002HS\"\u0004\b\u0000\u0010S2\u0006\u0010T\u001a\u00020\u0011H\u0016\u00a2\u0006\u0002\u0010UJ\b\u0010V\u001a\u00020#H\u0016J\u000e\u0010W\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010(\u001a\u00020\u0011H\u0016J\b\u0010,\u001a\u00020-H\u0016J\b\u0010\u001b\u001a\u00020\u0011H\u0016J\u0006\u0010X\u001a\u00020YJ\u001a\u0010Z\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u0011090\u0010H\u0016J\u001c\u0010[\u001a\u00020\\2\u0012\u0010[\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u001e0\u001dH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0011H\u0016J\b\u0010=\u001a\u00020\u0018H\u0016J\b\u0010]\u001a\u00020^H\u0016J\u0010\u0010_\u001a\u00020\u00112\u0006\u0010T\u001a\u00020\u0011H\u0016J\u0014\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\u0010H\u0016J\u001a\u0010`\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u0011090\u0010H\u0016J\u0018\u0010a\u001a\u00020\\2\u0006\u0010b\u001a\u00020\u00112\u0006\u0010c\u001a\u00020dH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 H\u0016J\n\u0010f\u001a\u0004\u0018\u00010 H\u0016J\u0016\u0010g\u001a\u00020\u00002\u0006\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020\u0011R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\"\u001a\u00020#8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b$\u0010%R\u001b\u0010(\u001a\u00020\u00118BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b+\u0010'\u001a\u0004\b)\u0010*R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010,\u001a\u00020-8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b0\u0010'\u001a\u0004\b.\u0010/R\u001a\u0010\u001b\u001a\u00020\u0011X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010*\"\u0004\b2\u00103R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R-\u00108\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u0011090\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b<\u0010'\u001a\u0004\b:\u0010;R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010=\u001a\u00020\u00188BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b@\u0010'\u001a\u0004\b>\u0010?R\u001a\u0010A\u001a\b\u0012\u0004\u0012\u00020C0BX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R-\u0010F\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u0011090\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\bH\u0010'\u001a\u0004\bG\u0010;R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010KR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u0010MR(\u0010\u001c\u001a\u0010\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u001e\u0018\u00010\u001dX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010Q\u00a8\u0006k"}, d2={"Lio/javalin/http/servlet/JavalinServletContext;", "Lio/javalin/http/Context;", "cfg", "Lio/javalin/config/JavalinConfig;", "tasks", "Ljava/util/Deque;", "Lio/javalin/http/servlet/Task;", "exceptionOccurred", "", "responseWritten", "Ljava/util/concurrent/atomic/AtomicBoolean;", "req", "Ljakarta/servlet/http/HttpServletRequest;", "res", "Ljakarta/servlet/http/HttpServletResponse;", "appAttributes", "", "", "", "compressionStrategy", "Lio/javalin/compression/CompressionStrategy;", "startTimeNanos", "", "handlerType", "Lio/javalin/http/HandlerType;", "matchedPath", "pathParamMap", "endpointHandlerPath", "userFutureSupplier", "Ljava/util/function/Supplier;", "Ljava/util/concurrent/CompletableFuture;", "resultStream", "Ljava/io/InputStream;", "(Lio/javalin/config/JavalinConfig;Ljava/util/Deque;ZLjava/util/concurrent/atomic/AtomicBoolean;Ljakarta/servlet/http/HttpServletRequest;Ljakarta/servlet/http/HttpServletResponse;Ljava/util/Map;Lio/javalin/compression/CompressionStrategy;Ljava/lang/Long;Lio/javalin/http/HandlerType;Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;Ljava/util/function/Supplier;Ljava/io/InputStream;)V", "body", "", "getBody", "()[B", "body$delegate", "Lkotlin/Lazy;", "characterEncoding", "getCharacterEncoding", "()Ljava/lang/String;", "characterEncoding$delegate", "cookieStore", "Lio/javalin/http/util/CookieStore;", "getCookieStore", "()Lio/javalin/http/util/CookieStore;", "cookieStore$delegate", "getEndpointHandlerPath$javalin", "setEndpointHandlerPath$javalin", "(Ljava/lang/String;)V", "getExceptionOccurred", "()Z", "setExceptionOccurred", "(Z)V", "formParams", "", "getFormParams", "()Ljava/util/Map;", "formParams$delegate", "method", "getMethod", "()Lio/javalin/http/HandlerType;", "method$delegate", "outputStreamWrapper", "Lkotlin/Lazy;", "Lio/javalin/compression/CompressedOutputStream;", "getOutputStreamWrapper$javalin", "()Lkotlin/Lazy;", "queryParams", "getQueryParams", "queryParams$delegate", "getResponseWritten", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "Ljava/lang/Long;", "getTasks", "()Ljava/util/Deque;", "getUserFutureSupplier$javalin", "()Ljava/util/function/Supplier;", "setUserFutureSupplier$javalin", "(Ljava/util/function/Supplier;)V", "appAttribute", "T", "key", "(Ljava/lang/String;)Ljava/lang/Object;", "bodyAsBytes", "changeBaseRequest", "executionTimeMs", "", "formParamMap", "future", "", "outputStream", "Ljakarta/servlet/ServletOutputStream;", "pathParam", "queryParamMap", "redirect", "location", "status", "Lio/javalin/http/HttpStatus;", "result", "resultInputStream", "update", "handlerEntry", "Lio/javalin/routing/HandlerEntry;", "requestUri", "javalin"})
@SourceDebugExtension(value={"SMAP\nJavalinServletContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JavalinServletContext.kt\nio/javalin/http/servlet/JavalinServletContext\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,219:1\n1#2:220\n*E\n"})
public final class JavalinServletContext
implements Context {
    @NotNull
    private final Deque<Task> tasks;
    private boolean exceptionOccurred;
    @NotNull
    private final AtomicBoolean responseWritten;
    @NotNull
    private HttpServletRequest req;
    @NotNull
    private final HttpServletResponse res;
    @NotNull
    private final Map<String, Object> appAttributes;
    @NotNull
    private final CompressionStrategy compressionStrategy;
    @Nullable
    private final Long startTimeNanos;
    @NotNull
    private HandlerType handlerType;
    @NotNull
    private String matchedPath;
    @NotNull
    private Map<String, String> pathParamMap;
    @NotNull
    private String endpointHandlerPath;
    @Nullable
    private Supplier<? extends CompletableFuture<?>> userFutureSupplier;
    @Nullable
    private InputStream resultStream;
    @NotNull
    private final Lazy characterEncoding$delegate;
    @NotNull
    private final Lazy cookieStore$delegate;
    @NotNull
    private final Lazy method$delegate;
    @NotNull
    private final Lazy body$delegate;
    @NotNull
    private final Lazy formParams$delegate;
    @NotNull
    private final Lazy queryParams$delegate;
    @NotNull
    private final Lazy<CompressedOutputStream> outputStreamWrapper;

    public JavalinServletContext(@NotNull JavalinConfig cfg, @NotNull Deque<Task> tasks2, boolean exceptionOccurred, @NotNull AtomicBoolean responseWritten, @NotNull HttpServletRequest req, @NotNull HttpServletResponse res, @NotNull Map<String, ? extends Object> appAttributes, @NotNull CompressionStrategy compressionStrategy, @Nullable Long startTimeNanos, @NotNull HandlerType handlerType, @NotNull String matchedPath, @NotNull Map<String, String> pathParamMap, @NotNull String endpointHandlerPath, @Nullable Supplier<? extends CompletableFuture<?>> userFutureSupplier, @Nullable InputStream resultStream) {
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        Intrinsics.checkNotNullParameter(tasks2, "tasks");
        Intrinsics.checkNotNullParameter(responseWritten, "responseWritten");
        Intrinsics.checkNotNullParameter(req, "req");
        Intrinsics.checkNotNullParameter(res, "res");
        Intrinsics.checkNotNullParameter(appAttributes, "appAttributes");
        Intrinsics.checkNotNullParameter(compressionStrategy, "compressionStrategy");
        Intrinsics.checkNotNullParameter((Object)handlerType, "handlerType");
        Intrinsics.checkNotNullParameter(matchedPath, "matchedPath");
        Intrinsics.checkNotNullParameter(pathParamMap, "pathParamMap");
        Intrinsics.checkNotNullParameter(endpointHandlerPath, "endpointHandlerPath");
        this.tasks = tasks2;
        this.exceptionOccurred = exceptionOccurred;
        this.responseWritten = responseWritten;
        this.req = req;
        this.res = res;
        this.appAttributes = appAttributes;
        this.compressionStrategy = compressionStrategy;
        this.startTimeNanos = startTimeNanos;
        this.handlerType = handlerType;
        this.matchedPath = matchedPath;
        this.pathParamMap = pathParamMap;
        this.endpointHandlerPath = endpointHandlerPath;
        this.userFutureSupplier = userFutureSupplier;
        this.resultStream = resultStream;
        this.contentType(cfg.http.defaultContentType);
        this.characterEncoding$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<String>(this){
            final /* synthetic */ JavalinServletContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final String invoke() {
                String string = JavalinServletContext.access$characterEncoding$s-1678783089(this.this$0);
                if (string == null) {
                    string = "UTF-8";
                }
                return string;
            }
        }, 1, null);
        this.cookieStore$delegate = ConcurrencyUtilKt.javalinLazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<CookieStore>(this){
            final /* synthetic */ JavalinServletContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final CookieStore invoke() {
                return JavalinServletContext.access$cookieStore$s-1678783089(this.this$0);
            }
        });
        this.method$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<HandlerType>(this){
            final /* synthetic */ JavalinServletContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final HandlerType invoke() {
                return JavalinServletContext.access$method$s-1678783089(this.this$0);
            }
        }, 1, null);
        this.body$delegate = ConcurrencyUtilKt.javalinLazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0)new Function0<byte[]>(this){
            final /* synthetic */ JavalinServletContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final byte[] invoke() {
                return JavalinServletContext.access$bodyAsBytes$s-1678783089(this.this$0);
            }
        });
        this.formParams$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Map<String, ? extends List<? extends String>>>(this){
            final /* synthetic */ JavalinServletContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Map<String, List<String>> invoke() {
                return JavalinServletContext.access$formParamMap$s-1678783089(this.this$0);
            }
        }, 1, null);
        this.queryParams$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Map<String, ? extends List<? extends String>>>(this){
            final /* synthetic */ JavalinServletContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Map<String, List<String>> invoke() {
                return JavalinServletContext.access$queryParamMap$s-1678783089(this.this$0);
            }
        }, 1, null);
        this.outputStreamWrapper = ConcurrencyUtilKt.javalinLazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0)new Function0<CompressedOutputStream>(this){
            final /* synthetic */ JavalinServletContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final CompressedOutputStream invoke() {
                return new CompressedOutputStream(JavalinServletContext.access$getCompressionStrategy$p(this.this$0), this.this$0);
            }
        });
    }

    public /* synthetic */ JavalinServletContext(JavalinConfig javalinConfig, Deque deque, boolean bl, AtomicBoolean atomicBoolean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Map map, CompressionStrategy compressionStrategy, Long l, HandlerType handlerType, String string, Map map2, String string2, Supplier supplier, InputStream inputStream2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            deque = new ArrayDeque(8);
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        if ((n & 8) != 0) {
            atomicBoolean = new AtomicBoolean(false);
        }
        if ((n & 0x40) != 0) {
            map = javalinConfig.pvt.appAttributes;
        }
        if ((n & 0x80) != 0) {
            compressionStrategy = javalinConfig.pvt.compressionStrategy;
        }
        if ((n & 0x100) != 0) {
            Long l2 = l = javalinConfig.pvt.requestLogger != null ? Long.valueOf(System.nanoTime()) : null;
        }
        if ((n & 0x200) != 0) {
            handlerType = HandlerType.BEFORE;
        }
        if ((n & 0x400) != 0) {
            string = "";
        }
        if ((n & 0x800) != 0) {
            map2 = MapsKt.emptyMap();
        }
        if ((n & 0x1000) != 0) {
            string2 = "";
        }
        if ((n & 0x2000) != 0) {
            supplier = null;
        }
        if ((n & 0x4000) != 0) {
            inputStream2 = null;
        }
        this(javalinConfig, deque, bl, atomicBoolean, httpServletRequest, httpServletResponse, map, compressionStrategy, l, handlerType, string, map2, string2, supplier, inputStream2);
    }

    @NotNull
    public final Deque<Task> getTasks() {
        return this.tasks;
    }

    public final boolean getExceptionOccurred() {
        return this.exceptionOccurred;
    }

    public final void setExceptionOccurred(boolean bl) {
        this.exceptionOccurred = bl;
    }

    @NotNull
    public final AtomicBoolean getResponseWritten() {
        return this.responseWritten;
    }

    @NotNull
    public final String getEndpointHandlerPath$javalin() {
        return this.endpointHandlerPath;
    }

    public final void setEndpointHandlerPath$javalin(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.endpointHandlerPath = string;
    }

    @Nullable
    public final Supplier<? extends CompletableFuture<?>> getUserFutureSupplier$javalin() {
        return this.userFutureSupplier;
    }

    public final void setUserFutureSupplier$javalin(@Nullable Supplier<? extends CompletableFuture<?>> supplier) {
        this.userFutureSupplier = supplier;
    }

    public final float executionTimeMs() {
        return this.startTimeNanos == null ? -1.0f : (float)(System.nanoTime() - this.startTimeNanos) / 1000000.0f;
    }

    @NotNull
    public final JavalinServletContext changeBaseRequest(@NotNull HttpServletRequest req) {
        JavalinServletContext javalinServletContext;
        Intrinsics.checkNotNullParameter(req, "req");
        JavalinServletContext it = javalinServletContext = this;
        boolean bl = false;
        this.req = req;
        return javalinServletContext;
    }

    @NotNull
    public final JavalinServletContext update(@NotNull HandlerEntry handlerEntry, @NotNull String requestUri) {
        JavalinServletContext javalinServletContext;
        Intrinsics.checkNotNullParameter(handlerEntry, "handlerEntry");
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        JavalinServletContext it = javalinServletContext = this;
        boolean bl = false;
        this.handlerType = handlerEntry.getType();
        if (!Intrinsics.areEqual(this.matchedPath, handlerEntry.getPath())) {
            this.matchedPath = handlerEntry.getPath();
            this.pathParamMap = handlerEntry.extractPathParams(requestUri);
        }
        if (this.handlerType != HandlerType.AFTER) {
            this.endpointHandlerPath = handlerEntry.getPath();
        }
        return javalinServletContext;
    }

    @Override
    @NotNull
    public HttpServletRequest req() {
        return this.req;
    }

    @Override
    @NotNull
    public HttpServletResponse res() {
        return this.res;
    }

    @Override
    public <T> T appAttribute(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (T)this.appAttributes.get(key);
    }

    @Override
    @NotNull
    public String endpointHandlerPath() {
        if (this.handlerType() == HandlerType.BEFORE) {
            throw new IllegalStateException("Cannot access the endpoint handler path in a 'BEFORE' handler");
        }
        return this.endpointHandlerPath;
    }

    private final String getCharacterEncoding() {
        Lazy lazy = this.characterEncoding$delegate;
        return (String)lazy.getValue();
    }

    @Override
    @NotNull
    public String characterEncoding() {
        return this.getCharacterEncoding();
    }

    private final CookieStore getCookieStore() {
        Lazy lazy = this.cookieStore$delegate;
        return (CookieStore)lazy.getValue();
    }

    @Override
    @NotNull
    public CookieStore cookieStore() {
        return this.getCookieStore();
    }

    private final HandlerType getMethod() {
        Lazy lazy = this.method$delegate;
        return (HandlerType)((Object)lazy.getValue());
    }

    @Override
    @NotNull
    public HandlerType method() {
        return this.getMethod();
    }

    @Override
    @NotNull
    public HandlerType handlerType() {
        return this.handlerType;
    }

    @Override
    @NotNull
    public String matchedPath() {
        return this.matchedPath;
    }

    private final byte[] getBody() {
        Lazy lazy = this.body$delegate;
        return (byte[])lazy.getValue();
    }

    @Override
    @NotNull
    public byte[] bodyAsBytes() {
        return this.getBody();
    }

    private final Map<String, List<String>> getFormParams() {
        Lazy lazy = this.formParams$delegate;
        return (Map)lazy.getValue();
    }

    @Override
    @NotNull
    public Map<String, List<String>> formParamMap() {
        return this.getFormParams();
    }

    @Override
    @NotNull
    public Map<String, String> pathParamMap() {
        Map<String, String> map = Collections.unmodifiableMap(this.pathParamMap);
        Intrinsics.checkNotNullExpressionValue(map, "unmodifiableMap(pathParamMap)");
        return map;
    }

    @Override
    @NotNull
    public String pathParam(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return JavalinServletContextKt.pathParamOrThrow(this.pathParamMap, key, this.matchedPath);
    }

    private final Map<String, List<String>> getQueryParams() {
        Lazy lazy = this.queryParams$delegate;
        return (Map)lazy.getValue();
    }

    @Override
    @NotNull
    public Map<String, List<String>> queryParamMap() {
        return this.getQueryParams();
    }

    @NotNull
    public final Lazy<CompressedOutputStream> getOutputStreamWrapper$javalin() {
        return this.outputStreamWrapper;
    }

    @Override
    @NotNull
    public ServletOutputStream outputStream() {
        return this.outputStreamWrapper.getValue();
    }

    @Override
    public void redirect(@NotNull String location, @NotNull HttpStatus status) {
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter((Object)status, "status");
        this.header("Location", location).status(status).result("Redirected");
        if (this.handlerType() == HandlerType.BEFORE) {
            this.tasks.removeIf(arg_0 -> JavalinServletContext.redirect$lambda$2(redirect.1.INSTANCE, arg_0));
        }
    }

    @Override
    @NotNull
    public Context result(@NotNull InputStream resultStream) {
        JavalinServletContext javalinServletContext;
        Intrinsics.checkNotNullParameter(resultStream, "resultStream");
        JavalinServletContext $this$result_u24lambda_u244 = javalinServletContext = this;
        boolean bl = false;
        JavalinServletContext javalinServletContext2 = $this$result_u24lambda_u244;
        try {
            Unit unit;
            JavalinServletContext $this$result_u24lambda_u244_u24lambda_u243 = javalinServletContext2;
            boolean bl2 = false;
            InputStream inputStream2 = $this$result_u24lambda_u244_u24lambda_u243.resultStream;
            if (inputStream2 != null) {
                inputStream2.close();
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            Object object = Result.constructor-impl(unit);
        }
        catch (Throwable throwable) {
            Object object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        $this$result_u24lambda_u244.resultStream = resultStream;
        return javalinServletContext;
    }

    @Override
    @Nullable
    public InputStream resultInputStream() {
        return this.resultStream;
    }

    @Override
    public void future(@NotNull Supplier<? extends CompletableFuture<?>> future) {
        Intrinsics.checkNotNullParameter(future, "future");
        if (this.userFutureSupplier != null) {
            throw new IllegalStateException("Cannot override future from the same handler");
        }
        this.userFutureSupplier = future;
    }

    private static final boolean redirect$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    public static final /* synthetic */ String access$characterEncoding$s-1678783089(JavalinServletContext $this) {
        return Context.super.characterEncoding();
    }

    public static final /* synthetic */ CookieStore access$cookieStore$s-1678783089(JavalinServletContext $this) {
        return Context.super.cookieStore();
    }

    public static final /* synthetic */ HandlerType access$method$s-1678783089(JavalinServletContext $this) {
        return Context.super.method();
    }

    public static final /* synthetic */ byte[] access$bodyAsBytes$s-1678783089(JavalinServletContext $this) {
        return Context.super.bodyAsBytes();
    }

    public static final /* synthetic */ Map access$formParamMap$s-1678783089(JavalinServletContext $this) {
        return Context.super.formParamMap();
    }

    public static final /* synthetic */ Map access$queryParamMap$s-1678783089(JavalinServletContext $this) {
        return Context.super.queryParamMap();
    }

    public static final /* synthetic */ CompressionStrategy access$getCompressionStrategy$p(JavalinServletContext $this) {
        return $this.compressionStrategy;
    }
}

