package com.yu.learnRetry;

import com.yu.learnRetry.retry.jdkdynamicproxy.DynamicProxyRetry;
import com.yu.learnRetry.retry.sampleretry.SampleRetry;
import com.yu.learnRetry.service.InvokeRemoteMethodService;
import com.yu.learnRetry.service.InvokeRemoteMethodServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearnRetryApplicationTests {

    @Autowired
    private SampleRetry sampleRetry;

    @Test
    public void test_sampleRetry(){
        sampleRetry.invokeMethodAndRetry(3);
    }

    @Test
    public void test_jdkDynamicProxyRetry(){

        InvokeRemoteMethodServiceImpl InvokeRemoteMethodServiceImpl = new InvokeRemoteMethodServiceImpl();
        InvokeRemoteMethodService proxy = (InvokeRemoteMethodService) DynamicProxyRetry.getProxy(InvokeRemoteMethodServiceImpl);
        proxy.doInvokeAndThrowException();
    }



}
