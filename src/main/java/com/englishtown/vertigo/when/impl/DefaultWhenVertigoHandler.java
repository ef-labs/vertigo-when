package com.englishtown.vertigo.when.impl;

import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertigo.when.WhenVertigoHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class DefaultWhenVertigoHandler<T> implements WhenVertigoHandler<T> {

    private final When when;
    private final Handler<AsyncResult<Void>> completeHandler;

    public DefaultWhenVertigoHandler(When when, Handler<AsyncResult<Void>> completeHandler) {
        this.when = when;
        this.completeHandler = completeHandler;
    }

    @Override
    public Promise<T> onSuccess(T result) {
        completeHandler.handle(Future.succeededFuture());
        return when.resolve(result);
    }

    @Override
    public Promise<T> onError(Throwable throwable) {
        completeHandler.handle(Future.failedFuture(throwable));
        return when.reject(throwable);
    }

}
