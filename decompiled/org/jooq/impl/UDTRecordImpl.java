/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Internal;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.impl.AbstractQualifiedRecord;
import org.jooq.impl.DSL;

@Internal
public class UDTRecordImpl<R extends UDTRecord<R>>
extends AbstractQualifiedRecord<R>
implements UDTRecord<R> {
    public UDTRecordImpl(UDT<R> udt) {
        super(udt);
    }

    @Override
    public final UDT<R> getUDT() {
        return (UDT)this.getQualifier();
    }

    @Override
    public String toString() {
        return DSL.using(this.configuration()).renderInlined(DSL.inline(this));
    }
}

