package cn;

import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;

public class UpdateInterFaceImpl {

	public static ResultModel checkNewVersion(String string) {
		try {
			Map<String, String> params = new HashMap<>();
			params.put("version", string);
			String sendPost = HttpUtils.sendGet(URL.CHECK_VERSION, params);
			System.out.println("sendPost=" + sendPost);
			if (sendPost != null && !sendPost.isEmpty()) {
				return JSON.parseObject(sendPost, ResultModel.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
