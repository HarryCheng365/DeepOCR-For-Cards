from PIL import Image
import cv2
import time
import numpy as np
import numba


# Gray = R*0.299 + G*0.587 + B*0.114

@numba.jit(numba.uint8[:, ::1](numba.uint8[:, :, ::1]), nopython=True, parallel=True, fastmath=True)
def my_convert(img):
    w = img.shape[0]
    h = img.shape[1]
    result = np.zeros((w, h), dtype=np.uint8)
    for i in numba.prange(w):
        for j in numba.prange(h):
            result[i, j] = img[i, j, 2]*0.299 + img[i, j, 1]*0.587 + img[i, j, 0]*0.114
    return result

def slow_convert(img):
    w = img.shape[0]
    h = img.shape[1]
    result = np.zeros((w, h), dtype=np.uint8)
    for i in range(w):
        for j in range(h):
            result[i, j] = img[i, j, 2]*0.299 + img[i, j, 1]*0.587 + img[i, j, 0]*0.114
    return result

source1 = cv2.imread("../pics/1.jpg")
source2 = Image.open("../pics/1.jpg")

time1 = time.time()

img1 = cv2.cvtColor(source1, cv2.COLOR_BGR2GRAY)

time2 = time.time()

img2 = source2.convert("L")

time3 = time.time()

img3 = my_convert(source1)

time4 = time.time()

img4 = slow_convert(source1)

time5 = time.time()

print("%8f(opencv)" % (time2 - time1))

print("%8f(PIL)" % (time3 - time2))

print("%8f(numba jit)" % (time4 - time3))

print("%8f(slow one)" % (time5 - time4))



