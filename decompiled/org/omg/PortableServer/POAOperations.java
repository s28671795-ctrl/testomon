/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.AdapterActivator;
import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.IdUniquenessPolicyValue;
import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerFactory;
import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongAdapter;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.omg.PortableServer.ThreadPolicy;
import org.omg.PortableServer.ThreadPolicyValue;

public interface POAOperations {
    public POA create_POA(String var1, POAManager var2, Policy[] var3) throws AdapterAlreadyExists, InvalidPolicy;

    public POA find_POA(String var1, boolean var2) throws AdapterNonExistent;

    public void destroy(boolean var1, boolean var2);

    public ThreadPolicy create_thread_policy(ThreadPolicyValue var1);

    public LifespanPolicy create_lifespan_policy(LifespanPolicyValue var1);

    public IdUniquenessPolicy create_id_uniqueness_policy(IdUniquenessPolicyValue var1);

    public IdAssignmentPolicy create_id_assignment_policy(IdAssignmentPolicyValue var1);

    public ImplicitActivationPolicy create_implicit_activation_policy(ImplicitActivationPolicyValue var1);

    public ServantRetentionPolicy create_servant_retention_policy(ServantRetentionPolicyValue var1);

    public RequestProcessingPolicy create_request_processing_policy(RequestProcessingPolicyValue var1);

    public String the_name();

    public POA the_parent();

    public POA[] the_children();

    public POAManager the_POAManager();

    public AdapterActivator the_activator();

    public void the_activator(AdapterActivator var1);

    public ServantManager get_servant_manager() throws WrongPolicy;

    public void set_servant_manager(ServantManager var1) throws WrongPolicy;

    public Servant get_servant() throws NoServant, WrongPolicy;

    public void set_servant(Servant var1) throws WrongPolicy;

    public byte[] activate_object(Servant var1) throws ServantAlreadyActive, WrongPolicy;

    public void activate_object_with_id(byte[] var1, Servant var2) throws ServantAlreadyActive, ObjectAlreadyActive, WrongPolicy;

    public void deactivate_object(byte[] var1) throws ObjectNotActive, WrongPolicy;

    public Object create_reference(String var1) throws WrongPolicy;

    public Object create_reference_with_id(byte[] var1, String var2) throws WrongPolicy;

    public byte[] servant_to_id(Servant var1) throws ServantNotActive, WrongPolicy;

    public Object servant_to_reference(Servant var1) throws ServantNotActive, WrongPolicy;

    public Servant reference_to_servant(Object var1) throws WrongAdapter, ObjectNotActive, WrongPolicy;

    public byte[] reference_to_id(Object var1) throws WrongAdapter, WrongPolicy;

    public Servant id_to_servant(byte[] var1) throws ObjectNotActive, WrongPolicy;

    public Object id_to_reference(byte[] var1) throws ObjectNotActive, WrongPolicy;

    public byte[] id();

    public POAManagerFactory the_POAManagerFactory();
}

