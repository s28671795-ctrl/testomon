/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata;

import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;

public interface BeanMetaDataManager {
    public <T> BeanMetaData<T> getBeanMetaData(Class<T> var1);

    public void clear();
}

