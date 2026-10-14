/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.LazyList;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Stream;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.java8.JFunction0$mcV$sp;
import scala.sys.process.BasicIO;
import scala.sys.process.BasicIO$;
import scala.sys.process.BasicIO$LazilyListed$;
import scala.sys.process.BasicIO$Streamed$;
import scala.sys.process.BasicIO$Uncloseable$;
import scala.sys.process.Process;
import scala.sys.process.Process$;
import scala.sys.process.ProcessBuilder;
import scala.sys.process.ProcessBuilder$;
import scala.sys.process.ProcessIO;
import scala.sys.process.ProcessImpl;
import scala.sys.process.ProcessLogger;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0011McADA\b\u0003#\u0001\n1!\u0001\u0002\u0012\u0005uAq\n\u0005\b\u0003O\u0001A\u0011AA\u0016\r!\t\u0019\u0004\u0001\u0001\u0002\u0012\u0005U\u0002B\u0003BY\u0005\t\u0005\t\u0015!\u0003\u0002@!9\u0011q\u000b\u0002\u0005\u0002\tM\u0006bBAB\u0005\u0011\u0015!\u0011\u0018\u0004\t\u0005{\u0003\u0001!!\u0005\u0003@\"Q!\u0011\u0019\u0004\u0003\u0006\u0004%\t%!,\t\u0015\t\rgA!A!\u0002\u0013\ty\u000b\u0003\u0006\u0003F\u001a\u0011\t\u0011*A\u0005\u0005\u000fDq!a\u0016\u0007\t\u0003\u0011i\rC\u0004\u0002\u0004\u001a!\tE!6\t\u000f\t-f\u0001\"\u0011\u0003.\u001aA!\u0011\u001c\u0001\u0001\u0003#\u0011Y\u000e\u0003\u0006\u0004\u00165\u0011\t\u0011)A\u0005\u0007/Aq!a\u0016\u000e\t\u0003\u0019iB\u0002\u0005\u0004$\u0001\u0001\u0011\u0011CB\u0013\u0011)\u00199\u0003\u0005B\u0001B\u0003%1\u0011\u0006\u0005\b\u0003/\u0002B\u0011AB\u0018\r!\u0019)\u0004\u0001\u0001\u0002\u0012\r]\u0002BCB\u0014'\t\u0005\t\u0015!\u0003\u0004*!Q1QJ\n\u0003\u0002\u0003\u0006I!a%\t\u000f\u0005]3\u0003\"\u0001\u0004P\u0019A11\b\u0001\u0001\u0003#\u0019i\u0004\u0003\u0006\u0003|^\u0011\t\u0011*A\u0005\u0007\u007fA1b!\u0004\u0018\u0005\u0003\u0005\u000b\u0011BAXE!9\u0011qK\f\u0005\u0002\r\u001d\u0003b\u0002BX/\u0011\u0005#Q\u0016\u0004\t\u0005?\u0004\u0001!!\u0005\u0003b\"Q!1 \u000f\u0003\u0002\u0013\u0006IA!@\t\u0017\r5AD!A!\u0002\u0013\tyK\t\u0005\b\u0003/bB\u0011AB\b\u0011\u001d\u0011y\u000b\bC!\u0005[3\u0011B!:\u0001\u0003\u0003\t\tBa:\t\u0015\t\u0005\u0017E!b\u0001\n\u0003\ni\u000b\u0003\u0006\u0003D\u0006\u0012\t\u0011)A\u0005\u0003_C!B!;\"\u0005\u0003\u0005\u000b\u0011\u0002Bv\u0011\u001d\t9&\tC\u0001\u0005cDq!a!\"\t\u0003\u00129P\u0002\u0005\u0004X\u0001\u0001\u0011\u0011CB-\u0011)\u0019Yf\nB\u0001B\u0003%1Q\f\u0005\b\u0003/:C\u0011AB2\u0011\u001d\t\u0019i\nC!\u0007SBqA!1(\t\u0003\u001ai\u0007C\u0004\u0003,\u001e\"\tE!,\u0007\u0013\u0005m\u0002!!\u0001\u0002\u001a\u0005u\u0002bBA,[\u0011\u0005\u0011\u0011\f\u0005\b\u00037jC\u0011CA/\u0011\u001d\ty&\fC\t\u0003;B\u0001\"!\u0019.A\u0003%\u00111\r\u0005\b\u0003SjC\u0011AA6\u0011\u001d\t\t(\fC\u0001\u0003gBq!a\u001e.\t\u0003\tI\bC\u0004\u0002~5\"\t!a \t\u000f\u0005\rU\u0006\"\u0001\u0002\u0006\"9\u00111Q\u0017\u0005\u0002\u00055\u0005bBAB[\u0011\u0005\u0011\u0011\u0014\u0005\b\u0003\u0007kC\u0011AAS\u0011\u001d\tY+\fC\u0001\u0003[Cq!a+.\t\u0003\t)\rC\u0004\u0002J6\"\t!!,\t\u000f\u0005%W\u0006\"\u0001\u0002L\"9\u0011qZ\u0017\u0005\u0002\u0005E\u0007bBAh[\u0011\u0005\u0011\u0011\u001d\u0005\b\u0003KlC\u0011AAi\u0011\u001d\t)/\fC\u0001\u0003ODq!a4.\t\u0003\tY\u000fC\u0004\u0002P6\"\tA!\u0001\t\u000f\u0005\u0015X\u0006\"\u0001\u0003\b!9\u0011Q]\u0017\u0005\u0002\t-\u0001b\u0002B\t[\u0011\u0005!1\u0003\u0005\b\u0005#iC\u0011\u0001B\u0018\u0011\u001d\u0011)$\fC\u0001\u0005'AqA!\u000e.\t\u0003\u0011I\u0004C\u0004\u0003\u00125\"\tAa\u0010\t\u000f\tEQ\u0006\"\u0001\u0003F!9!QG\u0017\u0005\u0002\t5\u0003b\u0002B\u001b[\u0011\u0005!1\u000b\u0005\b\u00057jC\u0011\u0001B/\u0011\u001d\u0011Y&\fC\u0001\u0005?BqAa\u0017.\t\u0003\u0011Y\u0007C\u0004\u0003p5\"\tA!\u0018\t\u000f\t=T\u0006\"\u0001\u0003r!9!QO\u0017\u0005\u0002\t]\u0004\u0002\u0003B=[\u0001&IAa\u001f\t\u0011\u0005=W\u0006)C\u0005\u0005\u0013C\u0001B!\u0005.A\u0013%!q\u0013\u0005\t\u0005Gk\u0003\u0015\"\u0003\u0003&\"9!1V\u0017\u0005\u0002\t5\u0006b\u0002BX[\u0011\u0005!Q\u0016\u0004\t\u0007g\u0002\u0001!!\u0005\u0004v!Q1Q\u0003.\u0003\u0002\u0003\u0006Iaa\u0006\t\u000f\u0005]#\f\"\u0001\u0004~!9\u00111\f.\u0005\u0012\r\re\u0001CBC\u0001\u0001\t\tba\"\t\u0015\r=eL!A!\u0002\u0013\u0019I\u0003C\u0004\u0002Xy#\ta!%\t\u000f\u0005mc\f\"\u0005\u0004\u0018\"9\u0011q\f0\u0005\u0012\re\u0005bBBN=\u0012\u00051Q\u0014\u0005\b\u00077sF\u0011ABR\u0011\u001d\u0019YJ\u0018C\u0001\u0007SCqaa'_\t\u0003\u0019yKB\u0005\u00046\u0002\t\t!!\u0005\u00048\"9\u0011qK4\u0005\u0002\re\u0006\u0002CB_O\u0002&\tba0\t\u000f\u0005\ru\r\"\u0002\u0004F\"A1\u0011Z4!\u000e#\u0019YMB\u0005\u0004`\u0002\t\t!!\u0005\u0004b\"Q11\u00197\u0003\u0002\u0003\u0006I!a\u0010\t\u0015\rMFN!A!\u0002\u0013\ty\u0004\u0003\u0006\u0004d2\u0014\t\u0011)A\u0005\u0003_Cq!a\u0016m\t\u0003\u0019)\u000fC\u0004\u0003B2$\te!\u001c\u0007\u0011\r=\b\u0001AA\t\u0007cD!ba=s\u0005\u0003\u0005\u000b\u0011BA \u0011)\u0019)P\u001dB\u0001B\u0003%\u0011q\b\u0005\u000b\u0007o\u0014(\u0011!Q\u0001\n\u0005M\u0005bBA,e\u0012\u00051\u0011 \u0005\b\u0007\u0013\u0014H\u0011\tC\u0002\r!!i\u0001\u0001\u0001\u0002\u0012\u0011=\u0001BCBzq\n\u0005\t\u0015!\u0003\u0002@!Q1Q\u001f=\u0003\u0002\u0003\u0006I!a\u0010\t\u000f\u0005]\u0003\u0010\"\u0001\u0005\u0012!91\u0011\u001a=\u0005B\u0011ea\u0001\u0003C\u0012\u0001\u0001\t\t\u0002\"\n\t\u0015\rMXP!A!\u0002\u0013\ty\u0004\u0003\u0006\u0004vv\u0014\t\u0011)A\u0005\u0003\u007fAq!a\u0016~\t\u0003!9\u0003C\u0004\u0004Jv$\t\u0005b\f\u0007\u0011\u0011e\u0002\u0001AA\t\twA1ba=\u0002\u0006\t\u0005\t\u0015!\u0003\u0002@!Y1Q_A\u0003\u0005\u0003\u0005\u000b\u0011BA \u0011!\t9&!\u0002\u0005\u0002\u0011u\u0002\u0002CBe\u0003\u000b!\t\u0005\"\u0012\u0003%A\u0013xnY3tg\n+\u0018\u000e\u001c3fe&k\u0007\u000f\u001c\u0006\u0005\u0003'\t)\"A\u0004qe>\u001cWm]:\u000b\t\u0005]\u0011\u0011D\u0001\u0004gf\u001c(BAA\u000e\u0003\u0015\u00198-\u00197b'\r\u0001\u0011q\u0004\t\u0005\u0003C\t\u0019#\u0004\u0002\u0002\u001a%!\u0011QEA\r\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\u001a\u0001\u0001\u0006\u0002\u0002.A!\u0011\u0011EA\u0018\u0013\u0011\t\t$!\u0007\u0003\tUs\u0017\u000e\u001e\u0002\u000e\t\u0006,Wn\u001c8Ck&dG-\u001a:\u0014\u0007\t\t9\u0004E\u0002\u0002:5j\u0011\u0001\u0001\u0002\u0010\u0003\n\u001cHO]1di\n+\u0018\u000e\u001c3feNIQ&a\b\u0002@\u0005\u001d\u0013\u0011\u000b\t\u0005\u0003\u0003\n\u0019%\u0004\u0002\u0002\u0012%!\u0011QIA\t\u00059\u0001&o\\2fgN\u0014U/\u001b7eKJ\u0004B!!\u000f\u0002J%!\u00111JA'\u0005\u0011\u0019\u0016N\\6\u000b\t\u0005=\u0013\u0011C\u0001\u000f!J|7-Z:t\u0005VLG\u000eZ3s!\u0011\tI$a\u0015\n\t\u0005U\u0013Q\n\u0002\u0007'>,(oY3\u0002\rqJg.\u001b;?)\t\t9$\u0001\u0005u_N{WO]2f+\t\t9$\u0001\u0004u_NKgn[\u0001\u0016I\u00164\u0017-\u001e7u'R\u0014X-Y7DCB\f7-\u001b;z!\u0011\t\t#!\u001a\n\t\u0005\u001d\u0014\u0011\u0004\u0002\u0004\u0013:$\u0018!\u0003\u0013iCNDGEY1s)\u0011\ty$!\u001c\t\u000f\u0005=$\u00071\u0001\u0002@\u0005)q\u000e\u001e5fe\u0006iA\u0005[1tQ\u0012\u0012\u0017M\u001d\u0013cCJ$B!a\u0010\u0002v!9\u0011qN\u001aA\u0002\u0005}\u0012!\u0004\u0013iCNDG%Y7qI\u0005l\u0007\u000f\u0006\u0003\u0002@\u0005m\u0004bBA8i\u0001\u0007\u0011qH\u0001\u0010I!\f7\u000f\u001b\u0013iCNDG\u0005[1tQR!\u0011qHAA\u0011\u001d\ty'\u000ea\u0001\u0003\u007f\t1A];o)\t\t9\t\u0005\u0003\u0002B\u0005%\u0015\u0002BAF\u0003#\u0011q\u0001\u0015:pG\u0016\u001c8\u000f\u0006\u0003\u0002\b\u0006=\u0005bBAIo\u0001\u0007\u00111S\u0001\rG>tg.Z2u\u0013:\u0004X\u000f\u001e\t\u0005\u0003C\t)*\u0003\u0003\u0002\u0018\u0006e!a\u0002\"p_2,\u0017M\u001c\u000b\u0005\u0003\u000f\u000bY\nC\u0004\u0002\u001eb\u0002\r!a(\u0002\u00071|w\r\u0005\u0003\u0002B\u0005\u0005\u0016\u0002BAR\u0003#\u0011Q\u0002\u0015:pG\u0016\u001c8\u000fT8hO\u0016\u0014HCBAD\u0003O\u000bI\u000bC\u0004\u0002\u001ef\u0002\r!a(\t\u000f\u0005E\u0015\b1\u0001\u0002\u0014\u0006QAEY1oO\u0012\u0012\u0017M\\4\u0016\u0005\u0005=\u0006\u0003BAY\u0003\u007fsA!a-\u0002<B!\u0011QWA\r\u001b\t\t9L\u0003\u0003\u0002:\u0006%\u0012A\u0002\u001fs_>$h(\u0003\u0003\u0002>\u0006e\u0011A\u0002)sK\u0012,g-\u0003\u0003\u0002B\u0006\r'AB*ue&twM\u0003\u0003\u0002>\u0006eA\u0003BAX\u0003\u000fDq!!(<\u0001\u0004\ty*A\b%E\u0006tw\r\n2b]\u001e$C.Z:t)\u0011\ty+!4\t\u000f\u0005uU\b1\u0001\u0002 \u0006IA.\u0019>z\u0019&tWm]\u000b\u0003\u0003'\u0004b!!6\u0002\\\u0006=f\u0002BA\u0011\u0003/LA!!7\u0002\u001a\u00059\u0001/Y2lC\u001e,\u0017\u0002BAo\u0003?\u0014\u0001\u0002T1{s2K7\u000f\u001e\u0006\u0005\u00033\fI\u0002\u0006\u0003\u0002T\u0006\r\bbBAO\u007f\u0001\u0007\u0011qT\u0001\u0010Y\u0006T\u0018\u0010T5oKN|FEY1oOR!\u00111[Au\u0011\u001d\ti*\u0011a\u0001\u0003?#B!a5\u0002n\"9\u0011q\u001e\"A\u0002\u0005E\u0018\u0001C2ba\u0006\u001c\u0017\u000e^=\u0011\t\u0005M\u0018Q`\u0007\u0003\u0003kTA!a>\u0002z\u0006!A.\u00198h\u0015\t\tY0\u0001\u0003kCZ\f\u0017\u0002BA\u0000\u0003k\u0014q!\u00138uK\u001e,'\u000f\u0006\u0004\u0002T\n\r!Q\u0001\u0005\b\u0003;\u001b\u0005\u0019AAP\u0011\u001d\tyo\u0011a\u0001\u0003c$B!a5\u0003\n!9\u0011q\u001e#A\u0002\u0005EHCBAj\u0005\u001b\u0011y\u0001C\u0004\u0002\u001e\u0016\u0003\r!a(\t\u000f\u0005=X\t1\u0001\u0002r\u0006QA.\u001b8f'R\u0014X-Y7\u0016\u0005\tU\u0001CBAk\u0005/\ty+\u0003\u0003\u0003\u001a\u0005}'AB*ue\u0016\fW\u000eK\u0006G\u0005;\u0011\u0019C!\n\u0003*\t-\u0002\u0003BA\u0011\u0005?IAA!\t\u0002\u001a\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\u0012!qE\u0001\tS:$XM\u001d8bY\u0006)1/\u001b8dK\u0006\u0012!QF\u0001\u0007e9\n4G\f\u001b\u0015\t\tU!\u0011\u0007\u0005\b\u0003;;\u0005\u0019AAPQ-9%Q\u0004B\u0012\u0005K\u0011ICa\u000b\u0002!1Lg.Z*ue\u0016\fWn\u0018\u0013cC:<\u0007f\u0003%\u0003\u001e\t\r\"Q\u0005B\u0015\u0005W!BA!\u0006\u0003<!9\u0011QT%A\u0002\u0005}\u0005fC%\u0003\u001e\t\r\"Q\u0005B\u0015\u0005W!BA!\u0006\u0003B!9\u0011q\u001e&A\u0002\u0005E\bf\u0003&\u0003\u001e\t\r\"Q\u0005B\u0015\u0005W!bA!\u0006\u0003H\t%\u0003bBAO\u0017\u0002\u0007\u0011q\u0014\u0005\b\u0003_\\\u0005\u0019AAyQ-Y%Q\u0004B\u0012\u0005K\u0011ICa\u000b\u0015\t\tU!q\n\u0005\b\u0003_d\u0005\u0019AAyQ-a%Q\u0004B\u0012\u0005K\u0011ICa\u000b\u0015\r\tU!Q\u000bB,\u0011\u001d\ti*\u0014a\u0001\u0003?Cq!a<N\u0001\u0004\t\t\u0010K\u0006N\u0005;\u0011\u0019C!\n\u0003*\t-\u0012!\u0002\u0013cC:<WCAA2)\u0011\t\u0019G!\u0019\t\u000f\t\rt\n1\u0001\u0003f\u0005\u0011\u0011n\u001c\t\u0005\u0003\u0003\u00129'\u0003\u0003\u0003j\u0005E!!\u0003)s_\u000e,7o]%P)\u0011\t\u0019G!\u001c\t\u000f\u0005u\u0005\u000b1\u0001\u0002 \u0006QAEY1oO\u0012bWm]:\u0015\t\u0005\r$1\u000f\u0005\b\u0003;\u0013\u0006\u0019AAP\u0003)!\u0017-Z7p]&TX\r\u001a\u000b\u0003\u0003\u007f\tQa\u001d7veB$b!a,\u0003~\t\u0015\u0005bBAO)\u0002\u0007!q\u0010\t\u0007\u0003C\u0011\t)a(\n\t\t\r\u0015\u0011\u0004\u0002\u0007\u001fB$\u0018n\u001c8\t\u000f\t\u001dE\u000b1\u0001\u0002\u0014\u00061q/\u001b;i\u0013:$\"\"a5\u0003\f\n=%1\u0013BK\u0011\u001d\u0011i)\u0016a\u0001\u0003'\u000b\u0011b^5uQ&s\u0007/\u001e;\t\u000f\tEU\u000b1\u0001\u0002\u0014\u0006\u0001bn\u001c8[KJ|W\t_2faRLwN\u001c\u0005\b\u0003;+\u0006\u0019\u0001B@\u0011\u001d\ty/\u0016a\u0001\u0003c$\"B!\u0006\u0003\u001a\nm%Q\u0014BP\u0011\u001d\u0011iI\u0016a\u0001\u0003'CqA!%W\u0001\u0004\t\u0019\nC\u0004\u0002\u001eZ\u0003\rAa \t\u000f\u0005=h\u000b1\u0001\u0002r\"ZaK!\b\u0003$\t\u0015\"\u0011\u0006B\u0016\u0003-\u0011XO\u001c\"vM\u001a,'/\u001a3\u0015\r\u0005\r$q\u0015BU\u0011\u001d\tij\u0016a\u0001\u0003?Cq!!%X\u0001\u0004\t\u0019*A\u0005dC:\u0004\u0016\u000e]3U_V\u0011\u00111S\u0001\rQ\u0006\u001cX\t_5u-\u0006dW/Z\u0001\u000bk:$WM\u001d7zS:<G\u0003\u0002B[\u0005o\u00032!!\u000f\u0003\u0011\u001d\u0011\t\f\u0002a\u0001\u0003\u007f!B!a\"\u0003<\"9!1M\u0003A\u0002\t\u0015$!\u0002#v[6L8c\u0001\u0004\u00028\u0005AAo\\*ue&tw-A\u0005u_N#(/\u001b8hA\u0005IQ\r_5u-\u0006dW/\u001a\t\u0007\u0003C\u0011I-a\u0019\n\t\t-\u0017\u0011\u0004\u0002\ty\tLh.Y7f}Q1!q\u001aBi\u0005'\u00042!!\u000f\u0007\u0011\u001d\u0011\tM\u0003a\u0001\u0003_C\u0001B!2\u000b\t\u0003\u0007!q\u0019\u000b\u0005\u0003\u000f\u00139\u000eC\u0004\u0003d-\u0001\rA!\u001a\u0003\u0011U\u0013F*\u00138qkR\u001c2!\u0004Bo!\r\tI\u0004\b\u0002\u000f\u0013N#(/Z1n\u0005VLG\u000eZ3s'\ra\"1\u001d\t\u0004\u0003s\t#!\u0004+ie\u0016\fGMQ;jY\u0012,'oE\u0002\"\u0003o\tqA];o\u00136\u0004H\u000e\u0005\u0005\u0002\"\t5(QMA\u0017\u0013\u0011\u0011y/!\u0007\u0003\u0013\u0019+hn\u0019;j_:\fDC\u0002Br\u0005g\u0014)\u0010C\u0004\u0003B\u0016\u0002\r!a,\t\u000f\t%X\u00051\u0001\u0003lR!\u0011q\u0011B}\u0011\u001d\u0011\u0019G\na\u0001\u0005K\naa\u001d;sK\u0006l\u0007CBA\u0011\u0005\u0013\u0014y\u0010\u0005\u0003\u0004\u0002\r\u001da\u0002BA!\u0007\u0007IAa!\u0002\u0002\u0012\u0005y\u0001O]8dKN\u001c\u0018J\u001c;fe:\fG.\u0003\u0003\u0004\n\r-!aC%oaV$8\u000b\u001e:fC6TAa!\u0002\u0002\u0012\u0005)A.\u00192fYR1!Q\\B\t\u0007'A\u0001Ba? \t\u0003\u0007!Q \u0005\b\u0007\u001by\u0002\u0019AAX\u0003\r)(\u000f\u001c\t\u0005\u0007\u0003\u0019I\"\u0003\u0003\u0004\u001c\r-!aA+S\u0019R!1qDB\u0011!\r\tI$\u0004\u0005\b\u0007+y\u0001\u0019AB\f\u0005%1\u0015\u000e\\3J]B,HoE\u0002\u0011\u0005;\fAAZ5mKB!1\u0011AB\u0016\u0013\u0011\u0019ica\u0003\u0003\t\u0019KG.\u001a\u000b\u0005\u0007c\u0019\u0019\u0004E\u0002\u0002:AAqaa\n\u0013\u0001\u0004\u0019IC\u0001\u0006GS2,w*\u001e;qkR\u001c2aEB\u001d!\r\tId\u0006\u0002\u000f\u001fN#(/Z1n\u0005VLG\u000eZ3s'\r9\"1\u001d\t\u0007\u0003C\u0011Im!\u0011\u0011\t\r\u000511I\u0005\u0005\u0007\u000b\u001aYA\u0001\u0007PkR\u0004X\u000f^*ue\u0016\fW\u000e\u0006\u0004\u0004:\r%31\n\u0005\t\u0005wTB\u00111\u0001\u0004@!91Q\u0002\u000eA\u0002\u0005=\u0016AB1qa\u0016tG\r\u0006\u0004\u0004R\rM3Q\u000b\t\u0004\u0003s\u0019\u0002bBB\u0014-\u0001\u00071\u0011\u0006\u0005\b\u0007\u001b2\u0002\u0019AAJ\u0005\u0019\u0019\u0016.\u001c9mKN\u0019q%a\u000e\u0002\u0003A\u0004Ba!\u0001\u0004`%!1\u0011MB\u0006\u0005=Q\u0005K]8dKN\u001c()^5mI\u0016\u0014H\u0003BB3\u0007O\u00022!!\u000f(\u0011\u001d\u0019Y&\u000ba\u0001\u0007;\"B!a\"\u0004l!9!1\r\u0016A\u0002\t\u0015DCAB8!\u0011\t\u0019p!\u001d\n\t\u0005\u0005\u0017Q\u001f\u0002\b+Jc\u0015*\u001c9m'\u001dQ\u0016qDB<\u0003#\u0002B!!\u000f\u0004z%!11PA'\u0005))&\u000b\u0014\"vS2$WM\u001d\u000b\u0005\u0007\u007f\u001a\t\tE\u0002\u0002:iCqa!\u0006]\u0001\u0004\u00199\"\u0006\u0002\u0004 \tAa)\u001b7f\u00136\u0004HnE\u0005_\u0003?\u0019I)a\u0012\u0002RA!\u0011\u0011HBF\u0013\u0011\u0019i)!\u0014\u0003\u0017\u0019KG.\u001a\"vS2$WM]\u0001\u0005E\u0006\u001cX\r\u0006\u0003\u0004\u0014\u000eU\u0005cAA\u001d=\"91q\u00121A\u0002\r%RCAB\u0019+\t\u0019\t&A\b%Q\u0006\u001c\b\u000e\n7fgN$C.Z:t)\u0011\tyda(\t\u000f\r\u00056\r1\u0001\u0004*\u0005\ta\r\u0006\u0003\u0002@\r\u0015\u0006bBBTI\u0002\u00071qC\u0001\u0002kR!\u0011qHBV\u0011!\u0019i+\u001aCA\u0002\tu\u0018!A:\u0015\t\u0005}2\u0011\u0017\u0005\b\u0007g3\u0007\u0019AA \u0003\u0005\u0011'\u0001\u0004\"bg&\u001c')^5mI\u0016\u00148cA4\u00028Q\u001111\u0018\t\u0004\u0003s9\u0017\u0001D2iK\u000e\\gj\u001c;UQ&\u001cH\u0003BA\u0017\u0007\u0003Dqaa1j\u0001\u0004\ty$A\u0001b)\u0011\t9ia2\t\u000f\t\r$\u000e1\u0001\u0003f\u0005i1M]3bi\u0016\u0004&o\\2fgN$Ba!4\u0004^B!1qZBk\u001d\u0011\t\te!5\n\t\rM\u0017\u0011C\u0001\b!J|7-Z:t\u0013\u0011\u00199n!7\u0003\u0019\t\u000b7/[2Qe>\u001cWm]:\n\t\rm\u0017\u0011\u0003\u0002\f!J|7-Z:t\u00136\u0004H\u000eC\u0004\u0003d-\u0004\rA!\u001a\u0003#M+\u0017/^3oi&\fGNQ;jY\u0012,'oE\u0002m\u0007w\u000bab\u001c9fe\u0006$xN]*ue&tw\r\u0006\u0005\u0004h\u000e%81^Bw!\r\tI\u0004\u001c\u0005\b\u0007\u0007\u0004\b\u0019AA \u0011\u001d\u0019\u0019\f\u001da\u0001\u0003\u007fAqaa9q\u0001\u0004\tyK\u0001\u0007QSB,GMQ;jY\u0012,'oE\u0002s\u0007O\fQAZ5sgR\faa]3d_:$\u0017a\u0002;p\u000bJ\u0014xN\u001d\u000b\t\u0007w\u001cipa@\u0005\u0002A\u0019\u0011\u0011\b:\t\u000f\rMh\u000f1\u0001\u0002@!91Q\u001f<A\u0002\u0005}\u0002bBB|m\u0002\u0007\u00111\u0013\u000b\u0005\t\u000b!Y\u0001\u0005\u0003\u0004P\u0012\u001d\u0011\u0002\u0002C\u0005\u00073\u0014a\u0002U5qK\u0012\u0004&o\\2fgN,7\u000fC\u0004\u0003d]\u0004\rA!\u001a\u0003\u0015\u0005sGMQ;jY\u0012,'oE\u0002y\u0007O$b\u0001b\u0005\u0005\u0016\u0011]\u0001cAA\u001dq\"911_>A\u0002\u0005}\u0002bBB{w\u0002\u0007\u0011q\b\u000b\u0005\t7!\t\u0003\u0005\u0003\u0004P\u0012u\u0011\u0002\u0002C\u0010\u00073\u0014!\"\u00118e!J|7-Z:t\u0011\u001d\u0011\u0019\u0007 a\u0001\u0005K\u0012\u0011b\u0014:Ck&dG-\u001a:\u0014\u0007u\u001c9\u000f\u0006\u0004\u0005*\u0011-BQ\u0006\t\u0004\u0003si\b\u0002CBz\u0003\u0003\u0001\r!a\u0010\t\u0011\rU\u0018\u0011\u0001a\u0001\u0003\u007f!B\u0001\"\r\u00058A!1q\u001aC\u001a\u0013\u0011!)d!7\u0003\u0013=\u0013\bK]8dKN\u001c\b\u0002\u0003B2\u0003\u0007\u0001\rA!\u001a\u0003\u001fM+\u0017/^3oG\u0016\u0014U/\u001b7eKJ\u001cB!!\u0002\u0004hR1Aq\bC!\t\u0007\u0002B!!\u000f\u0002\u0006!A11_A\u0006\u0001\u0004\ty\u0004\u0003\u0005\u0004v\u0006-\u0001\u0019AA )\u0011!9\u0005\"\u0014\u0011\t\r=G\u0011J\u0005\u0005\t\u0017\u001aINA\bQe>\u001cWm]:TKF,XM\\2f\u0011!\u0011\u0019'!\u0004A\u0002\t\u0015d\u0002BA!\t#JA!a\u0014\u0002\u0012\u0001")
public interface ProcessBuilderImpl {
    public static void $init$(ProcessBuilderImpl $this) {
    }

    public static abstract class AbstractBuilder
    implements ProcessBuilder {
        private final int defaultStreamCapacity;
        public final /* synthetic */ ProcessBuilder$ $outer;

        @Override
        public ProcessBuilder $hash$less(File f) {
            return ProcessBuilder.Sink.$hash$less$((ProcessBuilder.Sink)this, f);
        }

        @Override
        public ProcessBuilder $hash$less(URL f) {
            return ProcessBuilder.Sink.$hash$less$((ProcessBuilder.Sink)this, f);
        }

        @Override
        public ProcessBuilder $hash$less(Function0<InputStream> in) {
            return ProcessBuilder.Sink.$hash$less$((ProcessBuilder.Sink)this, in);
        }

        @Override
        public ProcessBuilder $hash$less(ProcessBuilder b) {
            return ProcessBuilder.Sink.$hash$less$((ProcessBuilder.Sink)this, b);
        }

        @Override
        public ProcessBuilder $hash$greater(File f) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, f);
        }

        @Override
        public ProcessBuilder $hash$greater$greater(File f) {
            return ProcessBuilder.Source.$hash$greater$greater$(this, f);
        }

        @Override
        public ProcessBuilder $hash$greater(Function0<OutputStream> out) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, out);
        }

        @Override
        public ProcessBuilder $hash$greater(ProcessBuilder b) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, b);
        }

        @Override
        public ProcessBuilder cat() {
            return ProcessBuilder.Source.cat$(this);
        }

        @Override
        public AbstractBuilder toSource() {
            return this;
        }

        @Override
        public AbstractBuilder toSink() {
            return this;
        }

        @Override
        public ProcessBuilder $hash$bar(ProcessBuilder other) {
            if (!other.canPipeTo()) {
                throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"Piping to multiple processes is not supported.").toString());
            }
            return new PipedBuilder(this.scala$sys$process$ProcessBuilderImpl$AbstractBuilder$$$outer(), (ProcessBuilder)this, other, false);
        }

        @Override
        public ProcessBuilder $hash$bar$bar(ProcessBuilder other) {
            return new OrBuilder(this.scala$sys$process$ProcessBuilderImpl$AbstractBuilder$$$outer(), this, other);
        }

        @Override
        public ProcessBuilder $hash$amp$amp(ProcessBuilder other) {
            return new AndBuilder(this.scala$sys$process$ProcessBuilderImpl$AbstractBuilder$$$outer(), this, other);
        }

        @Override
        public ProcessBuilder $hash$hash$hash(ProcessBuilder other) {
            return new SequenceBuilder(this.scala$sys$process$ProcessBuilderImpl$AbstractBuilder$$$outer(), this, other);
        }

        @Override
        public Process run() {
            return this.run(false);
        }

        @Override
        public Process run(boolean connectInput) {
            return this.run(BasicIO$.MODULE$.standard(connectInput));
        }

        @Override
        public Process run(ProcessLogger log) {
            return this.run(log, false);
        }

        @Override
        public Process run(ProcessLogger log, boolean connectInput) {
            return this.run(BasicIO$.MODULE$.apply(connectInput, log));
        }

        @Override
        public String $bang$bang() {
            return this.slurp(None$.MODULE$, false);
        }

        @Override
        public String $bang$bang(ProcessLogger log) {
            return this.slurp(new Some<ProcessLogger>(log), false);
        }

        @Override
        public String $bang$bang$less() {
            return this.slurp(None$.MODULE$, true);
        }

        @Override
        public String $bang$bang$less(ProcessLogger log) {
            return this.slurp(new Some<ProcessLogger>(log), true);
        }

        @Override
        public LazyList<String> lazyLines() {
            return this.lazyLines(false, true, None$.MODULE$, this.defaultStreamCapacity);
        }

        @Override
        public LazyList<String> lazyLines(ProcessLogger log) {
            return this.lazyLines(false, true, new Some<ProcessLogger>(log), this.defaultStreamCapacity);
        }

        @Override
        public LazyList<String> lazyLines_$bang() {
            return this.lazyLines(false, false, None$.MODULE$, this.defaultStreamCapacity);
        }

        @Override
        public LazyList<String> lazyLines_$bang(ProcessLogger log) {
            return this.lazyLines(false, false, new Some<ProcessLogger>(log), this.defaultStreamCapacity);
        }

        @Override
        public LazyList<String> lazyLines(Integer capacity) {
            return this.lazyLines(false, true, None$.MODULE$, capacity);
        }

        @Override
        public LazyList<String> lazyLines(ProcessLogger log, Integer capacity) {
            return this.lazyLines(false, true, new Some<ProcessLogger>(log), capacity);
        }

        @Override
        public LazyList<String> lazyLines_$bang(Integer capacity) {
            return this.lazyLines(false, false, None$.MODULE$, capacity);
        }

        @Override
        public LazyList<String> lazyLines_$bang(ProcessLogger log, Integer capacity) {
            return this.lazyLines(false, false, new Some<ProcessLogger>(log), capacity);
        }

        @Override
        public Stream<String> lineStream() {
            return this.lineStream(false, true, None$.MODULE$, this.defaultStreamCapacity);
        }

        @Override
        public Stream<String> lineStream(ProcessLogger log) {
            return this.lineStream(false, true, new Some<ProcessLogger>(log), this.defaultStreamCapacity);
        }

        @Override
        public Stream<String> lineStream_$bang() {
            return this.lineStream(false, false, None$.MODULE$, this.defaultStreamCapacity);
        }

        @Override
        public Stream<String> lineStream_$bang(ProcessLogger log) {
            return this.lineStream(false, false, new Some<ProcessLogger>(log), this.defaultStreamCapacity);
        }

        @Override
        public Stream<String> lineStream(Integer capacity) {
            return this.lineStream(false, true, None$.MODULE$, capacity);
        }

        @Override
        public Stream<String> lineStream(ProcessLogger log, Integer capacity) {
            return this.lineStream(false, true, new Some<ProcessLogger>(log), capacity);
        }

        @Override
        public Stream<String> lineStream_$bang(Integer capacity) {
            return this.lineStream(false, false, None$.MODULE$, capacity);
        }

        @Override
        public Stream<String> lineStream_$bang(ProcessLogger log, Integer capacity) {
            return this.lineStream(false, false, new Some<ProcessLogger>(log), capacity);
        }

        @Override
        public int $bang() {
            return this.run(false).exitValue();
        }

        public int $bang(ProcessIO io) {
            return this.run(io).exitValue();
        }

        @Override
        public int $bang(ProcessLogger log) {
            return this.runBuffered(log, false);
        }

        @Override
        public int $bang$less() {
            return this.run(true).exitValue();
        }

        @Override
        public int $bang$less(ProcessLogger log) {
            return this.runBuffered(log, true);
        }

        public ProcessBuilder daemonized() {
            return new DaemonBuilder(this.scala$sys$process$ProcessBuilderImpl$AbstractBuilder$$$outer(), this);
        }

        private String slurp(Option<ProcessLogger> log, boolean withIn) {
            StringBuffer buffer = new StringBuffer();
            int code = this.$bang(BasicIO$.MODULE$.apply(withIn, buffer, log));
            if (code == 0) {
                return buffer.toString();
            }
            String error_message = new StringBuilder(20).append("Nonzero exit value: ").append(code).toString();
            throw new RuntimeException(error_message);
        }

        /*
         * WARNING - void declaration
         */
        private LazyList<String> lazyLines(boolean withInput, boolean nonZeroException, Option<ProcessLogger> log, Integer capacity) {
            void apply_daemon;
            void apply_prefix;
            void apply_f;
            BasicIO.LazilyListed lazilyListed = BasicIO$LazilyListed$.MODULE$.apply(nonZeroException, capacity);
            Process process = this.run(BasicIO$.MODULE$.apply(withInput, lazilyListed.process(), log));
            Function1<Object, BoxedUnit> done = lazilyListed.done();
            if (Process$.MODULE$.Spawn() == null) {
                throw null;
            }
            JFunction0$mcV$sp jFunction0$mcV$sp = () -> done.apply$mcVI$sp(AbstractBuilder.liftedTree1$1(process));
            boolean bl = false;
            String string = "LazyLines";
            if (Process$.MODULE$.Spawn() == null) {
                throw null;
            }
            Thread apply_thread = new Thread(null, (Function0)apply_f){
                private final Function0 f$1;

                public void run() {
                    this.f$1.apply$mcV$sp();
                }
                {
                    this.f$1 = f$1;
                }
            };
            apply_thread.setName(new StringBuilder(7).append((String)apply_prefix).append("-spawn-").append(apply_thread.getName()).toString());
            apply_thread.setDaemon((boolean)apply_daemon);
            apply_thread.start();
            string = null;
            jFunction0$mcV$sp = null;
            Object var11_11 = null;
            return lazilyListed.lazyList();
        }

        /*
         * WARNING - void declaration
         */
        private Stream<String> lineStream(boolean withInput, boolean nonZeroException, Option<ProcessLogger> log, Integer capacity) {
            void apply_daemon;
            void apply_prefix;
            void apply_f;
            BasicIO.Streamed streamed = BasicIO$Streamed$.MODULE$.apply(nonZeroException, capacity);
            Process process = this.run(BasicIO$.MODULE$.apply(withInput, streamed.process(), log));
            if (Process$.MODULE$.Spawn() == null) {
                throw null;
            }
            JFunction0$mcV$sp jFunction0$mcV$sp = () -> streamed.done().apply$mcVI$sp(process.exitValue());
            boolean bl = false;
            String string = "LineStream";
            if (Process$.MODULE$.Spawn() == null) {
                throw null;
            }
            Thread apply_thread = new /* invalid duplicate definition of identical inner class */;
            apply_thread.setName(new StringBuilder(7).append((String)apply_prefix).append("-spawn-").append(apply_thread.getName()).toString());
            apply_thread.setDaemon((boolean)apply_daemon);
            apply_thread.start();
            string = null;
            jFunction0$mcV$sp = null;
            Object var10_10 = null;
            return streamed.stream().apply();
        }

        private int runBuffered(ProcessLogger log, boolean connectInput) {
            return BoxesRunTime.unboxToInt(log.buffer(() -> this.run(log, connectInput).exitValue()));
        }

        @Override
        public boolean canPipeTo() {
            return false;
        }

        @Override
        public boolean hasExitValue() {
            return true;
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$AbstractBuilder$$$outer() {
            return this.$outer;
        }

        public static final /* synthetic */ String $anonfun$$hash$bar$1() {
            return "Piping to multiple processes is not supported.";
        }

        private static final /* synthetic */ int liftedTree1$1(Process process$2) {
            try {
                return process$2.exitValue();
            }
            catch (Throwable throwable) {
                if (throwable != null && !NonFatal$.MODULE$.unapply(throwable).isEmpty()) {
                    return -2;
                }
                throw throwable;
            }
        }

        public AbstractBuilder(ProcessBuilder$ $outer) {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            this.defaultStreamCapacity = 4096;
        }
    }

    public class AndBuilder
    extends SequentialBuilder {
        private final ProcessBuilder first;
        private final ProcessBuilder second;

        @Override
        public ProcessImpl.AndProcess createProcess(ProcessIO io) {
            return new ProcessImpl.AndProcess(Process$.MODULE$, this.first, this.second, io);
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$AndBuilder$$$outer() {
            return this.$outer;
        }

        public AndBuilder(ProcessBuilder$ $outer, ProcessBuilder first, ProcessBuilder second) {
            this.first = first;
            this.second = second;
            super($outer, first, second, "#&&");
        }
    }

    public abstract class BasicBuilder
    extends AbstractBuilder {
        public void checkNotThis(ProcessBuilder a) {
            ProcessBuilder processBuilder = a;
            if (!(processBuilder == null || !processBuilder.equals(this))) {
                throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)BasicBuilder.$anonfun$checkNotThis$1(a)).toString());
            }
        }

        @Override
        public final Process run(ProcessIO io) {
            ProcessImpl.BasicProcess p = this.createProcess(io);
            p.start();
            return p;
        }

        public abstract ProcessImpl.BasicProcess createProcess(ProcessIO var1);

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$BasicBuilder$$$outer() {
            return this.$outer;
        }

        public static final /* synthetic */ String $anonfun$checkNotThis$1(ProcessBuilder a$1) {
            return new StringBuilder(42).append("Compound process '").append(a$1).append("' cannot contain itself.").toString();
        }

        public BasicBuilder(ProcessBuilder$ $outer) {
            super($outer);
        }
    }

    public class DaemonBuilder
    extends AbstractBuilder {
        private final ProcessBuilder underlying;

        @Override
        public final Process run(ProcessIO io) {
            return this.underlying.run(io.daemonized());
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$DaemonBuilder$$$outer() {
            return this.$outer;
        }

        public DaemonBuilder(ProcessBuilder$ $outer, ProcessBuilder underlying) {
            this.underlying = underlying;
            super($outer);
        }
    }

    public class Dummy
    extends AbstractBuilder {
        private final String toString;
        private final Function0<Object> exitValue;

        public String toString() {
            return this.toString;
        }

        @Override
        public Process run(ProcessIO io) {
            return new ProcessImpl.DummyProcess(Process$.MODULE$, this.exitValue);
        }

        @Override
        public boolean canPipeTo() {
            return true;
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$Dummy$$$outer() {
            return this.$outer;
        }

        public Dummy(ProcessBuilder$ $outer, String toString2, Function0<Object> exitValue) {
            this.toString = toString2;
            this.exitValue = exitValue;
            super($outer);
        }
    }

    public class FileImpl
    implements ProcessBuilder.FileBuilder {
        private final File base;
        public final /* synthetic */ ProcessBuilder$ $outer;

        @Override
        public ProcessBuilder $hash$greater(File f) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, f);
        }

        @Override
        public ProcessBuilder $hash$greater$greater(File f) {
            return ProcessBuilder.Source.$hash$greater$greater$(this, f);
        }

        @Override
        public ProcessBuilder $hash$greater(Function0<OutputStream> out) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, out);
        }

        @Override
        public ProcessBuilder $hash$greater(ProcessBuilder b) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, b);
        }

        @Override
        public ProcessBuilder cat() {
            return ProcessBuilder.Source.cat$(this);
        }

        @Override
        public ProcessBuilder $hash$less(File f) {
            return ProcessBuilder.Sink.$hash$less$((ProcessBuilder.Sink)this, f);
        }

        @Override
        public ProcessBuilder $hash$less(URL f) {
            return ProcessBuilder.Sink.$hash$less$((ProcessBuilder.Sink)this, f);
        }

        @Override
        public ProcessBuilder $hash$less(Function0<InputStream> in) {
            return ProcessBuilder.Sink.$hash$less$((ProcessBuilder.Sink)this, in);
        }

        @Override
        public ProcessBuilder $hash$less(ProcessBuilder b) {
            return ProcessBuilder.Sink.$hash$less$((ProcessBuilder.Sink)this, b);
        }

        @Override
        public FileInput toSource() {
            return new FileInput(this.scala$sys$process$ProcessBuilderImpl$FileImpl$$$outer(), this.base);
        }

        @Override
        public FileOutput toSink() {
            return new FileOutput(this.scala$sys$process$ProcessBuilderImpl$FileImpl$$$outer(), this.base, false);
        }

        @Override
        public ProcessBuilder $hash$less$less(File f) {
            return this.$hash$less$less(new FileInput(this.scala$sys$process$ProcessBuilderImpl$FileImpl$$$outer(), f));
        }

        @Override
        public ProcessBuilder $hash$less$less(URL u) {
            return this.$hash$less$less(new URLInput(this.scala$sys$process$ProcessBuilderImpl$FileImpl$$$outer(), u));
        }

        @Override
        public ProcessBuilder $hash$less$less(Function0<InputStream> s2) {
            return this.$hash$less$less(new IStreamBuilder(this.scala$sys$process$ProcessBuilderImpl$FileImpl$$$outer(), s2, "<input stream>"));
        }

        @Override
        public ProcessBuilder $hash$less$less(ProcessBuilder b) {
            return new PipedBuilder(this.scala$sys$process$ProcessBuilderImpl$FileImpl$$$outer(), b, (ProcessBuilder)new FileOutput(this.scala$sys$process$ProcessBuilderImpl$FileImpl$$$outer(), this.base, true), false);
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$FileImpl$$$outer() {
            return this.$outer;
        }

        public FileImpl(ProcessBuilder$ $outer, File base) {
            this.base = base;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class FileInput
    extends IStreamBuilder {
        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$FileInput$$$outer() {
            return this.$outer;
        }

        public FileInput(ProcessBuilder$ $outer, File file) {
            super($outer, (Function0<InputStream>)((Object)new Serializable(null, file){
                private static final long serialVersionUID = 0L;
                private final File file$1;

                public final FileInputStream apply() {
                    return new FileInputStream(this.file$1);
                }
                {
                    this.file$1 = file$1;
                }
            }), file.getAbsolutePath());
        }
    }

    public class FileOutput
    extends OStreamBuilder {
        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$FileOutput$$$outer() {
            return this.$outer;
        }

        public FileOutput(ProcessBuilder$ $outer, File file, boolean append) {
            super($outer, (Function0<OutputStream>)((Object)new Serializable(null, file, append){
                private static final long serialVersionUID = 0L;
                private final File file$2;
                private final boolean append$1;

                public final FileOutputStream apply() {
                    return new FileOutputStream(this.file$2, this.append$1);
                }
                {
                    this.file$2 = file$2;
                    this.append$1 = append$1;
                }
            }), file.getAbsolutePath());
        }
    }

    public class IStreamBuilder
    extends ThreadBuilder {
        @Override
        public boolean hasExitValue() {
            return false;
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$IStreamBuilder$$$outer() {
            return this.$outer;
        }

        public IStreamBuilder(ProcessBuilder$ $outer, Function0<InputStream> stream, String label) {
            super($outer, label, (Function1<ProcessIO, BoxedUnit>)((Object)new Serializable(null, stream){
                private static final long serialVersionUID = 0L;
                private final Function0 stream$2;

                public final void apply(ProcessIO x$2) {
                    x$2.processOutput().apply(BasicIO$Uncloseable$.MODULE$.protect((InputStream)this.stream$2.apply()));
                }
                {
                    this.stream$2 = stream$2;
                }
            }));
        }
    }

    public class OStreamBuilder
    extends ThreadBuilder {
        @Override
        public boolean hasExitValue() {
            return false;
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$OStreamBuilder$$$outer() {
            return this.$outer;
        }

        public OStreamBuilder(ProcessBuilder$ $outer, Function0<OutputStream> stream, String label) {
            super($outer, label, (Function1<ProcessIO, BoxedUnit>)((Object)new Serializable(null, stream){
                private static final long serialVersionUID = 0L;
                private final Function0 stream$1;

                public final void apply(ProcessIO x$1) {
                    x$1.writeInput().apply(BasicIO$Uncloseable$.MODULE$.protect((OutputStream)this.stream$1.apply()));
                }
                {
                    this.stream$1 = stream$1;
                }
            }));
        }
    }

    public class OrBuilder
    extends SequentialBuilder {
        private final ProcessBuilder first;
        private final ProcessBuilder second;

        @Override
        public ProcessImpl.OrProcess createProcess(ProcessIO io) {
            return new ProcessImpl.OrProcess(Process$.MODULE$, this.first, this.second, io);
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$OrBuilder$$$outer() {
            return this.$outer;
        }

        public OrBuilder(ProcessBuilder$ $outer, ProcessBuilder first, ProcessBuilder second) {
            this.first = first;
            this.second = second;
            super($outer, first, second, "#||");
        }
    }

    public class PipedBuilder
    extends SequentialBuilder {
        private final ProcessBuilder first;
        private final ProcessBuilder second;
        private final boolean toError;

        @Override
        public ProcessImpl.PipedProcesses createProcess(ProcessIO io) {
            return new ProcessImpl.PipedProcesses(Process$.MODULE$, this.first, this.second, io, this.toError);
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$PipedBuilder$$$outer() {
            return this.$outer;
        }

        public PipedBuilder(ProcessBuilder$ $outer, ProcessBuilder first, ProcessBuilder second, boolean toError) {
            this.first = first;
            this.second = second;
            this.toError = toError;
            super($outer, first, second, toError ? "#|!" : "#|");
        }
    }

    public class SequenceBuilder
    extends SequentialBuilder {
        private final ProcessBuilder first;
        private final ProcessBuilder second;

        @Override
        public ProcessImpl.ProcessSequence createProcess(ProcessIO io) {
            return new ProcessImpl.ProcessSequence(Process$.MODULE$, this.first, this.second, io);
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$SequenceBuilder$$$outer() {
            return this.$outer;
        }

        public SequenceBuilder(ProcessBuilder$ $outer, ProcessBuilder first, ProcessBuilder second) {
            this.first = first;
            this.second = second;
            super($outer, first, second, "###");
        }
    }

    public abstract class SequentialBuilder
    extends BasicBuilder {
        private final ProcessBuilder a;
        private final ProcessBuilder b;
        private final String operatorString;

        public String toString() {
            return new StringBuilder(8).append(" ( ").append(this.a).append(" ").append(this.operatorString).append(" ").append(this.b).append(" ) ").toString();
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$SequentialBuilder$$$outer() {
            return this.$outer;
        }

        public SequentialBuilder(ProcessBuilder$ $outer, ProcessBuilder a, ProcessBuilder b, String operatorString) {
            this.a = a;
            this.b = b;
            this.operatorString = operatorString;
            super($outer);
            this.checkNotThis(a);
            this.checkNotThis(b);
        }
    }

    public class Simple
    extends AbstractBuilder {
        private final java.lang.ProcessBuilder p;

        /*
         * WARNING - void declaration
         */
        @Override
        public Process run(ProcessIO io) {
            List list;
            void apply_daemon;
            void apply_prefix;
            void apply_f;
            Thread thread2;
            boolean inherit;
            boolean bl = inherit = io.writeInput() == BasicIO$.MODULE$.connectToStdIn();
            if (inherit) {
                this.p.redirectInput(ProcessBuilder.Redirect.INHERIT);
            }
            java.lang.Process process = this.p.start();
            if (inherit || io.writeInput() == BasicIO$.MODULE$.connectNoOp()) {
                thread2 = null;
            } else {
                void apply_daemon2;
                void apply_prefix2;
                void apply_f2;
                JFunction0$mcV$sp jFunction0$mcV$sp = () -> io.writeInput().apply(process.getOutputStream());
                boolean bl2 = true;
                String string = "Simple-input";
                if (Process$.MODULE$.Spawn() == null) {
                    throw null;
                }
                Thread apply_thread = new /* invalid duplicate definition of identical inner class */;
                apply_thread.setName(new StringBuilder(7).append((String)apply_prefix2).append("-spawn-").append(apply_thread.getName()).toString());
                apply_thread.setDaemon((boolean)apply_daemon2);
                apply_thread.start();
                thread2 = apply_thread;
                string = null;
                jFunction0$mcV$sp = null;
                Object var10_7 = null;
            }
            Thread inThread = thread2;
            JFunction0$mcV$sp jFunction0$mcV$sp = () -> io.processOutput().apply(process.getInputStream());
            boolean bl3 = io.daemonizeThreads();
            String string = "Simple-output";
            if (Process$.MODULE$.Spawn() == null) {
                throw null;
            }
            Thread apply_thread = new /* invalid duplicate definition of identical inner class */;
            apply_thread.setName(new StringBuilder(7).append((String)apply_prefix).append("-spawn-").append(apply_thread.getName()).toString());
            apply_thread.setDaemon((boolean)apply_daemon);
            apply_thread.start();
            string = null;
            jFunction0$mcV$sp = null;
            Object var14_12 = null;
            Thread outThread = apply_thread;
            if (this.p.redirectErrorStream()) {
                list = Nil$.MODULE$;
            } else {
                void apply_daemon3;
                void apply_prefix3;
                void apply_f3;
                JFunction0$mcV$sp jFunction0$mcV$sp2 = () -> io.processError().apply(process.getErrorStream());
                boolean bl4 = io.daemonizeThreads();
                String string2 = "Simple-error";
                if (Process$.MODULE$.Spawn() == null) {
                    throw null;
                }
                Thread apply_thread2 = new /* invalid duplicate definition of identical inner class */;
                apply_thread2.setName(new StringBuilder(7).append((String)apply_prefix3).append("-spawn-").append(apply_thread2.getName()).toString());
                apply_thread2.setDaemon((boolean)apply_daemon3);
                apply_thread2.start();
                string2 = null;
                jFunction0$mcV$sp2 = null;
                Object var18_17 = null;
                $colon$colon<Nothing$> $colon$colon = new $colon$colon<Nothing$>((Nothing$)((Object)apply_thread2), Nil$.MODULE$);
                list = $colon$colon;
            }
            List errorThread = list;
            return new ProcessImpl.SimpleProcess(Process$.MODULE$, process, inThread, errorThread.$colon$colon(outThread));
        }

        public String toString() {
            return this.p.command().toString();
        }

        @Override
        public boolean canPipeTo() {
            return true;
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$Simple$$$outer() {
            return this.$outer;
        }

        public Simple(ProcessBuilder$ $outer, java.lang.ProcessBuilder p) {
            this.p = p;
            super($outer);
        }
    }

    public abstract class ThreadBuilder
    extends AbstractBuilder {
        private final String toString;
        private final Function1<ProcessIO, BoxedUnit> runImpl;

        public String toString() {
            return this.toString;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Process run(ProcessIO io) {
            void apply_daemon;
            void apply_prefix;
            void apply_f;
            LinkedBlockingQueue<Object> success = new LinkedBlockingQueue<Object>(1);
            JFunction0$mcV$sp jFunction0$mcV$sp = () -> this.go$1(io, success);
            boolean bl = io.daemonizeThreads();
            String string = "ThreadProcess";
            if (Process$.MODULE$.Spawn() == null) {
                throw null;
            }
            Thread apply_thread = new /* invalid duplicate definition of identical inner class */;
            apply_thread.setName(new StringBuilder(7).append((String)apply_prefix).append("-spawn-").append(apply_thread.getName()).toString());
            apply_thread.setDaemon((boolean)apply_daemon);
            apply_thread.start();
            string = null;
            jFunction0$mcV$sp = null;
            Object var7_6 = null;
            Thread t2 = apply_thread;
            return new ProcessImpl.ThreadProcess(Process$.MODULE$, t2, success);
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$ThreadBuilder$$$outer() {
            return this.$outer;
        }

        private final void go$1(ProcessIO io$1, LinkedBlockingQueue success$1) {
            boolean ok = false;
            try {
                this.runImpl.apply(io$1);
                ok = true;
            }
            finally {
                success$1.put(ok);
            }
        }

        public ThreadBuilder(ProcessBuilder$ $outer, String toString2, Function1<ProcessIO, BoxedUnit> runImpl) {
            this.toString = toString2;
            this.runImpl = runImpl;
            super($outer);
        }
    }

    public class URLImpl
    implements ProcessBuilder.URLBuilder {
        private final URL url;
        public final /* synthetic */ ProcessBuilder$ $outer;

        @Override
        public ProcessBuilder $hash$greater(File f) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, f);
        }

        @Override
        public ProcessBuilder $hash$greater$greater(File f) {
            return ProcessBuilder.Source.$hash$greater$greater$(this, f);
        }

        @Override
        public ProcessBuilder $hash$greater(Function0<OutputStream> out) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, out);
        }

        @Override
        public ProcessBuilder $hash$greater(ProcessBuilder b) {
            return ProcessBuilder.Source.$hash$greater$((ProcessBuilder.Source)this, b);
        }

        @Override
        public ProcessBuilder cat() {
            return ProcessBuilder.Source.cat$(this);
        }

        @Override
        public URLInput toSource() {
            return new URLInput(this.scala$sys$process$ProcessBuilderImpl$URLImpl$$$outer(), this.url);
        }

        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$URLImpl$$$outer() {
            return this.$outer;
        }

        public URLImpl(ProcessBuilder$ $outer, URL url2) {
            this.url = url2;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class URLInput
    extends IStreamBuilder {
        public /* synthetic */ ProcessBuilder$ scala$sys$process$ProcessBuilderImpl$URLInput$$$outer() {
            return this.$outer;
        }

        public URLInput(ProcessBuilder$ $outer, URL url2) {
            super($outer, (Function0<InputStream>)((Object)new Serializable(null, url2){
                private static final long serialVersionUID = 0L;
                private final URL url$1;

                public final InputStream apply() {
                    return this.url$1.openStream();
                }
                {
                    this.url$1 = url$1;
                }
            }), url2.toString());
        }
    }
}

