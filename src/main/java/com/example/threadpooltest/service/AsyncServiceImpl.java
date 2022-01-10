package com.example.threadpooltest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author ：yy
 * @date ：Created in 2021/12/28 18:11
 * @description：实现类
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    //异步多线程调用
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        log.info("start executeAsync");
        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("end executeAsync");
    }

    //多线程调用并获取回调结果
    @Async("asyncServiceExecutor")
    public Future<String> sendMessageAsync1(){
        log.info("异步发送消息1---执行开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("异步发送消息1---执行结束");
        return new AsyncResult<>("异步发送消息1");
    }

    //多线程调用并获取回调结果
    @Async("asyncServiceExecutor")
    public Future<String> sendMessageAsync2(){
        log.info("异步发送消息2---执行开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("异步发送消息2---执行结束");
        return new AsyncResult<>("异步发送消息2");
    }


    public String sendMessage1(){
        log.info("发送消息1---执行开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送消息1---执行结束");
        return "发送消息1";
    }

    public String sendMessage2(){
        log.info("发送消息2---执行开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("发送消息2---执行结束");
        return "发送消息2";
    }


}
