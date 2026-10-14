/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.Address;
import inet.ipaddr.AddressSegmentSeries;
import inet.ipaddr.HostIdentifierException;
import inet.ipaddr.IPAddressSegmentSeries;
import inet.ipaddr.format.util.AddressTrieOps;
import inet.ipaddr.format.util.BinaryTreeNode;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

abstract class AbstractTree<E extends Address>
implements AddressTrieOps.AddressTrieAddOps<E> {
    private static final long serialVersionUID = 1L;
    static ResourceBundle bundle;
    BinaryTreeNode<E> root;

    static String getMessage(String key) {
        if (bundle != null) {
            try {
                return bundle.getString(key);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return key;
    }

    protected AbstractTree(BinaryTreeNode<E> root) {
        this.root = root;
    }

    public BinaryTreeNode<E> getRoot() {
        return this.root;
    }

    public int size() {
        return this.getRoot().size();
    }

    public int nodeSize() {
        return this.getRoot().nodeSize();
    }

    static <E extends Address> E checkBlockOrAddress(E addr, boolean thro) {
        IPAddressSegmentSeries series;
        if (!addr.isMultiple()) {
            if (!addr.isPrefixed()) {
                return addr;
            }
            return (E)addr.withoutPrefixLength();
        }
        if (addr.isSinglePrefixBlock()) {
            return addr;
        }
        if (addr instanceof IPAddressSegmentSeries) {
            series = ((IPAddressSegmentSeries)((Object)addr)).assignPrefixForSingleBlock();
        } else {
            Integer newPrefix = addr.getPrefixLengthForSingleBlock();
            AddressSegmentSeries addressSegmentSeries = series = newPrefix == null ? null : addr.setPrefixLength(newPrefix, false);
        }
        if (series != null) {
            return (E)((Address)((Object)series));
        }
        if (thro) {
            throw new IllegalArgumentException(AbstractTree.getMessage("ipaddress.error.address.not.block"));
        }
        return null;
    }

    public void clear() {
        this.getRoot().clear();
    }

    @Override
    public Iterator<E> iterator() {
        return new BinaryTreeNode.KeyIterator(this.nodeIterator(true));
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new BinaryTreeNode.KeyIterator(this.nodeIterator(false));
    }

    public int hashCode() {
        int hashCode = 0;
        Iterator these = this.nodeIterator(true);
        while (these.hasNext()) {
            BinaryTreeNode node = these.next();
            hashCode += node.hashCode();
        }
        return hashCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbstractTree) {
            AbstractTree other = (AbstractTree)o;
            if (other.size() != this.size()) {
                return false;
            }
            Iterator these = this.nodeIterator(true);
            Iterator others = other.nodeIterator(true);
            while (these.hasNext()) {
                BinaryTreeNode otherNode;
                BinaryTreeNode node = these.next();
                if (node.equals(otherNode = (BinaryTreeNode)others.next())) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public String toString() {
        return this.toString(true);
    }

    public String toString(boolean withNonAddedKeys) {
        return this.getRoot().toTreeString(withNonAddedKeys, true);
    }

    public AbstractTree<E> clone() {
        try {
            return (AbstractTree)super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    static {
        String propertyFileName = "IPAddressResources";
        String name = HostIdentifierException.class.getPackage().getName() + '.' + propertyFileName;
        try {
            bundle = ResourceBundle.getBundle(name);
        }
        catch (MissingResourceException e) {
            System.err.println("bundle " + name + " is missing");
        }
    }
}

