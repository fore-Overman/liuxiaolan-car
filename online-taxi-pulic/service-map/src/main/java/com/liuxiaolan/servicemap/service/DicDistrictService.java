package com.liuxiaolan.servicemap.service;

import com.liuxiaolan.internalcommon.constant.AmapConfigConstants;
import com.liuxiaolan.internalcommon.constant.CommonStatusEnum;
import com.liuxiaolan.internalcommon.dto.DicDistrict;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.servicemap.mapper.DicDistrictMapper;
import com.liuxiaolan.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;


    public ResponseResult initDicDistrict(String keywords){
         //请求地图
        String dicDistrict = mapDicDistrictClient.dicDistrict(keywords);
        JSONObject dicDistrictJson = JSONObject.fromObject(dicDistrict);
        int status = dicDistrictJson.getInt(AmapConfigConstants.STATUS);
        if(status != 1){
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(),CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }
        JSONArray countryJsonArray = dicDistrictJson.getJSONArray(AmapConfigConstants.DISTRICTS);
        for(int i = 0;i<countryJsonArray.size();i++){
            //国家
            JSONObject districtObject = countryJsonArray.getJSONObject(i);
            String addressCode = districtObject.getString(AmapConfigConstants.ADCODE);
            String name = districtObject.getString(AmapConfigConstants.NAME);
            String parentAddressCode = "0";
            String level = districtObject.getString(AmapConfigConstants.LEVEL);
            insertDicDistrict(addressCode,name,parentAddressCode,level);
            //省市
            JSONArray proviceJsonArray = districtObject.getJSONArray(AmapConfigConstants.DISTRICTS);
            for(int p = 0;p<proviceJsonArray.size();p++){
                JSONObject proviceObject = proviceJsonArray.getJSONObject(p);
                String  ProviceaddressCode = proviceObject.getString(AmapConfigConstants.ADCODE);
                String  Proviceaddname = proviceObject.getString(AmapConfigConstants.NAME);
                String  ProviceparentAddressCode = addressCode;
                String  Provicelevel = proviceObject.getString(AmapConfigConstants.LEVEL);
                insertDicDistrict(ProviceaddressCode,Proviceaddname,ProviceparentAddressCode,Provicelevel);

                JSONArray JsonArray = proviceObject.getJSONArray(AmapConfigConstants.DISTRICTS);
                for(int c = 0;c<JsonArray.size();c++){
                    JSONObject cityObject = JsonArray.getJSONObject(c);
                    String  CityaddressCode = cityObject.getString(AmapConfigConstants.ADCODE);
                    String  Cityname = cityObject.getString(AmapConfigConstants.NAME);
                    String  CityAddressCode = ProviceaddressCode;
                    String  Citylevel = cityObject.getString(AmapConfigConstants.LEVEL);
                    if(Citylevel.trim().equals(AmapConfigConstants.STREET)){
                        continue;
                    }
                    insertDicDistrict(CityaddressCode,Cityname,CityAddressCode,Citylevel);
                }
            }





        }

        return ResponseResult.success();
    }

    /** 方法描述
    * @return
    * @author liuxiaolan
    * @date 2023/5/4
    */

    public void insertDicDistrict(String addressCode,String name,String parentAddressCode,String level){
        //组装实体
        DicDistrict dicDistrict1 = new DicDistrict();
        dicDistrict1.setAddressCode(addressCode);
        dicDistrict1.setAddressName(name);
        dicDistrict1.setParentAddressCode(parentAddressCode);
        int levelInt = generateLevel(level);
        dicDistrict1.setLevel(levelInt);
        dicDistrictMapper.insert(dicDistrict1);
    }


    /** 方法描述 level等级 转换
    * @return
    * @author liuxiaolan
    * @date 2023/5/4
    */
    public int generateLevel(String level){
       int levelInt = 0;
       if(level.trim().equals("country")){
           levelInt = 0;
       } else if (level.trim().equals("province")) {
           levelInt = 1;
       } else if (level.trim().equals("city")) {
           levelInt = 2;
       }else if (level.trim().equals("district")) {
           levelInt = 3;
       }

       return levelInt;

    }




}
