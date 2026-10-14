/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.DeleteResultStep;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDMLQueryAsResultQuery;
import org.jooq.impl.DeleteQueryImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class DeleteAsResultQuery<R extends Record>
extends AbstractDMLQueryAsResultQuery<R, DeleteQueryImpl<R>>
implements DeleteResultStep<R>,
QOM.DeleteReturning<R> {
    DeleteAsResultQuery(DeleteQueryImpl<R> delegate, boolean returningResult) {
        super(delegate, returningResult);
    }

    @Override
    public final QOM.Delete<?> $delete() {
        return (QOM.Delete)this.getDelegate();
    }

    @Override
    public final QOM.DeleteReturning<R> $delete(QOM.Delete<?> newDelete) {
        return new DeleteAsResultQuery(Tools.deleteQueryImpl(newDelete).copy(d -> d.setReturning(this.$returning())), this.returningResult);
    }

    @Override
    public final QOM.UnmodifiableList<? extends SelectFieldOrAsterisk> $returning() {
        return QOM.unmodifiable(((DeleteQueryImpl)this.getDelegate()).returning);
    }

    @Override
    public final QOM.DeleteReturning<?> $returning(Collection<? extends SelectFieldOrAsterisk> returning) {
        return new DeleteAsResultQuery(((DeleteQueryImpl)this.getDelegate()).copy(d -> d.setReturning(returning)), this.returningResult);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$delete(), this.$returning());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$delete(), this.$returning(), (d, ret) -> new DeleteAsResultQuery(Tools.deleteQueryImpl(d).copy(x -> x.setReturning((Collection<? extends SelectFieldOrAsterisk>)ret)), this.returningResult), replacer);
    }
}

