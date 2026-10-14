/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jooq.Binding;
import org.jooq.Check;
import org.jooq.Comment;
import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.DDLExportConfiguration;
import org.jooq.DataType;
import org.jooq.Domain;
import org.jooq.EmbeddableRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.ParamMode;
import org.jooq.Parameter;
import org.jooq.Pro;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Statement;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableElement;
import org.jooq.TableField;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.UniqueKey;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.CheckImpl;
import org.jooq.impl.DDL;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.DefaultConverterContext;
import org.jooq.impl.DomainImpl;
import org.jooq.impl.EmbeddableTableField;
import org.jooq.impl.IAdd;
import org.jooq.impl.IDiv;
import org.jooq.impl.IMul;
import org.jooq.impl.ISub;
import org.jooq.impl.IdentityImpl;
import org.jooq.impl.IndexImpl;
import org.jooq.impl.Lazy;
import org.jooq.impl.Neg;
import org.jooq.impl.ParameterImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.ReferenceImpl;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.SequenceImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.UniqueKeyImpl;
import org.jooq.tools.StringUtils;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;
import org.jooq.util.oracle.Queue;
import org.jooq.util.oracle.QueueImpl;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@org.jooq.Internal
public final class Internal {
    private static final Lazy<ConverterContext> CONVERTER_SCOPE = Lazy.of(() -> new DefaultConverterContext(Tools.CONFIG.get()));
    private static final Lazy<Integer> JAVA_VERSION = Lazy.of(() -> {
        try {
            return (Integer)Reflect.onClass(Runtime.class).call("version").call("feature").get();
        }
        catch (ReflectException e) {
            return 8;
        }
    });

    @SafeVarargs
    @NotNull
    public static final <R extends Record, E extends EmbeddableRecord<E>> TableField<R, E> createEmbeddable(Name name, Class<E> recordType, Table<R> table, TableField<R, ?> ... fields) {
        return Internal.createEmbeddable(name, recordType, false, table, fields);
    }

    @SafeVarargs
    @NotNull
    public static final <R extends Record, E extends EmbeddableRecord<E>> TableField<R, E> createEmbeddable(Name name, Class<E> recordType, boolean replacesFields, Table<R> table, TableField<R, ?> ... fields) {
        return new EmbeddableTableField<R, E>(name, recordType, replacesFields, table, fields);
    }

    @NotNull
    public static final Index createIndex(Name name, Table<?> table, OrderField<?>[] sortFields, boolean unique) {
        return new IndexImpl(name, table, sortFields, null, unique);
    }

    @NotNull
    public static final <R extends Record, T> Identity<R, T> createIdentity(Table<R> table, TableField<R, T> field2) {
        return new IdentityImpl<R, T>(table, field2);
    }

    @SafeVarargs
    @NotNull
    public static final <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, TableField<R, ?> ... fields) {
        return Internal.createUniqueKey(table, (Name)null, fields, true);
    }

    @SafeVarargs
    @NotNull
    public static final <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, Name name, TableField<R, ?> ... fields) {
        return Internal.createUniqueKey(table, name, fields, true);
    }

    @NotNull
    public static final <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, Name name, TableField<R, ?>[] fields, boolean enforced) {
        return new UniqueKeyImpl<R>(table, name, fields, enforced);
    }

    @NotNull
    public static final <R extends Record, ER extends EmbeddableRecord<ER>> UniqueKey<R> createUniqueKey(Table<R> table, Name name, TableField<R, ER> embeddableField, boolean enforced) {
        return Internal.createUniqueKey(table, name, Internal.fields(embeddableField), enforced);
    }

    @Deprecated
    @SafeVarargs
    @NotNull
    public static final <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(UniqueKey<U> key, Table<R> table, TableField<R, ?> ... fields) {
        return Internal.createForeignKey(table, (Name)null, fields, key, key.getFieldsArray(), true);
    }

    @NotNull
    public static final <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(Table<R> table, Name name, TableField<R, ?>[] fkFields, UniqueKey<U> uk, TableField<U, ?>[] ukFields, boolean enforced) {
        ReferenceImpl<R, Object> result = new ReferenceImpl<R, Object>(table, name, fkFields, uk, ukFields == null ? uk.getFieldsArray() : ukFields, enforced);
        if (uk instanceof UniqueKeyImpl) {
            UniqueKeyImpl u = (UniqueKeyImpl)uk;
            u.references.add(result);
        }
        return result;
    }

    @NotNull
    public static final <R extends Record, U extends Record, ER extends EmbeddableRecord<ER>> ForeignKey<R, U> createForeignKey(Table<R> table, Name name, TableField<R, ER> fkEmbeddableField, UniqueKey<U> uk, TableField<U, ER> ukEmbeddableField, boolean enforced) {
        return Internal.createForeignKey(table, name, Internal.fields(fkEmbeddableField), uk, Internal.fields(ukEmbeddableField), enforced);
    }

    @NotNull
    public static final <T extends Number> Sequence<T> createSequence(String name, Schema schema, DataType<T> type) {
        return new SequenceImpl<T>(name, schema, type, false);
    }

    @NotNull
    public static final <T extends Number> Sequence<T> createSequence(String name, Schema schema, DataType<T> type, Number startWith, Number incrementBy, Number minvalue, Number maxvalue, boolean cycle, Number cache) {
        return new SequenceImpl<T>(DSL.name(name), schema, type, false, startWith != null ? Tools.field((Object)startWith, type) : null, incrementBy != null ? Tools.field((Object)incrementBy, type) : null, minvalue != null ? Tools.field((Object)minvalue, type) : null, maxvalue != null ? Tools.field((Object)maxvalue, type) : null, cycle, cache != null ? Tools.field((Object)cache, type) : null);
    }

    @NotNull
    public static final <R extends Record> Check<R> createCheck(Table<R> table, Name name, String condition) {
        return Internal.createCheck(table, name, condition, true);
    }

    @NotNull
    public static final <R extends Record> Check<R> createCheck(Table<R> table, Name name, String condition, boolean enforced) {
        return new CheckImpl<R>(table, name, DSL.condition(condition), enforced);
    }

    @NotNull
    public static final <T> Domain<T> createDomain(Schema schema, Name name, DataType<T> type, Check<?> ... checks) {
        return Internal.createDomain(schema, name, type, null, null, checks);
    }

    @NotNull
    public static final <T, U> Domain<U> createDomain(Schema schema, Name name, DataType<T> type, Converter<T, U> converter, Check<?> ... checks) {
        return Internal.createDomain(schema, name, type, converter, null, checks);
    }

    @NotNull
    public static final <T, U> Domain<U> createDomain(Schema schema, Name name, DataType<T> type, Binding<T, U> binding, Check<?> ... checks) {
        return Internal.createDomain(schema, name, type, null, binding, checks);
    }

    @NotNull
    public static final <T, X, U> Domain<U> createDomain(Schema schema, Name name, DataType<T> type, Converter<X, U> converter, Binding<T, X> binding, Check<?> ... checks) {
        Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
        DataType<Object> actualType = converter == null && binding == null ? type : type.asConvertedDataType(actualBinding);
        return new DomainImpl<T>(schema, name, actualType, checks);
    }

    @NotNull
    public static final Name createPathAlias(Table<?> child, ForeignKey<?, ?> path) {
        Name name = DSL.name(path.getName());
        if (child instanceof TableImpl) {
            TableImpl t2 = (TableImpl)child;
            Table<?> ancestor = t2.child;
            name = ancestor != null ? Internal.createPathAlias(ancestor, t2.childPath).append(name) : child.getQualifiedName().append(name);
        }
        return DSL.name("alias_" + Internal.hash(name));
    }

    @NotNull
    public static final <T> Parameter<T> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed) {
        return Internal.createParameter(name, type, isDefaulted, isUnnamed, null, null);
    }

    @NotNull
    public static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Converter<T, U> converter) {
        return Internal.createParameter(name, type, isDefaulted, isUnnamed, converter, null);
    }

    @NotNull
    public static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Binding<T, U> binding) {
        return Internal.createParameter(name, type, isDefaulted, isUnnamed, null, binding);
    }

    @NotNull
    public static final <T, X, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Converter<X, U> converter, Binding<T, X> binding) {
        Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
        DataType<Object> actualType = converter == null && binding == null ? type : type.asConvertedDataType(actualBinding);
        return new ParameterImpl<T>(ParamMode.IN, DSL.name(name), actualType, isDefaulted, isUnnamed);
    }

    @NotNull
    @Pro
    public static final <R extends UDTRecord<R>> Queue<R> createQueue(String name, Schema schema, UDT<R> type) {
        return new QueueImpl<R>(name, schema, type);
    }

    private Internal() {
    }

    @Deprecated(since="3.14", forRemoval=true)
    @NotNull
    public static final Index createIndex(String name, Table<?> table, OrderField<?>[] sortFields, boolean unique) {
        return Internal.createIndex(DSL.name(name), table, sortFields, unique);
    }

    @SafeVarargs
    @Deprecated(since="3.14", forRemoval=true)
    @NotNull
    public static final <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, String name, TableField<R, ?> ... fields) {
        return Internal.createUniqueKey(table, name, fields, true);
    }

    @Deprecated(since="3.14", forRemoval=true)
    @NotNull
    public static final <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, String name, TableField<R, ?>[] fields, boolean enforced) {
        return Internal.createUniqueKey(table, DSL.name(name), fields, enforced);
    }

    @SafeVarargs
    @Deprecated(since="3.14", forRemoval=true)
    @NotNull
    public static final <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(UniqueKey<U> key, Table<R> table, String name, TableField<R, ?> ... fields) {
        return Internal.createForeignKey(key, table, name, fields, true);
    }

    @Deprecated(since="3.14", forRemoval=true)
    @NotNull
    public static final <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(UniqueKey<U> key, Table<R> table, String name, TableField<R, ?>[] fields, boolean enforced) {
        return Internal.createForeignKey(table, DSL.name(name), fields, key, key.getFieldsArray(), enforced);
    }

    @Deprecated(since="3.14", forRemoval=true)
    @NotNull
    public static final <R extends Record, ER extends EmbeddableRecord<ER>> TableField<R, ?>[] fields(TableField<R, ER> embeddableField) {
        return ((EmbeddableTableField)embeddableField).fields;
    }

    @Deprecated(since="3.16", forRemoval=true)
    @NotNull
    public static final <R extends Record, ER extends EmbeddableRecord<ER>> Row fieldsRow(TableField<R, ER> embeddableField) {
        return embeddableField.getDataType().getRow();
    }

    @Support
    static final <T> Field<T> ineg(Field<T> field2) {
        return new Neg<T>(field2, true);
    }

    @Support
    static final <T> Field<T> iadd(Field<T> lhs, Field<?> rhs) {
        return new IAdd<T>(lhs, Tools.nullSafe(rhs, lhs.getDataType()));
    }

    @Support
    static final <T> Field<T> isub(Field<T> lhs, Field<?> rhs) {
        return new ISub<T>(lhs, Tools.nullSafe(rhs, lhs.getDataType()));
    }

    @Support
    static final <T> Field<T> imul(Field<T> lhs, Field<?> rhs) {
        return new IMul<T>(lhs, Tools.nullSafe(rhs, lhs.getDataType()));
    }

    @Support
    static final <T> Field<T> idiv(Field<T> lhs, Field<?> rhs) {
        return new IDiv<T>(lhs, Tools.nullSafe(rhs, lhs.getDataType()));
    }

    public static final <T> Subscriber<T> subscriber(final Consumer<? super Subscription> subscription, final Consumer<? super T> onNext, final Consumer<? super Throwable> onError2, final Runnable onComplete) {
        return new Subscriber<T>(){

            @Override
            public void onSubscribe(Subscription s2) {
                subscription.accept(s2);
            }

            @Override
            public void onNext(T t2) {
                onNext.accept(t2);
            }

            @Override
            public void onError(Throwable t2) {
                onError2.accept(t2);
            }

            @Override
            public void onComplete() {
                onComplete.run();
            }
        };
    }

    public static final <T> Class<T[]> arrayType(Class<T> type) {
        return type.arrayType();
    }

    public static final <R extends Record> Result<R> result(R record) {
        return new ResultImpl(Tools.configuration(record), (AbstractRow)((AbstractRecord)record).fields);
    }

    public static final boolean commercial() {
        return Tools.CONFIG.get().commercial();
    }

    public static final boolean commercial(Supplier<String> logMessage) {
        return Tools.CONFIG.get().commercial(logMessage);
    }

    public static final void requireCommercial(Supplier<String> logMessage) throws DataAccessException {
        Tools.CONFIG.get().requireCommercial(logMessage);
    }

    @Pro
    public static final Replacer decomposing() {
        return Replacer.of(p -> p instanceof Queries || p instanceof Statement, p -> {
            if (p instanceof Queries) {
                Queries q = (Queries)p;
                List<? extends Query> output = null;
                QOM.UnmodifiableList<? extends Query> input = q.$queries();
                for (int i = 0; i < input.size(); ++i) {
                    Query query = (Query)input.get(i);
                    ArrayList<Query> append = new ArrayList<Query>();
                    if (query instanceof QOM.CreateTable) {
                        QOM.CreateTable ct = (QOM.CreateTable)query;
                        Comment comment = ct.$comment();
                        if (comment != null) {
                            ct = ct.$comment(null);
                            query = ct;
                            append.add(Tools.configuration(query).dsl().commentOnTable(ct.$table()).is(comment));
                        }
                        List<? extends TableElement> outputElements = null;
                        QOM.UnmodifiableList<? extends TableElement> inputElements = ct.$tableElements();
                        for (int j = 0; j < inputElements.size(); ++j) {
                            TableElement te = (TableElement)inputElements.get(j);
                            if (te instanceof Field) {
                                Field column = (Field)te;
                                comment = column.getCommentPart();
                                if (!StringUtils.isBlank(comment.$comment())) {
                                    outputElements = Internal.lazy(outputElements, inputElements, j);
                                    outputElements.add(DSL.field(column.getQualifiedName(), column.getDataType()));
                                    append.add(Tools.configuration(query).dsl().commentOnColumn(ct.$table().getQualifiedName().append(column.getUnqualifiedName())).is(comment));
                                    continue;
                                }
                            } else if (te instanceof Index) {
                                Index index = (Index)te;
                                outputElements = Internal.lazy(outputElements, inputElements, j);
                                append.add(new DDL(Tools.configuration(query).dsl(), new DDLExportConfiguration()).createIndex(index));
                                continue;
                            }
                            if (outputElements == null) continue;
                            outputElements.add(te);
                        }
                        if (outputElements != null) {
                            ct = ct.$tableElements(outputElements);
                            query = ct;
                        }
                    }
                    if (append.isEmpty()) continue;
                    output = Internal.lazy(output, input, i);
                    output.add(query);
                    output.addAll(append);
                }
                if (output != null) {
                    return Tools.configuration(q).dsl().queries(output);
                }
            }
            return p;
        });
    }

    private static final <T> List<T> lazy(List<T> result, List<? extends T> source2, int i) {
        if (result == null) {
            result = new ArrayList<T>(source2.size());
            result.addAll(source2.subList(0, i));
        }
        return result;
    }

    public static final int hash(QueryPart part) {
        return Internal.hash0(Tools.CTX.get().render(part));
    }

    static final int hash0(Object object) {
        if (object == null) {
            return 0;
        }
        return 0x7FFFFFF & object.hashCode();
    }

    public static final ConverterContext converterContext() {
        return CONVERTER_SCOPE.get();
    }

    public static final int javaVersion() {
        return JAVA_VERSION.get();
    }
}

