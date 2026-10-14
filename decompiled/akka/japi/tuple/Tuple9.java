/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple9$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r-v!\u0002\u00192\u0011\u0003Ad!\u0002\u001e2\u0011\u0003Y\u0004\"\u0002&\u0002\t\u0003Y\u0005\"\u0002'\u0002\t\u0003i\u0005\"CB\u0016\u0003\u0005\u0005I\u0011QB\u0017\u0011%\u0019I'AA\u0001\n\u0003\u001bY\u0007C\u0005\u0004\"\u0006\t\t\u0011\"\u0003\u0004$\u001a!!(\r\"Q\u0011!\u0001wA!f\u0001\n\u0003\t\u0007\u0002C7\b\u0005#\u0005\u000b\u0011\u00022\t\u00119<!Q3A\u0005\u0002=D\u0001b]\u0004\u0003\u0012\u0003\u0006I\u0001\u001d\u0005\ti\u001e\u0011)\u001a!C\u0001k\"A\u0011p\u0002B\tB\u0003%a\u000f\u0003\u0005{\u000f\tU\r\u0011\"\u0001|\u0011!yxA!E!\u0002\u0013a\bBCA\u0001\u000f\tU\r\u0011\"\u0001\u0002\u0004!Q\u00111B\u0004\u0003\u0012\u0003\u0006I!!\u0002\t\u0015\u00055qA!f\u0001\n\u0003\ty\u0001\u0003\u0006\u0002\u0018\u001d\u0011\t\u0012)A\u0005\u0003#A!\"!\u0007\b\u0005+\u0007I\u0011AA\u000e\u0011)\t\u0019c\u0002B\tB\u0003%\u0011Q\u0004\u0005\u000b\u0003K9!Q3A\u0005\u0002\u0005\u001d\u0002BCA\u0018\u000f\tE\t\u0015!\u0003\u0002*!Q\u0011\u0011G\u0004\u0003\u0016\u0004%\t!a\r\t\u0015\u0005mrA!E!\u0002\u0013\t)\u0004\u0003\u0004K\u000f\u0011\u0005\u0011Q\b\u0005\n\u0003':!\u0019!C\u0001\u0003+B\u0001\"a\u0017\bA\u0003%\u0011q\u000b\u0005\n\u0003;:\u0011\u0011!C\u0001\u0003?B\u0011\"a'\b#\u0003%\t!!(\t\u0013\u0005\u001dw!%A\u0005\u0002\u0005%\u0007\"CAq\u000fE\u0005I\u0011AAr\u0011%\tYpBI\u0001\n\u0003\ti\u0010C\u0005\u0003\u0016\u001d\t\n\u0011\"\u0001\u0003\u0018!I!qF\u0004\u0012\u0002\u0013\u0005!\u0011\u0007\u0005\n\u0005\u0013:\u0011\u0013!C\u0001\u0005\u0017B\u0011Ba\u0019\b#\u0003%\tA!\u001a\t\u0013\tut!%A\u0005\u0002\t}\u0004\"\u0003BL\u000f\u0005\u0005I\u0011\tBM\u0011%\u00119kBA\u0001\n\u0003\u0011I\u000bC\u0005\u00032\u001e\t\t\u0011\"\u0001\u00034\"I!\u0011X\u0004\u0002\u0002\u0013\u0005#1\u0018\u0005\n\u0005\u0013<\u0011\u0011!C\u0001\u0005\u0017D\u0011B!6\b\u0003\u0003%\tEa6\t\u0013\tmw!!A\u0005B\tu\u0007\"\u0003Bp\u000f\u0005\u0005I\u0011\tBq\u0011%\u0011\u0019oBA\u0001\n\u0003\u0012)/\u0001\u0004UkBdW-\u000f\u0006\u0003eM\nQ\u0001^;qY\u0016T!\u0001N\u001b\u0002\t)\f\u0007/\u001b\u0006\u0002m\u0005!\u0011m[6b\u0007\u0001\u0001\"!O\u0001\u000e\u0003E\u0012a\u0001V;qY\u0016L4cA\u0001=\u0005B\u0011Q\bQ\u0007\u0002})\tq(A\u0003tG\u0006d\u0017-\u0003\u0002B}\t1\u0011I\\=SK\u001a\u0004\"a\u0011%\u000e\u0003\u0011S!!\u0012$\u0002\u0005%|'\"A$\u0002\t)\fg/Y\u0005\u0003\u0013\u0012\u0013AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtD#\u0001\u001d\u0002\r\r\u0014X-\u0019;f+Mq%q\u001fB~\u0005\u007f\u001c\u0019aa\u0002\u0004\f\r=11CB\f)My5\u0011DB\u000e\u0007;\u0019yb!\t\u0004$\r\u00152qEB\u0015!QItA!>\u0003z\nu8\u0011AB\u0003\u0007\u0013\u0019ia!\u0005\u0004\u0016Uy\u0011\u000bZ9x{\u0006\u001d\u00111CA\u0010\u0003W\t9d\u0005\u0003\byI+\u0006CA\u001fT\u0013\t!fHA\u0004Qe>$Wo\u0019;\u0011\u0005YsfBA,]\u001d\tA6,D\u0001Z\u0015\tQv'\u0001\u0004=e>|GOP\u0005\u0002\u007f%\u0011QLP\u0001\ba\u0006\u001c7.Y4f\u0013\tIuL\u0003\u0002^}\u0005\u0011A/M\u000b\u0002EB\u00111\r\u001a\u0007\u0001\t\u0015)wA1\u0001g\u0005\t!\u0016'\u0005\u0002hUB\u0011Q\b[\u0005\u0003Sz\u0012qAT8uQ&tw\r\u0005\u0002>W&\u0011AN\u0010\u0002\u0004\u0003:L\u0018a\u0001;2A\u0005\u0011AOM\u000b\u0002aB\u00111-\u001d\u0003\u0006e\u001e\u0011\rA\u001a\u0002\u0003)J\n1\u0001\u001e\u001a!\u0003\t!8'F\u0001w!\t\u0019w\u000fB\u0003y\u000f\t\u0007aM\u0001\u0002Ug\u0005\u0019Ao\r\u0011\u0002\u0005Q$T#\u0001?\u0011\u0005\rlH!\u0002@\b\u0005\u00041'A\u0001+5\u0003\r!H\u0007I\u0001\u0003iV*\"!!\u0002\u0011\u0007\r\f9\u0001\u0002\u0004\u0002\n\u001d\u0011\rA\u001a\u0002\u0003)V\n1\u0001^\u001b!\u0003\t!h'\u0006\u0002\u0002\u0012A\u00191-a\u0005\u0005\r\u0005UqA1\u0001g\u0005\t!f'A\u0002um\u0001\n!\u0001^\u001c\u0016\u0005\u0005u\u0001cA2\u0002 \u00111\u0011\u0011E\u0004C\u0002\u0019\u0014!\u0001V\u001c\u0002\u0007Q<\u0004%\u0001\u0002uqU\u0011\u0011\u0011\u0006\t\u0004G\u0006-BABA\u0017\u000f\t\u0007aM\u0001\u0002Uq\u0005\u0019A\u000f\u000f\u0011\u0002\u0005QLTCAA\u001b!\r\u0019\u0017q\u0007\u0003\u0007\u0003s9!\u0019\u00014\u0003\u0005QK\u0014a\u0001;:AQ!\u0012qHA!\u0003\u0007\n)%a\u0012\u0002J\u0005-\u0013QJA(\u0003#\u0002\u0002#O\u0004caZd\u0018QAA\t\u0003;\tI#!\u000e\t\u000b\u0001T\u0002\u0019\u00012\t\u000b9T\u0002\u0019\u00019\t\u000bQT\u0002\u0019\u0001<\t\u000biT\u0002\u0019\u0001?\t\u000f\u0005\u0005!\u00041\u0001\u0002\u0006!9\u0011Q\u0002\u000eA\u0002\u0005E\u0001bBA\r5\u0001\u0007\u0011Q\u0004\u0005\b\u0003KQ\u0002\u0019AA\u0015\u0011\u001d\t\tD\u0007a\u0001\u0003k\tq\u0001^8TG\u0006d\u0017-\u0006\u0002\u0002XA\tR(!\u0017caZd\u0018QAA\t\u0003;\tI#!\u000e\n\u0005ir\u0014\u0001\u0003;p'\u000e\fG.\u0019\u0011\u0002\t\r|\u0007/_\u000b\u0015\u0003C\n9'a\u001b\u0002p\u0005M\u0014qOA>\u0003\u007f\n\u0019)a\"\u0015)\u0005\r\u0014\u0011RAF\u0003\u001b\u000by)!%\u0002\u0014\u0006U\u0015qSAM!QIt!!\u001a\u0002j\u00055\u0014\u0011OA;\u0003s\ni(!!\u0002\u0006B\u00191-a\u001a\u0005\u000b\u0015l\"\u0019\u00014\u0011\u0007\r\fY\u0007B\u0003s;\t\u0007a\rE\u0002d\u0003_\"Q\u0001_\u000fC\u0002\u0019\u00042aYA:\t\u0015qXD1\u0001g!\r\u0019\u0017q\u000f\u0003\u0007\u0003\u0013i\"\u0019\u00014\u0011\u0007\r\fY\b\u0002\u0004\u0002\u0016u\u0011\rA\u001a\t\u0004G\u0006}DABA\u0011;\t\u0007a\rE\u0002d\u0003\u0007#a!!\f\u001e\u0005\u00041\u0007cA2\u0002\b\u00121\u0011\u0011H\u000fC\u0002\u0019D\u0001\u0002Y\u000f\u0011\u0002\u0003\u0007\u0011Q\r\u0005\t]v\u0001\n\u00111\u0001\u0002j!AA/\bI\u0001\u0002\u0004\ti\u0007\u0003\u0005{;A\u0005\t\u0019AA9\u0011%\t\t!\bI\u0001\u0002\u0004\t)\bC\u0005\u0002\u000eu\u0001\n\u00111\u0001\u0002z!I\u0011\u0011D\u000f\u0011\u0002\u0003\u0007\u0011Q\u0010\u0005\n\u0003Ki\u0002\u0013!a\u0001\u0003\u0003C\u0011\"!\r\u001e!\u0003\u0005\r!!\"\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU!\u0012qTA[\u0003o\u000bI,a/\u0002>\u0006}\u0016\u0011YAb\u0003\u000b,\"!!)+\u0007\t\f\u0019k\u000b\u0002\u0002&B!\u0011qUAY\u001b\t\tIK\u0003\u0003\u0002,\u00065\u0016!C;oG\",7m[3e\u0015\r\tyKP\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAZ\u0003S\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015)gD1\u0001g\t\u0015\u0011hD1\u0001g\t\u0015AhD1\u0001g\t\u0015qhD1\u0001g\t\u0019\tIA\bb\u0001M\u00121\u0011Q\u0003\u0010C\u0002\u0019$a!!\t\u001f\u0005\u00041GABA\u0017=\t\u0007a\r\u0002\u0004\u0002:y\u0011\rAZ\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+Q\tY-a4\u0002R\u0006M\u0017Q[Al\u00033\fY.!8\u0002`V\u0011\u0011Q\u001a\u0016\u0004a\u0006\rF!B3 \u0005\u00041G!\u0002: \u0005\u00041G!\u0002= \u0005\u00041G!\u0002@ \u0005\u00041GABA\u0005?\t\u0007a\r\u0002\u0004\u0002\u0016}\u0011\rA\u001a\u0003\u0007\u0003Cy\"\u0019\u00014\u0005\r\u00055rD1\u0001g\t\u0019\tId\bb\u0001M\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aT\u0003FAs\u0003S\fY/!<\u0002p\u0006E\u00181_A{\u0003o\fI0\u0006\u0002\u0002h*\u001aa/a)\u0005\u000b\u0015\u0004#\u0019\u00014\u0005\u000bI\u0004#\u0019\u00014\u0005\u000ba\u0004#\u0019\u00014\u0005\u000by\u0004#\u0019\u00014\u0005\r\u0005%\u0001E1\u0001g\t\u0019\t)\u0002\tb\u0001M\u00121\u0011\u0011\u0005\u0011C\u0002\u0019$a!!\f!\u0005\u00041GABA\u001dA\t\u0007a-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016)\u0005}(1\u0001B\u0003\u0005\u000f\u0011IAa\u0003\u0003\u000e\t=!\u0011\u0003B\n+\t\u0011\tAK\u0002}\u0003G#Q!Z\u0011C\u0002\u0019$QA]\u0011C\u0002\u0019$Q\u0001_\u0011C\u0002\u0019$QA`\u0011C\u0002\u0019$a!!\u0003\"\u0005\u00041GABA\u000bC\t\u0007a\r\u0002\u0004\u0002\"\u0005\u0012\rA\u001a\u0003\u0007\u0003[\t#\u0019\u00014\u0005\r\u0005e\u0012E1\u0001g\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIU*BC!\u0007\u0003\u001e\t}!\u0011\u0005B\u0012\u0005K\u00119C!\u000b\u0003,\t5RC\u0001B\u000eU\u0011\t)!a)\u0005\u000b\u0015\u0014#\u0019\u00014\u0005\u000bI\u0014#\u0019\u00014\u0005\u000ba\u0014#\u0019\u00014\u0005\u000by\u0014#\u0019\u00014\u0005\r\u0005%!E1\u0001g\t\u0019\t)B\tb\u0001M\u00121\u0011\u0011\u0005\u0012C\u0002\u0019$a!!\f#\u0005\u00041GABA\u001dE\t\u0007a-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001c\u0016)\tM\"q\u0007B\u001d\u0005w\u0011iDa\u0010\u0003B\t\r#Q\tB$+\t\u0011)D\u000b\u0003\u0002\u0012\u0005\rF!B3$\u0005\u00041G!\u0002:$\u0005\u00041G!\u0002=$\u0005\u00041G!\u0002@$\u0005\u00041GABA\u0005G\t\u0007a\r\u0002\u0004\u0002\u0016\r\u0012\rA\u001a\u0003\u0007\u0003C\u0019#\u0019\u00014\u0005\r\u000552E1\u0001g\t\u0019\tId\tb\u0001M\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012:T\u0003\u0006B'\u0005#\u0012\u0019F!\u0016\u0003X\te#1\fB/\u0005?\u0012\t'\u0006\u0002\u0003P)\"\u0011QDAR\t\u0015)GE1\u0001g\t\u0015\u0011HE1\u0001g\t\u0015AHE1\u0001g\t\u0015qHE1\u0001g\t\u0019\tI\u0001\nb\u0001M\u00121\u0011Q\u0003\u0013C\u0002\u0019$a!!\t%\u0005\u00041GABA\u0017I\t\u0007a\r\u0002\u0004\u0002:\u0011\u0012\rAZ\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139+Q\u00119Ga\u001b\u0003n\t=$\u0011\u000fB:\u0005k\u00129H!\u001f\u0003|U\u0011!\u0011\u000e\u0016\u0005\u0003S\t\u0019\u000bB\u0003fK\t\u0007a\rB\u0003sK\t\u0007a\rB\u0003yK\t\u0007a\rB\u0003\u007fK\t\u0007a\r\u0002\u0004\u0002\n\u0015\u0012\rA\u001a\u0003\u0007\u0003+)#\u0019\u00014\u0005\r\u0005\u0005RE1\u0001g\t\u0019\ti#\nb\u0001M\u00121\u0011\u0011H\u0013C\u0002\u0019\fabY8qs\u0012\"WMZ1vYR$\u0013(\u0006\u000b\u0003\u0002\n\u0015%q\u0011BE\u0005\u0017\u0013iIa$\u0003\u0012\nM%QS\u000b\u0003\u0005\u0007SC!!\u000e\u0002$\u0012)QM\nb\u0001M\u0012)!O\nb\u0001M\u0012)\u0001P\nb\u0001M\u0012)aP\nb\u0001M\u00121\u0011\u0011\u0002\u0014C\u0002\u0019$a!!\u0006'\u0005\u00041GABA\u0011M\t\u0007a\r\u0002\u0004\u0002.\u0019\u0012\rA\u001a\u0003\u0007\u0003s1#\u0019\u00014\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011Y\n\u0005\u0003\u0003\u001e\n\rVB\u0001BP\u0015\r\u0011\tKR\u0001\u0005Y\u0006tw-\u0003\u0003\u0003&\n}%AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0003,B\u0019QH!,\n\u0007\t=fHA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002k\u0005kC\u0011Ba.*\u0003\u0003\u0005\rAa+\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0011i\fE\u0003\u0003@\n\u0015'.\u0004\u0002\u0003B*\u0019!1\u0019 \u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0003H\n\u0005'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$BA!4\u0003TB\u0019QHa4\n\u0007\tEgHA\u0004C_>dW-\u00198\t\u0011\t]6&!AA\u0002)\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!!1\u0014Bm\u0011%\u00119\fLA\u0001\u0002\u0004\u0011Y+\u0001\u0005iCND7i\u001c3f)\t\u0011Y+\u0001\u0005u_N#(/\u001b8h)\t\u0011Y*\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0005\u001b\u00149\u000f\u0003\u0005\u00038>\n\t\u00111\u0001kQ\u001d9!1\u001eBy\u0005g\u00042!\u0010Bw\u0013\r\u0011yO\u0010\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001\t\u0004G\n]H!B3\u0004\u0005\u00041\u0007cA2\u0003|\u0012)!o\u0001b\u0001MB\u00191Ma@\u0005\u000ba\u001c!\u0019\u00014\u0011\u0007\r\u001c\u0019\u0001B\u0003\u007f\u0007\t\u0007a\rE\u0002d\u0007\u000f!a!!\u0003\u0004\u0005\u00041\u0007cA2\u0004\f\u00111\u0011QC\u0002C\u0002\u0019\u00042aYB\b\t\u0019\t\tc\u0001b\u0001MB\u00191ma\u0005\u0005\r\u000552A1\u0001g!\r\u00197q\u0003\u0003\u0007\u0003s\u0019!\u0019\u00014\t\r\u0001\u001c\u0001\u0019\u0001B{\u0011\u0019q7\u00011\u0001\u0003z\"1Ao\u0001a\u0001\u0005{DaA_\u0002A\u0002\r\u0005\u0001bBA\u0001\u0007\u0001\u00071Q\u0001\u0005\b\u0003\u001b\u0019\u0001\u0019AB\u0005\u0011\u001d\tIb\u0001a\u0001\u0007\u001bAq!!\n\u0004\u0001\u0004\u0019\t\u0002C\u0004\u00022\r\u0001\ra!\u0006\u0002\u000b\u0005\u0004\b\u000f\\=\u0016)\r=2QGB\u001d\u0007{\u0019\te!\u0012\u0004J\r53\u0011KB+)Q\u0019\tda\u0016\u0004Z\rm3QLB0\u0007C\u001a\u0019g!\u001a\u0004hA!\u0012hBB\u001a\u0007o\u0019Yda\u0010\u0004D\r\u001d31JB(\u0007'\u00022aYB\u001b\t\u0015)GA1\u0001g!\r\u00197\u0011\b\u0003\u0006e\u0012\u0011\rA\u001a\t\u0004G\u000euB!\u0002=\u0005\u0005\u00041\u0007cA2\u0004B\u0011)a\u0010\u0002b\u0001MB\u00191m!\u0012\u0005\r\u0005%AA1\u0001g!\r\u00197\u0011\n\u0003\u0007\u0003+!!\u0019\u00014\u0011\u0007\r\u001ci\u0005\u0002\u0004\u0002\"\u0011\u0011\rA\u001a\t\u0004G\u000eECABA\u0017\t\t\u0007a\rE\u0002d\u0007+\"a!!\u000f\u0005\u0005\u00041\u0007B\u00021\u0005\u0001\u0004\u0019\u0019\u0004\u0003\u0004o\t\u0001\u00071q\u0007\u0005\u0007i\u0012\u0001\raa\u000f\t\ri$\u0001\u0019AB \u0011\u001d\t\t\u0001\u0002a\u0001\u0007\u0007Bq!!\u0004\u0005\u0001\u0004\u00199\u0005C\u0004\u0002\u001a\u0011\u0001\raa\u0013\t\u000f\u0005\u0015B\u00011\u0001\u0004P!9\u0011\u0011\u0007\u0003A\u0002\rM\u0013aB;oCB\u0004H._\u000b\u0015\u0007[\u001aIh! \u0004\u0002\u000e\u00155\u0011RBG\u0007#\u001b)j!'\u0015\t\r=41\u0014\t\u0006{\rE4QO\u0005\u0004\u0007gr$AB(qi&|g\u000eE\u000b>\u00033\u001a9ha\u001f\u0004\u0000\r\r5qQBF\u0007\u001f\u001b\u0019ja&\u0011\u0007\r\u001cI\bB\u0003f\u000b\t\u0007a\rE\u0002d\u0007{\"QA]\u0003C\u0002\u0019\u00042aYBA\t\u0015AXA1\u0001g!\r\u00197Q\u0011\u0003\u0006}\u0016\u0011\rA\u001a\t\u0004G\u000e%EABA\u0005\u000b\t\u0007a\rE\u0002d\u0007\u001b#a!!\u0006\u0006\u0005\u00041\u0007cA2\u0004\u0012\u00121\u0011\u0011E\u0003C\u0002\u0019\u00042aYBK\t\u0019\ti#\u0002b\u0001MB\u00191m!'\u0005\r\u0005eRA1\u0001g\u0011%\u0019i*BA\u0001\u0002\u0004\u0019y*A\u0002yIA\u0002B#O\u0004\u0004x\rm4qPBB\u0007\u000f\u001bYia$\u0004\u0014\u000e]\u0015\u0001D<sSR,'+\u001a9mC\u000e,GCABS!\u0011\u0011ija*\n\t\r%&q\u0014\u0002\u0007\u001f\nTWm\u0019;")
public final class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
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
    private final scala.Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> toScala;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Option<scala.Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> unapply(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> x$0) {
        return Tuple9$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
        return Tuple9$.MODULE$.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
        return Tuple9$.MODULE$.create(t1, t2, t3, t4, t5, t6, t7, t8, t9);
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

    public scala.Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
        return new Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T6 copy$default$6() {
        return this.t6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T7 copy$default$7() {
        return this.t7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T8 copy$default$8() {
        return this.t8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> T9 copy$default$9() {
        return this.t9();
    }

    @Override
    public String productPrefix() {
        return "Tuple9";
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
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Tuple9;
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
        if (!(object instanceof Tuple9)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple9 tuple9 = (Tuple9)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple9.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple9.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple9.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple9.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple9.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple9.t6())) return false;
        if (!BoxesRunTime.equals(this.t7(), tuple9.t7())) return false;
        if (!BoxesRunTime.equals(this.t8(), tuple9.t8())) return false;
        if (!BoxesRunTime.equals(this.t9(), tuple9.t9())) return false;
        return true;
    }

    public Tuple9(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
        Product.$init$(this);
        this.toScala = new scala.Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }
}

