package hun.mesh.carwithenhance.hook;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.policy.MediaTabSelectionPolicy;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/** Restores the selected root media tab after the detail Fragment is popped. */
public final class MediaTabSelectionHook implements IHook {
    private static final String ANDROIDX_VIEW_PAGER =
            "androidx.viewpager.widget.ViewPager";
    private static final String MEDIA_PAGER_ADAPTER =
            "com.carwith.launcher.media.MediaStatePagerAdapter";
    private static final Map<String, Selection> SELECTIONS = new HashMap<>();
    private static volatile boolean installed;

    @Override
    public void onHook(ClassLoader classLoader) {
        if (installed) {
            return;
        }
        try {
            Class<?> viewPager = XposedHelpers.findClass(
                    ANDROIDX_VIEW_PAGER, classLoader);
            hookSetAdapter(viewPager);
            hookSetCurrentItem(viewPager, 1);
            hookSetCurrentItem(viewPager, 2);
            installed = true;
            XLog.i("[媒体页签] 详情页返回后恢复根页签已注册");
        } catch (Throwable th) {
            XLog.e("[媒体页签] 注册失败，保留宿主默认返回行为", th);
        }
    }

    private static void hookSetAdapter(Class<?> viewPager) {
        boolean hooked = false;
        for (Method method : viewPager.getDeclaredMethods()) {
            if (!"setAdapter".equals(method.getName())
                    || method.getParameterTypes().length != 1) {
                continue;
            }
            XposedBridge.hookMethod(method, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    scheduleRestore(param.thisObject);
                }
            });
            hooked = true;
        }
        if (!hooked) {
            throw new IllegalStateException("ViewPager.setAdapter not found");
        }
    }

    private static void hookSetCurrentItem(
            Class<?> viewPager, final int parameterCount) {
        boolean hooked = false;
        for (Method method : viewPager.getDeclaredMethods()) {
            if (!"setCurrentItem".equals(method.getName())
                    || method.getParameterTypes().length != parameterCount) {
                continue;
            }
            XposedBridge.hookMethod(method, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    rememberCurrentItem(param.thisObject);
                }
            });
            hooked = true;
        }
        if (!hooked) {
            throw new IllegalStateException(
                    "ViewPager.setCurrentItem/" + parameterCount + " not found");
        }
    }

    private static void rememberCurrentItem(Object pager) {
        try {
            Object adapter = adapter(pager);
            String packageName = currentMediaPackage();
            if (!isMediaAdapter(adapter) || packageName == null) {
                return;
            }
            int count = adapterCount(adapter);
            int current = currentItem(pager);
            if (count <= 0 || current < 0 || current >= count) {
                return;
            }
            synchronized (SELECTIONS) {
                Selection selection = SELECTIONS.get(packageName);
                if (selection == null) {
                    SELECTIONS.put(packageName, new Selection(current));
                } else if (!selection.pending) {
                    selection.index = current;
                    log("媒体根页签已记录: " + packageName
                            + " index=" + current + "/" + count);
                }
            }
        } catch (Throwable th) {
            XLog.e("[媒体页签] 记录当前根页签失败", th);
        }
    }

    private static void scheduleRestore(final Object pager) {
        if (!(pager instanceof View)) {
            return;
        }
        Selection selection;
        String packageName = currentMediaPackage();
        synchronized (SELECTIONS) {
            selection = packageName == null ? null : SELECTIONS.get(packageName);
            if (selection == null || selection.scheduled) {
                return;
            }
            Object adapter = adapter(pager);
            if (!isMediaAdapter(adapter)) {
                return;
            }
            int count = adapterCount(adapter);
            if (count <= 0 || selection.index < 0 || selection.index >= count) {
                return;
            }
            selection.pending = true;
            selection.scheduled = true;
        }
        final String targetPackage = packageName;
        boolean posted = ((View) pager).post(new Runnable() {
            @Override
            public void run() {
                restoreSelection(pager, targetPackage);
            }
        });
        if (!posted) {
            synchronized (SELECTIONS) {
                selection.scheduled = false;
            }
        }
    }

    private static void restoreSelection(Object pager, String packageName) {
        Selection selection;
        synchronized (SELECTIONS) {
            selection = SELECTIONS.get(packageName);
            if (selection == null) {
                return;
            }
            selection.scheduled = false;
            if (!selection.pending) {
                return;
            }
        }
        if (!packageName.equals(currentMediaPackage())) {
            synchronized (SELECTIONS) {
                selection.pending = false;
            }
            return;
        }
        try {
            int count = adapterCount(adapter(pager));
            int current = currentItem(pager);
            int target = MediaTabSelectionPolicy.restoreIndex(
                    selection.index, count, current);
            if (target != current) {
                XposedHelpers.callMethod(
                        pager, "setCurrentItem",
                        new Object[]{target, Boolean.FALSE});
            }
            synchronized (SELECTIONS) {
                selection.pending = false;
            }
            log("媒体根页签已恢复: " + packageName
                    + " index=" + target + "/" + count
                    + " (原值=" + current + ")");
        } catch (Throwable th) {
            synchronized (SELECTIONS) {
                selection.pending = false;
            }
            XLog.e("[媒体页签] 恢复根页签失败", th);
        }
    }

    private static boolean isMediaAdapter(Object adapter) {
        if (adapter == null) {
            return false;
        }
        Class<?> type = adapter.getClass();
        while (type != null) {
            String name = type.getName();
            if (MEDIA_PAGER_ADAPTER.equals(name)
                    || (name.startsWith("com.carwith.launcher.media.")
                    && name.endsWith("PagerAdapter"))) {
                return true;
            }
            type = type.getSuperclass();
        }
        return false;
    }

    private static Object adapter(Object pager) {
        return XposedHelpers.callMethod(pager, "getAdapter");
    }

    private static int currentItem(Object pager) {
        Object value = XposedHelpers.callMethod(pager, "getCurrentItem");
        return value instanceof Integer ? (Integer) value : -1;
    }

    private static int adapterCount(Object adapter) {
        if (adapter == null) {
            return 0;
        }
        Object value = XposedHelpers.callMethod(adapter, "getCount");
        return value instanceof Integer ? (Integer) value : 0;
    }

    private static String currentMediaPackage() {
        return CarWithMediaCapabilities.INSTANCE.getCurrentMediaPackage();
    }

    private static void log(String message) {
        AutoPlayAllHook.log(AutoPlayAllHook.getAppContext(), message);
    }

    private static final class Selection {
        int index;
        boolean pending;
        boolean scheduled;

        Selection(int index) {
            this.index = index;
        }
    }
}
