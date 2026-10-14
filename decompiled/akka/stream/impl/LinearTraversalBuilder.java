/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.impl.EmptyTraversal$;
import akka.stream.impl.EnterIsland;
import akka.stream.impl.ExitIsland$;
import akka.stream.impl.GraphStageTag$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.LinearTraversalBuilder$;
import akka.stream.impl.PopAttributes$;
import akka.stream.impl.PushAttributes;
import akka.stream.impl.PushNotUsed$;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.Transform;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuilder;
import akka.stream.scaladsl.Keep$;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Product;
import scala.Tuple9;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Null$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r\rtA\u0002(P\u0011\u0003\u0019VK\u0002\u0004X\u001f\"\u00051\u000b\u0017\u0005\u0006O\u0006!\t!\u001b\u0005\bU\u0006\u0011\r\u0011\"\u0003l\u0011\u001d\u0011Y.\u0001Q\u0001\n1D\u0001B!8\u0002A\u00035!q\u001c\u0005\t\u0005K\f\u0001\u0015!\u0004\u0003`\"9!q]\u0001\u0005\u0002\t%\b\"\u0003Bw\u0003E\u0005I\u0011\u0001B=\u0011\u001d\u0011y/\u0001C\u0001\u0005cDqaa\u0002\u0002\t\u0003\u0019I\u0001C\u0004\u0004\u0010\u0005!\ta!\u0005\t\u0013\ru\u0011!%A\u0005\u0002\r}\u0001\"CB\u0012\u0003\u0005\u0005I\u0011QB\u0013\u0011%\u0019I$AI\u0001\n\u0003\u0011i\u0007C\u0005\u0004<\u0005\t\n\u0011\"\u0001\u0003\u0002\"I1QH\u0001\u0002\u0002\u0013\u00055q\b\u0005\n\u0007#\n\u0011\u0013!C\u0001\u0005[B\u0011ba\u0015\u0002#\u0003%\tA!!\t\u0013\rU\u0013!!A\u0005\n\r]c!B,P\u0005Nk\u0007\"C@\u0015\u0005+\u0007I\u0011AA\u0001\u0011)\t9\u0002\u0006B\tB\u0003%\u00111\u0001\u0005\u000b\u00033!\"Q3A\u0005\u0002\u0005m\u0001BCA\u0013)\tE\t\u0015!\u0003\u0002\u001e!Q\u0011q\u0005\u000b\u0003\u0016\u0004%\t!!\u000b\t\u0015\u0005EBC!E!\u0002\u0013\tY\u0003\u0003\u0006\u00024Q\u0011)\u001a!C!\u0003SA!\"!\u000e\u0015\u0005#\u0005\u000b\u0011BA\u0016\u0011)\t9\u0004\u0006BK\u0002\u0013\u0005\u0011\u0011\b\u0005\u000b\u0003\u0003\"\"\u0011#Q\u0001\n\u0005m\u0002BCA\")\tU\r\u0011\"\u0001\u0002F!Q\u0011\u0011\n\u000b\u0003\u0012\u0003\u0006I!a\u0012\t\u0015\u0005-CC!f\u0001\n\u0003\ti\u0005\u0003\u0006\u0002VQ\u0011\t\u0012)A\u0005\u0003\u001fB!\"a\u0016\u0015\u0005+\u0007I\u0011AA\u001d\u0011)\tI\u0006\u0006B\tB\u0003%\u00111\b\u0005\u000b\u00037\"\"Q3A\u0005\u0002\u0005u\u0003BCA4)\tE\t\u0015!\u0003\u0002`!1q\r\u0006C\u0001\u0003SBq!! \u0015\t#\ty\bC\u0004\u0002\bR!\t%!#\t\u000f\u0005]F\u0003\"\u0011\u0002:!9\u0011\u0011\u0018\u000b\u0005B\u0005m\u0006bBA`)\u0011\u0005\u0013\u0011\u0019\u0005\b\u0003\u000b$B\u0011BAd\u0011\u001d\ti\r\u0006C\u0005\u0003\u001fDq!a6\u0015\t\u0003\nI\u000eC\u0004\u0002dR!\t%!:\t\u000f\u0005%H\u0003\"\u0011\u0002l\"9\u0011\u0011\u001e\u000b\u0005B\u0005=\bbBAz)\u0011\u0005\u0013Q\u001f\u0005\b\u0003s$B\u0011IA~\u0011\u001d\u0011\u0019\u0001\u0006C!\u0003\u007fBqA!\u0002\u0015\t\u0003\nI\u0003C\u0004\u0003\bQ!\tA!\u0003\t\u000f\t\u001dA\u0003\"\u0001\u0003\u0016!9!1\u0004\u000b\u0005B\tu\u0001b\u0002B\u0015)\u0011\u0005#1\u0006\u0005\n\u0005_!\u0012\u0011!C\u0001\u0005cA\u0011B!\u0012\u0015#\u0003%\tAa\u0012\t\u0013\tuC#%A\u0005\u0002\t}\u0003\"\u0003B2)E\u0005I\u0011\u0001B3\u0011%\u0011I\u0007FI\u0001\n\u0003\u0011)\u0007C\u0005\u0003lQ\t\n\u0011\"\u0001\u0003n!I!\u0011\u000f\u000b\u0012\u0002\u0013\u0005!1\u000f\u0005\n\u0005o\"\u0012\u0013!C\u0001\u0005sB\u0011B! \u0015#\u0003%\tA!\u001c\t\u0013\t}D#%A\u0005\u0002\t\u0005\u0005\"\u0003BC)\u0005\u0005I\u0011\tBD\u0011%\u0011)\nFA\u0001\n\u0003\tI\u0003C\u0005\u0003\u0018R\t\t\u0011\"\u0001\u0003\u001a\"I!Q\u0015\u000b\u0002\u0002\u0013\u0005#q\u0015\u0005\n\u0005k#\u0012\u0011!C\u0001\u0005oC\u0011Ba/\u0015\u0003\u0003%\tE!0\t\u0013\t\u0005G#!A\u0005B\t\r\u0007\"\u0003Bc)\u0005\u0005I\u0011\tBd\u0011%\u0011I\rFA\u0001\n\u0003\u0012Y-\u0001\fMS:,\u0017M\u001d+sCZ,'o]1m\u0005VLG\u000eZ3s\u0015\t\u0001\u0016+\u0001\u0003j[Bd'B\u0001*T\u0003\u0019\u0019HO]3b[*\tA+\u0001\u0003bW.\f\u0007C\u0001,\u0002\u001b\u0005y%A\u0006'j]\u0016\f'\u000f\u0016:bm\u0016\u00148/\u00197Ck&dG-\u001a:\u0014\u0007\u0005Iv\f\u0005\u0002[;6\t1LC\u0001]\u0003\u0015\u00198-\u00197b\u0013\tq6L\u0001\u0004B]f\u0014VM\u001a\t\u0003A\u0016l\u0011!\u0019\u0006\u0003E\u000e\f!![8\u000b\u0003\u0011\fAA[1wC&\u0011a-\u0019\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\tQ+A\tdC\u000eDW\rZ#naRLH*\u001b8fCJ,\u0012\u0001\u001c\t\u0003-R\u0019R\u0001F-ocR\u0004\"AV8\n\u0005A|%\u0001\u0005+sCZ,'o]1m\u0005VLG\u000eZ3s!\tQ&/\u0003\u0002t7\n9\u0001K]8ek\u000e$\bCA;~\u001d\t18P\u0004\u0002xu6\t\u0001P\u0003\u0002zQ\u00061AH]8pizJ\u0011\u0001X\u0005\u0003yn\u000bq\u0001]1dW\u0006<W-\u0003\u0002g}*\u0011ApW\u0001\u0007S:\u0004vN\u001d;\u0016\u0005\u0005\r\u0001CBA\u0003\u0003\u0017\ty!\u0004\u0002\u0002\b)\u0019\u0011\u0011B*\u0002\tU$\u0018\u000e\\\u0005\u0005\u0003\u001b\t9AA\u0005PaRLwN\u001c,bYB!\u0011\u0011CA\n\u001b\u0005\t\u0016bAA\u000b#\n1\u0011J\u001c)peR\fq!\u001b8Q_J$\b%A\u0004pkR\u0004vN\u001d;\u0016\u0005\u0005u\u0001CBA\u0003\u0003\u0017\ty\u0002\u0005\u0003\u0002\u0012\u0005\u0005\u0012bAA\u0012#\n9q*\u001e;Q_J$\u0018\u0001C8viB{'\u000f\u001e\u0011\u0002\u0011%twJ\u001a4tKR,\"!a\u000b\u0011\u0007i\u000bi#C\u0002\u00020m\u00131!\u00138u\u0003%Ign\u00144gg\u0016$\b%A\u0004j]Ncw\u000e^:\u0002\u0011%t7\u000b\\8ug\u0002\na\u0002\u001e:bm\u0016\u00148/\u00197T_\u001a\u000b'/\u0006\u0002\u0002<A\u0019a+!\u0010\n\u0007\u0005}rJA\u0005Ue\u00064XM]:bY\u0006yAO]1wKJ\u001c\u0018\r\\*p\r\u0006\u0014\b%\u0001\bqK:$\u0017N\\4Ck&dG-\u001a:\u0016\u0005\u0005\u001d\u0003#BA\u0003\u0003\u0017q\u0017a\u00049f]\u0012Lgn\u001a\"vS2$WM\u001d\u0011\u0002\u0015\u0005$HO]5ckR,7/\u0006\u0002\u0002PA!\u0011\u0011CA)\u0013\r\t\u0019&\u0015\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018aC1uiJL'-\u001e;fg\u0002\nQBY3g_J,')^5mI\u0016\u0014\u0018A\u00042fM>\u0014XMQ;jY\u0012,'\u000fI\u0001\nSNd\u0017M\u001c3UC\u001e,\"!a\u0018\u0011\r\u0005\u0015\u00111BA1!\r1\u00161M\u0005\u0004\u0003Kz%!C%tY\u0006tG\rV1h\u0003)I7\u000f\\1oIR\u000bw\r\t\u000b\u0014Y\u0006-\u0014QNA8\u0003c\n\u0019(!\u001e\u0002x\u0005e\u00141\u0010\u0005\u0007\u007f\u001e\u0002\r!a\u0001\t\u000f\u0005eq\u00051\u0001\u0002\u001e!9\u0011qE\u0014A\u0002\u0005-\u0002bBA\u001aO\u0001\u0007\u00111\u0006\u0005\b\u0003o9\u0003\u0019AA\u001e\u0011\u001d\t\u0019e\na\u0001\u0003\u000fBq!a\u0013(\u0001\u0004\ty\u0005C\u0005\u0002X\u001d\u0002\n\u00111\u0001\u0002<!I\u00111L\u0014\u0011\u0002\u0003\u0007\u0011qL\u0001\bSN,U\u000e\u001d;z+\t\t\t\tE\u0002[\u0003\u0007K1!!\"\\\u0005\u001d\u0011un\u001c7fC:\f1!\u00193e)\u001dq\u00171RAH\u00033Ca!!$*\u0001\u0004q\u0017!C:vE6|G-\u001e7f\u0011\u001d\t\t*\u000ba\u0001\u0003'\u000bQa\u001d5ba\u0016\u0004B!!\u0005\u0002\u0016&\u0019\u0011qS)\u0003\u000bMC\u0017\r]3\t\u000f\u0005m\u0015\u00061\u0001\u0002\u001e\u0006Q1m\\7cS:,W*\u0019;\u0011\t\u0005}\u0015\u0011\u0017\b\u0005\u0003C\u000biK\u0004\u0003\u0002$\u0006-f\u0002BAS\u0003Ss1a^AT\u0013\u0005!\u0016B\u0001*T\u0013\t\u0001\u0016+C\u0002\u00020>\u000b\u0001\u0003\u0016:bm\u0016\u00148/\u00197Ck&dG-\u001a:\n\t\u0005M\u0016Q\u0017\u0002\r\u0003:Lh)\u001e8di&|gN\r\u0006\u0004\u0003_{\u0015!\u0003;sCZ,'o]1m\u0003UIg\u000e^3s]\u0006d7+\u001a;BiR\u0014\u0018NY;uKN$2\u0001\\A_\u0011\u001d\tYe\u000ba\u0001\u0003\u001f\nQb]3u\u0003R$(/\u001b2vi\u0016\u001cHc\u00017\u0002D\"9\u00111\n\u0017A\u0002\u0005=\u0013\u0001G1qa2L\u0018j\u001d7b]\u0012\fe\u000eZ!uiJL'-\u001e;fgR!\u00111HAe\u0011\u001d\tY-\fa\u0001\u0003w\t\u0011\u0001^\u0001\u0010e\u0016<\u0018N]3MCN$x*\u001e;U_R1\u00111HAi\u0003'Dq!a./\u0001\u0004\tY\u0004C\u0004\u0002V:\u0002\r!a\u000b\u0002\u001dI,G.\u0019;jm\u0016|eMZ:fi\u0006!q/\u001b:f)\u0015q\u00171\\Ap\u0011\u001d\tin\fa\u0001\u0003?\t1a\\;u\u0011\u001d\t\to\fa\u0001\u0003\u001f\t!!\u001b8\u0002\u001d=4gm]3u\u001f\u001alu\u000eZ;mKR!\u00111FAt\u0011\u001d\ti\u000e\ra\u0001\u0003?\t\u0011\"[:V]^L'/\u001a3\u0015\t\u0005\u0005\u0015Q\u001e\u0005\b\u0003;\f\u0004\u0019AA\u0010)\u0011\t\t)!=\t\u000f\u0005\u0005(\u00071\u0001\u0002\u0010\u0005AqN\u001a4tKR|e\r\u0006\u0003\u0002,\u0005]\bbBAqg\u0001\u0007\u0011qB\u0001\u0007CN\u001c\u0018n\u001a8\u0015\u000b9\fi0a@\t\u000f\u0005uG\u00071\u0001\u0002 !9!\u0011\u0001\u001bA\u0002\u0005-\u0012\u0001\u0004:fY\u0006$\u0018N^3TY>$\u0018aE5t)J\fg/\u001a:tC2\u001cu.\u001c9mKR,\u0017aC;oo&\u0014X\rZ(viN\fa!\u00199qK:$Gc\u00027\u0003\f\t=!\u0011\u0003\u0005\u0007\u0005\u001b9\u0004\u0019\u00018\u0002\u0011Q|\u0017\t\u001d9f]\u0012Dq!!%8\u0001\u0004\t\u0019\nC\u0004\u0003\u0014]\u0002\r!!(\u0002\u00155\fGoQ8na>\u001cX\rF\u0003m\u0005/\u0011I\u0002\u0003\u0004\u0003\u000ea\u0002\r\u0001\u001c\u0005\b\u0005'A\u0004\u0019AAO\u00031!(/\u00198tM>\u0014X.T1u)\ra'q\u0004\u0005\b\u0005CI\u0004\u0019\u0001B\u0012\u0003\u00051\u0007\u0003BAP\u0005KIAAa\n\u00026\na\u0011I\\=Gk:\u001cG/[8oc\u0005QQ.Y6f\u0013Nd\u0017M\u001c3\u0015\u00071\u0014i\u0003C\u0004\u0002\\i\u0002\r!!\u0019\u0002\t\r|\u0007/\u001f\u000b\u0014Y\nM\"Q\u0007B\u001c\u0005s\u0011YD!\u0010\u0003@\t\u0005#1\t\u0005\t\u007fn\u0002\n\u00111\u0001\u0002\u0004!I\u0011\u0011D\u001e\u0011\u0002\u0003\u0007\u0011Q\u0004\u0005\n\u0003OY\u0004\u0013!a\u0001\u0003WA\u0011\"a\r<!\u0003\u0005\r!a\u000b\t\u0013\u0005]2\b%AA\u0002\u0005m\u0002\"CA\"wA\u0005\t\u0019AA$\u0011%\tYe\u000fI\u0001\u0002\u0004\ty\u0005C\u0005\u0002Xm\u0002\n\u00111\u0001\u0002<!I\u00111L\u001e\u0011\u0002\u0003\u0007\u0011qL\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\u0011IE\u000b\u0003\u0002\u0004\t-3F\u0001B'!\u0011\u0011yE!\u0017\u000e\u0005\tE#\u0002\u0002B*\u0005+\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\t]3,\u0001\u0006b]:|G/\u0019;j_:LAAa\u0017\u0003R\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011!\u0011\r\u0016\u0005\u0003;\u0011Y%\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\t\u001d$\u0006BA\u0016\u0005\u0017\nabY8qs\u0012\"WMZ1vYR$C'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016\u0005\t=$\u0006BA\u001e\u0005\u0017\nabY8qs\u0012\"WMZ1vYR$c'\u0006\u0002\u0003v)\"\u0011q\tB&\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*\"Aa\u001f+\t\u0005=#1J\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIe*\"Aa!+\t\u0005}#1J\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\t%\u0005\u0003\u0002BF\u0005#k!A!$\u000b\u0007\t=5-\u0001\u0003mC:<\u0017\u0002\u0002BJ\u0005\u001b\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u00057\u0013\t\u000bE\u0002[\u0005;K1Aa(\\\u0005\r\te.\u001f\u0005\n\u0005G;\u0015\u0011!a\u0001\u0003W\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001BU!\u0019\u0011YK!-\u0003\u001c6\u0011!Q\u0016\u0006\u0004\u0005_[\u0016AC2pY2,7\r^5p]&!!1\u0017BW\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\u0005%\u0011\u0018\u0005\n\u0005GK\u0015\u0011!a\u0001\u00057\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!!\u0011\u0012B`\u0011%\u0011\u0019KSA\u0001\u0002\u0004\tY#\u0001\u0005iCND7i\u001c3f)\t\tY#\u0001\u0005u_N#(/\u001b8h)\t\u0011I)\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u0003\u0013i\rC\u0005\u0003$6\u000b\t\u00111\u0001\u0003\u001c\"\u001aAC!5\u0011\t\tM'q[\u0007\u0003\u0005+T1Aa\u0016T\u0013\u0011\u0011IN!6\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0013G\u0006\u001c\u0007.\u001a3F[B$\u0018\u0010T5oK\u0006\u0014\b%\u0001\u0007xSJ,')Y2lo\u0006\u0014H\rE\u0003[\u0005C\fY#C\u0002\u0003dn\u0013Q!\u0011:sCf\faA\\8XSJ,\u0017!B3naRLHc\u00017\u0003l\"I\u00111J\u0004\u0011\u0002\u0003\u0007\u0011qJ\u0001\u0010K6\u0004H/\u001f\u0013eK\u001a\fW\u000f\u001c;%c\u0005QaM]8n\u001b>$W\u000f\\3\u0015\u000b1\u0014\u0019p!\u0002\t\u000f\tU\u0018\u00021\u0001\u0003x\u00061Qn\u001c3vY\u0016\u0004\u0002B!?\u0003\u0000\u0006M%1\u0014\b\u0005\u0003C\u0013Y0C\u0002\u0003~>\u000bAb\u0015;sK\u0006lG*Y=pkRLAa!\u0001\u0004\u0004\ta\u0011\t^8nS\u000elu\u000eZ;mK*\u0019!Q`(\t\u000f\u0005-\u0013\u00021\u0001\u0002P\u0005i\u0011\r\u001a3NCR\u001cu.\u001c9pg\u0016$b!a\u000f\u0004\f\r5\u0001bBAf\u0015\u0001\u0007\u00111\b\u0005\b\u0005'Q\u0001\u0019AAO\u0003-1'o\\7Ck&dG-\u001a:\u0015\u000f1\u001c\u0019ba\u0006\u0004\u001a!11QC\u0006A\u00029\f\u0001\u0003\u001e:bm\u0016\u00148/\u00197Ck&dG-\u001a:\t\u000f\u0005E5\u00021\u0001\u0002\u0014\"I11D\u0006\u0011\u0002\u0003\u0007\u0011QT\u0001\bG>l'-\u001b8f\u0003U1'o\\7Ck&dG-\u001a:%I\u00164\u0017-\u001e7uIM*\"a!\t+\t\u0005u%1J\u0001\u0006CB\u0004H.\u001f\u000b\u0014Y\u000e\u001d2\u0011FB\u0016\u0007[\u0019yc!\r\u00044\rU2q\u0007\u0005\u0007\u007f6\u0001\r!a\u0001\t\u000f\u0005eQ\u00021\u0001\u0002\u001e!9\u0011qE\u0007A\u0002\u0005-\u0002bBA\u001a\u001b\u0001\u0007\u00111\u0006\u0005\b\u0003oi\u0001\u0019AA\u001e\u0011\u001d\t\u0019%\u0004a\u0001\u0003\u000fBq!a\u0013\u000e\u0001\u0004\ty\u0005C\u0005\u0002X5\u0001\n\u00111\u0001\u0002<!I\u00111L\u0007\u0011\u0002\u0003\u0007\u0011qL\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%q\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$\u0013(A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\r\u00053Q\n\t\u00065\u000e\r3qI\u0005\u0004\u0007\u000bZ&AB(qi&|g\u000eE\u000b[\u0007\u0013\n\u0019!!\b\u0002,\u0005-\u00121HA$\u0003\u001f\nY$a\u0018\n\u0007\r-3L\u0001\u0004UkBdW-\u000f\u0005\t\u0007\u001f\u0002\u0012\u0011!a\u0001Y\u0006\u0019\u0001\u0010\n\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00139\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%s\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u00111\u0011\f\t\u0005\u0005\u0017\u001bY&\u0003\u0003\u0004^\t5%AB(cU\u0016\u001cG\u000fK\u0002\u0002\u0005#D3\u0001\u0001Bi\u0001")
@InternalApi
public final class LinearTraversalBuilder
implements TraversalBuilder,
Product,
Serializable {
    private final InPort inPort;
    private final OutPort outPort;
    private final int inOffset;
    private final int inSlots;
    private final Traversal traversalSoFar;
    private final TraversalBuilder pendingBuilder;
    private final Attributes attributes;
    private final Traversal beforeBuilder;
    private final IslandTag islandTag;

    public static IslandTag $lessinit$greater$default$9() {
        return LinearTraversalBuilder$.MODULE$.$lessinit$greater$default$9();
    }

    public static Traversal $lessinit$greater$default$8() {
        return LinearTraversalBuilder$.MODULE$.$lessinit$greater$default$8();
    }

    public static Option<Tuple9<OptionVal<InPort>, OptionVal<OutPort>, Object, Object, Traversal, OptionVal<TraversalBuilder>, Attributes, Traversal, OptionVal<IslandTag>>> unapply(LinearTraversalBuilder x$0) {
        return LinearTraversalBuilder$.MODULE$.unapply(x$0);
    }

    public static IslandTag apply$default$9() {
        return LinearTraversalBuilder$.MODULE$.apply$default$9();
    }

    public static Traversal apply$default$8() {
        return LinearTraversalBuilder$.MODULE$.apply$default$8();
    }

    public static LinearTraversalBuilder apply(InPort inPort, OutPort outPort, int inOffset, int inSlots, Traversal traversalSoFar, TraversalBuilder pendingBuilder, Attributes attributes, Traversal beforeBuilder, IslandTag islandTag) {
        return LinearTraversalBuilder$.MODULE$.apply(inPort, outPort, inOffset, inSlots, traversalSoFar, pendingBuilder, attributes, beforeBuilder, islandTag);
    }

    public static Function2<Nothing$, Nothing$, Object> fromBuilder$default$3() {
        return LinearTraversalBuilder$.MODULE$.fromBuilder$default$3();
    }

    public static LinearTraversalBuilder fromBuilder(TraversalBuilder traversalBuilder, Shape shape, Function2<Nothing$, Nothing$, Object> combine) {
        return LinearTraversalBuilder$.MODULE$.fromBuilder(traversalBuilder, shape, combine);
    }

    public static Traversal addMatCompose(Traversal t2, Function2<Nothing$, Nothing$, Object> matCompose) {
        return LinearTraversalBuilder$.MODULE$.addMatCompose(t2, matCompose);
    }

    public static LinearTraversalBuilder fromModule(StreamLayout.AtomicModule<Shape, Object> module, Attributes attributes) {
        return LinearTraversalBuilder$.MODULE$.fromModule(module, attributes);
    }

    public static Attributes empty$default$1() {
        return LinearTraversalBuilder$.MODULE$.empty$default$1();
    }

    public static LinearTraversalBuilder empty(Attributes attributes) {
        return LinearTraversalBuilder$.MODULE$.empty(attributes);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public InPort inPort() {
        return this.inPort;
    }

    public OutPort outPort() {
        return this.outPort;
    }

    public int inOffset() {
        return this.inOffset;
    }

    @Override
    public int inSlots() {
        return this.inSlots;
    }

    public Traversal traversalSoFar() {
        return this.traversalSoFar;
    }

    public TraversalBuilder pendingBuilder() {
        return this.pendingBuilder;
    }

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    public Traversal beforeBuilder() {
        return this.beforeBuilder;
    }

    public IslandTag islandTag() {
        return this.islandTag;
    }

    public boolean isEmpty() {
        return this.inSlots() == 0 && OptionVal$.MODULE$.isEmpty$extension(this.outPort());
    }

    @Override
    public TraversalBuilder add(TraversalBuilder submodule, Shape shape, Function2<Nothing$, Nothing$, Object> combineMat) {
        throw new UnsupportedOperationException("LinearTraversal does not support free-form addition. Add it into acomposite builder instead and add the second module to that.");
    }

    @Override
    public Traversal traversal() {
        if (OptionVal$.MODULE$.isDefined$extension(this.outPort())) {
            throw new IllegalStateException("Traversal cannot be acquired until all output ports have been wired");
        }
        return this.applyIslandAndAttributes(this.traversalSoFar());
    }

    @Override
    public LinearTraversalBuilder internalSetAttributes(Attributes attributes) {
        Attributes x$1 = attributes;
        InPort x$2 = this.copy$default$1();
        OutPort x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        int x$5 = this.copy$default$4();
        Traversal x$6 = this.copy$default$5();
        TraversalBuilder x$7 = this.copy$default$6();
        Traversal x$8 = this.copy$default$8();
        IslandTag x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8, x$9);
    }

    @Override
    public LinearTraversalBuilder setAttributes(Attributes attributes) {
        if (attributes.isAsync()) {
            return this.makeIsland(GraphStageTag$.MODULE$).internalSetAttributes(attributes);
        }
        return this.internalSetAttributes(attributes);
    }

    private Traversal applyIslandAndAttributes(Traversal t2) {
        Traversal withIslandTag;
        Traversal traversal;
        IslandTag islandTag = this.islandTag();
        IslandTag islandTag2 = OptionVal$Some$.MODULE$.unapply(islandTag);
        if (!OptionVal$.MODULE$.isEmpty$extension(islandTag2)) {
            IslandTag tag = OptionVal$.MODULE$.get$extension(islandTag2);
            traversal = new EnterIsland(tag).concat(t2).concat(ExitIsland$.MODULE$);
        } else {
            traversal = withIslandTag = t2;
        }
        if (this.attributes() == Attributes$.MODULE$.none()) {
            return withIslandTag;
        }
        return new PushAttributes(this.attributes()).concat(withIslandTag).concat(PopAttributes$.MODULE$);
    }

    private Traversal rewireLastOutTo(Traversal traversal, int relativeOffset) {
        if (relativeOffset == -1) {
            return traversal;
        }
        return traversal.rewireFirstTo(relativeOffset);
    }

    @Override
    public TraversalBuilder wire(OutPort out, InPort in) {
        if (OptionVal$.MODULE$.contains$extension(this.outPort(), out) && OptionVal$.MODULE$.contains$extension(this.inPort(), in)) {
            TraversalBuilder traversalBuilder = this.pendingBuilder();
            TraversalBuilder traversalBuilder2 = OptionVal$Some$.MODULE$.unapply(traversalBuilder);
            if (!OptionVal$.MODULE$.isEmpty$extension(traversalBuilder2)) {
                TraversalBuilder composite = OptionVal$.MODULE$.get$extension(traversalBuilder2);
                Null$ x$1 = OptionVal$.MODULE$.None();
                Null$ x$2 = OptionVal$.MODULE$.None();
                Traversal x$3 = this.applyIslandAndAttributes(this.beforeBuilder().concat(composite.assign(out, this.inOffset() - composite.offsetOfModule(out)).traversal()).concat(this.traversalSoFar()));
                Null$ x$4 = OptionVal$.MODULE$.None();
                EmptyTraversal$ x$5 = EmptyTraversal$.MODULE$;
                int x$6 = this.copy$default$3();
                int x$7 = this.copy$default$4();
                Attributes x$8 = this.copy$default$7();
                IslandTag x$9 = this.copy$default$9();
                return this.copy(null, null, x$6, x$7, x$3, null, x$8, x$5, x$9);
            }
            Null$ x$10 = OptionVal$.MODULE$.None();
            Null$ x$11 = OptionVal$.MODULE$.None();
            Traversal x$12 = this.rewireLastOutTo(this.traversalSoFar(), this.inOffset());
            int x$13 = this.copy$default$3();
            int x$14 = this.copy$default$4();
            TraversalBuilder x$15 = this.copy$default$6();
            Attributes x$16 = this.copy$default$7();
            Traversal x$17 = this.copy$default$8();
            IslandTag x$18 = this.copy$default$9();
            return this.copy(null, null, x$13, x$14, x$12, x$15, x$16, x$17, x$18);
        }
        throw new IllegalArgumentException(new StringBuilder(51).append("The ports ").append(in).append(" and ").append(out).append(" cannot be accessed in this builder.").toString());
    }

    @Override
    public int offsetOfModule(OutPort out) {
        if (OptionVal$.MODULE$.contains$extension(this.outPort(), out)) {
            TraversalBuilder traversalBuilder = this.pendingBuilder();
            TraversalBuilder traversalBuilder2 = OptionVal$Some$.MODULE$.unapply(traversalBuilder);
            if (!OptionVal$.MODULE$.isEmpty$extension(traversalBuilder2)) {
                TraversalBuilder composite = OptionVal$.MODULE$.get$extension(traversalBuilder2);
                return composite.offsetOfModule(out);
            }
            return 0;
        }
        throw new IllegalArgumentException(new StringBuilder(40).append("Port ").append(out).append(" cannot be accessed in this builder").toString());
    }

    @Override
    public boolean isUnwired(OutPort out) {
        return OptionVal$.MODULE$.contains$extension(this.outPort(), out);
    }

    @Override
    public boolean isUnwired(InPort in) {
        return OptionVal$.MODULE$.contains$extension(this.inPort(), in);
    }

    @Override
    public int offsetOf(InPort in) {
        if (OptionVal$.MODULE$.contains$extension(this.inPort(), in)) {
            return this.inOffset();
        }
        throw new IllegalArgumentException(new StringBuilder(40).append("Port ").append(in).append(" cannot be accessed in this builder").toString());
    }

    @Override
    public TraversalBuilder assign(OutPort out, int relativeSlot) {
        if (OptionVal$.MODULE$.contains$extension(this.outPort(), out)) {
            TraversalBuilder traversalBuilder = this.pendingBuilder();
            TraversalBuilder traversalBuilder2 = OptionVal$Some$.MODULE$.unapply(traversalBuilder);
            if (!OptionVal$.MODULE$.isEmpty$extension(traversalBuilder2)) {
                TraversalBuilder composite = OptionVal$.MODULE$.get$extension(traversalBuilder2);
                Null$ x$1 = OptionVal$.MODULE$.None();
                Traversal x$2 = this.applyIslandAndAttributes(this.beforeBuilder().concat(composite.assign(out, relativeSlot).traversal().concat(this.traversalSoFar())));
                Null$ x$3 = OptionVal$.MODULE$.None();
                EmptyTraversal$ x$4 = EmptyTraversal$.MODULE$;
                InPort x$5 = this.copy$default$1();
                int x$6 = this.copy$default$3();
                int x$7 = this.copy$default$4();
                Attributes x$8 = this.copy$default$7();
                IslandTag x$9 = this.copy$default$9();
                return this.copy(x$5, null, x$6, x$7, x$2, null, x$8, x$4, x$9);
            }
            Null$ x$10 = OptionVal$.MODULE$.None();
            Traversal x$11 = this.rewireLastOutTo(this.traversalSoFar(), relativeSlot);
            InPort x$12 = this.copy$default$1();
            int x$13 = this.copy$default$3();
            int x$14 = this.copy$default$4();
            TraversalBuilder x$15 = this.copy$default$6();
            Attributes x$16 = this.copy$default$7();
            Traversal x$17 = this.copy$default$8();
            IslandTag x$18 = this.copy$default$9();
            return this.copy(x$12, null, x$13, x$14, x$11, x$15, x$16, x$17, x$18);
        }
        throw new IllegalArgumentException(new StringBuilder(40).append("Port ").append(out).append(" cannot be assigned in this builder").toString());
    }

    @Override
    public boolean isTraversalComplete() {
        return OptionVal$.MODULE$.isEmpty$extension(this.outPort());
    }

    @Override
    public int unwiredOuts() {
        if (OptionVal$.MODULE$.isDefined$extension(this.outPort())) {
            return 1;
        }
        return 0;
    }

    public LinearTraversalBuilder append(TraversalBuilder toAppend, Shape shape, Function2<Nothing$, Nothing$, Object> matCompose) {
        return this.append(LinearTraversalBuilder$.MODULE$.fromBuilder(toAppend, shape, Keep$.MODULE$.right()), matCompose);
    }

    public LinearTraversalBuilder append(LinearTraversalBuilder toAppend, Function2<Nothing$, Nothing$, Object> matCompose) {
        if (toAppend.isEmpty()) {
            Traversal x$1 = PushNotUsed$.MODULE$.concat(LinearTraversalBuilder$.MODULE$.addMatCompose(this.traversalSoFar(), matCompose));
            InPort x$2 = this.copy$default$1();
            OutPort x$3 = this.copy$default$2();
            int x$4 = this.copy$default$3();
            int x$5 = this.copy$default$4();
            TraversalBuilder x$6 = this.copy$default$6();
            Attributes x$7 = this.copy$default$7();
            Traversal x$8 = this.copy$default$8();
            IslandTag x$9 = this.copy$default$9();
            return this.copy(x$2, x$3, x$4, x$5, x$1, x$6, x$7, x$8, x$9);
        }
        if (this.isEmpty()) {
            Traversal x$10 = toAppend.traversalSoFar().concat(LinearTraversalBuilder$.MODULE$.addMatCompose(this.traversal(), matCompose));
            InPort x$11 = toAppend.copy$default$1();
            OutPort x$12 = toAppend.copy$default$2();
            int x$13 = toAppend.copy$default$3();
            int x$14 = toAppend.copy$default$4();
            TraversalBuilder x$15 = toAppend.copy$default$6();
            Attributes x$16 = toAppend.copy$default$7();
            Traversal x$17 = toAppend.copy$default$8();
            IslandTag x$18 = toAppend.copy$default$9();
            return toAppend.copy(x$11, x$12, x$13, x$14, x$10, x$15, x$16, x$17, x$18);
        }
        if (OptionVal$.MODULE$.isDefined$extension(this.outPort())) {
            Traversal traversal;
            if (OptionVal$.MODULE$.isEmpty$extension(toAppend.inPort())) {
                throw new IllegalArgumentException("Appended linear module must have an unwired input port because there is a dangling output.");
            }
            TraversalBuilder traversalBuilder = this.pendingBuilder();
            TraversalBuilder traversalBuilder2 = OptionVal$Some$.MODULE$.unapply(traversalBuilder);
            if (!OptionVal$.MODULE$.isEmpty$extension(traversalBuilder2)) {
                TraversalBuilder composite = OptionVal$.MODULE$.get$extension(traversalBuilder2);
                OutPort out = OptionVal$.MODULE$.get$extension(this.outPort());
                Traversal compositeTraversal = composite.assign(out, -composite.offsetOfModule(out) - toAppend.inSlots() + toAppend.inOffset()).traversal();
                traversal = this.beforeBuilder().concat(compositeTraversal).concat(this.traversalSoFar());
            } else {
                traversal = toAppend.inOffset() == toAppend.inSlots() - 1 ? this.traversalSoFar() : this.rewireLastOutTo(this.traversalSoFar(), toAppend.inOffset() - toAppend.inSlots());
            }
            Traversal assembledTraversalForThis = traversal;
            Traversal finalTraversalForThis = LinearTraversalBuilder$.MODULE$.addMatCompose(this.applyIslandAndAttributes(assembledTraversalForThis), matCompose);
            TraversalBuilder traversalBuilder3 = toAppend.pendingBuilder();
            OptionVal$.MODULE$.None();
            TraversalBuilder traversalBuilder4 = traversalBuilder3;
            if (!(null != null ? !((Object)null).equals(traversalBuilder4) : traversalBuilder4 != null)) {
                InPort x$19 = this.inPort();
                OutPort x$20 = toAppend.outPort();
                int x$21 = this.inSlots() + toAppend.inSlots();
                int x$22 = this.inOffset() + toAppend.inSlots();
                Traversal x$23 = toAppend.applyIslandAndAttributes(toAppend.traversalSoFar()).concat(finalTraversalForThis);
                Null$ x$24 = OptionVal$.MODULE$.None();
                Attributes x$25 = Attributes$.MODULE$.none();
                EmptyTraversal$ x$26 = EmptyTraversal$.MODULE$;
                Null$ x$27 = OptionVal$.MODULE$.None();
                return new LinearTraversalBuilder(x$19, x$20, x$22, x$21, x$23, null, x$25, x$26, null);
            }
            Traversal newTraversalSoFar = finalTraversalForThis;
            Traversal newBeforeTraversal = toAppend.beforeBuilder();
            IslandTag islandTag = toAppend.islandTag();
            IslandTag islandTag2 = OptionVal$Some$.MODULE$.unapply(islandTag);
            if (!OptionVal$.MODULE$.isEmpty$extension(islandTag2)) {
                IslandTag tag = OptionVal$.MODULE$.get$extension(islandTag2);
                newBeforeTraversal = new EnterIsland(tag).concat(newBeforeTraversal);
                newTraversalSoFar = ExitIsland$.MODULE$.concat(newTraversalSoFar);
            }
            if (toAppend.attributes() != Attributes$.MODULE$.none()) {
                newBeforeTraversal = new PushAttributes(toAppend.attributes()).concat(newBeforeTraversal);
                newTraversalSoFar = PopAttributes$.MODULE$.concat(newTraversalSoFar);
            }
            newTraversalSoFar = toAppend.traversalSoFar().concat(newTraversalSoFar);
            InPort x$28 = this.inPort();
            OutPort x$29 = toAppend.outPort();
            int x$30 = this.inSlots() + toAppend.inSlots();
            int x$31 = this.inOffset() + toAppend.inSlots();
            Traversal x$32 = newTraversalSoFar;
            TraversalBuilder x$33 = toAppend.pendingBuilder();
            Attributes x$34 = Attributes$.MODULE$.none();
            Traversal x$35 = newBeforeTraversal;
            Null$ x$36 = OptionVal$.MODULE$.None();
            return new LinearTraversalBuilder(x$28, x$29, x$31, x$30, x$32, x$33, x$34, x$35, null);
        }
        throw new Exception("should this happen?");
    }

    @Override
    public LinearTraversalBuilder transformMat(Function1<Nothing$, Object> f) {
        Traversal x$1 = this.traversalSoFar().concat(new Transform(f));
        InPort x$2 = this.copy$default$1();
        OutPort x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        int x$5 = this.copy$default$4();
        TraversalBuilder x$6 = this.copy$default$6();
        Attributes x$7 = this.copy$default$7();
        Traversal x$8 = this.copy$default$8();
        IslandTag x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6, x$7, x$8, x$9);
    }

    @Override
    public LinearTraversalBuilder makeIsland(IslandTag islandTag) {
        IslandTag islandTag2 = this.islandTag();
        IslandTag islandTag3 = OptionVal$Some$.MODULE$.unapply(islandTag2);
        if (!OptionVal$.MODULE$.isEmpty$extension(islandTag3)) {
            return this;
        }
        IslandTag x$1 = OptionVal$Some$.MODULE$.apply(islandTag);
        InPort x$2 = this.copy$default$1();
        OutPort x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        int x$5 = this.copy$default$4();
        Traversal x$6 = this.copy$default$5();
        TraversalBuilder x$7 = this.copy$default$6();
        Attributes x$8 = this.copy$default$7();
        Traversal x$9 = this.copy$default$8();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$1);
    }

    public LinearTraversalBuilder copy(InPort inPort, OutPort outPort, int inOffset, int inSlots, Traversal traversalSoFar, TraversalBuilder pendingBuilder, Attributes attributes, Traversal beforeBuilder, IslandTag islandTag) {
        return new LinearTraversalBuilder(inPort, outPort, inOffset, inSlots, traversalSoFar, pendingBuilder, attributes, beforeBuilder, islandTag);
    }

    public InPort copy$default$1() {
        return this.inPort();
    }

    public OutPort copy$default$2() {
        return this.outPort();
    }

    public int copy$default$3() {
        return this.inOffset();
    }

    public int copy$default$4() {
        return this.inSlots();
    }

    public Traversal copy$default$5() {
        return this.traversalSoFar();
    }

    public TraversalBuilder copy$default$6() {
        return this.pendingBuilder();
    }

    public Attributes copy$default$7() {
        return this.attributes();
    }

    public Traversal copy$default$8() {
        return this.beforeBuilder();
    }

    public IslandTag copy$default$9() {
        return this.islandTag();
    }

    @Override
    public String productPrefix() {
        return "LinearTraversalBuilder";
    }

    @Override
    public int productArity() {
        return 9;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return new OptionVal<InPort>(this.inPort());
            }
            case 1: {
                return new OptionVal<OutPort>(this.outPort());
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.inOffset());
            }
            case 3: {
                return BoxesRunTime.boxToInteger(this.inSlots());
            }
            case 4: {
                return this.traversalSoFar();
            }
            case 5: {
                return new OptionVal<TraversalBuilder>(this.pendingBuilder());
            }
            case 6: {
                return this.attributes();
            }
            case 7: {
                return this.beforeBuilder();
            }
            case 8: {
                return new OptionVal<IslandTag>(this.islandTag());
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof LinearTraversalBuilder;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "inPort";
            }
            case 1: {
                return "outPort";
            }
            case 2: {
                return "inOffset";
            }
            case 3: {
                return "inSlots";
            }
            case 4: {
                return "traversalSoFar";
            }
            case 5: {
                return "pendingBuilder";
            }
            case 6: {
                return "attributes";
            }
            case 7: {
                return "beforeBuilder";
            }
            case 8: {
                return "islandTag";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(new OptionVal<InPort>(this.inPort())));
        n = Statics.mix(n, Statics.anyHash(new OptionVal<OutPort>(this.outPort())));
        n = Statics.mix(n, this.inOffset());
        n = Statics.mix(n, this.inSlots());
        n = Statics.mix(n, Statics.anyHash(this.traversalSoFar()));
        n = Statics.mix(n, Statics.anyHash(new OptionVal<TraversalBuilder>(this.pendingBuilder())));
        n = Statics.mix(n, Statics.anyHash(this.attributes()));
        n = Statics.mix(n, Statics.anyHash(this.beforeBuilder()));
        n = Statics.mix(n, Statics.anyHash(new OptionVal<IslandTag>(this.islandTag())));
        return Statics.finalizeHash(n, 9);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof LinearTraversalBuilder)) return false;
        boolean bl = true;
        if (!bl) return false;
        LinearTraversalBuilder linearTraversalBuilder = (LinearTraversalBuilder)x$1;
        if (this.inOffset() != linearTraversalBuilder.inOffset()) return false;
        if (this.inSlots() != linearTraversalBuilder.inSlots()) return false;
        InPort inPort = this.inPort();
        InPort inPort2 = linearTraversalBuilder.inPort();
        if (inPort == null) {
            if (inPort2 != null) {
                return false;
            }
        } else if (!((Object)inPort).equals(inPort2)) return false;
        OutPort outPort = this.outPort();
        OutPort outPort2 = linearTraversalBuilder.outPort();
        if (outPort == null) {
            if (outPort2 != null) {
                return false;
            }
        } else if (!((Object)outPort).equals(outPort2)) return false;
        Traversal traversal = this.traversalSoFar();
        Traversal traversal2 = linearTraversalBuilder.traversalSoFar();
        if (traversal == null) {
            if (traversal2 != null) {
                return false;
            }
        } else if (!traversal.equals(traversal2)) return false;
        TraversalBuilder traversalBuilder = this.pendingBuilder();
        TraversalBuilder traversalBuilder2 = linearTraversalBuilder.pendingBuilder();
        if (traversalBuilder == null) {
            if (traversalBuilder2 != null) {
                return false;
            }
        } else if (!traversalBuilder.equals(traversalBuilder2)) return false;
        Attributes attributes = this.attributes();
        Attributes attributes2 = linearTraversalBuilder.attributes();
        if (attributes == null) {
            if (attributes2 != null) {
                return false;
            }
        } else if (!((Object)attributes).equals(attributes2)) return false;
        Traversal traversal3 = this.beforeBuilder();
        Traversal traversal4 = linearTraversalBuilder.beforeBuilder();
        if (traversal3 == null) {
            if (traversal4 != null) {
                return false;
            }
        } else if (!traversal3.equals(traversal4)) return false;
        IslandTag islandTag = this.islandTag();
        IslandTag islandTag2 = linearTraversalBuilder.islandTag();
        if (islandTag == null) {
            if (islandTag2 == null) return true;
            return false;
        } else {
            if (!islandTag.equals(islandTag2)) return false;
            return true;
        }
    }

    public LinearTraversalBuilder(InPort inPort, OutPort outPort, int inOffset, int inSlots, Traversal traversalSoFar, TraversalBuilder pendingBuilder, Attributes attributes, Traversal beforeBuilder, IslandTag islandTag) {
        this.inPort = inPort;
        this.outPort = outPort;
        this.inOffset = inOffset;
        this.inSlots = inSlots;
        this.traversalSoFar = traversalSoFar;
        this.pendingBuilder = pendingBuilder;
        this.attributes = attributes;
        this.beforeBuilder = beforeBuilder;
        this.islandTag = islandTag;
        TraversalBuilder.$init$(this);
        Product.$init$(this);
    }
}

