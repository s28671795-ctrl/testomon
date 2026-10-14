/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.OneForOneStrategy$;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.SupervisorStrategy$;
import akka.pattern.AutoReset;
import akka.pattern.BackoffOnStopOptions;
import akka.pattern.BackoffOnStopOptionsImpl$;
import akka.pattern.BackoffReset;
import akka.pattern.ForwardTo;
import akka.pattern.HandlingWhileStopped;
import akka.pattern.ManualReset$;
import akka.pattern.ReplyWith;
import akka.pattern.internal.BackoffOnStopSupervisor;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple9;
import scala.collection.Iterator;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\rea\u0001B!C\r\u001eC\u0001B\u0019\u0001\u0003\u0016\u0004%\ta\u0019\u0005\tU\u0002\u0011\t\u0012)A\u0005I\"A1\u000e\u0001BK\u0002\u0013\u0005A\u000e\u0003\u0005v\u0001\tE\t\u0015!\u0003n\u0011!1\bA!f\u0001\n\u00039\b\"CA\u0001\u0001\tE\t\u0015!\u0003y\u0011%\t\u0019\u0001\u0001BK\u0002\u0013\u0005q\u000fC\u0005\u0002\u0006\u0001\u0011\t\u0012)A\u0005q\"Q\u0011q\u0001\u0001\u0003\u0016\u0004%\t!!\u0003\t\u0015\u0005E\u0001A!E!\u0002\u0013\tY\u0001\u0003\u0006\u0002\u0014\u0001\u0011)\u001a!C\u0001\u0003+A!\"a\t\u0001\u0005#\u0005\u000b\u0011BA\f\u0011)\t)\u0003\u0001BK\u0002\u0013\u0005\u0011q\u0005\u0005\u000b\u0003_\u0001!\u0011#Q\u0001\n\u0005%\u0002BCA\u0019\u0001\tU\r\u0011\"\u0001\u00024!Q\u00111\b\u0001\u0003\u0012\u0003\u0006I!!\u000e\t\u0015\u0005u\u0002A!f\u0001\n\u0003\ty\u0004\u0003\u0006\u0002V\u0001\u0011\t\u0012)A\u0005\u0003\u0003Bq!a\u0016\u0001\t\u0003\tI\u0006C\u0005\u0002\u0000\u0001\u0011\r\u0011\"\u0003\u0002\u0002\"A\u00111\u0011\u0001!\u0002\u0013\ti\u0002C\u0004\u0002\u0006\u0002!\t!a\"\t\u000f\u0005=\u0005\u0001\"\u0001\u0002\u0012\"9\u00111\u0013\u0001\u0005\u0002\u0005U\u0005bBAM\u0001\u0011\u0005\u00111\u0014\u0005\b\u0003C\u0003A\u0011AAR\u0011\u001d\ty\u000b\u0001C\u0001\u0003cCq!!0\u0001\t\u0003\t\t\nC\u0004\u0002@\u0002!\t!!1\t\r\u0005\u001d\u0007\u0001\"\u0001d\u0011%\tI\rAA\u0001\n\u0003\tY\rC\u0005\u0002h\u0002\t\n\u0011\"\u0001\u0002j\"I!1\u0001\u0001\u0012\u0002\u0013\u0005!Q\u0001\u0005\n\u0005\u001b\u0001\u0011\u0013!C\u0001\u0005\u001fA\u0011Ba\u0006\u0001#\u0003%\tA!\u0007\t\u0013\tu\u0001!%A\u0005\u0002\t}\u0001\"\u0003B\u0014\u0001E\u0005I\u0011\u0001B\u0015\u0011%\u0011\t\u0004AI\u0001\n\u0003\u0011\u0019\u0004C\u0005\u0003<\u0001\t\n\u0011\"\u0001\u0003>!I!Q\t\u0001\u0012\u0002\u0013\u0005!q\t\u0005\n\u0005\u001f\u0002\u0011\u0011!C!\u0005#B\u0011B!\u0019\u0001\u0003\u0003%\tAa\u0019\t\u0013\t\u0015\u0004!!A\u0005\u0002\t\u001d\u0004\"\u0003B7\u0001\u0005\u0005I\u0011\tB8\u0011%\u0011i\bAA\u0001\n\u0003\u0011y\bC\u0005\u0003\u0004\u0002\t\t\u0011\"\u0011\u0003\u0006\"I!\u0011\u0012\u0001\u0002\u0002\u0013\u0005#1\u0012\u0005\n\u0005\u001b\u0003\u0011\u0011!C!\u0005\u001fC\u0011B!%\u0001\u0003\u0003%\tEa%\b\u0013\t]%)!A\t\n\tee\u0001C!C\u0003\u0003EIAa'\t\u000f\u0005]3\u0007\"\u0001\u0003(\"I!QR\u001a\u0002\u0002\u0013\u0015#q\u0012\u0005\n\u0005S\u001b\u0014\u0011!CA\u0005WC\u0011Ba24#\u0003%\tA!3\t\u0013\t57'%A\u0005\u0002\t=\u0007\"\u0003BjgE\u0005I\u0011\u0001Bk\u0011%\u0011InMI\u0001\n\u0003\u0011Y\u000eC\u0005\u0003`N\n\t\u0011\"!\u0003b\"I!q_\u001a\u0012\u0002\u0013\u0005!\u0011 \u0005\n\u0005{\u001c\u0014\u0013!C\u0001\u0005\u007fD\u0011ba\u00014#\u0003%\ta!\u0002\t\u0013\r%1'%A\u0005\u0002\r-\u0001\"CB\bg\u0005\u0005I\u0011BB\t\u0005a\u0011\u0015mY6pM\u001a|en\u0015;pa>\u0003H/[8og&k\u0007\u000f\u001c\u0006\u0003\u0007\u0012\u000bq\u0001]1ui\u0016\u0014hNC\u0001F\u0003\u0011\t7n[1\u0004\u0001U\u0019\u0001*!\u0019\u0014\u000b\u0001Iuj\u0015,\u0011\u0005)kU\"A&\u000b\u00031\u000bQa]2bY\u0006L!AT&\u0003\r\u0005s\u0017PU3g!\t\u0001\u0016+D\u0001C\u0013\t\u0011&I\u0001\u000bCC\u000e\\wN\u001a4P]N#x\u000e](qi&|gn\u001d\t\u0003\u0015RK!!V&\u0003\u000fA\u0013x\u000eZ;diB\u0011qk\u0018\b\u00031vs!!\u0017/\u000e\u0003iS!a\u0017$\u0002\rq\u0012xn\u001c;?\u0013\u0005a\u0015B\u00010L\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001Y1\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005y[\u0015AC2iS2$\u0007K]8qgV\tA\r\u0005\u0002fQ6\taM\u0003\u0002h\t\u0006)\u0011m\u0019;pe&\u0011\u0011N\u001a\u0002\u0006!J|\u0007o]\u0001\fG\"LG\u000e\u001a)s_B\u001c\b%A\u0005dQ&dGMT1nKV\tQ\u000e\u0005\u0002oe:\u0011q\u000e\u001d\t\u00033.K!!]&\u0002\rA\u0013X\rZ3g\u0013\t\u0019HO\u0001\u0004TiJLgn\u001a\u0006\u0003c.\u000b!b\u00195jY\u0012t\u0015-\\3!\u0003)i\u0017N\u001c\"bG.|gMZ\u000b\u0002qB\u0011\u0011P`\u0007\u0002u*\u00111\u0010`\u0001\tIV\u0014\u0018\r^5p]*\u0011QpS\u0001\u000bG>t7-\u001e:sK:$\u0018BA@{\u000591\u0015N\\5uK\u0012+(/\u0019;j_:\f1\"\\5o\u0005\u0006\u001c7n\u001c4gA\u0005QQ.\u0019=CC\u000e\\wN\u001a4\u0002\u00175\f\u0007PQ1dW>4g\rI\u0001\re\u0006tGm\\7GC\u000e$xN]\u000b\u0003\u0003\u0017\u00012ASA\u0007\u0013\r\tya\u0013\u0002\u0007\t>,(\r\\3\u0002\u001bI\fg\u000eZ8n\r\u0006\u001cGo\u001c:!\u0003\u0015\u0011Xm]3u+\t\t9\u0002E\u0003K\u00033\ti\"C\u0002\u0002\u001c-\u0013aa\u00149uS>t\u0007c\u0001)\u0002 %\u0019\u0011\u0011\u0005\"\u0003\u0019\t\u000b7m[8gMJ+7/\u001a;\u0002\rI,7/\u001a;!\u0003I\u0019X\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0016\u0005\u0005%\u0002cA3\u0002,%\u0019\u0011Q\u00064\u0003#=sWMR8s\u001f:,7\u000b\u001e:bi\u0016<\u00170A\ntkB,'O^5t_J\u001cFO]1uK\u001eL\b%\u0001\u000biC:$G.\u001b8h/\"LG.Z*u_B\u0004X\rZ\u000b\u0003\u0003k\u00012\u0001UA\u001c\u0013\r\tID\u0011\u0002\u0015\u0011\u0006tG\r\\5oO^C\u0017\u000e\\3Ti>\u0004\b/\u001a3\u0002+!\fg\u000e\u001a7j]\u001e<\u0006.\u001b7f'R|\u0007\u000f]3eA\u0005\u0001b-\u001b8bYN#x\u000e]'fgN\fw-Z\u000b\u0003\u0003\u0003\u0002RASA\r\u0003\u0007\u0002rASA#\u0003\u0013\ny%C\u0002\u0002H-\u0013\u0011BR;oGRLwN\\\u0019\u0011\u0007)\u000bY%C\u0002\u0002N-\u00131!\u00118z!\rQ\u0015\u0011K\u0005\u0004\u0003'Z%a\u0002\"p_2,\u0017M\\\u0001\u0012M&t\u0017\r\\*u_BlUm]:bO\u0016\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u000b\u0002\\\u00055\u0014qNA9\u0003g\n)(a\u001e\u0002z\u0005m\u0014Q\u0010\t\u0005!\u0002\ti\u0006\u0005\u0003\u0002`\u0005\u0005D\u0002\u0001\u0003\b\u0003G\u0002!\u0019AA3\u0005\u0005!\u0016\u0003BA4\u0003\u0013\u00022ASA5\u0013\r\tYg\u0013\u0002\b\u001d>$\b.\u001b8h\u0011\u0015\u00117\u00031\u0001e\u0011\u0015Y7\u00031\u0001n\u0011\u001518\u00031\u0001y\u0011\u0019\t\u0019a\u0005a\u0001q\"9\u0011qA\nA\u0002\u0005-\u0001\"CA\n'A\u0005\t\u0019AA\f\u0011%\t)c\u0005I\u0001\u0002\u0004\tI\u0003C\u0005\u00022M\u0001\n\u00111\u0001\u00026!I\u0011QH\n\u0011\u0002\u0003\u0007\u0011\u0011I\u0001\rE\u0006\u001c7n\u001c4g%\u0016\u001cX\r^\u000b\u0003\u0003;\tQBY1dW>4gMU3tKR\u0004\u0013!D<ji\"\fU\u000f^8SKN,G\u000f\u0006\u0003\u0002\n\u0006-\u0005\u0003\u0002)\u0001\u0003OBa!!$\u0017\u0001\u0004A\u0018\u0001\u0004:fg\u0016$()Y2l_\u001a4\u0017aD<ji\"l\u0015M\\;bYJ+7/\u001a;\u0016\u0005\u0005%\u0015AF<ji\"\u001cV\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0015\t\u0005%\u0015q\u0013\u0005\b\u0003KA\u0002\u0019AA\u0015\u0003U9\u0018\u000e\u001e5SKBd\u0017p\u00165jY\u0016\u001cFo\u001c9qK\u0012$B!!#\u0002\u001e\"9\u0011qT\rA\u0002\u0005%\u0013!\u0005:fa2Lx\u000b[5mKN#x\u000e\u001d9fI\u00069r/\u001b;i\u0011\u0006tG\r\\3s/\"LG.Z*u_B\u0004X\r\u001a\u000b\u0005\u0003\u0013\u000b)\u000bC\u0004\u0002(j\u0001\r!!+\u0002'!\fg\u000e\u001a7fe^C\u0017\u000e\\3Ti>\u0004\b/\u001a3\u0011\u0007\u0015\fY+C\u0002\u0002.\u001a\u0014\u0001\"Q2u_J\u0014VMZ\u0001\u0013o&$\b.T1y\u001dJ|eMU3ue&,7\u000f\u0006\u0003\u0002\n\u0006M\u0006bBA[7\u0001\u0007\u0011qW\u0001\u000f[\u0006DhJ](g%\u0016$(/[3t!\rQ\u0015\u0011X\u0005\u0004\u0003w[%aA%oi\u0006Yr/\u001b;i\t\u00164\u0017-\u001e7u'R|\u0007\u000f]5oON#(/\u0019;fOf\fAc^5uQ\u001aKg.\u00197Ti>\u0004X*Z:tC\u001e,G\u0003BAE\u0003\u0007Dq!!2\u001e\u0001\u0004\t\u0019%\u0001\u0004bGRLwN\\\u0001\u0006aJ|\u0007o]\u0001\u0005G>\u0004\u00180\u0006\u0003\u0002N\u0006MG\u0003FAh\u0003+\f9.!7\u0002\\\u0006u\u0017q\\Aq\u0003G\f)\u000f\u0005\u0003Q\u0001\u0005E\u0007\u0003BA0\u0003'$q!a\u0019 \u0005\u0004\t)\u0007C\u0004c?A\u0005\t\u0019\u00013\t\u000f-|\u0002\u0013!a\u0001[\"9ao\bI\u0001\u0002\u0004A\b\u0002CA\u0002?A\u0005\t\u0019\u0001=\t\u0013\u0005\u001dq\u0004%AA\u0002\u0005-\u0001\"CA\n?A\u0005\t\u0019AA\f\u0011%\t)c\bI\u0001\u0002\u0004\tI\u0003C\u0005\u00022}\u0001\n\u00111\u0001\u00026!I\u0011QH\u0010\u0011\u0002\u0003\u0007\u0011\u0011I\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0011\tYO!\u0001\u0016\u0005\u00055(f\u00013\u0002p.\u0012\u0011\u0011\u001f\t\u0005\u0003g\fi0\u0004\u0002\u0002v*!\u0011q_A}\u0003%)hn\u00195fG.,GMC\u0002\u0002|.\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\ty0!>\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0004\u0002d\u0001\u0012\r!!\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU!!q\u0001B\u0006+\t\u0011IAK\u0002n\u0003_$q!a\u0019\"\u0005\u0004\t)'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\t\tE!QC\u000b\u0003\u0005'Q3\u0001_Ax\t\u001d\t\u0019G\tb\u0001\u0003K\nabY8qs\u0012\"WMZ1vYR$C'\u0006\u0003\u0003\u0012\tmAaBA2G\t\u0007\u0011QM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+\u0011\u0011\tC!\n\u0016\u0005\t\r\"\u0006BA\u0006\u0003_$q!a\u0019%\u0005\u0004\t)'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001c\u0016\t\t-\"qF\u000b\u0003\u0005[QC!a\u0006\u0002p\u00129\u00111M\u0013C\u0002\u0005\u0015\u0014AD2paf$C-\u001a4bk2$HeN\u000b\u0005\u0005k\u0011I$\u0006\u0002\u00038)\"\u0011\u0011FAx\t\u001d\t\u0019G\nb\u0001\u0003K\nabY8qs\u0012\"WMZ1vYR$\u0003(\u0006\u0003\u0003@\t\rSC\u0001B!U\u0011\t)$a<\u0005\u000f\u0005\rtE1\u0001\u0002f\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012JT\u0003\u0002B%\u0005\u001b*\"Aa\u0013+\t\u0005\u0005\u0013q\u001e\u0003\b\u0003GB#\u0019AA3\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011!1\u000b\t\u0005\u0005+\u0012y&\u0004\u0002\u0003X)!!\u0011\fB.\u0003\u0011a\u0017M\\4\u000b\u0005\tu\u0013\u0001\u00026bm\u0006L1a\u001dB,\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\t9,\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005%#\u0011\u000e\u0005\n\u0005WZ\u0013\u0011!a\u0001\u0003o\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B9!\u0019\u0011\u0019H!\u001f\u0002J5\u0011!Q\u000f\u0006\u0004\u0005oZ\u0015AC2pY2,7\r^5p]&!!1\u0010B;\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005=#\u0011\u0011\u0005\n\u0005Wj\u0013\u0011!a\u0001\u0003\u0013\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!!1\u000bBD\u0011%\u0011YGLA\u0001\u0002\u0004\t9,\u0001\u0005iCND7i\u001c3f)\t\t9,\u0001\u0005u_N#(/\u001b8h)\t\u0011\u0019&\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u001f\u0012)\nC\u0005\u0003lE\n\t\u00111\u0001\u0002J\u0005A\")Y2l_\u001a4wJ\\*u_B|\u0005\u000f^5p]NLU\u000e\u001d7\u0011\u0005A\u001b4\u0003B\u001aJ\u0005;\u0003BAa(\u0003&6\u0011!\u0011\u0015\u0006\u0005\u0005G\u0013Y&\u0001\u0002j_&\u0019\u0001M!)\u0015\u0005\te\u0015!B1qa2LX\u0003\u0002BW\u0005g#BCa,\u00036\n]&\u0011\u0018B^\u0005{\u0013yL!1\u0003D\n\u0015\u0007\u0003\u0002)\u0001\u0005c\u0003B!a\u0018\u00034\u00129\u00111\r\u001cC\u0002\u0005\u0015\u0004\"\u000227\u0001\u0004!\u0007\"B67\u0001\u0004i\u0007\"\u0002<7\u0001\u0004A\bBBA\u0002m\u0001\u0007\u0001\u0010C\u0004\u0002\bY\u0002\r!a\u0003\t\u0013\u0005Ma\u0007%AA\u0002\u0005]\u0001\"CA\u0013mA\u0005\t\u0019AA\u0015\u0011%\t\tD\u000eI\u0001\u0002\u0004\t)\u0004C\u0005\u0002>Y\u0002\n\u00111\u0001\u0002B\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$c'\u0006\u0003\u0003,\t-GaBA2o\t\u0007\u0011QM\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%oU!!Q\u0007Bi\t\u001d\t\u0019\u0007\u000fb\u0001\u0003K\nq\"\u00199qYf$C-\u001a4bk2$H\u0005O\u000b\u0005\u0005\u007f\u00119\u000eB\u0004\u0002de\u0012\r!!\u001a\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIe*BA!\u0013\u0003^\u00129\u00111\r\u001eC\u0002\u0005\u0015\u0014aB;oCB\u0004H._\u000b\u0005\u0005G\u0014)\u0010\u0006\u0003\u0003f\n5\b#\u0002&\u0002\u001a\t\u001d\b#\u0005&\u0003j\u0012l\u0007\u0010_A\u0006\u0003/\tI#!\u000e\u0002B%\u0019!1^&\u0003\rQ+\b\u000f\\3:\u0011%\u0011yoOA\u0001\u0002\u0004\u0011\t0A\u0002yIA\u0002B\u0001\u0015\u0001\u0003tB!\u0011q\fB{\t\u001d\t\u0019g\u000fb\u0001\u0003K\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u00122T\u0003\u0002B\u0016\u0005w$q!a\u0019=\u0005\u0004\t)'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HeN\u000b\u0005\u0005k\u0019\t\u0001B\u0004\u0002du\u0012\r!!\u001a\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00139+\u0011\u0011yda\u0002\u0005\u000f\u0005\rdH1\u0001\u0002f\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIe*BA!\u0013\u0004\u000e\u00119\u00111M C\u0002\u0005\u0015\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAB\n!\u0011\u0011)f!\u0006\n\t\r]!q\u000b\u0002\u0007\u001f\nTWm\u0019;")
public final class BackoffOnStopOptionsImpl<T>
implements BackoffOnStopOptions,
Product,
Serializable {
    private final Props childProps;
    private final String childName;
    private final FiniteDuration minBackoff;
    private final FiniteDuration maxBackoff;
    private final double randomFactor;
    private final Option<BackoffReset> reset;
    private final OneForOneStrategy supervisorStrategy;
    private final HandlingWhileStopped handlingWhileStopped;
    private final Option<Function1<Object, Object>> finalStopMessage;
    private final BackoffReset backoffReset;

    public static <T> Option<Function1<Object, Object>> $lessinit$greater$default$9() {
        return BackoffOnStopOptionsImpl$.MODULE$.$lessinit$greater$default$9();
    }

    public static <T> HandlingWhileStopped $lessinit$greater$default$8() {
        return BackoffOnStopOptionsImpl$.MODULE$.$lessinit$greater$default$8();
    }

    public static <T> OneForOneStrategy $lessinit$greater$default$7() {
        return BackoffOnStopOptionsImpl$.MODULE$.$lessinit$greater$default$7();
    }

    public static <T> Option<BackoffReset> $lessinit$greater$default$6() {
        return BackoffOnStopOptionsImpl$.MODULE$.$lessinit$greater$default$6();
    }

    public static <T> Option<Tuple9<Props, String, FiniteDuration, FiniteDuration, Object, Option<BackoffReset>, OneForOneStrategy, HandlingWhileStopped, Option<Function1<Object, Object>>>> unapply(BackoffOnStopOptionsImpl<T> x$0) {
        return BackoffOnStopOptionsImpl$.MODULE$.unapply(x$0);
    }

    public static <T> Option<Function1<Object, Object>> apply$default$9() {
        return BackoffOnStopOptionsImpl$.MODULE$.apply$default$9();
    }

    public static <T> HandlingWhileStopped apply$default$8() {
        return BackoffOnStopOptionsImpl$.MODULE$.apply$default$8();
    }

    public static <T> OneForOneStrategy apply$default$7() {
        return BackoffOnStopOptionsImpl$.MODULE$.apply$default$7();
    }

    public static <T> Option<BackoffReset> apply$default$6() {
        return BackoffOnStopOptionsImpl$.MODULE$.apply$default$6();
    }

    public static <T> BackoffOnStopOptionsImpl<T> apply(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Option<BackoffReset> reset, OneForOneStrategy supervisorStrategy, HandlingWhileStopped handlingWhileStopped, Option<Function1<Object, Object>> finalStopMessage) {
        return BackoffOnStopOptionsImpl$.MODULE$.apply(childProps, childName, minBackoff, maxBackoff, randomFactor, reset, supervisorStrategy, handlingWhileStopped, finalStopMessage);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Props childProps() {
        return this.childProps;
    }

    public String childName() {
        return this.childName;
    }

    public FiniteDuration minBackoff() {
        return this.minBackoff;
    }

    public FiniteDuration maxBackoff() {
        return this.maxBackoff;
    }

    public double randomFactor() {
        return this.randomFactor;
    }

    public Option<BackoffReset> reset() {
        return this.reset;
    }

    public OneForOneStrategy supervisorStrategy() {
        return this.supervisorStrategy;
    }

    public HandlingWhileStopped handlingWhileStopped() {
        return this.handlingWhileStopped;
    }

    public Option<Function1<Object, Object>> finalStopMessage() {
        return this.finalStopMessage;
    }

    private BackoffReset backoffReset() {
        return this.backoffReset;
    }

    @Override
    public BackoffOnStopOptionsImpl<Nothing$> withAutoReset(FiniteDuration resetBackoff) {
        Some<BackoffReset> x$1 = new Some<BackoffReset>(new AutoReset(resetBackoff));
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        OneForOneStrategy x$7 = this.copy$default$7();
        HandlingWhileStopped x$8 = this.copy$default$8();
        Option<Function1<Object, Object>> x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7, x$8, x$9);
    }

    @Override
    public BackoffOnStopOptionsImpl<Nothing$> withManualReset() {
        Some<BackoffReset> x$1 = new Some<BackoffReset>(ManualReset$.MODULE$);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        OneForOneStrategy x$7 = this.copy$default$7();
        HandlingWhileStopped x$8 = this.copy$default$8();
        Option<Function1<Object, Object>> x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7, x$8, x$9);
    }

    @Override
    public BackoffOnStopOptionsImpl<Nothing$> withSupervisorStrategy(OneForOneStrategy supervisorStrategy) {
        OneForOneStrategy x$1 = supervisorStrategy;
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        HandlingWhileStopped x$8 = this.copy$default$8();
        Option<Function1<Object, Object>> x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8, x$9);
    }

    @Override
    public BackoffOnStopOptionsImpl<Nothing$> withReplyWhileStopped(Object replyWhileStopped) {
        ReplyWith x$1 = new ReplyWith(replyWhileStopped);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        OneForOneStrategy x$8 = this.copy$default$7();
        Option<Function1<Object, Object>> x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$1, x$9);
    }

    @Override
    public BackoffOnStopOptionsImpl<Nothing$> withHandlerWhileStopped(ActorRef handlerWhileStopped) {
        ForwardTo x$1 = new ForwardTo(handlerWhileStopped);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        OneForOneStrategy x$8 = this.copy$default$7();
        Option<Function1<Object, Object>> x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$1, x$9);
    }

    @Override
    public BackoffOnStopOptionsImpl<Nothing$> withMaxNrOfRetries(int maxNrOfRetries) {
        OneForOneStrategy x$1 = this.supervisorStrategy().withMaxNrOfRetries(maxNrOfRetries);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        HandlingWhileStopped x$8 = this.copy$default$8();
        Option<Function1<Object, Object>> x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8, x$9);
    }

    @Override
    public BackoffOnStopOptionsImpl<Nothing$> withDefaultStoppingStrategy() {
        OneForOneStrategy x$1 = new OneForOneStrategy(this.supervisorStrategy().maxNrOfRetries(), OneForOneStrategy$.MODULE$.apply$default$2(), OneForOneStrategy$.MODULE$.apply$default$3(), SupervisorStrategy$.MODULE$.stoppingStrategy().decider());
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        HandlingWhileStopped x$8 = this.copy$default$8();
        Option<Function1<Object, Object>> x$9 = this.copy$default$9();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8, x$9);
    }

    @Override
    public BackoffOnStopOptionsImpl<Nothing$> withFinalStopMessage(Function1<Object, Object> action) {
        Some<Function1<Object, Object>> x$1 = new Some<Function1<Object, Object>>(action);
        Props x$2 = this.copy$default$1();
        String x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        double x$6 = this.copy$default$5();
        Option<BackoffReset> x$7 = this.copy$default$6();
        OneForOneStrategy x$8 = this.copy$default$7();
        HandlingWhileStopped x$9 = this.copy$default$8();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$1);
    }

    @Override
    public Props props() {
        block0: {
            Predef$.MODULE$.require(this.minBackoff().$greater(Duration$.MODULE$.Zero()), (Function0<Object>)(Function0<String> & Serializable)() -> "minBackoff must be > 0");
            Predef$.MODULE$.require(this.maxBackoff().$greater$eq(this.minBackoff()), (Function0<Object>)(Function0<String> & Serializable)() -> "maxBackoff must be >= minBackoff");
            Predef$.MODULE$.require(0.0 <= this.randomFactor() && this.randomFactor() <= 1.0, (Function0<Object>)(Function0<String> & Serializable)() -> "randomFactor must be between 0.0 and 1.0");
            BackoffReset backoffReset = this.backoffReset();
            if (!(backoffReset instanceof AutoReset)) break block0;
            AutoReset autoReset = (AutoReset)backoffReset;
            FiniteDuration resetBackoff = autoReset.resetBackoff();
            Predef$.MODULE$.require(this.minBackoff().$less$eq(resetBackoff) && resetBackoff.$less$eq(this.maxBackoff()));
        }
        return Props$.MODULE$.apply((Function0<BackoffOnStopSupervisor> & Serializable)() -> new BackoffOnStopSupervisor(this.childProps(), this.childName(), this.minBackoff(), this.maxBackoff(), this.backoffReset(), this.randomFactor(), this.supervisorStrategy(), this.handlingWhileStopped(), this.finalStopMessage()), ClassTag$.MODULE$.apply(BackoffOnStopSupervisor.class));
    }

    public <T> BackoffOnStopOptionsImpl<T> copy(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Option<BackoffReset> reset, OneForOneStrategy supervisorStrategy, HandlingWhileStopped handlingWhileStopped, Option<Function1<Object, Object>> finalStopMessage) {
        return new BackoffOnStopOptionsImpl<T>(childProps, childName, minBackoff, maxBackoff, randomFactor, reset, supervisorStrategy, handlingWhileStopped, finalStopMessage);
    }

    public <T> Props copy$default$1() {
        return this.childProps();
    }

    public <T> String copy$default$2() {
        return this.childName();
    }

    public <T> FiniteDuration copy$default$3() {
        return this.minBackoff();
    }

    public <T> FiniteDuration copy$default$4() {
        return this.maxBackoff();
    }

    public <T> double copy$default$5() {
        return this.randomFactor();
    }

    public <T> Option<BackoffReset> copy$default$6() {
        return this.reset();
    }

    public <T> OneForOneStrategy copy$default$7() {
        return this.supervisorStrategy();
    }

    public <T> HandlingWhileStopped copy$default$8() {
        return this.handlingWhileStopped();
    }

    public <T> Option<Function1<Object, Object>> copy$default$9() {
        return this.finalStopMessage();
    }

    @Override
    public String productPrefix() {
        return "BackoffOnStopOptionsImpl";
    }

    @Override
    public int productArity() {
        return 9;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.childProps();
            }
            case 1: {
                return this.childName();
            }
            case 2: {
                return this.minBackoff();
            }
            case 3: {
                return this.maxBackoff();
            }
            case 4: {
                return BoxesRunTime.boxToDouble(this.randomFactor());
            }
            case 5: {
                return this.reset();
            }
            case 6: {
                return this.supervisorStrategy();
            }
            case 7: {
                return this.handlingWhileStopped();
            }
            case 8: {
                return this.finalStopMessage();
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
        return x$1 instanceof BackoffOnStopOptionsImpl;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "childProps";
            }
            case 1: {
                return "childName";
            }
            case 2: {
                return "minBackoff";
            }
            case 3: {
                return "maxBackoff";
            }
            case 4: {
                return "randomFactor";
            }
            case 5: {
                return "reset";
            }
            case 6: {
                return "supervisorStrategy";
            }
            case 7: {
                return "handlingWhileStopped";
            }
            case 8: {
                return "finalStopMessage";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.childProps()));
        n = Statics.mix(n, Statics.anyHash(this.childName()));
        n = Statics.mix(n, Statics.anyHash(this.minBackoff()));
        n = Statics.mix(n, Statics.anyHash(this.maxBackoff()));
        n = Statics.mix(n, Statics.doubleHash(this.randomFactor()));
        n = Statics.mix(n, Statics.anyHash(this.reset()));
        n = Statics.mix(n, Statics.anyHash(this.supervisorStrategy()));
        n = Statics.mix(n, Statics.anyHash(this.handlingWhileStopped()));
        n = Statics.mix(n, Statics.anyHash(this.finalStopMessage()));
        return Statics.finalizeHash(n, 9);
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
        if (!(object instanceof BackoffOnStopOptionsImpl)) return false;
        boolean bl = true;
        if (!bl) return false;
        BackoffOnStopOptionsImpl backoffOnStopOptionsImpl = (BackoffOnStopOptionsImpl)x$1;
        if (this.randomFactor() != backoffOnStopOptionsImpl.randomFactor()) return false;
        Props props = this.childProps();
        Props props2 = backoffOnStopOptionsImpl.childProps();
        if (props == null) {
            if (props2 != null) {
                return false;
            }
        } else if (!((Object)props).equals(props2)) return false;
        String string = this.childName();
        String string2 = backoffOnStopOptionsImpl.childName();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        FiniteDuration finiteDuration = this.minBackoff();
        FiniteDuration finiteDuration2 = backoffOnStopOptionsImpl.minBackoff();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        FiniteDuration finiteDuration3 = this.maxBackoff();
        FiniteDuration finiteDuration4 = backoffOnStopOptionsImpl.maxBackoff();
        if (finiteDuration3 == null) {
            if (finiteDuration4 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration3).equals(finiteDuration4)) return false;
        Option<BackoffReset> option = this.reset();
        Option<BackoffReset> option2 = backoffOnStopOptionsImpl.reset();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        OneForOneStrategy oneForOneStrategy = this.supervisorStrategy();
        OneForOneStrategy oneForOneStrategy2 = backoffOnStopOptionsImpl.supervisorStrategy();
        if (oneForOneStrategy == null) {
            if (oneForOneStrategy2 != null) {
                return false;
            }
        } else if (!((Object)oneForOneStrategy).equals(oneForOneStrategy2)) return false;
        HandlingWhileStopped handlingWhileStopped = this.handlingWhileStopped();
        HandlingWhileStopped handlingWhileStopped2 = backoffOnStopOptionsImpl.handlingWhileStopped();
        if (handlingWhileStopped == null) {
            if (handlingWhileStopped2 != null) {
                return false;
            }
        } else if (!handlingWhileStopped.equals(handlingWhileStopped2)) return false;
        Option<Function1<Object, Object>> option3 = this.finalStopMessage();
        Option<Function1<Object, Object>> option4 = backoffOnStopOptionsImpl.finalStopMessage();
        if (option3 == null) {
            if (option4 == null) return true;
            return false;
        } else {
            if (!option3.equals(option4)) return false;
            return true;
        }
    }

    public BackoffOnStopOptionsImpl(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Option<BackoffReset> reset, OneForOneStrategy supervisorStrategy, HandlingWhileStopped handlingWhileStopped, Option<Function1<Object, Object>> finalStopMessage) {
        this.childProps = childProps;
        this.childName = childName;
        this.minBackoff = minBackoff;
        this.maxBackoff = maxBackoff;
        this.randomFactor = randomFactor;
        this.reset = reset;
        this.supervisorStrategy = supervisorStrategy;
        this.handlingWhileStopped = handlingWhileStopped;
        this.finalStopMessage = finalStopMessage;
        Product.$init$(this);
        this.backoffReset = (BackoffReset)reset.getOrElse((Function0<AutoReset> & Serializable)() -> new AutoReset(this.minBackoff()));
    }
}

