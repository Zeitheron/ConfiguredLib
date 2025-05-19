import org.junit.jupiter.api.Test;
import org.zeith.libs.configured.ConfiguredLib;
import org.zeith.libs.configured.data.DecimalValueRange;
import org.zeith.libs.configured.data.IntValueRange;
import org.zeith.libs.configured.io.buf.NioByteBuf;
import org.zeith.libs.configured.io.buf.WritingBuf;
import org.zeith.libs.configured.struct.ConfigStructure;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class TestConfigs
{
	@Test
	public void main()
			throws IOException
	{
		var cfg = ConfiguredLib.create(new File("DEMO CONFIG FILE.cfg"), false);
		
		System.out.println("Errors: " + cfg.load());
		
		System.out.println(cfg);
		
		System.out.println("new category.0tes0t = " + cfg.setupCategory("new category")
				.withComment("Test comment 2!")
				.getElement(ConfiguredLib.BOOLEAN, "0tes0t")
				.withDefault(true)
				.withComment("Test boolean")
				.getValue());
		
		System.out.println("new category.0tes0t str = " + cfg.setupCategory("new category")
				.getElement(ConfiguredLib.STRING, "0tes0t str")
				.withDefault("test\n\tstring\n\\\tvalue")
				.withComment("Test string")
				.getValue());
		
		System.out.println("new category.0tes0t int = " + cfg.setupCategory("new category")
				.getElement(ConfiguredLib.INT, "0tes0t int")
				.withRange(IntValueRange.minClosed(BigInteger.valueOf(5)))
				.withDefault(4963467349067309463L)
				.withComment("Test comment integer!")
				.getValue());
		
		System.out.println("new category.0tes0t dec = " + cfg.setupCategory("new category")
				.getElement(ConfiguredLib.DECIMAL, "0tes0t dec")
				.withRange(DecimalValueRange.min(BigDecimal.valueOf(5.5)))
				.withDefault(5.6)
				.withComment("Test comment decimal!")
				.getValue());
		
		if(cfg.hasChanged())
		{
			cfg.save();
			System.out.println("Changes detected, saved.");
		}
		
		var pth = new File("DEMO CONFIG FILE.bin").toPath();
		Files.write(pth, WritingBuf.encode(cfg::toBuffer));
		
		System.out.println("Wrote configs to binary file. Reading...");
//			cfg = ConfiguredLib.createInMemory(new NioByteBuf(ByteBuffer.wrap(Files.readAllBytes(pth))));
		
		System.out.println(cfg);
		
		var struct = ConfigStructure.createConfig(TestConfigStructure::new, cfg, true);
		System.out.println(struct);
		
		if(cfg.hasChanged())
			cfg.save();
	}
}