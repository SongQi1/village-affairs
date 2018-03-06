package com.bocs.special.service;

import java.util.List;

import com.bocs.special.model.Information;
import com.bocs.special.model.SysUser;

import core.exception.ServiceException;
import core.service.Service;
import core.support.PageView;

/**
 * 信息发布的业务逻辑层的接口
 */
public interface InformationService extends Service<Information> {

	// 获取信息，包括内容的HTML和过滤HTML两部分
	List<Information> queryInformationHTMLList(List<Information> resultList);

	// 生成信息的索引
	void indexingInformation();

	// 全文检索信息
	List<Information> queryByInformationName(String name);
	

	/**
	 * 分页查询发布的项目信息
	 * @param informationParam
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	PageView<Information> doPaginationQuery(Information informationParam, Integer firstResult, Integer pageSize);

	/**
	 * 按栏目分别 统计各社区年度发布信息总数
	 * @param year
	 * @param community
	 * @param categories 
	 * @return
	 */
	int[] allCategoryCountEchart(String year, String community, String[] categories);
	
	int[] allCategoryCountEchart(String startDate, String endDate, String community, String[] categories);

	/**
	 * 按年度统计各社区发布的信息总数
	 * @param year
	 * @param community
	 * @return
	 */
	int yearInformationCount(String year, String community);

	/**
	 * 回复信息
	 * @param id
	 * @param content
	 * @param completepercent 
	 * @param sysUser 
	 */
	void replyInformation(String id, String content, Double completepercent, SysUser sysUser) throws ServiceException;

	

}
