package org.shersfy.datahub.fs.client.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.shersfy.datahub.fs.client")
@SpringBootApplication
public class FsClientApplication {

	public static void main(String[] args) {
	    
		SpringApplication.run(FsClientApplication.class, args);
	}

}
