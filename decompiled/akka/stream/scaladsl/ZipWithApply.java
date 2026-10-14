/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.ZipWith10;
import akka.stream.scaladsl.ZipWith11;
import akka.stream.scaladsl.ZipWith12;
import akka.stream.scaladsl.ZipWith13;
import akka.stream.scaladsl.ZipWith14;
import akka.stream.scaladsl.ZipWith15;
import akka.stream.scaladsl.ZipWith16;
import akka.stream.scaladsl.ZipWith17;
import akka.stream.scaladsl.ZipWith18;
import akka.stream.scaladsl.ZipWith19;
import akka.stream.scaladsl.ZipWith2;
import akka.stream.scaladsl.ZipWith20;
import akka.stream.scaladsl.ZipWith21;
import akka.stream.scaladsl.ZipWith22;
import akka.stream.scaladsl.ZipWith3;
import akka.stream.scaladsl.ZipWith4;
import akka.stream.scaladsl.ZipWith5;
import akka.stream.scaladsl.ZipWith6;
import akka.stream.scaladsl.ZipWith7;
import akka.stream.scaladsl.ZipWith8;
import akka.stream.scaladsl.ZipWith9;
import scala.Function10;
import scala.Function11;
import scala.Function12;
import scala.Function13;
import scala.Function14;
import scala.Function15;
import scala.Function16;
import scala.Function17;
import scala.Function18;
import scala.Function19;
import scala.Function2;
import scala.Function20;
import scala.Function21;
import scala.Function22;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Function6;
import scala.Function7;
import scala.Function8;
import scala.Function9;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0019ecaB\f\u0019!\u0003\r\ta\b\u0005\u0006M\u0001!\ta\n\u0005\u0006W\u0001!\t\u0001\f\u0005\u0006W\u0001!\t\u0001\u0013\u0005\u0006W\u0001!\tA\u0017\u0005\u0006W\u0001!\tA\u001c\u0005\u0007W\u0001!\t!!\u0003\t\r-\u0002A\u0011AA\u001d\u0011\u0019Y\u0003\u0001\"\u0001\u0002n!11\u0006\u0001C\u0001\u0003KCaa\u000b\u0001\u0005\u0002\u0005\u0005\bBB\u0016\u0001\t\u0003\u0011\t\u0003\u0003\u0004,\u0001\u0011\u0005!Q\r\u0005\u0007W\u0001!\tA!,\t\r-\u0002A\u0011\u0001B}\u0011\u0019Y\u0003\u0001\"\u0001\u0004J!11\u0006\u0001C\u0001\u0007;Caa\u000b\u0001\u0005\u0002\rU\bBB\u0016\u0001\t\u0003!\t\u0006\u0003\u0004,\u0001\u0011\u0005A\u0011\u0017\u0005\u0007W\u0001!\t!\"\u0006\t\r-\u0002A\u0011AC?\u0011\u0019Y\u0003\u0001\"\u0001\u0006j\na!,\u001b9XSRD\u0017\t\u001d9ms*\u0011\u0011DG\u0001\tg\u000e\fG.\u00193tY*\u00111\u0004H\u0001\u0007gR\u0014X-Y7\u000b\u0003u\tA!Y6lC\u000e\u00011C\u0001\u0001!!\t\tC%D\u0001#\u0015\u0005\u0019\u0013!B:dC2\f\u0017BA\u0013#\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012\u0001\u000b\t\u0003C%J!A\u000b\u0012\u0003\tUs\u0017\u000e^\u0001\u0006CB\u0004H._\u000b\u0005[Qr\u0014\t\u0006\u0002/\u0007B)q\u0006\r\u001a>\u00016\t\u0001$\u0003\u000221\tA!,\u001b9XSRD'\u0007\u0005\u00024i1\u0001A!B\u001b\u0003\u0005\u00041$AA!2#\t9$\b\u0005\u0002\"q%\u0011\u0011H\t\u0002\b\u001d>$\b.\u001b8h!\t\t3(\u0003\u0002=E\t\u0019\u0011I\\=\u0011\u0005MrD!B \u0003\u0005\u00041$AA!3!\t\u0019\u0014\tB\u0003C\u0005\t\u0007aGA\u0001P\u0011\u0015!%\u00011\u0001F\u0003\u0019Q\u0018\u000e\u001d9feB)\u0011E\u0012\u001a>\u0001&\u0011qI\t\u0002\n\rVt7\r^5p]J*R!\u0013(Q%V#\"A\u0013,\u0011\r=ZUjT)U\u0013\ta\u0005D\u0001\u0005[SB<\u0016\u000e\u001e54!\t\u0019d\nB\u00036\u0007\t\u0007a\u0007\u0005\u00024!\u0012)qh\u0001b\u0001mA\u00111G\u0015\u0003\u0006'\u000e\u0011\rA\u000e\u0002\u0003\u0003N\u0002\"aM+\u0005\u000b\t\u001b!\u0019\u0001\u001c\t\u000b\u0011\u001b\u0001\u0019A,\u0011\r\u0005BVjT)U\u0013\tI&EA\u0005Gk:\u001cG/[8ogU11\f\u00192eM&$\"\u0001\u00186\u0011\u000f=jv,Y2fQ&\u0011a\f\u0007\u0002\t5&\u0004x+\u001b;iiA\u00111\u0007\u0019\u0003\u0006k\u0011\u0011\rA\u000e\t\u0003g\t$Qa\u0010\u0003C\u0002Y\u0002\"a\r3\u0005\u000bM#!\u0019\u0001\u001c\u0011\u0005M2G!B4\u0005\u0005\u00041$AA!5!\t\u0019\u0014\u000eB\u0003C\t\t\u0007a\u0007C\u0003E\t\u0001\u00071\u000eE\u0004\"Y~\u000b7-\u001a5\n\u00055\u0014#!\u0003$v]\u000e$\u0018n\u001c85+\u001dyGO\u001e={y~$2\u0001]A\u0001!!y\u0013o];xsnt\u0018B\u0001:\u0019\u0005!Q\u0016\u000e],ji\",\u0004CA\u001au\t\u0015)TA1\u00017!\t\u0019d\u000fB\u0003@\u000b\t\u0007a\u0007\u0005\u00024q\u0012)1+\u0002b\u0001mA\u00111G\u001f\u0003\u0006O\u0016\u0011\rA\u000e\t\u0003gq$Q!`\u0003C\u0002Y\u0012!!Q\u001b\u0011\u0005MzH!\u0002\"\u0006\u0005\u00041\u0004B\u0002#\u0006\u0001\u0004\t\u0019\u0001E\u0005\"\u0003\u000b\u0019Xo^=|}&\u0019\u0011q\u0001\u0012\u0003\u0013\u0019+hn\u0019;j_:,T\u0003EA\u0006\u0003+\tI\"!\b\u0002\"\u0005\u0015\u0012\u0011FA\u0018)\u0011\ti!!\r\u0011#=\ny!a\u0005\u0002\u0018\u0005m\u0011qDA\u0012\u0003O\ti#C\u0002\u0002\u0012a\u0011\u0001BW5q/&$\bN\u000e\t\u0004g\u0005UA!B\u001b\u0007\u0005\u00041\u0004cA\u001a\u0002\u001a\u0011)qH\u0002b\u0001mA\u00191'!\b\u0005\u000bM3!\u0019\u0001\u001c\u0011\u0007M\n\t\u0003B\u0003h\r\t\u0007a\u0007E\u00024\u0003K!Q! \u0004C\u0002Y\u00022aMA\u0015\t\u0019\tYC\u0002b\u0001m\t\u0011\u0011I\u000e\t\u0004g\u0005=B!\u0002\"\u0007\u0005\u00041\u0004B\u0002#\u0007\u0001\u0004\t\u0019\u0004E\t\"\u0003k\t\u0019\"a\u0006\u0002\u001c\u0005}\u00111EA\u0014\u0003[I1!a\u000e#\u0005%1UO\\2uS>tg'\u0006\n\u0002<\u0005\u0015\u0013\u0011JA'\u0003#\n)&!\u0017\u0002^\u0005\rD\u0003BA\u001f\u0003K\u00022cLA \u0003\u0007\n9%a\u0013\u0002P\u0005M\u0013qKA.\u0003CJ1!!\u0011\u0019\u0005!Q\u0016\u000e],ji\"<\u0004cA\u001a\u0002F\u0011)Qg\u0002b\u0001mA\u00191'!\u0013\u0005\u000b}:!\u0019\u0001\u001c\u0011\u0007M\ni\u0005B\u0003T\u000f\t\u0007a\u0007E\u00024\u0003#\"QaZ\u0004C\u0002Y\u00022aMA+\t\u0015ixA1\u00017!\r\u0019\u0014\u0011\f\u0003\u0007\u0003W9!\u0019\u0001\u001c\u0011\u0007M\ni\u0006\u0002\u0004\u0002`\u001d\u0011\rA\u000e\u0002\u0003\u0003^\u00022aMA2\t\u0015\u0011uA1\u00017\u0011\u0019!u\u00011\u0001\u0002hA\u0019\u0012%!\u001b\u0002D\u0005\u001d\u00131JA(\u0003'\n9&a\u0017\u0002b%\u0019\u00111\u000e\u0012\u0003\u0013\u0019+hn\u0019;j_:<T\u0003FA8\u0003s\ni(!!\u0002\u0006\u0006%\u0015QRAI\u0003+\u000bY\n\u0006\u0003\u0002r\u0005u\u0005#F\u0018\u0002t\u0005]\u00141PA@\u0003\u0007\u000b9)a#\u0002\u0010\u0006M\u0015\u0011T\u0005\u0004\u0003kB\"\u0001\u0003.ja^KG\u000f\u001b\u001d\u0011\u0007M\nI\bB\u00036\u0011\t\u0007a\u0007E\u00024\u0003{\"Qa\u0010\u0005C\u0002Y\u00022aMAA\t\u0015\u0019\u0006B1\u00017!\r\u0019\u0014Q\u0011\u0003\u0006O\"\u0011\rA\u000e\t\u0004g\u0005%E!B?\t\u0005\u00041\u0004cA\u001a\u0002\u000e\u00121\u00111\u0006\u0005C\u0002Y\u00022aMAI\t\u0019\ty\u0006\u0003b\u0001mA\u00191'!&\u0005\r\u0005]\u0005B1\u00017\u0005\t\t\u0005\bE\u00024\u00037#QA\u0011\u0005C\u0002YBa\u0001\u0012\u0005A\u0002\u0005}\u0005#F\u0011\u0002\"\u0006]\u00141PA@\u0003\u0007\u000b9)a#\u0002\u0010\u0006M\u0015\u0011T\u0005\u0004\u0003G\u0013#!\u0003$v]\u000e$\u0018n\u001c89+Y\t9+!-\u00026\u0006e\u0016QXAa\u0003\u000b\fI-!4\u0002R\u0006]G\u0003BAU\u00033\u0004rcLAV\u0003_\u000b\u0019,a.\u0002<\u0006}\u00161YAd\u0003\u0017\fy-!6\n\u0007\u00055\u0006D\u0001\u0005[SB<\u0016\u000e\u001e5:!\r\u0019\u0014\u0011\u0017\u0003\u0006k%\u0011\rA\u000e\t\u0004g\u0005UF!B \n\u0005\u00041\u0004cA\u001a\u0002:\u0012)1+\u0003b\u0001mA\u00191'!0\u0005\u000b\u001dL!\u0019\u0001\u001c\u0011\u0007M\n\t\rB\u0003~\u0013\t\u0007a\u0007E\u00024\u0003\u000b$a!a\u000b\n\u0005\u00041\u0004cA\u001a\u0002J\u00121\u0011qL\u0005C\u0002Y\u00022aMAg\t\u0019\t9*\u0003b\u0001mA\u00191'!5\u0005\r\u0005M\u0017B1\u00017\u0005\t\t\u0015\bE\u00024\u0003/$QAQ\u0005C\u0002YBa\u0001R\u0005A\u0002\u0005m\u0007cF\u0011\u0002^\u0006=\u00161WA\\\u0003w\u000by,a1\u0002H\u0006-\u0017qZAk\u0013\r\tyN\t\u0002\n\rVt7\r^5p]f*\u0002$a9\u0002n\u0006E\u0018Q_A}\u0003{\u0014\tA!\u0002\u0003\n\t5!\u0011\u0003B\f)\u0011\t)O!\u0007\u00113=\n9/a;\u0002p\u0006M\u0018q_A~\u0003\u007f\u0014\u0019Aa\u0002\u0003\f\t=!QC\u0005\u0004\u0003SD\"!\u0003.ja^KG\u000f[\u00191!\r\u0019\u0014Q\u001e\u0003\u0006k)\u0011\rA\u000e\t\u0004g\u0005EH!B \u000b\u0005\u00041\u0004cA\u001a\u0002v\u0012)1K\u0003b\u0001mA\u00191'!?\u0005\u000b\u001dT!\u0019\u0001\u001c\u0011\u0007M\ni\u0010B\u0003~\u0015\t\u0007a\u0007E\u00024\u0005\u0003!a!a\u000b\u000b\u0005\u00041\u0004cA\u001a\u0003\u0006\u00111\u0011q\f\u0006C\u0002Y\u00022a\rB\u0005\t\u0019\t9J\u0003b\u0001mA\u00191G!\u0004\u0005\r\u0005M'B1\u00017!\r\u0019$\u0011\u0003\u0003\u0007\u0005'Q!\u0019\u0001\u001c\u0003\u0007\u0005\u000b\u0004\u0007E\u00024\u0005/!QA\u0011\u0006C\u0002YBa\u0001\u0012\u0006A\u0002\tm\u0001#G\u0011\u0003\u001e\u0005-\u0018q^Az\u0003o\fY0a@\u0003\u0004\t\u001d!1\u0002B\b\u0005+I1Aa\b#\u0005)1UO\\2uS>t\u0017\u0007M\u000b\u001b\u0005G\u0011iC!\r\u00036\te\"Q\bB!\u0005\u000b\u0012IE!\u0014\u0003R\tU#1\f\u000b\u0005\u0005K\u0011i\u0006E\u000e0\u0005O\u0011YCa\f\u00034\t]\"1\bB \u0005\u0007\u00129Ea\u0013\u0003P\tM#\u0011L\u0005\u0004\u0005SA\"!\u0003.ja^KG\u000f[\u00192!\r\u0019$Q\u0006\u0003\u0006k-\u0011\rA\u000e\t\u0004g\tEB!B \f\u0005\u00041\u0004cA\u001a\u00036\u0011)1k\u0003b\u0001mA\u00191G!\u000f\u0005\u000b\u001d\\!\u0019\u0001\u001c\u0011\u0007M\u0012i\u0004B\u0003~\u0017\t\u0007a\u0007E\u00024\u0005\u0003\"a!a\u000b\f\u0005\u00041\u0004cA\u001a\u0003F\u00111\u0011qL\u0006C\u0002Y\u00022a\rB%\t\u0019\t9j\u0003b\u0001mA\u00191G!\u0014\u0005\r\u0005M7B1\u00017!\r\u0019$\u0011\u000b\u0003\u0007\u0005'Y!\u0019\u0001\u001c\u0011\u0007M\u0012)\u0006\u0002\u0004\u0003X-\u0011\rA\u000e\u0002\u0004\u0003F\n\u0004cA\u001a\u0003\\\u0011)!i\u0003b\u0001m!1Ai\u0003a\u0001\u0005?\u00022$\tB1\u0005W\u0011yCa\r\u00038\tm\"q\bB\"\u0005\u000f\u0012YEa\u0014\u0003T\te\u0013b\u0001B2E\tQa)\u001e8di&|g.M\u0019\u00169\t\u001d$\u0011\u000fB;\u0005s\u0012iH!!\u0003\u0006\n%%Q\u0012BI\u0005+\u0013IJ!(\u0003$R!!\u0011\u000eBS!uy#1\u000eB8\u0005g\u00129Ha\u001f\u0003\u0000\t\r%q\u0011BF\u0005\u001f\u0013\u0019Ja&\u0003\u001c\n\u0005\u0016b\u0001B71\tI!,\u001b9XSRD\u0017G\r\t\u0004g\tED!B\u001b\r\u0005\u00041\u0004cA\u001a\u0003v\u0011)q\b\u0004b\u0001mA\u00191G!\u001f\u0005\u000bMc!\u0019\u0001\u001c\u0011\u0007M\u0012i\bB\u0003h\u0019\t\u0007a\u0007E\u00024\u0005\u0003#Q! \u0007C\u0002Y\u00022a\rBC\t\u0019\tY\u0003\u0004b\u0001mA\u00191G!#\u0005\r\u0005}CB1\u00017!\r\u0019$Q\u0012\u0003\u0007\u0003/c!\u0019\u0001\u001c\u0011\u0007M\u0012\t\n\u0002\u0004\u0002T2\u0011\rA\u000e\t\u0004g\tUEA\u0002B\n\u0019\t\u0007a\u0007E\u00024\u00053#aAa\u0016\r\u0005\u00041\u0004cA\u001a\u0003\u001e\u00121!q\u0014\u0007C\u0002Y\u00121!Q\u00193!\r\u0019$1\u0015\u0003\u0006\u00052\u0011\rA\u000e\u0005\u0007\t2\u0001\rAa*\u0011;\u0005\u0012IKa\u001c\u0003t\t]$1\u0010B@\u0005\u0007\u00139Ia#\u0003\u0010\nM%q\u0013BN\u0005CK1Aa+#\u0005)1UO\\2uS>t\u0017GM\u000b\u001f\u0005_\u0013IL!0\u0003B\n\u0015'\u0011\u001aBg\u0005#\u0014)N!7\u0003^\n\u0005(Q\u001dBu\u0005_$BA!-\u0003rByrFa-\u00038\nm&q\u0018Bb\u0005\u000f\u0014YMa4\u0003T\n]'1\u001cBp\u0005G\u00149O!<\n\u0007\tU\u0006DA\u0005[SB<\u0016\u000e\u001e52gA\u00191G!/\u0005\u000bUj!\u0019\u0001\u001c\u0011\u0007M\u0012i\fB\u0003@\u001b\t\u0007a\u0007E\u00024\u0005\u0003$QaU\u0007C\u0002Y\u00022a\rBc\t\u00159WB1\u00017!\r\u0019$\u0011\u001a\u0003\u0006{6\u0011\rA\u000e\t\u0004g\t5GABA\u0016\u001b\t\u0007a\u0007E\u00024\u0005#$a!a\u0018\u000e\u0005\u00041\u0004cA\u001a\u0003V\u00121\u0011qS\u0007C\u0002Y\u00022a\rBm\t\u0019\t\u0019.\u0004b\u0001mA\u00191G!8\u0005\r\tMQB1\u00017!\r\u0019$\u0011\u001d\u0003\u0007\u0005/j!\u0019\u0001\u001c\u0011\u0007M\u0012)\u000f\u0002\u0004\u0003 6\u0011\rA\u000e\t\u0004g\t%HA\u0002Bv\u001b\t\u0007aGA\u0002BcM\u00022a\rBx\t\u0015\u0011UB1\u00017\u0011\u0019!U\u00021\u0001\u0003tBy\u0012E!>\u00038\nm&q\u0018Bb\u0005\u000f\u0014YMa4\u0003T\n]'1\u001cBp\u0005G\u00149O!<\n\u0007\t](E\u0001\u0006Gk:\u001cG/[8ocM*\u0002Ea?\u0004\u0006\r%1QBB\t\u0007+\u0019Ib!\b\u0004\"\r\u00152\u0011FB\u0017\u0007c\u0019)d!\u000f\u0004@Q!!Q`B!!\u0005z#q`B\u0002\u0007\u000f\u0019Yaa\u0004\u0004\u0014\r]11DB\u0010\u0007G\u00199ca\u000b\u00040\rM2qGB\u001f\u0013\r\u0019\t\u0001\u0007\u0002\n5&\u0004x+\u001b;icQ\u00022aMB\u0003\t\u0015)dB1\u00017!\r\u00194\u0011\u0002\u0003\u0006\u007f9\u0011\rA\u000e\t\u0004g\r5A!B*\u000f\u0005\u00041\u0004cA\u001a\u0004\u0012\u0011)qM\u0004b\u0001mA\u00191g!\u0006\u0005\u000but!\u0019\u0001\u001c\u0011\u0007M\u001aI\u0002\u0002\u0004\u0002,9\u0011\rA\u000e\t\u0004g\ruAABA0\u001d\t\u0007a\u0007E\u00024\u0007C!a!a&\u000f\u0005\u00041\u0004cA\u001a\u0004&\u00111\u00111\u001b\bC\u0002Y\u00022aMB\u0015\t\u0019\u0011\u0019B\u0004b\u0001mA\u00191g!\f\u0005\r\t]cB1\u00017!\r\u00194\u0011\u0007\u0003\u0007\u0005?s!\u0019\u0001\u001c\u0011\u0007M\u001a)\u0004\u0002\u0004\u0003l:\u0011\rA\u000e\t\u0004g\reBABB\u001e\u001d\t\u0007aGA\u0002BcQ\u00022aMB \t\u0015\u0011eB1\u00017\u0011\u0019!e\u00021\u0001\u0004DA\t\u0013e!\u0012\u0004\u0004\r\u001d11BB\b\u0007'\u00199ba\u0007\u0004 \r\r2qEB\u0016\u0007_\u0019\u0019da\u000e\u0004>%\u00191q\t\u0012\u0003\u0015\u0019+hn\u0019;j_:\fD'\u0006\u0012\u0004L\rU3\u0011LB/\u0007C\u001a)g!\u001b\u0004n\rE4QOB=\u0007{\u001a\ti!\"\u0004\n\u000e551\u0013\u000b\u0005\u0007\u001b\u001a)\nE\u00120\u0007\u001f\u001a\u0019fa\u0016\u0004\\\r}31MB4\u0007W\u001ayga\u001d\u0004x\rm4qPBB\u0007\u000f\u001bYi!%\n\u0007\rE\u0003DA\u0005[SB<\u0016\u000e\u001e52kA\u00191g!\u0016\u0005\u000bUz!\u0019\u0001\u001c\u0011\u0007M\u001aI\u0006B\u0003@\u001f\t\u0007a\u0007E\u00024\u0007;\"QaU\bC\u0002Y\u00022aMB1\t\u00159wB1\u00017!\r\u00194Q\r\u0003\u0006{>\u0011\rA\u000e\t\u0004g\r%DABA\u0016\u001f\t\u0007a\u0007E\u00024\u0007[\"a!a\u0018\u0010\u0005\u00041\u0004cA\u001a\u0004r\u00111\u0011qS\bC\u0002Y\u00022aMB;\t\u0019\t\u0019n\u0004b\u0001mA\u00191g!\u001f\u0005\r\tMqB1\u00017!\r\u00194Q\u0010\u0003\u0007\u0005/z!\u0019\u0001\u001c\u0011\u0007M\u001a\t\t\u0002\u0004\u0003 >\u0011\rA\u000e\t\u0004g\r\u0015EA\u0002Bv\u001f\t\u0007a\u0007E\u00024\u0007\u0013#aaa\u000f\u0010\u0005\u00041\u0004cA\u001a\u0004\u000e\u001211qR\bC\u0002Y\u00121!Q\u00196!\r\u001941\u0013\u0003\u0006\u0005>\u0011\rA\u000e\u0005\u0007\t>\u0001\raa&\u0011G\u0005\u001aIja\u0015\u0004X\rm3qLB2\u0007O\u001aYga\u001c\u0004t\r]41PB@\u0007\u0007\u001b9ia#\u0004\u0012&\u001911\u0014\u0012\u0003\u0015\u0019+hn\u0019;j_:\fT'\u0006\u0013\u0004 \u000e%6QVBY\u0007k\u001bIl!0\u0004B\u000e\u00157\u0011ZBg\u0007#\u001c)n!7\u0004^\u000e\u00058Q]Bv)\u0011\u0019\tk!<\u0011K=\u001a\u0019ka*\u0004,\u000e=61WB\\\u0007w\u001byla1\u0004H\u000e-7qZBj\u0007/\u001cYna8\u0004d\u000e%\u0018bABS1\tI!,\u001b9XSRD\u0017G\u000e\t\u0004g\r%F!B\u001b\u0011\u0005\u00041\u0004cA\u001a\u0004.\u0012)q\b\u0005b\u0001mA\u00191g!-\u0005\u000bM\u0003\"\u0019\u0001\u001c\u0011\u0007M\u001a)\fB\u0003h!\t\u0007a\u0007E\u00024\u0007s#Q! \tC\u0002Y\u00022aMB_\t\u0019\tY\u0003\u0005b\u0001mA\u00191g!1\u0005\r\u0005}\u0003C1\u00017!\r\u00194Q\u0019\u0003\u0007\u0003/\u0003\"\u0019\u0001\u001c\u0011\u0007M\u001aI\r\u0002\u0004\u0002TB\u0011\rA\u000e\t\u0004g\r5GA\u0002B\n!\t\u0007a\u0007E\u00024\u0007#$aAa\u0016\u0011\u0005\u00041\u0004cA\u001a\u0004V\u00121!q\u0014\tC\u0002Y\u00022aMBm\t\u0019\u0011Y\u000f\u0005b\u0001mA\u00191g!8\u0005\r\rm\u0002C1\u00017!\r\u00194\u0011\u001d\u0003\u0007\u0007\u001f\u0003\"\u0019\u0001\u001c\u0011\u0007M\u001a)\u000f\u0002\u0004\u0004hB\u0011\rA\u000e\u0002\u0004\u0003F2\u0004cA\u001a\u0004l\u0012)!\t\u0005b\u0001m!1A\t\u0005a\u0001\u0007_\u0004R%IBy\u0007O\u001bYka,\u00044\u000e]61XB`\u0007\u0007\u001c9ma3\u0004P\u000eM7q[Bn\u0007?\u001c\u0019o!;\n\u0007\rM(E\u0001\u0006Gk:\u001cG/[8ocY*bea>\u0005\u0002\u0011\u0015A\u0011\u0002C\u0007\t#!)\u0002\"\u0007\u0005\u001e\u0011\u0005BQ\u0005C\u0015\t[!\t\u0004\"\u000e\u0005:\u0011uB\u0011\tC$)\u0011\u0019I\u0010\"\u0013\u0011O=\u001aYpa@\u0005\u0004\u0011\u001dA1\u0002C\b\t'!9\u0002b\u0007\u0005 \u0011\rBq\u0005C\u0016\t_!\u0019\u0004b\u000e\u0005<\u0011}BQI\u0005\u0004\u0007{D\"!\u0003.ja^KG\u000f[\u00198!\r\u0019D\u0011\u0001\u0003\u0006kE\u0011\rA\u000e\t\u0004g\u0011\u0015A!B \u0012\u0005\u00041\u0004cA\u001a\u0005\n\u0011)1+\u0005b\u0001mA\u00191\u0007\"\u0004\u0005\u000b\u001d\f\"\u0019\u0001\u001c\u0011\u0007M\"\t\u0002B\u0003~#\t\u0007a\u0007E\u00024\t+!a!a\u000b\u0012\u0005\u00041\u0004cA\u001a\u0005\u001a\u00111\u0011qL\tC\u0002Y\u00022a\rC\u000f\t\u0019\t9*\u0005b\u0001mA\u00191\u0007\"\t\u0005\r\u0005M\u0017C1\u00017!\r\u0019DQ\u0005\u0003\u0007\u0005'\t\"\u0019\u0001\u001c\u0011\u0007M\"I\u0003\u0002\u0004\u0003XE\u0011\rA\u000e\t\u0004g\u00115BA\u0002BP#\t\u0007a\u0007E\u00024\tc!aAa;\u0012\u0005\u00041\u0004cA\u001a\u00056\u0011111H\tC\u0002Y\u00022a\rC\u001d\t\u0019\u0019y)\u0005b\u0001mA\u00191\u0007\"\u0010\u0005\r\r\u001d\u0018C1\u00017!\r\u0019D\u0011\t\u0003\u0007\t\u0007\n\"\u0019\u0001\u001c\u0003\u0007\u0005\u000bt\u0007E\u00024\t\u000f\"QAQ\tC\u0002YBa\u0001R\tA\u0002\u0011-\u0003cJ\u0011\u0005N\r}H1\u0001C\u0004\t\u0017!y\u0001b\u0005\u0005\u0018\u0011mAq\u0004C\u0012\tO!Y\u0003b\f\u00054\u0011]B1\bC \t\u000bJ1\u0001b\u0014#\u0005)1UO\\2uS>t\u0017gN\u000b)\t'\"i\u0006\"\u0019\u0005f\u0011%DQ\u000eC9\tk\"I\b\" \u0005\u0002\u0012\u0015E\u0011\u0012CG\t##)\n\"'\u0005\u001e\u0012\u0005Fq\u0015\u000b\u0005\t+\"I\u000bE\u00150\t/\"Y\u0006b\u0018\u0005d\u0011\u001dD1\u000eC8\tg\"9\bb\u001f\u0005\u0000\u0011\rEq\u0011CF\t\u001f#\u0019\nb&\u0005\u001c\u0012}EQU\u0005\u0004\t3B\"!\u0003.ja^KG\u000f[\u00199!\r\u0019DQ\f\u0003\u0006kI\u0011\rA\u000e\t\u0004g\u0011\u0005D!B \u0013\u0005\u00041\u0004cA\u001a\u0005f\u0011)1K\u0005b\u0001mA\u00191\u0007\"\u001b\u0005\u000b\u001d\u0014\"\u0019\u0001\u001c\u0011\u0007M\"i\u0007B\u0003~%\t\u0007a\u0007E\u00024\tc\"a!a\u000b\u0013\u0005\u00041\u0004cA\u001a\u0005v\u00111\u0011q\f\nC\u0002Y\u00022a\rC=\t\u0019\t9J\u0005b\u0001mA\u00191\u0007\" \u0005\r\u0005M'C1\u00017!\r\u0019D\u0011\u0011\u0003\u0007\u0005'\u0011\"\u0019\u0001\u001c\u0011\u0007M\")\t\u0002\u0004\u0003XI\u0011\rA\u000e\t\u0004g\u0011%EA\u0002BP%\t\u0007a\u0007E\u00024\t\u001b#aAa;\u0013\u0005\u00041\u0004cA\u001a\u0005\u0012\u0012111\b\nC\u0002Y\u00022a\rCK\t\u0019\u0019yI\u0005b\u0001mA\u00191\u0007\"'\u0005\r\r\u001d(C1\u00017!\r\u0019DQ\u0014\u0003\u0007\t\u0007\u0012\"\u0019\u0001\u001c\u0011\u0007M\"\t\u000b\u0002\u0004\u0005$J\u0011\rA\u000e\u0002\u0004\u0003FB\u0004cA\u001a\u0005(\u0012)!I\u0005b\u0001m!1AI\u0005a\u0001\tW\u0003\u0012&\tCW\t7\"y\u0006b\u0019\u0005h\u0011-Dq\u000eC:\to\"Y\bb \u0005\u0004\u0012\u001dE1\u0012CH\t'#9\nb'\u0005 \u0012\u0015\u0016b\u0001CXE\tQa)\u001e8di&|g.\r\u001d\u0016U\u0011MFQ\u0018Ca\t\u000b$I\r\"4\u0005R\u0012UG\u0011\u001cCo\tC$)\u000f\";\u0005n\u0012EHQ\u001fC}\t{,\t!\"\u0002\u0006\fQ!AQWC\u0007!-zCq\u0017C^\t\u007f#\u0019\rb2\u0005L\u0012=G1\u001bCl\t7$y\u000eb9\u0005h\u0012-Hq\u001eCz\to$Y\u0010b@\u0006\u0004\u0015%\u0011b\u0001C]1\tI!,\u001b9XSRD\u0017'\u000f\t\u0004g\u0011uF!B\u001b\u0014\u0005\u00041\u0004cA\u001a\u0005B\u0012)qh\u0005b\u0001mA\u00191\u0007\"2\u0005\u000bM\u001b\"\u0019\u0001\u001c\u0011\u0007M\"I\rB\u0003h'\t\u0007a\u0007E\u00024\t\u001b$Q!`\nC\u0002Y\u00022a\rCi\t\u0019\tYc\u0005b\u0001mA\u00191\u0007\"6\u0005\r\u0005}3C1\u00017!\r\u0019D\u0011\u001c\u0003\u0007\u0003/\u001b\"\u0019\u0001\u001c\u0011\u0007M\"i\u000e\u0002\u0004\u0002TN\u0011\rA\u000e\t\u0004g\u0011\u0005HA\u0002B\n'\t\u0007a\u0007E\u00024\tK$aAa\u0016\u0014\u0005\u00041\u0004cA\u001a\u0005j\u00121!qT\nC\u0002Y\u00022a\rCw\t\u0019\u0011Yo\u0005b\u0001mA\u00191\u0007\"=\u0005\r\rm2C1\u00017!\r\u0019DQ\u001f\u0003\u0007\u0007\u001f\u001b\"\u0019\u0001\u001c\u0011\u0007M\"I\u0010\u0002\u0004\u0004hN\u0011\rA\u000e\t\u0004g\u0011uHA\u0002C\"'\t\u0007a\u0007E\u00024\u000b\u0003!a\u0001b)\u0014\u0005\u00041\u0004cA\u001a\u0006\u0006\u00111QqA\nC\u0002Y\u00121!Q\u0019:!\r\u0019T1\u0002\u0003\u0006\u0005N\u0011\rA\u000e\u0005\u0007\tN\u0001\r!b\u0004\u0011W\u0005*\t\u0002b/\u0005@\u0012\rGq\u0019Cf\t\u001f$\u0019\u000eb6\u0005\\\u0012}G1\u001dCt\tW$y\u000fb=\u0005x\u0012mHq`C\u0002\u000b\u0013I1!b\u0005#\u0005)1UO\\2uS>t\u0017'O\u000b-\u000b/)\t#\"\n\u0006*\u00155R\u0011GC\u001b\u000bs)i$\"\u0011\u0006F\u0015%SQJC)\u000b+*I&\"\u0018\u0006b\u0015\u0015T\u0011NC7\u000bg\"B!\"\u0007\u0006vAis&b\u0007\u0006 \u0015\rRqEC\u0016\u000b_)\u0019$b\u000e\u0006<\u0015}R1IC$\u000b\u0017*y%b\u0015\u0006X\u0015mSqLC2\u000bO*Y'\"\u001d\n\u0007\u0015u\u0001DA\u0005[SB<\u0016\u000e\u001e53aA\u00191'\"\t\u0005\u000bU\"\"\u0019\u0001\u001c\u0011\u0007M*)\u0003B\u0003@)\t\u0007a\u0007E\u00024\u000bS!Qa\u0015\u000bC\u0002Y\u00022aMC\u0017\t\u00159GC1\u00017!\r\u0019T\u0011\u0007\u0003\u0006{R\u0011\rA\u000e\t\u0004g\u0015UBABA\u0016)\t\u0007a\u0007E\u00024\u000bs!a!a\u0018\u0015\u0005\u00041\u0004cA\u001a\u0006>\u00111\u0011q\u0013\u000bC\u0002Y\u00022aMC!\t\u0019\t\u0019\u000e\u0006b\u0001mA\u00191'\"\u0012\u0005\r\tMAC1\u00017!\r\u0019T\u0011\n\u0003\u0007\u0005/\"\"\u0019\u0001\u001c\u0011\u0007M*i\u0005\u0002\u0004\u0003 R\u0011\rA\u000e\t\u0004g\u0015ECA\u0002Bv)\t\u0007a\u0007E\u00024\u000b+\"aaa\u000f\u0015\u0005\u00041\u0004cA\u001a\u0006Z\u001111q\u0012\u000bC\u0002Y\u00022aMC/\t\u0019\u00199\u000f\u0006b\u0001mA\u00191'\"\u0019\u0005\r\u0011\rCC1\u00017!\r\u0019TQ\r\u0003\u0007\tG#\"\u0019\u0001\u001c\u0011\u0007M*I\u0007\u0002\u0004\u0006\bQ\u0011\rA\u000e\t\u0004g\u00155DABC8)\t\u0007aGA\u0002BeA\u00022aMC:\t\u0015\u0011EC1\u00017\u0011\u0019!E\u00031\u0001\u0006xAi\u0013%\"\u001f\u0006 \u0015\rRqEC\u0016\u000b_)\u0019$b\u000e\u0006<\u0015}R1IC$\u000b\u0017*y%b\u0015\u0006X\u0015mSqLC2\u000bO*Y'\"\u001d\n\u0007\u0015m$E\u0001\u0006Gk:\u001cG/[8oeA*b&b \u0006\n\u00165U\u0011SCK\u000b3+i*\")\u0006&\u0016%VQVCY\u000bk+I,\"0\u0006B\u0016\u0015W\u0011ZCg\u000b#,).\"7\u0006`R!Q\u0011QCq!=zS1QCD\u000b\u0017+y)b%\u0006\u0018\u0016mUqTCR\u000bO+Y+b,\u00064\u0016]V1XC`\u000b\u0007,9-b3\u0006P\u0016MWq[Co\u0013\r))\t\u0007\u0002\n5&\u0004x+\u001b;ieE\u00022aMCE\t\u0015)TC1\u00017!\r\u0019TQ\u0012\u0003\u0006\u007fU\u0011\rA\u000e\t\u0004g\u0015EE!B*\u0016\u0005\u00041\u0004cA\u001a\u0006\u0016\u0012)q-\u0006b\u0001mA\u00191'\"'\u0005\u000bu,\"\u0019\u0001\u001c\u0011\u0007M*i\n\u0002\u0004\u0002,U\u0011\rA\u000e\t\u0004g\u0015\u0005FABA0+\t\u0007a\u0007E\u00024\u000bK#a!a&\u0016\u0005\u00041\u0004cA\u001a\u0006*\u00121\u00111[\u000bC\u0002Y\u00022aMCW\t\u0019\u0011\u0019\"\u0006b\u0001mA\u00191'\"-\u0005\r\t]SC1\u00017!\r\u0019TQ\u0017\u0003\u0007\u0005?+\"\u0019\u0001\u001c\u0011\u0007M*I\f\u0002\u0004\u0003lV\u0011\rA\u000e\t\u0004g\u0015uFABB\u001e+\t\u0007a\u0007E\u00024\u000b\u0003$aaa$\u0016\u0005\u00041\u0004cA\u001a\u0006F\u001211q]\u000bC\u0002Y\u00022aMCe\t\u0019!\u0019%\u0006b\u0001mA\u00191'\"4\u0005\r\u0011\rVC1\u00017!\r\u0019T\u0011\u001b\u0003\u0007\u000b\u000f)\"\u0019\u0001\u001c\u0011\u0007M*)\u000e\u0002\u0004\u0006pU\u0011\rA\u000e\t\u0004g\u0015eGABCn+\t\u0007aGA\u0002BeE\u00022aMCp\t\u0015\u0011UC1\u00017\u0011\u0019!U\u00031\u0001\u0006dBy\u0013%\":\u0006\b\u0016-UqRCJ\u000b/+Y*b(\u0006$\u0016\u001dV1VCX\u000bg+9,b/\u0006@\u0016\rWqYCf\u000b\u001f,\u0019.b6\u0006^&\u0019Qq\u001d\u0012\u0003\u0015\u0019+hn\u0019;j_:\u0014\u0014'\u0006\u0019\u0006l\u0016UX\u0011`C\u007f\r\u00031)A\"\u0003\u0007\u000e\u0019EaQ\u0003D\r\r;1\tC\"\n\u0007*\u00195b\u0011\u0007D\u001b\rs1iD\"\u0011\u0007F\u0019%cq\n\u000b\u0005\u000b[4\t\u0006E\u00190\u000b_,\u00190b>\u0006|\u0016}h1\u0001D\u0004\r\u00171yAb\u0005\u0007\u0018\u0019maq\u0004D\u0012\rO1YCb\f\u00074\u0019]b1\bD \r\u000729E\"\u0014\n\u0007\u0015E\bDA\u0005[SB<\u0016\u000e\u001e53eA\u00191'\">\u0005\u000bU2\"\u0019\u0001\u001c\u0011\u0007M*I\u0010B\u0003@-\t\u0007a\u0007E\u00024\u000b{$Qa\u0015\fC\u0002Y\u00022a\rD\u0001\t\u00159gC1\u00017!\r\u0019dQ\u0001\u0003\u0006{Z\u0011\rA\u000e\t\u0004g\u0019%AABA\u0016-\t\u0007a\u0007E\u00024\r\u001b!a!a\u0018\u0017\u0005\u00041\u0004cA\u001a\u0007\u0012\u00111\u0011q\u0013\fC\u0002Y\u00022a\rD\u000b\t\u0019\t\u0019N\u0006b\u0001mA\u00191G\"\u0007\u0005\r\tMaC1\u00017!\r\u0019dQ\u0004\u0003\u0007\u0005/2\"\u0019\u0001\u001c\u0011\u0007M2\t\u0003\u0002\u0004\u0003 Z\u0011\rA\u000e\t\u0004g\u0019\u0015BA\u0002Bv-\t\u0007a\u0007E\u00024\rS!aaa\u000f\u0017\u0005\u00041\u0004cA\u001a\u0007.\u001111q\u0012\fC\u0002Y\u00022a\rD\u0019\t\u0019\u00199O\u0006b\u0001mA\u00191G\"\u000e\u0005\r\u0011\rcC1\u00017!\r\u0019d\u0011\b\u0003\u0007\tG3\"\u0019\u0001\u001c\u0011\u0007M2i\u0004\u0002\u0004\u0006\bY\u0011\rA\u000e\t\u0004g\u0019\u0005CABC8-\t\u0007a\u0007E\u00024\r\u000b\"a!b7\u0017\u0005\u00041\u0004cA\u001a\u0007J\u00111a1\n\fC\u0002Y\u00121!\u0011\u001a3!\r\u0019dq\n\u0003\u0006\u0005Z\u0011\rA\u000e\u0005\u0007\tZ\u0001\rAb\u0015\u0011c\u00052)&b=\u0006x\u0016mXq D\u0002\r\u000f1YAb\u0004\u0007\u0014\u0019]a1\u0004D\u0010\rG19Cb\u000b\u00070\u0019Mbq\u0007D\u001e\r\u007f1\u0019Eb\u0012\u0007N%\u0019aq\u000b\u0012\u0003\u0015\u0019+hn\u0019;j_:\u0014$\u0007")
public interface ZipWithApply {
    public static /* synthetic */ ZipWith2 apply$(ZipWithApply $this, Function2 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, O> ZipWith2<A1, A2, O> apply(Function2<A1, A2, O> zipper) {
        return new ZipWith2<A1, A2, O>(zipper);
    }

    public static /* synthetic */ ZipWith3 apply$(ZipWithApply $this, Function3 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, O> ZipWith3<A1, A2, A3, O> apply(Function3<A1, A2, A3, O> zipper) {
        return new ZipWith3<A1, A2, A3, O>(zipper);
    }

    public static /* synthetic */ ZipWith4 apply$(ZipWithApply $this, Function4 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, O> ZipWith4<A1, A2, A3, A4, O> apply(Function4<A1, A2, A3, A4, O> zipper) {
        return new ZipWith4<A1, A2, A3, A4, O>(zipper);
    }

    public static /* synthetic */ ZipWith5 apply$(ZipWithApply $this, Function5 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, O> ZipWith5<A1, A2, A3, A4, A5, O> apply(Function5<A1, A2, A3, A4, A5, O> zipper) {
        return new ZipWith5<A1, A2, A3, A4, A5, O>(zipper);
    }

    public static /* synthetic */ ZipWith6 apply$(ZipWithApply $this, Function6 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, O> ZipWith6<A1, A2, A3, A4, A5, A6, O> apply(Function6<A1, A2, A3, A4, A5, A6, O> zipper) {
        return new ZipWith6<A1, A2, A3, A4, A5, A6, O>(zipper);
    }

    public static /* synthetic */ ZipWith7 apply$(ZipWithApply $this, Function7 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, O> ZipWith7<A1, A2, A3, A4, A5, A6, A7, O> apply(Function7<A1, A2, A3, A4, A5, A6, A7, O> zipper) {
        return new ZipWith7<A1, A2, A3, A4, A5, A6, A7, O>(zipper);
    }

    public static /* synthetic */ ZipWith8 apply$(ZipWithApply $this, Function8 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, O> ZipWith8<A1, A2, A3, A4, A5, A6, A7, A8, O> apply(Function8<A1, A2, A3, A4, A5, A6, A7, A8, O> zipper) {
        return new ZipWith8<A1, A2, A3, A4, A5, A6, A7, A8, O>(zipper);
    }

    public static /* synthetic */ ZipWith9 apply$(ZipWithApply $this, Function9 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, O> ZipWith9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> apply(Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> zipper) {
        return new ZipWith9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O>(zipper);
    }

    public static /* synthetic */ ZipWith10 apply$(ZipWithApply $this, Function10 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> ZipWith10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> apply(Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> zipper) {
        return new ZipWith10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O>(zipper);
    }

    public static /* synthetic */ ZipWith11 apply$(ZipWithApply $this, Function11 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> ZipWith11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> apply(Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> zipper) {
        return new ZipWith11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O>(zipper);
    }

    public static /* synthetic */ ZipWith12 apply$(ZipWithApply $this, Function12 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> ZipWith12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> apply(Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> zipper) {
        return new ZipWith12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O>(zipper);
    }

    public static /* synthetic */ ZipWith13 apply$(ZipWithApply $this, Function13 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> ZipWith13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> apply(Function13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O> zipper) {
        return new ZipWith13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, O>(zipper);
    }

    public static /* synthetic */ ZipWith14 apply$(ZipWithApply $this, Function14 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> ZipWith14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> apply(Function14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O> zipper) {
        return new ZipWith14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, O>(zipper);
    }

    public static /* synthetic */ ZipWith15 apply$(ZipWithApply $this, Function15 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> ZipWith15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> apply(Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> zipper) {
        return new ZipWith15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O>(zipper);
    }

    public static /* synthetic */ ZipWith16 apply$(ZipWithApply $this, Function16 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> ZipWith16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> apply(Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> zipper) {
        return new ZipWith16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O>(zipper);
    }

    public static /* synthetic */ ZipWith17 apply$(ZipWithApply $this, Function17 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> ZipWith17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> apply(Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> zipper) {
        return new ZipWith17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O>(zipper);
    }

    public static /* synthetic */ ZipWith18 apply$(ZipWithApply $this, Function18 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> ZipWith18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> apply(Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> zipper) {
        return new ZipWith18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O>(zipper);
    }

    public static /* synthetic */ ZipWith19 apply$(ZipWithApply $this, Function19 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> ZipWith19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> apply(Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> zipper) {
        return new ZipWith19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O>(zipper);
    }

    public static /* synthetic */ ZipWith20 apply$(ZipWithApply $this, Function20 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> ZipWith20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> apply(Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> zipper) {
        return new ZipWith20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O>(zipper);
    }

    public static /* synthetic */ ZipWith21 apply$(ZipWithApply $this, Function21 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> ZipWith21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> apply(Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> zipper) {
        return new ZipWith21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O>(zipper);
    }

    public static /* synthetic */ ZipWith22 apply$(ZipWithApply $this, Function22 zipper) {
        return $this.apply(zipper);
    }

    default public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> ZipWith22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> apply(Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> zipper) {
        return new ZipWith22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O>(zipper);
    }

    public static void $init$(ZipWithApply $this) {
    }
}

