/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Address;
import akka.actor.AddressTerminated$;
import akka.actor.AutoReceivedMessage;
import akka.actor.DeadLetterSuppression;
import akka.actor.PossiblyHarmful;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=c!\u0002\f\u0018\u0005fY\u0002\u0002\u0003\u001f\u0001\u0005+\u0007I\u0011A\u001f\t\u0011\u0005\u0003!\u0011#Q\u0001\nyBQA\u0011\u0001\u0005\u0002\rCqA\u0012\u0001\u0002\u0002\u0013\u0005q\tC\u0004J\u0001E\u0005I\u0011\u0001&\t\u000fU\u0003\u0011\u0011!C!-\"9q\fAA\u0001\n\u0003\u0001\u0007b\u00023\u0001\u0003\u0003%\t!\u001a\u0005\bW\u0002\t\t\u0011\"\u0011m\u0011\u001d\u0019\b!!A\u0005\u0002QDq!\u001f\u0001\u0002\u0002\u0013\u0005#\u0010C\u0004}\u0001\u0005\u0005I\u0011I?\t\u000fy\u0004\u0011\u0011!C!\u007f\"I\u0011\u0011\u0001\u0001\u0002\u0002\u0013\u0005\u00131A\u0004\u000b\u0003'9\u0012\u0011!E\u00013\u0005Ua!\u0003\f\u0018\u0003\u0003E\t!GA\f\u0011\u0019\u0011\u0005\u0003\"\u0001\u00020!9a\u0010EA\u0001\n\u000bz\b\"CA\u0019!\u0005\u0005I\u0011QA\u001a\u0011%\t9\u0004EA\u0001\n\u0003\u000bI\u0004C\u0005\u0002FA\t\t\u0011\"\u0003\u0002H\t\t\u0012\t\u001a3sKN\u001cH+\u001a:nS:\fG/\u001a3\u000b\u0005aI\u0012!B1di>\u0014(\"\u0001\u000e\u0002\t\u0005\\7.Y\n\b\u0001q\u0011c%\u000b\u00170!\ti\u0002%D\u0001\u001f\u0015\u0005y\u0012!B:dC2\f\u0017BA\u0011\u001f\u0005\u0019\te.\u001f*fMB\u00111\u0005J\u0007\u0002/%\u0011Qe\u0006\u0002\u0014\u0003V$xNU3dK&4X\rZ'fgN\fw-\u001a\t\u0003G\u001dJ!\u0001K\f\u0003\u001fA{7o]5cYfD\u0015M]7gk2\u0004\"a\t\u0016\n\u0005-:\"!\u0006#fC\u0012dU\r\u001e;feN+\b\u000f\u001d:fgNLwN\u001c\t\u0003;5J!A\f\u0010\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001'\u000f\b\u0003c]r!A\r\u001c\u000e\u0003MR!\u0001N\u001b\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011aH\u0005\u0003qy\tq\u0001]1dW\u0006<W-\u0003\u0002;w\ta1+\u001a:jC2L'0\u00192mK*\u0011\u0001HH\u0001\bC\u0012$'/Z:t+\u0005q\u0004CA\u0012@\u0013\t\u0001uCA\u0004BI\u0012\u0014Xm]:\u0002\u0011\u0005$GM]3tg\u0002\na\u0001P5oSRtDC\u0001#F!\t\u0019\u0003\u0001C\u0003=\u0007\u0001\u0007a(\u0001\u0003d_BLHC\u0001#I\u0011\u001daD\u0001%AA\u0002y\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001LU\tqDjK\u0001N!\tq5+D\u0001P\u0015\t\u0001\u0016+A\u0005v]\u000eDWmY6fI*\u0011!KH\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001+P\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003]\u0003\"\u0001W/\u000e\u0003eS!AW.\u0002\t1\fgn\u001a\u0006\u00029\u0006!!.\u0019<b\u0013\tq\u0016L\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002CB\u0011QDY\u0005\u0003Gz\u00111!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"AZ5\u0011\u0005u9\u0017B\u00015\u001f\u0005\r\te.\u001f\u0005\bU\"\t\t\u00111\u0001b\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\tQ\u000eE\u0002oc\u001al\u0011a\u001c\u0006\u0003az\t!bY8mY\u0016\u001cG/[8o\u0013\t\u0011xN\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGCA;y!\tib/\u0003\u0002x=\t9!i\\8mK\u0006t\u0007b\u00026\u000b\u0003\u0003\u0005\rAZ\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0002Xw\"9!nCA\u0001\u0002\u0004\t\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003\u0005\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002/\u00061Q-];bYN$2!^A\u0003\u0011\u001dQg\"!AA\u0002\u0019Ds\u0001AA\u0005\u0003\u001f\t\t\u0002E\u0002\u001e\u0003\u0017I1!!\u0004\u001f\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0003E\tE\r\u001a:fgN$VM]7j]\u0006$X\r\u001a\t\u0003GA\u0019R\u0001EA\r\u0003K\u0001b!a\u0007\u0002\"y\"UBAA\u000f\u0015\r\tyBH\u0001\beVtG/[7f\u0013\u0011\t\u0019#!\b\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0002(\u00055RBAA\u0015\u0015\r\tYcW\u0001\u0003S>L1AOA\u0015)\t\t)\"A\u0003baBd\u0017\u0010F\u0002E\u0003kAQ\u0001P\nA\u0002y\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002<\u0005\u0005\u0003\u0003B\u000f\u0002>yJ1!a\u0010\u001f\u0005\u0019y\u0005\u000f^5p]\"A\u00111\t\u000b\u0002\u0002\u0003\u0007A)A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u0013\u0011\u0007a\u000bY%C\u0002\u0002Ne\u0013aa\u00142kK\u000e$\b")
public final class AddressTerminated
implements AutoReceivedMessage,
PossiblyHarmful,
DeadLetterSuppression,
Product {
    private static final long serialVersionUID = 1L;
    private final Address address;

    public static Option<Address> unapply(AddressTerminated x$0) {
        return AddressTerminated$.MODULE$.unapply(x$0);
    }

    public static AddressTerminated apply(Address address) {
        return AddressTerminated$.MODULE$.apply(address);
    }

    public static <A> Function1<Address, A> andThen(Function1<AddressTerminated, A> g2) {
        return AddressTerminated$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, AddressTerminated> compose(Function1<A, Address> g2) {
        return AddressTerminated$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Address address() {
        return this.address;
    }

    public AddressTerminated copy(Address address) {
        return new AddressTerminated(address);
    }

    public Address copy$default$1() {
        return this.address();
    }

    @Override
    public String productPrefix() {
        return "AddressTerminated";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.address();
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
        return x$1 instanceof AddressTerminated;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "address";
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
        if (!(object instanceof AddressTerminated)) return false;
        boolean bl = true;
        if (!bl) return false;
        AddressTerminated addressTerminated = (AddressTerminated)x$1;
        Address address = this.address();
        Address address2 = addressTerminated.address();
        if (address != null) {
            if (!((Object)address).equals(address2)) return false;
            return true;
        }
        if (address2 == null) return true;
        return false;
    }

    public AddressTerminated(Address address) {
        this.address = address;
        Product.$init$(this);
    }
}

