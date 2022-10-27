package org.zeith.libs.configured.io;

import java.io.IOException;

@FunctionalInterface
public interface IoFunction<K, R>
{
	R apply(K key) throws IOException;
}