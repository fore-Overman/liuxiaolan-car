package com.liuxiaolan.serviceprice.service;

import com.liuxiaolan.internalcommon.constant.CommonStatusEnum;
import com.liuxiaolan.internalcommon.dto.PriceRule;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.ForecastPriceDTO;
import com.liuxiaolan.internalcommon.responese.DirectionResponse;
import com.liuxiaolan.internalcommon.responese.ForecastPriceResponse;
import com.liuxiaolan.internalcommon.util.BigDecimalUtils;
import com.liuxiaolan.serviceprice.mapper.PriceRuleMapper;
import com.liuxiaolan.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;


    public ResponseResult forecastPrice(String depLongitude,String depLatitude,
                                        String destLongitude,String destLatitude){

        ForecastPriceDTO forDto = new ForecastPriceDTO();
        forDto.setDepLongitude(depLongitude);
        forDto.setDepLatitude(depLatitude);
        forDto.setDestLongitude(destLongitude);
        forDto.setDestLatitude(destLatitude);
        //调用地图服务 查询距离和时长
        ResponseResult<DirectionResponse> driving = serviceMapClient.driving(forDto);
        Integer distance = driving.getData().getDistance();//距离
        Integer duration = driving.getData().getDuration();//时常
        log.info("距离："+distance);
        log.info("时常："+duration);
        //读取计价规则
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("city_code","110000");//城市编码
        queryMap.put("vehicle_type","1");//车型
        List<PriceRule> priceRule = priceRuleMapper.selectByMap(queryMap);
        if(priceRule.size() == 0){
           return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule prRule = priceRule.get(0);
        //根据距离时长  及  计价规则 计算价格
        double allPrice = getPrice(distance,duration,prRule);

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(allPrice);
        return ResponseResult.success(forecastPriceResponse);
    }

    /** 方法描述 根据距离和时长 计算价格
    * @return 
    * @author liuxiaolan
    * @date 2023/4/25
    */
    private  double getPrice(Integer distance,Integer duration,PriceRule prRule){
        double price = 0;
        //起步价
        double startFare = prRule.getStartFare();
        price = BigDecimalUtils.add(price,startFare);
        //里程费 = （总里程 - 起步公里）*
        //总里程 km
        double divide = BigDecimalUtils.divide(distance,1000);
        //起步里程
        double subtract = BigDecimalUtils.subtract(divide,prRule.getStartMile());
        //最终收费的里程数
        Double endMile = subtract<0?0:subtract;
        // 里程总价格
        double bigDecimal = BigDecimalUtils.multiply(endMile,prRule.getUnitPricePerMile());
        price = BigDecimalUtils.add(bigDecimal,price);
        //时长费
        //时长分钟数
        double timeDecimal = BigDecimalUtils.divide(duration,60);
        //每分钟的价格
        //时长总费用
        double timeFare = BigDecimalUtils.multiply(timeDecimal,prRule.getUnitPricePerMinute());
        //总价格
        price = BigDecimalUtils.add(price,timeFare);
        return price;
    }





}
