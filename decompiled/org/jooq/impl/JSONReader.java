/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;
import org.jooq.tools.json.ContainerFactory;
import org.jooq.tools.json.JSONParser;

final class JSONReader<R extends Record> {
    private final DSLContext ctx;
    private final AbstractRow<R> row;
    private final Class<? extends R> recordType;
    private final boolean multiset;
    private static final Set<SQLDialect> ENCODE_BINARY_AS_HEX = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> ENCODE_BINARY_AS_TEXT = SQLDialect.supportedBy(SQLDialect.MARIADB);

    JSONReader(DSLContext ctx, AbstractRow<R> row, Class<? extends R> recordType, boolean multiset) {
        this.ctx = ctx;
        this.row = row;
        this.recordType = recordType != null ? recordType : Record.class;
        this.multiset = multiset;
    }

    final Result<R> read(String string) {
        return this.read(new StringReader(string));
    }

    final Result<R> read(Reader reader) {
        return this.read(reader, false);
    }

    final Result<R> read(Reader reader, boolean multiset) {
        try {
            Object root = new JSONParser().parse(reader, new ContainerFactory(){

                @Override
                public Map createObjectContainer() {
                    return new LinkedHashMap();
                }

                @Override
                public List createArrayContainer() {
                    return new ArrayList();
                }
            });
            return JSONReader.read(this.ctx, this.row, this.recordType, multiset, root);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final <R extends Record> Result<R> read(DSLContext ctx, AbstractRow<R> actualRow, Class<? extends R> recordType, boolean multiset, Object root) {
        List records;
        ArrayList header = new ArrayList();
        ResultImpl result = null;
        if (root instanceof Map) {
            Map o1 = (Map)root;
            List fields = (List)o1.get("fields");
            if (fields != null) {
                for (Object field2 : fields) {
                    String catalog = (String)field2.get("catalog");
                    String schema = (String)field2.get("schema");
                    String table = (String)field2.get("table");
                    String name = (String)field2.get("name");
                    String type = (String)field2.get("type");
                    header.add(DSL.field(DSL.name(catalog, schema, table, name), DefaultDataType.getDataType(null, StringUtils.defaultIfBlank(type, "VARCHAR"))));
                }
            }
            records = (List)o1.get("records");
        } else {
            records = (List)root;
        }
        if (actualRow == null && !header.isEmpty()) {
            actualRow = Tools.row0(header);
        }
        if (actualRow != null) {
            result = new ResultImpl(ctx.configuration(), (AbstractRow)actualRow);
        }
        if (records != null) {
            for (Object o3 : records) {
                Object record;
                if (o3 instanceof Map) {
                    record = (Map)o3;
                    if (result == null) {
                        if (header.isEmpty()) {
                            for (String name : record.keySet()) {
                                header.add(DSL.field(DSL.name(name), SQLDataType.VARCHAR));
                            }
                        }
                        actualRow = Tools.row0(header);
                        result = new ResultImpl(ctx.configuration(), (AbstractRow)actualRow);
                    }
                    List<Object> list = multiset ? JSONReader.patchRecord(ctx, multiset, actualRow, JSONReader.sortedValues((Map<String, Object>)record)) : null;
                    result.add(Tools.newRecord(true, recordType, actualRow, ctx.configuration()).operate(arg_0 -> JSONReader.lambda$read$0(multiset, list, (Map)record, arg_0)));
                    continue;
                }
                record = (List)o3;
                if (result == null) {
                    if (header.isEmpty()) {
                        header.addAll(Arrays.asList(Tools.fields(record.size())));
                    }
                    actualRow = Tools.row0(header);
                    result = new ResultImpl(ctx.configuration(), (AbstractRow)actualRow);
                }
                JSONReader.patchRecord(ctx, multiset, actualRow, (List<Object>)record);
                if (record == null) {
                    result.add(null);
                    continue;
                }
                result.add(Tools.newRecord(true, recordType, actualRow, ctx.configuration()).operate(arg_0 -> JSONReader.lambda$read$1((List)record, arg_0)));
            }
        }
        return result;
    }

    private static final List<Object> sortedValues(Map<String, Object> record) {
        List<Object> result = Arrays.asList(new Object[record.size()]);
        for (Map.Entry<String, Object> e : record.entrySet()) {
            result.set(Integer.parseInt(e.getKey().substring(1)), e.getValue());
        }
        return result;
    }

    private static final List<Object> patchRecord(DSLContext ctx, boolean multiset, Fields result, List<Object> record) {
        for (int i = 0; i < result.fields().length; ++i) {
            Field<?> field2 = result.field(i);
            if (field2.getType() == byte[].class && record.get(i) instanceof String) {
                String s2 = (String)record.get(i);
                if (multiset) {
                    if (ENCODE_BINARY_AS_HEX.contains((Object)ctx.dialect())) {
                        if (s2.startsWith("\\x")) {
                            record.set(i, Tools.convertHexToBytes(s2, 1, Integer.MAX_VALUE));
                            continue;
                        }
                        record.set(i, Tools.convertHexToBytes(s2));
                        continue;
                    }
                    if (ENCODE_BINARY_AS_TEXT.contains((Object)ctx.dialect())) {
                        record.set(i, s2);
                        continue;
                    }
                    if (s2.startsWith("base64:type15:")) {
                        record.set(i, Base64.getDecoder().decode(s2.substring(14)));
                        continue;
                    }
                    record.set(i, Base64.getDecoder().decode(s2));
                    continue;
                }
                record.set(i, Base64.getDecoder().decode(s2));
                continue;
            }
            if (multiset && field2.getDataType().isMultiset()) {
                record.set(i, JSONReader.read(ctx, (AbstractRow)field2.getDataType().getRow(), field2.getDataType().getRecordType(), multiset, record.get(i)));
                continue;
            }
            if (!multiset || !field2.getDataType().isRecord() || !(record.get(i) instanceof List)) continue;
            AbstractRow actualRow = (AbstractRow)field2.getDataType().getRow();
            Class<Record> recordType = field2.getDataType().getRecordType();
            List l = (List)record.get(i);
            JSONReader.patchRecord(ctx, multiset, actualRow, l);
            record.set(i, Tools.newRecord(true, recordType, actualRow, ctx.configuration()).operate(r -> {
                r.from(l);
                r.changed(false);
                return r;
            }));
        }
        return record;
    }

    private static /* synthetic */ Record lambda$read$1(List record, Record r) throws RuntimeException {
        r.from(record);
        r.changed(false);
        return r;
    }

    private static /* synthetic */ Record lambda$read$0(boolean multiset, List list, Map record, Record r) throws RuntimeException {
        if (multiset) {
            r.from(list);
        } else {
            r.fromMap(record);
        }
        r.changed(false);
        return r;
    }
}

