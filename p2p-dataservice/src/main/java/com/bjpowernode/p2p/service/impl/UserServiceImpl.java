package com.bjpowernode.p2p.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.p2p.mapper.*;
import com.bjpowernode.p2p.model.FinanceAccount;
import com.bjpowernode.p2p.model.RechargeRecord;
import com.bjpowernode.p2p.model.User;
import com.bjpowernode.p2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass =  UserService.class)
@Component("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FinanceAccountMapper financeAccountMapper;
    @Autowired
    private BidInfoMapper bidInfoMapper;
    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;
    @Autowired
    private IncomeRecordMapper incomeRecordMapper;

    //登陆方法
    @Override
    public User login(String phone, String loginPassword) {

        System.out.println("查询SQL");
        User user = userMapper.selectUserByPhoneAndPassowrd(phone,loginPassword);

        if(user==null){

            return null;
        }
        //修改用户对象最后一次登陆时间
//        user.setLastLoginTime(new Date());
//        userMapper.updateLastLoginTime(user);
        return user;
    }
    //检查手机号
    @Override
    public int checkPhone(String phone) {

        int result = userMapper.checkUserByPhone(phone);

        return result;
    }
    //注册方法。已使用 1；成功0
    @Override
    public int register(User user) {
        int result = userMapper.checkUserByPhone(user.getPhone());
        //表示手机号已经存在
        if(result>0){
            return 1;
        }
        userMapper.insertUser(user);
        return 0;
    }



    public static void main(String [] args){
        UserServiceImpl userService = new UserServiceImpl();
        User user = new User();
        userService.realName(user);

    }
    //实名认证
    @Override
    public int realName(User user) {
        String strUrl="https://www.baidu.com";
        //调用实名验证方法，私有方法，方便关闭
//      String resultJson = realName(strUrl);
        String resultJson = "{\"name\":\"张三\",\"sex\":\"男\",\"age\":25}";


        //使用fastjson将使命认证的json字符串转换为HashMap集合
        HashMap<String,Object> resultMap = JSONObject.parseObject(resultJson,HashMap.class);
        System.out.println(resultMap.get("name"));
        if(!((Boolean) resultMap.get("success"))){
            return 1;
        }
        userMapper.updateUserRealName(user);
        return 0;
    }

    //账户信息主界面方法
    @Override
    public Map getMyCenter(User user) {

        //用于存储返回结果
        Map resultData = new HashMap();

        //获取可用余额
        System.out.println(user.getId());
        FinanceAccount financeAccountinance = financeAccountMapper.selectFinanceAccountByUserId(user.getId());

        //获取最近投资记录
        Map paramMap = new HashMap();
        paramMap.put("uid",user.getId());
        paramMap.put("skipNum",0);
        paramMap.put("getNum",5);
        List<Map>  bidInfoList =bidInfoMapper.selectBidInfoByUserId(paramMap);
        System.out.println(bidInfoList);

        //最近充值记录
        List<RechargeRecord> rechargeRecordList = rechargeRecordMapper.selectRechargeRecordByUserId(paramMap);
        for(RechargeRecord rr:rechargeRecordList){
            System.out.println(rr);
        }


        //最近收益
        List<Map> incomeRecordList= incomeRecordMapper.selectIncomeRecordByUserId(paramMap);
        System.out.println(incomeRecordList);



        resultData.put("financeAccountinance",financeAccountinance);
        resultData.put("bidInfoList",bidInfoList);
        resultData.put("rechargeRecordList",rechargeRecordList);
        resultData.put("incomeRecordList",incomeRecordList);

        return resultData;
    }
    //实名认证主方法
    private String realName(String strUrl){

        HttpURLConnection connection= null;
        InputStream is =null;
        BufferedReader br =null;
        try {
            //创建一个地址类
            URL url =new URL(strUrl);
            //打开连接
            connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            is =connection.getInputStream();
            br =new BufferedReader(new InputStreamReader(is));

            String temp = "";
            StringBuilder strJson = new StringBuilder("");
            while((temp =br.readLine())!=null){
                strJson.append(temp);
            }
            System.out.println(strJson);

            return strJson.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  "";
    }


}
