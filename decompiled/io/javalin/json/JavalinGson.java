/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.json;

import com.google.gson.Gson;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.json.JsonMapper;
import io.javalin.json.PipedStreamUtil;
import io.javalin.util.CoreDependency;
import io.javalin.util.DependencyUtil;
import io.javalin.util.JavalinLogger;
import io.javalin.util.Util;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J'\u0010\u0005\u001a\u0002H\u0006\"\b\b\u0000\u0010\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016\u00a2\u0006\u0002\u0010\fJ'\u0010\r\u001a\u0002H\u0006\"\b\b\u0000\u0010\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0016\u00a2\u0006\u0002\u0010\u000fJ\u0018\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u000bH\u0016J\u0018\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u000bH\u0016J\u001c\u0010\u0014\u001a\u00020\u00152\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2={"Lio/javalin/json/JavalinGson;", "Lio/javalin/json/JsonMapper;", "gson", "Lcom/google/gson/Gson;", "(Lcom/google/gson/Gson;)V", "fromJsonStream", "T", "", "json", "Ljava/io/InputStream;", "targetType", "Ljava/lang/reflect/Type;", "(Ljava/io/InputStream;Ljava/lang/reflect/Type;)Ljava/lang/Object;", "fromJsonString", "", "(Ljava/lang/String;Ljava/lang/reflect/Type;)Ljava/lang/Object;", "toJsonStream", "obj", "type", "toJsonString", "writeToOutputStream", "", "stream", "Ljava/util/stream/Stream;", "outputStream", "Ljava/io/OutputStream;", "javalin"})
public class JavalinGson
implements JsonMapper {
    @NotNull
    private final Gson gson;

    public JavalinGson(@NotNull Gson gson) {
        Intrinsics.checkNotNullParameter(gson, "gson");
        this.gson = gson;
        if (!Util.INSTANCE.classExists(CoreDependency.GSON.getTestClass())) {
            String message = StringsKt.trimMargin$default("|It looks like you don't have Gson dependency on classpath.\n                   |The easiest way to fix this is to simply add the '" + CoreDependency.GSON.getArtifactId() + "' dependency:\n                   |\n                   |" + DependencyUtil.INSTANCE.mavenAndGradleSnippets(CoreDependency.GSON) + "\n                   |", null, 1, null);
            JavalinLogger.warn$default(DependencyUtil.INSTANCE.wrapInSeparators(message), null, 2, null);
            throw new InternalServerErrorResponse(message, null, 2, null);
        }
    }

    public /* synthetic */ JavalinGson(Gson gson, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            gson = new Gson();
        }
        this(gson);
    }

    @Override
    @NotNull
    public String toJsonString(@NotNull Object obj, @NotNull Type type) {
        String string;
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(type, "type");
        if (obj instanceof String) {
            string = (String)obj;
        } else {
            String string2 = this.gson.toJson(obj, type);
            string = string2;
            Intrinsics.checkNotNullExpressionValue(string2, "gson.toJson(obj, type)");
        }
        return string;
    }

    @Override
    @NotNull
    public InputStream toJsonStream(@NotNull Object obj, @NotNull Type type) {
        InputStream inputStream2;
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(type, "type");
        if (obj instanceof String) {
            String string = (String)obj;
            Charset charset = Charsets.UTF_8;
            byte[] byArray = string.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
            inputStream2 = new ByteArrayInputStream(byArray);
        } else {
            inputStream2 = PipedStreamUtil.INSTANCE.getInputStream((Function1<? super PipedOutputStream, Unit>)new Function1<PipedOutputStream, Unit>(this, obj, type){
                final /* synthetic */ JavalinGson this$0;
                final /* synthetic */ Object $obj;
                final /* synthetic */ Type $type;
                {
                    this.this$0 = $receiver;
                    this.$obj = $obj;
                    this.$type = $type;
                    super(1);
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public final void invoke(@NotNull PipedOutputStream pipedOutputStream) {
                    Intrinsics.checkNotNullParameter(pipedOutputStream, "pipedOutputStream");
                    Closeable closeable = new BufferedWriter(new OutputStreamWriter(pipedOutputStream));
                    JavalinGson javalinGson = this.this$0;
                    Object object = this.$obj;
                    Type type = this.$type;
                    Throwable throwable = null;
                    try {
                        BufferedWriter bufferedWriter = (BufferedWriter)closeable;
                        boolean bl = false;
                        JavalinGson.access$getGson$p(javalinGson).toJson(object, type, bufferedWriter);
                        Unit unit = Unit.INSTANCE;
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        CloseableKt.closeFinally(closeable, throwable);
                    }
                }
            });
        }
        return inputStream2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeToOutputStream(@NotNull Stream<?> stream, @NotNull OutputStream outputStream2) {
        Intrinsics.checkNotNullParameter(stream, "stream");
        Intrinsics.checkNotNullParameter(outputStream2, "outputStream");
        Closeable closeable = new BufferedWriter(new OutputStreamWriter(outputStream2));
        Throwable throwable = null;
        try {
            BufferedWriter bufferedWriter = (BufferedWriter)closeable;
            boolean bl = false;
            Ref.BooleanRef hasComma = new Ref.BooleanRef();
            bufferedWriter.write("[");
            stream.forEach(arg_0 -> JavalinGson.writeToOutputStream$lambda$1$lambda$0(hasComma, bufferedWriter, this, arg_0));
            bufferedWriter.write("]");
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    @Override
    @NotNull
    public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        Object t2 = this.gson.fromJson(json, targetType);
        Intrinsics.checkNotNullExpressionValue(t2, "gson.fromJson(json, targetType)");
        return t2;
    }

    @Override
    @NotNull
    public <T> T fromJsonStream(@NotNull InputStream json, @NotNull Type targetType) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        Object t2 = this.gson.fromJson((Reader)new InputStreamReader(json), targetType);
        Intrinsics.checkNotNullExpressionValue(t2, "gson.fromJson(InputStreamReader(json), targetType)");
        return t2;
    }

    private static final void writeToOutputStream$lambda$1$lambda$0(Ref.BooleanRef $hasComma, BufferedWriter $bufferedWriter, JavalinGson this$0, Object it) {
        Intrinsics.checkNotNullParameter($hasComma, "$hasComma");
        Intrinsics.checkNotNullParameter($bufferedWriter, "$bufferedWriter");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if ($hasComma.element) {
            $bufferedWriter.write(",");
        } else {
            $hasComma.element = true;
        }
        this$0.gson.toJson(it, (Appendable)$bufferedWriter);
    }

    public JavalinGson() {
        this(null, 1, null);
    }

    public static final /* synthetic */ Gson access$getGson$p(JavalinGson $this) {
        return $this.gson;
    }
}

