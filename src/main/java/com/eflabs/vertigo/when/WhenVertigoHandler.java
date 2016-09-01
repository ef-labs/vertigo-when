package com.eflabs.vertigo.when;

import com.englishtown.promises.Promise;

/**
 * Helper class for handling results for a particular completion handler.
 * @param <T> The type of event to handle.
 */
public interface WhenVertigoHandler<T> {

    /**
     * Handles success and forwards the input in a promise
     * @param result The success value.
     * @return A promise for the input value.
     */
    Promise<T> onSuccess(T result);

    /**
     * Handles errors and forwards a rejected promise
     * @param throwable The exception.
     * @return A rejected promise of the same type as the input type.
     */
    Promise<T> onReject(Throwable throwable);

}
