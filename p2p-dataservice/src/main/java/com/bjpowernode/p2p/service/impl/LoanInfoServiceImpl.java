package com.bjpowernode.p2p.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.IncomeRecordMapper;
import com.bjpowernode.p2p.mapper.LoanInfoMapper;
import com.bjpowernode.p2p.model.LoanInfo;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = LoanInfoService.class)
@Component(value="loanInfoService")
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private IncomeRecordMapper incomeRecordMapper;

    @Override
    public Map loanList(Long pageNumber) {
        Map params = new HashMap();

        //分页查询首先做数据统计
        long countNum = loanInfoMapper.countLoan();

        //定义每页显示的记录数量这个值应该定义在配置文件中
        Long pageCount=9L;

        //计算总页数
        Long pageSize = countNum/pageCount;

        //总记录数除以每页显示的数量，如果余数大于0表示不能整除，因此总页数需要+1
        if(countNum%pageCount!=0){
            pageSize++;
        }

        params.put("skipNum",(pageNumber-1)*pageCount);
        params.put("getNum",pageCount);

        List<LoanInfo> list= loanInfoMapper.selectLoanInfo(params);

        //返回数据信息
        Map dataMap = new HashMap();
        dataMap.put("pageNumber",pageNumber);
        dataMap.put("countNum",countNum);
        dataMap.put("pageSize",pageSize);
        dataMap.put("loanInfoList",list);

        return dataMap;
    }

    @Override
    public Map loanInfo(Long loanInfoId) {

        //获取产品对象
        LoanInfo loanInfo = loanInfoMapper.selectLoanInfoById(loanInfoId);

        //获取投资记录

        List<Map> incomeRecordList =incomeRecordMapper.selectIncomeRecordByLoanInfoId(loanInfoId);

        Map dataMap = new HashMap();
        dataMap.put("loanInfo",loanInfo);
        dataMap.put("incomeRecordList",incomeRecordList);

        return dataMap;
    }
}
