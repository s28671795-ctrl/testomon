/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util;

import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.eclipse.jetty.util.AbstractTrie;
import org.eclipse.jetty.util.MathUtils;
import org.eclipse.jetty.util.StringUtil;

@Deprecated
class ArrayTernaryTrie<V>
extends AbstractTrie<V> {
    private static final int LO = 1;
    private static final int EQ = 2;
    private static final int HI = 3;
    private static final int ROW_SIZE = 4;
    private static final int MAX_CAPACITY = 65534;
    private final char[] _tree;
    private final String[] _key;
    private final V[] _value;
    private char _rows;

    ArrayTernaryTrie(boolean caseSensitive, int capacity) {
        super(caseSensitive);
        if (capacity > 65534) {
            throw new IllegalArgumentException("ArrayTernaryTrie maximum capacity overflow (" + capacity + " > 65534)");
        }
        this._value = new Object[capacity + 1];
        this._tree = new char[(capacity + 1) * 4];
        this._key = new String[capacity + 1];
    }

    @Override
    public void clear() {
        this._rows = '\u0000';
        Arrays.fill(this._value, null);
        Arrays.fill(this._tree, '\u0000');
        Arrays.fill(this._key, null);
    }

    @Override
    public boolean put(String s2, V v) {
        char t2 = '\u0000';
        int limit = s2.length();
        if (limit > 65534) {
            return false;
        }
        int last = 0;
        for (int k = 0; k < limit; ++k) {
            int diff;
            char c = s2.charAt(k);
            if (this.isCaseInsensitive() && c < '\u0080') {
                c = StringUtil.asciiToLowerCase(c);
            }
            do {
                char n;
                if (this._rows == '\ufffe') {
                    return false;
                }
                int row = 4 * t2;
                if (t2 == this._rows) {
                    this._rows = (char)MathUtils.cappedAdd(this._rows, 1, this._key.length);
                    if (this._rows == this._key.length) {
                        return false;
                    }
                    this._tree[row] = c;
                }
                if ((diff = (n = this._tree[row]) - c) == 0) {
                    last = row + 2;
                    t2 = this._tree[last];
                } else if (diff < 0) {
                    last = row + 1;
                    t2 = this._tree[last];
                } else {
                    last = row + 3;
                    t2 = this._tree[last];
                }
                if (t2 != '\u0000') continue;
                t2 = this._rows;
                this._tree[last] = t2;
            } while (diff != 0);
        }
        if (t2 == this._rows) {
            if (this._rows == this._key.length) {
                return false;
            }
            this._rows = (char)(this._rows + '\u0001');
        }
        this._key[t2] = v == null ? null : s2;
        this._value[t2] = v;
        return true;
    }

    @Override
    public V get(String s2, int offset, int len) {
        int t2 = 0;
        int i = 0;
        block0: while (i < len) {
            int diff;
            int row;
            char c = s2.charAt(offset + i++);
            if (this.isCaseInsensitive() && c < '\u0080') {
                c = StringUtil.asciiToLowerCase(c);
            }
            do {
                char n;
                if ((diff = (n = this._tree[row = 4 * t2]) - c) != 0) continue;
                t2 = this._tree[row + 2];
                if (t2 != 0) continue block0;
                return null;
            } while ((t2 = this._tree[row + ArrayTernaryTrie.hilo(diff)]) != 0);
            return null;
        }
        return this._value[t2];
    }

    @Override
    public V get(ByteBuffer b, int offset, int len) {
        int t2 = 0;
        offset += b.position();
        int i = 0;
        block0: while (i < len) {
            int diff;
            int row;
            byte c = (byte)(b.get(offset + i++) & 0x7F);
            if (this.isCaseInsensitive()) {
                c = StringUtil.asciiToLowerCase(c);
            }
            do {
                char n;
                if ((diff = (n = this._tree[row = 4 * t2]) - c) != 0) continue;
                t2 = this._tree[row + 2];
                if (t2 != 0) continue block0;
                return null;
            } while ((t2 = this._tree[row + ArrayTernaryTrie.hilo(diff)]) != 0);
            return null;
        }
        return this._value[t2];
    }

    @Override
    public V getBest(String s2) {
        return this.getBest(0, s2, 0, s2.length());
    }

    @Override
    public V getBest(String s2, int offset, int length) {
        return this.getBest(0, s2, offset, length);
    }

    private V getBest(int t2, String s2, int offset, int len) {
        int node = t2;
        int end = offset + len;
        block0: while (offset < end) {
            int diff;
            int row;
            char c = s2.charAt(offset++);
            --len;
            if (this.isCaseInsensitive() && c < '\u0080') {
                c = StringUtil.asciiToLowerCase(c);
            }
            do {
                char n;
                if ((diff = (n = this._tree[row = 4 * t2]) - c) != 0) continue;
                t2 = this._tree[row + 2];
                if (t2 == 0) break block0;
                if (this._key[t2] == null) continue block0;
                node = t2;
                V better = this.getBest(t2, s2, offset, len);
                if (better == null) continue block0;
                return better;
            } while ((t2 = this._tree[row + ArrayTernaryTrie.hilo(diff)]) != 0);
            break;
        }
        return this._value[node];
    }

    @Override
    public V getBest(ByteBuffer b, int offset, int len) {
        if (b.hasArray()) {
            return this.getBest(0, b.array(), b.arrayOffset() + b.position() + offset, len);
        }
        return this.getBest(0, b, offset, len);
    }

    @Override
    public V getBest(byte[] b, int offset, int len) {
        return this.getBest(0, b, offset, len);
    }

    private V getBest(int t2, byte[] b, int offset, int len) {
        int node = t2;
        int end = offset + len;
        block0: while (offset < end) {
            int diff;
            int row;
            byte c = (byte)(b[offset++] & 0x7F);
            --len;
            if (this.isCaseInsensitive()) {
                c = StringUtil.asciiToLowerCase(c);
            }
            do {
                char n;
                if ((diff = (n = this._tree[row = 4 * t2]) - c) != 0) continue;
                t2 = this._tree[row + 2];
                if (t2 == 0) break block0;
                if (this._key[t2] == null) continue block0;
                node = t2;
                V better = this.getBest(t2, b, offset, len);
                if (better == null) continue block0;
                return better;
            } while ((t2 = this._tree[row + ArrayTernaryTrie.hilo(diff)]) != 0);
            break;
        }
        return this._value[node];
    }

    private V getBest(int t2, ByteBuffer b, int offset, int len) {
        int node = t2;
        int o = offset + b.position();
        block0: for (int i = 0; i < len; ++i) {
            int diff;
            int row;
            if (o + i >= b.limit()) {
                return null;
            }
            byte c = (byte)(b.get(o + i) & 0x7F);
            if (this.isCaseInsensitive()) {
                c = StringUtil.asciiToLowerCase(c);
            }
            do {
                char n;
                if ((diff = (n = this._tree[row = 4 * t2]) - c) != 0) continue;
                t2 = this._tree[row + 2];
                if (t2 == 0) break block0;
                if (this._key[t2] == null) continue block0;
                node = t2;
                V best = this.getBest(t2, b, offset + i + 1, len - i - 1);
                if (best == null) continue block0;
                return best;
            } while ((t2 = this._tree[row + ArrayTernaryTrie.hilo(diff)]) != 0);
            break;
        }
        return this._value[node];
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("ATT@").append(Integer.toHexString(this.hashCode())).append('{');
        buf.append("ci=").append(this.isCaseInsensitive()).append(';');
        buf.append("c=").append(this._tree.length / 4).append(';');
        for (int r = 0; r <= this._rows; ++r) {
            if (this._key[r] == null || this._value[r] == null) continue;
            if (r != 0) {
                buf.append(',');
            }
            buf.append(this._key[r]);
            buf.append('=');
            buf.append(this._value[r]);
        }
        buf.append('}');
        return buf.toString();
    }

    @Override
    public Set<String> keySet() {
        HashSet<String> keys2 = new HashSet<String>();
        for (int r = 0; r < this._rows; ++r) {
            if (this._key[r] == null || this._value[r] == null) continue;
            keys2.add(this._key[r]);
        }
        return keys2;
    }

    @Override
    public int size() {
        int s2 = 0;
        for (int r = 0; r < this._rows; ++r) {
            if (this._key[r] == null || this._value[r] == null) continue;
            ++s2;
        }
        return s2;
    }

    @Override
    public boolean isEmpty() {
        for (int r = 0; r < this._rows; ++r) {
            if (this._key[r] == null || this._value[r] == null) continue;
            return false;
        }
        return true;
    }

    public Set<Map.Entry<String, V>> entrySet() {
        HashSet<Map.Entry<String, V>> entries = new HashSet<Map.Entry<String, V>>();
        for (int r = 0; r < this._rows; ++r) {
            if (this._key[r] == null || this._value[r] == null) continue;
            entries.add(new AbstractMap.SimpleEntry<String, V>(this._key[r], this._value[r]));
        }
        return entries;
    }

    public static int hilo(int diff) {
        return 1 + (diff | Integer.MAX_VALUE) / 0x3FFFFFFF;
    }

    public void dump() {
        for (int r = 0; r < this._rows; ++r) {
            char c = this._tree[r * 4 + 0];
            System.err.printf("%4d [%s,%d,%d,%d] '%s':%s%n", r, c < ' ' || c > '\u007f' ? "" + c : "'" + c + "'", (int)this._tree[r * 4 + 1], (int)this._tree[r * 4 + 2], (int)this._tree[r * 4 + 3], this._key[r], this._value[r]);
        }
    }

    @Deprecated
    public static class Growing<V>
    extends AbstractTrie<V> {
        private final int _growby;
        private ArrayTernaryTrie<V> _trie;

        public Growing(boolean insensitive, int capacity, int growby) {
            super(insensitive);
            this._growby = growby;
            this._trie = new ArrayTernaryTrie(insensitive, capacity);
        }

        public int hashCode() {
            return this._trie.hashCode();
        }

        @Override
        public V remove(String s2) {
            return this._trie.remove(s2);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Growing growing = (Growing)o;
            return Objects.equals(this._trie, growing._trie);
        }

        @Override
        public void clear() {
            this._trie.clear();
        }

        @Override
        public boolean put(V v) {
            return this.put(v.toString(), v);
        }

        @Override
        public boolean put(String s2, V v) {
            boolean added = this._trie.put(s2, v);
            while (!added && this._growby > 0) {
                int newCapacity = this._trie._key.length + this._growby;
                if (newCapacity > 65534) {
                    return false;
                }
                ArrayTernaryTrie<V> bigger = new ArrayTernaryTrie<V>(this._trie.isCaseInsensitive(), newCapacity);
                for (Map.Entry<String, V> entry : this._trie.entrySet()) {
                    bigger.put(entry.getKey(), entry.getValue());
                }
                this._trie = bigger;
                added = this._trie.put(s2, v);
            }
            return added;
        }

        @Override
        public V get(String s2) {
            return this._trie.get(s2);
        }

        @Override
        public V get(ByteBuffer b) {
            return this._trie.get(b);
        }

        @Override
        public V get(String s2, int offset, int len) {
            return this._trie.get(s2, offset, len);
        }

        @Override
        public V get(ByteBuffer b, int offset, int len) {
            return this._trie.get(b, offset, len);
        }

        @Override
        public V getBest(byte[] b, int offset, int len) {
            return this._trie.getBest(b, offset, len);
        }

        @Override
        public V getBest(String s2) {
            return this._trie.getBest(s2);
        }

        @Override
        public V getBest(String s2, int offset, int length) {
            return this._trie.getBest(s2, offset, length);
        }

        @Override
        public V getBest(ByteBuffer b, int offset, int len) {
            return this._trie.getBest(b, offset, len);
        }

        public String toString() {
            return this._trie.toString();
        }

        @Override
        public Set<String> keySet() {
            return this._trie.keySet();
        }

        public void dump() {
            this._trie.dump();
        }

        @Override
        public boolean isEmpty() {
            return this._trie.isEmpty();
        }

        @Override
        public int size() {
            return this._trie.size();
        }
    }
}

