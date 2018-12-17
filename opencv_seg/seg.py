import cv2
import numpy as np


def fix_color(img):
    # opencv读图像是奇怪的BGR的顺序，在plot输出的时候重新排序成RGB
    (b, g, r) = cv2.split(img)
    return cv2.merge([r, g, b])


def get_card(input):
    # 计算整个图片的面积
    total_area = input.shape[0] * input.shape[1]
    # 灰度化
    imgray = cv2.cvtColor(input, cv2.COLOR_BGR2GRAY)
    # 二值化，去除深色背景设为0，其他的设为255
    ret, thresh = cv2.threshold(imgray, 127, 255, cv2.THRESH_BINARY)
    # 框出校园卡的大致轮廓
    _, contours, hierarchy = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    # 面积不够的轮廓直接扔掉，阈值设为总面积的0.3
    contours = [c for c in contours if cv2.contourArea(c) > total_area * 0.3]
    # 基本这里只会剩下一个
    contour = contours[0]
    # 用四边形拟合
    approx = cv2.approxPolyDP(contour, 0.1 * cv2.arcLength(contour, True), True)
    approx = approx.reshape((4, 2)).astype(np.float32)
    # 做投影消除透视畸变
    approx = np.array(sorted(approx, key=lambda p: p[0] + p[1]))
    corners = np.float32([[0, 0], [0, 540], [860, 0], [860, 540]])
    t = cv2.getPerspectiveTransform(approx, corners)
    return cv2.warpPerspective(input, t, (860, 540))


def get_pic(input):
    # 计算整个图片的面积
    total_area = input.shape[0] * input.shape[1]
    # 灰度化
    imgray = cv2.cvtColor(input, cv2.COLOR_BGR2GRAY)
    # 二值化，去除白色边框设为0，其他的设为255
    ret, thresh = cv2.threshold(imgray, 200, 255, cv2.THRESH_BINARY_INV)
    # 框出身份照的大致轮廓
    _, contours, hierarchy = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    # 面积不够的轮廓直接扔掉，阈值设为总面积的0.3
    contours = [c for c in contours if cv2.contourArea(c) > total_area * 0.3]
    # 基本这里只会剩下一个
    contour = contours[0]
    # 用四边形拟合
    approx = cv2.approxPolyDP(contour, 0.1 * cv2.arcLength(contour, True), True)
    approx = approx.reshape((4, 2)).astype(np.float32)
    # 做投影消除透视畸变
    approx = np.array(sorted(approx, key=lambda p: p[0] + p[1]))
    corners = np.float32([[0, 0], [250, 0], [0, 350], [250, 350]])
    t = cv2.getPerspectiveTransform(approx, corners)
    return cv2.warpPerspective(input, t, (250, 350))


def get_img_from_local():
    return cv2.imread('1.jpg')


def get_img_from_pipeline():
    # todo: implement
    pass


def seg(img):
    card = get_card(img)
    pic = card[50:355, 40:270]
    pic = get_pic(pic)
    word = card[355:520, 30:330]
    return pic, word


def plot():
    from matplotlib import pyplot as plt
    # plt.imshow(fix_color(cv2.drawContours(bgr_img, contours, -1, (0, 255, 0), 3)))
    # plt.imshow(fix_color(cv2.drawContours(bgr_img, [approx], -1, (0, 255, 0), 3)))
    plt.subplot(131), plt.imshow(fix_color(img)), plt.title('input')
    # plt.subplot(222), plt.imshow(fix_color(card)), plt.title('card')
    # plt.subplot(223), plt.imshow(fix_color(pic)), plt.title('pic')
    # plt.subplot(224), plt.imshow(fix_color(word)), plt.title('word')
    plt.subplot(132), plt.imshow(fix_color(pic)), plt.title('pic')
    plt.subplot(133), plt.imshow(fix_color(word)), plt.title('word')
    plt.show()


if __name__ == "__main__":
    # todo: 正式版本中应该是读管道
    img = get_img_from_local()
    # pic就是证件照，word就是文字部分的切片
    pic, word = seg(img)
    plot()

