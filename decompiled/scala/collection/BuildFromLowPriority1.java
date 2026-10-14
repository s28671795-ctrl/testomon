/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.BuildFrom;
import scala.collection.BuildFromLowPriority2;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SortedSet;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00154q\u0001B\u0003\u0011\u0002\u0007\u0005!\u0002C\u0003\u0014\u0001\u0011\u0005A\u0003C\u0003\u0019\u0001\u0011\r\u0011\u0004C\u0003R\u0001\u0011\r!KA\u000bCk&dGM\u0012:p[2{w\u000f\u0015:j_JLG/_\u0019\u000b\u0005\u00199\u0011AC2pY2,7\r^5p]*\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001Yq\u0002\u0005\u0002\r\u001b5\tq!\u0003\u0002\u000f\u000f\t1\u0011I\\=SK\u001a\u0004\"\u0001E\t\u000e\u0003\u0015I!AE\u0003\u0003+\t+\u0018\u000e\u001c3Ge>lGj\\<Qe&|'/\u001b;ze\u00051A%\u001b8ji\u0012\"\u0012!\u0006\t\u0003\u0019YI!aF\u0004\u0003\tUs\u0017\u000e^\u0001\u0016EVLG\u000e\u001a$s_6\u001cvN\u001d;fIN+Go\u00149t+\u0011Q\u0002%\u0010!\u0015\u0005m\u0019\u0005#\u0002\t\u001d=}\u0012\u0015BA\u000f\u0006\u0005%\u0011U/\u001b7e\rJ|W\u000eE\u0002 Aqb\u0001\u0001B\u0003\"\u0005\t\u0007!E\u0001\u0002D\u0007V\u00111EL\t\u0003I\u001d\u0002\"\u0001D\u0013\n\u0005\u0019:!a\u0002(pi\"Lgn\u001a\n\u0004Q)\"d\u0001B\u0015\u0001\u0001\u001d\u0012A\u0002\u0010:fM&tW-\\3oiz\u00022\u0001E\u0016.\u0013\taSAA\u0005T_J$X\rZ*fiB\u0011qD\f\u0003\u0006_\u0001\u0012\r\u0001\r\u0002\u00021F\u0011A%\r\t\u0003\u0019IJ!aM\u0004\u0003\u0007\u0005s\u0017\u0010\r\u00026uA)\u0001CN\u00179s%\u0011q'\u0002\u0002\r'>\u0014H/\u001a3TKR|\u0005o\u001d\t\u0003?\u0001\u0002\"a\b\u001e\u0005\u0013m\u0002\u0013\u0011!A\u0001\u0006\u0003\u0001$\u0001B0%cE\u0002\"aH\u001f\u0005\u000by\u0012!\u0019\u0001\u0019\u0003\u0005\u0005\u0003\u0004CA\u0010A\t\u0015\t%A1\u00011\u0005\u0005\t\u0005cA\u0010!\u007f!9AIAA\u0001\u0002\b)\u0015AC3wS\u0012,gnY3%gA\u0019aIT \u000f\u0005\u001dceB\u0001%L\u001b\u0005I%B\u0001&\n\u0003\u0019a$o\\8u}%\t\u0001\"\u0003\u0002N\u000f\u00059\u0001/Y2lC\u001e,\u0017BA(Q\u0005!y%\u000fZ3sS:<'BA'\b\u0003i1\u0017\r\u001c7cC\u000e\\7\u000b\u001e:j]\u001e\u001c\u0015M\u001c\"vS2$gI]8n+\t\u0019f,F\u0001U!\u0015\u0001B$V/`!\t1&L\u0004\u0002X1B\u0011\u0001jB\u0005\u00033\u001e\ta\u0001\u0015:fI\u00164\u0017BA.]\u0005\u0019\u0019FO]5oO*\u0011\u0011l\u0002\t\u0003?y#Q!Q\u0002C\u0002A\u00022\u0001Y2^\u001b\u0005\t'B\u00012\u0006\u0003%IW.\\;uC\ndW-\u0003\u0002eC\nQ\u0011J\u001c3fq\u0016$7+Z9")
public interface BuildFromLowPriority1
extends BuildFromLowPriority2 {
    public static /* synthetic */ BuildFrom buildFromSortedSetOps$(BuildFromLowPriority1 $this, Ordering evidence$3) {
        return $this.buildFromSortedSetOps(evidence$3);
    }

    default public <CC extends SortedSet<Object>, A0, A> BuildFrom<CC, A, CC> buildFromSortedSetOps(Ordering<A> evidence$3) {
        return new BuildFrom<CC, A, CC>(null, evidence$3){
            private final Ordering evidence$3$1;

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public Builder<A, CC> newBuilder(CC from) {
                return from.sortedIterableFactory().newBuilder(this.evidence$3$1);
            }

            public CC fromSpecific(CC from, IterableOnce<A> it) {
                return (CC)((SortedSet)from.sortedIterableFactory().from(it, this.evidence$3$1));
            }
            {
                this.evidence$3$1 = evidence$3$1;
            }
        };
    }

    public static /* synthetic */ BuildFrom fallbackStringCanBuildFrom$(BuildFromLowPriority1 $this) {
        return $this.fallbackStringCanBuildFrom();
    }

    default public <A> BuildFrom<String, A, IndexedSeq<A>> fallbackStringCanBuildFrom() {
        return new BuildFrom<String, A, IndexedSeq<A>>(null){

            public Builder apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public IndexedSeq<A> fromSpecific(String from, IterableOnce<A> it) {
                return IndexedSeq$.MODULE$.from((IterableOnce)it);
            }

            public Builder<A, IndexedSeq<A>> newBuilder(String from) {
                return IndexedSeq$.MODULE$.newBuilder();
            }
        };
    }

    public static void $init$(BuildFromLowPriority1 $this) {
    }
}

