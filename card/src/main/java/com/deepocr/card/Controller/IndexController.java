package com.deepocr.card.Controller;

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


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
    public String handleFileUpload(HttpServletRequest request) {
        System.out.println(request.getParameter("guid"));
        System.out.println(request.getParameter("user"));

        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        MultipartFile file = ((MultipartHttpServletRequest) request)
                .getFile("file");

        UploadInfo uploadInfo=new UploadInfo(request.getParameter("guid"),Integer.parseInt(request.getParameter("user")),file);
        return fileUtil.saveFileList(uploadInfo);

    }


}
