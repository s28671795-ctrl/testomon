/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.format.util.AddressTrie;
import inet.ipaddr.format.util.AssociativeAddressTrie;

abstract class AddedTreeBase<E extends Address, N extends AddressTrie.SubNodesMapping<E, N>> {
    AssociativeAddressTrie<E, N> wrapped;

    AddedTreeBase(AssociativeAddressTrie<E, N> wrapped) {
        this.wrapped = wrapped;
    }

    public abstract AddedTreeNodeBase<E, N> getRoot();

    public String toString() {
        return AddressTrie.toAddedNodesTreeString(this.wrapped.getRoot());
    }

    static abstract class AddedTreeNodeBase<E extends Address, N extends AddressTrie.SubNodesMapping<E, N>> {
        AssociativeAddressTrie.AssociativeTrieNode<E, N> node;

        public AddedTreeNodeBase(AssociativeAddressTrie.AssociativeTrieNode<E, N> node) {
            this.node = node;
        }

        abstract AddedTreeNodeBase<E, N>[] getSubNodes();

        public E getKey() {
            return (E)((Address)this.node.getKey());
        }

        public boolean isAdded() {
            return this.node.isAdded();
        }

        public String toString() {
            return AddressTrie.TrieNode.toNodeString(new StringBuilder(50), this.node.isAdded(), this.getKey(), null).toString();
        }

        public String toTreeString() {
            return AddressTrie.toAddedNodesTreeString(this.node);
        }
    }
}

