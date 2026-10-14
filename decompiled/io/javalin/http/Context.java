/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.config.ContextResolverConfigKt;
import io.javalin.http.ContentType;
import io.javalin.http.Cookie;
import io.javalin.http.CookieKt;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import io.javalin.http.servlet.JavalinServletContextKt;
import io.javalin.http.util.AsyncUtil;
import io.javalin.http.util.CookieStore;
import io.javalin.http.util.MultipartUtil;
import io.javalin.http.util.SeekableWriter;
import io.javalin.json.JsonMapper;
import io.javalin.json.JsonMapperKt;
import io.javalin.rendering.FileRendererKt;
import io.javalin.security.BasicAuthCredentials;
import io.javalin.util.function.ThrowingRunnable;
import io.javalin.validation.BodyValidator;
import io.javalin.validation.Validator;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00f6\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010\u0007\u001a\u00020\b2\u0010\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\nH\u0016J:\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0010\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\nH\u0016J.\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0010\u0010\t\u001a\f\u0012\b\u0012\u00060\u000bj\u0002`\f0\nH\u0016J\u001d\u0010\u0013\u001a\u0004\u0018\u0001H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0016J\u0016\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0016H\u0016J1\u0010\u0017\u001a\u0004\u0018\u0001H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u0002H\u00030\u0019H\u0016\u00a2\u0006\u0002\u0010\u001aJ\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0005H\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J!\u0010 \u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00030\"H\u0016\u00a2\u0006\u0002\u0010#J\u001b\u0010 \u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010$\u001a\u00020%H\u0016\u00a2\u0006\u0002\u0010&J\b\u0010'\u001a\u00020(H\u0016J\u001b\u0010)\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010$\u001a\u00020%H\u0016\u00a2\u0006\u0002\u0010&J\"\u0010*\u001a\b\u0012\u0004\u0012\u0002H\u00030+\"\u0004\b\u0000\u0010\u00032\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00030\"H\u0016J\u001d\u0010,\u001a\u0004\u0018\u0001H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010,\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0016J1\u0010-\u001a\u0004\u0018\u0001H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u0002H\u00030\u0019H\u0016\u00a2\u0006\u0002\u0010\u001aJ\n\u0010.\u001a\u0004\u0018\u00010\u0005H\u0016J\u001d\u0010/\u001a\u0004\u0018\u0001H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016\u00a2\u0006\u0002\u0010\u0006J\b\u00100\u001a\u000201H\u0016J\n\u00102\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u00102\u001a\u00020\u00002\u0006\u00102\u001a\u000203H\u0016J\u0010\u00102\u001a\u00020\u00002\u0006\u00102\u001a\u00020\u0005H\u0016J\b\u00104\u001a\u00020\u0005H\u0016J\u0010\u00105\u001a\u00020\u00002\u0006\u00105\u001a\u000206H\u0016J\u0012\u00105\u001a\u0004\u0018\u00010\u00052\u0006\u00107\u001a\u00020\u0005H\u0016J\u0018\u00105\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0005H\u0016J \u00105\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u00108\u001a\u000201H\u0016J\u0014\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0016H\u0016J\b\u0010:\u001a\u00020;H\u0016J\b\u0010<\u001a\u00020\u0005H&J\u0012\u0010=\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J*\u0010>\u001a\b\u0012\u0004\u0012\u0002H\u00030?\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00030\"H\u0016J\u001a\u0010@\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050A0\u0016H\u0016J\u0016\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00050A2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010C\u001a\u00020\u0005H\u0016J\u001c\u0010D\u001a\u00020\b2\u0012\u0010D\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030F0EH&J\b\u0010G\u001a\u00020HH&J\u0012\u0010I\u001a\u0004\u0018\u00010\u00052\u0006\u0010I\u001a\u00020\u0005H\u0016J\u0018\u0010I\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0005H\u0016J*\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00030?\"\u0004\b\u0000\u0010\u00032\u0006\u0010I\u001a\u00020\u00052\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00030\"H\u0016J\u0014\u0010K\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0016H\u0016J\n\u0010L\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010M\u001a\u00020\u00002\u0006\u0010M\u001a\u00020\u0005H\u0016J\b\u0010N\u001a\u00020\u0005H\u0016J\b\u0010O\u001a\u00020PH\u0016J\b\u0010Q\u001a\u00020PH\u0016J\u0010\u0010R\u001a\u00020\u00002\u0006\u0010S\u001a\u00020\u0001H\u0016J\u0018\u0010R\u001a\u00020\u00002\u0006\u0010S\u001a\u00020\u00012\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010T\u001a\u00020\u00002\u0006\u0010S\u001a\u00020\u0001H\u0016J\u0018\u0010T\u001a\u00020\u00002\u0006\u0010S\u001a\u00020\u00012\u0006\u0010$\u001a\u00020%H\u0016J\b\u0010U\u001a\u00020\u0005H&J\b\u0010V\u001a\u00020HH\u0016J\b\u0010W\u001a\u00020XH&J\b\u0010Y\u001a\u00020\u0005H\u0016J\u0010\u0010Z\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H&J*\u0010[\u001a\b\u0012\u0004\u0012\u0002H\u00030?\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00030\"H\u0016J\u0014\u0010\\\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0016H&J\b\u0010]\u001a\u000201H\u0016J\b\u0010^\u001a\u00020\u0005H\u0016J\u0012\u0010_\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J*\u0010`\u001a\b\u0012\u0004\u0012\u0002H\u00030?\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00030\"H\u0016J\u001a\u0010a\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050A0\u0016H\u0016J\u0016\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00050A2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\n\u0010c\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010d\u001a\u00020\b2\u0006\u0010e\u001a\u00020\u0005H\u0016J\u0018\u0010d\u001a\u00020\b2\u0006\u0010e\u001a\u00020\u00052\u0006\u0010f\u001a\u00020gH&J\u0010\u0010h\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u0005H\u0016J\u001a\u0010h\u001a\u00020\u00002\u0006\u00107\u001a\u00020\u00052\b\u0010Y\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010i\u001a\u00020\u00002\u0006\u0010j\u001a\u00020\u0005H\u0016J&\u0010i\u001a\u00020\u00002\u0006\u0010j\u001a\u00020\u00052\u0014\u0010k\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0016H\u0016J\b\u0010l\u001a\u00020mH&J\b\u0010n\u001a\u00020oH&J\b\u0010p\u001a\u00020qH\u0016J\n\u0010r\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010r\u001a\u00020\u00002\u0006\u0010s\u001a\u00020(H&J\u0010\u0010r\u001a\u00020\u00002\u0006\u0010t\u001a\u00020\u001fH\u0016J\u0010\u0010r\u001a\u00020\u00002\u0006\u0010u\u001a\u00020\u0005H\u0016J\n\u0010v\u001a\u0004\u0018\u00010(H&J\b\u0010w\u001a\u00020\u0005H\u0016J\u001d\u0010x\u001a\u0004\u0018\u0001H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010x\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u0016J\u0016\u0010y\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0016H\u0016J\b\u0010f\u001a\u00020gH\u0016J\u0010\u0010f\u001a\u00020\u00002\u0006\u0010f\u001a\u00020gH\u0016J\u0010\u0010f\u001a\u00020\u00002\u0006\u0010f\u001a\u000201H\u0016J\b\u0010z\u001a\u000201H\u0016J\u0012\u0010{\u001a\u0004\u0018\u00010|2\u0006\u0010}\u001a\u00020\u0005H\u0016J\u001a\u0010~\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020|0A0\u0016H\u0016J\u000e\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020|0AH\u0016J\u0016\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020|0A2\u0006\u0010}\u001a\u00020\u0005H\u0016J\t\u0010\u0080\u0001\u001a\u00020\u0005H\u0016J\u000b\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u0005H\u0016J\u0017\u0010\u0082\u0001\u001a\u00020\b2\f\u0010\u0083\u0001\u001a\u0007\u0012\u0002\b\u00030\u0084\u0001H\u0016J\u001a\u0010\u0085\u0001\u001a\u00020\b2\u0007\u0010\u0086\u0001\u001a\u00020(2\u0006\u00102\u001a\u00020\u0005H\u0016J#\u0010\u0085\u0001\u001a\u00020\b2\u0007\u0010\u0086\u0001\u001a\u00020(2\u0006\u00102\u001a\u00020\u00052\u0007\u0010\u0087\u0001\u001a\u00020\u0010H\u0016\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\u0088\u0001\u00c0\u0006\u0001"}, d2={"Lio/javalin/http/Context;", "", "appAttribute", "T", "key", "", "(Ljava/lang/String;)Ljava/lang/Object;", "async", "", "task", "Lio/javalin/util/function/ThrowingRunnable;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "executor", "Ljava/util/concurrent/ExecutorService;", "timeout", "", "onTimeout", "Ljava/lang/Runnable;", "attribute", "value", "attributeMap", "", "attributeOrCompute", "callback", "Lkotlin/Function1;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "basicAuthCredentials", "Lio/javalin/security/BasicAuthCredentials;", "body", "bodyAsBytes", "", "bodyAsClass", "clazz", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "type", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Type;)Ljava/lang/Object;", "bodyInputStream", "Ljava/io/InputStream;", "bodyStreamAsClass", "bodyValidator", "Lio/javalin/validation/BodyValidator;", "cachedSessionAttribute", "cachedSessionAttributeOrCompute", "characterEncoding", "consumeSessionAttribute", "contentLength", "", "contentType", "Lio/javalin/http/ContentType;", "contextPath", "cookie", "Lio/javalin/http/Cookie;", "name", "maxAge", "cookieMap", "cookieStore", "Lio/javalin/http/util/CookieStore;", "endpointHandlerPath", "formParam", "formParamAsClass", "Lio/javalin/validation/Validator;", "formParamMap", "", "formParams", "fullUrl", "future", "Ljava/util/function/Supplier;", "Ljava/util/concurrent/CompletableFuture;", "handlerType", "Lio/javalin/http/HandlerType;", "header", "headerAsClass", "headerMap", "host", "html", "ip", "isMultipart", "", "isMultipartFormData", "json", "obj", "jsonStream", "matchedPath", "method", "outputStream", "Ljakarta/servlet/ServletOutputStream;", "path", "pathParam", "pathParamAsClass", "pathParamMap", "port", "protocol", "queryParam", "queryParamAsClass", "queryParamMap", "queryParams", "queryString", "redirect", "location", "status", "Lio/javalin/http/HttpStatus;", "removeCookie", "render", "filePath", "model", "req", "Ljakarta/servlet/http/HttpServletRequest;", "res", "Ljakarta/servlet/http/HttpServletResponse;", "responseCharset", "Ljava/nio/charset/Charset;", "result", "resultStream", "resultBytes", "resultString", "resultInputStream", "scheme", "sessionAttribute", "sessionAttributeMap", "statusCode", "uploadedFile", "Lio/javalin/http/UploadedFile;", "fileName", "uploadedFileMap", "uploadedFiles", "url", "userAgent", "writeJsonStream", "stream", "Ljava/util/stream/Stream;", "writeSeekableStream", "inputStream", "totalBytes", "javalin"})
@SourceDebugExtension(value={"SMAP\nContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Context.kt\nio/javalin/http/Context\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,485:1\n1#2:486\n759#3,2:487\n775#3,4:489\n759#3,2:493\n775#3,4:495\n759#3,2:505\n775#3,4:507\n8676#4,2:499\n9358#4,4:501\n*S KotlinDebug\n*F\n+ 1 Context.kt\nio/javalin/http/Context\n*L\n210#1:487,2\n210#1:489,4\n223#1:493,2\n223#1:495,4\n241#1:505,2\n241#1:507,4\n232#1:499,2\n232#1:501,4\n*E\n"})
public interface Context {
    @NotNull
    public HttpServletRequest req();

    @NotNull
    public HttpServletResponse res();

    public <T> T appAttribute(@NotNull String var1);

    @NotNull
    public HandlerType handlerType();

    @NotNull
    public String matchedPath();

    @NotNull
    public String endpointHandlerPath();

    default public int contentLength() {
        return this.req().getContentLength();
    }

    @Nullable
    default public String contentType() {
        return this.req().getContentType();
    }

    @NotNull
    default public HandlerType method() {
        String string = this.header("X-HTTP-Method-Override");
        if (string == null) {
            string = this.req().getMethod();
        }
        Intrinsics.checkNotNullExpressionValue(string, "header(Header.X_HTTP_MET\u2026OVERRIDE) ?: req().method");
        return HandlerType.Companion.findByName(string);
    }

    @NotNull
    default public String path() {
        String string = this.req().getRequestURI();
        Intrinsics.checkNotNullExpressionValue(string, "req().requestURI");
        return string;
    }

    default public int port() {
        return this.req().getServerPort();
    }

    @NotNull
    default public String protocol() {
        String string = this.req().getProtocol();
        Intrinsics.checkNotNullExpressionValue(string, "req().protocol");
        return string;
    }

    @NotNull
    default public String contextPath() {
        String string = this.req().getContextPath();
        Intrinsics.checkNotNullExpressionValue(string, "req().contextPath");
        return string;
    }

    @Nullable
    default public String userAgent() {
        return this.req().getHeader("User-Agent");
    }

    @Nullable
    default public String characterEncoding() {
        return JavalinServletContextKt.getRequestCharset(this);
    }

    @NotNull
    default public String url() {
        return ContextResolverConfigKt.contextResolver((Context)this).url.invoke(this);
    }

    @NotNull
    default public String fullUrl() {
        return ContextResolverConfigKt.contextResolver((Context)this).fullUrl.invoke(this);
    }

    @NotNull
    default public String scheme() {
        return ContextResolverConfigKt.contextResolver((Context)this).scheme.invoke(this);
    }

    @Nullable
    default public String host() {
        return ContextResolverConfigKt.contextResolver((Context)this).host.invoke(this);
    }

    @NotNull
    default public String ip() {
        return ContextResolverConfigKt.contextResolver((Context)this).ip.invoke(this);
    }

    @NotNull
    default public String body() {
        byte[] byArray = this.bodyAsBytes();
        String string = this.characterEncoding();
        if (string == null) {
            string = "UTF-8";
        }
        Charset charset = Charset.forName(string);
        Intrinsics.checkNotNullExpressionValue(charset, "forName(characterEncoding() ?: \"UTF-8\")");
        Charset charset2 = charset;
        return new String(byArray, charset2);
    }

    @NotNull
    default public byte[] bodyAsBytes() {
        JavalinServletContextKt.throwContentTooLargeIfContentTooLarge(this);
        ServletInputStream servletInputStream = this.req().getInputStream();
        Intrinsics.checkNotNullExpressionValue(servletInputStream, "req().inputStream");
        return ByteStreamsKt.readBytes(servletInputStream);
    }

    default public <T> T bodyAsClass(@NotNull Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return JsonMapperKt.jsonMapper(this).fromJsonString(this.body(), type);
    }

    default public <T> T bodyAsClass(@NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return this.bodyAsClass((Type)clazz);
    }

    default public <T> T bodyStreamAsClass(@NotNull Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        JsonMapper jsonMapper = JsonMapperKt.jsonMapper(this);
        ServletInputStream servletInputStream = this.req().getInputStream();
        Intrinsics.checkNotNullExpressionValue(servletInputStream, "req().inputStream");
        return jsonMapper.fromJsonStream(servletInputStream, type);
    }

    @NotNull
    default public InputStream bodyInputStream() {
        ServletInputStream servletInputStream = this.req().getInputStream();
        Intrinsics.checkNotNullExpressionValue(servletInputStream, "req().inputStream");
        return servletInputStream;
    }

    @NotNull
    default public <T> BodyValidator<T> bodyValidator(@NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return new BodyValidator<T>(this.body(), clazz, JsonMapperKt.jsonMapper(this));
    }

    @Nullable
    default public String formParam(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return CollectionsKt.firstOrNull(this.formParams(key));
    }

    @NotNull
    default public <T> Validator<T> formParamAsClass(@NotNull String key, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return Validator.Companion.create(clazz, this.formParam(key), key);
    }

    @NotNull
    default public List<String> formParams(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        List<String> list = this.formParamMap().get(key);
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    @NotNull
    default public Map<String, List<String>> formParamMap() {
        Map<String, List<String>> map;
        if (this.isMultipartFormData()) {
            map = MultipartUtil.INSTANCE.getFieldMap(this.req());
        } else {
            String string = this.body();
            String string2 = this.characterEncoding();
            if (string2 == null) {
                string2 = "UTF-8";
            }
            map = JavalinServletContextKt.splitKeyValueStringAndGroupByKey(string, string2);
        }
        return map;
    }

    @NotNull
    public String pathParam(@NotNull String var1);

    @NotNull
    default public <T> Validator<T> pathParamAsClass(@NotNull String key, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return Validator.Companion.create(clazz, this.pathParam(key), key);
    }

    @NotNull
    public Map<String, String> pathParamMap();

    @Nullable
    default public String queryParam(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return CollectionsKt.firstOrNull(this.queryParams(key));
    }

    @NotNull
    default public <T> Validator<T> queryParamAsClass(@NotNull String key, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return Validator.Companion.create(clazz, this.queryParam(key), key);
    }

    @NotNull
    default public List<String> queryParams(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        List<String> list = this.queryParamMap().get(key);
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    @NotNull
    default public Map<String, List<String>> queryParamMap() {
        String string;
        String string2 = this.queryString();
        if (string2 == null) {
            string2 = "";
        }
        if ((string = this.characterEncoding()) == null) {
            string = "UTF-8";
        }
        return JavalinServletContextKt.splitKeyValueStringAndGroupByKey(string2, string);
    }

    @Nullable
    default public String queryString() {
        return this.req().getQueryString();
    }

    default public void sessionAttribute(@NotNull String key, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.req().getSession().setAttribute(key, value);
    }

    @Nullable
    default public <T> T sessionAttribute(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        HttpSession httpSession = this.req().getSession(false);
        Object object = httpSession != null ? httpSession.getAttribute(key) : null;
        if (object == null) {
            object = null;
        }
        return (T)object;
    }

    @Nullable
    default public <T> T consumeSessionAttribute(@NotNull String key) {
        T t2;
        Intrinsics.checkNotNullParameter(key, "key");
        T it = t2 = this.sessionAttribute(key);
        boolean bl = false;
        this.sessionAttribute(key, null);
        return t2;
    }

    default public void cachedSessionAttribute(@NotNull String key, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(key, "key");
        JavalinServletContextKt.cacheAndSetSessionAttribute(key, value, this.req());
    }

    @Nullable
    default public <T> T cachedSessionAttribute(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return JavalinServletContextKt.getCachedRequestAttributeOrSessionAttribute(key, this.req());
    }

    @Nullable
    default public <T> T cachedSessionAttributeOrCompute(@NotNull String key, @NotNull Function1<? super Context, ? extends T> callback) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callback, "callback");
        return JavalinServletContextKt.cachedSessionAttributeOrCompute(callback, key, this);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    default public Map<String, Object> sessionAttributeMap() {
        Enumeration<String> enumeration = this.req().getSession().getAttributeNames();
        Intrinsics.checkNotNullExpressionValue(enumeration, "req().session.attributeNames");
        Sequence<String> $this$associateWith$iv = SequencesKt.asSequence(CollectionsKt.iterator(enumeration));
        boolean $i$f$associateWith = false;
        LinkedHashMap result$iv = new LinkedHashMap();
        Sequence<String> $this$associateWithTo$iv$iv = $this$associateWith$iv;
        boolean $i$f$associateWithTo = false;
        Iterator<String> iterator2 = $this$associateWithTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            void it;
            String element$iv$iv;
            String string = element$iv$iv = iterator2.next();
            String string2 = element$iv$iv;
            Map map = result$iv;
            boolean bl = false;
            Object t2 = this.sessionAttribute((String)it);
            map.put(string2, t2);
        }
        return result$iv;
    }

    default public void attribute(@NotNull String key, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.req().setAttribute(key, value);
    }

    @Nullable
    default public <T> T attribute(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Object object = this.req().getAttribute(key);
        if (object == null) {
            object = null;
        }
        return (T)object;
    }

    @Nullable
    default public <T> T attributeOrCompute(@NotNull String key, @NotNull Function1<? super Context, ? extends T> callback) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callback, "callback");
        return JavalinServletContextKt.attributeOrCompute(callback, key, this);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    default public Map<String, Object> attributeMap() {
        Enumeration<String> enumeration = this.req().getAttributeNames();
        Intrinsics.checkNotNullExpressionValue(enumeration, "req().attributeNames");
        Sequence<String> $this$associateWith$iv = SequencesKt.asSequence(CollectionsKt.iterator(enumeration));
        boolean $i$f$associateWith = false;
        LinkedHashMap result$iv = new LinkedHashMap();
        Sequence<String> $this$associateWithTo$iv$iv = $this$associateWith$iv;
        boolean $i$f$associateWithTo = false;
        Iterator<String> iterator2 = $this$associateWithTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            void it;
            String element$iv$iv;
            String string = element$iv$iv = iterator2.next();
            String string2 = element$iv$iv;
            Map map = result$iv;
            boolean bl = false;
            Object t2 = this.attribute((String)it);
            map.put(string2, t2);
        }
        return result$iv;
    }

    @NotNull
    default public CookieStore cookieStore() {
        return new CookieStore(this);
    }

    @Nullable
    default public String cookie(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return CookieKt.getCookie(this.req(), name);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    default public Map<String, String> cookieMap() {
        Map map;
        jakarta.servlet.http.Cookie[] cookieArray = this.req().getCookies();
        if (cookieArray != null) {
            void $this$associateTo$iv$iv;
            jakarta.servlet.http.Cookie[] $this$associate$iv = cookieArray;
            boolean $i$f$associate = false;
            int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity($this$associate$iv.length), 16);
            jakarta.servlet.http.Cookie[] cookieArray2 = $this$associate$iv;
            Map destination$iv$iv = new LinkedHashMap(capacity$iv);
            boolean $i$f$associateTo = false;
            for (void element$iv$iv : $this$associateTo$iv$iv) {
                Map map2 = destination$iv$iv;
                void it = element$iv$iv;
                boolean bl = false;
                Pair<String, String> pair = TuplesKt.to(it.getName(), it.getValue());
                map2.put(pair.getFirst(), pair.getSecond());
            }
            map = destination$iv$iv;
        } else {
            map = MapsKt.emptyMap();
        }
        return map;
    }

    @Nullable
    default public String header(@NotNull String header) {
        Intrinsics.checkNotNullParameter(header, "header");
        return this.req().getHeader(header);
    }

    @NotNull
    default public <T> Validator<T> headerAsClass(@NotNull String header, @NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return Validator.Companion.create(clazz, this.header(header), header);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    default public Map<String, String> headerMap() {
        Enumeration<String> enumeration = this.req().getHeaderNames();
        Intrinsics.checkNotNullExpressionValue(enumeration, "req().headerNames");
        Sequence<String> $this$associateWith$iv = SequencesKt.asSequence(CollectionsKt.iterator(enumeration));
        boolean $i$f$associateWith = false;
        LinkedHashMap result$iv = new LinkedHashMap();
        Sequence<String> $this$associateWithTo$iv$iv = $this$associateWith$iv;
        boolean $i$f$associateWithTo = false;
        Iterator<String> iterator2 = $this$associateWithTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            String string;
            void it;
            String element$iv$iv;
            String string2 = element$iv$iv = iterator2.next();
            String string3 = element$iv$iv;
            Map map = result$iv;
            boolean bl = false;
            Intrinsics.checkNotNull(this.header((String)it));
            map.put(string3, string);
        }
        return result$iv;
    }

    @Nullable
    default public BasicAuthCredentials basicAuthCredentials() {
        return JavalinServletContextKt.getBasicAuthCredentials(this.header("Authorization"));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    default public boolean isMultipart() {
        String string = this.header("Content-Type");
        String string2 = string;
        if (string == null) return false;
        String string3 = string2;
        Locale locale = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
        String string4 = string3.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).toLowerCase(locale)");
        string2 = string4;
        if (string4 == null) return false;
        if (!StringsKt.contains$default((CharSequence)string2, "multipart/", false, 2, null)) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    default public boolean isMultipartFormData() {
        String string = this.header("Content-Type");
        String string2 = string;
        if (string == null) return false;
        String string3 = string2;
        Locale locale = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
        String string4 = string3.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).toLowerCase(locale)");
        string2 = string4;
        if (string4 == null) return false;
        if (!StringsKt.contains$default((CharSequence)string2, "multipart/form-data", false, 2, null)) return false;
        return true;
    }

    @Nullable
    default public UploadedFile uploadedFile(@NotNull String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return CollectionsKt.firstOrNull(this.uploadedFiles(fileName));
    }

    @NotNull
    default public List<UploadedFile> uploadedFiles(@NotNull String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return this.isMultipartFormData() ? MultipartUtil.INSTANCE.getUploadedFiles(this.req(), fileName) : CollectionsKt.emptyList();
    }

    @NotNull
    default public List<UploadedFile> uploadedFiles() {
        return this.isMultipartFormData() ? MultipartUtil.INSTANCE.getUploadedFiles(this.req()) : CollectionsKt.emptyList();
    }

    @NotNull
    default public Map<String, List<UploadedFile>> uploadedFileMap() {
        return this.isMultipartFormData() ? MultipartUtil.INSTANCE.getUploadedFileMap(this.req()) : MapsKt.emptyMap();
    }

    @NotNull
    default public Charset responseCharset() {
        Object object;
        Object $this$responseCharset_u24lambda_u245;
        Object object2 = this;
        try {
            $this$responseCharset_u24lambda_u245 = object2;
            boolean bl = false;
            $this$responseCharset_u24lambda_u245 = Result.constructor-impl(Charset.forName($this$responseCharset_u24lambda_u245.res().getCharacterEncoding()));
        }
        catch (Throwable bl) {
            $this$responseCharset_u24lambda_u245 = Result.constructor-impl(ResultKt.createFailure(bl));
        }
        object2 = $this$responseCharset_u24lambda_u245;
        Throwable throwable = Result.exceptionOrNull-impl(object2);
        if (throwable == null) {
            object = object2;
        } else {
            Throwable it = throwable;
            boolean bl = false;
            object = Charset.defaultCharset();
        }
        Intrinsics.checkNotNullExpressionValue(object, "runCatching { Charset.fo\u2026harset.defaultCharset() }");
        return (Charset)object;
    }

    @NotNull
    public ServletOutputStream outputStream();

    default public void writeSeekableStream(@NotNull InputStream inputStream2, @NotNull String contentType, long totalBytes) {
        Intrinsics.checkNotNullParameter(inputStream2, "inputStream");
        Intrinsics.checkNotNullParameter(contentType, "contentType");
        SeekableWriter.INSTANCE.write(this, inputStream2, contentType, totalBytes);
    }

    default public void writeSeekableStream(@NotNull InputStream inputStream2, @NotNull String contentType) {
        Intrinsics.checkNotNullParameter(inputStream2, "inputStream");
        Intrinsics.checkNotNullParameter(contentType, "contentType");
        this.writeSeekableStream(inputStream2, contentType, inputStream2.available());
    }

    @NotNull
    default public Context result(@NotNull String resultString) {
        Intrinsics.checkNotNullParameter(resultString, "resultString");
        byte[] byArray = resultString.getBytes(this.responseCharset());
        Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
        return this.result(new ByteArrayInputStream(byArray));
    }

    @NotNull
    default public Context result(@NotNull byte[] resultBytes) {
        Intrinsics.checkNotNullParameter(resultBytes, "resultBytes");
        return this.result(new ByteArrayInputStream(resultBytes));
    }

    @NotNull
    public Context result(@NotNull InputStream var1);

    @Nullable
    default public String result() {
        return JavalinServletContextKt.readAndResetStreamIfPossible(this.resultInputStream(), this.responseCharset());
    }

    @Nullable
    public InputStream resultInputStream();

    default public void async(@Nullable ExecutorService executor2, long timeout2, @Nullable Runnable onTimeout, @NotNull ThrowingRunnable<Exception> task) {
        Intrinsics.checkNotNullParameter(task, "task");
        AsyncUtil.INSTANCE.submitAsyncTask(this, executor2, timeout2, onTimeout, task);
    }

    public static /* synthetic */ void async$default(Context context, ExecutorService executorService2, long l, Runnable runnable2, ThrowingRunnable throwingRunnable, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: async");
        }
        if ((n & 1) != 0) {
            executorService2 = null;
        }
        if ((n & 2) != 0) {
            l = 0L;
        }
        context.async(executorService2, l, runnable2, throwingRunnable);
    }

    default public void async(long timeout2, @Nullable Runnable onTimeout, @NotNull ThrowingRunnable<Exception> task) {
        Intrinsics.checkNotNullParameter(task, "task");
        this.async(null, timeout2, onTimeout, task);
    }

    public static /* synthetic */ void async$default(Context context, long l, Runnable runnable2, ThrowingRunnable throwingRunnable, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: async");
        }
        if ((n & 2) != 0) {
            runnable2 = null;
        }
        context.async(l, runnable2, throwingRunnable);
    }

    default public void async(@NotNull ThrowingRunnable<Exception> task) {
        Intrinsics.checkNotNullParameter(task, "task");
        Context.async$default(this, null, 0L, null, task, 3, null);
    }

    public void future(@NotNull Supplier<? extends CompletableFuture<?>> var1);

    @NotNull
    default public Context contentType(@NotNull String contentType) {
        Context context;
        Intrinsics.checkNotNullParameter(contentType, "contentType");
        Context it = context = this;
        boolean bl = false;
        this.res().setContentType(contentType);
        return context;
    }

    @NotNull
    default public Context contentType(@NotNull ContentType contentType) {
        Intrinsics.checkNotNullParameter((Object)contentType, "contentType");
        return this.contentType(contentType.getMimeType());
    }

    @NotNull
    default public Context header(@NotNull String name, @NotNull String value) {
        Context context;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Context it = context = this;
        boolean bl = false;
        this.res().setHeader(name, value);
        return context;
    }

    public void redirect(@NotNull String var1, @NotNull HttpStatus var2);

    default public void redirect(@NotNull String location) {
        Intrinsics.checkNotNullParameter(location, "location");
        this.redirect(location, HttpStatus.FOUND);
    }

    @NotNull
    default public Context status(@NotNull HttpStatus status) {
        Context context;
        Intrinsics.checkNotNullParameter((Object)status, "status");
        Context it = context = this;
        boolean bl = false;
        this.res().setStatus(status.getCode());
        return context;
    }

    @NotNull
    default public Context status(int status) {
        Context context;
        Context it = context = this;
        boolean bl = false;
        this.res().setStatus(status);
        return context;
    }

    @NotNull
    default public HttpStatus status() {
        return HttpStatus.Companion.forStatus(this.res().getStatus());
    }

    default public int statusCode() {
        return this.res().getStatus();
    }

    @NotNull
    default public Context cookie(@NotNull String name, @NotNull String value) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        return this.cookie(name, value, -1);
    }

    @NotNull
    default public Context cookie(@NotNull String name, @NotNull String value, int maxAge) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        return this.cookie(new Cookie(name, value, null, maxAge, false, 0, false, null, null, null, 1012, null));
    }

    @NotNull
    default public Context cookie(@NotNull Cookie cookie) {
        Context context;
        Intrinsics.checkNotNullParameter(cookie, "cookie");
        Context it = context = this;
        boolean bl = false;
        CookieKt.setJavalinCookie(this.res(), cookie);
        return context;
    }

    @NotNull
    default public Context removeCookie(@NotNull String name, @Nullable String path) {
        Context context;
        Intrinsics.checkNotNullParameter(name, "name");
        Context it = context = this;
        boolean bl = false;
        CookieKt.removeCookie(this.res(), name, path);
        return context;
    }

    @NotNull
    default public Context removeCookie(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.removeCookie(name, "/");
    }

    @NotNull
    default public Context json(@NotNull Object obj, @NotNull Type type) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.contentType(ContentType.APPLICATION_JSON).result(JsonMapperKt.jsonMapper(this).toJsonString(obj, type));
    }

    @NotNull
    default public Context json(@NotNull Object obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        return this.json(obj, obj.getClass());
    }

    @NotNull
    default public Context jsonStream(@NotNull Object obj, @NotNull Type type) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.contentType(ContentType.APPLICATION_JSON).result(JsonMapperKt.jsonMapper(this).toJsonStream(obj, type));
    }

    @NotNull
    default public Context jsonStream(@NotNull Object obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        return this.jsonStream(obj, obj.getClass());
    }

    default public void writeJsonStream(@NotNull Stream<?> stream) {
        Intrinsics.checkNotNullParameter(stream, "stream");
        JsonMapperKt.jsonMapper(this).writeToOutputStream(stream, this.contentType(ContentType.APPLICATION_JSON).outputStream());
    }

    @NotNull
    default public Context html(@NotNull String html) {
        Intrinsics.checkNotNullParameter(html, "html");
        return this.contentType(ContentType.TEXT_HTML).result(html);
    }

    @NotNull
    default public Context render(@NotNull String filePath, @NotNull Map<String, ? extends Object> model) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(model, "model");
        return this.html(FileRendererKt.fileRenderer(this).render(filePath, model, this));
    }

    @NotNull
    default public Context render(@NotNull String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        return this.render(filePath, new LinkedHashMap());
    }
}

