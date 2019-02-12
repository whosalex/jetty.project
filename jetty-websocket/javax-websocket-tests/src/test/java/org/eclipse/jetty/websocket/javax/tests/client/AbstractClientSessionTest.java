//
//  ========================================================================
//  Copyright (c) 1995-2019 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.websocket.javax.tests.client;

import javax.websocket.EndpointConfig;

import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.javax.tests.DummyEndpoint;
import org.eclipse.jetty.websocket.javax.common.JavaxWebSocketContainer;
import org.eclipse.jetty.websocket.javax.common.JavaxWebSocketFrameHandler;
import org.eclipse.jetty.websocket.javax.common.JavaxWebSocketSession;
import org.eclipse.jetty.websocket.javax.common.UpgradeRequest;
import org.eclipse.jetty.websocket.javax.common.UpgradeRequestAdapter;
import org.eclipse.jetty.websocket.javax.common.UpgradeResponse;
import org.eclipse.jetty.websocket.javax.common.UpgradeResponseAdapter;
import org.eclipse.jetty.websocket.javax.client.JavaxWebSocketClientContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractClientSessionTest
{
    protected static JavaxWebSocketSession session;
    protected static JavaxWebSocketContainer container;

    @BeforeAll
    public static void initSession() throws Exception
    {
        container = new JavaxWebSocketClientContainer();
        container.start();
        Object websocketPojo = new DummyEndpoint();
        UpgradeRequest upgradeRequest = new UpgradeRequestAdapter();
        UpgradeResponse upgradeResponse = new UpgradeResponseAdapter();
        JavaxWebSocketFrameHandler frameHandler =
            container.newFrameHandler(websocketPojo, upgradeRequest, upgradeResponse, null);
        FrameHandler.CoreSession channel = new FrameHandler.CoreSession.Empty();
        String id = "dummy";
        EndpointConfig endpointConfig = null;
        session = new JavaxWebSocketSession(container,
            channel,
            frameHandler,
            null,
            id,
            endpointConfig);
        container.addManaged(session);
    }

    @AfterAll
    public static void stopContainer() throws Exception
    {
        container.stop();
    }
}