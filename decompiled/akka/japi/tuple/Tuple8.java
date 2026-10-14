/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple8$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r]s!B\u0017/\u0011\u0003)d!B\u001c/\u0011\u0003A\u0004\"B$\u0002\t\u0003A\u0005\"B%\u0002\t\u0003Q\u0005\"\u0003Bq\u0003\u0005\u0005I\u0011\u0011Br\u0011%\u0019I\"AA\u0001\n\u0003\u001bY\u0002C\u0005\u0004N\u0005\t\t\u0011\"\u0003\u0004P\u0019!qG\f\"N\u0011!ivA!f\u0001\n\u0003q\u0006\u0002\u00036\b\u0005#\u0005\u000b\u0011B0\t\u0011-<!Q3A\u0005\u00021D\u0001\u0002]\u0004\u0003\u0012\u0003\u0006I!\u001c\u0005\tc\u001e\u0011)\u001a!C\u0001e\"Aao\u0002B\tB\u0003%1\u000f\u0003\u0005x\u000f\tU\r\u0011\"\u0001y\u0011!axA!E!\u0002\u0013I\b\u0002C?\b\u0005+\u0007I\u0011\u0001@\t\u0013\u0005\u0015qA!E!\u0002\u0013y\bBCA\u0004\u000f\tU\r\u0011\"\u0001\u0002\n!Q\u0011\u0011C\u0004\u0003\u0012\u0003\u0006I!a\u0003\t\u0015\u0005MqA!f\u0001\n\u0003\t)\u0002\u0003\u0006\u0002\u001e\u001d\u0011\t\u0012)A\u0005\u0003/A!\"a\b\b\u0005+\u0007I\u0011AA\u0011\u0011)\tIc\u0002B\tB\u0003%\u00111\u0005\u0005\u0007\u000f\u001e!\t!a\u000b\t\u0013\u0005}rA1A\u0005\u0002\u0005\u0005\u0003\u0002CA$\u000f\u0001\u0006I!a\u0011\t\u0013\u0005%s!!A\u0005\u0002\u0005-\u0003\"CAA\u000fE\u0005I\u0011AAB\u0011%\tYkBI\u0001\n\u0003\ti\u000bC\u0005\u0002D\u001e\t\n\u0011\"\u0001\u0002F\"I\u00111\\\u0004\u0012\u0002\u0013\u0005\u0011Q\u001c\u0005\n\u0003g<\u0011\u0013!C\u0001\u0003kD\u0011Ba\u0003\b#\u0003%\tA!\u0004\t\u0013\t\rr!%A\u0005\u0002\t\u0015\u0002\"\u0003B\u001e\u000fE\u0005I\u0011\u0001B\u001f\u0011%\u0011\u0019fBA\u0001\n\u0003\u0012)\u0006C\u0005\u0003d\u001d\t\t\u0011\"\u0001\u0003f!I!QN\u0004\u0002\u0002\u0013\u0005!q\u000e\u0005\n\u0005k:\u0011\u0011!C!\u0005oB\u0011B!\"\b\u0003\u0003%\tAa\"\t\u0013\tEu!!A\u0005B\tM\u0005\"\u0003BL\u000f\u0005\u0005I\u0011\tBM\u0011%\u0011YjBA\u0001\n\u0003\u0012i\nC\u0005\u0003 \u001e\t\t\u0011\"\u0011\u0003\"\u00061A+\u001e9mKbR!a\f\u0019\u0002\u000bQ,\b\u000f\\3\u000b\u0005E\u0012\u0014\u0001\u00026ba&T\u0011aM\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005Y\nQ\"\u0001\u0018\u0003\rQ+\b\u000f\\39'\r\t\u0011h\u0010\t\u0003uuj\u0011a\u000f\u0006\u0002y\u0005)1oY1mC&\u0011ah\u000f\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0001+U\"A!\u000b\u0005\t\u001b\u0015AA5p\u0015\u0005!\u0015\u0001\u00026bm\u0006L!AR!\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\rqJg.\u001b;?)\u0005)\u0014AB2sK\u0006$X-F\tL\u0005g\u00139La/\u0003@\n\r'q\u0019Bf\u0005\u001f$\u0012\u0003\u0014Bi\u0005'\u0014)Na6\u0003Z\nm'Q\u001cBp!I1tA!-\u00036\ne&Q\u0018Ba\u0005\u000b\u0014IM!4\u0016\u001b9\u000bg\u000e\u001e>\u0002\u0002\u00055\u0011\u0011DA\u0013'\u00119\u0011h\u0014*\u0011\u0005i\u0002\u0016BA)<\u0005\u001d\u0001&o\u001c3vGR\u0004\"aU.\u000f\u0005QKfBA+Y\u001b\u00051&BA,5\u0003\u0019a$o\\8u}%\tA(\u0003\u0002[w\u00059\u0001/Y2lC\u001e,\u0017B\u0001$]\u0015\tQ6(\u0001\u0002ucU\tq\f\u0005\u0002aC2\u0001A!\u00022\b\u0005\u0004\u0019'A\u0001+2#\t!w\r\u0005\u0002;K&\u0011am\u000f\u0002\b\u001d>$\b.\u001b8h!\tQ\u0004.\u0003\u0002jw\t\u0019\u0011I\\=\u0002\u0007Q\f\u0004%\u0001\u0002ueU\tQ\u000e\u0005\u0002a]\u0012)qn\u0002b\u0001G\n\u0011AKM\u0001\u0004iJ\u0002\u0013A\u0001;4+\u0005\u0019\bC\u00011u\t\u0015)xA1\u0001d\u0005\t!6'A\u0002ug\u0001\n!\u0001\u001e\u001b\u0016\u0003e\u0004\"\u0001\u0019>\u0005\u000bm<!\u0019A2\u0003\u0005Q#\u0014a\u0001;5A\u0005\u0011A/N\u000b\u0002\u007fB\u0019\u0001-!\u0001\u0005\r\u0005\rqA1\u0001d\u0005\t!V'A\u0002uk\u0001\n!\u0001\u001e\u001c\u0016\u0005\u0005-\u0001c\u00011\u0002\u000e\u00111\u0011qB\u0004C\u0002\r\u0014!\u0001\u0016\u001c\u0002\u0007Q4\u0004%\u0001\u0002uoU\u0011\u0011q\u0003\t\u0004A\u0006eAABA\u000e\u000f\t\u00071M\u0001\u0002Uo\u0005\u0019Ao\u000e\u0011\u0002\u0005QDTCAA\u0012!\r\u0001\u0017Q\u0005\u0003\u0007\u0003O9!\u0019A2\u0003\u0005QC\u0014a\u0001;9AQ\u0011\u0012QFA\u0018\u0003c\t\u0019$!\u000e\u00028\u0005e\u00121HA\u001f!51taX7ts~\fY!a\u0006\u0002$!)Q\f\u0007a\u0001?\")1\u000e\u0007a\u0001[\")\u0011\u000f\u0007a\u0001g\")q\u000f\u0007a\u0001s\")Q\u0010\u0007a\u0001\u007f\"9\u0011q\u0001\rA\u0002\u0005-\u0001bBA\n1\u0001\u0007\u0011q\u0003\u0005\b\u0003?A\u0002\u0019AA\u0012\u0003\u001d!xnU2bY\u0006,\"!a\u0011\u0011\u001di\n)eX7ts~\fY!a\u0006\u0002$%\u0011qgO\u0001\ti>\u001c6-\u00197bA\u0005!1m\u001c9z+I\ti%a\u0015\u0002X\u0005m\u0013qLA2\u0003O\nY'a\u001c\u0015%\u0005=\u0013\u0011OA:\u0003k\n9(!\u001f\u0002|\u0005u\u0014q\u0010\t\u0013m\u001d\t\t&!\u0016\u0002Z\u0005u\u0013\u0011MA3\u0003S\ni\u0007E\u0002a\u0003'\"QAY\u000eC\u0002\r\u00042\u0001YA,\t\u0015y7D1\u0001d!\r\u0001\u00171\f\u0003\u0006kn\u0011\ra\u0019\t\u0004A\u0006}C!B>\u001c\u0005\u0004\u0019\u0007c\u00011\u0002d\u00111\u00111A\u000eC\u0002\r\u00042\u0001YA4\t\u0019\tya\u0007b\u0001GB\u0019\u0001-a\u001b\u0005\r\u0005m1D1\u0001d!\r\u0001\u0017q\u000e\u0003\u0007\u0003OY\"\u0019A2\t\u0011u[\u0002\u0013!a\u0001\u0003#B\u0001b[\u000e\u0011\u0002\u0003\u0007\u0011Q\u000b\u0005\tcn\u0001\n\u00111\u0001\u0002Z!Aqo\u0007I\u0001\u0002\u0004\ti\u0006\u0003\u0005~7A\u0005\t\u0019AA1\u0011%\t9a\u0007I\u0001\u0002\u0004\t)\u0007C\u0005\u0002\u0014m\u0001\n\u00111\u0001\u0002j!I\u0011qD\u000e\u0011\u0002\u0003\u0007\u0011QN\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+I\t))a'\u0002\u001e\u0006}\u0015\u0011UAR\u0003K\u000b9+!+\u0016\u0005\u0005\u001d%fA0\u0002\n.\u0012\u00111\u0012\t\u0005\u0003\u001b\u000b9*\u0004\u0002\u0002\u0010*!\u0011\u0011SAJ\u0003%)hn\u00195fG.,GMC\u0002\u0002\u0016n\n!\"\u00198o_R\fG/[8o\u0013\u0011\tI*a$\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003c9\t\u00071\rB\u0003p9\t\u00071\rB\u0003v9\t\u00071\rB\u0003|9\t\u00071\r\u0002\u0004\u0002\u0004q\u0011\ra\u0019\u0003\u0007\u0003\u001fa\"\u0019A2\u0005\r\u0005mAD1\u0001d\t\u0019\t9\u0003\bb\u0001G\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TCEAX\u0003g\u000b),a.\u0002:\u0006m\u0016QXA`\u0003\u0003,\"!!-+\u00075\fI\tB\u0003c;\t\u00071\rB\u0003p;\t\u00071\rB\u0003v;\t\u00071\rB\u0003|;\t\u00071\r\u0002\u0004\u0002\u0004u\u0011\ra\u0019\u0003\u0007\u0003\u001fi\"\u0019A2\u0005\r\u0005mQD1\u0001d\t\u0019\t9#\bb\u0001G\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTCEAd\u0003\u0017\fi-a4\u0002R\u0006M\u0017Q[Al\u00033,\"!!3+\u0007M\fI\tB\u0003c=\t\u00071\rB\u0003p=\t\u00071\rB\u0003v=\t\u00071\rB\u0003|=\t\u00071\r\u0002\u0004\u0002\u0004y\u0011\ra\u0019\u0003\u0007\u0003\u001fq\"\u0019A2\u0005\r\u0005maD1\u0001d\t\u0019\t9C\bb\u0001G\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCEAp\u0003G\f)/a:\u0002j\u0006-\u0018Q^Ax\u0003c,\"!!9+\u0007e\fI\tB\u0003c?\t\u00071\rB\u0003p?\t\u00071\rB\u0003v?\t\u00071\rB\u0003|?\t\u00071\r\u0002\u0004\u0002\u0004}\u0011\ra\u0019\u0003\u0007\u0003\u001fy\"\u0019A2\u0005\r\u0005mqD1\u0001d\t\u0019\t9c\bb\u0001G\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012*TCEA|\u0003w\fi0a@\u0003\u0002\t\r!Q\u0001B\u0004\u0005\u0013)\"!!?+\u0007}\fI\tB\u0003cA\t\u00071\rB\u0003pA\t\u00071\rB\u0003vA\t\u00071\rB\u0003|A\t\u00071\r\u0002\u0004\u0002\u0004\u0001\u0012\ra\u0019\u0003\u0007\u0003\u001f\u0001#\u0019A2\u0005\r\u0005m\u0001E1\u0001d\t\u0019\t9\u0003\tb\u0001G\u0006q1m\u001c9zI\u0011,g-Y;mi\u00122TC\u0005B\b\u0005'\u0011)Ba\u0006\u0003\u001a\tm!Q\u0004B\u0010\u0005C)\"A!\u0005+\t\u0005-\u0011\u0011\u0012\u0003\u0006E\u0006\u0012\ra\u0019\u0003\u0006_\u0006\u0012\ra\u0019\u0003\u0006k\u0006\u0012\ra\u0019\u0003\u0006w\u0006\u0012\ra\u0019\u0003\u0007\u0003\u0007\t#\u0019A2\u0005\r\u0005=\u0011E1\u0001d\t\u0019\tY\"\tb\u0001G\u00121\u0011qE\u0011C\u0002\r\fabY8qs\u0012\"WMZ1vYR$s'\u0006\n\u0003(\t-\"Q\u0006B\u0018\u0005c\u0011\u0019D!\u000e\u00038\teRC\u0001B\u0015U\u0011\t9\"!#\u0005\u000b\t\u0014#\u0019A2\u0005\u000b=\u0014#\u0019A2\u0005\u000bU\u0014#\u0019A2\u0005\u000bm\u0014#\u0019A2\u0005\r\u0005\r!E1\u0001d\t\u0019\tyA\tb\u0001G\u00121\u00111\u0004\u0012C\u0002\r$a!a\n#\u0005\u0004\u0019\u0017AD2paf$C-\u001a4bk2$H\u0005O\u000b\u0013\u0005\u007f\u0011\u0019E!\u0012\u0003H\t%#1\nB'\u0005\u001f\u0012\t&\u0006\u0002\u0003B)\"\u00111EAE\t\u0015\u00117E1\u0001d\t\u0015y7E1\u0001d\t\u0015)8E1\u0001d\t\u0015Y8E1\u0001d\t\u0019\t\u0019a\tb\u0001G\u00121\u0011qB\u0012C\u0002\r$a!a\u0007$\u0005\u0004\u0019GABA\u0014G\t\u00071-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0005/\u0002BA!\u0017\u0003`5\u0011!1\f\u0006\u0004\u0005;\u001a\u0015\u0001\u00027b]\u001eLAA!\u0019\u0003\\\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"Aa\u001a\u0011\u0007i\u0012I'C\u0002\u0003lm\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2a\u001aB9\u0011%\u0011\u0019HJA\u0001\u0002\u0004\u00119'A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0005s\u0002RAa\u001f\u0003\u0002\u001el!A! \u000b\u0007\t}4(\u0001\u0006d_2dWm\u0019;j_:LAAa!\u0003~\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\u0011IIa$\u0011\u0007i\u0012Y)C\u0002\u0003\u000en\u0012qAQ8pY\u0016\fg\u000e\u0003\u0005\u0003t!\n\t\u00111\u0001h\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\t]#Q\u0013\u0005\n\u0005gJ\u0013\u0011!a\u0001\u0005O\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0005O\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005/\na!Z9vC2\u001cH\u0003\u0002BE\u0005GC\u0001Ba\u001d-\u0003\u0003\u0005\ra\u001a\u0015\b\u000f\t\u001d&Q\u0016BX!\rQ$\u0011V\u0005\u0004\u0005W[$\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0001c\u00011\u00034\u0012)!m\u0001b\u0001GB\u0019\u0001Ma.\u0005\u000b=\u001c!\u0019A2\u0011\u0007\u0001\u0014Y\fB\u0003v\u0007\t\u00071\rE\u0002a\u0005\u007f#Qa_\u0002C\u0002\r\u00042\u0001\u0019Bb\t\u0019\t\u0019a\u0001b\u0001GB\u0019\u0001Ma2\u0005\r\u0005=1A1\u0001d!\r\u0001'1\u001a\u0003\u0007\u00037\u0019!\u0019A2\u0011\u0007\u0001\u0014y\r\u0002\u0004\u0002(\r\u0011\ra\u0019\u0005\u0007;\u000e\u0001\rA!-\t\r-\u001c\u0001\u0019\u0001B[\u0011\u0019\t8\u00011\u0001\u0003:\"1qo\u0001a\u0001\u0005{Ca!`\u0002A\u0002\t\u0005\u0007bBA\u0004\u0007\u0001\u0007!Q\u0019\u0005\b\u0003'\u0019\u0001\u0019\u0001Be\u0011\u001d\tyb\u0001a\u0001\u0005\u001b\fQ!\u00199qYf,\"C!:\u0003l\n=(1\u001fB|\u0005w\u0014ypa\u0001\u0004\bQ\u0011\"q]B\u0005\u0007\u0017\u0019iaa\u0004\u0004\u0012\rM1QCB\f!I1tA!;\u0003n\nE(Q\u001fB}\u0005{\u001c\ta!\u0002\u0011\u0007\u0001\u0014Y\u000fB\u0003c\t\t\u00071\rE\u0002a\u0005_$Qa\u001c\u0003C\u0002\r\u00042\u0001\u0019Bz\t\u0015)HA1\u0001d!\r\u0001'q\u001f\u0003\u0006w\u0012\u0011\ra\u0019\t\u0004A\nmHABA\u0002\t\t\u00071\rE\u0002a\u0005\u007f$a!a\u0004\u0005\u0005\u0004\u0019\u0007c\u00011\u0004\u0004\u00111\u00111\u0004\u0003C\u0002\r\u00042\u0001YB\u0004\t\u0019\t9\u0003\u0002b\u0001G\"1Q\f\u0002a\u0001\u0005SDaa\u001b\u0003A\u0002\t5\bBB9\u0005\u0001\u0004\u0011\t\u0010\u0003\u0004x\t\u0001\u0007!Q\u001f\u0005\u0007{\u0012\u0001\rA!?\t\u000f\u0005\u001dA\u00011\u0001\u0003~\"9\u00111\u0003\u0003A\u0002\r\u0005\u0001bBA\u0010\t\u0001\u00071QA\u0001\bk:\f\u0007\u000f\u001d7z+I\u0019ib!\u000b\u0004.\rE2QGB\u001d\u0007{\u0019\te!\u0012\u0015\t\r}1q\t\t\u0006u\r\u00052QE\u0005\u0004\u0007GY$AB(qi&|g\u000eE\n;\u0003\u000b\u001a9ca\u000b\u00040\rM2qGB\u001e\u0007\u007f\u0019\u0019\u0005E\u0002a\u0007S!QAY\u0003C\u0002\r\u00042\u0001YB\u0017\t\u0015yWA1\u0001d!\r\u00017\u0011\u0007\u0003\u0006k\u0016\u0011\ra\u0019\t\u0004A\u000eUB!B>\u0006\u0005\u0004\u0019\u0007c\u00011\u0004:\u00111\u00111A\u0003C\u0002\r\u00042\u0001YB\u001f\t\u0019\ty!\u0002b\u0001GB\u0019\u0001m!\u0011\u0005\r\u0005mQA1\u0001d!\r\u00017Q\t\u0003\u0007\u0003O)!\u0019A2\t\u0013\r%S!!AA\u0002\r-\u0013a\u0001=%aA\u0011bgBB\u0014\u0007W\u0019yca\r\u00048\rm2qHB\"\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0019\t\u0006\u0005\u0003\u0003Z\rM\u0013\u0002BB+\u00057\u0012aa\u00142kK\u000e$\b")
public final class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>
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
    private final scala.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> toScala;

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Option<scala.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> unapply(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> x$0) {
        return Tuple8$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return Tuple8$.MODULE$.apply(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return Tuple8$.MODULE$.create(t1, t2, t3, t4, t5, t6, t7, t8);
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

    public scala.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T6 copy$default$6() {
        return this.t6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T7 copy$default$7() {
        return this.t7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> T8 copy$default$8() {
        return this.t8();
    }

    @Override
    public String productPrefix() {
        return "Tuple8";
    }

    @Override
    public int productArity() {
        return 8;
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
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Tuple8;
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
        if (!(object instanceof Tuple8)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple8 tuple8 = (Tuple8)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple8.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple8.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple8.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple8.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple8.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple8.t6())) return false;
        if (!BoxesRunTime.equals(this.t7(), tuple8.t7())) return false;
        if (!BoxesRunTime.equals(this.t8(), tuple8.t8())) return false;
        return true;
    }

    public Tuple8(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        Product.$init$(this);
        this.toScala = new scala.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(t1, t2, t3, t4, t5, t6, t7, t8);
    }
}

