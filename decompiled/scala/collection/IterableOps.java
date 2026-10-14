/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.AbstractView;
import scala.collection.Iterable;
import scala.collection.Iterable$;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.IterableOps$SizeCompareOps$;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.MapFactory;
import scala.collection.View;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.HashMap;
import scala.collection.immutable.HashMap$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$EmptyMap$;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.Map$;
import scala.reflect.ScalaSignature;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.Nothing$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\u0015mha\u00022d!\u0003\r\t\u0001\u001b\u0005\b\u0003\u001b\u0001A\u0011AA\b\u0011\u001d\t9\u0002\u0001D\u0001\u00033Aq!!\u000e\u0001\t\u000b\t9\u0004C\u0004\u0002R\u0001!\t%a\u0015\t\u000f\u0005m\u0003A\"\u0005\u0002^!9\u0011q\f\u0001\u0005\u0006\u0005u\u0003bBA4\u0001\u0019E\u0011\u0011\u000e\u0005\b\u0003\u0007\u0003a\u0011AAC\u0011\u001d\ti\t\u0001C\u0001\u0003\u000bCq!!*\u0001\r#\t9\u000bC\u0004\u00026\u0002!\t!!\u0018\t\u000f\u0005]\u0006\u0001\"\u0001\u0002:\"9\u00111\u0018\u0001\u0005\u0002\u0005u\u0006bBAc\u0001\u0011\u0005\u0011\u0011\u0018\u0005\b\u0003\u000f\u0004A\u0011AA_\u0011\u001d\tI\r\u0001C\u0001\u0003\u0017Dq!a5\u0001\t\u0003\t)\u000eC\u0004\u0002b\u0002!)!a9\t\u000f\u0005M\u0007\u0001\"\u0001\u0004n!9\u0011\u0011\u001a\u0001\u0005\u0002\ru\u0004bBBG\u0001\u0011\u00051q\u0012\u0005\b\u0007K\u0003A\u0011ABT\u0011\u001d\u0019y\u000b\u0001C\u0001\u0007cCqaa\u0016\u0001\t\u0003\u0019)\fC\u0004\u0004<\u0002!\ta!0\t\u000f\r\u001d\u0007\u0001\"\u0011\u0004J\"91q\u001a\u0001\u0005\u0002\rE\u0007bBBk\u0001\u0011\u00051q\u001b\u0005\b\u00077\u0004A\u0011ABo\u0011\u001d\u0019\t\u000f\u0001C\u0001\u0007GDqaa:\u0001\t\u0003\u0019I\u000fC\u0004\u0004n\u0002!\taa<\t\u000f\rM\b\u0001\"\u0001\u0004v\"91\u0011 \u0001\u0005\u0002\rm\bb\u0002C\u0003\u0001\u0011\u0005Aq\u0001\u0005\b\t\u000b\u0001A\u0011\u0001C\u0006\u0011\u001d!\u0019\u0002\u0001C\u0001\u0003;Bq\u0001\"\u0006\u0001\t\u0003\ti\u0006C\u0004\u0005\u0018\u0001!\t\u0001\"\u0007\t\u000f\u0011}\u0001\u0001\"\u0001\u0005\"!9A1\b\u0001\u0005\u0002\u0011u\u0002b\u0002C-\u0001\u0011\u0005A1\f\u0005\b\t\u007f\u0002A\u0011\u0001CA\u0011\u001d!I\n\u0001C\u0001\t7Cq\u0001\",\u0001\t\u0003!y\u000bC\u0004\u0004\"\u0001!\t\u0001\"1\t\u000f\rU\u0002\u0001\"\u0001\u0005P\"9Aq\u001c\u0001\u0005\u0002\u0011\u0005\bb\u0002Cy\u0001\u0011\u0005A1\u001f\u0005\b\u000b\u000f\u0001A\u0011AC\u0005\u0011\u001d)I\u0003\u0001C\u0001\u000bWAq!b\u000f\u0001\t\u000b)i\u0004C\u0004\u0006N\u0001!\t!b\u0014\t\u000f\u0015}\u0003\u0001\"\u0001\u0006b!9Qq\r\u0001\u0005\u0002\u0015%\u0004bBCC\u0001\u0011\u0005Qq\u0011\u0005\b\u000bC\u0003A\u0011ACR\u0011\u001d)I\r\u0001C\u0001\u000b\u0017Dq!\"4\u0001\t\u0003)Y\rC\u0004\u0006P\u0002!\t%\"5\t\u0011\u0015u\u0007\u0001)C\u0005\u000b?Dq!\":\u0001\t\u0003)9oB\u0004\u0002j\u000eD\t!a;\u0007\r\t\u001c\u0007\u0012AAw\u0011\u001d\t)\u0010\u0011C\u0001\u0003o4a!!?A\u0005\u0005m\bB\u0003B\u0002\u0005\n\u0015\r\u0011\"\u0001\u0003\u0006!Q!\u0011\u0003\"\u0003\u0002\u0003\u0006IAa\u0002\t\u0011\u0005U(\t\"\u0001d\u0005?AqAa\rC\t\u0003\u0011)\u0004C\u0004\u0003>\t#\tAa\u0010\t\u000f\t\u0015#\t\"\u0001\u0003H!9!Q\n\"\u0005\u0002\t=\u0003b\u0002B+\u0005\u0012\u0005!q\u000b\u0005\b\u0005;\u0012E\u0011\u0001B0\u0011%\u0011)GQA\u0001\n\u0003\u00129\u0007C\u0005\u0003j\t\u000b\t\u0011\"\u0011\u0003l\u001dI!\u0011\u000f!\u0002\u0002#\u0005!1\u000f\u0004\n\u0003s\u0004\u0015\u0011!E\u0001\u0005kBq!!>P\t\u0003\u00119\bC\u0004\u0003z=#)Aa\u001f\t\u000f\t\u001du\n\"\u0002\u0003\n\"9!1S(\u0005\u0006\tU\u0005b\u0002BP\u001f\u0012\u0015!\u0011\u0015\u0005\b\u0005W{EQ\u0001BW\u0011\u001d\u00119l\u0014C\u0003\u0005sC\u0011Ba1P\u0003\u0003%)A!2\t\u0013\t%w*!A\u0005\u0006\t-gA\u0002Bj\u0001\u0002\u0011)\u000e\u0003\u0006\u0003xf\u0013\t\u0011)A\u0005\u0005sD!ba\u0001Z\u0005\u0003\u0005\u000b\u0011BB\u0003\u0011\u001d\t)0\u0017C\u0001\u0007\u0017Aqaa\u0007Z\t#\u0019i\u0002C\u0004\u0004\"e#\taa\t\t\u000f\rU\u0012\f\"\u0001\u00048!91qI-\u0005\u0002\r%\u0003bBB,3\u0012\u00051\u0011\f\u0002\f\u0013R,'/\u00192mK>\u00038O\u0003\u0002eK\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003\u0019\fQa]2bY\u0006\u001c\u0001!F\u0003jiz\fIa\u0005\u0003\u0001U:T\bCA6m\u001b\u0005)\u0017BA7f\u0005\r\te.\u001f\t\u0004_B\u0014X\"A2\n\u0005E\u001c'\u0001D%uKJ\f'\r\\3P]\u000e,\u0007CA:u\u0019\u0001!a!\u001e\u0001\u0005\u0006\u00041(!A!\u0012\u0005]T\u0007CA6y\u0013\tIXMA\u0004O_RD\u0017N\\4\u0011\r=\\(/`A\u0004\u0013\ta8MA\bJi\u0016\u0014\u0018M\u00197f\u001f:\u001cWm\u00149t!\t\u0019h\u0010B\u0004\u0000\u0001\u0011\u0015\r!!\u0001\u0003\u0005\r\u001bUc\u0001<\u0002\u0004\u00111\u0011Q\u0001@C\u0002Y\u0014Aa\u0018\u0013%cA\u00191/!\u0003\u0005\u000f\u0005-\u0001\u0001\"b\u0001m\n\t1)\u0001\u0004%S:LG\u000f\n\u000b\u0003\u0003#\u00012a[A\n\u0013\r\t)\"\u001a\u0002\u0005+:LG/\u0001\u0006u_&#XM]1cY\u0016,\"!a\u0007\u0011\t=\fiB]\u0005\u0004\u0003?\u0019'\u0001C%uKJ\f'\r\\3)\u0017\t\t\u0019#!\u000b\u0002,\u0005=\u0012\u0011\u0007\t\u0004W\u0006\u0015\u0012bAA\u0014K\nQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012\u0011QF\u0001\u0002\u001aQ|\u0017\n^3sC\ndW\rI5tA%tG/\u001a:oC2\u0004\u0013M\u001c3!o&dG\u000e\t2fA5\fG-\u001a\u0011qe>$Xm\u0019;fIn\u0002\u0013\u000e^:!]\u0006lW\rI5tAMLW.\u001b7be\u0002\"x\u000e\t1u_2K7\u000f\u001e1!_J\u0004\u0003\r^8TKF\u0004G\u0006\t2vi\u0002JG\u000f\t3pKNtw\u0005\u001e\u0011d_BL\bE\\8o[%lW.\u001e;bE2,\u0007eY8mY\u0016\u001cG/[8og\u0006)1/\u001b8dK\u0006\u0012\u00111G\u0001\u0007e9\n4GL\u001c\u0002\u001bQ|GK]1wKJ\u001c\u0018M\u00197f+\t\tI\u0004E\u0003\u0002<\u0005\u0005#OD\u0002p\u0003{I1!a\u0010d\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u0011\u0002F\tYAK]1wKJ\u001c\u0018M\u00197f\u0015\r\tyd\u0019\u0015\f\u0007\u0005\r\u0012\u0011FA%\u0003_\ti%\t\u0002\u0002L\u0005\ty\u0002^8Ue\u00064XM]:bE2,\u0007%[:!S:$XM\u001d8bY\u0002\ng\u000e\u001a\u0011xS2d\u0007EY3![\u0006$W\r\t9s_R,7\r^3ew\u0001JGo\u001d\u0011oC6,\u0007%[:!g&l\u0017\u000e\\1sAQ|\u0007\u0005\u0019;p\u0019&\u001cH\u000f\u0019\u0011pe\u0002\u0002Go\\*fc\u0002d\u0003EY;uA%$\b\u0005Z8fg:<C\u000fI2paf\u0004cn\u001c8.S6lW\u000f^1cY\u0016\u00043m\u001c7mK\u000e$\u0018n\u001c8tC\t\ty%\u0001\u00043]E\u001ad\u0006M\u0001\u0013SN$&/\u0019<feN\f'\r\\3BO\u0006Lg.\u0006\u0002\u0002VA\u00191.a\u0016\n\u0007\u0005eSMA\u0004C_>dW-\u00198\u0002\t\r|G\u000e\\\u000b\u0003\u0003\u000f\tAA]3qe\"Za!a\t\u0002*\u0005\r\u0014qFA'C\t\t)'A5Vg\u0016\u00043m\u001c7mA%t7\u000f^3bI\u0002zg\r\t:faJ\u0004\u0013N\u001c\u0011bA\r|G\u000e\\3di&|g\u000eI5na2,W.\u001a8uCRLwN\u001c\u0017!kN,\u0007\u0005\u001e5fA\r|G\u000e\\3di&|g\u000e\t<bYV,\u0007%\u001b;tK24\u0007E\u001a:p[\u0002\"\b.\u001a\u0011pkR\u001c\u0018\u000eZ3\u0002\u0019\u0019\u0014x.\\*qK\u000eLg-[2\u0015\t\u0005\u001d\u00111\u000e\u0005\b\u00037:\u0001\u0019AA7!\u0011y\u0007/a\u001c+\u0007I\f\th\u000b\u0002\u0002tA!\u0011QOA@\u001b\t\t9H\u0003\u0003\u0002z\u0005m\u0014!C;oG\",7m[3e\u0015\r\ti(Z\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAA\u0003o\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003=IG/\u001a:bE2,g)Y2u_JLXCAAD!\u0011y\u0017\u0011R?\n\u0007\u0005-5MA\bJi\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z\u0003%\u0019w.\u001c9b]&|g\u000eK\u0002\n\u0003#\u00032a[AJ\u0013\r\t)*\u001a\u0002\u0007S:d\u0017N\\3)\u0017%\tI*!\u000b\u0002 \u0006=\u0012Q\n\t\u0004W\u0006m\u0015bAAOK\n!B-\u001a9sK\u000e\fG/\u001a3Pm\u0016\u0014(/\u001b3j]\u001e\f#!!)\u00027U\u001bX\rI5uKJ\f'\r\\3GC\u000e$xN]=!S:\u001cH/Z1eQ-I\u00111EA\u0015\u0003?\u000by#!\u0014\u0002%9,wo\u00159fG&4\u0017n\u0019\"vS2$WM]\u000b\u0003\u0003S\u0003\u0002\"a+\u00022\u0006=\u0014qA\u0007\u0003\u0003[S1!a,d\u0003\u001diW\u000f^1cY\u0016LA!a-\u0002.\n9!)^5mI\u0016\u0014\u0018!B3naRL\u0018\u0001\u00025fC\u0012,\u0012A]\u0001\u000bQ\u0016\fGm\u00149uS>tWCAA`!\u0011Y\u0017\u0011\u0019:\n\u0007\u0005\rWM\u0001\u0004PaRLwN\\\u0001\u0005Y\u0006\u001cH/\u0001\u0006mCN$x\n\u001d;j_:\fAA^5foV\u0011\u0011Q\u001a\t\u0005_\u0006='/C\u0002\u0002R\u000e\u0014AAV5fo\u0006Y1/\u001b>f\u0007>l\u0007/\u0019:f)\u0011\t9.!8\u0011\u0007-\fI.C\u0002\u0002\\\u0016\u00141!\u00138u\u0011\u001d\ty.\u0005a\u0001\u0003/\f\u0011b\u001c;iKJ\u001c\u0016N_3\u0002\rML'0Z%t+\t\t)\u000fE\u0002\u0002h\ns!a\\ \u0002\u0017%#XM]1cY\u0016|\u0005o\u001d\t\u0003_\u0002\u001b2\u0001QAx!\rY\u0017\u0011_\u0005\u0004\u0003g,'AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0003\u0003W\u0014abU5{K\u000e{W\u000e]1sK>\u00038oE\u0002C\u0003{\u00042a[A\u0000\u0013\r\u0011\t!\u001a\u0002\u0007\u0003:Lh+\u00197\u0002\u0005%$XC\u0001B\u0004a\u0019\u0011IA!\u0004\u0003\u001cAAq\u000e\u0001B\u0006\u0005'\u0011I\u0002E\u0002t\u0005\u001b!!Ba\u0004E\u0003\u0003\u0005\tQ!\u0001w\u0005\ryFEM\u0001\u0004SR\u0004\u0003\u0003BA\u001e\u0005+IAAa\u0006\u0002F\tI\u0011I\\=D_:\u001cHO\u001d\t\u0004g\nmAA\u0003B\u000f\t\u0006\u0005\t\u0011!B\u0001m\n\u0019q\fJ\u001a\u0015\t\t\u0005\"Q\u0005\t\u0004\u0005G\u0011U\"\u0001!\t\u000f\t\rQ\t1\u0001\u0003(A2!\u0011\u0006B\u0017\u0005c\u0001\u0002b\u001c\u0001\u0003,\tM!q\u0006\t\u0004g\n5Ba\u0003B\b\u0005K\t\t\u0011!A\u0003\u0002Y\u00042a\u001dB\u0019\t-\u0011iB!\n\u0002\u0002\u0003\u0005)\u0011\u0001<\u0002\u000b\u0011bWm]:\u0015\t\u0005U#q\u0007\u0005\b\u0005s1\u0005\u0019AAl\u0003\u0011\u0019\u0018N_3)\u0007\u0019\u000b\t*\u0001\u0005%Y\u0016\u001c8\u000fJ3r)\u0011\t)F!\u0011\t\u000f\ter\t1\u0001\u0002X\"\u001aq)!%\u0002\r\u0011*\u0017\u000fJ3r)\u0011\t)F!\u0013\t\u000f\te\u0002\n1\u0001\u0002X\"\u001a\u0001*!%\u0002\u0011\u0011\u0012\u0017M\\4%KF$B!!\u0016\u0003R!9!\u0011H%A\u0002\u0005]\u0007fA%\u0002\u0012\u0006YAe\u001a:fCR,'\u000fJ3r)\u0011\t)F!\u0017\t\u000f\te\"\n1\u0001\u0002X\"\u001a!*!%\u0002\u0011\u0011:'/Z1uKJ$B!!\u0016\u0003b!9!\u0011H&A\u0002\u0005]\u0007fA&\u0002\u0012\u0006A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002X\u00061Q-];bYN$B!!\u0016\u0003n!A!qN'\u0002\u0002\u0003\u0007!.A\u0002yIE\nabU5{K\u000e{W\u000e]1sK>\u00038\u000fE\u0002\u0003$=\u001b2aTAx)\t\u0011\u0019(A\b%Y\u0016\u001c8\u000fJ3yi\u0016t7/[8o)\u0011\u0011iH!!\u0015\t\u0005U#q\u0010\u0005\b\u0005s\t\u0006\u0019AAl\u0011\u001d\u0011\u0019)\u0015a\u0001\u0005C\tQ\u0001\n;iSND3!UAI\u0003I!C.Z:tI\u0015\fH%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\t-%q\u0012\u000b\u0005\u0003+\u0012i\tC\u0004\u0003:I\u0003\r!a6\t\u000f\t\r%\u000b1\u0001\u0003\"!\u001a!+!%\u0002!\u0011*\u0017\u000fJ3rI\u0015DH/\u001a8tS>tG\u0003\u0002BL\u00057#B!!\u0016\u0003\u001a\"9!\u0011H*A\u0002\u0005]\u0007b\u0002BB'\u0002\u0007!\u0011\u0005\u0015\u0004'\u0006E\u0015A\u0005\u0013cC:<G%Z9%Kb$XM\\:j_:$BAa)\u0003(R!\u0011Q\u000bBS\u0011\u001d\u0011I\u0004\u0016a\u0001\u0003/DqAa!U\u0001\u0004\u0011\t\u0003K\u0002U\u0003#\u000bQ\u0003J4sK\u0006$XM\u001d\u0013fc\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u00030\nMF\u0003BA+\u0005cCqA!\u000fV\u0001\u0004\t9\u000eC\u0004\u0003\u0004V\u0003\rA!\t)\u0007U\u000b\t*\u0001\n%OJ,\u0017\r^3sI\u0015DH/\u001a8tS>tG\u0003\u0002B^\u0005\u007f#B!!\u0016\u0003>\"9!\u0011\b,A\u0002\u0005]\u0007b\u0002BB-\u0002\u0007!\u0011\u0005\u0015\u0004-\u0006E\u0015A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:$BAa\u001a\u0003H\"9!1Q,A\u0002\t\u0005\u0012\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\u0011\u0011iM!5\u0015\t\u0005U#q\u001a\u0005\t\u0005_B\u0016\u0011!a\u0001U\"9!1\u0011-A\u0002\t\u0005\"AC,ji\"4\u0015\u000e\u001c;feV1!q\u001bBp\u0005G\u001cR!\u0017Bm\u0005W\u0004ra\u001cBn\u0005;\u0014\t/C\u0002\u0003T\u000e\u00042a\u001dBp\t\u0019)\u0018\f\"b\u0001mB\u00191Oa9\u0005\u000f}LFQ1\u0001\u0003fV\u0019aOa:\u0005\u000f\t%(1\u001db\u0001m\n!q\f\n\u00133!\u0011\u0011iO!=\u000f\u0007-\u0014y/C\u0002\u0002@\u0015LAAa=\u0003v\na1+\u001a:jC2L'0\u00192mK*\u0019\u0011qH3\u0002\tM,GN\u001a\u0019\u0005\u0005w\u0014y\u0010\u0005\u0005p\u0001\tu'\u0011\u001dB\u007f!\r\u0019(q \u0003\u000b\u0007\u0003Q\u0016\u0011!A\u0001\u0006\u00031(aA0%i\u0005\t\u0001\u000fE\u0004l\u0007\u000f\u0011i.!\u0016\n\u0007\r%QMA\u0005Gk:\u001cG/[8ocQ11QBB\b\u00073\u0001rAa\tZ\u0005;\u0014\t\u000fC\u0004\u0003xr\u0003\ra!\u00051\t\rM1q\u0003\t\t_\u0002\u0011iN!9\u0004\u0016A\u00191oa\u0006\u0005\u0017\r\u00051qBA\u0001\u0002\u0003\u0015\tA\u001e\u0005\b\u0007\u0007a\u0006\u0019AB\u0003\u0003!1\u0017\u000e\u001c;fe\u0016$WCAB\u0010!\u0015y\u0017Q\u0004Bo\u0003\ri\u0017\r]\u000b\u0005\u0007K\u0019Y\u0003\u0006\u0003\u0004(\r=\u0002#B:\u0003d\u000e%\u0002cA:\u0004,\u001111Q\u00060C\u0002Y\u0014\u0011A\u0011\u0005\b\u0007cq\u0006\u0019AB\u001a\u0003\u00051\u0007cB6\u0004\b\tu7\u0011F\u0001\bM2\fG/T1q+\u0011\u0019Ida\u0010\u0015\t\rm2\u0011\t\t\u0006g\n\r8Q\b\t\u0004g\u000e}BABB\u0017?\n\u0007a\u000fC\u0004\u00042}\u0003\raa\u0011\u0011\u000f-\u001c9A!8\u0004FA!q\u000e]B\u001f\u0003\u001d1wN]3bG\",Baa\u0013\u0004TQ!\u0011\u0011CB'\u0011\u001d\u0019\t\u0004\u0019a\u0001\u0007\u001f\u0002ra[B\u0004\u0005;\u001c\t\u0006E\u0002t\u0007'\"aa!\u0016a\u0005\u00041(!A+\u0002\u0015]LG\u000f\u001b$jYR,'\u000f\u0006\u0003\u0004\u000e\rm\u0003bBB/C\u0002\u00071QA\u0001\u0002c\":\u0011l!\u0019\u0004h\r%\u0004cA6\u0004d%\u00191QM3\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0002)\u0007I\t\t\n\u0006\u0003\u0002X\u000e=\u0004bBB9'\u0001\u000711O\u0001\u0005i\"\fG\u000f\r\u0003\u0004v\re\u0004#B8\u0002\u001e\r]\u0004cA:\u0004z\u0011Y11PB8\u0003\u0003\u0005\tQ!\u0001w\u0005\ryF%\r\u000b\u0007\u0003\u001b\u001cyha!\t\u000f\r\u0005E\u00031\u0001\u0002X\u0006!aM]8n\u0011\u001d\u0019)\t\u0006a\u0001\u0003/\fQ!\u001e8uS2D3\u0002FA\u0012\u0003S\u0019I)a\f\u0002N\u0005\u001211R\u0001;+N,\u0007E\f<jK^t3\u000f\\5dK\"2'o\\7-AUtG/\u001b7*A%t7\u000f^3bI\u0002zg\r\t\u0018wS\u0016<\bF\u001a:p[2\u0002SO\u001c;jY&\n\u0011\u0002\u001e:b]N\u0004xn]3\u0016\t\rE51\u0014\u000b\u0005\u0007'\u001bi\n\u0005\u0003t}\u000eU%\u0006BBL\u0003c\u0002Ba\u001d@\u0004\u001aB\u00191oa'\u0005\r\r5RC1\u0001w\u0011\u001d\u0019y*\u0006a\u0002\u0007C\u000b!\"Y:Ji\u0016\u0014\u0018M\u00197f!\u0019Y7q\u0001:\u0004$B)q.!\b\u0004\u001a\u00061a-\u001b7uKJ$B!a\u0002\u0004*\"911\u0016\fA\u0002\r5\u0016\u0001\u00029sK\u0012\u0004ba[B\u0004e\u0006U\u0013!\u00034jYR,'OT8u)\u0011\t9aa-\t\u000f\r-v\u00031\u0001\u0004.R!1qWB]!\u0015y'1\u001c:~\u0011\u001d\u0019\u0019\u0001\u0007a\u0001\u0007[\u000b\u0011\u0002]1si&$\u0018n\u001c8\u0015\t\r}6Q\u0019\t\bW\u000e\u0005\u0017qAA\u0004\u0013\r\u0019\u0019-\u001a\u0002\u0007)V\u0004H.\u001a\u001a\t\u000f\r\r\u0011\u00041\u0001\u0004.\u000691\u000f\u001d7ji\u0006#H\u0003BB`\u0007\u0017Dqa!4\u001b\u0001\u0004\t9.A\u0001o\u0003\u0011!\u0018m[3\u0015\t\u0005\u001d11\u001b\u0005\b\u0007\u001b\\\u0002\u0019AAl\u0003%!\u0018m[3SS\u001eDG\u000f\u0006\u0003\u0002\b\re\u0007bBBg9\u0001\u0007\u0011q[\u0001\ni\u0006\\Wm\u00165jY\u0016$B!a\u0002\u0004`\"911A\u000fA\u0002\r5\u0016\u0001B:qC:$Baa0\u0004f\"911\u0001\u0010A\u0002\r5\u0016\u0001\u00023s_B$B!a\u0002\u0004l\"91QZ\u0010A\u0002\u0005]\u0017!\u00033s_B\u0014\u0016n\u001a5u)\u0011\t9a!=\t\u000f\r5\u0007\u00051\u0001\u0002X\u0006IAM]8q/\"LG.\u001a\u000b\u0005\u0003\u000f\u00199\u0010C\u0004\u0004\u0004\u0005\u0002\ra!,\u0002\u000f\u001d\u0014x.\u001e9fIR!1Q C\u0002!\u0015y7q`A\u0004\u0013\r!\ta\u0019\u0002\t\u0013R,'/\u0019;pe\"9!\u0011\b\u0012A\u0002\u0005]\u0017aB:mS\u0012Lgn\u001a\u000b\u0005\u0007{$I\u0001C\u0004\u0003:\r\u0002\r!a6\u0015\r\ruHQ\u0002C\b\u0011\u001d\u0011I\u0004\na\u0001\u0003/Dq\u0001\"\u0005%\u0001\u0004\t9.\u0001\u0003ti\u0016\u0004\u0018\u0001\u0002;bS2\fA!\u001b8ji\u0006)1\u000f\\5dKR1\u0011q\u0001C\u000e\t;Aqa!!(\u0001\u0004\t9\u000eC\u0004\u0004\u0006\u001e\u0002\r!a6\u0002\u000f\u001d\u0014x.\u001e9CsV!A1\u0005C\u001a)\u0011!)\u0003b\u000e\u0011\u0011\u0011\u001dBQ\u0006C\u0019\u0003\u000fi!\u0001\"\u000b\u000b\u0007\u0011-2-A\u0005j[6,H/\u00192mK&!Aq\u0006C\u0015\u0005\ri\u0015\r\u001d\t\u0004g\u0012MBA\u0002C\u001bQ\t\u0007aOA\u0001L\u0011\u001d\u0019\t\u0004\u000ba\u0001\ts\u0001ba[B\u0004e\u0012E\u0012\u0001C4s_V\u0004X*\u00199\u0016\r\u0011}Bq\tC')\u0011!\t\u0005b\u0015\u0015\t\u0011\rCq\n\t\t\tO!i\u0003\"\u0012\u0005JA\u00191\u000fb\u0012\u0005\r\u0011U\u0012F1\u0001w!\u0011\u0019h\u0010b\u0013\u0011\u0007M$i\u0005\u0002\u0004\u0004.%\u0012\rA\u001e\u0005\b\u0007cI\u0003\u0019\u0001C)!\u0019Y7q\u0001:\u0005L!9AQK\u0015A\u0002\u0011]\u0013aA6fsB11na\u0002s\t\u000b\nab\u001a:pkBl\u0015\r\u001d*fIV\u001cW-\u0006\u0004\u0005^\u0011\u001dD1\u000e\u000b\u0005\t?\"Y\b\u0006\u0003\u0005b\u0011]D\u0003\u0002C2\t[\u0002\u0002\u0002b\n\u0005.\u0011\u0015D\u0011\u000e\t\u0004g\u0012\u001dDA\u0002C\u001bU\t\u0007a\u000fE\u0002t\tW\"aa!\f+\u0005\u00041\bb\u0002C8U\u0001\u0007A\u0011O\u0001\u0007e\u0016$WoY3\u0011\u0013-$\u0019\b\"\u001b\u0005j\u0011%\u0014b\u0001C;K\nIa)\u001e8di&|gN\r\u0005\b\u0007cQ\u0003\u0019\u0001C=!\u0019Y7q\u0001:\u0005j!9AQ\u000b\u0016A\u0002\u0011u\u0004CB6\u0004\bI$)'\u0001\u0003tG\u0006tW\u0003\u0002CB\t\u0017#B\u0001\"\"\u0005\u0016R!Aq\u0011CH!\u0011\u0019h\u0010\"#\u0011\u0007M$Y\tB\u0004\u0004.-\u0012\r\u0001\"$\u0012\u0005IT\u0007b\u0002CIW\u0001\u0007A1S\u0001\u0003_B\u0004\u0012b\u001bC:\t\u0013#I\t\"#\t\u000f\u0011]5\u00061\u0001\u0005\n\u0006\t!0\u0001\u0005tG\u0006tG*\u001a4u+\u0011!i\n\"*\u0015\t\u0011}E1\u0016\u000b\u0005\tC#9\u000b\u0005\u0003t}\u0012\r\u0006cA:\u0005&\u001211Q\u0006\u0017C\u0002YDq\u0001\"%-\u0001\u0004!I\u000b\u0005\u0005l\tg\"\u0019K\u001dCR\u0011\u001d!9\n\fa\u0001\tG\u000b\u0011b]2b]JKw\r\u001b;\u0016\t\u0011EF\u0011\u0018\u000b\u0005\tg#y\f\u0006\u0003\u00056\u0012m\u0006\u0003B:\u007f\to\u00032a\u001dC]\t\u0019\u0019i#\fb\u0001m\"9A\u0011S\u0017A\u0002\u0011u\u0006\u0003C6\u0005tI$9\fb.\t\u000f\u0011]U\u00061\u0001\u00058V!A1\u0019Ce)\u0011!)\rb3\u0011\tMtHq\u0019\t\u0004g\u0012%GABB\u0017]\t\u0007a\u000fC\u0004\u000429\u0002\r\u0001\"4\u0011\r-\u001c9A\u001dCd+\u0011!\t\u000eb6\u0015\t\u0011MG\u0011\u001c\t\u0005gz$)\u000eE\u0002t\t/$aa!\f0\u0005\u00041\bbBB\u0019_\u0001\u0007A1\u001c\t\u0007W\u000e\u001d!\u000f\"8\u0011\t=\u0004HQ[\u0001\bM2\fG\u000f^3o+\u0011!\u0019\u000f\";\u0015\t\u0011\u0015H1\u001e\t\u0005gz$9\u000fE\u0002t\tS$aa!\f1\u0005\u00041\bbBBPa\u0001\u000fAQ\u001e\t\u0007W\u000e\u001d!\u000fb<\u0011\t=\u0004Hq]\u0001\bG>dG.Z2u+\u0011!)\u0010b?\u0015\t\u0011]HQ \t\u0005gz$I\u0010E\u0002t\tw$aa!\f2\u0005\u00041\bb\u0002C\u0000c\u0001\u0007Q\u0011A\u0001\u0003a\u001a\u0004ba[C\u0002e\u0012e\u0018bAC\u0003K\ny\u0001+\u0019:uS\u0006dg)\u001e8di&|g.\u0001\u0007qCJ$\u0018\u000e^5p]6\u000b\u0007/\u0006\u0004\u0006\f\u0015MQ1\u0004\u000b\u0005\u000b\u001b)y\u0002E\u0004l\u0007\u0003,y!b\u0006\u0011\tMtX\u0011\u0003\t\u0004g\u0016MAABC\u000be\t\u0007aO\u0001\u0002BcA!1O`C\r!\r\u0019X1\u0004\u0003\u0007\u000b;\u0011$\u0019\u0001<\u0003\u0005\u0005\u0013\u0004bBB\u0019e\u0001\u0007Q\u0011\u0005\t\u0007W\u000e\u001d!/b\t\u0011\u0011\t5XQEC\t\u000b3IA!b\n\u0003v\n1Q)\u001b;iKJ\faaY8oG\u0006$X\u0003BC\u0017\u000bg!B!b\f\u00066A!1O`C\u0019!\r\u0019X1\u0007\u0003\b\u0007[\u0019$\u0019\u0001CG\u0011\u001d)9d\ra\u0001\u000bs\taa];gM&D\b\u0003B8q\u000bc\t!\u0002\n9mkN$\u0003\u000f\\;t+\u0011)y$\"\u0012\u0015\t\u0015\u0005Sq\t\t\u0005gz,\u0019\u0005E\u0002t\u000b\u000b\"qa!\f5\u0005\u0004!i\tC\u0004\u00068Q\u0002\r!\"\u0013\u0011\t=\u0004X1\t\u0015\u0004i\u0005E\u0015a\u0001>jaV!Q\u0011KC-)\u0011)\u0019&b\u0017\u0011\tMtXQ\u000b\t\bW\u000e\u0005\u0017qNC,!\r\u0019X\u0011\f\u0003\u0007\u0007[)$\u0019\u0001<\t\u000f\rET\u00071\u0001\u0006^A!q\u000e]C,\u00031Q\u0018\u000e],ji\"Le\u000eZ3y+\t)\u0019\u0007\u0005\u0003t}\u0016\u0015\u0004cB6\u0004B\u0006=\u0014q[\u0001\u0007u&\u0004\u0018\t\u001c7\u0016\r\u0015-T1OC<)!)i'\"\u001f\u0006~\u0015\u0005\u0005\u0003B:\u007f\u000b_\u0002ra[Ba\u000bc*)\bE\u0002t\u000bg\"q!\"\u00068\u0005\u0004!i\tE\u0002t\u000bo\"aa!\f8\u0005\u00041\bbBB9o\u0001\u0007Q1\u0010\t\u0006_\u0006uQQ\u000f\u0005\b\u000b\u007f:\u0004\u0019AC9\u0003!!\b.[:FY\u0016l\u0007bBCBo\u0001\u0007QQO\u0001\ti\"\fG/\u00127f[\u0006)QO\u001c>jaV1Q\u0011RCI\u000b/#B!b#\u0006\u001aB91n!1\u0006\u000e\u0016M\u0005\u0003B:\u007f\u000b\u001f\u00032a]CI\t\u0019))\u0002\u000fb\u0001mB!1O`CK!\r\u0019Xq\u0013\u0003\u0007\u000b;A$\u0019\u0001<\t\u000f\u0015m\u0005\bq\u0001\u0006\u001e\u00061\u0011m\u001d)bSJ\u0004ba[B\u0004e\u0016}\u0005cB6\u0004B\u0016=UQS\u0001\u0007k:T\u0018\u000e]\u001a\u0016\u0011\u0015\u0015V\u0011WC\\\u000b{#B!b*\u0006BBI1.\"+\u0006.\u0016MV\u0011X\u0005\u0004\u000bW+'A\u0002+va2,7\u0007\u0005\u0003t}\u0016=\u0006cA:\u00062\u00121QQC\u001dC\u0002Y\u0004Ba\u001d@\u00066B\u00191/b.\u0005\r\u0015u\u0011H1\u0001w!\u0011\u0019h0b/\u0011\u0007M,i\f\u0002\u0004\u0006@f\u0012\rA\u001e\u0002\u0003\u0003NBq!b1:\u0001\b))-\u0001\u0005bgR\u0013\u0018\u000e\u001d7f!\u0019Y7q\u0001:\u0006HBI1.\"+\u00060\u0016UV1X\u0001\u0006i\u0006LGn]\u000b\u0003\u0007{\fQ!\u001b8jiN\fq\u0001^1q\u000b\u0006\u001c\u0007.\u0006\u0003\u0006T\u0016mG\u0003BA\u0004\u000b+Dqa!\r=\u0001\u0004)9\u000e\u0005\u0004l\u0007\u000f\u0011X\u0011\u001c\t\u0004g\u0016mGABB+y\t\u0007a/A\tji\u0016\u0014\u0018\r^3V]RLG.R7qif$Ba!@\u0006b\"91\u0011G\u001fA\u0002\u0015\r\bcB6\u0004\b\u0005m\u00111D\u0001\u0011IAdWo\u001d\u0013qYV\u001cHeY8m_:,B!\";\u0006pR!Q1^Cy!\u0011\u0019h0\"<\u0011\u0007M,y\u000fB\u0004\u0004.y\u0012\r\u0001\"$\t\u000f\rEd\b1\u0001\u0006tB!q\u000e]CwQ-q\u00141EA\u0015\u000bo\fy#!\u0014\"\u0005\u0015e\u0018AN+tK\u0002Z3\u0006I5ogR,\u0017\r\u001a\u0011pM\u0002Z3F\u000f\u0011g_J\u00043m\u001c7mK\u000e$\u0018n\u001c8tA=4\u0007\u0005^=qK\u0002JE/\u001a:bE2,\u0007")
public interface IterableOps<A, CC, C>
extends IterableOnce<A>,
IterableOnceOps<A, CC, C> {
    public Iterable<A> toIterable();

    public static /* synthetic */ Iterable toTraversable$(IterableOps $this) {
        return $this.toTraversable();
    }

    default public Iterable<A> toTraversable() {
        return this.toIterable();
    }

    public static /* synthetic */ boolean isTraversableAgain$(IterableOps $this) {
        return $this.isTraversableAgain();
    }

    @Override
    default public boolean isTraversableAgain() {
        return true;
    }

    public C coll();

    public static /* synthetic */ Object repr$(IterableOps $this) {
        return $this.repr();
    }

    default public C repr() {
        return this.coll();
    }

    public C fromSpecific(IterableOnce<A> var1);

    public IterableFactory<CC> iterableFactory();

    public static /* synthetic */ IterableFactory companion$(IterableOps $this) {
        return $this.companion();
    }

    default public IterableFactory<CC> companion() {
        return this.iterableFactory();
    }

    public Builder<A, C> newSpecificBuilder();

    public static /* synthetic */ Object empty$(IterableOps $this) {
        return $this.empty();
    }

    default public C empty() {
        return this.fromSpecific(Nil$.MODULE$);
    }

    public static /* synthetic */ Object head$(IterableOps $this) {
        return $this.head();
    }

    default public A head() {
        return this.iterator().next();
    }

    public static /* synthetic */ Option headOption$(IterableOps $this) {
        return $this.headOption();
    }

    default public Option<A> headOption() {
        Iterator it = this.iterator();
        if (it.hasNext()) {
            return new Some(it.next());
        }
        return None$.MODULE$;
    }

    public static /* synthetic */ Object last$(IterableOps $this) {
        return $this.last();
    }

    default public A last() {
        Iterator it = this.iterator();
        Object lst = it.next();
        while (it.hasNext()) {
            lst = it.next();
        }
        return lst;
    }

    public static /* synthetic */ Option lastOption$(IterableOps $this) {
        return $this.lastOption();
    }

    default public Option<A> lastOption() {
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<A>(this.last());
    }

    public static /* synthetic */ View view$(IterableOps $this) {
        return $this.view();
    }

    default public View<A> view() {
        Function0<Iterator> & Serializable fromIteratorProvider_it = (Function0<Iterator> & Serializable)() -> this.iterator();
        return new AbstractView<A>(fromIteratorProvider_it){
            private final Function0 it$1;

            public Iterator<A> iterator() {
                return (Iterator)this.it$1.apply();
            }
            {
                this.it$1 = it$1;
            }
        };
    }

    public static /* synthetic */ int sizeCompare$(IterableOps $this, int otherSize) {
        return $this.sizeCompare(otherSize);
    }

    default public int sizeCompare(int otherSize) {
        if (otherSize < 0) {
            return 1;
        }
        int known = this.knownSize();
        if (known >= 0) {
            return Integer.compare(known, otherSize);
        }
        int i = 0;
        Iterator it = this.iterator();
        while (it.hasNext()) {
            if (i == otherSize) {
                return 1;
            }
            it.next();
            ++i;
        }
        return i - otherSize;
    }

    public static /* synthetic */ IterableOps sizeIs$(IterableOps $this) {
        return $this.sizeIs();
    }

    default public IterableOps sizeIs() {
        return this;
    }

    public static /* synthetic */ int sizeCompare$(IterableOps $this, Iterable that) {
        return $this.sizeCompare(that);
    }

    default public int sizeCompare(Iterable<?> that) {
        int thatKnownSize = that.knownSize();
        if (thatKnownSize >= 0) {
            return this.sizeCompare(thatKnownSize);
        }
        int thisKnownSize = this.knownSize();
        if (thisKnownSize >= 0) {
            int res = that.sizeCompare(thisKnownSize);
            if (res == Integer.MIN_VALUE) {
                return 1;
            }
            return -res;
        }
        Iterator thisIt = this.iterator();
        Iterator thatIt = that.iterator();
        while (thisIt.hasNext() && thatIt.hasNext()) {
            thisIt.next();
            thatIt.next();
        }
        return Boolean.compare(thisIt.hasNext(), thatIt.hasNext());
    }

    public static /* synthetic */ View view$(IterableOps $this, int from, int until) {
        return $this.view(from, until);
    }

    default public View<A> view(int from, int until) {
        return (View)this.view().slice(from, until);
    }

    public static /* synthetic */ Object transpose$(IterableOps $this, Function1 asIterable) {
        return $this.transpose(asIterable);
    }

    /*
     * WARNING - void declaration
     */
    default public <B> CC transpose(Function1<A, Iterable<B>> asIterable) {
        void fill_elem;
        if (this.isEmpty()) {
            return this.iterableFactory().empty();
        }
        int headSize = ((IterableOnceOps)asIterable.apply(this.head())).size();
        Function0<Builder> & Serializable intersect = (Function0<Builder> & Serializable)() -> this.iterableFactory().newBuilder();
        IndexedSeq$ fill_this = IndexedSeq$.MODULE$;
        View.Fill from_source = new View.Fill(headSize, fill_elem);
        Object var6_5 = null;
        Object var4_4 = null;
        intersect = null;
        Object bs = fill_this.from((IterableOnce)from_source);
        this.iterator().foreach(arg_0 -> IterableOps.$anonfun$transpose$2$adapted(asIterable, headSize, (IndexedSeq)bs, arg_0));
        return this.iterableFactory().from((IterableOnce)bs.map((Function1<Builder, Object> & Serializable)x$1 -> x$1.result()));
    }

    public static /* synthetic */ Object filter$(IterableOps $this, Function1 pred) {
        return $this.filter(pred);
    }

    @Override
    default public C filter(Function1<A, Object> pred) {
        return this.fromSpecific(new View.Filter<A>(this, pred, false));
    }

    public static /* synthetic */ Object filterNot$(IterableOps $this, Function1 pred) {
        return $this.filterNot(pred);
    }

    @Override
    default public C filterNot(Function1<A, Object> pred) {
        return this.fromSpecific(new View.Filter<A>(this, pred, true));
    }

    public static /* synthetic */ scala.collection.WithFilter withFilter$(IterableOps $this, Function1 p) {
        return $this.withFilter(p);
    }

    default public scala.collection.WithFilter<A, CC> withFilter(Function1<A, Object> p) {
        return new WithFilter(this, p);
    }

    public static /* synthetic */ Tuple2 partition$(IterableOps $this, Function1 p) {
        return $this.partition(p);
    }

    default public Tuple2<C, C> partition(Function1<A, Object> p) {
        View.Filter<A> first = new View.Filter<A>(this, p, false);
        View.Filter<A> second = new View.Filter<A>(this, p, true);
        return new Tuple2<C, C>(this.fromSpecific(first), this.fromSpecific(second));
    }

    public static /* synthetic */ Tuple2 splitAt$(IterableOps $this, int n) {
        return $this.splitAt(n);
    }

    @Override
    default public Tuple2<C, C> splitAt(int n) {
        return new Tuple2<C, C>(this.take(n), this.drop(n));
    }

    public static /* synthetic */ Object take$(IterableOps $this, int n) {
        return $this.take(n);
    }

    @Override
    default public C take(int n) {
        return this.fromSpecific(new View.Take(this, n));
    }

    public static /* synthetic */ Object takeRight$(IterableOps $this, int n) {
        return $this.takeRight(n);
    }

    default public C takeRight(int n) {
        return this.fromSpecific(new View.TakeRight(this, n));
    }

    public static /* synthetic */ Object takeWhile$(IterableOps $this, Function1 p) {
        return $this.takeWhile(p);
    }

    @Override
    default public C takeWhile(Function1<A, Object> p) {
        return this.fromSpecific(new View.TakeWhile<A>(this, p));
    }

    public static /* synthetic */ Tuple2 span$(IterableOps $this, Function1 p) {
        return $this.span(p);
    }

    @Override
    default public Tuple2<C, C> span(Function1<A, Object> p) {
        return new Tuple2<C, C>(this.takeWhile(p), this.dropWhile(p));
    }

    public static /* synthetic */ Object drop$(IterableOps $this, int n) {
        return $this.drop(n);
    }

    @Override
    default public C drop(int n) {
        return this.fromSpecific(new View.Drop(this, n));
    }

    public static /* synthetic */ Object dropRight$(IterableOps $this, int n) {
        return $this.dropRight(n);
    }

    default public C dropRight(int n) {
        return this.fromSpecific(new View.DropRight(this, n));
    }

    public static /* synthetic */ Object dropWhile$(IterableOps $this, Function1 p) {
        return $this.dropWhile(p);
    }

    @Override
    default public C dropWhile(Function1<A, Object> p) {
        return this.fromSpecific(new View.DropWhile<A>(this, p));
    }

    public static /* synthetic */ Iterator grouped$(IterableOps $this, int size) {
        return $this.grouped(size);
    }

    default public Iterator<C> grouped(int size) {
        return this.iterator().grouped(size).map((Function1<IterableOnce, Object> & Serializable)(T1 coll) -> this.fromSpecific((IterableOnce)coll));
    }

    public static /* synthetic */ Iterator sliding$(IterableOps $this, int size) {
        return $this.sliding(size);
    }

    default public Iterator<C> sliding(int size) {
        return this.sliding(size, 1);
    }

    public static /* synthetic */ Iterator sliding$(IterableOps $this, int size, int step) {
        return $this.sliding(size, step);
    }

    default public Iterator<C> sliding(int size, int step) {
        return this.iterator().sliding(size, step).map((Function1<IterableOnce, Object> & Serializable)(T1 coll) -> this.fromSpecific((IterableOnce)coll));
    }

    public static /* synthetic */ Object tail$(IterableOps $this) {
        return $this.tail();
    }

    default public C tail() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return this.drop(1);
    }

    public static /* synthetic */ Object init$(IterableOps $this) {
        return $this.init();
    }

    default public C init() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return this.dropRight(1);
    }

    public static /* synthetic */ Object slice$(IterableOps $this, int from, int until) {
        return $this.slice(from, until);
    }

    @Override
    default public C slice(int from, int until) {
        return this.fromSpecific(new View.Drop(new View.Take(this, until), from));
    }

    public static /* synthetic */ Map groupBy$(IterableOps $this, Function1 f) {
        return $this.groupBy(f);
    }

    /*
     * WARNING - void declaration
     */
    default public <K$> Map<K$, C> groupBy(Function1<A, K$> f) {
        scala.collection.mutable.Map m4 = (scala.collection.mutable.Map)Map$.MODULE$.empty();
        Iterator it = this.iterator();
        while (it.hasNext()) {
            Object elem = it.next();
            K$ key = f.apply(elem);
            Builder bldr = (Builder)m4.getOrElseUpdate(key, (Function0<Builder> & Serializable)() -> this.newSpecificBuilder());
            if (bldr == null) {
                throw null;
            }
            bldr.addOne(elem);
        }
        Object result = HashMap$.MODULE$.empty();
        Iterator mapIt = m4.iterator();
        while (mapIt.hasNext()) {
            void var11_11;
            void var10_10;
            Tuple2 tuple2 = (Tuple2)mapIt.next();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Object k = tuple2._1();
            Builder v = (Builder)tuple2._2();
            result = ((HashMap)result).updated((Object)var10_10, var11_11.result());
        }
        return result;
    }

    public static /* synthetic */ Map groupMap$(IterableOps $this, Function1 key, Function1 f) {
        return $this.groupMap(key, f);
    }

    default public <K$, B> Map<K$, CC> groupMap(Function1<A, K$> key, Function1<A, B> f) {
        scala.collection.mutable.Map m4 = (scala.collection.mutable.Map)Map$.MODULE$.empty();
        this.foreach((Function1<Object, Builder> & Serializable)elem -> {
            void $plus$eq_elem;
            Object k = key.apply(elem);
            Builder bldr = (Builder)m4.getOrElseUpdate(k, (Function0<Builder> & Serializable)() -> this.iterableFactory().newBuilder());
            Object r = f.apply(elem);
            if (bldr == null) {
                throw null;
            }
            return (Builder)bldr.addOne($plus$eq_elem);
        });
        public class Scala_collection_IterableOps$Result$1
        extends AbstractFunction1<Tuple2<K, Builder<B, CC>>, BoxedUnit> {
            private Map<K, CC> built;

            public Map<K, CC> built() {
                return this.built;
            }

            public void built_$eq(Map<K, CC> x$1) {
                this.built = x$1;
            }

            public void apply(Tuple2<K, Builder<B, CC>> kv) {
                this.built_$eq((Map)this.built().updated(kv._1(), kv._2().result()));
            }

            public Scala_collection_IterableOps$Result$1(IterableOps $outer) {
                this.built = Map$EmptyMap$.MODULE$;
            }
        }
        Scala_collection_IterableOps$Result$1 result = new Scala_collection_IterableOps$Result$1(null);
        m4.foreach(result);
        return result.built();
    }

    public static /* synthetic */ Map groupMapReduce$(IterableOps $this, Function1 key, Function1 f, Function2 reduce) {
        return $this.groupMapReduce(key, f, reduce);
    }

    default public <K$, B> Map<K$, B> groupMapReduce(Function1<A, K$> key, Function1<A, B> f, Function2<B, B, B> reduce) {
        scala.collection.mutable.Map m4 = (scala.collection.mutable.Map)Map$.MODULE$.empty();
        this.foreach((Function1<Object, Option> & Serializable)elem -> {
            Object r;
            Object k = key.apply(elem);
            Option option = m4.get(k);
            if (option instanceof Some) {
                Object b = ((Some)option).value();
                r = reduce.apply(b, f.apply(elem));
            } else if (None$.MODULE$.equals(option)) {
                r = f.apply(elem);
            } else {
                throw new MatchError(option);
            }
            Object v = r;
            return m4.put(k, v);
        });
        scala.collection.immutable.Map$ toFactory_factory = scala.collection.immutable.Map$.MODULE$;
        Object var5_5 = null;
        return (Map)m4.to(new MapFactory.ToFactory(toFactory_factory));
    }

    public static /* synthetic */ Object scan$(IterableOps $this, Object z, Function2 op) {
        return $this.scan(z, op);
    }

    default public <B> CC scan(B z, Function2<B, B, B> op) {
        return this.scanLeft(z, op);
    }

    public static /* synthetic */ Object scanLeft$(IterableOps $this, Object z, Function2 op) {
        return $this.scanLeft(z, op);
    }

    @Override
    default public <B> CC scanLeft(B z, Function2<B, A, B> op) {
        return this.iterableFactory().from(new View.ScanLeft<A, B>(this, z, op));
    }

    public static /* synthetic */ Object scanRight$(IterableOps $this, Object z, Function2 op) {
        return $this.scanRight(z, op);
    }

    default public <B> CC scanRight(B z, Function2<A, B, B> op) {
        public class Scala_collection_IterableOps$Scanner$1
        extends AbstractFunction1<A, BoxedUnit> {
            private B acc;
            private List<B> scanned;
            private final Function2 op$1;

            public B acc() {
                return this.acc;
            }

            public void acc_$eq(B x$1) {
                this.acc = x$1;
            }

            public List<B> scanned() {
                return this.scanned;
            }

            public void scanned_$eq(List<B> x$1) {
                this.scanned = x$1;
            }

            /*
             * WARNING - void declaration
             */
            public void apply(A x) {
                void $colon$colon_elem;
                this.acc_$eq(this.op$1.apply(x, this.acc()));
                List<B> list = this.scanned();
                B b = this.acc();
                if (list == null) {
                    throw null;
                }
                List<B> $colon$colon_this = list;
                Object var2_3 = null;
                b = null;
                this.scanned_$eq(new $colon$colon<void>($colon$colon_elem, $colon$colon_this));
            }

            public Scala_collection_IterableOps$Scanner$1(IterableOps $outer, Object z$1, Function2 op$1) {
                this.op$1 = op$1;
                this.acc = z$1;
                B b = this.acc();
                Nil$ $colon$colon_this = Nil$.MODULE$;
                this.scanned = new $colon$colon<Nothing$>((Nothing$)b, $colon$colon_this);
            }
        }
        Scala_collection_IterableOps$Scanner$1 scanner = new Scala_collection_IterableOps$Scanner$1(null, z, op);
        this.reversed().foreach(scanner);
        return this.iterableFactory().from(scanner.scanned());
    }

    public static /* synthetic */ Object map$(IterableOps $this, Function1 f) {
        return $this.map(f);
    }

    @Override
    default public <B> CC map(Function1<A, B> f) {
        return this.iterableFactory().from(new View.Map<A, B>(this, f));
    }

    public static /* synthetic */ Object flatMap$(IterableOps $this, Function1 f) {
        return $this.flatMap(f);
    }

    @Override
    default public <B> CC flatMap(Function1<A, IterableOnce<B>> f) {
        return this.iterableFactory().from(new View.FlatMap<A, B>(this, f));
    }

    public static /* synthetic */ Object flatten$(IterableOps $this, Function1 asIterable) {
        return $this.flatten(asIterable);
    }

    @Override
    default public <B> CC flatten(Function1<A, IterableOnce<B>> asIterable) {
        return this.flatMap(asIterable);
    }

    public static /* synthetic */ Object collect$(IterableOps $this, PartialFunction pf) {
        return $this.collect(pf);
    }

    @Override
    default public <B> CC collect(PartialFunction<A, B> pf) {
        return this.iterableFactory().from(new View.Collect<A, B>(this, pf));
    }

    public static /* synthetic */ Tuple2 partitionMap$(IterableOps $this, Function1 f) {
        return $this.partitionMap(f);
    }

    default public <A1, A2> Tuple2<CC, CC> partitionMap(Function1<A, Either<A1, A2>> f) {
        View.LeftPartitionMapped<A, A1, A2> left = new View.LeftPartitionMapped<A, A1, A2>(this, f);
        View.RightPartitionMapped<A, A1, A2> right = new View.RightPartitionMapped<A, A1, A2>(this, f);
        return new Tuple2<CC, CC>(this.iterableFactory().from(left), this.iterableFactory().from(right));
    }

    public static /* synthetic */ Object concat$(IterableOps $this, IterableOnce suffix) {
        return $this.concat(suffix);
    }

    /*
     * WARNING - void declaration
     */
    default public <B> CC concat(IterableOnce<B> suffix) {
        IterableOnce<Object> iterableOnce;
        IterableFactory<CC> iterableFactory = this.iterableFactory();
        if (suffix instanceof Iterable) {
            Iterable iterable = (Iterable)suffix;
            iterableOnce = new View.Concat(this, iterable);
        } else {
            void $plus$plus_xs;
            Iterator iterator2 = this.iterator();
            Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> suffix.iterator();
            if (iterator2 == null) {
                throw null;
            }
            iterableOnce = iterator2.concat($plus$plus_xs);
            intersect = null;
        }
        return iterableFactory.from(iterableOnce);
    }

    public static /* synthetic */ Object $plus$plus$(IterableOps $this, IterableOnce suffix) {
        return $this.$plus$plus(suffix);
    }

    default public <B> CC $plus$plus(IterableOnce<B> suffix) {
        return this.concat(suffix);
    }

    public static /* synthetic */ Object zip$(IterableOps $this, IterableOnce that) {
        return $this.zip(that);
    }

    default public <B> CC zip(IterableOnce<B> that) {
        IterableOnce<Tuple2<Object, Object>> iterableOnce;
        IterableFactory<CC> iterableFactory = this.iterableFactory();
        if (that instanceof Iterable) {
            Iterable iterable = (Iterable)that;
            iterableOnce = new View.Zip(this, iterable);
        } else {
            iterableOnce = this.iterator().zip(that);
        }
        return iterableFactory.from(iterableOnce);
    }

    public static /* synthetic */ Object zipWithIndex$(IterableOps $this) {
        return $this.zipWithIndex();
    }

    @Override
    default public CC zipWithIndex() {
        return this.iterableFactory().from(new View.ZipWithIndex(this));
    }

    public static /* synthetic */ Object zipAll$(IterableOps $this, Iterable that, Object thisElem, Object thatElem) {
        return $this.zipAll(that, thisElem, thatElem);
    }

    default public <A1, B> CC zipAll(Iterable<B> that, A1 thisElem, B thatElem) {
        return this.iterableFactory().from(new View.ZipAll<A1, B>(this, that, thisElem, thatElem));
    }

    public static /* synthetic */ Tuple2 unzip$(IterableOps $this, Function1 asPair) {
        return $this.unzip(asPair);
    }

    default public <A1, A2> Tuple2<CC, CC> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        View.Map first = new View.Map(this, (Function1<Object, Object> & Serializable)x$3 -> ((Tuple2)asPair.apply(x$3))._1());
        View.Map second = new View.Map(this, (Function1<Object, Object> & Serializable)x$4 -> ((Tuple2)asPair.apply(x$4))._2());
        return new Tuple2<CC, CC>(this.iterableFactory().from(first), this.iterableFactory().from(second));
    }

    public static /* synthetic */ Tuple3 unzip3$(IterableOps $this, Function1 asTriple) {
        return $this.unzip3(asTriple);
    }

    default public <A1, A2, A3> Tuple3<CC, CC, CC> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        View.Map first = new View.Map(this, (Function1<Object, Object> & Serializable)x$5 -> ((Tuple3)asTriple.apply(x$5))._1());
        View.Map second = new View.Map(this, (Function1<Object, Object> & Serializable)x$6 -> ((Tuple3)asTriple.apply(x$6))._2());
        View.Map third = new View.Map(this, (Function1<Object, Object> & Serializable)x$7 -> ((Tuple3)asTriple.apply(x$7))._3());
        return new Tuple3<CC, CC, CC>(this.iterableFactory().from(first), this.iterableFactory().from(second), this.iterableFactory().from(third));
    }

    public static /* synthetic */ Iterator tails$(IterableOps $this) {
        return $this.tails();
    }

    /*
     * WARNING - void declaration
     */
    default public Iterator<C> tails() {
        void iterateUntilEmpty_$plus$plus_xs;
        Function1<Iterable, Iterable> & Serializable iterateUntilEmpty_f = (Function1<Iterable, Iterable> & Serializable)x$8 -> (Iterable)x$8.tail();
        Iterable<A> iterateUntilEmpty_iterate_start = this.toIterable();
        Object var4_2 = null;
        Iterator iterateUntilEmpty_it = new AbstractIterator<T>(iterateUntilEmpty_iterate_start, iterateUntilEmpty_f){
            private boolean first;
            private T acc;
            private final Function1 f$6;

            public boolean hasNext() {
                return true;
            }

            public T next() {
                if (this.first) {
                    this.first = false;
                } else {
                    this.acc = this.f$6.apply(this.acc);
                }
                return this.acc;
            }
            {
                this.f$6 = f$6;
                this.first = true;
                this.acc = start$3;
            }
        }.takeWhile((Function1<Iterable, Object> & Serializable)x$10 -> BoxesRunTime.boxToBoolean(x$10.nonEmpty()));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> {
            Object single_a = Iterable$.MODULE$.empty();
            return new AbstractIterator<A>(single_a){
                private boolean consumed;
                private final Object a$1;

                public boolean hasNext() {
                    return !this.consumed;
                }

                public A next() {
                    if (this.consumed) {
                        return (A)Iterator$.scala$collection$Iterator$$_empty.next();
                    }
                    this.consumed = true;
                    return (A)this.a$1;
                }

                public Iterator<A> sliceIterator(int from, int until) {
                    if (this.consumed || from > 0 || until == 0) {
                        return Iterator$.scala$collection$Iterator$$_empty;
                    }
                    return this;
                }
                {
                    this.a$1 = a$1;
                    this.consumed = false;
                }
            };
        };
        if (iterateUntilEmpty_it == null) {
            throw null;
        }
        intersect = null;
        return iterateUntilEmpty_it.concat(iterateUntilEmpty_$plus$plus_xs).map((Function1<IterableOnce, Object> & Serializable)coll -> this.fromSpecific((IterableOnce)coll));
    }

    public static /* synthetic */ Iterator inits$(IterableOps $this) {
        return $this.inits();
    }

    /*
     * WARNING - void declaration
     */
    default public Iterator<C> inits() {
        void iterateUntilEmpty_$plus$plus_xs;
        Function1<Iterable, Iterable> & Serializable iterateUntilEmpty_f = (Function1<Iterable, Iterable> & Serializable)x$9 -> (Iterable)x$9.init();
        Iterable<A> iterateUntilEmpty_iterate_start = this.toIterable();
        Object var4_2 = null;
        Iterator iterateUntilEmpty_it = new /* invalid duplicate definition of identical inner class */.takeWhile((Function1<Iterable, Object> & Serializable)x$10 -> BoxesRunTime.boxToBoolean(x$10.nonEmpty()));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> {
            Object single_a = Iterable$.MODULE$.empty();
            return new /* invalid duplicate definition of identical inner class */;
        };
        if (iterateUntilEmpty_it == null) {
            throw null;
        }
        intersect = null;
        return iterateUntilEmpty_it.concat(iterateUntilEmpty_$plus$plus_xs).map((Function1<IterableOnce, Object> & Serializable)coll -> this.fromSpecific((IterableOnce)coll));
    }

    public static /* synthetic */ Object tapEach$(IterableOps $this, Function1 f) {
        return $this.tapEach(f);
    }

    @Override
    default public <U> C tapEach(Function1<A, U> f) {
        return this.fromSpecific(new View.Map(this, (Function1<Object, Object> & Serializable)a -> {
            f.apply(a);
            return a;
        }));
    }

    /*
     * WARNING - void declaration
     */
    private Iterator<C> iterateUntilEmpty(Function1<Iterable<A>, Iterable<A>> f) {
        void $plus$plus_xs;
        Iterable<A> iterate_start = this.toIterable();
        Object var4_2 = null;
        Iterator it = new /* invalid duplicate definition of identical inner class */.takeWhile((Function1<Iterable, Object> & Serializable)x$10 -> BoxesRunTime.boxToBoolean(x$10.nonEmpty()));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> {
            Object single_a = Iterable$.MODULE$.empty();
            return new /* invalid duplicate definition of identical inner class */;
        };
        if (it == null) {
            throw null;
        }
        intersect = null;
        return it.concat($plus$plus_xs).map((Function1<IterableOnce, Object> & Serializable)coll -> this.fromSpecific((IterableOnce)coll));
    }

    public static /* synthetic */ Object $plus$plus$colon$(IterableOps $this, IterableOnce that) {
        return $this.$plus$plus$colon(that);
    }

    /*
     * WARNING - void declaration
     */
    default public <B> CC $plus$plus$colon(IterableOnce<B> that) {
        IterableOnce<Object> iterableOnce;
        IterableFactory<CC> iterableFactory = this.iterableFactory();
        if (that instanceof Iterable) {
            Iterable iterable = (Iterable)that;
            iterableOnce = new View.Concat(iterable, this);
        } else {
            void $plus$plus_xs;
            Iterator<B> iterator2 = that.iterator();
            Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> this.iterator();
            if (iterator2 == null) {
                throw null;
            }
            iterableOnce = iterator2.concat($plus$plus_xs);
            intersect = null;
        }
        return iterableFactory.from(iterableOnce);
    }

    private static Nothing$ fail$1() {
        throw new IllegalArgumentException("transpose requires all collections have the same size");
    }

    public static /* synthetic */ void $anonfun$transpose$3(IntRef i$1, int headSize$1, IndexedSeq bs$1, Object x) {
        if (i$1.elem >= headSize$1) {
            throw new IllegalArgumentException("transpose requires all collections have the same size");
        }
        Growable growable = (Growable)bs$1.apply(i$1.elem);
        if (growable == null) {
            throw null;
        }
        growable.addOne(x);
        ++i$1.elem;
    }

    public static /* synthetic */ void $anonfun$transpose$2(Function1 asIterable$1, int headSize$1, IndexedSeq bs$1, Object xs) {
        int create_e = 0;
        IntRef i = new IntRef(create_e);
        ((IterableOnceOps)asIterable$1.apply(xs)).foreach((Function1<Object, Object> & Serializable)x -> {
            IterableOps.$anonfun$transpose$3(i, headSize$1, bs$1, x);
            return BoxedUnit.UNIT;
        });
        if (i.elem != headSize$1) {
            throw new IllegalArgumentException("transpose requires all collections have the same size");
        }
    }

    public static void $init$(IterableOps $this) {
    }

    public static /* synthetic */ Object $anonfun$transpose$2$adapted(Function1 asIterable$1, int headSize$1, IndexedSeq bs$1, Object xs) {
        IterableOps.$anonfun$transpose$2(asIterable$1, headSize$1, bs$1, xs);
        return BoxedUnit.UNIT;
    }

    public static final class SizeCompareOps {
        private final IterableOps<?, Object, ?> it;

        public IterableOps<?, Object, ?> it() {
            return this.it;
        }

        public boolean $less(int size) {
            return this.it().sizeCompare(size) < 0;
        }

        public boolean $less$eq(int size) {
            return this.it().sizeCompare(size) <= 0;
        }

        public boolean $eq$eq(int size) {
            return this.it().sizeCompare(size) == 0;
        }

        public boolean $bang$eq(int size) {
            return this.it().sizeCompare(size) != 0;
        }

        public boolean $greater$eq(int size) {
            return this.it().sizeCompare(size) >= 0;
        }

        public boolean $greater(int size) {
            return this.it().sizeCompare(size) > 0;
        }

        public int hashCode() {
            return this.it().hashCode();
        }

        public boolean equals(Object x$1) {
            return IterableOps$SizeCompareOps$.MODULE$.equals$extension(this.it(), x$1);
        }

        public SizeCompareOps(IterableOps<?, Object, ?> it) {
            this.it = it;
        }
    }

    public static class WithFilter<A, CC>
    extends scala.collection.WithFilter<A, CC> {
        private static final long serialVersionUID = 3L;
        private final IterableOps<A, CC, ?> self;
        private final Function1<A, Object> p;

        public Iterable<A> filtered() {
            return new View.Filter<A>(this.self, this.p, false);
        }

        @Override
        public <B> CC map(Function1<A, B> f) {
            return this.self.iterableFactory().from(new View.Map<A, B>(this.filtered(), f));
        }

        @Override
        public <B> CC flatMap(Function1<A, IterableOnce<B>> f) {
            return this.self.iterableFactory().from(new View.FlatMap<A, B>(this.filtered(), f));
        }

        @Override
        public <U> void foreach(Function1<A, U> f) {
            this.filtered().foreach(f);
        }

        @Override
        public WithFilter<A, CC> withFilter(Function1<A, Object> q) {
            return new WithFilter<A, CC>(this.self, (Function1<Object, Object> & Serializable)a -> BoxesRunTime.boxToBoolean(WithFilter.$anonfun$withFilter$1(this, q, a)));
        }

        public static final /* synthetic */ boolean $anonfun$withFilter$1(WithFilter $this, Function1 q$1, Object a) {
            return BoxesRunTime.unboxToBoolean($this.p.apply(a)) && BoxesRunTime.unboxToBoolean(q$1.apply(a));
        }

        public WithFilter(IterableOps<A, CC, ?> self, Function1<A, Object> p) {
            this.self = self;
            this.p = p;
        }
    }
}

