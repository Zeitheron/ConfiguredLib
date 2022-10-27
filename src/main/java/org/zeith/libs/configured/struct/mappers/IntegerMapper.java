package org.zeith.libs.configured.struct.mappers;

import org.zeith.libs.configured.ConfigToken;
import org.zeith.libs.configured.ConfiguredLib;
import org.zeith.libs.configured.data.IntValueRange;
import org.zeith.libs.configured.struct.RangeInt;
import org.zeith.libs.configured.struct.RangeLong;
import org.zeith.libs.configured.struct.reflection.IField;
import org.zeith.libs.configured.types.ConfigInteger;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

public class IntegerMapper<N extends Number>
		implements ITokenMapper<ConfigInteger, N>
{
	protected final Class<N> type;
	protected final Function<BigInteger, N> converter;
	
	public IntegerMapper(Class<N> type, Function<BigInteger, N> converter)
	{
		this.type = type;
		this.converter = converter;
	}
	
	@Override
	public Class<N> getType()
	{
		return type;
	}
	
	@Override
	public ConfigToken<ConfigInteger> getToken()
	{
		return ConfiguredLib.INT;
	}
	
	@Override
	public N apply(ConfigInteger element)
	{
		return Optional.ofNullable(element.getValue()).map(converter).orElse(null);
	}
	
	@Override
	public void defaultValue(ConfigInteger element, IField<?> ownerField, N defaultValue)
	{
		Optional.ofNullable(ownerField)
				.flatMap(f -> f.annotation(RangeInt.class).map(IntValueRange::fromIntRange)
						.or(() -> f.annotation(RangeLong.class).map(IntValueRange::fromLongRange))
				).ifPresent(element::withRange);
		
		if(defaultValue != null)
		{
			if(defaultValue instanceof BigInteger bint) element.withDefault(bint);
			else element.withDefault(BigInteger.valueOf(defaultValue.longValue()));
		}
	}
}