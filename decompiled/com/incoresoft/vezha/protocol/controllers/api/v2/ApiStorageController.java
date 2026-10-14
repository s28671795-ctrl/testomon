/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.plugin.commons.utils.ContentTypeUtils;
import com.incoresoft.vezha.analytics.cameraanalytics.StoredImageRequester;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class ApiStorageController {
    private static final Pattern NATS_STORED_FILE_PATTERN = Pattern.compile("^nats/.*?/([0-9]+)/.*$");
    private final Storage storage;
    private final StoredImageRequester storedImageRequester;

    @Inject
    public void init(Javalin app) {
        app.get("storage/*", this::storageHandler, AppRole.LOGGED_IN, AppRole.API_CALL);
    }

    public void storageHandler(Context ctx) throws FileNotFoundException {
        String imagePath = ctx.path().replace("/storage/", "");
        if (StringUtils.isEmpty(imagePath)) {
            ctx.status(404);
            return;
        }
        Matcher matcher = NATS_STORED_FILE_PATTERN.matcher(imagePath);
        if (matcher.matches()) {
            int vaId = Integer.parseInt(matcher.group(1));
            Optional<InputStream> file = this.storedImageRequester.request(vaId, imagePath);
            if (file.isPresent()) {
                ctx.contentType(ContentTypeUtils.guessContentType(imagePath));
                ctx.result(file.orElseThrow());
                return;
            }
        } else {
            File file = this.storage.get(URLDecoder.decode(imagePath, StandardCharsets.UTF_8));
            if (file != null && Files.exists(file.toPath(), new LinkOption[0])) {
                ctx.contentType(ContentTypeUtils.guessContentType(imagePath));
                ctx.result(new FileInputStream(file));
                return;
            }
        }
        throw new NotFoundResponse();
    }

    @Inject
    public ApiStorageController(Storage storage, StoredImageRequester storedImageRequester) {
        this.storage = storage;
        this.storedImageRequester = storedImageRequester;
    }
}

