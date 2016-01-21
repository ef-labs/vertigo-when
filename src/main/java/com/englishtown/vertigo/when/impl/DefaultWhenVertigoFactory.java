package com.englishtown.vertigo.when.impl;

import com.englishtown.promises.When;
import com.englishtown.vertigo.when.WhenComponentInstance;
import com.englishtown.vertigo.when.WhenVertigoFactory;
import net.kuujo.vertigo.component.ComponentInstance;

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
        return new DefaultWhenComponentInstance(component, when);
    }
}
