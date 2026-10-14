/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple10$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011\rq!B\u001a5\u0011\u0003Yd!B\u001f5\u0011\u0003q\u0004\"B'\u0002\t\u0003q\u0005\"B(\u0002\t\u0003\u0001\u0006\"CB=\u0003\u0005\u0005I\u0011QB>\u0011%\u0019i,AA\u0001\n\u0003\u001by\fC\u0005\u0004z\u0006\t\t\u0011\"\u0003\u0004|\u001a!Q\b\u000e\"T\u0011!\u0019wA!f\u0001\n\u0003!\u0007\u0002\u00039\b\u0005#\u0005\u000b\u0011B3\t\u0011E<!Q3A\u0005\u0002ID\u0001B^\u0004\u0003\u0012\u0003\u0006Ia\u001d\u0005\to\u001e\u0011)\u001a!C\u0001q\"AAp\u0002B\tB\u0003%\u0011\u0010\u0003\u0005~\u000f\tU\r\u0011\"\u0001\u007f\u0011%\t)a\u0002B\tB\u0003%q\u0010\u0003\u0006\u0002\b\u001d\u0011)\u001a!C\u0001\u0003\u0013A!\"!\u0005\b\u0005#\u0005\u000b\u0011BA\u0006\u0011)\t\u0019b\u0002BK\u0002\u0013\u0005\u0011Q\u0003\u0005\u000b\u0003;9!\u0011#Q\u0001\n\u0005]\u0001BCA\u0010\u000f\tU\r\u0011\"\u0001\u0002\"!Q\u0011\u0011F\u0004\u0003\u0012\u0003\u0006I!a\t\t\u0015\u0005-rA!f\u0001\n\u0003\ti\u0003\u0003\u0006\u00026\u001d\u0011\t\u0012)A\u0005\u0003_A!\"a\u000e\b\u0005+\u0007I\u0011AA\u001d\u0011)\t\te\u0002B\tB\u0003%\u00111\b\u0005\u000b\u0003\u0007:!Q3A\u0005\u0002\u0005\u0015\u0003BCA'\u000f\tE\t\u0015!\u0003\u0002H!1Qj\u0002C\u0001\u0003\u001fB\u0011\"a\u001a\b\u0005\u0004%\t!!\u001b\t\u0011\u0005=t\u0001)A\u0005\u0003WB\u0011\"!\u001d\b\u0003\u0003%\t!a\u001d\t\u0013\u0005Uv!%A\u0005\u0002\u0005]\u0006\"CAr\u000fE\u0005I\u0011AAs\u0011%\typBI\u0001\n\u0003\u0011\t\u0001C\u0005\u0003\u001c\u001d\t\n\u0011\"\u0001\u0003\u001e!I!qG\u0004\u0012\u0002\u0013\u0005!\u0011\b\u0005\n\u0005':\u0011\u0013!C\u0001\u0005+B\u0011Ba\u001c\b#\u0003%\tA!\u001d\t\u0013\t-u!%A\u0005\u0002\t5\u0005\"\u0003BT\u000fE\u0005I\u0011\u0001BU\u0011%\u0011\u0019mBI\u0001\n\u0003\u0011)\rC\u0005\u0003`\u001e\t\t\u0011\"\u0011\u0003b\"I!q^\u0004\u0002\u0002\u0013\u0005!\u0011\u001f\u0005\n\u0005s<\u0011\u0011!C\u0001\u0005wD\u0011b!\u0001\b\u0003\u0003%\tea\u0001\t\u0013\rEq!!A\u0005\u0002\rM\u0001\"CB\u000f\u000f\u0005\u0005I\u0011IB\u0010\u0011%\u0019\u0019cBA\u0001\n\u0003\u001a)\u0003C\u0005\u0004(\u001d\t\t\u0011\"\u0011\u0004*!I11F\u0004\u0002\u0002\u0013\u00053QF\u0001\b)V\u0004H.Z\u00191\u0015\t)d'A\u0003ukBdWM\u0003\u00028q\u0005!!.\u00199j\u0015\u0005I\u0014\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002=\u00035\tAGA\u0004UkBdW-\r\u0019\u0014\u0007\u0005yT\t\u0005\u0002A\u00076\t\u0011IC\u0001C\u0003\u0015\u00198-\u00197b\u0013\t!\u0015I\u0001\u0004B]f\u0014VM\u001a\t\u0003\r.k\u0011a\u0012\u0006\u0003\u0011&\u000b!![8\u000b\u0003)\u000bAA[1wC&\u0011Aj\u0012\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003m\naa\u0019:fCR,W#F)\u0004@\r\r3qIB&\u0007\u001f\u001a\u0019fa\u0016\u0004\\\r}31\r\u000b\u0016%\u000e\u00154qMB5\u0007W\u001aiga\u001c\u0004r\rM4QOB<!Yata!\u0010\u0004B\r\u00153\u0011JB'\u0007#\u001a)f!\u0017\u0004^\r\u0005TC\u0005+hij\f\t!!\u0004\u0002\u001a\u0005\u0015\u0012\u0011GA\u001f\u0003\u0013\u001aBaB V1B\u0011\u0001IV\u0005\u0003/\u0006\u0013q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002ZC:\u0011!l\u0018\b\u00037zk\u0011\u0001\u0018\u0006\u0003;j\na\u0001\u0010:p_Rt\u0014\"\u0001\"\n\u0005\u0001\f\u0015a\u00029bG.\fw-Z\u0005\u0003\u0019\nT!\u0001Y!\u0002\u0005Q\fT#A3\u0011\u0005\u0019<G\u0002\u0001\u0003\u0006Q\u001e\u0011\r!\u001b\u0002\u0003)F\n\"A[7\u0011\u0005\u0001[\u0017B\u00017B\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u00118\n\u0005=\f%aA!os\u0006\u0019A/\r\u0011\u0002\u0005Q\u0014T#A:\u0011\u0005\u0019$H!B;\b\u0005\u0004I'A\u0001+3\u0003\r!(\u0007I\u0001\u0003iN*\u0012!\u001f\t\u0003Mj$Qa_\u0004C\u0002%\u0014!\u0001V\u001a\u0002\u0007Q\u001c\u0004%\u0001\u0002uiU\tq\u0010E\u0002g\u0003\u0003!a!a\u0001\b\u0005\u0004I'A\u0001+5\u0003\r!H\u0007I\u0001\u0003iV*\"!a\u0003\u0011\u0007\u0019\fi\u0001\u0002\u0004\u0002\u0010\u001d\u0011\r!\u001b\u0002\u0003)V\n1\u0001^\u001b!\u0003\t!h'\u0006\u0002\u0002\u0018A\u0019a-!\u0007\u0005\r\u0005mqA1\u0001j\u0005\t!f'A\u0002um\u0001\n!\u0001^\u001c\u0016\u0005\u0005\r\u0002c\u00014\u0002&\u00111\u0011qE\u0004C\u0002%\u0014!\u0001V\u001c\u0002\u0007Q<\u0004%\u0001\u0002uqU\u0011\u0011q\u0006\t\u0004M\u0006EBABA\u001a\u000f\t\u0007\u0011N\u0001\u0002Uq\u0005\u0019A\u000f\u000f\u0011\u0002\u0005QLTCAA\u001e!\r1\u0017Q\b\u0003\u0007\u0003\u007f9!\u0019A5\u0003\u0005QK\u0014a\u0001;:A\u0005\u0019A/\r\u0019\u0016\u0005\u0005\u001d\u0003c\u00014\u0002J\u00111\u00111J\u0004C\u0002%\u00141\u0001V\u00191\u0003\u0011!\u0018\u0007\r\u0011\u0015-\u0005E\u00131KA+\u0003/\nI&a\u0017\u0002^\u0005}\u0013\u0011MA2\u0003K\u0002\"\u0003P\u0004fgf|\u00181BA\f\u0003G\ty#a\u000f\u0002H!)1\r\ba\u0001K\")\u0011\u000f\ba\u0001g\")q\u000f\ba\u0001s\")Q\u0010\ba\u0001\u007f\"9\u0011q\u0001\u000fA\u0002\u0005-\u0001bBA\n9\u0001\u0007\u0011q\u0003\u0005\b\u0003?a\u0002\u0019AA\u0012\u0011\u001d\tY\u0003\ba\u0001\u0003_Aq!a\u000e\u001d\u0001\u0004\tY\u0004C\u0004\u0002Dq\u0001\r!a\u0012\u0002\u000fQ|7kY1mCV\u0011\u00111\u000e\t\u0014\u0001\u00065Tm]=\u0000\u0003\u0017\t9\"a\t\u00020\u0005m\u0012qI\u0005\u0003{\u0005\u000b\u0001\u0002^8TG\u0006d\u0017\rI\u0001\u0005G>\u0004\u00180\u0006\f\u0002v\u0005m\u0014qPAB\u0003\u000f\u000bY)a$\u0002\u0014\u0006]\u00151TAP)Y\t9(!)\u0002$\u0006\u0015\u0016qUAU\u0003W\u000bi+a,\u00022\u0006M\u0006C\u0006\u001f\b\u0003s\ni(!!\u0002\u0006\u0006%\u0015QRAI\u0003+\u000bI*!(\u0011\u0007\u0019\fY\bB\u0003i?\t\u0007\u0011\u000eE\u0002g\u0003\u007f\"Q!^\u0010C\u0002%\u00042AZAB\t\u0015YxD1\u0001j!\r1\u0017q\u0011\u0003\u0007\u0003\u0007y\"\u0019A5\u0011\u0007\u0019\fY\t\u0002\u0004\u0002\u0010}\u0011\r!\u001b\t\u0004M\u0006=EABA\u000e?\t\u0007\u0011\u000eE\u0002g\u0003'#a!a\n \u0005\u0004I\u0007c\u00014\u0002\u0018\u00121\u00111G\u0010C\u0002%\u00042AZAN\t\u0019\tyd\bb\u0001SB\u0019a-a(\u0005\r\u0005-sD1\u0001j\u0011!\u0019w\u0004%AA\u0002\u0005e\u0004\u0002C9 !\u0003\u0005\r!! \t\u0011]|\u0002\u0013!a\u0001\u0003\u0003C\u0001\"`\u0010\u0011\u0002\u0003\u0007\u0011Q\u0011\u0005\n\u0003\u000fy\u0002\u0013!a\u0001\u0003\u0013C\u0011\"a\u0005 !\u0003\u0005\r!!$\t\u0013\u0005}q\u0004%AA\u0002\u0005E\u0005\"CA\u0016?A\u0005\t\u0019AAK\u0011%\t9d\bI\u0001\u0002\u0004\tI\nC\u0005\u0002D}\u0001\n\u00111\u0001\u0002\u001e\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCFA]\u0003\u001f\f\t.a5\u0002V\u0006]\u0017\u0011\\An\u0003;\fy.!9\u0016\u0005\u0005m&fA3\u0002>.\u0012\u0011q\u0018\t\u0005\u0003\u0003\fY-\u0004\u0002\u0002D*!\u0011QYAd\u0003%)hn\u00195fG.,GMC\u0002\u0002J\u0006\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\ti-a1\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003iA\t\u0007\u0011\u000eB\u0003vA\t\u0007\u0011\u000eB\u0003|A\t\u0007\u0011\u000e\u0002\u0004\u0002\u0004\u0001\u0012\r!\u001b\u0003\u0007\u0003\u001f\u0001#\u0019A5\u0005\r\u0005m\u0001E1\u0001j\t\u0019\t9\u0003\tb\u0001S\u00121\u00111\u0007\u0011C\u0002%$a!a\u0010!\u0005\u0004IGABA&A\t\u0007\u0011.\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016-\u0005\u001d\u00181^Aw\u0003_\f\t0a=\u0002v\u0006]\u0018\u0011`A~\u0003{,\"!!;+\u0007M\fi\fB\u0003iC\t\u0007\u0011\u000eB\u0003vC\t\u0007\u0011\u000eB\u0003|C\t\u0007\u0011\u000e\u0002\u0004\u0002\u0004\u0005\u0012\r!\u001b\u0003\u0007\u0003\u001f\t#\u0019A5\u0005\r\u0005m\u0011E1\u0001j\t\u0019\t9#\tb\u0001S\u00121\u00111G\u0011C\u0002%$a!a\u0010\"\u0005\u0004IGABA&C\t\u0007\u0011.\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016-\t\r!q\u0001B\u0005\u0005\u0017\u0011iAa\u0004\u0003\u0012\tM!Q\u0003B\f\u00053)\"A!\u0002+\u0007e\fi\fB\u0003iE\t\u0007\u0011\u000eB\u0003vE\t\u0007\u0011\u000eB\u0003|E\t\u0007\u0011\u000e\u0002\u0004\u0002\u0004\t\u0012\r!\u001b\u0003\u0007\u0003\u001f\u0011#\u0019A5\u0005\r\u0005m!E1\u0001j\t\u0019\t9C\tb\u0001S\u00121\u00111\u0007\u0012C\u0002%$a!a\u0010#\u0005\u0004IGABA&E\t\u0007\u0011.\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016-\t}!1\u0005B\u0013\u0005O\u0011ICa\u000b\u0003.\t=\"\u0011\u0007B\u001a\u0005k)\"A!\t+\u0007}\fi\fB\u0003iG\t\u0007\u0011\u000eB\u0003vG\t\u0007\u0011\u000eB\u0003|G\t\u0007\u0011\u000e\u0002\u0004\u0002\u0004\r\u0012\r!\u001b\u0003\u0007\u0003\u001f\u0019#\u0019A5\u0005\r\u0005m1E1\u0001j\t\u0019\t9c\tb\u0001S\u00121\u00111G\u0012C\u0002%$a!a\u0010$\u0005\u0004IGABA&G\t\u0007\u0011.\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016-\tm\"q\bB!\u0005\u0007\u0012)Ea\u0012\u0003J\t-#Q\nB(\u0005#*\"A!\u0010+\t\u0005-\u0011Q\u0018\u0003\u0006Q\u0012\u0012\r!\u001b\u0003\u0006k\u0012\u0012\r!\u001b\u0003\u0006w\u0012\u0012\r!\u001b\u0003\u0007\u0003\u0007!#\u0019A5\u0005\r\u0005=AE1\u0001j\t\u0019\tY\u0002\nb\u0001S\u00121\u0011q\u0005\u0013C\u0002%$a!a\r%\u0005\u0004IGABA I\t\u0007\u0011\u000e\u0002\u0004\u0002L\u0011\u0012\r![\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+Y\u00119Fa\u0017\u0003^\t}#\u0011\rB2\u0005K\u00129G!\u001b\u0003l\t5TC\u0001B-U\u0011\t9\"!0\u0005\u000b!,#\u0019A5\u0005\u000bU,#\u0019A5\u0005\u000bm,#\u0019A5\u0005\r\u0005\rQE1\u0001j\t\u0019\ty!\nb\u0001S\u00121\u00111D\u0013C\u0002%$a!a\n&\u0005\u0004IGABA\u001aK\t\u0007\u0011\u000e\u0002\u0004\u0002@\u0015\u0012\r!\u001b\u0003\u0007\u0003\u0017*#\u0019A5\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%oU1\"1\u000fB<\u0005s\u0012YH! \u0003\u0000\t\u0005%1\u0011BC\u0005\u000f\u0013I)\u0006\u0002\u0003v)\"\u00111EA_\t\u0015AgE1\u0001j\t\u0015)hE1\u0001j\t\u0015YhE1\u0001j\t\u0019\t\u0019A\nb\u0001S\u00121\u0011q\u0002\u0014C\u0002%$a!a\u0007'\u0005\u0004IGABA\u0014M\t\u0007\u0011\u000e\u0002\u0004\u00024\u0019\u0012\r!\u001b\u0003\u0007\u0003\u007f1#\u0019A5\u0005\r\u0005-cE1\u0001j\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIa*bCa$\u0003\u0014\nU%q\u0013BM\u00057\u0013iJa(\u0003\"\n\r&QU\u000b\u0003\u0005#SC!a\f\u0002>\u0012)\u0001n\nb\u0001S\u0012)Qo\nb\u0001S\u0012)1p\nb\u0001S\u00121\u00111A\u0014C\u0002%$a!a\u0004(\u0005\u0004IGABA\u000eO\t\u0007\u0011\u000e\u0002\u0004\u0002(\u001d\u0012\r!\u001b\u0003\u0007\u0003g9#\u0019A5\u0005\r\u0005}rE1\u0001j\t\u0019\tYe\nb\u0001S\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012JTC\u0006BV\u0005_\u0013\tLa-\u00036\n]&\u0011\u0018B^\u0005{\u0013yL!1\u0016\u0005\t5&\u0006BA\u001e\u0003{#Q\u0001\u001b\u0015C\u0002%$Q!\u001e\u0015C\u0002%$Qa\u001f\u0015C\u0002%$a!a\u0001)\u0005\u0004IGABA\bQ\t\u0007\u0011\u000e\u0002\u0004\u0002\u001c!\u0012\r!\u001b\u0003\u0007\u0003OA#\u0019A5\u0005\r\u0005M\u0002F1\u0001j\t\u0019\ty\u0004\u000bb\u0001S\u00121\u00111\n\u0015C\u0002%\fqbY8qs\u0012\"WMZ1vYR$\u0013\u0007M\u000b\u0017\u0005\u000f\u0014YM!4\u0003P\nE'1\u001bBk\u0005/\u0014INa7\u0003^V\u0011!\u0011\u001a\u0016\u0005\u0003\u000f\ni\fB\u0003iS\t\u0007\u0011\u000eB\u0003vS\t\u0007\u0011\u000eB\u0003|S\t\u0007\u0011\u000e\u0002\u0004\u0002\u0004%\u0012\r!\u001b\u0003\u0007\u0003\u001fI#\u0019A5\u0005\r\u0005m\u0011F1\u0001j\t\u0019\t9#\u000bb\u0001S\u00121\u00111G\u0015C\u0002%$a!a\u0010*\u0005\u0004IGABA&S\t\u0007\u0011.A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0005G\u0004BA!:\u0003l6\u0011!q\u001d\u0006\u0004\u0005SL\u0015\u0001\u00027b]\u001eLAA!<\u0003h\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"Aa=\u0011\u0007\u0001\u0013)0C\u0002\u0003x\u0006\u00131!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2!\u001cB\u007f\u0011%\u0011y\u0010LA\u0001\u0002\u0004\u0011\u00190A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0007\u000b\u0001Raa\u0002\u0004\u000e5l!a!\u0003\u000b\u0007\r-\u0011)\u0001\u0006d_2dWm\u0019;j_:LAaa\u0004\u0004\n\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\u0019)ba\u0007\u0011\u0007\u0001\u001b9\"C\u0002\u0004\u001a\u0005\u0013qAQ8pY\u0016\fg\u000e\u0003\u0005\u0003\u0000:\n\t\u00111\u0001n\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\t\r8\u0011\u0005\u0005\n\u0005\u007f|\u0013\u0011!a\u0001\u0005g\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0005g\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005G\fa!Z9vC2\u001cH\u0003BB\u000b\u0007_A\u0001Ba@3\u0003\u0003\u0005\r!\u001c\u0015\b\u000f\rM2\u0011HB\u001e!\r\u00015QG\u0005\u0004\u0007o\t%\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0001c\u00014\u0004@\u0011)\u0001n\u0001b\u0001SB\u0019ama\u0011\u0005\u000bU\u001c!\u0019A5\u0011\u0007\u0019\u001c9\u0005B\u0003|\u0007\t\u0007\u0011\u000eE\u0002g\u0007\u0017\"a!a\u0001\u0004\u0005\u0004I\u0007c\u00014\u0004P\u00111\u0011qB\u0002C\u0002%\u00042AZB*\t\u0019\tYb\u0001b\u0001SB\u0019ama\u0016\u0005\r\u0005\u001d2A1\u0001j!\r171\f\u0003\u0007\u0003g\u0019!\u0019A5\u0011\u0007\u0019\u001cy\u0006\u0002\u0004\u0002@\r\u0011\r!\u001b\t\u0004M\u000e\rDABA&\u0007\t\u0007\u0011\u000e\u0003\u0004d\u0007\u0001\u00071Q\b\u0005\u0007c\u000e\u0001\ra!\u0011\t\r]\u001c\u0001\u0019AB#\u0011\u0019i8\u00011\u0001\u0004J!9\u0011qA\u0002A\u0002\r5\u0003bBA\n\u0007\u0001\u00071\u0011\u000b\u0005\b\u0003?\u0019\u0001\u0019AB+\u0011\u001d\tYc\u0001a\u0001\u00073Bq!a\u000e\u0004\u0001\u0004\u0019i\u0006C\u0004\u0002D\r\u0001\ra!\u0019\u0002\u000b\u0005\u0004\b\u000f\\=\u0016-\ru41QBD\u0007\u0017\u001byia%\u0004\u0018\u000em5qTBR\u0007O#bca \u0004*\u000e-6QVBX\u0007c\u001b\u0019l!.\u00048\u000ee61\u0018\t\u0017y\u001d\u0019\ti!\"\u0004\n\u000e55\u0011SBK\u00073\u001bij!)\u0004&B\u0019ama!\u0005\u000b!$!\u0019A5\u0011\u0007\u0019\u001c9\tB\u0003v\t\t\u0007\u0011\u000eE\u0002g\u0007\u0017#Qa\u001f\u0003C\u0002%\u00042AZBH\t\u0019\t\u0019\u0001\u0002b\u0001SB\u0019ama%\u0005\r\u0005=AA1\u0001j!\r17q\u0013\u0003\u0007\u00037!!\u0019A5\u0011\u0007\u0019\u001cY\n\u0002\u0004\u0002(\u0011\u0011\r!\u001b\t\u0004M\u000e}EABA\u001a\t\t\u0007\u0011\u000eE\u0002g\u0007G#a!a\u0010\u0005\u0005\u0004I\u0007c\u00014\u0004(\u00121\u00111\n\u0003C\u0002%Daa\u0019\u0003A\u0002\r\u0005\u0005BB9\u0005\u0001\u0004\u0019)\t\u0003\u0004x\t\u0001\u00071\u0011\u0012\u0005\u0007{\u0012\u0001\ra!$\t\u000f\u0005\u001dA\u00011\u0001\u0004\u0012\"9\u00111\u0003\u0003A\u0002\rU\u0005bBA\u0010\t\u0001\u00071\u0011\u0014\u0005\b\u0003W!\u0001\u0019ABO\u0011\u001d\t9\u0004\u0002a\u0001\u0007CCq!a\u0011\u0005\u0001\u0004\u0019)+A\u0004v]\u0006\u0004\b\u000f\\=\u0016-\r\u00057QZBi\u0007+\u001cIn!8\u0004b\u000e\u00158\u0011^Bw\u0007c$Baa1\u0004tB)\u0001i!2\u0004J&\u00191qY!\u0003\r=\u0003H/[8o!]\u0001\u0015QNBf\u0007\u001f\u001c\u0019na6\u0004\\\u000e}71]Bt\u0007W\u001cy\u000fE\u0002g\u0007\u001b$Q\u0001[\u0003C\u0002%\u00042AZBi\t\u0015)XA1\u0001j!\r17Q\u001b\u0003\u0006w\u0016\u0011\r!\u001b\t\u0004M\u000eeGABA\u0002\u000b\t\u0007\u0011\u000eE\u0002g\u0007;$a!a\u0004\u0006\u0005\u0004I\u0007c\u00014\u0004b\u00121\u00111D\u0003C\u0002%\u00042AZBs\t\u0019\t9#\u0002b\u0001SB\u0019am!;\u0005\r\u0005MRA1\u0001j!\r17Q\u001e\u0003\u0007\u0003\u007f)!\u0019A5\u0011\u0007\u0019\u001c\t\u0010\u0002\u0004\u0002L\u0015\u0011\r!\u001b\u0005\n\u0007k,\u0011\u0011!a\u0001\u0007o\f1\u0001\u001f\u00131!Yataa3\u0004P\u000eM7q[Bn\u0007?\u001c\u0019oa:\u0004l\u000e=\u0018\u0001D<sSR,'+\u001a9mC\u000e,GCAB\u007f!\u0011\u0011)oa@\n\t\u0011\u0005!q\u001d\u0002\u0007\u001f\nTWm\u0019;")
public final class Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;
    private final T4 t4;
    private final T5 t5;
    private final T6 t6;
    private final T7 t7;
    private final T8 t8;
    private final T9 t9;
    private final T10 t10;
    private final scala.Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> toScala;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Option<scala.Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> unapply(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> x$0) {
        return Tuple10$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return Tuple10$.MODULE$.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return Tuple10$.MODULE$.create(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public T1 t1() {
        return this.t1;
    }

    public T2 t2() {
        return this.t2;
    }

    public T3 t3() {
        return this.t3;
    }

    public T4 t4() {
        return this.t4;
    }

    public T5 t5() {
        return this.t5;
    }

    public T6 t6() {
        return this.t6;
    }

    public T7 t7() {
        return this.t7;
    }

    public T8 t8() {
        return this.t8;
    }

    public T9 t9() {
        return this.t9;
    }

    public T10 t10() {
        return this.t10;
    }

    public scala.Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        return new Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T10 copy$default$10() {
        return this.t10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T6 copy$default$6() {
        return this.t6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T7 copy$default$7() {
        return this.t7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T8 copy$default$8() {
        return this.t8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T9 copy$default$9() {
        return this.t9();
    }

    @Override
    public String productPrefix() {
        return "Tuple10";
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
                return this.t1();
            }
            case 1: {
                return this.t2();
            }
            case 2: {
                return this.t3();
            }
            case 3: {
                return this.t4();
            }
            case 4: {
                return this.t5();
            }
            case 5: {
                return this.t6();
            }
            case 6: {
                return this.t7();
            }
            case 7: {
                return this.t8();
            }
            case 8: {
                return this.t9();
            }
            case 9: {
                return this.t10();
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
        return x$1 instanceof Tuple10;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "t1";
            }
            case 1: {
                return "t2";
            }
            case 2: {
                return "t3";
            }
            case 3: {
                return "t4";
            }
            case 4: {
                return "t5";
            }
            case 5: {
                return "t6";
            }
            case 6: {
                return "t7";
            }
            case 7: {
                return "t8";
            }
            case 8: {
                return "t9";
            }
            case 9: {
                return "t10";
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
        if (!(object instanceof Tuple10)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple10 tuple10 = (Tuple10)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple10.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple10.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple10.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple10.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple10.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple10.t6())) return false;
        if (!BoxesRunTime.equals(this.t7(), tuple10.t7())) return false;
        if (!BoxesRunTime.equals(this.t8(), tuple10.t8())) return false;
        if (!BoxesRunTime.equals(this.t9(), tuple10.t9())) return false;
        if (!BoxesRunTime.equals(this.t10(), tuple10.t10())) return false;
        return true;
    }

    public Tuple10(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
        this.t10 = t10;
        Product.$init$(this);
        this.toScala = new scala.Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }
}

