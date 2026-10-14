/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR&\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0013"}, d2={"Lio/javalin/openapi/OpenApiServerVariable;", "", "()V", "default", "", "getDefault", "()Ljava/lang/String;", "setDefault", "(Ljava/lang/String;)V", "description", "getDescription", "setDescription", "values", "", "getValues", "()[Ljava/lang/String;", "setValues", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "openapi-specification"})
public final class OpenApiServerVariable {
    @JsonProperty(value="enum")
    @NotNull
    private String[] values;
    @NotNull
    private String default;
    @Nullable
    private String description;

    public OpenApiServerVariable() {
        String[] stringArray = new String[]{""};
        this.values = stringArray;
        this.default = "";
    }

    @NotNull
    public final String[] getValues() {
        return this.values;
    }

    public final void setValues(@NotNull String[] stringArray) {
        Intrinsics.checkNotNullParameter(stringArray, "<set-?>");
        this.values = stringArray;
    }

    @NotNull
    public final String getDefault() {
        return this.default;
    }

    public final void setDefault(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.default = string;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(@Nullable String string) {
        this.description = string;
    }
}

