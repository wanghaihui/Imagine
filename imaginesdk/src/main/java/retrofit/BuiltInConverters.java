package retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp.RequestBody;
import okhttp.ResponseBody;
import retrofit.http.Streaming;

/**
 * 内置转换器
 */

final class BuiltInConverters extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        if (ResponseBody.class.equals(type)) {
            boolean isStreaming = Utils.isAnnotationPresent(annotations, Streaming.class);
            return new OkHttpResponseBodyConverter(isStreaming);
        }
        if (Void.class.equals(type)) {
            return new VoidConverter();
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        // isAssignableFrom--用来判断一个类Class1和另一个类Class2是否相同或是另一个类的超类或接口
        if (type instanceof Class && RequestBody.class.isAssignableFrom((Class<?>) type)) {
            return new OkHttpRequestBodyConverter();
        }
        return null;
    }

    static final class VoidConverter implements Converter<ResponseBody, Void> {

        @Override
        public Void convert(ResponseBody value) throws IOException {
            value.close();
            return null;
        }
    }

    static final class OkHttpRequestBodyConverter implements Converter<RequestBody, RequestBody> {
        @Override
        public RequestBody convert(RequestBody value) throws IOException {
            return value;
        }
    }

    static final class OkHttpResponseBodyConverter implements Converter<ResponseBody, ResponseBody> {
        private final boolean isStreaming;

        OkHttpResponseBodyConverter(boolean isStreaming) {
            this.isStreaming = isStreaming;
        }

        @Override public ResponseBody convert(ResponseBody value) throws IOException {
            if (isStreaming) {
                return value;
            }

            // Buffer the entire body to avoid future I/O(缓存整个的body来避免以后的I/O).
            try {
                return Utils.readBodyToBytesIfNecessary(value);
            } finally {
                Utils.closeQuietly(value);
            }
        }
    }
}
