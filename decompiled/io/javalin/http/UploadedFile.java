/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import jakarta.servlet.http.Part;
import java.io.Closeable;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J%\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\b0\n\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/javalin/http/UploadedFile;", "", "part", "Ljakarta/servlet/http/Part;", "(Ljakarta/servlet/http/Part;)V", "content", "Ljava/io/InputStream;", "contentAndClose", "T", "callback", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "contentType", "", "extension", "filename", "size", "", "javalin"})
@SourceDebugExtension(value={"SMAP\nUploadedFile.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UploadedFile.kt\nio/javalin/http/UploadedFile\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,30:1\n1#2:31\n*E\n"})
public final class UploadedFile {
    @NotNull
    private final Part part;

    public UploadedFile(@NotNull Part part) {
        Intrinsics.checkNotNullParameter(part, "part");
        this.part = part;
    }

    @NotNull
    public final InputStream content() {
        InputStream inputStream2 = this.part.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream2, "part.inputStream");
        return inputStream2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final <T> T contentAndClose(@NotNull Function1<? super InputStream, ? extends T> callback) {
        T t2;
        Intrinsics.checkNotNullParameter(callback, "callback");
        Closeable closeable = this.content();
        Throwable throwable = null;
        try {
            InputStream it = (InputStream)closeable;
            boolean bl = false;
            t2 = callback.invoke(it);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
        return t2;
    }

    @Nullable
    public final String contentType() {
        return this.part.getContentType();
    }

    @NotNull
    public final String filename() {
        String string = this.part.getSubmittedFileName();
        Intrinsics.checkNotNullExpressionValue(string, "part.submittedFileName");
        return string;
    }

    @NotNull
    public final String extension() {
        String string = this.part.getSubmittedFileName();
        Intrinsics.checkNotNullExpressionValue(string, "part.submittedFileName");
        return StringsKt.replaceBeforeLast$default(string, ".", "", null, 4, null);
    }

    public final long size() {
        return this.part.getSize();
    }
}

