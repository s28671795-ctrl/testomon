/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.eventmanager.EventNode;
import com.incoresoft.middleware.eventmanager.EventNodeContext;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.model.settings.Settings;
import com.incoresoft.middleware.plugin.ExtensionContext;
import com.incoresoft.middleware.plugin.InitializableExtension;
import com.incoresoft.middleware.validation.ValidationViolation;
import io.javalin.http.NotFoundResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class EventManagerExtension
implements InitializableExtension<ExtensionContext> {
    protected Settings settings;

    public abstract String getNameLocaleKey();

    @Override
    public void init(ExtensionContext context) {
        this.settings = context.getSettings();
        context.getJavalin().get("event_manager/" + this.getExtensionId() + "/icon", ctx -> {
            ctx.contentType("image/svg+xml");
            ctx.result(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("public/plugin_icon.svg")));
        }, AppRole.ANYONE);
        context.getJavalin().get("event_manager/" + this.getExtensionId() + "/{file}", ctx -> {
            String file = ctx.pathParam("file");
            InputStream inputStream2 = this.getClass().getClassLoader().getResourceAsStream("public/" + file);
            if (inputStream2 == null) {
                throw new NotFoundResponse();
            }
            ctx.result(inputStream2);
        }, AppRole.ANYONE);
        context.getJavalin().get("event_manager/" + this.getExtensionId() + "/locales/{lang}", ctx -> {
            String language2 = ctx.pathParam("lang");
            ctx.json(this.localeProvider().apply(new Locale(language2)));
        });
    }

    public List<String> getFrontComponents() {
        return Optional.ofNullable(this.getClass().getClassLoader().getResourceAsStream("public/component.json")).map(is -> {
            try {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).map(it -> JSON.parseArray(it, String.class)).orElseGet(Collections::emptyList);
    }

    public JSONObject getDevParams() {
        return Optional.ofNullable(this.getClass().getClassLoader().getResourceAsStream("public/dev.json")).map(is -> {
            try {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).map(JSON::parseObject).orElse(new JSONObject());
    }

    public JSONObject getSettings() {
        return Optional.ofNullable(this.getClass().getClassLoader().getResourceAsStream("public/settings.json")).map(is -> {
            try {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).map(JSON::parseObject).orElse(new JSONObject());
    }

    public Function<Locale, Map<String, String>> localeProvider() {
        return locale -> Collections.emptyMap();
    }

    public abstract String getExtensionId();

    public abstract List<ValidationViolation> validate(EventNode var1);

    public abstract Integer execute(EventNodeContext var1);
}

