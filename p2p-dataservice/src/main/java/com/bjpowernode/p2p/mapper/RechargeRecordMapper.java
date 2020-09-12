package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface RechargeRecordMapper {
    @Select(value="select * from b_recharge_record where uid = #{uid} order by  rechargeTime desc limit #{skipNum},#{getNum};")
    List<RechargeRecord> selectRechargeRecordByUserId(Map paramsMap);
}
