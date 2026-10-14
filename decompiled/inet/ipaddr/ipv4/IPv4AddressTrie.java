/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.ipv4;

import inet.ipaddr.IPAddressString;
import inet.ipaddr.format.util.AddedTree;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import inet.ipaddr.ipv4.IPv4Address;
import inet.ipaddr.ipv4.IPv4AddressAssociativeTrie;
import java.util.Iterator;
import java.util.Spliterator;

public class IPv4AddressTrie
extends AddressTrie<IPv4Address> {
    private static final long serialVersionUID = 1L;
    static final IPv4Address INIT_ROOT = new IPAddressString("0.0.0.0/0").getAddress().toIPv4();

    public IPv4AddressTrie() {
        super(new IPv4TrieNode());
    }

    protected IPv4AddressTrie(AddressTrie.AddressBounds<IPv4Address> bounds) {
        super(new IPv4TrieNode(), bounds);
    }

    protected IPv4AddressTrie(IPv4TrieNode root, AddressTrie.AddressBounds<IPv4Address> bounds) {
        super(root, bounds);
    }

    protected IPv4TrieNode absoluteRoot() {
        return (IPv4TrieNode)super.absoluteRoot();
    }

    protected IPv4AddressTrie createNew(AddressTrie.AddressBounds<IPv4Address> bounds) {
        return new IPv4AddressTrie(bounds);
    }

    protected IPv4AddressTrie createSubTrie(AddressTrie.AddressBounds<IPv4Address> bounds) {
        return new IPv4AddressTrie(this.absoluteRoot(), bounds);
    }

    @Override
    public IPv4TrieNode getRoot() {
        return (IPv4TrieNode)super.getRoot();
    }

    public IPv4TrieNode removeElementsContainedBy(IPv4Address addr) {
        return (IPv4TrieNode)super.removeElementsContainedBy(addr);
    }

    public IPv4TrieNode elementsContainedBy(IPv4Address addr) {
        return (IPv4TrieNode)super.elementsContainedBy(addr);
    }

    public IPv4TrieNode elementsContaining(IPv4Address addr) {
        return (IPv4TrieNode)super.elementsContaining(addr);
    }

    public IPv4TrieNode longestPrefixMatchNode(IPv4Address addr) {
        return (IPv4TrieNode)super.longestPrefixMatchNode(addr);
    }

    public IPv4TrieNode getAddedNode(IPv4Address addr) {
        return (IPv4TrieNode)super.getAddedNode(addr);
    }

    public IPv4TrieNode getNode(IPv4Address addr) {
        return (IPv4TrieNode)super.getNode(addr);
    }

    public IPv4TrieNode addNode(IPv4Address addr) {
        return (IPv4TrieNode)super.addNode(addr);
    }

    public IPv4TrieNode addTrie(AddressTrie.TrieNode<IPv4Address> trie) {
        return (IPv4TrieNode)super.addTrie(trie);
    }

    @Override
    public Iterator<IPv4TrieNode> nodeIterator(boolean forward) {
        return super.nodeIterator(forward);
    }

    @Override
    public Iterator<IPv4TrieNode> allNodeIterator(boolean forward) {
        return super.allNodeIterator(forward);
    }

    @Override
    public Iterator<IPv4TrieNode> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public Iterator<IPv4TrieNode> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv4TrieNode, IPv4Address, C> blockSizeCachingAllNodeIterator() {
        return super.blockSizeCachingAllNodeIterator();
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv4TrieNode, IPv4Address, C> containingFirstIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<IPv4TrieNode, IPv4Address, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<IPv4TrieNode> containedFirstIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<IPv4TrieNode> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Spliterator<IPv4TrieNode> nodeSpliterator(boolean forward) {
        return super.nodeSpliterator(forward);
    }

    @Override
    public Spliterator<IPv4TrieNode> allNodeSpliterator(boolean forward) {
        return super.allNodeSpliterator(forward);
    }

    public IPv4TrieNode lowerAddedNode(IPv4Address addr) {
        return (IPv4TrieNode)super.lowerAddedNode(addr);
    }

    public IPv4TrieNode floorAddedNode(IPv4Address addr) {
        return (IPv4TrieNode)super.floorAddedNode(addr);
    }

    public IPv4TrieNode higherAddedNode(IPv4Address addr) {
        return (IPv4TrieNode)super.higherAddedNode(addr);
    }

    public IPv4TrieNode ceilingAddedNode(IPv4Address addr) {
        return (IPv4TrieNode)super.ceilingAddedNode(addr);
    }

    public IPv4TrieNode firstNode() {
        return (IPv4TrieNode)super.firstNode();
    }

    public IPv4TrieNode lastNode() {
        return (IPv4TrieNode)super.lastNode();
    }

    @Override
    public IPv4AddressTrie clone() {
        return (IPv4AddressTrie)super.clone();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IPv4AddressTrie && super.equals(o);
    }

    public AddedTree<IPv4Address> constructAddedNodesTree() {
        IPv4AddressAssociativeTrie trie = new IPv4AddressAssociativeTrie();
        this.contructAddedTree(trie);
        return new AddedTree<IPv4Address>(trie);
    }

    @Override
    public String toAddedNodesTreeString() {
        IPv4AddressAssociativeTrie trie = new IPv4AddressAssociativeTrie();
        this.contructAddedTree(trie);
        return IPv4AddressTrie.toAddedNodesTreeString(trie);
    }

    public static class IPv4TrieNode
    extends AddressTrie.TrieNode<IPv4Address> {
        private static final long serialVersionUID = 1L;

        protected IPv4TrieNode(IPv4Address addr) {
            super(addr);
        }

        public IPv4TrieNode() {
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
        public IPv4TrieNode getUpperSubNode() {
            return (IPv4TrieNode)super.getUpperSubNode();
        }

        @Override
        public IPv4TrieNode getLowerSubNode() {
            return (IPv4TrieNode)super.getLowerSubNode();
        }

        @Override
        public IPv4TrieNode getParent() {
            return (IPv4TrieNode)super.getParent();
        }

        protected IPv4TrieNode createNewImpl(IPv4Address addr) {
            return new IPv4TrieNode(addr);
        }

        public IPv4TrieNode removeElementsContainedBy(IPv4Address addr) {
            return (IPv4TrieNode)super.removeElementsContainedBy(addr);
        }

        public IPv4TrieNode elementsContainedBy(IPv4Address addr) {
            return (IPv4TrieNode)super.elementsContainedBy(addr);
        }

        public IPv4TrieNode elementsContaining(IPv4Address addr) {
            return (IPv4TrieNode)super.elementsContaining(addr);
        }

        public IPv4TrieNode longestPrefixMatchNode(IPv4Address addr) {
            return (IPv4TrieNode)super.longestPrefixMatchNode(addr);
        }

        public IPv4TrieNode getAddedNode(IPv4Address addr) {
            return (IPv4TrieNode)super.getAddedNode(addr);
        }

        public IPv4TrieNode getNode(IPv4Address addr) {
            return (IPv4TrieNode)super.getNode(addr);
        }

        @Override
        public Iterator<IPv4TrieNode> nodeIterator(boolean forward) {
            return super.nodeIterator(forward);
        }

        @Override
        public Iterator<IPv4TrieNode> allNodeIterator(boolean forward) {
            return super.allNodeIterator(forward);
        }

        @Override
        public Iterator<IPv4TrieNode> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public Iterator<IPv4TrieNode> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv4TrieNode, IPv4Address, C> blockSizeCachingAllNodeIterator() {
            return super.blockSizeCachingAllNodeIterator();
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv4TrieNode, IPv4Address, C> containingFirstIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<IPv4TrieNode, IPv4Address, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<IPv4TrieNode> containedFirstIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<IPv4TrieNode> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Spliterator<IPv4TrieNode> nodeSpliterator(boolean forward) {
            return super.nodeSpliterator(forward);
        }

        @Override
        public Spliterator<IPv4TrieNode> allNodeSpliterator(boolean forward) {
            return super.allNodeSpliterator(forward);
        }

        @Override
        public IPv4TrieNode previousAddedNode() {
            return (IPv4TrieNode)super.previousAddedNode();
        }

        @Override
        public IPv4TrieNode nextAddedNode() {
            return (IPv4TrieNode)super.nextAddedNode();
        }

        @Override
        public IPv4TrieNode nextNode() {
            return (IPv4TrieNode)super.nextNode();
        }

        @Override
        public IPv4TrieNode previousNode() {
            return (IPv4TrieNode)super.previousNode();
        }

        public IPv4TrieNode lowerAddedNode(IPv4Address addr) {
            return (IPv4TrieNode)super.lowerAddedNode(addr);
        }

        public IPv4TrieNode floorAddedNode(IPv4Address addr) {
            return (IPv4TrieNode)super.floorAddedNode(addr);
        }

        public IPv4TrieNode higherAddedNode(IPv4Address addr) {
            return (IPv4TrieNode)super.higherAddedNode(addr);
        }

        public IPv4TrieNode ceilingAddedNode(IPv4Address addr) {
            return (IPv4TrieNode)super.ceilingAddedNode(addr);
        }

        @Override
        public IPv4TrieNode firstNode() {
            return (IPv4TrieNode)super.firstNode();
        }

        @Override
        public IPv4TrieNode lastNode() {
            return (IPv4TrieNode)super.lastNode();
        }

        protected IPv4AddressTrie createNewTree() {
            return new IPv4AddressTrie();
        }

        public IPv4AddressTrie asNewTrie() {
            return (IPv4AddressTrie)super.asNewTrie();
        }

        @Override
        public IPv4TrieNode cloneTree() {
            return (IPv4TrieNode)super.cloneTree();
        }

        @Override
        public IPv4TrieNode clone() {
            return (IPv4TrieNode)super.clone();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof IPv4TrieNode && super.equals(o);
        }
    }
}

