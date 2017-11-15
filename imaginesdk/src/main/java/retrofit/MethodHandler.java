package retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import okhttp.ResponseBody;

/**
 * Interface中的方法的处理
 */

final class MethodHandler<T> {

    @SuppressWarnings("unchecked")
    static MethodHandler<?> create(Retrofit retrofit, Method method) {
        CallAdapter<Object> callAdapter = (CallAdapter<Object>) createCallAdapter(method, retrofit);
        Type responseType = callAdapter.responseType();
        Converter<ResponseBody, Object> responseConverter =
                (Converter<ResponseBody, Object>) createResponseConverter(method, retrofit, responseType);
        RequestFactory requestFactory = RequestFactoryParser.parse(method, responseType, retrofit);
        return new MethodHandler<>(retrofit, requestFactory, callAdapter, responseConverter);
    }

    private static CallAdapter<?> createCallAdapter(Method method, Retrofit retrofit) {
        // 得到泛型的返回类型
        Type returnType = method.getGenericReturnType();
        if (Utils.hasUnresolvableType(returnType)) {
            throw Utils.methodError(method,
                    "Method return type must not include a type variable or wildcard: %s", returnType);
        }
        if (returnType == void.class) {
            throw Utils.methodError(method, "Service methods cannot return void.");
        }
        Annotation[] annotations = method.getAnnotations();
        try {
            return retrofit.callAdapter(returnType, annotations);
        } catch (RuntimeException e) { // Wide exception range because factories are user code.
            throw Utils.methodError(e, method, "Unable to create call adapter for %s", returnType);
        }
    }

    private static Converter<ResponseBody, ?> createResponseConverter(Method method,
                                                                      Retrofit retrofit, Type responseType) {
        Annotation[] annotations = method.getAnnotations();
        try {
            return retrofit.responseConverter(responseType, annotations);
        } catch (RuntimeException e) { // Wide exception range because factories are user code.
            throw Utils.methodError(e, method, "Unable to create converter for %s", responseType);
        }
    }

    private final Retrofit retrofit;
    private final RequestFactory requestFactory;
    private final CallAdapter<T> callAdapter;
    private final Converter<ResponseBody, T> responseConverter;

    private MethodHandler(Retrofit retrofit, RequestFactory requestFactory,
                          CallAdapter<T> callAdapter, Converter<ResponseBody, T> responseConverter) {
        this.retrofit = retrofit;
        this.requestFactory = requestFactory;
        this.callAdapter = callAdapter;
        this.responseConverter = responseConverter;
    }

    Object invoke(Object... args) {
        return callAdapter.adapt(new OkHttpCall<>(retrofit, requestFactory, responseConverter, args));
    }
}
