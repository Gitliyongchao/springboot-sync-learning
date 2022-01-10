package com.example.threadpooltest.controller;

import com.example.threadpooltest.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author ：yy
 * @date ：Created in 2021/12/28 18:16
 * @description：controller
 */
@Slf4j
@RestController
public class HelloController {
    @Autowired
    private AsyncService asyncService;

    //异步多线程调用方法，不用等方法返回结果
    @RequestMapping("/test")
    public String test(){
        log.info("start submit");

        //调用service层的任务
        asyncService.executeAsync();

        log.info("end submit");
        return "success";
    }

    //正常的单线程处理，要花4秒
    @RequestMapping("/sendmessage")
    public String sendMessage() throws ExecutionException, InterruptedException {
        System.out.println(new Date());

        //调用service层的任务
        String sendMessage1=asyncService.sendMessage1();
        String sendMessage2=asyncService.sendMessage2();

        String result="";
        result=sendMessage1+sendMessage2;

        System.out.println(new Date());
        return result;
    }

    //异步多线程调用，但是要等方法回调结果。用多线程，所以只需要2秒
    @RequestMapping("/sendMessageAsync")
    public String sendMessageAsync() throws ExecutionException, InterruptedException {
        System.out.println("开始时间："+new Date());
        Future<String> sendMessageAsync1 = asyncService.sendMessageAsync1();
        Future<String> sendMessageAsync2 = asyncService.sendMessageAsync2();

        String result="";
        String result1="";
        String result2="";

        //通过future.get()方法阻塞性获取执行结果，设置超时时间为3秒，3秒还没获取到值，就超时报错
//        try {
//            result1=sendMessageAsync1.get(3000, TimeUnit.MILLISECONDS);
//        } catch (TimeoutException e) {
//            sendMessageAsync1.cancel(true);
//            log.error("sendMessageAsync1方法超时未返回结果");
//            e.printStackTrace();
//        }
//
//        try {
//            result2=sendMessageAsync2.get(3000, TimeUnit.MILLISECONDS);
//        } catch (TimeoutException e) {
//            sendMessageAsync2.cancel(true);
//            log.error("sendMessageAsync2方法超时未返回结果");
//            e.printStackTrace();
//        }
//        result=result1+result2;

//        Future.get() 方法是一个阻塞方法。如果任务还没执行完毕，那么会一直阻塞直到直到任务完成
//        为了防止调用 Future.get() 方法阻塞当前线程，推荐的做法是先调用 Future.isDone() 判断任务是否完成，
//        然后再调用 Future.get() 从完成的任务中获取任务执行的结果
//        使用while循环判断两个方法的线程都有运行结果后（即isDone为true之后），再通过get方法立即获取回调结果值
//        因为 Future.isDone() 和 Future.get() 的存在，我们就可以在等待任务完成时运行其它一些代码
//        使用 isDone() 和 get() 方法来获取结果，这应该是消费 Future 最常见的方式
//        针对下面的代码, 如果不用isDone(),直接用get(), 那么get()阻塞的这2秒内就不能做任何其他事情。而用了while isDone(), 这2秒内
//        则可以做一些其他的事情，像下面的输出打印一段话。

        while(!(sendMessageAsync1.isDone() && sendMessageAsync2.isDone())){
//            System.out.println(
//                    String.format(
//                            "future1 is %s and future2 is %s",
//                            sendMessageAsync1.isDone() ? "done" : "not done",
//                            sendMessageAsync2.isDone() ? "done" : "not done"
//                    )
//            );
//            Thread.sleep(300);
        }

        result +=sendMessageAsync1.get();
        result +=sendMessageAsync2.get();

        System.out.println("结束时间："+new Date());
        return result;
    }

}
