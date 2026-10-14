/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Comment;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.Name;
import org.jooq.Nullability;
import org.jooq.impl.AbstractDataType;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;

abstract class AbstractDataTypeX<T>
extends AbstractDataType<T> {
    private static final JooqLogger logGeneratedAlwaysAs = JooqLogger.getLogger(AbstractDataTypeX.class, "generateAlwaysAs", 1);

    AbstractDataTypeX(Name name, Comment comment) {
        super(name, comment);
    }

    abstract AbstractDataTypeX<T> construct(Integer var1, Integer var2, Integer var3, Nullability var4, boolean var5, Generator<?, ?, T> var6, QOM.GenerationOption var7, QOM.GenerationLocation var8, Collation var9, CharacterSet var10, boolean var11, Field<T> var12);

    @Override
    public final DataType<T> nullability(Nullability n) {
        return this.construct(this.precision0(), this.scale0(), this.length0(), n, this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), !n.nullable() && this.identity(), this.defaultValue());
    }

    @Override
    public final DataType<T> readonly(boolean r) {
        if (r && !Tools.CONFIG.get().commercial()) {
            logGeneratedAlwaysAs.info((Object)"Readonly columns", "Readonly columns are a commercial only jOOQ feature. If you wish to profit from this feature, please upgrade to the jOOQ Professional Edition");
        }
        return this.construct(this.precision0(), this.scale0(), this.length0(), this.nullability(), r, this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), this.identity(), this.defaultValue());
    }

    @Override
    public final DataType<T> generatedAlwaysAs(Generator<?, ?, T> g2) {
        if (g2 != null && !Tools.CONFIG.get().commercial()) {
            logGeneratedAlwaysAs.info((Object)"Computed columns", "Computed columns are a commercial only jOOQ feature. If you wish to profit from this feature, please upgrade to the jOOQ Professional Edition");
        }
        return this.construct(this.precision0(), this.scale0(), this.length0(), this.nullability(), g2 != null ? true : this.readonly(), g2, this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), this.identity(), g2 != null ? null : this.defaultValue());
    }

    @Override
    public final DataType<T> generationOption(QOM.GenerationOption g2) {
        if (g2 != null && !Tools.CONFIG.get().commercial()) {
            logGeneratedAlwaysAs.info((Object)"Computed columns", "Computed columns are a commercial only jOOQ feature. If you wish to profit from this feature, please upgrade to the jOOQ Professional Edition");
        }
        return this.construct(this.precision0(), this.scale0(), this.length0(), this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), g2, this.generationLocation(), this.collation(), this.characterSet(), this.identity(), this.defaultValue());
    }

    @Override
    public final DataType<T> generationLocation(QOM.GenerationLocation g2) {
        if (g2 != null && !Tools.CONFIG.get().commercial()) {
            logGeneratedAlwaysAs.info((Object)"Computed columns", "Computed columns are a commercial only jOOQ feature. If you wish to profit from this feature, please upgrade to the jOOQ Professional Edition");
        }
        return this.construct(this.precision0(), this.scale0(), this.length0(), this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), g2, this.collation(), this.characterSet(), this.identity(), this.defaultValue());
    }

    @Override
    public final DataType<T> collation(Collation c) {
        return this.construct(this.precision0(), this.scale0(), this.length0(), this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), c, this.characterSet(), this.identity(), this.defaultValue());
    }

    @Override
    public final DataType<T> characterSet(CharacterSet c) {
        return this.construct(this.precision0(), this.scale0(), this.length0(), this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), c, this.identity(), this.defaultValue());
    }

    @Override
    public final DataType<T> identity(boolean i) {
        return this.construct(this.precision0(), this.scale0(), this.length0(), i ? Nullability.NOT_NULL : this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), i, this.defaultValue());
    }

    @Override
    public final DataType<T> default_(Field<T> d) {
        return this.construct(this.precision0(), this.scale0(), this.length0(), this.nullability(), this.readonly(), d != null ? null : this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), this.identity(), d);
    }

    @Override
    final AbstractDataTypeX<T> precision1(Integer p, Integer s2) {
        return this.construct(p, s2, this.length0(), this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), this.identity(), this.defaultValue());
    }

    @Override
    final AbstractDataTypeX<T> scale1(Integer s2) {
        return this.construct(this.precision0(), s2, this.length0(), this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), this.identity(), this.defaultValue());
    }

    @Override
    final AbstractDataTypeX<T> length1(Integer l) {
        return this.construct(this.precision0(), this.scale0(), l, this.nullability(), this.readonly(), this.generatedAlwaysAsGenerator(), this.generationOption(), this.generationLocation(), this.collation(), this.characterSet(), this.identity(), this.defaultValue());
    }
}

