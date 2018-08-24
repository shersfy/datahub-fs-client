package org.shersfy.datahub.fs.client.controller;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.shersfy.datahub.commons.meta.HdfsMeta;
import org.shersfy.datahub.commons.utils.FileUtil;
import org.shersfy.datahub.fs.client.service.RPCClient;
import org.shersfy.datahub.fs.protocols.FsStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FsClientController extends BaseController{
    
    @Autowired
    private RPCClient client;
    
    @GetMapping("/upload/local")
    public Object uploadLocal(String src, String tar) {
        FsStreamService service = client.getFsStreamService();
        service.connect(null);
        service.createNewFile(new Text(FileUtil.concat(tar, FilenameUtils.getName(src))));
        
        try {
            BytesWritable bytes = new BytesWritable();
            InputStream input = new FileInputStream(src);
            byte[] cache = new byte[2048];
            int len = 0;
            while((len = input.read(cache))!=-1) {
                bytes.set(cache, 0, len);
                service.write(bytes);
            }
            IOUtils.closeQuietly(input);
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        service.closeOutputStream();
        
        return tar;
    }
    
    @GetMapping("/upload/hdfs")
    public Object uploadHdfs(String src, String tar) {
        FsStreamService service = client.getFsStreamService();
        HdfsMeta meta = new HdfsMeta();
        meta.setUserName("hadoop");
        meta.setUrl("hdfs://192.168.186.129:9000/");
        service.connect(new Text(meta.toString()));
        service.createNewFile(new Text(FileUtil.concat(tar, FilenameUtils.getName(src))));
        
        try {
            BytesWritable bytes = new BytesWritable(new byte[2048]);
            InputStream input = new FileInputStream(src);
            while(input.read(bytes.getBytes())!=1) {
                service.write(bytes);
            }
            IOUtils.closeQuietly(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        service.closeOutputStream();
        
        return tar;
    }

}
