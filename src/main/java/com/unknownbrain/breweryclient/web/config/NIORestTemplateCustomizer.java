package com.unknownbrain.breweryclient.web.config;

import lombok.Setter;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Setter
//@Component
//@ConfigurationProperties(prefix = "custom.nio.rest.template", ignoreUnknownFields = false)
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    private int maxTotal;
    private int defaultMaxPerRoute;

    private int connectionRequestTimeout;
    private int socketTimeout;
    private int ioThreadCount;

    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(this.clientHttpRequestFactory());
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {

        final DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(IOReactorConfig.custom().
                setConnectTimeout(connectionRequestTimeout).
                setSoTimeout(socketTimeout).
                setIoThreadCount(ioThreadCount).
                build());

        final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients
                .custom()
                .setConnectionManager(connectionManager)
                .build();

        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
    }
}
