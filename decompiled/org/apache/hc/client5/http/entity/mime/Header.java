/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.entity.mime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.hc.client5.http.entity.mime.MimeField;

public class Header
implements Iterable<MimeField> {
    private final List<MimeField> fields = new LinkedList<MimeField>();
    private final Map<String, List<MimeField>> fieldMap = new HashMap<String, List<MimeField>>();

    public void addField(MimeField field2) {
        if (field2 == null) {
            return;
        }
        String key = field2.getName().toLowerCase(Locale.ROOT);
        List values2 = this.fieldMap.computeIfAbsent(key, k -> new LinkedList());
        values2.add(field2);
        this.fields.add(field2);
    }

    public List<MimeField> getFields() {
        return new ArrayList<MimeField>(this.fields);
    }

    public MimeField getField(String name) {
        if (name == null) {
            return null;
        }
        String key = name.toLowerCase(Locale.ROOT);
        List<MimeField> list = this.fieldMap.get(key);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<MimeField> getFields(String name) {
        if (name == null) {
            return null;
        }
        String key = name.toLowerCase(Locale.ROOT);
        List<MimeField> list = this.fieldMap.get(key);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<MimeField>(list);
    }

    public int removeFields(String name) {
        if (name == null) {
            return 0;
        }
        String key = name.toLowerCase(Locale.ROOT);
        List<MimeField> removed = this.fieldMap.remove(key);
        if (removed == null || removed.isEmpty()) {
            return 0;
        }
        this.fields.removeAll(removed);
        return removed.size();
    }

    public void setField(MimeField field2) {
        if (field2 == null) {
            return;
        }
        String key = field2.getName().toLowerCase(Locale.ROOT);
        List<MimeField> list = this.fieldMap.get(key);
        if (list == null || list.isEmpty()) {
            this.addField(field2);
            return;
        }
        list.clear();
        list.add(field2);
        int firstOccurrence = -1;
        int index = 0;
        Iterator<MimeField> it = this.fields.iterator();
        while (it.hasNext()) {
            MimeField f = it.next();
            if (f.getName().equalsIgnoreCase(field2.getName())) {
                it.remove();
                if (firstOccurrence == -1) {
                    firstOccurrence = index;
                }
            }
            ++index;
        }
        this.fields.add(firstOccurrence, field2);
    }

    @Override
    public Iterator<MimeField> iterator() {
        return Collections.unmodifiableList(this.fields).iterator();
    }

    public String toString() {
        return this.fields.toString();
    }
}

