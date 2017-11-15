package okhttp;

import java.io.Closeable;
import java.io.IOException;

/**
 * Response体
 */

public abstract class ResponseBody implements Closeable {

    @Override
    public void close() throws IOException {

    }
}
