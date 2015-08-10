package uk.co.icfuture.mvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Item not found")
public class ItemNotFoundException extends Exception {

	private static final long serialVersionUID = -7562993524235185735L;

	private int index = 0;

	public ItemNotFoundException(String itemType, int id) {
		super("ItemNotFoundException for item " + itemType + " with id " + id);
	}

	public ItemNotFoundException(String itemType, String text, int index) {
		super("ItemNotFoundException for item " + itemType + " with text '"
				+ text + "'");
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
