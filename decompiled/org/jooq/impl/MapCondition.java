/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Map;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class MapCondition
extends AbstractCondition
implements QOM.UEmpty {
    private final Map<Field<?>, ?> map;

    MapCondition(Map<Field<?>, ?> map) {
        this.map = map;
    }

    @Override
    public void accept(Context<?> ctx) {
        Condition c = DSL.noCondition();
        for (Map.Entry<Field<?>, ?> e : this.map.entrySet()) {
            c = c.and(e.getKey().eq(Tools.field(e.getValue(), e.getKey())));
        }
        ctx.visit(c);
    }
}

