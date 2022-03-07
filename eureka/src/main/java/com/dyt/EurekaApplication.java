package com.dyt;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;




@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(EurekaApplication.class, args);
//	}

	private static final Logger LOG = (Logger) LoggerFactory.getLogger(EurekaApplication.class);

	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(EurekaApplication.class);
		Environment env = (Environment) app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("Eureka地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));



	}




}
