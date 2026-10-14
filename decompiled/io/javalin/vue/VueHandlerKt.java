/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.http.Context;
import io.javalin.vue.VueHandlerKt;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\"\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\f\u0010\u0006\u001a\u00020\u0003*\u00020\u0007H\u0000\u001a\u0012\u0010\b\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00070\tH\u0002\u001a\f\u0010\n\u001a\u00020\u0001*\u00020\u0007H\u0000\u00a8\u0006\u000b"}, d2={"insertNoncesAndCspHeader", "", "enableCspAndNonces", "", "ctx", "Lio/javalin/http/Context;", "isVueFile", "Ljava/nio/file/Path;", "joinVueFiles", "", "readText", "javalin"})
@SourceDebugExtension(value={"SMAP\nVueHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VueHandler.kt\nio/javalin/vue/VueHandlerKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n1#2:62\n766#3:63\n857#3,2:64\n*S KotlinDebug\n*F\n+ 1 VueHandler.kt\nio/javalin/vue/VueHandlerKt\n*L\n60#1:63\n60#1:64,2\n*E\n"})
public final class VueHandlerKt {
    private static final String insertNoncesAndCspHeader(String $this$insertNoncesAndCspHeader, boolean enableCspAndNonces, Context ctx) {
        if (!enableCspAndNonces) {
            return StringsKt.replace$default($this$insertNoncesAndCspHeader, "nonce=\"@internalAddNonce\"", "", false, 4, null);
        }
        Set nonces = new LinkedHashSet();
        CharSequence charSequence = $this$insertNoncesAndCspHeader;
        Regex regex2 = new Regex("@internalAddNonce");
        Function1 function1 = new Function1<MatchResult, CharSequence>((Set<String>)nonces){
            final /* synthetic */ Set<String> $nonces;
            {
                this.$nonces = $nonces;
                super(1);
            }

            @NotNull
            public final CharSequence invoke(@NotNull MatchResult it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return VueHandlerKt.access$insertNoncesAndCspHeader$newNonce(this.$nonces);
            }
        };
        charSequence = regex2.replace(charSequence, function1);
        regex2 = new Regex("@addNonce");
        function1 = new Function1<MatchResult, CharSequence>((Set<String>)nonces){
            final /* synthetic */ Set<String> $nonces;
            {
                this.$nonces = $nonces;
                super(1);
            }

            @NotNull
            public final CharSequence invoke(@NotNull MatchResult it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return VueHandlerKt.access$insertNoncesAndCspHeader$newNonce(this.$nonces);
            }
        };
        CharSequence it = charSequence = regex2.replace(charSequence, function1);
        boolean bl = false;
        ctx.header("Content-Security-Policy", "script-src 'unsafe-eval' " + CollectionsKt.joinToString$default(nonces, " ", null, null, 0, null, insertNoncesAndCspHeader.3.1.INSTANCE, 30, null));
        return charSequence;
    }

    @NotNull
    public static final String readText(@NotNull Path $this$readText) {
        Intrinsics.checkNotNullParameter($this$readText, "<this>");
        byte[] byArray = Files.readAllBytes($this$readText);
        Intrinsics.checkNotNullExpressionValue(byArray, "readAllBytes(this)");
        byte[] byArray2 = byArray;
        return new String(byArray2, Charsets.UTF_8);
    }

    public static final boolean isVueFile(@NotNull Path $this$isVueFile) {
        Intrinsics.checkNotNullParameter($this$isVueFile, "<this>");
        return StringsKt.endsWith$default(((Object)$this$isVueFile).toString(), ".vue", false, 2, null);
    }

    /*
     * WARNING - void declaration
     */
    private static final String joinVueFiles(Set<? extends Path> $this$joinVueFiles) {
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv = $this$joinVueFiles;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Path it = (Path)element$iv$iv;
            boolean bl = false;
            if (!VueHandlerKt.isVueFile(it)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return CollectionsKt.joinToString$default((List)destination$iv$iv, "", null, null, 0, null, joinVueFiles.2.INSTANCE, 30, null);
    }

    private static final String insertNoncesAndCspHeader$newNonce(Set<String> $this$insertNoncesAndCspHeader_u24newNonce) {
        String string;
        String string2 = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string2, "randomUUID().toString()");
        String it = string = "jv-" + StringsKt.replace$default(string2, "-", "", false, 4, null);
        boolean bl = false;
        $this$insertNoncesAndCspHeader_u24newNonce.add(it);
        return string;
    }

    public static final /* synthetic */ String access$joinVueFiles(Set $receiver) {
        return VueHandlerKt.joinVueFiles($receiver);
    }

    public static final /* synthetic */ String access$insertNoncesAndCspHeader(String $receiver, boolean enableCspAndNonces, Context ctx) {
        return VueHandlerKt.insertNoncesAndCspHeader($receiver, enableCspAndNonces, ctx);
    }

    public static final /* synthetic */ String access$insertNoncesAndCspHeader$newNonce(Set $receiver) {
        return VueHandlerKt.insertNoncesAndCspHeader$newNonce($receiver);
    }
}

