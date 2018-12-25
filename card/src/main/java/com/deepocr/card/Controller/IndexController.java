package com.deepocr.card.Controller;

import antlr.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deepocr.card.Entity.UploadInfo;
import com.deepocr.card.Service.FileUtil;

import com.deepocr.card.dao.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpContext;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/upload")
public class IndexController {
    @Autowired
    FileRepository fileRepository;
    @Resource
    private FileUtil fileUtil;

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value="/",method= RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }


    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject handleFileUpload(HttpServletRequest request) throws IOException{
        System.out.println(request.getParameter("guid"));
        System.out.println(request.getParameter("user"));

        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        MultipartFile file = ((MultipartHttpServletRequest) request)
                .getFile("file");
        System.out.println(file.getOriginalFilename());
        UploadInfo uploadInfo = new UploadInfo(request.getParameter("guid"), Integer.parseInt(request.getParameter("user")), file);
        //File convertFile = new File(file.getOriginalFilename());
        //file.transferTo(convertFile);
        String result;
        String basePath = "/Users/Haoyu/Documents/DeepOCR-For-Cards/UploadFiles/";


        try {
            Socket socket = new Socket();
            socket.setSoTimeout(50000);
            socket.connect(new InetSocketAddress("39.108.104.137", 8000), 10000);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            //FileInputStream fileInputStream = new FileInputStream(convertFile);
            byte[] sendBytes = new byte[4096];
            byte[] getBytes = new byte[4096];
            //int length = 0;
            //while ((length = fileInputStream.read(sendBytes)) > 0)
            //    os.write(sendBytes, 0, length);
            String filename = file.getOriginalFilename();
            String sub;
            if (filename.indexOf('.') == -1)
                sub = ".jpg";
            else
                sub = filename.substring(filename.lastIndexOf('.'));
            System.out.println(sub);
            os.write(sub.getBytes());
            //os.write((file.getBytes().length+"").getBytes());
            //os.write(file.getBytes());
            os.write(fileUtil.myGetBytes(fileUtil.compressFilePath(uploadInfo)));
            //os.write("exit".getBytes("UTF-8"));
            System.out.println("write finish");
            //OutputStream fileStream = new FileOutputStream(new File("/Users/Haoyu/Desktop/test.jpg"));
            //fileStream.write(file.getBytes());
            //fileStream.close();
            //os.flush();
            is.read(getBytes);
            result = new String(getBytes, "UTF-8");

            socket.close();
        }
        catch (SocketTimeoutException | SocketException e)
        {
            result = e.getMessage();
            result="{\"info\":\"服务器响应异常，请重新上传图片或重新拍摄\"}";
            return JSONObject.parseObject(result);
        }

        result=StringUtils.stripFront(result,'}');
        if(result.contains("error")){
            result="{\"info\":\"图片识别异常，请重新上传图片或重新拍摄\"}";
            System.out.println(result);
            return JSONObject.parseObject(result);
        }


        JSONObject jsonObject= JSONObject.parseObject(result);
        System.out.println(jsonObject);



//        UploadInfo uploadInfo = new UploadInfo(request.getParameter("guid"), Integer.parseInt(request.getParameter("user")), file);
        if(fileUtil.saveFileList(uploadInfo,jsonObject))
            return jsonObject;
        else
            return null;

    }


}
