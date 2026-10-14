/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.format.util.AbstractTree;
import inet.ipaddr.format.util.TreeOps;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class BinaryTreeNode<E>
implements TreeOps<E>,
Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    protected static boolean FREEZE_ROOT = true;
    static final int SIZE_UNKNOWN = -1;
    private E item;
    private BinaryTreeNode<E> parent;
    private BinaryTreeNode<E> lower;
    private BinaryTreeNode<E> upper;
    int size;
    ChangeTracker changeTracker;
    private boolean added;
    static final String NON_ADDED_NODE_CIRCLE = "\u25cb";
    static final String ADDED_NODE_CIRCLE = "\u25cf";
    static final String LEFT_ELBOW = "\u251c\u2500";
    static final String IN_BETWEEN_ELBOWS = "\u2502 ";
    static final String RIGHT_ELBOW = "\u2514\u2500";
    static final String BELOW_ELBOWS = "  ";

    static String getMessage(String key) {
        return AbstractTree.getMessage(key);
    }

    protected BinaryTreeNode(E item) {
        this.item = item;
    }

    protected void setKey(E item) {
        this.item = item;
    }

    public E getKey() {
        return this.item;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public BinaryTreeNode<E> getParent() {
        return this.parent;
    }

    void setParent(BinaryTreeNode<E> parent) {
        this.parent = parent;
    }

    public BinaryTreeNode<E> getUpperSubNode() {
        return this.upper;
    }

    public BinaryTreeNode<E> getLowerSubNode() {
        return this.lower;
    }

    protected void setUpper(BinaryTreeNode<E> upper) {
        this.upper = upper;
        if (upper != null) {
            upper.setParent(this);
        }
    }

    protected void setLower(BinaryTreeNode<E> lower) {
        this.lower = lower;
        if (lower != null) {
            lower.setParent(this);
        }
    }

    public boolean isAdded() {
        return this.added;
    }

    public void setAdded() {
        if (!this.added) {
            this.setAdded(true);
            this.adjustCount(1);
        }
    }

    protected void setAdded(boolean added) {
        this.added = added;
    }

    public int size() {
        int storedSize = this.size;
        if (storedSize == -1) {
            Iterator<BinaryTreeNode<E>> iterator2 = this.containedFirstAllNodeIterator(true);
            while (iterator2.hasNext()) {
                BinaryTreeNode<E> upper;
                BinaryTreeNode<E> next = iterator2.next();
                int nodeSize = next.isAdded() ? 1 : 0;
                BinaryTreeNode<E> lower = next.getLowerSubNode();
                if (lower != null) {
                    nodeSize += lower.size;
                }
                if ((upper = next.getUpperSubNode()) != null) {
                    nodeSize += upper.size;
                }
                next.size = nodeSize;
            }
            storedSize = this.size;
        }
        return storedSize;
    }

    public int nodeSize() {
        int totalCount = 0;
        NodeIterator<E> iterator2 = this.iterator(true, false);
        while (iterator2.hasNext()) {
            ++totalCount;
            iterator2.next();
        }
        return totalCount;
    }

    void adjustCount(int delta) {
        if (delta != 0) {
            BinaryTreeNode<E> node = this;
            do {
                node.size += delta;
            } while ((node = node.getParent()) != null);
        }
    }

    public void remove() {
        if (!this.isAdded()) {
            return;
        }
        if (FREEZE_ROOT && this.isRoot()) {
            this.removed();
        } else if (this.getUpperSubNode() == null) {
            this.replaceThis(this.getLowerSubNode());
        } else if (this.getLowerSubNode() == null) {
            this.replaceThis(this.getUpperSubNode());
        } else {
            this.removed();
        }
    }

    void removed() {
        this.adjustCount(-1);
        this.setAdded(false);
        this.changeTracker.changed();
    }

    void replaceThis(BinaryTreeNode<E> replacement) {
        this.replaceThisRecursive(replacement, 0);
        this.changeTracker.changed();
    }

    void replaceThisRecursive(BinaryTreeNode<E> replacement, int additionalSizeAdjustment) {
        if (this.isRoot()) {
            this.replaceThisRoot(replacement);
            return;
        }
        BinaryTreeNode<E> parent = this.getParent();
        if (parent.getUpperSubNode() == this) {
            this.adjustTree(parent, replacement, additionalSizeAdjustment, true);
            parent.setUpper(replacement);
        } else if (parent.getLowerSubNode() == this) {
            this.adjustTree(parent, replacement, additionalSizeAdjustment, false);
            parent.setLower(replacement);
        } else {
            throw new Error();
        }
    }

    private void adjustTree(BinaryTreeNode<E> parent, BinaryTreeNode<E> replacement, int additionalSizeAdjustment, boolean replacedUpper) {
        int sizeAdjustment = -this.size;
        if (replacement == null) {
            if (!(parent.isAdded() || FREEZE_ROOT && parent.isRoot())) {
                parent.size += sizeAdjustment;
                BinaryTreeNode<E> parentReplacement = replacedUpper ? parent.getLowerSubNode() : parent.getUpperSubNode();
                parent.replaceThisRecursive(parentReplacement, sizeAdjustment);
            } else {
                parent.adjustCount(sizeAdjustment + additionalSizeAdjustment);
            }
        } else {
            parent.adjustCount(replacement.size + sizeAdjustment + additionalSizeAdjustment);
        }
        this.setParent(null);
    }

    protected void replaceThisRoot(BinaryTreeNode<E> replacement) {
        if (replacement == null) {
            this.setAdded(false);
            this.setUpper(null);
            this.setLower(null);
            if (!FREEZE_ROOT) {
                this.setKey(null);
            }
            this.size = 0;
        } else {
            this.setAdded(replacement.isAdded());
            this.setUpper(replacement.getUpperSubNode());
            this.setLower(replacement.getLowerSubNode());
            this.setKey(replacement.getKey());
            this.size = replacement.size;
        }
    }

    public void clear() {
        this.replaceThis(null);
    }

    public boolean isEmpty() {
        return !this.isAdded() && this.getUpperSubNode() == null && this.getLowerSubNode() == null;
    }

    public boolean isLeaf() {
        return this.isAdded() && this.getUpperSubNode() == null && this.getLowerSubNode() == null;
    }

    public BinaryTreeNode<E> firstNode() {
        BinaryTreeNode<E> first = this;
        BinaryTreeNode<E> lower;
        while ((lower = first.getLowerSubNode()) != null) {
            first = lower;
        }
        return first;
    }

    public BinaryTreeNode<E> firstAddedNode() {
        BinaryTreeNode<E> first = this.firstNode();
        if (first.isAdded()) {
            return first;
        }
        return first.nextAddedNode();
    }

    public BinaryTreeNode<E> lastNode() {
        BinaryTreeNode<E> last = this;
        BinaryTreeNode<E> upper;
        while ((upper = last.getUpperSubNode()) != null) {
            last = upper;
        }
        return last;
    }

    public BinaryTreeNode<E> lastAddedNode() {
        BinaryTreeNode<E> last = this.lastNode();
        if (last.isAdded()) {
            return last;
        }
        return last.previousAddedNode();
    }

    BinaryTreeNode<E> firstPostOrderNode() {
        BinaryTreeNode<E> next = this;
        BinaryTreeNode<E> nextNext;
        while ((nextNext = next.getLowerSubNode()) != null || (nextNext = next.getUpperSubNode()) != null) {
            next = nextNext;
        }
        return next;
    }

    BinaryTreeNode<E> lastPreOrderNode() {
        BinaryTreeNode<E> next = this;
        BinaryTreeNode<E> nextNext;
        while ((nextNext = next.getUpperSubNode()) != null || (nextNext = next.getLowerSubNode()) != null) {
            next = nextNext;
        }
        return next;
    }

    public BinaryTreeNode<E> nextNode() {
        return this.nextNode(null);
    }

    BinaryTreeNode<E> nextNode(BinaryTreeNode<E> bound2) {
        BinaryTreeNode<E> next = this.getUpperSubNode();
        if (next != null) {
            while (true) {
                BinaryTreeNode<E> nextLower;
                if ((nextLower = next.getLowerSubNode()) == null) {
                    return next;
                }
                next = nextLower;
            }
        }
        next = this.getParent();
        if (next == bound2) {
            return null;
        }
        BinaryTreeNode<E> current = this;
        while (next != null && current == next.getUpperSubNode()) {
            current = next;
            if ((next = next.getParent()) != bound2) continue;
            return null;
        }
        return next;
    }

    public BinaryTreeNode<E> previousNode() {
        return this.previousNode(null);
    }

    BinaryTreeNode<E> previousNode(BinaryTreeNode<E> bound2) {
        BinaryTreeNode<E> previous = this.getLowerSubNode();
        if (previous != null) {
            BinaryTreeNode<E> previousUpper;
            while ((previousUpper = previous.getUpperSubNode()) != null) {
                previous = previousUpper;
            }
        } else {
            previous = this.getParent();
            if (previous == bound2) {
                return null;
            }
            BinaryTreeNode<E> current = this;
            while (previous != null && current == previous.getLowerSubNode()) {
                current = previous;
                if ((previous = previous.getParent()) != bound2) continue;
                return null;
            }
        }
        return previous;
    }

    BinaryTreeNode<E> nextPreOrderNode(BinaryTreeNode<E> end) {
        BinaryTreeNode<E> next = this.getLowerSubNode();
        if (next == null && (next = this.getUpperSubNode()) == null) {
            BinaryTreeNode<E> current = this;
            for (next = this.getParent(); next != null; next = next.getParent()) {
                BinaryTreeNode<E> nextNext;
                if (next == end) {
                    return null;
                }
                if (current == next.getLowerSubNode() && (nextNext = next.getUpperSubNode()) != null) {
                    return nextNext;
                }
                current = next;
            }
        }
        return next;
    }

    BinaryTreeNode<E> previousPostOrderNode(BinaryTreeNode<E> end) {
        BinaryTreeNode<E> next = this.getUpperSubNode();
        if (next == null && (next = this.getLowerSubNode()) == null) {
            BinaryTreeNode<E> current = this;
            for (next = this.getParent(); next != null; next = next.getParent()) {
                BinaryTreeNode<E> nextNext;
                if (next == end) {
                    return null;
                }
                if (current == next.getUpperSubNode() && (nextNext = next.getLowerSubNode()) != null) {
                    next = nextNext;
                    break;
                }
                current = next;
            }
        }
        return next;
    }

    BinaryTreeNode<E> previousPreOrderNode(BinaryTreeNode<E> end) {
        BinaryTreeNode<E> next = this.getParent();
        if (next == null || next == end) {
            return null;
        }
        if (next.getLowerSubNode() == this) {
            return next;
        }
        BinaryTreeNode<E> nextNext = next.getLowerSubNode();
        if (nextNext == null) {
            return next;
        }
        next = nextNext;
        while ((nextNext = next.getUpperSubNode()) != null || (nextNext = next.getLowerSubNode()) != null) {
            next = nextNext;
        }
        return next;
    }

    BinaryTreeNode<E> nextPostOrderNode(BinaryTreeNode<E> end) {
        BinaryTreeNode<E> next = this.getParent();
        if (next == null || next == end) {
            return null;
        }
        if (next.getUpperSubNode() == this) {
            return next;
        }
        BinaryTreeNode<E> nextNext = next.getUpperSubNode();
        if (nextNext == null) {
            return next;
        }
        next = nextNext;
        while ((nextNext = next.getLowerSubNode()) != null || (nextNext = next.getUpperSubNode()) != null) {
            next = nextNext;
        }
        return next;
    }

    public BinaryTreeNode<E> nextAddedNode() {
        return this.nextAdded(null, BinaryTreeNode::nextNode);
    }

    public BinaryTreeNode<E> previousAddedNode() {
        return this.nextAdded(null, BinaryTreeNode::previousNode);
    }

    private static <E> BinaryTreeNode<E> nextTest(BinaryTreeNode<E> current, BinaryTreeNode<E> end, BinaryOperator<BinaryTreeNode<E>> nextOperator, Predicate<BinaryTreeNode<E>> tester) {
        do {
            if ((current = (BinaryTreeNode)nextOperator.apply(current, end)) != end && current != null) continue;
            return null;
        } while (!tester.test(current));
        return current;
    }

    private BinaryTreeNode<E> nextAdded(BinaryTreeNode<E> end, BinaryOperator<BinaryTreeNode<E>> nextOperator) {
        return BinaryTreeNode.nextTest(this, end, nextOperator, BinaryTreeNode::isAdded);
    }

    private BinaryTreeNode<E> nextInBounds(BinaryTreeNode<E> end, BinaryOperator<BinaryTreeNode<E>> nextOperator, Bounds<E> bounds) {
        return BinaryTreeNode.nextTest(this, end, nextOperator, node -> bounds.isInBounds(node.getKey()));
    }

    @Override
    public Iterator<E> iterator() {
        return new KeyIterator<E>(this.nodeIterator(true));
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new KeyIterator<E>(this.nodeIterator(false));
    }

    @Override
    public Iterator<? extends BinaryTreeNode<E>> nodeIterator(boolean forward) {
        return this.iterator(forward, true);
    }

    @Override
    public Iterator<? extends BinaryTreeNode<E>> allNodeIterator(boolean forward) {
        return this.iterator(forward, false);
    }

    <C> CachingIterator<? extends BinaryTreeNode<E>, E, C> blockSizeCachingAllNodeIterator() {
        return new BlockSizeCachingNodeIterator(this, false, this.changeTracker);
    }

    Iterator<? extends BinaryTreeNode<E>> blockSizeNodeIterator(boolean lowerSubNodeFirst, boolean addedNodesOnly) {
        return new BlockSizeNodeIterator(addedNodesOnly ? this.size() : 0, addedNodesOnly, this, !lowerSubNodeFirst, this.changeTracker);
    }

    @Override
    public <C> CachingIterator<? extends BinaryTreeNode<E>, E, C> containingFirstIterator(boolean forwardSubNodeOrder) {
        return this.containingFirstIterator(forwardSubNodeOrder, true);
    }

    @Override
    public <C> CachingIterator<? extends BinaryTreeNode<E>, E, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return this.containingFirstIterator(forwardSubNodeOrder, false);
    }

    private <C> CachingIterator<? extends BinaryTreeNode<E>, E, C> containingFirstIterator(boolean forwardSubNodeOrder, boolean addedNodesOnly) {
        if (forwardSubNodeOrder) {
            return new PreOrderNodeIterator(true, addedNodesOnly, this, this.getParent(), this.changeTracker);
        }
        return new PostOrderNodeIterator(false, addedNodesOnly, this, this.getParent(), this.changeTracker);
    }

    @Override
    public Iterator<? extends BinaryTreeNode<E>> containedFirstIterator(boolean forwardSubNodeOrder) {
        return this.containedFirstIterator(forwardSubNodeOrder, true);
    }

    @Override
    public Iterator<? extends BinaryTreeNode<E>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        return this.containedFirstIterator(forwardSubNodeOrder, false);
    }

    private Iterator<? extends BinaryTreeNode<E>> containedFirstIterator(boolean forwardSubNodeOrder, boolean addedNodesOnly) {
        if (forwardSubNodeOrder) {
            return new PostOrderNodeIterator(true, addedNodesOnly, this.firstPostOrderNode(), this.getParent(), this.changeTracker);
        }
        return new PreOrderNodeIterator(false, addedNodesOnly, this.lastPreOrderNode(), this.getParent(), this.changeTracker);
    }

    private NodeIterator<E> iterator(boolean forward, boolean addedOnly) {
        return new NodeIterator<E>(forward, addedOnly, forward ? this.firstNode() : this.lastNode(), this.getParent(), this.changeTracker);
    }

    static int compareLowValues(Address one, Address two) {
        return Address.ADDRESS_LOW_VALUE_COMPARATOR.compare(one, two);
    }

    public String toTreeString(boolean withNonAddedKeys, boolean withSizes) {
        StringBuilder builder = new StringBuilder("\n");
        this.printTree(builder, new Indents(), withNonAddedKeys, withSizes, this.containingFirstAllNodeIterator(true));
        return builder.toString();
    }

    void printTree(StringBuilder builder, Indents initialIndents, boolean withNonAdded, boolean withSizes, CachingIterator<? extends BinaryTreeNode<E>, E, Indents> iterator2) {
        while (iterator2.hasNext()) {
            Indents lowerIndents;
            String subNodeIndent;
            String nodeIndent;
            BinaryTreeNode next = (BinaryTreeNode)iterator2.next();
            Indents cached = iterator2.getCached();
            if (cached == null) {
                nodeIndent = initialIndents.nodeIndent;
                subNodeIndent = initialIndents.subNodeInd;
            } else {
                nodeIndent = cached.nodeIndent;
                subNodeIndent = cached.subNodeInd;
            }
            if (withNonAdded || next.isAdded()) {
                builder.append(nodeIndent).append(next);
                if (withSizes) {
                    builder.append(" (").append(next.size()).append(')');
                }
                builder.append('\n');
            } else {
                builder.append(nodeIndent).append("\u25cb\n");
            }
            BinaryTreeNode<E> upper = next.getUpperSubNode();
            BinaryTreeNode<E> lower = next.getLowerSubNode();
            if (upper != null) {
                if (lower != null) {
                    lowerIndents = new Indents(subNodeIndent + LEFT_ELBOW, subNodeIndent + IN_BETWEEN_ELBOWS);
                    iterator2.cacheWithLowerSubNode(lowerIndents);
                }
                Indents upperIndents = new Indents(subNodeIndent + RIGHT_ELBOW, subNodeIndent + BELOW_ELBOWS);
                iterator2.cacheWithUpperSubNode(upperIndents);
                continue;
            }
            if (lower == null) continue;
            lowerIndents = new Indents(subNodeIndent + RIGHT_ELBOW, subNodeIndent + BELOW_ELBOWS);
            iterator2.cacheWithLowerSubNode(lowerIndents);
        }
    }

    public String toString() {
        return BinaryTreeNode.toNodeString(new StringBuilder(50), this.isAdded(), this.getKey(), null).toString();
    }

    static <E, V> StringBuilder toNodeString(StringBuilder builder, boolean isAdded, E key, V value) {
        builder.append(isAdded ? ADDED_NODE_CIRCLE : NON_ADDED_NODE_CIRCLE).append(' ').append(key);
        if (value != null) {
            builder.append(" = ").append(value);
        }
        return builder;
    }

    public BinaryTreeNode<E> clone() {
        try {
            BinaryTreeNode result = (BinaryTreeNode)super.clone();
            result.setParent(null);
            result.setLower(null);
            result.setUpper(null);
            result.size = this.isAdded() ? 1 : 0;
            result.changeTracker = null;
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    BinaryTreeNode<E> cloneTreeNode(ChangeTracker changeTracker) {
        try {
            BinaryTreeNode result = (BinaryTreeNode)super.clone();
            result.setParent(null);
            result.changeTracker = changeTracker;
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    BinaryTreeNode<E> cloneTree(ChangeTracker changeTracker, Bounds<E> bounds) {
        BinaryTreeNode<E> lower;
        BinaryTreeNode<E> rootClone;
        BinaryTreeNode<E> clonedNode = rootClone = this.cloneTreeNode(changeTracker);
        SubNodeCachingIterator iterator2 = (SubNodeCachingIterator)clonedNode.containingFirstAllNodeIterator(true);
        boolean recalculateSize = false;
        do {
            BinaryTreeNode<E> upper;
            if (bounds != null) {
                block1: for (lower = clonedNode.getLowerSubNode(); lower != null; lower = lower.getUpperSubNode()) {
                    if (bounds.isWithinLowerBound(lower.getKey())) {
                        if (lower.isAdded()) break;
                        BinaryTreeNode<E> next = lower.getLowerSubNode();
                        while (bounds.isBelowLowerBound(next.getKey())) {
                            if ((next = next.getUpperSubNode()) != null) continue;
                            lower = lower.getUpperSubNode();
                            recalculateSize = true;
                            break block1;
                        }
                        break;
                    }
                    recalculateSize = true;
                }
            }
            if (lower != null) {
                clonedNode.setLower(lower.cloneTreeNode(changeTracker));
            } else {
                clonedNode.setLower(null);
            }
            if (bounds != null) {
                block3: for (upper = clonedNode.getUpperSubNode(); upper != null; upper = upper.getLowerSubNode()) {
                    if (bounds.isWithinUpperBound(upper.getKey())) {
                        if (upper.isAdded()) break;
                        BinaryTreeNode<E> next = upper.getUpperSubNode();
                        while (bounds.isAboveUpperBound(next.getKey())) {
                            if ((next = next.getLowerSubNode()) != null) continue;
                            upper = upper.getLowerSubNode();
                            recalculateSize = true;
                            break block3;
                        }
                        break;
                    }
                    recalculateSize = true;
                }
            }
            if (upper != null) {
                clonedNode.setUpper(upper.cloneTreeNode(changeTracker));
            } else {
                clonedNode.setUpper(null);
            }
            iterator2.next();
            clonedNode = iterator2.next;
        } while (iterator2.hasNext());
        if (!rootClone.isAdded() && !this.isRoot()) {
            lower = rootClone.getLowerSubNode();
            if (lower == null) {
                rootClone = rootClone.getUpperSubNode();
            } else if (rootClone.getUpperSubNode() == null) {
                rootClone = lower;
            }
        }
        if (recalculateSize && rootClone != null) {
            rootClone.size = -1;
            rootClone.size();
        }
        return rootClone;
    }

    BinaryTreeNode<E> cloneTree(Bounds<E> bounds) {
        return this.cloneTree(new ChangeTracker(), bounds);
    }

    public BinaryTreeNode<E> cloneTree() {
        return this.cloneTree(null);
    }

    public int hashCode() {
        return this.getKey().hashCode();
    }

    public int treeHashCode() {
        int hashCode = 0;
        Iterator<BinaryTreeNode<E>> these = this.nodeIterator(true);
        while (these.hasNext()) {
            BinaryTreeNode<E> node = these.next();
            hashCode += node.hashCode();
        }
        return hashCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof BinaryTreeNode) {
            BinaryTreeNode other = (BinaryTreeNode)o;
            return this.getKey().equals(other.getKey());
        }
        return false;
    }

    public boolean treeEquals(BinaryTreeNode<?> other) {
        if (other == this) {
            return true;
        }
        if (other.size() != this.size()) {
            return false;
        }
        Iterator<BinaryTreeNode<E>> these = this.nodeIterator(true);
        Iterator<BinaryTreeNode<?>> others = other.nodeIterator(true);
        while (these.hasNext()) {
            BinaryTreeNode<?> otherNode;
            BinaryTreeNode<E> node = these.next();
            if (node.equals(otherNode = others.next())) continue;
            return false;
        }
        return true;
    }

    static class Indents {
        final String nodeIndent;
        final String subNodeInd;

        Indents() {
            this("", "");
        }

        Indents(String nodeIndent, String subNodeIndent) {
            this.nodeIndent = nodeIndent;
            this.subNodeInd = subNodeIndent;
        }
    }

    static class KeySpliterator<E>
    implements Spliterator<E> {
        private final Spliterator<? extends BinaryTreeNode<E>> wrapped;
        private final Comparator<? super E> comparator;

        KeySpliterator(Spliterator<? extends BinaryTreeNode<E>> wrapped, Comparator<? super E> comparator) {
            this.wrapped = wrapped;
            this.comparator = comparator;
        }

        private static <E> Consumer<? super BinaryTreeNode<E>> wrapAction(Consumer<? super E> action) {
            return node -> action.accept((Object)node.getKey());
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> action) {
            return this.wrapped.tryAdvance(KeySpliterator.wrapAction(action));
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            this.wrapped.forEachRemaining(KeySpliterator.wrapAction(action));
        }

        @Override
        public Comparator<? super E> getComparator() {
            return this.comparator;
        }

        @Override
        public Spliterator<E> trySplit() {
            Spliterator<? extends BinaryTreeNode<E>> split = this.wrapped.trySplit();
            if (split == null) {
                return null;
            }
            return new KeySpliterator<E>(split, this.comparator);
        }

        @Override
        public long estimateSize() {
            return this.wrapped.estimateSize();
        }

        @Override
        public long getExactSizeIfKnown() {
            return this.wrapped.getExactSizeIfKnown();
        }

        @Override
        public int characteristics() {
            return this.wrapped.characteristics();
        }

        public String toString() {
            return this.wrapped.toString();
        }
    }

    static class NodeSpliterator<E>
    implements Spliterator<BinaryTreeNode<E>> {
        private final ChangeTracker changeTracker;
        private ChangeTracker.Change currentChange;
        private final Comparator<? super BinaryTreeNode<E>> comparator;
        private Side position;
        private BinaryTreeNode<E> begin;
        private BinaryTreeNode<E> end;
        private BinaryTreeNode<E> root;
        private NodeIterator<E> iterator;
        private long sizeEstimate;
        private final boolean addedOnly;
        private final boolean forward;

        NodeSpliterator(boolean forward, Comparator<? super BinaryTreeNode<E>> comparator, BinaryTreeNode<E> root, BinaryTreeNode<E> begin, BinaryTreeNode<E> end, long size, ChangeTracker changeTracker, boolean addedOnly) {
            this(forward, comparator, Side.ALL, begin, end, size, changeTracker, addedOnly);
            this.root = root;
        }

        private NodeSpliterator(boolean forward, Comparator<? super BinaryTreeNode<E>> comparator, Side position, BinaryTreeNode<E> begin, BinaryTreeNode<E> end, long sizeEstimate, ChangeTracker changeTracker, boolean addedOnly) {
            this.comparator = comparator;
            this.sizeEstimate = sizeEstimate;
            this.end = end;
            this.begin = begin;
            this.position = position;
            this.changeTracker = changeTracker;
            this.addedOnly = addedOnly;
            this.forward = forward;
            this.currentChange = changeTracker.getCurrent();
        }

        public String toString() {
            return "spliterator from " + this.begin + " to " + this.end;
        }

        private BinaryTreeNode<E> getMiddle() {
            BinaryTreeNode<E> mid;
            if (this.position == Side.BEGINNING) {
                mid = this.forward ? this.end.getLowerSubNode() : this.end.getUpperSubNode();
            } else if (this.position == Side.ENDING) {
                BinaryTreeNode<E> binaryTreeNode = mid = this.forward ? this.begin.getUpperSubNode() : this.begin.getLowerSubNode();
                if (mid != null && this.end != null && this.getComparator().compare(mid, this.end) >= 0) {
                    return null;
                }
            } else {
                mid = this.root;
            }
            return mid;
        }

        private BinaryTreeNode<E> nextNode(BinaryTreeNode<E> current, BinaryTreeNode<E> bound2) {
            return this.forward ? current.nextNode(bound2) : current.previousNode(bound2);
        }

        @Override
        public Spliterator<BinaryTreeNode<E>> trySplit() {
            BinaryTreeNode<E> current;
            if (this.begin == null) {
                return null;
            }
            this.changeTracker.changedSince(this.currentChange);
            BinaryTreeNode<E> mid = this.getMiddle();
            if (mid == null) {
                return null;
            }
            if (this.iterator == null) {
                current = this.begin;
            } else {
                current = this.iterator.next;
                if (current == null) {
                    return null;
                }
            }
            if (current == this.end) {
                return null;
            }
            this.position = Side.ENDING;
            if (current == mid || this.getComparator().compare(current, mid) >= 0) {
                this.begin = current;
                return this.trySplit();
            }
            this.begin = mid;
            if (this.addedOnly) {
                while (!current.isAdded()) {
                    if ((current = this.nextNode(current, mid)) != mid && current != null) continue;
                    return this.trySplit();
                }
            }
            BinaryTreeNode<E> next = mid;
            if (this.addedOnly) {
                while (!next.isAdded()) {
                    if ((next = this.nextNode(next, this.end)) != this.end && next != null) continue;
                    this.begin = current;
                    this.end = mid;
                    this.position = Side.BEGINNING;
                    if (this.iterator != null) {
                        this.iterator.end = mid;
                    }
                    return this.trySplit();
                }
            }
            long sizeEst = this.sizeEstimate;
            NodeSpliterator<E> lowerSplit = new NodeSpliterator<E>(this.forward, this.comparator, Side.BEGINNING, current, mid, sizeEst >>> 1, this.changeTracker, this.addedOnly);
            this.sizeEstimate = sizeEst + 1L >>> 1;
            if (this.iterator != null) {
                lowerSplit.iterator = this.iterator;
                this.iterator.end = mid;
            }
            this.iterator = null;
            return lowerSplit;
        }

        private NodeIterator<E> createIterator() {
            return new NodeIterator<E>(this.forward, this.addedOnly, this.begin, this.end, this.changeTracker);
        }

        private NodeIterator<E> provideIterator() {
            this.changeTracker.changedSince(this.currentChange);
            NodeIterator<E> iter = this.iterator;
            if (iter == null) {
                this.iterator = iter = this.createIterator();
            }
            return this.iterator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super BinaryTreeNode<E>> action) {
            BinaryTreeNode next = this.provideIterator().nextNoThrow();
            if (next != null) {
                action.accept(next);
                return true;
            }
            if (action == null) {
                throw new NullPointerException();
            }
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super BinaryTreeNode<E>> action) {
            BinaryTreeNode next = this.provideIterator().nextNoThrow();
            if (next != null) {
                action.accept(next);
                while ((next = this.iterator.nextNoThrow()) != null) {
                    action.accept(next);
                }
            } else if (action == null) {
                throw new NullPointerException();
            }
        }

        @Override
        public long estimateSize() {
            return this.sizeEstimate;
        }

        @Override
        public int characteristics() {
            int characteristics = 277;
            if (this.position == Side.ALL) {
                characteristics |= 0x40;
            }
            return characteristics;
        }

        @Override
        public Comparator<? super BinaryTreeNode<E>> getComparator() {
            return this.comparator;
        }

        private static enum Side {
            ALL,
            BEGINNING,
            ENDING;

        }
    }

    static abstract class AbstractNodeIterator<E>
    implements Iterator<BinaryTreeNode<E>> {
        private final ChangeTracker changeTracker;
        private ChangeTracker.Change currentChange;
        BinaryTreeNode<E> current;
        BinaryTreeNode<E> next;
        BinaryTreeNode<E> end;
        BinaryOperator<BinaryTreeNode<E>> operator;

        AbstractNodeIterator(BinaryTreeNode<E> start2, BinaryTreeNode<E> end, ChangeTracker changeTracker) {
            this.end = end;
            this.changeTracker = changeTracker;
            if (changeTracker != null) {
                this.currentChange = changeTracker.getCurrent();
            }
        }

        abstract BinaryOperator<BinaryTreeNode<E>> getToNextOperation();

        BinaryTreeNode<E> getStart(BinaryTreeNode<E> start2, BinaryTreeNode<E> end, Bounds<E> bounds, boolean addedOnly) {
            if (start2 == end || start2 == null) {
                return null;
            }
            if (!(addedOnly && !start2.isAdded() || bounds != null && !bounds.isInBounds(start2.getKey()))) {
                return start2;
            }
            return this.toNext(start2);
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public BinaryTreeNode<E> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.doNext();
        }

        BinaryTreeNode<E> nextNoThrow() {
            if (!this.hasNext()) {
                return null;
            }
            return this.doNext();
        }

        BinaryTreeNode<E> doNext() {
            ChangeTracker changeTracker = this.changeTracker;
            if (changeTracker != null) {
                changeTracker.changedSince(this.currentChange);
            }
            this.current = this.next;
            this.next = this.toNext(this.next);
            return this.current;
        }

        BinaryTreeNode<E> toNext(BinaryTreeNode<E> current) {
            BinaryOperator<BinaryTreeNode<E>> op = this.getToNextOperation();
            BinaryTreeNode result = (BinaryTreeNode)op.apply(current, this.end);
            return result;
        }

        @Override
        public void remove() {
            if (this.current == null) {
                throw new IllegalStateException(BinaryTreeNode.getMessage("ipaddress.error.no.iterator.element.to.remove"));
            }
            ChangeTracker changeTracker = this.changeTracker;
            if (changeTracker != null) {
                changeTracker.changedSince(this.currentChange);
            }
            this.current.remove();
            this.current = null;
            if (changeTracker != null) {
                this.currentChange = changeTracker.getCurrent();
            }
        }
    }

    static class NodeIterator<E>
    extends AbstractNodeIterator<E> {
        final boolean forward;
        final boolean addedOnly;

        NodeIterator(boolean forward, boolean addedOnly, BinaryTreeNode<E> start2, BinaryTreeNode<E> end, ChangeTracker changeTracker) {
            super(start2, end, changeTracker);
            this.forward = forward;
            this.addedOnly = addedOnly;
            this.next = this.getStart(start2, end, null, addedOnly);
        }

        @Override
        BinaryOperator<BinaryTreeNode<E>> getToNextOperation() {
            BinaryOperator op = this.operator;
            if (op == null) {
                BinaryOperator binaryOperator = op = this.forward ? BinaryTreeNode::nextNode : BinaryTreeNode::previousNode;
                if (this.addedOnly) {
                    BinaryOperator wrappedOp = op;
                    op = (currentNode, endNode) -> ((BinaryTreeNode)currentNode).nextAdded((BinaryTreeNode)endNode, wrappedOp);
                }
                this.operator = op;
            }
            return op;
        }
    }

    static abstract class SubNodeCachingIterator<E, C>
    extends AbstractNodeIterator<E>
    implements CachingIterator<BinaryTreeNode<E>, E, C> {
        private static final int STACK_SIZE = 130;
        private C cacheItem;
        private E nextKey;
        private C nextCached;
        private Object[] stack;
        private int stackIndex = -1;
        final Bounds<E> bounds;
        final boolean addedOnly;
        final boolean isForward;

        SubNodeCachingIterator(Bounds<E> bounds, boolean isForward, boolean addedOnly, BinaryTreeNode<E> start2, BinaryTreeNode<E> end, ChangeTracker changeTracker) {
            super(start2, end, changeTracker);
            this.isForward = isForward;
            this.addedOnly = addedOnly;
            this.bounds = bounds;
            this.next = this.getStart(start2, end, bounds, addedOnly);
        }

        @Override
        BinaryTreeNode<E> doNext() {
            BinaryTreeNode result = super.doNext();
            this.populateCacheItem();
            return result;
        }

        abstract void checkCaching();

        @Override
        public C getCached() {
            this.checkCaching();
            return this.cacheItem;
        }

        void populateCacheItem() {
            E nextKey = this.nextKey;
            if (nextKey != null && this.current.getKey() == nextKey) {
                this.cacheItem = this.nextCached;
                this.nextCached = null;
                nextKey = null;
            } else {
                Object[] stack = this.stack;
                if (stack != null) {
                    int stackIndex;
                    if ((stackIndex = this.stackIndex--) >= 0 && stack[stackIndex] == this.current.getKey()) {
                        this.cacheItem = stack[stackIndex + 130];
                        stack[stackIndex + 130] = null;
                        stack[stackIndex] = null;
                    } else {
                        this.cacheItem = null;
                    }
                } else {
                    this.cacheItem = null;
                }
            }
        }

        @Override
        public boolean cacheWithLowerSubNode(C object) {
            return this.isForward ? this.cacheWithFirstSubNode(object) : this.cacheWithSecondSubNode(object);
        }

        @Override
        public boolean cacheWithUpperSubNode(C object) {
            return this.isForward ? this.cacheWithSecondSubNode(object) : this.cacheWithFirstSubNode(object);
        }

        private boolean cacheWithFirstSubNode(C object) {
            this.checkCaching();
            if (this.current != null) {
                BinaryTreeNode firstNode;
                BinaryTreeNode binaryTreeNode = firstNode = this.isForward ? this.current.getLowerSubNode() : this.current.getUpperSubNode();
                if (firstNode != null) {
                    if (this.addedOnly && !firstNode.isAdded() || this.bounds != null && !this.bounds.isInBounds(firstNode.getKey())) {
                        firstNode = (BinaryTreeNode)this.getToNextOperation().apply(firstNode, this.current);
                    }
                    if (firstNode != null) {
                        this.nextKey = firstNode.getKey();
                        this.nextCached = object;
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean cacheWithSecondSubNode(C object) {
            this.checkCaching();
            if (this.current != null) {
                BinaryTreeNode secondNode;
                BinaryTreeNode binaryTreeNode = secondNode = this.isForward ? this.current.getUpperSubNode() : this.current.getLowerSubNode();
                if (secondNode != null) {
                    if (this.addedOnly && !secondNode.isAdded() || this.bounds != null && !this.bounds.isInBounds(secondNode.getKey())) {
                        secondNode = (BinaryTreeNode)this.getToNextOperation().apply(secondNode, this.current);
                    }
                    if (secondNode != null) {
                        BinaryTreeNode firstNode;
                        BinaryTreeNode binaryTreeNode2 = firstNode = this.isForward ? this.current.getLowerSubNode() : this.current.getUpperSubNode();
                        if (firstNode == null) {
                            this.nextKey = secondNode.getKey();
                            this.nextCached = object;
                        } else {
                            if (this.stack == null) {
                                this.stack = new Object[260];
                            }
                            ++this.stackIndex;
                            this.stack[this.stackIndex] = secondNode.getKey();
                            this.stack[this.stackIndex + 130] = object;
                        }
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class PreOrderNodeIterator<E, C>
    extends SubNodeCachingIterator<E, C> {
        PreOrderNodeIterator(boolean isForward, boolean addedOnly, BinaryTreeNode<E> start2, BinaryTreeNode<E> bound2, ChangeTracker changeTracker) {
            this(null, isForward, addedOnly, start2, bound2, changeTracker);
        }

        PreOrderNodeIterator(Bounds<E> bounds, boolean isForward, boolean addedOnly, BinaryTreeNode<E> start2, BinaryTreeNode<E> end, ChangeTracker changeTracker) {
            super(bounds, isForward, addedOnly, start2, end, changeTracker);
        }

        @Override
        BinaryOperator<BinaryTreeNode<E>> getToNextOperation() {
            BinaryOperator op = this.operator;
            if (op == null) {
                BinaryOperator wrappedOp;
                BinaryOperator binaryOperator = op = this.isForward ? BinaryTreeNode::nextPreOrderNode : BinaryTreeNode::previousPreOrderNode;
                if (this.addedOnly) {
                    wrappedOp = op;
                    op = (currentNode, endNode) -> ((BinaryTreeNode)currentNode).nextAdded((BinaryTreeNode)endNode, wrappedOp);
                }
                if (this.bounds != null) {
                    wrappedOp = op;
                    op = (currentNode, endNode) -> ((BinaryTreeNode)currentNode).nextInBounds((BinaryTreeNode)endNode, wrappedOp, this.bounds);
                }
                this.operator = op;
            }
            return op;
        }

        @Override
        void checkCaching() {
            if (!this.isForward) {
                throw new Error();
            }
        }

        @Override
        void populateCacheItem() {
            if (this.isForward) {
                super.populateCacheItem();
            }
        }

        @Override
        public void remove() {
            if (this.isForward || this.addedOnly) {
                super.remove();
                return;
            }
            throw new UnsupportedOperationException();
        }
    }

    static class PostOrderNodeIterator<E, C>
    extends SubNodeCachingIterator<E, C> {
        PostOrderNodeIterator(boolean isForward, boolean addedOnly, BinaryTreeNode<E> start2, BinaryTreeNode<E> bound2, ChangeTracker changeTracker) {
            this(null, isForward, addedOnly, start2, bound2, changeTracker);
        }

        PostOrderNodeIterator(Bounds<E> bounds, boolean isForward, boolean addedOnly, BinaryTreeNode<E> start2, BinaryTreeNode<E> end, ChangeTracker changeTracker) {
            super(bounds, isForward, addedOnly, start2, end, changeTracker);
        }

        @Override
        void checkCaching() {
            if (this.isForward) {
                throw new Error();
            }
        }

        @Override
        void populateCacheItem() {
            if (!this.isForward) {
                super.populateCacheItem();
            }
        }

        @Override
        BinaryOperator<BinaryTreeNode<E>> getToNextOperation() {
            BinaryOperator op = this.operator;
            if (op == null) {
                BinaryOperator wrappedOp;
                BinaryOperator binaryOperator = op = this.isForward ? BinaryTreeNode::nextPostOrderNode : BinaryTreeNode::previousPostOrderNode;
                if (this.addedOnly) {
                    wrappedOp = op;
                    op = (currentNode, endNode) -> ((BinaryTreeNode)currentNode).nextAdded((BinaryTreeNode)endNode, wrappedOp);
                }
                if (this.bounds != null) {
                    wrappedOp = op;
                    op = (currentNode, endNode) -> ((BinaryTreeNode)currentNode).nextInBounds((BinaryTreeNode)endNode, wrappedOp, this.bounds);
                }
                this.operator = op;
            }
            return op;
        }

        @Override
        public void remove() {
            if (!this.isForward || this.addedOnly) {
                super.remove();
                return;
            }
            throw new UnsupportedOperationException();
        }
    }

    static class BlockSizeCachingNodeIterator<E, C>
    extends AbstractNodeIterator<E>
    implements CachingIterator<BinaryTreeNode<E>, E, C> {
        private static final Comparator<?> COMP = new Comp(false);
        private static final Comparator<?> REVERSE_COMP = new Comp(true);
        private PriorityQueue<Cached<E, C>> queue;
        private C cacheItem;
        private Cached<E, C> nextCachedItem;
        private Cached<E, C> lowerCacheObj;
        private Cached<E, C> upperCacheObj;

        BlockSizeCachingNodeIterator(int treeSize, BinaryTreeNode<E> start2, boolean reverseBlocksEqualSize, ChangeTracker changeTracker) {
            super(start2, null, changeTracker);
            Comparator<?> comp = reverseBlocksEqualSize ? REVERSE_COMP : COMP;
            this.queue = treeSize == 0 ? new PriorityQueue(comp) : new PriorityQueue(treeSize >> 1, comp);
            this.next = this.getStart(start2, null, null, false);
        }

        BlockSizeCachingNodeIterator(BinaryTreeNode<E> start2, boolean reverseBlocksEqualSize, ChangeTracker changeTracker) {
            this(0, start2, reverseBlocksEqualSize, changeTracker);
        }

        @Override
        BinaryOperator<BinaryTreeNode<E>> getToNextOperation() {
            BinaryOperator op = this.operator;
            if (op == null) {
                this.operator = op = (currentNode, endNode) -> {
                    BinaryTreeNode node;
                    Cached cached;
                    BinaryTreeNode lower = currentNode.getLowerSubNode();
                    if (lower != null) {
                        Cached cached2 = new Cached();
                        cached2.node = lower;
                        this.lowerCacheObj = cached2;
                        this.queue.add(cached2);
                    } else {
                        this.lowerCacheObj = null;
                    }
                    BinaryTreeNode upper = currentNode.getUpperSubNode();
                    if (upper != null) {
                        cached = new Cached();
                        cached.node = upper;
                        this.upperCacheObj = cached;
                        this.queue.add(cached);
                    } else {
                        this.upperCacheObj = null;
                    }
                    if (this.nextCachedItem != null) {
                        this.cacheItem = this.nextCachedItem.cached;
                    }
                    if ((cached = this.queue.poll()) != null && (node = cached.node) != endNode) {
                        this.nextCachedItem = cached;
                        return node;
                    }
                    this.nextCachedItem = null;
                    return null;
                };
            }
            return op;
        }

        @Override
        public C getCached() {
            return this.cacheItem;
        }

        @Override
        public boolean cacheWithLowerSubNode(C object) {
            if (this.lowerCacheObj != null) {
                this.lowerCacheObj.cached = object;
                return true;
            }
            return false;
        }

        @Override
        public boolean cacheWithUpperSubNode(C object) {
            if (this.upperCacheObj != null) {
                this.upperCacheObj.cached = object;
                return true;
            }
            return false;
        }

        static class Cached<E, C> {
            BinaryTreeNode<E> node;
            C cached;

            Cached() {
            }
        }

        static class Comp<E extends Address>
        implements Comparator<Cached<E, ?>> {
            private final boolean reverseBlocksEqualSize;

            Comp(boolean reverseBlocksEqualSize) {
                this.reverseBlocksEqualSize = reverseBlocksEqualSize;
            }

            @Override
            public int compare(Cached<E, ?> o1, Cached<E, ?> o2) {
                Address addr2;
                BinaryTreeNode node1 = o1.node;
                BinaryTreeNode node2 = o2.node;
                Address addr1 = (Address)node1.getKey();
                if (addr1 == (addr2 = (Address)node2.getKey())) {
                    return 0;
                }
                if (addr1.isPrefixed()) {
                    if (addr2.isPrefixed()) {
                        int val = addr1.getPrefixLength() - addr2.getPrefixLength();
                        if (val == 0) {
                            int compVal = BinaryTreeNode.compareLowValues(addr1, addr2);
                            return this.reverseBlocksEqualSize ? -compVal : compVal;
                        }
                        return val;
                    }
                    return -1;
                }
                if (addr2.isPrefixed()) {
                    return 1;
                }
                int compVal = BinaryTreeNode.compareLowValues(addr1, addr2);
                return this.reverseBlocksEqualSize ? -compVal : compVal;
            }
        }
    }

    static class BlockSizeNodeIterator<E>
    extends AbstractNodeIterator<E> {
        private static final Comparator<?> COMP = new Comp(false);
        private static final Comparator<?> REVERSE_COMP = new Comp(true);
        PriorityQueue<BinaryTreeNode<E>> queue;
        private final boolean addedOnly;
        private final Bounds<E> bounds;

        BlockSizeNodeIterator(int treeSize, boolean addedOnly, BinaryTreeNode<E> start2, boolean reverseBlocksEqualSize, ChangeTracker changeTracker) {
            this(treeSize, null, addedOnly, start2, reverseBlocksEqualSize, changeTracker);
        }

        BlockSizeNodeIterator(int treeSize, Bounds<E> bounds, boolean addedOnly, BinaryTreeNode<E> start2, boolean reverseBlocksEqualSize, ChangeTracker changeTracker) {
            super(start2, null, changeTracker);
            Comparator<?> comp;
            this.addedOnly = addedOnly;
            this.bounds = bounds;
            Comparator<?> comparator = comp = reverseBlocksEqualSize ? REVERSE_COMP : COMP;
            if (treeSize > 0) {
                int initialCapacity = treeSize >> 1;
                if (initialCapacity == 0) {
                    initialCapacity = 1;
                }
                this.queue = new PriorityQueue(initialCapacity, comp);
            } else {
                this.queue = new PriorityQueue(comp);
            }
            this.next = this.getStart(start2, null, bounds, addedOnly);
        }

        @Override
        BinaryOperator<BinaryTreeNode<E>> getToNextOperation() {
            BinaryOperator op = this.operator;
            if (op == null) {
                BinaryOperator wrappedOp;
                op = (currentNode, endNode) -> {
                    BinaryTreeNode<E> node;
                    BinaryTreeNode upper;
                    BinaryTreeNode lower = currentNode.getLowerSubNode();
                    if (lower != null) {
                        this.queue.add(lower);
                    }
                    if ((upper = currentNode.getUpperSubNode()) != null) {
                        this.queue.add(upper);
                    }
                    return (node = this.queue.poll()) == endNode ? null : node;
                };
                if (this.addedOnly) {
                    wrappedOp = op;
                    op = (currentNode, endNode) -> ((BinaryTreeNode)currentNode).nextAdded((BinaryTreeNode)endNode, wrappedOp);
                }
                if (this.bounds != null) {
                    wrappedOp = op;
                    op = (currentNode, endNode) -> ((BinaryTreeNode)currentNode).nextInBounds((BinaryTreeNode)endNode, wrappedOp, this.bounds);
                }
                this.operator = op;
            }
            return op;
        }

        static class Comp<E extends Address>
        implements Comparator<BinaryTreeNode<E>> {
            private final boolean reverseBlocksEqualSize;

            Comp(boolean reverseBlocksEqualSize) {
                this.reverseBlocksEqualSize = reverseBlocksEqualSize;
            }

            @Override
            public int compare(BinaryTreeNode<E> node1, BinaryTreeNode<E> node2) {
                Address addr2;
                Address addr1 = (Address)node1.getKey();
                if (addr1 == (addr2 = (Address)node2.getKey())) {
                    return 0;
                }
                if (addr1.isPrefixed()) {
                    if (addr2.isPrefixed()) {
                        int val = addr1.getPrefixLength() - addr2.getPrefixLength();
                        if (val == 0) {
                            int compVal = BinaryTreeNode.compareLowValues(addr1, addr2);
                            return this.reverseBlocksEqualSize ? -compVal : compVal;
                        }
                        return val;
                    }
                    return -1;
                }
                if (addr2.isPrefixed()) {
                    return 1;
                }
                int compVal = BinaryTreeNode.compareLowValues(addr1, addr2);
                return this.reverseBlocksEqualSize ? -compVal : compVal;
            }
        }
    }

    public static interface CachingIterator<N extends BinaryTreeNode<E>, E, C>
    extends Iterator<N> {
        public C getCached();

        public boolean cacheWithLowerSubNode(C var1);

        public boolean cacheWithUpperSubNode(C var1);
    }

    static class KeyIterator<E>
    implements Iterator<E> {
        private Iterator<? extends BinaryTreeNode<E>> iterator;

        KeyIterator(Iterator<? extends BinaryTreeNode<E>> iterator2) {
            this.iterator = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public E next() {
            return this.iterator.next().getKey();
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }
    }

    static class ChangeTracker
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private Change currentChange = new Change();

        ChangeTracker() {
        }

        void changedSince(Change change) throws ConcurrentModificationException {
            if (this.isChangedSince(change)) {
                throw new ConcurrentModificationException();
            }
        }

        boolean isChangedSince(Change otherChange) {
            return !this.currentChange.equalsChange(otherChange);
        }

        Change getCurrent() {
            Change change = this.currentChange;
            change.shared = true;
            return change;
        }

        void changed() {
            Change change = this.currentChange;
            if (change.shared) {
                change = change.clone();
                change.shared = false;
                change.increment();
                this.currentChange = change;
            }
        }

        public String toString() {
            return "current change: " + this.currentChange;
        }

        static class Change
        implements Cloneable,
        Serializable {
            private static final long serialVersionUID = 1L;
            boolean shared;
            private BigInteger big = BigInteger.ZERO;
            private int small;

            Change() {
            }

            void increment() {
                if (++this.small == 0) {
                    this.big = this.big.add(BigInteger.ONE);
                }
            }

            public boolean equals(Object o) {
                return o instanceof Change && this.equalsChange((Change)o);
            }

            public boolean equalsChange(Change change) {
                return this.small == change.small && this.big.equals(change.big);
            }

            public Change clone() {
                try {
                    return (Change)super.clone();
                }
                catch (CloneNotSupportedException cannotHappen) {
                    return null;
                }
            }

            public String toString() {
                return this.big + " " + this.small;
            }
        }
    }

    static class Bounds<E>
    implements Serializable {
        private static final long serialVersionUID = 1L;
        final Comparator<? super E> comparator;
        final E lowerBound;
        final E upperBound;
        final boolean lowerInclusive;
        final boolean upperInclusive;

        Bounds(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive, Comparator<? super E> comparator) {
            if (comparator == null) {
                throw new NullPointerException();
            }
            this.comparator = comparator;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.lowerInclusive = lowerInclusive;
            this.upperInclusive = upperInclusive;
            if (upperBound != null && this.isBelowLowerBound(upperBound)) {
                throw new IllegalArgumentException(BinaryTreeNode.getMessage("ipaddress.error.address.lower.exceeds.upper") + " " + lowerBound + ", " + upperBound);
            }
        }

        Bounds<E> restrict(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive) {
            return this.restrict(lowerBound, lowerInclusive, upperBound, upperInclusive, true);
        }

        Bounds<E> restrict(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive, boolean thro) {
            BoundsCheck check2;
            if (lowerBound != null) {
                check2 = this.compareToLowerBound(lowerBound, lowerInclusive);
                if (check2.isLessRestrictive()) {
                    if (thro) {
                        throw new IllegalArgumentException(BinaryTreeNode.getMessage("ipaddress.error.lower.below.range") + " " + lowerBound);
                    }
                    lowerBound = null;
                } else if (!check2.isMoreRestrictive() && check2 != BoundsCheck.EQUIVALENT_TO_INCLUSIVE) {
                    lowerBound = null;
                }
            }
            if (upperBound != null) {
                check2 = this.compareToUpperBound(upperBound, upperInclusive);
                if (check2.isLessRestrictive()) {
                    if (thro) {
                        throw new IllegalArgumentException(BinaryTreeNode.getMessage("ipaddress.error.lower.above.range") + " " + upperBound);
                    }
                    upperBound = null;
                } else if (!check2.isMoreRestrictive() && check2 != BoundsCheck.EQUIVALENT_TO_INCLUSIVE) {
                    upperBound = null;
                }
            }
            if (lowerBound == null) {
                if (upperBound == null) {
                    return null;
                }
                lowerBound = this.lowerBound;
                lowerInclusive = this.lowerInclusive;
            }
            if (upperBound == null) {
                upperBound = this.upperBound;
                upperInclusive = this.upperInclusive;
            }
            return this.createBounds(lowerBound, lowerInclusive, upperBound, upperInclusive, this.comparator);
        }

        Bounds<E> intersect(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive) {
            Bounds<E> newBounds = this.restrict(lowerBound, lowerInclusive, upperBound, upperInclusive, false);
            if (newBounds == null) {
                return this;
            }
            return newBounds;
        }

        Bounds<E> createBounds(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive, Comparator<? super E> comparator) {
            return new Bounds<E>(lowerBound, lowerInclusive, upperBound, upperInclusive, comparator);
        }

        public boolean isInBounds(E addr) {
            return this.isWithinLowerBound(addr) && this.isWithinUpperBound(addr);
        }

        public E getLowerBound() {
            return this.lowerBound;
        }

        public E getUpperBound() {
            return this.upperBound;
        }

        public boolean lowerIsInclusive() {
            return this.lowerInclusive;
        }

        public boolean upperIsInclusive() {
            return this.upperInclusive;
        }

        public boolean isLowerBounded() {
            return this.lowerBound != null;
        }

        public boolean isUpperBounded() {
            return this.upperBound != null;
        }

        public boolean isUnbounded() {
            return !this.isLowerBounded() && !this.isUpperBounded();
        }

        private int compare(E one, E two) {
            return this.comparator.compare(one, two);
        }

        public boolean isBelowLowerBound(E addr) {
            return this.isLowerBounded() && (this.lowerInclusive ? this.compare(addr, this.lowerBound) < 0 : this.compare(addr, this.lowerBound) <= 0);
        }

        public boolean isWithinLowerBound(E addr) {
            return !this.isBelowLowerBound(addr);
        }

        public boolean isAboveUpperBound(E addr) {
            return this.isUpperBounded() && (this.upperInclusive ? this.compare(addr, this.upperBound) > 0 : this.compare(addr, this.upperBound) >= 0);
        }

        public boolean isWithinUpperBound(E addr) {
            return !this.isAboveUpperBound(addr);
        }

        BoundsCheck compareToLowerBound(E addr, boolean inclusive) {
            if (this.isLowerBounded()) {
                if (inclusive) {
                    if (this.lowerInclusive) {
                        return BoundsCheck.convertEquivBoundaryComparison(this.compare(this.lowerBound, addr));
                    }
                    int comp = this.compare(this.lowerBound, addr);
                    if (comp >= 0) {
                        return BoundsCheck.OUTSIDE;
                    }
                    if (this.isAdjacentAboveLowerBound(addr)) {
                        return BoundsCheck.EQUIVALENT_TO_EXCLUSIVE;
                    }
                    return BoundsCheck.INSIDE;
                }
                if (this.lowerInclusive) {
                    int comp = this.compare(this.lowerBound, addr);
                    if (comp <= 0) {
                        return BoundsCheck.INSIDE;
                    }
                    if (this.isAdjacentBelowLowerBound(addr)) {
                        return BoundsCheck.EQUIVALENT_TO_INCLUSIVE;
                    }
                    return BoundsCheck.OUTSIDE;
                }
                return BoundsCheck.convertEquivBoundaryComparison(this.compare(this.lowerBound, addr));
            }
            if (inclusive && this.isMin(addr)) {
                return BoundsCheck.EQUIVALENT_TO_UNBOUNDED;
            }
            return BoundsCheck.INSIDE;
        }

        BoundsCheck compareToUpperBound(E addr, boolean inclusive) {
            if (this.isUpperBounded()) {
                if (inclusive) {
                    if (this.upperInclusive) {
                        return BoundsCheck.convertEquivBoundaryComparison(this.compare(addr, this.upperBound));
                    }
                    int comp = this.compare(addr, this.upperBound);
                    if (comp >= 0) {
                        return BoundsCheck.OUTSIDE;
                    }
                    if (this.isAdjacentBelowUpperBound(addr)) {
                        return BoundsCheck.EQUIVALENT_TO_EXCLUSIVE;
                    }
                    return BoundsCheck.INSIDE;
                }
                if (this.upperInclusive) {
                    int comp = this.compare(addr, this.upperBound);
                    if (comp <= 0) {
                        return BoundsCheck.INSIDE;
                    }
                    if (this.isAdjacentAboveUpperBound(addr)) {
                        return BoundsCheck.EQUIVALENT_TO_INCLUSIVE;
                    }
                    return BoundsCheck.OUTSIDE;
                }
                return BoundsCheck.convertEquivBoundaryComparison(this.compare(addr, this.upperBound));
            }
            if (inclusive && this.isMax(addr)) {
                return BoundsCheck.EQUIVALENT_TO_UNBOUNDED;
            }
            return BoundsCheck.INSIDE;
        }

        boolean isMax(E addr) {
            return false;
        }

        boolean isMin(E addr) {
            return false;
        }

        boolean isAdjacentAboveUpperBound(E addr) {
            return false;
        }

        boolean isAdjacentBelowUpperBound(E addr) {
            return false;
        }

        boolean isAdjacentAboveLowerBound(E addr) {
            return false;
        }

        boolean isAdjacentBelowLowerBound(E addr) {
            return false;
        }

        public String toCanonicalString() {
            return this.toCanonicalString(" -> ");
        }

        public String toCanonicalString(String separator) {
            Function<Object, String> stringer = Object::toString;
            return this.toString(stringer, separator, stringer);
        }

        public String toString(Function<? super E, String> lowerStringer, String separator, Function<? super E, String> upperStringer) {
            return Bounds.toString(this.getLowerBound(), this.lowerIsInclusive(), this.getUpperBound(), this.upperIsInclusive(), lowerStringer, separator, upperStringer);
        }

        static <E> String toString(E lower, boolean lowerIsInclusive, E upper, boolean upperIsInclusive, Function<? super E, String> lowerStringer, String separator, Function<? super E, String> upperStringer) {
            String upperStr;
            String lowerStr;
            if (lower == null) {
                lowerStr = "";
            } else {
                lowerStr = lowerStringer.apply(lower);
                lowerStr = lowerIsInclusive ? '[' + lowerStr : '(' + lowerStr;
            }
            if (upper == null) {
                upperStr = "";
            } else {
                upperStr = upperStringer.apply(upper);
                upperStr = upperIsInclusive ? upperStr + ']' : upperStr + ')';
            }
            return lowerStr + separator + upperStr;
        }

        public String toString() {
            return this.toCanonicalString();
        }

        static enum BoundsCheck {
            INSIDE(false, true),
            EQUIVALENT_TO_UNBOUNDED(false, false),
            EQUIVALENT_TO_EXCLUSIVE(false, false),
            EQUIVALENT_TO_INCLUSIVE(false, false),
            SAME(false, false),
            OUTSIDE(true, false);

            private boolean less;
            private boolean more;

            private BoundsCheck(boolean lessRestrictive, boolean moreRestrictive) {
                this.less = lessRestrictive;
                this.more = moreRestrictive;
            }

            boolean isLessRestrictive() {
                return this.less;
            }

            boolean isMoreRestrictive() {
                return this.more;
            }

            static BoundsCheck convertEquivBoundaryComparison(int comparison) {
                if (comparison > 0) {
                    return OUTSIDE;
                }
                if (comparison < 0) {
                    return INSIDE;
                }
                return SAME;
            }
        }
    }
}

