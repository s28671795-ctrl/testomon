/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Arrays;
import java.util.NoSuchElementException;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.Hashing$;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.immutable.HashCollisionMapNode;
import scala.collection.immutable.HashMapBuilder;
import scala.collection.immutable.MapNode;
import scala.collection.immutable.MapNode$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Node;
import scala.collection.immutable.Node$;
import scala.collection.immutable.Vector$;
import scala.collection.mutable.AbstractBuffer;
import scala.collection.mutable.Queue;
import scala.collection.mutable.Queue$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\rud\u0001B\u001e=\r\rC\u0001\u0002\u0017\u0001\u0003\u0002\u0004%\t!\u0017\u0005\t;\u0002\u0011\t\u0019!C\u0001=\"AA\r\u0001B\u0001B\u0003&!\f\u0003\u0005f\u0001\t\u0005\r\u0011\"\u0001Z\u0011!1\u0007A!a\u0001\n\u00039\u0007\u0002C5\u0001\u0005\u0003\u0005\u000b\u0015\u0002.\t\u0011)\u0004!\u00111A\u0005\u0002-D\u0001b\u001c\u0001\u0003\u0002\u0004%\t\u0001\u001d\u0005\te\u0002\u0011\t\u0011)Q\u0005Y\"A1\u000f\u0001BA\u0002\u0013\u0005A\u000f\u0003\u0005w\u0001\t\u0005\r\u0011\"\u0001x\u0011!I\bA!A!B\u0013)\b\u0002\u0003>\u0001\u0005\u0003\u0007I\u0011A-\t\u0011m\u0004!\u00111A\u0005\u0002qD\u0001B \u0001\u0003\u0002\u0003\u0006KA\u0017\u0005\t\u007f\u0002\u0011\t\u0019!C\u00013\"Q\u0011\u0011\u0001\u0001\u0003\u0002\u0004%\t!a\u0001\t\u0013\u0005\u001d\u0001A!A!B\u0013Q\u0006bBA\u0005\u0001\u0011\u0005\u00111\u0002\u0005\b\u00037\u0001A\u0011AA\u000f\u0011\u001d\t\u0019\u0003\u0001C\u0001\u0003KAq!!\u000b\u0001\t\u0003\tY\u0003C\u0004\u00026\u0001!\t%a\u000e\t\u000f\u0005m\u0002\u0001\"\u0001\u0002>!9\u0011\u0011\t\u0001\u0005\u0002\u0005\r\u0003bBA+\u0001\u0011\u0005\u0011q\u000b\u0005\b\u0003O\u0002A\u0011IA5\u0011\u001d\t)\b\u0001C\u0001\u0003oBq!!&\u0001\t\u0003\n9\nC\u0004\u0002(\u0002!\t!!+\t\u000f\u0005\r\u0007\u0001\"\u0001\u0002F\"9\u00111\u001c\u0001\u0005\u0002\u0005u\u0007bBAx\u0001\u0011\u0005\u0011\u0011\u001f\u0005\b\u0005;\u0001A\u0011\u0001B\u0010\u0011\u0019\u0011\t\u0003\u0001C\u00013\"9!1\u0005\u0001\u0005\u0002\t}\u0001B\u0002B\u0013\u0001\u0011\u0005\u0011\fC\u0004\u0003(\u0001!\tA!\u000b\t\u000f\t=\u0002\u0001\"\u0001\u00032!9!Q\u0007\u0001\u0005\u0002\t]\u0002b\u0002B&\u0001\u0011\u0005!Q\n\u0005\b\u0005G\u0002A\u0011\u0001B3\u0011\u001d\u0011I\b\u0001C\u0001\u0005wBqA!!\u0001\t\u0003\u0011\u0019\tC\u0004\u0003\u0018\u0002!\tA!'\t\u000f\t-\u0006\u0001\"\u0001\u0003.\"9!q\u0018\u0001\u0005B\t\u0005\u0007b\u0002Bj\u0001\u0011\u0005#Q\u001b\u0005\b\u0005K\u0004A\u0011\tBt\u0011\u001d\u0011\t\u0010\u0001C!\u0005gDqa!\u0002\u0001\t\u0003\u001a9\u0001C\u0004\u0004\u0018\u0001!\te!\u0007\t\u000f\r]\u0002\u0001\"\u0011\u0004:!91Q\b\u0001\u0005\n\r}\u0002bBB+\u0001\u0011\u00053q\u000b\u0005\b\u00073\u0002A\u0011IB.\u0011\u001d\u0019Y\u0007\u0001C!\u0007[Bqaa\u001c\u0001\t\u0003\u001a\tH\u0001\u000bCSRl\u0017\r]%oI\u0016DX\rZ'ba:{G-\u001a\u0006\u0003{y\n\u0011\"[7nkR\f'\r\\3\u000b\u0005}\u0002\u0015AC2pY2,7\r^5p]*\t\u0011)A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007\u0011[ek\u0005\u0002\u0001\u000bB!aiR%V\u001b\u0005a\u0014B\u0001%=\u0005\u001di\u0015\r\u001d(pI\u0016\u0004\"AS&\r\u0001\u0011)A\n\u0001b\u0001\u001b\n\t1*\u0005\u0002O%B\u0011q\nU\u0007\u0002\u0001&\u0011\u0011\u000b\u0011\u0002\b\u001d>$\b.\u001b8h!\ty5+\u0003\u0002U\u0001\n\u0019\u0011I\\=\u0011\u0005)3FAB,\u0001\t\u000b\u0007QJA\u0001W\u0003\u001d!\u0017\r^1NCB,\u0012A\u0017\t\u0003\u001fnK!\u0001\u0018!\u0003\u0007%sG/A\u0006eCR\fW*\u00199`I\u0015\fHCA0c!\ty\u0005-\u0003\u0002b\u0001\n!QK\\5u\u0011\u001d\u0019'!!AA\u0002i\u000b1\u0001\u001f\u00132\u0003!!\u0017\r^1NCB\u0004\u0013a\u00028pI\u0016l\u0015\r]\u0001\f]>$W-T1q?\u0012*\u0017\u000f\u0006\u0002`Q\"91-BA\u0001\u0002\u0004Q\u0016\u0001\u00038pI\u0016l\u0015\r\u001d\u0011\u0002\u000f\r|g\u000e^3oiV\tA\u000eE\u0002P[JK!A\u001c!\u0003\u000b\u0005\u0013(/Y=\u0002\u0017\r|g\u000e^3oi~#S-\u001d\u000b\u0003?FDqa\u0019\u0005\u0002\u0002\u0003\u0007A.\u0001\u0005d_:$XM\u001c;!\u00039y'/[4j]\u0006d\u0007*Y:iKN,\u0012!\u001e\t\u0004\u001f6T\u0016AE8sS\u001eLg.\u00197ICNDWm]0%KF$\"a\u0018=\t\u000f\r\\\u0011\u0011!a\u0001k\u0006yqN]5hS:\fG\u000eS1tQ\u0016\u001c\b%\u0001\u0003tSj,\u0017\u0001C:ju\u0016|F%Z9\u0015\u0005}k\bbB2\u000f\u0003\u0003\u0005\rAW\u0001\u0006g&TX\rI\u0001\u0019G\u0006\u001c\u0007.\u001a3KCZ\f7*Z=TKRD\u0015m\u001d5D_\u0012,\u0017\u0001H2bG\",GMS1wC.+\u0017pU3u\u0011\u0006\u001c\bnQ8eK~#S-\u001d\u000b\u0004?\u0006\u0015\u0001bB2\u0012\u0003\u0003\u0005\rAW\u0001\u001aG\u0006\u001c\u0007.\u001a3KCZ\f7*Z=TKRD\u0015m\u001d5D_\u0012,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u000f\u0003\u001b\ty!!\u0005\u0002\u0014\u0005U\u0011qCA\r!\u00111\u0005!S+\t\u000ba\u001b\u0002\u0019\u0001.\t\u000b\u0015\u001c\u0002\u0019\u0001.\t\u000b)\u001c\u0002\u0019\u00017\t\u000bM\u001c\u0002\u0019A;\t\u000bi\u001c\u0002\u0019\u0001.\t\u000b}\u001c\u0002\u0019\u0001.\u0002\r\u001d,GoS3z)\rI\u0015q\u0004\u0005\u0007\u0003C!\u0002\u0019\u0001.\u0002\u000b%tG-\u001a=\u0002\u0011\u001d,GOV1mk\u0016$2!VA\u0014\u0011\u0019\t\t#\u0006a\u00015\u0006Qq-\u001a;QCfdw.\u00193\u0015\t\u00055\u00121\u0007\t\u0006\u001f\u0006=\u0012*V\u0005\u0004\u0003c\u0001%A\u0002+va2,'\u0007\u0003\u0004\u0002\"Y\u0001\rAW\u0001\bO\u0016$\b*Y:i)\rQ\u0016\u0011\b\u0005\u0007\u0003C9\u0002\u0019\u0001.\u0002\u000f\u001d,GOT8eKR\u0019Q)a\u0010\t\r\u0005\u0005\u0002\u00041\u0001[\u0003\u0015\t\u0007\u000f\u001d7z)%)\u0016QIA%\u0003\u001b\n\t\u0006\u0003\u0004\u0002He\u0001\r!S\u0001\u0004W\u0016L\bBBA&3\u0001\u0007!,\u0001\u0007pe&<\u0017N\\1m\u0011\u0006\u001c\b\u000e\u0003\u0004\u0002Pe\u0001\rAW\u0001\bW\u0016L\b*Y:i\u0011\u0019\t\u0019&\u0007a\u00015\u0006)1\u000f[5gi\u0006\u0019q-\u001a;\u0015\u0015\u0005e\u0013qLA1\u0003G\n)\u0007\u0005\u0003P\u00037*\u0016bAA/\u0001\n1q\n\u001d;j_:Da!a\u0012\u001b\u0001\u0004I\u0005BBA&5\u0001\u0007!\f\u0003\u0004\u0002Pi\u0001\rA\u0017\u0005\u0007\u0003'R\u0002\u0019\u0001.\u0002\u0011\u001d,G\u000fV;qY\u0016$\"\"!\f\u0002l\u00055\u0014qNA:\u0011\u0019\t9e\u0007a\u0001\u0013\"1\u00111J\u000eA\u0002iCa!!\u001d\u001c\u0001\u0004Q\u0016\u0001\u00025bg\"Da!a\u0015\u001c\u0001\u0004Q\u0016!C4fi>\u0013X\t\\:f+\u0011\tI(! \u0015\u0019\u0005m\u00141QAC\u0003\u000f\u000bI)a#\u0011\u0007)\u000bi\bB\u0004\u0002\u0000q\u0011\r!!!\u0003\u0005Y\u000b\u0014CA+S\u0011\u0019\t9\u0005\ba\u0001\u0013\"1\u00111\n\u000fA\u0002iCa!a\u0014\u001d\u0001\u0004Q\u0006BBA*9\u0001\u0007!\f\u0003\u0005\u0002\u000er!\t\u0019AAH\u0003\u00051\u0007#B(\u0002\u0012\u0006m\u0014bAAJ\u0001\nAAHY=oC6,g(A\u0006d_:$\u0018-\u001b8t\u0017\u0016LHCCAM\u0003?\u000b\t+a)\u0002&B\u0019q*a'\n\u0007\u0005u\u0005IA\u0004C_>dW-\u00198\t\r\u0005\u001dS\u00041\u0001J\u0011\u0019\tY%\ba\u00015\"1\u0011qJ\u000fA\u0002iCa!a\u0015\u001e\u0001\u0004Q\u0016aB;qI\u0006$X\rZ\u000b\u0005\u0003W\u000b\t\f\u0006\b\u0002.\u0006M\u0016QWA]\u0003w\u000bi,a0\u0011\u000b\u0019\u0003\u0011*a,\u0011\u0007)\u000b\t\fB\u0004\u0002\u0000y\u0011\r!!!\t\r\u0005\u001dc\u00041\u0001J\u0011\u001d\t9L\ba\u0001\u0003_\u000bQA^1mk\u0016Da!a\u0013\u001f\u0001\u0004Q\u0006BBA(=\u0001\u0007!\f\u0003\u0004\u0002Ty\u0001\rA\u0017\u0005\b\u0003\u0003t\u0002\u0019AAM\u00031\u0011X\r\u001d7bG\u00164\u0016\r\\;f\u0003i)\b\u000fZ1uK^KG\u000f[*iC2dwn^'vi\u0006$\u0018n\u001c8t+\u0011\t9-a4\u0015\u001bi\u000bI-a3\u0002R\u0006M\u0017Q[Al\u0011\u0019\t9e\ba\u0001\u0013\"9\u0011qW\u0010A\u0002\u00055\u0007c\u0001&\u0002P\u00129\u0011qP\u0010C\u0002\u0005\u0005\u0005BBA&?\u0001\u0007!\f\u0003\u0004\u0002P}\u0001\rA\u0017\u0005\u0007\u0003'z\u0002\u0019\u0001.\t\r\u0005ew\u00041\u0001[\u0003]\u0019\b.\u00197m_^d\u00170T;uC\ndWMT8eK6\u000b\u0007/A\u0004sK6|g/\u001a3\u0016\t\u0005}\u0017Q\u001d\u000b\u000b\u0003C\f9/!;\u0002l\u00065\b#\u0002$\u0001\u0013\u0006\r\bc\u0001&\u0002f\u00129\u0011q\u0010\u0011C\u0002\u0005\u0005\u0005BBA$A\u0001\u0007\u0011\n\u0003\u0004\u0002L\u0001\u0002\rA\u0017\u0005\u0007\u0003\u001f\u0002\u0003\u0019\u0001.\t\r\u0005M\u0003\u00051\u0001[\u0003MiWM]4f)^|7*Z=WC2\u0004\u0016-\u001b:t+\u0011\t\u00190!?\u0015)\u0005U\u00181`A\u0000\u0005\u0007\u00119Aa\u0003\u0003\u0010\tM!q\u0003B\u000e!\u00151u)SA|!\rQ\u0015\u0011 \u0003\b\u0003\u007f\n#\u0019AAA\u0011\u0019\ti0\ta\u0001\u0013\u0006!1.Z=1\u0011\u001d\u0011\t!\ta\u0001\u0003o\faA^1mk\u0016\u0004\u0004B\u0002B\u0003C\u0001\u0007!,A\u0007pe&<\u0017N\\1m\u0011\u0006\u001c\b\u000e\r\u0005\u0007\u0005\u0013\t\u0003\u0019\u0001.\u0002\u0011-,\u0017\u0010S1tQBBaA!\u0004\"\u0001\u0004I\u0015\u0001B6fsFBqA!\u0005\"\u0001\u0004\t90\u0001\u0004wC2,X-\r\u0005\u0007\u0005+\t\u0003\u0019\u0001.\u0002\u001b=\u0014\u0018nZ5oC2D\u0015m\u001d52\u0011\u0019\u0011I\"\ta\u00015\u0006A1.Z=ICND\u0017\u0007\u0003\u0004\u0002T\u0005\u0002\rAW\u0001\tQ\u0006\u001chj\u001c3fgV\u0011\u0011\u0011T\u0001\n]>$W-\u0011:jif\f!\u0002[1t!\u0006LHn\\1e\u00031\u0001\u0018-\u001f7pC\u0012\f%/\u001b;z\u0003%!\u0017\r^1J]\u0012,\u0007\u0010F\u0002[\u0005WAaA!\f'\u0001\u0004Q\u0016A\u00022jiB|7/A\u0005o_\u0012,\u0017J\u001c3fqR\u0019!La\r\t\r\t5r\u00051\u0001[\u0003=\u0019w\u000e]=B]\u0012\u001cV\r\u001e,bYV,W\u0003\u0002B\u001d\u0005\u007f!\u0002Ba\u000f\u0003B\t\r#q\t\t\u0006\r\u0002I%Q\b\t\u0004\u0015\n}BaBA@Q\t\u0007\u0011\u0011\u0011\u0005\u0007\u0005[A\u0003\u0019\u0001.\t\r\t\u0015\u0003\u00061\u0001J\u0003\u0019qWm^&fs\"9!\u0011\n\u0015A\u0002\tu\u0012\u0001\u00038foZ\u000bG.^3\u0002\u001d\r|\u0007/_!oIN+GOT8eKV!!q\nB+)!\u0011\tFa\u0016\u0003Z\t}\u0003#\u0002$\u0001\u0013\nM\u0003c\u0001&\u0003V\u00119\u0011qP\u0015C\u0002\u0005\u0005\u0005B\u0002B\u0017S\u0001\u0007!\fC\u0004\u0003\\%\u0002\rA!\u0018\u0002\u000f=dGMT8eKB)aiR%\u0003T!9!\u0011M\u0015A\u0002\tu\u0013a\u00028fo:{G-Z\u0001\u0013G>\u0004\u00180\u00118e\u0013:\u001cXM\u001d;WC2,X-\u0006\u0003\u0003h\t5D\u0003\u0004B5\u0005_\u0012\tHa\u001d\u0003v\t]\u0004#\u0002$\u0001\u0013\n-\u0004c\u0001&\u0003n\u00119\u0011q\u0010\u0016C\u0002\u0005\u0005\u0005B\u0002B\u0017U\u0001\u0007!\f\u0003\u0004\u0002H)\u0002\r!\u0013\u0005\u0007\u0003\u0017R\u0003\u0019\u0001.\t\r\u0005=#\u00061\u0001[\u0011\u001d\t9L\u000ba\u0001\u0005W\n!cY8qs\u0006sGMU3n_Z,g+\u00197vKR1\u0011Q\u0002B?\u0005\u007fBaA!\f,\u0001\u0004Q\u0006BBA(W\u0001\u0007!,\u0001\u0010nS\u001e\u0014\u0018\r^3Ge>l\u0017J\u001c7j]\u0016$vNT8eK&s\u0007\u000b\\1dKV!!Q\u0011BK)!\u00119I!#\u0003\f\n5U\"\u0001\u0001\t\r\t5B\u00061\u0001[\u0011\u0019\ty\u0005\fa\u00015\"9!q\u0012\u0017A\u0002\tE\u0015\u0001\u00028pI\u0016\u0004RAR$J\u0005'\u00032A\u0013BK\t\u001d\ty\b\fb\u0001\u0003\u0003\u000badY8qs\u0006sG-T5he\u0006$XM\u0012:p[&sG.\u001b8f)>tu\u000eZ3\u0016\t\tm%\u0011\u0015\u000b\t\u0005;\u0013\u0019K!*\u0003(B)a\tA%\u0003 B\u0019!J!)\u0005\u000f\u0005}TF1\u0001\u0002\u0002\"1!QF\u0017A\u0002iCa!a\u0014.\u0001\u0004Q\u0006b\u0002BH[\u0001\u0007!\u0011\u0016\t\u0006\r\u001eK%qT\u0001\u001fG>\u0004\u00180\u00118e\u001b&<'/\u0019;f\rJ|WNT8eKR{\u0017J\u001c7j]\u0016,BAa,\u00036RA!\u0011\u0017B\\\u0005s\u0013i\fE\u0003G\u0001%\u0013\u0019\fE\u0002K\u0005k#q!a /\u0005\u0004\t\t\t\u0003\u0004\u0003.9\u0002\rA\u0017\u0005\b\u00057r\u0003\u0019\u0001B^!\u00151u)\u0013BZ\u0011\u001d\u0011yI\fa\u0001\u0005w\u000bqAZ8sK\u0006\u001c\u0007.\u0006\u0003\u0003D\n=GcA0\u0003F\"9\u0011QR\u0018A\u0002\t\u001d\u0007cB(\u0003J\u00065\"QZ\u0005\u0004\u0005\u0017\u0004%!\u0003$v]\u000e$\u0018n\u001c82!\rQ%q\u001a\u0003\u0007\u0005#|#\u0019A'\u0003\u0003U\u000bABZ8sK\u0006\u001c\u0007.\u00128uef,BAa6\u0003dR\u0019qL!7\t\u000f\u00055\u0005\u00071\u0001\u0003\\B9qJ!8J+\n\u0005\u0018b\u0001Bp\u0001\nIa)\u001e8di&|gN\r\t\u0004\u0015\n\rHA\u0002Bia\t\u0007Q*A\bg_J,\u0017m\u00195XSRD\u0007*Y:i)\ry&\u0011\u001e\u0005\b\u0003\u001b\u000b\u0004\u0019\u0001Bv!\u001dy%Q^%V5~K1Aa<A\u0005%1UO\\2uS>t7'A\u0004ck&dG\rV8\u0016\t\tU81\u0001\u000b\u0004?\n]\bb\u0002B}e\u0001\u0007!1`\u0001\bEVLG\u000eZ3s!\u00191%Q`%\u0004\u0002%\u0019!q \u001f\u0003\u001d!\u000b7\u000f['ba\n+\u0018\u000e\u001c3feB\u0019!ja\u0001\u0005\u000f\u0005}$G1\u0001\u0002\u0002\u0006IAO]1og\u001a|'/\\\u000b\u0005\u0007\u0013\u0019y\u0001\u0006\u0003\u0004\f\rM\u0001#\u0002$\u0001\u0013\u000e5\u0001c\u0001&\u0004\u0010\u001111\u0011C\u001aC\u00025\u0013\u0011a\u0016\u0005\b\u0003\u001b\u001b\u0004\u0019AB\u000b!\u001dy%Q\\%V\u0007\u001b\t\u0011\"\\3sO\u0016Le\u000e^8\u0016\t\rm1\u0011\u0006\u000b\t\u0007;\u0019Yc!\r\u00046Q\u0019qla\b\t\u000f\r\u0005B\u00071\u0001\u0004$\u00051Q.\u001a:hK\u001a\u0004\u0012b\u0014Bo\u0003[\u0019)c!\n\u0011\r=\u000by#SB\u0014!\rQ5\u0011\u0006\u0003\b\u0003\u007f\"$\u0019AAA\u0011\u001d\u0019i\u0003\u000ea\u0001\u0007_\tA\u0001\u001e5biB)aiR%\u0004(!9!\u0011 \u001bA\u0002\rM\u0002C\u0002$\u0003~&\u001b9\u0003\u0003\u0004\u0002TQ\u0002\rAW\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005e51\b\u0005\u0007\u0007[)\u0004\u0019\u0001*\u0002'\u0011,W\r]\"p]R,g\u000e^#rk\u0006d\u0017\u000e^=\u0015\u0011\u0005e5\u0011IB#\u0007\u0013Baaa\u00117\u0001\u0004a\u0017AA12\u0011\u0019\u00199E\u000ea\u0001Y\u0006\u0011\u0011M\r\u0005\u0007\u0007\u00172\u0004\u0019\u0001.\u0002\r1,gn\u001a;iQ\r14q\n\t\u0004\u001f\u000eE\u0013bAB*\u0001\n1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u00025\u000611m\u001c8dCR,Ba!\u0018\u0004dQ11qLB3\u0007S\u0002RA\u0012\u0001J\u0007C\u00022ASB2\t\u001d\ty\b\u000fb\u0001\u0003\u0003Cqa!\f9\u0001\u0004\u00199\u0007E\u0003G\u000f&\u001b\t\u0007\u0003\u0004\u0002Ta\u0002\rAW\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002\u0002\u000e\u0005Qa-\u001b7uKJLU\u000e\u001d7\u0015\r\u0005511OB=\u0011\u001d\u0019)H\u000fa\u0001\u0007o\nA\u0001\u001d:fIB9qJ!3\u0002.\u0005e\u0005bBB>u\u0001\u0007\u0011\u0011T\u0001\bM2L\u0007\u000f]3e\u0001")
public final class BitmapIndexedMapNode<K, V>
extends MapNode<K, V> {
    private int dataMap;
    private int nodeMap;
    private Object[] content;
    private int[] originalHashes;
    private int size;
    private int cachedJavaKeySetHashCode;

    public int dataMap() {
        return this.dataMap;
    }

    public void dataMap_$eq(int x$1) {
        this.dataMap = x$1;
    }

    public int nodeMap() {
        return this.nodeMap;
    }

    public void nodeMap_$eq(int x$1) {
        this.nodeMap = x$1;
    }

    public Object[] content() {
        return this.content;
    }

    public void content_$eq(Object[] x$1) {
        this.content = x$1;
    }

    public int[] originalHashes() {
        return this.originalHashes;
    }

    public void originalHashes_$eq(int[] x$1) {
        this.originalHashes = x$1;
    }

    @Override
    public int size() {
        return this.size;
    }

    public void size_$eq(int x$1) {
        this.size = x$1;
    }

    @Override
    public int cachedJavaKeySetHashCode() {
        return this.cachedJavaKeySetHashCode;
    }

    public void cachedJavaKeySetHashCode_$eq(int x$1) {
        this.cachedJavaKeySetHashCode = x$1;
    }

    @Override
    public K getKey(int index) {
        return (K)this.content()[2 * index];
    }

    @Override
    public V getValue(int index) {
        return (V)this.content()[2 * index + 1];
    }

    @Override
    public Tuple2<K, V> getPayload(int index) {
        return new Tuple2<Object, Object>(this.content()[2 * index], this.content()[2 * index + 1]);
    }

    @Override
    public int getHash(int index) {
        return this.originalHashes()[index];
    }

    @Override
    public MapNode<K, V> getNode(int index) {
        return (MapNode)this.content()[this.content().length - 1 - index];
    }

    @Override
    public V apply(K key, int originalHash, int keyHash, int shift) {
        int mask = keyHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            if (BoxesRunTime.equals(key, this.content()[2 * index])) {
                return (V)this.content()[2 * index + 1];
            }
            throw new NoSuchElementException(new StringBuilder(15).append("key not found: ").append(key).toString());
        }
        if ((this.nodeMap() & bitpos) != 0) {
            return ((MapNode)this.getNode(Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos))).apply(key, originalHash, keyHash, shift + 5);
        }
        throw new NoSuchElementException(new StringBuilder(15).append("key not found: ").append(key).toString());
    }

    @Override
    public Option<V> get(K key, int originalHash, int keyHash, int shift) {
        int mask = keyHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            Object key0 = this.content()[2 * index];
            if (BoxesRunTime.equals(key, key0)) {
                return new Some<Object>(this.content()[2 * index + 1]);
            }
            return None$.MODULE$;
        }
        if ((this.nodeMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            return ((MapNode)this.getNode(index)).get(key, originalHash, keyHash, shift + 5);
        }
        return None$.MODULE$;
    }

    @Override
    public Tuple2<K, V> getTuple(K key, int originalHash, int hash, int shift) {
        int mask = hash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            Object payload = this.getPayload(index);
            if (BoxesRunTime.equals(key, ((Tuple2)payload)._1())) {
                return payload;
            }
            throw new NoSuchElementException();
        }
        if ((this.nodeMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            return ((MapNode)this.getNode(index)).getTuple(key, originalHash, hash, shift + 5);
        }
        throw new NoSuchElementException();
    }

    @Override
    public <V1> V1 getOrElse(K key, int originalHash, int keyHash, int shift, Function0<V1> f) {
        int mask = keyHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            Object key0 = this.content()[2 * index];
            if (BoxesRunTime.equals(key, key0)) {
                return (V1)this.content()[2 * index + 1];
            }
            return f.apply();
        }
        if ((this.nodeMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            return ((MapNode)this.getNode(index)).getOrElse(key, originalHash, keyHash, shift + 5, f);
        }
        return f.apply();
    }

    @Override
    public boolean containsKey(K key, int originalHash, int keyHash, int shift) {
        int mask = keyHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            return this.originalHashes()[index] == originalHash && BoxesRunTime.equals(key, this.content()[2 * index]);
        }
        if ((this.nodeMap() & bitpos) != 0) {
            return ((MapNode)this.getNode(Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos))).containsKey(key, originalHash, keyHash, shift + 5);
        }
        return false;
    }

    @Override
    public <V1> BitmapIndexedMapNode<K, V1> updated(K key, V1 value, int originalHash, int keyHash, int shift, boolean replaceValue) {
        int mask = keyHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            Object key0 = this.content()[2 * index];
            int key0UnimprovedHash = this.originalHashes()[index];
            if (key0UnimprovedHash == originalHash && BoxesRunTime.equals(key0, key)) {
                if (replaceValue) {
                    Object value0 = this.content()[2 * index + 1];
                    if (key0 == key && value0 == value) {
                        return this;
                    }
                    return this.copyAndSetValue(bitpos, key, value);
                }
                return this;
            }
            Object value0 = this.content()[2 * index + 1];
            int key0Hash = Hashing$.MODULE$.improve(key0UnimprovedHash);
            MapNode<Object, Object> subNodeNew = this.mergeTwoKeyValPairs(key0, value0, key0UnimprovedHash, key0Hash, key, value, originalHash, keyHash, shift + 5);
            return this.copyAndMigrateFromInlineToNode(bitpos, key0Hash, subNodeNew);
        }
        if ((this.nodeMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            Node subNode = this.getNode(index);
            MapNode<K, V1> subNodeNew = ((MapNode)subNode).updated(key, value, originalHash, keyHash, shift + 5, replaceValue);
            if (subNodeNew == subNode) {
                return this;
            }
            return this.copyAndSetNode(bitpos, (MapNode<K, V1>)subNode, subNodeNew);
        }
        return this.copyAndInsertValue(bitpos, key, originalHash, keyHash, value);
    }

    /*
     * Unable to fully structure code
     */
    public <V1> int updateWithShallowMutations(K key, V1 value, int originalHash, int keyHash, int shift, int shallowlyMutableNodeMap) {
        block6: {
            mask = keyHash >>> shift & 31;
            bitpos = 1 << mask;
            if ((this.dataMap() & bitpos) != 0) {
                index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
                key0 = this.content()[2 * index];
                key0UnimprovedHash = this.originalHashes()[index];
                if (key0UnimprovedHash == originalHash && BoxesRunTime.equals(key0, key)) {
                    value0 = this.content()[2 * index + 1];
                    if (key0 != key || value0 != value) {
                        dataIx = this.dataIndex(bitpos);
                        idx = 2 * dataIx;
                        this.content()[idx + 1] = value;
                    }
                    return shallowlyMutableNodeMap;
                }
                value0 = this.content()[2 * index + 1];
                key0Hash = Hashing$.MODULE$.improve(key0UnimprovedHash);
                subNodeNew = this.mergeTwoKeyValPairs(key0, value0, key0UnimprovedHash, key0Hash, key, value, originalHash, keyHash, shift + 5);
                this.migrateFromInlineToNodeInPlace(bitpos, key0Hash, subNodeNew);
                return shallowlyMutableNodeMap | bitpos;
            }
            if ((this.nodeMap() & bitpos) == 0) break block6;
            index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            subNode = this.getNode(index);
            subNodeSize = subNode.size();
            subNodeHashCode = subNode.cachedJavaKeySetHashCode();
            returnMutableNodeMap = shallowlyMutableNodeMap;
            if (!(subNode instanceof BitmapIndexedMapNode)) ** GOTO lbl-1000
            var24_23 = (BitmapIndexedMapNode)subNode;
            if ((bitpos & shallowlyMutableNodeMap) != 0) {
                var24_23.updateWithShallowMutations(key, value, originalHash, keyHash, shift + 5, 0);
                v0 = var24_23;
            } else lbl-1000:
            // 2 sources

            {
                if ((result = subNode.updated(key, value, originalHash, keyHash, shift + 5, true)) != subNode) {
                    returnMutableNodeMap = shallowlyMutableNodeMap | bitpos;
                }
                v0 = result;
            }
            subNodeNew = v0;
            this.content()[this.content().length - 1 - this.nodeIndex((int)bitpos)] = subNodeNew;
            this.size_$eq(this.size() - subNodeSize + subNodeNew.size());
            this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() - subNodeHashCode + subNodeNew.cachedJavaKeySetHashCode());
            return returnMutableNodeMap;
        }
        dataIx = this.dataIndex(bitpos);
        idx = 2 * dataIx;
        src = this.content();
        dst = new Object[src.length + 2];
        System.arraycopy(src, 0, dst, 0, idx);
        dst[idx] = key;
        dst[idx + 1] = value;
        System.arraycopy(src, idx, dst, idx + 2, src.length - idx);
        this.dataMap_$eq(this.dataMap() | bitpos);
        this.content_$eq(dst);
        this.originalHashes_$eq(this.insertElement(this.originalHashes(), dataIx, originalHash));
        this.size_$eq(this.size() + 1);
        this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() + keyHash);
        return shallowlyMutableNodeMap;
    }

    @Override
    public <V1> BitmapIndexedMapNode<K, V1> removed(K key, int originalHash, int keyHash, int shift) {
        int mask = keyHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            if (BoxesRunTime.equals(this.content()[2 * index], key)) {
                if (this.payloadArity() == 2 && this.nodeArity() == 0) {
                    int newDataMap;
                    int n;
                    if (shift == 0) {
                        n = this.dataMap() ^ bitpos;
                    } else {
                        int maskFrom_shift = 0;
                        int bitposFrom_mask = keyHash >>> maskFrom_shift & 0x1F;
                        n = newDataMap = 1 << bitposFrom_mask;
                    }
                    if (index == 0) {
                        Object[] objectArray = new Object[2];
                        int getKey_index = 1;
                        objectArray[0] = this.content()[2 * getKey_index];
                        int getValue_index = 1;
                        objectArray[1] = this.content()[2 * getValue_index + 1];
                        int getHash_index = 1;
                        return new BitmapIndexedMapNode<K, V>(newDataMap, 0, objectArray, new int[]{this.originalHashes()[1]}, 1, Hashing$.MODULE$.improve(this.originalHashes()[getHash_index]));
                    }
                    Object[] objectArray = new Object[2];
                    int getKey_index = 0;
                    objectArray[0] = this.content()[2 * getKey_index];
                    int getValue_index = 0;
                    objectArray[1] = this.content()[2 * getValue_index + 1];
                    int getHash_index = 0;
                    return new BitmapIndexedMapNode<K, V>(newDataMap, 0, objectArray, new int[]{this.originalHashes()[0]}, 1, Hashing$.MODULE$.improve(this.originalHashes()[getHash_index]));
                }
                return this.copyAndRemoveValue(bitpos, keyHash);
            }
            return this;
        }
        if ((this.nodeMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            Node subNode = this.getNode(index);
            MapNode subNodeNew = ((MapNode)subNode).removed(key, originalHash, keyHash, shift + 5);
            if (subNodeNew == subNode) {
                return this;
            }
            int subNodeNewSize = subNodeNew.size();
            if (subNodeNewSize == 1) {
                if (this.size() == ((MapNode)subNode).size()) {
                    return (BitmapIndexedMapNode)subNodeNew;
                }
                return this.copyAndMigrateFromNodeToInline(bitpos, (MapNode<K, V1>)subNode, subNodeNew);
            }
            if (subNodeNewSize > 1) {
                return this.copyAndSetNode(bitpos, (MapNode<K, V1>)subNode, subNodeNew);
            }
            return this;
        }
        return this;
    }

    public <V1> MapNode<K, V1> mergeTwoKeyValPairs(K key0, V1 value0, int originalHash0, int keyHash0, K key1, V1 value1, int originalHash1, int keyHash1, int shift) {
        if (shift >= 32) {
            return new HashCollisionMapNode(originalHash0, keyHash0, Vector$.MODULE$.from((IterableOnce)ScalaRunTime$.MODULE$.wrapRefArray(new Tuple2[]{new Tuple2<K, V1>(key0, value0), new Tuple2<K, V1>(key1, value1)})));
        }
        int mask0 = keyHash0 >>> shift & 0x1F;
        int mask1 = keyHash1 >>> shift & 0x1F;
        int newCachedHash = keyHash0 + keyHash1;
        if (mask0 != mask1) {
            int dataMap = 1 << mask0 | 1 << mask1;
            if (mask0 < mask1) {
                return new BitmapIndexedMapNode<K, V>(dataMap, 0, new Object[]{key0, value0, key1, value1}, new int[]{originalHash0, originalHash1}, 2, newCachedHash);
            }
            return new BitmapIndexedMapNode<K, V>(dataMap, 0, new Object[]{key1, value1, key0, value0}, new int[]{originalHash1, originalHash0}, 2, newCachedHash);
        }
        int nodeMap = 1 << mask0;
        MapNode<K, V1> node = this.mergeTwoKeyValPairs(key0, value0, originalHash0, keyHash0, key1, value1, originalHash1, keyHash1, shift + 5);
        return new BitmapIndexedMapNode<K, V>(0, nodeMap, new Object[]{node}, Array$.MODULE$.emptyIntArray(), node.size(), node.cachedJavaKeySetHashCode());
    }

    @Override
    public boolean hasNodes() {
        return this.nodeMap() != 0;
    }

    @Override
    public int nodeArity() {
        return Integer.bitCount(this.nodeMap());
    }

    @Override
    public boolean hasPayload() {
        return this.dataMap() != 0;
    }

    @Override
    public int payloadArity() {
        return Integer.bitCount(this.dataMap());
    }

    public int dataIndex(int bitpos) {
        return Integer.bitCount(this.dataMap() & bitpos - 1);
    }

    public int nodeIndex(int bitpos) {
        return Integer.bitCount(this.nodeMap() & bitpos - 1);
    }

    public <V1> BitmapIndexedMapNode<K, V1> copyAndSetValue(int bitpos, K newKey, V1 newValue) {
        int dataIx = this.dataIndex(bitpos);
        int idx = 2 * dataIx;
        Object[] src = this.content();
        Object[] dst = new Object[src.length];
        System.arraycopy(src, 0, dst, 0, src.length);
        dst[idx + 1] = newValue;
        return new BitmapIndexedMapNode<K, V>(this.dataMap(), this.nodeMap(), dst, this.originalHashes(), this.size(), this.cachedJavaKeySetHashCode());
    }

    public <V1> BitmapIndexedMapNode<K, V1> copyAndSetNode(int bitpos, MapNode<K, V1> oldNode, MapNode<K, V1> newNode) {
        int idx = this.content().length - 1 - this.nodeIndex(bitpos);
        Object[] src = this.content();
        Object[] dst = new Object[src.length];
        System.arraycopy(src, 0, dst, 0, src.length);
        dst[idx] = newNode;
        return new BitmapIndexedMapNode<K, V>(this.dataMap(), this.nodeMap(), dst, this.originalHashes(), this.size() - oldNode.size() + newNode.size(), this.cachedJavaKeySetHashCode() - oldNode.cachedJavaKeySetHashCode() + newNode.cachedJavaKeySetHashCode());
    }

    public <V1> BitmapIndexedMapNode<K, V1> copyAndInsertValue(int bitpos, K key, int originalHash, int keyHash, V1 value) {
        int dataIx = this.dataIndex(bitpos);
        int idx = 2 * dataIx;
        Object[] src = this.content();
        Object[] dst = new Object[src.length + 2];
        System.arraycopy(src, 0, dst, 0, idx);
        dst[idx] = key;
        dst[idx + 1] = value;
        System.arraycopy(src, idx, dst, idx + 2, src.length - idx);
        int[] dstHashes = this.insertElement(this.originalHashes(), dataIx, originalHash);
        return new BitmapIndexedMapNode<K, V>(this.dataMap() | bitpos, this.nodeMap(), dst, dstHashes, this.size() + 1, this.cachedJavaKeySetHashCode() + keyHash);
    }

    public BitmapIndexedMapNode<K, V> copyAndRemoveValue(int bitpos, int keyHash) {
        int dataIx = this.dataIndex(bitpos);
        int idx = 2 * dataIx;
        Object[] src = this.content();
        Object[] dst = new Object[src.length - 2];
        System.arraycopy(src, 0, dst, 0, idx);
        System.arraycopy(src, idx + 2, dst, idx, src.length - idx - 2);
        int[] dstHashes = this.removeElement(this.originalHashes(), dataIx);
        return new BitmapIndexedMapNode<K, V>(this.dataMap() ^ bitpos, this.nodeMap(), dst, dstHashes, this.size() - 1, this.cachedJavaKeySetHashCode() - keyHash);
    }

    public <V1> BitmapIndexedMapNode<K, V> migrateFromInlineToNodeInPlace(int bitpos, int keyHash, MapNode<K, V1> node) {
        int dataIx = this.dataIndex(bitpos);
        int idxOld = 2 * dataIx;
        int idxNew = this.content().length - 2 - this.nodeIndex(bitpos);
        Object[] src = this.content();
        Object[] dst = new Object[src.length - 2 + 1];
        System.arraycopy(src, 0, dst, 0, idxOld);
        System.arraycopy(src, idxOld + 2, dst, idxOld, idxNew - idxOld);
        dst[idxNew] = node;
        System.arraycopy(src, idxNew + 2, dst, idxNew + 1, src.length - idxNew - 2);
        int[] dstHashes = this.removeElement(this.originalHashes(), dataIx);
        this.dataMap_$eq(this.dataMap() ^ bitpos);
        this.nodeMap_$eq(this.nodeMap() | bitpos);
        this.content_$eq(dst);
        this.originalHashes_$eq(dstHashes);
        this.size_$eq(this.size() - 1 + node.size());
        this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() - keyHash + node.cachedJavaKeySetHashCode());
        return this;
    }

    public <V1> BitmapIndexedMapNode<K, V1> copyAndMigrateFromInlineToNode(int bitpos, int keyHash, MapNode<K, V1> node) {
        int dataIx = this.dataIndex(bitpos);
        int idxOld = 2 * dataIx;
        int idxNew = this.content().length - 2 - this.nodeIndex(bitpos);
        Object[] src = this.content();
        Object[] dst = new Object[src.length - 2 + 1];
        System.arraycopy(src, 0, dst, 0, idxOld);
        System.arraycopy(src, idxOld + 2, dst, idxOld, idxNew - idxOld);
        dst[idxNew] = node;
        System.arraycopy(src, idxNew + 2, dst, idxNew + 1, src.length - idxNew - 2);
        int[] dstHashes = this.removeElement(this.originalHashes(), dataIx);
        return new BitmapIndexedMapNode<K, V>(this.dataMap() ^ bitpos, this.nodeMap() | bitpos, dst, dstHashes, this.size() - 1 + node.size(), this.cachedJavaKeySetHashCode() - keyHash + node.cachedJavaKeySetHashCode());
    }

    public <V1> BitmapIndexedMapNode<K, V1> copyAndMigrateFromNodeToInline(int bitpos, MapNode<K, V1> oldNode, MapNode<K, V1> node) {
        int idxOld = this.content().length - 1 - this.nodeIndex(bitpos);
        int dataIxNew = this.dataIndex(bitpos);
        int idxNew = 2 * dataIxNew;
        K key = node.getKey(0);
        V1 value = node.getValue(0);
        Object[] src = this.content();
        Object[] dst = new Object[src.length - 1 + 2];
        System.arraycopy(src, 0, dst, 0, idxNew);
        dst[idxNew] = key;
        dst[idxNew + 1] = value;
        System.arraycopy(src, idxNew, dst, idxNew + 2, idxOld - idxNew);
        System.arraycopy(src, idxOld + 1, dst, idxOld + 2, src.length - idxOld - 1);
        int hash = node.getHash(0);
        int[] dstHashes = this.insertElement(this.originalHashes(), dataIxNew, hash);
        return new BitmapIndexedMapNode<K, V>(this.dataMap() | bitpos, this.nodeMap() ^ bitpos, dst, dstHashes, this.size() - oldNode.size() + 1, this.cachedJavaKeySetHashCode() - oldNode.cachedJavaKeySetHashCode() + node.cachedJavaKeySetHashCode());
    }

    @Override
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        int iN = this.payloadArity();
        for (int i = 0; i < iN; ++i) {
            f.apply((Tuple2<K, V>)this.getPayload(i));
        }
        int jN = this.nodeArity();
        for (int j = 0; j < jN; ++j) {
            ((MapNode)this.getNode(j)).foreach(f);
        }
    }

    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        int iN = this.payloadArity();
        for (int i = 0; i < iN; ++i) {
            f.apply(this.content()[2 * i], this.content()[2 * i + 1]);
        }
        int jN = this.nodeArity();
        for (int j = 0; j < jN; ++j) {
            ((MapNode)this.getNode(j)).foreachEntry(f);
        }
    }

    @Override
    public void foreachWithHash(Function3<K, V, Object, BoxedUnit> f) {
        int iN = this.payloadArity();
        for (int i = 0; i < iN; ++i) {
            f.apply(this.content()[2 * i], this.content()[2 * i + 1], this.originalHashes()[i]);
        }
        int jN = this.nodeArity();
        for (int j = 0; j < jN; ++j) {
            ((MapNode)this.getNode(j)).foreachWithHash(f);
        }
    }

    @Override
    public <V1> void buildTo(HashMapBuilder<K, V1> builder) {
        int iN = this.payloadArity();
        int jN = this.nodeArity();
        for (int i = 0; i < iN; ++i) {
            builder.addOne(this.content()[2 * i], this.content()[2 * i + 1], this.originalHashes()[i]);
        }
        for (int j = 0; j < jN; ++j) {
            ((MapNode)this.getNode(j)).buildTo(builder);
        }
    }

    @Override
    public <W> BitmapIndexedMapNode<K, W> transform(Function2<K, V, W> f) {
        Object[] newContent = null;
        int iN = this.payloadArity();
        int jN = this.nodeArity();
        int newContentLength = this.content().length;
        for (int i = 0; i < iN; ++i) {
            Object key = this.content()[2 * i];
            Object value = this.content()[2 * i + 1];
            W newValue = f.apply(key, value);
            if (newContent == null) {
                if (newValue == value) continue;
                newContent = (Object[])this.content().clone();
                newContent[2 * i + 1] = newValue;
                continue;
            }
            newContent[2 * i + 1] = newValue;
        }
        for (int j = 0; j < jN; ++j) {
            Node node = this.getNode(j);
            MapNode<K, W> newNode = ((MapNode)node).transform(f);
            if (newContent == null) {
                if (newNode == node) continue;
                newContent = (Object[])this.content().clone();
                newContent[newContentLength - j - 1] = newNode;
                continue;
            }
            newContent[newContentLength - j - 1] = newNode;
        }
        if (newContent == null) {
            return this;
        }
        return new BitmapIndexedMapNode<K, V>(this.dataMap(), this.nodeMap(), newContent, this.originalHashes(), this.size(), this.cachedJavaKeySetHashCode());
    }

    @Override
    public <V1> void mergeInto(MapNode<K, V1> that, HashMapBuilder<K, V1> builder, int shift, Function2<Tuple2<K, V>, Tuple2<K, V1>, Tuple2<K, V1>> mergef) {
        if (that instanceof BitmapIndexedMapNode) {
            BitmapIndexedMapNode bitmapIndexedMapNode = (BitmapIndexedMapNode)that;
            if (this.size() == 0) {
                that.buildTo(builder);
                return;
            }
            if (bitmapIndexedMapNode.size() == 0) {
                this.buildTo(builder);
                return;
            }
            int allMap = this.dataMap() | bitmapIndexedMapNode.dataMap() | this.nodeMap() | bitmapIndexedMapNode.nodeMap();
            int minIndex = Integer.numberOfTrailingZeros(allMap);
            int maxIndex = 32 - Integer.numberOfLeadingZeros(allMap);
            int leftIdx = 0;
            int rightIdx = 0;
            for (int index = minIndex; index < maxIndex; ++index) {
                int bitpos = 1 << index;
                if ((bitpos & this.dataMap()) != 0) {
                    Object leftKey = this.content()[2 * leftIdx];
                    Object leftValue = this.content()[2 * leftIdx + 1];
                    int leftOriginalHash = this.originalHashes()[leftIdx];
                    if ((bitpos & bitmapIndexedMapNode.dataMap()) != 0) {
                        Object rightKey = bitmapIndexedMapNode.content()[2 * rightIdx];
                        Object rightValue = bitmapIndexedMapNode.content()[2 * rightIdx + 1];
                        int rightOriginalHash = bitmapIndexedMapNode.originalHashes()[rightIdx];
                        if (leftOriginalHash == rightOriginalHash && BoxesRunTime.equals(leftKey, rightKey)) {
                            builder.addOne(mergef.apply(new Tuple2<Object, Object>(leftKey, leftValue), new Tuple2<Object, Object>(rightKey, rightValue)));
                        } else {
                            builder.addOne(leftKey, leftValue, leftOriginalHash);
                            builder.addOne(rightKey, rightValue, rightOriginalHash);
                        }
                        ++rightIdx;
                    } else if ((bitpos & bitmapIndexedMapNode.nodeMap()) != 0) {
                        int leftImprovedHash;
                        Node subNode = bitmapIndexedMapNode.getNode(bitmapIndexedMapNode.nodeIndex(bitpos));
                        MapNode<Object, V1> removed = ((MapNode)subNode).removed(leftKey, leftOriginalHash, leftImprovedHash = Hashing$.MODULE$.improve(leftOriginalHash), shift + 5);
                        if (removed == subNode) {
                            ((MapNode)subNode).buildTo(builder);
                            builder.addOne(leftKey, leftValue, leftOriginalHash, leftImprovedHash);
                        } else {
                            removed.buildTo(builder);
                            builder.addOne(mergef.apply(new Tuple2<Object, Object>(leftKey, leftValue), ((MapNode)subNode).getTuple(leftKey, leftOriginalHash, leftImprovedHash, shift + 5)));
                        }
                    } else {
                        builder.addOne(leftKey, leftValue, leftOriginalHash);
                    }
                    ++leftIdx;
                    continue;
                }
                if ((bitpos & this.nodeMap()) != 0) {
                    if ((bitpos & bitmapIndexedMapNode.dataMap()) != 0) {
                        Object rightKey = bitmapIndexedMapNode.content()[2 * rightIdx];
                        Object rightValue = bitmapIndexedMapNode.content()[2 * rightIdx + 1];
                        int rightOriginalHash = bitmapIndexedMapNode.originalHashes()[rightIdx];
                        int rightImprovedHash = Hashing$.MODULE$.improve(rightOriginalHash);
                        Node subNode = this.getNode(this.nodeIndex(bitpos));
                        MapNode<Object, V1> removed = ((MapNode)subNode).removed(rightKey, rightOriginalHash, rightImprovedHash, shift + 5);
                        if (removed == subNode) {
                            ((MapNode)subNode).buildTo(builder);
                            builder.addOne(rightKey, rightValue, rightOriginalHash, rightImprovedHash);
                        } else {
                            removed.buildTo(builder);
                            builder.addOne(mergef.apply(((MapNode)subNode).getTuple(rightKey, rightOriginalHash, rightImprovedHash, shift + 5), new Tuple2<Object, Object>(rightKey, rightValue)));
                        }
                        ++rightIdx;
                        continue;
                    }
                    if ((bitpos & bitmapIndexedMapNode.nodeMap()) != 0) {
                        ((MapNode)this.getNode(this.nodeIndex(bitpos))).mergeInto(bitmapIndexedMapNode.getNode(bitmapIndexedMapNode.nodeIndex(bitpos)), builder, shift + 5, mergef);
                        continue;
                    }
                    ((MapNode)this.getNode(this.nodeIndex(bitpos))).buildTo(builder);
                    continue;
                }
                if ((bitpos & bitmapIndexedMapNode.dataMap()) != 0) {
                    int dataIndex = bitmapIndexedMapNode.dataIndex(bitpos);
                    builder.addOne(bitmapIndexedMapNode.content()[2 * dataIndex], bitmapIndexedMapNode.content()[2 * dataIndex + 1], bitmapIndexedMapNode.originalHashes()[dataIndex]);
                    ++rightIdx;
                    continue;
                }
                if ((bitpos & bitmapIndexedMapNode.nodeMap()) == 0) continue;
                ((MapNode)bitmapIndexedMapNode.getNode(bitmapIndexedMapNode.nodeIndex(bitpos))).buildTo(builder);
            }
            return;
        }
        if (that instanceof HashCollisionMapNode) {
            throw new Exception("Cannot merge BitmapIndexedMapNode with HashCollisionMapNode");
        }
        throw new MatchError(that);
    }

    /*
     * WARNING - void declaration
     */
    public boolean equals(Object that) {
        block6: {
            block8: {
                block7: {
                    boolean bl;
                    void deepContentEquality_a2;
                    if (!(that instanceof BitmapIndexedMapNode)) break block6;
                    BitmapIndexedMapNode bitmapIndexedMapNode = (BitmapIndexedMapNode)that;
                    if (this == bitmapIndexedMapNode) break block7;
                    if (this.cachedJavaKeySetHashCode() != bitmapIndexedMapNode.cachedJavaKeySetHashCode() || this.nodeMap() != bitmapIndexedMapNode.nodeMap() || this.dataMap() != bitmapIndexedMapNode.dataMap() || this.size() != bitmapIndexedMapNode.size() || !Arrays.equals(this.originalHashes(), bitmapIndexedMapNode.originalHashes())) break block8;
                    int n = this.content().length;
                    Object[] objectArray = bitmapIndexedMapNode.content();
                    Object[] deepContentEquality_a1 = this.content();
                    if (deepContentEquality_a1 == deepContentEquality_a2) {
                        bl = true;
                    } else {
                        void deepContentEquality_length;
                        boolean deepContentEquality_isEqual = true;
                        for (int deepContentEquality_i = 0; deepContentEquality_isEqual && deepContentEquality_i < deepContentEquality_length; ++deepContentEquality_i) {
                            deepContentEquality_isEqual = BoxesRunTime.equals(deepContentEquality_a1[deepContentEquality_i], deepContentEquality_a2[deepContentEquality_i]);
                        }
                        bl = deepContentEquality_isEqual;
                    }
                    Object var3_5 = null;
                    objectArray = null;
                    if (!bl) break block8;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean deepContentEquality(Object[] a1, Object[] a2, int length) {
        if (a1 == a2) {
            return true;
        }
        boolean isEqual = true;
        for (int i = 0; isEqual && i < length; ++i) {
            isEqual = BoxesRunTime.equals(a1[i], a2[i]);
        }
        return isEqual;
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Trie nodes do not support hashing.");
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <V1> BitmapIndexedMapNode<K, V1> concat(MapNode<K, V1> that, int shift) {
        if (that instanceof BitmapIndexedMapNode) {
            BitmapIndexedMapNode bitmapIndexedMapNode = (BitmapIndexedMapNode)that;
            if (this.size() == 0) {
                return bitmapIndexedMapNode;
            }
            if (bitmapIndexedMapNode.size() == 0 || bitmapIndexedMapNode == this) {
                return this;
            }
            if (bitmapIndexedMapNode.size() == 1) {
                int originalHash = bitmapIndexedMapNode.getHash(0);
                return this.updated((Object)bitmapIndexedMapNode.getKey(0), bitmapIndexedMapNode.getValue(0), originalHash, Hashing$.MODULE$.improve(originalHash), shift, true);
            }
            boolean anyChangesMadeSoFar = false;
            int allMap = this.dataMap() | bitmapIndexedMapNode.dataMap() | this.nodeMap() | bitmapIndexedMapNode.nodeMap();
            int minimumBitPos = Node$.MODULE$.bitposFrom(Integer.numberOfTrailingZeros(allMap));
            int maximumBitPos = Node$.MODULE$.bitposFrom(32 - Integer.numberOfLeadingZeros(allMap) - 1);
            int leftNodeRightNode = 0;
            int leftDataRightNode = 0;
            int leftNodeRightData = 0;
            int leftDataOnly = 0;
            int rightDataOnly = 0;
            int leftNodeOnly = 0;
            int rightNodeOnly = 0;
            int leftDataRightDataMigrateToNode = 0;
            int leftDataRightDataRightOverwrites = 0;
            int dataToNodeMigrationTargets = 0;
            int bitpos = minimumBitPos;
            int leftIdx = 0;
            int rightIdx = 0;
            boolean finished = false;
            while (!finished) {
                if ((bitpos & this.dataMap()) != 0) {
                    if ((bitpos & bitmapIndexedMapNode.dataMap()) != 0) {
                        int leftOriginalHash = this.getHash(leftIdx);
                        if (leftOriginalHash == bitmapIndexedMapNode.getHash(rightIdx) && BoxesRunTime.equals(this.getKey(leftIdx), bitmapIndexedMapNode.getKey(rightIdx))) {
                            leftDataRightDataRightOverwrites |= bitpos;
                        } else {
                            leftDataRightDataMigrateToNode |= bitpos;
                            dataToNodeMigrationTargets |= Node$.MODULE$.bitposFrom(Node$.MODULE$.maskFrom(Hashing$.MODULE$.improve(leftOriginalHash), shift));
                        }
                        ++rightIdx;
                    } else if ((bitpos & bitmapIndexedMapNode.nodeMap()) != 0) {
                        leftDataRightNode |= bitpos;
                    } else {
                        leftDataOnly |= bitpos;
                    }
                    ++leftIdx;
                } else if ((bitpos & this.nodeMap()) != 0) {
                    if ((bitpos & bitmapIndexedMapNode.dataMap()) != 0) {
                        leftNodeRightData |= bitpos;
                        ++rightIdx;
                    } else if ((bitpos & bitmapIndexedMapNode.nodeMap()) != 0) {
                        leftNodeRightNode |= bitpos;
                    } else {
                        leftNodeOnly |= bitpos;
                    }
                } else if ((bitpos & bitmapIndexedMapNode.dataMap()) != 0) {
                    rightDataOnly |= bitpos;
                    ++rightIdx;
                } else if ((bitpos & bitmapIndexedMapNode.nodeMap()) != 0) {
                    rightNodeOnly |= bitpos;
                }
                if (bitpos == maximumBitPos) {
                    finished = true;
                    continue;
                }
                bitpos <<= 1;
            }
            int newDataMap = leftDataOnly | rightDataOnly | leftDataRightDataRightOverwrites;
            int newNodeMap = leftNodeRightNode | leftDataRightNode | leftNodeRightData | leftNodeOnly | rightNodeOnly | dataToNodeMigrationTargets;
            if (newDataMap == (rightDataOnly | leftDataRightDataRightOverwrites) && newNodeMap == rightNodeOnly) {
                return bitmapIndexedMapNode;
            }
            int newDataSize = Integer.bitCount(newDataMap);
            int newContentSize = 2 * newDataSize + Integer.bitCount(newNodeMap);
            Object[] newContent = new Object[newContentSize];
            int[] newOriginalHashes = new int[newDataSize];
            int newSize = 0;
            int newCachedHashCode = 0;
            int leftDataIdx = 0;
            int rightDataIdx = 0;
            int leftNodeIdx = 0;
            int rightNodeIdx = 0;
            int nextShift = shift + 5;
            int compressedDataIdx = 0;
            int compressedNodeIdx = 0;
            int bitpos2 = minimumBitPos;
            boolean finished2 = false;
            while (!finished2) {
                if ((bitpos2 & leftNodeRightNode) != 0) {
                    MapNode newNode;
                    Node rightNode = bitmapIndexedMapNode.getNode(rightNodeIdx);
                    if (rightNode != (newNode = ((MapNode)this.getNode(leftNodeIdx)).concat(rightNode, nextShift))) {
                        anyChangesMadeSoFar = true;
                    }
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode;
                    ++compressedNodeIdx;
                    ++rightNodeIdx;
                    ++leftNodeIdx;
                    newSize += newNode.size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftDataRightNode) != 0) {
                    void var48_48;
                    int leftImproved;
                    int leftOriginalHash;
                    V leftValue;
                    K leftKey;
                    Node n = bitmapIndexedMapNode.getNode(rightNodeIdx);
                    MapNode<K, V> updated = ((MapNode)n).updated(leftKey = this.getKey(leftDataIdx), leftValue = this.getValue(leftDataIdx), leftOriginalHash = this.getHash(leftDataIdx), leftImproved = Hashing$.MODULE$.improve(leftOriginalHash), nextShift, false);
                    if (updated != n) {
                        anyChangesMadeSoFar = true;
                    }
                    newContent[newContentSize - compressedNodeIdx - 1] = var48_48;
                    ++compressedNodeIdx;
                    ++rightNodeIdx;
                    ++leftDataIdx;
                    newSize += var48_48.size();
                    newCachedHashCode += var48_48.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftNodeRightData) != 0) {
                    MapNode<K, V> newNode;
                    anyChangesMadeSoFar = true;
                    int rightOriginalHash = bitmapIndexedMapNode.getHash(rightDataIdx);
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode = ((MapNode)this.getNode(leftNodeIdx)).updated(bitmapIndexedMapNode.getKey(rightDataIdx), bitmapIndexedMapNode.getValue(rightDataIdx), bitmapIndexedMapNode.getHash(rightDataIdx), Hashing$.MODULE$.improve(rightOriginalHash), nextShift, true);
                    ++compressedNodeIdx;
                    ++leftNodeIdx;
                    ++rightDataIdx;
                    newSize += newNode.size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftDataOnly) != 0) {
                    anyChangesMadeSoFar = true;
                    int originalHash = this.originalHashes()[leftDataIdx];
                    newContent[2 * compressedDataIdx] = this.getKey(leftDataIdx);
                    newContent[2 * compressedDataIdx + 1] = this.getValue(leftDataIdx);
                    newOriginalHashes[compressedDataIdx] = originalHash;
                    ++compressedDataIdx;
                    ++leftDataIdx;
                    ++newSize;
                    newCachedHashCode += Hashing$.MODULE$.improve(originalHash);
                } else if ((bitpos2 & rightDataOnly) != 0) {
                    int originalHash = bitmapIndexedMapNode.originalHashes()[rightDataIdx];
                    newContent[2 * compressedDataIdx] = bitmapIndexedMapNode.getKey(rightDataIdx);
                    newContent[2 * compressedDataIdx + 1] = bitmapIndexedMapNode.getValue(rightDataIdx);
                    newOriginalHashes[compressedDataIdx] = originalHash;
                    ++compressedDataIdx;
                    ++rightDataIdx;
                    ++newSize;
                    newCachedHashCode += Hashing$.MODULE$.improve(originalHash);
                } else if ((bitpos2 & leftNodeOnly) != 0) {
                    anyChangesMadeSoFar = true;
                    Node newNode = this.getNode(leftNodeIdx);
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode;
                    ++compressedNodeIdx;
                    ++leftNodeIdx;
                    newSize += ((MapNode)newNode).size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & rightNodeOnly) != 0) {
                    Node newNode = bitmapIndexedMapNode.getNode(rightNodeIdx);
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode;
                    ++compressedNodeIdx;
                    ++rightNodeIdx;
                    newSize += ((MapNode)newNode).size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftDataRightDataMigrateToNode) != 0) {
                    MapNode<K, V> newNode;
                    anyChangesMadeSoFar = true;
                    int leftOriginalHash = this.getHash(leftDataIdx);
                    int rightOriginalHash = bitmapIndexedMapNode.getHash(rightDataIdx);
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode = bitmapIndexedMapNode.mergeTwoKeyValPairs(this.getKey(leftDataIdx), this.getValue(leftDataIdx), leftOriginalHash, Hashing$.MODULE$.improve(leftOriginalHash), bitmapIndexedMapNode.getKey(rightDataIdx), bitmapIndexedMapNode.getValue(rightDataIdx), rightOriginalHash, Hashing$.MODULE$.improve(rightOriginalHash), nextShift);
                    ++compressedNodeIdx;
                    ++leftDataIdx;
                    ++rightDataIdx;
                    newSize += newNode.size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftDataRightDataRightOverwrites) != 0) {
                    int originalHash = bitmapIndexedMapNode.originalHashes()[rightDataIdx];
                    newContent[2 * compressedDataIdx] = bitmapIndexedMapNode.getKey(rightDataIdx);
                    newContent[2 * compressedDataIdx + 1] = bitmapIndexedMapNode.getValue(rightDataIdx);
                    newOriginalHashes[compressedDataIdx] = originalHash;
                    ++compressedDataIdx;
                    ++rightDataIdx;
                    ++newSize;
                    newCachedHashCode += Hashing$.MODULE$.improve(originalHash);
                    ++leftDataIdx;
                }
                if (bitpos2 == maximumBitPos) {
                    finished2 = true;
                    continue;
                }
                bitpos2 <<= 1;
            }
            if (anyChangesMadeSoFar) {
                return new BitmapIndexedMapNode<K, V>(newDataMap, newNodeMap, newContent, newOriginalHashes, newSize, newCachedHashCode);
            }
            return bitmapIndexedMapNode;
        }
        throw new UnsupportedOperationException("Cannot concatenate a HashCollisionMapNode with a BitmapIndexedMapNode");
    }

    @Override
    public BitmapIndexedMapNode<K, V> copy() {
        Object[] contentClone = (Object[])this.content().clone();
        int contentLength = contentClone.length;
        for (int i = Integer.bitCount(this.dataMap()) * 2; i < contentLength; ++i) {
            contentClone[i] = ((MapNode)contentClone[i]).copy();
        }
        return new BitmapIndexedMapNode<K, V>(this.dataMap(), this.nodeMap(), contentClone, (int[])this.originalHashes().clone(), this.size(), this.cachedJavaKeySetHashCode());
    }

    @Override
    public BitmapIndexedMapNode<K, V> filterImpl(Function1<Tuple2<K, V>, Object> pred, boolean flipped) {
        if (this.size() == 0) {
            return this;
        }
        if (this.size() == 1) {
            if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)this.getPayload(0))) != flipped) {
                return this;
            }
            return MapNode$.MODULE$.empty();
        }
        if (this.nodeMap() == 0) {
            int minimumIndex = Integer.numberOfTrailingZeros(this.dataMap());
            int maximumIndex = 32 - Integer.numberOfLeadingZeros(this.dataMap());
            int newDataMap = 0;
            int newCachedHashCode = 0;
            int dataIndex = 0;
            for (int i = minimumIndex; i < maximumIndex; ++i) {
                int bitpos = 1 << i;
                if ((bitpos & this.dataMap()) == 0) continue;
                Object payload = this.getPayload(dataIndex);
                if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)payload)) != flipped) {
                    newDataMap |= bitpos;
                    newCachedHashCode += Hashing$.MODULE$.improve(this.originalHashes()[dataIndex]);
                }
                ++dataIndex;
            }
            if (newDataMap == 0) {
                return MapNode$.MODULE$.empty();
            }
            if (newDataMap == this.dataMap()) {
                return this;
            }
            int newSize = Integer.bitCount(newDataMap);
            Object[] newContent = new Object[newSize * 2];
            int[] newOriginalHashCodes = new int[newSize];
            int newMaximumIndex = 32 - Integer.numberOfLeadingZeros(newDataMap);
            int newDataIndex = 0;
            for (int j = Integer.numberOfTrailingZeros(newDataMap); j < newMaximumIndex; ++j) {
                int bitpos = 1 << j;
                if ((bitpos & newDataMap) == 0) continue;
                int oldIndex = Integer.bitCount(this.dataMap() & bitpos - 1);
                newContent[newDataIndex * 2] = this.content()[oldIndex * 2];
                newContent[newDataIndex * 2 + 1] = this.content()[oldIndex * 2 + 1];
                newOriginalHashCodes[newDataIndex] = this.originalHashes()[oldIndex];
                ++newDataIndex;
            }
            return new BitmapIndexedMapNode<K, V>(newDataMap, 0, newContent, newOriginalHashCodes, newSize, newCachedHashCode);
        }
        int allMap = this.dataMap() | this.nodeMap();
        int minimumIndex = Integer.numberOfTrailingZeros(allMap);
        int maximumIndex = 32 - Integer.numberOfLeadingZeros(allMap);
        int oldDataPassThrough = 0;
        int nodeMigrateToDataTargetMap = 0;
        AbstractBuffer nodesToMigrateToData = null;
        int nodesToPassThroughMap = 0;
        int mapOfNewNodes = 0;
        Object newNodes = null;
        int newDataMap = 0;
        int newNodeMap = 0;
        int newSize = 0;
        int newCachedHashCode = 0;
        int dataIndex = 0;
        int nodeIndex = 0;
        for (int i = minimumIndex; i < maximumIndex; ++i) {
            int bitpos = 1 << i;
            if ((bitpos & this.dataMap()) != 0) {
                Object payload = this.getPayload(dataIndex);
                if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)payload)) != flipped) {
                    newDataMap |= bitpos;
                    oldDataPassThrough |= bitpos;
                    ++newSize;
                    newCachedHashCode += Hashing$.MODULE$.improve(this.originalHashes()[dataIndex]);
                }
                ++dataIndex;
                continue;
            }
            if ((bitpos & this.nodeMap()) == 0) continue;
            Node oldSubNode = this.getNode(nodeIndex);
            MapNode<K, V> newSubNode = ((MapNode)oldSubNode).filterImpl(pred, flipped);
            newSize += newSubNode.size();
            newCachedHashCode += newSubNode.cachedJavaKeySetHashCode();
            if (newSubNode.size() > 1) {
                newNodeMap |= bitpos;
                if (oldSubNode == newSubNode) {
                    nodesToPassThroughMap |= bitpos;
                } else {
                    mapOfNewNodes |= bitpos;
                    if (newNodes == null) {
                        newNodes = Queue$.MODULE$.empty();
                    }
                    ((AbstractBuffer)newNodes).$plus$eq(newSubNode);
                }
            } else if (newSubNode.size() == 1) {
                newDataMap |= bitpos;
                nodeMigrateToDataTargetMap |= bitpos;
                if (nodesToMigrateToData == null) {
                    nodesToMigrateToData = (Queue)IterableFactory.apply$(Queue$.MODULE$, Nil$.MODULE$);
                }
                nodesToMigrateToData.$plus$eq(newSubNode);
            }
            ++nodeIndex;
        }
        if (newSize == 0) {
            return MapNode$.MODULE$.empty();
        }
        if (newSize == this.size()) {
            return this;
        }
        int newDataSize = Integer.bitCount(newDataMap);
        int newContentSize = 2 * newDataSize + Integer.bitCount(newNodeMap);
        Object[] newContent = new Object[newContentSize];
        int[] newOriginalHashes = new int[newDataSize];
        int newAllMap = newDataMap | newNodeMap;
        int maxIndex = 32 - Integer.numberOfLeadingZeros(newAllMap);
        int oldDataIndex = 0;
        int oldNodeIndex = 0;
        int newDataIndex = 0;
        int newNodeIndex = 0;
        for (int i = minimumIndex; i < maxIndex; ++i) {
            int bitpos = 1 << i;
            if ((bitpos & oldDataPassThrough) != 0) {
                newContent[newDataIndex * 2] = this.content()[2 * oldDataIndex];
                newContent[newDataIndex * 2 + 1] = this.content()[2 * oldDataIndex + 1];
                newOriginalHashes[newDataIndex] = this.originalHashes()[oldDataIndex];
                ++newDataIndex;
                ++oldDataIndex;
                continue;
            }
            if ((bitpos & nodesToPassThroughMap) != 0) {
                newContent[newContentSize - newNodeIndex - 1] = this.getNode(oldNodeIndex);
                ++newNodeIndex;
                ++oldNodeIndex;
                continue;
            }
            if ((bitpos & nodeMigrateToDataTargetMap) != 0) {
                MapNode node = (MapNode)((Queue)nodesToMigrateToData).dequeue();
                newContent[2 * newDataIndex] = node.getKey(0);
                newContent[2 * newDataIndex + 1] = node.getValue(0);
                newOriginalHashes[newDataIndex] = node.getHash(0);
                ++newDataIndex;
                ++oldNodeIndex;
                continue;
            }
            if ((bitpos & mapOfNewNodes) != 0) {
                newContent[newContentSize - newNodeIndex - 1] = ((Queue)newNodes).dequeue();
                ++newNodeIndex;
                ++oldNodeIndex;
                continue;
            }
            if ((bitpos & this.dataMap()) != 0) {
                ++oldDataIndex;
                continue;
            }
            if ((bitpos & this.nodeMap()) == 0) continue;
            ++oldNodeIndex;
        }
        return new BitmapIndexedMapNode<K, V>(newDataMap, newNodeMap, newContent, newOriginalHashes, newSize, newCachedHashCode);
    }

    public BitmapIndexedMapNode(int dataMap, int nodeMap, Object[] content, int[] originalHashes, int size, int cachedJavaKeySetHashCode) {
        this.dataMap = dataMap;
        this.nodeMap = nodeMap;
        this.content = content;
        this.originalHashes = originalHashes;
        this.size = size;
        this.cachedJavaKeySetHashCode = cachedJavaKeySetHashCode;
        Statics.releaseFence();
    }
}

