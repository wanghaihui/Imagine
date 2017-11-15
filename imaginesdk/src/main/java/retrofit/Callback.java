package retrofit;

/**
 * Communicates responses from a server or offline requests. One and only one method will be
 * invoked in response to a given request.
 *
 * Callback methods are executed using the {@link Retrofit} callback executor. When none is
 * specified, the following defaults are used:
 *
 * Android: Callbacks are executed on the application's main (UI) thread.
 * JVM: Callbacks are executed on the background thread which performed the request.
 *
 * @param <T> expected response type
 */

public interface Callback<T> {

    /** Successful HTTP response. */
    void onResponse(Response<T> response, Retrofit retrofit);

    /** Invoked when a network or unexpected exception occurred during the HTTP request. */
    void onFailure(Throwable t);
}
