/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import io.javalin.openapi.JsonSchemaLoader;
import io.javalin.openapi.JsonSchemaResource;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a8\u0006\u0006"}, d2={"Lio/javalin/openapi/JsonSchemaLoader;", "", "()V", "loadGeneratedSchemes", "", "Lio/javalin/openapi/JsonSchemaResource;", "openapi-specification"})
public final class JsonSchemaLoader {
    /*
     * Enabled aggressive block sorting
     */
    @NotNull
    public final Set<JsonSchemaResource> loadGeneratedSchemes() {
        Set<JsonSchemaResource> set;
        String string;
        byte[] byArray;
        InputStream inputStream2 = JsonSchemaLoader.class.getResourceAsStream("/json-schemes/index");
        if (inputStream2 != null && (byArray = inputStream2.readAllBytes()) != null && (string = StringsKt.decodeToString(byArray)) != null) {
            Set set2;
            Sequence sequence2;
            Sequence sequence3;
            Sequence sequence4;
            Sequence sequence5;
            String[] stringArray;
            List list;
            String string2 = ((Object)StringsKt.trim((CharSequence)string)).toString();
            if (string2 != null && (list = StringsKt.split$default((CharSequence)string2, stringArray = new String[]{"\n"}, false, 0, 6, null)) != null && (sequence5 = CollectionsKt.asSequence(list)) != null && (sequence4 = SequencesKt.distinct(sequence5)) != null && (sequence3 = SequencesKt.filter(sequence4, loadGeneratedSchemes.1.INSTANCE)) != null && (sequence2 = SequencesKt.map(sequence3, loadGeneratedSchemes.2.INSTANCE)) != null && (set2 = SequencesKt.toSet(sequence2)) != null) {
                set = set2;
                return set;
            }
        }
        set = SetsKt.emptySet();
        return set;
    }
}

