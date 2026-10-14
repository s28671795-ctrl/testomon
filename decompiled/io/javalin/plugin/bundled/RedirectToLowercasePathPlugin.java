/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.Javalin;
import io.javalin.event.EventListener;
import io.javalin.event.HandlerMetaInfo;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import io.javalin.plugin.Plugin;
import io.javalin.plugin.PluginLifecycleInit;
import io.javalin.plugin.bundled.RedirectToLowercasePathPlugin;
import io.javalin.plugin.bundled.RedirectToLowercasePathPlugin$init$lambda$5$lambda$4$;
import io.javalin.routing.HandlerEntry;
import io.javalin.routing.PathMatcher;
import io.javalin.routing.PathParser;
import io.javalin.routing.PathSegment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\t"}, d2={"Lio/javalin/plugin/bundled/RedirectToLowercasePathPlugin;", "Lio/javalin/plugin/Plugin;", "Lio/javalin/plugin/PluginLifecycleInit;", "()V", "apply", "", "app", "Lio/javalin/Javalin;", "init", "javalin"})
@SourceDebugExtension(value={"SMAP\nRedirectToLowercasePathPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RedirectToLowercasePathPlugin.kt\nio/javalin/plugin/bundled/RedirectToLowercasePathPlugin\n+ 2 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,100:1\n473#2:101\n179#2,2:102\n473#2:105\n473#2:106\n179#2,2:107\n1#3:104\n766#4:109\n857#4,2:110\n1864#4,2:114\n800#4,11:116\n1855#4,2:127\n1866#4:129\n37#5,2:112\n*S KotlinDebug\n*F\n+ 1 RedirectToLowercasePathPlugin.kt\nio/javalin/plugin/bundled/RedirectToLowercasePathPlugin\n*L\n35#1:101\n37#1:102,2\n41#1:105\n43#1:106\n45#1:107,2\n65#1:109\n65#1:110,2\n71#1:114,2\n80#1:116,11\n81#1:127,2\n71#1:129\n66#1:112,2\n*E\n"})
public final class RedirectToLowercasePathPlugin
implements Plugin,
PluginLifecycleInit {
    @Override
    public void init(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        if (app.cfg.routing.caseInsensitiveRoutes) {
            throw new IllegalStateException("RedirectToLowercasePathPlugin is not compatible with caseInsensitiveRoutes");
        }
        app.events(arg_0 -> RedirectToLowercasePathPlugin.init$lambda$5(app, arg_0));
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.before(arg_0 -> RedirectToLowercasePathPlugin.apply$lambda$10(app, arg_0));
    }

    private static final void init$lambda$5$lambda$4(Javalin $app, HandlerMetaInfo handlerMetaInfo) {
        Object v10;
        String string;
        block5: {
            Object v4;
            String string2;
            String it;
            Object element$iv;
            Sequence $this$firstOrNull$iv;
            boolean $i$f$firstOrNull;
            Sequence $this$filterIsInstance$iv;
            boolean $i$f$filterIsInstance;
            PathParser parser;
            block4: {
                Intrinsics.checkNotNullParameter($app, "$app");
                parser = new PathParser(handlerMetaInfo.getPath(), $app.cfg.routing);
                Sequence sequence2 = CollectionsKt.asSequence((Iterable)parser.getSegments());
                $i$f$filterIsInstance = false;
                Sequence sequence3 = SequencesKt.filter($this$filterIsInstance$iv, init$lambda$5$lambda$4$$inlined$filterIsInstance$1.INSTANCE);
                Intrinsics.checkNotNull(sequence3, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
                $this$filterIsInstance$iv = SequencesKt.map(sequence3, init.1.1.1.INSTANCE);
                $i$f$firstOrNull = false;
                Iterator iterator2 = $this$firstOrNull$iv.iterator();
                while (iterator2.hasNext()) {
                    element$iv = iterator2.next();
                    it = (String)element$iv;
                    boolean bl = false;
                    string2 = it;
                    Locale locale = Locale.ROOT;
                    Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
                    String string3 = string2.toLowerCase(locale);
                    Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(locale)");
                    boolean bl2 = !Intrinsics.areEqual(it, string3);
                    if (!bl2) continue;
                    v4 = element$iv;
                    break block4;
                }
                v4 = null;
            }
            string = v4;
            if (string != null) {
                String $this$init_u24lambda_u245_u24lambda_u244_u24lambda_u241 = string;
                boolean bl = false;
                throw new IllegalArgumentException("Paths must be lowercase when using RedirectToLowercasePathPlugin");
            }
            $this$firstOrNull$iv = CollectionsKt.asSequence((Iterable)parser.getSegments());
            $i$f$filterIsInstance = false;
            Sequence sequence4 = SequencesKt.filter($this$filterIsInstance$iv, init$lambda$5$lambda$4$$inlined$filterIsInstance$2.INSTANCE);
            Intrinsics.checkNotNull(sequence4, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            $this$filterIsInstance$iv = SequencesKt.flatMapIterable(sequence4, init.1.1.4.INSTANCE);
            $i$f$filterIsInstance = false;
            Sequence sequence5 = SequencesKt.filter($this$filterIsInstance$iv, init$lambda$5$lambda$4$$inlined$filterIsInstance$3.INSTANCE);
            Intrinsics.checkNotNull(sequence5, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            $this$filterIsInstance$iv = SequencesKt.map(sequence5, init.1.1.5.INSTANCE);
            $i$f$firstOrNull = false;
            Iterator bl = $this$firstOrNull$iv.iterator();
            while (bl.hasNext()) {
                element$iv = bl.next();
                it = (String)element$iv;
                boolean bl3 = false;
                string2 = it;
                Locale locale = Locale.ROOT;
                Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
                String string4 = string2.toLowerCase(locale);
                Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).toLowerCase(locale)");
                boolean bl4 = !Intrinsics.areEqual(it, string4);
                if (!bl4) continue;
                v10 = element$iv;
                break block5;
            }
            v10 = null;
        }
        string = v10;
        if (string != null) {
            String $this$init_u24lambda_u245_u24lambda_u244_u24lambda_u243 = string;
            boolean bl = false;
            throw new IllegalArgumentException("Paths must be lowercase when using RedirectToLowercasePathPlugin");
        }
    }

    private static final void init$lambda$5(Javalin $app, EventListener listener) {
        Intrinsics.checkNotNullParameter($app, "$app");
        listener.handlerAdded(arg_0 -> RedirectToLowercasePathPlugin.init$lambda$5$lambda$4($app, arg_0));
    }

    /*
     * WARNING - void declaration
     */
    private static final void apply$lambda$10(Javalin $app, Context ctx) {
        Object object;
        String string;
        Context context;
        block12: {
            block11: {
                void it;
                void $this$toTypedArray$iv;
                Object it2;
                void $this$filterTo$iv$iv;
                Collection $this$filter$iv;
                Intrinsics.checkNotNullParameter($app, "$app");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                String[] requestUri = StringsKt.removePrefix(ctx.path(), (CharSequence)ctx.contextPath());
                PathMatcher matcher = $app.javalinServlet().getMatcher();
                if (CollectionsKt.firstOrNull(matcher.findEntries(ctx.method(), (String)requestUri)) != null) {
                    return;
                }
                HandlerType handlerType = ctx.method();
                Object object2 = requestUri;
                Locale locale = Locale.ROOT;
                Intrinsics.checkNotNullExpressionValue(locale, "ROOT");
                String string2 = object2.toLowerCase(locale);
                Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(locale)");
                HandlerEntry handlerEntry = CollectionsKt.firstOrNull(matcher.findEntries(handlerType, string2));
                if (handlerEntry == null) {
                    return;
                }
                HandlerEntry lowercaseRoute = handlerEntry;
                object2 = new String[]{"/"};
                object2 = StringsKt.split$default((CharSequence)requestUri, object2, false, 0, 6, null);
                boolean $i$f$filter = false;
                void var8_8 = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo22 = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv) {
                    it2 = (String)element$iv$iv;
                    boolean bl = false;
                    boolean bl2 = ((CharSequence)it2).length() > 0;
                    if (!bl2) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = (List)destination$iv$iv;
                boolean $i$f$toTypedArray = false;
                void thisCollection$iv = $this$toTypedArray$iv;
                Object[] clientSegments = thisCollection$iv.toArray(new String[0]);
                List<PathSegment> serverSegments = new PathParser(lowercaseRoute.getPath(), $app.cfg.routing).getSegments();
                Iterable $this$forEachIndexed$iv = serverSegments;
                boolean $i$f$forEachIndexed = false;
                int index$iv = 0;
                for (Object item$iv : $this$forEachIndexed$iv) {
                    void $this$forEach$iv;
                    void $this$filterIsInstanceTo$iv$iv;
                    Iterable $this$filterIsInstance$iv;
                    Object object3;
                    void serverSegment;
                    int n;
                    if ((n = index$iv++) < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    it2 = (PathSegment)item$iv;
                    int index = n;
                    boolean bl = false;
                    if (serverSegment instanceof PathSegment.Normal) {
                        object3 = clientSegments[index];
                        Locale locale2 = Locale.ROOT;
                        Intrinsics.checkNotNullExpressionValue(locale2, "ROOT");
                        Intrinsics.checkNotNullExpressionValue(((String)object3).toLowerCase(locale2), "this as java.lang.String).toLowerCase(locale)");
                    }
                    if (!(serverSegment instanceof PathSegment.MultipleSegments)) continue;
                    object3 = ((PathSegment.MultipleSegments)serverSegment).getInnerSegments();
                    boolean $i$f$filterIsInstance = false;
                    Iterator iterator2 = $this$filterIsInstance$iv;
                    Collection destination$iv$iv2 = new ArrayList();
                    boolean $i$f$filterIsInstanceTo = false;
                    for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                        if (!(element$iv$iv instanceof PathSegment.Normal)) continue;
                        destination$iv$iv2.add(element$iv$iv);
                    }
                    $this$filterIsInstance$iv = (List)destination$iv$iv2;
                    boolean $i$f$forEach = false;
                    for (Object element$iv : $this$forEach$iv) {
                        String string3;
                        PathSegment.Normal innerServerSegment = (PathSegment.Normal)element$iv;
                        boolean bl3 = false;
                        Object object4 = clientSegments[index];
                        String string4 = string3 = innerServerSegment.getContent();
                        Locale locale3 = Locale.ROOT;
                        Intrinsics.checkNotNullExpressionValue(locale3, "ROOT");
                        String string5 = string3.toLowerCase(locale3);
                        Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String).toLowerCase(locale)");
                        clientSegments[index] = StringsKt.replace((String)object4, string4, string5, true);
                    }
                }
                context = ctx;
                string = ArraysKt.joinToString$default(clientSegments, (CharSequence)"/", null, null, 0, null, null, 62, null);
                object = ctx.queryString();
                if (object == null) break block11;
                Object $i$f$filterTo22 = object;
                String string6 = string;
                Context context2 = context;
                boolean bl = false;
                String string7 = "?" + (String)it;
                context = context2;
                string = string6;
                String string8 = string7;
                object = string8;
                if (string8 != null) break block12;
            }
            object = "";
        }
        context.redirect("/" + string + (String)object, HttpStatus.MOVED_PERMANENTLY);
    }
}

