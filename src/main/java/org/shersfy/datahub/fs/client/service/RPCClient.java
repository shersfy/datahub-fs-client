package org.shersfy.datahub.fs.client.service;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.shersfy.datahub.fs.client.config.RPCClientConfig;
import org.shersfy.datahub.fs.protocols.FsStreamService;
import org.shersfy.datahub.fs.protocols.StandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RPCClient {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private RPCClientConfig config;
    
    private StandardService standardService;
    
    @PostConstruct
    protected void init() {
        
        InetSocketAddress addr = new InetSocketAddress(config.getHost(), config.getPort());
        try {
            standardService = RPC.getProxy(StandardService.class, StandardService.versionID, addr, new Configuration(false));
        } catch (IOException e) {
            logger.error("", e);
        }
        
    }
    
    public FsStreamService getFsStreamService() {
        FsStreamService fsStreamService = null;
        InetSocketAddress addr = new InetSocketAddress(config.getFsServiceHost(), config.getFsServicePort());
        try {
            fsStreamService = RPC.getProxy(FsStreamService.class, FsStreamService.versionID, addr, new Configuration(false));
        } catch (IOException e) {
            logger.error("", e);
        }
        
        return fsStreamService;
    }
    
    public StandardService getStandardService() {
        return standardService;
    }

}
