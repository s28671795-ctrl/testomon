/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import java.math.MathContext;
import java.math.RoundingMode;
import scala.Enumeration;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.NumericRange;
import scala.collection.immutable.Range$BigDecimal$;
import scala.math.BigDecimal$;
import scala.math.BigInt;
import scala.math.Ordered;
import scala.math.ScalaNumber;
import scala.math.ScalaNumericAnyConversions;
import scala.math.ScalaNumericConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0011\u0005v\u0001CA\u0011\u0003GA\t!!\f\u0007\u0011\u0005E\u00121\u0005E\u0001\u0003gAq!!\u0014\u0002\t\u0003\ty\u0005C\u0005\u0002R\u0005\u0011\r\u0011\"\u0004\u0002T!A\u0011\u0011L\u0001!\u0002\u001b\t)\u0006C\u0005\u0002\\\u0005\u0011\r\u0011\"\u0004\u0002^!A\u00111M\u0001!\u0002\u001b\ty\u0006C\u0005\u0002f\u0005\u0011\r\u0011\"\u0004\u0002h!A\u0011QN\u0001!\u0002\u001b\tI\u0007\u0003\u0005\u0002p\u0005\u0001\u000b\u0011BA9\u0011!\t9(\u0001Q\u0001\n\u0005E\u0004\"CA=\u0003\t\u0007I\u0011AA>\u0011!\t9)\u0001Q\u0001\n\u0005u\u0004BCAE\u0003!\u0015\r\u0015\"\u0003\u0002\f\u001e9!qV\u0001\t\u0002\tEfa\u0002B[\u0003!\u0005!q\u0017\u0005\b\u0003\u001bzA\u0011\u0001B`\u000b\u0019\u0011)l\u0004\u0001\u0003B\"I!\u0011Z\bC\u0002\u0013\u0005!1\u001a\u0005\t\u0005\u001b|\u0001\u0015!\u0003\u0003B\"I!qZ\bC\u0002\u0013\u0005!1\u001a\u0005\t\u0005#|\u0001\u0015!\u0003\u0003B\"I!1[\bC\u0002\u0013\u0005!1\u001a\u0005\t\u0005+|\u0001\u0015!\u0003\u0003B\"I!q[\bC\u0002\u0013\u0005!1\u001a\u0005\t\u00053|\u0001\u0015!\u0003\u0003B\"I!1\\\bC\u0002\u0013\u0005!1\u001a\u0005\t\u0005;|\u0001\u0015!\u0003\u0003B\"I!q\\\bC\u0002\u0013\u0005!1\u001a\u0005\t\u0005C|\u0001\u0015!\u0003\u0003B\"I!1]\bC\u0002\u0013\u0005!1\u001a\u0005\t\u0005K|\u0001\u0015!\u0003\u0003B\"I!q]\bC\u0002\u0013\u0005!1\u001a\u0005\t\u0005S|\u0001\u0015!\u0003\u0003B\"I!1^\b\u0002\u0002\u0013%!Q\u001e\u0005\b\u0007\u007f\u000bA\u0011ABa\u0011\u001d\u0019y,\u0001C\u0001\u0007\u0013Dqaa0\u0002\t\u0003\u0019i\rC\u0004\u0004@\u0006!\ta!6\t\u000f\r}\u0016\u0001\"\u0001\u0004Z\"91qX\u0001\u0005\u0002\r\u0005\bbBB`\u0003\u0011\u00051Q\u001d\u0005\b\u0007[\fA\u0011ABx\u0011\u001d\u0019i/\u0001C\u0001\u0007kDqa!?\u0002\t\u0003\u0019Y\u0010C\u0004\u0004z\u0006!\t\u0001\"\u0001\t\u000f\re\u0018\u0001\"\u0001\u0005\u0006!91\u0011`\u0001\u0005\u0002\u0011-\u0001bBB}\u0003\u0011\u0005Aq\u0002\u0005\b\u0007s\fA\u0011\u0001C\u000b\u0011\u001d!i\"\u0001C\u0001\t?Aq\u0001\"\b\u0002\t\u0003!\u0019\u0003C\u0004\u0003\u0016\u0006!\t\u0001\"\u000b\t\u000f\tU\u0015\u0001\"\u0001\u00050!9!QS\u0001\u0005\u0002\u0011U\u0002b\u0002BK\u0003\u0011\u0005A\u0011\b\u0005\b\u0005+\u000bA\u0011\u0001C \u0011\u001d\u0011)*\u0001C\u0001\t\u000fBqA!&\u0002\t\u0003!y\u0005C\u0004\u0003\u0016\u0006!\t\u0001b\u0015\t\u000f\tU\u0015\u0001\"\u0001\u0005Z!9!QS\u0001\u0005\u0002\u0011u\u0003b\u0002BK\u0003\u0011\u0005A1\r\u0005\b\u0005+\u000bA\u0011\u0001C4\u0011\u001d\u0011)*\u0001C\u0001\t[BqA!&\u0002\t\u0003!\t\bC\u0004\u0003\u0016\u0006!\t\u0001b\u001e\t\u000f\tU\u0015\u0001\"\u0001\u0005~!9!QS\u0001\u0005\u0002\u0011\u0015\u0005b\u0002CE\u0003\u0011\rA1\u0012\u0005\b\t\u001f\u000bA1\u0001CI\u0011\u001d!)*\u0001C\u0002\t/Cq\u0001b'\u0002\t\u0007!i\nC\u0005\u0003l\u0006\t\t\u0011\"\u0003\u0003n\u001a9\u0011\u0011GA\u0012\u0005\u0005U\u0005BCA[\u0015\n\u0015\r\u0011\"\u0001\u00028\"Q\u0011Q\u0018&\u0003\u0002\u0003\u0006I!!/\t\u0015\u0005}&J!b\u0001\n\u0003\tY\b\u0003\u0006\u0002B*\u0013\t\u0011)A\u0005\u0003{Bq!!\u0014K\t\u0003\t\u0019\rC\u0004\u0002N)#\t!!3\t\u0013\u00055'\n1A\u0005\u000e\u0005=\u0007\"CAi\u0015\u0002\u0007IQBAj\u0011!\tyN\u0013Q!\u000e\u0005E\u0004bBAq\u0015\u00125\u00111\u001d\u0005\b\u0003KTE\u0011IAt\u0011\u001d\tIO\u0013C!\u0003WDq!!@K\t\u0003\ny\u0010C\u0004\u0003\u0002)#\t%a@\t\u000f\t\r!\n\"\u0011\u0002\u0000\"9!Q\u0001&\u0005B\u0005}\bb\u0002B\u0004\u0015\u0012\u0005\u0011q \u0005\b\u0005\u0013QE\u0011AA\u0000\u0011\u001d\u0011YA\u0013C\u0001\u0003\u007fDqA!\u0004K\t\u0003\ty\u0010C\u0004\u0003\u0010)#\t!a@\t\u000f\tE!\n\"\u0001\u0002\u0000\"9!1\u0003&\u0005\u0002\u0005}\bb\u0002B\u000b\u0015\u0012%!q\u0003\u0005\b\u0005GQE\u0011AA\u0000\u0011\u001d\u0011)C\u0013C\u0001\u0005OAq!!;K\t\u0003\u0011I\u0003C\u0004\u0003.)#\tAa\f\t\u000f\tM\"\n\"\u0001\u00036!9!\u0011\b&\u0005\u0002\tm\u0002b\u0002B \u0015\u0012\u0005!\u0011\t\u0005\b\u0005\u000bRE\u0011\u0001B$\u0011\u001d\u0011YE\u0013C\u0001\u0005\u001bBqAa\u0016K\t\u0003\u0011I\u0006C\u0004\u0003^)#\tAa\u0018\t\u000f\t\r$\n\"\u0001\u0003f!9!\u0011\u000e&\u0005\u0002\t-\u0004b\u0002B8\u0015\u0012\u0005!\u0011\u000f\u0005\b\u0005kRE\u0011\u0001B<\u0011\u001d\u0011iH\u0013C\u0001\u0005\u007fBqA!!K\t\u0003\u0011y\bC\u0004\u0003\u0004*#\t!a4\t\u000f\t\u0015%\n\"\u0001\u0003\u0000!9!q\u0011&\u0005\u0002\u0005=\u0007b\u0002BE\u0015\u0012\u0005!1\u0012\u0005\b\u0005\u001fSE\u0011\u0001B@\u0011\u001d\u0011\tJ\u0013C\u0001\u0003\u001fDqAa%K\t\u0003\u0011y\bC\u0004\u0003\u0016*#\tAa&\t\u000f\tm%\n\"\u0001\u0003\u001e\"9!1\u0014&\u0005\u0002\t\u0005\u0006b\u0002B~\u0015\u0012\u0005#Q \u0005\b\u0007\u000bQE\u0011IB\u0004\u0011\u001d\u0019yA\u0013C\u0001\u0007#Aqa!\u0007K\t\u0003\ty\rC\u0004\u0004\u001c)#\ta!\b\t\u000f\r\u0015\"\n\"\u0001\u0004(!91q\u0006&\u0005\u0002\rE\u0002bBB\u001d\u0015\u0012\u0005!Q \u0005\b\u0007wQE\u0011AB\u0004\u0011\u001d\u0019iD\u0013C\u0001\u0003\u001fDqaa\u0010K\t\u0003\u0019i\u0002C\u0004\u0004B)#\taa\u0011\t\u000f\r\u0005#\n\"\u0001\u0004p!91q\u000f&\u0005\u0002\re\u0004bBB<\u0015\u0012\u00051Q\u0011\u0005\b\u0007#SE\u0011ABJ\u0011\u001d\u0019YJ\u0013C\u0001\u0007;Cqa!*K\t\u0003\u001a9+\u0001\u0006CS\u001e$UmY5nC2TA!!\n\u0002(\u0005!Q.\u0019;i\u0015\t\tI#A\u0003tG\u0006d\u0017m\u0001\u0001\u0011\u0007\u0005=\u0012!\u0004\u0002\u0002$\tQ!)[4EK\u000eLW.\u00197\u0014\u000b\u0005\t)$!\u0010\u0011\t\u0005]\u0012\u0011H\u0007\u0003\u0003OIA!a\u000f\u0002(\t1\u0011I\\=SK\u001a\u0004B!a\u0010\u0002J5\u0011\u0011\u0011\t\u0006\u0005\u0003\u0007\n)%\u0001\u0002j_*\u0011\u0011qI\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002L\u0005\u0005#\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\b\u0006\u0002\u0002.\u0005\u0001R.\u0019=j[Vl\u0007*Y:i'\u000e\fG.Z\u000b\u0003\u0003+z!!a\u0016\u001e\u0005M1\u0015!E7bq&lW/\u001c%bg\"\u001c6-\u00197fA\u0005\u0019\u0002.Y:i\u0007>$WMT8u\u0007>l\u0007/\u001e;fIV\u0011\u0011qL\b\u0003\u0003CjB!\u0018)j\u001f\u0005!\u0002.Y:i\u0007>$WMT8u\u0007>l\u0007/\u001e;fI\u0002\n1\u0002Z3dSJ\u0012\u0017N\\1ssV\u0011\u0011\u0011N\b\u0003\u0003W\u0002\u0003\u0002\u0011\u0006\u0014 &I8U]\u0001\rI\u0016\u001c\u0017N\r2j]\u0006\u0014\u0018\u0010I\u0001\n[&t7)Y2iK\u0012\u0004B!a\u000e\u0002t%!\u0011QOA\u0014\u0005\rIe\u000e^\u0001\n[\u0006D8)Y2iK\u0012\f!\u0003Z3gCVdG/T1uQ\u000e{g\u000e^3yiV\u0011\u0011Q\u0010\t\u0005\u0003\u007f\n\u0019)\u0004\u0002\u0002\u0002*!\u0011QEA#\u0013\u0011\t))!!\u0003\u00175\u000bG\u000f[\"p]R,\u0007\u0010^\u0001\u0014I\u00164\u0017-\u001e7u\u001b\u0006$\bnQ8oi\u0016DH\u000fI\u0001\u0006G\u0006\u001c\u0007.Z\u000b\u0003\u0003\u001b\u0003b!a\u000e\u0002\u0010\u0006M\u0015\u0002BAI\u0003O\u0011Q!\u0011:sCf\u00042!a\fK'%Q\u0015qSAO\u0003G\u000by\u000b\u0005\u0003\u00020\u0005e\u0015\u0002BAN\u0003G\u00111bU2bY\u0006tU/\u001c2feB!\u0011qFAP\u0013\u0011\t\t+a\t\u0003/M\u001b\u0017\r\\1Ok6,'/[2D_:4XM]:j_:\u001c\b\u0003BAS\u0003WsA!a\u000e\u0002(&!\u0011\u0011VA\u0014\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u0013\u0002.*!\u0011\u0011VA\u0014!\u0019\ty#!-\u0002\u0014&!\u00111WA\u0012\u0005\u001dy%\u000fZ3sK\u0012\f!BY5h\t\u0016\u001c\u0017.\\1m+\t\tI\f\u0005\u0003\u0002\u0000\u0005m\u0016\u0002BA\u0019\u0003\u0003\u000b1BY5h\t\u0016\u001c\u0017.\\1mA\u0005\u0011QnY\u0001\u0004[\u000e\u0004CCBAJ\u0003\u000b\f9\rC\u0004\u00026>\u0003\r!!/\t\u000f\u0005}v\n1\u0001\u0002~Q!\u00111SAf\u0011\u001d\t)\f\u0015a\u0001\u0003s\u000b\u0001cY8naV$X\r\u001a%bg\"\u001cu\u000eZ3\u0016\u0005\u0005E\u0014\u0001F2p[B,H/\u001a3ICND7i\u001c3f?\u0012*\u0017\u000f\u0006\u0003\u0002V\u0006m\u0007\u0003BA\u001c\u0003/LA!!7\u0002(\t!QK\\5u\u0011%\tiNUA\u0001\u0002\u0004\t\t(A\u0002yIE\n\u0011cY8naV$X\r\u001a%bg\"\u001cu\u000eZ3!\u0003=\u0019w.\u001c9vi\u0016D\u0015m\u001d5D_\u0012,GCAAk\u0003!A\u0017m\u001d5D_\u0012,GCAA9\u0003\u0019)\u0017/^1mgR!\u0011Q^Az!\u0011\t9$a<\n\t\u0005E\u0018q\u0005\u0002\b\u0005>|G.Z1o\u0011\u001d\t)P\u0016a\u0001\u0003o\fA\u0001\u001e5biB!\u0011qGA}\u0013\u0011\tY0a\n\u0003\u0007\u0005s\u00170A\u0006jgZ\u000bG.\u001b3CsR,WCAAw\u00031I7OV1mS\u0012\u001c\u0006n\u001c:u\u0003-I7OV1mS\u0012\u001c\u0005.\u0019:\u0002\u0015%\u001ch+\u00197jI&sG/A\u0006jgZ\u000bG.\u001b3M_:<\u0017aD5t\t\u0016\u001c\u0017.\\1m\t>,(\r\\3\u0002\u001d%\u001cH)Z2j[\u0006dg\t\\8bi\u0006q\u0011n\u001d\"j]\u0006\u0014\u0018\u0010R8vE2,\u0017!D5t\u0005&t\u0017M]=GY>\fG/A\u0007jg\u0016C\u0018m\u0019;E_V\u0014G.Z\u0001\rSN,\u00050Y2u\r2|\u0017\r^\u0001\u0016]>\f%/\u001b;i[\u0016$\u0018nY#yG\u0016\u0004H/[8o)\u0011\tiO!\u0007\t\u0011\tm!\r\"a\u0001\u0005;\tAAY8esB1\u0011q\u0007B\u0010\u0003+LAA!\t\u0002(\tAAHY=oC6,g(A\u0004jg^Cw\u000e\\3\u0002\u0015UtG-\u001a:ms&tw\r\u0006\u0002\u0002:R!\u0011Q\u001eB\u0016\u0011\u001d\t)0\u001aa\u0001\u0003'\u000bqaY8na\u0006\u0014X\r\u0006\u0003\u0002r\tE\u0002bBA{M\u0002\u0007\u00111S\u0001\u0006IAdWo\u001d\u000b\u0005\u0003'\u00139\u0004C\u0004\u0002v\u001e\u0004\r!a%\u0002\r\u0011j\u0017N\\;t)\u0011\t\u0019J!\u0010\t\u000f\u0005U\b\u000e1\u0001\u0002\u0014\u00061A\u0005^5nKN$B!a%\u0003D!9\u0011Q_5A\u0002\u0005M\u0015\u0001\u0002\u0013eSZ$B!a%\u0003J!9\u0011Q\u001f6A\u0002\u0005M\u0015\u0001\u0004\u0013eSZ$\u0003/\u001a:dK:$H\u0003\u0002B(\u0005+\u0002\u0002\"a\u000e\u0003R\u0005M\u00151S\u0005\u0005\u0005'\n9C\u0001\u0004UkBdWM\r\u0005\b\u0003k\\\u0007\u0019AAJ\u0003\u0011\tXo\u001c;\u0015\t\u0005M%1\f\u0005\b\u0003kd\u0007\u0019AAJ\u0003\ri\u0017N\u001c\u000b\u0005\u0003'\u0013\t\u0007C\u0004\u0002v6\u0004\r!a%\u0002\u00075\f\u0007\u0010\u0006\u0003\u0002\u0014\n\u001d\u0004bBA{]\u0002\u0007\u00111S\u0001\ne\u0016l\u0017-\u001b8eKJ$B!a%\u0003n!9\u0011Q_8A\u0002\u0005M\u0015\u0001\u0003\u0013qKJ\u001cWM\u001c;\u0015\t\u0005M%1\u000f\u0005\b\u0003k\u0004\b\u0019AAJ\u0003\r\u0001xn\u001e\u000b\u0005\u0003'\u0013I\bC\u0004\u0003|E\u0004\r!!\u001d\u0002\u00039\fA\"\u001e8bef|F%\\5okN,\"!a%\u0002\u0007\u0005\u00147/\u0001\u0004tS\u001etW/\\\u0001\u0005g&<g.A\u0005qe\u0016\u001c\u0017n]5p]\u0006)!o\\;oIR!\u00111\u0013BG\u0011\u001d\tyl\u001ea\u0001\u0003{\nqA]8v]\u0012,G-A\u0003tG\u0006dW-A\u0002vYB\fQ!\u00199qYf$B!a%\u0003\u001a\"9\u0011qX>A\u0002\u0005u\u0014\u0001C:fiN\u001b\u0017\r\\3\u0015\t\u0005M%q\u0014\u0005\b\u0005#c\b\u0019AA9)\u0019\t\u0019Ja)\u0003&\"9!\u0011S?A\u0002\u0005E\u0004b\u0002BT{\u0002\u0007!\u0011V\u0001\u0005[>$W\rE\u0002\u0003,Fq1A!,\u000f\u001d\r\ty\u0003A\u0001\r%>,h\u000eZ5oO6{G-\u001a\t\u0004\u0005g{Q\"A\u0001\u0003\u0019I{WO\u001c3j]\u001elu\u000eZ3\u0014\u0007=\u0011I\f\u0005\u0003\u00028\tm\u0016\u0002\u0002B_\u0003O\u00111\"\u00128v[\u0016\u0014\u0018\r^5p]R\u0011!\u0011\u0017\t\u0005\u0005\u0007\u0014)-D\u0001\u0010\u0013\u0011\u00119Ma/\u0003\u000bY\u000bG.^3\u0002\u0005U\u0003VC\u0001Ba\u0003\r)\u0006\u000bI\u0001\u0005\t>;f*A\u0003E\u001f^s\u0005%A\u0004D\u000b&c\u0015JT$\u0002\u0011\r+\u0015\nT%O\u000f\u0002\nQA\u0012'P\u001fJ\u000baA\u0012'P\u001fJ\u0003\u0013a\u0002%B\u0019\u001a{V\u000bU\u0001\t\u0011\u0006ceiX+QA\u0005I\u0001*\u0011'G?\u0012{uKT\u0001\u000b\u0011\u0006cei\u0018#P/:\u0003\u0013!\u0003%B\u0019\u001a{VIV#O\u0003)A\u0015\t\u0014$`\u000bZ+e\nI\u0001\f+:sUiQ#T'\u0006\u0013\u0016,\u0001\u0007V\u001d:+5)R*T\u0003JK\u0006%\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003pB!!\u0011\u001fB|\u001b\t\u0011\u0019P\u0003\u0003\u0003v\u0006\u0015\u0013\u0001\u00027b]\u001eLAA!?\u0003t\n1qJ\u00196fGR\f\u0011BY=uKZ\u000bG.^3\u0016\u0005\t}\b\u0003BA\u001c\u0007\u0003IAaa\u0001\u0002(\t!!)\u001f;f\u0003)\u0019\bn\u001c:u-\u0006dW/Z\u000b\u0003\u0007\u0013\u0001B!a\u000e\u0004\f%!1QBA\u0014\u0005\u0015\u0019\u0006n\u001c:u\u0003%\u0019\u0007.\u0019:WC2,X-\u0006\u0002\u0004\u0014A!\u0011qGB\u000b\u0013\u0011\u00199\"a\n\u0003\t\rC\u0017M]\u0001\tS:$h+\u00197vK\u0006IAn\u001c8h-\u0006dW/Z\u000b\u0003\u0007?\u0001B!a\u000e\u0004\"%!11EA\u0014\u0005\u0011auN\\4\u0002\u0015\u0019dw.\u0019;WC2,X-\u0006\u0002\u0004*A!\u0011qGB\u0016\u0013\u0011\u0019i#a\n\u0003\u000b\u0019cw.\u0019;\u0002\u0017\u0011|WO\u00197f-\u0006dW/Z\u000b\u0003\u0007g\u0001B!a\u000e\u00046%!1qGA\u0014\u0005\u0019!u.\u001e2mK\u0006YAo\u001c\"zi\u0016,\u00050Y2u\u00031!xn\u00155peR,\u00050Y2u\u0003)!x.\u00138u\u000bb\f7\r^\u0001\fi>duN\\4Fq\u0006\u001cG/A\u0003v]RLG\u000e\u0006\u0003\u0004F\r-\u0004\u0003CB$\u0007\u001b\n\u0019ja\u0017\u000f\t\u0005\u00156\u0011J\u0005\u0005\u0007\u0017\ni+A\u0003SC:<W-\u0003\u0003\u0004P\rE#a\u0002)beRL\u0017\r\u001c\u0006\u0005\u0007\u0017\u001a\u0019F\u0003\u0003\u0004V\r]\u0013!C5n[V$\u0018M\u00197f\u0015\u0011\u0019I&a\n\u0002\u0015\r|G\u000e\\3di&|g\u000e\u0005\u0004\u0004^\r\u0015\u00141\u0013\b\u0005\u0007?\u001a\t'\u0004\u0002\u0004T%!11MB*\u00031qU/\\3sS\u000e\u0014\u0016M\\4f\u0013\u0011\u00199g!\u001b\u0003\u0013\u0015C8\r\\;tSZ,'\u0002BB2\u0007'B\u0001b!\u001c\u0002\u0014\u0001\u0007\u00111S\u0001\u0004K:$GCBB.\u0007c\u001a\u0019\b\u0003\u0005\u0004n\u0005U\u0001\u0019AAJ\u0011!\u0019)(!\u0006A\u0002\u0005M\u0015\u0001B:uKB\f!\u0001^8\u0015\t\rm41\u0011\t\t\u0007\u000f\u001ai%a%\u0004~A11QLB@\u0003'KAa!!\u0004j\tI\u0011J\\2mkNLg/\u001a\u0005\t\u0007[\n9\u00021\u0001\u0002\u0014R11qQBG\u0007\u001f\u0003ba!\u0018\u0004\u0000\r%\u0005\u0003BAS\u0007\u0017KA!!\r\u0002.\"A1QNA\r\u0001\u0004\t\u0019\n\u0003\u0005\u0004v\u0005e\u0001\u0019AAJ\u0003!!xNQ5h\u0013:$XCABK!\u0011\tyca&\n\t\re\u00151\u0005\u0002\u0007\u0005&<\u0017J\u001c;\u0002\u001bQ|')[4J]R,\u00050Y2u+\t\u0019y\n\u0005\u0004\u00028\r\u00056QS\u0005\u0005\u0007G\u000b9C\u0001\u0004PaRLwN\\\u0001\ti>\u001cFO]5oOR\u00111\u0011\u0016\t\u0005\u0007W\u001bIL\u0004\u0003\u0004.\u000eU\u0006\u0003BBX\u0003Oi!a!-\u000b\t\rM\u00161F\u0001\u0007yI|w\u000e\u001e \n\t\r]\u0016qE\u0001\u0007!J,G-\u001a4\n\t\rm6Q\u0018\u0002\u0007'R\u0014\u0018N\\4\u000b\t\r]\u0016qE\u0001\bI\u0016\u001c\u0017.\\1m)\u0019\t\u0019ja1\u0004H\"91QY\u0012A\u0002\rM\u0012!\u00013\t\u000f\u0005}6\u00051\u0001\u0002~Q!\u00111SBf\u0011\u001d\u0019)\r\na\u0001\u0007g!b!a%\u0004P\u000eM\u0007bBBiK\u0001\u00071\u0011F\u0001\u0002M\"9\u0011qX\u0013A\u0002\u0005uD\u0003BAJ\u0007/Dqa!5'\u0001\u0004\u0019I\u0003\u0006\u0004\u0002\u0014\u000em7q\u001c\u0005\b\u0007;<\u0003\u0019AB\u0010\u0003\u0005a\u0007bBA`O\u0001\u0007\u0011Q\u0010\u000b\u0005\u0003'\u001b\u0019\u000fC\u0004\u0004^\"\u0002\raa\b\u0015\r\u0005M5q]Bv\u0011\u001d\u0019I/\u000ba\u0001\u0003s\u000b!A\u00193\t\u000f\u0005}\u0016\u00061\u0001\u0002~\u00051!-\u001b8bef$b!a%\u0004r\u000eM\bbBBcU\u0001\u000711\u0007\u0005\b\u0003\u007fS\u0003\u0019AA?)\u0011\t\u0019ja>\t\u000f\r\u00157\u00061\u0001\u00044\u0005)Q\r_1diR!\u00111SB\u007f\u0011\u001d\u0019y\u0010\fa\u0001\u0003s\u000bAA]3qeR!\u00111\u0013C\u0002\u0011\u001d\u0019)-\fa\u0001\u0007g!B!a%\u0005\b!9A\u0011\u0002\u0018A\u0002\rU\u0015A\u00012j)\u0011\t\u0019\n\"\u0004\t\u000f\ruw\u00061\u0001\u0004 Q!\u00111\u0013C\t\u0011\u001d!\u0019\u0002\ra\u0001\u0007S\u000b\u0011a\u001d\u000b\u0005\u0003'#9\u0002C\u0004\u0005\u001aE\u0002\r\u0001b\u0007\u0002\u0005\r\u001c\bCBA\u001c\u0003\u001f\u001b\u0019\"A\u0004wC2,Xm\u00144\u0015\t\u0005ME\u0011\u0005\u0005\b\u0007\u000b\u0014\u0004\u0019AB\u001a)\u0011\t\u0019\n\"\n\t\u000f\u0011\u001d2\u00071\u0001\u0004 \u0005\t\u0001\u0010\u0006\u0003\u0002\u0014\u0012-\u0002b\u0002C\u0017i\u0001\u0007\u0011\u0011O\u0001\u0002SR1\u00111\u0013C\u0019\tgAq\u0001\"\f6\u0001\u0004\t\t\bC\u0004\u0002@V\u0002\r!! \u0015\t\u0005MEq\u0007\u0005\b\u0007;4\u0004\u0019AB\u0010)\u0019\t\u0019\nb\u000f\u0005>!91Q\\\u001cA\u0002\r}\u0001bBA`o\u0001\u0007\u0011Q\u0010\u000b\u0007\u0003'#\t\u0005\"\u0012\t\u000f\u0011\r\u0003\b1\u0001\u0004 \u0005YQO\\:dC2,GMV1m\u0011\u001d\u0011\t\n\u000fa\u0001\u0003c\"\u0002\"a%\u0005J\u0011-CQ\n\u0005\b\t\u0007J\u0004\u0019AB\u0010\u0011\u001d\u0011\t*\u000fa\u0001\u0003cBq!a0:\u0001\u0004\ti\b\u0006\u0003\u0002\u0014\u0012E\u0003bBBcu\u0001\u000711\u0007\u000b\u0007\u0003'#)\u0006b\u0016\t\u000f\r\u00157\b1\u0001\u00044!9\u0011qX\u001eA\u0002\u0005uD\u0003BAJ\t7Bq\u0001b\n=\u0001\u0004!Y\u0002\u0006\u0004\u0002\u0014\u0012}C\u0011\r\u0005\b\tOi\u0004\u0019\u0001C\u000e\u0011\u001d\ty,\u0010a\u0001\u0003{\"B!a%\u0005f!9Aq\u0005 A\u0002\r%FCBAJ\tS\"Y\u0007C\u0004\u0005(}\u0002\ra!+\t\u000f\u0005}v\b1\u0001\u0002~Q!\u00111\u0013C8\u0011\u001d!9\u0003\u0011a\u0001\u0007+#b!a%\u0005t\u0011U\u0004b\u0002C\u0014\u0003\u0002\u00071Q\u0013\u0005\b\u0003\u007f\u000b\u0005\u0019AA?)\u0019\t\u0019\n\"\u001f\u0005|!9A1\t\"A\u0002\rU\u0005b\u0002BI\u0005\u0002\u0007\u0011\u0011\u000f\u000b\t\u0003'#y\b\"!\u0005\u0004\"9A1I\"A\u0002\rU\u0005b\u0002BI\u0007\u0002\u0007\u0011\u0011\u000f\u0005\b\u0003\u007f\u001b\u0005\u0019AA?)\u0011\t\u0019\nb\"\t\u000f\r%H\t1\u0001\u0002:\u0006q\u0011N\u001c;3E&<G)Z2j[\u0006dG\u0003BAJ\t\u001bCq\u0001\"\fF\u0001\u0004\t\t(A\bm_:<'GY5h\t\u0016\u001c\u0017.\\1m)\u0011\t\u0019\nb%\t\u000f\rug\t1\u0001\u0004 \u0005\tBm\\;cY\u0016\u0014$-[4EK\u000eLW.\u00197\u0015\t\u0005ME\u0011\u0014\u0005\b\u0007\u000b<\u0005\u0019AB\u001a\u0003eQ\u0017M^1CS\u001e$UmY5nC2\u0014$-[4EK\u000eLW.\u00197\u0015\t\u0005MEq\u0014\u0005\b\tOA\u0005\u0019AA]\u0001")
public final class BigDecimal
extends ScalaNumber
implements ScalaNumericConversions,
Ordered<BigDecimal> {
    private final java.math.BigDecimal bigDecimal;
    private final MathContext mc;
    private int computedHashCode;

    public static BigDecimal javaBigDecimal2bigDecimal(java.math.BigDecimal x) {
        return BigDecimal$.MODULE$.javaBigDecimal2bigDecimal(x);
    }

    public static BigDecimal double2bigDecimal(double d) {
        return BigDecimal$.MODULE$.decimal(d);
    }

    public static BigDecimal long2bigDecimal(long l) {
        return BigDecimal$.MODULE$.apply(l);
    }

    public static BigDecimal int2bigDecimal(int i) {
        return BigDecimal$.MODULE$.apply(i);
    }

    public static BigDecimal valueOf(long x) {
        return BigDecimal$.MODULE$.apply(x);
    }

    public static BigDecimal valueOf(double d) {
        return BigDecimal$.MODULE$.valueOf(d);
    }

    public static BigDecimal exact(char[] cs) {
        return BigDecimal$.MODULE$.exact(cs);
    }

    public static BigDecimal exact(String s2) {
        return BigDecimal$.MODULE$.exact(s2);
    }

    public static BigDecimal exact(long l) {
        return BigDecimal$.MODULE$.apply(l);
    }

    public static BigDecimal exact(BigInt bi) {
        return BigDecimal$.MODULE$.exact(bi);
    }

    public static BigDecimal exact(double d) {
        return BigDecimal$.MODULE$.exact(d);
    }

    public static BigDecimal exact(java.math.BigDecimal repr) {
        return BigDecimal$.MODULE$.exact(repr);
    }

    public static BigDecimal binary(double d) {
        return BigDecimal$.MODULE$.binary(d);
    }

    public static BigDecimal binary(double d, MathContext mc) {
        return BigDecimal$.MODULE$.binary(d, mc);
    }

    public static BigDecimal decimal(java.math.BigDecimal bd, MathContext mc) {
        return BigDecimal$.MODULE$.decimal(bd, mc);
    }

    public static BigDecimal decimal(long l) {
        return BigDecimal$.MODULE$.apply(l);
    }

    public static BigDecimal decimal(long l, MathContext mc) {
        return BigDecimal$.MODULE$.apply(l, mc);
    }

    public static BigDecimal decimal(float f) {
        return BigDecimal$.MODULE$.decimal(f);
    }

    public static BigDecimal decimal(float f, MathContext mc) {
        return BigDecimal$.MODULE$.decimal(f, mc);
    }

    public static BigDecimal decimal(double d) {
        return BigDecimal$.MODULE$.decimal(d);
    }

    public static BigDecimal decimal(double d, MathContext mc) {
        return BigDecimal$.MODULE$.decimal(d, mc);
    }

    public static MathContext defaultMathContext() {
        return BigDecimal$.MODULE$.defaultMathContext();
    }

    @Override
    public boolean $less(Object that) {
        return Ordered.$less$(this, that);
    }

    @Override
    public boolean $greater(Object that) {
        return Ordered.$greater$(this, that);
    }

    @Override
    public boolean $less$eq(Object that) {
        return Ordered.$less$eq$(this, that);
    }

    @Override
    public boolean $greater$eq(Object that) {
        return Ordered.$greater$eq$(this, that);
    }

    @Override
    public int compareTo(Object that) {
        return Ordered.compareTo$(this, that);
    }

    @Override
    public char toChar() {
        return ScalaNumericAnyConversions.toChar$(this);
    }

    @Override
    public byte toByte() {
        return ScalaNumericAnyConversions.toByte$(this);
    }

    @Override
    public short toShort() {
        return ScalaNumericAnyConversions.toShort$(this);
    }

    @Override
    public int toInt() {
        return ScalaNumericAnyConversions.toInt$(this);
    }

    @Override
    public long toLong() {
        return ScalaNumericAnyConversions.toLong$(this);
    }

    @Override
    public float toFloat() {
        return ScalaNumericAnyConversions.toFloat$(this);
    }

    @Override
    public double toDouble() {
        return ScalaNumericAnyConversions.toDouble$(this);
    }

    @Override
    public int unifiedPrimitiveHashcode() {
        return ScalaNumericAnyConversions.unifiedPrimitiveHashcode$(this);
    }

    @Override
    public boolean unifiedPrimitiveEquals(Object x) {
        return ScalaNumericAnyConversions.unifiedPrimitiveEquals$(this, x);
    }

    public java.math.BigDecimal bigDecimal() {
        return this.bigDecimal;
    }

    public MathContext mc() {
        return this.mc;
    }

    private final int computedHashCode() {
        return this.computedHashCode;
    }

    private final void computedHashCode_$eq(int x$1) {
        this.computedHashCode = x$1;
    }

    private final void computeHashCode() {
        int n;
        if (this.isWhole() && this.precision() - this.scale() < 4934) {
            n = this.toBigInt().hashCode();
        } else if (this.isDecimalDouble()) {
            n = Statics.doubleHash(this.doubleValue());
        } else {
            java.math.BigDecimal temp = this.bigDecimal().stripTrailingZeros();
            n = MurmurHash3$.MODULE$.mixLast(temp.scaleByPowerOfTen(temp.scale()).toBigInteger().hashCode(), temp.scale());
        }
        this.computedHashCode_$eq(n);
    }

    public int hashCode() {
        if (this.computedHashCode() == 1565550863) {
            this.computeHashCode();
        }
        return this.computedHashCode();
    }

    public boolean equals(Object that) {
        if (that instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal)that;
            return this.equals(bigDecimal);
        }
        if (that instanceof BigInt) {
            BigInt bigInt = (BigInt)that;
            if ((double)bigInt.bitLength() > (double)(this.precision() - this.scale() - 2) * 3.3219280948873626) {
                BigInt bigInt2;
                Option<BigInt> option = this.toBigIntExact();
                if (option == null) {
                    throw null;
                }
                Option<BigInt> exists_this = option;
                boolean bl = !exists_this.isEmpty() && bigInt.equals(bigInt2 = exists_this.get());
                Object var10_4 = null;
                if (bl) {
                    return true;
                }
            }
            return false;
        }
        if (that instanceof Double) {
            double d = BoxesRunTime.unboxToDouble(that);
            if (!Double.isInfinite(d)) {
                double d2 = this.doubleValue();
                if (!Double.isInfinite(d2) && d2 == d && this.equals(BigDecimal$.MODULE$.decimal(d2))) {
                    return true;
                }
            }
            return false;
        }
        if (that instanceof Float) {
            float f = BoxesRunTime.unboxToFloat(that);
            if (!Float.isInfinite(f)) {
                float f2 = this.floatValue();
                if (!Float.isInfinite(f2) && f2 == f && this.equals(BigDecimal$.MODULE$.decimal((double)f2))) {
                    return true;
                }
            }
            return false;
        }
        return this.isValidLong() && ScalaNumericAnyConversions.unifiedPrimitiveEquals$(this, that);
    }

    @Override
    public boolean isValidByte() {
        try {
            this.toByteExact();
            return true;
        }
        catch (ArithmeticException arithmeticException) {
            return false;
        }
    }

    @Override
    public boolean isValidShort() {
        try {
            this.toShortExact();
            return true;
        }
        catch (ArithmeticException arithmeticException) {
            return false;
        }
    }

    @Override
    public boolean isValidChar() {
        return this.isValidInt() && this.toIntExact() >= 0 && this.toIntExact() <= 65535;
    }

    @Override
    public boolean isValidInt() {
        try {
            this.toIntExact();
            return true;
        }
        catch (ArithmeticException arithmeticException) {
            return false;
        }
    }

    public boolean isValidLong() {
        try {
            this.toLongExact();
            return true;
        }
        catch (ArithmeticException arithmeticException) {
            return false;
        }
    }

    public boolean isDecimalDouble() {
        double d = this.doubleValue();
        return !Double.isInfinite(d) && this.equals(BigDecimal$.MODULE$.decimal(d));
    }

    public boolean isDecimalFloat() {
        float f = this.floatValue();
        return !Float.isInfinite(f) && this.equals(BigDecimal$.MODULE$.decimal(f));
    }

    public boolean isBinaryDouble() {
        double d = this.doubleValue();
        return !Double.isInfinite(d) && this.equals(BigDecimal$.MODULE$.binary(d, this.mc()));
    }

    public boolean isBinaryFloat() {
        float f = this.floatValue();
        return !Float.isInfinite(f) && this.equals(BigDecimal$.MODULE$.binary(f, this.mc()));
    }

    public boolean isExactDouble() {
        double d = this.doubleValue();
        return !Double.isInfinite(d) && this.equals(BigDecimal$.MODULE$.exact(d));
    }

    public boolean isExactFloat() {
        float f = this.floatValue();
        return !Float.isInfinite(f) && this.equals(BigDecimal$.MODULE$.exact(f));
    }

    private boolean noArithmeticException(Function0<BoxedUnit> body2) {
        try {
            body2.apply$mcV$sp();
            return true;
        }
        catch (ArithmeticException arithmeticException) {
            return false;
        }
    }

    @Override
    public boolean isWhole() {
        return this.scale() <= 0 || this.bigDecimal().stripTrailingZeros().scale() <= 0;
    }

    @Override
    public java.math.BigDecimal underlying() {
        return this.bigDecimal();
    }

    public boolean equals(BigDecimal that) {
        return this.compare(that) == 0;
    }

    @Override
    public int compare(BigDecimal that) {
        return this.bigDecimal().compareTo(that.bigDecimal());
    }

    public BigDecimal $plus(BigDecimal that) {
        return new BigDecimal(this.bigDecimal().add(that.bigDecimal(), this.mc()), this.mc());
    }

    public BigDecimal $minus(BigDecimal that) {
        return new BigDecimal(this.bigDecimal().subtract(that.bigDecimal(), this.mc()), this.mc());
    }

    public BigDecimal $times(BigDecimal that) {
        return new BigDecimal(this.bigDecimal().multiply(that.bigDecimal(), this.mc()), this.mc());
    }

    public BigDecimal $div(BigDecimal that) {
        return new BigDecimal(this.bigDecimal().divide(that.bigDecimal(), this.mc()), this.mc());
    }

    public Tuple2<BigDecimal, BigDecimal> $div$percent(BigDecimal that) {
        java.math.BigDecimal[] qr = this.bigDecimal().divideAndRemainder(that.bigDecimal(), this.mc());
        return new Tuple2<BigDecimal, BigDecimal>(new BigDecimal(qr[0], this.mc()), new BigDecimal(qr[1], this.mc()));
    }

    public BigDecimal quot(BigDecimal that) {
        return new BigDecimal(this.bigDecimal().divideToIntegralValue(that.bigDecimal(), this.mc()), this.mc());
    }

    public BigDecimal min(BigDecimal that) {
        int n = this.compare(that);
        switch (n) {
            default: 
        }
        if (n <= 0) {
            return this;
        }
        return that;
    }

    public BigDecimal max(BigDecimal that) {
        int n = this.compare(that);
        switch (n) {
            default: 
        }
        if (n >= 0) {
            return this;
        }
        return that;
    }

    public BigDecimal remainder(BigDecimal that) {
        return new BigDecimal(this.bigDecimal().remainder(that.bigDecimal(), this.mc()), this.mc());
    }

    public BigDecimal $percent(BigDecimal that) {
        return this.remainder(that);
    }

    public BigDecimal pow(int n) {
        return new BigDecimal(this.bigDecimal().pow(n, this.mc()), this.mc());
    }

    public BigDecimal unary_$minus() {
        return new BigDecimal(this.bigDecimal().negate(this.mc()), this.mc());
    }

    public BigDecimal abs() {
        if (this.signum() < 0) {
            return this.unary_$minus();
        }
        return this;
    }

    public int signum() {
        return this.bigDecimal().signum();
    }

    public BigDecimal sign() {
        return BigDecimal$.MODULE$.apply(this.signum());
    }

    public int precision() {
        return this.bigDecimal().precision();
    }

    public BigDecimal round(MathContext mc) {
        java.math.BigDecimal r = this.bigDecimal().round(mc);
        if (r == this.bigDecimal()) {
            return this;
        }
        return new BigDecimal(r, this.mc());
    }

    public BigDecimal rounded() {
        java.math.BigDecimal r = this.bigDecimal().round(this.mc());
        if (r == this.bigDecimal()) {
            return this;
        }
        return new BigDecimal(r, this.mc());
    }

    public int scale() {
        return this.bigDecimal().scale();
    }

    public BigDecimal ulp() {
        return new BigDecimal(this.bigDecimal().ulp(), this.mc());
    }

    public BigDecimal apply(MathContext mc) {
        return new BigDecimal(this.bigDecimal().round(mc), mc);
    }

    public BigDecimal setScale(int scale) {
        if (this.scale() == scale) {
            return this;
        }
        return new BigDecimal(this.bigDecimal().setScale(scale), this.mc());
    }

    public BigDecimal setScale(int scale, Enumeration.Value mode) {
        if (this.scale() == scale) {
            return this;
        }
        return new BigDecimal(this.bigDecimal().setScale(scale, RoundingMode.valueOf(mode.id())), this.mc());
    }

    @Override
    public byte byteValue() {
        return (byte)this.intValue();
    }

    @Override
    public short shortValue() {
        return (short)this.intValue();
    }

    public char charValue() {
        return (char)this.intValue();
    }

    @Override
    public int intValue() {
        return this.bigDecimal().intValue();
    }

    @Override
    public long longValue() {
        return this.bigDecimal().longValue();
    }

    @Override
    public float floatValue() {
        return this.bigDecimal().floatValue();
    }

    @Override
    public double doubleValue() {
        return this.bigDecimal().doubleValue();
    }

    public byte toByteExact() {
        return this.bigDecimal().byteValueExact();
    }

    public short toShortExact() {
        return this.bigDecimal().shortValueExact();
    }

    public int toIntExact() {
        return this.bigDecimal().intValueExact();
    }

    public long toLongExact() {
        return this.bigDecimal().longValueExact();
    }

    public Function1<BigDecimal, NumericRange.Exclusive<BigDecimal>> until(BigDecimal end) {
        return (Function1<BigDecimal, NumericRange.Exclusive> & Serializable)x$2 -> this.until(end, (BigDecimal)x$2);
    }

    public NumericRange.Exclusive<BigDecimal> until(BigDecimal end, BigDecimal step) {
        return Range$BigDecimal$.MODULE$.apply(this, end, step);
    }

    public Function1<BigDecimal, NumericRange.Inclusive<BigDecimal>> to(BigDecimal end) {
        return (Function1<BigDecimal, NumericRange.Inclusive> & Serializable)x$3 -> this.to(end, (BigDecimal)x$3);
    }

    public NumericRange.Inclusive<BigDecimal> to(BigDecimal end, BigDecimal step) {
        return Range$BigDecimal$.MODULE$.inclusive(this, end, step);
    }

    public BigInt toBigInt() {
        return new BigInt(this.bigDecimal().toBigInteger());
    }

    public Option<BigInt> toBigIntExact() {
        if (this.isWhole()) {
            try {
                return new Some<BigInt>(new BigInt(this.bigDecimal().toBigIntegerExact()));
            }
            catch (ArithmeticException arithmeticException) {
                return None$.MODULE$;
            }
        }
        return None$.MODULE$;
    }

    public String toString() {
        return this.bigDecimal().toString();
    }

    public static final /* synthetic */ void $anonfun$isValidByte$1(BigDecimal $this) {
        $this.toByteExact();
    }

    public static final /* synthetic */ void $anonfun$isValidShort$1(BigDecimal $this) {
        $this.toShortExact();
    }

    public static final /* synthetic */ void $anonfun$isValidInt$1(BigDecimal $this) {
        $this.toIntExact();
    }

    public static final /* synthetic */ void $anonfun$isValidLong$1(BigDecimal $this) {
        $this.toLongExact();
    }

    public BigDecimal(java.math.BigDecimal bigDecimal, MathContext mc) {
        this.bigDecimal = bigDecimal;
        this.mc = mc;
        if (bigDecimal == null) {
            throw new IllegalArgumentException("null value for BigDecimal");
        }
        if (mc == null) {
            throw new IllegalArgumentException("null MathContext for BigDecimal");
        }
        this.computedHashCode = 1565550863;
    }

    public BigDecimal(java.math.BigDecimal bigDecimal) {
        this(bigDecimal, BigDecimal$.MODULE$.defaultMathContext());
    }

    public static final /* synthetic */ Object $anonfun$equals$1$adapted(BigInt x3$1, BigInt x$1) {
        return BoxesRunTime.boxToBoolean(x3$1.equals(x$1));
    }
}

