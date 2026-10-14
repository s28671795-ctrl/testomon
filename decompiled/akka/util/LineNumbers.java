/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.LineNumbers$;
import java.io.DataInputStream;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\u0011\rs\u0001CA\u0005\u0003\u0017A\t!!\u0006\u0007\u0011\u0005e\u00111\u0002E\u0001\u00037Aq!!\u000b\u0002\t\u0003\tYCB\u0005\u0002.\u0005\u0001\n1%\t\u00020\u001d9!\u0011M\u0001\t\u0002\u0006mcaBA\u001a\u0003!\u0005\u0015Q\u0007\u0005\b\u0003S)A\u0011AA-\u0011%\ti&BA\u0001\n\u0003\ny\u0006C\u0005\u0002r\u0015\t\t\u0011\"\u0001\u0002t!I\u00111P\u0003\u0002\u0002\u0013\u0005\u0011Q\u0010\u0005\n\u0003\u0013+\u0011\u0011!C!\u0003\u0017C\u0011\"!'\u0006\u0003\u0003%\t!a'\t\u0013\u0005\u0015V!!A\u0005B\u0005\u001d\u0006\"CAU\u000b\u0005\u0005I\u0011IAV\u0011%\ti+BA\u0001\n\u0013\tyK\u0002\u0004\u0003@\u0005\u0011%\u0011\t\u0005\u000b\u0005\u0007z!Q3A\u0005\u0002\u0005u\u0006B\u0003B#\u001f\tE\t\u0015!\u0003\u0002@\"9\u0011\u0011F\b\u0005\u0002\t\u001d\u0003\"CAl\u001f\u0005\u0005I\u0011\u0001B'\u0011%\tinDI\u0001\n\u0003\ty\u000eC\u0005\u0002^=\t\t\u0011\"\u0011\u0002`!I\u0011\u0011O\b\u0002\u0002\u0013\u0005\u00111\u000f\u0005\n\u0003wz\u0011\u0011!C\u0001\u0005#B\u0011\"!#\u0010\u0003\u0003%\t%a#\t\u0013\u0005eu\"!A\u0005\u0002\tU\u0003\"CA\u007f\u001f\u0005\u0005I\u0011\tB-\u0011%\t)kDA\u0001\n\u0003\n9\u000bC\u0005\u0002*>\t\t\u0011\"\u0011\u0002,\"I!1A\b\u0002\u0002\u0013\u0005#QL\u0004\n\u0005G\n\u0011\u0011!E\u0001\u0005K2\u0011Ba\u0010\u0002\u0003\u0003E\tAa\u001a\t\u000f\u0005%r\u0004\"\u0001\u0003\u0000!I\u0011\u0011V\u0010\u0002\u0002\u0013\u0015\u00131\u0016\u0005\n\u0005\u0003{\u0012\u0011!CA\u0005\u0007C\u0011Ba\" \u0003\u0003%\tI!#\t\u0013\u00055v$!A\u0005\n\u0005=fABA\\\u0003\t\u000bI\f\u0003\u0006\u0002<\u0016\u0012)\u001a!C\u0001\u0003{C!\"!4&\u0005#\u0005\u000b\u0011BA`\u0011\u001d\tI#\nC\u0001\u0003\u001fDq!!+&\t\u0003\n)\u000eC\u0005\u0002X\u0016\n\t\u0011\"\u0001\u0002Z\"I\u0011Q\\\u0013\u0012\u0002\u0013\u0005\u0011q\u001c\u0005\n\u0003;*\u0013\u0011!C!\u0003?B\u0011\"!\u001d&\u0003\u0003%\t!a\u001d\t\u0013\u0005mT%!A\u0005\u0002\u0005U\b\"CAEK\u0005\u0005I\u0011IAF\u0011%\tI*JA\u0001\n\u0003\tI\u0010C\u0005\u0002~\u0016\n\t\u0011\"\u0011\u0002\u0000\"I\u0011QU\u0013\u0002\u0002\u0013\u0005\u0013q\u0015\u0005\n\u0005\u0007)\u0013\u0011!C!\u0005\u000b9\u0011B!&\u0002\u0003\u0003E\tAa&\u0007\u0013\u0005]\u0016!!A\t\u0002\te\u0005bBA\u0015k\u0011\u0005!Q\u0014\u0005\n\u0003S+\u0014\u0011!C#\u0003WC\u0011B!!6\u0003\u0003%\tIa(\t\u0013\t\u001dU'!A\u0005\u0002\n\r\u0006\"CAWk\u0005\u0005I\u0011BAX\r\u0019\u0011I!\u0001\"\u0003\f!Q\u00111X\u001e\u0003\u0016\u0004%\t!!0\t\u0015\u000557H!E!\u0002\u0013\ty\f\u0003\u0006\u0003\u000em\u0012)\u001a!C\u0001\u0003gB!Ba\u0004<\u0005#\u0005\u000b\u0011BA;\u0011)\u0011\tb\u000fBK\u0002\u0013\u0005\u00111\u000f\u0005\u000b\u0005'Y$\u0011#Q\u0001\n\u0005U\u0004bBA\u0015w\u0011\u0005!Q\u0003\u0005\b\u0003S[D\u0011IAV\u0011%\t9nOA\u0001\n\u0003\u0011y\u0002C\u0005\u0002^n\n\n\u0011\"\u0001\u0002`\"I!qE\u001e\u0012\u0002\u0013\u0005!\u0011\u0006\u0005\n\u0005[Y\u0014\u0013!C\u0001\u0005SA\u0011\"!\u0018<\u0003\u0003%\t%a\u0018\t\u0013\u0005E4(!A\u0005\u0002\u0005M\u0004\"CA>w\u0005\u0005I\u0011\u0001B\u0018\u0011%\tIiOA\u0001\n\u0003\nY\tC\u0005\u0002\u001an\n\t\u0011\"\u0001\u00034!I\u0011Q`\u001e\u0002\u0002\u0013\u0005#q\u0007\u0005\n\u0003K[\u0014\u0011!C!\u0003OC\u0011Ba\u0001<\u0003\u0003%\tEa\u000f\b\u0013\t\u001d\u0016!!A\t\u0002\t%f!\u0003B\u0005\u0003\u0005\u0005\t\u0012\u0001BV\u0011\u001d\tI#\u0015C\u0001\u0005gC\u0011\"!+R\u0003\u0003%)%a+\t\u0013\t\u0005\u0015+!A\u0005\u0002\nU\u0006\"\u0003BD#\u0006\u0005I\u0011\u0011B_\u0011%\ti+UA\u0001\n\u0013\ty\u000bC\u0004\u0003\u0002\u0006!\tA!3\t\u000f\t=\u0017\u0001\"\u0001\u0003R\"9!Q[\u0001\u0005\u0002\t]\u0007\"\u0003Bn\u0003\t\u0007IQ\u0002Bo\u0011!\u0011\u0019/\u0001Q\u0001\u000e\t}gA\u0002Bs\u0003\u0011\u00119\u000f\u0003\u0006\u0003jr\u0013\t\u0011)A\u0005\u0003kBq!!\u000b]\t\u0003\u0011Y\u000fC\u0005\u0003rr\u0003\r\u0011\"\u0003\u0003t\"I1\u0011\u0001/A\u0002\u0013%11\u0001\u0005\t\u0007\u001ba\u0006\u0015)\u0003\u0003v\"I1q\u0002/A\u0002\u0013%1\u0011\u0003\u0005\n\u0007+a\u0006\u0019!C\u0005\u0007/A\u0001ba\u0007]A\u0003&11\u0003\u0005\n\u0007;a\u0006\u0019!C\u0005\u0007?A\u0011ba\t]\u0001\u0004%Ia!\n\t\u0011\r%B\f)Q\u0005\u0007CAqaa\u000b]\t\u0003\u0019i\u0003C\u0004\u00044q#\ta!\u000e\t\u0013\reB\f1A\u0005\n\u0005M\u0004\"CB\u001e9\u0002\u0007I\u0011BB\u001f\u0011!\u0019\t\u0005\u0018Q!\n\u0005U\u0004bBB\"9\u0012\u00051Q\t\u0005\b\u0005\u0003cF\u0011AB$\u0011\u001d\u0011\t\t\u0018C\u0001\u0007\u001bBqaa\u0015]\t\u0003\u0019)\u0006C\u0004\u0004Xq#\ta!\u0017\t\u000f\ruC\f\"\u0003\u0004`!91Q\r/\u0005\u0002\r\u001d\u0004bBB:\u0003\u0011%1Q\u000f\u0005\b\u0007s\nA\u0011BB>\u0011\u001d\u0019Y)\u0001C\u0005\u0007\u001bCqa!/\u0002\t\u0013\u0019Y\fC\u0004\u0004L\u0006!Ia!4\t\u000f\rE\u0017\u0001\"\u0003\u0004T\"91q[\u0001\u0005\n\re\u0007bBBo\u0003\u0011%1q\u001c\u0005\b\u0007O\fA\u0011BBu\u0011\u001d\u0019\t0\u0001C\u0005\u0007gDqaa?\u0002\t\u0013\u0019i\u0010C\u0004\u0005\u0006\u0005!I\u0001b\u0002\t\u000f\u0011-\u0011\u0001\"\u0003\u0005\u000e!9A1D\u0001\u0005\n\u0011u\u0001b\u0002C\u0018\u0003\u0011%A\u0011\u0007\u0005\b\ts\tA\u0011\u0002C\u001e\u0003-a\u0015N\\3Ok6\u0014WM]:\u000b\t\u00055\u0011qB\u0001\u0005kRLGN\u0003\u0002\u0002\u0012\u0005!\u0011m[6b\u0007\u0001\u00012!a\u0006\u0002\u001b\t\tYAA\u0006MS:,g*^7cKJ\u001c8cA\u0001\u0002\u001eA!\u0011qDA\u0013\u001b\t\t\tC\u0003\u0002\u0002$\u0005)1oY1mC&!\u0011qEA\u0011\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\"!!\u0006\u0003\rI+7/\u001e7u'\r\u0019\u0011QD\u0015\u0006\u0007\u0015)3h\u0004\u0002\r\u001d>\u001cv.\u001e:dK&sgm\\\n\n\u000b\u0005u\u0011qGA\u001e\u0003\u0003\u00022!!\u000f\u0004\u001b\u0005\t\u0001\u0003BA\u0010\u0003{IA!a\u0010\u0002\"\t9\u0001K]8ek\u000e$\b\u0003BA\"\u0003'rA!!\u0012\u0002P9!\u0011qIA'\u001b\t\tIE\u0003\u0003\u0002L\u0005M\u0011A\u0002\u001fs_>$h(\u0003\u0002\u0002$%!\u0011\u0011KA\u0011\u0003\u001d\u0001\u0018mY6bO\u0016LA!!\u0016\u0002X\ta1+\u001a:jC2L'0\u00192mK*!\u0011\u0011KA\u0011)\t\tY\u0006E\u0002\u0002:\u0015\tQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA1!\u0011\t\u0019'!\u001c\u000e\u0005\u0005\u0015$\u0002BA4\u0003S\nA\u0001\\1oO*\u0011\u00111N\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002p\u0005\u0015$AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002vA!\u0011qDA<\u0013\u0011\tI(!\t\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005}\u0014Q\u0011\t\u0005\u0003?\t\t)\u0003\u0003\u0002\u0004\u0006\u0005\"aA!os\"I\u0011qQ\u0005\u0002\u0002\u0003\u0007\u0011QO\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u00055\u0005CBAH\u0003+\u000by(\u0004\u0002\u0002\u0012*!\u00111SA\u0011\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003/\u000b\tJ\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAO\u0003G\u0003B!a\b\u0002 &!\u0011\u0011UA\u0011\u0005\u001d\u0011un\u001c7fC:D\u0011\"a\"\f\u0003\u0003\u0005\r!a \u0002\u0011!\f7\u000f[\"pI\u0016$\"!!\u001e\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005E\u0006\u0003BA2\u0003gKA!!.\u0002f\t1qJ\u00196fGR\u0014!bU8ve\u000e,g)\u001b7f'%)\u0013QDA\u001c\u0003w\t\t%\u0001\u0005gS2,g.Y7f+\t\ty\f\u0005\u0003\u0002B\u0006%g\u0002BAb\u0003\u000b\u0004B!a\u0012\u0002\"%!\u0011qYA\u0011\u0003\u0019\u0001&/\u001a3fM&!\u0011qNAf\u0015\u0011\t9-!\t\u0002\u0013\u0019LG.\u001a8b[\u0016\u0004C\u0003BAi\u0003'\u00042!!\u000f&\u0011\u001d\tY\f\u000ba\u0001\u0003\u007f#\"!a0\u0002\t\r|\u0007/\u001f\u000b\u0005\u0003#\fY\u000eC\u0005\u0002<*\u0002\n\u00111\u0001\u0002@\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAAqU\u0011\ty,a9,\u0005\u0005\u0015\b\u0003BAt\u0003cl!!!;\u000b\t\u0005-\u0018Q^\u0001\nk:\u001c\u0007.Z2lK\u0012TA!a<\u0002\"\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005M\u0018\u0011\u001e\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G\u0003BA@\u0003oD\u0011\"a\"/\u0003\u0003\u0005\r!!\u001e\u0015\t\u0005u\u00151 \u0005\n\u0003\u000f\u0003\u0014\u0011!a\u0001\u0003\u007f\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011\u0011\rB\u0001\u0011%\t9)MA\u0001\u0002\u0004\t)(\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003;\u00139\u0001C\u0005\u0002\bN\n\t\u00111\u0001\u0002\u0000\ty1k\\;sG\u00164\u0015\u000e\\3MS:,7oE\u0005<\u0003;\t9$a\u000f\u0002B\u0005!aM]8n\u0003\u00151'o\\7!\u0003\t!x.A\u0002u_\u0002\"\u0002Ba\u0006\u0003\u001a\tm!Q\u0004\t\u0004\u0003sY\u0004bBA^\u0005\u0002\u0007\u0011q\u0018\u0005\b\u0005\u001b\u0011\u0005\u0019AA;\u0011\u001d\u0011\tB\u0011a\u0001\u0003k\"\u0002Ba\u0006\u0003\"\t\r\"Q\u0005\u0005\n\u0003w#\u0005\u0013!a\u0001\u0003\u007fC\u0011B!\u0004E!\u0003\u0005\r!!\u001e\t\u0013\tEA\t%AA\u0002\u0005U\u0014AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0005WQC!!\u001e\u0002d\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aD\u0003BA@\u0005cA\u0011\"a\"K\u0003\u0003\u0005\r!!\u001e\u0015\t\u0005u%Q\u0007\u0005\n\u0003\u000fc\u0015\u0011!a\u0001\u0003\u007f\"B!!\u0019\u0003:!I\u0011qQ'\u0002\u0002\u0003\u0007\u0011Q\u000f\u000b\u0005\u0003;\u0013i\u0004C\u0005\u0002\b>\u000b\t\u00111\u0001\u0002\u0000\t\u0019RK\\6o_^t7k\\;sG\u00164uN]7biNIq\"!\b\u00028\u0005m\u0012\u0011I\u0001\fKb\u0004H.\u00198bi&|g.\u0001\u0007fqBd\u0017M\\1uS>t\u0007\u0005\u0006\u0003\u0003J\t-\u0003cAA\u001d\u001f!9!1\t\nA\u0002\u0005}F\u0003\u0002B%\u0005\u001fB\u0011Ba\u0011\u0014!\u0003\u0005\r!a0\u0015\t\u0005}$1\u000b\u0005\n\u0003\u000f;\u0012\u0011!a\u0001\u0003k\"B!!(\u0003X!I\u0011qQ\r\u0002\u0002\u0003\u0007\u0011q\u0010\u000b\u0005\u0003C\u0012Y\u0006C\u0005\u0002\bj\t\t\u00111\u0001\u0002vQ!\u0011Q\u0014B0\u0011%\t9)HA\u0001\u0002\u0004\ty(\u0001\u0007O_N{WO]2f\u0013:4w.A\nV].twn\u001e8T_V\u00148-\u001a$pe6\fG\u000fE\u0002\u0002:}\u0019Ra\bB5\u0005k\u0002\u0002Ba\u001b\u0003r\u0005}&\u0011J\u0007\u0003\u0005[RAAa\u001c\u0002\"\u00059!/\u001e8uS6,\u0017\u0002\u0002B:\u0005[\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\u00119H! \u000e\u0005\te$\u0002\u0002B>\u0003S\n!![8\n\t\u0005U#\u0011\u0010\u000b\u0003\u0005K\nQ!\u00199qYf$BA!\u0013\u0003\u0006\"9!1\t\u0012A\u0002\u0005}\u0016aB;oCB\u0004H.\u001f\u000b\u0005\u0005\u0017\u0013\t\n\u0005\u0004\u0002 \t5\u0015qX\u0005\u0005\u0005\u001f\u000b\tC\u0001\u0004PaRLwN\u001c\u0005\n\u0005'\u001b\u0013\u0011!a\u0001\u0005\u0013\n1\u0001\u001f\u00131\u0003)\u0019v.\u001e:dK\u001aKG.\u001a\t\u0004\u0003s)4#B\u001b\u0003\u001c\nU\u0004\u0003\u0003B6\u0005c\ny,!5\u0015\u0005\t]E\u0003BAi\u0005CCq!a/9\u0001\u0004\ty\f\u0006\u0003\u0003\f\n\u0015\u0006\"\u0003BJs\u0005\u0005\t\u0019AAi\u0003=\u0019v.\u001e:dK\u001aKG.\u001a'j]\u0016\u001c\bcAA\u001d#N)\u0011K!,\u0003vAa!1\u000eBX\u0003\u007f\u000b)(!\u001e\u0003\u0018%!!\u0011\u0017B7\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|gn\r\u000b\u0003\u0005S#\u0002Ba\u0006\u00038\ne&1\u0018\u0005\b\u0003w#\u0006\u0019AA`\u0011\u001d\u0011i\u0001\u0016a\u0001\u0003kBqA!\u0005U\u0001\u0004\t)\b\u0006\u0003\u0003@\n\u001d\u0007CBA\u0010\u0005\u001b\u0013\t\r\u0005\u0006\u0002 \t\r\u0017qXA;\u0003kJAA!2\u0002\"\t1A+\u001e9mKNB\u0011Ba%V\u0003\u0003\u0005\rAa\u0006\u0015\t\u0005]\"1\u001a\u0005\b\u0005\u001b<\u0006\u0019AA\u000f\u0003\ry'M[\u0001\u0004M>\u0014H\u0003BA\u001c\u0005'DqA!4Y\u0001\u0004\ti\"\u0001\u0006qe\u0016$H/\u001f(b[\u0016$B!a0\u0003Z\"9!QZ-A\u0002\u0005u\u0011!\u00023fEV<WC\u0001Bp\u001f\t\u0011\t/G\u0001\u0001\u0003\u0019!WMY;hA\tI1i\u001c8ti\u0006tGo]\n\u00049\u0006u\u0011!B2pk:$H\u0003\u0002Bw\u0005_\u00042!!\u000f]\u0011\u001d\u0011IO\u0018a\u0001\u0003k\nAa\u00184xIV\u0011!Q\u001f\t\t\u0005o\u0014i0!\u001e\u0002@6\u0011!\u0011 \u0006\u0005\u0005w\f\t*A\u0005j[6,H/\u00192mK&!!q B}\u0005\ri\u0015\r]\u0001\t?\u001a<Hm\u0018\u0013fcR!1QAB\u0006!\u0011\tyba\u0002\n\t\r%\u0011\u0011\u0005\u0002\u0005+:LG\u000fC\u0005\u0002\b\u0002\f\t\u00111\u0001\u0003v\u0006)qLZ<eA\u0005!qL]3w+\t\u0019\u0019\u0002\u0005\u0005\u0003x\nu\u0018qXA;\u0003!y&/\u001a<`I\u0015\fH\u0003BB\u0003\u00073A\u0011\"a\"d\u0003\u0003\u0005\raa\u0005\u0002\u000b}\u0013XM\u001e\u0011\u0002\u000b}C(/\u001a4\u0016\u0005\r\u0005\u0002\u0003\u0003B|\u0005{\f)(!\u001e\u0002\u0013}C(/\u001a4`I\u0015\fH\u0003BB\u0003\u0007OA\u0011\"a\"g\u0003\u0003\u0005\ra!\t\u0002\r}C(/\u001a4!\u0003\r1w\u000fZ\u000b\u0003\u0007_\u0001\u0002\"!1\u00042\u0005U\u0014qX\u0005\u0005\u0005\u007f\fY-A\u0002sKZ,\"aa\u000e\u0011\u0011\u0005\u00057\u0011GA`\u0003k\nqA\\3yi&#\u00070A\u0006oKb$\u0018\n\u001a=`I\u0015\fH\u0003BB\u0003\u0007\u007fA\u0011\"a\"l\u0003\u0003\u0005\r!!\u001e\u0002\u00119,\u0007\u0010^%eq\u0002\na![:E_:,WCAAO)\u0011\tyl!\u0013\t\u000f\r-c\u000e1\u0001\u0002v\u0005\u0019\u0011\u000e\u001a=\u0015\t\u0005U4q\n\u0005\b\u0007#z\u0007\u0019AA`\u0003\r\u0019HO]\u0001\be\u0016\u001cx\u000e\u001c<f)\t\u0019)!\u0001\u0005d_:$\u0018-\u001b8t)\u0011\tija\u0017\t\u000f\rE\u0013\u000f1\u0001\u0002@\u0006\u0019\u0001/\u001e;\u0015\r\r\u00151\u0011MB2\u0011\u001d\u0019YE\u001da\u0001\u0003kBqa!\u0015s\u0001\u0004\ty,A\u0004sK\u0006$wJ\\3\u0015\t\r\u00151\u0011\u000e\u0005\b\u0007W\u001a\b\u0019AB7\u0003\u0005!\u0007\u0003\u0002B<\u0007_JAa!\u001d\u0003z\tyA)\u0019;b\u0013:\u0004X\u000f^*ue\u0016\fW.A\u0005g_J|%M[3diR!\u0011qGB<\u0011\u001d\u0011i\r\u001ea\u0001\u0003;\tqaZ3u\u0013:4w\u000e\u0006\u0004\u00028\ru4q\u0011\u0005\b\u0007\u007f*\b\u0019ABA\u0003\u0019\u0019HO]3b[B!!qOBB\u0013\u0011\u0019)I!\u001f\u0003\u0017%s\u0007/\u001e;TiJ,\u0017-\u001c\u0005\b\u0007\u0013+\b\u0019\u0001BF\u0003\u00191\u0017\u000e\u001c;fe\u0006\tr-\u001a;TiJ,\u0017-\u001c$pe\u000ec\u0017m]:\u0015\t\r=5Q\u0014\t\u0007\u0003?\u0011ii!%\u0011\u0011\u0005}11SBA\u0007/KAa!&\u0002\"\t1A+\u001e9mKJrA!a\b\u0004\u001a&!11TA\u0011\u0003\u0011quN\\3\t\u000f\r}e\u000f1\u0001\u0004\"\u0006\t1\r\r\u0003\u0004$\u000e5\u0006CBAa\u0007K\u001bI+\u0003\u0003\u0004(\u0006-'!B\"mCN\u001c\b\u0003BBV\u0007[c\u0001\u0001\u0002\u0007\u00040\u000eu\u0015\u0011!A\u0001\u0006\u0003\u0019\tLA\u0002`IE\nBaa-\u0002\u0000A!\u0011qDB[\u0013\u0011\u00199,!\t\u0003\u000f9{G\u000f[5oO\u0006\u0011r-\u001a;TiJ,\u0017-\u001c$pe2\u000bWN\u00193b)\u0011\u0019ila2\u0011\r\u0005}!QRB`!!\tyba%\u0004\u0002\u000e\u0005\u0007CBA\u0010\u0007\u0007\fy,\u0003\u0003\u0004F\u0006\u0005\"\u0001B*p[\u0016Dqa!3x\u0001\u0004\ti\"A\u0001m\u0003\u0019\u00198.\u001b9J\tR!1QABh\u0011\u001d\u0019Y\u0007\u001fa\u0001\u0007[\n1b]6jaZ+'o]5p]R!1QABk\u0011\u001d\u0019Y'\u001fa\u0001\u0007[\nAbZ3u\u0007>t7\u000f^1oiN$BA!<\u0004\\\"911\u000e>A\u0002\r5\u0014!D:lSB\u001cE.Y:t\u0013:4w\u000e\u0006\u0003\u0004b\u000e\u0015H\u0003BB\u0003\u0007GDqaa(|\u0001\b\u0011i\u000fC\u0004\u0004lm\u0004\ra!\u001c\u0002#M\\\u0017\u000e]%oi\u0016\u0014h-Y2f\u0013:4w\u000e\u0006\u0003\u0004l\u000e=H\u0003BB\u0003\u0007[Dqaa(}\u0001\b\u0011i\u000fC\u0004\u0004lq\u0004\ra!\u001c\u0002\u0015M\\\u0017\u000e\u001d$jK2$7\u000f\u0006\u0003\u0004v\u000eeH\u0003BB\u0003\u0007oDqaa(~\u0001\b\u0011i\u000fC\u0004\u0004lu\u0004\ra!\u001c\u0002#M\\\u0017\u000e]'fi\"|Gm\u0014:GS\u0016dG\r\u0006\u0003\u0004\u0000\u0012\rA\u0003BB\u0003\t\u0003Aqaa(\u007f\u0001\b\u0011i\u000fC\u0004\u0004ly\u0004\ra!\u001c\u0002\u001bM\\\u0017\u000e]!uiJL'-\u001e;f)\u0011\u0019)\u0001\"\u0003\t\u000f\r-t\u00101\u0001\u0004n\u0005Y!/Z1e\u001b\u0016$\bn\u001c3t)\u0019!y\u0001b\u0006\u0005\u001aQ!A\u0011\u0003C\u000b!\u0019\tyB!$\u0005\u0014AA\u0011qDBJ\u0003k\n)\b\u0003\u0005\u0004 \u0006\u0005\u00019\u0001Bw\u0011!\u0019Y'!\u0001A\u0002\r5\u0004\u0002CBE\u0003\u0003\u0001\rAa#\u0002\u0015I,\u0017\rZ'fi\"|G\r\u0006\u0006\u0005 \u0011\rBQ\u0005C\u0015\t[!B\u0001\"\u0005\u0005\"!A1qTA\u0002\u0001\b\u0011i\u000f\u0003\u0005\u0004l\u0005\r\u0001\u0019AB7\u0011!!9#a\u0001A\u0002\u0005U\u0014aB2pI\u0016$\u0016m\u001a\u0005\t\tW\t\u0019\u00011\u0001\u0002v\u0005\u0011B.\u001b8f\u001dVl'-\u001a:UC\ndW\rV1h\u0011!\u0019I)a\u0001A\u0002\t-\u0015A\u0004:fC\u0012\fE\u000f\u001e:jEV$Xm\u001d\u000b\u0005\tg!9\u0004\u0006\u0003\u0003\f\u0012U\u0002\u0002CBP\u0003\u000b\u0001\u001dA!<\t\u0011\r-\u0014Q\u0001a\u0001\u0007[\nAa]6jaR11Q\u0001C\u001f\t\u007fA\u0001ba\u001b\u0002\b\u0001\u00071Q\u000e\u0005\t\t\u0003\n9\u00011\u0001\u0002v\u00051A.\u001a8hi\"\u0004")
public final class LineNumbers {
    public static String prettyName(Object obj) {
        return LineNumbers$.MODULE$.prettyName(obj);
    }

    public static Result for(Object obj) {
        return LineNumbers$.MODULE$.for(obj);
    }

    public static Result apply(Object obj) {
        return LineNumbers$.MODULE$.apply(obj);
    }

    private static class Constants {
        private final int count;
        private Map<Object, String> _fwd;
        private Map<String, Object> _rev;
        private Map<Object, Object> _xref;
        private int nextIdx;

        private Map<Object, String> _fwd() {
            return this._fwd;
        }

        private void _fwd_$eq(Map<Object, String> x$1) {
            this._fwd = x$1;
        }

        private Map<String, Object> _rev() {
            return this._rev;
        }

        private void _rev_$eq(Map<String, Object> x$1) {
            this._rev = x$1;
        }

        private Map<Object, Object> _xref() {
            return this._xref;
        }

        private void _xref_$eq(Map<Object, Object> x$1) {
            this._xref = x$1;
        }

        public Map<Object, String> fwd() {
            return this._fwd();
        }

        public Map<String, Object> rev() {
            return this._rev();
        }

        private int nextIdx() {
            return this.nextIdx;
        }

        private void nextIdx_$eq(int x$1) {
            this.nextIdx = x$1;
        }

        public boolean isDone() {
            return this.nextIdx() >= this.count;
        }

        public String apply(int idx) {
            return (String)this._fwd().apply(BoxesRunTime.boxToInteger(idx));
        }

        public int apply(String str) {
            return BoxesRunTime.unboxToInt(this._rev().apply(str));
        }

        public void resolve() {
            this._xref().foreach((Function1<Tuple2, Object> & Serializable)p -> {
                this.put(p._1$mcI$sp(), this.apply(p._2$mcI$sp()));
                return BoxedUnit.UNIT;
            });
        }

        public boolean contains(String str) {
            return this._rev().contains(str);
        }

        private void put(int idx, String str) {
            if (!this._rev().contains(str)) {
                this._rev_$eq((Map)this._rev().updated(str, BoxesRunTime.boxToInteger(idx)));
            }
            this._fwd_$eq((Map)this._fwd().updated(BoxesRunTime.boxToInteger(idx), str));
        }

        public void readOne(DataInputStream d) {
            byte by = d.readByte();
            switch (by) {
                case 1: {
                    String str = d.readUTF();
                    this.put(this.nextIdx(), str);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 3: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 4);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 4: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 4);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 5: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 8);
                    this.nextIdx_$eq(this.nextIdx() + 2);
                    return;
                }
                case 6: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 8);
                    this.nextIdx_$eq(this.nextIdx() + 2);
                    return;
                }
                case 7: {
                    int other = d.readUnsignedShort();
                    this._xref_$eq((Map)this._xref().updated(BoxesRunTime.boxToInteger(this.nextIdx()), BoxesRunTime.boxToInteger(other)));
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 8: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 2);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 9: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 4);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 10: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 4);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 11: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 4);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 12: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 4);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 15: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 3);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 16: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 2);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
                case 18: {
                    LineNumbers$.MODULE$.akka$util$LineNumbers$$skip(d, 4);
                    this.nextIdx_$eq(this.nextIdx() + 1);
                    return;
                }
            }
            throw new MatchError(BoxesRunTime.boxToByte(by));
        }

        public Constants(int count) {
            this.count = count;
            this._fwd = Predef$.MODULE$.Map().empty();
            this._rev = Predef$.MODULE$.Map().empty();
            this._xref = Predef$.MODULE$.Map().empty();
            this.nextIdx = 1;
        }
    }

    public static interface Result {
    }

    public static final class SourceFile
    implements Result,
    Product,
    Serializable {
        private final String filename;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String filename() {
            return this.filename;
        }

        public String toString() {
            return this.filename();
        }

        public SourceFile copy(String filename) {
            return new SourceFile(filename);
        }

        public String copy$default$1() {
            return this.filename();
        }

        @Override
        public String productPrefix() {
            return "SourceFile";
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
                    return this.filename();
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
            return x$1 instanceof SourceFile;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "filename";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof SourceFile)) return false;
            boolean bl = true;
            if (!bl) return false;
            SourceFile sourceFile = (SourceFile)x$1;
            String string = this.filename();
            String string2 = sourceFile.filename();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public SourceFile(String filename) {
            this.filename = filename;
            Product.$init$(this);
        }
    }

    public static final class SourceFileLines
    implements Result,
    Product,
    Serializable {
        private final String filename;
        private final int from;
        private final int to;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String filename() {
            return this.filename;
        }

        public int from() {
            return this.from;
        }

        public int to() {
            return this.to;
        }

        public String toString() {
            if (this.from() != this.to()) {
                return new StringBuilder(2).append(this.filename()).append(":").append(this.from()).append("-").append(this.to()).toString();
            }
            return new StringBuilder(1).append(this.filename()).append(":").append(this.from()).toString();
        }

        public SourceFileLines copy(String filename, int from, int to) {
            return new SourceFileLines(filename, from, to);
        }

        public String copy$default$1() {
            return this.filename();
        }

        public int copy$default$2() {
            return this.from();
        }

        public int copy$default$3() {
            return this.to();
        }

        @Override
        public String productPrefix() {
            return "SourceFileLines";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.filename();
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.from());
                }
                case 2: {
                    return BoxesRunTime.boxToInteger(this.to());
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
            return x$1 instanceof SourceFileLines;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "filename";
                }
                case 1: {
                    return "from";
                }
                case 2: {
                    return "to";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.filename()));
            n = Statics.mix(n, this.from());
            n = Statics.mix(n, this.to());
            return Statics.finalizeHash(n, 3);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof SourceFileLines)) return false;
            boolean bl = true;
            if (!bl) return false;
            SourceFileLines sourceFileLines = (SourceFileLines)x$1;
            if (this.from() != sourceFileLines.from()) return false;
            if (this.to() != sourceFileLines.to()) return false;
            String string = this.filename();
            String string2 = sourceFileLines.filename();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public SourceFileLines(String filename, int from, int to) {
            this.filename = filename;
            this.from = from;
            this.to = to;
            Product.$init$(this);
        }
    }

    public static final class UnknownSourceFormat
    implements Result,
    Product,
    Serializable {
        private final String explanation;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String explanation() {
            return this.explanation;
        }

        public UnknownSourceFormat copy(String explanation) {
            return new UnknownSourceFormat(explanation);
        }

        public String copy$default$1() {
            return this.explanation();
        }

        @Override
        public String productPrefix() {
            return "UnknownSourceFormat";
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
                    return this.explanation();
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
            return x$1 instanceof UnknownSourceFormat;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "explanation";
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
            if (!(object instanceof UnknownSourceFormat)) return false;
            boolean bl = true;
            if (!bl) return false;
            UnknownSourceFormat unknownSourceFormat = (UnknownSourceFormat)x$1;
            String string = this.explanation();
            String string2 = unknownSourceFormat.explanation();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public UnknownSourceFormat(String explanation) {
            this.explanation = explanation;
            Product.$init$(this);
        }
    }
}

