/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License, version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.Closeable;
import java.util.List;

/**
 * Handler for inbound traffic on behalf of {@link Http2ConnectionHandler}.
 */
public interface Http2ConnectionDecoder extends Closeable {

    /**
     * Builder for new instances of {@link Http2ConnectionDecoder}.
     */
    public interface Builder {

        /**
         * Sets the {@link Http2Connection} to be used when building the decoder.
         */
        Builder connection(Http2Connection connection);

        /**
         * Sets the {@link LifecycleManager} to be used when building the decoder.
         */
        Builder lifecycleManager(Http2LifecycleManager lifecycleManager);

        /**
         * Sets the {@link Http2InboundFlowController} to be used when building the decoder.
         */
        Builder inboundFlow(Http2InboundFlowController inboundFlow);

        /**
         * Sets the {@link Http2FrameReader} to be used when building the decoder.
         */
        Builder frameReader(Http2FrameReader frameReader);

        /**
         * Sets the {@link Http2FrameListener} to be used when building the decoder.
         */
        Builder listener(Http2FrameListener listener);

        /**
         * Sets the {@link Http2ConnectionEncoder} used when building the decoder.
         */
        Builder encoder(Http2ConnectionEncoder encoder);

        /**
         * Creates a new decoder instance.
         */
        Http2ConnectionDecoder build();
    }

    /**
     * Provides direct access to the underlying connection.
     */
    Http2Connection connection();

    /**
     * Provides direct access to the underlying frame listener.
     */
    Http2FrameListener listener();

    /**
     * Called by the {@link Http2ConnectionHandler} to decode the next frame from the input buffer.
     */
    void decodeFrame(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Http2Exception;

    /**
     * Gets the local settings for this endpoint of the HTTP/2 connection.
     */
    Http2Settings localSettings();

    /**
     * Sets the local settings for this endpoint of the HTTP/2 connection.
     */
    void localSettings(Http2Settings settings) throws Http2Exception;

    /**
     * Indicates whether or not the first initial {@code SETTINGS} frame was received from the remote endpoint.
     */
    boolean prefaceReceived();

    @Override
    void close();
}
