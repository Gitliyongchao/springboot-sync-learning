package com.example.threadpooltest.service;

import java.util.concurrent.Future;

public interface AsyncService {
    /**
     * 执行异步任务
     **/
    void executeAsync();
    Future<String> sendMessageAsync1();
    Future<String> sendMessageAsync2();
    String sendMessage1();
    String sendMessage2();
}
