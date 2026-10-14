/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product11;
import scala.Tuple11$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\rMh\u0001\u0002\u001a4\u0005ZB\u0001b\u001e\u0001\u0003\u0016\u0004%\t\u0001\u001f\u0005\ts\u0002\u0011\t\u0012)A\u0005\u007f!A!\u0010\u0001BK\u0002\u0013\u00051\u0010\u0003\u0005}\u0001\tE\t\u0015!\u0003K\u0011!i\bA!f\u0001\n\u0003q\b\u0002C@\u0001\u0005#\u0005\u000b\u0011B'\t\u0015\u0005\u0005\u0001A!f\u0001\n\u0003\t\u0019\u0001C\u0005\u0002\u0006\u0001\u0011\t\u0012)A\u0005!\"Q\u0011q\u0001\u0001\u0003\u0016\u0004%\t!!\u0003\t\u0013\u0005-\u0001A!E!\u0002\u0013\u0019\u0006BCA\u0007\u0001\tU\r\u0011\"\u0001\u0002\u0010!I\u0011\u0011\u0003\u0001\u0003\u0012\u0003\u0006IA\u0016\u0005\u000b\u0003'\u0001!Q3A\u0005\u0002\u0005U\u0001\"CA\f\u0001\tE\t\u0015!\u0003Z\u0011)\tI\u0002\u0001BK\u0002\u0013\u0005\u00111\u0004\u0005\n\u0003;\u0001!\u0011#Q\u0001\nqC!\"a\b\u0001\u0005+\u0007I\u0011AA\u0011\u0011%\t\u0019\u0003\u0001B\tB\u0003%q\f\u0003\u0006\u0002&\u0001\u0011)\u001a!C\u0001\u0003OA\u0011\"!\u000b\u0001\u0005#\u0005\u000b\u0011\u00022\t\u0015\u0005-\u0002A!f\u0001\n\u0003\ti\u0003C\u0005\u00020\u0001\u0011\t\u0012)A\u0005K\"9\u0011\u0011\u0007\u0001\u0005\u0002\u0005M\u0002bBA'\u0001\u0011\u0005\u0013q\n\u0005\n\u0003C\u0002\u0011\u0011!C\u0001\u0003GB\u0011\"a+\u0001#\u0003%\t!!,\t\u0013\u0005m\u0007!%A\u0005\u0002\u0005u\u0007\"CA}\u0001E\u0005I\u0011AA~\u0011%\u00119\u0002AI\u0001\n\u0003\u0011I\u0002C\u0005\u00036\u0001\t\n\u0011\"\u0001\u00038!I!1\u000b\u0001\u0012\u0002\u0013\u0005!Q\u000b\u0005\n\u0005c\u0002\u0011\u0013!C\u0001\u0005gB\u0011Ba$\u0001#\u0003%\tA!%\t\u0013\t5\u0006!%A\u0005\u0002\t=\u0006\"\u0003Bf\u0001E\u0005I\u0011\u0001Bg\u0011%\u0011I\u000fAI\u0001\n\u0003\u0011Y\u000fC\u0005\u0004\b\u0001\t\t\u0011\"\u0011\u0004\n!I1\u0011\u0004\u0001\u0002\u0002\u0013\u000531\u0004\u0005\n\u0007S\u0001\u0011\u0011!C\u0001\u0007WA\u0011ba\u000e\u0001\u0003\u0003%\te!\u000f\t\u0013\r\r\u0003!!A\u0005B\r\u0015\u0003\"CB$\u0001\u0005\u0005I\u0011IB%\u000f%\u0019ieMA\u0001\u0012\u0003\u0019yE\u0002\u00053g\u0005\u0005\t\u0012AB)\u0011\u001d\t\t\u0004\fC\u0001\u0007;B\u0011\"!\u0014-\u0003\u0003%)ea\u0018\t\u0013\r\u0005D&!A\u0005\u0002\u000e\r\u0004\"CBVY\u0005\u0005I\u0011QBW\u0011%\u0019I\u000fLA\u0001\n\u0013\u0019YOA\u0004UkBdW-M\u0019\u000b\u0003Q\nQa]2bY\u0006\u001c\u0001!\u0006\u00078\u0003.s\u0015\u000bV,[;\u0002\u001cgmE\u0003\u0001qqB7\u000e\u0005\u0002:u5\t1'\u0003\u0002<g\t1\u0011I\\=SK\u001a\u0004R\"O\u001f@\u00156\u00036KV-]?\n,\u0017B\u0001 4\u0005%\u0001&o\u001c3vGR\f\u0014\u0007\u0005\u0002A\u00032\u0001AA\u0002\"\u0001\t\u000b\u00071I\u0001\u0002UcE\u0011Ai\u0012\t\u0003s\u0015K!AR\u001a\u0003\u000f9{G\u000f[5oOB\u0011\u0011\bS\u0005\u0003\u0013N\u00121!\u00118z!\t\u00015\n\u0002\u0004M\u0001\u0011\u0015\ra\u0011\u0002\u0003)J\u0002\"\u0001\u0011(\u0005\r=\u0003AQ1\u0001D\u0005\t!6\u0007\u0005\u0002A#\u00121!\u000b\u0001CC\u0002\r\u0013!\u0001\u0016\u001b\u0011\u0005\u0001#FAB+\u0001\t\u000b\u00071I\u0001\u0002UkA\u0011\u0001i\u0016\u0003\u00071\u0002!)\u0019A\"\u0003\u0005Q3\u0004C\u0001![\t\u0019Y\u0006\u0001\"b\u0001\u0007\n\u0011Ak\u000e\t\u0003\u0001v#aA\u0018\u0001\u0005\u0006\u0004\u0019%A\u0001+9!\t\u0001\u0005\r\u0002\u0004b\u0001\u0011\u0015\ra\u0011\u0002\u0003)f\u0002\"\u0001Q2\u0005\r\u0011\u0004AQ1\u0001D\u0005\r!\u0016\u0007\r\t\u0003\u0001\u001a$aa\u001a\u0001\u0005\u0006\u0004\u0019%a\u0001+2cA\u0011\u0011([\u0005\u0003UN\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002mi:\u0011QN\u001d\b\u0003]Fl\u0011a\u001c\u0006\u0003aV\na\u0001\u0010:p_Rt\u0014\"\u0001\u001b\n\u0005M\u001c\u0014a\u00029bG.\fw-Z\u0005\u0003kZ\u0014AbU3sS\u0006d\u0017N_1cY\u0016T!a]\u001a\u0002\u0005}\u000bT#A \u0002\u0007}\u000b\u0004%\u0001\u0002`eU\t!*A\u0002`e\u0001\n!aX\u001a\u0016\u00035\u000b1aX\u001a!\u0003\tyF'F\u0001Q\u0003\ryF\u0007I\u0001\u0003?V*\u0012aU\u0001\u0004?V\u0002\u0013AA07+\u00051\u0016aA07A\u0005\u0011qlN\u000b\u00023\u0006\u0019ql\u000e\u0011\u0002\u0005}CT#\u0001/\u0002\u0007}C\u0004%\u0001\u0002`sU\tq,A\u0002`s\u0001\n1aX\u00191+\u0005\u0011\u0017\u0001B02a\u0001\n1aX\u00192+\u0005)\u0017\u0001B02c\u0001\na\u0001P5oSRtD\u0003GA\u001b\u0003o\tI$a\u000f\u0002>\u0005}\u0012\u0011IA\"\u0003\u000b\n9%!\u0013\u0002LAi\u0011\bA K\u001bB\u001bf+\u0017/`E\u0016DQa^\fA\u0002}BQA_\fA\u0002)CQ!`\fA\u00025Ca!!\u0001\u0018\u0001\u0004\u0001\u0006BBA\u0004/\u0001\u00071\u000b\u0003\u0004\u0002\u000e]\u0001\rA\u0016\u0005\u0007\u0003'9\u0002\u0019A-\t\r\u0005eq\u00031\u0001]\u0011\u0019\tyb\u0006a\u0001?\"1\u0011QE\fA\u0002\tDa!a\u000b\u0018\u0001\u0004)\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005E\u0003\u0003BA*\u00037rA!!\u0016\u0002XA\u0011anM\u0005\u0004\u00033\u001a\u0014A\u0002)sK\u0012,g-\u0003\u0003\u0002^\u0005}#AB*ue&twMC\u0002\u0002ZM\nAaY8qsVA\u0012QMA6\u0003_\n\u0019(a\u001e\u0002|\u0005}\u00141QAD\u0003\u0017\u000by)a%\u00151\u0005\u001d\u0014QSAL\u00033\u000bY*!(\u0002 \u0006\u0005\u00161UAS\u0003O\u000bI\u000b\u0005\r:\u0001\u0005%\u0014QNA9\u0003k\nI(! \u0002\u0002\u0006\u0015\u0015\u0011RAG\u0003#\u00032\u0001QA6\t\u0015\u0011\u0015D1\u0001D!\r\u0001\u0015q\u000e\u0003\u0006\u0019f\u0011\ra\u0011\t\u0004\u0001\u0006MD!B(\u001a\u0005\u0004\u0019\u0005c\u0001!\u0002x\u0011)!+\u0007b\u0001\u0007B\u0019\u0001)a\u001f\u0005\u000bUK\"\u0019A\"\u0011\u0007\u0001\u000by\bB\u0003Y3\t\u00071\tE\u0002A\u0003\u0007#QaW\rC\u0002\r\u00032\u0001QAD\t\u0015q\u0016D1\u0001D!\r\u0001\u00151\u0012\u0003\u0006Cf\u0011\ra\u0011\t\u0004\u0001\u0006=E!\u00023\u001a\u0005\u0004\u0019\u0005c\u0001!\u0002\u0014\u0012)q-\u0007b\u0001\u0007\"Aq/\u0007I\u0001\u0002\u0004\tI\u0007\u0003\u0005{3A\u0005\t\u0019AA7\u0011!i\u0018\u0004%AA\u0002\u0005E\u0004\"CA\u00013A\u0005\t\u0019AA;\u0011%\t9!\u0007I\u0001\u0002\u0004\tI\bC\u0005\u0002\u000ee\u0001\n\u00111\u0001\u0002~!I\u00111C\r\u0011\u0002\u0003\u0007\u0011\u0011\u0011\u0005\n\u00033I\u0002\u0013!a\u0001\u0003\u000bC\u0011\"a\b\u001a!\u0003\u0005\r!!#\t\u0013\u0005\u0015\u0012\u0004%AA\u0002\u00055\u0005\"CA\u00163A\u0005\t\u0019AAI\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0002$a,\u0002F\u0006\u001d\u0017\u0011ZAf\u0003\u001b\fy-!5\u0002T\u0006U\u0017q[Am+\t\t\tLK\u0002@\u0003g[#!!.\u0011\t\u0005]\u0016\u0011Y\u0007\u0003\u0003sSA!a/\u0002>\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u007f\u001b\u0014AC1o]>$\u0018\r^5p]&!\u00111YA]\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006\u0005j\u0011\ra\u0011\u0003\u0006\u0019j\u0011\ra\u0011\u0003\u0006\u001fj\u0011\ra\u0011\u0003\u0006%j\u0011\ra\u0011\u0003\u0006+j\u0011\ra\u0011\u0003\u00061j\u0011\ra\u0011\u0003\u00067j\u0011\ra\u0011\u0003\u0006=j\u0011\ra\u0011\u0003\u0006Cj\u0011\ra\u0011\u0003\u0006Ij\u0011\ra\u0011\u0003\u0006Oj\u0011\raQ\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+a\ty.a9\u0002f\u0006\u001d\u0018\u0011^Av\u0003[\fy/!=\u0002t\u0006U\u0018q_\u000b\u0003\u0003CT3ASAZ\t\u0015\u00115D1\u0001D\t\u0015a5D1\u0001D\t\u0015y5D1\u0001D\t\u0015\u00116D1\u0001D\t\u0015)6D1\u0001D\t\u0015A6D1\u0001D\t\u0015Y6D1\u0001D\t\u0015q6D1\u0001D\t\u0015\t7D1\u0001D\t\u0015!7D1\u0001D\t\u001597D1\u0001D\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\u0002$!@\u0003\u0002\t\r!Q\u0001B\u0004\u0005\u0013\u0011YA!\u0004\u0003\u0010\tE!1\u0003B\u000b+\t\tyPK\u0002N\u0003g#QA\u0011\u000fC\u0002\r#Q\u0001\u0014\u000fC\u0002\r#Qa\u0014\u000fC\u0002\r#QA\u0015\u000fC\u0002\r#Q!\u0016\u000fC\u0002\r#Q\u0001\u0017\u000fC\u0002\r#Qa\u0017\u000fC\u0002\r#QA\u0018\u000fC\u0002\r#Q!\u0019\u000fC\u0002\r#Q\u0001\u001a\u000fC\u0002\r#Qa\u001a\u000fC\u0002\r\u000babY8qs\u0012\"WMZ1vYR$C'\u0006\r\u0003\u001c\t}!\u0011\u0005B\u0012\u0005K\u00119C!\u000b\u0003,\t5\"q\u0006B\u0019\u0005g)\"A!\b+\u0007A\u000b\u0019\fB\u0003C;\t\u00071\tB\u0003M;\t\u00071\tB\u0003P;\t\u00071\tB\u0003S;\t\u00071\tB\u0003V;\t\u00071\tB\u0003Y;\t\u00071\tB\u0003\\;\t\u00071\tB\u0003_;\t\u00071\tB\u0003b;\t\u00071\tB\u0003e;\t\u00071\tB\u0003h;\t\u00071)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u00161\te\"Q\bB \u0005\u0003\u0012\u0019E!\u0012\u0003H\t%#1\nB'\u0005\u001f\u0012\t&\u0006\u0002\u0003<)\u001a1+a-\u0005\u000b\ts\"\u0019A\"\u0005\u000b1s\"\u0019A\"\u0005\u000b=s\"\u0019A\"\u0005\u000bIs\"\u0019A\"\u0005\u000bUs\"\u0019A\"\u0005\u000bas\"\u0019A\"\u0005\u000bms\"\u0019A\"\u0005\u000bys\"\u0019A\"\u0005\u000b\u0005t\"\u0019A\"\u0005\u000b\u0011t\"\u0019A\"\u0005\u000b\u001dt\"\u0019A\"\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%mUA\"q\u000bB.\u0005;\u0012yF!\u0019\u0003d\t\u0015$q\rB5\u0005W\u0012iGa\u001c\u0016\u0005\te#f\u0001,\u00024\u0012)!i\bb\u0001\u0007\u0012)Aj\bb\u0001\u0007\u0012)qj\bb\u0001\u0007\u0012)!k\bb\u0001\u0007\u0012)Qk\bb\u0001\u0007\u0012)\u0001l\bb\u0001\u0007\u0012)1l\bb\u0001\u0007\u0012)al\bb\u0001\u0007\u0012)\u0011m\bb\u0001\u0007\u0012)Am\bb\u0001\u0007\u0012)qm\bb\u0001\u0007\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012:T\u0003\u0007B;\u0005s\u0012YH! \u0003\u0000\t\u0005%1\u0011BC\u0005\u000f\u0013IIa#\u0003\u000eV\u0011!q\u000f\u0016\u00043\u0006MF!\u0002\"!\u0005\u0004\u0019E!\u0002'!\u0005\u0004\u0019E!B(!\u0005\u0004\u0019E!\u0002*!\u0005\u0004\u0019E!B+!\u0005\u0004\u0019E!\u0002-!\u0005\u0004\u0019E!B.!\u0005\u0004\u0019E!\u00020!\u0005\u0004\u0019E!B1!\u0005\u0004\u0019E!\u00023!\u0005\u0004\u0019E!B4!\u0005\u0004\u0019\u0015AD2paf$C-\u001a4bk2$H\u0005O\u000b\u0019\u0005'\u00139J!'\u0003\u001c\nu%q\u0014BQ\u0005G\u0013)Ka*\u0003*\n-VC\u0001BKU\ra\u00161\u0017\u0003\u0006\u0005\u0006\u0012\ra\u0011\u0003\u0006\u0019\u0006\u0012\ra\u0011\u0003\u0006\u001f\u0006\u0012\ra\u0011\u0003\u0006%\u0006\u0012\ra\u0011\u0003\u0006+\u0006\u0012\ra\u0011\u0003\u00061\u0006\u0012\ra\u0011\u0003\u00067\u0006\u0012\ra\u0011\u0003\u0006=\u0006\u0012\ra\u0011\u0003\u0006C\u0006\u0012\ra\u0011\u0003\u0006I\u0006\u0012\ra\u0011\u0003\u0006O\u0006\u0012\raQ\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u0013:+a\u0011\tL!.\u00038\ne&1\u0018B_\u0005\u007f\u0013\tMa1\u0003F\n\u001d'\u0011Z\u000b\u0003\u0005gS3aXAZ\t\u0015\u0011%E1\u0001D\t\u0015a%E1\u0001D\t\u0015y%E1\u0001D\t\u0015\u0011&E1\u0001D\t\u0015)&E1\u0001D\t\u0015A&E1\u0001D\t\u0015Y&E1\u0001D\t\u0015q&E1\u0001D\t\u0015\t'E1\u0001D\t\u0015!'E1\u0001D\t\u00159'E1\u0001D\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\u0002T\u0003\u0007Bh\u0005'\u0014)Na6\u0003Z\nm'Q\u001cBp\u0005C\u0014\u0019O!:\u0003hV\u0011!\u0011\u001b\u0016\u0004E\u0006MF!\u0002\"$\u0005\u0004\u0019E!\u0002'$\u0005\u0004\u0019E!B($\u0005\u0004\u0019E!\u0002*$\u0005\u0004\u0019E!B+$\u0005\u0004\u0019E!\u0002-$\u0005\u0004\u0019E!B.$\u0005\u0004\u0019E!\u00020$\u0005\u0004\u0019E!B1$\u0005\u0004\u0019E!\u00023$\u0005\u0004\u0019E!B4$\u0005\u0004\u0019\u0015aD2paf$C-\u001a4bk2$H%M\u0019\u00161\t5(\u0011\u001fBz\u0005k\u00149P!?\u0003|\nu(q`B\u0001\u0007\u0007\u0019)!\u0006\u0002\u0003p*\u001aQ-a-\u0005\u000b\t##\u0019A\"\u0005\u000b1##\u0019A\"\u0005\u000b=##\u0019A\"\u0005\u000bI##\u0019A\"\u0005\u000bU##\u0019A\"\u0005\u000ba##\u0019A\"\u0005\u000bm##\u0019A\"\u0005\u000by##\u0019A\"\u0005\u000b\u0005$#\u0019A\"\u0005\u000b\u0011$#\u0019A\"\u0005\u000b\u001d$#\u0019A\"\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0019Y\u0001\u0005\u0003\u0004\u000e\r]QBAB\b\u0015\u0011\u0019\tba\u0005\u0002\t1\fgn\u001a\u0006\u0003\u0007+\tAA[1wC&!\u0011QLB\b\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAB\u000f!\u0015\u0019yb!\nH\u001b\t\u0019\tCC\u0002\u0004$M\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\u00199c!\t\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0007[\u0019\u0019\u0004E\u0002:\u0007_I1a!\r4\u0005\u001d\u0011un\u001c7fC:D\u0001b!\u000e(\u0003\u0003\u0005\raR\u0001\u0004q\u0012\n\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$Baa\u0003\u0004<!I1Q\u0007\u0015\u0002\u0002\u0003\u00071Q\b\t\u0004s\r}\u0012bAB!g\t\u0019\u0011J\u001c;\u0002\u0011!\f7\u000f[\"pI\u0016$\"a!\u0010\u0002\r\u0015\fX/\u00197t)\u0011\u0019ica\u0013\t\u0011\rU\"&!AA\u0002\u001d\u000bq\u0001V;qY\u0016\f\u0014\u0007\u0005\u0002:YM!A\u0006OB*!\u0011\u0019)fa\u0017\u000e\u0005\r]#\u0002BB-\u0007'\t!![8\n\u0007U\u001c9\u0006\u0006\u0002\u0004PQ\u001111B\u0001\u0006CB\u0004H._\u000b\u0019\u0007K\u001aYga\u001c\u0004t\r]41PB@\u0007\u0007\u001b9ia#\u0004\u0010\u000eME\u0003GB4\u0007+\u001b9j!'\u0004\u001c\u000eu5qTBQ\u0007G\u001b)ka*\u0004*BA\u0012\bAB5\u0007[\u001a\th!\u001e\u0004z\ru4\u0011QBC\u0007\u0013\u001bii!%\u0011\u0007\u0001\u001bY\u0007B\u0003C_\t\u00071\tE\u0002A\u0007_\"Q\u0001T\u0018C\u0002\r\u00032\u0001QB:\t\u0015yuF1\u0001D!\r\u00015q\u000f\u0003\u0006%>\u0012\ra\u0011\t\u0004\u0001\u000emD!B+0\u0005\u0004\u0019\u0005c\u0001!\u0004\u0000\u0011)\u0001l\fb\u0001\u0007B\u0019\u0001ia!\u0005\u000bm{#\u0019A\"\u0011\u0007\u0001\u001b9\tB\u0003__\t\u00071\tE\u0002A\u0007\u0017#Q!Y\u0018C\u0002\r\u00032\u0001QBH\t\u0015!wF1\u0001D!\r\u000151\u0013\u0003\u0006O>\u0012\ra\u0011\u0005\u0007o>\u0002\ra!\u001b\t\ri|\u0003\u0019AB7\u0011\u0019ix\u00061\u0001\u0004r!9\u0011\u0011A\u0018A\u0002\rU\u0004bBA\u0004_\u0001\u00071\u0011\u0010\u0005\b\u0003\u001by\u0003\u0019AB?\u0011\u001d\t\u0019b\fa\u0001\u0007\u0003Cq!!\u00070\u0001\u0004\u0019)\tC\u0004\u0002 =\u0002\ra!#\t\u000f\u0005\u0015r\u00061\u0001\u0004\u000e\"9\u00111F\u0018A\u0002\rE\u0015aB;oCB\u0004H._\u000b\u0019\u0007_\u001bYla0\u0004D\u000e\u001d71ZBh\u0007'\u001c9na7\u0004`\u000e\rH\u0003BBY\u0007K\u0004R!OBZ\u0007oK1a!.4\u0005\u0019y\u0005\u000f^5p]BA\u0012\bAB]\u0007{\u001b\tm!2\u0004J\u000e57\u0011[Bk\u00073\u001cin!9\u0011\u0007\u0001\u001bY\fB\u0003Ca\t\u00071\tE\u0002A\u0007\u007f#Q\u0001\u0014\u0019C\u0002\r\u00032\u0001QBb\t\u0015y\u0005G1\u0001D!\r\u00015q\u0019\u0003\u0006%B\u0012\ra\u0011\t\u0004\u0001\u000e-G!B+1\u0005\u0004\u0019\u0005c\u0001!\u0004P\u0012)\u0001\f\rb\u0001\u0007B\u0019\u0001ia5\u0005\u000bm\u0003$\u0019A\"\u0011\u0007\u0001\u001b9\u000eB\u0003_a\t\u00071\tE\u0002A\u00077$Q!\u0019\u0019C\u0002\r\u00032\u0001QBp\t\u0015!\u0007G1\u0001D!\r\u000151\u001d\u0003\u0006OB\u0012\ra\u0011\u0005\n\u0007O\u0004\u0014\u0011!a\u0001\u0007o\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0019i\u000f\u0005\u0003\u0004\u000e\r=\u0018\u0002BBy\u0007\u001f\u0011aa\u00142kK\u000e$\b")
public final class Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
implements Product11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;
    private final T5 _5;
    private final T6 _6;
    private final T7 _7;
    private final T8 _8;
    private final T9 _9;
    private final T10 _10;
    private final T11 _11;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Option<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> unapply(Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> x$0) {
        return Tuple11$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11) {
        return new Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11);
    }

    @Override
    public int productArity() {
        return Product11.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product11.productElement$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public T1 _1() {
        return this._1;
    }

    @Override
    public T2 _2() {
        return this._2;
    }

    @Override
    public T3 _3() {
        return this._3;
    }

    @Override
    public T4 _4() {
        return this._4;
    }

    @Override
    public T5 _5() {
        return this._5;
    }

    @Override
    public T6 _6() {
        return this._6;
    }

    @Override
    public T7 _7() {
        return this._7;
    }

    @Override
    public T8 _8() {
        return this._8;
    }

    @Override
    public T9 _9() {
        return this._9;
    }

    @Override
    public T10 _10() {
        return this._10;
    }

    @Override
    public T11 _11() {
        return this._11;
    }

    public String toString() {
        return new StringBuilder(12).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(",").append(this._8()).append(",").append(this._9()).append(",").append(this._10()).append(",").append(this._11()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11) {
        return new Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T10 copy$default$10() {
        return this._10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T11 copy$default$11() {
        return this._11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T7 copy$default$7() {
        return this._7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T8 copy$default$8() {
        return this._8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T9 copy$default$9() {
        return this._9();
    }

    @Override
    public String productPrefix() {
        return "Tuple11";
    }

    @Override
    public Iterator<Object> productIterator() {
        return new AbstractIterator<T>(this){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Tuple11;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "_1";
            }
            case 1: {
                return "_2";
            }
            case 2: {
                return "_3";
            }
            case 3: {
                return "_4";
            }
            case 4: {
                return "_5";
            }
            case 5: {
                return "_6";
            }
            case 6: {
                return "_7";
            }
            case 7: {
                return "_8";
            }
            case 8: {
                return "_9";
            }
            case 9: {
                return "_10";
            }
            case 10: {
                return "_11";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return MurmurHash3$.MODULE$.productHash(this);
    }

    @Override
    public boolean equals(Object x$1) {
        block3: {
            block2: {
                if (this == x$1) break block2;
                if (!(x$1 instanceof Tuple11)) break block3;
                Tuple11 tuple11 = (Tuple11)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple11._1()) || !BoxesRunTime.equals(this._2(), tuple11._2()) || !BoxesRunTime.equals(this._3(), tuple11._3()) || !BoxesRunTime.equals(this._4(), tuple11._4()) || !BoxesRunTime.equals(this._5(), tuple11._5()) || !BoxesRunTime.equals(this._6(), tuple11._6()) || !BoxesRunTime.equals(this._7(), tuple11._7()) || !BoxesRunTime.equals(this._8(), tuple11._8()) || !BoxesRunTime.equals(this._9(), tuple11._9()) || !BoxesRunTime.equals(this._10(), tuple11._10()) || !BoxesRunTime.equals(this._11(), tuple11._11())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple11(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
        this._8 = _8;
        this._9 = _9;
        this._10 = _10;
        this._11 = _11;
    }
}

