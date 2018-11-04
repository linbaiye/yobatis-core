package unit.org.nalby.yobatis.core.database;

import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.database.Table;

import static org.junit.Assert.assertEquals;

public class TableTests {
	
	private Table table;
	
	@Before
	public void setup() {
		table = new Table("test");
	}

	@Test
    public void all() {
		assertEquals("test", table.getName());
	}


}
