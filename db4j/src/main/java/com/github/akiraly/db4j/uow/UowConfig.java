package com.github.akiraly.db4j.uow;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableSet;

@Configuration
@Nonnull
public class UowConfig {
	@Bean
	public Set<Package> packagesToScan() {
		return ImmutableSet.of(Uow.class.getPackage());
	}

	@Bean
	public UowDaoFactory uowDaoFactory(EntityManagerFactory entityManagerFactory) {
		return new UowDaoFactory(entityManagerFactory);
	}
}
