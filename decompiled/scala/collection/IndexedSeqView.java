/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.collection.AbstractIndexedSeqView;
import scala.collection.AbstractIterator;
import scala.collection.IndexedSeqOps;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Searching;
import scala.collection.SeqView;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0011\u0005daB1c!\u0003\r\ta\u001a\u0005\b\u0003\u000f\u0001A\u0011AA\u0005\u0011\u001d\t\t\u0002\u0001C!\u0003'Aq!!\u0005\u0001\t\u0003\n9\u0002C\u0004\u0002<\u0001!\t%!\u0010\t\u000f\u0005\u0015\u0003\u0001\"\u0011\u0002>!9\u0011q\t\u0001\u0005B\u0005%\u0003bBA.\u0001\u0011\u0005\u0013Q\f\u0005\b\u0003S\u0002A\u0011IA6\u0011\u001d\t\t\b\u0001C!\u0003gBq!a\u001e\u0001\t\u0003\nI\bC\u0004\u0002~\u0001!\t%a \t\u000f\u0005\r\u0005\u0001\"\u0011\u0002\u0006\"9\u0011\u0011\u0014\u0001\u0005B\u0005M\u0001bBAN\u0001\u0011\u0005\u0013Q\u0014\u0005\b\u0003G\u0003A\u0011IAS\u0011\u001d\t\u0019\f\u0001C\u0001\u0003kCq\u0001b\u0010\u0001\t\u0003!\t\u0005C\u0004\u0005P\u0001!\t\u0001\"\u0015\t\u0011\u0011}\u0003\u0001)C)\t\u00139q!a2c\u0011\u0003\tIM\u0002\u0004bE\"\u0005\u00111\u001a\u0005\b\u0003;,B\u0011AAp\r\u001d\t\t/\u0006\u0001c\u0003GD!\"!@\u0018\u0005\u0003\u0005\u000b\u0011BA\u0000\u0011\u001d\tin\u0006C\u0001\u0005\u0003A\u0001B!\u0003\u0018A\u0003&\u0011Q\u0004\u0005\u000f\u0005\u00179B\u0011!A\u0003\u0002\u0003\u0005\u000b\u0015BA\u000f\u0011\u001d\u0011ia\u0006C!\u0005\u001fA\u0001B!\u0005\u0018A\u0013%!1\u0003\u0005\b\u0005G9B\u0011\u0001B\n\u0011\u001d\u0011)c\u0006C\u0001\u0005OAq!a\u001e\u0018\t\u0003\u0012I\u0003C\u0004\u00030]!\tF!\r\u0007\u000f\t\rS\u0003\u00012\u0003F!Q\u0011Q \u0012\u0003\u0002\u0003\u0006IAa\u0014\t\u000f\u0005u'\u0005\"\u0001\u0003R!q!q\u000b\u0012\u0005\u0002\u0003\u0015\t\u0011!Q!\n\u0005u\u0001\u0002\u0003B-E\u0001\u0006K!!\b\t\u0011\tE!\u0005)C\u0005\u0005'AqAa\t#\t\u0003\u0011\u0019\u0002C\u0004\u0003&\t\"\tA!\u0018\t\u000f\t=\"\u0005\"\u0015\u0003`\u00151!\u0011N\u000b\u0001\u0005W2aAa\"\u0016\u0001\t%\u0005B\u0003BPY\t\u0005\t\u0015!\u0003\u0003\"\"9\u0011Q\u001c\u0017\u0005\u0002\t\rfA\u0002BV+\u0001\u0011i\u000b\u0003\u0006\u0003 >\u0012\t\u0011)A\u0005\u0005wC!\"!\u00170\u0005\u0003\u0005\u000b\u0011\u0002B[\u0011\u001d\tin\fC\u0001\u0005{3aAa2\u0016\u0001\t%\u0007BCA-g\t\u0005\t\u0015!\u0003\u0003R\"Q!qT\u001a\u0003\u0002\u0003\u0006IAa6\t\u000f\u0005u7\u0007\"\u0001\u0003Z\u001a1!1]\u000b\u0001\u0005KD!Ba=8\u0005\u0003\u0005\u000b\u0011\u0002B{\u0011)\t\tm\u000eB\u0001B\u0003%!Q\u001f\u0005\b\u0003;<D\u0011\u0001B|\r\u0019\u0019\t!\u0006\u0001\u0004\u0004!Q!qT\u001e\u0003\u0002\u0003\u0006Ia!\u0005\t\u0015\u0005=4H!A!\u0002\u0013\ti\u0002C\u0004\u0002^n\"\taa\u0005\u0007\r\ruQ\u0003AB\u0010\u0011)\u0011yj\u0010B\u0001B\u0003%1Q\u0006\u0005\u000b\u0003_z$\u0011!Q\u0001\n\u0005u\u0001bBAo\u007f\u0011\u00051q\u0006\u0004\u0007\u0007s)\u0002aa\u000f\t\u0015\t}5I!A!\u0002\u0013\u0019I\u0005\u0003\u0006\u0002p\r\u0013\t\u0011)A\u0005\u0003;Aq!!8D\t\u0003\u0019YE\u0002\u0004\u0004VU\u00011q\u000b\u0005\u000b\u0005?;%\u0011!Q\u0001\n\r\u0015\u0004BCA8\u000f\n\u0005\t\u0015!\u0003\u0002\u001e!9\u0011Q\\$\u0005\u0002\r\u001ddABB9+\u0001\u0019\u0019\b\u0003\u0006\u0003 .\u0013\t\u0011)A\u0005\u0007\u000bC!\"!%L\u0005\u0003\u0005\u000b\u0011BBD\u0011\u001d\tin\u0013C\u0001\u0007\u00133aaa%\u0016\u0001\rU\u0005B\u0003BP\u001f\n\u0005\t\u0015!\u0003\u0004$\"9\u0011Q\\(\u0005\u0002\r\u0015\u0006bBAM\u001f\u0012\u000531\u0016\u0004\u0007\u0007_+\u0002a!-\t\u0015\t}5K!A!\u0002\u0013\u0019y\f\u0003\u0006\u0002\u001cM\u0013\t\u0011)A\u0005\u0003;A!\"!\nT\u0005\u0003\u0005\u000b\u0011BA\u000f\u0011\u001d\tin\u0015C\u0001\u0007\u0003D\u0011ba3T\u0005\u0004%\tBa\u0004\t\u0011\r57\u000b)A\u0005\u0003;A\u0011ba4T\u0005\u0004%\tBa\u0004\t\u0011\rE7\u000b)A\u0005\u0003;A\u0011ba5T\u0005\u0004%\tBa\u0004\t\u0011\rU7\u000b)A\u0005\u0003;Aqaa6T\t\u0003\u0019I\u000eC\u0004\u0005,M#\tAa\u0004\t\u0013\u0011=R#!A\u0005\n\u0011E\"AD%oI\u0016DX\rZ*fcZKWm\u001e\u0006\u0003G\u0012\f!bY8mY\u0016\u001cG/[8o\u0015\u0005)\u0017!B:dC2\f7\u0001A\u000b\u0003QN\u001cR\u0001A5n\u0003\u0003\u0001\"A[6\u000e\u0003\u0011L!\u0001\u001c3\u0003\r\u0005s\u0017PU3g!\u0015qw.\u001d?\u0000\u001b\u0005\u0011\u0017B\u00019c\u00055Ie\u000eZ3yK\u0012\u001cV-](qgB\u0011!o\u001d\u0007\u0001\t\u0019!\b\u0001\"b\u0001k\n\t\u0011)\u0005\u0002wsB\u0011!n^\u0005\u0003q\u0012\u0014qAT8uQ&tw\r\u0005\u0002ku&\u00111\u0010\u001a\u0002\u0004\u0003:L\bC\u00018~\u0013\tq(M\u0001\u0003WS\u0016<\bc\u00018~cB!a.a\u0001r\u0013\r\t)A\u0019\u0002\b'\u0016\fh+[3x\u0003\u0019!\u0013N\\5uIQ\u0011\u00111\u0002\t\u0004U\u00065\u0011bAA\bI\n!QK\\5u\u0003\u00111\u0018.Z<\u0016\u0005\u0005U\u0001c\u00018\u0001cR1\u0011QCA\r\u0003GAq!a\u0007\u0004\u0001\u0004\ti\"\u0001\u0003ge>l\u0007c\u00016\u0002 %\u0019\u0011\u0011\u00053\u0003\u0007%sG\u000fC\u0004\u0002&\r\u0001\r!!\b\u0002\u000bUtG/\u001b7)\u0017\r\tI#a\f\u00022\u0005U\u0012q\u0007\t\u0004U\u0006-\u0012bAA\u0017I\nQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012\u00111G\u0001;+N,\u0007E\f<jK^t3\u000f\\5dK\"2'o\\7-AUtG/\u001b7*A%t7\u000f^3bI\u0002zg\r\t\u0018wS\u0016<\bF\u001a:p[2\u0002SO\u001c;jY&\nQa]5oG\u0016\f#!!\u000f\u0002\rIr\u0013g\r\u00181\u0003!IG/\u001a:bi>\u0014XCAA !\u0011q\u0017\u0011I9\n\u0007\u0005\r#M\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003=\u0011XM^3sg\u0016LE/\u001a:bi>\u0014\u0018\u0001C1qa\u0016tG-\u001a3\u0016\t\u0005-\u0013\u0011\u000b\u000b\u0005\u0003\u001b\n9\u0006\u0005\u0003o\u0001\u0005=\u0003c\u0001:\u0002R\u00119\u00111\u000b\u0004C\u0002\u0005U#!\u0001\"\u0012\u0005EL\bbBA-\r\u0001\u0007\u0011qJ\u0001\u0005K2,W.A\u0005qe\u0016\u0004XM\u001c3fIV!\u0011qLA3)\u0011\t\t'a\u001a\u0011\t9\u0004\u00111\r\t\u0004e\u0006\u0015DaBA*\u000f\t\u0007\u0011Q\u000b\u0005\b\u00033:\u0001\u0019AA2\u0003\u0011!\u0018m[3\u0015\t\u0005U\u0011Q\u000e\u0005\b\u0003_B\u0001\u0019AA\u000f\u0003\u0005q\u0017!\u0003;bW\u0016\u0014\u0016n\u001a5u)\u0011\t)\"!\u001e\t\u000f\u0005=\u0014\u00021\u0001\u0002\u001e\u0005!AM]8q)\u0011\t)\"a\u001f\t\u000f\u0005=$\u00021\u0001\u0002\u001e\u0005IAM]8q%&<\u0007\u000e\u001e\u000b\u0005\u0003+\t\t\tC\u0004\u0002p-\u0001\r!!\b\u0002\u00075\f\u0007/\u0006\u0003\u0002\b\u00065E\u0003BAE\u0003\u001f\u0003BA\u001c\u0001\u0002\fB\u0019!/!$\u0005\r\u0005MCB1\u0001v\u0011\u001d\t\t\n\u0004a\u0001\u0003'\u000b\u0011A\u001a\t\u0007U\u0006U\u0015/a#\n\u0007\u0005]EMA\u0005Gk:\u001cG/[8oc\u00059!/\u001a<feN,\u0017!B:mS\u000e,GCBA\u000b\u0003?\u000b\t\u000bC\u0004\u0002\u001c9\u0001\r!!\b\t\u000f\u0005\u0015b\u00021\u0001\u0002\u001e\u00059A/\u00199FC\u000eDW\u0003BAT\u0003_#B!!\u0006\u0002*\"9\u0011\u0011S\bA\u0002\u0005-\u0006C\u00026\u0002\u0016F\fi\u000bE\u0002s\u0003_#a!!-\u0010\u0005\u0004)(!A+\u0002\r\r|gnY1u+\u0011\t9,!0\u0015\t\u0005e\u0016q\u0018\t\u0005]\u0002\tY\fE\u0002s\u0003{#q!a\u0015\u0011\u0005\u0004\t)\u0006C\u0004\u0002BB\u0001\r!a1\u0002\rM,hMZ5y!\u0015\t)mKA^\u001d\tqG#\u0001\bJ]\u0012,\u00070\u001a3TKF4\u0016.Z<\u0011\u00059,2\u0003B\u000bj\u0003\u001b\u0004B!a4\u0002Z6\u0011\u0011\u0011\u001b\u0006\u0005\u0003'\f).\u0001\u0002j_*\u0011\u0011q[\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002\\\u0006E'\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\b\u0006\u0002\u0002J\n1\u0012J\u001c3fq\u0016$7+Z9WS\u0016<\u0018\n^3sCR|'/\u0006\u0003\u0002f\u0006=8#B\f\u0002h\u0006E\b#\u00028\u0002j\u00065\u0018bAAvE\n\u0001\u0012IY:ue\u0006\u001cG/\u0013;fe\u0006$xN\u001d\t\u0004e\u0006=H!\u0002;\u0018\u0005\u0004)\b\u0003BAz\u0003st1A[A{\u0013\r\t9\u0010Z\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\tY.a?\u000b\u0007\u0005]H-\u0001\u0003tK24\u0007\u0003\u00028\u0001\u0003[$BAa\u0001\u0003\bA)!QA\f\u0002n6\tQ\u0003C\u0004\u0002~f\u0001\r!a@\u0002\u000f\r,(O]3oi\u0006\t5oY1mC\u0012\u001aw\u000e\u001c7fGRLwN\u001c\u0013J]\u0012,\u00070\u001a3TKF4\u0016.Z<%\u0013:$W\r_3e'\u0016\fh+[3x\u0013R,'/\u0019;pe\u0012\"#/Z7bS:$WM]\u0001\nW:|wO\\*ju\u0016,\"!!\b\u0002\u0011}C\u0017m\u001d(fqR,\"A!\u0006\u0011\u0007)\u00149\"C\u0002\u0003\u001a\u0011\u0014qAQ8pY\u0016\fg\u000eK\u0002\u001e\u0005;\u00012A\u001bB\u0010\u0013\r\u0011\t\u0003\u001a\u0002\u0007S:d\u0017N\\3\u0002\u000f!\f7OT3yi\u0006!a.\u001a=u)\t\ti\u000f\u0006\u0003\u0003,\t5\u0002#\u00028\u0002B\u00055\bbBA8A\u0001\u0007\u0011QD\u0001\u000eg2L7-Z%uKJ\fGo\u001c:\u0015\r\t-\"1\u0007B\u001b\u0011\u001d\tY\"\ta\u0001\u0003;Aq!!\n\"\u0001\u0004\ti\u0002K\u0004\u0018\u0005s\u0011yD!\u0011\u0011\u0007)\u0014Y$C\u0002\u0003>\u0011\u0014\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\r\u0011Q$\u00138eKb,GmU3r-&,wOU3wKJ\u001cX-\u0013;fe\u0006$xN]\u000b\u0005\u0005\u000f\u0012ieE\u0003#\u0005\u0013\n\t\u0010E\u0003o\u0003S\u0014Y\u0005E\u0002s\u0005\u001b\"Q\u0001\u001e\u0012C\u0002U\u0004BA\u001c\u0001\u0003LQ!!1\u000bB+!\u0015\u0011)A\tB&\u0011\u001d\ti\u0010\na\u0001\u0005\u001f\n\u0001j]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%\u0013:$W\r_3e'\u0016\fh+[3xI%sG-\u001a=fIN+\u0017OV5foJ+g/\u001a:tK&#XM]1u_J$CE]3nC&tG-\u001a:\u0002\u0007A|7\u000fK\u0002(\u0005;!\"Aa\u0013\u0015\r\t\u0005$1\rB3!\u0015q\u0017\u0011\tB&\u0011\u001d\tYB\u000ba\u0001\u0003;Aq!!\n+\u0001\u0004\ti\u0002K\u0004#\u0005s\u0011yD!\u0011\u0003#M{W.Z%oI\u0016DX\rZ*fc>\u00038/\u0006\u0003\u0003n\tM\u0004\u0007\u0002B8\u0005\u0007\u0003\u0002B\\8\u0003r\tU$\u0011\u0011\t\u0004e\nMD!\u0002;,\u0005\u0004)\b\u0003\u0002B<\u0005wr1A\u001cB=\u0013\r\t9PY\u0005\u0005\u0005{\u0012yHA\u0005B]f\u001cuN\\:ue*\u0019\u0011q\u001f2\u0011\u0007I\u0014\u0019\t\u0002\u0006\u0003\u0006.\n\t\u0011!A\u0003\u0002U\u00141a\u0018\u00132\u0005\tIE-\u0006\u0003\u0003\f\nm5#\u0002\u0017\u0003\u000e\nu\u0005C\u0002BH\u0005+\u0013IJD\u0002o\u0005#K1Aa%c\u0003\u001d\u0019V-\u001d,jK^LAAa\"\u0003\u0018*\u0019!1\u00132\u0011\u0007I\u0014Y\n\u0002\u0004uY\u0011\u0015\r!\u001e\t\u0005]\u0002\u0011I*\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\u0004RA!\u0002,\u00053#BA!*\u0003(B)!Q\u0001\u0017\u0003\u001a\"9!q\u0014\u0018A\u0002\t\u0005\u0006f\u0002\u0017\u0003:\t}\"\u0011\t\u0002\t\u0003B\u0004XM\u001c3fIV!!q\u0016B\\'\u0015y#\u0011\u0017B]!\u0019\u0011yIa-\u00036&!!1\u0016BL!\r\u0011(q\u0017\u0003\u0007i>\")\u0019A;\u0011\t9\u0004!Q\u0017\t\u0006\u0005\u000bY#Q\u0017\u000b\u0007\u0005\u007f\u0013\tMa1\u0011\u000b\t\u0015qF!.\t\u000f\t}%\u00071\u0001\u0003<\"9\u0011\u0011\f\u001aA\u0002\tU\u0006fB\u0018\u0003:\t}\"\u0011\t\u0002\n!J,\u0007/\u001a8eK\u0012,BAa3\u0003TN)1G!4\u0003VB1!q\u0012Bh\u0005#LAAa2\u0003\u0018B\u0019!Oa5\u0005\rQ\u001cDQ1\u0001v!\u0011q\u0007A!5\u0011\u000b\t\u00151F!5\u0015\r\tm'Q\u001cBp!\u0015\u0011)a\rBi\u0011\u001d\tIF\u000ea\u0001\u0005#DqAa(7\u0001\u0004\u00119\u000eK\u00044\u0005s\u0011yD!\u0011\u0003\r\r{gnY1u+\u0011\u00119Oa<\u0014\u000b]\u0012IO!=\u0011\r\t=%1\u001eBw\u0013\u0011\u0011\u0019Oa&\u0011\u0007I\u0014y\u000fB\u0003uo\t\u0007Q\u000f\u0005\u0003o\u0001\t5\u0018A\u00029sK\u001aL\u0007\u0010E\u0003\u0003\u0006-\u0012i\u000f\u0006\u0004\u0003z\nm(Q \t\u0006\u0005\u000b9$Q\u001e\u0005\b\u0005gT\u0004\u0019\u0001B{\u0011\u001d\t\tM\u000fa\u0001\u0005kDsa\u000eB\u001d\u0005\u007f\u0011\tE\u0001\u0003UC.,W\u0003BB\u0003\u0007\u001b\u0019RaOB\u0004\u0007\u001f\u0001bAa$\u0004\n\r-\u0011\u0002BB\u0001\u0005/\u00032A]B\u0007\t\u0015!8H1\u0001v!\u0011q\u0007aa\u0003\u0011\u000b\t\u00151fa\u0003\u0015\r\rU1qCB\r!\u0015\u0011)aOB\u0006\u0011\u001d\u0011yJ\u0010a\u0001\u0007#Aq!a\u001c?\u0001\u0004\ti\u0002K\u0004<\u0005s\u0011yD!\u0011\u0003\u0013Q\u000b7.\u001a*jO\"$X\u0003BB\u0011\u0007S\u0019RaPB\u0012\u0007W\u0001bAa$\u0004&\r\u001d\u0012\u0002BB\u000f\u0005/\u00032A]B\u0015\t\u0015!xH1\u0001v!\u0011q\u0007aa\n\u0011\u000b\t\u00151fa\n\u0015\r\rE21GB\u001b!\u0015\u0011)aPB\u0014\u0011\u001d\u0011yJ\u0011a\u0001\u0007[Aq!a\u001cC\u0001\u0004\ti\u0002K\u0004@\u0005s\u0011yD!\u0011\u0003\t\u0011\u0013x\u000e]\u000b\u0005\u0007{\u0019)eE\u0003D\u0007\u007f\u00199\u0005\u0005\u0004\u0003\u0010\u000e\u000531I\u0005\u0005\u0007s\u00119\nE\u0002s\u0007\u000b\"Q\u0001^\"C\u0002U\u0004BA\u001c\u0001\u0004DA)!QA\u0016\u0004DQ11QJB(\u0007#\u0002RA!\u0002D\u0007\u0007BqAa(G\u0001\u0004\u0019I\u0005C\u0004\u0002p\u0019\u0003\r!!\b)\u000f\r\u0013IDa\u0010\u0003B\tIAI]8q%&<\u0007\u000e^\u000b\u0005\u00073\u001a\tgE\u0003H\u00077\u001a\u0019\u0007\u0005\u0004\u0003\u0010\u000eu3qL\u0005\u0005\u0007+\u00129\nE\u0002s\u0007C\"Q\u0001^$C\u0002U\u0004BA\u001c\u0001\u0004`A)!QA\u0016\u0004`Q11\u0011NB6\u0007[\u0002RA!\u0002H\u0007?BqAa(K\u0001\u0004\u0019)\u0007C\u0004\u0002p)\u0003\r!!\b)\u000f\u001d\u0013IDa\u0010\u0003B\t\u0019Q*\u00199\u0016\r\rU4QPBA'\u0015Y5qOBB!!\u0011yi!\u001f\u0004|\r}\u0014\u0002BB9\u0005/\u00032A]B?\t\u0015!8J1\u0001v!\r\u00118\u0011\u0011\u0003\u0007\u0003'Z%\u0019A;\u0011\t9\u00041q\u0010\t\u0006\u0005\u000bY31\u0010\t\bU\u0006U51PB@)\u0019\u0019Yi!$\u0004\u0010B9!QA&\u0004|\r}\u0004b\u0002BP\u001d\u0002\u00071Q\u0011\u0005\b\u0003#s\u0005\u0019ABDQ\u001dY%\u0011\bB \u0005\u0003\u0012qAU3wKJ\u001cX-\u0006\u0003\u0004\u0018\u000e}5#B(\u0004\u001a\u000e\u0005\u0006C\u0002BH\u00077\u001bi*\u0003\u0003\u0004\u0014\n]\u0005c\u0001:\u0004 \u0012)Ao\u0014b\u0001kB!a\u000eABO!\u0015\u0011)aKBO)\u0011\u00199k!+\u0011\u000b\t\u0015qj!(\t\u000f\t}\u0015\u000b1\u0001\u0004$V\u00111\u0011\u0015\u0015\b\u001f\ne\"q\bB!\u0005\u0015\u0019F.[2f+\u0011\u0019\u0019l!0\u0014\u0007M\u001b)\fE\u0003o\u0007o\u001bY,C\u0002\u0004:\n\u0014a#\u00112tiJ\f7\r^%oI\u0016DX\rZ*fcZKWm\u001e\t\u0004e\u000euF!\u0002;T\u0005\u0004)\b#\u0002B\u0003W\rmF\u0003CBb\u0007\u000b\u001c9m!3\u0011\u000b\t\u00151ka/\t\u000f\t}u\u000b1\u0001\u0004@\"9\u00111D,A\u0002\u0005u\u0001bBA\u0013/\u0002\u0007\u0011QD\u0001\u0003Y>\f1\u0001\\8!\u0003\tA\u0017.A\u0002iS\u0002\n1\u0001\\3o\u0003\u0011aWM\u001c\u0011\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\rm61\u001c\u0005\b\u0007;t\u0006\u0019AA\u000f\u0003\u0005I\u0007&\u00020\u0004b\u000e5\b#\u00026\u0004d\u000e\u001d\u0018bABsI\n1A\u000f\u001b:poN\u0004B!a=\u0004j&!11^A~\u0005eIe\u000eZ3y\u001fV$xJ\u001a\"pk:$7/\u0012=dKB$\u0018n\u001c82\u000fy\u0019y\u000f\"\u0002\u0005*A!1\u0011_B\u0000\u001d\u0011\u0019\u0019pa?\u0011\u0007\rUH-\u0004\u0002\u0004x*\u00191\u0011 4\u0002\rq\u0012xn\u001c;?\u0013\r\u0019i\u0010Z\u0001\u0007!J,G-\u001a4\n\t\u0011\u0005A1\u0001\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\ruH-M\u0005$\t\u000f!y\u0001b\b\u0005\u0012U!A\u0011\u0002C\u0006+\t\u0019y\u000fB\u0004\u0005\u000e\u0019\u0014\r\u0001b\u0006\u0003\u0003QKA\u0001\"\u0005\u0005\u0014\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIER1\u0001\"\u0006e\u0003\u0019!\bN]8xgF\u0019a\u000f\"\u0007\u0011\t\u0005MH1D\u0005\u0005\t;\tYPA\u0005UQJ|w/\u00192mKFJ1\u0005\"\t\u0005$\u0011\u0015BQ\u0003\b\u0004U\u0012\r\u0012b\u0001C\u000bIF*!E\u001b3\u0005(\t)1oY1mCF\u001aaea:\u0002\r1,gn\u001a;iQ\u001d\u0019&\u0011\bB \u0005\u0003\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"\u0001b\r\u0011\t\u0011UB1H\u0007\u0003\toQA\u0001\"\u000f\u0002V\u0006!A.\u00198h\u0013\u0011!i\u0004b\u000e\u0003\r=\u0013'.Z2u\u0003-\t\u0007\u000f]3oI\u0016$\u0017\t\u001c7\u0016\t\u0011\rC\u0011\n\u000b\u0005\t\u000b\"Y\u0005\u0005\u0003o\u0001\u0011\u001d\u0003c\u0001:\u0005J\u00119\u00111K\tC\u0002\u0005U\u0003bBAa#\u0001\u0007AQ\n\t\u0006\u0003\u000b\\CqI\u0001\raJ,\u0007/\u001a8eK\u0012\fE\u000e\\\u000b\u0005\t'\"I\u0006\u0006\u0003\u0005V\u0011m\u0003\u0003\u00028\u0001\t/\u00022A\u001dC-\t\u001d\t\u0019F\u0005b\u0001\u0003+BqAa=\u0013\u0001\u0004!i\u0006E\u0003\u0002F.\"9&\u0001\u0007tiJLgn\u001a)sK\u001aL\u0007\u0010")
public interface IndexedSeqView<A>
extends IndexedSeqOps<A, View, View<A>>,
SeqView<A> {
    @Override
    default public IndexedSeqView<A> view() {
        return this;
    }

    @Override
    default public IndexedSeqView<A> view(int from, int until) {
        return this.view().slice(from, until);
    }

    @Override
    default public Iterator<A> iterator() {
        return new IndexedSeqViewIterator(this);
    }

    @Override
    default public Iterator<A> reverseIterator() {
        return new IndexedSeqViewReverseIterator(this);
    }

    @Override
    default public <B> IndexedSeqView<B> appended(B elem) {
        return new Appended<B>(this, elem);
    }

    @Override
    default public <B> IndexedSeqView<B> prepended(B elem) {
        return new Prepended<B>(elem, this);
    }

    @Override
    default public IndexedSeqView<A> take(int n) {
        return new Take(this, n);
    }

    @Override
    default public IndexedSeqView<A> takeRight(int n) {
        return new TakeRight(this, n);
    }

    @Override
    default public IndexedSeqView<A> drop(int n) {
        return new Drop(this, n);
    }

    @Override
    default public IndexedSeqView<A> dropRight(int n) {
        return new DropRight(this, n);
    }

    @Override
    default public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
        return new Map<A, B$>(this, f);
    }

    @Override
    default public IndexedSeqView<A> reverse() {
        return new Reverse(this);
    }

    @Override
    default public IndexedSeqView<A> slice(int from, int until) {
        return new Slice(this, from, until);
    }

    @Override
    default public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
        return new Map(this, (Function1<Object, Object> & Serializable)a -> {
            f.apply(a);
            return a;
        });
    }

    @Override
    default public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
        return new Concat<B>(this, suffix);
    }

    @Override
    default public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
        return new Concat<B>(this, suffix);
    }

    @Override
    default public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
        return new Concat<B>(prefix, this);
    }

    @Override
    default public String stringPrefix() {
        return "IndexedSeqView";
    }

    public static void $init$(IndexedSeqView $this) {
    }

    public static class Appended<A>
    extends SeqView.Appended<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public Appended(IndexedSeqOps<A, ?, Object> underlying, A elem) {
            super(underlying, elem);
        }
    }

    public static class Concat<A>
    extends SeqView.Concat<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public Concat(IndexedSeqOps<A, ?, Object> prefix, IndexedSeqOps<A, ?, Object> suffix) {
            super(prefix, suffix);
        }
    }

    public static class Drop<A>
    extends SeqView.Drop<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public Drop(IndexedSeqOps<A, ?, Object> underlying, int n) {
            super(underlying, n);
        }
    }

    public static class DropRight<A>
    extends SeqView.DropRight<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public DropRight(IndexedSeqOps<A, ?, Object> underlying, int n) {
            super(underlying, n);
        }
    }

    public static class Id<A>
    extends SeqView.Id<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public Id(IndexedSeqOps<A, ?, Object> underlying) {
            super(underlying);
        }
    }

    public static class IndexedSeqViewIterator<A>
    extends AbstractIterator<A>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final IndexedSeqView<A> self;
        private int current;
        public int scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder;

        @Override
        public int knownSize() {
            return this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder;
        }

        private boolean _hasNext() {
            return this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder > 0;
        }

        @Override
        public boolean hasNext() {
            return this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder > 0;
        }

        @Override
        public A next() {
            if (this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder > 0) {
                Object r = this.self.apply(this.current);
                ++this.current;
                --this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder;
                return r;
            }
            return (A)Iterator$.scala$collection$Iterator$$_empty.next();
        }

        @Override
        public Iterator<A> drop(int n) {
            if (n > 0) {
                this.current += n;
                this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder = Math.max(0, this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder - n);
            }
            return this;
        }

        @Override
        public Iterator<A> sliceIterator(int from, int until) {
            int formatFrom = this.formatRange$1(from);
            int formatUntil = this.formatRange$1(until);
            this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder = Math.max(0, formatUntil - formatFrom);
            this.current += formatFrom;
            return this;
        }

        private final int formatRange$1(int value) {
            if (value < 0) {
                return 0;
            }
            if (value > this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder) {
                return this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder;
            }
            return value;
        }

        public IndexedSeqViewIterator(IndexedSeqView<A> self) {
            this.self = self;
            this.current = 0;
            this.scala$collection$IndexedSeqView$IndexedSeqViewIterator$$remainder = self.length();
        }
    }

    public static class IndexedSeqViewReverseIterator<A>
    extends AbstractIterator<A>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final IndexedSeqView<A> self;
        public int scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder;
        private int pos;

        private boolean _hasNext() {
            return this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder > 0;
        }

        @Override
        public boolean hasNext() {
            return this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder > 0;
        }

        @Override
        public A next() {
            if (this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder > 0) {
                Object r = this.self.apply(this.pos);
                --this.pos;
                --this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder;
                return r;
            }
            return (A)Iterator$.scala$collection$Iterator$$_empty.next();
        }

        @Override
        public Iterator<A> sliceIterator(int from, int until) {
            if (this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder > 0) {
                if (this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder <= from) {
                    this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder = 0;
                } else if (from <= 0) {
                    if (until >= 0 && until < this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder) {
                        this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder = until;
                    }
                } else {
                    this.pos -= from;
                    this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder = until >= 0 && until < this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder ? (until <= from ? 0 : until - from) : (this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder -= from);
                }
            }
            return this;
        }

        public IndexedSeqViewReverseIterator(IndexedSeqView<A> self) {
            this.self = self;
            this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder = self.length();
            this.pos = this.scala$collection$IndexedSeqView$IndexedSeqViewReverseIterator$$remainder - 1;
        }
    }

    public static class Map<A, B>
    extends SeqView.Map<A, B>
    implements IndexedSeqView<B> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<B> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<B> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<B> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<B> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B$> IndexedSeqView<B$> appended(B$ elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B$> IndexedSeqView<B$> prepended(B$ elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<B> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<B> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<B> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<B> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<B, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<B> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<B> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<B> tapEach(Function1<B, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B$> IndexedSeqView<B$> concat(IndexedSeqOps<B$, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B$> IndexedSeqView<B$> appendedAll(IndexedSeqOps<B$, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B$> IndexedSeqView<B$> prependedAll(IndexedSeqOps<B$, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<B, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<B, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<B> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public B head() {
            return (B)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<B> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public B last() {
            return (B)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B$> Searching.SearchResult search(B$ elem, Ordering<B$> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B$> Searching.SearchResult search(B$ elem, int from, int to, Ordering<B$> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public Map(IndexedSeqOps<A, ?, Object> underlying, Function1<A, B> f) {
            super(underlying, f);
        }
    }

    public static class Prepended<A>
    extends SeqView.Prepended<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public Prepended(A elem, IndexedSeqOps<A, ?, Object> underlying) {
            super(elem, underlying);
        }
    }

    public static class Reverse<A>
    extends SeqView.Reverse<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final IndexedSeqOps<A, ?, Object> underlying;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            IndexedSeqOps<A, ?, Object> indexedSeqOps = this.underlying;
            if (indexedSeqOps instanceof IndexedSeqView) {
                return (IndexedSeqView)indexedSeqOps;
            }
            return ((IndexedSeqView)this).reverse();
        }

        public Reverse(IndexedSeqOps<A, ?, Object> underlying) {
            this.underlying = underlying;
            super(underlying);
        }
    }

    public static class Slice<A>
    extends AbstractIndexedSeqView<A> {
        private static final long serialVersionUID = 3L;
        private final IndexedSeqOps<A, ?, Object> underlying;
        private final int lo;
        private final int hi;
        private final int len;

        public int lo() {
            return this.lo;
        }

        public int hi() {
            return this.hi;
        }

        public int len() {
            return this.len;
        }

        @Override
        public A apply(int i) throws IndexOutOfBoundsException {
            return this.underlying.apply(this.lo() + i);
        }

        @Override
        public int length() {
            return this.len();
        }

        public Slice(IndexedSeqOps<A, ?, Object> underlying, int from, int until) {
            this.underlying = underlying;
            int max$extension_that = 0;
            this.lo = Math.max(from, max$extension_that);
            int max$extension_that2 = 0;
            int n = Math.max(until, max$extension_that2);
            int min$extension_that = underlying.length();
            this.hi = Math.min(n, min$extension_that);
            int n2 = this.hi() - this.lo();
            int max$extension_that3 = 0;
            this.len = Math.max(n2, max$extension_that3);
        }
    }

    public static class Take<A>
    extends SeqView.Take<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public Take(IndexedSeqOps<A, ?, Object> underlying, int n) {
            super(underlying, n);
        }
    }

    public static class TakeRight<A>
    extends SeqView.TakeRight<A>
    implements IndexedSeqView<A> {
        private static final long serialVersionUID = 3L;

        @Override
        public IndexedSeqView<A> view() {
            return ((IndexedSeqView)this).view();
        }

        @Override
        public IndexedSeqView<A> view(int from, int until) {
            return ((IndexedSeqView)this).view(from, until);
        }

        @Override
        public Iterator<A> iterator() {
            return ((IndexedSeqView)this).iterator();
        }

        @Override
        public Iterator<A> reverseIterator() {
            return ((IndexedSeqView)this).reverseIterator();
        }

        @Override
        public <B> IndexedSeqView<B> appended(B elem) {
            return ((IndexedSeqView)this).appended(elem);
        }

        @Override
        public <B> IndexedSeqView<B> prepended(B elem) {
            return ((IndexedSeqView)this).prepended(elem);
        }

        @Override
        public IndexedSeqView<A> take(int n) {
            return ((IndexedSeqView)this).take(n);
        }

        @Override
        public IndexedSeqView<A> takeRight(int n) {
            return ((IndexedSeqView)this).takeRight(n);
        }

        @Override
        public IndexedSeqView<A> drop(int n) {
            return ((IndexedSeqView)this).drop(n);
        }

        @Override
        public IndexedSeqView<A> dropRight(int n) {
            return ((IndexedSeqView)this).dropRight(n);
        }

        @Override
        public <B$> IndexedSeqView<B$> map(Function1<A, B$> f) {
            return ((IndexedSeqView)this).map(f);
        }

        @Override
        public IndexedSeqView<A> reverse() {
            return ((IndexedSeqView)this).reverse();
        }

        @Override
        public IndexedSeqView<A> slice(int from, int until) {
            return ((IndexedSeqView)this).slice(from, until);
        }

        @Override
        public <U> IndexedSeqView<A> tapEach(Function1<A, U> f) {
            return ((IndexedSeqView)this).tapEach(f);
        }

        @Override
        public <B> IndexedSeqView<B> concat(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).concat(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> appendedAll(IndexedSeqOps<B, ?, Object> suffix) {
            return ((IndexedSeqView)this).appendedAll(suffix);
        }

        @Override
        public <B> IndexedSeqView<B> prependedAll(IndexedSeqOps<B, ?, Object> prefix) {
            return ((IndexedSeqView)this).prependedAll(prefix);
        }

        @Override
        public String stringPrefix() {
            return ((IndexedSeqView)this).stringPrefix();
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
            return (S)IndexedSeqOps.stepper$(this, shape);
        }

        @Override
        public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
            return (B$)IndexedSeqOps.foldRight$(this, z, op);
        }

        @Override
        public Iterable<A> reversed() {
            return IndexedSeqOps.reversed$(this);
        }

        @Override
        public A head() {
            return (A)IndexedSeqOps.head$(this);
        }

        @Override
        public Option<A> headOption() {
            return IndexedSeqOps.headOption$(this);
        }

        @Override
        public A last() {
            return (A)IndexedSeqOps.last$(this);
        }

        @Override
        public final int lengthCompare(int len) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
        }

        @Override
        public int knownSize() {
            return IndexedSeqOps.knownSize$(this);
        }

        @Override
        public final int lengthCompare(Iterable<?> that) {
            return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, ord);
        }

        @Override
        public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
            return IndexedSeqOps.search$(this, elem, from, to, ord);
        }

        public TakeRight(IndexedSeqOps<A, ?, Object> underlying, int n) {
            super(underlying, n);
        }
    }
}

