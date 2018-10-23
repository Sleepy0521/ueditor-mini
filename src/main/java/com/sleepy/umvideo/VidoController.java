package com.sleepy.umvideo;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


@RestController
public class VidoController {
    @PostMapping("/uploadVideo")
    public String uploadVideo(@RequestParam("upvideo") MultipartFile upvideo)  {
        String fileName = upvideo.getOriginalFilename();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            InputStream is = upvideo.getInputStream();
            FileOutputStream fos = new FileOutputStream(".\\src\\main\\resources\\static\\video\\"+fileName);
            bis=new BufferedInputStream(is);
            bos=new BufferedOutputStream(fos);
            int len;
            byte[] bytes = new byte[1024];
            while((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bos!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("url", "http://localhost:8081/video/"+fileName);// 展示图片的请求url
        result.put("state", "SUCCESS");
        String jStr = JSON.toJSONString(result);
        return "http://localhost:8081/video/"+fileName;
    }
    @GetMapping(value = "/hello")
    public String hello(){
        return "success";
    }

}
