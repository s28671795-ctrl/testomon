/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.SubscriptionWithCancelException$NoMoreElementsNeeded$;
import akka.stream.impl.ActorSubscriberMessage;
import akka.stream.impl.ActorSubscriberMessage$OnComplete$;
import akka.stream.impl.fusing.SubSink$;
import akka.stream.impl.fusing.SubSink$RequestOne$;
import akka.stream.impl.fusing.SubSink$RequestOneScheduledBeforeMaterialization$;
import akka.stream.impl.fusing.SubSink$Uninitialized$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function1;
import scala.MatchError;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r\u001dsAB4i\u0011\u0003a\u0007O\u0002\u0004sQ\"\u0005An\u001d\u0005\u0006u\u0006!\t\u0001 \u0004\b{\u0006\u0001\n1%\t\u007f\u000f\u001d\u0011)#\u0001EA\u000571qA!\u0006\u0002\u0011\u0003\u00139\u0002\u0003\u0004{\u000b\u0011\u0005!\u0011\u0004\u0005\n\u0003O*\u0011\u0011!C!\u0003SB\u0011\"a\u001f\u0006\u0003\u0003%\t!! \t\u0013\u0005\u0015U!!A\u0005\u0002\tu\u0001\"CAJ\u000b\u0005\u0005I\u0011IAK\u0011%\t\u0019+BA\u0001\n\u0003\u0011\t\u0003C\u0005\u00026\u0016\t\t\u0011\"\u0011\u00028\"I\u0011\u0011X\u0003\u0002\u0002\u0013\u0005\u00131\u0018\u0005\n\u0003',\u0011\u0011!C\u0005\u0003+4q!!\u0001\u0002\u0003C\t\u0019\u0001\u0003\u0006\u0002\n=\u0011)\u0019!C\u0001\u0003\u0017A!\"!8\u0010\u0005\u0003\u0005\u000b\u0011BA\u0007\u0011\u0019Qx\u0002\"\u0001\u0002`\u001e9!qE\u0001\t\u0002\n-aa\u0002B\u0003\u0003!\u0005%q\u0001\u0005\u0007uR!\tA!\u0003\t\u0013\u0005\u001dD#!A\u0005B\u0005%\u0004\"CA>)\u0005\u0005I\u0011AA?\u0011%\t)\tFA\u0001\n\u0003\u0011i\u0001C\u0005\u0002\u0014R\t\t\u0011\"\u0011\u0002\u0016\"I\u00111\u0015\u000b\u0002\u0002\u0013\u0005!\u0011\u0003\u0005\n\u0003k#\u0012\u0011!C!\u0003oC\u0011\"!/\u0015\u0003\u0003%\t%a/\t\u0013\u0005MG#!A\u0005\n\u0005UgABAt\u0003\u0001\u000bI\u000f\u0003\u0006\u00028y\u0011)\u001a!C\u0001\u0003sA!\"!\u0011\u001f\u0005#\u0005\u000b\u0011BA\u001e\u0011\u0019Qh\u0004\"\u0001\u0002l\"I\u0011\u0011\n\u0010\u0002\u0002\u0013\u0005\u0011\u0011\u001f\u0005\n\u0003\u001fr\u0012\u0013!C\u0001\u0003#B\u0011\"a\u001a\u001f\u0003\u0003%\t%!\u001b\t\u0013\u0005md$!A\u0005\u0002\u0005u\u0004\"CAC=\u0005\u0005I\u0011AA{\u0011%\t\u0019JHA\u0001\n\u0003\n)\nC\u0005\u0002$z\t\t\u0011\"\u0001\u0002z\"I\u0011q\u0016\u0010\u0002\u0002\u0013\u0005\u0013Q \u0005\n\u0003ks\u0012\u0011!C!\u0003oC\u0011\"!/\u001f\u0003\u0003%\t%a/\t\u0013\u0005uf$!A\u0005B\t\u0005q!\u0003B\u0015\u0003\u0005\u0005\t\u0012\u0001B\u0016\r%\t9/AA\u0001\u0012\u0003\u0011i\u0003\u0003\u0004{]\u0011\u0005!Q\t\u0005\n\u0003ss\u0013\u0011!C#\u0003wC\u0011Ba\u0012/\u0003\u0003%\tI!\u0013\t\u0013\t5c&!A\u0005\u0002\n=\u0003\"CAj]\u0005\u0005I\u0011BAk\r%\ty!\u0001I\u0001$C\t\tbB\u0004\u0003\\\u0005A\t)!3\u0007\u000f\u0005\r\u0017\u0001#!\u0002F\"1!P\u000eC\u0001\u0003\u000fD\u0011\"a\u001a7\u0003\u0003%\t%!\u001b\t\u0013\u0005md'!A\u0005\u0002\u0005u\u0004\"CACm\u0005\u0005I\u0011AAf\u0011%\t\u0019JNA\u0001\n\u0003\n)\nC\u0005\u0002$Z\n\t\u0011\"\u0001\u0002P\"I\u0011Q\u0017\u001c\u0002\u0002\u0013\u0005\u0013q\u0017\u0005\n\u0003s3\u0014\u0011!C!\u0003wC\u0011\"a57\u0003\u0003%I!!6\u0007\r\u0005U\u0011\u0001QA\f\u0011)\t9\u0004\u0011BK\u0002\u0013\u0005\u0011\u0011\b\u0005\u000b\u0003\u0003\u0002%\u0011#Q\u0001\n\u0005m\u0002B\u0002>A\t\u0003\t\u0019\u0005C\u0005\u0002J\u0001\u000b\t\u0011\"\u0001\u0002L!I\u0011q\n!\u0012\u0002\u0013\u0005\u0011\u0011\u000b\u0005\n\u0003O\u0002\u0015\u0011!C!\u0003SB\u0011\"a\u001fA\u0003\u0003%\t!! \t\u0013\u0005\u0015\u0005)!A\u0005\u0002\u0005\u001d\u0005\"CAJ\u0001\u0006\u0005I\u0011IAK\u0011%\t\u0019\u000bQA\u0001\n\u0003\t)\u000bC\u0005\u00020\u0002\u000b\t\u0011\"\u0011\u00022\"I\u0011Q\u0017!\u0002\u0002\u0013\u0005\u0013q\u0017\u0005\n\u0003s\u0003\u0015\u0011!C!\u0003wC\u0011\"!0A\u0003\u0003%\t%a0\b\u0013\tu\u0013!!A\t\u0002\t}c!CA\u000b\u0003\u0005\u0005\t\u0012\u0001B1\u0011\u0019Q\b\u000b\"\u0001\u0003f!I\u0011\u0011\u0018)\u0002\u0002\u0013\u0015\u00131\u0018\u0005\n\u0005\u000f\u0002\u0016\u0011!CA\u0005OB\u0011B!\u0014Q\u0003\u0003%\tIa\u001b\t\u0013\u0005M\u0007+!A\u0005\n\u0005UgA\u0002:i\u00051\u0014i\b\u0003\u0006\u0003&Z\u0013\t\u0011)A\u0005\u0005OC!B!.W\u0005\u0003\u0005\u000b\u0011\u0002B\\\u0011\u0019Qh\u000b\"\u0001\u0003L\"I!1\u001b,C\u0002\u0013%!Q\u001b\u0005\t\u0005;4\u0006\u0015!\u0003\u0003X\"9!q\u001c,\u0005B\t\u0005\b\"\u0003Bu-\n\u0007I\u0011\tBv\u0011!\u0011iO\u0016Q\u0001\n\t5\u0005\"\u0003Bx-\n\u0007I\u0011\u0002By\u0011!\u00199A\u0016Q\u0001\n\tM\bbBB\u0005-\u0012\u000511\u0002\u0005\b\u0007\u001b1F\u0011AB\u0006\u0011\u001d\u0019iA\u0016C\u0001\u0007\u001fAqaa\u0005W\t\u0013\u0019)\u0002C\u0004\u0004*Y#\tea\u000b\t\u000f\u0005ef\u000b\"\u0011\u0004D\u000591+\u001e2TS:\\'BA5k\u0003\u00191Wo]5oO*\u00111\u000e\\\u0001\u0005S6\u0004HN\u0003\u0002n]\u000611\u000f\u001e:fC6T\u0011a\\\u0001\u0005C.\\\u0017\r\u0005\u0002r\u00035\t\u0001NA\u0004Tk\n\u001c\u0016N\\6\u0014\u0005\u0005!\bCA;y\u001b\u00051(\"A<\u0002\u000bM\u001c\u0017\r\\1\n\u0005e4(AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005\u0001(!B*uCR,7CA\u0002uS\r\u0019q\"\u0002\u0002&\u0007>lW.\u00198e'\u000eDW\rZ;mK\u0012\u0014UMZ8sK6\u000bG/\u001a:jC2L'0\u0019;j_:\u001cBa\u0004;\u0002\u0006A\u0019\u0011qA\u0002\u000e\u0003\u0005\tqaY8n[\u0006tG-\u0006\u0002\u0002\u000eA\u0019\u0011q\u0001\u001b\u0003\u000f\r{W.\\1oIN\u0011A\u0007^\u0015\u0004i\u00013$AB\"b]\u000e,Gn\u0005\u0005Ai\u00065\u0011\u0011DA\u0010!\r)\u00181D\u0005\u0004\u0003;1(a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003C\t\tD\u0004\u0003\u0002$\u00055b\u0002BA\u0013\u0003Wi!!a\n\u000b\u0007\u0005%20\u0001\u0004=e>|GOP\u0005\u0002o&\u0019\u0011q\u0006<\u0002\u000fA\f7m[1hK&!\u00111GA\u001b\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\tyC^\u0001\u0006G\u0006,8/Z\u000b\u0003\u0003w\u0001B!!\t\u0002>%!\u0011qHA\u001b\u0005%!\u0006N]8xC\ndW-\u0001\u0004dCV\u001cX\r\t\u000b\u0005\u0003\u000b\n9\u0005E\u0002\u0002\b\u0001Cq!a\u000eD\u0001\u0004\tY$\u0001\u0003d_BLH\u0003BA#\u0003\u001bB\u0011\"a\u000eE!\u0003\u0005\r!a\u000f\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u00111\u000b\u0016\u0005\u0003w\t)f\u000b\u0002\u0002XA!\u0011\u0011LA2\u001b\t\tYF\u0003\u0003\u0002^\u0005}\u0013!C;oG\",7m[3e\u0015\r\t\tG^\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA3\u00037\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u000e\t\u0005\u0003[\n9(\u0004\u0002\u0002p)!\u0011\u0011OA:\u0003\u0011a\u0017M\\4\u000b\u0005\u0005U\u0014\u0001\u00026bm\u0006LA!!\u001f\u0002p\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a \u0011\u0007U\f\t)C\u0002\u0002\u0004Z\u00141!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!!#\u0002\u0010B\u0019Q/a#\n\u0007\u00055eOA\u0002B]fD\u0011\"!%I\u0003\u0003\u0005\r!a \u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t9\n\u0005\u0004\u0002\u001a\u0006}\u0015\u0011R\u0007\u0003\u00037S1!!(w\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003C\u000bYJ\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAT\u0003[\u00032!^AU\u0013\r\tYK\u001e\u0002\b\u0005>|G.Z1o\u0011%\t\tJSA\u0001\u0002\u0004\tI)\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA6\u0003gC\u0011\"!%L\u0003\u0003\u0005\r!a \u0002\u0011!\f7\u000f[\"pI\u0016$\"!a \u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u001b\u0002\r\u0015\fX/\u00197t)\u0011\t9+!1\t\u0013\u0005Ee*!AA\u0002\u0005%%A\u0003*fcV,7\u000f^(oKNAa\u0007^A\u0007\u00033\ty\u0002\u0006\u0002\u0002JB\u0019\u0011q\u0001\u001c\u0015\t\u0005%\u0015Q\u001a\u0005\n\u0003#S\u0014\u0011!a\u0001\u0003\u007f\"B!a*\u0002R\"I\u0011\u0011\u0013\u001f\u0002\u0002\u0003\u0007\u0011\u0011R\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003/\u0004B!!\u001c\u0002Z&!\u00111\\A8\u0005\u0019y%M[3di\u0006A1m\\7nC:$\u0007\u0005\u0006\u0003\u0002b\u0006\r\bcAA\u0004\u001f!9\u0011\u0011\u0002\nA\u0002\u00055\u0011fA\b\u001f)\t!3)\u00198dK2\u001c6\r[3ek2,GMQ3g_J,W*\u0019;fe&\fG.\u001b>bi&|gnE\u0004\u001f\u0003C\fI\"a\b\u0015\t\u00055\u0018q\u001e\t\u0004\u0003\u000fq\u0002bBA\u001cC\u0001\u0007\u00111\b\u000b\u0005\u0003[\f\u0019\u0010C\u0005\u00028\t\u0002\n\u00111\u0001\u0002<Q!\u0011\u0011RA|\u0011%\t\tJJA\u0001\u0002\u0004\ty\b\u0006\u0003\u0002(\u0006m\b\"CAIQ\u0005\u0005\t\u0019AAE)\u0011\tY'a@\t\u0013\u0005E\u0015&!AA\u0002\u0005}D\u0003BAT\u0005\u0007A\u0011\"!%-\u0003\u0003\u0005\r!!#\u0003QI+\u0017/^3ti>sWmU2iK\u0012,H.\u001a3CK\u001a|'/Z'bi\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8\u0014\u000fQ\t\t/!\u0007\u0002 Q\u0011!1\u0002\t\u0004\u0003\u000f!B\u0003BAE\u0005\u001fA\u0011\"!%\u0019\u0003\u0003\u0005\r!a \u0015\t\u0005\u001d&1\u0003\u0005\n\u0003#S\u0012\u0011!a\u0001\u0003\u0013\u0013Q\"\u00168j]&$\u0018.\u00197ju\u0016$7\u0003C\u0003u\u0003\u000b\tI\"a\b\u0015\u0005\tm\u0001cAA\u0004\u000bQ!\u0011\u0011\u0012B\u0010\u0011%\t\t*CA\u0001\u0002\u0004\ty\b\u0006\u0003\u0002(\n\r\u0002\"CAI\u0017\u0005\u0005\t\u0019AAE\u00035)f.\u001b8ji&\fG.\u001b>fI\u0006A#+Z9vKN$xJ\\3TG\",G-\u001e7fI\n+gm\u001c:f\u001b\u0006$XM]5bY&T\u0018\r^5p]\u0006!3)\u00198dK2\u001c6\r[3ek2,GMQ3g_J,W*\u0019;fe&\fG.\u001b>bi&|g\u000eE\u0002\u0002\b9\u001aRA\fB\u0018\u0005w\u0001\u0002B!\r\u00038\u0005m\u0012Q^\u0007\u0003\u0005gQ1A!\u000ew\u0003\u001d\u0011XO\u001c;j[\u0016LAA!\u000f\u00034\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\tu\"1I\u0007\u0003\u0005\u007fQAA!\u0011\u0002t\u0005\u0011\u0011n\\\u0005\u0005\u0003g\u0011y\u0004\u0006\u0002\u0003,\u0005)\u0011\r\u001d9msR!\u0011Q\u001eB&\u0011\u001d\t9$\ra\u0001\u0003w\tq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003R\t]\u0003#B;\u0003T\u0005m\u0012b\u0001B+m\n1q\n\u001d;j_:D\u0011B!\u00173\u0003\u0003\u0005\r!!<\u0002\u0007a$\u0003'\u0001\u0006SKF,Xm\u001d;P]\u0016\faaQ1oG\u0016d\u0007cAA\u0004!N)\u0001Ka\u0019\u0003<AA!\u0011\u0007B\u001c\u0003w\t)\u0005\u0006\u0002\u0003`Q!\u0011Q\tB5\u0011\u001d\t9d\u0015a\u0001\u0003w!BA!\u0015\u0003n!I!\u0011\f+\u0002\u0002\u0003\u0007\u0011Q\t\u0015\u0004\u0003\tE\u0004\u0003\u0002B:\u0005oj!A!\u001e\u000b\u0007\u0005\u0005d.\u0003\u0003\u0003z\tU$aC%oi\u0016\u0014h.\u00197Ba&D3\u0001\u0001B9+\u0011\u0011yH!'\u0014\u0007Y\u0013\t\t\u0005\u0004\u0003\u0004\n%%QR\u0007\u0003\u0005\u000bS1Aa\"m\u0003\u0015\u0019H/Y4f\u0013\u0011\u0011YI!\"\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\r\u0005\u0004\u0003\u0010\nE%QS\u0007\u0002Y&\u0019!1\u00137\u0003\u0013MKgn[*iCB,\u0007\u0003\u0002BL\u00053c\u0001\u0001B\u0004\u0003\u001cZ\u0013\rA!(\u0003\u0003Q\u000bBAa(\u0002\nB\u0019QO!)\n\u0007\t\rfOA\u0004O_RD\u0017N\\4\u0002\t9\fW.\u001a\t\u0005\u0005S\u0013\tL\u0004\u0003\u0003,\n5\u0006cAA\u0013m&\u0019!q\u0016<\u0002\rA\u0013X\rZ3g\u0013\u0011\tIHa-\u000b\u0007\t=f/\u0001\tfqR,'O\\1m\u0007\u0006dGNY1dWB9QO!/\u0003>\n\u0015\u0017b\u0001B^m\nIa)\u001e8di&|g.\r\t\u0005\u0005\u007f\u0013\t-D\u0001k\u0013\r\u0011\u0019M\u001b\u0002\u0017\u0003\u000e$xN]*vEN\u001c'/\u001b2fe6+7o]1hKB\u0019QOa2\n\u0007\t%gO\u0001\u0003V]&$HC\u0002Bg\u0005\u001f\u0014\t\u000e\u0005\u0003r-\nU\u0005b\u0002BS3\u0002\u0007!q\u0015\u0005\b\u0005kK\u0006\u0019\u0001B\\\u0003\tIg.\u0006\u0002\u0003XB1!q\u0012Bm\u0005+K1Aa7m\u0005\u0015Ie\u000e\\3u\u0003\rIg\u000eI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cXC\u0001Br!\u0011\u0011yI!:\n\u0007\t\u001dHN\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u001d5ba\u0016,\"A!$\u0002\rMD\u0017\r]3!\u0003\u0019\u0019H/\u0019;vgV\u0011!1\u001f\t\u0006\u0005k\u001c\u0019\u0001^\u0007\u0003\u0005oTAA!?\u0003|\u00061\u0011\r^8nS\u000eTAA!@\u0003\u0000\u0006Q1m\u001c8dkJ\u0014XM\u001c;\u000b\t\r\u0005\u00111O\u0001\u0005kRLG.\u0003\u0003\u0004\u0006\t](aD!u_6L7MU3gKJ,gnY3\u0002\u000fM$\u0018\r^;tA\u0005i\u0001/\u001e7m'V\u00147\u000f\u001e:fC6$\"A!2\u0002\u001f\r\fgnY3m'V\u00147\u000f\u001e:fC6$BA!2\u0004\u0012!9\u0011qG2A\u0002\u0005m\u0012a\u00043jgB\fGo\u00195D_6l\u0017M\u001c3\u0015\t\t\u00157q\u0003\u0005\b\u00073!\u0007\u0019AB\u000e\u0003!qWm^*uCR,\u0007cAB\u000f\u001f9\u0011\u0011\u000f\u0001\u0015\u0004I\u000e\u0005\u0002\u0003BB\u0012\u0007Ki!!a\u0018\n\t\r\u001d\u0012q\f\u0002\bi\u0006LGN]3d\u0003-\u0019'/Z1uK2{w-[2\u0015\t\r52q\b\n\u0007\u0007_\u0019\u0019d!\u000f\u0007\r\rER\rAB\u0017\u00051a$/\u001a4j]\u0016lWM\u001c;?!\u0011\u0011\u0019i!\u000e\n\t\r]\"Q\u0011\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jGB!!1QB\u001e\u0013\u0011\u0019iD!\"\u0003\u0013%s\u0007*\u00198eY\u0016\u0014\bbBB!K\u0002\u0007!1]\u0001\u0005CR$(\u000f\u0006\u0002\u0003(\"\u001aaK!\u001d")
@InternalApi
public final class SubSink<T>
extends GraphStage<SinkShape<T>> {
    public final String akka$stream$impl$fusing$SubSink$$name;
    public final Function1<ActorSubscriberMessage, BoxedUnit> akka$stream$impl$fusing$SubSink$$externalCallback;
    private final Inlet<T> akka$stream$impl$fusing$SubSink$$in;
    private final SinkShape<T> shape;
    private final AtomicReference<Object> akka$stream$impl$fusing$SubSink$$status;

    public Inlet<T> akka$stream$impl$fusing$SubSink$$in() {
        return this.akka$stream$impl$fusing$SubSink$$in;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name(new StringBuilder(9).append("SubSink(").append(this.akka$stream$impl$fusing$SubSink$$name).append(")").toString());
    }

    @Override
    public SinkShape<T> shape() {
        return this.shape;
    }

    public AtomicReference<Object> akka$stream$impl$fusing$SubSink$$status() {
        return this.akka$stream$impl$fusing$SubSink$$status;
    }

    public void pullSubstream() {
        this.dispatchCommand(SubSink$RequestOneScheduledBeforeMaterialization$.MODULE$);
    }

    public void cancelSubstream() {
        this.cancelSubstream(SubscriptionWithCancelException$NoMoreElementsNeeded$.MODULE$);
    }

    public void cancelSubstream(Throwable cause) {
        this.dispatchCommand(new CancelScheduledBeforeMaterialization(cause));
    }

    private void dispatchCommand(CommandScheduledBeforeMaterialization newState) {
        block6: {
            Object object;
            block7: {
                while (true) {
                    if ((object = this.akka$stream$impl$fusing$SubSink$$status().get()) instanceof AsyncCallback) {
                        AsyncCallback asyncCallback = (AsyncCallback)object;
                        asyncCallback.invoke(newState.command());
                        break block6;
                    }
                    if (SubSink$Uninitialized$.MODULE$.equals(object)) {
                        if (!this.akka$stream$impl$fusing$SubSink$$status().compareAndSet(SubSink$Uninitialized$.MODULE$, newState)) continue;
                        break block6;
                    }
                    if (!SubSink$RequestOneScheduledBeforeMaterialization$.MODULE$.equals(object) || !(newState instanceof CancelScheduledBeforeMaterialization)) break block7;
                    if (this.akka$stream$impl$fusing$SubSink$$status().compareAndSet(SubSink$RequestOneScheduledBeforeMaterialization$.MODULE$, newState)) break;
                    newState = SubSink$RequestOneScheduledBeforeMaterialization$.MODULE$;
                }
                break block6;
            }
            if (object instanceof CancelScheduledBeforeMaterialization && newState instanceof CancelScheduledBeforeMaterialization) {
            } else {
                if (object instanceof CommandScheduledBeforeMaterialization) {
                    CommandScheduledBeforeMaterialization commandScheduledBeforeMaterialization = (CommandScheduledBeforeMaterialization)object;
                    throw new IllegalStateException(new StringBuilder(47).append(newState.command()).append(" on subsink(").append(this.akka$stream$impl$fusing$SubSink$$name).append(") is illegal when ").append(commandScheduledBeforeMaterialization.command()).append(" is still pending").toString());
                }
                throw new RuntimeException();
            }
        }
    }

    @Override
    public GraphStageLogic createLogic(Attributes attr) {
        return new InHandler(this){
            private final IllegalStateException materializationException;
            private final /* synthetic */ SubSink $outer;

            public void onPush() {
                this.$outer.akka$stream$impl$fusing$SubSink$$externalCallback.apply(new ActorSubscriberMessage.OnNext(this.grab(this.$outer.akka$stream$impl$fusing$SubSink$$in())));
            }

            public void onUpstreamFinish() {
                this.$outer.akka$stream$impl$fusing$SubSink$$externalCallback.apply(ActorSubscriberMessage$OnComplete$.MODULE$);
            }

            public void onUpstreamFailure(Throwable ex) {
                this.$outer.akka$stream$impl$fusing$SubSink$$externalCallback.apply(new ActorSubscriberMessage.OnError(ex));
            }

            private void setCallback(Function1<Command, BoxedUnit> callback) {
                block5: {
                    Object object;
                    while (true) {
                        if (SubSink$Uninitialized$.MODULE$.equals(object = this.$outer.akka$stream$impl$fusing$SubSink$$status().get())) {
                            if (!this.$outer.akka$stream$impl$fusing$SubSink$$status().compareAndSet(SubSink$Uninitialized$.MODULE$, this.getAsyncCallback(callback))) continue;
                        } else {
                            if (!(object instanceof CommandScheduledBeforeMaterialization)) break;
                            CommandScheduledBeforeMaterialization commandScheduledBeforeMaterialization = (CommandScheduledBeforeMaterialization)object;
                            if (!this.$outer.akka$stream$impl$fusing$SubSink$$status().compareAndSet(commandScheduledBeforeMaterialization, this.getAsyncCallback(callback))) continue;
                            callback.apply(commandScheduledBeforeMaterialization.command());
                        }
                        break block5;
                    }
                    if (object instanceof AsyncCallback) {
                        this.failStage(OptionVal$.MODULE$.getOrElse$extension(this.materializationException, this.createMaterializedTwiceException()));
                    } else {
                        throw new RuntimeException();
                    }
                }
            }

            public void preStart() {
                this.setCallback((Function1<Command, BoxedUnit>)(Function1<Command, Object> & Serializable)x0$1 -> {
                    $anon$10.$anonfun$preStart$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
            }

            private IllegalStateException createMaterializedTwiceException() {
                return new IllegalStateException(new StringBuilder(54).append("Substream Sink(").append(this.$outer.akka$stream$impl$fusing$SubSink$$name).append(") cannot be materialized more than once").toString());
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$10 $this, Command x0$1) {
                Command command = x0$1;
                if (SubSink$RequestOne$.MODULE$.equals(command)) {
                    $this.tryPull($this.$outer.akka$stream$impl$fusing$SubSink$$in());
                    return;
                }
                if (command instanceof Cancel) {
                    Cancel cancel = (Cancel)command;
                    Throwable cause = cancel.cause();
                    $this.cancelStage(cause);
                    return;
                }
                throw new MatchError(command);
            }
            {
                IllegalStateException illegalStateException;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                if ($outer.akka$stream$impl$fusing$SubSink$$status().get() instanceof AsyncCallback) {
                    illegalStateException = OptionVal$Some$.MODULE$.apply(this.createMaterializedTwiceException());
                } else {
                    OptionVal$.MODULE$.None();
                    illegalStateException = null;
                }
                this.materializationException = illegalStateException;
                this.setHandler($outer.akka$stream$impl$fusing$SubSink$$in(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$1$adapted(akka.stream.impl.fusing.SubSink$$anon$10 akka.stream.impl.fusing.SubSink$Command )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return this.akka$stream$impl$fusing$SubSink$$name;
    }

    public SubSink(String name, Function1<ActorSubscriberMessage, BoxedUnit> externalCallback) {
        this.akka$stream$impl$fusing$SubSink$$name = name;
        this.akka$stream$impl$fusing$SubSink$$externalCallback = externalCallback;
        this.akka$stream$impl$fusing$SubSink$$in = Inlet$.MODULE$.apply(new StringBuilder(12).append("SubSink(").append(name).append(").in").toString());
        this.shape = new SinkShape<T>(this.akka$stream$impl$fusing$SubSink$$in());
        this.akka$stream$impl$fusing$SubSink$$status = new AtomicReference<SubSink$Uninitialized$>(SubSink$Uninitialized$.MODULE$);
    }

    public static class Cancel
    implements Command,
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

        public Cancel copy(Throwable cause) {
            return new Cancel(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "Cancel";
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
            return x$1 instanceof Cancel;
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
            if (!(object instanceof Cancel)) return false;
            boolean bl = true;
            if (!bl) return false;
            Cancel cancel = (Cancel)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = cancel.cause();
            if (throwable == null) {
                if (throwable2 != null) {
                    return false;
                }
            } else if (!throwable.equals(throwable2)) return false;
            if (!cancel.canEqual(this)) return false;
            return true;
        }

        public Cancel(Throwable cause) {
            this.cause = cause;
            Product.$init$(this);
        }
    }

    public static class CancelScheduledBeforeMaterialization
    extends CommandScheduledBeforeMaterialization
    implements Product,
    Serializable {
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        public CancelScheduledBeforeMaterialization copy(Throwable cause) {
            return new CancelScheduledBeforeMaterialization(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "CancelScheduledBeforeMaterialization";
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
            return x$1 instanceof CancelScheduledBeforeMaterialization;
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
            if (!(object instanceof CancelScheduledBeforeMaterialization)) return false;
            boolean bl = true;
            if (!bl) return false;
            CancelScheduledBeforeMaterialization cancelScheduledBeforeMaterialization = (CancelScheduledBeforeMaterialization)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = cancelScheduledBeforeMaterialization.cause();
            if (throwable == null) {
                if (throwable2 != null) {
                    return false;
                }
            } else if (!throwable.equals(throwable2)) return false;
            if (!cancelScheduledBeforeMaterialization.canEqual(this)) return false;
            return true;
        }

        public CancelScheduledBeforeMaterialization(Throwable cause) {
            this.cause = cause;
            super(new Cancel(cause));
            Product.$init$(this);
        }
    }

    public static interface Command {
    }

    public static abstract class CommandScheduledBeforeMaterialization
    implements State {
        private final Command command;

        public Command command() {
            return this.command;
        }

        public CommandScheduledBeforeMaterialization(Command command) {
            this.command = command;
        }
    }

    public static interface State {
    }
}

