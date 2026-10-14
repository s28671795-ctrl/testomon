/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.process;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;

public interface Endpoint
extends Inflector<RequestProcessingContext, ContainerResponse> {
}

