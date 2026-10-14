/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007\u00a8\u0006\u000b"}, d2={"Lio/javalin/util/FileUtil;", "", "()V", "readFile", "", "path", "readResource", "streamToFile", "", "inputStream", "Ljava/io/InputStream;", "javalin"})
public final class FileUtil {
    @NotNull
    public static final FileUtil INSTANCE = new FileUtil();

    private FileUtil() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @JvmStatic
    public static final void streamToFile(@NotNull InputStream inputStream2, @NotNull String path) {
        Intrinsics.checkNotNullParameter(inputStream2, "inputStream");
        Intrinsics.checkNotNullParameter(path, "path");
        File newFile = new File(path);
        newFile.getParentFile().mkdirs();
        newFile.createNewFile();
        Closeable closeable = inputStream2;
        Throwable throwable = null;
        try {
            InputStream input = (InputStream)closeable;
            boolean bl = false;
            CopyOption[] copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            long l = Files.copy(input, newFile.toPath(), copyOptionArray);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    @JvmStatic
    @NotNull
    public static final String readResource(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        URL uRL = FileUtil.class.getResource(path);
        Intrinsics.checkNotNullExpressionValue(uRL, "FileUtil::class.java.getResource(path)");
        URL uRL2 = uRL;
        Charset charset = Charsets.UTF_8;
        byte[] byArray = TextStreamsKt.readBytes(uRL2);
        return new String(byArray, charset);
    }

    @JvmStatic
    @NotNull
    public static final String readFile(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return FilesKt.readText$default(new File(path), null, 1, null);
    }
}

