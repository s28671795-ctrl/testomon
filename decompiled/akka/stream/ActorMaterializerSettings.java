/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorSystem;
import akka.annotation.InternalApi;
import akka.japi.function.Function;
import akka.stream.ActorAttributes;
import akka.stream.ActorMaterializerSettings$;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Attributes$CancellationStrategy$;
import akka.stream.Attributes$NestedMaterializationCancellationPolicy$;
import akka.stream.IOSettings;
import akka.stream.StreamRefSettings;
import akka.stream.StreamSubscriptionTimeoutSettings;
import akka.stream.Supervision;
import akka.stream.Supervision$;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\rEv!B%K\u0011\u0003ye!B)K\u0011\u0003\u0011\u0006\"B-\u0002\t\u0003Q\u0006\"B.\u0002\t\u0003a\u0006BB.\u0002\t\u0003\u0019i\u0006\u0003\u0004\\\u0003\u0011\u00051\u0011\u000f\u0005\b\u0007\u0017\u000bA\u0011ABG\u0011\u001d\u0019Y)\u0001C\u0001\u0007KCqaa#\u0002\t\u0003\u0019YK\u0002\u0003R\u0015\nq\u0006\u0002C0\n\u0005\u000b\u0007I\u0011\u00011\t\u00119L!\u0011!Q\u0001\n\u0005D\u0001\u0002]\u0005\u0003\u0006\u0004%\t\u0001\u0019\u0005\te&\u0011\t\u0011)A\u0005C\"AA/\u0003BC\u0002\u0013\u0005Q\u000fC\u0005\u0002\n%\u0011\t\u0011)A\u0005m\"Q\u0011QB\u0005\u0003\u0006\u0004%\t!a\u0004\t\u0015\u0005\u0015\u0012B!A!\u0002\u0013\t\t\u0002\u0003\u0006\u0002*%\u0011)\u0019!C\u0001\u0003WA!\"a\r\n\u0005\u0003\u0005\u000b\u0011BA\u0017\u0011)\t)$\u0003BC\u0002\u0013\u0005\u0011q\u0007\u0005\u000b\u0003\u000bJ!\u0011!Q\u0001\n\u0005e\u0002\"CA%\u0013\t\u0015\r\u0011\"\u0001a\u0011%\t\t&\u0003B\u0001B\u0003%\u0011\r\u0003\u0006\u0002V%\u0011)\u0019!C\u0001\u0003oA!\"!\u0018\n\u0005\u0003\u0005\u000b\u0011BA\u001d\u0011)\t\t'\u0003BC\u0002\u0013\u0005\u0011q\u0007\u0005\u000b\u0003SJ!\u0011!Q\u0001\n\u0005e\u0002\"CA7\u0013\t\u0015\r\u0011\"\u0001a\u0011%\t)(\u0003B\u0001B\u0003%\u0011\rC\u0005\u0002z%\u0011)\u0019!C\u0001A\"I\u0011\u0011Q\u0005\u0003\u0002\u0003\u0006I!\u0019\u0005\u000b\u0003\u000bK!Q1A\u0005\u0002\u0005\u001d\u0005BCAH\u0013\t\u0005\t\u0015!\u0003\u0002\n\"Q\u0011\u0011S\u0005\u0003\u0006\u0004%\t!a%\t\u0015\u0005m\u0015B!A!\u0002\u0013\t)\nC\u0005\u0002\u001e&\u0011)\u0019!C\u0001k\"I\u0011QU\u0005\u0003\u0002\u0003\u0006IA\u001e\u0005\u00073&!I!!+\t\u000f\u0005U\u0017\u0002\"\u0003\u0002X\"I\u0011Q_\u0005\u0012\u0002\u0013%\u0011q\u001f\u0005\n\u0005\u0017I\u0011\u0013!C\u0005\u0003oD\u0011B!\u0004\n#\u0003%IAa\u0004\t\u0013\tM\u0011\"%A\u0005\n\tU\u0001\"\u0003B\r\u0013E\u0005I\u0011\u0002B\u000e\u0011%\u0011y\"CI\u0001\n\u0013\u0011\t\u0003C\u0005\u0003&%\t\n\u0011\"\u0003\u0002x\"I!qE\u0005\u0012\u0002\u0013%!\u0011\u0005\u0005\n\u0005SI\u0011\u0013!C\u0005\u0005CA\u0011Ba\u000b\n#\u0003%I!a>\t\u0013\t5\u0012\"%A\u0005\n\u0005]\b\"\u0003B\u0018\u0013E\u0005I\u0011\u0002B\u0019\u0011%\u0011)$CI\u0001\n\u0013\u00119\u0004C\u0005\u0003<%\t\n\u0011\"\u0003\u0003\u0010!I!QH\u0005C\u0002\u0013%!q\b\u0005\t\u0005\u000fJ\u0001\u0015!\u0003\u0003B!9!\u0011J\u0005\u0005\u0002\t-\u0003b\u0002B.\u0013\u0011\u0005!Q\f\u0005\b\u0005OJA\u0011\u0001B5\u0011\u001d\u00119'\u0003C\u0001\u0005kBqAa*\n\t\u0003\u0011I\u000bC\u0004\u00036&!\tAa.\t\u000f\t\r\u0017\u0002\"\u0001\u0003F\"9!qZ\u0005\u0005\u0002\tE\u0007b\u0002Bn\u0013\u0011\u0005!Q\u001c\u0005\b\u0005SLA\u0011\u0001Bv\u0011\u001d\u0011\t0\u0003C\u0001\u0005gDqAa>\n\t\u0003\u0011I\u0010C\u0004\u0003~&!\tAa@\t\u000f\r-\u0011\u0002\"\u0003\u0004\u000e!91QF\u0005\u0005B\r=\u0002\u0002CB\u001e\u0013\u0011\u0005AJa\u0010\t\u000f\r}\u0012\u0002\"\u0011\u0004B\u0005I\u0012i\u0019;pe6\u000bG/\u001a:jC2L'0\u001a:TKR$\u0018N\\4t\u0015\tYE*\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001b\u0006!\u0011m[6b\u0007\u0001\u0001\"\u0001U\u0001\u000e\u0003)\u0013\u0011$Q2u_Jl\u0015\r^3sS\u0006d\u0017N_3s'\u0016$H/\u001b8hgN\u0011\u0011a\u0015\t\u0003)^k\u0011!\u0016\u0006\u0002-\u0006)1oY1mC&\u0011\u0001,\u0016\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005y\u0015!B1qa2LH#F/\u0004D\r\u00153qIB%\u0007\u0017\u001aiea\u0014\u0004R\rM3Q\u000b\t\u0003!&\u0019\"!C*\u0002-%t\u0017\u000e^5bY&s\u0007/\u001e;Ck\u001a4WM]*ju\u0016,\u0012!\u0019\t\u0003)\nL!aY+\u0003\u0007%sG\u000f\u000b\u0004\u000bK\"L7\u000e\u001c\t\u0003)\u001aL!aZ+\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003)\f\u0011*V:fA\u0005$HO]5ckR,\u0007eJ!uiJL'-\u001e;fg:Je\u000e];u\u0005V4g-\u001a:(AQ|\u0007E]3bI\u0002\"\b.\u001a\u0011d_:\u001c'/\u001a;fAM,G\u000f^5oO\u00022\u0018\r\\;f\u0003\u0015\u0019\u0018N\\2fC\u0005i\u0017!\u0002\u001a/m9\u0002\u0014aF5oSRL\u0017\r\\%oaV$()\u001e4gKJ\u001c\u0016N_3!Q\u0019YQ\r[5lY\u0006\u0011R.\u0019=J]B,HOQ;gM\u0016\u00148+\u001b>fQ\u0019aQ\r[5lY\u0006\u0019R.\u0019=J]B,HOQ;gM\u0016\u00148+\u001b>fA!2Q\"\u001a5jW2\f!\u0002Z5ta\u0006$8\r[3s+\u00051\bCA<\u007f\u001d\tAH\u0010\u0005\u0002z+6\t!P\u0003\u0002|\u001d\u00061AH]8pizJ!!`+\u0002\rA\u0013X\rZ3g\u0013\ry\u0018\u0011\u0001\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005u,\u0006f\u0002\bfQ\u0006\u00151\u000e\\\u0011\u0003\u0003\u000f\tQ*V:fA\u0005$HO]5ckR,\u0007eJ!di>\u0014\u0018\t\u001e;sS\n,H/Z:/\t&\u001c\b/\u0019;dQ\u0016\u0014x\u0005\t;pAI,\u0017\r\u001a\u0011uQ\u0016\u00043m\u001c8de\u0016$X\rI:fiRLgn\u001a\u0011wC2,X-A\u0006eSN\u0004\u0018\r^2iKJ\u0004\u0003fB\bfQ\u0006\u00151\u000e\\\u0001\u0013gV\u0004XM\u001d<jg&|g\u000eR3dS\u0012,'/\u0006\u0002\u0002\u0012A!\u00111CA\r\u001d\r\u0001\u0016QC\u0005\u0004\u0003/Q\u0015aC*va\u0016\u0014h/[:j_:LA!a\u0007\u0002\u001e\t9A)Z2jI\u0016\u0014(bAA\f\u0015\":\u0001#\u001a5\u0002\"-d\u0017EAA\u0012\u0003Y+6/\u001a\u0011biR\u0014\u0018NY;uK\u0002:\u0013i\u0019;pe\u0006#HO]5ckR,7OL*va\u0016\u0014h/[:j_:\u001cFO]1uK\u001eLx\u0005\t;pAI,\u0017\r\u001a\u0011uQ\u0016\u00043m\u001c8de\u0016$X\rI:fiRLgn\u001a\u0011wC2,X-A\ntkB,'O^5tS>tG)Z2jI\u0016\u0014\b\u0005K\u0004\u0012K\"\f\tc\u001b7\u00027M,(m]2sSB$\u0018n\u001c8US6,w.\u001e;TKR$\u0018N\\4t+\t\ti\u0003E\u0002Q\u0003_I1!!\rK\u0005\u0005\u001aFO]3b[N+(m]2sSB$\u0018n\u001c8US6,w.\u001e;TKR$\u0018N\\4t\u0003q\u0019XOY:de&\u0004H/[8o)&lWm\\;u'\u0016$H/\u001b8hg\u0002\nA\u0002Z3ck\u001edunZ4j]\u001e,\"!!\u000f\u0011\u0007Q\u000bY$C\u0002\u0002>U\u0013qAQ8pY\u0016\fg\u000eK\u0004\u0015K\"\f\te\u001b7\"\u0005\u0005\r\u0013aT+tK\u0002\nG\u000f\u001e:jEV$X\rI\u0014BGR|'/\u0011;ue&\u0014W\u000f^3t]\u0011+'-^4M_\u001e<\u0017N\\4(AQ|\u0007E]3bI\u0002\"\b.\u001a\u0011d_:\u001c'/\u001a;fAM,G\u000f^5oO\u00022\u0018\r\\;f\u00035!WMY;h\u0019><w-\u001b8hA!:Q#\u001a5\u0002B-d\u0017\u0001E8viB,HOQ;sgRd\u0015.\\5uQ\u001d1R\r[A'W2\f#!a\u0014\u0002'V\u001bX\rI1uiJL'-\u001e;fA\u001d\n5\r^8s\u0003R$(/\u001b2vi\u0016\u001chfT;uaV$()\u001e:ti2KW.\u001b;(AQ|\u0007E]3bI\u0002\"\b.\u001a\u0011d_:\u001c'/\u001a;fAM,G\u000f^5oO\u00022\u0018\r\\;f\u0003EyW\u000f\u001e9vi\n+(o\u001d;MS6LG\u000f\t\u0015\b/\u0015D\u0017QJ6m\u0003-1WO\u001f>j]\u001elu\u000eZ3)\u000fa)\u0007.!\u0017lY\u0006\u0012\u00111L\u0001O+N,\u0007%\u0019;ue&\u0014W\u000f^3!O\u0005\u001bGo\u001c:BiR\u0014\u0018NY;uKNtc)\u001e>{S:<Wj\u001c3fO\u0001\"x\u000e\t:fC\u0012\u0004C\u000f[3!G>t7M]3uK\u0002\u001aX\r\u001e;j]\u001e\u0004c/\u00197vK\u0006aa-\u001e>{S:<Wj\u001c3fA!:\u0011$\u001a5\u0002Z-d\u0017AC1vi>4Uo]5oO\":!$\u001a5\u0002f-d\u0017EAA4\u0003aqu\u000e\t7p]\u001e,'\u000f\t5bg\u0002\ng.\u001f\u0011fM\u001a,7\r^\u0001\fCV$xNR;tS:<\u0007\u0005K\u0004\u001cK\"\f)g\u001b7\u0002%5\f\u0007PR5yK\u0012\u0014UO\u001a4feNK'0\u001a\u0015\b9\u0015D\u0017\u0011O6mC\t\t\u0019(A+Vg\u0016\u0004\u0013\r\u001e;sS\n,H/\u001a\u0011(\u0003\u000e$xN]!uiJL'-\u001e;fg:j\u0015\r\u001f$jq\u0016$')\u001e4gKJ\u001c\u0016N_3(AQ|\u0007E]3bI\u0002\"\b.\u001a\u0011d_:\u001c'/\u001a;fAM,G\u000f^5oO\u00022\u0018\r\\;f\u0003Mi\u0017\r\u001f$jq\u0016$')\u001e4gKJ\u001c\u0016N_3!Q\u001diR\r[A9W2\f1c]=oGB\u0013xnY3tg&tw\rT5nSRDsAH3i\u0003{ZG.\t\u0002\u0002\u0000\u00051Vk]3!CR$(/\u001b2vi\u0016\u0004s%Q2u_J\fE\u000f\u001e:jEV$Xm\u001d\u0018Ts:\u001c\u0007K]8dKN\u001c\u0018N\\4MS6LGo\n\u0011u_\u0002\u0012X-\u00193!i\",\u0007eY8oGJ,G/\u001a\u0011tKR$\u0018N\\4!m\u0006dW/Z\u0001\u0015gft7\r\u0015:pG\u0016\u001c8/\u001b8h\u0019&l\u0017\u000e\u001e\u0011)\u000f})\u0007.! lY\u0006Q\u0011n\\*fiRLgnZ:\u0016\u0005\u0005%\u0005c\u0001)\u0002\f&\u0019\u0011Q\u0012&\u0003\u0015%{5+\u001a;uS:<7/A\u0006j_N+G\u000f^5oON\u0004\u0013!E:ue\u0016\fWNU3g'\u0016$H/\u001b8hgV\u0011\u0011Q\u0013\t\u0004!\u0006]\u0015bAAM\u0015\n\t2\u000b\u001e:fC6\u0014VMZ*fiRLgnZ:\u0002%M$(/Z1n%\u001647+\u001a;uS:<7\u000fI\u0001\u0015E2|7m[5oO&{G)[:qCR\u001c\u0007.\u001a:)\u000f\u0011*\u0007.!)lY\u0006\u0012\u00111U\u0001X+N,\u0007%\u0019;ue&\u0014W\u000f^3!O\u0005\u001bGo\u001c:BiR\u0014\u0018NY;uKNt#\t\\8dW&tw-S8ESN\u0004\u0018\r^2iKJ<\u0003\u0005^8!e\u0016\fG\r\t;iK\u0002\u001awN\\2sKR,\u0007e]3ui&tw\r\t<bYV,\u0017!\u00062m_\u000e\\\u0017N\\4J_\u0012K7\u000f]1uG\",'\u000f\t\u0015\bK\u0015D\u0017\u0011U6m)ui\u00161VAW\u0003_\u000b\t,a-\u00026\u0006]\u0016\u0011XA^\u0003{\u000by,!1\u0002D\u0006\u0015\u0007\"B0'\u0001\u0004\t\u0007\"\u00029'\u0001\u0004\t\u0007\"\u0002;'\u0001\u00041\bbBA\u0007M\u0001\u0007\u0011\u0011\u0003\u0005\b\u0003S1\u0003\u0019AA\u0017\u0011\u001d\t)D\na\u0001\u0003sAa!!\u0013'\u0001\u0004\t\u0007bBA+M\u0001\u0007\u0011\u0011\b\u0005\b\u0003C2\u0003\u0019AA\u001d\u0011\u0019\tiG\na\u0001C\"1\u0011\u0011\u0010\u0014A\u0002\u0005Dq!!\"'\u0001\u0004\tI\tC\u0004\u0002\u0012\u001a\u0002\r!!&\t\r\u0005ue\u00051\u0001wQ\r1\u0013\u0011\u001a\t\u0005\u0003\u0017\f\t.\u0004\u0002\u0002N*\u0019\u0011q\u001a'\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002T\u00065'aC%oi\u0016\u0014h.\u00197Ba&\fAaY8qsRiR,!7\u0002\\\u0006u\u0017q\\Aq\u0003G\f)/a:\u0002j\u0006-\u0018Q^Ax\u0003c\f\u0019\u0010C\u0004`OA\u0005\t\u0019A1\t\u000fA<\u0003\u0013!a\u0001C\"9Ao\nI\u0001\u0002\u00041\b\"CA\u0007OA\u0005\t\u0019AA\t\u0011%\tIc\nI\u0001\u0002\u0004\ti\u0003C\u0005\u00026\u001d\u0002\n\u00111\u0001\u0002:!A\u0011\u0011J\u0014\u0011\u0002\u0003\u0007\u0011\rC\u0005\u0002V\u001d\u0002\n\u00111\u0001\u0002:!I\u0011\u0011M\u0014\u0011\u0002\u0003\u0007\u0011\u0011\b\u0005\t\u0003[:\u0003\u0013!a\u0001C\"A\u0011\u0011P\u0014\u0011\u0002\u0003\u0007\u0011\rC\u0005\u0002\u0006\u001e\u0002\n\u00111\u0001\u0002\n\"I\u0011\u0011S\u0014\u0011\u0002\u0003\u0007\u0011Q\u0013\u0005\t\u0003;;\u0003\u0013!a\u0001m\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA}U\r\t\u00171`\u0016\u0003\u0003{\u0004B!a@\u0003\b5\u0011!\u0011\u0001\u0006\u0005\u0005\u0007\u0011)!A\u0005v]\u000eDWmY6fI*\u0019\u0011qZ+\n\t\t%!\u0011\u0001\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\t\u0011\tBK\u0002w\u0003w\fabY8qs\u0012\"WMZ1vYR$C'\u0006\u0002\u0003\u0018)\"\u0011\u0011CA~\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIU*\"A!\b+\t\u00055\u00121`\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+\t\u0011\u0019C\u000b\u0003\u0002:\u0005m\u0018AD2paf$C-\u001a4bk2$HeN\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIe\nqbY8qs\u0012\"WMZ1vYR$\u0013\u0007M\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132c\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\n$'\u0006\u0002\u00034)\"\u0011\u0011RA~\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\u001aTC\u0001B\u001dU\u0011\t)*a?\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cQ\nA\"Y:BiR\u0014\u0018NY;uKN,\"A!\u0011\u0011\u0007A\u0013\u0019%C\u0002\u0003F)\u0013!\"\u0011;ue&\u0014W\u000f^3t\u00035\t7/\u0011;ue&\u0014W\u000f^3tA\u0005yq/\u001b;i\u0013:\u0004X\u000f\u001e\"vM\u001a,'\u000fF\u0003^\u0005\u001b\u0012\t\u0006\u0003\u0004\u0003Pa\u0002\r!Y\u0001\fS:LG/[1m'&TX\r\u0003\u0004\u0003Ta\u0002\r!Y\u0001\b[\u0006D8+\u001b>fQ\u001dAT\r\u001bB,W2\f#A!\u0017\u0002}U\u001bX\rI1uiJL'-\u001e;fA\u001d\nE\u000f\u001e:jEV$Xm\u001d\u0018J]B,HOQ;gM\u0016\u0014x\u0005\t;pA\rD\u0017M\\4fAM,G\u000f^5oO\u00022\u0018\r\\;f\u000399\u0018\u000e\u001e5ESN\u0004\u0018\r^2iKJ$2!\u0018B0\u0011\u0015!\u0018\b1\u0001wQ\u001dIT\r\u001bB2W2\f#A!\u001a\u0002\u0005V\u001bX\rI1uiJL'-\u001e;fA\u001d\n5\r^8s\u0003R$(/\u001b2vi\u0016\u001ch\u0006R5ta\u0006$8\r[3sO\u0001\"x\u000eI2iC:<W\rI:fiRLgn\u001a\u0011wC2,X-A\fxSRD7+\u001e9feZL7/[8o'R\u0014\u0018\r^3hsR\u0019QLa\u001b\t\u000f\t5$\b1\u0001\u0002\u0012\u00059A-Z2jI\u0016\u0014\bf\u0002\u001efQ\nE4\u000e\\\u0011\u0003\u0005g\n1*V:fA\u0005$HO]5ckR,\u0007eJ!di>\u0014\u0018\t\u001e;sS\n,H/Z:/gV\u0004XM\u001d<jg&|gn\u0015;sCR,w-_\u0014!i>\u00043\r[1oO\u0016\u00043/\u001a;uS:<\u0007E^1mk\u0016$2!\u0018B<\u0011\u001d\u0011ig\u000fa\u0001\u0005s\u0002\u0002Ba\u001f\u0003\u0006\n%%1T\u0007\u0003\u0005{RAAa \u0003\u0002\u0006Aa-\u001e8di&|gNC\u0002\u0003\u00042\u000bAA[1qS&!!q\u0011B?\u0005!1UO\\2uS>t\u0007\u0003\u0002BF\u0005+sAA!$\u0003\u0012:\u0019\u0011Pa$\n\u0003YK1Aa%V\u0003\u001d\u0001\u0018mY6bO\u0016LAAa&\u0003\u001a\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0004\u0005'+\u0006\u0003BA\n\u0005;KAAa(\u0002\u001e\tIA)\u001b:fGRLg/\u001a\u0015\bw\u0015D'1U6mC\t\u0011)+A&Vg\u0016\u0004\u0013\r\u001e;sS\n,H/\u001a\u0011(\u0003\u000e$xN]!uiJL'-\u001e;fg:\u001aV\u000f]3sm&\u001c\u0018n\u001c8TiJ\fG/Z4zO\u0001\"x\u000eI2iC:<W\rI:fiRLgn\u001a\u0011wC2,X-A\u0006xSRDg)\u001e>{S:<GcA/\u0003,\"9!Q\u0016\u001fA\u0002\u0005e\u0012AB3oC\ndW\rK\u0004=K\"\u0014\tl\u001b7\"\u0005\tM\u0016aQ+tK\u0002\nG\u000f\u001e:jEV$X\rI\u0014BGR|'/\u0011;ue&\u0014W\u000f^3t]\u0019+(P_5oO6{G-Z\u0014!i>\u00043\r[1oO\u0016\u00043/\u001a;uS:<\u0007E^1mk\u0016\fAc^5uQ>+H\u000f];u\u0005V\u00148\u000f\u001e'j[&$HcA/\u0003:\"1!1X\u001fA\u0002\u0005\fQ\u0001\\5nSRDs!P3i\u0005\u007f[G.\t\u0002\u0003B\u0006AUk]3!CR$(/\u001b2vi\u0016\u0004s%Q2u_J\fE\u000f\u001e:jEV$Xm\u001d\u0018PkR\u0004X\u000f\u001e\"veN$H*[7ji\u001e\u0002Co\u001c\u0011dQ\u0006tw-\u001a\u0011tKR$\u0018N\\4!m\u0006dW/Z\u0001\u0018o&$\bnU=oGB\u0013xnY3tg&tw\rT5nSR$2!\u0018Bd\u0011\u0019\u0011YL\u0010a\u0001C\":a(\u001a5\u0003L.d\u0017E\u0001Bg\u0003-+6/\u001a\u0011biR\u0014\u0018NY;uK\u0002:\u0013i\u0019;pe\u0006#HO]5ckR,7OL*z]\u000e\u0004&o\\2fgNLgn\u001a'j[&$x\u0005\t;pA\rD\u0017M\\4fAM,G\u000f^5oO\u00022\u0018\r\\;f\u0003A9\u0018\u000e\u001e5EK\n,x\rT8hO&tw\rF\u0002^\u0005'DqA!,@\u0001\u0004\tI\u0004K\u0004@K\"\u00149n\u001b7\"\u0005\te\u0017\u0001R+tK\u0002\nG\u000f\u001e:jEV$X\rI\u0014BGR|'/\u0011;ue&\u0014W\u000f^3t]\u0011+'-^4M_\u001e<\u0017N\\4(AQ|\u0007e\u00195b]\u001e,\u0007e]3ui&tw\r\t<bYV,\u0017AF<ji\"l\u0015\r\u001f$jq\u0016$')\u001e4gKJ\u001c\u0016N_3\u0015\u0007u\u0013y\u000e\u0003\u0004\u0003b\u0002\u0003\r!Y\u0001\u0005g&TX\rK\u0004AK\"\u0014)o\u001b7\"\u0005\t\u001d\u0018AS+tK\u0002\nG\u000f\u001e:jEV$X\rI\u0014BGR|'/\u0011;ue&\u0014W\u000f^3t]5\u000b\u0007PR5yK\u0012\u0014UO\u001a4feNK'0Z\u0014!i>\u00043\r[1oO\u0016\u00043/\u001a;uS:<\u0007E^1mk\u0016\fqd^5uQN+(m]2sSB$\u0018n\u001c8US6,w.\u001e;TKR$\u0018N\\4t)\ri&Q\u001e\u0005\b\u0005_\f\u0005\u0019AA\u0017\u0003!\u0019X\r\u001e;j]\u001e\u001c\u0018AD<ji\"LujU3ui&twm\u001d\u000b\u0004;\nU\bbBAC\u0005\u0002\u0007\u0011\u0011R\u0001\u0016o&$\bn\u0015;sK\u0006l'+\u001a4TKR$\u0018N\\4t)\ri&1 \u0005\b\u0003#\u001b\u0005\u0019AAK\u0003a9\u0018\u000e\u001e5CY>\u001c7.\u001b8h\u0013>$\u0015n\u001d9bi\u000eDWM\u001d\u000b\u0004;\u000e\u0005\u0001BBB\u0002\t\u0002\u0007a/A\foK^\u0014En\\2lS:<\u0017j\u001c#jgB\fGo\u00195fe\":A)\u001a5\u0004\b-d\u0017EAB\u0005\u00031+6/\u001a\u0011biR\u0014\u0018NY;uK\u0002:\u0013i\u0019;pe\u0006#HO]5ckR,7O\f\"m_\u000e\\\u0017N\\4J_\u0012K7\u000f]1uG\",'o\n\u0011u_\u0002\u001a\u0007.\u00198hK\u0002\u001aX\r\u001e;j]\u001e\u0004c/\u00197vK\u0006\t\"/Z9vSJ,\u0007k\\<fe>3Gk^8\u0015\r\r=1QCB\u0015!\r!6\u0011C\u0005\u0004\u0007')&\u0001B+oSRDqaa\u0006F\u0001\u0004\u0019I\"A\u0001o!\u0011\u0019Yb!\n\u000e\u0005\ru!\u0002BB\u0010\u0007C\tA\u0001\\1oO*\u001111E\u0001\u0005U\u00064\u0018-\u0003\u0003\u0004(\ru!aB%oi\u0016<WM\u001d\u0005\u0007\u0007W)\u0005\u0019\u0001<\u0002\t9\fW.Z\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005e2\u0011\u0007\u0005\b\u0007g1\u0005\u0019AB\u001b\u0003\u0015yG\u000f[3s!\r!6qG\u0005\u0004\u0007s)&aA!os\u0006aAo\\!uiJL'-\u001e;fg\"\u001aq)!3\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012A\u001e\u0005\u0006?\u000e\u0001\r!\u0019\u0005\u0006a\u000e\u0001\r!\u0019\u0005\u0006i\u000e\u0001\rA\u001e\u0005\b\u0003\u001b\u0019\u0001\u0019AA\t\u0011\u001d\tIc\u0001a\u0001\u0003[Aq!!\u000e\u0004\u0001\u0004\tI\u0004\u0003\u0004\u0002J\r\u0001\r!\u0019\u0005\b\u0003+\u001a\u0001\u0019AA\u001d\u0011\u001d\t\tg\u0001a\u0001\u0003sAa!!\u001c\u0004\u0001\u0004\t\u0007fB\u0002fQ\u000ee3\u000e\\\u0011\u0003\u00077\n\u0011QI+tK\u0002\u001awN\u001c4jO\u0002z'\u000fI1uiJL'-\u001e;fg\u0002\"x\u000eI2p]\u001aLw-\u001e:fAQDW\rI7bi\u0016\u0014\u0018.\u00197ju\u0016\u0014h\u0006I*fK\u0002j\u0017n\u001a:bi&|g\u000eI4vS\u0012,\u0007EZ8sA\u0011,G/Y5mg\u0002BG\u000f\u001e9tu=zCm\\2/C.\\\u0017ML5p_\u0011|7m]\u0018bW.\fwF\r\u00187_A\u0014xN[3di>j\u0017n\u001a:bi&|g.L4vS\u0012,WF\r\u00186]al#G\f\u001c/q:BG/\u001c7\u0015\u0007u\u001by\u0006C\u0004\u0004b\u0011\u0001\raa\u0019\u0002\rML8\u000f^3n!\u0011\u0019)ga\u001b\u000e\u0005\r\u001d$bAB5\u0019\u0006)\u0011m\u0019;pe&!1QNB4\u0005-\t5\r^8s'f\u001cH/Z7)\u000f\u0011)\u0007n!\u0017lYR\u0019Qla\u001d\t\u000f\rUT\u00011\u0001\u0004x\u000511m\u001c8gS\u001e\u0004Ba!\u001f\u0004\u00066\u001111\u0010\u0006\u0005\u0007k\u001aiH\u0003\u0003\u0004\u0000\r\u0005\u0015\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0005\r\r\u0015aA2p[&!1qQB>\u0005\u0019\u0019uN\u001c4jO\":Q!\u001a5\u0004Z-d\u0017AB2sK\u0006$X\rF\u000b^\u0007\u001f\u001b\tja%\u0004\u0016\u000e]5\u0011TBN\u0007;\u001byj!)\t\u000b}3\u0001\u0019A1\t\u000bA4\u0001\u0019A1\t\u000bQ4\u0001\u0019\u0001<\t\u000f\u00055a\u00011\u0001\u0002\u0012!9\u0011\u0011\u0006\u0004A\u0002\u00055\u0002bBA\u001b\r\u0001\u0007\u0011\u0011\b\u0005\u0007\u0003\u00132\u0001\u0019A1\t\u000f\u0005Uc\u00011\u0001\u0002:!9\u0011\u0011\r\u0004A\u0002\u0005e\u0002BBA7\r\u0001\u0007\u0011\rK\u0004\u0007K\"\u001cIf\u001b7\u0015\u0007u\u001b9\u000bC\u0004\u0004b\u001d\u0001\raa\u0019)\u000f\u001d)\u0007n!\u0017lYR\u0019Ql!,\t\u000f\rU\u0004\u00021\u0001\u0004x!:\u0001\"\u001a5\u0004Z-d\u0007")
public final class ActorMaterializerSettings {
    private final int initialInputBufferSize;
    private final int maxInputBufferSize;
    private final String dispatcher;
    private final Function1<Throwable, Supervision.Directive> supervisionDecider;
    private final StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings;
    private final boolean debugLogging;
    private final int outputBurstLimit;
    private final boolean fuzzingMode;
    private final boolean autoFusing;
    private final int maxFixedBufferSize;
    private final int syncProcessingLimit;
    private final IOSettings ioSettings;
    private final StreamRefSettings streamRefSettings;
    private final String blockingIoDispatcher;
    private final Attributes asAttributes;

    public static ActorMaterializerSettings create(Config config) {
        return ActorMaterializerSettings$.MODULE$.create(config);
    }

    public static ActorMaterializerSettings create(ActorSystem system) {
        return ActorMaterializerSettings$.MODULE$.create(system);
    }

    public static ActorMaterializerSettings create(int initialInputBufferSize, int maxInputBufferSize, String dispatcher, Function1<Throwable, Supervision.Directive> supervisionDecider, StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings, boolean debugLogging, int outputBurstLimit, boolean fuzzingMode, boolean autoFusing, int maxFixedBufferSize) {
        return ActorMaterializerSettings$.MODULE$.create(initialInputBufferSize, maxInputBufferSize, dispatcher, supervisionDecider, subscriptionTimeoutSettings, debugLogging, outputBurstLimit, fuzzingMode, autoFusing, maxFixedBufferSize);
    }

    public static ActorMaterializerSettings apply(Config config) {
        return ActorMaterializerSettings$.MODULE$.apply(config);
    }

    public static ActorMaterializerSettings apply(ActorSystem system) {
        return ActorMaterializerSettings$.MODULE$.apply(system);
    }

    public static ActorMaterializerSettings apply(int initialInputBufferSize, int maxInputBufferSize, String dispatcher, Function1<Throwable, Supervision.Directive> supervisionDecider, StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings, boolean debugLogging, int outputBurstLimit, boolean fuzzingMode, boolean autoFusing, int maxFixedBufferSize) {
        return ActorMaterializerSettings$.MODULE$.apply(initialInputBufferSize, maxInputBufferSize, dispatcher, supervisionDecider, subscriptionTimeoutSettings, debugLogging, outputBurstLimit, fuzzingMode, autoFusing, maxFixedBufferSize);
    }

    public int initialInputBufferSize() {
        return this.initialInputBufferSize;
    }

    public int maxInputBufferSize() {
        return this.maxInputBufferSize;
    }

    public String dispatcher() {
        return this.dispatcher;
    }

    public Function1<Throwable, Supervision.Directive> supervisionDecider() {
        return this.supervisionDecider;
    }

    public StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings() {
        return this.subscriptionTimeoutSettings;
    }

    public boolean debugLogging() {
        return this.debugLogging;
    }

    public int outputBurstLimit() {
        return this.outputBurstLimit;
    }

    public boolean fuzzingMode() {
        return this.fuzzingMode;
    }

    public boolean autoFusing() {
        return this.autoFusing;
    }

    public int maxFixedBufferSize() {
        return this.maxFixedBufferSize;
    }

    public int syncProcessingLimit() {
        return this.syncProcessingLimit;
    }

    public IOSettings ioSettings() {
        return this.ioSettings;
    }

    public StreamRefSettings streamRefSettings() {
        return this.streamRefSettings;
    }

    public String blockingIoDispatcher() {
        return this.blockingIoDispatcher;
    }

    private ActorMaterializerSettings copy(int initialInputBufferSize, int maxInputBufferSize, String dispatcher, Function1<Throwable, Supervision.Directive> supervisionDecider, StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings, boolean debugLogging, int outputBurstLimit, boolean fuzzingMode, boolean autoFusing, int maxFixedBufferSize, int syncProcessingLimit, IOSettings ioSettings, StreamRefSettings streamRefSettings, String blockingIoDispatcher) {
        return new ActorMaterializerSettings(initialInputBufferSize, maxInputBufferSize, dispatcher, supervisionDecider, subscriptionTimeoutSettings, debugLogging, outputBurstLimit, fuzzingMode, autoFusing, maxFixedBufferSize, syncProcessingLimit, ioSettings, streamRefSettings, blockingIoDispatcher);
    }

    private int copy$default$1() {
        return this.initialInputBufferSize();
    }

    private int copy$default$2() {
        return this.maxInputBufferSize();
    }

    private String copy$default$3() {
        return this.dispatcher();
    }

    private Function1<Throwable, Supervision.Directive> copy$default$4() {
        return this.supervisionDecider();
    }

    private StreamSubscriptionTimeoutSettings copy$default$5() {
        return this.subscriptionTimeoutSettings();
    }

    private boolean copy$default$6() {
        return this.debugLogging();
    }

    private int copy$default$7() {
        return this.outputBurstLimit();
    }

    private boolean copy$default$8() {
        return this.fuzzingMode();
    }

    private boolean copy$default$9() {
        return this.autoFusing();
    }

    private int copy$default$10() {
        return this.maxFixedBufferSize();
    }

    private int copy$default$11() {
        return this.syncProcessingLimit();
    }

    private IOSettings copy$default$12() {
        return this.ioSettings();
    }

    private StreamRefSettings copy$default$13() {
        return this.streamRefSettings();
    }

    private String copy$default$14() {
        return this.blockingIoDispatcher();
    }

    private Attributes asAttributes() {
        return this.asAttributes;
    }

    public ActorMaterializerSettings withInputBuffer(int initialSize, int maxSize) {
        if (initialSize == this.initialInputBufferSize() && maxSize == this.maxInputBufferSize()) {
            return this;
        }
        return this.copy(initialSize, maxSize, this.copy$default$3(), this.copy$default$4(), this.copy$default$5(), this.copy$default$6(), this.copy$default$7(), this.copy$default$8(), this.copy$default$9(), this.copy$default$10(), this.copy$default$11(), this.copy$default$12(), this.copy$default$13(), this.copy$default$14());
    }

    public ActorMaterializerSettings withDispatcher(String dispatcher) {
        String string = this.dispatcher();
        String string2 = dispatcher;
        if (!(string != null ? !string.equals(string2) : string2 != null)) {
            return this;
        }
        String x$1 = dispatcher;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        Function1<Throwable, Supervision.Directive> x$4 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$5 = this.copy$default$5();
        boolean x$6 = this.copy$default$6();
        int x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        boolean x$9 = this.copy$default$9();
        int x$10 = this.copy$default$10();
        int x$11 = this.copy$default$11();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$1, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withSupervisionStrategy(Function1<Throwable, Supervision.Directive> decider) {
        if (decider == this.supervisionDecider()) {
            return this;
        }
        Function1<Throwable, Supervision.Directive> x$1 = decider;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        StreamSubscriptionTimeoutSettings x$5 = this.copy$default$5();
        boolean x$6 = this.copy$default$6();
        int x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        boolean x$9 = this.copy$default$9();
        int x$10 = this.copy$default$10();
        int x$11 = this.copy$default$11();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$1, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withSupervisionStrategy(Function<Throwable, Supervision.Directive> decider) {
        Function1<Throwable, Supervision.Directive> function1;
        Function<Throwable, Supervision.Directive> function = decider;
        Function1<Throwable, Supervision.Directive> function12 = Supervision$.MODULE$.resumingDecider();
        Function<Throwable, Supervision.Directive> function2 = function;
        if (!(function12 != null ? !function12.equals(function2) : function2 != null)) {
            function1 = Supervision$.MODULE$.resumingDecider();
        } else {
            Function1<Throwable, Supervision.Directive> function13 = Supervision$.MODULE$.restartingDecider();
            Function<Throwable, Supervision.Directive> function3 = function;
            if (!(function13 != null ? !function13.equals(function3) : function3 != null)) {
                function1 = Supervision$.MODULE$.restartingDecider();
            } else {
                Function1<Throwable, Supervision.Directive> function14 = Supervision$.MODULE$.stoppingDecider();
                Function<Throwable, Supervision.Directive> function4 = function;
                function1 = !(function14 != null ? !function14.equals(function4) : function4 != null) ? Supervision$.MODULE$.stoppingDecider() : param2 -> (Supervision.Directive)function.apply((Throwable)param2);
            }
        }
        Function1<Throwable, Supervision.Directive> x$1 = function1;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        StreamSubscriptionTimeoutSettings x$5 = this.copy$default$5();
        boolean x$6 = this.copy$default$6();
        int x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        boolean x$9 = this.copy$default$9();
        int x$10 = this.copy$default$10();
        int x$11 = this.copy$default$11();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$1, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withFuzzing(boolean enable) {
        if (enable == this.fuzzingMode()) {
            return this;
        }
        boolean x$1 = enable;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        int x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$9();
        int x$10 = this.copy$default$10();
        int x$11 = this.copy$default$11();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$1, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withOutputBurstLimit(int limit) {
        if (limit == this.outputBurstLimit()) {
            return this;
        }
        int x$1 = limit;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$8();
        boolean x$9 = this.copy$default$9();
        int x$10 = this.copy$default$10();
        int x$11 = this.copy$default$11();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withSyncProcessingLimit(int limit) {
        if (limit == this.syncProcessingLimit()) {
            return this;
        }
        int x$1 = limit;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        int x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        boolean x$10 = this.copy$default$9();
        int x$11 = this.copy$default$10();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$1, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withDebugLogging(boolean enable) {
        if (enable == this.debugLogging()) {
            return this;
        }
        boolean x$1 = enable;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$6 = this.copy$default$5();
        int x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        boolean x$9 = this.copy$default$9();
        int x$10 = this.copy$default$10();
        int x$11 = this.copy$default$11();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withMaxFixedBufferSize(int size) {
        if (size == this.maxFixedBufferSize()) {
            return this;
        }
        int x$1 = size;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        int x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        boolean x$10 = this.copy$default$9();
        int x$11 = this.copy$default$11();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$1, x$11, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withSubscriptionTimeoutSettings(StreamSubscriptionTimeoutSettings settings) {
        StreamSubscriptionTimeoutSettings streamSubscriptionTimeoutSettings = settings;
        StreamSubscriptionTimeoutSettings streamSubscriptionTimeoutSettings2 = this.subscriptionTimeoutSettings();
        if (!(streamSubscriptionTimeoutSettings != null ? !((Object)streamSubscriptionTimeoutSettings).equals(streamSubscriptionTimeoutSettings2) : streamSubscriptionTimeoutSettings2 != null)) {
            return this;
        }
        StreamSubscriptionTimeoutSettings x$1 = settings;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$6();
        int x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        boolean x$9 = this.copy$default$9();
        int x$10 = this.copy$default$10();
        int x$11 = this.copy$default$11();
        IOSettings x$12 = this.copy$default$12();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public ActorMaterializerSettings withIOSettings(IOSettings ioSettings) {
        IOSettings iOSettings = ioSettings;
        IOSettings iOSettings2 = this.ioSettings();
        if (!(iOSettings != null ? !((Object)iOSettings).equals(iOSettings2) : iOSettings2 != null)) {
            return this;
        }
        IOSettings x$1 = ioSettings;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        int x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        boolean x$10 = this.copy$default$9();
        int x$11 = this.copy$default$10();
        int x$12 = this.copy$default$11();
        StreamRefSettings x$13 = this.copy$default$13();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$1, x$13, x$14);
    }

    public ActorMaterializerSettings withStreamRefSettings(StreamRefSettings streamRefSettings) {
        StreamRefSettings streamRefSettings2 = streamRefSettings;
        StreamRefSettings streamRefSettings3 = this.streamRefSettings();
        if (!(streamRefSettings2 != null ? !streamRefSettings2.equals(streamRefSettings3) : streamRefSettings3 != null)) {
            return this;
        }
        StreamRefSettings x$1 = streamRefSettings;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        int x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        boolean x$10 = this.copy$default$9();
        int x$11 = this.copy$default$10();
        int x$12 = this.copy$default$11();
        IOSettings x$13 = this.copy$default$12();
        String x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$1, x$14);
    }

    public ActorMaterializerSettings withBlockingIoDispatcher(String newBlockingIoDispatcher) {
        String string = newBlockingIoDispatcher;
        String string2 = this.blockingIoDispatcher();
        if (!(string != null ? !string.equals(string2) : string2 != null)) {
            return this;
        }
        String x$1 = newBlockingIoDispatcher;
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Function1<Throwable, Supervision.Directive> x$5 = this.copy$default$4();
        StreamSubscriptionTimeoutSettings x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        int x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        boolean x$10 = this.copy$default$9();
        int x$11 = this.copy$default$10();
        int x$12 = this.copy$default$11();
        IOSettings x$13 = this.copy$default$12();
        StreamRefSettings x$14 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14, x$1);
    }

    private void requirePowerOfTwo(Integer n, String name) {
        Predef$.MODULE$.require(Predef$.MODULE$.Integer2int(n) > 0, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(12).append(name).append(" must be > 0").toString());
        Predef$.MODULE$.require((Predef$.MODULE$.Integer2int(n) & Predef$.MODULE$.Integer2int(n) - 1) == 0, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(23).append(name).append(" must be a power of two").toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object other) {
        Object object = other;
        if (!(object instanceof ActorMaterializerSettings)) return false;
        ActorMaterializerSettings actorMaterializerSettings = (ActorMaterializerSettings)object;
        if (actorMaterializerSettings.initialInputBufferSize() != this.initialInputBufferSize()) return false;
        if (actorMaterializerSettings.maxInputBufferSize() != this.maxInputBufferSize()) return false;
        String string = actorMaterializerSettings.dispatcher();
        String string2 = this.dispatcher();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        Function1<Throwable, Supervision.Directive> function1 = actorMaterializerSettings.supervisionDecider();
        Function1<Throwable, Supervision.Directive> function12 = this.supervisionDecider();
        if (function1 == null) {
            if (function12 != null) {
                return false;
            }
        } else if (!function1.equals(function12)) return false;
        StreamSubscriptionTimeoutSettings streamSubscriptionTimeoutSettings = actorMaterializerSettings.subscriptionTimeoutSettings();
        StreamSubscriptionTimeoutSettings streamSubscriptionTimeoutSettings2 = this.subscriptionTimeoutSettings();
        if (streamSubscriptionTimeoutSettings == null) {
            if (streamSubscriptionTimeoutSettings2 != null) {
                return false;
            }
        } else if (!((Object)streamSubscriptionTimeoutSettings).equals(streamSubscriptionTimeoutSettings2)) return false;
        if (actorMaterializerSettings.debugLogging() != this.debugLogging()) return false;
        if (actorMaterializerSettings.outputBurstLimit() != this.outputBurstLimit()) return false;
        if (actorMaterializerSettings.syncProcessingLimit() != this.syncProcessingLimit()) return false;
        if (actorMaterializerSettings.fuzzingMode() != this.fuzzingMode()) return false;
        if (actorMaterializerSettings.autoFusing() != this.autoFusing()) return false;
        IOSettings iOSettings = actorMaterializerSettings.ioSettings();
        IOSettings iOSettings2 = this.ioSettings();
        if (iOSettings == null) {
            if (iOSettings2 != null) {
                return false;
            }
        } else if (!((Object)iOSettings).equals(iOSettings2)) return false;
        String string3 = actorMaterializerSettings.blockingIoDispatcher();
        String string4 = this.blockingIoDispatcher();
        if (string3 == null) {
            if (string4 == null) return true;
            return false;
        } else {
            if (!string3.equals(string4)) return false;
            return true;
        }
    }

    @InternalApi
    public Attributes toAttributes() {
        return this.asAttributes();
    }

    public String toString() {
        return new StringBuilder(37).append("ActorMaterializerSettings(").append(this.initialInputBufferSize()).append(",").append(this.maxInputBufferSize()).append(",").append(this.dispatcher()).append(",").append(this.supervisionDecider()).append(",").append(this.subscriptionTimeoutSettings()).append(",").append(this.debugLogging()).append(",").append(this.outputBurstLimit()).append(",").append(this.syncProcessingLimit()).append(",").append(this.fuzzingMode()).append(",").append(this.autoFusing()).append(",").append(this.ioSettings()).append(")").toString();
    }

    @InternalApi
    public ActorMaterializerSettings(int initialInputBufferSize, int maxInputBufferSize, String dispatcher, Function1<Throwable, Supervision.Directive> supervisionDecider, StreamSubscriptionTimeoutSettings subscriptionTimeoutSettings, boolean debugLogging, int outputBurstLimit, boolean fuzzingMode, boolean autoFusing, int maxFixedBufferSize, int syncProcessingLimit, IOSettings ioSettings, StreamRefSettings streamRefSettings, String blockingIoDispatcher) {
        this.initialInputBufferSize = initialInputBufferSize;
        this.maxInputBufferSize = maxInputBufferSize;
        this.dispatcher = dispatcher;
        this.supervisionDecider = supervisionDecider;
        this.subscriptionTimeoutSettings = subscriptionTimeoutSettings;
        this.debugLogging = debugLogging;
        this.outputBurstLimit = outputBurstLimit;
        this.fuzzingMode = fuzzingMode;
        this.autoFusing = autoFusing;
        this.maxFixedBufferSize = maxFixedBufferSize;
        this.syncProcessingLimit = syncProcessingLimit;
        this.ioSettings = ioSettings;
        this.streamRefSettings = streamRefSettings;
        this.blockingIoDispatcher = blockingIoDispatcher;
        Predef$.MODULE$.require(initialInputBufferSize > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "initialInputBufferSize must be > 0");
        Predef$.MODULE$.require(syncProcessingLimit > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "syncProcessingLimit must be > 0");
        this.requirePowerOfTwo(Predef$.MODULE$.int2Integer(maxInputBufferSize), "maxInputBufferSize");
        Predef$.MODULE$.require(initialInputBufferSize <= maxInputBufferSize, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(56).append("initialInputBufferSize(").append(this.initialInputBufferSize()).append(") must be <= maxInputBufferSize(").append(this.maxInputBufferSize()).append(")").toString());
        Attributes.InputBuffer inputBuffer = new Attributes.InputBuffer(initialInputBufferSize, maxInputBufferSize);
        Attributes.CancellationStrategy cancellationStrategy = Attributes$CancellationStrategy$.MODULE$.Default();
        Attributes.NestedMaterializationCancellationPolicy nestedMaterializationCancellationPolicy = Attributes$NestedMaterializationCancellationPolicy$.MODULE$.Default();
        ActorAttributes.Dispatcher dispatcher2 = new ActorAttributes.Dispatcher(dispatcher);
        ActorAttributes.SupervisionStrategy supervisionStrategy = new ActorAttributes.SupervisionStrategy(supervisionDecider);
        ActorAttributes.DebugLogging debugLogging2 = new ActorAttributes.DebugLogging(debugLogging);
        ActorAttributes.StreamSubscriptionTimeout streamSubscriptionTimeout = new ActorAttributes.StreamSubscriptionTimeout(subscriptionTimeoutSettings.timeout(), subscriptionTimeoutSettings.mode());
        ActorAttributes.OutputBurstLimit outputBurstLimit2 = new ActorAttributes.OutputBurstLimit(outputBurstLimit);
        ActorAttributes.FuzzingMode fuzzingMode2 = new ActorAttributes.FuzzingMode(fuzzingMode);
        ActorAttributes.MaxFixedBufferSize maxFixedBufferSize2 = new ActorAttributes.MaxFixedBufferSize(maxFixedBufferSize);
        ActorAttributes.SyncProcessingLimit syncProcessingLimit2 = new ActorAttributes.SyncProcessingLimit(syncProcessingLimit);
        this.asAttributes = Attributes$.MODULE$.apply(Nil$.MODULE$.$colon$colon(syncProcessingLimit2).$colon$colon(maxFixedBufferSize2).$colon$colon(fuzzingMode2).$colon$colon(outputBurstLimit2).$colon$colon(streamSubscriptionTimeout).$colon$colon(debugLogging2).$colon$colon(supervisionStrategy).$colon$colon(dispatcher2).$colon$colon(nestedMaterializationCancellationPolicy).$colon$colon(cancellationStrategy).$colon$colon(inputBuffer));
    }
}

