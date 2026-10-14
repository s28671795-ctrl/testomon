/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.format.util.AddedTreeBase;
import inet.ipaddr.format.util.AssociativeAddressTrie;
import java.util.ArrayList;

public class AssociativeAddedTree<E extends Address, V>
extends AddedTreeBase<E, AssociativeAddressTrie.SubNodesMappingAssociative<E, V>> {
    public AssociativeAddedTree(AssociativeAddressTrie<E, AssociativeAddressTrie.SubNodesMappingAssociative<E, V>> wrapped) {
        super(wrapped);
    }

    @Override
    public AssociativeAddedTreeNode<E, V> getRoot() {
        return new AssociativeAddedTreeNode(this.wrapped.getRoot());
    }

    public static class AssociativeAddedTreeNode<E extends Address, V>
    extends AddedTreeBase.AddedTreeNodeBase<E, AssociativeAddressTrie.SubNodesMappingAssociative<E, V>> {
        public AssociativeAddedTreeNode(AssociativeAddressTrie.AssociativeTrieNode<E, AssociativeAddressTrie.SubNodesMappingAssociative<E, V>> node) {
            super(node);
        }

        public AssociativeAddedTreeNode<E, V>[] getSubNodes() {
            AssociativeAddressTrie.SubNodesMappingAssociative value = (AssociativeAddressTrie.SubNodesMappingAssociative)this.node.getValue();
            if (value == null) {
                return null;
            }
            ArrayList subNodes = value.subNodes;
            if (subNodes == null || subNodes.size() == 0) {
                return null;
            }
            AssociativeAddedTreeNode[] nodes = new AssociativeAddedTreeNode[subNodes.size()];
            for (int i = 0; i < nodes.length; ++i) {
                nodes[i] = new AssociativeAddedTreeNode<E, V>((AssociativeAddressTrie.AssociativeTrieNode)subNodes.get(i));
            }
            return nodes;
        }
    }
}

