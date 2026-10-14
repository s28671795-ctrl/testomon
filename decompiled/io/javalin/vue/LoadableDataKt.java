/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0002"}, d2={"loadableDataScript", "", "javalin"})
public final class LoadableDataKt {
    @NotNull
    public static final String loadableDataScript = "\n\n<script nonce=\"@internalAddNonce\">\n    class LoadableData {\n        constructor(url, cache = true, errorCallback = null) {\n            this._url = url;\n            this._errorCallback = errorCallback;\n            this.refresh(cache);\n            this.addRefreshListener();\n        }\n        refresh(cache = true) {\n            this.data = null;\n            this.loading = true;\n            this.loaded = false;\n            this.loadError = null;\n            let cacheKey = \"LoadableData:\" + this._url;\n            if (cache) {\n                this.data = JSON.parse(localStorage.getItem(cacheKey)) || null;\n                this.loaded = this.data !== null;\n                this.loading = this.loaded === false;\n            }\n            fetch(this._url).then(res => {\n                if (res.ok) return res.json();\n                throw JSON.stringify({code: res.status, text: res.statusText});\n            }).then(data => {\n                this.data = data;\n                this.loaded = true;\n                if (cache) {\n                    localStorage.setItem(cacheKey, JSON.stringify(data));\n                }\n            }).catch(error => {\n                this.loadError = JSON.parse(error);\n                if (this._errorCallback !== null) { // should probably handle in UI\n                    this._errorCallback(error);\n                }\n            }).finally(() => this.loading = false);\n        }\n        refreshAll() {\n            LoadableData.refreshAll(this._url);\n        }\n        static refreshAll(url) {\n            window.dispatchEvent(new CustomEvent(\"javalinvue-loadable-data-update\", {detail: url}));\n        }\n        addRefreshListener() {\n            window.addEventListener(\"javalinvue-loadable-data-update\", e => {\n                if (this._url === e.detail) {\n                    this.refresh(false);\n                }\n            }, false);\n        }\n    }\n</script>";
}

