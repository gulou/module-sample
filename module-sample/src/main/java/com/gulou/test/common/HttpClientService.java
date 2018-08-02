package com.gulou.test.common;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * <p>http请求服务</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/18 14:52
 */

@Service
@Slf4j
public class HttpClientService {

    private CloseableHttpClient createClient() {
        return HttpClients.createDefault();
    }

    /**
     * Get请求
     * @param uri
     * @param headers
     * @param responseHandler
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T doGet(String uri, Map<String,String> headers,ResponseHandler<T> responseHandler) throws IOException {

        T responseBody = null;

        try (CloseableHttpClient httpClient = createClient()) {

            HttpGet httpget = new HttpGet(uri);

            for(Map.Entry<String,String> entry : headers.entrySet()){
                httpget.addHeader(entry.getKey(),entry.getValue());
            }

            responseBody = httpClient.execute(httpget, responseHandler);
        }
        return responseBody;
    }

    /**
     * post请求
     * @param uri
     * @param headers
     * @param params
     * @param responseHandler
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T doPost(String uri,String params, Map<String,String> headers,ResponseHandler<T> responseHandler) throws IOException {

        T responseBody = null;

        try (CloseableHttpClient httpClient = createClient()) {

            HttpPost httpPost = new HttpPost(uri);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpPost.setEntity(new StringEntity(params));


            for(Map.Entry<String,String> entry : headers.entrySet()){
                httpPost.addHeader(entry.getKey(),entry.getValue());
            }

            responseBody = httpClient.execute(httpPost, responseHandler);
        }
        return responseBody;
    }

    public ResponseHandler<String> defaultHandler() {
        return response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity == null ?
                        null : StringUtils.toEncodedString(StreamUtils.copyToByteArray(entity.getContent()), Charset.forName("UTF-8"));
            }
            else {
                log.error("[defaultHandler] : response error,status=[{}]", status);
                return null;
            }
        };
    }

    public static void main(String[] args) throws IOException {
        HttpClientService clientService = new HttpClientService();
        String params = "{\n" +
                "  \"name\":\"gulou\",\n" +
                "  \"age\":20\n" +
                "}";
        Map<String,String> headers = Maps.newHashMap();
        headers.put("language","engli");
        clientService.doPost("http://localhost:8080/hello/http-test-post", params,headers,clientService.defaultHandler());
    }
}
