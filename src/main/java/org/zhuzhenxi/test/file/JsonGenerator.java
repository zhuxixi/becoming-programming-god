package org.zhuzhenxi.test.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.zhuzhenxi.test.random.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonGenerator {

    private static JSONObject jsonObject = JSON.parseObject("{\"CB\":\"000000\",\"CL\":\"000000\",\"XRYC\":\"000000\",\"E\":\"000000\",\"AS\":\"000000\",\"amis\":\"000000\",\"epay\":\"000000\",\"sams01\":\"000000\",\"amis01\":\"000000\",\"n3s03\":\"000000\",\"n3s02\":\"000000\",\"n3s01\":\"000000\",\"clhp01\":\"000000\",\"amis02\":\"000000\",\"csamp01\":\"000000\",\"n3s04\":\"000000\",\"ecss01\":\"000000\",\"CSAMP\":\"000000\",\"csbps01\":\"000000\"}");

    private  static Map<Integer,String> provins = new HashMap<>();


    static {
        int i = 0;
        for (Map.Entry entry : jsonObject.entrySet()) {
            provins.put(i, (String) entry.getKey());
            i++;
        }
    }
    public static void main(String[] args) {

        List<String> pids = FileUtil.readFileByPath("/Users/zhuzhenxi/Downloads/spi_info_no.csv");
        List<String> messages = FileUtil.readFileByPath("/Users/zhuzhenxi/Downloads/geshihua.csv");
        List<JSONObject> result = new ArrayList<>();

        for (int i = 0; i < messages.size(); i++) {
            JSONObject object = JSONObject.parseObject(messages.get(i));
            object.put("sendTo", pids.get(i));
            int random1 = (int)RandomUtil.generateRandomNumber(1);
            int random2 = (int)RandomUtil.generateRandomNumber(1);
            object.put("msgType", provins.get(random1+random2+1));

            result.add(object);
        }
        FileUtil.writeFileByPath("/Users/zhuzhenxi/Downloads/epush_controller_param.csv",result);

    }
}
