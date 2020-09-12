package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.FinanceAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component(value ="financeAccountMapper")
public interface FinanceAccountMapper {

    @Select(value="select * from u_finance_account where uid=#{uid}")
    FinanceAccount selectFinanceAccountByUserId(Long id);


}
