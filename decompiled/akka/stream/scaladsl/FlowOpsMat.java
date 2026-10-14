/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.stream.FlowMonitor;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.impl.fusing.FlatMapPrefix;
import akka.stream.impl.fusing.GraphStages$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.FlowOps;
import akka.stream.scaladsl.Keep$;
import scala.Function1;
import scala.Function2;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015-fa\u0002\u0012$!\u0003\r\tA\u000b\u0005\u0006\t\u0002!\t!\u0012\u0003\u0006\u0013\u0002\u0011\tA\u0013\u0003\u0006#\u0002\u0011\tA\u0015\u0003\u0006q\u0002\u0011\t!\u001f\u0005\b\u0003\u0003\u0002a\u0011AA\"\u0011\u001d\t\u0019\b\u0001D\u0001\u0003kBq!!&\u0001\t\u0003\t9\nC\u0004\u0002l\u0002!\t!!<\t\u000f\te\u0001\u0001\"\u0001\u0003\u001c!9!1\n\u0001\u0005\u0002\t5\u0003b\u0002B<\u0001\u0011\u0005!\u0011\u0010\u0005\b\u00053\u0003A\u0011\u0001BN\u0011\u001d\u0011I\n\u0001C\u0001\u0005\u0007DqA!>\u0001\t\u0003\u00119\u0010C\u0005\u0004\u0018\u0001\t\n\u0011\"\u0001\u0004\u001a!91Q\u0005\u0001\u0005\u0002\r\u001d\u0002bBB\u0013\u0001\u0011\u000511\u0013\u0005\b\u0007\u0007\u0004A\u0011ABc\u0011\u001d\u00199\u000f\u0001C\u0001\u0007SDq\u0001\"\u0004\u0001\t\u0003!y\u0001C\u0004\u00058\u0001!\t\u0001\"\u000f\t\u000f\u0011-\u0004\u0001\"\u0001\u0005n!9A1\u0012\u0001\u0005\u0002\u00115\u0005b\u0002CV\u0001\u0011\u0005AQ\u0016\u0005\b\t\u0017\u0004A\u0011\u0001Cg\u0011\u001d!Y\u000f\u0001C\u0001\t[Dq!\"\u0004\u0001\t\u0003)y\u0001C\u0004\u0006(\u0001!\t!\"\u000b\t\u000f\u0015\u001d\u0003\u0001\"\u0001\u0006J!9Q\u0011\r\u0001\u0005\u0002\u0015\r\u0004bBC?\u0001\u0019\u0005Qq\u0010\u0005\b\u000b\u001b\u0003A\u0011ACH\u0011\u001d)\u0019\u000b\u0001C\u0001\u000bK\u0013!B\u00127po>\u00038/T1u\u0015\t!S%\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\t1s%\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002Q\u0005!\u0011m[6b\u0007\u0001)2a\u000b\u001dC'\r\u0001AF\r\t\u0003[Aj\u0011A\f\u0006\u0002_\u0005)1oY1mC&\u0011\u0011G\f\u0002\u0007\u0003:L(+\u001a4\u0011\tM\"d'Q\u0007\u0002G%\u0011Qg\t\u0002\b\r2|wo\u00149t!\t9\u0004\b\u0004\u0001\u0005\re\u0002AQ1\u0001;\u0005\ryU\u000f^\t\u0003wy\u0002\"!\f\u001f\n\u0005ur#a\u0002(pi\"Lgn\u001a\t\u0003[}J!\u0001\u0011\u0018\u0003\u0007\u0005s\u0017\u0010\u0005\u00028\u0005\u001211\t\u0001CC\u0002i\u00121!T1u\u0003\u0019!\u0013N\\5uIQ\ta\t\u0005\u0002.\u000f&\u0011\u0001J\f\u0002\u0005+:LGO\u0001\u0003SKB\u0014XcA&\u0002\u0018E\u00111\b\u0014\n\u0003\u001b>3AA\u0014\u0001\u0001\u0019\naAH]3gS:,W.\u001a8u}A)\u0001kAA\u000b\u00036\t\u0001AA\u0004SKB\u0014X*\u0019;\u0016\u0007MC6,\u0005\u0002<)J\u0011QK\u0016\u0004\u0005\u001d\u0002\u0001A\u000b\u0005\u00034\u0001]S\u0006CA\u001cY\t\u0019I6\u0001\"b\u0001u\t\tq\n\u0005\u000287\u00121Al\u0001CC\u0002i\u0012\u0011!T\u0003\u0005\u0013V\u0003c,\u0006\u0002`CB!\u0001k\u00011d!\t9\u0014\r\u0002\u0004c;\u0012\u0015\rA\u000f\u0002\u0003\u001f>S#A\u00173,\u0003\u0015\u0004\"AZ6\u000e\u0003\u001dT!\u0001[5\u0002\u0013Ut7\r[3dW\u0016$'B\u00016/\u0003)\tgN\\8uCRLwN\\\u0005\u0003Y\u001e\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u000b\u0011\tV\u000b\t8\u0016\u0007=\f8\u000f\u0005\u0003Q\u0007A\u0014\bCA\u001cr\t\u0019\u0011W\u000e\"b\u0001uA\u0011qg\u001d\u0003\u0007i6$)\u0019\u0001\u001e\u0003\u00055kU\u0001\u0002<VA]\u0014aa\u00117pg\u0016$\u0007c\u0001)\u0005G\nI1\t\\8tK\u0012l\u0015\r^\u000b\u0004u\u0006%\u0011CA\u001e|a\ra\u00181\u0001\t\u0007{z\f\t!a\u0002\u000e\u0003\u0015J!a`\u0013\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\u0007]\n\u0019\u0001\u0002\u0006\u0002\u0006\u0011\t\t\u0011!A\u0003\u0002i\u0012Aa\u0018\u00133eA\u0019q'!\u0003\u0005\rq#AQ1\u0001;\u000b\u0015AX\u000bIA\u0007+\u0011\ty!a\u0005\u0011\tA#\u0011\u0011\u0003\t\u0004o\u0005MAa\u0002;\u0002\f\u0011\u0015\rA\u000f\t\u0004o\u0005]AAB-\u0003\t\u000b\u0007!(B\u0003J\u001b\u0002\nY\"\u0006\u0003\u0002\u001e\u0005\u0005\u0002\u0003\u0002)\u0003\u0003?\u00012aNA\u0011\t\u001d\u0011\u0017\u0011\u0004CC\u0002i*Q!U'!\u0003K)b!a\n\u0002,\u0005=\u0002C\u0002)\u0004\u0003S\ti\u0003E\u00028\u0003W!qAYA\u0012\t\u000b\u0007!\bE\u00028\u0003_!q\u0001^A\u0012\t\u000b\u0007!(B\u0003w\u001b\u0002\n\u0019\u0004E\u0002Q\u0003kI!A\u001e\u001b\u0006\u000bal\u0005%!\u000f\u0016\t\u0005m\u0012q\b\t\u0005!\u0012\ti\u0004E\u00028\u0003\u007f!q\u0001XA\u001c\t\u000b\u0007!(\u0001\u0004wS\u0006l\u0015\r^\u000b\t\u0003\u000b\ni%a\u0019\u0002TQ!\u0011qIA4)\u0011\tI%a\u0016\u0011\rA\u001b\u00111JA)!\r9\u0014Q\n\u0003\u0007\u0003\u001f*!\u0019\u0001\u001e\u0003\u0003Q\u00032aNA*\t\u0019\t)&\u0002b\u0001u\t!Q*\u0019;4\u0011\u001d\tI&\u0002a\u0001\u00037\nqaY8nE&tW\r\u0005\u0005.\u0003;\n\u0015\u0011MA)\u0013\r\tyF\f\u0002\n\rVt7\r^5p]J\u00022aNA2\t\u0019\t)'\u0002b\u0001u\t!Q*\u0019;3\u0011\u001d\tI'\u0002a\u0001\u0003W\nAA\u001a7poB1QP`A7\u0003C\u0002b!`A8m\u0005-\u0013bAA9K\tIa\t\\8x'\"\f\u0007/Z\u0001\u0006i>l\u0015\r^\u000b\u0007\u0003o\n9)a \u0015\t\u0005e\u0014\u0011\u0012\u000b\u0005\u0003w\n\t\t\u0005\u0003Q\t\u0005u\u0004cA\u001c\u0002\u0000\u00111\u0011Q\u000b\u0004C\u0002iBq!!\u0017\u0007\u0001\u0004\t\u0019\t\u0005\u0005.\u0003;\n\u0015QQA?!\r9\u0014q\u0011\u0003\u0007\u0003K2!\u0019\u0001\u001e\t\u000f\u0005-e\u00011\u0001\u0002\u000e\u0006!1/\u001b8l!\u0019ih0a$\u0002\u0006B!Q0!%7\u0013\r\t\u0019*\n\u0002\n'&t7n\u00155ba\u0016\f\u0001C\u001a7bi6\u000b\u0007\u000f\u0015:fM&DX*\u0019;\u0016\u0011\u0005e\u00151UA`\u0003S#B!a'\u0002bR!\u0011QTAa)\u0011\ty*a+\u0011\rA\u001b\u0011\u0011UAT!\r9\u00141\u0015\u0003\u0007\u0003K;!\u0019\u0001\u001e\u0003\t=+HO\r\t\u0004o\u0005%FABA+\u000f\t\u0007!\bC\u0004\u0002.\u001e\u0001\r!a,\u0002\t5\fGO\u0012\t\t[\u0005u\u0013)!-\u0002(B1\u00111WA]\u0003{k!!!.\u000b\u0007\u0005]f&\u0001\u0006d_:\u001cWO\u001d:f]RLA!a/\u00026\n1a)\u001e;ve\u0016\u00042aNA`\t\u0019\t)g\u0002b\u0001u!9\u00111Y\u0004A\u0002\u0005\u0015\u0017!\u00014\u0011\u000f5\n9-a3\u0002\\&\u0019\u0011\u0011\u001a\u0018\u0003\u0013\u0019+hn\u0019;j_:\f\u0004#BAg\u0003/4TBAAh\u0015\u0011\t\t.a5\u0002\u0013%lW.\u001e;bE2,'bAAk]\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005e\u0017q\u001a\u0002\u0004'\u0016\f\b\u0003C\u001a\u0002^Z\n\t+!0\n\u0007\u0005}7E\u0001\u0003GY><\bbBAr\u000f\u0001\u0007\u0011Q]\u0001\u0002]B\u0019Q&a:\n\u0007\u0005%hFA\u0002J]R\faA_5q\u001b\u0006$X\u0003CAx\u0003{\u0014YAa\u0001\u0015\t\u0005E(Q\u0002\u000b\u0005\u0003g\u0014)\u0001\u0005\u0004Q\u0007\u0005U(\u0011\u0001\t\u0007[\u0005]h'a?\n\u0007\u0005ehF\u0001\u0004UkBdWM\r\t\u0004o\u0005uHABA\u0000\u0011\t\u0007!HA\u0001V!\r9$1\u0001\u0003\u0007\u0003+B!\u0019\u0001\u001e\t\u000f\u00055\u0006\u00021\u0001\u0003\bAAQ&!\u0018B\u0005\u0013\u0011\t\u0001E\u00028\u0005\u0017!a!!\u001a\t\u0005\u0004Q\u0004b\u0002B\b\u0011\u0001\u0007!\u0011C\u0001\u0005i\"\fG\u000f\u0005\u0004~}\nM!\u0011\u0002\t\u0006{\nU\u00111`\u0005\u0004\u0005/)#aC*pkJ\u001cWm\u00155ba\u0016\f\u0011B_5q\u00032dW*\u0019;\u0016\u0015\tu!q\u0006B\u001e\u0005g\u00119\u0003\u0006\u0005\u0003 \tu\"1\tB$)\u0011\u0011\tC!\u000e\u0011\rA\u001b!1\u0005B\u0019!\u001di\u0013q\u001fB\u0013\u0005[\u00012a\u000eB\u0014\t\u001d\u0011I#\u0003b\u0001\u0005W\u0011\u0011!Q\t\u0003my\u00022a\u000eB\u0018\t\u0019\ty0\u0003b\u0001uA\u0019qGa\r\u0005\r\u0005U\u0013B1\u0001;\u0011\u001d\ti+\u0003a\u0001\u0005o\u0001\u0002\"LA/\u0003\ne\"\u0011\u0007\t\u0004o\tmBABA3\u0013\t\u0007!\bC\u0004\u0003\u0010%\u0001\rAa\u0010\u0011\rut(\u0011\tB\u001d!\u0015i(Q\u0003B\u0017\u0011\u001d\u0011)%\u0003a\u0001\u0005K\t\u0001\u0002\u001e5jg\u0016cW-\u001c\u0005\b\u0005\u0013J\u0001\u0019\u0001B\u0017\u0003!!\b.\u0019;FY\u0016l\u0017A\u0003>ja^KG\u000f['biVQ!q\nB8\u00053\u00129Ga\u0018\u0015\t\tE#\u0011\u000f\u000b\u0005\u0005'\u0012I\u0007\u0006\u0003\u0003V\t\u0005\u0004C\u0002)\u0004\u0005/\u0012i\u0006E\u00028\u00053\"aAa\u0017\u000b\u0005\u0004Q$\u0001B(viN\u00022a\u000eB0\t\u0019\t)F\u0003b\u0001u!9\u0011Q\u0016\u0006A\u0002\t\r\u0004\u0003C\u0017\u0002^\u0005\u0013)G!\u0018\u0011\u0007]\u00129\u0007\u0002\u0004\u0002f)\u0011\rA\u000f\u0005\b\u00033R\u0001\u0019\u0001B6!!i\u0013Q\f\u001c\u0003n\t]\u0003cA\u001c\u0003p\u00111\u0011Q\u0015\u0006C\u0002iBqAa\u0004\u000b\u0001\u0004\u0011\u0019\b\u0005\u0004~}\nU$Q\r\t\u0006{\nU!QN\u0001\ru&\u0004H*\u0019;fgRl\u0015\r^\u000b\t\u0005w\u0012)I!%\u0003\nR!!Q\u0010BJ)\u0011\u0011yHa#\u0011\rA\u001b!\u0011\u0011BD!\u0019i\u0013q\u001f\u001c\u0003\u0004B\u0019qG!\"\u0005\r\u0005}8B1\u0001;!\r9$\u0011\u0012\u0003\u0007\u0003+Z!\u0019\u0001\u001e\t\u000f\u000556\u00021\u0001\u0003\u000eBAQ&!\u0018B\u0005\u001f\u00139\tE\u00028\u0005##a!!\u001a\f\u0005\u0004Q\u0004b\u0002B\b\u0017\u0001\u0007!Q\u0013\t\u0007{z\u00149Ja$\u0011\u000bu\u0014)Ba!\u0002!iL\u0007\u000fT1uKN$x+\u001b;i\u001b\u0006$XC\u0003BO\u0005w\u00139Ka-\u0003,R!!q\u0014B_)\u0011\u0011\tK!.\u0015\t\t\r&Q\u0016\t\u0007!\u000e\u0011)K!+\u0011\u0007]\u00129\u000b\u0002\u0004\u0003\\1\u0011\rA\u000f\t\u0004o\t-FABA+\u0019\t\u0007!\bC\u0004\u0002.2\u0001\rAa,\u0011\u00115\ni&\u0011BY\u0005S\u00032a\u000eBZ\t\u0019\t)\u0007\u0004b\u0001u!9\u0011\u0011\f\u0007A\u0002\t]\u0006\u0003C\u0017\u0002^Y\u0012IL!*\u0011\u0007]\u0012Y\f\u0002\u0004\u0002&2\u0011\rA\u000f\u0005\b\u0005\u001fa\u0001\u0019\u0001B`!\u0019ihP!1\u00032B)QP!\u0006\u0003:VQ!Q\u0019Br\u0005\u001f\u0014YNa5\u0015\r\t\u001d'Q\u001dBv)\u0011\u0011IM!8\u0015\t\t-'Q\u001b\t\u0007!\u000e\u0011iM!5\u0011\u0007]\u0012y\r\u0002\u0004\u0003\\5\u0011\rA\u000f\t\u0004o\tMGABA+\u001b\t\u0007!\bC\u0004\u0002.6\u0001\rAa6\u0011\u00115\ni&\u0011Bm\u0005#\u00042a\u000eBn\t\u0019\t)'\u0004b\u0001u!9\u0011\u0011L\u0007A\u0002\t}\u0007\u0003C\u0017\u0002^Y\u0012\tO!4\u0011\u0007]\u0012\u0019\u000f\u0002\u0004\u0002&6\u0011\rA\u000f\u0005\b\u0005\u001fi\u0001\u0019\u0001Bt!\u0019ihP!;\u0003ZB)QP!\u0006\u0003b\"9!Q^\u0007A\u0002\t=\u0018!D3bO\u0016\u00148i\\7qY\u0016$X\rE\u0002.\u0005cL1Aa=/\u0005\u001d\u0011un\u001c7fC:\f\u0001\"\\3sO\u0016l\u0015\r^\u000b\t\u0005s\u001c\ta!\u0004\u0004\u0006Q1!1`B\b\u0007+!BA!@\u0004\bA1\u0001k\u0001B\u0000\u0007\u0007\u00012aNB\u0001\t\u001d\tyP\u0004b\u0001\u0005W\u00012aNB\u0003\t\u0019\t)F\u0004b\u0001u!9\u0011Q\u0016\bA\u0002\r%\u0001\u0003C\u0017\u0002^\u0005\u001bYaa\u0001\u0011\u0007]\u001ai\u0001\u0002\u0004\u0002f9\u0011\rA\u000f\u0005\b\u0005\u001fq\u0001\u0019AB\t!\u0019ihpa\u0005\u0004\fA)QP!\u0006\u0003\u0000\"I!Q\u001e\b\u0011\u0002\u0003\u0007!q^\u0001\u0013[\u0016\u0014x-Z'bi\u0012\"WMZ1vYR$#'\u0006\u0005\u0004\u001c\r}1\u0011EB\u0012+\t\u0019iBK\u0002\u0003p\u0012$q!a@\u0010\u0005\u0004\u0011Y\u0003\u0002\u0004\u0002f=\u0011\rA\u000f\u0003\u0007\u0003+z!\u0019\u0001\u001e\u0002\u001b%tG/\u001a:mK\u00064X-T1u+!\u0019Ic!\r\u0004>\rUBCBB\u0016\u0007\u007f\u0019)\u0005\u0006\u0003\u0004.\r]\u0002C\u0002)\u0004\u0007_\u0019\u0019\u0004E\u00028\u0007c!q!a@\u0011\u0005\u0004\u0011Y\u0003E\u00028\u0007k!a!!\u0016\u0011\u0005\u0004Q\u0004bBAW!\u0001\u00071\u0011\b\t\t[\u0005u\u0013ia\u000f\u00044A\u0019qg!\u0010\u0005\r\u0005\u0015\u0004C1\u0001;\u0011\u001d\u0011y\u0001\u0005a\u0001\u0007\u0003\u0002b! @\u0004D\rm\u0002#B?\u0003\u0016\r=\u0002bBB$!\u0001\u0007\u0011Q]\u0001\fg\u0016<W.\u001a8u'&TX\r\u000b\u0004\u0004F\r-3\u0011\u000b\t\u0004[\r5\u0013bAB(]\tqA-\u001a9sK\u000e\fG/\u001a3OC6,\u0017gB\u0010\u0004T\re31\u0012\t\u0004[\rU\u0013bAB,]\t11+_7c_2\f\u0014bIB.\u0007C\u001ayha\u0019\u0015\t\rM3Q\f\u0005\b\u0007?J\u0003\u0019AB5\u0003\u0011q\u0017-\\3\n\t\r\r4QM\u0001\u0006CB\u0004H.\u001f\u0006\u0004\u0007Or\u0013AB*z[\n|G\u000e\u0005\u0003\u0004l\red\u0002BB7\u0007k\u00022aa\u001c/\u001b\t\u0019\tHC\u0002\u0004t%\na\u0001\u0010:p_Rt\u0014bAB<]\u00051\u0001K]3eK\u001aLAaa\u001f\u0004~\t11\u000b\u001e:j]\u001eT1aa\u001e/c%\u00193\u0011QBD\u0007\u0013\u001b9G\u0004\u0003\u0004\u0004\u000e\u001de\u0002BB8\u0007\u000bK\u0011aL\u0005\u0004\u0007Or\u0013G\u0002\u0013\u0004\u0004\u000e\u0015u&M\u0003&\u0007\u001b\u001byi\u0004\u0002\u0004\u0010\u0006\u00121\u0011S\u0001\be\u0016\fX/Z:u+!\u0019)j!(\u0004*\u000e\u0005F\u0003CBL\u0007W\u001b\tla0\u0015\t\re51\u0015\t\u0007!\u000e\u0019Yja(\u0011\u0007]\u001ai\nB\u0004\u0002\u0000F\u0011\rAa\u000b\u0011\u0007]\u001a\t\u000b\u0002\u0004\u0002VE\u0011\rA\u000f\u0005\b\u0003[\u000b\u0002\u0019ABS!!i\u0013QL!\u0004(\u000e}\u0005cA\u001c\u0004*\u00121\u0011QM\tC\u0002iBqAa\u0004\u0012\u0001\u0004\u0019i\u000b\u0005\u0004~}\u000e=6q\u0015\t\u0006{\nU11\u0014\u0005\b\u0007\u000f\n\u0002\u0019AAsQ\u0019\u0019\tla\u0013\u00046F:qda\u0015\u00048\u000eu\u0016'C\u0012\u0004\\\r\u00054\u0011XB2c%\u00193\u0011QBD\u0007w\u001b9'\r\u0004%\u0007\u0007\u001b)iL\u0019\u0006K\r55q\u0012\u0005\b\u0007\u0003\f\u0002\u0019\u0001Bx\u0003))\u0017mZ3s\u00072|7/Z\u0001\u000f[\u0016\u0014x-\u001a'bi\u0016\u001cH/T1u+!\u00199m!5\u0004^\u000eUGCBBe\u0007?\u001c)\u000f\u0006\u0003\u0004L\u000e]\u0007C\u0002)\u0004\u0007\u001b\u001c\u0019\u000e\u0005\u0004\u0002N\u0006]7q\u001a\t\u0004o\rEGaBA\u0000%\t\u0007!1\u0006\t\u0004o\rUGABA+%\t\u0007!\bC\u0004\u0002.J\u0001\ra!7\u0011\u00115\ni&QBn\u0007'\u00042aNBo\t\u0019\t)G\u0005b\u0001u!9!q\u0002\nA\u0002\r\u0005\bCB?\u007f\u0007G\u001cY\u000eE\u0003~\u0005+\u0019y\rC\u0004\u0004BJ\u0001\rAa<\u0002#5,'oZ3Qe\u00164WM\u001d:fI6\u000bG/\u0006\u0005\u0004l\u000eM8q`B|)!\u0019i\u000f\"\u0001\u0005\b\u0011-A\u0003BBx\u0007s\u0004b\u0001U\u0002\u0004r\u000eU\bcA\u001c\u0004t\u00129\u0011q`\nC\u0002\t-\u0002cA\u001c\u0004x\u00121\u0011QK\nC\u0002iBq!!,\u0014\u0001\u0004\u0019Y\u0010\u0005\u0005.\u0003;\n5Q`B{!\r94q \u0003\u0007\u0003K\u001a\"\u0019\u0001\u001e\t\u000f\t=1\u00031\u0001\u0005\u0004A1QP C\u0003\u0007{\u0004R! B\u000b\u0007cDq\u0001\"\u0003\u0014\u0001\u0004\u0011y/A\u0005qe\u00164WM\u001d:fI\"91\u0011Y\nA\u0002\t=\u0018aE7fe\u001e,\u0007K]5pe&$\u0018N_3e\u001b\u0006$X\u0003\u0003C\t\t3!)\u0003\"\b\u0015\u0015\u0011MAq\u0005C\u0017\tc!)\u0004\u0006\u0003\u0005\u0016\u0011}\u0001C\u0002)\u0004\t/!Y\u0002E\u00028\t3!q!a@\u0015\u0005\u0004\u0011Y\u0003E\u00028\t;!a!!\u0016\u0015\u0005\u0004Q\u0004bBAW)\u0001\u0007A\u0011\u0005\t\t[\u0005u\u0013\tb\t\u0005\u001cA\u0019q\u0007\"\n\u0005\r\u0005\u0015DC1\u0001;\u0011\u001d\u0011y\u0001\u0006a\u0001\tS\u0001b! @\u0005,\u0011\r\u0002#B?\u0003\u0016\u0011]\u0001b\u0002C\u0018)\u0001\u0007\u0011Q]\u0001\rY\u00164G\u000f\u0015:j_JLG/\u001f\u0005\b\tg!\u0002\u0019AAs\u00035\u0011\u0018n\u001a5u!JLwN]5us\"91\u0011\u0019\u000bA\u0002\t=\u0018AD7fe\u001e,7k\u001c:uK\u0012l\u0015\r^\u000b\t\tw!)\u0005b\u0019\u0005JQ!AQ\bC3)\u0011!y\u0004\"\u0018\u0015\t\u0011\u0005C1\n\t\u0007!\u000e!\u0019\u0005b\u0012\u0011\u0007]\")\u0005B\u0004\u0002\u0000V\u0011\rAa\u000b\u0011\u0007]\"I\u0005\u0002\u0004\u0002VU\u0011\rA\u000f\u0005\b\t\u001b*\u00029\u0001C(\u0003\ry'\u000f\u001a\t\u0007\t#\"9\u0006b\u0011\u000f\t\r\rE1K\u0005\u0004\t+r\u0013a\u00029bG.\fw-Z\u0005\u0005\t3\"YF\u0001\u0005Pe\u0012,'/\u001b8h\u0015\r!)F\f\u0005\b\u0003[+\u0002\u0019\u0001C0!!i\u0013QL!\u0005b\u0011\u001d\u0003cA\u001c\u0005d\u00111\u0011QM\u000bC\u0002iBqAa\u0004\u0016\u0001\u0004!9\u0007\u0005\u0004~}\u0012%D\u0011\r\t\u0006{\nUA1I\u0001\nG>t7-\u0019;NCR,\u0002\u0002b\u001c\u0005x\u0011\rE1\u0010\u000b\u0005\tc\")\t\u0006\u0003\u0005t\u0011u\u0004C\u0002)\u0004\tk\"I\bE\u00028\to\"q!a@\u0017\u0005\u0004\u0011Y\u0003E\u00028\tw\"a!!\u0016\u0017\u0005\u0004Q\u0004bBAW-\u0001\u0007Aq\u0010\t\t[\u0005u\u0013\t\"!\u0005zA\u0019q\u0007b!\u0005\r\u0005\u0015dC1\u0001;\u0011\u001d\u0011yA\u0006a\u0001\t\u000f\u0003b! @\u0005\n\u0012\u0005\u0005#B?\u0003\u0016\u0011U\u0014!D2p]\u000e\fG\u000fT1{s6\u000bG/\u0006\u0005\u0005\u0010\u0012]E1\u0015CN)\u0011!\t\n\"*\u0015\t\u0011MEQ\u0014\t\u0007!\u000e!)\n\"'\u0011\u0007]\"9\nB\u0004\u0002\u0000^\u0011\rAa\u000b\u0011\u0007]\"Y\n\u0002\u0004\u0002V]\u0011\rA\u000f\u0005\b\u0003[;\u0002\u0019\u0001CP!!i\u0013QL!\u0005\"\u0012e\u0005cA\u001c\u0005$\u00121\u0011QM\fC\u0002iBqAa\u0004\u0018\u0001\u0004!9\u000b\u0005\u0004~}\u0012%F\u0011\u0015\t\u0006{\nUAQS\u0001\u000baJ,\u0007/\u001a8e\u001b\u0006$X\u0003\u0003CX\to#\u0019\rb/\u0015\t\u0011EFQ\u0019\u000b\u0005\tg#i\f\u0005\u0004Q\u0007\u0011UF\u0011\u0018\t\u0004o\u0011]FaBA\u00001\t\u0007!1\u0006\t\u0004o\u0011mFABA+1\t\u0007!\bC\u0004\u0002.b\u0001\r\u0001b0\u0011\u00115\ni&\u0011Ca\ts\u00032a\u000eCb\t\u0019\t)\u0007\u0007b\u0001u!9!q\u0002\rA\u0002\u0011\u001d\u0007CB?\u007f\t\u0013$\t\rE\u0003~\u0005+!),\u0001\bqe\u0016\u0004XM\u001c3MCjLX*\u0019;\u0016\u0011\u0011=Gq\u001bCr\t7$B\u0001\"5\u0005fR!A1\u001bCo!\u0019\u00016\u0001\"6\u0005ZB\u0019q\u0007b6\u0005\u000f\u0005}\u0018D1\u0001\u0003,A\u0019q\u0007b7\u0005\r\u0005U\u0013D1\u0001;\u0011\u001d\ti+\u0007a\u0001\t?\u0004\u0002\"LA/\u0003\u0012\u0005H\u0011\u001c\t\u0004o\u0011\rHABA33\t\u0007!\bC\u0004\u0003\u0010e\u0001\r\u0001b:\u0011\rutH\u0011\u001eCq!\u0015i(Q\u0003Ck\u0003%y'/\u00127tK6\u000bG/\u0006\u0005\u0005p\u0012]X1\u0001C~)\u0011!\t0\"\u0002\u0015\t\u0011MHQ \t\u0007!\u000e!)\u0010\"?\u0011\u0007]\"9\u0010B\u0004\u0002\u0000j\u0011\rAa\u000b\u0011\u0007]\"Y\u0010\u0002\u0004\u0002Vi\u0011\rA\u000f\u0005\b\u0003[S\u0002\u0019\u0001C\u0000!!i\u0013QL!\u0006\u0002\u0011e\bcA\u001c\u0006\u0004\u00111\u0011Q\r\u000eC\u0002iBq!b\u0002\u001b\u0001\u0004)I!A\u0005tK\u000e|g\u000eZ1ssB1QP`C\u0006\u000b\u0003\u0001R! B\u000b\tk\f\u0011\"\u00197t_R{W*\u0019;\u0016\r\u0015EQ\u0011EC\r)\u0011)\u0019\"b\t\u0015\t\u0015UQ1\u0004\t\u0006!\u000e1Tq\u0003\t\u0004o\u0015eAABA+7\t\u0007!\bC\u0004\u0002.n\u0001\r!\"\b\u0011\u00115\ni&QC\u0010\u000b/\u00012aNC\u0011\t\u0019\t)g\u0007b\u0001u!9!qB\u000eA\u0002\u0015\u0015\u0002CB?\u007f\u0003\u001f+y\"A\u0006eSZ,'\u000f\u001e+p\u001b\u0006$XCBC\u0016\u000bw)\u0019\u0004\u0006\u0004\u0006.\u0015uR\u0011\t\u000b\u0005\u000b_))\u0004E\u0003Q\u0007Y*\t\u0004E\u00028\u000bg!a!!\u0016\u001d\u0005\u0004Q\u0004bBAW9\u0001\u0007Qq\u0007\t\t[\u0005u\u0013)\"\u000f\u00062A\u0019q'b\u000f\u0005\r\u0005\u0015DD1\u0001;\u0011\u001d\u0011y\u0001\ba\u0001\u000b\u007f\u0001b! @\u0002\u0010\u0016e\u0002bBC\"9\u0001\u0007QQI\u0001\u0005o\",g\u000e\u0005\u0004.\u0003\u000f4$q^\u0001\u000bo&\u0014X\rV1q\u001b\u0006$XCBC&\u000b7*\u0019\u0006\u0006\u0003\u0006N\u0015uC\u0003BC(\u000b+\u0002R\u0001U\u00027\u000b#\u00022aNC*\t\u0019\t)&\bb\u0001u!9\u0011QV\u000fA\u0002\u0015]\u0003\u0003C\u0017\u0002^\u0005+I&\"\u0015\u0011\u0007]*Y\u0006\u0002\u0004\u0002fu\u0011\rA\u000f\u0005\b\u0005\u001fi\u0002\u0019AC0!\u0019ih0a$\u0006Z\u0005\u0001r/\u0019;dQR+'/\\5oCRLwN\\\u000b\u0005\u000bK*i\u0007\u0006\u0002\u0006hQ!Q\u0011NC8!\u0015\u00016ANC6!\r9TQ\u000e\u0003\u0007\u0003Kr\"\u0019\u0001\u001e\t\u000f\u00055f\u00041\u0001\u0006rAAQ&!\u0018B\u000bg*Y\u0007\u0005\u0004\u00024\u0006eVQ\u000f\t\u0005\u000bo*I(D\u0001(\u0013\r)Yh\n\u0002\u0005\t>tW-\u0001\u000bnCBl\u0015\r^3sS\u0006d\u0017N_3e-\u0006dW/Z\u000b\u0005\u000b\u0003+9\t\u0006\u0003\u0006\u0004\u0016%\u0005#\u0002)\u0004m\u0015\u0015\u0005cA\u001c\u0006\b\u00121\u0011QM\u0010C\u0002iBq!a1 \u0001\u0004)Y\t\u0005\u0004.\u0003\u000f\fUQQ\u0001\u000b[>t\u0017\u000e^8s\u001b\u0006$X\u0003BCI\u000b/#B!b%\u0006\u001aB)\u0001k\u0001\u001c\u0006\u0016B\u0019q'b&\u0005\r\u0005\u0015\u0004E1\u0001;\u0011\u001d\tI\u0006\ta\u0001\u000b7\u0003\u0002\"LA/\u0003\u0016uUQ\u0013\t\u0005{\u0016}e'C\u0002\u0006\"\u0016\u00121B\u00127po6{g.\u001b;pe\u00069Qn\u001c8ji>\u0014XCACT!\u0015\u00016ANCU!\u0019i\u0013q_!\u0006\u001e\u0002")
public interface FlowOpsMat<Out, Mat>
extends FlowOps<Out, Mat> {
    public <T, Mat2, Mat3> FlowOpsMat viaMat(Graph<FlowShape<Out, T>, Mat2> var1, Function2<Mat, Mat2, Mat3> var2);

    public <Mat2, Mat3> Graph toMat(Graph<SinkShape<Out>, Mat2> var1, Function2<Mat, Mat2, Mat3> var2);

    public static /* synthetic */ FlowOpsMat flatMapPrefixMat$(FlowOpsMat $this, int n, Function1 f, Function2 matF) {
        return $this.flatMapPrefixMat(n, f, matF);
    }

    default public <Out2, Mat2, Mat3> FlowOpsMat flatMapPrefixMat(int n, Function1<Seq<Out>, Flow<Out, Out2, Mat2>> f, Function2<Mat, Future<Mat2>, Mat3> matF) {
        return this.viaMat(new FlatMapPrefix(n, f), matF);
    }

    public static /* synthetic */ FlowOpsMat zipMat$(FlowOpsMat $this, Graph that, Function2 matF) {
        return $this.zipMat(that, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat zipMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.zipGraph(that), matF);
    }

    public static /* synthetic */ FlowOpsMat zipAllMat$(FlowOpsMat $this, Graph that, Object thisElem, Object thatElem, Function2 matF) {
        return $this.zipAllMat(that, thisElem, thatElem, matF);
    }

    default public <U, Mat2, Mat3, A> FlowOpsMat zipAllMat(Graph<SourceShape<U>, Mat2> that, A thisElem, U thatElem, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.zipAllFlow(that, thisElem, thatElem), matF);
    }

    public static /* synthetic */ FlowOpsMat zipWithMat$(FlowOpsMat $this, Graph that, Function2 combine, Function2 matF) {
        return $this.zipWithMat(that, combine, matF);
    }

    default public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipWithMat(Graph<SourceShape<Out2>, Mat2> that, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.zipWithGraph(that, combine), matF);
    }

    public static /* synthetic */ FlowOpsMat zipLatestMat$(FlowOpsMat $this, Graph that, Function2 matF) {
        return $this.zipLatestMat(that, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat zipLatestMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.zipLatestGraph(that), matF);
    }

    public static /* synthetic */ FlowOpsMat zipLatestWithMat$(FlowOpsMat $this, Graph that, Function2 combine, Function2 matF) {
        return $this.zipLatestWithMat(that, combine, matF);
    }

    default public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipLatestWithMat(Graph<SourceShape<Out2>, Mat2> that, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return this.zipLatestWithMat(that, true, combine, matF);
    }

    public static /* synthetic */ FlowOpsMat zipLatestWithMat$(FlowOpsMat $this, Graph that, boolean eagerComplete, Function2 combine, Function2 matF) {
        return $this.zipLatestWithMat(that, eagerComplete, combine, matF);
    }

    default public <Out2, Out3, Mat2, Mat3> FlowOpsMat zipLatestWithMat(Graph<SourceShape<Out2>, Mat2> that, boolean eagerComplete, Function2<Out, Out2, Out3> combine, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.zipLatestWithGraph(that, eagerComplete, combine), matF);
    }

    public static /* synthetic */ FlowOpsMat mergeMat$(FlowOpsMat $this, Graph that, boolean eagerComplete, Function2 matF) {
        return $this.mergeMat(that, eagerComplete, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat mergeMat(Graph<SourceShape<U>, Mat2> that, boolean eagerComplete, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.mergeGraph(that, eagerComplete), matF);
    }

    public static /* synthetic */ boolean mergeMat$default$2$(FlowOpsMat $this) {
        return $this.mergeMat$default$2();
    }

    default public <U, Mat2, Mat3> boolean mergeMat$default$2() {
        return false;
    }

    public static /* synthetic */ FlowOpsMat interleaveMat$(FlowOpsMat $this, Graph that, int segmentSize, Function2 matF) {
        return $this.interleaveMat(that, segmentSize, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat interleaveMat(Graph<SourceShape<U>, Mat2> that, int segmentSize, Function2<Mat, Mat2, Mat3> matF) {
        return this.interleaveMat(that, segmentSize, false, matF);
    }

    public static /* synthetic */ FlowOpsMat interleaveMat$(FlowOpsMat $this, Graph that, int segmentSize, boolean eagerClose, Function2 matF) {
        return $this.interleaveMat(that, segmentSize, eagerClose, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat interleaveMat(Graph<SourceShape<U>, Mat2> that, int segmentSize, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.interleaveGraph(that, segmentSize, eagerClose), matF);
    }

    public static /* synthetic */ FlowOpsMat mergeLatestMat$(FlowOpsMat $this, Graph that, boolean eagerClose, Function2 matF) {
        return $this.mergeLatestMat(that, eagerClose, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat mergeLatestMat(Graph<SourceShape<U>, Mat2> that, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.mergeLatestGraph(that, eagerClose), matF);
    }

    public static /* synthetic */ FlowOpsMat mergePreferredMat$(FlowOpsMat $this, Graph that, boolean preferred, boolean eagerClose, Function2 matF) {
        return $this.mergePreferredMat(that, preferred, eagerClose, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat mergePreferredMat(Graph<SourceShape<U>, Mat2> that, boolean preferred, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.mergePreferredGraph(that, preferred, eagerClose), matF);
    }

    public static /* synthetic */ FlowOpsMat mergePrioritizedMat$(FlowOpsMat $this, Graph that, int leftPriority, int rightPriority, boolean eagerClose, Function2 matF) {
        return $this.mergePrioritizedMat(that, leftPriority, rightPriority, eagerClose, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat mergePrioritizedMat(Graph<SourceShape<U>, Mat2> that, int leftPriority, int rightPriority, boolean eagerClose, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.mergePrioritizedGraph(that, leftPriority, rightPriority, eagerClose), matF);
    }

    public static /* synthetic */ FlowOpsMat mergeSortedMat$(FlowOpsMat $this, Graph that, Function2 matF, Ordering ord) {
        return $this.mergeSortedMat(that, matF, ord);
    }

    default public <U, Mat2, Mat3> FlowOpsMat mergeSortedMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF, Ordering<U> ord) {
        return this.viaMat(this.mergeSortedGraph(that, ord), matF);
    }

    public static /* synthetic */ FlowOpsMat concatMat$(FlowOpsMat $this, Graph that, Function2 matF) {
        return $this.concatMat(that, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat concatMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.concatGraph(that, true), matF);
    }

    public static /* synthetic */ FlowOpsMat concatLazyMat$(FlowOpsMat $this, Graph that, Function2 matF) {
        return $this.concatLazyMat(that, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat concatLazyMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.concatGraph(that, false), matF);
    }

    public static /* synthetic */ FlowOpsMat prependMat$(FlowOpsMat $this, Graph that, Function2 matF) {
        return $this.prependMat(that, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat prependMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.prependGraph(that, true), matF);
    }

    public static /* synthetic */ FlowOpsMat prependLazyMat$(FlowOpsMat $this, Graph that, Function2 matF) {
        return $this.prependLazyMat(that, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat prependLazyMat(Graph<SourceShape<U>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.prependGraph(that, true), matF);
    }

    public static /* synthetic */ FlowOpsMat orElseMat$(FlowOpsMat $this, Graph secondary, Function2 matF) {
        return $this.orElseMat(secondary, matF);
    }

    default public <U, Mat2, Mat3> FlowOpsMat orElseMat(Graph<SourceShape<U>, Mat2> secondary, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.orElseGraph(secondary), matF);
    }

    public static /* synthetic */ FlowOpsMat alsoToMat$(FlowOpsMat $this, Graph that, Function2 matF) {
        return $this.alsoToMat(that, matF);
    }

    default public <Mat2, Mat3> FlowOpsMat alsoToMat(Graph<SinkShape<Out>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.alsoToGraph(that), matF);
    }

    public static /* synthetic */ FlowOpsMat divertToMat$(FlowOpsMat $this, Graph that, Function1 when, Function2 matF) {
        return $this.divertToMat(that, when, matF);
    }

    default public <Mat2, Mat3> FlowOpsMat divertToMat(Graph<SinkShape<Out>, Mat2> that, Function1<Out, Object> when, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.divertToGraph(that, when), matF);
    }

    public static /* synthetic */ FlowOpsMat wireTapMat$(FlowOpsMat $this, Graph that, Function2 matF) {
        return $this.wireTapMat(that, matF);
    }

    default public <Mat2, Mat3> FlowOpsMat wireTapMat(Graph<SinkShape<Out>, Mat2> that, Function2<Mat, Mat2, Mat3> matF) {
        return this.viaMat(this.wireTapGraph(that), matF);
    }

    public static /* synthetic */ FlowOpsMat watchTermination$(FlowOpsMat $this, Function2 matF) {
        return $this.watchTermination(matF);
    }

    default public <Mat2> FlowOpsMat watchTermination(Function2<Mat, Future<Done>, Mat2> matF) {
        return this.viaMat(GraphStages$.MODULE$.terminationWatcher(), matF);
    }

    public <Mat2> FlowOpsMat mapMaterializedValue(Function1<Mat, Mat2> var1);

    public static /* synthetic */ FlowOpsMat monitorMat$(FlowOpsMat $this, Function2 combine) {
        return $this.monitorMat(combine);
    }

    default public <Mat2> FlowOpsMat monitorMat(Function2<Mat, FlowMonitor<Out>, Mat2> combine) {
        return this.viaMat(GraphStages$.MODULE$.monitor(), combine);
    }

    public static /* synthetic */ FlowOpsMat monitor$(FlowOpsMat $this) {
        return $this.monitor();
    }

    default public FlowOpsMat monitor() {
        return this.monitorMat(Keep$.MODULE$.both());
    }

    public static void $init$(FlowOpsMat $this) {
    }
}

