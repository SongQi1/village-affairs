package com.bocs.special.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.bocs.special.dao.InformationDao;
import com.bocs.special.model.Information;
import com.bocs.util.HtmlUtils;

import core.dao.BaseDao;
import core.support.PageView;

/**
 * 信息发布的数据持久层的实现类
 */
@Repository
public class InformationDaoImpl extends BaseDao<Information> implements InformationDao {


	// 生成信息的索引l
	public void indexingInformation() {
		try {
			FullTextSession fullTextSession = Search.getFullTextSession(getSession());
			// Object information = fullTextSession.load(Information.class, new BigDecimal(99));
			// fullTextSession.index(information);
			fullTextSession.createIndexer(Information.class).threadsForSubsequentFetching(1).threadsToLoadObjects(1).startAndWait();
			fullTextSession.flushToIndexes();
			fullTextSession.getSearchFactory().optimize();
			fullTextSession.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全文检索信息
	public List<Information> queryByInformationName(final String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		FullTextSession fullTextSession = Search.getFullTextSession(getSession());
		SearchFactory searchFactory = fullTextSession.getSearchFactory();
		final QueryBuilder queryBuilder = searchFactory.buildQueryBuilder().forEntity(Information.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder.bool().should(queryBuilder.keyword().onField("title").matching(name).createQuery())
				.should(queryBuilder.keyword().onField("author").matching(name).createQuery()).should(queryBuilder.keyword().onField("content").matching(name).createQuery()).createQuery();
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Information.class).setMaxResults(500);

		List<Information> originalInformationList = fullTextQuery.list();
		List<Information> informationList = new ArrayList<Information>();
		for (Information entity : originalInformationList) {
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

	@Override
	public PageView<Information> doPaginationQuery(Information informationParam,
			Integer firstResult, Integer pageSize) {
		Criteria criteria = getCriteria(informationParam);
		criteria.addOrder(Order.desc("refreshTime"));
		return resultPage(criteria, firstResult, pageSize);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Information> query(Information informationParam){
		Criteria criteria = getCriteria(informationParam);
		return criteria.list();
	}
	
	private Criteria getCriteria(Information informationParam){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Information.class);
		if(informationParam != null){
			if(StringUtils.isNotBlank(informationParam.getAuthor())){
				criteria.add(Restrictions.eq("author",informationParam.getAuthor()));
			}
			if(StringUtils.isNotBlank(informationParam.getTitle())){
				criteria.add(Restrictions.like("title", informationParam.getTitle(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(informationParam.getType())){
				criteria.add(Restrictions.eq("type",informationParam.getType()));
			}
			if(informationParam.getTypes() != null && informationParam.getTypes().size() > 0){
				criteria.add(Restrictions.in("type", informationParam.getTypes()));
			}
			if(StringUtils.isNotBlank(informationParam.getCommunity())){
				criteria.add(Restrictions.eq("community",informationParam.getCommunity()));
			}
			if(informationParam.getCommunities() != null && informationParam.getCommunities().size() > 0){
				criteria.add(Restrictions.in("community", informationParam.getCommunities()));
			}
			if(informationParam.getImportant() != null){
				criteria.add(Restrictions.eq("important", informationParam.getImportant()));
			}
			if(StringUtils.isNotBlank(informationParam.getEndDate()) && StringUtils.isNoneBlank(informationParam.getStartDate())){
				String startTime = informationParam.getStartDate() + " 00:00:01";
				String endTime = informationParam.getEndDate() + " 23:59:59";
				try {
					criteria.add(Restrictions.gt("refreshTime", DateUtils.parseDate(startTime, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"})));
					criteria.add(Restrictions.lt("refreshTime", DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"})));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return criteria;
	}

	@Override
	public int countByYear(Date startTime, Date endTime, String community, String type) {
		String hql = "select count(*) from Information where community=:community and type=:type and important=false ";
		if(startTime != null && endTime != null){
			hql += "and refreshTime between :startTime and :endTime";
		}
		Query query = getSession().createQuery(hql)
		.setParameter("community", community)
		.setParameter("type", type);
		if(startTime != null && endTime != null){
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
		}
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}
	
	@Override
	public int countByYear(String year, String community) {
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = DateUtils.parseDate(year+"/01/01 00:00:00", new String[]{"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"});
			endTime = DateUtils.parseDate(year+"/12/31 23:59:59", new String[]{"yyyy/MM/dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "select count(*) from Information where community=:community and refreshTime between :startTime and :endTime and important=false";
		Long count = (Long) getSession().createQuery(hql)
		.setParameter("community", community)
		.setParameter("startTime", startTime)
		.setParameter("endTime", endTime)
		.uniqueResult();
		return count.intValue();
	}

}
