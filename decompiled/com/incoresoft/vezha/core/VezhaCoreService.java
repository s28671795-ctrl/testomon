/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.core;

import com.incoresoft.core.models.CoreService;
import org.jetbrains.annotations.NotNull;

public class VezhaCoreService
implements CoreService {
    public static final String ID = "middleware_core";

    @Override
    @NotNull
    public String getId() {
        return ID;
    }

    @Override
    @NotNull
    public String getName() {
        return "Middleware-Core";
    }

    @Override
    public String getFilename() {
        return "middleware-core.jar";
    }

    @Override
    public String getSpecificationTitle() {
        return "middleware-core";
    }
}

