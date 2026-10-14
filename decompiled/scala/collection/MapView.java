/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.AbstractMapView;
import scala.collection.AbstractView;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.MapOps;
import scala.collection.MapView$;
import scala.collection.MapViewFactory;
import scala.collection.View;
import scala.collection.View$;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0011=baB)S!\u0003\r\ta\u0016\u0005\b\u00037\u0001A\u0011AA\u000f\u0011\u001d\t)\u0003\u0001C!\u0003OAq!a\u000b\u0001\t\u0003\ni\u0003C\u0004\u00026\u0001!\t%a\u000e\t\u000f\u0005m\u0002\u0001\"\u0011\u0002>!9\u0011q\n\u0001\u0005B\u0005E\u0003bBA2\u0001\u0011\u0005\u0013Q\r\u0005\b\u0003[\u0002A\u0011IA8\u0011\u001d\t\u0019\b\u0001C!\u0003kBq!a\u001f\u0001\t\u0003\ni\bC\u0004\u0002\f\u0002!\t!!$\t\u000f\u0005U\u0005\u0001\"\u0011\u0002(!9\u0011q\u0013\u0001\u0005B\u0005e\u0005bBAi\u0001\u0011\u0005\u00131\u001b\u0005\t\u0003W\u0004\u0001\u0015\"\u0015\u0002n\u001e9\u0011q\u001e*\t\u0002\u0005EhAB)S\u0011\u0003\t\u0019\u0010C\u0004\u0002vF!\t!a>\u0006\r\u0005e\u0018\u0003AA~\u000b\u0019\u0011\u0019#\u0005\u0001\u0003&!I!QH\tC\u0002\u0013%!q\b\u0005\t\u0005\u0007\n\u0002\u0015!\u0003\u0003B\u00191!\u0011K\t\u0001\u0005'B!B!\u001a\u0018\u0005\u0003\u0005\u000b\u0011\u0002B4\u0011\u001d\t)p\u0006C\u0001\u0005SBqAa\u001c\u0018\t\u0003\u0011\t\bC\u0004\u0003~]!\tAa \t\u000f\t%u\u0003\"\u0011\u0003\f\"9!1S\f\u0005B\tUeA\u0002BM#\u0011\u0011Y\n\u0003\u0006\u0003fy\u0011\t\u0011)A\u0005\u0005SCq!!>\u001f\t\u0003\u0011\u0019\fC\u0004\u0003~y!\tA!1\t\u000f\t%e\u0004\"\u0011\u0003\f\"9!1\u0013\u0010\u0005B\tUeA\u0002Bd#\u0011\u0011I\r\u0003\u0006\u0003f\u0011\u0012\t\u0011)A\u0005\u0005'Dq!!>%\t\u0003\u0011i\u000eC\u0004\u0003~\u0011\"\tAa;\t\u000f\t%E\u0005\"\u0011\u0003\f\"9!1\u0013\u0013\u0005B\tUeA\u0002By#\u0001\u0011\u0019\u0010\u0003\u0006\u0003f)\u0012\t\u0011)A\u0005\u0007\u0003A!\"a\u0018+\u0005\u0003\u0005\u000b\u0011BB\u0004\u0011\u001d\t)P\u000bC\u0001\u0007\u0013AqA! +\t\u0003\u0019\t\u0002C\u0004\u0003p)\"\taa\u0006\t\u000f\t%%\u0006\"\u0011\u0003\f\"9!1\u0013\u0016\u0005B\tUeABB\u0010#\u0001\u0019\t\u0003\u0003\u0006\u0003fI\u0012\t\u0011)A\u0005\u0007_A!\"!\u00113\u0005\u0003\u0005\u000b\u0011BB\u0019\u0011\u001d\t)P\rC\u0001\u0007gAqA! 3\t\u0003\u0019Y\u0004C\u0004\u0003pI\"\ta!\u0011\t\u000f\t%%\u0007\"\u0011\u0003\f\"9!1\u0013\u001a\u0005B\tUeABB%#\u0001\u0019Y\u0005\u0003\u0006\u0003fi\u0012\t\u0011)A\u0005\u00073B!ba\u0017;\u0005\u0003\u0005\u000b\u0011BA%\u0011)\t\tE\u000fB\u0001B\u0003%1Q\f\u0005\b\u0003kTD\u0011AB1\u0011\u001d\u0011iH\u000fC\u0001\u0007WBqAa\u001c;\t\u0003\u0019y\u0007C\u0004\u0003\nj\"\tEa#\t\u000f\tM%\b\"\u0011\u0003\u0016\u001a11qO\t\u0001\u0007sB!B!\u001aD\u0005\u0003\u0005\u000b\u0011BBD\u0011)\tyf\u0011B\u0001B\u0003%1\u0011\u0012\u0005\b\u0003k\u001cE\u0011ABI\u0011\u001d\u0011yg\u0011C!\u00073CqA! D\t\u0003\u001ay\nC\u0004\u0003\n\u000e#\tEa#\t\u000f\tM5\t\"\u0011\u0003\u0016\"91QU\t\u0005B\r\u001d\u0006bBAK#\u0011\u000531\u0019\u0005\b\u0007#\fB\u0011IBj\u0011\u001d\u0019\t.\u0005C!\u0007[Dqaa@\u0012\t\u0003\"\t\u0001C\u0005\u0005\u001cE\t\t\u0011\"\u0003\u0005\u001e\t9Q*\u00199WS\u0016<(BA*U\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002+\u0006)1oY1mC\u000e\u0001Qc\u0001-d[N)\u0001!W/\u0002\u0018A\u0011!lW\u0007\u0002)&\u0011A\f\u0016\u0002\u0007\u0003:L(+\u001a4\u0011\u000fy{\u0016\r\\8\u0002\u00185\t!+\u0003\u0002a%\n1Q*\u00199PaN\u0004\"AY2\r\u0001\u0011)A\r\u0001b\u0001K\n\t1*\u0005\u0002gSB\u0011!lZ\u0005\u0003QR\u0013qAT8uQ&tw\r\u0005\u0002[U&\u00111\u000e\u0016\u0002\u0004\u0003:L\bC\u00012n\t\u0019q\u0007\u0001\"b\u0001K\n\ta+\u0006\u0003qo\u0006U\u0001c\u00010rg&\u0011!O\u0015\u0002\u0005-&,w\u000fE\u0003[iZ\f\u0019\"\u0003\u0002v)\n1A+\u001e9mKJ\u0002\"AY<\u0005\u000baL(\u0019A3\u0003\u0003aCQA_>\u0001\u0003#\t\u0011\u0002\u00107pG\u0006d\u0007\u0005\u001c \u0006\u000bql\b!!\u0001\u0003\u000314AA \u0001\u0001\u007f\naAH]3gS:,W.\u001a8u}I\u0011Q0W\u000b\u0007\u0003\u0007\tI!!\u0004\u0011\ty\u000b\u0018Q\u0001\t\u00075R\f9!a\u0003\u0011\u0007\t\fI\u0001B\u0003yw\n\u0007Q\rE\u0002c\u0003\u001b!a!a\u0004|\u0005\u0004)'!A-\f\u0001A\u0019!-!\u0006\u0005\r\u0005=\u0011P1\u0001f!\u0011q\u0016/!\u0007\u0011\ti#\u0018\r\\\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0005\u0005}\u0001c\u0001.\u0002\"%\u0019\u00111\u0005+\u0003\tUs\u0017\u000e^\u0001\u0005m&,w/\u0006\u0002\u0002*A!a\fA1m\u0003\u0011YW-_:\u0016\u0005\u0005=\u0002\u0003\u00020\u00022\u0005L1!a\rS\u0005!IE/\u001a:bE2,\u0017A\u0002<bYV,7/\u0006\u0002\u0002:A!a,!\rm\u0003)1\u0017\u000e\u001c;fe.+\u0017p\u001d\u000b\u0005\u0003S\ty\u0004C\u0004\u0002B\u0015\u0001\r!a\u0011\u0002\u0003A\u0004bAWA#C\u0006%\u0013bAA$)\nIa)\u001e8di&|g.\r\t\u00045\u0006-\u0013bAA')\n9!i\\8mK\u0006t\u0017!C7baZ\u000bG.^3t+\u0011\t\u0019&!\u0017\u0015\t\u0005U\u0013Q\f\t\u0006=\u0002\t\u0017q\u000b\t\u0004E\u0006eCABA.\r\t\u0007QMA\u0001X\u0011\u001d\tyF\u0002a\u0001\u0003C\n\u0011A\u001a\t\u00075\u0006\u0015C.a\u0016\u0002\r\u0019LG\u000e^3s)\u0011\tI#a\u001a\t\u000f\u0005%t\u00011\u0001\u0002l\u0005!\u0001O]3e!\u001dQ\u0016QIA\r\u0003\u0013\n\u0011BZ5mi\u0016\u0014hj\u001c;\u0015\t\u0005%\u0012\u0011\u000f\u0005\b\u0003SB\u0001\u0019AA6\u0003%\u0001\u0018M\u001d;ji&|g\u000e\u0006\u0003\u0002x\u0005e\u0004C\u0002.u\u0003S\tI\u0003C\u0004\u0002B%\u0001\r!a\u001b\u0002\u000fQ\f\u0007/R1dQV!\u0011qPAD)\u0011\tI#!!\t\u000f\u0005}#\u00021\u0001\u0002\u0004B9!,!\u0012\u0002\u001a\u0005\u0015\u0005c\u00012\u0002\b\u00121\u0011\u0011\u0012\u0006C\u0002\u0015\u0014\u0011!V\u0001\u000b[\u0006\u0004h)Y2u_JLXCAAH!\rq\u0016\u0011S\u0005\u0004\u0003'\u0013&AD'baZKWm\u001e$bGR|'/_\u0001\u0006K6\u0004H/_\u0001\u000bo&$\bNR5mi\u0016\u0014H\u0003BAN\u0003\u001f\u0004\"\"!(\u0002$\u0006d\u0017\u0011VAV\u001d\rq\u0016qT\u0005\u0004\u0003C\u0013\u0016AB'ba>\u00038/\u0003\u0003\u0002&\u0006\u001d&AC,ji\"4\u0015\u000e\u001c;fe*\u0019\u0011\u0011\u0015*\u0011\u0005y\u000bXCBAW\u0003g\u000bi\r\u0005\u0003_c\u0006=\u0006C\u0002.u\u0003c\u000bY\rE\u0002c\u0003g#a\u0001_A[\u0005\u0004)\u0007B\u0002>\u00028\u0002\t\t\"\u0002\u0004}\u0003s\u0003\u0011Q\u0018\u0004\u0006}\u0002\u0001\u00111\u0018\n\u0004\u0003sKVCBA`\u0003\u000b\fI\r\u0005\u0003_c\u0006\u0005\u0007C\u0002.u\u0003\u0007\f9\rE\u0002c\u0003\u000b$a\u0001_A\\\u0005\u0004)\u0007c\u00012\u0002J\u00129\u0011qBA\\\u0005\u0004)\u0007c\u00012\u0002N\u00129\u0011qBA[\u0005\u0004)\u0007bBA!\u001b\u0001\u0007\u00111N\u0001\ti>\u001cFO]5oOR\u0011\u0011Q\u001b\t\u0005\u0003/\f)O\u0004\u0003\u0002Z\u0006\u0005\bcAAn)6\u0011\u0011Q\u001c\u0006\u0004\u0003?4\u0016A\u0002\u001fs_>$h(C\u0002\u0002dR\u000ba\u0001\u0015:fI\u00164\u0017\u0002BAt\u0003S\u0014aa\u0015;sS:<'bAAr)\u0006a1\u000f\u001e:j]\u001e\u0004&/\u001a4jqV\u0011\u0011Q[\u0001\b\u001b\u0006\u0004h+[3x!\tq\u0016c\u0005\u0003\u00123\u0006=\u0015A\u0002\u001fj]&$h\b\u0006\u0002\u0002r\n\u00112k\\7f\u0013R,'/\u00192mK\u000e{gn\u001d;s+\u0019\tiPa\b\u0003\"A2\u0011q B\u0004\u00057\u0001\u0012B\u0018B\u0001\u0005\u000b\u0011YA!\u0007\n\u0007\t\r!KA\u0006Ji\u0016\u0014\u0018M\u00197f\u001fB\u001c\bc\u00012\u0003\b\u0011Q!\u0011B\n\u0002\u0002\u0003\u0005)\u0011A3\u0003\u0007}#\u0013\u0007\u0005\u0003\u0003\u000e\tMab\u00010\u0003\u0010%\u0019!\u0011\u0003*\u0002\u000fA\f7m[1hK&!!Q\u0003B\f\u0005%\te._\"p]N$(OC\u0002\u0003\u0012I\u00032A\u0019B\u000e\t)\u0011ibEA\u0001\u0002\u0003\u0015\t!\u001a\u0002\u0004?\u0012\u0012D!\u0002=\u0014\u0005\u0004)GABA\b'\t\u0007QM\u0001\u0006T_6,W*\u00199PaN,bAa\n\u0003.\tE\u0002\u0007\u0002B\u0015\u0005s\u0001\"BX0\u0003,\t=\"1\u0007B\u001c!\r\u0011'Q\u0006\u0003\u0006IR\u0011\r!\u001a\t\u0004E\nEBA\u00028\u0015\t\u000b\u0007Q\rE\u0002\u00036Mi\u0011!\u0005\t\u0004E\neBA\u0003B\u001e)\u0005\u0005\t\u0011!B\u0001K\n\u0019q\fJ\u001a\u0002\u0019\u0015k\u0007\u000f^=NCB4\u0016.Z<\u0016\u0005\t\u0005\u0003\u0003\u00020\u0001S\u001a\fQ\"R7qifl\u0015\r\u001d,jK^\u0004\u0003f\u0002\f\u0003H\t5#q\n\t\u00045\n%\u0013b\u0001B&)\n\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007\t\u0011\u0011\nZ\u000b\u0007\u0005+\u0012yFa\u0019\u0014\u0007]\u00119\u0006E\u0004_\u00053\u0012iF!\u0019\n\u0007\tm#KA\bBEN$(/Y2u\u001b\u0006\u0004h+[3x!\r\u0011'q\f\u0003\u0006I^\u0011\r!\u001a\t\u0004E\n\rDA\u00028\u0018\t\u000b\u0007Q-\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\u0004rA!\u000e\u0015\u0005;\u0012\t\u0007\u0006\u0003\u0003l\t5\u0004c\u0002B\u001b/\tu#\u0011\r\u0005\b\u0005KJ\u0002\u0019\u0001B4\u0003\r9W\r\u001e\u000b\u0005\u0005g\u0012I\bE\u0003[\u0005k\u0012\t'C\u0002\u0003xQ\u0013aa\u00149uS>t\u0007b\u0002B>5\u0001\u0007!QL\u0001\u0004W\u0016L\u0018\u0001C5uKJ\fGo\u001c:\u0016\u0005\t\u0005\u0005#\u00020\u0003\u0004\n\u001d\u0015b\u0001BC%\nA\u0011\n^3sCR|'\u000f\u0005\u0004[i\nu#\u0011M\u0001\nW:|wO\\*ju\u0016,\"A!$\u0011\u0007i\u0013y)C\u0002\u0003\u0012R\u00131!\u00138u\u0003\u001dI7/R7qif,\"!!\u0013)\u000f]\u00119E!\u0014\u0003P\t!1*Z=t+\u0011\u0011iJa*\u0014\u0007y\u0011y\nE\u0003_\u0005C\u0013)+C\u0002\u0003$J\u0013A\"\u00112tiJ\f7\r\u001e,jK^\u00042A\u0019BT\t\u0015!gD1\u0001fa\u0011\u0011YKa,\u0011\u000f\tUBC!*\u0003.B\u0019!Ma,\u0005\u0015\tEv$!A\u0001\u0002\u000b\u0005QMA\u0002`IQ\"BA!.\u00038B)!Q\u0007\u0010\u0003&\"9!Q\r\u0011A\u0002\te\u0006\u0007\u0002B^\u0005\u007f\u0003rA!\u000e\u0015\u0005K\u0013i\fE\u0002c\u0005\u007f#1B!-\u00038\u0006\u0005\t\u0011!B\u0001KV\u0011!1\u0019\t\u0006=\n\r%Q\u0015\u0015\b=\t\u001d#Q\nB(\u0005\u00191\u0016\r\\;fgV!!1\u001aBi'\r!#Q\u001a\t\u0006=\n\u0005&q\u001a\t\u0004E\nEGA\u00028%\t\u000b\u0007Q\r\r\u0003\u0003V\ne\u0007c\u0002B\u001b)\t]'q\u001a\t\u0004E\neGA\u0003BnK\u0005\u0005\t\u0011!B\u0001K\n\u0019q\fJ\u001b\u0015\t\t}'\u0011\u001d\t\u0006\u0005k!#q\u001a\u0005\b\u0005K2\u0003\u0019\u0001Bra\u0011\u0011)O!;\u0011\u000f\tUBCa:\u0003PB\u0019!M!;\u0005\u0017\tm'\u0011]A\u0001\u0002\u0003\u0015\t!Z\u000b\u0003\u0005[\u0004RA\u0018BB\u0005\u001fDs\u0001\nB$\u0005\u001b\u0012yEA\u0005NCB4\u0016\r\\;fgVA!Q\u001fB~\u0007\u000b\u0011ypE\u0002+\u0005o\u0004rA\u0018B-\u0005s\u0014i\u0010E\u0002c\u0005w$Q\u0001\u001a\u0016C\u0002\u0015\u00042A\u0019B\u0000\t\u001d\tYF\u000bCC\u0002\u0015\u0004rA!\u000e\u0015\u0005s\u001c\u0019\u0001E\u0002c\u0007\u000b!aA\u001c\u0016\u0005\u0006\u0004)\u0007c\u0002.\u0002F\r\r!Q \u000b\u0007\u0007\u0017\u0019iaa\u0004\u0011\u0013\tU\"F!?\u0004\u0004\tu\bb\u0002B3[\u0001\u00071\u0011\u0001\u0005\b\u0003?j\u0003\u0019AB\u0004+\t\u0019\u0019\u0002E\u0003_\u0005\u0007\u001b)\u0002\u0005\u0004[i\ne(Q \u000b\u0005\u00073\u0019Y\u0002E\u0003[\u0005k\u0012i\u0010C\u0004\u0003|=\u0002\rA!?)\u000f)\u00129E!\u0014\u0003P\tQa)\u001b7uKJ\\U-_:\u0016\r\r\r2\u0011FB\u0017'\r\u00114Q\u0005\t\b=\ne3qEB\u0016!\r\u00117\u0011\u0006\u0003\u0006IJ\u0012\r!\u001a\t\u0004E\u000e5BA\u000283\t\u000b\u0007Q\rE\u0004\u00036Q\u00199ca\u000b\u0011\u000fi\u000b)ea\n\u0002JQ11QGB\u001c\u0007s\u0001rA!\u000e3\u0007O\u0019Y\u0003C\u0004\u0003fU\u0002\raa\f\t\u000f\u0005\u0005S\u00071\u0001\u00042U\u00111Q\b\t\u0006=\n\r5q\b\t\u00075R\u001c9ca\u000b\u0015\t\r\r3Q\t\t\u00065\nU41\u0006\u0005\b\u0005w:\u0004\u0019AB\u0014Q\u001d\u0011$q\tB'\u0005\u001f\u0012aAR5mi\u0016\u0014XCBB'\u0007'\u001a9fE\u0002;\u0007\u001f\u0002rA\u0018B-\u0007#\u001a)\u0006E\u0002c\u0007'\"Q\u0001\u001a\u001eC\u0002\u0015\u00042AYB,\t\u0019q'\b\"b\u0001KB9!Q\u0007\u000b\u0004R\rU\u0013!C5t\r2L\u0007\u000f]3e!\u001dQ\u0016QIB0\u0003\u0013\u0002bA\u0017;\u0004R\rUC\u0003CB2\u0007K\u001a9g!\u001b\u0011\u000f\tU\"h!\u0015\u0004V!9!Q\r A\u0002\re\u0003bBB.}\u0001\u0007\u0011\u0011\n\u0005\b\u0003\u0003r\u0004\u0019AB/+\t\u0019i\u0007E\u0003_\u0005\u0007\u001by\u0006\u0006\u0003\u0004r\rM\u0004#\u0002.\u0003v\rU\u0003b\u0002B>\u0001\u0002\u00071\u0011\u000b\u0015\bu\t\u001d#Q\nB(\u0005\u001d!\u0016\r]#bG\",\u0002ba\u001f\u0004\u0002\u000e\u00155qR\n\u0004\u0007\u000eu\u0004c\u00020\u0003Z\r}41\u0011\t\u0004E\u000e\u0005E!\u00023D\u0005\u0004)\u0007c\u00012\u0004\u0006\u00121an\u0011CC\u0002\u0015\u0004rA!\u000e\u0015\u0007\u007f\u001a\u0019\tE\u0004[\u0003\u000b\u001aYi!$\u0011\ri#8qPBB!\r\u00117q\u0012\u0003\b\u0003\u0013\u001bEQ1\u0001f)\u0019\u0019\u0019j!&\u0004\u0018BI!QG\"\u0004\u0000\r\r5Q\u0012\u0005\b\u0005K2\u0005\u0019ABD\u0011\u001d\tyF\u0012a\u0001\u0007\u0013#Baa'\u0004\u001eB)!L!\u001e\u0004\u0004\"9!1P$A\u0002\r}TCABQ!\u0015q&1QBFQ\u001d\u0019%q\tB'\u0005\u001f\n!B\\3x\u0005VLG\u000eZ3s+\u0019\u0019Ika/\u0004@V\u001111\u0016\t\t\u0007[\u001b\u0019la.\u0004B6\u00111q\u0016\u0006\u0004\u0007c\u0013\u0016aB7vi\u0006\u0014G.Z\u0005\u0005\u0007k\u001byKA\u0004Ck&dG-\u001a:\u0011\ri#8\u0011XB_!\r\u001171\u0018\u0003\u0006q.\u0013\r!\u001a\t\u0004E\u000e}FABA\b\u0017\n\u0007Q\r\u0005\u0004_\u0001\re6QX\u000b\u0007\u0007\u000b\u001cYma4\u0016\u0005\r\u001d\u0007C\u00020\u0001\u0007\u0013\u001ci\rE\u0002c\u0007\u0017$Q\u0001\u001a'C\u0002\u0015\u00042AYBh\t\u0015qGJ1\u0001f\u0003\u00111'o\\7\u0016\r\rU7Q\\Bq)\u0011\u00199na9\u0011\ty\u000b8\u0011\u001c\t\u00075R\u001cYna8\u0011\u0007\t\u001ci\u000eB\u0003e\u001b\n\u0007Q\rE\u0002c\u0007C$QA\\'C\u0002\u0015Dqa!:N\u0001\u0004\u00199/\u0001\u0002jiB)al!;\u0004Z&\u001911\u001e*\u0003\u0019%#XM]1cY\u0016|enY3\u0016\r\r=8Q_B})\u0011\u0019\tpa?\u0011\ry\u000311_B|!\r\u00117Q\u001f\u0003\u0006I:\u0013\r!\u001a\t\u0004E\u000eeH!\u00028O\u0005\u0004)\u0007bBBs\u001d\u0002\u00071Q \t\b\u0005k!21_B|\u0003\u0015\t\u0007\u000f\u001d7z+\u0019!\u0019\u0001\"\u0003\u0005\u000eQ!AQ\u0001C\b!\u0019q\u0006\u0001b\u0002\u0005\fA\u0019!\r\"\u0003\u0005\u000b\u0011|%\u0019A3\u0011\u0007\t$i\u0001B\u0003o\u001f\n\u0007Q\rC\u0004\u0005\u0012=\u0003\r\u0001b\u0005\u0002\u000b\u0015dW-\\:\u0011\u000bi#)\u0002\"\u0007\n\u0007\u0011]AK\u0001\u0006=e\u0016\u0004X-\u0019;fIz\u0002bA\u0017;\u0005\b\u0011-\u0011\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001C\u0010!\u0011!\t\u0003b\u000b\u000e\u0005\u0011\r\"\u0002\u0002C\u0013\tO\tA\u0001\\1oO*\u0011A\u0011F\u0001\u0005U\u00064\u0018-\u0003\u0003\u0005.\u0011\r\"AB(cU\u0016\u001cG\u000f")
public interface MapView<K, V>
extends MapOps<K, V, ?, View<Tuple2<K, V>>>,
View<Tuple2<K, V>> {
    public static <K, V> MapView<K, V> from(MapOps<K, V, ?, Object> it) {
        return MapView$.MODULE$.from(it);
    }

    public static <K, V> View<Tuple2<K, V>> from(IterableOnce<Tuple2<K, V>> it) {
        return View$.MODULE$.from((IterableOnce)it);
    }

    public static <X, Y> Builder<Tuple2<X, Y>, MapView<X, Y>> newBuilder() {
        return MapView$.MODULE$.newBuilder();
    }

    public static /* synthetic */ MapView view$(MapView $this) {
        return $this.view();
    }

    @Override
    default public MapView<K, V> view() {
        return this;
    }

    public static /* synthetic */ Iterable keys$(MapView $this) {
        return $this.keys();
    }

    @Override
    default public Iterable<K> keys() {
        return new Keys(this);
    }

    public static /* synthetic */ Iterable values$(MapView $this) {
        return $this.values();
    }

    @Override
    default public Iterable<V> values() {
        return new Values(this);
    }

    public static /* synthetic */ MapView filterKeys$(MapView $this, Function1 p) {
        return $this.filterKeys(p);
    }

    @Override
    default public MapView<K, V> filterKeys(Function1<K, Object> p) {
        return new FilterKeys(this, p);
    }

    public static /* synthetic */ MapView mapValues$(MapView $this, Function1 f) {
        return $this.mapValues(f);
    }

    @Override
    default public <W> MapView<K, W> mapValues(Function1<V, W> f) {
        return new MapValues(this, f);
    }

    public static /* synthetic */ MapView filter$(MapView $this, Function1 pred) {
        return $this.filter(pred);
    }

    @Override
    default public MapView<K, V> filter(Function1<Tuple2<K, V>, Object> pred) {
        return new Filter<K, V>(this, false, pred);
    }

    public static /* synthetic */ MapView filterNot$(MapView $this, Function1 pred) {
        return $this.filterNot(pred);
    }

    @Override
    default public MapView<K, V> filterNot(Function1<Tuple2<K, V>, Object> pred) {
        return new Filter<K, V>(this, true, pred);
    }

    public static /* synthetic */ Tuple2 partition$(MapView $this, Function1 p) {
        return $this.partition(p);
    }

    @Override
    default public Tuple2<MapView<K, V>, MapView<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return new Tuple2<MapView<K, V>, MapView<K, V>>(this.filter(p), this.filterNot(p));
    }

    public static /* synthetic */ MapView tapEach$(MapView $this, Function1 f) {
        return $this.tapEach(f);
    }

    @Override
    default public <U> MapView<K, V> tapEach(Function1<Tuple2<K, V>, U> f) {
        return new TapEach<K, V, U>(this, f);
    }

    public static /* synthetic */ MapViewFactory mapFactory$(MapView $this) {
        return $this.mapFactory();
    }

    default public MapViewFactory mapFactory() {
        return MapView$.MODULE$;
    }

    public static /* synthetic */ MapView empty$(MapView $this) {
        return $this.empty();
    }

    @Override
    default public MapView<K, V> empty() {
        return this.mapFactory().empty();
    }

    public static /* synthetic */ MapOps.WithFilter withFilter$(MapView $this, Function1 p) {
        return $this.withFilter(p);
    }

    default public MapOps.WithFilter<K, V, View, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
        return new MapOps.WithFilter(this, p);
    }

    public static /* synthetic */ String toString$(MapView $this) {
        return $this.toString();
    }

    @Override
    default public String toString() {
        return View.toString$(this);
    }

    public static /* synthetic */ String stringPrefix$(MapView $this) {
        return $this.stringPrefix();
    }

    @Override
    default public String stringPrefix() {
        return "MapView";
    }

    public static void $init$(MapView $this) {
    }

    public static class Filter<K, V>
    extends AbstractMapView<K, V> {
        private static final long serialVersionUID = 3L;
        private final MapOps<K, V, ?, Object> underlying;
        private final boolean isFlipped;
        private final Function1<Tuple2<K, V>, Object> p;

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            return this.underlying.iterator().filterImpl(this.p, this.isFlipped);
        }

        @Override
        public Option<V> get(K key) {
            Some some;
            Object v;
            Option<V> option = this.underlying.get(key);
            if (option instanceof Some && BoxesRunTime.unboxToBoolean(this.p.apply(new Tuple2(key, v = (some = (Some)option).value()))) != this.isFlipped) {
                return some;
            }
            return None$.MODULE$;
        }

        @Override
        public int knownSize() {
            if (this.underlying.knownSize() == 0) {
                return 0;
            }
            return -1;
        }

        @Override
        public boolean isEmpty() {
            return this.iterator().isEmpty();
        }

        public Filter(MapOps<K, V, ?, Object> underlying, boolean isFlipped, Function1<Tuple2<K, V>, Object> p) {
            this.underlying = underlying;
            this.isFlipped = isFlipped;
            this.p = p;
        }
    }

    public static class FilterKeys<K, V>
    extends AbstractMapView<K, V> {
        private static final long serialVersionUID = 3L;
        private final MapOps<K, V, ?, Object> underlying;
        private final Function1<K, Object> p;

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            return this.underlying.iterator().filter((Function1<Tuple2, Object> & Serializable)(A x0$1) -> BoxesRunTime.boxToBoolean(FilterKeys.$anonfun$iterator$2(this, x0$1)));
        }

        @Override
        public Option<V> get(K key) {
            if (BoxesRunTime.unboxToBoolean(this.p.apply(key))) {
                return this.underlying.get(key);
            }
            return None$.MODULE$;
        }

        @Override
        public int knownSize() {
            if (this.underlying.knownSize() == 0) {
                return 0;
            }
            return -1;
        }

        @Override
        public boolean isEmpty() {
            return this.iterator().isEmpty();
        }

        public static final /* synthetic */ boolean $anonfun$iterator$2(FilterKeys $this, Tuple2 x0$1) {
            if (x0$1 != null) {
                Object k = x0$1._1();
                return BoxesRunTime.unboxToBoolean($this.p.apply(k));
            }
            throw new MatchError((Object)null);
        }

        public FilterKeys(MapOps<K, V, ?, Object> underlying, Function1<K, Object> p) {
            this.underlying = underlying;
            this.p = p;
        }
    }

    public static class Id<K, V>
    extends AbstractMapView<K, V> {
        private static final long serialVersionUID = 3L;
        private final MapOps<K, V, ?, Object> underlying;

        @Override
        public Option<V> get(K key) {
            return this.underlying.get(key);
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            return this.underlying.iterator();
        }

        @Override
        public int knownSize() {
            return this.underlying.knownSize();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying.isEmpty();
        }

        public Id(MapOps<K, V, ?, Object> underlying) {
            this.underlying = underlying;
        }
    }

    private static class Keys<K>
    extends AbstractView<K> {
        private static final long serialVersionUID = 3L;
        private final MapOps<K, Object, ?, Object> underlying;

        @Override
        public Iterator<K> iterator() {
            return this.underlying.keysIterator();
        }

        @Override
        public int knownSize() {
            return this.underlying.knownSize();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying.isEmpty();
        }

        public Keys(MapOps<K, Object, ?, Object> underlying) {
            this.underlying = underlying;
        }
    }

    public static class MapValues<K, V, W>
    extends AbstractMapView<K, W> {
        private static final long serialVersionUID = 3L;
        private final MapOps<K, V, ?, Object> underlying;
        private final Function1<V, W> f;

        @Override
        public Iterator<Tuple2<K, W>> iterator() {
            return this.underlying.iterator().map((Function1<Tuple2, Tuple2> & Serializable)(A kv) -> new Tuple2(kv._1(), $this.f.apply(kv._2())));
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Option<W> get(K key) {
            void map_f;
            Option<V> option = this.underlying.get(key);
            Function1<V, W> function1 = this.f;
            if (option == null) {
                throw null;
            }
            Option<V> map_this = option;
            if (map_this.isEmpty()) {
                return None$.MODULE$;
            }
            return new Some(map_f.apply(map_this.get()));
        }

        @Override
        public int knownSize() {
            return this.underlying.knownSize();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying.isEmpty();
        }

        public MapValues(MapOps<K, V, ?, Object> underlying, Function1<V, W> f) {
            this.underlying = underlying;
            this.f = f;
        }
    }

    public static class TapEach<K, V, U>
    extends AbstractMapView<K, V> {
        private static final long serialVersionUID = 3L;
        private final MapOps<K, V, ?, Object> underlying;
        private final Function1<Tuple2<K, V>, U> f;

        @Override
        public Option<V> get(K key) {
            Option<V> option = this.underlying.get(key);
            if (option instanceof Some) {
                Some some = (Some)option;
                Object v = some.value();
                this.f.apply(new Tuple2(key, v));
                return some;
            }
            if (None$.MODULE$.equals(option)) {
                return None$.MODULE$;
            }
            throw new MatchError(option);
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            return this.underlying.iterator().tapEach(this.f);
        }

        @Override
        public int knownSize() {
            return this.underlying.knownSize();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying.isEmpty();
        }

        public TapEach(MapOps<K, V, ?, Object> underlying, Function1<Tuple2<K, V>, U> f) {
            this.underlying = underlying;
            this.f = f;
        }
    }

    private static class Values<V>
    extends AbstractView<V> {
        private static final long serialVersionUID = 3L;
        private final MapOps<?, V, ?, Object> underlying;

        @Override
        public Iterator<V> iterator() {
            return this.underlying.valuesIterator();
        }

        @Override
        public int knownSize() {
            return this.underlying.knownSize();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying.isEmpty();
        }

        public Values(MapOps<?, V, ?, Object> underlying) {
            this.underlying = underlying;
        }
    }
}

