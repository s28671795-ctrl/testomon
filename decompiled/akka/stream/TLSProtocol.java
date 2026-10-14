/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.TLSClientAuth;
import akka.stream.TLSProtocol$;
import akka.stream.scaladsl.ScalaSessionAPI;
import akka.util.ByteString;
import java.io.Serializable;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r%s!B3g\u0011\u0003Yg!B7g\u0011\u0003q\u0007\"B;\u0002\t\u00031haB<\u0002!\u0003\r\n\u0003\u001f\u0004\b\u0003;\f\u0011\u0011EAp\u0011\u0019)H\u0001\"\u0001\u0002b\u001e9\u0011q`\u0001\t\u0002\u0006-haBAo\u0003!\u0005\u0015q\u001d\u0005\u0007k\u001e!\t!!;\t\u0013\u0005\u0005u!!A\u0005B\u0005\r\u0005\"CAK\u000f\u0005\u0005I\u0011AAL\u0011%\tyjBA\u0001\n\u0003\ti\u000fC\u0005\u0002.\u001e\t\t\u0011\"\u0011\u00020\"I\u0011QX\u0004\u0002\u0002\u0013\u0005\u0011\u0011\u001f\u0005\n\u0003\u001f<\u0011\u0011!C!\u0003#D\u0011\"a5\b\u0003\u0003%\t%!6\t\u0013\u0005Ux!!A\u0005\n\u0005]h\u0001\u0002>\u0002\u0005nD!\"a\n\u0012\u0005+\u0007I\u0011AA\u0015\u0011)\ty$\u0005B\tB\u0003%\u00111\u0006\u0005\u000b\u0003\u0003\n\"Q3A\u0005\u0002\u0005\r\u0003BCA)#\tE\t\u0015!\u0003\u0002F!1Q/\u0005C\u0001\u0003'B\u0011\"a\u0017\u0012\u0003\u0003%\t!!\u0018\t\u0013\u0005\r\u0014#%A\u0005\u0002\u0005\u0015\u0004\"CA>#E\u0005I\u0011AA?\u0011%\t\t)EA\u0001\n\u0003\n\u0019\tC\u0005\u0002\u0016F\t\t\u0011\"\u0001\u0002\u0018\"I\u0011qT\t\u0002\u0002\u0013\u0005\u0011\u0011\u0015\u0005\n\u0003[\u000b\u0012\u0011!C!\u0003_C\u0011\"!0\u0012\u0003\u0003%\t!a0\t\u0013\u0005%\u0017#!A\u0005B\u0005-\u0007\"CAh#\u0005\u0005I\u0011IAi\u0011%\t\u0019.EA\u0001\n\u0003\n)\u000eC\u0005\u0002XF\t\t\u0011\"\u0011\u0002Z\u001eI!\u0011A\u0001\u0002\u0002#\u0005!1\u0001\u0004\tu\u0006\t\t\u0011#\u0001\u0003\u0006!1Q\u000f\nC\u0001\u0005;A\u0011\"a5%\u0003\u0003%)%!6\t\u0013\t}A%!A\u0005\u0002\n\u0005\u0002\"\u0003B\u0014I\u0005\u0005I\u0011\u0011B\u0015\u0011%\t)\u0010JA\u0001\n\u0013\t9PB\u0005\u0003<\u0005\u0001\n1%\t\u0003>\u00191!\u0011I\u0001A\u0005\u0007B!Ba\u0012,\u0005+\u0007I\u0011\u0001B%\u0011)\u00119g\u000bB\tB\u0003%!1\n\u0005\u000b\u0005SZ#Q3A\u0005\u0002\t%\u0003B\u0003B6W\tE\t\u0015!\u0003\u0003L!Q!QN\u0016\u0003\u0016\u0004%\tAa\u001c\t\u0015\te4F!E!\u0002\u0013\u0011\t\b\u0003\u0006\u0003|-\u0012)\u001a!C\u0001\u0005{B!Ba\",\u0005#\u0005\u000b\u0011\u0002B@\u0011\u0019)8\u0006\"\u0001\u0003\n\"9!QS\u0016\u0005\u0002\t]\u0005b\u0002BWW\u0011\u0005!q\u0016\u0005\b\u0005o[C\u0011\u0001B]\u0011\u001d\u0011yl\u000bC\u0001\u0005\u0003D\u0011\"a\u0017,\u0003\u0003%\tA!2\t\u0013\u0005\r4&%A\u0005\u0002\t=\u0007\"CA>WE\u0005I\u0011\u0001Bh\u0011%\u0011\u0019nKI\u0001\n\u0003\u0011)\u000eC\u0005\u0003Z.\n\n\u0011\"\u0001\u0003\\\"I\u0011\u0011Q\u0016\u0002\u0002\u0013\u0005\u00131\u0011\u0005\n\u0003+[\u0013\u0011!C\u0001\u0003/C\u0011\"a(,\u0003\u0003%\tAa8\t\u0013\u000556&!A\u0005B\u0005=\u0006\"CA_W\u0005\u0005I\u0011\u0001Br\u0011%\tImKA\u0001\n\u0003\u00129\u000fC\u0005\u0002P.\n\t\u0011\"\u0011\u0002R\"I\u00111[\u0016\u0002\u0002\u0013\u0005\u0013Q\u001b\u0005\n\u0003/\\\u0013\u0011!C!\u0005W<qa!\u0004\u0002\u0011\u0003\u0019yAB\u0004\u0003B\u0005A\ta!\u0005\t\rUDE\u0011AB\n\u0011\u001d\u0019)\u0002\u0013C\u0001\u0007/A\u0011Ba\bI\u0003\u0003%\ti!\u0007\t\u0013\t\u001d\u0002*!A\u0005\u0002\u000e\r\u0002\"CA{\u0011\u0006\u0005I\u0011BA|\u0011\u001d\u0019y#\u0001C\u0001\u0007/1aAa<\u0002\u0005\nE\bBCA!\u001f\nU\r\u0011\"\u0001\u0002D!Q\u0011\u0011K(\u0003\u0012\u0003\u0006I!!\u0012\t\rU|E\u0011\u0001Bz\u0011%\tYfTA\u0001\n\u0003\u0011I\u0010C\u0005\u0002d=\u000b\n\u0011\"\u0001\u0002~!I\u0011\u0011Q(\u0002\u0002\u0013\u0005\u00131\u0011\u0005\n\u0003+{\u0015\u0011!C\u0001\u0003/C\u0011\"a(P\u0003\u0003%\tA!@\t\u0013\u00055v*!A\u0005B\u0005=\u0006\"CA_\u001f\u0006\u0005I\u0011AB\u0001\u0011%\tImTA\u0001\n\u0003\u001a)\u0001C\u0005\u0002P>\u000b\t\u0011\"\u0011\u0002R\"I\u00111[(\u0002\u0002\u0013\u0005\u0013Q\u001b\u0005\n\u0003/|\u0015\u0011!C!\u0007\u00139\u0011b!\r\u0002\u0003\u0003E\taa\r\u0007\u0013\t=\u0018!!A\t\u0002\rU\u0002BB;`\t\u0003\u0019i\u0004C\u0005\u0002T~\u000b\t\u0011\"\u0012\u0002V\"I!qD0\u0002\u0002\u0013\u00055q\b\u0005\n\u0005Oy\u0016\u0011!CA\u0007\u0007B\u0011\"!>`\u0003\u0003%I!a>\u0002\u0017Qc5\u000b\u0015:pi>\u001cw\u000e\u001c\u0006\u0003O\"\faa\u001d;sK\u0006l'\"A5\u0002\t\u0005\\7.Y\u0002\u0001!\ta\u0017!D\u0001g\u0005-!Fj\u0015)s_R|7m\u001c7\u0014\u0005\u0005y\u0007C\u00019t\u001b\u0005\t(\"\u0001:\u0002\u000bM\u001c\u0017\r\\1\n\u0005Q\f(AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002W\ni1k\u001d7UYNLeNY8v]\u0012\u001c\"aA8*\u0007\r\tBA\u0001\u0007TKN\u001c\u0018n\u001c8CsR,7o\u0005\u0005\u0012_rt\u0018\u0011BA\b!\ti8!D\u0001\u0002!\ry\u0018QA\u0007\u0003\u0003\u0003Q1!a\u0001g\u0003!\u00198-\u00197bINd\u0017\u0002BA\u0004\u0003\u0003\u0011qbU2bY\u0006\u001cVm]:j_:\f\u0005+\u0013\t\u0004a\u0006-\u0011bAA\u0007c\n9\u0001K]8ek\u000e$\b\u0003BA\t\u0003CqA!a\u0005\u0002\u001e9!\u0011QCA\u000e\u001b\t\t9BC\u0002\u0002\u001a)\fa\u0001\u0010:p_Rt\u0014\"\u0001:\n\u0007\u0005}\u0011/A\u0004qC\u000e\\\u0017mZ3\n\t\u0005\r\u0012Q\u0005\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0004\u0003?\t\u0018aB:fgNLwN\\\u000b\u0003\u0003W\u0001B!!\f\u0002<5\u0011\u0011q\u0006\u0006\u0005\u0003c\t\u0019$A\u0002tg2TA!!\u000e\u00028\u0005\u0019a.\u001a;\u000b\u0005\u0005e\u0012!\u00026bm\u0006D\u0018\u0002BA\u001f\u0003_\u0011!bU*M'\u0016\u001c8/[8o\u0003!\u0019Xm]:j_:\u0004\u0013!\u00022zi\u0016\u001cXCAA#!\u0011\t9%!\u0014\u000e\u0005\u0005%#bAA&Q\u0006!Q\u000f^5m\u0013\u0011\ty%!\u0013\u0003\u0015\tKH/Z*ue&tw-\u0001\u0004csR,7\u000f\t\u000b\u0007\u0003+\n9&!\u0017\u0011\u0005u\f\u0002bBA\u0014-\u0001\u0007\u00111\u0006\u0005\b\u0003\u00032\u0002\u0019AA#\u0003\u0011\u0019w\u000e]=\u0015\r\u0005U\u0013qLA1\u0011%\t9c\u0006I\u0001\u0002\u0004\tY\u0003C\u0005\u0002B]\u0001\n\u00111\u0001\u0002F\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA4U\u0011\tY#!\u001b,\u0005\u0005-\u0004\u0003BA7\u0003oj!!a\u001c\u000b\t\u0005E\u00141O\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u001er\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003s\nyGA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002\u0000)\"\u0011QIA5\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011Q\u0011\t\u0005\u0003\u000f\u000b\t*\u0004\u0002\u0002\n*!\u00111RAG\u0003\u0011a\u0017M\\4\u000b\u0005\u0005=\u0015\u0001\u00026bm\u0006LA!a%\u0002\n\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!!'\u0011\u0007A\fY*C\u0002\u0002\u001eF\u00141!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a)\u0002*B\u0019\u0001/!*\n\u0007\u0005\u001d\u0016OA\u0002B]fD\u0011\"a+\u001d\u0003\u0003\u0005\r!!'\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t\t\f\u0005\u0004\u00024\u0006e\u00161U\u0007\u0003\u0003kS1!a.r\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003w\u000b)L\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAa\u0003\u000f\u00042\u0001]Ab\u0013\r\t)-\u001d\u0002\b\u0005>|G.Z1o\u0011%\tYKHA\u0001\u0002\u0004\t\u0019+\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BAC\u0003\u001bD\u0011\"a+ \u0003\u0003\u0005\r!!'\u0002\u0011!\f7\u000f[\"pI\u0016$\"!!'\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\"\u0002\r\u0015\fX/\u00197t)\u0011\t\t-a7\t\u0013\u0005-&%!AA\u0002\u0005\r&\u0001E*fgNLwN\u001c+sk:\u001c\u0017\r^3e'\r!q\u000e \u000b\u0003\u0003G\u0004\"! \u0003*\u0005\u001191cB\u0004\u0002d\u0006%\u0011q\u0002\u000b\u0003\u0003W\u0004\"!`\u0004\u0015\t\u0005\r\u0016q\u001e\u0005\n\u0003W[\u0011\u0011!a\u0001\u00033#B!!1\u0002t\"I\u00111V\u0007\u0002\u0002\u0003\u0007\u00111U\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003s\u0004B!a\"\u0002|&!\u0011Q`AE\u0005\u0019y%M[3di\u0006\u00012+Z:tS>tGK];oG\u0006$X\rZ\u0001\r'\u0016\u001c8/[8o\u0005f$Xm\u001d\t\u0003{\u0012\u001aR\u0001\nB\u0004\u0005'\u0001\"B!\u0003\u0003\u0010\u0005-\u0012QIA+\u001b\t\u0011YAC\u0002\u0003\u000eE\fqA];oi&lW-\u0003\u0003\u0003\u0012\t-!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!!Q\u0003B\u000e\u001b\t\u00119B\u0003\u0003\u0003\u001a\u00055\u0015AA5p\u0013\u0011\t\u0019Ca\u0006\u0015\u0005\t\r\u0011!B1qa2LHCBA+\u0005G\u0011)\u0003C\u0004\u0002(\u001d\u0002\r!a\u000b\t\u000f\u0005\u0005s\u00051\u0001\u0002F\u00059QO\\1qa2LH\u0003\u0002B\u0016\u0005o\u0001R\u0001\u001dB\u0017\u0005cI1Aa\fr\u0005\u0019y\u0005\u000f^5p]B9\u0001Oa\r\u0002,\u0005\u0015\u0013b\u0001B\u001bc\n1A+\u001e9mKJB\u0011B!\u000f)\u0003\u0003\u0005\r!!\u0016\u0002\u0007a$\u0003G\u0001\bTg2$Fn](vi\n|WO\u001c3\u0014\u0005)z\u0017f\u0001\u0016,\u001f\n\u0019b*Z4pi&\fG/\u001a(foN+7o]5p]NA1f\u001cB#\u0003\u0013\ty\u0001\u0005\u0002~U\u0005\u0019RM\\1cY\u0016$7)\u001b9iKJ\u001cV/\u001b;fgV\u0011!1\n\t\u0006a\n5\"Q\n\t\u0007\u0005\u001f\u0012)F!\u0017\u000e\u0005\tE#\u0002\u0002B*\u0003k\u000b\u0011\"[7nkR\f'\r\\3\n\t\t]#\u0011\u000b\u0002\u0004'\u0016\f\b\u0003\u0002B.\u0005GrAA!\u0018\u0003`A\u0019\u0011QC9\n\u0007\t\u0005\u0014/\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003'\u0013)GC\u0002\u0003bE\fA#\u001a8bE2,GmQ5qQ\u0016\u00148+^5uKN\u0004\u0013\u0001E3oC\ndW\r\u001a)s_R|7m\u001c7t\u0003E)g.\u00192mK\u0012\u0004&o\u001c;pG>d7\u000fI\u0001\u000bG2LWM\u001c;BkRDWC\u0001B9!\u0015\u0001(Q\u0006B:!\ra'QO\u0005\u0004\u0005o2'!\u0004+M'\u000ec\u0017.\u001a8u\u0003V$\b.A\u0006dY&,g\u000e^!vi\"\u0004\u0013!D:tYB\u000b'/Y7fi\u0016\u00148/\u0006\u0002\u0003\u0000A)\u0001O!\f\u0003\u0002B!\u0011Q\u0006BB\u0013\u0011\u0011))a\f\u0003\u001bM\u001bF\nU1sC6,G/\u001a:t\u00039\u00198\u000f\u001c)be\u0006lW\r^3sg\u0002\"\"Ba#\u0003\u000e\n=%\u0011\u0013BJ!\ti8\u0006C\u0004\u0003HQ\u0002\rAa\u0013\t\u000f\t%D\u00071\u0001\u0003L!9!Q\u000e\u001bA\u0002\tE\u0004b\u0002B>i\u0001\u0007!qP\u0001\u0011o&$\bnQ5qQ\u0016\u00148+^5uKN$BAa#\u0003\u001a\"9!1T\u001bA\u0002\tu\u0015!A:\u0011\u000bA\u0014yJ!\u0017\n\u0007\t\u0005\u0016O\u0001\u0006=e\u0016\u0004X-\u0019;fIzB3!\u000eBS!\u0011\u00119K!+\u000e\u0005\u0005M\u0014\u0002\u0002BV\u0003g\u0012qA^1sCJ<7/A\u0007xSRD\u0007K]8u_\u000e|Gn\u001d\u000b\u0005\u0005\u0017\u0013\t\fC\u0004\u00034Z\u0002\rA!(\u0002\u0003AD3A\u000eBS\u000399\u0018\u000e\u001e5DY&,g\u000e^!vi\"$BAa#\u0003<\"9!QX\u001cA\u0002\tM\u0014AA2b\u000399\u0018\u000e\u001e5QCJ\fW.\u001a;feN$BAa#\u0003D\"9!1\u0017\u001dA\u0002\t\u0005EC\u0003BF\u0005\u000f\u0014IMa3\u0003N\"I!qI\u001d\u0011\u0002\u0003\u0007!1\n\u0005\n\u0005SJ\u0004\u0013!a\u0001\u0005\u0017B\u0011B!\u001c:!\u0003\u0005\rA!\u001d\t\u0013\tm\u0014\b%AA\u0002\t}TC\u0001BiU\u0011\u0011Y%!\u001b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011!q\u001b\u0016\u0005\u0005c\nI'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\tu'\u0006\u0002B@\u0003S\"B!a)\u0003b\"I\u00111\u0016!\u0002\u0002\u0003\u0007\u0011\u0011\u0014\u000b\u0005\u0003\u0003\u0014)\u000fC\u0005\u0002,\n\u000b\t\u00111\u0001\u0002$R!\u0011Q\u0011Bu\u0011%\tYkQA\u0001\u0002\u0004\tI\n\u0006\u0003\u0002B\n5\b\"CAV\r\u0006\u0005\t\u0019AAR\u0005%\u0019VM\u001c3CsR,7o\u0005\u0005P_\n\u0015\u0013\u0011BA\b)\u0011\u0011)Pa>\u0011\u0005u|\u0005bBA!%\u0002\u0007\u0011Q\t\u000b\u0005\u0005k\u0014Y\u0010C\u0005\u0002BM\u0003\n\u00111\u0001\u0002FQ!\u00111\u0015B\u0000\u0011%\tYkVA\u0001\u0002\u0004\tI\n\u0006\u0003\u0002B\u000e\r\u0001\"CAV3\u0006\u0005\t\u0019AAR)\u0011\t)ia\u0002\t\u0013\u0005-&,!AA\u0002\u0005eE\u0003BAa\u0007\u0017A\u0011\"a+^\u0003\u0003\u0005\r!a)\u0002'9+wm\u001c;jCR,g*Z<TKN\u001c\u0018n\u001c8\u0011\u0005uD5c\u0001%\u0003\fR\u00111qB\u0001\ro&$\b\u000eR3gCVdGo]\u000b\u0003\u0005\u0017#\"Ba#\u0004\u001c\ru1qDB\u0011\u0011\u001d\u00119e\u0013a\u0001\u0005\u0017BqA!\u001bL\u0001\u0004\u0011Y\u0005C\u0004\u0003n-\u0003\rA!\u001d\t\u000f\tm4\n1\u0001\u0003\u0000Q!1QEB\u0017!\u0015\u0001(QFB\u0014!-\u00018\u0011\u0006B&\u0005\u0017\u0012\tHa \n\u0007\r-\u0012O\u0001\u0004UkBdW\r\u000e\u0005\n\u0005sa\u0015\u0011!a\u0001\u0005\u0017\u000b1C\\3h_RL\u0017\r^3OK^\u001cVm]:j_:\f\u0011bU3oI\nKH/Z:\u0011\u0005u|6#B0\u00048\tM\u0001\u0003\u0003B\u0005\u0007s\t)E!>\n\t\rm\"1\u0002\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\fDCAB\u001a)\u0011\u0011)p!\u0011\t\u000f\u0005\u0005#\r1\u0001\u0002FQ!1QIB$!\u0015\u0001(QFA#\u0011%\u0011IdYA\u0001\u0002\u0004\u0011)\u0010")
public final class TLSProtocol {
    public static NegotiateNewSession negotiateNewSession() {
        return TLSProtocol$.MODULE$.negotiateNewSession();
    }

    public static class NegotiateNewSession
    implements SslTlsOutbound,
    Product,
    Serializable {
        private final Option<Seq<String>> enabledCipherSuites;
        private final Option<Seq<String>> enabledProtocols;
        private final Option<TLSClientAuth> clientAuth;
        private final Option<SSLParameters> sslParameters;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public NegotiateNewSession withCipherSuites(String ... s2) {
            return this.withCipherSuites(ScalaRunTime$.MODULE$.wrapRefArray((Object[])s2));
        }

        public NegotiateNewSession withProtocols(String ... p) {
            return this.withProtocols(ScalaRunTime$.MODULE$.wrapRefArray((Object[])p));
        }

        public Option<Seq<String>> enabledCipherSuites() {
            return this.enabledCipherSuites;
        }

        public Option<Seq<String>> enabledProtocols() {
            return this.enabledProtocols;
        }

        public Option<TLSClientAuth> clientAuth() {
            return this.clientAuth;
        }

        public Option<SSLParameters> sslParameters() {
            return this.sslParameters;
        }

        public NegotiateNewSession withCipherSuites(Seq<String> s2) {
            return this.copy(new Some<Seq<String>>(s2.toList()), this.copy$default$2(), this.copy$default$3(), this.copy$default$4());
        }

        public NegotiateNewSession withProtocols(Seq<String> p) {
            Some<Seq<String>> x$1 = new Some<Seq<String>>(p.toList());
            Option<Seq<String>> x$2 = this.copy$default$1();
            Option<TLSClientAuth> x$3 = this.copy$default$3();
            Option<SSLParameters> x$4 = this.copy$default$4();
            return this.copy(x$2, x$1, x$3, x$4);
        }

        public NegotiateNewSession withClientAuth(TLSClientAuth ca) {
            Some<TLSClientAuth> x$1 = new Some<TLSClientAuth>(ca);
            Option<Seq<String>> x$2 = this.copy$default$1();
            Option<Seq<String>> x$3 = this.copy$default$2();
            Option<SSLParameters> x$4 = this.copy$default$4();
            return this.copy(x$2, x$3, x$1, x$4);
        }

        public NegotiateNewSession withParameters(SSLParameters p) {
            Some<SSLParameters> x$1 = new Some<SSLParameters>(p);
            Option<Seq<String>> x$2 = this.copy$default$1();
            Option<Seq<String>> x$3 = this.copy$default$2();
            Option<TLSClientAuth> x$4 = this.copy$default$3();
            return this.copy(x$2, x$3, x$4, x$1);
        }

        public NegotiateNewSession copy(Option<Seq<String>> enabledCipherSuites, Option<Seq<String>> enabledProtocols, Option<TLSClientAuth> clientAuth, Option<SSLParameters> sslParameters) {
            return new NegotiateNewSession(enabledCipherSuites, enabledProtocols, clientAuth, sslParameters);
        }

        public Option<Seq<String>> copy$default$1() {
            return this.enabledCipherSuites();
        }

        public Option<Seq<String>> copy$default$2() {
            return this.enabledProtocols();
        }

        public Option<TLSClientAuth> copy$default$3() {
            return this.clientAuth();
        }

        public Option<SSLParameters> copy$default$4() {
            return this.sslParameters();
        }

        @Override
        public String productPrefix() {
            return "NegotiateNewSession";
        }

        @Override
        public int productArity() {
            return 4;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.enabledCipherSuites();
                }
                case 1: {
                    return this.enabledProtocols();
                }
                case 2: {
                    return this.clientAuth();
                }
                case 3: {
                    return this.sslParameters();
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
            return x$1 instanceof NegotiateNewSession;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "enabledCipherSuites";
                }
                case 1: {
                    return "enabledProtocols";
                }
                case 2: {
                    return "clientAuth";
                }
                case 3: {
                    return "sslParameters";
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
            if (!(object instanceof NegotiateNewSession)) return false;
            boolean bl = true;
            if (!bl) return false;
            NegotiateNewSession negotiateNewSession = (NegotiateNewSession)x$1;
            Option<Seq<String>> option = this.enabledCipherSuites();
            Option<Seq<String>> option2 = negotiateNewSession.enabledCipherSuites();
            if (option == null) {
                if (option2 != null) {
                    return false;
                }
            } else if (!option.equals(option2)) return false;
            Option<Seq<String>> option3 = this.enabledProtocols();
            Option<Seq<String>> option4 = negotiateNewSession.enabledProtocols();
            if (option3 == null) {
                if (option4 != null) {
                    return false;
                }
            } else if (!option3.equals(option4)) return false;
            Option<TLSClientAuth> option5 = this.clientAuth();
            Option<TLSClientAuth> option6 = negotiateNewSession.clientAuth();
            if (option5 == null) {
                if (option6 != null) {
                    return false;
                }
            } else if (!option5.equals(option6)) return false;
            Option<SSLParameters> option7 = this.sslParameters();
            Option<SSLParameters> option8 = negotiateNewSession.sslParameters();
            if (option7 == null) {
                if (option8 != null) {
                    return false;
                }
            } else if (!option7.equals(option8)) return false;
            if (!negotiateNewSession.canEqual(this)) return false;
            return true;
        }

        public NegotiateNewSession(Option<Seq<String>> enabledCipherSuites, Option<Seq<String>> enabledProtocols, Option<TLSClientAuth> clientAuth, Option<SSLParameters> sslParameters) {
            this.enabledCipherSuites = enabledCipherSuites;
            this.enabledProtocols = enabledProtocols;
            this.clientAuth = clientAuth;
            this.sslParameters = sslParameters;
            Product.$init$(this);
        }
    }

    public static final class SendBytes
    implements SslTlsOutbound,
    Product,
    Serializable {
        private final ByteString bytes;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ByteString bytes() {
            return this.bytes;
        }

        public SendBytes copy(ByteString bytes) {
            return new SendBytes(bytes);
        }

        public ByteString copy$default$1() {
            return this.bytes();
        }

        @Override
        public String productPrefix() {
            return "SendBytes";
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
                    return this.bytes();
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
            return x$1 instanceof SendBytes;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "bytes";
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
            if (!(object instanceof SendBytes)) return false;
            boolean bl = true;
            if (!bl) return false;
            SendBytes sendBytes = (SendBytes)x$1;
            ByteString byteString = this.bytes();
            ByteString byteString2 = sendBytes.bytes();
            if (byteString != null) {
                if (!((Object)byteString).equals(byteString2)) return false;
                return true;
            }
            if (byteString2 == null) return true;
            return false;
        }

        public SendBytes(ByteString bytes) {
            this.bytes = bytes;
            Product.$init$(this);
        }
    }

    public static final class SessionBytes
    implements SslTlsInbound,
    ScalaSessionAPI,
    Product,
    Serializable {
        private final SSLSession session;
        private final ByteString bytes;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public List<Certificate> localCertificates() {
            return ScalaSessionAPI.localCertificates$(this);
        }

        @Override
        public Option<Principal> localPrincipal() {
            return ScalaSessionAPI.localPrincipal$(this);
        }

        @Override
        public List<Certificate> peerCertificates() {
            return ScalaSessionAPI.peerCertificates$(this);
        }

        @Override
        public Option<Principal> peerPrincipal() {
            return ScalaSessionAPI.peerPrincipal$(this);
        }

        @Override
        public SSLSession session() {
            return this.session;
        }

        public ByteString bytes() {
            return this.bytes;
        }

        public SessionBytes copy(SSLSession session, ByteString bytes) {
            return new SessionBytes(session, bytes);
        }

        public SSLSession copy$default$1() {
            return this.session();
        }

        public ByteString copy$default$2() {
            return this.bytes();
        }

        @Override
        public String productPrefix() {
            return "SessionBytes";
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
                    return this.session();
                }
                case 1: {
                    return this.bytes();
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
            return x$1 instanceof SessionBytes;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "session";
                }
                case 1: {
                    return "bytes";
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
            if (!(object instanceof SessionBytes)) return false;
            boolean bl = true;
            if (!bl) return false;
            SessionBytes sessionBytes = (SessionBytes)x$1;
            SSLSession sSLSession = this.session();
            SSLSession sSLSession2 = sessionBytes.session();
            if (sSLSession == null) {
                if (sSLSession2 != null) {
                    return false;
                }
            } else if (!sSLSession.equals(sSLSession2)) return false;
            ByteString byteString = this.bytes();
            ByteString byteString2 = sessionBytes.bytes();
            if (byteString == null) {
                if (byteString2 == null) return true;
                return false;
            } else {
                if (!((Object)byteString).equals(byteString2)) return false;
                return true;
            }
        }

        public SessionBytes(SSLSession session, ByteString bytes) {
            this.session = session;
            this.bytes = bytes;
            ScalaSessionAPI.$init$(this);
            Product.$init$(this);
        }
    }

    public static abstract class SessionTruncated
    implements SslTlsInbound {
    }

    public static interface SslTlsInbound {
    }

    public static interface SslTlsOutbound {
    }
}

