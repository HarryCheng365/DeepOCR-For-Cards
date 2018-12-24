package com.deepocr.card.Service;

import com.alibaba.fastjson.JSONObject;
import com.deepocr.card.Entity.Entity;
import com.deepocr.card.Entity.UploadInfo;
import com.deepocr.card.dao.FileRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class FileUtil {
    @Resource
    private FileRepository fileRepository;

    public int saveFile(String guid,int user,String path,String feedback){

        Entity entity = new Entity(feedback,guid,path,user);
        System.out.println(entity);
        try {
            Entity entity1 = fileRepository.save(entity);
            if(entity1 ==null|| entity1.getId()<=0)
                return -1;
            return entity1.getId();
        }
        catch(Exception e){
            e.printStackTrace();

        }
        return -1;


    }

    public boolean saveFileList(UploadInfo uploadInfo, JSONObject jsonObject){

    if(!uploadInfo.getFile().isEmpty()) {

    String contentType = uploadInfo.getFile().getContentType();
    String filename = uploadInfo.getUser()+"-"+uploadInfo.getGuid()+"-"+uploadInfo.getFile().getOriginalFilename();
    String filePath = "/Users/Haoyu/Documents/DeepOCR-For-Cards/UploadFiles/";

    try {
        uploadFile(uploadInfo.getFile().getBytes(), filePath, filename);
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return false;
    }

    int temp = saveFile(uploadInfo.getGuid(), uploadInfo.getUser(), filePath+filename, jsonObject.toString());
    if (temp <= 0)
        return false;


    return true;
}
else
    return false;

    }

    public String addFeedback(String guid,String feedback){
        Entity entity =null;
        try {
           entity = fileRepository.findByGuid(guid);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(entity ==null)
            return "未找到相应文件";

        entity.setFeedback(feedback);
        fileRepository.save(entity);
        return entity.getFeedback();

    }



    public void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
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

}

