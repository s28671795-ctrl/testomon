/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.AbstractIndexedSeqView;
import scala.collection.IndexedSeq;
import scala.collection.IndexedSeqView;
import scala.collection.Iterator;
import scala.collection.Seq;
import scala.runtime.BoxesRunTime;

public final class SeqOps$ {
    public static final SeqOps$ MODULE$ = new SeqOps$();

    public <B> int scala$collection$SeqOps$$kmpSearch(Seq<B> S, int m0, int m1, Seq<B> W, int n0, int n1, boolean forward) {
        if (n1 == n0 + 1) {
            if (forward) {
                return SeqOps$.clipR$1(S.indexOf(W.apply(n0), m0), m1);
            }
            return SeqOps$.clipL$1(S.lastIndexOf(W.apply(n0), m1 - 1), m0 - 1);
        }
        if (m1 - m0 == n1 - n0) {
            if (S.iterator().slice(m0, m1).sameElements(W.iterator().slice(n0, n1))) {
                return m0;
            }
            return -1;
        }
        if (S instanceof IndexedSeq) {
            int delta;
            IndexedSeqView<B> Wopt = this.kmpOptimizeWord(W, n0, n1, forward);
            int[] T = this.kmpJumpTable(Wopt, n1 - n0);
            int i = 0;
            int m4 = 0;
            int zero = forward ? m0 : m1 - 1;
            int n = delta = forward ? 1 : -1;
            while (i + m4 < m1 - m0) {
                if (BoxesRunTime.equals(Wopt.apply(i), S.apply(zero + delta * (i + m4)))) {
                    if (++i != n1 - n0) continue;
                    if (forward) {
                        return m4 + m0;
                    }
                    return m1 - m4 - i;
                }
                int ti = T[i];
                m4 += i - ti;
                if (i <= 0) continue;
                i = ti;
            }
            return -1;
        }
        Iterator iter = S.iterator().drop(m0);
        IndexedSeqView<B> Wopt = this.kmpOptimizeWord(W, n0, n1, true);
        int[] T = this.kmpJumpTable(Wopt, n1 - n0);
        Object[] cache = new Object[n1 - n0];
        int largest = 0;
        int i = 0;
        int m5 = 0;
        int answer = -1;
        while (m5 + m0 + n1 - n0 <= m1) {
            while (i + m5 >= largest) {
                cache[largest % (n1 - n0)] = iter.next();
                ++largest;
            }
            if (BoxesRunTime.equals(Wopt.apply(i), cache[(i + m5) % (n1 - n0)])) {
                if (++i != n1 - n0) continue;
                if (forward) {
                    return m5 + m0;
                }
                answer = m5 + m0;
                int ti = T[--i];
                m5 += i - ti;
                if (i <= 0) continue;
                i = ti;
                continue;
            }
            int ti = T[i];
            m5 += i - ti;
            if (i <= 0) continue;
            i = ti;
        }
        return answer;
    }

    private <B> IndexedSeqView<B> kmpOptimizeWord(Seq<B> W, int n0, int n1, boolean forward) {
        if (W instanceof IndexedSeq) {
            IndexedSeq indexedSeq = (IndexedSeq)W;
            if (forward && n0 == 0 && n1 == W.length()) {
                return indexedSeq.view();
            }
            if (forward) {
                return new AbstractIndexedSeqView<B>(n1, n0, indexedSeq){
                    private final int length;
                    private final int n0$1;
                    private final IndexedSeq x2$1;

                    public int length() {
                        return this.length;
                    }

                    public B apply(int x) {
                        return (B)this.x2$1.apply(this.n0$1 + x);
                    }
                    {
                        this.n0$1 = n0$1;
                        this.x2$1 = x2$1;
                        this.length = n1$1 - n0$1;
                    }
                };
            }
            return new AbstractIndexedSeqView<B>(n1, n0, indexedSeq){
                private final int n1$1;
                private final int n0$1;
                private final IndexedSeq x2$1;

                public int length() {
                    return this.n1$1 - this.n0$1;
                }

                public B apply(int x) {
                    return (B)this.x2$1.apply(this.n1$1 - 1 - x);
                }
                {
                    this.n1$1 = n1$1;
                    this.n0$1 = n0$1;
                    this.x2$1 = x2$1;
                }
            };
        }
        return new AbstractIndexedSeqView<B>(n1, n0, forward, W){
            private final Object[] Warr;
            private final int delta;
            private final int done;
            private final Iterator<B> wit;
            private int i;
            private final int length;

            private Iterator<B> wit() {
                return this.wit;
            }

            private int i() {
                return this.i;
            }

            private void i_$eq(int x$1) {
                this.i = x$1;
            }

            public int length() {
                return this.length;
            }

            public B apply(int x) {
                return (B)this.Warr[x];
            }
            {
                this.Warr = new Object[n1$1 - n0$1];
                this.delta = forward$1 ? 1 : -1;
                this.done = forward$1 ? n1$1 - n0$1 : -1;
                this.wit = W$1.iterator().drop(n0$1);
                int n = this.i = forward$1 ? 0 : n1$1 - n0$1 - 1;
                while (this.i() != this.done) {
                    this.Warr[this.i()] = this.wit().next();
                    this.i_$eq(this.i() + this.delta);
                }
                this.length = n1$1 - n0$1;
            }
        };
    }

    private <B> int[] kmpJumpTable(IndexedSeqView<B> Wopt, int wlen) {
        int[] arr = new int[wlen];
        int pos = 2;
        int cnd = 0;
        arr[0] = -1;
        arr[1] = 0;
        while (pos < wlen) {
            if (BoxesRunTime.equals(Wopt.apply(pos - 1), Wopt.apply(cnd))) {
                arr[pos] = cnd + 1;
                ++pos;
                ++cnd;
                continue;
            }
            if (cnd > 0) {
                cnd = arr[cnd];
                continue;
            }
            arr[pos] = 0;
            ++pos;
        }
        return arr;
    }

    private static final int clipR$1(int x, int y) {
        if (x < y) {
            return x;
        }
        return -1;
    }

    private static final int clipL$1(int x, int y) {
        if (x > y) {
            return x;
        }
        return -1;
    }

    private SeqOps$() {
    }
}

