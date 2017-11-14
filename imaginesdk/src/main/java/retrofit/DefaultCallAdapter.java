package retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * A call adapter(一个Call Adapter) that uses the same thread(使用相同的线程) for both I/O and application-level callbacks. For
 * synchronous calls(对于同步调用) this is the application thread making the request; for asynchronous calls this
 * is a thread provided by OkHttp's dispatcher(OkHttp的分发者).
 */

final class DefaultCallAdapter implements CallAdapter<Call<?>> {

    static final Factory FACTORY = new Factory() {
        @Override
        public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
            if (Utils.getRawType(returnType) != Call.class) {
                return null;
            }

            Type responseType = Utils.getCallResponseType(returnType);
            return new DefaultCallAdapter(responseType);
        }
    };

    private final Type responseType;

    DefaultCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public <R> Call<R> adapt(Call<R> call) {
        return call;
    }

}
