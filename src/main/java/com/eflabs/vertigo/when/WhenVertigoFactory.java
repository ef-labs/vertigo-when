package com.eflabs.vertigo.when;

import com.englishtown.promises.When;
import net.kuujo.vertigo.instance.ComponentInstance;

/**
 * Factory for when vertigo classes.
 */
public interface WhenVertigoFactory {

    /**
     * Returns the underlying when instance.
     *
     * @return The underlying when instance.
     */
    When getWhen();

    /**
     * Creates a wrapper for the Vertigo ComponentInstance.
     *
     * @param component The underlying ComponentInstance instance.
     * @return ComponentInstance wrapper.
     */
    WhenComponentInstance getComponentInstance(ComponentInstance component);

}
