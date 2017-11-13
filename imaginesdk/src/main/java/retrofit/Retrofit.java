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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp.OkHttpClient;

/**
 * 何为RESTful--看Url就知道要什么，看http method就知道干什么，看http status code就知道结果如何--祖传烂代码，专克架构师
 * 经典概括--用URL定位资源，用HTTP动词（GET,POST,DELETE,DETC）描述操作
 * 如何设计 RESTful API--REST风格的网络接口
 */
public final class Retrofit {

    /**
     * 创建Retrofit实例
     * Build a new {@link Retrofit}.
     *
     * Calling {@link #baseUrl} is required before calling {@link #build()}. All other methods
     * are optional.
     */
    public static final class Builder {
        // OkHttp客户端
        private OkHttpClient client;
        // 基础Url
        private BaseUrl baseUrl;
        // 转换器工厂
        private List<Converter.Factory> converterFactories = new ArrayList<>();
        // 适配器工厂
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
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

        public Builder baseUrl(String baseUrl) {

        }
    }
}
