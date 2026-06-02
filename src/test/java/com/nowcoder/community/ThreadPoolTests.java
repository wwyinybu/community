package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ThreadPoolTests {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTests.class);

    //JDK普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    //JDK可定时执行任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private void sleep(long m){
        try{
            Thread.sleep(m);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("hello,ExecutorService");
            }
        };
        for(int i=0;i<10;i++){
            executorService.submit(task);
            sleep(10000);
        }
    }

    @Test
    public void testSchedledExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("hello,ScheduledExecutorService");
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(task,1000,1000, TimeUnit.MILLISECONDS);
        sleep(30000);
    }

    @Test
    public void testThreadPoolTaskExecutor(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("hello,ThreadPoolTaskExecutor");
            }
        };
        for(int i=0;i<10;i++){
            threadPoolTaskExecutor.submit(task);
        }
        sleep(10000);
    }
}
