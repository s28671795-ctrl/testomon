/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.io.ByteStringParser$;
import akka.stream.impl.io.ByteStringParser$FinishedParser$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0011]aAB5k\u0003\u0003\u0001(\u000fC\u0004\u0002(\u0001!\t!!\u000b\t\u0013\u0005=\u0002A1A\u0005\n\u0005E\u0002\u0002CA\u001d\u0001\u0001\u0006I!a\r\t\u0013\u0005m\u0002A1A\u0005\n\u0005u\u0002\u0002CA#\u0001\u0001\u0006I!a\u0010\t\u000f\u0005\u001d\u0003\u0001\"\u0011\u0002J!I\u0011\u0011\u000b\u0001C\u0002\u0013\u0015\u00131\u000b\u0005\b\u0003+\u0002\u0001\u0015!\u0004{\r\u0019\t9\u0006\u0001\u0001\u0002Z!9\u0011qE\u0005\u0005\u0002\u00055\u0004\"CA:\u0013\u0001\u0007I\u0011BA;\u0011%\t9(\u0003a\u0001\n\u0013\tI\bC\u0004\u0002\u0006&\u0001\u000b\u0015\u0002@\t\u0013\u0005\u001d\u0015\u00021A\u0005\n\u0005%\u0005\"CBi\u0013\u0001\u0007I\u0011BBj\u0011!\u00199.\u0003Q!\n\u0005-\u0005\"\u0003B3\u0013\u0001\u0007I\u0011\u0002B\u0003\u0011%\u0019I.\u0003a\u0001\n\u0013\u0019Y\u000e\u0003\u0005\u0003h%\u0001\u000b\u0015\u0002B\u0004\u0011%\u0019y.\u0003a\u0001\n\u0013\ty\nC\u0005\u0004b&\u0001\r\u0011\"\u0003\u0004d\"A1q]\u0005!B\u0013\t\t\u000bC\u0004\u0004j&!)ba;\t\u000f\rE\u0018\u0002\"\u0005\u0002 \"911_\u0005\u0005\n\rU\bbBB|\u0013\u0011%1\u0011 \u0005\n\t\u0013I\u0011\u0013!C\u0005\t\u0017Aq\u0001b\u0004\n\t\u0003\u0012\t\u0001C\u0004\u0005\u0012%!\tA!\u0001\t\u000f\u0011M\u0011\u0002\"\u0011\u0003\u0002\u001dA\u0011q\u00126\t\u0002A\f\tJB\u0004jU\"\u0005\u0001/a%\t\u000f\u0005\u001d\u0002\u0005\"\u0001\u0002\u001c\"I\u0011Q\u0014\u0011C\u0002\u0013\u0005\u0011q\u0014\u0005\t\u0003O\u0003\u0003\u0015!\u0003\u0002\"\"I\u0011\u0011\u0016\u0011C\u0002\u00135\u00111\u0016\u0005\t\u0003c\u0003\u0003\u0015!\u0004\u0002.\"I\u00111\u0017\u0011C\u0002\u00135\u0011Q\u0017\u0005\t\u0003w\u0003\u0003\u0015!\u0004\u00028\u001a1\u0011Q\u0018\u0011A\u0003\u007fC!\"!9)\u0005+\u0007I\u0011AAr\u0011)\ty\u000f\u000bB\tB\u0003%\u0011Q\u001d\u0005\u000b\u0003cD#Q3A\u0005\u0002\u0005M\bB\u0003B2Q\tE\t\u0015!\u0003\u0002v\"Q!Q\r\u0015\u0003\u0016\u0004%\tA!\u0002\t\u0015\t\u001d\u0004F!E!\u0002\u0013\u00119\u0001C\u0004\u0002(!\"\tA!\u001b\t\u0013\tM\u0004&!A\u0005\u0002\tU\u0004\"\u0003BEQE\u0005I\u0011\u0001BF\u0011%\u0011)\u000bKI\u0001\n\u0003\u00119\u000bC\u0005\u00030\"\n\n\u0011\"\u0001\u00032\"I!\u0011\u0018\u0015\u0002\u0002\u0013\u0005#1\u0018\u0005\n\u0005\u001bD\u0013\u0011!C\u0001\u0003?C\u0011Ba4)\u0003\u0003%\tA!5\t\u0013\tU\u0007&!A\u0005B\t]\u0007\"\u0003BsQ\u0005\u0005I\u0011\u0001Bt\u0011%\u0011Y\u000fKA\u0001\n\u0003\u0012i\u000fC\u0005\u0003r\"\n\t\u0011\"\u0011\u0003B!I!1\u001f\u0015\u0002\u0002\u0013\u0005#Q\u001f\u0005\n\u0005oD\u0013\u0011!C!\u0005s<\u0011B!@!\u0003\u0003E\tAa@\u0007\u0013\u0005u\u0006%!A\t\u0002\r\u0005\u0001bBA\u0014}\u0011\u000511\u0002\u0005\n\u0005gt\u0014\u0011!C#\u0005kD\u0011b!\u0004?\u0003\u0003%\tia\u0004\t\u0013\r\rb(%A\u0005\u0002\r\u0015\u0002\"CB\u0015}\u0005\u0005I\u0011QB\u0016\u0011%\u0019)EPI\u0001\n\u0003\u00199\u0005C\u0005\u0004Ly\n\t\u0011\"\u0003\u0004N\u0019I\u0011\u0011 \u0011\u0011\u0002\u0007\u0005\u00111 \u0005\b\u0003\u007f4E\u0011\u0001B\u0001\u0011\u001d\u0011\u0019A\u0012C\u0001\u0005\u000bAqA!\u0004G\r\u0003\u0011y\u0001C\u0004\u0003b\u0019#\tA!\u0001\b\u000f\rU\u0003\u0005#\u0001\u0004X\u001991\u0011\f\u0011\t\u0002\rm\u0003bBA\u0014\u0019\u0012\u00051q\f\u0005\b\u0005\u001baE\u0011IB1\r\u0019\u0019)\u0007\t\u0001\u0004h!Q1qN(\u0003\u0002\u0003\u0006Ia!\u001d\t\u0015\r}tJ!A!\u0002\u0013\u0019\t\tC\u0004\u0002(=#\taa\"\t\u0013\r\r\u0006E1A\u0005\u0002\r\u0015\u0006\u0002CBWA\u0001\u0006Iaa*\u0007\r\tu\u0001\u0005\u0001B\u0010\u0011%\u0011\t#\u0016B\u0001B\u0003%a\u0010C\u0004\u0002(U#\tAa\t\t\u0011\t\u001dR\u000b)Q\u0005\u0003CCqA!\u000bV\t\u0003\u0011)\u0001C\u0004\u0003,U#\t!a(\t\u000f\t5R\u000b\"\u0001\u0002 \"9!qF+\u0005\u0002\u0005U\u0004b\u0002B\u0019+\u0012\u0005\u0011Q\u000f\u0005\b\u0005g)F\u0011\u0001B\u001b\u0011\u001d\u0011Y$\u0016C\u0001\u0005{AqAa\u0010V\t\u0003\u0011\t\u0005C\u0004\u0003DU#\tA!\u0011\t\u000f\t\u0015S\u000b\"\u0001\u0003B!9!qI+\u0005\u0002\t%\u0003b\u0002B)+\u0012\u0005!\u0011\t\u0005\b\u0005'*F\u0011\u0001B!\u0011\u001d\u0011)&\u0016C\u0001\u0005\u0013BqAa\u0016V\t\u0003\u0011I\u0006C\u0004\u0003`U#\tA!\u0001\u0003!\tKH/Z*ue&tw\rU1sg\u0016\u0014(BA6m\u0003\tIwN\u0003\u0002n]\u0006!\u0011.\u001c9m\u0015\ty\u0007/\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002c\u0006!\u0011m[6b+\r\u0019\u0018QB\n\u0003\u0001Q\u00042!\u001e={\u001b\u00051(BA<o\u0003\u0015\u0019H/Y4f\u0013\tIhO\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004Ra\u001f?\u007f\u0003\u0013i\u0011A\\\u0005\u0003{:\u0014\u0011B\u00127poNC\u0017\r]3\u0011\u0007}\f)!\u0004\u0002\u0002\u0002)\u0019\u00111\u00019\u0002\tU$\u0018\u000e\\\u0005\u0005\u0003\u000f\t\tA\u0001\u0006CsR,7\u000b\u001e:j]\u001e\u0004B!a\u0003\u0002\u000e1\u0001AaBA\b\u0001\t\u0007\u00111\u0003\u0002\u0002)\u000e\u0001\u0011\u0003BA\u000b\u0003C\u0001B!a\u0006\u0002\u001e5\u0011\u0011\u0011\u0004\u0006\u0003\u00037\tQa]2bY\u0006LA!a\b\u0002\u001a\t9aj\u001c;iS:<\u0007\u0003BA\f\u0003GIA!!\n\u0002\u001a\t\u0019\u0011I\\=\u0002\rqJg.\u001b;?)\t\tY\u0003E\u0003\u0002.\u0001\tI!D\u0001k\u0003\u001d\u0011\u0017\u0010^3t\u0013:,\"!a\r\u0011\tm\f)D`\u0005\u0004\u0003oq'!B%oY\u0016$\u0018\u0001\u00032zi\u0016\u001c\u0018J\u001c\u0011\u0002\r=\u0014'nT;u+\t\ty\u0004E\u0003|\u0003\u0003\nI!C\u0002\u0002D9\u0014aaT;uY\u0016$\u0018aB8cU>+H\u000fI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cXCAA&!\rY\u0018QJ\u0005\u0004\u0003\u001fr'AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\t!0\u0001\u0004tQ\u0006\u0004X\r\t\u0002\r!\u0006\u00148/\u001b8h\u0019><\u0017nY\n\b\u0013\u0005m\u0013\u0011MA4!\r)\u0018QL\u0005\u0004\u0003?2(aD$sCBD7\u000b^1hK2{w-[2\u0011\u0007U\f\u0019'C\u0002\u0002fY\u0014\u0011\"\u00138IC:$G.\u001a:\u0011\u0007U\fI'C\u0002\u0002lY\u0014!bT;u\u0011\u0006tG\r\\3s)\t\ty\u0007E\u0002\u0002r%i\u0011\u0001A\u0001\u0007EV4g-\u001a:\u0016\u0003y\f!BY;gM\u0016\u0014x\fJ3r)\u0011\tY(!!\u0011\t\u0005]\u0011QP\u0005\u0005\u0003\u007f\nIB\u0001\u0003V]&$\b\u0002CAB\u0019\u0005\u0005\t\u0019\u0001@\u0002\u0007a$\u0013'A\u0004ck\u001a4WM\u001d\u0011\u0002\u000f\r,(O]3oiV\u0011\u00111\u0012\t\u0006\u0003\u001b3\u0015\u0011\u0002\b\u0004\u0003[y\u0012\u0001\u0005\"zi\u0016\u001cFO]5oOB\u000b'o]3s!\r\ti\u0003I\n\u0004A\u0005U\u0005\u0003BA\f\u0003/KA!!'\u0002\u001a\t1\u0011I\\=SK\u001a$\"!!%\u0002'\r{W\u000e]1di&|g\u000e\u00165sKNDw\u000e\u001c3\u0016\u0005\u0005\u0005\u0006\u0003BA\f\u0003GKA!!*\u0002\u001a\t\u0019\u0011J\u001c;\u0002)\r{W\u000e]1di&|g\u000e\u00165sKNDw\u000e\u001c3!\u0003\u001d\u0011VmY;sg\u0016,\"!!,\u0010\u0005\u0005=\u0016$A\u0001\u0002\u0011I+7-\u001e:tK\u0002\n1\u0002R8oiJ+7-\u001e:tKV\u0011\u0011qW\b\u0003\u0003sK\u0012\u0001A\u0001\r\t>tGOU3dkJ\u001cX\r\t\u0002\f!\u0006\u00148/\u001a*fgVdG/\u0006\u0003\u0002B\u000658c\u0002\u0015\u0002\u0016\u0006\r\u0017\u0011\u001a\t\u0005\u0003/\t)-\u0003\u0003\u0002H\u0006e!a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003\u0017\fYN\u0004\u0003\u0002N\u0006]g\u0002BAh\u0003+l!!!5\u000b\t\u0005M\u0017\u0011C\u0001\u0007yI|w\u000e\u001e \n\u0005\u0005m\u0011\u0002BAm\u00033\tq\u0001]1dW\u0006<W-\u0003\u0003\u0002^\u0006}'\u0001D*fe&\fG.\u001b>bE2,'\u0002BAm\u00033\taA]3tk2$XCAAs!\u0019\t9\"a:\u0002l&!\u0011\u0011^A\r\u0005\u0019y\u0005\u000f^5p]B!\u00111BAw\t!\ty\u0001\u000bCC\u0002\u0005M\u0011a\u0002:fgVdG\u000fI\u0001\t]\u0016DHo\u0015;faV\u0011\u0011Q\u001f\t\u0006\u0003o4\u00151^\u0007\u0002A\tI\u0001+\u0019:tKN#X\r]\u000b\u0005\u0003{\u0014)bE\u0002G\u0003+\u000ba\u0001J5oSR$CCAA>\u0003Y\u0019\u0017M\\,pe.<\u0016\u000e\u001e5QCJ$\u0018.\u00197ECR\fWC\u0001B\u0004!\u0011\t9B!\u0003\n\t\t-\u0011\u0011\u0004\u0002\b\u0005>|G.Z1o\u0003\u0015\u0001\u0018M]:f)\u0011\u0011\tBa\u0006\u0011\u000b\u0005]\bFa\u0005\u0011\t\u0005-!Q\u0003\u0003\t\u0003\u001f1EQ1\u0001\u0002\u0014!9!\u0011D%A\u0002\tm\u0011A\u0002:fC\u0012,'\u000fE\u0002\u0002xV\u0013!BQ=uKJ+\u0017\rZ3s'\r)\u0016QS\u0001\u0006S:\u0004X\u000f\u001e\u000b\u0005\u00057\u0011)\u0003\u0003\u0004\u0003\"]\u0003\rA`\u0001\u0004_\u001a4\u0017\u0001\u00045bgJ+W.Y5oS:<\u0017!\u0004:f[\u0006Lg.\u001b8h'&TX-A\u0007dkJ\u0014XM\u001c;PM\u001a\u001cX\r^\u0001\u000ee\u0016l\u0017-\u001b8j]\u001e$\u0015\r^1\u0002\u001f\u0019\u0014x.\\*uCJ$Hk\u001c%fe\u0016\fA\u0001^1lKR\u0019aPa\u000e\t\u000f\teb\f1\u0001\u0002\"\u0006\ta.A\u0004uC.,\u0017\t\u001c7\u0015\u0003y\f\u0001B]3bI\nKH/\u001a\u000b\u0003\u0003C\u000b1B]3bINCwN\u001d;M\u000b\u0006I!/Z1e\u0013:$H*R\u0001\u000be\u0016\fG\rT8oO2+EC\u0001B&!\u0011\t9B!\u0014\n\t\t=\u0013\u0011\u0004\u0002\u0005\u0019>tw-A\u0006sK\u0006$7\u000b[8si\n+\u0015!\u0003:fC\u0012Le\u000e\u001e\"F\u0003)\u0011X-\u00193M_:<')R\u0001\u0005g.L\u0007\u000f\u0006\u0003\u0002|\tm\u0003b\u0002B/O\u0002\u0007\u0011\u0011U\u0001\t]Vl')\u001f;fg\u0006A2o[5q5\u0016\u0014x\u000eV3s[&t\u0017\r^3e'R\u0014\u0018N\\4\u0002\u0019=tGK];oG\u0006$\u0018n\u001c8\u0002\u00139,\u0007\u0010^*uKB\u0004\u0013\u0001F1dG\u0016\u0004H/\u00169tiJ,\u0017-\u001c$j]&\u001c\b.A\u000bbG\u000e,\u0007\u000f^+qgR\u0014X-Y7GS:L7\u000f\u001b\u0011\u0015\u0011\t-$Q\u000eB8\u0005c\u0002R!a>)\u0003WDq!!90\u0001\u0004\t)\u000fC\u0004\u0002r>\u0002\r!!>\t\u0013\t\u0015t\u0006%AA\u0002\t\u001d\u0011\u0001B2paf,BAa\u001e\u0003~QA!\u0011\u0010B@\u0005\u0007\u00139\tE\u0003\u0002x\"\u0012Y\b\u0005\u0003\u0002\f\tuDaBA\ba\t\u0007\u00111\u0003\u0005\n\u0003C\u0004\u0004\u0013!a\u0001\u0005\u0003\u0003b!a\u0006\u0002h\nm\u0004\"CAyaA\u0005\t\u0019\u0001BC!\u0015\t9P\u0012B>\u0011%\u0011)\u0007\rI\u0001\u0002\u0004\u00119!\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\t\t5%1U\u000b\u0003\u0005\u001fSC!!:\u0003\u0012.\u0012!1\u0013\t\u0005\u0005+\u0013y*\u0004\u0002\u0003\u0018*!!\u0011\u0014BN\u0003%)hn\u00195fG.,GM\u0003\u0003\u0003\u001e\u0006e\u0011AC1o]>$\u0018\r^5p]&!!\u0011\u0015BL\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\b\u0003\u001f\t$\u0019AA\n\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*BA!+\u0003.V\u0011!1\u0016\u0016\u0005\u0003k\u0014\t\nB\u0004\u0002\u0010I\u0012\r!a\u0005\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU!!1\u0017B\\+\t\u0011)L\u000b\u0003\u0003\b\tEEaBA\bg\t\u0007\u00111C\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\tu\u0006\u0003\u0002B`\u0005\u0013l!A!1\u000b\t\t\r'QY\u0001\u0005Y\u0006twM\u0003\u0002\u0003H\u0006!!.\u0019<b\u0013\u0011\u0011YM!1\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!!\t\u0003T\"I\u00111\u0011\u001c\u0002\u0002\u0003\u0007\u0011\u0011U\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011!\u0011\u001c\t\u0007\u00057\u0014\t/!\t\u000e\u0005\tu'\u0002\u0002Bp\u00033\t!bY8mY\u0016\u001cG/[8o\u0013\u0011\u0011\u0019O!8\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0005\u000f\u0011I\u000fC\u0005\u0002\u0004b\n\t\u00111\u0001\u0002\"\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u0011iLa<\t\u0013\u0005\r\u0015(!AA\u0002\u0005\u0005\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"A!0\u0002\r\u0015\fX/\u00197t)\u0011\u00119Aa?\t\u0013\u0005\rE(!AA\u0002\u0005\u0005\u0012a\u0003)beN,'+Z:vYR\u00042!a>?'\u0015q\u0014QSB\u0002!\u0011\u0019)a!\u0003\u000e\u0005\r\u001d!bA6\u0003F&!\u0011Q\\B\u0004)\t\u0011y0A\u0003baBd\u00170\u0006\u0003\u0004\u0012\r]A\u0003CB\n\u00073\u0019ib!\t\u0011\u000b\u0005]\bf!\u0006\u0011\t\u0005-1q\u0003\u0003\b\u0003\u001f\t%\u0019AA\n\u0011\u001d\t\t/\u0011a\u0001\u00077\u0001b!a\u0006\u0002h\u000eU\u0001bBAy\u0003\u0002\u00071q\u0004\t\u0006\u0003o45Q\u0003\u0005\n\u0005K\n\u0005\u0013!a\u0001\u0005\u000f\tq\"\u00199qYf$C-\u001a4bk2$HeM\u000b\u0005\u0005g\u001b9\u0003B\u0004\u0002\u0010\t\u0013\r!a\u0005\u0002\u000fUt\u0017\r\u001d9msV!1QFB\u001e)\u0011\u0019yca\u0010\u0011\r\u0005]\u0011q]B\u0019!)\t9ba\r\u00048\ru\"qA\u0005\u0005\u0007k\tIB\u0001\u0004UkBdWm\r\t\u0007\u0003/\t9o!\u000f\u0011\t\u0005-11\b\u0003\b\u0003\u001f\u0019%\u0019AA\n!\u0015\t9PRB\u001d\u0011%\u0019\teQA\u0001\u0002\u0004\u0019\u0019%A\u0002yIA\u0002R!a>)\u0007s\t1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u001aT\u0003\u0002BZ\u0007\u0013\"q!a\u0004E\u0005\u0004\t\u0019\"\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004PA!!qXB)\u0013\u0011\u0019\u0019F!1\u0003\r=\u0013'.Z2u\u000391\u0015N\\5tQ\u0016$\u0007+\u0019:tKJ\u00042!a>M\u000591\u0015N\\5tQ\u0016$\u0007+\u0019:tKJ\u001cR\u0001TAK\u0007;\u0002R!a>G\u0003+!\"aa\u0016\u0015\t\u0005U11\r\u0005\b\u00053q\u0005\u0019\u0001B\u000e\u0005A\u0001\u0016M]:j]\u001e,\u0005pY3qi&|gnE\u0002P\u0007S\u0002B!a3\u0004l%!1QNAp\u0005A\u0011VO\u001c;j[\u0016,\u0005pY3qi&|g.A\u0002ng\u001e\u0004Baa\u001d\u0004|9!1QOB<!\u0011\ty-!\u0007\n\t\re\u0014\u0011D\u0001\u0007!J,G-\u001a4\n\t\t-7Q\u0010\u0006\u0005\u0007s\nI\"A\u0003dCV\u001cX\r\u0005\u0003\u0002L\u000e\r\u0015\u0002BBC\u0003?\u0014\u0011\u0002\u00165s_^\f'\r\\3\u0015\r\r%51RBG!\r\t9p\u0014\u0005\b\u0007_\u0012\u0006\u0019AB9\u0011\u001d\u0019yH\u0015a\u0001\u0007\u0003C3bTBI\u0007/\u001bIj!(\u0004 B!\u0011qCBJ\u0013\u0011\u0019)*!\u0007\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0005\rm\u0015\u0001\u0010#faJ,7-\u0019;fI\u00022wN\u001d\u0011j]R,'O\\1mAU\u001c\u0018mZ3/A]KG\u000e\u001c\u0011o_R\u0004#-\u001a\u0011f[&$H/\u001a3!C:L\b%\\8sK:\nQa]5oG\u0016\f#a!)\u0002\rIrcG\f\u001a1\u00031qU-\u001a3N_J,G)\u0019;b+\t\u00199K\u0005\u0004\u0004*\u000e=6Q\u0017\u0004\u0007\u0007W#\u0006aa*\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0002\u001b9+W\rZ'pe\u0016$\u0015\r^1!!\u0011\tYm!-\n\t\rM\u0016q\u001c\u0002\n\u000bb\u001cW\r\u001d;j_:\u0004Baa.\u0004@6\u00111\u0011\u0018\u0006\u0005\u0007w\u001bi,A\u0004d_:$(o\u001c7\u000b\t\u0005\r\u0011\u0011D\u0005\u0005\u0007\u0003\u001cIL\u0001\u0007O_N#\u0018mY6Ue\u0006\u001cW\rK\u0002!\u0007\u000b\u0004Baa2\u0004L6\u00111\u0011\u001a\u0006\u0004\u0005;\u0003\u0018\u0002BBg\u0007\u0013\u00141\"\u00138uKJt\u0017\r\\!qS\"\u001aqd!2\u0002\u0017\r,(O]3oi~#S-\u001d\u000b\u0005\u0003w\u001a)\u000eC\u0005\u0002\u0004>\t\t\u00111\u0001\u0002\f\u0006A1-\u001e:sK:$\b%\u0001\rbG\u000e,\u0007\u000f^+qgR\u0014X-Y7GS:L7\u000f[0%KF$B!a\u001f\u0004^\"I\u00111\u0011\n\u0002\u0002\u0003\u0007!qA\u0001\rk:$\u0018\u000e\\\"p[B\f7\r^\u0001\u0011k:$\u0018\u000e\\\"p[B\f7\r^0%KF$B!a\u001f\u0004f\"I\u00111Q\u000b\u0002\u0002\u0003\u0007\u0011\u0011U\u0001\u000ek:$\u0018\u000e\\\"p[B\f7\r\u001e\u0011\u0002\u0013M$\u0018M\u001d;XSRDG\u0003BA>\u0007[Dqaa<\u0018\u0001\u0004\tY)\u0001\u0003ti\u0016\u0004\u0018A\u0004:fGV\u00148/[8o\u0019&l\u0017\u000e^\u0001\rI>\u0004\u0016M]:f\u0013:tWM\u001d\u000b\u0003\u0005\u000f\tq\u0001Z8QCJ\u001cX\r\u0006\u0003\u0002|\rm\b\"CB\u007f5A\u0005\t\u0019AAQ\u0003M\u0011X-\\1j]&twMU3dkJ\u001c\u0018n\u001c8tQ\rQB\u0011\u0001\t\u0005\t\u0007!)!\u0004\u0002\u0003\u001c&!Aq\u0001BN\u0005\u001d!\u0018-\u001b7sK\u000e\f\u0011\u0003Z8QCJ\u001cX\r\n3fM\u0006,H\u000e\u001e\u00132+\t!iA\u000b\u0003\u0002\"\nE\u0015AB8o!VdG.\u0001\u0004p]B+8\u000f[\u0001\u0011_:,\u0006o\u001d;sK\u0006lg)\u001b8jg\"D3\u0001ABc\u0001")
@InternalApi
public abstract class ByteStringParser<T>
extends GraphStage<FlowShape<ByteString, T>> {
    private final Inlet<ByteString> akka$stream$impl$io$ByteStringParser$$bytesIn = Inlet$.MODULE$.apply("bytesIn");
    private final Outlet<T> akka$stream$impl$io$ByteStringParser$$objOut = Outlet$.MODULE$.apply("objOut");
    private final FlowShape<ByteString, T> shape = new FlowShape<ByteString, T>(this.akka$stream$impl$io$ByteStringParser$$bytesIn(), this.akka$stream$impl$io$ByteStringParser$$objOut());

    public static Exception NeedMoreData() {
        return ByteStringParser$.MODULE$.NeedMoreData();
    }

    public static int CompactionThreshold() {
        return ByteStringParser$.MODULE$.CompactionThreshold();
    }

    public Inlet<ByteString> akka$stream$impl$io$ByteStringParser$$bytesIn() {
        return this.akka$stream$impl$io$ByteStringParser$$bytesIn;
    }

    public Outlet<T> akka$stream$impl$io$ByteStringParser$$objOut() {
        return this.akka$stream$impl$io$ByteStringParser$$objOut;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ByteStringParser");
    }

    @Override
    public final FlowShape<ByteString, T> shape() {
        return this.shape;
    }

    public static class ByteReader {
        private final ByteString input;
        private int off;

        public boolean hasRemaining() {
            return this.off < this.input.length();
        }

        public int remainingSize() {
            return this.input.length() - this.off;
        }

        public int currentOffset() {
            return this.off;
        }

        public ByteString remainingData() {
            return this.input.drop(this.off);
        }

        public ByteString fromStartToHere() {
            return this.input.take(this.off);
        }

        public ByteString take(int n) {
            if (this.off + n <= this.input.length()) {
                int o = this.off;
                this.off = o + n;
                return this.input.slice(o, this.off);
            }
            throw ByteStringParser$.MODULE$.NeedMoreData();
        }

        public ByteString takeAll() {
            ByteString ret = this.remainingData();
            this.off = this.input.size();
            return ret;
        }

        public int readByte() {
            if (this.off < this.input.length()) {
                byte x = this.input.apply(this.off);
                ++this.off;
                return x & 0xFF;
            }
            throw ByteStringParser$.MODULE$.NeedMoreData();
        }

        public int readShortLE() {
            return this.readByte() | this.readByte() << 8;
        }

        public int readIntLE() {
            return this.readShortLE() | this.readShortLE() << 16;
        }

        public long readLongLE() {
            return (long)this.readIntLE() & 0xFFFFFFFFL | ((long)this.readIntLE() & 0xFFFFFFFFL) << 32;
        }

        public int readShortBE() {
            return this.readByte() << 8 | this.readByte();
        }

        public int readIntBE() {
            return this.readShortBE() << 16 | this.readShortBE();
        }

        public long readLongBE() {
            return ((long)this.readIntBE() & 0xFFFFFFFFL) << 32 | (long)this.readIntBE() & 0xFFFFFFFFL;
        }

        public void skip(int numBytes) {
            if (this.off + numBytes <= this.input.length()) {
                this.off += numBytes;
                return;
            }
            throw ByteStringParser$.MODULE$.NeedMoreData();
        }

        public void skipZeroTerminatedString() {
            while (this.readByte() != 0) {
            }
        }

        public ByteReader(ByteString input) {
            this.input = input;
            this.off = 0;
        }
    }

    public static class ParseResult<T>
    implements Product,
    Serializable {
        private final Option<T> result;
        private final ParseStep<T> nextStep;
        private final boolean acceptUpstreamFinish;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Option<T> result() {
            return this.result;
        }

        public ParseStep<T> nextStep() {
            return this.nextStep;
        }

        public boolean acceptUpstreamFinish() {
            return this.acceptUpstreamFinish;
        }

        public <T> ParseResult<T> copy(Option<T> result, ParseStep<T> nextStep, boolean acceptUpstreamFinish) {
            return new ParseResult<T>(result, nextStep, acceptUpstreamFinish);
        }

        public <T> Option<T> copy$default$1() {
            return this.result();
        }

        public <T> ParseStep<T> copy$default$2() {
            return this.nextStep();
        }

        public <T> boolean copy$default$3() {
            return this.acceptUpstreamFinish();
        }

        @Override
        public String productPrefix() {
            return "ParseResult";
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
                    return this.result();
                }
                case 1: {
                    return this.nextStep();
                }
                case 2: {
                    return BoxesRunTime.boxToBoolean(this.acceptUpstreamFinish());
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
            return x$1 instanceof ParseResult;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "result";
                }
                case 1: {
                    return "nextStep";
                }
                case 2: {
                    return "acceptUpstreamFinish";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.result()));
            n = Statics.mix(n, Statics.anyHash(this.nextStep()));
            n = Statics.mix(n, this.acceptUpstreamFinish() ? 1231 : 1237);
            return Statics.finalizeHash(n, 3);
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
            if (!(object instanceof ParseResult)) return false;
            boolean bl = true;
            if (!bl) return false;
            ParseResult parseResult = (ParseResult)x$1;
            if (this.acceptUpstreamFinish() != parseResult.acceptUpstreamFinish()) return false;
            Option<T> option = this.result();
            Option<T> option2 = parseResult.result();
            if (option == null) {
                if (option2 != null) {
                    return false;
                }
            } else if (!option.equals(option2)) return false;
            ParseStep<T> parseStep = this.nextStep();
            ParseStep<T> parseStep2 = parseResult.nextStep();
            if (parseStep == null) {
                if (parseStep2 != null) {
                    return false;
                }
            } else if (!parseStep.equals(parseStep2)) return false;
            if (!parseResult.canEqual(this)) return false;
            return true;
        }

        public ParseResult(Option<T> result, ParseStep<T> nextStep, boolean acceptUpstreamFinish) {
            this.result = result;
            this.nextStep = nextStep;
            this.acceptUpstreamFinish = acceptUpstreamFinish;
            Product.$init$(this);
        }
    }

    public static interface ParseStep<T> {
        public static /* synthetic */ boolean canWorkWithPartialData$(ParseStep $this) {
            return $this.canWorkWithPartialData();
        }

        default public boolean canWorkWithPartialData() {
            return false;
        }

        public ParseResult<T> parse(ByteReader var1);

        public static /* synthetic */ void onTruncation$(ParseStep $this) {
            $this.onTruncation();
        }

        default public void onTruncation() {
            throw new IllegalStateException("truncated data in ByteStringParser");
        }

        public static void $init$(ParseStep $this) {
        }
    }

    public static class ParsingException
    extends RuntimeException {
        public ParsingException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public class ParsingLogic
    extends GraphStageLogic
    implements InHandler,
    OutHandler {
        private ByteString buffer;
        private ParseStep<T> current;
        private boolean acceptUpstreamFinish;
        private int untilCompact;

        @Override
        public void onDownstreamFinish() throws Exception {
            OutHandler.onDownstreamFinish$(this);
        }

        @Override
        public void onDownstreamFinish(Throwable cause) throws Exception {
            OutHandler.onDownstreamFinish$(this, cause);
        }

        @Override
        public void onUpstreamFailure(Throwable ex) throws Exception {
            InHandler.onUpstreamFailure$(this, ex);
        }

        private ByteString buffer() {
            return this.buffer;
        }

        private void buffer_$eq(ByteString x$1) {
            this.buffer = x$1;
        }

        private ParseStep<T> current() {
            return this.current;
        }

        private void current_$eq(ParseStep<T> x$1) {
            this.current = x$1;
        }

        private boolean acceptUpstreamFinish() {
            return this.acceptUpstreamFinish;
        }

        private void acceptUpstreamFinish_$eq(boolean x$1) {
            this.acceptUpstreamFinish = x$1;
        }

        private int untilCompact() {
            return this.untilCompact;
        }

        private void untilCompact_$eq(int x$1) {
            this.untilCompact = x$1;
        }

        public final void startWith(ParseStep<T> step) {
            this.current_$eq(step);
        }

        public int recursionLimit() {
            return 1000;
        }

        private boolean doParseInner() {
            if (this.buffer().nonEmpty()) {
                boolean bl;
                ByteReader reader = new ByteReader(this.buffer());
                try {
                    ParseResult parseResult = this.current().parse(reader);
                    this.acceptUpstreamFinish_$eq(parseResult.acceptUpstreamFinish());
                    parseResult.result().foreach((Function1<Object, Object> & Serializable)x$1 -> {
                        this.push(this.akka$stream$impl$io$ByteStringParser$ParsingLogic$$$outer().akka$stream$impl$io$ByteStringParser$$objOut(), x$1);
                        return BoxedUnit.UNIT;
                    });
                    ParseStep parseStep = parseResult.nextStep();
                    ByteStringParser$FinishedParser$ byteStringParser$FinishedParser$ = ByteStringParser$FinishedParser$.MODULE$;
                    if (!(parseStep != null ? !parseStep.equals(byteStringParser$FinishedParser$) : byteStringParser$FinishedParser$ != null)) {
                        this.completeStage();
                        bl = false;
                    } else {
                        this.buffer_$eq(reader.remainingData());
                        this.current_$eq(parseResult.nextStep());
                        bl = parseResult.result().isEmpty();
                    }
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    Exception exception = ByteStringParser$.MODULE$.NeedMoreData();
                    Throwable throwable3 = throwable2;
                    if (!(exception != null ? !exception.equals(throwable3) : throwable3 != null)) {
                        this.acceptUpstreamFinish_$eq(false);
                        if (this.current().canWorkWithPartialData()) {
                            this.buffer_$eq(reader.remainingData());
                        }
                        if (this.isClosed(this.akka$stream$impl$io$ByteStringParser$ParsingLogic$$$outer().akka$stream$impl$io$ByteStringParser$$bytesIn())) {
                            this.current().onTruncation();
                        } else {
                            this.pull(this.akka$stream$impl$io$ByteStringParser$ParsingLogic$$$outer().akka$stream$impl$io$ByteStringParser$$bytesIn());
                        }
                        bl = false;
                    }
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        this.failStage(ex);
                        bl = false;
                    }
                    throw throwable;
                }
                return bl;
            }
            if (this.isClosed(this.akka$stream$impl$io$ByteStringParser$ParsingLogic$$$outer().akka$stream$impl$io$ByteStringParser$$bytesIn())) {
                if (this.acceptUpstreamFinish()) {
                    this.completeStage();
                } else {
                    this.current().onTruncation();
                }
            } else {
                this.pull(this.akka$stream$impl$io$ByteStringParser$ParsingLogic$$$outer().akka$stream$impl$io$ByteStringParser$$bytesIn());
            }
            return false;
        }

        private void doParse(int remainingRecursions) {
            block2: {
                BoxedUnit boxedUnit;
                while (true) {
                    if (remainingRecursions == 0) {
                        this.failStage(new IllegalStateException(new StringBuilder(199).append("Parsing logic didn't produce result after ").append(this.recursionLimit()).append(" steps. ").append("Aborting processing to avoid infinite cycles. In the unlikely case that the parsing logic ").append("needs more recursion, override ParsingLogic.recursionLimit.").toString()));
                        boxedUnit = BoxedUnit.UNIT;
                        break block2;
                    }
                    boolean recurse = this.doParseInner();
                    if (!recurse) break;
                    --remainingRecursions;
                }
                boxedUnit = BoxedUnit.UNIT;
            }
        }

        private int doParse$default$1() {
            return this.recursionLimit();
        }

        @Override
        public void onPull() {
            this.doParse(this.doParse$default$1());
        }

        @Override
        public void onPush() {
            this.buffer_$eq(this.buffer().$plus$plus(this.grab(this.akka$stream$impl$io$ByteStringParser$ParsingLogic$$$outer().akka$stream$impl$io$ByteStringParser$$bytesIn())));
            this.untilCompact_$eq(this.untilCompact() - 1);
            if (this.untilCompact() == 0) {
                this.untilCompact_$eq(ByteStringParser$.MODULE$.CompactionThreshold());
                this.buffer_$eq(this.buffer().compact());
            }
            this.doParse(this.doParse$default$1());
        }

        @Override
        public void onUpstreamFinish() {
            if (this.isAvailable(this.akka$stream$impl$io$ByteStringParser$ParsingLogic$$$outer().akka$stream$impl$io$ByteStringParser$$objOut())) {
                this.doParse(this.doParse$default$1());
                return;
            }
            if (this.buffer().isEmpty()) {
                if (this.acceptUpstreamFinish()) {
                    this.completeStage();
                    return;
                }
                this.current().onTruncation();
                return;
            }
        }

        public /* synthetic */ ByteStringParser akka$stream$impl$io$ByteStringParser$ParsingLogic$$$outer() {
            return ByteStringParser.this;
        }

        public ParsingLogic() {
            if (ByteStringParser.this == null) {
                throw null;
            }
            super(ByteStringParser.this.shape());
            InHandler.$init$(this);
            OutHandler.$init$(this);
            this.buffer = ByteString$.MODULE$.empty();
            this.current = ByteStringParser$FinishedParser$.MODULE$;
            this.acceptUpstreamFinish = true;
            this.untilCompact = ByteStringParser$.MODULE$.CompactionThreshold();
            this.setHandlers(ByteStringParser.this.akka$stream$impl$io$ByteStringParser$$bytesIn(), ByteStringParser.this.akka$stream$impl$io$ByteStringParser$$objOut(), this);
        }
    }
}

