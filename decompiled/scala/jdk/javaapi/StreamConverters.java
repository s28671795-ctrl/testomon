/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import scala.collection.IterableOnce;
import scala.collection.Map;
import scala.jdk.javaapi.StreamConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\rMt!B\u001a5\u0011\u0003Yd!B\u001f5\u0011\u0003q\u0004\"B\"\u0002\t\u0003!\u0005\"B#\u0002\t\u00031\u0005\"B6\u0002\t\u0003a\u0007\"\u0002=\u0002\t\u0003I\bBB@\u0002\t\u0003\t\t\u0001C\u0004\u0002\u000e\u0005!\t!a\u0004\t\u000f\u0005m\u0011\u0001\"\u0001\u0002\u001e!9\u0011qF\u0001\u0005\u0002\u0005E\u0002bBA\u001f\u0003\u0011\u0005\u0011q\b\u0005\b\u0003#\nA\u0011AA*\u0011\u001d\t)(\u0001C\u0001\u0003oBq!a!\u0002\t\u0003\t)\tC\u0004\u0002\u0012\u0006!\t!a%\t\u000f\u0005}\u0015\u0001\"\u0001\u0002\"\"9\u0011QV\u0001\u0005\u0002\u0005=\u0006bBA^\u0003\u0011\u0005\u0011Q\u0018\u0005\b\u0003\u0013\fA\u0011AAf\u0011\u001d\t9.\u0001C\u0001\u00033Dq!a;\u0002\t\u0003\ti\u000fC\u0004\u0002z\u0006!\t!a?\t\u000f\t\u001d\u0011\u0001\"\u0001\u0003\n!9!QC\u0001\u0005\u0002\t]\u0001b\u0002B\u0012\u0003\u0011\u0005!Q\u0005\u0005\b\u0005c\tA\u0011\u0001B\u001a\u0011\u001d\u0011y$\u0001C\u0001\u0005\u0003BqA!\u0014\u0002\t\u0003\u0011y\u0005C\u0004\u0003^\u0005!\tAa\u0018\t\u000f\t\r\u0014\u0001\"\u0001\u0003f!9!\u0011N\u0001\u0005\u0002\t-\u0004b\u0002B8\u0003\u0011\u0005!\u0011\u000f\u0005\b\u0005k\nA\u0011\u0001B<\u0011\u001d\u0011Y(\u0001C\u0001\u0005{BqA!!\u0002\t\u0003\u0011\u0019\tC\u0004\u0003\b\u0006!\tA!#\t\u000f\tm\u0015\u0001\"\u0001\u0003\u001e\"9!\u0011V\u0001\u0005\u0002\t-\u0006b\u0002B\\\u0003\u0011\u0005!\u0011\u0018\u0005\b\u0005\u000b\fA\u0011\u0001Bd\u0011\u001d\u0011\u0019.\u0001C\u0001\u0005+DqA!9\u0002\t\u0003\u0011\u0019\u000fC\u0004\u0003p\u0006!\tA!=\t\u000f\tu\u0018\u0001\"\u0001\u0003\u0000\"91\u0011C\u0001\u0005\u0002\rM\u0001bBB\u0010\u0003\u0011\u00051\u0011\u0005\u0005\b\u0007[\tA\u0011AB\u0018\u0011\u001d\u0019Y$\u0001C\u0001\u0007{Aqa!\u0013\u0002\t\u0003\u0019Y\u0005C\u0004\u0004X\u0005!\ta!\u0017\t\u000f\r\u0015\u0014\u0001\"\u0001\u0004h\u0005\u00012\u000b\u001e:fC6\u001cuN\u001c<feR,'o\u001d\u0006\u0003kY\nqA[1wC\u0006\u0004\u0018N\u0003\u00028q\u0005\u0019!\u000eZ6\u000b\u0003e\nQa]2bY\u0006\u001c\u0001\u0001\u0005\u0002=\u00035\tAG\u0001\tTiJ,\u0017-\\\"p]Z,'\u000f^3sgN\u0011\u0011a\u0010\t\u0003\u0001\u0006k\u0011\u0001O\u0005\u0003\u0005b\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001<\u0003=\t7OS1wCN+\u0017o\u0015;sK\u0006lWCA$U)\tAU\fE\u0002J!Jk\u0011A\u0013\u0006\u0003\u00172\u000baa\u001d;sK\u0006l'BA'O\u0003\u0011)H/\u001b7\u000b\u0003=\u000bAA[1wC&\u0011\u0011K\u0013\u0002\u0007'R\u0014X-Y7\u0011\u0005M#F\u0002\u0001\u0003\u0006+\u000e\u0011\rA\u0016\u0002\u0002\u0003F\u0011qK\u0017\t\u0003\u0001bK!!\u0017\u001d\u0003\u000f9{G\u000f[5oOB\u0011\u0001iW\u0005\u00039b\u00121!\u00118z\u0011\u0015q6\u00011\u0001`\u0003\t\u00197\rE\u0002aQJs!!\u00194\u000f\u0005\t,W\"A2\u000b\u0005\u0011T\u0014A\u0002\u001fs_>$h(C\u0001:\u0013\t9\u0007(A\u0004qC\u000e\\\u0017mZ3\n\u0005%T'\u0001D%uKJ\f'\r\\3P]\u000e,'BA49\u0003I\t7OS1wCN+\u0017/\u00138u'R\u0014X-Y7\u0015\u00055\u0004\bCA%o\u0013\ty'JA\u0005J]R\u001cFO]3b[\")a\f\u0002a\u0001cB\u0019\u0001\r\u001b:\u0011\u0005M4X\"\u0001;\u000b\u0005Ut\u0015\u0001\u00027b]\u001eL!a\u001e;\u0003\u000f%sG/Z4fe\u0006Q\u0012m\u001d&bm\u0006\u001cV-]%oiN#(/Z1n\rJ|WNQ=uKR\u0011QN\u001f\u0005\u0006=\u0016\u0001\ra\u001f\t\u0004A\"d\bCA:~\u0013\tqHO\u0001\u0003CsR,\u0017aG1t\u0015\u00064\u0018mU3r\u0013:$8\u000b\u001e:fC64%o\\7TQ>\u0014H\u000fF\u0002n\u0003\u0007AaA\u0018\u0004A\u0002\u0005\u0015\u0001\u0003\u00021i\u0003\u000f\u00012a]A\u0005\u0013\r\tY\u0001\u001e\u0002\u0006'\"|'\u000f^\u0001\u001bCNT\u0015M^1TKFLe\u000e^*ue\u0016\fWN\u0012:p[\u000eC\u0017M\u001d\u000b\u0004[\u0006E\u0001B\u00020\b\u0001\u0004\t\u0019\u0002\u0005\u0003aQ\u0006U\u0001cA:\u0002\u0018%\u0019\u0011\u0011\u0004;\u0003\u0013\rC\u0017M]1di\u0016\u0014\u0018!F1t\u0015\u00064\u0018mU3r\t>,(\r\\3TiJ,\u0017-\u001c\u000b\u0005\u0003?\t)\u0003E\u0002J\u0003CI1!a\tK\u00051!u.\u001e2mKN#(/Z1n\u0011\u0019q\u0006\u00021\u0001\u0002(A!\u0001\r[A\u0015!\r\u0019\u00181F\u0005\u0004\u0003[!(A\u0002#pk\ndW-\u0001\u0010bg*\u000bg/Y*fc\u0012{WO\u00197f'R\u0014X-Y7Ge>lg\t\\8biR!\u0011qDA\u001a\u0011\u0019q\u0016\u00021\u0001\u00026A!\u0001\r[A\u001c!\r\u0019\u0018\u0011H\u0005\u0004\u0003w!(!\u0002$m_\u0006$\u0018aE1t\u0015\u00064\u0018mU3r\u0019>twm\u0015;sK\u0006lG\u0003BA!\u0003\u000f\u00022!SA\"\u0013\r\t)E\u0013\u0002\u000b\u0019>twm\u0015;sK\u0006l\u0007B\u00020\u000b\u0001\u0004\tI\u0005\u0005\u0003aQ\u0006-\u0003cA:\u0002N%\u0019\u0011q\n;\u0003\t1{gnZ\u0001\u0013CNT\u0015M^1TKF\\U-_*ue\u0016\fW.\u0006\u0004\u0002V\u0005m\u0013\u0011\u000f\u000b\u0005\u0003/\ny\u0006\u0005\u0003J!\u0006e\u0003cA*\u0002\\\u00111\u0011QL\u0006C\u0002Y\u0013\u0011a\u0013\u0005\b\u0003CZ\u0001\u0019AA2\u0003\u0005i\u0007\u0003CA3\u0003W\nI&a\u001c\u000e\u0005\u0005\u001d$bAA5q\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u00055\u0014q\r\u0002\u0004\u001b\u0006\u0004\bcA*\u0002r\u00111\u00111O\u0006C\u0002Y\u0013\u0011AV\u0001\u0016CNT\u0015M^1TKF\\U-_%oiN#(/Z1n+\u0011\tI(!!\u0015\u00075\fY\bC\u0004\u0002b1\u0001\r!! \u0011\u000f\u0005\u0015\u00141\u000e:\u0002\u0000A\u00191+!!\u0005\r\u0005MDB1\u0001W\u0003u\t7OS1wCN+\u0017oS3z\u0013:$8\u000b\u001e:fC64%o\\7CsR,W\u0003BAD\u0003\u001f#2!\\AE\u0011\u001d\t\t'\u0004a\u0001\u0003\u0017\u0003r!!\u001a\u0002lq\fi\tE\u0002T\u0003\u001f#a!a\u001d\u000e\u0005\u00041\u0016AH1t\u0015\u00064\u0018mU3r\u0017\u0016L\u0018J\u001c;TiJ,\u0017-\u001c$s_6\u001c\u0006n\u001c:u+\u0011\t)*!(\u0015\u00075\f9\nC\u0004\u0002b9\u0001\r!!'\u0011\u0011\u0005\u0015\u00141NA\u0004\u00037\u00032aUAO\t\u0019\t\u0019H\u0004b\u0001-\u0006i\u0012m\u001d&bm\u0006\u001cV-]&fs&sGo\u0015;sK\u0006lgI]8n\u0007\"\f'/\u0006\u0003\u0002$\u0006-FcA7\u0002&\"9\u0011\u0011M\bA\u0002\u0005\u001d\u0006\u0003CA3\u0003W\n)\"!+\u0011\u0007M\u000bY\u000b\u0002\u0004\u0002t=\u0011\rAV\u0001\u0019CNT\u0015M^1TKF\\U-\u001f#pk\ndWm\u0015;sK\u0006lW\u0003BAY\u0003s#B!a\b\u00024\"9\u0011\u0011\r\tA\u0002\u0005U\u0006\u0003CA3\u0003W\nI#a.\u0011\u0007M\u000bI\f\u0002\u0004\u0002tA\u0011\rAV\u0001\"CNT\u0015M^1TKF\\U-\u001f#pk\ndWm\u0015;sK\u0006lgI]8n\r2|\u0017\r^\u000b\u0005\u0003\u007f\u000b9\r\u0006\u0003\u0002 \u0005\u0005\u0007bBA1#\u0001\u0007\u00111\u0019\t\t\u0003K\nY'a\u000e\u0002FB\u00191+a2\u0005\r\u0005M\u0014C1\u0001W\u0003Y\t7OS1wCN+\u0017oS3z\u0019>twm\u0015;sK\u0006lW\u0003BAg\u0003+$B!!\u0011\u0002P\"9\u0011\u0011\r\nA\u0002\u0005E\u0007\u0003CA3\u0003W\nY%a5\u0011\u0007M\u000b)\u000e\u0002\u0004\u0002tI\u0011\rAV\u0001\u0015CNT\u0015M^1TKF4\u0016\r\\;f'R\u0014X-Y7\u0016\r\u0005m\u0017\u0011^Aq)\u0011\ti.a9\u0011\t%\u0003\u0016q\u001c\t\u0004'\u0006\u0005HABA:'\t\u0007a\u000bC\u0004\u0002bM\u0001\r!!:\u0011\u0011\u0005\u0015\u00141NAt\u0003?\u00042aUAu\t\u0019\tif\u0005b\u0001-\u00069\u0012m\u001d&bm\u0006\u001cV-\u001d,bYV,\u0017J\u001c;TiJ,\u0017-\\\u000b\u0005\u0003_\f9\u0010F\u0002n\u0003cDq!!\u0019\u0015\u0001\u0004\t\u0019\u0010E\u0004\u0002f\u0005-\u0014Q\u001f:\u0011\u0007M\u000b9\u0010\u0002\u0004\u0002^Q\u0011\rAV\u0001 CNT\u0015M^1TKF4\u0016\r\\;f\u0013:$8\u000b\u001e:fC64%o\\7CsR,W\u0003BA\u007f\u0005\u000b!2!\\A\u0000\u0011\u001d\t\t'\u0006a\u0001\u0005\u0003\u0001r!!\u001a\u0002l\t\rA\u0010E\u0002T\u0005\u000b!a!!\u0018\u0016\u0005\u00041\u0016\u0001I1t\u0015\u00064\u0018mU3r-\u0006dW/Z%oiN#(/Z1n\rJ|Wn\u00155peR,BAa\u0003\u0003\u0014Q\u0019QN!\u0004\t\u000f\u0005\u0005d\u00031\u0001\u0003\u0010AA\u0011QMA6\u0005#\t9\u0001E\u0002T\u0005'!a!!\u0018\u0017\u0005\u00041\u0016aH1t\u0015\u00064\u0018mU3r-\u0006dW/Z%oiN#(/Z1n\rJ|Wn\u00115beV!!\u0011\u0004B\u0011)\ri'1\u0004\u0005\b\u0003C:\u0002\u0019\u0001B\u000f!!\t)'a\u001b\u0003 \u0005U\u0001cA*\u0003\"\u00111\u0011QL\fC\u0002Y\u000b!$Y:KCZ\f7+Z9WC2,X\rR8vE2,7\u000b\u001e:fC6,BAa\n\u00030Q!\u0011q\u0004B\u0015\u0011\u001d\t\t\u0007\u0007a\u0001\u0005W\u0001\u0002\"!\u001a\u0002l\t5\u0012\u0011\u0006\t\u0004'\n=BABA/1\t\u0007a+A\u0012bg*\u000bg/Y*fcZ\u000bG.^3E_V\u0014G.Z*ue\u0016\fWN\u0012:p[\u001acw.\u0019;\u0016\t\tU\"Q\b\u000b\u0005\u0003?\u00119\u0004C\u0004\u0002be\u0001\rA!\u000f\u0011\u0011\u0005\u0015\u00141\u000eB\u001e\u0003o\u00012a\u0015B\u001f\t\u0019\ti&\u0007b\u0001-\u0006A\u0012m\u001d&bm\u0006\u001cV-\u001d,bYV,Gj\u001c8h'R\u0014X-Y7\u0016\t\t\r#1\n\u000b\u0005\u0003\u0003\u0012)\u0005C\u0004\u0002bi\u0001\rAa\u0012\u0011\u0011\u0005\u0015\u00141\u000eB%\u0003\u0017\u00022a\u0015B&\t\u0019\tiF\u0007b\u0001-\u0006y\u0011m\u001d&bm\u0006\u0004\u0016M]*ue\u0016\fW.\u0006\u0003\u0003R\t]C\u0003\u0002B*\u00053\u0002B!\u0013)\u0003VA\u00191Ka\u0016\u0005\u000bU[\"\u0019\u0001,\t\ry[\u0002\u0019\u0001B.!\u0011\u0001\u0007N!\u0016\u0002%\u0005\u001c(*\u0019<b!\u0006\u0014\u0018J\u001c;TiJ,\u0017-\u001c\u000b\u0004[\n\u0005\u0004\"\u00020\u001d\u0001\u0004\t\u0018AG1t\u0015\u00064\u0018\rU1s\u0013:$8\u000b\u001e:fC64%o\\7CsR,GcA7\u0003h!)a,\ba\u0001w\u0006Y\u0012m\u001d&bm\u0006\u0004\u0016M]%oiN#(/Z1n\rJ|Wn\u00155peR$2!\u001cB7\u0011\u0019qf\u00041\u0001\u0002\u0006\u0005Q\u0012m\u001d&bm\u0006\u0004\u0016M]%oiN#(/Z1n\rJ|Wn\u00115beR\u0019QNa\u001d\t\ry{\u0002\u0019AA\n\u0003U\t7OS1wCB\u000b'\u000fR8vE2,7\u000b\u001e:fC6$B!a\b\u0003z!1a\f\ta\u0001\u0003O\ta$Y:KCZ\f\u0007+\u0019:E_V\u0014G.Z*ue\u0016\fWN\u0012:p[\u001acw.\u0019;\u0015\t\u0005}!q\u0010\u0005\u0007=\u0006\u0002\r!!\u000e\u0002'\u0005\u001c(*\u0019<b!\u0006\u0014Hj\u001c8h'R\u0014X-Y7\u0015\t\u0005\u0005#Q\u0011\u0005\u0007=\n\u0002\r!!\u0013\u0002%\u0005\u001c(*\u0019<b!\u0006\u00148*Z=TiJ,\u0017-\\\u000b\u0007\u0005\u0017\u0013\tJ!'\u0015\t\t5%1\u0013\t\u0005\u0013B\u0013y\tE\u0002T\u0005##a!!\u0018$\u0005\u00041\u0006bBA1G\u0001\u0007!Q\u0013\t\t\u0003K\nYGa$\u0003\u0018B\u00191K!'\u0005\r\u0005M4E1\u0001W\u0003U\t7OS1wCB\u000b'oS3z\u0013:$8\u000b\u001e:fC6,BAa(\u0003(R\u0019QN!)\t\u000f\u0005\u0005D\u00051\u0001\u0003$B9\u0011QMA6e\n\u0015\u0006cA*\u0003(\u00121\u00111\u000f\u0013C\u0002Y\u000bQ$Y:KCZ\f\u0007+\u0019:LKfLe\u000e^*ue\u0016\fWN\u0012:p[\nKH/Z\u000b\u0005\u0005[\u0013)\fF\u0002n\u0005_Cq!!\u0019&\u0001\u0004\u0011\t\fE\u0004\u0002f\u0005-DPa-\u0011\u0007M\u0013)\f\u0002\u0004\u0002t\u0015\u0012\rAV\u0001\u001fCNT\u0015M^1QCJ\\U-_%oiN#(/Z1n\rJ|Wn\u00155peR,BAa/\u0003DR\u0019QN!0\t\u000f\u0005\u0005d\u00051\u0001\u0003@BA\u0011QMA6\u0003\u000f\u0011\t\rE\u0002T\u0005\u0007$a!a\u001d'\u0005\u00041\u0016!H1t\u0015\u00064\u0018\rU1s\u0017\u0016L\u0018J\u001c;TiJ,\u0017-\u001c$s_6\u001c\u0005.\u0019:\u0016\t\t%'\u0011\u001b\u000b\u0004[\n-\u0007bBA1O\u0001\u0007!Q\u001a\t\t\u0003K\nY'!\u0006\u0003PB\u00191K!5\u0005\r\u0005MtE1\u0001W\u0003a\t7OS1wCB\u000b'oS3z\t>,(\r\\3TiJ,\u0017-\\\u000b\u0005\u0005/\u0014y\u000e\u0006\u0003\u0002 \te\u0007bBA1Q\u0001\u0007!1\u001c\t\t\u0003K\nY'!\u000b\u0003^B\u00191Ka8\u0005\r\u0005M\u0004F1\u0001W\u0003\u0005\n7OS1wCB\u000b'oS3z\t>,(\r\\3TiJ,\u0017-\u001c$s_64En\\1u+\u0011\u0011)O!<\u0015\t\u0005}!q\u001d\u0005\b\u0003CJ\u0003\u0019\u0001Bu!!\t)'a\u001b\u00028\t-\bcA*\u0003n\u00121\u00111O\u0015C\u0002Y\u000ba#Y:KCZ\f\u0007+\u0019:LKfduN\\4TiJ,\u0017-\\\u000b\u0005\u0005g\u0014Y\u0010\u0006\u0003\u0002B\tU\bbBA1U\u0001\u0007!q\u001f\t\t\u0003K\nY'a\u0013\u0003zB\u00191Ka?\u0005\r\u0005M$F1\u0001W\u0003Q\t7OS1wCB\u000b'OV1mk\u0016\u001cFO]3b[V11\u0011AB\b\u0007\u000f!Baa\u0001\u0004\nA!\u0011\nUB\u0003!\r\u00196q\u0001\u0003\u0007\u0003gZ#\u0019\u0001,\t\u000f\u0005\u00054\u00061\u0001\u0004\fAA\u0011QMA6\u0007\u001b\u0019)\u0001E\u0002T\u0007\u001f!a!!\u0018,\u0005\u00041\u0016aF1t\u0015\u00064\u0018\rU1s-\u0006dW/Z%oiN#(/Z1n+\u0011\u0019)b!\b\u0015\u00075\u001c9\u0002C\u0004\u0002b1\u0002\ra!\u0007\u0011\u000f\u0005\u0015\u00141NB\u000eeB\u00191k!\b\u0005\r\u0005uCF1\u0001W\u0003}\t7OS1wCB\u000b'OV1mk\u0016Le\u000e^*ue\u0016\fWN\u0012:p[\nKH/Z\u000b\u0005\u0007G\u0019Y\u0003F\u0002n\u0007KAq!!\u0019.\u0001\u0004\u00199\u0003E\u0004\u0002f\u0005-4\u0011\u0006?\u0011\u0007M\u001bY\u0003\u0002\u0004\u0002^5\u0012\rAV\u0001!CNT\u0015M^1QCJ4\u0016\r\\;f\u0013:$8\u000b\u001e:fC64%o\\7TQ>\u0014H/\u0006\u0003\u00042\reBcA7\u00044!9\u0011\u0011\r\u0018A\u0002\rU\u0002\u0003CA3\u0003W\u001a9$a\u0002\u0011\u0007M\u001bI\u0004\u0002\u0004\u0002^9\u0012\rAV\u0001 CNT\u0015M^1QCJ4\u0016\r\\;f\u0013:$8\u000b\u001e:fC64%o\\7DQ\u0006\u0014X\u0003BB \u0007\u000f\"2!\\B!\u0011\u001d\t\tg\fa\u0001\u0007\u0007\u0002\u0002\"!\u001a\u0002l\r\u0015\u0013Q\u0003\t\u0004'\u000e\u001dCABA/_\t\u0007a+\u0001\u000ebg*\u000bg/\u0019)beZ\u000bG.^3E_V\u0014G.Z*ue\u0016\fW.\u0006\u0003\u0004N\rUC\u0003BA\u0010\u0007\u001fBq!!\u00191\u0001\u0004\u0019\t\u0006\u0005\u0005\u0002f\u0005-41KA\u0015!\r\u00196Q\u000b\u0003\u0007\u0003;\u0002$\u0019\u0001,\u0002G\u0005\u001c(*\u0019<b!\u0006\u0014h+\u00197vK\u0012{WO\u00197f'R\u0014X-Y7Ge>lg\t\\8biV!11LB2)\u0011\tyb!\u0018\t\u000f\u0005\u0005\u0014\u00071\u0001\u0004`AA\u0011QMA6\u0007C\n9\u0004E\u0002T\u0007G\"a!!\u00182\u0005\u00041\u0016\u0001G1t\u0015\u00064\u0018\rU1s-\u0006dW/\u001a'p]\u001e\u001cFO]3b[V!1\u0011NB9)\u0011\t\tea\u001b\t\u000f\u0005\u0005$\u00071\u0001\u0004nAA\u0011QMA6\u0007_\nY\u0005E\u0002T\u0007c\"a!!\u00183\u0005\u00041\u0006")
public final class StreamConverters {
    public static <K> LongStream asJavaParValueLongStream(Map<K, Long> m4) {
        return StreamConverters$.MODULE$.asJavaParValueLongStream(m4);
    }

    public static <K> DoubleStream asJavaParValueDoubleStreamFromFloat(Map<K, Float> m4) {
        return StreamConverters$.MODULE$.asJavaParValueDoubleStreamFromFloat(m4);
    }

    public static <K> DoubleStream asJavaParValueDoubleStream(Map<K, Double> m4) {
        return StreamConverters$.MODULE$.asJavaParValueDoubleStream(m4);
    }

    public static <K> IntStream asJavaParValueIntStreamFromChar(Map<K, Character> m4) {
        return StreamConverters$.MODULE$.asJavaParValueIntStreamFromChar(m4);
    }

    public static <K> IntStream asJavaParValueIntStreamFromShort(Map<K, Short> m4) {
        return StreamConverters$.MODULE$.asJavaParValueIntStreamFromShort(m4);
    }

    public static <K> IntStream asJavaParValueIntStreamFromByte(Map<K, Byte> m4) {
        return StreamConverters$.MODULE$.asJavaParValueIntStreamFromByte(m4);
    }

    public static <K> IntStream asJavaParValueIntStream(Map<K, Integer> m4) {
        return StreamConverters$.MODULE$.asJavaParValueIntStream(m4);
    }

    public static <K, V> Stream<V> asJavaParValueStream(Map<K, V> m4) {
        return StreamConverters$.MODULE$.asJavaParValueStream(m4);
    }

    public static <V> LongStream asJavaParKeyLongStream(Map<Long, V> m4) {
        return StreamConverters$.MODULE$.asJavaParKeyLongStream(m4);
    }

    public static <V> DoubleStream asJavaParKeyDoubleStreamFromFloat(Map<Float, V> m4) {
        return StreamConverters$.MODULE$.asJavaParKeyDoubleStreamFromFloat(m4);
    }

    public static <V> DoubleStream asJavaParKeyDoubleStream(Map<Double, V> m4) {
        return StreamConverters$.MODULE$.asJavaParKeyDoubleStream(m4);
    }

    public static <V> IntStream asJavaParKeyIntStreamFromChar(Map<Character, V> m4) {
        return StreamConverters$.MODULE$.asJavaParKeyIntStreamFromChar(m4);
    }

    public static <V> IntStream asJavaParKeyIntStreamFromShort(Map<Short, V> m4) {
        return StreamConverters$.MODULE$.asJavaParKeyIntStreamFromShort(m4);
    }

    public static <V> IntStream asJavaParKeyIntStreamFromByte(Map<Byte, V> m4) {
        return StreamConverters$.MODULE$.asJavaParKeyIntStreamFromByte(m4);
    }

    public static <V> IntStream asJavaParKeyIntStream(Map<Integer, V> m4) {
        return StreamConverters$.MODULE$.asJavaParKeyIntStream(m4);
    }

    public static <K, V> Stream<K> asJavaParKeyStream(Map<K, V> m4) {
        return StreamConverters$.MODULE$.asJavaParKeyStream(m4);
    }

    public static LongStream asJavaParLongStream(IterableOnce<Long> cc) {
        return StreamConverters$.MODULE$.asJavaParLongStream(cc);
    }

    public static DoubleStream asJavaParDoubleStreamFromFloat(IterableOnce<Float> cc) {
        return StreamConverters$.MODULE$.asJavaParDoubleStreamFromFloat(cc);
    }

    public static DoubleStream asJavaParDoubleStream(IterableOnce<Double> cc) {
        return StreamConverters$.MODULE$.asJavaParDoubleStream(cc);
    }

    public static IntStream asJavaParIntStreamFromChar(IterableOnce<Character> cc) {
        return StreamConverters$.MODULE$.asJavaParIntStreamFromChar(cc);
    }

    public static IntStream asJavaParIntStreamFromShort(IterableOnce<Short> cc) {
        return StreamConverters$.MODULE$.asJavaParIntStreamFromShort(cc);
    }

    public static IntStream asJavaParIntStreamFromByte(IterableOnce<Byte> cc) {
        return StreamConverters$.MODULE$.asJavaParIntStreamFromByte(cc);
    }

    public static IntStream asJavaParIntStream(IterableOnce<Integer> cc) {
        return StreamConverters$.MODULE$.asJavaParIntStream(cc);
    }

    public static <A> Stream<A> asJavaParStream(IterableOnce<A> cc) {
        return StreamConverters$.MODULE$.asJavaParStream(cc);
    }

    public static <K> LongStream asJavaSeqValueLongStream(Map<K, Long> m4) {
        return StreamConverters$.MODULE$.asJavaSeqValueLongStream(m4);
    }

    public static <K> DoubleStream asJavaSeqValueDoubleStreamFromFloat(Map<K, Float> m4) {
        return StreamConverters$.MODULE$.asJavaSeqValueDoubleStreamFromFloat(m4);
    }

    public static <K> DoubleStream asJavaSeqValueDoubleStream(Map<K, Double> m4) {
        return StreamConverters$.MODULE$.asJavaSeqValueDoubleStream(m4);
    }

    public static <K> IntStream asJavaSeqValueIntStreamFromChar(Map<K, Character> m4) {
        return StreamConverters$.MODULE$.asJavaSeqValueIntStreamFromChar(m4);
    }

    public static <K> IntStream asJavaSeqValueIntStreamFromShort(Map<K, Short> m4) {
        return StreamConverters$.MODULE$.asJavaSeqValueIntStreamFromShort(m4);
    }

    public static <K> IntStream asJavaSeqValueIntStreamFromByte(Map<K, Byte> m4) {
        return StreamConverters$.MODULE$.asJavaSeqValueIntStreamFromByte(m4);
    }

    public static <K> IntStream asJavaSeqValueIntStream(Map<K, Integer> m4) {
        return StreamConverters$.MODULE$.asJavaSeqValueIntStream(m4);
    }

    public static <K, V> Stream<V> asJavaSeqValueStream(Map<K, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqValueStream(m4);
    }

    public static <V> LongStream asJavaSeqKeyLongStream(Map<Long, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqKeyLongStream(m4);
    }

    public static <V> DoubleStream asJavaSeqKeyDoubleStreamFromFloat(Map<Float, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqKeyDoubleStreamFromFloat(m4);
    }

    public static <V> DoubleStream asJavaSeqKeyDoubleStream(Map<Double, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqKeyDoubleStream(m4);
    }

    public static <V> IntStream asJavaSeqKeyIntStreamFromChar(Map<Character, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqKeyIntStreamFromChar(m4);
    }

    public static <V> IntStream asJavaSeqKeyIntStreamFromShort(Map<Short, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqKeyIntStreamFromShort(m4);
    }

    public static <V> IntStream asJavaSeqKeyIntStreamFromByte(Map<Byte, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqKeyIntStreamFromByte(m4);
    }

    public static <V> IntStream asJavaSeqKeyIntStream(Map<Integer, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqKeyIntStream(m4);
    }

    public static <K, V> Stream<K> asJavaSeqKeyStream(Map<K, V> m4) {
        return StreamConverters$.MODULE$.asJavaSeqKeyStream(m4);
    }

    public static LongStream asJavaSeqLongStream(IterableOnce<Long> cc) {
        return StreamConverters$.MODULE$.asJavaSeqLongStream(cc);
    }

    public static DoubleStream asJavaSeqDoubleStreamFromFloat(IterableOnce<Float> cc) {
        return StreamConverters$.MODULE$.asJavaSeqDoubleStreamFromFloat(cc);
    }

    public static DoubleStream asJavaSeqDoubleStream(IterableOnce<Double> cc) {
        return StreamConverters$.MODULE$.asJavaSeqDoubleStream(cc);
    }

    public static IntStream asJavaSeqIntStreamFromChar(IterableOnce<Character> cc) {
        return StreamConverters$.MODULE$.asJavaSeqIntStreamFromChar(cc);
    }

    public static IntStream asJavaSeqIntStreamFromShort(IterableOnce<Short> cc) {
        return StreamConverters$.MODULE$.asJavaSeqIntStreamFromShort(cc);
    }

    public static IntStream asJavaSeqIntStreamFromByte(IterableOnce<Byte> cc) {
        return StreamConverters$.MODULE$.asJavaSeqIntStreamFromByte(cc);
    }

    public static IntStream asJavaSeqIntStream(IterableOnce<Integer> cc) {
        return StreamConverters$.MODULE$.asJavaSeqIntStream(cc);
    }

    public static <A> Stream<A> asJavaSeqStream(IterableOnce<A> cc) {
        return StreamConverters$.MODULE$.asJavaSeqStream(cc);
    }
}

