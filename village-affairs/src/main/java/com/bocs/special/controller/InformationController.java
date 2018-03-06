package com.bocs.special.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bocs.special.core.Constant;
import com.bocs.special.core.JavaEEFrameworkBaseController;
import com.bocs.special.dto.Echart;
import com.bocs.special.dto.Series;
import com.bocs.special.model.Information;
import com.bocs.special.model.Role;
import com.bocs.special.model.SysUser;
import com.bocs.special.service.InformationService;
import com.bocs.special.service.RoleService;
import com.bocs.util.HtmlUtils;

import core.exception.ServiceException;
import core.support.JsonData;
import core.support.PageView;
import core.support.SystemContext;

/**
 * 信息发布的控制层
 */
@Controller
@RequestMapping("/information")
public class InformationController extends JavaEEFrameworkBaseController<Information> implements Constant {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6252284364517593805L;
	@Resource
	private InformationService informationService;
	@Resource
	private RoleService roleService;
	
	
	
	
	
	// 管理所有信息
	@RequestMapping(value = "/manageNotices", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView manageNotices(Information informationParam) throws Exception {
		if(informationParam == null){
			informationParam = new Information();
		}
		int firstResult = SystemContext.getOffset();
		int pageSize = SystemContext.getPageSize();
		SysUser currentUser = getCurrentSysUser();
		Role adminRole = roleService.getByProerties("roleKey", "ROLE_ADMIN");
		if(currentUser.getRoles().contains(adminRole)){
		}else{
			//普通用户进来只能看到与自己本社区相关的信息
			//informationParam.setAuthor(currentUser.getUserName());
			List<String> communities = new ArrayList<String>();
			String community = currentUser.getCommunity();
			communities.add(community);//普通用户只能维护一个社区
			informationParam.setCommunities(communities);
		}
		PageView<Information> pageView = informationService.doPaginationQuery(informationParam, firstResult, pageSize);
		if(pageView != null && pageView.getRecords() != null && pageView.getRecords().size() > 0){
			for(Information info : pageView.getRecords()){
				info.setContentNoHTML(HtmlUtils.htmltoText(info.getContent()));
			}
		}
		ModelAndView modelAndView = new ModelAndView("information/sign_up/notice-list");
		modelAndView.addObject("pageView", pageView);
		modelAndView.addObject("community", currentUser.getCommunity());
		return modelAndView; 
	}
	
	/**
	 * 普通用户查看所有的信息（分不社区，不分栏目）
	 * @param informationParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView list(Information informationParam) throws Exception {
		if(informationParam == null){
			informationParam = new Information();
		}
		int firstResult = SystemContext.getOffset();
		int pageSize = SystemContext.getPageSize();
		PageView<Information> pageView = informationService.doPaginationQuery(informationParam, firstResult, pageSize);
		if(pageView != null && pageView.getRecords() != null && pageView.getRecords().size() > 0){
			for(Information info : pageView.getRecords()){
				info.setContentNoHTML(HtmlUtils.htmltoText(info.getContent()));
			}
		}
		ModelAndView modelAndView = new ModelAndView("information/info-list");
		modelAndView.addObject("pageView", pageView);
		return modelAndView; 
	}
	
	
	/**
	 * 查询所有社区的党建服务信息
	 * @param informationParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/partyInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView partyInfoList(Information informationParam) throws Exception{
		ModelAndView modelAndView = queryInfoList("party");
		modelAndView.setViewName("information/party-info-list");
		return modelAndView; 
	}
	
	/**
	 * 查看某个社区的所有民政民生信息
	 * @param community
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/communityPartyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView communityPartyInfo(String community) throws Exception{
		ModelAndView modelAndView = queryInfo("party", community);
		
		modelAndView.setViewName("information/community-party-info");
		return modelAndView;
	}
	
	
	/**
	 * 查询所有社区的民政民生信息
	 * @param informationParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/civilAffairsList", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView civilAffairsList(Information informationParam) throws Exception{
		ModelAndView modelAndView = queryInfoList("civilaffairs");
		modelAndView.setViewName("information/civilaffairs-list");
		return modelAndView; 
	}
	/**
	 * 查看某个社区的所有民政民生信息
	 * @param community
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/communitycivilAffairs", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView communitycivilAffairs(String community) throws Exception{
		ModelAndView modelAndView = queryInfo("civilaffairs", community);
		modelAndView.setViewName("information/community-civilaffairs");
		return modelAndView;
	}
	
	/**
	 * 查询所有社区的小区管理信息
	 * @param informationParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/communityAffairsList", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView communityAffairsList(Information informationParam) throws Exception{
		ModelAndView modelAndView = queryInfoList("communityaffairs");
		modelAndView.setViewName("information/community-affairs-list");
		return modelAndView; 
	}
	
	/**
	 * 查看某个社区的所有小区管理信息
	 * @param community
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/communityAffairs", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView communitycommunityAffairs(String community) throws Exception{
		ModelAndView modelAndView = queryInfo("communityaffairs", community);
		modelAndView.setViewName("information/community-affairs");
		return modelAndView;
	}
	
	/**
	 * 查询所有社区的综合治理信息
	 * @param informationParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/comprehensivesInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView comprehensivesInfoList(Information informationParam) throws Exception{
		ModelAndView modelAndView = queryInfoList("comprehensives");
		modelAndView.setViewName("information/comprehensives-info-list");
		return modelAndView; 
	}
	
	/**
	 * 查看某个社区的所有综合治理信息
	 * @param community
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/communityComprehensivesInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView communityComprehensivesInfo(String community) throws Exception{
		ModelAndView modelAndView = queryInfo("comprehensives", community);
		modelAndView.setViewName("information/community-comprehensives-info");
		return modelAndView;
	}
	
	/**
	 * 查询所有社区的其他信息
	 * @param informationParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/otherAffairsList", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView otherAffairsList(Information informationParam) throws Exception{
		ModelAndView modelAndView = queryInfoList("otheraffairs");
		modelAndView.setViewName("information/other-affairs-list");
		return modelAndView; 
	}
	
	/**
	 * 查看某个社区的所有其他信息
	 * @param community
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/communityOtherAffairs", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView communityOtherAffairs(String community) throws Exception{
		ModelAndView modelAndView = queryInfo("otheraffairs", community);
		modelAndView.setViewName("information/community-other-affairs");
		return modelAndView;
	}
	
	
	private ModelAndView queryInfoList(String type){
		ModelAndView modelAndView = new ModelAndView();
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("refreshTime", "desc");
		
		List<Information> penglangInfoList = informationService.queryByProerties(new String[]{"type","community"},  new Object[]{type,"penglang"}, sortedCondition, 5);
		List<Information> pengxiInfoList = informationService.queryByProerties(new String[]{"type","community"},  new Object[]{type,"pengxii"}, sortedCondition, 5);
		List<Information> pengxinInfoList = informationService.queryByProerties(new String[]{"type","community"},  new Object[]{type,"pengxin"}, sortedCondition, 5);
		List<Information> pengchenInfoList = informationService.queryByProerties(new String[]{"type","community"},  new Object[]{type,"pengchen"}, sortedCondition, 5);
		List<Information> pengyuanInfoList = informationService.queryByProerties(new String[]{"type","community"},  new Object[]{type,"pengyuan"}, sortedCondition, 5);
		List<Information> penglaiInfoList = informationService.queryByProerties(new String[]{"type","community"},  new Object[]{type,"penglai"}, sortedCondition, 5);
		modelAndView.addObject("penglangInfoList", penglangInfoList);
		modelAndView.addObject("pengxiInfoList", pengxiInfoList);
		modelAndView.addObject("pengxinInfoList", pengxinInfoList);
		modelAndView.addObject("pengchenInfoList", pengchenInfoList);
		modelAndView.addObject("pengyuanInfoList", pengyuanInfoList);
		modelAndView.addObject("penglaiInfoList", penglaiInfoList);
		return modelAndView;
	}
	
	private ModelAndView queryInfo(String type, String community){
		ModelAndView modelAndView = new ModelAndView();
		int firstResult  = SystemContext.getOffset();  
		int pageSize = SystemContext.getPageSize();
		Information informationParam = new Information();
		informationParam.setType(type);
		informationParam.setCommunity(community);
		PageView<Information> pageView = informationService.doPaginationQuery(informationParam, firstResult, pageSize);
		if(pageView != null && pageView.getRecords() != null && pageView.getRecords().size() > 0){
			for(Information info : pageView.getRecords()){
				info.setContentNoHTML(HtmlUtils.htmltoText(info.getContent()));
			}
		}
		modelAndView.addObject("pageView", pageView);
		modelAndView.addObject("community", community);
		return modelAndView;
	}
	
	// 查看单条发布的信息
	@RequestMapping(value = "/viewInfoDetail/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewInfoDetail(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Information information = informationService.get(id);
		SysUser currentUser = getCurrentSysUser();
		if(currentUser != null){
			return new ModelAndView("information/sign_up/view-information", "information", information); 
		}
		return new ModelAndView("information/view-information", "information", information); 
	}
	
	/**
	 * 跳转到回复界面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView replyPage(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Information information = informationService.get(id);
		return new ModelAndView("information/sign_up/reply-information", "information", information); 
	}
	
	/**
	 * 回复
	 * @param id
	 * @param content
	 * @return
	 */
	@PostMapping("reply")
	@ResponseBody
	public JsonData reply(String id, String content, String completePercent){
		JsonData jsonData = new JsonData();
		jsonData.setSuccess(true);
		try {
			Double percent = null;
			if(StringUtils.isNoneBlank(completePercent)){
				try {
					percent = Double.valueOf(completePercent);
				} catch (Exception e) {
					e.printStackTrace();
					jsonData.setSuccess(false);
					jsonData.setMessage("进度必须是数字，保留两位小数，不带%");
				}
			}
			informationService.replyInformation(id, content, percent, getCurrentSysUser());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonData.setSuccess(false);
			jsonData.setMessage(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			jsonData.setSuccess(false);
			jsonData.setMessage("系统发生错误");
		}
		return jsonData;
	}
	
	//获取单条信息，以便更新
	@RequestMapping(value = "/getInformation/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getInformation(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Information information = informationService.get(id);
		SysUser currentUser = getCurrentSysUser();
		ModelAndView modelAndView = new ModelAndView("information/sign_up/ckeditor");
		modelAndView.addObject("information", information);
		modelAndView.addObject("community", currentUser.getCommunity());
		return modelAndView;
	}

	// 全文检索信息
	@RequestMapping(value = "/getInformationHibernateSearch", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getInformationHibernateSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String luceneName = request.getParameter("luceneName");
		List<Information> informationLuceneList = informationService.queryByInformationName(luceneName);
		return new ModelAndView("information/sign_up/search-information_list", "informationLuceneList", informationLuceneList); 
	}

	@RequestMapping("/ckeditor")
	public ModelAndView ckeditor(){
		SysUser currentUser = getCurrentSysUser();
		return new ModelAndView("information/sign_up/ckeditor", "community", currentUser.getCommunity());
	}
	
	@RequestMapping("/statistics")
	public ModelAndView statistics(){
		return new ModelAndView("information/info-statistics");
	}
	
	
	@RequestMapping(value="/getEchart", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Echart getEchart(Information information){
		Echart echart = new Echart();
		echart.setCategories(getCategoriesCH(information.getTypes()));
		echart.setLegendData(getLegendData(information.getCommunities()));
		echart.setTitle("各社区查询统计结果");
		if(StringUtils.isNotBlank(information.getStartDate()) && StringUtils.isNotBlank(information.getEndDate())){
			echart.setSubTitle(information.getStartDate() + "至" + information.getEndDate());
		}else{
			echart.setSubTitle("");
		}
		String[] types = new String[]{Information.TYPE_PARTY, Information.TYPE_CIVIL_AFFAIRS, Information.TYPE_COMPREHENSIVES, Information.TYPE_COMMUNITYAFFAIRS, Information.TYPE_OTHER_AFFAIRS};
		String[] colors = new String[]{"#C23531", "#2F4554", "#61A0A8", "#5BB75B", "#FCEC1A","#3385FF"};
		Series[] series = new Series[6];
		if(information.getCommunities() != null && information.getCommunities().size() > 0){
			series = new Series[information.getCommunities().size()];
			colors = new String[information.getCommunities().size()];
			for(int i=0;i<information.getCommunities().size();i++){
				String community = information.getCommunities().get(i);
				colors[i] = getColor(community);
				Series serie = new Series();
				serie.setName(getCommunityCH(community));
				serie.setType("bar");
				if(information.getTypes() != null && information.getTypes().size() > 0){
					serie.setData(informationService.allCategoryCountEchart(information.getStartDate(), information.getEndDate(), community, (String[])information.getTypes().toArray(new String[information.getTypes().size()])));	
				}else{
					serie.setData(informationService.allCategoryCountEchart(information.getStartDate(), information.getEndDate(), community, types));	
				}
				series[i] = serie;
			}
		}else{
			
			series = new Series[6];
			Series penglang = new Series();
			penglang.setName("蓬朗");
			penglang.setType("bar");
			penglang.setData(informationService.allCategoryCountEchart(information.getStartDate(), information.getEndDate(), Information.COMMUNITY_PENGLANG, types));
			series[0] = penglang;
			 
			Series penglai = new Series();
			penglai.setName("蓬莱");
			penglai.setType("bar");
			penglai.setData(informationService.allCategoryCountEchart(information.getStartDate(), information.getEndDate(), Information.COMMUNITY_PENGLAI, types));
			series[1] = penglai;
			
			Series pengchen = new Series();
			pengchen.setName("蓬晨");
			pengchen.setType("bar");
			pengchen.setData(informationService.allCategoryCountEchart(information.getStartDate(), information.getEndDate(), Information.COMMUNITY_PENGCHEN, types));
			series[2] = pengchen;
			
			Series pengxii = new Series();
			pengxii.setName("蓬曦");
			pengxii.setType("bar");
			pengxii.setData(informationService.allCategoryCountEchart(information.getStartDate(), information.getEndDate(), Information.COMMUNITY_PENGXII, types));
			series[3] = pengxii;
			
			Series pengxin = new Series();
			pengxin.setName("蓬欣");
			pengxin.setType("bar");
			pengxin.setData(informationService.allCategoryCountEchart(information.getStartDate(), information.getEndDate(), Information.COMMUNITY_PENGXIN, types));
			series[4] = pengxin;
			
			Series pengyuan = new Series();
			pengyuan.setName("蓬苑");
			pengyuan.setType("bar");
			pengyuan.setData(informationService.allCategoryCountEchart(information.getStartDate(), information.getEndDate(), Information.COMMUNITY_PENGYUAN, types));
			series[5] = pengyuan;
		}
		echart.setSeries(series );
		echart.setColors(colors);
		return echart;
	}
	
	//"#C23531", "#2F4554", "#61A0A8", "#5BB75B", "#FCEC1A","#3385FF"
	private String getColor(String community) {
		String color = "";
		if(Information.COMMUNITY_PENGLANG.equals(community)){
			color = "#C23531";
		}else if(Information.COMMUNITY_PENGLAI.equals(community)){
			color = "#2F4554";
		}else if(Information.COMMUNITY_PENGCHEN.equals(community)){
			color = "#61A0A8";
		}else if(Information.COMMUNITY_PENGXII.equals(community)){
			color = "#5BB75B";
		}else if(Information.COMMUNITY_PENGXIN.equals(community)){
			color = "#FCEC1A";
		}else if(Information.COMMUNITY_PENGYUAN.equals(community)){
			color = "#3385FF";
		}
		return color;
	}

	private String[] getLegendData(List<String> communities) {
		String[] legendData = new String[]{"蓬朗","蓬莱","蓬晨","蓬曦","蓬欣","蓬苑"};
		if(communities != null && communities.size() > 0){
			legendData = new String[communities.size()];
			for(int i=0;i<communities.size();i++){
				legendData[i] = getCommunityCH(communities.get(i));
			}
		}
		return legendData;
	}

	private String getCommunityCH(String community) {
		String communityCH = "";
		if(Information.COMMUNITY_PENGCHEN.equals(community)){
			communityCH = "蓬晨";
		}else if(Information.COMMUNITY_PENGLAI.equals(community)){
			communityCH = "蓬莱";
		}else if(Information.COMMUNITY_PENGLANG.equals(community)){
			communityCH = "蓬朗";
		}else if(Information.COMMUNITY_PENGXII.equals(community)){
			communityCH = "蓬曦";
		}else if (Information.COMMUNITY_PENGXIN.equals(community)){
			communityCH = "蓬欣";
		}else if(Information.COMMUNITY_PENGYUAN.equals(community)){
			communityCH = "蓬苑";
		}else{
			communityCH = "蓬朗";
		}
		return communityCH;
	}

	private String[] getCategoriesCH(List<String> types) {
		String[] categories = new String[]{"党建服务","民政民生","综合治理","小区管理","其他"};
		if(types != null && types.size() > 0){
			categories = new String[types.size()];
			for(int i=0;i<types.size();i++){
				categories[i] = getCategoryCH(types.get(i));
			}
		}
		return categories;
	}

	private String getCategoryCH(String type) {
		String category = "";
		if(Information.TYPE_CIVIL_AFFAIRS.equals(type)){
			category = "民政民生";
		}else if(Information.TYPE_COMMUNITYAFFAIRS.equals(type)){
			category = "小区管理";
		}else if(Information.TYPE_COMPREHENSIVES.equals(type)){
			category = "综合治理";
		}else if(Information.TYPE_OTHER_AFFAIRS.equals(type)){
			category = "其他";
		}else if(Information.TYPE_PARTY.equals(type)){
			category = "党建服务";
		}else{
			category = "党建服务";
		}
		return category;
	}

	// 保存信息发布的实体Bean
	@RequestMapping(value = "/saveInformation", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonData doSave(Information entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonData jsonData = new JsonData();
		SysUser currentUser = getCurrentSysUser();
		if(StringUtils.isNotBlank(entity.getId())){
			Information infoDb = informationService.get(entity.getId());
			if(!currentUser.getUserName().equalsIgnoreCase(infoDb.getAuthor())){
				jsonData.setSuccess(false);
				jsonData.setMessage("不能修改");
				return jsonData;
			}else{
				if(StringUtils.isNotBlank(entity.getTitle())){
					infoDb.setTitle(entity.getTitle());
				}
				if(StringUtils.isNotBlank(entity.getContent())){
					String content = entity.getContent().replace("<img", "<img class=\"img-responsive\"");//加上bootstrap样式，让图片根据屏幕宽度自适应
					infoDb.setContent(content);
				}
				if(StringUtils.isNotBlank(entity.getCommunity())){
					infoDb.setCommunity(entity.getCommunity());
				}
				if(StringUtils.isNoneBlank(entity.getType())){
					infoDb.setType(entity.getType());
				}
				infoDb.setRefreshTime(new Date());
				informationService.update(infoDb);
			}
		}else{
			if("admin".equalsIgnoreCase(currentUser.getUserName()) && !"street".equalsIgnoreCase(entity.getCommunity())){
				entity.setImportant(true);
			}else{
				entity.setImportant(false);
			}
			entity.setAuthor(currentUser.getUserName());
			entity.setRefreshTime(new Date());
			informationService.persist(entity);
		}
		jsonData.setSuccess(true);
		return jsonData;
	}



	// 删除信息发布
	@RequestMapping(value = "/deleteInformation", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonData deleteInformation(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") String[] ids) throws IOException {
		JsonData jsonData = new JsonData();
		boolean flag = informationService.deleteByPK(ids);
		jsonData.setSuccess(flag);
		if (! flag) {
			jsonData.setMessage("删除失败");
		} 
		return jsonData;
	}

}
