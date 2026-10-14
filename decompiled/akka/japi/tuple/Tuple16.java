/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple16$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0019\u001dt!B#G\u0011\u0003ie!B(G\u0011\u0003\u0001\u0006\"B0\u0002\t\u0003\u0001\u0007\"B1\u0002\t\u0003\u0011\u0007\"CCQ\u0003\u0005\u0005I\u0011QCR\u0011%1I!AA\u0001\n\u00033Y\u0001C\u0005\u0007^\u0005\t\t\u0011\"\u0003\u0007`\u0019!qJ\u0012\"f\u0011!)xA!f\u0001\n\u00031\b\"CA\u0003\u000f\tE\t\u0015!\u0003x\u0011)\t9a\u0002BK\u0002\u0013\u0005\u0011\u0011\u0002\u0005\u000b\u0003#9!\u0011#Q\u0001\n\u0005-\u0001BCA\n\u000f\tU\r\u0011\"\u0001\u0002\u0016!Q\u0011QD\u0004\u0003\u0012\u0003\u0006I!a\u0006\t\u0015\u0005}qA!f\u0001\n\u0003\t\t\u0003\u0003\u0006\u0002*\u001d\u0011\t\u0012)A\u0005\u0003GA!\"a\u000b\b\u0005+\u0007I\u0011AA\u0017\u0011)\t)d\u0002B\tB\u0003%\u0011q\u0006\u0005\u000b\u0003o9!Q3A\u0005\u0002\u0005e\u0002BCA!\u000f\tE\t\u0015!\u0003\u0002<!Q\u00111I\u0004\u0003\u0016\u0004%\t!!\u0012\t\u0015\u00055sA!E!\u0002\u0013\t9\u0005\u0003\u0006\u0002P\u001d\u0011)\u001a!C\u0001\u0003#B!\"!\u0017\b\u0005#\u0005\u000b\u0011BA*\u0011)\tYf\u0002BK\u0002\u0013\u0005\u0011Q\f\u0005\u000b\u0003K:!\u0011#Q\u0001\n\u0005}\u0003BCA4\u000f\tU\r\u0011\"\u0001\u0002j!Q\u0011\u0011O\u0004\u0003\u0012\u0003\u0006I!a\u001b\t\u0015\u0005MtA!f\u0001\n\u0003\t)\b\u0003\u0006\u0002~\u001d\u0011\t\u0012)A\u0005\u0003oB!\"a \b\u0005+\u0007I\u0011AAA\u0011)\tIi\u0002B\tB\u0003%\u00111\u0011\u0005\u000b\u0003\u0017;!Q3A\u0005\u0002\u00055\u0005BCAK\u000f\tE\t\u0015!\u0003\u0002\u0010\"Q\u0011qS\u0004\u0003\u0016\u0004%\t!!'\t\u0015\u0005\u0005vA!E!\u0002\u0013\tY\n\u0003\u0006\u0002$\u001e\u0011)\u001a!C\u0001\u0003KC!\"!,\b\u0005#\u0005\u000b\u0011BAT\u0011)\tyk\u0002BK\u0002\u0013\u0005\u0011\u0011\u0017\u0005\u000b\u0003s;!\u0011#Q\u0001\n\u0005M\u0006BB0\b\t\u0003\tY\fC\u0005\u0002`\u001e\u0011\r\u0011\"\u0001\u0002b\"A\u0011q]\u0004!\u0002\u0013\t\u0019\u000fC\u0005\u0002j\u001e\t\t\u0011\"\u0001\u0002l\"I!\u0011K\u0004\u0012\u0002\u0013\u0005!1\u000b\u0005\n\u0005\u0017;\u0011\u0013!C\u0001\u0005\u001bC\u0011Ba-\b#\u0003%\tA!.\t\u0013\tmw!%A\u0005\u0002\tu\u0007\"CB\u0002\u000fE\u0005I\u0011AB\u0003\u0011%\u0019YcBI\u0001\n\u0003\u0019i\u0003C\u0005\u0004T\u001d\t\n\u0011\"\u0001\u0004V!I11P\u0004\u0012\u0002\u0013\u00051Q\u0010\u0005\n\u0007G;\u0011\u0013!C\u0001\u0007KC\u0011ba3\b#\u0003%\ta!4\t\u0013\rMx!%A\u0005\u0002\rU\b\"\u0003C\u000e\u000fE\u0005I\u0011\u0001C\u000f\u0011%!\u0019eBI\u0001\n\u0003!)\u0005C\u0005\u0005l\u001d\t\n\u0011\"\u0001\u0005n!IA1S\u0004\u0012\u0002\u0013\u0005AQ\u0013\u0005\n\tw;\u0011\u0013!C\u0001\t{C\u0011\u0002b9\b\u0003\u0003%\t\u0005\":\t\u0013\u0011Mx!!A\u0005\u0002\u0011U\b\"\u0003C\u007f\u000f\u0005\u0005I\u0011\u0001C\u0000\u0011%))aBA\u0001\n\u0003*9\u0001C\u0005\u0006\u0016\u001d\t\t\u0011\"\u0001\u0006\u0018!IQ\u0011E\u0004\u0002\u0002\u0013\u0005S1\u0005\u0005\n\u000bO9\u0011\u0011!C!\u000bSA\u0011\"b\u000b\b\u0003\u0003%\t%\"\f\t\u0013\u0015=r!!A\u0005B\u0015E\u0012a\u0002+va2,\u0017G\u000e\u0006\u0003\u000f\"\u000bQ\u0001^;qY\u0016T!!\u0013&\u0002\t)\f\u0007/\u001b\u0006\u0002\u0017\u0006!\u0011m[6b\u0007\u0001\u0001\"AT\u0001\u000e\u0003\u0019\u0013q\u0001V;qY\u0016\fdgE\u0002\u0002#^\u0003\"AU+\u000e\u0003MS\u0011\u0001V\u0001\u0006g\u000e\fG.Y\u0005\u0003-N\u0013a!\u00118z%\u00164\u0007C\u0001-^\u001b\u0005I&B\u0001.\\\u0003\tIwNC\u0001]\u0003\u0011Q\u0017M^1\n\u0005yK&\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\bF\u0001N\u0003\u0019\u0019'/Z1uKV\t3-b\u0011\u0006H\u0015-SqJC*\u000b/*Y&b\u0018\u0006d\u0015\u001dT1NC8\u000bg*9(b\u001f\u0006\u0000Q\tC-\"!\u0006\u0004\u0016\u0015UqQCE\u000b\u0017+i)b$\u0006\u0012\u0016MUQSCL\u000b3+Y*\"(\u0006 B\u0011cjBC!\u000b\u000b*I%\"\u0014\u0006R\u0015US\u0011LC/\u000bC*)'\"\u001b\u0006n\u0015ETQOC=\u000b{*\u0002EZ=\u0002\u000e\u0005e\u0011QEA\u0019\u0003{\tI%!\u0016\u0002b\u00055\u0014\u0011PAC\u0003#\u000bi*!+\u00026N!q!U4k!\t\u0011\u0006.\u0003\u0002j'\n9\u0001K]8ek\u000e$\bCA6t\u001d\ta\u0017O\u0004\u0002na6\taN\u0003\u0002p\u0019\u00061AH]8pizJ\u0011\u0001V\u0005\u0003eN\u000bq\u0001]1dW\u0006<W-\u0003\u0002_i*\u0011!oU\u0001\u0003iF*\u0012a\u001e\t\u0003qfd\u0001\u0001B\u0003{\u000f\t\u00071P\u0001\u0002UcE\u0011Ap \t\u0003%vL!A`*\u0003\u000f9{G\u000f[5oOB\u0019!+!\u0001\n\u0007\u0005\r1KA\u0002B]f\f1\u0001^\u0019!\u0003\t!('\u0006\u0002\u0002\fA\u0019\u00010!\u0004\u0005\r\u0005=qA1\u0001|\u0005\t!&'A\u0002ue\u0001\n!\u0001^\u001a\u0016\u0005\u0005]\u0001c\u0001=\u0002\u001a\u00111\u00111D\u0004C\u0002m\u0014!\u0001V\u001a\u0002\u0007Q\u001c\u0004%\u0001\u0002uiU\u0011\u00111\u0005\t\u0004q\u0006\u0015BABA\u0014\u000f\t\u00071P\u0001\u0002Ui\u0005\u0019A\u000f\u000e\u0011\u0002\u0005Q,TCAA\u0018!\rA\u0018\u0011\u0007\u0003\u0007\u0003g9!\u0019A>\u0003\u0005Q+\u0014a\u0001;6A\u0005\u0011AON\u000b\u0003\u0003w\u00012\u0001_A\u001f\t\u0019\tyd\u0002b\u0001w\n\u0011AKN\u0001\u0004iZ\u0002\u0013A\u0001;8+\t\t9\u0005E\u0002y\u0003\u0013\"a!a\u0013\b\u0005\u0004Y(A\u0001+8\u0003\r!x\u0007I\u0001\u0003ib*\"!a\u0015\u0011\u0007a\f)\u0006\u0002\u0004\u0002X\u001d\u0011\ra\u001f\u0002\u0003)b\n1\u0001\u001e\u001d!\u0003\t!\u0018(\u0006\u0002\u0002`A\u0019\u00010!\u0019\u0005\r\u0005\rtA1\u0001|\u0005\t!\u0016(A\u0002us\u0001\n1\u0001^\u00191+\t\tY\u0007E\u0002y\u0003[\"a!a\u001c\b\u0005\u0004Y(a\u0001+2a\u0005!A/\r\u0019!\u0003\r!\u0018'M\u000b\u0003\u0003o\u00022\u0001_A=\t\u0019\tYh\u0002b\u0001w\n\u0019A+M\u0019\u0002\tQ\f\u0014\u0007I\u0001\u0004iF\u0012TCAAB!\rA\u0018Q\u0011\u0003\u0007\u0003\u000f;!\u0019A>\u0003\u0007Q\u000b$'\u0001\u0003ucI\u0002\u0013a\u0001;2gU\u0011\u0011q\u0012\t\u0004q\u0006EEABAJ\u000f\t\u00071PA\u0002UcM\nA\u0001^\u00194A\u0005\u0019A/\r\u001b\u0016\u0005\u0005m\u0005c\u0001=\u0002\u001e\u00121\u0011qT\u0004C\u0002m\u00141\u0001V\u00195\u0003\u0011!\u0018\u0007\u000e\u0011\u0002\u0007Q\fT'\u0006\u0002\u0002(B\u0019\u00010!+\u0005\r\u0005-vA1\u0001|\u0005\r!\u0016'N\u0001\u0005iF*\u0004%A\u0002ucY*\"!a-\u0011\u0007a\f)\f\u0002\u0004\u00028\u001e\u0011\ra\u001f\u0002\u0004)F2\u0014\u0001\u0002;2m\u0001\"\"%!0\u0002@\u0006\u0005\u00171YAc\u0003\u000f\fI-a3\u0002N\u0006=\u0017\u0011[Aj\u0003+\f9.!7\u0002\\\u0006u\u0007#\t(\bo\u0006-\u0011qCA\u0012\u0003_\tY$a\u0012\u0002T\u0005}\u00131NA<\u0003\u0007\u000by)a'\u0002(\u0006M\u0006\"B;)\u0001\u00049\bbBA\u0004Q\u0001\u0007\u00111\u0002\u0005\b\u0003'A\u0003\u0019AA\f\u0011\u001d\ty\u0002\u000ba\u0001\u0003GAq!a\u000b)\u0001\u0004\ty\u0003C\u0004\u00028!\u0002\r!a\u000f\t\u000f\u0005\r\u0003\u00061\u0001\u0002H!9\u0011q\n\u0015A\u0002\u0005M\u0003bBA.Q\u0001\u0007\u0011q\f\u0005\b\u0003OB\u0003\u0019AA6\u0011\u001d\t\u0019\b\u000ba\u0001\u0003oBq!a )\u0001\u0004\t\u0019\tC\u0004\u0002\f\"\u0002\r!a$\t\u000f\u0005]\u0005\u00061\u0001\u0002\u001c\"9\u00111\u0015\u0015A\u0002\u0005\u001d\u0006bBAXQ\u0001\u0007\u00111W\u0001\bi>\u001c6-\u00197b+\t\t\u0019\u000f\u0005\u0012S\u0003K<\u00181BA\f\u0003G\ty#a\u000f\u0002H\u0005M\u0013qLA6\u0003o\n\u0019)a$\u0002\u001c\u0006\u001d\u00161W\u0005\u0003\u001fN\u000b\u0001\u0002^8TG\u0006d\u0017\rI\u0001\u0005G>\u0004\u00180\u0006\u0012\u0002n\u0006M\u0018q_A~\u0003\u007f\u0014\u0019Aa\u0002\u0003\f\t=!1\u0003B\f\u00057\u0011yBa\t\u0003(\t-\"q\u0006\u000b#\u0003_\u0014\tDa\r\u00036\t]\"\u0011\bB\u001e\u0005{\u0011yD!\u0011\u0003D\t\u0015#q\tB%\u0005\u0017\u0012iEa\u0014\u0011E9;\u0011\u0011_A{\u0003s\fiP!\u0001\u0003\u0006\t%!Q\u0002B\t\u0005+\u0011IB!\b\u0003\"\t\u0015\"\u0011\u0006B\u0017!\rA\u00181\u001f\u0003\u0006u.\u0012\ra\u001f\t\u0004q\u0006]HABA\bW\t\u00071\u0010E\u0002y\u0003w$a!a\u0007,\u0005\u0004Y\bc\u0001=\u0002\u0000\u00121\u0011qE\u0016C\u0002m\u00042\u0001\u001fB\u0002\t\u0019\t\u0019d\u000bb\u0001wB\u0019\u0001Pa\u0002\u0005\r\u0005}2F1\u0001|!\rA(1\u0002\u0003\u0007\u0003\u0017Z#\u0019A>\u0011\u0007a\u0014y\u0001\u0002\u0004\u0002X-\u0012\ra\u001f\t\u0004q\nMAABA2W\t\u00071\u0010E\u0002y\u0005/!a!a\u001c,\u0005\u0004Y\bc\u0001=\u0003\u001c\u00111\u00111P\u0016C\u0002m\u00042\u0001\u001fB\u0010\t\u0019\t9i\u000bb\u0001wB\u0019\u0001Pa\t\u0005\r\u0005M5F1\u0001|!\rA(q\u0005\u0003\u0007\u0003?[#\u0019A>\u0011\u0007a\u0014Y\u0003\u0002\u0004\u0002,.\u0012\ra\u001f\t\u0004q\n=BABA\\W\t\u00071\u0010\u0003\u0005vWA\u0005\t\u0019AAy\u0011%\t9a\u000bI\u0001\u0002\u0004\t)\u0010C\u0005\u0002\u0014-\u0002\n\u00111\u0001\u0002z\"I\u0011qD\u0016\u0011\u0002\u0003\u0007\u0011Q \u0005\n\u0003WY\u0003\u0013!a\u0001\u0005\u0003A\u0011\"a\u000e,!\u0003\u0005\rA!\u0002\t\u0013\u0005\r3\u0006%AA\u0002\t%\u0001\"CA(WA\u0005\t\u0019\u0001B\u0007\u0011%\tYf\u000bI\u0001\u0002\u0004\u0011\t\u0002C\u0005\u0002h-\u0002\n\u00111\u0001\u0003\u0016!I\u00111O\u0016\u0011\u0002\u0003\u0007!\u0011\u0004\u0005\n\u0003\u007fZ\u0003\u0013!a\u0001\u0005;A\u0011\"a#,!\u0003\u0005\rA!\t\t\u0013\u0005]5\u0006%AA\u0002\t\u0015\u0002\"CARWA\u0005\t\u0019\u0001B\u0015\u0011%\tyk\u000bI\u0001\u0002\u0004\u0011i#\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016E\tU#1\u000eB7\u0005_\u0012\tHa\u001d\u0003v\t]$\u0011\u0010B>\u0005{\u0012yH!!\u0003\u0004\n\u0015%q\u0011BE+\t\u00119FK\u0002x\u00053Z#Aa\u0017\u0011\t\tu#qM\u0007\u0003\u0005?RAA!\u0019\u0003d\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0005K\u001a\u0016AC1o]>$\u0018\r^5p]&!!\u0011\u000eB0\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006u2\u0012\ra\u001f\u0003\u0007\u0003\u001fa#\u0019A>\u0005\r\u0005mAF1\u0001|\t\u0019\t9\u0003\fb\u0001w\u00121\u00111\u0007\u0017C\u0002m$a!a\u0010-\u0005\u0004YHABA&Y\t\u00071\u0010\u0002\u0004\u0002X1\u0012\ra\u001f\u0003\u0007\u0003Gb#\u0019A>\u0005\r\u0005=DF1\u0001|\t\u0019\tY\b\fb\u0001w\u00121\u0011q\u0011\u0017C\u0002m$a!a%-\u0005\u0004YHABAPY\t\u00071\u0010\u0002\u0004\u0002,2\u0012\ra\u001f\u0003\u0007\u0003oc#\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011#q\u0012BJ\u0005+\u00139J!'\u0003\u001c\nu%q\u0014BQ\u0005G\u0013)Ka*\u0003*\n-&Q\u0016BX\u0005c+\"A!%+\t\u0005-!\u0011\f\u0003\u0006u6\u0012\ra\u001f\u0003\u0007\u0003\u001fi#\u0019A>\u0005\r\u0005mQF1\u0001|\t\u0019\t9#\fb\u0001w\u00121\u00111G\u0017C\u0002m$a!a\u0010.\u0005\u0004YHABA&[\t\u00071\u0010\u0002\u0004\u0002X5\u0012\ra\u001f\u0003\u0007\u0003Gj#\u0019A>\u0005\r\u0005=TF1\u0001|\t\u0019\tY(\fb\u0001w\u00121\u0011qQ\u0017C\u0002m$a!a%.\u0005\u0004YHABAP[\t\u00071\u0010\u0002\u0004\u0002,6\u0012\ra\u001f\u0003\u0007\u0003ok#\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011#q\u0017B^\u0005{\u0013yL!1\u0003D\n\u0015'q\u0019Be\u0005\u0017\u0014iMa4\u0003R\nM'Q\u001bBl\u00053,\"A!/+\t\u0005]!\u0011\f\u0003\u0006u:\u0012\ra\u001f\u0003\u0007\u0003\u001fq#\u0019A>\u0005\r\u0005maF1\u0001|\t\u0019\t9C\fb\u0001w\u00121\u00111\u0007\u0018C\u0002m$a!a\u0010/\u0005\u0004YHABA&]\t\u00071\u0010\u0002\u0004\u0002X9\u0012\ra\u001f\u0003\u0007\u0003Gr#\u0019A>\u0005\r\u0005=dF1\u0001|\t\u0019\tYH\fb\u0001w\u00121\u0011q\u0011\u0018C\u0002m$a!a%/\u0005\u0004YHABAP]\t\u00071\u0010\u0002\u0004\u0002,:\u0012\ra\u001f\u0003\u0007\u0003os#\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011#q\u001cBr\u0005K\u00149O!;\u0003l\n5(q\u001eBy\u0005g\u0014)Pa>\u0003z\nm(Q B\u0000\u0007\u0003)\"A!9+\t\u0005\r\"\u0011\f\u0003\u0006u>\u0012\ra\u001f\u0003\u0007\u0003\u001fy#\u0019A>\u0005\r\u0005mqF1\u0001|\t\u0019\t9c\fb\u0001w\u00121\u00111G\u0018C\u0002m$a!a\u00100\u0005\u0004YHABA&_\t\u00071\u0010\u0002\u0004\u0002X=\u0012\ra\u001f\u0003\u0007\u0003Gz#\u0019A>\u0005\r\u0005=tF1\u0001|\t\u0019\tYh\fb\u0001w\u00121\u0011qQ\u0018C\u0002m$a!a%0\u0005\u0004YHABAP_\t\u00071\u0010\u0002\u0004\u0002,>\u0012\ra\u001f\u0003\u0007\u0003o{#\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%kU\u00113qAB\u0006\u0007\u001b\u0019ya!\u0005\u0004\u0014\rU1qCB\r\u00077\u0019iba\b\u0004\"\r\r2QEB\u0014\u0007S)\"a!\u0003+\t\u0005=\"\u0011\f\u0003\u0006uB\u0012\ra\u001f\u0003\u0007\u0003\u001f\u0001$\u0019A>\u0005\r\u0005m\u0001G1\u0001|\t\u0019\t9\u0003\rb\u0001w\u00121\u00111\u0007\u0019C\u0002m$a!a\u00101\u0005\u0004YHABA&a\t\u00071\u0010\u0002\u0004\u0002XA\u0012\ra\u001f\u0003\u0007\u0003G\u0002$\u0019A>\u0005\r\u0005=\u0004G1\u0001|\t\u0019\tY\b\rb\u0001w\u00121\u0011q\u0011\u0019C\u0002m$a!a%1\u0005\u0004YHABAPa\t\u00071\u0010\u0002\u0004\u0002,B\u0012\ra\u001f\u0003\u0007\u0003o\u0003$\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%mU\u00113qFB\u001a\u0007k\u00199d!\u000f\u0004<\ru2qHB!\u0007\u0007\u001a)ea\u0012\u0004J\r-3QJB(\u0007#*\"a!\r+\t\u0005m\"\u0011\f\u0003\u0006uF\u0012\ra\u001f\u0003\u0007\u0003\u001f\t$\u0019A>\u0005\r\u0005m\u0011G1\u0001|\t\u0019\t9#\rb\u0001w\u00121\u00111G\u0019C\u0002m$a!a\u00102\u0005\u0004YHABA&c\t\u00071\u0010\u0002\u0004\u0002XE\u0012\ra\u001f\u0003\u0007\u0003G\n$\u0019A>\u0005\r\u0005=\u0014G1\u0001|\t\u0019\tY(\rb\u0001w\u00121\u0011qQ\u0019C\u0002m$a!a%2\u0005\u0004YHABAPc\t\u00071\u0010\u0002\u0004\u0002,F\u0012\ra\u001f\u0003\u0007\u0003o\u000b$\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%oU\u00113qKB.\u0007;\u001ayf!\u0019\u0004d\r\u00154qMB5\u0007W\u001aiga\u001c\u0004r\rM4QOB<\u0007s*\"a!\u0017+\t\u0005\u001d#\u0011\f\u0003\u0006uJ\u0012\ra\u001f\u0003\u0007\u0003\u001f\u0011$\u0019A>\u0005\r\u0005m!G1\u0001|\t\u0019\t9C\rb\u0001w\u00121\u00111\u0007\u001aC\u0002m$a!a\u00103\u0005\u0004YHABA&e\t\u00071\u0010\u0002\u0004\u0002XI\u0012\ra\u001f\u0003\u0007\u0003G\u0012$\u0019A>\u0005\r\u0005=$G1\u0001|\t\u0019\tYH\rb\u0001w\u00121\u0011q\u0011\u001aC\u0002m$a!a%3\u0005\u0004YHABAPe\t\u00071\u0010\u0002\u0004\u0002,J\u0012\ra\u001f\u0003\u0007\u0003o\u0013$\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%qU\u00113qPBB\u0007\u000b\u001b9i!#\u0004\f\u000e55qRBI\u0007'\u001b)ja&\u0004\u001a\u000em5QTBP\u0007C+\"a!!+\t\u0005M#\u0011\f\u0003\u0006uN\u0012\ra\u001f\u0003\u0007\u0003\u001f\u0019$\u0019A>\u0005\r\u0005m1G1\u0001|\t\u0019\t9c\rb\u0001w\u00121\u00111G\u001aC\u0002m$a!a\u00104\u0005\u0004YHABA&g\t\u00071\u0010\u0002\u0004\u0002XM\u0012\ra\u001f\u0003\u0007\u0003G\u001a$\u0019A>\u0005\r\u0005=4G1\u0001|\t\u0019\tYh\rb\u0001w\u00121\u0011qQ\u001aC\u0002m$a!a%4\u0005\u0004YHABAPg\t\u00071\u0010\u0002\u0004\u0002,N\u0012\ra\u001f\u0003\u0007\u0003o\u001b$\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%sU\u00113qUBV\u0007[\u001byk!-\u00044\u000eU6qWB]\u0007w\u001bila0\u0004B\u000e\r7QYBd\u0007\u0013,\"a!++\t\u0005}#\u0011\f\u0003\u0006uR\u0012\ra\u001f\u0003\u0007\u0003\u001f!$\u0019A>\u0005\r\u0005mAG1\u0001|\t\u0019\t9\u0003\u000eb\u0001w\u00121\u00111\u0007\u001bC\u0002m$a!a\u00105\u0005\u0004YHABA&i\t\u00071\u0010\u0002\u0004\u0002XQ\u0012\ra\u001f\u0003\u0007\u0003G\"$\u0019A>\u0005\r\u0005=DG1\u0001|\t\u0019\tY\b\u000eb\u0001w\u00121\u0011q\u0011\u001bC\u0002m$a!a%5\u0005\u0004YHABAPi\t\u00071\u0010\u0002\u0004\u0002,R\u0012\ra\u001f\u0003\u0007\u0003o#$\u0019A>\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cA*\"ea4\u0004T\u000eU7q[Bm\u00077\u001cina8\u0004b\u000e\r8Q]Bt\u0007S\u001cYo!<\u0004p\u000eEXCABiU\u0011\tYG!\u0017\u0005\u000bi,$\u0019A>\u0005\r\u0005=QG1\u0001|\t\u0019\tY\"\u000eb\u0001w\u00121\u0011qE\u001bC\u0002m$a!a\r6\u0005\u0004YHABA k\t\u00071\u0010\u0002\u0004\u0002LU\u0012\ra\u001f\u0003\u0007\u0003/*$\u0019A>\u0005\r\u0005\rTG1\u0001|\t\u0019\ty'\u000eb\u0001w\u00121\u00111P\u001bC\u0002m$a!a\"6\u0005\u0004YHABAJk\t\u00071\u0010\u0002\u0004\u0002 V\u0012\ra\u001f\u0003\u0007\u0003W+$\u0019A>\u0005\r\u0005]VG1\u0001|\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\nTCIB|\u0007w\u001cipa@\u0005\u0002\u0011\rAQ\u0001C\u0004\t\u0013!Y\u0001\"\u0004\u0005\u0010\u0011EA1\u0003C\u000b\t/!I\"\u0006\u0002\u0004z*\"\u0011q\u000fB-\t\u0015QhG1\u0001|\t\u0019\tyA\u000eb\u0001w\u00121\u00111\u0004\u001cC\u0002m$a!a\n7\u0005\u0004YHABA\u001am\t\u00071\u0010\u0002\u0004\u0002@Y\u0012\ra\u001f\u0003\u0007\u0003\u00172$\u0019A>\u0005\r\u0005]cG1\u0001|\t\u0019\t\u0019G\u000eb\u0001w\u00121\u0011q\u000e\u001cC\u0002m$a!a\u001f7\u0005\u0004YHABADm\t\u00071\u0010\u0002\u0004\u0002\u0014Z\u0012\ra\u001f\u0003\u0007\u0003?3$\u0019A>\u0005\r\u0005-fG1\u0001|\t\u0019\t9L\u000eb\u0001w\u0006y1m\u001c9zI\u0011,g-Y;mi\u0012\n$'\u0006\u0012\u0005 \u0011\rBQ\u0005C\u0014\tS!Y\u0003\"\f\u00050\u0011EB1\u0007C\u001b\to!I\u0004b\u000f\u0005>\u0011}B\u0011I\u000b\u0003\tCQC!a!\u0003Z\u0011)!p\u000eb\u0001w\u00121\u0011qB\u001cC\u0002m$a!a\u00078\u0005\u0004YHABA\u0014o\t\u00071\u0010\u0002\u0004\u00024]\u0012\ra\u001f\u0003\u0007\u0003\u007f9$\u0019A>\u0005\r\u0005-sG1\u0001|\t\u0019\t9f\u000eb\u0001w\u00121\u00111M\u001cC\u0002m$a!a\u001c8\u0005\u0004YHABA>o\t\u00071\u0010\u0002\u0004\u0002\b^\u0012\ra\u001f\u0003\u0007\u0003';$\u0019A>\u0005\r\u0005}uG1\u0001|\t\u0019\tYk\u000eb\u0001w\u00121\u0011qW\u001cC\u0002m\fqbY8qs\u0012\"WMZ1vYR$\u0013gM\u000b#\t\u000f\"Y\u0005\"\u0014\u0005P\u0011EC1\u000bC+\t/\"I\u0006b\u0017\u0005^\u0011}C\u0011\rC2\tK\"9\u0007\"\u001b\u0016\u0005\u0011%#\u0006BAH\u00053\"QA\u001f\u001dC\u0002m$a!a\u00049\u0005\u0004YHABA\u000eq\t\u00071\u0010\u0002\u0004\u0002(a\u0012\ra\u001f\u0003\u0007\u0003gA$\u0019A>\u0005\r\u0005}\u0002H1\u0001|\t\u0019\tY\u0005\u000fb\u0001w\u00121\u0011q\u000b\u001dC\u0002m$a!a\u00199\u0005\u0004YHABA8q\t\u00071\u0010\u0002\u0004\u0002|a\u0012\ra\u001f\u0003\u0007\u0003\u000fC$\u0019A>\u0005\r\u0005M\u0005H1\u0001|\t\u0019\ty\n\u000fb\u0001w\u00121\u00111\u0016\u001dC\u0002m$a!a.9\u0005\u0004Y\u0018aD2paf$C-\u001a4bk2$H%\r\u001b\u0016E\u0011=D1\u000fC;\to\"I\bb\u001f\u0005~\u0011}D\u0011\u0011CB\t\u000b#9\t\"#\u0005\f\u00125Eq\u0012CI+\t!\tH\u000b\u0003\u0002\u001c\neC!\u0002>:\u0005\u0004YHABA\bs\t\u00071\u0010\u0002\u0004\u0002\u001ce\u0012\ra\u001f\u0003\u0007\u0003OI$\u0019A>\u0005\r\u0005M\u0012H1\u0001|\t\u0019\ty$\u000fb\u0001w\u00121\u00111J\u001dC\u0002m$a!a\u0016:\u0005\u0004YHABA2s\t\u00071\u0010\u0002\u0004\u0002pe\u0012\ra\u001f\u0003\u0007\u0003wJ$\u0019A>\u0005\r\u0005\u001d\u0015H1\u0001|\t\u0019\t\u0019*\u000fb\u0001w\u00121\u0011qT\u001dC\u0002m$a!a+:\u0005\u0004YHABA\\s\t\u000710A\bd_BLH\u0005Z3gCVdG\u000fJ\u00196+\t\"9\nb'\u0005\u001e\u0012}E\u0011\u0015CR\tK#9\u000b\"+\u0005,\u00125Fq\u0016CY\tg#)\fb.\u0005:V\u0011A\u0011\u0014\u0016\u0005\u0003O\u0013I\u0006B\u0003{u\t\u00071\u0010\u0002\u0004\u0002\u0010i\u0012\ra\u001f\u0003\u0007\u00037Q$\u0019A>\u0005\r\u0005\u001d\"H1\u0001|\t\u0019\t\u0019D\u000fb\u0001w\u00121\u0011q\b\u001eC\u0002m$a!a\u0013;\u0005\u0004YHABA,u\t\u00071\u0010\u0002\u0004\u0002di\u0012\ra\u001f\u0003\u0007\u0003_R$\u0019A>\u0005\r\u0005m$H1\u0001|\t\u0019\t9I\u000fb\u0001w\u00121\u00111\u0013\u001eC\u0002m$a!a(;\u0005\u0004YHABAVu\t\u00071\u0010\u0002\u0004\u00028j\u0012\ra_\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132mU\u0011Cq\u0018Cb\t\u000b$9\r\"3\u0005L\u00125Gq\u001aCi\t'$)\u000eb6\u0005Z\u0012mGQ\u001cCp\tC,\"\u0001\"1+\t\u0005M&\u0011\f\u0003\u0006un\u0012\ra\u001f\u0003\u0007\u0003\u001fY$\u0019A>\u0005\r\u0005m1H1\u0001|\t\u0019\t9c\u000fb\u0001w\u00121\u00111G\u001eC\u0002m$a!a\u0010<\u0005\u0004YHABA&w\t\u00071\u0010\u0002\u0004\u0002Xm\u0012\ra\u001f\u0003\u0007\u0003GZ$\u0019A>\u0005\r\u0005=4H1\u0001|\t\u0019\tYh\u000fb\u0001w\u00121\u0011qQ\u001eC\u0002m$a!a%<\u0005\u0004YHABAPw\t\u00071\u0010\u0002\u0004\u0002,n\u0012\ra\u001f\u0003\u0007\u0003o[$\u0019A>\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t!9\u000f\u0005\u0003\u0005j\u0012=XB\u0001Cv\u0015\r!ioW\u0001\u0005Y\u0006tw-\u0003\u0003\u0005r\u0012-(AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0005xB\u0019!\u000b\"?\n\u0007\u0011m8KA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002\u0000\u000b\u0003A\u0011\"b\u0001?\u0003\u0003\u0005\r\u0001b>\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t)I\u0001E\u0003\u0006\f\u0015Eq0\u0004\u0002\u0006\u000e)\u0019QqB*\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0006\u0014\u00155!\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!\"\u0007\u0006 A\u0019!+b\u0007\n\u0007\u0015u1KA\u0004C_>dW-\u00198\t\u0011\u0015\r\u0001)!AA\u0002}\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!Aq]C\u0013\u0011%)\u0019!QA\u0001\u0002\u0004!90\u0001\u0005iCND7i\u001c3f)\t!90\u0001\u0005u_N#(/\u001b8h)\t!9/\u0001\u0004fcV\fGn\u001d\u000b\u0005\u000b3)\u0019\u0004\u0003\u0005\u0006\u0004\u0011\u000b\t\u00111\u0001\u0000Q\u001d9QqGC\u001f\u000b\u007f\u00012AUC\u001d\u0013\r)Yd\u0015\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001\t\u0004q\u0016\rC!\u0002>\u0004\u0005\u0004Y\bc\u0001=\u0006H\u00111\u0011qB\u0002C\u0002m\u00042\u0001_C&\t\u0019\tYb\u0001b\u0001wB\u0019\u00010b\u0014\u0005\r\u0005\u001d2A1\u0001|!\rAX1\u000b\u0003\u0007\u0003g\u0019!\u0019A>\u0011\u0007a,9\u0006\u0002\u0004\u0002@\r\u0011\ra\u001f\t\u0004q\u0016mCABA&\u0007\t\u00071\u0010E\u0002y\u000b?\"a!a\u0016\u0004\u0005\u0004Y\bc\u0001=\u0006d\u00111\u00111M\u0002C\u0002m\u00042\u0001_C4\t\u0019\tyg\u0001b\u0001wB\u0019\u00010b\u001b\u0005\r\u0005m4A1\u0001|!\rAXq\u000e\u0003\u0007\u0003\u000f\u001b!\u0019A>\u0011\u0007a,\u0019\b\u0002\u0004\u0002\u0014\u000e\u0011\ra\u001f\t\u0004q\u0016]DABAP\u0007\t\u00071\u0010E\u0002y\u000bw\"a!a+\u0004\u0005\u0004Y\bc\u0001=\u0006\u0000\u00111\u0011qW\u0002C\u0002mDa!^\u0002A\u0002\u0015\u0005\u0003bBA\u0004\u0007\u0001\u0007QQ\t\u0005\b\u0003'\u0019\u0001\u0019AC%\u0011\u001d\tyb\u0001a\u0001\u000b\u001bBq!a\u000b\u0004\u0001\u0004)\t\u0006C\u0004\u00028\r\u0001\r!\"\u0016\t\u000f\u0005\r3\u00011\u0001\u0006Z!9\u0011qJ\u0002A\u0002\u0015u\u0003bBA.\u0007\u0001\u0007Q\u0011\r\u0005\b\u0003O\u001a\u0001\u0019AC3\u0011\u001d\t\u0019h\u0001a\u0001\u000bSBq!a \u0004\u0001\u0004)i\u0007C\u0004\u0002\f\u000e\u0001\r!\"\u001d\t\u000f\u0005]5\u00011\u0001\u0006v!9\u00111U\u0002A\u0002\u0015e\u0004bBAX\u0007\u0001\u0007QQP\u0001\u0006CB\u0004H._\u000b#\u000bK+Y+b,\u00064\u0016]V1XC`\u000b\u0007,9-b3\u0006P\u0016MWq[Cn\u000b?,\u0019/b:\u0015E\u0015\u001dV\u0011^Cv\u000b[,y/\"=\u0006t\u0016UXq_C}\u000bw,i0b@\u0007\u0002\u0019\raQ\u0001D\u0004!\tru!\"+\u0006.\u0016EVQWC]\u000b{+\t-\"2\u0006J\u00165W\u0011[Ck\u000b3,i.\"9\u0006fB\u0019\u00010b+\u0005\u000bi$!\u0019A>\u0011\u0007a,y\u000b\u0002\u0004\u0002\u0010\u0011\u0011\ra\u001f\t\u0004q\u0016MFABA\u000e\t\t\u00071\u0010E\u0002y\u000bo#a!a\n\u0005\u0005\u0004Y\bc\u0001=\u0006<\u00121\u00111\u0007\u0003C\u0002m\u00042\u0001_C`\t\u0019\ty\u0004\u0002b\u0001wB\u0019\u00010b1\u0005\r\u0005-CA1\u0001|!\rAXq\u0019\u0003\u0007\u0003/\"!\u0019A>\u0011\u0007a,Y\r\u0002\u0004\u0002d\u0011\u0011\ra\u001f\t\u0004q\u0016=GABA8\t\t\u00071\u0010E\u0002y\u000b'$a!a\u001f\u0005\u0005\u0004Y\bc\u0001=\u0006X\u00121\u0011q\u0011\u0003C\u0002m\u00042\u0001_Cn\t\u0019\t\u0019\n\u0002b\u0001wB\u0019\u00010b8\u0005\r\u0005}EA1\u0001|!\rAX1\u001d\u0003\u0007\u0003W#!\u0019A>\u0011\u0007a,9\u000f\u0002\u0004\u00028\u0012\u0011\ra\u001f\u0005\u0007k\u0012\u0001\r!\"+\t\u000f\u0005\u001dA\u00011\u0001\u0006.\"9\u00111\u0003\u0003A\u0002\u0015E\u0006bBA\u0010\t\u0001\u0007QQ\u0017\u0005\b\u0003W!\u0001\u0019AC]\u0011\u001d\t9\u0004\u0002a\u0001\u000b{Cq!a\u0011\u0005\u0001\u0004)\t\rC\u0004\u0002P\u0011\u0001\r!\"2\t\u000f\u0005mC\u00011\u0001\u0006J\"9\u0011q\r\u0003A\u0002\u00155\u0007bBA:\t\u0001\u0007Q\u0011\u001b\u0005\b\u0003\u007f\"\u0001\u0019ACk\u0011\u001d\tY\t\u0002a\u0001\u000b3Dq!a&\u0005\u0001\u0004)i\u000eC\u0004\u0002$\u0012\u0001\r!\"9\t\u000f\u0005=F\u00011\u0001\u0006f\u00069QO\\1qa2LXC\tD\u0007\r31iB\"\t\u0007&\u0019%bQ\u0006D\u0019\rk1ID\"\u0010\u0007B\u0019\u0015c\u0011\nD'\r#2)\u0006\u0006\u0003\u0007\u0010\u0019]\u0003#\u0002*\u0007\u0012\u0019U\u0011b\u0001D\n'\n1q\n\u001d;j_:\u00042EUAs\r/1YBb\b\u0007$\u0019\u001db1\u0006D\u0018\rg19Db\u000f\u0007@\u0019\rcq\tD&\r\u001f2\u0019\u0006E\u0002y\r3!QA_\u0003C\u0002m\u00042\u0001\u001fD\u000f\t\u0019\ty!\u0002b\u0001wB\u0019\u0001P\"\t\u0005\r\u0005mQA1\u0001|!\rAhQ\u0005\u0003\u0007\u0003O)!\u0019A>\u0011\u0007a4I\u0003\u0002\u0004\u00024\u0015\u0011\ra\u001f\t\u0004q\u001a5BABA \u000b\t\u00071\u0010E\u0002y\rc!a!a\u0013\u0006\u0005\u0004Y\bc\u0001=\u00076\u00111\u0011qK\u0003C\u0002m\u00042\u0001\u001fD\u001d\t\u0019\t\u0019'\u0002b\u0001wB\u0019\u0001P\"\u0010\u0005\r\u0005=TA1\u0001|!\rAh\u0011\t\u0003\u0007\u0003w*!\u0019A>\u0011\u0007a4)\u0005\u0002\u0004\u0002\b\u0016\u0011\ra\u001f\t\u0004q\u001a%CABAJ\u000b\t\u00071\u0010E\u0002y\r\u001b\"a!a(\u0006\u0005\u0004Y\bc\u0001=\u0007R\u00111\u00111V\u0003C\u0002m\u00042\u0001\u001fD+\t\u0019\t9,\u0002b\u0001w\"Ia\u0011L\u0003\u0002\u0002\u0003\u0007a1L\u0001\u0004q\u0012\u0002\u0004C\t(\b\r/1YBb\b\u0007$\u0019\u001db1\u0006D\u0018\rg19Db\u000f\u0007@\u0019\rcq\tD&\r\u001f2\u0019&\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0007bA!A\u0011\u001eD2\u0013\u00111)\u0007b;\u0003\r=\u0013'.Z2u\u0001")
public final class Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
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
    private final T16 t16;
    private final scala.Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> toScala;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Option<scala.Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> unapply(Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> x$0) {
        return Tuple16$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
        return Tuple16$.MODULE$.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
        return Tuple16$.MODULE$.create(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
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

    public T16 t16() {
        return this.t16;
    }

    public scala.Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
        return new Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T10 copy$default$10() {
        return this.t10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T11 copy$default$11() {
        return this.t11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T12 copy$default$12() {
        return this.t12();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T13 copy$default$13() {
        return this.t13();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T14 copy$default$14() {
        return this.t14();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T15 copy$default$15() {
        return this.t15();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T16 copy$default$16() {
        return this.t16();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T6 copy$default$6() {
        return this.t6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T7 copy$default$7() {
        return this.t7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T8 copy$default$8() {
        return this.t8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T9 copy$default$9() {
        return this.t9();
    }

    @Override
    public String productPrefix() {
        return "Tuple16";
    }

    @Override
    public int productArity() {
        return 16;
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
            case 15: {
                return this.t16();
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
        return x$1 instanceof Tuple16;
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
            case 15: {
                return "t16";
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
        if (!(object instanceof Tuple16)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple16 tuple16 = (Tuple16)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple16.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple16.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple16.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple16.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple16.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple16.t6())) return false;
        if (!BoxesRunTime.equals(this.t7(), tuple16.t7())) return false;
        if (!BoxesRunTime.equals(this.t8(), tuple16.t8())) return false;
        if (!BoxesRunTime.equals(this.t9(), tuple16.t9())) return false;
        if (!BoxesRunTime.equals(this.t10(), tuple16.t10())) return false;
        if (!BoxesRunTime.equals(this.t11(), tuple16.t11())) return false;
        if (!BoxesRunTime.equals(this.t12(), tuple16.t12())) return false;
        if (!BoxesRunTime.equals(this.t13(), tuple16.t13())) return false;
        if (!BoxesRunTime.equals(this.t14(), tuple16.t14())) return false;
        if (!BoxesRunTime.equals(this.t15(), tuple16.t15())) return false;
        if (!BoxesRunTime.equals(this.t16(), tuple16.t16())) return false;
        return true;
    }

    public Tuple16(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16) {
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
        this.t16 = t16;
        Product.$init$(this);
        this.toScala = new scala.Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
    }
}

