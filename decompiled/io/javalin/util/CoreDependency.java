/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.util.OptionalDependency;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B/\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0006\u0010\b\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\tR\u0014\u0010\u0007\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0014\u0010\u0006\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0014\u0010\b\u001a\u00020\u0004X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018\u00a8\u0006\u0019"}, d2={"Lio/javalin/util/CoreDependency;", "", "Lio/javalin/util/OptionalDependency;", "displayName", "", "testClass", "groupId", "artifactId", "version", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getArtifactId", "()Ljava/lang/String;", "getDisplayName", "getGroupId", "getTestClass", "getVersion", "JACKSON", "JACKSON_KT", "JACKSON_JSR_310", "JACKSON_ECLIPSE_COLLECTIONS", "JACKSON_KTORM", "GSON", "SLF4JSIMPLE", "JVMBROTLI", "BROTLI4J", "javalin"})
public final class CoreDependency
extends Enum<CoreDependency>
implements OptionalDependency {
    @NotNull
    private final String displayName;
    @NotNull
    private final String testClass;
    @NotNull
    private final String groupId;
    @NotNull
    private final String artifactId;
    @NotNull
    private final String version;
    public static final /* enum */ CoreDependency JACKSON = new CoreDependency("Jackson", "com.fasterxml.jackson.databind.ObjectMapper", "com.fasterxml.jackson.core", "jackson-databind", "2.15.0");
    public static final /* enum */ CoreDependency JACKSON_KT = new CoreDependency("JacksonKt", "com.fasterxml.jackson.module.kotlin.KotlinModule", "com.fasterxml.jackson.module", "jackson-module-kotlin", "2.15.0");
    public static final /* enum */ CoreDependency JACKSON_JSR_310 = new CoreDependency("JacksonJsr310", "com.fasterxml.jackson.datatype.jsr310.JavaTimeModule", "com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", "2.15.0");
    public static final /* enum */ CoreDependency JACKSON_ECLIPSE_COLLECTIONS = new CoreDependency("JacksonEclipseCollections", "com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule", "com.fasterxml.jackson.datatype", "jackson-datatype-eclipse-collections", "2.15.0");
    public static final /* enum */ CoreDependency JACKSON_KTORM = new CoreDependency("Jackson Ktorm", "org.ktorm.jackson.KtormModule", "org.ktorm", "ktorm-jackson", "3.4.1");
    public static final /* enum */ CoreDependency GSON = new CoreDependency("Gson", "com.google.gson.Gson", "com.google.code.gson", "gson", "2.10");
    public static final /* enum */ CoreDependency SLF4JSIMPLE = new CoreDependency("Slf4j simple", "org.slf4j.impl.StaticLoggerBinder", "org.slf4j", "slf4j-simple", "2.0.7");
    public static final /* enum */ CoreDependency JVMBROTLI = new CoreDependency("Jvm-Brotli", "com.nixxcode.jvmbrotli.common.BrotliLoader", "com.nixxcode.jvmbrotli", "jvmbrotli", "0.2.0");
    public static final /* enum */ CoreDependency BROTLI4J = new CoreDependency("Brotli4j", "com.aayushatharva.brotli4j.Brotli4jLoader", "com.aayushatharva.brotli4j", "brotli4j", "1.11.0");
    private static final /* synthetic */ CoreDependency[] $VALUES;

    private CoreDependency(String displayName, String testClass, String groupId, String artifactId, String version) {
        this.displayName = displayName;
        this.testClass = testClass;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    @Override
    @NotNull
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    @NotNull
    public String getTestClass() {
        return this.testClass;
    }

    @Override
    @NotNull
    public String getGroupId() {
        return this.groupId;
    }

    @Override
    @NotNull
    public String getArtifactId() {
        return this.artifactId;
    }

    @Override
    @NotNull
    public String getVersion() {
        return this.version;
    }

    public static CoreDependency[] values() {
        return (CoreDependency[])$VALUES.clone();
    }

    public static CoreDependency valueOf(String value) {
        return Enum.valueOf(CoreDependency.class, value);
    }

    static {
        $VALUES = coreDependencyArray = new CoreDependency[]{CoreDependency.JACKSON, CoreDependency.JACKSON_KT, CoreDependency.JACKSON_JSR_310, CoreDependency.JACKSON_ECLIPSE_COLLECTIONS, CoreDependency.JACKSON_KTORM, CoreDependency.GSON, CoreDependency.SLF4JSIMPLE, CoreDependency.JVMBROTLI, CoreDependency.BROTLI4J};
    }
}

