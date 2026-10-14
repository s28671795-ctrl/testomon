/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.util.CoreDependency;
import io.javalin.util.DependencyUtil;
import io.javalin.util.JavalinLogger;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006JG\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\u0004\b\u0000\u0010\b2\u001e\u0010\t\u001a\u001a\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u00060\fj\u0002`\r0\u000b\u0012\u0004\u0012\u0002H\b0\n2\u0012\u0010\u000e\u001a\u000e\u0012\n\b\u0001\u0012\u00060\fj\u0002`\r0\u000b\u00a2\u0006\u0002\u0010\u000fJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u000e\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0006J\u0014\u0010\u0018\u001a\u00020\u00062\n\u0010\u0019\u001a\u00060\fj\u0002`\rH\u0007J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0006H\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0004H\u0007J\b\u0010\u001e\u001a\u00020\u001cH\u0007J\u0010\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u0006H\u0007J\u0018\u0010!\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007J\b\u0010\"\u001a\u00020\u001cH\u0007J\b\u0010#\u001a\u00020\u0004H\u0002\u00a8\u0006$"}, d2={"Lio/javalin/util/Util;", "", "()V", "classExists", "", "className", "", "findByClass", "T", "map", "", "Ljava/lang/Class;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "exceptionClass", "(Ljava/util/Map;Ljava/lang/Class;)Ljava/lang/Object;", "formatBuildTime", "buildTime", "getChecksumAndReset", "inputStream", "Ljava/io/ByteArrayInputStream;", "getFileUrl", "Ljava/net/URL;", "path", "getPort", "e", "getResourceUrl", "logJavalinBanner", "", "showBanner", "logJavalinVersion", "normalizeContextPath", "contextPath", "prefixContextPath", "printHelpfulMessageIfLoggerIsMissing", "slf4jServiceImplementationExists", "javalin"})
@SourceDebugExtension(value={"SMAP\nUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Util.kt\nio/javalin/util/Util\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,130:1\n616#2,6:131\n*S KotlinDebug\n*F\n+ 1 Util.kt\nio/javalin/util/Util\n*L\n116#1:131,6\n*E\n"})
public final class Util {
    @NotNull
    public static final Util INSTANCE = new Util();

    private Util() {
    }

    @JvmStatic
    @NotNull
    public static final String normalizeContextPath(@NotNull String contextPath) {
        String string;
        Intrinsics.checkNotNullParameter(contextPath, "contextPath");
        if (Intrinsics.areEqual(contextPath, "/")) {
            string = "/";
        } else {
            CharSequence charSequence = "/" + contextPath;
            Regex regex2 = new Regex("/{2,}");
            String string2 = "/";
            string = StringsKt.removeSuffix(regex2.replace(charSequence, string2), (CharSequence)"/");
        }
        return string;
    }

    @JvmStatic
    @NotNull
    public static final String prefixContextPath(@NotNull String contextPath, @NotNull String path) {
        String string;
        Intrinsics.checkNotNullParameter(contextPath, "contextPath");
        Intrinsics.checkNotNullParameter(path, "path");
        if (Intrinsics.areEqual(path, "*")) {
            string = path;
        } else {
            CharSequence charSequence = contextPath + "/" + path;
            Regex regex2 = new Regex("/{2,}");
            String string2 = "/";
            string = regex2.replace(charSequence, string2);
        }
        return string;
    }

    public final boolean classExists(@NotNull String className) {
        boolean bl;
        Intrinsics.checkNotNullParameter(className, "className");
        try {
            Class.forName(className);
            bl = true;
        }
        catch (ClassNotFoundException e) {
            bl = false;
        }
        return bl;
    }

    private final boolean slf4jServiceImplementationExists() {
        boolean bl;
        try {
            Class<?> serviceClass = Class.forName("org.slf4j.spi.SLF4JServiceProvider");
            ServiceLoader<?> loader = ServiceLoader.load(serviceClass);
            Intrinsics.checkNotNullExpressionValue(loader, "loader");
            bl = CollectionsKt.any((Iterable)loader);
        }
        catch (ClassNotFoundException e) {
            bl = false;
        }
        return bl;
    }

    @JvmStatic
    public static final void printHelpfulMessageIfLoggerIsMissing() {
        boolean hasLogger;
        boolean bl = hasLogger = INSTANCE.classExists(CoreDependency.SLF4JSIMPLE.getTestClass()) || INSTANCE.slf4jServiceImplementationExists();
        if (!hasLogger) {
            System.err.println(DependencyUtil.INSTANCE.wrapInSeparators(StringsKt.trimMargin$default("|Javalin: It looks like you don't have a logger in your project.\n                       |The easiest way to fix this is to add '" + CoreDependency.SLF4JSIMPLE.getArtifactId() + "':\n                       |\n                       |" + DependencyUtil.INSTANCE.mavenAndGradleSnippets(CoreDependency.SLF4JSIMPLE) + "\n                       |\n                       |Visit https://javalin.io/documentation#logging if you need more help", null, 1, null)));
        }
    }

    @JvmStatic
    public static final void logJavalinBanner(boolean showBanner) {
        if (showBanner) {
            JavalinLogger.info$default("\n       __                  ___          ______\n      / /___ __   ______ _/ (_)___     / ____/\n __  / / __ `/ | / / __ `/ / / __ \\   /___ \\\n/ /_/ / /_/ /| |/ / /_/ / / / / / /  ____/ /\n\\____/\\__,_/ |___/\\__,_/_/_/_/ /_/  /_____/\n\n       https://javalin.io/documentation\n", null, 2, null);
        }
    }

    @JvmStatic
    public static final void logJavalinVersion() {
        try {
            Object object = new Properties();
            String[] it = object;
            boolean bl = false;
            String propertiesPath = "META-INF/maven/io.javalin/javalin/pom.properties";
            it.load(INSTANCE.getClass().getClassLoader().getResourceAsStream(propertiesPath));
            String[] properties = object;
            it = new String[2];
            Intrinsics.checkNotNull(properties.getProperty("version"));
            Intrinsics.checkNotNull(properties.getProperty("buildTime"));
            object = CollectionsKt.listOf(it);
            String version = (String)object.get(0);
            String buildTime = (String)object.get(1);
            JavalinLogger.startup("You are running Javalin " + version + " (released " + INSTANCE.formatBuildTime(buildTime) + ").");
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private final String formatBuildTime(String buildTime) {
        Object object;
        try {
            Instant[] instantArray = new Instant[]{Instant.parse(buildTime), Instant.now()};
            object = CollectionsKt.listOf(instantArray);
            Instant release = object.get(0);
            Instant now = object.get(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy").withLocale(Locale.US).withZone(ZoneId.of("Z"));
            object = formatter.format(release) + (String)(now.isAfter(release.plus(90L, ChronoUnit.DAYS)) ? ". Your Javalin version is " + ChronoUnit.DAYS.between(release, now) + " days old. Consider checking for a newer version." : "");
        }
        catch (Exception e) {
            object = null;
        }
        return object;
    }

    @NotNull
    public final String getChecksumAndReset(@NotNull ByteArrayInputStream inputStream2) {
        Intrinsics.checkNotNullParameter(inputStream2, "inputStream");
        CheckedInputStream cis = new CheckedInputStream(inputStream2, new Adler32());
        int n = cis.read();
        while (n > -1) {
            n = cis.read();
        }
        inputStream2.reset();
        return String.valueOf(cis.getChecksum().getValue());
    }

    @JvmStatic
    @Nullable
    public static final URL getResourceUrl(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return INSTANCE.getClass().getClassLoader().getResource(path);
    }

    @Nullable
    public final URL getFileUrl(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new File(path).exists() ? new File(path).toURI().toURL() : null;
    }

    /*
     * WARNING - void declaration
     */
    @JvmStatic
    @NotNull
    public static final String getPort(@NotNull Exception e) {
        String string;
        block1: {
            void var1_1;
            Intrinsics.checkNotNullParameter(e, "e");
            String string2 = e.getMessage();
            Intrinsics.checkNotNull(string2);
            String $this$takeLastWhile$iv = string2;
            boolean $i$f$takeLastWhile = false;
            for (int index$iv = StringsKt.getLastIndex($this$takeLastWhile$iv); -1 < index$iv; --index$iv) {
                char it = $this$takeLastWhile$iv.charAt(index$iv);
                boolean bl = false;
                if (it != ':') continue;
                String string3 = $this$takeLastWhile$iv;
                int n = index$iv + 1;
                String string4 = string3.substring(n);
                string = string4;
                Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).substring(startIndex)");
                break block1;
            }
            string = var1_1;
        }
        return string;
    }

    @Nullable
    public final <T> T findByClass(@NotNull Map<Class<? extends Exception>, ? extends T> map, @NotNull Class<? extends Exception> exceptionClass) {
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(exceptionClass, "exceptionClass");
        T t2 = map.get(exceptionClass);
        if (t2 == null) {
            boolean bl = false;
            for (Class<? extends Exception> superclass = exceptionClass.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
                if (!map.containsKey(superclass)) continue;
                return map.get(superclass);
            }
            return null;
        }
        return t2;
    }
}

