/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import scala.Function0;
import scala.Function1;
import scala.PartialFunction;
import scala.collection.Factory;
import scala.collection.IntStepper;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.SeqFactory;
import scala.collection.SpecificIterableFactory;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.View;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.Growable;
import scala.jdk.Accumulator;
import scala.jdk.Accumulator$mcI$sp;
import scala.jdk.AnyAccumulator;
import scala.jdk.AnyAccumulator$;
import scala.jdk.IntAccumulator$;
import scala.jdk.IntAccumulatorStepper;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\reb\u0001\u0002#F\u0005)CQa\u001b\u0001\u0005\u00021D\u0001\"\u001c\u0001A\u0002\u0013\u0005QI\u001c\u0005\te\u0002\u0001\r\u0011\"\u0001Fg\"1\u0011\u0010\u0001Q!\n=D\u0001B\u001f\u0001A\u0002\u0013\u0005Qi\u001f\u0005\t{\u0002\u0001\r\u0011\"\u0001F}\"9\u0011\u0011\u0001\u0001!B\u0013a\b\u0002CA\u0002\u0001\u0011\u0005Q)!\u0002\t\u0011\u0005E\u0001\u0001)C)\u0003'Aq!!\n\u0001\t\u0003\t9\u0003C\u0004\u0002v\u0001!I!a\u001e\t\u000f\u0005e\u0004\u0001\"\u0003\u0002x!9\u00111\u0010\u0001\u0005\u0002\u0005u\u0004BBAC\u0001\u0011\u0005C\u000eC\u0004\u0002\b\u0002!\t!!#\t\u000f\u0005=\u0005\u0001\"\u0011\u0002x!9\u0011\u0011\u0013\u0001\u0005\u0002\u0005M\u0005bBAI\u0001\u0011\u0005\u0011\u0011\u0014\u0005\b\u0003;\u0003A\u0011AAP\u0011\u001d\ti\n\u0001C\u0001\u0003SCq!a,\u0001\t\u0003\t\t\fC\u0004\u0002:\u0002!\t%a/\t\u000f\u0005=\u0007\u0001\"\u0001\u0002R\"9\u0011q\u001b\u0001\u0005\u0002\u0005e\u0007bBAs\u0001\u0011\u0005\u0011q\u001d\u0005\b\u0003g\u0004A\u0011BA{\u0011\u001d\u00119\u0001\u0001C!\u0005\u0013AqA!\u0004\u0001\t\u0003\u0012y\u0001C\u0004\u0003\u0014\u0001!\tE!\u0006\t\u000f\tm\u0001\u0001\"\u0011\u0003\u001e!9!\u0011\u0005\u0001\u0005B\t\r\u0002b\u0002B\u0014\u0001\u0011\u0005!\u0011\u0006\u0005\u0007\u0005[\u0001A\u0011\u00018\t\u000f\t=\u0002\u0001\"\u0011\u00032!9!\u0011\b\u0001\u0005B\tm\u0002b\u0002B(\u0001\u0011E#\u0011\u000b\u0005\b\u0005/\u0002A\u0011\u000bB-\u0011\u001d\u0011Y\u0006\u0001C!\u0005;BqA!\u001a\u0001\t\u0003\u0012I\u0006C\u0004\u0003h\u0001!IA!\u001b\b\u000f\tET\t#\u0001\u0003t\u00191A)\u0012E\u0001\u0005kBaa\u001b\u0016\u0005\u0002\t-\u0005\u0002\u0003BGU\t\u0007I\u0011\u00028\t\u000f\t=%\u0006)A\u0005_\"A!\u0011\u0013\u0016C\u0002\u0013%1\u0010C\u0004\u0003\u0014*\u0002\u000b\u0011\u0002?\t\u000f\tU%\u0006b\u0001\u0003\u0018\"9!Q\u0016\u0016\u0005\u0002\t=\u0006b\u0002BaU\u0011\u0005!1\u0019\u0005\b\u0005\u0017TC\u0011\u0001Bg\u0011\u001d\u0011)N\u000bC\u0001\u0005/DqAa7+\t\u0013\u0011i\u000eC\u0004\u0003P)\"\tE!9\t\u000f\t\u0015$\u0006\"\u0011\u0003Z!9!q\u001d\u0016\u0005B\tecA\u0002BuU\u0001\u0011Y\u000f\u0003\u0006\u0003pf\u0012)\u0019!C\u0005\u00053B\u0011B!=:\u0005\u0003\u0005\u000b\u0011\u0002,\t\r-LD\u0011\u0001B~\u0011-\t))\u000fa\u0001\u0002\u0004%IA!\u0017\t\u0017\r%\u0011\b1AA\u0002\u0013%11\u0002\u0005\u000b\u0007\u001fI\u0004\u0019!A!B\u00131\u0006bBB\ns\u0011%1Q\u0003\u0005\b\u0007CID\u0011BB\u0012\u0011\u001d\u0019y#\u000fC\u0005\u0005SB\u0011Ba\u001a+\u0003\u0003%Ia!\r\u0003\u001d%sG/Q2dk6,H.\u0019;pe*\u0011aiR\u0001\u0004U\u0012\\'\"\u0001%\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M!\u0001aS,`!\u0015aUjT*W\u001b\u0005)\u0015B\u0001(F\u0005-\t5mY;nk2\fGo\u001c:\u0011\u0005A\u000bV\"A$\n\u0005I;%aA%oiB\u0011A\nV\u0005\u0003+\u0016\u0013a\"\u00118z\u0003\u000e\u001cW/\\;mCR|'\u000f\u0005\u0002M\u0001A)\u0001,X(T-6\t\u0011L\u0003\u0002[7\u00069Q.\u001e;bE2,'B\u0001/H\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003=f\u0013aaU3r\u001fB\u001c\bC\u00011i\u001d\t\tgM\u0004\u0002cK6\t1M\u0003\u0002e\u0013\u00061AH]8pizJ\u0011\u0001S\u0005\u0003O\u001e\u000bq\u0001]1dW\u0006<W-\u0003\u0002jU\na1+\u001a:jC2L'0\u00192mK*\u0011qmR\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003Y\u000bqaY;se\u0016tG/F\u0001p!\r\u0001\u0006oT\u0005\u0003c\u001e\u0013Q!\u0011:sCf\f1bY;se\u0016tGo\u0018\u0013fcR\u0011Ao\u001e\t\u0003!VL!A^$\u0003\tUs\u0017\u000e\u001e\u0005\bq\u000e\t\t\u00111\u0001p\u0003\rAH%M\u0001\tGV\u0014(/\u001a8uA\u00059\u0001.[:u_JLX#\u0001?\u0011\u0007A\u0003x.A\u0006iSN$xN]=`I\u0015\fHC\u0001;\u0000\u0011\u001dAh!!AA\u0002q\f\u0001\u0002[5ti>\u0014\u0018\u0010I\u0001\u000bGVlW\u000f\\1uSZ,G\u0003BA\u0004\u0003\u001b\u00012\u0001UA\u0005\u0013\r\tYa\u0012\u0002\u0005\u0019>tw\r\u0003\u0004\u0002\u0010!\u0001\raT\u0001\u0002S\u0006I1\r\\1tg:\u000bW.Z\u000b\u0003\u0003+\u0001B!a\u0006\u0002 9!\u0011\u0011DA\u000e!\t\u0011w)C\u0002\u0002\u001e\u001d\u000ba\u0001\u0015:fI\u00164\u0017\u0002BA\u0011\u0003G\u0011aa\u0015;sS:<'bAA\u000f\u000f\u0006\u0001RM\u001a4jG&,g\u000e^*uKB\u0004XM]\u000b\u0005\u0003S\t)\u0004\u0006\u0003\u0002,\u0005-$CBA\u0017\u0003c\tIF\u0002\u0004\u00020\u0001\u0001\u00111\u0006\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0005\u0003g\t)\u0004\u0004\u0001\u0005\u000f\u0005]\"B1\u0001\u0002:\t\t1+\u0005\u0003\u0002<\u0005\u0005\u0003c\u0001)\u0002>%\u0019\u0011qH$\u0003\u000f9{G\u000f[5oOB\"\u00111IA'!\u0019\t)%a\u0012\u0002L5\t1,C\u0002\u0002Jm\u0013qa\u0015;faB,'\u000f\u0005\u0003\u00024\u00055C\u0001DA(\u0003k\t\t\u0011!A\u0003\u0002\u0005E#aA0%cE!\u00111HA*!\r\u0001\u0016QK\u0005\u0004\u0003/:%aA!osB!\u00111LA3\u001d\u0011\ti&!\u0019\u000f\u0007\u0005\fy&\u0003\u0002]\u000f&\u0019\u00111M.\u0002\u000fM#X\r\u001d9fe&!\u0011qMA5\u00059)eMZ5dS\u0016tGo\u00159mSRT1!a\u0019\\\u0011\u001d\tiG\u0003a\u0002\u0003_\nQa\u001d5ba\u0016\u0004r!!\u0012\u0002r=\u000b\t$C\u0002\u0002tm\u0013Ab\u0015;faB,'o\u00155ba\u0016\fa!\u001a=qC:$G#\u0001;\u0002\u000f!,\u0005\u0010]1oI\u00061\u0011\r\u001a3P]\u0016$B!a \u0002\u00026\t\u0001\u0001\u0003\u0004\u0002\u00046\u0001\raT\u0001\u0002C\u00061!/Z:vYR\fQ\u0001\u001a:bS:$2\u0001^AF\u0011\u0019\tii\u0004a\u0001-\u0006!A\u000f[1u\u0003\u0015\u0019G.Z1s\u0003\u0015\t\u0007\u000f\u001d7z)\ry\u0015Q\u0013\u0005\b\u0003/\u000b\u0002\u0019AA\u0004\u0003\tI\u0007\u0010F\u0002P\u00037Ca!a\u0004\u0013\u0001\u0004y\u0015AB;qI\u0006$X\rF\u0003u\u0003C\u000b)\u000bC\u0004\u0002$N\u0001\r!a\u0002\u0002\u0007%$\u0007\u0010\u0003\u0004\u0002(N\u0001\raT\u0001\u0005K2,W\u000eF\u0003u\u0003W\u000bi\u000b\u0003\u0004\u0002$R\u0001\ra\u0014\u0005\u0007\u0003O#\u0002\u0019A(\u0002\u0011%$XM]1u_J,\"!a-\u0011\t\u0001\f)lT\u0005\u0004\u0003oS'\u0001C%uKJ\fGo\u001c:\u0002\u000f\u0019|'/Z1dQV!\u0011QXAf)\r!\u0018q\u0018\u0005\b\u0003\u00034\u0002\u0019AAb\u0003\u00051\u0007C\u0002)\u0002F>\u000bI-C\u0002\u0002H\u001e\u0013\u0011BR;oGRLwN\\\u0019\u0011\t\u0005M\u00121\u001a\u0003\b\u0003\u001b4\"\u0019AA)\u0005\u0005)\u0016aA7baR\u0019a+a5\t\u000f\u0005\u0005w\u00031\u0001\u0002VB)\u0001+!2P\u001f\u00069a\r\\1u\u001b\u0006\u0004Hc\u0001,\u0002\\\"9\u0011\u0011\u0019\rA\u0002\u0005u\u0007C\u0002)\u0002F>\u000by\u000e\u0005\u0003a\u0003C|\u0015bAArU\na\u0011\n^3sC\ndWm\u00148dK\u000691m\u001c7mK\u000e$Hc\u0001,\u0002j\"9\u00111^\rA\u0002\u00055\u0018A\u00019g!\u0015\u0001\u0016q^(P\u0013\r\t\tp\u0012\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]\u0006ia-\u001b7uKJ\f5mY%na2$RAVA|\u0005\u0007Aq!!?\u001b\u0001\u0004\tY0\u0001\u0003qe\u0016$\u0007C\u0002)\u0002F>\u000bi\u0010E\u0002Q\u0003\u007fL1A!\u0001H\u0005\u001d\u0011un\u001c7fC:DqA!\u0002\u001b\u0001\u0004\ti0A\u0002o_R\faAZ5mi\u0016\u0014Hc\u0001,\u0003\f!9\u0011\u0011`\u000eA\u0002\u0005m\u0018!\u00034jYR,'OT8u)\r1&\u0011\u0003\u0005\b\u0003sd\u0002\u0019AA~\u0003\u00191wN]1mYR!\u0011Q B\f\u0011\u001d\u0011I\"\ba\u0001\u0003w\f\u0011\u0001]\u0001\u0007KbL7\u000f^:\u0015\t\u0005u(q\u0004\u0005\b\u00053q\u0002\u0019AA~\u0003\u0015\u0019w.\u001e8u)\ry%Q\u0005\u0005\b\u00053y\u0002\u0019AA~\u0003%\u0019w.\u001e8u\u0019>tw\r\u0006\u0003\u0002\b\t-\u0002b\u0002B\rA\u0001\u0007\u00111`\u0001\bi>\f%O]1z\u0003\u0019!x\u000eT5tiV\u0011!1\u0007\t\u0005A\nUr*C\u0002\u00038)\u0014A\u0001T5ti\u0006\u0011Ao\\\u000b\u0005\u0005{\u0011\t\u0005\u0006\u0003\u0003@\t\u0015\u0003\u0003BA\u001a\u0005\u0003\"qAa\u0011$\u0005\u0004\t\tF\u0001\u0002Dc!9!qI\u0012A\u0002\t%\u0013a\u00024bGR|'/\u001f\t\b\u0003\u000b\u0012Ye\u0014B \u0013\r\u0011ie\u0017\u0002\b\r\u0006\u001cGo\u001c:z\u000311'o\\7Ta\u0016\u001c\u0017NZ5d)\r1&1\u000b\u0005\b\u0005+\"\u0003\u0019AAp\u0003\u0011\u0019w\u000e\u001c7\u0002%9,wo\u00159fG&4\u0017n\u0019\"vS2$WM]\u000b\u0002-\u0006y\u0011\u000e^3sC\ndWMR1di>\u0014\u00180\u0006\u0002\u0003`A)\u0011Q\tB1'&\u0019!1M.\u0003\u0015M+\u0017OR1di>\u0014\u00180A\u0003f[B$\u00180\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003lA\u0019\u0001K!\u001c\n\u0007\t=tI\u0001\u0004B]f\u0014VMZ\u0001\u000f\u0013:$\u0018iY2v[Vd\u0017\r^8s!\ta%fE\u0004+\u0005W\u00129H! \u0011\r\u0005\u0015#\u0011P(W\u0013\r\u0011Yh\u0017\u0002\u0018'B,7-\u001b4jG&#XM]1cY\u00164\u0015m\u0019;pef\u0004BAa \u0003\n6\u0011!\u0011\u0011\u0006\u0005\u0005\u0007\u0013))\u0001\u0002j_*\u0011!qQ\u0001\u0005U\u00064\u0018-C\u0002j\u0005\u0003#\"Aa\u001d\u0002\u001b\u0015l\u0007\u000f^=J]R\f%O]1z\u00039)W\u000e\u001d;z\u0013:$\u0018I\u001d:bs\u0002\n!#Z7qifLe\u000e^!se\u0006L\u0018I\u001d:bs\u0006\u0019R-\u001c9us&sG/\u0011:sCf\f%O]1zA\u0005ABo\u001c&bm\u0006Le\u000e^3hKJ\f5mY;nk2\fGo\u001c:\u0015\t\te%q\u0015\t\b\u0003\u000b\u0012IHa'W!\u0011\u0011iJa)\u000e\u0005\t}%\u0002\u0002BQ\u0005\u000b\u000bA\u0001\\1oO&!!Q\u0015BP\u0005\u001dIe\u000e^3hKJDqA!+1\u0001\u0004\u0011Y+\u0001\u0002jC:\u0011A*K\u0001\tgV\u0004\b\u000f\\5feV\u0011!\u0011\u0017\t\u0006\u0005g\u0013iLV\u0007\u0003\u0005kSAAa.\u0003:\u0006Aa-\u001e8di&|gN\u0003\u0003\u0003<\n\u0015\u0015\u0001B;uS2LAAa0\u00036\nA1+\u001e9qY&,'/A\u0003bI\u0012,'/\u0006\u0002\u0003FB)!1\u0017Bd-&!!\u0011\u001aB[\u00059y%M[%oi\u000e{gn];nKJ\f!BY8yK\u0012\fE\rZ3s+\t\u0011y\r\u0005\u0004\u00034\nEgkT\u0005\u0005\u0005'\u0014)L\u0001\u0006CS\u000e{gn];nKJ\fa!\\3sO\u0016\u0014XC\u0001Bm!\u0019\u0011\u0019L!5W-\u0006IaM]8n\u0003J\u0014\u0018-\u001f\u000b\u0004-\n}\u0007BBABk\u0001\u0007q\u000eF\u0002W\u0005GDqA!:7\u0001\u0004\ty.\u0001\u0002ji\u0006Qa.Z<Ck&dG-\u001a:\u0003%M+'/[1mSj\fG/[8o!J|\u00070_\u000b\u0005\u0005[\u001c\u0019a\u0005\u0003:\u0005Wz\u0016aA1dG\u0006!\u0011mY2!Q\rY$Q\u001f\t\u0004!\n]\u0018b\u0001B}\u000f\nIAO]1og&,g\u000e\u001e\u000b\u0005\u0005{\u001c9\u0001E\u0003\u0003\u0000f\u001a\t!D\u0001+!\u0011\t\u0019da\u0001\u0005\u000f\r\u0015\u0011H1\u0001\u0002R\t\t\u0011\t\u0003\u0004\u0003pr\u0002\rAV\u0001\u000be\u0016\u001cX\u000f\u001c;`I\u0015\fHc\u0001;\u0004\u000e!9\u0001PPA\u0001\u0002\u00041\u0016a\u0002:fgVdG\u000f\t\u0015\u0004\u007f\tU\u0018aC<sSR,wJ\u00196fGR$2\u0001^B\f\u0011\u001d\u0019I\u0002\u0011a\u0001\u00077\t1a\\;u!\u0011\u0011yh!\b\n\t\r}!\u0011\u0011\u0002\u0013\u001f\nTWm\u0019;PkR\u0004X\u000f^*ue\u0016\fW.\u0001\u0006sK\u0006$wJ\u00196fGR$2\u0001^B\u0013\u0011\u001d\u00199#\u0011a\u0001\u0007S\t!!\u001b8\u0011\t\t}41F\u0005\u0005\u0007[\u0011\tIA\tPE*,7\r^%oaV$8\u000b\u001e:fC6\f1B]3bIJ+7o\u001c7wKR\u001111\u0007\t\u0005\u0005;\u001b)$\u0003\u0003\u00048\t}%AB(cU\u0016\u001cG\u000f")
public final class IntAccumulator
extends Accumulator$mcI$sp<AnyAccumulator, IntAccumulator>
implements Serializable {
    private int[] current = IntAccumulator$.MODULE$.scala$jdk$IntAccumulator$$emptyIntArray();
    private int[][] history = IntAccumulator$.MODULE$.scala$jdk$IntAccumulator$$emptyIntArrayArray();

    public static IntAccumulator newBuilder() {
        return new IntAccumulator();
    }

    public static BiConsumer<IntAccumulator, IntAccumulator> merger() {
        return IntAccumulator$.MODULE$.merger();
    }

    public static BiConsumer<IntAccumulator, Object> boxedAdder() {
        return IntAccumulator$.MODULE$.boxedAdder();
    }

    public static ObjIntConsumer<IntAccumulator> adder() {
        return IntAccumulator$.MODULE$.adder();
    }

    public static Supplier<IntAccumulator> supplier() {
        return IntAccumulator$.MODULE$.supplier();
    }

    public static SpecificIterableFactory<Integer, IntAccumulator> toJavaIntegerAccumulator(IntAccumulator$ ia) {
        return IntAccumulator$.MODULE$;
    }

    public static Factory<Object, IntAccumulator> specificIterableFactory() {
        return IntAccumulator$.MODULE$;
    }

    public static Object fill(int n, Function0 elem) {
        IntAccumulator$ fill_this = IntAccumulator$.MODULE$;
        View.Fill fromSpecific_it = new View.Fill(n, elem);
        return fill_this.fromSpecific((IterableOnce)fromSpecific_it);
    }

    public int[] current() {
        return this.current;
    }

    public void current_$eq(int[] x$1) {
        this.current = x$1;
    }

    public int[][] history() {
        return this.history;
    }

    public void history_$eq(int[][] x$1) {
        this.history = x$1;
    }

    @Override
    public long cumulative(int i) {
        int[] x = this.history()[i];
        return (long)x[x.length - 2] << 32 | (long)x[x.length - 1] & 0xFFFFFFFFL;
    }

    @Override
    public String className() {
        return "IntAccumulator";
    }

    @Override
    public <S extends Stepper<?>> S efficientStepper(StepperShape<Object, S> shape) {
        IntAccumulatorStepper st = new IntAccumulatorStepper(this);
        if (shape.shape() == StepperShape$.MODULE$.IntShape()) {
            return (S)st;
        }
        if (!(shape.shape() == StepperShape$.MODULE$.ReferenceShape())) {
            throw new AssertionError((Object)new StringBuilder(18).append("assertion failed: ").append((Object)IntAccumulator.$anonfun$efficientStepper$1(shape)).toString());
        }
        return (S)new Stepper.EfficientSplit(st){};
    }

    private void expand() {
        if (this.index() > 0) {
            long cuml = (this.hIndex() > 0 ? this.cumulative(this.hIndex() - 1) : 0L) + (long)this.index();
            this.current()[this.current().length - 2] = (int)(cuml >>> 32);
            this.current()[this.current().length - 1] = (int)(cuml & 0xFFFFFFFFL);
            if (this.hIndex() >= this.history().length) {
                this.hExpand();
            }
            this.history()[this.hIndex()] = this.current();
            this.hIndex_$eq(this.hIndex() + 1);
        }
        this.current_$eq(new int[this.nextBlockSize() + 1]);
        this.index_$eq(0);
    }

    private void hExpand() {
        if (this.hIndex() == 0) {
            this.history_$eq(new int[4][]);
            return;
        }
        this.history_$eq((int[][])Arrays.copyOf(this.history(), this.history().length << 1));
    }

    public IntAccumulator addOne(int a) {
        this.totalSize_$eq(this.totalSize() + 1L);
        if (this.index() + 2 >= this.current().length) {
            this.expand();
        }
        this.current()[this.index()] = a;
        this.index_$eq(this.index() + 1);
        return this;
    }

    @Override
    public IntAccumulator result() {
        return this;
    }

    public void drain(IntAccumulator that) {
        int h2 = 0;
        long prev = 0L;
        boolean more = true;
        while (more && h2 < that.hIndex()) {
            long cuml = that.cumulative(h2);
            int n = (int)(cuml - prev);
            if (this.current().length - this.index() - 2 >= n) {
                System.arraycopy(that.history()[h2], 0, this.current(), this.index(), n);
                prev = cuml;
                this.index_$eq(this.index() + n);
                ++h2;
                continue;
            }
            more = false;
        }
        if (h2 >= that.hIndex() && this.current().length - this.index() - 2 >= that.index()) {
            if (that.index() > 0) {
                System.arraycopy(that.current(), 0, this.current(), this.index(), that.index());
            }
            this.index_$eq(this.index() + that.index());
        } else {
            long pv;
            int slots = (this.index() > 0 ? 1 : 0) + that.hIndex() - h2;
            if (this.hIndex() + slots > this.history().length) {
                int n = Math.max(4, 1 << 32 - Integer.numberOfLeadingZeros(1 + this.hIndex() + slots));
                this.history_$eq((int[][])Arrays.copyOf(this.history(), n));
            }
            long l = pv = this.hIndex() > 0 ? this.cumulative(this.hIndex() - 1) : 0L;
            if (this.index() > 0) {
                int[] nArray;
                if (this.index() < this.current().length >>> 3 && this.current().length - 1 > 32) {
                    int[] ans = Arrays.copyOf(this.current(), this.index() + 2);
                    ans[ans.length - 2] = this.current()[this.current().length - 2];
                    ans[ans.length - 1] = this.current()[this.current().length - 1];
                    nArray = ans;
                } else {
                    nArray = this.current();
                }
                int[] x = nArray;
                x[x.length - 2] = (int)((pv += (long)this.index()) >>> 32);
                x[x.length - 1] = (int)(pv & 0xFFFFFFFFL);
                this.history()[this.hIndex()] = x;
                this.hIndex_$eq(this.hIndex() + 1);
            }
            while (h2 < that.hIndex()) {
                long cuml = that.cumulative(h2);
                pv = pv + cuml - prev;
                prev = cuml;
                int[] x = that.history()[h2];
                x[x.length - 2] = (int)(pv >>> 32);
                x[x.length - 1] = (int)(pv & 0xFFFFFFFFL);
                this.history()[this.hIndex()] = x;
                ++h2;
                this.hIndex_$eq(this.hIndex() + 1);
            }
            this.index_$eq(that.index());
            this.current_$eq(that.current());
        }
        this.totalSize_$eq(this.totalSize() + that.totalSize());
        that.clear();
    }

    @Override
    public void clear() {
        super.clear();
        this.current_$eq(IntAccumulator$.MODULE$.scala$jdk$IntAccumulator$$emptyIntArray());
        this.history_$eq(IntAccumulator$.MODULE$.scala$jdk$IntAccumulator$$emptyIntArrayArray());
    }

    @Override
    public int apply(long ix) {
        if (this.totalSize() - ix <= (long)this.index() || this.hIndex() == 0) {
            return this.current()[(int)(ix - (this.totalSize() - (long)this.index()))];
        }
        long w = this.seekSlot(ix);
        return this.history()[(int)(w >>> 32)][(int)(w & 0xFFFFFFFFL)];
    }

    @Override
    public int apply(int i) {
        return this.apply((long)i);
    }

    public void update(long idx, int elem) {
        if (this.totalSize() - idx <= (long)this.index() || this.hIndex() == 0) {
            this.current()[(int)(idx - (this.totalSize() - (long)this.index()))] = elem;
            return;
        }
        long w = this.seekSlot(idx);
        this.history()[(int)(w >>> 32)][(int)(w & 0xFFFFFFFFL)] = elem;
    }

    @Override
    public void update(int idx, int elem) {
        this.update((long)idx, elem);
    }

    @Override
    public Iterator<Object> iterator() {
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var1_1 = null;
        return ((Accumulator)this).efficientStepper(stepper_shape).iterator();
    }

    @Override
    public <U> void foreach(Function1<Object, U> f) {
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var3_2 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            f.apply(s2.nextStep$mcI$sp());
        }
    }

    @Override
    public IntAccumulator map(Function1<Object, Object> f) {
        IntAccumulator b = new IntAccumulator();
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var4_3 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            b.addOne(f.apply$mcII$sp(s2.nextStep$mcI$sp()));
        }
        return b;
    }

    @Override
    public IntAccumulator flatMap(Function1<Object, IterableOnce<Object>> f) {
        IntAccumulator b = new IntAccumulator();
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var5_3 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            IterableOnce<Object> addAll_xs = f.apply(s2.nextStep$mcI$sp());
            Growable.addAll$(b, addAll_xs);
            Object var4_5 = null;
        }
        return b;
    }

    @Override
    public IntAccumulator collect(PartialFunction<Object, Object> pf) {
        IntAccumulator b = new IntAccumulator();
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var5_3 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            int n = s2.nextStep$mcI$sp();
            pf.runWith((Function1<Object, IntAccumulator> & Serializable)(B a) -> b.addOne(BoxesRunTime.unboxToInt(a))).apply$mcZI$sp(n);
        }
        return b;
    }

    private IntAccumulator filterAccImpl(Function1<Object, Object> pred, boolean not) {
        IntAccumulator b = new IntAccumulator();
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var6_4 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            int n = s2.nextStep$mcI$sp();
            if (pred.apply$mcZI$sp(n) == not) continue;
            b.addOne(n);
        }
        return b;
    }

    @Override
    public IntAccumulator filter(Function1<Object, Object> pred) {
        boolean filterAccImpl_not = false;
        IntAccumulator filterAccImpl_b = new IntAccumulator();
        StepperShape<Object, IntStepper> filterAccImpl_stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var6_4 = null;
        IntStepper filterAccImpl_s = ((Accumulator)this).efficientStepper(filterAccImpl_stepper_shape);
        while (filterAccImpl_s.hasStep()) {
            int filterAccImpl_n = filterAccImpl_s.nextStep$mcI$sp();
            if (pred.apply$mcZI$sp(filterAccImpl_n) == filterAccImpl_not) continue;
            filterAccImpl_b.addOne(filterAccImpl_n);
        }
        return filterAccImpl_b;
    }

    @Override
    public IntAccumulator filterNot(Function1<Object, Object> pred) {
        boolean filterAccImpl_not = true;
        IntAccumulator filterAccImpl_b = new IntAccumulator();
        StepperShape<Object, IntStepper> filterAccImpl_stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var6_4 = null;
        IntStepper filterAccImpl_s = ((Accumulator)this).efficientStepper(filterAccImpl_stepper_shape);
        while (filterAccImpl_s.hasStep()) {
            int filterAccImpl_n = filterAccImpl_s.nextStep$mcI$sp();
            if (pred.apply$mcZI$sp(filterAccImpl_n) == filterAccImpl_not) continue;
            filterAccImpl_b.addOne(filterAccImpl_n);
        }
        return filterAccImpl_b;
    }

    @Override
    public boolean forall(Function1<Object, Object> p) {
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var3_2 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            if (p.apply$mcZI$sp(s2.nextStep$mcI$sp())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean exists(Function1<Object, Object> p) {
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var3_2 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            if (!p.apply$mcZI$sp(s2.nextStep$mcI$sp())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int count(Function1<Object, Object> p) {
        int r = 0;
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var4_3 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            if (!p.apply$mcZI$sp(s2.nextStep$mcI$sp())) continue;
            ++r;
        }
        return r;
    }

    public long countLong(Function1<Object, Object> p) {
        long r = 0L;
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var5_3 = null;
        IntStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            if (!p.apply$mcZI$sp(s2.nextStep$mcI$sp())) continue;
            ++r;
        }
        return r;
    }

    public int[] toArray() {
        if (this.totalSize() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(new StringBuilder(44).append("Too many elements accumulated for an array: ").append(Long.toString(this.totalSize())).toString());
        }
        int[] a = new int[(int)this.totalSize()];
        int j = 0;
        long pv = 0L;
        for (int h2 = 0; h2 < this.hIndex(); ++h2) {
            int[] x = this.history()[h2];
            long cuml = this.cumulative(h2);
            int n = (int)(cuml - pv);
            pv = cuml;
            System.arraycopy(x, 0, a, j, n);
            j += n;
        }
        System.arraycopy(this.current(), 0, a, j, this.index());
        this.index();
        return a;
    }

    @Override
    public List<Object> toList() {
        int i;
        List ans = Nil$.MODULE$;
        for (i = this.index() - 1; i >= 0; --i) {
            int n = this.current()[i];
            ans = ans.$colon$colon(n);
        }
        for (int h2 = this.hIndex() - 1; h2 >= 0; --h2) {
            int[] a = this.history()[h2];
            for (i = (int)(this.cumulative(h2) - (h2 == 0 ? 0L : this.cumulative(h2 - 1))) - 1; i >= 0; --i) {
                int n = a[i];
                ans = ans.$colon$colon(n);
            }
        }
        return ans;
    }

    @Override
    public <C1> C1 to(Factory<Object, C1> factory2) {
        if (this.totalSize() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(new StringBuilder(54).append("Too many elements accumulated for a Scala collection: ").append(Long.toString(this.totalSize())).toString());
        }
        return factory2.fromSpecific(this.iterator());
    }

    @Override
    public IntAccumulator fromSpecific(IterableOnce<Object> coll) {
        return IntAccumulator$.MODULE$.fromSpecific((IterableOnce)coll);
    }

    public IntAccumulator newSpecificBuilder() {
        return new IntAccumulator();
    }

    @Override
    public SeqFactory<AnyAccumulator> iterableFactory() {
        return AnyAccumulator$.MODULE$;
    }

    @Override
    public IntAccumulator empty() {
        return new IntAccumulator();
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    @Override
    public int apply$mcII$sp(int i) {
        return this.apply((long)i);
    }

    public static final /* synthetic */ String $anonfun$efficientStepper$1(StepperShape shape$1) {
        return new StringBuilder(25).append("unexpected StepperShape: ").append(shape$1).toString();
    }

    public static class SerializationProxy<A>
    implements Serializable {
        private final transient IntAccumulator acc;
        private transient IntAccumulator result;

        private IntAccumulator acc() {
            return this.acc;
        }

        private IntAccumulator result() {
            return this.result;
        }

        private void result_$eq(IntAccumulator x$1) {
            this.result = x$1;
        }

        /*
         * WARNING - void declaration
         */
        private void writeObject(ObjectOutputStream out) {
            void stepper_shape;
            out.defaultWriteObject();
            IntAccumulator intAccumulator = this.acc();
            if (intAccumulator == null) {
                throw null;
            }
            IntAccumulator sizeLong_this = intAccumulator;
            Object var5_2 = null;
            long size = sizeLong_this.totalSize();
            out.writeLong(size);
            IntAccumulator intAccumulator2 = this.acc();
            StepperShape<Object, IntStepper> stepperShape = StepperShape$.MODULE$.intStepperShape();
            if (intAccumulator2 == null) {
                throw null;
            }
            stepperShape = null;
            IntStepper st = (IntStepper)((Accumulator)intAccumulator2).efficientStepper(stepper_shape);
            while (st.hasStep()) {
                out.writeInt(st.nextStep$mcI$sp());
            }
        }

        /*
         * WARNING - void declaration
         */
        private void readObject(ObjectInputStream in) {
            in.defaultReadObject();
            IntAccumulator res = new IntAccumulator();
            for (long elems = in.readLong(); elems > 0L; --elems) {
                void var5_4;
                int boxToInteger_i = in.readInt();
                res.addOne((int)var5_4);
            }
            this.result_$eq(res);
        }

        private Object readResolve() {
            return this.result();
        }

        public SerializationProxy(IntAccumulator acc) {
            this.acc = acc;
        }
    }
}

