package ${package};

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.github.akiraly.db4j.converter.Convert;

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
	private Calendar dt = Calendar.getInstance(
			TimeZone.getTimeZone(ZoneOffset.UTC), Locale.US);

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

	public Optional<OffsetDateTime> getDt() {
		return Convert.calendar2OptDateTime(dt);
	}

	public void setDt(Optional<OffsetDateTime> dt) {
		this.dt = Convert.dateTime2Calendar(dt);
	}

	@Override
	public String toString() {
		return "Foo [getBar()=" + getBar() + ", getName()=" + getName()
				+ ", getDt()=" + getDt() + ", toString()=" + super.toString()
				+ "]";
	}
}
