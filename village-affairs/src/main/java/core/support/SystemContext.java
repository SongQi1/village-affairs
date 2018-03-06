package core.support;

public class SystemContext {

	public static ThreadLocal offset = new ThreadLocal();

	public static ThreadLocal pageSize = new ThreadLocal();

	public static int DEFAULT_PAGE_SIZE = 10;

	public static void setOffset(int _offset) {

		offset.set(_offset);

	}

	public static int getOffset() {

		Integer _offset = (Integer) offset.get();

		if (_offset == null) {

			_offset = 0;
		}

		return _offset;
	}

	public static void removeOffset() {
		offset.remove();
	}

	public static void setPageSize(int _pageSize) {

		pageSize.set(_pageSize);
	}

	public static int getPageSize() {

		Integer _pageSize = (Integer) pageSize.get();

		if (_pageSize == null) {

			_pageSize = DEFAULT_PAGE_SIZE;
		}

		return _pageSize;

	}

	public static void removePagesize() {
		pageSize.remove();
	}
}