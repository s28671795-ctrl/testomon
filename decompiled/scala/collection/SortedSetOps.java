/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SetOps;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedOps;
import scala.collection.SortedSet;
import scala.collection.View;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t}gaB\u0011#!\u0003\r\ta\n\u0005\u0006%\u0002!\ta\u0015\u0005\u0006/\u00021\t\u0001\u0017\u0005\u00069\u00021\t!\u0018\u0005\u0006?\u00021\t\u0001\u0019\u0005\u0006M\u0002!\ta\u001a\u0005\u0006o\u0002!\t\u0001\u001f\u0005\u0006s\u0002!\t\u0001\u001f\u0005\u0006u\u0002!\ta\u001f\u0005\b\u0003\u0007\u0001A\u0011AA\u0003\u0011\u001d\tI\u0001\u0001C!\u0003\u0017Aq!a\r\u0001\t\u0003\n)\u0004C\u0004\u0002B\u0001!\t!a\u0011\t\u000f\u0005%\u0003\u0001\"\u0001\u0002L!9\u00111\u0010\u0001\u0005\u0002\u0005u\u0004bBAM\u0001\u0011\u0005\u00111\u0014\u0005\b\u0003\u001b\u0004A\u0011AAh\u00119\tY\u000f\u0001I\u0001\u0004\u0003\u0005I\u0011BAw\u0005\u0003AaBa\u0002\u0001!\u0003\r\t\u0011!C\u0005\u0005\u0013\u0011)bB\u0004\u0003\u0018\tB\tA!\u0007\u0007\r\u0005\u0012\u0003\u0012\u0001B\u000e\u0011\u001d\u0011i\u0002\u0006C\u0001\u0005?A!B!\t\u0015\u0005\u0004%)A\tB\u0012\u0011!\u00119\u0003\u0006Q\u0001\u000e\t\u0015\u0002B\u0003B\u0015)\t\u0007IQ\u0001\u0012\u0003,!A!q\u0006\u000b!\u0002\u001b\u0011iC\u0002\u0004\u00032Q\u0001!1\u0007\u0005\u000b\u0005'R\"\u0011!Q\u0001\n\tU\u0003B\u0003BA5\t\u0005\t\u0015!\u0003\u0003\u0004\"9!Q\u0004\u000e\u0005\u0002\t-\u0005bBA%5\u0011\u0005!\u0011\u0016\u0005\b\u0003wRB\u0011\u0001B`\u0011\u001d\u00119N\u0007C!\u00053\u0014AbU8si\u0016$7+\u001a;PaNT!a\t\u0013\u0002\u0015\r|G\u000e\\3di&|gNC\u0001&\u0003\u0015\u00198-\u00197b\u0007\u0001)B\u0001K\u001aF\u0001N!\u0001!K\u0017P!\tQ3&D\u0001%\u0013\taCE\u0001\u0004B]f\u0014VM\u001a\t\u0006]=\nDhP\u0007\u0002E%\u0011\u0001G\t\u0002\u0007'\u0016$x\n]:\u0011\u0005I\u001aD\u0002\u0001\u0003\u0006i\u0001\u0011\r!\u000e\u0002\u0002\u0003F\u0011a'\u000f\t\u0003U]J!\u0001\u000f\u0013\u0003\u000f9{G\u000f[5oOB\u0011!FO\u0005\u0003w\u0011\u00121!\u00118z!\tqS(\u0003\u0002?E\t\u00191+\u001a;\u0011\u0005I\u0002EAB!\u0001\t\u000b\u0007!IA\u0001D#\t14\tE\u0003/\u0001E\"u\b\u0005\u00023\u000b\u00121a\t\u0001CC\u0002\u001d\u0013!aQ\"\u0016\u0005!k\u0015C\u0001\u001cJ!\rq#\nT\u0005\u0003\u0017\n\u0012\u0011bU8si\u0016$7+\u001a;\u0011\u0005IjE!\u0002(F\u0005\u0004)$!\u0001-\u0011\t9\u0002\u0016gP\u0005\u0003#\n\u0012\u0011bU8si\u0016$w\n]:\u0002\r\u0011Jg.\u001b;%)\u0005!\u0006C\u0001\u0016V\u0013\t1FE\u0001\u0003V]&$\u0018!F:peR,G-\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u00023B\u0019aF\u0017#\n\u0005m\u0013#!F*peR,G-\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u0001\tk:\u001cxN\u001d;fIV\ta\fE\u0002/{E\nA\"\u001b;fe\u0006$xN\u001d$s_6$\"!\u00193\u0011\u00079\u0012\u0017'\u0003\u0002dE\tA\u0011\n^3sCR|'\u000fC\u0003f\t\u0001\u0007\u0011'A\u0003ti\u0006\u0014H/\u0001\tlKf\u001c\u0018\n^3sCR|'O\u0012:p[R\u0011\u0011\r\u001b\u0005\u0006K\u0016\u0001\r!\r\u0015\u0003\u000b)\u0004\"AK6\n\u00051$#AB5oY&tW\r\u000b\u0004\u0006]F\u0014H/\u001e\t\u0003U=L!\u0001\u001d\u0013\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003M\f1$V:fA\u0001LG/\u001a:bi>\u0014hI]8nA\u0002Jgn\u001d;fC\u0012t\u0013!B:j]\u000e,\u0017%\u0001<\u0002\rIr\u0013g\r\u00181\u0003!1\u0017N]:u\u0017\u0016LX#A\u0019\u0002\u000f1\f7\u000f^&fs\u0006AQ.\u001b8BMR,'\u000f\u0006\u0002}\u007fB\u0019!&`\u0019\n\u0005y$#AB(qi&|g\u000e\u0003\u0004\u0002\u0002!\u0001\r!M\u0001\u0004W\u0016L\u0018!C7bq\n+gm\u001c:f)\ra\u0018q\u0001\u0005\u0007\u0003\u0003I\u0001\u0019A\u0019\u0002\u00075Lg.\u0006\u0003\u0002\u000e\u00055BcA\u0019\u0002\u0010!9\u0011\u0011\u0003\u0006A\u0004\u0005M\u0011aA8sIB1\u0011QCA\u0013\u0003WqA!a\u0006\u0002\"9!\u0011\u0011DA\u0010\u001b\t\tYBC\u0002\u0002\u001e\u0019\na\u0001\u0010:p_Rt\u0014\"A\u0013\n\u0007\u0005\rB%A\u0004qC\u000e\\\u0017mZ3\n\t\u0005\u001d\u0012\u0011\u0006\u0002\t\u001fJ$WM]5oO*\u0019\u00111\u0005\u0013\u0011\u0007I\ni\u0003B\u0004\u00020)\u0011\r!!\r\u0003\u0003\t\u000b\"!M\u001d\u0002\u00075\f\u00070\u0006\u0003\u00028\u0005}BcA\u0019\u0002:!9\u0011\u0011C\u0006A\u0004\u0005m\u0002CBA\u000b\u0003K\ti\u0004E\u00023\u0003\u007f!q!a\f\f\u0005\u0004\t\t$A\u0004sC:<W\rV8\u0015\u0007}\n)\u0005\u0003\u0004\u0002H1\u0001\r!M\u0001\u0003i>\f1!\\1q+\u0011\ti%!\u0016\u0015\t\u0005=\u0013\u0011\u000f\u000b\u0005\u0003#\n9\u0006\u0005\u00033\u000b\u0006M\u0003c\u0001\u001a\u0002V\u00111\u0011qF\u0007C\u0002UBq!!\u0017\u000e\u0001\b\tY&\u0001\u0002fmB1\u0011QCA\u0013\u0003'B\u0003\"a\u0016\u0002`\u0005-\u0014Q\u000e\t\u0005\u0003C\n9'\u0004\u0002\u0002d)\u0019\u0011Q\r\u0013\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002j\u0005\r$\u0001E5na2L7-\u001b;O_R4u.\u001e8e\u0003\ri7oZ\u0011\u0003\u0003_\nqPT8!S6\u0004H.[2ji\u0002z%\u000fZ3sS:<7\fJ>C{v\u0003cm\\;oI\u0002\"x\u000e\t2vS2$\u0007%\u0019\u0011T_J$X\rZ*fin#3PQ?^]\u0001Jv.\u001e\u0011nCf\u0004s/\u00198uAQ|\u0007%\u001e9dCN$\b\u0005^8!C\u0002\u001aV\r^.%w\u0006kX\f\t4jeN$\bEY=!G\u0006dG.\u001b8hA\u0001,hn]8si\u0016$\u0007M\f\u0005\b\u0003gj\u0001\u0019AA;\u0003\u00051\u0007C\u0002\u0016\u0002xE\n\u0019&C\u0002\u0002z\u0011\u0012\u0011BR;oGRLwN\\\u0019\u0002\u000f\u0019d\u0017\r^'baV!\u0011qPAD)\u0011\t\t)a$\u0015\t\u0005\r\u0015\u0011\u0012\t\u0005e\u0015\u000b)\tE\u00023\u0003\u000f#a!a\f\u000f\u0005\u0004)\u0004bBA-\u001d\u0001\u000f\u00111\u0012\t\u0007\u0003+\t)#!\")\u0011\u0005%\u0015qLA6\u0003[Bq!a\u001d\u000f\u0001\u0004\t\t\n\u0005\u0004+\u0003o\n\u00141\u0013\t\u0006]\u0005U\u0015QQ\u0005\u0004\u0003/\u0013#\u0001D%uKJ\f'\r\\3P]\u000e,\u0017a\u0001>jaV!\u0011QTA^)\u0011\ty*a2\u0015\t\u0005\u0005\u0016Q\u0018\t\u0005e\u0015\u000b\u0019\u000bE\u0004+\u0003K\u000bI+!/\n\u0007\u0005\u001dFE\u0001\u0004UkBdWM\r\u0016\u0004c\u0005-6FAAW!\u0011\ty+!.\u000e\u0005\u0005E&\u0002BAZ\u0003G\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\n\t\u0005]\u0016\u0011\u0017\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007c\u0001\u001a\u0002<\u00121\u0011qF\bC\u0002UBq!!\u0017\u0010\u0001\b\ty\f\u0005\u0004\u0002\u0016\u0005\u0015\u00121\u0015\u0015\t\u0003{\u000by&a\u001b\u0002D\u0006\u0012\u0011QY\u0001\u0002\u00109{\u0007%[7qY&\u001c\u0017\u000e\u001e\u0011Pe\u0012,'/\u001b8h7\u0012Z()`/!M>,h\u000e\u001a\u0011u_\u0002\u0012W/\u001b7eA\u0005\u00043k\u001c:uK\u0012\u001cV\r^.)Im\fU\u0010\f\u0011%w\nk\u0018&\u0018\u0018!3>,\b%\\1zA]\fg\u000e\u001e\u0011u_\u0002*\boY1ti\u0002\"x\u000eI1!'\u0016$8\fJ>B{v\u0003c-\u001b:ti\u0002\u0012\u0017\u0010I2bY2Lgn\u001a\u0011ak:\u001cxN\u001d;fI\u0002t\u0003bBAe\u001f\u0001\u0007\u00111Z\u0001\u0005i\"\fG\u000fE\u0003/\u0003+\u000bI,A\u0004d_2dWm\u0019;\u0016\t\u0005E\u0017\u0011\u001c\u000b\u0005\u0003'\f\t\u000f\u0006\u0003\u0002V\u0006m\u0007\u0003\u0002\u001aF\u0003/\u00042AMAm\t\u0019\ty\u0003\u0005b\u0001k!9\u0011\u0011\f\tA\u0004\u0005u\u0007CBA\u000b\u0003K\t9\u000e\u000b\u0005\u0002\\\u0006}\u00131NA7\u0011\u001d\t\u0019\u000f\u0005a\u0001\u0003K\f!\u0001\u001d4\u0011\r)\n9/MAl\u0013\r\tI\u000f\n\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]\u0006I1/\u001e9fe\u0012j\u0017N\\\u000b\u0005\u0003_\fy\u0010F\u00022\u0003cDq!!\u0005\u0012\u0001\b\t\u0019\u0010\u0005\u0004\u0002v\u0006m\u0018Q`\u0007\u0003\u0003oT1!!?%\u0003\u0011i\u0017\r\u001e5\n\t\u0005\u001d\u0012q\u001f\t\u0004e\u0005}HaBA\u0018#\t\u0007\u0011\u0011G\u0005\u0005\u0003\u0013\u0011\u0019!C\u0002\u0003\u0006\t\u0012q\"\u0013;fe\u0006\u0014G.Z(oG\u0016|\u0005o]\u0001\ngV\u0004XM\u001d\u0013nCb,BAa\u0003\u0003\u0014Q\u0019\u0011G!\u0004\t\u000f\u0005E!\u0003q\u0001\u0003\u0010A1\u0011Q_A~\u0005#\u00012A\rB\n\t\u001d\tyC\u0005b\u0001\u0003cIA!a\r\u0003\u0004\u0005a1k\u001c:uK\u0012\u001cV\r^(qgB\u0011a\u0006F\n\u0003)%\na\u0001P5oSRtDC\u0001B\r\u0003\u0019y'\u000fZ'tOV\u0011!QE\b\u0003\u0003[\nqa\u001c:e\u001bN<\u0007%A\u0005{SB|%\u000fZ'tOV\u0011!QF\b\u0003\u0003\u0007\f!B_5q\u001fJ$Wj]4!\u0005)9\u0016\u000e\u001e5GS2$XM]\u000b\t\u0005k\u0011)E!\u0013\u0003bM\u0019!Da\u000e\u0011\u0011\te\"q\bB\"\u0005\u000fr1A\fB\u001e\u0013\r\u0011iDI\u0001\f\u0013R,'/\u00192mK>\u00038/\u0003\u0003\u00032\t\u0005#b\u0001B\u001fEA\u0019!G!\u0012\u0005\rQRBQ1\u00016!\r\u0011$\u0011\n\u0003\t\u0005\u0017RBQ1\u0001\u0003N\tQ\u0011\n^3sC\ndWmQ\"\u0016\u0007U\u0012y\u0005B\u0004\u0003R\t%#\u0019A\u001b\u0003\t}#C%M\u0001\u0005g\u0016dgM\u0005\u0004\u0003X\tm#1\u000f\u0004\u0007\u00053\"\u0002A!\u0016\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e 1\t\tu#q\u000e\t\t]\u0001\u0011\u0019Ea\u0018\u0003nA\u0019!G!\u0019\u0005\u000f\u0019SBQ1\u0001\u0003dU!!Q\rB6#\r1$q\r\t\u0005])\u0013I\u0007E\u00023\u0005W\"aA\u0014B1\u0005\u0004)\u0004c\u0001\u001a\u0003p\u0011Q!\u0011O\u000e\u0002\u0002\u0003\u0005)\u0011A\u001b\u0003\u0007}#\u0013\u0007\r\u0003\u0003v\tu\u0004#\u0003\u0018\u0003x\t\r#q\tB>\u0013\r\u0011IH\t\u0002\f\u0013R,'/\u00192mK>\u00038\u000fE\u00023\u0005{\"!Ba \u001c\u0003\u0003\u0005\tQ!\u00016\u0005\ryFEM\u0001\u0002aB9!&a\u001e\u0003D\t\u0015\u0005c\u0001\u0016\u0003\b&\u0019!\u0011\u0012\u0013\u0003\u000f\t{w\u000e\\3b]R1!Q\u0012BI\u0005O\u0003\u0012Ba$\u001b\u0005\u0007\u00129Ea\u0018\u000e\u0003QAqAa\u0015\u001e\u0001\u0004\u0011\u0019J\u0005\u0004\u0003\u0016\n]%q\u0014\u0004\u0007\u00053\"\u0002Aa%1\t\te%Q\u0014\t\t]\u0001\u0011\u0019Ea\u0018\u0003\u001cB\u0019!G!(\u0005\u0017\tE$\u0011SA\u0001\u0002\u0003\u0015\t!\u000e\u0019\u0005\u0005C\u0013)\u000bE\u0005/\u0005o\u0012\u0019Ea\u0012\u0003$B\u0019!G!*\u0005\u0017\t}$\u0011SA\u0001\u0002\u0003\u0015\t!\u000e\u0005\b\u0005\u0003k\u0002\u0019\u0001BB+\u0011\u0011YKa-\u0015\t\t5&1\u0018\u000b\u0005\u0005_\u0013)\fE\u00033\u0005C\u0012\t\fE\u00023\u0005g#a!a\f\u001f\u0005\u0004)\u0004\"\u0003B\\=\u0005\u0005\t9\u0001B]\u0003))g/\u001b3f]\u000e,G%\r\t\u0007\u0003+\t)C!-\t\u000f\u0005Md\u00041\u0001\u0003>B9!&a\u001e\u0003D\tEV\u0003\u0002Ba\u0005\u0013$BAa1\u0003RR!!Q\u0019Bf!\u0015\u0011$\u0011\rBd!\r\u0011$\u0011\u001a\u0003\u0007\u0003_y\"\u0019A\u001b\t\u0013\t5w$!AA\u0004\t=\u0017AC3wS\u0012,gnY3%eA1\u0011QCA\u0013\u0005\u000fDq!a\u001d \u0001\u0004\u0011\u0019\u000eE\u0004+\u0003o\u0012\u0019E!6\u0011\u000b9\n)Ja2\u0002\u0015]LG\u000f\u001b$jYR,'\u000f\u0006\u0003\u0003\u000e\nm\u0007b\u0002BoA\u0001\u0007!1Q\u0001\u0002c\u0002")
public interface SortedSetOps<A, CC extends SortedSet<Object>, C extends SortedSetOps<A, CC, C>>
extends SetOps<A, Set, C>,
SortedOps<A, C> {
    public /* synthetic */ Object scala$collection$SortedSetOps$$super$min(Ordering var1);

    public /* synthetic */ Object scala$collection$SortedSetOps$$super$max(Ordering var1);

    public SortedIterableFactory<CC> sortedIterableFactory();

    public Set<A> unsorted();

    public Iterator<A> iteratorFrom(A var1);

    public static /* synthetic */ Iterator keysIteratorFrom$(SortedSetOps $this, Object start2) {
        return $this.keysIteratorFrom(start2);
    }

    default public Iterator<A> keysIteratorFrom(A start2) {
        return this.iteratorFrom(start2);
    }

    public static /* synthetic */ Object firstKey$(SortedSetOps $this) {
        return $this.firstKey();
    }

    @Override
    default public A firstKey() {
        return this.head();
    }

    public static /* synthetic */ Object lastKey$(SortedSetOps $this) {
        return $this.lastKey();
    }

    @Override
    default public A lastKey() {
        return this.last();
    }

    public static /* synthetic */ Option minAfter$(SortedSetOps $this, Object key) {
        return $this.minAfter(key);
    }

    default public Option<A> minAfter(A key) {
        return ((IterableOps)this.rangeFrom(key)).headOption();
    }

    public static /* synthetic */ Option maxBefore$(SortedSetOps $this, Object key) {
        return $this.maxBefore(key);
    }

    default public Option<A> maxBefore(A key) {
        return ((IterableOps)this.rangeUntil(key)).lastOption();
    }

    public static /* synthetic */ Object min$(SortedSetOps $this, Ordering ord) {
        return $this.min(ord);
    }

    @Override
    default public <B> A min(Ordering<B> ord) {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.min");
        }
        Ordering<B> ordering = ord;
        Ordering ordering2 = this.ordering();
        if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
            return this.head();
        }
        if (ord.isReverseOf(this.ordering())) {
            return this.last();
        }
        return (A)this.scala$collection$SortedSetOps$$super$min(ord);
    }

    public static /* synthetic */ Object max$(SortedSetOps $this, Ordering ord) {
        return $this.max(ord);
    }

    @Override
    default public <B> A max(Ordering<B> ord) {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.max");
        }
        Ordering<B> ordering = ord;
        Ordering ordering2 = this.ordering();
        if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
            return this.last();
        }
        if (ord.isReverseOf(this.ordering())) {
            return this.head();
        }
        return (A)this.scala$collection$SortedSetOps$$super$max(ord);
    }

    public static /* synthetic */ SortedSetOps rangeTo$(SortedSetOps $this, Object to) {
        return $this.rangeTo(to);
    }

    @Override
    default public C rangeTo(A to) {
        Iterator i = ((IterableOnce)this.rangeFrom(to)).iterator();
        if (i.isEmpty()) {
            return (C)((SortedSetOps)this.coll());
        }
        Object next = i.next();
        if (this.ordering().compare(next, to) == 0) {
            if (i.isEmpty()) {
                return (C)((SortedSetOps)this.coll());
            }
            return (C)((SortedSetOps)this.rangeUntil(i.next()));
        }
        return (C)((SortedSetOps)this.rangeUntil(next));
    }

    public static /* synthetic */ SortedSet map$(SortedSetOps $this, Function1 f, Ordering ev) {
        return $this.map(f, ev);
    }

    default public <B> CC map(Function1<A, B> f, Ordering<B> ev) {
        return (CC)((SortedSet)this.sortedIterableFactory().from(new View.Map<A, B>(this, f), ev));
    }

    public static /* synthetic */ SortedSet flatMap$(SortedSetOps $this, Function1 f, Ordering ev) {
        return $this.flatMap(f, ev);
    }

    default public <B> CC flatMap(Function1<A, IterableOnce<B>> f, Ordering<B> ev) {
        return (CC)((SortedSet)this.sortedIterableFactory().from(new View.FlatMap<A, B>(this, f), ev));
    }

    public static /* synthetic */ SortedSet zip$(SortedSetOps $this, IterableOnce that, Ordering ev) {
        return $this.zip(that, ev);
    }

    default public <B> CC zip(IterableOnce<B> that, Ordering<Tuple2<A, B>> ev) {
        IterableOnce<Tuple2<Object, Object>> iterableOnce;
        SortedIterableFactory<CC> sortedIterableFactory = this.sortedIterableFactory();
        if (that instanceof Iterable) {
            Iterable iterable = (Iterable)that;
            iterableOnce = new View.Zip(this, iterable);
        } else {
            iterableOnce = this.iterator().zip(that);
        }
        return (CC)((SortedSet)sortedIterableFactory.from(iterableOnce, ev));
    }

    public static /* synthetic */ SortedSet collect$(SortedSetOps $this, PartialFunction pf, Ordering ev) {
        return $this.collect(pf, ev);
    }

    default public <B> CC collect(PartialFunction<A, B> pf, Ordering<B> ev) {
        return (CC)((SortedSet)this.sortedIterableFactory().from(new View.Collect<A, B>(this, pf), ev));
    }

    public static void $init$(SortedSetOps $this) {
    }

    public static class WithFilter<A, IterableCC, CC extends SortedSet<Object>>
    extends IterableOps.WithFilter<A, IterableCC> {
        private final SortedSetOps<A, CC, ?> self;
        private final Function1<A, Object> p;

        public <B> CC map(Function1<A, B> f, Ordering<B> evidence$1) {
            return (CC)((SortedSet)this.self.sortedIterableFactory().from(new View.Map<A, B>(this.filtered(), f), evidence$1));
        }

        public <B> CC flatMap(Function1<A, IterableOnce<B>> f, Ordering<B> evidence$2) {
            return (CC)((SortedSet)this.self.sortedIterableFactory().from(new View.FlatMap<A, B>(this.filtered(), f), evidence$2));
        }

        public WithFilter<A, IterableCC, CC> withFilter(Function1<A, Object> q) {
            return new WithFilter<A, IterableCC, CC>(this.self, (Function1<Object, Object> & Serializable)a -> BoxesRunTime.boxToBoolean(WithFilter.$anonfun$withFilter$1(this, q, a)));
        }

        public static final /* synthetic */ boolean $anonfun$withFilter$1(WithFilter $this, Function1 q$1, Object a) {
            return BoxesRunTime.unboxToBoolean($this.p.apply(a)) && BoxesRunTime.unboxToBoolean(q$1.apply(a));
        }

        public WithFilter(SortedSetOps<A, CC, ?> self, Function1<A, Object> p) {
            this.self = self;
            this.p = p;
            super(self, p);
        }
    }
}

