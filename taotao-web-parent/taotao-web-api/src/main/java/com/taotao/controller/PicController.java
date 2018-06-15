package com.taotao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PicController {

    @Value("${LOCAL_IMAGE_DIR}")
    private String localImageDir;


    @Value("${IMAGE_URL}")
    private String imageUrl;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map<String, Object> uploadFiles(@RequestParam("uploadFile") MultipartFile uploadFile) {
        String originalFilename = uploadFile.getOriginalFilename();
        File newFile = new File(localImageDir + originalFilename);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            uploadFile.transferTo(newFile);// 将内存中的数据写入磁盘
            resultMap.put("url", imageUrl + originalFilename);
            resultMap.put("error", 0);
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("message", "文件上传失败");
            resultMap.put("error", 1);
        }
        return resultMap;


    }

}
