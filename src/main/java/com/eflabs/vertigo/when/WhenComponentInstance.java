package com.eflabs.vertigo.when;

import net.kuujo.vertigo.context.ComponentContext;
import net.kuujo.vertigo.instance.ComponentInstance;

/**
 * When wrapper over a Vertigo {@link ComponentInstance}.
 */
public interface WhenComponentInstance {

    /**
     * Returns a wrapped input collector
     *
     * @return Wrapped input collector
     */
    WhenInputCollector input();

    /**
     * Returns a wrapped output collector
     *
     * @return Wrapped output collector
     */
    WhenOutputCollector output();

    /**
     * Returns the component context
     *
     * @return The context.
     */
    ComponentContext context();

    /**
     * Returns the component instance
     *
     * @return The component instance.
     */
    ComponentInstance componentInstance();

}
