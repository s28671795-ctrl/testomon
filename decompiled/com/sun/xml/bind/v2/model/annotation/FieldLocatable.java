/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind.v2.model.annotation;

import com.sun.xml.bind.v2.model.annotation.Locatable;
import com.sun.xml.bind.v2.model.nav.Navigator;
import com.sun.xml.bind.v2.runtime.Location;

public class FieldLocatable<F>
implements Locatable {
    private final Locatable upstream;
    private final F field;
    private final Navigator<?, ?, F, ?> nav;

    public FieldLocatable(Locatable upstream, F field2, Navigator<?, ?, F, ?> nav) {
        this.upstream = upstream;
        this.field = field2;
        this.nav = nav;
    }

    @Override
    public Locatable getUpstream() {
        return this.upstream;
    }

    @Override
    public Location getLocation() {
        return this.nav.getFieldLocation(this.field);
    }
}

