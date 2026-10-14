/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.AggregateFunction;
import org.jooq.ArrayRecord;
import org.jooq.Catalog;
import org.jooq.CheckReturnValue;
import org.jooq.Collation;
import org.jooq.Comment;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Constraint;
import org.jooq.DDLQuery;
import org.jooq.DMLQuery;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.Function0;
import org.jooq.Function1;
import org.jooq.Function10;
import org.jooq.Function11;
import org.jooq.Function12;
import org.jooq.Function13;
import org.jooq.Function14;
import org.jooq.Function15;
import org.jooq.Function16;
import org.jooq.Function17;
import org.jooq.Function18;
import org.jooq.Function19;
import org.jooq.Function2;
import org.jooq.Function20;
import org.jooq.Function21;
import org.jooq.Function22;
import org.jooq.Function3;
import org.jooq.Function4;
import org.jooq.Function5;
import org.jooq.Function6;
import org.jooq.Function7;
import org.jooq.Function8;
import org.jooq.Function9;
import org.jooq.Geometry;
import org.jooq.GroupField;
import org.jooq.Index;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.JSONEntry;
import org.jooq.Keyword;
import org.jooq.Link;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.Param;
import org.jooq.Parameter;
import org.jooq.Privilege;
import org.jooq.Pro;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Replacer;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Role;
import org.jooq.Row;
import org.jooq.RowCountQuery;
import org.jooq.RowId;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.Spatial;
import org.jooq.Statement;
import org.jooq.Table;
import org.jooq.TableElement;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.Variable;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.XML;
import org.jooq.XMLAttributes;
import org.jooq.impl.CachingTraverser;
import org.jooq.impl.DSL;
import org.jooq.impl.NoCondition;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.QueryPartMapView;
import org.jooq.impl.TupleImpl2;
import org.jooq.types.DayToSecond;

@ApiStatus.Experimental
public final class QOM {
    @Pro
    static final <Q> Q replace(Q q, Replacer replacer) {
        if (q instanceof List) {
            List l = (List)q;
            List<?> r = null;
            for (int i = 0; i < l.size(); ++i) {
                Object x;
                Object o = l.get(i);
                if (o != (x = QOM.replace(o, replacer))) {
                    if (r == null) {
                        r = QOM.newInstance(l);
                        r.addAll(l.subList(0, i));
                    }
                    r.add(x);
                    continue;
                }
                if (r == null) continue;
                r.add(o);
            }
            return (Q)(r != null ? r : (List<?>)q);
        }
        return (Q)(q instanceof QueryPart && replacer.recurse().test((QueryPart)q) ? ((QueryPart)q).$replace(replacer) : q);
    }

    @Pro
    private static final List<?> newInstance(List<?> l) {
        if (l instanceof QueryPartList) {
            return new QueryPartList();
        }
        if (l instanceof QueryPartListView) {
            return QueryPartListView.wrap(new ArrayList());
        }
        return new ArrayList();
    }

    @Pro
    static final <T> boolean test(Predicate<? super T> predicate, T value) {
        return predicate != null && predicate.test(value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Pro
    static final <A, T> T traverse(Traverser<A, T> traverser, Object part, Object ... parts) {
        CachingTraverser t2;
        block13: {
            CachingTraverser cachingTraverser = t2 = traverser instanceof CachingTraverser ? (CachingTraverser)traverser : new CachingTraverser<A, T>(traverser);
            if (QOM.test(t2.abort(), t2.supplied())) {
                return (T)t2.finished();
            }
            try {
                QueryPart p;
                if (part instanceof QueryPart) {
                    p = (QueryPart)part;
                    t2.before().apply(t2.supplied(), p);
                }
                if (QOM.test(t2.abort(), t2.supplied())) {
                    p = t2.finished();
                    return (T)p;
                }
                if (part instanceof QueryPart) {
                    p = (QueryPart)part;
                    if (!QOM.test(t2.recurseChildren(), p)) break block13;
                }
                for (int i = 0; i < parts.length; ++i) {
                    Object object = parts[i];
                    if (!(object instanceof QueryPart)) continue;
                    QueryPart p2 = (QueryPart)object;
                    if (!QOM.test(t2.recurse(), p2)) continue;
                    p2.$traverse(t2);
                    if (!QOM.test(t2.abort(), t2.supplied())) continue;
                    object = t2.finished();
                    return (T)object;
                }
            }
            finally {
                if (part instanceof QueryPart) {
                    QueryPart p = (QueryPart)part;
                    t2.after().apply(t2.supplied(), p);
                }
            }
        }
        return (T)t2.finished();
    }

    @Pro
    static final <QR extends QueryPart, Q> QR replace(QR wrapper, Q[] q, Function1<? super Q[], ? extends QR> wrap, Replacer replacer) {
        int i;
        Object[] r = (Object[])java.lang.reflect.Array.newInstance(q.getClass().getComponentType(), q.length);
        for (i = 0; i < r.length; ++i) {
            r[i] = QOM.replace(q[i], replacer);
        }
        for (i = 0; i < r.length; ++i) {
            if (r[i] == q[i]) continue;
            wrapper = (QueryPart)wrap.apply(r);
            break;
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1> QR replace(QR wrapper, Function0<? extends QR> wrap, Replacer replacer) {
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1> QR replace(QR wrapper, Q1 q1, Function1<? super Q1, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        if (r1 != q1) {
            wrapper = (QueryPart)wrap.apply(r1);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2> QR replace(QR wrapper, Q1 q1, Q2 q2, Function2<? super Q1, ? super Q2, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        if (r1 != q1 || r2 != q2) {
            wrapper = (QueryPart)wrap.apply(r1, r2);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Function3<? super Q1, ? super Q2, ? super Q3, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Function4<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Function5<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Function6<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Function7<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Function8<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Function9<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Function10<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Function11<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Function12<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Function13<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Function14<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Q15 q15, Function15<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? super Q15, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        Q15 r15 = QOM.replace(q15, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14 || r15 != q15) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15, Q16> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Q15 q15, Q16 q16, Function16<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? super Q15, ? super Q16, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        Q15 r15 = QOM.replace(q15, replacer);
        Q16 r16 = QOM.replace(q16, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14 || r15 != q15 || r16 != q16) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15, Q16, Q17> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Q15 q15, Q16 q16, Q17 q17, Function17<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? super Q15, ? super Q16, ? super Q17, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        Q15 r15 = QOM.replace(q15, replacer);
        Q16 r16 = QOM.replace(q16, replacer);
        Q17 r17 = QOM.replace(q17, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14 || r15 != q15 || r16 != q16 || r17 != q17) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15, Q16, Q17, Q18> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Q15 q15, Q16 q16, Q17 q17, Q18 q18, Function18<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? super Q15, ? super Q16, ? super Q17, ? super Q18, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        Q15 r15 = QOM.replace(q15, replacer);
        Q16 r16 = QOM.replace(q16, replacer);
        Q17 r17 = QOM.replace(q17, replacer);
        Q18 r18 = QOM.replace(q18, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14 || r15 != q15 || r16 != q16 || r17 != q17 || r18 != q18) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15, Q16, Q17, Q18, Q19> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Q15 q15, Q16 q16, Q17 q17, Q18 q18, Q19 q19, Function19<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? super Q15, ? super Q16, ? super Q17, ? super Q18, ? super Q19, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        Q15 r15 = QOM.replace(q15, replacer);
        Q16 r16 = QOM.replace(q16, replacer);
        Q17 r17 = QOM.replace(q17, replacer);
        Q18 r18 = QOM.replace(q18, replacer);
        Q19 r19 = QOM.replace(q19, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14 || r15 != q15 || r16 != q16 || r17 != q17 || r18 != q18 || r19 != q19) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15, Q16, Q17, Q18, Q19, Q20> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Q15 q15, Q16 q16, Q17 q17, Q18 q18, Q19 q19, Q20 q20, Function20<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? super Q15, ? super Q16, ? super Q17, ? super Q18, ? super Q19, ? super Q20, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        Q15 r15 = QOM.replace(q15, replacer);
        Q16 r16 = QOM.replace(q16, replacer);
        Q17 r17 = QOM.replace(q17, replacer);
        Q18 r18 = QOM.replace(q18, replacer);
        Q19 r19 = QOM.replace(q19, replacer);
        Q20 r20 = QOM.replace(q20, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14 || r15 != q15 || r16 != q16 || r17 != q17 || r18 != q18 || r19 != q19 || r20 != q20) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15, Q16, Q17, Q18, Q19, Q20, Q21> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Q15 q15, Q16 q16, Q17 q17, Q18 q18, Q19 q19, Q20 q20, Q21 q21, Function21<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? super Q15, ? super Q16, ? super Q17, ? super Q18, ? super Q19, ? super Q20, ? super Q21, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        Q15 r15 = QOM.replace(q15, replacer);
        Q16 r16 = QOM.replace(q16, replacer);
        Q17 r17 = QOM.replace(q17, replacer);
        Q18 r18 = QOM.replace(q18, replacer);
        Q19 r19 = QOM.replace(q19, replacer);
        Q20 r20 = QOM.replace(q20, replacer);
        Q21 r21 = QOM.replace(q21, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14 || r15 != q15 || r16 != q16 || r17 != q17 || r18 != q18 || r19 != q19 || r20 != q20 || r21 != q21) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    static final <QR extends QueryPart, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, Q14, Q15, Q16, Q17, Q18, Q19, Q20, Q21, Q22> QR replace(QR wrapper, Q1 q1, Q2 q2, Q3 q3, Q4 q4, Q5 q5, Q6 q6, Q7 q7, Q8 q8, Q9 q9, Q10 q10, Q11 q11, Q12 q12, Q13 q13, Q14 q14, Q15 q15, Q16 q16, Q17 q17, Q18 q18, Q19 q19, Q20 q20, Q21 q21, Q22 q22, Function22<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? super Q5, ? super Q6, ? super Q7, ? super Q8, ? super Q9, ? super Q10, ? super Q11, ? super Q12, ? super Q13, ? super Q14, ? super Q15, ? super Q16, ? super Q17, ? super Q18, ? super Q19, ? super Q20, ? super Q21, ? super Q22, ? extends QR> wrap, Replacer replacer) {
        Q1 r1 = QOM.replace(q1, replacer);
        Q2 r2 = QOM.replace(q2, replacer);
        Q3 r3 = QOM.replace(q3, replacer);
        Q4 r4 = QOM.replace(q4, replacer);
        Q5 r5 = QOM.replace(q5, replacer);
        Q6 r6 = QOM.replace(q6, replacer);
        Q7 r7 = QOM.replace(q7, replacer);
        Q8 r8 = QOM.replace(q8, replacer);
        Q9 r9 = QOM.replace(q9, replacer);
        Q10 r10 = QOM.replace(q10, replacer);
        Q11 r11 = QOM.replace(q11, replacer);
        Q12 r12 = QOM.replace(q12, replacer);
        Q13 r13 = QOM.replace(q13, replacer);
        Q14 r14 = QOM.replace(q14, replacer);
        Q15 r15 = QOM.replace(q15, replacer);
        Q16 r16 = QOM.replace(q16, replacer);
        Q17 r17 = QOM.replace(q17, replacer);
        Q18 r18 = QOM.replace(q18, replacer);
        Q19 r19 = QOM.replace(q19, replacer);
        Q20 r20 = QOM.replace(q20, replacer);
        Q21 r21 = QOM.replace(q21, replacer);
        Q22 r22 = QOM.replace(q22, replacer);
        if (r1 != q1 || r2 != q2 || r3 != q3 || r4 != q4 || r5 != q5 || r6 != q6 || r7 != q7 || r8 != q8 || r9 != q9 || r10 != q10 || r11 != q11 || r12 != q12 || r13 != q13 || r14 != q14 || r15 != q15 || r16 != q16 || r17 != q17 || r18 != q18 || r19 != q19 || r20 != q20 || r21 != q21 || r22 != q22) {
            wrapper = (QueryPart)wrap.apply(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22);
        }
        return QOM.replaceUntilStable(wrapper, replacer);
    }

    @Pro
    private static final <QR extends QueryPart> QR replaceUntilStable(QR wrapper, Replacer replacer) {
        Object q = wrapper;
        Object r = wrapper;
        do {
            if (!replacer.recurse().test((QueryPart)q)) continue;
            r = replacer.replacer().apply((QueryPart)q);
        } while (r != null && r != q && (q = r.$replace(replacer)) != null);
        return (QR)r;
    }

    @Pro
    static final Replacer internalReplacer(Replacer delegate) {
        return Replacer.of(delegate.recurse(), q -> {
            QueryPart r = delegate.replacer().apply((QueryPart)q);
            if (r instanceof And) {
                And c = (And)r;
                if (c.$arg1() instanceof NoCondition) {
                    return (QueryPart)c.$arg2();
                }
                if (c.$arg2() instanceof NoCondition) {
                    return (QueryPart)c.$arg1();
                }
            } else if (r instanceof Or) {
                Or c = (Or)r;
                if (c.$arg1() instanceof NoCondition) {
                    return (QueryPart)c.$arg2();
                }
                if (c.$arg2() instanceof NoCondition) {
                    return (QueryPart)c.$arg1();
                }
            }
            return r;
        });
    }

    @ApiStatus.Internal
    public static final <Q extends QueryPart> UnmodifiableList<Q> unmodifiable(Q[] array) {
        return QOM.unmodifiable(QueryPartListView.wrap(array));
    }

    @ApiStatus.Internal
    public static final <Q extends QueryPart> UnmodifiableList<Q> unmodifiable(List<Q> list) {
        return QueryPartListView.wrap(Collections.unmodifiableList(list));
    }

    @ApiStatus.Internal
    public static final <Q extends QueryPart> UnmodifiableList<Q> unmodifiable(Collection<Q> collection) {
        if (collection instanceof List) {
            return QOM.unmodifiable((List)collection);
        }
        return new QueryPartList<Q>(Collections.unmodifiableCollection(collection));
    }

    @ApiStatus.Internal
    public static final <K extends QueryPart, V extends QueryPart> UnmodifiableMap<K, V> unmodifiable(Map<K, V> map) {
        return new QueryPartMapView<K, V>(Collections.unmodifiableMap(map));
    }

    @ApiStatus.Internal
    public static final <Q1 extends QueryPart, Q2 extends QueryPart> Tuple2<Q1, Q2> tuple(Q1 q1, Q2 q2) {
        return new TupleImpl2<Q1, Q2>(q1, q2);
    }

    static final <Q extends QueryPart> boolean commutativeCheck(UCommutativeOperator<Q, ?> op, Predicate<? super Q> f) {
        return f.test(op.$arg1()) || f.test(op.$arg2());
    }

    static final <Q extends QueryPart> boolean commutativeCheck(UCommutativeOperator<Q, ?> op, BiPredicate<? super Q, ? super Q> f) {
        return f.test(op.$arg1(), op.$arg2()) || f.test(op.$arg2(), op.$arg1());
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface UnmodifiableList<Q extends QueryPart>
    extends UnmodifiableCollection<Q>,
    List<Q> {
        default public <R> R $collect(Collector<Q, ?, R> collector) {
            return this.stream().collect(collector);
        }

        @NotNull
        @CheckReturnValue
        default public UnmodifiableList<Q> $concat(Collection<? extends Q> other) {
            QueryPartList<? extends Q> r = new QueryPartList<Q>(this);
            r.addAll(other);
            return QOM.unmodifiable(r);
        }

        @NotNull
        @CheckReturnValue
        default public UnmodifiableList<Q> $remove(int position) {
            QueryPartList<QueryPart> r = new QueryPartList<QueryPart>();
            for (int i = 0; i < this.size(); ++i) {
                if (i == position) continue;
                r.add((QueryPart)this.get(i));
            }
            return QOM.unmodifiable(r);
        }

        @Nullable
        default public Q $first() {
            return (Q)(this.isEmpty() ? null : (QueryPart)this.get(0));
        }

        @Nullable
        default public Q $last() {
            return (Q)(this.isEmpty() ? null : (QueryPart)this.get(this.size() - 1));
        }

        @NotNull
        @CheckReturnValue
        default public UnmodifiableList<Q> $removeFirst() {
            QueryPartList<QueryPart> r = new QueryPartList<QueryPart>();
            for (int i = 1; i < this.size(); ++i) {
                r.add((QueryPart)this.get(i));
            }
            return QOM.unmodifiable(r);
        }

        @NotNull
        @CheckReturnValue
        default public UnmodifiableList<Q> $removeLast() {
            QueryPartList<QueryPart> r = new QueryPartList<QueryPart>();
            for (int i = 0; i < this.size() - 1; ++i) {
                r.add((QueryPart)this.get(i));
            }
            return QOM.unmodifiable(r);
        }

        @NotNull
        @CheckReturnValue
        default public UnmodifiableList<Q> $set(int position, Q newValue) {
            QueryPartList<Object> r = new QueryPartList<Object>();
            for (int i = 0; i < this.size(); ++i) {
                if (i == position) {
                    r.add((Object)newValue);
                    continue;
                }
                r.add((Object)((QueryPart)this.get(i)));
            }
            return QOM.unmodifiable(r);
        }

        @NotNull
        @CheckReturnValue
        default public UnmodifiableList<Q> $setAll(int position, Collection<? extends Q> newValues) {
            QueryPartList<Object> r = new QueryPartList<Object>();
            for (int i = 0; i < this.size(); ++i) {
                if (i == position) {
                    r.addAll((Collection<Object>)newValues);
                    continue;
                }
                r.add((Object)((QueryPart)this.get(i)));
            }
            return QOM.unmodifiable(r);
        }
    }

    static interface UCommutativeOperator<Q, R extends UCommutativeOperator<Q, R>>
    extends UConvertibleOperator<Q, R, R> {
        @NotNull
        default public R $swap() {
            return (R)((UCommutativeOperator)this.$constructor().apply(this.$arg2(), this.$arg1()));
        }

        @Override
        default public R $converse() {
            return this.$swap();
        }
    }

    public static interface And
    extends UCommutativeOperator<Condition, And>,
    CombinedCondition<And> {
    }

    public static interface Or
    extends UCommutativeOperator<Condition, Or>,
    CombinedCondition<Or> {
    }

    static interface UEmptyQuery
    extends Query,
    UEmpty {
    }

    static interface UEmptyStatement
    extends Statement,
    UEmpty {
    }

    static interface UEmptyTable<R extends Record>
    extends Table<R>,
    UEmpty {
    }

    static interface UEmptyField<T>
    extends Field<T>,
    UEmpty {
    }

    static interface UEmptyCondition
    extends Condition,
    UEmpty {
    }

    static interface UEmpty
    extends QueryPart {
        @Override
        default public <R> R $traverse(Traverser<?, R> traverser) {
            return QOM.traverse(traverser, this, new Object[0]);
        }

        @Override
        default public QueryPart $replace(Replacer replacer) {
            if (replacer.recurse().test(this)) {
                return replacer.replacer().apply(this);
            }
            return this;
        }
    }

    static interface UProxy<Q extends QueryPart>
    extends QueryPart {
        public Q $delegate();

        @Override
        default public <R> R $traverse(Traverser<?, R> traverser) {
            return this.$delegate().$traverse(traverser);
        }

        @Override
        default public QueryPart $replace(Replacer replacer) {
            QueryPart r = this.$delegate().$replace(replacer);
            return this.$delegate() == r ? this : r;
        }
    }

    @Deprecated(forRemoval=true)
    public static class NotYetImplementedException
    extends RuntimeException {
    }

    @Deprecated(forRemoval=true)
    static interface UNotYetImplemented
    extends UEmpty {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static interface UOpaque
    extends UEmpty {
    }

    static interface UTransient
    extends UEmpty {
    }

    static interface UReturnsNullOnNullInput
    extends QueryPart {
    }

    static interface UConvertibleOperator<Q, R extends UConvertibleOperator<Q, R, C>, C extends UConvertibleOperator<Q, C, R>>
    extends UOperator2<Q, Q, R> {
        @NotNull
        public C $converse();
    }

    static interface UOperator4<Q1, Q2, Q3, Q4, R extends UOperator4<Q1, Q2, Q3, Q4, R>>
    extends UOperator<R> {
        public Q1 $arg1();

        public Q2 $arg2();

        public Q3 $arg3();

        public Q4 $arg4();

        @CheckReturnValue
        @NotNull
        default public R $arg1(Q1 newArg1) {
            return (R)((UOperator4)this.$constructor().apply(newArg1, this.$arg2(), this.$arg3(), this.$arg4()));
        }

        @CheckReturnValue
        @NotNull
        default public R $arg2(Q2 newArg2) {
            return (R)((UOperator4)this.$constructor().apply(this.$arg1(), newArg2, this.$arg3(), this.$arg4()));
        }

        @CheckReturnValue
        @NotNull
        default public R $arg3(Q3 newArg3) {
            return (R)((UOperator4)this.$constructor().apply(this.$arg1(), this.$arg2(), newArg3, this.$arg4()));
        }

        @CheckReturnValue
        @NotNull
        default public R $arg4(Q4 newArg4) {
            return (R)((UOperator4)this.$constructor().apply(this.$arg1(), this.$arg2(), this.$arg3(), newArg4));
        }

        @NotNull
        public Function4<? super Q1, ? super Q2, ? super Q3, ? super Q4, ? extends R> $constructor();

        @Override
        @NotNull
        default public List<?> $args() {
            return Collections.unmodifiableList(Arrays.asList(this.$arg1(), this.$arg2(), this.$arg3(), this.$arg4()));
        }

        default public <T> T $traverse(Traverser<?, T> traverser) {
            return QOM.traverse(traverser, this, this.$arg1(), this.$arg2(), this.$arg3(), this.$arg4());
        }

        @Override
        @NotNull
        default public QueryPart $replace(Replacer replacer) {
            return QOM.replace(this, this.$arg1(), this.$arg2(), this.$arg3(), this.$arg4(), this.$constructor(), replacer);
        }
    }

    static interface UOperator3<Q1, Q2, Q3, R extends UOperator3<Q1, Q2, Q3, R>>
    extends UOperator<R> {
        public Q1 $arg1();

        public Q2 $arg2();

        public Q3 $arg3();

        @CheckReturnValue
        @NotNull
        default public R $arg1(Q1 newArg1) {
            return (R)((UOperator3)this.$constructor().apply(newArg1, this.$arg2(), this.$arg3()));
        }

        @CheckReturnValue
        @NotNull
        default public R $arg2(Q2 newArg2) {
            return (R)((UOperator3)this.$constructor().apply(this.$arg1(), newArg2, this.$arg3()));
        }

        @CheckReturnValue
        @NotNull
        default public R $arg3(Q3 newArg3) {
            return (R)((UOperator3)this.$constructor().apply(this.$arg1(), this.$arg2(), newArg3));
        }

        @NotNull
        public Function3<? super Q1, ? super Q2, ? super Q3, ? extends R> $constructor();

        @Override
        @NotNull
        default public List<?> $args() {
            return Collections.unmodifiableList(Arrays.asList(this.$arg1(), this.$arg2(), this.$arg3()));
        }

        default public <T> T $traverse(Traverser<?, T> traverser) {
            return QOM.traverse(traverser, this, this.$arg1(), this.$arg2(), this.$arg3());
        }

        @Override
        @NotNull
        default public QueryPart $replace(Replacer replacer) {
            return QOM.replace(this, this.$arg1(), this.$arg2(), this.$arg3(), this.$constructor(), replacer);
        }
    }

    static interface UOperator2<Q1, Q2, R extends UOperator2<Q1, Q2, R>>
    extends UOperator<R> {
        public Q1 $arg1();

        public Q2 $arg2();

        @CheckReturnValue
        @NotNull
        default public R $arg1(Q1 newArg1) {
            return (R)((UOperator2)this.$constructor().apply(newArg1, this.$arg2()));
        }

        @CheckReturnValue
        @NotNull
        default public R $arg2(Q2 newArg2) {
            return (R)((UOperator2)this.$constructor().apply(this.$arg1(), newArg2));
        }

        @NotNull
        public Function2<? super Q1, ? super Q2, ? extends R> $constructor();

        @Override
        @NotNull
        default public List<?> $args() {
            return Collections.unmodifiableList(Arrays.asList(this.$arg1(), this.$arg2()));
        }

        default public <T> T $traverse(Traverser<?, T> traverser) {
            return QOM.traverse(traverser, this, this.$arg1(), this.$arg2());
        }

        @Override
        @NotNull
        default public QueryPart $replace(Replacer replacer) {
            return QOM.replace(this, this.$arg1(), this.$arg2(), this.$constructor(), replacer);
        }
    }

    static interface UOperator1<Q1, R extends UOperator1<Q1, R>>
    extends UOperator<R> {
        public Q1 $arg1();

        @CheckReturnValue
        @NotNull
        default public R $arg1(Q1 newArg1) {
            return (R)((UOperator1)this.$constructor().apply(newArg1));
        }

        @NotNull
        public Function1<? super Q1, ? extends R> $constructor();

        @Override
        @NotNull
        default public List<?> $args() {
            return Collections.unmodifiableList(Arrays.asList(this.$arg1()));
        }

        default public <T> T $traverse(Traverser<?, T> traverser) {
            return QOM.traverse(traverser, this, this.$arg1());
        }

        @Override
        @NotNull
        default public QueryPart $replace(Replacer replacer) {
            return QOM.replace(this, this.$arg1(), this.$constructor(), replacer);
        }
    }

    static interface UOperator0<R extends UOperator0<R>>
    extends UOperator<R> {
        @NotNull
        public Function0<? extends R> $constructor();

        @Override
        @NotNull
        default public List<?> $args() {
            return Collections.emptyList();
        }

        default public <T> T $traverse(Traverser<?, T> traverser) {
            return QOM.traverse(traverser, this, new Object[0]);
        }

        @Override
        @NotNull
        default public QueryPart $replace(Replacer replacer) {
            return QOM.replace(this, this.$constructor(), replacer);
        }
    }

    static interface UOperator<R extends QueryPart>
    extends QueryPart {
        @NotNull
        public List<?> $args();
    }

    public static enum Quantifier {
        ANY(DSL.keyword("any")),
        ALL(DSL.keyword("all"));

        final Keyword keyword;

        private Quantifier(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum ResultOption {
        OLD(DSL.keyword("old")),
        NEW(DSL.keyword("new")),
        FINAL(DSL.keyword("final"));

        final Keyword keyword;

        private ResultOption(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum Materialized {
        MATERIALIZED(DSL.keyword("materialized")),
        NOT_MATERIALIZED(DSL.keyword("not materialized"));

        final Keyword keyword;

        private Materialized(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum DocumentOrContent {
        DOCUMENT(DSL.keyword("document")),
        CONTENT(DSL.keyword("content"));

        final Keyword keyword;

        private DocumentOrContent(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum XMLPassingMechanism {
        BY_REF(DSL.keyword("by ref")),
        BY_VALUE(DSL.keyword("by value")),
        DEFAULT(DSL.keyword("default"));

        final Keyword keyword;

        private XMLPassingMechanism(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum JSONOnNull {
        NULL_ON_NULL(DSL.keyword("null on null")),
        ABSENT_ON_NULL(DSL.keyword("absent on null"));

        final Keyword keyword;

        private JSONOnNull(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum FrameExclude {
        CURRENT_ROW(DSL.keyword("current row")),
        TIES(DSL.keyword("ties")),
        GROUP(DSL.keyword("group")),
        NO_OTHERS(DSL.keyword("no others"));

        final Keyword keyword;

        private FrameExclude(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum FrameUnits {
        ROWS(DSL.keyword("rows")),
        RANGE(DSL.keyword("range")),
        GROUPS(DSL.keyword("groups"));

        final Keyword keyword;

        private FrameUnits(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum FromFirstOrLast {
        FROM_FIRST(DSL.keyword("from first")),
        FROM_LAST(DSL.keyword("from last"));

        final Keyword keyword;

        private FromFirstOrLast(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum NullTreatment {
        RESPECT_NULLS(DSL.keyword("respect nulls")),
        IGNORE_NULLS(DSL.keyword("ignore nulls"));

        final Keyword keyword;

        private NullTreatment(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum NullOrdering {
        NULLS_FIRST(DSL.keyword("nulls first")),
        NULLS_LAST(DSL.keyword("nulls last"));

        final Keyword keyword;

        private NullOrdering(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    @Pro
    public static enum NullCallClause {
        RETURNS_NULL_ON_NULL_INPUT(DSL.keyword("returns null on null input")),
        CALLED_ON_NULL_INPUT(DSL.keyword("called on null input"));

        final Keyword keyword;

        private NullCallClause(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    @Pro
    public static enum Deterministic {
        DETERMINISTIC(DSL.keyword("deterministic")),
        NOT_DETERMINISTIC(DSL.keyword("not deterministic"));

        final Keyword keyword;

        private Deterministic(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    @Pro
    public static enum SQLDataAccess {
        NO_SQL(DSL.keyword("no sql")),
        CONTAINS_SQL(DSL.keyword("contains sql")),
        READS_SQL_DATA(DSL.keyword("reads sql data")),
        MODIFIES_SQL_DATA(DSL.keyword("modifies sql data"));

        final Keyword keyword;

        private SQLDataAccess(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum TableCommitAction {
        DELETE_ROWS(DSL.keyword("delete rows")),
        PRESERVE_ROWS(DSL.keyword("preserve rows")),
        DROP(DSL.keyword("drop"));

        final Keyword keyword;

        private TableCommitAction(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum WithOrWithoutData {
        WITH_DATA(DSL.keyword("with data")),
        WITH_NO_DATA(DSL.keyword("with no data"));

        final Keyword keyword;

        private WithOrWithoutData(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum GenerationOption {
        STORED(DSL.keyword("stored")),
        VIRTUAL(DSL.keyword("virtual")),
        DEFAULT(DSL.keyword("default"));

        final Keyword keyword;

        private GenerationOption(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum GenerationLocation {
        CLIENT(DSL.keyword("client")),
        SERVER(DSL.keyword("server"));

        final Keyword keyword;

        private GenerationLocation(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum IdentityRestartOption {
        CONTINUE_IDENTITY(DSL.keyword("continue identity")),
        RESTART_IDENTITY(DSL.keyword("restart identity"));

        final Keyword keyword;

        private IdentityRestartOption(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum CycleOption {
        CYCLE(DSL.keyword("cycle")),
        NO_CYCLE(DSL.keyword("no cycle"));

        final Keyword keyword;

        private CycleOption(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    public static enum Cascade {
        CASCADE(DSL.keyword("cascade")),
        RESTRICT(DSL.keyword("restrict"));

        final Keyword keyword;

        private Cascade(Keyword keyword) {
            this.keyword = keyword;
        }
    }

    @Pro
    public static interface StZ
    extends UReturnsNullOnNullInput,
    UOperator1<Field<Geometry>, StZ>,
    Field<Double> {
        @NotNull
        default public Field<Geometry> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StZ $value(Field<Geometry> newValue) {
            return (StZ)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StY
    extends UReturnsNullOnNullInput,
    UOperator1<Field<Geometry>, StY>,
    Field<Double> {
        @NotNull
        default public Field<Geometry> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StY $value(Field<Geometry> newValue) {
            return (StY)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StX
    extends UReturnsNullOnNullInput,
    UOperator1<Field<Geometry>, StX>,
    Field<Double> {
        @NotNull
        default public Field<Geometry> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StX $value(Field<Geometry> newValue) {
            return (StX)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StWithin<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StWithin<T>>,
    Condition {
    }

    @Pro
    public static interface StUnion<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StUnion<T>>,
    Field<T> {
    }

    @Pro
    public static interface StTouches<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StTouches<T>>,
    Condition {
    }

    @Pro
    public static interface StStartPoint<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator1<Field<T>, StStartPoint<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StStartPoint<T> $value(Field<T> newValue) {
            return (StStartPoint)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StSrid
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StSrid>,
    Field<Integer> {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StSrid $value(Field<? extends Spatial> newValue) {
            return (StSrid)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StPointN<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<? extends Number>, StPointN<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StPointN<T> $value(Field<T> newValue) {
            return (StPointN)this.$arg1(newValue);
        }

        @NotNull
        default public Field<? extends Number> $position() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public StPointN<T> $position(Field<? extends Number> newPosition) {
            return (StPointN)this.$arg2(newPosition);
        }
    }

    @Pro
    public static interface StOverlaps<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StOverlaps<T>>,
    Condition {
    }

    @Pro
    public static interface StNumPoints
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StNumPoints>,
    Field<Integer> {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StNumPoints $value(Field<? extends Spatial> newValue) {
            return (StNumPoints)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StNumInteriorRings
    extends UReturnsNullOnNullInput,
    UOperator1<Field<Geometry>, StNumInteriorRings>,
    Field<Integer> {
        @NotNull
        default public Field<Geometry> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StNumInteriorRings $value(Field<Geometry> newValue) {
            return (StNumInteriorRings)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StNumGeometries
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StNumGeometries>,
    Field<Integer> {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StNumGeometries $value(Field<? extends Spatial> newValue) {
            return (StNumGeometries)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StLength
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StLength>,
    Field<Double> {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StLength $value(Field<? extends Spatial> newValue) {
            return (StLength)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StIsEmpty
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StIsEmpty>,
    Condition {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StIsEmpty $value(Field<? extends Spatial> newValue) {
            return (StIsEmpty)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StIsClosed
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StIsClosed>,
    Condition {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StIsClosed $value(Field<? extends Spatial> newValue) {
            return (StIsClosed)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StIntersects<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StIntersects<T>>,
    Condition {
    }

    @Pro
    public static interface StIntersection<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StIntersection<T>>,
    Field<T> {
    }

    @Pro
    public static interface StInteriorRingN
    extends UReturnsNullOnNullInput,
    UOperator2<Field<Geometry>, Field<? extends Number>, StInteriorRingN>,
    Field<Geometry> {
        @NotNull
        default public Field<Geometry> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StInteriorRingN $value(Field<Geometry> newValue) {
            return (StInteriorRingN)this.$arg1(newValue);
        }

        @NotNull
        default public Field<? extends Number> $position() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public StInteriorRingN $position(Field<? extends Number> newPosition) {
            return (StInteriorRingN)this.$arg2(newPosition);
        }
    }

    @Pro
    public static interface StGeomFromWKB
    extends UReturnsNullOnNullInput,
    UOperator2<Field<byte[]>, Field<? extends Number>, StGeomFromWKB>,
    Field<Geometry> {
        @NotNull
        default public Field<byte[]> $wkb() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StGeomFromWKB $wkb(Field<byte[]> newWkb) {
            return (StGeomFromWKB)this.$arg1(newWkb);
        }

        @Nullable
        default public Field<? extends Number> $srid() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public StGeomFromWKB $srid(Field<? extends Number> newSrid) {
            return (StGeomFromWKB)this.$arg2(newSrid);
        }
    }

    @Pro
    public static interface StGeomFromText
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<? extends Number>, StGeomFromText>,
    Field<Geometry> {
        @NotNull
        default public Field<String> $text() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StGeomFromText $text(Field<String> newText) {
            return (StGeomFromText)this.$arg1(newText);
        }

        @Nullable
        default public Field<? extends Number> $srid() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public StGeomFromText $srid(Field<? extends Number> newSrid) {
            return (StGeomFromText)this.$arg2(newSrid);
        }
    }

    @Pro
    public static interface StGeometryType
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StGeometryType>,
    Field<String> {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StGeometryType $value(Field<? extends Spatial> newValue) {
            return (StGeometryType)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StGeometryN<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<? extends Number>, StGeometryN<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StGeometryN<T> $value(Field<T> newValue) {
            return (StGeometryN)this.$arg1(newValue);
        }

        @NotNull
        default public Field<? extends Number> $position() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public StGeometryN<T> $position(Field<? extends Number> newPosition) {
            return (StGeometryN)this.$arg2(newPosition);
        }
    }

    @Pro
    public static interface StExteriorRing
    extends UReturnsNullOnNullInput,
    UOperator1<Field<Geometry>, StExteriorRing>,
    Field<Geometry> {
        @NotNull
        default public Field<Geometry> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StExteriorRing $value(Field<Geometry> newValue) {
            return (StExteriorRing)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StEquals<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StEquals<T>>,
    Condition {
    }

    @Pro
    public static interface StEndPoint<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator1<Field<T>, StEndPoint<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StEndPoint<T> $value(Field<T> newValue) {
            return (StEndPoint)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StDistance<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StDistance<T>>,
    Field<Double> {
    }

    @Pro
    public static interface StDisjoint<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StDisjoint<T>>,
    Condition {
    }

    @Pro
    public static interface StDifference<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StDifference<T>>,
    Field<T> {
    }

    @Pro
    public static interface StCrosses
    extends UReturnsNullOnNullInput,
    UOperator2<Field<Geometry>, Field<Geometry>, StCrosses>,
    Condition {
    }

    @Pro
    public static interface StContains<T extends Spatial>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StContains<T>>,
    Condition {
    }

    @Pro
    public static interface StCentroid
    extends UReturnsNullOnNullInput,
    UOperator1<Field<Geometry>, StCentroid>,
    Field<Geometry> {
        @NotNull
        default public Field<Geometry> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StCentroid $value(Field<Geometry> newValue) {
            return (StCentroid)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StAsText
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StAsText>,
    Field<String> {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StAsText $value(Field<? extends Spatial> newValue) {
            return (StAsText)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StAsBinary
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StAsBinary>,
    Field<byte[]> {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StAsBinary $value(Field<? extends Spatial> newValue) {
            return (StAsBinary)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface StArea
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Spatial>, StArea>,
    Field<Double> {
        @NotNull
        default public Field<? extends Spatial> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StArea $value(Field<? extends Spatial> newValue) {
            return (StArea)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface Signal
    extends UOperator2<Field<?>, Field<String>, Signal>,
    Statement {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Signal $value(Field<?> newValue) {
            return (Signal)this.$arg1(newValue);
        }

        @Nullable
        default public Field<String> $messageText() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Signal $messageText(Field<String> newMessageText) {
            return (Signal)this.$arg2(newMessageText);
        }
    }

    @Pro
    public static interface Return
    extends UOperator1<Field<?>, Return>,
    Statement {
        @Nullable
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Return $value(Field<?> newValue) {
            return (Return)this.$arg1(newValue);
        }
    }

    @Pro
    public static interface Execute
    extends UOperator1<Field<String>, Execute>,
    Statement {
        @NotNull
        default public Field<String> $sql() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Execute $sql(Field<String> newSql) {
            return (Execute)this.$arg1(newSql);
        }
    }

    @Pro
    public static interface Goto
    extends UOperator1<org.jooq.Label, Goto>,
    Statement {
        @NotNull
        default public org.jooq.Label $label() {
            return (org.jooq.Label)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Goto $label(org.jooq.Label newLabel) {
            return (Goto)this.$arg1(newLabel);
        }
    }

    public static interface VarSamp
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();

        @CheckReturnValue
        @NotNull
        public VarSamp $field(Field<? extends Number> var1);
    }

    public static interface VarPop
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();

        @CheckReturnValue
        @NotNull
        public VarPop $field(Field<? extends Number> var1);
    }

    public static interface Sum
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();

        public boolean $distinct();

        @CheckReturnValue
        @NotNull
        public Sum $field(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public Sum $distinct(boolean var1);
    }

    public static interface StddevSamp
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();

        @CheckReturnValue
        @NotNull
        public StddevSamp $field(Field<? extends Number> var1);
    }

    public static interface StddevPop
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();

        @CheckReturnValue
        @NotNull
        public StddevPop $field(Field<? extends Number> var1);
    }

    public static interface RegrSyy
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrSyy $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrSyy $x(Field<? extends Number> var1);
    }

    public static interface RegrSxy
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrSxy $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrSxy $x(Field<? extends Number> var1);
    }

    public static interface RegrSxx
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrSxx $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrSxx $x(Field<? extends Number> var1);
    }

    public static interface RegrSlope
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrSlope $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrSlope $x(Field<? extends Number> var1);
    }

    public static interface RegrR2
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrR2 $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrR2 $x(Field<? extends Number> var1);
    }

    public static interface RegrIntercept
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrIntercept $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrIntercept $x(Field<? extends Number> var1);
    }

    public static interface RegrCount
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrCount $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrCount $x(Field<? extends Number> var1);
    }

    public static interface RegrAvgY
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrAvgY $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrAvgY $x(Field<? extends Number> var1);
    }

    public static interface RegrAvgX
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public RegrAvgX $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public RegrAvgX $x(Field<? extends Number> var1);
    }

    public static interface Product
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();

        public boolean $distinct();

        @CheckReturnValue
        @NotNull
        public Product $field(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public Product $distinct(boolean var1);
    }

    public static interface Min<T>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $field();

        public boolean $distinct();

        @CheckReturnValue
        @NotNull
        public Min<T> $field(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public Min<T> $distinct(boolean var1);
    }

    public static interface Median
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();

        @CheckReturnValue
        @NotNull
        public Median $field(Field<? extends Number> var1);
    }

    public static interface Max<T>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $field();

        public boolean $distinct();

        @CheckReturnValue
        @NotNull
        public Max<T> $field(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public Max<T> $distinct(boolean var1);
    }

    public static interface CovarPop
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public CovarPop $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public CovarPop $x(Field<? extends Number> var1);
    }

    public static interface CovarSamp
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public CovarSamp $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public CovarSamp $x(Field<? extends Number> var1);
    }

    public static interface Count
    extends AggregateFunction<Integer> {
        @NotNull
        public Field<?> $field();

        public boolean $distinct();

        @CheckReturnValue
        @NotNull
        public Count $field(Field<?> var1);

        @CheckReturnValue
        @NotNull
        public Count $distinct(boolean var1);
    }

    public static interface Corr
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $y();

        @NotNull
        public Field<? extends Number> $x();

        @CheckReturnValue
        @NotNull
        public Corr $y(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public Corr $x(Field<? extends Number> var1);
    }

    public static interface BoolOr
    extends AggregateFunction<Boolean> {
        @NotNull
        public Condition $condition();

        @CheckReturnValue
        @NotNull
        public BoolOr $condition(Condition var1);
    }

    public static interface BoolAnd
    extends AggregateFunction<Boolean> {
        @NotNull
        public Condition $condition();

        @CheckReturnValue
        @NotNull
        public BoolAnd $condition(Condition var1);
    }

    public static interface BitXNorAgg<T extends Number>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $value();

        @CheckReturnValue
        @NotNull
        public BitXNorAgg<T> $value(Field<T> var1);
    }

    public static interface BitNorAgg<T extends Number>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $value();

        @CheckReturnValue
        @NotNull
        public BitNorAgg<T> $value(Field<T> var1);
    }

    public static interface BitNandAgg<T extends Number>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $value();

        @CheckReturnValue
        @NotNull
        public BitNandAgg<T> $value(Field<T> var1);
    }

    public static interface BitXorAgg<T extends Number>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $value();

        @CheckReturnValue
        @NotNull
        public BitXorAgg<T> $value(Field<T> var1);
    }

    public static interface BitOrAgg<T extends Number>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $value();

        @CheckReturnValue
        @NotNull
        public BitOrAgg<T> $value(Field<T> var1);
    }

    public static interface BitAndAgg<T extends Number>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $value();

        @CheckReturnValue
        @NotNull
        public BitAndAgg<T> $value(Field<T> var1);
    }

    public static interface Avg
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();

        public boolean $distinct();

        @CheckReturnValue
        @NotNull
        public Avg $field(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public Avg $distinct(boolean var1);
    }

    public static interface AnyValue<T>
    extends AggregateFunction<T> {
        @NotNull
        public Field<T> $field();

        @CheckReturnValue
        @NotNull
        public AnyValue<T> $field(Field<T> var1);
    }

    public static interface FieldCondition
    extends UReturnsNullOnNullInput,
    UOperator1<Field<Boolean>, FieldCondition>,
    Condition {
        @NotNull
        default public Field<Boolean> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public FieldCondition $field(Field<Boolean> newField) {
            return (FieldCondition)this.$arg1(newField);
        }
    }

    public static interface ConditionAsField
    extends UReturnsNullOnNullInput,
    UOperator1<Condition, ConditionAsField>,
    Field<Boolean> {
        @NotNull
        default public Condition $condition() {
            return (Condition)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public ConditionAsField $condition(Condition newCondition) {
            return (ConditionAsField)this.$arg1(newCondition);
        }
    }

    @Pro
    public static interface Updating
    extends UOperator0<Updating>,
    Condition {
    }

    @Pro
    public static interface Inserting
    extends UOperator0<Inserting>,
    Condition {
    }

    @Pro
    public static interface Deleting
    extends UOperator0<Deleting>,
    Condition {
    }

    public static interface JSONBRemove
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSONB>, Field<String>, JSONBRemove>,
    Field<JSONB> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBRemove $field(Field<JSONB> newField) {
            return (JSONBRemove)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $path() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBRemove $path(Field<String> newPath) {
            return (JSONBRemove)this.$arg2(newPath);
        }
    }

    public static interface JSONRemove
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSON>, Field<String>, JSONRemove>,
    Field<JSON> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONRemove $field(Field<JSON> newField) {
            return (JSONRemove)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $path() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONRemove $path(Field<String> newPath) {
            return (JSONRemove)this.$arg2(newPath);
        }
    }

    public static interface JSONBReplace
    extends UOperator3<Field<JSONB>, Field<String>, Field<?>, JSONBReplace>,
    Field<JSONB> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBReplace $field(Field<JSONB> newField) {
            return (JSONBReplace)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $path() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBReplace $path(Field<String> newPath) {
            return (JSONBReplace)this.$arg2(newPath);
        }

        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBReplace $value(Field<?> newValue) {
            return (JSONBReplace)this.$arg3(newValue);
        }
    }

    public static interface JSONReplace
    extends UOperator3<Field<JSON>, Field<String>, Field<?>, JSONReplace>,
    Field<JSON> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONReplace $field(Field<JSON> newField) {
            return (JSONReplace)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $path() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONReplace $path(Field<String> newPath) {
            return (JSONReplace)this.$arg2(newPath);
        }

        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public JSONReplace $value(Field<?> newValue) {
            return (JSONReplace)this.$arg3(newValue);
        }
    }

    public static interface JSONBInsert
    extends UOperator3<Field<JSONB>, Field<String>, Field<?>, JSONBInsert>,
    Field<JSONB> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBInsert $field(Field<JSONB> newField) {
            return (JSONBInsert)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $path() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBInsert $path(Field<String> newPath) {
            return (JSONBInsert)this.$arg2(newPath);
        }

        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBInsert $value(Field<?> newValue) {
            return (JSONBInsert)this.$arg3(newValue);
        }
    }

    public static interface JSONInsert
    extends UOperator3<Field<JSON>, Field<String>, Field<?>, JSONInsert>,
    Field<JSON> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONInsert $field(Field<JSON> newField) {
            return (JSONInsert)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $path() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONInsert $path(Field<String> newPath) {
            return (JSONInsert)this.$arg2(newPath);
        }

        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public JSONInsert $value(Field<?> newValue) {
            return (JSONInsert)this.$arg3(newValue);
        }
    }

    public static interface JSONBSet
    extends UOperator3<Field<JSONB>, Field<String>, Field<?>, JSONBSet>,
    Field<JSONB> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBSet $field(Field<JSONB> newField) {
            return (JSONBSet)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $path() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBSet $path(Field<String> newPath) {
            return (JSONBSet)this.$arg2(newPath);
        }

        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBSet $value(Field<?> newValue) {
            return (JSONBSet)this.$arg3(newValue);
        }
    }

    public static interface JSONSet
    extends UOperator3<Field<JSON>, Field<String>, Field<?>, JSONSet>,
    Field<JSON> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONSet $field(Field<JSON> newField) {
            return (JSONSet)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $path() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONSet $path(Field<String> newPath) {
            return (JSONSet)this.$arg2(newPath);
        }

        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public JSONSet $value(Field<?> newValue) {
            return (JSONSet)this.$arg3(newValue);
        }
    }

    public static interface JSONBKeys
    extends UReturnsNullOnNullInput,
    UOperator1<Field<JSONB>, JSONBKeys>,
    Field<JSONB> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBKeys $field(Field<JSONB> newField) {
            return (JSONBKeys)this.$arg1(newField);
        }
    }

    public static interface JSONKeys
    extends UReturnsNullOnNullInput,
    UOperator1<Field<JSON>, JSONKeys>,
    Field<JSON> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONKeys $field(Field<JSON> newField) {
            return (JSONKeys)this.$arg1(newField);
        }
    }

    public static interface JSONBGetAttributeAsText
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSONB>, Field<String>, JSONBGetAttributeAsText>,
    Field<String> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBGetAttributeAsText $field(Field<JSONB> newField) {
            return (JSONBGetAttributeAsText)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $attribute() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBGetAttributeAsText $attribute(Field<String> newAttribute) {
            return (JSONBGetAttributeAsText)this.$arg2(newAttribute);
        }
    }

    public static interface JSONGetAttributeAsText
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSON>, Field<String>, JSONGetAttributeAsText>,
    Field<String> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONGetAttributeAsText $field(Field<JSON> newField) {
            return (JSONGetAttributeAsText)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $attribute() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONGetAttributeAsText $attribute(Field<String> newAttribute) {
            return (JSONGetAttributeAsText)this.$arg2(newAttribute);
        }
    }

    public static interface JSONBGetAttribute
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSONB>, Field<String>, JSONBGetAttribute>,
    Field<JSONB> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBGetAttribute $field(Field<JSONB> newField) {
            return (JSONBGetAttribute)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $attribute() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBGetAttribute $attribute(Field<String> newAttribute) {
            return (JSONBGetAttribute)this.$arg2(newAttribute);
        }
    }

    public static interface JSONGetAttribute
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSON>, Field<String>, JSONGetAttribute>,
    Field<JSON> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONGetAttribute $field(Field<JSON> newField) {
            return (JSONGetAttribute)this.$arg1(newField);
        }

        @NotNull
        default public Field<String> $attribute() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONGetAttribute $attribute(Field<String> newAttribute) {
            return (JSONGetAttribute)this.$arg2(newAttribute);
        }
    }

    public static interface JSONBGetElementAsText
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSONB>, Field<Integer>, JSONBGetElementAsText>,
    Field<String> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBGetElementAsText $field(Field<JSONB> newField) {
            return (JSONBGetElementAsText)this.$arg1(newField);
        }

        @NotNull
        default public Field<Integer> $index() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBGetElementAsText $index(Field<Integer> newIndex) {
            return (JSONBGetElementAsText)this.$arg2(newIndex);
        }
    }

    public static interface JSONGetElementAsText
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSON>, Field<Integer>, JSONGetElementAsText>,
    Field<String> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONGetElementAsText $field(Field<JSON> newField) {
            return (JSONGetElementAsText)this.$arg1(newField);
        }

        @NotNull
        default public Field<Integer> $index() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONGetElementAsText $index(Field<Integer> newIndex) {
            return (JSONGetElementAsText)this.$arg2(newIndex);
        }
    }

    public static interface JSONBGetElement
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSONB>, Field<Integer>, JSONBGetElement>,
    Field<JSONB> {
        @NotNull
        default public Field<JSONB> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBGetElement $field(Field<JSONB> newField) {
            return (JSONBGetElement)this.$arg1(newField);
        }

        @NotNull
        default public Field<Integer> $index() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONBGetElement $index(Field<Integer> newIndex) {
            return (JSONBGetElement)this.$arg2(newIndex);
        }
    }

    public static interface JSONGetElement
    extends UReturnsNullOnNullInput,
    UOperator2<Field<JSON>, Field<Integer>, JSONGetElement>,
    Field<JSON> {
        @NotNull
        default public Field<JSON> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONGetElement $field(Field<JSON> newField) {
            return (JSONGetElement)this.$arg1(newField);
        }

        @NotNull
        default public Field<Integer> $index() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONGetElement $index(Field<Integer> newIndex) {
            return (JSONGetElement)this.$arg2(newIndex);
        }
    }

    public static interface JSONObject<T>
    extends UOperator4<DataType<T>, UnmodifiableList<? extends JSONEntry<?>>, JSONOnNull, DataType<?>, JSONObject<T>>,
    Field<T> {
        @NotNull
        default public DataType<T> $type() {
            return (DataType)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONObject<T> $type(DataType<T> newType) {
            return (JSONObject)this.$arg1(newType);
        }

        @NotNull
        default public UnmodifiableList<? extends JSONEntry<?>> $entries() {
            return (UnmodifiableList)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONObject<T> $entries(UnmodifiableList<? extends JSONEntry<?>> newEntries) {
            return (JSONObject)this.$arg2(newEntries);
        }

        @Nullable
        default public JSONOnNull $onNull() {
            return (JSONOnNull)((Object)this.$arg3());
        }

        @CheckReturnValue
        @NotNull
        default public JSONObject<T> $onNull(JSONOnNull newOnNull) {
            return (JSONObject)this.$arg3(newOnNull);
        }

        @Nullable
        default public DataType<?> $returning() {
            return (DataType)this.$arg4();
        }

        @CheckReturnValue
        @NotNull
        default public JSONObject<T> $returning(DataType<?> newReturning) {
            return (JSONObject)this.$arg4(newReturning);
        }
    }

    public static interface JSONArray<T>
    extends UOperator4<DataType<T>, UnmodifiableList<? extends Field<?>>, JSONOnNull, DataType<?>, JSONArray<T>>,
    Field<T> {
        @NotNull
        default public DataType<T> $type() {
            return (DataType)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public JSONArray<T> $type(DataType<T> newType) {
            return (JSONArray)this.$arg1(newType);
        }

        @NotNull
        default public UnmodifiableList<? extends Field<?>> $fields() {
            return (UnmodifiableList)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public JSONArray<T> $fields(UnmodifiableList<? extends Field<?>> newFields) {
            return (JSONArray)this.$arg2(newFields);
        }

        @Nullable
        default public JSONOnNull $onNull() {
            return (JSONOnNull)((Object)this.$arg3());
        }

        @CheckReturnValue
        @NotNull
        default public JSONArray<T> $onNull(JSONOnNull newOnNull) {
            return (JSONArray)this.$arg3(newOnNull);
        }

        @Nullable
        default public DataType<?> $returning() {
            return (DataType)this.$arg4();
        }

        @CheckReturnValue
        @NotNull
        default public JSONArray<T> $returning(DataType<?> newReturning) {
            return (JSONArray)this.$arg4(newReturning);
        }
    }

    public static interface XMLSerialize<T>
    extends UOperator3<Boolean, Field<XML>, DataType<T>, XMLSerialize<T>>,
    Field<T> {
        @NotNull
        default public Boolean $content() {
            return (Boolean)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public XMLSerialize<T> $content(Boolean newContent) {
            return (XMLSerialize)this.$arg1(newContent);
        }

        @NotNull
        default public Field<XML> $value() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public XMLSerialize<T> $value(Field<XML> newValue) {
            return (XMLSerialize)this.$arg2(newValue);
        }

        @NotNull
        default public DataType<T> $type() {
            return (DataType)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public XMLSerialize<T> $type(DataType<T> newType) {
            return (XMLSerialize)this.$arg3(newType);
        }
    }

    public static interface XMLPi
    extends UOperator2<Name, Field<?>, XMLPi>,
    Field<XML> {
        @NotNull
        default public Name $target() {
            return (Name)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public XMLPi $target(Name newTarget) {
            return (XMLPi)this.$arg1(newTarget);
        }

        @Nullable
        default public Field<?> $content() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public XMLPi $content(Field<?> newContent) {
            return (XMLPi)this.$arg2(newContent);
        }
    }

    public static interface XMLForest
    extends UOperator1<UnmodifiableList<? extends Field<?>>, XMLForest>,
    Field<XML> {
        @NotNull
        default public UnmodifiableList<? extends Field<?>> $fields() {
            return (UnmodifiableList)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public XMLForest $fields(UnmodifiableList<? extends Field<?>> newFields) {
            return (XMLForest)this.$arg1(newFields);
        }
    }

    @Pro
    public static interface XMLDocument
    extends UOperator1<Field<XML>, XMLDocument>,
    Field<XML> {
        @NotNull
        default public Field<XML> $content() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public XMLDocument $content(Field<XML> newContent) {
            return (XMLDocument)this.$arg1(newContent);
        }
    }

    public static interface XMLConcat
    extends UOperator1<UnmodifiableList<? extends Field<?>>, XMLConcat>,
    Field<XML> {
    }

    public static interface XMLComment
    extends UOperator1<Field<String>, XMLComment>,
    Field<XML> {
        @NotNull
        default public Field<String> $comment() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public XMLComment $comment(Field<String> newComment) {
            return (XMLComment)this.$arg1(newComment);
        }
    }

    @Pro
    public static interface PlusJoin<T>
    extends UOperator1<Field<T>, PlusJoin<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public PlusJoin<T> $field(Field<T> newField) {
            return (PlusJoin)this.$arg1(newField);
        }
    }

    @Pro
    public static interface Rownum
    extends UOperator0<Rownum>,
    Field<Integer> {
    }

    @Pro
    public static interface SysConnectByPath
    extends UOperator2<Field<?>, String, SysConnectByPath>,
    Field<String> {
        @NotNull
        default public Field<?> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public SysConnectByPath $field(Field<?> newField) {
            return (SysConnectByPath)this.$arg1(newField);
        }

        @NotNull
        default public String $separator() {
            return (String)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public SysConnectByPath $separator(String newSeparator) {
            return (SysConnectByPath)this.$arg2(newSeparator);
        }
    }

    @Pro
    public static interface Prior<T>
    extends UOperator1<Field<T>, Prior<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Prior<T> $field(Field<T> newField) {
            return (Prior)this.$arg1(newField);
        }
    }

    @Pro
    public static interface Level
    extends UOperator0<Level>,
    Field<Integer> {
    }

    @Pro
    public static interface ConnectByRoot<T>
    extends UOperator1<Field<T>, ConnectByRoot<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public ConnectByRoot<T> $field(Field<T> newField) {
            return (ConnectByRoot)this.$arg1(newField);
        }
    }

    @Pro
    public static interface ConnectByIsLeaf
    extends UOperator0<ConnectByIsLeaf>,
    Field<Boolean> {
    }

    @Pro
    public static interface ConnectByIsCycle
    extends UOperator0<ConnectByIsCycle>,
    Field<Boolean> {
    }

    public static interface CurrentUser
    extends UOperator0<CurrentUser>,
    Field<String> {
    }

    public static interface CurrentSchema
    extends UOperator0<CurrentSchema>,
    Field<String> {
    }

    public static interface CurrentCatalog
    extends UOperator0<CurrentCatalog>,
    Field<String> {
    }

    public static interface Nullif<T>
    extends UOperator2<Field<T>, Field<T>, Nullif<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Nullif<T> $value(Field<T> newValue) {
            return (Nullif)this.$arg1(newValue);
        }

        @NotNull
        default public Field<T> $other() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Nullif<T> $other(Field<T> newOther) {
            return (Nullif)this.$arg2(newOther);
        }
    }

    public static interface Nvl<T>
    extends UOperator2<Field<T>, Field<T>, Nvl<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Nvl<T> $value(Field<T> newValue) {
            return (Nvl)this.$arg1(newValue);
        }

        @NotNull
        default public Field<T> $defaultValue() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Nvl<T> $defaultValue(Field<T> newDefaultValue) {
            return (Nvl)this.$arg2(newDefaultValue);
        }
    }

    public static interface ArrayReplace<T>
    extends UOperator3<Field<T[]>, Field<T>, Field<T>, ArrayReplace<T>>,
    Field<T[]> {
    }

    public static interface ArrayRemove<T>
    extends UOperator2<Field<T[]>, Field<T>, ArrayRemove<T>>,
    Field<T[]> {
    }

    public static interface ArrayOverlap<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T[]>, Field<T[]>, ArrayOverlap<T>>,
    Condition {
    }

    public static interface ArrayPrepend<T>
    extends UOperator2<Field<T>, Field<T[]>, ArrayPrepend<T>>,
    Field<T[]> {
    }

    public static interface ArrayAppend<T>
    extends UOperator2<Field<T[]>, Field<T>, ArrayAppend<T>>,
    Field<T[]> {
    }

    public static interface ArrayConcat<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T[]>, Field<T[]>, ArrayConcat<T>>,
    Field<T[]> {
    }

    public static interface ArrayGet<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T[]>, Field<Integer>, ArrayGet<T>>,
    Field<T> {
        @NotNull
        default public Field<T[]> $array() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public ArrayGet<T> $array(Field<T[]> newArray) {
            return (ArrayGet)this.$arg1(newArray);
        }

        @NotNull
        default public Field<Integer> $index() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public ArrayGet<T> $index(Field<Integer> newIndex) {
            return (ArrayGet)this.$arg2(newIndex);
        }
    }

    public static interface Cardinality
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Object[]>, Cardinality>,
    Field<Integer> {
        @NotNull
        default public Field<? extends Object[]> $array() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Cardinality $array(Field<? extends Object[]> newArray) {
            return (Cardinality)this.$arg1(newArray);
        }
    }

    public static interface DateAdd<T>
    extends UReturnsNullOnNullInput,
    UOperator3<Field<T>, Field<? extends Number>, DatePart, DateAdd<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $date() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public DateAdd<T> $date(Field<T> newDate) {
            return (DateAdd)this.$arg1(newDate);
        }

        @NotNull
        default public Field<? extends Number> $interval() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public DateAdd<T> $interval(Field<? extends Number> newInterval) {
            return (DateAdd)this.$arg2(newInterval);
        }

        @Nullable
        default public DatePart $datePart() {
            return (DatePart)((Object)this.$arg3());
        }

        @CheckReturnValue
        @NotNull
        default public DateAdd<T> $datePart(DatePart newDatePart) {
            return (DateAdd)this.$arg3(newDatePart);
        }
    }

    public static interface Uuid
    extends UOperator0<Uuid>,
    Field<UUID> {
    }

    public static interface Upper
    extends UReturnsNullOnNullInput,
    UOperator1<Field<String>, Upper>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Upper $string(Field<String> newString) {
            return (Upper)this.$arg1(newString);
        }
    }

    public static interface Trim
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<String>, Trim>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Trim $string(Field<String> newString) {
            return (Trim)this.$arg1(newString);
        }

        @Nullable
        default public Field<String> $characters() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Trim $characters(Field<String> newCharacters) {
            return (Trim)this.$arg2(newCharacters);
        }
    }

    public static interface Translate
    extends UReturnsNullOnNullInput,
    UOperator3<Field<String>, Field<String>, Field<String>, Translate>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Translate $string(Field<String> newString) {
            return (Translate)this.$arg1(newString);
        }

        @NotNull
        default public Field<String> $from() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Translate $from(Field<String> newFrom) {
            return (Translate)this.$arg2(newFrom);
        }

        @NotNull
        default public Field<String> $to() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Translate $to(Field<String> newTo) {
            return (Translate)this.$arg3(newTo);
        }
    }

    public static interface ToTimestamp
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<String>, ToTimestamp>,
    Field<Timestamp> {
        @NotNull
        default public Field<String> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public ToTimestamp $value(Field<String> newValue) {
            return (ToTimestamp)this.$arg1(newValue);
        }

        @NotNull
        default public Field<String> $formatMask() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public ToTimestamp $formatMask(Field<String> newFormatMask) {
            return (ToTimestamp)this.$arg2(newFormatMask);
        }
    }

    public static interface ToHex
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, ToHex>,
    Field<String> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public ToHex $value(Field<? extends Number> newValue) {
            return (ToHex)this.$arg1(newValue);
        }
    }

    public static interface ToDate
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<String>, ToDate>,
    Field<Date> {
        @NotNull
        default public Field<String> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public ToDate $value(Field<String> newValue) {
            return (ToDate)this.$arg1(newValue);
        }

        @NotNull
        default public Field<String> $formatMask() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public ToDate $formatMask(Field<String> newFormatMask) {
            return (ToDate)this.$arg2(newFormatMask);
        }
    }

    public static interface ToChar
    extends UReturnsNullOnNullInput,
    UOperator2<Field<?>, Field<String>, ToChar>,
    Field<String> {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public ToChar $value(Field<?> newValue) {
            return (ToChar)this.$arg1(newValue);
        }

        @Nullable
        default public Field<String> $formatMask() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public ToChar $formatMask(Field<String> newFormatMask) {
            return (ToChar)this.$arg2(newFormatMask);
        }
    }

    public static interface SubstringIndex
    extends UReturnsNullOnNullInput,
    UOperator3<Field<String>, Field<String>, Field<? extends Number>, SubstringIndex>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public SubstringIndex $string(Field<String> newString) {
            return (SubstringIndex)this.$arg1(newString);
        }

        @NotNull
        default public Field<String> $delimiter() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public SubstringIndex $delimiter(Field<String> newDelimiter) {
            return (SubstringIndex)this.$arg2(newDelimiter);
        }

        @NotNull
        default public Field<? extends Number> $n() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public SubstringIndex $n(Field<? extends Number> newN) {
            return (SubstringIndex)this.$arg3(newN);
        }
    }

    public static interface Substring
    extends UReturnsNullOnNullInput,
    UOperator3<Field<String>, Field<? extends Number>, Field<? extends Number>, Substring>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Substring $string(Field<String> newString) {
            return (Substring)this.$arg1(newString);
        }

        @NotNull
        default public Field<? extends Number> $startingPosition() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Substring $startingPosition(Field<? extends Number> newStartingPosition) {
            return (Substring)this.$arg2(newStartingPosition);
        }

        @Nullable
        default public Field<? extends Number> $length() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Substring $length(Field<? extends Number> newLength) {
            return (Substring)this.$arg3(newLength);
        }
    }

    public static interface StartsWithIgnoreCase<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StartsWithIgnoreCase<T>>,
    CompareCondition<T, StartsWithIgnoreCase<T>> {
        @NotNull
        default public Field<T> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StartsWithIgnoreCase<T> $string(Field<T> newString) {
            return (StartsWithIgnoreCase)this.$arg1(newString);
        }

        @NotNull
        default public Field<T> $prefix() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public StartsWithIgnoreCase<T> $prefix(Field<T> newPrefix) {
            return (StartsWithIgnoreCase)this.$arg2(newPrefix);
        }
    }

    public static interface StartsWith<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, StartsWith<T>>,
    CompareCondition<T, StartsWith<T>> {
        @NotNull
        default public Field<T> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public StartsWith<T> $string(Field<T> newString) {
            return (StartsWith)this.$arg1(newString);
        }

        @NotNull
        default public Field<T> $prefix() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public StartsWith<T> $prefix(Field<T> newPrefix) {
            return (StartsWith)this.$arg2(newPrefix);
        }
    }

    public static interface SplitPart
    extends UReturnsNullOnNullInput,
    UOperator3<Field<String>, Field<String>, Field<? extends Number>, SplitPart>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public SplitPart $string(Field<String> newString) {
            return (SplitPart)this.$arg1(newString);
        }

        @NotNull
        default public Field<String> $delimiter() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public SplitPart $delimiter(Field<String> newDelimiter) {
            return (SplitPart)this.$arg2(newDelimiter);
        }

        @NotNull
        default public Field<? extends Number> $n() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public SplitPart $n(Field<? extends Number> newN) {
            return (SplitPart)this.$arg3(newN);
        }
    }

    public static interface Space
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Space>,
    Field<String> {
        @NotNull
        default public Field<? extends Number> $count() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Space $count(Field<? extends Number> newCount) {
            return (Space)this.$arg1(newCount);
        }
    }

    public static interface Rtrim
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<String>, Rtrim>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Rtrim $string(Field<String> newString) {
            return (Rtrim)this.$arg1(newString);
        }

        @Nullable
        default public Field<String> $characters() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Rtrim $characters(Field<String> newCharacters) {
            return (Rtrim)this.$arg2(newCharacters);
        }
    }

    public static interface Rpad
    extends UReturnsNullOnNullInput,
    UOperator3<Field<String>, Field<? extends Number>, Field<String>, Rpad>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Rpad $string(Field<String> newString) {
            return (Rpad)this.$arg1(newString);
        }

        @NotNull
        default public Field<? extends Number> $length() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Rpad $length(Field<? extends Number> newLength) {
            return (Rpad)this.$arg2(newLength);
        }

        @Nullable
        default public Field<String> $character() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Rpad $character(Field<String> newCharacter) {
            return (Rpad)this.$arg3(newCharacter);
        }
    }

    public static interface Right
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<? extends Number>, Right>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Right $string(Field<String> newString) {
            return (Right)this.$arg1(newString);
        }

        @NotNull
        default public Field<? extends Number> $length() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Right $length(Field<? extends Number> newLength) {
            return (Right)this.$arg2(newLength);
        }
    }

    public static interface Reverse
    extends UReturnsNullOnNullInput,
    UOperator1<Field<String>, Reverse>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Reverse $string(Field<String> newString) {
            return (Reverse)this.$arg1(newString);
        }
    }

    public static interface Replace
    extends UReturnsNullOnNullInput,
    UOperator3<Field<String>, Field<String>, Field<String>, Replace>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Replace $string(Field<String> newString) {
            return (Replace)this.$arg1(newString);
        }

        @NotNull
        default public Field<String> $search() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Replace $search(Field<String> newSearch) {
            return (Replace)this.$arg2(newSearch);
        }

        @Nullable
        default public Field<String> $replace() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Replace $replace(Field<String> newReplace) {
            return (Replace)this.$arg3(newReplace);
        }
    }

    public static interface Repeat
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<? extends Number>, Repeat>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Repeat $string(Field<String> newString) {
            return (Repeat)this.$arg1(newString);
        }

        @NotNull
        default public Field<? extends Number> $count() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Repeat $count(Field<? extends Number> newCount) {
            return (Repeat)this.$arg2(newCount);
        }
    }

    public static interface Position
    extends UReturnsNullOnNullInput,
    UOperator3<Field<String>, Field<String>, Field<? extends Number>, Position>,
    Field<Integer> {
        @NotNull
        default public Field<String> $in() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Position $in(Field<String> newIn) {
            return (Position)this.$arg1(newIn);
        }

        @NotNull
        default public Field<String> $search() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Position $search(Field<String> newSearch) {
            return (Position)this.$arg2(newSearch);
        }

        @Nullable
        default public Field<? extends Number> $startIndex() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Position $startIndex(Field<? extends Number> newStartIndex) {
            return (Position)this.$arg3(newStartIndex);
        }
    }

    public static interface Overlay
    extends UReturnsNullOnNullInput,
    UOperator4<Field<String>, Field<String>, Field<? extends Number>, Field<? extends Number>, Overlay>,
    Field<String> {
        @NotNull
        default public Field<String> $in() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Overlay $in(Field<String> newIn) {
            return (Overlay)this.$arg1(newIn);
        }

        @NotNull
        default public Field<String> $placing() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Overlay $placing(Field<String> newPlacing) {
            return (Overlay)this.$arg2(newPlacing);
        }

        @NotNull
        default public Field<? extends Number> $startIndex() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Overlay $startIndex(Field<? extends Number> newStartIndex) {
            return (Overlay)this.$arg3(newStartIndex);
        }

        @Nullable
        default public Field<? extends Number> $length() {
            return (Field)this.$arg4();
        }

        @CheckReturnValue
        @NotNull
        default public Overlay $length(Field<? extends Number> newLength) {
            return (Overlay)this.$arg4(newLength);
        }
    }

    public static interface OctetLength
    extends UReturnsNullOnNullInput,
    UOperator1<Field<String>, OctetLength>,
    Field<Integer> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public OctetLength $string(Field<String> newString) {
            return (OctetLength)this.$arg1(newString);
        }
    }

    public static interface Md5
    extends UReturnsNullOnNullInput,
    UOperator1<Field<String>, Md5>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Md5 $string(Field<String> newString) {
            return (Md5)this.$arg1(newString);
        }
    }

    public static interface Ltrim
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<String>, Ltrim>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Ltrim $string(Field<String> newString) {
            return (Ltrim)this.$arg1(newString);
        }

        @Nullable
        default public Field<String> $characters() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Ltrim $characters(Field<String> newCharacters) {
            return (Ltrim)this.$arg2(newCharacters);
        }
    }

    public static interface Lpad
    extends UReturnsNullOnNullInput,
    UOperator3<Field<String>, Field<? extends Number>, Field<String>, Lpad>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Lpad $string(Field<String> newString) {
            return (Lpad)this.$arg1(newString);
        }

        @NotNull
        default public Field<? extends Number> $length() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Lpad $length(Field<? extends Number> newLength) {
            return (Lpad)this.$arg2(newLength);
        }

        @Nullable
        default public Field<String> $character() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Lpad $character(Field<String> newCharacter) {
            return (Lpad)this.$arg3(newCharacter);
        }
    }

    public static interface Lower
    extends UReturnsNullOnNullInput,
    UOperator1<Field<String>, Lower>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Lower $string(Field<String> newString) {
            return (Lower)this.$arg1(newString);
        }
    }

    public static interface Left
    extends UReturnsNullOnNullInput,
    UOperator2<Field<String>, Field<? extends Number>, Left>,
    Field<String> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Left $string(Field<String> newString) {
            return (Left)this.$arg1(newString);
        }

        @NotNull
        default public Field<? extends Number> $length() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Left $length(Field<? extends Number> newLength) {
            return (Left)this.$arg2(newLength);
        }
    }

    public static interface EndsWithIgnoreCase<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, EndsWithIgnoreCase<T>>,
    CompareCondition<T, EndsWithIgnoreCase<T>> {
        @NotNull
        default public Field<T> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public EndsWithIgnoreCase<T> $string(Field<T> newString) {
            return (EndsWithIgnoreCase)this.$arg1(newString);
        }

        @NotNull
        default public Field<T> $suffix() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public EndsWithIgnoreCase<T> $suffix(Field<T> newSuffix) {
            return (EndsWithIgnoreCase)this.$arg2(newSuffix);
        }
    }

    public static interface EndsWith<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, EndsWith<T>>,
    CompareCondition<T, EndsWith<T>> {
        @NotNull
        default public Field<T> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public EndsWith<T> $string(Field<T> newString) {
            return (EndsWith)this.$arg1(newString);
        }

        @NotNull
        default public Field<T> $suffix() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public EndsWith<T> $suffix(Field<T> newSuffix) {
            return (EndsWith)this.$arg2(newSuffix);
        }
    }

    public static interface Digits
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Digits>,
    Field<String> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Digits $value(Field<? extends Number> newValue) {
            return (Digits)this.$arg1(newValue);
        }
    }

    public static interface ContainsIgnoreCase<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, ContainsIgnoreCase<T>>,
    CompareCondition<T, ContainsIgnoreCase<T>> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public ContainsIgnoreCase<T> $value(Field<T> newValue) {
            return (ContainsIgnoreCase)this.$arg1(newValue);
        }

        @NotNull
        default public Field<T> $content() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public ContainsIgnoreCase<T> $content(Field<T> newContent) {
            return (ContainsIgnoreCase)this.$arg2(newContent);
        }
    }

    public static interface Contains<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, Contains<T>>,
    CompareCondition<T, Contains<T>> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Contains<T> $value(Field<T> newValue) {
            return (Contains)this.$arg1(newValue);
        }

        @NotNull
        default public Field<T> $content() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Contains<T> $content(Field<T> newContent) {
            return (Contains)this.$arg2(newContent);
        }
    }

    public static interface Chr
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Chr>,
    Field<String> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Chr $value(Field<? extends Number> newValue) {
            return (Chr)this.$arg1(newValue);
        }
    }

    public static interface CharLength
    extends UReturnsNullOnNullInput,
    UOperator1<Field<String>, CharLength>,
    Field<Integer> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public CharLength $string(Field<String> newString) {
            return (CharLength)this.$arg1(newString);
        }
    }

    public static interface BitLength
    extends UReturnsNullOnNullInput,
    UOperator1<Field<String>, BitLength>,
    Field<Integer> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public BitLength $string(Field<String> newString) {
            return (BitLength)this.$arg1(newString);
        }
    }

    public static interface Ascii
    extends UReturnsNullOnNullInput,
    UOperator1<Field<String>, Ascii>,
    Field<Integer> {
        @NotNull
        default public Field<String> $string() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Ascii $string(Field<String> newString) {
            return (Ascii)this.$arg1(newString);
        }
    }

    public static interface WidthBucket<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator4<Field<T>, Field<T>, Field<T>, Field<Integer>, WidthBucket<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public WidthBucket<T> $field(Field<T> newField) {
            return (WidthBucket)this.$arg1(newField);
        }

        @NotNull
        default public Field<T> $low() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public WidthBucket<T> $low(Field<T> newLow) {
            return (WidthBucket)this.$arg2(newLow);
        }

        @NotNull
        default public Field<T> $high() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public WidthBucket<T> $high(Field<T> newHigh) {
            return (WidthBucket)this.$arg3(newHigh);
        }

        @NotNull
        default public Field<Integer> $buckets() {
            return (Field)this.$arg4();
        }

        @CheckReturnValue
        @NotNull
        default public WidthBucket<T> $buckets(Field<Integer> newBuckets) {
            return (WidthBucket)this.$arg4(newBuckets);
        }
    }

    public static interface Trunc<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<Integer>, Trunc<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Trunc<T> $value(Field<T> newValue) {
            return (Trunc)this.$arg1(newValue);
        }

        @NotNull
        default public Field<Integer> $decimals() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Trunc<T> $decimals(Field<Integer> newDecimals) {
            return (Trunc)this.$arg2(newDecimals);
        }
    }

    public static interface Tau
    extends UOperator0<Tau>,
    Field<BigDecimal> {
    }

    public static interface Tanh
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Tanh>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Tanh $value(Field<? extends Number> newValue) {
            return (Tanh)this.$arg1(newValue);
        }
    }

    public static interface Tan
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Tan>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Tan $value(Field<? extends Number> newValue) {
            return (Tan)this.$arg1(newValue);
        }
    }

    public static interface Sub<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, Sub<T>>,
    Field<T> {
    }

    public static interface Square<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator1<Field<T>, Square<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Square<T> $value(Field<T> newValue) {
            return (Square)this.$arg1(newValue);
        }
    }

    public static interface Sqrt
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Sqrt>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Sqrt $value(Field<? extends Number> newValue) {
            return (Sqrt)this.$arg1(newValue);
        }
    }

    public static interface Sinh
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Sinh>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Sinh $value(Field<? extends Number> newValue) {
            return (Sinh)this.$arg1(newValue);
        }
    }

    public static interface Sin
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Sin>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Sin $value(Field<? extends Number> newValue) {
            return (Sin)this.$arg1(newValue);
        }
    }

    public static interface Sign
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Sign>,
    Field<Integer> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Sign $value(Field<? extends Number> newValue) {
            return (Sign)this.$arg1(newValue);
        }
    }

    public static interface Shr<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<? extends Number>, Shr<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Shr<T> $value(Field<T> newValue) {
            return (Shr)this.$arg1(newValue);
        }

        @NotNull
        default public Field<? extends Number> $count() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Shr<T> $count(Field<? extends Number> newCount) {
            return (Shr)this.$arg2(newCount);
        }
    }

    public static interface Shl<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<? extends Number>, Shl<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Shl<T> $value(Field<T> newValue) {
            return (Shl)this.$arg1(newValue);
        }

        @NotNull
        default public Field<? extends Number> $count() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Shl<T> $count(Field<? extends Number> newCount) {
            return (Shl)this.$arg2(newCount);
        }
    }

    public static interface Round<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<Integer>, Round<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Round<T> $value(Field<T> newValue) {
            return (Round)this.$arg1(newValue);
        }

        @Nullable
        default public Field<Integer> $decimals() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Round<T> $decimals(Field<Integer> newDecimals) {
            return (Round)this.$arg2(newDecimals);
        }
    }

    public static interface Rand
    extends UOperator0<Rand>,
    Field<BigDecimal> {
    }

    public static interface Radians
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Radians>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $degrees() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Radians $degrees(Field<? extends Number> newDegrees) {
            return (Radians)this.$arg1(newDegrees);
        }
    }

    public static interface Power
    extends UReturnsNullOnNullInput,
    UOperator2<Field<? extends Number>, Field<? extends Number>, Power>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $base() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Power $base(Field<? extends Number> newBase) {
            return (Power)this.$arg1(newBase);
        }

        @NotNull
        default public Field<? extends Number> $exponent() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Power $exponent(Field<? extends Number> newExponent) {
            return (Power)this.$arg2(newExponent);
        }
    }

    public static interface Pi
    extends UOperator0<Pi>,
    Field<BigDecimal> {
    }

    public static interface Mul<T>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, Mul<T>>,
    Field<T> {
    }

    public static interface Mod<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<? extends Number>, Mod<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $dividend() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Mod<T> $dividend(Field<T> newDividend) {
            return (Mod)this.$arg1(newDividend);
        }

        @NotNull
        default public Field<? extends Number> $divisor() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Mod<T> $divisor(Field<? extends Number> newDivisor) {
            return (Mod)this.$arg2(newDivisor);
        }
    }

    public static interface Log10
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Log10>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Log10 $value(Field<? extends Number> newValue) {
            return (Log10)this.$arg1(newValue);
        }
    }

    public static interface Log
    extends UReturnsNullOnNullInput,
    UOperator2<Field<? extends Number>, Field<? extends Number>, Log>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Log $value(Field<? extends Number> newValue) {
            return (Log)this.$arg1(newValue);
        }

        @NotNull
        default public Field<? extends Number> $base() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Log $base(Field<? extends Number> newBase) {
            return (Log)this.$arg2(newBase);
        }
    }

    public static interface Ln
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Ln>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Ln $value(Field<? extends Number> newValue) {
            return (Ln)this.$arg1(newValue);
        }
    }

    public static interface Floor<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator1<Field<T>, Floor<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Floor<T> $value(Field<T> newValue) {
            return (Floor)this.$arg1(newValue);
        }
    }

    public static interface Exp
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Exp>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Exp $value(Field<? extends Number> newValue) {
            return (Exp)this.$arg1(newValue);
        }
    }

    public static interface Euler
    extends UOperator0<Euler>,
    Field<BigDecimal> {
    }

    public static interface Div<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<T>, Div<T>>,
    Field<T> {
    }

    public static interface Degrees
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Degrees>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $radians() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Degrees $radians(Field<? extends Number> newRadians) {
            return (Degrees)this.$arg1(newRadians);
        }
    }

    public static interface Coth
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Coth>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Coth $value(Field<? extends Number> newValue) {
            return (Coth)this.$arg1(newValue);
        }
    }

    public static interface Cot
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Cot>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Cot $value(Field<? extends Number> newValue) {
            return (Cot)this.$arg1(newValue);
        }
    }

    public static interface Cosh
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Cosh>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Cosh $value(Field<? extends Number> newValue) {
            return (Cosh)this.$arg1(newValue);
        }
    }

    public static interface Cos
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Cos>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Cos $value(Field<? extends Number> newValue) {
            return (Cos)this.$arg1(newValue);
        }
    }

    public static interface Ceil<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator1<Field<T>, Ceil<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Ceil<T> $value(Field<T> newValue) {
            return (Ceil)this.$arg1(newValue);
        }
    }

    public static interface BitXor<T extends Number>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, BitXor<T>>,
    Field<T> {
    }

    public static interface BitXNor<T extends Number>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, BitXNor<T>>,
    Field<T> {
    }

    public static interface BitSet<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator3<Field<T>, Field<? extends Number>, Field<T>, BitSet<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public BitSet<T> $value(Field<T> newValue) {
            return (BitSet)this.$arg1(newValue);
        }

        @NotNull
        default public Field<? extends Number> $bit() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public BitSet<T> $bit(Field<? extends Number> newBit) {
            return (BitSet)this.$arg2(newBit);
        }

        @Nullable
        default public Field<T> $newValue() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public BitSet<T> $newValue(Field<T> newNewValue) {
            return (BitSet)this.$arg3(newNewValue);
        }
    }

    public static interface BitOr<T extends Number>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, BitOr<T>>,
    Field<T> {
    }

    public static interface BitNot<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator1<Field<T>, BitNot<T>>,
    Field<T> {
    }

    public static interface BitNor<T extends Number>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, BitNor<T>>,
    Field<T> {
    }

    public static interface BitNand<T extends Number>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, BitNand<T>>,
    Field<T> {
    }

    public static interface BitGet<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, Field<? extends Number>, BitGet<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public BitGet<T> $value(Field<T> newValue) {
            return (BitGet)this.$arg1(newValue);
        }

        @NotNull
        default public Field<? extends Number> $bit() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public BitGet<T> $bit(Field<? extends Number> newBit) {
            return (BitGet)this.$arg2(newBit);
        }
    }

    public static interface BitCount
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, BitCount>,
    Field<Integer> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public BitCount $value(Field<? extends Number> newValue) {
            return (BitCount)this.$arg1(newValue);
        }
    }

    public static interface BitAnd<T extends Number>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, BitAnd<T>>,
    Field<T> {
    }

    public static interface Atanh
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Atanh>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Atanh $value(Field<? extends Number> newValue) {
            return (Atanh)this.$arg1(newValue);
        }
    }

    public static interface Atan2
    extends UReturnsNullOnNullInput,
    UOperator2<Field<? extends Number>, Field<? extends Number>, Atan2>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $x() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Atan2 $x(Field<? extends Number> newX) {
            return (Atan2)this.$arg1(newX);
        }

        @NotNull
        default public Field<? extends Number> $y() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Atan2 $y(Field<? extends Number> newY) {
            return (Atan2)this.$arg2(newY);
        }
    }

    public static interface Atan
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Atan>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Atan $value(Field<? extends Number> newValue) {
            return (Atan)this.$arg1(newValue);
        }
    }

    public static interface Asinh
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Asinh>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Asinh $value(Field<? extends Number> newValue) {
            return (Asinh)this.$arg1(newValue);
        }
    }

    public static interface Asin
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Asin>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Asin $value(Field<? extends Number> newValue) {
            return (Asin)this.$arg1(newValue);
        }
    }

    public static interface Add<T>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, Add<T>>,
    Field<T> {
    }

    public static interface Acoth
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Acoth>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Acoth $value(Field<? extends Number> newValue) {
            return (Acoth)this.$arg1(newValue);
        }
    }

    public static interface Acosh
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Acosh>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Acosh $value(Field<? extends Number> newValue) {
            return (Acosh)this.$arg1(newValue);
        }
    }

    public static interface Acos
    extends UReturnsNullOnNullInput,
    UOperator1<Field<? extends Number>, Acos>,
    Field<BigDecimal> {
        @NotNull
        default public Field<? extends Number> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Acos $value(Field<? extends Number> newValue) {
            return (Acos)this.$arg1(newValue);
        }
    }

    public static interface Abs<T extends Number>
    extends UReturnsNullOnNullInput,
    UOperator1<Field<T>, Abs<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Abs<T> $value(Field<T> newValue) {
            return (Abs)this.$arg1(newValue);
        }
    }

    public static interface QualifiedRowid
    extends UOperator1<Table<?>, QualifiedRowid>,
    Field<RowId> {
        @NotNull
        default public Table<?> $table() {
            return (Table)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public QualifiedRowid $table(Table<?> newTable) {
            return (QualifiedRowid)this.$arg1(newTable);
        }
    }

    public static interface Excluded<T>
    extends UOperator1<Field<T>, Excluded<T>>,
    Field<T> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Excluded<T> $field(Field<T> newField) {
            return (Excluded)this.$arg1(newField);
        }
    }

    public static interface IsNotJson
    extends UReturnsNullOnNullInput,
    UOperator1<Field<?>, IsNotJson>,
    Condition {
        @NotNull
        default public Field<?> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public IsNotJson $field(Field<?> newField) {
            return (IsNotJson)this.$arg1(newField);
        }
    }

    public static interface IsJson
    extends UReturnsNullOnNullInput,
    UOperator1<Field<?>, IsJson>,
    Condition {
        @NotNull
        default public Field<?> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public IsJson $field(Field<?> newField) {
            return (IsJson)this.$arg1(newField);
        }
    }

    public static interface IsNotDocument
    extends UReturnsNullOnNullInput,
    UOperator1<Field<?>, IsNotDocument>,
    Condition {
        @NotNull
        default public Field<?> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public IsNotDocument $field(Field<?> newField) {
            return (IsNotDocument)this.$arg1(newField);
        }
    }

    public static interface IsDocument
    extends UReturnsNullOnNullInput,
    UOperator1<Field<?>, IsDocument>,
    Condition {
        @NotNull
        default public Field<?> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public IsDocument $field(Field<?> newField) {
            return (IsDocument)this.$arg1(newField);
        }
    }

    public static interface RowLe<T extends Row>
    extends UReturnsNullOnNullInput,
    UConvertibleOperator<T, RowLe<T>, RowGe<T>>,
    Condition {
        @Override
        default public RowGe<T> $converse() {
            return new org.jooq.impl.RowGe<Row>((Row)this.$arg2(), (Row)this.$arg1());
        }
    }

    public static interface RowLt<T extends Row>
    extends UReturnsNullOnNullInput,
    UConvertibleOperator<T, RowLt<T>, RowGt<T>>,
    Condition {
        @Override
        default public RowGt<T> $converse() {
            return new org.jooq.impl.RowGt<Row>((Row)this.$arg2(), (Row)this.$arg1());
        }
    }

    public static interface RowGe<T extends Row>
    extends UReturnsNullOnNullInput,
    UConvertibleOperator<T, RowGe<T>, RowLe<T>>,
    Condition {
        @Override
        default public RowLe<T> $converse() {
            return new org.jooq.impl.RowLe<Row>((Row)this.$arg2(), (Row)this.$arg1());
        }
    }

    public static interface RowGt<T extends Row>
    extends UReturnsNullOnNullInput,
    UConvertibleOperator<T, RowGt<T>, RowLt<T>>,
    Condition {
        @Override
        default public RowLt<T> $converse() {
            return new org.jooq.impl.RowLt<Row>((Row)this.$arg2(), (Row)this.$arg1());
        }
    }

    public static interface RowNe<T extends Row>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<T, RowNe<T>>,
    Condition {
    }

    public static interface RowEq<T extends Row>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<T, RowEq<T>>,
    Condition {
    }

    public static interface Xor
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Condition, Xor>,
    CombinedCondition<Xor> {
    }

    public static interface Unique
    extends UOperator1<Select<?>, Unique>,
    Condition {
        @NotNull
        default public Select<?> $query() {
            return (Select)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Unique $query(Select<?> newQuery) {
            return (Unique)this.$arg1(newQuery);
        }
    }

    public static interface SimilarToQuantified
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, org.jooq.QuantifiedSelect<? extends Record1<String>>, Character, SimilarToQuantified>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public SimilarToQuantified $value(Field<?> newValue) {
            return (SimilarToQuantified)this.$arg1(newValue);
        }

        @NotNull
        default public org.jooq.QuantifiedSelect<? extends Record1<String>> $pattern() {
            return (org.jooq.QuantifiedSelect)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public SimilarToQuantified $pattern(org.jooq.QuantifiedSelect<? extends Record1<String>> newPattern) {
            return (SimilarToQuantified)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public SimilarToQuantified $escape(Character newEscape) {
            return (SimilarToQuantified)this.$arg3(newEscape);
        }
    }

    public static interface SimilarTo
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, Field<String>, Character, SimilarTo>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public SimilarTo $value(Field<?> newValue) {
            return (SimilarTo)this.$arg1(newValue);
        }

        @NotNull
        default public Field<String> $pattern() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public SimilarTo $pattern(Field<String> newPattern) {
            return (SimilarTo)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public SimilarTo $escape(Character newEscape) {
            return (SimilarTo)this.$arg3(newEscape);
        }
    }

    public static interface NotSimilarToQuantified
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, org.jooq.QuantifiedSelect<? extends Record1<String>>, Character, NotSimilarToQuantified>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public NotSimilarToQuantified $value(Field<?> newValue) {
            return (NotSimilarToQuantified)this.$arg1(newValue);
        }

        @NotNull
        default public org.jooq.QuantifiedSelect<? extends Record1<String>> $pattern() {
            return (org.jooq.QuantifiedSelect)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public NotSimilarToQuantified $pattern(org.jooq.QuantifiedSelect<? extends Record1<String>> newPattern) {
            return (NotSimilarToQuantified)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public NotSimilarToQuantified $escape(Character newEscape) {
            return (NotSimilarToQuantified)this.$arg3(newEscape);
        }
    }

    public static interface NotSimilarTo
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, Field<String>, Character, NotSimilarTo>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public NotSimilarTo $value(Field<?> newValue) {
            return (NotSimilarTo)this.$arg1(newValue);
        }

        @NotNull
        default public Field<String> $pattern() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public NotSimilarTo $pattern(Field<String> newPattern) {
            return (NotSimilarTo)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public NotSimilarTo $escape(Character newEscape) {
            return (NotSimilarTo)this.$arg3(newEscape);
        }
    }

    public static interface NotLikeIgnoreCase
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, Field<String>, Character, NotLikeIgnoreCase>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public NotLikeIgnoreCase $value(Field<?> newValue) {
            return (NotLikeIgnoreCase)this.$arg1(newValue);
        }

        @NotNull
        default public Field<String> $pattern() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public NotLikeIgnoreCase $pattern(Field<String> newPattern) {
            return (NotLikeIgnoreCase)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public NotLikeIgnoreCase $escape(Character newEscape) {
            return (NotLikeIgnoreCase)this.$arg3(newEscape);
        }
    }

    public static interface NotLikeQuantified
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, org.jooq.QuantifiedSelect<? extends Record1<String>>, Character, NotLikeQuantified>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public NotLikeQuantified $value(Field<?> newValue) {
            return (NotLikeQuantified)this.$arg1(newValue);
        }

        @NotNull
        default public org.jooq.QuantifiedSelect<? extends Record1<String>> $pattern() {
            return (org.jooq.QuantifiedSelect)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public NotLikeQuantified $pattern(org.jooq.QuantifiedSelect<? extends Record1<String>> newPattern) {
            return (NotLikeQuantified)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public NotLikeQuantified $escape(Character newEscape) {
            return (NotLikeQuantified)this.$arg3(newEscape);
        }
    }

    public static interface NotLike
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, Field<String>, Character, NotLike>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public NotLike $value(Field<?> newValue) {
            return (NotLike)this.$arg1(newValue);
        }

        @NotNull
        default public Field<String> $pattern() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public NotLike $pattern(Field<String> newPattern) {
            return (NotLike)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public NotLike $escape(Character newEscape) {
            return (NotLike)this.$arg3(newEscape);
        }
    }

    public static interface NotIn<T>
    extends UOperator2<Field<T>, Select<? extends Record1<T>>, NotIn<T>>,
    Condition {
    }

    public static interface NotField
    extends UReturnsNullOnNullInput,
    UOperator1<Field<Boolean>, NotField>,
    Field<Boolean> {
        @NotNull
        default public Field<Boolean> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public NotField $field(Field<Boolean> newField) {
            return (NotField)this.$arg1(newField);
        }
    }

    public static interface Not
    extends UReturnsNullOnNullInput,
    UOperator1<Condition, Not>,
    Condition {
        @NotNull
        default public Condition $condition() {
            return (Condition)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Not $condition(Condition newCondition) {
            return (Not)this.$arg1(newCondition);
        }
    }

    public static interface NeQuantified<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, org.jooq.QuantifiedSelect<? extends Record1<T>>, NeQuantified<T>>,
    Condition {
    }

    public static interface Ne<T>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, Ne<T>>,
    CompareCondition<T, Ne<T>> {
    }

    public static interface TableNe<R extends Record>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Table<R>, TableNe<R>>,
    Condition {
    }

    public static interface LtQuantified<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, org.jooq.QuantifiedSelect<? extends Record1<T>>, LtQuantified<T>>,
    Condition {
    }

    public static interface Lt<T>
    extends UReturnsNullOnNullInput,
    UConvertibleOperator<Field<T>, Lt<T>, Gt<T>>,
    CompareCondition<T, Lt<T>> {
        @Override
        default public Gt<T> $converse() {
            return new org.jooq.impl.Gt((Field)this.$arg2(), (Field)this.$arg1());
        }
    }

    public static interface LikeIgnoreCase
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, Field<String>, Character, LikeIgnoreCase>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public LikeIgnoreCase $value(Field<?> newValue) {
            return (LikeIgnoreCase)this.$arg1(newValue);
        }

        @NotNull
        default public Field<String> $pattern() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public LikeIgnoreCase $pattern(Field<String> newPattern) {
            return (LikeIgnoreCase)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public LikeIgnoreCase $escape(Character newEscape) {
            return (LikeIgnoreCase)this.$arg3(newEscape);
        }
    }

    public static interface LikeQuantified
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, org.jooq.QuantifiedSelect<? extends Record1<String>>, Character, LikeQuantified>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public LikeQuantified $value(Field<?> newValue) {
            return (LikeQuantified)this.$arg1(newValue);
        }

        @NotNull
        default public org.jooq.QuantifiedSelect<? extends Record1<String>> $pattern() {
            return (org.jooq.QuantifiedSelect)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public LikeQuantified $pattern(org.jooq.QuantifiedSelect<? extends Record1<String>> newPattern) {
            return (LikeQuantified)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public LikeQuantified $escape(Character newEscape) {
            return (LikeQuantified)this.$arg3(newEscape);
        }
    }

    public static interface Like
    extends UReturnsNullOnNullInput,
    UOperator3<Field<?>, Field<String>, Character, Like>,
    Condition {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Like $value(Field<?> newValue) {
            return (Like)this.$arg1(newValue);
        }

        @NotNull
        default public Field<String> $pattern() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Like $pattern(Field<String> newPattern) {
            return (Like)this.$arg2(newPattern);
        }

        @Nullable
        default public Character $escape() {
            return (Character)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Like $escape(Character newEscape) {
            return (Like)this.$arg3(newEscape);
        }
    }

    public static interface LeQuantified<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, org.jooq.QuantifiedSelect<? extends Record1<T>>, LeQuantified<T>>,
    Condition {
    }

    public static interface Le<T>
    extends UReturnsNullOnNullInput,
    UConvertibleOperator<Field<T>, Le<T>, Ge<T>>,
    CompareCondition<T, Le<T>> {
        @Override
        default public Ge<T> $converse() {
            return new org.jooq.impl.Ge((Field)this.$arg2(), (Field)this.$arg1());
        }
    }

    public static interface IsNotNull
    extends UOperator1<Field<?>, IsNotNull>,
    Condition {
        @NotNull
        default public Field<?> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public IsNotNull $field(Field<?> newField) {
            return (IsNotNull)this.$arg1(newField);
        }
    }

    public static interface IsNotDistinctFrom<T>
    extends UCommutativeOperator<Field<T>, IsNotDistinctFrom<T>>,
    CompareCondition<T, IsNotDistinctFrom<T>> {
    }

    public static interface IsNull
    extends UOperator1<Field<?>, IsNull>,
    Condition {
        @NotNull
        default public Field<?> $field() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public IsNull $field(Field<?> newField) {
            return (IsNull)this.$arg1(newField);
        }
    }

    public static interface IsDistinctFrom<T>
    extends UCommutativeOperator<Field<T>, IsDistinctFrom<T>>,
    CompareCondition<T, IsDistinctFrom<T>> {
    }

    public static interface In<T>
    extends UOperator2<Field<T>, Select<? extends Record1<T>>, In<T>>,
    Condition {
    }

    public static interface GtQuantified<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, org.jooq.QuantifiedSelect<? extends Record1<T>>, GtQuantified<T>>,
    Condition {
    }

    public static interface Gt<T>
    extends UReturnsNullOnNullInput,
    UConvertibleOperator<Field<T>, Gt<T>, Lt<T>>,
    CompareCondition<T, Gt<T>> {
        @Override
        default public Lt<T> $converse() {
            return new org.jooq.impl.Lt((Field)this.$arg2(), (Field)this.$arg1());
        }
    }

    public static interface GeQuantified<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, org.jooq.QuantifiedSelect<? extends Record1<T>>, GeQuantified<T>>,
    Condition {
    }

    public static interface Ge<T>
    extends UReturnsNullOnNullInput,
    UConvertibleOperator<Field<T>, Ge<T>, Le<T>>,
    CompareCondition<T, Ge<T>> {
        @Override
        default public Le<T> $converse() {
            return new org.jooq.impl.Le((Field)this.$arg2(), (Field)this.$arg1());
        }
    }

    public static interface Exists
    extends UOperator1<Select<?>, Exists>,
    Condition {
        @NotNull
        default public Select<?> $query() {
            return (Select)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Exists $query(Select<?> newQuery) {
            return (Exists)this.$arg1(newQuery);
        }
    }

    public static interface EqQuantified<T>
    extends UReturnsNullOnNullInput,
    UOperator2<Field<T>, org.jooq.QuantifiedSelect<? extends Record1<T>>, EqQuantified<T>>,
    Condition {
    }

    public static interface Eq<T>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Field<T>, Eq<T>>,
    CompareCondition<T, Eq<T>> {
    }

    public static interface TableEq<R extends Record>
    extends UReturnsNullOnNullInput,
    UCommutativeOperator<Table<R>, TableEq<R>>,
    Condition {
    }

    public static interface Rollback
    extends RowCountQuery {
        @Nullable
        public Name $toSavepoint();

        @CheckReturnValue
        @NotNull
        public Rollback $toSavepoint(Name var1);
    }

    public static interface Commit
    extends UEmpty,
    Query {
    }

    public static interface ReleaseSavepoint
    extends Query {
        @NotNull
        public Name $name();

        @CheckReturnValue
        @NotNull
        public ReleaseSavepoint $name(Name var1);
    }

    public static interface Savepoint
    extends Query {
        @NotNull
        public Name $name();

        @CheckReturnValue
        @NotNull
        public Savepoint $name(Name var1);
    }

    public static interface StartTransaction
    extends UEmpty,
    Query {
    }

    @Pro
    public static interface Call
    extends RowCountQuery {
        @NotNull
        public Name $procedure();

        @NotNull
        public UnmodifiableList<? extends Field<?>> $args();

        @CheckReturnValue
        @NotNull
        public Call $procedure(Name var1);

        @CheckReturnValue
        @NotNull
        public Call $args(Collection<? extends Field<?>> var1);
    }

    public static interface Truncate<R extends Record>
    extends DDLQuery {
        @NotNull
        public Table<R> $table();

        @Nullable
        public IdentityRestartOption $restartIdentity();

        @Nullable
        public Cascade $cascade();

        @CheckReturnValue
        @NotNull
        public Truncate<R> $table(Table<R> var1);

        @CheckReturnValue
        @NotNull
        public Truncate<R> $restartIdentity(IdentityRestartOption var1);

        @CheckReturnValue
        @NotNull
        public Truncate<R> $cascade(Cascade var1);
    }

    public static interface SetSchema
    extends RowCountQuery {
        @NotNull
        public Schema $schema();

        @CheckReturnValue
        @NotNull
        public SetSchema $schema(Schema var1);
    }

    public static interface SetCatalog
    extends RowCountQuery {
        @NotNull
        public Catalog $catalog();

        @CheckReturnValue
        @NotNull
        public SetCatalog $catalog(Catalog var1);
    }

    public static interface SetCommand
    extends RowCountQuery {
        @NotNull
        public Name $name();

        @NotNull
        public Param<?> $value();

        public boolean $local();

        @CheckReturnValue
        @NotNull
        public SetCommand $name(Name var1);

        @CheckReturnValue
        @NotNull
        public SetCommand $value(Param<?> var1);

        @CheckReturnValue
        @NotNull
        public SetCommand $local(boolean var1);
    }

    public static interface Revoke
    extends DDLQuery {
        @NotNull
        public UnmodifiableList<? extends Privilege> $privileges();

        public boolean $grantOptionFor();

        @NotNull
        public Table<?> $on();

        @Nullable
        public Role $from();

        public boolean $fromPublic();

        @CheckReturnValue
        @NotNull
        public Revoke $privileges(Collection<? extends Privilege> var1);

        @CheckReturnValue
        @NotNull
        public Revoke $grantOptionFor(boolean var1);

        @CheckReturnValue
        @NotNull
        public Revoke $on(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public Revoke $from(Role var1);

        @CheckReturnValue
        @NotNull
        public Revoke $fromPublic(boolean var1);
    }

    public static interface Grant
    extends DDLQuery {
        @NotNull
        public UnmodifiableList<? extends Privilege> $privileges();

        @NotNull
        public Table<?> $on();

        @Nullable
        public Role $to();

        public boolean $toPublic();

        public boolean $withGrantOption();

        @CheckReturnValue
        @NotNull
        public Grant $privileges(Collection<? extends Privilege> var1);

        @CheckReturnValue
        @NotNull
        public Grant $on(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public Grant $to(Role var1);

        @CheckReturnValue
        @NotNull
        public Grant $toPublic(boolean var1);

        @CheckReturnValue
        @NotNull
        public Grant $withGrantOption(boolean var1);
    }

    public static interface DropView
    extends DDLQuery {
        @NotNull
        public Table<?> $view();

        public boolean $ifExists();

        @CheckReturnValue
        @NotNull
        public DropView $view(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public DropView $ifExists(boolean var1);
    }

    @Pro
    public static interface DropTrigger
    extends DDLQuery {
        @NotNull
        public Name $trigger();

        public boolean $ifExists();

        @CheckReturnValue
        @NotNull
        public DropTrigger $trigger(Name var1);

        @CheckReturnValue
        @NotNull
        public DropTrigger $ifExists(boolean var1);
    }

    public static interface DropTable
    extends DDLQuery {
        public boolean $temporary();

        @NotNull
        public Table<?> $table();

        public boolean $ifExists();

        @Nullable
        public Cascade $cascade();

        @CheckReturnValue
        @NotNull
        public DropTable $temporary(boolean var1);

        @CheckReturnValue
        @NotNull
        public DropTable $table(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public DropTable $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public DropTable $cascade(Cascade var1);
    }

    public static interface DropSequence
    extends DDLQuery {
        @NotNull
        public Sequence<?> $sequence();

        public boolean $ifExists();

        @CheckReturnValue
        @NotNull
        public DropSequence $sequence(Sequence<?> var1);

        @CheckReturnValue
        @NotNull
        public DropSequence $ifExists(boolean var1);
    }

    public static interface DropSchema
    extends DDLQuery {
        @NotNull
        public Schema $schema();

        public boolean $ifExists();

        @Nullable
        public Cascade $cascade();

        @CheckReturnValue
        @NotNull
        public DropSchema $schema(Schema var1);

        @CheckReturnValue
        @NotNull
        public DropSchema $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public DropSchema $cascade(Cascade var1);
    }

    @Pro
    public static interface DropProcedure
    extends DDLQuery {
        @NotNull
        public Name $procedure();

        public boolean $ifExists();

        @CheckReturnValue
        @NotNull
        public DropProcedure $procedure(Name var1);

        @CheckReturnValue
        @NotNull
        public DropProcedure $ifExists(boolean var1);
    }

    public static interface DropIndex
    extends DDLQuery {
        @NotNull
        public Index $index();

        public boolean $ifExists();

        @Nullable
        public Table<?> $on();

        @Nullable
        public Cascade $cascade();

        @CheckReturnValue
        @NotNull
        public DropIndex $index(Index var1);

        @CheckReturnValue
        @NotNull
        public DropIndex $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public DropIndex $on(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public DropIndex $cascade(Cascade var1);
    }

    @Pro
    public static interface DropFunction
    extends DDLQuery {
        @NotNull
        public Name $function();

        public boolean $ifExists();

        @CheckReturnValue
        @NotNull
        public DropFunction $function(Name var1);

        @CheckReturnValue
        @NotNull
        public DropFunction $ifExists(boolean var1);
    }

    public static interface DropDomain
    extends DDLQuery {
        @NotNull
        public Domain<?> $domain();

        public boolean $ifExists();

        @Nullable
        public Cascade $cascade();

        @CheckReturnValue
        @NotNull
        public DropDomain $domain(Domain<?> var1);

        @CheckReturnValue
        @NotNull
        public DropDomain $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public DropDomain $cascade(Cascade var1);
    }

    public static interface DropDatabase
    extends DDLQuery {
        @NotNull
        public Catalog $database();

        public boolean $ifExists();

        @CheckReturnValue
        @NotNull
        public DropDatabase $database(Catalog var1);

        @CheckReturnValue
        @NotNull
        public DropDatabase $ifExists(boolean var1);
    }

    public static interface CreateSequence
    extends DDLQuery {
        @NotNull
        public Sequence<?> $sequence();

        public boolean $ifNotExists();

        @Nullable
        public Field<? extends Number> $startWith();

        @Nullable
        public Field<? extends Number> $incrementBy();

        @Nullable
        public Field<? extends Number> $minvalue();

        public boolean $noMinvalue();

        @Nullable
        public Field<? extends Number> $maxvalue();

        public boolean $noMaxvalue();

        @Nullable
        public CycleOption $cycle();

        @Nullable
        public Field<? extends Number> $cache();

        public boolean $noCache();

        @CheckReturnValue
        @NotNull
        public CreateSequence $sequence(Sequence<?> var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $ifNotExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $startWith(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $incrementBy(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $minvalue(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $noMinvalue(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $maxvalue(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $noMaxvalue(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $cycle(CycleOption var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $cache(Field<? extends Number> var1);

        @CheckReturnValue
        @NotNull
        public CreateSequence $noCache(boolean var1);
    }

    public static interface CreateSchema
    extends DDLQuery {
        @NotNull
        public Schema $schema();

        public boolean $ifNotExists();

        @CheckReturnValue
        @NotNull
        public CreateSchema $schema(Schema var1);

        @CheckReturnValue
        @NotNull
        public CreateSchema $ifNotExists(boolean var1);
    }

    @Pro
    public static interface CreateTrigger
    extends DDLQuery {
        @NotNull
        public Name $trigger();

        public boolean $orReplace();

        public boolean $before();

        public boolean $after();

        public boolean $insteadOf();

        public boolean $insert();

        public boolean $update();

        public boolean $delete();

        @NotNull
        public UnmodifiableList<? extends Field<?>> $of();

        @NotNull
        public Table<?> $on();

        @Nullable
        public Name $referencingOldAs();

        @Nullable
        public Name $referencingNewAs();

        public boolean $forEachRow();

        public boolean $forEachStatement();

        @Nullable
        public Condition $when();

        @NotNull
        public Statement $body();

        @CheckReturnValue
        @NotNull
        public CreateTrigger $trigger(Name var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $orReplace(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $before(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $after(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $insteadOf(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $insert(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $update(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $delete(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $of(Collection<? extends Field<?>> var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $on(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $referencingOldAs(Name var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $referencingNewAs(Name var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $forEachRow(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $forEachStatement(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $when(Condition var1);

        @CheckReturnValue
        @NotNull
        public CreateTrigger $body(Statement var1);
    }

    public static interface CreateTable
    extends DDLQuery {
        @NotNull
        public Table<?> $table();

        public boolean $temporary();

        public boolean $ifNotExists();

        @NotNull
        public UnmodifiableList<? extends TableElement> $tableElements();

        @Nullable
        public Select<?> $select();

        @Nullable
        public WithOrWithoutData $withData();

        @Nullable
        public TableCommitAction $onCommit();

        @Nullable
        public Comment $comment();

        @Nullable
        public SQL $storage();

        @CheckReturnValue
        @NotNull
        public CreateTable $table(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public CreateTable $temporary(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTable $ifNotExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateTable $tableElements(Collection<? extends TableElement> var1);

        @CheckReturnValue
        @NotNull
        public CreateTable $select(Select<?> var1);

        @CheckReturnValue
        @NotNull
        public CreateTable $withData(WithOrWithoutData var1);

        @CheckReturnValue
        @NotNull
        public CreateTable $onCommit(TableCommitAction var1);

        @CheckReturnValue
        @NotNull
        public CreateTable $comment(Comment var1);

        @CheckReturnValue
        @NotNull
        public CreateTable $storage(SQL var1);
    }

    @Pro
    public static interface CreateProcedure
    extends DDLQuery {
        @NotNull
        public Name $procedure();

        public boolean $orReplace();

        @NotNull
        public UnmodifiableList<? extends Parameter<?>> $parameters();

        @Nullable
        public SQLDataAccess $sqlDataAccess();

        @NotNull
        public Statement $body();

        @CheckReturnValue
        @NotNull
        public CreateProcedure $procedure(Name var1);

        @CheckReturnValue
        @NotNull
        public CreateProcedure $orReplace(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateProcedure $parameters(Collection<? extends Parameter<?>> var1);

        @CheckReturnValue
        @NotNull
        public CreateProcedure $sqlDataAccess(SQLDataAccess var1);

        @CheckReturnValue
        @NotNull
        public CreateProcedure $body(Statement var1);
    }

    public static interface CreateIndex
    extends DDLQuery {
        public boolean $unique();

        @Nullable
        public Index $index();

        public boolean $ifNotExists();

        @Nullable
        public Table<?> $table();

        @NotNull
        public UnmodifiableList<? extends OrderField<?>> $on();

        @NotNull
        public UnmodifiableList<? extends Field<?>> $include();

        @Nullable
        public Condition $where();

        public boolean $excludeNullKeys();

        @CheckReturnValue
        @NotNull
        public CreateIndex $unique(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateIndex $index(Index var1);

        @CheckReturnValue
        @NotNull
        public CreateIndex $ifNotExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateIndex $table(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public CreateIndex $on(Collection<? extends OrderField<?>> var1);

        @CheckReturnValue
        @NotNull
        public CreateIndex $include(Collection<? extends Field<?>> var1);

        @CheckReturnValue
        @NotNull
        public CreateIndex $where(Condition var1);

        @CheckReturnValue
        @NotNull
        public CreateIndex $excludeNullKeys(boolean var1);
    }

    @Pro
    public static interface CreateFunction
    extends DDLQuery {
        @NotNull
        public Name $function();

        public boolean $orReplace();

        @NotNull
        public UnmodifiableList<? extends Parameter<?>> $parameters();

        @Nullable
        public DataType<?> $returns();

        @NotNull
        public UnmodifiableList<? extends Field<?>> $returnsTable();

        @Nullable
        public SQLDataAccess $sqlDataAccess();

        @Nullable
        public NullCallClause $returnsNullOnNullInput();

        @Nullable
        public Deterministic $deterministic();

        @NotNull
        public Statement $body();

        @CheckReturnValue
        @NotNull
        public CreateFunction $function(Name var1);

        @CheckReturnValue
        @NotNull
        public CreateFunction $orReplace(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateFunction $parameters(Collection<? extends Parameter<?>> var1);

        @CheckReturnValue
        @NotNull
        public CreateFunction $returns(DataType<?> var1);

        @CheckReturnValue
        @NotNull
        public CreateFunction $returnsTable(Collection<? extends Field<?>> var1);

        @CheckReturnValue
        @NotNull
        public CreateFunction $sqlDataAccess(SQLDataAccess var1);

        @CheckReturnValue
        @NotNull
        public CreateFunction $returnsNullOnNullInput(NullCallClause var1);

        @CheckReturnValue
        @NotNull
        public CreateFunction $deterministic(Deterministic var1);

        @CheckReturnValue
        @NotNull
        public CreateFunction $body(Statement var1);
    }

    public static interface CreateDomain<T>
    extends DDLQuery {
        @NotNull
        public Domain<?> $domain();

        public boolean $ifNotExists();

        @NotNull
        public DataType<T> $dataType();

        @Nullable
        public Field<T> $default_();

        @NotNull
        public UnmodifiableList<? extends Constraint> $constraints();

        @CheckReturnValue
        @NotNull
        public CreateDomain<T> $domain(Domain<?> var1);

        @CheckReturnValue
        @NotNull
        public CreateDomain<T> $ifNotExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public CreateDomain<T> $dataType(DataType<T> var1);

        @CheckReturnValue
        @NotNull
        public CreateDomain<T> $default_(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public CreateDomain<T> $constraints(Collection<? extends Constraint> var1);
    }

    public static interface CreateDatabase
    extends DDLQuery {
        @NotNull
        public Catalog $database();

        public boolean $ifNotExists();

        @CheckReturnValue
        @NotNull
        public CreateDatabase $database(Catalog var1);

        @CheckReturnValue
        @NotNull
        public CreateDatabase $ifNotExists(boolean var1);
    }

    public static interface CommentOn
    extends DDLQuery {
        @Nullable
        public Table<?> $table();

        public boolean $isView();

        @Nullable
        public Field<?> $field();

        @NotNull
        public Comment $comment();

        @CheckReturnValue
        @NotNull
        public CommentOn $table(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public CommentOn $isView(boolean var1);

        @CheckReturnValue
        @NotNull
        public CommentOn $field(Field<?> var1);

        @CheckReturnValue
        @NotNull
        public CommentOn $comment(Comment var1);
    }

    public static interface AlterView
    extends DDLQuery {
        @NotNull
        public Table<?> $view();

        @NotNull
        public UnmodifiableList<? extends Field<?>> $fields();

        public boolean $ifExists();

        @Nullable
        public Comment $comment();

        @Nullable
        public Table<?> $renameTo();

        @Nullable
        public Select<?> $as();

        @CheckReturnValue
        @NotNull
        public AlterView $view(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public AlterView $fields(Collection<? extends Field<?>> var1);

        @CheckReturnValue
        @NotNull
        public AlterView $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterView $comment(Comment var1);

        @CheckReturnValue
        @NotNull
        public AlterView $renameTo(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public AlterView $as(Select<?> var1);
    }

    public static interface AlterType
    extends DDLQuery {
        @NotNull
        public Name $type();

        @Nullable
        public Name $renameTo();

        @Nullable
        public Schema $setSchema();

        @Nullable
        public Field<String> $addValue();

        @Nullable
        public Field<String> $renameValue();

        @Nullable
        public Field<String> $renameValueTo();

        @CheckReturnValue
        @NotNull
        public AlterType $type(Name var1);

        @CheckReturnValue
        @NotNull
        public AlterType $renameTo(Name var1);

        @CheckReturnValue
        @NotNull
        public AlterType $setSchema(Schema var1);

        @CheckReturnValue
        @NotNull
        public AlterType $addValue(Field<String> var1);

        @CheckReturnValue
        @NotNull
        public AlterType $renameValue(Field<String> var1);

        @CheckReturnValue
        @NotNull
        public AlterType $renameValueTo(Field<String> var1);
    }

    public static interface AlterSequence<T extends Number>
    extends DDLQuery {
        @NotNull
        public Sequence<T> $sequence();

        public boolean $ifExists();

        @Nullable
        public Sequence<?> $renameTo();

        public boolean $restart();

        @Nullable
        public Field<T> $restartWith();

        @Nullable
        public Field<T> $startWith();

        @Nullable
        public Field<T> $incrementBy();

        @Nullable
        public Field<T> $minvalue();

        public boolean $noMinvalue();

        @Nullable
        public Field<T> $maxvalue();

        public boolean $noMaxvalue();

        @Nullable
        public CycleOption $cycle();

        @Nullable
        public Field<T> $cache();

        public boolean $noCache();

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $sequence(Sequence<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $renameTo(Sequence<?> var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $restart(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $restartWith(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $startWith(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $incrementBy(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $minvalue(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $noMinvalue(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $maxvalue(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $noMaxvalue(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $cycle(CycleOption var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $cache(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterSequence<T> $noCache(boolean var1);
    }

    public static interface AlterSchema
    extends DDLQuery {
        @NotNull
        public Schema $schema();

        public boolean $ifExists();

        @NotNull
        public Schema $renameTo();

        @CheckReturnValue
        @NotNull
        public AlterSchema $schema(Schema var1);

        @CheckReturnValue
        @NotNull
        public AlterSchema $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterSchema $renameTo(Schema var1);
    }

    public static interface AlterIndex
    extends DDLQuery {
        @NotNull
        public Index $index();

        public boolean $ifExists();

        @Nullable
        public Table<?> $on();

        @NotNull
        public Index $renameTo();

        @CheckReturnValue
        @NotNull
        public AlterIndex $index(Index var1);

        @CheckReturnValue
        @NotNull
        public AlterIndex $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterIndex $on(Table<?> var1);

        @CheckReturnValue
        @NotNull
        public AlterIndex $renameTo(Index var1);
    }

    public static interface AlterDomain<T>
    extends DDLQuery {
        @NotNull
        public Domain<T> $domain();

        public boolean $ifExists();

        @Nullable
        public Constraint $addConstraint();

        @Nullable
        public Constraint $dropConstraint();

        public boolean $dropConstraintIfExists();

        @Nullable
        public Domain<?> $renameTo();

        @Nullable
        public Constraint $renameConstraint();

        public boolean $renameConstraintIfExists();

        @Nullable
        public Field<T> $setDefault();

        public boolean $dropDefault();

        public boolean $setNotNull();

        public boolean $dropNotNull();

        @Nullable
        public Cascade $cascade();

        @Nullable
        public Constraint $renameConstraintTo();

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $domain(Domain<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $addConstraint(Constraint var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $dropConstraint(Constraint var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $dropConstraintIfExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $renameTo(Domain<?> var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $renameConstraint(Constraint var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $renameConstraintIfExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $setDefault(Field<T> var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $dropDefault(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $setNotNull(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $dropNotNull(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $cascade(Cascade var1);

        @CheckReturnValue
        @NotNull
        public AlterDomain<T> $renameConstraintTo(Constraint var1);
    }

    public static interface AlterDatabase
    extends DDLQuery {
        @NotNull
        public Catalog $database();

        public boolean $ifExists();

        @NotNull
        public Catalog $renameTo();

        @CheckReturnValue
        @NotNull
        public AlterDatabase $database(Catalog var1);

        @CheckReturnValue
        @NotNull
        public AlterDatabase $ifExists(boolean var1);

        @CheckReturnValue
        @NotNull
        public AlterDatabase $renameTo(Catalog var1);
    }

    public static interface XMLParse
    extends Field<XML> {
        @NotNull
        public Field<String> $content();

        @NotNull
        public DocumentOrContent $documentOrContent();
    }

    public static interface XMLExists
    extends Condition {
        @NotNull
        public Field<String> $xpath();

        @NotNull
        public Field<XML> $passing();

        @Nullable
        public XMLPassingMechanism $passingMechanism();
    }

    public static interface XMLElement
    extends Field<XML> {
        @NotNull
        public Name $elementName();

        @NotNull
        public XMLAttributes $attributes();

        @NotNull
        public UnmodifiableList<? extends Field<?>> $content();
    }

    public static interface XMLQuery
    extends Field<XML> {
        @NotNull
        public Field<String> $xpath();

        @NotNull
        public Field<XML> $passing();

        @Nullable
        public XMLPassingMechanism $passingMechanism();
    }

    public static interface CurrentTimestamp<T>
    extends Field<T>,
    UEmpty {
    }

    public static interface CurrentTime<T>
    extends Field<T>,
    UEmpty {
    }

    public static interface CurrentDate<T>
    extends Field<T>,
    UEmpty {
    }

    public static interface Convert<T>
    extends Field<T> {
        @NotNull
        public Field<?> $field();

        public int $style();
    }

    public static interface TimestampDiff<T>
    extends Field<DayToSecond>,
    UOperator2<Field<T>, Field<T>, TimestampDiff<T>> {
        @NotNull
        default public Field<T> $minuend() {
            return (Field)this.$arg1();
        }

        @NotNull
        default public Field<T> $subtrahend() {
            return (Field)this.$arg2();
        }
    }

    public static interface Concat
    extends Field<String>,
    UOperator1<UnmodifiableList<? extends Field<?>>, Concat> {
    }

    public static interface Decode<V, T>
    extends Field<T>,
    UOperator3<Field<V>, UnmodifiableList<? extends Tuple2<Field<V>, Field<T>>>, Field<T>, Decode<V, T>> {
        @NotNull
        default public Field<V> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public Decode<V, T> $value(Field<V> value) {
            return (Decode)this.$arg1(value);
        }

        @NotNull
        default public UnmodifiableList<? extends Tuple2<Field<V>, Field<T>>> $when() {
            return (UnmodifiableList)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public Decode<V, T> $when(UnmodifiableList<? extends Tuple2<Field<V>, Field<T>>> when) {
            return (Decode)this.$arg2(when);
        }

        @Nullable
        default public Field<T> $else() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public Decode<V, T> $else(Field<T> else_) {
            return (Decode)this.$arg3(else_);
        }
    }

    public static interface CaseSearched<T>
    extends Field<T>,
    UOperator2<UnmodifiableList<? extends Tuple2<Condition, Field<T>>>, Field<T>, CaseSearched<T>> {
        @NotNull
        default public UnmodifiableList<? extends Tuple2<Condition, Field<T>>> $when() {
            return (UnmodifiableList)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public CaseSearched<T> $when(UnmodifiableList<? extends Tuple2<Condition, Field<T>>> when) {
            return (CaseSearched)this.$arg1(when);
        }

        @Nullable
        default public Field<T> $else() {
            return (Field)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public CaseSearched<T> $else(Field<T> else_) {
            return (CaseSearched)this.$arg2(else_);
        }
    }

    public static interface CaseSimple<V, T>
    extends Field<T>,
    UOperator3<Field<V>, UnmodifiableList<? extends Tuple2<Field<V>, Field<T>>>, Field<T>, CaseSimple<V, T>> {
        @NotNull
        default public Field<V> $value() {
            return (Field)this.$arg1();
        }

        @CheckReturnValue
        @NotNull
        default public CaseSimple<V, T> $value(Field<V> value) {
            return (CaseSimple)this.$arg1(value);
        }

        @NotNull
        default public UnmodifiableList<? extends Tuple2<Field<V>, Field<T>>> $when() {
            return (UnmodifiableList)this.$arg2();
        }

        @CheckReturnValue
        @NotNull
        default public CaseSimple<V, T> $when(UnmodifiableList<? extends Tuple2<Field<V>, Field<T>>> when) {
            return (CaseSimple)this.$arg2(when);
        }

        @Nullable
        default public Field<T> $else() {
            return (Field)this.$arg3();
        }

        @CheckReturnValue
        @NotNull
        default public CaseSimple<V, T> $else(Field<T> else_) {
            return (CaseSimple)this.$arg3(else_);
        }
    }

    public static interface Coalesce<T>
    extends Field<T>,
    UOperator1<UnmodifiableList<? extends Field<T>>, Coalesce<T>> {
    }

    public static interface Iif<T>
    extends Field<T>,
    UOperator3<Condition, Field<T>, Field<T>, Iif<T>> {
        @NotNull
        default public Condition $condition() {
            return (Condition)this.$arg1();
        }

        @NotNull
        default public Field<T> $ifTrue() {
            return (Field)this.$arg2();
        }

        @NotNull
        default public Field<T> $ifFalse() {
            return (Field)this.$arg3();
        }
    }

    public static interface Nvl2<T>
    extends Field<T>,
    UOperator3<Field<?>, Field<T>, Field<T>, Nvl2<T>> {
        @NotNull
        default public Field<?> $value() {
            return (Field)this.$arg1();
        }

        @NotNull
        default public Field<T> $ifNotNull() {
            return (Field)this.$arg2();
        }

        @NotNull
        default public Field<T> $ifIfNull() {
            return (Field)this.$arg3();
        }
    }

    public static interface FieldFunction<T>
    extends Field<Integer>,
    UOperator2<Field<T>, UnmodifiableList<? extends Field<T>>, FieldFunction<T>> {
    }

    public static interface Choose<T>
    extends Field<T>,
    UOperator2<Field<Integer>, UnmodifiableList<? extends Field<T>>, Choose<T>> {
    }

    public static interface Least<T>
    extends Field<T>,
    UOperator1<UnmodifiableList<? extends Field<T>>, Least<T>> {
    }

    public static interface Greatest<T>
    extends Field<T>,
    UOperator1<UnmodifiableList<? extends Field<T>>, Greatest<T>> {
    }

    public static interface Neg<T>
    extends UReturnsNullOnNullInput,
    Field<T>,
    UOperator1<Field<T>, Neg<T>> {
    }

    public static interface RowSubquery
    extends Row,
    UOperator1<Select<?>, RowSubquery> {
    }

    public static interface ScalarSubquery<T>
    extends Field<T>,
    UOperator1<Select<? extends Record1<T>>, ScalarSubquery<T>> {
    }

    public static interface Multiset<R extends Record>
    extends Field<Result<R>> {
        @NotNull
        public TableLike<R> $table();
    }

    public static interface ArrayQuery<T>
    extends Field<T[]> {
        @NotNull
        public Select<? extends Record1<T>> $query();
    }

    public static interface Array<T>
    extends Field<T[]> {
        @NotNull
        public UnmodifiableList<? extends Field<T>> $elements();
    }

    public static interface Collated
    extends Field<String> {
        @NotNull
        public Field<?> $field();

        @NotNull
        public Collation $collation();
    }

    public static interface Default<T>
    extends Field<T>,
    UEmpty {
    }

    public static interface Coerce<T>
    extends Field<T> {
        @NotNull
        public Field<?> $field();
    }

    public static interface Cast<T>
    extends Field<T> {
        @NotNull
        public Field<?> $field();
    }

    public static interface Function<T>
    extends Field<T> {
        @NotNull
        public UnmodifiableList<? extends Field<?>> $args();
    }

    public static interface FieldAlias<T>
    extends Field<T>,
    Aliasable<Field<?>> {
        @NotNull
        public Field<T> $field();

        @Override
        @NotNull
        default public Field<?> $aliased() {
            return this.$field();
        }

        @Override
        @NotNull
        public Name $alias();
    }

    public static interface NthValue<T>
    extends WindowFunction<T> {
        @NotNull
        public Field<T> $field();

        @Nullable
        public FromFirstOrLast $fromFirstOrLast();

        @Nullable
        public NullTreatment $nullTreatment();
    }

    public static interface LastValue<T>
    extends WindowFunction<T> {
        @NotNull
        public Field<T> $field();

        @Nullable
        public NullTreatment $nullTreatment();
    }

    public static interface FirstValue<T>
    extends WindowFunction<T> {
        @NotNull
        public Field<T> $field();

        @Nullable
        public NullTreatment $nullTreatment();
    }

    public static interface Lag<T>
    extends WindowFunction<T> {
        @NotNull
        public Field<T> $field();

        @Nullable
        public Field<Integer> $offset();

        @Nullable
        public Field<T> $defaultValue();

        @Nullable
        public NullTreatment $nullTreatment();
    }

    public static interface Lead<T>
    extends WindowFunction<T> {
        @NotNull
        public Field<T> $field();

        @Nullable
        public Field<Integer> $offset();

        @Nullable
        public Field<T> $defaultValue();

        @Nullable
        public NullTreatment $nullTreatment();
    }

    public static interface Ntile
    extends WindowFunction<Integer> {
        @NotNull
        public Field<Integer> $tiles();
    }

    public static interface CumeDist
    extends WindowFunction<BigDecimal> {
    }

    public static interface PercentRank
    extends WindowFunction<BigDecimal> {
    }

    public static interface DenseRank
    extends WindowFunction<Integer> {
    }

    public static interface Rank
    extends WindowFunction<Integer> {
    }

    public static interface RowNumber
    extends WindowFunction<Integer> {
    }

    public static interface WindowFunction<T>
    extends Field<T> {
        @Nullable
        public WindowSpecification $windowSpecification();

        @Nullable
        public WindowDefinition $windowDefinition();
    }

    @Pro
    public static interface Collect<T, A extends ArrayRecord<T>>
    extends AggregateFunction<A>,
    UOperator1<Field<T>, Collect<T, A>> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        public boolean $distinct();
    }

    public static interface CountTable
    extends AggregateFunction<Integer> {
        @NotNull
        public Table<?> $table();

        public boolean $distinct();
    }

    public static interface JSONObjectAgg<J>
    extends AggregateFunction<J>,
    UOperator1<JSONEntry<?>, JSONObjectAgg<J>> {
        @NotNull
        default public JSONEntry<?> $entry() {
            return (JSONEntry)this.$arg1();
        }

        @Nullable
        public JSONOnNull $onNull();

        @Nullable
        public DataType<?> $returning();
    }

    public static interface JSONArrayAgg<J>
    extends AggregateFunction<J>,
    UOperator1<Field<?>, JSONArrayAgg<J>> {
        @NotNull
        default public Field<?> $field() {
            return (Field)this.$arg1();
        }

        public boolean $distinct();

        @Nullable
        public JSONOnNull $onNull();

        @Nullable
        public DataType<?> $returning();
    }

    public static interface XMLAgg
    extends AggregateFunction<XML>,
    UOperator1<Field<XML>, XMLAgg> {
        @NotNull
        default public Field<XML> $field() {
            return (Field)this.$arg1();
        }
    }

    public static interface ArrayAgg<T>
    extends AggregateFunction<T[]>,
    UOperator1<Field<T>, ArrayAgg<T>> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        public boolean $distinct();
    }

    public static interface MultisetAgg<R extends Record>
    extends AggregateFunction<Result<R>> {
        @NotNull
        public Row $row();
    }

    public static interface Mode<T>
    extends AggregateFunction<T>,
    UOperator1<Field<T>, Mode<T>> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }
    }

    public static interface RatioToReport
    extends AggregateFunction<BigDecimal> {
        @NotNull
        public Field<? extends Number> $field();
    }

    public static interface GroupingSets
    extends GroupField,
    UOperator1<UnmodifiableList<? extends UnmodifiableList<? extends FieldOrRow>>, GroupingSets> {
    }

    public static interface Cube
    extends GroupField,
    UOperator1<UnmodifiableList<? extends FieldOrRow>, Cube> {
    }

    public static interface Rollup
    extends GroupField,
    UOperator1<UnmodifiableList<? extends FieldOrRow>, Rollup> {
    }

    public static interface EmptyGroupingSet
    extends GroupField,
    UEmpty {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface LeftAntiJoin<R extends Record>
    extends QualifiedJoin<R, LeftAntiJoin<R>> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface LeftSemiJoin<R extends Record>
    extends QualifiedJoin<R, LeftSemiJoin<R>> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface FullJoin<R extends Record>
    extends QualifiedJoin<R, FullJoin<R>> {
        @NotNull
        public UnmodifiableList<Field<?>> $partitionBy1();

        @NotNull
        public FullJoin<R> $partitionBy1(Collection<? extends Field<?>> var1);

        @NotNull
        public UnmodifiableList<Field<?>> $partitionBy2();

        @NotNull
        public FullJoin<R> $partitionBy2(Collection<? extends Field<?>> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface RightJoin<R extends Record>
    extends QualifiedJoin<R, RightJoin<R>> {
        @NotNull
        public UnmodifiableList<Field<?>> $partitionBy1();

        @NotNull
        public RightJoin<R> $partitionBy1(Collection<? extends Field<?>> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface LeftJoin<R extends Record>
    extends QualifiedJoin<R, LeftJoin<R>> {
        @NotNull
        public UnmodifiableList<Field<?>> $partitionBy2();

        @NotNull
        public LeftJoin<R> $partitionBy2(Collection<? extends Field<?>> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface StraightJoin<R extends Record>
    extends QualifiedJoin<R, StraightJoin<R>> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface Join<R extends Record>
    extends QualifiedJoin<R, Join<R>> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface QualifiedJoin<R extends Record, J extends QualifiedJoin<R, J>>
    extends JoinTable<R, J> {
        @Nullable
        public Condition $on();

        @NotNull
        public J $on(Condition var1);

        @NotNull
        public UnmodifiableList<Field<?>> $using();

        @NotNull
        public J $using(Collection<? extends Field<?>> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface NaturalFullJoin<R extends Record>
    extends JoinTable<R, NaturalFullJoin<R>> {
        @NotNull
        public UnmodifiableList<Field<?>> $partitionBy1();

        @NotNull
        public NaturalFullJoin<R> $partitionBy1(Collection<? extends Field<?>> var1);

        @NotNull
        public UnmodifiableList<Field<?>> $partitionBy2();

        @NotNull
        public NaturalFullJoin<R> $partitionBy2(Collection<? extends Field<?>> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface NaturalRightJoin<R extends Record>
    extends JoinTable<R, NaturalRightJoin<R>> {
        @NotNull
        public UnmodifiableList<Field<?>> $partitionBy1();

        @NotNull
        public NaturalRightJoin<R> $partitionBy1(Collection<? extends Field<?>> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface NaturalLeftJoin<R extends Record>
    extends JoinTable<R, NaturalLeftJoin<R>> {
        @NotNull
        public UnmodifiableList<Field<?>> $partitionBy2();

        @NotNull
        public NaturalLeftJoin<R> $partitionBy2(Collection<? extends Field<?>> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface NaturalJoin<R extends Record>
    extends JoinTable<R, NaturalJoin<R>> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface OuterApply<R extends Record>
    extends JoinTable<R, OuterApply<R>> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface CrossApply<R extends Record>
    extends JoinTable<R, CrossApply<R>> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface CrossJoin<R extends Record>
    extends JoinTable<R, CrossJoin<R>> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface JoinTable<R extends Record, J extends JoinTable<R, J>>
    extends Table<R> {
        @NotNull
        public Table<?> $table1();

        @NotNull
        public Table<?> $table2();

        @NotNull
        public J $table1(Table<?> var1);

        @NotNull
        public J $table2(Table<?> var1);
    }

    public static interface TableAsField<R extends Record>
    extends Field<R> {
        @NotNull
        public Table<R> $table();
    }

    public static interface RowAsField<R extends Record>
    extends Field<R> {
        @NotNull
        public Row $row();
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface QuantifiedArray<T>
    extends Quantified,
    UOperator2<Quantifier, Field<T[]>, QuantifiedArray<T>> {
        @NotNull
        default public Quantifier $quantifier() {
            return (Quantifier)((Object)this.$arg1());
        }

        @NotNull
        default public QuantifiedArray<T> $quantifier(Quantifier newQuantifier) {
            return (QuantifiedArray)this.$arg1(newQuantifier);
        }

        @NotNull
        default public Field<T[]> $array() {
            return (Field)this.$arg2();
        }

        @NotNull
        default public QuantifiedArray<T> $array(Field<T[]> newArray) {
            return (QuantifiedArray)this.$arg2(newArray);
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface QuantifiedSelect<R extends Record>
    extends Quantified,
    UOperator2<Quantifier, Select<R>, QuantifiedSelect<R>> {
        @NotNull
        default public Quantifier $quantifier() {
            return (Quantifier)((Object)this.$arg1());
        }

        @NotNull
        default public QuantifiedSelect<R> $quantifier(Quantifier newQuantifier) {
            return (QuantifiedSelect)this.$arg1(newQuantifier);
        }

        @NotNull
        default public Select<R> $query() {
            return (Select)this.$arg2();
        }

        @NotNull
        default public QuantifiedSelect<R> $query(Select<R> newSelect) {
            return (QuantifiedSelect)this.$arg2(newSelect);
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface Quantified
    extends QueryPart {
    }

    public static interface SelectIsNotNull
    extends Condition,
    UOperator1<Select<?>, SelectIsNotNull> {
        @NotNull
        default public Select<?> $field() {
            return (Select)this.$arg1();
        }
    }

    public static interface SelectIsNull
    extends Condition,
    UOperator1<Select<?>, SelectIsNull> {
        @NotNull
        default public Select<?> $field() {
            return (Select)this.$arg1();
        }
    }

    public static interface RowOverlaps
    extends Condition,
    UOperator2<Row, Row, RowOverlaps> {
    }

    public static interface RowIsNotNull
    extends Condition,
    UOperator1<Row, RowIsNotNull> {
        @NotNull
        default public Row $field() {
            return (Row)this.$arg1();
        }
    }

    public static interface RowIsNull
    extends Condition,
    UOperator1<Row, RowIsNull> {
        @NotNull
        default public Row $field() {
            return (Row)this.$arg1();
        }
    }

    public static interface Extract
    extends Field<Integer> {
        @NotNull
        public Field<?> $field();

        @NotNull
        public DatePart $datePart();
    }

    public static interface RegexpLike
    extends Condition {
        @NotNull
        public Field<?> $search();

        @NotNull
        public Field<String> $pattern();
    }

    public static interface NotInList<T>
    extends Condition,
    UOperator2<Field<T>, UnmodifiableList<? extends Field<T>>, NotInList<T>> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        @NotNull
        default public UnmodifiableList<? extends Field<T>> $list() {
            return (UnmodifiableList)this.$arg2();
        }
    }

    public static interface InList<T>
    extends Condition,
    UOperator2<Field<T>, UnmodifiableList<? extends Field<T>>, InList<T>> {
        @NotNull
        default public Field<T> $field() {
            return (Field)this.$arg1();
        }

        @NotNull
        default public UnmodifiableList<? extends Field<T>> $list() {
            return (UnmodifiableList)this.$arg2();
        }
    }

    public static interface Between<T>
    extends Condition,
    UOperator3<Field<T>, Field<T>, Field<T>, Between<T>> {
        public boolean $symmetric();

        @NotNull
        public Between<T> $symmetric(boolean var1);
    }

    public static interface CompareCondition<T, R extends CompareCondition<T, R>>
    extends Condition,
    UOperator2<Field<T>, Field<T>, R> {
    }

    public static interface CombinedCondition<R extends CombinedCondition<R>>
    extends Condition,
    UCommutativeOperator<Condition, R> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface Null
    extends Condition {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface False
    extends Condition {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface True
    extends Condition {
    }

    public static interface GenerateSeries<T>
    extends Table<Record1<T>>,
    UOperator3<Field<T>, Field<T>, Field<T>, GenerateSeries<T>> {
        @NotNull
        default public Field<T> $from() {
            return (Field)this.$arg1();
        }

        @NotNull
        default public Field<T> $to() {
            return (Field)this.$arg2();
        }

        @Nullable
        default public Field<T> $step() {
            return (Field)this.$arg3();
        }
    }

    public static interface RowsFrom
    extends Table<Record> {
        @NotNull
        public UnmodifiableList<? extends Table<?>> $tables();
    }

    public static interface DataChangeDeltaTable<R extends Record>
    extends Table<R> {
        @NotNull
        public ResultOption $resultOption();

        @NotNull
        public DMLQuery<R> $query();
    }

    public static interface Values<R extends Record>
    extends Table<R>,
    UOperator1<UnmodifiableList<? extends Row>, Values<R>> {
    }

    public static interface DerivedTable<R extends Record>
    extends Table<R>,
    UOperator1<Select<R>, DerivedTable<R>> {
    }

    public static interface Lateral<R extends Record>
    extends Table<R>,
    UOperator1<Table<R>, Lateral<R>> {
    }

    public static interface Dual
    extends Table<Record>,
    UEmpty {
    }

    public static interface TableAlias<R extends Record>
    extends Table<R>,
    Aliasable<Table<R>> {
        @NotNull
        public Table<R> $table();

        @Override
        @NotNull
        default public Table<R> $aliased() {
            return this.$table();
        }

        @Override
        @NotNull
        public Name $alias();
    }

    @Pro
    public static interface RowAssignment
    extends Statement {
    }

    @Pro
    public static interface Loop
    extends Statement {
    }

    @Pro
    public static interface LabelledStatement
    extends Statement {
        @NotNull
        public Label $label();

        @NotNull
        public Statement $statement();
    }

    @Pro
    public static interface Label
    extends Statement {
        @NotNull
        public Name $name();
    }

    @Pro
    public static interface If
    extends Statement {
    }

    @Pro
    public static interface Exit
    extends Statement {
        @Nullable
        public Label $label();

        @Nullable
        public Condition $condition();
    }

    @Pro
    public static interface Declaration<T>
    extends Statement {
        @NotNull
        public Variable<T> $variable();

        @Nullable
        public Field<T> $value();
    }

    @Pro
    public static interface Continue
    extends Statement {
        @Nullable
        public Label $label();

        @Nullable
        public Condition $condition();
    }

    @Pro
    public static interface Assignment<T>
    extends Statement {
        @NotNull
        public Variable<T> $variable();

        @NotNull
        public Field<T> $value();
    }

    public static interface NullStatement
    extends Statement {
    }

    public static interface Check
    extends Constraint {
        @NotNull
        public Condition $condition();
    }

    public static interface ForeignKey
    extends Constraint {
        @NotNull
        public UnmodifiableList<? extends Field<?>> $fields();

        @NotNull
        public Constraint $references();
    }

    public static interface UniqueKey
    extends Constraint {
        @NotNull
        public UnmodifiableList<? extends Field<?>> $fields();
    }

    public static interface PrimaryKey
    extends Constraint {
        @NotNull
        public UnmodifiableList<? extends Field<?>> $fields();
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface WithOrdinalityTable<R extends Record>
    extends Table<R> {
        @NotNull
        public Table<?> $table();

        @CheckReturnValue
        @NotNull
        public WithOrdinalityTable<?> $table(Table<?> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface HintedTable<R extends Record>
    extends Table<R> {
        @NotNull
        public Table<R> $table();

        @CheckReturnValue
        @NotNull
        public <O extends Record> HintedTable<O> $table(Table<O> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    @Pro
    public static interface WithTable<R extends Record>
    extends Table<R> {
        @NotNull
        public Table<R> $table();

        @CheckReturnValue
        @NotNull
        public <O extends Record> WithTable<O> $table(Table<O> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    @Pro
    public static interface LinkedTable<R extends Record>
    extends Table<R> {
        @NotNull
        public Table<R> $table();

        @CheckReturnValue
        @NotNull
        public <O extends Record> LinkedTable<O> $table(Table<O> var1);

        @NotNull
        public Link $link();

        @CheckReturnValue
        @NotNull
        public LinkedTable<R> $link(Link var1);
    }

    public static interface CreateView<R extends Record>
    extends DDLQuery {
        public boolean $ifNotExists();

        public boolean $orReplace();

        @NotNull
        public Table<?> $view();

        @NotNull
        public UnmodifiableList<? extends Field<?>> $fields();

        @NotNull
        public ResultQuery<R> $query();
    }

    public static interface DropType
    extends DDLQuery {
        @NotNull
        public UnmodifiableList<? extends Name> $names();

        public boolean $ifExists();

        @Nullable
        public Cascade $cascade();
    }

    public static interface CreateType
    extends DDLQuery {
        @NotNull
        public Name $name();

        @NotNull
        public UnmodifiableList<? extends Field<String>> $values();
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface DeleteReturning<R extends Record>
    extends ResultQuery<R> {
        @NotNull
        public Delete<?> $delete();

        @CheckReturnValue
        @NotNull
        public DeleteReturning<R> $delete(Delete<?> var1);

        @NotNull
        public UnmodifiableList<? extends SelectFieldOrAsterisk> $returning();

        @CheckReturnValue
        @NotNull
        public DeleteReturning<?> $returning(Collection<? extends SelectFieldOrAsterisk> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface Delete<R extends Record>
    extends DMLQuery<R> {
        @Nullable
        public With $with();

        @NotNull
        public Table<R> $from();

        @CheckReturnValue
        @NotNull
        public Delete<?> $from(Table<?> var1);

        @NotNull
        public UnmodifiableList<? extends Table<?>> $using();

        @CheckReturnValue
        @NotNull
        public Delete<R> $using(Collection<? extends Table<?>> var1);

        @Nullable
        public Condition $where();

        @CheckReturnValue
        @NotNull
        public Delete<R> $where(Condition var1);

        @NotNull
        public UnmodifiableList<? extends SortField<?>> $orderBy();

        @CheckReturnValue
        @NotNull
        public Delete<R> $orderBy(Collection<? extends SortField<?>> var1);

        @Nullable
        public Field<? extends Number> $limit();

        @CheckReturnValue
        @NotNull
        public Delete<R> $limit(Field<? extends Number> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface UpdateReturning<R extends Record>
    extends ResultQuery<R> {
        @NotNull
        public Update<?> $update();

        @CheckReturnValue
        @NotNull
        public UpdateReturning<R> $update(Update<?> var1);

        @NotNull
        public UnmodifiableList<? extends SelectFieldOrAsterisk> $returning();

        @CheckReturnValue
        @NotNull
        public UpdateReturning<?> $returning(Collection<? extends SelectFieldOrAsterisk> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface Update<R extends Record>
    extends DMLQuery<R> {
        @Nullable
        public With $with();

        @NotNull
        public Table<R> $table();

        @CheckReturnValue
        @NotNull
        public Update<?> $table(Table<?> var1);

        @NotNull
        public UnmodifiableList<? extends Table<?>> $from();

        @CheckReturnValue
        @NotNull
        public Update<R> $from(Collection<? extends Table<?>> var1);

        @NotNull
        public UnmodifiableMap<? extends FieldOrRow, ? extends FieldOrRowOrSelect> $set();

        @CheckReturnValue
        @NotNull
        public Update<R> $set(Map<? extends FieldOrRow, ? extends FieldOrRowOrSelect> var1);

        @Nullable
        public Condition $where();

        @CheckReturnValue
        @NotNull
        public Update<R> $where(Condition var1);

        @NotNull
        public UnmodifiableList<? extends SortField<?>> $orderBy();

        @CheckReturnValue
        @NotNull
        public Update<R> $orderBy(Collection<? extends SortField<?>> var1);

        @Nullable
        public Field<? extends Number> $limit();

        @CheckReturnValue
        @NotNull
        public Update<R> $limit(Field<? extends Number> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface InsertReturning<R extends Record>
    extends ResultQuery<R> {
        @NotNull
        public Insert<?> $insert();

        @CheckReturnValue
        @NotNull
        public InsertReturning<R> $insert(Insert<?> var1);

        @NotNull
        public UnmodifiableList<? extends SelectFieldOrAsterisk> $returning();

        @CheckReturnValue
        @NotNull
        public InsertReturning<?> $returning(Collection<? extends SelectFieldOrAsterisk> var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface Insert<R extends Record>
    extends DMLQuery<R> {
        @Nullable
        public With $with();

        @NotNull
        public Table<R> $into();

        @CheckReturnValue
        @NotNull
        public Insert<?> $into(Table<?> var1);

        @NotNull
        public UnmodifiableList<? extends Field<?>> $columns();

        @CheckReturnValue
        @NotNull
        public Insert<?> $columns(Collection<? extends Field<?>> var1);

        @Nullable
        public Select<?> $select();

        @CheckReturnValue
        @NotNull
        public Insert<?> $select(Select<?> var1);

        public boolean $defaultValues();

        @CheckReturnValue
        @NotNull
        public Insert<?> $defaultValues(boolean var1);

        @NotNull
        public UnmodifiableList<? extends Row> $values();

        @CheckReturnValue
        @NotNull
        public Insert<?> $values(Collection<? extends Row> var1);

        public boolean $onDuplicateKeyIgnore();

        @CheckReturnValue
        @NotNull
        public Insert<?> $onDuplicateKeyIgnore(boolean var1);

        public boolean $onDuplicateKeyUpdate();

        @CheckReturnValue
        @NotNull
        public Insert<?> $onDuplicateKeyUpdate(boolean var1);

        @NotNull
        public UnmodifiableList<? extends Field<?>> $onConflict();

        @CheckReturnValue
        @NotNull
        public Insert<?> $onConflict(Collection<? extends Field<?>> var1);

        @Nullable
        public Condition $onConflictWhere();

        @CheckReturnValue
        @NotNull
        public Insert<?> $onConflictWhere(Condition var1);

        @NotNull
        public UnmodifiableMap<? extends FieldOrRow, ? extends FieldOrRowOrSelect> $updateSet();

        @CheckReturnValue
        @NotNull
        public Insert<?> $updateSet(Map<? extends FieldOrRow, ? extends FieldOrRowOrSelect> var1);

        @Nullable
        public Condition $updateWhere();

        @CheckReturnValue
        @NotNull
        public Insert<?> $updateWhere(Condition var1);
    }

    public static interface Aliasable<Q extends QueryPart>
    extends QueryPart {
        @NotNull
        public Q $aliased();

        @Nullable
        public Name $alias();
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface With
    extends QueryPart {
        @NotNull
        public UnmodifiableList<? extends CommonTableExpression<?>> $commonTableExpressions();

        @CheckReturnValue
        @NotNull
        public With $commonTableExpressions(UnmodifiableList<? extends CommonTableExpression<?>> var1);

        public boolean $recursive();

        @CheckReturnValue
        @NotNull
        public With $recursive(boolean var1);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface UnmodifiableCollection<Q extends QueryPart>
    extends QueryPart,
    Collection<Q> {
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface UnmodifiableMap<K extends QueryPart, V extends QueryPart>
    extends QueryPart,
    Map<K, V> {
        @NotNull
        public UnmodifiableList<Tuple2<K, V>> $tuples();
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static interface Tuple2<Q1 extends QueryPart, Q2 extends QueryPart>
    extends QueryPart {
        @NotNull
        public Q1 $1();

        @NotNull
        public Q2 $2();

        @CheckReturnValue
        @NotNull
        public Tuple2<Q1, Q2> $1(Q1 var1);

        @CheckReturnValue
        @NotNull
        public Tuple2<Q1, Q2> $2(Q2 var1);
    }
}

