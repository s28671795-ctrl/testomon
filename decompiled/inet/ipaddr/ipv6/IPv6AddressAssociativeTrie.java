/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv6;

import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.AssociativeAddedTree;
import inet.ipaddr.format.util.AssociativeAddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressTrie;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;

public class IPv6AddressAssociativeTrie<V>
extends AssociativeAddressTrie<IPv6Address, V> {
    private static final long serialVersionUID = 1L;
    private static final IPv6Address INIT_ROOT = IPv6AddressTrie.INIT_ROOT;

    public IPv6AddressAssociativeTrie() {
        super(new IPv6AssociativeTrieNode());
    }

    protected IPv6AddressAssociativeTrie(AddressTrie.AddressBounds<IPv6Address> bounds) {
        super(new IPv6AssociativeTrieNode(), bounds);
    }

    protected IPv6AddressAssociativeTrie(IPv6AssociativeTrieNode<V> root, AddressTrie.AddressBounds<IPv6Address> bounds) {
        super(root, bounds);
    }

    @Override
    public IPv6AssociativeTrieNode<V> getRoot() {
        return (IPv6AssociativeTrieNode)super.getRoot();
    }

    @Override
    protected IPv6AssociativeTrieNode<V> absoluteRoot() {
        return (IPv6AssociativeTrieNode)super.absoluteRoot();
    }

    @Override
    protected IPv6AddressAssociativeTrie<V> createNew(AddressTrie.AddressBounds<IPv6Address> bounds) {
        return new IPv6AddressAssociativeTrie<V>(bounds);
    }

    @Override
    protected IPv6AddressAssociativeTrie<V> createSubTrie(AddressTrie.AddressBounds<IPv6Address> bounds) {
        return new IPv6AddressAssociativeTrie<V>(this.absoluteRoot(), bounds);
    }

    @Override
    public IPv6AssociativeTrieNode<V> removeElementsContainedBy(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.removeElementsContainedBy(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> elementsContainedBy(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.elementsContainedBy(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> elementsContaining(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.elementsContaining(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> longestPrefixMatchNode(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.longestPrefixMatchNode(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> getAddedNode(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.getAddedNode(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> getNode(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.getNode(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> addNode(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.addNode(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> addTrie(AddressTrie.TrieNode<IPv6Address> trie) {
        return (IPv6AssociativeTrieNode)super.addTrie(trie);
    }

    @Override
    public Iterator<IPv6AssociativeTrieNode<V>> nodeIterator(boolean forward) {
        return super.nodeIterator(forward);
    }

    @Override
    public Iterator<IPv6AssociativeTrieNode<V>> allNodeIterator(boolean forward) {
        return super.allNodeIterator(forward);
    }

    @Override
    public Iterator<IPv6AssociativeTrieNode<V>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public Iterator<IPv6AssociativeTrieNode<V>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv6AssociativeTrieNode<V>, IPv6Address, C> blockSizeCachingAllNodeIterator() {
        return super.blockSizeCachingAllNodeIterator();
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv6AssociativeTrieNode<V>, IPv6Address, C> containingFirstIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv6AssociativeTrieNode<V>, IPv6Address, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<IPv6AssociativeTrieNode<V>> containedFirstIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<IPv6AssociativeTrieNode<V>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Spliterator<IPv6AssociativeTrieNode<V>> nodeSpliterator(boolean forward) {
        return super.nodeSpliterator(forward);
    }

    @Override
    public Spliterator<IPv6AssociativeTrieNode<V>> allNodeSpliterator(boolean forward) {
        return super.allNodeSpliterator(forward);
    }

    @Override
    public IPv6AssociativeTrieNode<V> lowerAddedNode(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.lowerAddedNode(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> floorAddedNode(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.floorAddedNode(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> higherAddedNode(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.higherAddedNode(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> ceilingAddedNode(IPv6Address addr) {
        return (IPv6AssociativeTrieNode)super.ceilingAddedNode(addr);
    }

    @Override
    public IPv6AssociativeTrieNode<V> firstNode() {
        return (IPv6AssociativeTrieNode)super.firstNode();
    }

    @Override
    public IPv6AssociativeTrieNode<V> lastNode() {
        return (IPv6AssociativeTrieNode)super.lastNode();
    }

    @Override
    public IPv6AssociativeTrieNode<V> firstAddedNode() {
        return (IPv6AssociativeTrieNode)super.firstAddedNode();
    }

    @Override
    public IPv6AssociativeTrieNode<V> lastAddedNode() {
        return (IPv6AssociativeTrieNode)super.lastAddedNode();
    }

    public IPv6AssociativeTrieNode<V> putNode(IPv6Address addr, V value) {
        return (IPv6AssociativeTrieNode)super.putNode(addr, value);
    }

    public IPv6AssociativeTrieNode<V> putTrie(AssociativeAddressTrie.AssociativeTrieNode<IPv6Address, V> trie) {
        return (IPv6AssociativeTrieNode)super.putTrie(trie);
    }

    public IPv6AssociativeTrieNode<V> remap(IPv6Address addr, Function<? super V, ? extends V> remapper) {
        return (IPv6AssociativeTrieNode)super.remap(addr, remapper);
    }

    public IPv6AssociativeTrieNode<V> remapIfAbsent(IPv6Address addr, Supplier<? extends V> remapper, boolean insertNull) {
        return (IPv6AssociativeTrieNode)super.remapIfAbsent(addr, remapper, insertNull);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IPv6AddressAssociativeTrie && super.equals(o);
    }

    @Override
    public IPv6AddressAssociativeTrie<V> clone() {
        return (IPv6AddressAssociativeTrie)super.clone();
    }

    @Override
    public AssociativeAddedTree<IPv6Address, V> constructAddedNodesTree() {
        IPv6AddressAssociativeTrie trie = new IPv6AddressAssociativeTrie();
        this.contructAssociativeAddedTree(trie);
        return new AssociativeAddedTree(trie);
    }

    @Override
    public String toAddedNodesTreeString() {
        IPv6AddressAssociativeTrie trie = new IPv6AddressAssociativeTrie();
        this.contructAssociativeAddedTree(trie);
        return IPv6AddressAssociativeTrie.toAddedNodesTreeString(trie);
    }

    public static class IPv6AssociativeTrieNode<V>
    extends AssociativeAddressTrie.AssociativeTrieNode<IPv6Address, V> {
        private static final long serialVersionUID = 1L;

        protected IPv6AssociativeTrieNode(IPv6Address addr) {
            super(addr);
        }

        public IPv6AssociativeTrieNode() {
            super(INIT_ROOT);
        }

        @Override
        protected void replaceThisRoot(BinaryTreeNode<IPv6Address> replacement) {
            super.replaceThisRoot(replacement);
            if (!FREEZE_ROOT && replacement == null) {
                this.setKey(INIT_ROOT);
            }
        }

        @Override
        public IPv6AssociativeTrieNode<V> getUpperSubNode() {
            return (IPv6AssociativeTrieNode)super.getUpperSubNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> getLowerSubNode() {
            return (IPv6AssociativeTrieNode)super.getLowerSubNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> getParent() {
            return (IPv6AssociativeTrieNode)super.getParent();
        }

        @Override
        protected IPv6AssociativeTrieNode<V> createNewImpl(IPv6Address addr) {
            return new IPv6AssociativeTrieNode<V>(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> removeElementsContainedBy(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.removeElementsContainedBy(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> elementsContainedBy(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.elementsContainedBy(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> elementsContaining(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.elementsContaining(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> longestPrefixMatchNode(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.longestPrefixMatchNode(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> getAddedNode(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.getAddedNode(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> getNode(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.getNode(addr);
        }

        @Override
        public Iterator<IPv6AssociativeTrieNode<V>> nodeIterator(boolean forward) {
            return super.nodeIterator(forward);
        }

        @Override
        public Iterator<IPv6AssociativeTrieNode<V>> allNodeIterator(boolean forward) {
            return super.allNodeIterator(forward);
        }

        @Override
        public Iterator<IPv6AssociativeTrieNode<V>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public Iterator<IPv6AssociativeTrieNode<V>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv6AssociativeTrieNode<V>, IPv6Address, C> blockSizeCachingAllNodeIterator() {
            return super.blockSizeCachingAllNodeIterator();
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv6AssociativeTrieNode<V>, IPv6Address, C> containingFirstIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv6AssociativeTrieNode<V>, IPv6Address, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<IPv6AssociativeTrieNode<V>> containedFirstIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<IPv6AssociativeTrieNode<V>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Spliterator<IPv6AssociativeTrieNode<V>> nodeSpliterator(boolean forward) {
            return super.nodeSpliterator(forward);
        }

        @Override
        public Spliterator<IPv6AssociativeTrieNode<V>> allNodeSpliterator(boolean forward) {
            return super.allNodeSpliterator(forward);
        }

        @Override
        public IPv6AssociativeTrieNode<V> previousAddedNode() {
            return (IPv6AssociativeTrieNode)super.previousAddedNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> nextAddedNode() {
            return (IPv6AssociativeTrieNode)super.nextAddedNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> nextNode() {
            return (IPv6AssociativeTrieNode)super.nextNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> previousNode() {
            return (IPv6AssociativeTrieNode)super.previousNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> lowerAddedNode(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.lowerAddedNode(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> floorAddedNode(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.floorAddedNode(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> higherAddedNode(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.higherAddedNode(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> ceilingAddedNode(IPv6Address addr) {
            return (IPv6AssociativeTrieNode)super.ceilingAddedNode(addr);
        }

        @Override
        public IPv6AssociativeTrieNode<V> firstNode() {
            return (IPv6AssociativeTrieNode)super.firstNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> lastNode() {
            return (IPv6AssociativeTrieNode)super.lastNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> firstAddedNode() {
            return (IPv6AssociativeTrieNode)super.firstAddedNode();
        }

        @Override
        public IPv6AssociativeTrieNode<V> lastAddedNode() {
            return (IPv6AssociativeTrieNode)super.lastAddedNode();
        }

        @Override
        protected IPv6AddressAssociativeTrie<V> createNewTree() {
            return new IPv6AddressAssociativeTrie();
        }

        @Override
        public IPv6AddressAssociativeTrie<V> asNewTrie() {
            return (IPv6AddressAssociativeTrie)super.asNewTrie();
        }

        @Override
        public IPv6AssociativeTrieNode<V> cloneTree() {
            return (IPv6AssociativeTrieNode)super.cloneTree();
        }

        @Override
        public IPv6AssociativeTrieNode<V> clone() {
            return (IPv6AssociativeTrieNode)super.clone();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof IPv6AssociativeTrieNode && super.equals(o);
        }
    }
}

