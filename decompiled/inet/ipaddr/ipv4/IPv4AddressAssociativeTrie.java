/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.AssociativeAddedTree;
import inet.ipaddr.format.util.AssociativeAddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressTrie;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;

public class IPv4AddressAssociativeTrie<V>
extends AssociativeAddressTrie<IPv4Address, V> {
    private static final long serialVersionUID = 1L;
    private static final IPv4Address INIT_ROOT = IPv4AddressTrie.INIT_ROOT;

    public IPv4AddressAssociativeTrie() {
        super(new IPv4AssociativeTrieNode());
    }

    protected IPv4AddressAssociativeTrie(AddressTrie.AddressBounds<IPv4Address> bounds) {
        super(new IPv4AssociativeTrieNode(), bounds);
    }

    protected IPv4AddressAssociativeTrie(IPv4AssociativeTrieNode<V> root, AddressTrie.AddressBounds<IPv4Address> bounds) {
        super(root, bounds);
    }

    @Override
    public IPv4AssociativeTrieNode<V> getRoot() {
        return (IPv4AssociativeTrieNode)super.getRoot();
    }

    @Override
    protected IPv4AssociativeTrieNode<V> absoluteRoot() {
        return (IPv4AssociativeTrieNode)super.absoluteRoot();
    }

    @Override
    protected IPv4AddressAssociativeTrie<V> createNew(AddressTrie.AddressBounds<IPv4Address> bounds) {
        return new IPv4AddressAssociativeTrie<V>(bounds);
    }

    @Override
    protected IPv4AddressAssociativeTrie<V> createSubTrie(AddressTrie.AddressBounds<IPv4Address> bounds) {
        return new IPv4AddressAssociativeTrie<V>(this.absoluteRoot(), bounds);
    }

    @Override
    public IPv4AssociativeTrieNode<V> removeElementsContainedBy(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.removeElementsContainedBy(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> elementsContainedBy(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.elementsContainedBy(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> elementsContaining(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.elementsContaining(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> longestPrefixMatchNode(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.longestPrefixMatchNode(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> getAddedNode(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.getAddedNode(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> getNode(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.getNode(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> addNode(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.addNode(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> addTrie(AddressTrie.TrieNode<IPv4Address> trie) {
        return (IPv4AssociativeTrieNode)super.addTrie(trie);
    }

    @Override
    public Iterator<IPv4AssociativeTrieNode<V>> nodeIterator(boolean forward) {
        return super.nodeIterator(forward);
    }

    @Override
    public Iterator<IPv4AssociativeTrieNode<V>> allNodeIterator(boolean forward) {
        return super.allNodeIterator(forward);
    }

    @Override
    public Iterator<IPv4AssociativeTrieNode<V>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public Iterator<IPv4AssociativeTrieNode<V>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv4AssociativeTrieNode<V>, IPv4Address, C> blockSizeCachingAllNodeIterator() {
        return super.blockSizeCachingAllNodeIterator();
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv4AssociativeTrieNode<V>, IPv4Address, C> containingFirstIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv4AssociativeTrieNode<V>, IPv4Address, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<IPv4AssociativeTrieNode<V>> containedFirstIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<IPv4AssociativeTrieNode<V>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Spliterator<IPv4AssociativeTrieNode<V>> nodeSpliterator(boolean forward) {
        return super.nodeSpliterator(forward);
    }

    @Override
    public Spliterator<IPv4AssociativeTrieNode<V>> allNodeSpliterator(boolean forward) {
        return super.allNodeSpliterator(forward);
    }

    @Override
    public IPv4AssociativeTrieNode<V> lowerAddedNode(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.lowerAddedNode(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> floorAddedNode(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.floorAddedNode(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> higherAddedNode(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.higherAddedNode(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> ceilingAddedNode(IPv4Address addr) {
        return (IPv4AssociativeTrieNode)super.ceilingAddedNode(addr);
    }

    @Override
    public IPv4AssociativeTrieNode<V> firstNode() {
        return (IPv4AssociativeTrieNode)super.firstNode();
    }

    @Override
    public IPv4AssociativeTrieNode<V> lastNode() {
        return (IPv4AssociativeTrieNode)super.lastNode();
    }

    @Override
    public IPv4AssociativeTrieNode<V> firstAddedNode() {
        return (IPv4AssociativeTrieNode)super.firstAddedNode();
    }

    @Override
    public IPv4AssociativeTrieNode<V> lastAddedNode() {
        return (IPv4AssociativeTrieNode)super.lastAddedNode();
    }

    public IPv4AssociativeTrieNode<V> putNode(IPv4Address addr, V value) {
        return (IPv4AssociativeTrieNode)super.putNode(addr, value);
    }

    public IPv4AssociativeTrieNode<V> putTrie(AssociativeAddressTrie.AssociativeTrieNode<IPv4Address, V> trie) {
        return (IPv4AssociativeTrieNode)super.putTrie(trie);
    }

    public IPv4AssociativeTrieNode<V> remap(IPv4Address addr, Function<? super V, ? extends V> remapper) {
        return (IPv4AssociativeTrieNode)super.remap(addr, remapper);
    }

    public IPv4AssociativeTrieNode<V> remapIfAbsent(IPv4Address addr, Supplier<? extends V> remapper, boolean insertNull) {
        return (IPv4AssociativeTrieNode)super.remapIfAbsent(addr, remapper, insertNull);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IPv4AddressAssociativeTrie && super.equals(o);
    }

    @Override
    public IPv4AddressAssociativeTrie<V> clone() {
        return (IPv4AddressAssociativeTrie)super.clone();
    }

    @Override
    public AssociativeAddedTree<IPv4Address, V> constructAddedNodesTree() {
        IPv4AddressAssociativeTrie trie = new IPv4AddressAssociativeTrie();
        this.contructAssociativeAddedTree(trie);
        return new AssociativeAddedTree(trie);
    }

    @Override
    public String toAddedNodesTreeString() {
        IPv4AddressAssociativeTrie trie = new IPv4AddressAssociativeTrie();
        this.contructAssociativeAddedTree(trie);
        return IPv4AddressAssociativeTrie.toAddedNodesTreeString(trie);
    }

    public static class IPv4AssociativeTrieNode<V>
    extends AssociativeAddressTrie.AssociativeTrieNode<IPv4Address, V> {
        private static final long serialVersionUID = 1L;

        protected IPv4AssociativeTrieNode(IPv4Address addr) {
            super(addr);
        }

        public IPv4AssociativeTrieNode() {
            super(INIT_ROOT);
        }

        @Override
        protected void replaceThisRoot(BinaryTreeNode<IPv4Address> replacement) {
            super.replaceThisRoot(replacement);
            if (!FREEZE_ROOT && replacement == null) {
                this.setKey(INIT_ROOT);
            }
        }

        @Override
        public IPv4AssociativeTrieNode<V> getUpperSubNode() {
            return (IPv4AssociativeTrieNode)super.getUpperSubNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> getLowerSubNode() {
            return (IPv4AssociativeTrieNode)super.getLowerSubNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> getParent() {
            return (IPv4AssociativeTrieNode)super.getParent();
        }

        @Override
        protected IPv4AssociativeTrieNode<V> createNewImpl(IPv4Address addr) {
            return new IPv4AssociativeTrieNode<V>(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> removeElementsContainedBy(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.removeElementsContainedBy(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> elementsContainedBy(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.elementsContainedBy(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> elementsContaining(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.elementsContaining(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> longestPrefixMatchNode(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.longestPrefixMatchNode(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> getAddedNode(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.getAddedNode(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> getNode(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.getNode(addr);
        }

        @Override
        public Iterator<IPv4AssociativeTrieNode<V>> nodeIterator(boolean forward) {
            return super.nodeIterator(forward);
        }

        @Override
        public Iterator<IPv4AssociativeTrieNode<V>> allNodeIterator(boolean forward) {
            return super.allNodeIterator(forward);
        }

        @Override
        public Iterator<IPv4AssociativeTrieNode<V>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public Iterator<IPv4AssociativeTrieNode<V>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv4AssociativeTrieNode<V>, IPv4Address, C> blockSizeCachingAllNodeIterator() {
            return super.blockSizeCachingAllNodeIterator();
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv4AssociativeTrieNode<V>, IPv4Address, C> containingFirstIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv4AssociativeTrieNode<V>, IPv4Address, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<IPv4AssociativeTrieNode<V>> containedFirstIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<IPv4AssociativeTrieNode<V>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Spliterator<IPv4AssociativeTrieNode<V>> nodeSpliterator(boolean forward) {
            return super.nodeSpliterator(forward);
        }

        @Override
        public Spliterator<IPv4AssociativeTrieNode<V>> allNodeSpliterator(boolean forward) {
            return super.allNodeSpliterator(forward);
        }

        @Override
        public IPv4AssociativeTrieNode<V> previousAddedNode() {
            return (IPv4AssociativeTrieNode)super.previousAddedNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> nextAddedNode() {
            return (IPv4AssociativeTrieNode)super.nextAddedNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> nextNode() {
            return (IPv4AssociativeTrieNode)super.nextNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> previousNode() {
            return (IPv4AssociativeTrieNode)super.previousNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> lowerAddedNode(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.lowerAddedNode(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> floorAddedNode(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.floorAddedNode(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> higherAddedNode(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.higherAddedNode(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> ceilingAddedNode(IPv4Address addr) {
            return (IPv4AssociativeTrieNode)super.ceilingAddedNode(addr);
        }

        @Override
        public IPv4AssociativeTrieNode<V> firstNode() {
            return (IPv4AssociativeTrieNode)super.firstNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> lastNode() {
            return (IPv4AssociativeTrieNode)super.lastNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> firstAddedNode() {
            return (IPv4AssociativeTrieNode)super.firstAddedNode();
        }

        @Override
        public IPv4AssociativeTrieNode<V> lastAddedNode() {
            return (IPv4AssociativeTrieNode)super.lastAddedNode();
        }

        @Override
        protected IPv4AddressAssociativeTrie<V> createNewTree() {
            return new IPv4AddressAssociativeTrie();
        }

        @Override
        public IPv4AddressAssociativeTrie<V> asNewTrie() {
            return (IPv4AddressAssociativeTrie)super.asNewTrie();
        }

        @Override
        public IPv4AssociativeTrieNode<V> cloneTree() {
            return (IPv4AssociativeTrieNode)super.cloneTree();
        }

        @Override
        public IPv4AssociativeTrieNode<V> clone() {
            return (IPv4AssociativeTrieNode)super.clone();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof IPv4AssociativeTrieNode && super.equals(o);
        }
    }
}

