/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015<Q\u0001D\u0007\t\u0002A1QAE\u0007\t\u0002MAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002i1qAE\u0007\u0011\u0002\u0007\u0005\u0001\u0005C\u0003)\t\u0011\u0005\u0011\u0006C\u0003.\t\u0011\u0005c\u0006C\u00033\t\u0011\u00053\u0007C\u0003C\t\u0019\u00051\tC\u0003M\t\u0019\u0005Q\nC\u0003R\t\u0019\u0005!\u000bC\u0003W\t\u0019\u0005q+\u0001\u0005Qe>$Wo\u0019;5\u0015\u0005q\u0011!B:dC2\f7\u0001\u0001\t\u0003#\u0005i\u0011!\u0004\u0002\t!J|G-^2uiM\u0011\u0011\u0001\u0006\t\u0003#UI!AF\u0007\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t\u0001#A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u000bmaf\f\u00192\u0015\u0005q\u0019\u0007cA\t\u001e?%\u0011a$\u0004\u0002\u0007\u001fB$\u0018n\u001c8\u0011\rE!1,X0b+\u0015\tci\u0014+Z'\r!!%\n\t\u0003#\rJ!\u0001J\u0007\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0012M%\u0011q%\u0004\u0002\b!J|G-^2u\u0003\u0019!\u0013N\\5uIQ\t!\u0006\u0005\u0002\u0012W%\u0011A&\u0004\u0002\u0005+:LG/\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u00010!\t\t\u0002'\u0003\u00022\u001b\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011!\u0005\u000e\u0005\u0006k\u001d\u0001\raL\u0001\u0002]\"\u001aqaN!\u0011\u0007EA$(\u0003\u0002:\u001b\t1A\u000f\u001b:poN\u0004\"a\u000f \u000f\u0005Ea\u0014BA\u001f\u000e\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0010!\u00033%sG-\u001a=PkR|eMQ8v]\u0012\u001cX\t_2faRLwN\u001c\u0006\u0003{5\u0019\u0013AO\u0001\u0003?F*\u0012\u0001\u0012\t\u0003\u000b\u001ac\u0001\u0001\u0002\u0004H\t\u0011\u0015\r\u0001\u0013\u0002\u0003)F\n\"!\u0013\u0012\u0011\u0005EQ\u0015BA&\u000e\u0005\u001dqu\u000e\u001e5j]\u001e\f!a\u0018\u001a\u0016\u00039\u0003\"!R(\u0005\rA#AQ1\u0001I\u0005\t!&'\u0001\u0002`gU\t1\u000b\u0005\u0002F)\u00121Q\u000b\u0002CC\u0002!\u0013!\u0001V\u001a\u0002\u0005}#T#\u0001-\u0011\u0005\u0015KFA\u0002.\u0005\t\u000b\u0007\u0001J\u0001\u0002UiA\u0011Q\t\u0018\u0003\u0006\u000f\u000e\u0011\r\u0001\u0013\t\u0003\u000bz#Q\u0001U\u0002C\u0002!\u0003\"!\u00121\u0005\u000bU\u001b!\u0019\u0001%\u0011\u0005\u0015\u0013G!\u0002.\u0004\u0005\u0004A\u0005\"\u00023\u0004\u0001\u0004y\u0012!\u0001=")
public interface Product4<T1, T2, T3, T4>
extends Product {
    public static <T1, T2, T3, T4> Option<Product4<T1, T2, T3, T4>> unapply(Product4<T1, T2, T3, T4> x) {
        return new Some<Product4<T1, T2, T3, T4>>(x);
    }

    public static /* synthetic */ int productArity$(Product4 $this) {
        return $this.productArity();
    }

    @Override
    default public int productArity() {
        return 4;
    }

    public static /* synthetic */ Object productElement$(Product4 $this, int n) {
        return $this.productElement(n);
    }

    @Override
    default public Object productElement(int n) throws IndexOutOfBoundsException {
        switch (n) {
            case 0: {
                return this._1();
            }
            case 1: {
                return this._2();
            }
            case 2: {
                return this._3();
            }
            case 3: {
                return this._4();
            }
        }
        throw new IndexOutOfBoundsException(new StringBuilder(32).append(n).append(" is out of bounds (min 0, max 3)").toString());
    }

    public T1 _1();

    public T2 _2();

    public T3 _3();

    public T4 _4();

    public static void $init$(Product4 $this) {
    }
}

