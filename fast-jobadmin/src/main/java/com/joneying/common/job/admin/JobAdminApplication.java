package com.joneying.common.job.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 *@Title
 *@Description 程序入口
 *@author Yingjianghua
 *@date 10:11 2018/12/12
 *@param
 *@return
 */
@ServletComponentScan
@SpringBootApplication
public class JobAdminApplication {

	public static void main(String[] args) {
        SpringApplication.run(JobAdminApplication.class, args);
	}

}