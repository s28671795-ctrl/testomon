/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.Parameter;
import org.jooq.Pro;
import org.jooq.impl.QueryPartCollectionView;

@Pro
final class ParameterList<T extends Parameter<?>>
extends QueryPartCollectionView<T> {
    ParameterList(Collection<T> parameters) {
        super(parameters);
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return true;
    }

    @Override
    public final boolean declaresParameters() {
        return true;
    }
}

