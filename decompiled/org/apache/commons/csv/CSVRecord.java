/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.csv;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.Constants;

public final class CSVRecord
implements Serializable,
Iterable<String> {
    private static final long serialVersionUID = 1L;
    private final long characterPosition;
    private final String comment;
    private final long recordNumber;
    private final String[] values;
    private final transient CSVParser parser;

    CSVRecord(CSVParser parser, String[] values2, String comment, long recordNumber, long characterPosition) {
        this.recordNumber = recordNumber;
        this.values = values2 != null ? values2 : Constants.EMPTY_STRING_ARRAY;
        this.parser = parser;
        this.comment = comment;
        this.characterPosition = characterPosition;
    }

    public String get(Enum<?> e) {
        return this.get(e == null ? null : e.name());
    }

    public String get(int i) {
        return this.values[i];
    }

    public String get(String name) {
        Map<String, Integer> headerMap = this.getHeaderMapRaw();
        if (headerMap == null) {
            throw new IllegalStateException("No header mapping was specified, the record values can't be accessed by name");
        }
        Integer index = headerMap.get(name);
        if (index == null) {
            throw new IllegalArgumentException(String.format("Mapping for %s not found, expected one of %s", name, headerMap.keySet()));
        }
        try {
            return this.values[index];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("Index for header '%s' is %d but CSVRecord only has %d values!", name, index, this.values.length));
        }
    }

    public long getCharacterPosition() {
        return this.characterPosition;
    }

    public String getComment() {
        return this.comment;
    }

    private Map<String, Integer> getHeaderMapRaw() {
        return this.parser == null ? null : this.parser.getHeaderMapRaw();
    }

    public CSVParser getParser() {
        return this.parser;
    }

    public long getRecordNumber() {
        return this.recordNumber;
    }

    public boolean hasComment() {
        return this.comment != null;
    }

    public boolean isConsistent() {
        Map<String, Integer> headerMap = this.getHeaderMapRaw();
        return headerMap == null || headerMap.size() == this.values.length;
    }

    public boolean isMapped(String name) {
        Map<String, Integer> headerMap = this.getHeaderMapRaw();
        return headerMap != null && headerMap.containsKey(name);
    }

    public boolean isSet(int index) {
        return 0 <= index && index < this.values.length;
    }

    public boolean isSet(String name) {
        return this.isMapped(name) && this.getHeaderMapRaw().get(name) < this.values.length;
    }

    @Override
    public Iterator<String> iterator() {
        return this.toList().iterator();
    }

    public <M extends Map<String, String>> M putIn(M map) {
        if (this.getHeaderMapRaw() == null) {
            return map;
        }
        this.getHeaderMapRaw().forEach((? super K key, ? super V value) -> {
            if (value < this.values.length) {
                map.put(key, this.values[value]);
            }
        });
        return map;
    }

    public int size() {
        return this.values.length;
    }

    public Stream<String> stream() {
        return Stream.of(this.values);
    }

    public List<String> toList() {
        return this.stream().collect(Collectors.toList());
    }

    public Map<String, String> toMap() {
        return this.putIn(new LinkedHashMap(this.values.length));
    }

    public String toString() {
        return "CSVRecord [comment='" + this.comment + "', recordNumber=" + this.recordNumber + ", values=" + Arrays.toString(this.values) + "]";
    }

    public String[] values() {
        return this.values;
    }
}

