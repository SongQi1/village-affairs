package com.bocs.special.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.bocs.special.dao.InformationDao;
import com.bocs.special.dao.ReplyInfoDao;
import com.bocs.special.model.Information;
import com.bocs.special.model.ReplyInfo;
import com.bocs.special.model.SysUser;
import com.bocs.special.service.InformationService;
import com.bocs.util.HtmlUtils;

import core.exception.ServiceException;
import core.service.BaseService;
import core.support.PageView;

/**
 * 信息发布的业务逻辑层的实现
 */
@Service
public class InformationServiceImpl extends BaseService<Information> implements InformationService {

	private InformationDao informationDao;
	@Resource
	private ReplyInfoDao replyInfoDao;

	@Resource
	public void setInformationDao(InformationDao informationDao) {
		this.informationDao = informationDao;
		this.dao = informationDao;
	}

	// 获取信息，包括内容的HTML和过滤HTML两部分

	public List<Information> queryInformationHTMLList(List<Information> resultList) {
		List<Information> informationList = new ArrayList<Information>();
		for (Information entity : resultList) {
			Information information = new Information();
			information.setId(entity.getId());
			information.setTitle(entity.getTitle());
			information.setAuthor(entity.getAuthor());
			information.setRefreshTime(entity.getRefreshTime());
			information.setContent(entity.getContent());
			information.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			informationList.add(information);
		}
		return informationList;
	}

	// 生成信息的索引

	public void indexingInformation() {
		informationDao.indexingInformation();
	}

	// 全文检索信息

	public List<Information> queryByInformationName(String name) {
		return informationDao.queryByInformationName(name);
	}

	@Override
	public PageView<Information> doPaginationQuery(Information informationParam,
			Integer firstResult, Integer pageSize) {
		return informationDao.doPaginationQuery(informationParam, firstResult, pageSize);
	}

	@Override
	public int[] allCategoryCountEchart(String year, String community, String[] categories) {
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = DateUtils.parseDate(year+"/01/01 00:00:00", new String[]{"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"});
			endTime = DateUtils.parseDate(year+"/12/31 23:59:59", new String[]{"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] count = new int[categories.length];
		for(int i=0;i<categories.length;i++){
			String category = categories[i];
			count[i] = informationDao.countByYear(startTime, endTime, community, category);
		}
		return count;
	}
	@Override
	public int[] allCategoryCountEchart(String startDate, String endDate, String community, String[] types) {
		Date startTime = null;
		Date endTime = null;
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNoneBlank(endDate)){
			try {
				startTime = DateUtils.parseDate(startDate+" 00:00:00", new String[]{"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"});
				endTime = DateUtils.parseDate(endDate+" 23:59:59", new String[]{"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"});
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int[] count = new int[types.length];
		for(int i=0;i<types.length;i++){
			String type = types[i];
			count[i] = informationDao.countByYear(startTime, endTime, community, type);
		}
		return count;
	}
	
	@Override
	public int yearInformationCount(String year, String community) {
		return informationDao.countByYear(year, community);
	}

	@Override
	public void replyInformation(String id, String content, Double completePercent, SysUser sysUser) throws ServiceException {
		// TODO Auto-generated method stub
		Information information = informationDao.get(id);
		if(information != null){
			if(completePercent != null){
				if(completePercent > 100){
					throw new ServiceException("进度不能大于100");
				}else{
					information.setCompletePercent(completePercent);
					informationDao.update(information);
				}
			}
			ReplyInfo replyInfo = new ReplyInfo();
			replyInfo.setAuthor(sysUser.getUserName());
			replyInfo.setContent(content);
			replyInfo.setReplyTime(new Date());
			replyInfo.setInformation(information);
			
			replyInfoDao.persist(replyInfo);
		}else{
			throw new ServiceException("信息编辑不存在");
		}
		
	}


}
