/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.event.HandlerMetaInfo;
import io.javalin.event.WsHandlerMetaInfo;
import io.javalin.http.HandlerType;
import io.javalin.security.RouteRole;
import io.javalin.util.ReflectionUtilKt;
import io.javalin.websocket.WsHandlerType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\t\u001a\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bH\u0007J$\u0010\u000f\u001a\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bH\u0007R\u001e\u0010\u0003\u001a\u00020\u0004*\u00020\u00018FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0010"}, d2={"Lio/javalin/plugin/bundled/RouteOverviewUtil;", "", "()V", "metaInfo", "", "getMetaInfo$annotations", "(Ljava/lang/Object;)V", "getMetaInfo", "(Ljava/lang/Object;)Ljava/lang/String;", "createHtmlOverview", "handlerInfo", "", "Lio/javalin/event/HandlerMetaInfo;", "wsHandlerInfo", "Lio/javalin/event/WsHandlerMetaInfo;", "createJsonOverview", "javalin"})
@SourceDebugExtension(value={"SMAP\nRouteOverviewUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RouteOverviewUtil.kt\nio/javalin/plugin/bundled/RouteOverviewUtil\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,229:1\n1549#2:230\n1620#2,3:231\n1549#2:234\n1620#2,3:235\n1549#2:238\n1620#2,3:239\n1549#2:242\n1620#2,3:243\n1#3:246\n*S KotlinDebug\n*F\n+ 1 RouteOverviewUtil.kt\nio/javalin/plugin/bundled/RouteOverviewUtil\n*L\n121#1:230\n121#1:231,3\n133#1:234\n133#1:235,3\n171#1:238\n171#1:239,3\n185#1:242\n185#1:243,3\n*E\n"})
public final class RouteOverviewUtil {
    @NotNull
    public static final RouteOverviewUtil INSTANCE = new RouteOverviewUtil();

    private RouteOverviewUtil() {
    }

    @JvmStatic
    @NotNull
    public static final String createHtmlOverview(@NotNull List<HandlerMetaInfo> handlerInfo, @NotNull List<WsHandlerMetaInfo> wsHandlerInfo) {
        Set<RouteRole> roles;
        Object handler;
        String path;
        Object object;
        Object object2;
        Iterable $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(handlerInfo, "handlerInfo");
        Intrinsics.checkNotNullParameter(wsHandlerInfo, "wsHandlerInfo");
        Iterable $this$map$iv = handlerInfo;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            object2 = (HandlerMetaInfo)item$iv$iv;
            object = destination$iv$iv;
            boolean bl = false;
            HandlerType handlerType = ((HandlerMetaInfo)object2).component1();
            path = ((HandlerMetaInfo)object2).component2();
            handler = ((HandlerMetaInfo)object2).component3();
            roles = ((HandlerMetaInfo)object2).component4();
            object.add("\n                    <tr class=\"method " + handlerType + "\">\n                        <td>" + handlerType + "</span></td>\n                        <td>" + path + "</td>\n                        <td><b>" + RouteOverviewUtil.getMetaInfo(handler) + "</b></td>\n                        <td>" + roles + "</td>\n                    </tr>\n                    ");
        }
        $this$map$iv = wsHandlerInfo;
        object = CollectionsKt.joinToString$default((List)destination$iv$iv, "", null, null, 0, null, null, 62, null);
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            object2 = (WsHandlerMetaInfo)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            WsHandlerType wsHandlerType = ((WsHandlerMetaInfo)object2).component1();
            path = ((WsHandlerMetaInfo)object2).component2();
            handler = ((WsHandlerMetaInfo)object2).component3();
            roles = ((WsHandlerMetaInfo)object2).component4();
            collection.add("\n                    <tr class=\"method " + wsHandlerType + "\">\n                        <td>" + wsHandlerType + "</span></td>\n                        <td>" + path + "</td>\n                        <td><b>" + RouteOverviewUtil.getMetaInfo(handler) + "</b></td>\n                        <td>" + roles + "</td>\n                    </tr>\n                    ");
        }
        return "\n        <meta name='viewport' content='width=device-width, initial-scale=1'>\n        <style>\n            * {\n                box-sizing: border-box;\n            }\n            b, thead {\n                font-weight: 700;\n            }\n            html {\n                background: #363e4c;\n            }\n            body {\n                font-family: monospace;\n                padding: 25px;\n            }\n            table {\n                background: #fff;\n                border-spacing: 0;\n                font-size: 14px;\n                width: 100%;\n                white-space: pre;\n                box-shadow: 0 5px 25px rgba(0,0,0,0.25);\n            }\n            thead {\n                background: #1a202b;\n                color: #fff;\n            }\n            thead td {\n                border-bottom: 2px solid #000;\n                cursor: pointer;\n            }\n            tr + tr td {\n                border-top: 1px solid rgba(0, 0, 0, 0.25);\n            }\n            tr + tr td:first-of-type {\n                border-top: 1px solid rgba(0, 0, 0, 0.35);\n            }\n            td {\n                padding: 10px 15px;\n            }\n            tbody td:not(:first-of-type) {\n                background-color: rgba(255,255,255,0.925);\n            }\n            tbody tr:hover td:not(:first-of-type) {\n                background-color: rgba(255,255,255,0.85);\n            }\n            .method td:first-of-type {\n                text-align: center;\n                max-width: 90px;\n            }\n            tbody .method td:first-of-type {\n                font-weight: 700;\n                color: #fff;\n                text-shadow: 1px 1px 0px rgba(0,0,0,0.5);\n                border-left: 6px solid rgba(0, 0, 0, 0.35);\n                border-right: 1px solid rgba(0, 0, 0, 0.15);\n            }\n            .GET {\n                background: #5a76ff;\n            }\n            .POST {\n                background: #5dca5d;\n            }\n            .PUT {\n                background: #d9cc00;\n            }\n            .PATCH {\n                background: #ef9a00;\n            }\n            .DELETE {\n                background: #e44848;\n            }\n            .HEAD, .TRACE, .OPTIONS, .CONNECT  {\n                background: #00b9b9;\n            }\n            .BEFORE, .AFTER {\n                background: #777;\n            }\n            .WEBSOCKET, .WS_BEFORE, .WS_AFTER {\n                background: #546E7A;\n            }\n        </style>\n        <body>\n            <table>\n                <thead>\n                    <tr class=\"method\">\n                        <td width=\"105px\">Method</td>\n                        <td>Path</td>\n                        <td>Handler</td>\n                        <td>Roles</td>\n                    </tr>\n                </thead>\n                " + (String)object + "\n                " + CollectionsKt.joinToString$default((List)destination$iv$iv, "", null, null, 0, null, null, 62, null) + "\n            </table>\n            <script>\n                const cachedRows = Array.from(document.querySelectorAll(\"tbody tr\"));\n                const verbOrder = [\"BEFORE\", \"GET\", \"POST\", \"PUT\", \"PATCH\", \"DELETE\", \"CONNECT\", \"OPTIONS\", \"TRACE\", \"HEAD\", \"AFTER\", \"WS_BEFORE\", \"WEBSOCKET\", \"WS_AFTER\"];\n                document.querySelector(\"thead\").addEventListener(\"click\", function (e) {\n                    cachedRows.map(function (el) {\n                        return {key: el.children[e.target.cellIndex].textContent, row: el};\n                    }).sort((a, b) => {\n                        if (e.target.textContent === \"Method\") {\n                            return verbOrder.indexOf(a.key) - verbOrder.indexOf(b.key);\n                        }\n                        return a.key.localeCompare(b.key);\n                    }).forEach((pair, i) => {\n                        document.querySelector(\"tbody\").children[i].outerHTML = pair.row.outerHTML\n                    });\n                });\n            </script>\n        </body>\n    ";
    }

    @JvmStatic
    @NotNull
    public static final String createJsonOverview(@NotNull List<HandlerMetaInfo> handlerInfo, @NotNull List<WsHandlerMetaInfo> wsHandlerInfo) {
        Set<RouteRole> roles;
        Object handler;
        String path;
        Object object;
        Object object2;
        Iterable $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(handlerInfo, "handlerInfo");
        Intrinsics.checkNotNullParameter(wsHandlerInfo, "wsHandlerInfo");
        Iterable $this$map$iv = handlerInfo;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            object2 = (HandlerMetaInfo)item$iv$iv;
            object = destination$iv$iv;
            boolean bl = false;
            HandlerType handlerType = ((HandlerMetaInfo)object2).component1();
            path = ((HandlerMetaInfo)object2).component2();
            handler = ((HandlerMetaInfo)object2).component3();
            roles = ((HandlerMetaInfo)object2).component4();
            object.add("\n                    {\n                        \"path\": \"" + path + "\",\n                        \"handlerType\": \"" + handlerType + "\",\n                        \"metaInfo\": \"" + handler + ".metaInfo\",\n                        \"roles\": \"" + roles + "\"\n                    }\n                    ");
        }
        $this$map$iv = wsHandlerInfo;
        object = CollectionsKt.joinToString$default((List)destination$iv$iv, ",", null, null, 0, null, null, 62, null);
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            object2 = (WsHandlerMetaInfo)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            WsHandlerType wsHandlerType = ((WsHandlerMetaInfo)object2).component1();
            path = ((WsHandlerMetaInfo)object2).component2();
            handler = ((WsHandlerMetaInfo)object2).component3();
            roles = ((WsHandlerMetaInfo)object2).component4();
            collection.add("\n                    {\n                        \"path\": \"" + path + "\",\n                        \"handlerType\": \"" + wsHandlerType + "\",\n                        \"metaInfo\": \"" + handler + ".metaInfo\",\n                        \"roles\": \"" + roles + "\"\n                    }\n                    ");
        }
        return "\n            {\n                \"handlers\": [\n                " + (String)object + "\n                ],\n                \"wsHandlers\": [\n                " + CollectionsKt.joinToString$default((List)destination$iv$iv, ",", null, null, 0, null, null, 62, null) + "\n                ]\n            }\n    ";
    }

    @NotNull
    public static final String getMetaInfo(@NotNull Object $this$metaInfo) {
        String string;
        Intrinsics.checkNotNullParameter($this$metaInfo, "<this>");
        if (ReflectionUtilKt.isClass($this$metaInfo)) {
            string = ((Class)$this$metaInfo).getName() + ".class";
        } else if (ReflectionUtilKt.isKotlinMethodReference($this$metaInfo)) {
            Object object;
            Object object2;
            block7: {
                Field[] fieldArray = $this$metaInfo.getClass().getDeclaredFields();
                Intrinsics.checkNotNullExpressionValue(fieldArray, "this.javaClass.declaredFields");
                object2 = fieldArray;
                int n = ((Object[])object2).length;
                for (int i = 0; i < n; ++i) {
                    Object object3 = object2[i];
                    Field it = (Field)object3;
                    boolean bl = false;
                    if (!(Intrinsics.areEqual(it.getName(), "function") || Intrinsics.areEqual(it.getName(), "$tmp0"))) continue;
                    object = object3;
                    break block7;
                }
                object = null;
            }
            Intrinsics.checkNotNull(object);
            String fieldName = ((Field)object).getName();
            Object $this$_get_metaInfo__u24lambda_u245 = object2 = $this$metaInfo.getClass().getDeclaredField(fieldName);
            boolean bl = false;
            ((Field)$this$_get_metaInfo__u24lambda_u245).setAccessible(true);
            Object field2 = ((Field)object2).get($this$metaInfo);
            if (Intrinsics.areEqual(fieldName, "function")) {
                Intrinsics.checkNotNullExpressionValue(field2, "field");
                string = ReflectionUtilKt.runMethod(ReflectionUtilKt.runMethod(ReflectionUtilKt.runMethod(field2, "getOwner"), "getJClass"), "getName") + "::" + ReflectionUtilKt.runMethod(field2, "getName");
            } else {
                Intrinsics.checkNotNullExpressionValue(field2, "field");
                string = ReflectionUtilKt.getImplementingClassName(field2) + "::??? (anonymous lambda)";
            }
        } else {
            string = ReflectionUtilKt.isKotlinAnonymousLambda($this$metaInfo) ? ReflectionUtilKt.getParentClass($this$metaInfo).getName() + "::??? (anonymous lambda)" : (ReflectionUtilKt.isKotlinField($this$metaInfo) ? ReflectionUtilKt.getParentClass($this$metaInfo).getName() + "." + ReflectionUtilKt.getKotlinFieldName($this$metaInfo) : (ReflectionUtilKt.isJavaField($this$metaInfo) ? ReflectionUtilKt.getParentClass($this$metaInfo).getName() + "." + ReflectionUtilKt.getJavaFieldName($this$metaInfo) : (ReflectionUtilKt.isJavaAnonymousLambda($this$metaInfo) ? ReflectionUtilKt.getParentClass($this$metaInfo).getName() + "::??? (anonymous lambda)" : ReflectionUtilKt.getImplementingClassName($this$metaInfo) + ".class")));
        }
        return string;
    }

    @JvmStatic
    public static /* synthetic */ void getMetaInfo$annotations(Object object) {
    }
}

