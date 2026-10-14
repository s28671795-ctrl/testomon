/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Map;
import org.jooq.ArrayOrAssociativeArrayRecord;
import org.jooq.DataType;
import org.jooq.Pro;

@Pro
public interface AssociativeArrayRecord<K, V>
extends ArrayOrAssociativeArrayRecord<V>,
Map<K, V> {
    public DataType<K> getIndexDataType();
}

