/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.transaction.manager.selector;

import javax.transaction.TransactionManager;
import net.sf.ehcache.transaction.manager.selector.Selector;

public class NullSelector
extends Selector {
    public NullSelector() {
        super("null");
    }

    @Override
    protected TransactionManager doLookup() {
        return null;
    }
}

