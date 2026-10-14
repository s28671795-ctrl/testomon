/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.setup;

import akka.actor.setup.ActorSystemSetup$;
import akka.actor.setup.Setup;
import akka.annotation.InternalApi;
import java.io.Serializable;
import java.util.Optional;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionForJava8$;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ur!\u0002\t\u0012\u0011\u0003Ab!\u0002\u000e\u0012\u0011\u0003Y\u0002\"\u0002\u0012\u0002\t\u0003\u0019\u0003b\u0002\u0013\u0002\u0005\u0004%\t!\n\u0005\b\u0003'\t\u0001\u0015!\u0003'\u0011\u001d\t)\"\u0001C\u0001\u0003/Aq!a\t\u0002\t\u0003\t)C\u0002\u0003\u001b#\t9\u0003\"\u0003\u0015\b\u0005\u000b\u0007I\u0011A\u000b*\u0011!itA!A!\u0002\u0013Q\u0003B\u0002\u0012\b\t\u0003)R\tC\u0003T\u000f\u0011\u0005A\u000bC\u0003T\u000f\u0011\u0005\u0001\u000eC\u0003x\u000f\u0011\u0005\u0001\u0010C\u0003\u007f\u000f\u0011\u0005q\u0010C\u0004\u0002\n\u001d!\t%a\u0003\u0002!\u0005\u001bGo\u001c:TsN$X-\\*fiV\u0004(B\u0001\n\u0014\u0003\u0015\u0019X\r^;q\u0015\t!R#A\u0003bGR|'OC\u0001\u0017\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0011$A\u0007\u0002#\t\u0001\u0012i\u0019;peNK8\u000f^3n'\u0016$X\u000f]\n\u0003\u0003q\u0001\"!\b\u0011\u000e\u0003yQ\u0011aH\u0001\u0006g\u000e\fG.Y\u0005\u0003Cy\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u0019\u0003\u0015)W\u000e\u001d;z+\u00051\u0003CA\r\b'\t9A$\u0001\u0004tKR,\bo]\u000b\u0002UA!1FM\u001b\u001d\u001d\ta\u0003\u0007\u0005\u0002.=5\taF\u0003\u00020/\u00051AH]8pizJ!!\r\u0010\u0002\rA\u0013X\rZ3g\u0013\t\u0019DGA\u0002NCBT!!\r\u00101\u0005YZ\u0004cA\u00168s%\u0011\u0001\b\u000e\u0002\u0006\u00072\f7o\u001d\t\u0003umb\u0001\u0001B\u0005=\u0013\u0005\u0005\t\u0011!B\u0001}\t\u0019q\fJ\u0019\u0002\u000fM,G/\u001e9tAE\u0011qH\u0011\t\u0003;\u0001K!!\u0011\u0010\u0003\u000f9{G\u000f[5oOB\u0011QdQ\u0005\u0003\tz\u00111!\u00118z)\t1c\tC\u0003)\u0015\u0001\u0007q\t\u0005\u0003,e!c\u0002GA%L!\rYsG\u0013\t\u0003u-#\u0011\u0002\u0010$\u0002\u0002\u0003\u0005)\u0011\u0001 )\u0005\u0019k\u0005C\u0001(R\u001b\u0005y%B\u0001)\u0016\u0003)\tgN\\8uCRLwN\\\u0005\u0003%>\u00131\"\u00138uKJt\u0017\r\\!qS\u0006\u0019q-\u001a;\u0016\u0005U{FC\u0001,f!\r9FLX\u0007\u00021*\u0011\u0011LW\u0001\u0005kRLGNC\u0001\\\u0003\u0011Q\u0017M^1\n\u0005uC&\u0001C(qi&|g.\u00197\u0011\u0005izF!\u00021\f\u0005\u0004\t'!\u0001+\u0012\u0005}\u0012\u0007CA\rd\u0013\t!\u0017CA\u0003TKR,\b\u000fC\u0003g\u0017\u0001\u0007q-A\u0003dY\u0006T(\u0010E\u0002,oy+\"!\u001b8\u0015\u0005)|\u0007cA\u000fl[&\u0011AN\b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005irG!\u00021\r\u0005\u0004\t\u0007b\u00029\r\u0003\u0003\u0005\u001d!]\u0001\u000bKZLG-\u001a8dK\u0012\n\u0004c\u0001:v[6\t1O\u0003\u0002u=\u00059!/\u001a4mK\u000e$\u0018B\u0001<t\u0005!\u0019E.Y:t)\u0006<\u0017!C<ji\"\u001cV\r^;q+\tIX\u0010\u0006\u0002'u\")10\u0004a\u0001y\u0006\tA\u000f\u0005\u0002;{\u0012)\u0001-\u0004b\u0001C\u0006\u0019\u0011M\u001c3\u0016\t\u0005\u0005\u0011q\u0001\u000b\u0004M\u0005\r\u0001BB>\u000f\u0001\u0004\t)\u0001E\u0002;\u0003\u000f!Q\u0001\u0019\bC\u0002\u0005\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u001b\u00012aKA\b\u0013\r\t\t\u0002\u000e\u0002\u0007'R\u0014\u0018N\\4\u0002\r\u0015l\u0007\u000f^=!\u0003\u0015\t\u0007\u000f\u001d7z)\r1\u0013\u0011\u0004\u0005\b\u00037)\u0001\u0019AA\u000f\u0003!\u0019X\r\u001e;j]\u001e\u001c\b\u0003B\u000f\u0002 \tL1!!\t\u001f\u0005)a$/\u001a9fCR,GMP\u0001\u0007GJ,\u0017\r^3\u0015\u0007\u0019\n9\u0003C\u0004\u0002\u001c\u0019\u0001\r!!\b)\u0007\u0019\tY\u0003\u0005\u0003\u0002.\u0005ERBAA\u0018\u0015\t\u0001f$\u0003\u0003\u00024\u0005=\"a\u0002<be\u0006\u0014xm\u001d")
public final class ActorSystemSetup {
    private final Map<Class<?>, Object> setups;

    public static ActorSystemSetup create(Setup ... settings) {
        return ActorSystemSetup$.MODULE$.create(settings);
    }

    public static ActorSystemSetup create(Seq<Setup> settings) {
        return ActorSystemSetup$.MODULE$.create(settings);
    }

    public static ActorSystemSetup apply(Seq<Setup> settings) {
        return ActorSystemSetup$.MODULE$.apply(settings);
    }

    public static ActorSystemSetup empty() {
        return ActorSystemSetup$.MODULE$.empty();
    }

    public Map<Class<?>, Object> setups() {
        return this.setups;
    }

    public <T extends Setup> Optional<T> get(Class<T> clazz) {
        return OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(this.setups().get(clazz).map((Function1<Object, Setup> & Serializable)x$1 -> (Setup)x$1)));
    }

    public <T extends Setup> Option<T> get(ClassTag<T> evidence$1) {
        Class<?> clazz = Predef$.MODULE$.implicitly(evidence$1).runtimeClass();
        return this.setups().get(clazz).map((Function1<Object, Setup> & Serializable)x$2 -> (Setup)x$2);
    }

    public <T extends Setup> ActorSystemSetup withSetup(T t2) {
        return new ActorSystemSetup((Map)this.setups().$plus(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(t2.getClass()), t2)));
    }

    public <T extends Setup> ActorSystemSetup and(T t2) {
        return this.withSetup(t2);
    }

    public String toString() {
        return new StringBuilder(21).append("ActorSystemSettings(").append(((IterableOnceOps)this.setups().keys().map((Function1<Class, String> & Serializable)x$3 -> x$3.getName())).mkString(",")).append(")").toString();
    }

    public ActorSystemSetup(@InternalApi Map<Class<?>, Object> setups) {
        this.setups = setups;
    }
}

