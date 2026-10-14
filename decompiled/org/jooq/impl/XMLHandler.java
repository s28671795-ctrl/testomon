/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Deque;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.helpers.DefaultHandler;

final class XMLHandler<R extends Record>
extends DefaultHandler {
    private static final JooqLogger log = JooqLogger.getLogger(XMLHandler.class);
    private static final boolean debug = false;
    private final DSLContext ctx;
    private final Deque<State<R>> states;
    private State<R> s;

    XMLHandler(DSLContext ctx, AbstractRow<R> row, Class<? extends R> recordType) {
        this.ctx = ctx;
        this.states = new ArrayDeque<State<R>>();
        this.s = new State<R>(ctx, row, recordType);
    }

    Result<R> read(String string) {
        try {
            SAXParserFactory factory2 = SAXParserFactory.newInstance();
            try {
                factory2.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            }
            catch (ParserConfigurationException | SAXNotRecognizedException exception) {
                // empty catch block
            }
            try {
                factory2.setFeature("http://xml.org/sax/features/external-general-entities", false);
            }
            catch (ParserConfigurationException | SAXNotRecognizedException exception) {
                // empty catch block
            }
            try {
                factory2.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            }
            catch (ParserConfigurationException | SAXNotRecognizedException exception) {
                // empty catch block
            }
            try {
                factory2.setXIncludeAware(false);
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                // empty catch block
            }
            SAXParser saxParser = factory2.newSAXParser();
            saxParser.parse((InputStream)new ByteArrayInputStream(string.getBytes(this.ctx.configuration().charsetProvider().provide())), (DefaultHandler)this);
            return this.s.result;
        }
        catch (Exception e) {
            throw new DataAccessException("Could not read the XML string", e);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!this.s.inResult && "result".equalsIgnoreCase(qName)) {
            this.s.inResult = true;
            return;
        } else if (this.s.inColumn && "result".equalsIgnoreCase(qName)) {
            Field<?> f = this.s.row.field(this.s.column);
            if (!f.getDataType().isMultiset()) throw new UnsupportedOperationException("Nested result sets not supported yet");
            this.states.push(this.s);
            this.s = new State<Record>(this.ctx, (AbstractRow)f.getDataType().getRow(), f.getDataType().getRecordType());
            this.s.inResult = true;
            return;
        } else if (this.s.inResult && "fields".equalsIgnoreCase(qName)) {
            this.s.inFields = true;
            return;
        } else if (this.s.inResult && this.s.inFields && "field".equalsIgnoreCase(qName)) {
            String catalog = attributes.getValue("catalog");
            String schema = attributes.getValue("schema");
            String table = attributes.getValue("table");
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            this.s.fields.add(DSL.field(DSL.name(catalog, schema, table, name), DefaultDataType.getDataType(null, StringUtils.defaultIfBlank(type, "VARCHAR"))));
            return;
        } else {
            if (this.s.inResult && "records".equalsIgnoreCase(qName)) return;
            if (this.s.inResult && "record".equalsIgnoreCase(qName)) {
                ++this.s.inRecord;
                if (!this.s.inColumn) return;
                Field<?> f = this.s.row.field(this.s.column);
                if (!f.getDataType().isRecord()) throw new UnsupportedOperationException("Nested records not supported yet");
                this.states.push(this.s);
                this.s = new State<Record>(this.ctx, (AbstractRow)f.getDataType().getRow(), f.getDataType().getRecordType());
                this.s.inResult = true;
                return;
            } else if (this.s.inColumn && "element".equalsIgnoreCase(qName) && this.s.elements != null) {
                this.s.inElement = true;
                return;
            } else {
                if (this.s.result == null) {
                    String fieldName;
                    if (!"value".equalsIgnoreCase(qName) || (fieldName = attributes.getValue("field")) == null) {
                        fieldName = qName;
                    }
                    if (this.s.row != null) {
                        this.s.fields.add(this.s.row.field(this.s.fields.size()));
                    } else {
                        this.s.fields.add(DSL.field(DSL.name(fieldName), SQLDataType.VARCHAR));
                    }
                }
                this.s.inColumn = true;
                DataType t2 = this.s.fields.get(this.s.column).getDataType();
                if (t2.isString() && !this.isNil(attributes)) {
                    this.s.values.add("");
                    return;
                } else if (t2.isArray() && !this.isNil(attributes)) {
                    this.s.elements = new ArrayList<Object>();
                    return;
                } else {
                    if (t2.isMultiset() || t2.isRecord()) return;
                    this.s.values.add(null);
                }
            }
        }
    }

    private final boolean isNil(Attributes attributes) {
        return "true".equals(attributes.getValue("xsi:nil")) || "true".equals(attributes.getValue("nil"));
    }

    @Override
    public final void endElement(String uri, String localName, String qName) throws SAXException {
        block3: {
            block9: {
                block7: {
                    Field<?> f;
                    State<R> peek;
                    block8: {
                        block6: {
                            block5: {
                                block4: {
                                    block2: {
                                        if (!this.states.isEmpty() || !this.s.inResult || this.s.inRecord != 0 || !"result".equalsIgnoreCase(qName)) break block2;
                                        if (this.s.result == null) {
                                            this.initResult();
                                        }
                                        this.s.inResult = false;
                                        break block3;
                                    }
                                    if (!this.s.inResult || !this.s.inFields || !"fields".equalsIgnoreCase(qName)) break block4;
                                    this.s.inFields = false;
                                    this.initResult();
                                    break block3;
                                }
                                if (this.s.inResult && this.s.inFields && "field".equalsIgnoreCase(qName) || this.s.inResult && "records".equalsIgnoreCase(qName)) break block3;
                                if (this.s.inRecord <= 0 || !"record".equalsIgnoreCase(qName)) break block5;
                                --this.s.inRecord;
                                this.initResult();
                                this.s.result.add(Tools.newRecord(true, this.s.recordType, this.s.row, this.ctx.configuration()).operate(this.s::into));
                                this.s.values.clear();
                                this.s.column = 0;
                                break block3;
                            }
                            if (!this.s.inColumn || !"element".equalsIgnoreCase(qName) || this.s.elements == null) break block6;
                            this.s.inElement = false;
                            this.s.elements.add(this.s.values.get(this.s.column));
                            this.s.values.remove(this.s.column);
                            break block3;
                        }
                        if (this.states.isEmpty()) break block7;
                        peek = this.states.peek();
                        f = peek.row.field(peek.column);
                        if (!"record".equalsIgnoreCase(qName) || !f.getDataType().isRecord()) break block8;
                        peek.values.add(Tools.newRecord(true, this.s.recordType, this.s.row, this.ctx.configuration()).operate(this.s::into));
                        this.s = this.states.pop();
                        break block3;
                    }
                    if (!"result".equalsIgnoreCase(qName) || !f.getDataType().isMultiset()) break block9;
                    this.initResult();
                    peek.values.add(this.s.result);
                    this.s = this.states.pop();
                    break block3;
                }
                if (this.s.elements != null) {
                    this.s.values.add(this.s.elements);
                    this.s.elements = null;
                }
            }
            this.s.inColumn = false;
            ++this.s.column;
        }
    }

    private void initResult() {
        if (this.s.result == null) {
            if (this.s.row == null) {
                this.s.row = XMLHandler.onlyValueFields(this.s.fields) ? Tools.row0(Tools.fields(this.s.fields.size())) : Tools.row0(this.s.fields.toArray(Tools.EMPTY_FIELD));
            }
            this.s.result = new ResultImpl(this.ctx.configuration(), this.s.row);
        }
    }

    private static boolean onlyValueFields(List<Field<?>> fields) {
        if (fields.size() <= 1) {
            return false;
        }
        return Tools.allMatch(fields, f -> "value".equalsIgnoreCase(f.getName()));
    }

    @Override
    public final void characters(char[] ch, int start2, int length) throws SAXException {
        DataType t2;
        if (!(!this.s.inColumn || (t2 = this.s.fields.get(this.s.column).getDataType()).isRecord() || t2.isMultiset() || t2.isArray() && !this.s.inElement)) {
            String value = new String(ch, start2, length);
            if (this.s.values.size() == this.s.column) {
                this.s.values.add(value);
            } else {
                Object old = this.s.values.get(this.s.column);
                if (old == null) {
                    this.s.values.set(this.s.column, value);
                } else {
                    this.s.values.set(this.s.column, String.valueOf(old) + value);
                }
            }
        }
    }

    private static class State<R extends Record> {
        final DSLContext ctx;
        AbstractRow<R> row;
        final Class<? extends R> recordType;
        boolean inResult;
        boolean inFields;
        int inRecord;
        boolean inColumn;
        boolean inElement;
        Result<R> result;
        final List<Field<?>> fields;
        final List<Object> values;
        List<Object> elements;
        int column;

        State(DSLContext ctx, AbstractRow<R> row, Class<? extends R> recordType) {
            this.ctx = ctx;
            this.row = row;
            this.recordType = recordType != null ? recordType : Record.class;
            this.fields = new ArrayList();
            this.values = new ArrayList<Object>();
        }

        final R into(R r) {
            for (int i = 0; i < this.fields.size(); ++i) {
                Object object;
                if (!this.fields.get(i).getDataType().isBinary() || !((object = this.values.get(i)) instanceof String)) continue;
                String s2 = (String)object;
                if (this.ctx.family() == SQLDialect.ORACLE) {
                    this.values.set(i, Tools.convertHexToBytes(s2));
                    continue;
                }
                this.values.set(i, Base64.getDecoder().decode(s2));
            }
            r.from(this.values);
            r.changed(false);
            return r;
        }
    }
}

