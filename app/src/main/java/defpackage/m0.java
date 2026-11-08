package defpackage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class m0 {
    public ByteBuffer a;
    public int b;
    public int c;
    public int[] d;
    public int e;
    public boolean f;
    public boolean g;
    public int h;
    public int[] i;
    public int j;
    public int k;
    public final l0 l;
    public final l0 m;

    public m0() {
        l0 l0Var = l0.a;
        l0 l0VarA = l0.a();
        this.c = 1;
        this.d = null;
        this.e = 0;
        this.f = false;
        this.g = false;
        this.i = new int[16];
        this.j = 0;
        this.k = 0;
        this.l = l0Var;
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
        this.a = byteBufferOrder;
        this.m = l0VarA;
        this.b = byteBufferOrder.capacity();
    }

    public final void a(int i, int i2) {
        if (i2 != 0) {
            h(4, 0);
            int iG = (g() - i2) + 4;
            ByteBuffer byteBuffer = this.a;
            int i3 = this.b - 4;
            this.b = i3;
            byteBuffer.putInt(i3, iG);
            this.d[i] = g();
        }
    }

    public final void b(short s) {
        h(2, 0);
        ByteBuffer byteBuffer = this.a;
        int i = this.b - 2;
        this.b = i;
        byteBuffer.putShort(i, s);
    }

    public final int c(CharSequence charSequence) {
        char c;
        char c2;
        int i;
        int i2;
        char cCharAt;
        this.m.getClass();
        int length = charSequence.length();
        int i3 = 0;
        while (i3 < length && charSequence.charAt(i3) < 128) {
            i3++;
        }
        int i4 = length;
        while (true) {
            c = 55296;
            c2 = 2048;
            if (i3 < length) {
                char cCharAt2 = charSequence.charAt(i3);
                if (cCharAt2 >= 2048) {
                    int length2 = charSequence.length();
                    int i5 = 0;
                    while (i3 < length2) {
                        char cCharAt3 = charSequence.charAt(i3);
                        if (cCharAt3 < 2048) {
                            i5 += (127 - cCharAt3) >>> 31;
                        } else {
                            i5 += 2;
                            if (55296 <= cCharAt3 && cCharAt3 <= 57343) {
                                if (Character.codePointAt(charSequence, i3) < 65536) {
                                    throw new p1(i3, length2);
                                }
                                i3++;
                            }
                        }
                        i3++;
                    }
                    i4 += i5;
                    break;
                }
                i4 += (127 - cCharAt2) >>> 31;
                i3++;
            } else {
                break;
            }
        }
        if (i4 < length) {
            throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) i4) + 4294967296L));
        }
        h(1, 0);
        ByteBuffer byteBuffer = this.a;
        int i6 = this.b - 1;
        this.b = i6;
        byteBuffer.put(i6, (byte) 0);
        if (this.f) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
        this.k = i4;
        h(4, i4);
        h(1, i4);
        this.f = true;
        ByteBuffer byteBuffer2 = this.a;
        int i7 = this.b - i4;
        this.b = i7;
        byteBuffer2.position(i7);
        ByteBuffer byteBuffer3 = this.a;
        if (byteBuffer3.hasArray()) {
            int iArrayOffset = byteBuffer3.arrayOffset();
            byte[] bArrArray = byteBuffer3.array();
            int iPosition = byteBuffer3.position() + iArrayOffset;
            int iRemaining = byteBuffer3.remaining();
            int length3 = charSequence.length();
            int i8 = iRemaining + iPosition;
            int i9 = 0;
            while (i9 < length3) {
                int i10 = i9 + iPosition;
                if (i10 >= i8 || (cCharAt = charSequence.charAt(i9)) >= 128) {
                    break;
                }
                bArrArray[i10] = (byte) cCharAt;
                i9++;
            }
            if (i9 == length3) {
                i = iPosition + length3;
            } else {
                i = iPosition + i9;
                while (i9 < length3) {
                    char cCharAt4 = charSequence.charAt(i9);
                    if (cCharAt4 < 128 && i < i8) {
                        bArrArray[i] = (byte) cCharAt4;
                        i++;
                    } else if (cCharAt4 < c2 && i <= i8 - 2) {
                        int i11 = i + 1;
                        bArrArray[i] = (byte) ((cCharAt4 >>> 6) | 960);
                        i += 2;
                        bArrArray[i11] = (byte) ((cCharAt4 & '?') | 128);
                    } else {
                        if ((cCharAt4 >= c && 57343 >= cCharAt4) || i > i8 - 3) {
                            if (i > i8 - 4) {
                                if (55296 <= cCharAt4 && cCharAt4 <= 57343 && ((i2 = i9 + 1) == charSequence.length() || !Character.isSurrogatePair(cCharAt4, charSequence.charAt(i2)))) {
                                    throw new p1(i9, length3);
                                }
                                throw new ArrayIndexOutOfBoundsException("Failed writing " + cCharAt4 + " at index " + i);
                            }
                            int i12 = i9 + 1;
                            if (i12 != charSequence.length()) {
                                char cCharAt5 = charSequence.charAt(i12);
                                if (Character.isSurrogatePair(cCharAt4, cCharAt5)) {
                                    int codePoint = Character.toCodePoint(cCharAt4, cCharAt5);
                                    bArrArray[i] = (byte) ((codePoint >>> 18) | 240);
                                    bArrArray[i + 1] = (byte) (((codePoint >>> 12) & 63) | 128);
                                    int i13 = i + 3;
                                    bArrArray[i + 2] = (byte) (((codePoint >>> 6) & 63) | 128);
                                    i += 4;
                                    bArrArray[i13] = (byte) ((codePoint & 63) | 128);
                                    i9 = i12;
                                } else {
                                    i9 = i12;
                                }
                            }
                            throw new p1(i9 - 1, length3);
                        }
                        bArrArray[i] = (byte) ((cCharAt4 >>> '\f') | 480);
                        int i14 = i + 2;
                        bArrArray[i + 1] = (byte) (((cCharAt4 >>> 6) & 63) | 128);
                        i += 3;
                        bArrArray[i14] = (byte) ((cCharAt4 & '?') | 128);
                    }
                    i9++;
                    c = 55296;
                    c2 = 2048;
                }
            }
            byteBuffer3.position(i - iArrayOffset);
        } else {
            int length4 = charSequence.length();
            int iPosition2 = byteBuffer3.position();
            int i15 = 0;
            while (i15 < length4) {
                try {
                    char cCharAt6 = charSequence.charAt(i15);
                    if (cCharAt6 >= 128) {
                        break;
                    }
                    byteBuffer3.put(iPosition2 + i15, (byte) cCharAt6);
                    i15++;
                } catch (IndexOutOfBoundsException unused) {
                }
            }
            if (i15 == length4) {
                byteBuffer3.position(iPosition2 + i15);
            } else {
                iPosition2 += i15;
                while (i15 < length4) {
                    char cCharAt7 = charSequence.charAt(i15);
                    if (cCharAt7 >= 128) {
                        if (cCharAt7 < 2048) {
                            int i16 = iPosition2 + 1;
                            try {
                                byteBuffer3.put(iPosition2, (byte) ((cCharAt7 >>> 6) | 192));
                                byteBuffer3.put(i16, (byte) ((cCharAt7 & '?') | 128));
                                iPosition2 = i16;
                            } catch (IndexOutOfBoundsException unused2) {
                                iPosition2 = i16;
                            }
                        } else {
                            if (cCharAt7 >= 55296 && 57343 >= cCharAt7) {
                                int i17 = i15 + 1;
                                if (i17 != length4) {
                                    try {
                                        char cCharAt8 = charSequence.charAt(i17);
                                        if (Character.isSurrogatePair(cCharAt7, cCharAt8)) {
                                            int codePoint2 = Character.toCodePoint(cCharAt7, cCharAt8);
                                            int i18 = iPosition2 + 1;
                                            try {
                                                byteBuffer3.put(iPosition2, (byte) ((codePoint2 >>> 18) | 240));
                                                int i19 = iPosition2 + 2;
                                                try {
                                                    byteBuffer3.put(i18, (byte) (((codePoint2 >>> 12) & 63) | 128));
                                                    iPosition2 += 3;
                                                    byteBuffer3.put(i19, (byte) (((codePoint2 >>> 6) & 63) | 128));
                                                    byteBuffer3.put(iPosition2, (byte) ((codePoint2 & 63) | 128));
                                                    i15 = i17;
                                                    i15++;
                                                    iPosition2++;
                                                } catch (IndexOutOfBoundsException unused3) {
                                                    i15 = i17;
                                                    iPosition2 = i19;
                                                }
                                            } catch (IndexOutOfBoundsException unused4) {
                                                iPosition2 = i18;
                                                i15 = i17;
                                            }
                                        } else {
                                            i15 = i17;
                                        }
                                    } catch (IndexOutOfBoundsException unused5) {
                                    }
                                    i15 = i17;
                                }
                                throw new p1(i15, length4);
                            }
                            int i20 = iPosition2 + 1;
                            try {
                                byteBuffer3.put(iPosition2, (byte) ((cCharAt7 >>> '\f') | 224));
                                iPosition2 += 2;
                                byteBuffer3.put(i20, (byte) (((cCharAt7 >>> 6) & 63) | 128));
                                byteBuffer3.put(iPosition2, (byte) ((cCharAt7 & '?') | 128));
                                i15++;
                                iPosition2++;
                            } catch (IndexOutOfBoundsException unused6) {
                                iPosition2 = i20;
                            }
                        }
                        int iMax = Math.max(i15, (iPosition2 - byteBuffer3.position()) + 1) + byteBuffer3.position();
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i15) + " at index " + iMax);
                    }
                    byteBuffer3.put(iPosition2, (byte) cCharAt7);
                    i15++;
                    iPosition2++;
                }
                byteBuffer3.position(iPosition2);
            }
        }
        if (!this.f) {
            throw new AssertionError("FlatBuffers: endVector called without startVector");
        }
        this.f = false;
        int i21 = this.k;
        ByteBuffer byteBuffer4 = this.a;
        int i22 = this.b - 4;
        this.b = i22;
        byteBuffer4.putInt(i22, i21);
        return g();
    }

    public final int d(int[] iArr) {
        boolean z = this.f;
        if (z) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
        int length = iArr.length;
        if (z) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
        this.k = length;
        int i = length * 4;
        h(4, i);
        h(4, i);
        this.f = true;
        for (int length2 = iArr.length - 1; length2 >= 0; length2--) {
            int i2 = iArr[length2];
            h(4, 0);
            int iG = (g() - i2) + 4;
            ByteBuffer byteBuffer = this.a;
            int i3 = this.b - 4;
            this.b = i3;
            byteBuffer.putInt(i3, iG);
        }
        if (!this.f) {
            throw new AssertionError("FlatBuffers: endVector called without startVector");
        }
        this.f = false;
        int i4 = this.k;
        ByteBuffer byteBuffer2 = this.a;
        int i5 = this.b - 4;
        this.b = i5;
        byteBuffer2.putInt(i5, i4);
        return g();
    }

    public final int e() {
        int i;
        if (this.d == null || !this.f) {
            throw new AssertionError("FlatBuffers: endTable called without startTable");
        }
        h(4, 0);
        ByteBuffer byteBuffer = this.a;
        int i2 = this.b - 4;
        this.b = i2;
        byteBuffer.putInt(i2, 0);
        int iG = g();
        int i3 = this.e - 1;
        while (i3 >= 0 && this.d[i3] == 0) {
            i3--;
        }
        for (int i4 = i3; i4 >= 0; i4--) {
            int i5 = this.d[i4];
            b((short) (i5 != 0 ? iG - i5 : 0));
        }
        b((short) (iG - this.h));
        b((short) ((i3 + 3) * 2));
        int i6 = 0;
        loop2: while (true) {
            if (i6 >= this.j) {
                i = 0;
                break;
            }
            int iCapacity = this.a.capacity() - this.i[i6];
            int i7 = this.b;
            short s = this.a.getShort(iCapacity);
            if (s == this.a.getShort(i7)) {
                int i8 = 2;
                while (true) {
                    if (i8 >= s) {
                        i = this.i[i6];
                        break loop2;
                    }
                    if (this.a.getShort(iCapacity + i8) != this.a.getShort(i7 + i8)) {
                        break;
                    }
                    i8 += 2;
                }
            }
            i6++;
        }
        if (i != 0) {
            int iCapacity2 = this.a.capacity() - iG;
            this.b = iCapacity2;
            this.a.putInt(iCapacity2, i - iG);
        } else {
            int i9 = this.j;
            int[] iArr = this.i;
            if (i9 == iArr.length) {
                this.i = Arrays.copyOf(iArr, i9 * 2);
            }
            int[] iArr2 = this.i;
            int i10 = this.j;
            this.j = i10 + 1;
            iArr2[i10] = g();
            ByteBuffer byteBuffer2 = this.a;
            byteBuffer2.putInt(byteBuffer2.capacity() - iG, g() - iG);
        }
        this.f = false;
        return iG;
    }

    public final void f(int i) {
        h(this.c, 4);
        h(4, 0);
        int iG = (g() - i) + 4;
        ByteBuffer byteBuffer = this.a;
        int i2 = this.b - 4;
        this.b = i2;
        byteBuffer.putInt(i2, iG);
        this.a.position(this.b);
        this.g = true;
    }

    public final int g() {
        return this.a.capacity() - this.b;
    }

    public final void h(int i, int i2) {
        int i3;
        if (i > this.c) {
            this.c = i;
        }
        int i4 = ((~((this.a.capacity() - this.b) + i2)) + 1) & (i - 1);
        while (this.b < i4 + i + i2) {
            int iCapacity = this.a.capacity();
            ByteBuffer byteBuffer = this.a;
            int iCapacity2 = byteBuffer.capacity();
            if (iCapacity2 == 0) {
                i3 = 1024;
            } else {
                i3 = 2147483639;
                if (iCapacity2 == 2147483639) {
                    throw new AssertionError("FlatBuffers: cannot grow buffer beyond 2 gigabytes.");
                }
                if (((-1073741824) & iCapacity2) == 0) {
                    i3 = iCapacity2 << 1;
                }
            }
            byteBuffer.position(0);
            this.l.getClass();
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(i3).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.position(byteBufferOrder.clear().capacity() - iCapacity2);
            byteBufferOrder.put(byteBuffer);
            this.a = byteBufferOrder;
            this.b = (byteBufferOrder.capacity() - iCapacity) + this.b;
        }
        for (int i5 = 0; i5 < i4; i5++) {
            ByteBuffer byteBuffer2 = this.a;
            int i6 = this.b - 1;
            this.b = i6;
            byteBuffer2.put(i6, (byte) 0);
        }
    }

    public final void i(int i) {
        if (this.f) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
        int[] iArr = this.d;
        if (iArr == null || iArr.length < i) {
            this.d = new int[i];
        }
        this.e = i;
        Arrays.fill(this.d, 0, i, 0);
        this.f = true;
        this.h = g();
    }
}
