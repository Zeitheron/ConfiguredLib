package org.zeith.libs.configured.struct.mappers;

import org.zeith.libs.configured.ConfigToken;
import org.zeith.libs.configured.ConfiguredLib;
import org.zeith.libs.configured.data.DecimalValueRange;
import org.zeith.libs.configured.struct.RangeDouble;
import org.zeith.libs.configured.struct.RangeFloat;
import org.zeith.libs.configured.struct.reflection.IField;
import org.zeith.libs.configured.types.ConfigDecimal;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class DecimalMapper<N extends Number>
		implements ITokenMapper<ConfigDecimal, N>
{
	protected final Class<N> type;
	protected final Function<BigDecimal, N> converter;
	
	public DecimalMapper(Class<N> type, Function<BigDecimal, N> converter)
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
	public ConfigToken<ConfigDecimal> getToken()
	{
		return ConfiguredLib.DECIMAL;
	}
	
	@Override
	public N apply(ConfigDecimal element)
	{
		return Optional.ofNullable(element.getValue()).map(converter).orElse(null);
	}
	
	@Override
	public void defaultValue(ConfigDecimal element, IField<?> ownerField, N defaultValue)
	{
		Optional.ofNullable(ownerField)
				.flatMap(f -> f.annotation(RangeFloat.class).map(DecimalValueRange::fromFloatRange)
						.or(() -> f.annotation(RangeDouble.class).map(DecimalValueRange::fromDoubleRange))
				).ifPresent(element::withRange);
		
		if(defaultValue != null)
		{
			if(defaultValue instanceof BigDecimal bdec) element.withDefault(bdec);
			else element.withDefault(BigDecimal.valueOf(defaultValue.doubleValue()));
		}
	}
}