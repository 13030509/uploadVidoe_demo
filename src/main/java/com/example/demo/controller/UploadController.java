package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("fileHander")
public class UploadController {

    @Value("${pic.webPath}")
    String webPaths;

    @Value("${pic.savePath}")
    String savePaths;


    @RequestMapping(value = "index")
    public String index(){
        return "index";
    }


    @RequestMapping(value = "uploadVidoe", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> savaVideo(@RequestParam("file") MultipartFile file)
            throws IllegalStateException {
        Map<String,String> resultMap = new HashMap<>();
        try{
            //获取文件后缀
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            // 重构文件名称
            String pikId = UUID.randomUUID().toString().replaceAll("-", "");
            String newVidoeName = pikId + "." + fileExt;
            //保存视频
            File fileSave = new File(savePaths, newVidoeName);
            file.transferTo(fileSave);
            resultMap.put("resCode","1");
            resultMap.put("webShowPath",webPaths + newVidoeName);

            return  resultMap;

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("resCode","0");
            return  resultMap ;

        }
    }

}
