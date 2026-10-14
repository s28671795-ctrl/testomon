/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.http.SameSite;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b,\b\u0086\b\u0018\u00002\u00020\u0001Bm\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0010J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0007H\u00c6\u0003J\t\u00101\u001a\u00020\tH\u00c6\u0003J\t\u00102\u001a\u00020\u0007H\u00c6\u0003J\t\u00103\u001a\u00020\tH\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003Js\u00106\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\t2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00c6\u0001J\u0013\u00107\u001a\u00020\t2\b\u00108\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00109\u001a\u00020\u0007H\u00d6\u0001J\t\u0010:\u001a\u00020\u0003H\u00d6\u0001R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0012\"\u0004\b\u0016\u0010\u0014R\u001a\u0010\u000b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0012\"\u0004\b\u001f\u0010\u0014R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0012\"\u0004\b!\u0010\u0014R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0017\"\u0004\b'\u0010\u0019R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0012\"\u0004\b)\u0010\u0014R\u001a\u0010\n\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001b\"\u0004\b+\u0010\u001d\u00a8\u0006;"}, d2={"Lio/javalin/http/Cookie;", "", "name", "", "value", "path", "maxAge", "", "secure", "", "version", "isHttpOnly", "comment", "domain", "sameSite", "Lio/javalin/http/SameSite;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IZIZLjava/lang/String;Ljava/lang/String;Lio/javalin/http/SameSite;)V", "getComment", "()Ljava/lang/String;", "setComment", "(Ljava/lang/String;)V", "getDomain", "setDomain", "()Z", "setHttpOnly", "(Z)V", "getMaxAge", "()I", "setMaxAge", "(I)V", "getName", "setName", "getPath", "setPath", "getSameSite", "()Lio/javalin/http/SameSite;", "setSameSite", "(Lio/javalin/http/SameSite;)V", "getSecure", "setSecure", "getValue", "setValue", "getVersion", "setVersion", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "javalin"})
public final class Cookie {
    @NotNull
    private String name;
    @NotNull
    private String value;
    @NotNull
    private String path;
    private int maxAge;
    private boolean secure;
    private int version;
    private boolean isHttpOnly;
    @Nullable
    private String comment;
    @Nullable
    private String domain;
    @Nullable
    private SameSite sameSite;

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value, @NotNull String path, int maxAge, boolean secure, int version, boolean isHttpOnly, @Nullable String comment, @Nullable String domain, @Nullable SameSite sameSite) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        this.name = name;
        this.value = value;
        this.path = path;
        this.maxAge = maxAge;
        this.secure = secure;
        this.version = version;
        this.isHttpOnly = isHttpOnly;
        this.comment = comment;
        this.domain = domain;
        this.sameSite = sameSite;
    }

    public /* synthetic */ Cookie(String string, String string2, String string3, int n, boolean bl, int n2, boolean bl2, String string4, String string5, SameSite sameSite, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 4) != 0) {
            string3 = "/";
        }
        if ((n3 & 8) != 0) {
            n = -1;
        }
        if ((n3 & 0x10) != 0) {
            bl = false;
        }
        if ((n3 & 0x20) != 0) {
            n2 = 0;
        }
        if ((n3 & 0x40) != 0) {
            bl2 = false;
        }
        if ((n3 & 0x80) != 0) {
            string4 = null;
        }
        if ((n3 & 0x100) != 0) {
            string5 = null;
        }
        if ((n3 & 0x200) != 0) {
            sameSite = null;
        }
        this(string, string2, string3, n, bl, n2, bl2, string4, string5, sameSite);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.name = string;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    public final void setValue(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.value = string;
    }

    @NotNull
    public final String getPath() {
        return this.path;
    }

    public final void setPath(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.path = string;
    }

    public final int getMaxAge() {
        return this.maxAge;
    }

    public final void setMaxAge(int n) {
        this.maxAge = n;
    }

    public final boolean getSecure() {
        return this.secure;
    }

    public final void setSecure(boolean bl) {
        this.secure = bl;
    }

    public final int getVersion() {
        return this.version;
    }

    public final void setVersion(int n) {
        this.version = n;
    }

    public final boolean isHttpOnly() {
        return this.isHttpOnly;
    }

    public final void setHttpOnly(boolean bl) {
        this.isHttpOnly = bl;
    }

    @Nullable
    public final String getComment() {
        return this.comment;
    }

    public final void setComment(@Nullable String string) {
        this.comment = string;
    }

    @Nullable
    public final String getDomain() {
        return this.domain;
    }

    public final void setDomain(@Nullable String string) {
        this.domain = string;
    }

    @Nullable
    public final SameSite getSameSite() {
        return this.sameSite;
    }

    public final void setSameSite(@Nullable SameSite sameSite) {
        this.sameSite = sameSite;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final String component2() {
        return this.value;
    }

    @NotNull
    public final String component3() {
        return this.path;
    }

    public final int component4() {
        return this.maxAge;
    }

    public final boolean component5() {
        return this.secure;
    }

    public final int component6() {
        return this.version;
    }

    public final boolean component7() {
        return this.isHttpOnly;
    }

    @Nullable
    public final String component8() {
        return this.comment;
    }

    @Nullable
    public final String component9() {
        return this.domain;
    }

    @Nullable
    public final SameSite component10() {
        return this.sameSite;
    }

    @NotNull
    public final Cookie copy(@NotNull String name, @NotNull String value, @NotNull String path, int maxAge, boolean secure, int version, boolean isHttpOnly, @Nullable String comment, @Nullable String domain, @Nullable SameSite sameSite) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        return new Cookie(name, value, path, maxAge, secure, version, isHttpOnly, comment, domain, sameSite);
    }

    public static /* synthetic */ Cookie copy$default(Cookie cookie, String string, String string2, String string3, int n, boolean bl, int n2, boolean bl2, String string4, String string5, SameSite sameSite, int n3, Object object) {
        if ((n3 & 1) != 0) {
            string = cookie.name;
        }
        if ((n3 & 2) != 0) {
            string2 = cookie.value;
        }
        if ((n3 & 4) != 0) {
            string3 = cookie.path;
        }
        if ((n3 & 8) != 0) {
            n = cookie.maxAge;
        }
        if ((n3 & 0x10) != 0) {
            bl = cookie.secure;
        }
        if ((n3 & 0x20) != 0) {
            n2 = cookie.version;
        }
        if ((n3 & 0x40) != 0) {
            bl2 = cookie.isHttpOnly;
        }
        if ((n3 & 0x80) != 0) {
            string4 = cookie.comment;
        }
        if ((n3 & 0x100) != 0) {
            string5 = cookie.domain;
        }
        if ((n3 & 0x200) != 0) {
            sameSite = cookie.sameSite;
        }
        return cookie.copy(string, string2, string3, n, bl, n2, bl2, string4, string5, sameSite);
    }

    @NotNull
    public String toString() {
        return "Cookie(name=" + this.name + ", value=" + this.value + ", path=" + this.path + ", maxAge=" + this.maxAge + ", secure=" + this.secure + ", version=" + this.version + ", isHttpOnly=" + this.isHttpOnly + ", comment=" + this.comment + ", domain=" + this.domain + ", sameSite=" + this.sameSite + ")";
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = result * 31 + this.value.hashCode();
        result = result * 31 + this.path.hashCode();
        result = result * 31 + Integer.hashCode(this.maxAge);
        int n = this.secure ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        result = result * 31 + Integer.hashCode(this.version);
        int n2 = this.isHttpOnly ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result = result * 31 + n2;
        result = result * 31 + (this.comment == null ? 0 : this.comment.hashCode());
        result = result * 31 + (this.domain == null ? 0 : this.domain.hashCode());
        result = result * 31 + (this.sameSite == null ? 0 : this.sameSite.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Cookie)) {
            return false;
        }
        Cookie cookie = (Cookie)other;
        if (!Intrinsics.areEqual(this.name, cookie.name)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.value, cookie.value)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.path, cookie.path)) {
            return false;
        }
        if (this.maxAge != cookie.maxAge) {
            return false;
        }
        if (this.secure != cookie.secure) {
            return false;
        }
        if (this.version != cookie.version) {
            return false;
        }
        if (this.isHttpOnly != cookie.isHttpOnly) {
            return false;
        }
        if (!Intrinsics.areEqual(this.comment, cookie.comment)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.domain, cookie.domain)) {
            return false;
        }
        return this.sameSite == cookie.sameSite;
    }

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value, @NotNull String path, int maxAge, boolean secure, int version, boolean isHttpOnly, @Nullable String comment, @Nullable String domain) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        this(name, value, path, maxAge, secure, version, isHttpOnly, comment, domain, null, 512, null);
    }

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value, @NotNull String path, int maxAge, boolean secure, int version, boolean isHttpOnly, @Nullable String comment) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        this(name, value, path, maxAge, secure, version, isHttpOnly, comment, null, null, 768, null);
    }

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value, @NotNull String path, int maxAge, boolean secure, int version, boolean isHttpOnly) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        this(name, value, path, maxAge, secure, version, isHttpOnly, null, null, null, 896, null);
    }

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value, @NotNull String path, int maxAge, boolean secure, int version) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        this(name, value, path, maxAge, secure, version, false, null, null, null, 960, null);
    }

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value, @NotNull String path, int maxAge, boolean secure) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        this(name, value, path, maxAge, secure, 0, false, null, null, null, 992, null);
    }

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value, @NotNull String path, int maxAge) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        this(name, value, path, maxAge, false, 0, false, null, null, null, 1008, null);
    }

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value, @NotNull String path) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(path, "path");
        this(name, value, path, 0, false, 0, false, null, null, null, 1016, null);
    }

    @JvmOverloads
    public Cookie(@NotNull String name, @NotNull String value) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this(name, value, null, 0, false, 0, false, null, null, null, 1020, null);
    }
}

