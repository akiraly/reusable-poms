package ${package};

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.persistence.EntityManagerFactory;

import com.github.akiraly.db4j.AbstractDaoFactory;
import com.github.akiraly.db4j.EntityInformation;

@Nonnull
public class FooDaoFactory extends AbstractDaoFactory<Long, Foo, FooDao>
		implements Supplier<FooDao> {

	public FooDaoFactory(EntityManagerFactory entityManagerFactory) {
		super(FooDao.class, new EntityInformation<>(Long.class, Foo.class),
				entityManagerFactory);
	}

	@Override
	public FooDao get() {
		return new FooDao(entityManagerOrFail(), entityInformation(),
				newRepository());
	}
}
