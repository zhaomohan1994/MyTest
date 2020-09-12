package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.BidInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface BidInfoMapper {


    @Select(value="select a.bid_money bid_money,a.bid_time bid_time,b.product_name product_name from b_bid_info a inner join b_loan_info b " +
            "on a.loan_id=b.id where uid=#{uid} order by bid_time desc limit #{skipNum},#{getNum} ")
    List<Map> selectBidInfoByUserId(Map paramMap);




    @Select(value="select bi.*,li.cycle,li.rate from b_bid_info bi left join b_income_record ir on bi.id=ir.bid_id inner join b_loan_info li on bi.loan_id=li.id where ir.id is null")
    List<HashMap> selectBidInfoNotInIncomeRecord();
}
