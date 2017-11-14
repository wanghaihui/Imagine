package retrofit;

/**
 * retrofit-2.0.0-beta2
 */

/**
 * Adapts a Java interface to a REST API--将一个Java接口适配成REST API
 *
 * API endpoints(API端点--/xxx/xxx) are defined as(被定义为) methods on an interface with annotations(带有注释的接口上的方法) providing metadata(元数据) about
 * the form in which the HTTP call should be made(HTTP调用应该制作的表单).
 *
 * The relative path(相对路径) for a given method(对于一个给定的方法) is obtained(被得到) from an annotation on the method(从方法的注解上) describing
 * the request type(该方法同时描述了请求的类型). The built-in methods(内置的方法有) are {@link retrofit.http.GET GET},
 * {@link retrofit.http.PUT PUT}, {@link retrofit.http.POST POST}, {@link retrofit.http.PATCH
 * PATCH}, {@link retrofit.http.HEAD HEAD}, and {@link retrofit.http.DELETE DELETE}. You can use a
 * custom HTTP method(自定义的HTTP方法) with {@link retrofit.http.HTTP @HTTP}.
 *
 * Method parameters(方法的参数) can be used to replace(代替) parts of the URL(部分URL) by annotating them with
 * {@link retrofit.http.Path @Path}(被Path注解标记的那部分URL).
 * Replacement sections(替换的部分) are denoted(被标记) by an identifier(标识符) surrounded by curly braces(花括号) (e.g., "{foo}").
 *
 * To add items(添加项) to the query string(请求串) of a URL use {@link retrofit.http.Query @Query}.
 *
 * The body of a request(请求的body) is denoted(被标记) by the {@link retrofit.http.Body @Body} annotation.
 * The object will be converted to request representation(请求表示) by one of the {@link retrofit.Converter.Factory} instances.
 * A {@link RequestBody} can also be used for a raw representation(原生表示).
 *
 * Alternative(可供选择的) request body formats are supported by method annotations(方法注解) and corresponding parameter
 * annotations(相对应的参数注解):
 *
 * {@link retrofit.http.FormUrlEncoded @FormUrlEncoded} - Form-encoded(表单编码) data with key-value
 * pairs specified by the {@link retrofit.http.Field @Field} parameter annotation.
 *
 * {@link retrofit.http.Multipart @Multipart} - RFC 2387-compliant multi-part data(多部分数据) with parts
 * specified by the {@link retrofit.http.Part @Part} parameter annotation.
 *
 * Additional(额外的，附加的) static headers can be added for an endpoint using the {@link retrofit.http.Headers @Headers} method annotation.
 * For per-request control over a header annotate a parameter with {@link retrofit.http.Header @Header}.
 *
 * By default, methods return a {@link retrofit.Call} which represents the HTTP request.
 * The generic parameter of the call(call的泛型参数) is the response body type(回复的body类型) and will be converted by one of the
 * {@link retrofit.Converter.Factory} instances.
 * {@link ResponseBody} can also be used for a raw representation.
 * {@link Void} can be used if you do not care about the body contents.
 *
 * For example:
 * public interface CategoryService {
 *   &#64;(@)POST("/category/{cat}")
 *   Call&lt;List&lt;Item&gt;&gt; categoryList(@Path("cat") String a, @Query("page") int b);
 * }
 *
 * Calling {@link #create(Class) create()} with {@code CategoryService.class} will validate(合法化) the
 * annotations and create a new implementation of the service definition.
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import okhttp.HttpUrl;
import okhttp.OkHttpClient;
import okhttp.ResponseBody;

/**
 * 何为RESTful--看Url就知道要什么，看http method就知道干什么，看http status code就知道结果如何--祖传烂代码，专克架构师
 * 经典概括--用URL定位资源，用HTTP动词（GET,POST,DELETE,DETC）描述操作
 * 如何设计 RESTful API--REST风格的网络接口
 */
public final class Retrofit {
    // LinkedHashMap保存了记录的插入顺序
    private final Map<Method, MethodHandler<?>> methodHandlerCache = new LinkedHashMap<>();

    private final OkHttpClient client;
    private final BaseUrl baseUrl;
    private final List<Converter.Factory> converterFactories;
    private final List<CallAdapter.Factory> adapterFactories;
    private final Executor callbackExecutor;
    private final boolean validateEagerly;

    private Retrofit(OkHttpClient client, BaseUrl baseUrl, List<Converter.Factory> converterFactories,
                     List<CallAdapter.Factory> adapterFactories, Executor callbackExecutor,
                     boolean validateEagerly) {
        this.client = client;
        this.baseUrl = baseUrl;
        this.converterFactories = converterFactories;
        this.adapterFactories = adapterFactories;
        this.callbackExecutor = callbackExecutor;
        this.validateEagerly = validateEagerly;
    }

    /** Create an implementation of the API defined by the {@code service} interface. */
    // Single-interface(单接口) proxy(代理) creation(创建) guarded by parameter safety(类型安全的保护).
    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
        Utils.validateServiceInterface(service);
        // 提前验证方法
        if (validateEagerly) {
            // 完成方法的解析
            eagerlyValidateMethods(service);
        }


    }

    private void eagerlyValidateMethods(Class<?> service) {
        Platform platform = Platform.get();
        for (Method method : service.getDeclaredMethods()) {
            if (!platform.isDefaultMethod(method)) {
                loadMethodHandler(method);
            }
        }
    }

    MethodHandler<?> loadMethodHandler(Method method) {
        MethodHandler<?> handler;
        synchronized (methodHandlerCache) {
            handler = methodHandlerCache.get(method);
            if (handler == null) {
                handler = MethodHandler.create(this, method);
                methodHandlerCache.put(method, handler);
            }
        }
        return handler;
    }

    /**
     * Returns the {@link CallAdapter} for {@code returnType} from the available {@linkplain
     * #callAdapterFactories() factories}.
     */
    public CallAdapter<?> callAdapter(Type returnType, Annotation[] annotations) {
        return nextCallAdapter(null, returnType, annotations);
    }

    /**
     * Returns the {@link CallAdapter} for {@code returnType} from the available {@linkplain
     * #callAdapterFactories() factories} except {@code skipPast}.
     */
    public CallAdapter<?> nextCallAdapter(CallAdapter.Factory skipPast, Type returnType,
                                          Annotation[] annotations) {
        Utils.checkNotNull(returnType, "returnType == null");
        Utils.checkNotNull(annotations, "annotations == null");

        int start = adapterFactories.indexOf(skipPast) + 1;
        for (int i = start, count = adapterFactories.size(); i < count; i++) {
            CallAdapter<?> adapter = adapterFactories.get(i).get(returnType, annotations, this);
            if (adapter != null) {
                return adapter;
            }
        }

        StringBuilder builder = new StringBuilder("Could not locate call adapter for ")
                .append(returnType)
                .append(". Tried:");
        for (int i = start, count = adapterFactories.size(); i < count; i++) {
            builder.append("\n * ").append(adapterFactories.get(i).getClass().getName());
        }
        if (skipPast != null) {
            builder.append("\nSkipped:");
            for (int i = 0; i < start; i++) {
                builder.append("\n * ").append(adapterFactories.get(i).getClass().getName());
            }
        }
        throw new IllegalArgumentException(builder.toString());
    }

    /**
     * Returns a {@link Converter} for {@link ResponseBody} to {@code type} from the available
     * {@linkplain #converterFactories() factories}.
     */
    public <T> Converter<ResponseBody, T> responseConverter(Type type, Annotation[] annotations) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(annotations, "annotations == null");

        for (int i = 0, count = converterFactories.size(); i < count; i++) {
            Converter<ResponseBody, ?> converter =
                    converterFactories.get(i).fromResponseBody(type, annotations);
            if (converter != null) {
                //noinspection unchecked
                return (Converter<ResponseBody, T>) converter;
            }
        }

        StringBuilder builder = new StringBuilder("Could not locate ResponseBody converter for ")
                .append(type)
                .append(". Tried:");
        for (Converter.Factory converterFactory : converterFactories) {
            builder.append("\n * ").append(converterFactory.getClass().getName());
        }
        throw new IllegalArgumentException(builder.toString());
    }

    public List<CallAdapter.Factory> callAdapterFactories() {
        // 不可用修改
        return Collections.unmodifiableList(adapterFactories);
    }

    public List<Converter.Factory> converterFactories() {
        return Collections.unmodifiableList(converterFactories);
    }

    /**
     * 创建Retrofit实例
     * Build a new {@link Retrofit}.
     *
     * Calling {@link #baseUrl} is required before calling {@link #build()}. All other methods
     * are optional.
     */
    /**
     * 建造者模式：将一个复杂对象的构建与表示分离，使得用户在不知道对象的创建细节情况下就可以直接创建复杂的对象
     */
    public static final class Builder {
        // OkHttp客户端
        private OkHttpClient client;
        // 基础Url
        private BaseUrl baseUrl;
        // 转换器工厂
        private List<Converter.Factory> converterFactories = new ArrayList<>();
        // 适配器工厂
        private List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>();
        // 线程执行器
        // An object that executes submitted {@link Runnable} tasks
        private Executor callbackExecutor;
        // 是否提前验证
        private boolean validateEagerly;

        public Builder() {
            // Add the built-in(内置的) converter factory(转换器工厂) first.
            // This prevents overriding its behavior(防止重写它的行为) but also ensures correct behavior(保证正确的行为) when using converters that consume all types
            converterFactories.add(new BuiltInConverters());
        }

        /** The HTTP client used for requests. */
        public Builder client(OkHttpClient client) {
            this.client = Utils.checkNotNull(client, "client == null");
            return this;
        }

        /** API base URL. */
        public Builder baseUrl(String baseUrl) {
            Utils.checkNotNull(baseUrl, "baseUrl == null");
            HttpUrl httpUrl = HttpUrl.parse(baseUrl);
            if (httpUrl == null) {
                throw new IllegalArgumentException("Illegal URL: " + baseUrl);
            }
            return baseUrl(httpUrl);
        }

        /** API base URL. */
        public Builder baseUrl(final HttpUrl baseUrl) {
            Utils.checkNotNull(baseUrl, "baseUrl == null");
            // 匿名内部类的使用
            return baseUrl(new BaseUrl() {
                @Override
                public HttpUrl url() {
                    return baseUrl;
                }
            });
        }

        /** API base URL. */
        public Builder baseUrl(BaseUrl baseUrl) {
            this.baseUrl = Utils.checkNotNull(baseUrl, "baseUrl == null");
            return this;
        }

        /** Add converter factory for serialization and deserialization of objects.(序列化和反序列化对象) */
        public Builder addConverterFactory(Converter.Factory converterFactory) {
            converterFactories.add(Utils.checkNotNull(converterFactory, "converterFactory == null"));
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory callAdapterFactory) {
            callAdapterFactories.add(Utils.checkNotNull(callAdapterFactory, "callAdapterFactory == null"));
            return this;
        }

        /**
         * The executor on which {@link Callback} methods are invoked when returning {@link Call} from
         * your service method.
         */
        public Builder callbackExecutor(Executor callbackExecutor) {
            this.callbackExecutor = Utils.checkNotNull(callbackExecutor, "callbackExecutor == null");
            return this;
        }

        /**
         * When calling {@link #create} on the resulting {@link Retrofit} instance, eagerly validate
         * the configuration of all methods in the supplied interface.
         */
        public Builder validateEagerly() {
            validateEagerly = true;
            return this;
        }

        /** Create the {@link Retrofit} instances. */
        public Retrofit build() {
            if (baseUrl == null) {
                throw new IllegalStateException("base url required.");
            }

            OkHttpClient client = this.client;
            if (client == null) {
                client = new OkHttpClient();
            }

            // Make a defensive copy(保护性拷贝) of the adapters and add the default Call adapter.
            List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>(this.callAdapterFactories);
            callAdapterFactories.add(Platform.get().defaultCallAdapterFactory(callbackExecutor));

            // Make a defensive copy of the converters.
            List<Converter.Factory> converterFactories = new ArrayList<>(this.converterFactories);

            return new Retrofit(client, baseUrl, converterFactories, callAdapterFactories, callbackExecutor, validateEagerly);
        }

    }
}
