package com.eflabs.vertigo.when.impl;

import com.eflabs.vertigo.when.WhenNetworkInputCollector;
import com.eflabs.vertigo.when.WhenNetworkReference;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.PromiseAdapter;
import net.kuujo.vertigo.reference.NetworkReference;

public class DefaultWhenNetworkReference implements WhenNetworkReference {
    private final NetworkReference networkReference;
    private final When when;
    private final PromiseAdapter promiseAdapter;

    public DefaultWhenNetworkReference(NetworkReference networkReference, When when, PromiseAdapter promiseAdapter) {
        this.networkReference = networkReference;
        this.when = when;
        this.promiseAdapter = promiseAdapter;
    }


    @Override
    public WhenNetworkInputCollector input() {
        return new DefaultWhenNetworkInputCollector(networkReference.input(), when, promiseAdapter);
    }

    @Override
    public String name() {
        return networkReference.name();
    }
}
