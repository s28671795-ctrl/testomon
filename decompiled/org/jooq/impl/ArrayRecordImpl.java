/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import org.jooq.ArrayRecord;
import org.jooq.Attachable;
import org.jooq.Binding;
import org.jooq.CSVFormat;
import org.jooq.Catalog;
import org.jooq.ChartFormat;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Internal;
import org.jooq.JSONFormat;
import org.jooq.Package;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Schema;
import org.jooq.TXTFormat;
import org.jooq.Table;
import org.jooq.XMLFormat;
import org.jooq.impl.AbstractResult;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractStore;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.Names;
import org.jooq.impl.RecordImpl1;
import org.jooq.impl.ResultImpl;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

@Internal
@Pro
public class ArrayRecordImpl<T>
extends AbstractStore
implements ArrayRecord<T> {
    private final Schema schema;
    private final Package pkg;
    private final DataType<T> baseType;
    private final DataType<?> type;
    private final String name;
    private final List<T> list;

    protected ArrayRecordImpl(Schema schema, String name, DataType<T> type) {
        this(schema, null, name, type, null, null);
    }

    protected ArrayRecordImpl(Schema schema, Package pkg, String name, DataType<T> type) {
        this(schema, pkg, name, type, null, null);
    }

    protected <X> ArrayRecordImpl(Schema schema, String name, DataType<X> type, Converter<X, T> converter) {
        this(schema, null, name, type, converter, null);
    }

    protected <X> ArrayRecordImpl(Schema schema, Package pkg, String name, DataType<X> type, Converter<X, T> converter) {
        this(schema, pkg, name, type, converter, null);
    }

    protected <X, Y> ArrayRecordImpl(Schema schema, String name, DataType<X> type, Binding<X, Y> binding) {
        this(schema, null, name, type, null, binding);
    }

    protected <X, Y> ArrayRecordImpl(Schema schema, Package pkg, String name, DataType<X> type, Binding<X, Y> binding) {
        this(schema, pkg, name, type, null, binding);
    }

    protected <X, Y> ArrayRecordImpl(Schema schema, String name, DataType<X> type, Converter<Y, T> converter, Binding<X, Y> binding) {
        this(schema, null, name, type, converter, binding);
    }

    protected <X, Y> ArrayRecordImpl(Schema schema, Package pkg, String name, DataType<X> type, Converter<Y, T> converter, Binding<X, Y> binding) {
        super(null);
        this.schema = schema;
        this.pkg = pkg;
        this.name = name;
        this.baseType = converter == null && binding == null ? type : type.asConvertedDataType(DefaultBinding.newBinding(converter, type, binding));
        this.type = this.baseType.asArrayDataType(this.getClass());
        this.list = new ArrayList<T>();
    }

    final List<Attachable> getAttachables() {
        return Attachable.class.isAssignableFrom(this.baseType.getType()) ? this : new ArrayList();
    }

    @Override
    public final Catalog getCatalog() {
        return this.getSchema() == null ? null : this.getSchema().getCatalog();
    }

    @Override
    public final Schema getSchema() {
        return this.schema;
    }

    @Override
    public final Package getPackage() {
        return this.pkg;
    }

    @Override
    public final boolean isSQLUsable() {
        return this.pkg == null;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final DataType<T> getDataType() {
        return this.baseType;
    }

    @Override
    public final DataType<?> getArrayType() {
        return this.type;
    }

    public String toString() {
        return this.create().render(DSL.inline(this));
    }

    @Override
    public final List<T> toList() {
        return Collections.unmodifiableList(this);
    }

    @Override
    public final int size() {
        return this.list.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public final boolean contains(Object o) {
        return this.list.contains(o);
    }

    @Override
    public final Iterator<T> iterator() {
        return this.list.iterator();
    }

    @Override
    public final Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    public final <Z> Z[] toArray(Z[] a) {
        return this.list.toArray(a);
    }

    @Override
    public final boolean add(T e) {
        return this.list.add(e);
    }

    @Override
    public final boolean remove(Object o) {
        return this.list.remove(o);
    }

    @Override
    public final boolean containsAll(Collection<?> c) {
        return this.list.containsAll(c);
    }

    @Override
    public final boolean addAll(Collection<? extends T> c) {
        return this.list.addAll(c);
    }

    @Override
    public final boolean addAll(int index, Collection<? extends T> c) {
        return this.list.addAll(index, c);
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        return this.list.removeAll(c);
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        return this.list.retainAll(c);
    }

    @Override
    public final void clear() {
        this.list.clear();
    }

    @Override
    public final boolean equals(Object o) {
        return this.list.equals(o);
    }

    @Override
    public final int hashCode() {
        return this.list.hashCode();
    }

    @Override
    public final T get(int index) {
        return this.list.get(index);
    }

    @Override
    public final T set(int index, T element) {
        return this.list.set(index, element);
    }

    @Override
    public final void add(int index, T element) {
        this.list.add(index, element);
    }

    @Override
    public final T remove(int index) {
        return this.list.remove(index);
    }

    @Override
    public final int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    @Override
    public final int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    @Override
    public final ListIterator<T> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public final ListIterator<T> listIterator(int index) {
        return this.list.listIterator(index);
    }

    @Override
    public final List<T> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    private final void formatAsResult(Consumer<? super Result<?>> format) {
        ResultImpl result = new ResultImpl(this.configuration(), (AbstractRow)this.baseType.getRow());
        for (T record : this) {
            result.add((Record)record);
        }
        format.accept(result);
    }

    private final void formatScalarAsResult(Consumer<? super Result<?>> format) {
        AbstractRow row = (AbstractRow)((Object)DSL.row(DSL.field(Names.N_VALUE, this.baseType)));
        ResultImpl result = new ResultImpl(this.configuration(), row);
        for (T value : this) {
            result.add(new RecordImpl1<T>(row).values(value));
        }
        format.accept(result);
    }

    @Override
    public final void format(Writer writer, TXTFormat format) {
        try {
            if (this.baseType.isUDT()) {
                this.formatAsResult(r -> r.format(writer, format));
            } else {
                writer.append(AbstractResult.format0(this.toArray(), false, false));
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing text", e);
        }
    }

    @Override
    public final void formatHTML(Writer writer) {
        try {
            if (this.baseType.isUDT()) {
                this.formatAsResult(r -> r.formatHTML(writer));
            } else {
                this.formatScalarAsResult(r -> r.formatHTML(writer));
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing text", e);
        }
    }

    @Override
    public final void formatCSV(Writer writer, CSVFormat format) {
        try {
            if (this.baseType.isUDT()) {
                this.formatAsResult(r -> r.formatCSV(writer, format));
            } else {
                this.formatScalarAsResult(r -> r.formatCSV(writer, format));
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing JSON", e);
        }
    }

    @Override
    public final void formatJSON(Writer writer, JSONFormat format) {
        JSONFormat f = format.mutable(true);
        try {
            if (this.baseType.isUDT()) {
                this.formatAsResult(r -> r.formatJSON(writer, f));
            } else {
                AbstractResult.formatJSON0(this.toArray(), writer, f);
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing JSON", e);
        }
    }

    @Override
    public final void formatXML(Writer writer, XMLFormat format) {
        XMLFormat f = format.mutable(true);
        try {
            if (this.baseType.isUDT()) {
                this.formatAsResult(r -> r.formatXML(writer, f));
            } else {
                this.formatScalarAsResult(r -> r.formatXML(writer, f));
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing JSON", e);
        }
    }

    @Override
    public final void formatChart(Writer writer, ChartFormat format) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final void formatInsert(Writer writer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final void formatInsert(Writer writer, Table<?> table, Field<?> ... fields) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final Document intoXML(XMLFormat format) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final <H extends ContentHandler> H intoXML(H handler, XMLFormat format) throws SAXException {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

