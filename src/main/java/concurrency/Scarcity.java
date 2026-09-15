package concurrency;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

public class Scarcity {
    Semaphore permits = new Semaphore(5); // max 5 concurrent

    public void download(String url) throws InterruptedException {
        permits.acquire(); // grab a permit (or block if none available)
        try {
            doDownload(url);
        } finally {
            permits.release(); // always run, even if exception is thrown
        }
    }

    // Different case
    public void DBConnection() {
        // Initializing pool with connections
        BlockingQueue<Connection> pool = new LinkedBlockingDeque<>(100);

        for (int i = 0; i < 10; i++) {
            pool.put(createConnection());
        }

        // Usage
        public Result query(String sql){
            Connection conn = pool.take(); // blocks if pool is empty
            try{
                return conn.exceute(sql);
            } finally {
                pool.put(conn); // same rule: Always return in finally
            }
        }
    }
}
