/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product6;
import scala.Tuple6$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\t5d\u0001B\u0012%\u0005\u001eB\u0001\"\u0017\u0001\u0003\u0016\u0004%\tA\u0017\u0005\t7\u0002\u0011\t\u0012)A\u0005a!AA\f\u0001BK\u0002\u0013\u0005Q\f\u0003\u0005_\u0001\tE\t\u0015!\u0003<\u0011!y\u0006A!f\u0001\n\u0003\u0001\u0007\u0002C1\u0001\u0005#\u0005\u000b\u0011\u0002 \t\u0011\t\u0004!Q3A\u0005\u0002\rD\u0001\u0002\u001a\u0001\u0003\u0012\u0003\u0006I!\u0011\u0005\tK\u0002\u0011)\u001a!C\u0001M\"Aq\r\u0001B\tB\u0003%A\t\u0003\u0005i\u0001\tU\r\u0011\"\u0001j\u0011!Q\u0007A!E!\u0002\u00139\u0005\"B6\u0001\t\u0003a\u0007\"\u0002;\u0001\t\u0003*\bb\u0002@\u0001\u0003\u0003%\ta \u0005\n\u0003S\u0001\u0011\u0013!C\u0001\u0003WA\u0011\"a\u0014\u0001#\u0003%\t!!\u0015\t\u0013\u0005\r\u0004!%A\u0005\u0002\u0005\u0015\u0004\"CA<\u0001E\u0005I\u0011AA=\u0011%\tY\tAI\u0001\n\u0003\ti\tC\u0005\u0002 \u0002\t\n\u0011\"\u0001\u0002\"\"I\u00111\u0017\u0001\u0002\u0002\u0013\u0005\u0013Q\u0017\u0005\n\u0003\u000b\u0004\u0011\u0011!C!\u0003\u000fD\u0011\"!6\u0001\u0003\u0003%\t!a6\t\u0013\u0005\r\b!!A\u0005B\u0005\u0015\b\"CAx\u0001\u0005\u0005I\u0011IAy\u0011%\t\u0019\u0010AA\u0001\n\u0003\n)pB\u0005\u0002z\u0012\n\t\u0011#\u0001\u0002|\u001aA1\u0005JA\u0001\u0012\u0003\ti\u0010\u0003\u0004l;\u0011\u0005!\u0011\u0002\u0005\tiv\t\t\u0011\"\u0012\u0003\f!I!QB\u000f\u0002\u0002\u0013\u0005%q\u0002\u0005\n\u0005si\u0012\u0011!CA\u0005wA\u0011Ba\u0019\u001e\u0003\u0003%IA!\u001a\u0003\rQ+\b\u000f\\37\u0015\u0005)\u0013!B:dC2\f7\u0001A\u000b\bQIbtHQ#I'\u0015\u0001\u0011&\f&N!\tQ3&D\u0001%\u0013\taCE\u0001\u0004B]f\u0014VM\u001a\t\tU9\u00024HP!E\u000f&\u0011q\u0006\n\u0002\t!J|G-^2umA\u0011\u0011G\r\u0007\u0001\t\u0019\u0019\u0004\u0001\"b\u0001i\t\u0011A+M\t\u0003ka\u0002\"A\u000b\u001c\n\u0005]\"#a\u0002(pi\"Lgn\u001a\t\u0003UeJ!A\u000f\u0013\u0003\u0007\u0005s\u0017\u0010\u0005\u00022y\u00111Q\b\u0001CC\u0002Q\u0012!\u0001\u0016\u001a\u0011\u0005EzDA\u0002!\u0001\t\u000b\u0007AG\u0001\u0002UgA\u0011\u0011G\u0011\u0003\u0007\u0007\u0002!)\u0019\u0001\u001b\u0003\u0005Q#\u0004CA\u0019F\t\u00191\u0005\u0001\"b\u0001i\t\u0011A+\u000e\t\u0003c!#a!\u0013\u0001\u0005\u0006\u0004!$A\u0001+7!\tQ3*\u0003\u0002MI\t9\u0001K]8ek\u000e$\bC\u0001(W\u001d\tyEK\u0004\u0002Q'6\t\u0011K\u0003\u0002SM\u00051AH]8pizJ\u0011!J\u0005\u0003+\u0012\nq\u0001]1dW\u0006<W-\u0003\u0002X1\na1+\u001a:jC2L'0\u00192mK*\u0011Q\u000bJ\u0001\u0003?F*\u0012\u0001M\u0001\u0004?F\u0002\u0013AA03+\u0005Y\u0014aA03A\u0005\u0011qlM\u000b\u0002}\u0005\u0019ql\r\u0011\u0002\u0005}#T#A!\u0002\u0007}#\u0004%\u0001\u0002`kU\tA)A\u0002`k\u0001\n!a\u0018\u001c\u0016\u0003\u001d\u000b1a\u0018\u001c!\u0003\u0019a\u0014N\\5u}Q9QN\\8qcJ\u001c\b\u0003\u0003\u0016\u0001amr\u0014\tR$\t\u000bek\u0001\u0019\u0001\u0019\t\u000bqk\u0001\u0019A\u001e\t\u000b}k\u0001\u0019\u0001 \t\u000b\tl\u0001\u0019A!\t\u000b\u0015l\u0001\u0019\u0001#\t\u000b!l\u0001\u0019A$\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012A\u001e\t\u0003ont!\u0001_=\u0011\u0005A#\u0013B\u0001>%\u0003\u0019\u0001&/\u001a3fM&\u0011A0 \u0002\u0007'R\u0014\u0018N\\4\u000b\u0005i$\u0013\u0001B2paf,b\"!\u0001\u0002\b\u0005-\u0011qBA\n\u0003/\tY\u0002\u0006\b\u0002\u0004\u0005u\u0011qDA\u0011\u0003G\t)#a\n\u0011\u001d)\u0002\u0011QAA\u0005\u0003\u001b\t\t\"!\u0006\u0002\u001aA\u0019\u0011'a\u0002\u0005\u000bMz!\u0019\u0001\u001b\u0011\u0007E\nY\u0001B\u0003>\u001f\t\u0007A\u0007E\u00022\u0003\u001f!Q\u0001Q\bC\u0002Q\u00022!MA\n\t\u0015\u0019uB1\u00015!\r\t\u0014q\u0003\u0003\u0006\r>\u0011\r\u0001\u000e\t\u0004c\u0005mA!B%\u0010\u0005\u0004!\u0004\u0002C-\u0010!\u0003\u0005\r!!\u0002\t\u0011q{\u0001\u0013!a\u0001\u0003\u0013A\u0001bX\b\u0011\u0002\u0003\u0007\u0011Q\u0002\u0005\tE>\u0001\n\u00111\u0001\u0002\u0012!AQm\u0004I\u0001\u0002\u0004\t)\u0002\u0003\u0005i\u001fA\u0005\t\u0019AA\r\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*b\"!\f\u0002D\u0005\u0015\u0013qIA%\u0003\u0017\ni%\u0006\u0002\u00020)\u001a\u0001'!\r,\u0005\u0005M\u0002\u0003BA\u001b\u0003\u007fi!!a\u000e\u000b\t\u0005e\u00121H\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u0010%\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u0003\n9DA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Qa\r\tC\u0002Q\"Q!\u0010\tC\u0002Q\"Q\u0001\u0011\tC\u0002Q\"Qa\u0011\tC\u0002Q\"QA\u0012\tC\u0002Q\"Q!\u0013\tC\u0002Q\nabY8qs\u0012\"WMZ1vYR$#'\u0006\b\u0002T\u0005]\u0013\u0011LA.\u0003;\ny&!\u0019\u0016\u0005\u0005U#fA\u001e\u00022\u0011)1'\u0005b\u0001i\u0011)Q(\u0005b\u0001i\u0011)\u0001)\u0005b\u0001i\u0011)1)\u0005b\u0001i\u0011)a)\u0005b\u0001i\u0011)\u0011*\u0005b\u0001i\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTCDA4\u0003W\ni'a\u001c\u0002r\u0005M\u0014QO\u000b\u0003\u0003SR3APA\u0019\t\u0015\u0019$C1\u00015\t\u0015i$C1\u00015\t\u0015\u0001%C1\u00015\t\u0015\u0019%C1\u00015\t\u00151%C1\u00015\t\u0015I%C1\u00015\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*b\"a\u001f\u0002\u0000\u0005\u0005\u00151QAC\u0003\u000f\u000bI)\u0006\u0002\u0002~)\u001a\u0011)!\r\u0005\u000bM\u001a\"\u0019\u0001\u001b\u0005\u000bu\u001a\"\u0019\u0001\u001b\u0005\u000b\u0001\u001b\"\u0019\u0001\u001b\u0005\u000b\r\u001b\"\u0019\u0001\u001b\u0005\u000b\u0019\u001b\"\u0019\u0001\u001b\u0005\u000b%\u001b\"\u0019\u0001\u001b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%kUq\u0011qRAJ\u0003+\u000b9*!'\u0002\u001c\u0006uUCAAIU\r!\u0015\u0011\u0007\u0003\u0006gQ\u0011\r\u0001\u000e\u0003\u0006{Q\u0011\r\u0001\u000e\u0003\u0006\u0001R\u0011\r\u0001\u000e\u0003\u0006\u0007R\u0011\r\u0001\u000e\u0003\u0006\rR\u0011\r\u0001\u000e\u0003\u0006\u0013R\u0011\r\u0001N\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+9\t\u0019+a*\u0002*\u0006-\u0016QVAX\u0003c+\"!!*+\u0007\u001d\u000b\t\u0004B\u00034+\t\u0007A\u0007B\u0003>+\t\u0007A\u0007B\u0003A+\t\u0007A\u0007B\u0003D+\t\u0007A\u0007B\u0003G+\t\u0007A\u0007B\u0003J+\t\u0007A'A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003o\u0003B!!/\u0002D6\u0011\u00111\u0018\u0006\u0005\u0003{\u000by,\u0001\u0003mC:<'BAAa\u0003\u0011Q\u0017M^1\n\u0007q\fY,A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\tI\rE\u0003\u0002L\u0006E\u0007(\u0004\u0002\u0002N*\u0019\u0011q\u001a\u0013\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002T\u00065'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!7\u0002`B\u0019!&a7\n\u0007\u0005uGEA\u0004C_>dW-\u00198\t\u0011\u0005\u0005\b$!AA\u0002a\n1\u0001\u001f\u00132\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005]\u0016q\u001d\u0005\n\u0003CL\u0012\u0011!a\u0001\u0003S\u00042AKAv\u0013\r\ti\u000f\n\u0002\u0004\u0013:$\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005%\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0002Z\u0006]\b\u0002CAq7\u0005\u0005\t\u0019\u0001\u001d\u0002\rQ+\b\u000f\\37!\tQSd\u0005\u0003\u001eS\u0005}\b\u0003\u0002B\u0001\u0005\u000fi!Aa\u0001\u000b\t\t\u0015\u0011qX\u0001\u0003S>L1a\u0016B\u0002)\t\tY\u0010\u0006\u0002\u00028\u0006)\u0011\r\u001d9msVq!\u0011\u0003B\f\u00057\u0011yBa\t\u0003(\t-BC\u0004B\n\u0005[\u0011yC!\r\u00034\tU\"q\u0007\t\u000fU\u0001\u0011)B!\u0007\u0003\u001e\t\u0005\"Q\u0005B\u0015!\r\t$q\u0003\u0003\u0006g\u0001\u0012\r\u0001\u000e\t\u0004c\tmA!B\u001f!\u0005\u0004!\u0004cA\u0019\u0003 \u0011)\u0001\t\tb\u0001iA\u0019\u0011Ga\t\u0005\u000b\r\u0003#\u0019\u0001\u001b\u0011\u0007E\u00129\u0003B\u0003GA\t\u0007A\u0007E\u00022\u0005W!Q!\u0013\u0011C\u0002QBa!\u0017\u0011A\u0002\tU\u0001B\u0002/!\u0001\u0004\u0011I\u0002\u0003\u0004`A\u0001\u0007!Q\u0004\u0005\u0007E\u0002\u0002\rA!\t\t\r\u0015\u0004\u0003\u0019\u0001B\u0013\u0011\u0019A\u0007\u00051\u0001\u0003*\u00059QO\\1qa2LXC\u0004B\u001f\u0005\u0013\u0012iE!\u0015\u0003V\te#Q\f\u000b\u0005\u0005\u007f\u0011y\u0006E\u0003+\u0005\u0003\u0012)%C\u0002\u0003D\u0011\u0012aa\u00149uS>t\u0007C\u0004\u0016\u0001\u0005\u000f\u0012YEa\u0014\u0003T\t]#1\f\t\u0004c\t%C!B\u001a\"\u0005\u0004!\u0004cA\u0019\u0003N\u0011)Q(\tb\u0001iA\u0019\u0011G!\u0015\u0005\u000b\u0001\u000b#\u0019\u0001\u001b\u0011\u0007E\u0012)\u0006B\u0003DC\t\u0007A\u0007E\u00022\u00053\"QAR\u0011C\u0002Q\u00022!\rB/\t\u0015I\u0015E1\u00015\u0011%\u0011\t'IA\u0001\u0002\u0004\u0011)%A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa\u001a\u0011\t\u0005e&\u0011N\u0005\u0005\u0005W\nYL\u0001\u0004PE*,7\r\u001e")
public final class Tuple6<T1, T2, T3, T4, T5, T6>
implements Product6<T1, T2, T3, T4, T5, T6>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;
    private final T5 _5;
    private final T6 _6;

    public static <T1, T2, T3, T4, T5, T6> Option<Tuple6<T1, T2, T3, T4, T5, T6>> unapply(Tuple6<T1, T2, T3, T4, T5, T6> x$0) {
        return Tuple6$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        return new Tuple6<T1, T2, T3, T4, T5, T6>(_1, _2, _3, _4, _5, _6);
    }

    @Override
    public int productArity() {
        return Product6.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product6.productElement$(this, n);
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

    public String toString() {
        return new StringBuilder(7).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        return new Tuple6<T1, T2, T3, T4, T5, T6>(_1, _2, _3, _4, _5, _6);
    }

    public <T1, T2, T3, T4, T5, T6> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6> T6 copy$default$6() {
        return this._6();
    }

    @Override
    public String productPrefix() {
        return "Tuple6";
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
        return x$1 instanceof Tuple6;
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
                if (!(x$1 instanceof Tuple6)) break block3;
                Tuple6 tuple6 = (Tuple6)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple6._1()) || !BoxesRunTime.equals(this._2(), tuple6._2()) || !BoxesRunTime.equals(this._3(), tuple6._3()) || !BoxesRunTime.equals(this._4(), tuple6._4()) || !BoxesRunTime.equals(this._5(), tuple6._5()) || !BoxesRunTime.equals(this._6(), tuple6._6())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple6(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
    }
}

