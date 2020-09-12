package com.bjpowernode.p2p.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface IncomeRecordMapper {

    @Select(value="select li.product_name productName, ir.income_money incomeMoney,ir.income_date incomeDate from b_income_record ir inner join b_loan_info li on ir.loan_id=li.id" +
            " where ir.uid = #{uid} order by ir.income_date asc limit #{skipNum},#{getNum}")
    List<Map> selectIncomeRecordByUserId(Map paramsMap);

    @Select(value="select u.phone,ir.income_money incomeMoney,ir.income_date incomeDate " +
            "from b_income_record ir inner join u_user u  on ir.uid=u.id where" +
            " ir.loan_id=#{loanInfoId} order by ir.income_date desc limit 0,10")
    List<Map> selectIncomeRecordByLoanInfoId(Long loanInfoId);
}
