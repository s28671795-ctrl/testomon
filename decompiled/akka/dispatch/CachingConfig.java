/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.CachingConfig$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigMemorySize;
import com.typesafe.config.ConfigMergeable;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigOrigin;
import com.typesafe.config.ConfigResolveOptions;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;
import java.io.Serializable;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import scala.Function0;
import scala.MatchError;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.Try$;

@ScalaSignature(bytes="\u0006\u0005\u0015]sACA\r\u00037A\t!a\b\u0002$\u0019Q\u0011qEA\u000e\u0011\u0003\ty\"!\u000b\t\u000f\u0005]\u0012\u0001\"\u0001\u0002<!I\u0011QH\u0001C\u0002\u0013\u0005\u0011q\b\u0005\t\u0003+\n\u0001\u0015!\u0003\u0002B\u0019I\u0011qK\u0001\u0011\u0002G\u0005\u0012\u0011\f\u0005\n\u00037*!\u0019!D\u0001\u0003;B\u0011\"!\u001a\u0006\u0005\u00045\t!!\u0018\t\u0013\u0005\u001dSA1A\u0007\u0002\u0005}bA\u0002B\u001f\u0003\t\u0013y\u0004\u0003\u0006\u0002\\%\u0011)\u001a!C\u0001\u0003;B!\"a$\n\u0005#\u0005\u000b\u0011BA0\u0011)\t)'\u0003BK\u0002\u0013\u0005\u0011Q\f\u0005\u000b\u0003#K!\u0011#Q\u0001\n\u0005}\u0003BCA$\u0013\tU\r\u0011\"\u0001\u0002@!Q\u00111S\u0005\u0003\u0012\u0003\u0006I!!\u0011\t\u000f\u0005]\u0012\u0002\"\u0001\u0003B!I\u0011qW\u0005\u0002\u0002\u0013\u0005!1\n\u0005\n\u0003\u0007L\u0011\u0013!C\u0001\u0003\u000bD\u0011\"a7\n#\u0003%\t!!2\t\u0013\u0005u\u0017\"%A\u0005\u0002\u0005}\u0007\"CAu\u0013\u0005\u0005I\u0011IAv\u0011%\tY0CA\u0001\n\u0003\ti\u0010C\u0005\u0003\u0006%\t\t\u0011\"\u0001\u0003T!I!1C\u0005\u0002\u0002\u0013\u0005#Q\u0003\u0005\n\u0005GI\u0011\u0011!C\u0001\u0005/B\u0011B!\u000b\n\u0003\u0003%\tEa\u0017\t\u0013\t=\u0012\"!A\u0005B\tE\u0002\"\u0003B\u001a\u0013\u0005\u0005I\u0011\tB\u001b\u0011%\u00119$CA\u0001\n\u0003\u0012yfB\u0005\u0003d\u0005\t\t\u0011#\u0001\u0003f\u0019I!QH\u0001\u0002\u0002#\u0005!q\r\u0005\b\u0003oyB\u0011\u0001B@\u0011%\u0011\u0019dHA\u0001\n\u000b\u0012)\u0004C\u0005\u0003\u0002~\t\t\u0011\"!\u0003\u0004\"I!1R\u0010\u0012\u0002\u0013\u0005\u0011q\u001c\u0005\n\u0005\u001b{\u0012\u0011!CA\u0005\u001fC\u0011B!) #\u0003%\t!a8\t\u0013\t\rv$!A\u0005\n\t\u0015fABA5\u0003\t\u000bY\u0007\u0003\u0006\u0002\\\u001d\u0012)\u001a!C\u0001\u0003;B!\"a$(\u0005#\u0005\u000b\u0011BA0\u0011)\t)g\nBK\u0002\u0013\u0005\u0011Q\f\u0005\u000b\u0003#;#\u0011#Q\u0001\n\u0005}\u0003BCA$O\tU\r\u0011\"\u0001\u0002@!Q\u00111S\u0014\u0003\u0012\u0003\u0006I!!\u0011\t\u0015\u0005UuE!f\u0001\n\u0003\t9\n\u0003\u0006\u0002*\u001e\u0012\t\u0012)A\u0005\u00033Cq!a\u000e(\t\u0003\tY\u000bC\u0005\u00028\u001e\n\t\u0011\"\u0001\u0002:\"I\u00111Y\u0014\u0012\u0002\u0013\u0005\u0011Q\u0019\u0005\n\u00037<\u0013\u0013!C\u0001\u0003\u000bD\u0011\"!8(#\u0003%\t!a8\t\u0013\u0005\rx%%A\u0005\u0002\u0005\u0015\b\"CAuO\u0005\u0005I\u0011IAv\u0011%\tYpJA\u0001\n\u0003\ti\u0010C\u0005\u0003\u0006\u001d\n\t\u0011\"\u0001\u0003\b!I!1C\u0014\u0002\u0002\u0013\u0005#Q\u0003\u0005\n\u0005G9\u0013\u0011!C\u0001\u0005KA\u0011B!\u000b(\u0003\u0003%\tEa\u000b\t\u0013\t=r%!A\u0005B\tE\u0002\"\u0003B\u001aO\u0005\u0005I\u0011\tB\u001b\u0011%\u00119dJA\u0001\n\u0003\u0012IdB\u0005\u0003.\u0006\t\t\u0011#\u0001\u00030\u001aI\u0011\u0011N\u0001\u0002\u0002#\u0005!\u0011\u0017\u0005\b\u0003o\u0001E\u0011\u0001B]\u0011%\u0011\u0019\u0004QA\u0001\n\u000b\u0012)\u0004C\u0005\u0003\u0002\u0002\u000b\t\u0011\"!\u0003<\"I!Q\u0012!\u0002\u0002\u0013\u0005%Q\u0019\u0005\n\u0005G\u0003\u0015\u0011!C\u0005\u0005KC\u0011B!5\u0002\u0005\u0004%\tAa5\t\u0011\tU\u0017\u0001)A\u0005\u0005\u0007B\u0011Ba6\u0002\u0005\u0004%\tAa5\t\u0011\te\u0017\u0001)A\u0005\u0005\u0007B\u0011Ba7\u0002\u0005\u0004%\tAa5\t\u0011\tu\u0017\u0001)A\u0005\u0005\u00072\u0011\"a\n\u0002\u001c\u0001\tyBa8\t\u0015\t\u0005HJ!A!\u0002\u0013\t\t\u0005C\u0004\u000281#\tAa9\t\u0019\tEA\n%A\u0001\u0004\u0003\u0006IA!;\t\u0013\u0005\u001dCJ1A\u0005\n\u0005}\u0002\u0002CAJ\u0019\u0002\u0006I!!\u0011\t\u0013\r\rAJ1A\u0005\n\r\u0015\u0001\u0002CB\u0004\u0019\u0002\u0006IAa<\t\u000f\r%A\n\"\u0003\u0004\f!91\u0011\u0003'\u0005\u0002\rM\u0001bBB\u0015\u0019\u0012\u000511\u0006\u0005\b\u0007gaE\u0011AB\u001b\u0011\u001d\u0019i\u0004\u0014C\u0001\u0007\u007fAqaa\u0013M\t\u0003\u0019i\u0005C\u0004\u0004L1#\taa\u0014\t\u000f\rmC\n\"\u0001\u0004^!91\u0011\r'\u0005\u0002\r\r\u0004bBB4\u0019\u0012\u00051\u0011\u000e\u0005\b\u0007WbE\u0011AB7\u0011\u001d\u0019I\t\u0014C\u0001\u0007\u0017Cqaa$M\t\u0003\u0019\t\nC\u0004\u0004\u001c2#\ta!(\t\u000f\r\u0005F\n\"\u0001\u0004$\"91Q\u0016'\u0005\u0002\r=\u0006bBB]\u0019\u0012\u000511\u0018\u0005\b\u0007\u007fcE\u0011ABa\u0011\u001d\u0019)\r\u0014C\u0001\u0007\u000fDqaa3M\t\u0003\u0019i\rC\u0004\u0004T2#\ta!6\t\u000f\reG\n\"\u0001\u0004\\\"911\u001d'\u0005\u0002\r\u0015\bbBBu\u0019\u0012\u000511\u001e\u0005\b\u0007_dE\u0011ABy\u0011\u001d\u0019Y\u0010\u0014C\u0001\u0007{Dq\u0001b\u0003M\t\u0003!i\u0001C\u0004\u0005\u00141#\t\u0001\"\u0006\t\u000f\u0011\u0005B\n\"\u0001\u0005$!9A\u0011\u0006'\u0005\u0002\u0011-\u0002b\u0002C\u001b\u0019\u0012\u0005Aq\u0007\u0005\b\t{aE\u0011\u0001C \u0011\u001d!9\u0006\u0014C\u0001\t3Bq\u0001b\u001aM\t\u0003!I\u0007C\u0004\u0005x1#\t\u0001\"\u001f\t\u000f\u0011uD\n\"\u0001\u0005\u0000!9A1\u0011'\u0005\u0002\u0011\u0015\u0005b\u0002CE\u0019\u0012\u0005A1\u0012\u0005\b\t\u001fcE\u0011\u0001CI\u0011\u001d!)\n\u0014C\u0001\t/Cq\u0001b'M\t\u0003!i\nC\u0004\u0005$2#\t\u0001\"*\t\u000f\u0011-F\n\"\u0001\u0005.\"9A1\u0018'\u0005\u0002\u0011u\u0006b\u0002CV\u0019\u0012\u0005A1\u0019\u0005\b\twcE\u0011\u0001Cj\u0011\u001d!I\u000e\u0014C\u0001\t7Dq\u0001\":M\t\u0003!9\u000fC\u0004\u0005x2#\t\u0001\"?\t\u000f\u0011uH\n\"\u0001\u0005\u0000\"9Q\u0011\u0002'\u0005\u0002\u0015-\u0001bBC\t\u0019\u0012\u00051\u0011\u000e\u0005\b\u000b'aE\u0011AC\u000b\u0011\u001d)\u0019\u0002\u0014C\u0001\u000b;Aq!\"\tM\t\u0003*\u0019\u0003C\u0004\u0006D1#\t%\"\u0012\u0002\u001b\r\u000b7\r[5oO\u000e{gNZ5h\u0015\u0011\ti\"a\b\u0002\u0011\u0011L7\u000f]1uG\"T!!!\t\u0002\t\u0005\\7.\u0019\t\u0004\u0003K\tQBAA\u000e\u00055\u0019\u0015m\u00195j]\u001e\u001cuN\u001c4jON\u0019\u0011!a\u000b\u0011\t\u00055\u00121G\u0007\u0003\u0003_Q!!!\r\u0002\u000bM\u001c\u0017\r\\1\n\t\u0005U\u0012q\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\"!a\t\u0002\u0017\u0015l\u0007\u000f^=D_:4\u0017nZ\u000b\u0003\u0003\u0003\u0002B!a\u0011\u0002R5\u0011\u0011Q\t\u0006\u0005\u0003\u000f\nI%\u0001\u0004d_:4\u0017n\u001a\u0006\u0005\u0003\u0017\ni%\u0001\u0005usB,7/\u00194f\u0015\t\ty%A\u0002d_6LA!a\u0015\u0002F\t11i\u001c8gS\u001e\fA\"Z7qif\u001cuN\u001c4jO\u0002\u0012\u0011\u0002U1uQ\u0016sGO]=\u0014\u0007\u0015\tY#A\u0003wC2LG-\u0006\u0002\u0002`A!\u0011QFA1\u0013\u0011\t\u0019'a\f\u0003\u000f\t{w\u000e\\3b]\u00061Q\r_5tiNL3!B\u0014\n\u0005=\u0019FO]5oOB\u000bG\u000f[#oiJL8#C\u0014\u0002,\u00055\u0014\u0011OA<!\r\ty'B\u0007\u0002\u0003A!\u0011QFA:\u0013\u0011\t)(a\f\u0003\u000fA\u0013x\u000eZ;diB!\u0011\u0011PAE\u001d\u0011\tY(!\"\u000f\t\u0005u\u00141Q\u0007\u0003\u0003\u007fRA!!!\u0002:\u00051AH]8pizJ!!!\r\n\t\u0005\u001d\u0015qF\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\tY)!$\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\t\u0005\u001d\u0015qF\u0001\u0007m\u0006d\u0017\u000e\u001a\u0011\u0002\u000f\u0015D\u0018n\u001d;tA\u000591m\u001c8gS\u001e\u0004\u0013!\u0002<bYV,WCAAM!\u0011\tY*a)\u000f\t\u0005u\u0015q\u0014\t\u0005\u0003{\ny#\u0003\u0003\u0002\"\u0006=\u0012A\u0002)sK\u0012,g-\u0003\u0003\u0002&\u0006\u001d&AB*ue&twM\u0003\u0003\u0002\"\u0006=\u0012A\u0002<bYV,\u0007\u0005\u0006\u0006\u0002.\u0006=\u0016\u0011WAZ\u0003k\u00032!a\u001c(\u0011\u001d\tY\u0006\ra\u0001\u0003?Bq!!\u001a1\u0001\u0004\ty\u0006C\u0004\u0002HA\u0002\r!!\u0011\t\u000f\u0005U\u0005\u00071\u0001\u0002\u001a\u0006!1m\u001c9z))\ti+a/\u0002>\u0006}\u0016\u0011\u0019\u0005\n\u00037\n\u0004\u0013!a\u0001\u0003?B\u0011\"!\u001a2!\u0003\u0005\r!a\u0018\t\u0013\u0005\u001d\u0013\u0007%AA\u0002\u0005\u0005\u0003\"CAKcA\u0005\t\u0019AAM\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!a2+\t\u0005}\u0013\u0011Z\u0016\u0003\u0003\u0017\u0004B!!4\u0002X6\u0011\u0011q\u001a\u0006\u0005\u0003#\f\u0019.A\u0005v]\u000eDWmY6fI*!\u0011Q[A\u0018\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u00033\fyMA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005\u0005(\u0006BA!\u0003\u0013\fabY8qs\u0012\"WMZ1vYR$C'\u0006\u0002\u0002h*\"\u0011\u0011TAe\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011Q\u001e\t\u0005\u0003_\fI0\u0004\u0002\u0002r*!\u00111_A{\u0003\u0011a\u0017M\\4\u000b\u0005\u0005]\u0018\u0001\u00026bm\u0006LA!!*\u0002r\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011q \t\u0005\u0003[\u0011\t!\u0003\u0003\u0003\u0004\u0005=\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003\u0002B\u0005\u0005\u001f\u0001B!!\f\u0003\f%!!QBA\u0018\u0005\r\te.\u001f\u0005\n\u0005#A\u0014\u0011!a\u0001\u0003\u007f\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B\f!\u0019\u0011IBa\b\u0003\n5\u0011!1\u0004\u0006\u0005\u0005;\ty#\u0001\u0006d_2dWm\u0019;j_:LAA!\t\u0003\u001c\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\tyFa\n\t\u0013\tE!(!AA\u0002\t%\u0011A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!<\u0003.!I!\u0011C\u001e\u0002\u0002\u0003\u0007\u0011q`\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011q`\u0001\ti>\u001cFO]5oOR\u0011\u0011Q^\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005}#1\b\u0005\n\u0005#q\u0014\u0011!a\u0001\u0005\u0013\u0011aBV1mk\u0016\u0004\u0016\r\u001e5F]R\u0014\u0018pE\u0005\n\u0003W\ti'!\u001d\u0002xQA!1\tB#\u0005\u000f\u0012I\u0005E\u0002\u0002p%Aq!a\u0017\u0011\u0001\u0004\ty\u0006C\u0004\u0002fA\u0001\r!a\u0018\t\u0013\u0005\u001d\u0003\u0003%AA\u0002\u0005\u0005C\u0003\u0003B\"\u0005\u001b\u0012yE!\u0015\t\u0013\u0005m\u0013\u0003%AA\u0002\u0005}\u0003\"CA3#A\u0005\t\u0019AA0\u0011%\t9%\u0005I\u0001\u0002\u0004\t\t\u0005\u0006\u0003\u0003\n\tU\u0003\"\u0003B\t/\u0005\u0005\t\u0019AA\u0000)\u0011\tyF!\u0017\t\u0013\tE\u0011$!AA\u0002\t%A\u0003BAw\u0005;B\u0011B!\u0005\u001b\u0003\u0003\u0005\r!a@\u0015\t\u0005}#\u0011\r\u0005\n\u0005#i\u0012\u0011!a\u0001\u0005\u0013\taBV1mk\u0016\u0004\u0016\r\u001e5F]R\u0014\u0018\u0010E\u0002\u0002p}\u0019Ra\bB5\u0005k\u0002BBa\u001b\u0003r\u0005}\u0013qLA!\u0005\u0007j!A!\u001c\u000b\t\t=\u0014qF\u0001\beVtG/[7f\u0013\u0011\u0011\u0019H!\u001c\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t7\u0007\u0005\u0003\u0003x\tuTB\u0001B=\u0015\u0011\u0011Y(!>\u0002\u0005%|\u0017\u0002BAF\u0005s\"\"A!\u001a\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0011\t\r#Q\u0011BD\u0005\u0013Cq!a\u0017#\u0001\u0004\ty\u0006C\u0004\u0002f\t\u0002\r!a\u0018\t\u0013\u0005\u001d#\u0005%AA\u0002\u0005\u0005\u0013aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0002\u000fUt\u0017\r\u001d9msR!!\u0011\u0013BO!\u0019\tiCa%\u0003\u0018&!!QSA\u0018\u0005\u0019y\u0005\u000f^5p]BQ\u0011Q\u0006BM\u0003?\ny&!\u0011\n\t\tm\u0015q\u0006\u0002\u0007)V\u0004H.Z\u001a\t\u0013\t}E%!AA\u0002\t\r\u0013a\u0001=%a\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa*\u0011\t\u0005=(\u0011V\u0005\u0005\u0005W\u000b\tP\u0001\u0004PE*,7\r^\u0001\u0010'R\u0014\u0018N\\4QCRDWI\u001c;ssB\u0019\u0011q\u000e!\u0014\u000b\u0001\u0013\u0019L!\u001e\u0011\u001d\t-$QWA0\u0003?\n\t%!'\u0002.&!!q\u0017B7\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g\u000e\u000e\u000b\u0003\u0005_#\"\"!,\u0003>\n}&\u0011\u0019Bb\u0011\u001d\tYf\u0011a\u0001\u0003?Bq!!\u001aD\u0001\u0004\ty\u0006C\u0004\u0002H\r\u0003\r!!\u0011\t\u000f\u0005U5\t1\u0001\u0002\u001aR!!q\u0019Bh!\u0019\tiCa%\u0003JBa\u0011Q\u0006Bf\u0003?\ny&!\u0011\u0002\u001a&!!QZA\u0018\u0005\u0019!V\u000f\u001d7fi!I!q\u0014#\u0002\u0002\u0003\u0007\u0011QV\u0001\u0011S:4\u0018\r\\5e!\u0006$\b.\u00128uef,\"Aa\u0011\u0002#%tg/\u00197jIB\u000bG\u000f[#oiJL\b%\u0001\u000bo_:,\u00050[:uS:<\u0007+\u0019;i\u000b:$(/_\u0001\u0016]>tW\t_5ti&tw\rU1uQ\u0016sGO]=!\u00039)W\u000e\u001d;z!\u0006$\b.\u00128uef\fq\"Z7qif\u0004\u0016\r\u001e5F]R\u0014\u0018\u0010I\n\u0006\u0019\n\u001d\u0016\u0011I\u0001\b?\u000e|gNZ5h)\u0011\u0011)Oa:\u0011\u0007\u0005\u0015B\nC\u0004\u0003b:\u0003\r!!\u0011\u0011\u0011\u00055\"1^A!\u0005_LAA!<\u00020\t1A+\u001e9mKJ\u0002\u0002B!=\u0003|\u0006e%q`\u0007\u0003\u0005gTAA!>\u0003x\u0006Q1m\u001c8dkJ\u0014XM\u001c;\u000b\t\te\u0018Q_\u0001\u0005kRLG.\u0003\u0003\u0003~\nM(!E\"p]\u000e,(O]3oi\"\u000b7\u000f['baB\u00191\u0011A\u0003\u000f\u0007\u0005\u0015\u0002!\u0001\u0005f]R\u0014\u00180T1q+\t\u0011y/A\u0005f]R\u0014\u00180T1qA\u0005aq-\u001a;QCRDWI\u001c;ssR!!q`B\u0007\u0011\u001d\u0019y\u0001\u0016a\u0001\u00033\u000bA\u0001]1uQ\u0006Q1\r[3dWZ\u000bG.\u001b3\u0015\r\rU11DB\u0010!\u0011\tica\u0006\n\t\re\u0011q\u0006\u0002\u0005+:LG\u000fC\u0004\u0004\u001eU\u0003\r!!\u0011\u0002\u0013I,g-\u001a:f]\u000e,\u0007bBB\u0011+\u0002\u000711E\u0001\u0010e\u0016\u001cHO]5diR{\u0007+\u0019;igB1\u0011QFB\u0013\u00033KAaa\n\u00020\tQAH]3qK\u0006$X\r\u001a \u0002\tI|w\u000e\u001e\u000b\u0003\u0007[\u0001B!a\u0011\u00040%!1\u0011GA#\u00051\u0019uN\u001c4jO>\u0013'.Z2u\u0003\u0019y'/[4j]R\u00111q\u0007\t\u0005\u0003\u0007\u001aI$\u0003\u0003\u0004<\u0005\u0015#\u0001D\"p]\u001aLwm\u0014:jO&t\u0017\u0001D<ji\"4\u0015\r\u001c7cC\u000e\\G\u0003\u0002Bs\u0007\u0003Bqaa\u0011Y\u0001\u0004\u0019)%A\u0003pi\",'\u000f\u0005\u0003\u0002D\r\u001d\u0013\u0002BB%\u0003\u000b\u0012qbQ8oM&<W*\u001a:hK\u0006\u0014G.Z\u0001\be\u0016\u001cx\u000e\u001c<f)\t\t\t\u0005\u0006\u0003\u0002B\rE\u0003bBB*5\u0002\u00071QK\u0001\b_B$\u0018n\u001c8t!\u0011\t\u0019ea\u0016\n\t\re\u0013Q\t\u0002\u0015\u0007>tg-[4SKN|GN^3PaRLwN\\:\u0002\u000f!\f7\u000fU1uQR!\u0011qLB0\u0011\u001d\u0019ya\u0017a\u0001\u00033\u000bQ\u0002[1t!\u0006$\bn\u0014:Ok2dG\u0003BA0\u0007KBqaa\u0004]\u0001\u0004\tI*A\u0004jg\u0016k\u0007\u000f^=\u0015\u0005\u0005}\u0013\u0001C3oiJL8+\u001a;\u0015\u0005\r=\u0004CBB9\u0007g\u001a9(\u0004\u0002\u0003x&!1Q\u000fB|\u0005\r\u0019V\r\u001e\t\t\u0007s\u001ay(!<\u0004\u00046\u001111\u0010\u0006\u0005\u0007{\u001290A\u0002NCBLAa!!\u0004|\t)QI\u001c;ssB!\u00111IBC\u0013\u0011\u00199)!\u0012\u0003\u0017\r{gNZ5h-\u0006dW/Z\u0001\u000bO\u0016$(i\\8mK\u0006tG\u0003BA0\u0007\u001bCqaa\u0004`\u0001\u0004\tI*A\u0005hKRtU/\u001c2feR!11SBM!\u0011\tyo!&\n\t\r]\u0015\u0011\u001f\u0002\u0007\u001dVl'-\u001a:\t\u000f\r=\u0001\r1\u0001\u0002\u001a\u00061q-\u001a;J]R$B!a@\u0004 \"91qB1A\u0002\u0005e\u0015aB4fi2{gn\u001a\u000b\u0005\u0007K\u001bY\u000b\u0005\u0003\u0002.\r\u001d\u0016\u0002BBU\u0003_\u0011A\u0001T8oO\"91q\u00022A\u0002\u0005e\u0015!C4fi\u0012{WO\u00197f)\u0011\u0019\tla.\u0011\t\u0005521W\u0005\u0005\u0007k\u000byC\u0001\u0004E_V\u0014G.\u001a\u0005\b\u0007\u001f\u0019\u0007\u0019AAM\u0003%9W\r^*ue&tw\r\u0006\u0003\u0002n\u000eu\u0006bBB\bI\u0002\u0007\u0011\u0011T\u0001\nO\u0016$xJ\u00196fGR$Ba!\f\u0004D\"91qB3A\u0002\u0005e\u0015!C4fi\u000e{gNZ5h)\u0011\t\te!3\t\u000f\r=a\r1\u0001\u0002\u001a\u0006Iq-\u001a;B]f\u0014VM\u001a\u000b\u0005\u0007\u001f\u001c\t\u000e\u0005\u0003\u0002p\n%\u0006bBB\bO\u0002\u0007\u0011\u0011T\u0001\tO\u0016$h+\u00197vKR!11QBl\u0011\u001d\u0019y\u0001\u001ba\u0001\u00033\u000b\u0001bZ3u\u0005f$Xm\u001d\u000b\u0005\u0007;\u001c\t\u000f\u0005\u0003\u0002p\u000e}\u0017\u0002BBU\u0003cDqaa\u0004j\u0001\u0004\tI*A\bhKRl\u0015\u000e\u001c7jg\u0016\u001cwN\u001c3t)\u0011\u0019ina:\t\u000f\r=!\u000e1\u0001\u0002\u001a\u0006qq-\u001a;OC:|7/Z2p]\u0012\u001cH\u0003BBo\u0007[Dqaa\u0004l\u0001\u0004\tI*A\u0004hKRd\u0015n\u001d;\u0015\t\rM8\u0011 \t\u0005\u0003\u0007\u001a)0\u0003\u0003\u0004x\u0006\u0015#AC\"p]\u001aLw\rT5ti\"91q\u00027A\u0002\u0005e\u0015AD4fi\n{w\u000e\\3b]2K7\u000f\u001e\u000b\u0005\u0007\u007f$I\u0001\u0005\u0004\u0004r\u0011\u0005AQA\u0005\u0005\t\u0007\u00119P\u0001\u0003MSN$\b\u0003BAx\t\u000fIA!a\u0019\u0002r\"91qB7A\u0002\u0005e\u0015!D4fi:+XNY3s\u0019&\u001cH\u000f\u0006\u0003\u0005\u0010\u0011E\u0001CBB9\t\u0003\u0019\u0019\nC\u0004\u0004\u00109\u0004\r!!'\u0002\u0015\u001d,G/\u00138u\u0019&\u001cH\u000f\u0006\u0003\u0005\u0018\u0011}\u0001CBB9\t\u0003!I\u0002\u0005\u0003\u0002p\u0012m\u0011\u0002\u0002C\u000f\u0003c\u0014q!\u00138uK\u001e,'\u000fC\u0004\u0004\u0010=\u0004\r!!'\u0002\u0017\u001d,G\u000fT8oO2K7\u000f\u001e\u000b\u0005\tK!9\u0003\u0005\u0004\u0004r\u0011\u00051Q\u001c\u0005\b\u0007\u001f\u0001\b\u0019AAM\u000359W\r\u001e#pk\ndW\rT5tiR!AQ\u0006C\u001a!\u0019\u0019\t\b\"\u0001\u00050A!\u0011q\u001eC\u0019\u0013\u0011\u0019),!=\t\u000f\r=\u0011\u000f1\u0001\u0002\u001a\u0006iq-\u001a;TiJLgn\u001a'jgR$B\u0001\"\u000f\u0005<A11\u0011\u000fC\u0001\u0003[Dqaa\u0004s\u0001\u0004\tI*A\u0007hKR|%M[3di2K7\u000f\u001e\u000b\u0005\t\u0003\")\u0006\r\u0003\u0005D\u0011%\u0003CBB9\t\u0003!)\u0005\u0005\u0003\u0005H\u0011%C\u0002\u0001\u0003\f\t\u0017\u001a\u0018\u0011!A\u0001\u0006\u0003!iE\u0001\u0002@aE!AqJB\u0017!\u0011\ti\u0003\"\u0015\n\t\u0011M\u0013q\u0006\u0002\b\u001d>$\b.\u001b8h\u0011\u001d\u0019ya\u001da\u0001\u00033\u000bQbZ3u\u0007>tg-[4MSN$H\u0003\u0002C.\tK\u0002D\u0001\"\u0018\u0005bA11\u0011\u000fC\u0001\t?\u0002B\u0001b\u0012\u0005b\u0011YA1\n;\u0002\u0002\u0003\u0005)\u0011\u0001C2#\u0011!y%!\u0011\t\u000f\r=A\u000f1\u0001\u0002\u001a\u0006iq-\u001a;B]f\u0014VM\u001a'jgR$B\u0001b\u001b\u0005vA\"AQ\u000eC9!\u0019\u0019\t\b\"\u0001\u0005pA!Aq\tC9\t-!Y%^A\u0001\u0002\u0003\u0015\t\u0001b\u001d\u0012\t\u0011=3q\u001a\u0005\b\u0007\u001f)\b\u0019AAM\u000319W\r\u001e\"zi\u0016\u001cH*[:u)\u0011!)\u0003b\u001f\t\u000f\r=a\u000f1\u0001\u0002\u001a\u0006\u0019r-\u001a;NS2d\u0017n]3d_:$7\u000fT5tiR!AQ\u0005CA\u0011\u001d\u0019ya\u001ea\u0001\u00033\u000b!cZ3u\u001d\u0006twn]3d_:$7\u000fT5tiR!AQ\u0005CD\u0011\u001d\u0019y\u0001\u001fa\u0001\u00033\u000bAb^5uQ>sG.\u001f)bi\"$BA!:\u0005\u000e\"91qB=A\u0002\u0005e\u0015aC<ji\"|W\u000f\u001e)bi\"$BA!:\u0005\u0014\"91q\u0002>A\u0002\u0005e\u0015AB1u!\u0006$\b\u000e\u0006\u0003\u0003f\u0012e\u0005bBB\bw\u0002\u0007\u0011\u0011T\u0001\u0006CR\\U-\u001f\u000b\u0005\u0005K$y\nC\u0004\u0005\"r\u0004\r!!'\u0002\u0007-,\u00170A\u0005xSRDg+\u00197vKR1!Q\u001dCT\tSCqaa\u0004~\u0001\u0004\tI\nC\u0004\u0002\u0016v\u0004\raa!\u0002\u0017\u001d,G\u000fR;sCRLwN\u001c\u000b\u0007\u0007K#y\u000b\"-\t\u000f\r=a\u00101\u0001\u0002\u001a\"9A1\u0017@A\u0002\u0011U\u0016\u0001B;oSR\u0004BA!=\u00058&!A\u0011\u0018Bz\u0005!!\u0016.\\3V]&$\u0018aD4fi\u0012+(/\u0019;j_:d\u0015n\u001d;\u0015\r\u0011\u0015Bq\u0018Ca\u0011\u001d\u0019ya a\u0001\u00033Cq\u0001b-\u0000\u0001\u0004!)\f\u0006\u0003\u0005F\u0012E\u0007\u0003\u0002Cd\t\u001bl!\u0001\"3\u000b\t\u0011-\u0017Q_\u0001\u0005i&lW-\u0003\u0003\u0005P\u0012%'\u0001\u0003#ve\u0006$\u0018n\u001c8\t\u0011\r=\u0011\u0011\u0001a\u0001\u00033#B\u0001\"6\u0005XB11\u0011\u000fC\u0001\t\u000bD\u0001ba\u0004\u0002\u0004\u0001\u0007\u0011\u0011T\u0001\nO\u0016$\b+\u001a:j_\u0012$B\u0001\"8\u0005dB!Aq\u0019Cp\u0013\u0011!\t\u000f\"3\u0003\rA+'/[8e\u0011!\u0019y!!\u0002A\u0002\u0005e\u0015aC4fiR+W\u000e]8sC2$B\u0001\";\u0005vB!A1\u001eCy\u001b\t!iO\u0003\u0003\u0005p\u0012%\u0017\u0001\u0003;f[B|'/\u00197\n\t\u0011MHQ\u001e\u0002\u000f)\u0016l\u0007o\u001c:bY\u0006kw.\u001e8u\u0011!\u0019y!a\u0002A\u0002\u0005e\u0015!C4fi&\u001bh*\u001e7m)\u0011\ty\u0006b?\t\u0011\r=\u0011\u0011\u0002a\u0001\u00033\u000bQbZ3u\u001b\u0016lwN]=TSj,G\u0003BC\u0001\u000b\u000f\u0001B!a\u0011\u0006\u0004%!QQAA#\u0005A\u0019uN\u001c4jO6+Wn\u001c:z'&TX\r\u0003\u0005\u0004\u0010\u0005-\u0001\u0019AAM\u0003E9W\r^'f[>\u0014\u0018pU5{K2K7\u000f\u001e\u000b\u0005\u000b\u001b)y\u0001\u0005\u0004\u0004r\u0011\u0005Q\u0011\u0001\u0005\t\u0007\u001f\ti\u00011\u0001\u0002\u001a\u0006Q\u0011n\u001d*fg>dg/\u001a3\u0002\u0017I,7o\u001c7wK^KG\u000f\u001b\u000b\u0007\u0003\u0003*9\"b\u0007\t\u0011\u0015e\u0011\u0011\u0003a\u0001\u0003\u0003\naa]8ve\u000e,\u0007\u0002CB*\u0003#\u0001\ra!\u0016\u0015\t\u0005\u0005Sq\u0004\u0005\t\u000b3\t\u0019\u00021\u0001\u0002B\u0005Yq-\u001a;F]VlG*[:u+\u0011))#b\u000b\u0015\r\u0015\u001dRqGC!!\u0019\u0019\t\b\"\u0001\u0006*A!AqIC\u0016\t!)i#!\u0006C\u0002\u0015=\"!\u0001+\u0012\t\u0011=S\u0011\u0007\t\u0007\u0003_,\u0019$\"\u000b\n\t\u0015U\u0012\u0011\u001f\u0002\u0005\u000b:,X\u000e\u0003\u0005\u0006:\u0005U\u0001\u0019AC\u001e\u0003%)g.^7DY\u0006\u001c8\u000f\u0005\u0004\u0002\u001c\u0016uR\u0011F\u0005\u0005\u000b\u007f\t9KA\u0003DY\u0006\u001c8\u000f\u0003\u0005\u0004\u0010\u0005U\u0001\u0019AAM\u0003\u001d9W\r^#ok6,B!b\u0012\u0006LQ1Q\u0011JC)\u000b+\u0002B\u0001b\u0012\u0006L\u0011AQQFA\f\u0005\u0004)i%\u0005\u0003\u0005P\u0015=\u0003CBAx\u000bg)I\u0005\u0003\u0005\u0006:\u0005]\u0001\u0019AC*!\u0019\tY*\"\u0010\u0006J!A1qBA\f\u0001\u0004\tI\n")
public class CachingConfig
implements Config {
    private final /* synthetic */ Tuple2 x$1;
    private final Config config;
    private final ConcurrentHashMap<String, PathEntry> entryMap;

    public static ValuePathEntry emptyPathEntry() {
        return CachingConfig$.MODULE$.emptyPathEntry();
    }

    public static ValuePathEntry nonExistingPathEntry() {
        return CachingConfig$.MODULE$.nonExistingPathEntry();
    }

    public static ValuePathEntry invalidPathEntry() {
        return CachingConfig$.MODULE$.invalidPathEntry();
    }

    public static Config emptyConfig() {
        return CachingConfig$.MODULE$.emptyConfig();
    }

    private Config config() {
        return this.config;
    }

    private ConcurrentHashMap<String, PathEntry> entryMap() {
        return this.entryMap;
    }

    private PathEntry getPathEntry(String path) {
        PathEntry pathEntry = this.entryMap().get(path);
        if (pathEntry == null) {
            Success success;
            boolean bl;
            Product product;
            Try<Object> try_ = Try$.MODULE$.apply(() -> this.config().hasPath(path));
            if (try_ instanceof Failure) {
                product = CachingConfig$.MODULE$.invalidPathEntry();
            } else if (try_ instanceof Success && !(bl = BoxesRunTime.unboxToBoolean((success = (Success)try_).value()))) {
                product = CachingConfig$.MODULE$.nonExistingPathEntry();
            } else {
                Try try_2 = Try$.MODULE$.apply((Function0<ConfigValue> & Serializable)() -> this.config().getValue(path));
                if (try_2 instanceof Failure) {
                    product = CachingConfig$.MODULE$.emptyPathEntry();
                } else if (try_2 instanceof Success) {
                    Success success2 = (Success)try_2;
                    ConfigValue v = (ConfigValue)success2.value();
                    ConfigValueType configValueType = v.valueType();
                    ConfigValueType configValueType2 = ConfigValueType.STRING;
                    product = !(configValueType != null ? !((Object)((Object)configValueType)).equals((Object)configValueType2) : configValueType2 != null) ? new StringPathEntry(true, true, v.atKey("cached"), (String)v.unwrapped()) : new ValuePathEntry(true, true, v.atKey("cached"));
                } else {
                    throw new MatchError(try_2);
                }
            }
            ValuePathEntry ne = product;
            PathEntry pathEntry2 = this.entryMap().putIfAbsent(path, ne);
            if (pathEntry2 == null) {
                return ne;
            }
            return pathEntry2;
        }
        return pathEntry;
    }

    public void checkValid(Config reference, Seq<String> restrictToPaths) {
        this.config().checkValid(reference, (String[])restrictToPaths.toArray(ClassTag$.MODULE$.apply(String.class)));
    }

    @Override
    public ConfigObject root() {
        return this.config().root();
    }

    @Override
    public ConfigOrigin origin() {
        return this.config().origin();
    }

    @Override
    public CachingConfig withFallback(ConfigMergeable other) {
        return new CachingConfig(this.config().withFallback(other));
    }

    @Override
    public Config resolve() {
        return this.resolve(ConfigResolveOptions.defaults());
    }

    @Override
    public Config resolve(ConfigResolveOptions options) {
        Config resolved = this.config().resolve(options);
        if (resolved == this.config()) {
            return this;
        }
        return new CachingConfig(resolved);
    }

    @Override
    public boolean hasPath(String path) {
        PathEntry entry = this.getPathEntry(path);
        if (entry.valid()) {
            return entry.exists();
        }
        return this.config().hasPath(path);
    }

    @Override
    public boolean hasPathOrNull(String path) {
        return this.config().hasPathOrNull(path);
    }

    @Override
    public boolean isEmpty() {
        return this.config().isEmpty();
    }

    @Override
    public Set<Map.Entry<String, ConfigValue>> entrySet() {
        return this.config().entrySet();
    }

    @Override
    public boolean getBoolean(String path) {
        return this.config().getBoolean(path);
    }

    @Override
    public Number getNumber(String path) {
        return this.config().getNumber(path);
    }

    @Override
    public int getInt(String path) {
        return this.config().getInt(path);
    }

    @Override
    public long getLong(String path) {
        return this.config().getLong(path);
    }

    @Override
    public double getDouble(String path) {
        return this.config().getDouble(path);
    }

    @Override
    public String getString(String path) {
        PathEntry pathEntry = this.getPathEntry(path);
        if (pathEntry instanceof StringPathEntry) {
            StringPathEntry stringPathEntry = (StringPathEntry)pathEntry;
            String string = stringPathEntry.value();
            return string;
        }
        return pathEntry.config().getString("cached");
    }

    @Override
    public ConfigObject getObject(String path) {
        return this.config().getObject(path);
    }

    @Override
    public Config getConfig(String path) {
        return this.config().getConfig(path);
    }

    @Override
    public Object getAnyRef(String path) {
        return this.config().getAnyRef(path);
    }

    @Override
    public ConfigValue getValue(String path) {
        return this.config().getValue(path);
    }

    @Override
    public Long getBytes(String path) {
        return this.config().getBytes(path);
    }

    @Override
    public Long getMilliseconds(String path) {
        return Predef$.MODULE$.long2Long(this.config().getDuration(path, TimeUnit.MILLISECONDS));
    }

    @Override
    public Long getNanoseconds(String path) {
        return Predef$.MODULE$.long2Long(this.config().getDuration(path, TimeUnit.NANOSECONDS));
    }

    @Override
    public ConfigList getList(String path) {
        return this.config().getList(path);
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return this.config().getBooleanList(path);
    }

    @Override
    public List<Number> getNumberList(String path) {
        return this.config().getNumberList(path);
    }

    @Override
    public List<Integer> getIntList(String path) {
        return this.config().getIntList(path);
    }

    @Override
    public List<Long> getLongList(String path) {
        return this.config().getLongList(path);
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return this.config().getDoubleList(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return this.config().getStringList(path);
    }

    @Override
    public List<? extends ConfigObject> getObjectList(String path) {
        return this.config().getObjectList(path);
    }

    @Override
    public List<? extends Config> getConfigList(String path) {
        return this.config().getConfigList(path);
    }

    public List<?> getAnyRefList(String path) {
        return this.config().getAnyRefList(path);
    }

    @Override
    public List<Long> getBytesList(String path) {
        return this.config().getBytesList(path);
    }

    @Override
    public List<Long> getMillisecondsList(String path) {
        return this.config().getDurationList(path, TimeUnit.MILLISECONDS);
    }

    @Override
    public List<Long> getNanosecondsList(String path) {
        return this.config().getDurationList(path, TimeUnit.NANOSECONDS);
    }

    @Override
    public CachingConfig withOnlyPath(String path) {
        return new CachingConfig(this.config().withOnlyPath(path));
    }

    @Override
    public CachingConfig withoutPath(String path) {
        return new CachingConfig(this.config().withoutPath(path));
    }

    @Override
    public CachingConfig atPath(String path) {
        return new CachingConfig(this.config().atPath(path));
    }

    @Override
    public CachingConfig atKey(String key) {
        return new CachingConfig(this.config().atKey(key));
    }

    @Override
    public CachingConfig withValue(String path, ConfigValue value) {
        return new CachingConfig(this.config().withValue(path, value));
    }

    @Override
    public long getDuration(String path, TimeUnit unit) {
        return this.config().getDuration(path, unit);
    }

    @Override
    public List<Long> getDurationList(String path, TimeUnit unit) {
        return this.config().getDurationList(path, unit);
    }

    @Override
    public Duration getDuration(String path) {
        return this.config().getDuration(path);
    }

    @Override
    public List<Duration> getDurationList(String path) {
        return this.config().getDurationList(path);
    }

    @Override
    public Period getPeriod(String path) {
        return this.config().getPeriod(path);
    }

    @Override
    public TemporalAmount getTemporal(String path) {
        return this.config().getTemporal(path);
    }

    @Override
    public boolean getIsNull(String path) {
        return this.config().getIsNull(path);
    }

    @Override
    public ConfigMemorySize getMemorySize(String path) {
        return this.config().getMemorySize(path);
    }

    @Override
    public List<ConfigMemorySize> getMemorySizeList(String path) {
        return this.config().getMemorySizeList(path);
    }

    @Override
    public boolean isResolved() {
        return this.config().isResolved();
    }

    @Override
    public Config resolveWith(Config source2, ConfigResolveOptions options) {
        return this.config().resolveWith(source2, options);
    }

    @Override
    public Config resolveWith(Config source2) {
        return this.config().resolveWith(source2);
    }

    @Override
    public <T extends Enum<T>> List<T> getEnumList(Class<T> enumClass, String path) {
        return this.config().getEnumList(enumClass, path);
    }

    @Override
    public <T extends Enum<T>> T getEnum(Class<T> enumClass, String path) {
        return this.config().getEnum(enumClass, path);
    }

    @Override
    public void checkValid(Config reference, String[] restrictToPaths) {
        this.checkValid(reference, ScalaRunTime$.MODULE$.wrapRefArray((Object[])restrictToPaths));
    }

    public CachingConfig(Config _config) {
        Config config;
        ConcurrentHashMap entryMap;
        block6: {
            Tuple2 tuple2;
            block5: {
                Tuple2 tuple22;
                Config config2 = _config;
                if (config2 instanceof CachingConfig) {
                    CachingConfig cachingConfig = (CachingConfig)config2;
                    tuple22 = new Tuple2<Config, ConcurrentHashMap<String, PathEntry>>(cachingConfig.config(), cachingConfig.entryMap());
                } else {
                    tuple22 = tuple2 = new Tuple2(_config, new ConcurrentHashMap());
                }
                if (tuple2 == null) break block5;
                Config config3 = tuple2._1();
                entryMap = tuple2._2();
                if (config3 == null) break block5;
                config = config3;
                if (entryMap != null) break block6;
            }
            throw new MatchError(tuple2);
        }
        ConcurrentHashMap concurrentHashMap = entryMap;
        this.x$1 = new Tuple2(config, concurrentHashMap);
        this.config = (Config)this.x$1._1();
        this.entryMap = (ConcurrentHashMap)this.x$1._2();
    }

    public static interface PathEntry {
        public boolean valid();

        public boolean exists();

        public Config config();
    }

    public static final class StringPathEntry
    implements PathEntry,
    Product,
    Serializable {
        private final boolean valid;
        private final boolean exists;
        private final Config config;
        private final String value;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public boolean valid() {
            return this.valid;
        }

        @Override
        public boolean exists() {
            return this.exists;
        }

        @Override
        public Config config() {
            return this.config;
        }

        public String value() {
            return this.value;
        }

        public StringPathEntry copy(boolean valid, boolean exists, Config config, String value) {
            return new StringPathEntry(valid, exists, config, value);
        }

        public boolean copy$default$1() {
            return this.valid();
        }

        public boolean copy$default$2() {
            return this.exists();
        }

        public Config copy$default$3() {
            return this.config();
        }

        public String copy$default$4() {
            return this.value();
        }

        @Override
        public String productPrefix() {
            return "StringPathEntry";
        }

        @Override
        public int productArity() {
            return 4;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToBoolean(this.valid());
                }
                case 1: {
                    return BoxesRunTime.boxToBoolean(this.exists());
                }
                case 2: {
                    return this.config();
                }
                case 3: {
                    return this.value();
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
            return x$1 instanceof StringPathEntry;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "valid";
                }
                case 1: {
                    return "exists";
                }
                case 2: {
                    return "config";
                }
                case 3: {
                    return "value";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.valid() ? 1231 : 1237);
            n = Statics.mix(n, this.exists() ? 1231 : 1237);
            n = Statics.mix(n, Statics.anyHash(this.config()));
            n = Statics.mix(n, Statics.anyHash(this.value()));
            return Statics.finalizeHash(n, 4);
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
            if (!(object instanceof StringPathEntry)) return false;
            boolean bl = true;
            if (!bl) return false;
            StringPathEntry stringPathEntry = (StringPathEntry)x$1;
            if (this.valid() != stringPathEntry.valid()) return false;
            if (this.exists() != stringPathEntry.exists()) return false;
            Config config = this.config();
            Config config2 = stringPathEntry.config();
            if (config == null) {
                if (config2 != null) {
                    return false;
                }
            } else if (!config.equals(config2)) return false;
            String string = this.value();
            String string2 = stringPathEntry.value();
            if (string == null) {
                if (string2 == null) return true;
                return false;
            } else {
                if (!string.equals(string2)) return false;
                return true;
            }
        }

        public StringPathEntry(boolean valid, boolean exists, Config config, String value) {
            this.valid = valid;
            this.exists = exists;
            this.config = config;
            this.value = value;
            Product.$init$(this);
        }
    }

    public static final class ValuePathEntry
    implements PathEntry,
    Product,
    Serializable {
        private final boolean valid;
        private final boolean exists;
        private final Config config;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public boolean valid() {
            return this.valid;
        }

        @Override
        public boolean exists() {
            return this.exists;
        }

        @Override
        public Config config() {
            return this.config;
        }

        public ValuePathEntry copy(boolean valid, boolean exists, Config config) {
            return new ValuePathEntry(valid, exists, config);
        }

        public boolean copy$default$1() {
            return this.valid();
        }

        public boolean copy$default$2() {
            return this.exists();
        }

        public Config copy$default$3() {
            return this.config();
        }

        @Override
        public String productPrefix() {
            return "ValuePathEntry";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToBoolean(this.valid());
                }
                case 1: {
                    return BoxesRunTime.boxToBoolean(this.exists());
                }
                case 2: {
                    return this.config();
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
            return x$1 instanceof ValuePathEntry;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "valid";
                }
                case 1: {
                    return "exists";
                }
                case 2: {
                    return "config";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.valid() ? 1231 : 1237);
            n = Statics.mix(n, this.exists() ? 1231 : 1237);
            n = Statics.mix(n, Statics.anyHash(this.config()));
            return Statics.finalizeHash(n, 3);
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
            if (!(object instanceof ValuePathEntry)) return false;
            boolean bl = true;
            if (!bl) return false;
            ValuePathEntry valuePathEntry = (ValuePathEntry)x$1;
            if (this.valid() != valuePathEntry.valid()) return false;
            if (this.exists() != valuePathEntry.exists()) return false;
            Config config = this.config();
            Config config2 = valuePathEntry.config();
            if (config != null) {
                if (!config.equals(config2)) return false;
                return true;
            }
            if (config2 == null) return true;
            return false;
        }

        public ValuePathEntry(boolean valid, boolean exists, Config config) {
            this.valid = valid;
            this.exists = exists;
            this.config = config;
            Product.$init$(this);
        }
    }
}

