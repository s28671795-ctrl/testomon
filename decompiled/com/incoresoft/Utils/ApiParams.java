/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.incoresoft.Utils.ApiUtils;
import io.javalin.http.Context;
import java.util.Objects;

public class ApiParams {
    private final Context context;
    private JSONObject queryParamsJSON;
    private JSONObject formParamsJSON;
    private JSONObject requestBody;

    public ApiParams(Context context) {
        this.context = Objects.requireNonNull(context);
    }

    public <T> T getQueryParamsAsClass(Class<T> tClass) {
        return ApiUtils.getJSONAsClass(this.getQueryParamsJSON(), tClass);
    }

    public <T> T getFormParamsAsClass(Class<T> tClass) {
        return ApiUtils.getJSONAsClass(this.getFormParamsJSON(), tClass);
    }

    public <T> T getBodyAsClass(Class<T> tClass) {
        return ApiUtils.getJSONAsClass(this.getRequestBody(), tClass);
    }

    private JSONObject getQueryParamsJSON() {
        if (this.queryParamsJSON == null) {
            this.queryParamsJSON = ApiUtils.getQueryParamJSON(this.context);
        }
        return this.queryParamsJSON;
    }

    private JSONObject getFormParamsJSON() {
        if (this.formParamsJSON == null) {
            this.formParamsJSON = ApiUtils.getFormParamJSON(this.context);
        }
        return this.formParamsJSON;
    }

    private JSONObject getRequestBody() {
        if (this.requestBody == null) {
            this.requestBody = JSON.parseObject(this.context.body());
        }
        return this.requestBody;
    }
}

