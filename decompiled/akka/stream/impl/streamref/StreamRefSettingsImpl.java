/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.annotation.InternalApi;
import akka.event.Logging$;
import akka.stream.StreamRefSettings;
import akka.stream.impl.streamref.StreamRefSettingsImpl$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dg!B\u0012%\u0005*b\u0003\u0002C$\u0001\u0005+\u0007I\u0011\t%\t\u00111\u0003!\u0011#Q\u0001\n%C\u0001\"\u0014\u0001\u0003\u0016\u0004%\tE\u0014\u0005\t/\u0002\u0011\t\u0012)A\u0005\u001f\"A\u0001\f\u0001BK\u0002\u0013\u0005c\n\u0003\u0005Z\u0001\tE\t\u0015!\u0003P\u0011!Q\u0006A!f\u0001\n\u0003r\u0005\u0002C.\u0001\u0005#\u0005\u000b\u0011B(\t\u000bq\u0003A\u0011A/\t\u000b\u0011\u0004A\u0011I3\t\u000b!\u0004A\u0011I5\t\u000b-\u0004A\u0011\t7\t\u000b9\u0004A\u0011I8\t\u000bE\u0004A\u0011\t:\t\u000fm\u0004\u0011\u0011!C\u0001y\"I\u00111\u0001\u0001\u0012\u0002\u0013\u0005\u0011Q\u0001\u0005\n\u00037\u0001\u0011\u0013!C\u0001\u0003;A\u0011\"!\t\u0001#\u0003%\t!!\b\t\u0013\u0005\r\u0002!%A\u0005\u0002\u0005u\u0001\u0002CA\u0013\u0001\u0005\u0005I\u0011\u0001%\t\u0013\u0005\u001d\u0002!!A\u0005\u0002\u0005%\u0002\"CA\u001b\u0001\u0005\u0005I\u0011IA\u001c\u0011%\t)\u0005AA\u0001\n\u0003\t9\u0005C\u0005\u0002R\u0001\t\t\u0011\"\u0011\u0002T!I\u0011Q\r\u0001\u0002\u0002\u0013\u0005\u0013q\r\u0005\n\u0003S\u0002\u0011\u0011!C!\u0003WB\u0011\"!\u001c\u0001\u0003\u0003%\t%a\u001c\b\u0015\u0005}D%!A\t\u0002)\n\tIB\u0005$I\u0005\u0005\t\u0012\u0001\u0016\u0002\u0004\"1A,\bC\u0001\u00037C\u0011\"!\u001b\u001e\u0003\u0003%)%a\u001b\t\u0013\u0005uU$!A\u0005\u0002\u0006}\u0005\"CAU;\u0005\u0005I\u0011QAV\u0011%\ti,HA\u0001\n\u0013\tyLA\u000bTiJ,\u0017-\u001c*fMN+G\u000f^5oONLU\u000e\u001d7\u000b\u0005\u00152\u0013!C:ue\u0016\fWN]3g\u0015\t9\u0003&\u0001\u0003j[Bd'BA\u0015+\u0003\u0019\u0019HO]3b[*\t1&\u0001\u0003bW.\f7#\u0002\u0001.g]R\u0004C\u0001\u00182\u001b\u0005y#\"\u0001\u0019\u0002\u000bM\u001c\u0017\r\\1\n\u0005Iz#AB!osJ+g\r\u0005\u00025k5\t\u0001&\u0003\u00027Q\t\t2\u000b\u001e:fC6\u0014VMZ*fiRLgnZ:\u0011\u00059B\u0014BA\u001d0\u0005\u001d\u0001&o\u001c3vGR\u0004\"a\u000f#\u000f\u0005q\u0012eBA\u001fB\u001b\u0005q$BA A\u0003\u0019a$o\\8u}\r\u0001\u0011\"\u0001\u0019\n\u0005\r{\u0013a\u00029bG.\fw-Z\u0005\u0003\u000b\u001a\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!aQ\u0018\u0002\u001d\t,hMZ3s\u0007\u0006\u0004\u0018mY5usV\t\u0011\n\u0005\u0002/\u0015&\u00111j\f\u0002\u0004\u0013:$\u0018a\u00042vM\u001a,'oQ1qC\u000eLG/\u001f\u0011\u00021\u0011,W.\u00198e%\u0016$W\r\\5wKJL\u0018J\u001c;feZ\fG.F\u0001P!\t\u0001V+D\u0001R\u0015\t\u00116+\u0001\u0005ekJ\fG/[8o\u0015\t!v&\u0001\u0006d_:\u001cWO\u001d:f]RL!AV)\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006IB-Z7b]\u0012\u0014V\rZ3mSZ,'/_%oi\u0016\u0014h/\u00197!\u0003M\u0019XOY:de&\u0004H/[8o)&lWm\\;u\u0003Q\u0019XOY:de&\u0004H/[8o)&lWm\\;uA\u0005qb-\u001b8bYR+'/\\5oCRLwN\\*jO:\fG\u000eR3bI2Lg.Z\u0001 M&t\u0017\r\u001c+fe6Lg.\u0019;j_:\u001c\u0016n\u001a8bY\u0012+\u0017\r\u001a7j]\u0016\u0004\u0013A\u0002\u001fj]&$h\bF\u0003_A\u0006\u00147\r\u0005\u0002`\u00015\tA\u0005C\u0003H\u0013\u0001\u0007\u0011\nC\u0003N\u0013\u0001\u0007q\nC\u0003Y\u0013\u0001\u0007q\nC\u0003[\u0013\u0001\u0007q*\u0001\nxSRD')\u001e4gKJ\u001c\u0015\r]1dSRLHCA\u001ag\u0011\u00159'\u00021\u0001J\u0003\u00151\u0018\r\\;f\u0003q9\u0018\u000e\u001e5EK6\fg\u000e\u001a*fI\u0016d\u0017N^3ss&sG/\u001a:wC2$\"a\r6\t\u000b\u001d\\\u0001\u0019A(\u0002/]LG\u000f[*vEN\u001c'/\u001b9uS>tG+[7f_V$HCA\u001an\u0011\u00159G\u00021\u0001P\u00035:\u0018\u000e\u001e5UKJl\u0017N\\1uS>t'+Z2fSZ,GMQ3g_J,7i\\7qY\u0016$\u0018n\u001c8MK\u0016<\u0018-\u001f\u000b\u0003gADQaZ\u0007A\u0002=\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A:\u0011\u0005QDhBA;w!\tit&\u0003\u0002x_\u00051\u0001K]3eK\u001aL!!\u001f>\u0003\rM#(/\u001b8h\u0015\t9x&\u0001\u0003d_BLHC\u00020~}~\f\t\u0001C\u0004H\u001fA\u0005\t\u0019A%\t\u000f5{\u0001\u0013!a\u0001\u001f\"9\u0001l\u0004I\u0001\u0002\u0004y\u0005b\u0002.\u0010!\u0003\u0005\raT\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\t9AK\u0002J\u0003\u0013Y#!a\u0003\u0011\t\u00055\u0011qC\u0007\u0003\u0003\u001fQA!!\u0005\u0002\u0014\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003+y\u0013AC1o]>$\u0018\r^5p]&!\u0011\u0011DA\b\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\tyBK\u0002P\u0003\u0013\tabY8qs\u0012\"WMZ1vYR$3'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u00111FA\u0019!\rq\u0013QF\u0005\u0004\u0003_y#aA!os\"A\u00111G\u000b\u0002\u0002\u0003\u0007\u0011*A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003s\u0001b!a\u000f\u0002B\u0005-RBAA\u001f\u0015\r\tydL\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA\"\u0003{\u0011\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011\u0011JA(!\rq\u00131J\u0005\u0004\u0003\u001bz#a\u0002\"p_2,\u0017M\u001c\u0005\n\u0003g9\u0012\u0011!a\u0001\u0003W\t!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011QKA2!\u0011\t9&!\u0019\u000e\u0005\u0005e#\u0002BA.\u0003;\nA\u0001\\1oO*\u0011\u0011qL\u0001\u0005U\u00064\u0018-C\u0002z\u00033B\u0001\"a\r\u0019\u0003\u0003\u0005\r!S\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0011*\u0001\u0005u_N#(/\u001b8h)\t\t)&\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u0013\n\t\bC\u0005\u00024m\t\t\u00111\u0001\u0002,!\u001a\u0001!!\u001e\u0011\t\u0005]\u00141P\u0007\u0003\u0003sR1!!\u0006+\u0013\u0011\ti(!\u001f\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0016'R\u0014X-Y7SK\u001a\u001cV\r\u001e;j]\u001e\u001c\u0018*\u001c9m!\tyVdE\u0003\u001e\u0003\u000b\u000b\t\nE\u0005\u0002\b\u00065\u0015jT(P=6\u0011\u0011\u0011\u0012\u0006\u0004\u0003\u0017{\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003\u001f\u000bIIA\tBEN$(/Y2u\rVt7\r^5p]R\u0002B!a%\u0002\u001a6\u0011\u0011Q\u0013\u0006\u0005\u0003/\u000bi&\u0001\u0002j_&\u0019Q)!&\u0015\u0005\u0005\u0005\u0015!B1qa2LH#\u00030\u0002\"\u0006\r\u0016QUAT\u0011\u00159\u0005\u00051\u0001J\u0011\u0015i\u0005\u00051\u0001P\u0011\u0015A\u0006\u00051\u0001P\u0011\u0015Q\u0006\u00051\u0001P\u0003\u001d)h.\u00199qYf$B!!,\u0002:B)a&a,\u00024&\u0019\u0011\u0011W\u0018\u0003\r=\u0003H/[8o!\u001dq\u0013QW%P\u001f>K1!a.0\u0005\u0019!V\u000f\u001d7fi!A\u00111X\u0011\u0002\u0002\u0003\u0007a,A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!1\u0011\t\u0005]\u00131Y\u0005\u0005\u0003\u000b\fIF\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class StreamRefSettingsImpl
implements StreamRefSettings,
Product,
Serializable {
    private final int bufferCapacity;
    private final FiniteDuration demandRedeliveryInterval;
    private final FiniteDuration subscriptionTimeout;
    private final FiniteDuration finalTerminationSignalDeadline;

    public static Option<Tuple4<Object, FiniteDuration, FiniteDuration, FiniteDuration>> unapply(StreamRefSettingsImpl x$0) {
        return StreamRefSettingsImpl$.MODULE$.unapply(x$0);
    }

    public static StreamRefSettingsImpl apply(int bufferCapacity, FiniteDuration demandRedeliveryInterval, FiniteDuration subscriptionTimeout, FiniteDuration finalTerminationSignalDeadline) {
        return StreamRefSettingsImpl$.MODULE$.apply(bufferCapacity, demandRedeliveryInterval, subscriptionTimeout, finalTerminationSignalDeadline);
    }

    public static Function1<Tuple4<Object, FiniteDuration, FiniteDuration, FiniteDuration>, StreamRefSettingsImpl> tupled() {
        return StreamRefSettingsImpl$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<FiniteDuration, Function1<FiniteDuration, Function1<FiniteDuration, StreamRefSettingsImpl>>>> curried() {
        return StreamRefSettingsImpl$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public int bufferCapacity() {
        return this.bufferCapacity;
    }

    @Override
    public FiniteDuration demandRedeliveryInterval() {
        return this.demandRedeliveryInterval;
    }

    @Override
    public FiniteDuration subscriptionTimeout() {
        return this.subscriptionTimeout;
    }

    @Override
    public FiniteDuration finalTerminationSignalDeadline() {
        return this.finalTerminationSignalDeadline;
    }

    @Override
    public StreamRefSettings withBufferCapacity(int value) {
        return this.copy(value, this.copy$default$2(), this.copy$default$3(), this.copy$default$4());
    }

    @Override
    public StreamRefSettings withDemandRedeliveryInterval(FiniteDuration value) {
        FiniteDuration x$1 = value;
        int x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$3();
        FiniteDuration x$4 = this.copy$default$4();
        return this.copy(x$2, x$1, x$3, x$4);
    }

    @Override
    public StreamRefSettings withSubscriptionTimeout(FiniteDuration value) {
        FiniteDuration x$1 = value;
        int x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$4();
        return this.copy(x$2, x$3, x$1, x$4);
    }

    @Override
    public StreamRefSettings withTerminationReceivedBeforeCompletionLeeway(FiniteDuration value) {
        FiniteDuration x$1 = value;
        int x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        return this.copy(x$2, x$3, x$4, x$1);
    }

    @Override
    public String productPrefix() {
        return Logging$.MODULE$.simpleName(StreamRefSettings.class);
    }

    public StreamRefSettingsImpl copy(int bufferCapacity, FiniteDuration demandRedeliveryInterval, FiniteDuration subscriptionTimeout, FiniteDuration finalTerminationSignalDeadline) {
        return new StreamRefSettingsImpl(bufferCapacity, demandRedeliveryInterval, subscriptionTimeout, finalTerminationSignalDeadline);
    }

    public int copy$default$1() {
        return this.bufferCapacity();
    }

    public FiniteDuration copy$default$2() {
        return this.demandRedeliveryInterval();
    }

    public FiniteDuration copy$default$3() {
        return this.subscriptionTimeout();
    }

    public FiniteDuration copy$default$4() {
        return this.finalTerminationSignalDeadline();
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.bufferCapacity());
            }
            case 1: {
                return this.demandRedeliveryInterval();
            }
            case 2: {
                return this.subscriptionTimeout();
            }
            case 3: {
                return this.finalTerminationSignalDeadline();
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
        return x$1 instanceof StreamRefSettingsImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "bufferCapacity";
            }
            case 1: {
                return "demandRedeliveryInterval";
            }
            case 2: {
                return "subscriptionTimeout";
            }
            case 3: {
                return "finalTerminationSignalDeadline";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.bufferCapacity());
        n = Statics.mix(n, Statics.anyHash(this.demandRedeliveryInterval()));
        n = Statics.mix(n, Statics.anyHash(this.subscriptionTimeout()));
        n = Statics.mix(n, Statics.anyHash(this.finalTerminationSignalDeadline()));
        return Statics.finalizeHash(n, 4);
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
        if (!(object instanceof StreamRefSettingsImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        StreamRefSettingsImpl streamRefSettingsImpl = (StreamRefSettingsImpl)x$1;
        if (this.bufferCapacity() != streamRefSettingsImpl.bufferCapacity()) return false;
        FiniteDuration finiteDuration = this.demandRedeliveryInterval();
        FiniteDuration finiteDuration2 = streamRefSettingsImpl.demandRedeliveryInterval();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        FiniteDuration finiteDuration3 = this.subscriptionTimeout();
        FiniteDuration finiteDuration4 = streamRefSettingsImpl.subscriptionTimeout();
        if (finiteDuration3 == null) {
            if (finiteDuration4 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration3).equals(finiteDuration4)) return false;
        FiniteDuration finiteDuration5 = this.finalTerminationSignalDeadline();
        FiniteDuration finiteDuration6 = streamRefSettingsImpl.finalTerminationSignalDeadline();
        if (finiteDuration5 == null) {
            if (finiteDuration6 == null) return true;
            return false;
        } else {
            if (!((Object)finiteDuration5).equals(finiteDuration6)) return false;
            return true;
        }
    }

    public StreamRefSettingsImpl(int bufferCapacity, FiniteDuration demandRedeliveryInterval, FiniteDuration subscriptionTimeout, FiniteDuration finalTerminationSignalDeadline) {
        this.bufferCapacity = bufferCapacity;
        this.demandRedeliveryInterval = demandRedeliveryInterval;
        this.subscriptionTimeout = subscriptionTimeout;
        this.finalTerminationSignalDeadline = finalTerminationSignalDeadline;
        Product.$init$(this);
    }
}

