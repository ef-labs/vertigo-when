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
     * @return
     */
    ComponentInstance getComponentInstance();

    /**
     * Returns a wrapped output collector
     * @return
     */
    WhenOutputCollector output();

    /**
     * Creates a handler useful for bridging from when to async handlers.
     * @param completeHandler
     * @param <T>
     * @return
     */
    <T> WhenVertigoHandler<T> handler(Handler<AsyncResult<Void>> completeHandler);

    /**
     * Creates a handler useful for bridging from when to Vertigo messages (for acking/failing messages).
     * @param message
     * @param <T>
     * @return
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
     * @param completeHandler
     * @param <T>
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
     * @param completeHandler
     * @param <T>
     * @return A function delegate that handles the success and forwards the result.
     */
    default <T> Function<T, Promise<T>> onSuccess(Handler<AsyncResult<Void>> completeHandler) {
        return this.<T>handler(completeHandler)::onSuccess;
    }

}
