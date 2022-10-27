package org.zeith.libs.configured.struct.mappers;

import org.zeith.libs.configured.ConfigToken;
import org.zeith.libs.configured.ConfiguredLib;
import org.zeith.libs.configured.struct.EntryString;
import org.zeith.libs.configured.struct.reflection.IField;
import org.zeith.libs.configured.types.ConfigString;

import java.util.Optional;

public class StringMapper
		implements ITokenMapper<ConfigString, String>
{
	@Override
	public Class<String> getType()
	{
		return String.class;
	}
	
	@Override
	public ConfigToken<ConfigString> getToken()
	{
		return ConfiguredLib.STRING;
	}
	
	@Override
	public String apply(ConfigString element)
	{
		return element.getValue();
	}
	
	@Override
	public void defaultValue(ConfigString element, IField<?> ownerField, String defaultValue)
	{
		defaultValue = Optional.ofNullable(ownerField)
				.flatMap(f -> f.annotation(EntryString.class))
				.map(EntryString::value)
				.orElse(defaultValue);
		
		if(defaultValue != null)
			element.withDefault(defaultValue);
	}
}
