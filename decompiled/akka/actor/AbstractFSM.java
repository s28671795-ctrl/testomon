/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActor;
import akka.actor.AbstractFSM$;
import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.FSM;
import akka.actor.FSM$$minus$greater$;
import akka.actor.FSM$Event$;
import akka.actor.FSM$Normal$;
import akka.actor.FSM$Shutdown$;
import akka.actor.FSM$StateTimeout$;
import akka.actor.FSM$StopEvent$;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.compat.PartialFunction$;
import akka.event.LoggingAdapter;
import akka.japi.pf.FI;
import akka.japi.pf.FSMStateFunctionBuilder;
import akka.japi.pf.FSMStopBuilder;
import akka.japi.pf.FSMTransitionHandlerBuilder;
import akka.japi.pf.UnitMatch;
import akka.japi.pf.UnitPFBuilder;
import akka.routing.Listeners;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import java.time.Duration;
import java.util.Set;
import scala.Function2;
import scala.Option;
import scala.Option$;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.mutable.Map;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\r\u0015v!\u0002\u00192\u0011\u00031d!\u0002\u001d2\u0011\u0003I\u0004\"\u0002!\u0002\t\u0003\t\u0005\"\u0002\"\u0002\t\u0003\u0019e!\u0002\u001d2\u0003\u00031\u0006\"\u0002!\u0005\t\u0003y\u0006\"B1\u0005\t\u0003\u0011\u0007\"\u00026\u0005\t\u0003Y\u0007\"B8\u0005\t\u0003Y\u0007\"\u00029\u0005\t\u000b\t\b\"\u00029\u0005\t\u000bq\bB\u00029\u0005\t\u000b\t)\u0002\u0003\u0004q\t\u0011\u0015\u0011q\u0006\u0005\b\u0003\u000f\"AQAA%\u0011\u001d\t9\u0005\u0002C\u0003\u0003#Bq!a\u0012\u0005\t\u000b\tY\u0006C\u0004\u0002d\u0011!)!!\u001a\t\u000f\u0005\rD\u0001\"\u0002\u0002r!9\u0011q\u0013\u0003\u0005\u0006\u0005e\u0005bBAO\t\u0011\u0015\u0011q\u0014\u0005\b\u0003W#AQAAW\u0011\u001d\tY\u000b\u0002C\u0003\u0003gDq!a+\u0005\t\u000b\u0011Y\u0001C\u0004\u0002,\u0012!)Aa\b\t\u000f\u0005-F\u0001\"\u0002\u00030!9\u00111\u0016\u0003\u0005\u0006\te\u0002bBAV\t\u0011\u0015!\u0011\f\u0005\b\u0005?\"AQ\u0001B1\u0011\u001d\u0011y\u0006\u0002C\u0003\u0005wBqA!#\u0005\t\u000b\u0011Y\tC\u0004\u0003\u0010\u0012!)A!%\t\u000f\t=E\u0001\"\u0002\u0003$\"9!1\u0016\u0003\u0005\u0006\t5\u0006b\u0002BV\t\u0011\u0015!Q\u0019\u0005\b\u0005W#AQ\u0001Bp\u0011\u001d\u00119\u0010\u0002C\u0003\u0005sDqAa>\u0005\t\u000b\u0019\u0019\u0002C\u0004\u0004(\u0011!)a!\u000b\t\u000f\r=B\u0001\"\u0001\u00042!91Q\t\u0003\u0005\u0002\r\u001d\u0003bBB)\t\u0011\u000511\u000b\u0005\b\u00077\"AQAB/\u0011\u001d\u0019Y\u0006\u0002C\u0003\u0007sBqaa\u0017\u0005\t\u000b\u0019\u0019\tC\u0005\u0004\u001c\u0012\u0011\r\u0011\"\u0001\u0004\u001e\"A1q\u0014\u0003!\u0002\u0013\u0011\u0019\fC\u0005\u0004\"\u0012\u0011\r\u0011\"\u0001\u0004\u001e\"A11\u0015\u0003!\u0002\u0013\u0011\u0019,A\u0006BEN$(/Y2u\rNk%B\u0001\u001a4\u0003\u0015\t7\r^8s\u0015\u0005!\u0014\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u00028\u00035\t\u0011GA\u0006BEN$(/Y2u\rNk5CA\u0001;!\tYd(D\u0001=\u0015\u0005i\u0014!B:dC2\f\u0017BA =\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AN\u0001\r\u001dVdGNR;oGRLwN\\\u000b\u0004\t*#V#A#\u0011\tm2\u0005jU\u0005\u0003\u000fr\u0012q\u0002U1si&\fGNR;oGRLwN\u001c\t\u0003\u0013*c\u0001\u0001B\u0003L\u0007\t\u0007AJA\u0001T#\ti\u0005\u000b\u0005\u0002<\u001d&\u0011q\n\u0010\u0002\b\u001d>$\b.\u001b8h!\tY\u0014+\u0003\u0002Sy\t\u0019\u0011I\\=\u0011\u0005%#F!B+\u0004\u0005\u0004a%!\u0001#\u0016\u0007]cflE\u0002\u0005ua\u0003BaN-\\;&\u0011!,\r\u0002\u0004\rNk\u0005CA%]\t\u0015YEA1\u0001M!\tIe\fB\u0003V\t\t\u0007A\nF\u0001a!\u00119DaW/\u0002\u0015\u001d,GoQ8oi\u0016DH\u000fF\u0001d!\t!wM\u0004\u00028K&\u0011a-M\u0001\u000e\u0003\n\u001cHO]1di\u0006\u001bGo\u001c:\n\u0005!L'\u0001D!di>\u00148i\u001c8uKb$(B\u000142\u0003\u001d9W\r^*fY\u001a$\u0012\u0001\u001c\t\u0003o5L!A\\\u0019\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f\u0011bZ3u'\u0016tG-\u001a:\u0002\t]DWM\u001c\u000b\u0003er$\"a\u001d<\u0011\u0005m\"\u0018BA;=\u0005\u0011)f.\u001b;\t\u000b]L\u0001\u0019\u0001=\u0002\u001bM$\u0018\r^3Gk:\u001cG/[8o!\tI(0D\u0001\u0005\u0013\tY\u0018LA\u0007Ti\u0006$XMR;oGRLwN\u001c\u0005\u0006{&\u0001\raW\u0001\ngR\fG/\u001a(b[\u0016$Ba]@\u0002\u0002!)QP\u0003a\u00017\"9\u00111\u0001\u0006A\u0002\u0005\u0015\u0011\u0001F:uCR,g)\u001e8di&|gNQ;jY\u0012,'\u000f\u0005\u0004\u0002\b\u0005E1,X\u0007\u0003\u0003\u0013QA!a\u0003\u0002\u000e\u0005\u0011\u0001O\u001a\u0006\u0004\u0003\u001f\u0019\u0014\u0001\u00026ba&LA!a\u0005\u0002\n\t9biU'Ti\u0006$XMR;oGRLwN\u001c\"vS2$WM\u001d\u000b\bg\u0006]\u0011\u0011DA\u0017\u0011\u0015i8\u00021\u0001\\\u0011\u001d\tYb\u0003a\u0001\u0003;\tAb\u001d;bi\u0016$\u0016.\\3pkR\u0004B!a\b\u0002*5\u0011\u0011\u0011\u0005\u0006\u0005\u0003G\t)#\u0001\u0005ekJ\fG/[8o\u0015\r\t9\u0003P\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002BA\u0016\u0003C\u0011aBR5oSR,G)\u001e:bi&|g\u000eC\u0004\u0002\u0004-\u0001\r!!\u0002\u0015\u000fM\f\t$a\r\u0002F!)Q\u0010\u0004a\u00017\"9\u00111\u0004\u0007A\u0002\u0005U\u0002\u0003BA\u001c\u0003\u0003j!!!\u000f\u000b\t\u0005m\u0012QH\u0001\u0005i&lWM\u0003\u0002\u0002@\u0005!!.\u0019<b\u0013\u0011\t\u0019%!\u000f\u0003\u0011\u0011+(/\u0019;j_:Dq!a\u0001\r\u0001\u0004\t)!A\u0005ti\u0006\u0014HoV5uQR)1/a\u0013\u0002N!)Q0\u0004a\u00017\"1\u0011qJ\u0007A\u0002u\u000b\u0011b\u001d;bi\u0016$\u0015\r^1\u0015\u000fM\f\u0019&!\u0016\u0002X!)QP\u0004a\u00017\"1\u0011q\n\bA\u0002uCq!!\u0017\u000f\u0001\u0004\ti\"A\u0004uS6,w.\u001e;\u0015\u000fM\fi&a\u0018\u0002b!)Qp\u0004a\u00017\"1\u0011qJ\bA\u0002uCq!!\u0017\u0010\u0001\u0004\t)$\u0001\u0007p]R\u0013\u0018M\\:ji&|g\u000eF\u0002t\u0003OBq!!\u001b\u0011\u0001\u0004\tY'\u0001\rue\u0006t7/\u001b;j_:D\u0015M\u001c3mKJ\u0014U/\u001b7eKJ\u0004R!a\u0002\u0002nmKA!a\u001c\u0002\n\tYbiU'Ue\u0006t7/\u001b;j_:D\u0015M\u001c3mKJ\u0014U/\u001b7eKJ$2a]A:\u0011\u001d\t)(\u0005a\u0001\u0003o\n\u0011\u0003\u001e:b]NLG/[8o\u0011\u0006tG\r\\3s!\u0019\tI(!%\\7:!\u00111PAG\u001d\u0011\ti(a#\u000f\t\u0005}\u0014\u0011\u0012\b\u0005\u0003\u0003\u000b9)\u0004\u0002\u0002\u0004*\u0019\u0011QQ\u001b\u0002\rq\u0012xn\u001c;?\u0013\u0005!\u0014bAA\bg%!\u00111BA\u0007\u0013\u0011\ty)!\u0003\u0002\u0005\u0019K\u0015\u0002BAJ\u0003+\u0013!\"\u00168ji\u0006\u0003\b\u000f\\=3\u0015\u0011\ty)!\u0003\u0002\u001b]DWM\\+oQ\u0006tG\r\\3e)\r\u0019\u00181\u0014\u0005\b\u0003\u0007\u0011\u0002\u0019AA\u0003\u00035yg\u000eV3s[&t\u0017\r^5p]R\u00191/!)\t\u000f\u0005\r6\u00031\u0001\u0002&\u0006Y1\u000f^8q\u0005VLG\u000eZ3s!\u0019\t9!a*\\;&!\u0011\u0011VA\u0005\u0005915+T*u_B\u0014U/\u001b7eKJ\f!\"\\1uG\",e/\u001a8u+\u0019\ty+a2\u0002TRQ\u0011QAAY\u0003\u0017\fI.a9\t\u000f\u0005MF\u00031\u0001\u00026\u0006IQM^3oiRK\b/\u001a\t\u0007\u0003o\u000by,!2\u000f\t\u0005e\u00161\u0018\t\u0004\u0003\u0003c\u0014bAA_y\u00051\u0001K]3eK\u001aLA!!1\u0002D\n)1\t\\1tg*\u0019\u0011Q\u0018\u001f\u0011\u0007%\u000b9\r\u0002\u0004\u0002JR\u0011\r\u0001\u0014\u0002\u0003\u000bRCq!!4\u0015\u0001\u0004\ty-\u0001\u0005eCR\fG+\u001f9f!\u0019\t9,a0\u0002RB\u0019\u0011*a5\u0005\u000f\u0005UGC1\u0001\u0002X\n\u0011A\tV\t\u0003\u001bvCq!a7\u0015\u0001\u0004\ti.A\u0005qe\u0016$\u0017nY1uKBA\u0011\u0011PAp\u0003\u000b\f\t.\u0003\u0003\u0002b\u0006U%a\u0004+za\u0016$\u0007K]3eS\u000e\fG/\u001a\u001a\t\u000f\u0005\u0015H\u00031\u0001\u0002h\u0006)\u0011\r\u001d9msBQ\u0011\u0011PAu\u0003\u000b\f\t.!<\n\t\u0005-\u0018Q\u0013\u0002\u0007\u0003B\u0004H.\u001f\u001a\u0011\u0007e\fy/C\u0002\u0002rf\u0013Qa\u0015;bi\u0016,b!!>\u0002~\n\u0015A\u0003CA\u0003\u0003o\fyPa\u0002\t\u000f\u0005MV\u00031\u0001\u0002zB1\u0011qWA`\u0003w\u00042!SA\u007f\t\u0019\tI-\u0006b\u0001\u0019\"9\u0011QZ\u000bA\u0002\t\u0005\u0001CBA\\\u0003\u007f\u0013\u0019\u0001E\u0002J\u0005\u000b!q!!6\u0016\u0005\u0004\t9\u000eC\u0004\u0002fV\u0001\rA!\u0003\u0011\u0015\u0005e\u0014\u0011^A~\u0005\u0007\ti/\u0006\u0003\u0003\u000e\tUA\u0003CA\u0003\u0005\u001f\u00119Ba\u0007\t\u000f\u0005Mf\u00031\u0001\u0003\u0012A1\u0011qWA`\u0005'\u00012!\u0013B\u000b\t\u0019\tIM\u0006b\u0001\u0019\"9\u00111\u001c\fA\u0002\te\u0001cBA=\u0003?\u0014\u0019\"\u0018\u0005\b\u0003K4\u0002\u0019\u0001B\u000f!%\tI(!;\u0003\u0014u\u000bi/\u0006\u0003\u0003\"\t%BCBA\u0003\u0005G\u0011Y\u0003C\u0004\u00024^\u0001\rA!\n\u0011\r\u0005]\u0016q\u0018B\u0014!\rI%\u0011\u0006\u0003\u0007\u0003\u0013<\"\u0019\u0001'\t\u000f\u0005\u0015x\u00031\u0001\u0003.AI\u0011\u0011PAu\u0005Oi\u0016Q\u001e\u000b\u0007\u0003\u000b\u0011\tD!\u000e\t\u000f\u0005m\u0007\u00041\u0001\u00034A1\u0011\u0011PApuuCq!!:\u0019\u0001\u0004\u00119\u0004\u0005\u0005\u0002z\u0005%((XAw+\u0011\u0011YDa\u0015\u0015\u0011\u0005\u0015!Q\bB'\u0005+BqAa\u0010\u001a\u0001\u0004\u0011\t%\u0001\u0007fm\u0016tG/T1uG\",7\u000fE\u0003\u0003D\t%#(\u0004\u0002\u0003F)!!qIA\u001f\u0003\u0011)H/\u001b7\n\t\t-#Q\t\u0002\u0005\u0019&\u001cH\u000fC\u0004\u0002Nf\u0001\rAa\u0014\u0011\r\u0005]\u0016q\u0018B)!\rI%1\u000b\u0003\b\u0003+L\"\u0019AAl\u0011\u001d\t)/\u0007a\u0001\u0005/\u0002\u0012\"!\u001f\u0002jj\u0012\t&!<\u0015\r\u0005\u0015!1\fB/\u0011\u001d\u0011yD\u0007a\u0001\u0005\u0003Bq!!:\u001b\u0001\u0004\u00119$\u0001\tnCR\u001c\u0007.\u0012<f]R,\u0015/^1mgV1!1\rB6\u0005k\"\u0002\"!\u0002\u0003f\t=$q\u000f\u0005\b\u0005OZ\u0002\u0019\u0001B5\u0003\u0015)g/\u001a8u!\rI%1\u000e\u0003\u0007\u0005[Z\"\u0019\u0001'\u0003\u0003\u0015Cq!!4\u001c\u0001\u0004\u0011\t\b\u0005\u0004\u00028\u0006}&1\u000f\t\u0004\u0013\nUDaBAk7\t\u0007\u0011q\u001b\u0005\b\u0003K\\\u0002\u0019\u0001B=!)\tI(!;\u0003j\tM\u0014Q^\u000b\u0005\u0005{\u0012\u0019\t\u0006\u0004\u0002\u0006\t}$Q\u0011\u0005\b\u0005Ob\u0002\u0019\u0001BA!\rI%1\u0011\u0003\u0007\u0005[b\"\u0019\u0001'\t\u000f\u0005\u0015H\u00041\u0001\u0003\bBI\u0011\u0011PAu\u0005\u0003k\u0016Q^\u0001\u000e[\u0006$8\r[!os\u00163XM\u001c;\u0015\t\u0005\u0015!Q\u0012\u0005\b\u0003Kl\u0002\u0019\u0001B\u001c\u0003)i\u0017\r^2i'R\fG/\u001a\u000b\t\u0003W\u0012\u0019Ja&\u0003\u001c\"1!Q\u0013\u0010A\u0002m\u000b\u0011B\u001a:p[N#\u0018\r^3\t\r\tee\u00041\u0001\\\u0003\u001d!xn\u0015;bi\u0016Dq!!:\u001f\u0001\u0004\u0011i\n\u0005\u0003\u0002z\t}\u0015\u0002\u0002BQ\u0003+\u0013Q\"\u00168ji\u0006\u0003\b\u000f\\=W_&$G\u0003CA6\u0005K\u00139K!+\t\r\tUu\u00041\u0001\\\u0011\u0019\u0011Ij\ba\u00017\"9\u0011Q]\u0010A\u0002\u0005]\u0014!C7bi\u000eD7\u000b^8q)\u0019\t)Ka,\u0003B\"9!\u0011\u0017\u0011A\u0002\tM\u0016A\u0002:fCN|g\u000e\u0005\u0003\u00036\nmfbA\u001c\u00038&\u0019!\u0011X\u0019\u0002\u0007\u0019\u001bV*\u0003\u0003\u0003>\n}&A\u0002*fCN|gNC\u0002\u0003:FBq!!:!\u0001\u0004\u0011\u0019\r\u0005\u0004\u0002z\u0005E5,X\u000b\u0005\u0005\u000f\u0014\t\u000e\u0006\u0004\u0002&\n%'q\u001b\u0005\b\u0005\u0017\f\u0003\u0019\u0001Bg\u0003)\u0011X-Y:p]RK\b/\u001a\t\u0007\u0003o\u000byLa4\u0011\u0007%\u0013\t\u000eB\u0004\u0003T\u0006\u0012\rA!6\u0003\u0005I#\u0016cA'\u00034\"9\u0011Q]\u0011A\u0002\te\u0007\u0003CA=\u00057\u0014ymW/\n\t\tu\u0017Q\u0013\u0002\u000b+:LG/\u00119qYf\u001cT\u0003\u0002Bq\u0005S$\u0002\"!*\u0003d\n-(1\u001f\u0005\b\u0005\u0017\u0014\u0003\u0019\u0001Bs!\u0019\t9,a0\u0003hB\u0019\u0011J!;\u0005\u000f\tM'E1\u0001\u0003V\"9\u00111\u001c\u0012A\u0002\t5\bCBA=\u0005_\u00149/\u0003\u0003\u0003r\u0006U%A\u0004+za\u0016$\u0007K]3eS\u000e\fG/\u001a\u0005\b\u0003K\u0014\u0003\u0019\u0001B{!!\tIHa7\u0003hnk\u0016!C7bi\u000eDG)\u0019;b+\u0011\u0011Yp!\u0003\u0015\r\tu81AB\u0006!\u0015\t9Aa@^\u0013\u0011\u0019\t!!\u0003\u0003\u001bUs\u0017\u000e\u001e)G\u0005VLG\u000eZ3s\u0011\u001d\tim\ta\u0001\u0007\u000b\u0001b!a.\u0002@\u000e\u001d\u0001cA%\u0004\n\u00119\u0011Q[\u0012C\u0002\u0005]\u0007bBAsG\u0001\u00071Q\u0002\t\u0007\u0003s\u001ayaa\u0002\n\t\rE\u0011Q\u0013\u0002\n+:LG/\u00119qYf,Ba!\u0006\u0004\u001eQA!Q`B\f\u0007?\u0019\u0019\u0003C\u0004\u0002N\u0012\u0002\ra!\u0007\u0011\r\u0005]\u0016qXB\u000e!\rI5Q\u0004\u0003\b\u0003+$#\u0019AAl\u0011\u001d\tY\u000e\na\u0001\u0007C\u0001b!!\u001f\u0003p\u000em\u0001bBAsI\u0001\u00071Q\u0005\t\u0007\u0003s\u001ayaa\u0007\u0002\t\u001d|Gk\u001c\u000b\u0005\u0003[\u001cY\u0003\u0003\u0004\u0004.\u0015\u0002\raW\u0001\u000e]\u0016DHo\u0015;bi\u0016t\u0015-\\3\u00021M$\u0018M\u001d;US6,'oV5uQ\u001aK\u00070\u001a3EK2\f\u0017\u0010F\u0004t\u0007g\u0019id!\u0011\t\u000f\rUb\u00051\u0001\u00048\u0005!a.Y7f!\u0011\t9l!\u000f\n\t\rm\u00121\u0019\u0002\u0007'R\u0014\u0018N\\4\t\r\r}b\u00051\u0001Q\u0003\ri7o\u001a\u0005\b\u0007\u00072\u0003\u0019AA\u001b\u0003\u0015!W\r\\1z\u0003U\u0019H/\u0019:u)&lWM]!u\r&DX\r\u001a*bi\u0016$ra]B%\u0007\u0017\u001ai\u0005C\u0004\u00046\u001d\u0002\raa\u000e\t\r\r}r\u00051\u0001Q\u0011\u001d\u0019ye\na\u0001\u0003k\t\u0001\"\u001b8uKJ4\u0018\r\\\u0001\u0011gR\f'\u000f^*j]\u001edW\rV5nKJ$ra]B+\u0007/\u001aI\u0006C\u0004\u00046!\u0002\raa\u000e\t\r\r}\u0002\u00061\u0001Q\u0011\u001d\u0019\u0019\u0005\u000ba\u0001\u0003k\t\u0001b]3u)&lWM\u001d\u000b\bg\u000e}3\u0011MB2\u0011\u001d\u0019)$\u000ba\u0001\u0007oAaaa\u0010*\u0001\u0004\u0001\u0006bBA-S\u0001\u0007\u0011Q\u0004\u0015\fS\r\u001d4QNB8\u0007g\u001a)\bE\u0002<\u0007SJ1aa\u001b=\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\u0019\t(A\u000fVg\u0016\u00043\u000f^1siNKgn\u001a7f)&lWM\u001d\u0011j]N$X-\u00193/\u0003\u0015\u0019\u0018N\\2fC\t\u00199(A\u00033]Yr\u0003\u0007F\u0004t\u0007w\u001aiha \t\u000f\rU\"\u00061\u0001\u00048!11q\b\u0016A\u0002ACq!!\u0017+\u0001\u0004\t)\u0004K\u0006+\u0007O\u001aiga\u001c\u0004t\rUD#C:\u0004\u0006\u000e\u001d5\u0011RBF\u0011\u001d\u0019)d\u000ba\u0001\u0007oAaaa\u0010,\u0001\u0004\u0001\u0006bBA-W\u0001\u0007\u0011Q\u0007\u0005\b\u0007\u001b[\u0003\u0019ABH\u0003\u0019\u0011X\r]3biB\u00191h!%\n\u0007\rMEHA\u0004C_>dW-\u00198)\u0017-\u001a9g!\u001c\u0004\u0018\u000eM4QO\u0011\u0003\u00073\u000b\u0011qN+tK\u0002\u001aH/\u0019:u'&tw\r\\3US6,'\u000f\f\u0011ti\u0006\u0014H\u000fV5nKJ<\u0016\u000e\u001e5GSb,G\rR3mCf\u0004sN\u001d\u0011ti\u0006\u0014H\u000fV5nKJ\fEOR5yK\u0012\u0014\u0016\r^3!S:\u001cH/Z1e]\u0001\"\u0006.[:!Q\u0006\u001c\b\u0005\u001e5fAM\fW.\u001a\u0011tK6\fg\u000e^5dg\u0002\n7\u000fI:uCJ$H+[7fe\u0006#h)\u001b=fIJ\u000bG/\u001a\u0017!EV$\be\u001d;beR$\u0016.\\3s/&$\bNR5yK\u0012$U\r\\1zA%\u001c\be\u001c4uK:\u0004\u0003O]3gKJ\u0014X\r\u001a\u0018\u0002\r9{'/\\1m+\t\u0011\u0019,A\u0004O_Jl\u0017\r\u001c\u0011\u0002\u0011MCW\u000f\u001e3po:\f\u0011b\u00155vi\u0012|wO\u001c\u0011")
public abstract class AbstractFSM<S, D>
implements FSM<S, D> {
    private final FSM.Reason Normal;
    private final FSM.Reason Shutdown;
    private FSM$Event$ Event;
    private FSM$StopEvent$ StopEvent;
    private FSM$$minus$greater$ $minus$greater;
    private FSM$StateTimeout$ StateTimeout;
    private FSM.State<S, D> akka$actor$FSM$$currentState;
    private Option<Cancellable> akka$actor$FSM$$timeoutFuture;
    private FSM.State<S, D> akka$actor$FSM$$nextState;
    private long akka$actor$FSM$$generation;
    private Map<String, FSM.Timer> akka$actor$FSM$$timers;
    private Iterator<Object> akka$actor$FSM$$timerGen;
    private Map<S, PartialFunction<FSM.Event<D>, FSM.State<S, D>>> akka$actor$FSM$$stateFunctions;
    private Map<S, Option<FiniteDuration>> akka$actor$FSM$$stateTimeouts;
    private PartialFunction<FSM.Event<D>, FSM.State<S, D>> akka$actor$FSM$$handleEventDefault;
    private PartialFunction<FSM.Event<D>, FSM.State<S, D>> akka$actor$FSM$$handleEvent;
    private PartialFunction<FSM.StopEvent<S, D>, BoxedUnit> akka$actor$FSM$$terminateEvent;
    private List<PartialFunction<Tuple2<S, S>, BoxedUnit>> akka$actor$FSM$$transitionEvent;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private Set<ActorRef> listeners;
    private ActorContext context;
    private ActorRef self;

    public static <S, D> PartialFunction<S, D> NullFunction() {
        return AbstractFSM$.MODULE$.NullFunction();
    }

    @Override
    public /* synthetic */ void akka$actor$FSM$$super$postStop() {
        Actor.postStop$(this);
    }

    @Override
    public final void when(S stateName, FiniteDuration stateTimeout, PartialFunction<FSM.Event<D>, FSM.State<S, D>> stateFunction2) {
        FSM.when$(this, stateName, stateTimeout, stateFunction2);
    }

    @Override
    public final FiniteDuration when$default$2() {
        return FSM.when$default$2$(this);
    }

    @Override
    public final void startWith(S stateName, D stateData, Option<FiniteDuration> timeout2) {
        FSM.startWith$(this, stateName, stateData, timeout2);
    }

    @Override
    public final Option<FiniteDuration> startWith$default$3() {
        return FSM.startWith$default$3$(this);
    }

    @Override
    public final FSM.State<S, D> goto(S nextStateName) {
        return FSM.goto$(this, nextStateName);
    }

    @Override
    public final FSM.State<S, D> stay() {
        return FSM.stay$(this);
    }

    @Override
    public final FSM.State<S, D> stop() {
        return FSM.stop$(this);
    }

    @Override
    public final FSM.State<S, D> stop(FSM.Reason reason) {
        return FSM.stop$(this, reason);
    }

    @Override
    public final FSM.State<S, D> stop(FSM.Reason reason, D stateData) {
        return FSM.stop$(this, reason, stateData);
    }

    @Override
    public final FSM.TransformHelper transform(PartialFunction<FSM.Event<D>, FSM.State<S, D>> func) {
        return FSM.transform$(this, func);
    }

    @Override
    public void startTimerWithFixedDelay(String name, Object msg, FiniteDuration delay) {
        FSM.startTimerWithFixedDelay$(this, name, msg, delay);
    }

    @Override
    public void startTimerAtFixedRate(String name, Object msg, FiniteDuration interval) {
        FSM.startTimerAtFixedRate$(this, name, msg, interval);
    }

    @Override
    public void startSingleTimer(String name, Object msg, FiniteDuration delay) {
        FSM.startSingleTimer$(this, name, msg, delay);
    }

    @Override
    public final void setTimer(String name, Object msg, FiniteDuration timeout2, boolean repeat) {
        FSM.setTimer$(this, name, msg, timeout2, repeat);
    }

    @Override
    public final boolean setTimer$default$4() {
        return FSM.setTimer$default$4$(this);
    }

    @Override
    public final void cancelTimer(String name) {
        FSM.cancelTimer$(this, name);
    }

    @Override
    public final boolean isTimerActive(String name) {
        return FSM.isTimerActive$(this, name);
    }

    @Override
    public final void setStateTimeout(S state, Option<FiniteDuration> timeout2) {
        FSM.setStateTimeout$(this, state, timeout2);
    }

    @Override
    public final boolean isStateTimerActive() {
        return FSM.isStateTimerActive$(this);
    }

    @Override
    public final void onTransition(PartialFunction<Tuple2<S, S>, BoxedUnit> transitionHandler) {
        FSM.onTransition$(this, transitionHandler);
    }

    @Override
    public final PartialFunction<Tuple2<S, S>, BoxedUnit> total2pf(Function2<S, S, BoxedUnit> transitionHandler) {
        return FSM.total2pf$(this, transitionHandler);
    }

    @Override
    public final void onTermination(PartialFunction<FSM.StopEvent<S, D>, BoxedUnit> terminationHandler) {
        FSM.onTermination$(this, terminationHandler);
    }

    @Override
    public final void whenUnhandled(PartialFunction<FSM.Event<D>, FSM.State<S, D>> stateFunction2) {
        FSM.whenUnhandled$(this, stateFunction2);
    }

    @Override
    public final void initialize() {
        FSM.initialize$(this);
    }

    @Override
    public final S stateName() {
        return (S)FSM.stateName$(this);
    }

    @Override
    public final D stateData() {
        return (D)FSM.stateData$(this);
    }

    @Override
    public final D nextStateData() {
        return (D)FSM.nextStateData$(this);
    }

    @Override
    public boolean debugEvent() {
        return FSM.debugEvent$(this);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return FSM.receive$(this);
    }

    @Override
    public void processEvent(FSM.Event<D> event, Object source2) {
        FSM.processEvent$(this, event, source2);
    }

    @Override
    public void applyState(FSM.State<S, D> nextState) {
        FSM.applyState$(this, nextState);
    }

    @Override
    public void makeTransition(FSM.State<S, D> nextState) {
        FSM.makeTransition$(this, nextState);
    }

    @Override
    public void postStop() {
        FSM.postStop$(this);
    }

    @Override
    public void logTermination(FSM.Reason reason) {
        FSM.logTermination$(this, reason);
    }

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> listenerManagement() {
        return Listeners.listenerManagement$(this);
    }

    @Override
    public void gossip(Object msg, ActorRef sender) {
        Listeners.gossip$(this, msg, sender);
    }

    @Override
    public ActorRef gossip$default$2(Object msg) {
        return Listeners.gossip$default$2$(this, msg);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public FSM$Event$ Event() {
        return this.Event;
    }

    @Override
    public FSM$StopEvent$ StopEvent() {
        return this.StopEvent;
    }

    @Override
    public FSM$$minus$greater$ $minus$greater() {
        return this.$minus$greater;
    }

    @Override
    public FSM$StateTimeout$ StateTimeout() {
        return this.StateTimeout;
    }

    @Override
    public FSM.State<S, D> akka$actor$FSM$$currentState() {
        return this.akka$actor$FSM$$currentState;
    }

    @Override
    public void akka$actor$FSM$$currentState_$eq(FSM.State<S, D> x$1) {
        this.akka$actor$FSM$$currentState = x$1;
    }

    @Override
    public Option<Cancellable> akka$actor$FSM$$timeoutFuture() {
        return this.akka$actor$FSM$$timeoutFuture;
    }

    @Override
    public void akka$actor$FSM$$timeoutFuture_$eq(Option<Cancellable> x$1) {
        this.akka$actor$FSM$$timeoutFuture = x$1;
    }

    @Override
    public FSM.State<S, D> akka$actor$FSM$$nextState() {
        return this.akka$actor$FSM$$nextState;
    }

    @Override
    public void akka$actor$FSM$$nextState_$eq(FSM.State<S, D> x$1) {
        this.akka$actor$FSM$$nextState = x$1;
    }

    @Override
    public long akka$actor$FSM$$generation() {
        return this.akka$actor$FSM$$generation;
    }

    @Override
    public void akka$actor$FSM$$generation_$eq(long x$1) {
        this.akka$actor$FSM$$generation = x$1;
    }

    @Override
    public Map<String, FSM.Timer> akka$actor$FSM$$timers() {
        return this.akka$actor$FSM$$timers;
    }

    @Override
    public Iterator<Object> akka$actor$FSM$$timerGen() {
        return this.akka$actor$FSM$$timerGen;
    }

    @Override
    public Map<S, PartialFunction<FSM.Event<D>, FSM.State<S, D>>> akka$actor$FSM$$stateFunctions() {
        return this.akka$actor$FSM$$stateFunctions;
    }

    @Override
    public Map<S, Option<FiniteDuration>> akka$actor$FSM$$stateTimeouts() {
        return this.akka$actor$FSM$$stateTimeouts;
    }

    @Override
    public PartialFunction<FSM.Event<D>, FSM.State<S, D>> akka$actor$FSM$$handleEventDefault() {
        return this.akka$actor$FSM$$handleEventDefault;
    }

    @Override
    public PartialFunction<FSM.Event<D>, FSM.State<S, D>> akka$actor$FSM$$handleEvent() {
        return this.akka$actor$FSM$$handleEvent;
    }

    @Override
    public void akka$actor$FSM$$handleEvent_$eq(PartialFunction<FSM.Event<D>, FSM.State<S, D>> x$1) {
        this.akka$actor$FSM$$handleEvent = x$1;
    }

    @Override
    public PartialFunction<FSM.StopEvent<S, D>, BoxedUnit> akka$actor$FSM$$terminateEvent() {
        return this.akka$actor$FSM$$terminateEvent;
    }

    @Override
    public void akka$actor$FSM$$terminateEvent_$eq(PartialFunction<FSM.StopEvent<S, D>, BoxedUnit> x$1) {
        this.akka$actor$FSM$$terminateEvent = x$1;
    }

    @Override
    public List<PartialFunction<Tuple2<S, S>, BoxedUnit>> akka$actor$FSM$$transitionEvent() {
        return this.akka$actor$FSM$$transitionEvent;
    }

    @Override
    public void akka$actor$FSM$$transitionEvent_$eq(List<PartialFunction<Tuple2<S, S>, BoxedUnit>> x$1) {
        this.akka$actor$FSM$$transitionEvent = x$1;
    }

    @Override
    public void akka$actor$FSM$_setter_$Event_$eq(FSM$Event$ x$1) {
        this.Event = x$1;
    }

    @Override
    public void akka$actor$FSM$_setter_$StopEvent_$eq(FSM$StopEvent$ x$1) {
        this.StopEvent = x$1;
    }

    @Override
    public void akka$actor$FSM$_setter_$$minus$greater_$eq(FSM$$minus$greater$ x$1) {
        this.$minus$greater = x$1;
    }

    @Override
    public void akka$actor$FSM$_setter_$StateTimeout_$eq(FSM$StateTimeout$ x$1) {
        this.StateTimeout = x$1;
    }

    @Override
    public final void akka$actor$FSM$_setter_$akka$actor$FSM$$timers_$eq(Map<String, FSM.Timer> x$1) {
        this.akka$actor$FSM$$timers = x$1;
    }

    @Override
    public final void akka$actor$FSM$_setter_$akka$actor$FSM$$timerGen_$eq(Iterator<Object> x$1) {
        this.akka$actor$FSM$$timerGen = x$1;
    }

    @Override
    public final void akka$actor$FSM$_setter_$akka$actor$FSM$$stateFunctions_$eq(Map<S, PartialFunction<FSM.Event<D>, FSM.State<S, D>>> x$1) {
        this.akka$actor$FSM$$stateFunctions = x$1;
    }

    @Override
    public final void akka$actor$FSM$_setter_$akka$actor$FSM$$stateTimeouts_$eq(Map<S, Option<FiniteDuration>> x$1) {
        this.akka$actor$FSM$$stateTimeouts = x$1;
    }

    @Override
    public final void akka$actor$FSM$_setter_$akka$actor$FSM$$handleEventDefault_$eq(PartialFunction<FSM.Event<D>, FSM.State<S, D>> x$1) {
        this.akka$actor$FSM$$handleEventDefault = x$1;
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public Set<ActorRef> listeners() {
        return this.listeners;
    }

    @Override
    public void akka$routing$Listeners$_setter_$listeners_$eq(Set<ActorRef> x$1) {
        this.listeners = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public AbstractActor.ActorContext getContext() {
        return (AbstractActor.ActorContext)this.context();
    }

    public ActorRef getSelf() {
        return this.self();
    }

    public ActorRef getSender() {
        return this.sender();
    }

    public final void when(S stateName, PartialFunction<FSM.Event<D>, FSM.State<S, D>> stateFunction2) {
        this.when(stateName, null, stateFunction2);
    }

    public final void when(S stateName, FSMStateFunctionBuilder<S, D> stateFunctionBuilder) {
        this.when(stateName, (FiniteDuration)null, stateFunctionBuilder);
    }

    public final void when(S stateName, FiniteDuration stateTimeout, FSMStateFunctionBuilder<S, D> stateFunctionBuilder) {
        FSM.when$(this, stateName, stateTimeout, stateFunctionBuilder.build());
    }

    public final void when(S stateName, Duration stateTimeout, FSMStateFunctionBuilder<S, D> stateFunctionBuilder) {
        this.when(stateName, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(stateTimeout)), stateFunctionBuilder);
    }

    public final void startWith(S stateName, D stateData) {
        this.startWith(stateName, stateData, (FiniteDuration)null);
    }

    public final void startWith(S stateName, D stateData, FiniteDuration timeout2) {
        FSM.startWith$(this, stateName, stateData, Option$.MODULE$.apply(timeout2));
    }

    public final void startWith(S stateName, D stateData, Duration timeout2) {
        this.startWith(stateName, stateData, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)));
    }

    public final void onTransition(FSMTransitionHandlerBuilder<S> transitionHandlerBuilder) {
        FSM.onTransition$(this, transitionHandlerBuilder.build());
    }

    public final void onTransition(FI.UnitApply2<S, S> transitionHandler) {
        PartialFunction<Tuple2<S, S>, BoxedUnit> pf = PartialFunction$.MODULE$.fromFunction(this.total2pf((Function2<Object, Object, Object> & Serializable)(x$1, x$2) -> {
            transitionHandler.apply(x$1, x$2);
            return BoxedUnit.UNIT;
        }));
        FSM.onTransition$(this, pf);
    }

    public final void whenUnhandled(FSMStateFunctionBuilder<S, D> stateFunctionBuilder) {
        FSM.whenUnhandled$(this, stateFunctionBuilder.build());
    }

    public final void onTermination(FSMStopBuilder<S, D> stopBuilder) {
        FSM.onTermination$(this, stopBuilder.build());
    }

    public final <ET, DT extends D> FSMStateFunctionBuilder<S, D> matchEvent(Class<ET> eventType, Class<DT> dataType, FI.TypedPredicate2<ET, DT> predicate, FI.Apply2<ET, DT, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().event(eventType, dataType, predicate, apply2);
    }

    public final <ET, DT extends D> FSMStateFunctionBuilder<S, D> matchEvent(Class<ET> eventType, Class<DT> dataType, FI.Apply2<ET, DT, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().event(eventType, dataType, apply2);
    }

    public final <ET> FSMStateFunctionBuilder<S, D> matchEvent(Class<ET> eventType, FI.TypedPredicate2<ET, D> predicate, FI.Apply2<ET, D, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().event(eventType, predicate, apply2);
    }

    public final <ET> FSMStateFunctionBuilder<S, D> matchEvent(Class<ET> eventType, FI.Apply2<ET, D, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().event(eventType, apply2);
    }

    public final FSMStateFunctionBuilder<S, D> matchEvent(FI.TypedPredicate2<Object, D> predicate, FI.Apply2<Object, D, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().event(predicate, apply2);
    }

    public final <DT extends D> FSMStateFunctionBuilder<S, D> matchEvent(java.util.List<Object> eventMatches, Class<DT> dataType, FI.Apply2<Object, DT, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().event(eventMatches, dataType, apply2);
    }

    public final FSMStateFunctionBuilder<S, D> matchEvent(java.util.List<Object> eventMatches, FI.Apply2<Object, D, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().event(eventMatches, apply2);
    }

    public final <E, DT extends D> FSMStateFunctionBuilder<S, D> matchEventEquals(E event, Class<DT> dataType, FI.Apply2<E, DT, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().eventEquals(event, dataType, apply2);
    }

    public final <E> FSMStateFunctionBuilder<S, D> matchEventEquals(E event, FI.Apply2<E, D, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().eventEquals(event, apply2);
    }

    public final FSMStateFunctionBuilder<S, D> matchAnyEvent(FI.Apply2<Object, D, FSM.State<S, D>> apply2) {
        return new FSMStateFunctionBuilder<S, D>().anyEvent(apply2);
    }

    public final FSMTransitionHandlerBuilder<S> matchState(S fromState, S toState, FI.UnitApplyVoid apply2) {
        return new FSMTransitionHandlerBuilder<S>().state(fromState, toState, apply2);
    }

    public final FSMTransitionHandlerBuilder<S> matchState(S fromState, S toState, FI.UnitApply2<S, S> apply2) {
        return new FSMTransitionHandlerBuilder<S>().state(fromState, toState, apply2);
    }

    public final FSMStopBuilder<S, D> matchStop(FSM.Reason reason, FI.UnitApply2<S, D> apply2) {
        return new FSMStopBuilder<S, D>().stop(reason, apply2);
    }

    public final <RT extends FSM.Reason> FSMStopBuilder<S, D> matchStop(Class<RT> reasonType, FI.UnitApply3<RT, S, D> apply2) {
        return new FSMStopBuilder<S, D>().stop(reasonType, apply2);
    }

    public final <RT extends FSM.Reason> FSMStopBuilder<S, D> matchStop(Class<RT> reasonType, FI.TypedPredicate<RT> predicate, FI.UnitApply3<RT, S, D> apply2) {
        return new FSMStopBuilder<S, D>().stop(reasonType, predicate, apply2);
    }

    public final <DT extends D> UnitPFBuilder<D> matchData(Class<DT> dataType, FI.UnitApply<DT> apply2) {
        return UnitMatch.match(dataType, apply2);
    }

    public final <DT extends D> UnitPFBuilder<D> matchData(Class<DT> dataType, FI.TypedPredicate<DT> predicate, FI.UnitApply<DT> apply2) {
        return UnitMatch.match(dataType, predicate, apply2);
    }

    public final FSM.State<S, D> goTo(S nextStateName) {
        return this.goto(nextStateName);
    }

    public void startTimerWithFixedDelay(String name, Object msg, Duration delay) {
        this.startTimerWithFixedDelay(name, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)));
    }

    public void startTimerAtFixedRate(String name, Object msg, Duration interval) {
        this.startTimerAtFixedRate(name, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }

    public void startSingleTimer(String name, Object msg, Duration delay) {
        this.startSingleTimer(name, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)));
    }

    public final void setTimer(String name, Object msg, FiniteDuration timeout2) {
        this.setTimer(name, msg, timeout2, false);
    }

    public final void setTimer(String name, Object msg, Duration timeout2) {
        this.setTimer(name, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)), false);
    }

    public final void setTimer(String name, Object msg, Duration timeout2, boolean repeat) {
        this.setTimer(name, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)), repeat);
    }

    public FSM.Reason Normal() {
        return this.Normal;
    }

    public FSM.Reason Shutdown() {
        return this.Shutdown;
    }

    public AbstractFSM() {
        Actor.$init$(this);
        Listeners.$init$(this);
        ActorLogging.$init$(this);
        FSM.$init$(this);
        this.Normal = FSM$Normal$.MODULE$;
        this.Shutdown = FSM$Shutdown$.MODULE$;
        Statics.releaseFence();
    }
}

