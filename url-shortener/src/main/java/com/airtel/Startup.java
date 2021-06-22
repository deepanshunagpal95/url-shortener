package com.airtel;


import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Import;
import com.airtel.online.boot.exception.config.ExceptionConfig;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@Import(value = {ExceptionConfig.class ,LoggerConfig.class})
@RefreshScope
public class Startup {

	public static void main(String[] args) {
		//System.setProperty("http.nonProxyHosts","10.5.251.235|*.local|10.*|10.*.*|*.airtel.itm|asierp.airtel.africa|*.webmail.airtel.com|www.aesdashboard*.*|mn.bharti.com|crux.nxtradata.com|*.airtel.com|*.airtelworld.in|*.airtelworld.in|influx.airtel.in");
        System.setProperty("isThreadContextMapInheritable", "true");
		SpringApplication.run(Startup.class, args);
	}

}
