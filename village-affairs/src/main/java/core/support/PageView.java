package core.support;

import java.util.List;

/**
 *
 */
public class PageView<E> {

	/** list data * */
	private List<E> records;
	/** total page * */
	private long totalPage = 1;
	/** count per page * */
	private int maxResult = 12;
	/** current page * */
	private int currentPage = 0;
	/** total record qty * */
	private long totalRecord;
	
	/**
	 * Whether has next page.
	 */
	private boolean hasNextPage;

	/**
	 * Whether has previous page.
	 */
	private boolean hasPreviousPage;
	
	

	public PageView(int maxResult, int firstResult) {
		this.maxResult = maxResult;
		this.currentPage = (firstResult/maxResult)+1;
	}
	

	public void setQueryResult(QueryResult<E> qr) {
		setRecords(qr.getResultList());
		setTotalRecord(qr.getTotalCount());
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord % this.maxResult == 0 ? this.totalRecord / this.maxResult : this.totalRecord / this.maxResult + 1);
		this.hasNextPage = (this.currentPage < this.totalPage);
		this.hasPreviousPage = (this.currentPage > 1);
	}

	public List<E> getRecords() {
		return records;
	}

	public void setRecords(List<E> records) {
		this.records = records;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalpage) {
		this.totalPage = totalpage;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getFirstResult() {
		return (this.currentPage - 1) * this.maxResult;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	
	
}
