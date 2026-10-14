/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ejb.EJBObject
 */
package javax.management.j2ee;

import java.rmi.RemoteException;
import java.util.Set;
import javax.ejb.EJBObject;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.j2ee.ListenerRegistration;

public interface Management
extends EJBObject {
    public Object getAttribute(ObjectName var1, String var2) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, RemoteException;

    public AttributeList getAttributes(ObjectName var1, String[] var2) throws InstanceNotFoundException, ReflectionException, RemoteException;

    public String getDefaultDomain() throws RemoteException;

    public Integer getMBeanCount() throws RemoteException;

    public MBeanInfo getMBeanInfo(ObjectName var1) throws IntrospectionException, InstanceNotFoundException, ReflectionException, RemoteException;

    public Object invoke(ObjectName var1, String var2, Object[] var3, String[] var4) throws InstanceNotFoundException, MBeanException, ReflectionException, RemoteException;

    public boolean isRegistered(ObjectName var1) throws RemoteException;

    public Set queryNames(ObjectName var1, QueryExp var2) throws RemoteException;

    public void setAttribute(ObjectName var1, Attribute var2) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, RemoteException;

    public AttributeList setAttributes(ObjectName var1, AttributeList var2) throws InstanceNotFoundException, ReflectionException, RemoteException;

    public ListenerRegistration getListenerRegistry() throws RemoteException;
}

