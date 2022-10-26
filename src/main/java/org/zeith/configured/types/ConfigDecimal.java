package org.zeith.configured.types;

import org.zeith.configured.ConfigToken;
import org.zeith.configured.data.IntValueRange;
import org.zeith.configured.io.IoNewLiner;

import java.io.*;
import java.math.BigInteger;
import java.util.Optional;

public class ConfigInteger
		extends ConfigElement<ConfigInteger>
{
	private IntValueRange range;
	private BigInteger defaultValue;
	
	private BigInteger value;
	
	public ConfigInteger(Runnable onChanged, ConfigToken<ConfigInteger> token, String name)
	{
		super(onChanged, token, name);
		this.nameTerminator = c -> c == '=';
	}
	
	public ConfigInteger withRange(IntValueRange range)
	{
		this.range = range;
		return this;
	}
	
	public ConfigInteger withDefault(BigInteger value)
	{
		if(!range.test(value)) throw new IllegalArgumentException("Default value does not match the range " + range);
		this.defaultValue = value;
		return this;
	}
	
	public ConfigInteger withDefault(int value)
	{
		return withDefault(BigInteger.valueOf(value));
	}
	
	public ConfigInteger withDefault(long value)
	{
		return withDefault(BigInteger.valueOf(value));
	}
	
	public BigInteger getValue()
	{
		return value != null ? range.enclose(value) : defaultValue;
	}
	
	@Override
	public String getComment()
	{
		return Optional.ofNullable(super.getComment()).orElse("") + " (Range: " + range + ")";
	}
	
	@Override
	public boolean read(BufferedReader reader, int depth, String readerStack) throws IOException
	{
		if(reader.read() != '=') return false;
		
		reader.mark(1);
		StringBuilder digits = new StringBuilder();
		int r;
		while((r = reader.read()) >= 0)
		{
			reader.mark(1);
			
			if(r == '\r') continue;
			
			if(r == '\n')
			{
				this.value = new BigInteger(digits.toString());
				return true;
			}
			
			if((r >= '0' && r <= '9') || (digits.length() == 0 && r == '-'))
			{
				digits.append((char) r);
			} else
			{
				reader.reset();
				throw new IOException(new NumberFormatException("Unexpected character '" + ((char) r) + "' while reading " + readerStack + "; Accumulated: " + digits));
			}
		}
		
		return false;
	}
	
	@Override
	public void write(BufferedWriter writer, IoNewLiner newLiner) throws IOException
	{
		writer.write("=" + getValue());
	}
	
	@Override
	public String toString()
	{
		return "ConfigInteger{" +
				"defaultValue=" + defaultValue +
				", value=" + value +
				", name='" + name + '\'' +
				", comment='" + getEscapedComment() + '\'' +
				'}';
	}
}