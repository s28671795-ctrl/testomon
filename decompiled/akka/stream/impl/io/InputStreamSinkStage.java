/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.SinkShape$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.io.InputStreamAdapter;
import akka.stream.impl.io.InputStreamSinkStage$;
import akka.stream.impl.io.InputStreamSinkStage$Close$;
import akka.stream.impl.io.InputStreamSinkStage$Finished$;
import akka.stream.impl.io.InputStreamSinkStage$Initialized$;
import akka.stream.impl.io.InputStreamSinkStage$ReadElementAcknowledgement$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.util.ByteString;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.LinkedBlockingDeque;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\rMqA\u00025j\u0011\u0003i\u0017O\u0002\u0004tS\"\u0005Q\u000e\u001e\u0005\u0006w\u0006!\t! \u0004\b}\u0006\u0001\n1%\t\u0000\u000f\u001d\t9*\u0001EA\u0003\u001b3q!a\"\u0002\u0011\u0003\u000bI\t\u0003\u0004|\u000b\u0011\u0005\u00111\u0012\u0005\n\u0003[)\u0011\u0011!C!\u0003_A\u0011\"!\u0011\u0006\u0003\u0003%\t!a\u0011\t\u0013\u0005-S!!A\u0005\u0002\u0005=\u0005\"CA-\u000b\u0005\u0005I\u0011IA.\u0011%\tI'BA\u0001\n\u0003\t\u0019\nC\u0005\u0002v\u0015\t\t\u0011\"\u0011\u0002x!I\u0011\u0011P\u0003\u0002\u0002\u0013\u0005\u00131\u0010\u0005\n\u0003{*\u0011\u0011!C\u0005\u0003\u007f:q!!'\u0002\u0011\u0003\u000bYCB\u0004\u0002\u0004\u0005A\t)!\u0002\t\rm\u0004B\u0011AA\u0015\u0011%\ti\u0003EA\u0001\n\u0003\ny\u0003C\u0005\u0002BA\t\t\u0011\"\u0001\u0002D!I\u00111\n\t\u0002\u0002\u0013\u0005\u0011Q\n\u0005\n\u00033\u0002\u0012\u0011!C!\u00037B\u0011\"!\u001b\u0011\u0003\u0003%\t!a\u001b\t\u0013\u0005U\u0004#!A\u0005B\u0005]\u0004\"CA=!\u0005\u0005I\u0011IA>\u0011%\ti\bEA\u0001\n\u0013\tyHB\u0005\u0002\u001c\u0006\u0001\n1%\t\u0002\u001e\u001a1\u0011\u0011U\u0001A\u0003GC!\"a*\u001c\u0005+\u0007I\u0011AAU\u0011)\t9l\u0007B\tB\u0003%\u00111\u0016\u0005\u0007wn!\t!!/\t\u0013\u0005}6$!A\u0005\u0002\u0005\u0005\u0007\"CAc7E\u0005I\u0011AAd\u0011%\ticGA\u0001\n\u0003\ny\u0003C\u0005\u0002Bm\t\t\u0011\"\u0001\u0002D!I\u00111J\u000e\u0002\u0002\u0013\u0005\u0011Q\u001c\u0005\n\u00033Z\u0012\u0011!C!\u00037B\u0011\"!\u001b\u001c\u0003\u0003%\t!!9\t\u0013\u0005\u00158$!A\u0005B\u0005\u001d\b\"CA;7\u0005\u0005I\u0011IA<\u0011%\tIhGA\u0001\n\u0003\nY\bC\u0005\u0002ln\t\t\u0011\"\u0011\u0002n\u001eI!qH\u0001\u0002\u0002#\u0005!\u0011\t\u0004\n\u0003C\u000b\u0011\u0011!E\u0001\u0005\u0007Baa_\u0016\u0005\u0002\te\u0003\"CA=W\u0005\u0005IQIA>\u0011%\u0011YfKA\u0001\n\u0003\u0013i\u0006C\u0005\u0003b-\n\t\u0011\"!\u0003d!I\u0011QP\u0016\u0002\u0002\u0013%\u0011qP\u0004\b\u0005_\n\u0001\u0012\u0011B\u0013\r\u001d\u0011y\"\u0001EA\u0005CAaa\u001f\u001a\u0005\u0002\t\r\u0002\"CA\u0017e\u0005\u0005I\u0011IA\u0018\u0011%\t\tEMA\u0001\n\u0003\t\u0019\u0005C\u0005\u0002LI\n\t\u0011\"\u0001\u0003(!I\u0011\u0011\f\u001a\u0002\u0002\u0013\u0005\u00131\f\u0005\n\u0003S\u0012\u0014\u0011!C\u0001\u0005WA\u0011\"!\u001e3\u0003\u0003%\t%a\u001e\t\u0013\u0005e$'!A\u0005B\u0005m\u0004\"CA?e\u0005\u0005I\u0011BA@\u000f\u001d\u0011\t(\u0001EA\u0005k1qAa\f\u0002\u0011\u0003\u0013\t\u0004\u0003\u0004|{\u0011\u0005!1\u0007\u0005\n\u0003[i\u0014\u0011!C!\u0003_A\u0011\"!\u0011>\u0003\u0003%\t!a\u0011\t\u0013\u0005-S(!A\u0005\u0002\t]\u0002\"CA-{\u0005\u0005I\u0011IA.\u0011%\tI'PA\u0001\n\u0003\u0011Y\u0004C\u0005\u0002vu\n\t\u0011\"\u0011\u0002x!I\u0011\u0011P\u001f\u0002\u0002\u0013\u0005\u00131\u0010\u0005\n\u0003{j\u0014\u0011!C\u0005\u0003\u007f2a!!=\u0002\u0001\u0006M\bBCA{\u000f\nU\r\u0011\"\u0001\u0002x\"Q\u0011q`$\u0003\u0012\u0003\u0006I!!?\t\rm<E\u0011\u0001B\u0001\u0011%\tylRA\u0001\n\u0003\u00119\u0001C\u0005\u0002F\u001e\u000b\n\u0011\"\u0001\u0003\f!I\u0011QF$\u0002\u0002\u0013\u0005\u0013q\u0006\u0005\n\u0003\u0003:\u0015\u0011!C\u0001\u0003\u0007B\u0011\"a\u0013H\u0003\u0003%\tAa\u0004\t\u0013\u0005es)!A\u0005B\u0005m\u0003\"CA5\u000f\u0006\u0005I\u0011\u0001B\n\u0011%\t)oRA\u0001\n\u0003\u00129\u0002C\u0005\u0002v\u001d\u000b\t\u0011\"\u0011\u0002x!I\u0011\u0011P$\u0002\u0002\u0013\u0005\u00131\u0010\u0005\n\u0003W<\u0015\u0011!C!\u000579\u0011Ba\u001d\u0002\u0003\u0003E\tA!\u001e\u0007\u0013\u0005E\u0018!!A\t\u0002\t]\u0004BB>X\t\u0003\u0011Y\bC\u0005\u0002z]\u000b\t\u0011\"\u0012\u0002|!I!1L,\u0002\u0002\u0013\u0005%Q\u0010\u0005\n\u0005C:\u0016\u0011!CA\u0005\u0003C\u0011\"! X\u0003\u0003%I!a \u0007\u0013\t\u001d\u0015\u0001%A\u0012\"\t%\u0005b\u0002BF;\u001a\u0005!Q\u0012\u0004\u0007g&\u0014QNa)\t\u0015\t}vL!A!\u0002\u0013\u0011\t\r\u0003\u0004|?\u0012\u0005!\u0011\u001b\u0005\n\u0005/|&\u0019!C\u0001\u00053D\u0001B!9`A\u0003%!1\u001c\u0005\b\u0005G|F\u0011\tBs\u0011%\u0011io\u0018b\u0001\n\u0003\u0012y\u000f\u0003\u0005\u0003r~\u0003\u000b\u0011\u0002BY\u0011\u001d\u0011\u0019p\u0018C!\u0005k\fA#\u00138qkR\u001cFO]3b[NKgn[*uC\u001e,'B\u00016l\u0003\tIwN\u0003\u0002m[\u0006!\u0011.\u001c9m\u0015\tqw.\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002a\u0006!\u0011m[6b!\t\u0011\u0018!D\u0001j\u0005QIe\u000e];u'R\u0014X-Y7TS:\\7\u000b^1hKN\u0011\u0011!\u001e\t\u0003mfl\u0011a\u001e\u0006\u0002q\u0006)1oY1mC&\u0011!p\u001e\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012!\u001d\u0002\u0016\u0003\u0012\f\u0007\u000f^3s)>\u001cF/Y4f\u001b\u0016\u001c8/Y4f'\t\u0019Q/K\u0002\u0004!\u0015\u0011Qa\u00117pg\u0016\u001c\u0002\u0002E;\u0002\b\u0005-\u0011\u0011\u0003\t\u0004\u0003\u0013\u0019Q\"A\u0001\u0011\u0007Y\fi!C\u0002\u0002\u0010]\u0014q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u0002\u0014\u0005\rb\u0002BA\u000b\u0003?qA!a\u0006\u0002\u001e5\u0011\u0011\u0011\u0004\u0006\u0004\u00037a\u0018A\u0002\u001fs_>$h(C\u0001y\u0013\r\t\tc^\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\t)#a\n\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0007\u0005\u0005r\u000f\u0006\u0002\u0002,A\u0019\u0011\u0011\u0002\t\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\t\t\u0004\u0005\u0003\u00024\u0005uRBAA\u001b\u0015\u0011\t9$!\u000f\u0002\t1\fgn\u001a\u0006\u0003\u0003w\tAA[1wC&!\u0011qHA\u001b\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011Q\t\t\u0004m\u0006\u001d\u0013bAA%o\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011qJA+!\r1\u0018\u0011K\u0005\u0004\u0003':(aA!os\"I\u0011q\u000b\u000b\u0002\u0002\u0003\u0007\u0011QI\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005u\u0003CBA0\u0003K\ny%\u0004\u0002\u0002b)\u0019\u00111M<\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002h\u0005\u0005$\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\u001c\u0002tA\u0019a/a\u001c\n\u0007\u0005EtOA\u0004C_>dW-\u00198\t\u0013\u0005]c#!AA\u0002\u0005=\u0013\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\u0015\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005E\u0012\u0001D<sSR,'+\u001a9mC\u000e,GCAAA!\u0011\t\u0019$a!\n\t\u0005\u0015\u0015Q\u0007\u0002\u0007\u001f\nTWm\u0019;\u00035I+\u0017\rZ#mK6,g\u000e^!dW:|w\u000f\\3eO\u0016lWM\u001c;\u0014\u0011\u0015)\u0018qAA\u0006\u0003#!\"!!$\u0011\u0007\u0005%Q\u0001\u0006\u0003\u0002P\u0005E\u0005\"CA,\u0013\u0005\u0005\t\u0019AA#)\u0011\ti'!&\t\u0013\u0005]3\"!AA\u0002\u0005=\u0013A\u0007*fC\u0012,E.Z7f]R\f5m\u001b8po2,GmZ3nK:$\u0018!B\"m_N,'AF*ue\u0016\fW\u000eV8BI\u0006\u0004H/\u001a:NKN\u001c\u0018mZ3\u0014\u0005i)\u0018&\u0002\u000e\u001c\u000fJj$\u0001\u0002#bi\u0006\u001c\u0002bG;\u0002&\u0006-\u0011\u0011\u0003\t\u0004\u0003\u0013Q\u0012\u0001\u00023bi\u0006,\"!a+\u0011\t\u00055\u00161W\u0007\u0003\u0003_S1!!-p\u0003\u0011)H/\u001b7\n\t\u0005U\u0016q\u0016\u0002\u000b\u0005f$Xm\u0015;sS:<\u0017!\u00023bi\u0006\u0004C\u0003BA^\u0003{\u00032!!\u0003\u001c\u0011\u001d\t9K\ba\u0001\u0003W\u000bAaY8qsR!\u00111XAb\u0011%\t9k\bI\u0001\u0002\u0004\tY+\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005%'\u0006BAV\u0003\u0017\\#!!4\u0011\t\u0005=\u0017\u0011\\\u0007\u0003\u0003#TA!a5\u0002V\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003/<\u0018AC1o]>$\u0018\r^5p]&!\u00111\\Ai\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u000b\u0005\u0003\u001f\ny\u000eC\u0005\u0002X\r\n\t\u00111\u0001\u0002FQ!\u0011QNAr\u0011%\t9&JA\u0001\u0002\u0004\ty%\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA\u0019\u0003SD\u0011\"a\u0016'\u0003\u0003\u0005\r!!\u0012\u0002\r\u0015\fX/\u00197t)\u0011\ti'a<\t\u0013\u0005]\u0013&!AA\u0002\u0005=#A\u0002$bS2,Gm\u0005\u0005Hk\u0006\u0015\u00161BA\t\u0003\u0015\u0019\u0017-^:f+\t\tI\u0010\u0005\u0003\u0002\u0014\u0005m\u0018\u0002BA\u007f\u0003O\u0011\u0011\u0002\u00165s_^\f'\r\\3\u0002\r\r\fWo]3!)\u0011\u0011\u0019A!\u0002\u0011\u0007\u0005%q\tC\u0004\u0002v*\u0003\r!!?\u0015\t\t\r!\u0011\u0002\u0005\n\u0003k\\\u0005\u0013!a\u0001\u0003s,\"A!\u0004+\t\u0005e\u00181\u001a\u000b\u0005\u0003\u001f\u0012\t\u0002C\u0005\u0002X=\u000b\t\u00111\u0001\u0002FQ!\u0011Q\u000eB\u000b\u0011%\t9&UA\u0001\u0002\u0004\ty\u0005\u0006\u0003\u00022\te\u0001\"CA,%\u0006\u0005\t\u0019AA#)\u0011\tiG!\b\t\u0013\u0005]S+!AA\u0002\u0005=#\u0001\u0003$j]&\u001c\b.\u001a3\u0014\u0011I*\u0018QUA\u0006\u0003#!\"A!\n\u0011\u0007\u0005%!\u0007\u0006\u0003\u0002P\t%\u0002\"CA,m\u0005\u0005\t\u0019AA#)\u0011\tiG!\f\t\u0013\u0005]\u0003(!AA\u0002\u0005=#aC%oSRL\u0017\r\\5{K\u0012\u001c\u0002\"P;\u0002&\u0006-\u0011\u0011\u0003\u000b\u0003\u0005k\u00012!!\u0003>)\u0011\tyE!\u000f\t\u0013\u0005]\u0013)!AA\u0002\u0005\u0015C\u0003BA7\u0005{A\u0011\"a\u0016D\u0003\u0003\u0005\r!a\u0014\u0002\t\u0011\u000bG/\u0019\t\u0004\u0003\u0013Y3#B\u0016\u0003F\tE\u0003\u0003\u0003B$\u0005\u001b\nY+a/\u000e\u0005\t%#b\u0001B&o\u00069!/\u001e8uS6,\u0017\u0002\u0002B(\u0005\u0013\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\u0011\u0019Fa\u0016\u000e\u0005\tU#b\u00016\u0002:%!\u0011Q\u0005B+)\t\u0011\t%A\u0003baBd\u0017\u0010\u0006\u0003\u0002<\n}\u0003bBAT]\u0001\u0007\u00111V\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\u0011)Ga\u001b\u0011\u000bY\u00149'a+\n\u0007\t%tO\u0001\u0004PaRLwN\u001c\u0005\n\u0005[z\u0013\u0011!a\u0001\u0003w\u000b1\u0001\u001f\u00131\u0003!1\u0015N\\5tQ\u0016$\u0017aC%oSRL\u0017\r\\5{K\u0012\faAR1jY\u0016$\u0007cAA\u0005/N)qK!\u001f\u0003RAA!q\tB'\u0003s\u0014\u0019\u0001\u0006\u0002\u0003vQ!!1\u0001B@\u0011\u001d\t)P\u0017a\u0001\u0003s$BAa!\u0003\u0006B)aOa\u001a\u0002z\"I!QN.\u0002\u0002\u0003\u0007!1\u0001\u0002\u0012'R\fw-Z,ji\"\u001c\u0015\r\u001c7cC\u000e\\7CA/v\u0003\u00199\u0018m[3VaR!!q\u0012BK!\r1(\u0011S\u0005\u0004\u0005';(\u0001B+oSRDqAa&_\u0001\u0004\t9!A\u0002ng\u001eL3!\u0018BN\r\u0019\u0011i*\u0018\u0001\u0003 \niA\b\\8dC2\u00043\r[5mIz\u001abAa'\u0002\u0002\n\u0005\u0006cAA\u0005;N\u0019qL!*\u0011\u0011\t\u001d&Q\u0016BY\u0005sk!A!+\u000b\u0007\t-V.A\u0003ti\u0006<W-\u0003\u0003\u00030\n%&aH$sCBD7\u000b^1hK^KG\u000f['bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKB1!1\u0017B[\u0003Wk\u0011!\\\u0005\u0004\u0005ok'!C*j].\u001c\u0006.\u00199f!\u0011\u0011\u0019Fa/\n\t\tu&Q\u000b\u0002\f\u0013:\u0004X\u000f^*ue\u0016\fW.A\u0006sK\u0006$G+[7f_V$\b\u0003\u0002Bb\u0005\u001bl!A!2\u000b\t\t\u001d'\u0011Z\u0001\tIV\u0014\u0018\r^5p]*\u0019!1Z<\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0003\u0003P\n\u0015'A\u0004$j]&$X\rR;sCRLwN\u001c\u000b\u0005\u0005'\u0014)\u000e\u0005\u0002s?\"9!qX1A\u0002\t\u0005\u0017AA5o+\t\u0011Y\u000e\u0005\u0004\u00034\nu\u00171V\u0005\u0004\u0005?l'!B%oY\u0016$\u0018aA5oA\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0005\t\u001d\b\u0003\u0002BZ\u0005SL1Aa;n\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0003\u0005c\u000baa\u001d5ba\u0016\u0004\u0013aH2sK\u0006$X\rT8hS\u000e\fe\u000eZ'bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKR!!q_B\u0002!\u001d1(\u0011 B\u007f\u0005sK1Aa?x\u0005\u0019!V\u000f\u001d7feA!!q\u0015B\u0000\u0013\u0011\u0019\tA!+\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDqa!\u0002h\u0001\u0004\u00119/A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000fK\u0002`\u0007\u0013\u0001Baa\u0003\u0004\u00105\u00111Q\u0002\u0006\u0004\u0003/|\u0017\u0002BB\t\u0007\u001b\u00111\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class InputStreamSinkStage
extends GraphStageWithMaterializedValue<SinkShape<ByteString>, InputStream> {
    private final FiniteDuration readTimeout;
    private final Inlet<ByteString> in;
    private final SinkShape<ByteString> shape;

    public Inlet<ByteString> in() {
        return this.in;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.inputStreamSink();
    }

    @Override
    public SinkShape<ByteString> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, InputStream> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        int maxBuffer = inheritedAttributes.get(new Attributes.InputBuffer(16, 16), ClassTag$.MODULE$.apply(Attributes.InputBuffer.class)).max();
        Predef$.MODULE$.require(maxBuffer > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer size must be greater than 0");
        LinkedBlockingDeque<StreamToAdapterMessage> dataQueue = new LinkedBlockingDeque<StreamToAdapterMessage>(maxBuffer + 2);
        StageWithCallback logic = new StageWithCallback(this, dataQueue){
            private boolean completionSignalled;
            private final AsyncCallback<AdapterToStageMessage> callback;
            private final /* synthetic */ InputStreamSinkStage $outer;
            private final LinkedBlockingDeque dataQueue$1;

            public boolean completionSignalled() {
                return this.completionSignalled;
            }

            public void completionSignalled_$eq(boolean x$1) {
                this.completionSignalled = x$1;
            }

            private AsyncCallback<AdapterToStageMessage> callback() {
                return this.callback;
            }

            public void wakeUp(AdapterToStageMessage msg) {
                this.callback().invoke(msg);
            }

            private void sendPullIfAllowed() {
                if (this.dataQueue$1.remainingCapacity() > 1 && !this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void preStart() {
                this.dataQueue$1.add(InputStreamSinkStage$Initialized$.MODULE$);
                this.pull(this.$outer.in());
            }

            public void onPush() {
                Predef$.MODULE$.require(this.dataQueue$1.remainingCapacity() > 1);
                ByteString bs = this.grab(this.$outer.in());
                Serializable serializable = bs.nonEmpty() ? BoxesRunTime.boxToBoolean(this.dataQueue$1.add(new Data(bs))) : BoxedUnit.UNIT;
                if (this.dataQueue$1.remainingCapacity() > 1) {
                    this.sendPullIfAllowed();
                    return;
                }
            }

            public void onUpstreamFinish() {
                this.dataQueue$1.add(InputStreamSinkStage$Finished$.MODULE$);
                this.completionSignalled_$eq(true);
                this.completeStage();
            }

            public void onUpstreamFailure(Throwable ex) {
                this.dataQueue$1.add(new Failed(ex));
                this.completionSignalled_$eq(true);
                this.failStage(ex);
            }

            public void postStop() {
                if (!this.completionSignalled()) {
                    this.dataQueue$1.add(new Failed(new AbruptStageTerminationException(this)));
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$callback$1($anon$1 $this, AdapterToStageMessage x0$1) {
                AdapterToStageMessage adapterToStageMessage = x0$1;
                if (InputStreamSinkStage$ReadElementAcknowledgement$.MODULE$.equals(adapterToStageMessage)) {
                    $this.sendPullIfAllowed();
                    return;
                }
                if (InputStreamSinkStage$Close$.MODULE$.equals(adapterToStageMessage)) {
                    $this.completeStage();
                    return;
                }
                throw new MatchError(adapterToStageMessage);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.dataQueue$1 = dataQueue$1;
                super($outer.shape());
                InHandler.$init$(this);
                this.completionSignalled = false;
                this.callback = this.getAsyncCallback((Function1<AdapterToStageMessage, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$callback$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.setHandler($outer.in(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$callback$1$adapted(akka.stream.impl.io.InputStreamSinkStage$$anon$1 akka.stream.impl.io.InputStreamSinkStage$AdapterToStageMessage )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, InputStream>((GraphStageLogic)((Object)logic), new InputStreamAdapter(dataQueue, (Function1<AdapterToStageMessage, BoxedUnit>)((Function1<AdapterToStageMessage, Object> & Serializable)arg_0 -> InputStreamSinkStage.$anonfun$createLogicAndMaterializedValue$2$adapted((GraphStageLogic)((Object)logic), arg_0)), this.readTimeout));
    }

    public static final /* synthetic */ void $anonfun$createLogicAndMaterializedValue$2(GraphStageLogic logic$1, AdapterToStageMessage msg) {
        ((StageWithCallback)((Object)logic$1)).wakeUp(msg);
    }

    public InputStreamSinkStage(FiniteDuration readTimeout) {
        this.readTimeout = readTimeout;
        this.in = Inlet$.MODULE$.apply("InputStreamSink.in");
        this.shape = SinkShape$.MODULE$.of(this.in());
    }

    public static final /* synthetic */ Object $anonfun$createLogicAndMaterializedValue$2$adapted(GraphStageLogic logic$1, AdapterToStageMessage msg) {
        InputStreamSinkStage.$anonfun$createLogicAndMaterializedValue$2(logic$1, msg);
        return BoxedUnit.UNIT;
    }

    public static interface AdapterToStageMessage {
    }

    public static class Data
    implements StreamToAdapterMessage,
    Product,
    Serializable {
        private final ByteString data;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ByteString data() {
            return this.data;
        }

        public Data copy(ByteString data) {
            return new Data(data);
        }

        public ByteString copy$default$1() {
            return this.data();
        }

        @Override
        public String productPrefix() {
            return "Data";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.data();
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
            return x$1 instanceof Data;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "data";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
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
            if (!(object instanceof Data)) return false;
            boolean bl = true;
            if (!bl) return false;
            Data data = (Data)x$1;
            ByteString byteString = this.data();
            ByteString byteString2 = data.data();
            if (byteString == null) {
                if (byteString2 != null) {
                    return false;
                }
            } else if (!((Object)byteString).equals(byteString2)) return false;
            if (!data.canEqual(this)) return false;
            return true;
        }

        public Data(ByteString data) {
            this.data = data;
            Product.$init$(this);
        }
    }

    public static class Failed
    implements StreamToAdapterMessage,
    Product,
    Serializable {
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        public Failed copy(Throwable cause) {
            return new Failed(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "Failed";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.cause();
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
            return x$1 instanceof Failed;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cause";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
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
            if (!(object instanceof Failed)) return false;
            boolean bl = true;
            if (!bl) return false;
            Failed failed = (Failed)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = failed.cause();
            if (throwable == null) {
                if (throwable2 != null) {
                    return false;
                }
            } else if (!throwable.equals(throwable2)) return false;
            if (!failed.canEqual(this)) return false;
            return true;
        }

        public Failed(Throwable cause) {
            this.cause = cause;
            Product.$init$(this);
        }
    }

    public static interface StageWithCallback {
        public void wakeUp(AdapterToStageMessage var1);
    }

    public static interface StreamToAdapterMessage {
    }
}

