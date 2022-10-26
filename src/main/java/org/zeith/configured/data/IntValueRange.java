package org.zeith.configured.data;

import java.math.BigInteger;
import java.util.function.Predicate;

public class ValueRange
		implements Predicate<Number>
{
	protected BigInteger min, max;
	protected boolean minInclusive, maxInclusive;
	
	@Override
	public boolean test(Number number)
	{
		if(number instanceof BigInteger bint)
		{
			
		}
		return false;
	}
}