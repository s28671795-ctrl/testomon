/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.util.BinaryTreeNode;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

public interface TreeOps<E>
extends Iterable<E>,
Serializable,
Cloneable {
    @Override
    public Iterator<E> iterator();

    public Iterator<E> descendingIterator();

    @Override
    default public Spliterator<E> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator(), 0);
    }

    default public Spliterator<E> descendingSpliterator() {
        return Spliterators.spliteratorUnknownSize(this.descendingIterator(), 0);
    }

    public Iterator<? extends BinaryTreeNode<E>> nodeIterator(boolean var1);

    public Iterator<? extends BinaryTreeNode<E>> allNodeIterator(boolean var1);

    public <C> BinaryTreeNode.CachingIterator<? extends BinaryTreeNode<E>, E, C> containingFirstIterator(boolean var1);

    public <C> BinaryTreeNode.CachingIterator<? extends BinaryTreeNode<E>, E, C> containingFirstAllNodeIterator(boolean var1);

    public Iterator<? extends BinaryTreeNode<E>> containedFirstIterator(boolean var1);

    public Iterator<? extends BinaryTreeNode<E>> containedFirstAllNodeIterator(boolean var1);

    default public Spliterator<? extends BinaryTreeNode<E>> nodeSpliterator(boolean forward) {
        return Spliterators.spliteratorUnknownSize(this.nodeIterator(forward), 0);
    }

    default public Spliterator<? extends BinaryTreeNode<E>> allNodeSpliterator(boolean forward) {
        return Spliterators.spliteratorUnknownSize(this.allNodeIterator(forward), 0);
    }
}

