package defpackage;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    public int a;
    public ByteBuffer b;
    public int c;
    public int d;
    public final l0 e = l0.a();

    public final int a(int i) {
        return this.b.getInt(i) + i;
    }

    public final int b(int i) {
        if (i < this.d) {
            return this.b.getShort(this.c + i);
        }
        return 0;
    }

    public final void c(int i, ByteBuffer byteBuffer) {
        this.b = byteBuffer;
        if (byteBuffer == null) {
            this.a = 0;
            this.c = 0;
            this.d = 0;
        } else {
            this.a = i;
            int i2 = i - byteBuffer.getInt(i);
            this.c = i2;
            this.d = this.b.getShort(i2);
        }
    }

    public final String d(int i) {
        ByteBuffer byteBuffer = this.b;
        int i2 = byteBuffer.getInt(i) + i;
        int i3 = byteBuffer.getInt(i2);
        int i4 = i2 + 4;
        this.e.getClass();
        if (!byteBuffer.hasArray()) {
            if ((i4 | i3 | ((byteBuffer.limit() - i4) - i3)) < 0) {
                throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i4), Integer.valueOf(i3)));
            }
            int i5 = i4 + i3;
            char[] cArr = new char[i3];
            int i6 = 0;
            while (i4 < i5) {
                byte b = byteBuffer.get(i4);
                if (b < 0) {
                    break;
                }
                i4++;
                cArr[i6] = (char) b;
                i6++;
            }
            int i7 = i6;
            while (i4 < i5) {
                int i8 = i4 + 1;
                byte b2 = byteBuffer.get(i4);
                if (b2 >= 0) {
                    int i9 = i7 + 1;
                    cArr[i7] = (char) b2;
                    while (i8 < i5) {
                        byte b3 = byteBuffer.get(i8);
                        if (b3 < 0) {
                            break;
                        }
                        i8++;
                        cArr[i9] = (char) b3;
                        i9++;
                    }
                    i7 = i9;
                    i4 = i8;
                } else if (b2 < -32) {
                    if (i8 >= i5) {
                        throw new IllegalArgumentException("Invalid UTF-8");
                    }
                    i4 += 2;
                    q.g(b2, byteBuffer.get(i8), cArr, i7);
                    i7++;
                } else if (b2 < -16) {
                    if (i8 >= i5 - 1) {
                        throw new IllegalArgumentException("Invalid UTF-8");
                    }
                    int i10 = i4 + 2;
                    i4 += 3;
                    q.f(b2, byteBuffer.get(i8), byteBuffer.get(i10), cArr, i7);
                    i7++;
                } else {
                    if (i8 >= i5 - 2) {
                        throw new IllegalArgumentException("Invalid UTF-8");
                    }
                    byte b4 = byteBuffer.get(i8);
                    int i11 = i4 + 3;
                    byte b5 = byteBuffer.get(i4 + 2);
                    i4 += 4;
                    q.e(b2, b4, b5, byteBuffer.get(i11), cArr, i7);
                    i7 += 2;
                }
            }
            return new String(cArr, 0, i7);
        }
        byte[] bArrArray = byteBuffer.array();
        int iArrayOffset = byteBuffer.arrayOffset() + i4;
        if ((iArrayOffset | i3 | ((bArrArray.length - iArrayOffset) - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArrArray.length), Integer.valueOf(iArrayOffset), Integer.valueOf(i3)));
        }
        int i12 = iArrayOffset + i3;
        char[] cArr2 = new char[i3];
        int i13 = 0;
        while (iArrayOffset < i12) {
            byte b6 = bArrArray[iArrayOffset];
            if (b6 < 0) {
                break;
            }
            iArrayOffset++;
            cArr2[i13] = (char) b6;
            i13++;
        }
        int i14 = i13;
        while (iArrayOffset < i12) {
            int i15 = iArrayOffset + 1;
            byte b7 = bArrArray[iArrayOffset];
            if (b7 >= 0) {
                int i16 = i14 + 1;
                cArr2[i14] = (char) b7;
                while (i15 < i12) {
                    byte b8 = bArrArray[i15];
                    if (b8 < 0) {
                        break;
                    }
                    i15++;
                    cArr2[i16] = (char) b8;
                    i16++;
                }
                i14 = i16;
                iArrayOffset = i15;
            } else if (b7 < -32) {
                if (i15 >= i12) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                iArrayOffset += 2;
                q.g(b7, bArrArray[i15], cArr2, i14);
                i14++;
            } else if (b7 < -16) {
                if (i15 >= i12 - 1) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                int i17 = iArrayOffset + 2;
                iArrayOffset += 3;
                q.f(b7, bArrArray[i15], bArrArray[i17], cArr2, i14);
                i14++;
            } else {
                if (i15 >= i12 - 2) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                byte b9 = bArrArray[i15];
                int i18 = iArrayOffset + 3;
                byte b10 = bArrArray[iArrayOffset + 2];
                iArrayOffset += 4;
                q.e(b7, b9, b10, bArrArray[i18], cArr2, i14);
                i14 += 2;
            }
        }
        return new String(cArr2, 0, i14);
    }

    public final int e(int i) {
        int i2 = i + this.a;
        return this.b.getInt(i2) + i2 + 4;
    }

    public final int f(int i) {
        int i2 = i + this.a;
        return this.b.getInt(this.b.getInt(i2) + i2);
    }

    public a g(int i) {
        a aVar = new a();
        int iB = b(4);
        if (iB == 0) {
            return null;
        }
        int iA = a((i * 4) + e(iB));
        ByteBuffer byteBuffer = this.b;
        byteBuffer.getClass();
        aVar.c(iA, byteBuffer);
        return aVar;
    }
}
