/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.websocket.api.BatchMode;
import org.eclipse.jetty.websocket.api.Frame;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketConnectionListener;
import org.eclipse.jetty.websocket.api.WebSocketContainer;
import org.eclipse.jetty.websocket.api.WebSocketFrameListener;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.WebSocketPartialListener;
import org.eclipse.jetty.websocket.api.WebSocketPingPongListener;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketFrame;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.common.JettyWebSocketFrameHandler;
import org.eclipse.jetty.websocket.common.JettyWebSocketFrameHandlerMetadata;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.exception.InvalidSignatureException;
import org.eclipse.jetty.websocket.core.exception.InvalidWebSocketException;
import org.eclipse.jetty.websocket.core.internal.messages.ByteArrayMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.ByteBufferMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.InputStreamMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.MessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.PartialByteBufferMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.PartialStringMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.ReaderMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.StringMessageSink;
import org.eclipse.jetty.websocket.core.internal.util.InvokerUtils;
import org.eclipse.jetty.websocket.core.internal.util.ReflectUtils;

public class JettyWebSocketFrameHandlerFactory
extends ContainerLifeCycle {
    private static final InvokerUtils.Arg[] textCallingArgs = new InvokerUtils.Arg[]{new InvokerUtils.Arg(Session.class), new InvokerUtils.Arg(String.class).required()};
    private static final InvokerUtils.Arg[] binaryBufferCallingArgs = new InvokerUtils.Arg[]{new InvokerUtils.Arg(Session.class), new InvokerUtils.Arg(ByteBuffer.class).required()};
    private static final InvokerUtils.Arg[] binaryArrayCallingArgs = new InvokerUtils.Arg[]{new InvokerUtils.Arg(Session.class), new InvokerUtils.Arg(byte[].class).required(), new InvokerUtils.Arg(Integer.TYPE), new InvokerUtils.Arg(Integer.TYPE)};
    private static final InvokerUtils.Arg[] inputStreamCallingArgs = new InvokerUtils.Arg[]{new InvokerUtils.Arg(Session.class), new InvokerUtils.Arg(InputStream.class).required()};
    private static final InvokerUtils.Arg[] readerCallingArgs = new InvokerUtils.Arg[]{new InvokerUtils.Arg(Session.class), new InvokerUtils.Arg(Reader.class).required()};
    private static final InvokerUtils.Arg[] textPartialCallingArgs = new InvokerUtils.Arg[]{new InvokerUtils.Arg(Session.class), new InvokerUtils.Arg(String.class).required(), new InvokerUtils.Arg(Boolean.TYPE).required()};
    private static final InvokerUtils.Arg[] binaryPartialBufferCallingArgs = new InvokerUtils.Arg[]{new InvokerUtils.Arg(Session.class), new InvokerUtils.Arg(ByteBuffer.class).required(), new InvokerUtils.Arg(Boolean.TYPE).required()};
    private static final InvokerUtils.Arg[] binaryPartialArrayCallingArgs = new InvokerUtils.Arg[]{new InvokerUtils.Arg(Session.class), new InvokerUtils.Arg(byte[].class).required(), new InvokerUtils.Arg(Boolean.TYPE).required()};
    private final WebSocketContainer container;
    private final WebSocketComponents components;
    private final Map<Class<?>, JettyWebSocketFrameHandlerMetadata> metadataMap = new ConcurrentHashMap();

    public JettyWebSocketFrameHandlerFactory(WebSocketContainer container, WebSocketComponents components) {
        this.container = container;
        this.components = components;
    }

    public WebSocketComponents getWebSocketComponents() {
        return this.components;
    }

    public JettyWebSocketFrameHandlerMetadata getMetadata(Class<?> endpointClass) {
        JettyWebSocketFrameHandlerMetadata metadata = this.metadataMap.get(endpointClass);
        if (metadata == null) {
            metadata = this.createMetadata(endpointClass);
            this.metadataMap.put(endpointClass, metadata);
        }
        return metadata;
    }

    public JettyWebSocketFrameHandlerMetadata createMetadata(Class<?> endpointClass) {
        if (WebSocketConnectionListener.class.isAssignableFrom(endpointClass)) {
            return this.createListenerMetadata(endpointClass);
        }
        WebSocket websocket = endpointClass.getAnnotation(WebSocket.class);
        if (websocket != null) {
            return this.createAnnotatedMetadata(websocket, endpointClass);
        }
        throw new InvalidWebSocketException("Unrecognized WebSocket endpoint: " + endpointClass.getName());
    }

    public JettyWebSocketFrameHandler newJettyFrameHandler(Object endpointInstance) {
        JettyWebSocketFrameHandlerMetadata metadata = this.getMetadata(endpointInstance.getClass());
        MethodHandle openHandle = InvokerUtils.bindTo(metadata.getOpenHandle(), endpointInstance);
        MethodHandle closeHandle = InvokerUtils.bindTo(metadata.getCloseHandle(), endpointInstance);
        MethodHandle errorHandle = InvokerUtils.bindTo(metadata.getErrorHandle(), endpointInstance);
        MethodHandle textHandle = InvokerUtils.bindTo(metadata.getTextHandle(), endpointInstance);
        MethodHandle binaryHandle = InvokerUtils.bindTo(metadata.getBinaryHandle(), endpointInstance);
        Class<? extends MessageSink> textSinkClass = metadata.getTextSink();
        Class<? extends MessageSink> binarySinkClass = metadata.getBinarySink();
        MethodHandle frameHandle = InvokerUtils.bindTo(metadata.getFrameHandle(), endpointInstance);
        MethodHandle pingHandle = InvokerUtils.bindTo(metadata.getPingHandle(), endpointInstance);
        MethodHandle pongHandle = InvokerUtils.bindTo(metadata.getPongHandle(), endpointInstance);
        BatchMode batchMode = metadata.getBatchMode();
        this.components.getObjectFactory().decorate(endpointInstance);
        return new JettyWebSocketFrameHandler(this.container, endpointInstance, openHandle, closeHandle, errorHandle, textHandle, binaryHandle, textSinkClass, binarySinkClass, frameHandle, pingHandle, pongHandle, batchMode, metadata);
    }

    public static MessageSink createMessageSink(MethodHandle msgHandle, Class<? extends MessageSink> sinkClass, Executor executor2, WebSocketSession session) {
        if (msgHandle == null) {
            return null;
        }
        if (sinkClass == null) {
            return null;
        }
        try {
            MethodHandles.Lookup lookup = JettyWebSocketFrameHandlerFactory.getServerMethodHandleLookup();
            MethodHandle ctorHandle = lookup.findConstructor(sinkClass, MethodType.methodType(Void.TYPE, CoreSession.class, MethodHandle.class));
            return ctorHandle.invoke(session.getCoreSession(), msgHandle);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Missing expected MessageSink constructor found at: " + sinkClass.getName(), e);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Unable to create MessageSink: " + sinkClass.getName(), e);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Throwable t2) {
            throw new RuntimeException(t2);
        }
    }

    private MethodHandle toMethodHandle(MethodHandles.Lookup lookup, Method method2) {
        try {
            return lookup.unreflect(method2);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access method " + method2, e);
        }
    }

    private JettyWebSocketFrameHandlerMetadata createListenerMetadata(Class<?> endpointClass) {
        MethodHandle binary;
        Method binaryMethod;
        MethodHandle text;
        Method textMethod;
        JettyWebSocketFrameHandlerMetadata metadata = new JettyWebSocketFrameHandlerMetadata();
        MethodHandles.Lookup lookup = JettyWebSocketFrameHandlerFactory.getServerMethodHandleLookup();
        if (!WebSocketConnectionListener.class.isAssignableFrom(endpointClass)) {
            throw new IllegalArgumentException("Class " + endpointClass + " does not implement " + WebSocketConnectionListener.class);
        }
        Method openMethod = ReflectUtils.findMethod(WebSocketConnectionListener.class, "onWebSocketConnect", Session.class);
        MethodHandle open = this.toMethodHandle(lookup, openMethod);
        metadata.setOpenHandler(open, openMethod);
        Method closeMethod = ReflectUtils.findMethod(WebSocketConnectionListener.class, "onWebSocketClose", Integer.TYPE, String.class);
        MethodHandle close = this.toMethodHandle(lookup, closeMethod);
        metadata.setCloseHandler(close, closeMethod);
        Method errorMethod = ReflectUtils.findMethod(WebSocketConnectionListener.class, "onWebSocketError", Throwable.class);
        MethodHandle error = this.toMethodHandle(lookup, errorMethod);
        metadata.setErrorHandler(error, errorMethod);
        if (WebSocketListener.class.isAssignableFrom(endpointClass)) {
            textMethod = ReflectUtils.findMethod(WebSocketListener.class, "onWebSocketText", String.class);
            text = this.toMethodHandle(lookup, textMethod);
            metadata.setTextHandler(StringMessageSink.class, text, textMethod);
            binaryMethod = ReflectUtils.findMethod(WebSocketListener.class, "onWebSocketBinary", byte[].class, Integer.TYPE, Integer.TYPE);
            binary = this.toMethodHandle(lookup, binaryMethod);
            metadata.setBinaryHandle(ByteArrayMessageSink.class, binary, binaryMethod);
        }
        if (WebSocketPingPongListener.class.isAssignableFrom(endpointClass)) {
            Method pongMethod = ReflectUtils.findMethod(WebSocketPingPongListener.class, "onWebSocketPong", ByteBuffer.class);
            MethodHandle pong = this.toMethodHandle(lookup, pongMethod);
            metadata.setPongHandle(pong, pongMethod);
            Method pingMethod = ReflectUtils.findMethod(WebSocketPingPongListener.class, "onWebSocketPing", ByteBuffer.class);
            MethodHandle ping = this.toMethodHandle(lookup, pingMethod);
            metadata.setPingHandle(ping, pingMethod);
        }
        if (WebSocketPartialListener.class.isAssignableFrom(endpointClass)) {
            textMethod = ReflectUtils.findMethod(WebSocketPartialListener.class, "onWebSocketPartialText", String.class, Boolean.TYPE);
            text = this.toMethodHandle(lookup, textMethod);
            metadata.setTextHandler(PartialStringMessageSink.class, text, textMethod);
            binaryMethod = ReflectUtils.findMethod(WebSocketPartialListener.class, "onWebSocketPartialBinary", ByteBuffer.class, Boolean.TYPE);
            binary = this.toMethodHandle(lookup, binaryMethod);
            metadata.setBinaryHandle(PartialByteBufferMessageSink.class, binary, binaryMethod);
        }
        if (WebSocketFrameListener.class.isAssignableFrom(endpointClass)) {
            Method frameMethod = ReflectUtils.findMethod(WebSocketFrameListener.class, "onWebSocketFrame", Frame.class);
            MethodHandle frame = this.toMethodHandle(lookup, frameMethod);
            metadata.setFrameHandler(frame, frameMethod);
        }
        return metadata;
    }

    private JettyWebSocketFrameHandlerMetadata createAnnotatedMetadata(WebSocket anno, Class<?> endpointClass) {
        Method[] onMessages;
        MethodHandle methodHandle;
        InvokerUtils.Arg SESSION;
        JettyWebSocketFrameHandlerMetadata metadata = new JettyWebSocketFrameHandlerMetadata();
        int max = anno.inputBufferSize();
        if (max >= 0) {
            metadata.setInputBufferSize(max);
        }
        if ((max = anno.maxBinaryMessageSize()) >= 0) {
            metadata.setMaxBinaryMessageSize(max);
        }
        if ((max = anno.maxTextMessageSize()) >= 0) {
            metadata.setMaxTextMessageSize(max);
        }
        if ((max = anno.idleTimeout()) >= 0) {
            metadata.setIdleTimeout(Duration.ofMillis(max));
        }
        metadata.setBatchMode(anno.batchMode());
        MethodHandles.Lookup lookup = JettyWebSocketFrameHandlerFactory.getApplicationMethodHandleLookup(endpointClass);
        Method onmethod = ReflectUtils.findAnnotatedMethod(endpointClass, OnWebSocketConnect.class);
        if (onmethod != null) {
            this.assertSignatureValid(endpointClass, onmethod, OnWebSocketConnect.class);
            SESSION = new InvokerUtils.Arg(Session.class).required();
            MethodHandle methodHandle2 = InvokerUtils.mutatedInvoker(lookup, endpointClass, onmethod, SESSION);
            metadata.setOpenHandler(methodHandle2, onmethod);
        }
        if ((onmethod = ReflectUtils.findAnnotatedMethod(endpointClass, OnWebSocketClose.class)) != null) {
            this.assertSignatureValid(endpointClass, onmethod, OnWebSocketClose.class);
            SESSION = new InvokerUtils.Arg(Session.class);
            InvokerUtils.Arg STATUS_CODE = new InvokerUtils.Arg(Integer.TYPE);
            InvokerUtils.Arg REASON = new InvokerUtils.Arg(String.class);
            MethodHandle methodHandle3 = InvokerUtils.mutatedInvoker(lookup, endpointClass, onmethod, SESSION, STATUS_CODE, REASON);
            metadata.setCloseHandler(methodHandle3, onmethod);
        }
        if ((onmethod = ReflectUtils.findAnnotatedMethod(endpointClass, OnWebSocketError.class)) != null) {
            this.assertSignatureValid(endpointClass, onmethod, OnWebSocketError.class);
            SESSION = new InvokerUtils.Arg(Session.class);
            InvokerUtils.Arg CAUSE = new InvokerUtils.Arg(Throwable.class).required();
            methodHandle = InvokerUtils.mutatedInvoker(lookup, endpointClass, onmethod, SESSION, CAUSE);
            metadata.setErrorHandler(methodHandle, onmethod);
        }
        if ((onmethod = ReflectUtils.findAnnotatedMethod(endpointClass, OnWebSocketFrame.class)) != null) {
            this.assertSignatureValid(endpointClass, onmethod, OnWebSocketFrame.class);
            SESSION = new InvokerUtils.Arg(Session.class);
            InvokerUtils.Arg FRAME = new InvokerUtils.Arg(Frame.class).required();
            methodHandle = InvokerUtils.mutatedInvoker(lookup, endpointClass, onmethod, SESSION, FRAME);
            metadata.setFrameHandler(methodHandle, onmethod);
        }
        if ((onMessages = ReflectUtils.findAnnotatedMethods(endpointClass, OnWebSocketMessage.class)) != null && onMessages.length > 0) {
            for (Method onMsg : onMessages) {
                this.assertSignatureValid(endpointClass, onMsg, OnWebSocketMessage.class);
                MethodHandle methodHandle4 = InvokerUtils.optionalMutatedInvoker(lookup, endpointClass, onMsg, textCallingArgs);
                if (methodHandle4 != null) {
                    this.assertSignatureValid(endpointClass, onMsg, OnWebSocketMessage.class);
                    metadata.setTextHandler(StringMessageSink.class, methodHandle4, onMsg);
                    continue;
                }
                methodHandle4 = InvokerUtils.optionalMutatedInvoker(lookup, endpointClass, onMsg, binaryBufferCallingArgs);
                if (methodHandle4 != null) {
                    this.assertSignatureValid(endpointClass, onMsg, OnWebSocketMessage.class);
                    metadata.setBinaryHandle(ByteBufferMessageSink.class, methodHandle4, onMsg);
                    continue;
                }
                methodHandle4 = InvokerUtils.optionalMutatedInvoker(lookup, endpointClass, onMsg, binaryArrayCallingArgs);
                if (methodHandle4 != null) {
                    this.assertSignatureValid(endpointClass, onMsg, OnWebSocketMessage.class);
                    metadata.setBinaryHandle(ByteArrayMessageSink.class, methodHandle4, onMsg);
                    continue;
                }
                methodHandle4 = InvokerUtils.optionalMutatedInvoker(lookup, endpointClass, onMsg, inputStreamCallingArgs);
                if (methodHandle4 != null) {
                    this.assertSignatureValid(endpointClass, onMsg, OnWebSocketMessage.class);
                    metadata.setBinaryHandle(InputStreamMessageSink.class, methodHandle4, onMsg);
                    continue;
                }
                methodHandle4 = InvokerUtils.optionalMutatedInvoker(lookup, endpointClass, onMsg, readerCallingArgs);
                if (methodHandle4 != null) {
                    this.assertSignatureValid(endpointClass, onMsg, OnWebSocketMessage.class);
                    metadata.setTextHandler(ReaderMessageSink.class, methodHandle4, onMsg);
                    continue;
                }
                methodHandle4 = InvokerUtils.optionalMutatedInvoker(lookup, endpointClass, onMsg, textPartialCallingArgs);
                if (methodHandle4 != null) {
                    this.assertSignatureValid(endpointClass, onMsg, OnWebSocketMessage.class);
                    metadata.setTextHandler(PartialStringMessageSink.class, methodHandle4, onMsg);
                    continue;
                }
                methodHandle4 = InvokerUtils.optionalMutatedInvoker(lookup, endpointClass, onMsg, binaryPartialBufferCallingArgs);
                if (methodHandle4 != null) {
                    this.assertSignatureValid(endpointClass, onMsg, OnWebSocketMessage.class);
                    metadata.setBinaryHandle(PartialByteBufferMessageSink.class, methodHandle4, onMsg);
                    continue;
                }
                throw InvalidSignatureException.build(endpointClass, OnWebSocketMessage.class, onMsg);
            }
        }
        return metadata;
    }

    private void assertSignatureValid(Class<?> endpointClass, Method method2, Class<? extends Annotation> annotationClass) {
        int mods = method2.getModifiers();
        if (!Modifier.isPublic(mods)) {
            StringBuilder err = new StringBuilder();
            err.append("@").append(annotationClass.getSimpleName());
            err.append(" method must be public: ");
            ReflectUtils.append(err, endpointClass, method2);
            throw new InvalidSignatureException(err.toString());
        }
        if (Modifier.isStatic(mods)) {
            StringBuilder err = new StringBuilder();
            err.append("@").append(annotationClass.getSimpleName());
            err.append(" method must not be static: ");
            ReflectUtils.append(err, endpointClass, method2);
            throw new InvalidSignatureException(err.toString());
        }
        Class<?> returnType = method2.getReturnType();
        if (returnType == Void.TYPE || returnType == Void.class) {
            return;
        }
        StringBuilder err = new StringBuilder();
        err.append("@").append(annotationClass.getSimpleName());
        err.append(" return must be void: ");
        ReflectUtils.append(err, endpointClass, method2);
        throw new InvalidSignatureException(err.toString());
    }

    public static MethodHandles.Lookup getServerMethodHandleLookup() {
        return MethodHandles.lookup();
    }

    public static MethodHandles.Lookup getApplicationMethodHandleLookup(Class<?> lookupClass) {
        return MethodHandles.publicLookup().in(lookupClass);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        this.dumpObjects(out, indent, this.metadataMap);
    }
}

