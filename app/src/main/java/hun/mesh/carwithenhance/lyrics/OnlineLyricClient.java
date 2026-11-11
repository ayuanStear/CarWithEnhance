package hun.mesh.carwithenhance.lyrics;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/** 复刻自 HyperMusicCover 的 Http：一次带超时的 GET，返回 body 或 null。 */
public final class OnlineLyricClient {
    private OnlineLyricClient() {
    }

    public static String get(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(4000);
            conn.setReadTimeout(6000);
            conn.setRequestProperty("User-Agent", "CarWithEnhance");
            int code = conn.getResponseCode();
            if (code != 200) {
                return null;
            }
            return read(conn.getInputStream());
        } catch (Throwable ignored) {
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Throwable ignored) {
                }
            }
        }
    }

    private static String read(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(32768);
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) {
            out.write(buf, 0, n);
        }
        in.close();
        return new String(out.toByteArray(), "UTF-8");
    }
}
