/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.ActorRefWithCell;
import akka.actor.Cell;
import akka.annotation.InternalApi;
import akka.routing.ActorRefRoutee;
import akka.routing.DefaultOptimalSizeExploringResizer$;
import akka.routing.OptimalSizeExploringResizer;
import akka.routing.OptimalSizeExploringResizer$ResizeRecord$;
import akka.routing.Routee;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$ScalaDurationOps$;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Some;
import scala.Tuple10;
import scala.Tuple2;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.SeqOps;
import scala.collection.StringOps$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.Duration$DurationIsOrdered$;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Numeric$IntIsIntegral$;
import scala.math.Ordering$Int$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r\u001dd\u0001\u0002.\\\u0001\u0002D\u0001B\u001f\u0001\u0003\u0016\u0004%\ta\u001f\u0005\n\u0003\u000f\u0001!\u0011#Q\u0001\nqD\u0011\"!\u0003\u0001\u0005+\u0007I\u0011A>\t\u0013\u0005-\u0001A!E!\u0002\u0013a\bBCA\u0007\u0001\tU\r\u0011\"\u0001\u0002\u0010!Q\u0011q\u0003\u0001\u0003\u0012\u0003\u0006I!!\u0005\t\u0015\u0005e\u0001A!f\u0001\n\u0003\tY\u0002\u0003\u0006\u0002.\u0001\u0011\t\u0012)A\u0005\u0003;A!\"a\f\u0001\u0005+\u0007I\u0011AA\u0019\u0011)\tI\u0004\u0001B\tB\u0003%\u00111\u0007\u0005\u000b\u0003w\u0001!Q3A\u0005\u0002\u0005=\u0001BCA\u001f\u0001\tE\t\u0015!\u0003\u0002\u0012!Q\u0011q\b\u0001\u0003\u0016\u0004%\t!a\u0004\t\u0015\u0005\u0005\u0003A!E!\u0002\u0013\t\t\u0002\u0003\u0006\u0002D\u0001\u0011)\u001a!C\u0001\u00037A!\"!\u0012\u0001\u0005#\u0005\u000b\u0011BA\u000f\u0011)\t9\u0005\u0001BK\u0002\u0013\u0005\u0011q\u0002\u0005\u000b\u0003\u0013\u0002!\u0011#Q\u0001\n\u0005E\u0001BCA&\u0001\tU\r\u0011\"\u0001\u0002\u0010!Q\u0011Q\n\u0001\u0003\u0012\u0003\u0006I!!\u0005\t\u000f\u0005=\u0003\u0001\"\u0001\u0002R!Q\u0011\u0011\u000e\u0001A\u0002\u0013\u00051,a\u001b\t\u0015\u0005M\u0004\u00011A\u0005\u0002m\u000b)\b\u0003\u0005\u0002\u0002\u0002\u0001\u000b\u0015BA7\u0011)\t\t\n\u0001a\u0001\n\u0003Y\u00161\u0013\u0005\u000b\u00037\u0003\u0001\u0019!C\u00017\u0006u\u0005\u0002CAQ\u0001\u0001\u0006K!!&\t\u0015\u0005\u0015\u0006\u00011A\u0005\u0002m\u000b9\u000b\u0003\u0006\u00020\u0002\u0001\r\u0011\"\u0001\\\u0003cC\u0001\"!.\u0001A\u0003&\u0011\u0011\u0016\u0005\b\u0003s\u0003A\u0011BA^\u0011\u001d\ty\r\u0001C\u0005\u0003#Dq!a;\u0001\t\u0013\ti\u000fC\u0004\u0002t\u0002!I!!>\t\u0013\u0005u\bA1A\u0005\n\u0005}\b\u0002\u0003B\u0004\u0001\u0001\u0006IA!\u0001\t\u000f\t%\u0001\u0001\"\u0001\u0003\f!9!\u0011\u0003\u0001\u0005\u0002\tM\u0001\u0002\u0003B\u0019\u0001\u0011\u00051La\r\t\u000f\t}\u0002\u0001\"\u0001\u0003B!9!Q\t\u0001\u0005\n\t\u001d\u0003b\u0002B'\u0001\u0011%!q\n\u0005\n\u0005'\u0002\u0011\u0011!C\u0001\u0005+B\u0011Ba\u001b\u0001#\u0003%\tA!\u001c\t\u0013\t\u0005\u0005!%A\u0005\u0002\t5\u0004\"\u0003BB\u0001E\u0005I\u0011\u0001BC\u0011%\u0011I\tAI\u0001\n\u0003\u0011Y\tC\u0005\u0003\u0010\u0002\t\n\u0011\"\u0001\u0003\u0012\"I!Q\u0013\u0001\u0012\u0002\u0013\u0005!Q\u0011\u0005\n\u0005/\u0003\u0011\u0013!C\u0001\u0005\u000bC\u0011B!'\u0001#\u0003%\tAa#\t\u0013\tm\u0005!%A\u0005\u0002\t\u0015\u0005\"\u0003BO\u0001E\u0005I\u0011\u0001BC\u0011%\u0011y\nAA\u0001\n\u0003\u0012\t\u000bC\u0005\u0003.\u0002\t\t\u0011\"\u0001\u00022!I!q\u0016\u0001\u0002\u0002\u0013\u0005!\u0011\u0017\u0005\n\u0005w\u0003\u0011\u0011!C!\u0005{C\u0011Ba2\u0001\u0003\u0003%\tA!3\t\u0013\t5\u0007!!A\u0005B\t=\u0007\"\u0003Bj\u0001\u0005\u0005I\u0011\tBk\u0011%\u00119\u000eAA\u0001\n\u0003\u0012I\u000eC\u0005\u0003\\\u0002\t\t\u0011\"\u0011\u0003^\u001eI!1^.\u0002\u0002#\u0005!Q\u001e\u0004\t5n\u000b\t\u0011#\u0001\u0003p\"9\u0011q\n!\u0005\u0002\r\u001d\u0001\"\u0003Bl\u0001\u0006\u0005IQ\tBm\u0011%\u0019I\u0001QA\u0001\n\u0003\u001bY\u0001C\u0005\u0004\"\u0001\u000b\n\u0011\"\u0001\u0003n!I11\u0005!\u0012\u0002\u0013\u0005!Q\u000e\u0005\n\u0007K\u0001\u0015\u0013!C\u0001\u0005\u000bC\u0011ba\nA#\u0003%\tAa#\t\u0013\r%\u0002)%A\u0005\u0002\tE\u0005\"CB\u0016\u0001F\u0005I\u0011\u0001BC\u0011%\u0019i\u0003QI\u0001\n\u0003\u0011)\tC\u0005\u00040\u0001\u000b\n\u0011\"\u0001\u0003\f\"I1\u0011\u0007!\u0012\u0002\u0013\u0005!Q\u0011\u0005\n\u0007g\u0001\u0015\u0013!C\u0001\u0005\u000bC\u0011b!\u000eA\u0003\u0003%\tia\u000e\t\u0013\r%\u0003)%A\u0005\u0002\t5\u0004\"CB&\u0001F\u0005I\u0011\u0001B7\u0011%\u0019i\u0005QI\u0001\n\u0003\u0011)\tC\u0005\u0004P\u0001\u000b\n\u0011\"\u0001\u0003\f\"I1\u0011\u000b!\u0012\u0002\u0013\u0005!\u0011\u0013\u0005\n\u0007'\u0002\u0015\u0013!C\u0001\u0005\u000bC\u0011b!\u0016A#\u0003%\tA!\"\t\u0013\r]\u0003)%A\u0005\u0002\t-\u0005\"CB-\u0001F\u0005I\u0011\u0001BC\u0011%\u0019Y\u0006QI\u0001\n\u0003\u0011)\tC\u0005\u0004^\u0001\u000b\t\u0011\"\u0003\u0004`\t\u0011C)\u001a4bk2$x\n\u001d;j[\u0006d7+\u001b>f\u000bb\u0004Hn\u001c:j]\u001e\u0014Vm]5{KJT!\u0001X/\u0002\u000fI|W\u000f^5oO*\ta,\u0001\u0003bW.\f7\u0001A\n\u0006\u0001\u0005<7N\u001c\t\u0003E\u0016l\u0011a\u0019\u0006\u0002I\u0006)1oY1mC&\u0011am\u0019\u0002\u0007\u0003:L(+\u001a4\u0011\u0005!LW\"A.\n\u0005)\\&aG(qi&l\u0017\r\\*ju\u0016,\u0005\u0010\u001d7pe&twMU3tSj,'\u000f\u0005\u0002cY&\u0011Qn\u0019\u0002\b!J|G-^2u!\tywO\u0004\u0002qk:\u0011\u0011\u000f^\u0007\u0002e*\u00111oX\u0001\u0007yI|w\u000e\u001e \n\u0003\u0011L!A^2\u0002\u000fA\f7m[1hK&\u0011\u00010\u001f\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003m\u000e\f!\u0002\\8xKJ\u0014u.\u001e8e+\u0005a\bcA?\u0002\u00029\u0011\u0001N`\u0005\u0003\u007fn\u000b1d\u00149uS6\fGnU5{K\u0016C\b\u000f\\8sS:<'+Z:ju\u0016\u0014\u0018\u0002BA\u0002\u0003\u000b\u0011\u0001\u0002U8pYNK'0\u001a\u0006\u0003\u007fn\u000b1\u0002\\8xKJ\u0014u.\u001e8eA\u0005QQ\u000f\u001d9fe\n{WO\u001c3\u0002\u0017U\u0004\b/\u001a:C_VtG\rI\u0001\u001cG\"\fgnY3PMN\u001b\u0017\r\\5oO\u0012{wO\\,iK:4U\u000f\u001c7\u0016\u0005\u0005E\u0001c\u00012\u0002\u0014%\u0019\u0011QC2\u0003\r\u0011{WO\u00197f\u0003q\u0019\u0007.\u00198dK>37kY1mS:<Gi\\<o/\",gNR;mY\u0002\na\"Y2uS>t\u0017J\u001c;feZ\fG.\u0006\u0002\u0002\u001eA!\u0011qDA\u0015\u001b\t\t\tC\u0003\u0003\u0002$\u0005\u0015\u0012\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0007\u0005\u001d2-\u0001\u0006d_:\u001cWO\u001d:f]RLA!a\u000b\u0002\"\tAA)\u001e:bi&|g.A\bbGRLwN\\%oi\u0016\u0014h/\u00197!\u00039rW/\\(g\u0003\u0012T\u0017mY3oiNK'0Z:U_\u000e{gn]5eKJ$UO]5oO>\u0003H/[7ju\u0006$\u0018n\u001c8\u0016\u0005\u0005M\u0002c\u00012\u00026%\u0019\u0011qG2\u0003\u0007%sG/A\u0018ok6|e-\u00113kC\u000e,g\u000e^*ju\u0016\u001cHk\\\"p]NLG-\u001a:EkJLgnZ(qi&l\u0017N_1uS>t\u0007%A\bfqBdwN]3Ti\u0016\u00048+\u001b>f\u0003A)\u0007\u0010\u001d7pe\u0016\u001cF/\u001a9TSj,\u0007%A\u0007e_^t7/\u001b>f%\u0006$\u0018n\\\u0001\u000fI><hn]5{KJ\u000bG/[8!\u0003u!wn\u001e8tSj,\u0017I\u001a;feVsG-\u001a:vi&d\u0017N_3e\r>\u0014\u0018A\b3po:\u001c\u0018N_3BMR,'/\u00168eKJ,H/\u001b7ju\u0016$gi\u001c:!\u0003Y)\u0007\u0010\u001d7pe\u0006$\u0018n\u001c8Qe>\u0014\u0017MY5mSRL\u0018aF3ya2|'/\u0019;j_:\u0004&o\u001c2bE&d\u0017\u000e^=!\u0003Q9X-[4ii>3G*\u0019;fgRlU\r\u001e:jG\u0006)r/Z5hQR|e\rT1uKN$X*\u001a;sS\u000e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\f\u0002T\u0005U\u0013qKA-\u00037\ni&a\u0018\u0002b\u0005\r\u0014QMA4!\tA\u0007\u0001C\u0004{+A\u0005\t\u0019\u0001?\t\u0011\u0005%Q\u0003%AA\u0002qD\u0011\"!\u0004\u0016!\u0003\u0005\r!!\u0005\t\u0013\u0005eQ\u0003%AA\u0002\u0005u\u0001\"CA\u0018+A\u0005\t\u0019AA\u001a\u0011%\tY$\u0006I\u0001\u0002\u0004\t\t\u0002C\u0005\u0002@U\u0001\n\u00111\u0001\u0002\u0012!I\u00111I\u000b\u0011\u0002\u0003\u0007\u0011Q\u0004\u0005\n\u0003\u000f*\u0002\u0013!a\u0001\u0003#A\u0011\"a\u0013\u0016!\u0003\u0005\r!!\u0005\u0002\u001dA,'OZ8s[\u0006t7-\u001a'pOV\u0011\u0011Q\u000e\t\u0004{\u0006=\u0014\u0002BA9\u0003\u000b\u0011a\u0002U3sM>\u0014X.\u00198dK2{w-\u0001\nqKJ4wN]7b]\u000e,Gj\\4`I\u0015\fH\u0003BA<\u0003{\u00022AYA=\u0013\r\tYh\u0019\u0002\u0005+:LG\u000fC\u0005\u0002\u0000]\t\t\u00111\u0001\u0002n\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA,'OZ8s[\u0006t7-\u001a'pO\u0002B3\u0001GAC!\u0011\t9)!$\u000e\u0005\u0005%%bAAF;\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005=\u0015\u0011\u0012\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\u0004sK\u000e|'\u000fZ\u000b\u0003\u0003+\u00032!`AL\u0013\u0011\tI*!\u0002\u0003\u0019I+7/\u001b>f%\u0016\u001cwN\u001d3\u0002\u0015I,7m\u001c:e?\u0012*\u0017\u000f\u0006\u0003\u0002x\u0005}\u0005\"CA@5\u0005\u0005\t\u0019AAK\u0003\u001d\u0011XmY8sI\u0002B3aGAC\u00035\u0019Ho\u001c9FqBdwN]5oOV\u0011\u0011\u0011\u0016\t\u0004E\u0006-\u0016bAAWG\n9!i\\8mK\u0006t\u0017!E:u_B,\u0005\u0010\u001d7pe&twm\u0018\u0013fcR!\u0011qOAZ\u0011%\ty(HA\u0001\u0002\u0004\tI+\u0001\bti>\u0004X\t\u001f9m_JLgn\u001a\u0011)\u0007y\t))\u0001\u0004sC:$w.\\\u000b\u0003\u0003{\u0003B!a0\u0002L6\u0011\u0011\u0011\u0019\u0006\u0005\u0003O\t\u0019M\u0003\u0003\u0002F\u0006\u001d\u0017\u0001B;uS2T!!!3\u0002\t)\fg/Y\u0005\u0005\u0003\u001b\f\tMA\tUQJ,\u0017\r\u001a'pG\u0006d'+\u00198e_6\fqc\u00195fG.\u0004\u0016M]1n\u0003N\u0004&o\u001c2bE&d\u0017\u000e^=\u0015\r\u0005]\u00141[Al\u0011\u001d\t)\u000e\ta\u0001\u0003#\tQA^1mk\u0016Dq!!7!\u0001\u0004\tY.A\u0005qCJ\fWNT1nKB!\u0011Q\\As\u001d\u0011\ty.!9\u0011\u0005E\u001c\u0017bAArG\u00061\u0001K]3eK\u001aLA!a:\u0002j\n11\u000b\u001e:j]\u001eT1!a9d\u0003]\u0019\u0007.Z2l!\u0006\u0014\u0018-\\!t!>\u001c\u0018\u000e^5wK:+X\u000e\u0006\u0004\u0002x\u0005=\u0018\u0011\u001f\u0005\b\u0003+\f\u0003\u0019AA\t\u0011\u001d\tI.\ta\u0001\u00037\fAc\u00195fG.\u0004\u0016M]1n\u0019><XM\u001d\"pk:$G\u0003CA<\u0003o\fI0a?\t\u000f\u0005U'\u00051\u0001\u0002\u0012!1!P\ta\u0001\u0003#Aq!!7#\u0001\u0004\tY.A\nbGRLwN\\%oi\u0016\u0014h.\u00197OC:|7/\u0006\u0002\u0003\u0002A\u0019!Ma\u0001\n\u0007\t\u00151M\u0001\u0003M_:<\u0017\u0001F1di&|g.\u00138uKJt\u0017\r\u001c(b]>\u001c\b%A\bjgRKW.\u001a$peJ+7/\u001b>f)\u0011\tIK!\u0004\t\u000f\t=Q\u00051\u0001\u0003\u0002\u0005qQ.Z:tC\u001e,7i\\;oi\u0016\u0014\u0018A\u0005:fa>\u0014H/T3tg\u0006<WmQ8v]R$b!a\u001e\u0003\u0016\t=\u0002b\u0002B\fM\u0001\u0007!\u0011D\u0001\u000fGV\u0014(/\u001a8u%>,H/Z3t!\u0019\u0011YB!\n\u0003*5\u0011!Q\u0004\u0006\u0005\u0005?\u0011\t#A\u0005j[6,H/\u00192mK*\u0019!1E2\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0003(\tu!AC%oI\u0016DX\rZ*fcB\u0019\u0001Na\u000b\n\u0007\t52L\u0001\u0004S_V$X-\u001a\u0005\b\u0005\u001f1\u0003\u0019\u0001B\u0001\u00031)\b\u000fZ1uK\u0012\u001cF/\u0019;t)\u0019\u0011)Da\u000f\u0003>A9!Ma\u000e\u0002n\u0005U\u0015b\u0001B\u001dG\n1A+\u001e9mKJBqAa\u0006(\u0001\u0004\u0011I\u0002C\u0004\u0003\u0010\u001d\u0002\rA!\u0001\u0002\rI,7/\u001b>f)\u0011\t\u0019Da\u0011\t\u000f\t]\u0001\u00061\u0001\u0003\u001a\u0005Aq\u000e\u001d;j[&TX\r\u0006\u0003\u00024\t%\u0003B\u0002B&S\u0001\u0007A0A\u0006dkJ\u0014XM\u001c;TSj,\u0017aB3ya2|'/\u001a\u000b\u0005\u0003g\u0011\t\u0006\u0003\u0004\u0003L)\u0002\r\u0001`\u0001\u0005G>\u0004\u0018\u0010\u0006\f\u0002T\t]#\u0011\fB.\u0005;\u0012yF!\u0019\u0003d\t\u0015$q\rB5\u0011\u001dQ8\u0006%AA\u0002qD\u0001\"!\u0003,!\u0003\u0005\r\u0001 \u0005\n\u0003\u001bY\u0003\u0013!a\u0001\u0003#A\u0011\"!\u0007,!\u0003\u0005\r!!\b\t\u0013\u0005=2\u0006%AA\u0002\u0005M\u0002\"CA\u001eWA\u0005\t\u0019AA\t\u0011%\tyd\u000bI\u0001\u0002\u0004\t\t\u0002C\u0005\u0002D-\u0002\n\u00111\u0001\u0002\u001e!I\u0011qI\u0016\u0011\u0002\u0003\u0007\u0011\u0011\u0003\u0005\n\u0003\u0017Z\u0003\u0013!a\u0001\u0003#\tabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0003p)\u001aAP!\u001d,\u0005\tM\u0004\u0003\u0002B;\u0005{j!Aa\u001e\u000b\t\te$1P\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a#d\u0013\u0011\u0011yHa\u001e\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011!q\u0011\u0016\u0005\u0003#\u0011\t(\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\t5%\u0006BA\u000f\u0005c\nabY8qs\u0012\"WMZ1vYR$S'\u0006\u0002\u0003\u0014*\"\u00111\u0007B9\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIY\nabY8qs\u0012\"WMZ1vYR$s'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001d\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%s\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\n\u0004'A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0005G\u0003BA!*\u0003,6\u0011!q\u0015\u0006\u0005\u0005S\u000b9-\u0001\u0003mC:<\u0017\u0002BAt\u0005O\u000bA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u00034\ne\u0006c\u00012\u00036&\u0019!qW2\u0003\u0007\u0005s\u0017\u0010C\u0005\u0002\u0000a\n\t\u00111\u0001\u00024\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0003@B1!\u0011\u0019Bb\u0005gk!A!\t\n\t\t\u0015'\u0011\u0005\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002*\n-\u0007\"CA@u\u0005\u0005\t\u0019\u0001BZ\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\t\r&\u0011\u001b\u0005\n\u0003\u007fZ\u0014\u0011!a\u0001\u0003g\t\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003g\t\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005G\u000ba!Z9vC2\u001cH\u0003BAU\u0005?D\u0011\"a ?\u0003\u0003\u0005\rAa-)\u000f\u0001\u0011\u0019/!6\u0003jB\u0019!M!:\n\u0007\t\u001d8M\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\tz\t\u0011!\u0001\u0012EK\u001a\fW\u000f\u001c;PaRLW.\u00197TSj,W\t\u001f9m_JLgn\u001a*fg&TXM\u001d\t\u0003Q\u0002\u001bR\u0001\u0011By\u0005{\u0004\u0002Da=\u0003zrd\u0018\u0011CA\u000f\u0003g\t\t\"!\u0005\u0002\u001e\u0005E\u0011\u0011CA*\u001b\t\u0011)PC\u0002\u0003x\u000e\fqA];oi&lW-\u0003\u0003\u0003|\nU(AE!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA\u0002BAa@\u0004\u00065\u00111\u0011\u0001\u0006\u0005\u0007\u0007\t9-\u0001\u0002j_&\u0019\u0001p!\u0001\u0015\u0005\t5\u0018!B1qa2LHCFA*\u0007\u001b\u0019ya!\u0005\u0004\u0014\rU1qCB\r\u00077\u0019iba\b\t\u000fi\u001c\u0005\u0013!a\u0001y\"A\u0011\u0011B\"\u0011\u0002\u0003\u0007A\u0010C\u0005\u0002\u000e\r\u0003\n\u00111\u0001\u0002\u0012!I\u0011\u0011D\"\u0011\u0002\u0003\u0007\u0011Q\u0004\u0005\n\u0003_\u0019\u0005\u0013!a\u0001\u0003gA\u0011\"a\u000fD!\u0003\u0005\r!!\u0005\t\u0013\u0005}2\t%AA\u0002\u0005E\u0001\"CA\"\u0007B\u0005\t\u0019AA\u000f\u0011%\t9e\u0011I\u0001\u0002\u0004\t\t\u0002C\u0005\u0002L\r\u0003\n\u00111\u0001\u0002\u0012\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$\u0013'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u001a\u0014aD1qa2LH\u0005Z3gCVdG\u000f\n\u001b\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIU\nq\"\u00199qYf$C-\u001a4bk2$HEN\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%o\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$\u0003(A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u0013:\u0003A\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\n\u0004'A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\re2Q\t\t\u0006E\u000em2qH\u0005\u0004\u0007{\u0019'AB(qi&|g\u000eE\u000bc\u0007\u0003bH0!\u0005\u0002\u001e\u0005M\u0012\u0011CA\t\u0003;\t\t\"!\u0005\n\u0007\r\r3MA\u0004UkBdW-\r\u0019\t\u0013\r\u001dc*!AA\u0002\u0005M\u0013a\u0001=%a\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$3'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H\u0005N\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001b\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00137\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%o\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIa\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012J\u0014\u0001\b\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013\u0007M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0007C\u0002BA!*\u0004d%!1Q\rBT\u0005\u0019y%M[3di\u0002")
public class DefaultOptimalSizeExploringResizer
implements OptimalSizeExploringResizer,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final int lowerBound;
    private final int upperBound;
    private final double chanceOfScalingDownWhenFull;
    private final Duration actionInterval;
    private final int numOfAdjacentSizesToConsiderDuringOptimization;
    private final double exploreStepSize;
    private final double downsizeRatio;
    private final Duration downsizeAfterUnderutilizedFor;
    private final double explorationProbability;
    private final double weightOfLatestMetric;
    @InternalApi
    private Map<Object, Duration> performanceLog;
    @InternalApi
    private OptimalSizeExploringResizer.ResizeRecord record;
    @InternalApi
    private boolean stopExploring;
    private final long actionInternalNanos;

    public static double $lessinit$greater$default$10() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$10();
    }

    public static double $lessinit$greater$default$9() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$9();
    }

    public static Duration $lessinit$greater$default$8() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$8();
    }

    public static double $lessinit$greater$default$7() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$7();
    }

    public static double $lessinit$greater$default$6() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$6();
    }

    public static int $lessinit$greater$default$5() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$5();
    }

    public static Duration $lessinit$greater$default$4() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$4();
    }

    public static double $lessinit$greater$default$3() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$3();
    }

    public static int $lessinit$greater$default$2() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$2();
    }

    public static int $lessinit$greater$default$1() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.$lessinit$greater$default$1();
    }

    public static Option<Tuple10<Object, Object, Object, Duration, Object, Object, Object, Duration, Object, Object>> unapply(DefaultOptimalSizeExploringResizer x$0) {
        return DefaultOptimalSizeExploringResizer$.MODULE$.unapply(x$0);
    }

    public static double apply$default$10() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$10();
    }

    public static double apply$default$9() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$9();
    }

    public static Duration apply$default$8() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$8();
    }

    public static double apply$default$7() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$7();
    }

    public static double apply$default$6() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$6();
    }

    public static int apply$default$5() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$5();
    }

    public static Duration apply$default$4() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$4();
    }

    public static double apply$default$3() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$3();
    }

    public static int apply$default$2() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$2();
    }

    public static int apply$default$1() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply$default$1();
    }

    public static DefaultOptimalSizeExploringResizer apply(int lowerBound, int upperBound, double chanceOfScalingDownWhenFull, Duration actionInterval, int numOfAdjacentSizesToConsiderDuringOptimization, double exploreStepSize, double downsizeRatio, Duration downsizeAfterUnderutilizedFor, double explorationProbability, double weightOfLatestMetric) {
        return DefaultOptimalSizeExploringResizer$.MODULE$.apply(lowerBound, upperBound, chanceOfScalingDownWhenFull, actionInterval, numOfAdjacentSizesToConsiderDuringOptimization, exploreStepSize, downsizeRatio, downsizeAfterUnderutilizedFor, explorationProbability, weightOfLatestMetric);
    }

    public static Function1<Tuple10<Object, Object, Object, Duration, Object, Object, Object, Duration, Object, Object>, DefaultOptimalSizeExploringResizer> tupled() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, Function1<Object, Function1<Duration, Function1<Object, Function1<Object, Function1<Object, Function1<Duration, Function1<Object, Function1<Object, DefaultOptimalSizeExploringResizer>>>>>>>>>> curried() {
        return DefaultOptimalSizeExploringResizer$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int lowerBound() {
        return this.lowerBound;
    }

    public int upperBound() {
        return this.upperBound;
    }

    public double chanceOfScalingDownWhenFull() {
        return this.chanceOfScalingDownWhenFull;
    }

    public Duration actionInterval() {
        return this.actionInterval;
    }

    public int numOfAdjacentSizesToConsiderDuringOptimization() {
        return this.numOfAdjacentSizesToConsiderDuringOptimization;
    }

    public double exploreStepSize() {
        return this.exploreStepSize;
    }

    public double downsizeRatio() {
        return this.downsizeRatio;
    }

    public Duration downsizeAfterUnderutilizedFor() {
        return this.downsizeAfterUnderutilizedFor;
    }

    public double explorationProbability() {
        return this.explorationProbability;
    }

    public double weightOfLatestMetric() {
        return this.weightOfLatestMetric;
    }

    public Map<Object, Duration> performanceLog() {
        return this.performanceLog;
    }

    public void performanceLog_$eq(Map<Object, Duration> x$1) {
        this.performanceLog = x$1;
    }

    public OptimalSizeExploringResizer.ResizeRecord record() {
        return this.record;
    }

    public void record_$eq(OptimalSizeExploringResizer.ResizeRecord x$1) {
        this.record = x$1;
    }

    public boolean stopExploring() {
        return this.stopExploring;
    }

    public void stopExploring_$eq(boolean x$1) {
        this.stopExploring = x$1;
    }

    private ThreadLocalRandom random() {
        return ThreadLocalRandom.current();
    }

    private void checkParamAsProbability(double value, String paramName) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString(new StringBuilder(47).append(paramName).append(" must be between 0 and 1 (inclusive), was: [%s]").toString()), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToDouble(value)})));
        }
    }

    private void checkParamAsPositiveNum(double value, String paramName) {
        this.checkParamLowerBound(value, 0.0, paramName);
    }

    private void checkParamLowerBound(double value, double lowerBound, String paramName) {
        if (value < lowerBound) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString(new StringBuilder(23).append(paramName).append(" must be >= ").append(lowerBound).append(", was: [%s]").toString()), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToDouble(value)})));
        }
    }

    private long actionInternalNanos() {
        return this.actionInternalNanos;
    }

    @Override
    public boolean isTimeForResize(long messageCounter) {
        return System.nanoTime() > this.record().checkTime() + this.actionInternalNanos();
    }

    @Override
    public void reportMessageCount(IndexedSeq<Routee> currentRoutees, long messageCounter) {
        Tuple2<Map<Object, Duration>, OptimalSizeExploringResizer.ResizeRecord> tuple2 = this.updatedStats(currentRoutees, messageCounter);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        Map<Object, Duration> newPerfLog = tuple2._1();
        OptimalSizeExploringResizer.ResizeRecord newRecord = tuple2._2();
        Tuple2<Map<Object, Duration>, OptimalSizeExploringResizer.ResizeRecord> tuple22 = new Tuple2<Map<Object, Duration>, OptimalSizeExploringResizer.ResizeRecord>(newPerfLog, newRecord);
        Map<Object, Duration> newPerfLog2 = tuple22._1();
        OptimalSizeExploringResizer.ResizeRecord newRecord2 = tuple22._2();
        this.performanceLog_$eq(newPerfLog2);
        this.record_$eq(newRecord2);
    }

    public Tuple2<Map<Object, Duration>, OptimalSizeExploringResizer.ResizeRecord> updatedStats(IndexedSeq<Routee> currentRoutees, long messageCounter) {
        Map map;
        Option newUnderutilizationStreak;
        LocalDateTime now = LocalDateTime.now();
        int currentSize = currentRoutees.length();
        IndexedSeq messagesInRoutees = (IndexedSeq)currentRoutees.map((Function1<Routee, Object> & Serializable)x0$1 -> BoxesRunTime.boxToInteger(DefaultOptimalSizeExploringResizer.$anonfun$updatedStats$1(x0$1)));
        int totalQueueLength = BoxesRunTime.unboxToInt(messagesInRoutees.sum(Numeric$IntIsIntegral$.MODULE$));
        int utilized = messagesInRoutees.count(x$2 -> x$2 > 0);
        boolean fullyUtilized = utilized == currentSize;
        Option option = newUnderutilizationStreak = fullyUtilized ? None$.MODULE$ : new Some<OptimalSizeExploringResizer.UnderUtilizationStreak>(new OptimalSizeExploringResizer.UnderUtilizationStreak((LocalDateTime)this.record().underutilizationStreak().fold((Function0<LocalDateTime> & Serializable)() -> now, (Function1<OptimalSizeExploringResizer.UnderUtilizationStreak, LocalDateTime> & Serializable)x$3 -> x$3.start()), Math.max(BoxesRunTime.unboxToInt(this.record().underutilizationStreak().fold(() -> 0, (Function1<OptimalSizeExploringResizer.UnderUtilizationStreak, Object> & Serializable)x$4 -> BoxesRunTime.boxToInteger(x$4.highestUtilization()))), utilized)));
        if (fullyUtilized && this.record().underutilizationStreak().isEmpty() && this.record().checkTime() > 0L) {
            long totalMessageReceived = messageCounter - this.record().messageCount();
            int queueSizeChange = this.record().totalQueueLength() - totalQueueLength;
            long totalProcessed = (long)queueSizeChange + totalMessageReceived;
            if (totalProcessed > 0L) {
                FiniteDuration duration = Duration$.MODULE$.fromNanos(System.nanoTime() - this.record().checkTime());
                FiniteDuration last = duration.$div(totalProcessed);
                Duration toUpdate = (Duration)this.performanceLog().get(BoxesRunTime.boxToInteger(currentSize)).fold((Function0<Duration> & Serializable)() -> last, (Function1<Duration, Duration> & Serializable)oldSpeed -> oldSpeed.$times(1.0 - this.weightOfLatestMetric()).$plus(last.$times(this.weightOfLatestMetric())));
                map = (Map)this.performanceLog().$plus(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(currentSize)), toUpdate));
            } else {
                map = this.performanceLog();
            }
        } else {
            map = this.performanceLog();
        }
        Map newPerformanceLog = map;
        OptimalSizeExploringResizer.ResizeRecord newRecord = this.record().copy(newUnderutilizationStreak, messageCounter, totalQueueLength, System.nanoTime());
        return new Tuple2<Map<Object, Duration>, OptimalSizeExploringResizer.ResizeRecord>(newPerformanceLog, newRecord);
    }

    @Override
    public int resize(IndexedSeq<Routee> currentRoutees) {
        int n;
        int currentSize = currentRoutees.length();
        LocalDateTime now = LocalDateTime.now();
        if (BoxesRunTime.unboxToBoolean(this.record().underutilizationStreak().fold(() -> false, (Function1<OptimalSizeExploringResizer.UnderUtilizationStreak, Object> & Serializable)x$5 -> BoxesRunTime.boxToBoolean(DefaultOptimalSizeExploringResizer.$anonfun$resize$2(this, now, x$5))))) {
            int downsizeTo = (int)((double)this.record().underutilizationStreak().get().highestUtilization() * this.downsizeRatio());
            n = Math.min(downsizeTo - currentSize, 0);
        } else {
            n = this.performanceLog().isEmpty() || this.record().underutilizationStreak().isDefined() ? 0 : (!this.stopExploring() && this.random().nextDouble() < this.explorationProbability() ? this.explore(currentSize) : this.optimize(currentSize));
        }
        int proposedChange = n;
        return Math.max(this.lowerBound(), Math.min(proposedChange + currentSize, this.upperBound())) - currentSize;
    }

    private int optimize(int currentSize) {
        Seq<Object> sizes = this.performanceLog().keys().toSeq();
        int numOfSizesEachSide = this.numOfAdjacentSizesToConsiderDuringOptimization() / 2;
        int leftBoundary = BoxesRunTime.unboxToInt(((IterableOps)((IterableOps)((SeqOps)sizes.filter(x$6 -> x$6 < currentSize)).sortBy(DefaultOptimalSizeExploringResizer.adjacency$1(currentSize), Ordering$Int$.MODULE$)).take(numOfSizesEachSide)).lastOption().getOrElse(() -> currentSize));
        int rightBoundary = BoxesRunTime.unboxToInt(((IterableOps)((IterableOps)((SeqOps)sizes.filter(x$7 -> x$7 >= currentSize)).sortBy(DefaultOptimalSizeExploringResizer.adjacency$1(currentSize), Ordering$Int$.MODULE$)).take(numOfSizesEachSide)).lastOption().getOrElse(() -> currentSize));
        Map adjacentDispatchWaits = (Map)this.performanceLog().filter((Function1<Tuple2, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(DefaultOptimalSizeExploringResizer.$anonfun$optimize$6(leftBoundary, rightBoundary, x0$1)));
        int optimalSize = ((Tuple2)adjacentDispatchWaits.minBy((Function1<Tuple2, Duration> & Serializable)x$8 -> (Duration)x$8._2(), Duration$DurationIsOrdered$.MODULE$))._1$mcI$sp();
        double movement = (double)(optimalSize - currentSize) / 2.0;
        if (movement < 0.0) {
            return (int)Math.floor(movement);
        }
        return (int)Math.ceil(movement);
    }

    private int explore(int currentSize) {
        int change = Math.max(1, this.random().nextInt((int)Math.ceil((double)currentSize * this.exploreStepSize())));
        if (this.random().nextDouble() < this.chanceOfScalingDownWhenFull()) {
            return -change;
        }
        return change;
    }

    public DefaultOptimalSizeExploringResizer copy(int lowerBound, int upperBound, double chanceOfScalingDownWhenFull, Duration actionInterval, int numOfAdjacentSizesToConsiderDuringOptimization, double exploreStepSize, double downsizeRatio, Duration downsizeAfterUnderutilizedFor, double explorationProbability, double weightOfLatestMetric) {
        return new DefaultOptimalSizeExploringResizer(lowerBound, upperBound, chanceOfScalingDownWhenFull, actionInterval, numOfAdjacentSizesToConsiderDuringOptimization, exploreStepSize, downsizeRatio, downsizeAfterUnderutilizedFor, explorationProbability, weightOfLatestMetric);
    }

    public int copy$default$1() {
        return this.lowerBound();
    }

    public double copy$default$10() {
        return this.weightOfLatestMetric();
    }

    public int copy$default$2() {
        return this.upperBound();
    }

    public double copy$default$3() {
        return this.chanceOfScalingDownWhenFull();
    }

    public Duration copy$default$4() {
        return this.actionInterval();
    }

    public int copy$default$5() {
        return this.numOfAdjacentSizesToConsiderDuringOptimization();
    }

    public double copy$default$6() {
        return this.exploreStepSize();
    }

    public double copy$default$7() {
        return this.downsizeRatio();
    }

    public Duration copy$default$8() {
        return this.downsizeAfterUnderutilizedFor();
    }

    public double copy$default$9() {
        return this.explorationProbability();
    }

    @Override
    public String productPrefix() {
        return "DefaultOptimalSizeExploringResizer";
    }

    @Override
    public int productArity() {
        return 10;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.lowerBound());
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.upperBound());
            }
            case 2: {
                return BoxesRunTime.boxToDouble(this.chanceOfScalingDownWhenFull());
            }
            case 3: {
                return this.actionInterval();
            }
            case 4: {
                return BoxesRunTime.boxToInteger(this.numOfAdjacentSizesToConsiderDuringOptimization());
            }
            case 5: {
                return BoxesRunTime.boxToDouble(this.exploreStepSize());
            }
            case 6: {
                return BoxesRunTime.boxToDouble(this.downsizeRatio());
            }
            case 7: {
                return this.downsizeAfterUnderutilizedFor();
            }
            case 8: {
                return BoxesRunTime.boxToDouble(this.explorationProbability());
            }
            case 9: {
                return BoxesRunTime.boxToDouble(this.weightOfLatestMetric());
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
        return x$1 instanceof DefaultOptimalSizeExploringResizer;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "lowerBound";
            }
            case 1: {
                return "upperBound";
            }
            case 2: {
                return "chanceOfScalingDownWhenFull";
            }
            case 3: {
                return "actionInterval";
            }
            case 4: {
                return "numOfAdjacentSizesToConsiderDuringOptimization";
            }
            case 5: {
                return "exploreStepSize";
            }
            case 6: {
                return "downsizeRatio";
            }
            case 7: {
                return "downsizeAfterUnderutilizedFor";
            }
            case 8: {
                return "explorationProbability";
            }
            case 9: {
                return "weightOfLatestMetric";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.lowerBound());
        n = Statics.mix(n, this.upperBound());
        n = Statics.mix(n, Statics.doubleHash(this.chanceOfScalingDownWhenFull()));
        n = Statics.mix(n, Statics.anyHash(this.actionInterval()));
        n = Statics.mix(n, this.numOfAdjacentSizesToConsiderDuringOptimization());
        n = Statics.mix(n, Statics.doubleHash(this.exploreStepSize()));
        n = Statics.mix(n, Statics.doubleHash(this.downsizeRatio()));
        n = Statics.mix(n, Statics.anyHash(this.downsizeAfterUnderutilizedFor()));
        n = Statics.mix(n, Statics.doubleHash(this.explorationProbability()));
        n = Statics.mix(n, Statics.doubleHash(this.weightOfLatestMetric()));
        return Statics.finalizeHash(n, 10);
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
        if (!(object instanceof DefaultOptimalSizeExploringResizer)) return false;
        boolean bl = true;
        if (!bl) return false;
        DefaultOptimalSizeExploringResizer defaultOptimalSizeExploringResizer = (DefaultOptimalSizeExploringResizer)x$1;
        if (this.lowerBound() != defaultOptimalSizeExploringResizer.lowerBound()) return false;
        if (this.upperBound() != defaultOptimalSizeExploringResizer.upperBound()) return false;
        if (this.chanceOfScalingDownWhenFull() != defaultOptimalSizeExploringResizer.chanceOfScalingDownWhenFull()) return false;
        if (this.numOfAdjacentSizesToConsiderDuringOptimization() != defaultOptimalSizeExploringResizer.numOfAdjacentSizesToConsiderDuringOptimization()) return false;
        if (this.exploreStepSize() != defaultOptimalSizeExploringResizer.exploreStepSize()) return false;
        if (this.downsizeRatio() != defaultOptimalSizeExploringResizer.downsizeRatio()) return false;
        if (this.explorationProbability() != defaultOptimalSizeExploringResizer.explorationProbability()) return false;
        if (this.weightOfLatestMetric() != defaultOptimalSizeExploringResizer.weightOfLatestMetric()) return false;
        Duration duration = this.actionInterval();
        Duration duration2 = defaultOptimalSizeExploringResizer.actionInterval();
        if (duration == null) {
            if (duration2 != null) {
                return false;
            }
        } else if (!duration.equals(duration2)) return false;
        Duration duration3 = this.downsizeAfterUnderutilizedFor();
        Duration duration4 = defaultOptimalSizeExploringResizer.downsizeAfterUnderutilizedFor();
        if (duration3 == null) {
            if (duration4 != null) {
                return false;
            }
        } else if (!duration3.equals(duration4)) return false;
        if (!defaultOptimalSizeExploringResizer.canEqual(this)) return false;
        return true;
    }

    public static final /* synthetic */ int $anonfun$updatedStats$1(Routee x0$1) {
        ActorRefRoutee actorRefRoutee;
        ActorRef a;
        Routee routee = x0$1;
        if (routee instanceof ActorRefRoutee && (a = (actorRefRoutee = (ActorRefRoutee)routee).ref()) instanceof ActorRefWithCell) {
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)a;
            Cell cell = actorRefWithCell.underlying();
            if (cell instanceof ActorCell) {
                ActorCell actorCell = (ActorCell)cell;
                return actorCell.mailbox().numberOfMessages() + (actorCell.currentMessage() != null ? 1 : 0);
            }
            return cell.numberOfMessages();
        }
        return 0;
    }

    public static final /* synthetic */ boolean $anonfun$resize$2(DefaultOptimalSizeExploringResizer $this, LocalDateTime now$2, OptimalSizeExploringResizer.UnderUtilizationStreak x$5) {
        return x$5.start().isBefore(now$2.minus(JavaDurationConverters$ScalaDurationOps$.MODULE$.asJava$extension(JavaDurationConverters$.MODULE$.ScalaDurationOps($this.downsizeAfterUnderutilizedFor()))));
    }

    private static final Function1 adjacency$1(int currentSize$1) {
        return size -> Math.abs(currentSize$1 - size);
    }

    public static final /* synthetic */ boolean $anonfun$optimize$6(int leftBoundary$1, int rightBoundary$1, Tuple2 x0$1) {
        Tuple2 tuple2 = x0$1;
        if (tuple2 != null) {
            int size = tuple2._1$mcI$sp();
            return size >= leftBoundary$1 && size <= rightBoundary$1;
        }
        throw new MatchError(tuple2);
    }

    public DefaultOptimalSizeExploringResizer(int lowerBound, int upperBound, double chanceOfScalingDownWhenFull, Duration actionInterval, int numOfAdjacentSizesToConsiderDuringOptimization, double exploreStepSize, double downsizeRatio, Duration downsizeAfterUnderutilizedFor, double explorationProbability, double weightOfLatestMetric) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.chanceOfScalingDownWhenFull = chanceOfScalingDownWhenFull;
        this.actionInterval = actionInterval;
        this.numOfAdjacentSizesToConsiderDuringOptimization = numOfAdjacentSizesToConsiderDuringOptimization;
        this.exploreStepSize = exploreStepSize;
        this.downsizeRatio = downsizeRatio;
        this.downsizeAfterUnderutilizedFor = downsizeAfterUnderutilizedFor;
        this.explorationProbability = explorationProbability;
        this.weightOfLatestMetric = weightOfLatestMetric;
        Product.$init$(this);
        this.performanceLog = Predef$.MODULE$.Map().empty();
        this.record = new OptimalSizeExploringResizer.ResizeRecord(OptimalSizeExploringResizer$ResizeRecord$.MODULE$.apply$default$1(), OptimalSizeExploringResizer$ResizeRecord$.MODULE$.apply$default$2(), OptimalSizeExploringResizer$ResizeRecord$.MODULE$.apply$default$3(), OptimalSizeExploringResizer$ResizeRecord$.MODULE$.apply$default$4());
        this.stopExploring = false;
        this.checkParamAsPositiveNum(lowerBound, "lowerBound");
        this.checkParamAsPositiveNum(upperBound, "upperBound");
        if (upperBound < lowerBound) {
            throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("upperBound must be >= lowerBound, was: [%s] < [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(upperBound), BoxesRunTime.boxToInteger(lowerBound)})));
        }
        this.checkParamLowerBound(numOfAdjacentSizesToConsiderDuringOptimization, 2.0, "numOfAdjacentSizesToConsiderDuringOptimization");
        this.checkParamAsProbability(chanceOfScalingDownWhenFull, "chanceOfScalingDownWhenFull");
        this.checkParamAsPositiveNum(numOfAdjacentSizesToConsiderDuringOptimization, "numOfAdjacentSizesToConsiderDuringOptimization");
        this.checkParamAsPositiveNum(exploreStepSize, "exploreStepSize");
        this.checkParamAsPositiveNum(downsizeRatio, "downsizeRatio");
        this.checkParamAsProbability(explorationProbability, "explorationProbability");
        this.checkParamAsProbability(weightOfLatestMetric, "weightOfLatestMetric");
        this.actionInternalNanos = actionInterval.toNanos();
    }
}

