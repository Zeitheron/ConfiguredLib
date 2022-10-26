package org.zeith.configured;

import org.zeith.configured.io.IoNewLiner;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigObject<T extends ConfigObject<T>>
		extends ConfigElement<T>
{
	protected final Map<String, ConfigElement<?>> elements = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public <E extends ConfigElement<E>> E getElement(ConfigToken<E> token, String name)
	{
		if(elements.containsKey(name) && !token.is(elements.get(name)))
			elements.remove(name);
		return (E) elements.computeIfAbsent(name, token::create);
	}
	
	@Override
	public void read(BufferedReader reader, int depth) throws IOException
	{
		elements.clear();
	}
	
	@Override
	public void write(BufferedWriter writer, IoNewLiner newLiner) throws IOException
	{
	
	}
}