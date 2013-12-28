package ${package};

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.google.common.base.Optional;

@Nonnull
@Entity
public class Foo extends AbstractPersistable<Long> {
	private static final long serialVersionUID = -5858125293405585370L;

	@ManyToOne(cascade = { CascadeType.PERSIST }, optional = false)
	@Nullable
	private Bar bar;

	@Nullable
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dt = new Date();

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

	public Optional<DateTime> getDt() {
		return dt != null ? Optional.of(new DateTime(dt, DateTimeZone.UTC))
				: null;
	}

	public void setDt(Optional<DateTime> dt) {
		this.dt = dt.isPresent() ? dt.get().toDate() : null;
	}

	@Override
	public String toString() {
		return "Foo [getBar()=" + getBar() + ", getName()=" + getName()
				+ ", getDt()=" + getDt() + ", toString()=" + super.toString()
				+ "]";
	}
}
