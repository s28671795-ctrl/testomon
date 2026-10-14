/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Arrays;
import org.simpleframework.xml.core.Label;

class KeyBuilder {
    private final Label label;

    public KeyBuilder(Label label) {
        this.label = label;
    }

    public Object getKey() throws Exception {
        if (this.label.isAttribute()) {
            return this.getKey(KeyType.ATTRIBUTE);
        }
        return this.getKey(KeyType.ELEMENT);
    }

    private Object getKey(KeyType type) throws Exception {
        String[] list = this.label.getPaths();
        String text = this.getKey(list);
        if (type == null) {
            return text;
        }
        return new Key(type, text);
    }

    private String getKey(String[] list) throws Exception {
        StringBuilder builder = new StringBuilder();
        if (list.length > 0) {
            Arrays.sort(list);
            for (String path : list) {
                builder.append(path);
                builder.append('>');
            }
        }
        return builder.toString();
    }

    private static class Key {
        private final KeyType type;
        private final String value;

        public Key(KeyType type, String value) throws Exception {
            this.value = value;
            this.type = type;
        }

        public boolean equals(Object value) {
            if (value instanceof Key) {
                Key key = (Key)value;
                return this.equals(key);
            }
            return false;
        }

        public boolean equals(Key key) {
            if (this.type == key.type) {
                return key.value.equals(this.value);
            }
            return false;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return this.value;
        }
    }

    private static enum KeyType {
        TEXT,
        ATTRIBUTE,
        ELEMENT;

    }
}

