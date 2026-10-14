/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.package$;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.X509Certificate;
import java.util.List;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015<Q!\u0003\u0006\t\u0002M1Q!\u0006\u0006\t\u0002YAQ!H\u0001\u0005\u0002yAQaH\u0001\u0005\u0004\u0001BQaL\u0001\u0005\u0004ABQ\u0001P\u0001\u0005\u0004uBQAR\u0001\u0005\u0002\u001dCa!X\u0001\u0005\u00021q\u0006\"\u00021\u0002\t\u0003\t\u0017a\u00029bG.\fw-\u001a\u0006\u0003\u00171\t1a]:m\u0015\tia\"A\u0005tg2\u001cwN\u001c4jO*\u0011q\u0002E\u0001\tif\u0004Xm]1gK*\t\u0011#A\u0002d_6\u001c\u0001\u0001\u0005\u0002\u0015\u00035\t!BA\u0004qC\u000e\\\u0017mZ3\u0014\u0005\u00059\u0002C\u0001\r\u001c\u001b\u0005I\"\"\u0001\u000e\u0002\u000bM\u001c\u0017\r\\1\n\u0005qI\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002'\u0005Y2-\u001a:uS\u001aL7-\u0019;fea+\u0004'O\"feRLg-[2bi\u0016$\"!I\u0016\u0011\u0005\tJS\"A\u0012\u000b\u0005\u0011*\u0013\u0001B2feRT!AJ\u0014\u0002\u0011M,7-\u001e:jifT\u0011\u0001K\u0001\u0005U\u00064\u0018-\u0003\u0002+G\ty\u0001,\u000e\u0019:\u0007\u0016\u0014H/\u001b4jG\u0006$X\rC\u0003%\u0007\u0001\u0007A\u0006\u0005\u0002#[%\u0011af\t\u0002\f\u0007\u0016\u0014H/\u001b4jG\u0006$X-A\u000bbeJ\f\u0017pQ3siN$v\u000eT5ti\u000e+'\u000f^:\u0015\u0005E:\u0004c\u0001\u001a6Y5\t1G\u0003\u00025O\u0005!Q\u000f^5m\u0013\t14G\u0001\u0003MSN$\b\"\u0002\u001d\u0005\u0001\u0004I\u0014!B2iC&t\u0007c\u0001\r;Y%\u00111(\u0007\u0002\u0006\u0003J\u0014\u0018-_\u0001\u0016G\u0016\u0014HOU3tk2$(\u0007U&J1J+7/\u001e7u)\tq\u0014\t\u0005\u0002#\u007f%\u0011\u0001i\t\u0002\u001c!.K\u0005lQ3siB\u000bG\u000f\u001b,bY&$\u0017\r^8s%\u0016\u001cX\u000f\u001c;\t\u000b\t+\u0001\u0019A\"\u0002\rI,7/\u001e7u!\t\u0011C)\u0003\u0002FG\t92)\u001a:u!\u0006$\bNV1mS\u0012\fGo\u001c:SKN,H\u000e^\u0001\u000bI\u0016\u0014WoZ\"iC&tGC\u0001%\\!\rI\u0005k\u0015\b\u0003\u0015>s!a\u0013(\u000e\u00031S!!\u0014\n\u0002\rq\u0012xn\u001c;?\u0013\u0005Q\u0012BA\u0005\u001a\u0013\t\t&KA\u0002TKFT!!C\r\u0011\u0005QCfBA+W!\tY\u0015$\u0003\u0002X3\u00051\u0001K]3eK\u001aL!!\u0017.\u0003\rM#(/\u001b8h\u0015\t9\u0016\u0004C\u00039\r\u0001\u0007A\fE\u0002\u0019u\u0005\n\u0011\u0002Z3ck\u001e\u001cUM\u001d;\u0015\u0005M{\u0006\"\u0002\u0013\b\u0001\u0004\t\u0013!C5t\u001fB,gN\u00133l+\u0005\u0011\u0007C\u0001\rd\u0013\t!\u0017DA\u0004C_>dW-\u00198")
public final class package {
    public static boolean isOpenJdk() {
        return package$.MODULE$.isOpenJdk();
    }

    public static Seq<String> debugChain(X509Certificate[] chain) {
        return package$.MODULE$.debugChain(chain);
    }

    public static PKIXCertPathValidatorResult certResult2PKIXResult(CertPathValidatorResult result) {
        return package$.MODULE$.certResult2PKIXResult(result);
    }

    public static List<Certificate> arrayCertsToListCerts(Certificate[] chain) {
        return package$.MODULE$.arrayCertsToListCerts(chain);
    }

    public static X509Certificate certificate2X509Certificate(Certificate cert) {
        return package$.MODULE$.certificate2X509Certificate(cert);
    }
}

