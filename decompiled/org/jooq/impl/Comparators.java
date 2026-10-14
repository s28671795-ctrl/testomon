/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Comparator;
import java.util.List;
import org.jooq.Check;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Key;
import org.jooq.Named;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

final class Comparators {
    static final Comparator<Named> NAMED_COMP = Comparator.comparing(Named::getQualifiedName);
    static final Comparator<Table<?>> TABLE_VIEW_COMP = Comparator.comparing(t2 -> t2.getTableType() == TableOptions.TableType.TABLE ? 0 : 1);
    static final Comparator<Key<?>> KEY_COMP = new KeyComparator();
    static final Comparator<ForeignKey<?, ?>> FOREIGN_KEY_COMP = new ForeignKeyComparator();
    static final Comparator<Check<?>> CHECK_COMP = Comparator.comparing(c -> c.condition().toString());
    static final Comparator<Index> INDEX_COMP = new IndexComparator();

    Comparators() {
    }

    private static final class KeyComparator
    implements Comparator<Key<?>> {
        private KeyComparator() {
        }

        @Override
        public int compare(Key<?> o1, Key<?> o2) {
            List<TableField<?, ?>> f1 = o1.getFields();
            List<TableField<?, ?>> f2 = o2.getFields();
            int c = f1.size() - f2.size();
            if (c != 0) {
                return c;
            }
            for (int i = 0; i < f1.size(); ++i) {
                c = NAMED_COMP.compare(f1.get(i), f2.get(i));
                if (c == 0) continue;
                return c;
            }
            return 0;
        }
    }

    private static final class ForeignKeyComparator
    implements Comparator<ForeignKey<?, ?>> {
        private ForeignKeyComparator() {
        }

        @Override
        public int compare(ForeignKey<?, ?> o1, ForeignKey<?, ?> o2) {
            int c = KEY_COMP.compare(o1, o2);
            if (c != 0) {
                return c;
            }
            return KEY_COMP.compare(o1.getKey(), o2.getKey());
        }
    }

    private static final class IndexComparator
    implements Comparator<Index> {
        private IndexComparator() {
        }

        @Override
        public int compare(Index o1, Index o2) {
            int c = Boolean.valueOf(o1.getUnique()).compareTo(o2.getUnique());
            if (c != 0) {
                return c;
            }
            c = StringUtils.defaultIfNull(o1.getWhere(), DSL.noCondition()).toString().compareTo(StringUtils.defaultIfNull(o2.getWhere(), DSL.noCondition()).toString());
            if (c != 0) {
                return c;
            }
            List<SortField<?>> f1 = o1.getFields();
            List<SortField<?>> f2 = o2.getFields();
            c = f1.size() - f2.size();
            if (c != 0) {
                return c;
            }
            for (int i = 0; i < f1.size(); ++i) {
                SortOrder d2;
                SortField<?> s1 = f1.get(i);
                SortField<?> s2 = f2.get(i);
                c = s1.getName().compareTo(s2.getName());
                if (c != 0) {
                    return c;
                }
                SortOrder d1 = s1.getOrder();
                if (d1 == SortOrder.DEFAULT) {
                    d1 = SortOrder.ASC;
                }
                if ((d2 = s2.getOrder()) == SortOrder.DEFAULT) {
                    d2 = SortOrder.ASC;
                }
                if ((c = d1.compareTo(d2)) == 0) continue;
                return c;
            }
            return 0;
        }
    }
}

