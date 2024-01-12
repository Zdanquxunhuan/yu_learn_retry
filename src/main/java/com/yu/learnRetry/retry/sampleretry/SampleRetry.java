package com.yu.learnRetry.retry.sampleretry;

import com.yu.learnRetry.service.InvokeRemoteMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简单重试
 */
@Service
public class SampleRetry {

    @Autowired
    private InvokeRemoteMethodService invokeRemoteMethodService;

    public void invokeMethodAndRetry(int retryTimes) {

        int invokeTimes = 0;
        while (invokeTimes < retryTimes) {
            try {
                invokeRemoteMethodService.doInvokeAndThrowException();
            } catch (Exception e) {
                invokeTimes++;
                System.out.println("进行第" + invokeTimes + "次重试");
                if (invokeTimes >= retryTimes) {
                    //记录落库，后续定时任务兜底重试
                    //do something record...
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
