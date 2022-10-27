package org.zeith.libs.configured.io;

import java.io.IOException;

public interface IoConsumer<T>
{
	void accept(T thing) throws IOException;
}