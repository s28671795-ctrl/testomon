/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import java.lang.annotation.Annotation;
import org.simpleframework.xml.convert.Scanner;
import org.simpleframework.xml.util.ConcurrentCache;

class ScannerBuilder
extends ConcurrentCache<Scanner> {
    public Scanner build(Class<?> type) {
        Scanner scanner = (Scanner)this.get(type);
        if (scanner == null) {
            scanner = new Entry(type);
            this.put(type, scanner);
        }
        return scanner;
    }

    private static class Entry
    extends ConcurrentCache<Annotation>
    implements Scanner {
        private final Class root;

        public Entry(Class root) {
            this.root = root;
        }

        @Override
        public <T extends Annotation> T scan(Class<T> type) {
            if (!this.contains(type)) {
                T value = this.find(type);
                if (type != null && value != null) {
                    this.put(type, value);
                }
            }
            return (T)((Annotation)this.get(type));
        }

        private <T extends Annotation> T find(Class<T> label) {
            for (Class type = this.root; type != null; type = type.getSuperclass()) {
                T value = type.getAnnotation(label);
                if (value == null) continue;
                return value;
            }
            return null;
        }
    }
}

