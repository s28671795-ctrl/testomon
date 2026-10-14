/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.format.util.AbstractTree;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.AddressTrieMap;
import inet.ipaddr.format.util.AddressTrieOps;
import inet.ipaddr.format.util.AssociativeAddedTree;
import inet.ipaddr.format.util.BinaryTreeNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AssociativeAddressTrie<K extends Address, V>
extends AddressTrie<K>
implements AddressTrieOps.AssociativeAddressTriePutOps<K, V> {
    private static final long serialVersionUID = 1L;
    AddressTrieMap<K, V> map;

    public AssociativeAddressTrie(AssociativeTrieNode<K, V> root) {
        super(root);
    }

    protected AssociativeAddressTrie(AssociativeTrieNode<K, V> root, AddressTrie.AddressBounds<K> bounds) {
        super(root, bounds);
    }

    protected AssociativeTrieNode<K, V> absoluteRoot() {
        return (AssociativeTrieNode)super.absoluteRoot();
    }

    @Override
    public AssociativeTrieNode<K, V> getRoot() {
        return (AssociativeTrieNode)super.getRoot();
    }

    @Override
    public V put(K addr, V value) {
        addr = AssociativeAddressTrie.checkBlockOrAddress(addr, true);
        if (this.bounds != null && !this.bounds.isInBounds(addr)) {
            AssociativeAddressTrie.throwOutOfBounds();
        }
        this.adjustRoot(addr);
        AddressTrie.TrieNode root = this.absoluteRoot();
        AddressTrie.OpResult<K> result = new AddressTrie.OpResult<K>(addr, AddressTrie.Operation.INSERT);
        result.newValue = value;
        root.matchBits(result);
        return (V)result.existingValue;
    }

    @Override
    public boolean putNew(K addr, V value) {
        addr = AssociativeAddressTrie.checkBlockOrAddress(addr, true);
        if (this.bounds != null && !this.bounds.isInBounds(addr)) {
            AssociativeAddressTrie.throwOutOfBounds();
        }
        this.adjustRoot(addr);
        AddressTrie.TrieNode root = this.absoluteRoot();
        AddressTrie.OpResult<K> result = new AddressTrie.OpResult<K>(addr, AddressTrie.Operation.INSERT);
        result.newValue = value;
        root.matchBits(result);
        return !result.exists;
    }

    public AssociativeTrieNode<K, V> addNode(K addr) {
        return (AssociativeTrieNode)super.addNode(addr);
    }

    @Override
    AddressTrie.TrieNode<K> addNode(AddressTrie.OpResult<K> result, AddressTrie.TrieNode<K> fromNode, AddressTrie.TrieNode<K> nodeToAdd, boolean withValues) {
        if (withValues && nodeToAdd instanceof AssociativeTrieNode) {
            AssociativeTrieNode node = (AssociativeTrieNode)nodeToAdd;
            result.newValue = node.getValue();
        }
        return super.addNode(result, fromNode, nodeToAdd, withValues);
    }

    @Override
    public abstract AssociativeAddedTree<K, V> constructAddedNodesTree();

    protected void contructAssociativeAddedTree(AssociativeAddressTrie<K, SubNodesMappingAssociative<K, V>> emptyTrie) {
        emptyTrie.addTrie(this.absoluteRoot());
        BinaryTreeNode.CachingIterator<AssociativeTrieNode<K, SubNodesMappingAssociative<K, V>>, K, AssociativeTrieNode> cachingIterator = emptyTrie.containingFirstAllNodeIterator(true);
        BinaryTreeNode.CachingIterator thisIterator = this.containingFirstAllNodeIterator(true);
        while (cachingIterator.hasNext()) {
            AddressTrie.TrieNode parentParent;
            AddressTrie.TrieNode parent;
            AssociativeTrieNode newNext = (AssociativeTrieNode)cachingIterator.next();
            AssociativeTrieNode thisNext = (AssociativeTrieNode)thisIterator.next();
            SubNodesMappingAssociative mapping = new SubNodesMappingAssociative();
            mapping.value = thisNext.getValue();
            newNext.setValue(mapping);
            cachingIterator.cacheWithLowerSubNode(newNext);
            cachingIterator.cacheWithUpperSubNode(newNext);
            if (!newNext.isAdded() || (parent = (AssociativeTrieNode)cachingIterator.getCached()) == null) continue;
            while (!parent.isAdded() && (parentParent = parent.getParent()) != null) {
                parent = parentParent;
            }
            SubNodesMappingAssociative mappedNodes = (SubNodesMappingAssociative)parent.getValue();
            ArrayList<AssociativeTrieNode> addedSubs = mappedNodes.subNodes;
            if (addedSubs == null) {
                mappedNodes.subNodes = addedSubs = new ArrayList<AssociativeTrieNode>(newNext.size() - 1);
            }
            addedSubs.add(newNext);
        }
        SubNodesMappingAssociative value = (SubNodesMappingAssociative)((AssociativeTrieNode)emptyTrie.getRoot()).getValue();
        if (value != null && value.subNodes != null) {
            value.subNodes.trimToSize();
        }
        Iterator<AssociativeTrieNode<K, SubNodesMappingAssociative<K, V>>> iter = emptyTrie.allNodeIterator(true);
        while (iter.hasNext()) {
            SubNodesMappingAssociative<K, V> list = iter.next().getValue();
            if (list == null || list.subNodes == null) continue;
            list.subNodes.trimToSize();
        }
    }

    @Override
    public AssociativeTrieNode<K, V> putTrie(AssociativeTrieNode<K, V> trie) {
        return (AssociativeTrieNode)this.addTrie(trie, true);
    }

    @Override
    public AssociativeTrieNode<K, V> putNode(K addr, V value) {
        addr = AssociativeAddressTrie.checkBlockOrAddress(addr, true);
        if (this.bounds != null && !this.bounds.isInBounds(addr)) {
            AssociativeAddressTrie.throwOutOfBounds();
        }
        this.adjustRoot(addr);
        AddressTrie.TrieNode root = this.absoluteRoot();
        AddressTrie.OpResult<K> result = new AddressTrie.OpResult<K>(addr, AddressTrie.Operation.INSERT);
        result.newValue = value;
        root.matchBits(result);
        AddressTrie.TrieNode node = result.existingNode;
        if (node == null) {
            node = result.inserted;
        }
        return (AssociativeTrieNode)node;
    }

    @Override
    public AssociativeTrieNode<K, V> remap(K addr, Function<? super V, ? extends V> remapper) {
        return this.remapImpl(addr, existingAddr -> {
            Object result = remapper.apply((Object)existingAddr);
            return result == null ? REMAP_ACTION.REMOVE_NODE : result;
        });
    }

    @Override
    public AssociativeTrieNode<K, V> remapIfAbsent(K addr, Supplier<? extends V> remapper, boolean insertNull) {
        return this.remapImpl(addr, existingVal -> {
            Object result;
            if (existingVal == null && ((result = remapper.get()) != null || insertNull)) {
                return result;
            }
            return REMAP_ACTION.DO_NOTHING;
        });
    }

    private AssociativeTrieNode<K, V> remapImpl(K addr, Function<? super V, ? extends Object> remapper) {
        AddressTrie.TrieNode subRoot;
        addr = AssociativeAddressTrie.checkBlockOrAddress(addr, true);
        if (this.bounds != null) {
            if (!this.bounds.isInBounds(addr)) {
                AssociativeAddressTrie.throwOutOfBounds();
            }
            if ((subRoot = this.getRoot()) == null) {
                subRoot = this.absoluteRoot();
            }
        } else {
            subRoot = this.absoluteRoot();
        }
        AddressTrie.OpResult<K> result = new AddressTrie.OpResult<K>(addr, AddressTrie.Operation.REMAP);
        result.remapper = remapper;
        subRoot.matchBits(result);
        AddressTrie.TrieNode node = result.existingNode;
        if (node == null) {
            node = result.inserted;
        }
        return (AssociativeTrieNode)node;
    }

    @Override
    public V get(K addr) {
        AddressTrie.TrieNode subRoot;
        if (this.bounds != null) {
            if (!this.bounds.isInBounds(addr = AssociativeAddressTrie.checkBlockOrAddress(addr, true))) {
                return null;
            }
            subRoot = this.getRoot();
            if (subRoot == null) {
                return null;
            }
        } else {
            subRoot = this.absoluteRoot();
        }
        return ((AssociativeTrieNode)subRoot).get(addr);
    }

    public AssociativeTrieNode<K, V> getAddedNode(K addr) {
        return (AssociativeTrieNode)super.getAddedNode(addr);
    }

    public AssociativeTrieNode<K, V> getNode(K addr) {
        return (AssociativeTrieNode)super.getNode(addr);
    }

    AssociativeTrieNode<K, V> smallestElementContainingBounds(K addr) {
        return (AssociativeTrieNode)super.smallestElementContainingBounds(addr);
    }

    public AssociativeTrieNode<K, V> removeElementsContainedBy(K addr) {
        return (AssociativeTrieNode)super.removeElementsContainedBy(addr);
    }

    public AssociativeTrieNode<K, V> elementsContainedBy(K addr) {
        return (AssociativeTrieNode)super.elementsContainedBy(addr);
    }

    public AssociativeTrieNode<K, V> elementsContaining(K addr) {
        return (AssociativeTrieNode)super.elementsContaining(addr);
    }

    public AssociativeTrieNode<K, V> longestPrefixMatchNode(K addr) {
        return (AssociativeTrieNode)super.longestPrefixMatchNode(addr);
    }

    public AddressTrieMap<K, V> asMap() {
        AddressTrieMap<K, V> map = this.map;
        if (map == null) {
            map = new AddressTrieMap(this);
        }
        return map;
    }

    AssociativeAddressTrie<K, V> elementsContainedByToSubTrie(K addr) {
        return (AssociativeAddressTrie)super.elementsContainedByToSubTrie(addr);
    }

    AssociativeAddressTrie<K, V> elementsContainingToTrie(K addr) {
        return (AssociativeAddressTrie)super.elementsContainingToTrie(addr);
    }

    protected abstract AssociativeAddressTrie<K, V> createNew(AddressTrie.AddressBounds<K> var1);

    protected abstract AssociativeAddressTrie<K, V> createSubTrie(AddressTrie.AddressBounds<K> var1);

    @Override
    public Iterator<? extends AssociativeTrieNode<K, V>> nodeIterator(boolean forward) {
        return super.nodeIterator(forward);
    }

    @Override
    public Iterator<? extends AssociativeTrieNode<K, V>> allNodeIterator(boolean forward) {
        return super.allNodeIterator(forward);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<? extends AssociativeTrieNode<K, V>, K, C> blockSizeCachingAllNodeIterator() {
        return super.blockSizeCachingAllNodeIterator();
    }

    @Override
    public Iterator<? extends AssociativeTrieNode<K, V>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public Iterator<? extends AssociativeTrieNode<K, V>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<? extends AssociativeTrieNode<K, V>, K, C> containingFirstIterator(boolean lowerSubNodeFirst) {
        return super.containingFirstIterator(lowerSubNodeFirst);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<? extends AssociativeTrieNode<K, V>, K, C> containingFirstAllNodeIterator(boolean lowerSubNodeFirst) {
        return super.containingFirstAllNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public Iterator<? extends AssociativeTrieNode<K, V>> containedFirstIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<? extends AssociativeTrieNode<K, V>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    Spliterator<? extends AssociativeTrieNode<K, V>> nodeSpliterator(boolean forward, boolean addedNodesOnly) {
        return super.nodeSpliterator(forward, addedNodesOnly);
    }

    @Override
    public Spliterator<? extends AssociativeTrieNode<K, V>> nodeSpliterator(boolean forward) {
        return this.nodeSpliterator(forward, true);
    }

    @Override
    public Spliterator<? extends AssociativeTrieNode<K, V>> allNodeSpliterator(boolean forward) {
        return this.nodeSpliterator(forward, false);
    }

    public AssociativeTrieNode<K, V> firstNode() {
        return (AssociativeTrieNode)super.firstNode();
    }

    public AssociativeTrieNode<K, V> lastNode() {
        return (AssociativeTrieNode)super.lastNode();
    }

    public AssociativeTrieNode<K, V> firstAddedNode() {
        return (AssociativeTrieNode)super.firstAddedNode();
    }

    public AssociativeTrieNode<K, V> lastAddedNode() {
        return (AssociativeTrieNode)super.lastAddedNode();
    }

    public AssociativeTrieNode<K, V> lowerAddedNode(K addr) {
        return (AssociativeTrieNode)super.lowerAddedNode(addr);
    }

    public AssociativeTrieNode<K, V> floorAddedNode(K addr) {
        return (AssociativeTrieNode)super.floorAddedNode(addr);
    }

    public AssociativeTrieNode<K, V> higherAddedNode(K addr) {
        return (AssociativeTrieNode)super.higherAddedNode(addr);
    }

    public AssociativeTrieNode<K, V> ceilingAddedNode(K addr) {
        return (AssociativeTrieNode)super.ceilingAddedNode(addr);
    }

    @Override
    public AssociativeAddressTrie<K, V> clone() {
        AssociativeAddressTrie result = (AssociativeAddressTrie)super.clone();
        result.map = null;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AssociativeAddressTrie && super.equals(o);
    }

    protected static class SubNodesMappingAssociative<E extends Address, V>
    extends AddressTrie.SubNodesMapping<E, SubNodesMappingAssociative<E, V>> {
        V value;

        protected SubNodesMappingAssociative() {
        }

        @Override
        Object getUnderlyingValue() {
            return this.value;
        }
    }

    static enum REMAP_ACTION {
        DO_NOTHING,
        REMOVE_NODE;

    }

    public static abstract class AssociativeTrieNode<K extends Address, V>
    extends AddressTrie.TrieNode<K>
    implements Map.Entry<K, V>,
    AddressTrieOps.AssociativeAddressTrieOps<K, V> {
        private static final long serialVersionUID = 1L;
        private V value;

        protected AssociativeTrieNode(K item) {
            super(item);
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V result = this.getValue();
            this.value = value;
            return result;
        }

        public void clearValue() {
            this.value = null;
        }

        @Override
        public AssociativeTrieNode<K, V> getUpperSubNode() {
            return (AssociativeTrieNode)super.getUpperSubNode();
        }

        @Override
        public AssociativeTrieNode<K, V> getLowerSubNode() {
            return (AssociativeTrieNode)super.getLowerSubNode();
        }

        @Override
        public AssociativeTrieNode<K, V> getParent() {
            return (AssociativeTrieNode)super.getParent();
        }

        @Override
        public V get(K addr) {
            addr = AbstractTree.checkBlockOrAddress(addr, true);
            AddressTrie.OpResult<K> result = new AddressTrie.OpResult<K>(addr, AddressTrie.Operation.LOOKUP);
            this.matchBits(result);
            AssociativeTrieNode node = (AssociativeTrieNode)result.existingNode;
            return node == null ? null : (V)node.getValue();
        }

        @Override
        public int hashCode() {
            if (this.value == null) {
                return super.hashCode();
            }
            return super.hashCode() ^ this.value.hashCode();
        }

        public AssociativeAddressTrie<K, V> asNewTrie() {
            return (AssociativeAddressTrie)super.asNewTrie();
        }

        @Override
        public AssociativeTrieNode<K, V> cloneTree() {
            return (AssociativeTrieNode)super.cloneTree();
        }

        @Override
        public AssociativeTrieNode<K, V> clone() {
            return (AssociativeTrieNode)super.clone();
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof AssociativeTrieNode) {
                AssociativeTrieNode other = (AssociativeTrieNode)o;
                return super.equals(o) && Objects.equals(this.getValue(), other.getValue());
            }
            return false;
        }

        @Override
        public Iterator<? extends AssociativeTrieNode<K, V>> nodeIterator(boolean forward) {
            return super.nodeIterator(forward);
        }

        @Override
        public Iterator<? extends AssociativeTrieNode<K, V>> allNodeIterator(boolean forward) {
            return super.allNodeIterator(forward);
        }

        @Override
        public Iterator<? extends AssociativeTrieNode<K, V>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public Iterator<? extends AssociativeTrieNode<K, V>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<? extends AssociativeTrieNode<K, V>, K, C> blockSizeCachingAllNodeIterator() {
            return super.blockSizeCachingAllNodeIterator();
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<? extends AssociativeTrieNode<K, V>, K, C> containingFirstIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<? extends AssociativeTrieNode<K, V>, K, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<? extends AssociativeTrieNode<K, V>> containedFirstIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<? extends AssociativeTrieNode<K, V>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Spliterator<? extends AssociativeTrieNode<K, V>> nodeSpliterator(boolean forward) {
            return this.nodeSpliterator(forward, true);
        }

        @Override
        public Spliterator<? extends AssociativeTrieNode<K, V>> allNodeSpliterator(boolean forward) {
            return this.nodeSpliterator(forward, false);
        }

        @Override
        Spliterator<? extends AssociativeTrieNode<K, V>> nodeSpliterator(boolean forward, boolean addedNodesOnly) {
            return super.nodeSpliterator(forward, addedNodesOnly);
        }

        @Override
        public AssociativeTrieNode<K, V> previousAddedNode() {
            return (AssociativeTrieNode)super.previousAddedNode();
        }

        @Override
        public AssociativeTrieNode<K, V> nextAddedNode() {
            return (AssociativeTrieNode)super.nextAddedNode();
        }

        @Override
        public AssociativeTrieNode<K, V> nextNode() {
            return (AssociativeTrieNode)super.nextNode();
        }

        @Override
        public AssociativeTrieNode<K, V> previousNode() {
            return (AssociativeTrieNode)super.previousNode();
        }

        @Override
        public AssociativeTrieNode<K, V> firstNode() {
            return (AssociativeTrieNode)super.firstNode();
        }

        @Override
        public AssociativeTrieNode<K, V> lastNode() {
            return (AssociativeTrieNode)super.lastNode();
        }

        @Override
        public AssociativeTrieNode<K, V> firstAddedNode() {
            return (AssociativeTrieNode)super.firstAddedNode();
        }

        @Override
        public AssociativeTrieNode<K, V> lastAddedNode() {
            return (AssociativeTrieNode)super.lastAddedNode();
        }

        public AssociativeTrieNode<K, V> lowerAddedNode(K addr) {
            return (AssociativeTrieNode)super.lowerAddedNode(addr);
        }

        public AssociativeTrieNode<K, V> floorAddedNode(K addr) {
            return (AssociativeTrieNode)super.floorAddedNode(addr);
        }

        public AssociativeTrieNode<K, V> higherAddedNode(K addr) {
            return (AssociativeTrieNode)super.higherAddedNode(addr);
        }

        public AssociativeTrieNode<K, V> ceilingAddedNode(K addr) {
            return (AssociativeTrieNode)super.ceilingAddedNode(addr);
        }

        public AssociativeTrieNode<K, V> getAddedNode(K addr) {
            return (AssociativeTrieNode)super.getAddedNode(addr);
        }

        public AssociativeTrieNode<K, V> getNode(K addr) {
            return (AssociativeTrieNode)super.getNode(addr);
        }

        public AssociativeTrieNode<K, V> removeElementsContainedBy(K addr) {
            return (AssociativeTrieNode)super.removeElementsContainedBy(addr);
        }

        public AssociativeTrieNode<K, V> elementsContainedBy(K addr) {
            return (AssociativeTrieNode)super.elementsContainedBy(addr);
        }

        public AssociativeTrieNode<K, V> elementsContaining(K addr) {
            return (AssociativeTrieNode)super.elementsContaining(addr);
        }

        public AssociativeTrieNode<K, V> longestPrefixMatchNode(K addr) {
            return (AssociativeTrieNode)super.longestPrefixMatchNode(addr);
        }

        @Override
        void matchedInserted(AddressTrie.OpResult<K> result) {
            super.matchedInserted(result);
            result.existingValue = this.getValue();
            this.setValue(result.newValue);
        }

        @Override
        void added(AddressTrie.OpResult<K> result) {
            super.added(result);
            this.setValue(result.newValue);
        }

        @Override
        boolean remap(AddressTrie.OpResult<K> result, boolean isMatch) {
            Function<?, ?> remapper = result.remapper;
            BinaryTreeNode.ChangeTracker.Change change = this.changeTracker.getCurrent();
            Object existingValue = isMatch ? (Object)this.getValue() : null;
            result.existingValue = existingValue;
            Object newValue = remapper.apply(existingValue);
            if (newValue == REMAP_ACTION.DO_NOTHING) {
                return false;
            }
            if (newValue == REMAP_ACTION.REMOVE_NODE) {
                if (isMatch) {
                    this.changeTracker.changedSince(change);
                    this.clearValue();
                    this.remove(result);
                }
                return false;
            }
            if (isMatch) {
                if (newValue != existingValue) {
                    this.changeTracker.changedSince(change);
                    result.newValue = newValue;
                    return true;
                }
                return false;
            }
            result.newValue = newValue;
            return true;
        }

        @Override
        void removed() {
            super.removed();
            this.clearValue();
        }

        @Override
        protected void replaceThisRoot(BinaryTreeNode<K> replacement) {
            super.replaceThisRoot(replacement);
            if (replacement == null) {
                this.setValue(null);
            } else {
                this.setValue(((AssociativeTrieNode)replacement).getValue());
            }
        }

        @Override
        public String toString() {
            return AssociativeTrieNode.toNodeString(new StringBuilder(80), this.isAdded(), (Address)this.getKey(), this.getValue()).toString();
        }
    }
}

