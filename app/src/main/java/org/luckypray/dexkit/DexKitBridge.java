package org.luckypray.dexkit;

import defpackage.a;
import defpackage.a0;
import defpackage.a1;
import defpackage.f0;
import defpackage.g0;
import defpackage.j0;
import defpackage.k0;
import defpackage.o;
import defpackage.o0;
import defpackage.q;
import defpackage.t0;
import defpackage.w;
import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class DexKitBridge implements Closeable {
    public volatile long a;
    public final ReentrantReadWriteLock b = new ReentrantReadWriteLock();

    public DexKitBridge(String str) {
        this.a = nativeInitDexKit(str);
    }

    private static final native byte[] nativeBatchFindClassUsingStrings(long j, byte[] bArr);

    private static final native byte[] nativeBatchFindMethodUsingStrings(long j, byte[] bArr);

    private static final native void nativeExportDexFile(long j, String str);

    private static final native byte[] nativeFieldGetMethods(long j, long j2);

    private static final native byte[] nativeFieldPutMethods(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] nativeFindClass(long j, byte[] bArr);

    private static final native byte[] nativeFindField(long j, byte[] bArr);

    private static final native byte[] nativeFindMethod(long j, byte[] bArr);

    private static final native byte[] nativeGetCallMethods(long j, long j2);

    private static final native byte[] nativeGetClassAnnotations(long j, long j2);

    private static final native byte[] nativeGetClassByIds(long j, long[] jArr);

    private static final native byte[] nativeGetClassData(long j, String str);

    private static final native int nativeGetDexNum(long j);

    private static final native byte[] nativeGetFieldAnnotations(long j, long j2);

    private static final native byte[] nativeGetFieldByIds(long j, long[] jArr);

    private static final native byte[] nativeGetFieldData(long j, String str);

    private static final native byte[] nativeGetInvokeMethods(long j, long j2);

    private static final native byte[] nativeGetMethodAnnotations(long j, long j2);

    private static final native byte[] nativeGetMethodByIds(long j, long[] jArr);

    private static final native byte[] nativeGetMethodData(long j, String str);

    private static final native int[] nativeGetMethodOpCodes(long j, long j2);

    private static final native byte[] nativeGetMethodUsingFields(long j, long j2);

    private static final native String[] nativeGetMethodUsingStrings(long j, long j2);

    private static final native byte[] nativeGetParameterAnnotations(long j, long j2);

    private static final native String[] nativeGetParameterNames(long j, long j2);

    private static final native long nativeInitDexKit(String str);

    private static final native long nativeInitDexKitByBytesArray(byte[][] bArr);

    private static final native long nativeInitDexKitByClassLoader(ClassLoader classLoader, boolean z);

    private static final native void nativeInitFullCache(long j);

    private static final native void nativeRelease(long j);

    private static final native void nativeSetMaxConcurrentQueries(long j, int i);

    private static final native void nativeSetThreadNum(long j, int i);

    public final g0 b(a1 a1Var) {
        int i;
        int i2;
        DexKitBridge dexKitBridge = this;
        j0 j0Var = new j0();
        a1Var.b(j0Var);
        byte[] bArrK = j0Var.k();
        ReentrantReadWriteLock.ReadLock lock = dexKitBridge.b.readLock();
        lock.lock();
        try {
            long j = dexKitBridge.a;
            if (j == 0) {
                throw new IllegalStateException("DexKitBridge is not valid");
            }
            byte[] bArrNativeFindField = nativeFindField(j, bArrK);
            lock.unlock();
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArrNativeFindField);
            byteBufferWrap.getClass();
            a aVar = new a();
            byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
            aVar.c(byteBufferWrap.position() + byteBufferWrap.getInt(byteBufferWrap.position()), byteBufferWrap);
            g0 g0Var = new g0(0);
            int iB = aVar.b(4);
            int iF = iB != 0 ? aVar.f(iB) : 0;
            int i3 = 0;
            while (i3 < iF) {
                a aVar2 = new a();
                int iB2 = aVar.b(4);
                if (iB2 != 0) {
                    int iA = aVar.a((i3 * 4) + aVar.e(iB2));
                    ByteBuffer byteBuffer = aVar.b;
                    byteBuffer.getClass();
                    aVar2.c(iA, byteBuffer);
                } else {
                    aVar2 = null;
                }
                aVar2.getClass();
                int iB3 = aVar2.b(4);
                int i4 = iB3 != 0 ? aVar2.b.getInt(iB3 + aVar2.a) : 0;
                int iB4 = aVar2.b(6);
                int i5 = iB4 != 0 ? aVar2.b.getInt(iB4 + aVar2.a) : 0;
                int iB5 = aVar2.b(8);
                int i6 = iB5 != 0 ? aVar2.b.getInt(iB5 + aVar2.a) : 0;
                int iB6 = aVar2.b(10);
                int i7 = iB6 != 0 ? aVar2.b.getInt(iB6 + aVar2.a) : 0;
                int iB7 = aVar2.b(12);
                String strD = iB7 != 0 ? aVar2.d(iB7 + aVar2.a) : null;
                strD.getClass();
                int iB8 = aVar2.b(14);
                if (iB8 != 0) {
                    int i8 = i7;
                    i2 = aVar2.b.getInt(iB8 + aVar2.a);
                    i = i8;
                } else {
                    i = i7;
                    i2 = 0;
                }
                f0 f0Var = new f0(dexKitBridge, i4, i5, i6, i, strD, i2);
                g0Var.add(f0Var);
                i3++;
                dexKitBridge = this;
            }
            int i9 = 1;
            if (g0Var.size() > 1) {
                w.l(g0Var, new a0(i9));
            }
            return g0Var;
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    public final g0 c(o0 o0Var) {
        k0 k0Var = new k0();
        o0Var.b(k0Var);
        byte[] bArrK = k0Var.k();
        ReentrantReadWriteLock.ReadLock lock = this.b.readLock();
        lock.lock();
        try {
            long j = this.a;
            if (j == 0) {
                throw new IllegalStateException("DexKitBridge is not valid");
            }
            byte[] bArrNativeFindMethod = nativeFindMethod(j, bArrK);
            lock.unlock();
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArrNativeFindMethod);
            byteBufferWrap.getClass();
            a aVar = new a();
            byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
            aVar.c(byteBufferWrap.position() + byteBufferWrap.getInt(byteBufferWrap.position()), byteBufferWrap);
            g0 g0Var = new g0(1);
            int i = 4;
            int iB = aVar.b(4);
            int iF = iB != 0 ? aVar.f(iB) : 0;
            int i2 = 0;
            while (i2 < iF) {
                a aVar2 = new a();
                int iB2 = aVar.b(i);
                if (iB2 != 0) {
                    int iA = aVar.a((i2 * 4) + aVar.e(iB2));
                    ByteBuffer byteBuffer = aVar.b;
                    byteBuffer.getClass();
                    aVar2.c(iA, byteBuffer);
                } else {
                    aVar2 = null;
                }
                aVar2.getClass();
                int iB3 = aVar2.b(10);
                int i3 = iB3 != 0 ? aVar2.b.getInt(iB3 + aVar2.a) : 0;
                if ((i3 & 131072) > 0) {
                    i3 = (i3 ^ 131072) | 32;
                }
                int i4 = i3;
                int iB4 = aVar2.b(i);
                int i5 = iB4 != 0 ? aVar2.b.getInt(iB4 + aVar2.a) : 0;
                int iB5 = aVar2.b(6);
                int i6 = iB5 != 0 ? aVar2.b.getInt(iB5 + aVar2.a) : 0;
                int iB6 = aVar2.b(8);
                int i7 = iB6 != 0 ? aVar2.b.getInt(iB6 + aVar2.a) : 0;
                int iB7 = aVar2.b(12);
                String strD = iB7 != 0 ? aVar2.d(iB7 + aVar2.a) : null;
                if (strD == null) {
                    strD = "";
                }
                int iB8 = aVar2.b(14);
                int i8 = iB8 != 0 ? aVar2.b.getInt(iB8 + aVar2.a) : 0;
                ArrayList arrayList = new ArrayList();
                int i9 = 16;
                int iB9 = aVar2.b(16);
                int iF2 = iB9 != 0 ? aVar2.f(iB9) : 0;
                int i10 = 0;
                while (i10 < iF2) {
                    int iB10 = aVar2.b(i9);
                    arrayList.add(Integer.valueOf(iB10 != 0 ? aVar2.b.getInt((i10 * 4) + aVar2.e(iB10)) : 0));
                    i10++;
                    i9 = 16;
                }
                g0Var.add(new t0(this, i5, i6, i7, i4, strD, i8, arrayList));
                i2++;
                i = 4;
            }
            if (g0Var.size() > 1) {
                w.l(g0Var, new a0(2));
            }
            return g0Var;
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        ReentrantReadWriteLock reentrantReadWriteLock = this.b;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            long j = this.a;
            if (j == 0) {
                while (i < readHoldCount) {
                    lock.lock();
                    i++;
                }
                writeLock.unlock();
                return;
            }
            this.a = 0L;
            nativeRelease(j);
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
        } catch (Throwable th) {
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
            throw th;
        }
    }

    public final o d(long[] jArr) {
        ReentrantReadWriteLock.ReadLock lock = this.b.readLock();
        lock.lock();
        try {
            long j = this.a;
            if (j == 0) {
                throw new IllegalStateException("DexKitBridge is not valid");
            }
            byte[] bArrNativeGetClassByIds = nativeGetClassByIds(j, jArr);
            lock.unlock();
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArrNativeGetClassByIds);
            byteBufferWrap.getClass();
            a aVar = new a();
            byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
            aVar.c(byteBufferWrap.position() + byteBufferWrap.getInt(byteBufferWrap.position()), byteBufferWrap);
            o oVar = new o();
            int iB = aVar.b(4);
            int iF = iB != 0 ? aVar.f(iB) : 0;
            for (int i = 0; i < iF; i++) {
                a aVarG = aVar.g(i);
                aVarG.getClass();
                oVar.add(q.d(this, aVarG));
            }
            return oVar;
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    public final void finalize() {
        close();
    }
}
