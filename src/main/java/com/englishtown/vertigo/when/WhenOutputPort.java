package com.englishtown.vertigo.when;

import com.englishtown.promises.Promise;
import io.vertx.core.MultiMap;

/**
 * When wrapper for a Vertigo output port.
 * @param <T> The type of message to send.
 */
public interface WhenOutputPort<T> {

    /**
     * Sends a message on the output.
     *
     * @param message The message to send.
     * @return The sent message.
     */
    default Promise<T> send(T message) {
        return this.send(message, null);
    }

    /**
     * Sends a message on the output.
     *
     * @param message The message to send.
     * @param headers The message headers.
     * @return The sent message.
     */
    Promise<T> send(T message, MultiMap headers);

}
