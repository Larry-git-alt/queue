package cn.queue.common.util;

import cn.hutool.json.JSONObject;

import cn.hutool.json.JSONUtil;
import cn.queue.common.domain.CommonResult;
import cn.queue.common.domain.entity.UserEntity;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class HttpUtils {
    public static OkHttpClient client = new OkHttpClient();
    public static Request.Builder builder = new Request.Builder();


    public static JSONObject doPost(String url, Object object) {
        String json = JSONUtil.toJsonStr(object);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        Request request = builder.url(url).post(body).build();

        //创建一个通话
        Call call = client.newCall(request);
//执行请求，结束时关闭连接
        try (Response response = call.execute()) {
            //返回响应
            if (response.isSuccessful() && 200 == response.code()) {
                ResponseBody responseBody = response.body();
                String jsonString = responseBody.string();
                //将响应体转化成字符串
                System.out.println(jsonString);
                System.out.println("成功");
                JSONObject jsonObject = new JSONObject(jsonString);
                return jsonObject;
            } else {
                //失败时返回失败信息
                System.out.println("失败");
                System.out.println(response.message());
                System.out.println("失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
