/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Spliterator;
import java.util.function.Function;

public class AddressTrieSet<E extends Address>
extends AbstractSet<E>
implements NavigableSet<E>,
Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private AddressTrie<E> trie;
    private final boolean isReverse;
    private final Range<E> bounds;
    private AddressTrieSet<E> descending;

    public AddressTrieSet(AddressTrie<E> trie) {
        this.trie = trie;
        this.isReverse = false;
        this.bounds = null;
        if (trie.set == null) {
            trie.set = this;
        }
    }

    public AddressTrieSet(AddressTrie<E> trie, Collection<? extends E> collection) {
        this.trie = trie;
        this.isReverse = false;
        this.bounds = null;
        if (trie.set == null) {
            trie.set = this;
        }
        this.addAll(collection);
    }

    AddressTrieSet(AddressTrie<E> trie, Range<E> bounds, boolean isReverse) {
        this.trie = trie;
        this.bounds = bounds;
        this.isReverse = isReverse;
        if (trie.set == null && !isReverse && bounds == null) {
            trie.set = this;
        }
    }

    private boolean isBounded() {
        return this.bounds != null;
    }

    @Override
    public AddressTrieSet<E> descendingSet() {
        AddressTrieSet<E> desc = this.descending;
        if (desc == null) {
            Range<E> reverseBounds = this.isBounded() ? this.bounds.reverse() : null;
            this.descending = desc = new AddressTrieSet<E>(this.trie, reverseBounds, !this.isReverse);
            desc.descending = this;
        }
        return desc;
    }

    public AddressTrie<E> asTrie() {
        if (this.isBounded()) {
            return this.trie.clone();
        }
        if (!this.isReverse) {
            this.trie.set = this;
        }
        return this.trie;
    }

    public boolean hasRestrictedRange() {
        return this.isBounded();
    }

    public Range<E> getRange() {
        return this.bounds;
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
        return this.trie.contains((Address)o);
    }

    @Override
    public boolean add(E e) {
        return this.trie.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.trie.remove((Address)o);
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
        if (o instanceof AddressTrieSet) {
            AddressTrieSet other = (AddressTrieSet)o;
            return this.trie.equals(other.trie);
        }
        return super.equals(o);
    }

    public AddressTrieSet<E> clone() {
        try {
            AddressTrieSet clone = (AddressTrieSet)super.clone();
            clone.trie = this.trie.clone();
            clone.trie.bounds = this.trie.bounds;
            clone.descending = null;
            return clone;
        }
        catch (CloneNotSupportedException cannotHappen) {
            return null;
        }
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

    @Override
    public Iterator<E> iterator() {
        return this.isReverse ? this.trie.descendingIterator() : this.trie.iterator();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return this.isReverse ? this.trie.iterator() : this.trie.descendingIterator();
    }

    public Iterator<E> containingFirstIterator() {
        return new BinaryTreeNode.KeyIterator(this.trie.containingFirstIterator(!this.isReverse));
    }

    public Iterator<E> containedFirstIterator() {
        return new BinaryTreeNode.KeyIterator<E>(this.trie.containedFirstIterator(!this.isReverse));
    }

    @Override
    public Spliterator<E> spliterator() {
        return this.isReverse ? this.trie.descendingSpliterator() : this.trie.spliterator();
    }

    @Override
    public Comparator<E> comparator() {
        return this.isReverse ? AddressTrie.reverseComparator() : AddressTrie.comparator();
    }

    public Iterator<E> blockSizeIterator() {
        return new BinaryTreeNode.KeyIterator<E>(this.trie.blockSizeNodeIterator(!this.isReverse));
    }

    private AddressTrieSet<E> toSubSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        Range<E> range;
        AddressTrie.AddressBounds<E> bounds;
        AddressTrie.AddressBounds newBounds;
        if (this.isReverse) {
            E tmp = fromElement;
            boolean tmpInc = fromInclusive;
            fromElement = toElement;
            fromInclusive = toInclusive;
            toElement = tmp;
            toInclusive = tmpInc;
        }
        if ((newBounds = (bounds = (range = this.bounds) != null ? range.wrapped : null) == null ? AddressTrie.AddressBounds.createNewBounds(fromElement, fromInclusive, toElement, toInclusive, this.trie.getComparator()) : bounds.restrict(fromElement, fromInclusive, toElement, toInclusive)) == null) {
            return this;
        }
        Range newRange = new Range(newBounds, this.isReverse);
        return new AddressTrieSet<E>(this.trie.createSubTrie(newBounds), newRange, this.isReverse);
    }

    @Override
    public AddressTrieSet<E> subSet(E fromElement, E toElement) {
        return this.subSet(fromElement, true, toElement, false);
    }

    @Override
    public AddressTrieSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        if (fromElement == null || toElement == null) {
            throw new NullPointerException();
        }
        return this.toSubSet(fromElement, fromInclusive, toElement, toInclusive);
    }

    @Override
    public AddressTrieSet<E> headSet(E toElement) {
        return this.headSet(toElement, false);
    }

    @Override
    public AddressTrieSet<E> headSet(E toElement, boolean inclusive) {
        if (toElement == null) {
            throw new NullPointerException();
        }
        return this.toSubSet(null, true, toElement, inclusive);
    }

    @Override
    public AddressTrieSet<E> tailSet(E fromElement) {
        return this.tailSet(fromElement, true);
    }

    @Override
    public AddressTrieSet<E> tailSet(E fromElement, boolean inclusive) {
        if (fromElement == null) {
            throw new NullPointerException();
        }
        return this.toSubSet(fromElement, inclusive, null, false);
    }

    @Override
    public E first() {
        AddressTrie.TrieNode<E> first;
        AddressTrie.TrieNode<E> trieNode = first = this.isReverse ? this.trie.lastAddedNode() : this.trie.firstAddedNode();
        if (first == null) {
            throw new NoSuchElementException();
        }
        return (E)((Address)first.getKey());
    }

    @Override
    public E last() {
        AddressTrie.TrieNode<E> last;
        AddressTrie.TrieNode<E> trieNode = last = this.isReverse ? this.trie.firstAddedNode() : this.trie.lastAddedNode();
        if (last == null) {
            throw new NoSuchElementException();
        }
        return (E)((Address)last.getKey());
    }

    @Override
    public E lower(E e) {
        AddressTrie.TrieNode<E> node;
        AddressTrie.TrieNode<E> trieNode = node = this.isReverse ? this.trie.higherAddedNode(e) : this.trie.lowerAddedNode(e);
        if (node == null) {
            return null;
        }
        return (E)((Address)node.getKey());
    }

    @Override
    public E floor(E e) {
        AddressTrie.TrieNode<E> node;
        AddressTrie.TrieNode<E> trieNode = node = this.isReverse ? this.trie.ceilingAddedNode(e) : this.trie.floorAddedNode(e);
        if (node == null) {
            return null;
        }
        return (E)((Address)node.getKey());
    }

    @Override
    public E ceiling(E e) {
        AddressTrie.TrieNode<E> node;
        AddressTrie.TrieNode<E> trieNode = node = this.isReverse ? this.trie.floorAddedNode(e) : this.trie.ceilingAddedNode(e);
        if (node == null) {
            return null;
        }
        return (E)((Address)node.getKey());
    }

    @Override
    public E higher(E e) {
        AddressTrie.TrieNode<E> node;
        AddressTrie.TrieNode<E> trieNode = node = this.isReverse ? this.trie.lowerAddedNode(e) : this.trie.higherAddedNode(e);
        if (node == null) {
            return null;
        }
        return (E)((Address)node.getKey());
    }

    @Override
    public E pollFirst() {
        AddressTrie.TrieNode<E> first;
        AddressTrie.TrieNode<E> trieNode = first = this.isReverse ? this.trie.lastAddedNode() : this.trie.firstAddedNode();
        if (first == null) {
            return null;
        }
        first.remove();
        return (E)((Address)first.getKey());
    }

    @Override
    public E pollLast() {
        AddressTrie.TrieNode<E> last;
        AddressTrie.TrieNode<E> trieNode = last = this.isReverse ? this.trie.firstAddedNode() : this.trie.lastAddedNode();
        if (last == null) {
            return null;
        }
        last.remove();
        return (E)((Address)last.getKey());
    }

    public String toTrieString() {
        return this.trie.toString();
    }

    public AddressTrieSet<E> elementsContainedBy(E addr) {
        AddressTrie<E> newTrie = this.trie.elementsContainedByToSubTrie(addr);
        if (this.trie == newTrie) {
            return this;
        }
        if (newTrie.bounds == null) {
            return new AddressTrieSet<E>(newTrie, null, this.isReverse);
        }
        Range newRange = new Range(newTrie.bounds, this.isReverse);
        return new AddressTrieSet<E>(newTrie, newRange, this.isReverse);
    }

    public AddressTrieSet<E> elementsContaining(E addr) {
        AddressTrie<E> newTrie = this.trie.elementsContainingToTrie(addr);
        if (this.trie == newTrie) {
            return this;
        }
        if (newTrie.bounds == null) {
            return new AddressTrieSet<E>(newTrie, null, this.isReverse);
        }
        Range newRange = new Range(newTrie.bounds, this.isReverse);
        return new AddressTrieSet<E>(newTrie, newRange, this.isReverse);
    }

    public boolean elementContains(E addr) {
        return this.trie.elementContainsBounds(addr);
    }

    public E longestPrefixMatch(E addr) {
        return this.trie.longestPrefixMatchBounds(addr);
    }

    public static class Range<E extends Address>
    implements Serializable {
        private static final long serialVersionUID = 1L;
        final AddressTrie.AddressBounds<E> wrapped;
        Range<E> reversed;
        final boolean isReverse;

        Range(AddressTrie.AddressBounds<E> wrapped) {
            this(wrapped, false);
        }

        Range(AddressTrie.AddressBounds<E> wrapped, boolean isReverse) {
            if (wrapped == null) {
                throw new NullPointerException();
            }
            this.wrapped = wrapped;
            this.isReverse = isReverse;
        }

        Range<E> reverse() {
            Range<E> result = this.reversed;
            if (result == null) {
                this.reversed = result = new Range<E>(this.wrapped, !this.isReverse);
                result.reversed = this;
            }
            return result;
        }

        public boolean isInBounds(E addr) {
            return this.isWithinLowerBound(addr) && this.isWithinUpperBound(addr);
        }

        public E getLowerBound() {
            return (E)(this.isReverse ? (Address)this.wrapped.getUpperBound() : (Address)this.wrapped.getLowerBound());
        }

        public E getUpperBound() {
            return (E)(this.isReverse ? (Address)this.wrapped.getLowerBound() : (Address)this.wrapped.getUpperBound());
        }

        public boolean lowerIsInclusive() {
            return this.isReverse ? this.wrapped.upperIsInclusive() : this.wrapped.lowerIsInclusive();
        }

        public boolean upperIsInclusive() {
            return this.isReverse ? this.wrapped.lowerIsInclusive() : this.wrapped.upperIsInclusive();
        }

        public boolean isLowerBounded() {
            return this.getLowerBound() != null;
        }

        public boolean isUpperBounded() {
            return this.getUpperBound() != null;
        }

        public boolean isBelowLowerBound(E addr) {
            return this.isReverse ? this.wrapped.isAboveUpperBound(addr) : this.wrapped.isBelowLowerBound(addr);
        }

        public boolean isAboveUpperBound(E addr) {
            return this.isReverse ? this.wrapped.isBelowLowerBound(addr) : this.wrapped.isAboveUpperBound(addr);
        }

        public boolean isWithinLowerBound(E addr) {
            return !this.isBelowLowerBound(addr);
        }

        public boolean isWithinUpperBound(E addr) {
            return !this.isAboveUpperBound(addr);
        }

        public String toString() {
            Function<Address, String> stringer = Address::toCanonicalString;
            return AddressTrie.AddressBounds.toString(this.getLowerBound(), this.lowerIsInclusive(), this.getUpperBound(), this.upperIsInclusive(), stringer, " -> ", stringer);
        }
    }
}

