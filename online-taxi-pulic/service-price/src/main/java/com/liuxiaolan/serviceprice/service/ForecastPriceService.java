package com.liuxiaolan.serviceprice.service;

import com.liuxiaolan.internalcommon.constant.CommonStatusEnum;
import com.liuxiaolan.internalcommon.dto.PriceRule;
import com.liuxiaolan.internalcommon.dto.ResponseResult;
import com.liuxiaolan.internalcommon.request.ForecastPriceDTO;
import com.liuxiaolan.internalcommon.responese.DirectionResponse;
import com.liuxiaolan.internalcommon.responese.ForecastPriceResponse;
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
        BigDecimal price = new BigDecimal(0);

        //起步价
        Double startFare = prRule.getStartFare();
        BigDecimal startBigDecimal = new BigDecimal(startFare);
        price = price.add(startBigDecimal);
        //里程费 = （总里程 - 起步公里）*
        BigDecimal distanceDecimal = new BigDecimal(distance);
        //总里程 km
        BigDecimal divide = distanceDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_CEILING);
        //起步里程
        BigDecimal startMile = new BigDecimal(prRule.getStartMile());
        double subtract = divide.subtract(startMile).doubleValue();
        //最终收费的里程数
        Double endMile = subtract<0?0:subtract;
        BigDecimal endMileM = new BigDecimal(endMile);
        // 里程总价格
        BigDecimal unitP = new BigDecimal(prRule.getUnitPricePerMile());
        BigDecimal bigDecimal = endMileM.multiply(unitP).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        price = price.add(bigDecimal);
        //时长费
        BigDecimal time = new BigDecimal(duration);
        //时长分钟数
        BigDecimal timeDecimal = time.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_DOWN);

        //每分钟的价格
        BigDecimal unitMinute = new BigDecimal(prRule.getUnitPricePerMinute());
        //时长总费用
        BigDecimal timeFare = timeDecimal.multiply(unitMinute);


        //总价格
        price = price.add(timeFare);

        return price.doubleValue();
    }


    /*public static void main(String[] args) {
        PriceRule price = new PriceRule();
        price.setUnitPricePerMile(1.8);
        price.setUnitPricePerMinute(0.5);
        price.setStartFare(10.0);
        price.setStartMile(3);
        System.out.printf(getPrice(6500,1800,price)+"");


    }*/




}
