/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.incoresoft.plugin.commons.exceptions.file.UploadedFileMustHaveJarExtension;
import com.incoresoft.plugin.commons.exceptions.file.UploadedFileNotFoundException;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import jakarta.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class JavalinUtils {
    public static File receiveAndSaveFile(Context ctx, String name, String dir) {
        return Optional.ofNullable(ctx.uploadedFile(name)).map(file -> JavalinUtils.receiveAndSaveFile(file, dir)).orElseThrow(() -> new UploadedFileNotFoundException(name));
    }

    private static File receiveAndSaveFile(UploadedFile file, String dir) {
        Path path = Paths.get(dir, file.filename());
        FileUtil.streamToFile(file.content(), path.toString());
        return path.toFile();
    }

    public static void validateFileJarExtension(Context context, String name) {
        UploadedFile file = context.uploadedFile(name);
        if (file == null) {
            throw new UploadedFileNotFoundException(name);
        }
        if (!file.filename().endsWith(".jar")) {
            throw new UploadedFileMustHaveJarExtension(name);
        }
    }

    public static void provideFile(Context ctx, File file) {
        ctx.header("Content-Disposition", "attachment; filename=" + file.getName());
        ctx.header("Content-Length", String.valueOf(file.length()));
        ctx.contentType("application/octet-stream");
        try (FileInputStream stream = new FileInputStream(file);
             ServletOutputStream outputStream2 = ctx.res().getOutputStream();){
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = stream.read(buffer)) != -1) {
                outputStream2.write(buffer, 0, bytesRead);
            }
            outputStream2.flush();
            ctx.res().flushBuffer();
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to provide file", e);
        }
    }
}

