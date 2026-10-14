/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import java.util.Arrays;

final class PersistentHashArrayMappedTrie {
    private PersistentHashArrayMappedTrie() {
    }

    static <K, V> V get(Node<K, V> root, K key) {
        if (root == null) {
            return null;
        }
        return root.get(key, key.hashCode(), 0);
    }

    static <K, V> Node<K, V> put(Node<K, V> root, K key, V value) {
        if (root == null) {
            return new Leaf<K, V>(key, value);
        }
        return root.put(key, value, key.hashCode(), 0);
    }

    static interface Node<K, V> {
        public V get(K var1, int var2, int var3);

        public Node<K, V> put(K var1, V var2, int var3, int var4);

        public int size();
    }

    static final class CompressedIndex<K, V>
    implements Node<K, V> {
        private static final int BITS = 5;
        private static final int BITS_MASK = 31;
        final int bitmap;
        final Node<K, V>[] values;
        private final int size;

        private CompressedIndex(int bitmap, Node<K, V>[] values2, int size) {
            this.bitmap = bitmap;
            this.values = values2;
            this.size = size;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public V get(K key, int hash, int bitsConsumed) {
            int indexBit = CompressedIndex.indexBit(hash, bitsConsumed);
            if ((this.bitmap & indexBit) == 0) {
                return null;
            }
            int compressedIndex = this.compressedIndex(indexBit);
            return this.values[compressedIndex].get(key, hash, bitsConsumed + 5);
        }

        @Override
        public Node<K, V> put(K key, V value, int hash, int bitsConsumed) {
            int indexBit = CompressedIndex.indexBit(hash, bitsConsumed);
            int compressedIndex = this.compressedIndex(indexBit);
            if ((this.bitmap & indexBit) == 0) {
                int newBitmap = this.bitmap | indexBit;
                Node[] newValues = new Node[this.values.length + 1];
                System.arraycopy(this.values, 0, newValues, 0, compressedIndex);
                newValues[compressedIndex] = new Leaf<K, V>(key, value);
                System.arraycopy(this.values, compressedIndex, newValues, compressedIndex + 1, this.values.length - compressedIndex);
                return new CompressedIndex<K, V>(newBitmap, newValues, this.size() + 1);
            }
            Node<K, V>[] newValues = Arrays.copyOf(this.values, this.values.length);
            newValues[compressedIndex] = this.values[compressedIndex].put(key, value, hash, bitsConsumed + 5);
            int newSize = this.size();
            newSize += newValues[compressedIndex].size();
            return new CompressedIndex<K, V>(this.bitmap, newValues, newSize -= this.values[compressedIndex].size());
        }

        static <K, V> Node<K, V> combine(Node<K, V> node1, int hash1, Node<K, V> node2, int hash2, int bitsConsumed) {
            int indexBit2;
            assert (hash1 != hash2);
            int indexBit1 = CompressedIndex.indexBit(hash1, bitsConsumed);
            if (indexBit1 == (indexBit2 = CompressedIndex.indexBit(hash2, bitsConsumed))) {
                Node<K, V> node = CompressedIndex.combine(node1, hash1, node2, hash2, bitsConsumed + 5);
                Node[] values2 = new Node[]{node};
                return new CompressedIndex<K, V>(indexBit1, values2, node.size());
            }
            if (CompressedIndex.uncompressedIndex(hash1, bitsConsumed) > CompressedIndex.uncompressedIndex(hash2, bitsConsumed)) {
                Node<K, V> nodeCopy = node1;
                node1 = node2;
                node2 = nodeCopy;
            }
            Node[] values3 = new Node[]{node1, node2};
            return new CompressedIndex<K, V>(indexBit1 | indexBit2, values3, node1.size() + node2.size());
        }

        public String toString() {
            StringBuilder valuesSb = new StringBuilder();
            valuesSb.append("CompressedIndex(").append(String.format("bitmap=%s ", Integer.toBinaryString(this.bitmap)));
            for (Node<K, V> value : this.values) {
                valuesSb.append(value).append(" ");
            }
            return valuesSb.append(")").toString();
        }

        private int compressedIndex(int indexBit) {
            return Integer.bitCount(this.bitmap & indexBit - 1);
        }

        private static int uncompressedIndex(int hash, int bitsConsumed) {
            return hash >>> bitsConsumed & 0x1F;
        }

        private static int indexBit(int hash, int bitsConsumed) {
            int uncompressedIndex = CompressedIndex.uncompressedIndex(hash, bitsConsumed);
            return 1 << uncompressedIndex;
        }
    }

    static final class CollisionLeaf<K, V>
    implements Node<K, V> {
        private final K[] keys;
        private final V[] values;

        CollisionLeaf(K key1, V value1, K key2, V value2) {
            this(new Object[]{key1, key2}, new Object[]{value1, value2});
            assert (key1 != key2);
            assert (key1.hashCode() == key2.hashCode());
        }

        private CollisionLeaf(K[] keys2, V[] values2) {
            this.keys = keys2;
            this.values = values2;
        }

        @Override
        public int size() {
            return this.values.length;
        }

        @Override
        public V get(K key, int hash, int bitsConsumed) {
            for (int i = 0; i < this.keys.length; ++i) {
                if (this.keys[i] != key) continue;
                return this.values[i];
            }
            return null;
        }

        @Override
        public Node<K, V> put(K key, V value, int hash, int bitsConsumed) {
            int thisHash = this.keys[0].hashCode();
            if (thisHash != hash) {
                return CompressedIndex.combine(new Leaf<K, V>(key, value), hash, this, thisHash, bitsConsumed);
            }
            int keyIndex = this.indexOfKey(key);
            if (keyIndex != -1) {
                K[] newKeys = Arrays.copyOf(this.keys, this.keys.length);
                V[] newValues = Arrays.copyOf(this.values, this.keys.length);
                newKeys[keyIndex] = key;
                newValues[keyIndex] = value;
                return new CollisionLeaf<K, V>(newKeys, newValues);
            }
            K[] newKeys = Arrays.copyOf(this.keys, this.keys.length + 1);
            V[] newValues = Arrays.copyOf(this.values, this.keys.length + 1);
            newKeys[this.keys.length] = key;
            newValues[this.keys.length] = value;
            return new CollisionLeaf<K, V>(newKeys, newValues);
        }

        private int indexOfKey(K key) {
            for (int i = 0; i < this.keys.length; ++i) {
                if (this.keys[i] != key) continue;
                return i;
            }
            return -1;
        }

        public String toString() {
            StringBuilder valuesSb = new StringBuilder();
            valuesSb.append("CollisionLeaf(");
            for (int i = 0; i < this.values.length; ++i) {
                valuesSb.append("(key=").append(this.keys[i]).append(" value=").append(this.values[i]).append(") ");
            }
            return valuesSb.append(")").toString();
        }
    }

    static final class Leaf<K, V>
    implements Node<K, V> {
        private final K key;
        private final V value;

        public Leaf(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public V get(K key, int hash, int bitsConsumed) {
            if (this.key == key) {
                return this.value;
            }
            return null;
        }

        @Override
        public Node<K, V> put(K key, V value, int hash, int bitsConsumed) {
            int thisHash = this.key.hashCode();
            if (thisHash != hash) {
                return CompressedIndex.combine(new Leaf<K, V>(key, value), hash, this, thisHash, bitsConsumed);
            }
            if (this.key == key) {
                return new Leaf<K, V>(key, value);
            }
            return new CollisionLeaf<K, V>(this.key, this.value, key, value);
        }

        public String toString() {
            return String.format("Leaf(key=%s value=%s)", this.key, this.value);
        }
    }
}

