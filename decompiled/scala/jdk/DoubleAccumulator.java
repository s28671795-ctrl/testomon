/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import scala.Function0;
import scala.Function1;
import scala.PartialFunction;
import scala.collection.DoubleStepper;
import scala.collection.Factory;
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
import scala.jdk.Accumulator$mcD$sp;
import scala.jdk.AnyAccumulator;
import scala.jdk.AnyAccumulator$;
import scala.jdk.DoubleAccumulator$;
import scala.jdk.DoubleAccumulatorStepper;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\rub\u0001\u0002#F\u0005)CQa\u001b\u0001\u0005\u00021D\u0001\"\u001c\u0001A\u0002\u0013\u0005QI\u001c\u0005\te\u0002\u0001\r\u0011\"\u0001Fg\"1\u0011\u0010\u0001Q!\n=D\u0001B\u001f\u0001A\u0002\u0013\u0005Qi\u001f\u0005\t{\u0002\u0001\r\u0011\"\u0001F}\"9\u0011\u0011\u0001\u0001!B\u0013a\b\u0002CA\u0002\u0001\u0011\u0005Q)!\u0002\t\u0011\u0005]\u0001\u0001)C)\u00033Aq!a\u000b\u0001\t\u0003\ti\u0003C\u0004\u0002|\u0001!I!! \t\u000f\u0005}\u0004\u0001\"\u0003\u0002~!9\u0011\u0011\u0011\u0001\u0005\u0002\u0005\r\u0005BBAF\u0001\u0011\u0005C\u000eC\u0004\u0002\u000e\u0002!\t!a$\t\u000f\u0005U\u0005\u0001\"\u0011\u0002~!9\u0011q\u0013\u0001\u0005\u0002\u0005e\u0005bBAL\u0001\u0011\u0005\u0011q\u0014\u0005\b\u0003G\u0003A\u0011AAS\u0011\u001d\t\u0019\u000b\u0001C\u0001\u0003_Cq!!.\u0001\t\u0003\t9\fC\u0004\u0002@\u0002!\t%!1\t\u000f\u0005U\u0007\u0001\"\u0001\u0002X\"9\u0011Q\u001c\u0001\u0005\u0002\u0005}\u0007bBAv\u0001\u0011\u0005\u0011Q\u001e\u0005\b\u0003s\u0004A\u0011BA~\u0011\u001d\u0011i\u0001\u0001C!\u0005\u001fAqAa\u0005\u0001\t\u0003\u0012)\u0002C\u0004\u0003\u001a\u0001!\tEa\u0007\t\u000f\t\u0005\u0002\u0001\"\u0011\u0003$!9!q\u0005\u0001\u0005B\t%\u0002b\u0002B\u0017\u0001\u0011\u0005!q\u0006\u0005\u0007\u0005g\u0001A\u0011\u00018\t\u000f\tU\u0002\u0001\"\u0011\u00038!9!q\b\u0001\u0005B\t\u0005\u0003b\u0002B+\u0001\u0011E#q\u000b\u0005\b\u0005;\u0002A\u0011\u000bB0\u0011\u001d\u0011\t\u0007\u0001C!\u0005GBqAa\u001b\u0001\t\u0003\u0012y\u0006C\u0004\u0003n\u0001!IAa\u001c\b\u000f\t]T\t#\u0001\u0003z\u00191A)\u0012E\u0001\u0005wBaa\u001b\u0016\u0005\u0002\tE\u0005\u0002\u0003BJU\t\u0007I\u0011\u00028\t\u000f\tU%\u0006)A\u0005_\"A!q\u0013\u0016C\u0002\u0013%1\u0010C\u0004\u0003\u001a*\u0002\u000b\u0011\u0002?\t\u000f\tm%\u0006b\u0001\u0003\u001e\"9!\u0011\u0017\u0016\u0005\u0002\tM\u0006b\u0002BcU\u0011\u0005!q\u0019\u0005\b\u0005\u001fTC\u0011\u0001Bi\u0011\u001d\u0011IN\u000bC\u0001\u00057DqAa8+\t\u0013\u0011\t\u000fC\u0004\u0003V)\"\tE!:\t\u000f\t-$\u0006\"\u0011\u0003`!9!1\u001e\u0016\u0005B\t}cA\u0002BwU\u0001\u0011y\u000f\u0003\u0006\u0003tf\u0012)\u0019!C\u0005\u0005?B\u0011B!>:\u0005\u0003\u0005\u000b\u0011\u0002,\t\r-LD\u0011\u0001B\u0000\u0011-\tY)\u000fa\u0001\u0002\u0004%IAa\u0018\t\u0017\r5\u0011\b1AA\u0002\u0013%1q\u0002\u0005\u000b\u0007'I\u0004\u0019!A!B\u00131\u0006bBB\fs\u0011%1\u0011\u0004\u0005\b\u0007KID\u0011BB\u0014\u0011\u001d\u0019\u0019$\u000fC\u0005\u0005_B\u0011B!\u001c+\u0003\u0003%Ia!\u000e\u0003#\u0011{WO\u00197f\u0003\u000e\u001cW/\\;mCR|'O\u0003\u0002G\u000f\u0006\u0019!\u000eZ6\u000b\u0003!\u000bQa]2bY\u0006\u001c\u0001a\u0005\u0003\u0001\u0017^{\u0006#\u0002'N\u001fN3V\"A#\n\u00059+%aC!dGVlW\u000f\\1u_J\u0004\"\u0001U)\u000e\u0003\u001dK!AU$\u0003\r\u0011{WO\u00197f!\taE+\u0003\u0002V\u000b\nq\u0011I\\=BG\u000e,X.\u001e7bi>\u0014\bC\u0001'\u0001!\u0015AVlT*W\u001b\u0005I&B\u0001.\\\u0003\u001diW\u000f^1cY\u0016T!\u0001X$\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002_3\n11+Z9PaN\u0004\"\u0001\u00195\u000f\u0005\u00054gB\u00012f\u001b\u0005\u0019'B\u00013J\u0003\u0019a$o\\8u}%\t\u0001*\u0003\u0002h\u000f\u00069\u0001/Y2lC\u001e,\u0017BA5k\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t9w)\u0001\u0004=S:LGO\u0010\u000b\u0002-\u000691-\u001e:sK:$X#A8\u0011\u0007A\u0003x*\u0003\u0002r\u000f\n)\u0011I\u001d:bs\u0006Y1-\u001e:sK:$x\fJ3r)\t!x\u000f\u0005\u0002Qk&\u0011ao\u0012\u0002\u0005+:LG\u000fC\u0004y\u0007\u0005\u0005\t\u0019A8\u0002\u0007a$\u0013'\u0001\u0005dkJ\u0014XM\u001c;!\u0003\u001dA\u0017n\u001d;pef,\u0012\u0001 \t\u0004!B|\u0017a\u00035jgR|'/_0%KF$\"\u0001^@\t\u000fa4\u0011\u0011!a\u0001y\u0006A\u0001.[:u_JL\b%\u0001\u0006dk6,H.\u0019;jm\u0016$B!a\u0002\u0002\u000eA\u0019\u0001+!\u0003\n\u0007\u0005-qI\u0001\u0003M_:<\u0007bBA\b\u0011\u0001\u0007\u0011\u0011C\u0001\u0002SB\u0019\u0001+a\u0005\n\u0007\u0005UqIA\u0002J]R\f\u0011b\u00197bgNt\u0015-\\3\u0016\u0005\u0005m\u0001\u0003BA\u000f\u0003KqA!a\b\u0002\"A\u0011!mR\u0005\u0004\u0003G9\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0002(\u0005%\"AB*ue&twMC\u0002\u0002$\u001d\u000b\u0001#\u001a4gS\u000eLWM\u001c;Ti\u0016\u0004\b/\u001a:\u0016\t\u0005=\u00121\b\u000b\u0005\u0003c\t\tH\u0005\u0004\u00024\u0005]\u0012q\f\u0004\u0007\u0003k\u0001\u0001!!\r\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\t\u0005e\u00121\b\u0007\u0001\t\u001d\tiD\u0003b\u0001\u0003\u007f\u0011\u0011aU\t\u0005\u0003\u0003\n9\u0005E\u0002Q\u0003\u0007J1!!\u0012H\u0005\u001dqu\u000e\u001e5j]\u001e\u0004D!!\u0013\u0002TA1\u00111JA'\u0003#j\u0011aW\u0005\u0004\u0003\u001fZ&aB*uKB\u0004XM\u001d\t\u0005\u0003s\t\u0019\u0006\u0002\u0007\u0002V\u0005m\u0012\u0011!A\u0001\u0006\u0003\t9FA\u0002`IE\nB!!\u0011\u0002ZA\u0019\u0001+a\u0017\n\u0007\u0005usIA\u0002B]f\u0004B!!\u0019\u0002l9!\u00111MA4\u001d\r\t\u0017QM\u0005\u00039\u001eK1!!\u001b\\\u0003\u001d\u0019F/\u001a9qKJLA!!\u001c\u0002p\tqQI\u001a4jG&,g\u000e^*qY&$(bAA57\"9\u00111\u000f\u0006A\u0004\u0005U\u0014!B:iCB,\u0007cBA&\u0003oz\u0015qG\u0005\u0004\u0003sZ&\u0001D*uKB\u0004XM]*iCB,\u0017AB3ya\u0006tG\rF\u0001u\u0003\u001dAW\t\u001f9b]\u0012\fa!\u00193e\u001f:,G\u0003BAC\u0003\u000fk\u0011\u0001\u0001\u0005\u0007\u0003\u0013k\u0001\u0019A(\u0002\u0003\u0005\faA]3tk2$\u0018!\u00023sC&tGc\u0001;\u0002\u0012\"1\u00111S\bA\u0002Y\u000bA\u0001\u001e5bi\u0006)1\r\\3be\u0006)\u0011\r\u001d9msR\u0019q*a'\t\u000f\u0005u\u0015\u00031\u0001\u0002\b\u0005\u0011\u0011\u000e\u001f\u000b\u0004\u001f\u0006\u0005\u0006bBA\b%\u0001\u0007\u0011\u0011C\u0001\u0007kB$\u0017\r^3\u0015\u000bQ\f9+a+\t\u000f\u0005%6\u00031\u0001\u0002\b\u0005\u0019\u0011\u000e\u001a=\t\r\u000556\u00031\u0001P\u0003\u0011)G.Z7\u0015\u000bQ\f\t,a-\t\u000f\u0005%F\u00031\u0001\u0002\u0012!1\u0011Q\u0016\u000bA\u0002=\u000b\u0001\"\u001b;fe\u0006$xN]\u000b\u0003\u0003s\u0003B\u0001YA^\u001f&\u0019\u0011Q\u00186\u0003\u0011%#XM]1u_J\fqAZ8sK\u0006\u001c\u0007.\u0006\u0003\u0002D\u0006EGc\u0001;\u0002F\"9\u0011q\u0019\fA\u0002\u0005%\u0017!\u00014\u0011\rA\u000bYmTAh\u0013\r\tim\u0012\u0002\n\rVt7\r^5p]F\u0002B!!\u000f\u0002R\u00129\u00111\u001b\fC\u0002\u0005]#!A+\u0002\u00075\f\u0007\u000fF\u0002W\u00033Dq!a2\u0018\u0001\u0004\tY\u000eE\u0003Q\u0003\u0017|u*A\u0004gY\u0006$X*\u00199\u0015\u0007Y\u000b\t\u000fC\u0004\u0002Hb\u0001\r!a9\u0011\rA\u000bYmTAs!\u0011\u0001\u0017q](\n\u0007\u0005%(N\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW-A\u0004d_2dWm\u0019;\u0015\u0007Y\u000by\u000fC\u0004\u0002rf\u0001\r!a=\u0002\u0005A4\u0007#\u0002)\u0002v>{\u0015bAA|\u000f\ny\u0001+\u0019:uS\u0006dg)\u001e8di&|g.A\u0007gS2$XM]!dG&k\u0007\u000f\u001c\u000b\u0006-\u0006u(\u0011\u0002\u0005\b\u0003\u007fT\u0002\u0019\u0001B\u0001\u0003\u0011\u0001(/\u001a3\u0011\rA\u000bYm\u0014B\u0002!\r\u0001&QA\u0005\u0004\u0005\u000f9%a\u0002\"p_2,\u0017M\u001c\u0005\b\u0005\u0017Q\u0002\u0019\u0001B\u0002\u0003\rqw\u000e^\u0001\u0007M&dG/\u001a:\u0015\u0007Y\u0013\t\u0002C\u0004\u0002\u0000n\u0001\rA!\u0001\u0002\u0013\u0019LG\u000e^3s\u001d>$Hc\u0001,\u0003\u0018!9\u0011q \u000fA\u0002\t\u0005\u0011A\u00024pe\u0006dG\u000e\u0006\u0003\u0003\u0004\tu\u0001b\u0002B\u0010;\u0001\u0007!\u0011A\u0001\u0002a\u00061Q\r_5tiN$BAa\u0001\u0003&!9!q\u0004\u0010A\u0002\t\u0005\u0011!B2pk:$H\u0003BA\t\u0005WAqAa\b \u0001\u0004\u0011\t!A\u0005d_VtG\u000fT8oOR!\u0011q\u0001B\u0019\u0011\u001d\u0011y\u0002\ta\u0001\u0005\u0003\tq\u0001^8BeJ\f\u00170\u0001\u0004u_2K7\u000f^\u000b\u0003\u0005s\u0001B\u0001\u0019B\u001e\u001f&\u0019!Q\b6\u0003\t1K7\u000f^\u0001\u0003i>,BAa\u0011\u0003HQ!!Q\tB&!\u0011\tIDa\u0012\u0005\u000f\t%3E1\u0001\u0002X\t\u00111)\r\u0005\b\u0005\u001b\u001a\u0003\u0019\u0001B(\u0003\u001d1\u0017m\u0019;pef\u0004r!a\u0013\u0003R=\u0013)%C\u0002\u0003Tm\u0013qAR1di>\u0014\u00180\u0001\u0007ge>l7\u000b]3dS\u001aL7\rF\u0002W\u00053BqAa\u0017%\u0001\u0004\t)/\u0001\u0003d_2d\u0017A\u00058foN\u0003XmY5gS\u000e\u0014U/\u001b7eKJ,\u0012AV\u0001\u0010SR,'/\u00192mK\u001a\u000b7\r^8ssV\u0011!Q\r\t\u0006\u0003\u0017\u00129gU\u0005\u0004\u0005SZ&AC*fc\u001a\u000b7\r^8ss\u0006)Q-\u001c9us\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011!\u0011\u000f\t\u0004!\nM\u0014b\u0001B;\u000f\n1\u0011I\\=SK\u001a\f\u0011\u0003R8vE2,\u0017iY2v[Vd\u0017\r^8s!\ta%fE\u0004+\u0005c\u0012iHa!\u0011\r\u0005-#qP(W\u0013\r\u0011\ti\u0017\u0002\u0018'B,7-\u001b4jG&#XM]1cY\u00164\u0015m\u0019;pef\u0004BA!\"\u0003\u00106\u0011!q\u0011\u0006\u0005\u0005\u0013\u0013Y)\u0001\u0002j_*\u0011!QR\u0001\u0005U\u00064\u0018-C\u0002j\u0005\u000f#\"A!\u001f\u0002!\u0015l\u0007\u000f^=E_V\u0014G.Z!se\u0006L\u0018!E3naRLHi\\;cY\u0016\f%O]1zA\u0005)R-\u001c9us\u0012{WO\u00197f\u0003J\u0014\u0018-_!se\u0006L\u0018AF3naRLHi\\;cY\u0016\f%O]1z\u0003J\u0014\u0018-\u001f\u0011\u0002/Q|'*\u0019<b\t>,(\r\\3BG\u000e,X.\u001e7bi>\u0014H\u0003\u0002BP\u0005W\u0003r!a\u0013\u0003\u0000\t\u0005f\u000b\u0005\u0003\u0003$\n%VB\u0001BS\u0015\u0011\u00119Ka#\u0002\t1\fgnZ\u0005\u0004%\n\u0015\u0006b\u0002BWa\u0001\u0007!qV\u0001\u0003S\u0006t!\u0001T\u0015\u0002\u0011M,\b\u000f\u001d7jKJ,\"A!.\u0011\u000b\t]&\u0011\u0019,\u000e\u0005\te&\u0002\u0002B^\u0005{\u000b\u0001BZ;oGRLwN\u001c\u0006\u0005\u0005\u007f\u0013Y)\u0001\u0003vi&d\u0017\u0002\u0002Bb\u0005s\u0013\u0001bU;qa2LWM]\u0001\u0006C\u0012$WM]\u000b\u0003\u0005\u0013\u0004RAa.\u0003LZKAA!4\u0003:\n\trJ\u00196E_V\u0014G.Z\"p]N,X.\u001a:\u0002\u0015\t|\u00070\u001a3BI\u0012,'/\u0006\u0002\u0003TB1!q\u0017Bk->KAAa6\u0003:\nQ!)[\"p]N,X.\u001a:\u0002\r5,'oZ3s+\t\u0011i\u000e\u0005\u0004\u00038\nUgKV\u0001\nMJ|W.\u0011:sCf$2A\u0016Br\u0011\u0019\tI)\u000ea\u0001_R\u0019aKa:\t\u000f\t%h\u00071\u0001\u0002f\u0006\u0011\u0011\u000e^\u0001\u000b]\u0016<()^5mI\u0016\u0014(AE*fe&\fG.\u001b>bi&|g\u000e\u0015:pqf,BA!=\u0004\bM!\u0011H!\u001d`\u0003\r\t7mY\u0001\u0005C\u000e\u001c\u0007\u0005K\u0002<\u0005s\u00042\u0001\u0015B~\u0013\r\u0011ip\u0012\u0002\niJ\fgn]5f]R$Ba!\u0001\u0004\fA)11A\u001d\u0004\u00065\t!\u0006\u0005\u0003\u0002:\r\u001dAaBB\u0005s\t\u0007\u0011q\u000b\u0002\u0002\u0003\"1!1\u001f\u001fA\u0002Y\u000b!B]3tk2$x\fJ3r)\r!8\u0011\u0003\u0005\bqz\n\t\u00111\u0001W\u0003\u001d\u0011Xm];mi\u0002B3a\u0010B}\u0003-9(/\u001b;f\u001f\nTWm\u0019;\u0015\u0007Q\u001cY\u0002C\u0004\u0004\u001e\u0001\u0003\raa\b\u0002\u0007=,H\u000f\u0005\u0003\u0003\u0006\u000e\u0005\u0012\u0002BB\u0012\u0005\u000f\u0013!c\u00142kK\u000e$x*\u001e;qkR\u001cFO]3b[\u0006Q!/Z1e\u001f\nTWm\u0019;\u0015\u0007Q\u001cI\u0003C\u0004\u0004,\u0005\u0003\ra!\f\u0002\u0005%t\u0007\u0003\u0002BC\u0007_IAa!\r\u0003\b\n\trJ\u00196fGRLe\u000e];u'R\u0014X-Y7\u0002\u0017I,\u0017\r\u001a*fg>dg/\u001a\u000b\u0003\u0007o\u0001BAa)\u0004:%!11\bBS\u0005\u0019y%M[3di\u0002")
public final class DoubleAccumulator
extends Accumulator$mcD$sp<AnyAccumulator, DoubleAccumulator>
implements Serializable {
    private double[] current = DoubleAccumulator$.MODULE$.scala$jdk$DoubleAccumulator$$emptyDoubleArray();
    private double[][] history = DoubleAccumulator$.MODULE$.scala$jdk$DoubleAccumulator$$emptyDoubleArrayArray();

    public static DoubleAccumulator newBuilder() {
        return new DoubleAccumulator();
    }

    public static BiConsumer<DoubleAccumulator, DoubleAccumulator> merger() {
        return DoubleAccumulator$.MODULE$.merger();
    }

    public static BiConsumer<DoubleAccumulator, Object> boxedAdder() {
        return DoubleAccumulator$.MODULE$.boxedAdder();
    }

    public static ObjDoubleConsumer<DoubleAccumulator> adder() {
        return DoubleAccumulator$.MODULE$.adder();
    }

    public static Supplier<DoubleAccumulator> supplier() {
        return DoubleAccumulator$.MODULE$.supplier();
    }

    public static SpecificIterableFactory<Double, DoubleAccumulator> toJavaDoubleAccumulator(DoubleAccumulator$ ia) {
        return DoubleAccumulator$.MODULE$;
    }

    public static Factory<Object, DoubleAccumulator> specificIterableFactory() {
        return DoubleAccumulator$.MODULE$;
    }

    public static Object fill(int n, Function0 elem) {
        DoubleAccumulator$ fill_this = DoubleAccumulator$.MODULE$;
        View.Fill fromSpecific_it = new View.Fill(n, elem);
        return fill_this.fromSpecific((IterableOnce)fromSpecific_it);
    }

    public double[] current() {
        return this.current;
    }

    public void current_$eq(double[] x$1) {
        this.current = x$1;
    }

    public double[][] history() {
        return this.history;
    }

    public void history_$eq(double[][] x$1) {
        this.history = x$1;
    }

    @Override
    public long cumulative(int i) {
        double[] x = this.history()[i];
        return (long)x[x.length - 1];
    }

    @Override
    public String className() {
        return "DoubleAccumulator";
    }

    @Override
    public <S extends Stepper<?>> S efficientStepper(StepperShape<Object, S> shape) {
        DoubleAccumulatorStepper st = new DoubleAccumulatorStepper(this);
        if (shape.shape() == StepperShape$.MODULE$.DoubleShape()) {
            return (S)st;
        }
        if (!(shape.shape() == StepperShape$.MODULE$.ReferenceShape())) {
            throw new AssertionError((Object)new StringBuilder(18).append("assertion failed: ").append((Object)DoubleAccumulator.$anonfun$efficientStepper$1(shape)).toString());
        }
        return (S)new Stepper.EfficientSplit(st){};
    }

    private void expand() {
        if (this.index() > 0) {
            double d;
            double[] dArray = this.current();
            int n = this.current().length - 1;
            if (this.hIndex() > 0) {
                double[] x = this.history()[this.hIndex() - 1];
                d = x[x.length - 1];
            } else {
                d = 0.0;
            }
            dArray[n] = d + (double)this.index();
            if (this.hIndex() >= this.history().length) {
                this.hExpand();
            }
            this.history()[this.hIndex()] = this.current();
            this.hIndex_$eq(this.hIndex() + 1);
        }
        this.current_$eq(new double[this.nextBlockSize() + 1]);
        this.index_$eq(0);
    }

    private void hExpand() {
        if (this.hIndex() == 0) {
            this.history_$eq(new double[4][]);
            return;
        }
        this.history_$eq((double[][])Arrays.copyOf(this.history(), this.history().length << 1));
    }

    public DoubleAccumulator addOne(double a) {
        this.totalSize_$eq(this.totalSize() + 1L);
        if (this.index() + 1 >= this.current().length) {
            this.expand();
        }
        this.current()[this.index()] = a;
        this.index_$eq(this.index() + 1);
        return this;
    }

    @Override
    public DoubleAccumulator result() {
        return this;
    }

    public void drain(DoubleAccumulator that) {
        int h2 = 0;
        long prev = 0L;
        boolean more = true;
        while (more && h2 < that.hIndex()) {
            long cuml = that.cumulative(h2);
            int n = (int)(cuml - prev);
            if (this.current().length - this.index() - 1 >= n) {
                System.arraycopy(that.history()[h2], 0, this.current(), this.index(), n);
                prev = cuml;
                this.index_$eq(this.index() + n);
                ++h2;
                continue;
            }
            more = false;
        }
        if (h2 >= that.hIndex() && this.current().length - this.index() - 1 >= that.index()) {
            if (that.index() > 0) {
                System.arraycopy(that.current(), 0, this.current(), this.index(), that.index());
            }
            this.index_$eq(this.index() + that.index());
        } else {
            long pv;
            int slots = (this.index() > 0 ? 1 : 0) + that.hIndex() - h2;
            if (this.hIndex() + slots > this.history().length) {
                int n = Math.max(4, 1 << 32 - Integer.numberOfLeadingZeros(1 + this.hIndex() + slots));
                this.history_$eq((double[][])Arrays.copyOf(this.history(), n));
            }
            long l = pv = this.hIndex() > 0 ? this.cumulative(this.hIndex() - 1) : 0L;
            if (this.index() > 0) {
                double[] dArray;
                if (this.index() < this.current().length >>> 3 && this.current().length - 1 > 32) {
                    double[] ans = Arrays.copyOf(this.current(), this.index() + 1);
                    ans[ans.length - 1] = this.current()[this.current().length - 1];
                    dArray = ans;
                } else {
                    dArray = this.current();
                }
                double[] x = dArray;
                x[x.length - 1] = pv += (long)this.index();
                this.history()[this.hIndex()] = x;
                this.hIndex_$eq(this.hIndex() + 1);
            }
            while (h2 < that.hIndex()) {
                long cuml = that.cumulative(h2);
                pv = pv + cuml - prev;
                prev = cuml;
                double[] x = that.history()[h2];
                x[x.length - 1] = pv;
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
        this.current_$eq(DoubleAccumulator$.MODULE$.scala$jdk$DoubleAccumulator$$emptyDoubleArray());
        this.history_$eq(DoubleAccumulator$.MODULE$.scala$jdk$DoubleAccumulator$$emptyDoubleArrayArray());
    }

    @Override
    public double apply(long ix) {
        if (this.totalSize() - ix <= (long)this.index() || this.hIndex() == 0) {
            return this.current()[(int)(ix - (this.totalSize() - (long)this.index()))];
        }
        long w = this.seekSlot(ix);
        return this.history()[(int)(w >>> 32)][(int)(w & 0xFFFFFFFFL)];
    }

    @Override
    public double apply(int i) {
        return this.apply((long)i);
    }

    public void update(long idx, double elem) {
        if (this.totalSize() - idx <= (long)this.index() || this.hIndex() == 0) {
            this.current()[(int)(idx - (this.totalSize() - (long)this.index()))] = elem;
            return;
        }
        long w = this.seekSlot(idx);
        this.history()[(int)(w >>> 32)][(int)(w & 0xFFFFFFFFL)] = elem;
    }

    @Override
    public void update(int idx, double elem) {
        this.update((long)idx, elem);
    }

    @Override
    public Iterator<Object> iterator() {
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var1_1 = null;
        return ((Accumulator)this).efficientStepper(stepper_shape).iterator();
    }

    @Override
    public <U> void foreach(Function1<Object, U> f) {
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var3_2 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            f.apply(s2.nextStep$mcD$sp());
        }
    }

    @Override
    public DoubleAccumulator map(Function1<Object, Object> f) {
        DoubleAccumulator b = new DoubleAccumulator();
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var4_3 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            b.addOne(f.apply$mcDD$sp(s2.nextStep$mcD$sp()));
        }
        return b;
    }

    @Override
    public DoubleAccumulator flatMap(Function1<Object, IterableOnce<Object>> f) {
        DoubleAccumulator b = new DoubleAccumulator();
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var5_3 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            IterableOnce<Object> addAll_xs = f.apply(s2.nextStep$mcD$sp());
            Growable.addAll$(b, addAll_xs);
            Object var4_5 = null;
        }
        return b;
    }

    @Override
    public DoubleAccumulator collect(PartialFunction<Object, Object> pf) {
        DoubleAccumulator b = new DoubleAccumulator();
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var6_3 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            double n = s2.nextStep$mcD$sp();
            pf.runWith((Function1<Object, DoubleAccumulator> & Serializable)(B a) -> b.addOne(BoxesRunTime.unboxToDouble(a))).apply$mcZD$sp(n);
        }
        return b;
    }

    private DoubleAccumulator filterAccImpl(Function1<Object, Object> pred, boolean not) {
        DoubleAccumulator b = new DoubleAccumulator();
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var7_4 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            double n = s2.nextStep$mcD$sp();
            if (pred.apply$mcZD$sp(n) == not) continue;
            b.addOne(n);
        }
        return b;
    }

    @Override
    public DoubleAccumulator filter(Function1<Object, Object> pred) {
        boolean filterAccImpl_not = false;
        DoubleAccumulator filterAccImpl_b = new DoubleAccumulator();
        StepperShape<Object, DoubleStepper> filterAccImpl_stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var7_4 = null;
        DoubleStepper filterAccImpl_s = ((Accumulator)this).efficientStepper(filterAccImpl_stepper_shape);
        while (filterAccImpl_s.hasStep()) {
            double filterAccImpl_n = filterAccImpl_s.nextStep$mcD$sp();
            if (pred.apply$mcZD$sp(filterAccImpl_n) == filterAccImpl_not) continue;
            filterAccImpl_b.addOne(filterAccImpl_n);
        }
        return filterAccImpl_b;
    }

    @Override
    public DoubleAccumulator filterNot(Function1<Object, Object> pred) {
        boolean filterAccImpl_not = true;
        DoubleAccumulator filterAccImpl_b = new DoubleAccumulator();
        StepperShape<Object, DoubleStepper> filterAccImpl_stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var7_4 = null;
        DoubleStepper filterAccImpl_s = ((Accumulator)this).efficientStepper(filterAccImpl_stepper_shape);
        while (filterAccImpl_s.hasStep()) {
            double filterAccImpl_n = filterAccImpl_s.nextStep$mcD$sp();
            if (pred.apply$mcZD$sp(filterAccImpl_n) == filterAccImpl_not) continue;
            filterAccImpl_b.addOne(filterAccImpl_n);
        }
        return filterAccImpl_b;
    }

    @Override
    public boolean forall(Function1<Object, Object> p) {
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var3_2 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            if (p.apply$mcZD$sp(s2.nextStep$mcD$sp())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean exists(Function1<Object, Object> p) {
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var3_2 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            if (!p.apply$mcZD$sp(s2.nextStep$mcD$sp())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int count(Function1<Object, Object> p) {
        int r = 0;
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var4_3 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            if (!p.apply$mcZD$sp(s2.nextStep$mcD$sp())) continue;
            ++r;
        }
        return r;
    }

    public long countLong(Function1<Object, Object> p) {
        long r = 0L;
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var5_3 = null;
        DoubleStepper s2 = ((Accumulator)this).efficientStepper(stepper_shape);
        while (s2.hasStep()) {
            if (!p.apply$mcZD$sp(s2.nextStep$mcD$sp())) continue;
            ++r;
        }
        return r;
    }

    public double[] toArray() {
        if (this.totalSize() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(new StringBuilder(44).append("Too many elements accumulated for an array: ").append(Long.toString(this.totalSize())).toString());
        }
        double[] a = new double[(int)this.totalSize()];
        int j = 0;
        long pv = 0L;
        for (int h2 = 0; h2 < this.hIndex(); ++h2) {
            double[] x = this.history()[h2];
            long cuml = (long)x[x.length - 1];
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
            double d = this.current()[i];
            ans = ans.$colon$colon(d);
        }
        for (int h2 = this.hIndex() - 1; h2 >= 0; --h2) {
            double[] a = this.history()[h2];
            for (i = (int)(this.cumulative(h2) - (h2 == 0 ? 0L : this.cumulative(h2 - 1))) - 1; i >= 0; --i) {
                double d = a[i];
                ans = ans.$colon$colon(d);
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
    public DoubleAccumulator fromSpecific(IterableOnce<Object> coll) {
        return DoubleAccumulator$.MODULE$.fromSpecific((IterableOnce)coll);
    }

    public DoubleAccumulator newSpecificBuilder() {
        return new DoubleAccumulator();
    }

    @Override
    public SeqFactory<AnyAccumulator> iterableFactory() {
        return AnyAccumulator$.MODULE$;
    }

    @Override
    public DoubleAccumulator empty() {
        return new DoubleAccumulator();
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    @Override
    public double apply$mcDI$sp(int i) {
        return this.apply((long)i);
    }

    public static final /* synthetic */ String $anonfun$efficientStepper$1(StepperShape shape$1) {
        return new StringBuilder(25).append("unexpected StepperShape: ").append(shape$1).toString();
    }

    public static class SerializationProxy<A>
    implements Serializable {
        private final transient DoubleAccumulator acc;
        private transient DoubleAccumulator result;

        private DoubleAccumulator acc() {
            return this.acc;
        }

        private DoubleAccumulator result() {
            return this.result;
        }

        private void result_$eq(DoubleAccumulator x$1) {
            this.result = x$1;
        }

        /*
         * WARNING - void declaration
         */
        private void writeObject(ObjectOutputStream out) {
            void stepper_shape;
            out.defaultWriteObject();
            DoubleAccumulator doubleAccumulator = this.acc();
            if (doubleAccumulator == null) {
                throw null;
            }
            DoubleAccumulator sizeLong_this = doubleAccumulator;
            Object var5_2 = null;
            long size = sizeLong_this.totalSize();
            out.writeLong(size);
            DoubleAccumulator doubleAccumulator2 = this.acc();
            StepperShape<Object, DoubleStepper> stepperShape = StepperShape$.MODULE$.doubleStepperShape();
            if (doubleAccumulator2 == null) {
                throw null;
            }
            stepperShape = null;
            DoubleStepper st = (DoubleStepper)((Accumulator)doubleAccumulator2).efficientStepper(stepper_shape);
            while (st.hasStep()) {
                out.writeDouble(st.nextStep$mcD$sp());
            }
        }

        /*
         * WARNING - void declaration
         */
        private void readObject(ObjectInputStream in) {
            in.defaultReadObject();
            DoubleAccumulator res = new DoubleAccumulator();
            for (long elems = in.readLong(); elems > 0L; --elems) {
                void var5_4;
                double boxToDouble_d = in.readDouble();
                res.addOne((double)var5_4);
            }
            this.result_$eq(res);
        }

        private Object readResolve() {
            return this.result();
        }

        public SerializationProxy(DoubleAccumulator acc) {
            this.acc = acc;
        }
    }
}

