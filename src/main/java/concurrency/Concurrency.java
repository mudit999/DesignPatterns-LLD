package concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Concurrency {
    // Producers and Consumers

    // BAD: busy-waiting burns CPU
    while(true){
        if(!queue.isEmpty()){
            Task task = queue.poll();
            process(task);
        }
        // it will spin forever when queue is Empty
    }

    // ALSO BAD: sleep polling add latency
    while(true){
        if(!queue.isEmpty()){
            Task task = queue.poll();
            process(task);
        }else{
            Thread.sleep(100); // in worst case,
            // 100ms latency on every task
        }
    }

    // Bounded Queue - blocks when full (backpressure)
    BlockingQueue<Task> queue = new LinkedBlockingQueue<>(100);

    // Producer - API handler
    public void handleSignUp(User user){
        saveUser(user);
        queue.put(new EmailTask(user)); // blocks if queue is full
        return success();
    }

    // Consumer - worker thread
    while(true){
        Task task = queue.take(); // blocks if queue is empty, zero CPU
        process(task);
    }

    // When put comes up (new task), take gets trigger

}
