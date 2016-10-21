package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenOutputPort;
import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import io.vertx.core.MultiMap;
import net.kuujo.vertigo.instance.OutputPort;

public class DefaultWhenOutputPort<T> implements WhenOutputPort<T> {
    private final OutputPort<T> outputPort;
    private final When when;

    public DefaultWhenOutputPort(OutputPort<T> outputPort, When when) {
        this.outputPort = outputPort;
        this.when = when;
    }

    @Override
    public Promise<T> send(T message, MultiMap headers) {
        Deferred<T> deferred = when.defer();
        outputPort.send(message, headers, result -> {
            if (result.succeeded()) {
                deferred.resolve(message);
            } else {
                deferred.reject(result.cause());
            }
        });
        return deferred.getPromise();
    }

    @Override
    public String name() {
        return outputPort.name();
    }

}
