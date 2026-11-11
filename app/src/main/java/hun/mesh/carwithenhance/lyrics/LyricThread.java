package hun.mesh.carwithenhance.lyrics;

/** 替换 HyperMusicCover 的 Gen.thread：跑一个后台任务，异常时执行兜底。 */
public final class LyricThread {
    private LyricThread() {
    }

    public static void run(String name, Runnable task, Runnable onFail) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Throwable th) {
                    if (onFail != null) {
                        onFail.run();
                    }
                }
            }
        }, name);
        t.setDaemon(true);
        t.start();
    }
}
