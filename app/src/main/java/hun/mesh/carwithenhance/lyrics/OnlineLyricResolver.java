package hun.mesh.carwithenhance.lyrics;

/** 对移植的 OnlineLyrics 暴露一个公开入口，供 hook 包调用。 */
public final class OnlineLyricResolver {
    public static final class Result {
        public final String id;
        public final String body;
        public final boolean words;

        Result(String id, String body, boolean words) {
            this.id = id;
            this.body = body;
            this.words = words;
        }
    }

    private OnlineLyricResolver() {
    }

    public static Result resolve(String pkg, NcmLyrics.Query q) {
        if (q == null) {
            return null;
        }
        OnlineLyrics.Found f = OnlineLyrics.first(pkg, q);
        if (f == null) {
            f = OnlineLyrics.rest(pkg, q);
        }
        if (f == null) {
            return null;
        }
        return new Result(f.id, f.body, f.words);
    }
}
