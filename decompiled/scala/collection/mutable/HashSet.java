/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.util.Arrays;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.convert.impl.AnyTableStepper;
import scala.collection.convert.impl.DoubleTableStepper;
import scala.collection.convert.impl.IntTableStepper;
import scala.collection.convert.impl.LongTableStepper;
import scala.collection.generic.DefaultSerializationProxy;
import scala.collection.immutable.BitmapIndexedSetNode;
import scala.collection.immutable.SetNode;
import scala.collection.mutable.AbstractSet;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.HashSet$;
import scala.collection.mutable.Shrinkable;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\r\u001de\u0001B'O\u0005UC\u0011b \u0001\u0003\u0002\u0003\u0006I!!\u0001\t\u0015\u0005\u001d\u0001A!A!\u0002\u0013\tI\u0001C\u0004\u0002\u0010\u0001!\t!!\u0005\t\u000f\u0005=\u0001\u0001\"\u0001\u0002\u0018!q\u0011\u0011\u0004\u0001\u0005\u0002\u0003\u0015\t\u0011!Q!\n\u0005m\u0001\u0002\u0003B%\u0001\u0001\u0006K!!\u0001\t\u0011\t-\u0003\u0001)Q\u0005\u0003\u0003AqA!\u0014\u0001\t\u0003\n)\t\u0003\u0005\u0003P\u0001!\t\u0001\u0015B)\u00119\u0011y\u0006\u0001C\u0001\u0002\u000b\u0005\t\u0011)C\u0005\u0005CBaBa\u001a\u0001\t\u0003\u0005)\u0011!A!\n\u0013\u0011I\u0007\u0003\b\u0003r\u0001!\t\u0011!B\u0001\u0002\u0003&IAa\u001d\t\u000f\te\u0004\u0001\"\u0011\u0003|!A\u0011Q\u001f\u0001!\n\u0013\u00119\tC\u0004\u0003\u000e\u0002!\tEa$\t\u000f\tM\u0005\u0001\"\u0011\u0003\u0016\"9!\u0011\u0014\u0001\u0005B\tm\u0005b\u0002BS\u0001\u0011\u0005#q\u0015\u0005\t\u0005W\u0003\u0001\u0015\"\u0003\u0003.\"A!1\u0017\u0001!\n\u0013\u0011)\fC\u0004\u00034\u0002!\tEa/\u0007\u0011\t}\u0006\u0001iA\u0005\u0005\u0003Dq!a\u0004\u0017\t\u0003\u0011y\r\u0003\u0005\u0003TZ\u0001\u000b\u0015BA\u0001\u0011!\u0011)N\u0006Q!\n\u0005\u0005\u0002\u0002\u0003Bl-\u0001\u0006I!!\u0001\t\u0011\teg\u0003)D\t\u00057DqA!9\u0017\t\u0003\u0011\u0019\u000fC\u0004\u0002dZ!\tA!:\t\u000f\t\u001d\b\u0001\"\u0011\u0003j\"A!\u0011\u001f\u0001\u0005\u0002A\u0013\u0019\u0010C\u0004\u0003x\u0002!\tE!?\t\u0011\rU\u0002\u0001)C\u0005\u0007oAqa!\u0010\u0001\t\u0003\u001ay\u0004\u0003\u0005\u0004H\u0001\u0001K\u0011BB%\u0011!\u0019y\u0005\u0001Q\u0005\n\rE\u0003bBB+\u0001\u0011\u00051q\u000b\u0005\b\u00073\u0002A\u0011IB.\u0011\u001d\u0019i\u0006\u0001C\u0001\u0007?Bqa!\u001a\u0001\t\u0003\u00199\u0007C\u0004\u0004n\u0001!\t%!\"\t\u000f\r=\u0004\u0001\"\u0011\u0003d\"9!q\u0002\u0001\u0005B\rE\u0004\u0002\u0003B\u001e\u0001\u0001&\tb! \t\u0011\r}\u0004\u0001)C)\u0007\u0003Cqaa!\u0001\t\u0003\u001a)iB\u0004\u0002&9C\t!a\n\u0007\r5s\u0005\u0012AA\u0015\u0011\u001d\ty\u0001\rC\u0001\u0003oAq!!\u000f1\t\u0003\tY\u0004C\u0004\u0002RA\"\t!a\u0015\t\u000f\u0005u\u0003\u0007\"\u0001\u0002`!9\u0011Q\f\u0019\u0005\u0002\u0005=\u0004bBA@a\u0011\u0015\u0011\u0011\u0011\u0005\b\u0003\u0007\u0003DQAAC\r\u0019\t9\t\r\u0004\u0002\n\"Q\u0011\u0011\u0014\u001d\u0003\u0006\u0004%\t!!\"\t\u0015\u0005m\u0005H!A!\u0002\u0013\t\t\u0001\u0003\u0006\u0002\ba\u0012)\u0019!C\u0001\u0003\u0003C!\"!(9\u0005\u0003\u0005\u000b\u0011BA\u0005\u0011\u001d\ty\u0001\u000fC\u0001\u0003?Cq!!+9\t\u0003\tY\u000bC\u0004\u0002^a\"\t!!-\u0007\u000f\u0005\u0005\u0007G\u0001)\u0002D\"Q\u0011q\u0019!\u0003\u0002\u0003\u0006I!!3\t\u0015\u0005=\u0007I!A!\u0002\u0013\t\t\u0001\u0003\u0006\u0002R\u0002\u0013\t\u0011)Q\u0005\u0003'Dq!a\u0004A\t\u0003\t)\u000eC\u0004\u0002^\u0002#\t!a8\t\u000f\u0005\u0005\b\t\"\u0001\u0002\u0006\"9\u00111\u001d!\u0005\u0002\u0005\u0015\bbBAt\u0001\u0012\u0005\u0011\u0011\u001e\u0005\b\u0003k\u0004E\u0011AA|\u0011\u001d\u0011y\u0001\u0011C\u0001\u0005#AqAa\nA\t\u0003\u0012I\u0003C\u0005\u0003<A\n\t\u0011\"\u0003\u0003>\t9\u0001*Y:i'\u0016$(BA(Q\u0003\u001diW\u000f^1cY\u0016T!!\u0015*\u0002\u0015\r|G\u000e\\3di&|gNC\u0001T\u0003\u0015\u00198-\u00197b\u0007\u0001)\"AV/\u0014\r\u00019v\r\u001c9t!\rA\u0016lW\u0007\u0002\u001d&\u0011!L\u0014\u0002\f\u0003\n\u001cHO]1diN+G\u000f\u0005\u0002];2\u0001A!\u00020\u0001\u0005\u0004y&!A!\u0012\u0005\u0001$\u0007CA1c\u001b\u0005\u0011\u0016BA2S\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!Y3\n\u0005\u0019\u0014&aA!osB)\u0001\f[.kW&\u0011\u0011N\u0014\u0002\u0007'\u0016$x\n]:\u0011\u0005a\u0003\u0001c\u0001-\u00017B)QN\\.kW6\t\u0001+\u0003\u0002p!\nQ2\u000b\u001e:jGR|\u0005\u000f^5nSj,G-\u0013;fe\u0006\u0014G.Z(qgB!Q.].k\u0013\t\u0011\bKA\fJi\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ugB\u0011A\u000f \b\u0003kjt!A^=\u000e\u0003]T!\u0001\u001f+\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0019\u0016BA>S\u0003\u001d\u0001\u0018mY6bO\u0016L!! @\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005m\u0014\u0016aD5oSRL\u0017\r\\\"ba\u0006\u001c\u0017\u000e^=\u0011\u0007\u0005\f\u0019!C\u0002\u0002\u0006I\u00131!\u00138u\u0003)aw.\u00193GC\u000e$xN\u001d\t\u0004C\u0006-\u0011bAA\u0007%\n1Ai\\;cY\u0016\fa\u0001P5oSRtD#B6\u0002\u0014\u0005U\u0001BB@\u0004\u0001\u0004\t\t\u0001C\u0004\u0002\b\r\u0001\r!!\u0003\u0015\u0003-\fqe]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI!\u000b7\u000f[*fi\u0012\"C/\u00192mKB)\u0011-!\b\u0002\"%\u0019\u0011q\u0004*\u0003\u000b\u0005\u0013(/Y=\u0011\t\u0005\r\u0002i\u0017\b\u00031>\nq\u0001S1tQN+G\u000f\u0005\u0002YaM)\u0001'a\u000b\u00022A\u0019\u0011-!\f\n\u0007\u0005=\"K\u0001\u0004B]f\u0014VM\u001a\t\u0005[\u0006M\".C\u0002\u00026A\u0013q\"\u0013;fe\u0006\u0014G.\u001a$bGR|'/\u001f\u000b\u0003\u0003O\tAA\u001a:p[V!\u0011QHA\")\u0011\ty$a\u0012\u0011\ta\u0003\u0011\u0011\t\t\u00049\u0006\rCABA#e\t\u0007qLA\u0001C\u0011\u001d\tIE\ra\u0001\u0003\u0017\n!!\u001b;\u0011\u000b5\fi%!\u0011\n\u0007\u0005=\u0003K\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW-A\u0003f[B$\u00180\u0006\u0003\u0002V\u0005mSCAA,!\u0011A\u0006!!\u0017\u0011\u0007q\u000bY\u0006B\u0003_g\t\u0007q,\u0001\u0006oK^\u0014U/\u001b7eKJ,B!!\u0019\u0002lU\u0011\u00111\r\t\b1\u0006\u0015\u0014\u0011NA7\u0013\r\t9G\u0014\u0002\b\u0005VLG\u000eZ3s!\ra\u00161\u000e\u0003\u0006=R\u0012\ra\u0018\t\u00051\u0002\tI'\u0006\u0003\u0002r\u0005]DCBA:\u0003w\ni\bE\u0004Y\u0003K\n)(!\u001f\u0011\u0007q\u000b9\bB\u0003_k\t\u0007q\f\u0005\u0003Y\u0001\u0005U\u0004BB@6\u0001\u0004\t\t\u0001C\u0004\u0002\bU\u0002\r!!\u0003\u0002#\u0011,g-Y;mi2{\u0017\r\u001a$bGR|'/\u0006\u0002\u0002\n\u00051B-\u001a4bk2$\u0018J\\5uS\u0006d7)\u00199bG&$\u00180\u0006\u0002\u0002\u0002\t1B)Z:fe&\fG.\u001b>bi&|gNR1di>\u0014\u00180\u0006\u0003\u0002\f\u0006U5C\u0002\u001d\u0002,\u000555\u000fE\u0004n\u0003\u001f\u000b\u0019*a&\n\u0007\u0005E\u0005KA\u0004GC\u000e$xN]=\u0011\u0007q\u000b)\nB\u0003_q\t\u0007q\f\u0005\u0003Y\u0001\u0005M\u0015a\u0003;bE2,G*\u001a8hi\"\fA\u0002^1cY\u0016dUM\\4uQ\u0002\n1\u0002\\8bI\u001a\u000b7\r^8sAQ1\u0011\u0011UAS\u0003O\u0003R!a)9\u0003'k\u0011\u0001\r\u0005\b\u00033k\u0004\u0019AA\u0001\u0011\u001d\t9!\u0010a\u0001\u0003\u0013\tAB\u001a:p[N\u0003XmY5gS\u000e$B!a&\u0002.\"9\u0011\u0011\n A\u0002\u0005=\u0006#B7\u0002N\u0005MUCAAZ!\u001dA\u0016QMAJ\u0003/Cs\u0001OA\\\u0003{\u000by\fE\u0002b\u0003sK1!a/S\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004\u0005\u0011qu\u000eZ3\u0016\t\u0005\u0015\u00171Z\n\u0004\u0001\u0006-\u0012\u0001B0lKf\u00042\u0001XAf\t\u0019\ti\r\u0011b\u0001?\n\t1*A\u0003`Q\u0006\u001c\b.A\u0003`]\u0016DH\u000fE\u0003\u0002$\u0002\u000bI\r\u0006\u0005\u0002T\u0006]\u0017\u0011\\An\u0011\u001d\t9\r\u0012a\u0001\u0003\u0013Dq!a4E\u0001\u0004\t\t\u0001C\u0004\u0002R\u0012\u0003\r!a5\u0002\u0007-,\u00170\u0006\u0002\u0002J\u0006!\u0001.Y:i\u0003\u0011qW\r\u001f;\u0016\u0005\u0005M\u0017\u0001\u00038fqR|F%Z9\u0015\t\u0005-\u0018\u0011\u001f\t\u0004C\u00065\u0018bAAx%\n!QK\\5u\u0011\u001d\t\u0019\u0010\u0013a\u0001\u0003'\f\u0011A\\\u0001\tM&tGMT8eKR1\u00111[A}\u0003{Dq!a?J\u0001\u0004\tI-A\u0001l\u0011\u001d\ty0\u0013a\u0001\u0003\u0003\t\u0011\u0001\u001b\u0015\u0004\u0013\n\r\u0001\u0003\u0002B\u0003\u0005\u0017i!Aa\u0002\u000b\u0007\t%!+\u0001\u0006b]:|G/\u0019;j_:LAA!\u0004\u0003\b\t9A/Y5me\u0016\u001c\u0017a\u00024pe\u0016\f7\r[\u000b\u0005\u0005'\u0011\t\u0003\u0006\u0003\u0002l\nU\u0001b\u0002B\f\u0015\u0002\u0007!\u0011D\u0001\u0002MB9\u0011Ma\u0007\u0002J\n}\u0011b\u0001B\u000f%\nIa)\u001e8di&|g.\r\t\u00049\n\u0005BA\u0002B\u0012\u0015\n\u0007qLA\u0001VQ\rQ%1A\u0001\ti>\u001cFO]5oOR\u0011!1\u0006\t\u0005\u0005[\u00119$\u0004\u0002\u00030)!!\u0011\u0007B\u001a\u0003\u0011a\u0017M\\4\u000b\u0005\tU\u0012\u0001\u00026bm\u0006LAA!\u000f\u00030\t11\u000b\u001e:j]\u001e\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa\u0010\u0011\t\t5\"\u0011I\u0005\u0005\u0005\u0007\u0012yC\u0001\u0004PE*,7\r\u001e\u0015\ba\u0005]\u0016QXA`Q\u001dy\u0013qWA_\u0003\u007f\u000b\u0011\u0002\u001e5sKNDw\u000e\u001c3\u0002\u0017\r|g\u000e^3oiNK'0Z\u0001\u0005g&TX-A\u0007v]&l\u0007O]8wK\"\u000b7\u000f\u001b\u000b\u0005\u0003\u0003\u0011\u0019\u0006C\u0004\u0003V%\u0001\r!!\u0001\u0002\u0019%l\u0007O]8wK\u0012D\u0015m\u001d5)\u0007%\u0011I\u0006E\u0002b\u00057J1A!\u0018S\u0005\u0019Ig\u000e\\5oK\u0006i3oY1mC\u0012\u001aw\u000e\u001c7fGRLwN\u001c\u0013nkR\f'\r\\3%\u0011\u0006\u001c\bnU3uI\u0011JW\u000e\u001d:pm\u0016D\u0015m\u001d5\u0015\t\u0005\u0005!1\r\u0005\b\u0005KR\u0001\u0019AA\u0001\u00031y'/[4j]\u0006d\u0007*Y:i\u00035\u001a8-\u00197bI\r|G\u000e\\3di&|g\u000eJ7vi\u0006\u0014G.\u001a\u0013ICND7+\u001a;%I\r|W\u000e];uK\"\u000b7\u000f\u001b\u000b\u0005\u0003\u0003\u0011Y\u0007\u0003\u0004\u0003n-\u0001\raW\u0001\u0002_\"\u001a1B!\u0017\u0002OM\u001c\u0017\r\\1%G>dG.Z2uS>tG%\\;uC\ndW\r\n%bg\"\u001cV\r\u001e\u0013%S:$W\r\u001f\u000b\u0005\u0003\u0003\u0011)\bC\u0004\u0002b2\u0001\r!!\u0001)\u00071\u0011I&\u0001\u0005d_:$\u0018-\u001b8t)\u0011\u0011iHa!\u0011\u0007\u0005\u0014y(C\u0002\u0003\u0002J\u0013qAQ8pY\u0016\fg\u000e\u0003\u0004\u0003\u00066\u0001\raW\u0001\u0005K2,W\u000e\u0006\u0003\u0002\"\t%\u0005B\u0002BC\u001d\u0001\u00071\fK\u0002\u000f\u00053\n\u0001b]5{K\"Kg\u000e\u001e\u000b\u0005\u0003W\u0014\t\nC\u0004\u0003N=\u0001\r!!\u0001\u0002\u0007\u0005$G\r\u0006\u0003\u0003~\t]\u0005B\u0002BC!\u0001\u00071,\u0001\u0004bI\u0012\fE\u000e\u001c\u000b\u0005\u0005;\u0013y*D\u0001\u0001\u0011\u001d\u0011\t+\u0005a\u0001\u0005G\u000b!\u0001_:\u0011\t5\fieW\u0001\fgV\u0014GO]1di\u0006cG\u000e\u0006\u0003\u0003\u001e\n%\u0006b\u0002BQ%\u0001\u0007!1U\u0001\bC\u0012$W\t\\3n)\u0019\u0011iHa,\u00032\"1!QQ\nA\u0002mCq!!9\u0014\u0001\u0004\t\t!\u0001\u0004sK6|g/\u001a\u000b\u0007\u0005{\u00129L!/\t\r\t\u0015E\u00031\u0001\\\u0011\u001d\t\t\u000f\u0006a\u0001\u0003\u0003!BA! \u0003>\"1!QQ\u000bA\u0002m\u0013q\u0002S1tQN+G/\u0013;fe\u0006$xN]\u000b\u0005\u0005\u0007\u0014imE\u0002\u0017\u0005\u000b\u0004R!\u001cBd\u0005\u0017L1A!3Q\u0005A\t%m\u001d;sC\u000e$\u0018\n^3sCR|'\u000fE\u0002]\u0005\u001b$a!!\u0012\u0017\u0005\u0004yFC\u0001Bi!\u0015\u0011iJ\u0006Bf\u0003\u0005I\u0017\u0001\u00028pI\u0016\f1\u0001\\3o\u0003\u001d)\u0007\u0010\u001e:bGR$BAa3\u0003^\"9!q\\\u000eA\u0002\u0005\u0005\u0012A\u00018e\u0003\u001dA\u0017m\u001d(fqR,\"A! \u0015\u0005\t-\u0017\u0001C5uKJ\fGo\u001c:\u0016\u0005\t-\b\u0003B7\u0003nnK1Aa<Q\u0005!IE/\u001a:bi>\u0014\u0018\u0001\u00048pI\u0016LE/\u001a:bi>\u0014XC\u0001B{!\u0015i'Q^A\u0011\u0003\u001d\u0019H/\u001a9qKJ,BAa?\u0004\u0006Q!!Q`B\u0016%\u0019\u0011ypa\u0001\u0004\u001a\u001911\u0011\u0001\u0001\u0001\u0005{\u0014A\u0002\u0010:fM&tW-\\3oiz\u00022\u0001XB\u0003\t\u001d\u00199\u0001\tb\u0001\u0007\u0013\u0011\u0011aU\t\u0004A\u000e-\u0001\u0007BB\u0007\u0007+\u0001R!\\B\b\u0007'I1a!\u0005Q\u0005\u001d\u0019F/\u001a9qKJ\u00042\u0001XB\u000b\t-\u00199b!\u0002\u0002\u0002\u0003\u0005)\u0011A0\u0003\u0007}#\u0013\u0007\u0005\u0003\u0004\u001c\r\u0015b\u0002BB\u000f\u0007Cq1!^B\u0010\u0013\t\t&+C\u0002\u0004$A\u000bqa\u0015;faB,'/\u0003\u0003\u0004(\r%\"AD#gM&\u001c\u0017.\u001a8u'Bd\u0017\u000e\u001e\u0006\u0004\u0007G\u0001\u0006bBB\u0017A\u0001\u000f1qF\u0001\u0006g\"\f\u0007/\u001a\t\u0007[\u000eE2la\u0001\n\u0007\rM\u0002K\u0001\u0007Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X-A\u0005he><H+\u00192mKR!\u00111^B\u001d\u0011\u001d\u0019Y$\ta\u0001\u0003\u0003\taA\\3xY\u0016t\u0017!\u00044jYR,'/\u00138QY\u0006\u001cW\r\u0006\u0003\u0003\u001e\u000e\u0005\u0003bBB\"E\u0001\u00071QI\u0001\u0002aB1\u0011Ma\u0007\\\u0005{\nA\u0002^1cY\u0016\u001c\u0016N_3G_J$B!!\u0001\u0004L!91QJ\u0012A\u0002\u0005\u0005\u0011\u0001C2ba\u0006\u001c\u0017\u000e^=\u0002\u00199,w\u000f\u00165sKNDw\u000e\u001c3\u0015\t\u0005\u000511\u000b\u0005\b\u0005\u001b\"\u0003\u0019AA\u0001\u0003\u0015\u0019G.Z1s)\t\tY/A\bji\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z+\t\t\t$\u0001\u0004bI\u0012|e.\u001a\u000b\u0005\u0005;\u001b\t\u0007\u0003\u0004\u0003\u0006\u001e\u0002\ra\u0017\u0015\u0004O\te\u0013aC:vER\u0014\u0018m\u0019;P]\u0016$BA!(\u0004j!1!Q\u0011\u0015A\u0002mC3\u0001\u000bB-\u0003%Ygn\\<o'&TX-A\u0004jg\u0016k\u0007\u000f^=\u0016\t\rM41\u0010\u000b\u0005\u0003W\u001c)\bC\u0004\u0003\u0018-\u0002\raa\u001e\u0011\r\u0005\u0014YbWB=!\ra61\u0010\u0003\u0007\u0005GY#\u0019A0\u0015\u0005\u0005-\u0012!C2mCN\u001ch*Y7f+\t\u0011Y#\u0001\u0005iCND7i\u001c3f)\t\t\t\u0001")
public final class HashSet<A>
extends AbstractSet<A>
implements StrictOptimizedIterableOps<A, HashSet, HashSet<A>>,
Serializable {
    private final double loadFactor;
    public Node<A>[] scala$collection$mutable$HashSet$$table;
    private int threshold;
    private int contentSize;

    public static int defaultInitialCapacity() {
        return 16;
    }

    public static double defaultLoadFactor() {
        return 0.75;
    }

    public static <A> Builder<A, HashSet<A>> newBuilder(int initialCapacity, double loadFactor) {
        return new GrowableBuilder<A, HashSet<A>>(initialCapacity, loadFactor){

            public void sizeHint(int size) {
                ((HashSet)this.elems()).sizeHint(size);
            }
        };
    }

    public static <A> Builder<A, HashSet<A>> newBuilder() {
        return HashSet$.MODULE$.newBuilder();
    }

    public static <B> HashSet<B> from(IterableOnce<B> it) {
        return HashSet$.MODULE$.from((IterableOnce)it);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n, Function1 f) {
        HashSet$ tabulate_this = HashSet$.MODULE$;
        View.Tabulate from_source = new View.Tabulate(n, f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n, Function0 elem) {
        HashSet$ fill_this = HashSet$.MODULE$;
        View.Fill from_source = new View.Fill(n, elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(HashSet$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(HashSet$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        HashSet$ unfold_this = HashSet$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        HashSet$ iterate_this = HashSet$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Tuple2<HashSet<A>, HashSet<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<HashSet<A>, HashSet<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<HashSet<A1>, HashSet<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<HashSet<A1>, HashSet<A2>, HashSet<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
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
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
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
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<HashSet<A1>, HashSet<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public int size() {
        return this.contentSize;
    }

    public int unimproveHash(int improvedHash) {
        return improvedHash ^ improvedHash >>> 16;
    }

    public int scala$collection$mutable$HashSet$$improveHash(int originalHash) {
        return originalHash ^ originalHash >>> 16;
    }

    public int scala$collection$mutable$HashSet$$computeHash(A o) {
        int scala$collection$mutable$HashSet$$improveHash_originalHash = Statics.anyHash(o);
        return scala$collection$mutable$HashSet$$improveHash_originalHash ^ scala$collection$mutable$HashSet$$improveHash_originalHash >>> 16;
    }

    public int scala$collection$mutable$HashSet$$index(int hash) {
        return hash & this.scala$collection$mutable$HashSet$$table.length - 1;
    }

    @Override
    public boolean contains(A elem) {
        int findNode_scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash = Statics.anyHash(elem);
        int findNode_hash = findNode_scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash ^ findNode_scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash >>> 16;
        Node<A> node = this.scala$collection$mutable$HashSet$$table[findNode_hash & this.scala$collection$mutable$HashSet$$table.length - 1];
        Node<A> node2 = node == null ? null : node.findNode(elem, findNode_hash);
        node = null;
        return node2 != null;
    }

    private Node<A> findNode(A elem) {
        int scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash = Statics.anyHash(elem);
        int hash = scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash ^ scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash >>> 16;
        Node<A> node = this.scala$collection$mutable$HashSet$$table[hash & this.scala$collection$mutable$HashSet$$table.length - 1];
        if (node == null) {
            return null;
        }
        return node.findNode(elem, hash);
    }

    @Override
    public void sizeHint(int size) {
        int target = this.tableSizeFor((int)((double)(size + 1) / this.loadFactor));
        if (target > this.scala$collection$mutable$HashSet$$table.length) {
            this.growTable(target);
            return;
        }
    }

    @Override
    public boolean add(A elem) {
        if (this.contentSize + 1 >= this.threshold) {
            this.growTable(this.scala$collection$mutable$HashSet$$table.length * 2);
        }
        int scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash = Statics.anyHash(elem);
        return this.addElem(elem, scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash ^ scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash >>> 16);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public HashSet<A> addAll(IterableOnce<A> xs) {
        this.sizeHint(xs.knownSize());
        if (xs instanceof scala.collection.immutable.HashSet) {
            scala.collection.immutable.HashSet hashSet = (scala.collection.immutable.HashSet)xs;
            Function2<Object, Object, Object> & Serializable foreachWithHash_f = (Function2<Object, Object, Object> & Serializable)(k, h2) -> {
                this.addElem(k, BoxesRunTime.unboxToInt(h2) ^ BoxesRunTime.unboxToInt(h2) >>> 16);
                return BoxedUnit.UNIT;
            };
            BitmapIndexedSetNode bitmapIndexedSetNode = hashSet.rootNode();
            if (bitmapIndexedSetNode == null) {
                throw null;
            }
            BitmapIndexedSetNode foreachWithHash_foreachWithHash_this = bitmapIndexedSetNode;
            int foreachWithHash_foreachWithHash_iN = foreachWithHash_foreachWithHash_this.payloadArity();
            for (int foreachWithHash_foreachWithHash_i = 0; foreachWithHash_foreachWithHash_i < foreachWithHash_foreachWithHash_iN; ++foreachWithHash_foreachWithHash_i) {
                void var12_7;
                int foreachWithHash_foreachWithHash_boxToInteger_i = foreachWithHash_foreachWithHash_this.originalHashes()[foreachWithHash_foreachWithHash_i];
                Object object = foreachWithHash_foreachWithHash_this.content()[foreachWithHash_foreachWithHash_i];
                this.addElem(object, (int)var12_7 ^ (int)var12_7 >>> 16);
            }
            int foreachWithHash_foreachWithHash_jN = foreachWithHash_foreachWithHash_this.nodeArity();
            for (int foreachWithHash_foreachWithHash_j = 0; foreachWithHash_foreachWithHash_j < foreachWithHash_foreachWithHash_jN; ++foreachWithHash_foreachWithHash_j) {
                ((SetNode)foreachWithHash_foreachWithHash_this.getNode(foreachWithHash_foreachWithHash_j)).foreachWithHash(foreachWithHash_f);
            }
            return this;
        }
        if (xs instanceof HashSet) {
            HashSet hashSet = (HashSet)xs;
            HashSetIterator iter = new HashSetIterator<Node<A>>(hashSet){

                public Node<A> extract(Node<A> nd) {
                    return nd;
                }
            };
            while (iter.hasNext()) {
                Node next = (Node)iter.next();
                this.addElem(next.key(), next.hash());
            }
            return this;
        }
        return (HashSet)Growable.addAll$(this, xs);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public HashSet<A> subtractAll(IterableOnce<A> xs) {
        block10: {
            if (this.size() == 0) {
                return this;
            }
            if (!(xs instanceof scala.collection.immutable.HashSet)) break block10;
            var2_2 = (scala.collection.immutable.HashSet)xs;
            var6_3 = (Function2<Object, Object, Object> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$subtractAll$1$adapted(scala.collection.mutable.HashSet java.lang.Object java.lang.Object ), (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;)((HashSet)this);
            if (var2_2 == null) {
                throw null;
            }
            v0 = var2_2.rootNode();
            if (v0 == null) {
                throw null;
            }
            foreachWithHashWhile_foreachWithHashWhile_this = v0;
            foreachWithHashWhile_foreachWithHashWhile_thisPayloadArity = foreachWithHashWhile_foreachWithHashWhile_this.payloadArity();
            foreachWithHashWhile_foreachWithHashWhile_pass = true;
            for (foreachWithHashWhile_foreachWithHashWhile_i = 0; foreachWithHashWhile_foreachWithHashWhile_i < foreachWithHashWhile_foreachWithHashWhile_thisPayloadArity && foreachWithHashWhile_foreachWithHashWhile_pass; ++foreachWithHashWhile_foreachWithHashWhile_i) {
                if (!foreachWithHashWhile_foreachWithHashWhile_pass) ** GOTO lbl-1000
                foreachWithHashWhile_foreachWithHashWhile_boxToInteger_i = foreachWithHashWhile_foreachWithHashWhile_this.originalHashes()[foreachWithHashWhile_foreachWithHashWhile_i];
                var14_9 = foreachWithHashWhile_foreachWithHashWhile_this.content()[foreachWithHashWhile_foreachWithHashWhile_i];
                if (HashSet.$anonfun$subtractAll$1(this, var14_9, (int)var13_8)) {
                    v1 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v1 = false;
                }
                foreachWithHashWhile_foreachWithHashWhile_pass = v1;
            }
            foreachWithHashWhile_foreachWithHashWhile_thisNodeArity = foreachWithHashWhile_foreachWithHashWhile_this.nodeArity();
            for (foreachWithHashWhile_foreachWithHashWhile_j = 0; foreachWithHashWhile_foreachWithHashWhile_j < foreachWithHashWhile_foreachWithHashWhile_thisNodeArity && foreachWithHashWhile_foreachWithHashWhile_pass; ++foreachWithHashWhile_foreachWithHashWhile_j) {
                foreachWithHashWhile_foreachWithHashWhile_pass = foreachWithHashWhile_foreachWithHashWhile_pass != false && foreachWithHashWhile_foreachWithHashWhile_this.getNode(foreachWithHashWhile_foreachWithHashWhile_j).foreachWithHashWhile(foreachWithHashWhile_f) != false;
            }
            return this;
        }
        if (xs instanceof HashSet) {
            var3_12 = (HashSet)xs;
            if (var3_12 == null) {
                throw null;
            }
            iter = new /* invalid duplicate definition of identical inner class */;
            while (iter.hasNext()) {
                next = (Node)iter.next();
                this.remove(next.key(), next.hash());
                if (this.size() != 0) continue;
                return this;
            }
            return this;
        }
        return (HashSet)Shrinkable.subtractAll$(this, xs);
    }

    private boolean addElem(A elem, int hash) {
        int idx = hash & this.scala$collection$mutable$HashSet$$table.length - 1;
        Node<A> node = this.scala$collection$mutable$HashSet$$table[idx];
        if (node == null) {
            this.scala$collection$mutable$HashSet$$table[idx] = new Node<A>(elem, hash, null);
        } else {
            Node<A> prev = null;
            for (Node<A> n = node; n != null && n.hash() <= hash; n = n.next()) {
                if (n.hash() == hash && BoxesRunTime.equals(elem, n.key())) {
                    return false;
                }
                prev = n;
            }
            if (prev == null) {
                this.scala$collection$mutable$HashSet$$table[idx] = new Node<A>(elem, hash, node);
            } else {
                prev.next_$eq(new Node<A>(elem, hash, prev.next()));
            }
        }
        ++this.contentSize;
        return true;
    }

    private boolean remove(A elem, int hash) {
        int idx = hash & this.scala$collection$mutable$HashSet$$table.length - 1;
        Node<A> node = this.scala$collection$mutable$HashSet$$table[idx];
        if (node == null) {
            return false;
        }
        if (node.hash() == hash && BoxesRunTime.equals(node.key(), elem)) {
            this.scala$collection$mutable$HashSet$$table[idx] = node.next();
            --this.contentSize;
            return true;
        }
        Node<A> prev = node;
        for (Node<A> next = node.next(); next != null && next.hash() <= hash; next = next.next()) {
            if (next.hash() == hash && BoxesRunTime.equals(next.key(), elem)) {
                prev.next_$eq(next.next());
                --this.contentSize;
                return true;
            }
            prev = next;
        }
        return false;
    }

    @Override
    public boolean remove(A elem) {
        int scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash = Statics.anyHash(elem);
        return this.remove(elem, scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash ^ scala$collection$mutable$HashSet$$computeHash_scala$collection$mutable$HashSet$$improveHash_originalHash >>> 16);
    }

    @Override
    public Iterator<A> iterator() {
        return new HashSetIterator<A>(this){

            public A extract(Node<A> nd) {
                return nd.key();
            }
        };
    }

    public Iterator<Node<A>> nodeIterator() {
        return new /* invalid duplicate definition of identical inner class */;
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            return (S)new IntTableStepper<Node<A>>(this.size(), this.scala$collection$mutable$HashSet$$table, (Function1<Node, Node> & Serializable)x$1 -> x$1.next(), (Function1<Node, Object> & Serializable)x$2 -> BoxesRunTime.boxToInteger(HashSet.$anonfun$stepper$2(x$2)), 0, this.scala$collection$mutable$HashSet$$table.length);
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            return (S)new LongTableStepper<Node<A>>(this.size(), this.scala$collection$mutable$HashSet$$table, (Function1<Node, Node> & Serializable)x$3 -> x$3.next(), (Function1<Node, Object> & Serializable)x$4 -> BoxesRunTime.boxToLong(HashSet.$anonfun$stepper$4(x$4)), 0, this.scala$collection$mutable$HashSet$$table.length);
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            return (S)new DoubleTableStepper<Node<A>>(this.size(), this.scala$collection$mutable$HashSet$$table, (Function1<Node, Node> & Serializable)x$5 -> x$5.next(), (Function1<Node, Object> & Serializable)x$6 -> BoxesRunTime.boxToDouble(HashSet.$anonfun$stepper$6(x$6)), 0, this.scala$collection$mutable$HashSet$$table.length);
        }
        return shape.parUnbox(new AnyTableStepper(this.size(), this.scala$collection$mutable$HashSet$$table, (Function1<Node, Node> & Serializable)x$7 -> x$7.next(), (Function1<Node, Object> & Serializable)x$8 -> x$8.key(), 0, this.scala$collection$mutable$HashSet$$table.length));
    }

    private void growTable(int newlen) {
        this.threshold = this.newThreshold(newlen);
        if (this.size() == 0) {
            this.scala$collection$mutable$HashSet$$table = new Node[newlen];
            return;
        }
        this.scala$collection$mutable$HashSet$$table = Arrays.copyOf(this.scala$collection$mutable$HashSet$$table, newlen);
        Node<Object> preLow = new Node<Object>(null, 0, null);
        Node<Object> preHigh = new Node<Object>(null, 0, null);
        for (int oldlen = this.scala$collection$mutable$HashSet$$table.length; oldlen < newlen; oldlen *= 2) {
            for (int i = 0; i < oldlen; ++i) {
                Node<A> old = this.scala$collection$mutable$HashSet$$table[i];
                if (old == null) continue;
                preLow.next_$eq(null);
                preHigh.next_$eq(null);
                Node<Object> lastLow = preLow;
                Node<Object> lastHigh = preHigh;
                Node<A> n = old;
                while (n != null) {
                    Node<A> next = n.next();
                    if ((n.hash() & oldlen) == 0) {
                        lastLow.next_$eq(n);
                        lastLow = n;
                    } else {
                        lastHigh.next_$eq(n);
                        lastHigh = n;
                    }
                    n = next;
                }
                lastLow.next_$eq(null);
                if (old != preLow.next()) {
                    this.scala$collection$mutable$HashSet$$table[i] = preLow.next();
                }
                if (preHigh.next() == null) continue;
                this.scala$collection$mutable$HashSet$$table[i + oldlen] = preHigh.next();
                lastHigh.next_$eq(null);
            }
        }
    }

    public HashSet<A> filterInPlace(Function1<A, Object> p) {
        if (IterableOnceOps.nonEmpty$(this)) {
            for (int bucket = 0; bucket < this.scala$collection$mutable$HashSet$$table.length; ++bucket) {
                Node<A> head = this.scala$collection$mutable$HashSet$$table[bucket];
                while (head != null && !BoxesRunTime.unboxToBoolean(p.apply(head.key()))) {
                    head = head.next();
                    --this.contentSize;
                }
                if (head != null) {
                    Node<A> prev = head;
                    for (Node<A> next = head.next(); next != null; next = next.next()) {
                        if (BoxesRunTime.unboxToBoolean(p.apply(next.key()))) {
                            prev = next;
                            continue;
                        }
                        prev.next_$eq(next.next());
                        --this.contentSize;
                    }
                }
                this.scala$collection$mutable$HashSet$$table[bucket] = head;
            }
        }
        return this;
    }

    private int tableSizeFor(int capacity) {
        int n = capacity - 1;
        int max$extension_that = 4;
        int n2 = Integer.highestOneBit(Math.max(n, max$extension_that)) * 2;
        int min$extension_that = 0x40000000;
        return Math.min(n2, min$extension_that);
    }

    private int newThreshold(int size) {
        return (int)((double)size * this.loadFactor);
    }

    @Override
    public void clear() {
        Arrays.fill(this.scala$collection$mutable$HashSet$$table, null);
        this.contentSize = 0;
    }

    @Override
    public IterableFactory<HashSet> iterableFactory() {
        return HashSet$.MODULE$;
    }

    @Override
    public HashSet<A> addOne(A elem) {
        this.add(elem);
        return this;
    }

    @Override
    public HashSet<A> subtractOne(A elem) {
        this.remove(elem);
        return this;
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        for (Node<A> n : this.scala$collection$mutable$HashSet$$table) {
            if (n == null) continue;
            n.foreach(f);
        }
    }

    public Object writeReplace() {
        return new DefaultSerializationProxy(new DeserializationFactory(this.scala$collection$mutable$HashSet$$table.length, this.loadFactor), this);
    }

    @Override
    public String className() {
        return "HashSet";
    }

    @Override
    public int hashCode() {
        HashSetIterator setIterator = new /* invalid duplicate definition of identical inner class */;
        HashSetIterator<Object> hashIterator = setIterator.isEmpty() ? setIterator : new HashSetIterator<Object>(this){
            private int hash;
            private final /* synthetic */ HashSet $outer;

            private int hash() {
                return this.hash;
            }

            private void hash_$eq(int x$1) {
                this.hash = x$1;
            }

            public int hashCode() {
                return this.hash();
            }

            /*
             * WARNING - void declaration
             */
            public Object extract(Node<A> nd) {
                void unimproveHash_improvedHash;
                int n = nd.hash();
                if (this.$outer == null) {
                    throw null;
                }
                this.hash_$eq(unimproveHash_improvedHash ^ unimproveHash_improvedHash >>> 16);
                return this;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.hash = 0;
            }
        };
        return MurmurHash3$.MODULE$.unorderedHash((IterableOnce<Object>)hashIterator, MurmurHash3$.MODULE$.setSeed());
    }

    public static final /* synthetic */ boolean $anonfun$subtractAll$1(HashSet $this, Object k, int h2) {
        $this.remove(k, h2 ^ h2 >>> 16);
        return $this.size() > 0;
    }

    public static final /* synthetic */ int $anonfun$stepper$2(Node x$2) {
        return BoxesRunTime.unboxToInt(x$2.key());
    }

    public static final /* synthetic */ long $anonfun$stepper$4(Node x$4) {
        return BoxesRunTime.unboxToLong(x$4.key());
    }

    public static final /* synthetic */ double $anonfun$stepper$6(Node x$6) {
        return BoxesRunTime.unboxToDouble(x$6.key());
    }

    public HashSet(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor;
        this.scala$collection$mutable$HashSet$$table = new Node[this.tableSizeFor(initialCapacity)];
        this.threshold = this.newThreshold(this.scala$collection$mutable$HashSet$$table.length);
        this.contentSize = 0;
    }

    public HashSet() {
        this(16, 0.75);
    }

    public static final /* synthetic */ Object $anonfun$subtractAll$1$adapted(HashSet $this, Object k, Object h2) {
        return BoxesRunTime.boxToBoolean(HashSet.$anonfun$subtractAll$1($this, k, BoxesRunTime.unboxToInt(h2)));
    }

    private static final class DeserializationFactory<A>
    implements Factory<A, HashSet<A>>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final int tableLength;
        private final double loadFactor;

        public int tableLength() {
            return this.tableLength;
        }

        public double loadFactor() {
            return this.loadFactor;
        }

        @Override
        public HashSet<A> fromSpecific(IterableOnce<A> it) {
            return new HashSet(this.tableLength(), this.loadFactor()).addAll((IterableOnce)it);
        }

        @Override
        public Builder<A, HashSet<A>> newBuilder() {
            double newBuilder_loadFactor = this.loadFactor();
            int newBuilder_initialCapacity = this.tableLength();
            return new /* invalid duplicate definition of identical inner class */;
        }

        public DeserializationFactory(int tableLength, double loadFactor) {
            this.tableLength = tableLength;
            this.loadFactor = loadFactor;
        }
    }

    private abstract class HashSetIterator<B>
    extends AbstractIterator<B> {
        private int i;
        private Node<A> node;
        private final int len;

        public abstract B extract(Node<A> var1);

        @Override
        public boolean hasNext() {
            if (this.node != null) {
                return true;
            }
            while (this.i < this.len) {
                Node n = this.scala$collection$mutable$HashSet$HashSetIterator$$$outer().scala$collection$mutable$HashSet$$table[this.i];
                ++this.i;
                if (n == null) continue;
                this.node = n;
                return true;
            }
            return false;
        }

        @Override
        public B next() {
            if (!this.hasNext()) {
                return (B)Iterator$.scala$collection$Iterator$$_empty.next();
            }
            B r = this.extract(this.node);
            this.node = this.node.next();
            return r;
        }

        public /* synthetic */ HashSet scala$collection$mutable$HashSet$HashSetIterator$$$outer() {
            return HashSet.this;
        }

        public HashSetIterator() {
            if (HashSet.this == null) {
                throw null;
            }
            this.i = 0;
            this.node = null;
            this.len = HashSet.this.scala$collection$mutable$HashSet$$table.length;
        }
    }

    public static final class Node<K> {
        private final K _key;
        private final int _hash;
        private Node<K> _next;

        public K key() {
            return this._key;
        }

        public int hash() {
            return this._hash;
        }

        public Node<K> next() {
            return this._next;
        }

        public void next_$eq(Node<K> n) {
            this._next = n;
        }

        public Node<K> findNode(K k, int h2) {
            while (h2 != this_._hash || !BoxesRunTime.equals(k, this_._key)) {
                if (this_._next == null || this_._hash > h2) {
                    return null;
                }
                Node<K> this_ = this_._next;
            }
            return this_;
        }

        public <U> void foreach(Function1<K, U> f) {
            while (true) {
                f.apply(this_._key);
                if (this_._next == null) break;
                Node<K> this_ = this_._next;
            }
        }

        public String toString() {
            return new StringBuilder(12).append("Node(").append(this.key()).append(", ").append(this.hash()).append(") -> ").append(this.next()).toString();
        }

        public Node(K _key, int _hash, Node<K> _next) {
            this._key = _key;
            this._hash = _hash;
            this._next = _next;
        }
    }
}

