/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LockableClassToInstanceMultiMap;
import org.opensaml.core.xml.Namespace;
import org.opensaml.core.xml.NamespaceManager;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.IDIndex;
import org.w3c.dom.Element;

public interface XMLObject {
    public void detach();

    @Nullable
    public Element getDOM();

    @Nonnull
    public QName getElementQName();

    @Nonnull
    public IDIndex getIDIndex();

    @Nonnull
    public NamespaceManager getNamespaceManager();

    @Nonnull
    public Set<Namespace> getNamespaces();

    @Nullable
    public String getNoNamespaceSchemaLocation();

    @Nullable
    public List<XMLObject> getOrderedChildren();

    @Nullable
    public XMLObject getParent();

    @Nullable
    public String getSchemaLocation();

    @Nullable
    public QName getSchemaType();

    public boolean hasChildren();

    public boolean hasParent();

    public void releaseChildrenDOM(boolean var1);

    public void releaseDOM();

    public void releaseParentDOM(boolean var1);

    @Nullable
    public XMLObject resolveID(@Nonnull String var1);

    @Nullable
    public XMLObject resolveIDFromRoot(@Nonnull String var1);

    public void setDOM(@Nullable Element var1);

    public void setNoNamespaceSchemaLocation(@Nullable String var1);

    public void setParent(@Nullable XMLObject var1);

    public void setSchemaLocation(@Nullable String var1);

    @Nullable
    public Boolean isNil();

    @Nullable
    public XSBooleanValue isNilXSBoolean();

    public void setNil(@Nullable Boolean var1);

    public void setNil(@Nullable XSBooleanValue var1);

    @Nonnull
    public LockableClassToInstanceMultiMap<Object> getObjectMetadata();
}

