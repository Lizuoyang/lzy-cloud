package com.lzy.cloud.utils;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import net.minidev.json.JSONObject;

import java.text.ParseException;

/**
 * jwt令牌工具类
 *
 * @author 2428
 * @date 2022/06/22
 */
public class JwtUtils {

    public static JSONObject decodeJwt(String jwt)
    {
        JWSObject jwsObject;
        JSONObject jsonObject = null;
        try {
            jwsObject = JWSObject.parse(jwt);
            Payload payload = jwsObject.getPayload();
            jsonObject = payload.toJSONObject();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
