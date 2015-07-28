package uk.co.icfuture.mvc.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import uk.co.icfuture.mvc.model.Meta;

public class HelperTest {

	@Test
	public void mergeList() {
		ArrayList<Meta> orgList = new ArrayList<Meta>(Arrays.asList(new Meta(1,
				"a"), new Meta(2, "b"), new Meta(3, "c")));
		ArrayList<Meta> newList = new ArrayList<Meta>(Arrays.asList(new Meta(0,
				"c"), new Meta(0, "b"), new Meta(0, "d"), new Meta(0, "a")));
		Helper.mergeCollection(orgList, newList, true);
		ArrayList<Meta> resList = new ArrayList<Meta>(Arrays.asList(new Meta(3,
				"c"), new Meta(2, "b"), new Meta(0, "d"), new Meta(1, "a")));
		assertEquals("Array not same length", resList.size(), orgList.size());
		for (int i = 0; i < resList.size(); i++) {
			assertEquals("Id's different for index " + i, resList.get(0)
					.getId(), orgList.get(0).getId());
			assertEquals("Text different for index " + i, resList.get(0)
					.getDescription(), orgList.get(0).getDescription());
		}
	}

	@Test
	public void mergeSet() {
		HashSet<Meta> orgList = new HashSet<Meta>(Arrays.asList(
				new Meta(1, "a"), new Meta(2, "b"), new Meta(3, "c")));
		HashSet<Meta> newList = new HashSet<Meta>(Arrays.asList(
				new Meta(0, "c"), new Meta(0, "b"), new Meta(0, "d"), new Meta(
						0, "a")));
		Helper.mergeCollection(orgList, newList, true);
		HashSet<Meta> resList = new HashSet<Meta>(Arrays.asList(
				new Meta(3, "c"), new Meta(2, "b"), new Meta(0, "d"), new Meta(
						1, "a")));
		assertEquals("Array not same length", resList.size(), orgList.size());
		HashSet<Meta> tested = new HashSet<Meta>();
		for (Meta m : resList) {
			for (Meta m2 : orgList) {
				if (m.equals(m2)) {
					assertEquals(
							"Id's different for item " + m.getDescription(),
							m.getId(), m2.getId());
					tested.add(m2);
				}
			}
		}
		assertEquals("Original list has duplicate items", resList.size(),
				tested.size());
	}

	@Test
	public void mergeList2() {
		ArrayList<Meta> newList = new ArrayList<Meta>(Arrays.asList(new Meta(1,
				"a"), new Meta(2, "b"), new Meta(0, "d"), new Meta(3, "c")));
		ArrayList<Meta> orgList = new ArrayList<Meta>(Arrays.asList(new Meta(0,
				"c"), new Meta(0, "b"), new Meta(0, "a")));
		Helper.mergeCollection(orgList, newList, false);
		ArrayList<Meta> resList = new ArrayList<Meta>(Arrays.asList(new Meta(1,
				"a"), new Meta(2, "b"), new Meta(0, "d"), new Meta(3, "c")));
		assertEquals("Array not same length", resList.size(), orgList.size());
		for (int i = 0; i < resList.size(); i++) {
			assertEquals("Id's different for index " + i, resList.get(0)
					.getId(), orgList.get(0).getId());
			assertEquals("Text different for index " + i, resList.get(0)
					.getDescription(), orgList.get(0).getDescription());
		}
	}

	@Test
	public void mergeSet2() {
		HashSet<Meta> newList = new HashSet<Meta>(Arrays.asList(
				new Meta(1, "a"), new Meta(2, "b"), new Meta(0, "d"), new Meta(3, "c")));
		HashSet<Meta> orgList = new HashSet<Meta>(Arrays.asList(
				new Meta(0, "c"), new Meta(0, "b"), new Meta(
						0, "a")));
		Helper.mergeCollection(orgList, newList, false);
		HashSet<Meta> resList = new HashSet<Meta>(Arrays.asList(
				new Meta(3, "c"), new Meta(2, "b"), new Meta(0, "d"), new Meta(
						1, "a")));
		assertEquals("Array not same length", resList.size(), orgList.size());
		HashSet<Meta> tested = new HashSet<Meta>();
		for (Meta m : resList) {
			for (Meta m2 : orgList) {
				if (m.equals(m2)) {
					assertEquals(
							"Id's different for item " + m.getDescription(),
							m.getId(), m2.getId());
					tested.add(m2);
				}
			}
		}
		assertEquals("Original list has duplicate items", resList.size(),
				tested.size());
	}

}
