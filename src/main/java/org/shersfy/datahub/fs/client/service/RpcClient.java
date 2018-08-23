package org.shersfy.datahub.fs.client.service;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.shersfy.datahub.fs.client.config.RpcClientConfig;
import org.shersfy.datahub.fs.protocols.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RpcClient {
    
    @Autowired
    private RpcClientConfig config;
    
    private FileUploadService fileUploadService;
    
    @PostConstruct
    protected void init() {
        
        InetSocketAddress addr = new InetSocketAddress(config.getHost(), config.getPort());
        try {
            fileUploadService = RPC.getProxy(FileUploadService.class, FileUploadService.versionID, addr, new Configuration(false));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
