package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

@Nonnull
public class SimpleId<T, I> extends Id<T> {
	private final I id;

	public SimpleId(I id, Class<T> type) {
		super(type);
		this.id = argNotNull(id, "id");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleId<?, ?> other = (SimpleId<?, ?>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public I getId() {
		return id;
	}

	@Override
	public String toString() {
		return getType().getSimpleName() + "Id [" + id + "]";
	}
}
