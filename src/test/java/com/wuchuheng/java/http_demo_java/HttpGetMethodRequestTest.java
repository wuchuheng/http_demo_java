package com.wuchuheng.java.http_demo_java;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class HttpGetMethodRequestTest {

    /**
     * 把Map数据转换为url格式上的数据
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public String convertParamsToUrlParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }


    /**
     * Get 请求测试
     */
    @Test
    void httpGetTest() throws IOException {
        // 1 请求参数url序列化
        Map<String, String> params = new HashMap<>();
        params.put("foo", "1");
        params.put("bar", "2");
        String paramsUrlStr = convertParamsToUrlParams(params);
        URL obj = new URL("http://localhost:8080/?" + paramsUrlStr);
        // 2 TCP 连接handler
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // 3 GET 请求方式
        con.setRequestMethod("GET");
        con.setDoInput(true);
        // 4 响应头部
        Map<String, List<String>> headers = con.getHeaderFields();
        // 5 响应状态码
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
        String inputLine;
        // 6 响应内容
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        // 7 关闭连接和缓存
        in.close();
        con.disconnect();
        System.out.println(responseCode);
        System.out.println(headers);
        System.out.println(response);
    }

    @Test
    void httpPostTest() throws IOException {
        // 1 请求参数url序列化
        Map<String, String> params = new HashMap<>();
        params.put("foo", "1");
        params.put("bar", "2");
        String paramsUrlStr = convertParamsToUrlParams(params);
        URL obj = new URL("http://localhost:8080/?" + paramsUrlStr);
        // 2 TCP 连接handler
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        // 3 GET 请求方式
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        // 3.1 设置请求体参数
        String requestBodyJson = "{\"body1\": 1, \"body2\": 2}";
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(requestBodyJson);
        out.flush();
        out.close();
        // 4 获取响应头部
        Map<String, List<String>> headers = con.getHeaderFields();
        // 5 获取响应状态码
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
        String inputLine;
        // 6 获取响应内容
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        // 7 关闭连接和缓存
        in.close();
        con.disconnect();
        System.out.println(responseCode);
        System.out.println(headers);
        System.out.println(response);
    }
}
