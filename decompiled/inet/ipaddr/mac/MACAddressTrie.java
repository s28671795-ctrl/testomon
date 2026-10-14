/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.mac;

import inet.ipaddr.MACAddressString;
import inet.ipaddr.format.util.AddedTree;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import inet.ipaddr.mac.MACAddress;
import inet.ipaddr.mac.MACAddressAssociativeTrie;
import java.util.Iterator;
import java.util.Spliterator;

public class MACAddressTrie
extends AddressTrie<MACAddress> {
    private static final long serialVersionUID = 1L;
    static final MACAddress INIT_ROOT = new MACAddressString("*:*:*:*:*:*").getAddress();
    static final MACAddress INIT_ROOT_EXTENDED = new MACAddressString("*:*:*:*:*:*:*:*").getAddress();

    public MACAddressTrie() {
        super(new MACTrieNode());
    }

    protected MACAddressTrie(AddressTrie.AddressBounds<MACAddress> bounds) {
        super(new MACTrieNode(), bounds);
    }

    protected MACAddressTrie(MACTrieNode root, AddressTrie.AddressBounds<MACAddress> bounds) {
        super(root, bounds);
    }

    @Override
    protected void adjustRoot(MACAddress addr) {
        if (this.isEmpty() && addr.getSegmentCount() == 8) {
            this.absoluteRoot().setExtendedRootKey();
        }
    }

    protected MACTrieNode absoluteRoot() {
        return (MACTrieNode)super.absoluteRoot();
    }

    protected MACAddressTrie createNew(AddressTrie.AddressBounds<MACAddress> bounds) {
        return new MACAddressTrie(bounds);
    }

    protected MACAddressTrie createSubTrie(AddressTrie.AddressBounds<MACAddress> bounds) {
        return new MACAddressTrie(this.absoluteRoot(), bounds);
    }

    @Override
    public MACTrieNode getRoot() {
        return (MACTrieNode)super.getRoot();
    }

    public MACTrieNode removeElementsContainedBy(MACAddress addr) {
        return (MACTrieNode)super.removeElementsContainedBy(addr);
    }

    public MACTrieNode elementsContainedBy(MACAddress addr) {
        return (MACTrieNode)super.elementsContainedBy(addr);
    }

    public MACTrieNode elementsContaining(MACAddress addr) {
        return (MACTrieNode)super.elementsContaining(addr);
    }

    public MACTrieNode longestPrefixMatchNode(MACAddress addr) {
        return (MACTrieNode)super.longestPrefixMatchNode(addr);
    }

    public MACTrieNode getAddedNode(MACAddress addr) {
        return (MACTrieNode)super.getAddedNode(addr);
    }

    public MACTrieNode getNode(MACAddress addr) {
        return (MACTrieNode)super.getNode(addr);
    }

    public MACTrieNode addNode(MACAddress addr) {
        return (MACTrieNode)super.addNode(addr);
    }

    public MACTrieNode addTrie(AddressTrie.TrieNode<MACAddress> trie) {
        return (MACTrieNode)super.addTrie(trie);
    }

    @Override
    public Iterator<MACTrieNode> nodeIterator(boolean forward) {
        return super.nodeIterator(forward);
    }

    @Override
    public Iterator<MACTrieNode> allNodeIterator(boolean forward) {
        return super.allNodeIterator(forward);
    }

    @Override
    public Iterator<MACTrieNode> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public Iterator<MACTrieNode> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
        return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<MACTrieNode, MACAddress, C> blockSizeCachingAllNodeIterator() {
        return super.blockSizeCachingAllNodeIterator();
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<MACTrieNode, MACAddress, C> containingFirstIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<MACTrieNode, MACAddress, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<MACTrieNode> containedFirstIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstIterator(forwardSubNodeOrder);
    }

    @Override
    public Iterator<MACTrieNode> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
    }

    @Override
    public Spliterator<MACTrieNode> nodeSpliterator(boolean forward) {
        return super.nodeSpliterator(forward);
    }

    @Override
    public Spliterator<MACTrieNode> allNodeSpliterator(boolean forward) {
        return super.allNodeSpliterator(forward);
    }

    public MACTrieNode lowerAddedNode(MACAddress addr) {
        return (MACTrieNode)super.lowerAddedNode(addr);
    }

    public MACTrieNode floorAddedNode(MACAddress addr) {
        return (MACTrieNode)super.floorAddedNode(addr);
    }

    public MACTrieNode higherAddedNode(MACAddress addr) {
        return (MACTrieNode)super.higherAddedNode(addr);
    }

    public MACTrieNode ceilingAddedNode(MACAddress addr) {
        return (MACTrieNode)super.ceilingAddedNode(addr);
    }

    public MACTrieNode firstNode() {
        return (MACTrieNode)super.firstNode();
    }

    public MACTrieNode lastNode() {
        return (MACTrieNode)super.lastNode();
    }

    public MACTrieNode firstAddedNode() {
        return (MACTrieNode)super.firstAddedNode();
    }

    public MACTrieNode lastAddedNode() {
        return (MACTrieNode)super.lastAddedNode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MACAddressTrie && super.equals(o);
    }

    @Override
    public MACAddressTrie clone() {
        return (MACAddressTrie)super.clone();
    }

    public AddedTree<MACAddress> constructAddedNodesTree() {
        MACAddressAssociativeTrie trie = new MACAddressAssociativeTrie();
        this.contructAddedTree(trie);
        return new AddedTree<MACAddress>(trie);
    }

    @Override
    public String toAddedNodesTreeString() {
        MACAddressAssociativeTrie trie = new MACAddressAssociativeTrie();
        this.contructAddedTree(trie);
        return MACAddressTrie.toAddedNodesTreeString(trie);
    }

    public static class MACTrieNode
    extends AddressTrie.TrieNode<MACAddress> {
        private static final long serialVersionUID = 1L;

        protected MACTrieNode(MACAddress addr) {
            super(addr);
        }

        public MACTrieNode() {
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
        public MACTrieNode getUpperSubNode() {
            return (MACTrieNode)super.getUpperSubNode();
        }

        @Override
        public MACTrieNode getLowerSubNode() {
            return (MACTrieNode)super.getLowerSubNode();
        }

        @Override
        public MACTrieNode getParent() {
            return (MACTrieNode)super.getParent();
        }

        protected MACTrieNode createNewImpl(MACAddress addr) {
            return new MACTrieNode(addr);
        }

        public MACTrieNode removeElementsContainedBy(MACAddress addr) {
            return (MACTrieNode)super.removeElementsContainedBy(addr);
        }

        public MACTrieNode elementsContainedBy(MACAddress addr) {
            return (MACTrieNode)super.elementsContainedBy(addr);
        }

        public MACTrieNode elementsContaining(MACAddress addr) {
            return (MACTrieNode)super.elementsContaining(addr);
        }

        public MACTrieNode longestPrefixMatchNode(MACAddress addr) {
            return (MACTrieNode)super.longestPrefixMatchNode(addr);
        }

        public MACTrieNode getAddedNode(MACAddress addr) {
            return (MACTrieNode)super.getAddedNode(addr);
        }

        public MACTrieNode getNode(MACAddress addr) {
            return (MACTrieNode)super.getNode(addr);
        }

        @Override
        public Iterator<MACTrieNode> nodeIterator(boolean forward) {
            return super.nodeIterator(forward);
        }

        @Override
        public Iterator<MACTrieNode> allNodeIterator(boolean forward) {
            return super.allNodeIterator(forward);
        }

        @Override
        public Iterator<MACTrieNode> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public Iterator<MACTrieNode> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeAllNodeIterator(lowerSubNodeFirst);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<MACTrieNode, MACAddress, C> blockSizeCachingAllNodeIterator() {
            return super.blockSizeCachingAllNodeIterator();
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<MACTrieNode, MACAddress, C> containingFirstIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<MACTrieNode, MACAddress, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<MACTrieNode> containedFirstIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<MACTrieNode> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Spliterator<MACTrieNode> nodeSpliterator(boolean forward) {
            return super.nodeSpliterator(forward);
        }

        @Override
        public Spliterator<MACTrieNode> allNodeSpliterator(boolean forward) {
            return super.allNodeSpliterator(forward);
        }

        @Override
        public MACTrieNode previousAddedNode() {
            return (MACTrieNode)super.previousAddedNode();
        }

        @Override
        public MACTrieNode nextAddedNode() {
            return (MACTrieNode)super.nextAddedNode();
        }

        @Override
        public MACTrieNode nextNode() {
            return (MACTrieNode)super.nextNode();
        }

        @Override
        public MACTrieNode previousNode() {
            return (MACTrieNode)super.previousNode();
        }

        public MACTrieNode lowerAddedNode(MACAddress addr) {
            return (MACTrieNode)super.lowerAddedNode(addr);
        }

        public MACTrieNode floorAddedNode(MACAddress addr) {
            return (MACTrieNode)super.floorAddedNode(addr);
        }

        public MACTrieNode higherAddedNode(MACAddress addr) {
            return (MACTrieNode)super.higherAddedNode(addr);
        }

        public MACTrieNode ceilingAddedNode(MACAddress addr) {
            return (MACTrieNode)super.ceilingAddedNode(addr);
        }

        @Override
        public MACTrieNode firstNode() {
            return (MACTrieNode)super.firstNode();
        }

        @Override
        public MACTrieNode lastNode() {
            return (MACTrieNode)super.lastNode();
        }

        @Override
        public MACTrieNode firstAddedNode() {
            return (MACTrieNode)super.firstAddedNode();
        }

        @Override
        public MACTrieNode lastAddedNode() {
            return (MACTrieNode)super.lastAddedNode();
        }

        protected MACAddressTrie createNewTree() {
            return new MACAddressTrie();
        }

        public MACAddressTrie asNewTrie() {
            return (MACAddressTrie)super.asNewTrie();
        }

        @Override
        public MACTrieNode cloneTree() {
            return (MACTrieNode)super.cloneTree();
        }

        @Override
        public MACTrieNode clone() {
            return (MACTrieNode)super.clone();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof MACTrieNode && super.equals(o);
        }
    }
}

