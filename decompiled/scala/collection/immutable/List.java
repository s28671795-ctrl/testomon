/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.LinearSeqOps;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedLinearSeqOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.AbstractSeq;
import scala.collection.immutable.LinearSeq;
import scala.collection.immutable.List$;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ListBuffer;
import scala.math.Integral;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Statics;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\r}b!\u0002\u001a4\u0003CQ\u0004\"\u00023\u0001\t\u0003)\u0007\"\u00024\u0001\t\u0003:\u0007\"B6\u0001\t\u0003a\u0007\"B;\u0001\t\u00031\b\"B?\u0001\t\u0003q\bbBA\u0005\u0001\u0011\u0015\u00131\u0002\u0005\b\u0003'\u0001A\u0011IA\u000b\u0011\u001d\t\t\u0003\u0001C!\u0003GAq!!\u000e\u0001\t\u0003\n9\u0004C\u0004\u0002H\u0001!\t%!\u0013\t\u000f\u0005U\u0003\u0001\"\u0011\u0002X!9\u0011\u0011\r\u0001\u0005B\u0005\r\u0004bBA4\u0001\u0011\u0005\u0013\u0011\u000e\u0005\b\u0003g\u0002A\u0011IA;\u0011\u001d\t)\t\u0001C#\u0003\u000fCq!a'\u0001\t\u000b\ni\nC\u0004\u00022\u0002!)%a-\t\u000f\u0005\r\u0007\u0001\"\u0012\u0002F\"9\u0011Q\u001b\u0001\u0005F\u0005]\u0007bBAo\u0001\u0011\u0015\u0013q\u001c\u0005\b\u0003k\u0004AQIA|\u0011\u001d\tI\u0010\u0001C#\u0003wDqAa\u0005\u0001\t\u000b\u0012)\u0002C\u0004\u0003\u0018\u0001!)E!\u0007\t\u000f\t}\u0001\u0001\"\u0012\u0003\"!9!Q\u0005\u0001\u0005F\t\u001d\u0002b\u0002B\u0016\u0001\u0011\u0015#Q\u0006\u0005\b\u0005s\u0001AQ\tB\u001e\u0011\u001d\u0011)\u0005\u0001C!\u0005\u000fBqA!\u0013\u0001\t\u0003\u0012Y\u0005\u0003\u0005\u0003d\u0001\u0001K\u0011\u000bB3\u0011\u001d\u00119\b\u0001C\u0003\u0005sBqA!%\u0001\t\u0003\u0012\u0019\nC\u0004\u0003\u0018\u0002!\tE!'\t\u0011\tu\u0005\u0001)C\u0005\u0005?CqAa*\u0001\t\u0003\u0012I\u000bC\u0004\u0003.\u0002!)%a>\t\u000f\t=\u0006\u0001\"\u0011\u00032\u001e9!QZ\u001a\t\u0002\t=gA\u0002\u001a4\u0011\u0003\u0011\t\u000e\u0003\u0004eQ\u0011\u0005!\u0011\u001c\u0005\n\u00057D#\u0019!C\u0005\u0005;D\u0001B!:)A\u0003%!q\u001c\u0005\b\u00037BC\u0011\u0001Bt\u0011\u001d\u00119\u0010\u000bC\u0001\u0005sDqaa\u0004)\t\u0003\u0019\t\u0002\u0003\u0006\u0004\u001c!\u0012\r\u0011\"\u00016\u0007;A\u0001b!\n)A\u0003%1q\u0004\u0005\n\u0007cA\u0013\u0011!C\u0005\u0007g\u0011A\u0001T5ti*\u0011A'N\u0001\nS6lW\u000f^1cY\u0016T!AN\u001c\u0002\u0015\r|G\u000e\\3di&|gNC\u00019\u0003\u0015\u00198-\u00197b\u0007\u0001)\"a\u000f\"\u0014\u0011\u0001aDj\u0014+Y7z\u00032!\u0010 A\u001b\u0005\u0019\u0014BA 4\u0005-\t%m\u001d;sC\u000e$8+Z9\u0011\u0005\u0005\u0013E\u0002\u0001\u0003\u0007\u0007\u0002!)\u0019\u0001#\u0003\u0003\u0005\u000b\"!R%\u0011\u0005\u0019;U\"A\u001c\n\u0005!;$a\u0002(pi\"Lgn\u001a\t\u0003\r*K!aS\u001c\u0003\u0007\u0005s\u0017\u0010E\u0002>\u001b\u0002K!AT\u001a\u0003\u00131Kg.Z1s'\u0016\f\b#B\u001fQ\u0001J\u001b\u0016BA)4\u00051a\u0015N\\3beN+\u0017o\u00149t!\ti\u0004\u0001E\u0002>\u0001\u0001\u0003R!\u0016,A%Nk\u0011!N\u0005\u0003/V\u00121d\u0015;sS\u000e$x\n\u001d;j[&TX\r\u001a'j]\u0016\f'oU3r\u001fB\u001c\b#B\u001fZ\u0001J\u001b\u0016B\u0001.4\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$7+Z9PaN\u0004B!\u0016/A%&\u0011Q,\u000e\u0002\u0018\u0013R,'/\u00192mK\u001a\u000b7\r^8ss\u0012+g-Y;miN\u0004\"a\u00182\u000e\u0003\u0001T!!Y\u001b\u0002\u000f\u001d,g.\u001a:jG&\u00111\r\u0019\u0002\u0014\t\u00164\u0017-\u001e7u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003M\u000bq\"\u001b;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0002QB\u0019Q+\u001b*\n\u0005),$AC*fc\u001a\u000b7\r^8ss\u0006aAeY8m_:$3m\u001c7p]V\u0011Q\u000e\u001d\u000b\u0003]N\u00042!\u0010\u0001p!\t\t\u0005\u000fB\u0003r\u0007\t\u0007!OA\u0001C#\t\u0001\u0015\nC\u0003u\u0007\u0001\u0007q.\u0001\u0003fY\u0016l\u0017A\u0005\u0013d_2|g\u000eJ2pY>tGeY8m_:,\"a\u001e>\u0015\u0005a\\\bcA\u001f\u0001sB\u0011\u0011I\u001f\u0003\u0006c\u0012\u0011\rA\u001d\u0005\u0006y\u0012\u0001\r\u0001_\u0001\u0007aJ,g-\u001b=\u00025I,g/\u001a:tK~#3m\u001c7p]\u0012\u001aw\u000e\\8oI\r|Gn\u001c8\u0016\u0007}\f)\u0001\u0006\u0003\u0002\u0002\u0005\u001d\u0001\u0003B\u001f\u0001\u0003\u0007\u00012!QA\u0003\t\u0015\tXA1\u0001s\u0011\u0019aX\u00011\u0001\u0002\u0002\u00059\u0011n]#naRLXCAA\u0007!\r1\u0015qB\u0005\u0004\u0003#9$a\u0002\"p_2,\u0017M\\\u0001\naJ,\u0007/\u001a8eK\u0012,B!a\u0006\u0002\u001eQ!\u0011\u0011DA\u0010!\u0011i\u0004!a\u0007\u0011\u0007\u0005\u000bi\u0002B\u0003r\u000f\t\u0007!\u000f\u0003\u0004u\u000f\u0001\u0007\u00111D\u0001\raJ,\u0007/\u001a8eK\u0012\fE\u000e\\\u000b\u0005\u0003K\tY\u0003\u0006\u0003\u0002(\u00055\u0002\u0003B\u001f\u0001\u0003S\u00012!QA\u0016\t\u0015\t\bB1\u0001s\u0011\u0019a\b\u00021\u0001\u00020A)Q+!\r\u0002*%\u0019\u00111G\u001b\u0003\u0019%#XM]1cY\u0016|enY3\u0002\u0017\u0005\u0004\b/\u001a8eK\u0012\fE\u000e\\\u000b\u0005\u0003s\ty\u0004\u0006\u0003\u0002<\u0005\u0005\u0003\u0003B\u001f\u0001\u0003{\u00012!QA \t\u0015\t\u0018B1\u0001s\u0011\u001d\t\u0019%\u0003a\u0001\u0003\u000b\naa];gM&D\b#B+\u00022\u0005u\u0012\u0001\u0002;bW\u0016$2aUA&\u0011\u001d\tiE\u0003a\u0001\u0003\u001f\n\u0011A\u001c\t\u0004\r\u0006E\u0013bAA*o\t\u0019\u0011J\u001c;\u0002\u000bMd\u0017nY3\u0015\u000bM\u000bI&!\u0018\t\u000f\u0005m3\u00021\u0001\u0002P\u0005!aM]8n\u0011\u001d\tyf\u0003a\u0001\u0003\u001f\nQ!\u001e8uS2\f\u0011\u0002^1lKJKw\r\u001b;\u0015\u0007M\u000b)\u0007C\u0004\u0002N1\u0001\r!a\u0014\u0002\u000fM\u0004H.\u001b;BiR!\u00111NA9!\u00151\u0015QN*T\u0013\r\tyg\u000e\u0002\u0007)V\u0004H.\u001a\u001a\t\u000f\u00055S\u00021\u0001\u0002P\u00059Q\u000f\u001d3bi\u0016$W\u0003BA<\u0003{\"b!!\u001f\u0002\u0000\u0005\r\u0005\u0003B\u001f\u0001\u0003w\u00022!QA?\t\u0015\thB1\u0001s\u0011\u001d\t\tI\u0004a\u0001\u0003\u001f\nQ!\u001b8eKbDa\u0001\u001e\bA\u0002\u0005m\u0014aA7baV!\u0011\u0011RAH)\u0011\tY)!%\u0011\tu\u0002\u0011Q\u0012\t\u0004\u0003\u0006=E!B9\u0010\u0005\u0004!\u0005bBAJ\u001f\u0001\u0007\u0011QS\u0001\u0002MB1a)a&A\u0003\u001bK1!!'8\u0005%1UO\\2uS>t\u0017'A\u0004d_2dWm\u0019;\u0016\t\u0005}\u0015Q\u0015\u000b\u0005\u0003C\u000b9\u000b\u0005\u0003>\u0001\u0005\r\u0006cA!\u0002&\u0012)\u0011\u000f\u0005b\u0001\t\"9\u0011\u0011\u0016\tA\u0002\u0005-\u0016A\u00019g!\u00191\u0015Q\u0016!\u0002$&\u0019\u0011qV\u001c\u0003\u001fA\u000b'\u000f^5bY\u001a+hn\u0019;j_:\fqA\u001a7bi6\u000b\u0007/\u0006\u0003\u00026\u0006mF\u0003BA\\\u0003{\u0003B!\u0010\u0001\u0002:B\u0019\u0011)a/\u0005\u000bE\f\"\u0019\u0001#\t\u000f\u0005M\u0015\u00031\u0001\u0002@B1a)a&A\u0003\u0003\u0004R!VA\u0019\u0003s\u000b\u0011\u0002^1lK^C\u0017\u000e\\3\u0015\u0007M\u000b9\rC\u0004\u0002JJ\u0001\r!a3\u0002\u0003A\u0004bARAL\u0001\u00065\u0001f\u0001\n\u0002PB\u0019a)!5\n\u0007\u0005MwG\u0001\u0004j]2Lg.Z\u0001\u0005gB\fg\u000e\u0006\u0003\u0002l\u0005e\u0007bBAe'\u0001\u0007\u00111\u001a\u0015\u0004'\u0005=\u0017a\u00024pe\u0016\f7\r[\u000b\u0005\u0003C\fy\u000f\u0006\u0003\u0002d\u0006%\bc\u0001$\u0002f&\u0019\u0011q]\u001c\u0003\tUs\u0017\u000e\u001e\u0005\b\u0003'#\u0002\u0019AAv!\u00191\u0015q\u0013!\u0002nB\u0019\u0011)a<\u0005\r\u0005EHC1\u0001E\u0005\u0005)\u0006f\u0001\u000b\u0002P\u00069!/\u001a<feN,W#A*\u0002\u0013\u0019|G\u000e\u001a*jO\"$X\u0003BA\u007f\u0005\u0007!B!a@\u0003\u0010Q!!\u0011\u0001B\u0003!\r\t%1\u0001\u0003\u0006cZ\u0011\r\u0001\u0012\u0005\b\u0005\u000f1\u0002\u0019\u0001B\u0005\u0003\ty\u0007\u000f\u0005\u0005G\u0005\u0017\u0001%\u0011\u0001B\u0001\u0013\r\u0011ia\u000e\u0002\n\rVt7\r^5p]JBqA!\u0005\u0017\u0001\u0004\u0011\t!A\u0001{\u0003\u0019aWM\\4uQV\u0011\u0011qJ\u0001\u000eY\u0016tw\r\u001e5D_6\u0004\u0018M]3\u0015\t\u0005=#1\u0004\u0005\b\u0005;A\u0002\u0019AA(\u0003\raWM\\\u0001\u0007M>\u0014\u0018\r\u001c7\u0015\t\u00055!1\u0005\u0005\b\u0003\u0013L\u0002\u0019AAf\u0003\u0019)\u00070[:ugR!\u0011Q\u0002B\u0015\u0011\u001d\tIM\u0007a\u0001\u0003\u0017\f\u0001bY8oi\u0006Lgn]\u000b\u0005\u0005_\u0011)\u0004\u0006\u0003\u0002\u000e\tE\u0002B\u0002;\u001c\u0001\u0004\u0011\u0019\u0004E\u0002B\u0005k!aAa\u000e\u001c\u0005\u0004\u0011(AA!2\u0003\u00111\u0017N\u001c3\u0015\t\tu\"1\t\t\u0005\r\n}\u0002)C\u0002\u0003B]\u0012aa\u00149uS>t\u0007bBAe9\u0001\u0007\u00111Z\u0001\u0005Y\u0006\u001cH/F\u0001A\u0003-\u0019wN\u001d:fgB|g\u000eZ:\u0016\t\t5#q\u000b\u000b\u0005\u0005\u001f\u0012I\u0006\u0006\u0003\u0002\u000e\tE\u0003bBAe=\u0001\u0007!1\u000b\t\t\r\n-\u0001I!\u0016\u0002\u000eA\u0019\u0011Ia\u0016\u0005\u000bEt\"\u0019\u0001#\t\u000f\tmc\u00041\u0001\u0003^\u0005!A\u000f[1u!\u0015)&q\fB+\u0013\r\u0011\t'\u000e\u0002\u0004'\u0016\f\u0018!C2mCN\u001ch*Y7f+\t\u00119\u0007\u0005\u0003\u0003j\tMTB\u0001B6\u0015\u0011\u0011iGa\u001c\u0002\t1\fgn\u001a\u0006\u0003\u0005c\nAA[1wC&!!Q\u000fB6\u0005\u0019\u0019FO]5oO\u0006YQ.\u00199D_:\u001cXM\u001d<f+\u0011\u0011YH!!\u0015\t\tu$1\u0012\t\u0005{\u0001\u0011y\bE\u0002B\u0005\u0003#a!\u001d\u0011C\u0002\t\r\u0015c\u0001!\u0003\u0006B\u0019aIa\"\n\u0007\t%uG\u0001\u0004B]f\u0014VM\u001a\u0005\b\u0003'\u0003\u0003\u0019\u0001BG!\u00191\u0015q\u0013!\u0003\u0000!\u001a\u0001%a4\u0002\r\u0019LG\u000e^3s)\r\u0019&Q\u0013\u0005\b\u0003\u0013\f\u0003\u0019AAf\u0003%1\u0017\u000e\u001c;fe:{G\u000fF\u0002T\u00057Cq!!3#\u0001\u0004\tY-\u0001\u0007gS2$XM]\"p[6|g\u000eF\u0003T\u0005C\u0013\u0019\u000bC\u0004\u0002J\u000e\u0002\r!a3\t\u000f\t\u00156\u00051\u0001\u0002\u000e\u0005I\u0011n\u001d$mSB\u0004X\rZ\u0001\na\u0006\u0014H/\u001b;j_:$B!a\u001b\u0003,\"9\u0011\u0011\u001a\u0013A\u0002\u0005-\u0017A\u0002;p\u0019&\u001cH/\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u001b\u0011\u0019\f\u0003\u0004\u00036\u001a\u0002\r!S\u0001\u0002_&*\u0001A!/\u0003>&\u0019!1X\u001a\u0003\u0019\u0011\u001aw\u000e\\8oI\r|Gn\u001c8\u000b\u0007\t}6'A\u0002OS2Ds\u0001\u0001Bb\u0005\u0013\u0014Y\rE\u0002G\u0005\u000bL1Aa28\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004\u0003\u0011a\u0015n\u001d;\u0011\u0005uB3#\u0002\u0015\u0003\u0006\nM\u0007\u0003B+\u0003VJK1Aa66\u0005e\u0019FO]5di>\u0003H/[7ju\u0016$7+Z9GC\u000e$xN]=\u0015\u0005\t=\u0017A\u0003+va2,wJ\u001a(jYV\u0011!q\u001c\t\b\r\u00065$\u0011\u001dBq\u001d\ri$1]\u0005\u0004\u0005\u007f\u001b\u0014a\u0003+va2,wJ\u001a(jY\u0002*BA!;\u0003pR!!1\u001eBy!\u0011i\u0004A!<\u0011\u0007\u0005\u0013y\u000fB\u0003rY\t\u0007A\tC\u0004\u0003t2\u0002\rA!>\u0002\t\r|G\u000e\u001c\t\u0006+\u0006E\"Q^\u0001\u000b]\u0016<()^5mI\u0016\u0014X\u0003\u0002B~\u0007\u0017)\"A!@\u0011\u0011\t}8QAB\u0005\u0007\u001bi!a!\u0001\u000b\u0007\r\rQ'A\u0004nkR\f'\r\\3\n\t\r\u001d1\u0011\u0001\u0002\b\u0005VLG\u000eZ3s!\r\t51\u0002\u0003\u0006\u00076\u0012\r\u0001\u0012\t\u0005{\u0001\u0019I!A\u0003f[B$\u00180\u0006\u0003\u0004\u0014\reQCAB\u000b!\u0011i\u0004aa\u0006\u0011\u0007\u0005\u001bI\u0002B\u0003D]\t\u0007A)A\tqCJ$\u0018.\u00197O_R\f\u0005\u000f\u001d7jK\u0012,\"aa\b\u0013\r\r\u0005\"QQB\u0018\r\u0019\u0019\u0019\u0003\r\u0001\u0004 \taAH]3gS:,W.\u001a8u}\u0005\u0011\u0002/\u0019:uS\u0006dgj\u001c;BaBd\u0017.\u001a3!Q\r\u00014\u0011\u0006\t\u0004\r\u000e-\u0012bAB\u0017o\tIAO]1og&,g\u000e\u001e\t\u0006\r\u0006]\u0015*S\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0007k\u0001BA!\u001b\u00048%!1\u0011\bB6\u0005\u0019y%M[3di\":\u0001Fa1\u0003J\n-\u0007fB\u0014\u0003D\n%'1\u001a")
public abstract class List<A>
extends AbstractSeq<A>
implements LinearSeq<A>,
StrictOptimizedLinearSeqOps<A, List, List<A>>,
scala.collection.immutable.StrictOptimizedSeqOps<A, List, List<A>>,
DefaultSerializable {
    private static final long serialVersionUID = 3L;

    public static <A> Builder<A, List<A>> newBuilder() {
        return new ListBuffer();
    }

    public static <B> List<B> from(IterableOnce<B> coll) {
        return Nil$.MODULE$.prependedAll((IterableOnce)coll);
    }

    public static SeqOps tabulate(int n, Function1 f) {
        ListBuffer tabulate_b = new ListBuffer();
        tabulate_b.sizeHint(n);
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            Object tabulate_$plus$eq_elem = f.apply(tabulate_i);
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var4_4 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static SeqOps fill(int n, Function0 elem) {
        ListBuffer fill_b = new ListBuffer();
        fill_b.sizeHint(n);
        for (int fill_i = 0; fill_i < n; ++fill_i) {
            Object fill_$plus$eq_elem = elem.apply();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var4_4 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        ListBuffer<SeqOps> tabulate_b = new ListBuffer<SeqOps>();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var11_11;
            ListBuffer<SeqOps> tabulate_b2 = new ListBuffer<SeqOps>();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var14_14;
                ListBuffer<SeqOps> tabulate_b3 = new ListBuffer<SeqOps>();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var17_17;
                    ListBuffer<SeqOps> tabulate_b4 = new ListBuffer<SeqOps>();
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object var20_20;
                        ListBuffer tabulate_b5 = new ListBuffer();
                        tabulate_b5.sizeHint(n5);
                        for (int tabulate_i5 = 0; tabulate_i5 < n5; ++tabulate_i5) {
                            Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4, tabulate_i5);
                            tabulate_b5.addOne(tabulate_$plus$eq_elem);
                            var20_20 = null;
                        }
                        Object var18_18 = null;
                        var20_20 = null;
                        SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b5.result();
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        ListBuffer<SeqOps> tabulate_b = new ListBuffer<SeqOps>();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var10_10;
            ListBuffer<SeqOps> tabulate_b2 = new ListBuffer<SeqOps>();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var13_13;
                ListBuffer<SeqOps> tabulate_b3 = new ListBuffer<SeqOps>();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var16_16;
                    ListBuffer tabulate_b4 = new ListBuffer();
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4);
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var16_16 = null;
                    }
                    Object var14_14 = null;
                    var16_16 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        ListBuffer<SeqOps> tabulate_b = new ListBuffer<SeqOps>();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var9_9;
            ListBuffer<SeqOps> tabulate_b2 = new ListBuffer<SeqOps>();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var12_12;
                ListBuffer tabulate_b3 = new ListBuffer();
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3);
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var12_12 = null;
                }
                Object var10_10 = null;
                var12_12 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var6_6 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        ListBuffer<SeqOps> tabulate_b = new ListBuffer<SeqOps>();
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var8_8;
            ListBuffer tabulate_b2 = new ListBuffer();
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2);
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var8_8 = null;
            }
            Object var6_6 = null;
            var8_8 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var5_5 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        ListBuffer<SeqOps> fill_b = new ListBuffer<SeqOps>();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var11_11;
            ListBuffer<SeqOps> fill_b2 = new ListBuffer<SeqOps>();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var14_14;
                ListBuffer<SeqOps> fill_b3 = new ListBuffer<SeqOps>();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var17_17;
                    ListBuffer<SeqOps> fill_b4 = new ListBuffer<SeqOps>();
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object var20_20;
                        ListBuffer fill_b5 = new ListBuffer();
                        fill_b5.sizeHint(n5);
                        for (int fill_i5 = 0; fill_i5 < n5; ++fill_i5) {
                            Object fill_$plus$eq_elem = elem.apply();
                            fill_b5.addOne(fill_$plus$eq_elem);
                            var20_20 = null;
                        }
                        Object var18_18 = null;
                        var20_20 = null;
                        SeqOps fill_$plus$eq_elem = (SeqOps)fill_b5.result();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        ListBuffer<SeqOps> fill_b = new ListBuffer<SeqOps>();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var10_10;
            ListBuffer<SeqOps> fill_b2 = new ListBuffer<SeqOps>();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var13_13;
                ListBuffer<SeqOps> fill_b3 = new ListBuffer<SeqOps>();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var16_16;
                    ListBuffer fill_b4 = new ListBuffer();
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object fill_$plus$eq_elem = elem.apply();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var16_16 = null;
                    }
                    Object var14_14 = null;
                    var16_16 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        ListBuffer<SeqOps> fill_b = new ListBuffer<SeqOps>();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var9_9;
            ListBuffer<SeqOps> fill_b2 = new ListBuffer<SeqOps>();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var12_12;
                ListBuffer fill_b3 = new ListBuffer();
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object fill_$plus$eq_elem = elem.apply();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var12_12 = null;
                }
                Object var10_10 = null;
                var12_12 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var6_6 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        ListBuffer<SeqOps> fill_b = new ListBuffer<SeqOps>();
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var8_8;
            ListBuffer fill_b2 = new ListBuffer();
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object fill_$plus$eq_elem = elem.apply();
                fill_b2.addOne(fill_$plus$eq_elem);
                var8_8 = null;
            }
            Object var6_6 = null;
            var8_8 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var5_5 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(List$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(List$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        View.Unfold from_source = new View.Unfold(init2, f);
        return Nil$.MODULE$.prependedAll((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return Nil$.MODULE$.prependedAll(from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public /* synthetic */ Object scala$collection$immutable$StrictOptimizedSeqOps$$super$sorted(Ordering ord) {
        return SeqOps.sorted$(this, ord);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return scala.collection.immutable.StrictOptimizedSeqOps.distinctBy$(this, f);
    }

    @Override
    public Object patch(int from, IterableOnce other, int replaced) {
        return scala.collection.immutable.StrictOptimizedSeqOps.patch$(this, from, other, replaced);
    }

    @Override
    public Object sorted(Ordering ord) {
        return scala.collection.immutable.StrictOptimizedSeqOps.sorted$(this, ord);
    }

    @Override
    public Iterator<A> iterator() {
        return StrictOptimizedLinearSeqOps.iterator$(this);
    }

    @Override
    public scala.collection.LinearSeq drop(int n) {
        return StrictOptimizedLinearSeqOps.drop$(this, n);
    }

    @Override
    public scala.collection.LinearSeq dropWhile(Function1 p) {
        return StrictOptimizedLinearSeqOps.dropWhile$(this, p);
    }

    @Override
    public Object appended(Object elem) {
        return StrictOptimizedSeqOps.appended$(this, elem);
    }

    @Override
    public Object padTo(int len, Object elem) {
        return StrictOptimizedSeqOps.padTo$(this, len, elem);
    }

    @Override
    public Object diff(Seq that) {
        return StrictOptimizedSeqOps.diff$(this, that);
    }

    @Override
    public Object intersect(Seq that) {
        return StrictOptimizedSeqOps.intersect$(this, that);
    }

    @Override
    public <A1, A2> Tuple2<List<A1>, List<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<List<A1>, List<A2>, List<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<List<A1>, List<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public String stringPrefix() {
        return scala.collection.LinearSeq.stringPrefix$(this);
    }

    @Override
    public /* synthetic */ boolean scala$collection$LinearSeqOps$$super$sameElements(IterableOnce that) {
        return SeqOps.sameElements$(this, that);
    }

    @Override
    public Option<A> headOption() {
        return LinearSeqOps.headOption$(this);
    }

    @Override
    public int lengthCompare(Iterable<?> that) {
        return LinearSeqOps.lengthCompare$((LinearSeqOps)this, that);
    }

    @Override
    public boolean isDefinedAt(int x) {
        return LinearSeqOps.isDefinedAt$(this, x);
    }

    @Override
    public A apply(int n) throws IndexOutOfBoundsException {
        return (A)LinearSeqOps.apply$(this, n);
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, A, B> op) {
        return (B)LinearSeqOps.foldLeft$(this, z, op);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        return LinearSeqOps.sameElements$(this, that);
    }

    @Override
    public int segmentLength(Function1<A, Object> p, int from) {
        return LinearSeqOps.segmentLength$(this, p, from);
    }

    @Override
    public int indexWhere(Function1<A, Object> p, int from) {
        return LinearSeqOps.indexWhere$(this, p, from);
    }

    @Override
    public int lastIndexWhere(Function1<A, Object> p, int end) {
        return LinearSeqOps.lastIndexWhere$(this, p, end);
    }

    @Override
    public Option<A> findLast(Function1<A, Object> p) {
        return LinearSeqOps.findLast$(this, p);
    }

    @Override
    public Iterator<List<A>> tails() {
        return LinearSeqOps.tails$(this);
    }

    @Override
    public SeqFactory<List> iterableFactory() {
        return List$.MODULE$;
    }

    public <B> List<B> $colon$colon(B elem) {
        return new $colon$colon<B>(elem, this);
    }

    public <B> List<B> $colon$colon$colon(List<B> prefix) {
        $colon$colon result;
        if (this.isEmpty()) {
            return prefix;
        }
        if (prefix.isEmpty()) {
            return this;
        }
        $colon$colon curr = result = new $colon$colon(prefix.head(), this);
        List that = (List)prefix.tail();
        while (!that.isEmpty()) {
            $colon$colon temp = new $colon$colon(that.head(), this);
            curr.next_$eq(temp);
            curr = temp;
            that = (List)that.tail();
        }
        Statics.releaseFence();
        return result;
    }

    public <B> List<B> reverse_$colon$colon$colon(List<B> prefix) {
        $colon$colon these = this;
        List pres = prefix;
        while (!pres.isEmpty()) {
            Object a = pres.head();
            these = new $colon$colon(a, these);
            pres = (List)pres.tail();
        }
        return these;
    }

    @Override
    public final boolean isEmpty() {
        return this == Nil$.MODULE$;
    }

    @Override
    public <B> List<B> prepended(B elem) {
        return new $colon$colon<B>(elem, this);
    }

    @Override
    public <B> List<B> prependedAll(IterableOnce<B> prefix) {
        Iterator<B> iter;
        if (prefix instanceof List) {
            List list = (List)prefix;
            return this.$colon$colon$colon(list);
        }
        if (prefix.knownSize() == 0) {
            return this;
        }
        if (prefix instanceof ListBuffer) {
            ListBuffer listBuffer = (ListBuffer)prefix;
            if (this.isEmpty()) {
                return listBuffer.toList();
            }
        }
        if ((iter = prefix.iterator()).hasNext()) {
            $colon$colon<B> result;
            $colon$colon<B> curr = result = new $colon$colon<B>(iter.next(), this);
            while (iter.hasNext()) {
                $colon$colon<B> temp = new $colon$colon<B>(iter.next(), this);
                curr.next_$eq(temp);
                curr = temp;
            }
            Statics.releaseFence();
            return result;
        }
        return this;
    }

    @Override
    public <B> List<B> appendedAll(IterableOnce<B> suffix) {
        if (suffix instanceof List) {
            return ((List)suffix).$colon$colon$colon(this);
        }
        return (List)StrictOptimizedSeqOps.appendedAll$(this, suffix);
    }

    @Override
    public List<A> take(int n) {
        $colon$colon<Nothing$> h2;
        if (this.isEmpty() || n <= 0) {
            return Nil$.MODULE$;
        }
        $colon$colon<Nothing$> t2 = h2 = new $colon$colon<Nothing$>((Nothing$)this.head(), Nil$.MODULE$);
        List rest = (List)this.tail();
        int i = 1;
        while (true) {
            if (rest.isEmpty()) {
                return this;
            }
            if (i >= n) break;
            ++i;
            $colon$colon<Nothing$> nx = new $colon$colon<Nothing$>((Nothing$)rest.head(), Nil$.MODULE$);
            t2.next_$eq(nx);
            t2 = nx;
            rest = (List)rest.tail();
        }
        Statics.releaseFence();
        return h2;
    }

    @Override
    public List<A> slice(int from, int until) {
        int max_y = 0;
        int lo = Math.max(from, max_y);
        if (until <= lo || this.isEmpty()) {
            return Nil$.MODULE$;
        }
        return ((List)StrictOptimizedLinearSeqOps.drop$(this, lo)).take(until - lo);
    }

    @Override
    public List<A> takeRight(int n) {
        return this.loop$1((List)StrictOptimizedLinearSeqOps.drop$(this, n), this);
    }

    @Override
    public Tuple2<List<A>, List<A>> splitAt(int n) {
        ListBuffer b = new ListBuffer();
        int i = 0;
        List these = this;
        while (!these.isEmpty() && i < n) {
            ++i;
            Object $plus$eq_elem = these.head();
            b.addOne($plus$eq_elem);
            Object var5_5 = null;
            these = (List)these.tail();
        }
        return new Tuple2<List<A>, List<A>>(b.toList(), these);
    }

    @Override
    public <B> List<B> updated(int index, B elem) {
        int i = 0;
        List current = this;
        ListBuffer<Object> prefix = new ListBuffer<Object>();
        while (i < index && current.nonEmpty()) {
            ++i;
            Object $plus$eq_elem = current.head();
            prefix.addOne($plus$eq_elem);
            Object var6_6 = null;
            current = (List)current.tail();
        }
        if (i == index && current.nonEmpty()) {
            List list = (List)current.tail();
            if (list == null) {
                throw null;
            }
            List $colon$colon_this = list;
            Object var7_7 = null;
            return prefix.prependToList(new $colon$colon<B>(elem, $colon$colon_this));
        }
        throw new IndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(this.length() - 1).append(")").toString());
    }

    @Override
    public final <B> List<B> map(Function1<A, B> f) {
        $colon$colon<Nothing$> h2;
        if (this == Nil$.MODULE$) {
            return Nil$.MODULE$;
        }
        $colon$colon<Nothing$> t2 = h2 = new $colon$colon<Nothing$>((Nothing$)f.apply(this.head()), Nil$.MODULE$);
        for (List rest = (List)this.tail(); rest != Nil$.MODULE$; rest = (List)rest.tail()) {
            $colon$colon<Nothing$> nx = new $colon$colon<Nothing$>((Nothing$)f.apply(rest.head()), Nil$.MODULE$);
            t2.next_$eq(nx);
            t2 = nx;
        }
        Statics.releaseFence();
        return h2;
    }

    @Override
    public final <B> List<B> collect(PartialFunction<A, B> pf) {
        Object x;
        if (this == Nil$.MODULE$) {
            return Nil$.MODULE$;
        }
        List rest = this;
        $colon$colon<Nothing$> h2 = null;
        while (h2 == null) {
            x = pf.applyOrElse(rest.head(), List$.MODULE$.partialNotApplied());
            if (x != List$.MODULE$.partialNotApplied()) {
                h2 = new $colon$colon<Nothing$>((Nothing$)x, Nil$.MODULE$);
            }
            if ((rest = (List)rest.tail()) != Nil$.MODULE$) continue;
            if (h2 == null) {
                return Nil$.MODULE$;
            }
            return h2;
        }
        $colon$colon<Nothing$> t2 = h2;
        while (rest != Nil$.MODULE$) {
            x = pf.applyOrElse(rest.head(), List$.MODULE$.partialNotApplied());
            if (x != List$.MODULE$.partialNotApplied()) {
                $colon$colon<Nothing$> nx = new $colon$colon<Nothing$>((Nothing$)x, Nil$.MODULE$);
                t2.next_$eq(nx);
                t2 = nx;
            }
            rest = (List)rest.tail();
        }
        Statics.releaseFence();
        return h2;
    }

    @Override
    public final <B> List<B> flatMap(Function1<A, IterableOnce<B>> f) {
        $colon$colon<Nothing$> h2 = null;
        $colon$colon<Nothing$> t2 = null;
        for (List rest = this; rest != Nil$.MODULE$; rest = (List)rest.tail()) {
            Iterator<B> it = f.apply(rest.head()).iterator();
            while (it.hasNext()) {
                $colon$colon<Nothing$> nx = new $colon$colon<Nothing$>((Nothing$)it.next(), Nil$.MODULE$);
                if (t2 == null) {
                    h2 = nx;
                } else {
                    t2.next_$eq(nx);
                }
                t2 = nx;
            }
        }
        if (h2 == null) {
            return Nil$.MODULE$;
        }
        Statics.releaseFence();
        return h2;
    }

    @Override
    public final List<A> takeWhile(Function1<A, Object> p) {
        ListBuffer b = new ListBuffer();
        List these = this;
        while (!these.isEmpty() && BoxesRunTime.unboxToBoolean(p.apply(these.head()))) {
            Object $plus$eq_elem = these.head();
            b.addOne($plus$eq_elem);
            Object var4_4 = null;
            these = (List)these.tail();
        }
        return b.toList();
    }

    @Override
    public final Tuple2<List<A>, List<A>> span(Function1<A, Object> p) {
        ListBuffer b = new ListBuffer();
        List these = this;
        while (!these.isEmpty() && BoxesRunTime.unboxToBoolean(p.apply(these.head()))) {
            Object $plus$eq_elem = these.head();
            b.addOne($plus$eq_elem);
            Object var4_4 = null;
            these = (List)these.tail();
        }
        return new Tuple2<List<A>, List<A>>(b.toList(), these);
    }

    @Override
    public final <U> void foreach(Function1<A, U> f) {
        List these = this;
        while (!these.isEmpty()) {
            f.apply(these.head());
            these = (List)these.tail();
        }
    }

    @Override
    public final List<A> reverse() {
        List result = Nil$.MODULE$;
        List these = this;
        while (!these.isEmpty()) {
            Object a = these.head();
            result = result.$colon$colon(a);
            these = (List)these.tail();
        }
        return result;
    }

    @Override
    public final <B> B foldRight(B z, Function2<A, B, B> op) {
        B acc = z;
        Object these = this.reverse();
        while (!((List)these).isEmpty()) {
            acc = op.apply(((AbstractIterable)these).head(), acc);
            these = (List)((AbstractIterable)these).tail();
        }
        return acc;
    }

    @Override
    public final int length() {
        List these = this;
        int len = 0;
        while (!these.isEmpty()) {
            ++len;
            these = (List)these.tail();
        }
        return len;
    }

    @Override
    public final int lengthCompare(int len) {
        if (len < 0) {
            return 1;
        }
        return this.loop$2(0, this, len);
    }

    @Override
    public final boolean forall(Function1<A, Object> p) {
        List these = this;
        while (!these.isEmpty()) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(these.head()))) {
                return false;
            }
            these = (List)these.tail();
        }
        return true;
    }

    @Override
    public final boolean exists(Function1<A, Object> p) {
        List these = this;
        while (!these.isEmpty()) {
            if (BoxesRunTime.unboxToBoolean(p.apply(these.head()))) {
                return true;
            }
            these = (List)these.tail();
        }
        return false;
    }

    @Override
    public final <A1> boolean contains(A1 elem) {
        List these = this;
        while (!these.isEmpty()) {
            if (BoxesRunTime.equals(these.head(), elem)) {
                return true;
            }
            these = (List)these.tail();
        }
        return false;
    }

    @Override
    public final Option<A> find(Function1<A, Object> p) {
        List these = this;
        while (!these.isEmpty()) {
            if (BoxesRunTime.unboxToBoolean(p.apply(these.head()))) {
                return new Some(these.head());
            }
            these = (List)these.tail();
        }
        return None$.MODULE$;
    }

    @Override
    public A last() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("List.last");
        }
        List these = this;
        List scout = (List)this.tail();
        while (!scout.isEmpty()) {
            these = scout;
            scout = (List)scout.tail();
        }
        return these.head();
    }

    @Override
    public <B> boolean corresponds(Seq<B> that, Function2<A, B, Object> p) {
        if (that instanceof LinearSeq) {
            LinearSeq linearSeq = (LinearSeq)that;
            List i = this;
            LinearSeq j = linearSeq;
            while (!i.isEmpty() && !j.isEmpty()) {
                if (!BoxesRunTime.unboxToBoolean(p.apply(i.head(), j.head()))) {
                    return false;
                }
                i = (List)i.tail();
                j = (LinearSeq)j.tail();
            }
            return i.isEmpty() && j.isEmpty();
        }
        Iterator corresponds_i = this.iterator();
        Iterator corresponds_j = that.iterator();
        while (corresponds_i.hasNext() && corresponds_j.hasNext()) {
            if (BoxesRunTime.unboxToBoolean(p.apply(corresponds_i.next(), corresponds_j.next()))) continue;
            return false;
        }
        return !corresponds_i.hasNext() && !corresponds_j.hasNext();
    }

    @Override
    public String className() {
        return "List";
    }

    public final <B> List<B> mapConserve(Function1<A, B> f) {
        $colon$colon<Nothing$> $colon$colon;
        List list = this;
        List list2 = this;
        Object var4_4 = null;
        $colon$colon<Nothing$> loop$3_mappedHead = null;
        while (true) {
            List loop$3_tail0;
            $colon$colon<Nothing$> loop$3_mappedLast;
            List loop$3_unchanged;
            List loop$3_pending;
            if (loop$3_pending.isEmpty()) {
                if (loop$3_mappedHead == null) {
                    $colon$colon = loop$3_unchanged;
                    break;
                }
                loop$3_mappedLast.next_$eq(loop$3_unchanged);
                $colon$colon = loop$3_mappedHead;
                break;
            }
            Object loop$3_head0 = loop$3_pending.head();
            B loop$3_head1 = f.apply(loop$3_head0);
            if (loop$3_head1 == loop$3_head0) {
                loop$3_pending = (List)loop$3_pending.tail();
                continue;
            }
            $colon$colon<Nothing$> loop$3_mappedHead1 = loop$3_mappedHead;
            $colon$colon<Nothing$> loop$3_mappedLast1 = loop$3_mappedLast;
            for (List loop$3_xc = loop$3_unchanged; loop$3_xc != loop$3_pending; loop$3_xc = (List)loop$3_xc.tail()) {
                $colon$colon<Nothing$> loop$3_next = new $colon$colon<Nothing$>((Nothing$)loop$3_xc.head(), Nil$.MODULE$);
                if (loop$3_mappedHead1 == null) {
                    loop$3_mappedHead1 = loop$3_next;
                }
                if (loop$3_mappedLast1 != null) {
                    loop$3_mappedLast1.next_$eq(loop$3_next);
                }
                loop$3_mappedLast1 = loop$3_next;
            }
            $colon$colon<Nothing$> loop$3_next = new $colon$colon<Nothing$>((Nothing$)loop$3_head1, Nil$.MODULE$);
            if (loop$3_mappedHead1 == null) {
                loop$3_mappedHead1 = loop$3_next;
            }
            if (loop$3_mappedLast1 != null) {
                loop$3_mappedLast1.next_$eq(loop$3_next);
            }
            loop$3_mappedLast1 = loop$3_next;
            loop$3_pending = loop$3_tail0 = (List)loop$3_pending.tail();
            loop$3_unchanged = loop$3_tail0;
            loop$3_mappedLast = loop$3_mappedLast1;
            loop$3_mappedHead = loop$3_mappedHead1;
        }
        Object var3_5 = null;
        var4_4 = null;
        list2 = null;
        list = null;
        Object var7_6 = null;
        Object var8_7 = null;
        Object var9_8 = null;
        Object var10_9 = null;
        Object var11_10 = null;
        Object var12_11 = null;
        Object var13_12 = null;
        Object var14_13 = null;
        $colon$colon<Nothing$> result = $colon$colon;
        Statics.releaseFence();
        return result;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public List<A> filter(Function1<A, Object> p) {
        void var3_16;
        Object var8_7;
        Object var7_6;
        Object var16_15;
        Object var15_14;
        Object var14_13;
        Object var13_12;
        Object var12_11;
        Object var11_10;
        Object var10_9;
        Object var9_8;
        List list;
        boolean filterCommon_isFlipped = false;
        List filterCommon_noneIn$1_l = this;
        while (true) {
            if (filterCommon_noneIn$1_l.isEmpty()) {
                list = Nil$.MODULE$;
                break;
            }
            Object filterCommon_noneIn$1_h = filterCommon_noneIn$1_l.head();
            List filterCommon_noneIn$1_t = (List)filterCommon_noneIn$1_l.tail();
            if (BoxesRunTime.unboxToBoolean(p.apply(filterCommon_noneIn$1_h)) != filterCommon_isFlipped) {
                block10: {
                    List filterCommon_noneIn$1_allIn$1_partialFill$1_next;
                    List filterCommon_noneIn$1_allIn$1_remaining = filterCommon_noneIn$1_t;
                    while (true) {
                        if (filterCommon_noneIn$1_allIn$1_remaining.isEmpty()) {
                            list = filterCommon_noneIn$1_l;
                            break block10;
                        }
                        Object filterCommon_noneIn$1_allIn$1_x = filterCommon_noneIn$1_allIn$1_remaining.head();
                        if (BoxesRunTime.unboxToBoolean(p.apply(filterCommon_noneIn$1_allIn$1_x)) == filterCommon_isFlipped) break;
                        filterCommon_noneIn$1_allIn$1_remaining = (List)filterCommon_noneIn$1_allIn$1_remaining.tail();
                    }
                    $colon$colon<Nothing$> filterCommon_noneIn$1_allIn$1_partialFill$1_newHead = new $colon$colon<Nothing$>((Nothing$)filterCommon_noneIn$1_l.head(), Nil$.MODULE$);
                    $colon$colon<Nothing$> filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterCommon_noneIn$1_allIn$1_partialFill$1_newHead;
                    for (List filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess = (List)filterCommon_noneIn$1_l.tail(); filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess != filterCommon_noneIn$1_allIn$1_remaining; filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess.tail()) {
                        $colon$colon<Nothing$> filterCommon_noneIn$1_allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess.head(), Nil$.MODULE$);
                        filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterCommon_noneIn$1_allIn$1_partialFill$1_newElem);
                        filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterCommon_noneIn$1_allIn$1_partialFill$1_newElem;
                    }
                    List filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterCommon_noneIn$1_allIn$1_remaining.tail();
                    while (!filterCommon_noneIn$1_allIn$1_partialFill$1_next.isEmpty()) {
                        Object filterCommon_noneIn$1_allIn$1_partialFill$1_head = filterCommon_noneIn$1_allIn$1_partialFill$1_next.head();
                        if (BoxesRunTime.unboxToBoolean(p.apply(filterCommon_noneIn$1_allIn$1_partialFill$1_head)) != filterCommon_isFlipped) {
                            filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                            continue;
                        }
                        while (filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy != filterCommon_noneIn$1_allIn$1_partialFill$1_next) {
                            $colon$colon<Nothing$> filterCommon_noneIn$1_allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.head(), Nil$.MODULE$);
                            filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterCommon_noneIn$1_allIn$1_partialFill$1_newElem);
                            filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterCommon_noneIn$1_allIn$1_partialFill$1_newElem;
                            filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.tail();
                        }
                        filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                        filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                    }
                    if (!filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.isEmpty()) {
                        filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy);
                    }
                    list = filterCommon_noneIn$1_allIn$1_partialFill$1_newHead;
                    var9_8 = null;
                    var10_9 = null;
                    var11_10 = null;
                    var12_11 = null;
                    var13_12 = null;
                    var14_13 = null;
                    var15_14 = null;
                    var16_15 = null;
                }
                var7_6 = null;
                var8_7 = null;
                var9_8 = null;
                var10_9 = null;
                var11_10 = null;
                var12_11 = null;
                var13_12 = null;
                var14_13 = null;
                var15_14 = null;
                var16_15 = null;
                break;
            }
            filterCommon_noneIn$1_l = filterCommon_noneIn$1_t;
        }
        Object var4_3 = null;
        Object var5_4 = null;
        Object var6_5 = null;
        var7_6 = null;
        var8_7 = null;
        var9_8 = null;
        var10_9 = null;
        var11_10 = null;
        var12_11 = null;
        var13_12 = null;
        var14_13 = null;
        var15_14 = null;
        var16_15 = null;
        List filterCommon_result = list;
        Statics.releaseFence();
        return var3_16;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public List<A> filterNot(Function1<A, Object> p) {
        void var3_16;
        Object var8_7;
        Object var7_6;
        Object var16_15;
        Object var15_14;
        Object var14_13;
        Object var13_12;
        Object var12_11;
        Object var11_10;
        Object var10_9;
        Object var9_8;
        List list;
        boolean filterCommon_isFlipped = true;
        List filterCommon_noneIn$1_l = this;
        while (true) {
            if (filterCommon_noneIn$1_l.isEmpty()) {
                list = Nil$.MODULE$;
                break;
            }
            Object filterCommon_noneIn$1_h = filterCommon_noneIn$1_l.head();
            List filterCommon_noneIn$1_t = (List)filterCommon_noneIn$1_l.tail();
            if (BoxesRunTime.unboxToBoolean(p.apply(filterCommon_noneIn$1_h)) != filterCommon_isFlipped) {
                block10: {
                    List filterCommon_noneIn$1_allIn$1_partialFill$1_next;
                    List filterCommon_noneIn$1_allIn$1_remaining = filterCommon_noneIn$1_t;
                    while (true) {
                        if (filterCommon_noneIn$1_allIn$1_remaining.isEmpty()) {
                            list = filterCommon_noneIn$1_l;
                            break block10;
                        }
                        Object filterCommon_noneIn$1_allIn$1_x = filterCommon_noneIn$1_allIn$1_remaining.head();
                        if (BoxesRunTime.unboxToBoolean(p.apply(filterCommon_noneIn$1_allIn$1_x)) == filterCommon_isFlipped) break;
                        filterCommon_noneIn$1_allIn$1_remaining = (List)filterCommon_noneIn$1_allIn$1_remaining.tail();
                    }
                    $colon$colon<Nothing$> filterCommon_noneIn$1_allIn$1_partialFill$1_newHead = new $colon$colon<Nothing$>((Nothing$)filterCommon_noneIn$1_l.head(), Nil$.MODULE$);
                    $colon$colon<Nothing$> filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterCommon_noneIn$1_allIn$1_partialFill$1_newHead;
                    for (List filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess = (List)filterCommon_noneIn$1_l.tail(); filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess != filterCommon_noneIn$1_allIn$1_remaining; filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess.tail()) {
                        $colon$colon<Nothing$> filterCommon_noneIn$1_allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)filterCommon_noneIn$1_allIn$1_partialFill$1_toProcess.head(), Nil$.MODULE$);
                        filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterCommon_noneIn$1_allIn$1_partialFill$1_newElem);
                        filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterCommon_noneIn$1_allIn$1_partialFill$1_newElem;
                    }
                    List filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterCommon_noneIn$1_allIn$1_remaining.tail();
                    while (!filterCommon_noneIn$1_allIn$1_partialFill$1_next.isEmpty()) {
                        Object filterCommon_noneIn$1_allIn$1_partialFill$1_head = filterCommon_noneIn$1_allIn$1_partialFill$1_next.head();
                        if (BoxesRunTime.unboxToBoolean(p.apply(filterCommon_noneIn$1_allIn$1_partialFill$1_head)) != filterCommon_isFlipped) {
                            filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                            continue;
                        }
                        while (filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy != filterCommon_noneIn$1_allIn$1_partialFill$1_next) {
                            $colon$colon<Nothing$> filterCommon_noneIn$1_allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.head(), Nil$.MODULE$);
                            filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterCommon_noneIn$1_allIn$1_partialFill$1_newElem);
                            filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast = filterCommon_noneIn$1_allIn$1_partialFill$1_newElem;
                            filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.tail();
                        }
                        filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                        filterCommon_noneIn$1_allIn$1_partialFill$1_next = (List)filterCommon_noneIn$1_allIn$1_partialFill$1_next.tail();
                    }
                    if (!filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy.isEmpty()) {
                        filterCommon_noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(filterCommon_noneIn$1_allIn$1_partialFill$1_nextToCopy);
                    }
                    list = filterCommon_noneIn$1_allIn$1_partialFill$1_newHead;
                    var9_8 = null;
                    var10_9 = null;
                    var11_10 = null;
                    var12_11 = null;
                    var13_12 = null;
                    var14_13 = null;
                    var15_14 = null;
                    var16_15 = null;
                }
                var7_6 = null;
                var8_7 = null;
                var9_8 = null;
                var10_9 = null;
                var11_10 = null;
                var12_11 = null;
                var13_12 = null;
                var14_13 = null;
                var15_14 = null;
                var16_15 = null;
                break;
            }
            filterCommon_noneIn$1_l = filterCommon_noneIn$1_t;
        }
        Object var4_3 = null;
        Object var5_4 = null;
        Object var6_5 = null;
        var7_6 = null;
        var8_7 = null;
        var9_8 = null;
        var10_9 = null;
        var11_10 = null;
        var12_11 = null;
        var13_12 = null;
        var14_13 = null;
        var15_14 = null;
        var16_15 = null;
        List filterCommon_result = list;
        Statics.releaseFence();
        return var3_16;
    }

    private List<A> filterCommon(Function1<A, Object> p, boolean isFlipped) {
        Object var8_7;
        Object var7_6;
        Object var16_15;
        Object var15_14;
        Object var14_13;
        Object var13_12;
        Object var12_11;
        Object var11_10;
        Object var10_9;
        Object var9_8;
        List list;
        List noneIn$1_l = this;
        while (true) {
            if (noneIn$1_l.isEmpty()) {
                list = Nil$.MODULE$;
                break;
            }
            Object noneIn$1_h = noneIn$1_l.head();
            List noneIn$1_t = (List)noneIn$1_l.tail();
            if (BoxesRunTime.unboxToBoolean(p.apply(noneIn$1_h)) != isFlipped) {
                block10: {
                    List noneIn$1_allIn$1_partialFill$1_next;
                    List noneIn$1_allIn$1_remaining = noneIn$1_t;
                    while (true) {
                        if (noneIn$1_allIn$1_remaining.isEmpty()) {
                            list = noneIn$1_l;
                            break block10;
                        }
                        Object noneIn$1_allIn$1_x = noneIn$1_allIn$1_remaining.head();
                        if (BoxesRunTime.unboxToBoolean(p.apply(noneIn$1_allIn$1_x)) == isFlipped) break;
                        noneIn$1_allIn$1_remaining = (List)noneIn$1_allIn$1_remaining.tail();
                    }
                    $colon$colon<Nothing$> noneIn$1_allIn$1_partialFill$1_newHead = new $colon$colon<Nothing$>((Nothing$)noneIn$1_l.head(), Nil$.MODULE$);
                    $colon$colon<Nothing$> noneIn$1_allIn$1_partialFill$1_currentLast = noneIn$1_allIn$1_partialFill$1_newHead;
                    for (List noneIn$1_allIn$1_partialFill$1_toProcess = (List)noneIn$1_l.tail(); noneIn$1_allIn$1_partialFill$1_toProcess != noneIn$1_allIn$1_remaining; noneIn$1_allIn$1_partialFill$1_toProcess = (List)noneIn$1_allIn$1_partialFill$1_toProcess.tail()) {
                        $colon$colon<Nothing$> noneIn$1_allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)noneIn$1_allIn$1_partialFill$1_toProcess.head(), Nil$.MODULE$);
                        noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(noneIn$1_allIn$1_partialFill$1_newElem);
                        noneIn$1_allIn$1_partialFill$1_currentLast = noneIn$1_allIn$1_partialFill$1_newElem;
                    }
                    List noneIn$1_allIn$1_partialFill$1_nextToCopy = noneIn$1_allIn$1_partialFill$1_next = (List)noneIn$1_allIn$1_remaining.tail();
                    while (!noneIn$1_allIn$1_partialFill$1_next.isEmpty()) {
                        Object noneIn$1_allIn$1_partialFill$1_head = noneIn$1_allIn$1_partialFill$1_next.head();
                        if (BoxesRunTime.unboxToBoolean(p.apply(noneIn$1_allIn$1_partialFill$1_head)) != isFlipped) {
                            noneIn$1_allIn$1_partialFill$1_next = (List)noneIn$1_allIn$1_partialFill$1_next.tail();
                            continue;
                        }
                        while (noneIn$1_allIn$1_partialFill$1_nextToCopy != noneIn$1_allIn$1_partialFill$1_next) {
                            $colon$colon<Nothing$> noneIn$1_allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)noneIn$1_allIn$1_partialFill$1_nextToCopy.head(), Nil$.MODULE$);
                            noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(noneIn$1_allIn$1_partialFill$1_newElem);
                            noneIn$1_allIn$1_partialFill$1_currentLast = noneIn$1_allIn$1_partialFill$1_newElem;
                            noneIn$1_allIn$1_partialFill$1_nextToCopy = (List)noneIn$1_allIn$1_partialFill$1_nextToCopy.tail();
                        }
                        noneIn$1_allIn$1_partialFill$1_nextToCopy = (List)noneIn$1_allIn$1_partialFill$1_next.tail();
                        noneIn$1_allIn$1_partialFill$1_next = (List)noneIn$1_allIn$1_partialFill$1_next.tail();
                    }
                    if (!noneIn$1_allIn$1_partialFill$1_nextToCopy.isEmpty()) {
                        noneIn$1_allIn$1_partialFill$1_currentLast.next_$eq(noneIn$1_allIn$1_partialFill$1_nextToCopy);
                    }
                    list = noneIn$1_allIn$1_partialFill$1_newHead;
                    var9_8 = null;
                    var10_9 = null;
                    var11_10 = null;
                    var12_11 = null;
                    var13_12 = null;
                    var14_13 = null;
                    var15_14 = null;
                    var16_15 = null;
                }
                var7_6 = null;
                var8_7 = null;
                var9_8 = null;
                var10_9 = null;
                var11_10 = null;
                var12_11 = null;
                var13_12 = null;
                var14_13 = null;
                var15_14 = null;
                var16_15 = null;
                break;
            }
            noneIn$1_l = noneIn$1_t;
        }
        Object var4_3 = null;
        Object var5_4 = null;
        Object var6_5 = null;
        var7_6 = null;
        var8_7 = null;
        var9_8 = null;
        var10_9 = null;
        var11_10 = null;
        var12_11 = null;
        var13_12 = null;
        var14_13 = null;
        var15_14 = null;
        var16_15 = null;
        List result = list;
        Statics.releaseFence();
        return result;
    }

    @Override
    public Tuple2<List<A>, List<A>> partition(Function1<A, Object> p) {
        if (this.isEmpty()) {
            return List$.MODULE$.scala$collection$immutable$List$$TupleOfNil();
        }
        Builder partition_l = this.newSpecificBuilder();
        Builder partition_r = this.newSpecificBuilder();
        this.iterator().foreach(arg_0 -> StrictOptimizedIterableOps.$anonfun$partition$1(p, partition_l, partition_r, arg_0));
        Object c = partition_r.result();
        Object c2 = partition_l.result();
        Object var5_2 = null;
        Object var6_3 = null;
        Tuple2<List<A>, List<A>> tuple2 = new Tuple2<List<A>, List<A>>(c2, c);
        List list = (List)c2;
        if (((Object)Nil$.MODULE$).equals(list)) {
            return new Tuple2<List<A>, List<A>>(Nil$.MODULE$, this);
        }
        List list2 = (List)c;
        if (((Object)Nil$.MODULE$).equals(list2)) {
            return new Tuple2<List<A>, List<A>>(this, Nil$.MODULE$);
        }
        return tuple2;
    }

    @Override
    public final List<A> toList() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof List) {
            List list = (List)o;
            return this.listEq$1(this, list);
        }
        return Seq.equals$(this, o);
    }

    private final List loop$1(List lead, List lag) {
        while (true) {
            if (((Object)Nil$.MODULE$).equals(lead)) {
                return lag;
            }
            if (!(lead instanceof $colon$colon)) break;
            lag = (List)lag.tail();
            lead = (($colon$colon)lead).next$access$1();
        }
        throw new MatchError(lead);
    }

    private final int loop$2(int i, List xs, int len$1) {
        while (true) {
            if (i == len$1) {
                if (xs.isEmpty()) {
                    return 0;
                }
                return 1;
            }
            if (xs.isEmpty()) {
                return -1;
            }
            xs = (List)xs.tail();
            ++i;
        }
    }

    private final List loop$3(List mappedHead, $colon$colon mappedLast, List unchanged, List pending, Function1 f$1) {
        while (true) {
            List tail0;
            if (pending.isEmpty()) {
                if (mappedHead == null) {
                    return unchanged;
                }
                mappedLast.next_$eq(unchanged);
                return mappedHead;
            }
            Object head0 = pending.head();
            Object head1 = f$1.apply(head0);
            if (head1 == head0) {
                pending = (List)pending.tail();
                continue;
            }
            $colon$colon<Nothing$> mappedHead1 = mappedHead;
            $colon$colon<Nothing$> mappedLast1 = mappedLast;
            for (List xc = unchanged; xc != pending; xc = (List)xc.tail()) {
                $colon$colon<Nothing$> next = new $colon$colon<Nothing$>((Nothing$)xc.head(), Nil$.MODULE$);
                if (mappedHead1 == null) {
                    mappedHead1 = next;
                }
                if (mappedLast1 != null) {
                    mappedLast1.next_$eq(next);
                }
                mappedLast1 = next;
            }
            $colon$colon<Nothing$> next = new $colon$colon<Nothing$>((Nothing$)head1, Nil$.MODULE$);
            if (mappedHead1 == null) {
                mappedHead1 = next;
            }
            if (mappedLast1 != null) {
                mappedLast1.next_$eq(next);
            }
            mappedLast1 = next;
            pending = tail0 = (List)pending.tail();
            unchanged = tail0;
            mappedLast = mappedLast1;
            mappedHead = mappedHead1;
        }
    }

    private final List noneIn$1(List l, Function1 p$1, boolean isFlipped$1) {
        while (!l.isEmpty()) {
            Object h2 = l.head();
            List t2 = (List)l.tail();
            if (BoxesRunTime.unboxToBoolean(p$1.apply(h2)) != isFlipped$1) {
                List allIn$1_partialFill$1_next;
                List allIn$1_remaining = t2;
                while (true) {
                    if (allIn$1_remaining.isEmpty()) {
                        return l;
                    }
                    Object allIn$1_x = allIn$1_remaining.head();
                    if (BoxesRunTime.unboxToBoolean(p$1.apply(allIn$1_x)) == isFlipped$1) break;
                    allIn$1_remaining = (List)allIn$1_remaining.tail();
                }
                $colon$colon<Nothing$> allIn$1_partialFill$1_newHead = new $colon$colon<Nothing$>((Nothing$)l.head(), Nil$.MODULE$);
                $colon$colon<Nothing$> allIn$1_partialFill$1_currentLast = allIn$1_partialFill$1_newHead;
                for (List allIn$1_partialFill$1_toProcess = (List)l.tail(); allIn$1_partialFill$1_toProcess != allIn$1_remaining; allIn$1_partialFill$1_toProcess = (List)allIn$1_partialFill$1_toProcess.tail()) {
                    $colon$colon<Nothing$> allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)allIn$1_partialFill$1_toProcess.head(), Nil$.MODULE$);
                    allIn$1_partialFill$1_currentLast.next_$eq(allIn$1_partialFill$1_newElem);
                    allIn$1_partialFill$1_currentLast = allIn$1_partialFill$1_newElem;
                }
                List allIn$1_partialFill$1_nextToCopy = allIn$1_partialFill$1_next = (List)allIn$1_remaining.tail();
                while (!allIn$1_partialFill$1_next.isEmpty()) {
                    Object allIn$1_partialFill$1_head = allIn$1_partialFill$1_next.head();
                    if (BoxesRunTime.unboxToBoolean(p$1.apply(allIn$1_partialFill$1_head)) != isFlipped$1) {
                        allIn$1_partialFill$1_next = (List)allIn$1_partialFill$1_next.tail();
                        continue;
                    }
                    while (allIn$1_partialFill$1_nextToCopy != allIn$1_partialFill$1_next) {
                        $colon$colon<Nothing$> allIn$1_partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)allIn$1_partialFill$1_nextToCopy.head(), Nil$.MODULE$);
                        allIn$1_partialFill$1_currentLast.next_$eq(allIn$1_partialFill$1_newElem);
                        allIn$1_partialFill$1_currentLast = allIn$1_partialFill$1_newElem;
                        allIn$1_partialFill$1_nextToCopy = (List)allIn$1_partialFill$1_nextToCopy.tail();
                    }
                    allIn$1_partialFill$1_nextToCopy = (List)allIn$1_partialFill$1_next.tail();
                    allIn$1_partialFill$1_next = (List)allIn$1_partialFill$1_next.tail();
                }
                if (!allIn$1_partialFill$1_nextToCopy.isEmpty()) {
                    allIn$1_partialFill$1_currentLast.next_$eq(allIn$1_partialFill$1_nextToCopy);
                }
                return allIn$1_partialFill$1_newHead;
            }
            l = t2;
        }
        return Nil$.MODULE$;
    }

    private final List allIn$1(List start2, List remaining, Function1 p$1, boolean isFlipped$1) {
        List partialFill$1_next;
        while (true) {
            if (remaining.isEmpty()) {
                return start2;
            }
            Object x = remaining.head();
            if (BoxesRunTime.unboxToBoolean(p$1.apply(x)) == isFlipped$1) break;
            remaining = (List)remaining.tail();
        }
        $colon$colon<Nothing$> partialFill$1_newHead = new $colon$colon<Nothing$>((Nothing$)start2.head(), Nil$.MODULE$);
        $colon$colon<Nothing$> partialFill$1_currentLast = partialFill$1_newHead;
        for (List partialFill$1_toProcess = (List)start2.tail(); partialFill$1_toProcess != remaining; partialFill$1_toProcess = (List)partialFill$1_toProcess.tail()) {
            $colon$colon<Nothing$> partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)partialFill$1_toProcess.head(), Nil$.MODULE$);
            partialFill$1_currentLast.next_$eq(partialFill$1_newElem);
            partialFill$1_currentLast = partialFill$1_newElem;
        }
        List partialFill$1_nextToCopy = partialFill$1_next = (List)remaining.tail();
        while (!partialFill$1_next.isEmpty()) {
            Object partialFill$1_head = partialFill$1_next.head();
            if (BoxesRunTime.unboxToBoolean(p$1.apply(partialFill$1_head)) != isFlipped$1) {
                partialFill$1_next = (List)partialFill$1_next.tail();
                continue;
            }
            while (partialFill$1_nextToCopy != partialFill$1_next) {
                $colon$colon<Nothing$> partialFill$1_newElem = new $colon$colon<Nothing$>((Nothing$)partialFill$1_nextToCopy.head(), Nil$.MODULE$);
                partialFill$1_currentLast.next_$eq(partialFill$1_newElem);
                partialFill$1_currentLast = partialFill$1_newElem;
                partialFill$1_nextToCopy = (List)partialFill$1_nextToCopy.tail();
            }
            partialFill$1_nextToCopy = (List)partialFill$1_next.tail();
            partialFill$1_next = (List)partialFill$1_next.tail();
        }
        if (!partialFill$1_nextToCopy.isEmpty()) {
            partialFill$1_currentLast.next_$eq(partialFill$1_nextToCopy);
        }
        return partialFill$1_newHead;
    }

    private static final List partialFill$1(List origStart, List firstMiss, Function1 p$1, boolean isFlipped$1) {
        List next;
        $colon$colon<Nothing$> newHead = new $colon$colon<Nothing$>((Nothing$)origStart.head(), Nil$.MODULE$);
        $colon$colon<Nothing$> currentLast = newHead;
        for (List toProcess = (List)origStart.tail(); toProcess != firstMiss; toProcess = (List)toProcess.tail()) {
            $colon$colon<Nothing$> newElem = new $colon$colon<Nothing$>((Nothing$)toProcess.head(), Nil$.MODULE$);
            currentLast.next_$eq(newElem);
            currentLast = newElem;
        }
        List nextToCopy = next = (List)firstMiss.tail();
        while (!next.isEmpty()) {
            Object head = next.head();
            if (BoxesRunTime.unboxToBoolean(p$1.apply(head)) != isFlipped$1) {
                next = (List)next.tail();
                continue;
            }
            while (nextToCopy != next) {
                $colon$colon<Nothing$> newElem = new $colon$colon<Nothing$>((Nothing$)nextToCopy.head(), Nil$.MODULE$);
                currentLast.next_$eq(newElem);
                currentLast = newElem;
                nextToCopy = (List)nextToCopy.tail();
            }
            nextToCopy = (List)next.tail();
            next = (List)next.tail();
        }
        if (!nextToCopy.isEmpty()) {
            currentLast.next_$eq(nextToCopy);
        }
        return newHead;
    }

    private final boolean listEq$1(List a, List b) {
        block2: {
            while (a != b) {
                boolean aEmpty = a.isEmpty();
                boolean bEmpty = b.isEmpty();
                if (!aEmpty && !bEmpty && BoxesRunTime.equals(a.head(), b.head())) {
                    b = (List)b.tail();
                    a = (List)a.tail();
                    continue;
                }
                if (!aEmpty || !bEmpty) break block2;
            }
            return true;
        }
        return false;
    }
}

