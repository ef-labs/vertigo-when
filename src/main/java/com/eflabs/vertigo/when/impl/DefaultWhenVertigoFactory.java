package com.eflabs.vertigo.when.impl;

import com.englishtown.promises.When;
import com.eflabs.vertigo.when.WhenComponentInstance;
import com.eflabs.vertigo.when.WhenVertigoFactory;
import net.kuujo.vertigo.instance.ComponentInstance;

import javax.inject.Inject;

/**
 * Created by Magnus.Koch on 1/20/2016.
 */
public class DefaultWhenVertigoFactory implements WhenVertigoFactory {
    private final When when;

    @Inject
    public DefaultWhenVertigoFactory(When when) {
        this.when = when;
    }

    @Override
    public When getWhen() {
        return when;
    }

    @Override
    public WhenComponentInstance getComponentInstance(ComponentInstance component) {
        return new DefaultWhenComponentInstance(when, component);
    }
}
