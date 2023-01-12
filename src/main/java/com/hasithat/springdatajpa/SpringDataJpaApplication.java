package com.hasithat.springdatajpa;

import com.hasithat.springdatajpa.common.AuditorAwareimpl;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//Below annotation  is introducing our AuditorAware bean
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//Below annotation is also related to maintaining revision
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
/*
* Enabling jasypt  encryption/decryption
* */
@EnableEncryptableProperties
public class SpringDataJpaApplication {

	@Bean
	public AuditorAware<String> auditorAware(){
		return new AuditorAwareimpl();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

}
