/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple15$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0015]x!\u0002\"D\u0011\u0003Qe!\u0002'D\u0011\u0003i\u0005\"\u0002/\u0002\t\u0003i\u0006\"\u00020\u0002\t\u0003y\u0006\"CC\u001e\u0003\u0005\u0005I\u0011QC\u001f\u0011%)i*AA\u0001\n\u0003+y\nC\u0005\u0006n\u0006\t\t\u0011\"\u0003\u0006p\u001a!Aj\u0011\"c\u0011!\u0011xA!f\u0001\n\u0003\u0019\b\u0002C@\b\u0005#\u0005\u000b\u0011\u0002;\t\u0015\u0005\u0005qA!f\u0001\n\u0003\t\u0019\u0001\u0003\u0006\u0002\f\u001d\u0011\t\u0012)A\u0005\u0003\u000bA!\"!\u0004\b\u0005+\u0007I\u0011AA\b\u0011)\t9b\u0002B\tB\u0003%\u0011\u0011\u0003\u0005\u000b\u000339!Q3A\u0005\u0002\u0005m\u0001BCA\u0012\u000f\tE\t\u0015!\u0003\u0002\u001e!Q\u0011QE\u0004\u0003\u0016\u0004%\t!a\n\t\u0015\u0005=rA!E!\u0002\u0013\tI\u0003\u0003\u0006\u00022\u001d\u0011)\u001a!C\u0001\u0003gA!\"a\u000f\b\u0005#\u0005\u000b\u0011BA\u001b\u0011)\tid\u0002BK\u0002\u0013\u0005\u0011q\b\u0005\u000b\u0003\u000f:!\u0011#Q\u0001\n\u0005\u0005\u0003BCA%\u000f\tU\r\u0011\"\u0001\u0002L!Q\u00111K\u0004\u0003\u0012\u0003\u0006I!!\u0014\t\u0015\u0005UsA!f\u0001\n\u0003\t9\u0006\u0003\u0006\u0002`\u001d\u0011\t\u0012)A\u0005\u00033B!\"!\u0019\b\u0005+\u0007I\u0011AA2\u0011)\tYg\u0002B\tB\u0003%\u0011Q\r\u0005\u000b\u0003[:!Q3A\u0005\u0002\u0005=\u0004BCA<\u000f\tE\t\u0015!\u0003\u0002r!Q\u0011\u0011P\u0004\u0003\u0016\u0004%\t!a\u001f\t\u0015\u0005\ruA!E!\u0002\u0013\ti\b\u0003\u0006\u0002\u0006\u001e\u0011)\u001a!C\u0001\u0003\u000fC!\"a$\b\u0005#\u0005\u000b\u0011BAE\u0011)\t\tj\u0002BK\u0002\u0013\u0005\u00111\u0013\u0005\u000b\u00037;!\u0011#Q\u0001\n\u0005U\u0005BCAO\u000f\tU\r\u0011\"\u0001\u0002 \"Q\u0011qU\u0004\u0003\u0012\u0003\u0006I!!)\t\rq;A\u0011AAU\u0011%\tYm\u0002b\u0001\n\u0003\ti\r\u0003\u0005\u0002T\u001e\u0001\u000b\u0011BAh\u0011%\t)nBA\u0001\n\u0003\t9\u000eC\u0005\u00038\u001d\t\n\u0011\"\u0001\u0003:!I!qN\u0004\u0012\u0002\u0013\u0005!\u0011\u000f\u0005\n\u0005+;\u0011\u0013!C\u0001\u0005/C\u0011Ba/\b#\u0003%\tA!0\t\u0013\t\u0005x!%A\u0005\u0002\t\r\b\"CB\u0004\u000fE\u0005I\u0011AB\u0005\u0011%\u0019icBI\u0001\n\u0003\u0019y\u0003C\u0005\u0004T\u001d\t\n\u0011\"\u0001\u0004V!I1\u0011P\u0004\u0012\u0002\u0013\u000511\u0010\u0005\n\u0007?;\u0011\u0013!C\u0001\u0007CC\u0011b!2\b#\u0003%\taa2\t\u0013\r-x!%A\u0005\u0002\r5\b\"\u0003C\t\u000fE\u0005I\u0011\u0001C\n\u0011%!9dBI\u0001\n\u0003!I\u0004C\u0005\u0005^\u001d\t\n\u0011\"\u0001\u0005`!IA1Q\u0004\u0002\u0002\u0013\u0005CQ\u0011\u0005\n\t';\u0011\u0011!C\u0001\t+C\u0011\u0002\"(\b\u0003\u0003%\t\u0001b(\t\u0013\u0011\u0015v!!A\u0005B\u0011\u001d\u0006\"\u0003C[\u000f\u0005\u0005I\u0011\u0001C\\\u0011%!\tmBA\u0001\n\u0003\"\u0019\rC\u0005\u0005H\u001e\t\t\u0011\"\u0011\u0005J\"IA1Z\u0004\u0002\u0002\u0013\u0005CQ\u001a\u0005\n\t\u001f<\u0011\u0011!C!\t#\fq\u0001V;qY\u0016\fTG\u0003\u0002E\u000b\u0006)A/\u001e9mK*\u0011aiR\u0001\u0005U\u0006\u0004\u0018NC\u0001I\u0003\u0011\t7n[1\u0004\u0001A\u00111*A\u0007\u0002\u0007\n9A+\u001e9mKF*4cA\u0001O)B\u0011qJU\u0007\u0002!*\t\u0011+A\u0003tG\u0006d\u0017-\u0003\u0002T!\n1\u0011I\\=SK\u001a\u0004\"!\u0016.\u000e\u0003YS!a\u0016-\u0002\u0005%|'\"A-\u0002\t)\fg/Y\u0005\u00037Z\u0013AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtD#\u0001&\u0002\r\r\u0014X-\u0019;f+}\u0001G1\u001dCt\tW$y\u000fb=\u0005x\u0012mHq`C\u0002\u000b\u000f)Y!b\u0004\u0006\u0014\u0015]Q1\u0004\u000b C\u0016uQqDC\u0011\u000bG))#b\n\u0006*\u0015-RQFC\u0018\u000bc)\u0019$\"\u000e\u00068\u0015e\u0002\u0003I&\b\tC$)\u000f\";\u0005n\u0012EHQ\u001fC}\t{,\t!\"\u0002\u0006\n\u00155Q\u0011CC\u000b\u000b3)bd\u0019<\u0002\b\u0005M\u0011qDA\u0016\u0003o\t\u0019%a\u0014\u0002\\\u0005\u001d\u00141OA@\u0003\u0017\u000b9*a)\u0014\t\u001dqEm\u001a\t\u0003\u001f\u0016L!A\u001a)\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001\u000e\u001d\b\u0003S:t!A[7\u000e\u0003-T!\u0001\\%\u0002\rq\u0012xn\u001c;?\u0013\u0005\t\u0016BA8Q\u0003\u001d\u0001\u0018mY6bO\u0016L!aW9\u000b\u0005=\u0004\u0016A\u0001;2+\u0005!\bCA;w\u0019\u0001!Qa^\u0004C\u0002a\u0014!\u0001V\u0019\u0012\u0005ed\bCA({\u0013\tY\bKA\u0004O_RD\u0017N\\4\u0011\u0005=k\u0018B\u0001@Q\u0005\r\te._\u0001\u0004iF\u0002\u0013A\u0001;3+\t\t)\u0001E\u0002v\u0003\u000f!a!!\u0003\b\u0005\u0004A(A\u0001+3\u0003\r!(\u0007I\u0001\u0003iN*\"!!\u0005\u0011\u0007U\f\u0019\u0002\u0002\u0004\u0002\u0016\u001d\u0011\r\u0001\u001f\u0002\u0003)N\n1\u0001^\u001a!\u0003\t!H'\u0006\u0002\u0002\u001eA\u0019Q/a\b\u0005\r\u0005\u0005rA1\u0001y\u0005\t!F'A\u0002ui\u0001\n!\u0001^\u001b\u0016\u0005\u0005%\u0002cA;\u0002,\u00111\u0011QF\u0004C\u0002a\u0014!\u0001V\u001b\u0002\u0007Q,\u0004%\u0001\u0002umU\u0011\u0011Q\u0007\t\u0004k\u0006]BABA\u001d\u000f\t\u0007\u0001P\u0001\u0002Um\u0005\u0019AO\u000e\u0011\u0002\u0005Q<TCAA!!\r)\u00181\t\u0003\u0007\u0003\u000b:!\u0019\u0001=\u0003\u0005Q;\u0014a\u0001;8A\u0005\u0011A\u000fO\u000b\u0003\u0003\u001b\u00022!^A(\t\u0019\t\tf\u0002b\u0001q\n\u0011A\u000bO\u0001\u0004ib\u0002\u0013A\u0001;:+\t\tI\u0006E\u0002v\u00037\"a!!\u0018\b\u0005\u0004A(A\u0001+:\u0003\r!\u0018\bI\u0001\u0004iF\u0002TCAA3!\r)\u0018q\r\u0003\u0007\u0003S:!\u0019\u0001=\u0003\u0007Q\u000b\u0004'\u0001\u0003ucA\u0002\u0013a\u0001;2cU\u0011\u0011\u0011\u000f\t\u0004k\u0006MDABA;\u000f\t\u0007\u0001PA\u0002UcE\nA\u0001^\u00192A\u0005\u0019A/\r\u001a\u0016\u0005\u0005u\u0004cA;\u0002\u0000\u00111\u0011\u0011Q\u0004C\u0002a\u00141\u0001V\u00193\u0003\u0011!\u0018G\r\u0011\u0002\u0007Q\f4'\u0006\u0002\u0002\nB\u0019Q/a#\u0005\r\u00055uA1\u0001y\u0005\r!\u0016gM\u0001\u0005iF\u001a\u0004%A\u0002ucQ*\"!!&\u0011\u0007U\f9\n\u0002\u0004\u0002\u001a\u001e\u0011\r\u0001\u001f\u0002\u0004)F\"\u0014\u0001\u0002;2i\u0001\n1\u0001^\u00196+\t\t\t\u000bE\u0002v\u0003G#a!!*\b\u0005\u0004A(a\u0001+2k\u0005!A/M\u001b!)\u0001\nY+!,\u00020\u0006E\u00161WA[\u0003o\u000bI,a/\u0002>\u0006}\u0016\u0011YAb\u0003\u000b\f9-!3\u0011?-;A/!\u0002\u0002\u0012\u0005u\u0011\u0011FA\u001b\u0003\u0003\ni%!\u0017\u0002f\u0005E\u0014QPAE\u0003+\u000b\t\u000bC\u0003sM\u0001\u0007A\u000fC\u0004\u0002\u0002\u0019\u0002\r!!\u0002\t\u000f\u00055a\u00051\u0001\u0002\u0012!9\u0011\u0011\u0004\u0014A\u0002\u0005u\u0001bBA\u0013M\u0001\u0007\u0011\u0011\u0006\u0005\b\u0003c1\u0003\u0019AA\u001b\u0011\u001d\tiD\na\u0001\u0003\u0003Bq!!\u0013'\u0001\u0004\ti\u0005C\u0004\u0002V\u0019\u0002\r!!\u0017\t\u000f\u0005\u0005d\u00051\u0001\u0002f!9\u0011Q\u000e\u0014A\u0002\u0005E\u0004bBA=M\u0001\u0007\u0011Q\u0010\u0005\b\u0003\u000b3\u0003\u0019AAE\u0011\u001d\t\tJ\na\u0001\u0003+Cq!!('\u0001\u0004\t\t+A\u0004u_N\u001b\u0017\r\\1\u0016\u0005\u0005=\u0007\u0003I(\u0002RR\f)!!\u0005\u0002\u001e\u0005%\u0012QGA!\u0003\u001b\nI&!\u001a\u0002r\u0005u\u0014\u0011RAK\u0003CK!\u0001\u0014)\u0002\u0011Q|7kY1mC\u0002\nAaY8qsV\u0001\u0013\u0011\\Ap\u0003G\f9/a;\u0002p\u0006M\u0018q_A~\u0003\u007f\u0014\u0019Aa\u0002\u0003\f\t=!1\u0003B\f)\u0001\nYN!\u0007\u0003\u001c\tu!q\u0004B\u0011\u0005G\u0011)Ca\n\u0003*\t-\"Q\u0006B\u0018\u0005c\u0011\u0019D!\u000e\u0011A-;\u0011Q\\Aq\u0003K\fI/!<\u0002r\u0006U\u0018\u0011`A\u007f\u0005\u0003\u0011)A!\u0003\u0003\u000e\tE!Q\u0003\t\u0004k\u0006}G!B<*\u0005\u0004A\bcA;\u0002d\u00121\u0011\u0011B\u0015C\u0002a\u00042!^At\t\u0019\t)\"\u000bb\u0001qB\u0019Q/a;\u0005\r\u0005\u0005\u0012F1\u0001y!\r)\u0018q\u001e\u0003\u0007\u0003[I#\u0019\u0001=\u0011\u0007U\f\u0019\u0010\u0002\u0004\u0002:%\u0012\r\u0001\u001f\t\u0004k\u0006]HABA#S\t\u0007\u0001\u0010E\u0002v\u0003w$a!!\u0015*\u0005\u0004A\bcA;\u0002\u0000\u00121\u0011QL\u0015C\u0002a\u00042!\u001eB\u0002\t\u0019\tI'\u000bb\u0001qB\u0019QOa\u0002\u0005\r\u0005U\u0014F1\u0001y!\r)(1\u0002\u0003\u0007\u0003\u0003K#\u0019\u0001=\u0011\u0007U\u0014y\u0001\u0002\u0004\u0002\u000e&\u0012\r\u0001\u001f\t\u0004k\nMAABAMS\t\u0007\u0001\u0010E\u0002v\u0005/!a!!**\u0005\u0004A\b\u0002\u0003:*!\u0003\u0005\r!!8\t\u0013\u0005\u0005\u0011\u0006%AA\u0002\u0005\u0005\b\"CA\u0007SA\u0005\t\u0019AAs\u0011%\tI\"\u000bI\u0001\u0002\u0004\tI\u000fC\u0005\u0002&%\u0002\n\u00111\u0001\u0002n\"I\u0011\u0011G\u0015\u0011\u0002\u0003\u0007\u0011\u0011\u001f\u0005\n\u0003{I\u0003\u0013!a\u0001\u0003kD\u0011\"!\u0013*!\u0003\u0005\r!!?\t\u0013\u0005U\u0013\u0006%AA\u0002\u0005u\b\"CA1SA\u0005\t\u0019\u0001B\u0001\u0011%\ti'\u000bI\u0001\u0002\u0004\u0011)\u0001C\u0005\u0002z%\u0002\n\u00111\u0001\u0003\n!I\u0011QQ\u0015\u0011\u0002\u0003\u0007!Q\u0002\u0005\n\u0003#K\u0003\u0013!a\u0001\u0005#A\u0011\"!(*!\u0003\u0005\rA!\u0006\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0001#1\bB)\u0005'\u0012)Fa\u0016\u0003Z\tm#Q\fB0\u0005C\u0012\u0019G!\u001a\u0003h\t%$1\u000eB7+\t\u0011iDK\u0002u\u0005\u007fY#A!\u0011\u0011\t\t\r#QJ\u0007\u0003\u0005\u000bRAAa\u0012\u0003J\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0005\u0017\u0002\u0016AC1o]>$\u0018\r^5p]&!!q\nB#\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006o*\u0012\r\u0001\u001f\u0003\u0007\u0003\u0013Q#\u0019\u0001=\u0005\r\u0005U!F1\u0001y\t\u0019\t\tC\u000bb\u0001q\u00121\u0011Q\u0006\u0016C\u0002a$a!!\u000f+\u0005\u0004AHABA#U\t\u0007\u0001\u0010\u0002\u0004\u0002R)\u0012\r\u0001\u001f\u0003\u0007\u0003;R#\u0019\u0001=\u0005\r\u0005%$F1\u0001y\t\u0019\t)H\u000bb\u0001q\u00121\u0011\u0011\u0011\u0016C\u0002a$a!!$+\u0005\u0004AHABAMU\t\u0007\u0001\u0010\u0002\u0004\u0002&*\u0012\r\u0001_\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0001\u0012\u0019Ha\u001e\u0003z\tm$Q\u0010B@\u0005\u0003\u0013\u0019I!\"\u0003\b\n%%1\u0012BG\u0005\u001f\u0013\tJa%\u0016\u0005\tU$\u0006BA\u0003\u0005\u007f!Qa^\u0016C\u0002a$a!!\u0003,\u0005\u0004AHABA\u000bW\t\u0007\u0001\u0010\u0002\u0004\u0002\"-\u0012\r\u0001\u001f\u0003\u0007\u0003[Y#\u0019\u0001=\u0005\r\u0005e2F1\u0001y\t\u0019\t)e\u000bb\u0001q\u00121\u0011\u0011K\u0016C\u0002a$a!!\u0018,\u0005\u0004AHABA5W\t\u0007\u0001\u0010\u0002\u0004\u0002v-\u0012\r\u0001\u001f\u0003\u0007\u0003\u0003[#\u0019\u0001=\u0005\r\u000555F1\u0001y\t\u0019\tIj\u000bb\u0001q\u00121\u0011QU\u0016C\u0002a\fabY8qs\u0012\"WMZ1vYR$3'\u0006\u0011\u0003\u001a\nu%q\u0014BQ\u0005G\u0013)Ka*\u0003*\n-&Q\u0016BX\u0005c\u0013\u0019L!.\u00038\neVC\u0001BNU\u0011\t\tBa\u0010\u0005\u000b]d#\u0019\u0001=\u0005\r\u0005%AF1\u0001y\t\u0019\t)\u0002\fb\u0001q\u00121\u0011\u0011\u0005\u0017C\u0002a$a!!\f-\u0005\u0004AHABA\u001dY\t\u0007\u0001\u0010\u0002\u0004\u0002F1\u0012\r\u0001\u001f\u0003\u0007\u0003#b#\u0019\u0001=\u0005\r\u0005uCF1\u0001y\t\u0019\tI\u0007\fb\u0001q\u00121\u0011Q\u000f\u0017C\u0002a$a!!!-\u0005\u0004AHABAGY\t\u0007\u0001\u0010\u0002\u0004\u0002\u001a2\u0012\r\u0001\u001f\u0003\u0007\u0003Kc#\u0019\u0001=\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0001#q\u0018Bb\u0005\u000b\u00149M!3\u0003L\n5'q\u001aBi\u0005'\u0014)Na6\u0003Z\nm'Q\u001cBp+\t\u0011\tM\u000b\u0003\u0002\u001e\t}B!B<.\u0005\u0004AHABA\u0005[\t\u0007\u0001\u0010\u0002\u0004\u0002\u00165\u0012\r\u0001\u001f\u0003\u0007\u0003Ci#\u0019\u0001=\u0005\r\u00055RF1\u0001y\t\u0019\tI$\fb\u0001q\u00121\u0011QI\u0017C\u0002a$a!!\u0015.\u0005\u0004AHABA/[\t\u0007\u0001\u0010\u0002\u0004\u0002j5\u0012\r\u0001\u001f\u0003\u0007\u0003kj#\u0019\u0001=\u0005\r\u0005\u0005UF1\u0001y\t\u0019\ti)\fb\u0001q\u00121\u0011\u0011T\u0017C\u0002a$a!!*.\u0005\u0004A\u0018AD2paf$C-\u001a4bk2$H%N\u000b!\u0005K\u0014IOa;\u0003n\n=(\u0011\u001fBz\u0005k\u00149P!?\u0003|\nu(q`B\u0001\u0007\u0007\u0019)!\u0006\u0002\u0003h*\"\u0011\u0011\u0006B \t\u00159hF1\u0001y\t\u0019\tIA\fb\u0001q\u00121\u0011Q\u0003\u0018C\u0002a$a!!\t/\u0005\u0004AHABA\u0017]\t\u0007\u0001\u0010\u0002\u0004\u0002:9\u0012\r\u0001\u001f\u0003\u0007\u0003\u000br#\u0019\u0001=\u0005\r\u0005EcF1\u0001y\t\u0019\tiF\fb\u0001q\u00121\u0011\u0011\u000e\u0018C\u0002a$a!!\u001e/\u0005\u0004AHABAA]\t\u0007\u0001\u0010\u0002\u0004\u0002\u000e:\u0012\r\u0001\u001f\u0003\u0007\u00033s#\u0019\u0001=\u0005\r\u0005\u0015fF1\u0001y\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIY*\u0002ea\u0003\u0004\u0010\rE11CB\u000b\u0007/\u0019Iba\u0007\u0004\u001e\r}1\u0011EB\u0012\u0007K\u00199c!\u000b\u0004,U\u00111Q\u0002\u0016\u0005\u0003k\u0011y\u0004B\u0003x_\t\u0007\u0001\u0010\u0002\u0004\u0002\n=\u0012\r\u0001\u001f\u0003\u0007\u0003+y#\u0019\u0001=\u0005\r\u0005\u0005rF1\u0001y\t\u0019\tic\fb\u0001q\u00121\u0011\u0011H\u0018C\u0002a$a!!\u00120\u0005\u0004AHABA)_\t\u0007\u0001\u0010\u0002\u0004\u0002^=\u0012\r\u0001\u001f\u0003\u0007\u0003Sz#\u0019\u0001=\u0005\r\u0005UtF1\u0001y\t\u0019\t\ti\fb\u0001q\u00121\u0011QR\u0018C\u0002a$a!!'0\u0005\u0004AHABAS_\t\u0007\u00010\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001c\u0016A\rE2QGB\u001c\u0007s\u0019Yd!\u0010\u0004@\r\u000531IB#\u0007\u000f\u001aIea\u0013\u0004N\r=3\u0011K\u000b\u0003\u0007gQC!!\u0011\u0003@\u0011)q\u000f\rb\u0001q\u00121\u0011\u0011\u0002\u0019C\u0002a$a!!\u00061\u0005\u0004AHABA\u0011a\t\u0007\u0001\u0010\u0002\u0004\u0002.A\u0012\r\u0001\u001f\u0003\u0007\u0003s\u0001$\u0019\u0001=\u0005\r\u0005\u0015\u0003G1\u0001y\t\u0019\t\t\u0006\rb\u0001q\u00121\u0011Q\f\u0019C\u0002a$a!!\u001b1\u0005\u0004AHABA;a\t\u0007\u0001\u0010\u0002\u0004\u0002\u0002B\u0012\r\u0001\u001f\u0003\u0007\u0003\u001b\u0003$\u0019\u0001=\u0005\r\u0005e\u0005G1\u0001y\t\u0019\t)\u000b\rb\u0001q\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012BT\u0003IB,\u00077\u001aifa\u0018\u0004b\r\r4QMB4\u0007S\u001aYg!\u001c\u0004p\rE41OB;\u0007o*\"a!\u0017+\t\u00055#q\b\u0003\u0006oF\u0012\r\u0001\u001f\u0003\u0007\u0003\u0013\t$\u0019\u0001=\u0005\r\u0005U\u0011G1\u0001y\t\u0019\t\t#\rb\u0001q\u00121\u0011QF\u0019C\u0002a$a!!\u000f2\u0005\u0004AHABA#c\t\u0007\u0001\u0010\u0002\u0004\u0002RE\u0012\r\u0001\u001f\u0003\u0007\u0003;\n$\u0019\u0001=\u0005\r\u0005%\u0014G1\u0001y\t\u0019\t)(\rb\u0001q\u00121\u0011\u0011Q\u0019C\u0002a$a!!$2\u0005\u0004AHABAMc\t\u0007\u0001\u0010\u0002\u0004\u0002&F\u0012\r\u0001_\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u0013:+\u0001\u001aih!!\u0004\u0004\u000e\u00155qQBE\u0007\u0017\u001biia$\u0004\u0012\u000eM5QSBL\u00073\u001bYj!(\u0016\u0005\r}$\u0006BA-\u0005\u007f!Qa\u001e\u001aC\u0002a$a!!\u00033\u0005\u0004AHABA\u000be\t\u0007\u0001\u0010\u0002\u0004\u0002\"I\u0012\r\u0001\u001f\u0003\u0007\u0003[\u0011$\u0019\u0001=\u0005\r\u0005e\"G1\u0001y\t\u0019\t)E\rb\u0001q\u00121\u0011\u0011\u000b\u001aC\u0002a$a!!\u00183\u0005\u0004AHABA5e\t\u0007\u0001\u0010\u0002\u0004\u0002vI\u0012\r\u0001\u001f\u0003\u0007\u0003\u0003\u0013$\u0019\u0001=\u0005\r\u00055%G1\u0001y\t\u0019\tIJ\rb\u0001q\u00121\u0011Q\u0015\u001aC\u0002a\fqbY8qs\u0012\"WMZ1vYR$\u0013\u0007M\u000b!\u0007G\u001b9k!+\u0004,\u000e56qVBY\u0007g\u001b)la.\u0004:\u000em6QXB`\u0007\u0003\u001c\u0019-\u0006\u0002\u0004&*\"\u0011Q\rB \t\u001598G1\u0001y\t\u0019\tIa\rb\u0001q\u00121\u0011QC\u001aC\u0002a$a!!\t4\u0005\u0004AHABA\u0017g\t\u0007\u0001\u0010\u0002\u0004\u0002:M\u0012\r\u0001\u001f\u0003\u0007\u0003\u000b\u001a$\u0019\u0001=\u0005\r\u0005E3G1\u0001y\t\u0019\tif\rb\u0001q\u00121\u0011\u0011N\u001aC\u0002a$a!!\u001e4\u0005\u0004AHABAAg\t\u0007\u0001\u0010\u0002\u0004\u0002\u000eN\u0012\r\u0001\u001f\u0003\u0007\u00033\u001b$\u0019\u0001=\u0005\r\u0005\u00156G1\u0001y\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\nT\u0003IBe\u0007\u001b\u001cym!5\u0004T\u000eU7q[Bm\u00077\u001cina8\u0004b\u000e\r8Q]Bt\u0007S,\"aa3+\t\u0005E$q\b\u0003\u0006oR\u0012\r\u0001\u001f\u0003\u0007\u0003\u0013!$\u0019\u0001=\u0005\r\u0005UAG1\u0001y\t\u0019\t\t\u0003\u000eb\u0001q\u00121\u0011Q\u0006\u001bC\u0002a$a!!\u000f5\u0005\u0004AHABA#i\t\u0007\u0001\u0010\u0002\u0004\u0002RQ\u0012\r\u0001\u001f\u0003\u0007\u0003;\"$\u0019\u0001=\u0005\r\u0005%DG1\u0001y\t\u0019\t)\b\u000eb\u0001q\u00121\u0011\u0011\u0011\u001bC\u0002a$a!!$5\u0005\u0004AHABAMi\t\u0007\u0001\u0010\u0002\u0004\u0002&R\u0012\r\u0001_\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132eU\u00013q^Bz\u0007k\u001c9p!?\u0004|\u000eu8q C\u0001\t\u0007!)\u0001b\u0002\u0005\n\u0011-AQ\u0002C\b+\t\u0019\tP\u000b\u0003\u0002~\t}B!B<6\u0005\u0004AHABA\u0005k\t\u0007\u0001\u0010\u0002\u0004\u0002\u0016U\u0012\r\u0001\u001f\u0003\u0007\u0003C)$\u0019\u0001=\u0005\r\u00055RG1\u0001y\t\u0019\tI$\u000eb\u0001q\u00121\u0011QI\u001bC\u0002a$a!!\u00156\u0005\u0004AHABA/k\t\u0007\u0001\u0010\u0002\u0004\u0002jU\u0012\r\u0001\u001f\u0003\u0007\u0003k*$\u0019\u0001=\u0005\r\u0005\u0005UG1\u0001y\t\u0019\ti)\u000eb\u0001q\u00121\u0011\u0011T\u001bC\u0002a$a!!*6\u0005\u0004A\u0018aD2paf$C-\u001a4bk2$H%M\u001a\u0016A\u0011UA\u0011\u0004C\u000e\t;!y\u0002\"\t\u0005$\u0011\u0015Bq\u0005C\u0015\tW!i\u0003b\f\u00052\u0011MBQG\u000b\u0003\t/QC!!#\u0003@\u0011)qO\u000eb\u0001q\u00121\u0011\u0011\u0002\u001cC\u0002a$a!!\u00067\u0005\u0004AHABA\u0011m\t\u0007\u0001\u0010\u0002\u0004\u0002.Y\u0012\r\u0001\u001f\u0003\u0007\u0003s1$\u0019\u0001=\u0005\r\u0005\u0015cG1\u0001y\t\u0019\t\tF\u000eb\u0001q\u00121\u0011Q\f\u001cC\u0002a$a!!\u001b7\u0005\u0004AHABA;m\t\u0007\u0001\u0010\u0002\u0004\u0002\u0002Z\u0012\r\u0001\u001f\u0003\u0007\u0003\u001b3$\u0019\u0001=\u0005\r\u0005eeG1\u0001y\t\u0019\t)K\u000eb\u0001q\u0006y1m\u001c9zI\u0011,g-Y;mi\u0012\nD'\u0006\u0011\u0005<\u0011}B\u0011\tC\"\t\u000b\"9\u0005\"\u0013\u0005L\u00115Cq\nC)\t'\")\u0006b\u0016\u0005Z\u0011mSC\u0001C\u001fU\u0011\t)Ja\u0010\u0005\u000b]<$\u0019\u0001=\u0005\r\u0005%qG1\u0001y\t\u0019\t)b\u000eb\u0001q\u00121\u0011\u0011E\u001cC\u0002a$a!!\f8\u0005\u0004AHABA\u001do\t\u0007\u0001\u0010\u0002\u0004\u0002F]\u0012\r\u0001\u001f\u0003\u0007\u0003#:$\u0019\u0001=\u0005\r\u0005usG1\u0001y\t\u0019\tIg\u000eb\u0001q\u00121\u0011QO\u001cC\u0002a$a!!!8\u0005\u0004AHABAGo\t\u0007\u0001\u0010\u0002\u0004\u0002\u001a^\u0012\r\u0001\u001f\u0003\u0007\u0003K;$\u0019\u0001=\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU*\u0002\u0005\"\u0019\u0005f\u0011\u001dD\u0011\u000eC6\t[\"y\u0007\"\u001d\u0005t\u0011UDq\u000fC=\tw\"i\bb \u0005\u0002V\u0011A1\r\u0016\u0005\u0003C\u0013y\u0004B\u0003xq\t\u0007\u0001\u0010\u0002\u0004\u0002\na\u0012\r\u0001\u001f\u0003\u0007\u0003+A$\u0019\u0001=\u0005\r\u0005\u0005\u0002H1\u0001y\t\u0019\ti\u0003\u000fb\u0001q\u00121\u0011\u0011\b\u001dC\u0002a$a!!\u00129\u0005\u0004AHABA)q\t\u0007\u0001\u0010\u0002\u0004\u0002^a\u0012\r\u0001\u001f\u0003\u0007\u0003SB$\u0019\u0001=\u0005\r\u0005U\u0004H1\u0001y\t\u0019\t\t\t\u000fb\u0001q\u00121\u0011Q\u0012\u001dC\u0002a$a!!'9\u0005\u0004AHABASq\t\u0007\u00010A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\t\u000f\u0003B\u0001\"#\u0005\u00106\u0011A1\u0012\u0006\u0004\t\u001bC\u0016\u0001\u00027b]\u001eLA\u0001\"%\u0005\f\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"\u0001b&\u0011\u0007=#I*C\u0002\u0005\u001cB\u00131!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2\u0001 CQ\u0011%!\u0019kOA\u0001\u0002\u0004!9*A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\tS\u0003R\u0001b+\u00052rl!\u0001\",\u000b\u0007\u0011=\u0006+\u0001\u0006d_2dWm\u0019;j_:LA\u0001b-\u0005.\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011!I\fb0\u0011\u0007=#Y,C\u0002\u0005>B\u0013qAQ8pY\u0016\fg\u000e\u0003\u0005\u0005$v\n\t\u00111\u0001}\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0011\u001dEQ\u0019\u0005\n\tGs\u0014\u0011!a\u0001\t/\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\t/\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\t\u000f\u000ba!Z9vC2\u001cH\u0003\u0002C]\t'D\u0001\u0002b)B\u0003\u0003\u0005\r\u0001 \u0015\b\u000f\u0011]GQ\u001cCp!\ryE\u0011\\\u0005\u0004\t7\u0004&\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0001cA;\u0005d\u0012)qo\u0001b\u0001qB\u0019Q\u000fb:\u0005\r\u0005%1A1\u0001y!\r)H1\u001e\u0003\u0007\u0003+\u0019!\u0019\u0001=\u0011\u0007U$y\u000f\u0002\u0004\u0002\"\r\u0011\r\u0001\u001f\t\u0004k\u0012MHABA\u0017\u0007\t\u0007\u0001\u0010E\u0002v\to$a!!\u000f\u0004\u0005\u0004A\bcA;\u0005|\u00121\u0011QI\u0002C\u0002a\u00042!\u001eC\u0000\t\u0019\t\tf\u0001b\u0001qB\u0019Q/b\u0001\u0005\r\u0005u3A1\u0001y!\r)Xq\u0001\u0003\u0007\u0003S\u001a!\u0019\u0001=\u0011\u0007U,Y\u0001\u0002\u0004\u0002v\r\u0011\r\u0001\u001f\t\u0004k\u0016=AABAA\u0007\t\u0007\u0001\u0010E\u0002v\u000b'!a!!$\u0004\u0005\u0004A\bcA;\u0006\u0018\u00111\u0011\u0011T\u0002C\u0002a\u00042!^C\u000e\t\u0019\t)k\u0001b\u0001q\"1!o\u0001a\u0001\tCDq!!\u0001\u0004\u0001\u0004!)\u000fC\u0004\u0002\u000e\r\u0001\r\u0001\";\t\u000f\u0005e1\u00011\u0001\u0005n\"9\u0011QE\u0002A\u0002\u0011E\bbBA\u0019\u0007\u0001\u0007AQ\u001f\u0005\b\u0003{\u0019\u0001\u0019\u0001C}\u0011\u001d\tIe\u0001a\u0001\t{Dq!!\u0016\u0004\u0001\u0004)\t\u0001C\u0004\u0002b\r\u0001\r!\"\u0002\t\u000f\u000554\u00011\u0001\u0006\n!9\u0011\u0011P\u0002A\u0002\u00155\u0001bBAC\u0007\u0001\u0007Q\u0011\u0003\u0005\b\u0003#\u001b\u0001\u0019AC\u000b\u0011\u001d\tij\u0001a\u0001\u000b3\tQ!\u00199qYf,\u0002%b\u0010\u0006F\u0015%SQJC)\u000b+*I&\"\u0018\u0006b\u0015\u0015T\u0011NC7\u000bc*)(\"\u001f\u0006~Q\u0001S\u0011IC@\u000b\u0003+\u0019)\"\"\u0006\b\u0016%U1RCG\u000b\u001f+\t*b%\u0006\u0016\u0016]U\u0011TCN!\u0001Zu!b\u0011\u0006H\u0015-SqJC*\u000b/*Y&b\u0018\u0006d\u0015\u001dT1NC8\u000bg*9(b\u001f\u0011\u0007U,)\u0005B\u0003x\t\t\u0007\u0001\u0010E\u0002v\u000b\u0013\"a!!\u0003\u0005\u0005\u0004A\bcA;\u0006N\u00111\u0011Q\u0003\u0003C\u0002a\u00042!^C)\t\u0019\t\t\u0003\u0002b\u0001qB\u0019Q/\"\u0016\u0005\r\u00055BA1\u0001y!\r)X\u0011\f\u0003\u0007\u0003s!!\u0019\u0001=\u0011\u0007U,i\u0006\u0002\u0004\u0002F\u0011\u0011\r\u0001\u001f\t\u0004k\u0016\u0005DABA)\t\t\u0007\u0001\u0010E\u0002v\u000bK\"a!!\u0018\u0005\u0005\u0004A\bcA;\u0006j\u00111\u0011\u0011\u000e\u0003C\u0002a\u00042!^C7\t\u0019\t)\b\u0002b\u0001qB\u0019Q/\"\u001d\u0005\r\u0005\u0005EA1\u0001y!\r)XQ\u000f\u0003\u0007\u0003\u001b#!\u0019\u0001=\u0011\u0007U,I\b\u0002\u0004\u0002\u001a\u0012\u0011\r\u0001\u001f\t\u0004k\u0016uDABAS\t\t\u0007\u0001\u0010\u0003\u0004s\t\u0001\u0007Q1\t\u0005\b\u0003\u0003!\u0001\u0019AC$\u0011\u001d\ti\u0001\u0002a\u0001\u000b\u0017Bq!!\u0007\u0005\u0001\u0004)y\u0005C\u0004\u0002&\u0011\u0001\r!b\u0015\t\u000f\u0005EB\u00011\u0001\u0006X!9\u0011Q\b\u0003A\u0002\u0015m\u0003bBA%\t\u0001\u0007Qq\f\u0005\b\u0003+\"\u0001\u0019AC2\u0011\u001d\t\t\u0007\u0002a\u0001\u000bOBq!!\u001c\u0005\u0001\u0004)Y\u0007C\u0004\u0002z\u0011\u0001\r!b\u001c\t\u000f\u0005\u0015E\u00011\u0001\u0006t!9\u0011\u0011\u0013\u0003A\u0002\u0015]\u0004bBAO\t\u0001\u0007Q1P\u0001\bk:\f\u0007\u000f\u001d7z+\u0001*\t+\",\u00062\u0016UV\u0011XC_\u000b\u0003,)-\"3\u0006N\u0016EWQ[Cm\u000b;,\t/\":\u0015\t\u0015\rVq\u001d\t\u0006\u001f\u0016\u0015V\u0011V\u0005\u0004\u000bO\u0003&AB(qi&|g\u000eE\u0011P\u0003#,Y+b,\u00064\u0016]V1XC`\u000b\u0007,9-b3\u0006P\u0016MWq[Cn\u000b?,\u0019\u000fE\u0002v\u000b[#Qa^\u0003C\u0002a\u00042!^CY\t\u0019\tI!\u0002b\u0001qB\u0019Q/\".\u0005\r\u0005UQA1\u0001y!\r)X\u0011\u0018\u0003\u0007\u0003C)!\u0019\u0001=\u0011\u0007U,i\f\u0002\u0004\u0002.\u0015\u0011\r\u0001\u001f\t\u0004k\u0016\u0005GABA\u001d\u000b\t\u0007\u0001\u0010E\u0002v\u000b\u000b$a!!\u0012\u0006\u0005\u0004A\bcA;\u0006J\u00121\u0011\u0011K\u0003C\u0002a\u00042!^Cg\t\u0019\ti&\u0002b\u0001qB\u0019Q/\"5\u0005\r\u0005%TA1\u0001y!\r)XQ\u001b\u0003\u0007\u0003k*!\u0019\u0001=\u0011\u0007U,I\u000e\u0002\u0004\u0002\u0002\u0016\u0011\r\u0001\u001f\t\u0004k\u0016uGABAG\u000b\t\u0007\u0001\u0010E\u0002v\u000bC$a!!'\u0006\u0005\u0004A\bcA;\u0006f\u00121\u0011QU\u0003C\u0002aD\u0011\"\";\u0006\u0003\u0003\u0005\r!b;\u0002\u0007a$\u0003\u0007\u0005\u0011L\u000f\u0015-VqVCZ\u000bo+Y,b0\u0006D\u0016\u001dW1ZCh\u000b',9.b7\u0006`\u0016\r\u0018\u0001D<sSR,'+\u001a9mC\u000e,GCACy!\u0011!I)b=\n\t\u0015UH1\u0012\u0002\u0007\u001f\nTWm\u0019;")
public final class Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>
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
    private final T9 t9;
    private final T10 t10;
    private final T11 t11;
    private final T12 t12;
    private final T13 t13;
    private final T14 t14;
    private final T15 t15;
    private final scala.Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> toScala;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Option<scala.Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> unapply(Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> x$0) {
        return Tuple15$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
        return Tuple15$.MODULE$.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
        return Tuple15$.MODULE$.create(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
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

    public T9 t9() {
        return this.t9;
    }

    public T10 t10() {
        return this.t10;
    }

    public T11 t11() {
        return this.t11;
    }

    public T12 t12() {
        return this.t12;
    }

    public T13 t13() {
        return this.t13;
    }

    public T14 t14() {
        return this.t14;
    }

    public T15 t15() {
        return this.t15;
    }

    public scala.Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
        return new Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T10 copy$default$10() {
        return this.t10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T11 copy$default$11() {
        return this.t11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T12 copy$default$12() {
        return this.t12();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T13 copy$default$13() {
        return this.t13();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T14 copy$default$14() {
        return this.t14();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T15 copy$default$15() {
        return this.t15();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T6 copy$default$6() {
        return this.t6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T7 copy$default$7() {
        return this.t7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T8 copy$default$8() {
        return this.t8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> T9 copy$default$9() {
        return this.t9();
    }

    @Override
    public String productPrefix() {
        return "Tuple15";
    }

    @Override
    public int productArity() {
        return 15;
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
            case 8: {
                return this.t9();
            }
            case 9: {
                return this.t10();
            }
            case 10: {
                return this.t11();
            }
            case 11: {
                return this.t12();
            }
            case 12: {
                return this.t13();
            }
            case 13: {
                return this.t14();
            }
            case 14: {
                return this.t15();
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
        return x$1 instanceof Tuple15;
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
            case 8: {
                return "t9";
            }
            case 9: {
                return "t10";
            }
            case 10: {
                return "t11";
            }
            case 11: {
                return "t12";
            }
            case 12: {
                return "t13";
            }
            case 13: {
                return "t14";
            }
            case 14: {
                return "t15";
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
        if (!(object instanceof Tuple15)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple15 tuple15 = (Tuple15)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple15.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple15.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple15.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple15.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple15.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple15.t6())) return false;
        if (!BoxesRunTime.equals(this.t7(), tuple15.t7())) return false;
        if (!BoxesRunTime.equals(this.t8(), tuple15.t8())) return false;
        if (!BoxesRunTime.equals(this.t9(), tuple15.t9())) return false;
        if (!BoxesRunTime.equals(this.t10(), tuple15.t10())) return false;
        if (!BoxesRunTime.equals(this.t11(), tuple15.t11())) return false;
        if (!BoxesRunTime.equals(this.t12(), tuple15.t12())) return false;
        if (!BoxesRunTime.equals(this.t13(), tuple15.t13())) return false;
        if (!BoxesRunTime.equals(this.t14(), tuple15.t14())) return false;
        if (!BoxesRunTime.equals(this.t15(), tuple15.t15())) return false;
        return true;
    }

    public Tuple15(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
        this.t10 = t10;
        this.t11 = t11;
        this.t12 = t12;
        this.t13 = t13;
        this.t14 = t14;
        this.t15 = t15;
        Product.$init$(this);
        this.toScala = new scala.Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
    }
}

