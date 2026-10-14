/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.context.ContextAccessor
 */
package reactor.util.context;

import io.micrometer.context.ContextAccessor;
import java.util.Map;
import java.util.function.Predicate;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

public final class ReactorContextAccessor
implements ContextAccessor<ContextView, Context> {
    public Class<? extends ContextView> readableType() {
        return ContextView.class;
    }

    public void readValues(ContextView source2, Predicate<Object> keyPredicate, Map<Object, Object> target) {
        source2.forEach((k, v) -> {
            if (keyPredicate.test(k)) {
                target.put(k, v);
            }
        });
    }

    @Nullable
    public <T> T readValue(ContextView sourceContext, Object key) {
        return sourceContext.getOrDefault(key, null);
    }

    public Class<? extends Context> writeableType() {
        return Context.class;
    }

    public Context writeValues(Map<Object, Object> source2, Context target) {
        return target.putAllMap(source2);
    }
}

