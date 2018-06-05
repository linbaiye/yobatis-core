package org.nalby.yobatis.core.structure;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TokenSimilarityMatcherTests {
	
	private class StringMatcher extends TokenSimilarityMatcher<String> {
		public StringMatcher(Set<String> tokens) {
			setTokens(tokens);
		}

		@Override
		protected String[] tokenize(String t) {
			return t.split("\\.");
		}
	}

	private StringMatcher stringMatcher;

	private Set<String> tokens;
	
	@Before
	public void setup() {
		tokens = new HashSet<>();
		tokens.add("sys");
		tokens.add("func");
		tokens.add("model");
		stringMatcher = new StringMatcher(tokens);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyTokens() {
		try {
			tokens.clear();
			stringMatcher = new StringMatcher(tokens);
			fail();
		} catch (IllegalArgumentException e) {
			//expected.
		}
		new StringMatcher(null);
	}
	
	
	@Test
	public void noCalculating() {
		assertNull(stringMatcher.findMostMatchingOne());
	}
	

	@Test
	public void withoutMatchedToken() {
		stringMatcher.calculateSimilarity("hello");
		assertTrue("hello".equals(stringMatcher.findMostMatchingOne()));
	}
	
	@Test
	public void mostMatchingTokens() {
		stringMatcher.calculateSimilarity("func.dao");
		stringMatcher.calculateSimilarity("func.sys.dao");
		assertTrue("func.sys.dao".equals(stringMatcher.findMostMatchingOne()));
		assertTrue(stringMatcher.getScore("func.dao") == 1);
		assertTrue(stringMatcher.getScore("func.sys.dao") == 2);
	}
}
