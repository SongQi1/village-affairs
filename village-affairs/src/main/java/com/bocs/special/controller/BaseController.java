package com.bocs.special.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bocs.special.core.Constant;
import com.bocs.special.core.JavaEEFrameworkBaseController;
import com.bocs.special.dto.Echart;
import com.bocs.special.dto.Epie;
import com.bocs.special.dto.PieSeriesData;
import com.bocs.special.dto.Series;
import com.bocs.special.model.Information;
import com.bocs.special.model.Volunteer;
import com.bocs.special.service.InformationService;
import com.bocs.special.service.VolunteerService;


@Controller
public class BaseController  extends JavaEEFrameworkBaseController<Information> implements Constant {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7085217039665874688L;
	@Resource
	private InformationService informationService;
	@Resource
	private VolunteerService volunteerService;
	
	@RequestMapping(value="/")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("index");
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put("totalServiceTime", "desc");
		List<Volunteer> volunteerList = volunteerService.doQueryAll(sortedCondition, 10);
		Map<String, String> sortedCondition1 = new HashMap<String, String>();
		sortedCondition1.put("refreshTime", "desc");
		List<Information> informationList = informationService.doQueryAll(sortedCondition1, 20);
		modelAndView.addObject("volunteerList", volunteerList);
		modelAndView.addObject("informationList", informationList);
		return modelAndView;
	}
	
	@RequestMapping("/getEchart")
	@ResponseBody
	public Echart getEchart(){
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		Echart echart = new Echart();
		echart.setTitle("各社区绩效情况明细");
		echart.setSubTitle(year +"年度");
		Series[] series = new Series[6];
		//"党建服务","民政民生","综合治理","小区管理","其他"
		String[] categories = new String[]{Information.TYPE_PARTY, Information.TYPE_CIVIL_AFFAIRS, Information.TYPE_COMPREHENSIVES, Information.TYPE_COMMUNITYAFFAIRS, Information.TYPE_OTHER_AFFAIRS};
		
		Series penglang = new Series();
		penglang.setName("蓬朗");
		penglang.setType("bar");
		penglang.setData(informationService.allCategoryCountEchart(year,"penglang", categories));
		series[0] = penglang;
		
		 
		Series penglai = new Series();
		penglai.setName("蓬莱");
		penglai.setType("bar");
		penglai.setData(informationService.allCategoryCountEchart(year,"penglai", categories));
		series[1] = penglai;
		
		Series pengchen = new Series();
		pengchen.setName("蓬晨");
		pengchen.setType("bar");
		pengchen.setData(informationService.allCategoryCountEchart(year,"pengchen", categories));
		series[2] = pengchen;
		
		Series pengxii = new Series();
		pengxii.setName("蓬曦");
		pengxii.setType("bar");
		pengxii.setData(informationService.allCategoryCountEchart(year,"pengxii", categories));
		series[3] = pengxii;
		
		Series pengxin = new Series();
		pengxin.setName("蓬欣");
		pengxin.setType("bar");
		pengxin.setData(informationService.allCategoryCountEchart(year,"pengxin", categories));
		series[4] = pengxin;
		
		Series pengyuan = new Series();
		pengyuan.setName("蓬苑");
		pengyuan.setType("bar");
		pengyuan.setData(informationService.allCategoryCountEchart(year,"pengyuan", categories));
		series[5] = pengyuan;
		
		
		echart.setCategories(new String[]{"党建服务","民政民生","综合治理","小区管理","其他"});
		echart.setSeries(series );
		return echart;
	}
	
	@RequestMapping("/getEpie")
	@ResponseBody
	public Epie getEpie(){
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		Epie ePie = new Epie();
		ePie.setTitle("各社区完成工作汇总");
		ePie.setSubTitle(year + "年度");
		
		
		
		PieSeriesData[] seriesData = new PieSeriesData[6];
		
		PieSeriesData penglang = new PieSeriesData();
		penglang.setName("蓬朗");
		penglang.setValue(informationService.yearInformationCount(year, "penglang"));
		seriesData[0] = penglang;
		
		PieSeriesData penglai = new PieSeriesData();
		penglai.setName("蓬莱");
		penglai.setValue(informationService.yearInformationCount(year, "penglai"));
		seriesData[1] = penglai;
		
		PieSeriesData pengchen = new PieSeriesData();
		pengchen.setName("蓬晨");
		pengchen.setValue(informationService.yearInformationCount(year, "pengchen"));
		seriesData[2] = pengchen;
		
		PieSeriesData pengxii = new PieSeriesData();
		pengxii.setName("蓬曦");
		pengxii.setValue(informationService.yearInformationCount(year, "pengxii"));
		seriesData[3] = pengxii;
		
		PieSeriesData pengxin = new PieSeriesData();
		pengxin.setName("蓬欣");
		pengxin.setValue(informationService.yearInformationCount(year, "pengxin"));
		seriesData[4] = pengxin;
		
		PieSeriesData pengyan = new PieSeriesData();
		pengyan.setName("蓬苑");
		pengyan.setValue(informationService.yearInformationCount(year, "pengyuan"));
		seriesData[5] = pengyan;
		
		
		ePie.setSeriesData(seriesData );
		return ePie;
	}
}
