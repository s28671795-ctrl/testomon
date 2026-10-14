/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.format.util.AddedTreeBase;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.AssociativeAddressTrie;
import java.util.ArrayList;

public class AddedTree<E extends Address>
extends AddedTreeBase<E, AddressTrie.SubNodesMappingBasic<E>> {
    public AddedTree(AssociativeAddressTrie<E, AddressTrie.SubNodesMappingBasic<E>> wrapped) {
        super(wrapped);
    }

    public AddedTreeNode<E> getRoot() {
        return new AddedTreeNode(this.wrapped.getRoot());
    }

    public static class AddedTreeNode<E extends Address>
    extends AddedTreeBase.AddedTreeNodeBase<E, AddressTrie.SubNodesMappingBasic<E>> {
        public AddedTreeNode(AssociativeAddressTrie.AssociativeTrieNode<E, AddressTrie.SubNodesMappingBasic<E>> node) {
            super(node);
        }

        public AddedTreeNode<E>[] getSubNodes() {
            AddressTrie.SubNodesMappingBasic value = (AddressTrie.SubNodesMappingBasic)this.node.getValue();
            if (value == null) {
                return null;
            }
            ArrayList subNodes = value.subNodes;
            if (subNodes == null || subNodes.size() == 0) {
                return null;
            }
            AddedTreeNode[] nodes = new AddedTreeNode[subNodes.size()];
            for (int i = 0; i < nodes.length; ++i) {
                nodes[i] = new AddedTreeNode<E>((AssociativeAddressTrie.AssociativeTrieNode)subNodes.get(i));
            }
            return nodes;
        }
    }
}

