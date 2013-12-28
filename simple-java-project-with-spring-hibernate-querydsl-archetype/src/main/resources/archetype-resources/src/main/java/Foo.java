package ${package};

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Nonnull
@Entity
public class Foo extends AbstractPersistable<Long> {
	private static final long serialVersionUID = -5858125293405585370L;

	@ManyToOne(cascade = { CascadeType.PERSIST }, optional = false)
	@Nullable
	private Bar bar;

	@Nullable
	private String name;

	public Bar getBar() {
		return bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Foo [bar=" + bar + ", name=" + name + ", toString()=" + super.toString() + "]";
	}
}
