/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Hashing$;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Set;
import scala.collection.SetOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.convert.impl.AnyChampStepper;
import scala.collection.convert.impl.DoubleChampStepper;
import scala.collection.convert.impl.IntChampStepper;
import scala.collection.convert.impl.LongChampStepper;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.AbstractSet;
import scala.collection.immutable.BitmapIndexedSetNode;
import scala.collection.immutable.HashSet$;
import scala.collection.immutable.HashSetBuilder;
import scala.collection.immutable.Range;
import scala.collection.immutable.SetHashIterator;
import scala.collection.immutable.SetIterator;
import scala.collection.immutable.SetNode;
import scala.collection.immutable.SetNode$;
import scala.collection.immutable.SetReverseIterator;
import scala.collection.immutable.StrictOptimizedSetOps;
import scala.collection.mutable.Builder;
import scala.collection.mutable.HashSet;
import scala.collection.mutable.ReusableBuilder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\t%f\u0001\u0002\u001a4\u0005iB\u0011b\u0017\u0001\u0003\u0006\u0004%\ta\r/\t\u0011\u0001\u0004!\u0011!Q\u0001\nuCa!\u0019\u0001\u0005\u0002M\u0012\u0007\"B1\u0001\t\u0003!\u0007BB3\u0001A\u0013%a\rC\u0003j\u0001\u0011\u0005#\u000eC\u0003o\u0001\u0011\u0005s\u000eC\u0003t\u0001\u0011\u0005s\u000eC\u0003u\u0001\u0011\u0005S\u000fC\u0003z\u0001\u0011\u0005!\u0010\u0003\u0004\u007f\u0001\u0011E1G\u001f\u0005\u0007\u007f\u0002!\t%!\u0001\t\u000f\u0005\u001d\u0003\u0001\"\u0001\u0002J!9\u0011q\n\u0001\u0005\u0002\u0005E\u0003bBA+\u0001\u0011\u0005\u0011q\u000b\u0005\b\u00037\u0002A\u0011IA/\u0011\u001d\tI\u0007\u0001C!\u0003WBq!!\u001c\u0001\t\u0003\nY\u0007C\u0004\u0002p\u0001!\t%!\u001d\t\u000f\u0005M\u0004\u0001\"\u0011\u0002r!9\u0011Q\u000f\u0001\u0005B\u0005]\u0004\u0002CAI\u0001\u0011\u0005Q'a%\t\u0011\u0005\u0015\u0006\u0001\"\u00016\u0003OCq!a,\u0001\t\u0003\t\t\fC\u0004\u0002<\u0002!\t%!0\t\u0011\u0005\u0005\u0007\u0001)C)\u0003\u0007Dq!!6\u0001\t\u0003\n9\u000eC\u0004\u0002Z\u0002!\t%a7\t\u0011\u0005\r\b\u0001)C\u0005\u0003KDq!!;\u0001\t\u0003\nY\u000fC\u0004\u0002p\u0002!\t%!=\t\u000f\u0005}\b\u0001\"\u0011\u0003\u0002!A!Q\u0001\u0001\u0005RU\u00129\u0001C\u0004\u0003\u0012\u0001!\tEa\u0005\t\u000f\t]\u0001\u0001\"\u0011\u0003\u001a!9!q\u0004\u0001\u0005B\t\u0005\u0002b\u0002B\u0013\u0001\u0011\u0005#q\u0005\u0005\b\u0005W\u0001A\u0011\tB\u0017\u0011\u001d\u0011\t\u0004\u0001C!\u0005gAqAa\u000e\u0001\t\u0003\u0012IdB\u0004\u0003>MB\tAa\u0010\u0007\rI\u001a\u0004\u0012\u0001B!\u0011\u0019\t'\u0006\"\u0001\u0003J!I!1\n\u0016C\u0002\u00135!Q\n\u0005\t\u0005#R\u0003\u0015!\u0004\u0003P!9!1\f\u0016\u0005\u0002\tu\u0003b\u0002B4U\u0011\u0005!\u0011\u000e\u0005\b\u0005sRC\u0011\u0001B>\u0011%\u0011\tJKA\u0001\n\u0013\u0011\u0019JA\u0004ICND7+\u001a;\u000b\u0005Q*\u0014!C5n[V$\u0018M\u00197f\u0015\t1t'\u0001\u0006d_2dWm\u0019;j_:T\u0011\u0001O\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tY$iE\u0003\u0001y1\u000bV\u000bE\u0002>}\u0001k\u0011aM\u0005\u0003\u007fM\u00121\"\u00112tiJ\f7\r^*fiB\u0011\u0011I\u0011\u0007\u0001\t\u0015\u0019\u0005A1\u0001E\u0005\u0005\t\u0015CA#J!\t1u)D\u00018\u0013\tAuGA\u0004O_RD\u0017N\\4\u0011\u0005\u0019S\u0015BA&8\u0005\r\te.\u001f\t\u0006{5\u0003u\nU\u0005\u0003\u001dN\u0012Qc\u0015;sS\u000e$x\n\u001d;j[&TX\rZ*fi>\u00038\u000f\u0005\u0002>\u0001A\u0019Q\b\u0001!\u0011\tI\u001b\u0006iT\u0007\u0002k%\u0011A+\u000e\u0002\u0018\u0013R,'/\u00192mK\u001a\u000b7\r^8ss\u0012+g-Y;miN\u0004\"AV-\u000e\u0003]S!\u0001W\u001b\u0002\u000f\u001d,g.\u001a:jG&\u0011!l\u0016\u0002\u0014\t\u00164\u0017-\u001e7u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\te>|GOT8eKV\tQ\fE\u0002>=\u0002K!aX\u001a\u0003)\tKG/\\1q\u0013:$W\r_3e'\u0016$hj\u001c3f\u0003%\u0011xn\u001c;O_\u0012,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003!\u000eDQaW\u0002A\u0002u#\u0012\u0001U\u0001\u0011]\u0016<\b*Y:i'\u0016$xJ\u001d+iSN$\"\u0001U4\t\u000b!,\u0001\u0019A/\u0002\u00179,wOU8pi:{G-Z\u0001\u0010SR,'/\u00192mK\u001a\u000b7\r^8ssV\t1\u000eE\u0002SY>K!!\\\u001b\u0003\u001f%#XM]1cY\u00164\u0015m\u0019;pef\f\u0011b\u001b8po:\u001c\u0016N_3\u0016\u0003A\u0004\"AR9\n\u0005I<$aA%oi\u0006!1/\u001b>f\u0003\u001dI7/R7qif,\u0012A\u001e\t\u0003\r^L!\u0001_\u001c\u0003\u000f\t{w\u000e\\3b]\u0006A\u0011\u000e^3sCR|'/F\u0001|!\r\u0011F\u0010Q\u0005\u0003{V\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\u0010e\u00164XM]:f\u0013R,'/\u0019;pe\u000691\u000f^3qa\u0016\u0014X\u0003BA\u0002\u0003\u001b!B!!\u0002\u0002>I1\u0011qAA\u0006\u0003C1a!!\u0003\u0001\u0001\u0005\u0015!\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004cA!\u0002\u000e\u00119\u0011q\u0002\u0007C\u0002\u0005E!!A*\u0012\u0007\u0015\u000b\u0019\u0002\r\u0003\u0002\u0016\u0005u\u0001#\u0002*\u0002\u0018\u0005m\u0011bAA\rk\t91\u000b^3qa\u0016\u0014\bcA!\u0002\u001e\u0011Y\u0011qDA\u0007\u0003\u0003\u0005\tQ!\u0001E\u0005\ryF%\r\t\u0005\u0003G\t9D\u0004\u0003\u0002&\u0005Mb\u0002BA\u0014\u0003cqA!!\u000b\u000205\u0011\u00111\u0006\u0006\u0004\u0003[I\u0014A\u0002\u001fs_>$h(C\u00019\u0013\t1t'C\u0002\u00026U\nqa\u0015;faB,'/\u0003\u0003\u0002:\u0005m\"AD#gM&\u001c\u0017.\u001a8u'Bd\u0017\u000e\u001e\u0006\u0004\u0003k)\u0004bBA \u0019\u0001\u000f\u0011\u0011I\u0001\u0006g\"\f\u0007/\u001a\t\u0007%\u0006\r\u0003)a\u0003\n\u0007\u0005\u0015SG\u0001\u0007Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X-\u0001\u0005d_:$\u0018-\u001b8t)\r1\u00181\n\u0005\u0007\u0003\u001bj\u0001\u0019\u0001!\u0002\u000f\u0015dW-\\3oi\u0006!\u0011N\\2m)\r\u0001\u00161\u000b\u0005\u0007\u0003\u001br\u0001\u0019\u0001!\u0002\t\u0015D8\r\u001c\u000b\u0004!\u0006e\u0003BBA'\u001f\u0001\u0007\u0001)\u0001\u0004d_:\u001c\u0017\r\u001e\u000b\u0004!\u0006}\u0003bBA1!\u0001\u0007\u00111M\u0001\u0005i\"\fG\u000f\u0005\u0003S\u0003K\u0002\u0015bAA4k\ta\u0011\n^3sC\ndWm\u00148dK\u0006!A/Y5m+\u0005\u0001\u0016\u0001B5oSR\fA\u0001[3bIV\t\u0001)\u0001\u0003mCN$\u0018a\u00024pe\u0016\f7\r[\u000b\u0005\u0003s\ni\t\u0006\u0003\u0002|\u0005\u0005\u0005c\u0001$\u0002~%\u0019\u0011qP\u001c\u0003\tUs\u0017\u000e\u001e\u0005\b\u0003\u0007+\u0002\u0019AAC\u0003\u00051\u0007C\u0002$\u0002\b\u0002\u000bY)C\u0002\u0002\n^\u0012\u0011BR;oGRLwN\\\u0019\u0011\u0007\u0005\u000bi\t\u0002\u0004\u0002\u0010V\u0011\r\u0001\u0012\u0002\u0002+\u0006yam\u001c:fC\u000eDw+\u001b;i\u0011\u0006\u001c\b\u000e\u0006\u0003\u0002|\u0005U\u0005bBAB-\u0001\u0007\u0011q\u0013\t\b\r\u0006e\u0005\t]A>\u0013\r\tYj\u000e\u0002\n\rVt7\r^5p]JB3AFAP!\r1\u0015\u0011U\u0005\u0004\u0003G;$AB5oY&tW-\u0001\u000bg_J,\u0017m\u00195XSRD\u0007*Y:i/\"LG.\u001a\u000b\u0005\u0003w\nI\u000bC\u0004\u0002\u0004^\u0001\r!a+\u0011\r\u0019\u000bI\n\u00119wQ\r9\u0012qT\u0001\tgV\u00147/\u001a;PMR\u0019a/a-\t\u000f\u0005\u0005\u0004\u00041\u0001\u00026B!Q(a.A\u0013\r\tIl\r\u0002\u0004'\u0016$\u0018AB3rk\u0006d7\u000fF\u0002w\u0003\u007fCa!!\u0019\u001a\u0001\u0004I\u0015!C2mCN\u001ch*Y7f+\t\t)\r\u0005\u0003\u0002H\u0006EWBAAe\u0015\u0011\tY-!4\u0002\t1\fgn\u001a\u0006\u0003\u0003\u001f\fAA[1wC&!\u00111[Ae\u0005\u0019\u0019FO]5oO\u0006A\u0001.Y:i\u0007>$W\rF\u0001q\u0003\u0011!\u0017N\u001a4\u0015\u0007A\u000bi\u000eC\u0004\u0002bq\u0001\r!a8\u0011\tI\u000b\t\u000fQ\u0005\u0004\u0003s+\u0014A\b:f[>4X\rZ!mY^KG\u000f[*iC2dwn^'vi\u0006$\u0018n\u001c8t)\r\u0001\u0016q\u001d\u0005\b\u0003Cj\u0002\u0019AA2\u0003)\u0011X-\\8wK\u0012\fE\u000e\u001c\u000b\u0004!\u00065\bbBA1=\u0001\u0007\u00111M\u0001\na\u0006\u0014H/\u001b;j_:$B!a=\u0002zB)a)!>Q!&\u0019\u0011q_\u001c\u0003\rQ+\b\u000f\\33\u0011\u001d\tYp\ba\u0001\u0003{\f\u0011\u0001\u001d\t\u0006\r\u0006\u001d\u0005I^\u0001\u0005gB\fg\u000e\u0006\u0003\u0002t\n\r\u0001bBA~A\u0001\u0007\u0011Q`\u0001\u000bM&dG/\u001a:J[BdG#\u0002)\u0003\n\t5\u0001b\u0002B\u0006C\u0001\u0007\u0011Q`\u0001\u0005aJ,G\r\u0003\u0004\u0003\u0010\u0005\u0002\rA^\u0001\nSN4E.\u001b9qK\u0012\f\u0011\"\u001b8uKJ\u001cXm\u0019;\u0015\u0007A\u0013)\u0002C\u0004\u0002b\t\u0002\r!a8\u0002\tQ\f7.\u001a\u000b\u0004!\nm\u0001B\u0002B\u000fG\u0001\u0007\u0001/A\u0001o\u0003%!\u0018m[3SS\u001eDG\u000fF\u0002Q\u0005GAaA!\b%\u0001\u0004\u0001\u0018!\u0003;bW\u0016<\u0006.\u001b7f)\r\u0001&\u0011\u0006\u0005\b\u0003w,\u0003\u0019AA\u007f\u0003\u0011!'o\u001c9\u0015\u0007A\u0013y\u0003\u0003\u0004\u0003\u001e\u0019\u0002\r\u0001]\u0001\nIJ|\u0007OU5hQR$2\u0001\u0015B\u001b\u0011\u0019\u0011ib\na\u0001a\u0006IAM]8q/\"LG.\u001a\u000b\u0004!\nm\u0002bBA~Q\u0001\u0007\u0011Q`\u0001\b\u0011\u0006\u001c\bnU3u!\ti$f\u0005\u0003+\u0005\u0007Z\u0007c\u0001$\u0003F%\u0019!qI\u001c\u0003\r\u0005s\u0017PU3g)\t\u0011y$\u0001\u0005F[B$\u0018pU3u+\t\u0011y\u0005E\u0002>\u0001\u0015\u000b\u0011\"R7qif\u001cV\r\u001e\u0011)\u00075\u0012)\u0006E\u0002G\u0005/J1A!\u00178\u0005%!(/\u00198tS\u0016tG/A\u0003f[B$\u00180\u0006\u0003\u0003`\t\u0015TC\u0001B1!\u0011i\u0004Aa\u0019\u0011\u0007\u0005\u0013)\u0007B\u0003D]\t\u0007A)\u0001\u0003ge>lW\u0003\u0002B6\u0005c\"BA!\u001c\u0003tA!Q\b\u0001B8!\r\t%\u0011\u000f\u0003\u0006\u0007>\u0012\r\u0001\u0012\u0005\b\u0005kz\u0003\u0019\u0001B<\u0003\u0019\u0019x.\u001e:dKB)!+!\u001a\u0003p\u0005Qa.Z<Ck&dG-\u001a:\u0016\t\tu$QR\u000b\u0003\u0005\u007f\u0002\u0002B!!\u0003\b\n-%qR\u0007\u0003\u0005\u0007S1A!\"6\u0003\u001diW\u000f^1cY\u0016LAA!#\u0003\u0004\ny!+Z;tC\ndWMQ;jY\u0012,'\u000fE\u0002B\u0005\u001b#Qa\u0011\u0019C\u0002\u0011\u0003B!\u0010\u0001\u0003\f\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011!Q\u0013\t\u0005\u0003\u000f\u00149*\u0003\u0003\u0003\u001a\u0006%'AB(cU\u0016\u001cG\u000fK\u0004+\u0005;\u0013\u0019K!*\u0011\u0007\u0019\u0013y*C\u0002\u0003\"^\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\rAs!\u000bBO\u0005G\u0013)\u000b")
public final class HashSet<A>
extends AbstractSet<A>
implements StrictOptimizedSetOps<A, HashSet, HashSet<A>>,
DefaultSerializable {
    private final BitmapIndexedSetNode<A> rootNode;

    public static <A> ReusableBuilder<A, HashSet<A>> newBuilder() {
        return new HashSetBuilder();
    }

    public static <A> HashSet<A> from(IterableOnce<A> source2) {
        return HashSet$.MODULE$.from((IterableOnce)source2);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n, Function1 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        View.Tabulate from_source = new View.Tabulate(n, f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        View.Fill from_source = new View.Fill(n, elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(HashSet$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(HashSet$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        HashSet$ unfold_this = HashSet$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        HashSet$ iterate_this = HashSet$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public <A1, A2> Tuple2<HashSet<A1>, HashSet<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<HashSet<A1>, HashSet<A2>, HashSet<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public <A1, A2> Tuple2<HashSet<A1>, HashSet<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    public BitmapIndexedSetNode<A> rootNode() {
        return this.rootNode;
    }

    private HashSet<A> newHashSetOrThis(BitmapIndexedSetNode<A> newRootNode) {
        if (this.rootNode() == newRootNode) {
            return this;
        }
        return new HashSet<A>(newRootNode);
    }

    @Override
    public IterableFactory<HashSet> iterableFactory() {
        return HashSet$.MODULE$;
    }

    @Override
    public int knownSize() {
        return this.rootNode().size();
    }

    @Override
    public int size() {
        return this.rootNode().size();
    }

    @Override
    public boolean isEmpty() {
        return this.rootNode().size() == 0;
    }

    @Override
    public Iterator<A> iterator() {
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        return new SetIterator<A>(this.rootNode());
    }

    public Iterator<A> reverseIterator() {
        return new SetReverseIterator<A>(this.rootNode());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        void from_root;
        void from_extract;
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            void from_root2;
            void from_extract2;
            Function2<SetNode, Object, Object> & Serializable intersect = (Function2<SetNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToInteger(HashSet.$anonfun$stepper$1(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedSetNode<A> bitmapIndexedSetNode = this.rootNode();
            int from_maxSize = this.size();
            IntChampStepper<void> from_ans = new IntChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract2);
            from_ans.initRoot(from_root2);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            void from_root3;
            void from_extract3;
            Function2<SetNode, Object, Object> & Serializable intersect = (Function2<SetNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToLong(HashSet.$anonfun$stepper$2(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedSetNode<A> bitmapIndexedSetNode = this.rootNode();
            int from_maxSize = this.size();
            LongChampStepper<void> from_ans = new LongChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract3);
            from_ans.initRoot(from_root3);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            void from_root4;
            void from_extract4;
            Function2<SetNode, Object, Object> & Serializable intersect = (Function2<SetNode, Object, Object> & Serializable)(node, i) -> BoxesRunTime.boxToDouble(HashSet.$anonfun$stepper$3(node, BoxesRunTime.unboxToInt(i)));
            BitmapIndexedSetNode<A> bitmapIndexedSetNode = this.rootNode();
            int from_maxSize = this.size();
            DoubleChampStepper<void> from_ans = new DoubleChampStepper<void>(from_maxSize, (Function2<void, Object, Object>)from_extract4);
            from_ans.initRoot(from_root4);
            return (S)from_ans;
        }
        Function2<SetNode, Object, Object> & Serializable intersect = (Function2<SetNode, Object, Object> & Serializable)(node, i) -> node.getPayload(BoxesRunTime.unboxToInt(i));
        BitmapIndexedSetNode<A> bitmapIndexedSetNode = this.rootNode();
        int from_maxSize = this.size();
        AnyChampStepper from_ans = new AnyChampStepper(from_maxSize, from_extract);
        from_ans.initRoot(from_root);
        bitmapIndexedSetNode = null;
        intersect = null;
        Object var18_18 = null;
        return shape.parUnbox(from_ans);
    }

    @Override
    public boolean contains(A element) {
        int elementUnimprovedHash = Statics.anyHash(element);
        int elementHash = Hashing$.MODULE$.improve(elementUnimprovedHash);
        return this.rootNode().contains(element, elementUnimprovedHash, elementHash, 0);
    }

    @Override
    public HashSet<A> incl(A element) {
        int elementUnimprovedHash = Statics.anyHash(element);
        int elementHash = Hashing$.MODULE$.improve(elementUnimprovedHash);
        SetNode newRootNode = this.rootNode().updated((Object)element, elementUnimprovedHash, elementHash, 0);
        return this.newHashSetOrThis((BitmapIndexedSetNode<A>)newRootNode);
    }

    @Override
    public HashSet<A> excl(A element) {
        int elementUnimprovedHash = Statics.anyHash(element);
        int elementHash = Hashing$.MODULE$.improve(elementUnimprovedHash);
        SetNode newRootNode = this.rootNode().removed((Object)element, elementUnimprovedHash, elementHash, 0);
        return this.newHashSetOrThis((BitmapIndexedSetNode<A>)newRootNode);
    }

    @Override
    public HashSet<A> concat(IterableOnce<A> that) {
        if (that instanceof HashSet) {
            HashSet hashSet = (HashSet)that;
            if (this.isEmpty()) {
                return hashSet;
            }
            SetNode newNode = this.rootNode().concat((SetNode)hashSet.rootNode(), 0);
            if (newNode == hashSet.rootNode()) {
                return hashSet;
            }
            return this.newHashSetOrThis((BitmapIndexedSetNode<A>)newNode);
        }
        if (that instanceof scala.collection.mutable.HashSet) {
            scala.collection.mutable.HashSet hashSet = (scala.collection.mutable.HashSet)that;
            if (hashSet == null) {
                throw null;
            }
            HashSet.HashSetIterator iter = new HashSet.HashSetIterator<HashSet.Node<A>>(hashSet){

                public HashSet.Node<A> extract(HashSet.Node<A> nd) {
                    return nd;
                }
            };
            SetNode current = this.rootNode();
            while (iter.hasNext()) {
                HashSet.Node next = (HashSet.Node)iter.next();
                int unimproveHash_improvedHash = next.hash();
                int originalHash = unimproveHash_improvedHash ^ unimproveHash_improvedHash >>> 16;
                int improved = Hashing$.MODULE$.improve(originalHash);
                if ((current = ((BitmapIndexedSetNode)current).updated(next.key(), originalHash, improved, 0)) == this.rootNode()) continue;
                int maskFrom_shift = 0;
                int bitposFrom_mask = improved >>> maskFrom_shift & 0x1F;
                int shallowlyMutableNodeMap = 1 << bitposFrom_mask;
                while (iter.hasNext()) {
                    HashSet.Node next2 = (HashSet.Node)iter.next();
                    int unimproveHash_improvedHash2 = next2.hash();
                    int originalHash2 = unimproveHash_improvedHash2 ^ unimproveHash_improvedHash2 >>> 16;
                    int improved2 = Hashing$.MODULE$.improve(originalHash2);
                    shallowlyMutableNodeMap = ((BitmapIndexedSetNode)current).updateWithShallowMutations(next2.key(), originalHash2, improved2, 0, shallowlyMutableNodeMap);
                }
                return new HashSet<A>(current);
            }
            return this;
        }
        Iterator<A> iter = that.iterator();
        SetNode current = this.rootNode();
        while (iter.hasNext()) {
            int improved;
            int originalHash;
            A element = iter.next();
            if ((current = ((BitmapIndexedSetNode)current).updated((Object)element, originalHash = Statics.anyHash(element), improved = Hashing$.MODULE$.improve(originalHash), 0)) == this.rootNode()) continue;
            int maskFrom_shift = 0;
            int bitposFrom_mask = improved >>> maskFrom_shift & 0x1F;
            int shallowlyMutableNodeMap = 1 << bitposFrom_mask;
            while (iter.hasNext()) {
                A element2 = iter.next();
                int originalHash3 = Statics.anyHash(element2);
                int improved3 = Hashing$.MODULE$.improve(originalHash3);
                shallowlyMutableNodeMap = ((BitmapIndexedSetNode)current).updateWithShallowMutations(element2, originalHash3, improved3, 0, shallowlyMutableNodeMap);
            }
            return new HashSet<A>(current);
        }
        return this;
    }

    @Override
    public HashSet<A> tail() {
        A $minus_elem = this.head();
        return this.excl((Object)$minus_elem);
    }

    @Override
    public HashSet<A> init() {
        A $minus_elem = this.last();
        return this.excl((Object)$minus_elem);
    }

    @Override
    public A head() {
        return this.iterator().next();
    }

    @Override
    public A last() {
        return this.reverseIterator().next();
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        BitmapIndexedSetNode<A> bitmapIndexedSetNode = this.rootNode();
        if (bitmapIndexedSetNode == null) {
            throw null;
        }
        BitmapIndexedSetNode<A> foreach_this = bitmapIndexedSetNode;
        int foreach_thisPayloadArity = foreach_this.payloadArity();
        for (int foreach_i = 0; foreach_i < foreach_thisPayloadArity; ++foreach_i) {
            f.apply(foreach_this.content()[foreach_i]);
        }
        int foreach_thisNodeArity = foreach_this.nodeArity();
        for (int foreach_j = 0; foreach_j < foreach_thisNodeArity; ++foreach_j) {
            ((SetNode)foreach_this.getNode(foreach_j)).foreach(f);
        }
    }

    public void foreachWithHash(Function2<A, Object, BoxedUnit> f) {
        BitmapIndexedSetNode<A> bitmapIndexedSetNode = this.rootNode();
        if (bitmapIndexedSetNode == null) {
            throw null;
        }
        BitmapIndexedSetNode<A> foreachWithHash_this = bitmapIndexedSetNode;
        int foreachWithHash_iN = foreachWithHash_this.payloadArity();
        for (int foreachWithHash_i = 0; foreachWithHash_i < foreachWithHash_iN; ++foreachWithHash_i) {
            f.apply(foreachWithHash_this.content()[foreachWithHash_i], foreachWithHash_this.originalHashes()[foreachWithHash_i]);
        }
        int foreachWithHash_jN = foreachWithHash_this.nodeArity();
        for (int foreachWithHash_j = 0; foreachWithHash_j < foreachWithHash_jN; ++foreachWithHash_j) {
            ((SetNode)foreachWithHash_this.getNode(foreachWithHash_j)).foreachWithHash(f);
        }
    }

    public void foreachWithHashWhile(Function2<A, Object, Object> f) {
        BitmapIndexedSetNode<A> bitmapIndexedSetNode = this.rootNode();
        if (bitmapIndexedSetNode == null) {
            throw null;
        }
        BitmapIndexedSetNode<A> foreachWithHashWhile_this = bitmapIndexedSetNode;
        int foreachWithHashWhile_thisPayloadArity = foreachWithHashWhile_this.payloadArity();
        boolean foreachWithHashWhile_pass = true;
        for (int foreachWithHashWhile_i = 0; foreachWithHashWhile_i < foreachWithHashWhile_thisPayloadArity && foreachWithHashWhile_pass; ++foreachWithHashWhile_i) {
            foreachWithHashWhile_pass = foreachWithHashWhile_pass && BoxesRunTime.unboxToBoolean(f.apply(foreachWithHashWhile_this.content()[foreachWithHashWhile_i], foreachWithHashWhile_this.originalHashes()[foreachWithHashWhile_i]));
        }
        int foreachWithHashWhile_thisNodeArity = foreachWithHashWhile_this.nodeArity();
        for (int foreachWithHashWhile_j = 0; foreachWithHashWhile_j < foreachWithHashWhile_thisNodeArity && foreachWithHashWhile_pass; ++foreachWithHashWhile_j) {
            foreachWithHashWhile_pass = foreachWithHashWhile_pass && ((SetNode)foreachWithHashWhile_this.getNode(foreachWithHashWhile_j)).foreachWithHashWhile(f);
        }
    }

    @Override
    public boolean subsetOf(scala.collection.immutable.Set<A> that) {
        if (that.isEmpty()) {
            return true;
        }
        if (that instanceof HashSet) {
            HashSet hashSet = (HashSet)that;
            return this.rootNode().subsetOf(hashSet.rootNode(), 0);
        }
        return this.forall(that);
    }

    @Override
    public boolean equals(Object that) {
        block2: {
            block4: {
                block3: {
                    if (!(that instanceof HashSet)) break block2;
                    HashSet hashSet = (HashSet)that;
                    if (this == hashSet) break block3;
                    BitmapIndexedSetNode<A> bitmapIndexedSetNode = this.rootNode();
                    BitmapIndexedSetNode<A> bitmapIndexedSetNode2 = hashSet.rootNode();
                    if (bitmapIndexedSetNode != null ? !((Object)bitmapIndexedSetNode).equals(bitmapIndexedSetNode2) : bitmapIndexedSetNode2 != null) break block4;
                }
                return true;
            }
            return false;
        }
        return Set.equals$(this, that);
    }

    @Override
    public String className() {
        return "HashSet";
    }

    @Override
    public int hashCode() {
        SetHashIterator<A> it = new SetHashIterator<A>(this.rootNode());
        return MurmurHash3$.MODULE$.unorderedHash(it, MurmurHash3$.MODULE$.setSeed());
    }

    @Override
    public HashSet<A> diff(Set<A> that) {
        if (this.isEmpty()) {
            return this;
        }
        if (that instanceof HashSet) {
            HashSet hashSet = (HashSet)that;
            if (hashSet.isEmpty()) {
                return this;
            }
            if (((BitmapIndexedSetNode)this.rootNode().diff((SetNode)hashSet.rootNode(), 0)).size() == 0) {
                return HashSet$.MODULE$.empty();
            }
            return this.newHashSetOrThis((BitmapIndexedSetNode<A>)this.rootNode().diff((SetNode)hashSet.rootNode(), 0));
        }
        if (that instanceof scala.collection.mutable.HashSet) {
            scala.collection.mutable.HashSet hashSet = (scala.collection.mutable.HashSet)that;
            if (hashSet == null) {
                throw null;
            }
            HashSet.HashSetIterator iter = new /* invalid duplicate definition of identical inner class */;
            SetNode curr = this.rootNode();
            while (iter.hasNext()) {
                HashSet.Node next = (HashSet.Node)iter.next();
                int unimproveHash_improvedHash = next.hash();
                int originalHash = unimproveHash_improvedHash ^ unimproveHash_improvedHash >>> 16;
                int improved = Hashing$.MODULE$.improve(originalHash);
                if ((curr = ((BitmapIndexedSetNode)curr).removed(next.key(), originalHash, improved, 0)) == this.rootNode()) continue;
                if (((BitmapIndexedSetNode)curr).size() == 0) {
                    return HashSet$.MODULE$.empty();
                }
                while (iter.hasNext()) {
                    HashSet.Node next2 = (HashSet.Node)iter.next();
                    int unimproveHash_improvedHash2 = next2.hash();
                    int originalHash2 = unimproveHash_improvedHash2 ^ unimproveHash_improvedHash2 >>> 16;
                    int improved2 = Hashing$.MODULE$.improve(originalHash2);
                    ((BitmapIndexedSetNode)curr).removeWithShallowMutations(next2.key(), originalHash2, improved2);
                    if (((BitmapIndexedSetNode)curr).size() != 0) continue;
                    return HashSet$.MODULE$.empty();
                }
                return new HashSet<A>(curr);
            }
            return this;
        }
        int thatKnownSize = that.knownSize();
        if (thatKnownSize == 0) {
            return this;
        }
        if (thatKnownSize <= this.size()) {
            return this.removedAllWithShallowMutations(that);
        }
        Function1<Object, Object> & Serializable filterNot_pred = (Function1<Object, Object> & Serializable)elem -> BoxesRunTime.boxToBoolean(that.contains(elem));
        boolean filterImpl_isFlipped = true;
        SetNode filterImpl_newRootNode = this.rootNode().filterImpl(filterNot_pred, filterImpl_isFlipped);
        if (filterImpl_newRootNode == this.rootNode()) {
            return this;
        }
        if (((BitmapIndexedSetNode)filterImpl_newRootNode).size() == 0) {
            return HashSet$.MODULE$.empty();
        }
        return new HashSet<A>(filterImpl_newRootNode);
    }

    private HashSet<A> removedAllWithShallowMutations(IterableOnce<A> that) {
        Iterator<A> iter = that.iterator();
        SetNode curr = this.rootNode();
        while (iter.hasNext()) {
            int improved;
            int originalHash;
            A next = iter.next();
            if ((curr = ((BitmapIndexedSetNode)curr).removed((Object)next, originalHash = Statics.anyHash(next), improved = Hashing$.MODULE$.improve(originalHash), 0)) == this.rootNode()) continue;
            if (((BitmapIndexedSetNode)curr).size() == 0) {
                return HashSet$.MODULE$.empty();
            }
            while (iter.hasNext()) {
                A next2 = iter.next();
                int originalHash2 = Statics.anyHash(next2);
                int improved2 = Hashing$.MODULE$.improve(originalHash2);
                ((BitmapIndexedSetNode)curr).removeWithShallowMutations(next2, originalHash2, improved2);
                if (((BitmapIndexedSetNode)curr).size() != 0) continue;
                return HashSet$.MODULE$.empty();
            }
            return new HashSet<A>(curr);
        }
        return this;
    }

    @Override
    public HashSet<A> removedAll(IterableOnce<A> that) {
        Range range;
        if (that instanceof Set) {
            Set set = (Set)that;
            return this.diff(set);
        }
        if (that instanceof Range && (range = (Range)that).length() > this.size()) {
            Function1<Object, Object> & Serializable filter_pred = (Function1<Object, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(HashSet.$anonfun$removedAll$1(range, x0$1));
            boolean filterImpl_isFlipped = false;
            SetNode filterImpl_newRootNode = this.rootNode().filterImpl(filter_pred, filterImpl_isFlipped);
            if (filterImpl_newRootNode == this.rootNode()) {
                return this;
            }
            if (((BitmapIndexedSetNode)filterImpl_newRootNode).size() == 0) {
                return HashSet$.MODULE$.empty();
            }
            return new HashSet<A>(filterImpl_newRootNode);
        }
        return this.removedAllWithShallowMutations(that);
    }

    @Override
    public Tuple2<HashSet<A>, HashSet<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<HashSet<A>, HashSet<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public HashSet<A> filterImpl(Function1<A, Object> pred, boolean isFlipped) {
        SetNode newRootNode = this.rootNode().filterImpl((Function1)pred, isFlipped);
        if (newRootNode == this.rootNode()) {
            return this;
        }
        if (((BitmapIndexedSetNode)newRootNode).size() == 0) {
            return HashSet$.MODULE$.empty();
        }
        return new HashSet<A>(newRootNode);
    }

    @Override
    public HashSet<A> intersect(Set<A> that) {
        return (HashSet)SetOps.intersect$(this, that);
    }

    @Override
    public HashSet<A> take(int n) {
        return (HashSet)IterableOps.take$(this, n);
    }

    @Override
    public HashSet<A> takeRight(int n) {
        return (HashSet)StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public HashSet<A> takeWhile(Function1<A, Object> p) {
        return (HashSet)IterableOps.takeWhile$(this, p);
    }

    @Override
    public HashSet<A> drop(int n) {
        return (HashSet)IterableOps.drop$(this, n);
    }

    @Override
    public HashSet<A> dropRight(int n) {
        return (HashSet)StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public HashSet<A> dropWhile(Function1<A, Object> p) {
        return (HashSet)IterableOps.dropWhile$(this, p);
    }

    public static final /* synthetic */ int $anonfun$stepper$1(SetNode node, int i) {
        return BoxesRunTime.unboxToInt(node.getPayload(i));
    }

    public static final /* synthetic */ long $anonfun$stepper$2(SetNode node, int i) {
        return BoxesRunTime.unboxToLong(node.getPayload(i));
    }

    public static final /* synthetic */ double $anonfun$stepper$3(SetNode node, int i) {
        return BoxesRunTime.unboxToDouble(node.getPayload(i));
    }

    public static final /* synthetic */ boolean $anonfun$removedAll$1(Range x3$1, Object x0$1) {
        if (x0$1 instanceof Integer) {
            int n = BoxesRunTime.unboxToInt(x0$1);
            return !x3$1.contains(n);
        }
        return true;
    }

    public HashSet(BitmapIndexedSetNode<A> rootNode) {
        this.rootNode = rootNode;
        Statics.releaseFence();
    }

    public HashSet() {
        this(SetNode$.MODULE$.empty());
    }
}

