package com.englishtown.vertigo.when;

import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertigo.when.impl.DefaultWhenComponentInstance;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.kuujo.vertigo.component.AbstractComponent;
import net.kuujo.vertigo.io.VertigoMessage;

import java.util.function.Function;

/**
 * Helper class for when based vertigo development.
 */
public abstract class WhenComponentBase extends AbstractComponent {

    private final When when;

    public WhenComponentBase(When when) {
        this.when = when;
    }

    public When when() {
        return when;
    }

    public WhenComponentInstance whenComponent() {
        return new DefaultWhenComponentInstance(this.component(), when);
    }

    protected <T> Function<Throwable, Promise<T>> onError(VertigoMessage<?> message) {
        return whenComponent().onError(message::handle);
    }

    protected <T> Function<T, Promise<T>> onSuccess(VertigoMessage<?> message) {
        return whenComponent().onSuccess(message::handle);
    }

    protected <T> Function<Throwable, Promise<T>> onError(Handler<AsyncResult<Void>> completeHandler) {
        return whenComponent().onError(completeHandler);
    }

    protected <T> Function<T, Promise<T>> onSuccess(Handler<AsyncResult<Void>> completeHandler) {
        return whenComponent().onSuccess(completeHandler);

    }

}
