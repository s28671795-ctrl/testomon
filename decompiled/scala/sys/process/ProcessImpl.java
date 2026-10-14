/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.java8.JFunction0$mcV$sp;
import scala.sys.package$;
import scala.sys.process.BasicIO$;
import scala.sys.process.Process;
import scala.sys.process.Process$;
import scala.sys.process.ProcessBuilder;
import scala.sys.process.ProcessIO;
import scala.sys.process.ProcessImpl$Future$;
import scala.sys.process.ProcessImpl$Spawn$;

@ScalaSignature(bytes="\u0006\u0005\rMeAC:u!\u0003\r\t\u0001\u001e>\u0004\u000e\"1q\u0010\u0001C\u0001\u0003\u00079\u0001\"a\u0003\u0001\u0011\u0003!\u0018Q\u0002\u0004\t\u0003#\u0001\u0001\u0012\u0001;\u0002\u0014!9\u0011QC\u0002\u0005\u0002\u0005]\u0001bBA\r\u0007\u0011\u0005\u00111\u0004\u0005\n\u0003;\u001a\u0011\u0013!C\u0001\u0003?:\u0001\"!\u001e\u0001\u0011\u0003!\u0018q\u000f\u0004\t\u0003s\u0002\u0001\u0012\u0001;\u0002|!9\u0011Q\u0003\u0005\u0005\u0002\u0005u\u0004bBA\r\u0011\u0011\u0005\u0011q\u0010\u0004\b\u0003S\u0003\u0001\u0001^AV\u0011)\u0011Yb\u0003B\u0001B\u0003%!Q\u0004\u0005\u000b\u0005GY!\u0011!Q\u0001\n\tu\u0001B\u0003B\u0013\u0017\t\u0005\t\u0015!\u0003\u0003(!9\u0011QC\u0006\u0005\u0002\t}ba\u0002B%\u0001\u0001!(1\n\u0005\u000b\u00057\u0001\"\u0011!Q\u0001\n\tu\u0001B\u0003B\u0012!\t\u0005\t\u0015!\u0003\u0003\u001e!Q!Q\u0005\t\u0003\u0002\u0003\u0006IAa\n\t\u000f\u0005U\u0001\u0003\"\u0001\u0003N\u00199!q\u000b\u0001\u0001i\ne\u0003B\u0003B\u000e+\t\u0005\t\u0015!\u0003\u0003\u001e!Q!1E\u000b\u0003\u0002\u0003\u0006IA!\b\t\u0015\t\u0015RC!A!\u0002\u0013\u00119\u0003C\u0004\u0002\u0016U!\tAa\u0017\u0007\u000f\u0005=\u0006\u0001\u0001;\u00022\"Q!1\u0004\u000e\u0003\u0002\u0003\u0006IA!\b\t\u0015\t\r\"D!A!\u0002\u0013\u0011i\u0002\u0003\u0006\u0003&i\u0011\t\u0011)A\u0005\u0005OA!B!\f\u001b\u0005\u0003\u0005\u000b\u0011\u0002B\u0018\u0011\u001d\t)B\u0007C\u0001\u0005kA\u0001\"a@\u001bA\u0013E#\u0011\u0001\u0004\t\u0003w\u0003\u0011\u0011\u0001;\u0002>\"9\u0011QC\u0011\u0005\u0002\u0005\u001d\u0007bBAeC\u0019\u0005\u00111\u0001\u0004\t\u0003k\u0003\u0011\u0011\u0001;\u00028\"9\u0011Q\u0003\u0013\u0005\u0002\u0005-\u0007bBAgI\u0011\u0005\u0011q\u001a\u0005\b\u0003#$C\u0011AA\u0002\u0011\u001d\t\u0019\u000e\nC\u0001\u0003+Dq!!3%\t\u0003\t\u0019\u0001\u0003\u0007\u0002^\u0012\u0002\n\u0011cb!\n\u0013\ty\u000e\u0003\u0006\u0002r\u0012B)\u0019!C\t\u0003gD!\"!>%\u0011\u000b\u0007I\u0011CAz\u0011)\t9\u0010\nEC\u0002\u0013E\u0011\u0011 \u0005\u000b\u0003w$\u0003R1A\u0005\u0012\u0005u\b\u0002CA\u0000I\u00016\tB!\u0001\t\u0011\t\rA\u0005)C\t\u0005\u000b1qA!\u001a\u0001\u0001Q\u00149\u0007\u0003\u0006\u0003\u001cE\u0012\t\u0011)A\u0005\u0005;A!Ba\t2\u0005\u0003\u0005\u000b\u0011\u0002B\u000f\u0011)\u0011I'\rB\u0001B\u0003%!q\u0005\u0005\u000b\u0005W\n$\u0011!Q\u0001\n\u0005]\u0003bBA\u000bc\u0011\u0005!Q\u000e\u0005\b\u0005s\nD\u0011\u0003B>\u0011\u001d\u0019y#\rC\t\u0007cA\u0001\"a@2A\u0013E#\u0011\u0001\u0005\t\u0003\u007f\f\u0004\u0015\"\u0005\u00044\u0019A!Q\u0011\u0001\u0002\u0002Q\u00149\t\u0003\u0006\u0003\nn\u0012\t\u0011)A\u0005\u0003/B!Ba#<\u0005\u0003\u0005\u000b\u0011\u0002BG\u0011\u001d\t)b\u000fC\u0001\u0005\u001fCqA!&<\r\u0003\t\u0019\u0001\u0003\u0005\u0003\u0018n\"\t\u0001\u001eBM\u0011\u001d\u00119l\u000fC\u0005\u0005s3qAa \u0001\u0001Q\u0014\t\t\u0003\u0006\u0003F\n\u0013\t\u0011*A\u0005\u0005\u000fDq!!\u0006C\t\u0003\u0011I\rC\u0005\u0003N\n\u0013\r\u0015\"\u0005\u0003P\"A!1\u001c\"!\u0002\u0013\u0011\t\u000eC\u0005\u0003^\n\u0013\r\u0015\"\u0005\u0003`\"A!1\u001f\"!\u0002\u0013\u0011\t\u000fC\u0004\u0003\u0016\n#)%a\u0001\t\u000f\tU(\t\"\u0001\u0003x\"9!Q \"\u0005\u0002\t}\bbBB\u0016\u0005\u0012\u0005\u00111\u0001\u0005\b\u0007[\u0011E\u0011AA\u0002\r\u001d\u00199\u0001\u0001\u0001u\u0007\u0013A!B!2O\u0005\u0003%\u000b\u0011\u0002Bd\u0011\u001d\t)B\u0014C\u0001\u0007\u0017A\u0011B!4O\u0005\u0004&\tba\u0004\t\u0011\tmg\n)A\u0005\u0007#A\u0011ba\u0001O\u0005\u0004&\tba\u0006\t\u0011\rua\n)A\u0005\u00073AqA!&O\t\u0003\n\u0019\u0001C\u0004\u0003~:#\taa\b\t\u000f\tUh\n\"\u0001\u0004&!911\u0006(\u0005\u0002\u0005\r\u0001bBB\u0017\u001d\u0012\u0005\u00111\u0001\u0004\b\u0007s\u0001\u0001\u0001^B\u001e\u0011)\u00119B\u0017B\u0001J\u0003%1Q\b\u0005\b\u0003+QF\u0011AB \u00111\u0019)E\u0017I\u0001\u0002\u0007\u0005\u000b\u0011BB$\u0011!\u0019YE\u0017Q\u0001\n\u0005}\u0001\u0002CB'5\u0002\u0006Ia!\u0013\t\u000f\u00055'\f\"\u0011\u0002P\"9\u00111\u001b.\u0005B\u0005U\u0007bBAi5\u0012\u0005\u00131\u0001\u0004\b\u0007\u001f\u0002\u0001\u0001^B)\u0011)\u0019\u0019f\u0019B\u0001B\u0003%1Q\u000b\u0005\u000b\u00077\u001a'\u0011!Q\u0001\n\u0005}\u0001BCB/G\n\u0005\t\u0015!\u0003\u0004`!9\u0011QC2\u0005\u0002\rE\u0004bBAgG\u0012\u0005\u0013q\u001a\u0005\b\u0003'\u001cG\u0011IAk\u0011\u001d\t\tn\u0019C!\u0003\u0007A\u0001ba\u001fdA\u0013%\u00111\u0001\u0004\b\u0007{\u0002!\u0001^B@\u0011)\u0019Y\u0005\u001cB\u0001B\u0003%\u0011q\u0004\u0005\u000b\u0007\u0003c'\u0011!Q\u0001\n\r\r\u0005bBA\u000bY\u0012\u00051Q\u0011\u0005\b\u0003\u001bdG\u0011IAh\u0011\u001d\t\u0019\u000e\u001cC!\u0003+Dq!!5m\t\u0003\n\u0019AA\u0006Qe>\u001cWm]:J[Bd'BA;w\u0003\u001d\u0001(o\\2fgNT!a\u001e=\u0002\u0007ML8OC\u0001z\u0003\u0015\u00198-\u00197b'\t\u00011\u0010\u0005\u0002}{6\t\u00010\u0003\u0002\u007fq\n1\u0011I\\=SK\u001a\fa\u0001J5oSR$3\u0001\u0001\u000b\u0003\u0003\u000b\u00012\u0001`A\u0004\u0013\r\tI\u0001\u001f\u0002\u0005+:LG/A\u0003Ta\u0006<h\u000eE\u0002\u0002\u0010\ri\u0011\u0001\u0001\u0002\u0006'B\fwO\\\n\u0003\u0007m\fa\u0001P5oSRtDCAA\u0007\u0003\u0015\t\u0007\u000f\u001d7z)\u0019\ti\"!\u000f\u0002TQ!\u0011qDA\u0018!\u0011\t\t#a\u000b\u000e\u0005\u0005\r\"\u0002BA\u0013\u0003O\tA\u0001\\1oO*\u0011\u0011\u0011F\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002.\u0005\r\"A\u0002+ie\u0016\fG\r\u0003\u0005\u00022\u0015!\t\u0019AA\u001a\u0003\u00051\u0007#\u0002?\u00026\u0005\u0015\u0011bAA\u001cq\nAAHY=oC6,g\bC\u0004\u0002<\u0015\u0001\r!!\u0010\u0002\rA\u0014XMZ5y!\u0011\ty$!\u0014\u000f\t\u0005\u0005\u0013\u0011\n\t\u0004\u0003\u0007BXBAA#\u0015\u0011\t9%!\u0001\u0002\rq\u0012xn\u001c;?\u0013\r\tY\u0005_\u0001\u0007!J,G-\u001a4\n\t\u0005=\u0013\u0011\u000b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005-\u0003\u0010C\u0005\u0002V\u0015\u0001\n\u00111\u0001\u0002X\u00051A-Y3n_:\u00042\u0001`A-\u0013\r\tY\u0006\u001f\u0002\b\u0005>|G.Z1o\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u0012TCAA1U\u0011\t9&a\u0019,\u0005\u0005\u0015\u0004\u0003BA4\u0003cj!!!\u001b\u000b\t\u0005-\u0014QN\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\u001cy\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003g\nIGA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\faAR;ukJ,\u0007cAA\b\u0011\t1a)\u001e;ve\u0016\u001c\"\u0001C>\u0015\u0005\u0005]T\u0003BAA\u0003'#B!a!\u0002&B9A0!\"\u0002 \u0005%\u0015bAADq\n1A+\u001e9mKJ\u0002R\u0001`AF\u0003\u001fK1!!$y\u0005%1UO\\2uS>t\u0007\u0007\u0005\u0003\u0002\u0012\u0006ME\u0002\u0001\u0003\b\u0003+S!\u0019AAL\u0005\u0005!\u0016\u0003BAM\u0003?\u00032\u0001`AN\u0013\r\ti\n\u001f\u0002\b\u001d>$\b.\u001b8h!\ra\u0018\u0011U\u0005\u0004\u0003GC(aA!os\"A\u0011\u0011\u0007\u0006\u0005\u0002\u0004\t9\u000bE\u0003}\u0003k\tyI\u0001\u0006B]\u0012\u0004&o\\2fgN\u001c2aCAW!\r\tyA\u0007\u0002\u0012'\u0016\fX/\u001a8uS\u0006d\u0007K]8dKN\u001c8c\u0001\u000e\u00024B\u0019\u0011q\u0002\u0013\u0003\u001f\r{W\u000e]8v]\u0012\u0004&o\\2fgN\u001c2\u0001JA]!\r\ty!\t\u0002\r\u0005\u0006\u001c\u0018n\u0019)s_\u000e,7o]\n\u0005Cm\fy\f\u0005\u0003\u0002B\u0006\rW\"\u0001;\n\u0007\u0005\u0015GOA\u0004Qe>\u001cWm]:\u0015\u0005\u0005e\u0016!B:uCJ$HCAAZ\u0003\u001dI7/\u00117jm\u0016$\"!a\u0016\u0002\u000f\u0011,7\u000f\u001e:ps\u0006IQ\r_5u-\u0006dW/\u001a\u000b\u0003\u0003/\u00042\u0001`Am\u0013\r\tY\u000e\u001f\u0002\u0004\u0013:$\u0018a\u0001=%iU\u0011\u0011\u0011\u001d\t\fy\u0006\r\u0018qDA\u0010\u0003O\fy/C\u0002\u0002fb\u0014a\u0001V;qY\u0016$\u0004#\u0002?\u0002\f\u0006%\b#\u0002?\u0002l\u0006]\u0017bAAwq\n1q\n\u001d;j_:\u0004R\u0001`AF\u0003\u000b\tQ\u0002\u001d:pG\u0016\u001c8\u000f\u00165sK\u0006$WCAA\u0010\u000311W\u000f^;sKRC'/Z1e\u0003-1W\u000f^;sKZ\u000bG.^3\u0016\u0005\u0005\u001d\u0018!\u00033fgR\u0014x._3s+\t\ty/A\bsk:\fe\u000eZ#ySR4\u0016\r\\;f)\t\tI/\u0001\tsk:Le\u000e^3seV\u0004H/\u001b2mKV!!q\u0001B\b)\u0011\u0011IA!\u0006\u0015\t\t-!\u0011\u0003\t\u0006y\u0006-(Q\u0002\t\u0005\u0003#\u0013y\u0001B\u0004\u0002\u0016B\u0012\r!a&\t\u0011\tM\u0001\u0007\"a\u0001\u0003g\t1\u0002Z3tiJ|\u00170S7qY\"A!q\u0003\u0019\u0005\u0002\u0004\u0011I\"\u0001\u0004bGRLwN\u001c\t\u0006y\u0006U\"QB\u0001\u0002CB!\u0011\u0011\u0019B\u0010\u0013\r\u0011\t\u0003\u001e\u0002\u000f!J|7-Z:t\u0005VLG\u000eZ3s\u0003\u0005\u0011\u0017AA5p!\u0011\t\tM!\u000b\n\u0007\t-BOA\u0005Qe>\u001cWm]:J\u001f\u0006)RM^1mk\u0006$XmU3d_:$\u0007K]8dKN\u001c\bc\u0002?\u00032\u0005]\u0017qK\u0005\u0004\u0005gA(!\u0003$v]\u000e$\u0018n\u001c82))\tiKa\u000e\u0003:\tm\"Q\b\u0005\b\u00057y\u0002\u0019\u0001B\u000f\u0011\u001d\u0011\u0019c\ba\u0001\u0005;AqA!\n \u0001\u0004\u00119\u0003C\u0004\u0003.}\u0001\rAa\f\u0015\u0011\t\u0005#1\tB#\u0005\u000f\u00022!a\u0004\f\u0011\u001d\u0011Yb\u0004a\u0001\u0005;AqAa\t\u0010\u0001\u0004\u0011i\u0002C\u0004\u0003&=\u0001\rAa\n\u0003\u0013=\u0013\bK]8dKN\u001c8c\u0001\t\u0002.RA!q\nB)\u0005'\u0012)\u0006E\u0002\u0002\u0010AAqAa\u0007\u0015\u0001\u0004\u0011i\u0002C\u0004\u0003$Q\u0001\rA!\b\t\u000f\t\u0015B\u00031\u0001\u0003(\ty\u0001K]8dKN\u001c8+Z9vK:\u001cWmE\u0002\u0016\u0003[#\u0002B!\u0018\u0003`\t\u0005$1\r\t\u0004\u0003\u001f)\u0002b\u0002B\u000e3\u0001\u0007!Q\u0004\u0005\b\u0005GI\u0002\u0019\u0001B\u000f\u0011\u001d\u0011)#\u0007a\u0001\u0005O\u0011a\u0002U5qK\u0012\u0004&o\\2fgN,7oE\u00022\u0003g\u000b\u0011\u0002Z3gCVdG/S(\u0002\u000fQ|WI\u001d:peRQ!q\u000eB9\u0005g\u0012)Ha\u001e\u0011\u0007\u0005=\u0011\u0007C\u0004\u0003\u001cY\u0002\rA!\b\t\u000f\t\rb\u00071\u0001\u0003\u001e!9!\u0011\u000e\u001cA\u0002\t\u001d\u0002b\u0002B6m\u0001\u0007\u0011qK\u0001\n]\u0016<8k\\;sG\u0016,\"A! \u0011\u0007\u0005=!I\u0001\u0006QSB,7k\\;sG\u0016\u001c2A\u0011BB!\r\tya\u000f\u0002\u000b!&\u0004X\r\u00165sK\u0006$7cA\u001e\u0002 \u00051\u0011n]*j].\fq\u0001\\1cK24e\u000eE\u0003}\u0003\u0017\u000bi\u0004\u0006\u0004\u0003\u0004\nE%1\u0013\u0005\b\u0005\u0013s\u0004\u0019AA,\u0011\u001d\u0011YI\u0010a\u0001\u0005\u001b\u000b1A];o\u0003\u001d\u0011XO\u001c7p_B$b!!\u0002\u0003\u001c\n5\u0006b\u0002BO\u0001\u0002\u0007!qT\u0001\u0004gJ\u001c\u0007\u0003\u0002BQ\u0005OsA!!1\u0003$&\u0019!Q\u0015;\u0002\u001fA\u0014xnY3tg&sG/\u001a:oC2LAA!+\u0003,\nY\u0011J\u001c9viN#(/Z1n\u0015\r\u0011)\u000b\u001e\u0005\b\u0005_\u0003\u0005\u0019\u0001BY\u0003\r!7\u000f\u001e\t\u0005\u0005C\u0013\u0019,\u0003\u0003\u00036\n-&\u0001D(viB,Ho\u0015;sK\u0006l\u0017!C5p\u0011\u0006tG\r\\3s)\u0011\t)Aa/\t\u000f\tu\u0016\t1\u0001\u0003@\u0006\tQ\r\u0005\u0003\u0003\"\n\u0005\u0017\u0002\u0002Bb\u0005W\u00131\"S(Fq\u000e,\u0007\u000f^5p]\u0006)A.\u00192fYB)A0!\u000e\u0002>Q!!Q\u0010Bf\u0011!\u0011)\r\u0012CA\u0002\t\u001d\u0017\u0001\u00029ja\u0016,\"A!5\u0011\t\tM'q[\u0007\u0003\u0005+TAA!\n\u0002(%!!\u0011\u001cBk\u0005E\u0001\u0016\u000e]3e\u001fV$\b/\u001e;TiJ,\u0017-\\\u0001\u0006a&\u0004X\rI\u0001\u0007g>,(oY3\u0016\u0005\t\u0005\bC\u0002Br\u0005[\u0014\t0\u0004\u0002\u0003f*!!q\u001dBu\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0005\u0005W\f9#\u0001\u0003vi&d\u0017\u0002\u0002Bx\u0005K\u00141\u0003T5oW\u0016$'\t\\8dW&tw-U;fk\u0016\u0004R\u0001`Av\u0005?\u000bqa]8ve\u000e,\u0007%A\u0005d_:tWm\u0019;J]R!\u0011Q\u0001B}\u0011\u001d\u0011YP\u0013a\u0001\u0005?\u000b!!\u001b8\u0002\u0015\r|gN\\3di>+H\u000f\u0006\u0003\u0002\u0006\r\u0005\u0001bBB\u0002\u0017\u0002\u00071QA\u0001\u0005g&t7\u000eE\u0002\u0002\u00109\u0013\u0001\u0002U5qKNKgn[\n\u0004\u001d\n\rE\u0003BB\u0003\u0007\u001bA\u0001B!2Q\t\u0003\u0007!qY\u000b\u0003\u0007#\u0001BAa5\u0004\u0014%!1Q\u0003Bk\u0005A\u0001\u0016\u000e]3e\u0013:\u0004X\u000f^*ue\u0016\fW.\u0006\u0002\u0004\u001aA1!1\u001dBw\u00077\u0001R\u0001`Av\u0005c\u000bQa]5oW\u0002\"B!!\u0002\u0004\"!911\u0005,A\u0002\tE\u0016aA8viR!\u0011QAB\u0014\u0011\u001d\u0019Ic\u0016a\u0001\u0005#\fq\u0001]5qK>+H/A\u0004sK2,\u0017m]3\u0002\t\u0011|g.Z\u0001\b]\u0016<8+\u001b8l+\t\u0019)\u0001\u0006\u0004\u0002j\u000eU2q\u0007\u0005\b\u0005;T\u0004\u0019\u0001B?\u0011\u001d\u0019\u0019A\u000fa\u0001\u0007\u000b\u0011A\u0002R;n[f\u0004&o\\2fgN\u001cBAW>\u0002@B)A0!\u000e\u0002XR!1\u0011IB\"!\r\tyA\u0017\u0005\t\u0005/aF\u00111\u0001\u0004>\u0005\u0019\u0001\u0010\n\u001c\u0011\u000fq\f))a\b\u0004JA)A0a#\u0002X\u00061A\u000f\u001b:fC\u0012\fQA^1mk\u0016\u0014QbU5na2,\u0007K]8dKN\u001c8\u0003B2|\u0003\u007f\u000b\u0011\u0001\u001d\t\u0005\u0005C\u001b9&\u0003\u0003\u0004Z\t-&\u0001\u0003&Qe>\u001cWm]:\u0002\u0017%t\u0007/\u001e;UQJ,\u0017\rZ\u0001\u000e_V$\b/\u001e;UQJ,\u0017\rZ:\u0011\r\r\u000541NA\u0010\u001d\u0011\u0019\u0019ga\u001a\u000f\t\u0005\r3QM\u0005\u0002s&\u00191\u0011\u000e=\u0002\u000fA\f7m[1hK&!1QNB8\u0005\u0011a\u0015n\u001d;\u000b\u0007\r%\u0004\u0010\u0006\u0005\u0004t\rU4qOB=!\r\tya\u0019\u0005\b\u0007':\u0007\u0019AB+\u0011\u001d\u0019Yf\u001aa\u0001\u0003?Aqa!\u0018h\u0001\u0004\u0019y&A\u0005j]R,'O];qi\niA\u000b\u001b:fC\u0012\u0004&o\\2fgN\u001cB\u0001\\>\u0002@\u000691/^2dKN\u001c\bC\u0002Br\u0005[\f9\u0006\u0006\u0004\u0004\b\u000e%51\u0012\t\u0004\u0003\u001fa\u0007bBB&_\u0002\u0007\u0011q\u0004\u0005\b\u0007\u0003{\u0007\u0019ABB\u001d\u0011\t\tma$\n\u0007\rEE/A\u0004Qe>\u001cWm]:")
public interface ProcessImpl {
    public ProcessImpl$Spawn$ Spawn();

    public ProcessImpl$Future$ Future();

    public static void $init$(ProcessImpl $this) {
    }

    public class AndProcess
    extends SequentialProcess {
        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$AndProcess$$$outer() {
            return this.$outer;
        }

        public AndProcess(Process$ $outer, ProcessBuilder a, ProcessBuilder b, ProcessIO io) {
            super($outer, a, b, io, (Function1<Object, Object>)((Object)new Serializable(null){
                private static final long serialVersionUID = 0L;

                public final boolean apply(int x$1) {
                    return this.apply$mcZI$sp(x$1);
                }

                public boolean apply$mcZI$sp(int x$1) {
                    return x$1 == 0;
                }
            }));
        }
    }

    public static abstract class BasicProcess
    implements Process {
        public final /* synthetic */ Process$ $outer;

        public abstract void start();

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$BasicProcess$$$outer() {
            return this.$outer;
        }

        public BasicProcess(Process$ $outer) {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public abstract class CompoundProcess
    extends BasicProcess {
        private Tuple4<Thread, Thread, Function0<Option<Object>>, Function0<BoxedUnit>> x$4;
        private Thread processThread;
        private Thread futureThread;
        private Function0<Option<Object>> futureValue;
        private Function0<BoxedUnit> destroyer;
        private volatile byte bitmap$0;

        @Override
        public boolean isAlive() {
            return this.processThread().isAlive();
        }

        @Override
        public void destroy() {
            this.destroyer().apply$mcV$sp();
        }

        @Override
        public int exitValue() {
            Option<Object> option = this.futureValue().apply();
            if (option == null) {
                throw null;
            }
            Option<Object> getOrElse_this = option;
            if (getOrElse_this.isEmpty()) {
                String error_message = "No exit code: process destroyed.";
                throw new RuntimeException(error_message);
            }
            Object var1_1 = null;
            return BoxesRunTime.unboxToInt(getOrElse_this.get());
        }

        @Override
        public void start() {
            this.futureThread();
        }

        /*
         * WARNING - void declaration
         */
        private Tuple4<Thread, Thread, Function0<Option<Object>>, Function0<BoxedUnit>> x$4$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 1) == 0) {
                    void var3_6;
                    void apply_daemon;
                    void apply_prefix;
                    void apply_f;
                    LinkedBlockingQueue code = new LinkedBlockingQueue(1);
                    if (this.scala$sys$process$ProcessImpl$CompoundProcess$$$outer().Spawn() == null) {
                        throw null;
                    }
                    JFunction0$mcV$sp jFunction0$mcV$sp = () -> {
                        block6: {
                            Option value = None$.MODULE$;
                            try {
                                try {
                                    value = this.runAndExitValue();
                                }
                                catch (Throwable throwable) {
                                    if (throwable instanceof IndexOutOfBoundsException ? true : (throwable instanceof IOException ? true : (throwable instanceof NullPointerException ? true : (throwable instanceof SecurityException ? true : throwable instanceof UnsupportedOperationException)))) {
                                        value = new Some<Integer>(-1);
                                        break block6;
                                    }
                                    throw throwable;
                                }
                            }
                            finally {
                                code.put(value);
                            }
                        }
                    };
                    boolean bl = false;
                    String string = "CompoundProcess";
                    if (this.scala$sys$process$ProcessImpl$CompoundProcess$$$outer().Spawn() == null) {
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
                    Object var9_5 = null;
                    Thread thread2 = apply_thread;
                    JFunction0$mcV$sp jFunction0$mcV$sp2 = () -> thread2.interrupt();
                    Tuple2 tuple2 = this.scala$sys$process$ProcessImpl$CompoundProcess$$$outer().Future().apply((Function0<Option> & Serializable)() -> (Option)code.take());
                    Tuple3 tuple3 = new Tuple3(thread2, tuple2, jFunction0$mcV$sp2);
                    if (tuple2 == null) {
                        throw new MatchError(tuple3);
                    }
                    Thread futureThread = tuple2._1();
                    Function0 futureValue = tuple2._2();
                    this.x$4 = new Tuple4(var3_6, futureThread, futureValue, jFunction0$mcV$sp2);
                    this.bitmap$0 = (byte)(this.bitmap$0 | 1);
                }
            }
            return this.x$4;
        }

        private /* synthetic */ Tuple4 x$4() {
            if ((byte)(this.bitmap$0 & 1) == 0) {
                return this.x$4$lzycompute();
            }
            return this.x$4;
        }

        private Thread processThread$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 2) == 0) {
                    this.processThread = (Thread)this.x$4()._1();
                    this.bitmap$0 = (byte)(this.bitmap$0 | 2);
                }
            }
            return this.processThread;
        }

        public Thread processThread() {
            if ((byte)(this.bitmap$0 & 2) == 0) {
                return this.processThread$lzycompute();
            }
            return this.processThread;
        }

        private Thread futureThread$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 4) == 0) {
                    this.futureThread = (Thread)this.x$4()._2();
                    this.bitmap$0 = (byte)(this.bitmap$0 | 4);
                }
            }
            return this.futureThread;
        }

        public Thread futureThread() {
            if ((byte)(this.bitmap$0 & 4) == 0) {
                return this.futureThread$lzycompute();
            }
            return this.futureThread;
        }

        private Function0<Option<Object>> futureValue$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 8) == 0) {
                    this.futureValue = (Function0)this.x$4()._3();
                    this.bitmap$0 = (byte)(this.bitmap$0 | 8);
                }
            }
            return this.futureValue;
        }

        public Function0<Option<Object>> futureValue() {
            if ((byte)(this.bitmap$0 & 8) == 0) {
                return this.futureValue$lzycompute();
            }
            return this.futureValue;
        }

        private Function0<BoxedUnit> destroyer$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 0x10) == 0) {
                    this.destroyer = (Function0)this.x$4()._4();
                    this.bitmap$0 = (byte)(this.bitmap$0 | 0x10);
                }
            }
            return this.destroyer;
        }

        public Function0<BoxedUnit> destroyer() {
            if ((byte)(this.bitmap$0 & 0x10) == 0) {
                return this.destroyer$lzycompute();
            }
            return this.destroyer;
        }

        public abstract Option<Object> runAndExitValue();

        public <T> Option<T> runInterruptible(Function0<T> action, Function0<BoxedUnit> destroyImpl) {
            try {
                return new Some<T>(action.apply());
            }
            catch (Throwable x$9) {
                Function0<None$> & Serializable onInterrupt_handler = (Function0<None$> & Serializable)() -> {
                    destroyImpl.apply$mcV$sp();
                    return None$.MODULE$;
                };
                Object var5_4 = null;
                Serializable catchExpr$1 = new Serializable(onInterrupt_handler){
                    private static final long serialVersionUID = 0L;
                    private final Function0 handler$3;

                    public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                        if (x1 instanceof InterruptedException) {
                            return (B1)this.handler$3.apply();
                        }
                        return function1.apply(x1);
                    }

                    public final boolean isDefinedAt(Throwable x1) {
                        return x1 instanceof InterruptedException;
                    }
                    {
                        this.handler$3 = handler$3;
                    }
                };
                if (catchExpr$1.isDefinedAt(x$9)) {
                    return (Option)catchExpr$1.apply(x$9);
                }
                throw x$9;
            }
        }

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$CompoundProcess$$$outer() {
            return this.$outer;
        }

        public static final /* synthetic */ Nothing$ $anonfun$exitValue$1() {
            return package$.MODULE$.error("No exit code: process destroyed.");
        }

        public CompoundProcess(Process$ $outer) {
            super($outer);
        }
    }

    public static class DummyProcess
    implements Process {
        private final /* synthetic */ Tuple2 x$6;
        private final Thread thread;
        private final Function0<Object> value;
        public final /* synthetic */ Process$ $outer;

        @Override
        public boolean isAlive() {
            return this.thread.isAlive();
        }

        @Override
        public int exitValue() {
            return this.value.apply$mcI$sp();
        }

        @Override
        public void destroy() {
        }

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$DummyProcess$$$outer() {
            return this.$outer;
        }

        public DummyProcess(Process$ $outer, Function0<Object> action) {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            Tuple2<Thread, Function0<Object>> tuple2 = $outer.Future().apply(action);
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Thread thread2 = tuple2._1();
            Function0<Object> value = tuple2._2();
            this.x$6 = new Tuple2<Thread, Function0<Object>>(thread2, value);
            this.thread = (Thread)this.x$6._1();
            this.value = (Function0)this.x$6._2();
        }
    }

    public class OrProcess
    extends SequentialProcess {
        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$OrProcess$$$outer() {
            return this.$outer;
        }

        public OrProcess(Process$ $outer, ProcessBuilder a, ProcessBuilder b, ProcessIO io) {
            super($outer, a, b, io, (Function1<Object, Object>)((Object)new Serializable(null){
                private static final long serialVersionUID = 0L;

                public final boolean apply(int x$2) {
                    return this.apply$mcZI$sp(x$2);
                }

                public boolean apply$mcZI$sp(int x$2) {
                    return x$2 != 0;
                }
            }));
        }
    }

    public class PipeSink
    extends PipeThread {
        private final PipedInputStream pipe;
        private final LinkedBlockingQueue<Option<OutputStream>> sink;

        public PipedInputStream pipe() {
            return this.pipe;
        }

        public LinkedBlockingQueue<Option<OutputStream>> sink() {
            return this.sink;
        }

        @Override
        public void run() {
            block6: {
                try {
                    try {
                        this.go$2();
                    }
                    catch (Throwable x$14) {
                        JFunction0$mcV$sp onInterrupt_handler = () -> {};
                        Object var4_2 = null;
                        Serializable catchExpr$6 = new /* invalid duplicate definition of identical inner class */;
                        if (catchExpr$6.isDefinedAt(x$14)) {
                            catchExpr$6.apply(x$14);
                            break block6;
                        }
                        throw x$14;
                    }
                }
                finally {
                    BasicIO$.MODULE$.close(this.pipe());
                }
            }
        }

        public void connectOut(OutputStream out) {
            this.sink().put(new Some<OutputStream>(out));
        }

        public void connectIn(PipedOutputStream pipeOut) {
            this.pipe().connect(pipeOut);
        }

        public void release() {
            this.interrupt();
            this.done();
            this.join();
        }

        public void done() {
            this.sink().put(None$.MODULE$);
        }

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$PipeSink$$$outer() {
            return this.$outer;
        }

        private final void go$2() {
            Option<OutputStream> option;
            while ((option = this.sink().take()) instanceof Some) {
                OutputStream out = (OutputStream)((Some)option).value();
                this.runloop(this.pipe(), out);
            }
            if (None$.MODULE$.equals(option)) {
                return;
            }
            throw new MatchError(option);
        }

        public PipeSink(Process$ $outer, Function0<String> label) {
            super($outer, true, label);
            this.setName(new StringBuilder(11).append("PipeSink(").append((Object)label.apply()).append(")-").append(this.getName()).toString());
            this.pipe = new PipedInputStream();
            this.sink = new LinkedBlockingQueue(1);
        }
    }

    public class PipeSource
    extends PipeThread {
        private final PipedOutputStream pipe;
        private final LinkedBlockingQueue<Option<InputStream>> source;

        public PipedOutputStream pipe() {
            return this.pipe;
        }

        public LinkedBlockingQueue<Option<InputStream>> source() {
            return this.source;
        }

        @Override
        public final void run() {
            block6: {
                try {
                    try {
                        this.go$1();
                    }
                    catch (Throwable x$13) {
                        JFunction0$mcV$sp onInterrupt_handler = () -> {};
                        Object var4_2 = null;
                        Serializable catchExpr$5 = new /* invalid duplicate definition of identical inner class */;
                        if (catchExpr$5.isDefinedAt(x$13)) {
                            catchExpr$5.apply(x$13);
                            break block6;
                        }
                        throw x$13;
                    }
                }
                finally {
                    BasicIO$.MODULE$.close(this.pipe());
                }
            }
        }

        public void connectIn(InputStream in) {
            this.source().put(new Some<InputStream>(in));
        }

        public void connectOut(PipeSink sink2) {
            sink2.connectIn(this.pipe());
        }

        public void release() {
            this.interrupt();
            this.done();
            this.join();
        }

        public void done() {
            this.source().put(None$.MODULE$);
        }

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$PipeSource$$$outer() {
            return this.$outer;
        }

        private final void go$1() {
            Option<InputStream> option;
            while ((option = this.source().take()) instanceof Some) {
                InputStream in = (InputStream)((Some)option).value();
                this.runloop(in, this.pipe());
            }
            if (None$.MODULE$.equals(option)) {
                return;
            }
            throw new MatchError(option);
        }

        public PipeSource(Process$ $outer, Function0<String> label) {
            super($outer, false, label);
            this.setName(new StringBuilder(13).append("PipeSource(").append((Object)label.apply()).append(")-").append(this.getName()).toString());
            this.pipe = new PipedOutputStream();
            this.source = new LinkedBlockingQueue(1);
        }
    }

    public abstract class PipeThread
    extends Thread {
        private final boolean isSink;
        public final /* synthetic */ Process$ $outer;

        @Override
        public abstract void run();

        public void runloop(InputStream src, OutputStream dst) {
            block5: {
                try {
                    try {
                        BasicIO$.MODULE$.transferFully(src, dst);
                    }
                    catch (Throwable x$12) {
                        Function1<IOException, Object> & Serializable ioFailure_handler = (Function1<IOException, Object> & Serializable)e -> {
                            this.ioHandler(e);
                            return BoxedUnit.UNIT;
                        };
                        Object var6_4 = null;
                        Serializable catchExpr$4 = new Serializable(ioFailure_handler){
                            private static final long serialVersionUID = 0L;
                            private final Function1 handler$4;

                            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                                if (x1 instanceof IOException) {
                                    IOException iOException = (IOException)x1;
                                    return (B1)this.handler$4.apply(iOException);
                                }
                                return function1.apply(x1);
                            }

                            public final boolean isDefinedAt(Throwable x1) {
                                return x1 instanceof IOException;
                            }
                            {
                                this.handler$4 = handler$4;
                            }
                        };
                        if (catchExpr$4.isDefinedAt(x$12)) {
                            catchExpr$4.apply(x$12);
                            break block5;
                        }
                        throw x$12;
                    }
                }
                catch (Throwable throwable) {
                    BasicIO$.MODULE$.close(this.isSink ? dst : src);
                    throw throwable;
                }
            }
            BasicIO$.MODULE$.close(this.isSink ? dst : src);
        }

        private void ioHandler(IOException e) {
            e.printStackTrace();
        }

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$PipeThread$$$outer() {
            return this.$outer;
        }

        public PipeThread(Process$ $outer, boolean isSink, Function0<String> labelFn) {
            this.isSink = isSink;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public class PipedProcesses
    extends CompoundProcess {
        private final ProcessBuilder a;
        private final ProcessBuilder b;
        private final ProcessIO defaultIO;
        private final boolean toError;

        public PipeSource newSource() {
            return new PipeSource(this.scala$sys$process$ProcessImpl$PipedProcesses$$$outer(), (Function0<String> & Serializable)() -> $this.a.toString());
        }

        public PipeSink newSink() {
            return new PipeSink(this.scala$sys$process$ProcessImpl$PipedProcesses$$$outer(), (Function0<String> & Serializable)() -> $this.b.toString());
        }

        @Override
        public Option<Object> runAndExitValue() {
            return this.runAndExitValue(this.newSource(), this.newSink());
        }

        /*
         * WARNING - void declaration
         */
        public Option<Object> runAndExitValue(PipeSource source2, PipeSink sink2) {
            Process process;
            Process process2;
            void withInput_write;
            ProcessIO processIO;
            source2.connectOut(sink2);
            source2.start();
            sink2.start();
            if (this.toError) {
                void withError_process;
                ProcessIO processIO2 = this.defaultIO;
                Function1<InputStream, Object> & Serializable intersect = (Function1<InputStream, Object> & Serializable)in -> {
                    source2.connectIn(in);
                    return BoxedUnit.UNIT;
                };
                if (processIO2 == null) {
                    throw null;
                }
                ProcessIO withError_this = processIO2;
                processIO = new ProcessIO(withError_this.writeInput(), withError_this.processOutput(), (Function1<InputStream, BoxedUnit>)withError_process, withError_this.daemonizeThreads());
                Object var9_4 = null;
                intersect = null;
            } else {
                void withOutput_process;
                ProcessIO processIO3 = this.defaultIO;
                Function1<InputStream, Object> & Serializable intersect = (Function1<InputStream, Object> & Serializable)in -> {
                    source2.connectIn(in);
                    return BoxedUnit.UNIT;
                };
                if (processIO3 == null) {
                    throw null;
                }
                ProcessIO withOutput_this = processIO3;
                processIO = new ProcessIO(withOutput_this.writeInput(), (Function1<InputStream, BoxedUnit>)withOutput_process, withOutput_this.processError(), withOutput_this.daemonizeThreads());
                Object var11_6 = null;
                intersect = null;
            }
            ProcessIO firstIO = processIO;
            ProcessIO processIO4 = this.defaultIO;
            Function1<OutputStream, Object> & Serializable intersect = (Function1<OutputStream, Object> & Serializable)out -> {
                sink2.connectOut(out);
                return BoxedUnit.UNIT;
            };
            if (processIO4 == null) {
                throw null;
            }
            ProcessIO withInput_this = processIO4;
            Object var13_9 = null;
            intersect = null;
            ProcessIO secondIO = new ProcessIO((Function1<OutputStream, BoxedUnit>)withInput_write, withInput_this.processOutput(), withInput_this.processError(), withInput_this.daemonizeThreads());
            try {
                process2 = this.b.run(secondIO);
            }
            catch (Throwable x$10) {
                Function1<Throwable, Nothing$> & Serializable onError_handler = (Function1<Throwable, Nothing$> & Serializable)err -> {
                    PipedProcesses.releaseResources$1(source2, sink2, Nil$.MODULE$);
                    throw err;
                };
                Object var15_12 = null;
                process2 = (Process)new Serializable(onError_handler){
                    private static final long serialVersionUID = 0L;
                    private final Function1 handler$1;

                    public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                        return (B1)this.handler$1.apply(x1);
                    }

                    public final boolean isDefinedAt(Throwable x1) {
                        return true;
                    }
                    {
                        this.handler$1 = handler$1;
                    }
                }.apply(x$10);
            }
            Process second = process2;
            try {
                process = this.a.run(firstIO);
            }
            catch (Throwable x$11) {
                Function1<Throwable, Nothing$> & Serializable onError_handler = (Function1<Throwable, Nothing$> & Serializable)err -> {
                    PipedProcesses.releaseResources$1(source2, sink2, ScalaRunTime$.MODULE$.wrapRefArray(new Process[]{second}));
                    throw err;
                };
                Object var16_15 = null;
                process = (Process)new /* invalid duplicate definition of identical inner class */.apply(x$11);
            }
            Process first = process;
            return this.runInterruptible(() -> {
                int exit1 = first.exitValue();
                source2.done();
                source2.join();
                int exit2 = second.exitValue();
                sink2.done();
                if ($this.b.hasExitValue()) {
                    return exit2;
                }
                return exit1;
            }, () -> PipedProcesses.releaseResources$1(source2, sink2, ScalaRunTime$.MODULE$.wrapRefArray(new Process[]{first, second})));
        }

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$PipedProcesses$$$outer() {
            return this.$outer;
        }

        private static final void releaseResources$1(PipeSource so, PipeSink sk, Seq ps) {
            so.release();
            sk.release();
            ps.foreach((Function1<Process, Object> & Serializable)x$5 -> {
                x$5.destroy();
                return BoxedUnit.UNIT;
            });
        }

        public PipedProcesses(Process$ $outer, ProcessBuilder a, ProcessBuilder b, ProcessIO defaultIO, boolean toError) {
            this.a = a;
            this.b = b;
            this.defaultIO = defaultIO;
            this.toError = toError;
            super($outer);
        }
    }

    public class ProcessSequence
    extends SequentialProcess {
        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$ProcessSequence$$$outer() {
            return this.$outer;
        }

        public ProcessSequence(Process$ $outer, ProcessBuilder a, ProcessBuilder b, ProcessIO io) {
            super($outer, a, b, io, (Function1<Object, Object>)((Object)new Serializable(null){
                private static final long serialVersionUID = 0L;

                public final boolean apply(int x$3) {
                    return true;
                }

                public boolean apply$mcZI$sp(int x$3) {
                    return true;
                }
            }));
        }
    }

    public class SequentialProcess
    extends CompoundProcess {
        private final ProcessBuilder a;
        private final ProcessBuilder b;
        private final ProcessIO io;
        private final Function1<Object, Object> evaluateSecondProcess;

        @Override
        public Option<Object> runAndExitValue() {
            Process first = this.a.run(this.io);
            Option<Object> option = this.runInterruptible(() -> first.exitValue(), () -> first.destroy());
            if (option == null) {
                throw null;
            }
            Option<Object> flatMap_this = option;
            if (flatMap_this.isEmpty()) {
                return None$.MODULE$;
            }
            Object object = flatMap_this.get();
            return SequentialProcess.$anonfun$runAndExitValue$3(this, BoxesRunTime.unboxToInt(object));
        }

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$SequentialProcess$$$outer() {
            return this.$outer;
        }

        public static final /* synthetic */ Option $anonfun$runAndExitValue$3(SequentialProcess $this, int codeA) {
            if ($this.evaluateSecondProcess.apply$mcZI$sp(codeA)) {
                Process second = $this.b.run($this.io);
                return $this.runInterruptible(() -> second.exitValue(), () -> second.destroy());
            }
            return new Some<Integer>(codeA);
        }

        public SequentialProcess(Process$ $outer, ProcessBuilder a, ProcessBuilder b, ProcessIO io, Function1<Object, Object> evaluateSecondProcess) {
            this.a = a;
            this.b = b;
            this.io = io;
            this.evaluateSecondProcess = evaluateSecondProcess;
            super($outer);
        }

        public static final /* synthetic */ Option $anonfun$runAndExitValue$3$adapted(SequentialProcess $this, Object codeA) {
            return SequentialProcess.$anonfun$runAndExitValue$3($this, BoxesRunTime.unboxToInt(codeA));
        }
    }

    public class SimpleProcess
    implements Process {
        private final java.lang.Process p;
        private final Thread inputThread;
        private final List<Thread> outputThreads;
        public final /* synthetic */ Process$ $outer;

        @Override
        public boolean isAlive() {
            return this.p.isAlive();
        }

        @Override
        public int exitValue() {
            try {
                this.p.waitFor();
            }
            finally {
                this.interrupt();
            }
            List list = this.outputThreads;
            if (list == null) {
                throw null;
            }
            List foreach_these = list;
            while (!foreach_these.isEmpty()) {
                ((Thread)foreach_these.head()).join();
                foreach_these = (List)foreach_these.tail();
            }
            Object var2_2 = null;
            return this.p.exitValue();
        }

        @Override
        public void destroy() {
            try {
                List list = this.outputThreads;
                if (list == null) {
                    throw null;
                }
                List foreach_these = list;
                while (!foreach_these.isEmpty()) {
                    ((Thread)foreach_these.head()).interrupt();
                    foreach_these = (List)foreach_these.tail();
                }
                Object var2_1 = null;
                this.p.destroy();
            }
            finally {
                this.interrupt();
            }
        }

        private void interrupt() {
            if (this.inputThread != null) {
                this.inputThread.interrupt();
                return;
            }
        }

        public /* synthetic */ Process$ scala$sys$process$ProcessImpl$SimpleProcess$$$outer() {
            return this.$outer;
        }

        public SimpleProcess(Process$ $outer, java.lang.Process p, Thread inputThread, List<Thread> outputThreads) {
            this.p = p;
            this.inputThread = inputThread;
            this.outputThreads = outputThreads;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }

        public static final /* synthetic */ Object $anonfun$exitValue$2$adapted(Thread x$7) {
            x$7.join();
            return BoxedUnit.UNIT;
        }

        public static final /* synthetic */ Object $anonfun$destroy$1$adapted(Thread x$8) {
            x$8.interrupt();
            return BoxedUnit.UNIT;
        }
    }

    public final class ThreadProcess
    implements Process {
        private final Thread thread;
        private final LinkedBlockingQueue<Object> success;

        @Override
        public boolean isAlive() {
            return this.thread.isAlive();
        }

        @Override
        public int exitValue() {
            if (BoxesRunTime.unboxToBoolean(this.success.take())) {
                return 0;
            }
            return 1;
        }

        @Override
        public void destroy() {
            this.thread.interrupt();
        }

        public ThreadProcess(Process$ $outer, Thread thread2, LinkedBlockingQueue<Object> success) {
            this.thread = thread2;
            this.success = success;
        }
    }
}

