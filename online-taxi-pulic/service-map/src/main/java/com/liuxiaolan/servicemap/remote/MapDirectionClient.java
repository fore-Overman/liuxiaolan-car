package com.liuxiaolan.servicemap.remote;

import com.liuxiaolan.internalcommon.constant.AmapConfigConstants;
import com.liuxiaolan.internalcommon.responese.DirectionResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//调用地图服务
@Service
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;
    
    /** 方法描述 调用高低api  返回距离和时长
    * @return 
    * @author liuxiaolan
    * @date 2023/4/24
    */
    public DirectionResponse direction(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        //组装请求路径
        /*https://restapi.amap.com/v3/direction/driving?
         origin=116.45925,39.910031   出发地
         &destination=116.587922,40.081577 目的地
         &output=json&key=939bdf7ad9118eb692326d00f1303b35*/
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(AmapConfigConstants.DIRECTTION_URL);
        urlBuild.append("?origin=");
        urlBuild.append(depLongitude).append(",").append(depLatitude).append("&");
        urlBuild.append("destination=").append(destLongitude).append(",").append(destLatitude).append("&");
        urlBuild.append("extensions=base");
        urlBuild.append("&output=json");
        urlBuild.append("&key=").append(amapKey);
        //调用高德接口   使用restTemplate
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuild.toString(), String.class);
        String directionBody = directionEntity.getBody();
        //解析接口
        DirectionResponse directionResponse = parseDirectionEntity(directionBody);
        return directionResponse;
    }



    private DirectionResponse parseDirectionEntity(String directionBody){
        //解析json
        DirectionResponse directionResponse = null;
        try{

            JSONObject result = JSONObject.fromObject(directionBody);
            //判断是否有这个key
            if(result.has(AmapConfigConstants.STATUS)){
                int status =  result.getInt(AmapConfigConstants.STATUS);
                if(status == 1){ //成功
                  if(result.has(AmapConfigConstants.STATUS)){
                      JSONObject routeJson = result.getJSONObject(AmapConfigConstants.ROUTE);
                      //得到paths 数组
                      JSONArray jsonArray = routeJson.getJSONArray(AmapConfigConstants.PATHS);
                      JSONObject jsonObject = jsonArray.getJSONObject(0);
                      directionResponse = new DirectionResponse();
                      if(jsonObject.has(AmapConfigConstants.DISTANCE)){
                          int distance = jsonObject.getInt(AmapConfigConstants.DISTANCE);
                          directionResponse.setDistance(distance);
                      }
                      if(jsonObject.has(AmapConfigConstants.DURATION)){
                          int duration = jsonObject.getInt(AmapConfigConstants.DURATION);
                          directionResponse.setDuration(duration);
                      }
                  }
                }
            }
        }catch (Exception e){

        }
        return directionResponse;
    }







    
}
