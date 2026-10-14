/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Option;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.View;
import scala.collection.View$Empty$;
import scala.collection.immutable.NumericRange;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\ruga\u0002\u0015*!\u0003\r\tA\f\u0005\u0006w\u0001!\t\u0001\u0010\u0005\u0006\u0001\u00021\t!\u0011\u0005\u00065\u00021\ta\u0017\u0005\u0006A\u0002!\t!\u0019\u0005\u0006W\u0002!\t\u0001\u001c\u0005\u0006}\u0002!\ta \u0005\b\u0003K\u0001A\u0011AA\u0014\u0011\u001d\t)\u0003\u0001C\u0001\u0003\u0007Bq!!\u0018\u0001\r\u0003\ty\u0006C\u0004\u0002v\u0001!\t!a\u001e\t\u000f\u0005U\u0004\u0001\"\u0001\u0002\u0012\"9\u0011Q\u000f\u0001\u0005\u0002\u0005}\u0006bBA;\u0001\u0011\u0005\u0011Q\u001c\u0005\b\u0003k\u0002A\u0011AA\u0000\u0011\u001d\u0011)\u0003\u0001C\u0001\u0005OAqA!\n\u0001\t\u0003\u0011I\u0004C\u0004\u0003&\u0001!\tA!\u0016\t\u000f\t\u0015\u0002\u0001\"\u0001\u0003v!9!Q\u0005\u0001\u0005\u0002\te\u0005b\u0002Ba\u0001\u0011\u0005!1\u0019\u0005\b\u00053\u0004A1\u0001Bn\u000f\u001d\u0011Y/\u000bE\u0001\u0005[4a\u0001K\u0015\t\u0002\t=\bb\u0002B\u0000/\u0011\u00051\u0011\u0001\u0005\b\u0007\u00079B1AB\u0003\r!\u0019\tc\u0006Q\u0001\n\r\r\u0002BCB\u000e5\t\u0005\t\u0015!\u0003\u00048!9!q \u000e\u0005\u0002\rm\u0002bBB\"5\u0011\u00051Q\t\u0005\b\u0003;RB\u0011AB'\u0011\u001d\u0019if\u0006C\u0002\u0007?2aa! \u0018\u0001\r}\u0004BCBHA\t\u0005\t\u0015!\u0003\u0004\u0004\"9!q \u0011\u0005\u0002\rE\u0005B\u00021!\t\u0003\u001a9\n\u0003\u0004[A\u0011\u00051Q\u0015\u0005\u0007\u0001\u0002\"\taa,\t\u000f\u0005u\u0003\u0005\"\u0001\u0004@\"I1QZ\f\u0002\u0002\u0013%1q\u001a\u0002\u0010\u0013R,'/\u00192mK\u001a\u000b7\r^8ss*\u0011!fK\u0001\u000bG>dG.Z2uS>t'\"\u0001\u0017\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011q&R\n\u0004\u0001A\"\u0004CA\u00193\u001b\u0005Y\u0013BA\u001a,\u0005\u0019\te.\u001f*fMB\u0011Q\u0007\u000f\b\u0003cYJ!aN\u0016\u0002\u000fA\f7m[1hK&\u0011\u0011H\u000f\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003o-\na\u0001J5oSR$C#A\u001f\u0011\u0005Er\u0014BA ,\u0005\u0011)f.\u001b;\u0002\t\u0019\u0014x.\\\u000b\u0003\u0005J#\"a\u0011+\u0011\u0007\u0011+\u0015\u000b\u0004\u0001\u0005\r\u0019\u0003AQ1\u0001H\u0005\t\u00195)\u0006\u0002I\u001fF\u0011\u0011\n\u0014\t\u0003c)K!aS\u0016\u0003\u000f9{G\u000f[5oOB\u0011\u0011'T\u0005\u0003\u001d.\u00121!\u00118z\t\u0015\u0001VI1\u0001I\u0005\u0011yF\u0005J\u0019\u0011\u0005\u0011\u0013F!B*\u0003\u0005\u0004A%!A!\t\u000bU\u0013\u0001\u0019\u0001,\u0002\rM|WO]2f!\r9\u0006,U\u0007\u0002S%\u0011\u0011,\u000b\u0002\r\u0013R,'/\u00192mK>s7-Z\u0001\u0006K6\u0004H/_\u000b\u00039~+\u0012!\u0018\t\u0004\t\u0016s\u0006C\u0001#`\t\u0015\u00196A1\u0001I\u0003\u0015\t\u0007\u000f\u001d7z+\t\u0011W\r\u0006\u0002dMB\u0019A)\u00123\u0011\u0005\u0011+G!B*\u0005\u0005\u0004A\u0005\"B4\u0005\u0001\u0004A\u0017!B3mK6\u001c\bcA\u0019jI&\u0011!n\u000b\u0002\u000byI,\u0007/Z1uK\u0012t\u0014aB5uKJ\fG/Z\u000b\u0003[F$2A\\<z)\ty'\u000fE\u0002E\u000bB\u0004\"\u0001R9\u0005\u000bM+!\u0019\u0001%\t\u000bM,\u0001\u0019\u0001;\u0002\u0003\u0019\u0004B!M;qa&\u0011ao\u000b\u0002\n\rVt7\r^5p]FBQ\u0001_\u0003A\u0002A\fQa\u001d;beRDQA_\u0003A\u0002m\f1\u0001\\3o!\t\tD0\u0003\u0002~W\t\u0019\u0011J\u001c;\u0002\rUtgm\u001c7e+\u0019\t\t!!\u0003\u0002\u0012Q!\u00111AA\u0011)\u0011\t)!a\u0003\u0011\t\u0011+\u0015q\u0001\t\u0004\t\u0006%A!B*\u0007\u0005\u0004A\u0005BB:\u0007\u0001\u0004\ti\u0001\u0005\u00042k\u0006=\u0011Q\u0003\t\u0004\t\u0006EAABA\n\r\t\u0007\u0001JA\u0001T!\u0015\t\u0014qCA\u000e\u0013\r\tIb\u000b\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000fE\ni\"a\u0002\u0002\u0010%\u0019\u0011qD\u0016\u0003\rQ+\b\u000f\\33\u0011\u001d\t\u0019C\u0002a\u0001\u0003\u001f\tA!\u001b8ji\u0006)!/\u00198hKV!\u0011\u0011FA\u0019)\u0019\tY#!\u0010\u0002@Q!\u0011QFA\u001a!\u0011!U)a\f\u0011\u0007\u0011\u000b\t\u0004B\u0003T\u000f\t\u0007\u0001\nC\u0005\u00026\u001d\t\t\u0011q\u0001\u00028\u0005QQM^5eK:\u001cW\rJ\u001a\u0011\u000bU\nI$a\f\n\u0007\u0005m\"H\u0001\u0005J]R,wM]1m\u0011\u0019Ax\u00011\u0001\u00020!9\u0011\u0011I\u0004A\u0002\u0005=\u0012aA3oIV!\u0011QIA')!\t9%!\u0016\u0002X\u0005eC\u0003BA%\u0003\u001f\u0002B\u0001R#\u0002LA\u0019A)!\u0014\u0005\u000bMC!\u0019\u0001%\t\u0013\u0005E\u0003\"!AA\u0004\u0005M\u0013AC3wS\u0012,gnY3%iA)Q'!\u000f\u0002L!1\u0001\u0010\u0003a\u0001\u0003\u0017Bq!!\u0011\t\u0001\u0004\tY\u0005C\u0004\u0002\\!\u0001\r!a\u0013\u0002\tM$X\r]\u0001\u000b]\u0016<()^5mI\u0016\u0014X\u0003BA1\u0003c*\"!a\u0019\u0011\u0011\u0005\u0015\u00141NA8\u0003gj!!a\u001a\u000b\u0007\u0005%\u0014&A\u0004nkR\f'\r\\3\n\t\u00055\u0014q\r\u0002\b\u0005VLG\u000eZ3s!\r!\u0015\u0011\u000f\u0003\u0006'&\u0011\r\u0001\u0013\t\u0005\t\u0016\u000by'\u0001\u0003gS2dW\u0003BA=\u0003\u0003#B!a\u001f\u0002\u000eR!\u0011QPAB!\u0011!U)a \u0011\u0007\u0011\u000b\t\tB\u0003T\u0015\t\u0007\u0001\n\u0003\u0005\u0002\u0006*!\t\u0019AAD\u0003\u0011)G.Z7\u0011\u000bE\nI)a \n\u0007\u0005-5F\u0001\u0005=Eft\u0017-\\3?\u0011\u0019\tyI\u0003a\u0001w\u0006\ta.\u0006\u0003\u0002\u0014\u0006}ECBAK\u0003o\u000bY\f\u0006\u0003\u0002\u0018\u0006M\u0006\u0003\u0002#F\u00033SC!a'\u0002\"B!A)RAO!\r!\u0015q\u0014\u0003\u0006'.\u0011\r\u0001S\u0016\u0003\u0003G\u0003B!!*\u000206\u0011\u0011q\u0015\u0006\u0005\u0003S\u000bY+A\u0005v]\u000eDWmY6fI*\u0019\u0011QV\u0016\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u00022\u0006\u001d&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\"A\u0011QQ\u0006\u0005\u0002\u0004\t)\fE\u00032\u0003\u0013\u000bi\n\u0003\u0004\u0002:.\u0001\ra_\u0001\u0003]FBa!!0\f\u0001\u0004Y\u0018A\u000183+\u0011\t\t-a4\u0015\u0011\u0005\r\u0017Q[Al\u00033$B!!2\u0002RB!A)RAdU\u0011\tI-!)\u0011\t\u0011+\u00151\u001a\t\u0005\t\u0016\u000bi\rE\u0002E\u0003\u001f$Qa\u0015\u0007C\u0002!C\u0001\"!\"\r\t\u0003\u0007\u00111\u001b\t\u0006c\u0005%\u0015Q\u001a\u0005\u0007\u0003sc\u0001\u0019A>\t\r\u0005uF\u00021\u0001|\u0011\u0019\tY\u000e\u0004a\u0001w\u0006\u0011anM\u000b\u0005\u0003?\fy\u000f\u0006\u0006\u0002b\u0006U\u0018q_A}\u0003w$B!a9\u0002rB!A)RAsU\u0011\t9/!)\u0011\t\u0011+\u0015\u0011\u001e\t\u0005\t\u0016\u000bY\u000f\u0005\u0003E\u000b\u00065\bc\u0001#\u0002p\u0012)1+\u0004b\u0001\u0011\"A\u0011QQ\u0007\u0005\u0002\u0004\t\u0019\u0010E\u00032\u0003\u0013\u000bi\u000f\u0003\u0004\u0002:6\u0001\ra\u001f\u0005\u0007\u0003{k\u0001\u0019A>\t\r\u0005mW\u00021\u0001|\u0011\u0019\ti0\u0004a\u0001w\u0006\u0011a\u000eN\u000b\u0005\u0005\u0003\u0011\u0019\u0002\u0006\u0007\u0003\u0004\te!1\u0004B\u000f\u0005?\u0011\t\u0003\u0006\u0003\u0003\u0006\tU\u0001\u0003\u0002#F\u0005\u000fQCA!\u0003\u0002\"B!A)\u0012B\u0006!\u0011!UI!\u0004\u0011\t\u0011+%q\u0002\t\u0005\t\u0016\u0013\t\u0002E\u0002E\u0005'!Qa\u0015\bC\u0002!C\u0001\"!\"\u000f\t\u0003\u0007!q\u0003\t\u0006c\u0005%%\u0011\u0003\u0005\u0007\u0003ss\u0001\u0019A>\t\r\u0005uf\u00021\u0001|\u0011\u0019\tYN\u0004a\u0001w\"1\u0011Q \bA\u0002mDaAa\t\u000f\u0001\u0004Y\u0018A\u000186\u0003!!\u0018MY;mCR,W\u0003\u0002B\u0015\u0005c!BAa\u000b\u00038Q!!Q\u0006B\u001a!\u0011!UIa\f\u0011\u0007\u0011\u0013\t\u0004B\u0003T\u001f\t\u0007\u0001\n\u0003\u0004t\u001f\u0001\u0007!Q\u0007\t\u0006cU\\(q\u0006\u0005\u0007\u0003\u001f{\u0001\u0019A>\u0016\t\tm\"q\t\u000b\u0007\u0005{\u0011\tFa\u0015\u0015\t\t}\"\u0011\n\t\u0005\t\u0016\u0013\tE\u000b\u0003\u0003D\u0005\u0005\u0006\u0003\u0002#F\u0005\u000b\u00022\u0001\u0012B$\t\u0015\u0019\u0006C1\u0001I\u0011\u0019\u0019\b\u00031\u0001\u0003LA9\u0011G!\u0014|w\n\u0015\u0013b\u0001B(W\tIa)\u001e8di&|gN\r\u0005\u0007\u0003s\u0003\u0002\u0019A>\t\r\u0005u\u0006\u00031\u0001|+\u0011\u00119F!\u001a\u0015\u0011\te#q\u000eB9\u0005g\"BAa\u0017\u0003hA!A)\u0012B/U\u0011\u0011y&!)\u0011\t\u0011+%\u0011\r\t\u0005\t\u0016\u0013\u0019\u0007E\u0002E\u0005K\"QaU\tC\u0002!Caa]\tA\u0002\t%\u0004\u0003C\u0019\u0003lm\\8Pa\u0019\n\u0007\t54FA\u0005Gk:\u001cG/[8og!1\u0011\u0011X\tA\u0002mDa!!0\u0012\u0001\u0004Y\bBBAn#\u0001\u000710\u0006\u0003\u0003x\t\u001dEC\u0003B=\u0005#\u0013\u0019J!&\u0003\u0018R!!1\u0010BE!\u0011!UI! +\t\t}\u0014\u0011\u0015\t\u0005\t\u0016\u0013\t\t\u0005\u0003E\u000b\n\r\u0005\u0003\u0002#F\u0005\u000b\u00032\u0001\u0012BD\t\u0015\u0019&C1\u0001I\u0011\u0019\u0019(\u00031\u0001\u0003\fBI\u0011G!$|wn\\(QQ\u0005\u0004\u0005\u001f[#!\u0003$v]\u000e$\u0018n\u001c85\u0011\u0019\tIL\u0005a\u0001w\"1\u0011Q\u0018\nA\u0002mDa!a7\u0013\u0001\u0004Y\bBBA\u007f%\u0001\u000710\u0006\u0003\u0003\u001c\n5F\u0003\u0004BO\u0005o\u0013ILa/\u0003>\n}F\u0003\u0002BP\u0005_\u0003B\u0001R#\u0003\"*\"!1UAQ!\u0011!UI!*\u0011\t\u0011+%q\u0015\t\u0005\t\u0016\u0013I\u000b\u0005\u0003E\u000b\n-\u0006c\u0001#\u0003.\u0012)1k\u0005b\u0001\u0011\"11o\u0005a\u0001\u0005c\u0003\"\"\rBZwn\\8p\u001fBV\u0013\r\u0011)l\u000b\u0002\n\rVt7\r^5p]VBa!!/\u0014\u0001\u0004Y\bBBA_'\u0001\u00071\u0010\u0003\u0004\u0002\\N\u0001\ra\u001f\u0005\u0007\u0003{\u001c\u0002\u0019A>\t\r\t\r2\u00031\u0001|\u0003\u0019\u0019wN\\2biV!!Q\u0019Bf)\u0011\u00119M!4\u0011\t\u0011+%\u0011\u001a\t\u0004\t\n-G!B*\u0015\u0005\u0004A\u0005b\u0002Bh)\u0001\u0007!\u0011[\u0001\u0004qN\u001c\b\u0003B\u0019j\u0005'\u0004Ra\u0016Bk\u0005\u0013L1Aa6*\u0005!IE/\u001a:bE2,\u0017aD5uKJ\f'\r\\3GC\u000e$xN]=\u0016\t\tu'q]\u000b\u0003\u0005?\u0004ra\u0016Bq\u0005K\u0014I/C\u0002\u0003d&\u0012qAR1di>\u0014\u0018\u0010E\u0002E\u0005O$QaU\u000bC\u0002!\u0003B\u0001R#\u0003f\u0006y\u0011\n^3sC\ndWMR1di>\u0014\u0018\u0010\u0005\u0002X/M!q\u0003\rBy!\u0011\u0011\u0019P!@\u000e\u0005\tU(\u0002\u0002B|\u0005s\f!![8\u000b\u0005\tm\u0018\u0001\u00026bm\u0006L1!\u000fB{\u0003\u0019a\u0014N\\5u}Q\u0011!Q^\u0001\ni>4\u0015m\u0019;pef,baa\u0002\u0004\u000e\rEA\u0003BB\u0005\u00073\u0001ra\u0016Bq\u0007\u0017\u0019y\u0001E\u0002E\u0007\u001b!QaU\rC\u0002!\u0003R\u0001RB\t\u0007\u0017!aAR\rC\u0002\rMQc\u0001%\u0004\u0016\u001191qCB\t\u0005\u0004A%\u0001B0%IIBqaa\u0007\u001a\u0001\u0004\u0019i\"A\u0004gC\u000e$xN]=\u0011\t]\u00031q\u0004\t\u0004\t\u000eE!!\u0003+p\r\u0006\u001cGo\u001c:z+\u0019\u0019)ca\u000b\u00040M)!\u0004MB\u0014iA9qK!9\u0004*\r5\u0002c\u0001#\u0004,\u0011)1K\u0007b\u0001\u0011B)Aia\f\u0004*\u00111aI\u0007b\u0001\u0007c)2\u0001SB\u001a\t\u001d\u0019)da\fC\u0002!\u0013Aa\u0018\u0013%gA!q\u000bAB\u001d!\r!5q\u0006\u000b\u0005\u0007{\u0019\t\u0005E\u0004\u0004@i\u0019Ic!\u000f\u000e\u0003]Aqaa\u0007\u001d\u0001\u0004\u00199$\u0001\u0007ge>l7\u000b]3dS\u001aL7\r\u0006\u0003\u0004.\r\u001d\u0003bBB%;\u0001\u000711J\u0001\u0003SR\u0004Ba\u0016-\u0004*U\u00111q\n\t\t\u0003K\nYg!\u000b\u0004.!:!da\u0015\u0004Z\rm\u0003cA\u0019\u0004V%\u00191qK\u0016\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0002\u0002\u0017Q|')^5mI\u001a\u0013x.\\\u000b\u0007\u0007C\u001aYga\u001c\u0015\t\r\r4q\u000f\t\t/\u000e\u0015Dj!\u001b\u0004n%\u00191qM\u0015\u0003\u0013\t+\u0018\u000e\u001c3Ge>l\u0007c\u0001#\u0004l\u0011)1k\bb\u0001\u0011B)Aia\u001c\u0004j\u00111ai\bb\u0001\u0007c*2\u0001SB:\t\u001d\u0019)ha\u001cC\u0002!\u0013Aa\u0018\u0013%i!911D\u0010A\u0002\re\u0004\u0003B,\u0001\u0007w\u00022\u0001RB8\u0005!!U\r\\3hCR,W\u0003BBA\u0007\u000f\u001bB\u0001\t\u0019\u0004\u0004B!q\u000bABC!\r!5q\u0011\u0003\u0007\r\u0002\u0012\ra!#\u0016\u0007!\u001bY\tB\u0004\u0004\u000e\u000e\u001d%\u0019\u0001%\u0003\t}#C%N\u0001\tI\u0016dWmZ1uKR!11SBK!\u0015\u0019y\u0004IBC\u0011\u001d\u0019yI\ta\u0001\u0007\u0007+Ba!'\u0004 R!11TBQ!\u0015!5qQBO!\r!5q\u0014\u0003\u0006'\u000e\u0012\r\u0001\u0013\u0005\u0007O\u000e\u0002\raa)\u0011\tEJ7QT\u000b\u0005\u0007O\u001bi+\u0006\u0002\u0004*B)Aia\"\u0004,B\u0019Ai!,\u0005\u000bM##\u0019\u0001%\u0016\t\rE6q\u0017\u000b\u0005\u0007g\u001bY\fE\u0003E\u0007\u000f\u001b)\fE\u0002E\u0007o#aa!/&\u0005\u0004A%!A#\t\u000f\r%S\u00051\u0001\u0004>B!q\u000bWB[+\u0011\u0019\tma2\u0016\u0005\r\r\u0007\u0003CA3\u0003W\u001a)m!3\u0011\u0007\u0011\u001b9\rB\u0003TM\t\u0007\u0001\nE\u0003E\u0007\u000f\u001b)\rK\u0004!\u0007'\u001aIfa\u0017\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\rE\u0007\u0003BBj\u00073l!a!6\u000b\t\r]'\u0011`\u0001\u0005Y\u0006tw-\u0003\u0003\u0004\\\u000eU'AB(cU\u0016\u001cG\u000f")
public interface IterableFactory<CC>
extends Serializable {
    public static <A, CC> BuildFrom<Object, A, CC> toBuildFrom(IterableFactory<CC> factory2) {
        return new BuildFrom<Object, A, CC>(factory2){
            private final IterableFactory factory$1;

            public Builder<A, CC> apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory<A, CC> toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public CC fromSpecific(Object from, IterableOnce<A> it) {
                return this.factory$1.from(it);
            }

            public Builder<A, CC> newBuilder(Object from) {
                return this.factory$1.newBuilder();
            }
            {
                this.factory$1 = factory$1;
            }
        };
    }

    public static <A, CC> Factory<A, CC> toFactory(IterableFactory<CC> factory2) {
        return new ToFactory(factory2);
    }

    public <A> CC from(IterableOnce<A> var1);

    public <A> CC empty();

    public static /* synthetic */ Object apply$(IterableFactory $this, Seq elems) {
        return $this.apply(elems);
    }

    default public <A> CC apply(Seq<A> elems) {
        return this.from(elems);
    }

    default public <A> CC iterate(A start2, int len, Function1<A, A> f) {
        return this.from(new View.Iterate<A>(start2, len, f));
    }

    default public <A, S> CC unfold(S init2, Function1<S, Option<Tuple2<A, S>>> f) {
        return this.from(new View.Unfold<A, S>(init2, f));
    }

    default public <A> CC range(A start2, A end, Integral<A> evidence$3) {
        Object apply_step = evidence$3.one();
        Object var4_4 = null;
        return this.from(new NumericRange.Exclusive<A>(start2, end, apply_step, evidence$3));
    }

    default public <A> CC range(A start2, A end, A step, Integral<A> evidence$4) {
        return this.from(new NumericRange.Exclusive<A>(start2, end, step, evidence$4));
    }

    public <A> Builder<A, CC> newBuilder();

    default public <A> CC fill(int n, Function0<A> elem) {
        return this.from(new View.Fill<A>(n, elem));
    }

    default public <A> CC fill(int n1, int n2, Function0<A> elem) {
        return this.fill(n1, (Function0<Object> & Serializable)() -> this.fill(n2, elem));
    }

    default public <A> CC fill(int n1, int n2, int n3, Function0<A> elem) {
        return this.fill(n1, (Function0<Object> & Serializable)() -> this.fill(n2, n3, elem));
    }

    default public <A> CC fill(int n1, int n2, int n3, int n4, Function0<A> elem) {
        return this.fill(n1, (Function0<Object> & Serializable)() -> this.fill(n2, n3, n4, elem));
    }

    default public <A> CC fill(int n1, int n2, int n3, int n4, int n5, Function0<A> elem) {
        return this.fill(n1, (Function0<Object> & Serializable)() -> this.fill(n2, n3, n4, n5, elem));
    }

    default public <A> CC tabulate(int n, Function1<Object, A> f) {
        return this.from(new View.Tabulate<A>(n, f));
    }

    default public <A> CC tabulate(int n1, int n2, Function2<Object, Object, A> f) {
        return this.tabulate(n1, (Function1<Object, Object> & Serializable)i1 -> this.tabulate(n2, (Function1<Object, Object> & Serializable)x$1 -> f$1.apply(BoxesRunTime.boxToInteger(i1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$1)))));
    }

    default public <A> CC tabulate(int n1, int n2, int n3, Function3<Object, Object, Object, A> f) {
        return this.tabulate(n1, (Function1<Object, Object> & Serializable)i1 -> this.tabulate(n2, n3, (Function2<Object, Object, Object> & Serializable)(x$2, x$3) -> f$2.apply(BoxesRunTime.boxToInteger(i1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$2)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$3)))));
    }

    default public <A> CC tabulate(int n1, int n2, int n3, int n4, Function4<Object, Object, Object, Object, A> f) {
        return this.tabulate(n1, (Function1<Object, Object> & Serializable)i1 -> this.tabulate(n2, n3, n4, (Function3<Object, Object, Object, Object> & Serializable)(x$4, x$5, x$6) -> f$3.apply(BoxesRunTime.boxToInteger(i1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$4)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$5)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$6)))));
    }

    default public <A> CC tabulate(int n1, int n2, int n3, int n4, int n5, Function5<Object, Object, Object, Object, Object, A> f) {
        return this.tabulate(n1, (Function1<Object, Object> & Serializable)i1 -> this.tabulate(n2, n3, n4, n5, (Function4<Object, Object, Object, Object, Object> & Serializable)(x$7, x$8, x$9, x$10) -> f$4.apply(BoxesRunTime.boxToInteger(i1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$7)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$8)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$9)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$10)))));
    }

    default public <A> CC concat(Seq<Iterable<A>> xss) {
        return this.from(xss.foldLeft(View$Empty$.MODULE$, (Function2<View, Iterable, View> & Serializable)(x$11, x$12) -> (View)x$11.$plus$plus(x$12)));
    }

    default public <A> Factory<A, CC> iterableFactory() {
        return new ToFactory(this);
    }

    public static void $init$(IterableFactory $this) {
    }

    public static class Delegate<CC>
    implements IterableFactory<CC> {
        private static final long serialVersionUID = 3L;
        private final IterableFactory<CC> delegate;

        @Override
        public <A> CC iterate(A start2, int len, Function1<A, A> f) {
            return (CC)((IterableFactory)this).iterate(start2, len, f);
        }

        @Override
        public <A, S> CC unfold(S init2, Function1<S, Option<Tuple2<A, S>>> f) {
            return (CC)((IterableFactory)this).unfold(init2, f);
        }

        @Override
        public <A> CC range(A start2, A end, Integral<A> evidence$3) {
            return (CC)((IterableFactory)this).range(start2, end, evidence$3);
        }

        @Override
        public <A> CC range(A start2, A end, A step, Integral<A> evidence$4) {
            return (CC)((IterableFactory)this).range(start2, end, step, evidence$4);
        }

        @Override
        public <A> CC fill(int n, Function0<A> elem) {
            return (CC)((IterableFactory)this).fill(n, elem);
        }

        @Override
        public <A> CC fill(int n1, int n2, Function0<A> elem) {
            return (CC)((IterableFactory)this).fill(n1, n2, elem);
        }

        @Override
        public <A> CC fill(int n1, int n2, int n3, Function0<A> elem) {
            return (CC)((IterableFactory)this).fill(n1, n2, n3, elem);
        }

        @Override
        public <A> CC fill(int n1, int n2, int n3, int n4, Function0<A> elem) {
            return (CC)((IterableFactory)this).fill(n1, n2, n3, n4, elem);
        }

        @Override
        public <A> CC fill(int n1, int n2, int n3, int n4, int n5, Function0<A> elem) {
            return (CC)((IterableFactory)this).fill(n1, n2, n3, n4, n5, elem);
        }

        @Override
        public <A> CC tabulate(int n, Function1<Object, A> f) {
            return (CC)((IterableFactory)this).tabulate(n, f);
        }

        @Override
        public <A> CC tabulate(int n1, int n2, Function2<Object, Object, A> f) {
            return (CC)((IterableFactory)this).tabulate(n1, n2, f);
        }

        @Override
        public <A> CC tabulate(int n1, int n2, int n3, Function3<Object, Object, Object, A> f) {
            return (CC)((IterableFactory)this).tabulate(n1, n2, n3, f);
        }

        @Override
        public <A> CC tabulate(int n1, int n2, int n3, int n4, Function4<Object, Object, Object, Object, A> f) {
            return (CC)((IterableFactory)this).tabulate(n1, n2, n3, n4, f);
        }

        @Override
        public <A> CC tabulate(int n1, int n2, int n3, int n4, int n5, Function5<Object, Object, Object, Object, Object, A> f) {
            return (CC)((IterableFactory)this).tabulate(n1, n2, n3, n4, n5, f);
        }

        @Override
        public <A> CC concat(Seq<Iterable<A>> xss) {
            return (CC)((IterableFactory)this).concat(xss);
        }

        @Override
        public <A> Factory<A, CC> iterableFactory() {
            return ((IterableFactory)this).iterableFactory();
        }

        @Override
        public <A> CC apply(Seq<A> elems) {
            return this.delegate.apply(elems);
        }

        @Override
        public <A> CC empty() {
            return this.delegate.empty();
        }

        @Override
        public <E> CC from(IterableOnce<E> it) {
            return this.delegate.from(it);
        }

        @Override
        public <A> Builder<A, CC> newBuilder() {
            return this.delegate.newBuilder();
        }

        public Delegate(IterableFactory<CC> delegate) {
            this.delegate = delegate;
        }
    }

    private static class ToFactory<A, CC>
    implements Factory<A, CC>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final IterableFactory<CC> factory;

        @Override
        public CC fromSpecific(IterableOnce<A> it) {
            return this.factory.from(it);
        }

        @Override
        public Builder<A, CC> newBuilder() {
            return this.factory.newBuilder();
        }

        public ToFactory(IterableFactory<CC> factory2) {
            this.factory = factory2;
        }
    }
}

