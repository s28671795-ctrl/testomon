/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IndexedSeq;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOnceOps$;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.collection.immutable.Set;
import scala.collection.immutable.Set$;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Stream$;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Buffer$;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0015}f!\u0003'N!\u0003\r\tAUCZ\u0011\u0015A\u0006\u0001\"\u0001Z\u0011\u0015i\u0006A\"\u0001_\u0011\u0015I\bA\"\u0001{\u0011\u001d\ti\u0001\u0001D\u0001\u0003\u001fAq!!\u0006\u0001\r\u0003\t9\u0002C\u0004\u0002$\u00011\t!!\n\t\u000f\u0005%\u0002A\"\u0001\u0002,!9\u0011q\u0006\u0001\u0007\u0002\u0005E\u0002bBA\u001b\u0001\u0019\u0005\u0011q\u0007\u0005\b\u0003\u0003\u0002a\u0011AA\"\u0011\u001d\t\u0019\u0006\u0001D\u0001\u0003+Bq!a\u001b\u0001\r\u0003\ti\u0007C\u0004\u0002\u0000\u00011\t!!!\t\u000f\u0005U\u0005A\"\u0001\u0002\u0018\"9\u0011Q\u0017\u0001\u0007\u0002\u0005]\u0006bBA_\u0001\u0011\u0005\u0011q\u0018\u0005\b\u0003\u0007\u0004a\u0011AAc\u0011\u001d\t\u0019\u000e\u0001C\u0001\u0003+Dq!a;\u0001\t\u0003\t)\u000eC\u0004\u0002n\u0002!\t!a<\t\u000f\u0005m\b\u0001\"\u0001\u0002~\"9!\u0011\u0001\u0001\u0005\u0002\t\r\u0001b\u0002B\u0004\u0001\u0011\u0005!\u0011\u0002\u0005\b\u0005\u001b\u0001A\u0011\u0001B\b\u0011!\u0011I\u0002\u0001Q\u0005\n\tm\u0001\u0002\u0003B\u001c\u0001\u0001&IA!\u000f\t\u000f\t%\u0003\u0001\"\u0001\u0003L!9!1\f\u0001\u0005\u0002\tu\u0003b\u0002B7\u0001\u0011\u0015!q\u000e\u0005\b\u0005\u001b\u0003AQ\u0001BH\u0011\u001d\u00119\u000b\u0001C\u0001\u0005SCqAa/\u0001\t\u0003\u0011i\fC\u0004\u0003J\u0002!\tAa3\t\u000f\te\u0007\u0001\"\u0001\u0003\\\"9!q\u001d\u0001\u0005\u0002\t%\bb\u0002B{\u0001\u0011\u0005!q\u001f\u0005\b\u0007\u000b\u0001A\u0011AB\u0004\u0011\u001d\u0019)\u0002\u0001C\u0001\u0003+Dqaa\u0006\u0001\t\u0003\t)\u000eC\u0004\u0004&\u0001!\taa\n\t\u000f\r%\u0002\u0001\"\u0002\u0004,!911\n\u0001\u0005\u0002\r5\u0003bBB&\u0001\u0011\u00051\u0011\u000e\u0005\b\u0007\u0017\u0002A\u0011AB=\u0011\u001d\u0019Y\t\u0001C\u0001\u0007\u001bCqa!*\u0001\t\u0003\u00199\u000bC\u0004\u00044\u0002!\ta!.\t\u000f\r\u001d\u0007\u0001\"\u0001\u0004J\"91Q\u001b\u0001\u0005\u0002\r]\u0007bBBr\u0001\u0011\u00051Q\u001d\u0005\b\u0007c\u0004A\u0011ABz\u0011\u001d!9\u0001\u0001C\u0001\t\u0013Aq\u0001b\u0007\u0001\t\u0003!i\u0002C\u0004\u00050\u0001!\t\u0001\"\r\t\u000f\u0011\r\u0003\u0001\"\u0001\u0005F!9A1\u000b\u0001\u0005\u0002\u0011U\u0003b\u0002C=\u0001\u0011\u0005A1\u0010\u0005\b\t\u001f\u0003AQ\u0001CI\u0011\u001d!y\t\u0001C\u0003\tgCq\u0001b$\u0001\t\u000b!I\fC\u0004\u0005>\u0002!\t\u0001b0\t\u000f\u0011u\u0006\u0001\"\u0002\u0005T\"9AQ\u0018\u0001\u0005\u0006\u0011u\u0007b\u0002Cs\u0001\u0011\u0005Aq\u001d\u0005\b\tw\u0004AQ\u0001C\u007f\u0011\u001d)i\u0001\u0001C\u0001\u000b\u001fAq!\"\b\u0001\t\u0003)y\u0002C\u0004\u0006(\u0001!\t!\"\u000b\t\u000f\u0015-\u0003\u0001\"\u0001\u0006N!9Q1\f\u0001\u0005\u0002\u0015u\u0003bBC3\u0001\u0011\u0005Qq\r\u0005\b\u000b[\u0002AQAC8\u0011\u001d)y\b\u0001C\u0003\u000b\u0003Cq!\"$\u0001\t\u0003)y\tC\u0004\u0006*\u0002!\t\"b+\u0003\u001f%#XM]1cY\u0016|enY3PaNT!AT(\u0002\u0015\r|G\u000e\\3di&|gNC\u0001Q\u0003\u0015\u00198-\u00197b\u0007\u0001)BaU;dyN\u0011\u0001\u0001\u0016\t\u0003+Zk\u0011aT\u0005\u0003/>\u00131!\u00118z\u0003\u0019!\u0013N\\5uIQ\t!\f\u0005\u0002V7&\u0011Al\u0014\u0002\u0005+:LG/\u0001\u0005tG\u0006tG*\u001a4u+\tyV\u000e\u0006\u0002aoR\u0011\u0011m\u001c\t\u0004E\u000edG\u0002\u0001\u0003\u0007I\u0002!)\u0019A3\u0003\u0005\r\u001bUC\u00014k#\t9G\u000b\u0005\u0002VQ&\u0011\u0011n\u0014\u0002\b\u001d>$\b.\u001b8h\t\u0015Y7M1\u0001g\u0005\u0011yF\u0005J\u0019\u0011\u0005\tlG!\u00028\u0003\u0005\u00041'!\u0001\"\t\u000bA\u0014\u0001\u0019A9\u0002\u0005=\u0004\b#B+sYRd\u0017BA:P\u0005%1UO\\2uS>t'\u0007\u0005\u0002ck\u00121a\u000f\u0001CC\u0002\u0019\u0014\u0011!\u0011\u0005\u0006q\n\u0001\r\u0001\\\u0001\u0002u\u00061a-\u001b7uKJ$\"a\u001f@\u0011\u0005\tdHAB?\u0001\t\u000b\u0007aMA\u0001D\u0011\u0019y8\u00011\u0001\u0002\u0002\u0005\t\u0001\u000f\u0005\u0004V\u0003\u0007!\u0018qA\u0005\u0004\u0003\u000by%!\u0003$v]\u000e$\u0018n\u001c82!\r)\u0016\u0011B\u0005\u0004\u0003\u0017y%a\u0002\"p_2,\u0017M\\\u0001\nM&dG/\u001a:O_R$2a_A\t\u0011\u001d\t\u0019\u0002\u0002a\u0001\u0003\u0003\tA\u0001\u001d:fI\u0006!A/Y6f)\rY\u0018\u0011\u0004\u0005\b\u00037)\u0001\u0019AA\u000f\u0003\u0005q\u0007cA+\u0002 %\u0019\u0011\u0011E(\u0003\u0007%sG/A\u0005uC.,w\u000b[5mKR\u001910a\n\t\r}4\u0001\u0019AA\u0001\u0003\u0011!'o\u001c9\u0015\u0007m\fi\u0003C\u0004\u0002\u001c\u001d\u0001\r!!\b\u0002\u0013\u0011\u0014x\u000e],iS2,GcA>\u00024!1q\u0010\u0003a\u0001\u0003\u0003\tQa\u001d7jG\u0016$Ra_A\u001d\u0003{Aq!a\u000f\n\u0001\u0004\ti\"\u0001\u0003ge>l\u0007bBA \u0013\u0001\u0007\u0011QD\u0001\u0006k:$\u0018\u000e\\\u0001\u0004[\u0006\u0004X\u0003BA#\u0003\u0017\"B!a\u0012\u0002NA!!mYA%!\r\u0011\u00171\n\u0003\u0006]*\u0011\rA\u001a\u0005\b\u0003\u001fR\u0001\u0019AA)\u0003\u00051\u0007CB+\u0002\u0004Q\fI%A\u0004gY\u0006$X*\u00199\u0016\t\u0005]\u0013Q\f\u000b\u0005\u00033\ny\u0006\u0005\u0003cG\u0006m\u0003c\u00012\u0002^\u0011)an\u0003b\u0001M\"9\u0011qJ\u0006A\u0002\u0005\u0005\u0004CB+\u0002\u0004Q\f\u0019\u0007\u0005\u0004\u0002f\u0005\u001d\u00141L\u0007\u0002\u001b&\u0019\u0011\u0011N'\u0003\u0019%#XM]1cY\u0016|enY3\u0002\u000f\u0019d\u0017\r\u001e;f]V!\u0011qNA;)\u0011\t\t(a\u001e\u0011\t\t\u001c\u00171\u000f\t\u0004E\u0006UD!\u00028\r\u0005\u00041\u0007bBA=\u0019\u0001\u000f\u00111P\u0001\u000bCNLE/\u001a:bE2,\u0007CB+\u0002\u0004Q\fi\b\u0005\u0004\u0002f\u0005\u001d\u00141O\u0001\bG>dG.Z2u+\u0011\t\u0019)!#\u0015\t\u0005\u0015\u00151\u0012\t\u0005E\u000e\f9\tE\u0002c\u0003\u0013#QA\\\u0007C\u0002\u0019Dq!!$\u000e\u0001\u0004\ty)\u0001\u0002qMB1Q+!%u\u0003\u000fK1!a%P\u0005=\u0001\u0016M\u001d;jC24UO\\2uS>t\u0017\u0001\u0004>ja^KG\u000f[%oI\u0016DXCAAM!\u0011\u00117-a'\u0011\u000fU\u000bi*!)\u0002\u001e%\u0019\u0011qT(\u0003\rQ+\b\u000f\\33U\r!\u00181U\u0016\u0003\u0003K\u0003B!a*\u000226\u0011\u0011\u0011\u0016\u0006\u0005\u0003W\u000bi+A\u0005v]\u000eDWmY6fI*\u0019\u0011qV(\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u00024\u0006%&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006!1\u000f]1o)\u0011\tI,a/\u0011\u000bU\u000bij_>\t\r}|\u0001\u0019AA\u0001\u0003\u001d\u0019\b\u000f\\5u\u0003R$B!!/\u0002B\"9\u00111\u0004\tA\u0002\u0005u\u0011a\u0002;ba\u0016\u000b7\r[\u000b\u0005\u0003\u000f\fy\rF\u0002|\u0003\u0013Dq!a\u0014\u0012\u0001\u0004\tY\r\u0005\u0004V\u0003\u0007!\u0018Q\u001a\t\u0004E\u0006=GABAi#\t\u0007aMA\u0001V\u0003=A\u0017m\u001d#fM&t\u0017\u000e^3TSj,WCAA\u0004Q-\u0011\u0012\u0011\\Ap\u0003C\f)/a:\u0011\u0007U\u000bY.C\u0002\u0002^>\u0013!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f#!a9\u0002O\u000eCWmY6!]-twn\u001e8TSj,\u0007%\u001b8ti\u0016\fG\rI8gA9B\u0017m\u001d#fM&t\u0017\u000e^3TSj,\u0007EZ8sA5|'/\u001a\u0011bGRLwN\\1cY\u0016\u0004\u0013N\u001c4pe6\fG/[8oA!\u001aX-\u001a\u0011tG\u0006d\u0017\rZ8dA\u0019|'\u000f\t3fi\u0006LGn]\u0015\u0002\u000bMLgnY3\"\u0005\u0005%\u0018A\u0002\u001a/cMr\u0003'\u0001\njgR\u0013\u0018M^3sg\u0006\u0014G.Z!hC&t\u0017a\u00024pe\u0016\f7\r[\u000b\u0005\u0003c\fI\u0010F\u0002[\u0003gDq!a\u0014\u0015\u0001\u0004\t)\u0010\u0005\u0004V\u0003\u0007!\u0018q\u001f\t\u0004E\u0006eHABAi)\t\u0007a-\u0001\u0004g_J\fG\u000e\u001c\u000b\u0005\u0003\u000f\ty\u0010\u0003\u0004\u0000+\u0001\u0007\u0011\u0011A\u0001\u0007KbL7\u000f^:\u0015\t\u0005\u001d!Q\u0001\u0005\u0007\u007fZ\u0001\r!!\u0001\u0002\u000b\r|WO\u001c;\u0015\t\u0005u!1\u0002\u0005\u0007\u007f^\u0001\r!!\u0001\u0002\t\u0019Lg\u000e\u001a\u000b\u0005\u0005#\u00119\u0002\u0005\u0003V\u0005'!\u0018b\u0001B\u000b\u001f\n1q\n\u001d;j_:Daa \rA\u0002\u0005\u0005\u0011!\u00024pY\u0012dW\u0003\u0002B\u000f\u0005C!\"Ba\b\u0003$\t5\"\u0011\u0007B\u001a!\r\u0011'\u0011\u0005\u0003\u0006]f\u0011\rA\u001a\u0005\b\u0005KI\u0002\u0019\u0001B\u0014\u0003\r\u0019X-\u001d\t\u0006\u0003K\u0012I\u0003^\u0005\u0004\u0005Wi%AC%oI\u0016DX\rZ*fc\"9!qF\rA\u0002\u0005u\u0011!B:uCJ$\bB\u0002=\u001a\u0001\u0004\u0011y\u0002\u0003\u0004q3\u0001\u0007!Q\u0007\t\b+J\u0014y\u0002\u001eB\u0010\u0003\u00151w\u000e\u001c3s+\u0011\u0011YDa\u0010\u0015\r\tu\"1\tB#!\r\u0011'q\b\u0003\u0007]j\u0011\rA!\u0011\u0012\u0005Q$\u0006b\u0002B\u00135\u0001\u0007!q\u0005\u0005\u0007aj\u0001\rAa\u0012\u0011\u000fU\u0013HO!\u0010\u0003>\u0005Aam\u001c7e\u0019\u00164G/\u0006\u0003\u0003N\tMC\u0003\u0002B(\u00053\"BA!\u0015\u0003VA\u0019!Ma\u0015\u0005\u000b9\\\"\u0019\u00014\t\rA\\\u0002\u0019\u0001B,!\u001d)&O!\u0015u\u0005#Ba\u0001_\u000eA\u0002\tE\u0013!\u00034pY\u0012\u0014\u0016n\u001a5u+\u0011\u0011yF!\u001a\u0015\t\t\u0005$1\u000e\u000b\u0005\u0005G\u00129\u0007E\u0002c\u0005K\"QA\u001c\u000fC\u0002\u0019Da\u0001\u001d\u000fA\u0002\t%\u0004cB+si\n\r$1\r\u0005\u0007qr\u0001\rAa\u0019\u0002\u0015\u0011\"\u0017N\u001e\u0013d_2|g.\u0006\u0003\u0003r\t]D\u0003\u0002B:\u0005{\"BA!\u001e\u0003zA\u0019!Ma\u001e\u0005\u000b9l\"\u0019\u00014\t\rAl\u0002\u0019\u0001B>!\u001d)&O!\u001eu\u0005kBa\u0001_\u000fA\u0002\tU\u0004fA\u000f\u0003\u0002B\u0019QKa!\n\u0007\t\u0015uJ\u0001\u0004j]2Lg.\u001a\u0015\f;\u0005e\u0017q\u001cBE\u0003K\f9/\t\u0002\u0003\f\u0006QRk]3!M>dG\rT3gi\u0002Jgn\u001d;fC\u0012\u0004sN\u001a\u00110u\u0005iAeY8m_:$#m\u001d7bg\",BA!%\u0003\u0018R!!1\u0013BO)\u0011\u0011)J!'\u0011\u0007\t\u00149\nB\u0003o=\t\u0007a\r\u0003\u0004q=\u0001\u0007!1\u0014\t\b+J$(Q\u0013BK\u0011\u0019Ah\u00041\u0001\u0003\u0016\"\u001aaD!!)\u0017y\tI.a8\u0003$\u0006\u0015\u0018q]\u0011\u0003\u0005K\u000b1$V:fA\u0019|G\u000e\u001a*jO\"$\b%\u001b8ti\u0016\fG\rI8gAib\u0016\u0001\u00024pY\u0012,BAa+\u00032R!!Q\u0016B])\u0011\u0011yK!.\u0011\u0007\t\u0014\t\fB\u0004\u00034~\u0011\rA!\u0011\u0003\u0005\u0005\u000b\u0004B\u00029 \u0001\u0004\u00119\f\u0005\u0005Ve\n=&q\u0016BX\u0011\u0019Ax\u00041\u0001\u00030\u00061!/\u001a3vG\u0016,BAa0\u0003DR!!\u0011\u0019Bc!\r\u0011'1\u0019\u0003\u0007]\u0002\u0012\rA!\u0011\t\rA\u0004\u0003\u0019\u0001Bd!!)&O!1\u0003B\n\u0005\u0017\u0001\u0004:fIV\u001cWm\u00149uS>tW\u0003\u0002Bg\u0005'$BAa4\u0003VB)QKa\u0005\u0003RB\u0019!Ma5\u0005\r9\f#\u0019\u0001B!\u0011\u0019\u0001\u0018\u00051\u0001\u0003XBAQK\u001dBi\u0005#\u0014\t.\u0001\u0006sK\u0012,8-\u001a'fMR,BA!8\u0003bR!!q\u001cBr!\r\u0011'\u0011\u001d\u0003\u0007]\n\u0012\rA!\u0011\t\rA\u0014\u0003\u0019\u0001Bs!\u001d)&Oa8u\u0005?\f1B]3ek\u000e,'+[4iiV!!1\u001eBx)\u0011\u0011iO!=\u0011\u0007\t\u0014y\u000f\u0002\u0004oG\t\u0007!\u0011\t\u0005\u0007a\u000e\u0002\rAa=\u0011\u000fU\u0013HO!<\u0003n\u0006\u0001\"/\u001a3vG\u0016dUM\u001a;PaRLwN\\\u000b\u0005\u0005s\u0014y\u0010\u0006\u0003\u0003|\u000e\u0005\u0001#B+\u0003\u0014\tu\bc\u00012\u0003\u0000\u00121a\u000e\nb\u0001\u0005\u0003Ba\u0001\u001d\u0013A\u0002\r\r\u0001cB+s\u0005{$(Q`\u0001\u0012e\u0016$WoY3SS\u001eDGo\u00149uS>tW\u0003BB\u0005\u0007\u001f!Baa\u0003\u0004\u0012A)QKa\u0005\u0004\u000eA\u0019!ma\u0004\u0005\r9,#\u0019\u0001B!\u0011\u0019\u0001X\u00051\u0001\u0004\u0014A9QK\u001d;\u0004\u000e\r5\u0011aB5t\u000b6\u0004H/_\u0001\t]>tW)\u001c9us\"Zqea\u0007\u0002`\u000e\u0005\u0012Q]At!\r)6QD\u0005\u0004\u0007?y%\u0001\u00063faJ,7-\u0019;fI>3XM\u001d:jI&tw-\t\u0002\u0004$\u0005Idn\u001c8F[B$\u0018\u0010I5tA\u0011,g-\u001b8fI\u0002\n7\u000fI\u0011jg\u0016k\u0007\u000f^=<A=4XM\u001d:jI\u0016\u0004\u0013n]#naRL\b%\u001b8ti\u0016\fG-\u0001\u0003tSj,WCAA\u000f\u00031\u0019w\u000e]=U_\n+hMZ3s+\u0011\u0019ic!\u0011\u0015\u0007i\u001by\u0003C\u0004\u00042%\u0002\raa\r\u0002\t\u0011,7\u000f\u001e\t\u0007\u0007k\u0019Yda\u0010\u000e\u0005\r]\"bAB\u001d\u001b\u00069Q.\u001e;bE2,\u0017\u0002BB\u001f\u0007o\u0011aAQ;gM\u0016\u0014\bc\u00012\u0004B\u00111a.\u000bb\u0001\u0005\u0003B3!\u000bBAQ-I\u0013\u0011\\Ap\u0007\u000f\n)/a:\"\u0005\r%\u0013aG+tK\u0002\u0002G-Z:uA-ZS\bI2pY2\u0004\u0007%\u001b8ti\u0016\fG-A\u0006d_BLHk\\!se\u0006LX\u0003BB(\u0007;\"B!!\b\u0004R!911\u000b\u0016A\u0002\rU\u0013A\u0001=t!\u0015)6qKB.\u0013\r\u0019If\u0014\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0004E\u000euCA\u00028+\u0005\u0004\u0011\t\u0005K\u0006+\u00077\tyn!\u0019\u0002f\u000e\u0015\u0014EAB2\u0003y\"\u0006.[:!g\"|W\u000f\u001c3!C2<\u0018-_:!M>\u0014x/\u0019:eAQ|\u0007\u0005\u001e5fAMj\u0013M]4!m\u0016\u00148/[8oA=4\u0007\u0005\u001e5jg\u0002jW\r\u001e5pI\u0006\u00121qM\u0001\u0007e9\n4G\f\u001b\u0016\t\r-41\u000f\u000b\u0007\u0003;\u0019ig!\u001e\t\u000f\rM3\u00061\u0001\u0004pA)Qka\u0016\u0004rA\u0019!ma\u001d\u0005\r9\\#\u0019\u0001B!\u0011\u001d\u0011yc\u000ba\u0001\u0003;A3bKB\u000e\u0003?\u001c\t'!:\u0004fU!11PBB)!\tib! \u0004\u0006\u000e\u001d\u0005bBB*Y\u0001\u00071q\u0010\t\u0006+\u000e]3\u0011\u0011\t\u0004E\u000e\rEA\u00028-\u0005\u0004\u0011\t\u0005C\u0004\u000301\u0002\r!!\b\t\u000f\r%E\u00061\u0001\u0002\u001e\u0005\u0019A.\u001a8\u0002\u0007M,X.\u0006\u0003\u0004\u0010\u000eME\u0003BBI\u0007+\u00032AYBJ\t\u0019qWF1\u0001\u0003B!91qS\u0017A\u0004\re\u0015a\u00018v[B111TBQ\u0007#k!a!(\u000b\u0007\r}u*\u0001\u0003nCRD\u0017\u0002BBR\u0007;\u0013qAT;nKJL7-A\u0004qe>$Wo\u0019;\u0016\t\r%6Q\u0016\u000b\u0005\u0007W\u001by\u000bE\u0002c\u0007[#aA\u001c\u0018C\u0002\t\u0005\u0003bBBL]\u0001\u000f1\u0011\u0017\t\u0007\u00077\u001b\tka+\u0002\u00075Lg.\u0006\u0003\u00048\u000e\u0015Gc\u0001;\u0004:\"911X\u0018A\u0004\ru\u0016aA8sIB111TB`\u0007\u0007LAa!1\u0004\u001e\nAqJ\u001d3fe&tw\rE\u0002c\u0007\u000b$aA\\\u0018C\u0002\t\u0005\u0013!C7j]>\u0003H/[8o+\u0011\u0019Yma5\u0015\t\tE1Q\u001a\u0005\b\u0007w\u0003\u00049ABh!\u0019\u0019Yja0\u0004RB\u0019!ma5\u0005\r9\u0004$\u0019\u0001B!\u0003\ri\u0017\r_\u000b\u0005\u00073\u001c\t\u000fF\u0002u\u00077Dqaa/2\u0001\b\u0019i\u000e\u0005\u0004\u0004\u001c\u000e}6q\u001c\t\u0004E\u000e\u0005HA\u000282\u0005\u0004\u0011\t%A\u0005nCb|\u0005\u000f^5p]V!1q]Bx)\u0011\u0011\tb!;\t\u000f\rm&\u0007q\u0001\u0004lB111TB`\u0007[\u00042AYBx\t\u0019q'G1\u0001\u0003B\u0005)Q.\u0019=CsV!1Q\u001fC\u0001)\u0011\u00199\u0010b\u0001\u0015\u0007Q\u001cI\u0010C\u0004\u0004|N\u0002\u001da!@\u0002\u0007\rl\u0007\u000f\u0005\u0004\u0004\u001c\u000e}6q \t\u0004E\u0012\u0005A!\u000284\u0005\u00041\u0007bBA(g\u0001\u0007AQ\u0001\t\u0007+\u0006\rAoa@\u0002\u00175\f\u0007PQ=PaRLwN\\\u000b\u0005\t\u0017!)\u0002\u0006\u0003\u0005\u000e\u0011]A\u0003\u0002B\t\t\u001fAqaa?5\u0001\b!\t\u0002\u0005\u0004\u0004\u001c\u000e}F1\u0003\t\u0004E\u0012UA!\u000285\u0005\u00041\u0007bBA(i\u0001\u0007A\u0011\u0004\t\u0007+\u0006\rA\u000fb\u0005\u0002\u000b5LgNQ=\u0016\t\u0011}A\u0011\u0006\u000b\u0005\tC!Y\u0003F\u0002u\tGAqaa?6\u0001\b!)\u0003\u0005\u0004\u0004\u001c\u000e}Fq\u0005\t\u0004E\u0012%B!\u000286\u0005\u00041\u0007bBA(k\u0001\u0007AQ\u0006\t\u0007+\u0006\rA\u000fb\n\u0002\u00175LgNQ=PaRLwN\\\u000b\u0005\tg!i\u0004\u0006\u0003\u00056\u0011}B\u0003\u0002B\t\toAqaa?7\u0001\b!I\u0004\u0005\u0004\u0004\u001c\u000e}F1\b\t\u0004E\u0012uB!\u000287\u0005\u00041\u0007bBA(m\u0001\u0007A\u0011\t\t\u0007+\u0006\rA\u000fb\u000f\u0002\u0019\r|G\u000e\\3di\u001aK'o\u001d;\u0016\t\u0011\u001dCQ\n\u000b\u0005\t\u0013\"y\u0005E\u0003V\u0005'!Y\u0005E\u0002c\t\u001b\"QA\\\u001cC\u0002\u0019Dq!!$8\u0001\u0004!\t\u0006\u0005\u0004V\u0003##H1J\u0001\nC\u001e<'/Z4bi\u0016,B\u0001b\u0016\u0005^Q!A\u0011\fC6)\u0019!Y\u0006b\u0018\u0005fA\u0019!\r\"\u0018\u0005\u000b9D$\u0019\u00014\t\u000f\u0011\u0005\u0004\b1\u0001\u0005d\u0005)1/Z9paB9QK\u001dC.i\u0012m\u0003b\u0002C4q\u0001\u0007A\u0011N\u0001\u0007G>l'm\u001c9\u0011\u0011U\u0013H1\fC.\t7Bq\u0001\u001f\u001d\u0005\u0002\u0004!i\u0007E\u0003V\t_\"Y&C\u0002\u0005r=\u0013\u0001\u0002\u00102z]\u0006lWM\u0010\u0015\fq\u0005e\u0017q\u001cC;\u0003K\f9/\t\u0002\u0005x\u0005I\u0006-Y4he\u0016<\u0017\r^3aA%\u001c\bE\\8uAI,G.\u001a<b]R\u0004cm\u001c:!g\u0016\fX/\u001a8uS\u0006d\u0007eY8mY\u0016\u001cG/[8og:\u0002Sk]3!A\u001a|G\u000e\u001a'fMRD#0\u000b\u0015tKF|\u0007/\u000b1!S:\u001cH/Z1e]\u0005Y1m\u001c:sKN\u0004xN\u001c3t+\u0011!i\bb\"\u0015\t\u0011}D\u0011\u0012\u000b\u0005\u0003\u000f!\t\t\u0003\u0004\u0000s\u0001\u0007A1\u0011\t\b+J$HQQA\u0004!\r\u0011Gq\u0011\u0003\u0006]f\u0012\rA\u001a\u0005\b\t\u0017K\u0004\u0019\u0001CG\u0003\u0011!\b.\u0019;\u0011\r\u0005\u0015\u0014q\rCC\u0003!i7n\u0015;sS:<G\u0003\u0003CJ\tS#Y\u000bb,\u0011\t\u0011UE1\u0015\b\u0005\t/#y\nE\u0002\u0005\u001a>k!\u0001b'\u000b\u0007\u0011u\u0015+\u0001\u0004=e>|GOP\u0005\u0004\tC{\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0005&\u0012\u001d&AB*ue&twMC\u0002\u0005\">CqAa\f;\u0001\u0004!\u0019\nC\u0004\u0005.j\u0002\r\u0001b%\u0002\u0007M,\u0007\u000fC\u0004\u00052j\u0002\r\u0001b%\u0002\u0007\u0015tG\r\u0006\u0003\u0005\u0014\u0012U\u0006b\u0002CWw\u0001\u0007A1\u0013\u0015\u0004w\t\u0005UC\u0001CJQ\ra$\u0011Q\u0001\nC\u0012$7\u000b\u001e:j]\u001e$\"\u0002\"1\u0005D\u00125Gq\u001aCi\u001d\r\u0011G1\u0019\u0005\b\t\u000bl\u0004\u0019\u0001Cd\u0003\u0005\u0011\u0007\u0003BB\u001b\t\u0013LA\u0001b3\u00048\ti1\u000b\u001e:j]\u001e\u0014U/\u001b7eKJDqAa\f>\u0001\u0004!\u0019\nC\u0004\u0005.v\u0002\r\u0001b%\t\u000f\u0011EV\b1\u0001\u0005\u0014R1AQ\u001bCl\t3t1A\u0019Cl\u0011\u001d!)M\u0010a\u0001\t\u000fDq\u0001\",?\u0001\u0004!\u0019\nK\u0002?\u0005\u0003#B\u0001b8\u0005b:\u0019!\r\"9\t\u000f\u0011\u0015w\b1\u0001\u0005H\"\u001aqH!!\u0002\u0005Q|W\u0003\u0002Cu\t[$B\u0001b;\u0005rB\u0019!\r\"<\u0005\r\u0011=\bI1\u0001g\u0005\t\u0019\u0015\u0007C\u0004\u0005t\u0002\u0003\r\u0001\">\u0002\u000f\u0019\f7\r^8ssB9\u0011Q\rC|i\u0012-\u0018b\u0001C}\u001b\n9a)Y2u_JL\u0018A\u0003;p\u0013R,'/\u0019;peV\u0011Aq \t\u0006\u0003K*\t\u0001^\u0005\u0004\u000b\u0007i%\u0001C%uKJ\fGo\u001c:)\u0007\u0005\u0013\t\tK\u0006B\u00033\fy.\"\u0003\u0002f\u0006\u001d\u0018EAC\u0006\u0003\u0011*6/\u001a\u0011/SR,'/\u0019;pe\u0002Jgn\u001d;fC\u0012\u0004sN\u001a\u0011/i>LE/\u001a:bi>\u0014\u0018A\u0002;p\u0019&\u001cH/\u0006\u0002\u0006\u0012A)Q1CC\ri6\u0011QQ\u0003\u0006\u0004\u000b/i\u0015!C5n[V$\u0018M\u00197f\u0013\u0011)Y\"\"\u0006\u0003\t1K7\u000f^\u0001\ti>4Vm\u0019;peV\u0011Q\u0011\u0005\t\u0006\u000b')\u0019\u0003^\u0005\u0005\u000bK))B\u0001\u0004WK\u000e$xN]\u0001\u0006i>l\u0015\r]\u000b\u0007\u000bW))$b\u000f\u0015\t\u00155Rq\b\t\t\u000b')y#b\r\u0006:%!Q\u0011GC\u000b\u0005\ri\u0015\r\u001d\t\u0004E\u0016UBABC\u001c\t\n\u0007aMA\u0001L!\r\u0011W1\b\u0003\u0007\u000b{!%\u0019\u00014\u0003\u0003YCq!\"\u0011E\u0001\b)\u0019%\u0001\u0002fmB1Q+\"\u0012u\u000b\u0013J1!b\u0012P\u0005A!C.Z:tI\r|Gn\u001c8%Y\u0016\u001c8\u000fE\u0004V\u0003;+\u0019$\"\u000f\u0002\u000bQ|7+\u001a;\u0016\t\u0015=S\u0011L\u000b\u0003\u000b#\u0002b!b\u0005\u0006T\u0015]\u0013\u0002BC+\u000b+\u00111aU3u!\r\u0011W\u0011\f\u0003\u0007]\u0016\u0013\rA!\u0011\u0002\u000bQ|7+Z9\u0016\u0005\u0015}\u0003#BC\n\u000bC\"\u0018\u0002BC2\u000b+\u00111aU3r\u00031!x.\u00138eKb,GmU3r+\t)I\u0007E\u0003\u0006\u0014\u0015-D/\u0003\u0003\u0003,\u0015U\u0011\u0001\u0003;p'R\u0014X-Y7\u0016\u0005\u0015E\u0004#BC\n\u000bg\"\u0018\u0002BC;\u000b+\u0011aa\u0015;sK\u0006l\u0007f\u0001%\u0003\u0002\"Z\u0001*!7\u0002`\u0016m\u0014Q]AtC\t)i(\u0001\u0014Vg\u0016\u0004c\u0006^8)\u0019\u0006T\u0018\u0010T5ti&\u0002\u0013N\\:uK\u0006$\u0007e\u001c4!]Q|7\u000b\u001e:fC6\f\u0001\u0002^8Ck\u001a4WM]\u000b\u0005\u000b\u0007+I)\u0006\u0002\u0006\u0006B11QGB\u001e\u000b\u000f\u00032AYCE\t\u0019q\u0017J1\u0001\u0003B!\u001a\u0011J!!\u0002\u000fQ|\u0017I\u001d:bsV!Q\u0011SCL)\u0011)\u0019*\"'\u0011\u000bU\u001b9&\"&\u0011\u0007\t,9\n\u0002\u0004o\u0015\n\u0007!\u0011\t\u0005\n\u000b7S\u0015\u0011!a\u0002\u000b;\u000b!\"\u001a<jI\u0016t7-\u001a\u00133!\u0019)y*\"*\u0006\u00166\u0011Q\u0011\u0015\u0006\u0004\u000bG{\u0015a\u0002:fM2,7\r^\u0005\u0005\u000bO+\tK\u0001\u0005DY\u0006\u001c8\u000fV1h\u0003!\u0011XM^3sg\u0016$WCACW!\u0015\t)'b,u\u0013\r)\t,\u0014\u0002\t\u0013R,'/\u00192mKJ1QQWC]\u000b{3a!b.\u0001\u0001\u0015M&\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004cBA3\u0001Q,Yl\u001f\t\u0003E\u000e\u0004R!!\u001a\u0002hQ\u0004")
public interface IterableOnceOps<A, CC, C> {
    public <B> CC scanLeft(B var1, Function2<B, A, B> var2);

    public C filter(Function1<A, Object> var1);

    public C filterNot(Function1<A, Object> var1);

    public C take(int var1);

    public C takeWhile(Function1<A, Object> var1);

    public C drop(int var1);

    public C dropWhile(Function1<A, Object> var1);

    public C slice(int var1, int var2);

    public <B> CC map(Function1<A, B> var1);

    public <B> CC flatMap(Function1<A, IterableOnce<B>> var1);

    public <B> CC flatten(Function1<A, IterableOnce<B>> var1);

    public <B> CC collect(PartialFunction<A, B> var1);

    public CC zipWithIndex();

    public Tuple2<C, C> span(Function1<A, Object> var1);

    public static /* synthetic */ Tuple2 splitAt$(IterableOnceOps $this, int n) {
        return $this.splitAt(n);
    }

    default public Tuple2<C, C> splitAt(int n) {
        public class Scala_collection_IterableOnceOps$Spanner$1
        extends AbstractFunction1<A, Object> {
            private int i;
            private final int n$1;

            public int i() {
                return this.i;
            }

            public void i_$eq(int x$1) {
                this.i = x$1;
            }

            public boolean apply(A a) {
                if (this.i() < this.n$1) {
                    this.i_$eq(this.i() + 1);
                    return true;
                }
                return false;
            }

            public Scala_collection_IterableOnceOps$Spanner$1(IterableOnceOps $outer, int n$1) {
                this.n$1 = n$1;
                this.i = 0;
            }
        }
        Scala_collection_IterableOnceOps$Spanner$1 spanner = new Scala_collection_IterableOnceOps$Spanner$1(null, n);
        return this.span(spanner);
    }

    public <U> C tapEach(Function1<A, U> var1);

    public static /* synthetic */ boolean hasDefiniteSize$(IterableOnceOps $this) {
        return $this.hasDefiniteSize();
    }

    default public boolean hasDefiniteSize() {
        return true;
    }

    public static /* synthetic */ boolean isTraversableAgain$(IterableOnceOps $this) {
        return $this.isTraversableAgain();
    }

    default public boolean isTraversableAgain() {
        return false;
    }

    public static /* synthetic */ void foreach$(IterableOnceOps $this, Function1 f) {
        $this.foreach(f);
    }

    default public <U> void foreach(Function1<A, U> f) {
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        while (it.hasNext()) {
            f.apply(it.next());
        }
    }

    public static /* synthetic */ boolean forall$(IterableOnceOps $this, Function1 p) {
        return $this.forall(p);
    }

    default public boolean forall(Function1<A, Object> p) {
        boolean res = true;
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        while (res && it.hasNext()) {
            res = BoxesRunTime.unboxToBoolean(p.apply(it.next()));
        }
        return res;
    }

    public static /* synthetic */ boolean exists$(IterableOnceOps $this, Function1 p) {
        return $this.exists(p);
    }

    default public boolean exists(Function1<A, Object> p) {
        boolean res = false;
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        while (!res && it.hasNext()) {
            res = BoxesRunTime.unboxToBoolean(p.apply(it.next()));
        }
        return res;
    }

    public static /* synthetic */ int count$(IterableOnceOps $this, Function1 p) {
        return $this.count(p);
    }

    default public int count(Function1<A, Object> p) {
        int res = 0;
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        while (it.hasNext()) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(it.next()))) continue;
            ++res;
        }
        return res;
    }

    public static /* synthetic */ Option find$(IterableOnceOps $this, Function1 p) {
        return $this.find(p);
    }

    default public Option<A> find(Function1<A, Object> p) {
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        while (it.hasNext()) {
            Object a = it.next();
            if (!BoxesRunTime.unboxToBoolean(p.apply(a))) continue;
            return new Some(a);
        }
        return None$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    private <B> B foldl(IndexedSeq<A> seq, int start2, B z, Function2<B, A, B> op) {
        B b = z;
        int n = seq.length();
        int loop$1_at = start2;
        B loop$1_acc;
        void loop$1_end;
        while (loop$1_at != loop$1_end) {
            loop$1_acc = op.apply(loop$1_acc, seq.apply(loop$1_at));
            ++loop$1_at;
        }
        return loop$1_acc;
    }

    private <B> B foldr(IndexedSeq<A> seq, Function2<A, B, B> op) {
        Object r = seq.apply(seq.length() - 1);
        int loop$2_at = seq.length() - 1;
        B loop$2_acc;
        while (loop$2_at != 0) {
            loop$2_acc = op.apply(seq.apply(loop$2_at - 1), loop$2_acc);
            --loop$2_at;
        }
        return loop$2_acc;
    }

    public static /* synthetic */ Object foldLeft$(IterableOnceOps $this, Object z, Function2 op) {
        return $this.foldLeft(z, op);
    }

    /*
     * WARNING - void declaration
     */
    default public <B> B foldLeft(B z, Function2<B, A, B> op) {
        if (this instanceof IndexedSeq) {
            IndexedSeq indexedSeq = (IndexedSeq)this;
            B b = z;
            int n = indexedSeq.length();
            int foldl_loop$1_at = 0;
            while (true) {
                B foldl_loop$1_acc;
                void foldl_loop$1_end;
                if (foldl_loop$1_at == foldl_loop$1_end) {
                    return foldl_loop$1_acc;
                }
                foldl_loop$1_acc = op.apply(foldl_loop$1_acc, indexedSeq.apply(foldl_loop$1_at));
                ++foldl_loop$1_at;
            }
        }
        B result = z;
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        while (it.hasNext()) {
            result = op.apply(result, it.next());
        }
        return result;
    }

    public static /* synthetic */ Object foldRight$(IterableOnceOps $this, Object z, Function2 op) {
        return $this.foldRight(z, op);
    }

    default public <B> B foldRight(B z, Function2<A, B, B> op) {
        return this.reversed().foldLeft(z, (Function2<Object, Object, Object> & Serializable)(b, a) -> op.apply(a, b));
    }

    public static /* synthetic */ Object $div$colon$(IterableOnceOps $this, Object z, Function2 op) {
        return $this.$div$colon(z, op);
    }

    default public <B> B $div$colon(B z, Function2<B, A, B> op) {
        return this.foldLeft(z, op);
    }

    public static /* synthetic */ Object $colon$bslash$(IterableOnceOps $this, Object z, Function2 op) {
        return $this.$colon$bslash(z, op);
    }

    default public <B> B $colon$bslash(B z, Function2<A, B, B> op) {
        return this.foldRight(z, op);
    }

    public static /* synthetic */ Object fold$(IterableOnceOps $this, Object z, Function2 op) {
        return $this.fold(z, op);
    }

    default public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
        return this.foldLeft(z, op);
    }

    public static /* synthetic */ Object reduce$(IterableOnceOps $this, Function2 op) {
        return $this.reduce(op);
    }

    default public <B> B reduce(Function2<B, B, B> op) {
        return this.reduceLeft(op);
    }

    public static /* synthetic */ Option reduceOption$(IterableOnceOps $this, Function2 op) {
        return $this.reduceOption(op);
    }

    default public <B> Option<B> reduceOption(Function2<B, B, B> op) {
        return this.reduceLeftOption(op);
    }

    public static /* synthetic */ Object reduceLeft$(IterableOnceOps $this, Function2 op) {
        return $this.reduceLeft(op);
    }

    /*
     * WARNING - void declaration
     */
    default public <B> B reduceLeft(Function2<B, A, B> op) {
        IndexedSeq indexedSeq;
        if (this instanceof IndexedSeq && (indexedSeq = (IndexedSeq)this).length() > 0) {
            Object foldl_z;
            Object r = foldl_z = indexedSeq.apply(false);
            int n = indexedSeq.length();
            int foldl_loop$1_at = 1;
            while (true) {
                B foldl_loop$1_acc;
                void foldl_loop$1_end;
                if (foldl_loop$1_at == foldl_loop$1_end) {
                    return foldl_loop$1_acc;
                }
                foldl_loop$1_acc = op.apply(foldl_loop$1_acc, indexedSeq.apply(foldl_loop$1_at));
                ++foldl_loop$1_at;
            }
        }
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        if (it.isEmpty()) {
            throw new UnsupportedOperationException("empty.reduceLeft");
        }
        boolean first = true;
        Object acc = null;
        while (it.hasNext()) {
            Object x = it.next();
            if (first) {
                acc = x;
                first = false;
                continue;
            }
            acc = op.apply(acc, x);
        }
        return (B)acc;
    }

    public static /* synthetic */ Object reduceRight$(IterableOnceOps $this, Function2 op) {
        return $this.reduceRight(op);
    }

    default public <B> B reduceRight(Function2<A, B, B> op) {
        IndexedSeq indexedSeq;
        if (this instanceof IndexedSeq && (indexedSeq = (IndexedSeq)this).length() > 0) {
            Object r = indexedSeq.apply(indexedSeq.length() - 1);
            int foldr_loop$2_at = indexedSeq.length() - 1;
            while (true) {
                B foldr_loop$2_acc;
                if (foldr_loop$2_at == 0) {
                    return foldr_loop$2_acc;
                }
                foldr_loop$2_acc = op.apply(indexedSeq.apply(foldr_loop$2_at - 1), foldr_loop$2_acc);
                --foldr_loop$2_at;
            }
        }
        if (((IterableOnce)((Object)this)).iterator().isEmpty()) {
            throw new UnsupportedOperationException("empty.reduceRight");
        }
        return this.reversed().reduceLeft((Function2<Object, Object, Object> & Serializable)(x, y) -> op.apply(y, x));
    }

    public static /* synthetic */ Option reduceLeftOption$(IterableOnceOps $this, Function2 op) {
        return $this.reduceLeftOption(op);
    }

    default public <B> Option<B> reduceLeftOption(Function2<B, A, B> op) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<B>(this.reduceLeft(op));
    }

    public static /* synthetic */ Option reduceRightOption$(IterableOnceOps $this, Function2 op) {
        return $this.reduceRightOption(op);
    }

    default public <B> Option<B> reduceRightOption(Function2<A, B, B> op) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<B>(this.reduceRight(op));
    }

    public static /* synthetic */ boolean isEmpty$(IterableOnceOps $this) {
        return $this.isEmpty();
    }

    default public boolean isEmpty() {
        return !((IterableOnce)((Object)this)).iterator().hasNext();
    }

    public static /* synthetic */ boolean nonEmpty$(IterableOnceOps $this) {
        return $this.nonEmpty();
    }

    default public boolean nonEmpty() {
        return !this.isEmpty();
    }

    public static /* synthetic */ int size$(IterableOnceOps $this) {
        return $this.size();
    }

    default public int size() {
        if (((IterableOnce)((Object)this)).knownSize() >= 0) {
            return ((IterableOnce)((Object)this)).knownSize();
        }
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        int len = 0;
        while (it.hasNext()) {
            ++len;
            it.next();
        }
        return len;
    }

    public static /* synthetic */ void copyToBuffer$(IterableOnceOps $this, Buffer dest) {
        $this.copyToBuffer(dest);
    }

    /*
     * WARNING - void declaration
     */
    default public <B> void copyToBuffer(Buffer<B> dest) {
        void $plus$plus$eq_xs;
        IterableOnce iterableOnce = (IterableOnce)((Object)this);
        if (dest == null) {
            throw null;
        }
        dest.addAll((IterableOnce<B>)$plus$plus$eq_xs);
    }

    public static /* synthetic */ int copyToArray$(IterableOnceOps $this, Object xs) {
        return $this.copyToArray(xs);
    }

    default public <B> int copyToArray(Object xs) {
        return this.copyToArray(xs, 0, Integer.MAX_VALUE);
    }

    public static /* synthetic */ int copyToArray$(IterableOnceOps $this, Object xs, int start2) {
        return $this.copyToArray(xs, start2);
    }

    default public <B> int copyToArray(Object xs, int start2) {
        return this.copyToArray(xs, start2, Integer.MAX_VALUE);
    }

    public static /* synthetic */ int copyToArray$(IterableOnceOps $this, Object xs, int start2, int len) {
        return $this.copyToArray(xs, start2, len);
    }

    default public <B> int copyToArray(Object xs, int start2, int len) {
        int i;
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        int min_y = Array.getLength(xs) - start2;
        int end = start2 + Math.min(len, min_y);
        for (i = start2; i < end && it.hasNext(); ++i) {
            ScalaRunTime$.MODULE$.array_update(xs, i, it.next());
        }
        return i - start2;
    }

    public static /* synthetic */ Object sum$(IterableOnceOps $this, Numeric num) {
        return $this.sum(num);
    }

    default public <B> B sum(Numeric<B> num) {
        if (this.isEmpty()) {
            return num.zero();
        }
        return this.reduce((Function2<Object, Object, Object> & Serializable)(x, y) -> num.plus(x, y));
    }

    public static /* synthetic */ Object product$(IterableOnceOps $this, Numeric num) {
        return $this.product(num);
    }

    default public <B> B product(Numeric<B> num) {
        if (this.isEmpty()) {
            return num.one();
        }
        return this.reduce((Function2<Object, Object, Object> & Serializable)(x, y) -> num.times(x, y));
    }

    public static /* synthetic */ Object min$(IterableOnceOps $this, Ordering ord) {
        return $this.min(ord);
    }

    default public <B> A min(Ordering<B> ord) {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.min");
        }
        return (A)this.reduceLeft((Function2<Object, Object, Object> & Serializable)(x, y) -> ord.min(x, y));
    }

    public static /* synthetic */ Option minOption$(IterableOnceOps $this, Ordering ord) {
        return $this.minOption(ord);
    }

    default public <B> Option<A> minOption(Ordering<B> ord) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<A>(this.min(ord));
    }

    public static /* synthetic */ Object max$(IterableOnceOps $this, Ordering ord) {
        return $this.max(ord);
    }

    default public <B> A max(Ordering<B> ord) {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.max");
        }
        return (A)this.reduceLeft((Function2<Object, Object, Object> & Serializable)(x, y) -> ord.max(x, y));
    }

    public static /* synthetic */ Option maxOption$(IterableOnceOps $this, Ordering ord) {
        return $this.maxOption(ord);
    }

    default public <B> Option<A> maxOption(Ordering<B> ord) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<A>(this.max(ord));
    }

    public static /* synthetic */ Object maxBy$(IterableOnceOps $this, Function1 f, Ordering cmp) {
        return $this.maxBy(f, cmp);
    }

    default public <B> A maxBy(Function1<A, B> f, Ordering<B> cmp) {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.maxBy");
        }
        public class Scala_collection_IterableOnceOps$Maximizer$1
        extends AbstractFunction1<A, BoxedUnit> {
            private B maxF;
            private A maxElem;
            private boolean first;
            private final Function1 f$1;
            private final Ordering cmp$1;

            public B maxF() {
                return this.maxF;
            }

            public void maxF_$eq(B x$1) {
                this.maxF = x$1;
            }

            public A maxElem() {
                return this.maxElem;
            }

            public void maxElem_$eq(A x$1) {
                this.maxElem = x$1;
            }

            public boolean first() {
                return this.first;
            }

            public void first_$eq(boolean x$1) {
                this.first = x$1;
            }

            public void apply(A elem) {
                block4: {
                    R fx;
                    block3: {
                        block2: {
                            fx = this.f$1.apply(elem);
                            if (!this.first()) break block2;
                            this.first_$eq(false);
                            break block3;
                        }
                        if (!this.cmp$1.gt(fx, this.maxF())) break block4;
                    }
                    this.maxElem_$eq(elem);
                    this.maxF_$eq(fx);
                    return;
                }
            }

            public Scala_collection_IterableOnceOps$Maximizer$1(IterableOnceOps $outer, Function1 f$1, Ordering cmp$1) {
                this.f$1 = f$1;
                this.cmp$1 = cmp$1;
                this.maxF = null;
                this.maxElem = null;
                this.first = true;
            }
        }
        Scala_collection_IterableOnceOps$Maximizer$1 maximizer = new Scala_collection_IterableOnceOps$Maximizer$1(null, f, cmp);
        this.foreach(maximizer);
        return maximizer.maxElem();
    }

    public static /* synthetic */ Option maxByOption$(IterableOnceOps $this, Function1 f, Ordering cmp) {
        return $this.maxByOption(f, cmp);
    }

    default public <B> Option<A> maxByOption(Function1<A, B> f, Ordering<B> cmp) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<A>(this.maxBy(f, cmp));
    }

    public static /* synthetic */ Object minBy$(IterableOnceOps $this, Function1 f, Ordering cmp) {
        return $this.minBy(f, cmp);
    }

    default public <B> A minBy(Function1<A, B> f, Ordering<B> cmp) {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.minBy");
        }
        public class Scala_collection_IterableOnceOps$Minimizer$1
        extends AbstractFunction1<A, BoxedUnit> {
            private B minF;
            private A minElem;
            private boolean first;
            private final Function1 f$2;
            private final Ordering cmp$2;

            public B minF() {
                return this.minF;
            }

            public void minF_$eq(B x$1) {
                this.minF = x$1;
            }

            public A minElem() {
                return this.minElem;
            }

            public void minElem_$eq(A x$1) {
                this.minElem = x$1;
            }

            public boolean first() {
                return this.first;
            }

            public void first_$eq(boolean x$1) {
                this.first = x$1;
            }

            public void apply(A elem) {
                block4: {
                    R fx;
                    block3: {
                        block2: {
                            fx = this.f$2.apply(elem);
                            if (!this.first()) break block2;
                            this.first_$eq(false);
                            break block3;
                        }
                        if (!this.cmp$2.lt(fx, this.minF())) break block4;
                    }
                    this.minElem_$eq(elem);
                    this.minF_$eq(fx);
                    return;
                }
            }

            public Scala_collection_IterableOnceOps$Minimizer$1(IterableOnceOps $outer, Function1 f$2, Ordering cmp$2) {
                this.f$2 = f$2;
                this.cmp$2 = cmp$2;
                this.minF = null;
                this.minElem = null;
                this.first = true;
            }
        }
        Scala_collection_IterableOnceOps$Minimizer$1 minimizer = new Scala_collection_IterableOnceOps$Minimizer$1(null, f, cmp);
        this.foreach(minimizer);
        return minimizer.minElem();
    }

    public static /* synthetic */ Option minByOption$(IterableOnceOps $this, Function1 f, Ordering cmp) {
        return $this.minByOption(f, cmp);
    }

    default public <B> Option<A> minByOption(Function1<A, B> f, Ordering<B> cmp) {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<A>(this.minBy(f, cmp));
    }

    public static /* synthetic */ Option collectFirst$(IterableOnceOps $this, PartialFunction pf) {
        return $this.collectFirst(pf);
    }

    default public <B> Option<B> collectFirst(PartialFunction<A, B> pf) {
        AbstractFunction1 sentinel = new AbstractFunction1<A, Object>(null){

            public $anon$1 apply(A a) {
                return this;
            }
        };
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        while (it.hasNext()) {
            Object x = pf.applyOrElse(it.next(), sentinel);
            if (x == sentinel) continue;
            return new Some(x);
        }
        return None$.MODULE$;
    }

    public static /* synthetic */ Object aggregate$(IterableOnceOps $this, Function0 z, Function2 seqop, Function2 combop) {
        return $this.aggregate(z, seqop, combop);
    }

    default public <B> B aggregate(Function0<B> z, Function2<B, A, B> seqop, Function2<B, B, B> combop) {
        return this.foldLeft(z.apply(), seqop);
    }

    public static /* synthetic */ boolean corresponds$(IterableOnceOps $this, IterableOnce that, Function2 p) {
        return $this.corresponds(that, p);
    }

    default public <B> boolean corresponds(IterableOnce<B> that, Function2<A, B, Object> p) {
        Iterator a = ((IterableOnce)((Object)this)).iterator();
        Iterator<B> b = that.iterator();
        while (a.hasNext() && b.hasNext()) {
            if (BoxesRunTime.unboxToBoolean(p.apply(a.next(), b.next()))) continue;
            return false;
        }
        return a.hasNext() == b.hasNext();
    }

    public static /* synthetic */ String mkString$(IterableOnceOps $this, String start2, String sep, String end) {
        return $this.mkString(start2, sep, end);
    }

    default public String mkString(String start2, String sep, String end) {
        if (this.isEmpty()) {
            return new java.lang.StringBuilder(0).append(start2).append(end).toString();
        }
        return this.addString(new StringBuilder(), start2, sep, end).result();
    }

    public static /* synthetic */ String mkString$(IterableOnceOps $this, String sep) {
        return $this.mkString(sep);
    }

    default public String mkString(String sep) {
        return this.mkString("", sep, "");
    }

    public static /* synthetic */ String mkString$(IterableOnceOps $this) {
        return $this.mkString();
    }

    default public String mkString() {
        String mkString_sep = "";
        return this.mkString("", mkString_sep, "");
    }

    public static /* synthetic */ StringBuilder addString$(IterableOnceOps $this, StringBuilder b, String start2, String sep, String end) {
        return $this.addString(b, start2, sep, end);
    }

    default public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
        Iterator it;
        java.lang.StringBuilder jsb = b.underlying();
        if (start2.length() != 0) {
            jsb.append(start2);
        }
        if ((it = ((IterableOnce)((Object)this)).iterator()).hasNext()) {
            jsb.append(it.next());
            while (it.hasNext()) {
                jsb.append(sep);
                jsb.append(it.next());
            }
        }
        if (end.length() != 0) {
            jsb.append(end);
        }
        return b;
    }

    public static /* synthetic */ StringBuilder addString$(IterableOnceOps $this, StringBuilder b, String sep) {
        return $this.addString(b, sep);
    }

    default public StringBuilder addString(StringBuilder b, String sep) {
        return this.addString(b, "", sep, "");
    }

    public static /* synthetic */ StringBuilder addString$(IterableOnceOps $this, StringBuilder b) {
        return $this.addString(b);
    }

    default public StringBuilder addString(StringBuilder b) {
        String addString_sep = "";
        return this.addString(b, "", addString_sep, "");
    }

    public static /* synthetic */ Object to$(IterableOnceOps $this, Factory factory2) {
        return $this.to(factory2);
    }

    default public <C1> C1 to(Factory<A, C1> factory2) {
        return factory2.fromSpecific((IterableOnce)((Object)this));
    }

    public static /* synthetic */ Iterator toIterator$(IterableOnceOps $this) {
        return $this.toIterator();
    }

    default public Iterator<A> toIterator() {
        return ((IterableOnce)((Object)this)).iterator();
    }

    public static /* synthetic */ List toList$(IterableOnceOps $this) {
        return $this.toList();
    }

    default public List<A> toList() {
        IterableOnce from_coll = (IterableOnce)((Object)this);
        return Nil$.MODULE$.prependedAll(from_coll);
    }

    public static /* synthetic */ Vector toVector$(IterableOnceOps $this) {
        return $this.toVector();
    }

    default public Vector<A> toVector() {
        return Vector$.MODULE$.from((IterableOnce)((Object)this));
    }

    public static /* synthetic */ Map toMap$(IterableOnceOps $this, $less$colon$less ev) {
        return $this.toMap(ev);
    }

    default public <K$, V$> Map<K$, V$> toMap($less$colon$less<A, Tuple2<K$, V$>> ev) {
        return Map$.MODULE$.from((IterableOnce)((Object)this));
    }

    public static /* synthetic */ Set toSet$(IterableOnceOps $this) {
        return $this.toSet();
    }

    default public <B> Set<B> toSet() {
        return Set$.MODULE$.from((IterableOnce)((Object)this));
    }

    public static /* synthetic */ Seq toSeq$(IterableOnceOps $this) {
        return $this.toSeq();
    }

    default public Seq<A> toSeq() {
        return Seq$.MODULE$.from((IterableOnce)((Object)this));
    }

    public static /* synthetic */ scala.collection.immutable.IndexedSeq toIndexedSeq$(IterableOnceOps $this) {
        return $this.toIndexedSeq();
    }

    default public scala.collection.immutable.IndexedSeq<A> toIndexedSeq() {
        return IndexedSeq$.MODULE$.from((IterableOnce)((Object)this));
    }

    public static /* synthetic */ Stream toStream$(IterableOnceOps $this) {
        return $this.toStream();
    }

    default public Stream<A> toStream() {
        Stream$ toFactory_factory = Stream$.MODULE$;
        Object var1_1 = null;
        return this.to(new IterableFactory.ToFactory(toFactory_factory));
    }

    public static /* synthetic */ Buffer toBuffer$(IterableOnceOps $this) {
        return $this.toBuffer();
    }

    default public <B> Buffer<B> toBuffer() {
        return (Buffer)Buffer$.MODULE$.from((IterableOnce)((Object)this));
    }

    public static /* synthetic */ Object toArray$(IterableOnceOps $this, ClassTag evidence$2) {
        return $this.toArray(evidence$2);
    }

    default public <B> Object toArray(ClassTag<B> evidence$2) {
        ArrayBuilder arrayBuilder;
        if (((IterableOnce)((Object)this)).knownSize() >= 0) {
            Object destination = evidence$2.newArray(((IterableOnce)((Object)this)).knownSize());
            this.copyToArray(destination, 0);
            return destination;
        }
        Class<?> clazz = evidence$2.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef<B>(evidence$2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        return arrayBuilder.addAll((IterableOnce)((Object)this)).result();
    }

    public static /* synthetic */ Iterable reversed$(IterableOnceOps $this) {
        return $this.reversed();
    }

    default public Iterable<A> reversed() {
        List xs = Nil$.MODULE$;
        Iterator it = ((IterableOnce)((Object)this)).iterator();
        while (it.hasNext()) {
            Object a = it.next();
            xs = xs.$colon$colon(a);
        }
        return xs;
    }

    private Object loop$1(int at, int end, Object acc, Function2 op$1, IndexedSeq seq$1) {
        while (at != end) {
            acc = op$1.apply(acc, seq$1.apply(at));
            ++at;
        }
        return acc;
    }

    private Object loop$2(int at, Object acc, Function2 op$2, IndexedSeq seq$2) {
        while (at != 0) {
            acc = op$2.apply(seq$2.apply(at - 1), acc);
            --at;
        }
        return acc;
    }

    public static void $init$(IterableOnceOps $this) {
    }
}

