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
    private final WhenVertigoFactory whenVertigoFactory;
    private WhenComponentInstance whenComponent;

    public WhenComponentBase(WhenVertigoFactory whenVertigoFactory) {
        this.whenVertigoFactory = whenVertigoFactory;
        this.when = this.whenVertigoFactory.getWhen();
    }

    @Override
    public void start() throws Exception {
        super.start();
        this.whenComponent = whenVertigoFactory.getComponentInstance(this.component());
    }

    public When when() {
        return when;
    }

    public WhenComponentInstance whenComponent() {
        return whenComponent;
    }

    protected <T> Function<Throwable, Promise<T>> onReject(VertigoMessage<?> message) {
        return whenComponent().onReject(message::handle);
    }

    protected <T> Function<T, Promise<T>> onSuccess(VertigoMessage<?> message) {
        return whenComponent().onSuccess(message::handle);
    }

    protected <T> Function<Throwable, Promise<T>> onReject(Handler<AsyncResult<Void>> completeHandler) {
        return whenComponent().onReject(completeHandler);
    }

    protected <T> Function<T, Promise<T>> onSuccess(Handler<AsyncResult<Void>> completeHandler) {
        return whenComponent().onSuccess(completeHandler);
    }

}
