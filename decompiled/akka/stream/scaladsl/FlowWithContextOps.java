/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.annotation.ApiMayChange;
import akka.dispatch.ExecutionContexts$;
import akka.event.LogMarker;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.ThrottleMode;
import akka.stream.ThrottleMode$Shaping$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.util.ConstantFun$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.PartialFunction;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\r\u0005ca\u0002\u0010 !\u0003\r\tA\n\u0005\u0006]\u0001!\ta\f\u0003\u0006g\u0001\u0011\t\u0001N\u0003\u00053\u0002\u0001!\fC\u0003n\u0001\u0019\u0005a\u000eC\u0004\u0002\u001c\u00011\t!!\b\t\u000f\u0005}\u0002A\"\u0001\u0002B!9\u0011Q\u000e\u0001\u0005\u0002\u0005=\u0004bBAB\u0001\u0011\u0005\u0011Q\u0011\u0005\b\u0003W\u0003A\u0011AAW\u0011\u001d\t\u0019\u000e\u0001C\u0001\u0003+Dq!a?\u0001\t\u0003\ti\u0010C\u0004\u0003\f\u0001!\tA!\u0004\t\u000f\tm\u0001\u0001\"\u0001\u0003\u001e!9!\u0011\u0005\u0001\u0005\u0002\t\r\u0002b\u0002B\u001f\u0001\u0011\u0005!q\b\u0005\n\u0005\u000f\u0002\u0011\u0013!C\u0001\u0005\u0013BqA!\u0014\u0001\t\u0003\u0011y\u0005C\u0004\u0003d\u0001!\tA!\u001a\t\u000f\tM\u0004\u0001\"\u0001\u0003v!I!\u0011\u0015\u0001\u0012\u0002\u0013\u0005!1\u0015\u0005\n\u0005O\u0003\u0011\u0013!C\u0001\u0005SCqA!-\u0001\t\u0003\u0011\u0019\fC\u0005\u0003P\u0002\t\n\u0011\"\u0001\u0003$\"I!\u0011\u001b\u0001\u0012\u0002\u0013\u0005!1\u001b\u0005\b\u0005;\u0004A\u0011\u0001Bp\u0011\u001d\u0011i\u000e\u0001C\u0001\u0005kDqA!8\u0001\t\u0003\u0019I\u0001C\u0004\u0003^\u0002!\taa\u0006\t\u000fa\u0004A\u0011A\u0012\u0004$\t\u0011b\t\\8x/&$\bnQ8oi\u0016DHo\u00149t\u0015\t\u0001\u0013%\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\t\u00113%\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002I\u0005!\u0011m[6b\u0007\u0001)baJA\u0005\u0003\u001f\u00117C\u0001\u0001)!\tIC&D\u0001+\u0015\u0005Y\u0013!B:dC2\f\u0017BA\u0017+\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012\u0001\r\t\u0003SEJ!A\r\u0016\u0003\tUs\u0017\u000e\u001e\u0002\b%\u0016\u0004(/T1u+\u0011)\u0004i\u0012&\u0012\u0005YJ\u0004CA\u00158\u0013\tA$FA\u0004O_RD\u0017N\\4\u0013\u0005ibd\u0001B\u001e\u0001\u0001e\u0012A\u0002\u0010:fM&tW-\\3oiz\u0002R!\u0010\u0001?\r&k\u0011a\b\t\u0003\u007f\u0001c\u0001\u0001\u0002\u0004B\u0005\u0011\u0015\rA\u0011\u0002\u0002\u001fF\u0011ag\u0011\t\u0003S\u0011K!!\u0012\u0016\u0003\u0007\u0005s\u0017\u0010\u0005\u0002@\u000f\u00121\u0001J\u0001CC\u0002\t\u0013\u0011a\u0011\t\u0003\u007f)#aa\u0013\u0002\u0005\u0006\u0004\u0011%!A'\u0006\tMR\u0004%T\u000b\u0005\u001dF#v\u000bE\u0003P\u0005A\u001bf+D\u0001\u0001!\ty\u0014\u000b\u0002\u0004S\u0019\u0012\u0015\rA\u0011\u0002\u0003\u001f>\u0003\"a\u0010+\u0005\rUcEQ1\u0001C\u0005\t\u00195\t\u0005\u0002@/\u00121\u0001\f\u0014CC\u0002\t\u0013a!T1u\u001b\u0006$(\u0001\u0002*faJ,2aW/`!\u0015y%\u0001\u00180a!\tyT\f\u0002\u0004B\u0007\u0011\u0015\rA\u0011\t\u0003\u007f}#a\u0001S\u0002\u0005\u0006\u0004\u0011%FA1e!\ty$\r\u0002\u0004d\u0001\u0011\u0015\rA\u0011\u0002\u0004\u001b\u0006$8&A3\u0011\u0005\u0019\\W\"A4\u000b\u0005!L\u0017!C;oG\",7m[3e\u0015\tQ'&\u0001\u0006b]:|G/\u0019;j_:L!\u0001\\4\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0002wS\u0006,Ra\u001c:v\u0003/!\"\u0001]<\u0011\t=\u001b\u0011\u000f\u001e\t\u0003\u007fI$Qa\u001d\u0003C\u0002\t\u0013AaT;ueA\u0011q(\u001e\u0003\u0006m\u0012\u0011\rA\u0011\u0002\u0005\u0007RD(\u0007C\u0003y\t\u0001\u0007\u00110\u0001\u0003gY><\b#\u0002>|{\u0006UQ\"A\u0011\n\u0005q\f#!B$sCBD\u0007C\u0002>\u007f\u0003\u0003\t\u0019\"\u0003\u0002\u0000C\tIa\t\\8x'\"\f\u0007/\u001a\t\bS\u0005\r\u0011qAA\u0007\u0013\r\t)A\u000b\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0007}\nI\u0001B\u0004\u0002\f\u0001!)\u0019\u0001\"\u0003\u0007=+H\u000fE\u0002@\u0003\u001f!q!!\u0005\u0001\t\u000b\u0007!IA\u0002Dib\u0004R!KA\u0002cR\u00042aPA\f\t\u0019\tI\u0002\u0002b\u0001\u0005\n!Q*\u0019;3\u00035)hn]1gK\u0012\u000bG/\u0019,jCV1\u0011qDA\u0013\u0003c!B!!\t\u0002(A1qjAA\u0012\u0003\u001b\u00012aPA\u0013\t\u0015\u0019XA1\u0001C\u0011\u001d\tI#\u0002a\u0001\u0003W\tqA^5b\r2|w\u000f\u0005\u0004{w\u00065\u0012q\u0006\t\u0007uz\f9!a\t\u0011\u0007}\n\t\u0004\u0002\u0004\u0002\u001a\u0015\u0011\rA\u0011\u0015\u0004\u000b\u0005U\u0002\u0003BA\u001c\u0003wi!!!\u000f\u000b\u0005)\u001c\u0013\u0002BA\u001f\u0003s\u0011A\"\u00119j\u001b\u0006L8\t[1oO\u0016\faA^5b\u001b\u0006$XCCA\"\u0003\u0017\ny%a\u0019\u0002TQ!\u0011QIA3)\u0011\t9%a\u0016\u0011\u0011=\u0013\u0011\u0011JA'\u0003#\u00022aPA&\t\u0015\u0019hA1\u0001C!\ry\u0014q\n\u0003\u0006m\u001a\u0011\rA\u0011\t\u0004\u007f\u0005MCABA+\r\t\u0007!I\u0001\u0003NCR\u001c\u0004bBA-\r\u0001\u0007\u00111L\u0001\bG>l'-\u001b8f!!I\u0013QL1\u0002b\u0005E\u0013bAA0U\tIa)\u001e8di&|gN\r\t\u0004\u007f\u0005\rDABA\r\r\t\u0007!\t\u0003\u0004y\r\u0001\u0007\u0011q\r\t\u0007un\fI'!\u0019\u0011\rit\u0018\u0011AA6!\u001dI\u00131AA%\u0003\u001b\n1!\\1q+\u0011\t\t(a\u001e\u0015\t\u0005M\u0014\u0011\u0010\t\u0007\u001f\u000e\t)(!\u0004\u0011\u0007}\n9\bB\u0003t\u000f\t\u0007!\tC\u0004\u0002|\u001d\u0001\r!! \u0002\u0003\u0019\u0004r!KA@\u0003\u000f\t)(C\u0002\u0002\u0002*\u0012\u0011BR;oGRLwN\\\u0019\u0002\u00115\f\u0007/\u0012:s_J$B!a\"\u0002\nB1qjAA\u0004\u0003\u001bAq!a#\t\u0001\u0004\ti)\u0001\u0002qMB9\u0011&a$\u0002\u0014\u0006M\u0015bAAIU\ty\u0001+\u0019:uS\u0006dg)\u001e8di&|g\u000e\u0005\u0003\u0002\u0016\u0006\u0015f\u0002BAL\u0003CsA!!'\u0002 6\u0011\u00111\u0014\u0006\u0004\u0003;+\u0013A\u0002\u001fs_>$h(C\u0001,\u0013\r\t\u0019KK\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\t9+!+\u0003\u0013QC'o\\<bE2,'bAARU\u0005AQ.\u00199Bgft7-\u0006\u0003\u00020\u0006]F\u0003BAY\u0003\u0013$B!a-\u0002:B1qjAA[\u0003\u001b\u00012aPA\\\t\u0015\u0019\u0018B1\u0001C\u0011\u001d\tY(\u0003a\u0001\u0003w\u0003r!KA@\u0003\u000f\ti\f\u0005\u0004\u0002@\u0006\u0015\u0017QW\u0007\u0003\u0003\u0003T1!a1+\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u0003\u000f\f\tM\u0001\u0004GkR,(/\u001a\u0005\b\u0003\u0017L\u0001\u0019AAg\u0003-\u0001\u0018M]1mY\u0016d\u0017n]7\u0011\u0007%\ny-C\u0002\u0002R*\u00121!\u00138u\u0003Mi\u0017\r]!ts:\u001c\u0007+\u0019:uSRLwN\\3e+\u0019\t9.!9\u0002jR1\u0011\u0011\\A{\u0003o$B!a7\u0002pR!\u0011Q\\Ar!\u0019y5!a8\u0002\u000eA\u0019q(!9\u0005\u000bMT!\u0019\u0001\"\t\u000f\u0005m$\u00021\u0001\u0002fBI\u0011&!\u0018\u0002\b\u0005\u001d\u0018Q\u001e\t\u0004\u007f\u0005%HABAv\u0015\t\u0007!IA\u0001Q!\u0019\ty,!2\u0002`\"9\u0011\u0011\u001f\u0006A\u0002\u0005M\u0018a\u00039beRLG/[8oKJ\u0004r!KA@\u0003\u000f\t9\u000fC\u0004\u0002L*\u0001\r!!4\t\u000f\u0005e(\u00021\u0001\u0002N\u0006a\u0001/\u001a:QCJ$\u0018\u000e^5p]\u000691m\u001c7mK\u000e$X\u0003BA\u0000\u0005\u000b!BA!\u0001\u0003\bA1qj\u0001B\u0002\u0003\u001b\u00012a\u0010B\u0003\t\u0015\u00198B1\u0001C\u0011\u001d\tYh\u0003a\u0001\u0005\u0013\u0001r!KAH\u0003\u000f\u0011\u0019!\u0001\u0004gS2$XM\u001d\u000b\u0005\u0003\u000f\u0013y\u0001C\u0004\u0003\u00121\u0001\rAa\u0005\u0002\tA\u0014X\r\u001a\t\bS\u0005}\u0014q\u0001B\u000b!\rI#qC\u0005\u0004\u00053Q#a\u0002\"p_2,\u0017M\\\u0001\nM&dG/\u001a:O_R$B!a\"\u0003 !9!\u0011C\u0007A\u0002\tM\u0011aB4s_V\u0004X\r\u001a\u000b\u0005\u0005K\u0011I\u0004\u0005\u0004P\u0007\t\u001d\"q\u0007\t\u0007\u0005S\u0011\u0019$a\u0002\u000e\u0005\t-\"\u0002\u0002B\u0017\u0005_\t\u0011\"[7nkR\f'\r\\3\u000b\u0007\tE\"&\u0001\u0006d_2dWm\u0019;j_:LAA!\u000e\u0003,\t\u00191+Z9\u0011\r\t%\"1GA\u0007\u0011\u001d\u0011YD\u0004a\u0001\u0003\u001b\f\u0011A\\\u0001\bg2LG-\u001b8h)\u0019\u0011)C!\u0011\u0003D!9!1H\bA\u0002\u00055\u0007\"\u0003B#\u001fA\u0005\t\u0019AAg\u0003\u0011\u0019H/\u001a9\u0002#Md\u0017\u000eZ5oO\u0012\"WMZ1vYR$#'\u0006\u0002\u0003L)\u001a\u0011Q\u001a3\u0002\u00135\f\u0007oQ8oG\u0006$X\u0003\u0002B)\u0005/\"BAa\u0015\u0003ZA1qj\u0001B+\u0003\u001b\u00012a\u0010B,\t\u0015\u0019\u0018C1\u0001C\u0011\u001d\tY(\u0005a\u0001\u00057\u0002r!KA@\u0003\u000f\u0011i\u0006\u0005\u0004\u0002\u0016\n}#QK\u0005\u0005\u0005C\nIK\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW-\u0001\u0006nCB\u001cuN\u001c;fqR,BAa\u001a\u0003nQ!!\u0011\u000eB8!\u0019y5!a\u0002\u0003lA\u0019qH!\u001c\u0005\u000bY\u0014\"\u0019\u0001\"\t\u000f\u0005m$\u00031\u0001\u0003rA9\u0011&a \u0002\u000e\t-\u0014a\u00017pOR1!q\u000fBD\u00057#B!a\"\u0003z!I!1O\n\u0011\u0002\u0003\u000f!1\u0010\t\u0005\u0005{\u0012\u0019)\u0004\u0002\u0003\u0000)\u0019!\u0011Q\u0012\u0002\u000b\u00154XM\u001c;\n\t\t\u0015%q\u0010\u0002\u000f\u0019><w-\u001b8h\u0003\u0012\f\u0007\u000f^3s\u0011\u001d\u0011Ii\u0005a\u0001\u0005\u0017\u000bAA\\1nKB!!Q\u0012BK\u001d\u0011\u0011yI!%\u0011\u0007\u0005e%&C\u0002\u0003\u0014*\na\u0001\u0015:fI\u00164\u0017\u0002\u0002BL\u00053\u0013aa\u0015;sS:<'b\u0001BJU!I!QT\n\u0011\u0002\u0003\u0007!qT\u0001\bKb$(/Y2u!\u0019I\u0013qPA\u0004\u0007\u0006iAn\\4%I\u00164\u0017-\u001e7uII*\"A!*+\u0007\t}E-A\u0007m_\u001e$C-\u001a4bk2$He\r\u000b\u0007\u0005W\u0013iKa,+\u0007\tmD\rC\u0004\u0003\nV\u0001\rAa#\t\u000f\tuU\u00031\u0001\u0003 \u0006iAn\\4XSRDW*\u0019:lKJ$\u0002B!.\u0003@\n\u0005'Q\u001a\u000b\u0005\u0003\u000f\u00139\fC\u0005\u0003tY\u0001\n\u0011q\u0001\u0003:B!!Q\u0010B^\u0013\u0011\u0011iLa \u0003)5\u000b'o[3s\u0019><w-\u001b8h\u0003\u0012\f\u0007\u000f^3s\u0011\u001d\u0011II\u0006a\u0001\u0005\u0017CqAa1\u0017\u0001\u0004\u0011)-\u0001\u0004nCJ\\WM\u001d\t\nS\u0005u\u0013qAA\u0007\u0005\u000f\u0004BA! \u0003J&!!1\u001aB@\u0005%aunZ'be.,'\u000fC\u0005\u0003\u001eZ\u0001\n\u00111\u0001\u0003 \u00069Bn\\4XSRDW*\u0019:lKJ$C-\u001a4bk2$HeM\u0001\u0018Y><w+\u001b;i\u001b\u0006\u00148.\u001a:%I\u00164\u0017-\u001e7uIQ\"\u0002B!6\u0003X\ne'1\u001c\u0016\u0004\u0005s#\u0007b\u0002BE1\u0001\u0007!1\u0012\u0005\b\u0005\u0007D\u0002\u0019\u0001Bc\u0011\u001d\u0011i\n\u0007a\u0001\u0005?\u000b\u0001\u0002\u001e5s_R$H.\u001a\u000b\u0007\u0003\u000f\u0013\tO!:\t\u000f\t\r\u0018\u00041\u0001\u0002N\u0006AQ\r\\3nK:$8\u000fC\u0004\u0003hf\u0001\rA!;\u0002\u0007A,'\u000f\u0005\u0003\u0003l\nEXB\u0001Bw\u0015\u0011\u0011y/!1\u0002\u0011\u0011,(/\u0019;j_:LAAa=\u0003n\nqa)\u001b8ji\u0016$UO]1uS>tGCCAD\u0005o\u0014IPa?\u0003\u0000\"9!1\u001d\u000eA\u0002\u00055\u0007b\u0002Bt5\u0001\u0007!\u0011\u001e\u0005\b\u0005{T\u0002\u0019AAg\u00031i\u0017\r_5nk6\u0014UO]:u\u0011\u001d\u0019\tA\u0007a\u0001\u0007\u0007\tA!\\8eKB\u0019!p!\u0002\n\u0007\r\u001d\u0011E\u0001\u0007UQJ|G\u000f\u001e7f\u001b>$W\r\u0006\u0005\u0002\b\u000e-1qBB\t\u0011\u001d\u0019ia\u0007a\u0001\u0003\u001b\fAaY8ti\"9!q]\u000eA\u0002\t%\bbBB\n7\u0001\u00071QC\u0001\u0010G>\u001cHoQ1mGVd\u0017\r^5p]B9\u0011&a \u0002\b\u00055G\u0003DAD\u00073\u0019Yb!\b\u0004 \r\u0005\u0002bBB\u00079\u0001\u0007\u0011Q\u001a\u0005\b\u0005Od\u0002\u0019\u0001Bu\u0011\u001d\u0011i\u0010\ba\u0001\u0003\u001bDqaa\u0005\u001d\u0001\u0004\u0019)\u0002C\u0004\u0004\u0002q\u0001\raa\u0001\u0016\r\r\u00152\u0011GB\u001c+\t\u00199\u0003E\u0005>\u0007S\u0019ic!\f\u0004:%\u001911F\u0010\u0003\t\u0019cwn\u001e\t\bS\u0005\r1qFB\u001b!\ry4\u0011\u0007\u0003\u0007\u0007gi\"\u0019\u0001\"\u0003\u0003Q\u00032aPB\u001c\t\u0015AUD1\u0001C!\u0011\u0019Yd!\u0010\u000e\u0003\rJ1aa\u0010$\u0005\u001dqu\u000e^+tK\u0012\u0004")
public interface FlowWithContextOps<Out, Ctx, Mat> {
    public <Out2, Ctx2, Mat2> FlowWithContextOps via(Graph<FlowShape<Tuple2<Out, Ctx>, Tuple2<Out2, Ctx2>>, Mat2> var1);

    @ApiMayChange
    public <Out2, Mat2> FlowWithContextOps unsafeDataVia(Graph<FlowShape<Out, Out2>, Mat2> var1);

    public <Out2, Ctx2, Mat2, Mat3> FlowWithContextOps viaMat(Graph<FlowShape<Tuple2<Out, Ctx>, Tuple2<Out2, Ctx2>>, Mat2> var1, Function2<Mat, Mat2, Mat3> var2);

    public static /* synthetic */ FlowWithContextOps map$(FlowWithContextOps $this, Function1 f) {
        return $this.map(f);
    }

    default public <Out2> FlowWithContextOps map(Function1<Out, Out2> f) {
        return this.via((Graph)((Object)this.flow().map((Function1<Tuple2, Tuple2> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object e = tuple2._1();
                Object ctx = tuple2._2();
                return new Tuple2(f.apply(e), ctx);
            }
            throw new MatchError(tuple2);
        })));
    }

    public static /* synthetic */ FlowWithContextOps mapError$(FlowWithContextOps $this, PartialFunction pf) {
        return $this.mapError(pf);
    }

    default public FlowWithContextOps mapError(PartialFunction<Throwable, Throwable> pf) {
        return this.via((Graph)((Object)this.flow().mapError(pf)));
    }

    public static /* synthetic */ FlowWithContextOps mapAsync$(FlowWithContextOps $this, int parallelism, Function1 f) {
        return $this.mapAsync(parallelism, f);
    }

    default public <Out2> FlowWithContextOps mapAsync(int parallelism, Function1<Out, Future<Out2>> f) {
        return this.via((Graph)((Object)this.flow().mapAsync(parallelism, (Function1<Tuple2, Future> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object e = tuple2._1();
                Object ctx = tuple2._2();
                return ((Future)f.apply(e)).map((Function1<Object, Tuple2> & Serializable)o -> new Tuple2<Object, Object>(o, ctx), ExecutionContexts$.MODULE$.parasitic());
            }
            throw new MatchError(tuple2);
        })));
    }

    public static /* synthetic */ FlowWithContextOps mapAsyncPartitioned$(FlowWithContextOps $this, int parallelism, int perPartition, Function1 partitioner, Function2 f) {
        return $this.mapAsyncPartitioned(parallelism, perPartition, partitioner, f);
    }

    default public <Out2, P> FlowWithContextOps mapAsyncPartitioned(int parallelism, int perPartition, Function1<Out, P> partitioner, Function2<Out, P, Future<Out2>> f) {
        Function1<Tuple2, Object> & Serializable pairPartitioner = (Function1<Tuple2, Object> & Serializable)pair -> partitioner.apply(pair._1());
        Function2<Tuple2, Object, Future> & Serializable pairF = (Function2<Tuple2, Object, Future> & Serializable)(pair, partition) -> {
            Tuple2 tuple2 = pair;
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            Object elem = tuple2._1();
            Object context = tuple2._2();
            Tuple2 tuple22 = new Tuple2(elem, context);
            Object elem2 = tuple22._1();
            Object context2 = tuple22._2();
            return ((Future)f.apply(elem2, partition)).map((Function1<Object, Tuple2> & Serializable)x$2 -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(x$2), context2), ExecutionContexts$.MODULE$.parasitic());
        };
        return this.via((Graph)((Object)this.flow().mapAsyncPartitioned(parallelism, perPartition, pairPartitioner, pairF)));
    }

    public static /* synthetic */ FlowWithContextOps collect$(FlowWithContextOps $this, PartialFunction f) {
        return $this.collect(f);
    }

    default public <Out2> FlowWithContextOps collect(PartialFunction<Out, Out2> f) {
        return this.via((Graph)((Object)this.flow().collect(new Serializable(null, f){
            private static final long serialVersionUID = 0L;
            private final PartialFunction f$4;

            public final <A1 extends Tuple2<Out, Ctx>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 != null) {
                    Out e = A1._1();
                    Ctx ctx = A1._2();
                    if (this.f$4.isDefinedAt(e)) {
                        return (B1)new Tuple2<R, Ctx>(this.f$4.apply(e), ctx);
                    }
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Tuple2<Out, Ctx> x1) {
                Out e;
                Tuple2<Out, Ctx> tuple2 = x1;
                return tuple2 != null && this.f$4.isDefinedAt(e = tuple2._1());
            }
            {
                this.f$4 = f$4;
            }
        })));
    }

    public static /* synthetic */ FlowWithContextOps filter$(FlowWithContextOps $this, Function1 pred) {
        return $this.filter(pred);
    }

    default public FlowWithContextOps filter(Function1<Out, Object> pred) {
        return this.collect((PartialFunction)((Object)new Serializable(null, pred){
            private static final long serialVersionUID = 0L;
            private final Function1 pred$1;

            public final <A1 extends Out, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (BoxesRunTime.unboxToBoolean(this.pred$1.apply(A1))) {
                    return (B1)A1;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Out x1) {
                Out Out2 = x1;
                return BoxesRunTime.unboxToBoolean(this.pred$1.apply(Out2));
            }
            {
                this.pred$1 = pred$1;
            }
        }));
    }

    public static /* synthetic */ FlowWithContextOps filterNot$(FlowWithContextOps $this, Function1 pred) {
        return $this.filterNot(pred);
    }

    default public FlowWithContextOps filterNot(Function1<Out, Object> pred) {
        return this.collect((PartialFunction)((Object)new Serializable(null, pred){
            private static final long serialVersionUID = 0L;
            private final Function1 pred$2;

            public final <A1 extends Out, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (!BoxesRunTime.unboxToBoolean(this.pred$2.apply(A1))) {
                    return (B1)A1;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Out x1) {
                Out Out2 = x1;
                return !BoxesRunTime.unboxToBoolean(this.pred$2.apply(Out2));
            }
            {
                this.pred$2 = pred$2;
            }
        }));
    }

    public static /* synthetic */ FlowWithContextOps grouped$(FlowWithContextOps $this, int n) {
        return $this.grouped(n);
    }

    default public FlowWithContextOps grouped(int n) {
        return this.via((Graph)((Object)this.flow().grouped(n).map((Function1<Seq, Tuple2> & Serializable)(Out elsWithContext) -> {
            Tuple2 tuple2 = elsWithContext.unzip(Predef$.MODULE$.$conforms());
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            Seq els = (Seq)tuple2._1();
            Seq ctxs = (Seq)tuple2._2();
            Tuple2<Seq, Seq> tuple22 = new Tuple2<Seq, Seq>(els, ctxs);
            Seq els2 = tuple22._1();
            Seq ctxs2 = tuple22._2();
            return new Tuple2<Seq, Seq>(els2, ctxs2);
        })));
    }

    public static /* synthetic */ FlowWithContextOps sliding$(FlowWithContextOps $this, int n, int step) {
        return $this.sliding(n, step);
    }

    default public FlowWithContextOps sliding(int n, int step) {
        return this.via((Graph)((Object)this.flow().sliding(n, step).map((Function1<Seq, Tuple2> & Serializable)(Out elsWithContext) -> {
            Tuple2 tuple2 = elsWithContext.unzip(Predef$.MODULE$.$conforms());
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            Seq els = (Seq)tuple2._1();
            Seq ctxs = (Seq)tuple2._2();
            Tuple2<Seq, Seq> tuple22 = new Tuple2<Seq, Seq>(els, ctxs);
            Seq els2 = tuple22._1();
            Seq ctxs2 = tuple22._2();
            return new Tuple2<Seq, Seq>(els2, ctxs2);
        })));
    }

    public static /* synthetic */ int sliding$default$2$(FlowWithContextOps $this) {
        return $this.sliding$default$2();
    }

    default public int sliding$default$2() {
        return 1;
    }

    public static /* synthetic */ FlowWithContextOps mapConcat$(FlowWithContextOps $this, Function1 f) {
        return $this.mapConcat(f);
    }

    default public <Out2> FlowWithContextOps mapConcat(Function1<Out, IterableOnce<Out2>> f) {
        return this.via((Graph)((Object)this.flow().mapConcat((Function1<Tuple2, IterableOnce> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object e = tuple2._1();
                Object ctx = tuple2._2();
                return ((IterableOnce)f.apply(e)).iterator().map((Function1<Object, Tuple2> & Serializable)(A x$5) -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(x$5), ctx));
            }
            throw new MatchError(tuple2);
        })));
    }

    public static /* synthetic */ FlowWithContextOps mapContext$(FlowWithContextOps $this, Function1 f) {
        return $this.mapContext(f);
    }

    default public <Ctx2> FlowWithContextOps mapContext(Function1<Ctx, Ctx2> f) {
        return this.via((Graph)((Object)this.flow().map((Function1<Tuple2, Tuple2> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object e = tuple2._1();
                Object ctx = tuple2._2();
                return new Tuple2(e, f.apply(ctx));
            }
            throw new MatchError(tuple2);
        })));
    }

    public static /* synthetic */ FlowWithContextOps log$(FlowWithContextOps $this, String name, Function1 extract, LoggingAdapter log) {
        return $this.log(name, extract, log);
    }

    default public FlowWithContextOps log(String name, Function1<Out, Object> extract, LoggingAdapter log) {
        Function1<Tuple2, Object> & Serializable extractWithContext = (Function1<Tuple2, Object> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object e = tuple2._1();
                return extract.apply(e);
            }
            throw new MatchError(tuple2);
        };
        return this.via((Graph)((Object)this.flow().log(name, extractWithContext, log)));
    }

    public static /* synthetic */ Function1 log$default$2$(FlowWithContextOps $this) {
        return $this.log$default$2();
    }

    default public Function1<Out, Object> log$default$2() {
        return ConstantFun$.MODULE$.scalaIdentityFunction();
    }

    public static /* synthetic */ LoggingAdapter log$default$3$(FlowWithContextOps $this, String name, Function1 extract) {
        return $this.log$default$3(name, extract);
    }

    default public LoggingAdapter log$default$3(String name, Function1<Out, Object> extract) {
        return null;
    }

    public static /* synthetic */ FlowWithContextOps logWithMarker$(FlowWithContextOps $this, String name, Function2 marker, Function1 extract, MarkerLoggingAdapter log) {
        return $this.logWithMarker(name, marker, extract, log);
    }

    default public FlowWithContextOps logWithMarker(String name, Function2<Out, Ctx, LogMarker> marker, Function1<Out, Object> extract, MarkerLoggingAdapter log) {
        Function1<Tuple2, Object> & Serializable extractWithContext = (Function1<Tuple2, Object> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object e = tuple2._1();
                return extract.apply(e);
            }
            throw new MatchError(tuple2);
        };
        return this.via((Graph)((Object)this.flow().logWithMarker(name, marker.tupled(), extractWithContext, log)));
    }

    public static /* synthetic */ Function1 logWithMarker$default$3$(FlowWithContextOps $this) {
        return $this.logWithMarker$default$3();
    }

    default public Function1<Out, Object> logWithMarker$default$3() {
        return ConstantFun$.MODULE$.scalaIdentityFunction();
    }

    public static /* synthetic */ MarkerLoggingAdapter logWithMarker$default$4$(FlowWithContextOps $this, String name, Function2 marker, Function1 extract) {
        return $this.logWithMarker$default$4(name, marker, extract);
    }

    default public MarkerLoggingAdapter logWithMarker$default$4(String name, Function2<Out, Ctx, LogMarker> marker, Function1<Out, Object> extract) {
        return null;
    }

    public static /* synthetic */ FlowWithContextOps throttle$(FlowWithContextOps $this, int elements, FiniteDuration per) {
        return $this.throttle(elements, per);
    }

    default public FlowWithContextOps throttle(int elements, FiniteDuration per) {
        return this.throttle(elements, per, -1, ConstantFun$.MODULE$.oneInt(), ThrottleMode$Shaping$.MODULE$);
    }

    public static /* synthetic */ FlowWithContextOps throttle$(FlowWithContextOps $this, int elements, FiniteDuration per, int maximumBurst, ThrottleMode mode) {
        return $this.throttle(elements, per, maximumBurst, mode);
    }

    default public FlowWithContextOps throttle(int elements, FiniteDuration per, int maximumBurst, ThrottleMode mode) {
        return this.throttle(elements, per, maximumBurst, ConstantFun$.MODULE$.oneInt(), mode);
    }

    public static /* synthetic */ FlowWithContextOps throttle$(FlowWithContextOps $this, int cost, FiniteDuration per, Function1 costCalculation) {
        return $this.throttle(cost, per, costCalculation);
    }

    default public FlowWithContextOps throttle(int cost, FiniteDuration per, Function1<Out, Object> costCalculation) {
        return this.throttle(cost, per, -1, costCalculation, ThrottleMode$Shaping$.MODULE$);
    }

    public static /* synthetic */ FlowWithContextOps throttle$(FlowWithContextOps $this, int cost, FiniteDuration per, int maximumBurst, Function1 costCalculation, ThrottleMode mode) {
        return $this.throttle(cost, per, maximumBurst, costCalculation, mode);
    }

    default public FlowWithContextOps throttle(int cost, FiniteDuration per, int maximumBurst, Function1<Out, Object> costCalculation, ThrottleMode mode) {
        return this.via((Graph)((Object)this.flow().throttle(cost, per, maximumBurst, (Function1<Tuple2, Object> & Serializable)a -> BoxesRunTime.boxToInteger(FlowWithContextOps.$anonfun$throttle$1(costCalculation, a)), mode)));
    }

    public static /* synthetic */ Flow flow$(FlowWithContextOps $this) {
        return $this.flow();
    }

    default public <T, C> Flow<Tuple2<T, C>, Tuple2<T, C>, NotUsed> flow() {
        return Flow$.MODULE$.apply();
    }

    public static /* synthetic */ int $anonfun$throttle$1(Function1 costCalculation$1, Tuple2 a) {
        return BoxesRunTime.unboxToInt(costCalculation$1.apply(a._1()));
    }

    public static void $init$(FlowWithContextOps $this) {
    }
}

