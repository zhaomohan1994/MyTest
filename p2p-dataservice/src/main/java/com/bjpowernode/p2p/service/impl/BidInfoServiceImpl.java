package com.bjpowernode.p2p.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.service.BidInfoService;
import org.springframework.stereotype.Component;


@Service(interfaceClass = BidInfoService.class)
@Component(value="bidInfoService")
public class BidInfoServiceImpl implements BidInfoService {

}
