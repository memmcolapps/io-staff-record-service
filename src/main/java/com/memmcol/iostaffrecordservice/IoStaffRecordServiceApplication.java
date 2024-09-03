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
