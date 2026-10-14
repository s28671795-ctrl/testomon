/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import scala.Console$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceExtensionMethods$;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.LinearSeqOps;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.View;
import scala.collection.generic.SerializeEnd$;
import scala.collection.immutable.AbstractSeq;
import scala.collection.immutable.LazyList$;
import scala.collection.immutable.LinearSeq;
import scala.collection.immutable.Stream$;
import scala.collection.immutable.Stream$Deferrer$;
import scala.collection.immutable.Stream$Empty$;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ObjectRef;

@ScalaSignature(bytes="\u0006\u0005\u0019ec!\u00029r\u0003CA\bbBA\u001e\u0001\u0011\u0005\u0011Q\b\u0005\b\u0003\u007f\u0001a\u0011AA!\u0011\u001d\t\u0019\u0005\u0001D\u0001\u0003\u000bBq!!\u0013\u0001\t\u0003\nY\u0005\u0003\u0005\u0002T\u0001\u0001K\u0011KA+\u0011\u001d\ti\u0007\u0001C#\u0003_Bq!a&\u0001\t\u000b\nI\nC\u0004\u00020\u0002!\t%!-\t\u000f\u0005u\u0006\u0001\"\u0012\u0002@\"9\u00111\u001c\u0001\u0005\u0006\u0005u\u0007\u0002\u0003B\u000b\u0001\u0001&\tBa\u0006\t\u000f\t}\u0001\u0001\"\u0001\u0003\"!9!q\u0004\u0001\u0005\u0002\t-\u0002b\u0002B\u001d\u0001\u0011\u0005!1\b\u0005\b\u0005\u001b\u0002A\u0011\tB(\u0011\u001d\u0011\t\u0007\u0001C#\u0005GBqAa\u001c\u0001\t\u0003\u0012\t\bC\u0004\u0003|\u0001!\tE! \t\u000f\t\r\u0005\u0001\"\u0011\u0003\u0006\"A!\u0011\u0012\u0001\u0005\u0002E\u0014Y\tC\u0004\u0003\u0014\u0002!)E!&\t\u000f\t}\u0005\u0001\"\u0012\u0003\"\"9!q\u0016\u0001\u0005F\tE\u0006b\u0002B`\u0001\u0011\u0015#\u0011\u0019\u0005\b\u0005/\u0004AQ\tBm\u0011\u001d\u0011I\u000f\u0001C#\u0005WDqAa?\u0001\t\u000b\u0012i\u0010C\u0004\u0004\u0010\u0001!)e!\u0005\t\u000f\r]\u0001A\"\u0005\u0004\u001a!911\u0004\u0001\u0005B\ru\u0001\u0002CB\u001e\u0001\u0001&Ia!\u0010\t\u000f\r]\u0003\u0001\"\u0011\u0004Z!91q\f\u0001\u0005B\reqa\u0002D*c\"\u00051q\u000e\u0004\u0007aFD\taa\u001b\t\u000f\u0005m2\u0005\"\u0001\u0004n\u001d91\u0011O\u0012\t\u0002\rMdaBB<G!\u00051\u0011\u0010\u0005\b\u0003w1C\u0011AB>\u0011\u001d\u0019iH\nC\u0001\u0007\u007fBqaa%'\t\u0003\u0019)jB\u0004\u0004(\u000eB\ta!+\u0007\u000f\r-6\u0005#\u0001\u0004.\"9\u00111H\u0016\u0005\u0002\rE\u0006bBBZW\u0011\u00053\u0011\u0004\u0005\b\u0007k[C\u0011IB\\\u0011\u001d\tyd\u000bC!\u0007sCq!a\u0011,\t\u0003\u0019Y\fC\u0004\u0004@.\"\te!1\t\u000f\r]1\u0006\"\u0005\u0004\u001a\u001911\u0011N\u0012\u0003\r?A!b!.4\u0005\u000b\u0007I\u0011\tD\u0015\u0011)1Yc\rB\u0001B\u0003%aQ\u0005\u0005\u000b\u0007\u001f\u001b$\u0011!S\u0001\n\u00195\u0002bBA\u001eg\u0011\u0005aq\u0006\u0005\b\u0007g\u001bD\u0011IB\r\u0011-19d\ra\u0001\u0002\u0003\u0006KAb\t\t\u0011\u0019\u00053\u0007)Q\u0005\r\u0007Bqaa\u00064\t#\u0019I\u0002C\u0004\u0002@M\"\tEb\u0012\t\u000f\u0005\r3\u0007\"\u0001\u0007J!911Y\u0012\u0005\u0004\r\u0015gABBfG\t\u0019i\r\u0003\b\u0004X~\"\t\u0011!B\u0003\u0006\u0004%Ia!7\t\u0017\r\u001dxH!B\u0001B\u0003%11\u001c\u0005\t\u0003wyD\u0011A\u0012\u0004j\"91\u0011_ \u0005\u0002\rM\bb\u0002C\u0001\u007f\u0011\u0005A1\u0001\u0005\n\t#y\u0014\u0011!C!\t'A\u0011\u0002\"\u0006@\u0003\u0003%\t\u0005b\u0006\b\u0013\u0011\u001d2%!A\t\u0002\u0011%b!CBfG\u0005\u0005\t\u0012\u0001C\u0016\u0011\u001d\tY\u0004\u0013C\u0001\t[Aq\u0001b\fI\t\u000b!\t\u0004C\u0004\u0005L!#)\u0001\"\u0014\t\u0013\u0011\u0015\u0004*!A\u0005\u0006\u0011\u001d\u0004\"\u0003C:\u0011\u0006\u0005IQ\u0001C;\u000f\u001d\u0019\tp\tE\u0001\t\u000b3q\u0001b\"$\u0011\u0003!I\tC\u0004\u0002<=#\t\u0001b#\t\u000f\rMu\n\"\u0001\u0005\u000e\"9AqT\u0012\u0005\u0002\u0011\u0005\u0006b\u0002CYG\u0011\u0005A1\u0017\u0005\b\t\u000f\u001cC\u0011\u0001Ce\u0011\u001d!\u0019n\tC!\t+D\u0001Ba%$\t\u0003\tHQ\u001d\u0004\t\u00057\u001b\u0003\u0015!\u0004\u0006\b!Q1q^,\u0003\u0002\u0003\u0006I!\"\u0005\t\u0015\u0005\rvK!A!\u0002\u0013))\u0002C\u0004\u0002<]#\t!b\u0006\t\u0011\u0011uu\u000b)Q\u0005\u000b'A!\"b\bX\u0011\u000b\u0007K\u0011BC\u0011\u0011\u001d\u0011yk\u0016C\u0001\u000bGAqA!;X\t\u0003)\t\u0004C\u0004\u0002n]#\t!\"\u0011\t\u000f\tMu\u000b\"\u0001\u0006N!9Q1K\u0012\u0005\u0002\u0015U\u0003b\u0002CPG\u0011\u0005Qq\r\u0005\b\t?\u001bC\u0011AC9\u0011\u001d))h\tC\u0001\u000boB\u0001\"\"\"$\t\u0003\u0019Sq\u0011\u0005\t\u000b;\u001bC\u0011A\u0012\u0006 \u001a1Q\u0011X\u0012\u0001\u000bwC!\u0002\",h\u0005\u0003\u0007I\u0011CC`\u0011))9m\u001aBA\u0002\u0013EQ\u0011\u001a\u0005\u000b\u000b\u001b<'\u0011!Q!\n\u0015\u0005\u0007bBA\u001eO\u0012\u0005Qq\u001b\u0005\t\u000b;<\u0007\u0015\"\u0003\u0006`\"AQ\u0011_4!\n\u0013)\u0019\u0010\u0003\u0005\u0006\u0000\u001e\u0004K\u0011\u0003D\u0001\u0011%\u0011)bIA\u0001\n\u00131yA\u0001\u0004TiJ,\u0017-\u001c\u0006\u0003eN\f\u0011\"[7nkR\f'\r\\3\u000b\u0005Q,\u0018AC2pY2,7\r^5p]*\ta/A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007e\f\ta\u0005\u0006\u0001u\u0006U\u00111DA\u0013\u0003[\u00012a\u001f?\u007f\u001b\u0005\t\u0018BA?r\u0005-\t%m\u001d;sC\u000e$8+Z9\u0011\u0007}\f\t\u0001\u0004\u0001\u0005\u0011\u0005\r\u0001\u0001\"b\u0001\u0003\u000b\u0011\u0011!Q\t\u0005\u0003\u000f\ty\u0001\u0005\u0003\u0002\n\u0005-Q\"A;\n\u0007\u00055QOA\u0004O_RD\u0017N\\4\u0011\t\u0005%\u0011\u0011C\u0005\u0004\u0003')(aA!osB!10a\u0006\u007f\u0013\r\tI\"\u001d\u0002\n\u0019&tW-\u0019:TKF\u0004\u0002b_A\u000f}\u0006\u0005\u00121E\u0005\u0004\u0003?\t(\u0001\u0004'j]\u0016\f'oU3r\u001fB\u001c\bCA>\u0001!\rY\bA \t\b\u0003O\tIC`A\u0011\u001b\u0005\u0019\u0018bAA\u0016g\n9\u0012\n^3sC\ndWMR1di>\u0014\u0018\u0010R3gCVdGo\u001d\t\u0005\u0003_\t)D\u0004\u0003\u0002\n\u0005E\u0012bAA\u001ak\u00069\u0001/Y2lC\u001e,\u0017\u0002BA\u001c\u0003s\u0011AbU3sS\u0006d\u0017N_1cY\u0016T1!a\rv\u0003\u0019a\u0014N\\5u}Q\u0011\u00111E\u0001\u0005i\u0006LG.\u0006\u0002\u0002$\u0005)am\u001c:dKV\u0011\u0011qI\u0007\u0002\u0001\u0005y\u0011\u000e^3sC\ndWMR1di>\u0014\u00180\u0006\u0002\u0002NA1\u0011qEA(\u0003CI1!!\u0015t\u0005)\u0019V-\u001d$bGR|'/_\u0001\nG2\f7o\u001d(b[\u0016,\"!a\u0016\u0011\t\u0005e\u0013q\r\b\u0005\u00037\n\u0019\u0007E\u0002\u0002^Ul!!a\u0018\u000b\u0007\u0005\u0005t/\u0001\u0004=e>|GOP\u0005\u0004\u0003K*\u0018A\u0002)sK\u0012,g-\u0003\u0003\u0002j\u0005-$AB*ue&twMC\u0002\u0002fU\fqAZ8sK\u0006\u001c\u0007.\u0006\u0003\u0002r\u0005\u0015E\u0003BA:\u0003s\u0002B!!\u0003\u0002v%\u0019\u0011qO;\u0003\tUs\u0017\u000e\u001e\u0005\b\u0003w2\u0001\u0019AA?\u0003\u00051\u0007cBA\u0005\u0003\u007fr\u00181Q\u0005\u0004\u0003\u0003+(!\u0003$v]\u000e$\u0018n\u001c82!\ry\u0018Q\u0011\u0003\b\u0003\u000f3!\u0019AA\u0003\u0005\u0005)\u0006f\u0001\u0004\u0002\fB!\u0011QRAJ\u001b\t\tyIC\u0002\u0002\u0012V\f!\"\u00198o_R\fG/[8o\u0013\u0011\t)*a$\u0003\u000fQ\f\u0017\u000e\u001c:fG\u0006!a-\u001b8e)\u0011\tY*!)\u0011\u000b\u0005%\u0011Q\u0014@\n\u0007\u0005}UO\u0001\u0004PaRLwN\u001c\u0005\b\u0003G;\u0001\u0019AAS\u0003\u0005\u0001\bcBA\u0005\u0003\u007fr\u0018q\u0015\t\u0005\u0003\u0013\tI+C\u0002\u0002,V\u0014qAQ8pY\u0016\fg\u000eK\u0002\b\u0003\u0017\u000bA\u0001^1lKR!\u00111EAZ\u0011\u001d\t)\f\u0003a\u0001\u0003o\u000b\u0011A\u001c\t\u0005\u0003\u0013\tI,C\u0002\u0002<V\u00141!\u00138u\u0003!1w\u000e\u001c3MK\u001a$X\u0003BAa\u0003\u000f$B!a1\u0002VR!\u0011QYAf!\ry\u0018q\u0019\u0003\b\u0003\u0013L!\u0019AA\u0003\u0005\u0005\u0011\u0005bBAg\u0013\u0001\u0007\u0011qZ\u0001\u0003_B\u0004\u0012\"!\u0003\u0002R\u0006\u0015g0!2\n\u0007\u0005MWOA\u0005Gk:\u001cG/[8oe!9\u0011q[\u0005A\u0002\u0005\u0015\u0017!\u0001>)\u0007%\tY)\u0001\u0004baB,g\u000eZ\u000b\u0005\u0003?\f)\u000f\u0006\u0003\u0002b\u0006%\b\u0003B>\u0001\u0003G\u00042a`As\t\u001d\tIM\u0003b\u0001\u0003O\f2A`A\b\u0011!\tYO\u0003CA\u0002\u00055\u0018\u0001\u0002:fgR\u0004b!!\u0003\u0002p\u0006M\u0018bAAyk\nAAHY=oC6,g\b\u0005\u0004\u0002(\u0005U\u00181]\u0005\u0004\u0003o\u001c(\u0001D%uKJ\f'\r\\3P]\u000e,\u0007f\u0001\u0006\u0002|B!\u0011\u0011BA\u007f\u0013\r\ty0\u001e\u0002\u0007S:d\u0017N\\3)\u0017)\u0011\u0019A!\u0003\u0003\f\t=!\u0011\u0003\t\u0005\u0003\u0013\u0011)!C\u0002\u0003\bU\u0014!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f#A!\u0004\u0002sQCW\r\t1baB,g\u000e\u001a1!_B,'/\u0019;j_:\u0004\u0003.Y:!E\u0016,g\u000e\t:f]\u0006lW\r\u001a\u0011aY\u0006T\u00180\u00119qK:$W\rZ!mY\u0002\fQa]5oG\u0016\f#Aa\u0005\u0002\rIr\u0013g\r\u00181\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011I\u0002\u0005\u0003\u0002\n\tm\u0011b\u0001B\u000fk\n1\u0011I\\=SK\u001a\fQ\u0001\u001d:j]R$\"!a\u001d)\u00071\tY\u0010K\u0006\r\u0005\u0007\u0011IAa\n\u0003\u0010\tE\u0011E\u0001B\u0015\u00039*6/\u001a\u0011qe&tG\u000fK:ue\u0016\fWN\f4pe\u000e,g&\\6TiJLgn\u001a\u0015#Y\u0001\u0012\u0013&\u000b\u0011j]N$X-\u00193\u0015\t\u0005M$Q\u0006\u0005\b\u0005_i\u0001\u0019AA,\u0003\r\u0019X\r\u001d\u0015\u0004\u001b\u0005m\bfC\u0007\u0003\u0004\t%!Q\u0007B\b\u0005#\t#Aa\u000e\u0002[U\u001bX\r\t9sS:$\bf\u001d;sK\u0006lgFZ8sG\u0016tSn[*ue&tw\rK:fa&J\u0003%\u001b8ti\u0016\fG-A\bmCjL\u0018\t\u001d9f]\u0012,G-\u00117m+\u0011\u0011iDa\u0011\u0015\t\t}\"Q\t\t\u0005w\u0002\u0011\t\u0005E\u0002\u0000\u0005\u0007\"q!!3\u000f\u0005\u0004\t9\u000f\u0003\u0005\u0003H9!\t\u0019\u0001B%\u0003\u0019\u0019XO\u001a4jqB1\u0011\u0011BAx\u0005\u0017\u0002b!a\n\u0002v\n\u0005\u0013\u0001C:dC:dUM\u001a;\u0016\t\tE#\u0011\f\u000b\u0005\u0005'\u0012y\u0006\u0006\u0003\u0003V\tm\u0003\u0003B>\u0001\u0005/\u00022a B-\t\u001d\tIm\u0004b\u0001\u0003\u000bAq!!4\u0010\u0001\u0004\u0011i\u0006E\u0005\u0002\n\u0005E'q\u000b@\u0003X!9\u0011q[\bA\u0002\t]\u0013A\u0003:fIV\u001cW\rT3giV!!Q\rB5)\u0011\u00119Ga\u001b\u0011\u0007}\u0014I\u0007B\u0004\u0002JB\u0011\r!a:\t\u000f\u0005m\u0004\u00031\u0001\u0003nAI\u0011\u0011BAi\u0005Or(qM\u0001\na\u0006\u0014H/\u001b;j_:$BAa\u001d\u0003zAA\u0011\u0011\u0002B;\u0003G\t\u0019#C\u0002\u0003xU\u0014a\u0001V;qY\u0016\u0014\u0004bBAR#\u0001\u0007\u0011QU\u0001\u0007M&dG/\u001a:\u0015\t\u0005\r\"q\u0010\u0005\b\u0005\u0003\u0013\u0002\u0019AAS\u0003\u0011\u0001(/\u001a3\u0002\u0013\u0019LG\u000e^3s\u001d>$H\u0003BA\u0012\u0005\u000fCqA!!\u0014\u0001\u0004\t)+\u0001\u0006gS2$XM]%na2$b!a\t\u0003\u000e\n=\u0005bBAR)\u0001\u0007\u0011Q\u0015\u0005\b\u0005##\u0002\u0019AAT\u0003%I7O\u00127jaB,G-\u0001\u0006xSRDg)\u001b7uKJ$BAa&\u0003\u001eB9\u0011q\u0005BM}\u0006\u0005\u0012b\u0001BNg\nQq+\u001b;i\r&dG/\u001a:\t\u000f\u0005\rV\u00031\u0001\u0002&\u0006I\u0001O]3qK:$W\rZ\u000b\u0005\u0005G\u0013I\u000b\u0006\u0003\u0003&\n-\u0006\u0003B>\u0001\u0005O\u00032a BU\t\u001d\tIM\u0006b\u0001\u0003ODqA!,\u0017\u0001\u0004\u00119+\u0001\u0003fY\u0016l\u0017aA7baV!!1\u0017B])\u0011\u0011)La/\u0011\tm\u0004!q\u0017\t\u0004\u007f\neFaBAe/\t\u0007\u0011Q\u0001\u0005\b\u0003w:\u0002\u0019\u0001B_!\u001d\tI!a \u007f\u0005o\u000bqaY8mY\u0016\u001cG/\u0006\u0003\u0003D\n%G\u0003\u0002Bc\u0005\u0017\u0004Ba\u001f\u0001\u0003HB\u0019qP!3\u0005\u000f\u0005%\u0007D1\u0001\u0002\u0006!9!Q\u001a\rA\u0002\t=\u0017A\u00019g!\u001d\tIA!5\u007f\u0005\u000fL1Aa5v\u0005=\u0001\u0016M\u001d;jC24UO\\2uS>t\u0007f\u0001\r\u0002\f\u0006a1m\u001c7mK\u000e$h)\u001b:tiV!!1\u001cBq)\u0011\u0011iNa9\u0011\r\u0005%\u0011Q\u0014Bp!\ry(\u0011\u001d\u0003\b\u0003\u0013L\"\u0019AA\u0003\u0011\u001d\u0011i-\u0007a\u0001\u0005K\u0004r!!\u0003\u0003Rz\u0014y\u000eK\u0002\u001a\u0003\u0017\u000bqA\u001a7bi6\u000b\u0007/\u0006\u0003\u0003n\nMH\u0003\u0002Bx\u0005k\u0004Ba\u001f\u0001\u0003rB\u0019qPa=\u0005\u000f\u0005%'D1\u0001\u0002\u0006!9\u00111\u0010\u000eA\u0002\t]\bcBA\u0005\u0003\u007fr(\u0011 \t\u0007\u0003O\t)P!=\u0002\u0007iL\u0007/\u0006\u0003\u0003\u0000\u000e\u001dA\u0003BB\u0001\u0007\u0013\u0001Ba\u001f\u0001\u0004\u0004A9\u0011\u0011\u0002B;}\u000e\u0015\u0001cA@\u0004\b\u00119\u0011\u0011Z\u000eC\u0002\u0005\u0015\u0001bBB\u00067\u0001\u00071QB\u0001\u0005i\"\fG\u000f\u0005\u0004\u0002(\u0005U8QA\u0001\ru&\u0004x+\u001b;i\u0013:$W\r_\u000b\u0003\u0007'\u0001Ba\u001f\u0001\u0004\u0016A9\u0011\u0011\u0002B;}\u0006]\u0016a\u0003;bS2$UMZ5oK\u0012,\"!a*\u0002\u0013\u0005$Gm\u0015;sS:<GCCB\u0010\u0007C\u0019\td!\u000e\u000489\u0019qp!\t\t\u000f\r\rb\u00041\u0001\u0004&\u0005\u00111O\u0019\t\u0005\u0007O\u0019i#\u0004\u0002\u0004*)\u001911F:\u0002\u000f5,H/\u00192mK&!1qFB\u0015\u00055\u0019FO]5oO\n+\u0018\u000e\u001c3fe\"911\u0007\u0010A\u0002\u0005]\u0013!B:uCJ$\bb\u0002B\u0018=\u0001\u0007\u0011q\u000b\u0005\b\u0007sq\u0002\u0019AA,\u0003\r)g\u000eZ\u0001\u0011C\u0012$7\u000b\u001e:j]\u001etuNR8sG\u0016$\"ba\u0010\u0004N\rE31KB+!\u0011\u0019\tea\u0013\u000e\u0005\r\r#\u0002BB#\u0007\u000f\nA\u0001\\1oO*\u00111\u0011J\u0001\u0005U\u00064\u0018-\u0003\u0003\u00040\r\r\u0003bBB(?\u0001\u00071qH\u0001\u0002E\"911G\u0010A\u0002\u0005]\u0003b\u0002B\u0018?\u0001\u0007\u0011q\u000b\u0005\b\u0007sy\u0002\u0019AA,\u0003!!xn\u0015;sS:<GCAB.!\u0011\u0019\te!\u0018\n\t\u0005%41I\u0001\u0010Q\u0006\u001cH)\u001a4j]&$XmU5{K\"Z\u0011Ea\u0001\u0003\n\r\r$q\u0002B\tC\t\u0019)'A4DQ\u0016\u001c7\u000e\t\u0018l]><hnU5{K\u0002Jgn\u001d;fC\u0012\u0004sN\u001a\u0011/Q\u0006\u001cH)\u001a4j]&$XmU5{K\u00022wN\u001d\u0011n_J,\u0007%Y2uS>t\u0017M\u00197fA%tgm\u001c:nCRLwN\u001c\u0011)g\u0016,\u0007e]2bY\u0006$wn\u0019\u0011g_J\u0004C-\u001a;bS2\u001c\u0018&K\u0002\u0001g-\u0012AaQ8ogN)1E!\u0007\u0002NQ\u00111q\u000e\t\u0003w\u000e\nAaY8ogB\u00191Q\u000f\u0014\u000e\u0003\r\u0012AaY8ogN\u0019aE!\u0007\u0015\u0005\rM\u0014!B1qa2LX\u0003BBA\u0007\u000f#baa!\u0004\n\u000e5\u0005\u0003B>\u0001\u0007\u000b\u00032a`BD\t\u001d\t\u0019\u0001\u000bb\u0001\u0003\u000bAqaa#)\u0001\u0004\u0019))\u0001\u0002iI\"A1q\u0012\u0015\u0005\u0002\u0004\u0019\t*\u0001\u0002uYB1\u0011\u0011BAx\u0007\u0007\u000bq!\u001e8baBd\u00170\u0006\u0003\u0004\u0018\u000e}E\u0003BBM\u0007G\u0003b!!\u0003\u0002\u001e\u000em\u0005\u0003CA\u0005\u0005k\u001aij!)\u0011\u0007}\u001cy\nB\u0004\u0002\u0004%\u0012\r!!\u0002\u0011\tm\u00041Q\u0014\u0005\b\u0007KK\u0003\u0019ABQ\u0003\tA8/A\u0003F[B$\u0018\u0010E\u0002\u0004v-\u0012Q!R7qif\u001c2aKBX!\u0011Y\b!a\u0002\u0015\u0005\r%\u0016aB5t\u000b6\u0004H/_\u0001\u0005Q\u0016\fG-\u0006\u0002\u0002\bU\u00111qV\u000b\u0003\u0007{k\u0011aK\u0001\nW:|wO\\*ju\u0016,\"!a.\u0002\u0015Q|G)\u001a4feJ,'/\u0006\u0003\u0004H\u0012}A\u0003BBe\tC\u0001Ra!\u001e@\t;\u0011\u0001\u0002R3gKJ\u0014XM]\u000b\u0005\u0007\u001f\u001c)oE\u0002@\u0007#\u0004B!!\u0003\u0004T&\u00191Q[;\u0003\r\u0005s\u0017PV1m\u00035\u001a8-\u00197bI\r|G\u000e\\3di&|g\u000eJ5n[V$\u0018M\u00197fIM#(/Z1nI\u0011+g-\u001a:sKJ$C\u0005\\\u000b\u0003\u00077\u0004b!!\u0003\u0004^\u000e\u0005\u0018bABpk\nIa)\u001e8di&|g\u000e\r\t\u0005w\u0002\u0019\u0019\u000fE\u0002\u0000\u0007K$q!a\u0001@\u0005\u0004\t)!\u0001\u0018tG\u0006d\u0017\rJ2pY2,7\r^5p]\u0012JW.\\;uC\ndW\rJ*ue\u0016\fW\u000e\n#fM\u0016\u0014(/\u001a:%I1\u0004C\u0003BBv\u0007[\u0004Ra!\u001e@\u0007GDqaa<C\u0001\u0004\u0019Y.A\u0001m\u0003E!\u0003.Y:iI\r|Gn\u001c8%G>dwN\\\u000b\u0005\u0007k\u001cY\u0010\u0006\u0003\u0004x\u000e}\b\u0003B>\u0001\u0007s\u00042a`B~\t\u001d\tIm\u0011b\u0001\u0007{\fBaa9\u0002\u0010!9!QV\"A\u0002\re\u0018a\u0006\u0013iCNDGeY8m_:$3m\u001c7p]\u0012\u001aw\u000e\\8o+\u0011!)\u0001b\u0003\u0015\t\u0011\u001dAQ\u0002\t\u0005w\u0002!I\u0001E\u0002\u0000\t\u0017!q!!3E\u0005\u0004\u0019i\u0010C\u0004\u0005\u0010\u0011\u0003\r\u0001b\u0002\u0002\rA\u0014XMZ5y\u0003!A\u0017m\u001d5D_\u0012,GCAA\\\u0003\u0019)\u0017/^1mgR!\u0011q\u0015C\r\u0011%!YBRA\u0001\u0002\u0004\ty!A\u0002yIE\u00022a C\u0010\t\u001d\t\u0019A\u0010b\u0001\u0003\u000bA\u0001ba<?\t\u0003\u0007A1\u0005\t\u0007\u0003\u0013\ty\u000f\"\n\u0011\tm\u0004AQD\u0001\t\t\u00164WM\u001d:feB\u00191Q\u000f%\u0014\u0007!\u0013I\u0002\u0006\u0002\u0005*\u0005YB\u0005[1tQ\u0012\u001aw\u000e\\8oI\r|Gn\u001c8%Kb$XM\\:j_:,b\u0001b\r\u0005<\u0011\u0005C\u0003\u0002C\u001b\t\u000b\"B\u0001b\u000e\u0005DA!1\u0010\u0001C\u001d!\ryH1\b\u0003\b\u0003\u0013T%\u0019\u0001C\u001f#\u0011!y$a\u0004\u0011\u0007}$\t\u0005B\u0004\u0002\u0004)\u0013\r!!\u0002\t\u000f\t5&\n1\u0001\u0005:!9Aq\t&A\u0002\u0011%\u0013!\u0002\u0013uQ&\u001c\b#BB;\u007f\u0011}\u0012!\t\u0013iCNDGeY8m_:$3m\u001c7p]\u0012\u001aw\u000e\\8oI\u0015DH/\u001a8tS>tWC\u0002C(\t/\"i\u0006\u0006\u0003\u0005R\u0011\u0005D\u0003\u0002C*\t?\u0002Ba\u001f\u0001\u0005VA\u0019q\u0010b\u0016\u0005\u000f\u0005%7J1\u0001\u0005ZE!A1LA\b!\ryHQ\f\u0003\b\u0003\u0007Y%\u0019AA\u0003\u0011\u001d!ya\u0013a\u0001\t'Bq\u0001b\u0012L\u0001\u0004!\u0019\u0007E\u0003\u0004v}\"Y&\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tW\u0003\u0002C5\tc\"B\u0001b\u0005\u0005l!9Aq\t'A\u0002\u00115\u0004#BB;\u007f\u0011=\u0004cA@\u0005r\u00119\u00111\u0001'C\u0002\u0005\u0015\u0011\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\u0011!9\bb!\u0015\t\u0011eDQ\u0010\u000b\u0005\u0003O#Y\bC\u0005\u0005\u001c5\u000b\t\u00111\u0001\u0002\u0010!9AqI'A\u0002\u0011}\u0004#BB;\u007f\u0011\u0005\u0005cA@\u0005\u0004\u00129\u00111A'C\u0002\u0005\u0015\u0001cAB;\u001f\n\tB\u0005[1tQ\u0012\u001aw\u000e\\8oI\r|Gn\u001c8\u0014\u0007=\u0013I\u0002\u0006\u0002\u0005\u0006V!Aq\u0012CL)\u0011!\t\nb'\u0011\r\u0005%\u0011Q\u0014CJ!!\tIA!\u001e\u0005\u0016\u0012e\u0005cA@\u0005\u0018\u00129\u00111A)C\u0002\u0005\u0015\u0001\u0003B>\u0001\t+Cq\u0001\"(R\u0001\u0004!I*A\u0001t\u0003\u00111'o\\7\u0016\t\u0011\rF\u0011\u0016\u000b\u0005\tK#Y\u000b\u0005\u0003|\u0001\u0011\u001d\u0006cA@\u0005*\u00129\u00111\u0001*C\u0002\u0005\u0015\u0001b\u0002CW%\u0002\u0007AqV\u0001\u0005G>dG\u000e\u0005\u0004\u0002(\u0005UHqU\u0001\rMJ|W.\u0013;fe\u0006$xN]\u000b\u0005\tk#Y\f\u0006\u0003\u00058\u0012u\u0006\u0003B>\u0001\ts\u00032a C^\t\u001d\t\u0019a\u0015b\u0001\u0003\u000bAq\u0001b0T\u0001\u0004!\t-\u0001\u0002jiB1\u0011q\u0005Cb\tsK1\u0001\"2t\u0005!IE/\u001a:bi>\u0014\u0018!B3naRLX\u0003\u0002Cf\t#,\"\u0001\"4\u0011\tm\u0004Aq\u001a\t\u0004\u007f\u0012EGaBA\u0002)\n\u0007\u0011QA\u0001\u000b]\u0016<()^5mI\u0016\u0014X\u0003\u0002Cl\tC,\"\u0001\"7\u0011\u0011\r\u001dB1\u001cCp\tGLA\u0001\"8\u0004*\t9!)^5mI\u0016\u0014\bcA@\u0005b\u00129\u00111A+C\u0002\u0005\u0015\u0001\u0003B>\u0001\t?,B\u0001b:\u0005nR1A\u0011\u001eCx\u000b\u0007\u0001\u0002\"a\n\u0003\u001a\u0012-\u0018\u0011\u0005\t\u0004\u007f\u00125HaBA\u0002-\n\u0007\u0011Q\u0001\u0005\b\u0007_4\u0006\u0019\u0001CyU\u0011!\u0019\u0010\">\u0011\tm\u0004A1^\u0016\u0003\to\u0004B\u0001\"?\u0005\u00006\u0011A1 \u0006\u0005\t{\fy)A\u0005v]\u000eDWmY6fI&!Q\u0011\u0001C~\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0005\b\u0003G3\u0006\u0019AC\u0003!!\tI!a \u0005l\u0006\u001dV\u0003BC\u0005\u000b\u001f\u00192aVC\u0006!!\t9C!'\u0006\u000e\u0005\u0005\u0002cA@\u0006\u0010\u00119\u00111A,C\u0002\u0005\u0015!\u0006BC\n\tk\u0004Ba\u001f\u0001\u0006\u000eAA\u0011\u0011BA@\u000b\u001b\t9\u000b\u0006\u0004\u0006\u001a\u0015mQQ\u0004\t\u0006\u0007k:VQ\u0002\u0005\b\u0007_T\u0006\u0019AC\t\u0011\u001d\t\u0019K\u0017a\u0001\u000b+\t\u0001BZ5mi\u0016\u0014X\rZ\u000b\u0003\u000b')B!\"\n\u0006,Q!QqEC\u0017!\u0011Y\b!\"\u000b\u0011\u0007},Y\u0003B\u0004\u0002Jv\u0013\r!!\u0002\t\u000f\u0005mT\f1\u0001\u00060AA\u0011\u0011BA@\u000b\u001b)I#\u0006\u0003\u00064\u0015eB\u0003BC\u001b\u000bw\u0001Ba\u001f\u0001\u00068A\u0019q0\"\u000f\u0005\u000f\u0005%gL1\u0001\u0002\u0006!9\u00111\u00100A\u0002\u0015u\u0002\u0003CA\u0005\u0003\u007f*i!b\u0010\u0011\r\u0005\u001d\u0012Q_C\u001c+\u0011)\u0019%b\u0013\u0015\t\u0005MTQ\t\u0005\b\u0003wz\u0006\u0019AC$!!\tI!a \u0006\u000e\u0015%\u0003cA@\u0006L\u00119\u0011qQ0C\u0002\u0005\u0015A\u0003BC\u0006\u000b\u001fBq!\"\u0015a\u0001\u0004))\"A\u0001r\u0003\u001dIG/\u001a:bi\u0016,B!b\u0016\u0006`Q!Q\u0011LC3)\u0011)Y&\"\u0019\u0011\tm\u0004QQ\f\t\u0004\u007f\u0016}CaBA\u0002C\n\u0007\u0011Q\u0001\u0005\b\u0003w\n\u0007\u0019AC2!!\tI!a \u0006^\u0015u\u0003bBB\u001aC\u0002\u0007QQ\f\u000b\u0007\u000bS*Y'\"\u001c\u0011\tm\u0004\u0011q\u0017\u0005\b\u0007g\u0011\u0007\u0019AA\\\u0011\u001d)yG\u0019a\u0001\u0003o\u000bAa\u001d;faR!Q\u0011NC:\u0011\u001d\u0019\u0019d\u0019a\u0001\u0003o\u000b1bY8oi&tW/\u00197msV!Q\u0011PC@)\u0011)Y(\"!\u0011\tm\u0004QQ\u0010\t\u0004\u007f\u0016}DaBA\u0002I\n\u0007\u0011Q\u0001\u0005\t\u0005[#G\u00111\u0001\u0006\u0004B1\u0011\u0011BAx\u000b{\nABZ5mi\u0016\u0014X\r\u001a+bS2,B!\"#\u0006\u0010RAQ1RCI\u000b/+Y\n\u0005\u0003|\u0001\u00155\u0005cA@\u0006\u0010\u00129\u00111A3C\u0002\u0005\u0015\u0001bBCJK\u0002\u0007QQS\u0001\u0007gR\u0014X-Y7+\t\u0015-EQ\u001f\u0005\b\u0003G+\u0007\u0019ACM!!\tI!a \u0006\u000e\u0006\u001d\u0006b\u0002BIK\u0002\u0007\u0011qU\u0001\u000eG>dG.Z2uK\u0012$\u0016-\u001b7\u0016\r\u0015\u0005V1WCT)!)\u0019+\"+\u0006,\u0016U\u0006\u0003B>\u0001\u000bK\u00032a`CT\t\u001d\tIM\u001ab\u0001\u0003\u000bAqa!.g\u0001\u0004))\u000bC\u0004\u0006\u0014\u001a\u0004\r!\",+\t\u0015=FQ\u001f\t\u0005w\u0002)\t\fE\u0002\u0000\u000bg#q!a\u0001g\u0005\u0004\t)\u0001C\u0004\u0003N\u001a\u0004\r!b.\u0011\u0011\u0005%!\u0011[CY\u000bK\u0013!cU3sS\u0006d\u0017N_1uS>t\u0007K]8ysV!QQXCc'\u00159'\u0011DA\u0017+\t)\t\r\u0005\u0003|\u0001\u0015\r\u0007cA@\u0006F\u00129\u00111A4C\u0002\u0005\u0015\u0011\u0001C2pY2|F%Z9\u0015\t\u0005MT1\u001a\u0005\n\t7I\u0017\u0011!a\u0001\u000b\u0003\fQaY8mY\u0002B3A[Ci!\u0011\tI!b5\n\u0007\u0015UWOA\u0005ue\u0006t7/[3oiR!Q\u0011\\Cn!\u0015\u0019)hZCb\u0011\u001d!ik\u001ba\u0001\u000b\u0003\f1b\u001e:ji\u0016|%M[3diR!\u00111OCq\u0011\u001d)\u0019\u000f\u001ca\u0001\u000bK\f1a\\;u!\u0011)9/\"<\u000e\u0005\u0015%(\u0002BCv\u0007\u000f\n!![8\n\t\u0015=X\u0011\u001e\u0002\u0013\u001f\nTWm\u0019;PkR\u0004X\u000f^*ue\u0016\fW.\u0001\u0006sK\u0006$wJ\u00196fGR$B!a\u001d\u0006v\"9Qq_7A\u0002\u0015e\u0018AA5o!\u0011)9/b?\n\t\u0015uX\u0011\u001e\u0002\u0012\u001f\nTWm\u0019;J]B,Ho\u0015;sK\u0006l\u0017a\u0003:fC\u0012\u0014Vm]8mm\u0016$\"!a\u0004)\u000f\u001d4)Ab\u0003\u0007\u000eA!\u0011\u0011\u0002D\u0004\u0013\r1I!\u001e\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012a\u0001\u000b\u0003\r#\u0001Ba!\u0011\u0007\u0014%!aQCB\"\u0005\u0019y%M[3di\":1E\"\u0002\u0007\f\u00195\u0001fC\u0012\u0003\u0004\t%a1\u0004B\b\u0005#\t#A\"\b\u0002#V\u001bX\r\t'bufd\u0015n\u001d;!Q]D\u0017n\u00195!SN\u0004c-\u001e7ms\u0002b\u0017M_=*A%t7\u000f^3bI\u0002zg\rI*ue\u0016\fW\u000e\t\u0015xQ&\u001c\u0007\u000e\t5bg\u0002\n\u0007\u0005\\1{s\u0002\"\u0018-\u001b7!_:d\u00170K\u000b\u0005\rC19cE\u00024\rG\u0001Ba\u001f\u0001\u0007&A\u0019qPb\n\u0005\u000f\u0005\r1G1\u0001\u0002\u0006U\u0011aQE\u0001\u0006Q\u0016\fG\r\t\t\u0007\u0003\u0013\tyOb\t\u0015\r\u0019Eb1\u0007D\u001b!\u0015\u0019)h\rD\u0013\u0011\u001d\u0019)l\u000ea\u0001\rKA\u0001ba$8\t\u0003\u0007aQF\u0001\u0006i24\u0016\r\u001c\u0015\u0004s\u0019m\u0002\u0003BA\u0005\r{I1Ab\u0010v\u0005!1x\u000e\\1uS2,\u0017!\u0002;m\u000f\u0016t\u0007CBA\u0005\u0007;4\u0019\u0003K\u0002;\rw)\"Ab\t\u0016\u0005\u0019-S\"A\u001a)\u000fM2)Ab\u0003\u0007\u000e!:\u0001A\"\u0002\u0007\f\u00195\u0001f\u0003\u0001\u0003\u0004\t%a1\u0004B\b\u0005#\taa\u0015;sK\u0006l\u0007f\u0002\u0012\u0007\u0006\u0019-aQ\u0002\u0015\fE\t\r!\u0011\u0002D\u000e\u0005\u001f\u0011\t\u0002")
public abstract class Stream<A>
extends AbstractSeq<A>
implements LinearSeq<A>,
Serializable {
    private static final long serialVersionUID = 3L;

    public static <A> Stream<A> continually(Function0<A> elem) {
        Function0<Stream> & Serializable continually_apply_tl = () -> Stream$.$anonfun$continually$1(elem);
        A continually_apply_hd = elem.apply();
        return new Cons<A>(continually_apply_hd, continually_apply_tl);
    }

    public static Stream<Object> from(int start2) {
        return Stream$.MODULE$.from(start2, 1);
    }

    public static Stream<Object> from(int start2, int step) {
        return Stream$.MODULE$.from(start2, step);
    }

    public static <A> Stream<A> iterate(A start2, Function1<A, A> f) {
        Function0<Stream> & Serializable iterate_apply_tl = () -> Stream$.$anonfun$iterate$1(f, start2);
        return new Cons<A>(start2, iterate_apply_tl);
    }

    public static <A> Builder<A, Stream<A>> newBuilder() {
        return Stream$.MODULE$.newBuilder();
    }

    public static <A> Stream<A> fromIterator(Iterator<A> it) {
        return Stream$.MODULE$.fromIterator(it);
    }

    public static <A> Stream<A> from(IterableOnce<A> coll) {
        return Stream$.MODULE$.from((IterableOnce)coll);
    }

    public static Function0 toDeferrer(Function0 l) {
        return l;
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        Stream$ tabulate_this = Stream$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        Stream$ tabulate_this = Stream$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        Stream$ tabulate_this = Stream$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        Stream$ tabulate_this = Stream$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n, Function1 f) {
        Stream$ tabulate_this = Stream$.MODULE$;
        View.Tabulate from_source = new View.Tabulate(n, f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        Stream$ fill_this = Stream$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        Stream$ fill_this = Stream$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        Stream$ fill_this = Stream$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        Stream$ fill_this = Stream$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n, Function0 elem) {
        Stream$ fill_this = Stream$.MODULE$;
        View.Fill from_source = new View.Fill(n, elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(Stream$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(Stream$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        Stream$ unfold_this = Stream$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        Stream$ iterate_this = Stream$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
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
    public Iterator<A> iterator() {
        return LinearSeqOps.iterator$(this);
    }

    @Override
    public int length() {
        return LinearSeqOps.length$(this);
    }

    @Override
    public A last() {
        return (A)LinearSeqOps.last$(this);
    }

    @Override
    public int lengthCompare(int len) {
        return LinearSeqOps.lengthCompare$((LinearSeqOps)this, len);
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
    public boolean forall(Function1<A, Object> p) {
        return LinearSeqOps.forall$(this, p);
    }

    @Override
    public boolean exists(Function1<A, Object> p) {
        return LinearSeqOps.exists$(this, p);
    }

    @Override
    public <A1> boolean contains(A1 elem) {
        return LinearSeqOps.contains$(this, elem);
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
    public Iterator<Stream<A>> tails() {
        return LinearSeqOps.tails$(this);
    }

    public abstract Stream<A> force();

    @Override
    public SeqFactory<Stream> iterableFactory() {
        return Stream$.MODULE$;
    }

    @Override
    public String className() {
        return "Stream";
    }

    @Override
    public final <U> void foreach(Function1<A, U> f) {
        while (!this_.isEmpty()) {
            f.apply(this_.head());
            Stream this_ = (Stream)this_.tail();
        }
    }

    @Override
    public final Option<A> find(Function1<A, Object> p) {
        while (!this_.isEmpty()) {
            if (BoxesRunTime.unboxToBoolean(p.apply(this_.head()))) {
                return new Some(this_.head());
            }
            Stream this_ = (Stream)this_.tail();
        }
        return None$.MODULE$;
    }

    @Override
    public Stream<A> take(int n) {
        if (n <= 0 || this.isEmpty()) {
            return Stream$Empty$.MODULE$;
        }
        if (n == 1) {
            return new Cons(this.head(), (Function0<Stream> & Serializable)() -> Stream$.MODULE$.empty());
        }
        return new Cons(this.head(), (Function0<Stream> & Serializable)() -> ((Stream)this.tail()).take(n - 1));
    }

    @Override
    public final <B> B foldLeft(B z, Function2<B, A, B> op) {
        while (!this_.isEmpty()) {
            B b = op.apply(z, this_.head());
            z = b;
            Stream this_ = (Stream)this_.tail();
        }
        return z;
    }

    public final <B> Stream<B> append(Function0<IterableOnce<B>> rest) {
        if (this.isEmpty()) {
            return Stream$.MODULE$.from((IterableOnce)rest.apply());
        }
        Function0<Stream> & Serializable lazyAppendedAll_apply_tl = (Function0<Stream> & Serializable)() -> {
            void lazyAppendedAll_apply_tl;
            Stream stream = (Stream)this.tail();
            if (stream == null) {
                throw null;
            }
            Stream lazyAppendedAll_this = stream;
            if (lazyAppendedAll_this.isEmpty()) {
                return Stream$.MODULE$.from((IterableOnce)rest.apply());
            }
            Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$lazyAppendedAll$1(lazyAppendedAll_this, rest);
            Object lazyAppendedAll_apply_hd = lazyAppendedAll_this.head();
            return new Cons(lazyAppendedAll_apply_hd, lazyAppendedAll_apply_tl);
        };
        Object lazyAppendedAll_apply_hd = this.head();
        return new Cons(lazyAppendedAll_apply_hd, lazyAppendedAll_apply_tl);
    }

    public Object writeReplace() {
        if (this.nonEmpty() && this.tailDefined()) {
            return new SerializationProxy(this);
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public void print() {
        void mkString_end;
        void mkString_sep;
        Stream<A> stream = this.force();
        String string = ", ";
        if (stream == null) {
            throw null;
        }
        Stream<A> mkString_this = stream;
        String string2 = "";
        String mkString_start = "";
        Object var3_4 = null;
        string2 = null;
        Object var1_2 = null;
        string = null;
        Console$.MODULE$.print(IterableOnceOps.mkString$(mkString_this, mkString_start, (String)mkString_sep, (String)mkString_end));
    }

    /*
     * WARNING - void declaration
     */
    public void print(String sep) {
        void mkString_end;
        Stream<A> stream = this.force();
        if (stream == null) {
            throw null;
        }
        Stream<A> mkString_this = stream;
        String string = "";
        String mkString_start = "";
        Object var3_4 = null;
        string = null;
        Object var2_2 = null;
        Console$.MODULE$.print(IterableOnceOps.mkString$(mkString_this, mkString_start, sep, (String)mkString_end));
    }

    public <B> Stream<B> lazyAppendedAll(Function0<IterableOnce<B>> suffix) {
        if (this.isEmpty()) {
            return Stream$.MODULE$.from((IterableOnce)suffix.apply());
        }
        Function0<Stream> & Serializable apply_tl = (Function0<Stream> & Serializable)() -> {
            void lazyAppendedAll_apply_tl;
            Stream stream = (Stream)this.tail();
            if (stream == null) {
                throw null;
            }
            Stream lazyAppendedAll_this = stream;
            if (lazyAppendedAll_this.isEmpty()) {
                return Stream$.MODULE$.from((IterableOnce)rest.apply());
            }
            Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$lazyAppendedAll$1(lazyAppendedAll_this, rest);
            Object lazyAppendedAll_apply_hd = lazyAppendedAll_this.head();
            return new Cons(lazyAppendedAll_apply_hd, lazyAppendedAll_apply_tl);
        };
        Object apply_hd = this.head();
        return new Cons(apply_hd, apply_tl);
    }

    @Override
    public <B> Stream<B> scanLeft(B z, Function2<B, A, B> op) {
        if (this.isEmpty()) {
            return (Stream)Stream$Empty$.MODULE$.prepended(z);
        }
        Function0<Stream> & Serializable apply_tl = (Function0<Stream> & Serializable)() -> {
            void scanLeft_z;
            Stream stream = (Stream)this.tail();
            Object r = op.apply(z, this.head());
            if (stream == null) {
                throw null;
            }
            Stream scanLeft_this = stream;
            if (scanLeft_this.isEmpty()) {
                return (Stream)Stream$Empty$.MODULE$.prepended(scanLeft_z);
            }
            Function0<Stream> & Serializable scanLeft_apply_tl = () -> Stream.$anonfun$scanLeft$1(scanLeft_this, op, scanLeft_z);
            return new Cons<void>(scanLeft_z, scanLeft_apply_tl);
        };
        return new Cons<B>(z, apply_tl);
    }

    @Override
    public final <B> B reduceLeft(Function2<B, A, B> f) {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("empty.reduceLeft");
        }
        Object reducedRes = this.head();
        Stream left = (Stream)this.tail();
        while (!left.isEmpty()) {
            reducedRes = f.apply(reducedRes, left.head());
            left = (Stream)left.tail();
        }
        return (B)reducedRes;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Tuple2<Stream<A>, Stream<A>> partition(Function1<A, Object> p) {
        Object var10_13;
        Stream stream;
        Function0<Stream> & Serializable intersect;
        Object a;
        Object var5_7;
        Stream stream2;
        Function0<Stream> & Serializable intersect2;
        Object a2;
        Function1<Object, Object> & Serializable filter_pred = (Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(Stream.$anonfun$partition$1(p, x$1));
        boolean filter_filterImpl_isFlipped = false;
        Stream filter_filterImpl_rest = this;
        while (filter_filterImpl_rest.nonEmpty() && BoxesRunTime.unboxToBoolean(p.apply(a2 = filter_filterImpl_rest.head())) == filter_filterImpl_isFlipped) {
            filter_filterImpl_rest = (Stream)filter_filterImpl_rest.tail();
        }
        if (filter_filterImpl_rest.nonEmpty()) {
            void filter_filterImpl_filteredTail_apply_tl;
            intersect2 = () -> Stream$.$anonfun$filteredTail$1(filter_filterImpl_rest, filter_pred, filter_filterImpl_isFlipped);
            Object filter_filterImpl_filteredTail_apply_hd = filter_filterImpl_rest.head();
            stream2 = new Cons(filter_filterImpl_filteredTail_apply_hd, filter_filterImpl_filteredTail_apply_tl);
            var5_7 = null;
            intersect2 = null;
        } else {
            stream2 = Stream$Empty$.MODULE$;
        }
        Object var4_4 = null;
        var5_7 = null;
        intersect2 = null;
        Object var2_2 = null;
        Function1<Object, Object> & Serializable filterNot_pred = (Function1<Object, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(Stream.$anonfun$partition$2(p, x$2));
        boolean filterNot_filterImpl_isFlipped = true;
        Stream filterNot_filterImpl_rest = this;
        while (filterNot_filterImpl_rest.nonEmpty() && BoxesRunTime.unboxToBoolean(p.apply(a = filterNot_filterImpl_rest.head())) == filterNot_filterImpl_isFlipped) {
            filterNot_filterImpl_rest = (Stream)filterNot_filterImpl_rest.tail();
        }
        if (filterNot_filterImpl_rest.nonEmpty()) {
            void filterNot_filterImpl_filteredTail_apply_tl;
            intersect = () -> Stream$.$anonfun$filteredTail$1(filterNot_filterImpl_rest, filterNot_pred, filterNot_filterImpl_isFlipped);
            Object filterNot_filterImpl_filteredTail_apply_hd = filterNot_filterImpl_rest.head();
            stream = new Cons(filterNot_filterImpl_filteredTail_apply_hd, filterNot_filterImpl_filteredTail_apply_tl);
            var10_13 = null;
            intersect = null;
        } else {
            stream = Stream$Empty$.MODULE$;
        }
        Object var9_10 = null;
        var10_13 = null;
        intersect = null;
        Object var7_8 = null;
        return new Tuple2<Stream<A>, Stream<A>>(stream2, stream);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Stream<A> filter(Function1<A, Object> pred) {
        boolean filterImpl_isFlipped = false;
        Stream filterImpl_rest = this;
        while (filterImpl_rest.nonEmpty() && BoxesRunTime.unboxToBoolean(pred.apply(filterImpl_rest.head())) == filterImpl_isFlipped) {
            filterImpl_rest = (Stream)filterImpl_rest.tail();
        }
        if (filterImpl_rest.nonEmpty()) {
            void filterImpl_filteredTail_apply_tl;
            Function0<Stream> & Serializable intersect = () -> Stream$.$anonfun$filteredTail$1(filterImpl_rest, pred, filterImpl_isFlipped);
            Object filterImpl_filteredTail_apply_hd = filterImpl_rest.head();
            return new Cons(filterImpl_filteredTail_apply_hd, filterImpl_filteredTail_apply_tl);
        }
        return Stream$Empty$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Stream<A> filterNot(Function1<A, Object> pred) {
        boolean filterImpl_isFlipped = true;
        Stream filterImpl_rest = this;
        while (filterImpl_rest.nonEmpty() && BoxesRunTime.unboxToBoolean(pred.apply(filterImpl_rest.head())) == filterImpl_isFlipped) {
            filterImpl_rest = (Stream)filterImpl_rest.tail();
        }
        if (filterImpl_rest.nonEmpty()) {
            void filterImpl_filteredTail_apply_tl;
            Function0<Stream> & Serializable intersect = () -> Stream$.$anonfun$filteredTail$1(filterImpl_rest, pred, filterImpl_isFlipped);
            Object filterImpl_filteredTail_apply_hd = filterImpl_rest.head();
            return new Cons(filterImpl_filteredTail_apply_hd, filterImpl_filteredTail_apply_tl);
        }
        return Stream$Empty$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    public Stream<A> filterImpl(Function1<A, Object> p, boolean isFlipped) {
        Stream rest = this;
        while (rest.nonEmpty() && BoxesRunTime.unboxToBoolean(p.apply(rest.head())) == isFlipped) {
            rest = (Stream)rest.tail();
        }
        if (rest.nonEmpty()) {
            void filteredTail_apply_tl;
            Function0<Stream> & Serializable intersect = () -> Stream$.$anonfun$filteredTail$1(rest, p, isFlipped);
            Object filteredTail_apply_hd = rest.head();
            return new Cons(filteredTail_apply_hd, filteredTail_apply_tl);
        }
        return Stream$Empty$.MODULE$;
    }

    @Override
    public final scala.collection.WithFilter<A, Stream> withFilter(Function1<A, Object> p) {
        return new WithFilter<A>(this, p);
    }

    @Override
    public final <B> Stream<B> prepended(B elem) {
        Function0<Stream> & Serializable apply_tl = (Function0<Stream> & Serializable)() -> (Stream)this.coll();
        return new Cons<B>(elem, apply_tl);
    }

    @Override
    public final <B> Stream<B> map(Function1<A, B> f) {
        if (this.isEmpty()) {
            return Stream$Empty$.MODULE$;
        }
        Function0<Stream> & Serializable apply_tl = (Function0<Stream> & Serializable)() -> {
            void map_apply_tl;
            Stream stream = (Stream)this.tail();
            if (stream == null) {
                throw null;
            }
            Stream map_this = stream;
            if (map_this.isEmpty()) {
                return Stream$Empty$.MODULE$;
            }
            Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$map$1(map_this, f);
            Object map_apply_hd = f.apply(map_this.head());
            return new Cons(map_apply_hd, map_apply_tl);
        };
        B apply_hd = f.apply(this.head());
        return new Cons<B>(apply_hd, apply_tl);
    }

    @Override
    public final <B> Stream<B> collect(PartialFunction<A, B> pf) {
        while (true) {
            if (this_.isEmpty()) {
                return Stream$Empty$.MODULE$;
            }
            ObjectRef<Object> newHead = new ObjectRef<Object>(null);
            if (BoxesRunTime.unboxToBoolean(pf.runWith((Function1<Object, Object> & Serializable)(B b) -> {
                newHead.elem = b;
                return BoxedUnit.UNIT;
            }).apply(this_.head()))) {
                return Stream$.MODULE$.collectedTail(newHead.elem, this_, pf);
            }
            Stream this_ = (Stream)this_.tail();
        }
    }

    @Override
    public final <B> Option<B> collectFirst(PartialFunction<A, B> pf) {
        while (!this_.isEmpty()) {
            ObjectRef<Object> newHead = new ObjectRef<Object>(null);
            if (BoxesRunTime.unboxToBoolean(pf.runWith((Function1<Object, Object> & Serializable)(B b) -> {
                newHead.elem = b;
                return BoxedUnit.UNIT;
            }).apply(this_.head()))) {
                return new Some(newHead.elem);
            }
            Stream this_ = (Stream)this_.tail();
        }
        return None$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final <B> Stream<B> flatMap(Function1<A, IterableOnce<B>> f) {
        void lazyAppendedAll_apply_tl;
        void lazyAppendedAll_suffix;
        void from_source;
        if (this.isEmpty()) {
            return Stream$Empty$.MODULE$;
        }
        ObjectRef<Stream> nonEmptyPrefix = new ObjectRef<Stream>(this);
        IterableOnce<B> iterableOnce = f.apply(((Stream)nonEmptyPrefix.elem).head());
        Stream$ from_this = Stream$.MODULE$;
        Object var7_4 = null;
        iterableOnce = null;
        Object prefix = from_this.from((IterableOnce)from_source);
        while (!((Stream)nonEmptyPrefix.elem).isEmpty() && ((scala.collection.AbstractSeq)prefix).isEmpty()) {
            void from_source2;
            nonEmptyPrefix.elem = (Stream)((Stream)nonEmptyPrefix.elem).tail();
            if (((Stream)nonEmptyPrefix.elem).isEmpty()) continue;
            IterableOnce<B> iterableOnce2 = f.apply(((Stream)nonEmptyPrefix.elem).head());
            Stream$ from_this2 = Stream$.MODULE$;
            Object var9_6 = null;
            iterableOnce2 = null;
            prefix = from_this2.from((IterableOnce)from_source2);
        }
        if (((Stream)nonEmptyPrefix.elem).isEmpty()) {
            return Stream$Empty$.MODULE$;
        }
        Function0<Stream> & Serializable intersect = (Function0<Stream> & Serializable)() -> {
            void flatMap_lazyAppendedAll_apply_tl;
            void flatMap_lazyAppendedAll_suffix;
            void from_source;
            Stream stream = (Stream)((Stream)nonEmptyPrefix$1.elem).tail();
            if (stream == null) {
                throw null;
            }
            Stream flatMap_this = stream;
            if (flatMap_this.isEmpty()) {
                return Stream$Empty$.MODULE$;
            }
            ObjectRef<Stream> flatMap_nonEmptyPrefix = new ObjectRef<Stream>(flatMap_this);
            IterableOnce iterableOnce = (IterableOnce)f.apply(((Stream)flatMap_nonEmptyPrefix.elem).head());
            Stream$ from_this = Stream$.MODULE$;
            Object var8_5 = null;
            iterableOnce = null;
            Object flatMap_prefix = from_this.from((IterableOnce)from_source);
            while (!((Stream)flatMap_nonEmptyPrefix.elem).isEmpty() && ((scala.collection.AbstractSeq)flatMap_prefix).isEmpty()) {
                void from_source2;
                flatMap_nonEmptyPrefix.elem = (Stream)((Stream)flatMap_nonEmptyPrefix.elem).tail();
                if (((Stream)flatMap_nonEmptyPrefix.elem).isEmpty()) continue;
                IterableOnce iterableOnce2 = (IterableOnce)f.apply(((Stream)flatMap_nonEmptyPrefix.elem).head());
                Stream$ from_this2 = Stream$.MODULE$;
                Object var10_7 = null;
                iterableOnce2 = null;
                flatMap_prefix = from_this2.from((IterableOnce)from_source2);
            }
            if (((Stream)flatMap_nonEmptyPrefix.elem).isEmpty()) {
                return Stream$Empty$.MODULE$;
            }
            Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$flatMap$1(flatMap_nonEmptyPrefix, f);
            if (flatMap_prefix == null) {
                throw null;
            }
            if (((scala.collection.AbstractSeq)flatMap_prefix).isEmpty()) {
                return Stream$.MODULE$.from((IterableOnce)Stream.$anonfun$flatMap$1(flatMap_nonEmptyPrefix, f));
            }
            Function0<Stream> & Serializable intersect2 = () -> Stream.$anonfun$lazyAppendedAll$1((Stream)flatMap_prefix, (Function0)flatMap_lazyAppendedAll_suffix);
            Object flatMap_lazyAppendedAll_apply_hd = ((AbstractIterable)flatMap_prefix).head();
            return new Cons(flatMap_lazyAppendedAll_apply_hd, flatMap_lazyAppendedAll_apply_tl);
        };
        if (prefix == null) {
            throw null;
        }
        if (((scala.collection.AbstractSeq)prefix).isEmpty()) {
            Object var15_18;
            Function0<Stream> & Serializable intersect2;
            Function0<Stream> & Serializable intersect3;
            Object var19_14;
            IterableOnce<B> iterableOnce3;
            Object var17_12;
            IterableOnce<B> iterableOnce4;
            Object object;
            Stream stream = (Stream)((Stream)nonEmptyPrefix.elem).tail();
            if (stream == null) {
                throw null;
            }
            Stream $anonfun$flatMap$1_flatMap_this = stream;
            if ($anonfun$flatMap$1_flatMap_this.isEmpty()) {
                object = Stream$Empty$.MODULE$;
            } else {
                void $anonfun$flatMap$1_from_source;
                ObjectRef<Stream> $anonfun$flatMap$1_flatMap_nonEmptyPrefix = new ObjectRef<Stream>($anonfun$flatMap$1_flatMap_this);
                iterableOnce4 = f.apply(((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).head());
                Stream$ $anonfun$flatMap$1_from_this = Stream$.MODULE$;
                var17_12 = null;
                iterableOnce4 = null;
                Object $anonfun$flatMap$1_flatMap_prefix = $anonfun$flatMap$1_from_this.from((IterableOnce)$anonfun$flatMap$1_from_source);
                while (!((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).isEmpty() && ((scala.collection.AbstractSeq)$anonfun$flatMap$1_flatMap_prefix).isEmpty()) {
                    void $anonfun$flatMap$1_from_source2;
                    $anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem = (Stream)((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).tail();
                    if (((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).isEmpty()) continue;
                    iterableOnce3 = f.apply(((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).head());
                    Stream$ $anonfun$flatMap$1_from_this2 = Stream$.MODULE$;
                    var19_14 = null;
                    iterableOnce3 = null;
                    $anonfun$flatMap$1_flatMap_prefix = $anonfun$flatMap$1_from_this2.from((IterableOnce)$anonfun$flatMap$1_from_source2);
                }
                if (((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).isEmpty()) {
                    object = Stream$Empty$.MODULE$;
                } else {
                    intersect3 = (Function0<Stream> & Serializable)() -> {
                        void flatMap_lazyAppendedAll_apply_tl;
                        void flatMap_lazyAppendedAll_suffix;
                        void from_source;
                        Stream stream = (Stream)((Stream)nonEmptyPrefix$1.elem).tail();
                        if (stream == null) {
                            throw null;
                        }
                        Stream flatMap_this = stream;
                        if (flatMap_this.isEmpty()) {
                            return Stream$Empty$.MODULE$;
                        }
                        ObjectRef<Stream> flatMap_nonEmptyPrefix = new ObjectRef<Stream>(flatMap_this);
                        IterableOnce iterableOnce = (IterableOnce)f.apply(((Stream)flatMap_nonEmptyPrefix.elem).head());
                        Stream$ from_this = Stream$.MODULE$;
                        Object var8_5 = null;
                        iterableOnce = null;
                        Object flatMap_prefix = from_this.from((IterableOnce)from_source);
                        while (!((Stream)flatMap_nonEmptyPrefix.elem).isEmpty() && ((scala.collection.AbstractSeq)flatMap_prefix).isEmpty()) {
                            void from_source2;
                            flatMap_nonEmptyPrefix.elem = (Stream)((Stream)flatMap_nonEmptyPrefix.elem).tail();
                            if (((Stream)flatMap_nonEmptyPrefix.elem).isEmpty()) continue;
                            IterableOnce iterableOnce2 = (IterableOnce)f.apply(((Stream)flatMap_nonEmptyPrefix.elem).head());
                            Stream$ from_this2 = Stream$.MODULE$;
                            Object var10_7 = null;
                            iterableOnce2 = null;
                            flatMap_prefix = from_this2.from((IterableOnce)from_source2);
                        }
                        if (((Stream)flatMap_nonEmptyPrefix.elem).isEmpty()) {
                            return Stream$Empty$.MODULE$;
                        }
                        Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$flatMap$1(flatMap_nonEmptyPrefix, f);
                        if (flatMap_prefix == null) {
                            throw null;
                        }
                        if (((scala.collection.AbstractSeq)flatMap_prefix).isEmpty()) {
                            return Stream$.MODULE$.from((IterableOnce)Stream.$anonfun$flatMap$1(flatMap_nonEmptyPrefix, f));
                        }
                        Function0<Stream> & Serializable intersect2 = () -> Stream.$anonfun$lazyAppendedAll$1((Stream)flatMap_prefix, (Function0)flatMap_lazyAppendedAll_suffix);
                        Object flatMap_lazyAppendedAll_apply_hd = ((AbstractIterable)flatMap_prefix).head();
                        return new Cons(flatMap_lazyAppendedAll_apply_hd, flatMap_lazyAppendedAll_apply_tl);
                    };
                    if ($anonfun$flatMap$1_flatMap_prefix == null) {
                        throw null;
                    }
                    if (((scala.collection.AbstractSeq)$anonfun$flatMap$1_flatMap_prefix).isEmpty()) {
                        object = Stream$.MODULE$.from((IterableOnce)Stream.$anonfun$flatMap$1($anonfun$flatMap$1_flatMap_nonEmptyPrefix, f));
                    } else {
                        void $anonfun$flatMap$1_flatMap_lazyAppendedAll_apply_tl;
                        void $anonfun$flatMap$1_flatMap_lazyAppendedAll_suffix;
                        intersect2 = () -> Stream.$anonfun$lazyAppendedAll$1((Stream)$anonfun$flatMap$1_flatMap_prefix, (Function0)$anonfun$flatMap$1_flatMap_lazyAppendedAll_suffix);
                        Object $anonfun$flatMap$1_flatMap_lazyAppendedAll_apply_hd = ((AbstractIterable)$anonfun$flatMap$1_flatMap_prefix).head();
                        object = new Cons($anonfun$flatMap$1_flatMap_lazyAppendedAll_apply_hd, $anonfun$flatMap$1_flatMap_lazyAppendedAll_apply_tl);
                        var15_18 = null;
                        intersect2 = null;
                    }
                    intersect3 = null;
                    var15_18 = null;
                    intersect2 = null;
                }
            }
            Object var11_9 = null;
            Object var12_10 = null;
            Object var13_13 = null;
            intersect3 = null;
            var15_18 = null;
            intersect2 = null;
            var17_12 = null;
            iterableOnce4 = null;
            var19_14 = null;
            iterableOnce3 = null;
            return Stream$.MODULE$.from((IterableOnce)object);
        }
        Function0<Stream> & Serializable intersect4 = () -> Stream.$anonfun$lazyAppendedAll$1((Stream)prefix, (Function0)lazyAppendedAll_suffix);
        Object lazyAppendedAll_apply_hd = ((AbstractIterable)prefix).head();
        return new Cons(lazyAppendedAll_apply_hd, lazyAppendedAll_apply_tl);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public final <B> Stream<Tuple2<A, B>> zip(IterableOnce<B> that) {
        void apply_tl;
        block3: {
            block2: {
                if (this.isEmpty()) break block2;
                if (!IterableOnceExtensionMethods$.MODULE$.isEmpty$extension(that)) break block3;
            }
            return Stream$Empty$.MODULE$;
        }
        Object thatIterable = that instanceof Iterable ? (Iterable)that : LazyList$.MODULE$.from((IterableOnce)that);
        Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$zip$1(this, (Iterable)thatIterable);
        Tuple2 apply_hd = new Tuple2(this.head(), thatIterable.head());
        return new Cons<Tuple2<A, B>>(apply_hd, apply_tl);
    }

    @Override
    public final Stream<Tuple2<A, Object>> zipWithIndex() {
        return this.zip(LazyList$.MODULE$.from(0, 1));
    }

    public abstract boolean tailDefined();

    @Override
    public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
        this.force();
        this.addStringNoForce(sb.underlying(), start2, sep, end);
        return sb;
    }

    private java.lang.StringBuilder addStringNoForce(java.lang.StringBuilder b, String start2, String sep, String end) {
        b.append(start2);
        if (this.nonEmpty()) {
            b.append(this.head());
            Stream stream = this;
            if (this.tailDefined()) {
                Stream scout = (Stream)this.tail();
                if (this != scout) {
                    stream = scout;
                    if (scout.tailDefined()) {
                        scout = (Stream)scout.tail();
                        while (stream != scout && scout.tailDefined()) {
                            b.append(sep).append(stream.head());
                            stream = (Stream)stream.tail();
                            if (!(scout = (Stream)scout.tail()).tailDefined()) continue;
                            scout = (Stream)scout.tail();
                        }
                    }
                }
                if (!scout.tailDefined()) {
                    while (stream != scout) {
                        b.append(sep).append(stream.head());
                        stream = (Stream)stream.tail();
                    }
                    if (stream.nonEmpty()) {
                        b.append(sep).append(stream.head());
                    }
                } else {
                    Stream runner = this;
                    int k = 0;
                    while (runner != scout) {
                        runner = (Stream)runner.tail();
                        scout = (Stream)scout.tail();
                        ++k;
                    }
                    if (stream == scout && k > 0) {
                        b.append(sep).append(stream.head());
                        stream = (Stream)stream.tail();
                    }
                    while (stream != scout) {
                        b.append(sep).append(stream.head());
                        stream = (Stream)stream.tail();
                    }
                }
            }
            if (stream.nonEmpty()) {
                if (!stream.tailDefined()) {
                    b.append(sep).append("<not computed>");
                } else {
                    b.append(sep).append("<cycle>");
                }
            }
        }
        return b.append(end);
    }

    @Override
    public String toString() {
        return this.addStringNoForce(new java.lang.StringBuilder("Stream"), "(", ", ", ")").toString();
    }

    @Override
    public boolean hasDefiniteSize() {
        if (!this.isEmpty()) {
            if (this.tailDefined()) {
                Stream these = (Stream)this.tail();
                for (Stream those = this; those != these; those = (Stream)those.tail()) {
                    if (these.isEmpty()) {
                        return true;
                    }
                    if (!these.tailDefined()) {
                        return false;
                    }
                    if ((these = (Stream)these.tail()).isEmpty()) {
                        return true;
                    }
                    if (!these.tailDefined()) {
                        return false;
                    }
                    if (those != (these = (Stream)these.tail())) continue;
                    return false;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$partition$1(Function1 p$1, Object x$1) {
        return BoxesRunTime.unboxToBoolean(p$1.apply(x$1));
    }

    public static final /* synthetic */ boolean $anonfun$partition$2(Function1 p$1, Object x$2) {
        return BoxesRunTime.unboxToBoolean(p$1.apply(x$2));
    }

    public static final /* synthetic */ Stream $anonfun$zip$1(Stream $this, Iterable thatIterable$1) {
        return ((Stream)$this.tail()).zip((IterableOnce)thatIterable$1.tail());
    }

    private static final void appendCursorElement$1(java.lang.StringBuilder b$1, String sep$1, ObjectRef cursor$1) {
        b$1.append(sep$1).append(((Stream)cursor$1.elem).head());
    }

    public static final class Cons<A>
    extends Stream<A> {
        private static final long serialVersionUID = 3L;
        private final A head;
        private volatile Stream<A> tlVal;
        private volatile Function0<Stream<A>> tlGen;

        @Override
        public A head() {
            return this.head;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean tailDefined() {
            return this.tlGen == null;
        }

        @Override
        public Stream<A> tail() {
            if (!this.tailDefined()) {
                synchronized (this) {
                    if (!this.tailDefined()) {
                        this.tlVal = this.tlGen.apply();
                        this.tlGen = null;
                    }
                }
            }
            return this.tlVal;
        }

        @Override
        public Cons<A> force() {
            scala.collection.LinearSeq these = this.tail();
            for (Stream those = this; those != these; those = (Stream)((AbstractIterable)those).tail()) {
                if (((scala.collection.AbstractSeq)((Object)these)).isEmpty()) {
                    return this;
                }
                if (((scala.collection.AbstractSeq)((Object)(these = (Stream)((AbstractIterable)((Object)these)).tail()))).isEmpty()) {
                    return this;
                }
                if ((these = (Stream)((AbstractIterable)((Object)these)).tail()) != those) continue;
                return this;
            }
            return this;
        }

        public Cons(A head, Function0<Stream<A>> tl) {
            this.head = head;
            this.tlGen = tl;
        }
    }

    public static final class Deferrer<A> {
        private final Function0<Stream<A>> scala$collection$immutable$Stream$Deferrer$$l;

        public Function0<Stream<A>> scala$collection$immutable$Stream$Deferrer$$l() {
            return this.scala$collection$immutable$Stream$Deferrer$$l;
        }

        public <B> Stream<B> $hash$colon$colon(B elem) {
            Function0<Stream<A>> $hash$colon$colon$extension_$this = this.scala$collection$immutable$Stream$Deferrer$$l();
            return new Cons<B>(elem, $hash$colon$colon$extension_$this);
        }

        /*
         * WARNING - void declaration
         */
        public <B> Stream<B> $hash$colon$colon$colon(Stream<B> prefix) {
            void $hash$colon$colon$colon$extension_lazyAppendedAll_apply_tl;
            Function0<Stream<A>> $hash$colon$colon$colon$extension_$this = this.scala$collection$immutable$Stream$Deferrer$$l();
            if (prefix == null) {
                throw null;
            }
            if (prefix.isEmpty()) {
                return Stream$.MODULE$.from((IterableOnce)$hash$colon$colon$colon$extension_$this.apply());
            }
            Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$lazyAppendedAll$1(prefix, $hash$colon$colon$colon$extension_$this);
            Object $hash$colon$colon$colon$extension_lazyAppendedAll_apply_hd = prefix.head();
            return new Cons($hash$colon$colon$colon$extension_lazyAppendedAll_apply_hd, $hash$colon$colon$colon$extension_lazyAppendedAll_apply_tl);
        }

        public int hashCode() {
            return this.scala$collection$immutable$Stream$Deferrer$$l().hashCode();
        }

        public boolean equals(Object x$1) {
            return Stream$Deferrer$.MODULE$.equals$extension(this.scala$collection$immutable$Stream$Deferrer$$l(), x$1);
        }

        public Deferrer(Function0<Stream<A>> l) {
            this.scala$collection$immutable$Stream$Deferrer$$l = l;
        }
    }

    public static class SerializationProxy<A>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private transient Stream<A> coll;

        public Stream<A> coll() {
            return this.coll;
        }

        public void coll_$eq(Stream<A> x$1) {
            this.coll = x$1;
        }

        private void writeObject(ObjectOutputStream out) {
            out.defaultWriteObject();
            Stream these = this.coll();
            while (these.nonEmpty() && these.tailDefined()) {
                out.writeObject(these.head());
                these = (Stream)these.tail();
            }
            out.writeObject(SerializeEnd$.MODULE$);
            out.writeObject(these);
        }

        private void readObject(ObjectInputStream in) {
            in.defaultReadObject();
            ArrayBuffer<Object> init2 = new ArrayBuffer<Object>();
            boolean initRead = false;
            while (!initRead) {
                Object object = in.readObject();
                if (SerializeEnd$.MODULE$.equals(object)) {
                    initRead = true;
                    continue;
                }
                init2.addOne(object);
            }
            Stream tail = (Stream)in.readObject();
            if (tail == null) {
                throw null;
            }
            this.coll_$eq((Stream)tail.prependedAll(init2));
        }

        public Object readResolve() {
            return this.coll();
        }

        public SerializationProxy(Stream<A> coll) {
            this.coll = coll;
        }
    }

    private static final class WithFilter<A>
    extends scala.collection.WithFilter<A, Stream> {
        private Stream<A> filtered;
        private final Function1<A, Object> p;
        private Stream<A> s;
        private volatile boolean bitmap$0;

        /*
         * WARNING - void declaration
         */
        private Stream<A> filtered$lzycompute() {
            synchronized (this) {
                if (!this.bitmap$0) {
                    void var1_7;
                    Object var6_6;
                    Stream stream;
                    Function0<Stream> & Serializable intersect;
                    void filter_pred;
                    Stream stream2 = this.s;
                    Function1<A, Object> function1 = this.p;
                    if (stream2 == null) {
                        throw null;
                    }
                    Stream filter_this = stream2;
                    boolean filter_filterImpl_isFlipped = false;
                    Stream filter_filterImpl_rest = filter_this;
                    while (filter_filterImpl_rest.nonEmpty() && BoxesRunTime.unboxToBoolean(filter_pred.apply(filter_filterImpl_rest.head())) == filter_filterImpl_isFlipped) {
                        filter_filterImpl_rest = (Stream)filter_filterImpl_rest.tail();
                    }
                    if (filter_filterImpl_rest.nonEmpty()) {
                        void filter_filterImpl_filteredTail_apply_tl;
                        intersect = () -> Stream$.$anonfun$filteredTail$1(filter_filterImpl_rest, (Function1)filter_pred, filter_filterImpl_isFlipped);
                        Object filter_filterImpl_filteredTail_apply_hd = filter_filterImpl_rest.head();
                        stream = new Cons(filter_filterImpl_filteredTail_apply_hd, filter_filterImpl_filteredTail_apply_tl);
                        var6_6 = null;
                        intersect = null;
                    } else {
                        stream = Stream$Empty$.MODULE$;
                    }
                    Object var5_4 = null;
                    var6_6 = null;
                    intersect = null;
                    Object var2_2 = null;
                    function1 = null;
                    Stream f = stream;
                    this.s = null;
                    this.filtered = var1_7;
                    this.bitmap$0 = true;
                }
            }
            return this.filtered;
        }

        private Stream<A> filtered() {
            if (!this.bitmap$0) {
                return this.filtered$lzycompute();
            }
            return this.filtered;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public <B> Stream<B> map(Function1<A, B> f) {
            void map_apply_tl;
            Stream<A> stream = this.filtered();
            if (stream == null) {
                throw null;
            }
            Stream<A> map_this = stream;
            if (map_this.isEmpty()) {
                return Stream$Empty$.MODULE$;
            }
            Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$map$1(map_this, f);
            B map_apply_hd = f.apply(map_this.head());
            return new Cons<B>(map_apply_hd, map_apply_tl);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public <B> Stream<B> flatMap(Function1<A, IterableOnce<B>> f) {
            void flatMap_lazyAppendedAll_apply_tl;
            void flatMap_lazyAppendedAll_suffix;
            void from_source;
            Stream<A> stream = this.filtered();
            if (stream == null) {
                throw null;
            }
            Stream<A> flatMap_this = stream;
            if (flatMap_this.isEmpty()) {
                return Stream$Empty$.MODULE$;
            }
            ObjectRef<Stream<A>> flatMap_nonEmptyPrefix = new ObjectRef<Stream<A>>(flatMap_this);
            IterableOnce<B> iterableOnce = f.apply(((Stream)flatMap_nonEmptyPrefix.elem).head());
            Stream$ from_this = Stream$.MODULE$;
            Object var8_5 = null;
            iterableOnce = null;
            Object flatMap_prefix = from_this.from((IterableOnce)from_source);
            while (!((Stream)flatMap_nonEmptyPrefix.elem).isEmpty() && ((scala.collection.AbstractSeq)flatMap_prefix).isEmpty()) {
                void from_source2;
                flatMap_nonEmptyPrefix.elem = (Stream)((Stream)flatMap_nonEmptyPrefix.elem).tail();
                if (((Stream)flatMap_nonEmptyPrefix.elem).isEmpty()) continue;
                IterableOnce<B> iterableOnce2 = f.apply(((Stream)flatMap_nonEmptyPrefix.elem).head());
                Stream$ from_this2 = Stream$.MODULE$;
                Object var10_7 = null;
                iterableOnce2 = null;
                flatMap_prefix = from_this2.from((IterableOnce)from_source2);
            }
            if (((Stream)flatMap_nonEmptyPrefix.elem).isEmpty()) {
                return Stream$Empty$.MODULE$;
            }
            Function0<Stream> & Serializable intersect = () -> Stream.$anonfun$flatMap$1(flatMap_nonEmptyPrefix, f);
            if (flatMap_prefix == null) {
                throw null;
            }
            if (((scala.collection.AbstractSeq)flatMap_prefix).isEmpty()) {
                Object var16_19;
                Function0<Stream> & Serializable intersect2;
                Function0<Stream> & Serializable intersect3;
                Object var20_15;
                IterableOnce<B> iterableOnce3;
                Object var18_13;
                IterableOnce<B> iterableOnce4;
                Object object;
                Stream stream2 = (Stream)((Stream)flatMap_nonEmptyPrefix.elem).tail();
                if (stream2 == null) {
                    throw null;
                }
                Stream $anonfun$flatMap$1_flatMap_this = stream2;
                if ($anonfun$flatMap$1_flatMap_this.isEmpty()) {
                    object = Stream$Empty$.MODULE$;
                } else {
                    void $anonfun$flatMap$1_from_source;
                    ObjectRef<Stream> $anonfun$flatMap$1_flatMap_nonEmptyPrefix = new ObjectRef<Stream>($anonfun$flatMap$1_flatMap_this);
                    iterableOnce4 = f.apply(((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).head());
                    Stream$ $anonfun$flatMap$1_from_this = Stream$.MODULE$;
                    var18_13 = null;
                    iterableOnce4 = null;
                    Object $anonfun$flatMap$1_flatMap_prefix = $anonfun$flatMap$1_from_this.from((IterableOnce)$anonfun$flatMap$1_from_source);
                    while (!((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).isEmpty() && ((scala.collection.AbstractSeq)$anonfun$flatMap$1_flatMap_prefix).isEmpty()) {
                        void $anonfun$flatMap$1_from_source2;
                        $anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem = (Stream)((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).tail();
                        if (((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).isEmpty()) continue;
                        iterableOnce3 = f.apply(((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).head());
                        Stream$ $anonfun$flatMap$1_from_this2 = Stream$.MODULE$;
                        var20_15 = null;
                        iterableOnce3 = null;
                        $anonfun$flatMap$1_flatMap_prefix = $anonfun$flatMap$1_from_this2.from((IterableOnce)$anonfun$flatMap$1_from_source2);
                    }
                    if (((Stream)$anonfun$flatMap$1_flatMap_nonEmptyPrefix.elem).isEmpty()) {
                        object = Stream$Empty$.MODULE$;
                    } else {
                        intersect3 = () -> Stream.$anonfun$flatMap$1($anonfun$flatMap$1_flatMap_nonEmptyPrefix, f);
                        if ($anonfun$flatMap$1_flatMap_prefix == null) {
                            throw null;
                        }
                        if (((scala.collection.AbstractSeq)$anonfun$flatMap$1_flatMap_prefix).isEmpty()) {
                            object = Stream$.MODULE$.from((IterableOnce)Stream.$anonfun$flatMap$1($anonfun$flatMap$1_flatMap_nonEmptyPrefix, f));
                        } else {
                            void $anonfun$flatMap$1_flatMap_lazyAppendedAll_apply_tl;
                            void $anonfun$flatMap$1_flatMap_lazyAppendedAll_suffix;
                            intersect2 = () -> Stream.$anonfun$lazyAppendedAll$1((Stream)$anonfun$flatMap$1_flatMap_prefix, (Function0)$anonfun$flatMap$1_flatMap_lazyAppendedAll_suffix);
                            Object $anonfun$flatMap$1_flatMap_lazyAppendedAll_apply_hd = ((AbstractIterable)$anonfun$flatMap$1_flatMap_prefix).head();
                            object = new Cons($anonfun$flatMap$1_flatMap_lazyAppendedAll_apply_hd, $anonfun$flatMap$1_flatMap_lazyAppendedAll_apply_tl);
                            var16_19 = null;
                            intersect2 = null;
                        }
                        intersect3 = null;
                        var16_19 = null;
                        intersect2 = null;
                    }
                }
                Object var12_10 = null;
                Object var13_11 = null;
                Object var14_14 = null;
                intersect3 = null;
                var16_19 = null;
                intersect2 = null;
                var18_13 = null;
                iterableOnce4 = null;
                var20_15 = null;
                iterableOnce3 = null;
                return Stream$.MODULE$.from((IterableOnce)object);
            }
            Function0<Stream> & Serializable intersect4 = () -> Stream.$anonfun$lazyAppendedAll$1((Stream)flatMap_prefix, (Function0)flatMap_lazyAppendedAll_suffix);
            Object flatMap_lazyAppendedAll_apply_hd = ((AbstractIterable)flatMap_prefix).head();
            return new Cons(flatMap_lazyAppendedAll_apply_hd, flatMap_lazyAppendedAll_apply_tl);
        }

        @Override
        public <U> void foreach(Function1<A, U> f) {
            Stream stream = this.filtered();
            if (stream == null) {
                throw null;
            }
            Stream foreach_this = stream;
            while (!foreach_this.isEmpty()) {
                f.apply(foreach_this.head());
                foreach_this = (Stream)foreach_this.tail();
            }
        }

        @Override
        public scala.collection.WithFilter<A, Stream> withFilter(Function1<A, Object> q) {
            return new WithFilter<A>(this.filtered(), q);
        }

        public WithFilter(Stream<A> l, Function1<A, Object> p) {
            this.p = p;
            this.s = l;
        }
    }
}

