package uk.co.icfuture.mvc.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Helper {

	@SuppressWarnings("unchecked")
	public static <T> T uncheckedCast(Object obj) {
		return (T) obj;
	}

	public static <T> void mergeCollection(Collection<T> myCollection,
			Collection<T> updateCollection, boolean myCollectionPriority) {
		if (myCollection instanceof List<?>) {
			List<T> collection = (List<T>) myCollection;
			int insertAt = 0;
			for (T item : updateCollection) {
				if (insertAt >= collection.size()) {
					collection.add(item);
				} else if (!collection.get(insertAt).equals(item)) {
					T i = collection.get(insertAt);

					if (collection.contains(item)) {
						int ind = collection.indexOf(item);
						if (myCollectionPriority) {
							collection.set(insertAt, collection.get(ind));
						} else {
							collection.set(insertAt, item);
						}
						collection.remove(ind);
					} else {
						collection.set(insertAt, item);
					}
					collection.add(i);
				}
				insertAt++;
			}
		} else {
			for (T item : updateCollection) {
				if (!myCollection.contains(item)) {
					myCollection.add(item);
				} else if (!myCollectionPriority) {
					myCollection.remove(item);
					myCollection.add(item);
				}
			}
			HashSet<T> removes = new HashSet<T>();
			for (T item : myCollection) {
				if (!updateCollection.contains(item)) {
					removes.add(item);
				}
			}
			for (T m : removes) {
				myCollection.remove(m);
			}
		}
	}
}
