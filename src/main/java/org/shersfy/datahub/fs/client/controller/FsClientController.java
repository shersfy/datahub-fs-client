package org.shersfy.datahub.fs.client.controller;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.shersfy.datahub.commons.utils.FileUtil;
import org.shersfy.datahub.fs.protocols.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FsClientController extends BaseController{
    
    @Autowired
    private StandardService standardService;
    
    @GetMapping("/upload/local")
    public Object uploadLocal(String src, String tar) {
        return upload(null, src, tar);
    }
    
    @GetMapping("/upload/hdfs")
    public Object uploadHdfs(String src, String tar) {
        return upload(1L, src, tar);
    }

    protected Object upload(Long resId, String src, String tar) {
        
        Text key = standardService.fsConnect(new Text("myclient"), resId==null?null:new LongWritable(resId));
        Text path = new Text(FileUtil.concat(tar, FilenameUtils.getName(src)));
        
        BooleanWritable res = standardService.fsCreateNewFile(key, path);
        if(!res.get()) {
            return "error";
        }
        
        try {
            BytesWritable bytes = new BytesWritable();
            InputStream input = new FileInputStream(src);
            byte[] cache = new byte[2048];
            int len = 0;
            while((len = input.read(cache))!=-1) {
                bytes.set(cache, 0, len);
                standardService.fsWrite(key, bytes);
            }
            IOUtils.closeQuietly(input);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            standardService.fsClose(key);
        }
        
        return tar;
    }
    
}
