/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.PartialFunction$;
import scala.PartialFunction$ElementWiseExtractor$;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.AbstractFunction1;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\rEha\u0002%J!\u0003\r\t\u0001\u0014\u0005\u0006G\u0002!\t\u0001\u001a\u0005\u0006Q\u0002!\t!\u001b\u0005\u0006_\u0002!\t\u0001\u001d\u0005\b\u0003\u000b\u0004a\u0011ABA\u0011\u001d\t\t\u0010\u0001C\u0001\u0007\u000bCqAa\u0002\u0001\t\u0003\u001aI\nC\u0004\u0003\b\u0001!\taa*\t\u000f\rU\u0006\u0001\"\u0001\u00048\"91\u0011\u0003\u0001\u0005\u0002\r\u001d\u0007bBAj\u0001\u0011\u000511\u001a\u0005\b\u0007;\u0004A\u0011ABp\u000f\u0015\u0019\u0018\n#\u0001u\r\u0015A\u0015\n#\u0001v\u0011\u00151X\u0002\"\u0001x\r\u0011AXBA=\t\u0019y|A\u0011!A\u0003\u0006\u000b\u0007I\u0011B@\t\u0017\u0005-qB!B\u0001B\u0003%\u0011\u0011\u0001\u0005\bm>!\t!DA\u0007\u0011\u001d\t9b\u0004C\u0001\u00033A\u0011\"!\r\u0010\u0003\u0003%\t%a\r\t\u0013\u0005mr\"!A\u0005B\u0005ur!CA%\u001b\u0005\u0005\t\u0012AA&\r!AX\"!A\t\u0002\u00055\u0003B\u0002<\u0018\t\u0003\ty\u0005C\u0004\u0002R]!)!a\u0015\t\u0013\u0005=t#!A\u0005\u0006\u0005E\u0004\"CAA/\u0005\u0005IQAAB\r\u0019\t9*\u0004\u0003\u0002\u001a\"Q\u0011q\u0017\u000f\u0003\u0002\u0003\u0006I!!/\t\u0015\u0005mFD!A!\u0002\u0013\tI\f\u0003\u0004w9\u0011\u0005\u0011Q\u0018\u0005\b\u0003\u000bdB\u0011AAd\u0011\u001d\ti\r\bC!\u0003\u001fDq!a5\u001d\t\u0003\n)\u000eC\u0004\u0002rr!\t%a=\t\u000f\t\u001dA\u0004\"\u0011\u0003\n\u00191!1D\u0007\u0005\u0005;A!\"!\u0006&\u0005\u0003\u0005\u000b\u0011\u0002B\u0016\u0011)\u00119\"\nB\u0001B\u0003%!\u0011\u0007\u0005\u0007m\u0016\"\tAa\r\t\u000f\u0005\u0015W\u0005\"\u0001\u0003<!9\u0011QZ\u0013\u0005\u0002\t}\u0002bBAjK\u0011\u0005#1\t\u0004\u0007\u00057jAA!\u0018\t\u0015\u0005UAF!A!\u0002\u0013\u0011Y\u0007\u0003\u0006\u0003\u00181\u0012\t\u0011)A\u0005\u0005cBaA\u001e\u0017\u0005\u0002\tM\u0004bBAcY\u0011\u0005!1\u0010\u0005\b\u0003\u001bdC\u0011\u0001B@\u0011\u001d\t\u0019\u000e\fC!\u0005\u0007C\u0001B!'\u000eA\u0003%!1\u0014\u0005\b\u0005;kA\u0011\u0002BP\u0011\u001d\u0011I+\u0004C\u0005\u0005W3aA!.\u000e\t\t]\u0006BCA\u000bm\t\u0015\r\u0011\"\u0001\u0003L\"Q!q\u001a\u001c\u0003\u0002\u0003\u0006IA!4\t\rY4D\u0011\u0001Bi\u0011\u001d\tiM\u000eC\u0001\u0005/4aAa7\u000e\t\tu\u0007B\u0003Bvw\t\u0005\t\u0015!\u0003\u0003n\"1ao\u000fC\u0001\u0005cDq!!2<\t\u0003\u00119\u0010C\u0004\u0002Tn\"\tEa?\t\u000f\rE1\b\"\u0011\u0004\u0014!A1QC\u0007\u0005\u0002%\u001b9\u0002C\u0004\u0004,5!\ta!\f\t\u0011\r}R\u0002)A\u0005\u0007\u0003B\u0001ba\u0011\u000eA\u0003%1Q\t\u0005\b\u0007\u000fjA\u0011AB%\u0011\u001d\u00199&\u0004C\u0001\u00073Bqa!\u001b\u000e\t\u0003\u0019YGA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o\u0015\u0005Q\u0015!B:dC2\f7\u0001A\u000b\u0004\u001b^\u000b7c\u0001\u0001O%B\u0011q\nU\u0007\u0002\u0013&\u0011\u0011+\u0013\u0002\u0007\u0003:L(+\u001a4\u0011\t=\u001bV\u000bY\u0005\u0003)&\u0013\u0011BR;oGRLwN\\\u0019\u0011\u0005Y;F\u0002\u0001\u0003\u00071\u0002A)\u0019A-\u0003\u0003\u0005\u000b\"AW/\u0011\u0005=[\u0016B\u0001/J\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\u00140\n\u0005}K%aA!osB\u0011a+\u0019\u0003\u0007E\u0002!)\u0019A-\u0003\u0003\t\u000ba\u0001J5oSR$C#A3\u0011\u0005=3\u0017BA4J\u0005\u0011)f.\u001b;\u0002\u000fUt\u0017\r\u001d9msR\u0011!.\u001c\t\u0004\u001f.\u0004\u0017B\u00017J\u0005\u0019y\u0005\u000f^5p]\")aN\u0001a\u0001+\u0006\t\u0011-A\u0006fY\u0016lWM\u001c;XSN,W#A9\u0011\tI|Q\u000b\u0019\b\u0003\u001f2\tq\u0002U1si&\fGNR;oGRLwN\u001c\t\u0003\u001f6\u0019\"!\u0004(\u0002\rqJg.\u001b;?)\u0005!(\u0001F#mK6,g\u000e^,jg\u0016,\u0005\u0010\u001e:bGR|'/F\u0003{\u0003\u000b\tIa\u0005\u0002\u0010wB\u0011q\n`\u0005\u0003{&\u0013a!\u00118z-\u0006d\u0017AL:dC2\fG\u0005U1si&\fGNR;oGRLwN\u001c\u0013FY\u0016lWM\u001c;XSN,W\t\u001f;sC\u000e$xN\u001d\u0013%a\u001a,\"!!\u0001\u0011\r=\u0003\u00111AA\u0004!\r1\u0016Q\u0001\u0003\u00071>A)\u0019A-\u0011\u0007Y\u000bI\u0001\u0002\u0004c\u001f\u0011\u0015\r!W\u00010g\u000e\fG.\u0019\u0013QCJ$\u0018.\u00197Gk:\u001cG/[8oI\u0015cW-\\3oi^K7/Z#yiJ\f7\r^8sI\u0011\u0002h\r\t\u000b\u0005\u0003\u001f\t\u0019\u0002E\u0004\u0002\u0012=\t\u0019!a\u0002\u000e\u00035Aq!!\u0006\u0013\u0001\u0004\t\t!\u0001\u0002qM\u0006QQO\\1qa2L8+Z9\u0015\t\u0005m\u00111\u0006\t\u0005\u001f.\fi\u0002\u0005\u0004\u0002 \u0005\u0015\u0012q\u0001\b\u0004\u001f\u0006\u0005\u0012bAA\u0012\u0013\u00069\u0001/Y2lC\u001e,\u0017\u0002BA\u0014\u0003S\u00111aU3r\u0015\r\t\u0019#\u0013\u0005\b\u0003[\u0019\u0002\u0019AA\u0018\u0003\r\u0019X-\u001d\t\u0007\u0003?\t)#a\u0001\u0002\u0011!\f7\u000f[\"pI\u0016$\"!!\u000e\u0011\u0007=\u000b9$C\u0002\u0002:%\u00131!\u00138u\u0003\u0019)\u0017/^1mgR!\u0011qHA#!\ry\u0015\u0011I\u0005\u0004\u0003\u0007J%a\u0002\"p_2,\u0017M\u001c\u0005\t\u0003\u000f*\u0012\u0011!a\u0001;\u0006\u0019\u0001\u0010J\u0019\u0002)\u0015cW-\\3oi^K7/Z#yiJ\f7\r^8s!\r\t\tbF\n\u0003/9#\"!a\u0013\u0002)Ut\u0017\r\u001d9msN+\u0017\u000fJ3yi\u0016t7/[8o+\u0019\t)&a\u001a\u0002`Q!\u0011qKA5)\u0011\tI&!\u0019\u0011\t=[\u00171\f\t\u0007\u0003?\t)#!\u0018\u0011\u0007Y\u000by\u0006B\u0003c3\t\u0007\u0011\fC\u0004\u0002.e\u0001\r!a\u0019\u0011\r\u0005}\u0011QEA3!\r1\u0016q\r\u0003\u00061f\u0011\r!\u0017\u0005\b\u0003WJ\u0002\u0019AA7\u0003\u0015!C\u000f[5t!\u001d\t\tbDA3\u0003;\n!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V1\u00111OA>\u0003\u007f\"B!a\r\u0002v!9\u00111\u000e\u000eA\u0002\u0005]\u0004cBA\t\u001f\u0005e\u0014Q\u0010\t\u0004-\u0006mD!\u0002-\u001b\u0005\u0004I\u0006c\u0001,\u0002\u0000\u0011)!M\u0007b\u00013\u0006\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0007\u0003\u000b\u000b\t*!&\u0015\t\u0005\u001d\u00151\u0012\u000b\u0005\u0003\u007f\tI\t\u0003\u0005\u0002Hm\t\t\u00111\u0001^\u0011\u001d\tYg\u0007a\u0001\u0003\u001b\u0003r!!\u0005\u0010\u0003\u001f\u000b\u0019\nE\u0002W\u0003##Q\u0001W\u000eC\u0002e\u00032AVAK\t\u0015\u00117D1\u0001Z\u0005\u0019y%/\u00127tKV1\u00111TAV\u0003_\u001bR\u0001HAO\u0003c\u0003\u0002\"a(\u0002&\u0006%\u0016QV\u0007\u0003\u0003CS1!a)J\u0003\u001d\u0011XO\u001c;j[\u0016LA!a*\u0002\"\n9\u0012IY:ue\u0006\u001cG\u000fU1si&\fGNR;oGRLwN\u001c\t\u0004-\u0006-FA\u0002-\u001d\u0011\u000b\u0007\u0011\fE\u0002W\u0003_#aA\u0019\u000f\u0005\u0006\u0004I\u0006\u0003BA\u0010\u0003gKA!!.\u0002*\ta1+\u001a:jC2L'0\u00192mK\u0006\u0011a-\r\t\u0007\u001f\u0002\tI+!,\u0002\u0005\u0019\u0014DCBA`\u0003\u0003\f\u0019\rE\u0004\u0002\u0012q\tI+!,\t\u000f\u0005]v\u00041\u0001\u0002:\"9\u00111X\u0010A\u0002\u0005e\u0016aC5t\t\u00164\u0017N\\3e\u0003R$B!a\u0010\u0002J\"9\u00111\u001a\u0011A\u0002\u0005%\u0016!\u0001=\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\u00055\u0016\u0011\u001b\u0005\b\u0003\u0017\f\u0003\u0019AAU\u0003-\t\u0007\u000f\u001d7z\u001fJ,En]3\u0016\r\u0005]\u0017Q]An)\u0019\tI.!9\u0002lB\u0019a+a7\u0005\u000f\u0005u'E1\u0001\u0002`\n\u0011!)M\t\u0004\u0003[k\u0006bBAfE\u0001\u0007\u00111\u001d\t\u0004-\u0006\u0015HaBAtE\t\u0007\u0011\u0011\u001e\u0002\u0003\u0003F\n2AWAU\u0011\u001d\tiO\ta\u0001\u0003_\fq\u0001Z3gCVdG\u000f\u0005\u0004P'\u0006\r\u0018\u0011\\\u0001\u0007_J,En]3\u0016\r\u0005U\u00181`A\u0000)\u0011\t9P!\u0001\u0011\u000f\u0005EA$!?\u0002~B\u0019a+a?\u0005\u000f\u0005\u001d8E1\u0001\u0002jB\u0019a+a@\u0005\u000f\u0005u7E1\u0001\u0002`\"9!1A\u0012A\u0002\t\u0015\u0011\u0001\u0002;iCR\u0004ba\u0014\u0001\u0002z\u0006u\u0018aB1oIRCWM\\\u000b\u0005\u0005\u0017\u0011\t\u0002\u0006\u0003\u0003\u000e\tU\u0001cBA\t9\u0005%&q\u0002\t\u0004-\nEAA\u0002B\nI\t\u0007\u0011LA\u0001D\u0011\u001d\u00119\u0002\na\u0001\u00053\t\u0011a\u001b\t\u0007\u001fN\u000biKa\u0004\u0003\u000f\u0005sG\r\u00165f]VA!q\u0004B\u0013\u0005_\u0011Ic\u0005\u0004&\u001d\n\u0005\u0012\u0011\u0017\t\u0007\u001f\u0002\u0011\u0019Ca\n\u0011\u0007Y\u0013)\u0003\u0002\u0004YK!\u0015\r!\u0017\t\u0004-\n%Ba\u0002B\nK\u0011\u0015\r!\u0017\t\u0007\u001f\u0002\u0011\u0019C!\f\u0011\u0007Y\u0013y\u0003B\u0003cK\t\u0007\u0011\f\u0005\u0004P'\n5\"q\u0005\u000b\u0007\u0005k\u00119D!\u000f\u0011\u0013\u0005EQEa\t\u0003.\t\u001d\u0002bBA\u000bQ\u0001\u0007!1\u0006\u0005\b\u0005/A\u0003\u0019\u0001B\u0019)\u0011\tyD!\u0010\t\u000f\u0005-\u0017\u00061\u0001\u0003$Q!!q\u0005B!\u0011\u001d\tYM\u000ba\u0001\u0005G)bA!\u0012\u0003T\t%CC\u0002B$\u0005\u001f\u00129\u0006E\u0002W\u0005\u0013\"qAa\u0013,\u0005\u0004\u0011iE\u0001\u0002DcE\u0019!qE/\t\u000f\u0005-7\u00061\u0001\u0003RA\u0019aKa\u0015\u0005\u000f\u0005\u001d8F1\u0001\u0003VE\u0019!La\t\t\u000f\u000558\u00061\u0001\u0003ZA1qj\u0015B)\u0005\u000f\u0012\u0001bQ8nE&tW\rZ\u000b\t\u0005?\u0012)Ga\u001c\u0003jM1AF\u0014B1\u0003c\u0003ba\u0014\u0001\u0003d\t\u001d\u0004c\u0001,\u0003f\u00111\u0001\f\fEC\u0002e\u00032A\u0016B5\t\u001d\u0011\u0019\u0002\fCC\u0002e\u0003ba\u0014\u0001\u0003d\t5\u0004c\u0001,\u0003p\u0011)!\r\fb\u00013B1q\n\u0001B7\u0005O\"bA!\u001e\u0003x\te\u0004#CA\tY\t\r$Q\u000eB4\u0011\u001d\t)b\fa\u0001\u0005WBqAa\u00060\u0001\u0004\u0011\t\b\u0006\u0003\u0002@\tu\u0004bBAfa\u0001\u0007!1\r\u000b\u0005\u0005O\u0012\t\tC\u0004\u0002LF\u0002\rAa\u0019\u0016\r\t\u0015%\u0011\u0013BE)\u0019\u00119I!$\u0003\u0016B\u0019aK!#\u0005\u000f\t-#G1\u0001\u0003\fF\u0019!qM/\t\u000f\u0005-'\u00071\u0001\u0003\u0010B\u0019aK!%\u0005\u000f\u0005\u001d(G1\u0001\u0003\u0014F\u0019!La\u0019\t\u000f\u00055(\u00071\u0001\u0003\u0018B1qj\u0015BH\u0005\u000f\u000b1BZ1mY\n\f7m[0g]B!qjU/^\u00035\u0019\u0007.Z2l\r\u0006dGNY1dWV!!\u0011\u0015BT+\t\u0011\u0019\u000bE\u0003P'v\u0013)\u000bE\u0002W\u0005O#QA\u0019\u001bC\u0002e\u000b\u0001CZ1mY\n\f7m[(dGV\u0014(/\u001a3\u0016\t\t5&1\u0017\u000b\u0005\u0003\u007f\u0011y\u000bC\u0004\u0002LV\u0002\rA!-\u0011\u0007Y\u0013\u0019\fB\u0003ck\t\u0007\u0011L\u0001\u0004MS\u001a$X\rZ\u000b\u0007\u0005s\u0013\u0019M!3\u0014\u000bY\u0012Y,!-\u0011\u0011\u0005}%Q\u0018Ba\u0005\u000bLAAa0\u0002\"\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\u0007Y\u0013\u0019\r\u0002\u0004Ym!\u0015\r!\u0017\t\u0005\u001f.\u00149\rE\u0002W\u0005\u0013$aA\u0019\u001c\u0005\u0006\u0004IVC\u0001Bg!\u0019y\u0005A!1\u0003H\u0006\u0019\u0001O\u001a\u0011\u0015\t\tM'Q\u001b\t\b\u0003#1$\u0011\u0019Bd\u0011\u001d\t)\"\u000fa\u0001\u0005\u001b$BA!2\u0003Z\"9\u00111\u001a\u001eA\u0002\t\u0005'\u0001C+oY&4G/\u001a3\u0016\r\t}'Q\u001dBu'\u0015Y$\u0011]AY!!\ty*!*\u0003d\n\u001d\bc\u0001,\u0003f\u0012)\u0001l\u000fb\u00013B\u0019aK!;\u0005\u000b\t\\$\u0019A-\u0002\u0003\u0019\u0004baT*\u0003d\n=\b\u0003B(l\u0005O$BAa=\u0003vB9\u0011\u0011C\u001e\u0003d\n\u001d\bb\u0002Bv{\u0001\u0007!Q\u001e\u000b\u0005\u0003\u007f\u0011I\u0010C\u0004\u0002Lz\u0002\rAa9\u0016\r\tu8\u0011BB\u0001)\u0019\u0011yp!\u0002\u0004\u000eA\u0019ak!\u0001\u0005\u000f\u0005uwH1\u0001\u0004\u0004E\u0019!q]/\t\u000f\u0005-w\b1\u0001\u0004\bA\u0019ak!\u0003\u0005\u000f\u0005\u001dxH1\u0001\u0004\fE\u0019!La9\t\u000f\u00055x\b1\u0001\u0004\u0010A1qjUB\u0004\u0005\u007f\fA\u0001\\5giV\u0011!Q^\u0001\tk:d\u0017N\u001a;fIV11\u0011DB\u0010\u0007G!Baa\u0007\u0004&A1q\nAB\u000f\u0007C\u00012AVB\u0010\t\u0015A\u0016I1\u0001Z!\r161\u0005\u0003\u0006E\u0006\u0013\r!\u0017\u0005\b\u0005W\f\u0005\u0019AB\u0014!\u0019y5k!\b\u0004*A!qj[B\u0011\u000311'o\\7Gk:\u001cG/[8o+\u0019\u0019yc!\u000e\u0004:Q!1\u0011GB\u001e!\u0019y\u0005aa\r\u00048A\u0019ak!\u000e\u0005\u000ba\u0013%\u0019A-\u0011\u0007Y\u001bI\u0004B\u0003c\u0005\n\u0007\u0011\fC\u0004\u0003l\n\u0003\ra!\u0010\u0011\r=\u001b61GB\u001c\u0003)\u0019wN\\:u\r\u0006d7/\u001a\t\u0006\u001fNk\u0016qH\u0001\tK6\u0004H/_0qMB!q\nA/[\u0003\u0015)W\u000e\u001d;z+\u0019\u0019Ye!\u0015\u0004VU\u00111Q\n\t\u0007\u001f\u0002\u0019yea\u0015\u0011\u0007Y\u001b\t\u0006B\u0003Y\u000b\n\u0007\u0011\fE\u0002W\u0007+\"QAY#C\u0002e\u000bAaY8oIV!11LB3)\u0011\u0019ifa\u001a\u0015\t\u0005}2q\f\u0005\b\u0003+1\u0005\u0019AB1!\u0019y\u0005aa\u0019\u0002@A\u0019ak!\u001a\u0005\u000ba3%\u0019A-\t\u000f\u0005-g\t1\u0001\u0004d\u000591m\u001c8e\u001fB$XCBB7\u0007{\u001a)\b\u0006\u0003\u0004p\r}D\u0003BB9\u0007o\u0002BaT6\u0004tA\u0019ak!\u001e\u0005\u000b\t<%\u0019A-\t\u000f\u0005Uq\t1\u0001\u0004zA1q\nAB>\u0007g\u00022AVB?\t\u0015AvI1\u0001Z\u0011\u001d\tYm\u0012a\u0001\u0007w\"B!a\u0010\u0004\u0004\"1\u00111\u001a\u0003A\u0002U+baa\"\u0004\u000e\u000eME\u0003BBE\u0007/\u0003ba\u0014\u0001\u0004\f\u000eE\u0005c\u0001,\u0004\u000e\u00129\u0011q]\u0003C\u0002\r=\u0015C\u0001.V!\r161\u0013\u0003\b\u0003;,!\u0019ABK#\t\u0001W\fC\u0004\u0003\u0004\u0015\u0001\ra!#\u0016\t\rm5\u0011\u0015\u000b\u0005\u0007;\u001b\u0019\u000bE\u0003P\u0001U\u001by\nE\u0002W\u0007C#aAa\u0005\u0007\u0005\u0004I\u0006b\u0002B\f\r\u0001\u00071Q\u0015\t\u0006\u001fN\u00037qT\u000b\u0005\u0007S\u001by\u000b\u0006\u0003\u0004,\u000eE\u0006#B(\u0001+\u000e5\u0006c\u0001,\u00040\u00121!1C\u0004C\u0002eCqAa\u0006\b\u0001\u0004\u0019\u0019\fE\u0003P\u0001\u0001\u001ci+A\u0004d_6\u0004xn]3\u0016\t\re6q\u0018\u000b\u0005\u0007w\u001b\u0019\rE\u0003P\u0001\ru\u0006\rE\u0002W\u0007\u007f#aa!1\t\u0005\u0004I&!\u0001*\t\u000f\t]\u0001\u00021\u0001\u0004FB)q\nAB_+V\u00111\u0011\u001a\t\u0005\u001fN+&.\u0006\u0004\u0004N\u000e]7\u0011\u001b\u000b\u0007\u0007\u001f\u001c\u0019n!7\u0011\u0007Y\u001b\t\u000eB\u0004\u0002^*\u0011\ra!&\t\u000f\u0005-'\u00021\u0001\u0004VB\u0019aka6\u0005\u000f\u0005\u001d(B1\u0001\u0004\u0010\"9\u0011Q\u001e\u0006A\u0002\rm\u0007CB(T\u0007+\u001cy-A\u0004sk:<\u0016\u000e\u001e5\u0016\t\r\u00058Q\u001e\u000b\u0005\u0007G\u001c)\u000fE\u0003P'V\u000by\u0004C\u0004\u0004h.\u0001\ra!;\u0002\r\u0005\u001cG/[8o!\u0015y5\u000bYBv!\r16Q\u001e\u0003\u0007\u0007_\\!\u0019A-\u0003\u0003U\u0003")
public interface PartialFunction<A, B>
extends Function1<A, B> {
    public static <A, B> Option<B> condOpt(A x, PartialFunction<A, B> pf) {
        return PartialFunction$.MODULE$.condOpt(x, pf);
    }

    public static <A> boolean cond(A x, PartialFunction<A, Object> pf) {
        return PartialFunction$.MODULE$.cond(x, pf);
    }

    public static <A, B> PartialFunction<A, B> empty() {
        return PartialFunction$.MODULE$.empty();
    }

    public static <A, B> PartialFunction<A, B> fromFunction(Function1<A, B> f) {
        return new Serializable(f){
            private static final long serialVersionUID = 0L;
            private final Function1 f$1;

            public final <A1 extends A, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                return (B1)this.f$1.apply(x1);
            }

            public final boolean isDefinedAt(A x1) {
                return true;
            }
            {
                this.f$1 = f$1;
            }
        };
    }

    default public Option<B> unapply(A a) {
        return this.lift().apply(a);
    }

    default public PartialFunction<A, B> elementWise() {
        return this;
    }

    public boolean isDefinedAt(A var1);

    default public <A1 extends A, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return new OrElse<A1, B1>(this, that);
    }

    @Override
    default public <C$> PartialFunction<A, C$> andThen(Function1<B, C$> k) {
        if (k instanceof PartialFunction) {
            PartialFunction partialFunction = (PartialFunction)k;
            return this.andThen(partialFunction);
        }
        return new AndThen(this, k);
    }

    @Override
    default public <C$> PartialFunction<A, C$> andThen(PartialFunction<B, C$> k) {
        return new Combined(this, k);
    }

    @Override
    default public <R$> PartialFunction<R$, B> compose(PartialFunction<R$, A> k) {
        return new Combined(k, this);
    }

    default public Function1<A, Option<B>> lift() {
        return new Lifted(this);
    }

    public static /* synthetic */ Object applyOrElse$(PartialFunction $this, Object x, Function1 function1) {
        return $this.applyOrElse(x, function1);
    }

    default public <A1 extends A, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
        if (this.isDefinedAt(x)) {
            return (B1)this.apply(x);
        }
        return function1.apply(x);
    }

    default public <U> Function1<A, Object> runWith(Function1<B, U> action) {
        return (Function1<Object, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(PartialFunction.$anonfun$runWith$1(this, action, x));
    }

    public static /* synthetic */ boolean $anonfun$runWith$1(PartialFunction $this, Function1 action$1, Object x) {
        Object z = $this.applyOrElse(x, PartialFunction$.MODULE$.scala$PartialFunction$$checkFallback());
        if (!PartialFunction$.MODULE$.scala$PartialFunction$$fallbackOccurred(z)) {
            action$1.apply(z);
            return true;
        }
        return false;
    }

    public static void $init$(PartialFunction $this) {
    }

    private static class AndThen<A, B, C>
    implements PartialFunction<A, C>,
    Serializable {
        private final PartialFunction<A, B> pf;
        private final Function1<B, C> k;

        @Override
        public Option<C> unapply(A a) {
            return ((PartialFunction)this).unapply(a);
        }

        @Override
        public PartialFunction<A, C> elementWise() {
            return ((PartialFunction)this).elementWise();
        }

        @Override
        public <A1 extends A, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
            return ((PartialFunction)this).orElse((PartialFunction)that);
        }

        @Override
        public <C$> PartialFunction<A, C$> andThen(Function1<C, C$> k) {
            return ((PartialFunction)this).andThen(k);
        }

        @Override
        public <C$> PartialFunction<A, C$> andThen(PartialFunction<C, C$> k) {
            return ((PartialFunction)this).andThen((PartialFunction)k);
        }

        @Override
        public <R$> PartialFunction<R$, C> compose(PartialFunction<R$, A> k) {
            return ((PartialFunction)this).compose((PartialFunction)k);
        }

        @Override
        public Function1<A, Option<C>> lift() {
            return ((PartialFunction)this).lift();
        }

        @Override
        public <U> Function1<A, Object> runWith(Function1<C, U> action) {
            return ((PartialFunction)this).runWith(action);
        }

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, C> compose(Function1<A$, A> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public String toString() {
            return Function1.toString$(this);
        }

        @Override
        public boolean isDefinedAt(A x) {
            return this.pf.isDefinedAt(x);
        }

        @Override
        public C apply(A x) {
            return this.k.apply(this.pf.apply(x));
        }

        @Override
        public <A1 extends A, C1> C1 applyOrElse(A1 x, Function1<A1, C1> function1) {
            Object z = this.pf.applyOrElse(x, PartialFunction$.MODULE$.scala$PartialFunction$$checkFallback());
            if (!PartialFunction$.MODULE$.scala$PartialFunction$$fallbackOccurred(z)) {
                return (C1)this.k.apply(z);
            }
            return function1.apply(x);
        }

        public AndThen(PartialFunction<A, B> pf, Function1<B, C> k) {
            this.pf = pf;
            this.k = k;
        }
    }

    private static class Combined<A, B, C>
    implements PartialFunction<A, C>,
    Serializable {
        private final PartialFunction<A, B> pf;
        private final PartialFunction<B, C> k;

        @Override
        public Option<C> unapply(A a) {
            return ((PartialFunction)this).unapply(a);
        }

        @Override
        public PartialFunction<A, C> elementWise() {
            return ((PartialFunction)this).elementWise();
        }

        @Override
        public <A1 extends A, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
            return ((PartialFunction)this).orElse((PartialFunction)that);
        }

        @Override
        public <C$> PartialFunction<A, C$> andThen(Function1<C, C$> k) {
            return ((PartialFunction)this).andThen(k);
        }

        @Override
        public <C$> PartialFunction<A, C$> andThen(PartialFunction<C, C$> k) {
            return ((PartialFunction)this).andThen((PartialFunction)k);
        }

        @Override
        public <R$> PartialFunction<R$, C> compose(PartialFunction<R$, A> k) {
            return ((PartialFunction)this).compose((PartialFunction)k);
        }

        @Override
        public Function1<A, Option<C>> lift() {
            return ((PartialFunction)this).lift();
        }

        @Override
        public <U> Function1<A, Object> runWith(Function1<C, U> action) {
            return ((PartialFunction)this).runWith(action);
        }

        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A$> Function1<A$, C> compose(Function1<A$, A> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public String toString() {
            return Function1.toString$(this);
        }

        @Override
        public boolean isDefinedAt(A x) {
            Object b = this.pf.applyOrElse(x, PartialFunction$.MODULE$.scala$PartialFunction$$checkFallback());
            if (!PartialFunction$.MODULE$.scala$PartialFunction$$fallbackOccurred(b)) {
                return this.k.isDefinedAt(b);
            }
            return false;
        }

        @Override
        public C apply(A x) {
            return (C)this.k.apply(this.pf.apply(x));
        }

        @Override
        public <A1 extends A, C1> C1 applyOrElse(A1 x, Function1<A1, C1> function1) {
            Object pfv = this.pf.applyOrElse(x, PartialFunction$.MODULE$.scala$PartialFunction$$checkFallback());
            if (!PartialFunction$.MODULE$.scala$PartialFunction$$fallbackOccurred(pfv)) {
                return (C1)this.k.applyOrElse(pfv, (Function1<Object, Object> & Serializable)(A1 x$1) -> function1.apply(x));
            }
            return function1.apply(x);
        }

        public Combined(PartialFunction<A, B> pf, PartialFunction<B, C> k) {
            this.pf = pf;
            this.k = k;
        }
    }

    public static final class ElementWiseExtractor<A, B> {
        private final PartialFunction<A, B> scala$PartialFunction$ElementWiseExtractor$$pf;

        public PartialFunction<A, B> scala$PartialFunction$ElementWiseExtractor$$pf() {
            return this.scala$PartialFunction$ElementWiseExtractor$$pf;
        }

        public Option<Seq<B>> unapplySeq(Seq<A> seq) {
            return PartialFunction$ElementWiseExtractor$.MODULE$.unapplySeq$extension(this.scala$PartialFunction$ElementWiseExtractor$$pf(), seq);
        }

        public int hashCode() {
            return this.scala$PartialFunction$ElementWiseExtractor$$pf().hashCode();
        }

        public boolean equals(Object x$1) {
            return PartialFunction$ElementWiseExtractor$.MODULE$.equals$extension(this.scala$PartialFunction$ElementWiseExtractor$$pf(), x$1);
        }

        public ElementWiseExtractor(PartialFunction<A, B> pf) {
            this.scala$PartialFunction$ElementWiseExtractor$$pf = pf;
        }
    }

    private static class Lifted<A, B>
    extends AbstractFunction1<A, Option<B>>
    implements Serializable {
        private final PartialFunction<A, B> pf;

        public PartialFunction<A, B> pf() {
            return this.pf;
        }

        @Override
        public Option<B> apply(A x) {
            Object z = this.pf().applyOrElse(x, PartialFunction$.MODULE$.scala$PartialFunction$$checkFallback());
            if (!PartialFunction$.MODULE$.scala$PartialFunction$$fallbackOccurred(z)) {
                return new Some(z);
            }
            return None$.MODULE$;
        }

        public Lifted(PartialFunction<A, B> pf) {
            this.pf = pf;
        }
    }

    private static class OrElse<A, B>
    extends AbstractPartialFunction<A, B>
    implements Serializable {
        private final PartialFunction<A, B> f1;
        private final PartialFunction<A, B> f2;

        @Override
        public boolean isDefinedAt(A x) {
            return this.f1.isDefinedAt(x) || this.f2.isDefinedAt(x);
        }

        @Override
        public B apply(A x) {
            return this.f1.applyOrElse(x, this.f2);
        }

        @Override
        public <A1 extends A, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
            Object z = this.f1.applyOrElse(x, PartialFunction$.MODULE$.scala$PartialFunction$$checkFallback());
            if (!PartialFunction$.MODULE$.scala$PartialFunction$$fallbackOccurred(z)) {
                return (B1)z;
            }
            return this.f2.applyOrElse(x, function1);
        }

        @Override
        public <A1 extends A, B1> OrElse<A1, B1> orElse(PartialFunction<A1, B1> that) {
            return new OrElse<A, B>(this.f1, this.f2.orElse(that));
        }

        @Override
        public <C> OrElse<A, C> andThen(Function1<B, C> k) {
            return new OrElse<A, C>(this.f1.andThen(k), this.f2.andThen(k));
        }

        public OrElse(PartialFunction<A, B> f1, PartialFunction<A, B> f2) {
            this.f1 = f1;
            this.f2 = f2;
        }
    }

    private static class Unlifted<A, B>
    extends AbstractPartialFunction<A, B>
    implements Serializable {
        private final Function1<A, Option<B>> f;

        @Override
        public boolean isDefinedAt(A x) {
            return this.f.apply(x).isDefined();
        }

        @Override
        public <A1 extends A, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
            Option<B> option = this.f.apply(x);
            if (option == null) {
                throw null;
            }
            Option<B> getOrElse_this = option;
            if (getOrElse_this.isEmpty()) {
                return function1.apply(x);
            }
            return (B1)getOrElse_this.get();
        }

        @Override
        public Function1<A, Option<B>> lift() {
            return this.f;
        }

        public static final /* synthetic */ Object $anonfun$applyOrElse$2(Function1 default$2, Object x$5) {
            return default$2.apply(x$5);
        }

        public Unlifted(Function1<A, Option<B>> f) {
            this.f = f;
        }
    }
}

