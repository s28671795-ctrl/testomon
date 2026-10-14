/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.Done;
import akka.Done$;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.BufferOverflowException;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.OverflowStrategies;
import akka.stream.OverflowStrategy;
import akka.stream.QueueOfferResult;
import akka.stream.QueueOfferResult$Dropped$;
import akka.stream.QueueOfferResult$Enqueued$;
import akka.stream.QueueOfferResult$QueueClosed$;
import akka.stream.SourceShape;
import akka.stream.SourceShape$;
import akka.stream.StreamDetachedException;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import akka.stream.impl.QueueSource$;
import akka.stream.impl.QueueSource$Completion$;
import akka.stream.scaladsl.SourceQueueWithComplete;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\r\rrA\u0002%J\u0011\u0003iuJ\u0002\u0004R\u0013\"\u0005QJ\u0015\u0005\u00063\u0006!\ta\u0017\u0004\b9\u0006\u0001\n1%\t^\r\u0019\tI*\u0001\"\u0002\u001c\"Q\u0011q\u0015\u0003\u0003\u0016\u0004%\t!!+\t\u0015\u0005-FA!E!\u0002\u0013\t\t\u000b\u0003\u0006\u0002.\u0012\u0011)\u001a!C\u0001\u0003_C!\"!2\u0005\u0005#\u0005\u000b\u0011BAY\u0011\u0019IF\u0001\"\u0001\u0002H\"I\u0011q\r\u0003\u0002\u0002\u0013\u0005\u0011q\u001a\u0005\n\u0003[\"\u0011\u0013!C\u0001\u0003;D\u0011\"!:\u0005#\u0003%\t!a:\t\u000fy$\u0011\u0011!C!\u007f\"I\u0011\u0011\u0003\u0003\u0002\u0002\u0013\u0005\u00111\u0003\u0005\n\u00037!\u0011\u0011!C\u0001\u0003_D\u0011\"a\t\u0005\u0003\u0003%\t%!\n\t\u0013\u0005MB!!A\u0005\u0002\u0005M\b\"CAG\t\u0005\u0005I\u0011IA|\u0011%\ty\u0004BA\u0001\n\u0003\n\t\u0005C\u0005\u0002D\u0011\t\t\u0011\"\u0011\u0002F!I\u00111\u0013\u0003\u0002\u0002\u0013\u0005\u00131`\u0004\n\u0003\u007f\f\u0011\u0011!E\u0001\u0005\u00031\u0011\"!'\u0002\u0003\u0003E\tAa\u0001\t\re;B\u0011\u0001B\b\u0011%\t\u0019eFA\u0001\n\u000b\n)\u0005C\u0005\u0003\u0012]\t\t\u0011\"!\u0003\u0014!I!\u0011E\f\u0002\u0002\u0013\u0005%1\u0005\u0005\n\u0003\u000f:\u0012\u0011!C\u0005\u0003\u0013:aA!\u0010\u0002\u0011\u0003kh!B5\u0002\u0011\u0003S\u0007\"B-\u001f\t\u0003a\bb\u0002@\u001f\u0003\u0003%\te \u0005\n\u0003#q\u0012\u0011!C\u0001\u0003'A\u0011\"a\u0007\u001f\u0003\u0003%\t!!\b\t\u0013\u0005\rb$!A\u0005B\u0005\u0015\u0002\"CA\u001a=\u0005\u0005I\u0011AA\u001b\u0011%\tyDHA\u0001\n\u0003\n\t\u0005C\u0005\u0002Dy\t\t\u0011\"\u0011\u0002F!I\u0011q\t\u0010\u0002\u0002\u0013%\u0011\u0011\n\u0004\u0007\u0003#\n!)a\u0015\t\u0015\u0005U\u0003F!f\u0001\n\u0003\t9\u0006\u0003\u0006\u0002`!\u0012\t\u0012)A\u0005\u00033Ba!\u0017\u0015\u0005\u0002\u0005\u0005\u0004\"CA4Q\u0005\u0005I\u0011AA5\u0011%\ti\u0007KI\u0001\n\u0003\ty\u0007C\u0004\u007fQ\u0005\u0005I\u0011I@\t\u0013\u0005E\u0001&!A\u0005\u0002\u0005M\u0001\"CA\u000eQ\u0005\u0005I\u0011AAC\u0011%\t\u0019\u0003KA\u0001\n\u0003\n)\u0003C\u0005\u00024!\n\t\u0011\"\u0001\u0002\n\"I\u0011Q\u0012\u0015\u0002\u0002\u0013\u0005\u0013q\u0012\u0005\n\u0003\u007fA\u0013\u0011!C!\u0003\u0003B\u0011\"a\u0011)\u0003\u0003%\t%!\u0012\t\u0013\u0005M\u0005&!A\u0005B\u0005Uu!\u0003B \u0003\u0005\u0005\t\u0012\u0001B!\r%\t\t&AA\u0001\u0012\u0003\u0011\u0019\u0005\u0003\u0004Zq\u0011\u0005!\u0011\u000b\u0005\n\u0003\u0007B\u0014\u0011!C#\u0003\u000bB\u0011B!\u00059\u0003\u0003%\tIa\u0015\t\u0013\t\u0005\u0002(!A\u0005\u0002\n]\u0003\"CA$q\u0005\u0005I\u0011BA%\r\u0019\t\u0016JA'\u0003l!Q!\u0011\u0013 \u0003\u0002\u0003\u0006I!!\u0006\t\u0015\tMeH!A!\u0002\u0013\u0011)\n\u0003\u0006\u0003\u001cz\u0012\t\u0011)A\u0005\u0003+Aa!\u0017 \u0005\u0002\tu\u0005\"\u0003BT}\t\u0007I\u0011\u0001BU\u0011!\u0011\tL\u0010Q\u0001\n\t-\u0006\"\u0003BZ}\t\u0007I\u0011\tB[\u0011!\u00119L\u0010Q\u0001\n\tm\u0004b\u0002B]}\u0011\u0005#1X\u0001\f#V,W/Z*pkJ\u001cWM\u0003\u0002K\u0017\u0006!\u0011.\u001c9m\u0015\taU*\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001d\u0006!\u0011m[6b!\t\u0001\u0016!D\u0001J\u0005-\tV/Z;f'>,(oY3\u0014\u0005\u0005\u0019\u0006C\u0001+X\u001b\u0005)&\"\u0001,\u0002\u000bM\u001c\u0017\r\\1\n\u0005a+&AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005y%!B%oaV$XC\u00010`'\t\u00191\u000b\u0002\u0004a\u0007\u0011\u0015\r!\u0019\u0002\u0002)F\u0011!-\u001a\t\u0003)\u000eL!\u0001Z+\u0003\u000f9{G\u000f[5oOB\u0011AKZ\u0005\u0003OV\u00131!\u00118zS\u0011\u0019a\u0004\u000b\u0003\u0003\u0015\r{W\u000e\u001d7fi&|gnE\u0003\u001f'.l\u0007\u000fE\u0002m\u0007\tl\u0011!\u0001\t\u0003):L!a\\+\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0011/\u001f\b\u0003e^t!a\u001d<\u000e\u0003QT!!\u001e.\u0002\rq\u0012xn\u001c;?\u0013\u00051\u0016B\u0001=V\u0003\u001d\u0001\u0018mY6bO\u0016L!A_>\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005a,F#A?\u0011\u00051t\u0012!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002\u0002A!\u00111AA\u0007\u001b\t\t)A\u0003\u0003\u0002\b\u0005%\u0011\u0001\u00027b]\u001eT!!a\u0003\u0002\t)\fg/Y\u0005\u0005\u0003\u001f\t)A\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003+\u00012\u0001VA\f\u0013\r\tI\"\u0016\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004K\u0006}\u0001\"CA\u0011E\u0005\u0005\t\u0019AA\u000b\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0005\t\u0006\u0003S\ty#Z\u0007\u0003\u0003WQ1!!\fV\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003c\tYC\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u001c\u0003{\u00012\u0001VA\u001d\u0013\r\tY$\u0016\u0002\b\u0005>|G.Z1o\u0011!\t\t\u0003JA\u0001\u0002\u0004)\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005U\u0011\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005\u0005\u0011\u0001D<sSR,'+\u001a9mC\u000e,GCAA&!\u0011\t\u0019!!\u0014\n\t\u0005=\u0013Q\u0001\u0002\u0007\u001f\nTWm\u0019;\u0003\u000f\u0019\u000b\u0017\u000e\\;sKN)\u0001fU6na\u0006\u0011Q\r_\u000b\u0003\u00033\u00022!]A.\u0013\r\tif\u001f\u0002\n)\"\u0014xn^1cY\u0016\f1!\u001a=!)\u0011\t\u0019'!\u001a\u0011\u00051D\u0003bBA+W\u0001\u0007\u0011\u0011L\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003\u0002d\u0005-\u0004\"CA+YA\u0005\t\u0019AA-\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!!\u001d+\t\u0005e\u00131O\u0016\u0003\u0003k\u0002B!a\u001e\u0002\u00026\u0011\u0011\u0011\u0010\u0006\u0005\u0003w\ni(A\u0005v]\u000eDWmY6fI*\u0019\u0011qP+\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\u0004\u0006e$!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dKR\u0019Q-a\"\t\u0013\u0005\u0005\u0002'!AA\u0002\u0005UA\u0003BA\u001c\u0003\u0017C\u0001\"!\t3\u0003\u0003\u0005\r!Z\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002\u0002\u0005E\u0005\"CA\u0011g\u0005\u0005\t\u0019AA\u000b\u0003\u0019)\u0017/^1mgR!\u0011qGAL\u0011!\t\tCNA\u0001\u0002\u0004)'!B(gM\u0016\u0014X\u0003BAO\u0003K\u001bb\u0001B*\u0002 6\u0004\b\u0003\u00027\u0004\u0003C\u0003B!a)\u0002&2\u0001AA\u00021\u0005\t\u000b\u0007\u0011-\u0001\u0003fY\u0016lWCAAQ\u0003\u0015)G.Z7!\u0003\u001d\u0001(o\\7jg\u0016,\"!!-\u0011\r\u0005M\u0016\u0011XA_\u001b\t\t)LC\u0002\u00028V\u000b!bY8oGV\u0014(/\u001a8u\u0013\u0011\tY,!.\u0003\u000fA\u0013x.\\5tKB!\u0011qXAa\u001b\u0005Y\u0015bAAb\u0017\n\u0001\u0012+^3vK>3g-\u001a:SKN,H\u000e^\u0001\taJ|W.[:fAQ1\u0011\u0011ZAf\u0003\u001b\u0004B\u0001\u001c\u0003\u0002\"\"9\u0011qU\u0005A\u0002\u0005\u0005\u0006bBAW\u0013\u0001\u0007\u0011\u0011W\u000b\u0005\u0003#\f9\u000e\u0006\u0004\u0002T\u0006e\u00171\u001c\t\u0005Y\u0012\t)\u000e\u0005\u0003\u0002$\u0006]G!\u00021\u000b\u0005\u0004\t\u0007\"CAT\u0015A\u0005\t\u0019AAk\u0011%\tiK\u0003I\u0001\u0002\u0004\t\t,\u0006\u0003\u0002`\u0006\rXCAAqU\u0011\t\t+a\u001d\u0005\u000b\u0001\\!\u0019A1\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU!\u0011\u0011^Aw+\t\tYO\u000b\u0003\u00022\u0006MD!\u00021\r\u0005\u0004\tGcA3\u0002r\"I\u0011\u0011E\b\u0002\u0002\u0003\u0007\u0011Q\u0003\u000b\u0005\u0003o\t)\u0010\u0003\u0005\u0002\"E\t\t\u00111\u0001f)\u0011\t\t!!?\t\u0013\u0005\u0005\"#!AA\u0002\u0005UA\u0003BA\u001c\u0003{D\u0001\"!\t\u0016\u0003\u0003\u0005\r!Z\u0001\u0006\u001f\u001a4WM\u001d\t\u0003Y^\u0019BaF*\u0003\u0006A!!q\u0001B\u0007\u001b\t\u0011IA\u0003\u0003\u0003\f\u0005%\u0011AA5p\u0013\rQ(\u0011\u0002\u000b\u0003\u0005\u0003\tQ!\u00199qYf,BA!\u0006\u0003\u001cQ1!q\u0003B\u000f\u0005?\u0001B\u0001\u001c\u0003\u0003\u001aA!\u00111\u0015B\u000e\t\u0015\u0001'D1\u0001b\u0011\u001d\t9K\u0007a\u0001\u00053Aq!!,\u001b\u0001\u0004\t\t,A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\t\u0015\"Q\u0007\u000b\u0005\u0005O\u00119\u0004E\u0003U\u0005S\u0011i#C\u0002\u0003,U\u0013aa\u00149uS>t\u0007c\u0002+\u00030\tM\u0012\u0011W\u0005\u0004\u0005c)&A\u0002+va2,'\u0007\u0005\u0003\u0002$\nUB!\u00021\u001c\u0005\u0004\t\u0007\"\u0003B\u001d7\u0005\u0005\t\u0019\u0001B\u001e\u0003\rAH\u0005\r\t\u0005Y\u0012\u0011\u0019$\u0001\u0006D_6\u0004H.\u001a;j_:\fqAR1jYV\u0014X\r\u0005\u0002mqM)\u0001H!\u0012\u0003\u0006AA!q\tB'\u00033\n\u0019'\u0004\u0002\u0003J)\u0019!1J+\u0002\u000fI,h\u000e^5nK&!!q\nB%\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\u000b\u0003\u0005\u0003\"B!a\u0019\u0003V!9\u0011QK\u001eA\u0002\u0005eC\u0003\u0002B-\u00057\u0002R\u0001\u0016B\u0015\u00033B\u0011B!\u000f=\u0003\u0003\u0005\r!a\u0019)\u0007\u0005\u0011y\u0006\u0005\u0003\u0003b\t\u0015TB\u0001B2\u0015\r\ty(T\u0005\u0005\u0005O\u0012\u0019GA\u0006J]R,'O\\1m\u0003BL\u0007f\u0001\u0001\u0003`U!!Q\u000eBB'\rq$q\u000e\t\t\u0005c\u00129Ha\u001f\u0003\u00066\u0011!1\u000f\u0006\u0004\u0005kZ\u0015!B:uC\u001e,\u0017\u0002\u0002B=\u0005g\u0012qd\u0012:ba\"\u001cF/Y4f/&$\b.T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f!\u0019\tyL! \u0003\u0002&\u0019!qP&\u0003\u0017M{WO]2f'\"\f\u0007/\u001a\t\u0005\u0003G\u0013\u0019\tB\u0003a}\t\u0007\u0011\r\u0005\u0004\u0003\b\n5%\u0011Q\u0007\u0003\u0005\u0013S1Aa#L\u0003!\u00198-\u00197bINd\u0017\u0002\u0002BH\u0005\u0013\u0013qcU8ve\u000e,\u0017+^3vK^KG\u000f[\"p[BdW\r^3\u0002\u00135\f\u0007PQ;gM\u0016\u0014\u0018\u0001E8wKJ4Gn\\<TiJ\fG/Z4z!\u0011\tyLa&\n\u0007\te5J\u0001\tPm\u0016\u0014h\r\\8x'R\u0014\u0018\r^3hs\u0006\u0019R.\u0019=D_:\u001cWO\u001d:f]R|eMZ3sgRA!q\u0014BQ\u0005G\u0013)\u000b\u0005\u0003Q}\t\u0005\u0005b\u0002BI\u0005\u0002\u0007\u0011Q\u0003\u0005\b\u0005'\u0013\u0005\u0019\u0001BK\u0011\u001d\u0011YJ\u0011a\u0001\u0003+\t1a\\;u+\t\u0011Y\u000b\u0005\u0004\u0002@\n5&\u0011Q\u0005\u0004\u0005_[%AB(vi2,G/\u0001\u0003pkR\u0004\u0013!B:iCB,WC\u0001B>\u0003\u0019\u0019\b.\u00199fA\u0005y2M]3bi\u0016dunZ5d\u0003:$W*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0015\t\tu6q\u0003\t\b)\n=\"q\u0018B`%)\u0011\tM!3\u0003P\n\u0015%Q\u001b\u0004\b\u0005\u0007\u0014)\r\u0001B`\u00051a$/\u001a4j]\u0016lWM\u001c;?\u0011\u0019\u00119m\u0012\u0001\u0003@\u0006Q1\u000f^1hK2{w-[2\u0011\t\tE$1Z\u0005\u0005\u0005\u001b\u0014\u0019HA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d!\u0011\u0011\tH!5\n\t\tM'1\u000f\u0002\u000b\u001fV$\b*\u00198eY\u0016\u0014\b\u0003\u0002B9\u0005/LAA!7\u0003t\ta1\u000b^1hK2{wmZ5oO\"a!Q\u001cBa\u0001\u0004\u0005\r\u0011\"\u0001\u0003`\u00061!-\u001e4gKJ,\"A!9\u0011\u000bA\u0013\u0019O!!\n\u0007\t\u0015\u0018J\u0001\u0004Ck\u001a4WM\u001d\u0005\r\u0005S\u0014\t\r1AA\u0002\u0013\u0005!1^\u0001\u000bEV4g-\u001a:`I\u0015\fH\u0003\u0002Bw\u0005g\u00042\u0001\u0016Bx\u0013\r\u0011\t0\u0016\u0002\u0005+:LG\u000f\u0003\u0006\u0002\"\t\u001d\u0018\u0011!a\u0001\u0005CDABa>\u0003B\u0002\u0007\t\u0019!C\u0001\u0005s\fQ\u0002]3oI&twm\u00144gKJ\u001cXC\u0001B~!\u0015\u0001&1\u001dB\u007f!\u0015\u0011y\u0010\u0002BA\u001d\t\u0001\u0006\u0001\u0003\u0007\u0004\u0004\t\u0005\u0007\u0019!a\u0001\n\u0003\u0019)!A\tqK:$\u0017N\\4PM\u001a,'o]0%KF$BA!<\u0004\b!Q\u0011\u0011EB\u0001\u0003\u0003\u0005\rAa?\t\u0015\r-!\u0011\u0019a\u0001\n\u0003\u0019i!A\u0006uKJl\u0017N\\1uS:<WCAA\u001c\u0011)\u0019\tB!1A\u0002\u0013\u000511C\u0001\u0010i\u0016\u0014X.\u001b8bi&twm\u0018\u0013fcR!!Q^B\u000b\u0011)\t\tca\u0004\u0002\u0002\u0003\u0007\u0011q\u0007\u0005\b\u000739\u0005\u0019AB\u000e\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t!\u0011\tyl!\b\n\u0007\r}1J\u0001\u0006BiR\u0014\u0018NY;uKND3A\u0010B0\u0001")
@InternalApi
public final class QueueSource<T>
extends GraphStageWithMaterializedValue<SourceShape<T>, SourceQueueWithComplete<T>> {
    public final int akka$stream$impl$QueueSource$$maxBuffer;
    public final OverflowStrategy akka$stream$impl$QueueSource$$overflowStrategy;
    public final int akka$stream$impl$QueueSource$$maxConcurrentOffers;
    private final Outlet<T> out;
    private final SourceShape<T> shape;

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, GraphStageLogic> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise completion = Promise$.MODULE$.apply();
        String name = inheritedAttributes.nameOrDefault(this.getClass().toString());
        OutHandler stageLogic = new OutHandler(this, inheritedAttributes, completion, name){
            private Buffer<T> buffer;
            private Buffer<Offer<T>> pendingOffers;
            private boolean terminating;
            private final AsyncCallback<Input<T>> callback;
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ QueueSource $outer;
            private final Attributes inheritedAttributes$1;
            private final Promise completion$1;
            private final String name$1;

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            public Class<?> logSource() {
                return QueueSource.class;
            }

            public Buffer<T> buffer() {
                return this.buffer;
            }

            public void buffer_$eq(Buffer<T> x$1) {
                this.buffer = x$1;
            }

            public Buffer<Offer<T>> pendingOffers() {
                return this.pendingOffers;
            }

            public void pendingOffers_$eq(Buffer<Offer<T>> x$1) {
                this.pendingOffers = x$1;
            }

            public boolean terminating() {
                return this.terminating;
            }

            public void terminating_$eq(boolean x$1) {
                this.terminating = x$1;
            }

            public void preStart() {
                if (this.$outer.akka$stream$impl$QueueSource$$maxBuffer > 0) {
                    this.buffer_$eq(Buffer$.MODULE$.apply(this.$outer.akka$stream$impl$QueueSource$$maxBuffer, this.inheritedAttributes$1));
                }
                this.pendingOffers_$eq(Buffer$.MODULE$.apply(this.$outer.akka$stream$impl$QueueSource$$maxConcurrentOffers, this.inheritedAttributes$1));
            }

            public void postStop() {
                StreamDetachedException exception = new StreamDetachedException();
                this.completion$1.tryFailure(exception);
            }

            private void enqueueAndSuccess(Offer<T> offer) {
                this.buffer().enqueue(offer.elem());
                offer.promise().success(QueueOfferResult$Enqueued$.MODULE$);
            }

            private void bufferElem(Offer<T> offer) {
                if (!this.buffer().isFull()) {
                    this.enqueueAndSuccess(offer);
                    return;
                }
                OverflowStrategy overflowStrategy = this.$outer.akka$stream$impl$QueueSource$$overflowStrategy;
                if (overflowStrategy instanceof OverflowStrategies.DropHead) {
                    OverflowStrategies.DropHead dropHead = (OverflowStrategies.DropHead)overflowStrategy;
                    this.log().log(dropHead.logLevel(), "Dropping the head element because buffer is full and overflowStrategy is: [DropHead] in stream [{}]", this.name$1);
                    this.buffer().dropHead();
                    this.enqueueAndSuccess(offer);
                    return;
                }
                if (overflowStrategy instanceof OverflowStrategies.DropTail) {
                    OverflowStrategies.DropTail dropTail = (OverflowStrategies.DropTail)overflowStrategy;
                    this.log().log(dropTail.logLevel(), "Dropping the tail element because buffer is full and overflowStrategy is: [DropTail] in stream [{}]", this.name$1);
                    this.buffer().dropTail();
                    this.enqueueAndSuccess(offer);
                    return;
                }
                if (overflowStrategy instanceof OverflowStrategies.DropBuffer) {
                    OverflowStrategies.DropBuffer dropBuffer = (OverflowStrategies.DropBuffer)overflowStrategy;
                    this.log().log(dropBuffer.logLevel(), "Dropping all the buffered elements because buffer is full and overflowStrategy is: [DropBuffer] in stream [{}]", this.name$1);
                    this.buffer().clear();
                    this.enqueueAndSuccess(offer);
                    return;
                }
                if (overflowStrategy instanceof OverflowStrategies.DropNew) {
                    OverflowStrategies.DropNew dropNew = (OverflowStrategies.DropNew)overflowStrategy;
                    this.log().log(dropNew.logLevel(), "Dropping the new element because buffer is full and overflowStrategy is: [DropNew] in stream [{}]", this.name$1);
                    offer.promise().success(QueueOfferResult$Dropped$.MODULE$);
                    return;
                }
                if (overflowStrategy instanceof OverflowStrategies.Fail) {
                    OverflowStrategies.Fail fail = (OverflowStrategies.Fail)overflowStrategy;
                    this.log().log(fail.logLevel(), "Failing because buffer is full and overflowStrategy is: [Fail] in stream [{}]", this.name$1);
                    BufferOverflowException bufferOverflowException = new BufferOverflowException(new StringBuilder(37).append("Buffer overflow (max capacity was: ").append(this.$outer.akka$stream$impl$QueueSource$$maxBuffer).append(")!").toString());
                    offer.promise().success(new QueueOfferResult.Failure(bufferOverflowException));
                    this.completion$1.failure(bufferOverflowException);
                    this.failStage(bufferOverflowException);
                    return;
                }
                if (overflowStrategy instanceof OverflowStrategies.Backpressure) {
                    OverflowStrategies.Backpressure backpressure = (OverflowStrategies.Backpressure)overflowStrategy;
                    this.log().log(backpressure.logLevel(), "Backpressuring because buffer is full and overflowStrategy is: [Backpressure] in stream [{}]", this.name$1);
                    if (this.pendingOffers().isFull()) {
                        offer.promise().failure(new IllegalStateException(new StringBuilder(130).append("Too many concurrent offers. Specified maximum is ").append(this.$outer.akka$stream$impl$QueueSource$$maxConcurrentOffers).append(". ").append("You have to wait for one previous future to be resolved to send another request").toString()));
                        return;
                    }
                    this.pendingOffers().enqueue(offer);
                    return;
                }
                throw new MatchError(overflowStrategy);
            }

            private AsyncCallback<Input<T>> callback() {
                return this.callback;
            }

            public void onDownstreamFinish(Throwable cause) {
                while (this.pendingOffers().nonEmpty()) {
                    this.pendingOffers().dequeue().promise().success(QueueOfferResult$QueueClosed$.MODULE$);
                }
                this.completion$1.success(Done$.MODULE$);
                this.completeStage();
            }

            public void onPull() {
                if (this.$outer.akka$stream$impl$QueueSource$$maxBuffer == 0) {
                    if (this.pendingOffers().nonEmpty()) {
                        Offer<T> offer = this.pendingOffers().dequeue();
                        this.push(this.$outer.out(), offer.elem());
                        offer.promise().success(QueueOfferResult$Enqueued$.MODULE$);
                        if (this.terminating()) {
                            this.completion$1.success(Done$.MODULE$);
                            this.completeStage();
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (this.buffer().nonEmpty()) {
                    this.push(this.$outer.out(), this.buffer().dequeue());
                    while (this.pendingOffers().nonEmpty() && !this.buffer().isFull()) {
                        this.enqueueAndSuccess(this.pendingOffers().dequeue());
                    }
                    if (this.terminating() && this.buffer().isEmpty()) {
                        this.completion$1.success(Done$.MODULE$);
                        this.completeStage();
                        return;
                    }
                    return;
                }
            }

            public Future<Done> watchCompletion() {
                return this.completion$1.future();
            }

            public Future<QueueOfferResult> offer(T element) {
                Promise<QueueOfferResult> p = Promise$.MODULE$.apply();
                this.callback().invokeWithFeedback(new Offer<T>(element, p)).onComplete((Function1<Try, Object> & Serializable)x0$1 -> {
                    Try try_ = x0$1;
                    if (try_ instanceof Success) {
                        return BoxedUnit.UNIT;
                    }
                    if (try_ instanceof scala.util.Failure) {
                        scala.util.Failure failure = (scala.util.Failure)try_;
                        Throwable e = failure.exception();
                        return BoxesRunTime.boxToBoolean(p.tryFailure(e));
                    }
                    throw new MatchError(try_);
                }, ExecutionContexts$.MODULE$.parasitic());
                return p.future();
            }

            public void complete() {
                this.callback().invoke(QueueSource$Completion$.MODULE$);
            }

            public void fail(Throwable ex) {
                this.callback().invoke(new Failure(ex));
            }

            public static final /* synthetic */ void $anonfun$callback$1($anon$1 $this, Input x0$1) {
                boolean bl = false;
                Offer offer = null;
                Input input = x0$1;
                if (input instanceof Offer) {
                    bl = true;
                    offer = (Offer)input;
                    Promise<QueueOfferResult> promise = offer.promise();
                    if ($this.terminating()) {
                        promise.success(QueueOfferResult$Dropped$.MODULE$);
                        return;
                    }
                }
                if (bl) {
                    T elem = offer.elem();
                    Promise<QueueOfferResult> promise = offer.promise();
                    if ($this.$outer.akka$stream$impl$QueueSource$$maxBuffer != 0) {
                        $this.bufferElem(offer);
                        if ($this.isAvailable($this.$outer.out())) {
                            $this.push($this.$outer.out(), $this.buffer().dequeue());
                            return;
                        }
                        return;
                    }
                    if ($this.isAvailable($this.$outer.out())) {
                        $this.push($this.$outer.out(), elem);
                        promise.success(QueueOfferResult$Enqueued$.MODULE$);
                        return;
                    }
                    if (!$this.pendingOffers().isFull()) {
                        $this.pendingOffers().enqueue(offer);
                        return;
                    }
                    OverflowStrategy overflowStrategy = $this.$outer.akka$stream$impl$QueueSource$$overflowStrategy;
                    if (overflowStrategy instanceof OverflowStrategies.DropHead ? true : overflowStrategy instanceof OverflowStrategies.DropBuffer) {
                        $this.log().log(overflowStrategy.logLevel(), "Dropping element because buffer is full and overflowStrategy is: [{}] in stream [{}]", overflowStrategy, $this.name$1);
                        $this.pendingOffers().dequeue().promise().success(QueueOfferResult$Dropped$.MODULE$);
                        $this.pendingOffers().enqueue(offer);
                    } else if (overflowStrategy instanceof OverflowStrategies.DropTail ? true : overflowStrategy instanceof OverflowStrategies.DropNew) {
                        $this.log().log(overflowStrategy.logLevel(), "Dropping element because buffer is full and overflowStrategy is: [{}] in stream [{}]", overflowStrategy, $this.name$1);
                        promise.success(QueueOfferResult$Dropped$.MODULE$);
                    } else if (overflowStrategy instanceof OverflowStrategies.Fail) {
                        OverflowStrategies.Fail fail = (OverflowStrategies.Fail)overflowStrategy;
                        $this.log().log(fail.logLevel(), "Failing because buffer is full and overflowStrategy is: [Fail] in stream [{}]", $this.name$1);
                        BufferOverflowException bufferOverflowException = new BufferOverflowException(new StringBuilder(37).append("Buffer overflow (max capacity was: ").append($this.$outer.akka$stream$impl$QueueSource$$maxBuffer).append(")!").toString());
                        promise.success(new QueueOfferResult.Failure(bufferOverflowException));
                        $this.completion$1.failure(bufferOverflowException);
                        $this.failStage(bufferOverflowException);
                    } else if (overflowStrategy instanceof OverflowStrategies.Backpressure) {
                        OverflowStrategies.Backpressure backpressure = (OverflowStrategies.Backpressure)overflowStrategy;
                        $this.log().log(backpressure.logLevel(), "Failing because buffer is full and overflowStrategy is: [Backpressure] in stream [{}]", $this.name$1);
                        promise.failure(new IllegalStateException("You have to wait for previous offer to be resolved to send another request"));
                    } else {
                        throw new MatchError(overflowStrategy);
                    }
                    return;
                }
                if (QueueSource$Completion$.MODULE$.equals(input)) {
                    if ($this.$outer.akka$stream$impl$QueueSource$$maxBuffer != 0 && $this.buffer().nonEmpty() || $this.pendingOffers().nonEmpty()) {
                        $this.terminating_$eq(true);
                        return;
                    }
                    $this.completion$1.success(Done$.MODULE$);
                    $this.completeStage();
                    return;
                }
                if (input instanceof Failure) {
                    Failure failure = (Failure)input;
                    Throwable ex = failure.ex();
                    $this.completion$1.failure(ex);
                    $this.failStage(ex);
                    return;
                }
                throw new MatchError(input);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                this.completion$1 = completion$1;
                this.name$1 = name$1;
                super($outer.shape());
                OutHandler.$init$(this);
                StageLogging.$init$(this);
                this.terminating = false;
                this.callback = this.getAsyncCallback((Function1<Input, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$callback$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$callback$1$adapted(akka.stream.impl.QueueSource$$anon$1 akka.stream.impl.QueueSource$Input ), $anonfun$offer$1(scala.concurrent.Promise scala.util.Try )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, GraphStageLogic>((GraphStageLogic)((Object)stageLogic), (GraphStageLogic)((Object)stageLogic));
    }

    public QueueSource(int maxBuffer, OverflowStrategy overflowStrategy, int maxConcurrentOffers) {
        this.akka$stream$impl$QueueSource$$maxBuffer = maxBuffer;
        this.akka$stream$impl$QueueSource$$overflowStrategy = overflowStrategy;
        this.akka$stream$impl$QueueSource$$maxConcurrentOffers = maxConcurrentOffers;
        Predef$.MODULE$.require(maxConcurrentOffers > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Max concurrent offers must be greater than 0");
        this.out = Outlet$.MODULE$.apply("queueSource.out");
        this.shape = SourceShape$.MODULE$.of(this.out());
    }

    public static final class Failure
    implements Input<Nothing$>,
    Product,
    Serializable {
        private final Throwable ex;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable ex() {
            return this.ex;
        }

        public Failure copy(Throwable ex) {
            return new Failure(ex);
        }

        public Throwable copy$default$1() {
            return this.ex();
        }

        @Override
        public String productPrefix() {
            return "Failure";
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
                    return this.ex();
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
            return x$1 instanceof Failure;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "ex";
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
            if (!(object instanceof Failure)) return false;
            boolean bl = true;
            if (!bl) return false;
            Failure failure = (Failure)x$1;
            Throwable throwable = this.ex();
            Throwable throwable2 = failure.ex();
            if (throwable != null) {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
            if (throwable2 == null) return true;
            return false;
        }

        public Failure(Throwable ex) {
            this.ex = ex;
            Product.$init$(this);
        }
    }

    public static interface Input<T> {
    }

    public static final class Offer<T>
    implements Input<T>,
    Product,
    Serializable {
        private final T elem;
        private final Promise<QueueOfferResult> promise;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public T elem() {
            return this.elem;
        }

        public Promise<QueueOfferResult> promise() {
            return this.promise;
        }

        public <T> Offer<T> copy(T elem, Promise<QueueOfferResult> promise) {
            return new Offer<T>(elem, promise);
        }

        public <T> T copy$default$1() {
            return this.elem();
        }

        public <T> Promise<QueueOfferResult> copy$default$2() {
            return this.promise();
        }

        @Override
        public String productPrefix() {
            return "Offer";
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
                    return this.elem();
                }
                case 1: {
                    return this.promise();
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
            return x$1 instanceof Offer;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "elem";
                }
                case 1: {
                    return "promise";
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
            if (!(object instanceof Offer)) return false;
            boolean bl = true;
            if (!bl) return false;
            Offer offer = (Offer)x$1;
            if (!BoxesRunTime.equals(this.elem(), offer.elem())) return false;
            Promise<QueueOfferResult> promise = this.promise();
            Promise<QueueOfferResult> promise2 = offer.promise();
            if (promise != null) {
                if (!promise.equals(promise2)) return false;
                return true;
            }
            if (promise2 == null) return true;
            return false;
        }

        public Offer(T elem, Promise<QueueOfferResult> promise) {
            this.elem = elem;
            this.promise = promise;
            Product.$init$(this);
        }
    }
}

