/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import scala.DummyImplicit;
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
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializationProxy;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.AnyRefMap$;
import scala.collection.mutable.AnyRefMap$ToBuildFrom$;
import scala.collection.mutable.AnyRefMap$ToFactory$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Nothing$;
import scala.runtime.Statics;
import scala.util.Either;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\u0019ec!B>}\u0001\u0005\u001d\u0001BCA5\u0001\t\u0005\t\u0015!\u0003\u0002l!Q\u0011\u0011\u000f\u0001\u0003\u0002\u0003\u0006I!a\u001d\t\u0015\u0005e\u0004A!A!\u0002\u0013\tY\b\u0003\u0005\u0002\u0002\u0002!\tA`AB\u0011\u001d\t\t\t\u0001C\u0001\u0003\u0017Cq!!!\u0001\t\u0003\ti\tC\u0004\u0002\u0002\u0002!\t!!%\t\u000f\u0005\u0005\u0005\u0001\"\u0001\u0002\u0016\"q\u00111\u0014\u0001\u0005\u0002\u0003\u0015\t\u0011!Q!\n\u0005M\u0004\u0002CAO\u0001\u0001\u0006K!a\u001d\t\u0011\u0005}\u0005\u0001)Q\u0005\u0003gBa\"!)\u0001\t\u0003\u0005)\u0011!A!B\u0013\t\u0019\u000b\u0003\b\u0002*\u0002!\t\u0011!B\u0001\u0002\u0003\u0006K!a+\t\u0011\u00055\u0006\u0001)Q\u0005\u0003WC\u0001\"a,\u0001A\u0013%\u0011\u0011\u0017\u0005\t\u0003{\u0003A\u0011\u0001@\u0002@\"9\u0011\u0011\u001c\u0001\u0005R\u0005m\u0007bBAt\u0001\u0011E\u0013\u0011\u001e\u0005\b\u0003c\u0004A\u0011IAz\u0011\u001d\t)\u0010\u0001C!\u0003gDq!a>\u0001\t\u0003\nI\u0010C\u0004\u0002|\u0002!\t%!@\t\u000f\u0005}\b\u0001\"\u0003\u0002z\"9!\u0011\u0001\u0001\u0005\n\t\r\u0001b\u0002B\u0005\u0001\u0011%!1\u0002\u0005\b\u0005+\u0001A\u0011\u0002B\f\u0011\u001d\u0011)\u0003\u0001C!\u0005OAqAa\u000b\u0001\t\u0003\u0012i\u0003C\u0004\u00038\u0001!\tE!\u000f\t\u000f\tE\u0003\u0001\"\u0011\u0003T!9!Q\f\u0001\u0005\u0002\t}\u0003b\u0002B2\u0001\u0011\u0005#Q\r\u0005\b\u0005\u0013\u0002A\u0011\tB5\u0011\u001d\u0011i\u0007\u0001C\u0005\u0005_BqA!\u001c\u0001\t\u0003\u0011)\bC\u0004\u0003x\u0001!\tE!\u001f\t\u000f\t\u0005\u0005\u0001\"\u0011\u0003\u0004\"9!\u0011\u0012\u0001\u0005\u0002\t-\u0005b\u0002BT\u0001\u0011\u0015!\u0011\u0016\u0005\b\u0005O\u0003AQ\tBY\u0011\u001d\u0011I\f\u0001C\u0001\u0005wCqAa0\u0001\t\u0003\u0011\t\rC\u0004\u0003J\u0002!\tEa3\t\u000f\t=\u0007\u0001\"\u0011\u0003R\u001a9!Q\u001b\u0001\u0002\n\t]\u0007bBAA[\u0011\u0005!q\u001d\u0005\t\u0003\u001fl\u0003\u0015!\u0003\u0002$\"A\u00111[\u0017!\u0002\u0013\tY\u000b\u0003\u0005\u0002X6\u0002\u000b\u0011BAV\u0011!\u0011Y/\fQ!\n\u0005M\u0004b\u0002Bw[\u0011\u0005\u0011\u0011 \u0005\b\u0005_lC\u0011\u0001By\u0011\u001d\u0011\u00190\fD\t\u0005kDqA!@\u0001\t\u0003\u0012y\u0010C\u0004\u0004\u0010\u0001!\te!\u0005\t\u000f\r\u0005\u0002\u0001\"\u0011\u0002\f\"911\u0005\u0001\u0005B\r\u0015\u0002bBB\u0012\u0001\u0011\u00053Q\b\u0005\b\u0007C\u0002A\u0011IB2\u0011\u001d\u00199\b\u0001C!\u0007sBqa!#\u0001\t\u0003\u001aY\t\u0003\u0005\u0004 \u0002\u0001K\u0011BBQ\u0011\u001d\u0019)\f\u0001C\u0001\u0007oCqaa1\u0001\t\u0003\u0019)\rC\u0004\u0004R\u0002!\taa5\t\u000f\r\u0005\b\u0001\"\u0002\u0004d\"91\u0011\u001f\u0001\u0005\u0002\rM\bbBB|\u0001\u0011\u00051\u0011 \u0005\b\t7\u0001A\u0011\u0001C\u000f\u0011\u001d!9\u0004\u0001C\u0001\tsAq\u0001b\u0016\u0001\t\u0003\u0012)\b\u0003\u0005\u0005Z\u0001\u0001K\u0011\u0003C.\u0011!!i\u0006\u0001Q\u0005R\u0011}sa\u0002C9y\"\u0005A1\u000f\u0004\u0007wrD\t\u0001\"\u001e\t\u000f\u0005\u00055\n\"\u0001\u0005\u0002\"IA1Q&C\u0002\u00135AQ\u0011\u0005\t\t\u0017[\u0005\u0015!\u0004\u0005\b\"IAQR&C\u0002\u00135Aq\u0012\u0005\t\t+[\u0005\u0015!\u0004\u0005\u0012\"IAqS&C\u0002\u00135A\u0011\u0014\u0005\t\t?[\u0005\u0015!\u0004\u0005\u001c\"IA\u0011U&C\u0002\u00135A1\u0015\u0005\t\tS[\u0005\u0015!\u0004\u0005&\u001a1A1V&\u0005\t[Cq!!!V\t\u0003!\t\fC\u0004\u0003dU#\t\u0001b.\t\u0013\u0011m6J1A\u0005\n\u0011u\u0006\u0002\u0003C`\u0017\u0002\u0006I\u0001b-\u0007\r\u0011\u00057J\u0001Cb\u0011\u001d\t\tI\u0017C\u0001\t3D!ba\u0015[\u0001\u0004%\tA Co\u0011)!yN\u0017a\u0001\n\u0003qH\u0011\u001d\u0005\t\tOT\u0006\u0015)\u0003\u0005X\"9!q\u0015.\u0005\u0002\u0011%\bb\u0002C,5\u0012\u0005!Q\u000f\u0005\b\tcTF\u0011\u0001Cz\u0011\u001d\t)P\u0017C!\u0003gDqAa\u0019L\t\u0003!)\u0010C\u0004\u0006\n-#\t!b\u0003\t\u000f\u0015u1\n\"\u0003\u0006 !9\u00111`&\u0005\u0002\u0015M\u0002bBC!\u0017\u0012\u0005Q1\t\u0005\b\u000b+ZE\u0011AC,\u0011\u001d)ig\u0013C\u0001\u000b_Bq!\"\u001cL\t\u0003)I\tC\u0004\u0006 .#\u0019!\")\b\u0011\u0015m6\n)E\u0005\u000b{3\u0001\"b0LA#%Q\u0011\u0019\u0005\b\u0003\u0003kG\u0011ACe\u0011\u001d\tI.\u001cC\u0001\u000b\u0017Dq!\"\u0003n\t\u0003)\u0019\u000eC\u0005\u0005Z5\f\t\u0011\"\u0003\u0006X\"9Q1^&\u0005\u0004\u00155x\u0001\u0003D\u0004\u0017\u0002FIA\"\u0003\u0007\u0011\u0019-1\n)E\u0005\r\u001bAq!!!u\t\u00031\t\u0002C\u0004\u0002ZR$\tAb\u0005\t\u000f\u0015%A\u000f\"\u0001\u0007\u001c!9a\u0011E&\u0005\u0004\u0019\r\u0002b\u0002D\u001b\u0017\u0012\raq\u0007\u0005\n\t3Z\u0015\u0011!C\u0005\u000b/\u0014\u0011\"\u00118z%\u00164W*\u00199\u000b\u0005ut\u0018aB7vi\u0006\u0014G.\u001a\u0006\u0004\u007f\u0006\u0005\u0011AC2pY2,7\r^5p]*\u0011\u00111A\u0001\u0006g\u000e\fG.Y\u0002\u0001+\u0019\tI!a\u0006\u0002.MI\u0001!a\u0003\u0002:\u0005\u001d\u00131\f\t\t\u0003\u001b\ty!a\u0005\u0002,5\tA0C\u0002\u0002\u0012q\u00141\"\u00112tiJ\f7\r^'baB!\u0011QCA\f\u0019\u0001!q!!\u0007\u0001\u0005\u0004\tYBA\u0001L#\u0011\ti\"!\n\u0011\t\u0005}\u0011\u0011E\u0007\u0003\u0003\u0003IA!a\t\u0002\u0002\t9aj\u001c;iS:<\u0007\u0003BA\u0010\u0003OIA!!\u000b\u0002\u0002\t1\u0011I\\=SK\u001a\u0004B!!\u0006\u0002.\u00119\u0011q\u0006\u0001C\u0002\u0005E\"!\u0001,\u0012\t\u0005u\u00111\u0007\t\u0005\u0003?\t)$\u0003\u0003\u00028\u0005\u0005!aA!osBa\u0011QBA\u001e\u0003'\tY#a\u0010\u0002F%\u0019\u0011Q\b?\u0003\r5\u000b\u0007o\u00149t!\u0011\ti!!\u0011\n\u0007\u0005\rCPA\u0002NCB\u0004r!!\u0004\u0001\u0003'\tY\u0003\u0005\u0006\u0002J\u0005-\u0013qJA+\u0003\u000bj\u0011A`\u0005\u0004\u0003\u001br(AG*ue&\u001cGo\u00149uS6L'0\u001a3Ji\u0016\u0014\u0018M\u00197f\u001fB\u001c\b\u0003CA\u0010\u0003#\n\u0019\"a\u000b\n\t\u0005M\u0013\u0011\u0001\u0002\u0007)V\u0004H.\u001a\u001a\u0011\t\u00055\u0011qK\u0005\u0004\u00033b(\u0001C%uKJ\f'\r\\3\u0011\t\u0005u\u00131\r\b\u0005\u0003?\ty&\u0003\u0003\u0002b\u0005\u0005\u0011a\u00029bG.\fw-Z\u0005\u0005\u0003K\n9G\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0003\u0002b\u0005\u0005\u0011\u0001\u00043fM\u0006,H\u000e^#oiJL\b\u0003CA\u0010\u0003[\n\u0019\"a\u000b\n\t\u0005=\u0014\u0011\u0001\u0002\n\rVt7\r^5p]F\n\u0011#\u001b8ji&\fGNQ;gM\u0016\u00148+\u001b>f!\u0011\ty\"!\u001e\n\t\u0005]\u0014\u0011\u0001\u0002\u0004\u0013:$\u0018!C5oSR\u0014E.\u00198l!\u0011\ty\"! \n\t\u0005}\u0014\u0011\u0001\u0002\b\u0005>|G.Z1o\u0003\u0019a\u0014N\\5u}QA\u0011QIAC\u0003\u000f\u000bI\tC\u0004\u0002j\u0011\u0001\r!a\u001b\t\u000f\u0005ED\u00011\u0001\u0002t!9\u0011\u0011\u0010\u0003A\u0002\u0005mDCAA#)\u0011\t)%a$\t\u000f\u0005%d\u00011\u0001\u0002lQ!\u0011QIAJ\u0011\u001d\t\th\u0002a\u0001\u0003g\"b!!\u0012\u0002\u0018\u0006e\u0005bBA5\u0011\u0001\u0007\u00111\u000e\u0005\b\u0003cB\u0001\u0019AA:\u0003!\u001a8-\u00197bI\r|G\u000e\\3di&|g\u000eJ7vi\u0006\u0014G.\u001a\u0013B]f\u0014VMZ'ba\u0012\"S.Y:l\u0003\u0015y6/\u001b>f\u0003\u001dyf/Y2b]R\f1f]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI\u0005s\u0017PU3g\u001b\u0006\u0004H\u0005J0iCNDWm\u001d\t\u0007\u0003?\t)+a\u001d\n\t\u0005\u001d\u0016\u0011\u0001\u0002\u0006\u0003J\u0014\u0018-_\u0001*g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$S.\u001e;bE2,G%\u00118z%\u00164W*\u00199%I}[W-_:\u0011\r\u0005}\u0011QUA\u0013\u0003\u001dyf/\u00197vKN\f\u0011\u0003Z3gCVdG/\u00138ji&\fG.\u001b>f)\u0011\t\u0019,!/\u0011\t\u0005}\u0011QW\u0005\u0005\u0003o\u000b\tA\u0001\u0003V]&$\bbBA^\u001f\u0001\u0007\u00111O\u0001\u0002]\u0006a\u0011N\\5uS\u0006d\u0017N_3U_Rq\u00111WAa\u0003\u000b\fI-!4\u0002R\u0006U\u0007bBAb!\u0001\u0007\u00111O\u0001\u0002[\"9\u0011q\u0019\tA\u0002\u0005M\u0014AA:{\u0011\u001d\tY\r\u0005a\u0001\u0003g\n!A^2\t\u000f\u0005=\u0007\u00031\u0001\u0002$\u0006\u0011\u0001N\u001f\u0005\b\u0003'\u0004\u0002\u0019AAV\u0003\tY'\u0010C\u0004\u0002XB\u0001\r!a+\u0002\u0005YT\u0018\u0001\u00044s_6\u001c\u0006/Z2jM&\u001cG\u0003BA#\u0003;Dq!a8\u0012\u0001\u0004\t\t/\u0001\u0003d_2d\u0007CBA%\u0003G\fy%C\u0002\u0002fz\u0014A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\f!C\\3x'B,7-\u001b4jG\n+\u0018\u000e\u001c3feV\u0011\u00111\u001e\t\t\u0003\u001b\ti/a\u0014\u0002F%\u0019\u0011q\u001e?\u0003\u000f\t+\u0018\u000e\u001c3fe\u0006!1/\u001b>f+\t\t\u0019(A\u0005l]><hnU5{K\u00069\u0011n]#naRLXCAA>\u0003\u0015)W\u000e\u001d;z+\t\t)%\u0001\u0006j[\n\fG.\u00198dK\u0012\fa\u0001[1tQ>3G\u0003BA:\u0005\u000bAqAa\u0002\u0019\u0001\u0004\t\u0019\"A\u0002lKf\f\u0011b]3fW\u0016sGO]=\u0015\r\u0005M$Q\u0002B\t\u0011\u001d\u0011y!\u0007a\u0001\u0003g\n\u0011\u0001\u001b\u0005\b\u0005'I\u0002\u0019AA\u0013\u0003\u0005Y\u0017aD:fK.,e\u000e\u001e:z\u001fJ|\u0005/\u001a8\u0015\r\u0005M$\u0011\u0004B\u000e\u0011\u001d\u0011yA\u0007a\u0001\u0003gBqAa\u0005\u001b\u0001\u0004\t)\u0003K\u0002\u001b\u0005?\u0001B!a\b\u0003\"%!!1EA\u0001\u0005\u0019Ig\u000e\\5oK\u0006A1m\u001c8uC&t7\u000f\u0006\u0003\u0002|\t%\u0002b\u0002B\u00047\u0001\u0007\u00111C\u0001\u0004O\u0016$H\u0003\u0002B\u0018\u0005k\u0001b!a\b\u00032\u0005-\u0012\u0002\u0002B\u001a\u0003\u0003\u0011aa\u00149uS>t\u0007b\u0002B\u00049\u0001\u0007\u00111C\u0001\nO\u0016$xJ]#mg\u0016,BAa\u000f\u0003@Q1!Q\bB#\u0005\u000f\u0002B!!\u0006\u0003@\u00119!\u0011I\u000fC\u0002\t\r#A\u0001,2#\u0011\tY#a\r\t\u000f\t\u001dQ\u00041\u0001\u0002\u0014!A!\u0011J\u000f\u0005\u0002\u0004\u0011Y%A\u0004eK\u001a\fW\u000f\u001c;\u0011\r\u0005}!Q\nB\u001f\u0013\u0011\u0011y%!\u0001\u0003\u0011q\u0012\u0017P\\1nKz\nqbZ3u\u001fJ,En]3Va\u0012\fG/\u001a\u000b\u0007\u0003W\u0011)Fa\u0016\t\u000f\t\u001da\u00041\u0001\u0002\u0014!A!\u0011\f\u0010\u0005\u0002\u0004\u0011Y&\u0001\u0007eK\u001a\fW\u000f\u001c;WC2,X\r\u0005\u0004\u0002 \t5\u00131F\u0001\nO\u0016$xJ\u001d(vY2$B!a\u000b\u0003b!9!qA\u0010A\u0002\u0005M\u0011!B1qa2LH\u0003BA\u0016\u0005OBqAa\u0002!\u0001\u0004\t\u0019\u0002\u0006\u0003\u0002,\t-\u0004b\u0002B\u0004C\u0001\u0007\u00111C\u0001\u0007e\u0016\u0004\u0018mY6\u0015\t\u0005M&\u0011\u000f\u0005\b\u0005g\u0012\u0003\u0019AA:\u0003\u001dqWm^'bg.$\"!a-\u0002\u0007A,H\u000f\u0006\u0004\u00030\tm$Q\u0010\u0005\b\u0005\u000f!\u0003\u0019AA\n\u0011\u001d\u0011y\b\na\u0001\u0003W\tQA^1mk\u0016\fa!\u001e9eCR,GCBAZ\u0005\u000b\u00139\tC\u0004\u0003\b\u0015\u0002\r!a\u0005\t\u000f\t}T\u00051\u0001\u0002,\u0005AA\u0005\u001d7vg\u0012*\u0017\u000f\u0006\u0004\u0003\u000e\n=%\u0011S\u0007\u0002\u0001!9!q\u0001\u0014A\u0002\u0005M\u0001b\u0002B@M\u0001\u0007\u00111\u0006\u0015\fM\tU%1\u0014BO\u0005C\u0013\u0019\u000b\u0005\u0003\u0002 \t]\u0015\u0002\u0002BM\u0003\u0003\u0011!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f#Aa(\u0002MV\u001bX\r\t1bI\u0012|e.\u001a1!_J\u0004\u0003-\u001e9eCR,\u0007\rI5ogR,\u0017\rZ\u001e!S:4\u0017\u000e\u001f\u0011pa\u0016\u0014\u0018\r^5p]N\u0004s/\u001b;iA\u0005t\u0007e\u001c9fe\u0006tG\rI8gA5,H\u000e^5qY\u0016\u0004\u0013M]4tA]LG\u000e\u001c\u0011cK\u0002\"W\r\u001d:fG\u0006$X\rZ\u0001\u0006g&t7-Z\u0011\u0003\u0005K\u000baA\r\u00182g9\u001a\u0014AB1eI>sW\r\u0006\u0004\u0003\u000e\n-&Q\u0016\u0005\b\u0005\u000f9\u0003\u0019AA\n\u0011\u001d\u0011yh\na\u0001\u0003WA3a\nB\u0010)\u0011\u0011iIa-\t\u000f\tU\u0006\u00061\u0001\u0002P\u0005\u00111N\u001e\u0015\u0004Q\t}\u0011aC:vER\u0014\u0018m\u0019;P]\u0016$BA!$\u0003>\"9!qA\u0015A\u0002\u0005M\u0011\u0001C5uKJ\fGo\u001c:\u0016\u0005\t\r\u0007CBA%\u0005\u000b\fy%C\u0002\u0003Hz\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\rW\u0016L8/\u0013;fe\u0006$xN]\u000b\u0003\u0005\u001b\u0004b!!\u0013\u0003F\u0006M\u0011A\u0004<bYV,7/\u0013;fe\u0006$xN]\u000b\u0003\u0005'\u0004b!!\u0013\u0003F\u0006-\"!E!osJ+g-T1q\u0013R,'/\u0019;peV!!\u0011\u001cBr'\ri#1\u001c\t\u0007\u0003\u0013\u0012iN!9\n\u0007\t}gP\u0001\tBEN$(/Y2u\u0013R,'/\u0019;peB!\u0011Q\u0003Br\t\u001d\u0011)/\fb\u0001\u0003c\u0011\u0011!\u0011\u000b\u0003\u0005S\u0004RA!$.\u0005C\fQ!\u001b8eKb\fq\u0001[1t\u001d\u0016DH/\u0001\u0003oKb$HC\u0001Bq\u0003)qW\r\u001f;SKN,H\u000e\u001e\u000b\u0007\u0005C\u00149P!?\t\u000f\tMQ\u00071\u0001\u0002\u0014!9!1`\u001bA\u0002\u0005-\u0012!\u0001<\u0002\u000f\u0019|'/Z1dQV!1\u0011AB\u0006)\u0011\t\u0019la\u0001\t\u000f\r\u0015a\u00071\u0001\u0004\b\u0005\ta\r\u0005\u0005\u0002 \u00055\u0014qJB\u0005!\u0011\t)ba\u0003\u0005\u000f\r5aG1\u0001\u00022\t\tQ+\u0001\u0007g_J,\u0017m\u00195F]R\u0014\u00180\u0006\u0003\u0004\u0014\r}A\u0003BAZ\u0007+Aqa!\u00028\u0001\u0004\u00199\u0002\u0005\u0006\u0002 \re\u00111CA\u0016\u0007;IAaa\u0007\u0002\u0002\tIa)\u001e8di&|gN\r\t\u0005\u0003+\u0019y\u0002B\u0004\u0004\u000e]\u0012\r!!\r\u0002\u000b\rdwN\\3\u0002\u000b\u0011\u0002H.^:\u0016\t\r\u001d2Q\u0006\u000b\u0005\u0007S\u0019y\u0003E\u0004\u0002\u000e\u0001\t\u0019ba\u000b\u0011\t\u0005U1Q\u0006\u0003\b\u0005\u0003J$\u0019\u0001B\"\u0011\u001d\u0011),\u000fa\u0001\u0007c\u0001\u0002\"a\b\u0002R\u0005M11\u0006\u0015\fs\tU%1TB\u001b\u0005C\u001bI$\t\u0002\u00048\u0005q4i\u001c8tS\u0012,'\u000f\t:fcVL'/\u001b8hA\u0005t\u0007%[7nkR\f'\r\\3!\u001b\u0006\u0004\be\u001c:!M\u0006dG\u000e\t2bG.\u0004Co\u001c\u0011NCBt3m\u001c8dCR\f#aa\u000f\u0002\rIr\u0013g\r\u00181+\u0011\u0019yd!\u0012\u0015\u0011\r\u00053qIB'\u0007#\u0002r!!\u0004\u0001\u0003'\u0019\u0019\u0005\u0005\u0003\u0002\u0016\r\u0015Ca\u0002B!u\t\u0007!1\t\u0005\b\u0007\u0013R\u0004\u0019AB&\u0003\u0015)G.Z72!!\ty\"!\u0015\u0002\u0014\r\r\u0003bBB(u\u0001\u000711J\u0001\u0006K2,WN\r\u0005\b\u0007'R\u0004\u0019AB+\u0003\u0015)G.Z7t!\u0019\tyba\u0016\u0004L%!1\u0011LA\u0001\u0005)a$/\u001a9fCR,GM\u0010\u0015\fu\tU%1TB/\u0005C\u001bI$\t\u0002\u0004`\u0005)Uk]3!W-\u0002s/\u001b;iA\u0005t\u0007%\u001a=qY&\u001c\u0017\u000e\u001e\u0011d_2dWm\u0019;j_:\u0004\u0013M]4v[\u0016tG\u000fI5ogR,\u0017\r\u001a\u0011pM\u0002Z\u0003e^5uQ\u00022\u0018M]1sON\faaY8oG\u0006$X\u0003BB3\u0007W\"Baa\u001a\u0004pA9\u0011Q\u0002\u0001\u0002\u0014\r%\u0004\u0003BA\u000b\u0007W\"qa!\u001c<\u0005\u0004\u0011\u0019E\u0001\u0002We!91\u0011O\u001eA\u0002\rM\u0014A\u0001=t!\u0019\tI%a9\u0004vAA\u0011qDA)\u0003'\u0019I'\u0001\u0006%a2,8\u000f\n9mkN,Baa\u001f\u0004\u0002R!1QPBB!\u001d\ti\u0001AA\n\u0007\u007f\u0002B!!\u0006\u0004\u0002\u001291Q\u000e\u001fC\u0002\t\r\u0003bBB9y\u0001\u00071Q\u0011\t\u0007\u0003\u0013\n\u0019oa\"\u0011\u0011\u0005}\u0011\u0011KA\n\u0007\u007f\nq!\u001e9eCR,G-\u0006\u0003\u0004\u000e\u000eMECBBH\u0007+\u001b9\nE\u0004\u0002\u000e\u0001\t\u0019b!%\u0011\t\u0005U11\u0013\u0003\b\u0005\u0003j$\u0019\u0001B\"\u0011\u001d\u00119!\u0010a\u0001\u0003'AqAa >\u0001\u0004\u0019\t\nK\u0006>\u0005+\u0013Yja'\u0003\"\u000ee\u0012EABO\u0003Q*6/\u001a\u0011n]\rdwN\\3)S9\nG\rZ(oK\"ZGF^\u0015!S:\u001cH/Z1eA=4\u0007%\u001c\u0018va\u0012\fG/\u001a3)W2\u0002c/K\u0001\u000fM>\u0014X-Y2i\u000b2,W.\u001a8u+\u0019\u0019\u0019k!,\u00042R1\u00111WBS\u0007OCqaa\u0015?\u0001\u0004\tY\u000bC\u0004\u0004\u0006y\u0002\ra!+\u0011\u0011\u0005}\u0011QNBV\u0007_\u0003B!!\u0006\u0004.\u00129!Q\u001d C\u0002\u0005E\u0002\u0003BA\u000b\u0007c#qaa-?\u0005\u0004\t\tDA\u0001C\u0003)1wN]3bG\"\\U-_\u000b\u0005\u0007s\u001b\t\r\u0006\u0003\u00024\u000em\u0006bBB\u0003\u007f\u0001\u00071Q\u0018\t\t\u0003?\ti'a\u0005\u0004@B!\u0011QCBa\t\u001d\u0011)o\u0010b\u0001\u0003c\tABZ8sK\u0006\u001c\u0007NV1mk\u0016,Baa2\u0004PR!\u00111WBe\u0011\u001d\u0019)\u0001\u0011a\u0001\u0007\u0017\u0004\u0002\"a\b\u0002n\u0005-2Q\u001a\t\u0005\u0003+\u0019y\rB\u0004\u0003f\u0002\u0013\r!!\r\u0002\u00195\f\u0007OV1mk\u0016\u001chj\\<\u0016\t\rU71\u001c\u000b\u0005\u0007/\u001ci\u000eE\u0004\u0002\u000e\u0001\t\u0019b!7\u0011\t\u0005U11\u001c\u0003\b\u0005\u0003\n%\u0019AA\u0019\u0011\u001d\u0019)!\u0011a\u0001\u0007?\u0004\u0002\"a\b\u0002n\u0005-2\u0011\\\u0001\u0010iJ\fgn\u001d4pe64\u0016\r\\;fgR!!QRBs\u0011\u001d\u0019)A\u0011a\u0001\u0007O\u0004\u0002\"a\b\u0002n\u0005-\u00121\u0006\u0015\u0004\u0005\n}\u0001f\u0003\"\u0003\u0016\nm5Q\u001eBQ\u0007s\t#aa<\u0002kU\u001bX\r\t;sC:\u001chm\u001c:n-\u0006dW/Z:J]Bc\u0017mY3!S:\u001cH/Z1eA=4\u0007\u0005\u001e:b]N4wN]7WC2,Xm]\u0001\u0017iJ\fgn\u001d4pe64\u0016\r\\;fg&s\u0007\u000b\\1dKR!!QRB{\u0011\u001d\u0019)a\u0011a\u0001\u0007O\f1!\\1q+\u0019\u0019Y\u0010b\u0001\u0005\nQ!1Q C\u000b)\u0011\u0019y\u0010b\u0003\u0011\u000f\u00055\u0001\u0001\"\u0001\u0005\bA!\u0011Q\u0003C\u0002\t\u001d!)\u0001\u0012b\u0001\u00037\u0011!a\u0013\u001a\u0011\t\u0005UA\u0011\u0002\u0003\b\u0007[\"%\u0019AA\u0019\u0011\u001d!i\u0001\u0012a\u0002\t\u001f\tQ\u0001Z;n[f\u0004B!a\b\u0005\u0012%!A1CA\u0001\u00055!U/\\7z\u00136\u0004H.[2ji\"91Q\u0001#A\u0002\u0011]\u0001\u0003CA\u0010\u0003[\ny\u0005\"\u0007\u0011\u0011\u0005}\u0011\u0011\u000bC\u0001\t\u000f\tqA\u001a7bi6\u000b\u0007/\u0006\u0004\u0005 \u0011\u001dB1\u0006\u000b\u0005\tC!y\u0003\u0006\u0003\u0005$\u00115\u0002cBA\u0007\u0001\u0011\u0015B\u0011\u0006\t\u0005\u0003+!9\u0003B\u0004\u0005\u0006\u0015\u0013\r!a\u0007\u0011\t\u0005UA1\u0006\u0003\b\u0007[*%\u0019AA\u0019\u0011\u001d!i!\u0012a\u0002\t\u001fAqa!\u0002F\u0001\u0004!\t\u0004\u0005\u0005\u0002 \u00055\u0014q\nC\u001a!\u0019\tI%a9\u00056AA\u0011qDA)\tK!I#A\u0004d_2dWm\u0019;\u0016\r\u0011mB1\tC$)\u0011!i\u0004b\u0013\u0015\t\u0011}B\u0011\n\t\b\u0003\u001b\u0001A\u0011\tC#!\u0011\t)\u0002b\u0011\u0005\u000f\u0011\u0015aI1\u0001\u0002\u001cA!\u0011Q\u0003C$\t\u001d\u0019iG\u0012b\u0001\u0003cAq\u0001\"\u0004G\u0001\b!y\u0001C\u0004\u0005N\u0019\u0003\r\u0001b\u0014\u0002\u0005A4\u0007\u0003CA\u0010\t#\ny\u0005\"\u0016\n\t\u0011M\u0013\u0011\u0001\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]BA\u0011qDA)\t\u0003\")%A\u0003dY\u0016\f'/\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002&\u0005a1\u000f\u001e:j]\u001e\u0004&/\u001a4jqV\u0011A\u0011\r\t\u0005\tG\"i'\u0004\u0002\u0005f)!Aq\rC5\u0003\u0011a\u0017M\\4\u000b\u0005\u0011-\u0014\u0001\u00026bm\u0006LA\u0001b\u001c\u0005f\t11\u000b\u001e:j]\u001e\f\u0011\"\u00118z%\u00164W*\u00199\u0011\u0007\u000551jE\u0003L\u0003K!9\b\u0005\u0003\u0005z\u0011}TB\u0001C>\u0015\u0011!i\b\"\u001b\u0002\u0005%|\u0017\u0002BA3\tw\"\"\u0001b\u001d\u0002\u0013%sG-\u001a=NCN\\WC\u0001CD\u001f\t!I)\b\u0003@\u007f\u0000\u0000 AC%oI\u0016DX*Y:lA\u0005QQ*[:tS:<')\u001b;\u0016\u0005\u0011EuB\u0001CJ;\u0011\u0001\t\u0001\u0001\u0001\u0002\u00175K7o]5oO\nKG\u000fI\u0001\n-\u0006\u001c\u0017M\u001c;CSR,\"\u0001b'\u0010\u0005\u0011uU\u0004\u0002!\u0001\u0001\u0001\t!BV1dC:$()\u001b;!\u0003)i\u0015n]:WC\u000e\fg\u000e^\u000b\u0003\tK{!\u0001b*\u001e\t\u0001\u0007\u0001\u0001A\u0001\f\u001b&\u001c8OV1dC:$\bE\u0001\tFq\u000e,\u0007\u000f^5p]\u0012+g-Y;miN9Q+!\n\u00050\u0006m\u0003\u0003CA\u0010\u0003[\n\u0019$!\b\u0015\u0005\u0011M\u0006c\u0001C[+6\t1\n\u0006\u0003\u0002\u001e\u0011e\u0006b\u0002B\n/\u0002\u0007\u00111G\u0001\u0011Kb\u001cW\r\u001d;j_:$UMZ1vYR,\"\u0001b-\u0002#\u0015D8-\u001a9uS>tG)\u001a4bk2$\bE\u0001\tB]f\u0014VMZ'ba\n+\u0018\u000e\u001c3feV1AQ\u0019Ci\t+\u001cRAWA\u0013\t\u000f\u0004\u0002\"!\u0004\u0005J\u00125Gq[\u0005\u0004\t\u0017d(a\u0004*fkN\f'\r\\3Ck&dG-\u001a:\u0011\u0011\u0005}\u0011\u0011\u000bCh\t'\u0004B!!\u0006\u0005R\u00129\u0011\u0011\u0004.C\u0002\u0005m\u0001\u0003BA\u000b\t+$q!a\f[\u0005\u0004\t\t\u0004E\u0004\u0002\u000e\u0001!y\rb5\u0015\u0005\u0011m\u0007c\u0002C[5\u0012=G1[\u000b\u0003\t/\f\u0011\"\u001a7f[N|F%Z9\u0015\t\u0005MF1\u001d\u0005\n\tKl\u0016\u0011!a\u0001\t/\f1\u0001\u001f\u00132\u0003\u0019)G.Z7tAQ!A1\u001eCw\u001b\u0005Q\u0006b\u0002Cx?\u0002\u0007AQZ\u0001\u0006K:$(/_\u0001\u0007e\u0016\u001cX\u000f\u001c;\u0015\u0005\u0011]WC\u0002C|\t{,\t\u0001\u0006\u0003\u0005z\u0016\r\u0001cBA\u0007\u0001\u0011mHq \t\u0005\u0003+!i\u0010B\u0004\u0002\u001a\r\u0014\r!a\u0007\u0011\t\u0005UQ\u0011\u0001\u0003\b\u0003_\u0019'\u0019AA\u0019\u0011\u001d\u0019\u0019f\u0019a\u0001\u000b\u000b\u0001b!a\b\u0004X\u0015\u001d\u0001\u0003CA\u0010\u0003#\"Y\u0010b@\u0002\u00159,wOQ;jY\u0012,'/\u0006\u0004\u0006\u000e\u0015UQ\u0011D\u000b\u0003\u000b\u001f\u0001\u0002\"!\u0004\u0005J\u0016EQ1\u0004\t\t\u0003?\t\t&b\u0005\u0006\u0018A!\u0011QCC\u000b\t\u001d\tI\u0002\u001ab\u0001\u00037\u0001B!!\u0006\u0006\u001a\u00119\u0011q\u00063C\u0002\u0005E\u0002cBA\u0007\u0001\u0015MQqC\u0001\u0016EVLG\u000e\u001a$s_6LE/\u001a:bE2,wJ\\2f+\u0019)\t#b\n\u0006,Q!Q1EC\u0017!\u001d\ti\u0001AC\u0013\u000bS\u0001B!!\u0006\u0006(\u00119\u0011\u0011D3C\u0002\u0005m\u0001\u0003BA\u000b\u000bW!q!a\ff\u0005\u0004\t\t\u0004C\u0004\u0004T\u0015\u0004\r!b\f\u0011\r\u0005%\u00131]C\u0019!!\ty\"!\u0015\u0006&\u0015%RCBC\u001b\u000bw)y$\u0006\u0002\u00068A9\u0011Q\u0002\u0001\u0006:\u0015u\u0002\u0003BA\u000b\u000bw!q!!\u0007g\u0005\u0004\tY\u0002\u0005\u0003\u0002\u0016\u0015}BaBA\u0018M\n\u0007\u0011\u0011G\u0001\fo&$\b\u000eR3gCVdG/\u0006\u0004\u0006F\u0015-Sq\n\u000b\u0005\u000b\u000f*\t\u0006E\u0004\u0002\u000e\u0001)I%\"\u0014\u0011\t\u0005UQ1\n\u0003\b\u000339'\u0019AA\u000e!\u0011\t)\"b\u0014\u0005\u000f\u0005=rM1\u0001\u00022!9!\u0011J4A\u0002\u0015M\u0003\u0003CA\u0010\u0003[*I%\"\u0014\u0002\t\u0019\u0014x.\\\u000b\u0007\u000b3*y&b\u0019\u0015\t\u0015mSQ\r\t\b\u0003\u001b\u0001QQLC1!\u0011\t)\"b\u0018\u0005\u000f\u0005e\u0001N1\u0001\u0002\u001cA!\u0011QCC2\t\u001d\ty\u0003\u001bb\u0001\u0003cAq!b\u001ai\u0001\u0004)I'\u0001\u0004t_V\u00148-\u001a\t\u0007\u0003\u0013\n\u0019/b\u001b\u0011\u0011\u0005}\u0011\u0011KC/\u000bC\nqA\u001a:p[jK\u0007/\u0006\u0004\u0006r\u0015]T1\u0010\u000b\u0007\u000bg*i(b!\u0011\u000f\u00055\u0001!\"\u001e\u0006zA!\u0011QCC<\t\u001d\tI\"\u001bb\u0001\u00037\u0001B!!\u0006\u0006|\u00119\u0011qF5C\u0002\u0005E\u0002bBC@S\u0002\u0007Q\u0011Q\u0001\u0005W\u0016L8\u000f\u0005\u0004\u0002 \u0005\u0015VQ\u000f\u0005\b\u000b\u000bK\u0007\u0019ACD\u0003\u00191\u0018\r\\;fgB1\u0011qDAS\u000bs*b!b#\u0006\u0012\u0016UECBCG\u000b/+Y\nE\u0004\u0002\u000e\u0001)y)b%\u0011\t\u0005UQ\u0011\u0013\u0003\b\u00033Q'\u0019AA\u000e!\u0011\t)\"\"&\u0005\u000f\u0005=\"N1\u0001\u00022!9Qq\u00106A\u0002\u0015e\u0005CBA\u0007\u0003/*y\tC\u0004\u0006\u0006*\u0004\r!\"(\u0011\r\u00055\u0011qKCJ\u0003%!xNR1di>\u0014\u00180\u0006\u0004\u0006$\u0016=V1\u0017\u000b\u0005\u000bK+9\f\u0005\u0005\u0002J\u0015\u001dV1VC[\u0013\r)IK \u0002\b\r\u0006\u001cGo\u001c:z!!\ty\"!\u0015\u0006.\u0016E\u0006\u0003BA\u000b\u000b_#q!!\u0007l\u0005\u0004\tY\u0002\u0005\u0003\u0002\u0016\u0015MFaBA\u0018W\n\u0007\u0011\u0011\u0007\t\b\u0003\u001b\u0001QQVCY\u0011\u001d!ia\u001ba\u0001\u000bss1!!\u0004K\u0003%!vNR1di>\u0014\u0018\u0010E\u0002\u000566\u0014\u0011\u0002V8GC\u000e$xN]=\u0014\u000f5\f)#b1\u0002\\AA\u0011\u0011JCT\u000b\u000b,9\r\u0005\u0005\u0002 \u0005E\u0013QEA\u0013!\u001d\ti\u0001AA\u0013\u0003K!\"!\"0\u0015\t\u0015\u001dWQ\u001a\u0005\b\u000b\u001f|\u0007\u0019ACi\u0003\tIG\u000f\u0005\u0004\u0002J\u0005\rXQY\u000b\u0003\u000b+\u0004\u0002\"!\u0004\u0002n\u0016\u0015Wq\u0019\u000b\u0003\u000b3\u0004B\u0001b\u0019\u0006\\&!QQ\u001cC3\u0005\u0019y%M[3di\":Q.\"9\u0003\u0000\u0015\u001d\b\u0003BA\u0010\u000bGLA!\":\u0002\u0002\t\u00012+\u001a:jC24VM]:j_:,\u0016\n\u0012\u0010\u0002\u0007!:A.\"9\u0003\u0000\u0015\u001d\u0018a\u0003;p\u0005VLG\u000e\u001a$s_6,b!b<\u0006|\u0016}H\u0003BCy\r\u0007\u0001\"\"!\u0013\u0006t\u0006MRq\u001fD\u0001\u0013\r))P \u0002\n\u0005VLG\u000e\u001a$s_6\u0004\u0002\"a\b\u0002R\u0015eXQ \t\u0005\u0003+)Y\u0010B\u0004\u0002\u001aI\u0014\r!a\u0007\u0011\t\u0005UQq \u0003\b\u0003_\u0011(\u0019AA\u0019!\u001d\ti\u0001AC}\u000b{DqA\"\u0002s\u0001\u0004)I,A\u0004gC\u000e$xN]=\u0002\u0017Q{')^5mI\u001a\u0013x.\u001c\t\u0004\tk#(a\u0003+p\u0005VLG\u000e\u001a$s_6\u001cR\u0001^A\u0013\r\u001f\u0001\"\"!\u0013\u0006t\u0006MRQYCd)\t1I\u0001\u0006\u0003\u0007\u0016\u0019eA\u0003BCd\r/Aq!b4w\u0001\u0004)\t\u000eC\u0004\u0006VY\u0004\r!a\r\u0015\t\u0019uaq\u0004\t\t\u0003\u001b!I-\"2\u0006H\"9QQK<A\u0002\u0005M\u0012aD5uKJ\f'\r\\3GC\u000e$xN]=\u0016\r\u0019\u0015bQ\u0006D\u0019+\t19\u0003\u0005\u0005\u0002J\u0015\u001df\u0011\u0006D\u001a!!\ty\"!\u0015\u0007,\u0019=\u0002\u0003BA\u000b\r[!q!!\u0007y\u0005\u0004\tY\u0002\u0005\u0003\u0002\u0016\u0019EBaBA\u0018q\n\u0007\u0011\u0011\u0007\t\b\u0003\u001b\u0001a1\u0006D\u0018\u0003I\u0011W/\u001b7e\rJ|W.\u00118z%\u00164W*\u00199\u0016\r\u0019eb\u0011\u000bD++\t1Y\u0004\u0005\u0006\u0002J\u0015MhQ\bD'\r/\u0002dAb\u0010\u0007D\u0019%\u0003cBA\u0007\u0001\u0019\u0005cq\t\t\u0005\u0003+1\u0019\u0005B\u0006\u0007Fe\f\t\u0011!A\u0003\u0002\u0005E\"aA0%cA!\u0011Q\u0003D%\t-1Y%_A\u0001\u0002\u0003\u0015\t!!\r\u0003\u0007}##\u0007\u0005\u0005\u0002 \u0005Ecq\nD*!\u0011\t)B\"\u0015\u0005\u000f\u0005e\u0011P1\u0001\u0002\u001cA!\u0011Q\u0003D+\t\u001d\ty#\u001fb\u0001\u0003c\u0001r!!\u0004\u0001\r\u001f2\u0019\u0006")
public class AnyRefMap<K, V>
extends AbstractMap<K, V>
implements StrictOptimizedIterableOps<Tuple2<K, V>, scala.collection.mutable.Iterable, AnyRefMap<K, V>>,
Serializable {
    private final Function1<K, V> defaultEntry;
    public int scala$collection$mutable$AnyRefMap$$mask;
    private int _size;
    private int _vacant;
    public int[] scala$collection$mutable$AnyRefMap$$_hashes;
    public Object[] scala$collection$mutable$AnyRefMap$$_keys;
    public Object[] scala$collection$mutable$AnyRefMap$$_values;

    public static <K, V> BuildFrom<AnyRefMap<?, ?>, Tuple2<K, V>, AnyRefMap<K, V>> buildFromAnyRefMap() {
        return AnyRefMap$ToBuildFrom$.MODULE$;
    }

    public static <K, V> BuildFrom<Object, Tuple2<K, V>, AnyRefMap<K, V>> toBuildFrom(AnyRefMap$ factory2) {
        return AnyRefMap$ToBuildFrom$.MODULE$;
    }

    public static <K, V> Factory<Tuple2<K, V>, AnyRefMap<K, V>> toFactory(AnyRefMap$ dummy) {
        return AnyRefMap$ToFactory$.MODULE$;
    }

    public static <K, V> AnyRefMap<K, V> fromZip(scala.collection.mutable.Iterable<K> keys2, scala.collection.mutable.Iterable<V> values2) {
        return AnyRefMap$.MODULE$.fromZip(keys2, values2);
    }

    public static <K, V> AnyRefMap<K, V> fromZip(K[] keys2, Object values2) {
        return AnyRefMap$.MODULE$.fromZip(keys2, values2);
    }

    public static <K, V> AnyRefMap<K, V> from(IterableOnce<Tuple2<K, V>> source2) {
        return AnyRefMap$.MODULE$.from(source2);
    }

    public static <K, V> ReusableBuilder<Tuple2<K, V>, AnyRefMap<K, V>> newBuilder() {
        return new AnyRefMapBuilder();
    }

    @Override
    public Tuple2<AnyRefMap<K, V>, AnyRefMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<AnyRefMap<K, V>, AnyRefMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>, scala.collection.mutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<K, V>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<K, V>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<K, V>, B>, C2> b) {
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
    public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
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

    private void defaultInitialize(int n) {
        this.scala$collection$mutable$AnyRefMap$$mask = n < 0 ? 7 : (1 << 32 - Integer.numberOfLeadingZeros(n - 1)) - 1 & 0x3FFFFFFF | 7;
        this.scala$collection$mutable$AnyRefMap$$_hashes = new int[this.scala$collection$mutable$AnyRefMap$$mask + 1];
        this.scala$collection$mutable$AnyRefMap$$_keys = new Object[this.scala$collection$mutable$AnyRefMap$$mask + 1];
        this.scala$collection$mutable$AnyRefMap$$_values = new Object[this.scala$collection$mutable$AnyRefMap$$mask + 1];
    }

    public void initializeTo(int m4, int sz, int vc, int[] hz, Object[] kz, Object[] vz) {
        this.scala$collection$mutable$AnyRefMap$$mask = m4;
        this._size = sz;
        this._vacant = vc;
        this.scala$collection$mutable$AnyRefMap$$_hashes = hz;
        this.scala$collection$mutable$AnyRefMap$$_keys = kz;
        this.scala$collection$mutable$AnyRefMap$$_values = vz;
    }

    @Override
    public AnyRefMap<K, V> fromSpecific(IterableOnce<Tuple2<K, V>> coll) {
        int sz = coll.knownSize();
        if (sz < 0) {
            sz = 4;
        }
        AnyRefMap arm = new AnyRefMap(sz * 2);
        coll.iterator().foreach((Function1<Tuple2, Object> & Serializable)(A x0$1) -> {
            AnyRefMap.$anonfun$fromSpecific$1(arm, x0$1);
            return BoxedUnit.UNIT;
        });
        if (arm.size() < sz >> 3) {
            arm.repack();
        }
        return arm;
    }

    @Override
    public Builder<Tuple2<K, V>, AnyRefMap<K, V>> newSpecificBuilder() {
        return new AnyRefMapBuilder();
    }

    @Override
    public int size() {
        return this._size;
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return this._size == 0;
    }

    @Override
    public AnyRefMap<K, V> empty() {
        return new AnyRefMap<K, V>(this.defaultEntry);
    }

    private boolean imbalanced() {
        return (double)(this._size + this._vacant) > 0.5 * (double)this.scala$collection$mutable$AnyRefMap$$mask || this._vacant > this._size;
    }

    private int hashOf(K key) {
        if (key == null) {
            return 1091049865;
        }
        int h2 = key.hashCode();
        int i = (h2 ^ h2 >>> 16) * -2048144789;
        int j = (i ^ i >>> 13) & Integer.MAX_VALUE;
        if (j == 0) {
            return 1091049865;
        }
        return j;
    }

    private int seekEntry(int h2, Object k) {
        int g2;
        int e = h2 & this.scala$collection$mutable$AnyRefMap$$mask;
        int x = 0;
        int[] hashes = this.scala$collection$mutable$AnyRefMap$$_hashes;
        Object[] keys2 = this.scala$collection$mutable$AnyRefMap$$_keys;
        while ((g2 = hashes[e]) != 0) {
            Object q;
            if (g2 == h2 && ((q = keys2[e]) == k || q != null && q.equals(k))) {
                return e;
            }
            e = e + 2 * (++x + 1) * x - 3 & this.scala$collection$mutable$AnyRefMap$$mask;
        }
        return e | Integer.MIN_VALUE;
    }

    private int seekEntryOrOpen(int h2, Object k) {
        int g2;
        int e = h2 & this.scala$collection$mutable$AnyRefMap$$mask;
        int x = 0;
        int o = -1;
        while ((g2 = this.scala$collection$mutable$AnyRefMap$$_hashes[e]) != 0) {
            Object q;
            if (g2 == h2 && ((q = this.scala$collection$mutable$AnyRefMap$$_keys[e]) == k || q != null && q.equals(k))) {
                return e;
            }
            if (o == -1 && g2 + g2 == 0) {
                o = e;
            }
            e = e + 2 * (++x + 1) * x - 3 & this.scala$collection$mutable$AnyRefMap$$mask;
        }
        if (o >= 0) {
            return o | 0xC0000000;
        }
        return e | Integer.MIN_VALUE;
    }

    @Override
    public boolean contains(K key) {
        return this.seekEntry(this.hashOf(key), key) >= 0;
    }

    @Override
    public Option<V> get(K key) {
        int i = this.seekEntry(this.hashOf(key), key);
        if (i < 0) {
            return None$.MODULE$;
        }
        return new Some<Object>(this.scala$collection$mutable$AnyRefMap$$_values[i]);
    }

    @Override
    public <V1> V1 getOrElse(K key, Function0<V1> function0) {
        int i = this.seekEntry(this.hashOf(key), key);
        if (i < 0) {
            return function0.apply();
        }
        return (V1)this.scala$collection$mutable$AnyRefMap$$_values[i];
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public V getOrElseUpdate(K key, Function0<V> defaultValue) {
        int n;
        int h2;
        block12: {
            int seekEntryOrOpen_g;
            h2 = this.hashOf(key);
            int seekEntryOrOpen_e = h2 & this.scala$collection$mutable$AnyRefMap$$mask;
            int seekEntryOrOpen_x = 0;
            int seekEntryOrOpen_o = -1;
            while ((seekEntryOrOpen_g = this.scala$collection$mutable$AnyRefMap$$_hashes[seekEntryOrOpen_e]) != 0) {
                Object seekEntryOrOpen_q;
                if (seekEntryOrOpen_g == h2 && ((seekEntryOrOpen_q = this.scala$collection$mutable$AnyRefMap$$_keys[seekEntryOrOpen_e]) == key || seekEntryOrOpen_q != null && seekEntryOrOpen_q.equals(key))) {
                    n = seekEntryOrOpen_e;
                    break block12;
                }
                if (seekEntryOrOpen_o == -1 && seekEntryOrOpen_g + seekEntryOrOpen_g == 0) {
                    seekEntryOrOpen_o = seekEntryOrOpen_e;
                }
                seekEntryOrOpen_e = seekEntryOrOpen_e + 2 * (++seekEntryOrOpen_x + 1) * seekEntryOrOpen_x - 3 & this.scala$collection$mutable$AnyRefMap$$mask;
            }
            n = seekEntryOrOpen_o >= 0 ? seekEntryOrOpen_o | 0xC0000000 : seekEntryOrOpen_e | Integer.MIN_VALUE;
        }
        Object var12_8 = null;
        int i = n;
        if (i < 0) {
            void var6_11;
            int[] oh = this.scala$collection$mutable$AnyRefMap$$_hashes;
            V ans = defaultValue.apply();
            if (oh != this.scala$collection$mutable$AnyRefMap$$_hashes) {
                int n2;
                block13: {
                    int seekEntryOrOpen_g;
                    int seekEntryOrOpen_e = h2 & this.scala$collection$mutable$AnyRefMap$$mask;
                    int seekEntryOrOpen_x = 0;
                    int seekEntryOrOpen_o = -1;
                    while ((seekEntryOrOpen_g = this.scala$collection$mutable$AnyRefMap$$_hashes[seekEntryOrOpen_e]) != 0) {
                        Object seekEntryOrOpen_q;
                        if (seekEntryOrOpen_g == h2 && ((seekEntryOrOpen_q = this.scala$collection$mutable$AnyRefMap$$_keys[seekEntryOrOpen_e]) == key || seekEntryOrOpen_q != null && seekEntryOrOpen_q.equals(key))) {
                            n2 = seekEntryOrOpen_e;
                            break block13;
                        }
                        if (seekEntryOrOpen_o == -1 && seekEntryOrOpen_g + seekEntryOrOpen_g == 0) {
                            seekEntryOrOpen_o = seekEntryOrOpen_e;
                        }
                        seekEntryOrOpen_e = seekEntryOrOpen_e + 2 * (++seekEntryOrOpen_x + 1) * seekEntryOrOpen_x - 3 & this.scala$collection$mutable$AnyRefMap$$mask;
                    }
                    n2 = seekEntryOrOpen_o >= 0 ? seekEntryOrOpen_o | 0xC0000000 : seekEntryOrOpen_e | Integer.MIN_VALUE;
                }
                Object var17_16 = null;
                i = n2;
                if (i >= 0) {
                    --this._size;
                }
            }
            ++this._size;
            int j = i & 0x3FFFFFFF;
            this.scala$collection$mutable$AnyRefMap$$_hashes[j] = h2;
            this.scala$collection$mutable$AnyRefMap$$_keys[j] = key;
            this.scala$collection$mutable$AnyRefMap$$_values[j] = var6_11;
            if ((i & 0x40000000) != 0) {
                --this._vacant;
            } else if (this.imbalanced()) {
                this.repack();
            }
            return var6_11;
        }
        return (V)this.scala$collection$mutable$AnyRefMap$$_values[i];
    }

    public V getOrNull(K key) {
        int i = this.seekEntry(this.hashOf(key), key);
        if (i < 0) {
            return null;
        }
        return (V)this.scala$collection$mutable$AnyRefMap$$_values[i];
    }

    @Override
    public V apply(K key) {
        int i = this.seekEntry(this.hashOf(key), key);
        if (i < 0) {
            return this.defaultEntry.apply(key);
        }
        return (V)this.scala$collection$mutable$AnyRefMap$$_values[i];
    }

    @Override
    public V default(K key) {
        return this.defaultEntry.apply(key);
    }

    private void repack(int newMask) {
        int[] oh = this.scala$collection$mutable$AnyRefMap$$_hashes;
        Object[] ok = this.scala$collection$mutable$AnyRefMap$$_keys;
        Object[] ov = this.scala$collection$mutable$AnyRefMap$$_values;
        this.scala$collection$mutable$AnyRefMap$$mask = newMask;
        this.scala$collection$mutable$AnyRefMap$$_hashes = new int[this.scala$collection$mutable$AnyRefMap$$mask + 1];
        this.scala$collection$mutable$AnyRefMap$$_keys = new Object[this.scala$collection$mutable$AnyRefMap$$mask + 1];
        this.scala$collection$mutable$AnyRefMap$$_values = new Object[this.scala$collection$mutable$AnyRefMap$$mask + 1];
        this._vacant = 0;
        for (int i = 0; i < oh.length; ++i) {
            int h2 = oh[i];
            if (h2 + h2 == 0) continue;
            int e = h2 & this.scala$collection$mutable$AnyRefMap$$mask;
            int x = 0;
            while (this.scala$collection$mutable$AnyRefMap$$_hashes[e] != 0) {
                e = e + 2 * (++x + 1) * x - 3 & this.scala$collection$mutable$AnyRefMap$$mask;
            }
            this.scala$collection$mutable$AnyRefMap$$_hashes[e] = h2;
            this.scala$collection$mutable$AnyRefMap$$_keys[e] = ok[i];
            this.scala$collection$mutable$AnyRefMap$$_values[e] = ov[i];
        }
    }

    public void repack() {
        int m4 = this.scala$collection$mutable$AnyRefMap$$mask;
        if ((double)(this._size + this._vacant) >= 0.5 * (double)this.scala$collection$mutable$AnyRefMap$$mask && !((double)this._vacant > 0.2 * (double)this.scala$collection$mutable$AnyRefMap$$mask)) {
            m4 = (m4 << 1) + 1 & 0x3FFFFFFF;
        }
        while (m4 > 8 && 8 * this._size < m4) {
            m4 >>>= 1;
        }
        this.repack(m4);
    }

    @Override
    public Option<V> put(K key, V value) {
        int n;
        int h2;
        block7: {
            int seekEntryOrOpen_g;
            h2 = this.hashOf(key);
            int seekEntryOrOpen_e = h2 & this.scala$collection$mutable$AnyRefMap$$mask;
            int seekEntryOrOpen_x = 0;
            int seekEntryOrOpen_o = -1;
            while ((seekEntryOrOpen_g = this.scala$collection$mutable$AnyRefMap$$_hashes[seekEntryOrOpen_e]) != 0) {
                Object seekEntryOrOpen_q;
                if (seekEntryOrOpen_g == h2 && ((seekEntryOrOpen_q = this.scala$collection$mutable$AnyRefMap$$_keys[seekEntryOrOpen_e]) == key || seekEntryOrOpen_q != null && seekEntryOrOpen_q.equals(key))) {
                    n = seekEntryOrOpen_e;
                    break block7;
                }
                if (seekEntryOrOpen_o == -1 && seekEntryOrOpen_g + seekEntryOrOpen_g == 0) {
                    seekEntryOrOpen_o = seekEntryOrOpen_e;
                }
                seekEntryOrOpen_e = seekEntryOrOpen_e + 2 * (++seekEntryOrOpen_x + 1) * seekEntryOrOpen_x - 3 & this.scala$collection$mutable$AnyRefMap$$mask;
            }
            n = seekEntryOrOpen_o >= 0 ? seekEntryOrOpen_o | 0xC0000000 : seekEntryOrOpen_e | Integer.MIN_VALUE;
        }
        Object var11_8 = null;
        int i = n;
        if (i < 0) {
            int j = i & 0x3FFFFFFF;
            this.scala$collection$mutable$AnyRefMap$$_hashes[j] = h2;
            this.scala$collection$mutable$AnyRefMap$$_keys[j] = key;
            this.scala$collection$mutable$AnyRefMap$$_values[j] = value;
            ++this._size;
            if ((i & 0x40000000) != 0) {
                --this._vacant;
            } else if (this.imbalanced()) {
                this.repack();
            }
            return None$.MODULE$;
        }
        Some<Object> ans = new Some<Object>(this.scala$collection$mutable$AnyRefMap$$_values[i]);
        this.scala$collection$mutable$AnyRefMap$$_hashes[i] = h2;
        this.scala$collection$mutable$AnyRefMap$$_values[i] = value;
        return ans;
    }

    @Override
    public void update(K key, V value) {
        int n;
        int h2;
        block6: {
            int seekEntryOrOpen_g;
            h2 = this.hashOf(key);
            int seekEntryOrOpen_e = h2 & this.scala$collection$mutable$AnyRefMap$$mask;
            int seekEntryOrOpen_x = 0;
            int seekEntryOrOpen_o = -1;
            while ((seekEntryOrOpen_g = this.scala$collection$mutable$AnyRefMap$$_hashes[seekEntryOrOpen_e]) != 0) {
                Object seekEntryOrOpen_q;
                if (seekEntryOrOpen_g == h2 && ((seekEntryOrOpen_q = this.scala$collection$mutable$AnyRefMap$$_keys[seekEntryOrOpen_e]) == key || seekEntryOrOpen_q != null && seekEntryOrOpen_q.equals(key))) {
                    n = seekEntryOrOpen_e;
                    break block6;
                }
                if (seekEntryOrOpen_o == -1 && seekEntryOrOpen_g + seekEntryOrOpen_g == 0) {
                    seekEntryOrOpen_o = seekEntryOrOpen_e;
                }
                seekEntryOrOpen_e = seekEntryOrOpen_e + 2 * (++seekEntryOrOpen_x + 1) * seekEntryOrOpen_x - 3 & this.scala$collection$mutable$AnyRefMap$$mask;
            }
            n = seekEntryOrOpen_o >= 0 ? seekEntryOrOpen_o | 0xC0000000 : seekEntryOrOpen_e | Integer.MIN_VALUE;
        }
        Object var10_8 = null;
        int i = n;
        if (i < 0) {
            int j = i & 0x3FFFFFFF;
            this.scala$collection$mutable$AnyRefMap$$_hashes[j] = h2;
            this.scala$collection$mutable$AnyRefMap$$_keys[j] = key;
            this.scala$collection$mutable$AnyRefMap$$_values[j] = value;
            ++this._size;
            if ((i & 0x40000000) != 0) {
                --this._vacant;
                return;
            }
            if (this.imbalanced()) {
                this.repack();
                return;
            }
            return;
        }
        this.scala$collection$mutable$AnyRefMap$$_hashes[i] = h2;
        this.scala$collection$mutable$AnyRefMap$$_values[i] = value;
    }

    public AnyRefMap<K, V> $plus$eq(K key, V value) {
        this.update(key, value);
        return this;
    }

    public final AnyRefMap<K, V> addOne(K key, V value) {
        this.update(key, value);
        return this;
    }

    public final AnyRefMap<K, V> addOne(Tuple2<K, V> kv) {
        this.update(kv._1(), kv._2());
        return this;
    }

    public AnyRefMap<K, V> subtractOne(K key) {
        int i = this.seekEntry(this.hashOf(key), key);
        if (i >= 0) {
            --this._size;
            ++this._vacant;
            this.scala$collection$mutable$AnyRefMap$$_hashes[i] = Integer.MIN_VALUE;
            this.scala$collection$mutable$AnyRefMap$$_keys[i] = null;
            this.scala$collection$mutable$AnyRefMap$$_values[i] = null;
        }
        return this;
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        return new AnyRefMapIterator<Tuple2<K, V>>(this){

            public Tuple2<K, V> nextResult(K k, V v) {
                return new Tuple2<K, V>(k, v);
            }
        };
    }

    @Override
    public Iterator<K> keysIterator() {
        return new AnyRefMapIterator<K>(this){

            public K nextResult(K k, V v) {
                return k;
            }
        };
    }

    @Override
    public Iterator<V> valuesIterator() {
        return new AnyRefMapIterator<V>(this){

            public V nextResult(K k, V v) {
                return v;
            }
        };
    }

    @Override
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        int i = 0;
        for (int e = this._size; e > 0; --e) {
            int h2;
            while (i < this.scala$collection$mutable$AnyRefMap$$_hashes.length && (h2 = this.scala$collection$mutable$AnyRefMap$$_hashes[i]) + h2 == 0 && i < this.scala$collection$mutable$AnyRefMap$$_hashes.length) {
                ++i;
            }
            if (i < this.scala$collection$mutable$AnyRefMap$$_hashes.length) {
                f.apply(new Tuple2<Object, Object>(this.scala$collection$mutable$AnyRefMap$$_keys[i], this.scala$collection$mutable$AnyRefMap$$_values[i]));
                ++i;
                continue;
            }
            return;
        }
    }

    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        int i = 0;
        for (int e = this._size; e > 0; --e) {
            int h2;
            while (i < this.scala$collection$mutable$AnyRefMap$$_hashes.length && (h2 = this.scala$collection$mutable$AnyRefMap$$_hashes[i]) + h2 == 0 && i < this.scala$collection$mutable$AnyRefMap$$_hashes.length) {
                ++i;
            }
            if (i < this.scala$collection$mutable$AnyRefMap$$_hashes.length) {
                f.apply(this.scala$collection$mutable$AnyRefMap$$_keys[i], this.scala$collection$mutable$AnyRefMap$$_values[i]);
                ++i;
                continue;
            }
            return;
        }
    }

    @Override
    public AnyRefMap<K, V> clone() {
        int[] hz = Arrays.copyOf(this.scala$collection$mutable$AnyRefMap$$_hashes, this.scala$collection$mutable$AnyRefMap$$_hashes.length);
        Object[] kz = Arrays.copyOf(this.scala$collection$mutable$AnyRefMap$$_keys, this.scala$collection$mutable$AnyRefMap$$_keys.length);
        Object[] vz = Arrays.copyOf(this.scala$collection$mutable$AnyRefMap$$_values, this.scala$collection$mutable$AnyRefMap$$_values.length);
        AnyRefMap<K, V> arm = new AnyRefMap<K, V>(this.defaultEntry, 1, false);
        arm.initializeTo(this.scala$collection$mutable$AnyRefMap$$mask, this._size, this._vacant, hz, kz, vz);
        return arm;
    }

    @Override
    public <V1> AnyRefMap<K, V1> $plus(Tuple2<K, V1> kv) {
        return AnyRefMap$.MODULE$.from(new View.Appended(this, kv));
    }

    @Override
    public <V1> AnyRefMap<K, V1> $plus(Tuple2<K, V1> elem1, Tuple2<K, V1> elem2, Seq<Tuple2<K, V1>> elems) {
        IterableOps m4 = ((AnyRefMap)this.$plus((Tuple2)elem1)).$plus((Tuple2)elem2);
        if (elems.isEmpty()) {
            return m4;
        }
        return ((AnyRefMap)m4).concat(elems);
    }

    @Override
    public <V2> AnyRefMap<K, V2> concat(IterableOnce<Tuple2<K, V2>> xs) {
        Object arm = this.clone();
        xs.iterator().foreach(arg_0 -> AnyRefMap.$anonfun$concat$1((AnyRefMap)arm, arg_0));
        return arm;
    }

    @Override
    public <V2> AnyRefMap<K, V2> $plus$plus(IterableOnce<Tuple2<K, V2>> xs) {
        return this.concat((IterableOnce)xs);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <V1> AnyRefMap<K, V1> updated(K key, V1 value) {
        void var3_3;
        Object object = this.clone();
        if (object == null) {
            throw null;
        }
        Object addOne_this = object;
        ((AnyRefMap)addOne_this).update(key, value);
        return var3_3;
    }

    private <A, B> void foreachElement(Object[] elems, Function1<A, B> f) {
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$AnyRefMap$$_hashes.length & j < this._size) {
            int h2 = this.scala$collection$mutable$AnyRefMap$$_hashes[i];
            if (h2 + h2 != 0) {
                ++j;
                f.apply(elems[i]);
            }
            ++i;
        }
    }

    public <A> void foreachKey(Function1<K, A> f) {
        Object[] foreachElement_elems = this.scala$collection$mutable$AnyRefMap$$_keys;
        int foreachElement_i = 0;
        int foreachElement_j = 0;
        while (foreachElement_i < this.scala$collection$mutable$AnyRefMap$$_hashes.length & foreachElement_j < this._size) {
            int foreachElement_h = this.scala$collection$mutable$AnyRefMap$$_hashes[foreachElement_i];
            if (foreachElement_h + foreachElement_h != 0) {
                ++foreachElement_j;
                f.apply(foreachElement_elems[foreachElement_i]);
            }
            ++foreachElement_i;
        }
    }

    public <A> void foreachValue(Function1<V, A> f) {
        Object[] foreachElement_elems = this.scala$collection$mutable$AnyRefMap$$_values;
        int foreachElement_i = 0;
        int foreachElement_j = 0;
        while (foreachElement_i < this.scala$collection$mutable$AnyRefMap$$_hashes.length & foreachElement_j < this._size) {
            int foreachElement_h = this.scala$collection$mutable$AnyRefMap$$_hashes[foreachElement_i];
            if (foreachElement_h + foreachElement_h != 0) {
                ++foreachElement_j;
                f.apply(foreachElement_elems[foreachElement_i]);
            }
            ++foreachElement_i;
        }
    }

    public <V1> AnyRefMap<K, V1> mapValuesNow(Function1<V, V1> f) {
        AnyRefMap<Object, Nothing$> arm = new AnyRefMap<Object, Nothing$>(AnyRefMap$.MODULE$.scala$collection$mutable$AnyRefMap$$exceptionDefault(), 1, false);
        int[] hz = Arrays.copyOf(this.scala$collection$mutable$AnyRefMap$$_hashes, this.scala$collection$mutable$AnyRefMap$$_hashes.length);
        Object[] kz = Arrays.copyOf(this.scala$collection$mutable$AnyRefMap$$_keys, this.scala$collection$mutable$AnyRefMap$$_keys.length);
        Object[] vz = new Object[this.scala$collection$mutable$AnyRefMap$$_values.length];
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$AnyRefMap$$_hashes.length & j < this._size) {
            int h2 = this.scala$collection$mutable$AnyRefMap$$_hashes[i];
            if (h2 + h2 != 0) {
                ++j;
                vz[i] = f.apply(this.scala$collection$mutable$AnyRefMap$$_values[i]);
            }
            ++i;
        }
        arm.initializeTo(this.scala$collection$mutable$AnyRefMap$$mask, this._size, this._vacant, hz, kz, vz);
        return arm;
    }

    public final AnyRefMap<K, V> transformValues(Function1<V, V> f) {
        return this.transformValuesInPlace(f);
    }

    public AnyRefMap<K, V> transformValuesInPlace(Function1<V, V> f) {
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$AnyRefMap$$_hashes.length & j < this._size) {
            int h2 = this.scala$collection$mutable$AnyRefMap$$_hashes[i];
            if (h2 + h2 != 0) {
                ++j;
                this.scala$collection$mutable$AnyRefMap$$_values[i] = f.apply(this.scala$collection$mutable$AnyRefMap$$_values[i]);
            }
            ++i;
        }
        return this;
    }

    public <K2, V2> AnyRefMap<K2, V2> map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f, DummyImplicit dummy) {
        return AnyRefMap$.MODULE$.from(new View.Map<Tuple2<K, V>, Tuple2<K2, V2>>(this, f));
    }

    public <K2, V2> AnyRefMap<K2, V2> flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f, DummyImplicit dummy) {
        return AnyRefMap$.MODULE$.from(new View.FlatMap<Tuple2<K, V>, Tuple2<K2, V2>>(this, f));
    }

    public <K2, V2> AnyRefMap<K2, V2> collect(PartialFunction<Tuple2<K, V>, Tuple2<K2, V2>> pf, DummyImplicit dummy) {
        AnyRefMapBuilder strictOptimizedCollect_b = new AnyRefMapBuilder();
        Object strictOptimizedCollect_marker = Statics.pfMarker;
        Iterator strictOptimizedCollect_it = this.iterator();
        while (strictOptimizedCollect_it.hasNext()) {
            Object strictOptimizedCollect_elem = strictOptimizedCollect_it.next();
            Object strictOptimizedCollect_v = pf.applyOrElse(strictOptimizedCollect_elem, arg_0 -> StrictOptimizedIterableOps.$anonfun$strictOptimizedCollect$1(strictOptimizedCollect_marker, arg_0));
            if (strictOptimizedCollect_marker == strictOptimizedCollect_v) continue;
            strictOptimizedCollect_b.addOne((Tuple2)strictOptimizedCollect_v);
        }
        return strictOptimizedCollect_b.elems();
    }

    @Override
    public void clear() {
        Arrays.fill(this.scala$collection$mutable$AnyRefMap$$_keys, null);
        Arrays.fill(this.scala$collection$mutable$AnyRefMap$$_values, null);
        Arrays.fill(this.scala$collection$mutable$AnyRefMap$$_hashes, 0);
        this._size = 0;
        this._vacant = 0;
    }

    public Object writeReplace() {
        return new DefaultSerializationProxy<Tuple2<Object, Object>>(AnyRefMap$ToFactory$.MODULE$, this);
    }

    @Override
    public String stringPrefix() {
        return "AnyRefMap";
    }

    public static final /* synthetic */ void $anonfun$fromSpecific$1(AnyRefMap arm$1, Tuple2 x0$1) {
        if (x0$1 != null) {
            Object k = x0$1._1();
            Object v = x0$1._2();
            arm$1.update(k, v);
            return;
        }
        throw new MatchError((Object)null);
    }

    public static final /* synthetic */ AnyRefMap $anonfun$concat$1(AnyRefMap arm$2, Tuple2 kv) {
        return (AnyRefMap)arm$2.$plus$eq(kv);
    }

    public AnyRefMap(Function1<K, V> defaultEntry, int initialBufferSize, boolean initBlank) {
        this.defaultEntry = defaultEntry;
        this.scala$collection$mutable$AnyRefMap$$mask = 0;
        this._size = 0;
        this._vacant = 0;
        this.scala$collection$mutable$AnyRefMap$$_hashes = null;
        this.scala$collection$mutable$AnyRefMap$$_keys = null;
        this.scala$collection$mutable$AnyRefMap$$_values = null;
        if (initBlank) {
            this.defaultInitialize(initialBufferSize);
        }
    }

    public AnyRefMap() {
        this(AnyRefMap$.MODULE$.scala$collection$mutable$AnyRefMap$$exceptionDefault(), 16, true);
    }

    public AnyRefMap(Function1<K, V> defaultEntry) {
        this(defaultEntry, 16, true);
    }

    public AnyRefMap(int initialBufferSize) {
        this(AnyRefMap$.MODULE$.scala$collection$mutable$AnyRefMap$$exceptionDefault(), initialBufferSize, true);
    }

    public AnyRefMap(Function1<K, V> defaultEntry, int initialBufferSize) {
        this(defaultEntry, initialBufferSize, true);
    }

    public static final class AnyRefMapBuilder<K, V>
    implements ReusableBuilder<Tuple2<K, V>, AnyRefMap<K, V>> {
        private AnyRefMap<K, V> elems = new AnyRefMap();

        @Override
        public void sizeHint(int size) {
            Builder.sizeHint$(this, size);
        }

        @Override
        public final void sizeHint(IterableOnce<?> coll, int delta) {
            Builder.sizeHint$(this, coll, delta);
        }

        @Override
        public final int sizeHint$default$2() {
            return Builder.sizeHint$default$2$(this);
        }

        @Override
        public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
            Builder.sizeHintBounded$(this, size, boundingColl);
        }

        @Override
        public <NewTo> Builder<Tuple2<K, V>, NewTo> mapResult(Function1<AnyRefMap<K, V>, NewTo> f) {
            return Builder.mapResult$(this, f);
        }

        @Override
        public final Growable $plus$eq(Object elem) {
            return Growable.$plus$eq$(this, elem);
        }

        @Override
        public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
            return Growable.$plus$eq$(this, elem1, elem2, elems);
        }

        @Override
        public Growable<Tuple2<K, V>> addAll(IterableOnce<Tuple2<K, V>> xs) {
            return Growable.addAll$(this, xs);
        }

        @Override
        public final Growable<Tuple2<K, V>> $plus$plus$eq(IterableOnce<Tuple2<K, V>> xs) {
            return Growable.$plus$plus$eq$(this, xs);
        }

        public AnyRefMap<K, V> elems() {
            return this.elems;
        }

        public void elems_$eq(AnyRefMap<K, V> x$1) {
            this.elems = x$1;
        }

        public AnyRefMapBuilder<K, V> addOne(Tuple2<K, V> entry) {
            AnyRefMap<K, V> anyRefMap = this.elems();
            if (anyRefMap == null) {
                throw null;
            }
            anyRefMap.addOne(entry);
            return this;
        }

        @Override
        public void clear() {
            this.elems_$eq(new AnyRefMap());
        }

        @Override
        public AnyRefMap<K, V> result() {
            return this.elems();
        }

        @Override
        public int knownSize() {
            return this.elems().knownSize();
        }
    }

    private abstract class AnyRefMapIterator<A>
    extends AbstractIterator<A> {
        private final int[] hz;
        private final Object[] kz;
        private final Object[] vz;
        private int index;

        @Override
        public boolean hasNext() {
            if (this.index < this.hz.length) {
                int h2 = this.hz[this.index];
                while (h2 + h2 == 0) {
                    ++this.index;
                    if (this.index >= this.hz.length) {
                        return false;
                    }
                    h2 = this.hz[this.index];
                }
                return true;
            }
            return false;
        }

        @Override
        public A next() {
            if (this.hasNext()) {
                A ans = this.nextResult(this.kz[this.index], this.vz[this.index]);
                ++this.index;
                return ans;
            }
            throw new NoSuchElementException("next");
        }

        public abstract A nextResult(K var1, V var2);

        public /* synthetic */ AnyRefMap scala$collection$mutable$AnyRefMap$AnyRefMapIterator$$$outer() {
            return AnyRefMap.this;
        }

        public AnyRefMapIterator() {
            if (AnyRefMap.this == null) {
                throw null;
            }
            this.hz = AnyRefMap.this.scala$collection$mutable$AnyRefMap$$_hashes;
            this.kz = AnyRefMap.this.scala$collection$mutable$AnyRefMap$$_keys;
            this.vz = AnyRefMap.this.scala$collection$mutable$AnyRefMap$$_values;
            this.index = 0;
        }
    }

    private static class ExceptionDefault
    implements Function1<Object, Nothing$>,
    Serializable {
        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, Nothing$> compose(Function1<A$, Object> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public <A$> Function1<Object, A$> andThen(Function1<Nothing$, A$> g2) {
            return Function1.andThen$(this, g2);
        }

        @Override
        public String toString() {
            return Function1.toString$(this);
        }

        @Override
        public Nothing$ apply(Object k) {
            throw new NoSuchElementException(k == null ? "(null)" : k.toString());
        }
    }
}

