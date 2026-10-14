/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.util.OptionalDependency;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004\u00a8\u0006\n"}, d2={"Lio/javalin/util/DependencyUtil;", "", "()V", "mavenAndGradleSnippets", "", "dependency", "Lio/javalin/util/OptionalDependency;", "missingDependencyMessage", "wrapInSeparators", "msg", "javalin"})
public final class DependencyUtil {
    @NotNull
    public static final DependencyUtil INSTANCE = new DependencyUtil();

    private DependencyUtil() {
    }

    @NotNull
    public final String missingDependencyMessage(@NotNull OptionalDependency dependency) {
        Intrinsics.checkNotNullParameter(dependency, "dependency");
        return this.wrapInSeparators(StringsKt.trimMargin$default("|You're missing the '" + dependency.getDisplayName() + "' dependency in your project. Add the dependency:\n           |\n           |" + this.mavenAndGradleSnippets(dependency), null, 1, null));
    }

    @NotNull
    public final String mavenAndGradleSnippets(@NotNull OptionalDependency dependency) {
        Intrinsics.checkNotNullParameter(dependency, "dependency");
        return StringsKt.trimMargin$default("\n           |pom.xml:\n           |<dependency>\n           |    <groupId>" + dependency.getGroupId() + "</groupId>\n           |    <artifactId>" + dependency.getArtifactId() + "</artifactId>\n           |    <version>" + dependency.getVersion() + "</version>\n           |</dependency>\n           |\n           |build.gradle or build.gradle.kts:\n           |implementation(\"" + dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + dependency.getVersion() + "\")", null, 1, null);
    }

    @NotNull
    public final String wrapInSeparators(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        return StringsKt.trimMargin$default("\n        |\n        |#########################################################################\n        |" + msg + "\n        |#########################################################################", null, 1, null);
    }
}

