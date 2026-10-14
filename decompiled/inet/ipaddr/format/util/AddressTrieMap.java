/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.AddressTrieSet;
import inet.ipaddr.format.util.AssociativeAddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AddressTrieMap<K extends Address, V>
extends AbstractMap<K, V>
implements NavigableMap<K, V>,
Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private AssociativeAddressTrie<K, V> trie;
    private final boolean isReverse;
    private final AddressTrieSet.Range<K> bounds;
    private EntrySet<K, V> entrySet;
    private AddressTrieSet<K> keySet;
    private AddressTrieMap<K, V> descending;

    public AddressTrieMap(AssociativeAddressTrie<K, V> trie) {
        this.trie = trie;
        this.isReverse = false;
        this.bounds = null;
        if (trie.map == null) {
            trie.map = this;
        }
    }

    public AddressTrieMap(AssociativeAddressTrie<K, V> trie, Map<? extends K, ? extends V> map) {
        this.trie = trie;
        this.isReverse = false;
        this.bounds = null;
        if (trie.map == null) {
            trie.map = this;
        }
        this.putAll(map);
    }

    AddressTrieMap(AssociativeAddressTrie<K, V> trie, AddressTrieSet.Range<K> bounds, boolean isReverse) {
        this.trie = trie;
        this.bounds = bounds;
        this.isReverse = isReverse;
        if (trie.map == null && !isReverse && bounds == null) {
            trie.map = this;
        }
    }

    boolean isBounded() {
        return this.bounds != null;
    }

    @Override
    public AddressTrieMap<K, V> descendingMap() {
        AddressTrieMap<K, V> desc = this.descending;
        if (desc == null) {
            AddressTrieSet.Range<K> reverseBounds = this.isBounded() ? this.bounds.reverse() : null;
            this.descending = desc = new AddressTrieMap<K, V>(this.trie, reverseBounds, !this.isReverse);
            desc.descending = this;
        }
        return desc;
    }

    @Override
    public AddressTrieSet<K> descendingKeySet() {
        return ((AddressTrieMap)this.descendingMap()).keySet();
    }

    public AssociativeAddressTrie<K, V> asTrie() {
        if (this.isBounded()) {
            return this.trie.clone();
        }
        if (!this.isReverse) {
            this.trie.map = this;
        }
        return this.trie;
    }

    public boolean hasRestrictedRange() {
        return this.isBounded();
    }

    public AddressTrieSet.Range<K> getRange() {
        return this.bounds;
    }

    @Override
    public AddressTrieSet<K> keySet() {
        AddressTrieSet<K> set = this.keySet;
        if (set == null) {
            this.keySet = set = new AddressTrieSet<K>(this.trie, this.bounds, this.isReverse);
        }
        return set;
    }

    @Override
    public AddressTrieSet<K> navigableKeySet() {
        return this.keySet();
    }

    public EntrySet<K, V> entrySet() {
        EntrySet<K, V> set = this.entrySet;
        if (set == null) {
            this.entrySet = set = new EntrySet<K, V>(this.trie, this.isReverse);
        }
        return set;
    }

    @Override
    public V merge(K key, V suppliedValue, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (suppliedValue == null) {
            throw new NullPointerException();
        }
        AssociativeAddressTrie.AssociativeTrieNode<K, Object> node = this.trie.remap(key, existingValue -> {
            Object newValue = existingValue == null ? suppliedValue : remappingFunction.apply((Object)existingValue, (Object)suppliedValue);
            return newValue;
        });
        if (node != null) {
            return (V)node.getValue();
        }
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        AssociativeAddressTrie.AssociativeTrieNode<K, Object> node = this.trie.remap(key, existingValue -> {
            Object newValue = remappingFunction.apply((Object)key, (Object)existingValue);
            return newValue;
        });
        if (node != null) {
            return (V)node.getValue();
        }
        return null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> remappingFunction) {
        AssociativeAddressTrie.AssociativeTrieNode<K, Object> node = this.trie.remapIfAbsent(key, () -> remappingFunction.apply((Object)key), false);
        if (node != null) {
            return (V)node.getValue();
        }
        return null;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return (V)this.trie.remapIfAbsent(key, () -> value, true).getValue();
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V prevValue;
        AssociativeAddressTrie.AssociativeTrieNode<K, V> node = this.getNode(key);
        if (node != null && (prevValue = node.getValue()) != null) {
            V newValue = remappingFunction.apply(key, prevValue);
            if (newValue != null) {
                node.setValue(newValue);
            } else {
                node.remove();
            }
            return newValue;
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.trie.contains((Address)key);
    }

    @Override
    public boolean containsValue(Object value) {
        Iterator<AssociativeAddressTrie.AssociativeTrieNode<K, V>> iterator2 = this.trie.nodeIterator(true);
        while (iterator2.hasNext()) {
            AssociativeAddressTrie.AssociativeTrieNode<K, V> node = iterator2.next();
            if (!value.equals(node.getValue())) continue;
            return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        return this.trie.get((Address)key);
    }

    @Override
    public V put(K key, V value) {
        return this.trie.put(key, value);
    }

    @Override
    public V remove(Object key) {
        AssociativeAddressTrie.AssociativeTrieNode<Address, V> node = this.getNode((Address)key);
        if (node != null) {
            V result = node.getValue();
            node.remove();
            return result;
        }
        return null;
    }

    private AssociativeAddressTrie.AssociativeTrieNode<K, V> getNode(K key) {
        return this.trie.getAddedNode((Address)key);
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        AssociativeAddressTrie.AssociativeTrieNode<Address, V> node = this.getNode((Address)key);
        return node == null ? defaultValue : node.getValue();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Iterator<AssociativeAddressTrie.AssociativeTrieNode<K, V>> iterator2 = this.trie.nodeIterator(!this.isReverse);
        if (iterator2.hasNext()) {
            AssociativeAddressTrie.AssociativeTrieNode<K, V> next = iterator2.next();
            action.accept(next.getKey(), next.getValue());
            while (iterator2.hasNext()) {
                next = iterator2.next();
                action.accept(next.getKey(), next.getValue());
            }
        } else if (action == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Iterator<AssociativeAddressTrie.AssociativeTrieNode<K, V>> iterator2 = this.trie.nodeIterator(!this.isReverse);
        if (iterator2.hasNext()) {
            AssociativeAddressTrie.AssociativeTrieNode<K, V> next = iterator2.next();
            next.setValue(function.apply(next.getKey(), next.getValue()));
            while (iterator2.hasNext()) {
                next = iterator2.next();
                next.setValue(function.apply(next.getKey(), next.getValue()));
            }
        } else if (function == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public boolean remove(Object key, Object value) {
        V prevValue;
        AssociativeAddressTrie.AssociativeTrieNode<Address, V> node = this.getNode((Address)key);
        if (node != null && Objects.equals(value, prevValue = node.getValue())) {
            node.remove();
            return true;
        }
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        V prevValue;
        AssociativeAddressTrie.AssociativeTrieNode<K, V> node = this.getNode(key);
        if (node != null && Objects.equals(oldValue, prevValue = node.getValue())) {
            node.setValue(newValue);
            return true;
        }
        return false;
    }

    @Override
    public V replace(K key, V value) {
        AssociativeAddressTrie.AssociativeTrieNode<K, V> node = this.getNode(key);
        if (node != null) {
            V prevValue = node.getValue();
            node.setValue(value);
            return prevValue;
        }
        return null;
    }

    @Override
    public int size() {
        return this.trie.size();
    }

    @Override
    public boolean isEmpty() {
        return this.trie.isEmpty();
    }

    @Override
    public void clear() {
        this.trie.clear();
    }

    @Override
    public int hashCode() {
        return this.trie.hashCode();
    }

    private AddressTrieMap<K, V> toSubMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        AddressTrie.AddressBounds bounds;
        AddressTrie.AddressBounds<K> newBounds;
        if (this.isReverse) {
            K tmp = fromKey;
            boolean tmpInc = fromInclusive;
            fromKey = toKey;
            fromInclusive = toInclusive;
            toKey = tmp;
            toInclusive = tmpInc;
        }
        if ((newBounds = (bounds = this.trie.bounds) == null ? AddressTrie.AddressBounds.createNewBounds(fromKey, fromInclusive, toKey, toInclusive, this.trie.getComparator()) : bounds.restrict(fromKey, fromInclusive, toKey, toInclusive)) == null) {
            return this;
        }
        AddressTrieSet.Range<K> newRange = new AddressTrieSet.Range<K>(newBounds, this.isReverse);
        return new AddressTrieMap<K, V>(this.trie.createSubTrie((AddressTrie.AddressBounds)newBounds), newRange, this.isReverse);
    }

    @Override
    public AddressTrieMap<K, V> subMap(K fromKey, K toKey) {
        return this.subMap(fromKey, true, toKey, false);
    }

    @Override
    public AddressTrieMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        if (fromKey == null || toKey == null) {
            throw new NullPointerException();
        }
        return this.toSubMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    @Override
    public AddressTrieMap<K, V> headMap(K toKey) {
        return this.headMap(toKey, false);
    }

    @Override
    public AddressTrieMap<K, V> headMap(K toKey, boolean inclusive) {
        if (toKey == null) {
            throw new NullPointerException();
        }
        return this.toSubMap(null, true, toKey, inclusive);
    }

    @Override
    public AddressTrieMap<K, V> tailMap(K fromKey) {
        return this.tailMap(fromKey, true);
    }

    @Override
    public AddressTrieMap<K, V> tailMap(K fromKey, boolean inclusive) {
        if (fromKey == null) {
            throw new NullPointerException();
        }
        return this.toSubMap(fromKey, inclusive, null, false);
    }

    @Override
    public Map.Entry<K, V> firstEntry() {
        AddressTrie.TrieNode node;
        AddressTrie.TrieNode trieNode = node = this.isReverse ? this.trie.lastAddedNode() : this.trie.firstAddedNode();
        if (node == null) {
            return null;
        }
        return node;
    }

    @Override
    public K firstKey() {
        return (K)((AddressTrieSet)this.keySet()).first();
    }

    @Override
    public Map.Entry<K, V> lastEntry() {
        AddressTrie.TrieNode node;
        AddressTrie.TrieNode trieNode = node = this.isReverse ? this.trie.firstAddedNode() : this.trie.lastAddedNode();
        if (node == null) {
            return null;
        }
        return node;
    }

    @Override
    public K lastKey() {
        return (K)((AddressTrieSet)this.keySet()).last();
    }

    @Override
    public Map.Entry<K, V> lowerEntry(K key) {
        AddressTrie.TrieNode node;
        AddressTrie.TrieNode trieNode = node = this.isReverse ? this.trie.higherAddedNode((Address)key) : this.trie.lowerAddedNode((Address)key);
        if (node == null) {
            return null;
        }
        return node;
    }

    @Override
    public K lowerKey(K key) {
        return ((AddressTrieSet)this.keySet()).lower(key);
    }

    @Override
    public Map.Entry<K, V> floorEntry(K key) {
        AddressTrie.TrieNode node;
        AddressTrie.TrieNode trieNode = node = this.isReverse ? this.trie.ceilingAddedNode((Address)key) : this.trie.floorAddedNode((Address)key);
        if (node == null) {
            return null;
        }
        return node;
    }

    @Override
    public K floorKey(K key) {
        return ((AddressTrieSet)this.keySet()).floor(key);
    }

    @Override
    public Map.Entry<K, V> ceilingEntry(K key) {
        AddressTrie.TrieNode node;
        AddressTrie.TrieNode trieNode = node = this.isReverse ? this.trie.floorAddedNode((Address)key) : this.trie.ceilingAddedNode((Address)key);
        if (node == null) {
            return null;
        }
        return node;
    }

    @Override
    public K ceilingKey(K key) {
        return ((AddressTrieSet)this.keySet()).ceiling(key);
    }

    @Override
    public Map.Entry<K, V> higherEntry(K key) {
        AddressTrie.TrieNode node;
        AddressTrie.TrieNode trieNode = node = this.isReverse ? this.trie.lowerAddedNode((Address)key) : this.trie.higherAddedNode((Address)key);
        if (node == null) {
            return null;
        }
        return node;
    }

    @Override
    public K higherKey(K key) {
        return ((AddressTrieSet)this.keySet()).higher(key);
    }

    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        AddressTrie.TrieNode first;
        AddressTrie.TrieNode trieNode = first = this.isReverse ? this.trie.lastAddedNode() : this.trie.firstAddedNode();
        if (first == null) {
            return null;
        }
        first.remove();
        return first;
    }

    @Override
    public Map.Entry<K, V> pollLastEntry() {
        AddressTrie.TrieNode last;
        AddressTrie.TrieNode trieNode = last = this.isReverse ? this.trie.firstAddedNode() : this.trie.lastAddedNode();
        if (last == null) {
            return null;
        }
        last.remove();
        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AddressTrieMap) {
            AddressTrieMap other = (AddressTrieMap)o;
            return this.trie.equals(other.trie);
        }
        return super.equals(o);
    }

    @Override
    public AddressTrieMap<K, V> clone() {
        try {
            AddressTrieMap clone = (AddressTrieMap)super.clone();
            clone.trie = this.trie.clone();
            clone.trie.bounds = this.trie.bounds;
            clone.keySet = null;
            clone.entrySet = null;
            clone.descending = null;
            return clone;
        }
        catch (CloneNotSupportedException cannotHappen) {
            return null;
        }
    }

    @Override
    public Comparator<K> comparator() {
        return this.isReverse ? AddressTrie.reverseComparator() : AddressTrie.comparator();
    }

    public String toTrieString() {
        return this.trie.toString();
    }

    public AddressTrieMap<K, V> subMapFromKeysContainedBy(K addr) {
        AddressTrie newTrie = this.trie.elementsContainedByToSubTrie((Address)addr);
        if (this.trie == newTrie) {
            return this;
        }
        if (((AssociativeAddressTrie)newTrie).bounds == null) {
            return new AddressTrieMap<K, V>(newTrie, null, this.isReverse);
        }
        AddressTrieSet.Range newRange = new AddressTrieSet.Range(((AssociativeAddressTrie)newTrie).bounds, this.isReverse);
        return new AddressTrieMap(newTrie, newRange, this.isReverse);
    }

    public AddressTrieMap<K, V> subMapFromKeysContaining(K addr) {
        AddressTrie newTrie = this.trie.elementsContainingToTrie((Address)addr);
        if (this.trie == newTrie) {
            return this;
        }
        if (((AssociativeAddressTrie)newTrie).bounds == null) {
            return new AddressTrieMap<K, V>(newTrie, null, this.isReverse);
        }
        AddressTrieSet.Range newRange = new AddressTrieSet.Range(((AssociativeAddressTrie)newTrie).bounds, this.isReverse);
        return new AddressTrieMap(newTrie, newRange, this.isReverse);
    }

    public boolean keyContains(K addr) {
        return this.trie.elementContainsBounds(addr);
    }

    public Map.Entry<K, V> longestPrefixMatchEntry(K addr) {
        return this.trie.smallestElementContainingBounds((Address)addr);
    }

    public static class EntrySet<K extends Address, V>
    extends AbstractSet<Map.Entry<K, V>>
    implements Serializable {
        private static final long serialVersionUID = 1L;
        AssociativeAddressTrie<K, V> trie;
        private final boolean isReverse;

        EntrySet(AssociativeAddressTrie<K, V> trie, boolean isReverse) {
            this.trie = trie;
            this.isReverse = isReverse;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            Iterator<Map.Entry<K, V>> result = this.trie.nodeIterator(!this.isReverse);
            return result;
        }

        public Iterator<Map.Entry<K, V>> containingFirstIterator() {
            BinaryTreeNode.CachingIterator it = this.trie.containingFirstIterator(!this.isReverse);
            return it;
        }

        public Iterator<Map.Entry<K, V>> containedFirstIterator() {
            Iterator<Map.Entry<K, V>> it = this.trie.containedFirstIterator(!this.isReverse);
            return it;
        }

        public Iterator<Map.Entry<K, V>> blockSizeIterator() {
            Iterator<Map.Entry<K, V>> iterator2 = this.trie.blockSizeNodeIterator(!this.isReverse);
            return iterator2;
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            Spliterator<Map.Entry<K, V>> result = this.trie.nodeSpliterator(!this.isReverse);
            return result;
        }

        @Override
        public int size() {
            return this.trie.size();
        }

        @Override
        public boolean isEmpty() {
            return this.trie.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)o;
            AddressTrie.TrieNode existingNode = this.trie.getAddedNode((Address)entry.getKey());
            return existingNode != null && Objects.equals(existingNode.getValue(), entry.getValue());
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)o;
            AddressTrie.TrieNode existingNode = this.trie.getAddedNode((Address)entry.getKey());
            if (existingNode != null && Objects.equals(((AssociativeAddressTrie.AssociativeTrieNode)existingNode).getValue(), entry.getValue())) {
                existingNode.remove();
                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            this.trie.clear();
        }

        @Override
        public int hashCode() {
            return this.trie.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof EntrySet) {
                EntrySet other = (EntrySet)o;
                return this.trie.equals(other.trie);
            }
            return super.equals(o);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            if (collection instanceof List || collection instanceof Queue || collection.size() < this.size()) {
                boolean result = false;
                for (Object object : collection) {
                    if (!this.remove(object)) continue;
                    result = true;
                }
                return result;
            }
            return this.removeIf(collection::contains);
        }
    }
}

