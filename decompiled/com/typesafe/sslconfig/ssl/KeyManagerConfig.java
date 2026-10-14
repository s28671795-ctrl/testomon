/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyManagerConfig$;
import com.typesafe.sslconfig.ssl.KeyStoreConfig;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4Aa\u0005\u000b\u0003;!AA\u0005\u0001BC\u0002\u0013\u0005Q\u0005\u0003\u00052\u0001\t\u0005\t\u0015!\u0003'\u0011!\u0011\u0004A!b\u0001\n\u0003\u0019\u0004\u0002\u0003!\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001b\t\r\u0005\u0003A\u0011\u0001\fC\u0011\u00151\u0005\u0001\"\u0001H\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u0015i\u0005\u0001\"\u0003O\u0011\u001d\t\u0006!%A\u0005\nICq!\u0018\u0001\u0012\u0002\u0013%a\fC\u0003a\u0001\u0011\u0005\u0013mB\u0003c)!\u00051MB\u0003\u0014)!\u0005A\rC\u0003B\u001b\u0011\u0005Q\rC\u0003g\u001b\u0011\u0005q\rC\u0003i\u001b\u0011\u0005q\r\u0003\u0005j\u001bE\u0005I\u0011\u0001\fS\u0011!QW\"%A\u0005\u0002Yq&\u0001E&fs6\u000bg.Y4fe\u000e{gNZ5h\u0015\t)b#A\u0002tg2T!a\u0006\r\u0002\u0013M\u001cHnY8oM&<'BA\r\u001b\u0003!!\u0018\u0010]3tC\u001a,'\"A\u000e\u0002\u0007\r|Wn\u0001\u0001\u0014\u0005\u0001q\u0002CA\u0010#\u001b\u0005\u0001#\"A\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\r\u0002#AB!osJ+g-A\u0005bY\u001e|'/\u001b;i[V\ta\u0005\u0005\u0002(]9\u0011\u0001\u0006\f\t\u0003S\u0001j\u0011A\u000b\u0006\u0003Wq\ta\u0001\u0010:p_Rt\u0014BA\u0017!\u0003\u0019\u0001&/\u001a3fM&\u0011q\u0006\r\u0002\u0007'R\u0014\u0018N\\4\u000b\u00055\u0002\u0013AC1mO>\u0014\u0018\u000e\u001e5nA\u0005y1.Z=Ti>\u0014XmQ8oM&<7/F\u00015!\r)$\bP\u0007\u0002m)\u0011q\u0007O\u0001\nS6lW\u000f^1cY\u0016T!!\u000f\u0011\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002<m\t\u00191+Z9\u0011\u0005urT\"\u0001\u000b\n\u0005}\"\"AD&fsN#xN]3D_:4\u0017nZ\u0001\u0011W\u0016L8\u000b^8sK\u000e{gNZ5hg\u0002\na\u0001P5oSRtDcA\"E\u000bB\u0011Q\b\u0001\u0005\bI\u0015\u0001\n\u00111\u0001'\u0011\u001d\u0011T\u0001%AA\u0002Q\nQb^5uQ\u0006cwm\u001c:ji\"lGCA\"I\u0011\u0015Ie\u00011\u0001'\u0003\u00151\u0018\r\\;f\u0003M9\u0018\u000e\u001e5LKf\u001cFo\u001c:f\u0007>tg-[4t)\t\u0019E\nC\u0003J\u000f\u0001\u0007A'\u0001\u0003d_BLHcA\"P!\"9A\u0005\u0003I\u0001\u0002\u00041\u0003b\u0002\u001a\t!\u0003\u0005\r\u0001N\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005\u0019&F\u0001\u0014UW\u0005)\u0006C\u0001,\\\u001b\u00059&B\u0001-Z\u0003%)hn\u00195fG.,GM\u0003\u0002[A\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005q;&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T#A0+\u0005Q\"\u0016\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003\u0019\n\u0001cS3z\u001b\u0006t\u0017mZ3s\u0007>tg-[4\u0011\u0005uj1CA\u0007\u001f)\u0005\u0019\u0017!B1qa2LH#A\"\u0002\u0017\u001d,G/\u00138ti\u0006t7-Z\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133\u0001")
public final class KeyManagerConfig {
    private final String algorithm;
    private final Seq<KeyStoreConfig> keyStoreConfigs;

    public static KeyManagerConfig getInstance() {
        return KeyManagerConfig$.MODULE$.getInstance();
    }

    public static KeyManagerConfig apply() {
        return KeyManagerConfig$.MODULE$.apply();
    }

    public String algorithm() {
        return this.algorithm;
    }

    public Seq<KeyStoreConfig> keyStoreConfigs() {
        return this.keyStoreConfigs;
    }

    public KeyManagerConfig withAlgorithm(String value) {
        return this.copy(value, this.copy$default$2());
    }

    public KeyManagerConfig withKeyStoreConfigs(Seq<KeyStoreConfig> value) {
        Seq<KeyStoreConfig> x$1 = value;
        String x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    private KeyManagerConfig copy(String algorithm, Seq<KeyStoreConfig> keyStoreConfigs) {
        return new KeyManagerConfig(algorithm, keyStoreConfigs);
    }

    private String copy$default$1() {
        return this.algorithm();
    }

    private Seq<KeyStoreConfig> copy$default$2() {
        return this.keyStoreConfigs();
    }

    public String toString() {
        return new StringBuilder(19).append("KeyManagerConfig(").append(this.algorithm()).append(",").append(this.keyStoreConfigs()).append(")").toString();
    }

    public KeyManagerConfig(String algorithm, Seq<KeyStoreConfig> keyStoreConfigs) {
        this.algorithm = algorithm;
        this.keyStoreConfigs = keyStoreConfigs;
    }
}

