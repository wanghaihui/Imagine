package okhttp;

/**
 * A uniform resource locator(统一资源定位符) (URL) with a scheme(底层使用的协议) of either {@code http} or {@code https}. Use this
 * class to compose(组合) and decompose(分解) Internet addresses. For example, this code will compose and print
 * a URL for Google search: {@code
 *
 *   HttpUrl url = new HttpUrl.Builder()
 *       .scheme("https")
 *       .host("www.google.com")
 *       .addPathSegment("search")
 *       .addQueryParameter("q", "polar bears")
 *       .build();
 *   System.out.println(url);
 * }
 *
 * which prints: {@code
 *
 *     https://www.google.com/search?q=polar%20bears
 * }
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的Http Url
 */
public final class HttpUrl {

    static final String USERNAME_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
    static final String PASSWORD_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";

    public static HttpUrl parse(String url) {
        return new HttpUrl();
    }


    public static class Builder {
        String scheme;
        // 编码的username
        String encodedUsername = "";
        // 编码的password
        String encodedPassword = "";
        String host;
        int port = -1;
        // 编码的Path Segments
        final List<String> encodedPathSegments = new ArrayList<>();
        // 编码的Query Names and Values
        List<String> encodedQueryNamesAndValues;
        // 编码的片段
        String encodedFragment;

        public Builder() {
            // The default path is '/' which needs a trailing space(尾部空格).
            encodedPathSegments.add("");
        }

        public Builder scheme(String scheme) {
            if (null == scheme) {
                throw new IllegalArgumentException("scheme == null");
            } else if (scheme.equalsIgnoreCase("http")) {
                this.scheme = "http";
            } else if (scheme.equalsIgnoreCase("https")) {
                this.scheme = "https";
            } else {
                throw new IllegalArgumentException("unexpected scheme: " + scheme);
            }

            return this;
        }

        public Builder username(String username) {
            if (null == username) {
                throw new IllegalArgumentException("username == null");
            }
            // this.encodedUsername = canonicalize(username, USERNAME_ENCODE_SET, false, false, false);
            return this;
        }
    }
}
