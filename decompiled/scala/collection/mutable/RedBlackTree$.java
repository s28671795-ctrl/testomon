/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.mutable.RedBlackTree;
import scala.math.Ordering;
import scala.runtime.Null$;

public final class RedBlackTree$ {
    public static final RedBlackTree$ MODULE$ = new RedBlackTree$();

    public boolean isRed(RedBlackTree.Node<?, ?> node) {
        return node != null && node.red();
    }

    public boolean isBlack(RedBlackTree.Node<?, ?> node) {
        return node == null || !node.red();
    }

    public int size(RedBlackTree.Node<?, ?> node) {
        if (node == null) {
            return 0;
        }
        return 1 + this.size(node.left()) + this.size(node.right());
    }

    public int size(RedBlackTree.Tree<?, ?> tree) {
        return tree.size();
    }

    public boolean isEmpty(RedBlackTree.Tree<?, ?> tree) {
        return tree.root() == null;
    }

    public void clear(RedBlackTree.Tree<?, ?> tree) {
        tree.root_$eq(null);
        tree.size_$eq(0);
    }

    public <A, B> Option<B> get(RedBlackTree.Tree<A, B> tree, A key, Ordering<A> evidence$1) {
        RedBlackTree.Node<A, B> node = this.getNode(tree.root(), key, evidence$1);
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<B>(node.value());
    }

    private <A, B> RedBlackTree.Node<A, B> getNode(RedBlackTree.Node<A, B> node, A key, Ordering<A> ord) {
        while (true) {
            if (node == null) {
                return null;
            }
            int cmp = ord.compare(key, node.key());
            if (cmp < 0) {
                node = node.left();
                continue;
            }
            if (cmp <= 0) break;
            node = node.right();
        }
        return node;
    }

    public <A> boolean contains(RedBlackTree.Tree<A, ?> tree, A key, Ordering<A> evidence$2) {
        return this.getNode(tree.root(), key, evidence$2) != null;
    }

    public <A, B> Option<Tuple2<A, B>> min(RedBlackTree.Tree<A, B> tree) {
        RedBlackTree.Node<A, B> node = this.scala$collection$mutable$RedBlackTree$$minNode(tree.root());
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<A, B>>(new Tuple2<A, B>(node.key(), node.value()));
    }

    public <A> Option<A> minKey(RedBlackTree.Tree<A, ?> tree) {
        RedBlackTree.Node<A, ?> node = this.scala$collection$mutable$RedBlackTree$$minNode(tree.root());
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<A>(node.key());
    }

    public <A, B> RedBlackTree.Node<A, B> scala$collection$mutable$RedBlackTree$$minNode(RedBlackTree.Node<A, B> node) {
        if (node == null) {
            return null;
        }
        return this.minNodeNonNull(node);
    }

    public <A, B> RedBlackTree.Node<A, B> minNodeNonNull(RedBlackTree.Node<A, B> node) {
        while (node.left() != null) {
            node = node.left();
        }
        return node;
    }

    public <A, B> Option<Tuple2<A, B>> max(RedBlackTree.Tree<A, B> tree) {
        RedBlackTree.Node<A, B> node = this.maxNode(tree.root());
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<A, B>>(new Tuple2<A, B>(node.key(), node.value()));
    }

    public <A> Option<A> maxKey(RedBlackTree.Tree<A, ?> tree) {
        RedBlackTree.Node<A, ?> node = this.maxNode(tree.root());
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<A>(node.key());
    }

    private <A, B> RedBlackTree.Node<A, B> maxNode(RedBlackTree.Node<A, B> node) {
        if (node == null) {
            return null;
        }
        return this.maxNodeNonNull(node);
    }

    public <A, B> RedBlackTree.Node<A, B> maxNodeNonNull(RedBlackTree.Node<A, B> node) {
        while (node.right() != null) {
            node = node.right();
        }
        return node;
    }

    public <A, B> Option<Tuple2<A, B>> minAfter(RedBlackTree.Tree<A, B> tree, A key, Ordering<A> ord) {
        RedBlackTree.Node<A, B> node = this.scala$collection$mutable$RedBlackTree$$minNodeAfter(tree.root(), key, ord);
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<A, B>>(new Tuple2<A, B>(node.key(), node.value()));
    }

    public <A> Option<A> minKeyAfter(RedBlackTree.Tree<A, ?> tree, A key, Ordering<A> ord) {
        RedBlackTree.Node<A, ?> node = this.scala$collection$mutable$RedBlackTree$$minNodeAfter(tree.root(), key, ord);
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<A>(node.key());
    }

    public <A, B> RedBlackTree.Node<A, B> scala$collection$mutable$RedBlackTree$$minNodeAfter(RedBlackTree.Node<A, B> node, A key, Ordering<A> ord) {
        if (node == null) {
            return null;
        }
        RedBlackTree.Node<A, B> y = null;
        RedBlackTree.Node<A, B> x = node;
        int cmp = 1;
        while (x != null && cmp != 0) {
            y = x;
            cmp = ord.compare(key, x.key());
            x = cmp < 0 ? x.left() : x.right();
        }
        if (cmp <= 0) {
            return y;
        }
        return this.scala$collection$mutable$RedBlackTree$$successor(y);
    }

    public <A, B> Option<Tuple2<A, B>> maxBefore(RedBlackTree.Tree<A, B> tree, A key, Ordering<A> ord) {
        RedBlackTree.Node<A, B> node = this.maxNodeBefore(tree.root(), key, ord);
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<A, B>>(new Tuple2<A, B>(node.key(), node.value()));
    }

    public <A> Option<A> maxKeyBefore(RedBlackTree.Tree<A, ?> tree, A key, Ordering<A> ord) {
        RedBlackTree.Node<A, ?> node = this.maxNodeBefore(tree.root(), key, ord);
        if (node == null) {
            return None$.MODULE$;
        }
        return new Some<A>(node.key());
    }

    private <A, B> RedBlackTree.Node<A, B> maxNodeBefore(RedBlackTree.Node<A, B> node, A key, Ordering<A> ord) {
        if (node == null) {
            return null;
        }
        RedBlackTree.Node<A, B> y = null;
        RedBlackTree.Node<A, B> x = node;
        int cmp = 1;
        while (x != null && cmp != 0) {
            y = x;
            cmp = ord.compare(key, x.key());
            x = cmp < 0 ? x.left() : x.right();
        }
        if (cmp > 0) {
            return y;
        }
        return this.predecessor(y);
    }

    public <A, B> void insert(RedBlackTree.Tree<A, B> tree, A key, B value, Ordering<A> ord) {
        RedBlackTree.Node<A, B> y = null;
        RedBlackTree.Node<A, B> x = tree.root();
        int cmp = 1;
        while (x != null && cmp != 0) {
            y = x;
            cmp = ord.compare(key, x.key());
            x = cmp < 0 ? x.left() : x.right();
        }
        if (cmp == 0) {
            y.value_$eq(value);
            return;
        }
        boolean leaf_red = true;
        RedBlackTree.Node<A, B> z = new RedBlackTree.Node<A, B>(key, value, leaf_red, null, null, y);
        if (y == null) {
            tree.root_$eq(z);
        } else if (cmp < 0) {
            y.left_$eq(z);
        } else {
            y.right_$eq(z);
        }
        this.fixAfterInsert(tree, z);
        tree.size_$eq(tree.size() + 1);
    }

    private <A, B> void fixAfterInsert(RedBlackTree.Tree<A, B> tree, RedBlackTree.Node<A, B> node) {
        RedBlackTree.Node<A, B> z = node;
        while (this.isRed(z.parent())) {
            if (z.parent() == z.parent().parent().left()) {
                RedBlackTree.Node<A, B> y = z.parent().parent().right();
                if (this.isRed(y)) {
                    z.parent().red_$eq(false);
                    y.red_$eq(false);
                    z.parent().parent().red_$eq(true);
                    z = z.parent().parent();
                    continue;
                }
                if (z == z.parent().right()) {
                    z = z.parent();
                    this.rotateLeft(tree, z);
                }
                z.parent().red_$eq(false);
                z.parent().parent().red_$eq(true);
                this.rotateRight(tree, z.parent().parent());
                continue;
            }
            RedBlackTree.Node<A, B> y = z.parent().parent().left();
            if (this.isRed(y)) {
                z.parent().red_$eq(false);
                y.red_$eq(false);
                z.parent().parent().red_$eq(true);
                z = z.parent().parent();
                continue;
            }
            if (z == z.parent().left()) {
                z = z.parent();
                this.rotateRight(tree, z);
            }
            z.parent().red_$eq(false);
            z.parent().parent().red_$eq(true);
            this.rotateLeft(tree, z.parent().parent());
        }
        tree.root().red_$eq(false);
    }

    public <A, B> void delete(RedBlackTree.Tree<A, B> tree, A key, Ordering<A> ord) {
        RedBlackTree.Node<A, B> z = this.getNode(tree.root(), key, ord);
        if (z != null) {
            RedBlackTree.Node<A, B> xParent;
            RedBlackTree.Node<A, B> x;
            boolean yIsRed = z.red();
            if (z.left() == null) {
                x = z.right();
                this.transplant(tree, z, z.right());
                xParent = z.parent();
            } else if (z.right() == null) {
                x = z.left();
                this.transplant(tree, z, z.left());
                xParent = z.parent();
            } else {
                RedBlackTree.Node<A, B> y = this.minNodeNonNull(z.right());
                yIsRed = y.red();
                x = y.right();
                if (y.parent() == z) {
                    xParent = y;
                } else {
                    xParent = y.parent();
                    this.transplant(tree, y, y.right());
                    y.right_$eq(z.right());
                    y.right().parent_$eq(y);
                }
                this.transplant(tree, z, y);
                y.left_$eq(z.left());
                y.left().parent_$eq(y);
                y.red_$eq(z.red());
            }
            if (!yIsRed) {
                this.fixAfterDelete(tree, x, xParent);
            }
            tree.size_$eq(tree.size() - 1);
            return;
        }
    }

    private <A, B> void fixAfterDelete(RedBlackTree.Tree<A, B> tree, RedBlackTree.Node<A, B> node, RedBlackTree.Node<A, B> parent) {
        RedBlackTree.Node<A, B> x = node;
        RedBlackTree.Node<A, B> xParent = parent;
        while (x != tree.root() && this.isBlack(x)) {
            if (x == xParent.left()) {
                RedBlackTree.Node<A, B> w = xParent.right();
                if (w.red()) {
                    w.red_$eq(false);
                    xParent.red_$eq(true);
                    this.rotateLeft(tree, xParent);
                    w = xParent.right();
                }
                if (this.isBlack(w.left()) && this.isBlack(w.right())) {
                    w.red_$eq(true);
                    x = xParent;
                } else {
                    if (this.isBlack(w.right())) {
                        w.left().red_$eq(false);
                        w.red_$eq(true);
                        this.rotateRight(tree, w);
                        w = xParent.right();
                    }
                    w.red_$eq(xParent.red());
                    xParent.red_$eq(false);
                    w.right().red_$eq(false);
                    this.rotateLeft(tree, xParent);
                    x = tree.root();
                }
            } else {
                RedBlackTree.Node<A, B> w = xParent.left();
                if (w.red()) {
                    w.red_$eq(false);
                    xParent.red_$eq(true);
                    this.rotateRight(tree, xParent);
                    w = xParent.left();
                }
                if (this.isBlack(w.right()) && this.isBlack(w.left())) {
                    w.red_$eq(true);
                    x = xParent;
                } else {
                    if (this.isBlack(w.left())) {
                        w.right().red_$eq(false);
                        w.red_$eq(true);
                        this.rotateLeft(tree, w);
                        w = xParent.left();
                    }
                    w.red_$eq(xParent.red());
                    xParent.red_$eq(false);
                    w.left().red_$eq(false);
                    this.rotateRight(tree, xParent);
                    x = tree.root();
                }
            }
            xParent = x.parent();
        }
        if (x != null) {
            x.red_$eq(false);
            return;
        }
    }

    public <A, B> RedBlackTree.Node<A, B> scala$collection$mutable$RedBlackTree$$successor(RedBlackTree.Node<A, B> node) {
        RedBlackTree.Node<A, B> y;
        if (node.right() != null) {
            return this.minNodeNonNull(node.right());
        }
        RedBlackTree.Node<A, B> x = node;
        for (y = node.parent(); y != null && x == y.right(); y = y.parent()) {
            x = y;
        }
        return y;
    }

    private <A, B> RedBlackTree.Node<A, B> predecessor(RedBlackTree.Node<A, B> node) {
        RedBlackTree.Node<A, B> y;
        if (node.left() != null) {
            return this.maxNodeNonNull(node.left());
        }
        RedBlackTree.Node<A, B> x = node;
        for (y = node.parent(); y != null && x == y.left(); y = y.parent()) {
            x = y;
        }
        return y;
    }

    private <A, B> void rotateLeft(RedBlackTree.Tree<A, B> tree, RedBlackTree.Node<A, B> x) {
        if (x != null) {
            RedBlackTree.Node<A, B> y = x.right();
            x.right_$eq(y.left());
            if (y.left() != null) {
                y.left().parent_$eq(x);
            }
            y.parent_$eq(x.parent());
            if (x.parent() == null) {
                tree.root_$eq(y);
            } else if (x == x.parent().left()) {
                x.parent().left_$eq(y);
            } else {
                x.parent().right_$eq(y);
            }
            y.left_$eq(x);
            x.parent_$eq(y);
            return;
        }
    }

    private <A, B> void rotateRight(RedBlackTree.Tree<A, B> tree, RedBlackTree.Node<A, B> x) {
        if (x != null) {
            RedBlackTree.Node<A, B> y = x.left();
            x.left_$eq(y.right());
            if (y.right() != null) {
                y.right().parent_$eq(x);
            }
            y.parent_$eq(x.parent());
            if (x.parent() == null) {
                tree.root_$eq(y);
            } else if (x == x.parent().right()) {
                x.parent().right_$eq(y);
            } else {
                x.parent().left_$eq(y);
            }
            y.right_$eq(x);
            x.parent_$eq(y);
            return;
        }
    }

    private <A, B> void transplant(RedBlackTree.Tree<A, B> tree, RedBlackTree.Node<A, B> to, RedBlackTree.Node<A, B> from) {
        if (to.parent() == null) {
            tree.root_$eq(from);
        } else if (to == to.parent().left()) {
            to.parent().left_$eq(from);
        } else {
            to.parent().right_$eq(from);
        }
        if (from != null) {
            from.parent_$eq(to.parent());
            return;
        }
    }

    public <A, B, U> void foreach(RedBlackTree.Tree<A, B> tree, Function1<Tuple2<A, B>, U> f) {
        RedBlackTree.Node<A, B> foreachNode_node = tree.root();
        if (foreachNode_node != null) {
            RedBlackTree.Node<A, B> foreachNode_foreachNodeNonNull_node = foreachNode_node;
            while (true) {
                if (foreachNode_foreachNodeNonNull_node.left() != null) {
                    RedBlackTree.Node<A, B> foreachNodeNonNull_node = foreachNode_foreachNodeNonNull_node.left();
                    while (true) {
                        if (foreachNodeNonNull_node.left() != null) {
                            this.foreachNodeNonNull(foreachNodeNonNull_node.left(), f);
                        }
                        f.apply(new Tuple2<A, B>(foreachNodeNonNull_node.key(), foreachNodeNonNull_node.value()));
                        if (foreachNodeNonNull_node.right() == null) break;
                        foreachNodeNonNull_node = foreachNodeNonNull_node.right();
                    }
                    Object var5_5 = null;
                }
                f.apply(new Tuple2<A, B>(foreachNode_foreachNodeNonNull_node.key(), foreachNode_foreachNodeNonNull_node.value()));
                if (foreachNode_foreachNodeNonNull_node.right() == null) break;
                foreachNode_foreachNodeNonNull_node = foreachNode_foreachNodeNonNull_node.right();
            }
            return;
        }
    }

    private <A, B, U> void foreachNode(RedBlackTree.Node<A, B> node, Function1<Tuple2<A, B>, U> f) {
        if (node != null) {
            RedBlackTree.Node<A, B> foreachNodeNonNull_node = node;
            while (true) {
                if (foreachNodeNonNull_node.left() != null) {
                    this.foreachNodeNonNull(foreachNodeNonNull_node.left(), f);
                }
                f.apply(new Tuple2<A, B>(foreachNodeNonNull_node.key(), foreachNodeNonNull_node.value()));
                if (foreachNodeNonNull_node.right() == null) break;
                foreachNodeNonNull_node = foreachNodeNonNull_node.right();
            }
            return;
        }
    }

    private <A, B, U> void foreachNodeNonNull(RedBlackTree.Node<A, B> node, Function1<Tuple2<A, B>, U> f) {
        while (true) {
            if (node.left() != null) {
                this.foreachNodeNonNull(node.left(), f);
            }
            f.apply(new Tuple2<A, B>(node.key(), node.value()));
            if (node.right() == null) break;
            node = node.right();
        }
    }

    public <A, U> void foreachKey(RedBlackTree.Tree<A, ?> tree, Function1<A, U> f) {
        RedBlackTree.Node<A, ?> r = tree.root();
        if (r != null) {
            RedBlackTree.Node<A, ?> g$1_node = r;
            while (true) {
                RedBlackTree.Node<A, ?> g$1_l;
                if ((g$1_l = g$1_node.left()) != null) {
                    this.g$1(g$1_l, f);
                }
                f.apply(g$1_node.key());
                RedBlackTree.Node<A, ?> g$1_r = g$1_node.right();
                if (g$1_r == null) break;
                g$1_node = g$1_r;
            }
            return;
        }
    }

    public <A, B, U> void foreachEntry(RedBlackTree.Tree<A, B> tree, Function2<A, B, U> f) {
        RedBlackTree.Node<A, B> r = tree.root();
        if (r != null) {
            RedBlackTree.Node<A, B> g$2_node = r;
            while (true) {
                RedBlackTree.Node<A, B> g$2_l;
                if ((g$2_l = g$2_node.left()) != null) {
                    this.g$2(g$2_l, f);
                }
                f.apply(g$2_node.key(), g$2_node.value());
                RedBlackTree.Node<A, B> g$2_r = g$2_node.right();
                if (g$2_r == null) break;
                g$2_node = g$2_r;
            }
            return;
        }
    }

    public <A, B> void transform(RedBlackTree.Tree<A, B> tree, Function2<A, B, B> f) {
        RedBlackTree.Node<A, B> transformNode_node = tree.root();
        if (transformNode_node != null) {
            RedBlackTree.Node<A, B> transformNode_transformNodeNonNull_node = transformNode_node;
            while (true) {
                if (transformNode_transformNodeNonNull_node.left() != null) {
                    RedBlackTree.Node<A, B> transformNodeNonNull_node = transformNode_transformNodeNonNull_node.left();
                    while (true) {
                        if (transformNodeNonNull_node.left() != null) {
                            this.transformNodeNonNull(transformNodeNonNull_node.left(), f);
                        }
                        transformNodeNonNull_node.value_$eq(f.apply(transformNodeNonNull_node.key(), transformNodeNonNull_node.value()));
                        if (transformNodeNonNull_node.right() == null) break;
                        transformNodeNonNull_node = transformNodeNonNull_node.right();
                    }
                    Object var5_5 = null;
                }
                transformNode_transformNodeNonNull_node.value_$eq(f.apply(transformNode_transformNodeNonNull_node.key(), transformNode_transformNodeNonNull_node.value()));
                if (transformNode_transformNodeNonNull_node.right() == null) break;
                transformNode_transformNodeNonNull_node = transformNode_transformNodeNonNull_node.right();
            }
            return;
        }
    }

    private <A, B, U> void transformNode(RedBlackTree.Node<A, B> node, Function2<A, B, B> f) {
        if (node != null) {
            RedBlackTree.Node<A, B> transformNodeNonNull_node = node;
            while (true) {
                if (transformNodeNonNull_node.left() != null) {
                    this.transformNodeNonNull(transformNodeNonNull_node.left(), f);
                }
                transformNodeNonNull_node.value_$eq(f.apply(transformNodeNonNull_node.key(), transformNodeNonNull_node.value()));
                if (transformNodeNonNull_node.right() == null) break;
                transformNodeNonNull_node = transformNodeNonNull_node.right();
            }
            return;
        }
    }

    private <A, B, U> void transformNodeNonNull(RedBlackTree.Node<A, B> node, Function2<A, B, B> f) {
        while (true) {
            if (node.left() != null) {
                this.transformNodeNonNull(node.left(), f);
            }
            node.value_$eq(f.apply(node.key(), node.value()));
            if (node.right() == null) break;
            node = node.right();
        }
    }

    public <A, B> Iterator<Tuple2<A, B>> iterator(RedBlackTree.Tree<A, B> tree, Option<A> start2, Option<A> end, Ordering<A> evidence$3) {
        return new RedBlackTree.EntriesIterator<A, B>(tree, start2, end, evidence$3);
    }

    public <A, B> None$ iterator$default$2() {
        return None$.MODULE$;
    }

    public <A, B> None$ iterator$default$3() {
        return None$.MODULE$;
    }

    public <A> Iterator<A> keysIterator(RedBlackTree.Tree<A, ?> tree, Option<A> start2, Option<A> end, Ordering<A> evidence$4) {
        return new RedBlackTree.KeysIterator(tree, start2, end, evidence$4);
    }

    public <A> None$ keysIterator$default$2() {
        return None$.MODULE$;
    }

    public <A> None$ keysIterator$default$3() {
        return None$.MODULE$;
    }

    public <A, B> Iterator<B> valuesIterator(RedBlackTree.Tree<A, B> tree, Option<A> start2, Option<A> end, Ordering<A> evidence$5) {
        return new RedBlackTree.ValuesIterator<A, B>(tree, start2, end, evidence$5);
    }

    public <A, B> None$ valuesIterator$default$2() {
        return None$.MODULE$;
    }

    public <A, B> None$ valuesIterator$default$3() {
        return None$.MODULE$;
    }

    public <A, B> boolean isValid(RedBlackTree.Tree<A, B> tree, Ordering<A> evidence$9) {
        return this.isValidBST(tree.root(), evidence$9) && this.hasProperParentRefs(tree) && this.isValidRedBlackTree(tree) && this.size(tree.root()) == tree.size();
    }

    private <A, B> boolean hasProperParentRefs(RedBlackTree.Tree<A, B> tree) {
        if (tree.root() == null) {
            return true;
        }
        return tree.root().parent() == null && this.hasProperParentRefs$1(tree.root());
    }

    private <A, B> boolean isValidBST(RedBlackTree.Node<A, B> node, Ordering<A> ord) {
        while (true) {
            if (node == null) {
                return true;
            }
            if (node.left() != null && ord.compare(node.key(), node.left().key()) <= 0 || node.right() != null && ord.compare(node.key(), node.right().key()) >= 0) {
                return false;
            }
            if (!this.isValidBST(node.left(), ord)) break;
            node = node.right();
        }
        return false;
    }

    private <A, B> boolean isValidRedBlackTree(RedBlackTree.Tree<A, B> tree) {
        return this.isBlack(tree.root()) && this.noRedAfterRed$1(tree.root()) && this.blackHeight$1(tree.root()) >= 0;
    }

    public <A> RedBlackTree.Tree<A, Null$> fromOrderedKeys(Iterator<A> xs, int size) {
        int maxUsedDepth = 32 - Integer.numberOfLeadingZeros(size);
        return new RedBlackTree.Tree(RedBlackTree$.f$3(1, size, xs, maxUsedDepth), size);
    }

    public <A, B> RedBlackTree.Tree<A, B> fromOrderedEntries(Iterator<Tuple2<A, B>> xs, int size) {
        int maxUsedDepth = 32 - Integer.numberOfLeadingZeros(size);
        return new RedBlackTree.Tree(RedBlackTree$.f$4(1, size, xs, maxUsedDepth), size);
    }

    public <A, B> RedBlackTree.Node<A, B> copyTree(RedBlackTree.Node<A, B> n) {
        if (n == null) {
            return null;
        }
        RedBlackTree.Node<A, B> c = new RedBlackTree.Node<A, B>(n.key(), n.value(), n.red(), this.copyTree(n.left()), this.copyTree(n.right()), null);
        if (c.left() != null) {
            c.left().parent_$eq(c);
        }
        if (c.right() != null) {
            c.right().parent_$eq(c);
        }
        return c;
    }

    private final void g$1(RedBlackTree.Node node, Function1 f$1) {
        while (true) {
            RedBlackTree.Node l;
            if ((l = node.left()) != null) {
                this.g$1(l, f$1);
            }
            f$1.apply(node.key());
            RedBlackTree.Node r = node.right();
            if (r == null) break;
            node = r;
        }
    }

    private final void g$2(RedBlackTree.Node node, Function2 f$2) {
        while (true) {
            RedBlackTree.Node l;
            if ((l = node.left()) != null) {
                this.g$2(l, f$2);
            }
            f$2.apply(node.key(), node.value());
            RedBlackTree.Node r = node.right();
            if (r == null) break;
            node = r;
        }
    }

    private final boolean hasProperParentRefs$1(RedBlackTree.Node node) {
        while (true) {
            if (node == null) {
                return true;
            }
            if (node.left() != null && node.left().parent() != node || node.right() != null && node.right().parent() != node) {
                return false;
            }
            if (!this.hasProperParentRefs$1(node.left())) break;
            node = node.right();
        }
        return false;
    }

    private final boolean noRedAfterRed$1(RedBlackTree.Node node) {
        while (true) {
            if (node == null) {
                return true;
            }
            if (node.red() && (this.isRed(node.left()) || this.isRed(node.right()))) {
                return false;
            }
            if (!this.noRedAfterRed$1(node.left())) break;
            node = node.right();
        }
        return false;
    }

    private final int blackHeight$1(RedBlackTree.Node node) {
        if (node == null) {
            return 1;
        }
        int lh = this.blackHeight$1(node.left());
        int rh = this.blackHeight$1(node.right());
        if (lh == -1 || lh != rh) {
            return -1;
        }
        if (this.isRed(node)) {
            return lh;
        }
        return lh + 1;
    }

    private static final RedBlackTree.Node f$3(int level, int size, Iterator xs$1, int maxUsedDepth$1) {
        switch (size) {
            case 0: {
                return null;
            }
            case 1: {
                return new RedBlackTree.Node(xs$1.next(), null, level == maxUsedDepth$1 && level != 1, null, null, null);
            }
        }
        int leftSize = (size - 1) / 2;
        RedBlackTree.Node left = RedBlackTree$.f$3(level + 1, leftSize, xs$1, maxUsedDepth$1);
        Object x = xs$1.next();
        RedBlackTree.Node right = RedBlackTree$.f$3(level + 1, size - 1 - leftSize, xs$1, maxUsedDepth$1);
        RedBlackTree.Node n = new RedBlackTree.Node(x, null, false, left, right, null);
        if (left != null) {
            left.parent_$eq(n);
        }
        right.parent_$eq(n);
        return n;
    }

    /*
     * WARNING - void declaration
     */
    private static final RedBlackTree.Node f$4(int level, int size, Iterator xs$2, int maxUsedDepth$2) {
        void var11_11;
        void var10_10;
        switch (size) {
            case 0: {
                return null;
            }
            case 1: {
                void var6_6;
                void var5_5;
                Tuple2 tuple2 = (Tuple2)xs$2.next();
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                Object k = tuple2._1();
                Object v = tuple2._2();
                return new RedBlackTree.Node<void, void>(var5_5, var6_6, level == maxUsedDepth$2 && level != 1, null, null, null);
            }
        }
        int leftSize = (size - 1) / 2;
        RedBlackTree.Node left = RedBlackTree$.f$4(level + 1, leftSize, xs$2, maxUsedDepth$2);
        Tuple2 tuple2 = (Tuple2)xs$2.next();
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        Object k = tuple2._1();
        Object v = tuple2._2();
        RedBlackTree.Node right = RedBlackTree$.f$4(level + 1, size - 1 - leftSize, xs$2, maxUsedDepth$2);
        RedBlackTree.Node<void, void> n = new RedBlackTree.Node<void, void>(var10_10, var11_11, false, left, right, null);
        if (left != null) {
            left.parent_$eq(n);
        }
        right.parent_$eq(n);
        return n;
    }

    private RedBlackTree$() {
    }
}

