/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.management.Attribute;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.management.remote.rmi.RMIJRMPServerImpl;
import org.apache.activemq.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import sun.rmi.registry.RegistryImpl;

public class ManagementContext
implements Service {
    public static final String DEFAULT_DOMAIN = "org.apache.activemq";
    public static final String DEFAULT_LOOKUP_NAME = "jmxrmi";
    public static final boolean DEFAULT_CREATE_CONNECTOR;
    private static final Logger LOG;
    private MBeanServer beanServer;
    private String jmxDomainName = "org.apache.activemq";
    private boolean useMBeanServer = true;
    private boolean createMBeanServer = true;
    private boolean locallyCreateMBeanServer;
    private boolean createConnector = DEFAULT_CREATE_CONNECTOR;
    private boolean findTigerMbeanServer = true;
    private String connectorHost = "localhost";
    private int connectorPort = 1099;
    private Map<String, ?> environment;
    private int rmiServerPort;
    private String connectorPath = "/jmxrmi";
    private String lookupName = "jmxrmi";
    private final AtomicBoolean started = new AtomicBoolean(false);
    private final CountDownLatch connectorStarted = new CountDownLatch(1);
    private JMXConnectorServer connectorServer;
    private ObjectName namingServiceObjectName;
    private Registry registry;
    private final Map<ObjectName, ObjectName> registeredMBeanNames = new ConcurrentHashMap<ObjectName, ObjectName>();
    private boolean allowRemoteAddressInMBeanNames = true;
    private String brokerName;
    private String suppressMBean;
    private List<ObjectName> suppressMBeanList;
    private Remote serverStub;
    private RMIJRMPServerImpl server;

    public ManagementContext() {
        this(null);
    }

    public ManagementContext(MBeanServer server) {
        this.beanServer = server;
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            this.populateMBeanSuppressionMap();
            if (this.connectorHost == null) {
                this.connectorHost = "localhost";
            }
            this.getMBeanServer();
            if (this.connectorServer != null) {
                try {
                    if (this.getMBeanServer().isRegistered(this.namingServiceObjectName)) {
                        LOG.debug("Invoking start on mbean: {}", (Object)this.namingServiceObjectName);
                        this.getMBeanServer().invoke(this.namingServiceObjectName, "start", null, null);
                    }
                }
                catch (Throwable ignore) {
                    LOG.debug("Error invoking start on MBean {}. This exception is ignored.", (Object)this.namingServiceObjectName, (Object)ignore);
                }
                Thread t2 = new Thread("JMX connector"){

                    @Override
                    public void run() {
                        block10: {
                            if (ManagementContext.this.brokerName != null) {
                                MDC.put("activemq.broker", ManagementContext.this.brokerName);
                            }
                            try {
                                if (!ManagementContext.this.started.get() || ManagementContext.this.server == null) break block10;
                                LOG.debug("Starting JMXConnectorServer...");
                                try {
                                    MDC.remove("activemq.broker");
                                    ManagementContext.this.connectorServer.start();
                                    ManagementContext.this.serverStub = ManagementContext.this.server.toStub();
                                }
                                finally {
                                    if (ManagementContext.this.brokerName != null) {
                                        MDC.put("activemq.broker", ManagementContext.this.brokerName);
                                    }
                                    ManagementContext.this.connectorStarted.countDown();
                                }
                                LOG.info("JMX consoles can connect to {}", (Object)ManagementContext.this.connectorServer.getAddress());
                            }
                            catch (IOException e) {
                                LOG.warn("Failed to start JMX connector {}. Will restart management to re-create JMX connector, trying to remedy this issue.", (Object)e.getMessage());
                                LOG.debug("Reason for failed JMX connector start", e);
                            }
                            finally {
                                MDC.remove("activemq.broker");
                            }
                        }
                    }
                };
                t2.setDaemon(true);
                t2.start();
            }
        }
    }

    private void populateMBeanSuppressionMap() throws Exception {
        if (this.suppressMBean != null) {
            this.suppressMBeanList = new LinkedList<ObjectName>();
            for (String pair : this.suppressMBean.split(",")) {
                this.suppressMBeanList.add(new ObjectName(this.jmxDomainName + ":*," + pair));
            }
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            ArrayList<MBeanServer> list;
            MBeanServer mbeanServer = this.getMBeanServer();
            if (mbeanServer != null) {
                for (Map.Entry<ObjectName, ObjectName> entry : this.registeredMBeanNames.entrySet()) {
                    ObjectName actualName = entry.getValue();
                    if (actualName == null || !this.beanServer.isRegistered(actualName)) continue;
                    LOG.debug("Unregistering MBean {}", (Object)actualName);
                    mbeanServer.unregisterMBean(actualName);
                }
            }
            this.registeredMBeanNames.clear();
            JMXConnectorServer server = this.connectorServer;
            this.connectorServer = null;
            if (server != null) {
                try {
                    if (this.connectorStarted.await(10L, TimeUnit.SECONDS)) {
                        LOG.debug("Stopping jmx connector");
                        server.stop();
                    }
                }
                catch (IOException e) {
                    LOG.warn("Failed to stop jmx connector: {}", (Object)e.getMessage());
                }
                try {
                    if (this.namingServiceObjectName != null && this.getMBeanServer().isRegistered(this.namingServiceObjectName)) {
                        LOG.debug("Stopping MBean {}", (Object)this.namingServiceObjectName);
                        this.getMBeanServer().invoke(this.namingServiceObjectName, "stop", null, null);
                        LOG.debug("Unregistering MBean {}", (Object)this.namingServiceObjectName);
                        this.getMBeanServer().unregisterMBean(this.namingServiceObjectName);
                    }
                }
                catch (Throwable ignore) {
                    LOG.warn("Error stopping and unregsitering MBean {} due to {}", (Object)this.namingServiceObjectName, (Object)ignore.getMessage());
                }
                this.namingServiceObjectName = null;
            }
            if (this.locallyCreateMBeanServer && this.beanServer != null && (list = MBeanServerFactory.findMBeanServer(null)) != null && !list.isEmpty() && list.contains(this.beanServer)) {
                LOG.debug("Releasing MBeanServer {}", (Object)this.beanServer);
                MBeanServerFactory.releaseMBeanServer(this.beanServer);
            }
            this.beanServer = null;
        }
        if (this.registry != null) {
            try {
                UnicastRemoteObject.unexportObject(this.registry, true);
                LOG.debug("Unexported JMX RMI Registry");
            }
            catch (NoSuchObjectException e) {
                LOG.debug("Error occurred while unexporting JMX RMI registry. This exception will be ignored.");
            }
            this.registry = null;
        }
    }

    public String getBrokerName() {
        return this.brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getJmxDomainName() {
        return this.jmxDomainName;
    }

    public void setJmxDomainName(String jmxDomainName) {
        this.jmxDomainName = jmxDomainName;
    }

    public MBeanServer getMBeanServer() {
        if (this.beanServer == null) {
            this.beanServer = this.findMBeanServer();
        }
        return this.beanServer;
    }

    public void setMBeanServer(MBeanServer beanServer) {
        this.beanServer = beanServer;
    }

    public boolean isUseMBeanServer() {
        return this.useMBeanServer;
    }

    public void setUseMBeanServer(boolean useMBeanServer) {
        this.useMBeanServer = useMBeanServer;
    }

    public boolean isCreateMBeanServer() {
        return this.createMBeanServer;
    }

    public void setCreateMBeanServer(boolean enableJMX) {
        this.createMBeanServer = enableJMX;
    }

    public boolean isFindTigerMbeanServer() {
        return this.findTigerMbeanServer;
    }

    public boolean isConnectorStarted() {
        return this.connectorStarted.getCount() == 0L || this.connectorServer != null && this.connectorServer.isActive();
    }

    public void setFindTigerMbeanServer(boolean findTigerMbeanServer) {
        this.findTigerMbeanServer = findTigerMbeanServer;
    }

    public ObjectName createCustomComponentMBeanName(String type, String name) {
        ObjectName result = null;
        String tmp = this.jmxDomainName + ":type=" + ManagementContext.sanitizeString(type) + ",name=" + ManagementContext.sanitizeString(name);
        try {
            result = new ObjectName(tmp);
        }
        catch (MalformedObjectNameException e) {
            LOG.error("Couldn't create ObjectName from: {}, {}", (Object)type, (Object)name);
        }
        return result;
    }

    private static String sanitizeString(String in) {
        String result = null;
        if (in != null) {
            result = in.replace(':', '_');
            result = result.replace('/', '_');
            result = result.replace('\\', '_');
        }
        return result;
    }

    public static ObjectName getSystemObjectName(String domainName, String containerName, Class<?> theClass) throws MalformedObjectNameException, NullPointerException {
        String tmp = domainName + ":type=" + theClass.getName() + ",name=" + ManagementContext.getRelativeName(containerName, theClass);
        return new ObjectName(tmp);
    }

    private static String getRelativeName(String containerName, Class<?> theClass) {
        String name = theClass.getName();
        int index = name.lastIndexOf(".");
        if (index >= 0 && index + 1 < name.length()) {
            name = name.substring(index + 1);
        }
        return containerName + "." + name;
    }

    public Object newProxyInstance(ObjectName objectName, Class<?> interfaceClass, boolean notificationBroadcaster) {
        return MBeanServerInvocationHandler.newProxyInstance(this.getMBeanServer(), objectName, interfaceClass, notificationBroadcaster);
    }

    public Object getAttribute(ObjectName name, String attribute) throws Exception {
        return this.getMBeanServer().getAttribute(name, attribute);
    }

    public ObjectInstance registerMBean(Object bean, ObjectName name) throws Exception {
        ObjectInstance result = null;
        if (this.isAllowedToRegister(name)) {
            result = this.getMBeanServer().registerMBean(bean, name);
            this.registeredMBeanNames.put(name, result.getObjectName());
        }
        return result;
    }

    protected boolean isAllowedToRegister(ObjectName name) {
        boolean result = true;
        if (this.suppressMBean != null && this.suppressMBeanList != null) {
            for (ObjectName attr : this.suppressMBeanList) {
                if (!attr.apply(name)) continue;
                result = false;
                break;
            }
        }
        return result;
    }

    public Set<ObjectName> queryNames(ObjectName name, QueryExp query) throws Exception {
        ObjectName actualName;
        if (name != null && (actualName = this.registeredMBeanNames.get(name)) != null) {
            return this.getMBeanServer().queryNames(actualName, query);
        }
        return this.getMBeanServer().queryNames(name, query);
    }

    public ObjectInstance getObjectInstance(ObjectName name) throws InstanceNotFoundException {
        return this.getMBeanServer().getObjectInstance(name);
    }

    public void unregisterMBean(ObjectName name) throws JMException {
        ObjectName actualName = this.registeredMBeanNames.get(name);
        if (this.beanServer != null && actualName != null && this.beanServer.isRegistered(actualName) && this.registeredMBeanNames.remove(name) != null) {
            LOG.debug("Unregistering MBean {}", (Object)actualName);
            this.beanServer.unregisterMBean(actualName);
        }
    }

    protected synchronized MBeanServer findMBeanServer() {
        MBeanServer result = null;
        try {
            if (this.useMBeanServer) {
                ArrayList<MBeanServer> list;
                if (this.findTigerMbeanServer) {
                    result = this.findTigerMBeanServer();
                }
                if (result == null && (list = MBeanServerFactory.findMBeanServer(null)) != null && list.size() > 0) {
                    result = (MBeanServer)list.get(0);
                }
            }
            if (result == null && this.createMBeanServer) {
                result = this.createMBeanServer();
            }
        }
        catch (NoClassDefFoundError e) {
            LOG.error("Could not load MBeanServer", e);
        }
        catch (Throwable e) {
            LOG.error("Failed to initialize MBeanServer", e);
        }
        return result;
    }

    public MBeanServer findTigerMBeanServer() {
        block7: {
            String name = "java.lang.management.ManagementFactory";
            Class<?> type = ManagementContext.loadClass(name, ManagementContext.class.getClassLoader());
            if (type != null) {
                try {
                    Method method2 = type.getMethod("getPlatformMBeanServer", new Class[0]);
                    if (method2 != null) {
                        Object answer = method2.invoke(null, new Object[0]);
                        if (answer instanceof MBeanServer) {
                            if (this.createConnector) {
                                this.createConnector((MBeanServer)answer);
                            }
                            return (MBeanServer)answer;
                        }
                        LOG.warn("Could not cast: {} into an MBeanServer. There must be some classloader strangeness in town", answer);
                        break block7;
                    }
                    LOG.warn("Method getPlatformMBeanServer() does not appear visible on type: {}", (Object)type.getName());
                }
                catch (Exception e) {
                    LOG.warn("Failed to call getPlatformMBeanServer() due to: ", e);
                }
            } else {
                LOG.trace("Class not found: {} so probably running on Java 1.4", (Object)name);
            }
        }
        return null;
    }

    private static Class<?> loadClass(String name, ClassLoader loader) {
        try {
            return loader.loadClass(name);
        }
        catch (ClassNotFoundException e) {
            try {
                return Thread.currentThread().getContextClassLoader().loadClass(name);
            }
            catch (ClassNotFoundException e1) {
                return null;
            }
        }
    }

    protected MBeanServer createMBeanServer() throws MalformedObjectNameException, IOException {
        MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer(this.jmxDomainName);
        this.locallyCreateMBeanServer = true;
        if (this.createConnector) {
            this.createConnector(mbeanServer);
        }
        return mbeanServer;
    }

    private void createConnector(MBeanServer mbeanServer) throws MalformedObjectNameException, IOException {
        try {
            if (this.registry == null) {
                LOG.debug("Creating RMIRegistry on port {}", (Object)this.connectorPort);
                this.registry = new JmxRegistry(this.connectorPort);
            }
            this.namingServiceObjectName = ObjectName.getInstance("naming:type=rmiregistry");
            Class<?> cl = Class.forName("mx4j.tools.naming.NamingService");
            mbeanServer.registerMBean(cl.newInstance(), this.namingServiceObjectName);
            Attribute attr = new Attribute("Port", this.connectorPort);
            mbeanServer.setAttribute(this.namingServiceObjectName, attr);
        }
        catch (ClassNotFoundException e) {
            LOG.debug("Probably not using JRE 1.4: {}", (Object)e.getLocalizedMessage());
        }
        catch (Throwable e) {
            LOG.debug("Failed to create local registry. This exception will be ignored.", e);
        }
        String rmiServer = "";
        if (this.rmiServerPort != 0) {
            rmiServer = "" + this.getConnectorHost() + ":" + this.rmiServerPort;
        }
        this.server = new RMIJRMPServerImpl(this.connectorPort, null, null, this.environment);
        String serviceURL = "service:jmx:rmi://" + rmiServer + "/jndi/rmi://" + this.getConnectorHost() + ":" + this.connectorPort + this.connectorPath;
        JMXServiceURL url2 = new JMXServiceURL(serviceURL);
        this.connectorServer = new RMIConnectorServer(url2, this.environment, this.server, ManagementFactory.getPlatformMBeanServer());
        LOG.debug("Created JMXConnectorServer {}", (Object)this.connectorServer);
    }

    public String getConnectorPath() {
        return this.connectorPath;
    }

    public void setConnectorPath(String connectorPath) {
        this.connectorPath = connectorPath;
        this.lookupName = connectorPath == null || connectorPath.length() == 0 ? DEFAULT_LOOKUP_NAME : connectorPath.replaceAll("^/+", "").replaceAll("/+$", "");
    }

    public int getConnectorPort() {
        return this.connectorPort;
    }

    public void setConnectorPort(int connectorPort) {
        this.connectorPort = connectorPort;
    }

    public int getRmiServerPort() {
        return this.rmiServerPort;
    }

    public void setRmiServerPort(int rmiServerPort) {
        this.rmiServerPort = rmiServerPort;
    }

    public boolean isCreateConnector() {
        return this.createConnector;
    }

    public void setCreateConnector(boolean createConnector) {
        this.createConnector = createConnector;
    }

    public String getConnectorHost() {
        return this.connectorHost;
    }

    public void setConnectorHost(String connectorHost) {
        this.connectorHost = connectorHost;
    }

    public Map<String, ?> getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(Map<String, ?> environment) {
        this.environment = environment;
    }

    public boolean isAllowRemoteAddressInMBeanNames() {
        return this.allowRemoteAddressInMBeanNames;
    }

    public void setAllowRemoteAddressInMBeanNames(boolean allowRemoteAddressInMBeanNames) {
        this.allowRemoteAddressInMBeanNames = allowRemoteAddressInMBeanNames;
    }

    public void setSuppressMBean(String commaListOfAttributeKeyValuePairs) {
        this.suppressMBean = commaListOfAttributeKeyValuePairs;
    }

    public String getSuppressMBean() {
        return this.suppressMBean;
    }

    static {
        String option = Boolean.TRUE.toString();
        try {
            option = System.getProperty("org.apache.activemq.broker.jmx.createConnector", "true");
        }
        catch (Exception exception) {
            // empty catch block
        }
        DEFAULT_CREATE_CONNECTOR = Boolean.valueOf(option);
        LOG = LoggerFactory.getLogger(ManagementContext.class);
    }

    private class JmxRegistry
    extends RegistryImpl {
        public JmxRegistry(int port) throws RemoteException {
            super(port);
        }

        @Override
        public Remote lookup(String s2) throws RemoteException, NotBoundException {
            return ManagementContext.this.lookupName.equals(s2) ? ManagementContext.this.serverStub : null;
        }

        @Override
        public void bind(String s2, Remote remote) throws RemoteException, AlreadyBoundException, AccessException {
        }

        @Override
        public void unbind(String s2) throws RemoteException, NotBoundException, AccessException {
        }

        @Override
        public void rebind(String s2, Remote remote) throws RemoteException, AccessException {
        }

        @Override
        public String[] list() throws RemoteException {
            return new String[]{ManagementContext.this.lookupName};
        }
    }
}

