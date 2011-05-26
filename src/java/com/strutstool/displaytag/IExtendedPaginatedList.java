package com.strutstool.displaytag;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;


/** A convienience Paginated List for Pagination using Display Tag
 * </code>com.gorti.project.web.ui.action.IExtendedPaginatedList</code> extends <code>org.displaytag.pagination.PaginatedList</code>
 * @author Ram Gorti
 *
 */
public interface IExtendedPaginatedList extends PaginatedList {

    /** Request params as constants.  */
    public interface IRequestParameters{
        String SORT = "sort";
        String PAGE = "page";
        String ASC = "asc";
        String DESC = "desc";
        String DIRECTION = "dir";
    }

    /** Set the default page size **/
    int DEFAULT_PAGE_SIZE = 25;

    /** set results list */
    void setList(List resultList);

    /**  Set the Total - total number of records or rows (e.g 10,000 rows found with the query) **/
    void setTotalNumberOfRows(int total);

    /**  set the Page Size - to display the required number of rows (e.g 25 rows out of 10,000)  **/
    void setPageSize(int pageSize);

    /** Set the Index - start with 0 and keep increamenting  **/
    void setIndex(int index);

    /** get the first record index **/
    int getFirstRecordIndex();

    /**  get page size **/
    int getPageSize();

    /**  Set the sort Direction  -  asc or dsc **/
    void setSortDirection(SortOrderEnum sortOrderEnum);

    /** set sort criterion **/
    void setSortCriterion(String sortCriterion);

}