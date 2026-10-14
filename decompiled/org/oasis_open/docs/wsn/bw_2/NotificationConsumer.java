/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.br_2.ObjectFactory;

@WebService(targetNamespace="http://docs.oasis-open.org/wsn/bw-2", name="NotificationConsumer")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.rp_2.ObjectFactory.class, org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface NotificationConsumer {
    @WebMethod(operationName="Notify")
    @Oneway
    public void notify(@WebParam(partName="Notify", name="Notify", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Notify var1);
}

