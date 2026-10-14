/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.text.CollationKey;
import java.text.Collator;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.util.SmallLRUCache;
import org.h2.value.CompareMode;

public class CompareModeDefault
extends CompareMode {
    private final Collator collator;
    private final SmallLRUCache<String, CollationKey> collationKeys;
    private volatile CompareModeDefault caseInsensitive;

    protected CompareModeDefault(String string, int n) {
        super(string, n);
        this.collator = CompareMode.getCollator(string);
        if (this.collator == null) {
            throw DbException.getInternalError(string);
        }
        this.collator.setStrength(n);
        int n2 = SysProperties.COLLATOR_CACHE_SIZE;
        this.collationKeys = n2 != 0 ? SmallLRUCache.newInstance(n2) : null;
    }

    @Override
    public int compareString(String string, String string2, boolean bl) {
        int n;
        if (bl && this.getStrength() > 1) {
            CompareModeDefault compareModeDefault = this.caseInsensitive;
            if (compareModeDefault == null) {
                this.caseInsensitive = compareModeDefault = new CompareModeDefault(this.getName(), 1);
            }
            return compareModeDefault.compareString(string, string2, false);
        }
        if (this.collationKeys != null) {
            CollationKey collationKey = this.getKey(string);
            CollationKey collationKey2 = this.getKey(string2);
            n = collationKey.compareTo(collationKey2);
        } else {
            n = this.collator.compare(string, string2);
        }
        return n;
    }

    @Override
    public boolean equalsChars(String string, int n, String string2, int n2, boolean bl) {
        return this.compareString(string.substring(n, n + 1), string2.substring(n2, n2 + 1), bl) == 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CollationKey getKey(String string) {
        SmallLRUCache<String, CollationKey> smallLRUCache = this.collationKeys;
        synchronized (smallLRUCache) {
            CollationKey collationKey = (CollationKey)this.collationKeys.get(string);
            if (collationKey == null) {
                collationKey = this.collator.getCollationKey(string);
                this.collationKeys.put(string, collationKey);
            }
            return collationKey;
        }
    }
}

