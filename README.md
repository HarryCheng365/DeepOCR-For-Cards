# DeepOCR-For-Cards
> Based on Java Web Project and Deep Learning Method, Extract Text Information from Students ID Card or  Identity Card 

### Goal

我们要做一个拥有比较高精度的，可以检测识别并提取用户上传的身份证或校园卡上信息的WEB项目。

### Brief

- Step1：用户拍摄身份证或者校园卡正面照（大小应有限制）并上传至服务器

- Step2:   服务器接受上传的图片，存至数据库，并传入消息中间件（未选定 Kafak 或其他）

- Step3:   消息中间件将图片传给Python环境下运行的模型

- Step4:   Python模型接受，通过OpenCV对图片进行处理，首先从图片中切割出校园卡（要考虑到边缘是否残缺，如果技术有难题，可以考虑让用户二次上传作为备选）

- Step5:   如果判断结果为True，则再通过OpenCV 对图像进行数据增强，比如增强对比度，或者二值化等操作

- Step6:   将数据增强后的图像输入OCR模型(Deep-OCR 或者传统的OCR模型) ，可以考虑增加语义模型，增加识别精度（如果有时间
- Step7:   输出信息处理并返还展示在WEB界面（一定要美观

### Structure

> WEB 端

- Html + Bootstrap + Angular JS
- SpringBoot (包管理器 Maven) + Mysql

> 消息中间件

- 

> Python环境

- Anaconda(包管理器)
- OpenCV 图像切割
- Github Deep - OCR 模型（主要是GoogleNet 等等

### Reference

1. Deep-OCR 比较成熟的模型https://github.com/JinpengLI/deep_ocr
2. Deep-HCCR 数据集比较不错https://github.com/chongyangtao/DeepHCCR
3. Spring Boot官网 https://spring.io/projects/spring-boot 
4. Spring Boot 连接Mysql 数据库 https://www.cnblogs.com/liangblog/p/5228548.html
5.  Spring Boot中使用Mysql 和 JPA https://www.cnblogs.com/douyamv/p/6607128.html
6. 通过Jython的方式 实现java和python互相调用的方式传递参数https://blog.csdn.net/wxiaow9000/article/details/51660299
7. 通过命令行的方式 Java和Python互相调用的方式传递参数https://blog.csdn.net/yue31313/article/details/79571540
8. USB连接 利用Chorme的Dev-Tool 进行调试 http://www.cnblogs.com/xy-nb/p/web.html 

### Distribution

1. Web 部分以及中间件 HarryCheng, ZZAster
2. OpenCV CClyx, KarenZhuu
3. Python模型部分 Haibaradu, Angus-ZHU





### Requirements

#### 最低要求

- 用户上传图片，切割并返回显示文本信息 切割识别准确率+模型OCR识别率 综合应在70%以上

#### 中级要求

- 利用USB外接设备 调用手机摄像头拍摄图片并上传 切割并返回显示文本信息 切割识别准确率+模型OCR识别率综合应在80%以上

#### 理想要求

- 服务器部署后 用户打开相应WEB页面 点击上传文件 调用手机原生摄像头 辅助拍摄图片并上传 切割并返回显示文本信息 切割识别准确率+模型OCR识别率 综合应在90%左右



### Schedule

#### 第一周 18.12.4-18.12.11

> Web组

- 完成环境搭建 选定前端模版 并撸出带用户登录注册 上传的主页面 

> OpenCV组

- 完成环境搭建 尝试学习 如何检测出 校园卡/身份证

> Python模型部分

- 完成环境搭建跑通 Deep-OCR或其他类似项目 看一看准确率是否有说的那么高

#### 第二周 18.12

> Web组

- 基本UI改好 / 选取文件上传接受 保存至本地，并将图片地址存入数据库 实现java程序 与 Python程序的通信