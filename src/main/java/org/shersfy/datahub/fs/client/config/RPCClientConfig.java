package org.shersfy.datahub.fs.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="rpc.client.connect.server")
public class RPCClientConfig {
    
    /**rpc server普通服务host*/
    private String host;
    
    /**rpc server普通服务端口*/
    private int port;
    
    /**rpc server FileSystem服务host*/
    private String fsServiceHost;
    
    /**rpc server FileSystem服务端口*/
    private int fsServicePort;

    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFsServiceHost() {
        return fsServiceHost;
    }

    public void setFsServiceHost(String fsServiceHost) {
        this.fsServiceHost = fsServiceHost;
    }

    public int getFsServicePort() {
        return fsServicePort;
    }

    public void setFsServicePort(int fsServicePort) {
        this.fsServicePort = fsServicePort;
    }

    
}
