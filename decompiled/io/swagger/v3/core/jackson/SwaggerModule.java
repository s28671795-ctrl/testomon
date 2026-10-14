/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.v3.core.jackson.PackageVersion;
import io.swagger.v3.core.jackson.SwaggerAnnotationIntrospector;

public class SwaggerModule
extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public SwaggerModule() {
        super(PackageVersion.VERSION);
    }

    @Override
    public void setupModule(Module.SetupContext context) {
        super.setupModule(context);
        context.insertAnnotationIntrospector(new SwaggerAnnotationIntrospector());
    }
}

