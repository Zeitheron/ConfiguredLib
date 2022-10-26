package org.zeith.configured.data;

import java.math.BigInteger;
import java.util.function.Predicate;

public class IntValueRange
		implements Predicate<Number>
{
	protected final BigInteger min, max;
	protected final boolean minInclusive, maxInclusive, eq;
	
	public IntValueRange(BigInteger min, BigInteger max, boolean minInclusive, boolean maxInclusive)
	{
		this.min = min;
		this.max = max;
		this.minInclusive = minInclusive;
		this.maxInclusive = maxInclusive;
		
		if(max.compareTo(min) < 0) throw new IllegalArgumentException("The min value of a range is greater than max value. Why?");
		
		var e = max.compareTo(min) == 0;
		this.eq = e && (minInclusive || maxInclusive);
		
		if(e && !test(min)) throw new IllegalArgumentException("The min value of a range is equal to max value, but is not included. Why?");
	}
	
	@Override
	public boolean test(Number number)
	{
		BigInteger big;
		if(number instanceof BigInteger bint) big = bint;
		else big = BigInteger.valueOf(number.longValue());
		
		if(eq && big.equals(min))
			return true;
		
		if(min != null && big.compareTo(min) < (minInclusive ? 0 : 1))
			return false;
		
		if(max != null && big.compareTo(max) > (maxInclusive ? 0 : 1))
			return false;
		
		return true;
	}
}