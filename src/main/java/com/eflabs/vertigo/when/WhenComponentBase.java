package com.eflabs.vertigo.when;

import com.englishtown.promises.Promise;
import com.englishtown.promises.Thenable;
import com.englishtown.promises.When;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import net.kuujo.vertigo.component.AbstractComponent;
import net.kuujo.vertigo.instance.OutputCollector;
import net.kuujo.vertigo.message.VertigoMessage;

import java.util.function.Function;

/**
 * Helper class for when based vertigo development.
 */
public abstract class WhenComponentBase extends AbstractComponent {

    private final When when;
    private final WhenVertigoFactory whenVertigoFactory;
    private WhenComponentInstance whenComponent;

    public WhenComponentBase(WhenVertigoFactory whenVertigoFactory) {
        this.whenVertigoFactory = whenVertigoFactory;
        this.when = this.whenVertigoFactory.getWhen();
    }

    @Override
    protected void initComponent(Future<Void> initFuture) throws Exception {
        this.whenComponent = whenVertigoFactory.getComponentInstance(this.component());
        super.initComponent(initFuture);
    }

    public When when() {
        return when;
    }

    public WhenComponentInstance whenComponent() {
        return whenComponent;
    }

    /**
     * Returns the component's {@link OutputCollector}. This is the element of the
     * component which provides an interface for sending messages to other components.
     *
     * @return The component's {@link OutputCollector}.
     */
    public WhenOutputCollector output() {
        return whenComponent().output();
    }

    /**
     * Creates a handler for failing a message. Useful for creating a shorthand fail in a when chain.
     * <code>
     *     [...]
     *     .then(onSuccess(message), onReject(message))
     *     .then [...]
     * </code>
     * @param message The message to ack or fail.
     * @param <T> The type of input/output value.
     * @return A handler.
     */
    protected <T> Function<Throwable, Promise<T>> onReject(VertigoMessage<?> message) {
        return whenComponent().onReject(message::handle);
    }

    /**
     * Creates a handler for acking a message. Useful for creating a shorthand ack in a when chain.
     * <code>
     *     [...]
     *     .then(onSuccess(message), onReject(message))
     *     .then [...]
     * </code>
     * @param message The message to ack or fail.
     * @param <T> The type of input/output value.
     * @return A  handler.
     */
    protected <T> Function<T, Promise<T>> onSuccess(VertigoMessage<?> message) {
        return whenComponent().onSuccess(message::handle);
    }

    /**
     * Creates a when wrapper to forward a failure to an async handler. Useful for creating a shorthand result in a
     * when chain.
     * <code>
     *     [...]
     *     .then(onSuccess(asyncHandler), onReject(asyncHandler))
     *     .then [...]
     * </code>
     * @param completeHandler the async handler
     * @param <T> The type of input/output value.
     * @return A handler.
     */
    protected <T> Function<Throwable, Promise<T>> onReject(Handler<AsyncResult<Void>> completeHandler) {
        return whenComponent().onReject(completeHandler);
    }

    /**
     * Creates a when wrapper to forward a success to an async handler. Useful for creating a shorthand result in a
     * when chain.
     * <code>
     *     [...]
     *     .then(onSuccess(asyncHandler), onReject(asyncHandler))
     *     .then [...]
     * </code>
     * @param completeHandler the async handler
     * @param <T> The type of input/output value.
     * @return A handler.
     */
    protected <T> Function<T, Promise<T>> onSuccess(Handler<AsyncResult<Void>> completeHandler) {
        return whenComponent().onSuccess(completeHandler);
    }

    protected <T> Handler<VertigoMessage<T>> safeHandler(Function<VertigoMessage<T>, Thenable<?>> handler) {
        return (message) -> {
            try {
                Thenable<?> result = handler.apply(message);
                result.then(aVoid -> {
                    message.ack();
                    return null;
                }, throwable -> {
                    message.fail(throwable);
                    return null;
                });
            }
            catch (Throwable cause) {
                message.fail(cause);
            }
        };
    }

}
