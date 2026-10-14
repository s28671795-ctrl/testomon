/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.PathException;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Style;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class PathParser
implements Expression {
    protected Cache<String> attributes = new ConcurrentCache<String>();
    protected Cache<String> elements = new ConcurrentCache<String>();
    protected List<Integer> indexes = new ArrayList<Integer>();
    protected List<String> prefixes = new ArrayList<String>();
    protected List<String> names = new ArrayList<String>();
    protected StringBuilder builder = new StringBuilder();
    protected String location;
    protected String cache;
    protected String path;
    protected Style style;
    protected Type type;
    protected boolean attribute;
    protected char[] data;
    protected int count;
    protected int start;
    protected int off;

    public PathParser(String path, Type type, Format format) throws Exception {
        this.style = format.getStyle();
        this.type = type;
        this.path = path;
        this.parse(path);
    }

    @Override
    public boolean isEmpty() {
        return this.isEmpty(this.location);
    }

    @Override
    public boolean isPath() {
        return this.names.size() > 1;
    }

    @Override
    public boolean isAttribute() {
        return this.attribute;
    }

    @Override
    public int getIndex() {
        return this.indexes.get(0);
    }

    @Override
    public String getPrefix() {
        return this.prefixes.get(0);
    }

    @Override
    public String getFirst() {
        return this.names.get(0);
    }

    @Override
    public String getLast() {
        int count = this.names.size();
        int index = count - 1;
        return this.names.get(index);
    }

    @Override
    public String getPath() {
        return this.location;
    }

    @Override
    public String getElement(String name) {
        if (!this.isEmpty(this.location)) {
            String path = this.elements.fetch(name);
            if (path == null && (path = this.getElementPath(this.location, name)) != null) {
                this.elements.cache(name, path);
            }
            return path;
        }
        return this.style.getElement(name);
    }

    protected String getElementPath(String path, String name) {
        String element = this.style.getElement(name);
        if (this.isEmpty(element)) {
            return path;
        }
        if (this.isEmpty(path)) {
            return element;
        }
        return path + "/" + element + "[1]";
    }

    @Override
    public String getAttribute(String name) {
        if (!this.isEmpty(this.location)) {
            String path = this.attributes.fetch(name);
            if (path == null && (path = this.getAttributePath(this.location, name)) != null) {
                this.attributes.cache(name, path);
            }
            return path;
        }
        return this.style.getAttribute(name);
    }

    protected String getAttributePath(String path, String name) {
        String attribute = this.style.getAttribute(name);
        if (this.isEmpty(path)) {
            return attribute;
        }
        return path + "/@" + attribute;
    }

    @Override
    public Iterator<String> iterator() {
        return this.names.iterator();
    }

    @Override
    public Expression getPath(int from) {
        return this.getPath(from, 0);
    }

    @Override
    public Expression getPath(int from, int trim) {
        int last = this.names.size() - 1;
        if (last - trim >= from) {
            return new PathSection(from, last - trim);
        }
        return new PathSection(from, from);
    }

    private void parse(String path) throws Exception {
        if (path != null) {
            this.count = path.length();
            this.data = new char[this.count];
            path.getChars(0, this.count, this.data, 0);
        }
        this.path();
    }

    private void path() throws Exception {
        if (this.data[this.off] == '/') {
            throw new PathException("Path '%s' in %s references document root", this.path, this.type);
        }
        if (this.data[this.off] == '.') {
            this.skip();
        }
        while (this.off < this.count) {
            if (this.attribute) {
                throw new PathException("Path '%s' in %s references an invalid attribute", this.path, this.type);
            }
            this.segment();
        }
        this.truncate();
        this.build();
    }

    private void build() {
        int count = this.names.size();
        int last = count - 1;
        for (int i = 0; i < count; ++i) {
            String prefix = this.prefixes.get(i);
            String segment = this.names.get(i);
            int index = this.indexes.get(i);
            if (i > 0) {
                this.builder.append('/');
            }
            if (this.attribute && i == last) {
                this.builder.append('@');
                this.builder.append(segment);
                continue;
            }
            if (prefix != null) {
                this.builder.append(prefix);
                this.builder.append(':');
            }
            this.builder.append(segment);
            this.builder.append('[');
            this.builder.append(index);
            this.builder.append(']');
        }
        this.location = this.builder.toString();
    }

    private void skip() throws Exception {
        if (this.data.length > 1) {
            if (this.data[this.off + 1] != '/') {
                throw new PathException("Path '%s' in %s has an illegal syntax", this.path, this.type);
            }
            ++this.off;
        }
        this.start = ++this.off;
    }

    private void segment() throws Exception {
        char first = this.data[this.off];
        if (first == '/') {
            throw new PathException("Invalid path expression '%s' in %s", this.path, this.type);
        }
        if (first == '@') {
            this.attribute();
        } else {
            this.element();
        }
        this.align();
    }

    private void element() throws Exception {
        int mark = this.off;
        int size = 0;
        while (this.off < this.count) {
            char value;
            if (!this.isValid(value = this.data[this.off++])) {
                if (value == '@') {
                    --this.off;
                    break;
                }
                if (value == '[') {
                    this.index();
                    break;
                }
                if (value == '/') break;
                throw new PathException("Illegal character '%s' in element for '%s' in %s", Character.valueOf(value), this.path, this.type);
            }
            ++size;
        }
        this.element(mark, size);
    }

    private void attribute() throws Exception {
        int mark = ++this.off;
        while (this.off < this.count) {
            char value;
            if (this.isValid(value = this.data[this.off++])) continue;
            throw new PathException("Illegal character '%s' in attribute for '%s' in %s", Character.valueOf(value), this.path, this.type);
        }
        if (this.off <= mark) {
            throw new PathException("Attribute reference in '%s' for %s is empty", this.path, this.type);
        }
        this.attribute = true;
        this.attribute(mark, this.off - mark);
    }

    private void index() throws Exception {
        int value = 0;
        if (this.data[this.off - 1] == '[') {
            char digit;
            while (this.off < this.count && this.isDigit(digit = this.data[this.off++])) {
                value *= 10;
                value += digit;
                value -= 48;
            }
        }
        if (this.data[this.off++ - 1] != ']') {
            throw new PathException("Invalid index for path '%s' in %s", this.path, this.type);
        }
        this.indexes.add(value);
    }

    private void truncate() throws Exception {
        if (this.off - 1 >= this.data.length) {
            --this.off;
        } else if (this.data[this.off - 1] == '/') {
            --this.off;
        }
    }

    private void align() throws Exception {
        int size;
        int require = this.names.size();
        if (require > (size = this.indexes.size())) {
            this.indexes.add(1);
        }
    }

    private boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    private boolean isDigit(char value) {
        return Character.isDigit(value);
    }

    private boolean isValid(char value) {
        return this.isLetter(value) || this.isSpecial(value);
    }

    private boolean isSpecial(char value) {
        return value == '_' || value == '-' || value == ':';
    }

    private boolean isLetter(char value) {
        return Character.isLetterOrDigit(value);
    }

    private void element(int start2, int count) {
        String segment = new String(this.data, start2, count);
        if (count > 0) {
            this.element(segment);
        }
    }

    private void attribute(int start2, int count) {
        String segment = new String(this.data, start2, count);
        if (count > 0) {
            this.attribute(segment);
        }
    }

    private void element(String segment) {
        int index = segment.indexOf(58);
        String prefix = null;
        if (index > 0) {
            prefix = segment.substring(0, index);
            segment = segment.substring(index + 1);
        }
        String element = this.style.getElement(segment);
        this.prefixes.add(prefix);
        this.names.add(element);
    }

    private void attribute(String segment) {
        String attribute = this.style.getAttribute(segment);
        this.prefixes.add(null);
        this.names.add(attribute);
    }

    @Override
    public String toString() {
        int size = this.off - this.start;
        if (this.cache == null) {
            this.cache = new String(this.data, this.start, size);
        }
        return this.cache;
    }

    private class PathSection
    implements Expression {
        private List<String> cache = new ArrayList<String>();
        private String section;
        private String path;
        private int begin;
        private int end;

        public PathSection(int index, int end) {
            this.begin = index;
            this.end = end;
        }

        @Override
        public boolean isEmpty() {
            return this.begin == this.end;
        }

        @Override
        public boolean isPath() {
            return this.end - this.begin >= 1;
        }

        @Override
        public boolean isAttribute() {
            if (PathParser.this.attribute) {
                return this.end >= PathParser.this.names.size() - 1;
            }
            return false;
        }

        @Override
        public String getPath() {
            if (this.section == null) {
                this.section = this.getCanonicalPath();
            }
            return this.section;
        }

        @Override
        public String getElement(String name) {
            String path = this.getPath();
            if (path != null) {
                return PathParser.this.getElementPath(path, name);
            }
            return name;
        }

        @Override
        public String getAttribute(String name) {
            String path = this.getPath();
            if (path != null) {
                return PathParser.this.getAttributePath(path, name);
            }
            return name;
        }

        @Override
        public int getIndex() {
            return PathParser.this.indexes.get(this.begin);
        }

        @Override
        public String getPrefix() {
            return PathParser.this.prefixes.get(this.begin);
        }

        @Override
        public String getFirst() {
            return PathParser.this.names.get(this.begin);
        }

        @Override
        public String getLast() {
            return PathParser.this.names.get(this.end);
        }

        @Override
        public Expression getPath(int from) {
            return this.getPath(from, 0);
        }

        @Override
        public Expression getPath(int from, int trim) {
            return new PathSection(this.begin + from, this.end - trim);
        }

        @Override
        public Iterator<String> iterator() {
            if (this.cache.isEmpty()) {
                for (int i = this.begin; i <= this.end; ++i) {
                    String segment = PathParser.this.names.get(i);
                    if (segment == null) continue;
                    this.cache.add(segment);
                }
            }
            return this.cache.iterator();
        }

        private String getCanonicalPath() {
            int start2 = 0;
            int last = 0;
            int pos = 0;
            for (pos = 0; pos < this.begin; ++pos) {
                start2 = PathParser.this.location.indexOf(47, start2 + 1);
            }
            last = start2;
            while (pos <= this.end) {
                if ((last = PathParser.this.location.indexOf(47, last + 1)) == -1) {
                    last = PathParser.this.location.length();
                }
                ++pos;
            }
            return PathParser.this.location.substring(start2 + 1, last);
        }

        private String getFragment() {
            int last = PathParser.this.start;
            int pos = 0;
            int i = 0;
            while (i <= this.end) {
                if (last >= PathParser.this.count) {
                    ++last;
                    break;
                }
                if (PathParser.this.data[last++] != '/' || ++i != this.begin) continue;
                pos = last;
            }
            return new String(PathParser.this.data, pos, --last - pos);
        }

        @Override
        public String toString() {
            if (this.path == null) {
                this.path = this.getFragment();
            }
            return this.path;
        }
    }
}

