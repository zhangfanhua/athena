package com.xx.cortp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 10:16
 * @description:
 */
@Component
@Slf4j
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    public int getPort() {
        return this.serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
        log.info("项目启动启动成功！访问地址: http://localhost:{}/common", serverPort);
    }

}
