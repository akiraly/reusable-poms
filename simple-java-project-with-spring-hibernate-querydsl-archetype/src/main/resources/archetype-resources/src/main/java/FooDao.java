package ${package};

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import com.google.common.base.Optional;

import foo.bar.baz.entity.Bar;
import foo.bar.baz.entity.Foo;

@Nonnull
public class FooDao {
	private final QueryDslJpaRepository<Foo, Long> repository;

	private final Bar bar;

	public FooDao(QueryDslJpaRepository<Foo, Long> repository, Bar bar) {
		this.repository = checkNotNull(repository,
				"Expected not null repository.");
		this.bar = checkNotNull(bar, "Expected not null bar.");
	}

	public Foo persist(Foo entity) {
		entity.setBar(bar);
		return repository
				.save(checkNotNull(entity, "Expected not null entity."));
	}

	public Optional<Foo> byId(long id) {
		return Optional.fromNullable(repository.findOne(id));
	}
}
