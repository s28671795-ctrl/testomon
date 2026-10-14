/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function0;
import scala.Function1;
import scala.collection.ClassTagSeqFactory;
import scala.collection.Seq;
import scala.collection.SeqOps;
import scala.collection.mutable.Builder;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3q\u0001B\u0003\u0011\u0002\u0007\u0005!\u0002C\u0003,\u0001\u0011\u0005A\u0006C\u00031\u0001\u0011\u0005\u0013\u0007C\u0003K\u0001\u0011\u00053JA\u0011TiJL7\r^(qi&l\u0017N_3e\u00072\f7o\u001d+bON+\u0017OR1di>\u0014\u0018P\u0003\u0002\u0007\u000f\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003!\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\f-M\u0019\u0001\u0001\u0004\t\u0011\u00055qQ\"A\u0004\n\u0005=9!AB!osJ+g\rE\u0002\u0012%Qi\u0011!B\u0005\u0003'\u0015\u0011!c\u00117bgN$\u0016mZ*fc\u001a\u000b7\r^8ssB\u0011QC\u0006\u0007\u0001\t\u00199\u0002\u0001\"b\u00011\t\u00111iQ\u000b\u00033\u0005\n\"AG\u000f\u0011\u00055Y\u0012B\u0001\u000f\b\u0005\u001dqu\u000e\u001e5j]\u001e\u0004R!\u0005\u0010!O)J!aH\u0003\u0003\rM+\u0017o\u00149t!\t)\u0012\u0005B\u0003#-\t\u00071EA\u0001B#\tQB\u0005\u0005\u0002\u000eK%\u0011ae\u0002\u0002\u0004\u0003:L\bCA\t)\u0013\tISAA\u0002TKF\u00042!\u0005\u0015!\u0003\u0019!\u0013N\\5uIQ\tQ\u0006\u0005\u0002\u000e]%\u0011qf\u0002\u0002\u0005+:LG/\u0001\u0003gS2dWC\u0001\u001a8)\t\u0019T\t\u0006\u00025\u0001R\u0011Q\u0007\u000f\t\u0004+Y1\u0004CA\u000b8\t\u0015\u0011#A1\u0001$\u0011\u001dI$!!AA\u0004i\n1\"\u001a<jI\u0016t7-\u001a\u00134iA\u00191H\u0010\u001c\u000e\u0003qR!!P\u0004\u0002\u000fI,g\r\\3di&\u0011q\b\u0010\u0002\t\u00072\f7o\u001d+bO\"1\u0011I\u0001CA\u0002\t\u000bA!\u001a7f[B\u0019Qb\u0011\u001c\n\u0005\u0011;!\u0001\u0003\u001fcs:\fW.\u001a \t\u000b\u0019\u0013\u0001\u0019A$\u0002\u00039\u0004\"!\u0004%\n\u0005%;!aA%oi\u0006AA/\u00192vY\u0006$X-\u0006\u0002M#R\u0011QJ\u0017\u000b\u0003\u001dV#\"a\u0014*\u0011\u0007U1\u0002\u000b\u0005\u0002\u0016#\u0012)!e\u0001b\u0001G!91kAA\u0001\u0002\b!\u0016aC3wS\u0012,gnY3%gU\u00022a\u000f Q\u0011\u001516\u00011\u0001X\u0003\u00051\u0007\u0003B\u0007Y\u000fBK!!W\u0004\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\"\u0002$\u0004\u0001\u00049\u0005")
public interface StrictOptimizedClassTagSeqFactory<CC extends SeqOps<Object, Seq, Seq<Object>>>
extends ClassTagSeqFactory<CC> {
    public static /* synthetic */ SeqOps fill$(StrictOptimizedClassTagSeqFactory $this, int n, Function0 elem, ClassTag evidence$34) {
        return $this.fill(n, elem, evidence$34);
    }

    @Override
    default public <A> CC fill(int n, Function0<A> elem, ClassTag<A> evidence$34) {
        Builder b = this.newBuilder(evidence$34);
        b.sizeHint(n);
        for (int i = 0; i < n; ++i) {
            A $plus$eq_elem = elem.apply();
            b.addOne($plus$eq_elem);
            Object var6_6 = null;
        }
        return (CC)((SeqOps)b.result());
    }

    public static /* synthetic */ SeqOps tabulate$(StrictOptimizedClassTagSeqFactory $this, int n, Function1 f, ClassTag evidence$35) {
        return $this.tabulate(n, f, evidence$35);
    }

    @Override
    default public <A> CC tabulate(int n, Function1<Object, A> f, ClassTag<A> evidence$35) {
        Builder b = this.newBuilder(evidence$35);
        b.sizeHint(n);
        for (int i = 0; i < n; ++i) {
            A $plus$eq_elem = f.apply(i);
            b.addOne($plus$eq_elem);
            Object var6_6 = null;
        }
        return (CC)((SeqOps)b.result());
    }

    public static void $init$(StrictOptimizedClassTagSeqFactory $this) {
    }
}

