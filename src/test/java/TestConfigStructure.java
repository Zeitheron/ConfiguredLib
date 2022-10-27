import org.zeith.libs.configured.io.UnsafeHax;
import org.zeith.libs.configured.struct.*;

import java.util.Arrays;

public class TestConfigStructure
{
	@Category("new category")
	@Comment("Test comment 2!")
	@Name("new category")
	public final Sub1 newCategory = new Sub1();
	
	@EntryString("test\n\tstring\n\\\tvalue")
	public String testString;
	
	public static class Sub1
	{
		@EntryString("reeee")
		@Name("0tes0t str")
		public String test2;
		
		@RangeLong(min = 6L)
		@Name("0tes0t int 2")
		@Comment("Test comment")
		public long testInt = 7;
		
		@RangeLong(min = 0L)
		@Name("array2")
		@Comment("Test comment")
		public Long[][] longArray = {
				{ 0L },
				{ 5L, 6L }
		};
		
		@Override
		public String toString()
		{
			return "Sub1{" +
					"test2='" + test2 + '\'' +
					", testInt=" + testInt +
					", longArray=" + UnsafeHax.toString(longArray) +
					'}';
		}
	}
	
	@Override
	public String toString()
	{
		return "TestConfigStructure{" +
				"newCategory=" + newCategory +
				", testString='" + testString + '\'' +
				'}';
	}
}