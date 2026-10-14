/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import scala.Option;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionForJava8$;
import scala.compat.java8.OptionConverters$RichOptionalDouble$;
import scala.compat.java8.OptionConverters$RichOptionalGeneric$;
import scala.compat.java8.OptionConverters$RichOptionalInt$;
import scala.compat.java8.OptionConverters$RichOptionalLong$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\ret!\u00022d\u0011\u0003Qg!\u00027d\u0011\u0003i\u0007\"\u0002:\u0002\t\u0003\u0019h!\u0002;\u0002\u0003C)\b\"\u0002:\u0004\t\u00039\bbBA\t\u0007\u0019\u0005\u00111\u0003\u0005\b\u0003S\u0019a\u0011AA\u0016\u0011%\ti$\u0001b\u0001\n\u0007\ty\u0004\u0003\u0005\u0002P\u0005\u0001\u000b\u0011BA!\u0011%\t\t&\u0001b\u0001\n\u0007\t\u0019\u0006\u0003\u0005\u0002d\u0005\u0001\u000b\u0011BA+\u0011%\t)'\u0001b\u0001\n\u0007\t9\u0007\u0003\u0005\u0002x\u0005\u0001\u000b\u0011BA5\r\u0019\tI(A\u0002\u0002|!Q\u0011QQ\u0007\u0003\u0006\u0004%\t!a\"\t\u0015\u0005=UB!A!\u0002\u0013\tI\t\u0003\u0004s\u001b\u0011\u0005\u0011\u0011\u0013\u0005\b\u0003/kA\u0011AAM\u0011\u001d\ti*\u0004C\u0001\u0003?C\u0011\"!,\u000e\u0003\u0003%\t%a,\t\u0013\u0005EV\"!A\u0005B\u0005Mv!CA`\u0003\u0005\u0005\t\u0012AAa\r%\tI(AA\u0001\u0012\u0003\t\u0019\r\u0003\u0004s-\u0011\u0005\u0011Q\u0019\u0005\b\u0003\u000f4BQAAe\u0011\u001d\tIN\u0006C\u0003\u00037D\u0011\"!=\u0017\u0003\u0003%)!a=\t\u0013\u0005}h#!A\u0005\u0006\t\u0005\u0001\"CA`\u0003\u0005\u0005I1\u0001B\t\r\u0019\u0011y\"A\u0002\u0003\"!Q\u0011QQ\u000f\u0003\u0006\u0004%\tA!\n\t\u0015\u0005=UD!A!\u0002\u0013\u00119\u0003\u0003\u0004s;\u0011\u0005!Q\u0006\u0005\b\u0005giB\u0011\u0001B\u001b\u0011\u001d\ti*\bC\u0001\u0005sA\u0011\"!,\u001e\u0003\u0003%\t%a,\t\u0013\u0005EV$!A\u0005B\t\u0015s!\u0003B%\u0003\u0005\u0005\t\u0012\u0001B&\r%\u0011y\"AA\u0001\u0012\u0003\u0011i\u0005\u0003\u0004sM\u0011\u0005!q\n\u0005\b\u0005#2CQ\u0001B*\u0011\u001d\tIN\nC\u0003\u0005CB\u0011\"!='\u0003\u0003%)Aa\u001e\t\u0013\u0005}h%!A\u0005\u0006\t\r\u0005\"\u0003B%\u0003\u0005\u0005I1\u0001BJ\r\u0019\u0011\t+A\u0002\u0003$\"Q\u0011QQ\u0017\u0003\u0006\u0004%\tA!*\t\u0015\u0005=UF!A!\u0002\u0013\tI\u0005\u0003\u0004s[\u0011\u0005!q\u0015\u0005\b\u0003/kC\u0011\u0001BW\u0011\u001d\u0011\t,\fC\u0001\u0005gC\u0011\"!,.\u0003\u0003%\t%a,\t\u0013\u0005EV&!A\u0005B\t]v!\u0003B^\u0003\u0005\u0005\t\u0012\u0001B_\r%\u0011\t+AA\u0001\u0012\u0003\u0011y\f\u0003\u0004sm\u0011\u0005!\u0011\u0019\u0005\b\u0003\u000f4DQ\u0001Bb\u0011\u001d\u00119M\u000eC\u0003\u0005\u0013D\u0011\"!=7\u0003\u0003%)A!4\t\u0013\u0005}h'!A\u0005\u0006\tE\u0007\"\u0003B^\u0003\u0005\u0005I1\u0001Bm\r\u0019\u0011i.A\u0002\u0003`\"Q\u0011QQ\u001f\u0003\u0006\u0004%\tA!9\t\u0015\u0005=UH!A!\u0002\u0013\ti\u0006\u0003\u0004s{\u0011\u0005!1\u001d\u0005\b\u0003/kD\u0011\u0001Bu\u0011\u001d\u0011\t,\u0010C\u0001\u0005[D\u0011\"!,>\u0003\u0003%\t%a,\t\u0013\u0005EV(!A\u0005B\tEx!\u0003B{\u0003\u0005\u0005\t\u0012\u0001B|\r%\u0011i.AA\u0001\u0012\u0003\u0011I\u0010\u0003\u0004s\r\u0012\u0005!1 \u0005\b\u0003\u000f4EQ\u0001B\u007f\u0011\u001d\u00119M\u0012C\u0003\u0007\u0003A\u0011\"!=G\u0003\u0003%)a!\u0002\t\u0013\u0005}h)!A\u0005\u0006\r%\u0001\"\u0003B{\u0003\u0005\u0005I1AB\t\r\u0019\u0019)\"A\u0002\u0004\u0018!Q\u0011QQ'\u0003\u0006\u0004%\ta!\u0007\t\u0015\u0005=UJ!A!\u0002\u0013\t\t\b\u0003\u0004s\u001b\u0012\u000511\u0004\u0005\b\u0003/kE\u0011AB\u0011\u0011\u001d\u0011\t,\u0014C\u0001\u0007KA\u0011\"!,N\u0003\u0003%\t%a,\t\u0013\u0005EV*!A\u0005B\r%r!CB\u0017\u0003\u0005\u0005\t\u0012AB\u0018\r%\u0019)\"AA\u0001\u0012\u0003\u0019\t\u0004\u0003\u0004s-\u0012\u000511\u0007\u0005\b\u0003\u000f4FQAB\u001b\u0011\u001d\u00119M\u0016C\u0003\u0007sA\u0011\"!=W\u0003\u0003%)a!\u0010\t\u0013\u0005}h+!A\u0005\u0006\r\u0005\u0003\"CB\u0017\u0003\u0005\u0005I1AB%\u0011\u001d\u0019i%\u0001C\u0003\u0007\u001fBqa!\u0018\u0002\t\u000b\u0019y\u0006C\u0004\u0004^\u0005!)a!\u001c\t\u000f\ru\u0013\u0001\"\u0002\u0004r!91QL\u0001\u0005\u0006\rU\u0014\u0001E(qi&|gnQ8om\u0016\u0014H/\u001a:t\u0015\t!W-A\u0003kCZ\f\u0007H\u0003\u0002gO\u000611m\\7qCRT\u0011\u0001[\u0001\u0006g\u000e\fG.Y\u0002\u0001!\tY\u0017!D\u0001d\u0005Ay\u0005\u000f^5p]\u000e{gN^3si\u0016\u00148o\u0005\u0002\u0002]B\u0011q\u000e]\u0007\u0002O&\u0011\u0011o\u001a\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005Q'\u0001F*qK\u000eL\u0017\r\\5{KJ|em\u00149uS>t7/\u0006\u0003wy\u000651CA\u0002o)\u0005A\b#B=\u0004u\u0006-Q\"A\u0001\u0011\u0005mdH\u0002\u0001\u0003\u0006{\u000e\u0011\rA \u0002\u0002\u0003F\u0019q0!\u0002\u0011\u0007=\f\t!C\u0002\u0002\u0004\u001d\u0014qAT8uQ&tw\rE\u0002p\u0003\u000fI1!!\u0003h\u0005\r\te.\u001f\t\u0004w\u00065AABA\b\u0007\t\u0007aP\u0001\u0003UQ\u0006$\u0018\u0001\u00034s_6T\u0015M^1\u0015\t\u0005-\u0011Q\u0003\u0005\b\u0003/)\u0001\u0019AA\r\u0003\u0005y\u0007#BA\u000e\u0003KQXBAA\u000f\u0015\u0011\ty\"!\t\u0002\tU$\u0018\u000e\u001c\u0006\u0003\u0003G\tAA[1wC&!\u0011qEA\u000f\u0005!y\u0005\u000f^5p]\u0006d\u0017!\u00034s_6\u001c6-\u00197b)\u0011\tY!!\f\t\u000f\u0005]a\u00011\u0001\u00020A!q.!\r{\u0013\r\t\u0019d\u001a\u0002\u0007\u001fB$\u0018n\u001c8*\u0007\r\t9D\u0002\u0004\u0002:\r\u0001\u00111\b\u0002\u000ey1|7-\u00197!G\"LG\u000e\u001a \u0014\u0007\u0005]\u00020\u0001\u000eta\u0016\u001c\u0017.\u00197ju\u0016\u0014xl\u00149uS>t\u0017\r\u001c#pk\ndW-\u0006\u0002\u0002BA1\u0011pAA\"\u0003\u0013\u00022a\\A#\u0013\r\t9e\u001a\u0002\u0007\t>,(\r\\3\u0011\t\u0005m\u00111J\u0005\u0005\u0003\u001b\niB\u0001\bPaRLwN\\1m\t>,(\r\\3\u00027M\u0004XmY5bY&TXM]0PaRLwN\\1m\t>,(\r\\3!\u0003]\u0019\b/Z2jC2L'0\u001a:`\u001fB$\u0018n\u001c8bY&sG/\u0006\u0002\u0002VA1\u0011pAA,\u0003;\u00022a\\A-\u0013\r\tYf\u001a\u0002\u0004\u0013:$\b\u0003BA\u000e\u0003?JA!!\u0019\u0002\u001e\tYq\n\u001d;j_:\fG.\u00138u\u0003a\u0019\b/Z2jC2L'0\u001a:`\u001fB$\u0018n\u001c8bY&sG\u000fI\u0001\u0019gB,7-[1mSj,'oX(qi&|g.\u00197M_:<WCAA5!\u0019I8!a\u001b\u0002rA\u0019q.!\u001c\n\u0007\u0005=tM\u0001\u0003M_:<\u0007\u0003BA\u000e\u0003gJA!!\u001e\u0002\u001e\taq\n\u001d;j_:\fG\u000eT8oO\u0006I2\u000f]3dS\u0006d\u0017N_3s?>\u0003H/[8oC2duN\\4!\u0005M\u0011\u0016n\u00195PaRLwN\\1m\u000f\u0016tWM]5d+\u0011\ti(!$\u0014\u00075\ty\bE\u0002p\u0003\u0003K1!a!h\u0005\u0019\te.\u001f,bY\u0006QQO\u001c3fe2L\u0018N\\4\u0016\u0005\u0005%\u0005CBA\u000e\u0003K\tY\tE\u0002|\u0003\u001b#Q!`\u0007C\u0002y\f1\"\u001e8eKJd\u00170\u001b8hAQ!\u00111SAK!\u0011IX\"a#\t\u000f\u0005\u0015\u0005\u00031\u0001\u0002\n\u00069\u0011m]*dC2\fWCAAN!\u0015y\u0017\u0011GAF\u0003-\t7\u000f\u0015:j[&$\u0018N^3\u0016\t\u0005\u0005\u0016Q\u0015\u000b\u0005\u0003G\u000b9\u000bE\u0002|\u0003K#a!a\u0004\u0013\u0005\u0004q\bbBAU%\u0001\u000f\u00111V\u0001\u0007gB,7m\u00149\u0011\re\u001c\u00111RAR\u0003!A\u0017m\u001d5D_\u0012,GCAA,\u0003\u0019)\u0017/^1mgR!\u0011QWA^!\ry\u0017qW\u0005\u0004\u0003s;'a\u0002\"p_2,\u0017M\u001c\u0005\n\u0003{#\u0012\u0011!a\u0001\u0003\u000b\t1\u0001\u001f\u00132\u0003M\u0011\u0016n\u00195PaRLwN\\1m\u000f\u0016tWM]5d!\tIhc\u0005\u0002\u0017]R\u0011\u0011\u0011Y\u0001\u0012CN\u001c6-\u00197bI\u0015DH/\u001a8tS>tW\u0003BAf\u0003#$B!!4\u0002TB)q.!\r\u0002PB\u001910!5\u0005\u000buD\"\u0019\u0001@\t\u000f\u0005U\u0007\u00041\u0001\u0002X\u0006)A\u0005\u001e5jgB!\u00110DAh\u0003U\t7\u000f\u0015:j[&$\u0018N^3%Kb$XM\\:j_:,b!!8\u0002d\u0006-H\u0003BAp\u0003[$B!!9\u0002fB\u001910a9\u0005\r\u0005=\u0011D1\u0001\u007f\u0011\u001d\tI+\u0007a\u0002\u0003O\u0004b!_\u0002\u0002j\u0006\u0005\bcA>\u0002l\u0012)Q0\u0007b\u0001}\"9\u0011Q[\rA\u0002\u0005=\b\u0003B=\u000e\u0003S\f!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V!\u0011Q_A\u007f)\u0011\ty+a>\t\u000f\u0005U'\u00041\u0001\u0002zB!\u00110DA~!\rY\u0018Q \u0003\u0006{j\u0011\rA`\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,BAa\u0001\u0003\u0010Q!!Q\u0001B\u0005)\u0011\t)La\u0002\t\u0013\u0005u6$!AA\u0002\u0005\u0015\u0001bBAk7\u0001\u0007!1\u0002\t\u0005s6\u0011i\u0001E\u0002|\u0005\u001f!Q!`\u000eC\u0002y,BAa\u0005\u0003\u001aQ!!Q\u0003B\u000e!\u0011IXBa\u0006\u0011\u0007m\u0014I\u0002B\u0003~9\t\u0007a\u0010C\u0004\u0002\u0006r\u0001\rA!\b\u0011\r\u0005m\u0011Q\u0005B\f\u0005I\u0011\u0016n\u00195PaRLwN\u001c$pe*\u000bg/\u0019\u001d\u0016\t\t\r\"1F\n\u0004;\u0005}TC\u0001B\u0014!\u0015y\u0017\u0011\u0007B\u0015!\rY(1\u0006\u0003\u0006{v\u0011\rA \u000b\u0005\u0005_\u0011\t\u0004\u0005\u0003z;\t%\u0002bBACA\u0001\u0007!qE\u0001\u0007CNT\u0015M^1\u0016\u0005\t]\u0002CBA\u000e\u0003K\u0011I#\u0006\u0003\u0003<\t}B\u0003\u0002B\u001f\u0005\u0003\u00022a\u001fB \t\u0019\tyA\tb\u0001}\"9\u0011\u0011\u0016\u0012A\u0004\t\r\u0003CB=\u0004\u0005S\u0011i\u0004\u0006\u0003\u00026\n\u001d\u0003\"CA_I\u0005\u0005\t\u0019AA\u0003\u0003I\u0011\u0016n\u00195PaRLwN\u001c$pe*\u000bg/\u0019\u001d\u0011\u0005e43C\u0001\u0014o)\t\u0011Y%\u0001\tbg*\u000bg/\u0019\u0013fqR,gn]5p]V!!Q\u000bB.)\u0011\u00119F!\u0018\u0011\r\u0005m\u0011Q\u0005B-!\rY(1\f\u0003\u0006{\"\u0012\rA \u0005\b\u0003+D\u0003\u0019\u0001B0!\u0011IXD!\u0017\u0016\r\t\r$\u0011\u000eB9)\u0011\u0011)Ga\u001d\u0015\t\t\u001d$1\u000e\t\u0004w\n%DABA\bS\t\u0007a\u0010C\u0004\u0002*&\u0002\u001dA!\u001c\u0011\re\u001c!q\u000eB4!\rY(\u0011\u000f\u0003\u0006{&\u0012\rA \u0005\b\u0003+L\u0003\u0019\u0001B;!\u0011IXDa\u001c\u0016\t\te$\u0011\u0011\u000b\u0005\u0003_\u0013Y\bC\u0004\u0002V*\u0002\rA! \u0011\tel\"q\u0010\t\u0004w\n\u0005E!B?+\u0005\u0004qX\u0003\u0002BC\u0005##BAa\"\u0003\fR!\u0011Q\u0017BE\u0011%\tilKA\u0001\u0002\u0004\t)\u0001C\u0004\u0002V.\u0002\rA!$\u0011\tel\"q\u0012\t\u0004w\nEE!B?,\u0005\u0004qX\u0003\u0002BK\u00057#BAa&\u0003\u001eB!\u00110\bBM!\rY(1\u0014\u0003\u0006{2\u0012\rA \u0005\b\u0003\u000bc\u0003\u0019\u0001BP!\u0015y\u0017\u0011\u0007BM\u0005I\u0011\u0016n\u00195PaRLwN\\1m\t>,(\r\\3\u0014\u00075\ny(\u0006\u0002\u0002JQ!!\u0011\u0016BV!\tIX\u0006C\u0004\u0002\u0006B\u0002\r!!\u0013\u0016\u0005\t=\u0006#B8\u00022\u0005\r\u0013!C1t\u000f\u0016tWM]5d+\t\u0011)\f\u0005\u0004\u0002\u001c\u0005\u0015\u00121\t\u000b\u0005\u0003k\u0013I\fC\u0005\u0002>R\n\t\u00111\u0001\u0002\u0006\u0005\u0011\"+[2i\u001fB$\u0018n\u001c8bY\u0012{WO\u00197f!\tIhg\u0005\u00027]R\u0011!Q\u0018\u000b\u0005\u0005_\u0013)\rC\u0004\u0002Vb\u0002\rA!+\u0002'\u0005\u001cx)\u001a8fe&\u001cG%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\tU&1\u001a\u0005\b\u0003+L\u0004\u0019\u0001BU)\u0011\tyKa4\t\u000f\u0005U'\b1\u0001\u0003*R!!1\u001bBl)\u0011\t)L!6\t\u0013\u0005u6(!AA\u0002\u0005\u0015\u0001bBAkw\u0001\u0007!\u0011\u0016\u000b\u0005\u0005S\u0013Y\u000eC\u0004\u0002\u0006r\u0002\r!!\u0013\u0003\u001fIK7\r[(qi&|g.\u00197J]R\u001c2!PA@+\t\ti\u0006\u0006\u0003\u0003f\n\u001d\bCA=>\u0011\u001d\t)\t\u0011a\u0001\u0003;*\"Aa;\u0011\u000b=\f\t$a\u0016\u0016\u0005\t=\bCBA\u000e\u0003K\t9\u0006\u0006\u0003\u00026\nM\b\"CA_\t\u0006\u0005\t\u0019AA\u0003\u0003=\u0011\u0016n\u00195PaRLwN\\1m\u0013:$\bCA=G'\t1e\u000e\u0006\u0002\u0003xR!!1\u001eB\u0000\u0011\u001d\t)\u000e\u0013a\u0001\u0005K$BAa<\u0004\u0004!9\u0011Q[%A\u0002\t\u0015H\u0003BAX\u0007\u000fAq!!6K\u0001\u0004\u0011)\u000f\u0006\u0003\u0004\f\r=A\u0003BA[\u0007\u001bA\u0011\"!0L\u0003\u0003\u0005\r!!\u0002\t\u000f\u0005U7\n1\u0001\u0003fR!!Q]B\n\u0011\u001d\t)\t\u0014a\u0001\u0003;\u0012\u0001CU5dQ>\u0003H/[8oC2duN\\4\u0014\u00075\u000by(\u0006\u0002\u0002rQ!1QDB\u0010!\tIX\nC\u0004\u0002\u0006B\u0003\r!!\u001d\u0016\u0005\r\r\u0002#B8\u00022\u0005-TCAB\u0014!\u0019\tY\"!\n\u0002lQ!\u0011QWB\u0016\u0011%\ti\fVA\u0001\u0002\u0004\t)!\u0001\tSS\u000eDw\n\u001d;j_:\fG\u000eT8oOB\u0011\u0011PV\n\u0003-:$\"aa\f\u0015\t\r\r2q\u0007\u0005\b\u0003+D\u0006\u0019AB\u000f)\u0011\u00199ca\u000f\t\u000f\u0005U\u0017\f1\u0001\u0004\u001eQ!\u0011qVB \u0011\u001d\t)N\u0017a\u0001\u0007;!Baa\u0011\u0004HQ!\u0011QWB#\u0011%\tilWA\u0001\u0002\u0004\t)\u0001C\u0004\u0002Vn\u0003\ra!\b\u0015\t\ru11\n\u0005\b\u0003\u000bc\u0006\u0019AA9\u0003\u0019!xNS1wCV!1\u0011KB,)\u0011\u0019\u0019f!\u0017\u0011\r\u0005m\u0011QEB+!\rY8q\u000b\u0003\u0006{v\u0013\rA \u0005\b\u0003/i\u0006\u0019AB.!\u0015y\u0017\u0011GB+\u0003\u001d!xnU2bY\u0006,Ba!\u0019\u0004hQ!11MB5!\u0015y\u0017\u0011GB3!\rY8q\r\u0003\u0006{z\u0013\rA \u0005\b\u0003/q\u0006\u0019AB6!\u0019\tY\"!\n\u0004fQ!!qVB8\u0011\u001d\t9b\u0018a\u0001\u0003\u0013\"BAa;\u0004t!9\u0011q\u00031A\u0002\u0005uC\u0003BB\u0012\u0007oBq!a\u0006b\u0001\u0004\t\t\b")
public final class OptionConverters {
    public static Option<Object> toScala(OptionalLong optionalLong) {
        return OptionConverters$.MODULE$.toScala(optionalLong);
    }

    public static Option<Object> toScala(OptionalInt optionalInt) {
        return OptionConverters$.MODULE$.toScala(optionalInt);
    }

    public static Option<Object> toScala(OptionalDouble optionalDouble) {
        return OptionConverters$.MODULE$.toScala(optionalDouble);
    }

    public static <A> Option<A> toScala(Optional<A> optional) {
        return OptionConverters$.MODULE$.toScala(optional);
    }

    public static <A> Optional<A> toJava(Option<A> option) {
        return OptionConverters$.MODULE$.toJava(option);
    }

    public static OptionalLong RichOptionalLong(OptionalLong optionalLong) {
        return OptionConverters$.MODULE$.RichOptionalLong(optionalLong);
    }

    public static OptionalInt RichOptionalInt(OptionalInt optionalInt) {
        return OptionConverters$.MODULE$.RichOptionalInt(optionalInt);
    }

    public static OptionalDouble RichOptionalDouble(OptionalDouble optionalDouble) {
        return OptionConverters$.MODULE$.RichOptionalDouble(optionalDouble);
    }

    public static Option RichOptionForJava8(Option option) {
        return OptionConverters$.MODULE$.RichOptionForJava8(option);
    }

    public static Optional RichOptionalGeneric(Optional optional) {
        return OptionConverters$.MODULE$.RichOptionalGeneric(optional);
    }

    public static SpecializerOfOptions<Object, OptionalLong> specializer_OptionalLong() {
        return OptionConverters$.MODULE$.specializer_OptionalLong();
    }

    public static SpecializerOfOptions<Object, OptionalInt> specializer_OptionalInt() {
        return OptionConverters$.MODULE$.specializer_OptionalInt();
    }

    public static SpecializerOfOptions<Object, OptionalDouble> specializer_OptionalDouble() {
        return OptionConverters$.MODULE$.specializer_OptionalDouble();
    }

    public static final class RichOptionForJava8<A> {
        private final Option<A> underlying;

        public Option<A> underlying() {
            return this.underlying;
        }

        public Optional<A> asJava() {
            return OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(this.underlying());
        }

        public <That> That asPrimitive(SpecializerOfOptions<A, That> specOp) {
            return OptionConverters$RichOptionForJava8$.MODULE$.asPrimitive$extension(this.underlying(), specOp);
        }

        public int hashCode() {
            return OptionConverters$RichOptionForJava8$.MODULE$.hashCode$extension(this.underlying());
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptionForJava8$.MODULE$.equals$extension(this.underlying(), x$1);
        }

        public RichOptionForJava8(Option<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static final class RichOptionalDouble {
        private final OptionalDouble underlying;

        public OptionalDouble underlying() {
            return this.underlying;
        }

        public Option<Object> asScala() {
            return OptionConverters$RichOptionalDouble$.MODULE$.asScala$extension(this.underlying());
        }

        public Optional<Object> asGeneric() {
            return OptionConverters$RichOptionalDouble$.MODULE$.asGeneric$extension(this.underlying());
        }

        public int hashCode() {
            return OptionConverters$RichOptionalDouble$.MODULE$.hashCode$extension(this.underlying());
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptionalDouble$.MODULE$.equals$extension(this.underlying(), x$1);
        }

        public RichOptionalDouble(OptionalDouble underlying) {
            this.underlying = underlying;
        }
    }

    public static final class RichOptionalGeneric<A> {
        private final Optional<A> underlying;

        public Optional<A> underlying() {
            return this.underlying;
        }

        public Option<A> asScala() {
            return OptionConverters$RichOptionalGeneric$.MODULE$.asScala$extension(this.underlying());
        }

        public <That> That asPrimitive(SpecializerOfOptions<A, That> specOp) {
            return OptionConverters$RichOptionalGeneric$.MODULE$.asPrimitive$extension(this.underlying(), specOp);
        }

        public int hashCode() {
            return OptionConverters$RichOptionalGeneric$.MODULE$.hashCode$extension(this.underlying());
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptionalGeneric$.MODULE$.equals$extension(this.underlying(), x$1);
        }

        public RichOptionalGeneric(Optional<A> underlying) {
            this.underlying = underlying;
        }
    }

    public static final class RichOptionalInt {
        private final OptionalInt underlying;

        public OptionalInt underlying() {
            return this.underlying;
        }

        public Option<Object> asScala() {
            return OptionConverters$RichOptionalInt$.MODULE$.asScala$extension(this.underlying());
        }

        public Optional<Object> asGeneric() {
            return OptionConverters$RichOptionalInt$.MODULE$.asGeneric$extension(this.underlying());
        }

        public int hashCode() {
            return OptionConverters$RichOptionalInt$.MODULE$.hashCode$extension(this.underlying());
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptionalInt$.MODULE$.equals$extension(this.underlying(), x$1);
        }

        public RichOptionalInt(OptionalInt underlying) {
            this.underlying = underlying;
        }
    }

    public static final class RichOptionalLong {
        private final OptionalLong underlying;

        public OptionalLong underlying() {
            return this.underlying;
        }

        public Option<Object> asScala() {
            return OptionConverters$RichOptionalLong$.MODULE$.asScala$extension(this.underlying());
        }

        public Optional<Object> asGeneric() {
            return OptionConverters$RichOptionalLong$.MODULE$.asGeneric$extension(this.underlying());
        }

        public int hashCode() {
            return OptionConverters$RichOptionalLong$.MODULE$.hashCode$extension(this.underlying());
        }

        public boolean equals(Object x$1) {
            return OptionConverters$RichOptionalLong$.MODULE$.equals$extension(this.underlying(), x$1);
        }

        public RichOptionalLong(OptionalLong underlying) {
            this.underlying = underlying;
        }
    }

    public static abstract class SpecializerOfOptions<A, That> {
        public abstract That fromJava(Optional<A> var1);

        public abstract That fromScala(Option<A> var1);
    }
}

