/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jooq.AssociativeArrayRecord;
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
import org.jooq.Schema;
import org.jooq.TXTFormat;
import org.jooq.Table;
import org.jooq.XMLFormat;
import org.jooq.impl.AbstractStore;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

@Internal
@Pro
public class AssociativeArrayRecordImpl<K, V>
extends AbstractStore
implements AssociativeArrayRecord<K, V> {
    private final Schema schema;
    private final Package pkg;
    private final DataType<V> baseType;
    private final DataType<K> indexDataType;
    private final DataType<?> type;
    private final String name;
    private final Map<K, V> map;

    protected AssociativeArrayRecordImpl(Schema schema, String name, DataType<V> type, DataType<K> indexDataType) {
        this(schema, null, name, type, indexDataType, null, null);
    }

    protected AssociativeArrayRecordImpl(Schema schema, Package pkg, String name, DataType<V> type, DataType<K> indexDataType) {
        this(schema, pkg, name, type, indexDataType, null, null);
    }

    protected <X> AssociativeArrayRecordImpl(Schema schema, String name, DataType<X> type, DataType<K> indexDataType, Converter<X, V> converter) {
        this(schema, null, name, type, indexDataType, converter, null);
    }

    protected <X> AssociativeArrayRecordImpl(Schema schema, Package pkg, String name, DataType<X> type, DataType<K> indexDataType, Converter<X, V> converter) {
        this(schema, pkg, name, type, indexDataType, converter, null);
    }

    protected <X, Y> AssociativeArrayRecordImpl(Schema schema, String name, DataType<X> type, DataType<K> indexDataType, Binding<X, Y> binding) {
        this(schema, null, name, type, indexDataType, null, binding);
    }

    protected <X, Y> AssociativeArrayRecordImpl(Schema schema, Package pkg, String name, DataType<X> type, DataType<K> indexDataType, Binding<X, Y> binding) {
        this(schema, pkg, name, type, indexDataType, null, binding);
    }

    protected <X, Y> AssociativeArrayRecordImpl(Schema schema, String name, DataType<X> type, DataType<K> indexDataType, Converter<Y, V> converter, Binding<X, Y> binding) {
        this(schema, null, name, type, indexDataType, converter, binding);
    }

    protected <X, Y> AssociativeArrayRecordImpl(Schema schema, Package pkg, String name, DataType<X> type, DataType<K> indexDataType, Converter<Y, V> converter, Binding<X, Y> binding) {
        super(null);
        this.schema = schema;
        this.pkg = pkg;
        this.name = name;
        this.baseType = converter == null && binding == null ? type : type.asConvertedDataType(DefaultBinding.newBinding(converter, type, binding));
        this.indexDataType = indexDataType;
        this.type = this.baseType.asAssociativeArrayDataType(this.getClass());
        this.map = new LinkedHashMap();
    }

    final List<Attachable> getAttachables() {
        return Attachable.class.isAssignableFrom(this.baseType.getType()) ? new ArrayList<V>(this.map.values()) : new ArrayList();
    }

    @Override
    final Object get(int index) {
        return new ArrayList<V>(this.map.values()).get(index);
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
    public final DataType<V> getDataType() {
        return this.baseType;
    }

    @Override
    public final DataType<K> getIndexDataType() {
        return this.indexDataType;
    }

    @Override
    public final DataType<?> getArrayType() {
        return this.type;
    }

    public final String toString() {
        return this.create().render(DSL.inline(this));
    }

    @Override
    public final List<V> toList() {
        return Collections.unmodifiableList(new ArrayList<V>(this.values()));
    }

    @Override
    public final int size() {
        return this.map.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public final boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public final boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public final V get(Object key) {
        return this.map.get(key);
    }

    @Override
    public final V put(K key, V value) {
        return this.map.put(key, value);
    }

    @Override
    public final V remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m4) {
        this.map.putAll(m4);
    }

    @Override
    public final void clear() {
        this.map.clear();
    }

    @Override
    public final Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public final Collection<V> values() {
        return this.map.values();
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public final V getOrDefault(Object key, V defaultValue) {
        return this.map.getOrDefault(key, defaultValue);
    }

    @Override
    public final void forEach(BiConsumer<? super K, ? super V> action) {
        this.map.forEach(action);
    }

    @Override
    public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.map.replaceAll(function);
    }

    @Override
    public final V putIfAbsent(K key, V value) {
        return this.map.putIfAbsent(key, value);
    }

    @Override
    public final boolean remove(Object key, Object value) {
        return this.map.remove(key, value);
    }

    @Override
    public final boolean replace(K key, V oldValue, V newValue) {
        return this.map.replace(key, oldValue, newValue);
    }

    @Override
    public final V replace(K key, V value) {
        return this.map.replace(key, value);
    }

    @Override
    public final V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return this.map.computeIfAbsent((K)key, mappingFunction);
    }

    @Override
    public final V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return this.map.computeIfPresent((K)key, (BiFunction<? super K, ? extends V, ? extends V>)remappingFunction);
    }

    @Override
    public final V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return this.map.compute((K)key, (BiFunction<? super K, ? extends V, ? extends V>)remappingFunction);
    }

    @Override
    public final V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return this.map.merge(key, (V)value, (BiFunction<? extends V, ? extends V, ? extends V>)remappingFunction);
    }

    @Override
    public final void format(Writer writer, TXTFormat format) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final void formatHTML(Writer writer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final void formatCSV(Writer writer, CSVFormat format) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final void formatJSON(Writer writer, JSONFormat format) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public final void formatXML(Writer writer, XMLFormat format) {
        throw new UnsupportedOperationException("Not yet implemented");
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

