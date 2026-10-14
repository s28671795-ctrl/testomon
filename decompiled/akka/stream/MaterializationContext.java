/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.MaterializationContext$;
import akka.stream.Materializer;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001de!\u0002\u000f\u001e\u0001~\t\u0003\u0002\u0003\u001d\u0001\u0005+\u0007I\u0011A\u001d\t\u0011y\u0002!\u0011#Q\u0001\niB\u0001b\u0010\u0001\u0003\u0016\u0004%\t\u0001\u0011\u0005\t\t\u0002\u0011\t\u0012)A\u0005\u0003\"AQ\t\u0001BK\u0002\u0013\u0005a\t\u0003\u0005P\u0001\tE\t\u0015!\u0003H\u0011\u0015\u0001\u0006\u0001\"\u0001R\u0011\u001d1\u0006!!A\u0005\u0002]Cqa\u0017\u0001\u0012\u0002\u0013\u0005A\fC\u0004h\u0001E\u0005I\u0011\u00015\t\u000f)\u0004\u0011\u0013!C\u0001W\"9Q\u000eAA\u0001\n\u0003r\u0007b\u0002<\u0001\u0003\u0003%\ta\u001e\u0005\bw\u0002\t\t\u0011\"\u0001}\u0011%\t)\u0001AA\u0001\n\u0003\n9\u0001C\u0005\u0002\u0016\u0001\t\t\u0011\"\u0001\u0002\u0018!I\u0011\u0011\u0005\u0001\u0002\u0002\u0013\u0005\u00131\u0005\u0005\n\u0003O\u0001\u0011\u0011!C!\u0003SA\u0011\"a\u000b\u0001\u0003\u0003%\t%!\f\t\u0013\u0005=\u0002!!A\u0005B\u0005ErACA!;\u0005\u0005\t\u0012A\u0010\u0002D\u0019IA$HA\u0001\u0012\u0003y\u0012Q\t\u0005\u0007!Z!\t!!\u0018\t\u0013\u0005-b#!A\u0005F\u00055\u0002\"CA0-\u0005\u0005I\u0011QA1\u0011%\tIGFA\u0001\n\u0003\u000bY\u0007C\u0005\u0002~Y\t\t\u0011\"\u0003\u0002\u0000\t1R*\u0019;fe&\fG.\u001b>bi&|gnQ8oi\u0016DHO\u0003\u0002\u001f?\u000511\u000f\u001e:fC6T\u0011\u0001I\u0001\u0005C.\\\u0017m\u0005\u0003\u0001E!Z\u0003CA\u0012'\u001b\u0005!#\"A\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u001d\"#AB!osJ+g\r\u0005\u0002$S%\u0011!\u0006\n\u0002\b!J|G-^2u!\taSG\u0004\u0002.g9\u0011aFM\u0007\u0002_)\u0011\u0001'M\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\tQ%\u0003\u00025I\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001c8\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t!D%\u0001\u0007nCR,'/[1mSj,'/F\u0001;!\tYD(D\u0001\u001e\u0013\tiTD\u0001\u0007NCR,'/[1mSj,'/A\u0007nCR,'/[1mSj,'\u000fI\u0001\u0014K\u001a4Wm\u0019;jm\u0016\fE\u000f\u001e:jEV$Xm]\u000b\u0002\u0003B\u00111HQ\u0005\u0003\u0007v\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0003Q)gMZ3di&4X-\u0011;ue&\u0014W\u000f^3tA\u0005Q\u0011n\u001d7b]\u0012t\u0015-\\3\u0016\u0003\u001d\u0003\"\u0001\u0013'\u000f\u0005%S\u0005C\u0001\u0018%\u0013\tYE%\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u001b:\u0013aa\u0015;sS:<'BA&%\u0003-I7\u000f\\1oI:\u000bW.\u001a\u0011\u0002\rqJg.\u001b;?)\u0011\u00116\u000bV+\u0011\u0005m\u0002\u0001\"\u0002\u001d\b\u0001\u0004Q\u0004\"B \b\u0001\u0004\t\u0005\"B#\b\u0001\u00049\u0015\u0001B2paf$BA\u0015-Z5\"9\u0001\b\u0003I\u0001\u0002\u0004Q\u0004bB \t!\u0003\u0005\r!\u0011\u0005\b\u000b\"\u0001\n\u00111\u0001H\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012!\u0018\u0016\u0003uy[\u0013a\u0018\t\u0003A\u0016l\u0011!\u0019\u0006\u0003E\u000e\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005\u0011$\u0013AC1o]>$\u0018\r^5p]&\u0011a-\u0019\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0002S*\u0012\u0011IX\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+\u0005a'FA$_\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\tq\u000e\u0005\u0002qk6\t\u0011O\u0003\u0002sg\u0006!A.\u00198h\u0015\u0005!\u0018\u0001\u00026bm\u0006L!!T9\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003a\u0004\"aI=\n\u0005i$#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HcA?\u0002\u0002A\u00111E`\u0005\u0003\u007f\u0012\u00121!\u00118z\u0011!\t\u0019ADA\u0001\u0002\u0004A\u0018a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002\nA)\u00111BA\t{6\u0011\u0011Q\u0002\u0006\u0004\u0003\u001f!\u0013AC2pY2,7\r^5p]&!\u00111CA\u0007\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005e\u0011q\u0004\t\u0004G\u0005m\u0011bAA\u000fI\t9!i\\8mK\u0006t\u0007\u0002CA\u0002!\u0005\u0005\t\u0019A?\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0004_\u0006\u0015\u0002\u0002CA\u0002#\u0005\u0005\t\u0019\u0001=\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001_\u0001\ti>\u001cFO]5oOR\tq.\u0001\u0004fcV\fGn\u001d\u000b\u0005\u00033\t\u0019\u0004\u0003\u0005\u0002\u0004Q\t\t\u00111\u0001~Q\r\u0001\u0011q\u0007\t\u0005\u0003s\ti$\u0004\u0002\u0002<)\u0011AmH\u0005\u0005\u0003\u007f\tYDA\u0006J]R,'O\\1m\u0003BL\u0017AF'bi\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8D_:$X\r\u001f;\u0011\u0005m22#\u0002\f\u0002H\u0005M\u0003\u0003CA%\u0003\u001fR\u0014i\u0012*\u000e\u0005\u0005-#bAA'I\u00059!/\u001e8uS6,\u0017\u0002BA)\u0003\u0017\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c84!\u0011\t)&a\u0017\u000e\u0005\u0005]#bAA-g\u0006\u0011\u0011n\\\u0005\u0004m\u0005]CCAA\"\u0003\u0015\t\u0007\u000f\u001d7z)\u001d\u0011\u00161MA3\u0003OBQ\u0001O\rA\u0002iBQaP\rA\u0002\u0005CQ!R\rA\u0002\u001d\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002n\u0005e\u0004#B\u0012\u0002p\u0005M\u0014bAA9I\t1q\n\u001d;j_:\u0004baIA;u\u0005;\u0015bAA<I\t1A+\u001e9mKNB\u0001\"a\u001f\u001b\u0003\u0003\u0005\rAU\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAA!\r\u0001\u00181Q\u0005\u0004\u0003\u000b\u000b(AB(cU\u0016\u001cG\u000f")
@InternalApi
public class MaterializationContext
implements Product,
Serializable {
    private final Materializer materializer;
    private final Attributes effectiveAttributes;
    private final String islandName;

    public static Option<Tuple3<Materializer, Attributes, String>> unapply(MaterializationContext x$0) {
        return MaterializationContext$.MODULE$.unapply(x$0);
    }

    public static MaterializationContext apply(Materializer materializer, Attributes effectiveAttributes, String islandName) {
        return MaterializationContext$.MODULE$.apply(materializer, effectiveAttributes, islandName);
    }

    public static Function1<Tuple3<Materializer, Attributes, String>, MaterializationContext> tupled() {
        return MaterializationContext$.MODULE$.tupled();
    }

    public static Function1<Materializer, Function1<Attributes, Function1<String, MaterializationContext>>> curried() {
        return MaterializationContext$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Materializer materializer() {
        return this.materializer;
    }

    public Attributes effectiveAttributes() {
        return this.effectiveAttributes;
    }

    public String islandName() {
        return this.islandName;
    }

    public MaterializationContext copy(Materializer materializer, Attributes effectiveAttributes, String islandName) {
        return new MaterializationContext(materializer, effectiveAttributes, islandName);
    }

    public Materializer copy$default$1() {
        return this.materializer();
    }

    public Attributes copy$default$2() {
        return this.effectiveAttributes();
    }

    public String copy$default$3() {
        return this.islandName();
    }

    @Override
    public String productPrefix() {
        return "MaterializationContext";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.materializer();
            }
            case 1: {
                return this.effectiveAttributes();
            }
            case 2: {
                return this.islandName();
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
        return x$1 instanceof MaterializationContext;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "materializer";
            }
            case 1: {
                return "effectiveAttributes";
            }
            case 2: {
                return "islandName";
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
        if (!(object instanceof MaterializationContext)) return false;
        boolean bl = true;
        if (!bl) return false;
        MaterializationContext materializationContext = (MaterializationContext)x$1;
        Materializer materializer = this.materializer();
        Materializer materializer2 = materializationContext.materializer();
        if (materializer == null) {
            if (materializer2 != null) {
                return false;
            }
        } else if (!materializer.equals(materializer2)) return false;
        Attributes attributes = this.effectiveAttributes();
        Attributes attributes2 = materializationContext.effectiveAttributes();
        if (attributes == null) {
            if (attributes2 != null) {
                return false;
            }
        } else if (!((Object)attributes).equals(attributes2)) return false;
        String string = this.islandName();
        String string2 = materializationContext.islandName();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        if (!materializationContext.canEqual(this)) return false;
        return true;
    }

    public MaterializationContext(Materializer materializer, Attributes effectiveAttributes, String islandName) {
        this.materializer = materializer;
        this.effectiveAttributes = effectiveAttributes;
        this.islandName = islandName;
        Product.$init$(this);
    }
}

