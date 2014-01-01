package $package;

import javax.annotation.Nonnull;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

@RestController
@Nonnull
@RequestMapping("/foo")
public class FooController {
	private final ImmutableMap<Long, Foo> foos;

	public FooController() {
		Builder<Long, Foo> builder = ImmutableMap.<Long, Foo> builder();
		Bar bar = new Bar(1L);
		for (long fi = 0; fi < 5; fi++)
			builder.put(fi, new Foo(fi, "foo" + fi, bar));
		foos = builder.build();
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ImmutableMap<Long, Foo> foos() {
		return foos;
	}
}
