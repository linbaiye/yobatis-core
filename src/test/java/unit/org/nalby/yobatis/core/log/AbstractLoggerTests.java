package unit.org.nalby.yobatis.core.log;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.nalby.yobatis.core.log.AbstractLogger;
import org.nalby.yobatis.core.log.LoggerFactory;

public class AbstractLoggerTests {
	
	public static class TestLogger extends AbstractLogger {
		private String logLine;
		public TestLogger(String className) {
			super(className);
		}
		@Override
		protected void wirteToConsole(String msg) {
			logLine = msg;
		}
		public String getLogLine() {
			return logLine;
		}
	}
	private TestLogger logger;
	
	@Before
	public void setup() {
		LoggerFactory.setLogger(TestLogger.class);
		logger = (TestLogger) LoggerFactory.getLogger(TestLogger.class);
	}
	
	
	@Test
	public void formatStrings() {
		logger.info("{}{}", "hello", "world");
		assertTrue(logger.getLogLine().contains("helloworld"));
		assertTrue(logger.getLogLine().contains("INFO"));
		assertTrue(logger.getLogLine().endsWith("\n"));
	}
	
	@Test
	public void voidArg() {
		Object[] args = null;
		logger.info("{}", args);
		assertTrue(logger.getLogLine().contains("{}"));
		args = new Object[0];
		logger.info("{}", args);
		assertTrue(logger.getLogLine().contains("{}"));
	}

	@Test
	public void formatToString() {
		logger.info("{}{}", 1, 1.2);
		assertTrue(logger.getLogLine().contains("11.2"));
		assertTrue(logger.getLogLine().contains("INFO"));
	}
	
	@Test
	public void formatException() {
		try {
			throw new IllegalArgumentException("TestException.");
		} catch (Exception e) {
			logger.error("{}", e);
		}
		assertTrue(logger.logLine.contains("TestException"));
		assertTrue(logger.getLogLine().contains("ERROR"));
		assertTrue(logger.getLogLine().endsWith("\n"));
	}
	
	@Test
	public void mismatchedArgs() {
		logger.info("{}{}", "world");
		assertTrue(logger.getLogLine().contains("world{}"));

		logger.info("{}", "world", "hello");
		assertTrue(logger.getLogLine().contains("world"));
		assertTrue(!logger.getLogLine().contains("hello"));
	}
	
	@Test
	public void nullFormat() {
		logger.info(null);
		assertTrue(logger.getLogLine().contains("INFO"));
	}
	
	@Test
	@SuppressWarnings("static-access")
	public void debugLog() {
		logger.debug("test");
		assertNull(logger.getLogLine());
		logger.defaultLevel = AbstractLogger.LogLevel.DEBUG;
		logger.debug("test");
		assertTrue(logger.getLogLine().contains("test"));
	}
	

}
