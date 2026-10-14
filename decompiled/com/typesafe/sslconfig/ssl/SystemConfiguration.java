/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLConfigSettings;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import java.io.Serializable;
import java.security.Security;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005!3AAC\u0006\u0001)!A1\u0004\u0001B\u0001B\u0003%A\u0004C\u0003#\u0001\u0011\u00051\u0005C\u0004(\u0001\t\u0007I\u0011\u0001\u0015\t\r1\u0002\u0001\u0015!\u0003*\u0011\u0015i\u0003\u0001\"\u0001/\u0011\u00159\u0004\u0001\"\u00019\u0011\u0015q\u0004\u0001\"\u0001@\u0011\u0015\u0011\u0005\u0001\"\u0001D\u0011\u00151\u0005\u0001\"\u0001H\u0005M\u0019\u0016p\u001d;f[\u000e{gNZ5hkJ\fG/[8o\u0015\taQ\"A\u0002tg2T!AD\b\u0002\u0013M\u001cHnY8oM&<'B\u0001\t\u0012\u0003!!\u0018\u0010]3tC\u001a,'\"\u0001\n\u0002\u0007\r|Wn\u0001\u0001\u0014\u0005\u0001)\u0002C\u0001\f\u001a\u001b\u00059\"\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005i9\"AB!osJ+g-\u0001\u0005nW2{wmZ3s!\ti\u0002%D\u0001\u001f\u0015\tyR\"\u0001\u0003vi&d\u0017BA\u0011\u001f\u00055aunZ4fe\u001a\u000b7\r^8ss\u00061A(\u001b8jiz\"\"\u0001\n\u0014\u0011\u0005\u0015\u0002Q\"A\u0006\t\u000bm\u0011\u0001\u0019\u0001\u000f\u0002\r1|wmZ3s+\u0005I\u0003CA\u000f+\u0013\tYcD\u0001\u0007O_\u0012+\u0007o\u001d'pO\u001e,'/A\u0004m_\u001e<WM\u001d\u0011\u0002\u0013\r|gNZ5hkJ,GCA\u00183!\t1\u0002'\u0003\u00022/\t!QK\\5u\u0011\u0015\u0019T\u00011\u00015\u0003\u0019\u0019wN\u001c4jOB\u0011Q%N\u0005\u0003m-\u0011\u0011cU*M\u0007>tg-[4TKR$\u0018N\\4t\u0003q\u0019wN\u001c4jOV\u0014X-\u00168tC\u001a,'+\u001a8fO>$\u0018.\u0019;j_:$\"aL\u001d\t\u000bi2\u0001\u0019A\u001e\u00021\u0005dGn\\<V]N\fg-\u001a*f]\u0016<w\u000e^5bi&|g\u000e\u0005\u0002\u0017y%\u0011Qh\u0006\u0002\b\u0005>|G.Z1o\u0003\u0005\u001awN\u001c4jOV\u0014X-\u00117m_^dUmZ1ds\"+G\u000e\\8NKN\u001c\u0018mZ3t)\ty\u0003\tC\u0003B\u000f\u0001\u00071(\u0001\rbY2|w\u000fT3hC\u000eL\b*\u001a7m_6+7o]1hKN\f\u0001dY8oM&<WO]3DQ\u0016\u001c7NU3w_\u000e\fG/[8o)\tyC\tC\u0003F\u0011\u0001\u00071(A\bdQ\u0016\u001c7NU3w_\u000e\fG/[8o\u0003=\u0019G.Z1s!J|\u0007/\u001a:uS\u0016\u001cH#A\u0018")
public class SystemConfiguration {
    private final NoDepsLogger logger;

    public NoDepsLogger logger() {
        return this.logger;
    }

    public void configure(SSLConfigSettings config) {
        config.loose().allowUnsafeRenegotiation().foreach((Function1<Object, Object> & Serializable)allowUnsafeRenegotiation -> {
            this.configureUnsafeRenegotiation(BoxesRunTime.unboxToBoolean(allowUnsafeRenegotiation));
            return BoxedUnit.UNIT;
        });
        config.loose().allowLegacyHelloMessages().foreach((Function1<Object, Object> & Serializable)allowLegacyHelloMessages -> {
            this.configureAllowLegacyHelloMessages(BoxesRunTime.unboxToBoolean(allowLegacyHelloMessages));
            return BoxedUnit.UNIT;
        });
        config.checkRevocation().foreach((Function1<Object, Object> & Serializable)checkRevocation -> {
            this.configureCheckRevocation(BoxesRunTime.unboxToBoolean(checkRevocation));
            return BoxedUnit.UNIT;
        });
    }

    public void configureUnsafeRenegotiation(boolean allowUnsafeRenegotiation) {
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", Boolean.toString(allowUnsafeRenegotiation));
        this.logger().debug(new StringBuilder(74).append("configureUnsafeRenegotiation: sun.security.ssl.allowUnsafeRenegotiation = ").append(Boolean.toString(allowUnsafeRenegotiation)).toString());
    }

    public void configureAllowLegacyHelloMessages(boolean allowLegacyHelloMessages) {
        System.setProperty("sun.security.ssl.allowLegacyHelloMessages", Boolean.toString(allowLegacyHelloMessages));
        this.logger().debug(new StringBuilder(79).append("configureAllowLegacyHelloMessages: sun.security.ssl.allowLegacyHelloMessages = ").append(Boolean.toString(allowLegacyHelloMessages)).toString());
    }

    public void configureCheckRevocation(boolean checkRevocation) {
        Security.setProperty("ocsp.enable", Boolean.toString(checkRevocation));
        this.logger().debug(new StringBuilder(40).append("configureCheckRevocation: ocsp.enable = ").append(Boolean.toString(checkRevocation)).toString());
        System.setProperty("com.sun.security.enableCRLDP", Boolean.toString(checkRevocation));
        this.logger().debug(new StringBuilder(57).append("configureCheckRevocation: com.sun.security.enableCRLDP = ").append(Boolean.toString(checkRevocation)).toString());
        System.setProperty("com.sun.net.ssl.checkRevocation", Boolean.toString(checkRevocation));
    }

    public void clearProperties() {
        Security.setProperty("ocsp.enable", "false");
        System.clearProperty("com.sun.security.enableCRLDP");
        System.clearProperty("com.sun.net.ssl.checkRevocation");
        System.clearProperty("sun.security.ssl.allowLegacyHelloMessages");
        System.clearProperty("sun.security.ssl.allowUnsafeRenegotiation");
    }

    public SystemConfiguration(LoggerFactory mkLogger) {
        this.logger = mkLogger.apply(this.getClass());
    }
}

