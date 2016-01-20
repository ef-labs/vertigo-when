package com.englishtown.vertigo.when;

import com.englishtown.promises.Promise;

/**
 * Helper class for handling results for a particular completion handler.
 * @param <T>
 */
public interface WhenVertigoHandler<T> {

    /**
     * Handles success and forwards the input in a promise
     * @param result
     * @return
     */
    Promise<T> onSuccess(T result);

    /**
     * Handles errors and forwards a rejected promise
     * @param throwable
     * @return
     */
    Promise<T> onError(Throwable throwable);

}
