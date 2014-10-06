package ${package};

import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableSet;

@Configuration
@Nonnull
public class FooConfig {
	@Bean
	public Set<Package> packagesToScan() {
		return ImmutableSet.of(Foo.class.getPackage());
	}

	@Bean
	public FooDaoFactory fooDaoFactory(EntityManagerFactory entityManagerFactory) {
		return new FooDaoFactory(entityManagerFactory);
	}
}