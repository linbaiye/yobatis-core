package unit.org.nalby.yobatis.core.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.nalby.yobatis.core.util.TextUtil;

import java.io.ByteArrayInputStream;

public class TextUtilTests {
	
	@Test
	public void isEmpty() {
		assertTrue(TextUtil.isEmpty(null));
		assertTrue(TextUtil.isEmpty(" "));
		assertTrue(TextUtil.isEmpty("    "));
		assertFalse(TextUtil.isEmpty("  x  "));
	}


	@Test
	public void capFirstChar() {
		assertEquals(null, TextUtil.capitalizeFirstChar(null));
		assertEquals(" ", TextUtil.capitalizeFirstChar(" "));
		assertEquals("", TextUtil.capitalizeFirstChar(""));
		assertEquals("A", TextUtil.capitalizeFirstChar("a"));
		assertEquals("A", TextUtil.capitalizeFirstChar("A"));
		assertEquals("Aa", TextUtil.capitalizeFirstChar("aa"));
		assertEquals("AA", TextUtil.capitalizeFirstChar("AA"));
	}


	@Test
	public void asString() {
		String tmp = "hello world";
		String str = TextUtil.asString(new ByteArrayInputStream(tmp.getBytes()));
		assertEquals(tmp, str);
	}
}
