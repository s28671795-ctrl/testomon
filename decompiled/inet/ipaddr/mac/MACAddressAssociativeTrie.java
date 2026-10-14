/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.mac;

import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.AssociativeAddedTree;
import inet.ipaddr.format.util.AssociativeAddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressTrie;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;

public class MACAddressAssociativeTrie<V>
extends AssociativeAddressTrie<MACAddress, V> {
    private static final long serialVersionUID = 1L;
    private static final MACAddress INIT_ROOT = MACAddressTrie.INIT_ROOT;
    private static final MACAddress INIT_ROOT_EXTENDED = MACAddressTrie.INIT_ROOT_EXTENDED;

    public MACAddressAssociativeTrie() {
        super(new MACAssociativeTrieNode());
    }

    protected MACAddressAssociativeTrie(AddressTrie.AddressBounds<MACAddress> bounds) {
        super(new MACAssociativeTrieNode(), bounds);
    }

    protected MACAddressAssociativeTrie(MACAssociativeTrieNode<V> root, AddressTrie.AddressBounds<MACAddress> bounds) {
        super(root, bounds);
    }

    @Override
    protected void adjustRoot(MACAddress addr) {
        if (this.isEmpty() && addr.getSegmentCount() == 8) {
            ((MACAssociativeTrieNode)this.absoluteRoot()).setExtendedRootKey();
        }
    }

    @Override
    public MACAssociativeTrieNode<V> getRoot() {
        return (MACAssociativeTrieNode)super.getRoot();
    }

    @Override
    protected MACAssociativeTrieNode<V> absoluteRoot() {
        return (MACAssociativeTrieNode)super.absoluteRoot();
    }

    @Override
    protected MACAddressAssociativeTrie<V> createNew(AddressTrie.AddressBounds<MACAddress> bounds) {
        return new MACAddressAssociativeTrie<V>(bounds);
    }

    @Override
    protected MACAddressAssociativeTrie<V> createSubTrie(AddressTrie.AddressBounds<MACAddress> bounds) {
        return new MACAddressAssociativeTrie<V>(this.absoluteRoot(), bounds);
    }

    @Override
    public MACAssociativeTrieNode<V> removeElementsContainedBy(MACAddress addr) {
        return (MACAssociativeTrieNode)super.removeElementsContainedBy(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> elementsContainedBy(MACAddress addr) {
        return (MACAssociativeTrieNode)super.elementsContainedBy(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> elementsContaining(MACAddress addr) {
        return (MACAssociativeTrieNode)super.elementsContaining(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> longestPrefixMatchNode(MACAddress addr) {
        return (MACAssociativeTrieNode)super.longestPrefixMatchNode(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> getAddedNode(MACAddress addr) {
        return (MACAssociativeTrieNode)super.getAddedNode(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> getNode(MACAddress addr) {
        return (MACAssociativeTrieNode)super.getNode(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> addNode(MACAddress addr) {
        return (MACAssociativeTrieNode)super.addNode(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> addTrie(AddressTrie.TrieNode<MACAddress> trie) {
        return (MACAssociativeTrieNode)super.addTrie(trie);
    }

    @Override
    public Iterator<MACAssociativeTrieNode<V>> nodeIterator(boolean forward) {
        return super.nodeIterator(forward);
    }

    @Override
    public Iterator<MACAssociativeTrieNode<V>> allNodeIterator(boolean forward) {
        return super.allNodeIterator(forward);
    }

    @Override
    public Iterator<MACAssociativeTrieNode<V>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public Iterator<MACAssociativeTrieNode<V>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<MACAssociativeTrieNode<V>, MACAddress, C> blockSizeCachingAllNodeIterator() {
        return super.blockSizeCachingAllNodeIterator();
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<MACAssociativeTrieNode<V>, MACAddress, C> containingFirstIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<MACAssociativeTrieNode<V>, MACAddress, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<MACAssociativeTrieNode<V>> containedFirstIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<MACAssociativeTrieNode<V>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Spliterator<MACAssociativeTrieNode<V>> nodeSpliterator(boolean forward) {
        return super.nodeSpliterator(forward);
    }

    @Override
    public Spliterator<MACAssociativeTrieNode<V>> allNodeSpliterator(boolean forward) {
        return super.allNodeSpliterator(forward);
    }

    @Override
    public MACAssociativeTrieNode<V> lowerAddedNode(MACAddress addr) {
        return (MACAssociativeTrieNode)super.lowerAddedNode(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> floorAddedNode(MACAddress addr) {
        return (MACAssociativeTrieNode)super.floorAddedNode(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> higherAddedNode(MACAddress addr) {
        return (MACAssociativeTrieNode)super.higherAddedNode(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> ceilingAddedNode(MACAddress addr) {
        return (MACAssociativeTrieNode)super.ceilingAddedNode(addr);
    }

    @Override
    public MACAssociativeTrieNode<V> firstNode() {
        return (MACAssociativeTrieNode)super.firstNode();
    }

    @Override
    public MACAssociativeTrieNode<V> lastNode() {
        return (MACAssociativeTrieNode)super.lastNode();
    }

    @Override
    public MACAssociativeTrieNode<V> firstAddedNode() {
        return (MACAssociativeTrieNode)super.firstAddedNode();
    }

    @Override
    public MACAssociativeTrieNode<V> lastAddedNode() {
        return (MACAssociativeTrieNode)super.lastAddedNode();
    }

    public MACAssociativeTrieNode<V> putNode(MACAddress addr, V value) {
        return (MACAssociativeTrieNode)super.putNode(addr, value);
    }

    public MACAssociativeTrieNode<V> putTrie(AssociativeAddressTrie.AssociativeTrieNode<MACAddress, V> trie) {
        return (MACAssociativeTrieNode)super.putTrie(trie);
    }

    public MACAssociativeTrieNode<V> remap(MACAddress addr, Function<? super V, ? extends V> remapper) {
        return (MACAssociativeTrieNode)super.remap(addr, remapper);
    }

    public MACAssociativeTrieNode<V> remapIfAbsent(MACAddress addr, Supplier<? extends V> remapper, boolean insertNull) {
        return (MACAssociativeTrieNode)super.remapIfAbsent(addr, remapper, insertNull);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MACAddressAssociativeTrie && super.equals(o);
    }

    @Override
    public MACAddressAssociativeTrie<V> clone() {
        return (MACAddressAssociativeTrie)super.clone();
    }

    @Override
    public AssociativeAddedTree<MACAddress, V> constructAddedNodesTree() {
        MACAddressAssociativeTrie trie = new MACAddressAssociativeTrie();
        this.contructAssociativeAddedTree(trie);
        return new AssociativeAddedTree(trie);
    }

    @Override
    public String toAddedNodesTreeString() {
        MACAddressAssociativeTrie trie = new MACAddressAssociativeTrie();
        this.contructAssociativeAddedTree(trie);
        return MACAddressAssociativeTrie.toAddedNodesTreeString(trie);
    }

    public static class MACAssociativeTrieNode<V>
    extends AssociativeAddressTrie.AssociativeTrieNode<MACAddress, V> {
        private static final long serialVersionUID = 1L;

        protected MACAssociativeTrieNode(MACAddress addr) {
            super(addr);
        }

        public MACAssociativeTrieNode() {
            super(INIT_ROOT);
        }

        @Override
        protected void replaceThisRoot(BinaryTreeNode<MACAddress> replacement) {
            super.replaceThisRoot(replacement);
            if (!FREEZE_ROOT && replacement == null) {
                this.setKey(INIT_ROOT);
            }
        }

        void setExtendedRootKey() {
            this.setKey(INIT_ROOT_EXTENDED);
        }

        @Override
        public MACAssociativeTrieNode<V> getUpperSubNode() {
            return (MACAssociativeTrieNode)super.getUpperSubNode();
        }

        @Override
        public MACAssociativeTrieNode<V> getLowerSubNode() {
            return (MACAssociativeTrieNode)super.getLowerSubNode();
        }

        @Override
        public MACAssociativeTrieNode<V> getParent() {
            return (MACAssociativeTrieNode)super.getParent();
        }

        @Override
        protected MACAssociativeTrieNode<V> createNewImpl(MACAddress addr) {
            return new MACAssociativeTrieNode<V>(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> removeElementsContainedBy(MACAddress addr) {
            return (MACAssociativeTrieNode)super.removeElementsContainedBy(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> elementsContainedBy(MACAddress addr) {
            return (MACAssociativeTrieNode)super.elementsContainedBy(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> elementsContaining(MACAddress addr) {
            return (MACAssociativeTrieNode)super.elementsContaining(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> longestPrefixMatchNode(MACAddress addr) {
            return (MACAssociativeTrieNode)super.longestPrefixMatchNode(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> getAddedNode(MACAddress addr) {
            return (MACAssociativeTrieNode)super.getAddedNode(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> getNode(MACAddress addr) {
            return (MACAssociativeTrieNode)super.getNode(addr);
        }

        @Override
        public Iterator<MACAssociativeTrieNode<V>> nodeIterator(boolean forward) {
            return super.nodeIterator(forward);
        }

        @Override
        public Iterator<MACAssociativeTrieNode<V>> allNodeIterator(boolean forward) {
            return super.allNodeIterator(forward);
        }

        @Override
        public Iterator<MACAssociativeTrieNode<V>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public Iterator<MACAssociativeTrieNode<V>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<MACAssociativeTrieNode<V>, MACAddress, C> blockSizeCachingAllNodeIterator() {
            return super.blockSizeCachingAllNodeIterator();
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<MACAssociativeTrieNode<V>, MACAddress, C> containingFirstIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<MACAssociativeTrieNode<V>, MACAddress, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<MACAssociativeTrieNode<V>> containedFirstIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<MACAssociativeTrieNode<V>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Spliterator<MACAssociativeTrieNode<V>> nodeSpliterator(boolean forward) {
            return super.nodeSpliterator(forward);
        }

        @Override
        public Spliterator<MACAssociativeTrieNode<V>> allNodeSpliterator(boolean forward) {
            return super.allNodeSpliterator(forward);
        }

        @Override
        public MACAssociativeTrieNode<V> previousAddedNode() {
            return (MACAssociativeTrieNode)super.previousAddedNode();
        }

        @Override
        public MACAssociativeTrieNode<V> nextAddedNode() {
            return (MACAssociativeTrieNode)super.nextAddedNode();
        }

        @Override
        public MACAssociativeTrieNode<V> nextNode() {
            return (MACAssociativeTrieNode)super.nextNode();
        }

        @Override
        public MACAssociativeTrieNode<V> previousNode() {
            return (MACAssociativeTrieNode)super.previousNode();
        }

        @Override
        public MACAssociativeTrieNode<V> lowerAddedNode(MACAddress addr) {
            return (MACAssociativeTrieNode)super.lowerAddedNode(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> floorAddedNode(MACAddress addr) {
            return (MACAssociativeTrieNode)super.floorAddedNode(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> higherAddedNode(MACAddress addr) {
            return (MACAssociativeTrieNode)super.higherAddedNode(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> ceilingAddedNode(MACAddress addr) {
            return (MACAssociativeTrieNode)super.ceilingAddedNode(addr);
        }

        @Override
        public MACAssociativeTrieNode<V> firstNode() {
            return (MACAssociativeTrieNode)super.firstNode();
        }

        @Override
        public MACAssociativeTrieNode<V> lastNode() {
            return (MACAssociativeTrieNode)super.lastNode();
        }

        @Override
        public MACAssociativeTrieNode<V> firstAddedNode() {
            return (MACAssociativeTrieNode)super.firstAddedNode();
        }

        @Override
        public MACAssociativeTrieNode<V> lastAddedNode() {
            return (MACAssociativeTrieNode)super.lastAddedNode();
        }

        @Override
        protected MACAddressAssociativeTrie<V> createNewTree() {
            return new MACAddressAssociativeTrie();
        }

        @Override
        public MACAddressAssociativeTrie<V> asNewTrie() {
            return (MACAddressAssociativeTrie)super.asNewTrie();
        }

        @Override
        public MACAssociativeTrieNode<V> cloneTree() {
            return (MACAssociativeTrieNode)super.cloneTree();
        }

        @Override
        public MACAssociativeTrieNode<V> clone() {
            return (MACAssociativeTrieNode)super.clone();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof MACAssociativeTrieNode && super.equals(o);
        }
    }
}

