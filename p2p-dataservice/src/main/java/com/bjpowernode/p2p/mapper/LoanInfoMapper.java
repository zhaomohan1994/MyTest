package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.LoanInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value="loanInfoMapper")
public interface LoanInfoMapper {

    @Select(value="select * from b_loan_info order by release_time desc limit #{skipNum} ,#{getNum}")
    List<LoanInfo> selectLoanInfo(Map params);

    @Select(value="select count(id) from b_loan_info")
    long countLoan();

    @Select(value="select * from  b_loan_info where id=#{loanInfoId}")
    LoanInfo selectLoanInfoById(Long loanInfoId);
}
