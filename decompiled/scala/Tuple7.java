/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product7;
import scala.Tuple7$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\tMf\u0001\u0002\u0014(\u0005*B\u0001b\u0018\u0001\u0003\u0016\u0004%\t\u0001\u0019\u0005\tC\u0002\u0011\t\u0012)A\u0005g!A!\r\u0001BK\u0002\u0013\u00051\r\u0003\u0005e\u0001\tE\t\u0015!\u0003?\u0011!)\u0007A!f\u0001\n\u00031\u0007\u0002C4\u0001\u0005#\u0005\u000b\u0011B!\t\u0011!\u0004!Q3A\u0005\u0002%D\u0001B\u001b\u0001\u0003\u0012\u0003\u0006I\u0001\u0012\u0005\tW\u0002\u0011)\u001a!C\u0001Y\"AQ\u000e\u0001B\tB\u0003%q\t\u0003\u0005o\u0001\tU\r\u0011\"\u0001p\u0011!\u0001\bA!E!\u0002\u0013Q\u0005\u0002C9\u0001\u0005+\u0007I\u0011\u0001:\t\u0011M\u0004!\u0011#Q\u0001\n5CQ\u0001\u001e\u0001\u0005\u0002UDQA \u0001\u0005B}D\u0011\"!\u0005\u0001\u0003\u0003%\t!a\u0005\t\u0013\u0005\r\u0003!%A\u0005\u0002\u0005\u0015\u0003\"CA6\u0001E\u0005I\u0011AA7\u0011%\t\t\tAI\u0001\n\u0003\t\u0019\tC\u0005\u0002\u0018\u0002\t\n\u0011\"\u0001\u0002\u001a\"I\u0011Q\u0016\u0001\u0012\u0002\u0013\u0005\u0011q\u0016\u0005\n\u0003\u0007\u0004\u0011\u0013!C\u0001\u0003\u000bD\u0011\"!7\u0001#\u0003%\t!a7\t\u0013\u0005=\b!!A\u0005B\u0005E\b\"\u0003B\u0001\u0001\u0005\u0005I\u0011\tB\u0002\u0011%\u0011\t\u0002AA\u0001\n\u0003\u0011\u0019\u0002C\u0005\u0003 \u0001\t\t\u0011\"\u0011\u0003\"!I!1\u0006\u0001\u0002\u0002\u0013\u0005#Q\u0006\u0005\n\u0005_\u0001\u0011\u0011!C!\u0005c9\u0011B!\u000e(\u0003\u0003E\tAa\u000e\u0007\u0011\u0019:\u0013\u0011!E\u0001\u0005sAa\u0001\u001e\u0011\u0005\u0002\t\u0015\u0003\u0002\u0003@!\u0003\u0003%)Ea\u0012\t\u0013\t%\u0003%!A\u0005\u0002\n-\u0003\"\u0003B>A\u0005\u0005I\u0011\u0011B?\u0011%\u0011I\u000bIA\u0001\n\u0013\u0011YK\u0001\u0004UkBdWm\u000e\u0006\u0002Q\u0005)1oY1mC\u000e\u0001Q\u0003C\u00166\u007f\t+\u0005j\u0013(\u0014\u000b\u0001a\u0003\u0007U*\u0011\u00055rS\"A\u0014\n\u0005=:#AB!osJ+g\rE\u0005.cMr\u0014\tR$K\u001b&\u0011!g\n\u0002\t!J|G-^2uoA\u0011A'\u000e\u0007\u0001\t\u00191\u0004\u0001\"b\u0001o\t\u0011A+M\t\u0003qm\u0002\"!L\u001d\n\u0005i:#a\u0002(pi\"Lgn\u001a\t\u0003[qJ!!P\u0014\u0003\u0007\u0005s\u0017\u0010\u0005\u00025\u007f\u00111\u0001\t\u0001CC\u0002]\u0012!\u0001\u0016\u001a\u0011\u0005Q\u0012EAB\"\u0001\t\u000b\u0007qG\u0001\u0002UgA\u0011A'\u0012\u0003\u0007\r\u0002!)\u0019A\u001c\u0003\u0005Q#\u0004C\u0001\u001bI\t\u0019I\u0005\u0001\"b\u0001o\t\u0011A+\u000e\t\u0003i-#a\u0001\u0014\u0001\u0005\u0006\u00049$A\u0001+7!\t!d\n\u0002\u0004P\u0001\u0011\u0015\ra\u000e\u0002\u0003)^\u0002\"!L)\n\u0005I;#a\u0002)s_\u0012,8\r\u001e\t\u0003)rs!!\u0016.\u000f\u0005YKV\"A,\u000b\u0005aK\u0013A\u0002\u001fs_>$h(C\u0001)\u0013\tYv%A\u0004qC\u000e\\\u0017mZ3\n\u0005us&\u0001D*fe&\fG.\u001b>bE2,'BA.(\u0003\ty\u0016'F\u00014\u0003\ry\u0016\u0007I\u0001\u0003?J*\u0012AP\u0001\u0004?J\u0002\u0013AA04+\u0005\t\u0015aA04A\u0005\u0011q\fN\u000b\u0002\t\u0006\u0019q\f\u000e\u0011\u0002\u0005}+T#A$\u0002\u0007}+\u0004%\u0001\u0002`mU\t!*A\u0002`m\u0001\n!aX\u001c\u0016\u00035\u000b1aX\u001c!\u0003\u0019a\u0014N\\5u}QAao\u001e=zundX\u0010E\u0005.\u0001Mr\u0014\tR$K\u001b\")ql\u0004a\u0001g!)!m\u0004a\u0001}!)Qm\u0004a\u0001\u0003\")\u0001n\u0004a\u0001\t\")1n\u0004a\u0001\u000f\")an\u0004a\u0001\u0015\")\u0011o\u0004a\u0001\u001b\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0002A!\u00111AA\u0006\u001d\u0011\t)!a\u0002\u0011\u0005Y;\u0013bAA\u0005O\u00051\u0001K]3eK\u001aLA!!\u0004\u0002\u0010\t11\u000b\u001e:j]\u001eT1!!\u0003(\u0003\u0011\u0019w\u000e]=\u0016!\u0005U\u00111DA\u0010\u0003G\t9#a\u000b\u00020\u0005MB\u0003EA\f\u0003k\t9$!\u000f\u0002<\u0005u\u0012qHA!!Ai\u0003!!\u0007\u0002\u001e\u0005\u0005\u0012QEA\u0015\u0003[\t\t\u0004E\u00025\u00037!QAN\tC\u0002]\u00022\u0001NA\u0010\t\u0015\u0001\u0015C1\u00018!\r!\u00141\u0005\u0003\u0006\u0007F\u0011\ra\u000e\t\u0004i\u0005\u001dB!\u0002$\u0012\u0005\u00049\u0004c\u0001\u001b\u0002,\u0011)\u0011*\u0005b\u0001oA\u0019A'a\f\u0005\u000b1\u000b\"\u0019A\u001c\u0011\u0007Q\n\u0019\u0004B\u0003P#\t\u0007q\u0007\u0003\u0005`#A\u0005\t\u0019AA\r\u0011!\u0011\u0017\u0003%AA\u0002\u0005u\u0001\u0002C3\u0012!\u0003\u0005\r!!\t\t\u0011!\f\u0002\u0013!a\u0001\u0003KA\u0001b[\t\u0011\u0002\u0003\u0007\u0011\u0011\u0006\u0005\t]F\u0001\n\u00111\u0001\u0002.!A\u0011/\u0005I\u0001\u0002\u0004\t\t$\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016!\u0005\u001d\u0013QLA0\u0003C\n\u0019'!\u001a\u0002h\u0005%TCAA%U\r\u0019\u00141J\u0016\u0003\u0003\u001b\u0002B!a\u0014\u0002Z5\u0011\u0011\u0011\u000b\u0006\u0005\u0003'\n)&A\u0005v]\u000eDWmY6fI*\u0019\u0011qK\u0014\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\\\u0005E#!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)aG\u0005b\u0001o\u0011)\u0001I\u0005b\u0001o\u0011)1I\u0005b\u0001o\u0011)aI\u0005b\u0001o\u0011)\u0011J\u0005b\u0001o\u0011)AJ\u0005b\u0001o\u0011)qJ\u0005b\u0001o\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T\u0003EA8\u0003g\n)(a\u001e\u0002z\u0005m\u0014QPA@+\t\t\tHK\u0002?\u0003\u0017\"QAN\nC\u0002]\"Q\u0001Q\nC\u0002]\"QaQ\nC\u0002]\"QAR\nC\u0002]\"Q!S\nC\u0002]\"Q\u0001T\nC\u0002]\"QaT\nC\u0002]\nabY8qs\u0012\"WMZ1vYR$3'\u0006\t\u0002\u0006\u0006%\u00151RAG\u0003\u001f\u000b\t*a%\u0002\u0016V\u0011\u0011q\u0011\u0016\u0004\u0003\u0006-C!\u0002\u001c\u0015\u0005\u00049D!\u0002!\u0015\u0005\u00049D!B\"\u0015\u0005\u00049D!\u0002$\u0015\u0005\u00049D!B%\u0015\u0005\u00049D!\u0002'\u0015\u0005\u00049D!B(\u0015\u0005\u00049\u0014AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0011\u00037\u000by*!)\u0002$\u0006\u0015\u0016qUAU\u0003W+\"!!(+\u0007\u0011\u000bY\u0005B\u00037+\t\u0007q\u0007B\u0003A+\t\u0007q\u0007B\u0003D+\t\u0007q\u0007B\u0003G+\t\u0007q\u0007B\u0003J+\t\u0007q\u0007B\u0003M+\t\u0007q\u0007B\u0003P+\t\u0007q'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016!\u0005E\u0016QWA\\\u0003s\u000bY,!0\u0002@\u0006\u0005WCAAZU\r9\u00151\n\u0003\u0006mY\u0011\ra\u000e\u0003\u0006\u0001Z\u0011\ra\u000e\u0003\u0006\u0007Z\u0011\ra\u000e\u0003\u0006\rZ\u0011\ra\u000e\u0003\u0006\u0013Z\u0011\ra\u000e\u0003\u0006\u0019Z\u0011\ra\u000e\u0003\u0006\u001fZ\u0011\raN\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+A\t9-a3\u0002N\u0006=\u0017\u0011[Aj\u0003+\f9.\u0006\u0002\u0002J*\u001a!*a\u0013\u0005\u000bY:\"\u0019A\u001c\u0005\u000b\u0001;\"\u0019A\u001c\u0005\u000b\r;\"\u0019A\u001c\u0005\u000b\u0019;\"\u0019A\u001c\u0005\u000b%;\"\u0019A\u001c\u0005\u000b1;\"\u0019A\u001c\u0005\u000b=;\"\u0019A\u001c\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%oU\u0001\u0012Q\\Aq\u0003G\f)/a:\u0002j\u0006-\u0018Q^\u000b\u0003\u0003?T3!TA&\t\u00151\u0004D1\u00018\t\u0015\u0001\u0005D1\u00018\t\u0015\u0019\u0005D1\u00018\t\u00151\u0005D1\u00018\t\u0015I\u0005D1\u00018\t\u0015a\u0005D1\u00018\t\u0015y\u0005D1\u00018\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u001f\t\u0005\u0003k\fy0\u0004\u0002\u0002x*!\u0011\u0011`A~\u0003\u0011a\u0017M\\4\u000b\u0005\u0005u\u0018\u0001\u00026bm\u0006LA!!\u0004\u0002x\u0006y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0003\u0006A)!q\u0001B\u0007w5\u0011!\u0011\u0002\u0006\u0004\u0005\u00179\u0013AC2pY2,7\r^5p]&!!q\u0002B\u0005\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\tU!1\u0004\t\u0004[\t]\u0011b\u0001B\rO\t9!i\\8mK\u0006t\u0007\u0002\u0003B\u000f7\u0005\u0005\t\u0019A\u001e\u0002\u0007a$\u0013'\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BAz\u0005GA\u0011B!\b\u001d\u0003\u0003\u0005\rA!\n\u0011\u00075\u00129#C\u0002\u0003*\u001d\u00121!\u00138u\u0003!A\u0017m\u001d5D_\u0012,GC\u0001B\u0013\u0003\u0019)\u0017/^1mgR!!Q\u0003B\u001a\u0011!\u0011iBHA\u0001\u0002\u0004Y\u0014A\u0002+va2,w\u0007\u0005\u0002.AM!\u0001\u0005\fB\u001e!\u0011\u0011iDa\u0011\u000e\u0005\t}\"\u0002\u0002B!\u0003w\f!![8\n\u0007u\u0013y\u0004\u0006\u0002\u00038Q\u0011\u00111_\u0001\u0006CB\u0004H._\u000b\u0011\u0005\u001b\u0012\u0019Fa\u0016\u0003\\\t}#1\rB4\u0005W\"\u0002Ca\u0014\u0003n\t=$\u0011\u000fB:\u0005k\u00129H!\u001f\u0011!5\u0002!\u0011\u000bB+\u00053\u0012iF!\u0019\u0003f\t%\u0004c\u0001\u001b\u0003T\u0011)ag\tb\u0001oA\u0019AGa\u0016\u0005\u000b\u0001\u001b#\u0019A\u001c\u0011\u0007Q\u0012Y\u0006B\u0003DG\t\u0007q\u0007E\u00025\u0005?\"QAR\u0012C\u0002]\u00022\u0001\u000eB2\t\u0015I5E1\u00018!\r!$q\r\u0003\u0006\u0019\u000e\u0012\ra\u000e\t\u0004i\t-D!B($\u0005\u00049\u0004BB0$\u0001\u0004\u0011\t\u0006\u0003\u0004cG\u0001\u0007!Q\u000b\u0005\u0007K\u000e\u0002\rA!\u0017\t\r!\u001c\u0003\u0019\u0001B/\u0011\u0019Y7\u00051\u0001\u0003b!1an\ta\u0001\u0005KBa!]\u0012A\u0002\t%\u0014aB;oCB\u0004H._\u000b\u0011\u0005\u007f\u0012YIa$\u0003\u0014\n]%1\u0014BP\u0005G#BA!!\u0003&B)QFa!\u0003\b&\u0019!QQ\u0014\u0003\r=\u0003H/[8o!Ai\u0003A!#\u0003\u000e\nE%Q\u0013BM\u0005;\u0013\t\u000bE\u00025\u0005\u0017#QA\u000e\u0013C\u0002]\u00022\u0001\u000eBH\t\u0015\u0001EE1\u00018!\r!$1\u0013\u0003\u0006\u0007\u0012\u0012\ra\u000e\t\u0004i\t]E!\u0002$%\u0005\u00049\u0004c\u0001\u001b\u0003\u001c\u0012)\u0011\n\nb\u0001oA\u0019AGa(\u0005\u000b1##\u0019A\u001c\u0011\u0007Q\u0012\u0019\u000bB\u0003PI\t\u0007q\u0007C\u0005\u0003(\u0012\n\t\u00111\u0001\u0003\b\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t5\u0006\u0003BA{\u0005_KAA!-\u0002x\n1qJ\u00196fGR\u0004")
public final class Tuple7<T1, T2, T3, T4, T5, T6, T7>
implements Product7<T1, T2, T3, T4, T5, T6, T7>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;
    private final T5 _5;
    private final T6 _6;
    private final T7 _7;

    public static <T1, T2, T3, T4, T5, T6, T7> Option<Tuple7<T1, T2, T3, T4, T5, T6, T7>> unapply(Tuple7<T1, T2, T3, T4, T5, T6, T7> x$0) {
        return Tuple7$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(_1, _2, _3, _4, _5, _6, _7);
    }

    @Override
    public int productArity() {
        return Product7.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product7.productElement$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public T1 _1() {
        return this._1;
    }

    @Override
    public T2 _2() {
        return this._2;
    }

    @Override
    public T3 _3() {
        return this._3;
    }

    @Override
    public T4 _4() {
        return this._4;
    }

    @Override
    public T5 _5() {
        return this._5;
    }

    @Override
    public T6 _6() {
        return this._6;
    }

    @Override
    public T7 _7() {
        return this._7;
    }

    public String toString() {
        return new StringBuilder(8).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(_1, _2, _3, _4, _5, _6, _7);
    }

    public <T1, T2, T3, T4, T5, T6, T7> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7> T7 copy$default$7() {
        return this._7();
    }

    @Override
    public String productPrefix() {
        return "Tuple7";
    }

    @Override
    public Iterator<Object> productIterator() {
        return new AbstractIterator<T>(this){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Tuple7;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "_1";
            }
            case 1: {
                return "_2";
            }
            case 2: {
                return "_3";
            }
            case 3: {
                return "_4";
            }
            case 4: {
                return "_5";
            }
            case 5: {
                return "_6";
            }
            case 6: {
                return "_7";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return MurmurHash3$.MODULE$.productHash(this);
    }

    @Override
    public boolean equals(Object x$1) {
        block3: {
            block2: {
                if (this == x$1) break block2;
                if (!(x$1 instanceof Tuple7)) break block3;
                Tuple7 tuple7 = (Tuple7)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple7._1()) || !BoxesRunTime.equals(this._2(), tuple7._2()) || !BoxesRunTime.equals(this._3(), tuple7._3()) || !BoxesRunTime.equals(this._4(), tuple7._4()) || !BoxesRunTime.equals(this._5(), tuple7._5()) || !BoxesRunTime.equals(this._6(), tuple7._6()) || !BoxesRunTime.equals(this._7(), tuple7._7())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple7(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
    }
}

