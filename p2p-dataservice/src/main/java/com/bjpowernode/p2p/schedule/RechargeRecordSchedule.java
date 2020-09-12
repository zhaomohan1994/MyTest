package com.bjpowernode.p2p.schedule;

import com.bjpowernode.p2p.mapper.BidInfoMapper;
import com.bjpowernode.p2p.model.BidInfo;
import com.bjpowernode.p2p.model.IncomeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Configuration//标记当前类是Spring的一个配置类
@EnableScheduling//让当前类开启定时任务
public class RechargeRecordSchedule {

    @Autowired
    private BidInfoMapper bidInfoMapper;

    //任意年任意月任意天任意小时任意分钟每五秒执行一次
    //当前定时任务是每5秒查询一次数据库将数据库中新添加的投资记录插入到收益表中
    @Scheduled(cron = "0/5 * * * * *")
    public void addRechargeRecord(){



    List<HashMap>  bidInfoList = bidInfoMapper.selectBidInfoNotInIncomeRecord();

    for(HashMap map:bidInfoList){
        IncomeRecord  incomeRecord = new IncomeRecord();
        incomeRecord.setBidId((Long) map.get("id"));
        incomeRecord.setBidMoney((Double) map.get("bid_money"));
        incomeRecord.setLoanId((Long) map.get("loan_id"));
        incomeRecord.setIncomeStatus(0L);
        incomeRecord.setUid((Long) map.get("uid"));
        Date bidTime=(Date)map.get("bid_time");
        //创建一个日历对象
        Calendar incomeRecordTime =Calendar.getInstance();
        //将Date对象转化为日历对象
        incomeRecordTime.setTime(bidTime);
        incomeRecordTime.add(Calendar.MONTH,(Integer) map.get("cycle"));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("投资时间"+sdf.format(bidTime));
        //将日历时间转化为Date时间
        incomeRecord.setIncomeDate(incomeRecordTime.getTime());
    }


    System.out.println("启动定时任务");
    }
}
