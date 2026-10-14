/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.AssociativeAddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import inet.ipaddr.format.util.TreeOps;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;

public interface AddressTrieOps<E extends Address>
extends TreeOps<E> {
    public AddressTrie.TrieNode<E> getNode(E var1);

    default public AddressTrie.TrieNode<E> getAddedNode(E addr) {
        AddressTrie.TrieNode<E> ret = this.getNode(addr);
        return ret == null || ret.isAdded() ? ret : null;
    }

    public boolean elementContains(E var1);

    public boolean contains(E var1);

    public boolean remove(E var1);

    public AddressTrie.TrieNode<E> removeElementsContainedBy(E var1);

    public AddressTrie.TrieNode<E> elementsContainedBy(E var1);

    public AddressTrie.TrieNode<E> elementsContaining(E var1);

    public AddressTrie.TrieNode<E> longestPrefixMatchNode(E var1);

    public E longestPrefixMatch(E var1);

    @Override
    public Iterator<? extends AddressTrie.TrieNode<E>> nodeIterator(boolean var1);

    @Override
    public Iterator<? extends AddressTrie.TrieNode<E>> allNodeIterator(boolean var1);

    @Override
    public <C> BinaryTreeNode.CachingIterator<? extends AddressTrie.TrieNode<E>, E, C> containingFirstIterator(boolean var1);

    @Override
    public <C> BinaryTreeNode.CachingIterator<? extends AddressTrie.TrieNode<E>, E, C> containingFirstAllNodeIterator(boolean var1);

    @Override
    public Iterator<? extends AddressTrie.TrieNode<E>> containedFirstIterator(boolean var1);

    @Override
    public Iterator<? extends AddressTrie.TrieNode<E>> containedFirstAllNodeIterator(boolean var1);

    @Override
    public Spliterator<? extends AddressTrie.TrieNode<E>> nodeSpliterator(boolean var1);

    @Override
    public Spliterator<? extends AddressTrie.TrieNode<E>> allNodeSpliterator(boolean var1);

    public AddressTrie.TrieNode<E> firstNode();

    public AddressTrie.TrieNode<E> lastNode();

    public AddressTrie.TrieNode<E> firstAddedNode();

    public AddressTrie.TrieNode<E> lastAddedNode();

    public AddressTrie.TrieNode<E> floorAddedNode(E var1);

    public AddressTrie.TrieNode<E> lowerAddedNode(E var1);

    public AddressTrie.TrieNode<E> ceilingAddedNode(E var1);

    public AddressTrie.TrieNode<E> higherAddedNode(E var1);

    public static interface AssociativeAddressTriePutOps<K extends Address, V>
    extends AssociativeAddressTrieOps<K, V> {
        public V put(K var1, V var2);

        public boolean putNew(K var1, V var2);

        public AssociativeAddressTrie.AssociativeTrieNode<K, V> putNode(K var1, V var2);

        public AssociativeAddressTrie.AssociativeTrieNode<K, V> remap(K var1, Function<? super V, ? extends V> var2);

        public AssociativeAddressTrie.AssociativeTrieNode<K, V> remapIfAbsent(K var1, Supplier<? extends V> var2, boolean var3);

        public AssociativeAddressTrie.AssociativeTrieNode<K, V> putTrie(AssociativeAddressTrie.AssociativeTrieNode<K, V> var1);
    }

    public static interface AssociativeAddressTrieOps<K extends Address, V>
    extends AddressTrieOps<K> {
        public V get(K var1);
    }

    public static interface AddressTrieAddOps<E extends Address>
    extends AddressTrieOps<E> {
        public boolean add(E var1);

        public AddressTrie.TrieNode<E> addNode(E var1);

        public AddressTrie.TrieNode<E> addTrie(AddressTrie.TrieNode<E> var1);
    }
}

