/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.LazyZip2;
import scala.collection.Map;
import scala.collection.MapOps;
import scala.collection.Set;
import scala.collection.SetOps;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedMapFactory;
import scala.collection.SortedOps;
import scala.collection.SortedSet;
import scala.collection.SortedSetFactoryDefaults;
import scala.collection.SortedSetOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\r=faB\u0015+!\u0003\r\ta\f\u0005\u0006M\u0002!\ta\u001a\u0005\u0006W\u00021\t\u0001\u001c\u0005\u0006a\u0002!)\"\u001d\u0005\b\u0003C\u0001a\u0011AA\u0012\u0011\u001d\t9\u0003\u0001D\u0001\u0003SAq!a\u000e\u0001\r\u0003\tI\u0004C\u0004\u0002@\u0001!\t!!\u0011\t\u000f\u0005\u001d\u0003\u0001\"\u0001\u0002J!9\u00111\n\u0001\u0005\u0002\u0005%\u0003bBA'\u0001\u0011\u0005\u0011q\n\u0005\b\u00037\u0002A\u0011AA/\u0011\u001d\t\t\u0007\u0001C\u0001\u0003GBq!!\u001b\u0001\t\u0003\nYG\u0002\u0004\u0002t\u0001A\u0011Q\u000f\u0005\b\u0003#sA\u0011AAJ\u0011\u001d\t9J\u0004C\u0001\u00033Cq!!*\u000f\t\u0003\t9KB\u0006\u0002\u0002\u0002\u0001\n1!\u0005\u0002\u0004\u00065\u0005\"\u00024\u0013\t\u00039\u0007B\u0002?\u0013\t\u0007\t)\tC\u0004\u0002(I!\t!!#\t\u000f\u0005M\u0006\u0001\"\u0001\u00026\"9\u0011\u0011\u001e\u0001\u0005\u0002\u0005-\bb\u0002B\u0007\u0001\u0011\u0005!q\u0002\u0005\b\u0005c\u0001A\u0011\tB\u001a\u0011\u001d\u00119\u0005\u0001C#\u0005\u0013BqA!\u0018\u0001\t\u0003\u0012y\u0006C\u0004\u0003^\u0001!\tE!\"\b\u000f\t%&\u0006#\u0001\u0003,\u001a1\u0011F\u000bE\u0001\u0005[Cq!!%\u001f\t\u0003\u0011y\u000b\u0003\u0006\u00032z\u0011\r\u0011\"\u0002+\u0005gC\u0001Ba.\u001fA\u00035!Q\u0017\u0004\u0007\u0005ss\u0002Aa/\t\u0015\tM(E!A!\u0002\u0013\u0011)\u0010\u0003\u0006\u0004>\t\u0012\t\u0011)A\u0005\u0007\u007fAq!!%#\t\u0003\u00199\u0005C\u0004\u00024\n\"\ta!\u001c\t\u000f\u0005%(\u0005\"\u0001\u0004\n\"91q\u0015\u0012\u0005B\r%&\u0001D*peR,G-T1q\u001fB\u001c(BA\u0016-\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002[\u0005)1oY1mC\u000e\u0001Q#\u0002\u0019<\u000bB[5\u0003\u0002\u00012k\r\u0004\"AM\u001a\u000e\u00031J!\u0001\u000e\u0017\u0003\r\u0005s\u0017PU3g!\u00191t'\u000f#H\u00156\t!&\u0003\u00029U\t1Q*\u00199PaN\u0004\"AO\u001e\r\u0001\u0011)A\b\u0001b\u0001{\t\t1*\u0005\u0002?\u0003B\u0011!gP\u0005\u0003\u00012\u0012qAT8uQ&tw\r\u0005\u00023\u0005&\u00111\t\f\u0002\u0004\u0003:L\bC\u0001\u001eF\t\u00191\u0005\u0001\"b\u0001{\t\ta\u000b\u0005\u00027\u0011&\u0011\u0011J\u000b\u0002\u0004\u001b\u0006\u0004\bC\u0001\u001eL\t\u0019a\u0005\u0001\"b\u0001\u001b\n\t1)\u0005\u0002?\u001dB1a\u0007A\u001dE\u001f*\u0003\"A\u000f)\u0005\rE\u0003AQ1\u0001S\u0005\t\u00195)F\u0002T3r\u000b\"A\u0010+\u0013\u0007U;fL\u0002\u0003W\u0001\u0001!&\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004\u0003\u0002\u001cI1n\u0003\"AO-\u0005\u000bi\u0003&\u0019A\u001f\u0003\u0003a\u0003\"A\u000f/\u0005\u000bu\u0003&\u0019A\u001f\u0003\u0003e\u0003$aX1\u0011\rY\u0002\u0001lW(a!\tQ\u0014\rB\u0005c!\u0006\u0005\t\u0011!B\u0001{\t\u0019q\fJ\u0019\u0011\tY\"\u0017HS\u0005\u0003K*\u0012\u0011bU8si\u0016$w\n]:\u0002\r\u0011Jg.\u001b;%)\u0005A\u0007C\u0001\u001aj\u0013\tQGF\u0001\u0003V]&$\u0018\u0001E:peR,G-T1q\r\u0006\u001cGo\u001c:z+\u0005i\u0007c\u0001\u001co\u001f&\u0011qN\u000b\u0002\u0011'>\u0014H/\u001a3NCB4\u0015m\u0019;pef\fQc]8si\u0016$W*\u00199Ge>l\u0017\n^3sC\ndW-F\u0002smf$2a]A\u0005)\t!8\u0010\u0005\u0003;!VD\bC\u0001\u001ew\t\u001598A1\u0001>\u0005\tY%\u0007\u0005\u0002;s\u0012)!p\u0001b\u0001{\t\u0011aK\r\u0005\u0006y\u000e\u0001\u001d!`\u0001\t_J$WM]5oOB!a0a\u0001v\u001d\t\u0011t0C\u0002\u0002\u00021\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002\u0006\u0005\u001d!\u0001C(sI\u0016\u0014\u0018N\\4\u000b\u0007\u0005\u0005A\u0006C\u0004\u0002\f\r\u0001\r!!\u0004\u0002\u0005%$\b#\u0002\u001c\u0002\u0010\u0005M\u0011bAA\tU\tA\u0011\n^3sC\ndW\rE\u00033\u0003+)\b0C\u0002\u0002\u00181\u0012a\u0001V;qY\u0016\u0014\u0004fA\u0002\u0002\u001cA\u0019!'!\b\n\u0007\u0005}AF\u0001\u0004j]2Lg.Z\u0001\tk:\u001cxN\u001d;fIV\u0011\u0011Q\u0005\t\u0005m!KD)\u0001\u0007ji\u0016\u0014\u0018\r^8s\rJ|W\u000e\u0006\u0003\u0002,\u0005M\u0002#\u0002\u001c\u0002.\u0005E\u0012bAA\u0018U\tA\u0011\n^3sCR|'\u000fE\u00033\u0003+ID\t\u0003\u0004\u00026\u0015\u0001\r!O\u0001\u0006gR\f'\u000f^\u0001\u0011W\u0016L8/\u0013;fe\u0006$xN\u001d$s_6$B!a\u000f\u0002>A!a'!\f:\u0011\u0019\t)D\u0002a\u0001s\u0005\u0011b/\u00197vKNLE/\u001a:bi>\u0014hI]8n)\u0011\t\u0019%!\u0012\u0011\tY\ni\u0003\u0012\u0005\u0007\u0003k9\u0001\u0019A\u001d\u0002\u0011\u0019L'o\u001d;LKf,\u0012!O\u0001\bY\u0006\u001cHoS3z\u0003!i\u0017N\\!gi\u0016\u0014H\u0003BA)\u0003/\u0002RAMA*\u0003cI1!!\u0016-\u0005\u0019y\u0005\u000f^5p]\"1\u0011\u0011\f\u0006A\u0002e\n1a[3z\u0003%i\u0017\r\u001f\"fM>\u0014X\r\u0006\u0003\u0002R\u0005}\u0003BBA-\u0017\u0001\u0007\u0011(A\u0004sC:<W\rV8\u0015\u0007)\u000b)\u0007\u0003\u0004\u0002h1\u0001\r!O\u0001\u0003i>\faa[3z'\u0016$XCAA7!\u00111\u0014qN\u001d\n\u0007\u0005E$FA\u0005T_J$X\rZ*fi\na1*Z=T_J$X\rZ*fiNAa\"MA7\u0003o\ny\b\u0005\u0003\u0002z\u0005mT\"\u0001\u0001\n\u0007\u0005utGA\u0005HK:\\U-_*fiB\u0019\u0011\u0011\u0010\n\u0003\u001f\u001d+gnS3z'>\u0014H/\u001a3TKR\u001cBAE\u0019\u0002xU\u0011\u0011q\u0011\t\u0005}\u0006\r\u0011\b\u0006\u0003\u0002<\u0005-\u0005BBA\u001b+\u0001\u0007\u0011H\u0005\u0004\u0002\u0010\u0006}\u0014Q\u000e\u0004\u0006-\u0002\u0001\u0011QR\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0005U\u0005cAA=\u001d\u0005!A-\u001b4g)\u0011\ti'a'\t\u000f\u0005u\u0005\u00031\u0001\u0002 \u0006!A\u000f[1u!\u00111\u0014\u0011U\u001d\n\u0007\u0005\r&FA\u0002TKR\f\u0011B]1oO\u0016LU\u000e\u001d7\u0015\r\u00055\u0014\u0011VAX\u0011\u001d\tY+\u0005a\u0001\u0003[\u000bAA\u001a:p[B!!'a\u0015:\u0011\u001d\t\t,\u0005a\u0001\u0003[\u000bQ!\u001e8uS2\f1!\\1q+\u0019\t9,a0\u0002DR!\u0011\u0011XAo)\u0011\tY,!2\u0011\ri\u0002\u0016QXAa!\rQ\u0014q\u0018\u0003\u0006oZ\u0011\r!\u0010\t\u0004u\u0005\rG!\u0002>\u0017\u0005\u0004i\u0004B\u0002?\u0017\u0001\b\t9\rE\u0003\u007f\u0003\u0007\ti\f\u000b\u0005\u0002F\u0006-\u0017q[Am!\u0011\ti-a5\u000e\u0005\u0005='bAAiY\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005U\u0017q\u001a\u0002\u0011S6\u0004H.[2ji:{GOR8v]\u0012\f1!\\:hC\t\tY.AA\u000f\u001d>\u0004\u0013.\u001c9mS\u000eLG\u000fI(sI\u0016\u0014\u0018N\\4\\Im\\%'`/!M>,h\u000e\u001a\u0011u_\u0002\u0012W/\u001b7eA\u0005\u00043k\u001c:uK\u0012l\u0015\r].%w.\u0013T\u0010\f\u0011%wZ\u0013T0\u0018\u0018!3>,\b%\\1zA]\fg\u000e\u001e\u0011u_\u0002*\boY1ti\u0002\"x\u000eI1!\u001b\u0006\u00048\fJ>L{2\u0002Ce\u001f,~;\u00022\u0017N]:uA\tL\beY1mY&tw\r\t1v]N|'\u000f^3eA:Bq!a8\u0017\u0001\u0004\t\t/A\u0001g!\u001d\u0011\u00141]A\u0019\u0003OL1!!:-\u0005%1UO\\2uS>t\u0017\u0007E\u00043\u0003+\ti,!1\u0002\u000f\u0019d\u0017\r^'baV1\u0011Q^A{\u0003s$B!a<\u0003\u0002Q!\u0011\u0011_A~!\u0019Q\u0004+a=\u0002xB\u0019!(!>\u0005\u000b]<\"\u0019A\u001f\u0011\u0007i\nI\u0010B\u0003{/\t\u0007Q\b\u0003\u0004}/\u0001\u000f\u0011Q \t\u0006}\u0006\r\u00111\u001f\u0015\t\u0003w\fY-a6\u0002Z\"9\u0011q\\\fA\u0002\t\r\u0001c\u0002\u001a\u0002d\u0006E\"Q\u0001\t\u0006m\t\u001d!1B\u0005\u0004\u0005\u0013Q#\u0001D%uKJ\f'\r\\3P]\u000e,\u0007c\u0002\u001a\u0002\u0016\u0005M\u0018q_\u0001\bG>dG.Z2u+\u0019\u0011\tB!\u0007\u0003\u001eQ!!1\u0003B\u0013)\u0011\u0011)Ba\b\u0011\ri\u0002&q\u0003B\u000e!\rQ$\u0011\u0004\u0003\u0006ob\u0011\r!\u0010\t\u0004u\tuA!\u0002>\u0019\u0005\u0004i\u0004B\u0002?\u0019\u0001\b\u0011\t\u0003E\u0003\u007f\u0003\u0007\u00119\u0002\u000b\u0005\u0003 \u0005-\u0017q[Am\u0011\u001d\u00119\u0003\u0007a\u0001\u0005S\t!\u0001\u001d4\u0011\u000fI\u0012Y#!\r\u00030%\u0019!Q\u0006\u0017\u0003\u001fA\u000b'\u000f^5bY\u001a+hn\u0019;j_:\u0004rAMA\u000b\u0005/\u0011Y\"\u0001\u0004d_:\u001c\u0017\r^\u000b\u0005\u0005k\u0011Y\u0004\u0006\u0003\u00038\t}\u0002#\u0002\u001eQs\te\u0002c\u0001\u001e\u0003<\u00111!0\u0007b\u0001\u0005{\t\"\u0001R!\t\u000f\t\u0005\u0013\u00041\u0001\u0003D\u000511/\u001e4gSb\u0004RA\u000eB\u0004\u0005\u000b\u0002bAMA\u000bs\te\u0012A\u0003\u0013qYV\u001cH\u0005\u001d7vgV!!1\nB))\u0011\u0011iEa\u0015\u0011\u000bi\u0002\u0016Ha\u0014\u0011\u0007i\u0012\t\u0006\u0002\u0004{5\t\u0007!Q\b\u0005\b\u0005+R\u0002\u0019\u0001B,\u0003\tA8\u000fE\u00037\u0005\u000f\u0011I\u0006\u0005\u00043\u0003+I$q\n\u0015\u00045\u0005m\u0011!\u0002\u0013qYV\u001cX\u0003\u0002B1\u0005O\"BAa\u0019\u0003lA)!\bU\u001d\u0003fA\u0019!Ha\u001a\u0005\u000f\t%4D1\u0001\u0003>\t\u0011a+\r\u0005\b\u0005[Z\u0002\u0019\u0001B8\u0003\tYg\u000f\u0005\u00043\u0003+I$Q\r\u0015\f7\tM$\u0011\u0010B>\u0005\u007f\u0012\t\tE\u00023\u0005kJ1Aa\u001e-\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\u0011i(\u0001 D_:\u001c\u0018\u000eZ3sAI,\u0017/^5sS:<\u0007%\u00198!S6lW\u000f^1cY\u0016\u0004S*\u00199!_J\u0004c-\u00197mA\t\f7m\u001b\u0011u_\u0002j\u0015\r\u001d\u0018d_:\u001c\u0017\r^\u0001\u0006g&t7-Z\u0011\u0003\u0005\u0007\u000baA\r\u00182g9\u0002T\u0003\u0002BD\u0005\u001b#\u0002B!#\u0003\u0010\nU%\u0011\u0014\t\u0006uAK$1\u0012\t\u0004u\t5Ea\u0002B59\t\u0007!Q\b\u0005\b\u0005#c\u0002\u0019\u0001BJ\u0003\u0015)G.Z72!\u0019\u0011\u0014QC\u001d\u0003\f\"9!q\u0013\u000fA\u0002\tM\u0015!B3mK6\u0014\u0004b\u0002BN9\u0001\u0007!QT\u0001\u0006K2,Wn\u001d\t\u0006e\t}%1S\u0005\u0004\u0005Cc#A\u0003\u001fsKB,\u0017\r^3e}!ZADa\u001d\u0003z\t\u0015&q\u0010BAC\t\u00119+A#Vg\u0016\u00043f\u000b\u0011xSRD\u0007%\u00198!Kb\u0004H.[2ji\u0002\u001aw\u000e\u001c7fGRLwN\u001c\u0011be\u001e,X.\u001a8uA%t7\u000f^3bI\u0002zg\rI\u0016!o&$\b\u000e\t<be\u0006\u0014xm]\u0001\r'>\u0014H/\u001a3NCB|\u0005o\u001d\t\u0003my\u0019\"AH\u0019\u0015\u0005\t-\u0016AB8sI6\u001bx-\u0006\u0002\u00036>\u0011\u0011\u0011\\\u0001\b_J$Wj]4!\u0005)9\u0016\u000e\u001e5GS2$XM]\u000b\r\u0005{\u0013iM!5\u0003V\n\u0005(q`\n\u0004E\t}\u0006\u0003\u0004Ba\u0005\u000f\u0014YMa4\u0003T\n}gb\u0001\u001c\u0003D&\u0019!Q\u0019\u0016\u0002\r5\u000b\u0007o\u00149t\u0013\u0011\u0011IL!3\u000b\u0007\t\u0015'\u0006E\u0002;\u0005\u001b$Q\u0001\u0010\u0012C\u0002u\u00022A\u000fBi\t\u00191%\u0005\"b\u0001{A\u0019!H!6\u0005\u0011\t]'\u0005\"b\u0001\u00053\u0014!\"\u0013;fe\u0006\u0014G.Z\"D+\ri$1\u001c\u0003\b\u0005;\u0014)N1\u0001>\u0005\u0011yF\u0005J\u0019\u0011\u0007i\u0012\t\u000f\u0002\u0005\u0003d\n\")\u0019\u0001Bs\u0005\u0015i\u0015\r]\"D+\u0019\u00119O!<\u0003rF\u0019aH!;\u0011\rYB%1\u001eBx!\rQ$Q\u001e\u0003\u00075\n\u0005(\u0019A\u001f\u0011\u0007i\u0012\t\u0010\u0002\u0004^\u0005C\u0014\r!P\u0001\u0005g\u0016dgM\u0005\u0005\u0003x\ne81EB\u0017\r\u00151f\u0004\u0001B{a\u0011\u0011Ypa\b\u0011\u0015Y\u0002!1\u001aBh\u0005{\u001ci\u0002E\u0002;\u0005\u007f$q!\u0015\u0012\u0005\u0006\u0004\u0019\t!\u0006\u0004\u0004\u0004\r51\u0011C\t\u0004}\r\u0015!CBB\u0004\u0007\u0013\u0019\u0019BB\u0003W=\u0001\u0019)\u0001\u0005\u00047\u0011\u000e-1q\u0002\t\u0004u\r5AA\u0002.\u0003\u0000\n\u0007Q\bE\u0002;\u0007#!a!\u0018B\u0000\u0005\u0004i\u0004\u0007BB\u000b\u00073\u0001\"B\u000e\u0001\u0004\f\r=!Q`B\f!\rQ4\u0011\u0004\u0003\f\u00077\u0011y0!A\u0001\u0002\u000b\u0005QHA\u0002`II\u00022AOB\u0010\t)\u0019\tcIA\u0001\u0002\u0003\u0015\t!\u0010\u0002\u0004?\u0012\u001a\u0004\u0007BB\u0013\u0007S\u0001\"BN\u001c\u0003L\n='q\\B\u0014!\rQ4\u0011\u0006\u0003\u000b\u0007W\u0019\u0013\u0011!A\u0001\u0006\u0003i$aA0%iA\"1qFB\u001d!%14\u0011GB\u001b\u0005'\u001c9$C\u0002\u00044)\u00121\"\u0013;fe\u0006\u0014G.Z(qgB9!'!\u0006\u0003L\n=\u0007c\u0001\u001e\u0004:\u0011Q11H\u0012\u0002\u0002\u0003\u0005)\u0011A\u001f\u0003\u0007}#S'A\u0001q!\u001d\u0011\u00141]B\u001b\u0007\u0003\u00022AMB\"\u0013\r\u0019)\u0005\f\u0002\b\u0005>|G.Z1o)\u0019\u0019Ie!\u0014\u0004lAi11\n\u0012\u0003L\n='1\u001bBp\u0005{l\u0011A\b\u0005\b\u0005g,\u0003\u0019AB(%!\u0019\tfa\u0015\u0004\\\r\rd!\u0002,\u001f\u0001\r=\u0003\u0007BB+\u00073\u0002\"B\u000e\u0001\u0003L\n='Q`B,!\rQ4\u0011\f\u0003\f\u0007C\u0019i%!A\u0001\u0002\u000b\u0005Q\b\r\u0003\u0004^\r\u0005\u0004C\u0003\u001c8\u0005\u0017\u0014yMa8\u0004`A\u0019!h!\u0019\u0005\u0017\r-2QJA\u0001\u0002\u0003\u0015\t!\u0010\u0019\u0005\u0007K\u001aI\u0007E\u00057\u0007c\u0019)Da5\u0004hA\u0019!h!\u001b\u0005\u0017\rm2QJA\u0001\u0002\u0003\u0015\t!\u0010\u0005\b\u0007{)\u0003\u0019AB +\u0019\u0019yga\u001e\u0004|Q!1\u0011OBB)\u0011\u0019\u0019h! \u0011\u000fi\u0012yp!\u001e\u0004zA\u0019!ha\u001e\u0005\u000b]4#\u0019A\u001f\u0011\u0007i\u001aY\bB\u0003{M\t\u0007Q\bC\u0005\u0004\u0000\u0019\n\t\u0011q\u0001\u0004\u0002\u0006QQM^5eK:\u001cW\rJ\u0019\u0011\u000by\f\u0019a!\u001e\t\u000f\u0005}g\u00051\u0001\u0004\u0006B9!'a9\u00046\r\u001d\u0005c\u0002\u001a\u0002\u0016\rU4\u0011P\u000b\u0007\u0007\u0017\u001b\u0019ja&\u0015\t\r55q\u0014\u000b\u0005\u0007\u001f\u001bI\nE\u0004;\u0005\u007f\u001c\tj!&\u0011\u0007i\u001a\u0019\nB\u0003xO\t\u0007Q\bE\u0002;\u0007/#QA_\u0014C\u0002uB\u0011ba'(\u0003\u0003\u0005\u001da!(\u0002\u0015\u00154\u0018\u000eZ3oG\u0016$#\u0007E\u0003\u007f\u0003\u0007\u0019\t\nC\u0004\u0002`\u001e\u0002\ra!)\u0011\u000fI\n\u0019o!\u000e\u0004$B)aGa\u0002\u0004&B9!'!\u0006\u0004\u0012\u000eU\u0015AC<ji\"4\u0015\u000e\u001c;feR!1\u0011JBV\u0011\u001d\u0019i\u000b\u000ba\u0001\u0007\u007f\t\u0011!\u001d")
public interface SortedMapOps<K, V, CC extends Map<Object, Object>, C extends SortedMapOps<K, V, CC, C>>
extends MapOps<K, V, Map, C>,
SortedOps<K, C> {
    public SortedMapFactory<CC> sortedMapFactory();

    public static /* synthetic */ Map sortedMapFromIterable$(SortedMapOps $this, Iterable it, Ordering ordering) {
        return $this.sortedMapFromIterable(it, ordering);
    }

    default public <K2, V2> CC sortedMapFromIterable(Iterable<Tuple2<K2, V2>> it, Ordering<K2> ordering) {
        return (CC)((Map)this.sortedMapFactory().from(it, ordering));
    }

    public Map<K, V> unsorted();

    public Iterator<Tuple2<K, V>> iteratorFrom(K var1);

    public Iterator<K> keysIteratorFrom(K var1);

    public static /* synthetic */ Iterator valuesIteratorFrom$(SortedMapOps $this, Object start2) {
        return $this.valuesIteratorFrom(start2);
    }

    default public Iterator<V> valuesIteratorFrom(K start2) {
        return this.iteratorFrom(start2).map((Function1<Tuple2, Object> & Serializable)(A x$1) -> x$1._2());
    }

    public static /* synthetic */ Object firstKey$(SortedMapOps $this) {
        return $this.firstKey();
    }

    @Override
    default public K firstKey() {
        return (K)((Tuple2)this.head())._1();
    }

    public static /* synthetic */ Object lastKey$(SortedMapOps $this) {
        return $this.lastKey();
    }

    @Override
    default public K lastKey() {
        return (K)((Tuple2)this.last())._1();
    }

    public static /* synthetic */ Option minAfter$(SortedMapOps $this, Object key) {
        return $this.minAfter(key);
    }

    default public Option<Tuple2<K, V>> minAfter(K key) {
        return ((IterableOps)this.rangeFrom(key)).headOption();
    }

    public static /* synthetic */ Option maxBefore$(SortedMapOps $this, Object key) {
        return $this.maxBefore(key);
    }

    default public Option<Tuple2<K, V>> maxBefore(K key) {
        return ((IterableOps)this.rangeUntil(key)).lastOption();
    }

    public static /* synthetic */ SortedMapOps rangeTo$(SortedMapOps $this, Object to) {
        return $this.rangeTo((K)to);
    }

    @Override
    default public C rangeTo(K to) {
        Iterator i = ((IterableOnce)this.keySet().rangeFrom(to)).iterator();
        if (i.isEmpty()) {
            return (C)((SortedMapOps)this.coll());
        }
        Object next = i.next();
        if (this.ordering().compare(next, to) == 0) {
            if (i.isEmpty()) {
                return (C)((SortedMapOps)this.coll());
            }
            return (C)((SortedMapOps)this.rangeUntil(i.next()));
        }
        return (C)((SortedMapOps)this.rangeUntil(next));
    }

    public static /* synthetic */ SortedSet keySet$(SortedMapOps $this) {
        return $this.keySet();
    }

    @Override
    default public SortedSet<K> keySet() {
        return new KeySortedSet();
    }

    public static /* synthetic */ Map map$(SortedMapOps $this, Function1 f, Ordering ordering) {
        return $this.map(f, ordering);
    }

    default public <K2, V2> CC map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f, Ordering<K2> ordering) {
        return (CC)((Map)this.sortedMapFactory().from(new View.Map<Tuple2<K, V>, Tuple2<K2, V2>>(this, f), ordering));
    }

    public static /* synthetic */ Map flatMap$(SortedMapOps $this, Function1 f, Ordering ordering) {
        return $this.flatMap(f, ordering);
    }

    default public <K2, V2> CC flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f, Ordering<K2> ordering) {
        return (CC)((Map)this.sortedMapFactory().from(new View.FlatMap<Tuple2<K, V>, Tuple2<K2, V2>>(this, f), ordering));
    }

    public static /* synthetic */ Map collect$(SortedMapOps $this, PartialFunction pf, Ordering ordering) {
        return $this.collect(pf, ordering);
    }

    default public <K2, V2> CC collect(PartialFunction<Tuple2<K, V>, Tuple2<K2, V2>> pf, Ordering<K2> ordering) {
        return (CC)((Map)this.sortedMapFactory().from(new View.Collect<Tuple2<K, V>, Tuple2<K2, V2>>(this, pf), ordering));
    }

    public static /* synthetic */ Map concat$(SortedMapOps $this, IterableOnce suffix) {
        return $this.concat(suffix);
    }

    @Override
    default public <V2> CC concat(IterableOnce<Tuple2<K, V2>> suffix) {
        IterableOnce<Object> iterableOnce;
        SortedMapFactory<CC> sortedMapFactory = this.sortedMapFactory();
        if (suffix instanceof Iterable) {
            Iterable iterable = (Iterable)suffix;
            iterableOnce = new View.Concat(this, iterable);
        } else {
            iterableOnce = this.iterator().concat((Function0<Iterator> & Serializable)() -> suffix.iterator());
        }
        return (CC)((Map)sortedMapFactory.from((IterableOnce)iterableOnce, this.ordering()));
    }

    public static /* synthetic */ Map $plus$plus$(SortedMapOps $this, IterableOnce xs) {
        return $this.$plus$plus(xs);
    }

    @Override
    default public <V2> CC $plus$plus(IterableOnce<Tuple2<K, V2>> xs) {
        return this.concat(xs);
    }

    public static /* synthetic */ Map $plus$(SortedMapOps $this, Tuple2 kv) {
        return $this.$plus(kv);
    }

    @Override
    default public <V1> CC $plus(Tuple2<K, V1> kv) {
        return (CC)((Map)this.sortedMapFactory().from(new View.Appended(this, kv), this.ordering()));
    }

    public static /* synthetic */ Map $plus$(SortedMapOps $this, Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return $this.$plus(elem1, elem2, elems);
    }

    @Override
    default public <V1> CC $plus(Tuple2<K, V1> elem1, Tuple2<K, V1> elem2, Seq<Tuple2<K, V1>> elems) {
        return (CC)((Map)this.sortedMapFactory().from(new View.Concat(new View.Appended<Tuple2<K, V1>>(new View.Appended<Tuple2<K, V1>>(this, elem1), elem2), elems), this.ordering()));
    }

    public static void $init$(SortedMapOps $this) {
    }

    public interface GenKeySortedSet
    extends MapOps.GenKeySet {
        default public Ordering<K> ordering() {
            return this.scala$collection$SortedMapOps$GenKeySortedSet$$$outer().ordering();
        }

        default public Iterator<K> iteratorFrom(K start2) {
            return this.scala$collection$SortedMapOps$GenKeySortedSet$$$outer().keysIteratorFrom(start2);
        }

        public /* synthetic */ SortedMapOps scala$collection$SortedMapOps$GenKeySortedSet$$$outer();

        public static void $init$(GenKeySortedSet $this) {
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public class KeySortedSet
    implements SortedSet<K>,
    GenKeySortedSet {
        @Override
        public Ordering<K> ordering() {
            return ((GenKeySortedSet)this).ordering();
        }

        @Override
        public Iterator<K> iteratorFrom(K start2) {
            return ((GenKeySortedSet)this).iteratorFrom(start2);
        }

        @Override
        public Iterator<Object> iterator() {
            return MapOps.GenKeySet.iterator$(this);
        }

        @Override
        public boolean contains(Object key) {
            return MapOps.GenKeySet.contains$(this, key);
        }

        @Override
        public int size() {
            return MapOps.GenKeySet.size$(this);
        }

        @Override
        public int knownSize() {
            return MapOps.GenKeySet.knownSize$(this);
        }

        @Override
        public boolean isEmpty() {
            return MapOps.GenKeySet.isEmpty$(this);
        }

        @Override
        public /* synthetic */ boolean scala$collection$SortedSet$$super$equals(Object that) {
            return Set.equals$(this, that);
        }

        @Override
        public Set<K> unsorted() {
            return SortedSet.unsorted$(this);
        }

        @Override
        public SortedIterableFactory<SortedSet> sortedIterableFactory() {
            return SortedSet.sortedIterableFactory$(this);
        }

        @Override
        public String stringPrefix() {
            return SortedSet.stringPrefix$(this);
        }

        @Override
        public boolean equals(Object that) {
            return SortedSet.equals$(this, that);
        }

        @Override
        public SortedSet<K> fromSpecific(IterableOnce<K> coll) {
            return SortedSetFactoryDefaults.fromSpecific$(this, coll);
        }

        @Override
        public Builder<K, SortedSet<K>> newSpecificBuilder() {
            return SortedSetFactoryDefaults.newSpecificBuilder$(this);
        }

        @Override
        public SortedSet<K> empty() {
            return SortedSetFactoryDefaults.empty$(this);
        }

        @Override
        public SortedSetOps.WithFilter<K, Set, SortedSet> withFilter(Function1<K, Object> p) {
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
        public Iterator<K> keysIteratorFrom(K start2) {
            return SortedSetOps.keysIteratorFrom$(this, start2);
        }

        @Override
        public K firstKey() {
            return SortedSetOps.firstKey$(this);
        }

        @Override
        public K lastKey() {
            return SortedSetOps.lastKey$(this);
        }

        @Override
        public Option<K> minAfter(K key) {
            return SortedSetOps.minAfter$(this, key);
        }

        @Override
        public Option<K> maxBefore(K key) {
            return SortedSetOps.maxBefore$(this, key);
        }

        @Override
        public <B> K min(Ordering<B> ord) {
            return SortedSetOps.min$(this, ord);
        }

        @Override
        public <B> K max(Ordering<B> ord) {
            return SortedSetOps.max$(this, ord);
        }

        @Override
        public SortedSetOps rangeTo(Object to) {
            return SortedSetOps.rangeTo$(this, to);
        }

        @Override
        public <B> SortedSet<B> map(Function1<K, B> f, Ordering<B> ev) {
            return SortedSetOps.map$(this, f, ev);
        }

        @Override
        public <B> SortedSet<B> flatMap(Function1<K, IterableOnce<B>> f, Ordering<B> ev) {
            return SortedSetOps.flatMap$(this, f, ev);
        }

        @Override
        public <B> SortedSet<Tuple2<K, B>> zip(IterableOnce<B> that, Ordering<Tuple2<K, B>> ev) {
            return SortedSetOps.zip$(this, that, ev);
        }

        @Override
        public <B> SortedSet<B> collect(PartialFunction<K, B> pf, Ordering<B> ev) {
            return SortedSetOps.collect$(this, pf, ev);
        }

        @Override
        public int compare(K k0, K k1) {
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
        public boolean canEqual(Object that) {
            return Set.canEqual$(this, that);
        }

        @Override
        public int hashCode() {
            return Set.hashCode$(this);
        }

        @Override
        public IterableFactory<Set> iterableFactory() {
            return Set.iterableFactory$(this);
        }

        @Override
        public String toString() {
            return Set.toString$(this);
        }

        @Override
        public final boolean apply(K elem) {
            return SetOps.apply$(this, elem);
        }

        @Override
        public boolean subsetOf(Set<K> that) {
            return SetOps.subsetOf$(this, that);
        }

        @Override
        public Iterator<SortedSet<K>> subsets(int len) {
            return SetOps.subsets$(this, len);
        }

        @Override
        public Iterator<SortedSet<K>> subsets() {
            return SetOps.subsets$(this);
        }

        @Override
        public SetOps intersect(Set that) {
            return SetOps.intersect$(this, that);
        }

        @Override
        public final SetOps $amp(Set that) {
            return SetOps.$amp$(this, that);
        }

        @Override
        public final SetOps $amp$tilde(Set that) {
            return SetOps.$amp$tilde$(this, that);
        }

        @Override
        public SetOps $minus$minus(IterableOnce that) {
            return SetOps.$minus$minus$(this, that);
        }

        @Override
        public SetOps $minus(Object elem) {
            return SetOps.$minus$(this, elem);
        }

        @Override
        public SetOps $minus(Object elem1, Object elem2, Seq elems) {
            return SetOps.$minus$(this, elem1, elem2, elems);
        }

        @Override
        public SetOps concat(IterableOnce that) {
            return SetOps.concat$(this, that);
        }

        @Override
        public SetOps $plus(Object elem) {
            return SetOps.$plus$(this, elem);
        }

        @Override
        public SetOps $plus(Object elem1, Object elem2, Seq elems) {
            return SetOps.$plus$(this, elem1, elem2, elems);
        }

        @Override
        public final SetOps $plus$plus(IterableOnce that) {
            return SetOps.$plus$plus$(this, that);
        }

        @Override
        public final SetOps union(Set that) {
            return SetOps.union$(this, that);
        }

        @Override
        public final SetOps $bar(Set that) {
            return SetOps.$bar$(this, that);
        }

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
        public <A$> Function1<A$, Object> compose(Function1<A$, K> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public <A$> Function1<K, A$> andThen(Function1<Object, A$> g2) {
            return Function1.andThen$(this, g2);
        }

        @Override
        public final Iterable<K> toIterable() {
            return Iterable.toIterable$(this);
        }

        @Override
        public final Iterable<K> coll() {
            return Iterable.coll$(this);
        }

        @Override
        public Iterable<K> seq() {
            return Iterable.seq$(this);
        }

        @Override
        public String className() {
            return Iterable.className$(this);
        }

        @Override
        public final String collectionClassName() {
            return Iterable.collectionClassName$(this);
        }

        @Override
        public <B> LazyZip2<K, B, Iterable> lazyZip(Iterable<B> that) {
            return Iterable.lazyZip$(this, that);
        }

        @Override
        public final Iterable<K> toTraversable() {
            return IterableOps.toTraversable$(this);
        }

        @Override
        public boolean isTraversableAgain() {
            return IterableOps.isTraversableAgain$(this);
        }

        @Override
        public final Object repr() {
            return IterableOps.repr$(this);
        }

        @Override
        public IterableFactory<?> companion() {
            return IterableOps.companion$(this);
        }

        @Override
        public K head() {
            return IterableOps.head$(this);
        }

        @Override
        public Option<K> headOption() {
            return IterableOps.headOption$(this);
        }

        @Override
        public K last() {
            return IterableOps.last$(this);
        }

        @Override
        public Option<K> lastOption() {
            return IterableOps.lastOption$(this);
        }

        @Override
        public View<K> view() {
            return IterableOps.view$(this);
        }

        @Override
        public int sizeCompare(int otherSize) {
            return IterableOps.sizeCompare$((IterableOps)this, otherSize);
        }

        @Override
        public final IterableOps sizeIs() {
            return IterableOps.sizeIs$(this);
        }

        @Override
        public int sizeCompare(Iterable<?> that) {
            return IterableOps.sizeCompare$((IterableOps)this, that);
        }

        @Override
        public View<K> view(int from, int until) {
            return IterableOps.view$(this, from, until);
        }

        @Override
        public Object transpose(Function1 asIterable) {
            return IterableOps.transpose$(this, asIterable);
        }

        @Override
        public Object filter(Function1 pred) {
            return IterableOps.filter$(this, pred);
        }

        @Override
        public Object filterNot(Function1 pred) {
            return IterableOps.filterNot$(this, pred);
        }

        @Override
        public Tuple2<SortedSet<K>, SortedSet<K>> partition(Function1<K, Object> p) {
            return IterableOps.partition$(this, p);
        }

        @Override
        public Tuple2<SortedSet<K>, SortedSet<K>> splitAt(int n) {
            return IterableOps.splitAt$(this, n);
        }

        @Override
        public Object take(int n) {
            return IterableOps.take$(this, n);
        }

        @Override
        public Object takeRight(int n) {
            return IterableOps.takeRight$(this, n);
        }

        @Override
        public Object takeWhile(Function1 p) {
            return IterableOps.takeWhile$(this, p);
        }

        @Override
        public Tuple2<SortedSet<K>, SortedSet<K>> span(Function1<K, Object> p) {
            return IterableOps.span$(this, p);
        }

        @Override
        public Object drop(int n) {
            return IterableOps.drop$(this, n);
        }

        @Override
        public Object dropRight(int n) {
            return IterableOps.dropRight$(this, n);
        }

        @Override
        public Object dropWhile(Function1 p) {
            return IterableOps.dropWhile$(this, p);
        }

        @Override
        public Iterator<SortedSet<K>> grouped(int size) {
            return IterableOps.grouped$(this, size);
        }

        @Override
        public Iterator<SortedSet<K>> sliding(int size) {
            return IterableOps.sliding$(this, size);
        }

        @Override
        public Iterator<SortedSet<K>> sliding(int size, int step) {
            return IterableOps.sliding$(this, size, step);
        }

        @Override
        public Object tail() {
            return IterableOps.tail$(this);
        }

        @Override
        public Object init() {
            return IterableOps.init$(this);
        }

        @Override
        public Object slice(int from, int until) {
            return IterableOps.slice$(this, from, until);
        }

        @Override
        public <K$> scala.collection.immutable.Map<K$, SortedSet<K>> groupBy(Function1<K, K$> f) {
            return IterableOps.groupBy$(this, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, Set<B>> groupMap(Function1<K, K$> key, Function1<K, B> f) {
            return IterableOps.groupMap$(this, key, f);
        }

        @Override
        public <K$, B> scala.collection.immutable.Map<K$, B> groupMapReduce(Function1<K, K$> key, Function1<K, B> f, Function2<B, B, B> reduce) {
            return IterableOps.groupMapReduce$(this, key, f, reduce);
        }

        @Override
        public Object scan(Object z, Function2 op) {
            return IterableOps.scan$(this, z, op);
        }

        @Override
        public Object scanLeft(Object z, Function2 op) {
            return IterableOps.scanLeft$(this, z, op);
        }

        @Override
        public Object scanRight(Object z, Function2 op) {
            return IterableOps.scanRight$(this, z, op);
        }

        @Override
        public Object map(Function1 f) {
            return IterableOps.map$(this, f);
        }

        @Override
        public Object flatMap(Function1 f) {
            return IterableOps.flatMap$(this, f);
        }

        @Override
        public Object flatten(Function1 asIterable) {
            return IterableOps.flatten$(this, asIterable);
        }

        @Override
        public Object collect(PartialFunction pf) {
            return IterableOps.collect$(this, pf);
        }

        @Override
        public <A1, A2> Tuple2<Set<A1>, Set<A2>> partitionMap(Function1<K, Either<A1, A2>> f) {
            return IterableOps.partitionMap$(this, f);
        }

        @Override
        public Object concat(IterableOnce suffix) {
            return IterableOps.concat$(this, suffix);
        }

        @Override
        public final Object $plus$plus(IterableOnce suffix) {
            return IterableOps.$plus$plus$(this, suffix);
        }

        @Override
        public Object zip(IterableOnce that) {
            return IterableOps.zip$(this, that);
        }

        @Override
        public Object zipWithIndex() {
            return IterableOps.zipWithIndex$(this);
        }

        @Override
        public Object zipAll(Iterable that, Object thisElem, Object thatElem) {
            return IterableOps.zipAll$(this, that, thisElem, thatElem);
        }

        @Override
        public <A1, A2> Tuple2<Set<A1>, Set<A2>> unzip(Function1<K, Tuple2<A1, A2>> asPair) {
            return IterableOps.unzip$(this, asPair);
        }

        @Override
        public <A1, A2, A3> Tuple3<Set<A1>, Set<A2>, Set<A3>> unzip3(Function1<K, Tuple3<A1, A2, A3>> asTriple) {
            return IterableOps.unzip3$(this, asTriple);
        }

        @Override
        public Iterator<SortedSet<K>> tails() {
            return IterableOps.tails$(this);
        }

        @Override
        public Iterator<SortedSet<K>> inits() {
            return IterableOps.inits$(this);
        }

        @Override
        public Object tapEach(Function1 f) {
            return IterableOps.tapEach$(this, f);
        }

        @Override
        public Object $plus$plus$colon(IterableOnce that) {
            return IterableOps.$plus$plus$colon$(this, that);
        }

        @Override
        public boolean hasDefiniteSize() {
            return IterableOnceOps.hasDefiniteSize$(this);
        }

        @Override
        public <U> void foreach(Function1<K, U> f) {
            IterableOnceOps.foreach$(this, f);
        }

        @Override
        public boolean forall(Function1<K, Object> p) {
            return IterableOnceOps.forall$(this, p);
        }

        @Override
        public boolean exists(Function1<K, Object> p) {
            return IterableOnceOps.exists$(this, p);
        }

        @Override
        public int count(Function1<K, Object> p) {
            return IterableOnceOps.count$(this, p);
        }

        @Override
        public Option<K> find(Function1<K, Object> p) {
            return IterableOnceOps.find$(this, p);
        }

        @Override
        public <B> B foldLeft(B z, Function2<B, K, B> op) {
            return (B)IterableOnceOps.foldLeft$(this, z, op);
        }

        @Override
        public <B> B foldRight(B z, Function2<K, B, B> op) {
            return (B)IterableOnceOps.foldRight$(this, z, op);
        }

        @Override
        public final <B> B $div$colon(B z, Function2<B, K, B> op) {
            return (B)IterableOnceOps.$div$colon$(this, z, op);
        }

        @Override
        public final <B> B $colon$bslash(B z, Function2<K, B, B> op) {
            return (B)IterableOnceOps.$colon$bslash$(this, z, op);
        }

        @Override
        public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
            return (A1)IterableOnceOps.fold$(this, z, op);
        }

        @Override
        public <B> B reduce(Function2<B, B, B> op) {
            return (B)IterableOnceOps.reduce$(this, op);
        }

        @Override
        public <B> Option<B> reduceOption(Function2<B, B, B> op) {
            return IterableOnceOps.reduceOption$(this, op);
        }

        @Override
        public <B> B reduceLeft(Function2<B, K, B> op) {
            return (B)IterableOnceOps.reduceLeft$(this, op);
        }

        @Override
        public <B> B reduceRight(Function2<K, B, B> op) {
            return (B)IterableOnceOps.reduceRight$(this, op);
        }

        @Override
        public <B> Option<B> reduceLeftOption(Function2<B, K, B> op) {
            return IterableOnceOps.reduceLeftOption$(this, op);
        }

        @Override
        public <B> Option<B> reduceRightOption(Function2<K, B, B> op) {
            return IterableOnceOps.reduceRightOption$(this, op);
        }

        @Override
        public boolean nonEmpty() {
            return IterableOnceOps.nonEmpty$(this);
        }

        @Override
        public final <B> void copyToBuffer(Buffer<B> dest) {
            IterableOnceOps.copyToBuffer$(this, dest);
        }

        @Override
        public <B> int copyToArray(Object xs) {
            return IterableOnceOps.copyToArray$(this, xs);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2) {
            return IterableOnceOps.copyToArray$(this, xs, start2);
        }

        @Override
        public <B> int copyToArray(Object xs, int start2, int len) {
            return IterableOnceOps.copyToArray$(this, xs, start2, len);
        }

        @Override
        public <B> B sum(Numeric<B> num) {
            return (B)IterableOnceOps.sum$(this, num);
        }

        @Override
        public <B> B product(Numeric<B> num) {
            return (B)IterableOnceOps.product$(this, num);
        }

        @Override
        public <B> Option<K> minOption(Ordering<B> ord) {
            return IterableOnceOps.minOption$(this, ord);
        }

        @Override
        public <B> Option<K> maxOption(Ordering<B> ord) {
            return IterableOnceOps.maxOption$(this, ord);
        }

        @Override
        public <B> K maxBy(Function1<K, B> f, Ordering<B> cmp) {
            return IterableOnceOps.maxBy$(this, f, cmp);
        }

        @Override
        public <B> Option<K> maxByOption(Function1<K, B> f, Ordering<B> cmp) {
            return IterableOnceOps.maxByOption$(this, f, cmp);
        }

        @Override
        public <B> K minBy(Function1<K, B> f, Ordering<B> cmp) {
            return IterableOnceOps.minBy$(this, f, cmp);
        }

        @Override
        public <B> Option<K> minByOption(Function1<K, B> f, Ordering<B> cmp) {
            return IterableOnceOps.minByOption$(this, f, cmp);
        }

        @Override
        public <B> Option<B> collectFirst(PartialFunction<K, B> pf) {
            return IterableOnceOps.collectFirst$(this, pf);
        }

        @Override
        public <B> B aggregate(Function0<B> z, Function2<B, K, B> seqop, Function2<B, B, B> combop) {
            return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
        }

        @Override
        public <B> boolean corresponds(IterableOnce<B> that, Function2<K, B, Object> p) {
            return IterableOnceOps.corresponds$(this, that, p);
        }

        @Override
        public final String mkString(String start2, String sep, String end) {
            return IterableOnceOps.mkString$(this, start2, sep, end);
        }

        @Override
        public final String mkString(String sep) {
            return IterableOnceOps.mkString$(this, sep);
        }

        @Override
        public final String mkString() {
            return IterableOnceOps.mkString$(this);
        }

        @Override
        public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
            return IterableOnceOps.addString$(this, b, start2, sep, end);
        }

        @Override
        public final StringBuilder addString(StringBuilder b, String sep) {
            return IterableOnceOps.addString$(this, b, sep);
        }

        @Override
        public final StringBuilder addString(StringBuilder b) {
            return IterableOnceOps.addString$(this, b);
        }

        @Override
        public <C1> C1 to(Factory<K, C1> factory2) {
            return (C1)IterableOnceOps.to$(this, factory2);
        }

        @Override
        public final Iterator<K> toIterator() {
            return IterableOnceOps.toIterator$(this);
        }

        @Override
        public List<K> toList() {
            return IterableOnceOps.toList$(this);
        }

        @Override
        public Vector<K> toVector() {
            return IterableOnceOps.toVector$(this);
        }

        @Override
        public <K$, V$> scala.collection.immutable.Map<K$, V$> toMap($less$colon$less<K, Tuple2<K$, V$>> ev) {
            return IterableOnceOps.toMap$(this, ev);
        }

        @Override
        public <B> scala.collection.immutable.Set<B> toSet() {
            return IterableOnceOps.toSet$(this);
        }

        @Override
        public Seq<K> toSeq() {
            return IterableOnceOps.toSeq$(this);
        }

        @Override
        public IndexedSeq<K> toIndexedSeq() {
            return IterableOnceOps.toIndexedSeq$(this);
        }

        @Override
        public final Stream<K> toStream() {
            return IterableOnceOps.toStream$(this);
        }

        @Override
        public final <B> Buffer<B> toBuffer() {
            return IterableOnceOps.toBuffer$(this);
        }

        @Override
        public <B> Object toArray(ClassTag<B> evidence$2) {
            return IterableOnceOps.toArray$(this, evidence$2);
        }

        @Override
        public Iterable<K> reversed() {
            return IterableOnceOps.reversed$(this);
        }

        @Override
        public <S extends Stepper<?>> S stepper(StepperShape<K, S> shape) {
            return (S)IterableOnce.stepper$(this, shape);
        }

        @Override
        public SortedSet<K> diff(Set<K> that) {
            return this.fromSpecific((IterableOnce)this.view().filterNot(that));
        }

        @Override
        public SortedSet<K> rangeImpl(Option<K> from, Option<K> until) {
            SortedMapOps map = (SortedMapOps)this.scala$collection$SortedMapOps$KeySortedSet$$$outer().rangeImpl(from, until);
            return map.new KeySortedSet();
        }

        public /* synthetic */ SortedMapOps scala$collection$SortedMapOps$KeySortedSet$$$outer() {
            return SortedMapOps.this;
        }

        @Override
        public /* synthetic */ SortedMapOps scala$collection$SortedMapOps$GenKeySortedSet$$$outer() {
            return this.scala$collection$SortedMapOps$KeySortedSet$$$outer();
        }

        @Override
        public /* synthetic */ MapOps scala$collection$MapOps$GenKeySet$$$outer() {
            return this.scala$collection$SortedMapOps$KeySortedSet$$$outer();
        }

        public KeySortedSet() {
            if (SortedMapOps.this == null) {
                throw null;
            }
        }
    }

    public static class WithFilter<K, V, IterableCC, MapCC extends Map<Object, Object>, CC extends Map<Object, Object>>
    extends MapOps.WithFilter<K, V, IterableCC, MapCC> {
        private final SortedMapOps<K, V, CC, ?> self;
        private final Function1<Tuple2<K, V>, Object> p;

        public <K2, V2> CC map(Function1<Tuple2<K, V>, Tuple2<K2, V2>> f, Ordering<K2> evidence$1) {
            return (CC)((Map)this.self.sortedMapFactory().from(new View.Map<Tuple2<K, V>, Tuple2<K2, V2>>(this.filtered(), f), evidence$1));
        }

        public <K2, V2> CC flatMap(Function1<Tuple2<K, V>, IterableOnce<Tuple2<K2, V2>>> f, Ordering<K2> evidence$2) {
            return (CC)((Map)this.self.sortedMapFactory().from(new View.FlatMap<Tuple2<K, V>, Tuple2<K2, V2>>(this.filtered(), f), evidence$2));
        }

        public WithFilter<K, V, IterableCC, MapCC, CC> withFilter(Function1<Tuple2<K, V>, Object> q) {
            return new WithFilter<K, V, IterableCC, MapCC, CC>(this.self, (Function1<Tuple2, Object> & Serializable)kv -> BoxesRunTime.boxToBoolean(WithFilter.$anonfun$withFilter$1(this, q, kv)));
        }

        public static final /* synthetic */ boolean $anonfun$withFilter$1(WithFilter $this, Function1 q$1, Tuple2 kv) {
            return BoxesRunTime.unboxToBoolean($this.p.apply(kv)) && BoxesRunTime.unboxToBoolean(q$1.apply(kv));
        }

        public WithFilter(SortedMapOps<K, V, CC, ?> self, Function1<Tuple2<K, V>, Object> p) {
            this.self = self;
            this.p = p;
            super(self, p);
        }
    }
}

