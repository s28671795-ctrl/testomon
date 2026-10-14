/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import scala.collection.concurrent.BasicNode;
import scala.collection.concurrent.CNode;
import scala.collection.concurrent.Gen;
import scala.collection.concurrent.INode;
import scala.collection.concurrent.LNode;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.SNode;
import scala.math.Equiv;

public final class CNode$ {
    public static final CNode$ MODULE$ = new CNode$();

    public <K, V> MainNode<K, V> dual(SNode<K, V> x, int xhc, SNode<K, V> y, int yhc, int lev, Gen gen, Equiv<K> equiv) {
        if (lev < 35) {
            int xidx = xhc >>> lev & 0x1F;
            int yidx = yhc >>> lev & 0x1F;
            int bmp = 1 << xidx | 1 << yidx;
            if (xidx == yidx) {
                INode subinode = new INode(gen, equiv);
                subinode.mainnode = this.dual(x, xhc, y, yhc, lev + 5, gen, equiv);
                return new CNode(bmp, new BasicNode[]{subinode}, gen);
            }
            if (xidx < yidx) {
                return new CNode(bmp, new BasicNode[]{x, y}, gen);
            }
            return new CNode(bmp, new BasicNode[]{y, x}, gen);
        }
        return new LNode<K, V>(x.k(), x.v(), y.k(), y.v(), equiv);
    }

    private CNode$() {
    }
}

