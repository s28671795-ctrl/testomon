/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.plugin.bundled.GlobalHeaderConfig;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0007$%&'()*B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000b\"\u00020\f\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0017J\u0016\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\tJ\u000e\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010\u001e\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u0005J\u000e\u0010!\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\"J\f\u0010#\u001a\u00020\u0005*\u00020\u0005H\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006+"}, d2={"Lio/javalin/plugin/bundled/GlobalHeaderConfig;", "", "()V", "headers", "", "", "getHeaders", "()Ljava/util/Map;", "clearSiteData", "", "data", "", "Lio/javalin/plugin/bundled/GlobalHeaderConfig$ClearSiteData;", "([Lio/javalin/plugin/bundled/GlobalHeaderConfig$ClearSiteData;)V", "contentSecurityPolicy", "crossOriginEmbedderPolicy", "policy", "Lio/javalin/plugin/bundled/GlobalHeaderConfig$CrossOriginEmbedderPolicy;", "crossOriginOpenerPolicy", "Lio/javalin/plugin/bundled/GlobalHeaderConfig$CrossOriginOpenerPolicy;", "crossOriginResourcePolicy", "Lio/javalin/plugin/bundled/GlobalHeaderConfig$CrossOriginResourcePolicy;", "referrerPolicy", "Lio/javalin/plugin/bundled/GlobalHeaderConfig$ReferrerPolicy;", "strictTransportSecurity", "duration", "Ljava/time/Duration;", "includeSubdomains", "", "xContentTypeOptionsNoSniff", "xFrameOptions", "Lio/javalin/plugin/bundled/GlobalHeaderConfig$XFrameOptions;", "domain", "xPermittedCrossDomainPolicies", "Lio/javalin/plugin/bundled/GlobalHeaderConfig$CrossDomainPolicy;", "toHttpHeaderValue", "ClearSiteData", "CrossDomainPolicy", "CrossOriginEmbedderPolicy", "CrossOriginOpenerPolicy", "CrossOriginResourcePolicy", "ReferrerPolicy", "XFrameOptions", "javalin"})
public final class GlobalHeaderConfig {
    @NotNull
    private final Map<String, String> headers = new LinkedHashMap();

    @NotNull
    public final Map<String, String> getHeaders() {
        return this.headers;
    }

    public final void strictTransportSecurity(@NotNull Duration duration, boolean includeSubdomains) {
        Intrinsics.checkNotNullParameter(duration, "duration");
        this.headers.put("Strict-Transport-Security", "max-age=" + duration.getSeconds() + (includeSubdomains ? " ; includeSubDomains" : ""));
    }

    public final void xFrameOptions(@NotNull XFrameOptions xFrameOptions) {
        Intrinsics.checkNotNullParameter((Object)xFrameOptions, "xFrameOptions");
        this.headers.put("X-Frame-Options", this.toHttpHeaderValue(xFrameOptions.name()));
    }

    public final void xFrameOptions(@NotNull String domain) {
        Intrinsics.checkNotNullParameter(domain, "domain");
        this.headers.put("X-Frame-Options", "allow-from: " + domain);
    }

    public final void xContentTypeOptionsNoSniff() {
        this.headers.put("X-Content-Type-Options", "nosniff");
    }

    public final void contentSecurityPolicy(@NotNull String contentSecurityPolicy) {
        Intrinsics.checkNotNullParameter(contentSecurityPolicy, "contentSecurityPolicy");
        this.headers.put("Content-Security-Policy", contentSecurityPolicy);
    }

    public final void xPermittedCrossDomainPolicies(@NotNull CrossDomainPolicy policy) {
        Intrinsics.checkNotNullParameter((Object)policy, "policy");
        this.headers.put("X-Permitted-Cross-Domain-Policies", this.toHttpHeaderValue(policy.name()));
    }

    public final void referrerPolicy(@NotNull ReferrerPolicy policy) {
        Intrinsics.checkNotNullParameter((Object)policy, "policy");
        this.headers.put("Referrer-Policy", this.toHttpHeaderValue(policy.name()));
    }

    public final void clearSiteData(ClearSiteData ... data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.headers.put("Clear-Site-Data", ArraysKt.joinToString$default((Object[])data, (CharSequence)",", null, null, 0, null, (Function1)clearSiteData.1.INSTANCE, 30, null));
    }

    public final void crossOriginEmbedderPolicy(@NotNull CrossOriginEmbedderPolicy policy) {
        Intrinsics.checkNotNullParameter((Object)policy, "policy");
        this.headers.put("Cross-Origin-Embedder-Policy", this.toHttpHeaderValue(policy.name()));
    }

    public final void crossOriginOpenerPolicy(@NotNull CrossOriginOpenerPolicy policy) {
        Intrinsics.checkNotNullParameter((Object)policy, "policy");
        this.headers.put("Cross-Origin-Opener-Policy", this.toHttpHeaderValue(policy.name()));
    }

    public final void crossOriginResourcePolicy(@NotNull CrossOriginResourcePolicy policy) {
        Intrinsics.checkNotNullParameter((Object)policy, "policy");
        this.headers.put("Cross-Origin-Resource-Policy", this.toHttpHeaderValue(policy.name()));
    }

    private final String toHttpHeaderValue(String $this$toHttpHeaderValue) {
        String string = $this$toHttpHeaderValue;
        Locale locale = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
        String string2 = string.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(locale)");
        return StringsKt.replace$default(string2, "_", "-", false, 4, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2={"Lio/javalin/plugin/bundled/GlobalHeaderConfig$ClearSiteData;", "", "name", "", "headerValue", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getHeaderValue", "()Ljava/lang/String;", "CACHE", "COOKIES", "STORAGE", "EXECUTION_CONTEXTS", "ANY", "javalin"})
    public static final class ClearSiteData
    extends Enum<ClearSiteData> {
        @NotNull
        private final String headerValue;
        public static final /* enum */ ClearSiteData CACHE = new ClearSiteData("CACHE", 0, "cache", null, 2, null);
        public static final /* enum */ ClearSiteData COOKIES = new ClearSiteData("COOKIES", 1, "cookies", null, 2, null);
        public static final /* enum */ ClearSiteData STORAGE = new ClearSiteData("STORAGE", 2, "storage", null, 2, null);
        public static final /* enum */ ClearSiteData EXECUTION_CONTEXTS = new ClearSiteData("EXECUTION_CONTEXTS", 3, "executionContexts", null, 2, null);
        public static final /* enum */ ClearSiteData ANY = new ClearSiteData("ANY", 4, "*", null, 2, null);
        private static final /* synthetic */ ClearSiteData[] $VALUES;

        private ClearSiteData(String name, String headerValue) {
            this.headerValue = headerValue;
        }

        /* synthetic */ ClearSiteData(String string, int n, String string2, String object, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 2) != 0) {
                char c = '\"';
                object = c + string2 + "\"";
            }
            this(string2, (String)object);
        }

        @NotNull
        public final String getHeaderValue() {
            return this.headerValue;
        }

        public static ClearSiteData[] values() {
            return (ClearSiteData[])$VALUES.clone();
        }

        public static ClearSiteData valueOf(String value) {
            return Enum.valueOf(ClearSiteData.class, value);
        }

        static {
            $VALUES = clearSiteDataArray = new ClearSiteData[]{ClearSiteData.CACHE, ClearSiteData.COOKIES, ClearSiteData.STORAGE, ClearSiteData.EXECUTION_CONTEXTS, ClearSiteData.ANY};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lio/javalin/plugin/bundled/GlobalHeaderConfig$CrossDomainPolicy;", "", "(Ljava/lang/String;I)V", "NONE", "MASTER_ONLY", "BY_CONTENT_TYPE", "BY_FTP_FILENAME", "ALL", "javalin"})
    public static final class CrossDomainPolicy
    extends Enum<CrossDomainPolicy> {
        public static final /* enum */ CrossDomainPolicy NONE = new CrossDomainPolicy();
        public static final /* enum */ CrossDomainPolicy MASTER_ONLY = new CrossDomainPolicy();
        public static final /* enum */ CrossDomainPolicy BY_CONTENT_TYPE = new CrossDomainPolicy();
        public static final /* enum */ CrossDomainPolicy BY_FTP_FILENAME = new CrossDomainPolicy();
        public static final /* enum */ CrossDomainPolicy ALL = new CrossDomainPolicy();
        private static final /* synthetic */ CrossDomainPolicy[] $VALUES;

        public static CrossDomainPolicy[] values() {
            return (CrossDomainPolicy[])$VALUES.clone();
        }

        public static CrossDomainPolicy valueOf(String value) {
            return Enum.valueOf(CrossDomainPolicy.class, value);
        }

        static {
            $VALUES = crossDomainPolicyArray = new CrossDomainPolicy[]{CrossDomainPolicy.NONE, CrossDomainPolicy.MASTER_ONLY, CrossDomainPolicy.BY_CONTENT_TYPE, CrossDomainPolicy.BY_FTP_FILENAME, CrossDomainPolicy.ALL};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lio/javalin/plugin/bundled/GlobalHeaderConfig$CrossOriginEmbedderPolicy;", "", "(Ljava/lang/String;I)V", "UNSAFE_NONE", "REQUIRE_CORP", "javalin"})
    public static final class CrossOriginEmbedderPolicy
    extends Enum<CrossOriginEmbedderPolicy> {
        public static final /* enum */ CrossOriginEmbedderPolicy UNSAFE_NONE = new CrossOriginEmbedderPolicy();
        public static final /* enum */ CrossOriginEmbedderPolicy REQUIRE_CORP = new CrossOriginEmbedderPolicy();
        private static final /* synthetic */ CrossOriginEmbedderPolicy[] $VALUES;

        public static CrossOriginEmbedderPolicy[] values() {
            return (CrossOriginEmbedderPolicy[])$VALUES.clone();
        }

        public static CrossOriginEmbedderPolicy valueOf(String value) {
            return Enum.valueOf(CrossOriginEmbedderPolicy.class, value);
        }

        static {
            $VALUES = crossOriginEmbedderPolicyArray = new CrossOriginEmbedderPolicy[]{CrossOriginEmbedderPolicy.UNSAFE_NONE, CrossOriginEmbedderPolicy.REQUIRE_CORP};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lio/javalin/plugin/bundled/GlobalHeaderConfig$CrossOriginOpenerPolicy;", "", "(Ljava/lang/String;I)V", "UNSAFE_NONE", "SAME_ORIGIN_ALLOW_POPUPS", "SAME_ORIGIN", "javalin"})
    public static final class CrossOriginOpenerPolicy
    extends Enum<CrossOriginOpenerPolicy> {
        public static final /* enum */ CrossOriginOpenerPolicy UNSAFE_NONE = new CrossOriginOpenerPolicy();
        public static final /* enum */ CrossOriginOpenerPolicy SAME_ORIGIN_ALLOW_POPUPS = new CrossOriginOpenerPolicy();
        public static final /* enum */ CrossOriginOpenerPolicy SAME_ORIGIN = new CrossOriginOpenerPolicy();
        private static final /* synthetic */ CrossOriginOpenerPolicy[] $VALUES;

        public static CrossOriginOpenerPolicy[] values() {
            return (CrossOriginOpenerPolicy[])$VALUES.clone();
        }

        public static CrossOriginOpenerPolicy valueOf(String value) {
            return Enum.valueOf(CrossOriginOpenerPolicy.class, value);
        }

        static {
            $VALUES = crossOriginOpenerPolicyArray = new CrossOriginOpenerPolicy[]{CrossOriginOpenerPolicy.UNSAFE_NONE, CrossOriginOpenerPolicy.SAME_ORIGIN_ALLOW_POPUPS, CrossOriginOpenerPolicy.SAME_ORIGIN};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lio/javalin/plugin/bundled/GlobalHeaderConfig$CrossOriginResourcePolicy;", "", "(Ljava/lang/String;I)V", "SAME_SITE", "SAME_ORIGIN", "CROSS_ORIGIN", "javalin"})
    public static final class CrossOriginResourcePolicy
    extends Enum<CrossOriginResourcePolicy> {
        public static final /* enum */ CrossOriginResourcePolicy SAME_SITE = new CrossOriginResourcePolicy();
        public static final /* enum */ CrossOriginResourcePolicy SAME_ORIGIN = new CrossOriginResourcePolicy();
        public static final /* enum */ CrossOriginResourcePolicy CROSS_ORIGIN = new CrossOriginResourcePolicy();
        private static final /* synthetic */ CrossOriginResourcePolicy[] $VALUES;

        public static CrossOriginResourcePolicy[] values() {
            return (CrossOriginResourcePolicy[])$VALUES.clone();
        }

        public static CrossOriginResourcePolicy valueOf(String value) {
            return Enum.valueOf(CrossOriginResourcePolicy.class, value);
        }

        static {
            $VALUES = crossOriginResourcePolicyArray = new CrossOriginResourcePolicy[]{CrossOriginResourcePolicy.SAME_SITE, CrossOriginResourcePolicy.SAME_ORIGIN, CrossOriginResourcePolicy.CROSS_ORIGIN};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lio/javalin/plugin/bundled/GlobalHeaderConfig$ReferrerPolicy;", "", "(Ljava/lang/String;I)V", "NO_REFERRER", "NO_REFERRER_WHEN_DOWNGRADE", "ORIGIN", "ORIGIN_WHEN_CROSS_ORIGIN", "SAME_ORIGIN", "STRICT_ORIGIN", "STRICT_ORIGIN_WHEN_CROSS_ORIGIN", "UNSAFE_URL", "javalin"})
    public static final class ReferrerPolicy
    extends Enum<ReferrerPolicy> {
        public static final /* enum */ ReferrerPolicy NO_REFERRER = new ReferrerPolicy();
        public static final /* enum */ ReferrerPolicy NO_REFERRER_WHEN_DOWNGRADE = new ReferrerPolicy();
        public static final /* enum */ ReferrerPolicy ORIGIN = new ReferrerPolicy();
        public static final /* enum */ ReferrerPolicy ORIGIN_WHEN_CROSS_ORIGIN = new ReferrerPolicy();
        public static final /* enum */ ReferrerPolicy SAME_ORIGIN = new ReferrerPolicy();
        public static final /* enum */ ReferrerPolicy STRICT_ORIGIN = new ReferrerPolicy();
        public static final /* enum */ ReferrerPolicy STRICT_ORIGIN_WHEN_CROSS_ORIGIN = new ReferrerPolicy();
        public static final /* enum */ ReferrerPolicy UNSAFE_URL = new ReferrerPolicy();
        private static final /* synthetic */ ReferrerPolicy[] $VALUES;

        public static ReferrerPolicy[] values() {
            return (ReferrerPolicy[])$VALUES.clone();
        }

        public static ReferrerPolicy valueOf(String value) {
            return Enum.valueOf(ReferrerPolicy.class, value);
        }

        static {
            $VALUES = referrerPolicyArray = new ReferrerPolicy[]{ReferrerPolicy.NO_REFERRER, ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE, ReferrerPolicy.ORIGIN, ReferrerPolicy.ORIGIN_WHEN_CROSS_ORIGIN, ReferrerPolicy.SAME_ORIGIN, ReferrerPolicy.STRICT_ORIGIN, ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN, ReferrerPolicy.UNSAFE_URL};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lio/javalin/plugin/bundled/GlobalHeaderConfig$XFrameOptions;", "", "(Ljava/lang/String;I)V", "DENY", "SAMEORIGIN", "javalin"})
    public static final class XFrameOptions
    extends Enum<XFrameOptions> {
        public static final /* enum */ XFrameOptions DENY = new XFrameOptions();
        public static final /* enum */ XFrameOptions SAMEORIGIN = new XFrameOptions();
        private static final /* synthetic */ XFrameOptions[] $VALUES;

        public static XFrameOptions[] values() {
            return (XFrameOptions[])$VALUES.clone();
        }

        public static XFrameOptions valueOf(String value) {
            return Enum.valueOf(XFrameOptions.class, value);
        }

        static {
            $VALUES = xFrameOptionsArray = new XFrameOptions[]{XFrameOptions.DENY, XFrameOptions.SAMEORIGIN};
        }
    }
}

