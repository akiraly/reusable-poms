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
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.github.akiraly.db4j.converter.Convert;
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
		return Convert.date2OptUtcDateTime(dt);
	}

	public void setDt(Optional<DateTime> dt) {
		this.dt = Convert.dateTime2Date(dt);
	}

	@Override
	public String toString() {
		return "Foo [getBar()=" + getBar() + ", getName()=" + getName()
				+ ", getDt()=" + getDt() + ", toString()=" + super.toString()
				+ "]";
	}
}
