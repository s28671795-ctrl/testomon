/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.WildcardIndex$;
import akka.util.WildcardTree;
import akka.util.WildcardTree$;
import java.io.Serializable;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055h!\u0002\u0011\"\u0005\u000e*\u0003\u0002C\u001f\u0001\u0005+\u0007I\u0011\u0001 \t\u00119\u0003!\u0011#Q\u0001\n}B\u0001b\u0014\u0001\u0003\u0016\u0004%\tA\u0010\u0005\t!\u0002\u0011\t\u0012)A\u0005\u007f!)\u0011\u000b\u0001C\u0001%\")a\u000b\u0001C\u0001/\")q\r\u0001C\u0001Q\")\u0001\u000f\u0001C\u0001c\"9Q\u000fAA\u0001\n\u00031\bb\u0002@\u0001#\u0003%\ta \u0005\n\u00033\u0001\u0011\u0013!C\u0001\u00037A\u0011\"a\b\u0001\u0003\u0003%\t%!\t\t\u0013\u0005E\u0002!!A\u0005\u0002\u0005M\u0002\"CA\u001e\u0001\u0005\u0005I\u0011AA\u001f\u0011%\t\u0019\u0005AA\u0001\n\u0003\n)\u0005C\u0005\u0002T\u0001\t\t\u0011\"\u0001\u0002V!I\u0011\u0011\f\u0001\u0002\u0002\u0013\u0005\u00131\f\u0005\n\u0003?\u0002\u0011\u0011!C!\u0003CB\u0011\"a\u0019\u0001\u0003\u0003%\t%!\u001a\t\u0013\u0005\u001d\u0004!!A\u0005B\u0005%tACA7C\u0005\u0005\t\u0012A\u0012\u0002p\u0019I\u0001%IA\u0001\u0012\u0003\u0019\u0013\u0011\u000f\u0005\u0007#Z!\t!! \t\u0013\u0005\rd#!A\u0005F\u0005\u0015\u0004\"CA@-\u0005\u0005I\u0011QAA\u0011%\t\tJFI\u0001\n\u0003\t\u0019\nC\u0005\u0002 Z\t\n\u0011\"\u0001\u0002\"\"I\u0011Q\u0016\f\u0002\u0002\u0013\u0005\u0015q\u0016\u0005\n\u0003\u000f4\u0012\u0013!C\u0001\u0003\u0013D\u0011\"!6\u0017#\u0003%\t!a6\t\u0013\u0005\rh#!A\u0005\n\u0005\u0015(!D,jY\u0012\u001c\u0017M\u001d3J]\u0012,\u0007P\u0003\u0002#G\u0005!Q\u000f^5m\u0015\u0005!\u0013\u0001B1lW\u0006,\"AJ#\u0014\t\u00019S\u0006\r\t\u0003Q-j\u0011!\u000b\u0006\u0002U\u0005)1oY1mC&\u0011A&\u000b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005!r\u0013BA\u0018*\u0005\u001d\u0001&o\u001c3vGR\u0004\"!\r\u001e\u000f\u0005IBdBA\u001a8\u001b\u0005!$BA\u001b7\u0003\u0019a$o\\8u}\r\u0001\u0011\"\u0001\u0016\n\u0005eJ\u0013a\u00029bG.\fw-Z\u0005\u0003wq\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!!O\u0015\u0002\u0019]LG\u000eZ2be\u0012$&/Z3\u0016\u0003}\u00022\u0001Q!D\u001b\u0005\t\u0013B\u0001\"\"\u000519\u0016\u000e\u001c3dCJ$GK]3f!\t!U\t\u0004\u0001\u0005\u000b\u0019\u0003!\u0019A$\u0003\u0003Q\u000b\"\u0001S&\u0011\u0005!J\u0015B\u0001&*\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u000b'\n\u00055K#aA!os\u0006iq/\u001b7eG\u0006\u0014H\r\u0016:fK\u0002\n!\u0003Z8vE2,w+\u001b7eG\u0006\u0014H\r\u0016:fK\u0006\u0019Bm\\;cY\u0016<\u0016\u000e\u001c3dCJ$GK]3fA\u00051A(\u001b8jiz\"2a\u0015+V!\r\u0001\u0005a\u0011\u0005\b{\u0015\u0001\n\u00111\u0001@\u0011\u001dyU\u0001%AA\u0002}\na!\u001b8tKJ$HcA*YK\")\u0011L\u0002a\u00015\u0006)Q\r\\3ngB\u0019\u0001fW/\n\u0005qK#!B!se\u0006L\bC\u00010c\u001d\ty\u0006\r\u0005\u00024S%\u0011\u0011-K\u0001\u0007!J,G-\u001a4\n\u0005\r$'AB*ue&twM\u0003\u0002bS!)aM\u0002a\u0001\u0007\u0006\tA-\u0001\u0003gS:$GCA5m!\rA#nQ\u0005\u0003W&\u0012aa\u00149uS>t\u0007\"B-\b\u0001\u0004i\u0007cA\u0019o;&\u0011q\u000e\u0010\u0002\t\u0013R,'/\u00192mK\u00069\u0011n]#naRLX#\u0001:\u0011\u0005!\u001a\u0018B\u0001;*\u0005\u001d\u0011un\u001c7fC:\fAaY8qsV\u0011qO\u001f\u000b\u0004qnl\bc\u0001!\u0001sB\u0011AI\u001f\u0003\u0006\r&\u0011\ra\u0012\u0005\b{%\u0001\n\u00111\u0001}!\r\u0001\u0015)\u001f\u0005\b\u001f&\u0001\n\u00111\u0001}\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*B!!\u0001\u0002\u0018U\u0011\u00111\u0001\u0016\u0004\u007f\u0005\u00151FAA\u0004!\u0011\tI!a\u0005\u000e\u0005\u0005-!\u0002BA\u0007\u0003\u001f\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005E\u0011&\u0001\u0006b]:|G/\u0019;j_:LA!!\u0006\u0002\f\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000b\u0019S!\u0019A$\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU!\u0011\u0011AA\u000f\t\u001515B1\u0001H\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u0005\t\u0005\u0003K\ty#\u0004\u0002\u0002()!\u0011\u0011FA\u0016\u0003\u0011a\u0017M\\4\u000b\u0005\u00055\u0012\u0001\u00026bm\u0006L1aYA\u0014\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\t)\u0004E\u0002)\u0003oI1!!\u000f*\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\rY\u0015q\b\u0005\n\u0003\u0003r\u0011\u0011!a\u0001\u0003k\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA$!\u0015\tI%a\u0014L\u001b\t\tYEC\u0002\u0002N%\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\t\t&a\u0013\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0004e\u0006]\u0003\u0002CA!!\u0005\u0005\t\u0019A&\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003G\ti\u0006C\u0005\u0002BE\t\t\u00111\u0001\u00026\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u00026\u0005AAo\\*ue&tw\r\u0006\u0002\u0002$\u00051Q-];bYN$2A]A6\u0011!\t\t\u0005FA\u0001\u0002\u0004Y\u0015!D,jY\u0012\u001c\u0017M\u001d3J]\u0012,\u0007\u0010\u0005\u0002A-M!acJA:!\u0011\t)(a\u001f\u000e\u0005\u0005]$\u0002BA=\u0003W\t!![8\n\u0007m\n9\b\u0006\u0002\u0002p\u0005)\u0011\r\u001d9msV!\u00111QAE)\u0019\t))a#\u0002\u0010B!\u0001\tAAD!\r!\u0015\u0011\u0012\u0003\u0006\rf\u0011\ra\u0012\u0005\t{e\u0001\n\u00111\u0001\u0002\u000eB!\u0001)QAD\u0011!y\u0015\u0004%AA\u0002\u00055\u0015aD1qa2LH\u0005Z3gCVdG\u000fJ\u0019\u0016\t\u0005U\u0015QT\u000b\u0003\u0003/SC!!'\u0002\u0006A!\u0001)QAN!\r!\u0015Q\u0014\u0003\u0006\rj\u0011\raR\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%eU!\u00111UAV+\t\t)K\u000b\u0003\u0002(\u0006\u0015\u0001\u0003\u0002!B\u0003S\u00032\u0001RAV\t\u001515D1\u0001H\u0003\u001d)h.\u00199qYf,B!!-\u0002@R!\u00111WAa!\u0011A#.!.\u0011\u000f!\n9,a/\u0002<&\u0019\u0011\u0011X\u0015\u0003\rQ+\b\u000f\\33!\u0011\u0001\u0015)!0\u0011\u0007\u0011\u000by\fB\u0003G9\t\u0007q\tC\u0005\u0002Dr\t\t\u00111\u0001\u0002F\u0006\u0019\u0001\u0010\n\u0019\u0011\t\u0001\u0003\u0011QX\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u0016\t\u0005-\u00171[\u000b\u0003\u0003\u001bTC!a4\u0002\u0006A!\u0001)QAi!\r!\u00151\u001b\u0003\u0006\rv\u0011\raR\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u0016\t\u0005e\u0017\u0011]\u000b\u0003\u00037TC!!8\u0002\u0006A!\u0001)QAp!\r!\u0015\u0011\u001d\u0003\u0006\rz\u0011\raR\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003O\u0004B!!\n\u0002j&!\u00111^A\u0014\u0005\u0019y%M[3di\u0002")
public final class WildcardIndex<T>
implements Product,
Serializable {
    private final WildcardTree<T> wildcardTree;
    private final WildcardTree<T> doubleWildcardTree;

    public static <T> WildcardTree<T> $lessinit$greater$default$2() {
        return WildcardIndex$.MODULE$.$lessinit$greater$default$2();
    }

    public static <T> WildcardTree<T> $lessinit$greater$default$1() {
        return WildcardIndex$.MODULE$.$lessinit$greater$default$1();
    }

    public static <T> Option<Tuple2<WildcardTree<T>, WildcardTree<T>>> unapply(WildcardIndex<T> x$0) {
        return WildcardIndex$.MODULE$.unapply(x$0);
    }

    public static <T> WildcardTree<T> apply$default$2() {
        return WildcardIndex$.MODULE$.apply$default$2();
    }

    public static <T> WildcardTree<T> apply$default$1() {
        return WildcardIndex$.MODULE$.apply$default$1();
    }

    public static <T> WildcardIndex<T> apply(WildcardTree<T> wildcardTree, WildcardTree<T> doubleWildcardTree) {
        return WildcardIndex$.MODULE$.apply(wildcardTree, doubleWildcardTree);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public WildcardTree<T> wildcardTree() {
        return this.wildcardTree;
    }

    public WildcardTree<T> doubleWildcardTree() {
        return this.doubleWildcardTree;
    }

    public WildcardIndex<T> insert(String[] elems, T d) {
        boolean bl = false;
        Some some = null;
        Option option = ArrayOps$.MODULE$.lastOption$extension(Predef$.MODULE$.refArrayOps(elems));
        if (option instanceof Some) {
            bl = true;
            some = (Some)option;
            String string = (String)some.value();
            if ("**".equals(string)) {
                WildcardTree<T> x$1 = this.doubleWildcardTree().insert(ArrayOps$.MODULE$.iterator$extension(Predef$.MODULE$.refArrayOps(elems)), d);
                WildcardTree<T> x$2 = this.copy$default$1();
                return this.copy(x$2, x$1);
            }
        }
        if (bl) {
            return this.copy(this.wildcardTree().insert(ArrayOps$.MODULE$.iterator$extension(Predef$.MODULE$.refArrayOps(elems)), d), this.copy$default$2());
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public Option<T> find(Iterable<String> elems) {
        void var2_2;
        WildcardTree<T> withSingleWildcard;
        Object object = this.wildcardTree().isEmpty() ? (this.doubleWildcardTree().isEmpty() ? WildcardTree$.MODULE$.apply() : this.doubleWildcardTree().findWithTerminalDoubleWildcard(elems.iterator(), this.doubleWildcardTree().findWithTerminalDoubleWildcard$default$2())) : ((withSingleWildcard = this.wildcardTree().findWithSingleWildcard(elems.iterator())).isEmpty() ? this.doubleWildcardTree().findWithTerminalDoubleWildcard(elems.iterator(), this.doubleWildcardTree().findWithTerminalDoubleWildcard$default$2()) : var2_2);
        return object.data();
    }

    public boolean isEmpty() {
        return this.wildcardTree().isEmpty() && this.doubleWildcardTree().isEmpty();
    }

    public <T> WildcardIndex<T> copy(WildcardTree<T> wildcardTree, WildcardTree<T> doubleWildcardTree) {
        return new WildcardIndex<T>(wildcardTree, doubleWildcardTree);
    }

    public <T> WildcardTree<T> copy$default$1() {
        return this.wildcardTree();
    }

    public <T> WildcardTree<T> copy$default$2() {
        return this.doubleWildcardTree();
    }

    @Override
    public String productPrefix() {
        return "WildcardIndex";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.wildcardTree();
            }
            case 1: {
                return this.doubleWildcardTree();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof WildcardIndex;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "wildcardTree";
            }
            case 1: {
                return "doubleWildcardTree";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof WildcardIndex)) return false;
        boolean bl = true;
        if (!bl) return false;
        WildcardIndex wildcardIndex = (WildcardIndex)x$1;
        WildcardTree<T> wildcardTree = this.wildcardTree();
        WildcardTree<T> wildcardTree2 = wildcardIndex.wildcardTree();
        if (wildcardTree == null) {
            if (wildcardTree2 != null) {
                return false;
            }
        } else if (!((Object)wildcardTree).equals(wildcardTree2)) return false;
        WildcardTree<T> wildcardTree3 = this.doubleWildcardTree();
        WildcardTree<T> wildcardTree4 = wildcardIndex.doubleWildcardTree();
        if (wildcardTree3 == null) {
            if (wildcardTree4 == null) return true;
            return false;
        } else {
            if (!((Object)wildcardTree3).equals(wildcardTree4)) return false;
            return true;
        }
    }

    public WildcardIndex(WildcardTree<T> wildcardTree, WildcardTree<T> doubleWildcardTree) {
        this.wildcardTree = wildcardTree;
        this.doubleWildcardTree = doubleWildcardTree;
        Product.$init$(this);
    }
}

