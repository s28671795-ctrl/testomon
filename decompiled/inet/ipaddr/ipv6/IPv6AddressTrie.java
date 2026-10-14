/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv6;

import inet.ipaddr.IPAddressString;
import inet.ipaddr.format.util.AddedTree;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import inet.ipaddr.ipv6.IPv6Address;
import inet.ipaddr.ipv6.IPv6AddressAssociativeTrie;
import java.util.Iterator;
import java.util.Spliterator;

public class IPv6AddressTrie
extends AddressTrie<IPv6Address> {
    private static final long serialVersionUID = 1L;
    static final IPv6Address INIT_ROOT = new IPAddressString("::/0").getAddress().toIPv6();

    public IPv6AddressTrie() {
        super(new IPv6TrieNode());
    }

    protected IPv6AddressTrie(AddressTrie.AddressBounds<IPv6Address> bounds) {
        super(new IPv6TrieNode(), bounds);
    }

    protected IPv6AddressTrie(IPv6TrieNode root, AddressTrie.AddressBounds<IPv6Address> bounds) {
        super(root, bounds);
    }

    protected IPv6TrieNode absoluteRoot() {
        return (IPv6TrieNode)super.absoluteRoot();
    }

    protected IPv6AddressTrie createNew(AddressTrie.AddressBounds<IPv6Address> bounds) {
        return new IPv6AddressTrie(bounds);
    }

    protected IPv6AddressTrie createSubTrie(AddressTrie.AddressBounds<IPv6Address> bounds) {
        return new IPv6AddressTrie(this.absoluteRoot(), bounds);
    }

    @Override
    public IPv6TrieNode getRoot() {
        return (IPv6TrieNode)super.getRoot();
    }

    public IPv6TrieNode removeElementsContainedBy(IPv6Address addr) {
        return (IPv6TrieNode)super.removeElementsContainedBy(addr);
    }

    public IPv6TrieNode elementsContainedBy(IPv6Address addr) {
        return (IPv6TrieNode)super.elementsContainedBy(addr);
    }

    public IPv6TrieNode elementsContaining(IPv6Address addr) {
        return (IPv6TrieNode)super.elementsContaining(addr);
    }

    public IPv6TrieNode longestPrefixMatchNode(IPv6Address addr) {
        return (IPv6TrieNode)super.longestPrefixMatchNode(addr);
    }

    public IPv6TrieNode getAddedNode(IPv6Address addr) {
        return (IPv6TrieNode)super.getAddedNode(addr);
    }

    public IPv6TrieNode getNode(IPv6Address addr) {
        return (IPv6TrieNode)super.getNode(addr);
    }

    public IPv6TrieNode addNode(IPv6Address addr) {
        return (IPv6TrieNode)super.addNode(addr);
    }

    public IPv6TrieNode addTrie(AddressTrie.TrieNode<IPv6Address> trie) {
        return (IPv6TrieNode)super.addTrie(trie);
    }

    @Override
    public Iterator<IPv6TrieNode> nodeIterator(boolean forward) {
        return super.nodeIterator(forward);
    }

    @Override
    public Iterator<IPv6TrieNode> allNodeIterator(boolean forward) {
        return super.allNodeIterator(forward);
    }

    @Override
    public Iterator<IPv6TrieNode> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public Iterator<IPv6TrieNode> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv6TrieNode, IPv6Address, C> blockSizeCachingAllNodeIterator() {
        return super.blockSizeCachingAllNodeIterator();
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv6TrieNode, IPv6Address, C> containingFirstIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv6TrieNode, IPv6Address, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<IPv6TrieNode> containedFirstIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<IPv6TrieNode> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Spliterator<IPv6TrieNode> nodeSpliterator(boolean forward) {
        return super.nodeSpliterator(forward);
    }

    @Override
    public Spliterator<IPv6TrieNode> allNodeSpliterator(boolean forward) {
        return super.allNodeSpliterator(forward);
    }

    public IPv6TrieNode lowerAddedNode(IPv6Address addr) {
        return (IPv6TrieNode)super.lowerAddedNode(addr);
    }

    public IPv6TrieNode floorAddedNode(IPv6Address addr) {
        return (IPv6TrieNode)super.floorAddedNode(addr);
    }

    public IPv6TrieNode higherAddedNode(IPv6Address addr) {
        return (IPv6TrieNode)super.higherAddedNode(addr);
    }

    public IPv6TrieNode ceilingAddedNode(IPv6Address addr) {
        return (IPv6TrieNode)super.ceilingAddedNode(addr);
    }

    public IPv6TrieNode firstNode() {
        return (IPv6TrieNode)super.firstNode();
    }

    public IPv6TrieNode lastNode() {
        return (IPv6TrieNode)super.lastNode();
    }

    public IPv6TrieNode firstAddedNode() {
        return (IPv6TrieNode)super.firstAddedNode();
    }

    public IPv6TrieNode lastAddedNode() {
        return (IPv6TrieNode)super.lastAddedNode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IPv6AddressTrie && super.equals(o);
    }

    @Override
    public IPv6AddressTrie clone() {
        return (IPv6AddressTrie)super.clone();
    }

    public AddedTree<IPv6Address> constructAddedNodesTree() {
        IPv6AddressAssociativeTrie trie = new IPv6AddressAssociativeTrie();
        this.contructAddedTree(trie);
        return new AddedTree<IPv6Address>(trie);
    }

    @Override
    public String toAddedNodesTreeString() {
        IPv6AddressAssociativeTrie trie = new IPv6AddressAssociativeTrie();
        this.contructAddedTree(trie);
        return IPv6AddressTrie.toAddedNodesTreeString(trie);
    }

    public static class IPv6TrieNode
    extends AddressTrie.TrieNode<IPv6Address> {
        private static final long serialVersionUID = 1L;

        protected IPv6TrieNode(IPv6Address addr) {
            super(addr);
        }

        public IPv6TrieNode() {
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
        public IPv6TrieNode getUpperSubNode() {
            return (IPv6TrieNode)super.getUpperSubNode();
        }

        @Override
        public IPv6TrieNode getLowerSubNode() {
            return (IPv6TrieNode)super.getLowerSubNode();
        }

        @Override
        public IPv6TrieNode getParent() {
            return (IPv6TrieNode)super.getParent();
        }

        protected IPv6TrieNode createNewImpl(IPv6Address addr) {
            return new IPv6TrieNode(addr);
        }

        public IPv6TrieNode removeElementsContainedBy(IPv6Address addr) {
            return (IPv6TrieNode)super.removeElementsContainedBy(addr);
        }

        public IPv6TrieNode elementsContainedBy(IPv6Address addr) {
            return (IPv6TrieNode)super.elementsContainedBy(addr);
        }

        public IPv6TrieNode elementsContaining(IPv6Address addr) {
            return (IPv6TrieNode)super.elementsContaining(addr);
        }

        public IPv6TrieNode longestPrefixMatchNode(IPv6Address addr) {
            return (IPv6TrieNode)super.longestPrefixMatchNode(addr);
        }

        public IPv6TrieNode getAddedNode(IPv6Address addr) {
            return (IPv6TrieNode)super.getAddedNode(addr);
        }

        public IPv6TrieNode getNode(IPv6Address addr) {
            return (IPv6TrieNode)super.getNode(addr);
        }

        @Override
        public Iterator<IPv6TrieNode> nodeIterator(boolean forward) {
            return super.nodeIterator(forward);
        }

        @Override
        public Iterator<IPv6TrieNode> allNodeIterator(boolean forward) {
            return super.allNodeIterator(forward);
        }

        @Override
        public Iterator<IPv6TrieNode> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public Iterator<IPv6TrieNode> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv6TrieNode, IPv6Address, C> blockSizeCachingAllNodeIterator() {
            return super.blockSizeCachingAllNodeIterator();
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv6TrieNode, IPv6Address, C> containingFirstIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv6TrieNode, IPv6Address, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<IPv6TrieNode> containedFirstIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<IPv6TrieNode> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Spliterator<IPv6TrieNode> nodeSpliterator(boolean forward) {
            return super.nodeSpliterator(forward);
        }

        @Override
        public Spliterator<IPv6TrieNode> allNodeSpliterator(boolean forward) {
            return super.allNodeSpliterator(forward);
        }

        @Override
        public IPv6TrieNode previousAddedNode() {
            return (IPv6TrieNode)super.previousAddedNode();
        }

        @Override
        public IPv6TrieNode nextAddedNode() {
            return (IPv6TrieNode)super.nextAddedNode();
        }

        @Override
        public IPv6TrieNode nextNode() {
            return (IPv6TrieNode)super.nextNode();
        }

        @Override
        public IPv6TrieNode previousNode() {
            return (IPv6TrieNode)super.previousNode();
        }

        public IPv6TrieNode lowerAddedNode(IPv6Address addr) {
            return (IPv6TrieNode)super.lowerAddedNode(addr);
        }

        public IPv6TrieNode floorAddedNode(IPv6Address addr) {
            return (IPv6TrieNode)super.floorAddedNode(addr);
        }

        public IPv6TrieNode higherAddedNode(IPv6Address addr) {
            return (IPv6TrieNode)super.higherAddedNode(addr);
        }

        public IPv6TrieNode ceilingAddedNode(IPv6Address addr) {
            return (IPv6TrieNode)super.ceilingAddedNode(addr);
        }

        @Override
        public IPv6TrieNode firstNode() {
            return (IPv6TrieNode)super.firstNode();
        }

        @Override
        public IPv6TrieNode lastNode() {
            return (IPv6TrieNode)super.lastNode();
        }

        @Override
        public IPv6TrieNode firstAddedNode() {
            return (IPv6TrieNode)super.firstAddedNode();
        }

        @Override
        public IPv6TrieNode lastAddedNode() {
            return (IPv6TrieNode)super.lastAddedNode();
        }

        protected IPv6AddressTrie createNewTree() {
            return new IPv6AddressTrie();
        }

        public IPv6AddressTrie asNewTrie() {
            return (IPv6AddressTrie)super.asNewTrie();
        }

        @Override
        public IPv6TrieNode cloneTree() {
            return (IPv6TrieNode)super.cloneTree();
        }

        @Override
        public IPv6TrieNode clone() {
            return (IPv6TrieNode)super.clone();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof IPv6TrieNode && super.equals(o);
        }
    }
}

