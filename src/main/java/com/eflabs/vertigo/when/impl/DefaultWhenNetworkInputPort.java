package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenOutputPort;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.PromiseAdapter;
import io.vertx.core.MultiMap;
import net.kuujo.vertigo.reference.InputPortReference;

public class DefaultWhenNetworkInputPort<T> implements WhenOutputPort<T> {
    private final InputPortReference<T> port;
    private final When when;
    private final PromiseAdapter promiseAdapter;

    public DefaultWhenNetworkInputPort(InputPortReference<T> portRef, When when, PromiseAdapter promiseAdapter) {
        this.port = portRef;
        this.when = when;
        this.promiseAdapter = promiseAdapter;
    }

    @Override
    public Promise<T> send(T message, MultiMap headers) {
        return promiseAdapter
                .<Void>toPromise(handler -> port.send(message, headers, handler))
                .then(o -> when.resolve(message));
    }

    @Override
    public String name() {
        return port.name();
    }
}
