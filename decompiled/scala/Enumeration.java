/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import scala.$less$colon$less$;
import scala.Enumeration$ValueOrdering$;
import scala.Enumeration$ValueSet$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.ArrayOps$;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedOps;
import scala.collection.SortedSetFactoryDefaults;
import scala.collection.SortedSetOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StringOps$;
import scala.collection.View;
import scala.collection.immutable.AbstractSet;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Seq;
import scala.collection.immutable.SortedSet;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.BitSet;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.Map;
import scala.math.Ordered;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\rUg!B/_\u0003\u0003\t\u0007\u0002C7\u0001\u0005\u0003\u0005\u000b\u0011\u00028\t\u000bE\u0004A\u0011\u0001:\t\u000bE\u0004A\u0011A;\t\u000bY\u0004A\u0011C<\t\u000ba\u0004A\u0011I=\t\u0013\u0005-\u0001A1A\u0005\n\u00055\u0001\u0002\u0003Bk\u0001\u0001\u0006I!a\u0004\t\u0013\t]\u0007\u00011A\u0005\n\u0005E\u0006\"\u0003Bm\u0001\u0001\u0007I\u0011\u0002Bn\u0011!\u00119\u000f\u0001Q!\n\u0005e\u0003\"\u0003Bv\u0001\u0001\u0007I\u0011BA\\\u0011%\u0011i\u000f\u0001a\u0001\n\u0013\u0011y\u000f\u0003\u0005\u0003t\u0002\u0001\u000b\u0015BA!\u0011!\u0011y\u0010\u0001Q\u0001\n\r\u0005\u0001bBB\u0002\u0001\u0011\u0005\u0011\u0011\u0017\u0005\n\u0007\u000b\u0001\u0001\u0019!C\t\u0003cA\u0011ba\u0002\u0001\u0001\u0004%\tb!\u0003\t\u000f\r5\u0001\u0001)Q\u0005]\"Y1q\u0002\u0001A\u0002\u0003\u0007I\u0011CB\t\u0011-\u0019)\u0002\u0001a\u0001\u0002\u0004%\tba\u0006\t\u0017\rm\u0001\u00011A\u0001B\u0003&11\u0003\u0005\b\u0007;\u0001A\u0011BAr\u0011\u001d\u0019y\u0002\u0001Q!\n9Dqa!\t\u0001A\u0003&a\u000eC\u0004\u0004$\u0001!)!!\r\t\u000f\r\u0015\u0002\u0001\"\u0002\u0004(!91Q\u0006\u0001\u0005\u0006\r=\u0002bBB\u001b\u0001\u0011U1q\u0007\u0005\b\u0007k\u0001AQCB\u001d\u0011\u001d\u0019)\u0004\u0001C\u000b\u0007\u007fAqa!\u000e\u0001\t+\u0019)\u0005C\u0004\u0004L\u0001!Ia!\u0014\t\u000f\r=\u0003\u0001\"\u0003\u0004R\u00199\u00111\u0005\u0001\u0002\u0002\u0005\u0015\u0002BB9#\t\u0003\ti\u0003C\u0004\u00020\t2\t!!\r\t\u0015\u0005M\"E!A\u0001B\u0003%1\u000fC\u0004\u00026\t\"\t%a\u000e\t\u000f\u0005u\"\u0005\"\u0011\u0002@!9\u0011\u0011\u000b\u0012\u0005B\u0005M\u0003bBA+E\u0011\u0005\u0011q\u000b\u0005\r\u0005\u001b\u0014#\u0011!b\u0001\n\u0003\u0001!q\u001a\u0004\u0007\u0007+\u0002\u0001ba\u0016\t\u0013\ru2F!A!\u0002\u0013q\u0007\"CB\"W\t\u0005\t\u0015!\u0003{\u0011\u0019\t8\u0006\"\u0001\u0004Z!1\u0011o\u000bC\u0001\u0007CBa!]\u0016\u0005\u0002\r\u0015\u0004BB9,\t\u0003\u0019I\u0007C\u0004\u00020-\"\t!!\r\t\u000ba\\C\u0011I=\t\u000bY\\C\u0011C<\b\u000f\r=\u0004\u0001c\u0001\u0004r\u0019911\u000f\u0001\t\u0002\rU\u0004BB97\t\u0003\u00199\tC\u0004\u00026Y\"\ta!#\u0007\r\u0005m\u0003\u0001AA/\u0011)\t9)\u000fB\u0001B\u0003&\u0011\u0011\u0012\u0005\bcf\"\t!OAH\u0011\u001d\t\u0019*\u000fC\u0002\u0003+Cq!!(:\t\u0003\ty\nC\u0004\u00020f\"\t%!-\t\u000f\u0005M\u0016\b\"\u0011\u00022!9\u0011QW\u001d\u0005B\u0005]\u0006bBA]s\u0011\u0005\u00111\u0018\u0005\b\u0003\u0003LD\u0011AAb\u0011\u001d\tI-\u000fC\u0001\u0003\u0017Dq!a4:\t\u0003\t\t\u000eC\u0004\u0002Zf\"\t%a7\t\u000f\u0005\u0005\u0018\b\"\u0011\u0002d\"9\u0011Q]\u001d\u0005\u0002\u0005\u001d\bbBA{s\u0011E\u0013q\u001f\u0005\b\u0005\u0007ID\u0011\u000bB\u0003\u0011\u001d\u0011i!\u000fC\u0001\u0005\u001fAqAa\u0007:\t\u0003\u0011i\u0002C\u0004\u0003\u000ee\"\tEa\t\t\u000f\tm\u0011\b\"\u0011\u0003Z!9!\u0011O\u001d\u0005B\tM\u0004b\u0002BJs\u0011\u0005#Q\u0013\u0005\r\u0005cK$\u0011#b\u0001\n\u0003\u0001!1W\u0004\b\u0007#\u0003\u0001\u0012ABJ\r\u001d\tY\u0006\u0001E\u0001\u0007+Ca!\u001d*\u0005\u0002\r\u001d\u0006\"CBU%\n\u0007IQBBV\u0011!\u0019yK\u0015Q\u0001\u000e\r5\u0006\"CBY%\n\u0007IQBBZ\u0011!\u00199L\u0015Q\u0001\u000e\rU\u0006\"CAX%\n\u0007I\u0011AAY\u0011!\u0019IL\u0015Q\u0001\n\u0005e\u0003bBB^%\u0012\u00051Q\u0018\u0005\b\u0007\u0007\u0014F\u0011\u0001B\u0003\u0011\u001d\t)P\u0015C\u0001\u0007\u000b\u00141\"\u00128v[\u0016\u0014\u0018\r^5p]*\tq,A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001\u0011g\r\u0005\u0002dI6\ta,\u0003\u0002f=\n1\u0011I\\=SK\u001a\u0004\"a\u001a6\u000f\u0005\rD\u0017BA5_\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u001b7\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005%t\u0016aB5oSRL\u0017\r\u001c\t\u0003G>L!\u0001\u001d0\u0003\u0007%sG/\u0001\u0004=S:LGO\u0010\u000b\u0003gR\u0004\"a\u0019\u0001\t\u000b5\u0014\u0001\u0019\u00018\u0015\u0003M\f1B]3bIJ+7o\u001c7wKR\t!-\u0001\u0005u_N#(/\u001b8h)\u0005Q\bcA>\u0002\u00069\u0019A0!\u0001\u0011\u0005utV\"\u0001@\u000b\u0005}\u0004\u0017A\u0002\u001fs_>$h(C\u0002\u0002\u0004y\u000ba\u0001\u0015:fI\u00164\u0017\u0002BA\u0004\u0003\u0013\u0011aa\u0015;sS:<'bAA\u0002=\u0006!a/\\1q+\t\ty\u0001E\u0004\u0002\u0012\u0005ma.a\b\u000e\u0005\u0005M!\u0002BA\u000b\u0003/\tq!\\;uC\ndWMC\u0002\u0002\u001ay\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\ti\"a\u0005\u0003\u00075\u000b\u0007\u000fE\u0002\u0002\"\tj\u0011\u0001\u0001\u0002\u0006-\u0006dW/Z\n\u0006E\t\f9C\u001a\t\u0006O\u0006%\u0012qD\u0005\u0004\u0003Wa'aB(sI\u0016\u0014X\r\u001a\u000b\u0003\u0003?\t!!\u001b3\u0016\u00039\fQd]2bY\u0006$SI\\;nKJ\fG/[8oI\u0011zW\u000f^3s\u000b:,X\u000eI\u0001\bG>l\u0007/\u0019:f)\rq\u0017\u0011\b\u0005\b\u0003w1\u0003\u0019AA\u0010\u0003\u0011!\b.\u0019;\u0002\r\u0015\fX/\u00197t)\u0011\t\t%a\u0012\u0011\u0007\r\f\u0019%C\u0002\u0002Fy\u0013qAQ8pY\u0016\fg\u000eC\u0004\u0002J\u001d\u0002\r!a\u0013\u0002\u000b=$\b.\u001a:\u0011\u0007\r\fi%C\u0002\u0002Py\u00131!\u00118z\u0003!A\u0017m\u001d5D_\u0012,G#\u00018\u0002\u000b\u0011\u0002H.^:\u0015\t\u0005e#1\u001a\t\u0004\u0003CI$\u0001\u0003,bYV,7+\u001a;\u0014\u0015e\ny&a\u001b\u0002r\u0005ed\r\u0005\u0004\u0002b\u0005\u001d\u0014qD\u0007\u0003\u0003GRA!!\u001a\u0002\u0018\u0005I\u0011.\\7vi\u0006\u0014G.Z\u0005\u0005\u0003S\n\u0019GA\u0006BEN$(/Y2u'\u0016$\bCBA1\u0003[\ny\"\u0003\u0003\u0002p\u0005\r$!C*peR,GmU3u!)\t\t'a\u001d\u0002 \u0005]\u0014\u0011L\u0005\u0005\u0003k\n\u0019G\u0001\u0007T_J$X\rZ*fi>\u00038\u000f\u0005\u0003\u0002b\u00055\u0004CCA>\u0003{\ny\"!!\u0002Z5\u0011\u0011qC\u0005\u0005\u0003\u007f\n9B\u0001\u000eTiJL7\r^(qi&l\u0017N_3e\u0013R,'/\u00192mK>\u00038\u000f\u0005\u0003\u0002b\u0005\r\u0015\u0002BAC\u0003G\u00121aU3u\u0003\u0015qg.\u00133t!\u0011\t\t'a#\n\t\u00055\u00151\r\u0002\u0007\u0005&$8+\u001a;\u0015\t\u0005e\u0013\u0011\u0013\u0005\b\u0003\u000f[\u0004\u0019AAE\u0003!y'\u000fZ3sS:<WCAAL!\u00159\u0017\u0011TA\u0010\u0013\r\tY\n\u001c\u0002\t\u001fJ$WM]5oO\u0006I!/\u00198hK&k\u0007\u000f\u001c\u000b\u0007\u00033\n\t+a+\t\u000f\u0005\rV\b1\u0001\u0002&\u0006!aM]8n!\u0015\u0019\u0017qUA\u0010\u0013\r\tIK\u0018\u0002\u0007\u001fB$\u0018n\u001c8\t\u000f\u00055V\b1\u0001\u0002&\u0006)QO\u001c;jY\u0006)Q-\u001c9usV\u0011\u0011\u0011L\u0001\nW:|wO\\*ju\u0016\fq![:F[B$\u00180\u0006\u0002\u0002B\u0005A1m\u001c8uC&t7\u000f\u0006\u0003\u0002B\u0005u\u0006bBA`\u0003\u0002\u0007\u0011qD\u0001\u0002m\u0006!\u0011N\\2m)\u0011\tI&!2\t\u000f\u0005\u001d'\t1\u0001\u0002 \u0005)a/\u00197vK\u0006!Q\r_2m)\u0011\tI&!4\t\u000f\u0005\u001d7\t1\u0001\u0002 \u0005A\u0011\u000e^3sCR|'/\u0006\u0002\u0002TB)q-!6\u0002 %\u0019\u0011q\u001b7\u0003\u0011%#XM]1u_J\fA\"\u001b;fe\u0006$xN\u001d$s_6$B!a5\u0002^\"9\u0011q\\#A\u0002\u0005}\u0011!B:uCJ$\u0018!C2mCN\u001ch*Y7f+\u0005Q\u0018!\u0003;p\u0005&$X*Y:l+\t\tI\u000fE\u0003d\u0003W\fy/C\u0002\u0002nz\u0013Q!\u0011:sCf\u00042aYAy\u0013\r\t\u0019P\u0018\u0002\u0005\u0019>tw-\u0001\u0007ge>l7\u000b]3dS\u001aL7\r\u0006\u0003\u0002Z\u0005e\bbBA~\u0011\u0002\u0007\u0011Q`\u0001\u0005G>dG\u000eE\u0003h\u0003\u007f\fy\"C\u0002\u0003\u00021\u0014A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\f!C\\3x'B,7-\u001b4jG\n+\u0018\u000e\u001c3feV\u0011!q\u0001\t\t\u0003#\u0011I!a\b\u0002Z%!!1BA\n\u0005\u001d\u0011U/\u001b7eKJ\f1!\\1q)\u0011\tIF!\u0005\t\u000f\tM!\n1\u0001\u0003\u0016\u0005\ta\rE\u0004d\u0005/\ty\"a\b\n\u0007\teaLA\u0005Gk:\u001cG/[8oc\u00059a\r\\1u\u001b\u0006\u0004H\u0003BA-\u0005?AqAa\u0005L\u0001\u0004\u0011\t\u0003E\u0004d\u0005/\ty\"!@\u0016\t\t\u0015\"q\u0006\u000b\u0005\u0005O\u0011)\u0006\u0006\u0003\u0003*\tm\u0002CBA1\u0003[\u0012Y\u0003\u0005\u0003\u0003.\t=B\u0002\u0001\u0003\b\u0005ca%\u0019\u0001B\u001a\u0005\u0005\u0011\u0015\u0003\u0002B\u001b\u0003\u0017\u00022a\u0019B\u001c\u0013\r\u0011ID\u0018\u0002\b\u001d>$\b.\u001b8h\u0011\u001d\u0011i\u0004\u0014a\u0002\u0005\u007f\t!!\u001a<\u0011\u000b\u001d\fIJa\u000b)\u0011\tm\"1\tB(\u0005#\u0002BA!\u0012\u0003L5\u0011!q\t\u0006\u0004\u0005\u0013r\u0016AC1o]>$\u0018\r^5p]&!!Q\nB$\u0005AIW\u000e\u001d7jG&$hj\u001c;G_VtG-A\u0002ng\u001e\f#Aa\u0015\u0002\u0003\u0003qu\u000eI5na2L7-\u001b;!\u001fJ$WM]5oOn#3PQ?^A\u0019|WO\u001c3!i>\u0004#-^5mI\u0002\n\u0007eU8si\u0016$7+\u001a;\\Im\u0014U0\u0018\u0018!3>,\b%\\1zA]\fg\u000e\u001e\u0011u_\u0002*\boY1ti\u0002\"x\u000eI1!'\u0016$8LV1mk\u0016l\u0006EZ5sgR\u0004#-\u001f\u0011dC2d\u0017N\\4!AVt7o\u001c:uK\u0012\u0004g\u0006C\u0004\u0003\u00141\u0003\rAa\u0016\u0011\u000f\r\u00149\"a\b\u0003,U!!1\fB2)\u0011\u0011iFa\u001b\u0015\t\t}#Q\r\t\u0007\u0003C\niG!\u0019\u0011\t\t5\"1\r\u0003\b\u0005ci%\u0019\u0001B\u001a\u0011\u001d\u0011i$\u0014a\u0002\u0005O\u0002RaZAM\u0005CB\u0003B!\u001a\u0003D\t=#\u0011\u000b\u0005\b\u0005'i\u0005\u0019\u0001B7!\u001d\u0019'qCA\u0010\u0005_\u0002RaZA\u0000\u0005C\n1A_5q+\u0011\u0011)Ha!\u0015\t\t]$q\u0012\u000b\u0005\u0005s\u0012)\t\u0005\u0004\u0002b\u00055$1\u0010\t\bG\nu\u0014q\u0004BA\u0013\r\u0011yH\u0018\u0002\u0007)V\u0004H.\u001a\u001a\u0011\t\t5\"1\u0011\u0003\b\u0005cq%\u0019\u0001B\u001a\u0011\u001d\u0011iD\u0014a\u0002\u0005\u000f\u0003RaZAM\u0005wB\u0003B!\"\u0003D\t=#1R\u0011\u0003\u0005\u001b\u000b\u00111\u0003(pA%l\u0007\u000f\\5dSR\u0004sJ\u001d3fe&twm\u0017\u0013|\u0005vl\u0006EZ8v]\u0012\u0004Co\u001c\u0011ck&dG\rI1!'>\u0014H/\u001a3TKR\\\u0006FV1mk\u0016d\u0003\u0005J>C{&jf\u0006I-pk\u0002j\u0017-\u001f\u0011xC:$\b\u0005^8!kB\u001c\u0017m\u001d;!i>\u0004\u0013\rI*fin3\u0016\r\\;f;\u00022\u0017N]:uA\tL\beY1mY&tw\r\t1v]N|'\u000f^3eA:Bq!a\u000fO\u0001\u0004\u0011\t\nE\u0003h\u0003\u007f\u0014\t)A\u0004d_2dWm\u0019;\u0016\t\t]%q\u0014\u000b\u0005\u00053\u00139\u000b\u0006\u0003\u0003\u001c\n\u0005\u0006CBA1\u0003[\u0012i\n\u0005\u0003\u0003.\t}Ea\u0002B\u0019\u001f\n\u0007!1\u0007\u0005\b\u0005{y\u00059\u0001BR!\u00159\u0017\u0011\u0014BOQ!\u0011\tKa\u0011\u0003P\tE\u0003b\u0002BU\u001f\u0002\u0007!1V\u0001\u0003a\u001a\u0004ra\u0019BW\u0003?\u0011i*C\u0002\u00030z\u0013q\u0002U1si&\fGNR;oGRLwN\\\u0001\u001ag\u000e\fG.\u0019\u0013F]VlWM]1uS>tG\u0005\n2z\u001d\u0006lW-\u0006\u0002\u00036B11Pa.{\u0003?IA!!\b\u0002\n!\u001a\u0001Ka/\u0011\u0007\r\u0014i,C\u0002\u0003@z\u0013\u0011\u0002\u001e:b]NLWM\u001c;)\u000fe\u0012\u0019-a2\u0003JB\u00191M!2\n\u0007\t\u001dgL\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\tzAA\rVz(\u0002a{%\u0013C\u0004\u0002@&\u0002\r!a\b\u00029M\u001c\u0017\r\\1%\u000b:,X.\u001a:bi&|g\u000e\n\u0013pkR,'/\u00128v[V\t1\u000fK\u0004#\u0005\u0007\f9Ma5\u001f\u0011\tLGpLw\";E\u000bQA^7ba\u0002\nAA^:fi\u0006Aao]3u?\u0012*\u0017\u000f\u0006\u0003\u0003^\n\r\bcA2\u0003`&\u0019!\u0011\u001d0\u0003\tUs\u0017\u000e\u001e\u0005\n\u0005KL\u0011\u0011!a\u0001\u00033\n1\u0001\u001f\u00132\u0003\u001518/\u001a;!Q\rQ!1X\u0001\fmN,G\u000fR3gS:,G-A\bwg\u0016$H)\u001a4j]\u0016$w\fJ3r)\u0011\u0011iN!=\t\u0013\t\u0015H\"!AA\u0002\u0005\u0005\u0013\u0001\u0004<tKR$UMZ5oK\u0012\u0004\u0003fA\u0007\u0003xB\u00191M!?\n\u0007\tmhL\u0001\u0005w_2\fG/\u001b7fQ\ri!1X\u0001\u0005]6\f\u0007\u000f\u0005\u0004\u0002\u0012\u0005maN_\u0001\u0007m\u0006dW/Z:\u0002\r9,\u0007\u0010^%e\u0003)qW\r\u001f;JI~#S-\u001d\u000b\u0005\u0005;\u001cY\u0001\u0003\u0005\u0003fF\t\t\u00111\u0001o\u0003\u001dqW\r\u001f;JI\u0002\n\u0001B\\3yi:\u000bW.Z\u000b\u0003\u0007'\u0001BaZAku\u0006aa.\u001a=u\u001d\u0006lWm\u0018\u0013fcR!!Q\\B\r\u0011%\u0011)\u000fFA\u0001\u0002\u0004\u0019\u0019\"A\u0005oKb$h*Y7fA\u0005qa.\u001a=u\u001d\u0006lWm\u0014:Ok2d\u0017!\u0002;pa&#\u0017\u0001\u00032piR|W.\u00133\u0002\u000b5\f\u00070\u00133\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\u0005}1\u0011\u0006\u0005\u0007\u0007WQ\u0002\u0019\u00018\u0002\u0003a\f\u0001b^5uQ:\u000bW.\u001a\u000b\u0005\u0003?\u0019\t\u0004\u0003\u0004\u00044m\u0001\rA_\u0001\u0002g\u0006)a+\u00197vKV\u0011\u0011q\u0004\u000b\u0005\u0003?\u0019Y\u0004\u0003\u0004\u0004>u\u0001\rA\\\u0001\u0002SR!\u0011qDB!\u0011\u0019\u0019\u0019E\ba\u0001u\u0006!a.Y7f)\u0019\tyba\u0012\u0004J!11QH\u0010A\u00029Daaa\u0011 \u0001\u0004Q\u0018a\u00049paVd\u0017\r^3OC6,W*\u00199\u0015\u0005\tu\u0017A\u00028b[\u0016|e\rF\u0002{\u0007'Baa!\u0010\"\u0001\u0004q'a\u0001,bYN!1&a\bg)\u0019\u0019Yf!\u0018\u0004`A\u0019\u0011\u0011E\u0016\t\r\rub\u00061\u0001o\u0011\u0019\u0019\u0019E\fa\u0001uR!11LB2\u0011\u0019\u0019id\fa\u0001]R!11LB4\u0011\u0019\u0019\u0019\u0005\ra\u0001uR\u001111\f\u0015\bW\t\r\u0017qYB7=!y\u001dnZXJz8\u0000\u0015!\u0004,bYV,wJ\u001d3fe&tw\rE\u0002\u0002\"Y\u0012QBV1mk\u0016|%\u000fZ3sS:<7#\u0002\u001c\u0004x\u0005]\u0005\u0003BB=\u0007\u0007k!aa\u001f\u000b\t\ru4qP\u0001\u0005Y\u0006twM\u0003\u0002\u0004\u0002\u0006!!.\u0019<b\u0013\u0011\u0019)ia\u001f\u0003\r=\u0013'.Z2u)\t\u0019\t\bF\u0003o\u0007\u0017\u001bi\tC\u0004\u0004,a\u0002\r!a\b\t\u000f\r=\u0005\b1\u0001\u0002 \u0005\t\u00110\u0001\u0005WC2,XmU3u!\r\t\tCU\n\u0007%\n\u001c9j!(\u0011\u0011\u0005m4\u0011TA\u0010\u00033JAaa'\u0002\u0018\t92\u000b]3dS\u001aL7-\u0013;fe\u0006\u0014G.\u001a$bGR|'/\u001f\t\u0005\u0007?\u001b)+\u0004\u0002\u0004\"*!11UB@\u0003\tIw.C\u0002l\u0007C#\"aa%\u0002\r=\u0014H-T:h+\t\u0019ik\u0004\u0002\u0003R\u00059qN\u001d3Ng\u001e\u0004\u0013!\u0003>ja>\u0013H-T:h+\t\u0019)l\u0004\u0002\u0003\f\u0006Q!0\u001b9Pe\u0012l5o\u001a\u0011\u0002\r\u0015l\u0007\u000f^=!\u0003-1'o\\7CSRl\u0015m]6\u0015\t\u0005e3q\u0018\u0005\b\u0007\u0003T\u0006\u0019AAu\u0003\u0015)G.Z7t\u0003)qWm\u001e\"vS2$WM\u001d\u000b\u0005\u00033\u001a9\rC\u0004\u0004Jr\u0003\r!!@\u0002\u0005%$\bf\u0002*\u0003D\u0006\u001d7Q\u001a\u0010\u0002\u0007!:\u0011Ka1\u0002H\u000e5\u0007f\u0002\u0001\u0003D\u0006\u001d71\u001b\u0010\tk\u0002n]\u001c'\bZ\u001d\u0003")
public abstract class Enumeration
implements Serializable {
    private static final long serialVersionUID = 8476000850333817230L;
    private volatile Enumeration$ValueOrdering$ ValueOrdering$module;
    private volatile Enumeration$ValueSet$ ValueSet$module;
    private final Map<Object, Value> scala$Enumeration$$vmap = new HashMap<Object, Value>();
    private transient ValueSet vset = null;
    private volatile transient boolean scala$Enumeration$$vsetDefined = false;
    private final Map<Object, String> nmap = new HashMap<Object, String>();
    private int nextId;
    private Iterator<String> nextName;
    public int scala$Enumeration$$topId;
    public int scala$Enumeration$$bottomId;

    public Enumeration$ValueOrdering$ ValueOrdering() {
        if (this.ValueOrdering$module == null) {
            this.ValueOrdering$lzycompute$1();
        }
        return this.ValueOrdering$module;
    }

    public Enumeration$ValueSet$ ValueSet() {
        if (this.ValueSet$module == null) {
            this.ValueSet$lzycompute$1();
        }
        return this.ValueSet$module;
    }

    public Object readResolve() {
        return this.getClass().getField("MODULE$").get(null);
    }

    public String toString() {
        return (String)ArrayOps$.MODULE$.last$extension(((String)ArrayOps$.MODULE$.last$extension(StringOps$.MODULE$.split$extension(StringOps$.MODULE$.stripSuffix$extension(this.getClass().getName(), "$"), '.'))).split(Pattern.quote("$")));
    }

    public Map<Object, Value> scala$Enumeration$$vmap() {
        return this.scala$Enumeration$$vmap;
    }

    private ValueSet vset() {
        return this.vset;
    }

    private void vset_$eq(ValueSet x$1) {
        this.vset = x$1;
    }

    private boolean vsetDefined() {
        return this.scala$Enumeration$$vsetDefined;
    }

    public void scala$Enumeration$$vsetDefined_$eq(boolean x$1) {
        this.scala$Enumeration$$vsetDefined = x$1;
    }

    public ValueSet values() {
        if (!this.vsetDefined()) {
            Enumeration$ValueSet$ enumeration$ValueSet$ = this.ValueSet();
            if (enumeration$ValueSet$ == null) {
                throw null;
            }
            Enumeration$ValueSet$ newBuilder_this = enumeration$ValueSet$;
            Object var1_1 = null;
            this.vset_$eq((ValueSet)((Builder)Growable.addAll$(new Builder<Value, ValueSet>(newBuilder_this){
                private final BitSet b;
                private final /* synthetic */ Enumeration$ValueSet$ $outer;

                public void sizeHint(int size) {
                    Builder.sizeHint$(this, size);
                }

                public final void sizeHint(IterableOnce<?> coll, int delta) {
                    Builder.sizeHint$(this, coll, delta);
                }

                public final int sizeHint$default$2() {
                    return Builder.sizeHint$default$2$(this);
                }

                public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
                    Builder.sizeHintBounded$(this, size, boundingColl);
                }

                public <NewTo> Builder<Value, NewTo> mapResult(Function1<ValueSet, NewTo> f) {
                    return Builder.mapResult$(this, f);
                }

                public final Growable $plus$eq(Object elem) {
                    return Growable.$plus$eq$(this, elem);
                }

                public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
                    return Growable.$plus$eq$(this, elem1, elem2, elems);
                }

                public Growable<Value> addAll(IterableOnce<Value> xs) {
                    return Growable.addAll$(this, xs);
                }

                public final Growable<Value> $plus$plus$eq(IterableOnce<Value> xs) {
                    return Growable.$plus$plus$eq$(this, xs);
                }

                public int knownSize() {
                    return Growable.knownSize$(this);
                }

                /*
                 * WARNING - void declaration
                 */
                public ValueSet$$anon$1 addOne(Value x) {
                    void $plus$eq_elem;
                    BitSet bitSet = this.b;
                    Integer n = x.id() - this.$outer.scala$Enumeration$ValueSet$$$outer().scala$Enumeration$$bottomId;
                    if (bitSet == null) {
                        throw null;
                    }
                    bitSet.addOne($plus$eq_elem);
                    return this;
                }

                public void clear() {
                    this.b.clear();
                }

                public ValueSet result() {
                    return new ValueSet(this.$outer.scala$Enumeration$ValueSet$$$outer(), this.b.toImmutable());
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.b = new BitSet();
                }
            }, this.scala$Enumeration$$vmap().values())).result());
            this.scala$Enumeration$$vsetDefined_$eq(true);
        }
        return this.vset();
    }

    public int nextId() {
        return this.nextId;
    }

    public void nextId_$eq(int x$1) {
        this.nextId = x$1;
    }

    public Iterator<String> nextName() {
        return this.nextName;
    }

    public void nextName_$eq(Iterator<String> x$1) {
        this.nextName = x$1;
    }

    public String scala$Enumeration$$nextNameOrNull() {
        if (this.nextName() != null && this.nextName().hasNext()) {
            return this.nextName().next();
        }
        return null;
    }

    public final int maxId() {
        return this.scala$Enumeration$$topId;
    }

    public final Value apply(int x) {
        return (Value)this.scala$Enumeration$$vmap().apply(Integer.valueOf(x));
    }

    public final Value withName(String s2) {
        return (Value)this.values().scala$Enumeration$$byName().getOrElse(s2, (Function0<Nothing$> & Serializable)() -> {
            throw new NoSuchElementException(new StringBuilder(21).append("No value found for '").append(s2).append("'").toString());
        });
    }

    public final Value Value() {
        return this.Value(this.nextId());
    }

    public final Value Value(int i) {
        String Value_name = this.scala$Enumeration$$nextNameOrNull();
        return new Val(this, i, Value_name);
    }

    public final Value Value(String name) {
        int Value_i = this.nextId();
        return new Val(this, Value_i, name);
    }

    public final Value Value(int i, String name) {
        return new Val(this, i, name);
    }

    private void populateNameMap() {
        ArrayBuilder arrayBuilder;
        Object var2_2;
        Field[] fields = this.getFields$1(this.getClass().getSuperclass(), this.getClass().getDeclaredFields());
        Method[] refArrayOps_xs = this.getClass().getMethods();
        void v0 = var2_2;
        var2_2 = null;
        void filter$extension_$this = v0;
        ClassTag filter$extension_make_evidence$1 = ClassTag$.MODULE$.apply(filter$extension_$this.getClass().getComponentType());
        Class<?> clazz = filter$extension_make_evidence$1.runtimeClass();
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
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(filter$extension_make_evidence$1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Object var7_4 = null;
        clazz = null;
        ArrayBuilder filter$extension_res = arrayBuilder;
        for (int filter$extension_i = 0; filter$extension_i < ((void)filter$extension_$this).length; ++filter$extension_i) {
            void filter$extension_x = filter$extension_$this[filter$extension_i];
            if (!Enumeration.$anonfun$populateNameMap$2(fields, (Method)filter$extension_x)) continue;
            filter$extension_res.addOne(filter$extension_x);
        }
        Object var3_3 = null;
        Object var4_6 = null;
        Object var6_8 = null;
        for (Method method2 : (Method[])filter$extension_res.result()) {
            Enumeration.$anonfun$populateNameMap$3(this, method2);
        }
    }

    public synchronized String scala$Enumeration$$nameOf(int i) {
        return (String)this.nmap.getOrElse(i, (Function0<String> & Serializable)() -> {
            this.populateNameMap();
            return (String)$this.nmap.apply(Integer.valueOf(i));
        });
    }

    private final void ValueOrdering$lzycompute$1() {
        synchronized (this) {
            if (this.ValueOrdering$module == null) {
                this.ValueOrdering$module = new Enumeration$ValueOrdering$(this);
            }
            return;
        }
    }

    private final void ValueSet$lzycompute$1() {
        synchronized (this) {
            if (this.ValueSet$module == null) {
                this.ValueSet$module = new Enumeration$ValueSet$(this);
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private final Field[] getFields$1(Class clazz, Field[] acc) {
        while (clazz != null) {
            Field[] fieldArray;
            Class clazz2 = clazz.getSuperclass();
            if (clazz.getDeclaredFields().length == 0) {
                fieldArray = acc;
            } else {
                void $plus$plus$extension_evidence$25;
                ClassTag classTag = ClassTag$.MODULE$.apply(Field.class);
                Field[] $plus$plus$extension_xs = clazz.getDeclaredFields();
                Object var3_3 = null;
                classTag = null;
                fieldArray = (Field[])ArrayOps$.MODULE$.appendedAll$extension((Object)acc, $plus$plus$extension_xs, $plus$plus$extension_evidence$25);
            }
            acc = fieldArray;
            clazz = clazz2;
        }
        return acc;
    }

    public static final /* synthetic */ boolean $anonfun$populateNameMap$1(Method m$1, Field fd) {
        String string = fd.getName();
        String string2 = m$1.getName();
        if (!(string != null ? !string.equals(string2) : string2 != null)) {
            Class<?> clazz = fd.getType();
            Class<?> clazz2 = m$1.getReturnType();
            if (!(clazz != null ? !clazz.equals(clazz2) : clazz2 != null)) {
                return true;
            }
        }
        return false;
    }

    private static final boolean isValDef$1(Method m4, Field[] fields$1) {
        int n;
        block1: {
            for (int exists$extension_indexWhere$extension_i = 0; exists$extension_indexWhere$extension_i < fields$1.length; ++exists$extension_indexWhere$extension_i) {
                Field field2 = fields$1[exists$extension_indexWhere$extension_i];
                if (!Enumeration.$anonfun$populateNameMap$1(m4, field2)) continue;
                n = exists$extension_indexWhere$extension_i;
                break block1;
            }
            n = -1;
        }
        return n >= 0;
    }

    public static final /* synthetic */ boolean $anonfun$populateNameMap$2(Field[] fields$1, Method m4) {
        if (m4.getParameterTypes().length == 0 && Value.class.isAssignableFrom(m4.getReturnType())) {
            Class<?> clazz = m4.getDeclaringClass();
            Class<Enumeration> clazz2 = Enumeration.class;
            if ((clazz == null || !clazz.equals(clazz2)) && Enumeration.isValDef$1(m4, fields$1)) {
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ Object $anonfun$populateNameMap$3(Enumeration $this, Method m4) {
        String name = m4.getName();
        Value value = (Value)m4.invoke((Object)$this, new Object[0]);
        if (value.scala$Enumeration$$outerEnum() == $this) {
            void $plus$eq_elem;
            int id = value.id();
            Map<Object, String> map = $this.nmap;
            Tuple2<Integer, String> tuple2 = new Tuple2<Integer, String>(id, name);
            if (map == null) {
                throw null;
            }
            return map.addOne($plus$eq_elem);
        }
        return BoxedUnit.UNIT;
    }

    public Enumeration(int initial) {
        this.nextId = initial;
        this.scala$Enumeration$$topId = initial;
        this.scala$Enumeration$$bottomId = initial < 0 ? initial : 0;
    }

    public Enumeration() {
        this(0);
    }

    public static final /* synthetic */ Object $anonfun$populateNameMap$2$adapted(Field[] fields$1, Method m4) {
        return BoxesRunTime.boxToBoolean(Enumeration.$anonfun$populateNameMap$2(fields$1, m4));
    }

    public static final /* synthetic */ Object $anonfun$populateNameMap$1$adapted(Method m$1, Field fd) {
        return BoxesRunTime.boxToBoolean(Enumeration.$anonfun$populateNameMap$1(m$1, fd));
    }

    public class Val
    extends Value {
        private static final long serialVersionUID = -3501153230598116017L;
        private final int i;
        private final String name;

        @Override
        public int id() {
            return this.i;
        }

        public String toString() {
            if (this.name != null) {
                return this.name;
            }
            try {
                return this.scala$Enumeration$Val$$$outer().scala$Enumeration$$nameOf(this.i);
            }
            catch (NoSuchElementException noSuchElementException) {
                return new StringBuilder(30).append("<Invalid enum: no field for #").append(this.i).append(">").toString();
            }
        }

        public Object readResolve() {
            Enumeration enumeration = (Enumeration)this.scala$Enumeration$Val$$$outer().readResolve();
            if (enumeration.scala$Enumeration$$vmap() == null) {
                return this;
            }
            return enumeration.scala$Enumeration$$vmap().apply(Integer.valueOf(this.i));
        }

        public /* synthetic */ Enumeration scala$Enumeration$Val$$$outer() {
            return this.$outer;
        }

        public static final /* synthetic */ String $anonfun$new$1(Val $this) {
            return new StringBuilder(14).append("Duplicate id: ").append($this.i).toString();
        }

        public Val(Enumeration $outer, int i, String name) {
            this.i = i;
            this.name = name;
            if (!(!$outer.scala$Enumeration$$vmap().isDefinedAt(Integer.valueOf(i)))) {
                throw new AssertionError((Object)new StringBuilder(18).append("assertion failed: ").append((Object)Val.$anonfun$new$1(this)).toString());
            }
            $outer.scala$Enumeration$$vmap().update(i, this);
            $outer.scala$Enumeration$$vsetDefined_$eq(false);
            $outer.nextId_$eq(i + 1);
            if ($outer.nextId() > $outer.scala$Enumeration$$topId) {
                $outer.scala$Enumeration$$topId = $outer.nextId();
            }
            if (i < $outer.scala$Enumeration$$bottomId) {
                $outer.scala$Enumeration$$bottomId = i;
            }
        }

        public Val(Enumeration $outer, int i) {
            this($outer, i, $outer.scala$Enumeration$$nextNameOrNull());
        }

        public Val(Enumeration $outer, String name) {
            this($outer, $outer.nextId(), name);
        }

        public Val(Enumeration $outer) {
            this($outer, $outer.nextId());
        }
    }

    public abstract class Value
    implements Ordered<Value>,
    Serializable {
        private static final long serialVersionUID = 7091335633555234129L;
        private final Enumeration scala$Enumeration$$outerEnum;

        @Override
        public boolean $less(Object that) {
            return Ordered.$less$(this, that);
        }

        @Override
        public boolean $greater(Object that) {
            return Ordered.$greater$(this, that);
        }

        @Override
        public boolean $less$eq(Object that) {
            return Ordered.$less$eq$(this, that);
        }

        @Override
        public boolean $greater$eq(Object that) {
            return Ordered.$greater$eq$(this, that);
        }

        @Override
        public int compareTo(Object that) {
            return Ordered.compareTo$(this, that);
        }

        public abstract int id();

        public Enumeration scala$Enumeration$$outerEnum() {
            return this.scala$Enumeration$$outerEnum;
        }

        @Override
        public int compare(Value that) {
            if (this.id() < that.id()) {
                return -1;
            }
            if (this.id() == that.id()) {
                return 0;
            }
            return 1;
        }

        public boolean equals(Object other) {
            if (other instanceof Value) {
                Value value = (Value)other;
                return this.scala$Enumeration$$outerEnum() == value.scala$Enumeration$$outerEnum() && this.id() == value.id();
            }
            return false;
        }

        public int hashCode() {
            return this.id();
        }

        /*
         * WARNING - void declaration
         */
        public ValueSet $plus(Value v) {
            void apply_xs;
            Enumeration$ValueSet$ enumeration$ValueSet$ = this.scala$Enumeration$Value$$$outer().ValueSet();
            ArraySeq<Value> arraySeq = ScalaRunTime$.MODULE$.wrapRefArray(new Value[]{this, v});
            if (enumeration$ValueSet$ == null) {
                throw null;
            }
            return enumeration$ValueSet$.fromSpecific((IterableOnce)apply_xs);
        }

        public /* synthetic */ Enumeration scala$Enumeration$Value$$$outer() {
            return Enumeration.this;
        }

        public Value() {
            if (Enumeration.this == null) {
                throw null;
            }
            this.scala$Enumeration$$outerEnum = Enumeration.this;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public class ValueSet
    extends AbstractSet<Value>
    implements SortedSet<Value>,
    StrictOptimizedIterableOps<Value, scala.collection.immutable.Set, ValueSet>,
    Serializable {
        private static final long serialVersionUID = 7229671200427364242L;
        private transient scala.collection.immutable.Map<String, Value> scala$Enumeration$$byName;
        private scala.collection.immutable.BitSet nnIds;
        private volatile transient boolean bitmap$trans$0;
        public final /* synthetic */ Enumeration $outer;

        @Override
        public Tuple2<ValueSet, ValueSet> partition(Function1<Value, Object> p) {
            return StrictOptimizedIterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<ValueSet, ValueSet> span(Function1<Value, Object> p) {
            return StrictOptimizedIterableOps.span$(this, p);
        }

        @Override
        public <A1, A2> Tuple2<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>> unzip(Function1<Value, Tuple2<A1, A2>> asPair) {
            return StrictOptimizedIterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>, scala.collection.immutable.Set<A3>> unzip3(Function1<Value, Tuple3<A1, A2, A3>> asTriple) {
            return StrictOptimizedIterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Object map(Function1 f) {
            return StrictOptimizedIterableOps.map$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Value, B> f) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return StrictOptimizedIterableOps.flatMap$(this, f);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Value, IterableOnce<B>> f) {
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
        public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Value, B> pf) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
        }

        @Override
        public Object flatten(Function1 toIterableOnce) {
            return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
        }

        @Override
        public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Value, IterableOnce<B>> toIterableOnce) {
            return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
        }

        @Override
        public Object zip(IterableOnce that) {
            return StrictOptimizedIterableOps.zip$(this, that);
        }

        @Override
        public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Value, B>, C2> b) {
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
        public <A1, A2> Tuple2<scala.collection.immutable.Set<A1>, scala.collection.immutable.Set<A2>> partitionMap(Function1<Value, Either<A1, A2>> f) {
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
        public scala.collection.immutable.Set<Value> unsorted() {
            return SortedSet.unsorted$(this);
        }

        @Override
        public SortedIterableFactory<SortedSet> sortedIterableFactory() {
            return SortedSet.sortedIterableFactory$(this);
        }

        @Override
        public /* synthetic */ boolean scala$collection$SortedSet$$super$equals(Object that) {
            return Set.equals$(this, that);
        }

        @Override
        public String stringPrefix() {
            return scala.collection.SortedSet.stringPrefix$(this);
        }

        @Override
        public boolean equals(Object that) {
            return scala.collection.SortedSet.equals$(this, that);
        }

        @Override
        public SortedSetOps.WithFilter<Value, ?, ?> withFilter(Function1<Value, Object> p) {
            return SortedSetFactoryDefaults.withFilter$(this, p);
        }

        @Override
        public /* synthetic */ Object scala$collection$SortedSetOps$$super$min(Ordering ord) {
            return IterableOnceOps.min$(this, ord);
        }

        @Override
        public /* synthetic */ Object scala$collection$SortedSetOps$$super$max(Ordering ord) {
            return IterableOnceOps.max$(this, ord);
        }

        @Override
        public Iterator keysIteratorFrom(Object start2) {
            return SortedSetOps.keysIteratorFrom$(this, start2);
        }

        @Override
        public Object firstKey() {
            return SortedSetOps.firstKey$(this);
        }

        @Override
        public Object lastKey() {
            return SortedSetOps.lastKey$(this);
        }

        @Override
        public Option minAfter(Object key) {
            return SortedSetOps.minAfter$(this, key);
        }

        @Override
        public Option maxBefore(Object key) {
            return SortedSetOps.maxBefore$(this, key);
        }

        @Override
        public Object min(Ordering ord) {
            return SortedSetOps.min$(this, ord);
        }

        @Override
        public Object max(Ordering ord) {
            return SortedSetOps.max$(this, ord);
        }

        @Override
        public SortedSetOps rangeTo(Object to) {
            return SortedSetOps.rangeTo$(this, to);
        }

        @Override
        public int compare(Object k0, Object k1) {
            return SortedOps.compare$(this, k0, k1);
        }

        @Override
        public Object range(Object from, Object until) {
            return SortedOps.range$(this, from, until);
        }

        @Override
        public final Object from(Object from) {
            return SortedOps.from$(this, from);
        }

        @Override
        public Object rangeFrom(Object from) {
            return SortedOps.rangeFrom$(this, from);
        }

        @Override
        public final Object until(Object until) {
            return SortedOps.until$(this, until);
        }

        @Override
        public Object rangeUntil(Object until) {
            return SortedOps.rangeUntil$(this, until);
        }

        @Override
        public final Object to(Object to) {
            return SortedOps.to$(this, to);
        }

        @Override
        public Ordering<Value> ordering() {
            return this.scala$Enumeration$ValueSet$$$outer().ValueOrdering();
        }

        @Override
        public ValueSet rangeImpl(Option<Value> from, Option<Value> until) {
            Option option;
            Option option2;
            Enumeration enumeration = this.scala$Enumeration$ValueSet$$$outer();
            if (from == null) {
                throw null;
            }
            if (from.isEmpty()) {
                option2 = None$.MODULE$;
            } else {
                Value value = from.get();
                Option option3 = option2 = new Option(ValueSet.$anonfun$rangeImpl$1(this, value));
            }
            if (until == null) {
                throw null;
            }
            if (until.isEmpty()) {
                option = None$.MODULE$;
            } else {
                Value value = until.get();
                Some<Integer> some = new Some<Integer>(ValueSet.$anonfun$rangeImpl$2(this, value));
                option = some;
            }
            return new ValueSet(enumeration, (scala.collection.immutable.BitSet)this.nnIds.rangeImpl(option2, option));
        }

        @Override
        public ValueSet empty() {
            return this.scala$Enumeration$ValueSet$$$outer().ValueSet().empty();
        }

        @Override
        public int knownSize() {
            return this.nnIds.size();
        }

        @Override
        public boolean isEmpty() {
            return this.nnIds.isEmpty();
        }

        @Override
        public boolean contains(Value v) {
            return this.nnIds.contains(v.id() - this.scala$Enumeration$ValueSet$$$outer().scala$Enumeration$$bottomId);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public ValueSet incl(Value value) {
            void $plus_elem;
            scala.collection.immutable.BitSet bitSet = this.nnIds;
            Integer n = value.id() - this.scala$Enumeration$ValueSet$$$outer().scala$Enumeration$$bottomId;
            if (bitSet == null) {
                throw null;
            }
            n = null;
            return new ValueSet(this.scala$Enumeration$ValueSet$$$outer(), (scala.collection.immutable.BitSet)bitSet.incl($plus_elem));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public ValueSet excl(Value value) {
            void $minus_elem;
            scala.collection.immutable.BitSet bitSet = this.nnIds;
            Integer n = value.id() - this.scala$Enumeration$ValueSet$$$outer().scala$Enumeration$$bottomId;
            if (bitSet == null) {
                throw null;
            }
            n = null;
            return new ValueSet(this.scala$Enumeration$ValueSet$$$outer(), (scala.collection.immutable.BitSet)bitSet.excl($minus_elem));
        }

        @Override
        public Iterator<Value> iterator() {
            return this.nnIds.iterator().map((Function1<Object, Value> & Serializable)(A id) -> ValueSet.$anonfun$iterator$1(this, BoxesRunTime.unboxToInt(id)));
        }

        @Override
        public Iterator<Value> iteratorFrom(Value start2) {
            return this.nnIds.iteratorFrom(start2.id()).map((Function1<Object, Value> & Serializable)(A id) -> ValueSet.$anonfun$iteratorFrom$1(this, BoxesRunTime.unboxToInt(id)));
        }

        @Override
        public String className() {
            return new StringBuilder(9).append(this.scala$Enumeration$ValueSet$$$outer()).append(".ValueSet").toString();
        }

        public long[] toBitMask() {
            return this.nnIds.toBitMask();
        }

        @Override
        public ValueSet fromSpecific(IterableOnce<Value> coll) {
            return this.scala$Enumeration$ValueSet$$$outer().ValueSet().fromSpecific((IterableOnce)coll);
        }

        @Override
        public Builder<Value, ValueSet> newSpecificBuilder() {
            Enumeration$ValueSet$ enumeration$ValueSet$ = this.scala$Enumeration$ValueSet$$$outer().ValueSet();
            if (enumeration$ValueSet$ == null) {
                throw null;
            }
            Enumeration$ValueSet$ newBuilder_this = enumeration$ValueSet$;
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        public ValueSet map(Function1<Value, Value> f) {
            return this.fromSpecific(new View.Map<Value, Value>(this, f));
        }

        @Override
        public ValueSet flatMap(Function1<Value, IterableOnce<Value>> f) {
            return this.fromSpecific(new View.FlatMap<Value, Value>(this, f));
        }

        @Override
        public <B> SortedSet<B> map(Function1<Value, B> f, Ordering<B> ev) {
            return (SortedSet)SortedSetOps.map$(this, f, ev);
        }

        @Override
        public <B> SortedSet<B> flatMap(Function1<Value, IterableOnce<B>> f, Ordering<B> ev) {
            return (SortedSet)SortedSetOps.flatMap$(this, f, ev);
        }

        @Override
        public <B> SortedSet<Tuple2<Value, B>> zip(IterableOnce<B> that, Ordering<Tuple2<Value, B>> ev) {
            return (SortedSet)SortedSetOps.zip$(this, that, ev);
        }

        @Override
        public <B> SortedSet<B> collect(PartialFunction<Value, B> pf, Ordering<B> ev) {
            return (SortedSet)SortedSetOps.collect$(this, pf, ev);
        }

        private scala.collection.immutable.Map<String, Value> scala$Enumeration$$byName$lzycompute() {
            synchronized (this) {
                if (!this.bitmap$trans$0) {
                    this.scala$Enumeration$$byName = this.iterator().map((Function1<Value, Tuple2> & Serializable)(A v) -> {
                        Object var2_1;
                        String ArrowAssoc_self = v.toString();
                        void v0 = var2_1;
                        var2_1 = null;
                        void $minus$greater$extension_$this = v0;
                        return new Tuple2<void, Value>($minus$greater$extension_$this, (Value)v);
                    }).toMap($less$colon$less$.MODULE$.refl());
                    this.bitmap$trans$0 = true;
                }
            }
            return this.scala$Enumeration$$byName;
        }

        public scala.collection.immutable.Map<String, Value> scala$Enumeration$$byName() {
            if (!this.bitmap$trans$0) {
                return this.scala$Enumeration$$byName$lzycompute();
            }
            return this.scala$Enumeration$$byName;
        }

        public /* synthetic */ Enumeration scala$Enumeration$ValueSet$$$outer() {
            return this.$outer;
        }

        public static final /* synthetic */ int $anonfun$rangeImpl$1(ValueSet $this, Value x$2) {
            return x$2.id() - $this.scala$Enumeration$ValueSet$$$outer().scala$Enumeration$$bottomId;
        }

        public static final /* synthetic */ int $anonfun$rangeImpl$2(ValueSet $this, Value x$3) {
            return x$3.id() - $this.scala$Enumeration$ValueSet$$$outer().scala$Enumeration$$bottomId;
        }

        public static final /* synthetic */ Value $anonfun$iterator$1(ValueSet $this, int id) {
            return $this.scala$Enumeration$ValueSet$$$outer().apply($this.scala$Enumeration$ValueSet$$$outer().scala$Enumeration$$bottomId + id);
        }

        public static final /* synthetic */ Value $anonfun$iteratorFrom$1(ValueSet $this, int id) {
            return $this.scala$Enumeration$ValueSet$$$outer().apply($this.scala$Enumeration$ValueSet$$$outer().scala$Enumeration$$bottomId + id);
        }

        public ValueSet(Enumeration $outer, scala.collection.immutable.BitSet nnIds) {
            this.nnIds = nnIds;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }

        public static final /* synthetic */ Object $anonfun$rangeImpl$1$adapted(ValueSet $this, Value x$2) {
            return BoxesRunTime.boxToInteger(ValueSet.$anonfun$rangeImpl$1($this, x$2));
        }

        public static final /* synthetic */ Object $anonfun$rangeImpl$2$adapted(ValueSet $this, Value x$3) {
            return BoxesRunTime.boxToInteger(ValueSet.$anonfun$rangeImpl$2($this, x$3));
        }
    }
}

