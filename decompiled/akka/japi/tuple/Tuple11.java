/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple11$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011}s!\u0002\u001c8\u0011\u0003qd!\u0002!8\u0011\u0003\t\u0005\"\u0002)\u0002\t\u0003\t\u0006\"\u0002*\u0002\t\u0003\u0019\u0006\"CBf\u0003\u0005\u0005I\u0011QBg\u0011%!)\"AA\u0001\n\u0003#9\u0002C\u0005\u0005V\u0005\t\t\u0011\"\u0003\u0005X\u0019!\u0001i\u000e\"W\u0011!1wA!f\u0001\n\u00039\u0007\u0002C:\b\u0005#\u0005\u000b\u0011\u00025\t\u0011Q<!Q3A\u0005\u0002UD\u0001\"_\u0004\u0003\u0012\u0003\u0006IA\u001e\u0005\tu\u001e\u0011)\u001a!C\u0001w\"Aqp\u0002B\tB\u0003%A\u0010\u0003\u0006\u0002\u0002\u001d\u0011)\u001a!C\u0001\u0003\u0007A!\"a\u0003\b\u0005#\u0005\u000b\u0011BA\u0003\u0011)\tia\u0002BK\u0002\u0013\u0005\u0011q\u0002\u0005\u000b\u0003/9!\u0011#Q\u0001\n\u0005E\u0001BCA\r\u000f\tU\r\u0011\"\u0001\u0002\u001c!Q\u00111E\u0004\u0003\u0012\u0003\u0006I!!\b\t\u0015\u0005\u0015rA!f\u0001\n\u0003\t9\u0003\u0003\u0006\u00020\u001d\u0011\t\u0012)A\u0005\u0003SA!\"!\r\b\u0005+\u0007I\u0011AA\u001a\u0011)\tYd\u0002B\tB\u0003%\u0011Q\u0007\u0005\u000b\u0003{9!Q3A\u0005\u0002\u0005}\u0002BCA$\u000f\tE\t\u0015!\u0003\u0002B!Q\u0011\u0011J\u0004\u0003\u0016\u0004%\t!a\u0013\t\u0015\u0005MsA!E!\u0002\u0013\ti\u0005\u0003\u0006\u0002V\u001d\u0011)\u001a!C\u0001\u0003/B!\"a\u0018\b\u0005#\u0005\u000b\u0011BA-\u0011\u0019\u0001v\u0001\"\u0001\u0002b!I\u00111P\u0004C\u0002\u0013\u0005\u0011Q\u0010\u0005\t\u0003\u0007;\u0001\u0015!\u0003\u0002\u0000!I\u0011QQ\u0004\u0002\u0002\u0013\u0005\u0011q\u0011\u0005\n\u0003\u001f<\u0011\u0013!C\u0001\u0003#D\u0011\"a@\b#\u0003%\tA!\u0001\t\u0013\tuq!%A\u0005\u0002\t}\u0001\"\u0003B\u001e\u000fE\u0005I\u0011\u0001B\u001f\u0011%\u0011IfBI\u0001\n\u0003\u0011Y\u0006C\u0005\u0003x\u001d\t\n\u0011\"\u0001\u0003z!I!QS\u0004\u0012\u0002\u0013\u0005!q\u0013\u0005\n\u0005g;\u0011\u0013!C\u0001\u0005kC\u0011B!5\b#\u0003%\tAa5\t\u0013\t=x!%A\u0005\u0002\tE\b\"CB\u0007\u000fE\u0005I\u0011AB\b\u0011%\u0019YcBA\u0001\n\u0003\u001ai\u0003C\u0005\u0004<\u001d\t\t\u0011\"\u0001\u0004>!I1QI\u0004\u0002\u0002\u0013\u00051q\t\u0005\n\u0007\u001b:\u0011\u0011!C!\u0007\u001fB\u0011b!\u0018\b\u0003\u0003%\taa\u0018\t\u0013\r%t!!A\u0005B\r-\u0004\"CB8\u000f\u0005\u0005I\u0011IB9\u0011%\u0019\u0019hBA\u0001\n\u0003\u001a)\bC\u0005\u0004x\u001d\t\t\u0011\"\u0011\u0004z\u00059A+\u001e9mKF\n$B\u0001\u001d:\u0003\u0015!X\u000f\u001d7f\u0015\tQ4(\u0001\u0003kCBL'\"\u0001\u001f\u0002\t\u0005\\7.Y\u0002\u0001!\ty\u0014!D\u00018\u0005\u001d!V\u000f\u001d7fcE\u001a2!\u0001\"I!\t\u0019e)D\u0001E\u0015\u0005)\u0015!B:dC2\f\u0017BA$E\u0005\u0019\te.\u001f*fMB\u0011\u0011JT\u0007\u0002\u0015*\u00111\nT\u0001\u0003S>T\u0011!T\u0001\u0005U\u00064\u0018-\u0003\u0002P\u0015\na1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012AP\u0001\u0007GJ,\u0017\r^3\u0016/Q\u001bYia$\u0004\u0014\u000e]51TBP\u0007G\u001b9ka+\u00040\u000eMFcF+\u00046\u000e]6\u0011XB^\u0007{\u001byl!1\u0004D\u000e\u00157qYBe!ayta!#\u0004\u000e\u000eE5QSBM\u0007;\u001b\tk!*\u0004*\u000e56\u0011W\u000b\u0015/*<X0a\u0002\u0002\u0014\u0005}\u00111FA\u001c\u0003\u0007\ny%a\u0017\u0014\t\u001d\u0011\u0005l\u0017\t\u0003\u0007fK!A\u0017#\u0003\u000fA\u0013x\u000eZ;diB\u0011A\f\u001a\b\u0003;\nt!AX1\u000e\u0003}S!\u0001Y\u001f\u0002\rq\u0012xn\u001c;?\u0013\u0005)\u0015BA2E\u0003\u001d\u0001\u0018mY6bO\u0016L!aT3\u000b\u0005\r$\u0015A\u0001;2+\u0005A\u0007CA5k\u0019\u0001!Qa[\u0004C\u00021\u0014!\u0001V\u0019\u0012\u00055\u0004\bCA\"o\u0013\tyGIA\u0004O_RD\u0017N\\4\u0011\u0005\r\u000b\u0018B\u0001:E\u0005\r\te._\u0001\u0004iF\u0002\u0013A\u0001;3+\u00051\bCA5x\t\u0015AxA1\u0001m\u0005\t!&'A\u0002ue\u0001\n!\u0001^\u001a\u0016\u0003q\u0004\"![?\u0005\u000by<!\u0019\u00017\u0003\u0005Q\u001b\u0014a\u0001;4A\u0005\u0011A\u000fN\u000b\u0003\u0003\u000b\u00012![A\u0004\t\u0019\tIa\u0002b\u0001Y\n\u0011A\u000bN\u0001\u0004iR\u0002\u0013A\u0001;6+\t\t\t\u0002E\u0002j\u0003'!a!!\u0006\b\u0005\u0004a'A\u0001+6\u0003\r!X\u0007I\u0001\u0003iZ*\"!!\b\u0011\u0007%\fy\u0002\u0002\u0004\u0002\"\u001d\u0011\r\u0001\u001c\u0002\u0003)Z\n1\u0001\u001e\u001c!\u0003\t!x'\u0006\u0002\u0002*A\u0019\u0011.a\u000b\u0005\r\u00055rA1\u0001m\u0005\t!v'A\u0002uo\u0001\n!\u0001\u001e\u001d\u0016\u0005\u0005U\u0002cA5\u00028\u00111\u0011\u0011H\u0004C\u00021\u0014!\u0001\u0016\u001d\u0002\u0007QD\u0004%\u0001\u0002usU\u0011\u0011\u0011\t\t\u0004S\u0006\rCABA#\u000f\t\u0007AN\u0001\u0002Us\u0005\u0019A/\u000f\u0011\u0002\u0007Q\f\u0004'\u0006\u0002\u0002NA\u0019\u0011.a\u0014\u0005\r\u0005EsA1\u0001m\u0005\r!\u0016\u0007M\u0001\u0005iF\u0002\u0004%A\u0002ucE*\"!!\u0017\u0011\u0007%\fY\u0006\u0002\u0004\u0002^\u001d\u0011\r\u0001\u001c\u0002\u0004)F\n\u0014\u0001\u0002;2c\u0001\"\u0002$a\u0019\u0002f\u0005\u001d\u0014\u0011NA6\u0003[\ny'!\u001d\u0002t\u0005U\u0014qOA=!Uyt\u0001\u001b<}\u0003\u000b\t\t\"!\b\u0002*\u0005U\u0012\u0011IA'\u00033BQA\u001a\u0010A\u0002!DQ\u0001\u001e\u0010A\u0002YDQA\u001f\u0010A\u0002qDq!!\u0001\u001f\u0001\u0004\t)\u0001C\u0004\u0002\u000ey\u0001\r!!\u0005\t\u000f\u0005ea\u00041\u0001\u0002\u001e!9\u0011Q\u0005\u0010A\u0002\u0005%\u0002bBA\u0019=\u0001\u0007\u0011Q\u0007\u0005\b\u0003{q\u0002\u0019AA!\u0011\u001d\tIE\ba\u0001\u0003\u001bBq!!\u0016\u001f\u0001\u0004\tI&A\u0004u_N\u001b\u0017\r\\1\u0016\u0005\u0005}\u0004CF\"\u0002\u0002\"4H0!\u0002\u0002\u0012\u0005u\u0011\u0011FA\u001b\u0003\u0003\ni%!\u0017\n\u0005\u0001#\u0015\u0001\u0003;p'\u000e\fG.\u0019\u0011\u0002\t\r|\u0007/_\u000b\u0019\u0003\u0013\u000by)a%\u0002\u0018\u0006m\u0015qTAR\u0003O\u000bY+a,\u00024\u0006]F\u0003GAF\u0003s\u000bY,!0\u0002@\u0006\u0005\u00171YAc\u0003\u000f\fI-a3\u0002NBArhBAG\u0003#\u000b)*!'\u0002\u001e\u0006\u0005\u0016QUAU\u0003[\u000b\t,!.\u0011\u0007%\fy\tB\u0003lC\t\u0007A\u000eE\u0002j\u0003'#Q\u0001_\u0011C\u00021\u00042![AL\t\u0015q\u0018E1\u0001m!\rI\u00171\u0014\u0003\u0007\u0003\u0013\t#\u0019\u00017\u0011\u0007%\fy\n\u0002\u0004\u0002\u0016\u0005\u0012\r\u0001\u001c\t\u0004S\u0006\rFABA\u0011C\t\u0007A\u000eE\u0002j\u0003O#a!!\f\"\u0005\u0004a\u0007cA5\u0002,\u00121\u0011\u0011H\u0011C\u00021\u00042![AX\t\u0019\t)%\tb\u0001YB\u0019\u0011.a-\u0005\r\u0005E\u0013E1\u0001m!\rI\u0017q\u0017\u0003\u0007\u0003;\n#\u0019\u00017\t\u0011\u0019\f\u0003\u0013!a\u0001\u0003\u001bC\u0001\u0002^\u0011\u0011\u0002\u0003\u0007\u0011\u0011\u0013\u0005\tu\u0006\u0002\n\u00111\u0001\u0002\u0016\"I\u0011\u0011A\u0011\u0011\u0002\u0003\u0007\u0011\u0011\u0014\u0005\n\u0003\u001b\t\u0003\u0013!a\u0001\u0003;C\u0011\"!\u0007\"!\u0003\u0005\r!!)\t\u0013\u0005\u0015\u0012\u0005%AA\u0002\u0005\u0015\u0006\"CA\u0019CA\u0005\t\u0019AAU\u0011%\ti$\tI\u0001\u0002\u0004\ti\u000bC\u0005\u0002J\u0005\u0002\n\u00111\u0001\u00022\"I\u0011QK\u0011\u0011\u0002\u0003\u0007\u0011QW\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+a\t\u0019.!;\u0002l\u00065\u0018q^Ay\u0003g\f)0a>\u0002z\u0006m\u0018Q`\u000b\u0003\u0003+T3\u0001[AlW\t\tI\u000e\u0005\u0003\u0002\\\u0006\u0015XBAAo\u0015\u0011\ty.!9\u0002\u0013Ut7\r[3dW\u0016$'bAAr\t\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\u001d\u0018Q\u001c\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G!B6#\u0005\u0004aG!\u0002=#\u0005\u0004aG!\u0002@#\u0005\u0004aGABA\u0005E\t\u0007A\u000e\u0002\u0004\u0002\u0016\t\u0012\r\u0001\u001c\u0003\u0007\u0003C\u0011#\u0019\u00017\u0005\r\u00055\"E1\u0001m\t\u0019\tID\tb\u0001Y\u00121\u0011Q\t\u0012C\u00021$a!!\u0015#\u0005\u0004aGABA/E\t\u0007A.\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u00161\t\r!q\u0001B\u0005\u0005\u0017\u0011iAa\u0004\u0003\u0012\tM!Q\u0003B\f\u00053\u0011Y\"\u0006\u0002\u0003\u0006)\u001aa/a6\u0005\u000b-\u001c#\u0019\u00017\u0005\u000ba\u001c#\u0019\u00017\u0005\u000by\u001c#\u0019\u00017\u0005\r\u0005%1E1\u0001m\t\u0019\t)b\tb\u0001Y\u00121\u0011\u0011E\u0012C\u00021$a!!\f$\u0005\u0004aGABA\u001dG\t\u0007A\u000e\u0002\u0004\u0002F\r\u0012\r\u0001\u001c\u0003\u0007\u0003#\u001a#\u0019\u00017\u0005\r\u0005u3E1\u0001m\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\u0002D!\t\u0003&\t\u001d\"\u0011\u0006B\u0016\u0005[\u0011yC!\r\u00034\tU\"q\u0007B\u001d+\t\u0011\u0019CK\u0002}\u0003/$Qa\u001b\u0013C\u00021$Q\u0001\u001f\u0013C\u00021$QA \u0013C\u00021$a!!\u0003%\u0005\u0004aGABA\u000bI\t\u0007A\u000e\u0002\u0004\u0002\"\u0011\u0012\r\u0001\u001c\u0003\u0007\u0003[!#\u0019\u00017\u0005\r\u0005eBE1\u0001m\t\u0019\t)\u0005\nb\u0001Y\u00121\u0011\u0011\u000b\u0013C\u00021$a!!\u0018%\u0005\u0004a\u0017AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0019\u0005\u007f\u0011\u0019E!\u0012\u0003H\t%#1\nB'\u0005\u001f\u0012\tFa\u0015\u0003V\t]SC\u0001B!U\u0011\t)!a6\u0005\u000b-,#\u0019\u00017\u0005\u000ba,#\u0019\u00017\u0005\u000by,#\u0019\u00017\u0005\r\u0005%QE1\u0001m\t\u0019\t)\"\nb\u0001Y\u00121\u0011\u0011E\u0013C\u00021$a!!\f&\u0005\u0004aGABA\u001dK\t\u0007A\u000e\u0002\u0004\u0002F\u0015\u0012\r\u0001\u001c\u0003\u0007\u0003#*#\u0019\u00017\u0005\r\u0005uSE1\u0001m\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIU*\u0002D!\u0018\u0003b\t\r$Q\rB4\u0005S\u0012YG!\u001c\u0003p\tE$1\u000fB;+\t\u0011yF\u000b\u0003\u0002\u0012\u0005]G!B6'\u0005\u0004aG!\u0002='\u0005\u0004aG!\u0002@'\u0005\u0004aGABA\u0005M\t\u0007A\u000e\u0002\u0004\u0002\u0016\u0019\u0012\r\u0001\u001c\u0003\u0007\u0003C1#\u0019\u00017\u0005\r\u00055bE1\u0001m\t\u0019\tID\nb\u0001Y\u00121\u0011Q\t\u0014C\u00021$a!!\u0015'\u0005\u0004aGABA/M\t\u0007A.\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001c\u00161\tm$q\u0010BA\u0005\u0007\u0013)Ia\"\u0003\n\n-%Q\u0012BH\u0005#\u0013\u0019*\u0006\u0002\u0003~)\"\u0011QDAl\t\u0015YwE1\u0001m\t\u0015AxE1\u0001m\t\u0015qxE1\u0001m\t\u0019\tIa\nb\u0001Y\u00121\u0011QC\u0014C\u00021$a!!\t(\u0005\u0004aGABA\u0017O\t\u0007A\u000e\u0002\u0004\u0002:\u001d\u0012\r\u0001\u001c\u0003\u0007\u0003\u000b:#\u0019\u00017\u0005\r\u0005EsE1\u0001m\t\u0019\tif\nb\u0001Y\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012:T\u0003\u0007BM\u0005;\u0013yJ!)\u0003$\n\u0015&q\u0015BU\u0005W\u0013iKa,\u00032V\u0011!1\u0014\u0016\u0005\u0003S\t9\u000eB\u0003lQ\t\u0007A\u000eB\u0003yQ\t\u0007A\u000eB\u0003\u007fQ\t\u0007A\u000e\u0002\u0004\u0002\n!\u0012\r\u0001\u001c\u0003\u0007\u0003+A#\u0019\u00017\u0005\r\u0005\u0005\u0002F1\u0001m\t\u0019\ti\u0003\u000bb\u0001Y\u00121\u0011\u0011\b\u0015C\u00021$a!!\u0012)\u0005\u0004aGABA)Q\t\u0007A\u000e\u0002\u0004\u0002^!\u0012\r\u0001\\\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139+a\u00119La/\u0003>\n}&\u0011\u0019Bb\u0005\u000b\u00149M!3\u0003L\n5'qZ\u000b\u0003\u0005sSC!!\u000e\u0002X\u0012)1.\u000bb\u0001Y\u0012)\u00010\u000bb\u0001Y\u0012)a0\u000bb\u0001Y\u00121\u0011\u0011B\u0015C\u00021$a!!\u0006*\u0005\u0004aGABA\u0011S\t\u0007A\u000e\u0002\u0004\u0002.%\u0012\r\u0001\u001c\u0003\u0007\u0003sI#\u0019\u00017\u0005\r\u0005\u0015\u0013F1\u0001m\t\u0019\t\t&\u000bb\u0001Y\u00121\u0011QL\u0015C\u00021\fabY8qs\u0012\"WMZ1vYR$\u0013(\u0006\r\u0003V\ne'1\u001cBo\u0005?\u0014\tOa9\u0003f\n\u001d(\u0011\u001eBv\u0005[,\"Aa6+\t\u0005\u0005\u0013q\u001b\u0003\u0006W*\u0012\r\u0001\u001c\u0003\u0006q*\u0012\r\u0001\u001c\u0003\u0006}*\u0012\r\u0001\u001c\u0003\u0007\u0003\u0013Q#\u0019\u00017\u0005\r\u0005U!F1\u0001m\t\u0019\t\tC\u000bb\u0001Y\u00121\u0011Q\u0006\u0016C\u00021$a!!\u000f+\u0005\u0004aGABA#U\t\u0007A\u000e\u0002\u0004\u0002R)\u0012\r\u0001\u001c\u0003\u0007\u0003;R#\u0019\u00017\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cA*\u0002Da=\u0003x\ne(1 B\u007f\u0005\u007f\u001c\taa\u0001\u0004\u0006\r\u001d1\u0011BB\u0006+\t\u0011)P\u000b\u0003\u0002N\u0005]G!B6,\u0005\u0004aG!\u0002=,\u0005\u0004aG!\u0002@,\u0005\u0004aGABA\u0005W\t\u0007A\u000e\u0002\u0004\u0002\u0016-\u0012\r\u0001\u001c\u0003\u0007\u0003CY#\u0019\u00017\u0005\r\u000552F1\u0001m\t\u0019\tId\u000bb\u0001Y\u00121\u0011QI\u0016C\u00021$a!!\u0015,\u0005\u0004aGABA/W\t\u0007A.A\bd_BLH\u0005Z3gCVdG\u000fJ\u00192+a\u0019\tb!\u0006\u0004\u0018\re11DB\u000f\u0007?\u0019\tca\t\u0004&\r\u001d2\u0011F\u000b\u0003\u0007'QC!!\u0017\u0002X\u0012)1\u000e\fb\u0001Y\u0012)\u0001\u0010\fb\u0001Y\u0012)a\u0010\fb\u0001Y\u00121\u0011\u0011\u0002\u0017C\u00021$a!!\u0006-\u0005\u0004aGABA\u0011Y\t\u0007A\u000e\u0002\u0004\u0002.1\u0012\r\u0001\u001c\u0003\u0007\u0003sa#\u0019\u00017\u0005\r\u0005\u0015CF1\u0001m\t\u0019\t\t\u0006\fb\u0001Y\u00121\u0011Q\f\u0017C\u00021\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAB\u0018!\u0011\u0019\tda\u000e\u000e\u0005\rM\"bAB\u001b\u0019\u0006!A.\u00198h\u0013\u0011\u0019Ida\r\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\u0019y\u0004E\u0002D\u0007\u0003J1aa\u0011E\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r\u00018\u0011\n\u0005\n\u0007\u0017z\u0013\u0011!a\u0001\u0007\u007f\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAB)!\u0015\u0019\u0019f!\u0017q\u001b\t\u0019)FC\u0002\u0004X\u0011\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\u0019Yf!\u0016\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0007C\u001a9\u0007E\u0002D\u0007GJ1a!\u001aE\u0005\u001d\u0011un\u001c7fC:D\u0001ba\u00132\u0003\u0003\u0005\r\u0001]\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u00040\r5\u0004\"CB&e\u0005\u0005\t\u0019AB \u0003!A\u0017m\u001d5D_\u0012,GCAB \u0003!!xn\u0015;sS:<GCAB\u0018\u0003\u0019)\u0017/^1mgR!1\u0011MB>\u0011!\u0019Y%NA\u0001\u0002\u0004\u0001\bfB\u0004\u0004\u0000\r\u00155q\u0011\t\u0004\u0007\u000e\u0005\u0015bABB\t\n\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003A\u0019\u0011na#\u0005\u000b-\u001c!\u0019\u00017\u0011\u0007%\u001cy\tB\u0003y\u0007\t\u0007A\u000eE\u0002j\u0007'#QA`\u0002C\u00021\u00042![BL\t\u0019\tIa\u0001b\u0001YB\u0019\u0011na'\u0005\r\u0005U1A1\u0001m!\rI7q\u0014\u0003\u0007\u0003C\u0019!\u0019\u00017\u0011\u0007%\u001c\u0019\u000b\u0002\u0004\u0002.\r\u0011\r\u0001\u001c\t\u0004S\u000e\u001dFABA\u001d\u0007\t\u0007A\u000eE\u0002j\u0007W#a!!\u0012\u0004\u0005\u0004a\u0007cA5\u00040\u00121\u0011\u0011K\u0002C\u00021\u00042![BZ\t\u0019\tif\u0001b\u0001Y\"1am\u0001a\u0001\u0007\u0013Ca\u0001^\u0002A\u0002\r5\u0005B\u0002>\u0004\u0001\u0004\u0019\t\nC\u0004\u0002\u0002\r\u0001\ra!&\t\u000f\u000551\u00011\u0001\u0004\u001a\"9\u0011\u0011D\u0002A\u0002\ru\u0005bBA\u0013\u0007\u0001\u00071\u0011\u0015\u0005\b\u0003c\u0019\u0001\u0019ABS\u0011\u001d\tid\u0001a\u0001\u0007SCq!!\u0013\u0004\u0001\u0004\u0019i\u000bC\u0004\u0002V\r\u0001\ra!-\u0002\u000b\u0005\u0004\b\u000f\\=\u00161\r=7Q[Bm\u0007;\u001c\to!:\u0004j\u000e58\u0011_B{\u0007s\u001ci\u0010\u0006\r\u0004R\u000e}H\u0011\u0001C\u0002\t\u000b!9\u0001\"\u0003\u0005\f\u00115Aq\u0002C\t\t'\u0001\u0002dP\u0004\u0004T\u000e]71\\Bp\u0007G\u001c9oa;\u0004p\u000eM8q_B~!\rI7Q\u001b\u0003\u0006W\u0012\u0011\r\u0001\u001c\t\u0004S\u000eeG!\u0002=\u0005\u0005\u0004a\u0007cA5\u0004^\u0012)a\u0010\u0002b\u0001YB\u0019\u0011n!9\u0005\r\u0005%AA1\u0001m!\rI7Q\u001d\u0003\u0007\u0003+!!\u0019\u00017\u0011\u0007%\u001cI\u000f\u0002\u0004\u0002\"\u0011\u0011\r\u0001\u001c\t\u0004S\u000e5HABA\u0017\t\t\u0007A\u000eE\u0002j\u0007c$a!!\u000f\u0005\u0005\u0004a\u0007cA5\u0004v\u00121\u0011Q\t\u0003C\u00021\u00042![B}\t\u0019\t\t\u0006\u0002b\u0001YB\u0019\u0011n!@\u0005\r\u0005uCA1\u0001m\u0011\u00191G\u00011\u0001\u0004T\"1A\u000f\u0002a\u0001\u0007/DaA\u001f\u0003A\u0002\rm\u0007bBA\u0001\t\u0001\u00071q\u001c\u0005\b\u0003\u001b!\u0001\u0019ABr\u0011\u001d\tI\u0002\u0002a\u0001\u0007ODq!!\n\u0005\u0001\u0004\u0019Y\u000fC\u0004\u00022\u0011\u0001\raa<\t\u000f\u0005uB\u00011\u0001\u0004t\"9\u0011\u0011\n\u0003A\u0002\r]\bbBA+\t\u0001\u000711`\u0001\bk:\f\u0007\u000f\u001d7z+a!I\u0002\"\n\u0005*\u00115B\u0011\u0007C\u001b\ts!i\u0004\"\u0011\u0005F\u0011%CQ\n\u000b\u0005\t7!y\u0005E\u0003D\t;!\t#C\u0002\u0005 \u0011\u0013aa\u00149uS>t\u0007#G\"\u0002\u0002\u0012\rBq\u0005C\u0016\t_!\u0019\u0004b\u000e\u0005<\u0011}B1\tC$\t\u0017\u00022!\u001bC\u0013\t\u0015YWA1\u0001m!\rIG\u0011\u0006\u0003\u0006q\u0016\u0011\r\u0001\u001c\t\u0004S\u00125B!\u0002@\u0006\u0005\u0004a\u0007cA5\u00052\u00111\u0011\u0011B\u0003C\u00021\u00042!\u001bC\u001b\t\u0019\t)\"\u0002b\u0001YB\u0019\u0011\u000e\"\u000f\u0005\r\u0005\u0005RA1\u0001m!\rIGQ\b\u0003\u0007\u0003[)!\u0019\u00017\u0011\u0007%$\t\u0005\u0002\u0004\u0002:\u0015\u0011\r\u0001\u001c\t\u0004S\u0012\u0015CABA#\u000b\t\u0007A\u000eE\u0002j\t\u0013\"a!!\u0015\u0006\u0005\u0004a\u0007cA5\u0005N\u00111\u0011QL\u0003C\u00021D\u0011\u0002\"\u0015\u0006\u0003\u0003\u0005\r\u0001b\u0015\u0002\u0007a$\u0003\u0007\u0005\r@\u000f\u0011\rBq\u0005C\u0016\t_!\u0019\u0004b\u000e\u0005<\u0011}B1\tC$\t\u0017\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"\u0001\"\u0017\u0011\t\rEB1L\u0005\u0005\t;\u001a\u0019D\u0001\u0004PE*,7\r\u001e")
public final class Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>
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
    private final scala.Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> toScala;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Option<scala.Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> unapply(Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> x$0) {
        return Tuple11$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
        return Tuple11$.MODULE$.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
        return Tuple11$.MODULE$.create(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
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

    public scala.Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
        return new Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T10 copy$default$10() {
        return this.t10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T11 copy$default$11() {
        return this.t11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T6 copy$default$6() {
        return this.t6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T7 copy$default$7() {
        return this.t7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T8 copy$default$8() {
        return this.t8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> T9 copy$default$9() {
        return this.t9();
    }

    @Override
    public String productPrefix() {
        return "Tuple11";
    }

    @Override
    public int productArity() {
        return 11;
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
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Tuple11;
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
        if (!(object instanceof Tuple11)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple11 tuple11 = (Tuple11)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple11.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple11.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple11.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple11.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple11.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple11.t6())) return false;
        if (!BoxesRunTime.equals(this.t7(), tuple11.t7())) return false;
        if (!BoxesRunTime.equals(this.t8(), tuple11.t8())) return false;
        if (!BoxesRunTime.equals(this.t9(), tuple11.t9())) return false;
        if (!BoxesRunTime.equals(this.t10(), tuple11.t10())) return false;
        if (!BoxesRunTime.equals(this.t11(), tuple11.t11())) return false;
        return true;
    }

    public Tuple11(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11) {
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
        Product.$init$(this);
        this.toScala = new scala.Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
    }
}

