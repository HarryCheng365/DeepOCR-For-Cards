package com.deepocr.card.Service;

import com.alibaba.fastjson.JSONObject;
import com.deepocr.card.Entity.Entity;
import com.deepocr.card.Entity.UploadInfo;
import com.deepocr.card.dao.FileRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.apache.commons.io.FileUtils.copyFile;

@Service
public class FileUtil {
    @Resource
    private FileRepository fileRepository;
    private String suffix = "";

    public int saveFile(String guid, int user, String path, String feedback) {

        Entity entity = new Entity(feedback, guid, path, user);
        System.out.println(entity);
        try {
            Entity entity1 = fileRepository.save(entity);
            if (entity1 == null || entity1.getId() <= 0)
                return -1;
            return entity1.getId();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return -1;


    }

    public String compressFilePath(UploadInfo uploadInfo) {
        String contentType = uploadInfo.getFile().getContentType();
        String filename = uploadInfo.getUser() + "-" + uploadInfo.getGuid() + "-" + uploadInfo.getFile().getOriginalFilename();
        String filePath = "/Users/Haoyu/Documents/DeepOCR-For-Cards/UploadFiles/";
        String destiPath = "/Users/Haoyu/Documents/DeepOCR-For-Cards/CompressFile/";
        try {
            Thumbnails.of(uploadFile(uploadInfo.getFile().getBytes(), filePath, filename)).scale(1f).outputQuality(0.25f).
                    toFile("/Users/Haoyu/Documents/DeepOCR-For-Cards/CompressFile/" + filename);
            return destiPath + filename;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public boolean saveFileList(UploadInfo uploadInfo, JSONObject jsonObject) {

        if (!uploadInfo.getFile().isEmpty()) {

            String contentType = uploadInfo.getFile().getContentType();
            String filename = uploadInfo.getUser() + "-" + uploadInfo.getGuid() + "-" + uploadInfo.getFile().getOriginalFilename();
            String filePath = "/Users/Haoyu/Documents/DeepOCR-For-Cards/UploadFiles/";

            try {
                uploadFile(uploadInfo.getFile().getBytes(), filePath, filename);


            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }

            int temp = saveFile(uploadInfo.getGuid(), uploadInfo.getUser(), filePath + filename, jsonObject.toString());
            if (temp <= 0)
                return false;


            return true;
        } else
            return false;

    }

    public String addFeedback(String guid, String feedback) {
        Entity entity = null;
        try {
            entity = fileRepository.findByGuid(guid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (entity == null)
            return "未找到相应文件";

        entity.setFeedback(feedback);
        fileRepository.save(entity);
        return entity.getFeedback();

    }

    public void compressFile(String filepath) {
        File file = new File(filepath);
        if (isImageFile(file)) {
            if (!scaleImage(filepath, "/Users/Haoyu/Documents/DeepOCR-For-Cards/CompressFile/" + file.getName())) {
                System.out.println(file.getName() + "转化成功！");
            }

        }
    }

    public boolean isImageFile(File file) {

        String fileName = file.getName();

        //获取文件名的后缀，可以将后缀定义为类变量，共后面的函数使用
        suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        // 声明图片后缀名数组
        if (!suffix.matches("^[(jpg)|(png)|(gif)]+$")) {
            System.out.println("请输入png,jpg,gif格式的图片");
            return false;
        }
        return true;
    }

    public boolean scaleImage(String imgSrc, String imgDist) {
        try {
            File file = new File(imgSrc);
            if (!file.exists()) {
                return false;
            }

            InputStream is = new FileInputStream(file);
            Image src = ImageIO.read(is);

            if (src.getWidth(null) <= 600) {
                File tofile = new File(imgDist);
                if (!tofile.exists()) {
                    tofile.mkdirs();
                }
                copyFile(file, tofile);
                is.close();
                return true;
            }

            //获取源文件的宽高
            int imageWidth = ((BufferedImage) src).getWidth();
            int imageHeight = ((BufferedImage) src).getHeight();

            double scale = (double) 600 / imageWidth;

            //计算等比例压缩之后的狂傲
            int newWidth = (int) (imageWidth * scale);
            int newHeight = (int) (imageHeight * scale);

            BufferedImage newImage = scaleImage((BufferedImage) src, scale, newWidth, newHeight);

            File file_out = new File(imgDist);
            ImageIO.write(newImage, this.suffix, file_out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BufferedImage scaleImage(BufferedImage bufferedImage, double scale, int width, int height) {
        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(bufferedImage, new BufferedImage(width, height, bufferedImage.getType()));
    }

    public void copyFile(File fromFile, File toFile) throws IOException {
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n = 0;
        while ((n = ins.read(b)) != -1) {
            out.write(b, 0, n);
        }
        ins.close();
        out.close();
    }

    public String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
//        compressFile(filePath+fileName);
        return filePath + fileName;
        //        BufferedOutputStream stream = null;
//        for (int i = 0; i < files.size(); ++i) {
//            file = files.get(i);
//            if (!file.isEmpty()) {
//                try {
//                    byte[] bytes = file.getBytes();
//                    stream = new BufferedOutputStream(new FileOutputStream(
//                            new File(file.getOriginalFilename())));
//                    stream.write(bytes);
//                    stream.close();
//                } catch (Exception e) {
//                    stream = null;
//                    return "You failed to upload " + i + " => "
//                            + e.getMessage();
//                }
//            } else {
//                return "You failed to upload " + i
//                        + " because the file was empty.";
//            }
//        }
//        return "upload successful";
    }

    public byte[] myGetBytes(String filePath){
        byte[] buffer = null;
        try {
            System.out.println(filePath);
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}

