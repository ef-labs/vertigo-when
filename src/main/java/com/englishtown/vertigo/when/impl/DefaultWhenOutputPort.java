package com.englishtown.vertigo.when.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertigo.when.WhenOutputPort;
import io.vertx.core.MultiMap;
import net.kuujo.vertigo.io.OutputCollector;
import net.kuujo.vertigo.io.port.OutputPort;

public class DefaultWhenOutputPort<T> implements WhenOutputPort<T> {
    private final OutputPort<T> outputPort;
    private final When when;

    public DefaultWhenOutputPort(OutputPort<T> outputPort, OutputCollector outputCollector, When when) {
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

}
