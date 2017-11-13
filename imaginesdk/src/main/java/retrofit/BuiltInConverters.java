package retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

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

        }
        if (Void.class.equals(type)) {

        }
        return null;
    }
}
