package org.zeith.configured.types;

import org.zeith.configured.ConfigElement;

import java.util.function.Supplier;

public class ConfigToken<E extends ConfigElement<E>>
{
	private final Class<E> type;
	private final Supplier<E> factory;
	
	public ConfigToken(Class<E> type, Supplier<E> factory)
	{
		this.type = type;
		this.factory = factory;
	}
	
	public boolean is(ConfigElement<?> elem)
	{
		return type.isInstance(elem);
	}
	
	public Class<E> getType()
	{
		return type;
	}
	
	public E create()
	{
		return factory.get();
	}
	
	public E create(String name)
	{
		return factory.get();
	}
}