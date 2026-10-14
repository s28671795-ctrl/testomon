/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.IterableOnceOps;
import scala.collection.Seq;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.reflect.AnyValManifest;
import scala.reflect.ClassManifestDeprecatedApis;
import scala.reflect.ClassTag;
import scala.reflect.Manifest;
import scala.reflect.Manifest$;
import scala.reflect.ManifestFactory$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Null$;

@ScalaSignature(bytes="\u0006\u0005!Er\u0001CA:\u0003kB\t!a \u0007\u0011\u0005\r\u0015Q\u000fE\u0001\u0003\u000bCq!a$\u0002\t\u0003\t\t\nC\u0004\u0002\u0014\u0006!\t!!&\u0007\u0011\u0005\r\u0017AAA;\u0003\u000bDq!a$\u0005\t\u0003\ty\rC\u0004\u0002V\u0012!\t!a6\t\u000f\u00055H\u0001\"\u0011\u0002p\"9!\u0011\u0002\u0003\u0005B\t-\u0001b\u0002B\u0010\t\u0011\u0005#\u0011\u0005\u0005\b\u0005S!A\u0011\tB\u0016\u0011\u001d\u00119\u0004\u0002C\u0005\u0005sA\u0011Ba\u0012\u0002\u0005\u0004%\tA!\u0013\t\u0011\t-\u0013\u0001)A\u0005\u0003#4\u0001B!\u0014\u0002\u0005\u0005U$q\n\u0005\b\u0003\u001fsA\u0011\u0001B-\u0011\u001d\t)N\u0004C\u0001\u0005;Bq!!<\u000f\t\u0003\u0012)\u0007C\u0004\u0003\n9!\tE!\u001c\t\u000f\t}a\u0002\"\u0011\u0003t!9!\u0011\u0006\b\u0005B\t]\u0004b\u0002B\u001c\u001d\u0011%!\u0011\b\u0005\n\u0005\u007f\n!\u0019!C\u0001\u0005\u0003C\u0001Ba!\u0002A\u0003%!1\f\u0004\t\u0005\u000b\u000b!!!\u001e\u0003\b\"9\u0011q\u0012\r\u0005\u0002\tE\u0005bBAk1\u0011\u0005!Q\u0013\u0005\b\u0003[DB\u0011\tBP\u0011\u001d\u0011I\u0001\u0007C!\u0005OCqAa\b\u0019\t\u0003\u0012i\u000bC\u0004\u0003*a!\tE!-\t\u000f\t]\u0002\u0004\"\u0003\u0003:!I!\u0011X\u0001C\u0002\u0013\u0005!1\u0018\u0005\t\u0005{\u000b\u0001\u0015!\u0003\u0003\u0014\u001aA!qX\u0001\u0003\u0003k\u0012\t\rC\u0004\u0002\u0010\n\"\tA!2\t\u000f\u0005U'\u0005\"\u0001\u0003J\"9\u0011Q\u001e\u0012\u0005B\tM\u0007b\u0002B\u0005E\u0011\u0005#1\u001c\u0005\b\u0005?\u0011C\u0011\tBq\u0011\u001d\u0011IC\tC!\u0005KDqAa\u000e#\t\u0013\u0011I\u0004C\u0005\u0003n\u0006\u0011\r\u0011\"\u0001\u0003p\"A!\u0011_\u0001!\u0002\u0013\u00119M\u0002\u0005\u0003t\u0006\u0011\u0011Q\u000fB{\u0011\u001d\ty\t\fC\u0001\u0005\u007fDq!!6-\t\u0003\u0019\u0019\u0001C\u0004\u0002n2\"\tea\u0003\t\u000f\t%A\u0006\"\u0011\u0004\u0014!9!q\u0004\u0017\u0005B\re\u0001b\u0002B\u0015Y\u0011\u00053Q\u0004\u0005\b\u0005oaC\u0011\u0002B\u001d\u0011%\u0019)#\u0001b\u0001\n\u0003\u00199\u0003\u0003\u0005\u0004*\u0005\u0001\u000b\u0011BB\u0001\r!\u0019Y#\u0001\u0002\u0002v\r5\u0002bBAHm\u0011\u00051q\u0007\u0005\b\u0003+4D\u0011AB\u001e\u0011\u001d\tiO\u000eC!\u0007\u0007BqA!\u00037\t\u0003\u001aY\u0005C\u0004\u0003 Y\"\te!\u0015\t\u000f\t%b\u0007\"\u0011\u0004V!9!q\u0007\u001c\u0005\n\te\u0002\"CB/\u0003\t\u0007I\u0011AB0\u0011!\u0019\t'\u0001Q\u0001\n\reb\u0001CB2\u0003\t\t)h!\u001a\t\u000f\u0005=\u0005\t\"\u0001\u0004p!9\u0011Q\u001b!\u0005\u0002\rM\u0004bBAw\u0001\u0012\u000531\u0010\u0005\b\u0005\u0013\u0001E\u0011IBB\u0011\u001d\u0011y\u0002\u0011C!\u0007\u0013CqA!\u000bA\t\u0003\u001ai\tC\u0004\u00038\u0001#IA!\u000f\t\u0013\rU\u0015A1A\u0005\u0002\r]\u0005\u0002CBM\u0003\u0001\u0006Ia!\u001d\u0007\u0011\rm\u0015AAA;\u0007;Cq!a$K\t\u0003\u00199\u000bC\u0004\u0002V*#\taa+\t\u000f\u00055(\n\"\u0011\u00044\"9!\u0011\u0002&\u0005B\rm\u0006b\u0002B\u0010\u0015\u0012\u00053\u0011\u0019\u0005\b\u0005SQE\u0011IBc\u0011\u001d\u00119D\u0013C\u0005\u0005sA\u0011b!4\u0002\u0005\u0004%\taa4\t\u0011\rE\u0017\u0001)A\u0005\u0007S3\u0001ba5\u0002\u0005\u0005U4Q\u001b\u0005\b\u0003\u001f#F\u0011ABp\u0011\u001d\t)\u000e\u0016C\u0001\u0007GDq!!<U\t\u0003\u001ai\u000fC\u0004\u0003\nQ#\te!>\t\u000f\t}A\u000b\"\u0011\u0004|\"91q +\u0005R\u0011\u0005\u0001b\u0002B\u0015)\u0012\u0005Cq\u0006\u0005\b\u0005o!F\u0011\u0002B\u001d\u0011%!9$\u0001b\u0001\n\u0003!I\u0004\u0003\u0005\u0005<\u0005\u0001\u000b\u0011BBq\u0011!!i$\u0001Q\u0001\n\u0011}\u0002\u0002\u0003C$\u0003\u0001\u0006I\u0001\"\u0013\t\u0011\u0011]\u0013\u0001)A\u0005\t32a\u0001\"\u0019\u0002\r\u0011\r\u0004bBAHE\u0012\u0005Q\u0011\u0005\u0005\b\u0003[\u0014G\u0011IC\u0013\u0011\u001d)YC\u0019C!\u000b[AqAa\u000ec\t\u0013\u0011I\u0004C\u0005\u0006H\u0005\u0011\r\u0011\"\u0001\u0006J!AQQJ\u0001!\u0002\u0013)YE\u0002\u0004\u0006P\u00051Q\u0011\u000b\u0005\b\u0003\u001fKG\u0011AC+\u0011\u001d\ti/\u001bC!\u000b3Bq!b\u000bj\t\u0003*y\u0006C\u0004\u00038%$IA!\u000f\t\u0013\u0015=\u0014A1A\u0005\u0002\u0015E\u0004\u0002CC;\u0003\u0001\u0006I!b\u001d\t\u0013\u0015]\u0014A1A\u0005\u0002\u0015e\u0004\u0002CC?\u0003\u0001\u0006I!b\u001f\u0007\r\u0015}\u0014ABCA\u0011\u001d\tyI\u001dC\u0001\u000b\u0017Cq!!<s\t\u0003*y\tC\u0004\u0006,I$\t%\"&\t\u000f\t]\"\u000f\"\u0003\u0003:!IQQU\u0001C\u0002\u0013\u0005Qq\u0015\u0005\t\u000bW\u000b\u0001\u0015!\u0003\u0006*\u001a1QQV\u0001\u0007\u000b_Cq!a$z\t\u0003)I\fC\u0004\u0002nf$\t%\"0\t\u000f\u0015-\u0012\u0010\"\u0011\u0006D\"9!qG=\u0005\n\te\u0002\"CCj\u0003\t\u0007I\u0011ACk\u0011!)I.\u0001Q\u0001\n\u0015]gABCn\u0003\u0019)i\u000e\u0003\u0005\u0002\u0010\u0006\u0005A\u0011ACq\u0011!\ti/!\u0001\u0005B\u0015\u0015\b\u0002CC\u0016\u0003\u0003!\t%b;\t\u0011\t]\u0012\u0011\u0001C\u0005\u0005sA\u0011\"b?\u0002\u0005\u0004%\t!\"@\t\u0011\u0019\u0005\u0011\u0001)A\u0005\u000b\u007f4aAb\u0001\u0002\r\u0019\u0015\u0001b\u0003B\"\u0003\u001f\u0011\t\u0011)A\u0005\u0003\u000fC\u0001\"a$\u0002\u0010\u0011\u0005a\u0011\u0003\u0005\f\u0003+\fy\u0001#b\u0001\n\u000319\u0002C\u0006\u0005V\u0006=\u0001R1A\u0005B\u0019\u0005\u0002b\u0002D\u0013\u0003\u0011\u0005aq\u0005\u0005\b\rg\tA\u0011\u0001D\u001b\u0011\u001d1\u0019$\u0001C\u0001\r\u001bBqAb\r\u0002\t\u00031yHB\u0004\u0005h\u0005\tI\u0001\"\u001b\t\u001b\u0011\u0015\u0018\u0011\u0005B\u0001B\u0003%Aq]A\u001b\u0011-!).!\t\u0003\u0006\u0004%\t\u0005\"=\t\u0017\u0011]\u0018\u0011\u0005B\u0001B\u0003%A1\u001f\u0005\t\u0003\u001f\u000b\t\u0003\"\u0001\u0005z\"AQ\u0011BA\u0011\t\u0003*Y\u0001\u0003\u0006\u0006\u0012\u0005\u0005\"\u0019!C!\u000b'A\u0011\"\"\u0006\u0002\"\u0001\u0006I!a?\u0007\r\u0011=\u0014\u0001\u0002C9\u0011-!y(!\r\u0003\u0002\u0003\u0006I\u0001\"!\t\u0017\u0005U\u0017\u0011\u0007BC\u0002\u0013\u0005AQ\u0012\u0005\f\t7\u000b\tD!A!\u0002\u0013!y\tC\u0006\u0005\u001e\u0006E\"Q1A\u0005B\u0011}\u0005b\u0003CW\u0003c\u0011\t\u0011)A\u0005\tCC\u0001\"a$\u00022\u0011\u0005Aq\u0016\u0005\t\t+\f\t\u0004\"\u0011\u0005X\"9aqV\u0001\u0005\u0002\u0019EfA\u0002Df\u0003\u00111i\rC\u0006\u0005\u0000\u0005\r#\u0011!Q\u0001\n\u0019]\u0007b\u0003Dq\u0003\u0007\u0012\t\u0011)A\u0005\tgD1Bb9\u0002D\t\u0005\t\u0015!\u0003\u0007f\"Ya1NA\"\u0005\u0003\u0005\u000b\u0011\u0002Dx\u0011!\ty)a\u0011\u0005\u0002\u001d\u0005\u0001\u0002CAk\u0003\u0007\"\tab\n\t\u0015\u0011u\u00151\tb\u0001\n\u0003:\t\u0004C\u0005\u0005.\u0006\r\u0003\u0015!\u0003\b4!AAQ[A\"\t\u0003\"9\u000eC\u0004\b@\u0005!\ta\"\u0011\u0007\r\u001dM\u0014\u0001BD;\u0011-9y(!\u0017\u0003\u0002\u0003\u0006Ia\"!\t\u0017\u0019\r\u0018\u0011\fB\u0001B\u0003%q1\u0012\u0005\t\u0003\u001f\u000bI\u0006\"\u0001\b\u0016\"A\u0011Q[A-\t\u00039i\u000b\u0003\u0005\u0005V\u0006eC\u0011\tCl\u0011\u001d9Y,\u0001C\u0001\u000f{3aab8\u0002\t\u001d\u0005\bbCDv\u0003O\u0012\t\u0011)A\u0005\u000f[D\u0001\"a$\u0002h\u0011\u0005q\u0011 \u0005\t\u0003+\f9\u0007\"\u0001\t\n!AAQ[A4\t\u0003B\u0019\u0002C\u0004\t\u0018\u0005!\t\u0001#\u0007\u0002\u001f5\u000bg.\u001b4fgR4\u0015m\u0019;pefTA!a\u001e\u0002z\u00059!/\u001a4mK\u000e$(BAA>\u0003\u0015\u00198-\u00197b\u0007\u0001\u00012!!!\u0002\u001b\t\t)HA\bNC:Lg-Z:u\r\u0006\u001cGo\u001c:z'\r\t\u0011q\u0011\t\u0005\u0003\u0013\u000bY)\u0004\u0002\u0002z%!\u0011QRA=\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\"!a \u0002\u001dY\fG.^3NC:Lg-Z:ugV\u0011\u0011q\u0013\t\u0007\u00033\u000by*!*\u000f\t\u0005%\u00151T\u0005\u0005\u0003;\u000bI(A\u0004qC\u000e\\\u0017mZ3\n\t\u0005\u0005\u00161\u0015\u0002\u0005\u0019&\u001cHO\u0003\u0003\u0002\u001e\u0006e\u0004\u0007BAT\u0003c\u0003b!!!\u0002*\u00065\u0016\u0002BAV\u0003k\u0012a\"\u00118z-\u0006dW*\u00198jM\u0016\u001cH\u000f\u0005\u0003\u00020\u0006EF\u0002\u0001\u0003\f\u0003g\u001b\u0011\u0011!A\u0001\u0006\u0003\t)L\u0001\u0003`IE:\u0014\u0003BA\\\u0003{\u0003B!!#\u0002:&!\u00111XA=\u0005\u001dqu\u000e\u001e5j]\u001e\u0004B!!#\u0002@&!\u0011\u0011YA=\u0005\r\te.\u001f\u0002\r\u0005f$X-T1oS\u001a,7\u000f^\n\u0004\t\u0005\u001d\u0007CBAA\u0003S\u000bI\r\u0005\u0003\u0002\n\u0006-\u0017\u0002BAg\u0003s\u0012AAQ=uKR\u0011\u0011\u0011\u001b\t\u0004\u0003'$Q\"A\u0001\u0002\u0019I,h\u000e^5nK\u000ec\u0017m]:\u0016\u0005\u0005e\u0007CBAn\u0003K\fI/\u0004\u0002\u0002^*!\u0011q\\Aq\u0003\u0011a\u0017M\\4\u000b\u0005\u0005\r\u0018\u0001\u00026bm\u0006LA!a:\u0002^\n)1\t\\1tgB!\u00111\\Av\u0013\u0011\ti-!8\u0002\u00119,w/\u0011:sCf$B!!=\u0002xB1\u0011\u0011RAz\u0003\u0013LA!!>\u0002z\t)\u0011I\u001d:bs\"9\u0011\u0011`\u0004A\u0002\u0005m\u0018a\u00017f]B!\u0011\u0011RA\u007f\u0013\u0011\ty0!\u001f\u0003\u0007%sG\u000fK\u0002\b\u0005\u0007\u0001B!!#\u0003\u0006%!!qAA=\u0005\u0019Ig\u000e\\5oK\u0006ya.Z<Xe\u0006\u0004\b/\u001a3BeJ\f\u0017\u0010\u0006\u0003\u0003\u000e\tu\u0001C\u0002B\b\u00053\tI-\u0004\u0002\u0003\u0012)!!1\u0003B\u000b\u0003\u001diW\u000f^1cY\u0016TAAa\u0006\u0002z\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\tm!\u0011\u0003\u0002\t\u0003J\u0014\u0018-_*fc\"9\u0011\u0011 \u0005A\u0002\u0005m\u0018a\u00048fo\u0006\u0013(/Y=Ck&dG-\u001a:\u0015\u0005\t\r\u0002C\u0002B\b\u0005K\tI-\u0003\u0003\u0003(\tE!\u0001D!se\u0006L()^5mI\u0016\u0014\u0018aB;oCB\u0004H.\u001f\u000b\u0005\u0005[\u0011\u0019\u0004\u0005\u0004\u0002\n\n=\u0012\u0011Z\u0005\u0005\u0005c\tIH\u0001\u0004PaRLwN\u001c\u0005\b\u0005kQ\u0001\u0019AA_\u0003\u0005A\u0018a\u0003:fC\u0012\u0014Vm]8mm\u0016$\"!!0)\u000f\u0011\u0011iDa\u0011\u0003FA!\u0011\u0011\u0012B \u0013\u0011\u0011\t%!\u001f\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001\u0002\t\tKH/Z\u000b\u0003\u0003#\fQAQ=uK\u0002\u0012Qb\u00155peRl\u0015M\\5gKN$8c\u0001\b\u0003RA1\u0011\u0011QAU\u0005'\u0002B!!#\u0003V%!!qKA=\u0005\u0015\u0019\u0006n\u001c:u)\t\u0011Y\u0006E\u0002\u0002T:)\"Aa\u0018\u0011\r\u0005m\u0017Q\u001dB1!\u0011\tYNa\u0019\n\t\t]\u0013Q\u001c\u000b\u0005\u0005O\u0012I\u0007\u0005\u0004\u0002\n\u0006M(1\u000b\u0005\b\u0003s\f\u0002\u0019AA~Q\r\t\"1\u0001\u000b\u0005\u0005_\u0012\t\b\u0005\u0004\u0003\u0010\te!1\u000b\u0005\b\u0003s\u0014\u0002\u0019AA~)\t\u0011)\b\u0005\u0004\u0003\u0010\t\u0015\"1\u000b\u000b\u0005\u0005s\u0012Y\b\u0005\u0004\u0002\n\n=\"1\u000b\u0005\b\u0005k!\u0002\u0019AA_Q\u001dq!Q\bB\"\u0005\u000b\nQa\u00155peR,\"Aa\u0017\u0002\rMCwN\u001d;!\u00051\u0019\u0005.\u0019:NC:Lg-Z:u'\rA\"\u0011\u0012\t\u0007\u0003\u0003\u000bIKa#\u0011\t\u0005%%QR\u0005\u0005\u0005\u001f\u000bIH\u0001\u0003DQ\u0006\u0014HC\u0001BJ!\r\t\u0019\u000eG\u000b\u0003\u0005/\u0003b!a7\u0002f\ne\u0005\u0003BAn\u00057KAA!(\u0002^\nI1\t[1sC\u000e$XM\u001d\u000b\u0005\u0005C\u0013\u0019\u000b\u0005\u0004\u0002\n\u0006M(1\u0012\u0005\b\u0003s\\\u0002\u0019AA~Q\rY\"1\u0001\u000b\u0005\u0005S\u0013Y\u000b\u0005\u0004\u0003\u0010\te!1\u0012\u0005\b\u0003sd\u0002\u0019AA~)\t\u0011y\u000b\u0005\u0004\u0003\u0010\t\u0015\"1\u0012\u000b\u0005\u0005g\u0013)\f\u0005\u0004\u0002\n\n=\"1\u0012\u0005\b\u0005kq\u0002\u0019AA_Q\u001dA\"Q\bB\"\u0005\u000b\nAa\u00115beV\u0011!1S\u0001\u0006\u0007\"\f'\u000f\t\u0002\f\u0013:$X*\u00198jM\u0016\u001cHoE\u0002#\u0005\u0007\u0004b!!!\u0002*\u0006mHC\u0001Bd!\r\t\u0019NI\u000b\u0003\u0005\u0017\u0004b!a7\u0002f\n5\u0007\u0003BAn\u0005\u001fLAA!5\u0002^\n9\u0011J\u001c;fO\u0016\u0014H\u0003\u0002Bk\u0005/\u0004b!!#\u0002t\u0006m\bbBA}K\u0001\u0007\u00111 \u0015\u0004K\t\rA\u0003\u0002Bo\u0005?\u0004bAa\u0004\u0003\u001a\u0005m\bbBA}M\u0001\u0007\u00111 \u000b\u0003\u0005G\u0004bAa\u0004\u0003&\u0005mH\u0003\u0002Bt\u0005S\u0004b!!#\u00030\u0005m\bb\u0002B\u001bQ\u0001\u0007\u0011Q\u0018\u0015\bE\tu\"1\tB#\u0003\rIe\u000e^\u000b\u0003\u0005\u000f\fA!\u00138uA\taAj\u001c8h\u001b\u0006t\u0017NZ3tiN\u0019AFa>\u0011\r\u0005\u0005\u0015\u0011\u0016B}!\u0011\tIIa?\n\t\tu\u0018\u0011\u0010\u0002\u0005\u0019>tw\r\u0006\u0002\u0004\u0002A\u0019\u00111\u001b\u0017\u0016\u0005\r\u0015\u0001CBAn\u0003K\u001c9\u0001\u0005\u0003\u0002\\\u000e%\u0011\u0002\u0002B\u007f\u0003;$Ba!\u0004\u0004\u0010A1\u0011\u0011RAz\u0005sDq!!?0\u0001\u0004\tY\u0010K\u00020\u0005\u0007!Ba!\u0006\u0004\u0018A1!q\u0002B\r\u0005sDq!!?1\u0001\u0004\tY\u0010\u0006\u0002\u0004\u001cA1!q\u0002B\u0013\u0005s$Baa\b\u0004\"A1\u0011\u0011\u0012B\u0018\u0005sDqA!\u000e3\u0001\u0004\ti\fK\u0004-\u0005{\u0011\u0019E!\u0012\u0002\t1{gnZ\u000b\u0003\u0007\u0003\tQ\u0001T8oO\u0002\u0012QB\u00127pCRl\u0015M\\5gKN$8c\u0001\u001c\u00040A1\u0011\u0011QAU\u0007c\u0001B!!#\u00044%!1QGA=\u0005\u00151En\\1u)\t\u0019I\u0004E\u0002\u0002TZ*\"a!\u0010\u0011\r\u0005m\u0017Q]B !\u0011\tYn!\u0011\n\t\rU\u0012Q\u001c\u000b\u0005\u0007\u000b\u001a9\u0005\u0005\u0004\u0002\n\u0006M8\u0011\u0007\u0005\b\u0003sL\u0004\u0019AA~Q\rI$1\u0001\u000b\u0005\u0007\u001b\u001ay\u0005\u0005\u0004\u0003\u0010\te1\u0011\u0007\u0005\b\u0003sT\u0004\u0019AA~)\t\u0019\u0019\u0006\u0005\u0004\u0003\u0010\t\u00152\u0011\u0007\u000b\u0005\u0007/\u001aI\u0006\u0005\u0004\u0002\n\n=2\u0011\u0007\u0005\b\u0005ka\u0004\u0019AA_Q\u001d1$Q\bB\"\u0005\u000b\nQA\u00127pCR,\"a!\u000f\u0002\r\u0019cw.\u0019;!\u00059!u.\u001e2mK6\u000bg.\u001b4fgR\u001c2\u0001QB4!\u0019\t\t)!+\u0004jA!\u0011\u0011RB6\u0013\u0011\u0019i'!\u001f\u0003\r\u0011{WO\u00197f)\t\u0019\t\bE\u0002\u0002T\u0002+\"a!\u001e\u0011\r\u0005m\u0017Q]B<!\u0011\tYn!\u001f\n\t\r5\u0014Q\u001c\u000b\u0005\u0007{\u001ay\b\u0005\u0004\u0002\n\u0006M8\u0011\u000e\u0005\b\u0003s\u001c\u0005\u0019AA~Q\r\u0019%1\u0001\u000b\u0005\u0007\u000b\u001b9\t\u0005\u0004\u0003\u0010\te1\u0011\u000e\u0005\b\u0003s$\u0005\u0019AA~)\t\u0019Y\t\u0005\u0004\u0003\u0010\t\u00152\u0011\u000e\u000b\u0005\u0007\u001f\u001b\t\n\u0005\u0004\u0002\n\n=2\u0011\u000e\u0005\b\u0005k1\u0005\u0019AA_Q\u001d\u0001%Q\bB\"\u0005\u000b\na\u0001R8vE2,WCAB9\u0003\u001d!u.\u001e2mK\u0002\u0012qBQ8pY\u0016\fg.T1oS\u001a,7\u000f^\n\u0004\u0015\u000e}\u0005CBAA\u0003S\u001b\t\u000b\u0005\u0003\u0002\n\u000e\r\u0016\u0002BBS\u0003s\u0012qAQ8pY\u0016\fg\u000e\u0006\u0002\u0004*B\u0019\u00111\u001b&\u0016\u0005\r5\u0006CBAn\u0003K\u001cy\u000b\u0005\u0003\u0002\\\u000eE\u0016\u0002BBS\u0003;$Ba!.\u00048B1\u0011\u0011RAz\u0007CCq!!?N\u0001\u0004\tY\u0010K\u0002N\u0005\u0007!Ba!0\u0004@B1!q\u0002B\r\u0007CCq!!?O\u0001\u0004\tY\u0010\u0006\u0002\u0004DB1!q\u0002B\u0013\u0007C#Baa2\u0004JB1\u0011\u0011\u0012B\u0018\u0007CCqA!\u000eQ\u0001\u0004\ti\fK\u0004K\u0005{\u0011\u0019E!\u0012\u0002\u000f\t{w\u000e\\3b]V\u00111\u0011V\u0001\t\u0005>|G.Z1oA\taQK\\5u\u001b\u0006t\u0017NZ3tiN\u0019Aka6\u0011\r\u0005\u0005\u0015\u0011VBm!\u0011\tIia7\n\t\ru\u0017\u0011\u0010\u0002\u0005+:LG\u000f\u0006\u0002\u0004bB\u0019\u00111\u001b+\u0016\u0005\r\u0015\bCBAn\u0003K\u001c9\u000f\u0005\u0003\u0002\\\u000e%\u0018\u0002BBv\u0003;\u0014AAV8jIR!1q^By!\u0019\tI)a=\u0004Z\"9\u0011\u0011`,A\u0002\u0005m\bfA,\u0003\u0004Q!1q_B}!\u0019\u0011yA!\u0007\u0004Z\"9\u0011\u0011 -A\u0002\u0005mHCAB\u007f!\u0019\u0011yA!\n\u0004Z\u0006Q\u0011M\u001d:bs\u000ec\u0017m]:\u0016\t\u0011\rAQ\u0004\u000b\u0005\t\u000b!\t\u0003\u0005\u0004\u0005\b\u0011UA\u0011\u0004\b\u0005\t\u0013!\t\u0002\u0005\u0003\u0005\f\u0005eTB\u0001C\u0007\u0015\u0011!y!! \u0002\rq\u0012xn\u001c;?\u0013\u0011!\u0019\"!\u001f\u0002\rA\u0013X\rZ3g\u0013\u0011\t9\u000fb\u0006\u000b\t\u0011M\u0011\u0011\u0010\t\u0007\u0003\u0013\u000b\u0019\u0010b\u0007\u0011\t\u0005=FQ\u0004\u0003\b\t?Q&\u0019AA[\u0005\u0005!\u0006b\u0002C\u00125\u0002\u0007AQE\u0001\u0003iB\u0004D\u0001b\n\u0005,A1Aq\u0001C\u000b\tS\u0001B!a,\u0005,\u0011aAQ\u0006C\u0011\u0003\u0003\u0005\tQ!\u0001\u00026\n!q\fJ\u00199)\u0011!\t\u0004b\r\u0011\r\u0005%%qFBm\u0011\u001d\u0011)d\u0017a\u0001\u0003{Cs\u0001\u0016B\u001f\u0005\u0007\u0012)%\u0001\u0003V]&$XCABq\u0003\u0015)f.\u001b;!\u0003)y%M[3diRK\u0006+\u0012\t\u0007\u00037\f)\u000f\"\u0011\u0011\t\u0005mG1I\u0005\u0005\t\u000b\niN\u0001\u0004PE*,7\r^\u0001\f\u001d>$\b.\u001b8h)f\u0003V\t\u0005\u0004\u0002\\\u0006\u0015H1\n\t\u0005\t\u001b\"\u0019&\u0004\u0002\u0005P)!A\u0011KA=\u0003\u001d\u0011XO\u001c;j[\u0016LA\u0001\"\u0016\u0005P\tAaj\u001c;iS:<G%\u0001\u0005Ok2dG+\u0017)F!\u0019\tY.!:\u0005\\A!AQ\nC/\u0013\u0011!y\u0006b\u0014\u0003\u000b9+H\u000e\u001c\u0013\u0003\u0017\u0005s\u00170T1oS\u001a,7\u000f^\n\u0004E\u0012\u0015\u0004CBAj\u0003C\tiLA\bQQ\u0006tGo\\7NC:Lg-Z:u+\u0011!Y\u0007b9\u0014\t\u0005\u0005BQ\u000e\t\u0007\u0003'\f\t\u0004\"9\u0003#\rc\u0017m]:UsB,W*\u00198jM\u0016\u001cH/\u0006\u0003\u0005t\u0011u4CBA\u0019\u0003\u000f#)\b\u0005\u0004\u0002\u0002\u0012]D1P\u0005\u0005\ts\n)H\u0001\u0005NC:Lg-Z:u!\u0011\ty\u000b\" \u0005\u0011\u0011}\u0011\u0011\u0007b\u0001\u0003k\u000ba\u0001\u001d:fM&D\bCBAE\u0005_!\u0019\t\r\u0003\u0005\u0006\u0012%\u0005CBAA\to\"9\t\u0005\u0003\u00020\u0012%E\u0001\u0004CF\u0003g\t\t\u0011!A\u0003\u0002\u0005U&\u0001B0%gE*\"\u0001b$1\t\u0011EEq\u0013\t\u0007\t'#)\u0002\"&\u000f\t\u0005%E\u0011\u0003\t\u0005\u0003_#9\n\u0002\u0007\u0005\u001a\u0006]\u0012\u0011!A\u0001\u0006\u0003\t)L\u0001\u0003`IM\u0012\u0014!\u0004:v]RLW.Z\"mCN\u001c\b%A\u0007usB,\u0017I]4v[\u0016tGo]\u000b\u0003\tC\u0003b!!'\u0002 \u0012\r\u0006\u0007\u0002CS\tS\u0003b!!!\u0005x\u0011\u001d\u0006\u0003BAX\tS#A\u0002b+\u0002<\u0005\u0005\t\u0011!B\u0001\u0003k\u0013Aa\u0018\u00134g\u0005qA/\u001f9f\u0003J<W/\\3oiN\u0004C\u0003\u0003CY\tg#y\f\"3\u0011\r\u0005M\u0017\u0011\u0007C>\u0011!!y(!\u0010A\u0002\u0011U\u0006CBAE\u0005_!9\f\r\u0003\u0005:\u0012u\u0006CBAA\to\"Y\f\u0005\u0003\u00020\u0012uF\u0001\u0004CF\tg\u000b\t\u0011!A\u0003\u0002\u0005U\u0006\u0002CAk\u0003{\u0001\r\u0001\"11\t\u0011\rGq\u0019\t\u0007\t'#)\u0002\"2\u0011\t\u0005=Fq\u0019\u0003\r\t3#y,!A\u0001\u0002\u000b\u0005\u0011Q\u0017\u0005\t\t;\u000bi\u00041\u0001\u0005LB1\u0011\u0011TAP\t\u001b\u0004D\u0001b4\u0005TB1\u0011\u0011\u0011C<\t#\u0004B!a,\u0005T\u0012aA1\u0016Ce\u0003\u0003\u0005\tQ!\u0001\u00026\u0006AAo\\*ue&tw\r\u0006\u0002\u0005ZB!\u00111\u001cCn\u0013\u0011!i.!8\u0003\rM#(/\u001b8hQ!\t\tD!\u0010\u0003D\t\u0015\u0003\u0003BAX\tG$\u0001\u0002b\b\u0002\"\t\u0007\u0011QW\u0001\u000e?J,h\u000e^5nK\u000ec\u0017m]:1\t\u0011%HQ\u001e\t\u0007\t'#)\u0002b;\u0011\t\u0005=FQ\u001e\u0003\r\t_\f\u0019#!A\u0001\u0002\u000b\u0005\u0011Q\u0017\u0002\u0005?\u0012\u001a\u0004'\u0006\u0002\u0005tB!Aq\u0001C{\u0013\u0011!i\u000eb\u0006\u0002\u0013Q|7\u000b\u001e:j]\u001e\u0004CC\u0002C~\t{,9\u0001\u0005\u0004\u0002T\u0006\u0005B\u0011\u001d\u0005\t\tK\fI\u00031\u0001\u0005\u0000B\"Q\u0011AC\u0003!\u0019!\u0019\n\"\u0006\u0006\u0004A!\u0011qVC\u0003\t1!y\u000f\"@\u0002\u0002\u0003\u0005)\u0011AA[\u0011!!).!\u000bA\u0002\u0011M\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0004\"\u00165\u0001\u0002CC\b\u0003W\u0001\r!!0\u0002\tQD\u0017\r^\u0001\tQ\u0006\u001c\bnQ8eKV\u0011\u00111`\u0001\nQ\u0006\u001c\bnQ8eK\u0002BC!a\f\u0006\u001aA!\u0011\u0011RC\u000e\u0013\u0011)i\"!\u001f\u0003\u0013Q\u0014\u0018M\\:jK:$\b\u0006CA\u0011\u0005{\u0011\u0019E!\u0012\u0015\u0005\u0015\r\u0002cAAjER!QqEC\u0015!\u0019\tI)a=\u0002>\"9\u0011\u0011 3A\u0002\u0005m\u0018\u0001\u0005\u0013mKN\u001cHeY8m_:$C.Z:t)\u0011\u0019\t+b\f\t\u000f\u0015=Q\r1\u0001\u00062A\"Q1GC!!\u0019))$\"\u000f\u0006@9!\u0011\u0011QC\u001c\u0013\u0011\ti*!\u001e\n\t\u0015mRQ\b\u0002\u000e\u00072\f7o]'b]&4Wm\u001d;\u000b\t\u0005u\u0015Q\u000f\t\u0005\u0003_+\t\u0005\u0002\u0007\u0006D\u0015=\u0012\u0011!A\u0001\u0006\u0003\t)L\u0001\u0003`IEJ\u0004f\u00022\u0003>\t\r#QI\u0001\u0004\u0003:LXCAC&!\u0019\t\t\tb\u001e\u0002>\u0006!\u0011I\\=!\u00059y%M[3di6\u000bg.\u001b4fgR\u001c2![C*!\u0019\t\u0019.!\t\u0005BQ\u0011Qq\u000b\t\u0004\u0003'LG\u0003BC.\u000b;\u0002b!!#\u0002t\u0012\u0005\u0003bBA}W\u0002\u0007\u00111 \u000b\u0005\u0007C+\t\u0007C\u0004\u0006\u00101\u0004\r!b\u00191\t\u0015\u0015T\u0011\u000e\t\u0007\u000bk)I$b\u001a\u0011\t\u0005=V\u0011\u000e\u0003\r\u000bW*\t'!A\u0001\u0002\u000b\u0005\u0011Q\u0017\u0002\u0005?\u0012\u0012\u0004\u0007K\u0004j\u0005{\u0011\u0019E!\u0012\u0002\r=\u0013'.Z2u+\t)\u0019\b\u0005\u0004\u0002\u0002\u0012]D\u0011I\u0001\b\u001f\nTWm\u0019;!\u0003\u0019\te.\u001f*fMV\u0011Q1\u0010\t\u0007\u0003\u0003#9(a\"\u0002\u000f\u0005s\u0017PU3gA\t)\u0012I\\=WC2\u0004\u0006.\u00198u_6l\u0015M\\5gKN$8c\u0001:\u0006\u0004B1\u00111[A\u0011\u000b\u000b\u0003B!!#\u0006\b&!Q\u0011RA=\u0005\u0019\te.\u001f,bYR\u0011QQ\u0012\t\u0004\u0003'\u0014H\u0003BCI\u000b'\u0003b!!#\u0002t\u0016\u0015\u0005bBA}i\u0002\u0007\u00111 \u000b\u0005\u0007C+9\nC\u0004\u0006\u0010U\u0004\r!\"'1\t\u0015mUq\u0014\t\u0007\u000bk)I$\"(\u0011\t\u0005=Vq\u0014\u0003\r\u000bC+9*!A\u0001\u0002\u000b\u0005\u0011Q\u0017\u0002\u0005?\u0012\u0012\u0014\u0007K\u0004s\u0005{\u0011\u0019E!\u0012\u0002\r\u0005s\u0017PV1m+\t)I\u000b\u0005\u0004\u0002\u0002\u0012]TQQ\u0001\b\u0003:Lh+\u00197!\u00051qU\u000f\u001c7NC:Lg-Z:u'\rIX\u0011\u0017\t\u0007\u0003'\f\t#b-\u0011\t\u0005%UQW\u0005\u0005\u000bo\u000bIH\u0001\u0003Ok2dGCAC^!\r\t\u0019.\u001f\u000b\u0005\u000b\u007f+\t\r\u0005\u0004\u0002\n\u0006MX1\u0017\u0005\b\u0003s\\\b\u0019AA~)\u0011\u0019\t+\"2\t\u000f\u0015=A\u00101\u0001\u0006HB\"Q\u0011ZCg!\u0019))$\"\u000f\u0006LB!\u0011qVCg\t1)y-\"2\u0002\u0002\u0003\u0005)\u0011AA[\u0005\u0011yFE\r\u001a)\u000fe\u0014iDa\u0011\u0003F\u0005!a*\u001e7m+\t)9\u000e\u0005\u0004\u0002\u0002\u0012]T1W\u0001\u0006\u001dVdG\u000e\t\u0002\u0010\u001d>$\b.\u001b8h\u001b\u0006t\u0017NZ3tiN!\u0011\u0011ACp!\u0019\t\u0019.!\t\u00028R\u0011Q1\u001d\t\u0005\u0003'\f\t\u0001\u0006\u0003\u0006h\u0016%\bCBAE\u0003g\f9\f\u0003\u0005\u0002z\u0006\u0015\u0001\u0019AA~)\u0011\u0019\t+\"<\t\u0011\u0015=\u0011q\u0001a\u0001\u000b_\u0004D!\"=\u0006vB1QQGC\u001d\u000bg\u0004B!a,\u0006v\u0012aQq_Cw\u0003\u0003\u0005\tQ!\u0001\u00026\n!q\f\n\u001a4Q!\t\tA!\u0010\u0003D\t\u0015\u0013a\u0002(pi\"LgnZ\u000b\u0003\u000b\u007f\u0004b!!!\u0005x\u0005]\u0016\u0001\u0003(pi\"Lgn\u001a\u0011\u0003+MKgn\u001a7fi>tG+\u001f9f\u001b\u0006t\u0017NZ3tiV!aq\u0001D\u0007'\u0019\ty!a\"\u0007\nA1\u0011\u0011\u0011C<\r\u0017\u0001B!a,\u0007\u000e\u0011AAqDA\b\u0005\u00041y!\u0005\u0003\u00028\u0006\u001dE\u0003\u0002D\n\r+\u0001b!a5\u0002\u0010\u0019-\u0001\u0002\u0003B\"\u0003'\u0001\r!a\"\u0016\u0005\u0019e\u0001\u0007\u0002D\u000e\r?\u0001b!a7\u0002f\u001au\u0001\u0003BAX\r?!A\u0002b\b\u0002\u0016\u0005\u0005\t\u0011!B\u0001\r\u001f)\"\u0001\"7)\u0011\u0005=!Q\bB\"\u0005\u000b\n!b]5oO2,G+\u001f9f+\u00111ICb\f\u0015\t\u0019-b\u0011\u0007\t\u0007\u0003\u0003#9H\"\f\u0011\t\u0005=fq\u0006\u0003\t\t?\tIB1\u0001\u0007\u0010!A!1IA\r\u0001\u0004\t9)A\u0005dY\u0006\u001c8\u000fV=qKV!aq\u0007D\u001f)\u00111IDb\u0010\u0011\r\u0005\u0005Eq\u000fD\u001e!\u0011\tyK\"\u0010\u0005\u0011\u0011}\u00111\u0004b\u0001\u0003kC\u0001B\"\u0011\u0002\u001c\u0001\u0007a1I\u0001\u0006G2\f'P\u001f\u0019\u0005\r\u000b2I\u0005\u0005\u0004\u0005\u0014\u0012Uaq\t\t\u0005\u0003_3I\u0005\u0002\u0007\u0007L\u0019}\u0012\u0011!A\u0001\u0006\u0003\t)L\u0001\u0003`II\"T\u0003\u0002D(\r+\"\u0002B\"\u0015\u0007X\u0019mc\u0011\u000e\t\u0007\u0003\u0003#9Hb\u0015\u0011\t\u0005=fQ\u000b\u0003\t\t?\tiB1\u0001\u00026\"Aa\u0011IA\u000f\u0001\u00041I\u0006\u0005\u0004\u0005\u0014\u0012Ua1\u000b\u0005\t\r;\ni\u00021\u0001\u0007`\u0005!\u0011M]42a\u00111\tG\"\u001a\u0011\r\u0005\u0005Eq\u000fD2!\u0011\tyK\"\u001a\u0005\u0019\u0019\u001dd1LA\u0001\u0002\u0003\u0015\t!!.\u0003\t}##'\u000e\u0005\t\rW\ni\u00021\u0001\u0007n\u0005!\u0011M]4t!\u0019\tIIb\u001c\u0007t%!a\u0011OA=\u0005)a$/\u001a9fCR,GM\u0010\u0019\u0005\rk2I\b\u0005\u0004\u0002\u0002\u0012]dq\u000f\t\u0005\u0003_3I\b\u0002\u0007\u0007|\u0019u\u0014\u0011!A\u0001\u0006\u0003\t)L\u0001\u0003`II2\u0004\u0002\u0003D6\u0003;\u0001\rA\"\u001c\u0016\t\u0019\u0005eq\u0011\u000b\t\r\u00073II\"&\u0007\"B1\u0011\u0011\u0011C<\r\u000b\u0003B!a,\u0007\b\u0012AAqDA\u0010\u0005\u0004\t)\f\u0003\u0005\u0005\u0000\u0005}\u0001\u0019\u0001DFa\u00111iI\"%\u0011\r\u0005\u0005Eq\u000fDH!\u0011\tyK\"%\u0005\u0019\u0019Me\u0011RA\u0001\u0002\u0003\u0015\t!!.\u0003\t}##g\u000e\u0005\t\r\u0003\ny\u00021\u0001\u0007\u0018B\"a\u0011\u0014DO!\u0019!\u0019\n\"\u0006\u0007\u001cB!\u0011q\u0016DO\t11yJ\"&\u0002\u0002\u0003\u0005)\u0011AA[\u0005\u0011yFE\r\u001d\t\u0011\u0019-\u0014q\u0004a\u0001\rG\u0003b!!#\u0007p\u0019\u0015\u0006\u0007\u0002DT\rW\u0003b!!!\u0005x\u0019%\u0006\u0003BAX\rW#AB\",\u0007\"\u0006\u0005\t\u0011!B\u0001\u0003k\u0013Aa\u0018\u00133s\u0005I\u0011M\u001d:bsRK\b/Z\u000b\u0005\rg3Y\f\u0006\u0003\u00076\u001au\u0006CBAA\to29\f\u0005\u0004\u0002\n\u0006Mh\u0011\u0018\t\u0005\u0003_3Y\f\u0002\u0005\u0005 \u0005\u0005#\u0019AA[\u0011!1y,!\u0011A\u0002\u0019\u0005\u0017aA1sOB\"a1\u0019Dd!\u0019\t\t\tb\u001e\u0007FB!\u0011q\u0016Dd\t11IM\"0\u0002\u0002\u0003\u0005)\u0011AA[\u0005\u0011yFe\r\u001b\u0003)\u0005\u00137\u000f\u001e:bGR$\u0016\u0010]3NC:Lg-Z:u+\u00111yM\"6\u0014\r\u0005\r\u0013q\u0011Di!\u0019\t\t\tb\u001e\u0007TB!\u0011q\u0016Dk\t!!y\"a\u0011C\u0002\u0005U\u0006\u0007\u0002Dm\r;\u0004b!!!\u0005x\u0019m\u0007\u0003BAX\r;$ABb8\u0002F\u0005\u0005\t\u0011!B\u0001\u0003k\u0013Aa\u0018\u00134k\u0005!a.Y7f\u0003))\b\u000f]3s\u0005>,h\u000e\u001a\u0019\u0005\rO4Y\u000f\u0005\u0004\u0005\u0014\u0012Ua\u0011\u001e\t\u0005\u0003_3Y\u000f\u0002\u0007\u0007n\u0006%\u0013\u0011!A\u0001\u0006\u0003\t)L\u0001\u0003`IM2\u0004C\u0002Dy\rg490\u0004\u0002\u0003\u0016%!aQ\u001fB\u000b\u0005\r\u0019V-\u001d\u0019\u0005\rs4i\u0010\u0005\u0004\u0002\u0002\u0012]d1 \t\u0005\u0003_3i\u0010\u0002\u0007\u0007\u0000\u0006-\u0013\u0011!A\u0001\u0006\u0003\t)L\u0001\u0003`IM:DCCD\u0002\u000f\u000b9ya\"\u0005\b\u001cA1\u00111[A\"\r'D\u0001\u0002b \u0002N\u0001\u0007qq\u0001\u0019\u0005\u000f\u00139i\u0001\u0005\u0004\u0002\u0002\u0012]t1\u0002\t\u0005\u0003_;i\u0001\u0002\u0007\u0007`\u001e\u0015\u0011\u0011!A\u0001\u0006\u0003\t)\f\u0003\u0005\u0007b\u00065\u0003\u0019\u0001Cz\u0011!1\u0019/!\u0014A\u0002\u001dM\u0001\u0007BD\u000b\u000f3\u0001b\u0001b%\u0005\u0016\u001d]\u0001\u0003BAX\u000f3!AB\"<\b\u0012\u0005\u0005\t\u0011!B\u0001\u0003kC\u0001Bb\u001b\u0002N\u0001\u0007qQ\u0004\t\u0007\rc4\u0019pb\b1\t\u001d\u0005rQ\u0005\t\u0007\u0003\u0003#9hb\t\u0011\t\u0005=vQ\u0005\u0003\r\r\u007f<Y\"!A\u0001\u0002\u000b\u0005\u0011QW\u000b\u0003\u000fS\u0001Dab\u000b\b0A1\u00111\\As\u000f[\u0001B!a,\b0\u0011aaQ^A(\u0003\u0003\u0005\tQ!\u0001\u00026V\u0011q1\u0007\t\u0007\u000fk9YDb>\u000e\u0005\u001d]\"\u0002BD\u001d\u0005+\t\u0011\"[7nkR\f'\r\\3\n\t\u0005\u0005vq\u0007\u0015\t\u0003\u0007\u0012iDa\u0011\u0003F\u0005a\u0011MY:ue\u0006\u001cG\u000fV=qKV!q1ID%))9)eb\u0013\bX\u001desQ\r\t\u0007\u0003\u0003#9hb\u0012\u0011\t\u0005=v\u0011\n\u0003\t\t?\t9F1\u0001\u00026\"AAqPA,\u0001\u00049i\u0005\r\u0003\bP\u001dM\u0003CBAA\to:\t\u0006\u0005\u0003\u00020\u001eMC\u0001DD+\u000f\u0017\n\t\u0011!A\u0003\u0002\u0005U&\u0001B0%gaB\u0001B\"9\u0002X\u0001\u0007A1\u001f\u0005\t\rG\f9\u00061\u0001\b\\A\"qQLD1!\u0019!\u0019\n\"\u0006\b`A!\u0011qVD1\t19\u0019g\"\u0017\u0002\u0002\u0003\u0005)\u0011AA[\u0005\u0011yFeM\u001d\t\u0011\u0019-\u0014q\u000ba\u0001\u000fO\u0002b!!#\u0007p\u001d%\u0004\u0007BD6\u000f_\u0002b!!!\u0005x\u001d5\u0004\u0003BAX\u000f_\"Ab\"\u001d\bf\u0005\u0005\t\u0011!B\u0001\u0003k\u0013Aa\u0018\u00135a\t\u0001r+\u001b7eG\u0006\u0014H-T1oS\u001a,7\u000f^\u000b\u0005\u000fo:ih\u0005\u0004\u0002Z\u0005\u001du\u0011\u0010\t\u0007\u0003\u0003#9hb\u001f\u0011\t\u0005=vQ\u0010\u0003\t\t?\tIF1\u0001\u00026\u0006QAn\\<fe\n{WO\u001c31\t\u001d\ruq\u0011\t\u0007\u0003\u0003#9h\"\"\u0011\t\u0005=vq\u0011\u0003\r\u000f\u0013\u000bY&!A\u0001\u0002\u000b\u0005\u0011Q\u0017\u0002\u0005?\u0012\"\u0014\u0007\r\u0003\b\u000e\u001eE\u0005CBAA\to:y\t\u0005\u0003\u00020\u001eEE\u0001DDJ\u0003;\n\t\u0011!A\u0003\u0002\u0005U&\u0001B0%iI\"bab&\b\u001a\u001e\r\u0006CBAj\u00033:Y\b\u0003\u0005\b\u0000\u0005}\u0003\u0019ADNa\u00119ij\")\u0011\r\u0005\u0005EqODP!\u0011\tyk\")\u0005\u0019\u001d%u\u0011TA\u0001\u0002\u0003\u0015\t!!.\t\u0011\u0019\r\u0018q\fa\u0001\u000fK\u0003Dab*\b,B1\u0011\u0011\u0011C<\u000fS\u0003B!a,\b,\u0012aq1SDR\u0003\u0003\u0005\tQ!\u0001\u00026V\u0011qq\u0016\u0019\u0005\u000fc;)\f\u0005\u0004\u0002\\\u0006\u0015x1\u0017\t\u0005\u0003_;)\f\u0002\u0007\b8\u0006\u0005\u0014\u0011!A\u0001\u0006\u0003\t)LA\u0002`IEB\u0003\"!\u0017\u0003>\t\r#QI\u0001\ro&dGmY1sIRK\b/Z\u000b\u0005\u000f\u007f;)\r\u0006\u0004\bB\u001e\u001dw1\u001b\t\u0007\u0003\u0003#9hb1\u0011\t\u0005=vQ\u0019\u0003\t\t?\t)G1\u0001\u00026\"AqqPA3\u0001\u00049I\r\r\u0003\bL\u001e=\u0007CBAA\to:i\r\u0005\u0003\u00020\u001e=G\u0001DDi\u000f\u000f\f\t\u0011!A\u0003\u0002\u0005U&\u0001B0%iMB\u0001Bb9\u0002f\u0001\u0007qQ\u001b\u0019\u0005\u000f/<Y\u000e\u0005\u0004\u0002\u0002\u0012]t\u0011\u001c\t\u0005\u0003_;Y\u000e\u0002\u0007\b^\u001eM\u0017\u0011!A\u0001\u0006\u0003\t)L\u0001\u0003`IQ\"$\u0001G%oi\u0016\u00148/Z2uS>tG+\u001f9f\u001b\u0006t\u0017NZ3tiV!q1]Du'\u0019\t9'a\"\bfB1\u0011\u0011\u0011C<\u000fO\u0004B!a,\bj\u0012AAqDA4\u0005\u0004\t),A\u0004qCJ,g\u000e^:\u0011\r\u0005%\u00151_Dxa\u00119\tp\">\u0011\r\u0005\u0005EqODz!\u0011\tyk\">\u0005\u0019\u001d]\u0018\u0011NA\u0001\u0002\u0003\u0015\t!!.\u0003\t}#C'\u000e\u000b\u0005\u000fw<i\u0010\u0005\u0004\u0002T\u0006\u001dtq\u001d\u0005\t\u000fW\fY\u00071\u0001\b\u0000B1\u0011\u0011RAz\u0011\u0003\u0001D\u0001c\u0001\t\bA1\u0011\u0011\u0011C<\u0011\u000b\u0001B!a,\t\b\u0011aqq_D\u007f\u0003\u0003\u0005\tQ!\u0001\u00026V\u0011\u00012\u0002\u0019\u0005\u0011\u001bA\t\u0002\u0005\u0004\u0002\\\u0006\u0015\br\u0002\t\u0005\u0003_C\t\u0002\u0002\u0007\b8\u00065\u0014\u0011!A\u0001\u0006\u0003\t)\f\u0006\u0002\u0005t\"B\u0011q\rB\u001f\u0005\u0007\u0012)%\u0001\tj]R,'o]3di&|g\u000eV=qKV!\u00012\u0004E\u0011)\u0011Ai\u0002c\t\u0011\r\u0005\u0005Eq\u000fE\u0010!\u0011\ty\u000b#\t\u0005\u0011\u0011}\u0011\u0011\u000fb\u0001\u0003kC\u0001bb;\u0002r\u0001\u0007\u0001R\u0005\t\u0007\u0003\u00133y\u0007c\n1\t!%\u0002R\u0006\t\u0007\u0003\u0003#9\bc\u000b\u0011\t\u0005=\u0006R\u0006\u0003\r\u0011_A\u0019#!A\u0001\u0002\u000b\u0005\u0011Q\u0017\u0002\u0005?\u0012\"d\u0007")
public final class ManifestFactory {
    public static <T> Manifest<T> intersectionType(scala.collection.immutable.Seq<Manifest<?>> parents) {
        return ManifestFactory$.MODULE$.intersectionType(parents);
    }

    public static <T> Manifest<T> wildcardType(Manifest<?> lowerBound, Manifest<?> upperBound) {
        return ManifestFactory$.MODULE$.wildcardType(lowerBound, upperBound);
    }

    public static <T> Manifest<T> abstractType(Manifest<?> prefix, String name, Class<?> upperBound, scala.collection.immutable.Seq<Manifest<?>> args2) {
        return ManifestFactory$.MODULE$.abstractType(prefix, name, upperBound, args2);
    }

    public static <T> Manifest<Object> arrayType(Manifest<?> arg) {
        return ManifestFactory$.MODULE$.arrayType(arg);
    }

    public static <T> Manifest<T> classType(Manifest<?> prefix, Class<?> clazz, scala.collection.immutable.Seq<Manifest<?>> args2) {
        return ManifestFactory$.MODULE$.classType(prefix, clazz, args2);
    }

    public static <T> Manifest<T> classType(Class<T> clazz, Manifest<?> arg1, scala.collection.immutable.Seq<Manifest<?>> args2) {
        return ManifestFactory$.MODULE$.classType(clazz, arg1, args2);
    }

    public static <T> Manifest<T> classType(Class<?> clazz) {
        return ManifestFactory$.MODULE$.classType(clazz);
    }

    public static <T> Manifest<T> singleType(Object value) {
        return ManifestFactory$.MODULE$.singleType(value);
    }

    public static Manifest<Nothing$> Nothing() {
        return ManifestFactory$.MODULE$.Nothing();
    }

    public static Manifest<Null$> Null() {
        return ManifestFactory$.MODULE$.Null();
    }

    public static Manifest<Object> AnyVal() {
        return ManifestFactory$.MODULE$.AnyVal();
    }

    public static Manifest<Object> AnyRef() {
        return ManifestFactory$.MODULE$.AnyRef();
    }

    public static Manifest<Object> Object() {
        return ManifestFactory$.MODULE$.Object();
    }

    public static Manifest<Object> Any() {
        return ManifestFactory$.MODULE$.Any();
    }

    public static UnitManifest Unit() {
        return ManifestFactory$.MODULE$.Unit();
    }

    public static BooleanManifest Boolean() {
        return ManifestFactory$.MODULE$.Boolean();
    }

    public static DoubleManifest Double() {
        return ManifestFactory$.MODULE$.Double();
    }

    public static FloatManifest Float() {
        return ManifestFactory$.MODULE$.Float();
    }

    public static LongManifest Long() {
        return ManifestFactory$.MODULE$.Long();
    }

    public static IntManifest Int() {
        return ManifestFactory$.MODULE$.Int();
    }

    public static CharManifest Char() {
        return ManifestFactory$.MODULE$.Char();
    }

    public static ShortManifest Short() {
        return ManifestFactory$.MODULE$.Short();
    }

    public static ByteManifest Byte() {
        return ManifestFactory$.MODULE$.Byte();
    }

    public static List<AnyValManifest<?>> valueManifests() {
        return ManifestFactory$.MODULE$.valueManifests();
    }

    private static class AbstractTypeManifest<T>
    implements Manifest<T> {
        private static final long serialVersionUID = 1L;
        private final Manifest<?> prefix;
        private final String name;
        private final Class<?> upperBound;
        private final List<Manifest<?>> typeArguments;

        @Override
        public Manifest<Object> arrayManifest() {
            return Manifest.arrayManifest$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Manifest.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object that) {
            return Manifest.equals$(this, that);
        }

        @Override
        public int hashCode() {
            return Manifest.hashCode$(this);
        }

        @Override
        public ClassTag<Object> wrap() {
            return ClassTag.wrap$(this);
        }

        @Override
        public Object newArray(int len) {
            return ClassTag.newArray$(this, len);
        }

        @Override
        public Option<T> unapply(Object x) {
            return ClassTag.unapply$(this, x);
        }

        @Override
        public Class<?> erasure() {
            return ClassManifestDeprecatedApis.erasure$(this);
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$less$colon$less$(this, that);
        }

        @Override
        public boolean $greater$colon$greater(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$greater$colon$greater$(this, that);
        }

        @Override
        public <A> Class<Object> arrayClass(Class<?> tp) {
            return ClassManifestDeprecatedApis.arrayClass$(this, tp);
        }

        @Override
        public Object[] newArray2(int len) {
            return ClassManifestDeprecatedApis.newArray2$(this, len);
        }

        @Override
        public Object[][] newArray3(int len) {
            return ClassManifestDeprecatedApis.newArray3$(this, len);
        }

        @Override
        public Object[][][] newArray4(int len) {
            return ClassManifestDeprecatedApis.newArray4$(this, len);
        }

        @Override
        public Object[][][][] newArray5(int len) {
            return ClassManifestDeprecatedApis.newArray5$(this, len);
        }

        @Override
        public ArraySeq<T> newWrappedArray(int len) {
            return ClassManifestDeprecatedApis.newWrappedArray$(this, len);
        }

        @Override
        public ArrayBuilder<T> newArrayBuilder() {
            return ClassManifestDeprecatedApis.newArrayBuilder$(this);
        }

        @Override
        public String argString() {
            return ClassManifestDeprecatedApis.argString$(this);
        }

        @Override
        public Class<?> runtimeClass() {
            return this.upperBound;
        }

        @Override
        public List<Manifest<?>> typeArguments() {
            return this.typeArguments;
        }

        @Override
        public String toString() {
            return new StringBuilder(1).append(this.prefix.toString()).append("#").append(this.name).append(this.argString()).toString();
        }

        public AbstractTypeManifest(Manifest<?> prefix, String name, Class<?> upperBound, Seq<Manifest<?>> args2) {
            this.prefix = prefix;
            this.name = name;
            this.upperBound = upperBound;
            this.typeArguments = args2.toList();
        }
    }

    private static final class AnyManifest
    extends PhantomManifest<Object> {
        private static final long serialVersionUID = 1L;

        public Object[] newArray(int len) {
            return new Object[len];
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return that == this;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Any();
        }

        public AnyManifest() {
            super(ManifestFactory$.scala$reflect$ManifestFactory$$ObjectTYPE, "Any");
        }
    }

    private static final class AnyValPhantomManifest
    extends PhantomManifest<Object> {
        private static final long serialVersionUID = 1L;

        public Object[] newArray(int len) {
            return new Object[len];
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return that == this || that == ManifestFactory$.MODULE$.Any();
        }

        private Object readResolve() {
            return Manifest$.MODULE$.AnyVal();
        }

        public AnyValPhantomManifest() {
            super(ManifestFactory$.scala$reflect$ManifestFactory$$ObjectTYPE, "AnyVal");
        }
    }

    public static final class BooleanManifest
    extends AnyValManifest<Object> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Boolean> runtimeClass() {
            return Boolean.TYPE;
        }

        public boolean[] newArray(int len) {
            return new boolean[len];
        }

        @Override
        public ArraySeq<Object> newWrappedArray(int len) {
            return new ArraySeq.ofBoolean(new boolean[len]);
        }

        @Override
        public ArrayBuilder<Object> newArrayBuilder() {
            return new ArrayBuilder.ofBoolean();
        }

        @Override
        public Option<Object> unapply(Object x) {
            if (x instanceof Boolean) {
                boolean bl = BoxesRunTime.unboxToBoolean(x);
                return new Some<Object>(bl);
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Boolean();
        }

        public BooleanManifest() {
            super("Boolean");
        }
    }

    public static final class ByteManifest
    extends AnyValManifest<Object> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Byte> runtimeClass() {
            return Byte.TYPE;
        }

        public byte[] newArray(int len) {
            return new byte[len];
        }

        @Override
        public ArraySeq<Object> newWrappedArray(int len) {
            return new ArraySeq.ofByte(new byte[len]);
        }

        @Override
        public ArrayBuilder<Object> newArrayBuilder() {
            return new ArrayBuilder.ofByte();
        }

        @Override
        public Option<Object> unapply(Object x) {
            if (x instanceof Byte) {
                byte by = BoxesRunTime.unboxToByte(x);
                return new Some<Object>(by);
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Byte();
        }

        public ByteManifest() {
            super("Byte");
        }
    }

    public static final class CharManifest
    extends AnyValManifest<Object> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Character> runtimeClass() {
            return Character.TYPE;
        }

        public char[] newArray(int len) {
            return new char[len];
        }

        @Override
        public ArraySeq<Object> newWrappedArray(int len) {
            return new ArraySeq.ofChar(new char[len]);
        }

        @Override
        public ArrayBuilder<Object> newArrayBuilder() {
            return new ArrayBuilder.ofChar();
        }

        @Override
        public Option<Object> unapply(Object x) {
            if (x instanceof Character) {
                char c = BoxesRunTime.unboxToChar(x);
                return new Some<Object>(Character.valueOf(c));
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Char();
        }

        public CharManifest() {
            super("Char");
        }
    }

    private static class ClassTypeManifest<T>
    implements Manifest<T> {
        private static final long serialVersionUID = 1L;
        private final Option<Manifest<?>> prefix;
        private final Class<?> runtimeClass;
        private final List<Manifest<?>> typeArguments;

        @Override
        public Manifest<Object> arrayManifest() {
            return Manifest.arrayManifest$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Manifest.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object that) {
            return Manifest.equals$(this, that);
        }

        @Override
        public int hashCode() {
            return Manifest.hashCode$(this);
        }

        @Override
        public ClassTag<Object> wrap() {
            return ClassTag.wrap$(this);
        }

        @Override
        public Object newArray(int len) {
            return ClassTag.newArray$(this, len);
        }

        @Override
        public Option<T> unapply(Object x) {
            return ClassTag.unapply$(this, x);
        }

        @Override
        public Class<?> erasure() {
            return ClassManifestDeprecatedApis.erasure$(this);
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$less$colon$less$(this, that);
        }

        @Override
        public boolean $greater$colon$greater(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$greater$colon$greater$(this, that);
        }

        @Override
        public <A> Class<Object> arrayClass(Class<?> tp) {
            return ClassManifestDeprecatedApis.arrayClass$(this, tp);
        }

        @Override
        public Object[] newArray2(int len) {
            return ClassManifestDeprecatedApis.newArray2$(this, len);
        }

        @Override
        public Object[][] newArray3(int len) {
            return ClassManifestDeprecatedApis.newArray3$(this, len);
        }

        @Override
        public Object[][][] newArray4(int len) {
            return ClassManifestDeprecatedApis.newArray4$(this, len);
        }

        @Override
        public Object[][][][] newArray5(int len) {
            return ClassManifestDeprecatedApis.newArray5$(this, len);
        }

        @Override
        public ArraySeq<T> newWrappedArray(int len) {
            return ClassManifestDeprecatedApis.newWrappedArray$(this, len);
        }

        @Override
        public ArrayBuilder<T> newArrayBuilder() {
            return ClassManifestDeprecatedApis.newArrayBuilder$(this);
        }

        @Override
        public String argString() {
            return ClassManifestDeprecatedApis.argString$(this);
        }

        @Override
        public Class<?> runtimeClass() {
            return this.runtimeClass;
        }

        @Override
        public List<Manifest<?>> typeArguments() {
            return this.typeArguments;
        }

        @Override
        public String toString() {
            return new StringBuilder(0).append(this.prefix.isEmpty() ? "" : new StringBuilder(1).append(((ClassTag)this.prefix.get()).toString()).append("#").toString()).append((Object)(this.runtimeClass().isArray() ? "Array" : this.runtimeClass().getName())).append(this.argString()).toString();
        }

        public ClassTypeManifest(Option<Manifest<?>> prefix, Class<?> runtimeClass, List<Manifest<?>> typeArguments) {
            this.prefix = prefix;
            this.runtimeClass = runtimeClass;
            this.typeArguments = typeArguments;
        }
    }

    public static final class DoubleManifest
    extends AnyValManifest<Object> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Double> runtimeClass() {
            return Double.TYPE;
        }

        public double[] newArray(int len) {
            return new double[len];
        }

        @Override
        public ArraySeq<Object> newWrappedArray(int len) {
            return new ArraySeq.ofDouble(new double[len]);
        }

        @Override
        public ArrayBuilder<Object> newArrayBuilder() {
            return new ArrayBuilder.ofDouble();
        }

        @Override
        public Option<Object> unapply(Object x) {
            if (x instanceof Double) {
                double d = BoxesRunTime.unboxToDouble(x);
                return new Some<Object>(d);
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Double();
        }

        public DoubleManifest() {
            super("Double");
        }
    }

    public static final class FloatManifest
    extends AnyValManifest<Object> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Float> runtimeClass() {
            return Float.TYPE;
        }

        public float[] newArray(int len) {
            return new float[len];
        }

        @Override
        public ArraySeq<Object> newWrappedArray(int len) {
            return new ArraySeq.ofFloat(new float[len]);
        }

        @Override
        public ArrayBuilder<Object> newArrayBuilder() {
            return new ArrayBuilder.ofFloat();
        }

        @Override
        public Option<Object> unapply(Object x) {
            if (x instanceof Float) {
                float f = BoxesRunTime.unboxToFloat(x);
                return new Some<Object>(Float.valueOf(f));
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Float();
        }

        public FloatManifest() {
            super("Float");
        }
    }

    public static final class IntManifest
    extends AnyValManifest<Object> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Integer> runtimeClass() {
            return Integer.TYPE;
        }

        public int[] newArray(int len) {
            return new int[len];
        }

        @Override
        public ArraySeq<Object> newWrappedArray(int len) {
            return new ArraySeq.ofInt(new int[len]);
        }

        @Override
        public ArrayBuilder<Object> newArrayBuilder() {
            return new ArrayBuilder.ofInt();
        }

        @Override
        public Option<Object> unapply(Object x) {
            if (x instanceof Integer) {
                int n = BoxesRunTime.unboxToInt(x);
                return new Some<Object>(n);
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Int();
        }

        public IntManifest() {
            super("Int");
        }
    }

    private static class IntersectionTypeManifest<T>
    implements Manifest<T> {
        private static final long serialVersionUID = 1L;
        private final Manifest<?>[] parents;

        @Override
        public List<Manifest<?>> typeArguments() {
            return Manifest.typeArguments$(this);
        }

        @Override
        public Manifest<Object> arrayManifest() {
            return Manifest.arrayManifest$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Manifest.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object that) {
            return Manifest.equals$(this, that);
        }

        @Override
        public int hashCode() {
            return Manifest.hashCode$(this);
        }

        @Override
        public ClassTag<Object> wrap() {
            return ClassTag.wrap$(this);
        }

        @Override
        public Object newArray(int len) {
            return ClassTag.newArray$(this, len);
        }

        @Override
        public Option<T> unapply(Object x) {
            return ClassTag.unapply$(this, x);
        }

        @Override
        public Class<?> erasure() {
            return ClassManifestDeprecatedApis.erasure$(this);
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$less$colon$less$(this, that);
        }

        @Override
        public boolean $greater$colon$greater(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$greater$colon$greater$(this, that);
        }

        @Override
        public <A> Class<Object> arrayClass(Class<?> tp) {
            return ClassManifestDeprecatedApis.arrayClass$(this, tp);
        }

        @Override
        public Object[] newArray2(int len) {
            return ClassManifestDeprecatedApis.newArray2$(this, len);
        }

        @Override
        public Object[][] newArray3(int len) {
            return ClassManifestDeprecatedApis.newArray3$(this, len);
        }

        @Override
        public Object[][][] newArray4(int len) {
            return ClassManifestDeprecatedApis.newArray4$(this, len);
        }

        @Override
        public Object[][][][] newArray5(int len) {
            return ClassManifestDeprecatedApis.newArray5$(this, len);
        }

        @Override
        public ArraySeq<T> newWrappedArray(int len) {
            return ClassManifestDeprecatedApis.newWrappedArray$(this, len);
        }

        @Override
        public ArrayBuilder<T> newArrayBuilder() {
            return ClassManifestDeprecatedApis.newArrayBuilder$(this);
        }

        @Override
        public String argString() {
            return ClassManifestDeprecatedApis.argString$(this);
        }

        @Override
        public Class<?> runtimeClass() {
            return this.parents[0].runtimeClass();
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public String toString() {
            void mkString_end;
            void mkString_sep;
            ArraySeq.ofRef<Manifest<?>> ofRef2 = Predef$.MODULE$.wrapRefArray(this.parents);
            String string = " with ";
            if (ofRef2 == null) {
                throw null;
            }
            ArraySeq.ofRef<Manifest<?>> mkString_this = ofRef2;
            String string2 = "";
            String mkString_start = "";
            return IterableOnceOps.mkString$(mkString_this, mkString_start, (String)mkString_sep, (String)mkString_end);
        }

        public IntersectionTypeManifest(Manifest<?>[] parents) {
            this.parents = parents;
        }
    }

    public static final class LongManifest
    extends AnyValManifest<Object> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Long> runtimeClass() {
            return Long.TYPE;
        }

        public long[] newArray(int len) {
            return new long[len];
        }

        @Override
        public ArraySeq<Object> newWrappedArray(int len) {
            return new ArraySeq.ofLong(new long[len]);
        }

        @Override
        public ArrayBuilder<Object> newArrayBuilder() {
            return new ArrayBuilder.ofLong();
        }

        @Override
        public Option<Object> unapply(Object x) {
            if (x instanceof Long) {
                long l = BoxesRunTime.unboxToLong(x);
                return new Some<Object>(l);
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Long();
        }

        public LongManifest() {
            super("Long");
        }
    }

    private static final class NothingManifest
    extends PhantomManifest<Nothing$> {
        private static final long serialVersionUID = 1L;

        public Object[] newArray(int len) {
            return new Object[len];
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return that != null;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Nothing();
        }

        public NothingManifest() {
            super(ManifestFactory$.scala$reflect$ManifestFactory$$NothingTYPE, "Nothing");
        }
    }

    private static final class NullManifest
    extends PhantomManifest<Null$> {
        private static final long serialVersionUID = 1L;

        public Object[] newArray(int len) {
            return new Object[len];
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return that != null && that != ManifestFactory$.MODULE$.Nothing() && !that.$less$colon$less(ManifestFactory$.MODULE$.AnyVal());
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Null();
        }

        public NullManifest() {
            super(ManifestFactory$.scala$reflect$ManifestFactory$$NullTYPE, "Null");
        }
    }

    private static final class ObjectManifest
    extends PhantomManifest<Object> {
        private static final long serialVersionUID = 1L;

        public Object[] newArray(int len) {
            return new Object[len];
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return that == this || that == ManifestFactory$.MODULE$.Any();
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Object();
        }

        public ObjectManifest() {
            super(ManifestFactory$.scala$reflect$ManifestFactory$$ObjectTYPE, "Object");
        }
    }

    private static abstract class PhantomManifest<T>
    extends ClassTypeManifest<T> {
        private static final long serialVersionUID = 1L;
        private final String toString;
        private final transient int hashCode;

        @Override
        public String toString() {
            return this.toString;
        }

        @Override
        public boolean equals(Object that) {
            return this == that;
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        public PhantomManifest(Class<?> _runtimeClass, String toString2) {
            this.toString = toString2;
            super(None$.MODULE$, _runtimeClass, Nil$.MODULE$);
            this.hashCode = System.identityHashCode(this);
        }
    }

    public static final class ShortManifest
    extends AnyValManifest<Object> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Short> runtimeClass() {
            return Short.TYPE;
        }

        public short[] newArray(int len) {
            return new short[len];
        }

        @Override
        public ArraySeq<Object> newWrappedArray(int len) {
            return new ArraySeq.ofShort(new short[len]);
        }

        @Override
        public ArrayBuilder<Object> newArrayBuilder() {
            return new ArrayBuilder.ofShort();
        }

        @Override
        public Option<Object> unapply(Object x) {
            if (x instanceof Short) {
                short s2 = BoxesRunTime.unboxToShort(x);
                return new Some<Object>(s2);
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Short();
        }

        public ShortManifest() {
            super("Short");
        }
    }

    private static final class SingletonTypeManifest<T>
    implements Manifest<T> {
        private static final long serialVersionUID = 1L;
        private Class<?> runtimeClass;
        private String toString;
        private final Object value;
        private volatile byte bitmap$0;

        @Override
        public List<Manifest<?>> typeArguments() {
            return Manifest.typeArguments$(this);
        }

        @Override
        public Manifest<T[]> arrayManifest() {
            return Manifest.arrayManifest$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Manifest.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object that) {
            return Manifest.equals$(this, that);
        }

        @Override
        public int hashCode() {
            return Manifest.hashCode$(this);
        }

        @Override
        public ClassTag<T[]> wrap() {
            return ClassTag.wrap$(this);
        }

        @Override
        public Object newArray(int len) {
            return ClassTag.newArray$(this, len);
        }

        @Override
        public Option<T> unapply(Object x) {
            return ClassTag.unapply$(this, x);
        }

        @Override
        public Class<?> erasure() {
            return ClassManifestDeprecatedApis.erasure$(this);
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$less$colon$less$(this, that);
        }

        @Override
        public boolean $greater$colon$greater(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$greater$colon$greater$(this, that);
        }

        @Override
        public <A> Class<Object> arrayClass(Class<?> tp) {
            return ClassManifestDeprecatedApis.arrayClass$(this, tp);
        }

        @Override
        public Object[] newArray2(int len) {
            return ClassManifestDeprecatedApis.newArray2$(this, len);
        }

        @Override
        public Object[][] newArray3(int len) {
            return ClassManifestDeprecatedApis.newArray3$(this, len);
        }

        @Override
        public Object[][][] newArray4(int len) {
            return ClassManifestDeprecatedApis.newArray4$(this, len);
        }

        @Override
        public Object[][][][] newArray5(int len) {
            return ClassManifestDeprecatedApis.newArray5$(this, len);
        }

        @Override
        public ArraySeq<T> newWrappedArray(int len) {
            return ClassManifestDeprecatedApis.newWrappedArray$(this, len);
        }

        @Override
        public ArrayBuilder<T> newArrayBuilder() {
            return ClassManifestDeprecatedApis.newArrayBuilder$(this);
        }

        @Override
        public String argString() {
            return ClassManifestDeprecatedApis.argString$(this);
        }

        private Class<?> runtimeClass$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 1) == 0) {
                    this.runtimeClass = this.value.getClass();
                    this.bitmap$0 = (byte)(this.bitmap$0 | 1);
                }
            }
            return this.runtimeClass;
        }

        @Override
        public Class<?> runtimeClass() {
            if ((byte)(this.bitmap$0 & 1) == 0) {
                return this.runtimeClass$lzycompute();
            }
            return this.runtimeClass;
        }

        private String toString$lzycompute() {
            synchronized (this) {
                if ((byte)(this.bitmap$0 & 2) == 0) {
                    this.toString = new StringBuilder(5).append(this.value.toString()).append(".type").toString();
                    this.bitmap$0 = (byte)(this.bitmap$0 | 2);
                }
            }
            return this.toString;
        }

        @Override
        public String toString() {
            if ((byte)(this.bitmap$0 & 2) == 0) {
                return this.toString$lzycompute();
            }
            return this.toString;
        }

        public SingletonTypeManifest(Object value) {
            this.value = value;
        }
    }

    public static final class UnitManifest
    extends AnyValManifest<BoxedUnit> {
        private static final long serialVersionUID = 1L;

        @Override
        public Class<Void> runtimeClass() {
            return Void.TYPE;
        }

        public BoxedUnit[] newArray(int len) {
            return new BoxedUnit[len];
        }

        @Override
        public ArraySeq<BoxedUnit> newWrappedArray(int len) {
            return new ArraySeq.ofUnit(new BoxedUnit[len]);
        }

        @Override
        public ArrayBuilder<BoxedUnit> newArrayBuilder() {
            return new ArrayBuilder.ofUnit();
        }

        @Override
        public <T> Class<Object> arrayClass(Class<?> tp) {
            if (tp == Void.TYPE) {
                return BoxedUnit[].class;
            }
            return ClassManifestDeprecatedApis.arrayClass$(this, tp);
        }

        @Override
        public Option<BoxedUnit> unapply(Object x) {
            if (x instanceof BoxedUnit) {
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return new Some<BoxedUnit>(boxedUnit);
            }
            return None$.MODULE$;
        }

        private Object readResolve() {
            return Manifest$.MODULE$.Unit();
        }

        public UnitManifest() {
            super("Unit");
        }
    }

    private static class WildcardManifest<T>
    implements Manifest<T> {
        private static final long serialVersionUID = 1L;
        private final Manifest<?> lowerBound;
        private final Manifest<?> upperBound;

        @Override
        public List<Manifest<?>> typeArguments() {
            return Manifest.typeArguments$(this);
        }

        @Override
        public Manifest<Object> arrayManifest() {
            return Manifest.arrayManifest$(this);
        }

        @Override
        public boolean canEqual(Object that) {
            return Manifest.canEqual$(this, that);
        }

        @Override
        public boolean equals(Object that) {
            return Manifest.equals$(this, that);
        }

        @Override
        public int hashCode() {
            return Manifest.hashCode$(this);
        }

        @Override
        public ClassTag<Object> wrap() {
            return ClassTag.wrap$(this);
        }

        @Override
        public Object newArray(int len) {
            return ClassTag.newArray$(this, len);
        }

        @Override
        public Option<T> unapply(Object x) {
            return ClassTag.unapply$(this, x);
        }

        @Override
        public Class<?> erasure() {
            return ClassManifestDeprecatedApis.erasure$(this);
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$less$colon$less$(this, that);
        }

        @Override
        public boolean $greater$colon$greater(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$greater$colon$greater$(this, that);
        }

        @Override
        public <A> Class<Object> arrayClass(Class<?> tp) {
            return ClassManifestDeprecatedApis.arrayClass$(this, tp);
        }

        @Override
        public Object[] newArray2(int len) {
            return ClassManifestDeprecatedApis.newArray2$(this, len);
        }

        @Override
        public Object[][] newArray3(int len) {
            return ClassManifestDeprecatedApis.newArray3$(this, len);
        }

        @Override
        public Object[][][] newArray4(int len) {
            return ClassManifestDeprecatedApis.newArray4$(this, len);
        }

        @Override
        public Object[][][][] newArray5(int len) {
            return ClassManifestDeprecatedApis.newArray5$(this, len);
        }

        @Override
        public ArraySeq<T> newWrappedArray(int len) {
            return ClassManifestDeprecatedApis.newWrappedArray$(this, len);
        }

        @Override
        public ArrayBuilder<T> newArrayBuilder() {
            return ClassManifestDeprecatedApis.newArrayBuilder$(this);
        }

        @Override
        public String argString() {
            return ClassManifestDeprecatedApis.argString$(this);
        }

        @Override
        public Class<?> runtimeClass() {
            return this.upperBound.runtimeClass();
        }

        @Override
        public String toString() {
            return new StringBuilder(1).append("_").append((Object)(this.lowerBound == ManifestFactory$.MODULE$.Nothing() ? "" : new StringBuilder(4).append(" >: ").append(this.lowerBound).toString())).append((Object)(this.upperBound == ManifestFactory$.MODULE$.Nothing() ? "" : new StringBuilder(4).append(" <: ").append(this.upperBound).toString())).toString();
        }

        public WildcardManifest(Manifest<?> lowerBound, Manifest<?> upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }
    }
}

