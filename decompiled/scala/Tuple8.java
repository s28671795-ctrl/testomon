/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product8;
import scala.Tuple8$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\tuh\u0001B\u0015+\u00056B\u0001\"\u001a\u0001\u0003\u0016\u0004%\tA\u001a\u0005\tO\u0002\u0011\t\u0012)A\u0005m!A\u0001\u000e\u0001BK\u0002\u0013\u0005\u0011\u000e\u0003\u0005k\u0001\tE\t\u0015!\u0003B\u0011!Y\u0007A!f\u0001\n\u0003a\u0007\u0002C7\u0001\u0005#\u0005\u000b\u0011\u0002#\t\u00119\u0004!Q3A\u0005\u0002=D\u0001\u0002\u001d\u0001\u0003\u0012\u0003\u0006Ia\u0012\u0005\tc\u0002\u0011)\u001a!C\u0001e\"A1\u000f\u0001B\tB\u0003%!\n\u0003\u0005u\u0001\tU\r\u0011\"\u0001v\u0011!1\bA!E!\u0002\u0013i\u0005\u0002C<\u0001\u0005+\u0007I\u0011\u0001=\t\u0011e\u0004!\u0011#Q\u0001\nAC\u0001B\u001f\u0001\u0003\u0016\u0004%\ta\u001f\u0005\ty\u0002\u0011\t\u0012)A\u0005'\")Q\u0010\u0001C\u0001}\"9\u0011\u0011\u0003\u0001\u0005B\u0005M\u0001\"CA\u0013\u0001\u0005\u0005I\u0011AA\u0014\u0011%\ti\u0006AI\u0001\n\u0003\ty\u0006C\u0005\u0002\b\u0002\t\n\u0011\"\u0001\u0002\n\"I\u0011q\u0014\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u0015\u0005\n\u0003o\u0003\u0011\u0013!C\u0001\u0003sC\u0011\"a4\u0001#\u0003%\t!!5\t\u0013\u0005\u001d\b!%A\u0005\u0002\u0005%\b\"CA\u0000\u0001E\u0005I\u0011\u0001B\u0001\u0011%\u00119\u0002AI\u0001\n\u0003\u0011I\u0002C\u0005\u00030\u0001\t\t\u0011\"\u0011\u00032!I!\u0011\t\u0001\u0002\u0002\u0013\u0005#1\t\u0005\n\u0005#\u0002\u0011\u0011!C\u0001\u0005'B\u0011Ba\u0018\u0001\u0003\u0003%\tE!\u0019\t\u0013\t-\u0004!!A\u0005B\t5\u0004\"\u0003B8\u0001\u0005\u0005I\u0011\tB9\u000f%\u0011)HKA\u0001\u0012\u0003\u00119H\u0002\u0005*U\u0005\u0005\t\u0012\u0001B=\u0011\u0019i8\u0005\"\u0001\u0003\u0006\"I\u0011\u0011C\u0012\u0002\u0002\u0013\u0015#q\u0011\u0005\n\u0005\u0013\u001b\u0013\u0011!CA\u0005\u0017C\u0011B!1$\u0003\u0003%\tIa1\t\u0013\tM8%!A\u0005\n\tU(A\u0002+va2,\u0007HC\u0001,\u0003\u0015\u00198-\u00197b\u0007\u0001)\u0012B\f\u001dC\u000b\"[e*\u0015+\u0014\u000b\u0001y3GV-\u0011\u0005A\nT\"\u0001\u0016\n\u0005IR#AB!osJ+g\r\u0005\u00061iY\nEi\u0012&N!NK!!\u000e\u0016\u0003\u0011A\u0013x\u000eZ;dib\u0002\"a\u000e\u001d\r\u0001\u00111\u0011\b\u0001CC\u0002i\u0012!\u0001V\u0019\u0012\u0005mr\u0004C\u0001\u0019=\u0013\ti$FA\u0004O_RD\u0017N\\4\u0011\u0005Az\u0014B\u0001!+\u0005\r\te.\u001f\t\u0003o\t#aa\u0011\u0001\u0005\u0006\u0004Q$A\u0001+3!\t9T\t\u0002\u0004G\u0001\u0011\u0015\rA\u000f\u0002\u0003)N\u0002\"a\u000e%\u0005\r%\u0003AQ1\u0001;\u0005\t!F\u0007\u0005\u00028\u0017\u00121A\n\u0001CC\u0002i\u0012!\u0001V\u001b\u0011\u0005]rEAB(\u0001\t\u000b\u0007!H\u0001\u0002UmA\u0011q'\u0015\u0003\u0007%\u0002!)\u0019\u0001\u001e\u0003\u0005Q;\u0004CA\u001cU\t\u0019)\u0006\u0001\"b\u0001u\t\u0011A\u000b\u000f\t\u0003a]K!\u0001\u0017\u0016\u0003\u000fA\u0013x\u000eZ;diB\u0011!L\u0019\b\u00037\u0002t!\u0001X0\u000e\u0003uS!A\u0018\u0017\u0002\rq\u0012xn\u001c;?\u0013\u0005Y\u0013BA1+\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u00193\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005\u0005T\u0013AA02+\u00051\u0014aA02A\u0005\u0011qLM\u000b\u0002\u0003\u0006\u0019qL\r\u0011\u0002\u0005}\u001bT#\u0001#\u0002\u0007}\u001b\u0004%\u0001\u0002`iU\tq)A\u0002`i\u0001\n!aX\u001b\u0016\u0003)\u000b1aX\u001b!\u0003\tyf'F\u0001N\u0003\ryf\u0007I\u0001\u0003?^*\u0012\u0001U\u0001\u0004?^\u0002\u0013AA09+\u0005\u0019\u0016aA09A\u00051A(\u001b8jiz\"\u0012c`A\u0001\u0003\u0007\t)!a\u0002\u0002\n\u0005-\u0011QBA\b!)\u0001\u0004AN!E\u000f*k\u0005k\u0015\u0005\u0006KF\u0001\rA\u000e\u0005\u0006QF\u0001\r!\u0011\u0005\u0006WF\u0001\r\u0001\u0012\u0005\u0006]F\u0001\ra\u0012\u0005\u0006cF\u0001\rA\u0013\u0005\u0006iF\u0001\r!\u0014\u0005\u0006oF\u0001\r\u0001\u0015\u0005\u0006uF\u0001\raU\u0001\ti>\u001cFO]5oOR\u0011\u0011Q\u0003\t\u0005\u0003/\tyB\u0004\u0003\u0002\u001a\u0005m\u0001C\u0001/+\u0013\r\tiBK\u0001\u0007!J,G-\u001a4\n\t\u0005\u0005\u00121\u0005\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005u!&\u0001\u0003d_BLXCEA\u0015\u0003_\t\u0019$a\u000e\u0002<\u0005}\u00121IA$\u0003\u0017\"\"#a\u000b\u0002N\u0005=\u0013\u0011KA*\u0003+\n9&!\u0017\u0002\\A\u0011\u0002\u0007AA\u0017\u0003c\t)$!\u000f\u0002>\u0005\u0005\u0013QIA%!\r9\u0014q\u0006\u0003\u0006sM\u0011\rA\u000f\t\u0004o\u0005MB!B\"\u0014\u0005\u0004Q\u0004cA\u001c\u00028\u0011)ai\u0005b\u0001uA\u0019q'a\u000f\u0005\u000b%\u001b\"\u0019\u0001\u001e\u0011\u0007]\ny\u0004B\u0003M'\t\u0007!\bE\u00028\u0003\u0007\"QaT\nC\u0002i\u00022aNA$\t\u0015\u00116C1\u0001;!\r9\u00141\n\u0003\u0006+N\u0011\rA\u000f\u0005\tKN\u0001\n\u00111\u0001\u0002.!A\u0001n\u0005I\u0001\u0002\u0004\t\t\u0004\u0003\u0005l'A\u0005\t\u0019AA\u001b\u0011!q7\u0003%AA\u0002\u0005e\u0002\u0002C9\u0014!\u0003\u0005\r!!\u0010\t\u0011Q\u001c\u0002\u0013!a\u0001\u0003\u0003B\u0001b^\n\u0011\u0002\u0003\u0007\u0011Q\t\u0005\tuN\u0001\n\u00111\u0001\u0002J\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCEA1\u0003o\nI(a\u001f\u0002~\u0005}\u0014\u0011QAB\u0003\u000b+\"!a\u0019+\u0007Y\n)g\u000b\u0002\u0002hA!\u0011\u0011NA:\u001b\t\tYG\u0003\u0003\u0002n\u0005=\u0014!C;oG\",7m[3e\u0015\r\t\tHK\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA;\u0003W\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015IDC1\u0001;\t\u0015\u0019EC1\u0001;\t\u00151EC1\u0001;\t\u0015IEC1\u0001;\t\u0015aEC1\u0001;\t\u0015yEC1\u0001;\t\u0015\u0011FC1\u0001;\t\u0015)FC1\u0001;\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"#a#\u0002\u0010\u0006E\u00151SAK\u0003/\u000bI*a'\u0002\u001eV\u0011\u0011Q\u0012\u0016\u0004\u0003\u0006\u0015D!B\u001d\u0016\u0005\u0004QD!B\"\u0016\u0005\u0004QD!\u0002$\u0016\u0005\u0004QD!B%\u0016\u0005\u0004QD!\u0002'\u0016\u0005\u0004QD!B(\u0016\u0005\u0004QD!\u0002*\u0016\u0005\u0004QD!B+\u0016\u0005\u0004Q\u0014AD2paf$C-\u001a4bk2$HeM\u000b\u0013\u0003G\u000b9+!+\u0002,\u00065\u0016qVAY\u0003g\u000b),\u0006\u0002\u0002&*\u001aA)!\u001a\u0005\u000be2\"\u0019\u0001\u001e\u0005\u000b\r3\"\u0019\u0001\u001e\u0005\u000b\u00193\"\u0019\u0001\u001e\u0005\u000b%3\"\u0019\u0001\u001e\u0005\u000b13\"\u0019\u0001\u001e\u0005\u000b=3\"\u0019\u0001\u001e\u0005\u000bI3\"\u0019\u0001\u001e\u0005\u000bU3\"\u0019\u0001\u001e\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u00121XA`\u0003\u0003\f\u0019-!2\u0002H\u0006%\u00171ZAg+\t\tiLK\u0002H\u0003K\"Q!O\fC\u0002i\"QaQ\fC\u0002i\"QAR\fC\u0002i\"Q!S\fC\u0002i\"Q\u0001T\fC\u0002i\"QaT\fC\u0002i\"QAU\fC\u0002i\"Q!V\fC\u0002i\nabY8qs\u0012\"WMZ1vYR$S'\u0006\n\u0002T\u0006]\u0017\u0011\\An\u0003;\fy.!9\u0002d\u0006\u0015XCAAkU\rQ\u0015Q\r\u0003\u0006sa\u0011\rA\u000f\u0003\u0006\u0007b\u0011\rA\u000f\u0003\u0006\rb\u0011\rA\u000f\u0003\u0006\u0013b\u0011\rA\u000f\u0003\u0006\u0019b\u0011\rA\u000f\u0003\u0006\u001fb\u0011\rA\u000f\u0003\u0006%b\u0011\rA\u000f\u0003\u0006+b\u0011\rAO\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+I\tY/a<\u0002r\u0006M\u0018Q_A|\u0003s\fY0!@\u0016\u0005\u00055(fA'\u0002f\u0011)\u0011(\u0007b\u0001u\u0011)1)\u0007b\u0001u\u0011)a)\u0007b\u0001u\u0011)\u0011*\u0007b\u0001u\u0011)A*\u0007b\u0001u\u0011)q*\u0007b\u0001u\u0011)!+\u0007b\u0001u\u0011)Q+\u0007b\u0001u\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012:TC\u0005B\u0002\u0005\u000f\u0011IAa\u0003\u0003\u000e\t=!\u0011\u0003B\n\u0005+)\"A!\u0002+\u0007A\u000b)\u0007B\u0003:5\t\u0007!\bB\u0003D5\t\u0007!\bB\u0003G5\t\u0007!\bB\u0003J5\t\u0007!\bB\u0003M5\t\u0007!\bB\u0003P5\t\u0007!\bB\u0003S5\t\u0007!\bB\u0003V5\t\u0007!(\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001d\u0016%\tm!q\u0004B\u0011\u0005G\u0011)Ca\n\u0003*\t-\"QF\u000b\u0003\u0005;Q3aUA3\t\u0015I4D1\u0001;\t\u0015\u00195D1\u0001;\t\u001515D1\u0001;\t\u0015I5D1\u0001;\t\u0015a5D1\u0001;\t\u0015y5D1\u0001;\t\u0015\u00116D1\u0001;\t\u0015)6D1\u0001;\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011!1\u0007\t\u0005\u0005k\u0011y$\u0004\u0002\u00038)!!\u0011\bB\u001e\u0003\u0011a\u0017M\\4\u000b\u0005\tu\u0012\u0001\u00026bm\u0006LA!!\t\u00038\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0003FA)!q\tB'}5\u0011!\u0011\n\u0006\u0004\u0005\u0017R\u0013AC2pY2,7\r^5p]&!!q\nB%\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\tU#1\f\t\u0004a\t]\u0013b\u0001B-U\t9!i\\8mK\u0006t\u0007\u0002\u0003B/=\u0005\u0005\t\u0019\u0001 \u0002\u0007a$\u0013'\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003\u0002B\u001a\u0005GB\u0011B!\u0018 \u0003\u0003\u0005\rA!\u001a\u0011\u0007A\u00129'C\u0002\u0003j)\u00121!\u00138u\u0003!A\u0017m\u001d5D_\u0012,GC\u0001B3\u0003\u0019)\u0017/^1mgR!!Q\u000bB:\u0011!\u0011i&IA\u0001\u0002\u0004q\u0014A\u0002+va2,\u0007\b\u0005\u00021GM!1e\fB>!\u0011\u0011iHa!\u000e\u0005\t}$\u0002\u0002BA\u0005w\t!![8\n\u0007\r\u0014y\b\u0006\u0002\u0003xQ\u0011!1G\u0001\u0006CB\u0004H._\u000b\u0013\u0005\u001b\u0013\u0019Ja&\u0003\u001c\n}%1\u0015BT\u0005W\u0013y\u000b\u0006\n\u0003\u0010\nE&1\u0017B[\u0005o\u0013ILa/\u0003>\n}\u0006C\u0005\u0019\u0001\u0005#\u0013)J!'\u0003\u001e\n\u0005&Q\u0015BU\u0005[\u00032a\u000eBJ\t\u0015IdE1\u0001;!\r9$q\u0013\u0003\u0006\u0007\u001a\u0012\rA\u000f\t\u0004o\tmE!\u0002$'\u0005\u0004Q\u0004cA\u001c\u0003 \u0012)\u0011J\nb\u0001uA\u0019qGa)\u0005\u000b13#\u0019\u0001\u001e\u0011\u0007]\u00129\u000bB\u0003PM\t\u0007!\bE\u00028\u0005W#QA\u0015\u0014C\u0002i\u00022a\u000eBX\t\u0015)fE1\u0001;\u0011\u0019)g\u00051\u0001\u0003\u0012\"1\u0001N\na\u0001\u0005+Caa\u001b\u0014A\u0002\te\u0005B\u00028'\u0001\u0004\u0011i\n\u0003\u0004rM\u0001\u0007!\u0011\u0015\u0005\u0007i\u001a\u0002\rA!*\t\r]4\u0003\u0019\u0001BU\u0011\u0019Qh\u00051\u0001\u0003.\u00069QO\\1qa2LXC\u0005Bc\u0005#\u0014)N!7\u0003^\n\u0005(Q\u001dBu\u0005[$BAa2\u0003pB)\u0001G!3\u0003N&\u0019!1\u001a\u0016\u0003\r=\u0003H/[8o!I\u0001\u0004Aa4\u0003T\n]'1\u001cBp\u0005G\u00149Oa;\u0011\u0007]\u0012\t\u000eB\u0003:O\t\u0007!\bE\u00028\u0005+$QaQ\u0014C\u0002i\u00022a\u000eBm\t\u00151uE1\u0001;!\r9$Q\u001c\u0003\u0006\u0013\u001e\u0012\rA\u000f\t\u0004o\t\u0005H!\u0002'(\u0005\u0004Q\u0004cA\u001c\u0003f\u0012)qj\nb\u0001uA\u0019qG!;\u0005\u000bI;#\u0019\u0001\u001e\u0011\u0007]\u0012i\u000fB\u0003VO\t\u0007!\bC\u0005\u0003r\u001e\n\t\u00111\u0001\u0003N\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t]\b\u0003\u0002B\u001b\u0005sLAAa?\u00038\t1qJ\u00196fGR\u0004")
public final class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>
implements Product8<T1, T2, T3, T4, T5, T6, T7, T8>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;
    private final T5 _5;
    private final T6 _6;
    private final T7 _7;
    private final T8 _8;

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Option<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> unapply(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> x$0) {
        return Tuple8$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        return new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(_1, _2, _3, _4, _5, _6, _7, _8);
    }

    @Override
    public int productArity() {
        return Product8.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product8.productElement$(this, n);
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

    @Override
    public T8 _8() {
        return this._8;
    }

    public String toString() {
        return new StringBuilder(9).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(",").append(this._8()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        return new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(_1, _2, _3, _4, _5, _6, _7, _8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T7 copy$default$7() {
        return this._7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T8 copy$default$8() {
        return this._8();
    }

    @Override
    public String productPrefix() {
        return "Tuple8";
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
        return x$1 instanceof Tuple8;
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
            case 7: {
                return "_8";
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
                if (!(x$1 instanceof Tuple8)) break block3;
                Tuple8 tuple8 = (Tuple8)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple8._1()) || !BoxesRunTime.equals(this._2(), tuple8._2()) || !BoxesRunTime.equals(this._3(), tuple8._3()) || !BoxesRunTime.equals(this._4(), tuple8._4()) || !BoxesRunTime.equals(this._5(), tuple8._5()) || !BoxesRunTime.equals(this._6(), tuple8._6()) || !BoxesRunTime.equals(this._7(), tuple8._7()) || !BoxesRunTime.equals(this._8(), tuple8._8())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple8(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
        this._8 = _8;
    }
}

