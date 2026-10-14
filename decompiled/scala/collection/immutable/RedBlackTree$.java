/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.NoSuchElementException;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.collection.immutable.RedBlackTree;
import scala.collection.immutable.RedBlackTree$partitioner$1$;
import scala.math.Ordering;
import scala.runtime.BoxesRunTime;
import scala.runtime.LazyRef;
import scala.runtime.Nothing$;
import scala.runtime.Null$;

public final class RedBlackTree$ {
    public static final RedBlackTree$ MODULE$ = new RedBlackTree$();
    private static final Tuple2<Null$, Null$> null2 = new Tuple2<Object, Object>(null, null);

    public boolean isEmpty(RedBlackTree.Tree<?, ?> tree) {
        return tree == null;
    }

    public <A> boolean contains(RedBlackTree.Tree<A, ?> tree, A x, Ordering<A> evidence$1) {
        return this.lookup(tree, x, evidence$1) != null;
    }

    public <A, B> Option<B> get(RedBlackTree.Tree<A, B> tree, A x, Ordering<A> evidence$2) {
        RedBlackTree.Tree<A, B> tree2 = this.lookup(tree, x, evidence$2);
        if (tree2 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(tree2.scala$collection$immutable$RedBlackTree$Tree$$_value);
    }

    public <A, B> RedBlackTree.Tree<A, B> lookup(RedBlackTree.Tree<A, B> tree, A x, Ordering<A> ordering) {
        while (true) {
            if (tree == null) {
                return null;
            }
            int cmp = ordering.compare(x, tree.scala$collection$immutable$RedBlackTree$Tree$$_key);
            if (cmp < 0) {
                tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_left;
                continue;
            }
            if (cmp <= 0) break;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
        return tree;
    }

    public int count(RedBlackTree.Tree<?, ?> tree) {
        if (tree == null) {
            return 0;
        }
        return tree.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE;
    }

    public <A, B, B1> RedBlackTree.Tree<A, B1> update(RedBlackTree.Tree<A, B> tree, A k, B1 v, boolean overwrite, Ordering<A> evidence$3) {
        return this.blacken(this.upd(tree, k, v, overwrite, evidence$3));
    }

    public <A, B> RedBlackTree.Tree<A, B> delete(RedBlackTree.Tree<A, B> tree, A k, Ordering<A> evidence$4) {
        return this.blacken(this.del(tree, k, evidence$4));
    }

    public <A, B> RedBlackTree.Tree<A, B> rangeImpl(RedBlackTree.Tree<A, B> tree, Option<A> from, Option<A> until, Ordering<A> evidence$5) {
        Tuple2<Option<A>, Option<A>> tuple2 = new Tuple2<Option<A>, Option<A>>(from, until);
        if (from instanceof Some) {
            Object from2 = ((Some)from).value();
            if (until instanceof Some) {
                Object until2 = ((Some)until).value();
                return this.range(tree, from2, until2, evidence$5);
            }
        }
        if (from instanceof Some) {
            Object from3 = ((Some)from).value();
            if (None$.MODULE$.equals(until)) {
                return this.from(tree, from3, evidence$5);
            }
        }
        if (None$.MODULE$.equals(from) && until instanceof Some) {
            Object until3 = ((Some)until).value();
            return this.until(tree, until3, evidence$5);
        }
        if (None$.MODULE$.equals(from) && None$.MODULE$.equals(until)) {
            return tree;
        }
        throw new MatchError(tuple2);
    }

    public <A, B> RedBlackTree.Tree<A, B> range(RedBlackTree.Tree<A, B> tree, A from, A until, Ordering<A> evidence$6) {
        return this.blacken(this.doRange(tree, from, until, evidence$6));
    }

    public <A, B> RedBlackTree.Tree<A, B> from(RedBlackTree.Tree<A, B> tree, A from, Ordering<A> evidence$7) {
        return this.blacken(this.doFrom(tree, from, evidence$7));
    }

    public <A, B> RedBlackTree.Tree<A, B> to(RedBlackTree.Tree<A, B> tree, A to, Ordering<A> evidence$8) {
        return this.blacken(this.doTo(tree, to, evidence$8));
    }

    public <A, B> RedBlackTree.Tree<A, B> until(RedBlackTree.Tree<A, B> tree, A key, Ordering<A> evidence$9) {
        return this.blacken(this.doUntil(tree, key, evidence$9));
    }

    public <A, B> RedBlackTree.Tree<A, B> drop(RedBlackTree.Tree<A, B> tree, int n, Ordering<A> evidence$10) {
        return this.blacken(this.doDrop(tree, n));
    }

    public <A, B> RedBlackTree.Tree<A, B> take(RedBlackTree.Tree<A, B> tree, int n, Ordering<A> evidence$11) {
        return this.blacken(this.doTake(tree, n));
    }

    public <A, B> RedBlackTree.Tree<A, B> slice(RedBlackTree.Tree<A, B> tree, int from, int until, Ordering<A> evidence$12) {
        return this.blacken(this.doSlice(tree, from, until));
    }

    public <A, B> RedBlackTree.Tree<A, B> smallest(RedBlackTree.Tree<A, B> tree) {
        if (tree == null) {
            throw new NoSuchElementException("empty tree");
        }
        RedBlackTree.Tree<A, Object> result = tree;
        while (true) {
            if (result == null) {
                throw null;
            }
            if (result.scala$collection$immutable$RedBlackTree$Tree$$_left == null) break;
            result = result.scala$collection$immutable$RedBlackTree$Tree$$_left;
        }
        return result;
    }

    public <A, B> RedBlackTree.Tree<A, B> greatest(RedBlackTree.Tree<A, B> tree) {
        if (tree == null) {
            throw new NoSuchElementException("empty tree");
        }
        RedBlackTree.Tree<A, Object> result = tree;
        while (true) {
            if (result == null) {
                throw null;
            }
            if (result.scala$collection$immutable$RedBlackTree$Tree$$_right == null) break;
            result = result.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
        return result;
    }

    public <A, B> RedBlackTree.Tree<A, B> tail(RedBlackTree.Tree<A, B> tree) {
        return this.blacken(this._tail$1(tree));
    }

    public <A, B> RedBlackTree.Tree<A, B> init(RedBlackTree.Tree<A, B> tree) {
        return this.blacken(this._init$1(tree));
    }

    public <A, B> RedBlackTree.Tree<A, B> minAfter(RedBlackTree.Tree<A, B> tree, A x, Ordering<A> ordering) {
        while (tree != null) {
            int cmp = ordering.compare(x, tree.scala$collection$immutable$RedBlackTree$Tree$$_key);
            if (cmp == 0) {
                return tree;
            }
            if (cmp < 0) {
                RedBlackTree.Tree l = this.minAfter(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, x, ordering);
                if (l != null) {
                    return l;
                }
                return tree;
            }
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
        return null;
    }

    public <A, B> RedBlackTree.Tree<A, B> maxBefore(RedBlackTree.Tree<A, B> tree, A x, Ordering<A> ordering) {
        while (true) {
            if (tree == null) {
                return null;
            }
            if (ordering.compare(x, tree.scala$collection$immutable$RedBlackTree$Tree$$_key) > 0) break;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_left;
        }
        RedBlackTree.Tree r = this.maxBefore(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, x, ordering);
        if (r != null) {
            return r;
        }
        return tree;
    }

    public <A, B, U> void foreach(RedBlackTree.Tree<A, B> tree, Function1<Tuple2<A, B>, U> f) {
        if (tree != null) {
            RedBlackTree.Tree<A, Object> _foreach_tree = tree;
            while (true) {
                if (_foreach_tree == null) {
                    throw null;
                }
                if (_foreach_tree.scala$collection$immutable$RedBlackTree$Tree$$_left != null) {
                    this._foreach(_foreach_tree.scala$collection$immutable$RedBlackTree$Tree$$_left, f);
                }
                f.apply(new Tuple2(_foreach_tree.scala$collection$immutable$RedBlackTree$Tree$$_key, _foreach_tree.scala$collection$immutable$RedBlackTree$Tree$$_value));
                if (_foreach_tree.scala$collection$immutable$RedBlackTree$Tree$$_right == null) break;
                _foreach_tree = _foreach_tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
            }
            return;
        }
    }

    public <A, X, Y> boolean keysEqual(RedBlackTree.Tree<A, X> a, RedBlackTree.Tree<A, Y> b, Ordering<A> evidence$13) {
        if (a == b) {
            return true;
        }
        if (a == null) {
            return false;
        }
        if (b == null) {
            return false;
        }
        return (a.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) == (b.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) && new RedBlackTree.EqualsIterator<A, Y>(a, evidence$13).sameKeys(new RedBlackTree.EqualsIterator<A, Y>(b, evidence$13));
    }

    public <A, X, Y> boolean valuesEqual(RedBlackTree.Tree<A, X> a, RedBlackTree.Tree<A, Y> b, Ordering<A> evidence$14) {
        if (a == b) {
            return true;
        }
        if (a == null) {
            return false;
        }
        if (b == null) {
            return false;
        }
        return (a.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) == (b.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) && new RedBlackTree.EqualsIterator<A, Y>(a, evidence$14).sameValues(new RedBlackTree.EqualsIterator<A, Y>(b, evidence$14));
    }

    public <A, X, Y> boolean entriesEqual(RedBlackTree.Tree<A, X> a, RedBlackTree.Tree<A, Y> b, Ordering<A> evidence$15) {
        if (a == b) {
            return true;
        }
        if (a == null) {
            return false;
        }
        if (b == null) {
            return false;
        }
        return (a.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) == (b.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) && new RedBlackTree.EqualsIterator<A, Y>(a, evidence$15).sameEntries(new RedBlackTree.EqualsIterator<A, Y>(b, evidence$15));
    }

    private <A, B, U> void _foreach(RedBlackTree.Tree<A, B> tree, Function1<Tuple2<A, B>, U> f) {
        while (true) {
            if (tree == null) {
                throw null;
            }
            if (tree.scala$collection$immutable$RedBlackTree$Tree$$_left != null) {
                this._foreach(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, f);
            }
            f.apply(new Tuple2(tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value));
            if (tree.scala$collection$immutable$RedBlackTree$Tree$$_right == null) break;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
    }

    public <A, U> void foreachKey(RedBlackTree.Tree<A, ?> tree, Function1<A, U> f) {
        if (tree != null) {
            RedBlackTree.Tree<A, ?> _foreachKey_tree = tree;
            while (true) {
                if (_foreachKey_tree == null) {
                    throw null;
                }
                if (_foreachKey_tree.scala$collection$immutable$RedBlackTree$Tree$$_left != null) {
                    this._foreachKey(_foreachKey_tree.scala$collection$immutable$RedBlackTree$Tree$$_left, f);
                }
                f.apply(_foreachKey_tree.scala$collection$immutable$RedBlackTree$Tree$$_key);
                if (_foreachKey_tree.scala$collection$immutable$RedBlackTree$Tree$$_right == null) break;
                _foreachKey_tree = _foreachKey_tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
            }
            return;
        }
    }

    private <A, U> void _foreachKey(RedBlackTree.Tree<A, ?> tree, Function1<A, U> f) {
        while (true) {
            if (tree == null) {
                throw null;
            }
            if (tree.scala$collection$immutable$RedBlackTree$Tree$$_left != null) {
                this._foreachKey(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, f);
            }
            f.apply(tree.scala$collection$immutable$RedBlackTree$Tree$$_key);
            if (tree.scala$collection$immutable$RedBlackTree$Tree$$_right == null) break;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
    }

    public <A, B, U> void foreachEntry(RedBlackTree.Tree<A, B> tree, Function2<A, B, U> f) {
        if (tree != null) {
            RedBlackTree.Tree<A, Object> _foreachEntry_tree = tree;
            while (true) {
                if (_foreachEntry_tree == null) {
                    throw null;
                }
                if (_foreachEntry_tree.scala$collection$immutable$RedBlackTree$Tree$$_left != null) {
                    this._foreachEntry(_foreachEntry_tree.scala$collection$immutable$RedBlackTree$Tree$$_left, f);
                }
                f.apply(_foreachEntry_tree.scala$collection$immutable$RedBlackTree$Tree$$_key, _foreachEntry_tree.scala$collection$immutable$RedBlackTree$Tree$$_value);
                if (_foreachEntry_tree.scala$collection$immutable$RedBlackTree$Tree$$_right == null) break;
                _foreachEntry_tree = _foreachEntry_tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
            }
            return;
        }
    }

    private <A, B, U> void _foreachEntry(RedBlackTree.Tree<A, B> tree, Function2<A, B, U> f) {
        while (true) {
            if (tree == null) {
                throw null;
            }
            if (tree.scala$collection$immutable$RedBlackTree$Tree$$_left != null) {
                this._foreachEntry(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, f);
            }
            f.apply(tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value);
            if (tree.scala$collection$immutable$RedBlackTree$Tree$$_right == null) break;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
    }

    public <A, B> Iterator<Tuple2<A, B>> iterator(RedBlackTree.Tree<A, B> tree, Option<A> start2, Ordering<A> evidence$16) {
        return new RedBlackTree.EntriesIterator<A, B>(tree, start2, evidence$16);
    }

    public <A, B> None$ iterator$default$2() {
        return None$.MODULE$;
    }

    public <A> Iterator<A> keysIterator(RedBlackTree.Tree<A, ?> tree, Option<A> start2, Ordering<A> evidence$17) {
        return new RedBlackTree.KeysIterator(tree, start2, evidence$17);
    }

    public <A> None$ keysIterator$default$2() {
        return None$.MODULE$;
    }

    public <A, B> Iterator<B> valuesIterator(RedBlackTree.Tree<A, B> tree, Option<A> start2, Ordering<A> evidence$18) {
        return new RedBlackTree.ValuesIterator<A, B>(tree, start2, evidence$18);
    }

    public <A, B> None$ valuesIterator$default$2() {
        return None$.MODULE$;
    }

    public <A, B> RedBlackTree.Tree<A, B> nth(RedBlackTree.Tree<A, B> tree, int n) {
        while (true) {
            if (tree == null) {
                throw null;
            }
            int count = this.count(tree.scala$collection$immutable$RedBlackTree$Tree$$_left);
            if (n < count) {
                tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_left;
                continue;
            }
            if (n <= count) break;
            n = n - count - 1;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
        return tree;
    }

    public boolean isBlack(RedBlackTree.Tree<?, ?> tree) {
        return tree == null || tree.scala$collection$immutable$RedBlackTree$Tree$$_count < 0;
    }

    public boolean scala$collection$immutable$RedBlackTree$$isRedTree(RedBlackTree.Tree<?, ?> tree) {
        return tree != null && tree.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0;
    }

    private boolean isBlackTree(RedBlackTree.Tree<?, ?> tree) {
        return tree != null && tree.scala$collection$immutable$RedBlackTree$Tree$$_count < 0;
    }

    private <A, B> RedBlackTree.Tree<A, B> blacken(RedBlackTree.Tree<A, B> t2) {
        if (t2 == null) {
            return null;
        }
        return t2.black();
    }

    private <A, B> RedBlackTree.Tree<A, B> maybeBlacken(RedBlackTree.Tree<A, B> t2) {
        block6: {
            block5: {
                if (this.isBlack(t2)) {
                    return t2;
                }
                if (t2 == null) {
                    throw null;
                }
                Object var2_2 = null;
                RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree = t2.scala$collection$immutable$RedBlackTree$Tree$$_left;
                if (scala$collection$immutable$RedBlackTree$$isRedTree_tree != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) break block5;
                Object var3_3 = null;
                RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree2 = t2.scala$collection$immutable$RedBlackTree$Tree$$_right;
                if (!(scala$collection$immutable$RedBlackTree$$isRedTree_tree2 != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree2.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0)) break block6;
            }
            return t2.black();
        }
        return t2;
    }

    private <A, B> RedBlackTree.Tree<A, Nothing$> mkTree(boolean isBlack, A key, B value, RedBlackTree.Tree<A, B> left, RedBlackTree.Tree<A, B> right) {
        int sizeAndColour = (left == null ? 0 : left.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) + (right == null ? 0 : right.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) + 1 | (isBlack ? Integer.MIN_VALUE : 0);
        return new RedBlackTree.Tree(key, value, left, right, sizeAndColour);
    }

    private <A, B1> RedBlackTree.Tree<A, B1> balanceLeft(RedBlackTree.Tree<A, B1> tree, RedBlackTree.Tree<A, B1> newLeft) {
        if (tree == null) {
            throw null;
        }
        if (tree.scala$collection$immutable$RedBlackTree$Tree$$_left == newLeft) {
            return tree;
        }
        if (newLeft == null) {
            throw null;
        }
        if (newLeft.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            RedBlackTree.Tree newLeft_left = newLeft.scala$collection$immutable$RedBlackTree$Tree$$_left;
            RedBlackTree.Tree<A, ?> newLeft_right = newLeft.scala$collection$immutable$RedBlackTree$Tree$$_right;
            if (newLeft_left != null && newLeft_left.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                RedBlackTree.Tree resultLeft = newLeft_left.black();
                RedBlackTree.Tree<A, ?> resultRight = tree.blackWithLeft(newLeft_right);
                return newLeft.withLeftRight(resultLeft, resultRight);
            }
            if (newLeft_right != null && newLeft_right.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                if (newLeft_right == null) {
                    throw null;
                }
                RedBlackTree.Tree newLeft_right_right = newLeft_right.scala$collection$immutable$RedBlackTree$Tree$$_right;
                RedBlackTree.Tree<A, ?> resultLeft = newLeft.blackWithRight(newLeft_right.scala$collection$immutable$RedBlackTree$Tree$$_left);
                RedBlackTree.Tree<A, ?> resultRight = tree.blackWithLeft(newLeft_right_right);
                return newLeft_right.withLeftRight(resultLeft, resultRight);
            }
            return tree.withLeft(newLeft);
        }
        return tree.withLeft(newLeft);
    }

    private <A, B1> RedBlackTree.Tree<A, B1> balanceRight(RedBlackTree.Tree<A, B1> tree, RedBlackTree.Tree<A, B1> newRight) {
        if (tree == null) {
            throw null;
        }
        if (tree.scala$collection$immutable$RedBlackTree$Tree$$_right == newRight) {
            return tree;
        }
        if (newRight == null) {
            throw null;
        }
        if (newRight.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            RedBlackTree.Tree<A, ?> newRight_left = newRight.scala$collection$immutable$RedBlackTree$Tree$$_left;
            if (newRight_left != null && newRight_left.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                if (newRight_left == null) {
                    throw null;
                }
                RedBlackTree.Tree<A, ?> resultLeft = tree.blackWithRight(newRight_left.scala$collection$immutable$RedBlackTree$Tree$$_left);
                RedBlackTree.Tree<A, ?> resultRight = newRight.blackWithLeft(newRight_left.scala$collection$immutable$RedBlackTree$Tree$$_right);
                return newRight_left.withLeftRight(resultLeft, resultRight);
            }
            RedBlackTree.Tree newRight_right = newRight.scala$collection$immutable$RedBlackTree$Tree$$_right;
            if (newRight_right != null && newRight_right.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                RedBlackTree.Tree<A, ?> resultLeft = tree.blackWithRight(newRight_left);
                RedBlackTree.Tree resultRight = newRight_right.black();
                return newRight.withLeftRight(resultLeft, resultRight);
            }
            return tree.withRight(newRight);
        }
        return tree.withRight(newRight);
    }

    private <A, B, B1> RedBlackTree.Tree<A, B1> upd(RedBlackTree.Tree<A, B> tree, A k, B1 v, boolean overwrite, Ordering<A> ordering) {
        if (tree == null) {
            return this.RedTree(k, v, null, null);
        }
        if (k == tree.scala$collection$immutable$RedBlackTree$Tree$$_key) {
            if (overwrite) {
                return tree.withV(v);
            }
            return tree;
        }
        int cmp = ordering.compare(k, tree.scala$collection$immutable$RedBlackTree$Tree$$_key);
        if (cmp < 0) {
            return this.balanceLeft(tree, this.upd(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, k, v, overwrite, ordering));
        }
        if (cmp > 0) {
            return this.balanceRight(tree, this.upd(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, k, v, overwrite, ordering));
        }
        if (overwrite && v != tree.scala$collection$immutable$RedBlackTree$Tree$$_value) {
            return tree.withV(v);
        }
        return tree;
    }

    private <A, B, B1> RedBlackTree.Tree<A, B1> updNth(RedBlackTree.Tree<A, B> tree, int idx, A k, B1 v) {
        if (tree == null) {
            return this.RedTree(k, v, null, null);
        }
        int rank = this.count(tree.scala$collection$immutable$RedBlackTree$Tree$$_left) + 1;
        if (idx < rank) {
            return this.balanceLeft(tree, this.updNth(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, idx, k, v));
        }
        if (idx > rank) {
            return this.balanceRight(tree, this.updNth(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, idx - rank, k, v));
        }
        return tree;
    }

    private <A, B> RedBlackTree.Tree<A, B> doFrom(RedBlackTree.Tree<A, B> tree, A from, Ordering<A> ordering) {
        if (tree == null) {
            return null;
        }
        if (ordering.lt(tree.scala$collection$immutable$RedBlackTree$Tree$$_key, from)) {
            return this.doFrom(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, from, ordering);
        }
        RedBlackTree.Tree newLeft = this.doFrom(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, from, ordering);
        if (newLeft == tree.scala$collection$immutable$RedBlackTree$Tree$$_left) {
            return tree;
        }
        if (newLeft == null) {
            return this.upd(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, false, ordering);
        }
        return this.scala$collection$immutable$RedBlackTree$$join(newLeft, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, tree.scala$collection$immutable$RedBlackTree$Tree$$_right);
    }

    private <A, B> RedBlackTree.Tree<A, B> doTo(RedBlackTree.Tree<A, B> tree, A to, Ordering<A> ordering) {
        if (tree == null) {
            return null;
        }
        if (ordering.lt(to, tree.scala$collection$immutable$RedBlackTree$Tree$$_key)) {
            return this.doTo(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, to, ordering);
        }
        RedBlackTree.Tree newRight = this.doTo(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, to, ordering);
        if (newRight == tree.scala$collection$immutable$RedBlackTree$Tree$$_right) {
            return tree;
        }
        if (newRight == null) {
            return this.upd(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, false, ordering);
        }
        return this.scala$collection$immutable$RedBlackTree$$join(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, newRight);
    }

    private <A, B> RedBlackTree.Tree<A, B> doUntil(RedBlackTree.Tree<A, B> tree, A until, Ordering<A> ordering) {
        if (tree == null) {
            return null;
        }
        if (ordering.lteq(until, tree.scala$collection$immutable$RedBlackTree$Tree$$_key)) {
            return this.doUntil(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, until, ordering);
        }
        RedBlackTree.Tree newRight = this.doUntil(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, until, ordering);
        if (newRight == tree.scala$collection$immutable$RedBlackTree$Tree$$_right) {
            return tree;
        }
        if (newRight == null) {
            return this.upd(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, false, ordering);
        }
        return this.scala$collection$immutable$RedBlackTree$$join(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, newRight);
    }

    private <A, B> RedBlackTree.Tree<A, B> doRange(RedBlackTree.Tree<A, B> tree, A from, A until, Ordering<A> ordering) {
        if (tree == null) {
            return null;
        }
        if (ordering.lt(tree.scala$collection$immutable$RedBlackTree$Tree$$_key, from)) {
            return this.doRange(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, from, until, ordering);
        }
        if (ordering.lteq(until, tree.scala$collection$immutable$RedBlackTree$Tree$$_key)) {
            return this.doRange(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, from, until, ordering);
        }
        RedBlackTree.Tree newLeft = this.doFrom(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, from, ordering);
        RedBlackTree.Tree newRight = this.doUntil(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, until, ordering);
        if (newLeft == tree.scala$collection$immutable$RedBlackTree$Tree$$_left && newRight == tree.scala$collection$immutable$RedBlackTree$Tree$$_right) {
            return tree;
        }
        if (newLeft == null) {
            return this.upd(newRight, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, false, ordering);
        }
        if (newRight == null) {
            return this.upd(newLeft, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, false, ordering);
        }
        return this.scala$collection$immutable$RedBlackTree$$join(newLeft, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, newRight);
    }

    private <A, B> RedBlackTree.Tree<A, B> doDrop(RedBlackTree.Tree<A, B> tree, int n) {
        int l;
        while (true) {
            if (tree == null || n <= 0) {
                return tree;
            }
            if (n >= (tree.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE)) {
                return null;
            }
            l = this.count(tree.scala$collection$immutable$RedBlackTree$Tree$$_left);
            if (n <= l) break;
            n = n - l - 1;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
        if (n == l) {
            return this.scala$collection$immutable$RedBlackTree$$join(null, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, tree.scala$collection$immutable$RedBlackTree$Tree$$_right);
        }
        return this.scala$collection$immutable$RedBlackTree$$join(this.doDrop(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, n), tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, tree.scala$collection$immutable$RedBlackTree$Tree$$_right);
    }

    private <A, B> RedBlackTree.Tree<A, B> doTake(RedBlackTree.Tree<A, B> tree, int n) {
        int l;
        while (true) {
            if (tree == null || n <= 0) {
                return null;
            }
            if (n >= (tree.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE)) {
                return tree;
            }
            l = this.count(tree.scala$collection$immutable$RedBlackTree$Tree$$_left);
            if (n > l) break;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_left;
        }
        if (n == l + 1) {
            return this.maybeBlacken(this.updNth(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, n, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value));
        }
        return this.scala$collection$immutable$RedBlackTree$$join(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, this.doTake(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, n - l - 1));
    }

    private <A, B> RedBlackTree.Tree<A, B> doSlice(RedBlackTree.Tree<A, B> tree, int from, int until) {
        int l;
        while (true) {
            if (tree == null || from >= until || from >= (tree.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) || until <= 0) {
                return null;
            }
            if (from <= 0 && until >= (tree.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE)) {
                return tree;
            }
            l = this.count(tree.scala$collection$immutable$RedBlackTree$Tree$$_left);
            if (until <= l) {
                tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_left;
                continue;
            }
            if (from <= l) break;
            until = until - l - 1;
            from = from - l - 1;
            tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
        return this.scala$collection$immutable$RedBlackTree$$join(this.doDrop(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, from), tree.scala$collection$immutable$RedBlackTree$Tree$$_key, tree.scala$collection$immutable$RedBlackTree$Tree$$_value, this.doTake(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, until - l - 1));
    }

    public final int colourBit() {
        return Integer.MIN_VALUE;
    }

    public final int colourMask() {
        return Integer.MAX_VALUE;
    }

    public final int initialBlackCount() {
        return Integer.MIN_VALUE;
    }

    public final int initialRedCount() {
        return 0;
    }

    public <A, B> RedBlackTree.Tree<A, B> mutableRedTree(A key, B value, RedBlackTree.Tree<A, B> left, RedBlackTree.Tree<A, B> right) {
        return new RedBlackTree.Tree(key, value, left, right, 0);
    }

    public <A, B> RedBlackTree.Tree<A, B> mutableBlackTree(A key, B value, RedBlackTree.Tree<A, B> left, RedBlackTree.Tree<A, B> right) {
        return new RedBlackTree.Tree(key, value, left, right, Integer.MIN_VALUE);
    }

    public <A, B> RedBlackTree.Tree<A, B> RedTree(A key, B value, RedBlackTree.Tree<A, B> left, RedBlackTree.Tree<A, B> right) {
        int size = (left == null ? 0 : left.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) + (right == null ? 0 : right.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) + 1;
        return new RedBlackTree.Tree(key, value, left, right, 0 | size);
    }

    public <A, B> RedBlackTree.Tree<A, B> BlackTree(A key, B value, RedBlackTree.Tree<A, B> left, RedBlackTree.Tree<A, B> right) {
        int size = (left == null ? 0 : left.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) + (right == null ? 0 : right.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE) + 1;
        return new RedBlackTree.Tree(key, value, left, right, Integer.MIN_VALUE | size);
    }

    private int sizeOf(RedBlackTree.Tree<?, ?> tree) {
        if (tree == null) {
            return 0;
        }
        return tree.scala$collection$immutable$RedBlackTree$Tree$$_count & Integer.MAX_VALUE;
    }

    public <A> RedBlackTree.Tree<A, Null$> fromOrderedKeys(Iterator<A> xs, int size) {
        int maxUsedDepth = 32 - Integer.numberOfLeadingZeros(size);
        return this.f$1(1, size, maxUsedDepth, xs);
    }

    public <A, B> RedBlackTree.Tree<A, B> fromOrderedEntries(Iterator<Tuple2<A, B>> xs, int size) {
        int maxUsedDepth = 32 - Integer.numberOfLeadingZeros(size);
        return this.f$2(1, size, xs, maxUsedDepth);
    }

    public <A, B, C> RedBlackTree.Tree<A, C> transform(RedBlackTree.Tree<A, B> t2, Function2<A, B, C> f) {
        if (t2 == null) {
            return null;
        }
        Object k = t2.scala$collection$immutable$RedBlackTree$Tree$$_key;
        Object v = t2.scala$collection$immutable$RedBlackTree$Tree$$_value;
        RedBlackTree.Tree l = t2.scala$collection$immutable$RedBlackTree$Tree$$_left;
        RedBlackTree.Tree r = t2.scala$collection$immutable$RedBlackTree$Tree$$_right;
        RedBlackTree.Tree l2 = this.transform(l, f);
        C v2 = f.apply(k, v);
        RedBlackTree.Tree r2 = this.transform(r, f);
        if (v2 == v && l2 == l && r2 == r) {
            return t2;
        }
        return this.mkTree(t2.scala$collection$immutable$RedBlackTree$Tree$$_count < 0, k, v2, l2, r2);
    }

    public <A, B> RedBlackTree.Tree<A, B> filterEntries(RedBlackTree.Tree<A, B> t2, Function2<A, B, Object> f) {
        RedBlackTree.Tree fk$1_r2;
        if (t2 == null) {
            return null;
        }
        Object fk$1_k = t2.scala$collection$immutable$RedBlackTree$Tree$$_key;
        Object fk$1_v = t2.scala$collection$immutable$RedBlackTree$Tree$$_value;
        RedBlackTree.Tree fk$1_l = t2.scala$collection$immutable$RedBlackTree$Tree$$_left;
        RedBlackTree.Tree fk$1_r = t2.scala$collection$immutable$RedBlackTree$Tree$$_right;
        RedBlackTree.Tree fk$1_l2 = fk$1_l == null ? null : this.fk$1(fk$1_l, f);
        boolean fk$1_keep = BoxesRunTime.unboxToBoolean(f.apply(fk$1_k, fk$1_v));
        RedBlackTree.Tree tree = fk$1_r2 = fk$1_r == null ? null : this.fk$1(fk$1_r, f);
        Object var3_3 = null;
        Object var4_4 = null;
        Object var5_5 = null;
        Object var6_6 = null;
        Object var7_7 = null;
        Object var9_9 = null;
        return this.blacken(!fk$1_keep ? this.scala$collection$immutable$RedBlackTree$$join2(fk$1_l2, fk$1_r2) : (fk$1_l2 == fk$1_l && fk$1_r2 == fk$1_r ? t2 : this.scala$collection$immutable$RedBlackTree$$join(fk$1_l2, fk$1_k, fk$1_v, fk$1_r2)));
    }

    public <A, B> Tuple2<RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>> partitionEntries(RedBlackTree.Tree<A, B> t2, Function2<A, B, Object> p) {
        RedBlackTree$partitioner$1$ redBlackTree$partitioner$1$;
        RedBlackTree$partitioner$1$ redBlackTree$partitioner$1$2;
        if (t2 == null) {
            return new Tuple2<Object, Object>(null, null);
        }
        LazyRef<RedBlackTree$partitioner$1$> partitioner$module = new LazyRef<RedBlackTree$partitioner$1$>();
        if (partitioner$module.initialized()) {
            redBlackTree$partitioner$1$2 = (RedBlackTree$partitioner$1$)partitioner$module.value();
        } else {
            synchronized (partitioner$module) {
                redBlackTree$partitioner$1$ = partitioner$module.initialized() ? (RedBlackTree$partitioner$1$)partitioner$module.value() : partitioner$module.initialize(new RedBlackTree$partitioner$1$(p));
                // MONITOREXIT @DISABLED, blocks:[0, 1, 4] lbl10 : MonitorExitStatement: MONITOREXIT : partitioner$module
                redBlackTree$partitioner$1$2 = redBlackTree$partitioner$1$;
            }
            redBlackTree$partitioner$1$ = null;
        }
        redBlackTree$partitioner$1$ = null;
        redBlackTree$partitioner$1$2.fk(t2);
        return new Tuple2(this.blacken(this.partitioner$2(partitioner$module, p).tmpk()), this.blacken(this.partitioner$2(partitioner$module, p).tmpd()));
    }

    private <A, B> RedBlackTree.Tree<A, B> del(RedBlackTree.Tree<A, B> tree, A k, Ordering<A> ordering) {
        if (tree == null) {
            return null;
        }
        int cmp = ordering.compare(k, tree.scala$collection$immutable$RedBlackTree$Tree$$_key);
        if (cmp < 0) {
            RedBlackTree.Tree newLeft = this.del(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, k, ordering);
            if (newLeft == tree.scala$collection$immutable$RedBlackTree$Tree$$_left) {
                return tree;
            }
            Object var7_6 = null;
            RedBlackTree.Tree isBlackTree_tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_left;
            if (isBlackTree_tree != null && isBlackTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
                return this.balLeft(tree, newLeft, tree.scala$collection$immutable$RedBlackTree$Tree$$_right);
            }
            return tree.redWithLeft(newLeft);
        }
        if (cmp > 0) {
            RedBlackTree.Tree newRight = this.del(tree.scala$collection$immutable$RedBlackTree$Tree$$_right, k, ordering);
            if (newRight == tree.scala$collection$immutable$RedBlackTree$Tree$$_right) {
                return tree;
            }
            Object var8_8 = null;
            RedBlackTree.Tree isBlackTree_tree = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
            if (isBlackTree_tree != null && isBlackTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
                return this.balRight(tree, tree.scala$collection$immutable$RedBlackTree$Tree$$_left, newRight);
            }
            return tree.redWithRight(newRight);
        }
        return this.append(tree.scala$collection$immutable$RedBlackTree$Tree$$_left, tree.scala$collection$immutable$RedBlackTree$Tree$$_right);
    }

    private <A, B> RedBlackTree.Tree<A, B> balance(RedBlackTree.Tree<A, B> tree, RedBlackTree.Tree<A, B> tl, RedBlackTree.Tree<A, B> tr) {
        if (tl != null && tl.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            if (tr != null && tr.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                return tree.redWithLeftRight(tl.black(), tr.black());
            }
            if (tl == null) {
                throw null;
            }
            Object var4_4 = null;
            RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree = tl.scala$collection$immutable$RedBlackTree$Tree$$_left;
            if (scala$collection$immutable$RedBlackTree$$isRedTree_tree != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                return tl.withLeftRight(tl.scala$collection$immutable$RedBlackTree$Tree$$_left.black(), tree.blackWithLeftRight(tl.scala$collection$immutable$RedBlackTree$Tree$$_right, tr));
            }
            Object var5_5 = null;
            RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree2 = tl.scala$collection$immutable$RedBlackTree$Tree$$_right;
            if (scala$collection$immutable$RedBlackTree$$isRedTree_tree2 != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree2.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                RedBlackTree.Tree tree2 = tl.scala$collection$immutable$RedBlackTree$Tree$$_right;
                if (tree2 == null) {
                    throw null;
                }
                RedBlackTree.Tree tree3 = tl.scala$collection$immutable$RedBlackTree$Tree$$_right;
                if (tree3 == null) {
                    throw null;
                }
                return tl.scala$collection$immutable$RedBlackTree$Tree$$_right.withLeftRight(tl.blackWithRight(tree2.scala$collection$immutable$RedBlackTree$Tree$$_left), tree.blackWithLeftRight(tree3.scala$collection$immutable$RedBlackTree$Tree$$_right, tr));
            }
            return tree.blackWithLeftRight(tl, tr);
        }
        if (tr != null && tr.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            if (tr == null) {
                throw null;
            }
            Object var6_6 = null;
            RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree = tr.scala$collection$immutable$RedBlackTree$Tree$$_right;
            if (scala$collection$immutable$RedBlackTree$$isRedTree_tree != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                return tr.withLeftRight(tree.blackWithLeftRight(tl, tr.scala$collection$immutable$RedBlackTree$Tree$$_left), tr.scala$collection$immutable$RedBlackTree$Tree$$_right.black());
            }
            Object var7_7 = null;
            RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree3 = tr.scala$collection$immutable$RedBlackTree$Tree$$_left;
            if (scala$collection$immutable$RedBlackTree$$isRedTree_tree3 != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree3.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                RedBlackTree.Tree tree4 = tr.scala$collection$immutable$RedBlackTree$Tree$$_left;
                if (tree4 == null) {
                    throw null;
                }
                RedBlackTree.Tree tree5 = tr.scala$collection$immutable$RedBlackTree$Tree$$_left;
                if (tree5 == null) {
                    throw null;
                }
                return tr.scala$collection$immutable$RedBlackTree$Tree$$_left.withLeftRight(tree.blackWithLeftRight(tl, tree4.scala$collection$immutable$RedBlackTree$Tree$$_left), tr.blackWithLeftRight(tree5.scala$collection$immutable$RedBlackTree$Tree$$_right, tr.scala$collection$immutable$RedBlackTree$Tree$$_right));
            }
            return tree.blackWithLeftRight(tl, tr);
        }
        return tree.blackWithLeftRight(tl, tr);
    }

    private <A, B> RedBlackTree.Tree<A, B> balLeft(RedBlackTree.Tree<A, B> tree, RedBlackTree.Tree<A, B> tl, RedBlackTree.Tree<A, B> tr) {
        if (tl != null && tl.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            return tree.redWithLeftRight(tl.black(), tr);
        }
        if (tr != null && tr.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
            return this.balance(tree, tl, tr.red());
        }
        if (tr != null && tr.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            if (tr == null) {
                throw null;
            }
            Object var4_4 = null;
            RedBlackTree.Tree isBlackTree_tree = tr.scala$collection$immutable$RedBlackTree$Tree$$_left;
            if (isBlackTree_tree != null && isBlackTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
                RedBlackTree.Tree tree2 = tr.scala$collection$immutable$RedBlackTree$Tree$$_left;
                if (tree2 == null) {
                    throw null;
                }
                RedBlackTree.Tree tree3 = tr.scala$collection$immutable$RedBlackTree$Tree$$_left;
                if (tree3 == null) {
                    throw null;
                }
                return tr.scala$collection$immutable$RedBlackTree$Tree$$_left.redWithLeftRight(tree.blackWithLeftRight(tl, tree2.scala$collection$immutable$RedBlackTree$Tree$$_left), this.balance(tr, tree3.scala$collection$immutable$RedBlackTree$Tree$$_right, tr.scala$collection$immutable$RedBlackTree$Tree$$_right.red()));
            }
        }
        String error_message = "Defect: invariance violation";
        throw new RuntimeException(error_message);
    }

    private <A, B> RedBlackTree.Tree<A, B> balRight(RedBlackTree.Tree<A, B> tree, RedBlackTree.Tree<A, B> tl, RedBlackTree.Tree<A, B> tr) {
        if (tr != null && tr.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            return tree.redWithLeftRight(tl, tr.black());
        }
        if (tl != null && tl.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
            return this.balance(tree, tl.red(), tr);
        }
        if (tl != null && tl.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            if (tl == null) {
                throw null;
            }
            Object var4_4 = null;
            RedBlackTree.Tree isBlackTree_tree = tl.scala$collection$immutable$RedBlackTree$Tree$$_right;
            if (isBlackTree_tree != null && isBlackTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
                RedBlackTree.Tree tree2 = tl.scala$collection$immutable$RedBlackTree$Tree$$_right;
                if (tree2 == null) {
                    throw null;
                }
                RedBlackTree.Tree tree3 = tl.scala$collection$immutable$RedBlackTree$Tree$$_right;
                if (tree3 == null) {
                    throw null;
                }
                return tl.scala$collection$immutable$RedBlackTree$Tree$$_right.redWithLeftRight(this.balance(tl, tl.scala$collection$immutable$RedBlackTree$Tree$$_left.red(), tree2.scala$collection$immutable$RedBlackTree$Tree$$_left), tree.blackWithLeftRight(tree3.scala$collection$immutable$RedBlackTree$Tree$$_right, tr));
            }
        }
        String error_message = "Defect: invariance violation";
        throw new RuntimeException(error_message);
    }

    private <A, B> RedBlackTree.Tree<A, B> append(RedBlackTree.Tree<A, B> tl, RedBlackTree.Tree<A, B> tr) {
        if (tl == null) {
            return tr;
        }
        if (tr == null) {
            return tl;
        }
        if (tl.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            if (tr.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                RedBlackTree.Tree<A, ?> bc = this.append(tl.scala$collection$immutable$RedBlackTree$Tree$$_right, tr.scala$collection$immutable$RedBlackTree$Tree$$_left);
                if (bc != null && bc.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                    if (bc == null) {
                        throw null;
                    }
                    return bc.withLeftRight(tl.withRight(bc.scala$collection$immutable$RedBlackTree$Tree$$_left), tr.withLeft(bc.scala$collection$immutable$RedBlackTree$Tree$$_right));
                }
                return tl.withRight(tr.withLeft(bc));
            }
            return tl.withRight(this.append(tl.scala$collection$immutable$RedBlackTree$Tree$$_right, tr));
        }
        if (tr.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
            RedBlackTree.Tree<A, ?> bc = this.append(tl.scala$collection$immutable$RedBlackTree$Tree$$_right, tr.scala$collection$immutable$RedBlackTree$Tree$$_left);
            if (bc != null && bc.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                if (bc == null) {
                    throw null;
                }
                return bc.withLeftRight(tl.withRight(bc.scala$collection$immutable$RedBlackTree$Tree$$_left), tr.withLeft(bc.scala$collection$immutable$RedBlackTree$Tree$$_right));
            }
            return this.balLeft(tl, tl.scala$collection$immutable$RedBlackTree$Tree$$_left, tr.withLeft(bc));
        }
        return tr.withLeft(this.append(tl, tr.scala$collection$immutable$RedBlackTree$Tree$$_left));
    }

    public <A, B> RedBlackTree.Tree<A, B> union(RedBlackTree.Tree<A, B> t1, RedBlackTree.Tree<A, B> t2, Ordering<A> ordering) {
        return this.blacken(this._union(t1, t2, ordering));
    }

    public <A, B> RedBlackTree.Tree<A, B> intersect(RedBlackTree.Tree<A, B> t1, RedBlackTree.Tree<A, B> t2, Ordering<A> ordering) {
        return this.blacken(this._intersect(t1, t2, ordering));
    }

    public <A, B> RedBlackTree.Tree<A, B> difference(RedBlackTree.Tree<A, B> t1, RedBlackTree.Tree<A, ?> t2, Ordering<A> ordering) {
        return this.blacken(this._difference(t1, t2, ordering));
    }

    private int rank(RedBlackTree.Tree<?, ?> t2, int bh) {
        if (t2 == null) {
            return 0;
        }
        if (t2.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
            return 2 * (bh - 1);
        }
        return 2 * bh - 1;
    }

    private <A, B> RedBlackTree.Tree<A, B> joinRight(RedBlackTree.Tree<A, B> tl, A k, B v, RedBlackTree.Tree<A, B> tr, int bhtl, int rtr) {
        int bhtlr;
        if ((tl == null ? 0 : (tl.scala$collection$immutable$RedBlackTree$Tree$$_count < 0 ? 2 * (bhtl - 1) : 2 * bhtl - 1)) == rtr / 2 * 2) {
            return this.RedTree(k, v, tl, tr);
        }
        boolean tlBlack = tl != null && tl.scala$collection$immutable$RedBlackTree$Tree$$_count < 0;
        int n = bhtlr = tlBlack ? bhtl - 1 : bhtl;
        if (tl == null) {
            throw null;
        }
        RedBlackTree.Tree ttr = this.joinRight(tl.scala$collection$immutable$RedBlackTree$Tree$$_right, k, v, tr, bhtlr, rtr);
        if (tlBlack && ttr != null && ttr.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            if (ttr == null) {
                throw null;
            }
            Object var10_10 = null;
            RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree = ttr.scala$collection$immutable$RedBlackTree$Tree$$_right;
            if (scala$collection$immutable$RedBlackTree$$isRedTree_tree != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                return this.RedTree(ttr.scala$collection$immutable$RedBlackTree$Tree$$_key, ttr.scala$collection$immutable$RedBlackTree$Tree$$_value, this.BlackTree(tl.scala$collection$immutable$RedBlackTree$Tree$$_key, tl.scala$collection$immutable$RedBlackTree$Tree$$_value, tl.scala$collection$immutable$RedBlackTree$Tree$$_left, ttr.scala$collection$immutable$RedBlackTree$Tree$$_left), ttr.scala$collection$immutable$RedBlackTree$Tree$$_right.black());
            }
        }
        return this.mkTree(tlBlack, tl.scala$collection$immutable$RedBlackTree$Tree$$_key, tl.scala$collection$immutable$RedBlackTree$Tree$$_value, tl.scala$collection$immutable$RedBlackTree$Tree$$_left, ttr);
    }

    private <A, B> RedBlackTree.Tree<A, B> joinLeft(RedBlackTree.Tree<A, B> tl, A k, B v, RedBlackTree.Tree<A, B> tr, int rtl, int bhtr) {
        int bhtrl;
        if ((tr == null ? 0 : (tr.scala$collection$immutable$RedBlackTree$Tree$$_count < 0 ? 2 * (bhtr - 1) : 2 * bhtr - 1)) == rtl / 2 * 2) {
            return this.RedTree(k, v, tl, tr);
        }
        boolean trBlack = tr != null && tr.scala$collection$immutable$RedBlackTree$Tree$$_count < 0;
        int n = bhtrl = trBlack ? bhtr - 1 : bhtr;
        if (tr == null) {
            throw null;
        }
        RedBlackTree.Tree<A, B> ttl = this.joinLeft(tl, k, v, tr.scala$collection$immutable$RedBlackTree$Tree$$_left, rtl, bhtrl);
        if (trBlack && ttl != null && ttl.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
            if (ttl == null) {
                throw null;
            }
            Object var10_10 = null;
            RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree = ttl.scala$collection$immutable$RedBlackTree$Tree$$_left;
            if (scala$collection$immutable$RedBlackTree$$isRedTree_tree != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                return this.RedTree(ttl.scala$collection$immutable$RedBlackTree$Tree$$_key, ttl.scala$collection$immutable$RedBlackTree$Tree$$_value, ttl.scala$collection$immutable$RedBlackTree$Tree$$_left.black(), this.BlackTree(tr.scala$collection$immutable$RedBlackTree$Tree$$_key, tr.scala$collection$immutable$RedBlackTree$Tree$$_value, ttl.scala$collection$immutable$RedBlackTree$Tree$$_right, tr.scala$collection$immutable$RedBlackTree$Tree$$_right));
            }
        }
        return this.mkTree(trBlack, tr.scala$collection$immutable$RedBlackTree$Tree$$_key, tr.scala$collection$immutable$RedBlackTree$Tree$$_value, ttl, tr.scala$collection$immutable$RedBlackTree$Tree$$_right);
    }

    public <A, B> RedBlackTree.Tree<A, B> scala$collection$immutable$RedBlackTree$$join(RedBlackTree.Tree<A, B> tl, A k, B v, RedBlackTree.Tree<A, B> tr) {
        int bhtr;
        int bhtl = this.h$1(tl, 0);
        if (bhtl > (bhtr = this.h$1(tr, 0))) {
            RedBlackTree.Tree<A, B> tt = this.joinRight(tl, k, v, tr, bhtl, tr == null ? 0 : (tr.scala$collection$immutable$RedBlackTree$Tree$$_count < 0 ? 2 * (bhtr - 1) : 2 * bhtr - 1));
            if (tt != null && tt.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                if (tt == null) {
                    throw null;
                }
                Object var9_8 = null;
                RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree = tt.scala$collection$immutable$RedBlackTree$Tree$$_right;
                if (scala$collection$immutable$RedBlackTree$$isRedTree_tree != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                    return tt.black();
                }
            }
            return tt;
        }
        if (bhtr > bhtl) {
            RedBlackTree.Tree<A, B> tt = this.joinLeft(tl, k, v, tr, tl == null ? 0 : (tl.scala$collection$immutable$RedBlackTree$Tree$$_count < 0 ? 2 * (bhtl - 1) : 2 * bhtl - 1), bhtr);
            if (tt != null && tt.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                if (tt == null) {
                    throw null;
                }
                Object var10_10 = null;
                RedBlackTree.Tree scala$collection$immutable$RedBlackTree$$isRedTree_tree = tt.scala$collection$immutable$RedBlackTree$Tree$$_left;
                if (scala$collection$immutable$RedBlackTree$$isRedTree_tree != null && scala$collection$immutable$RedBlackTree$$isRedTree_tree.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0) {
                    return tt.black();
                }
            }
            return tt;
        }
        return this.mkTree(tl != null && tl.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0 || tr != null && tr.scala$collection$immutable$RedBlackTree$Tree$$_count >= 0, k, v, tl, tr);
    }

    /*
     * WARNING - void declaration
     */
    private <A, B> Tuple4<RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>, A> split(RedBlackTree.Tree<A, B> t2, A k2, Ordering<A> ordering) {
        void var14_14;
        void var13_13;
        void var12_12;
        void var11_11;
        if (t2 == null) {
            return new Tuple4<Object, Object, Object, A>(null, null, null, k2);
        }
        int cmp = ordering.compare(k2, t2.scala$collection$immutable$RedBlackTree$Tree$$_key);
        if (cmp == 0) {
            return new Tuple4(t2.scala$collection$immutable$RedBlackTree$Tree$$_left, t2, t2.scala$collection$immutable$RedBlackTree$Tree$$_right, t2.scala$collection$immutable$RedBlackTree$Tree$$_key);
        }
        if (cmp < 0) {
            void var9_9;
            void var8_8;
            void var7_7;
            void var6_6;
            Tuple4 tuple4 = this.split(t2.scala$collection$immutable$RedBlackTree$Tree$$_left, k2, ordering);
            if (tuple4 == null) {
                throw new MatchError((Object)null);
            }
            RedBlackTree.Tree ll = tuple4._1();
            RedBlackTree.Tree b = tuple4._2();
            RedBlackTree.Tree lr = tuple4._3();
            Object k1 = tuple4._4();
            return new Tuple4(var6_6, var7_7, this.scala$collection$immutable$RedBlackTree$$join((RedBlackTree.Tree<A, B>)var8_8, t2.scala$collection$immutable$RedBlackTree$Tree$$_key, (B)t2.scala$collection$immutable$RedBlackTree$Tree$$_value, t2.scala$collection$immutable$RedBlackTree$Tree$$_right), var9_9);
        }
        Tuple4 tuple4 = this.split(t2.scala$collection$immutable$RedBlackTree$Tree$$_right, k2, ordering);
        if (tuple4 == null) {
            throw new MatchError((Object)null);
        }
        RedBlackTree.Tree rl = tuple4._1();
        RedBlackTree.Tree b = tuple4._2();
        RedBlackTree.Tree rr = tuple4._3();
        Object k1 = tuple4._4();
        return new Tuple4(this.scala$collection$immutable$RedBlackTree$$join(t2.scala$collection$immutable$RedBlackTree$Tree$$_left, t2.scala$collection$immutable$RedBlackTree$Tree$$_key, (B)t2.scala$collection$immutable$RedBlackTree$Tree$$_value, (RedBlackTree.Tree<A, B>)var11_11), var12_12, var13_13, var14_14);
    }

    /*
     * WARNING - void declaration
     */
    private <A, B> Tuple3<RedBlackTree.Tree<A, B>, A, B> splitLast(RedBlackTree.Tree<A, B> t2) {
        void var5_5;
        void var4_4;
        void var3_3;
        if (t2 == null) {
            throw null;
        }
        if (t2.scala$collection$immutable$RedBlackTree$Tree$$_right == null) {
            return new Tuple3(t2.scala$collection$immutable$RedBlackTree$Tree$$_left, t2.scala$collection$immutable$RedBlackTree$Tree$$_key, t2.scala$collection$immutable$RedBlackTree$Tree$$_value);
        }
        Tuple3 tuple3 = this.splitLast(t2.scala$collection$immutable$RedBlackTree$Tree$$_right);
        if (tuple3 == null) {
            throw new MatchError((Object)null);
        }
        RedBlackTree.Tree tt = tuple3._1();
        Object kk = tuple3._2();
        Object vv = tuple3._3();
        return new Tuple3(this.scala$collection$immutable$RedBlackTree$$join(t2.scala$collection$immutable$RedBlackTree$Tree$$_left, t2.scala$collection$immutable$RedBlackTree$Tree$$_key, (B)t2.scala$collection$immutable$RedBlackTree$Tree$$_value, (RedBlackTree.Tree<A, B>)var3_3), var4_4, var5_5);
    }

    /*
     * WARNING - void declaration
     */
    public <A, B> RedBlackTree.Tree<A, B> scala$collection$immutable$RedBlackTree$$join2(RedBlackTree.Tree<A, B> tl, RedBlackTree.Tree<A, B> tr) {
        void var6_6;
        void var5_5;
        void var4_4;
        if (tl == null) {
            return tr;
        }
        if (tr == null) {
            return tl;
        }
        Tuple3<RedBlackTree.Tree<A, B>, A, B> tuple3 = this.splitLast(tl);
        if (tuple3 == null) {
            throw new MatchError((Object)null);
        }
        RedBlackTree.Tree<A, B> ttl = tuple3._1();
        A k = tuple3._2();
        B v = tuple3._3();
        return this.scala$collection$immutable$RedBlackTree$$join((RedBlackTree.Tree<A, B>)var4_4, var5_5, var6_6, tr);
    }

    /*
     * WARNING - void declaration
     */
    private <A, B> RedBlackTree.Tree<A, B> _union(RedBlackTree.Tree<A, B> t1, RedBlackTree.Tree<A, B> t2, Ordering<A> ordering) {
        void var7_7;
        void var6_6;
        void var5_5;
        if (t1 == null || t1 == t2) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        Tuple4<RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>, A> tuple4 = this.split(t1, t2.scala$collection$immutable$RedBlackTree$Tree$$_key, ordering);
        if (tuple4 == null) {
            throw new MatchError((Object)null);
        }
        RedBlackTree.Tree<A, B> l1 = tuple4._1();
        RedBlackTree.Tree<A, B> r1 = tuple4._3();
        A k1 = tuple4._4();
        RedBlackTree.Tree tl = this._union((RedBlackTree.Tree<A, B>)var5_5, t2.scala$collection$immutable$RedBlackTree$Tree$$_left, ordering);
        RedBlackTree.Tree tr = this._union((RedBlackTree.Tree<A, B>)var6_6, t2.scala$collection$immutable$RedBlackTree$Tree$$_right, ordering);
        return this.scala$collection$immutable$RedBlackTree$$join(tl, var7_7, t2.scala$collection$immutable$RedBlackTree$Tree$$_value, tr);
    }

    /*
     * WARNING - void declaration
     */
    private <A, B> RedBlackTree.Tree<A, B> _intersect(RedBlackTree.Tree<A, B> t1, RedBlackTree.Tree<A, B> t2, Ordering<A> ordering) {
        void var6_6;
        void var7_7;
        void var5_5;
        if (t1 == null || t2 == null) {
            return null;
        }
        if (t1 == t2) {
            return t1;
        }
        Tuple4<RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>, A> tuple4 = this.split(t1, t2.scala$collection$immutable$RedBlackTree$Tree$$_key, ordering);
        if (tuple4 == null) {
            throw new MatchError((Object)null);
        }
        RedBlackTree.Tree<A, B> l1 = tuple4._1();
        RedBlackTree.Tree<A, B> b = tuple4._2();
        RedBlackTree.Tree<A, B> r1 = tuple4._3();
        A k1 = tuple4._4();
        RedBlackTree.Tree tl = this._intersect((RedBlackTree.Tree<A, B>)var5_5, t2.scala$collection$immutable$RedBlackTree$Tree$$_left, ordering);
        RedBlackTree.Tree tr = this._intersect((RedBlackTree.Tree<A, B>)var7_7, t2.scala$collection$immutable$RedBlackTree$Tree$$_right, ordering);
        if (var6_6 != null) {
            void var8_8;
            return this.scala$collection$immutable$RedBlackTree$$join(tl, var8_8, t2.scala$collection$immutable$RedBlackTree$Tree$$_value, tr);
        }
        return this.scala$collection$immutable$RedBlackTree$$join2(tl, tr);
    }

    /*
     * WARNING - void declaration
     */
    private <A, B> RedBlackTree.Tree<A, B> _difference(RedBlackTree.Tree<A, B> t1, RedBlackTree.Tree<A, B> t2, Ordering<A> ordering) {
        void var6_6;
        void var5_5;
        if (t1 == null || t2 == null) {
            return t1;
        }
        if (t1 == t2) {
            return null;
        }
        Tuple4<RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>, RedBlackTree.Tree<A, B>, A> tuple4 = this.split(t1, t2.scala$collection$immutable$RedBlackTree$Tree$$_key, ordering);
        if (tuple4 == null) {
            throw new MatchError((Object)null);
        }
        RedBlackTree.Tree<A, B> l1 = tuple4._1();
        RedBlackTree.Tree<A, B> r1 = tuple4._3();
        tuple4._4();
        RedBlackTree.Tree tl = this._difference((RedBlackTree.Tree<A, B>)var5_5, t2.scala$collection$immutable$RedBlackTree$Tree$$_left, ordering);
        RedBlackTree.Tree tr = this._difference((RedBlackTree.Tree<A, B>)var6_6, t2.scala$collection$immutable$RedBlackTree$Tree$$_right, ordering);
        return this.scala$collection$immutable$RedBlackTree$$join2(tl, tr);
    }

    private final RedBlackTree.Tree _tail$1(RedBlackTree.Tree tree) {
        if (tree == null) {
            throw new NoSuchElementException("empty tree");
        }
        RedBlackTree.Tree tl = tree.scala$collection$immutable$RedBlackTree$Tree$$_left;
        if (tl == null) {
            return tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        }
        if (tl.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
            return this.balLeft(tree, this._tail$1(tl), tree.scala$collection$immutable$RedBlackTree$Tree$$_right);
        }
        return tree.redWithLeft(this._tail$1(tree.scala$collection$immutable$RedBlackTree$Tree$$_left));
    }

    private final RedBlackTree.Tree _init$1(RedBlackTree.Tree tree) {
        if (tree == null) {
            throw new NoSuchElementException("empty tree");
        }
        RedBlackTree.Tree tr = tree.scala$collection$immutable$RedBlackTree$Tree$$_right;
        if (tr == null) {
            return tree.scala$collection$immutable$RedBlackTree$Tree$$_left;
        }
        if (tr.scala$collection$immutable$RedBlackTree$Tree$$_count < 0) {
            return this.balRight(tree, tree.scala$collection$immutable$RedBlackTree$Tree$$_left, this._init$1(tr));
        }
        return tree.redWithRight(this._init$1(tr));
    }

    private final RedBlackTree.Tree f$1(int level, int size, int maxUsedDepth$1, Iterator xs$1) {
        switch (size) {
            case 0: {
                return null;
            }
            case 1: {
                return this.mkTree(level != maxUsedDepth$1 || level == 1, xs$1.next(), null, null, null);
            }
        }
        int leftSize = (size - 1) / 2;
        RedBlackTree.Tree left = this.f$1(level + 1, leftSize, maxUsedDepth$1, xs$1);
        Object x = xs$1.next();
        RedBlackTree.Tree right = this.f$1(level + 1, size - 1 - leftSize, maxUsedDepth$1, xs$1);
        return this.BlackTree(x, null, left, right);
    }

    /*
     * WARNING - void declaration
     */
    private final RedBlackTree.Tree f$2(int level, int size, Iterator xs$2, int maxUsedDepth$2) {
        void var12_12;
        void var11_11;
        switch (size) {
            case 0: {
                return null;
            }
            case 1: {
                void var7_7;
                void var6_6;
                Tuple2 tuple2 = (Tuple2)xs$2.next();
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                Object k = tuple2._1();
                Object v = tuple2._2();
                return this.mkTree(level != maxUsedDepth$2 || level == 1, var6_6, var7_7, null, null);
            }
        }
        int leftSize = (size - 1) / 2;
        RedBlackTree.Tree left = this.f$2(level + 1, leftSize, xs$2, maxUsedDepth$2);
        Tuple2 tuple2 = (Tuple2)xs$2.next();
        if (tuple2 == null) {
            throw new MatchError((Object)null);
        }
        Object k = tuple2._1();
        Object v = tuple2._2();
        RedBlackTree.Tree right = this.f$2(level + 1, size - 1 - leftSize, xs$2, maxUsedDepth$2);
        return this.BlackTree(var11_11, var12_12, left, right);
    }

    private final RedBlackTree.Tree fk$1(RedBlackTree.Tree t2, Function2 f$3) {
        RedBlackTree.Tree r2;
        if (t2 == null) {
            throw null;
        }
        Object k = t2.scala$collection$immutable$RedBlackTree$Tree$$_key;
        Object v = t2.scala$collection$immutable$RedBlackTree$Tree$$_value;
        RedBlackTree.Tree l = t2.scala$collection$immutable$RedBlackTree$Tree$$_left;
        RedBlackTree.Tree r = t2.scala$collection$immutable$RedBlackTree$Tree$$_right;
        RedBlackTree.Tree l2 = l == null ? null : this.fk$1(l, f$3);
        boolean keep = BoxesRunTime.unboxToBoolean(f$3.apply(k, v));
        RedBlackTree.Tree tree = r2 = r == null ? null : this.fk$1(r, f$3);
        if (!keep) {
            return this.scala$collection$immutable$RedBlackTree$$join2(l2, r2);
        }
        if (l2 == l && r2 == r) {
            return t2;
        }
        return this.scala$collection$immutable$RedBlackTree$$join(l2, k, v, r2);
    }

    private static final /* synthetic */ RedBlackTree$partitioner$1$ partitioner$lzycompute$1(LazyRef partitioner$module$1, Function2 p$1) {
        synchronized (partitioner$module$1) {
            RedBlackTree$partitioner$1$ redBlackTree$partitioner$1$ = partitioner$module$1.initialized() ? (RedBlackTree$partitioner$1$)partitioner$module$1.value() : partitioner$module$1.initialize(new RedBlackTree$partitioner$1$(p$1));
            return redBlackTree$partitioner$1$;
        }
    }

    private final RedBlackTree$partitioner$1$ partitioner$2(LazyRef partitioner$module$1, Function2 p$1) {
        if (partitioner$module$1.initialized()) {
            return (RedBlackTree$partitioner$1$)partitioner$module$1.value();
        }
        synchronized (partitioner$module$1) {
            RedBlackTree$partitioner$1$ redBlackTree$partitioner$1$ = partitioner$module$1.initialized() ? (RedBlackTree$partitioner$1$)partitioner$module$1.value() : partitioner$module$1.initialize(new RedBlackTree$partitioner$1$(p$1));
            return redBlackTree$partitioner$1$;
        }
    }

    private final int h$1(RedBlackTree.Tree t2, int i) {
        while (t2 != null) {
            i = t2.scala$collection$immutable$RedBlackTree$Tree$$_count < 0 ? i + 1 : i;
            t2 = t2.scala$collection$immutable$RedBlackTree$Tree$$_left;
        }
        return i + 1;
    }

    private RedBlackTree$() {
    }
}

