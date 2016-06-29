package com.englishtown.vertigo.when;

import com.englishtown.promises.Promise;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.kuujo.vertigo.component.ComponentInstance;
import net.kuujo.vertigo.io.VertigoMessage;

import java.util.function.Function;

/**
 * When wrapper over a Vertigo ComponentInstance.
 */
public interface WhenComponentInstance {

    /**
     * Gets the underlying component instance.
     * @return The underlying ComponentInstance
     */
    ComponentInstance getComponentInstance();

    /**
     * Returns a wrapped output collector
     * @return Wrapped output collector
     */
    WhenOutputCollector output();

    /**
     * Creates a handler wrapper useful for bridging from when to async handlers.
     * @param completeHandler The handler to call.
     * @param <T> The type of promise the handler should return.
     * @return A handler for bridging from when to async handlers
     */
    <T> WhenVertigoHandler<T> handler(Handler<AsyncResult<Void>> completeHandler);

    /**
     * Creates a handler wrapper useful for bridging from when to Vertigo messages (for acking/failing messages).
     * @param message The message to ack or fail.
     * @param <T> The type of promise the handler should return.
     * @return A handler useful for bridging from when to Vertigo
     */
    <T> WhenVertigoHandler<T> handler(VertigoMessage<?> message);

    /**
     * Convenience method for chaining an async handler ack/fail.
     *
     * For example:
     *  WhenComponentInstance ci;
     *      [...]
     *      .then(ci.onSuccess(completeHandler), ci.onReject(completeHandler));
     *
     * @param completeHandler The handler to call.
     * @param <T> The type of promise the handler should return.
     * @return A function delegate that handles the error and forwards the result.
     */
    default  <T> Function<Throwable, Promise<T>> onReject(Handler<AsyncResult<Void>> completeHandler) {
        return this.<T>handler(completeHandler)::onReject;
    }

    /**
     * Convenience method for chaining an async handler ack/fail.
     *
     * For example:
     *  WhenComponentInstance ci;
     *      [...]
     *      .then(ci.onSuccess(completeHandler), ci.onReject(completeHandler));
     *
     * @param completeHandler The handler to call.
     * @param <T> The type of promise to return.
     * @return A function delegate that handles the success and forwards the result.
     */
    default <T> Function<T, Promise<T>> onSuccess(Handler<AsyncResult<Void>> completeHandler) {
        return this.<T>handler(completeHandler)::onSuccess;
    }

}
