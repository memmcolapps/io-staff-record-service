package com.memmcol.iostaffrecordservice;

import com.memmcol.iostaffrecordservice.service.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
public class IoStaffRecordServiceApplication implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(IoStaffRecordServiceApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws InterruptedException {
    	 new Thread(() -> {
			try {
				nettyServer.startServer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
    }
    
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        nettyServer.stopServer();
    }
}


//3F F3 00 00 00 00 00 00 00 00 00 01 18 18 01 01 01 37 AA EE EE 00 02 2A B6 24 07 25 05 15 42 00 00 00 00 00 00 16 26 00 03 00 01 00 03 00 00 15 2C 33 38 30 30 34 32 32 38 45 45 42 43 FF FF FF FF FF FF FF FF FF FF FF FF A6 4D 21 12
//3F F3 00 00 00 00 00 00 00 00 00 01 18 18 02 01 01 15 00 01 33 38 30 30 34 32 32 38 45 45 42 43 24 07 25 05 15 41 17 E7 31 21 12
//3F F3 00 00 00 00 00 00 00 00 00 01 18 18 03 01 01 1E 00 01 01 24 07 25 05 15 42 17 00 01 02 24 07 25 05 15 42 40 00 01 01 24 08 13 03 10 39 35 DB 0B 21 12