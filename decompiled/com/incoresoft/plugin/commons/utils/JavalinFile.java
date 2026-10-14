/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.incoresoft.plugin.commons.utils.JavalinUtils;
import io.javalin.http.Context;
import java.io.File;
import java.nio.file.Path;

public class JavalinFile {
    private final Context context;
    private final String fileName;

    public File save(Path directory) {
        return JavalinUtils.receiveAndSaveFile(this.context, this.fileName, directory.toString());
    }

    private JavalinFile(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public static JavalinFile from(Context context, String fileName) {
        return new JavalinFile(context, fileName);
    }
}

