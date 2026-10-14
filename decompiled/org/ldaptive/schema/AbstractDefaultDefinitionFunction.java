/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.ldaptive.schema.DefinitionFunction;
import org.ldaptive.schema.SchemaElement;
import org.ldaptive.schema.SchemaParseException;
import org.ldaptive.schema.SchemaUtils;

public abstract class AbstractDefaultDefinitionFunction<T extends SchemaElement>
implements DefinitionFunction<T> {
    protected CharBuffer validate(String definition) throws SchemaParseException {
        if (definition == null || definition.isEmpty()) {
            throw new SchemaParseException("Definition cannot be null or empty");
        }
        CharBuffer buffer = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(definition.getBytes()));
        if (buffer.get() != '(') {
            throw new SchemaParseException("Definition '" + definition + "' must start with '('");
        }
        if (buffer.get(buffer.limit() - 1) != ')') {
            throw new SchemaParseException("Definition '" + definition + "' must end with ')'");
        }
        if (!buffer.hasRemaining()) {
            throw new SchemaParseException("Definition '" + definition + "' does not contain an expression");
        }
        if (!(buffer = buffer.limit(buffer.limit() - 1).slice()).hasRemaining()) {
            throw new SchemaParseException("Definition '" + definition + "' does not contain an expression");
        }
        return buffer;
    }

    protected String readOID(CharBuffer cb) {
        String oid = this.readUntilSpace(cb);
        return oid;
    }

    protected String[] readOIDs(CharBuffer cb) {
        if (!cb.hasRemaining()) {
            throw new IllegalArgumentException("Cannot read oids from empty buffer");
        }
        char c = cb.get();
        if (c != '(') {
            return new String[]{this.readOID(cb.position(cb.position() - 1))};
        }
        if (!cb.hasRemaining()) {
            throw new IllegalArgumentException("Cannot read oids with empty content");
        }
        int startPos = cb.position();
        int limit = cb.limit();
        c = this.readUntil(cb, ')');
        if (c != ')') {
            throw new IllegalArgumentException("oids must end with a close paren");
        }
        int endPos = cb.position() - 1;
        CharBuffer slice = cb.limit(endPos).position(startPos).slice();
        cb.limit(limit).position(endPos + 1);
        String[] oids = SchemaUtils.parseOIDs(slice.toString().trim());
        if (oids.length == 0) {
            throw new IllegalArgumentException("oids cannot be empty");
        }
        return oids;
    }

    protected int readRuleID(CharBuffer cb) {
        String id = this.readUntilSpace(cb);
        return Integer.parseInt(id);
    }

    protected int[] readRuleIDs(CharBuffer cb) {
        if (!cb.hasRemaining()) {
            throw new IllegalArgumentException("Cannot read ruleids from empty buffer");
        }
        char c = cb.get();
        if (c != '(') {
            return new int[]{this.readRuleID(cb.position(cb.position() - 1))};
        }
        if (!cb.hasRemaining()) {
            throw new IllegalArgumentException("Cannot read ruleids with empty content");
        }
        int startPos = cb.position();
        int limit = cb.limit();
        c = this.readUntil(cb, ')');
        if (c != ')') {
            throw new IllegalArgumentException("ruleids must end with a close paren");
        }
        int endPos = cb.position() - 1;
        CharBuffer slice = cb.limit(endPos).position(startPos).slice();
        cb.limit(limit).position(endPos + 1);
        int[] ids = SchemaUtils.parseNumbers(slice.toString().trim());
        if (ids.length == 0) {
            throw new IllegalArgumentException("ruleids cannot be empty");
        }
        return ids;
    }

    protected String readQDString(CharBuffer cb) {
        if (!cb.hasRemaining()) {
            throw new IllegalArgumentException("Cannot read qdstring from empty buffer");
        }
        char c = cb.get();
        if (c != '\'') {
            throw new IllegalArgumentException("qdstring must start with a single quote");
        }
        if (!cb.hasRemaining()) {
            throw new IllegalArgumentException("Cannot read qdstring with empty content");
        }
        int startPos = cb.position();
        int limit = cb.limit();
        c = this.readUntil(cb, '\'');
        if (c != '\'') {
            throw new IllegalArgumentException("qdstring must end with a single quote");
        }
        int endPos = cb.position() - 1;
        CharBuffer slice = cb.limit(endPos).position(startPos).slice();
        cb.limit(limit).position(endPos + 1);
        return slice.toString();
    }

    protected String[] readQDStrings(CharBuffer cb) {
        if (!cb.hasRemaining()) {
            throw new IllegalArgumentException("Cannot read qdstrings from empty buffer");
        }
        char c = cb.get();
        if (c == '\'') {
            return new String[]{this.readQDString(cb.position(cb.position() - 1))};
        }
        if (c != '(') {
            throw new IllegalArgumentException("qdstrings must start with a single quote or an open paren");
        }
        if (!cb.hasRemaining()) {
            throw new IllegalArgumentException("Cannot read qdstrings with empty content");
        }
        ArrayList<String> values2 = new ArrayList<String>();
        int limit = cb.limit();
        while (cb.hasRemaining() && (c = cb.get()) != ')') {
            if (c != '\'') continue;
            int startValue = cb.position();
            c = this.readUntil(cb, '\'');
            if (c != '\'') {
                throw new IllegalArgumentException("qdstring must end with a single quote");
            }
            int endPos = cb.position() - 1;
            CharBuffer slice = cb.limit(endPos).position(startValue).slice();
            cb.limit(limit).position(endPos + 1);
            values2.add(slice.toString());
        }
        if (c != ')') {
            throw new IllegalArgumentException("qdstrings must end with a close paren");
        }
        if (values2.isEmpty()) {
            throw new IllegalArgumentException("qdstrings cannot be empty");
        }
        return values2.toArray(new String[0]);
    }

    protected String readUntilSpace(CharBuffer cb) {
        if (!cb.hasRemaining()) {
            return "";
        }
        int startPos = cb.position();
        int limit = cb.limit();
        this.readUntil(cb, ' ');
        int endPos = cb.position() - 1;
        CharBuffer slice = cb.limit(endPos).position(startPos).slice();
        cb.limit(limit).position(endPos);
        return slice.toString();
    }

    protected void skipSpaces(CharBuffer cb) {
        if (!cb.hasRemaining()) {
            return;
        }
        while (cb.hasRemaining() && cb.get() == ' ') {
        }
        if (!cb.hasRemaining()) {
            return;
        }
        cb.position(cb.position() - 1);
    }

    private char readUntil(CharBuffer cb, char c) {
        char bufferChar = '\u0000';
        while (cb.hasRemaining() && (bufferChar = cb.get()) != c) {
        }
        return bufferChar;
    }
}

