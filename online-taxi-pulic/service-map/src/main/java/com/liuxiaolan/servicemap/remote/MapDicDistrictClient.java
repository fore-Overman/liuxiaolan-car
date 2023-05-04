package com.liuxiaolan.servicemap.remote;

import com.liuxiaolan.internalcommon.constant.AmapConfigConstants;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapDicDistrictClient {
    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${amap.key}")
    private String key;


    public String dicDistrict(String keywords){
        //https://restapi.amap.com/v3/config/district
        // ?keywords=北京
        // &subdistrict=2
        // &key=<用户的key>

        //拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.DISTRICT_URL);
        url.append("?keywords=");
        url.append(keywords);
        url.append("&subdistrict=3");
        url.append("&");
        url.append("key="+key);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);
        String directionBody = forEntity.getBody();
        return directionBody;
    }
}
