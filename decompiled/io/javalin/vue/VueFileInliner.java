/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.vue;

import io.javalin.util.ConcurrencyUtilKt;
import io.javalin.vue.VueFileInliner;
import io.javalin.vue.VueHandlerKt;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f\u00b2\u0006\u0012\u0010\u0010\u001a\n \u0011*\u0004\u0018\u00010\t0\tX\u008a\u0084\u0002"}, d2={"Lio/javalin/vue/VueFileInliner;", "", "()V", "devRegex", "Lkotlin/text/Regex;", "newlineRegex", "notDevRegex", "unconditionalRegex", "inlineFiles", "", "isDev", "", "nonVueFiles", "", "Ljava/nio/file/Path;", "javalin", "matchingFileContent", "kotlin.jvm.PlatformType"})
@SourceDebugExtension(value={"SMAP\nVueFileInliner.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VueFileInliner.kt\nio/javalin/vue/VueFileInliner\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,28:1\n1194#2,2:29\n1222#2,4:31\n*S KotlinDebug\n*F\n+ 1 VueFileInliner.kt\nio/javalin/vue/VueFileInliner\n*L\n15#1:29,2\n15#1:31,4\n*E\n"})
public final class VueFileInliner {
    @NotNull
    public static final VueFileInliner INSTANCE = new VueFileInliner();
    @NotNull
    private static final Regex newlineRegex = new Regex("\\r?\\n");
    @NotNull
    private static final Regex unconditionalRegex = new Regex("@inlineFile\\(\".*\"\\)");
    @NotNull
    private static final Regex devRegex = new Regex("@inlineFileDev\\(\".*\"\\)");
    @NotNull
    private static final Regex notDevRegex = new Regex("@inlineFileNotDev\\(\".*\"\\)");

    private VueFileInliner() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final String inlineFiles(@NotNull String $this$inlineFiles, boolean isDev, @NotNull List<? extends Path> nonVueFiles) {
        void $this$associateByTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$inlineFiles, "<this>");
        Intrinsics.checkNotNullParameter(nonVueFiles, "nonVueFiles");
        Iterable $this$associateBy$iv = nonVueFiles;
        boolean $i$f$associateBy = false;
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
        Iterable iterable = $this$associateBy$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv : $this$associateByTo$iv$iv) {
            void it;
            Path path = (Path)element$iv$iv;
            Map map = destination$iv$iv;
            boolean bl = false;
            map.put("\"/vue/" + StringsKt.substringAfter$default(StringsKt.replace$default(it.toString(), "\\", "/", false, 4, null), "/vue/", null, 2, null) + "\"", element$iv$iv);
        }
        Map pathMap = destination$iv$iv;
        CharSequence charSequence = $this$inlineFiles;
        Regex regex2 = newlineRegex;
        int n = 0;
        return CollectionsKt.joinToString$default(regex2.split(charSequence, n), "\n", null, null, 0, null, new Function1<String, CharSequence>((Map<String, ? extends Path>)pathMap, isDev){
            final /* synthetic */ Map<String, Path> $pathMap;
            final /* synthetic */ boolean $isDev;
            {
                this.$pathMap = $pathMap;
                this.$isDev = $isDev;
                super(1);
            }

            @NotNull
            public final CharSequence invoke(@NotNull String line) {
                CharSequence charSequence;
                Object v0;
                Object object;
                Object object2;
                block11: {
                    Intrinsics.checkNotNullParameter(line, "line");
                    if (!StringsKt.contains$default((CharSequence)line, "@inlineFile", false, 2, null)) {
                        return line;
                    }
                    object2 = this.$pathMap.keySet();
                    object = object2.iterator();
                    while (object.hasNext()) {
                        T t2 = object.next();
                        String it = (String)t2;
                        boolean bl = false;
                        if (!StringsKt.contains$default((CharSequence)line, it, false, 2, null)) continue;
                        v0 = t2;
                        break block11;
                    }
                    v0 = null;
                }
                String string = v0;
                if (string == null) {
                    throw new IllegalStateException("Invalid path found: " + line);
                }
                String matchingKey = string;
                Lazy matchingFileContent$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<String>(this.$pathMap, matchingKey){
                    final /* synthetic */ Map<String, Path> $pathMap;
                    final /* synthetic */ String $matchingKey;
                    {
                        this.$pathMap = $pathMap;
                        this.$matchingKey = $matchingKey;
                        super(0);
                    }

                    public final String invoke() {
                        Path path = this.$pathMap.get(this.$matchingKey);
                        Intrinsics.checkNotNull(path);
                        return Matcher.quoteReplacement(VueHandlerKt.readText(path));
                    }
                }, 1, null);
                if (VueFileInliner.access$getDevRegex$p().containsMatchIn(line)) {
                    String string2;
                    if (this.$isDev) {
                        CharSequence charSequence2 = line;
                        object2 = VueFileInliner.access$getDevRegex$p();
                        String string3 = inlineFiles.1.invoke$lambda$1(matchingFileContent$delegate);
                        Intrinsics.checkNotNullExpressionValue(string3, "matchingFileContent");
                        object = string3;
                        string2 = ((Regex)object2).replace(charSequence2, (String)object);
                    } else {
                        string2 = "";
                    }
                    charSequence = string2;
                } else if (VueFileInliner.access$getNotDevRegex$p().containsMatchIn(line)) {
                    String string4;
                    if (!this.$isDev) {
                        CharSequence charSequence3 = line;
                        object2 = VueFileInliner.access$getNotDevRegex$p();
                        String string5 = inlineFiles.1.invoke$lambda$1(matchingFileContent$delegate);
                        Intrinsics.checkNotNullExpressionValue(string5, "matchingFileContent");
                        object = string5;
                        string4 = ((Regex)object2).replace(charSequence3, (String)object);
                    } else {
                        string4 = "";
                    }
                    charSequence = string4;
                } else {
                    CharSequence charSequence4 = line;
                    object2 = VueFileInliner.access$getUnconditionalRegex$p();
                    String string6 = inlineFiles.1.invoke$lambda$1(matchingFileContent$delegate);
                    Intrinsics.checkNotNullExpressionValue(string6, "matchingFileContent");
                    object = string6;
                    charSequence = ((Regex)object2).replace(charSequence4, (String)object);
                }
                return charSequence;
            }

            private static final String invoke$lambda$1(Lazy<String> $matchingFileContent$delegate) {
                Lazy<String> lazy = $matchingFileContent$delegate;
                return lazy.getValue();
            }
        }, 30, null);
    }

    public static final /* synthetic */ Regex access$getDevRegex$p() {
        return devRegex;
    }

    public static final /* synthetic */ Regex access$getNotDevRegex$p() {
        return notDevRegex;
    }

    public static final /* synthetic */ Regex access$getUnconditionalRegex$p() {
        return unconditionalRegex;
    }
}

