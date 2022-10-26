package org.zeith.configured;

import org.zeith.configured.io.IoNewLiner;
import org.zeith.configured.io.StringReader;

import java.io.*;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ConfigElement<T extends ConfigElement<T>>
{
	protected final ConfigToken<T> token;
	protected String name;
	protected String comment;
	
	public ConfigElement(ConfigToken<T> token, String name)
	{
		this.token = token;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public final ConfigToken<T> getToken()
	{
		return token;
	}
	
	public String getComment()
	{
		return this.comment;
	}
	
	public T withComment(String comment)
	{
		this.comment = comment;
		return (T) this;
	}
	
	protected Optional<String> readComment(BufferedReader reader, int depth) throws IOException
	{
		if(StringReader.skipWhitespaces(reader) != '/'
				|| reader.read() != '*')
		{
			reader.reset();
			return Optional.empty();
		}
		
		StringBuilder sb = new StringBuilder();
		boolean closed = false;
		
		boolean backspace = false;
		
		int r;
		while((r = reader.read()) >= 0)
		{
			if(r == '\\')
			{
				if(!backspace)
				{
					backspace = true;
				} else
				{
					sb.append((char) r);
					backspace = false;
				}
				continue;
			}
			
			if(r == '*' && backspace)
			{
				sb.append((char) r);
				continue;
			}
			
			if(r == '/' && sb.charAt(sb.length() - 1) == '*' && !backspace)
			{
				sb.deleteCharAt(sb.length() - 1);
				closed = true;
				break;
			}
			
			sb.append((char) r);
			backspace = false;
		}
		
		var str = sb.toString()
				.replace(System.lineSeparator(), "\n")
				.lines()
				.map(String::trim)
				.filter(s -> !s.isBlank())
				.collect(Collectors.joining("\n"));
		
		while(str.startsWith("\n")) str = str.substring(1);
		while(str.endsWith("\n")) str = str.substring(0, str.length() - 1);
		
		return !closed ? Optional.empty() : Optional.of(str.stripIndent());
	}
	
	public abstract boolean read(BufferedReader reader, int depth) throws IOException;
	
	public abstract void write(BufferedWriter writer, IoNewLiner newLiner) throws IOException;
}