/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.json.JavalinJacksonKt;
import io.javalin.json.JsonMapper;
import io.javalin.json.PipedStreamUtil;
import io.javalin.util.ConcurrencyUtilKt;
import io.javalin.util.CoreDependency;
import io.javalin.util.DependencyUtil;
import io.javalin.util.JavalinLogger;
import io.javalin.util.Util;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import java.util.stream.Stream;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J'\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016\u00a2\u0006\u0002\u0010\u0011J'\u0010\u0012\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0010H\u0016\u00a2\u0006\u0002\u0010\u0014J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0010H\u0016J\u0018\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0010H\u0016J\u0014\u0010\u0019\u001a\u00020\u00002\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u001bJ\u001c\u0010\u001c\u001a\u00020\u001d2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001f2\u0006\u0010 \u001a\u00020!H\u0016R\u001b\u0010\u0005\u001a\u00020\u00038FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lio/javalin/json/JavalinJackson;", "Lio/javalin/json/JsonMapper;", "objectMapper", "Lcom/fasterxml/jackson/databind/ObjectMapper;", "(Lcom/fasterxml/jackson/databind/ObjectMapper;)V", "mapper", "getMapper", "()Lcom/fasterxml/jackson/databind/ObjectMapper;", "mapper$delegate", "Lkotlin/Lazy;", "fromJsonStream", "T", "", "json", "Ljava/io/InputStream;", "targetType", "Ljava/lang/reflect/Type;", "(Ljava/io/InputStream;Ljava/lang/reflect/Type;)Ljava/lang/Object;", "fromJsonString", "", "(Ljava/lang/String;Ljava/lang/reflect/Type;)Ljava/lang/Object;", "toJsonStream", "obj", "type", "toJsonString", "updateMapper", "updateFunction", "Ljava/util/function/Consumer;", "writeToOutputStream", "", "stream", "Ljava/util/stream/Stream;", "outputStream", "Ljava/io/OutputStream;", "Companion", "javalin"})
public final class JavalinJackson
implements JsonMapper {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private ObjectMapper objectMapper;
    @NotNull
    private final Lazy mapper$delegate;

    public JavalinJackson(@Nullable ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.mapper$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<ObjectMapper>(this){
            final /* synthetic */ JavalinJackson this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final ObjectMapper invoke() {
                if (!Util.INSTANCE.classExists(CoreDependency.JACKSON.getTestClass())) {
                    String message = StringsKt.trimMargin$default("|It looks like you don't have an object mapper configured.\n                   |The easiest way to fix this is to simply add the '" + CoreDependency.JACKSON.getArtifactId() + "' dependency:\n                   |\n                   |" + DependencyUtil.INSTANCE.mavenAndGradleSnippets(CoreDependency.JACKSON) + "\n                   |\n                   |If you're using Kotlin, you will need to add '" + CoreDependency.JACKSON_KT.getArtifactId() + "'.\n                   |\n                   |To use a different object mapper, visit https://javalin.io/documentation#configuring-the-json-mapper", null, 1, null);
                    JavalinLogger.warn$default(DependencyUtil.INSTANCE.wrapInSeparators(message), null, 2, null);
                    throw new InternalServerErrorResponse(message, null, 2, null);
                }
                ObjectMapper objectMapper = JavalinJackson.access$getObjectMapper$p(this.this$0);
                if (objectMapper == null) {
                    objectMapper = JavalinJackson.Companion.defaultMapper();
                }
                return objectMapper;
            }
        }, 1, null);
    }

    public /* synthetic */ JavalinJackson(ObjectMapper objectMapper, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            objectMapper = null;
        }
        this(objectMapper);
    }

    @NotNull
    public final ObjectMapper getMapper() {
        Lazy lazy = this.mapper$delegate;
        return (ObjectMapper)lazy.getValue();
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
            String string2 = this.getMapper().writeValueAsString(obj);
            string = string2;
            Intrinsics.checkNotNullExpressionValue(string2, "mapper.writeValueAsString(obj)");
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
            inputStream2 = PipedStreamUtil.INSTANCE.getInputStream((Function1<? super PipedOutputStream, Unit>)new Function1<PipedOutputStream, Unit>(this, obj){
                final /* synthetic */ JavalinJackson this$0;
                final /* synthetic */ Object $obj;
                {
                    this.this$0 = $receiver;
                    this.$obj = $obj;
                    super(1);
                }

                public final void invoke(@NotNull PipedOutputStream pipedOutputStream) {
                    Intrinsics.checkNotNullParameter(pipedOutputStream, "pipedOutputStream");
                    this.this$0.getMapper().getFactory().createGenerator(pipedOutputStream).writeObject(this.$obj);
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
        Closeable closeable = this.getMapper().writer().writeValuesAsArray(outputStream2);
        Throwable throwable = null;
        try {
            SequenceWriter sequenceWriter = (SequenceWriter)closeable;
            boolean bl = false;
            stream.forEach(arg_0 -> JavalinJackson.writeToOutputStream$lambda$1$lambda$0(sequenceWriter, arg_0));
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
        Object t2 = this.getMapper().readValue(json, this.getMapper().getTypeFactory().constructType(targetType));
        Intrinsics.checkNotNullExpressionValue(t2, "mapper.readValue(json, m\u2026onstructType(targetType))");
        return t2;
    }

    @Override
    @NotNull
    public <T> T fromJsonStream(@NotNull InputStream json, @NotNull Type targetType) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        Object t2 = this.getMapper().readValue(json, this.getMapper().getTypeFactory().constructType(targetType));
        Intrinsics.checkNotNullExpressionValue(t2, "mapper.readValue(json, m\u2026onstructType(targetType))");
        return t2;
    }

    @NotNull
    public final JavalinJackson updateMapper(@NotNull Consumer<ObjectMapper> updateFunction) {
        Intrinsics.checkNotNullParameter(updateFunction, "updateFunction");
        updateFunction.accept(this.getMapper());
        return this;
    }

    private static final void writeToOutputStream$lambda$1$lambda$0(SequenceWriter $sequenceWriter, Object it) {
        $sequenceWriter.write(it);
    }

    public JavalinJackson() {
        this(null, 1, null);
    }

    @JvmStatic
    @NotNull
    public static final ObjectMapper defaultMapper() {
        return Companion.defaultMapper();
    }

    public static final /* synthetic */ ObjectMapper access$getObjectMapper$p(JavalinJackson $this) {
        return $this.objectMapper;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007\u00a8\u0006\u0005"}, d2={"Lio/javalin/json/JavalinJackson$Companion;", "", "()V", "defaultMapper", "Lcom/fasterxml/jackson/databind/ObjectMapper;", "javalin"})
    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public final ObjectMapper defaultMapper() {
            return JavalinJacksonKt.access$registerOptionalModule(JavalinJacksonKt.access$registerOptionalModule(JavalinJacksonKt.access$registerOptionalModule(JavalinJacksonKt.access$registerOptionalModule(new ObjectMapper(), CoreDependency.JACKSON_KT.getTestClass()), CoreDependency.JACKSON_JSR_310.getTestClass()), CoreDependency.JACKSON_ECLIPSE_COLLECTIONS.getTestClass()), CoreDependency.JACKSON_KTORM.getTestClass());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

