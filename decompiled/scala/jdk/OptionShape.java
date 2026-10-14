/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import scala.Option;
import scala.jdk.OptionShape$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ma!B\n\u0015\u0003CI\u0002\"B\u0010\u0001\t\u0003\u0001\u0003\"B\u0019\u0001\r\u0003\u0011\u0004\"B\u001f\u0001\r\u0003qt!B)\u0015\u0011\u0003\u0011f!B\n\u0015\u0011\u0003\u0019\u0006\"B\u0010\u0006\t\u0003!\u0006bB+\u0006\u0005\u0004%\u0019A\u0016\u0005\u0007=\u0016\u0001\u000b\u0011B,\t\u000f}+!\u0019!C\u0002A\"1q-\u0002Q\u0001\n\u0005Dq\u0001[\u0003C\u0002\u0013\r\u0011\u000e\u0003\u0004r\u000b\u0001\u0006IA\u001b\u0005\be\u0016\u0011\r\u0011b\u0001t\u0011\u0019AX\u0001)A\u0005i\"9\u00110\u0002b\u0001\n\u0007Q\bbBA\u0003\u000b\u0001\u0006Ia\u001f\u0005\n\u0003\u000f)!\u0019!C\u0002\u0003\u0013A\u0001\"!\u0005\u0006A\u0003%\u00111\u0002\u0002\f\u001fB$\u0018n\u001c8TQ\u0006\u0004XM\u0003\u0002\u0016-\u0005\u0019!\u000eZ6\u000b\u0003]\tQa]2bY\u0006\u001c\u0001!F\u0002\u001bK=\u001a\"\u0001A\u000e\u0011\u0005qiR\"\u0001\f\n\u0005y1\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002CA!!\u0005A\u0012/\u001b\u0005!\u0002C\u0001\u0013&\u0019\u0001!QA\n\u0001C\u0002\u001d\u0012\u0011!Q\t\u0003Q-\u0002\"\u0001H\u0015\n\u0005)2\"a\u0002(pi\"Lgn\u001a\t\u000391J!!\f\f\u0003\u0007\u0005s\u0017\u0010\u0005\u0002%_\u0011)\u0001\u0007\u0001b\u0001O\t\tq*\u0001\u0005ge>l'*\u0019<b)\tq3\u0007C\u00035\u0005\u0001\u0007Q'A\u0001p!\r14hI\u0007\u0002o)\u0011\u0001(O\u0001\u0005kRLGNC\u0001;\u0003\u0011Q\u0017M^1\n\u0005q:$\u0001C(qi&|g.\u00197\u0002\u0013\u0019\u0014x.\\*dC2\fGC\u0001\u0018@\u0011\u0015!4\u00011\u0001A!\ra\u0012iI\u0005\u0003\u0005Z\u0011aa\u00149uS>t\u0017F\u0001\u0001E\r\u0011)\u0005\u0001\u0001$\u0003\u001bqbwnY1mA\rD\u0017\u000e\u001c3?'\t!\u0015\u0005\u000b\u0003\u0001\u0011:{\u0005CA%M\u001b\u0005Q%BA&\u0017\u0003)\tgN\\8uCRLwN\\\u0005\u0003\u001b*\u0013\u0001#[7qY&\u001c\u0017\u000e\u001e(pi\u001a{WO\u001c3\u0002\u00075\u001cx-I\u0001Q\u0003uru\u000eI:qK\u000eL\u0017\r\\5{K\u0012\u0004s\n\u001d;j_:\fG\u000e\t;za\u0016\u0004S\r_5tiN\u0004cm\u001c:!K2,W.\u001a8ug\u0002zg\r\t;za\u0016\u0004Ce_!~\u0003-y\u0005\u000f^5p]NC\u0017\r]3\u0011\u0005\t*1CA\u0003\u001c)\u0005\u0011\u0016!\u00053pk\ndWm\u00149uS>t7\u000b[1qKV\tq\u000b\u0005\u0003#\u0001a[\u0006C\u0001\u000fZ\u0013\tQfC\u0001\u0004E_V\u0014G.\u001a\t\u0003mqK!!X\u001c\u0003\u001d=\u0003H/[8oC2$u.\u001e2mK\u0006\u0011Bm\\;cY\u0016|\u0005\u000f^5p]NC\u0017\r]3!\u0003IQGi\\;cY\u0016|\u0005\u000f^5p]NC\u0017\r]3\u0016\u0003\u0005\u0004BA\t\u0001c7B\u00111MZ\u0007\u0002I*\u0011Q-O\u0001\u0005Y\u0006tw-\u0003\u0002[I\u0006\u0019\"\u000eR8vE2,w\n\u001d;j_:\u001c\u0006.\u00199fA\u0005q\u0011N\u001c;PaRLwN\\*iCB,W#\u00016\u0011\t\t\u00021N\u001c\t\u000391L!!\u001c\f\u0003\u0007%sG\u000f\u0005\u00027_&\u0011\u0001o\u000e\u0002\f\u001fB$\u0018n\u001c8bY&sG/A\bj]R|\u0005\u000f^5p]NC\u0017\r]3!\u0003MQ\u0017J\u001c;fO\u0016\u0014x\n\u001d;j_:\u001c\u0006.\u00199f+\u0005!\b\u0003\u0002\u0012\u0001k:\u0004\"a\u0019<\n\u0005]$'aB%oi\u0016<WM]\u0001\u0015U&sG/Z4fe>\u0003H/[8o'\"\f\u0007/\u001a\u0011\u0002\u001f1|gnZ(qi&|gn\u00155ba\u0016,\u0012a\u001f\t\u0005E\u0001ax\u0010\u0005\u0002\u001d{&\u0011aP\u0006\u0002\u0005\u0019>tw\rE\u00027\u0003\u0003I1!a\u00018\u00051y\u0005\u000f^5p]\u0006dGj\u001c8h\u0003AawN\\4PaRLwN\\*iCB,\u0007%\u0001\tk\u0019>twm\u00149uS>t7\u000b[1qKV\u0011\u00111\u0002\t\u0006E\u0001\tia \t\u0004G\u0006=\u0011B\u0001@e\u0003EQGj\u001c8h\u001fB$\u0018n\u001c8TQ\u0006\u0004X\r\t")
public abstract class OptionShape<A, O> {
    public static OptionShape<Long, OptionalLong> jLongOptionShape() {
        return OptionShape$.MODULE$.jLongOptionShape();
    }

    public static OptionShape<Object, OptionalLong> longOptionShape() {
        return OptionShape$.MODULE$.longOptionShape();
    }

    public static OptionShape<Integer, OptionalInt> jIntegerOptionShape() {
        return OptionShape$.MODULE$.jIntegerOptionShape();
    }

    public static OptionShape<Object, OptionalInt> intOptionShape() {
        return OptionShape$.MODULE$.intOptionShape();
    }

    public static OptionShape<Double, OptionalDouble> jDoubleOptionShape() {
        return OptionShape$.MODULE$.jDoubleOptionShape();
    }

    public static OptionShape<Object, OptionalDouble> doubleOptionShape() {
        return OptionShape$.MODULE$.doubleOptionShape();
    }

    public abstract O fromJava(Optional<A> var1);

    public abstract O fromScala(Option<A> var1);
}

