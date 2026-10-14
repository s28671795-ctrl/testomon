/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.FrequencyList;
import akka.util.OptionVal$;

@InternalApi
public final class FrequencyList$ {
    public static final FrequencyList$ MODULE$ = new FrequencyList$();

    public <A> FrequencyList<A> empty(boolean dynamicAging) {
        OptionVal$.MODULE$.None();
        return new FrequencyList(dynamicAging, null);
    }

    public <A> boolean empty$default$1() {
        return false;
    }

    private FrequencyList$() {
    }
}

