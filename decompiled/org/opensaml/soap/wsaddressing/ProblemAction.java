/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.soap.wsaddressing.Action;
import org.opensaml.soap.wsaddressing.SoapAction;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface ProblemAction
extends AttributeExtensibleXMLObject,
WSAddressingObject {
    public static final String ELEMENT_LOCAL_NAME = "ProblemAction";
    public static final QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "ProblemAction", "wsa");
    public static final String TYPE_LOCAL_NAME = "ProblemActionType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "ProblemActionType", "wsa");

    public Action getAction();

    public void setAction(Action var1);

    public SoapAction getSoapAction();

    public void setSoapAction(SoapAction var1);
}

