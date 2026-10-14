/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.Either;
import scala.util.Failure;
import scala.util.Left;
import scala.util.Right;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.Exception$;

@ScalaSignature(bytes="\u0006\u0005\u0011\u001ds!B%K\u0011\u0003\tf!B*K\u0011\u0003!\u0006\"B-\u0002\t\u0003QV\u0001B.\u0002\u0001qCQA]\u0001\u0005\u0002MDq!!\t\u0002\t\u0003\t\u0019\u0003C\u0004\u00026\u0005!\u0019!a\u000e\t\u000f\u0005U\u0013\u0001\"\u0001\u0002X\u0019I\u0011QL\u0001\u0011\u0002\u0007\u0005\u0011q\f\u0005\b\u0003CBA\u0011AA2\u0011%\tY\u0007\u0003b\u0001\u000e#\ti\u0007C\u0005\u0002\u0006\"\u0001\r\u0015\"\u0003\u0002n!I\u0011q\u0011\u0005AB\u0013%\u0011\u0011\u0012\u0005\b\u0003\u001fCA\u0011AA7\u0011\u001d\t\t\n\u0003C\u0001\u0003'Cq!a'\t\t\u0003\niJ\u0002\u0004\u0002 \u0006\u0001\u0011\u0011\u0015\u0005\u000b\u0003K\u0003\"\u0011!S\u0001\n\u0005\u001d\u0006bB-\u0011\t\u0003\t\u0011Q\u0016\u0005\n\u0003W\u0002\"\u0019!C\t\u0003gC\u0001\"a1\u0011A\u0003%\u0011Q\u0017\u0005\b\u0003\u000b\u0004B\u0011AAd\u0011\u001d\ti\r\u0005C\u0001\u0003G2a!a4\u0002\u0001\u0005E\u0007BCA)/\t\u0015\r\u0011\"\u0001\u0002V\"Q\u0011Q\\\f\u0003\u0002\u0003\u0006I!a6\t\u0015\u0005}wC!b\u0001\n\u0003\t\t\u000f\u0003\u0006\u0002j^\u0011\t\u0011)A\u0005\u0003GD!\"a;\u0018\u0005\u000b\u0007I\u0011AAw\u0011)\tyo\u0006B\u0001B\u0003%\u0011q\u0006\u0005\u00073^!\t!!=\t\u0013\u0005-tC1A\u0005\u0012\u0005M\u0006\u0002CAb/\u0001\u0006I!!.\t\u000f\u0005mx\u0003\"\u0001\u0002~\"9\u00111`\f\u0005\u0002\tE\u0001b\u0002B\u000f/\u0011\u0005!q\u0004\u0005\b\u0005W9B\u0011\u0001B\u0017\u0011\u001d\u0011\td\u0006C\u0001\u0005gAqA!\u0011\u0018\t\u0003\u0011\u0019\u0005C\u0004\u0003X]!\tA!\u0017\t\u000f\t-t\u0003\"\u0001\u0003n!9!1P\f\u0005\u0002\tu\u0004b\u0002BB/\u0011\u0005!Q\u0011\u0005\b\u0005\u0017;B\u0011\u0001BG\u000f%\u0011\u0019*AA\u0001\u0012\u0003\u0011)JB\u0005\u0002P\u0006\t\t\u0011#\u0001\u0003\u0018\"1\u0011,\fC\u0001\u00053C\u0011Ba'.#\u0003%\tA!(\t\u0013\t]V&%A\u0005\u0002\te\u0006\"\u0003Ba\u0003\t\u0007IQ\u0001Bb\u0011!\u00119-\u0001Q\u0001\u000e\t\u0015\u0007b\u0002Be\u0003\u0011\u0015!1\u001a\u0005\b\u0005+\fAQ\u0001Bl\u0011%\u0011\t/\u0001b\u0001\n\u000b\u0011\u0019\u000f\u0003\u0005\u0003h\u0006\u0001\u000bQ\u0002Bs\u0011\u001d\u0011I/\u0001C\u0003\u0005WDqA!>\u0002\t\u000b\u00119\u0010C\u0004\u0004\u0002\u0005!\taa\u0001\t\u000f\r\u0005\u0011\u0001\"\u0001\u0004&!91QG\u0001\u0005\u0002\r]\u0002bBB\u001b\u0003\u0011\u00051q\n\u0005\b\u0007;\nA\u0011AB0\u0011\u001d\u0019\t(\u0001C\u0001\u0007gBqa!$\u0002\t\u0003\u0019yI\u0002\u0004\u00040\u0006\u00011\u0011\u0017\u0005\u000b\u0003;\u0001%\u0011!Q\u0001\n\rU\u0006BB-A\t\u0003\u0019\t\rC\u0004\u0004H\u0002#\ta!3\t\u000f\r5\u0017\u0001\"\u0001\u0004P\"911^\u0001\u0005\u0002\r5\bbBB}\u0003\u0011\u000511 \u0005\b\t'\tA\u0011\u0002C\u000b\u0011\u001d!\u0019$\u0001C\u0005\tk\t\u0011\"\u0012=dKB$\u0018n\u001c8\u000b\u0005-c\u0015aB2p]R\u0014x\u000e\u001c\u0006\u0003\u001b:\u000bA!\u001e;jY*\tq*A\u0003tG\u0006d\u0017m\u0001\u0001\u0011\u0005I\u000bQ\"\u0001&\u0003\u0013\u0015C8-\u001a9uS>t7CA\u0001V!\t1v+D\u0001O\u0013\tAfJ\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003E\u0013qaQ1uG\",'/\u0006\u0002^SB!aK\u00181h\u0013\tyfJA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o!\t\tGM\u0004\u0002WE&\u00111MT\u0001\ba\u0006\u001c7.Y4f\u0013\t)gMA\u0005UQJ|w/\u00192mK*\u00111M\u0014\t\u0003Q&d\u0001\u0001\u0002\u0004k\u0007\u0011\u0015\ra\u001b\u0002\u0002)F\u0011An\u001c\t\u0003-6L!A\u001c(\u0003\u000f9{G\u000f[5oOB\u0011a\u000b]\u0005\u0003c:\u00131!\u00118z\u0003%i7nQ1uG\",'/\u0006\u0003u\u0003\u000bAH#B;\u0002\f\u0005mAC\u0001<z!\u00111f\fY<\u0011\u0005!DH!\u00026\u0005\u0005\u0004Y\u0007b\u0002>\u0005\u0003\u0003\u0005\u001da_\u0001\u000bKZLG-\u001a8dK\u0012\n\u0004\u0003\u0002?\u0000\u0003\u0007i\u0011! \u0006\u0003}:\u000bqA]3gY\u0016\u001cG/C\u0002\u0002\u0002u\u0014\u0001b\u00117bgN$\u0016m\u001a\t\u0004Q\u0006\u0015AaBA\u0004\t\t\u0007\u0011\u0011\u0002\u0002\u0003\u000bb\f\"\u0001\u001c1\t\u000f\u00055A\u00011\u0001\u0002\u0010\u0005)\u0011n\u001d#fMB9a+!\u0005\u0002\u0004\u0005U\u0011bAA\n\u001d\nIa)\u001e8di&|g.\r\t\u0004-\u0006]\u0011bAA\r\u001d\n9!i\\8mK\u0006t\u0007bBA\u000f\t\u0001\u0007\u0011qD\u0001\u0002MB1a+!\u0005\u0002\u0004]\f!#\\6UQJ|w/\u00192mK\u000e\u000bGo\u00195feV!\u0011QEA\u0016)\u0019\t9#!\f\u00022A)aK\u00181\u0002*A\u0019\u0001.a\u000b\u0005\u000b),!\u0019A6\t\u000f\u00055Q\u00011\u0001\u00020A1a+!\u0005a\u0003+Aq!!\b\u0006\u0001\u0004\t\u0019\u0004\u0005\u0004W\u0003#\u0001\u0017\u0011F\u0001\u001ai\"\u0014xn^1cY\u0016\u001cVO\u0019;za\u0016$vnQ1uG\",'/\u0006\u0004\u0002:\u00055\u00131\t\u000b\u0005\u0003w\ty\u0005\u0006\u0003\u0002>\u0005\u0015\u0003#BA \u0007\u0005\u0005S\"A\u0001\u0011\u0007!\f\u0019\u0005B\u0003k\r\t\u00071\u000eC\u0005\u0002H\u0019\t\t\u0011q\u0001\u0002J\u0005QQM^5eK:\u001cW\r\n\u001a\u0011\tq|\u00181\n\t\u0004Q\u00065CaBA\u0004\r\t\u0007\u0011\u0011\u0002\u0005\b\u0003#2\u0001\u0019AA*\u0003\t\u0001h\r\u0005\u0004W=\u0006-\u0013\u0011I\u0001\u000eg\"|W\u000f\u001c3SKRD'o\\<\u0015\t\u0005U\u0011\u0011\f\u0005\u0007\u00037:\u0001\u0019\u00011\u0002\u0003a\u0014\u0011\u0002R3tGJL'-\u001a3\u0014\u0005!)\u0016A\u0002\u0013j]&$H\u0005\u0006\u0002\u0002fA\u0019a+a\u001a\n\u0007\u0005%dJ\u0001\u0003V]&$\u0018\u0001\u00028b[\u0016,\"!a\u001c\u0011\t\u0005E\u0014q\u0010\b\u0005\u0003g\nY\bE\u0002\u0002v9k!!a\u001e\u000b\u0007\u0005e\u0004+\u0001\u0004=e>|GOP\u0005\u0004\u0003{r\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0002\u0002\u0006\r%AB*ue&twMC\u0002\u0002~9\u000bQa\u00183fg\u000e\f\u0011b\u00183fg\u000e|F%Z9\u0015\t\u0005\u0015\u00141\u0012\u0005\n\u0003\u001bc\u0011\u0011!a\u0001\u0003_\n1\u0001\u001f\u00132\u0003\u0011!Wm]2\u0002\u0011]LG\u000f\u001b#fg\u000e$B!!&\u0002\u00186\t\u0001\u0002C\u0004\u0002\u001a:\u0001\r!a\u001c\u0002\u0003M\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003_\u0012qAR5oC2d\u0017p\u0005\u0003\u0011+\u0006\r\u0006cAA \u0011\u0005!!m\u001c3z!\u00151\u0016\u0011VA3\u0013\r\tYK\u0014\u0002\ty\tLh.Y7f}Q!\u0011qVAY!\r\ty\u0004\u0005\u0005\t\u0003K\u0013B\u00111\u0001\u0002(V\u0011\u0011Q\u0017\t\u0005\u0003o\u000b\t-\u0004\u0002\u0002:*!\u00111XA_\u0003\u0011a\u0017M\\4\u000b\u0005\u0005}\u0016\u0001\u00026bm\u0006LA!!!\u0002:\u0006)a.Y7fA\u0005\u0019\u0011M\u001c3\u0015\t\u0005=\u0016\u0011\u001a\u0005\t\u0003\u0017,B\u00111\u0001\u0002(\u0006)q\u000e\u001e5fe\u00061\u0011N\u001c<pW\u0016\u0014QaQ1uG\",B!a5\u0002\\N!q#VAR+\t\t9\u000eE\u0003\u0002@\r\tI\u000eE\u0002i\u00037$aA[\f\u0005\u0006\u0004Y\u0017a\u00019gA\u0005\u0019a-\u001b8\u0016\u0005\u0005\r\b#\u0002,\u0002f\u0006=\u0016bAAt\u001d\n1q\n\u001d;j_:\fAAZ5oA\u00059!/\u001a;ie><XCAA\u0018\u0003!\u0011X\r\u001e5s_^\u0004C\u0003CAz\u0003k\f90!?\u0011\u000b\u0005}r#!7\t\u000f\u0005Ec\u00041\u0001\u0002X\"I\u0011q\u001c\u0010\u0011\u0002\u0003\u0007\u00111\u001d\u0005\n\u0003Wt\u0002\u0013!a\u0001\u0003_\t!a\u001c:\u0016\t\u0005}(Q\u0001\u000b\u0005\u0005\u0003\u0011Y\u0001E\u0003\u0002@]\u0011\u0019\u0001E\u0002i\u0005\u000b!qAa\u0002\"\u0005\u0004\u0011IAA\u0001V#\r\tIn\u001c\u0005\b\u0005\u001b\t\u0003\u0019\u0001B\b\u0003\r\u0001hM\r\t\u0006\u0003\u007f\u0019!1A\u000b\u0005\u0005'\u0011I\u0002\u0006\u0003\u0003\u0016\tm\u0001#BA /\t]\u0001c\u00015\u0003\u001a\u00119!q\u0001\u0012C\u0002\t%\u0001bBAfE\u0001\u0007!QC\u0001\u0006CB\u0004H._\u000b\u0005\u0005C\u0011)\u0003\u0006\u0003\u0003$\t\u001d\u0002c\u00015\u0003&\u00119!qA\u0012C\u0002\t%\u0001\u0002CASG\u0011\u0005\rA!\u000b\u0011\u000bY\u000bIKa\t\u0002\u0015\u0005tGMR5oC2d\u0017\u0010\u0006\u0003\u0002t\n=\u0002\u0002CASI\u0011\u0005\r!a*\u0002\u0007=\u0004H/\u0006\u0003\u00036\tmB\u0003\u0002B\u001c\u0005{\u0001RAVAs\u0005s\u00012\u0001\u001bB\u001e\t\u001d\u00119!\nb\u0001\u0005\u0013A\u0001\"!*&\t\u0003\u0007!q\b\t\u0006-\u0006%&\u0011H\u0001\u0007K&$\b.\u001a:\u0016\t\t\u0015#\u0011\u000b\u000b\u0005\u0005\u000f\u0012\u0019\u0006E\u0004\u0003J\t-\u0003Ma\u0014\u000e\u00031K1A!\u0014M\u0005\u0019)\u0015\u000e\u001e5feB\u0019\u0001N!\u0015\u0005\u000f\t\u001daE1\u0001\u0003\n!A\u0011Q\u0015\u0014\u0005\u0002\u0004\u0011)\u0006E\u0003W\u0003S\u0013y%A\u0004xSRDGK]=\u0016\t\tm#Q\r\u000b\u0005\u0005;\u00129\u0007\u0005\u0004\u0003J\t}#1M\u0005\u0004\u0005Cb%a\u0001+ssB\u0019\u0001N!\u001a\u0005\u000f\t\u001dqE1\u0001\u0003\n!A\u0011QU\u0014\u0005\u0002\u0004\u0011I\u0007E\u0003W\u0003S\u0013\u0019'A\u0005xSRD\u0017\t\u001d9msV!!q\u000eB;)\u0011\u0011\tHa\u001e\u0011\u000b\u0005}rCa\u001d\u0011\u0007!\u0014)\b\u0002\u0004\u0003\b!\u0012\ra\u001b\u0005\b\u0003;A\u0003\u0019\u0001B=!\u00191\u0016\u0011\u00031\u0003t\u0005AAo\\(qi&|g.\u0006\u0002\u0003\u0000A)\u0011qH\f\u0003\u0002B)a+!:\u0002Z\u0006AAo\\#ji\",'/\u0006\u0002\u0003\bB)\u0011qH\f\u0003\nB9!\u0011\nB&A\u0006e\u0017!\u0002;p)JLXC\u0001BH!\u0015\tyd\u0006BI!\u0019\u0011IEa\u0018\u0002Z\u0006)1)\u0019;dQB\u0019\u0011qH\u0017\u0014\u00055*FC\u0001BK\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%eU!!q\u0014B[+\t\u0011\tK\u000b\u0003\u0002d\n\r6F\u0001BS!\u0011\u00119K!-\u000e\u0005\t%&\u0002\u0002BV\u0005[\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\t=f*\u0001\u0006b]:|G/\u0019;j_:LAAa-\u0003*\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000b)|#\u0019A6\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00134+\u0011\u0011YLa0\u0016\u0005\tu&\u0006BA\u0018\u0005G#QA\u001b\u0019C\u0002-\faB\\8uQ&twmQ1uG\",'/\u0006\u0002\u0003FB!\u0011qH\u0002m\u0003=qw\u000e\u001e5j]\u001e\u001c\u0015\r^2iKJ\u0004\u0013a\u00048p]\u001a\u000bG/\u00197DCR\u001c\u0007.\u001a:\u0016\t\t5'1[\u000b\u0003\u0005\u001f\u0004R!a\u0010\u0004\u0005#\u00042\u0001\u001bBj\t\u0015Q7G1\u0001l\u0003)\tG\u000e\\\"bi\u000eDWM]\u000b\u0005\u00053\u0014y.\u0006\u0002\u0003\\B)\u0011qH\u0002\u0003^B\u0019\u0001Na8\u0005\u000b)$$\u0019A6\u0002\u000f9|7)\u0019;dQV\u0011!Q\u001d\t\u0005\u0003\u007f9B.\u0001\u0005o_\u000e\u000bGo\u00195!\u0003!\tG\u000e\\\"bi\u000eDW\u0003\u0002Bw\u0005g,\"Aa<\u0011\u000b\u0005}rC!=\u0011\u0007!\u0014\u0019\u0010B\u0003ko\t\u00071.A\u0007o_:4\u0015\r^1m\u0007\u0006$8\r[\u000b\u0005\u0005s\u0014y0\u0006\u0002\u0003|B)\u0011qH\f\u0003~B\u0019\u0001Na@\u0005\u000b)D$\u0019A6\u0002\u0011\r\fGo\u00195j]\u001e,Ba!\u0002\u0004\fQ!1qAB\u0007!\u0015\tydFB\u0005!\rA71\u0002\u0003\u0006Uf\u0012\ra\u001b\u0005\b\u0007\u001fI\u0004\u0019AB\t\u0003))\u0007pY3qi&|gn\u001d\t\u0006-\u000eM1qC\u0005\u0004\u0007+q%A\u0003\u001fsKB,\u0017\r^3e}A\"1\u0011DB\u0011!\u0019\t\tha\u0007\u0004 %!1QDAB\u0005\u0015\u0019E.Y:t!\rA7\u0011\u0005\u0003\f\u0007G\u0019i!!A\u0001\u0002\u000b\u00051NA\u0002`IE*Baa\n\u0004.Q!1\u0011FB\u0018!\u0015\tydFB\u0016!\rA7Q\u0006\u0003\u0006Uj\u0012\ra\u001b\u0005\b\u0007cQ\u0004\u0019AB\u001a\u0003\u0005\u0019\u0007#BA \u0007\r-\u0012!F2bi\u000eD\u0017N\\4Qe>l\u0017n]2v_V\u001cH._\u000b\u0005\u0007s\u0019y\u0004\u0006\u0003\u0004<\r\u0005\u0003#BA /\ru\u0002c\u00015\u0004@\u0011)!n\u000fb\u0001W\"91qB\u001eA\u0002\r\r\u0003#\u0002,\u0004\u0014\r\u0015\u0003\u0007BB$\u0007\u0017\u0002b!!\u001d\u0004\u001c\r%\u0003c\u00015\u0004L\u0011Y1QJB!\u0003\u0003\u0005\tQ!\u0001l\u0005\ryFEM\u000b\u0005\u0007#\u001a9\u0006\u0006\u0003\u0004T\re\u0003#BA /\rU\u0003c\u00015\u0004X\u0011)!\u000e\u0010b\u0001W\"91\u0011\u0007\u001fA\u0002\rm\u0003#BA \u0007\rU\u0013\u0001C5h]>\u0014\u0018N\\4\u0015\t\r\u000541\r\t\u0006\u0003\u007f9\u0012Q\r\u0005\b\u0007\u001fi\u0004\u0019AB3!\u0015161CB4a\u0011\u0019Ig!\u001c\u0011\r\u0005E41DB6!\rA7Q\u000e\u0003\f\u0007_\u001a\u0019'!A\u0001\u0002\u000b\u00051NA\u0002`IM\nqAZ1jY&tw-\u0006\u0003\u0004v\ruD\u0003BB<\u0007\u007f\u0002R!a\u0010\u0018\u0007s\u0002RAVAs\u0007w\u00022\u0001[B?\t\u0015QgH1\u0001l\u0011\u001d\u0019yA\u0010a\u0001\u0007\u0003\u0003RAVB\n\u0007\u0007\u0003Da!\"\u0004\nB1\u0011\u0011OB\u000e\u0007\u000f\u00032\u0001[BE\t-\u0019Yia \u0002\u0002\u0003\u0005)\u0011A6\u0003\u0007}#C'A\u0006gC&d\u0017i\u001d,bYV,W\u0003BBI\u00073#Baa%\u0004\"R!1QSBN!\u0015\tydFBL!\rA7\u0011\u0014\u0003\u0006U~\u0012\ra\u001b\u0005\t\u0007;{D\u00111\u0001\u0004 \u0006)a/\u00197vKB)a+!+\u0004\u0018\"91qB A\u0002\r\r\u0006#\u0002,\u0004\u0014\r\u0015\u0006\u0007BBT\u0007W\u0003b!!\u001d\u0004\u001c\r%\u0006c\u00015\u0004,\u0012Y1QVBQ\u0003\u0003\u0005\tQ!\u0001l\u0005\ryF%\u000e\u0002\u0003\u0005f,baa-\u0004:\u000eu6C\u0001!V!\u001d1\u0016\u0011CB\\\u0007w\u00032\u0001[B]\t\u0015Q\u0007I1\u0001l!\rA7Q\u0018\u0003\u0007\u0007\u007f\u0003%\u0019A6\u0003\u0003I#Baa1\u0004FB9\u0011q\b!\u00048\u000em\u0006bBA\u000f\u0005\u0002\u00071QW\u0001\u0003Ef$Baa/\u0004L\"9\u00111L\"A\u0002\r]\u0016\u0001\u00035b]\u0012d\u0017N\\4\u0016\t\rE7\u0011\u001c\u000b\u0005\u0007'\u001ci\u000eE\u0004\u0002@\u0001\u001b)na7\u0011\rY\u000b\t\u0002YBl!\rA7\u0011\u001c\u0003\u0006U\u0012\u0013\ra\u001b\t\u0006\u0003\u007f92q\u001b\u0005\b\u0007\u001f!\u0005\u0019ABp!\u0015161CBqa\u0011\u0019\u0019oa:\u0011\r\u0005E41DBs!\rA7q\u001d\u0003\f\u0007S\u001ci.!A\u0001\u0002\u000b\u00051NA\u0002`IY\n!\"\u001e7uS6\fG/\u001a7z+\u0011\u0019yo!>\u0015\t\rE8q\u001f\t\u0006\u0003\u007f921\u001f\t\u0004Q\u000eUH!\u00026F\u0005\u0004Y\u0007\u0002CAS\u000b\u0012\u0005\r!a*\u0002\u0015UtwO]1qa&tw-\u0006\u0003\u0004~\u0012\rA\u0003BB\u0000\t\u000b\u0001R!a\u0010\u0018\t\u0003\u00012\u0001\u001bC\u0002\t\u0015QgI1\u0001l\u0011\u001d\u0019yA\u0012a\u0001\t\u000f\u0001RAVB\n\t\u0013\u0001D\u0001b\u0003\u0005\u0010A1\u0011\u0011OB\u000e\t\u001b\u00012\u0001\u001bC\b\t-!\t\u0002\"\u0002\u0002\u0002\u0003\u0005)\u0011A6\u0003\u0007}#s'\u0001\u0006x_VdG-T1uG\"$b!!\u0006\u0005\u0018\u0011e\u0001BBA.\u000f\u0002\u0007\u0001\rC\u0004\u0005\u001c\u001d\u0003\r\u0001\"\b\u0002\u000f\rd\u0017m]:fgB1Aq\u0004C\u0013\tSi!\u0001\"\t\u000b\u0007\u0011\rb*\u0001\u0006d_2dWm\u0019;j_:LA\u0001b\n\u0005\"\t\u00191+Z91\t\u0011-Bq\u0006\t\u0007\u0003c\u001aY\u0002\"\f\u0011\u0007!$y\u0003B\u0006\u00052\u0011e\u0011\u0011!A\u0001\u0006\u0003Y'aA0%q\u0005\u0001\u0002O\u001a$s_6,\u0005pY3qi&|gn\u001d\u000b\u0005\to!I\u0004\u0005\u0003W=\u0002d\u0007bBB\b\u0011\u0002\u0007A1\b\t\u0006-\u000eMAQ\b\u0019\u0005\t\u007f!\u0019\u0005\u0005\u0004\u0002r\rmA\u0011\t\t\u0004Q\u0012\rCa\u0003C#\ts\t\t\u0011!A\u0003\u0002-\u00141a\u0018\u0013:\u0001")
public final class Exception {
    public static <T> Catch<T> unwrapping(Seq<Class<?>> exceptions) {
        return Exception$.MODULE$.unwrapping(exceptions);
    }

    public static <T> Catch<T> ultimately(Function0<BoxedUnit> body2) {
        return Exception$.MODULE$.ultimately(body2);
    }

    public static <T> By<Function1<Throwable, T>, Catch<T>> handling(Seq<Class<?>> exceptions) {
        return Exception$.MODULE$.handling(exceptions);
    }

    public static <T> Catch<T> failAsValue(Seq<Class<?>> exceptions, Function0<T> value) {
        return Exception$.MODULE$.failAsValue(exceptions, value);
    }

    public static <T> Catch<Option<T>> failing(Seq<Class<?>> exceptions) {
        return Exception$.MODULE$.failing(exceptions);
    }

    public static Catch<BoxedUnit> ignoring(Seq<Class<?>> exceptions) {
        return Exception$.MODULE$.ignoring(exceptions);
    }

    public static <T> Catch<T> catchingPromiscuously(PartialFunction<Throwable, T> c) {
        return Exception$.MODULE$.catchingPromiscuously(c);
    }

    public static <T> Catch<T> catchingPromiscuously(Seq<Class<?>> exceptions) {
        return Exception$.MODULE$.catchingPromiscuously(exceptions);
    }

    public static <T> Catch<T> catching(PartialFunction<Throwable, T> c) {
        return Exception$.MODULE$.catching(c);
    }

    public static <T> Catch<T> catching(Seq<Class<?>> exceptions) {
        return Exception$.MODULE$.catching(exceptions);
    }

    public static <T> Catch<T> nonFatalCatch() {
        return Exception$.MODULE$.nonFatalCatch();
    }

    public static <T> Catch<T> allCatch() {
        return Exception$.MODULE$.allCatch();
    }

    public static Catch<Nothing$> noCatch() {
        return Exception$.MODULE$.noCatch();
    }

    public static <T> PartialFunction<Throwable, T> allCatcher() {
        return Exception$.MODULE$.allCatcher();
    }

    public static <T> PartialFunction<Throwable, T> nonFatalCatcher() {
        return Exception$.MODULE$.nonFatalCatcher();
    }

    public static PartialFunction<Throwable, Nothing$> nothingCatcher() {
        return Exception$.MODULE$.nothingCatcher();
    }

    public static boolean shouldRethrow(Throwable x) {
        return Exception$.MODULE$.shouldRethrow(x);
    }

    public static <Ex extends Throwable, T> PartialFunction<Throwable, T> throwableSubtypeToCatcher(PartialFunction<Ex, T> pf, ClassTag<Ex> evidence$2) {
        return Exception$.MODULE$.throwableSubtypeToCatcher(pf, evidence$2);
    }

    public static <T> PartialFunction<Throwable, T> mkThrowableCatcher(Function1<Throwable, Object> isDef, Function1<Throwable, T> f) {
        return Exception$.MODULE$.mkThrowableCatcher(isDef, f);
    }

    public static <Ex extends Throwable, T> PartialFunction<Throwable, T> mkCatcher(Function1<Ex, Object> isDef, Function1<Ex, T> f, ClassTag<Ex> evidence$1) {
        return Exception$.MODULE$.mkCatcher(isDef, f, evidence$1);
    }

    public static class By<T, R> {
        private final Function1<T, R> f;

        public R by(T x) {
            return this.f.apply(x);
        }

        public By(Function1<T, R> f) {
            this.f = f;
        }
    }

    public static class Catch<T>
    implements Described {
        private final PartialFunction<Throwable, T> pf;
        private final Option<Finally> fin;
        private final Function1<Throwable, Object> rethrow;
        private final String name;
        private String scala$util$control$Exception$Described$$_desc;

        @Override
        public String desc() {
            return ((Described)this).desc();
        }

        @Override
        public Described withDesc(String s2) {
            return ((Described)this).withDesc(s2);
        }

        @Override
        public String toString() {
            return ((Described)this).toString();
        }

        @Override
        public String scala$util$control$Exception$Described$$_desc() {
            return this.scala$util$control$Exception$Described$$_desc;
        }

        @Override
        public void scala$util$control$Exception$Described$$_desc_$eq(String x$1) {
            this.scala$util$control$Exception$Described$$_desc = x$1;
        }

        public PartialFunction<Throwable, T> pf() {
            return this.pf;
        }

        public Option<Finally> fin() {
            return this.fin;
        }

        public Function1<Throwable, Object> rethrow() {
            return this.rethrow;
        }

        @Override
        public String name() {
            return this.name;
        }

        public <U> Catch<U> or(PartialFunction<Throwable, U> pf2) {
            return new Catch<U>(this.pf().orElse(pf2), this.fin(), this.rethrow());
        }

        public <U> Catch<U> or(Catch<U> other) {
            return this.or(other.pf());
        }

        public <U> U apply(Function0<U> body2) {
            Object object;
            block10: {
                try {
                    try {
                        object = body2.apply();
                    }
                    catch (Throwable throwable) {
                        if (BoxesRunTime.unboxToBoolean(this.rethrow().apply(throwable))) {
                            throw throwable;
                        }
                        if (this.pf().isDefinedAt(throwable)) {
                            object = this.pf().apply(throwable);
                            break block10;
                        }
                        throw throwable;
                    }
                }
                catch (Throwable throwable) {
                    Option<Finally> option = this.fin();
                    if (option == null) {
                        throw null;
                    }
                    Option<Finally> foreach_this = option;
                    if (!foreach_this.isEmpty()) {
                        foreach_this.get().invoke();
                    }
                    Object var4_4 = null;
                    throw throwable;
                }
            }
            Option<Finally> option = this.fin();
            if (option == null) {
                throw null;
            }
            Option<Finally> foreach_this = option;
            if (!foreach_this.isEmpty()) {
                foreach_this.get().invoke();
                return object;
            }
            return object;
        }

        public Catch<T> andFinally(Function0<BoxedUnit> body2) {
            Option<Finally> option = this.fin();
            if (option == null) {
                throw null;
            }
            Option<Finally> map_this = option;
            Option option2 = map_this.isEmpty() ? None$.MODULE$ : new Some<Finally>(map_this.get().and(body2));
            Object var3_2 = null;
            Option getOrElse_this = option2;
            Object var4_3 = null;
            Finally appendedFin = getOrElse_this.isEmpty() ? new Finally(body2) : getOrElse_this.get();
            return new Catch<T>(this.pf(), new Some<Finally>(appendedFin), this.rethrow());
        }

        public <U> Option<U> opt(Function0<U> body2) {
            return (Option)this.toOption().apply((Function0<Some> & Serializable)() -> new Some(body2.apply()));
        }

        public <U> Either<Throwable, U> either(Function0<U> body2) {
            return (Either)this.toEither().apply((Function0<Right> & Serializable)() -> new Right(body2.apply()));
        }

        public <U> Try<U> withTry(Function0<U> body2) {
            return (Try)this.toTry().apply((Function0<Success> & Serializable)() -> new Success(body2.apply()));
        }

        public <U> Catch<U> withApply(Function1<Throwable, U> f) {
            PartialFunction pf2 = new PartialFunction<Throwable, U>(this, f){
                private final /* synthetic */ Catch $outer;
                private final Function1 f$2;

                public Option unapply(Object a) {
                    return PartialFunction.unapply$(this, a);
                }

                public PartialFunction<Throwable, U> elementWise() {
                    return PartialFunction.elementWise$(this);
                }

                public <A1 extends Throwable, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
                    return PartialFunction.orElse$(this, that);
                }

                public <C$> PartialFunction<Throwable, C$> andThen(Function1<U, C$> k) {
                    return PartialFunction.andThen$((PartialFunction)this, k);
                }

                public <C$> PartialFunction<Throwable, C$> andThen(PartialFunction<U, C$> k) {
                    return PartialFunction.andThen$((PartialFunction)this, k);
                }

                public <R$> PartialFunction<R$, U> compose(PartialFunction<R$, Throwable> k) {
                    return PartialFunction.compose$(this, k);
                }

                public Function1<Throwable, Option<U>> lift() {
                    return PartialFunction.lift$(this);
                }

                public Object applyOrElse(Object x, Function1 function1) {
                    return PartialFunction.applyOrElse$(this, x, function1);
                }

                public <U> Function1<Throwable, Object> runWith(Function1<U, U> action) {
                    return PartialFunction.runWith$(this, action);
                }

                public boolean apply$mcZD$sp(double v1) {
                    return Function1.apply$mcZD$sp$(this, v1);
                }

                public double apply$mcDD$sp(double v1) {
                    return Function1.apply$mcDD$sp$(this, v1);
                }

                public float apply$mcFD$sp(double v1) {
                    return Function1.apply$mcFD$sp$(this, v1);
                }

                public int apply$mcID$sp(double v1) {
                    return Function1.apply$mcID$sp$(this, v1);
                }

                public long apply$mcJD$sp(double v1) {
                    return Function1.apply$mcJD$sp$(this, v1);
                }

                public void apply$mcVD$sp(double v1) {
                    Function1.apply$mcVD$sp$(this, v1);
                }

                public boolean apply$mcZF$sp(float v1) {
                    return Function1.apply$mcZF$sp$(this, v1);
                }

                public double apply$mcDF$sp(float v1) {
                    return Function1.apply$mcDF$sp$(this, v1);
                }

                public float apply$mcFF$sp(float v1) {
                    return Function1.apply$mcFF$sp$(this, v1);
                }

                public int apply$mcIF$sp(float v1) {
                    return Function1.apply$mcIF$sp$(this, v1);
                }

                public long apply$mcJF$sp(float v1) {
                    return Function1.apply$mcJF$sp$(this, v1);
                }

                public void apply$mcVF$sp(float v1) {
                    Function1.apply$mcVF$sp$(this, v1);
                }

                public boolean apply$mcZI$sp(int v1) {
                    return Function1.apply$mcZI$sp$(this, v1);
                }

                public double apply$mcDI$sp(int v1) {
                    return Function1.apply$mcDI$sp$(this, v1);
                }

                public float apply$mcFI$sp(int v1) {
                    return Function1.apply$mcFI$sp$(this, v1);
                }

                public int apply$mcII$sp(int v1) {
                    return Function1.apply$mcII$sp$(this, v1);
                }

                public long apply$mcJI$sp(int v1) {
                    return Function1.apply$mcJI$sp$(this, v1);
                }

                public void apply$mcVI$sp(int v1) {
                    Function1.apply$mcVI$sp$(this, v1);
                }

                public boolean apply$mcZJ$sp(long v1) {
                    return Function1.apply$mcZJ$sp$(this, v1);
                }

                public double apply$mcDJ$sp(long v1) {
                    return Function1.apply$mcDJ$sp$(this, v1);
                }

                public float apply$mcFJ$sp(long v1) {
                    return Function1.apply$mcFJ$sp$(this, v1);
                }

                public int apply$mcIJ$sp(long v1) {
                    return Function1.apply$mcIJ$sp$(this, v1);
                }

                public long apply$mcJJ$sp(long v1) {
                    return Function1.apply$mcJJ$sp$(this, v1);
                }

                public void apply$mcVJ$sp(long v1) {
                    Function1.apply$mcVJ$sp$(this, v1);
                }

                public <A$> Function1<A$, U> compose(Function1<A$, Throwable> g2) {
                    return Function1.compose$(this, g2);
                }

                public String toString() {
                    return Function1.toString$(this);
                }

                public boolean isDefinedAt(Throwable x) {
                    return this.$outer.pf().isDefinedAt(x);
                }

                public U apply(Throwable x) {
                    return (U)this.f$2.apply(x);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.f$2 = f$2;
                }
            };
            return new Catch<T>(pf2, this.fin(), this.rethrow());
        }

        public Catch<Option<T>> toOption() {
            return this.withApply((Function1<Throwable, None$> & Serializable)x$3 -> None$.MODULE$);
        }

        public Catch<Either<Throwable, T>> toEither() {
            return this.withApply((Function1<Throwable, Left> & Serializable)x$4 -> new Left((Throwable)x$4));
        }

        public Catch<Try<T>> toTry() {
            return this.withApply((Function1<Throwable, Failure> & Serializable)x -> new Failure((Throwable)x));
        }

        public static final /* synthetic */ Finally $anonfun$andFinally$1(Function0 body$1, Finally x$2) {
            return x$2.and(body$1);
        }

        public static final /* synthetic */ Finally $anonfun$andFinally$2(Function0 body$1) {
            return new Finally(body$1);
        }

        public Catch(PartialFunction<Throwable, T> pf, Option<Finally> fin, Function1<Throwable, Object> rethrow) {
            this.pf = pf;
            this.fin = fin;
            this.rethrow = rethrow;
            this.scala$util$control$Exception$Described$$_desc_$eq("");
            this.name = "Catch";
        }

        public static final /* synthetic */ Object $anonfun$apply$1$adapted(Finally x$1) {
            x$1.invoke();
            return BoxedUnit.UNIT;
        }
    }

    public static interface Described {
        public String name();

        public String scala$util$control$Exception$Described$$_desc();

        public void scala$util$control$Exception$Described$$_desc_$eq(String var1);

        default public String desc() {
            return this.scala$util$control$Exception$Described$$_desc();
        }

        default public Described withDesc(String s2) {
            this.scala$util$control$Exception$Described$$_desc_$eq(s2);
            return this;
        }

        default public String toString() {
            return new StringBuilder(2).append(this.name()).append("(").append(this.desc()).append(")").toString();
        }

        public static void $init$(Described $this) {
            $this.scala$util$control$Exception$Described$$_desc_$eq("");
        }
    }

    public static class Finally
    implements Described {
        private final Function0<BoxedUnit> body;
        private final String name;
        private String scala$util$control$Exception$Described$$_desc;

        @Override
        public String desc() {
            return ((Described)this).desc();
        }

        @Override
        public Described withDesc(String s2) {
            return ((Described)this).withDesc(s2);
        }

        @Override
        public String toString() {
            return ((Described)this).toString();
        }

        @Override
        public String scala$util$control$Exception$Described$$_desc() {
            return this.scala$util$control$Exception$Described$$_desc;
        }

        @Override
        public void scala$util$control$Exception$Described$$_desc_$eq(String x$1) {
            this.scala$util$control$Exception$Described$$_desc = x$1;
        }

        @Override
        public String name() {
            return this.name;
        }

        public Finally and(Function0<BoxedUnit> other) {
            return new Finally(() -> {
                $this.body.apply$mcV$sp();
                other.apply$mcV$sp();
            });
        }

        public void invoke() {
            this.body.apply$mcV$sp();
        }

        public Finally(Function0<BoxedUnit> body2) {
            this.body = body2;
            this.scala$util$control$Exception$Described$$_desc_$eq("");
            this.name = "Finally";
        }
    }
}

