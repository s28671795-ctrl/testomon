/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.DeadLetterSuppression;
import akka.actor.Props;
import akka.annotation.InternalApi;
import akka.pattern.BackoffOnFailureOptions;
import akka.pattern.BackoffOnStopOptions;
import akka.pattern.BackoffSupervisor$;
import akka.pattern.BackoffSupervisor$GetCurrentChild$;
import akka.pattern.BackoffSupervisor$GetRestartCount$;
import akka.pattern.BackoffSupervisor$Reset$;
import java.io.Serializable;
import java.util.Optional;
import scala.$less$colon$less$;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\rer!\u0002=z\u0011\u0003qhaBA\u0001s\"\u0005\u00111\u0001\u0005\b\u0003#\tA\u0011AA\n\u0011\u001d\t)\"\u0001C\u0001\u0003/Aq!!\u0006\u0002\t\u0003\tycB\u0004\u0002:\u0005A\t)a\u000f\u0007\u000f\u0005}\u0012\u0001#!\u0002B!9\u0011\u0011\u0003\u0004\u0005\u0002\u0005\u0005\u0004\"CA2\r\u0005\u0005I\u0011IA3\u0011%\t9HBA\u0001\n\u0003\tI\bC\u0005\u0002\u0002\u001a\t\t\u0011\"\u0001\u0002\u0004\"I\u0011q\u0012\u0004\u0002\u0002\u0013\u0005\u0013\u0011\u0013\u0005\n\u0003?3\u0011\u0011!C\u0001\u0003CC\u0011\"a+\u0007\u0003\u0003%\t%!,\t\u0013\u0005=f!!A\u0005B\u0005E\u0006\"CAZ\r\u0005\u0005I\u0011BA[\u0011\u001d\ti,\u0001C\u0001\u0003\u007f3a!!1\u0002\u0005\u0006\r\u0007BCAc#\tU\r\u0011\"\u0001\u0002H\"Q\u0011Q[\t\u0003\u0012\u0003\u0006I!!3\t\u000f\u0005E\u0011\u0003\"\u0001\u0002X\"9\u0011Q\\\t\u0005\u0002\u0005}\u0007\"CAw#\u0005\u0005I\u0011AAx\u0011%\t\u00190EI\u0001\n\u0003\t)\u0010C\u0005\u0002dE\t\t\u0011\"\u0011\u0002f!I\u0011qO\t\u0002\u0002\u0013\u0005\u0011\u0011\u0010\u0005\n\u0003\u0003\u000b\u0012\u0011!C\u0001\u0005\u0017A\u0011\"a$\u0012\u0003\u0003%\t%!%\t\u0013\u0005}\u0015#!A\u0005\u0002\t=\u0001\"\u0003B\n#\u0005\u0005I\u0011\tB\u000b\u0011%\tY+EA\u0001\n\u0003\ni\u000bC\u0005\u00020F\t\t\u0011\"\u0011\u00022\"I!\u0011D\t\u0002\u0002\u0013\u0005#1D\u0004\n\u0005?\t\u0011\u0011!E\u0001\u0005C1\u0011\"!1\u0002\u0003\u0003E\tAa\t\t\u000f\u0005E!\u0005\"\u0001\u0003<!I\u0011q\u0016\u0012\u0002\u0002\u0013\u0015\u0013\u0011\u0017\u0005\n\u0005{\u0011\u0013\u0011!CA\u0005\u007fA\u0011Ba\u0011#\u0003\u0003%\tI!\u0012\t\u0013\u0005M&%!A\u0005\n\u0005Uva\u0002B'\u0003!\u0005%q\n\u0004\b\u0005#\n\u0001\u0012\u0011B*\u0011\u001d\t\t\"\u000bC\u0001\u0005+B\u0011\"a\u0019*\u0003\u0003%\t%!\u001a\t\u0013\u0005]\u0014&!A\u0005\u0002\u0005e\u0004\"CAAS\u0005\u0005I\u0011\u0001B,\u0011%\ty)KA\u0001\n\u0003\n\t\nC\u0005\u0002 &\n\t\u0011\"\u0001\u0003\\!I\u00111V\u0015\u0002\u0002\u0013\u0005\u0013Q\u0016\u0005\n\u0003_K\u0013\u0011!C!\u0003cC\u0011\"a-*\u0003\u0003%I!!.\t\u000f\t}\u0013\u0001\"\u0001\u0003b\u001d9!1M\u0001\t\u0002\n\u0015da\u0002B4\u0003!\u0005%\u0011\u000e\u0005\b\u0003#)D\u0011\u0001B6\u0011%\t\u0019'NA\u0001\n\u0003\n)\u0007C\u0005\u0002xU\n\t\u0011\"\u0001\u0002z!I\u0011\u0011Q\u001b\u0002\u0002\u0013\u0005!Q\u000e\u0005\n\u0003\u001f+\u0014\u0011!C!\u0003#C\u0011\"a(6\u0003\u0003%\tA!\u001d\t\u0013\u0005-V'!A\u0005B\u00055\u0006\"CAXk\u0005\u0005I\u0011IAY\u0011%\t\u0019,NA\u0001\n\u0013\t)\fC\u0004\u0003v\u0005!\tAa\u001e\u0007\r\te\u0014A\u0011B>\u0011)\u0011i\b\u0011BK\u0002\u0013\u0005\u0011\u0011\u0010\u0005\u000b\u0005\u007f\u0002%\u0011#Q\u0001\n\u0005m\u0004bBA\t\u0001\u0012\u0005!\u0011\u0011\u0005\n\u0003[\u0004\u0015\u0011!C\u0001\u0005\u000fC\u0011\"a=A#\u0003%\tAa#\t\u0013\u0005\r\u0004)!A\u0005B\u0005\u0015\u0004\"CA<\u0001\u0006\u0005I\u0011AA=\u0011%\t\t\tQA\u0001\n\u0003\u0011y\tC\u0005\u0002\u0010\u0002\u000b\t\u0011\"\u0011\u0002\u0012\"I\u0011q\u0014!\u0002\u0002\u0013\u0005!1\u0013\u0005\n\u0005'\u0001\u0015\u0011!C!\u0005/C\u0011\"a+A\u0003\u0003%\t%!,\t\u0013\u0005=\u0006)!A\u0005B\u0005E\u0006\"\u0003B\r\u0001\u0006\u0005I\u0011\tBN\u000f%\u0011y*AA\u0001\u0012\u0003\u0011\tKB\u0005\u0003z\u0005\t\t\u0011#\u0001\u0003$\"9\u0011\u0011\u0003)\u0005\u0002\t\u001d\u0006\"CAX!\u0006\u0005IQIAY\u0011%\u0011i\u0004UA\u0001\n\u0003\u0013I\u000bC\u0005\u0003DA\u000b\t\u0011\"!\u0003.\"I\u00111\u0017)\u0002\u0002\u0013%\u0011QW\u0004\t\u0005g\u000b\u0001\u0012Q>\u00036\u001aA!qW\u0001\t\u0002n\u0014I\fC\u0004\u0002\u0012]#\tA!1\t\u0013\u0005\rt+!A\u0005B\u0005\u0015\u0004\"CA</\u0006\u0005I\u0011AA=\u0011%\t\tiVA\u0001\n\u0003\u0011\u0019\rC\u0005\u0002\u0010^\u000b\t\u0011\"\u0011\u0002\u0012\"I\u0011qT,\u0002\u0002\u0013\u0005!q\u0019\u0005\n\u0003W;\u0016\u0011!C!\u0003[C\u0011\"a,X\u0003\u0003%\t%!-\t\u0013\u0005Mv+!A\u0005\n\u0005Ufa\u0002Bm\u0003\u0001[(1\u001c\u0005\u000b\u0005;\f'Q3A\u0005\u0002\u0005e\u0004B\u0003BpC\nE\t\u0015!\u0003\u0002|!9\u0011\u0011C1\u0005\u0002\t\u0005\b\"CAwC\u0006\u0005I\u0011\u0001Bt\u0011%\t\u00190YI\u0001\n\u0003\u0011Y\tC\u0005\u0002d\u0005\f\t\u0011\"\u0011\u0002f!I\u0011qO1\u0002\u0002\u0013\u0005\u0011\u0011\u0010\u0005\n\u0003\u0003\u000b\u0017\u0011!C\u0001\u0005WD\u0011\"a$b\u0003\u0003%\t%!%\t\u0013\u0005}\u0015-!A\u0005\u0002\t=\b\"\u0003B\nC\u0006\u0005I\u0011\tBz\u0011%\tY+YA\u0001\n\u0003\ni\u000bC\u0005\u00020\u0006\f\t\u0011\"\u0011\u00022\"I!\u0011D1\u0002\u0002\u0013\u0005#q_\u0004\u000b\u0005{\f\u0011\u0011!E\u0001w\n}hA\u0003Bm\u0003\u0005\u0005\t\u0012A>\u0004\u0002!9\u0011\u0011C9\u0005\u0002\r\u0015\u0001\"CAXc\u0006\u0005IQIAY\u0011%\u0011i$]A\u0001\n\u0003\u001b9\u0001C\u0005\u0003DE\f\t\u0011\"!\u0004\f!I\u00111W9\u0002\u0002\u0013%\u0011Q\u0017\u0005\t\u0007\u001f\tA\u0011A>\u0004\u0012\u0005\t\")Y2l_\u001a47+\u001e9feZL7o\u001c:\u000b\u0005i\\\u0018a\u00029biR,'O\u001c\u0006\u0002y\u0006!\u0011m[6b\u0007\u0001\u0001\"a`\u0001\u000e\u0003e\u0014\u0011CQ1dW>4gmU;qKJ4\u0018n]8s'\r\t\u0011Q\u0001\t\u0005\u0003\u000f\ti!\u0004\u0002\u0002\n)\u0011\u00111B\u0001\u0006g\u000e\fG.Y\u0005\u0005\u0003\u001f\tIA\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003y\fQ\u0001\u001d:paN$B!!\u0007\u0002&A!\u00111DA\u0011\u001b\t\tiBC\u0002\u0002 m\fQ!Y2u_JLA!a\t\u0002\u001e\t)\u0001K]8qg\"9\u0011qE\u0002A\u0002\u0005%\u0012aB8qi&|gn\u001d\t\u0004\u007f\u0006-\u0012bAA\u0017s\n!\")Y2l_\u001a4wJ\\*u_B|\u0005\u000f^5p]N$B!!\u0007\u00022!9\u0011q\u0005\u0003A\u0002\u0005M\u0002cA@\u00026%\u0019\u0011qG=\u0003/\t\u000b7m[8gM>sg)Y5mkJ,w\n\u001d;j_:\u001c\u0018aD$fi\u000e+(O]3oi\u000eC\u0017\u000e\u001c3\u0011\u0007\u0005ub!D\u0001\u0002\u0005=9U\r^\"veJ,g\u000e^\"iS2$7c\u0002\u0004\u0002\u0006\u0005\r\u0013\u0011\n\t\u0005\u0003\u000f\t)%\u0003\u0003\u0002H\u0005%!a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003\u0017\nYF\u0004\u0003\u0002N\u0005]c\u0002BA(\u0003+j!!!\u0015\u000b\u0007\u0005MS0\u0001\u0004=e>|GOP\u0005\u0003\u0003\u0017IA!!\u0017\u0002\n\u00059\u0001/Y2lC\u001e,\u0017\u0002BA/\u0003?\u0012AbU3sS\u0006d\u0017N_1cY\u0016TA!!\u0017\u0002\nQ\u0011\u00111H\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005\u001d\u0004\u0003BA5\u0003gj!!a\u001b\u000b\t\u00055\u0014qN\u0001\u0005Y\u0006twM\u0003\u0002\u0002r\u0005!!.\u0019<b\u0013\u0011\t)(a\u001b\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\tY\b\u0005\u0003\u0002\b\u0005u\u0014\u0002BA@\u0003\u0013\u00111!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!!\"\u0002\fB!\u0011qAAD\u0013\u0011\tI)!\u0003\u0003\u0007\u0005s\u0017\u0010C\u0005\u0002\u000e*\t\t\u00111\u0001\u0002|\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a%\u0011\r\u0005U\u00151TAC\u001b\t\t9J\u0003\u0003\u0002\u001a\u0006%\u0011AC2pY2,7\r^5p]&!\u0011QTAL\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\r\u0016\u0011\u0016\t\u0005\u0003\u000f\t)+\u0003\u0003\u0002(\u0006%!a\u0002\"p_2,\u0017M\u001c\u0005\n\u0003\u001bc\u0011\u0011!a\u0001\u0003\u000b\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003w\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003O\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a.\u0011\t\u0005%\u0014\u0011X\u0005\u0005\u0003w\u000bYG\u0001\u0004PE*,7\r^\u0001\u0010O\u0016$8)\u001e:sK:$8\t[5mIV\u0011\u00111\b\u0002\r\u0007V\u0014(/\u001a8u\u0007\"LG\u000eZ\n\b#\u0005\u0015\u00111IA%\u0003\r\u0011XMZ\u000b\u0003\u0003\u0013\u0004b!a\u0002\u0002L\u0006=\u0017\u0002BAg\u0003\u0013\u0011aa\u00149uS>t\u0007\u0003BA\u000e\u0003#LA!a5\u0002\u001e\tA\u0011i\u0019;peJ+g-\u0001\u0003sK\u001a\u0004C\u0003BAm\u00037\u00042!!\u0010\u0012\u0011\u001d\t)\r\u0006a\u0001\u0003\u0013\faaZ3u%\u00164WCAAq!\u0019\t\u0019/!;\u0002P6\u0011\u0011Q\u001d\u0006\u0005\u0003O\fy'\u0001\u0003vi&d\u0017\u0002BAv\u0003K\u0014\u0001b\u00149uS>t\u0017\r\\\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003\u0002Z\u0006E\b\"CAc-A\u0005\t\u0019AAe\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!a>+\t\u0005%\u0017\u0011`\u0016\u0003\u0003w\u0004B!!@\u0003\b5\u0011\u0011q \u0006\u0005\u0005\u0003\u0011\u0019!A\u0005v]\u000eDWmY6fI*!!QAA\u0005\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0005\u0013\tyPA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$B!!\"\u0003\u000e!I\u0011Q\u0012\u000e\u0002\u0002\u0003\u0007\u00111\u0010\u000b\u0005\u0003G\u0013\t\u0002C\u0005\u0002\u000er\t\t\u00111\u0001\u0002\u0006\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t9Ga\u0006\t\u0013\u00055U$!AA\u0002\u0005m\u0014AB3rk\u0006d7\u000f\u0006\u0003\u0002$\nu\u0001\"CAGA\u0005\u0005\t\u0019AAC\u00031\u0019UO\u001d:f]R\u001c\u0005.\u001b7e!\r\tiDI\n\u0006E\t\u0015\"\u0011\u0007\t\t\u0005O\u0011i#!3\u0002Z6\u0011!\u0011\u0006\u0006\u0005\u0005W\tI!A\u0004sk:$\u0018.\\3\n\t\t=\"\u0011\u0006\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003\u0002B\u001a\u0005si!A!\u000e\u000b\t\t]\u0012qN\u0001\u0003S>LA!!\u0018\u00036Q\u0011!\u0011E\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u00033\u0014\t\u0005C\u0004\u0002F\u0016\u0002\r!!3\u0002\u000fUt\u0017\r\u001d9msR!!q\tB%!\u0019\t9!a3\u0002J\"I!1\n\u0014\u0002\u0002\u0003\u0007\u0011\u0011\\\u0001\u0004q\u0012\u0002\u0014!\u0002*fg\u0016$\bcAA\u001fS\t)!+Z:fiN9\u0011&!\u0002\u0002D\u0005%CC\u0001B()\u0011\t)I!\u0017\t\u0013\u00055U&!AA\u0002\u0005mD\u0003BAR\u0005;B\u0011\"!$0\u0003\u0003\u0005\r!!\"\u0002\u000bI,7/\u001a;\u0016\u0005\t=\u0013aD$fiJ+7\u000f^1si\u000e{WO\u001c;\u0011\u0007\u0005uRGA\bHKR\u0014Vm\u001d;beR\u001cu.\u001e8u'\u001d)\u0014QAA\"\u0003\u0013\"\"A!\u001a\u0015\t\u0005\u0015%q\u000e\u0005\n\u0003\u001bK\u0014\u0011!a\u0001\u0003w\"B!a)\u0003t!I\u0011QR\u001e\u0002\u0002\u0003\u0007\u0011QQ\u0001\u0010O\u0016$(+Z:uCJ$8i\\;oiV\u0011!Q\r\u0002\r%\u0016\u001cH/\u0019:u\u0007>,h\u000e^\n\b\u0001\u0006\u0015\u00111IA%\u0003\u0015\u0019w.\u001e8u\u0003\u0019\u0019w.\u001e8uAQ!!1\u0011BC!\r\ti\u0004\u0011\u0005\b\u0005{\u001a\u0005\u0019AA>)\u0011\u0011\u0019I!#\t\u0013\tuD\t%AA\u0002\u0005mTC\u0001BGU\u0011\tY(!?\u0015\t\u0005\u0015%\u0011\u0013\u0005\n\u0003\u001bC\u0015\u0011!a\u0001\u0003w\"B!a)\u0003\u0016\"I\u0011Q\u0012&\u0002\u0002\u0003\u0007\u0011Q\u0011\u000b\u0005\u0003O\u0012I\nC\u0005\u0002\u000e.\u000b\t\u00111\u0001\u0002|Q!\u00111\u0015BO\u0011%\tiITA\u0001\u0002\u0004\t))\u0001\u0007SKN$\u0018M\u001d;D_VtG\u000fE\u0002\u0002>A\u001bR\u0001\u0015BS\u0005c\u0001\u0002Ba\n\u0003.\u0005m$1\u0011\u000b\u0003\u0005C#BAa!\u0003,\"9!QP*A\u0002\u0005mD\u0003\u0002BX\u0005c\u0003b!a\u0002\u0002L\u0006m\u0004\"\u0003B&)\u0006\u0005\t\u0019\u0001BB\u0003)\u0019F/\u0019:u\u0007\"LG\u000e\u001a\t\u0004\u0003{9&AC*uCJ$8\t[5mINIq+!\u0002\u0003<\u0006\r\u0013\u0011\n\t\u0005\u00037\u0011i,\u0003\u0003\u0003@\u0006u!!\u0006#fC\u0012dU\r\u001e;feN+\b\u000f\u001d:fgNLwN\u001c\u000b\u0003\u0005k#B!!\"\u0003F\"I\u0011QR.\u0002\u0002\u0003\u0007\u00111\u0010\u000b\u0005\u0003G\u0013I\rC\u0005\u0002\u000ev\u000b\t\u00111\u0001\u0002\u0006\"\u001aqK!4\u0011\t\t='1[\u0007\u0003\u0005#T1A!\u0002|\u0013\u0011\u0011)N!5\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\u0004-\n5'!\u0005*fg\u0016$(+Z:uCJ$8i\\;oiNI\u0011-!\u0002\u0003<\u0006\r\u0013\u0011J\u0001\bGV\u0014(/\u001a8u\u0003!\u0019WO\u001d:f]R\u0004C\u0003\u0002Br\u0005K\u00042!!\u0010b\u0011\u001d\u0011i\u000e\u001aa\u0001\u0003w\"BAa9\u0003j\"I!Q\\3\u0011\u0002\u0003\u0007\u00111\u0010\u000b\u0005\u0003\u000b\u0013i\u000fC\u0005\u0002\u000e&\f\t\u00111\u0001\u0002|Q!\u00111\u0015By\u0011%\tii[A\u0001\u0002\u0004\t)\t\u0006\u0003\u0002h\tU\b\"CAGY\u0006\u0005\t\u0019AA>)\u0011\t\u0019K!?\t\u0013\u00055u.!AA\u0002\u0005\u0015\u0005fA1\u0003N\u0006\t\"+Z:fiJ+7\u000f^1si\u000e{WO\u001c;\u0011\u0007\u0005u\u0012oE\u0003r\u0007\u0007\u0011\t\u0004\u0005\u0005\u0003(\t5\u00121\u0010Br)\t\u0011y\u0010\u0006\u0003\u0003d\u000e%\u0001b\u0002Boi\u0002\u0007\u00111\u0010\u000b\u0005\u0005_\u001bi\u0001C\u0005\u0003LU\f\t\u00111\u0001\u0003d\u0006q1-\u00197dk2\fG/\u001a#fY\u0006LHCCB\n\u0007G\u00199ca\u000b\u00040A!1QCB\u0010\u001b\t\u00199B\u0003\u0003\u0004\u001a\rm\u0011\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\t\ru\u0011\u0011B\u0001\u000bG>t7-\u001e:sK:$\u0018\u0002BB\u0011\u0007/\u0011aBR5oSR,G)\u001e:bi&|g\u000eC\u0004\u0004&]\u0004\r!a\u001f\u0002\u0019I,7\u000f^1si\u000e{WO\u001c;\t\u000f\r%r\u000f1\u0001\u0004\u0014\u0005QQ.\u001b8CC\u000e\\wN\u001a4\t\u000f\r5r\u000f1\u0001\u0004\u0014\u0005QQ.\u0019=CC\u000e\\wN\u001a4\t\u000f\rEr\u000f1\u0001\u00044\u0005a!/\u00198e_64\u0015m\u0019;peB!\u0011qAB\u001b\u0013\u0011\u00199$!\u0003\u0003\r\u0011{WO\u00197f\u0001")
public final class BackoffSupervisor {
    public static BackoffSupervisor$GetRestartCount$ getRestartCount() {
        return BackoffSupervisor$.MODULE$.getRestartCount();
    }

    public static BackoffSupervisor$Reset$ reset() {
        return BackoffSupervisor$.MODULE$.reset();
    }

    public static BackoffSupervisor$GetCurrentChild$ getCurrentChild() {
        return BackoffSupervisor$.MODULE$.getCurrentChild();
    }

    public static Props props(BackoffOnFailureOptions options) {
        return BackoffSupervisor$.MODULE$.props(options);
    }

    public static Props props(BackoffOnStopOptions options) {
        return BackoffSupervisor$.MODULE$.props(options);
    }

    public static final class CurrentChild
    implements Product,
    Serializable {
        private final Option<ActorRef> ref;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Option<ActorRef> ref() {
            return this.ref;
        }

        public Optional<ActorRef> getRef() {
            return Optional.ofNullable(this.ref().orNull($less$colon$less$.MODULE$.refl()));
        }

        public CurrentChild copy(Option<ActorRef> ref) {
            return new CurrentChild(ref);
        }

        public Option<ActorRef> copy$default$1() {
            return this.ref();
        }

        @Override
        public String productPrefix() {
            return "CurrentChild";
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
                    return this.ref();
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
            return x$1 instanceof CurrentChild;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "ref";
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
            if (!(object instanceof CurrentChild)) return false;
            boolean bl = true;
            if (!bl) return false;
            CurrentChild currentChild = (CurrentChild)x$1;
            Option<ActorRef> option = this.ref();
            Option<ActorRef> option2 = currentChild.ref();
            if (option != null) {
                if (!option.equals(option2)) return false;
                return true;
            }
            if (option2 == null) return true;
            return false;
        }

        public CurrentChild(Option<ActorRef> ref) {
            this.ref = ref;
            Product.$init$(this);
        }
    }

    @InternalApi
    public static class ResetRestartCount
    implements DeadLetterSuppression,
    Product,
    Serializable {
        private final int current;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int current() {
            return this.current;
        }

        public ResetRestartCount copy(int current) {
            return new ResetRestartCount(current);
        }

        public int copy$default$1() {
            return this.current();
        }

        @Override
        public String productPrefix() {
            return "ResetRestartCount";
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
                    return BoxesRunTime.boxToInteger(this.current());
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
            return x$1 instanceof ResetRestartCount;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "current";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.current());
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
            if (!(object instanceof ResetRestartCount)) return false;
            boolean bl = true;
            if (!bl) return false;
            ResetRestartCount resetRestartCount = (ResetRestartCount)x$1;
            if (this.current() != resetRestartCount.current()) return false;
            if (!resetRestartCount.canEqual(this)) return false;
            return true;
        }

        public ResetRestartCount(int current) {
            this.current = current;
            Product.$init$(this);
        }
    }

    public static final class RestartCount
    implements Product,
    Serializable {
        private final int count;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int count() {
            return this.count;
        }

        public RestartCount copy(int count) {
            return new RestartCount(count);
        }

        public int copy$default$1() {
            return this.count();
        }

        @Override
        public String productPrefix() {
            return "RestartCount";
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
                    return BoxesRunTime.boxToInteger(this.count());
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
            return x$1 instanceof RestartCount;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "count";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.count());
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
            if (!(object instanceof RestartCount)) return false;
            boolean bl = true;
            if (!bl) return false;
            RestartCount restartCount = (RestartCount)x$1;
            if (this.count() != restartCount.count()) return false;
            return true;
        }

        public RestartCount(int count) {
            this.count = count;
            Product.$init$(this);
        }
    }
}

