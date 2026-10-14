/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.AddressSegment;
import inet.ipaddr.AddressSegmentSeries;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSegment;
import inet.ipaddr.format.util.AbstractTree;
import inet.ipaddr.format.util.AddedTreeBase;
import inet.ipaddr.format.util.AddressTrieOps;
import inet.ipaddr.format.util.AddressTrieSet;
import inet.ipaddr.format.util.AssociativeAddressTrie;
import inet.ipaddr.format.util.BinaryTreeNode;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Function;

public abstract class AddressTrie<E extends Address>
extends AbstractTree<E> {
    private static final long serialVersionUID = 1L;
    static final TrieComparator<?> comparator = new TrieComparator(new AddressComparator());
    static final TrieComparator<?> reverseComparator = new TrieComparator(Collections.reverseOrder(new AddressComparator()));
    AddressTrieSet<E> set;
    AddressBounds<E> bounds;
    private TrieNode<E> subRoot;
    private BinaryTreeNode.ChangeTracker.Change subRootChange;

    public static <E extends Address> E increment(E addr) {
        if (addr.isMax()) {
            return null;
        }
        if (addr.isIPAddress()) {
            IPAddress ipaddr = addr.toIPAddress();
            if (addr.isPrefixed()) {
                return (E)ipaddr.getUpper().setPrefixLength(ipaddr.getPrefixLength() + 1).toZeroHost();
            }
            return (E)ipaddr.toPrefixBlock(ipaddr.getBitCount() - (ipaddr.getTrailingBitCount(false) + 1));
        }
        if (addr.isPrefixed()) {
            return (E)addr.getUpper().setPrefixLength(addr.getPrefixLength() + 1).toPrefixBlock().getLower();
        }
        int trailingBitCount = 0;
        for (int i = addr.getSegmentCount() - 1; i >= 0; --i) {
            AddressSegment seg = addr.getSegment(i);
            if (!seg.isMax()) {
                trailingBitCount += Integer.numberOfTrailingZeros(~seg.getSegmentValue());
                break;
            }
            trailingBitCount += seg.getBitCount();
        }
        return (E)addr.setPrefixLength(addr.getBitCount() - (trailingBitCount + 1)).toPrefixBlock();
    }

    public static <E extends Address> E decrement(E addr) {
        if (addr.isZero()) {
            return null;
        }
        if (addr.isIPAddress()) {
            IPAddress ipaddr = addr.toIPAddress();
            if (addr.isPrefixed()) {
                return (E)ipaddr.getLower().setPrefixLength(ipaddr.getPrefixLength() + 1).toMaxHost();
            }
            return (E)ipaddr.toPrefixBlock(ipaddr.getBitCount() - (ipaddr.getTrailingBitCount(true) + 1));
        }
        if (addr.isPrefixed()) {
            return (E)addr.getLower().setPrefixLength(addr.getPrefixLength() + 1).toPrefixBlock().getUpper();
        }
        int trailingBitCount = 0;
        for (int i = addr.getSegmentCount() - 1; i >= 0; --i) {
            AddressSegment seg = addr.getSegment(i);
            if (!seg.isZero()) {
                trailingBitCount += Integer.numberOfTrailingZeros(seg.getSegmentValue());
                break;
            }
            trailingBitCount += seg.getBitCount();
        }
        return (E)addr.setPrefixLength(addr.getBitCount() - (trailingBitCount + 1)).toPrefixBlock();
    }

    protected AddressTrie(TrieNode<E> root) {
        super(root);
        root.changeTracker = new BinaryTreeNode.ChangeTracker();
    }

    protected AddressTrie(TrieNode<E> root, AddressBounds<E> bounds) {
        super(root);
        if (root.changeTracker == null) {
            root.changeTracker = new BinaryTreeNode.ChangeTracker();
        }
        this.bounds = bounds;
    }

    private static Integer getSegmentPrefLen(AddressSegmentSeries addr, int bitsMatchedSoFar, AddressSegment segment) {
        int existingPrefLen;
        if (segment instanceof IPAddressSegment) {
            return ((IPAddressSegment)segment).getSegmentPrefixLength();
        }
        if (addr.isPrefixed() && (existingPrefLen = addr.getPrefixLength().intValue()) <= bitsMatchedSoFar + addr.getBitsPerSegment()) {
            Integer result = existingPrefLen - bitsMatchedSoFar;
            if (result < 0) {
                result = 0;
            }
            return result;
        }
        return null;
    }

    private static int getMatchingBits(AddressSegment segment1, AddressSegment segment2, int maxBits, int adjustment) {
        if (maxBits == 0) {
            return 0;
        }
        int val1 = segment1.getSegmentValue();
        int val2 = segment2.getSegmentValue();
        int xor = val1 ^ val2;
        if (adjustment == 16) {
            return AddressTrie.numberOfLeadingZerosShort(xor);
        }
        if (adjustment == 24) {
            return AddressTrie.numberOfLeadingZerosByte(xor);
        }
        return Integer.numberOfLeadingZeros(xor) - adjustment;
    }

    private static int numberOfLeadingZerosShort(int i) {
        int half = i >>> 8;
        if (half == 0) {
            return 8 + AddressTrie.numberOfLeadingZerosByte(i & 0xFF);
        }
        return AddressTrie.numberOfLeadingZerosByte(half);
    }

    private static int numberOfLeadingZerosByte(int i) {
        if (i <= 0) {
            if (i == 0) {
                return 8;
            }
            return 0;
        }
        int n = 1;
        if (i >>> 4 == 0) {
            n += 4;
            i <<= 4;
        }
        if (i >>> 6 == 0) {
            n += 2;
            i <<= 2;
        }
        return n -= i >>> 7;
    }

    @Override
    public boolean isEmpty() {
        if (this.bounds == null) {
            return super.isEmpty();
        }
        return this.firstAddedNode() == null;
    }

    @Override
    public int nodeSize() {
        if (this.bounds == null) {
            return super.nodeSize();
        }
        int totalCount = 0;
        Iterator<TrieNode<E>> iterator2 = this.allNodeIterator(true);
        while (iterator2.hasNext()) {
            ++totalCount;
            iterator2.next();
        }
        return totalCount;
    }

    @Override
    public int size() {
        if (this.bounds == null) {
            return super.size();
        }
        int totalCount = 0;
        Iterator<TrieNode<E>> iterator2 = this.nodeIterator(true);
        while (iterator2.hasNext()) {
            TrieNode<E> node = iterator2.next();
            if (!node.isAdded() || !this.bounds.isInBounds((Address)node.getKey())) continue;
            ++totalCount;
        }
        return totalCount;
    }

    @Override
    public boolean add(E addr) {
        addr = AddressTrie.checkBlockOrAddress(addr, true);
        if (this.bounds != null && !this.bounds.isInBounds(addr)) {
            AddressTrie.throwOutOfBounds();
        }
        this.adjustRoot(addr);
        TrieNode<E> root = this.absoluteRoot();
        OpResult<E> result = new OpResult<E>(addr, Operation.INSERT);
        root.matchBits(result);
        return !result.exists;
    }

    static void throwOutOfBounds() {
        throw new IllegalArgumentException(AddressTrie.getMessage("ipaddress.error.address.out.of.range"));
    }

    protected void adjustRoot(E addr) {
    }

    @Override
    public TrieNode<E> addNode(E addr) {
        addr = AddressTrie.checkBlockOrAddress(addr, true);
        if (this.bounds != null && !this.bounds.isInBounds(addr)) {
            AddressTrie.throwOutOfBounds();
        }
        this.adjustRoot(addr);
        TrieNode<E> root = this.absoluteRoot();
        OpResult<E> result = new OpResult<E>(addr, Operation.INSERT);
        root.matchBits(result);
        TrieNode node = result.existingNode;
        if (node == null) {
            node = result.inserted;
        }
        return node;
    }

    public abstract AddedTreeBase<E, ? extends SubNodesMapping<E, ? extends SubNodesMapping<E, ?>>> constructAddedNodesTree();

    protected void contructAddedTree(AssociativeAddressTrie<E, SubNodesMappingBasic<E>> emptyTrie) {
        emptyTrie.addTrie(this.absoluteRoot());
        BinaryTreeNode.CachingIterator<AssociativeAddressTrie.AssociativeTrieNode<E, SubNodesMappingBasic<E>>, E, AssociativeAddressTrie.AssociativeTrieNode> cachingIterator = emptyTrie.containingFirstAllNodeIterator(true);
        while (cachingIterator.hasNext()) {
            TrieNode parentParent;
            TrieNode parent;
            AssociativeAddressTrie.AssociativeTrieNode newNext = (AssociativeAddressTrie.AssociativeTrieNode)cachingIterator.next();
            newNext.setValue(new SubNodesMappingBasic());
            cachingIterator.cacheWithLowerSubNode(newNext);
            cachingIterator.cacheWithUpperSubNode(newNext);
            if (!newNext.isAdded() || (parent = (AssociativeAddressTrie.AssociativeTrieNode)cachingIterator.getCached()) == null) continue;
            while (!parent.isAdded() && (parentParent = parent.getParent()) != null) {
                parent = parentParent;
            }
            SubNodesMappingBasic mappedNodes = (SubNodesMappingBasic)parent.getValue();
            ArrayList<AssociativeAddressTrie.AssociativeTrieNode> addedSubs = mappedNodes.subNodes;
            if (addedSubs == null) {
                mappedNodes.subNodes = addedSubs = new ArrayList<AssociativeAddressTrie.AssociativeTrieNode>(newNext.size() - 1);
            }
            addedSubs.add(newNext);
        }
        SubNodesMappingBasic value = (SubNodesMappingBasic)((AssociativeAddressTrie.AssociativeTrieNode)emptyTrie.getRoot()).getValue();
        if (value != null && value.subNodes != null) {
            value.subNodes.trimToSize();
        }
        Iterator<AssociativeAddressTrie.AssociativeTrieNode<E, SubNodesMappingBasic<E>>> iter = emptyTrie.allNodeIterator(true);
        while (iter.hasNext()) {
            SubNodesMappingBasic<E> list = iter.next().getValue();
            if (list == null || list.subNodes == null) continue;
            list.subNodes.trimToSize();
        }
    }

    public abstract String toAddedNodesTreeString();

    protected static <E extends Address, N extends SubNodesMapping<E, N>> String toAddedNodesTreeString(AssociativeAddressTrie<E, N> addedTree) {
        TrieNode root = addedTree.absoluteRoot();
        return AddressTrie.toAddedNodesTreeString(root);
    }

    protected static <E extends Address, N extends SubNodesMapping<E, N>> String toAddedNodesTreeString(AssociativeAddressTrie.AssociativeTrieNode<E, N> root) {
        class IndentsNode {
            BinaryTreeNode.Indents indents;
            AssociativeAddressTrie.AssociativeTrieNode<E, N> node;

            IndentsNode(BinaryTreeNode.Indents indents, AssociativeAddressTrie.AssociativeTrieNode<E, N> node) {
                this.indents = indents;
                this.node = node;
            }
        }
        ArrayDeque<IndentsNode> stack = null;
        StringBuilder builder = new StringBuilder();
        builder.append('\n');
        AssociativeAddressTrie.AssociativeTrieNode<E, N> nextNode = root;
        String nodeIndent = "";
        String subNodeIndent = "";
        while (true) {
            IndentsNode nextItem;
            SubNodesMapping nextNodeList = (SubNodesMapping)nextNode.getValue();
            TrieNode.toNodeString(builder.append(nodeIndent), nextNode.isAdded(), (Address)nextNode.getKey(), nextNodeList.getUnderlyingValue()).append('\n');
            ArrayList nextNodes = nextNodeList.subNodes;
            if (nextNodes != null && nextNodes.size() > 0) {
                AssociativeAddressTrie.AssociativeTrieNode nNode;
                int i = nextNodes.size() - 1;
                BinaryTreeNode.Indents lastIndents = new BinaryTreeNode.Indents(subNodeIndent + "\u2514\u2500", subNodeIndent + "  ");
                AssociativeAddressTrie.AssociativeTrieNode next = nNode = nextNodes.get(i);
                if (stack == null) {
                    stack = new ArrayDeque<IndentsNode>(root.size());
                }
                stack.addFirst(new IndentsNode(lastIndents, next));
                if (nextNodes.size() > 1) {
                    BinaryTreeNode.Indents firstIndents = new BinaryTreeNode.Indents(subNodeIndent + "\u251c\u2500", subNodeIndent + "\u2502 ");
                    --i;
                    while (i >= 0) {
                        next = nNode = nextNodes.get(i);
                        stack.addFirst(new IndentsNode(firstIndents, next));
                        --i;
                    }
                }
            }
            if (stack == null || (nextItem = (IndentsNode)stack.pollFirst()) == null) break;
            nextNode = nextItem.node;
            BinaryTreeNode.Indents nextIndents = nextItem.indents;
            nodeIndent = nextIndents.nodeIndent;
            subNodeIndent = nextIndents.subNodeInd;
        }
        return builder.toString();
    }

    TrieNode<E> addNode(OpResult<E> result, TrieNode<E> fromNode, TrieNode<E> nodeToAdd, boolean withValues) {
        fromNode.matchBits(((Address)fromNode.getKey()).getPrefixLength(), result);
        TrieNode node = result.existingNode;
        return node == null ? result.inserted : node;
    }

    TrieNode<E> addTrie(TrieNode<E> tree, boolean withValues) {
        TrieNode<Address> firstNode;
        BinaryTreeNode.CachingIterator<TrieNode<E>, E, TrieNode<Address>> iterator2 = tree.containingFirstAllNodeIterator(true);
        TrieNode toAdd = (TrieNode)iterator2.next();
        OpResult<Address> result = new OpResult<Address>((Address)toAdd.getKey(), Operation.INSERT);
        TrieNode<Address> root = this.absoluteRoot();
        boolean firstAdded = toAdd.isAdded();
        boolean addedOne = false;
        if (firstAdded) {
            addedOne = true;
            this.adjustRoot((Address)toAdd.getKey());
            firstNode = this.addNode(result, root, toAdd, withValues);
        } else {
            firstNode = root;
        }
        TrieNode<Address> lastAddedNode = firstNode;
        while (iterator2.hasNext()) {
            iterator2.cacheWithLowerSubNode(lastAddedNode);
            iterator2.cacheWithUpperSubNode(lastAddedNode);
            toAdd = (TrieNode)iterator2.next();
            TrieNode<Address> cachedNode = (TrieNode<Address>)iterator2.getCached();
            if (toAdd.isAdded()) {
                Address addrNext = (Address)toAdd.getKey();
                if (!addedOne) {
                    addedOne = true;
                    this.adjustRoot(addrNext);
                }
                result.addr = addrNext;
                result.existingNode = null;
                result.inserted = null;
                lastAddedNode = this.addNode(result, cachedNode, toAdd, withValues);
                continue;
            }
            lastAddedNode = cachedNode;
        }
        if (!firstAdded) {
            firstNode = this.getNode((Address)tree.getKey());
        }
        return firstNode;
    }

    @Override
    public TrieNode<E> addTrie(TrieNode<E> trie) {
        return this.addTrie(trie, false);
    }

    @Override
    public boolean contains(E addr) {
        if (this.bounds != null && !this.bounds.isInBounds(addr = AddressTrie.checkBlockOrAddress(addr, true))) {
            return false;
        }
        return this.absoluteRoot().contains(addr);
    }

    @Override
    public boolean remove(E addr) {
        if (this.bounds != null && !this.bounds.isInBounds(addr = AddressTrie.checkBlockOrAddress(addr, true))) {
            return false;
        }
        return this.absoluteRoot().remove(addr);
    }

    @Override
    public TrieNode<E> removeElementsContainedBy(E addr) {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().removeElementsContainedBy(addr);
    }

    @Override
    public TrieNode<E> elementsContainedBy(E addr) {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().elementsContainedBy(addr);
    }

    @Override
    public TrieNode<E> elementsContaining(E addr) {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().elementsContaining(addr);
    }

    @Override
    public E longestPrefixMatch(E addr) {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().longestPrefixMatch(addr);
    }

    @Override
    public TrieNode<E> longestPrefixMatchNode(E addr) {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().longestPrefixMatchNode(addr);
    }

    @Override
    public boolean elementContains(E addr) {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().elementContains(addr);
    }

    AddressTrie<E> elementsContainedByToSubTrie(E addr) {
        Address lower = ((Address)addr).getLower().withoutPrefixLength();
        Address upper = ((Address)addr).getUpper().withoutPrefixLength();
        AddressBounds<Address> newBounds = this.bounds == null ? AddressBounds.createNewBounds(lower, true, upper, true, AddressTrie.comparator()) : this.bounds.intersect(lower, true, upper, true);
        if (newBounds == this.bounds) {
            return this;
        }
        return this.createSubTrie(newBounds);
    }

    AddressTrie<E> elementsContainingToTrie(E addr) {
        if (this.isEmpty()) {
            return this;
        }
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot == null) {
            return this.createNew(this.bounds);
        }
        TrieNode<E> node = ((TrieNode)subRoot).elementsContaining(addr);
        if (node == null) {
            return this.createNew(this.bounds);
        }
        if (this.size() == node.size()) {
            return this;
        }
        return this.createNewSameBoundsFromList(node);
    }

    boolean elementContainsBounds(E addr) {
        if (this.bounds == null) {
            return this.elementContains(addr);
        }
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot == null) {
            return false;
        }
        TrieNode<E> node = ((TrieNode)subRoot).elementsContaining(addr);
        if (node == null) {
            return false;
        }
        return !this.createNewSameBoundsFromList(node).isEmpty();
    }

    TrieNode<E> smallestElementContainingBounds(E addr) {
        if (this.bounds == null) {
            return this.longestPrefixMatchNode(addr);
        }
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot == null) {
            return null;
        }
        BinaryTreeNode node = ((TrieNode)subRoot).longestPrefixMatchNode(addr);
        if (node == null) {
            return null;
        }
        if (!this.bounds.isInBounds((Address)node.getKey())) {
            BinaryTreeNode next;
            BinaryTreeNode lastInBounds;
            node = ((TrieNode)subRoot).elementsContaining(addr);
            BinaryTreeNode binaryTreeNode = lastInBounds = this.bounds.isInBounds((Address)node.getKey()) ? node : null;
            do {
                if ((next = ((TrieNode)node).getLowerSubNode()) != null) {
                    node = next;
                    if (!this.bounds.isInBounds((Address)node.getKey())) continue;
                    lastInBounds = node;
                    continue;
                }
                next = ((TrieNode)node).getUpperSubNode();
                if (next == null || !this.bounds.isInBounds((Address)(node = next).getKey())) continue;
                lastInBounds = node;
            } while (next != null);
            node = lastInBounds;
        }
        return node;
    }

    E longestPrefixMatchBounds(E addr) {
        TrieNode<E> node = this.smallestElementContainingBounds(addr);
        return (E)(node == null ? null : (Address)node.getKey());
    }

    protected abstract AddressTrie<E> createNew(AddressBounds<E> var1);

    protected abstract AddressTrie<E> createSubTrie(AddressBounds<E> var1);

    private AddressTrie<E> createNewSameBoundsFromList(TrieNode<E> node) {
        BinaryTreeNode.ChangeTracker tracker;
        AddressTrie<E> newTrie = this.createNew(this.bounds);
        TrieNode<E> root = newTrie.absoluteRoot();
        if (((Address)node.getKey()).equals(root.getKey())) {
            newTrie.root = node;
        } else {
            root.init(node);
        }
        node.changeTracker = tracker = root.changeTracker;
        BinaryTreeNode next = node;
        while (true) {
            BinaryTreeNode lower;
            if ((lower = next.getLowerSubNode()) == null) {
                if ((next = next.getUpperSubNode()) == null) {
                    break;
                }
            } else {
                next = lower;
            }
            next.changeTracker = tracker;
        }
        newTrie.root.size = -1;
        newTrie.root.size();
        return newTrie;
    }

    @Override
    public TrieNode<E> getNode(E addr) {
        BinaryTreeNode subRoot;
        if (this.bounds != null) {
            if (!this.bounds.isInBounds(addr = AddressTrie.checkBlockOrAddress(addr, true))) {
                return null;
            }
            subRoot = this.getRoot();
            if (subRoot == null) {
                return null;
            }
        } else {
            subRoot = this.absoluteRoot();
        }
        return subRoot.getNode(addr);
    }

    @Override
    public Iterator<? extends TrieNode<E>> allNodeIterator(boolean forward) {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().allNodeIterator(forward);
    }

    public Iterator<? extends TrieNode<E>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
        Iterator<TrieNode<E>> iterator2 = this.bounds == null ? this.absoluteRoot().blockSizeNodeIterator(lowerSubNodeFirst) : new BinaryTreeNode.BlockSizeNodeIterator<E>(this.size(), this.bounds, true, this.getRoot(), !lowerSubNodeFirst, this.absoluteRoot().changeTracker);
        return iterator2;
    }

    public Iterator<? extends TrieNode<E>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
        Iterator<TrieNode<E>> iterator2 = this.bounds == null ? this.absoluteRoot().blockSizeAllNodeIterator(lowerSubNodeFirst) : new BinaryTreeNode.BlockSizeNodeIterator<E>(0, this.bounds, false, this.getRoot(), !lowerSubNodeFirst, this.absoluteRoot().changeTracker);
        return iterator2;
    }

    public <C> BinaryTreeNode.CachingIterator<? extends TrieNode<E>, E, C> blockSizeCachingAllNodeIterator() {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().blockSizeCachingAllNodeIterator();
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<? extends TrieNode<E>, E, C> containingFirstIterator(boolean forwardSubNodeOrder) {
        BinaryTreeNode.SubNodeCachingIterator iterator2 = this.bounds == null ? this.absoluteRoot().containingFirstIterator(forwardSubNodeOrder) : (forwardSubNodeOrder ? new BinaryTreeNode.PreOrderNodeIterator(this.bounds, true, true, this.absoluteRoot(), null, this.absoluteRoot().changeTracker) : new BinaryTreeNode.PostOrderNodeIterator(this.bounds, false, true, this.absoluteRoot(), null, this.absoluteRoot().changeTracker));
        return iterator2;
    }

    @Override
    public <C> BinaryTreeNode.CachingIterator<? extends TrieNode<E>, E, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        BinaryTreeNode.SubNodeCachingIterator iterator2 = this.bounds == null ? this.absoluteRoot().containingFirstAllNodeIterator(forwardSubNodeOrder) : (forwardSubNodeOrder ? new BinaryTreeNode.PreOrderNodeIterator(this.bounds, true, false, this.absoluteRoot(), null, this.absoluteRoot().changeTracker) : new BinaryTreeNode.PostOrderNodeIterator(this.bounds, false, false, this.absoluteRoot(), null, this.absoluteRoot().changeTracker));
        return iterator2;
    }

    @Override
    public Iterator<? extends TrieNode<E>> containedFirstIterator(boolean forwardSubNodeOrder) {
        Iterator<BinaryTreeNode> iterator2 = this.bounds == null ? this.absoluteRoot().containedFirstIterator(forwardSubNodeOrder) : this.containedFirstBoundedIterator(forwardSubNodeOrder, true);
        return iterator2;
    }

    private Iterator<? extends BinaryTreeNode<E>> containedFirstBoundedIterator(boolean forwardSubNodeOrder, boolean addedNodesOnly) {
        BinaryTreeNode.SubNodeCachingIterator iterator2;
        if (forwardSubNodeOrder) {
            BinaryTreeNode startNode = this.absoluteRoot().firstPostOrderNode();
            iterator2 = new BinaryTreeNode.PostOrderNodeIterator(this.bounds, true, addedNodesOnly, startNode, null, this.absoluteRoot().changeTracker);
        } else {
            BinaryTreeNode startNode = this.absoluteRoot().lastPreOrderNode();
            iterator2 = new BinaryTreeNode.PreOrderNodeIterator(this.bounds, false, addedNodesOnly, startNode, null, this.absoluteRoot().changeTracker);
        }
        return iterator2;
    }

    @Override
    public Iterator<? extends TrieNode<E>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
        Iterator<BinaryTreeNode> iterator2 = this.bounds == null ? this.absoluteRoot().containedFirstAllNodeIterator(forwardSubNodeOrder) : this.containedFirstBoundedIterator(forwardSubNodeOrder, false);
        return iterator2;
    }

    @Override
    public Spliterator<E> spliterator() {
        return new BinaryTreeNode.KeySpliterator<E>(this.nodeSpliterator(true, true), AddressTrie.comparator());
    }

    @Override
    public Spliterator<E> descendingSpliterator() {
        return new BinaryTreeNode.KeySpliterator<E>(this.nodeSpliterator(false, true), AddressTrie.reverseComparator());
    }

    @Override
    public Spliterator<? extends TrieNode<E>> nodeSpliterator(boolean forward) {
        return this.nodeSpliterator(forward, true);
    }

    @Override
    public Spliterator<? extends TrieNode<E>> allNodeSpliterator(boolean forward) {
        if (this.bounds != null) {
            throw new Error();
        }
        return this.absoluteRoot().nodeSpliterator(forward, false);
    }

    Spliterator<? extends TrieNode<E>> nodeSpliterator(boolean forward, boolean addedNodesOnly) {
        Spliterator<TrieNode<E>> spliterator;
        if (this.bounds == null) {
            spliterator = this.absoluteRoot().nodeSpliterator(forward, addedNodesOnly);
        } else {
            Comparator<BinaryTreeNode<E>> comp = forward ? AddressTrie.nodeComparator() : AddressTrie.reverseNodeComparator();
            BinaryTreeNode.NodeSpliterator<E> split = new BinaryTreeNode.NodeSpliterator<E>(forward, comp, this.getRoot(), forward ? this.firstAddedNode() : this.lastAddedNode(), forward ? this.getIteratingUpperBoundary() : this.getIteratingLowerBoundary(), (long)this.size(), this.absoluteRoot().changeTracker, addedNodesOnly);
            spliterator = split;
        }
        return spliterator;
    }

    @Override
    public Iterator<? extends TrieNode<E>> nodeIterator(boolean forward) {
        Iterator<TrieNode<E>> iterator2 = this.bounds == null ? this.absoluteRoot().nodeIterator(forward) : new BinaryTreeNode.NodeIterator<E>(forward, true, forward ? this.firstAddedNode() : this.lastAddedNode(), forward ? this.getIteratingUpperBoundary() : this.getIteratingLowerBoundary(), this.absoluteRoot().changeTracker);
        return iterator2;
    }

    @Override
    public TrieNode<E> firstNode() {
        return this.absoluteRoot().firstNode();
    }

    @Override
    public TrieNode<E> firstAddedNode() {
        if (this.bounds == null) {
            return this.absoluteRoot().firstAddedNode();
        }
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot != null) {
            TrieNode<Address> node = this.bounds.isLowerBounded() ? (this.bounds.lowerInclusive ? ((TrieNode)subRoot).ceilingNodeNoCheck((Address)this.bounds.lowerBound) : ((TrieNode)subRoot).higherNodeNoCheck((Address)this.bounds.lowerBound)) : ((TrieNode)subRoot).firstAddedNode();
            return node == null || this.bounds.isAboveUpperBound((Address)node.getKey()) ? null : node;
        }
        return null;
    }

    private TrieNode<E> getIteratingUpperBoundary() {
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot == null) {
            return null;
        }
        if (this.bounds.isUpperBounded()) {
            return this.bounds.upperInclusive ? ((TrieNode)subRoot).higherNodeNoCheck((Address)this.bounds.upperBound) : ((TrieNode)subRoot).ceilingNodeNoCheck((Address)this.bounds.upperBound);
        }
        return ((TrieNode)subRoot).getParent();
    }

    @Override
    public TrieNode<E> lastNode() {
        return this.absoluteRoot().lastNode();
    }

    @Override
    public TrieNode<E> lastAddedNode() {
        if (this.bounds == null) {
            return this.absoluteRoot().lastAddedNode();
        }
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot != null) {
            TrieNode<Address> node = this.bounds.isUpperBounded() ? (this.bounds.upperInclusive ? ((TrieNode)subRoot).floorNodeNoCheck((Address)this.bounds.upperBound) : ((TrieNode)subRoot).lowerNodeNoCheck((Address)this.bounds.upperBound)) : ((TrieNode)subRoot).lastAddedNode();
            return node == null || this.bounds.isBelowLowerBound((Address)node.getKey()) ? null : node;
        }
        return null;
    }

    private TrieNode<E> getIteratingLowerBoundary() {
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot == null) {
            return null;
        }
        if (this.bounds.isLowerBounded()) {
            return this.bounds.lowerInclusive ? ((TrieNode)subRoot).lowerNodeNoCheck((Address)this.bounds.lowerBound) : ((TrieNode)subRoot).floorNodeNoCheck((Address)this.bounds.lowerBound);
        }
        return ((TrieNode)subRoot).getParent();
    }

    public Comparator<E> getComparator() {
        return AddressTrie.comparator();
    }

    static <E extends Address> Comparator<E> comparator() {
        return AddressTrie.comparator.comparator;
    }

    static <E extends Address> Comparator<BinaryTreeNode<E>> nodeComparator() {
        return comparator;
    }

    static <E extends Address> Comparator<E> reverseComparator() {
        return AddressTrie.reverseComparator.comparator;
    }

    static <E extends Address> Comparator<BinaryTreeNode<E>> reverseNodeComparator() {
        return reverseComparator;
    }

    public AddressTrieSet<E> asSet() {
        AddressTrieSet<E> set = this.set;
        if (set == null) {
            set = new AddressTrieSet(this);
        }
        return set;
    }

    protected TrieNode<E> absoluteRoot() {
        return (TrieNode)this.root;
    }

    @Override
    public TrieNode<E> getRoot() {
        if (this.bounds == null) {
            return this.absoluteRoot();
        }
        if (this.subRootChange != null && !this.absoluteRoot().changeTracker.isChangedSince(this.subRootChange)) {
            return this.subRoot;
        }
        BinaryTreeNode current = this.absoluteRoot();
        do {
            Address currentKey = (Address)current.getKey();
            if (this.bounds.isLowerBounded() && this.bounds.isBelowLowerBound(currentKey)) {
                current = ((TrieNode)current).getUpperSubNode();
                continue;
            }
            if (!this.bounds.isUpperBounded() || !this.bounds.isAboveUpperBound(currentKey)) break;
            current = ((TrieNode)current).getLowerSubNode();
        } while (current != null);
        this.subRootChange = this.absoluteRoot().changeTracker.getCurrent();
        this.subRoot = current;
        return current;
    }

    @Override
    public TrieNode<E> lowerAddedNode(E addr) {
        if (this.bounds == null) {
            return this.absoluteRoot().lowerAddedNode(addr);
        }
        addr = AddressTrie.checkBlockOrAddress(addr, true);
        return this.lowerNodeBounded(addr);
    }

    private TrieNode<E> lowerNodeBounded(E addr) {
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot != null) {
            TrieNode<E> node = this.bounds.isAboveUpperBound(addr) ? this.lastAddedNode() : ((TrieNode)subRoot).lowerNodeNoCheck(addr);
            return node == null || this.bounds.isBelowLowerBound((Address)node.getKey()) ? null : node;
        }
        return null;
    }

    @Override
    public TrieNode<E> floorAddedNode(E addr) {
        if (this.bounds == null) {
            return this.absoluteRoot().floorAddedNode(addr);
        }
        addr = AddressTrie.checkBlockOrAddress(addr, true);
        return this.floorNodeBounded(addr);
    }

    private TrieNode<E> floorNodeBounded(E addr) {
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot != null) {
            TrieNode<E> node = this.bounds.isAboveUpperBound(addr) ? this.lastAddedNode() : ((TrieNode)subRoot).floorNodeNoCheck(addr);
            return node == null || this.bounds.isBelowLowerBound((Address)node.getKey()) ? null : node;
        }
        return null;
    }

    @Override
    public TrieNode<E> higherAddedNode(E addr) {
        if (this.bounds == null) {
            return this.absoluteRoot().higherAddedNode(addr);
        }
        addr = AddressTrie.checkBlockOrAddress(addr, true);
        return this.higherNodeBounded(addr);
    }

    private TrieNode<E> higherNodeBounded(E addr) {
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot != null) {
            TrieNode<E> node = this.bounds.isBelowLowerBound(addr) ? this.firstAddedNode() : ((TrieNode)subRoot).higherNodeNoCheck(addr);
            return node == null || this.bounds.isAboveUpperBound((Address)node.getKey()) ? null : node;
        }
        return null;
    }

    @Override
    public TrieNode<E> ceilingAddedNode(E addr) {
        if (this.bounds == null) {
            return this.absoluteRoot().ceilingAddedNode(addr);
        }
        addr = AddressTrie.checkBlockOrAddress(addr, true);
        return this.ceilingNodeBounded(addr);
    }

    private TrieNode<E> ceilingNodeBounded(E addr) {
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot != null) {
            TrieNode<E> node = this.bounds.isBelowLowerBound(addr) ? this.firstAddedNode() : ((TrieNode)subRoot).ceilingNodeNoCheck(addr);
            return node == null || this.bounds.isAboveUpperBound((Address)node.getKey()) ? null : node;
        }
        return null;
    }

    @Override
    public void clear() {
        if (this.bounds == null) {
            super.clear();
        } else {
            Iterator<TrieNode<E>> iterator2 = this.nodeIterator(true);
            while (iterator2.hasNext()) {
                BinaryTreeNode node = iterator2.next();
                if (!this.bounds.isInBounds((Address)node.getKey())) continue;
                iterator2.remove();
            }
        }
    }

    @Override
    public AddressTrie<E> clone() {
        AddressTrie result = (AddressTrie)super.clone();
        result.set = null;
        if (this.bounds == null) {
            result.root = ((TrieNode)this.getRoot()).cloneTree();
        } else {
            TrieNode<E> root = this.absoluteRoot();
            if (this.bounds.isInBounds((Address)root.getKey())) {
                result.root = root.cloneTree((BinaryTreeNode.Bounds)this.bounds);
            } else {
                BinaryTreeNode clonedRoot;
                result.root = clonedRoot = root.cloneTreeNode(new BinaryTreeNode.ChangeTracker());
                clonedRoot.setAdded(false);
                clonedRoot.setLower(null);
                clonedRoot.setUpper(null);
                BinaryTreeNode subRoot = this.getRoot();
                if (subRoot != null) {
                    BinaryTreeNode subCloned = ((TrieNode)subRoot).cloneTree((BinaryTreeNode.Bounds)this.bounds);
                    if (subCloned != null) {
                        result.absoluteRoot().init((TrieNode<E>)subCloned);
                    } else {
                        clonedRoot.size = clonedRoot.isAdded() ? 1 : 0;
                    }
                } else {
                    clonedRoot.size = clonedRoot.isAdded() ? 1 : 0;
                }
            }
            result.bounds = null;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AddressTrie && super.equals(o);
    }

    @Override
    public String toString() {
        if (this.bounds == null) {
            return super.toString();
        }
        return this.toString(true);
    }

    String noBoundsString() {
        return this.absoluteRoot().toTreeString(true, true);
    }

    @Override
    public String toString(boolean withNonAddedKeys) {
        if (this.bounds == null) {
            return super.toString(withNonAddedKeys);
        }
        StringBuilder builder = new StringBuilder("\n");
        this.printTree(builder, new BinaryTreeNode.Indents(), withNonAddedKeys);
        return builder.toString();
    }

    void printTree(StringBuilder builder, BinaryTreeNode.Indents indents, boolean withNonAddedKeys) {
        BinaryTreeNode subRoot = this.getRoot();
        if (subRoot == null) {
            return;
        }
        subRoot.printTree(builder, indents, withNonAddedKeys, true, this.containingFirstAllNodeIterator(true));
    }

    public static String toString(boolean withNonAddedKeys, AddressTrie<?> ... tries) {
        boolean isEmpty;
        StringBuilder builder = new StringBuilder("\n\u25cb");
        String topLabel = ' ' + Address.SEGMENT_WILDCARD_STR;
        boolean bl = isEmpty = tries == null;
        if (!isEmpty) {
            int lastTreeIndex;
            AddressTrie<?> lastTree = null;
            for (lastTreeIndex = tries.length - 1; lastTreeIndex >= 0; --lastTreeIndex) {
                if (tries[lastTreeIndex] == null) continue;
                lastTree = tries[lastTreeIndex];
                break;
            }
            boolean bl2 = isEmpty = lastTree == null;
            if (!isEmpty) {
                AddressTrie<?> tree;
                int i;
                int totalSize = lastTree.size();
                for (i = 0; i < lastTreeIndex; ++i) {
                    tree = tries[i];
                    if (tree == null) continue;
                    totalSize += ((AbstractTree)tree).size();
                }
                if (withNonAddedKeys) {
                    builder.append(topLabel).append(" (").append(totalSize).append(')');
                }
                builder.append('\n');
                for (i = 0; i < lastTreeIndex; ++i) {
                    tree = tries[i];
                    if (tree == null) continue;
                    tree.printTree(builder, new BinaryTreeNode.Indents("\u251c\u2500", "\u2502 "), withNonAddedKeys);
                }
                lastTree.printTree(builder, new BinaryTreeNode.Indents("\u2514\u2500", "  "), withNonAddedKeys);
            }
        }
        if (isEmpty) {
            if (withNonAddedKeys) {
                builder.append(topLabel).append(" (0)");
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    protected static class SubNodesMappingBasic<E extends Address>
    extends SubNodesMapping<E, SubNodesMappingBasic<E>> {
        protected SubNodesMappingBasic() {
        }

        @Override
        Object getUnderlyingValue() {
            return null;
        }
    }

    static abstract class SubNodesMapping<E extends Address, N extends SubNodesMapping<E, N>> {
        ArrayList<AssociativeAddressTrie.AssociativeTrieNode<E, N>> subNodes;

        SubNodesMapping() {
        }

        abstract Object getUnderlyingValue();
    }

    public static abstract class TrieNode<E extends Address>
    extends BinaryTreeNode<E>
    implements AddressTrieOps<E> {
        private static final long serialVersionUID = 1L;

        protected TrieNode(E item) {
            super(item);
        }

        @Override
        public TrieNode<E> getParent() {
            return (TrieNode)super.getParent();
        }

        @Override
        public TrieNode<E> getUpperSubNode() {
            return (TrieNode)super.getUpperSubNode();
        }

        @Override
        public TrieNode<E> getLowerSubNode() {
            return (TrieNode)super.getLowerSubNode();
        }

        private TrieNode<E> findNodeNear(E addr, boolean below, boolean exclusive) {
            addr = AbstractTree.checkBlockOrAddress(addr, true);
            return this.findNodeNearNoCheck(addr, below, exclusive);
        }

        private TrieNode<E> findNodeNearNoCheck(E addr, boolean below, boolean exclusive) {
            OpResult<E> result = new OpResult<E>(addr, below, exclusive);
            this.matchBits(result);
            BinaryTreeNode backtrack = result.backtrackNode;
            if (backtrack != null) {
                BinaryTreeNode parent = ((TrieNode)backtrack).getParent();
                while (parent != null && backtrack == (below ? ((TrieNode)parent).getLowerSubNode() : ((TrieNode)parent).getUpperSubNode())) {
                    backtrack = parent;
                    parent = ((TrieNode)backtrack).getParent();
                }
                if (parent != null) {
                    result.nearestNode = parent.isAdded() ? parent : (below ? ((TrieNode)parent).previousAddedNode() : ((TrieNode)parent).nextAddedNode());
                }
            }
            return result.nearestNode;
        }

        @Override
        public TrieNode<E> previousAddedNode() {
            return (TrieNode)super.previousAddedNode();
        }

        @Override
        public TrieNode<E> nextAddedNode() {
            return (TrieNode)super.nextAddedNode();
        }

        @Override
        public TrieNode<E> nextNode() {
            return (TrieNode)super.nextNode();
        }

        @Override
        public TrieNode<E> previousNode() {
            return (TrieNode)super.previousNode();
        }

        @Override
        public TrieNode<E> firstNode() {
            return (TrieNode)super.firstNode();
        }

        @Override
        public TrieNode<E> firstAddedNode() {
            return (TrieNode)super.firstAddedNode();
        }

        @Override
        public TrieNode<E> lastNode() {
            return (TrieNode)super.lastNode();
        }

        @Override
        public TrieNode<E> lastAddedNode() {
            return (TrieNode)super.lastAddedNode();
        }

        @Override
        public TrieNode<E> lowerAddedNode(E addr) {
            return this.findNodeNear(addr, true, true);
        }

        TrieNode<E> lowerNodeNoCheck(E addr) {
            return this.findNodeNearNoCheck(addr, true, true);
        }

        @Override
        public TrieNode<E> floorAddedNode(E addr) {
            return this.findNodeNear(addr, true, false);
        }

        TrieNode<E> floorNodeNoCheck(E addr) {
            return this.findNodeNearNoCheck(addr, true, false);
        }

        @Override
        public TrieNode<E> higherAddedNode(E addr) {
            return this.findNodeNear(addr, false, true);
        }

        TrieNode<E> higherNodeNoCheck(E addr) {
            return this.findNodeNearNoCheck(addr, false, true);
        }

        @Override
        public TrieNode<E> ceilingAddedNode(E addr) {
            return this.findNodeNear(addr, false, false);
        }

        TrieNode<E> ceilingNodeNoCheck(E addr) {
            return this.findNodeNearNoCheck(addr, false, false);
        }

        @Override
        public Iterator<? extends TrieNode<E>> nodeIterator(boolean forward) {
            return super.nodeIterator(forward);
        }

        @Override
        public Iterator<? extends TrieNode<E>> allNodeIterator(boolean forward) {
            return super.allNodeIterator(forward);
        }

        public Iterator<? extends TrieNode<E>> blockSizeNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst, true);
        }

        public Iterator<? extends TrieNode<E>> blockSizeAllNodeIterator(boolean lowerSubNodeFirst) {
            return super.blockSizeNodeIterator(lowerSubNodeFirst, false);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<? extends TrieNode<E>, E, C> blockSizeCachingAllNodeIterator() {
            return super.blockSizeCachingAllNodeIterator();
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<? extends TrieNode<E>, E, C> containingFirstIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public <C> BinaryTreeNode.CachingIterator<? extends TrieNode<E>, E, C> containingFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containingFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<? extends TrieNode<E>> containedFirstIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstIterator(forwardSubNodeOrder);
        }

        @Override
        public Iterator<? extends TrieNode<E>> containedFirstAllNodeIterator(boolean forwardSubNodeOrder) {
            return super.containedFirstAllNodeIterator(forwardSubNodeOrder);
        }

        @Override
        public Spliterator<? extends TrieNode<E>> nodeSpliterator(boolean forward) {
            return this.nodeSpliterator(forward, true);
        }

        @Override
        public Spliterator<? extends TrieNode<E>> allNodeSpliterator(boolean forward) {
            return this.nodeSpliterator(forward, false);
        }

        Spliterator<? extends TrieNode<E>> nodeSpliterator(boolean forward, boolean addedNodesOnly) {
            Comparator comp = forward ? AddressTrie.nodeComparator() : AddressTrie.reverseNodeComparator();
            BinaryTreeNode.NodeSpliterator spliterator = new BinaryTreeNode.NodeSpliterator(forward, comp, this, forward ? this.firstNode() : this.lastNode(), this.getParent(), (long)this.size(), this.changeTracker, addedNodesOnly);
            return spliterator;
        }

        @Override
        public Spliterator<E> spliterator() {
            return new BinaryTreeNode.KeySpliterator<E>(this.nodeSpliterator(true, true), AddressTrie.comparator());
        }

        @Override
        public Spliterator<E> descendingSpliterator() {
            return new BinaryTreeNode.KeySpliterator<E>(this.nodeSpliterator(false, true), AddressTrie.reverseComparator());
        }

        @Override
        public boolean contains(E addr) {
            OpResult<E> result = this.doLookup(addr);
            return result.exists;
        }

        @Override
        public boolean remove(E addr) {
            addr = AbstractTree.checkBlockOrAddress(addr, true);
            OpResult<E> result = new OpResult<E>(addr, Operation.INSERTED_DELETE);
            this.matchBits(result);
            return result.exists;
        }

        @Override
        public TrieNode<E> getNode(E addr) {
            OpResult<E> result = this.doLookup(addr);
            TrieNode ret = result.existingNode;
            return ret;
        }

        @Override
        public TrieNode<E> removeElementsContainedBy(E addr) {
            addr = AbstractTree.checkBlockOrAddress(addr, true);
            OpResult<E> result = new OpResult<E>(addr, Operation.SUBNET_DELETE);
            this.matchBits(result);
            return result.deleted;
        }

        @Override
        public TrieNode<E> elementsContainedBy(E addr) {
            OpResult<E> result = this.doLookup(addr);
            return result.containedBy;
        }

        @Override
        public TrieNode<E> elementsContaining(E addr) {
            addr = AbstractTree.checkBlockOrAddress(addr, true);
            OpResult<E> result = new OpResult<E>(addr, Operation.CONTAINING);
            this.matchBits(result);
            return result.getContaining();
        }

        @Override
        public E longestPrefixMatch(E addr) {
            TrieNode<E> node = this.longestPrefixMatchNode(addr);
            return (E)(node == null ? null : (Address)node.getKey());
        }

        @Override
        public TrieNode<E> longestPrefixMatchNode(E addr) {
            return this.doLookup(addr).smallestContaining;
        }

        @Override
        public boolean elementContains(E addr) {
            return this.longestPrefixMatch(addr) != null;
        }

        private OpResult<E> doLookup(E addr) {
            addr = AbstractTree.checkBlockOrAddress(addr, true);
            OpResult<E> result = new OpResult<E>(addr, Operation.LOOKUP);
            this.matchBits(result);
            return result;
        }

        private void removeSubnet(OpResult<E> result) {
            result.deleted = this;
            this.clear();
        }

        protected void remove(OpResult<E> result) {
            result.deleted = this;
            this.remove();
        }

        void matchBits(OpResult<E> result) {
            this.matchBits(0, result);
        }

        void matchBits(int bitIndex, OpResult<E> result) {
            TrieNode.matchBits(this, bitIndex, result);
        }

        static <E extends Address> void matchBits(TrieNode<E> node, int bitIndex, OpResult<E> result) {
            int bits;
            while ((bits = node.matchNodeBits(bitIndex, result)) >= 0 && (node = super.matchSubNode(bits, result)) != null) {
                bitIndex = bits + 1;
            }
        }

        int matchNodeBits(int bitIndex, OpResult<E> result) {
            int segmentCount;
            Object newAddr = result.addr;
            Operation op = result.op;
            AddressSegmentSeries existingAddr = (AddressSegmentSeries)this.getKey();
            int bitsPerSegment = existingAddr.getBitsPerSegment();
            int segmentIndex = bitIndex / bitsPerSegment;
            if (segmentIndex >= (segmentCount = existingAddr.getSegmentCount())) {
                Integer newPref;
                Integer existingPref = existingAddr.getPrefixLength();
                if (Objects.equals(existingPref, newPref = ((Address)newAddr).getPrefixLength())) {
                    result.containedBy = this;
                    this.handleMatch(result);
                } else if (existingPref == null) {
                    result.containedBy = this;
                    this.handleContained(result, newPref);
                } else {
                    this.handleContains(result);
                    return existingPref;
                }
                return -1;
            }
            if (((Address)newAddr).getSegmentCount() != segmentCount) {
                throw new IllegalArgumentException(TrieNode.getMessage("ipaddress.error.mismatched.bit.size"));
            }
            int bitsMatchedSoFar = segmentIndex * bitsPerSegment;
            int extraBits = 32 - bitsPerSegment;
            while (true) {
                int matchingBits;
                AddressSegment existingSegment = existingAddr.getSegment(segmentIndex);
                AddressSegment newSegment = newAddr.getSegment(segmentIndex);
                Integer segmentPref = AddressTrie.getSegmentPrefLen(existingAddr, bitsMatchedSoFar, existingSegment);
                Integer newPref = AddressTrie.getSegmentPrefLen(newAddr, bitsMatchedSoFar, newSegment);
                if (segmentPref != null) {
                    int newPrefixLen;
                    int segmentPrefLen = segmentPref;
                    if (newPref != null && (newPrefixLen = newPref.intValue()) <= segmentPrefLen) {
                        int matchingBits2 = AddressTrie.getMatchingBits(existingSegment, newSegment, newPrefixLen, extraBits);
                        if (matchingBits2 >= newPrefixLen) {
                            result.containedBy = this;
                            if (newPrefixLen == segmentPrefLen) {
                                if (this.isAdded()) {
                                    this.handleMatch(result);
                                    break;
                                }
                                if (op == Operation.LOOKUP) {
                                    result.existingNode = this;
                                    break;
                                }
                                if (op == Operation.INSERT) {
                                    this.existingAdded(result);
                                    break;
                                }
                                if (op == Operation.SUBNET_DELETE) {
                                    this.removeSubnet(result);
                                    break;
                                }
                                if (op == Operation.NEAR) {
                                    this.findNearestFromMatch(result);
                                    break;
                                }
                                if (op != Operation.REMAP) break;
                                this.remapNonAdded(result);
                                break;
                            }
                            this.handleContained(result, bitsMatchedSoFar + newPrefixLen);
                            break;
                        }
                        this.handleSplitNode(result, bitsMatchedSoFar + matchingBits2);
                        break;
                    }
                    int matchingBits3 = AddressTrie.getMatchingBits(existingSegment, newSegment, segmentPrefLen, extraBits);
                    if (matchingBits3 >= segmentPrefLen) {
                        if (this.isAdded()) {
                            this.handleContains(result);
                        }
                        return segmentPrefLen + bitsMatchedSoFar;
                    }
                    this.handleSplitNode(result, bitsMatchedSoFar + matchingBits3);
                    break;
                }
                if (newPref != null) {
                    int newPrefixLen = newPref;
                    matchingBits = AddressTrie.getMatchingBits(existingSegment, newSegment, newPrefixLen, extraBits);
                    if (matchingBits >= newPrefixLen) {
                        result.containedBy = this;
                        this.handleContained(result, bitsMatchedSoFar + newPrefixLen);
                        break;
                    }
                    this.handleSplitNode(result, bitsMatchedSoFar + matchingBits);
                    break;
                }
                matchingBits = AddressTrie.getMatchingBits(existingSegment, newSegment, bitsPerSegment, extraBits);
                if (matchingBits < bitsPerSegment) {
                    this.handleSplitNode(result, bitsMatchedSoFar + matchingBits);
                    break;
                }
                if (++segmentIndex == segmentCount) {
                    result.containedBy = this;
                    this.handleMatch(result);
                    break;
                }
                bitsMatchedSoFar += bitsPerSegment;
            }
            return -1;
        }

        private void handleContained(OpResult<E> result, int newPref) {
            Operation op = result.op;
            if (op == Operation.INSERT) {
                this.replace(result, newPref);
            } else if (op == Operation.SUBNET_DELETE) {
                this.removeSubnet(result);
            } else if (op == Operation.NEAR) {
                this.findNearest(result, newPref);
            } else if (op == Operation.REMAP) {
                this.remapNonExistingReplace(result, newPref);
            }
        }

        private boolean handleContains(OpResult<E> result) {
            result.smallestContaining = this;
            if (result.op == Operation.CONTAINING) {
                result.addContaining(this);
                return true;
            }
            return false;
        }

        private void handleSplitNode(OpResult<E> result, int totalMatchingBits) {
            Object newAddr = result.addr;
            Operation op = result.op;
            if (op == Operation.INSERT) {
                this.split(result, totalMatchingBits, this.createNew(newAddr));
            } else if (op == Operation.NEAR) {
                this.findNearest(result, totalMatchingBits);
            } else if (op == Operation.REMAP) {
                this.remapNonExistingSplit(result, totalMatchingBits);
            }
        }

        private void handleMatch(OpResult<E> result) {
            result.exists = true;
            if (!this.handleContains(result)) {
                Operation op = result.op;
                if (op == Operation.LOOKUP) {
                    this.matched(result);
                } else if (op == Operation.INSERT) {
                    this.matchedInserted(result);
                } else if (op == Operation.INSERTED_DELETE) {
                    this.remove(result);
                } else if (op == Operation.SUBNET_DELETE) {
                    this.removeSubnet(result);
                } else if (op == Operation.NEAR) {
                    if (result.nearExclusive) {
                        this.findNearestFromMatch(result);
                    } else {
                        this.matched(result);
                    }
                } else if (op == Operation.REMAP) {
                    this.remapMatch(result);
                }
            }
        }

        private void remapNonExistingReplace(OpResult<E> result, int totalMatchingBits) {
            if (this.remap(result, false)) {
                this.replace(result, totalMatchingBits);
            }
        }

        private void remapNonExistingSplit(OpResult<E> result, int totalMatchingBits) {
            if (this.remap(result, false)) {
                this.split(result, totalMatchingBits, this.createNew(result.addr));
            }
        }

        private TrieNode<E> remapNonExisting(OpResult<E> result) {
            if (this.remap(result, false)) {
                return this.createNew(result.addr);
            }
            return null;
        }

        private void remapNonAdded(OpResult<E> result) {
            if (this.remap(result, false)) {
                this.existingAdded(result);
            }
        }

        private void remapMatch(OpResult<E> result) {
            result.existingNode = this;
            if (this.remap(result, true)) {
                this.matchedInserted(result);
            }
        }

        boolean remap(OpResult<E> result, boolean isMatch) {
            return false;
        }

        private void matched(OpResult<E> result) {
            result.existingNode = this;
            result.nearestNode = this;
        }

        void matchedInserted(OpResult<E> result) {
            result.existingNode = this;
            result.addedAlready = this;
        }

        private void existingAdded(OpResult<E> result) {
            result.existingNode = this;
            result.added = this;
            this.added(result);
        }

        private void inserted(OpResult<E> result) {
            result.inserted = this;
            this.added(result);
        }

        void added(OpResult<E> result) {
            this.setAdded(true);
            this.adjustCount(1);
            this.changeTracker.changed();
        }

        private void split(OpResult<E> result, int totalMatchingBits, TrieNode<E> newSubNode) {
            Address key = (Address)this.getKey();
            Address newBlock = key.isIPAddress() ? key.toIPAddress().toPrefixBlock(totalMatchingBits) : key.setPrefixLength(totalMatchingBits).toPrefixBlock();
            this.replace(newBlock, result, totalMatchingBits, newSubNode);
            super.inserted(result);
        }

        private void replace(OpResult<E> result, int totalMatchingBits) {
            result.containedBy = this;
            TrieNode newNode = this.replace(result.addr, result, totalMatchingBits, null);
            super.inserted(result);
        }

        private TrieNode<E> replace(E newAssignedAddr, OpResult<E> result, int totalMatchingBits, TrieNode<E> newSubNode) {
            TrieNode<E> newNode = this.createNew(newAssignedAddr);
            newNode.size = this.size;
            BinaryTreeNode parent = this.getParent();
            if (((TrieNode)parent).getUpperSubNode() == this) {
                parent.setUpper(newNode);
            } else if (((TrieNode)parent).getLowerSubNode() == this) {
                parent.setLower(newNode);
            }
            Address existingAddr = (Address)this.getKey();
            if (totalMatchingBits < existingAddr.getBitCount() && existingAddr.isOneBit(totalMatchingBits)) {
                if (newSubNode != null) {
                    newNode.setLower(newSubNode);
                }
                newNode.setUpper(this);
            } else {
                newNode.setLower(this);
                if (newSubNode != null) {
                    newNode.setUpper(newSubNode);
                }
            }
            return newNode;
        }

        private void findNearestFromMatch(OpResult<E> result) {
            if (result.nearestFloor) {
                BinaryTreeNode lower = this.getLowerSubNode();
                if (lower == null) {
                    result.backtrackNode = this;
                } else {
                    BinaryTreeNode last;
                    do {
                        last = lower;
                    } while ((lower = ((TrieNode)lower).getUpperSubNode()) != null);
                    result.nearestNode = last;
                }
            } else {
                BinaryTreeNode upper = this.getUpperSubNode();
                if (upper == null) {
                    result.backtrackNode = this;
                } else {
                    BinaryTreeNode last;
                    do {
                        last = upper;
                    } while ((upper = ((TrieNode)upper).getLowerSubNode()) != null);
                    result.nearestNode = last;
                }
            }
        }

        private void findNearest(OpResult<E> result, int differingBitIndex) {
            Address thisAddr = (Address)this.getKey();
            if (differingBitIndex < thisAddr.getBitCount() && thisAddr.isOneBit(differingBitIndex)) {
                if (result.nearestFloor) {
                    result.backtrackNode = this;
                } else {
                    TrieNode last;
                    BinaryTreeNode lower = this;
                    do {
                        last = lower;
                    } while ((lower = lower.getLowerSubNode()) != null);
                    result.nearestNode = last;
                }
            } else if (result.nearestFloor) {
                TrieNode last;
                BinaryTreeNode upper = this;
                do {
                    last = upper;
                } while ((upper = upper.getUpperSubNode()) != null);
                result.nearestNode = last;
            } else {
                result.backtrackNode = this;
            }
        }

        void init(TrieNode<E> node) {
            Address newAddr = (Address)node.getKey();
            if (newAddr.getBitCount() > 0 && newAddr.isOneBit(0)) {
                this.setUpper(node);
            } else {
                this.setLower(node);
            }
            this.size = (this.isAdded() ? 1 : 0) + node.size;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private TrieNode<E> matchSubNode(int bitIndex, OpResult<E> result) {
            Object newAddr = result.addr;
            if (!FREEZE_ROOT && this.isEmpty()) {
                if (result.op == Operation.REMAP) {
                    this.remapNonAdded(result);
                    return null;
                } else {
                    if (result.op != Operation.INSERT) return null;
                    this.setKey(newAddr);
                    this.existingAdded(result);
                }
                return null;
            } else if (bitIndex < ((Address)newAddr).getBitCount() && newAddr.isOneBit(bitIndex)) {
                TrieNode upper = this.getUpperSubNode();
                if (upper != null) return upper;
                Operation op = result.op;
                if (op == Operation.INSERT) {
                    upper = this.createNew(newAddr);
                    this.setUpper(upper);
                    super.inserted(result);
                    return null;
                } else if (op == Operation.NEAR) {
                    if (result.nearestFloor) {
                        if (this.isAdded()) {
                            result.nearestNode = this;
                            return null;
                        } else {
                            BinaryTreeNode lower = this.getLowerSubNode();
                            if (lower == null) return null;
                            BinaryTreeNode res = lower;
                            BinaryTreeNode next = ((TrieNode)res).getUpperSubNode();
                            while (next != null) {
                                res = next;
                                next = ((TrieNode)res).getUpperSubNode();
                            }
                            result.nearestNode = res;
                        }
                        return null;
                    } else {
                        result.backtrackNode = this;
                    }
                    return null;
                } else {
                    if (op != Operation.REMAP || (upper = this.remapNonExisting(result)) == null) return null;
                    this.setUpper(upper);
                    super.inserted(result);
                }
                return null;
            } else {
                TrieNode lower = this.getLowerSubNode();
                if (lower != null) return lower;
                Operation op = result.op;
                if (op == Operation.INSERT) {
                    lower = this.createNew(newAddr);
                    this.setLower(lower);
                    super.inserted(result);
                    return null;
                } else if (op == Operation.NEAR) {
                    if (result.nearestFloor) {
                        result.backtrackNode = this;
                        return null;
                    } else if (this.isAdded()) {
                        result.nearestNode = this;
                        return null;
                    } else {
                        BinaryTreeNode upper = this.getUpperSubNode();
                        if (upper == null) return null;
                        BinaryTreeNode res = upper;
                        BinaryTreeNode next = ((TrieNode)res).getLowerSubNode();
                        while (next != null) {
                            res = next;
                            next = ((TrieNode)res).getLowerSubNode();
                        }
                        result.nearestNode = res;
                    }
                    return null;
                } else {
                    if (op != Operation.REMAP || (lower = this.remapNonExisting(result)) == null) return null;
                    this.setLower(lower);
                    super.inserted(result);
                }
            }
            return null;
        }

        private TrieNode<E> createNew(E newAddr) {
            TrieNode<E> newNode = this.createNewImpl(newAddr);
            newNode.changeTracker = this.changeTracker;
            return newNode;
        }

        protected abstract TrieNode<E> createNewImpl(E var1);

        protected abstract AddressTrie<E> createNewTree();

        public AddressTrie<E> asNewTrie() {
            AddressTrie<E> newTrie = this.createNewTree();
            newTrie.addTrie(this);
            return newTrie;
        }

        @Override
        public TrieNode<E> cloneTree() {
            return (TrieNode)super.cloneTree();
        }

        @Override
        public TrieNode<E> clone() {
            return (TrieNode)super.clone();
        }

        @Override
        TrieNode<E> cloneTree(BinaryTreeNode.Bounds<E> bounds) {
            return (TrieNode)super.cloneTree(bounds);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof TrieNode && super.equals(o);
        }
    }

    public static class TrieComparator<E extends Address>
    implements Comparator<BinaryTreeNode<E>>,
    Serializable {
        private static final long serialVersionUID = 1L;
        Comparator<E> comparator;

        TrieComparator(Comparator<E> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(BinaryTreeNode<E> tree1, BinaryTreeNode<E> tree2) {
            Address o1 = (Address)tree1.getKey();
            Address o2 = (Address)tree2.getKey();
            return this.comparator.compare(o1, o2);
        }
    }

    public static class AddressComparator<E extends Address>
    implements Comparator<E>,
    Serializable {
        private static final long serialVersionUID = 1L;

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int compare(E o1, E o2) {
            if (o1 == o2) {
                return 0;
            }
            int segmentCount = ((Address)o1).getSegmentCount();
            int bitsPerSegment = o1.getBitsPerSegment();
            int bitsMatchedSoFar = 0;
            int extraBits = 32 - bitsPerSegment;
            int i = 0;
            while (true) {
                int matchingBits;
                int segmentPref2;
                AddressSegment segment1 = o1.getSegment(i);
                AddressSegment segment2 = o2.getSegment(i);
                Integer pref1 = AddressTrie.getSegmentPrefLen(o1, bitsMatchedSoFar, segment1);
                Integer pref2 = AddressTrie.getSegmentPrefLen(o2, bitsMatchedSoFar, segment2);
                if (pref1 != null) {
                    int matchingBits2;
                    int segmentPref1 = pref1;
                    if (pref2 != null && (segmentPref2 = pref2.intValue()) <= segmentPref1) {
                        matchingBits2 = AddressTrie.getMatchingBits(segment1, segment2, segmentPref2, extraBits);
                        if (matchingBits2 < segmentPref2) return segment1.getSegmentValue() - segment2.getSegmentValue();
                        if (segmentPref2 == segmentPref1) {
                            return 0;
                        }
                        if (!segment1.isOneBit(segmentPref2)) return -1;
                        return 1;
                    }
                    matchingBits2 = AddressTrie.getMatchingBits(segment1, segment2, segmentPref1, extraBits);
                    if (matchingBits2 < segmentPref1) return segment1.getSegmentValue() - segment2.getSegmentValue();
                    if (segmentPref1 < bitsPerSegment) {
                        if (!segment2.isOneBit(segmentPref1)) return 1;
                        return -1;
                    }
                    if (++i == segmentCount) {
                        return 1;
                    }
                } else if (pref2 != null) {
                    segmentPref2 = pref2;
                    matchingBits = AddressTrie.getMatchingBits(segment1, segment2, segmentPref2, extraBits);
                    if (matchingBits < pref2) return segment1.getSegmentValue() - segment2.getSegmentValue();
                    if (segmentPref2 < bitsPerSegment) {
                        if (!segment1.isOneBit(segmentPref2)) return -1;
                        return 1;
                    }
                    if (++i == segmentCount) {
                        return -1;
                    }
                } else {
                    matchingBits = AddressTrie.getMatchingBits(segment1, segment2, bitsPerSegment, extraBits);
                    if (matchingBits < bitsPerSegment) {
                        return segment1.getSegmentValue() - segment2.getSegmentValue();
                    }
                    if (++i == segmentCount) {
                        return 0;
                    }
                }
                bitsMatchedSoFar += bitsPerSegment;
            }
        }
    }

    protected static class OpResult<E extends Address> {
        E addr;
        final boolean nearestFloor;
        final boolean nearExclusive;
        final Operation op;
        boolean exists;
        TrieNode<E> existingNode;
        TrieNode<E> nearestNode;
        TrieNode<E> backtrackNode;
        TrieNode<E> containing;
        TrieNode<E> containingEnd;
        TrieNode<E> smallestContaining;
        TrieNode<E> containedBy;
        TrieNode<E> deleted;
        Object newValue;
        Object existingValue;
        TrieNode<E> inserted;
        TrieNode<E> added;
        TrieNode<E> addedAlready;
        Function<?, ?> remapper;

        OpResult(E addr, Operation op) {
            this(addr, op, false, false);
        }

        OpResult(E addr, boolean floor, boolean exclusive) {
            this(addr, Operation.NEAR, floor, exclusive);
        }

        private OpResult(E addr, Operation op, boolean floor, boolean exclusive) {
            this.addr = addr;
            this.op = op;
            this.nearestFloor = floor;
            this.nearExclusive = exclusive;
        }

        static <E extends Address> TrieNode<E> getNextAdded(TrieNode<E> node) {
            while (node != null && !node.isAdded()) {
                BinaryTreeNode next = node.getUpperSubNode();
                if (next == null) {
                    node = node.getLowerSubNode();
                    continue;
                }
                node = next;
            }
            return node;
        }

        TrieNode<E> getContaining() {
            TrieNode<E> containing = OpResult.getNextAdded(this.containing);
            this.containing = containing;
            if (containing != null) {
                TrieNode<E> nextAdded;
                TrieNode<E> current = containing;
                do {
                    BinaryTreeNode next;
                    if ((next = current.getUpperSubNode()) == null) {
                        next = current.getLowerSubNode();
                        if (next == (nextAdded = OpResult.getNextAdded(next))) continue;
                        current.setLower(nextAdded);
                        continue;
                    }
                    nextAdded = OpResult.getNextAdded(next);
                    if (next == nextAdded) continue;
                    current.setUpper(nextAdded);
                } while ((current = nextAdded) != null);
            }
            return containing;
        }

        void addContaining(TrieNode<E> containingSub) {
            BinaryTreeNode cloned = containingSub.clone();
            if (this.containing == null) {
                this.containing = cloned;
            } else {
                Comparator comp = AddressTrie.nodeComparator();
                if (comp.compare(this.containingEnd, cloned) > 0) {
                    this.containingEnd.setLower(cloned);
                } else {
                    this.containingEnd.setUpper(cloned);
                }
                this.containingEnd.adjustCount(1);
            }
            this.containingEnd = cloned;
        }
    }

    protected static enum Operation {
        INSERT,
        REMAP,
        LOOKUP,
        NEAR,
        CONTAINING,
        INSERTED_DELETE,
        SUBNET_DELETE;

    }

    protected static class AddressBounds<E extends Address>
    extends BinaryTreeNode.Bounds<E> {
        private static final long serialVersionUID = 1L;
        E oneAboveUpperBound;
        E oneBelowUpperBound;
        E oneAboveLowerBound;
        E oneBelowLowerBound;

        AddressBounds(E lowerBound, E upperBound, Comparator<? super E> comparator) {
            this((E)lowerBound, true, (E)upperBound, false, comparator);
        }

        AddressBounds(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive, Comparator<? super E> comparator) {
            super(lowerBound, lowerInclusive, upperBound, upperInclusive, comparator);
            if (lowerBound != null) {
                AbstractTree.checkBlockOrAddress(lowerBound, true);
            }
            if (upperBound != null) {
                AbstractTree.checkBlockOrAddress(upperBound, true);
            }
        }

        static <E extends Address> AddressBounds<E> createNewBounds(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive, Comparator<? super E> comparator) {
            if (lowerBound != null && lowerInclusive && lowerBound.isZero()) {
                lowerBound = null;
            }
            if (upperBound != null && upperInclusive && upperBound.isMax()) {
                upperBound = null;
            }
            if (lowerBound == null && upperBound == null) {
                return null;
            }
            return new AddressBounds<E>(lowerBound, lowerInclusive, upperBound, upperInclusive, comparator);
        }

        @Override
        AddressBounds<E> createBounds(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive, Comparator<? super E> comparator) {
            return new AddressBounds<E>(lowerBound, lowerInclusive, upperBound, upperInclusive, comparator);
        }

        @Override
        AddressBounds<E> restrict(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive) {
            return (AddressBounds)super.restrict(lowerBound, lowerInclusive, upperBound, upperInclusive);
        }

        @Override
        AddressBounds<E> intersect(E lowerBound, boolean lowerInclusive, E upperBound, boolean upperInclusive) {
            return (AddressBounds)super.intersect(lowerBound, lowerInclusive, upperBound, upperInclusive);
        }

        @Override
        boolean isAdjacentAboveUpperBound(E addr) {
            Object res = this.oneAboveUpperBound;
            if (res == null) {
                this.oneAboveUpperBound = res = AddressTrie.increment((Address)this.upperBound);
            }
            return res != null && ((Address)res).equals(addr);
        }

        @Override
        boolean isAdjacentBelowLowerBound(E addr) {
            Object res = this.oneBelowLowerBound;
            if (res == null) {
                this.oneBelowLowerBound = res = AddressTrie.decrement((Address)this.lowerBound);
            }
            return res != null && ((Address)res).equals(addr);
        }

        @Override
        boolean isAdjacentBelowUpperBound(E addr) {
            Object res = this.oneBelowUpperBound;
            if (res == null) {
                this.oneBelowUpperBound = res = AddressTrie.decrement((Address)this.upperBound);
            }
            return res != null && ((Address)res).equals(addr);
        }

        @Override
        boolean isAdjacentAboveLowerBound(E addr) {
            Object res = this.oneAboveLowerBound;
            if (res == null) {
                this.oneAboveLowerBound = res = AddressTrie.increment((Address)this.lowerBound);
            }
            return res != null && ((Address)res).equals(addr);
        }

        @Override
        boolean isMax(E addr) {
            return ((Address)addr).isMax();
        }

        @Override
        boolean isMin(E addr) {
            return ((Address)addr).isZero();
        }

        @Override
        public String toCanonicalString(String separator) {
            Function<Address, String> stringer = Address::toCanonicalString;
            return this.toString(stringer, separator, stringer);
        }
    }
}

