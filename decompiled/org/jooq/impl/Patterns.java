/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jooq.AggregateFunction;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.False;
import org.jooq.Field;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.GroupField;
import org.jooq.Null;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Replacers;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.Traverser;
import org.jooq.Traversers;
import org.jooq.True;
import org.jooq.WindowDefinition;
import org.jooq.conf.Settings;
import org.jooq.impl.Abs;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.AbstractInList;
import org.jooq.impl.BitAnd;
import org.jooq.impl.BitNand;
import org.jooq.impl.BitNor;
import org.jooq.impl.BitXNor;
import org.jooq.impl.BitXor;
import org.jooq.impl.CaseSimple;
import org.jooq.impl.Coalesce;
import org.jooq.impl.Coerce;
import org.jooq.impl.ConditionAsField;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.Cosh;
import org.jooq.impl.Cot;
import org.jooq.impl.DSL;
import org.jooq.impl.IsDistinctFrom;
import org.jooq.impl.Lower;
import org.jooq.impl.NoCondition;
import org.jooq.impl.NotInList;
import org.jooq.impl.NullCondition;
import org.jooq.impl.Nullif;
import org.jooq.impl.QOM;
import org.jooq.impl.Shr;
import org.jooq.impl.Sign;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

@Pro
final class Patterns {
    private static final JooqLogger log = JooqLogger.getLogger(Patterns.class);
    private static final int[] OP2 = new int[]{0, 1};
    private static final Traverser<?, Boolean> CONTAINING_AGGREGATES = Traversers.recursing(p -> !(p instanceof Select), Traversers.containing(p -> p instanceof AggregateFunction && !((AbstractAggregateFunction)p).isWindow()));
    private static final Traverser<?, Boolean> CONTAINING_AGGREGATES_OR_WINDOW_FNS = Traversers.recursing(p -> !(p instanceof Select), Traversers.containing(p -> p instanceof AggregateFunction || p instanceof QOM.WindowFunction));
    private final Configuration configuration;
    private final Settings settings;
    private final boolean force;

    Patterns(Configuration configuration, boolean force) {
        this.configuration = configuration;
        this.settings = configuration.settings();
        this.force = force;
    }

    static final QueryPart replace(Configuration configuration, QueryPart p) {
        return p.$replace(new Patterns(configuration, false).transformPatterns());
    }

    final boolean check(Predicate<? super Settings> test) {
        return this.force || Boolean.TRUE.equals(test.test(this.settings));
    }

    final Replacer transformPatterns() {
        if (!Boolean.FALSE.equals(this.settings.isTransformPatternsLogging()) && log.isDebugEnabled()) {
            DSLContext logger2 = this.configuration.deriveSettings(s2 -> s2.withRenderFormatted(false).withTransformPatterns(false)).dsl();
            return Replacers.listening(this.transformPatterns0(), (p, q) -> log.debug((Object)"Pattern replacement", "[" + logger2.renderInlined((QueryPart)p) + "] => [" + logger2.renderInlined((QueryPart)q) + "]"));
        }
        return this.transformPatterns0();
    }

    final <D, E, N extends Number> Replacer transformPatterns0() {
        return Replacer.of(q -> {
            QOM.And a2;
            Condition result;
            QueryPart o;
            block539: {
                QOM.Join j;
                Function f2;
                Function<Boolean, Function> f;
                Function f1;
                Select<?> select;
                Select s2;
                Object as2;
                QOM.UOperator op;
                Object patt47788$temp;
                Object patt47514$temp;
                QOM.UOperator2 c14;
                FieldOrRowOrSelect s1;
                Object patt37834$temp;
                QOM.UReturnsNullOnNullInput b;
                QOM.UOperator2 c;
                int s3;
                LinkedHashMap m4;
                QOM.UOperator c1;
                Field patt19487$temp;
                QOM.Tuple2 t1;
                QOM.ScalarSubquery s4;
                QOM.Count x2;
                Object patt16072$temp;
                QOM.ScalarSubquery s5;
                Object patt15795$temp;
                QOM.ScalarSubquery s52;
                QOM.UOperator c2;
                Object patt14504$temp;
                QOM.UOperator d;
                Object patt13517$temp;
                QOM.UOperator<QOM.Not> n2;
                Object patt12182$temp;
                QOM.Not n22;
                Object patt11904$temp;
                QOM.UReturnsNullOnNullInput n1;
                Object patt11647$temp;
                QueryPart l;
                QOM.Count c22;
                QOM.FieldAlias a1;
                QueryPart patt10035$temp;
                if (q instanceof QOM.FieldAlias && (patt10035$temp = (a1 = (QOM.FieldAlias)q).$aliased()) instanceof QOM.FieldAlias) {
                    QOM.FieldAlias a22 = (QOM.FieldAlias)patt10035$temp;
                    return a22.$aliased().as(a1.$alias());
                }
                if (this.check(Settings::isTransformPatternsCountConstant) && q instanceof QOM.Count && Patterns.isInline((c22 = (QOM.Count)q).$field()) && !Patterns.isNull0(c22.$field())) {
                    return DSL.count();
                }
                if (this.check(Settings::isTransformPatternsTrim)) {
                    QOM.Rtrim r;
                    QOM.Ltrim l2;
                    Field<String> patt11097$temp;
                    if (q instanceof QOM.Rtrim) {
                        QOM.Rtrim r2 = (QOM.Rtrim)q;
                        Field<String> patt10617$temp = r2.$string();
                        if (patt10617$temp instanceof QOM.Ltrim) {
                            l = (QOM.Ltrim)patt10617$temp;
                            if (StringUtils.equals(r2.$characters(), l.$characters())) {
                                if (l.$characters() == null) {
                                    return DSL.trim(l.$string());
                                }
                                return DSL.trim(l.$string(), l.$characters());
                            }
                        }
                    } else if (q instanceof QOM.Ltrim && (patt11097$temp = (l2 = (QOM.Ltrim)q).$string()) instanceof QOM.Rtrim && StringUtils.equals((r = (QOM.Rtrim)patt11097$temp).$characters(), l2.$characters())) {
                        if (r.$characters() == null) {
                            return DSL.trim(r.$string());
                        }
                        return DSL.trim(r.$string(), r.$characters());
                    }
                }
                if (this.check(Settings::isTransformPatternsNotNot) && q instanceof QOM.Not && (patt11647$temp = (n1 = (QOM.Not)q).$arg1()) instanceof QOM.Not) {
                    QOM.Not n222 = (QOM.Not)patt11647$temp;
                    return (QueryPart)n222.$arg1();
                }
                if (this.check(Settings::isTransformPatternsNotAnd) && q instanceof QOM.Not && (patt11904$temp = (n22 = (QOM.Not)q).$arg1()) instanceof QOM.And) {
                    QOM.And a3 = (QOM.And)patt11904$temp;
                    return DSL.not((Condition)a3.$arg1()).or(DSL.not((Condition)a3.$arg2()));
                }
                if (this.check(Settings::isTransformPatternsNotOr) && q instanceof QOM.Not && (patt12182$temp = (n2 = (QOM.Not)q).$arg1()) instanceof QOM.Or) {
                    QOM.Or a3 = (QOM.Or)patt12182$temp;
                    return DSL.not((Condition)a3.$arg1()).and(DSL.not((Condition)a3.$arg2()));
                }
                if (this.check(Settings::isTransformPatternsNotComparison) && q instanceof QOM.Not) {
                    n1 = (QOM.Not)q;
                    Object patt12470$temp = n1.$arg1();
                    if (patt12470$temp instanceof QOM.Eq) {
                        QOM.Eq cc = (QOM.Eq)patt12470$temp;
                        return ((Field)cc.$arg1()).ne((Field)cc.$arg2());
                    }
                    Object patt12621$temp = n1.$arg1();
                    if (patt12621$temp instanceof QOM.Ne) {
                        QOM.Ne cc = (QOM.Ne)patt12621$temp;
                        return ((Field)cc.$arg1()).eq((Field)cc.$arg2());
                    }
                    Object patt12772$temp = n1.$arg1();
                    if (patt12772$temp instanceof QOM.Gt) {
                        QOM.Gt cc = (QOM.Gt)patt12772$temp;
                        return ((Field)cc.$arg1()).le((Field)cc.$arg2());
                    }
                    Object patt12923$temp = n1.$arg1();
                    if (patt12923$temp instanceof QOM.Ge) {
                        QOM.Ge ge = (QOM.Ge)patt12923$temp;
                        return ((Field)ge.$arg1()).lt((Field)ge.$arg2());
                    }
                    Object patt13074$temp = n1.$arg1();
                    if (patt13074$temp instanceof QOM.Lt) {
                        QOM.Lt lt = (QOM.Lt)patt13074$temp;
                        return ((Field)lt.$arg1()).ge((Field)lt.$arg2());
                    }
                    Object patt13225$temp = n1.$arg1();
                    if (patt13225$temp instanceof QOM.Le) {
                        QOM.Le cc = (QOM.Le)patt13225$temp;
                        return ((Field)cc.$arg1()).gt((Field)cc.$arg2());
                    }
                }
                if (this.check(Settings::isTransformPatternsNotNotDistinct) && q instanceof QOM.Not && (patt13517$temp = (n1 = (QOM.Not)q).$arg1()) instanceof QOM.IsNotDistinctFrom) {
                    QOM.IsNotDistinctFrom d2 = (QOM.IsNotDistinctFrom)patt13517$temp;
                    return ((Field)d2.$arg1()).isDistinctFrom((Field)d2.$arg2());
                }
                if (this.check(Settings::isTransformPatternsDistinctFromNull)) {
                    if (q instanceof IsDistinctFrom) {
                        d = (IsDistinctFrom)q;
                        if (Patterns.isNull((QueryPart)((IsDistinctFrom)d).$arg1())) {
                            return ((IsDistinctFrom)d).$arg2().isNotNull();
                        }
                        if (Patterns.isNull((QueryPart)((IsDistinctFrom)d).$arg2())) {
                            return ((IsDistinctFrom)d).$arg1().isNotNull();
                        }
                    } else if (q instanceof QOM.IsNotDistinctFrom) {
                        QOM.IsNotDistinctFrom d2 = (QOM.IsNotDistinctFrom)q;
                        if (Patterns.isNull((QueryPart)d2.$arg1())) {
                            return ((Field)d2.$arg2()).isNull();
                        }
                        if (Patterns.isNull((QueryPart)d2.$arg2())) {
                            return ((Field)d2.$arg1()).isNull();
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsScalarSubqueryCountAsteriskGtZero) && q instanceof QOM.CompareCondition && (patt14504$temp = (c2 = (QOM.CompareCondition)q).$arg1()) instanceof QOM.ScalarSubquery && ((Select)(s52 = (QOM.ScalarSubquery)patt14504$temp).$arg1()).$select().equals(Arrays.asList(DSL.count())) && ((Select)s52.$arg1()).$groupBy().isEmpty() && ((Select)s52.$arg1()).$having() == null) {
                    if (Patterns.isInlineVal((Field)c2.$arg2(), 0)) {
                        if (c2 instanceof QOM.Gt || c2 instanceof QOM.Ne) {
                            return DSL.exists(((Select)s52.$arg1()).$select(Arrays.asList(DSL.one())));
                        }
                        if (c2 instanceof QOM.Eq || c2 instanceof QOM.Le) {
                            return DSL.notExists(((Select)s52.$arg1()).$select(Arrays.asList(DSL.one())));
                        }
                    } else if (Patterns.isInlineVal((Field)c2.$arg2(), 1)) {
                        if (c2 instanceof QOM.Ge) {
                            return DSL.exists(((Select)s52.$arg1()).$select(Arrays.asList(DSL.one())));
                        }
                        if (c2 instanceof QOM.Lt) {
                            return DSL.notExists(((Select)s52.$arg1()).$select(Arrays.asList(DSL.one())));
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsScalarSubqueryCountExpressionGtZero) && q instanceof QOM.CompareCondition && (patt15795$temp = (c2 = (QOM.CompareCondition)q).$arg1()) instanceof QOM.ScalarSubquery && ((Select)(s5 = (QOM.ScalarSubquery)patt15795$temp).$arg1()).$select().size() == 1 && ((Select)s5.$arg1()).$groupBy().isEmpty() && ((Select)s5.$arg1()).$having() == null && (patt16072$temp = ((Select)s5.$arg1()).$select().get(0)) instanceof QOM.Count && (x2 = (QOM.Count)patt16072$temp).$field() != AbstractAggregateFunction.ASTERISK.get()) {
                    if (Patterns.isInlineVal((Field)c2.$arg2(), 0)) {
                        if (c2 instanceof QOM.Gt || c2 instanceof QOM.Ne) {
                            return DSL.exists(((Select)s5.$arg1()).$select(Arrays.asList(DSL.one())).$where(DSL.and(((Select)s5.$arg1()).$where(), x2.$field().isNotNull())));
                        }
                        if (c2 instanceof QOM.Eq || c2 instanceof QOM.Le) {
                            return DSL.notExists(((Select)s5.$arg1()).$select(Arrays.asList(DSL.one())).$where(DSL.and(((Select)s5.$arg1()).$where(), x2.$field().isNotNull())));
                        }
                    } else if (Patterns.isInlineVal((Field)c2.$arg2(), 1)) {
                        if (c2 instanceof QOM.Ge) {
                            return DSL.exists(((Select)s5.$arg1()).$select(Arrays.asList(DSL.one())).$where(DSL.and(((Select)s5.$arg1()).$where(), x2.$field().isNotNull())));
                        }
                        if (c2 instanceof QOM.Lt) {
                            return DSL.notExists(((Select)s5.$arg1()).$select(Arrays.asList(DSL.one())).$where(DSL.and(((Select)s5.$arg1()).$where(), x2.$field().isNotNull())));
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsEmptyScalarSubquery) && q instanceof QOM.ScalarSubquery && Patterns.isEmpty((Select)(s4 = (QOM.ScalarSubquery)q).$arg1())) {
                    return DSL.inline(null, ((Select)s4.$arg1()).getSelect().get(0).getDataType());
                }
                if (this.check(Settings::isTransformPatternsCaseSearchedToCaseSimple) && q instanceof QOM.CaseSearched && !(c2 = (QOM.CaseSearched)q).$when().isEmpty() && (t1 = c2.$when().$first()).$1() instanceof QOM.Eq) {
                    QOM.Eq e1 = (QOM.Eq)t1.$1();
                    if (Tools.allMatch(c2.$when().$removeFirst(), e -> e.$1() instanceof QOM.Eq && ((Field)((QOM.Eq)e.$1()).$arg1()).equals(e1.$arg1()))) {
                        QOM.CaseSimple s6 = new CaseSimple((Field)e1.$arg1(), c2.getDataType()).$when(QOM.unmodifiable(Tools.map(c2.$when(), e -> QOM.tuple((Field)((QOM.Eq)e.$1()).$arg2(), (Field)e.$2()))));
                        if (c2.$else() != null) {
                            return s6.$else(c2.$else());
                        }
                        return s6;
                    }
                }
                if (this.check(Settings::isTransformPatternsCaseElseNull)) {
                    QOM.CaseSimple c3;
                    if (q instanceof QOM.CaseSearched) {
                        c2 = (QOM.CaseSearched)q;
                        if (Patterns.isNull(c2.$else())) {
                            return c2.$else(null);
                        }
                    } else if (q instanceof QOM.CaseSimple && Patterns.isNull((c3 = (QOM.CaseSimple)q).$else())) {
                        return c3.$else(null);
                    }
                }
                if (this.check(Settings::isTransformPatternsFlattenCase) && q instanceof QOM.CaseSearched && (patt19487$temp = (c1 = (QOM.CaseSearched)q).$else()) instanceof QOM.CaseSearched) {
                    QOM.CaseSearched c222 = (QOM.CaseSearched)patt19487$temp;
                    return c1.$when(c1.$when().$concat(c222.$when())).$else(c222.$else());
                }
                if (this.check(Settings::isTransformPatternsUnreachableCaseClauses)) {
                    int s7;
                    if (q instanceof QOM.CaseSearched) {
                        c2 = (QOM.CaseSearched)q;
                        if (c2.$when().isEmpty()) {
                            if (c2.$else() == null) {
                                return DSL.inline(null, c2.$dataType());
                            }
                            return c2.$else();
                        }
                        int s8 = c2.$when().size();
                        LinkedHashMap<Condition, Field> m42 = s8 == 1 ? null : new LinkedHashMap<Condition, Field>();
                        for (int i2 = 0; i2 < s8; ++i2) {
                            QOM.Tuple2 tuple2 = (QOM.Tuple2)c2.$when().get(i2);
                            if (Patterns.isTrue((Field)tuple2.$1())) {
                                return c2.$when(QOM.unmodifiable(c2.$when().subList(0, i2))).$else((Field)tuple2.$2());
                            }
                            if (Patterns.isFalse((Field)tuple2.$1())) {
                                return c2.$when(c2.$when().$remove(i2));
                            }
                            if (m42 == null) continue;
                            m42.putIfAbsent((Condition)tuple2.$1(), (Field)tuple2.$2());
                        }
                        if (m42 != null && m42.size() != c2.$when().size()) {
                            return c2.$when(QOM.unmodifiable(Tools.map(m42.entrySet(), e -> QOM.tuple((Condition)e.getKey(), (Field)e.getValue()))));
                        }
                    } else if (q instanceof QOM.CaseSimple && (s7 = (c2 = (QOM.CaseSimple)q).$when().size()) > 1) {
                        m4 = new LinkedHashMap();
                        c2.$when().forEach(t2 -> m42.putIfAbsent((Condition)((Field)t2.$1()), (Field)t2.$2()));
                        if (m4.size() != s7) {
                            return c2.$when(QOM.unmodifiable(Tools.map(m4.entrySet(), e -> QOM.tuple((Field)e.getKey(), (Field)e.getValue()))));
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsUnreachableDecodeClauses) && q instanceof QOM.Decode && (s3 = (d = (QOM.Decode)q).$when().size()) > 1) {
                    m4 = new LinkedHashMap();
                    d.$when().forEach(t2 -> m42.putIfAbsent((Condition)((Field)t2.$1()), (Field)t2.$2()));
                    if (m4.size() != s3) {
                        return d.$when(QOM.unmodifiable(Tools.map(m4.entrySet(), e -> QOM.tuple((Field)e.getKey(), (Field)e.getValue()))));
                    }
                }
                if (this.check(Settings::isTransformPatternsCaseMergeWhenElse)) {
                    if (q instanceof QOM.CaseSearched) {
                        c2 = (QOM.CaseSearched)q;
                        Field e3 = c2.$else();
                        l = c2.$when().$last();
                        if (e3 != null && l != null && e3.equals(l.$2())) {
                            if (c2.$when().size() == 1) {
                                return e3;
                            }
                            return c2.$when(c2.$when().$removeLast());
                        }
                    } else if (q instanceof QOM.CaseSimple) {
                        c2 = (QOM.CaseSimple)q;
                        Field e4 = c2.$else();
                        l = c2.$when().$last();
                        if (e4 != null && l != null && e4.equals(l.$2())) {
                            if (c2.$when().size() == 1) {
                                return e4;
                            }
                            return c2.$when(c2.$when().$removeLast());
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsCaseMergeWhenWhen) && q instanceof QOM.CaseSearched && (c2 = (QOM.CaseSearched)q).$when().size() > 1) {
                    for (int i3 = 0; i3 < c2.$when().size() - 1; ++i3) {
                        QOM.Tuple2 w1 = (QOM.Tuple2)c2.$when().get(i3);
                        QOM.Tuple2 w2 = (QOM.Tuple2)c2.$when().get(i3 + 1);
                        if (!((Field)w1.$2()).equals(w2.$2())) continue;
                        return c2.$when(c2.$when().$set(i3, w1.$1(((Condition)w1.$1()).or((Condition)w2.$1()))).$remove(i3 + 1));
                    }
                }
                if (this.check(Settings::isTransformPatternsCaseToCaseAbbreviation)) {
                    if (q instanceof QOM.CaseSearched) {
                        c2 = (QOM.CaseSearched)q;
                        if (c2.$when().size() == 1) {
                            Object Q1;
                            QOM.Tuple2 t4 = (QOM.Tuple2)c2.$when().get(0);
                            if (Patterns.isTrue((Field)t4.$2())) {
                                if (c2.$else() == null) {
                                    return ((Condition)t4.$1()).or(DSL.nullCondition());
                                }
                                if (Patterns.isFalse(c2.$else())) {
                                    return DSL.nvl(DSL.field((Condition)t4.$1()), c2.$else());
                                }
                                return DSL.condition(DSL.nvl((Field)t4.$1(), DSL.inline(false))).or(DSL.condition(c2.$else()));
                            }
                            if (Patterns.isFalse((Field)t4.$2())) {
                                if (c2.$else() == null) {
                                    return DSL.not(((Condition)t4.$1()).or(DSL.nullCondition()));
                                }
                                if (Patterns.isTrue(c2.$else())) {
                                    return DSL.nvl(DSL.field(DSL.not((Condition)t4.$1())), c2.$else());
                                }
                            }
                            if ((Q1 = t4.$1()) instanceof QOM.IsNull) {
                                QOM.IsNull n3 = (QOM.IsNull)Q1;
                                if (((Field)n3.$arg1()).equals(c2.$else())) {
                                    return DSL.nvl((Field)n3.$arg1(), (Field)t4.$2());
                                }
                                return DSL.nvl2((Field)n3.$arg1(), c2.$else(), (Field)t4.$2());
                            }
                            Object Q12 = t4.$1();
                            if (Q12 instanceof QOM.IsNotNull) {
                                QOM.IsNotNull n4 = (QOM.IsNotNull)Q12;
                                return DSL.nvl2((Field)n4.$arg1(), (Field)t4.$2(), c2.$else());
                            }
                            Object Q13 = t4.$1();
                            if (Q13 instanceof QOM.Eq) {
                                QOM.Eq eq2 = (QOM.Eq)Q13;
                                if (Patterns.isNull(t4.$2())) {
                                    if (((Field)eq2.$arg1()).equals(c2.$else())) {
                                        return DSL.nullif((Field)eq2.$arg1(), (Field)eq2.$arg2());
                                    }
                                    if (((Field)eq2.$arg2()).equals(c2.$else())) {
                                        return DSL.nullif((Field)eq2.$arg2(), (Field)eq2.$arg1());
                                    }
                                }
                            }
                        }
                    } else if (q instanceof QOM.CaseSimple) {
                        c2 = (QOM.CaseSimple)q;
                        if (c2.$when().size() == 1) {
                            QOM.Tuple2 t5 = (QOM.Tuple2)c2.$when().get(0);
                            if (Patterns.isTrue((Field)t5.$2())) {
                                l = c2.$value().eq((Field)t5.$1());
                                if (c2.$else() == null) {
                                    return l.or(DSL.nullCondition());
                                }
                                if (Patterns.isFalse(c2.$else())) {
                                    return DSL.nvl(l, c2.$else());
                                }
                                return DSL.condition(DSL.nvl(l, DSL.inline(false))).or(DSL.condition(c2.$else()));
                            }
                            if (Patterns.isFalse((Field)t5.$2())) {
                                if (c2.$else() == null) {
                                    return DSL.not(c2.$value().eq((Field)t5.$1()).or(DSL.nullCondition()));
                                }
                                if (Patterns.isTrue(c2.$else())) {
                                    return DSL.nvl(DSL.field(c2.$value().ne((Field)t5.$1())), c2.$else());
                                }
                            }
                            if (Patterns.isNull(t5.$2())) {
                                if (c2.$value().equals(c2.$else())) {
                                    return DSL.nullif(c2.$value(), (Field)t5.$1());
                                }
                                if (((Field)t5.$1()).equals(c2.$else())) {
                                    return DSL.nullif((Field)t5.$1(), c2.$value());
                                }
                            }
                        }
                        if (Tools.allMatch(c2.$when(), (e, i) -> Patterns.isInlineVal((Field)e.$1(), i + 1))) {
                            Field<Field<?>> choose = DSL.choose(c2.$value(), Tools.map(c2.$when(), e -> (Field)e.$2()).toArray(Tools.EMPTY_FIELD));
                            if (c2.$else() == null) {
                                return choose;
                            }
                            return DSL.nvl(choose, c2.$else());
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsSimplifyCaseAbbreviation)) {
                    if (q instanceof QOM.Or) {
                        o = (QOM.Or)q;
                        for (int i4 : OP2) {
                            Condition patt28746$temp;
                            Condition patt28508$temp = Patterns.arg1(o, i4);
                            if (!(patt28508$temp instanceof QOM.IsNull)) continue;
                            n = (QOM.IsNull)patt28508$temp;
                            Field<Boolean> patt28588$temp = Patterns.extractField(Patterns.arg2(o, i4));
                            if (!(patt28588$temp instanceof QOM.Nvl) || !Patterns.isFalse((c = (QOM.Nvl)patt28588$temp).$defaultValue()) || !((patt28746$temp = Patterns.extractCondition(c.$value())) instanceof QOM.Eq)) continue;
                            e = (QOM.Eq)patt28746$temp;
                            if (((Field)n.$arg1()).equals(e.$arg1())) {
                                return DSL.nullif((Field)n.$arg1(), (Field)e.$arg2()).isNull();
                            }
                            if (!((Field)n.$arg1()).equals(e.$arg2())) continue;
                            return DSL.nullif((Field)n.$arg1(), (Field)e.$arg1()).isNull();
                        }
                    } else if (q instanceof QOM.And) {
                        QOM.And a4 = (QOM.And)q;
                        for (int i5 : OP2) {
                            Condition patt29634$temp;
                            Condition patt29394$temp = Patterns.arg1(a4, i5);
                            if (!(patt29394$temp instanceof QOM.IsNotNull)) continue;
                            n = (QOM.IsNotNull)patt29394$temp;
                            Field<Boolean> patt29477$temp = Patterns.extractField(Patterns.arg2(a4, i5));
                            if (!(patt29477$temp instanceof QOM.Nvl) || !Patterns.isTrue((c = (QOM.Nvl)patt29477$temp).$defaultValue()) || !((patt29634$temp = Patterns.extractCondition(c.$value())) instanceof QOM.Ne)) continue;
                            e = (QOM.Ne)patt29634$temp;
                            if (((Field)n.$arg1()).equals(e.$arg1())) {
                                return DSL.nullif((Field)n.$arg1(), (Field)e.$arg2()).isNotNull();
                            }
                            if (!((Field)n.$arg1()).equals(e.$arg2())) continue;
                            return DSL.nullif((Field)n.$arg1(), (Field)e.$arg1()).isNotNull();
                        }
                    } else if (q instanceof QOM.Nvl) {
                        QOM.Nvl c4 = (QOM.Nvl)q;
                        Condition patt30265$temp = Patterns.extractCondition(c4.$value());
                        if (patt30265$temp instanceof QOM.Eq) {
                            QOM.Eq eq3 = (QOM.Eq)patt30265$temp;
                            Condition patt30351$temp = Patterns.extractCondition(c4.$defaultValue());
                            if (patt30351$temp instanceof QOM.IsNull) {
                                n = (QOM.IsNull)patt30351$temp;
                                if (((Field)n.$arg1()).equals(eq3.$arg1())) {
                                    return DSL.nullif(n.$arg1(), (Field)eq3.$arg2()).isNull();
                                }
                                if (n.$arg1().equals(eq3.$arg2())) {
                                    return DSL.nullif(n.$arg1(), (Field)eq3.$arg1()).isNull();
                                }
                            }
                        } else {
                            Condition patt30763$temp = Patterns.extractCondition(c4.$value());
                            if (patt30763$temp instanceof QOM.Ne) {
                                QOM.Ne e6 = (QOM.Ne)patt30763$temp;
                                Condition patt30849$temp = Patterns.extractCondition(c4.$defaultValue());
                                if (patt30849$temp instanceof QOM.IsNotNull) {
                                    n = (QOM.IsNotNull)patt30849$temp;
                                    if (n.$arg1().equals(e6.$arg1())) {
                                        return DSL.nullif(n.$arg1(), (Field)e6.$arg2()).isNotNull();
                                    }
                                    if (n.$arg1().equals(e6.$arg2())) {
                                        return DSL.nullif(n.$arg1(), (Field)e6.$arg1()).isNotNull();
                                    }
                                }
                            }
                        }
                    } else if (q instanceof QOM.IsNotDistinctFrom) {
                        QOM.IsNotDistinctFrom e7 = (QOM.IsNotDistinctFrom)q;
                        for (int i6 : OP2) {
                            Object patt31373$temp = Patterns.arg1(e7, i6);
                            if (!(patt31373$temp instanceof QOM.Nvl) || !(n = (QOM.Nvl)patt31373$temp).$defaultValue().equals(Patterns.arg2(e7, i6))) continue;
                            return DSL.nullif(n.$value(), (Field)Patterns.arg2(e7, i6)).isNull();
                        }
                    } else if (q instanceof IsDistinctFrom) {
                        IsDistinctFrom isDistinctFrom = (IsDistinctFrom)q;
                        for (int i7 : OP2) {
                            Object patt31753$temp = Patterns.arg1(isDistinctFrom, i7);
                            if (!(patt31753$temp instanceof QOM.Nvl) || !(n = (QOM.Nvl)patt31753$temp).$defaultValue().equals(Patterns.arg2(isDistinctFrom, i7))) continue;
                            return DSL.nullif(n.$value(), (Field)Patterns.arg2(isDistinctFrom, i7)).isNotNull();
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsFlattenCaseAbbreviation)) {
                    if (q instanceof QOM.Nvl) {
                        QOM.Nvl n12 = (QOM.Nvl)q;
                        Field patt32211$temp = n12.$value();
                        if (patt32211$temp instanceof QOM.Nvl) {
                            QOM.Nvl n23 = (QOM.Nvl)patt32211$temp;
                            return DSL.coalesce(n23.$value(), n23.$defaultValue(), n12.$defaultValue());
                        }
                        Field patt32399$temp = n12.$defaultValue();
                        if (patt32399$temp instanceof QOM.Nvl) {
                            QOM.Nvl n24 = (QOM.Nvl)patt32399$temp;
                            return DSL.coalesce(n12.$value(), n24.$value(), n24.$defaultValue());
                        }
                        Field patt32573$temp = n12.$value();
                        if (patt32573$temp instanceof Coalesce) {
                            Coalesce coalesce = (Coalesce)patt32573$temp;
                            return new Coalesce(coalesce.$arg1().$concat(QOM.unmodifiable(Arrays.asList(n12.$defaultValue()))));
                        }
                        Field patt32812$temp = n12.$defaultValue();
                        if (patt32812$temp instanceof Coalesce) {
                            Coalesce coalesce = (Coalesce)patt32812$temp;
                            return new Coalesce(QOM.unmodifiable(Arrays.asList(n12.$value())).$concat((Collection<Field>)coalesce.$arg1()));
                        }
                    } else if (q instanceof Coalesce) {
                        c1 = (Coalesce)q;
                        Object l3 = ((Coalesce)c1).$arg1();
                        for (int i8 = 0; i8 < l3.size(); ++i8) {
                            Object e2 = l3.get(i8);
                            if (e2 instanceof QOM.Nvl) {
                                QOM.Nvl n25 = (QOM.Nvl)e2;
                                return new Coalesce(l3.$setAll(i8, Arrays.asList(n25.$value(), n25.$defaultValue())));
                            }
                            Object e3 = l3.get(i8);
                            if (!(e3 instanceof Coalesce)) continue;
                            Coalesce coalesce = (Coalesce)e3;
                            return new Coalesce(l3.$setAll(i8, coalesce.$arg1()));
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsFlattenDecode) && q instanceof QOM.Decode && (d = (QOM.Decode)q).$else() instanceof QOM.Decode) {
                    QOM.Decode e8 = (QOM.Decode)d.$else();
                    if (((Field)d.$arg1()).equals(e8.$arg1())) {
                        return ((QOM.Decode)d.$arg2(((QOM.UnmodifiableList)d.$arg2()).$concat((QOM.UnmodifiableList)e8.$arg2()))).$else(e8.$else());
                    }
                }
                if (this.check(Settings::isTransformPatternsTrivialCaseAbbreviation)) {
                    if (q instanceof QOM.Nvl) {
                        n2 = (QOM.Nvl)q;
                        if (Patterns.isNull(n2.$value())) {
                            return n2.$defaultValue();
                        }
                        if (Patterns.isInlineVal0(n2.$value(), v -> v != null)) {
                            return n2.$value();
                        }
                    } else if (q instanceof Coalesce) {
                        void var8_192;
                        Coalesce c5 = (Coalesce)q;
                        Object l4 = c5.$arg1();
                        int n3 = l4.size();
                        if (n3 == 1) {
                            return (QueryPart)l4.get(0);
                        }
                        boolean bl = false;
                        while (var8_192 < n3) {
                            if (Patterns.isNull((QueryPart)l4.get((int)var8_192))) {
                                return new Coalesce(l4.$remove((int)var8_192));
                            }
                            if (var8_192 < n3 - 1 && Patterns.isInlineVal0((Field)l4.get((int)var8_192), v -> v != null)) {
                                return new Coalesce(l4.subList(0, (int)(var8_192 + true)));
                            }
                            ++var8_192;
                        }
                    } else if (q instanceof Nullif) {
                        Nullif n5 = (Nullif)q;
                        if (Patterns.isNull(n5.$value())) {
                            return DSL.inline(null, n5.$value());
                        }
                        if (Patterns.isNull(n5.$other())) {
                            return n5.$value();
                        }
                        if (n5.$value().equals(n5.$other())) {
                            return DSL.inline(null, n5.$value());
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsTrivialBitwiseOperations)) {
                    if (q instanceof QOM.BitOr) {
                        b = (QOM.BitOr)q;
                        if (Patterns.isInlineVal((Field)b.$arg1(), 0)) {
                            return (QueryPart)b.$arg2();
                        }
                        if (Patterns.isInlineVal((Field)b.$arg2(), 0)) {
                            return (QueryPart)b.$arg1();
                        }
                    } else if (q instanceof BitAnd) {
                        BitAnd b2 = (BitAnd)q;
                        if (Patterns.isInlineVal(b2.$arg1(), -1)) {
                            return b2.$arg2();
                        }
                        if (Patterns.isInlineVal(b2.$arg2(), -1)) {
                            return b2.$arg1();
                        }
                    } else if (q instanceof BitXor) {
                        BitXor b3 = (BitXor)q;
                        if (Patterns.isInlineVal(b3.$arg1(), 0)) {
                            return b3.$arg2();
                        }
                        if (Patterns.isInlineVal(b3.$arg2(), 0)) {
                            return b3.$arg1();
                        }
                        if (Patterns.isInlineVal(b3.$arg1(), -1)) {
                            return DSL.bitNot(b3.$arg2());
                        }
                        if (Patterns.isInlineVal(b3.$arg2(), -1)) {
                            return DSL.bitNot(b3.$arg1());
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsBitSet)) {
                    if (q instanceof QOM.BitOr) {
                        b = (QOM.BitOr)q;
                        for (int n4 : OP2) {
                            QOM.Shl s9;
                            Field field2 = (Field)Patterns.arg2(b, n4);
                            if (!(field2 instanceof QOM.Shl) || !Patterns.isInlineVal((Field)(s9 = (QOM.Shl)field2).$arg1(), 1)) continue;
                            return DSL.bitSet((Field)Patterns.arg1(b, n4), (Field)s9.$arg2());
                        }
                    } else if (q instanceof BitAnd) {
                        b = (BitAnd)q;
                        for (int n5 : OP2) {
                            QOM.Shl s10;
                            QOM.BitNot n6;
                            Object patt37398$temp;
                            Field field3 = (Field)Patterns.arg2(b, n5);
                            if (!(field3 instanceof QOM.BitNot) || !((patt37398$temp = (n6 = (QOM.BitNot)field3).$arg1()) instanceof QOM.Shl) || !Patterns.isInlineVal((Field)(s10 = (QOM.Shl)patt37398$temp).$arg1(), 1)) continue;
                            return DSL.bitSet((Field)Patterns.arg1(b, n5), (Field<? extends Number>)((Field)s10.$arg2()), DSL.inline(0));
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsBitGet) && q instanceof Shr && (patt37834$temp = ((Shr)(s1 = (Shr)q)).$arg1()) instanceof BitAnd) {
                    void var8_196;
                    BitAnd a5;
                    BitAnd b4 = a5 = (BitAnd)patt37834$temp;
                    int[] l4 = OP2;
                    int n6 = l4.length;
                    boolean bl = false;
                    while (var8_196 < n6) {
                        QOM.Shl s22;
                        int i12 = l4[var8_196];
                        Field a25 = (Field)Patterns.arg2(b4, i12);
                        if (a25 instanceof QOM.Shl && Patterns.isInlineVal((Field)(s22 = (QOM.Shl)a25).$arg1(), 1) && ((Field)s22.$arg2()).equals(((Shr)s1).$arg2())) {
                            return DSL.bitGet((Field)Patterns.arg1(b4, i12), (Field<? extends Number>)((Shr)s1).$arg2());
                        }
                        ++var8_196;
                    }
                }
                if (this.check(Settings::isTransformPatternsTrivialPredicates)) {
                    if (q instanceof Select) {
                        s1 = (Select)q;
                        FieldOrRowOrSelect s23 = s1;
                        if (Patterns.isTrue(s23.$where())) {
                            s23 = s23.$where(DSL.noCondition());
                        }
                        if (Patterns.isTrue(s23.$having()) && (!s23.$groupBy().isEmpty() || Patterns.hasAggregateFunctions(s23))) {
                            s23 = s23.$having(DSL.noCondition());
                        }
                        if (Patterns.isTrue(s23.$qualify())) {
                            s23 = s23.$qualify(DSL.noCondition());
                        }
                        if (s1 != s23) {
                            return s23;
                        }
                    } else if (q instanceof QOM.Not) {
                        QOM.Not c6 = (QOM.Not)q;
                        if (Patterns.isTrue((Field)c6.$arg1())) {
                            return DSL.falseCondition();
                        }
                        if (Patterns.isFalse((Field)c6.$arg1())) {
                            return DSL.trueCondition();
                        }
                        if (Patterns.isNull((Condition)c6.$arg1())) {
                            return DSL.nullCondition();
                        }
                    } else if (q instanceof QOM.And) {
                        QOM.And c7 = (QOM.And)q;
                        if (Patterns.isFalse((Field)c7.$arg1()) || Patterns.isFalse((Field)c7.$arg2())) {
                            return DSL.falseCondition();
                        }
                        if (Patterns.isTrue((Field)c7.$arg1())) {
                            return (QueryPart)c7.$arg2();
                        }
                        if (Patterns.isTrue((Field)c7.$arg2())) {
                            return (QueryPart)c7.$arg1();
                        }
                        if (Patterns.isNull((Condition)c7.$arg1()) && Patterns.isNull((Condition)c7.$arg2())) {
                            return DSL.nullCondition();
                        }
                        if (((Condition)c7.$arg1()).$traverse(Traversers.recursingChildren(p -> p instanceof QOM.Or, Traversers.containing(p -> Patterns.same(p, (QueryPart)c7.$arg2())))).booleanValue()) {
                            return (QueryPart)c7.$arg2();
                        }
                        if (((Condition)c7.$arg2()).$traverse(Traversers.recursingChildren(p -> p instanceof QOM.Or, Traversers.containing(p -> Patterns.same(p, (QueryPart)c7.$arg1())))).booleanValue()) {
                            return (QueryPart)c7.$arg1();
                        }
                        if (((Condition)c7.$arg1()).$traverse(Traversers.recursingChildren(p -> p instanceof QOM.And, Traversers.containing(p -> Patterns.same(p, (QueryPart)c7.$arg2())))).booleanValue()) {
                            return (QueryPart)c7.$arg1();
                        }
                        Object patt40368$temp = c7.$arg1();
                        if (patt40368$temp instanceof QOM.Eq) {
                            QOM.Eq e1 = (QOM.Eq)patt40368$temp;
                            Object patt40429$temp = c7.$arg2();
                            if (patt40429$temp instanceof QOM.Eq) {
                                QOM.Eq e22 = (QOM.Eq)patt40429$temp;
                                if (((Field)e1.$arg1()).equals(e22.$arg1()) && Patterns.isInline((QueryPart)e1.$arg2()) && Patterns.isInline((QueryPart)e22.$arg2()) && !((Field)e1.$arg2()).equals(e22.$arg2())) {
                                    return ((Field)e1.$arg1()).ne((Field)e22.$arg1());
                                }
                            }
                        }
                    } else if (q instanceof QOM.Or) {
                        QOM.Or c8 = (QOM.Or)q;
                        if (Patterns.isTrue((Field)c8.$arg1()) || Patterns.isTrue((Field)c8.$arg2())) {
                            return DSL.trueCondition();
                        }
                        if (Patterns.isFalse((Field)c8.$arg1())) {
                            return (QueryPart)c8.$arg2();
                        }
                        if (Patterns.isFalse((Field)c8.$arg2())) {
                            return (QueryPart)c8.$arg1();
                        }
                        if (Patterns.isNull((Condition)c8.$arg1()) && Patterns.isNull((Condition)c8.$arg2())) {
                            return DSL.nullCondition();
                        }
                        if (((Condition)c8.$arg1()).equals(c8.$arg2())) {
                            return (QueryPart)c8.$arg1();
                        }
                        if (((Condition)c8.$arg1()).$traverse(Traversers.recursingChildren(p -> p instanceof QOM.And, Traversers.containing(p -> Patterns.same(p, (QueryPart)c8.$arg2())))).booleanValue()) {
                            return (QueryPart)c8.$arg2();
                        }
                        if (((Condition)c8.$arg2()).$traverse(Traversers.recursingChildren(p -> p instanceof QOM.And, Traversers.containing(p -> Patterns.same(p, (QueryPart)c8.$arg1())))).booleanValue()) {
                            return (QueryPart)c8.$arg1();
                        }
                        if (((Condition)c8.$arg1()).$traverse(Traversers.recursingChildren(p -> p instanceof QOM.Or, Traversers.containing(p -> Patterns.same(p, (QueryPart)c8.$arg2())))).booleanValue()) {
                            return (QueryPart)c8.$arg1();
                        }
                    } else if (q instanceof QOM.IsNull) {
                        QOM.IsNull isNull = (QOM.IsNull)q;
                        if (Patterns.isInline((QueryPart)isNull.$arg1())) {
                            return Patterns.isNull0((Field)isNull.$arg1()) ? DSL.trueCondition() : DSL.falseCondition();
                        }
                    } else if (q instanceof QOM.IsNotNull) {
                        QOM.IsNotNull isNotNull = (QOM.IsNotNull)q;
                        if (Patterns.isInline((QueryPart)isNotNull.$arg1())) {
                            return Patterns.isNull0((Field)isNotNull.$arg1()) ? DSL.falseCondition() : DSL.trueCondition();
                        }
                    } else if (q instanceof QOM.CompareCondition) {
                        QOM.CompareCondition c11 = (QOM.CompareCondition)q;
                        boolean i1 = Patterns.isInline((QueryPart)c11.$arg1());
                        boolean i2 = Patterns.isInline((QueryPart)c11.$arg2());
                        if (!(q instanceof IsDistinctFrom) && !(q instanceof QOM.IsNotDistinctFrom) && (i1 && Patterns.isNull0((Field)c11.$arg1()) || i2 && Patterns.isNull0((Field)c11.$arg2()))) {
                            return DSL.nullCondition();
                        }
                        if (i1 && i2) {
                            if (q instanceof QOM.Eq) {
                                return ((Field)c11.$arg1()).equals(c11.$arg2()) ? DSL.trueCondition() : DSL.falseCondition();
                            }
                            if (q instanceof QOM.Ne) {
                                return ((Field)c11.$arg1()).equals(c11.$arg2()) ? DSL.falseCondition() : DSL.trueCondition();
                            }
                        }
                        if (((Field)c11.$arg1()).equals(c11.$arg2())) {
                            if (q instanceof QOM.Gt || q instanceof QOM.Lt) {
                                return ((Field)c11.$arg1()).ne((Field)c11.$arg2());
                            }
                            if (q instanceof QOM.Ge || q instanceof QOM.Le) {
                                return ((Field)c11.$arg1()).eq((Field)c11.$arg2());
                            }
                            if (q instanceof QOM.IsNotDistinctFrom) {
                                return DSL.trueCondition();
                            }
                            if (q instanceof IsDistinctFrom) {
                                return DSL.falseCondition();
                            }
                        }
                        if (q instanceof QOM.Eq) {
                            QOM.Eq e9 = (QOM.Eq)q;
                            if (Patterns.isTrue((Field)e9.$arg1())) {
                                return DSL.condition((Field)e9.$arg2());
                            }
                            if (Patterns.isTrue((Field)e9.$arg2())) {
                                return DSL.condition((Field)e9.$arg1());
                            }
                            if (Patterns.isFalse((Field)e9.$arg1())) {
                                return DSL.not(DSL.condition((Field)e9.$arg2()));
                            }
                            if (Patterns.isFalse((Field)e9.$arg2())) {
                                return DSL.not(DSL.condition((Field)e9.$arg1()));
                            }
                        } else if (q instanceof QOM.Ne) {
                            QOM.Ne e10 = (QOM.Ne)q;
                            if (Patterns.isTrue((Field)e10.$arg1())) {
                                return DSL.not(DSL.condition((Field)e10.$arg2()));
                            }
                            if (Patterns.isTrue((Field)e10.$arg2())) {
                                return DSL.not(DSL.condition((Field)e10.$arg1()));
                            }
                            if (Patterns.isFalse((Field)e10.$arg1())) {
                                return DSL.condition((Field)e10.$arg2());
                            }
                            if (Patterns.isFalse((Field)e10.$arg2())) {
                                return DSL.condition((Field)e10.$arg1());
                            }
                        }
                    } else if (q instanceof QOM.InList) {
                        QOM.InList c12 = (QOM.InList)q;
                        boolean i1 = Patterns.isInline((QueryPart)c12.$arg1());
                        if (((QOM.UnmodifiableList)c12.$arg2()).isEmpty()) {
                            return DSL.falseCondition();
                        }
                        if (i1 && Patterns.isNull0((Field)c12.$arg1())) {
                            return DSL.nullCondition();
                        }
                        if (i1 && Tools.allMatch((Iterable)c12.$arg2(), v -> Patterns.isInline(v))) {
                            for (Field v2 : (QOM.UnmodifiableList)c12.$arg2()) {
                                if (!((Field)c12.$arg1()).equals(v2)) continue;
                                return DSL.trueCondition();
                            }
                            return DSL.falseCondition();
                        }
                    } else if (q instanceof NotInList) {
                        NotInList c13 = (NotInList)q;
                        boolean i1 = Patterns.isInline((QueryPart)c13.$arg1());
                        if (c13.$arg2().isEmpty()) {
                            return DSL.trueCondition();
                        }
                        if (i1) {
                            if (Tools.anyMatch(c13.$arg2(), v -> Patterns.isNull(v))) {
                                return DSL.nullCondition();
                            }
                            if (Tools.allMatch(c13.$arg2(), v -> Patterns.isInline(v))) {
                                if (Tools.anyMatch(c13.$arg2(), ((Field)c13.$arg1())::equals)) {
                                    return DSL.falseCondition();
                                }
                                return DSL.trueCondition();
                            }
                        }
                    } else if (q instanceof QOM.In) {
                        c14 = (QOM.In)q;
                        if (Patterns.isEmpty((Select)c14.$arg2())) {
                            return DSL.falseCondition();
                        }
                    } else if (q instanceof QOM.NotIn) {
                        c = (QOM.NotIn)q;
                        if (Patterns.isEmpty((Select)((Object)c.$arg2()))) {
                            return DSL.trueCondition();
                        }
                    } else if (q instanceof QOM.Exists) {
                        QOM.Exists c15 = (QOM.Exists)q;
                        Select<?> s11 = c15.$query();
                        if (s11.$from().isEmpty() && Patterns.isEffectivelyTrue(s11.$where()) && Patterns.isEffectivelyTrue(s11.$having()) && Patterns.isEffectivelyTrue(s11.$connectBy()) && Patterns.isEffectivelyTrue(s11.$connectByStartWith()) && Patterns.isEffectivelyTrue(s11.$qualify()) && (s11.$limit() == null || Patterns.isInlineVal0(s11.$limit(), n -> n != null && n.intValue() >= 1)) && (s11.$offset() == null || Patterns.isInlineVal0(s11.$offset(), n -> n != null && n.intValue() == 0))) {
                            return DSL.trueCondition();
                        }
                        if (Patterns.isEmpty(s11)) {
                            return DSL.falseCondition();
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsNegNeg) && q instanceof QOM.Neg && (patt47514$temp = (n1 = (QOM.Neg)q).$arg1()) instanceof QOM.Neg) {
                    QOM.Neg n26 = (QOM.Neg)patt47514$temp;
                    return (QueryPart)n26.$arg1();
                }
                if (this.check(Settings::isTransformPatternsBitNotBitNot) && q instanceof QOM.BitNot && (patt47788$temp = (n1 = (QOM.BitNot)q).$arg1()) instanceof QOM.BitNot) {
                    QOM.BitNot n27 = (QOM.BitNot)patt47788$temp;
                    return (QueryPart)n27.$arg1();
                }
                if (this.check(Settings::isTransformPatternsBitNotBitNand) && q instanceof QOM.BitNot) {
                    n1 = (QOM.BitNot)q;
                    Object patt48066$temp = n1.$arg1();
                    if (patt48066$temp instanceof BitNand) {
                        BitNand n28 = (BitNand)patt48066$temp;
                        return n28.$arg1().bitAnd(n28.$arg2());
                    }
                    Object patt48226$temp = n1.$arg1();
                    if (patt48226$temp instanceof BitAnd) {
                        BitAnd n29 = (BitAnd)patt48226$temp;
                        return n29.$arg1().bitNand(n29.$arg2());
                    }
                }
                if (this.check(Settings::isTransformPatternsBitNotBitNor) && q instanceof QOM.BitNot) {
                    n1 = (QOM.BitNot)q;
                    Object patt48531$temp = n1.$arg1();
                    if (patt48531$temp instanceof BitNor) {
                        BitNor n210 = (BitNor)patt48531$temp;
                        return n210.$arg1().bitOr(n210.$arg2());
                    }
                    Object patt48689$temp = n1.$arg1();
                    if (patt48689$temp instanceof QOM.BitOr) {
                        QOM.BitOr n211 = (QOM.BitOr)patt48689$temp;
                        return ((Field)n211.$arg1()).bitNor((Field)n211.$arg2());
                    }
                }
                if (this.check(Settings::isTransformPatternsBitNotBitXNor) && q instanceof QOM.BitNot) {
                    n1 = (QOM.BitNot)q;
                    Object patt48993$temp = n1.$arg1();
                    if (patt48993$temp instanceof BitXNor) {
                        BitXNor n212 = (BitXNor)patt48993$temp;
                        return n212.$arg1().bitXor(n212.$arg2());
                    }
                    Object patt49153$temp = n1.$arg1();
                    if (patt49153$temp instanceof BitXor) {
                        BitXor n213 = (BitXor)patt49153$temp;
                        return n213.$arg1().bitXNor(n213.$arg2());
                    }
                }
                if (this.check(Settings::isTransformPatternsIdempotentFunctionRepetition)) {
                    QOM.Cast c12;
                    Field<?> patt53465$temp;
                    if (q instanceof QOM.Upper) {
                        QOM.Upper u1 = (QOM.Upper)q;
                        Field<String> patt49472$temp = u1.$string();
                        if (patt49472$temp instanceof QOM.Upper) {
                            QOM.Upper u2 = (QOM.Upper)patt49472$temp;
                            return u2;
                        }
                    } else if (q instanceof Lower) {
                        Lower l1 = (Lower)q;
                        Field<String> patt49657$temp = l1.$string();
                        if (patt49657$temp instanceof Lower) {
                            Lower l2 = (Lower)patt49657$temp;
                            return l2;
                        }
                    } else if (q instanceof QOM.Ltrim) {
                        QOM.Ltrim t12 = (QOM.Ltrim)q;
                        Field<String> patt49960$temp = t12.$string();
                        if (patt49960$temp instanceof QOM.Ltrim) {
                            QOM.Ltrim t22 = (QOM.Ltrim)patt49960$temp;
                            if (StringUtils.equals(t12.$characters(), t22.$characters())) {
                                return t22;
                            }
                        } else {
                            Field<String> patt50198$temp = t12.$string();
                            if (patt50198$temp instanceof QOM.Trim) {
                                QOM.Trim t23 = (QOM.Trim)patt50198$temp;
                                if (StringUtils.equals(t12.$characters(), t23.$characters())) {
                                    return t23;
                                }
                            }
                        }
                    } else if (q instanceof QOM.Rtrim) {
                        QOM.Rtrim t13 = (QOM.Rtrim)q;
                        Field<String> patt50498$temp = t13.$string();
                        if (patt50498$temp instanceof QOM.Rtrim) {
                            QOM.Rtrim t24 = (QOM.Rtrim)patt50498$temp;
                            if (StringUtils.equals(t13.$characters(), t24.$characters())) {
                                return t24;
                            }
                        } else {
                            Field<String> patt50736$temp = t13.$string();
                            if (patt50736$temp instanceof QOM.Trim) {
                                QOM.Trim t25 = (QOM.Trim)patt50736$temp;
                                if (StringUtils.equals(t13.$characters(), t25.$characters())) {
                                    return t25;
                                }
                            }
                        }
                    } else if (q instanceof QOM.Trim) {
                        QOM.Trim trim = (QOM.Trim)q;
                        Field<String> patt51035$temp = trim.$string();
                        if (patt51035$temp instanceof QOM.Trim) {
                            QOM.Trim t26 = (QOM.Trim)patt51035$temp;
                            if (StringUtils.equals(trim.$characters(), t26.$characters())) {
                                return t26;
                            }
                        } else {
                            Field<String> patt51272$temp = trim.$string();
                            if (patt51272$temp instanceof QOM.Ltrim) {
                                QOM.Ltrim t27 = (QOM.Ltrim)patt51272$temp;
                                if (StringUtils.equals(trim.$characters(), t27.$characters())) {
                                    if (t27.$characters() == null) {
                                        return DSL.trim(t27.$string());
                                    }
                                    return DSL.trim(t27.$string(), t27.$characters());
                                }
                            } else {
                                Field<String> patt51698$temp = trim.$string();
                                if (patt51698$temp instanceof QOM.Rtrim) {
                                    QOM.Rtrim t28 = (QOM.Rtrim)patt51698$temp;
                                    if (StringUtils.equals(trim.$characters(), t28.$characters())) {
                                        if (t28.$characters() == null) {
                                            return DSL.trim(t28.$string());
                                        }
                                        return DSL.trim(t28.$string(), t28.$characters());
                                    }
                                }
                            }
                        }
                    } else if (q instanceof Abs) {
                        Abs abs = (Abs)q;
                        Field patt52184$temp = abs.$value();
                        if (patt52184$temp instanceof Abs) {
                            Abs a26 = (Abs)patt52184$temp;
                            return a26;
                        }
                    } else if (q instanceof Sign) {
                        Sign s12 = (Sign)q;
                        Field<? extends Number> patt52365$temp = s12.$value();
                        if (patt52365$temp instanceof Sign) {
                            Sign s24 = (Sign)patt52365$temp;
                            return s24;
                        }
                    } else if (q instanceof QOM.Ceil) {
                        QOM.Ceil c13 = (QOM.Ceil)q;
                        Field patt52550$temp = c13.$value();
                        if (patt52550$temp instanceof QOM.Ceil) {
                            QOM.Ceil c24 = (QOM.Ceil)patt52550$temp;
                            return c24;
                        }
                    } else if (q instanceof QOM.Floor) {
                        QOM.Floor f12 = (QOM.Floor)q;
                        Field patt52739$temp = f12.$value();
                        if (patt52739$temp instanceof QOM.Floor) {
                            QOM.Floor f22 = (QOM.Floor)patt52739$temp;
                            return f22;
                        }
                    } else if (q instanceof QOM.Trunc) {
                        c14 = (QOM.Trunc)q;
                        Field patt52929$temp = c14.$value();
                        if (patt52929$temp instanceof QOM.Trunc) {
                            QOM.Trunc c25 = (QOM.Trunc)patt52929$temp;
                            if (Objects.equals(c14.$decimals(), c25.$decimals())) {
                                return c25;
                            }
                        }
                    } else if (q instanceof QOM.Round) {
                        QOM.Round c15 = (QOM.Round)q;
                        Field patt53199$temp = c15.$value();
                        if (patt53199$temp instanceof QOM.Round) {
                            QOM.Round c26 = (QOM.Round)patt53199$temp;
                            if (Objects.equals(c15.$decimals(), c26.$decimals())) {
                                return c26;
                            }
                        }
                    } else if (q instanceof QOM.Cast && (patt53465$temp = (c12 = (QOM.Cast)q).$field()) instanceof QOM.Cast) {
                        QOM.Cast c27 = (QOM.Cast)patt53465$temp;
                        if (Objects.equals(c12.$dataType(), c27.$dataType())) {
                            return c27;
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsNullOnNullInput) && q instanceof QOM.UReturnsNullOnNullInput && q instanceof QOM.UOperator && Tools.anyMatch((op = (QOM.UOperator)q).$args(), a -> a instanceof QueryPart && Patterns.isNull((QueryPart)a))) {
                    if (op instanceof Condition) {
                        return DSL.nullCondition();
                    }
                    if (op instanceof Field) {
                        Field f3 = (Field)((Object)op);
                        return DSL.inline(null, f3.getDataType());
                    }
                }
                if (this.check(Settings::isTransformPatternsArithmeticComparisons) && q instanceof QOM.Eq) {
                    c2 = (QOM.CompareCondition)q;
                    for (int n7 : OP2) {
                        Field field4 = (Field)Patterns.arg2(c2, n7);
                        if (!Patterns.isInline(field4)) continue;
                        Object patt54622$temp = Patterns.arg1(c2, n7);
                        if (patt54622$temp instanceof QOM.Add) {
                            QOM.Add a6 = (QOM.Add)patt54622$temp;
                            if (!Patterns.isInline((QueryPart)a6.$arg1()) || !Tools.reference((Field)a6.$arg2())) continue;
                            return ((Field)a6.$arg2()).eq(field4.minus((Field)a6.$arg1()));
                        }
                        Object patt54882$temp = Patterns.arg1(c2, n7);
                        if (!(patt54882$temp instanceof QOM.Sub)) continue;
                        QOM.Sub s12 = (QOM.Sub)patt54882$temp;
                        if (Tools.reference((Field)s12.$arg1()) && Patterns.isInline((QueryPart)s12.$arg2())) {
                            return ((Field)s12.$arg1()).eq(field4.plus((Field)s12.$arg2()));
                        }
                        if (!Patterns.isInline((QueryPart)s12.$arg1()) || !Tools.reference((Field)s12.$arg2())) continue;
                        return ((Field)s12.$arg2()).eq(((Field)s12.$arg1()).minus(field4));
                    }
                }
                if (this.check(Settings::isTransformPatternsArithmeticExpressions)) {
                    QOM.Power power;
                    if (q instanceof QOM.Add) {
                        QOM.Add a22 = (QOM.Add)q;
                        if (!Patterns.isInline((QueryPart)a22.$arg1()) && Patterns.isInline((QueryPart)a22.$arg2())) {
                            return a22.$swap();
                        }
                        if (Patterns.isInlineVal((Field)a22.$arg1(), 0)) {
                            return (QueryPart)a22.$arg2();
                        }
                        Object patt55870$temp = a22.$arg2();
                        if (patt55870$temp instanceof QOM.Neg) {
                            QOM.Neg n214 = (QOM.Neg)patt55870$temp;
                            return ((Field)a22.$arg1()).minus((Field)n214.$arg1());
                        }
                    } else if (q instanceof QOM.Sub) {
                        QOM.Sub s13 = (QOM.Sub)q;
                        if (Patterns.isInlineVal((Field)s13.$arg1(), 0)) {
                            return ((Field)s13.$arg2()).neg();
                        }
                        Object patt56283$temp = s13.$arg2();
                        if (patt56283$temp instanceof QOM.Neg) {
                            QOM.Neg n215 = (QOM.Neg)patt56283$temp;
                            return ((Field)s13.$arg1()).plus((Field)n215.$arg1());
                        }
                    } else if (q instanceof QOM.Mul) {
                        QOM.Mul m5 = (QOM.Mul)q;
                        if (!Patterns.isInline((QueryPart)m5.$arg1()) && Patterns.isInline((QueryPart)m5.$arg2())) {
                            return m5.$swap();
                        }
                        if (Patterns.isInlineVal((Field)m5.$arg1(), 1)) {
                            return (QueryPart)m5.$arg2();
                        }
                        if (Patterns.isInlineVal((Field)m5.$arg1(), -1)) {
                            return ((Field)m5.$arg2()).neg();
                        }
                        Object patt57033$temp = m5.$arg1();
                        if (patt57033$temp instanceof QOM.Div) {
                            QOM.Div d1 = (QOM.Div)patt57033$temp;
                            if (Patterns.isInlineVal((Field)d1.$arg1(), 1)) {
                                return ((Field)m5.$arg2()).div((Field)d1.$arg2());
                            }
                        } else {
                            Object patt57317$temp = m5.$arg2();
                            if (patt57317$temp instanceof QOM.Div) {
                                QOM.Div d22 = (QOM.Div)patt57317$temp;
                                if (Patterns.isInlineVal((Field)d22.$arg1(), 1)) {
                                    return ((Field)m5.$arg1()).div((Field)d22.$arg2());
                                }
                            } else {
                                if (Patterns.isNeg((Field)m5.$arg1()) && Patterns.isNeg((Field)m5.$arg2())) {
                                    return Patterns.neg((Field)m5.$arg1()).times(Patterns.neg((Field)m5.$arg2()));
                                }
                                if (Objects.equals(m5.$arg1(), m5.$arg2())) {
                                    return DSL.square((Field)m5.$arg1());
                                }
                            }
                        }
                    } else if (q instanceof QOM.Div) {
                        QOM.Div d3 = (QOM.Div)q;
                        if (Patterns.isNeg((Field)d3.$arg1()) && Patterns.isNeg((Field)d3.$arg2())) {
                            return Patterns.neg((Field)d3.$arg1()).div(Patterns.neg((Field)d3.$arg2()));
                        }
                    } else if (q instanceof QOM.Neg) {
                        QOM.Neg neg = (QOM.Neg)q;
                        if (Patterns.isInline((QueryPart)neg.$arg1())) {
                            Val v3 = (Val)neg.$arg1();
                            Object patt58456$temp = v3.getValue();
                            if (patt58456$temp instanceof Number) {
                                Number x3 = (Number)patt58456$temp;
                                if ((x3 = Patterns.neg(x3)) != null) {
                                    return DSL.inline(x3);
                                }
                            }
                        } else {
                            Object patt58700$temp = neg.$arg1();
                            if (patt58700$temp instanceof QOM.Sub) {
                                QOM.Sub s14 = (QOM.Sub)patt58700$temp;
                                return ((Field)s14.$arg2()).minus((Field)s14.$arg1());
                            }
                        }
                    } else if (q instanceof QOM.Power && Patterns.isInlineVal((power = (QOM.Power)q).$exponent(), 1)) {
                        return power.$base();
                    }
                }
                if (this.check(Settings::isTransformPatternsHyperbolicFunctions) && q instanceof QOM.Div) {
                    d = (QOM.Div)q;
                    if (Patterns.isInlineVal((Field)d.$arg1(), 1)) {
                        Object patt59300$temp = d.$arg2();
                        if (patt59300$temp instanceof QOM.Tanh) {
                            QOM.Tanh tanh = (QOM.Tanh)patt59300$temp;
                            return DSL.coth(tanh.$value());
                        }
                        Object patt59494$temp = d.$arg2();
                        if (patt59494$temp instanceof QOM.Coth) {
                            QOM.Coth coth = (QOM.Coth)patt59494$temp;
                            return DSL.tanh(coth.$value());
                        }
                    } else if (Patterns.isInlineVal((Field)d.$arg2(), 2)) {
                        Object patt59990$temp;
                        QOM.UOperator2 as;
                        boolean bl = d.$arg1() instanceof QOM.Sub;
                        boolean bl2 = d.$arg1() instanceof QOM.Add;
                        QOM.UOperator2 uOperator2 = as = bl || bl2 ? (QOM.UOperator2)d.$arg1() : null;
                        if (as != null && (patt59990$temp = as.$arg1()) instanceof QOM.Exp) {
                            QOM.Exp e1 = (QOM.Exp)patt59990$temp;
                            Object patt60058$temp = as.$arg2();
                            if (patt60058$temp instanceof QOM.Exp) {
                                QOM.Exp e23 = (QOM.Exp)patt60058$temp;
                                Field<? extends Number> patt60278$temp = e23.$value();
                                if (patt60278$temp instanceof QOM.Neg) {
                                    QOM.Neg n216 = (QOM.Neg)patt60278$temp;
                                    if (e1.$value().equals(n216.$arg1())) {
                                        return bl ? DSL.sinh(e1.$value()) : DSL.cosh(e1.$value());
                                    }
                                } else {
                                    Field<? extends Number> patt60762$temp = e1.$value();
                                    if (patt60762$temp instanceof QOM.Neg) {
                                        QOM.Neg n13 = (QOM.Neg)patt60762$temp;
                                        if (e23.$value().equals(n13.$arg1())) {
                                            return bl ? DSL.sinh(e23.$value().neg()) : DSL.cosh(e23.$value().neg());
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Object Q2 = d.$arg2();
                        if (Q2 instanceof QOM.Mul) {
                            Object Q22;
                            QOM.Mul m22 = (QOM.Mul)Q2;
                            if (Patterns.isInlineVal((Field)m22.$arg1(), 2) && (Q22 = m22.$arg2()) instanceof QOM.Exp) {
                                QOM.UOperator2 as;
                                QOM.Exp exp = (QOM.Exp)Q22;
                                Field<? extends Number> field5 = exp.$value();
                                boolean sub = d.$arg1() instanceof QOM.Sub;
                                boolean add = d.$arg1() instanceof QOM.Add;
                                QOM.UOperator2 uOperator2 = as = sub || add ? (QOM.UOperator2)d.$arg1() : null;
                                if (as != null) {
                                    Field<? extends Number> patt63021$temp;
                                    Object patt62940$temp;
                                    QOM.Mul m1;
                                    QOM.Exp e1;
                                    if (Patterns.isInlineVal((Field)as.$arg1(), 1)) {
                                        Field<? extends Number> patt62129$temp;
                                        Object patt62048$temp = as.$arg2();
                                        if (patt62048$temp instanceof QOM.Exp && (patt62129$temp = (e1 = (QOM.Exp)patt62048$temp).$value()) instanceof QOM.Mul && Patterns.isInlineVal((Field)(m1 = (QOM.Mul)patt62129$temp).$arg1(), 2) && ((Field)m1.$arg2()).equals(field5)) {
                                            return sub ? DSL.sinh(field5.neg()) : DSL.cosh(field5.neg());
                                        }
                                    } else if (Patterns.isInlineVal((Field)as.$arg2(), 1) && (patt62940$temp = as.$arg1()) instanceof QOM.Exp && (patt63021$temp = (e1 = (QOM.Exp)patt62940$temp).$value()) instanceof QOM.Mul && Patterns.isInlineVal((Field)(m1 = (QOM.Mul)patt63021$temp).$arg1(), 2) && ((Field)m1.$arg2()).equals(field5)) {
                                        return sub ? DSL.sinh(field5) : DSL.cosh(field5);
                                    }
                                }
                            }
                        } else {
                            Object Q1 = d.$arg1();
                            if (Q1 instanceof QOM.Sinh) {
                                QOM.Sinh s15 = (QOM.Sinh)Q1;
                                Object Q23 = d.$arg2();
                                if (Q23 instanceof Cosh) {
                                    Cosh cosh = (Cosh)Q23;
                                    if (s15.$value().equals(cosh.$value())) {
                                        return DSL.tanh(s15.$value());
                                    }
                                }
                            } else {
                                Object Q14 = d.$arg1();
                                if (Q14 instanceof Cosh) {
                                    Cosh c18 = (Cosh)Q14;
                                    Object Q24 = d.$arg2();
                                    if (Q24 instanceof QOM.Sinh) {
                                        QOM.Sinh sinh = (QOM.Sinh)Q24;
                                        if (c18.$value().equals(sinh.$value())) {
                                            return DSL.coth(c18.$value());
                                        }
                                    }
                                } else {
                                    boolean bl = d.$arg1() instanceof QOM.Sub;
                                    boolean bl3 = d.$arg1() instanceof QOM.Add;
                                    QOM.UOperator2 as1 = bl || bl3 ? (QOM.UOperator2)d.$arg1() : null;
                                    boolean sub2 = d.$arg2() instanceof QOM.Sub;
                                    boolean add2 = d.$arg2() instanceof QOM.Add;
                                    QOM.UOperator2 uOperator2 = as2 = bl3 && sub2 || bl && add2 ? (QOM.UOperator2)d.$arg2() : null;
                                    if (as1 != null && as2 != null) {
                                        Object patt65204$temp = as1.$arg1();
                                        if (patt65204$temp instanceof QOM.Exp) {
                                            QOM.Exp e11 = (QOM.Exp)patt65204$temp;
                                            Object patt65274$temp = as2.$arg2();
                                            if (patt65274$temp instanceof QOM.Exp) {
                                                QOM.Exp e22 = (QOM.Exp)patt65274$temp;
                                                Object patt65348$temp = as1.$arg2();
                                                if (patt65348$temp instanceof QOM.Exp) {
                                                    QOM.Exp e12 = (QOM.Exp)patt65348$temp;
                                                    Object patt65426$temp = as2.$arg1();
                                                    if (patt65426$temp instanceof QOM.Exp) {
                                                        QOM.Neg n11;
                                                        QOM.Neg n12;
                                                        QOM.Exp e21 = (QOM.Exp)patt65426$temp;
                                                        Field<? extends Number> x11 = e11.$value();
                                                        Field<? extends Number> x12 = e12.$value();
                                                        if (e21.$value().equals(x11) && e22.$value().equals(x12) && (x12 instanceof QOM.Neg ? x11.equals((n12 = (QOM.Neg)x12).$arg1()) : x11 instanceof QOM.Neg && x12.equals((n11 = (QOM.Neg)x11).$arg1()))) {
                                                            return bl ? DSL.tanh(x11) : DSL.coth(x11);
                                                        }
                                                    }
                                                } else if (bl && Patterns.isInlineVal((Field)as1.$arg2(), 1) && Patterns.isInlineVal((Field)as2.$arg1(), 1)) {
                                                    Field<? extends Number> x11 = e11.$value();
                                                    if (e22.$value().equals(x11)) {
                                                        return DSL.tanh(x11.div(DSL.inline(2)));
                                                    }
                                                }
                                            }
                                        } else {
                                            Object patt67746$temp = as1.$arg2();
                                            if (patt67746$temp instanceof QOM.Exp) {
                                                QOM.Exp e12 = (QOM.Exp)patt67746$temp;
                                                Object patt67816$temp = as2.$arg1();
                                                if (patt67816$temp instanceof QOM.Exp) {
                                                    QOM.Exp e21 = (QOM.Exp)patt67816$temp;
                                                    if (bl3 && Patterns.isInlineVal((Field)as1.$arg1(), 1) && Patterns.isInlineVal((Field)as2.$arg2(), 1)) {
                                                        Field<? extends Number> x12 = e12.$value();
                                                        if (e21.$value().equals(x12)) {
                                                            return DSL.coth(x12.div(DSL.inline(2)));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsInverseHyperbolicFunctions)) {
                    QOM.Ln l5;
                    Field<? extends Number> patt69900$temp;
                    if (q instanceof QOM.Div) {
                        Object patt68909$temp;
                        QOM.Add add;
                        QOM.Div d1;
                        Object Q1;
                        QOM.Ln l1;
                        Field<? extends Number> field6;
                        Object patt68625$temp;
                        d = (QOM.Div)q;
                        if (Patterns.isInlineVal((Field)d.$arg2(), 2) && (patt68625$temp = d.$arg1()) instanceof QOM.Ln && (field6 = (l1 = (QOM.Ln)patt68625$temp).$value()) instanceof QOM.Div && (Q1 = (d1 = (QOM.Div)field6).$arg1()) instanceof QOM.Add && Patterns.isInlineVal((Field)(add = (QOM.Add)Q1).$arg1(), 1) && (patt68909$temp = d1.$arg2()) instanceof QOM.Sub) {
                            QOM.Sub sub = (QOM.Sub)patt68909$temp;
                            if (Patterns.isInlineVal((Field)sub.$arg1(), 1) && Objects.equals(add.$arg2(), sub.$arg2())) {
                                return DSL.atanh((Field)add.$arg2());
                            }
                            if (Patterns.isInlineVal((Field)sub.$arg2(), 1) && Objects.equals(add.$arg2(), sub.$arg1())) {
                                return DSL.acoth((Field)add.$arg2());
                            }
                        }
                    } else if (q instanceof QOM.Ln && (patt69900$temp = (l5 = (QOM.Ln)q).$value()) instanceof QOM.Add) {
                        Object Q2;
                        QOM.Add a7 = (QOM.Add)patt69900$temp;
                        if (a7.$arg1() instanceof QOM.Sqrt) {
                            a7 = (QOM.Add)a7.$swap();
                        }
                        if ((Q2 = a7.$arg2()) instanceof QOM.Sqrt) {
                            QOM.Sqrt q1 = (QOM.Sqrt)Q2;
                            Field<? extends Number> patt70288$temp = q1.$value();
                            if (patt70288$temp instanceof QOM.Add) {
                                Object patt70429$temp;
                                QOM.Add add = (QOM.Add)patt70288$temp;
                                if (Patterns.isInlineVal((Field)add.$arg1(), 1) && (patt70429$temp = add.$arg2()) instanceof QOM.Square) {
                                    q2 = (QOM.Square)patt70429$temp;
                                    if (Objects.equals(a7.$arg1(), q2.$value())) {
                                        return DSL.asinh((Field)a7.$arg1());
                                    }
                                }
                            } else {
                                Object patt70981$temp;
                                QOM.Sub sub;
                                Field<? extends Number> patt70840$temp = q1.$value();
                                if (patt70840$temp instanceof QOM.Sub && Patterns.isInlineVal((Field)(sub = (QOM.Sub)patt70840$temp).$arg2(), 1) && (patt70981$temp = sub.$arg1()) instanceof QOM.Square) {
                                    q2 = (QOM.Square)patt70981$temp;
                                    if (Objects.equals(a7.$arg1(), q2.$value())) {
                                        return DSL.acosh((Field)a7.$arg1());
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsTrigonometricFunctions) && q instanceof QOM.Div) {
                    d = (QOM.Div)q;
                    if (Patterns.isInlineVal((Field)d.$arg1(), 1)) {
                        Object Q2 = d.$arg2();
                        if (Q2 instanceof QOM.Tan) {
                            QOM.Tan t7 = (QOM.Tan)Q2;
                            return DSL.cot(t7.$value());
                        }
                        Object Q25 = d.$arg2();
                        if (Q25 instanceof Cot) {
                            Cot cot = (Cot)Q25;
                            return DSL.tan(cot.$value());
                        }
                    } else {
                        Object patt72014$temp = d.$arg1();
                        if (patt72014$temp instanceof QOM.Sin) {
                            QOM.Sin s17 = (QOM.Sin)patt72014$temp;
                            Object Q2 = d.$arg2();
                            if (Q2 instanceof QOM.Cos) {
                                QOM.Cos c20 = (QOM.Cos)Q2;
                                if (s17.$value().equals(c20.$value())) {
                                    return DSL.tan(s17.$value());
                                }
                            }
                        } else {
                            Object patt72380$temp = d.$arg1();
                            if (patt72380$temp instanceof QOM.Cos) {
                                QOM.Cos c21 = (QOM.Cos)patt72380$temp;
                                Object Q2 = d.$arg2();
                                if (Q2 instanceof QOM.Sin) {
                                    QOM.Sin s18 = (QOM.Sin)Q2;
                                    if (c21.$value().equals(s18.$value())) {
                                        return DSL.cot(c21.$value());
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.check(Settings::isTransformPatternsLogarithmicFunctions)) {
                    QOM.Power p3;
                    if (q instanceof QOM.Div) {
                        Field<? extends Number> field7;
                        Field<? extends Number> field8;
                        d = (QOM.Div)q;
                        Object add2 = d.$arg1();
                        if (add2 instanceof QOM.Ln) {
                            QOM.Ln l6 = (QOM.Ln)add2;
                            field8 = l6.$value();
                        } else {
                            add2 = d.$arg1();
                            if (add2 instanceof QOM.Log) {
                                QOM.Log l7 = (QOM.Log)add2;
                                field8 = l7.$value();
                            } else {
                                add2 = d.$arg1();
                                if (add2 instanceof QOM.Log10) {
                                    QOM.Log10 log10 = (QOM.Log10)add2;
                                    field8 = log10.$value();
                                } else {
                                    field8 = null;
                                }
                            }
                        }
                        Field<? extends Number> field9 = field8;
                        as2 = d.$arg2();
                        if (as2 instanceof QOM.Ln) {
                            QOM.Ln l9 = (QOM.Ln)as2;
                            v4 = l9.$value();
                        } else {
                            as2 = d.$arg2();
                            if (as2 instanceof QOM.Log) {
                                QOM.Log l2 = (QOM.Log)as2;
                                v4 = l2.$value();
                            } else {
                                as2 = d.$arg2();
                                if (as2 instanceof QOM.Log10) {
                                    QOM.Log10 l2 = (QOM.Log10)as2;
                                    v4 = l2.$value();
                                } else {
                                    v4 = field7 = null;
                                }
                            }
                        }
                        if (field9 != null && field7 != null) {
                            Param<Integer> b2;
                            Field<Number> field10;
                            as2 = d.$arg1();
                            if (as2 instanceof QOM.Ln) {
                                QOM.Ln l1 = (QOM.Ln)as2;
                                field10 = null;
                            } else {
                                as2 = d.$arg1();
                                if (as2 instanceof QOM.Log) {
                                    QOM.Log l1 = (QOM.Log)as2;
                                    field10 = l1.$base();
                                } else {
                                    field10 = DSL.inline(10);
                                }
                            }
                            Param<Integer> b1 = field10;
                            Object Q2 = d.$arg2();
                            if (Q2 instanceof QOM.Ln) {
                                QOM.Ln l2 = (QOM.Ln)Q2;
                                v6 = null;
                            } else {
                                Q2 = d.$arg2();
                                if (Q2 instanceof QOM.Log) {
                                    QOM.Log l2 = (QOM.Log)Q2;
                                    v6 = l2.$base();
                                } else {
                                    v6 = b2 = DSL.inline(10);
                                }
                            }
                            if (Objects.equals(b1, b2)) {
                                return DSL.log(field9, field7);
                            }
                            if (Patterns.isInlineVal0(b1, v -> Patterns.isInlineVal(b2, v))) {
                                return DSL.log(field9, field7);
                            }
                        }
                    } else if (q instanceof QOM.Exp) {
                        QOM.Exp e11 = (QOM.Exp)q;
                        if (Patterns.isInlineVal(e11.$value(), 1)) {
                            return DSL.e();
                        }
                    } else if (q instanceof QOM.Log) {
                        QOM.Log l10 = (QOM.Log)q;
                        if (l10.$base() instanceof QOM.Euler) {
                            return DSL.ln(l10.$value());
                        }
                        if (Patterns.isInlineVal(l10.$base(), 10)) {
                            return DSL.log10(l10.$value());
                        }
                    } else if (q instanceof QOM.Power && (p3 = (QOM.Power)q).$base() instanceof QOM.Euler) {
                        return DSL.exp(p3.$exponent());
                    }
                }
                if (this.check(Settings::isTransformPatternsNormaliseFieldCompareValue) && q instanceof QOM.CompareCondition && q instanceof QOM.UConvertibleOperator && (o = (QOM.UConvertibleOperator)q).$arg1() instanceof Val && (o.$arg2() instanceof TableField || o.$arg2() instanceof QOM.ScalarSubquery)) {
                    return o.$converse();
                }
                if (this.check(Settings::isTransformPatternsNormaliseInListSingleElementToComparison)) {
                    NotInList c223;
                    if (q instanceof QOM.InList) {
                        c2 = (QOM.InList)q;
                        if (((QOM.UnmodifiableList)c2.$arg2()).size() == 1) {
                            return ((Field)c2.$arg1()).eq((Field)((QOM.UnmodifiableList)c2.$arg2()).get(0));
                        }
                    } else if (q instanceof NotInList && (c223 = (NotInList)q).$arg2().size() == 1) {
                        return c223.$arg1().ne((Field)c223.$arg2().get(0));
                    }
                }
                if (this.check(Settings::isTransformPatternsNormaliseAssociativeOps)) {
                    QOM.CrossJoin crossJoin;
                    Table<?> patt76694$temp;
                    if (q instanceof QOM.Add) {
                        QOM.Add o1 = (QOM.Add)q;
                        Object patt75794$temp = o1.$arg2();
                        if (patt75794$temp instanceof QOM.Add) {
                            QOM.Add add = (QOM.Add)patt75794$temp;
                            return ((Field)o1.$arg1()).add((Field)add.$arg1()).add((Field)add.$arg2());
                        }
                    } else if (q instanceof QOM.Mul) {
                        QOM.Mul o1 = (QOM.Mul)q;
                        Object patt76019$temp = o1.$arg2();
                        if (patt76019$temp instanceof QOM.Mul) {
                            QOM.Mul mul = (QOM.Mul)patt76019$temp;
                            return ((Field)o1.$arg1()).mul((Field)mul.$arg1()).mul((Field)mul.$arg2());
                        }
                    } else if (q instanceof QOM.And) {
                        QOM.And o1 = (QOM.And)q;
                        Object patt76249$temp = o1.$arg2();
                        if (patt76249$temp instanceof QOM.And) {
                            QOM.And and = (QOM.And)patt76249$temp;
                            return ((Condition)o1.$arg1()).and((Condition)and.$arg1()).and((Condition)and.$arg2());
                        }
                    } else if (q instanceof QOM.Or) {
                        QOM.Or o1 = (QOM.Or)q;
                        Object patt76467$temp = o1.$arg2();
                        if (patt76467$temp instanceof QOM.Or) {
                            QOM.Or or = (QOM.Or)patt76467$temp;
                            return ((Condition)o1.$arg1()).or((Condition)or.$arg1()).or((Condition)or.$arg2());
                        }
                    } else if (q instanceof QOM.CrossJoin && (patt76694$temp = (crossJoin = (QOM.CrossJoin)q).$table2()) instanceof QOM.CrossJoin) {
                        QOM.CrossJoin crossJoin2 = (QOM.CrossJoin)patt76694$temp;
                        return crossJoin.$table1().crossJoin(crossJoin2.$table1()).crossJoin(crossJoin2.$table2());
                    }
                }
                if (this.check(Settings::isTransformPatternsNormaliseCoalesceToNvl) && q instanceof Coalesce && ((Coalesce)(c2 = (Coalesce)q)).$arg1().size() == 2) {
                    return DSL.nvl((Field)((Coalesce)c2).$arg1().get(0), (Field)((Coalesce)c2).$arg1().get(1));
                }
                if (this.check(Settings::isTransformPatternsUnnecessaryDistinct) && q instanceof Select && (s2 = (Select)q).$distinct()) {
                    if (Patterns.projectionOnly(s2)) {
                        return s2.$distinct(false);
                    }
                    if (s2.$distinctOn().isEmpty() && !s2.$groupBy().isEmpty() && !Tools.anyMatch(s2.$groupBy(), e -> e instanceof Param) && Tools.map(s2.getSelect(), t2 -> Tools.unalias(t2)).containsAll(s2.$groupBy())) {
                        return s2.$distinct(false);
                    }
                }
                if (this.check(Settings::isTransformPatternsUnnecessaryScalarSubquery) && q instanceof Select && (select = Patterns.replaceSelect(o = (Select)q, f1 = (f = unalias -> x -> {
                    QOM.ScalarSubquery u;
                    Select s2;
                    if (x instanceof QOM.ScalarSubquery && Patterns.projectionOnlyNoAggregatesOrWindowFunctions(s2 = (Select)(u = (QOM.ScalarSubquery)x).$arg1())) {
                        if (unalias.booleanValue()) {
                            return Tools.unalias(s2.getSelect().get(0));
                        }
                        return s2.getSelect().get(0);
                    }
                    return x;
                }).apply(false), f2 = f.apply(true), f2, f2, f2, x -> x, f2, f2, f2, x -> x, f2, f2)) != o) {
                    return select;
                }
                if (this.check(Settings::isTransformPatternsUnnecessaryInnerJoin) && q instanceof QOM.Join && Patterns.isTrue((j = (QOM.Join)q).$on())) {
                    return j.$table1().crossJoin(j.$table2());
                }
                if (this.check(Settings::isTransformPatternsUnnecessaryGroupByExpressions) && q instanceof Select && !(s2 = (Select)q).$groupBy().isEmpty()) {
                    LinkedHashSet<GroupField> d4 = new LinkedHashSet<GroupField>();
                    ArrayList<GroupField> r = new ArrayList<GroupField>(s2.$groupBy().size());
                    for (GroupField groupField : s2.$groupBy()) {
                        if (!(groupField instanceof QOM.Rollup) && !(groupField instanceof QOM.Cube) && !(groupField instanceof QOM.GroupingSets) && !d4.add(groupField)) continue;
                        r.add(groupField);
                    }
                    if (r.size() != s2.$groupBy().size()) {
                        return s2.$groupBy(r);
                    }
                }
                if (this.check(Settings::isTransformPatternsUnnecessaryOrderByExpressions) && q instanceof Select && !(s2 = (Select)q).$orderBy().isEmpty()) {
                    LinkedHashSet d5 = new LinkedHashSet();
                    ArrayList<SortField> r = new ArrayList<SortField>(s2.$orderBy().size());
                    for (SortField sortField : s2.$orderBy()) {
                        if (!d5.add(sortField.$field())) continue;
                        r.add(sortField);
                    }
                    if (r.size() != s2.$orderBy().size()) {
                        return s2.$orderBy(r);
                    }
                }
                if (this.check(Settings::isTransformPatternsUnnecessaryExistsSubqueryClauses) && q instanceof QOM.Exists) {
                    QOM.Exists e14 = (QOM.Exists)q;
                    Select<?> s14 = e14.$query();
                    Select<?> s26 = s14;
                    if (!Patterns.hasUnions(s26)) {
                        if (s26.$distinct()) {
                            s26 = s26.$distinct(false);
                        }
                        if (Patterns.isInlineVal0(s26.$limit(), v -> v != null && v.longValue() > 0L)) {
                            s26 = s26.$limit(null);
                        }
                        if (!(s26.$orderBy().isEmpty() && s26.$select().size() <= 1 && !Tools.anyMatch(s26.$select(), p -> !Patterns.isInline(p)) || Patterns.hasAggregateFunctions(s26))) {
                            s26 = s26.$select(Arrays.asList(DSL.one())).$orderBy(Arrays.asList(new SortField[0]));
                        }
                    }
                    if (s14 != s26) {
                        return DSL.exists(s26);
                    }
                }
                if (this.check(Settings::isTransformPatternsCaseDistinctToDecode) && q instanceof QOM.CaseSearched && Tools.allMatch((Iterable)(c2 = (QOM.CaseSearched)q).$arg1(), t2 -> t2.$1() instanceof QOM.IsNotDistinctFrom)) {
                    void var8_222;
                    void var7_171;
                    QOM.Tuple2 t8 = (QOM.Tuple2)((QOM.UnmodifiableList)c2.$arg1()).$first();
                    QOM.IsNotDistinctFrom d6 = (QOM.IsNotDistinctFrom)t8.$1();
                    if (((QOM.UnmodifiableList)c2.$arg1()).size() == 1) {
                        if (c2.$else() == null) {
                            return DSL.decode((Field)d6.$arg1(), (Field)d6.$arg2(), (Field)t8.$2());
                        }
                        return DSL.decode((Field)d6.$arg1(), (Field)d6.$arg2(), (Field)t8.$2(), new Object[]{c2.$else()});
                    }
                    ArrayList l11 = new ArrayList();
                    Object var7_170 = null;
                    Object var8_221 = null;
                    for (QOM.Tuple2 tx : ((QOM.UnmodifiableList)c2.$arg1()).$removeFirst()) {
                        QOM.IsNotDistinctFrom dx = (QOM.IsNotDistinctFrom)tx.$1();
                        if (var7_171 == null) {
                            if (((Field)d6.$arg1()).equals(dx.$arg1()) || ((Field)d6.$arg1()).equals(dx.$arg2())) {
                                Field field11 = (Field)d6.$arg1();
                                Field field12 = (Field)d6.$arg2();
                            } else {
                                if (!((Field)d6.$arg2()).equals(dx.$arg1()) && !((Field)d6.$arg2()).equals(dx.$arg2())) break block539;
                                Field field13 = (Field)d6.$arg2();
                                Field field14 = (Field)d6.$arg1();
                            }
                        }
                        if (var7_171.equals(dx.$arg1())) {
                            l11.add((Field)dx.$arg2());
                        } else {
                            if (!var7_171.equals(dx.$arg2())) break block539;
                            l11.add((Field)dx.$arg1());
                        }
                        l11.add((Field)tx.$2());
                    }
                    if (c2.$else() != null) {
                        l11.add(c2.$else());
                    }
                    return DSL.decode(var7_171, var8_222, (Field)t8.$2(), (Object[])l11.toArray(Tools.EMPTY_FIELD));
                }
            }
            if (this.check(Settings::isTransformPatternsOrEqToIn) && q instanceof QOM.Or) {
                o = (QOM.Or)q;
                int[] nArray = OP2;
                int n8 = nArray.length;
                for (int j = 0; j < n8; ++j) {
                    int n9;
                    result = Patterns.mergeToInOrNotIn(Patterns.arg1(o, n9), Patterns.arg2(o, n9), (n9 = nArray[j]) != 0, QOM.Or.class, QOM.Eq.class, QOM.InList.class, Condition::or, Field::in);
                    if (result == null) continue;
                    return result;
                }
            }
            if (this.check(Settings::isTransformPatternsAndNeToNotIn) && q instanceof QOM.And) {
                a2 = (QOM.And)q;
                int[] nArray = OP2;
                int n10 = nArray.length;
                for (int j = 0; j < n10; ++j) {
                    int n11;
                    result = Patterns.mergeToInOrNotIn(Patterns.arg1(a2, n11), Patterns.arg2(a2, n11), (n11 = nArray[j]) != 0, QOM.And.class, QOM.Ne.class, NotInList.class, Condition::and, Field::notIn);
                    if (result == null) continue;
                    return result;
                }
            }
            if (this.check(Settings::isTransformPatternsMergeInLists) && q instanceof QOM.And && (result = Patterns.merge(a2 = (QOM.And)q, Patterns::mergeInList)) != null) {
                return result;
            }
            if (this.check(Settings::isTransformPatternsMergeBetweenSymmetricPredicates) && q instanceof QOM.Or && (result = Patterns.merge(o = (QOM.Or)q, Patterns::mergeBetweenSymmetric)) != null) {
                return result;
            }
            if (this.check(Settings::isTransformPatternsMergeOrComparison) && q instanceof QOM.Or && (result = Patterns.merge(o = (QOM.Or)q, Patterns::mergeOrComparison)) != null) {
                return result;
            }
            if (this.check(Settings::isTransformPatternsMergeAndComparison) && q instanceof QOM.And && (result = Patterns.merge(a2 = (QOM.And)q, Patterns::mergeAndComparison)) != null) {
                return result;
            }
            if (this.check(Settings::isTransformPatternsMergeRangePredicates) && q instanceof QOM.And && (result = Patterns.merge(a2 = (QOM.And)q, Patterns::mergeRangePredicates)) != null) {
                return result;
            }
            return q;
        });
    }

    private static final boolean projectionOnly(Select<?> s2) {
        return s2.$distinctOn().isEmpty() && s2.$from().isEmpty() && s2.$where() == null && s2.$connectBy() == null && s2.$connectByStartWith() == null && s2.$groupBy().isEmpty() && s2.$having() == null && s2.$window().isEmpty() && !Patterns.hasUnions(s2);
    }

    private static final boolean projectionOnlyNoAggregatesOrWindowFunctions(Select<?> s2) {
        return Patterns.projectionOnly(s2) && s2.$traverse(CONTAINING_AGGREGATES_OR_WINDOW_FNS) == false;
    }

    private static final Select<?> replaceSelect(Select<?> s2, Function<QueryPart, QueryPart> select, Function<QueryPart, QueryPart> from, Function<QueryPart, QueryPart> where, Function<QueryPart, QueryPart> connectBy, Function<QueryPart, QueryPart> startWith, Function<QueryPart, QueryPart> groupBy, Function<QueryPart, QueryPart> having, Function<QueryPart, QueryPart> window, Function<QueryPart, QueryPart> qualify, Function<QueryPart, QueryPart> orderBy, Function<QueryPart, QueryPart> offset, Function<QueryPart, QueryPart> limit) {
        s2 = Patterns.replaceSelect(s2, x -> x.$select(), (x, q) -> x.$select((Collection<? extends SelectFieldOrAsterisk>)q), select);
        s2 = Patterns.replaceSelect(s2, x -> x.$from(), (x, q) -> x.$from((Collection<? extends Table<?>>)q), from);
        s2 = Patterns.replaceSelect(s2, x -> x.$where(), (x, q) -> x.$where((Condition)q), where);
        s2 = Patterns.replaceSelect(s2, x -> x.$connectBy(), (x, q) -> x.$connectBy((Condition)q), connectBy);
        s2 = Patterns.replaceSelect(s2, x -> x.$connectByStartWith(), (x, q) -> x.$connectByStartWith((Condition)q), startWith);
        s2 = Patterns.replaceSelect(s2, x -> x.$groupBy(), (x, q) -> x.$groupBy((Collection<? extends GroupField>)q), groupBy);
        s2 = Patterns.replaceSelect(s2, x -> x.$having(), (x, q) -> x.$having((Condition)q), having);
        s2 = Patterns.replaceSelect(s2, x -> x.$window(), (x, q) -> x.$window((Collection<? extends WindowDefinition>)q), window);
        s2 = Patterns.replaceSelect(s2, x -> x.$qualify(), (x, q) -> x.$qualify((Condition)q), qualify);
        s2 = Patterns.replaceSelect(s2, x -> x.$orderBy(), (x, q) -> x.$orderBy((Collection<? extends SortField<?>>)q), orderBy);
        s2 = Patterns.replaceSelect(s2, x -> x.$offset(), (x, q) -> x.$offset((Field<? extends Number>)q), offset);
        s2 = Patterns.replaceSelect(s2, x -> x.$limit(), (x, q) -> x.$limit((Field<? extends Number>)q), limit);
        return s2;
    }

    private static final <Q extends QueryPart> Select<?> replaceSelect(Select<?> s2, Function<? super Select<?>, ? extends Q> get2, BiFunction<? super Select<?>, ? super Q, ? extends Select<?>> set, Function<QueryPart, QueryPart> replace) {
        QueryPart q2;
        QueryPart q1 = (QueryPart)get2.apply(s2);
        if (q1 != null && (q2 = q1.$replace(replace)) != q1) {
            return set.apply(s2, q2);
        }
        return s2;
    }

    private static final boolean same(QueryPart p1, QueryPart p2) {
        return p1.equals(p2) || p1 instanceof QOM.UCommutativeOperator && p2 instanceof QOM.UCommutativeOperator && p1.equals(((QOM.UCommutativeOperator)p2).$swap());
    }

    private static final Condition mergeRangePredicates(Condition c1, Condition c2) {
        QOM.CompareCondition cc1;
        if (c1 instanceof QOM.CompareCondition) {
            cc1 = (QOM.CompareCondition)c1;
            c1 = Patterns.tableFieldFilter(cc1);
        }
        if (c2 instanceof QOM.CompareCondition) {
            QOM.CompareCondition cc2 = (QOM.CompareCondition)c2;
            c2 = Patterns.tableFieldFilter(cc2);
        }
        if (c1 instanceof QOM.Ge) {
            cc1 = (QOM.Ge)c1;
            if (c2 instanceof QOM.Le) {
                QOM.Le cc2 = (QOM.Le)c2;
                if (((Field)cc1.$arg1()).equals(cc2.$arg1())) {
                    if (((Field)cc1.$arg2()).equals(cc2.$arg2())) {
                        return ((Field)cc1.$arg1()).eq((Field)cc1.$arg2());
                    }
                    return ((Field)cc1.$arg1()).between((Field)cc1.$arg2()).and((Field)cc2.$arg2());
                }
            } else if (c2 instanceof QOM.And) {
                QOM.And a2 = (QOM.And)c2;
                return Patterns.mergeRecurse(c1, a2, Patterns::mergeRangePredicates, Condition::and);
            }
        } else if (c1 instanceof QOM.Le) {
            QOM.Le cc12 = (QOM.Le)c1;
            if (c2 instanceof QOM.Ge) {
                QOM.Ge cc2 = (QOM.Ge)c2;
                if (((Field)cc12.$arg1()).equals(cc2.$arg1())) {
                    if (((Field)cc12.$arg2()).equals(cc2.$arg2())) {
                        return ((Field)cc12.$arg1()).eq((Field)cc12.$arg2());
                    }
                    return ((Field)cc12.$arg1()).between((Field)cc2.$arg2()).and((Field)cc12.$arg2());
                }
            } else if (c2 instanceof QOM.And) {
                QOM.And a2 = (QOM.And)c2;
                return Patterns.mergeRecurse(c1, a2, Patterns::mergeRangePredicates, Condition::and);
            }
        }
        return null;
    }

    private static final Condition mergeInList(Condition c1, Condition c2) {
        if (c1 instanceof QOM.InList) {
            QOM.And a2;
            Condition result;
            QOM.InList i1 = (QOM.InList)c1;
            if (c2 instanceof QOM.Eq) {
                QOM.Eq e2 = (QOM.Eq)c2;
                if ((i1 = Patterns.tableFieldFilter(i1)) != null && (e2 = Patterns.tableFieldFilter(e2)) != null && ((Field)i1.$arg1()).equals(e2.$arg1())) {
                    ArrayList list = new ArrayList((Collection)i1.$arg2());
                    list.retainAll(Arrays.asList((Field)e2.$arg2()));
                    return ((Field)i1.$arg1()).in(list);
                }
            } else if (c2 instanceof QOM.InList) {
                QOM.InList i2 = (QOM.InList)c2;
                if ((i1 = Patterns.tableFieldFilter(i1)) != null && (i2 = Patterns.tableFieldFilter(i2)) != null && ((Field)i1.$arg1()).equals(i2.$arg1())) {
                    ArrayList list = new ArrayList((Collection)i1.$arg2());
                    list.retainAll((Collection)i2.$arg2());
                    return ((Field)i1.$arg1()).in(list);
                }
            } else if (c2 instanceof QOM.And && (result = Patterns.mergeRecurse(c1, a2 = (QOM.And)c2, Patterns::mergeInList, Condition::and)) != null) {
                return result;
            }
        }
        return null;
    }

    private static final <Q extends QueryPart, R extends QOM.UOperator2<Q, Q, R>> Q merge(R op, BiFunction<? super Q, ? super Q, ? extends Q> merge) {
        QueryPart result = (QueryPart)merge.apply(op.$arg1(), op.$arg2());
        if (result != null) {
            return (Q)result;
        }
        result = (QueryPart)merge.apply(op.$arg2(), op.$arg1());
        if (result != null) {
            return (Q)result;
        }
        return null;
    }

    private static final <Q extends QueryPart, R extends QOM.UOperator2<Q, Q, R>> Q mergeRecurse(Q r1, R r2, BiFunction<? super Q, ? super Q, ? extends Q> recursion, BiFunction<? super Q, ? super Q, ? extends Q> wrap) {
        QueryPart y2;
        QueryPart x1 = (QueryPart)r2.$arg1();
        QueryPart x2 = (QueryPart)r2.$arg2();
        QueryPart y1 = (QueryPart)recursion.apply(r1, x1);
        if (y1 == null) {
            y1 = (QueryPart)recursion.apply(x1, r1);
        }
        if ((y2 = (QueryPart)recursion.apply(y1 != null ? y1 : r1, x2)) == null) {
            y2 = (QueryPart)recursion.apply(x2, y1 != null ? y1 : r1);
        }
        if (y2 != null) {
            if (y1 != null) {
                return (Q)y2;
            }
            return (Q)((QueryPart)wrap.apply(x1, y2));
        }
        if (y1 != null) {
            return (Q)((QueryPart)wrap.apply(y1, x2));
        }
        return null;
    }

    private static final Condition mergeBetweenSymmetric(Condition c1, Condition c2) {
        if (c1 instanceof QOM.Between) {
            QOM.Or o2;
            Condition result;
            QOM.Between b1 = (QOM.Between)c1;
            if (c2 instanceof QOM.Between) {
                QOM.Between b2 = (QOM.Between)c2;
                if (((Field)b1.$arg1()).equals(b2.$arg1()) && ((Field)b1.$arg2()).equals(b2.$arg3()) && ((Field)b1.$arg3()).equals(b2.$arg2())) {
                    return b1.$symmetric(true);
                }
            } else if (c2 instanceof QOM.Or && (result = Patterns.mergeRecurse(c1, o2 = (QOM.Or)c2, Patterns::mergeBetweenSymmetric, Condition::or)) != null) {
                return result;
            }
        }
        return null;
    }

    private static final Condition mergeOrComparison(Condition c1, Condition c2) {
        if (c1 instanceof QOM.CompareCondition) {
            QOM.Or o2;
            Condition result;
            QOM.CompareCondition cc1 = (QOM.CompareCondition)c1;
            if (c2 instanceof QOM.CompareCondition) {
                QOM.CompareCondition cc2 = (QOM.CompareCondition)c2;
                if ((cc1 = Patterns.tableFieldFilter(cc1)) != null && (cc2 = Patterns.tableFieldFilter(cc2)) != null && ((Field)cc1.$arg1()).equals(cc2.$arg1()) && ((Field)cc1.$arg2()).equals(cc2.$arg2())) {
                    if (cc1 instanceof QOM.Eq) {
                        if (cc2 instanceof QOM.Eq) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Ne) {
                            return ((Field)cc1.$arg1()).eq((Field)cc2.$arg1());
                        }
                        if (cc2 instanceof QOM.Gt) {
                            return ((Field)cc1.$arg1()).ge((Field)cc1.$arg2());
                        }
                        if (cc2 instanceof QOM.Ge) {
                            return cc2;
                        }
                        if (cc2 instanceof QOM.Lt) {
                            return ((Field)cc1.$arg1()).le((Field)cc1.$arg2());
                        }
                        if (cc2 instanceof QOM.Le) {
                            return cc2;
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Ne) {
                        if (cc2 instanceof QOM.Ne) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Gt) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Ge) {
                            return ((Field)cc1.$arg1()).eq((Field)cc2.$arg1());
                        }
                        if (cc2 instanceof QOM.Lt) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Le) {
                            return ((Field)cc1.$arg1()).eq((Field)cc2.$arg1());
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Gt) {
                        if (cc2 instanceof QOM.Gt) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Ge) {
                            return cc2;
                        }
                        if (cc2 instanceof QOM.Lt) {
                            return ((Field)cc1.$arg1()).ne((Field)cc2.$arg2());
                        }
                        if (cc2 instanceof QOM.Le) {
                            return ((Field)cc1.$arg1()).eq((Field)cc2.$arg1());
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Ge) {
                        if (cc2 instanceof QOM.Ge) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Lt) {
                            return ((Field)cc1.$arg1()).eq((Field)cc2.$arg1());
                        }
                        if (cc2 instanceof QOM.Le) {
                            return ((Field)cc1.$arg1()).eq((Field)cc2.$arg1());
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Lt) {
                        if (cc2 instanceof QOM.Lt) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Le) {
                            return cc2;
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Le) {
                        if (cc2 instanceof QOM.Le) {
                            return cc1;
                        }
                        return null;
                    }
                    return null;
                }
            } else if (c2 instanceof QOM.Or && (result = Patterns.mergeRecurse(c1, o2 = (QOM.Or)c2, Patterns::mergeOrComparison, Condition::or)) != null) {
                return result;
            }
        }
        return null;
    }

    private static final Condition mergeAndComparison(Condition c1, Condition c2) {
        if (c1 instanceof QOM.CompareCondition) {
            QOM.And a2;
            Condition result;
            QOM.CompareCondition cc1 = (QOM.CompareCondition)c1;
            if (c2 instanceof QOM.CompareCondition) {
                QOM.CompareCondition cc2 = (QOM.CompareCondition)c2;
                if ((cc1 = Patterns.tableFieldFilter(cc1)) != null && (cc2 = Patterns.tableFieldFilter(cc2)) != null && ((Field)cc1.$arg1()).equals(cc2.$arg1()) && ((Field)cc1.$arg2()).equals(cc2.$arg2())) {
                    if (cc1 instanceof QOM.Eq) {
                        if (cc2 instanceof QOM.Eq) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Ne) {
                            return ((Field)cc1.$arg1()).ne((Field)cc1.$arg1());
                        }
                        if (cc2 instanceof QOM.Gt) {
                            return ((Field)cc1.$arg1()).ne((Field)cc1.$arg1());
                        }
                        if (cc2 instanceof QOM.Ge) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Lt) {
                            return ((Field)cc1.$arg1()).ne((Field)cc1.$arg1());
                        }
                        if (cc2 instanceof QOM.Le) {
                            return cc1;
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Ne) {
                        if (cc2 instanceof QOM.Ne) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Gt) {
                            return cc2;
                        }
                        if (cc2 instanceof QOM.Ge) {
                            return ((Field)cc1.$arg1()).gt((Field)cc1.$arg2());
                        }
                        if (cc2 instanceof QOM.Lt) {
                            return cc2;
                        }
                        if (cc2 instanceof QOM.Le) {
                            return ((Field)cc1.$arg1()).lt((Field)cc1.$arg2());
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Gt) {
                        if (cc2 instanceof QOM.Gt) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Ge) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Lt) {
                            return ((Field)cc1.$arg1()).ne((Field)cc1.$arg1());
                        }
                        if (cc2 instanceof QOM.Le) {
                            return ((Field)cc1.$arg1()).ne((Field)cc1.$arg1());
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Ge) {
                        if (cc2 instanceof QOM.Ge) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Lt) {
                            return ((Field)cc1.$arg1()).ne((Field)cc1.$arg1());
                        }
                        if (cc2 instanceof QOM.Le) {
                            return ((Field)cc1.$arg1()).eq((Field)cc1.$arg2());
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Lt) {
                        if (cc2 instanceof QOM.Lt) {
                            return cc1;
                        }
                        if (cc2 instanceof QOM.Le) {
                            return cc1;
                        }
                        return null;
                    }
                    if (cc1 instanceof QOM.Le) {
                        if (cc2 instanceof QOM.Le) {
                            return cc1;
                        }
                        return null;
                    }
                    return null;
                }
            } else if (c2 instanceof QOM.And && (result = Patterns.mergeRecurse(c1, a2 = (QOM.And)c2, Patterns::mergeAndComparison, Condition::and)) != null) {
                return result;
            }
        }
        return null;
    }

    private static final Condition mergeToInOrNotIn(Condition c1, Condition c2, boolean reverse, Class<? extends QOM.CombinedCondition> combType, Class<? extends QOM.CompareCondition<?, ?>> compType, Class<? extends AbstractInList<?>> listType, BiFunction<? super Condition, ? super Condition, ? extends Condition> comb, BiFunction<? super Field<?>, ? super List<? extends Field<?>>, ? extends Condition> list) {
        QOM.CompareCondition<?, ?> e1;
        if (compType.isInstance(c1) && (e1 = Patterns.tableFieldFilter(compType.cast(c1))) != null) {
            Condition result;
            if (compType.isInstance(c2)) {
                QOM.CompareCondition<?, ?> e2 = Patterns.tableFieldFilter(compType.cast(c2));
                if (e2 != null && ((Field)e1.$arg1()).equals(e2.$arg1())) {
                    return list.apply((Field<?>)e1.$arg1(), (List<Field<?>>)(reverse ? Arrays.asList((Field)e2.$arg2(), (Field)e1.$arg2()) : Arrays.asList((Field)e1.$arg2(), (Field)e2.$arg2())));
                }
            } else if (listType.isInstance(c2)) {
                AbstractInList<?> i2 = Patterns.tableFieldFilter(listType.cast(c2));
                if (i2 != null && ((Field)e1.$arg1()).equals(i2.$arg1())) {
                    ArrayList l = new ArrayList();
                    if (reverse) {
                        l.addAll(i2.$arg2());
                    }
                    l.add((Field)e1.$arg2());
                    if (!reverse) {
                        l.addAll(i2.$arg2());
                    }
                    return list.apply((Field<?>)e1.$arg1(), l);
                }
            } else if (combType.isInstance(c2) && (result = Patterns.mergeRecurse(c1, combType.cast(c2), (q1, q2) -> Patterns.mergeToInOrNotIn(q1, q2, reverse, combType, compType, listType, comb, list), comb)) != null) {
                return result;
            }
        }
        return null;
    }

    private static final <Q extends QOM.CompareCondition<?, ?>> Q tableFieldFilter(Q c) {
        if (c.$arg1() instanceof TableField && c.$arg2() instanceof Val) {
            return c;
        }
        if (c.$arg2() instanceof TableField && c.$arg1() instanceof Val) {
            if (c instanceof QOM.UConvertibleOperator) {
                QOM.UConvertibleOperator o = (QOM.UConvertibleOperator)((Object)c);
                return (Q)((QOM.CompareCondition)o.$converse());
            }
            return null;
        }
        return null;
    }

    private static final <Q extends AbstractInList<?>> Q tableFieldFilter(Q c) {
        if (c.$arg1() instanceof TableField) {
            return c;
        }
        return null;
    }

    private static final <Q extends QOM.InList<?>> Q tableFieldFilter(Q c) {
        if (c.$arg1() instanceof TableField) {
            return c;
        }
        return null;
    }

    private static final boolean isGrouped(Select<?> s2) {
        return !s2.$groupBy().isEmpty() || s2.$having() != null || Patterns.hasAggregateFunctions(s2);
    }

    private static final boolean hasAggregateFunctions(Select<?> s2) {
        return s2.$select().$traverse(CONTAINING_AGGREGATES) != false || s2.$having() != null && s2.$having().$traverse(CONTAINING_AGGREGATES) != false || s2.$window().$traverse(CONTAINING_AGGREGATES) != false || s2.$qualify() != null && s2.$qualify().$traverse(CONTAINING_AGGREGATES) != false || s2.$orderBy().$traverse(CONTAINING_AGGREGATES) != false;
    }

    private static final boolean hasUnions(Select<?> s2) {
        return Tools.selectQueryImpl(s2).hasUnions();
    }

    static final boolean isEmpty(Select<?> s2) {
        if (Patterns.hasUnions(s2)) {
            return false;
        }
        return Patterns.isFalse(s2.$where()) && !Patterns.isGrouped(s2) || Patterns.isFalse(s2.$having()) || Patterns.isFalse(s2.$connectBy()) || Patterns.isFalse(s2.$connectByStartWith()) || Patterns.isFalse(s2.$qualify()) || Patterns.isInlineVal0(s2.$limit(), n -> n == null || n.intValue() <= 0);
    }

    static final boolean isNull(QueryPart p) {
        if (Patterns.isInline(p)) {
            return Patterns.isNull0((Field)p);
        }
        if (p instanceof QOM.Cast) {
            QOM.Cast c = (QOM.Cast)p;
            return Patterns.isNull(c.$field());
        }
        if (p instanceof Coerce) {
            Coerce c = (Coerce)p;
            return Patterns.isNull(c.$field());
        }
        return p instanceof NullCondition;
    }

    static final boolean isNull0(Field<?> val) {
        return ((Val)val).$value() == null;
    }

    static final boolean isInline(QueryPart p) {
        if (p instanceof Val) {
            Val v = (Val)p;
            return v.isInline();
        }
        return false;
    }

    static final boolean isVal(Field<?> field2, Object val) {
        return Patterns.isVal0(field2, v -> Patterns.eq(val, v));
    }

    static final boolean isInlineVal(Field<?> field2, Object val) {
        return Patterns.isInlineVal0(field2, v -> Patterns.eq(val, v));
    }

    static final boolean eq(Object v1, Object v2) {
        if (Objects.equals(v1, v2)) {
            return true;
        }
        if (v1 instanceof Number) {
            Number n1 = (Number)v1;
            if (v2 instanceof Number) {
                Long l2;
                Number n2 = (Number)v2;
                Long l1 = Patterns.longIfInteger(n1);
                if (l1 != null && (l2 = Patterns.longIfInteger(n2)) != null) {
                    return l1.equals(l2);
                }
            }
        }
        return false;
    }

    static final boolean isNeg(Field<?> f) {
        return f instanceof QOM.Neg || Patterns.isInlineVal0(f, v -> v instanceof Number && Patterns.isNeg((Number)v));
    }

    static final boolean isNeg(Number n) {
        return n.doubleValue() < -0.0;
    }

    static final <N extends Number> Field<N> neg(Field<N> f) {
        if (f instanceof QOM.Neg) {
            QOM.Neg n = (QOM.Neg)f;
            return (Field)n.$arg1();
        }
        if (Patterns.isInline(f)) {
            return DSL.inline(Patterns.neg((Number)((Val)f).getValue()));
        }
        return f.neg();
    }

    static final <N extends Number> N neg(N n) {
        if (n instanceof Byte) {
            Byte b = (Byte)n;
            return (N)Byte.valueOf(-b.byteValue());
        }
        if (n instanceof Short) {
            Short s2 = (Short)n;
            return (N)Short.valueOf(-s2.shortValue());
        }
        if (n instanceof Integer) {
            Integer i = (Integer)n;
            return (N)Integer.valueOf(-i.intValue());
        }
        if (n instanceof Long) {
            Long l = (Long)n;
            return (N)Long.valueOf(-l.longValue());
        }
        if (n instanceof Float) {
            Float f = (Float)n;
            return (N)Float.valueOf(-f.floatValue());
        }
        if (n instanceof Double) {
            Double d = (Double)n;
            return (N)Double.valueOf(-d.doubleValue());
        }
        if (n instanceof BigInteger) {
            BigInteger i = (BigInteger)n;
            return (N)i.negate();
        }
        if (n instanceof BigDecimal) {
            BigDecimal d = (BigDecimal)n;
            return (N)d.negate();
        }
        return null;
    }

    static final Long longIfInteger(Number n) {
        if (n instanceof Byte) {
            return n.longValue();
        }
        if (n instanceof Short) {
            return n.longValue();
        }
        if (n instanceof Integer) {
            return n.longValue();
        }
        if (n instanceof Long) {
            Long l = (Long)n;
            return l;
        }
        return null;
    }

    static final <T> boolean isVal0(Field<T> field2, Predicate<? super T> predicate) {
        if (field2 instanceof Val) {
            Val v = (Val)field2;
            return predicate.test(v.$value());
        }
        return false;
    }

    static final <T> boolean isInlineVal0(Field<T> field2, Predicate<? super T> predicate) {
        Val v;
        if (field2 instanceof Val && (v = (Val)field2).isInline()) {
            return predicate.test(v.$value());
        }
        return false;
    }

    static final boolean isInlineVal1(QueryPart field2, Predicate<? super Object> predicate) {
        Val v;
        if (field2 instanceof Val && (v = (Val)field2).isInline()) {
            return predicate.test(v.$value());
        }
        return false;
    }

    static final boolean isNull(Condition f) {
        Condition c = Patterns.extractCondition(f);
        if (c instanceof ConditionProviderImpl) {
            ConditionProviderImpl p = (ConditionProviderImpl)c;
            return Patterns.isNull(p.getWhere());
        }
        if (c instanceof Null) {
            return true;
        }
        if (c instanceof QOM.FieldCondition) {
            QOM.FieldCondition fc = (QOM.FieldCondition)c;
            return Patterns.isInlineVal(fc.$field(), null);
        }
        return false;
    }

    static final boolean isFalse(Field<?> f) {
        Condition c = Patterns.extractCondition(f);
        if (c instanceof ConditionProviderImpl) {
            ConditionProviderImpl p = (ConditionProviderImpl)c;
            return Patterns.isFalse(p.getWhere());
        }
        if (c instanceof False) {
            return true;
        }
        if (c instanceof QOM.FieldCondition) {
            QOM.FieldCondition fc = (QOM.FieldCondition)c;
            return Patterns.isInlineVal(fc.$field(), Boolean.FALSE);
        }
        return Patterns.isInlineVal(f, Boolean.FALSE);
    }

    static final boolean isTrue(Field<?> f) {
        Condition c = Patterns.extractCondition(f);
        if (c instanceof ConditionProviderImpl) {
            ConditionProviderImpl p = (ConditionProviderImpl)c;
            return Patterns.isTrue(p.getWhere());
        }
        if (c instanceof True) {
            return true;
        }
        if (c instanceof QOM.FieldCondition) {
            QOM.FieldCondition fc = (QOM.FieldCondition)c;
            return Patterns.isInlineVal(fc.$field(), Boolean.TRUE);
        }
        return Patterns.isInlineVal(f, Boolean.TRUE);
    }

    static final boolean isEffectivelyTrue(Field<?> f) {
        if (f == null) {
            return true;
        }
        Condition c = Patterns.extractCondition(f);
        if (c instanceof ConditionProviderImpl) {
            ConditionProviderImpl p = (ConditionProviderImpl)c;
            return Patterns.isEffectivelyTrue(p.getWhere());
        }
        if (c instanceof True) {
            return true;
        }
        if (c instanceof NoCondition) {
            return true;
        }
        if (c instanceof QOM.FieldCondition) {
            QOM.FieldCondition fc = (QOM.FieldCondition)c;
            return Patterns.isInlineVal(fc.$field(), Boolean.TRUE);
        }
        return false;
    }

    static final Condition extractCondition(Field<?> f) {
        if (f instanceof Condition) {
            return (Condition)f;
        }
        if (f instanceof ConditionAsField) {
            ConditionAsField cf = (ConditionAsField)f;
            return cf.$condition();
        }
        return null;
    }

    static final Field<Boolean> extractField(Condition c) {
        if (c instanceof QOM.FieldCondition) {
            QOM.FieldCondition fc = (QOM.FieldCondition)c;
            return fc.$field();
        }
        return c;
    }

    static final <Q extends QueryPart> Q arg1(QOM.UOperator2<Q, Q, ?> op, int i) {
        return (Q)(i == 0 ? (QueryPart)op.$arg1() : (QueryPart)op.$arg2());
    }

    static final <Q extends QueryPart> Q arg2(QOM.UOperator2<Q, Q, ?> op, int i) {
        return (Q)(i == 0 ? (QueryPart)op.$arg2() : (QueryPart)op.$arg1());
    }
}

