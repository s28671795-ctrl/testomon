/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.NotUsed$;
import akka.dispatch.AbstractNodeQueue;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.scaladsl.MergeHub$;
import akka.stream.scaladsl.MergeHub$Deregister$;
import akka.stream.scaladsl.MergeHub$Element$;
import akka.stream.scaladsl.MergeHub$Register$;
import akka.stream.scaladsl.MergeHubDrainingControlImpl;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.atomic.AtomicLong;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.mutable.LongMap;
import scala.collection.mutable.LongMap$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.runtime.java8.JFunction0$mcV$sp;

@ScalaSignature(bytes="\u0006\u0005\u0011\u001dsAB?\u007f\u0011\u0003\tYAB\u0004\u0002\u0010yD\t!!\u0005\t\u000f\u0005}\u0011\u0001\"\u0001\u0002\"!I\u00111E\u0001C\u0002\u0013%\u0011Q\u0005\u0005\t\u0003[\t\u0001\u0015!\u0003\u0002(\u0019I\u0011qF\u0001\u0011\u0002G\u0005\u0012\u0011\u0007\u0005\b\u0003g)a\u0011AA\u001b\u0011\u001d\t\u0019%\u0001C\u0001\u0003\u000bBq!a\u001e\u0002\t\u0003\tI\bC\u0004\u0002D\u0005!\t!!%\t\u000f\u0005]\u0014\u0001\"\u0001\u0002\u001e\u001a1\u00111V\u0001\u0003\u0003[C!\"a2\f\u0005\u0003\u0005\u000b\u0011BAe\u0011)\tIn\u0003B\u0001B\u0003%\u00111\u001c\u0005\b\u0003?YA\u0011AAq\u0011%\tI/AI\u0001\n\u0003\tYO\u0002\u0005\u0002\u0010y\u0004\u0011Q\u0001B\u0006\u0011)\t)\b\u0005B\u0001B\u0003%\u0011q\u0005\u0005\u000b\u0005_\u0001\"\u0011!Q\u0001\n\u0005E\bbBA\u0010!\u0011\u0005!\u0011\u0007\u0005\n\u0005s\u0001\"\u0019!C\u0001\u0005wA\u0001Ba\u0011\u0011A\u0003%!Q\b\u0005\n\u0005\u000b\u0002\"\u0019!C!\u0005\u000fB\u0001B!\u0013\u0011A\u0003%!1\u0004\u0005\t\u0005\u0017\u0002\u0002\u0015!\u0003\u0002(\u0019I!Q\n\t\u0011\u0002G%\"q\n\u0005\b\u0005#Jb\u0011\u0001B*\r\u0019\u0011Y\r\u0005#\u0003N\"Q!\u0011K\u000e\u0003\u0016\u0004%\tAa\u0015\t\u0015\tE4D!E!\u0002\u0013\u0011)\u0006\u0003\u0006\u0003Pn\u0011)\u001a!C\u0001\u0005#D!Ba5\u001c\u0005#\u0005\u000b\u0011\u0002B\u0012\u0011\u001d\tyb\u0007C\u0001\u0005+D\u0011B!\u001f\u001c\u0003\u0003%\tA!8\t\u0013\t}4$%A\u0005\u0002\t\u0005\u0005\"\u0003Br7E\u0005I\u0011\u0001Bs\u0011%\u0011)iGA\u0001\n\u0003\u00129\tC\u0005\u0003\u0018n\t\t\u0011\"\u0001\u0002&!I!\u0011T\u000e\u0002\u0002\u0013\u0005!\u0011\u001e\u0005\n\u0005C[\u0012\u0011!C!\u0005GC\u0011B!-\u001c\u0003\u0003%\tA!<\t\u0013\t]6$!A\u0005B\tE\b\"\u0003B_7\u0005\u0005I\u0011\tB`\u0011%\u0011\tmGA\u0001\n\u0003\u0012\u0019\rC\u0005\u0003Fn\t\t\u0011\"\u0011\u0003v\u001eI11\u0006\t\u0002\u0002#%1Q\u0006\u0004\n\u0005\u0017\u0004\u0012\u0011!E\u0005\u0007_Aq!a\b/\t\u0003\u00199\u0005C\u0005\u0003B:\n\t\u0011\"\u0012\u0003D\"I1\u0011\n\u0018\u0002\u0002\u0013\u000551\n\u0005\n\u0007#r\u0013\u0011!CA\u0007'2aA!?\u0011\t\nm\bB\u0003B)g\tU\r\u0011\"\u0001\u0003T!Q!\u0011O\u001a\u0003\u0012\u0003\u0006IA!\u0016\t\u0015\tu8G!f\u0001\n\u0003\u0011y\u0010\u0003\u0006\u0004\bM\u0012\t\u0012)A\u0005\u0007\u0003Aq!a\b4\t\u0003\u0019I\u0001C\u0005\u0003zM\n\t\u0011\"\u0001\u0004\u0012!I!qP\u001a\u0012\u0002\u0013\u0005!\u0011\u0011\u0005\n\u0005G\u001c\u0014\u0013!C\u0001\u0007/A\u0011B!\"4\u0003\u0003%\tEa\"\t\u0013\t]5'!A\u0005\u0002\u0005\u0015\u0002\"\u0003BMg\u0005\u0005I\u0011AB\u000e\u0011%\u0011\tkMA\u0001\n\u0003\u0012\u0019\u000bC\u0005\u00032N\n\t\u0011\"\u0001\u0004 !I!qW\u001a\u0002\u0002\u0013\u000531\u0005\u0005\n\u0005{\u001b\u0014\u0011!C!\u0005\u007fC\u0011B!14\u0003\u0003%\tEa1\t\u0013\t\u00157'!A\u0005B\r\u001dr!CB1!\u0005\u0005\t\u0012BB2\r%\u0011I\u0010EA\u0001\u0012\u0013\u0019)\u0007C\u0004\u0002 \u0019#\ta!\u001b\t\u0013\t\u0005g)!A\u0005F\t\r\u0007\"CB%\r\u0006\u0005I\u0011QB6\u0011%\u0019\tFRA\u0001\n\u0003\u001b\tH\u0002\u0004\u0003^A!%q\f\u0005\u000b\u0005#Z%Q3A\u0005\u0002\tM\u0003B\u0003B9\u0017\nE\t\u0015!\u0003\u0003V!9\u0011qD&\u0005\u0002\tM\u0004\"\u0003B=\u0017\u0006\u0005I\u0011\u0001B>\u0011%\u0011yhSI\u0001\n\u0003\u0011\t\tC\u0005\u0003\u0006.\u000b\t\u0011\"\u0011\u0003\b\"I!qS&\u0002\u0002\u0013\u0005\u0011Q\u0005\u0005\n\u00053[\u0015\u0011!C\u0001\u00057C\u0011B!)L\u0003\u0003%\tEa)\t\u0013\tE6*!A\u0005\u0002\tM\u0006\"\u0003B\\\u0017\u0006\u0005I\u0011\tB]\u0011%\u0011ilSA\u0001\n\u0003\u0012y\fC\u0005\u0003B.\u000b\t\u0011\"\u0011\u0003D\"I!QY&\u0002\u0002\u0013\u0005#qY\u0004\n\u0007s\u0002\u0012\u0011!E\u0005\u0007w2\u0011B!\u0018\u0011\u0003\u0003EIa! \t\u000f\u0005}1\f\"\u0001\u0004\u0006\"I!\u0011Y.\u0002\u0002\u0013\u0015#1\u0019\u0005\n\u0007\u0013Z\u0016\u0011!CA\u0007\u000fC\u0011b!\u0015\\\u0003\u0003%\tia#\u0007\r\rE\u0005CABJ\u0011)\u0019)\n\u0019B\u0001B\u0003%1\u0011\u0001\u0005\b\u0003?\u0001G\u0011ABL\u0011%\u0019i\n\u0019a\u0001\n\u0013\t)\u0003C\u0005\u0004 \u0002\u0004\r\u0011\"\u0003\u0004\"\"A1Q\u00151!B\u0013\t9\u0003C\u0004\u0004(\u0002$\t!!\u000e\t\u000f\r%\u0006\r\"\u0001\u00026\u0019111\u0016\t\u0003\u0007[C!ba/i\u0005\u0003\u0005\u000b\u0011BB_\u0011\u001d\ty\u0002\u001bC\u0001\u0007\u000fD\u0011b!4i\u0005\u0004%Iaa4\t\u0011\ru\u0007\u000e)A\u0005\u0007#D\u0001ba8iA\u0003&\u0011\u0011\u001f\u0005\t\u0007SD\u0007\u0015)\u0003\u0002r\"A1Q\u001e5!B\u0013\t\t\u0010\u0003\u0005\u0004r\"\u0004\u000b\u0011BBz\u0011!\u0019y\u0010\u001bQ\u0001\n\u0011\u0005\u0001b\u0003C\u0002Q\n\u0005\t\u0011)A\u0005\t\u000bAq\u0001b\u0002i\t\u0013!I\u0001C\u0004\u0005\u0010!$I!!\u000e\t\u000f\u0011E\u0001\u000e\"\u0011\u00026!9A1\u00035\u0005\n\u0011U\u0001b\u0002C\u0013Q\u0012\u0005Aq\u0005\u0005\b\tSAG\u0011\u0001C\u0014\u0011\u001d!Y\u0003\u001bC!\u0003kAA\u0002\"\fi\u0005\u0003\u0015\r\u0011\"\u0001\u0011\t_AA\u0002\"\ri\u0005\u0003\u0005\t\u0011\"\u0001\u0011\tgAq\u0001b\u000e\u0011\t\u0003\"I$\u0001\u0005NKJ<W\rS;c\u0015\ry\u0018\u0011A\u0001\tg\u000e\fG.\u00193tY*!\u00111AA\u0003\u0003\u0019\u0019HO]3b[*\u0011\u0011qA\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0007\u00055\u0011!D\u0001\u007f\u0005!iUM]4f\u0011V\u00147cA\u0001\u0002\u0014A!\u0011QCA\u000e\u001b\t\t9B\u0003\u0002\u0002\u001a\u0005)1oY1mC&!\u0011QDA\f\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\"!a\u0003\u0002\r\r\u000bgnY3m+\t\t9\u0003\u0005\u0003\u0002\u0016\u0005%\u0012\u0002BA\u0016\u0003/\u00111!\u00138u\u0003\u001d\u0019\u0015M\\2fY\u0002\u0012q\u0002\u0012:bS:LgnZ\"p]R\u0014x\u000e\\\n\u0004\u000b\u0005M\u0011\u0001\u00053sC&t\u0017I\u001c3D_6\u0004H.\u001a;f)\t\t9\u0004\u0005\u0003\u0002\u0016\u0005e\u0012\u0002BA\u001e\u0003/\u0011A!\u00168ji&\u001aQ!a\u0010\n\u0007\u0005\u0005cPA\u000eNKJ<W\rS;c\tJ\f\u0017N\\5oO\u000e{g\u000e\u001e:pY&k\u0007\u000f\\\u0001\u0007g>,(oY3\u0016\t\u0005\u001d\u00131\u000b\u000b\u0005\u0003\u0013\n\u0019\b\u0005\u0005\u0002\u000e\u0005-\u0013qJA3\u0013\r\tiE \u0002\u0007'>,(oY3\u0011\t\u0005E\u00131\u000b\u0007\u0001\t\u001d\t)f\u0002b\u0001\u0003/\u0012\u0011\u0001V\t\u0005\u00033\ny\u0006\u0005\u0003\u0002\u0016\u0005m\u0013\u0002BA/\u0003/\u0011qAT8uQ&tw\r\u0005\u0003\u0002\u0016\u0005\u0005\u0014\u0002BA2\u0003/\u00111!\u00118z!!\ti!a\u001a\u0002P\u0005-\u0014bAA5}\n!1+\u001b8l!\u0011\ti'a\u001c\u000e\u0005\u0005\u0015\u0011\u0002BA9\u0003\u000b\u0011qAT8u+N,G\rC\u0004\u0002v\u001d\u0001\r!a\n\u0002+A,'\u000f\u0015:pIV\u001cWM\u001d\"vM\u001a,'oU5{K\u0006\u00112o\\;sG\u0016<\u0016\u000e\u001e5Ee\u0006Lg.\u001b8h+\u0011\tY(!!\u0015\t\u0005u\u0014q\u0012\t\t\u0003\u001b\tY%a \u0002\u0004B!\u0011\u0011KAA\t\u001d\t)\u0006\u0003b\u0001\u0003/\u0002\u0002\"!\u0006\u0002\u0006\u0006%\u00151R\u0005\u0005\u0003\u000f\u000b9B\u0001\u0004UkBdWM\r\t\t\u0003\u001b\t9'a \u0002lA\u0019\u0011QR\u0003\u000e\u0003\u0005Aq!!\u001e\t\u0001\u0004\t9#\u0006\u0003\u0002\u0014\u0006eUCAAK!!\ti!a\u0013\u0002\u0018\u0006m\u0005\u0003BA)\u00033#q!!\u0016\n\u0005\u0004\t9\u0006\u0005\u0005\u0002\u000e\u0005\u001d\u0014qSA6+\u0011\ty*!*\u0015\u0005\u0005\u0005\u0006\u0003CA\u0007\u0003\u0017\n\u0019+a*\u0011\t\u0005E\u0013Q\u0015\u0003\b\u0003+R!\u0019AA,!!\t)\"!\"\u0002*\u0006-\u0005\u0003CA\u0007\u0003O\n\u0019+a\u001b\u0003\u001dA\u0013x\u000eZ;dKJ4\u0015-\u001b7fIN\u00191\"a,\u0011\t\u0005E\u0016\u0011\u0019\b\u0005\u0003g\u000biL\u0004\u0003\u00026\u0006mVBAA\\\u0015\u0011\tI,!\u0003\u0002\rq\u0012xn\u001c;?\u0013\t\tI\"\u0003\u0003\u0002@\u0006]\u0011a\u00029bG.\fw-Z\u0005\u0005\u0003\u0007\f)M\u0001\tSk:$\u0018.\\3Fq\u000e,\u0007\u000f^5p]*!\u0011qXA\f\u0003\ri7o\u001a\t\u0005\u0003\u0017\f\u0019N\u0004\u0003\u0002N\u0006=\u0007\u0003BA[\u0003/IA!!5\u0002\u0018\u00051\u0001K]3eK\u001aLA!!6\u0002X\n11\u000b\u001e:j]\u001eTA!!5\u0002\u0018\u0005)1-Y;tKB!\u0011\u0011WAo\u0013\u0011\ty.!2\u0003\u0013QC'o\\<bE2,GCBAr\u0003K\f9\u000fE\u0002\u0002\u000e.Aq!a2\u000f\u0001\u0004\tI\rC\u0004\u0002Z:\u0001\r!a7\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133+\u0011\tiO!\u0003\u0016\u0005\u0005=(\u0006BAy\u0003o\u0004B!!\u0006\u0002t&!\u0011Q_A\f\u0005\u001d\u0011un\u001c7fC:\\#!!?\u0011\t\u0005m(QA\u0007\u0003\u0003{TA!a@\u0003\u0002\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0005\u0005\u0007\t9\"\u0001\u0006b]:|G/\u0019;j_:LAAa\u0002\u0002~\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000f\u0005UsB1\u0001\u0002XU!!Q\u0002B\u0013'\r\u0001\"q\u0002\t\t\u0005#\u00119Ba\u0007\u0003(5\u0011!1\u0003\u0006\u0005\u0005+\t\t!A\u0003ti\u0006<W-\u0003\u0003\u0003\u001a\tM!aH$sCBD7\u000b^1hK^KG\u000f['bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKB1!Q\u0004B\u0010\u0005Gi!!!\u0001\n\t\t\u0005\u0012\u0011\u0001\u0002\f'>,(oY3TQ\u0006\u0004X\r\u0005\u0003\u0002R\t\u0015BaBA+!\t\u0007\u0011q\u000b\t\t\u0003+\t)I!\u000b\u0003,AA\u0011QBA4\u0005G\tY\u0007E\u0002\u0003.\u0015q1!!\u0004\u0001\u0003=!'/Y5oS:<WI\\1cY\u0016$GC\u0002B\u001a\u0005k\u00119\u0004E\u0003\u0002\u000eA\u0011\u0019\u0003C\u0004\u0002vM\u0001\r!a\n\t\u0013\t=2\u0003%AA\u0002\u0005E\u0018aA8viV\u0011!Q\b\t\u0007\u0005;\u0011yDa\t\n\t\t\u0005\u0013\u0011\u0001\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0006g\"\f\u0007/Z\u000b\u0003\u00057\taa\u001d5ba\u0016\u0004\u0013a\u0004#f[\u0006tG\r\u00165sKNDw\u000e\u001c3\u0003\u000b\u00153XM\u001c;\u0014\u0007e\t\u0019\"\u0001\u0002jIV\u0011!Q\u000b\t\u0005\u0003+\u00119&\u0003\u0003\u0003Z\u0005]!\u0001\u0002'p]\u001eLC!G&\u001cg\tQA)\u001a:fO&\u001cH/\u001a:\u0014\u0013-\u000b\u0019B!\u0019\u0003f\t-\u0004c\u0001B235\t\u0001\u0003\u0005\u0003\u0002\u0016\t\u001d\u0014\u0002\u0002B5\u0003/\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u00022\n5\u0014\u0002\u0002B8\u0003\u000b\u0014AbU3sS\u0006d\u0017N_1cY\u0016\f1!\u001b3!)\u0011\u0011)Ha\u001e\u0011\u0007\t\r4\nC\u0004\u0003R9\u0003\rA!\u0016\u0002\t\r|\u0007/\u001f\u000b\u0005\u0005k\u0012i\bC\u0005\u0003R=\u0003\n\u00111\u0001\u0003V\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nTC\u0001BBU\u0011\u0011)&a>\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011I\t\u0005\u0003\u0003\f\nUUB\u0001BG\u0015\u0011\u0011yI!%\u0002\t1\fgn\u001a\u0006\u0003\u0005'\u000bAA[1wC&!\u0011Q\u001bBG\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a\u0018\u0003\u001e\"I!qT*\u0002\u0002\u0003\u0007\u0011qE\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\t\u0015\u0006C\u0002BT\u0005[\u000by&\u0004\u0002\u0003**!!1VA\f\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0005_\u0013IK\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAy\u0005kC\u0011Ba(V\u0003\u0003\u0005\r!a\u0018\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0005\u0013\u0013Y\fC\u0005\u0003 Z\u000b\t\u00111\u0001\u0002(\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002(\u0005AAo\\*ue&tw\r\u0006\u0002\u0003\n\u00061Q-];bYN$B!!=\u0003J\"I!qT-\u0002\u0002\u0003\u0007\u0011q\f\u0002\b\u000b2,W.\u001a8u'%Y\u00121\u0003B1\u0005K\u0012Y'\u0001\u0003fY\u0016lWC\u0001B\u0012\u0003\u0015)G.Z7!)\u0019\u00119N!7\u0003\\B\u0019!1M\u000e\t\u000f\tE\u0003\u00051\u0001\u0003V!9!q\u001a\u0011A\u0002\t\rBC\u0002Bl\u0005?\u0014\t\u000fC\u0005\u0003R\u0005\u0002\n\u00111\u0001\u0003V!I!qZ\u0011\u0011\u0002\u0003\u0007!1E\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\u00119O\u000b\u0003\u0003$\u0005]H\u0003BA0\u0005WD\u0011Ba('\u0003\u0003\u0005\r!a\n\u0015\t\u0005E(q\u001e\u0005\n\u0005?C\u0013\u0011!a\u0001\u0003?\"BA!#\u0003t\"I!qT\u0015\u0002\u0002\u0003\u0007\u0011q\u0005\u000b\u0005\u0003c\u00149\u0010C\u0005\u0003 2\n\t\u00111\u0001\u0002`\tA!+Z4jgR,'oE\u00054\u0003'\u0011\tG!\u001a\u0003l\u0005qA-Z7b]\u0012\u001c\u0015\r\u001c7cC\u000e\\WCAB\u0001!\u0019\u0011\tba\u0001\u0003V%!1Q\u0001B\n\u00055\t5/\u001f8d\u0007\u0006dGNY1dW\u0006yA-Z7b]\u0012\u001c\u0015\r\u001c7cC\u000e\\\u0007\u0005\u0006\u0004\u0004\f\r51q\u0002\t\u0004\u0005G\u001a\u0004b\u0002B)q\u0001\u0007!Q\u000b\u0005\b\u0005{D\u0004\u0019AB\u0001)\u0019\u0019Yaa\u0005\u0004\u0016!I!\u0011K\u001d\u0011\u0002\u0003\u0007!Q\u000b\u0005\n\u0005{L\u0004\u0013!a\u0001\u0007\u0003)\"a!\u0007+\t\r\u0005\u0011q\u001f\u000b\u0005\u0003?\u001ai\u0002C\u0005\u0003 z\n\t\u00111\u0001\u0002(Q!\u0011\u0011_B\u0011\u0011%\u0011y\nQA\u0001\u0002\u0004\ty\u0006\u0006\u0003\u0003\n\u000e\u0015\u0002\"\u0003BP\u0003\u0006\u0005\t\u0019AA\u0014)\u0011\t\tp!\u000b\t\u0013\t}E)!AA\u0002\u0005}\u0013aB#mK6,g\u000e\u001e\t\u0004\u0005Gr3#\u0002\u0018\u00042\ru\u0002CCB\u001a\u0007s\u0011)Fa\t\u0003X6\u00111Q\u0007\u0006\u0005\u0007o\t9\"A\u0004sk:$\u0018.\\3\n\t\rm2Q\u0007\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u0014\u0004\u0003BB \u0007\u000bj!a!\u0011\u000b\t\r\r#\u0011S\u0001\u0003S>LAAa\u001c\u0004BQ\u00111QF\u0001\u0006CB\u0004H.\u001f\u000b\u0007\u0005/\u001ciea\u0014\t\u000f\tE\u0013\u00071\u0001\u0003V!9!qZ\u0019A\u0002\t\r\u0012aB;oCB\u0004H.\u001f\u000b\u0005\u0007+\u001ai\u0006\u0005\u0004\u0002\u0016\r]31L\u0005\u0005\u00073\n9B\u0001\u0004PaRLwN\u001c\t\t\u0003+\t)I!\u0016\u0003$!I1q\f\u001a\u0002\u0002\u0003\u0007!q[\u0001\u0004q\u0012\u0002\u0014\u0001\u0003*fO&\u001cH/\u001a:\u0011\u0007\t\rdiE\u0003G\u0007O\u001ai\u0004\u0005\u0006\u00044\re\"QKB\u0001\u0007\u0017!\"aa\u0019\u0015\r\r-1QNB8\u0011\u001d\u0011\t&\u0013a\u0001\u0005+BqA!@J\u0001\u0004\u0019\t\u0001\u0006\u0003\u0004t\r]\u0004CBA\u000b\u0007/\u001a)\b\u0005\u0005\u0002\u0016\u0005\u0015%QKB\u0001\u0011%\u0019yFSA\u0001\u0002\u0004\u0019Y!\u0001\u0006EKJ,w-[:uKJ\u00042Aa\u0019\\'\u0015Y6qPB\u001f!!\u0019\u0019d!!\u0003V\tU\u0014\u0002BBB\u0007k\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82)\t\u0019Y\b\u0006\u0003\u0003v\r%\u0005b\u0002B)=\u0002\u0007!Q\u000b\u000b\u0005\u0007\u001b\u001by\t\u0005\u0004\u0002\u0016\r]#Q\u000b\u0005\n\u0007?z\u0016\u0011!a\u0001\u0005k\u0012!\"\u00138qkR\u001cF/\u0019;f'\r\u0001\u00171C\u0001\rg&<g.\u00197EK6\fg\u000e\u001a\u000b\u0005\u00073\u001bY\nE\u0002\u0003d\u0001Dqa!&c\u0001\u0004\u0019\t!A\u000bv]RLGNT3yi\u0012+W.\u00198e'&<g.\u00197\u00023UtG/\u001b7OKb$H)Z7b]\u0012\u001c\u0016n\u001a8bY~#S-\u001d\u000b\u0005\u0003o\u0019\u0019\u000bC\u0005\u0003 \u0012\f\t\u00111\u0001\u0002(\u00051RO\u001c;jY:+\u0007\u0010\u001e#f[\u0006tGmU5h]\u0006d\u0007%A\u0005p]\u0016cW-\\3oi\u0006)1\r\\8tK\n\tR*\u001a:hK\u0012\u001cv.\u001e:dK2{w-[2\u0014\u000b!\u001cyk!.\u0011\t\tE1\u0011W\u0005\u0005\u0007g\u0013\u0019BA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d!\u0011\u0011\tba.\n\t\re&1\u0003\u0002\u000b\u001fV$\b*\u00198eY\u0016\u0014\u0018AB0tQ\u0006\u0004X\r\u0005\u0003\u0003d\r}\u0016\u0002BBa\u0007\u0007\u0014Qa\u00155ba\u0016LAa!2\u0002\u0002\t)qI]1qQR!1\u0011ZBf!\r\u0011\u0019\u0007\u001b\u0005\b\u0007wS\u0007\u0019AB_\u0003\u0015\tX/Z;f+\t\u0019\t\u000e\u0005\u0004\u0004T\u000ee'\u0011M\u0007\u0003\u0007+TAaa6\u0002\u0006\u0005AA-[:qCR\u001c\u0007.\u0003\u0003\u0004\\\u000eU'!E!cgR\u0014\u0018m\u0019;O_\u0012,\u0017+^3vK\u00061\u0011/^3vK\u0002\n!B\\3fI^\u000b7.Z;qQ\ri71\u001d\t\u0005\u0003+\u0019)/\u0003\u0003\u0004h\u0006]!\u0001\u0003<pY\u0006$\u0018\u000e\\3\u0002\u0019MDW\u000f\u001e;j]\u001e$un\u001e8)\u00079\u001c\u0019/\u0001\u0005ee\u0006Lg.\u001b8hQ\ry71]\u0001\bI\u0016l\u0017M\u001c3t!\u0019\u0019)pa?\u0004\u001a6\u00111q\u001f\u0006\u0005\u0007s\u0014I+A\u0004nkR\f'\r\\3\n\t\ru8q\u001f\u0002\b\u0019>tw-T1q\u000399\u0018m[3va\u000e\u000bG\u000e\u001c2bG.\u0004bA!\u0005\u0004\u0004\u0005-\u0014\u0001M1lW\u0006$3\u000f\u001e:fC6$3oY1mC\u0012\u001cH\u000eJ'fe\u001e,\u0007*\u001e2%I\u0011\u0014\u0018-\u001b8j]\u001e\u001c\u0015\r\u001c7cC\u000e\\\u0007\u0005\u0005\u0004\u0002\u0016\r]C\u0011A\u0001\b_:,e/\u001a8u)\u0011\t\t\u0010b\u0003\t\u000f\u001151\u000f1\u0001\u0003b\u0005\u0011QM^\u0001\u0016iJL8i\\7qY\u0016$Xm\u00148Ee\u0006Lg.\u001b8h\u0003\u0019yg\u000eU;mY\u0006qAO]=Qe>\u001cWm]:OKb$H\u0003BA\u001c\t/Aq\u0001\"\u0007w\u0001\u0004\t\t0\u0001\u0007gSJ\u001cH/\u0011;uK6\u0004H\u000fK\u0002w\t;\u0001B\u0001b\b\u0005\"5\u0011!\u0011A\u0005\u0005\tG\u0011\tAA\u0004uC&d'/Z2\u0002\u001d%\u001c8\u000b[;ui&tw\rR8x]V\u0011\u0011\u0011_\u0001\u000bSN$%/Y5oS:<\u0017\u0001\u00039pgR\u001cFo\u001c9\u0002_\u0005\\7.\u0019\u0013tiJ,\u0017-\u001c\u0013tG\u0006d\u0017\rZ:mI5+'oZ3Ik\n$C\u0005\u001a:bS:LgnZ\"bY2\u0014\u0017mY6\u0016\u0005\u0011\u0015\u0011AJ1lW\u0006$3\u000f\u001e:fC6$3oY1mC\u0012\u001cH\u000eJ'fe\u001e,\u0007*\u001e2%I\u0015t\u0017/^3vKR!\u0011q\u0007C\u001b\u0011\u001d!ia\u001fa\u0001\u0005C\nqd\u0019:fCR,Gj\\4jG\u0006sG-T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f)\u0011!Y\u0004\"\u0010\u0011\u0011\u0005U\u0011QQBX\u0005OAq\u0001b\u0010}\u0001\u0004!\t%A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000f\u0005\u0003\u0003\u001e\u0011\r\u0013\u0002\u0002C#\u0003\u0003\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0001")
public class MergeHub<T>
extends GraphStageWithMaterializedValue<SourceShape<T>, Tuple2<Sink<T, NotUsed>, DrainingControl>> {
    private volatile MergeHub$Element$ Element$module;
    private volatile MergeHub$Register$ Register$module;
    private volatile MergeHub$Deregister$ Deregister$module;
    public final int akka$stream$scaladsl$MergeHub$$perProducerBufferSize;
    public final boolean akka$stream$scaladsl$MergeHub$$drainingEnabled;
    private final Outlet<T> out;
    private final SourceShape<T> shape;
    public final int akka$stream$scaladsl$MergeHub$$DemandThreshold;

    public static <T> boolean $lessinit$greater$default$2() {
        return MergeHub$.MODULE$.$lessinit$greater$default$2();
    }

    public static <T> Source<T, Tuple2<Sink<T, NotUsed>, DrainingControl>> sourceWithDraining() {
        return MergeHub$.MODULE$.sourceWithDraining();
    }

    public static <T> Source<T, Sink<T, NotUsed>> source() {
        return MergeHub$.MODULE$.source();
    }

    public static <T> Source<T, Tuple2<Sink<T, NotUsed>, DrainingControl>> sourceWithDraining(int perProducerBufferSize) {
        return MergeHub$.MODULE$.sourceWithDraining(perProducerBufferSize);
    }

    public static <T> Source<T, Sink<T, NotUsed>> source(int perProducerBufferSize) {
        return MergeHub$.MODULE$.source(perProducerBufferSize);
    }

    private MergeHub$Element$ Element() {
        if (this.Element$module == null) {
            this.Element$lzycompute$1();
        }
        return this.Element$module;
    }

    private MergeHub$Register$ Register() {
        if (this.Register$module == null) {
            this.Register$lzycompute$1();
        }
        return this.Register$module;
    }

    private MergeHub$Deregister$ Deregister() {
        if (this.Deregister$module == null) {
            this.Deregister$lzycompute$1();
        }
        return this.Deregister$module;
    }

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, Tuple2<Sink<T, NotUsed>, DrainingControl>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        JFunction0$mcV$sp jFunction0$mcV$sp;
        Graph<SinkShape<Object>, Object> graph;
        AtomicLong idCounter = new AtomicLong();
        MergedSourceLogic logic = new MergedSourceLogic(this.shape());
        GraphStage sink2 = new GraphStage<SinkShape<T>>(this, idCounter, logic){
            private final Inlet<T> in;
            private final SinkShape<T> shape;
            private final /* synthetic */ MergeHub $outer;
            public final AtomicLong idCounter$1;
            public final MergedSourceLogic logic$1;

            public Inlet<T> in() {
                return this.in;
            }

            public SinkShape<T> shape() {
                return this.shape;
            }

            public GraphStageLogic createLogic(Attributes inheritedAttributes) {
                return new InHandler(this){
                    private long demand;
                    private final long id;
                    private final /* synthetic */ $anon$2 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void preStart() {
                        if (!this.$outer.logic$1.isDraining() && !this.$outer.logic$1.isShuttingDown()) {
                            this.$outer.logic$1.akka$stream$scaladsl$MergeHub$$enqueue(new Register(this.$outer.akka$stream$scaladsl$MergeHub$$anon$$$outer(), this.id, this.getAsyncCallback(moreDemand -> this.onDemand(moreDemand))));
                            if (!this.$outer.logic$1.isShuttingDown()) {
                                this.pullWithDemand();
                                return;
                            }
                            this.completeStage();
                            return;
                        }
                        this.completeStage();
                    }

                    public void postStop() {
                        if (!this.$outer.logic$1.isShuttingDown()) {
                            this.$outer.logic$1.akka$stream$scaladsl$MergeHub$$enqueue(new Deregister(this.$outer.akka$stream$scaladsl$MergeHub$$anon$$$outer(), this.id));
                            return;
                        }
                    }

                    public void onPush() {
                        this.$outer.logic$1.akka$stream$scaladsl$MergeHub$$enqueue(new Element(this.$outer.akka$stream$scaladsl$MergeHub$$anon$$$outer(), this.id, this.grab(this.$outer.in())));
                        if (this.demand > 0L) {
                            this.pullWithDemand();
                            return;
                        }
                    }

                    private void pullWithDemand() {
                        --this.demand;
                        this.pull(this.$outer.in());
                    }

                    public void onUpstreamFailure(Throwable ex) {
                        throw new ProducerFailed("Upstream producer failed with exception, removing from MergeHub now", ex);
                    }

                    private void onDemand(long moreDemand) {
                        if (moreDemand == (long)MergeHub$.MODULE$.akka$stream$scaladsl$MergeHub$$Cancel()) {
                            this.completeStage();
                            return;
                        }
                        this.demand += moreDemand;
                        if (!this.hasBeenPulled(this.$outer.in())) {
                            this.pullWithDemand();
                            return;
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        super($outer.shape());
                        InHandler.$init$(this);
                        this.demand = $outer.akka$stream$scaladsl$MergeHub$$anon$$$outer().akka$stream$scaladsl$MergeHub$$perProducerBufferSize;
                        this.id = $outer.idCounter$1.getAndIncrement();
                        this.setHandler($outer.in(), this);
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$1(akka.stream.scaladsl.MergeHub$$anon$2$$anon$3 long )}, serializedLambda);
                    }
                };
            }

            public /* synthetic */ MergeHub akka$stream$scaladsl$MergeHub$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.idCounter$1 = idCounter$1;
                this.logic$1 = logic$1;
                this.in = Inlet$.MODULE$.apply("MergeHub.in");
                this.shape = new SinkShape<T>(this.in());
            }
        };
        Option option = inheritedAttributes.get(ClassTag$.MODULE$.apply(Attributes.LogLevels.class));
        if (option instanceof Some) {
            Some some = (Some)option;
            Attributes.LogLevels a = (Attributes.LogLevels)some.value();
            graph = Sink$.MODULE$.fromGraph(sink2).addAttributes(Attributes$.MODULE$.apply(a));
        } else if (None$.MODULE$.equals(option)) {
            graph = Sink$.MODULE$.fromGraph(sink2);
        } else {
            throw new MatchError(option);
        }
        Sink sinkWithAttributes = graph;
        Option<AsyncCallback<NotUsed>> option2 = logic.akka$stream$scaladsl$MergeHub$$drainingCallback();
        if (option2 instanceof Some) {
            Some some = (Some)option2;
            AsyncCallback cbk = (AsyncCallback)some.value();
            jFunction0$mcV$sp = () -> cbk.invoke(NotUsed$.MODULE$);
        } else if (None$.MODULE$.equals(option2)) {
            jFunction0$mcV$sp = () -> {
                throw new IllegalStateException("Draining control not enabled");
            };
        } else {
            throw new MatchError(option2);
        }
        JFunction0$mcV$sp drainingAction = jFunction0$mcV$sp;
        MergeHubDrainingControlImpl drainingControl = new MergeHubDrainingControlImpl(drainingAction);
        return new Tuple2<GraphStageLogic, Tuple2<Sink<T, NotUsed>, DrainingControl>>(logic, new Tuple2(sinkWithAttributes, drainingControl));
    }

    private final void Element$lzycompute$1() {
        MergeHub mergeHub = this;
        synchronized (mergeHub) {
            if (this.Element$module == null) {
                this.Element$module = new MergeHub$Element$(this);
            }
        }
    }

    private final void Register$lzycompute$1() {
        MergeHub mergeHub = this;
        synchronized (mergeHub) {
            if (this.Register$module == null) {
                this.Register$module = new MergeHub$Register$(this);
            }
        }
    }

    private final void Deregister$lzycompute$1() {
        MergeHub mergeHub = this;
        synchronized (mergeHub) {
            if (this.Deregister$module == null) {
                this.Deregister$module = new MergeHub$Deregister$(this);
            }
        }
    }

    public MergeHub(int perProducerBufferSize, boolean drainingEnabled) {
        this.akka$stream$scaladsl$MergeHub$$perProducerBufferSize = perProducerBufferSize;
        this.akka$stream$scaladsl$MergeHub$$drainingEnabled = drainingEnabled;
        Predef$.MODULE$.require(perProducerBufferSize > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer size must be positive");
        this.out = Outlet$.MODULE$.apply("MergeHub.out");
        this.shape = new SourceShape<T>(this.out());
        this.akka$stream$scaladsl$MergeHub$$DemandThreshold = perProducerBufferSize / 2 + perProducerBufferSize % 2;
    }

    private class Deregister
    implements Event,
    Product,
    Serializable {
        private final long id;
        public final /* synthetic */ MergeHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public long id() {
            return this.id;
        }

        public Deregister copy(long id) {
            return new Deregister(this.akka$stream$scaladsl$MergeHub$Deregister$$$outer(), id);
        }

        public long copy$default$1() {
            return this.id();
        }

        @Override
        public String productPrefix() {
            return "Deregister";
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
                    return BoxesRunTime.boxToLong(this.id());
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
            return x$1 instanceof Deregister;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.id()));
            return Statics.finalizeHash(n, 1);
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
            if (!(object instanceof Deregister)) return false;
            if (((Deregister)object).akka$stream$scaladsl$MergeHub$Deregister$$$outer() != this.akka$stream$scaladsl$MergeHub$Deregister$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Deregister deregister = (Deregister)x$1;
            if (this.id() != deregister.id()) return false;
            if (!deregister.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ MergeHub akka$stream$scaladsl$MergeHub$Deregister$$$outer() {
            return this.$outer;
        }

        public Deregister(MergeHub $outer, long id) {
            this.id = id;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    public static interface DrainingControl {
        public void drainAndComplete();
    }

    private class Element
    implements Event,
    Product,
    Serializable {
        private final long id;
        private final T elem;
        public final /* synthetic */ MergeHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public long id() {
            return this.id;
        }

        public T elem() {
            return this.elem;
        }

        public Element copy(long id, T elem) {
            return new Element(this.akka$stream$scaladsl$MergeHub$Element$$$outer(), id, elem);
        }

        public long copy$default$1() {
            return this.id();
        }

        public T copy$default$2() {
            return this.elem();
        }

        @Override
        public String productPrefix() {
            return "Element";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToLong(this.id());
                }
                case 1: {
                    return this.elem();
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
            return x$1 instanceof Element;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "elem";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.id()));
            n = Statics.mix(n, Statics.anyHash(this.elem()));
            return Statics.finalizeHash(n, 2);
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
            if (!(object instanceof Element)) return false;
            if (((Element)object).akka$stream$scaladsl$MergeHub$Element$$$outer() != this.akka$stream$scaladsl$MergeHub$Element$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Element element = (Element)x$1;
            if (this.id() != element.id()) return false;
            if (!BoxesRunTime.equals(this.elem(), element.elem())) return false;
            if (!element.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ MergeHub akka$stream$scaladsl$MergeHub$Element$$$outer() {
            return this.$outer;
        }

        public Element(MergeHub $outer, long id, T elem) {
            this.id = id;
            this.elem = elem;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }

    private interface Event {
        public long id();
    }

    public final class InputState {
        private final AsyncCallback<Object> signalDemand;
        private int untilNextDemandSignal;
        private final /* synthetic */ MergeHub $outer;

        private int untilNextDemandSignal() {
            return this.untilNextDemandSignal;
        }

        private void untilNextDemandSignal_$eq(int x$1) {
            this.untilNextDemandSignal = x$1;
        }

        public void onElement() {
            this.untilNextDemandSignal_$eq(this.untilNextDemandSignal() - 1);
            if (this.untilNextDemandSignal() == 0) {
                this.untilNextDemandSignal_$eq(this.$outer.akka$stream$scaladsl$MergeHub$$DemandThreshold);
                this.signalDemand.invoke(BoxesRunTime.boxToLong(this.$outer.akka$stream$scaladsl$MergeHub$$DemandThreshold));
                return;
            }
        }

        public void close() {
            this.signalDemand.invoke(BoxesRunTime.boxToLong(MergeHub$.MODULE$.akka$stream$scaladsl$MergeHub$$Cancel()));
        }

        public InputState(MergeHub $outer, AsyncCallback<Object> signalDemand) {
            this.signalDemand = signalDemand;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            this.untilNextDemandSignal = $outer.akka$stream$scaladsl$MergeHub$$DemandThreshold;
        }
    }

    public final class MergedSourceLogic
    extends GraphStageLogic
    implements OutHandler {
        private final AbstractNodeQueue<Event> queue;
        private volatile boolean needWakeup;
        private volatile boolean shuttingDown;
        private volatile boolean draining;
        private final LongMap<InputState> demands;
        private final AsyncCallback<NotUsed> wakeupCallback;
        private final Option<AsyncCallback<NotUsed>> akka$stream$scaladsl$MergeHub$$drainingCallback;

        @Override
        public void onDownstreamFinish() throws Exception {
            OutHandler.onDownstreamFinish$(this);
        }

        @Override
        public void onDownstreamFinish(Throwable cause) throws Exception {
            OutHandler.onDownstreamFinish$(this, cause);
        }

        private AbstractNodeQueue<Event> queue() {
            return this.queue;
        }

        public Option<AsyncCallback<NotUsed>> akka$stream$scaladsl$MergeHub$$drainingCallback() {
            return this.akka$stream$scaladsl$MergeHub$$drainingCallback;
        }

        private boolean onEvent(Event ev) {
            Event event = ev;
            if (event instanceof Element) {
                Element element = (Element)event;
                long id = element.id();
                Object elem = element.elem();
                this.demands.apply(id).onElement();
                this.push(MergeHub.this.out(), elem);
                return false;
            }
            if (event instanceof Register) {
                Register register = (Register)event;
                long id = register.id();
                AsyncCallback<Object> callback = register.demandCallback();
                this.demands.put(id, new InputState(MergeHub.this, callback));
                return true;
            }
            if (event instanceof Deregister) {
                Deregister deregister = (Deregister)event;
                long id = deregister.id();
                this.demands.remove(BoxesRunTime.boxToLong(id));
                if (MergeHub.this.akka$stream$scaladsl$MergeHub$$drainingEnabled && this.draining) {
                    this.tryCompleteOnDraining();
                }
                return true;
            }
            throw new MatchError(event);
        }

        private void tryCompleteOnDraining() {
            if (this.demands.isEmpty() && this.queue().peek() == null) {
                this.completeStage();
                return;
            }
        }

        @Override
        public void onPull() {
            this.tryProcessNext(true);
        }

        private void tryProcessNext(boolean firstAttempt) {
            block5: {
                BoxedUnit boxedUnit;
                while (true) {
                    Event nextElem;
                    if ((nextElem = this.queue().poll()) != null) {
                        this.needWakeup = false;
                        if (this.onEvent(nextElem)) {
                            firstAttempt = true;
                            continue;
                        }
                        boxedUnit = BoxedUnit.UNIT;
                        break block5;
                    }
                    this.needWakeup = true;
                    if (!firstAttempt) break;
                    firstAttempt = false;
                }
                if (MergeHub.this.akka$stream$scaladsl$MergeHub$$drainingEnabled && this.draining) {
                    this.tryCompleteOnDraining();
                    boxedUnit = BoxedUnit.UNIT;
                } else {
                    boxedUnit = BoxedUnit.UNIT;
                }
            }
        }

        public boolean isShuttingDown() {
            return this.shuttingDown;
        }

        public boolean isDraining() {
            return MergeHub.this.akka$stream$scaladsl$MergeHub$$drainingEnabled && this.draining;
        }

        public void akka$stream$scaladsl$MergeHub$$enqueue(Event ev) {
            this.queue().add(ev);
            if (this.needWakeup) {
                this.needWakeup = false;
                this.wakeupCallback.invoke(NotUsed$.MODULE$);
                return;
            }
        }

        @Override
        public void postStop() {
            this.shuttingDown = true;
            Event event = this.queue().poll();
            while (event != null) {
                Event event2 = event;
                if (event2 instanceof Register) {
                    Register register = (Register)event2;
                    AsyncCallback<Object> demandCallback = register.demandCallback();
                    demandCallback.invoke(BoxesRunTime.boxToLong(MergeHub$.MODULE$.akka$stream$scaladsl$MergeHub$$Cancel()));
                }
                event = this.queue().poll();
            }
            Iterator<InputState> states = this.demands.valuesIterator();
            while (states.hasNext()) {
                states.next().close();
            }
        }

        public static final /* synthetic */ void $anonfun$wakeupCallback$1(MergedSourceLogic $this, NotUsed x$2) {
            if ($this.isAvailable($this.MergeHub.this.out())) {
                $this.tryProcessNext(true);
                return;
            }
        }

        public static final /* synthetic */ void $anonfun$akka$stream$scaladsl$MergeHub$$drainingCallback$1(MergedSourceLogic $this, NotUsed x$3) {
            $this.draining = true;
            $this.tryCompleteOnDraining();
        }

        public MergedSourceLogic(SourceShape<T> _shape) {
            if (MergeHub.this == null) {
                throw null;
            }
            super(_shape);
            OutHandler.$init$(this);
            this.queue = new AbstractNodeQueue<Event>(null){};
            this.needWakeup = false;
            this.shuttingDown = false;
            this.draining = false;
            this.demands = LongMap$.MODULE$.empty();
            this.wakeupCallback = this.getAsyncCallback((Function1<NotUsed, Object> & Serializable)x$2 -> {
                MergedSourceLogic.$anonfun$wakeupCallback$1(this, x$2);
                return BoxedUnit.UNIT;
            });
            this.akka$stream$scaladsl$MergeHub$$drainingCallback = MergeHub.this.akka$stream$scaladsl$MergeHub$$drainingEnabled ? new Some(this.getAsyncCallback((Function1<NotUsed, Object> & Serializable)x$3 -> {
                MergedSourceLogic.$anonfun$akka$stream$scaladsl$MergeHub$$drainingCallback$1(this, x$3);
                return BoxedUnit.UNIT;
            })) : None$.MODULE$;
            this.setHandler(MergeHub.this.out(), this);
        }
    }

    public static final class ProducerFailed
    extends RuntimeException {
        public ProducerFailed(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    private class Register
    implements Event,
    Product,
    Serializable {
        private final long id;
        private final AsyncCallback<Object> demandCallback;
        public final /* synthetic */ MergeHub $outer;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public long id() {
            return this.id;
        }

        public AsyncCallback<Object> demandCallback() {
            return this.demandCallback;
        }

        public Register copy(long id, AsyncCallback<Object> demandCallback) {
            return new Register(this.akka$stream$scaladsl$MergeHub$Register$$$outer(), id, demandCallback);
        }

        public long copy$default$1() {
            return this.id();
        }

        public AsyncCallback<Object> copy$default$2() {
            return this.demandCallback();
        }

        @Override
        public String productPrefix() {
            return "Register";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToLong(this.id());
                }
                case 1: {
                    return this.demandCallback();
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
            return x$1 instanceof Register;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "id";
                }
                case 1: {
                    return "demandCallback";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.id()));
            n = Statics.mix(n, Statics.anyHash(this.demandCallback()));
            return Statics.finalizeHash(n, 2);
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
            if (!(object instanceof Register)) return false;
            if (((Register)object).akka$stream$scaladsl$MergeHub$Register$$$outer() != this.akka$stream$scaladsl$MergeHub$Register$$$outer()) return false;
            boolean bl = true;
            if (!bl) return false;
            Register register = (Register)x$1;
            if (this.id() != register.id()) return false;
            AsyncCallback<Object> asyncCallback = this.demandCallback();
            AsyncCallback<Object> asyncCallback2 = register.demandCallback();
            if (asyncCallback == null) {
                if (asyncCallback2 != null) {
                    return false;
                }
            } else if (!asyncCallback.equals(asyncCallback2)) return false;
            if (!register.canEqual(this)) return false;
            return true;
        }

        public /* synthetic */ MergeHub akka$stream$scaladsl$MergeHub$Register$$$outer() {
            return this.$outer;
        }

        public Register(MergeHub $outer, long id, AsyncCallback<Object> demandCallback) {
            this.id = id;
            this.demandCallback = demandCallback;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Product.$init$(this);
        }
    }
}

