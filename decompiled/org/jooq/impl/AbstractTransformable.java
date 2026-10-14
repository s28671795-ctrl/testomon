/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Name;
import org.jooq.conf.SettingsTools;
import org.jooq.conf.TransformUnneededArithmeticExpressions;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Tools;
import org.jooq.impl.Transformable;

abstract class AbstractTransformable<T>
extends AbstractField<T>
implements Transformable<T> {
    AbstractTransformable(Name name, DataType<T> type) {
        super(name, type);
    }

    @Override
    public final void accept(Context<?> ctx) {
        TransformUnneededArithmeticExpressions transform = SettingsTools.getTransformUnneededArithmeticExpressions(ctx.settings());
        Object previous = ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_UNNEEDED_ARITHMETIC_EXPRESSIONS);
        if (transform != TransformUnneededArithmeticExpressions.NEVER) {
            if (previous == null) {
                ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_UNNEEDED_ARITHMETIC_EXPRESSIONS, true, c -> c.visit(this.transform(transform)));
                return;
            }
            ctx.data().remove(Tools.ExtendedDataKey.DATA_TRANSFORM_UNNEEDED_ARITHMETIC_EXPRESSIONS);
        }
        this.accept0(ctx);
        if (transform != TransformUnneededArithmeticExpressions.NEVER && previous != null) {
            ctx.data(Tools.ExtendedDataKey.DATA_TRANSFORM_UNNEEDED_ARITHMETIC_EXPRESSIONS, previous);
        }
    }

    abstract void accept0(Context<?> var1);
}

