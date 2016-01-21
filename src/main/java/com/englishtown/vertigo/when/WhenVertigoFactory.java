package com.englishtown.vertigo.when;

import com.englishtown.promises.When;
import net.kuujo.vertigo.component.ComponentInstance;

/**
 * Factory for when vertigo classes.
 */
public interface WhenVertigoFactory {

    /**
     * Returns the underlying when object.
     * @return
     */
    When getWhen();

    /**
     * Creates a wrapper for the Vertigo ComponentInstance.
     * @param component
     * @return
     */
    WhenComponentInstance getComponentInstance(ComponentInstance component);

}
