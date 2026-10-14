/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingListHolder;

public interface BindingIteratorOperations {
    public boolean next_one(BindingHolder var1);

    public boolean next_n(int var1, BindingListHolder var2);

    public void destroy();
}

