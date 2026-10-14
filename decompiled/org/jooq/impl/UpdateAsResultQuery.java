/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Traverser;
import org.jooq.UpdateResultStep;
import org.jooq.impl.AbstractDMLQueryAsResultQuery;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.UpdateQueryImpl;

final class UpdateAsResultQuery<R extends Record>
extends AbstractDMLQueryAsResultQuery<R, UpdateQueryImpl<R>>
implements UpdateResultStep<R>,
QOM.UpdateReturning<R> {
    UpdateAsResultQuery(UpdateQueryImpl<R> delegate, boolean returningResult) {
        super(delegate, returningResult);
    }

    @Override
    public final QOM.Update<?> $update() {
        return (QOM.Update)this.getDelegate();
    }

    @Override
    public final QOM.UpdateReturning<R> $update(QOM.Update<?> newUpdate) {
        return new UpdateAsResultQuery(Tools.updateQueryImpl(newUpdate).copy(d -> d.setReturning(this.$returning())), this.returningResult);
    }

    @Override
    public final QOM.UnmodifiableList<? extends SelectFieldOrAsterisk> $returning() {
        return QOM.unmodifiable(((UpdateQueryImpl)this.getDelegate()).returning);
    }

    @Override
    public final QOM.UpdateReturning<?> $returning(Collection<? extends SelectFieldOrAsterisk> returning) {
        return new UpdateAsResultQuery(((UpdateQueryImpl)this.getDelegate()).copy(d -> d.setReturning(returning)), this.returningResult);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$update(), this.$returning());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$update(), this.$returning(), (d, ret) -> new UpdateAsResultQuery(Tools.updateQueryImpl(d).copy(x -> x.setReturning((Collection<? extends SelectFieldOrAsterisk>)ret)), this.returningResult), replacer);
    }
}

