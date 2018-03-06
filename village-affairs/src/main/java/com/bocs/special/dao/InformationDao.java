package com.bocs.special.dao;

import java.util.Date;
import java.util.List;

import com.bocs.special.model.Information;

import core.dao.Dao;
import core.support.PageView;

/**
 * 信息发布的数据持久层的接口
 */
public interface InformationDao extends Dao<Information> {

	// 生成信息的索引
	void indexingInformation();

	// 全文检索信息
	List<Information> queryByInformationName(String name);

	/**
	 * 分页查询已发布信息
	 * @param informationParam
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	PageView<Information> doPaginationQuery(Information informationParam, Integer firstResult, Integer pageSize);
	
	/**
	 * 查询已发布信息
	 * @param informationParam
	 * @return
	 */
	List<Information> query(Information informationParam);

	/**
	 * 统计各社区的栏目数量
	 */
	int countByYear(Date startTime, Date endTime, String community, String type);

	/**
	 * 按年统计各社区所有发布信息的数量 
	 * @param year
	 * @param community
	 * @return
	 */
	int countByYear(String year, String community);

}
