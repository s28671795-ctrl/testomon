/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.Util$;
import java.util.List;
import java.util.Optional;
import scala.Option;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\rs!B\u0006\r\u0011\u0003\tb!B\n\r\u0011\u0003!\u0002\"B\u000e\u0002\t\u0003a\u0002\"B\u000f\u0002\t\u0003q\u0002\"\u0002 \u0002\t\u0003y\u0004\"\u0002 \u0002\t\u00039\u0006\"\u0002 \u0002\t\u0003q\u0006\"B7\u0002\t\u0003q\u0007\"B;\u0002\t\u00031\bbBA\u000b\u0003\u0011\u0005\u0011q\u0003\u0005\b\u0003S\tA\u0011AA\u0016\u0003\u0011)F/\u001b7\u000b\u00055q\u0011\u0001\u00026ba&T\u0011aD\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005I\tQ\"\u0001\u0007\u0003\tU#\u0018\u000e\\\n\u0003\u0003U\u0001\"AF\r\u000e\u0003]Q\u0011\u0001G\u0001\u0006g\u000e\fG.Y\u0005\u00035]\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u0012\u0003!\u0019G.Y:t)\u0006<WCA\u0010))\t\u0001\u0013\u0007E\u0002\"I\u0019j\u0011A\t\u0006\u0003G]\tqA]3gY\u0016\u001cG/\u0003\u0002&E\tA1\t\\1tgR\u000bw\r\u0005\u0002(Q1\u0001A!B\u0015\u0004\u0005\u0004Q#!\u0001+\u0012\u0005-r\u0003C\u0001\f-\u0013\tisCA\u0004O_RD\u0017N\\4\u0011\u0005Yy\u0013B\u0001\u0019\u0018\u0005\r\te.\u001f\u0005\u0006e\r\u0001\raM\u0001\u0006G2\f'P\u001f\t\u0004im2cBA\u001b:!\t1t#D\u00018\u0015\tA\u0004#\u0001\u0004=e>|GOP\u0005\u0003u]\ta\u0001\u0015:fI\u00164\u0017B\u0001\u001f>\u0005\u0015\u0019E.Y:t\u0015\tQt#\u0001\u0007j[6,H/\u00192mKN+\u0017\u000f\u0006\u0002A\u001bB\u0019\u0011I\u0012%\u000e\u0003\tS!a\u0011#\u0002\u0013%lW.\u001e;bE2,'BA#\u0018\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003\u000f\n\u00131aU3ra\tI5\nE\u00025w)\u0003\"aJ&\u0005\u00131#\u0011\u0011!A\u0001\u0006\u0003Q#aA0%e!)a\n\u0002a\u0001\u001f\u0006\u0019\u0011M\u001d:\u0011\u0007Y\u0001&+\u0003\u0002R/\t)\u0011I\u001d:bsB\u00121+\u0016\t\u0004im\"\u0006CA\u0014V\t%1V*!A\u0001\u0002\u000b\u0005!FA\u0002`IE*\"\u0001W.\u0015\u0005ec\u0006cA!G5B\u0011qe\u0017\u0003\u0006S\u0015\u0011\rA\u000b\u0005\u0006\u001d\u0016\u0001\r!\u0018\t\u0004-ASVCA0c)\t\u00017\rE\u0002B\r\u0006\u0004\"a\n2\u0005\u000b%2!\u0019\u0001\u0016\t\u000b\u00114\u0001\u0019A3\u0002\u0011%$XM]1cY\u0016\u00042AZ6b\u001b\u00059'B\u00015j\u0003\u0011a\u0017M\\4\u000b\u0003)\fAA[1wC&\u0011An\u001a\u0002\t\u0013R,'/\u00192mK\u0006)\u0012.\\7vi\u0006\u0014G.Z*j]\u001edW\r^8o'\u0016\fXCA8s)\t\u00018\u000fE\u0002B\rF\u0004\"a\n:\u0005\u000b%:!\u0019\u0001\u0016\t\u000bQ<\u0001\u0019A9\u0002\u000bY\fG.^3\u0002\u001b)\fg/Y!se\u0006LH*[:u+\t9x\u0010F\u0002y\u0003\u0003\u00012!\u001f?\u007f\u001b\u0005Q(BA>j\u0003\u0011)H/\u001b7\n\u0005uT(\u0001\u0002'jgR\u0004\"aJ@\u0005\u000b%B!\u0019\u0001\u0016\t\u000f\u0005\r\u0001\u00021\u0001\u0002\u0006\u0005\u00191/Z9\u0011\u000b\u0005\u001d\u0011\u0011\u0003@\u000f\t\u0005%\u0011Q\u0002\b\u0004m\u0005-\u0011\"\u0001\r\n\u0007\u0005=q#A\u0004qC\u000e\\\u0017mZ3\n\u0007\u001d\u000b\u0019BC\u0002\u0002\u0010]\t1#[7nkR\f'\r\\3J]\u0012,\u00070\u001a3TKF,B!!\u0007\u0002$Q!\u00111DA\u0013!\u0015\t\u0015QDA\u0011\u0013\r\tyB\u0011\u0002\u000b\u0013:$W\r_3e'\u0016\f\bcA\u0014\u0002$\u0011)\u0011&\u0003b\u0001U!1A-\u0003a\u0001\u0003O\u0001BAZ6\u0002\"\u00051q\u000e\u001d;j_:,B!!\f\u00028Q!\u0011qFA\u001d!\u00151\u0012\u0011GA\u001b\u0013\r\t\u0019d\u0006\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0007\u001d\n9\u0004B\u0003*\u0015\t\u0007!\u0006C\u0004\u0002<)\u0001\r!!\u0010\u0002\u000f)|\u0005\u000f^5p]B)\u00110a\u0010\u00026%\u0019\u0011\u0011\t>\u0003\u0011=\u0003H/[8oC2\u0004")
public final class Util {
    public static <T> Option<T> option(Optional<T> jOption) {
        return Util$.MODULE$.option(jOption);
    }

    public static <T> IndexedSeq<T> immutableIndexedSeq(Iterable<T> iterable) {
        return Util$.MODULE$.immutableIndexedSeq(iterable);
    }

    public static <T> List<T> javaArrayList(Seq<T> seq) {
        return Util$.MODULE$.javaArrayList(seq);
    }

    public static <T> Seq<T> immutableSingletonSeq(T value) {
        return Util$.MODULE$.immutableSingletonSeq(value);
    }

    public static <T> Seq<T> immutableSeq(Iterable<T> iterable) {
        return Util$.MODULE$.immutableSeq(iterable);
    }

    public static <T> Seq<T> immutableSeq(Object arr) {
        return Util$.MODULE$.immutableSeq(arr);
    }

    public static Seq<Class<?>> immutableSeq(Class<?>[] arr) {
        return Util$.MODULE$.immutableSeq(arr);
    }

    public static <T> ClassTag<T> classTag(Class<T> clazz) {
        return Util$.MODULE$.classTag(clazz);
    }
}

