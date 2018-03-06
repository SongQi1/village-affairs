package com.bocs.special.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bocs.special.core.Constant;
import com.bocs.special.core.JavaEEFrameworkBaseController;
import com.bocs.special.model.Information;
import com.bocs.special.model.Role;
import com.bocs.special.model.ServiceDetail;
import com.bocs.special.model.SysUser;
import com.bocs.special.model.Volunteer;
import com.bocs.special.service.RoleService;
import com.bocs.special.service.ServiceDetailService;
import com.bocs.special.service.VolunteerService;

import core.support.JsonData;
import core.support.PageView;
import core.support.SystemContext;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController  extends JavaEEFrameworkBaseController<Information> implements Constant{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2239923881334389871L;
	
	@Resource
	private VolunteerService volunteerService;
	@Resource
	private ServiceDetailService serviceDetailService;
	@Resource
	private RoleService roleService;

	@RequestMapping("/list")
	public ModelAndView list(Volunteer volunteer){
		int firstResult = SystemContext.getOffset();
		int pageSize = SystemContext.getPageSize();
		SysUser currentUser = getCurrentSysUser();
		
		ModelAndView modelAndView = new ModelAndView();
		if(currentUser != null){
			Role adminRole = roleService.getByProerties("roleKey", "ROLE_ADMIN");
			if(currentUser.getRoles().contains(adminRole)){
			}else{
				//普通用户进来只能看到与自己本社区的志愿者
				volunteer.setCommunity(currentUser.getCommunity());
			}
			modelAndView.setViewName("volunteer/sign_up/volunteer-list");
		}else{
			modelAndView.setViewName("volunteer/volunteer-list");
		}
		PageView<Volunteer> pageView = volunteerService.doPaginationQuery(volunteer, firstResult, pageSize);
		modelAndView.addObject("pageView", pageView);
		return modelAndView; 
	}

	
	@RequestMapping("/addVolunteerPage")
	public ModelAndView addVolunteerPage(Volunteer volunteer){
		ModelAndView modelAndView = new ModelAndView("volunteer/sign_up/add-volunteer");
		return modelAndView; 
	}
	
	
	/**
	 * 验证志愿者编号
	 * @param id
	 * @param no
	 * @return
	 */
	@RequestMapping(value="/checkNo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public boolean checkNo(String id, String no){
		if(StringUtils.isNoneBlank(id)){//更新时验证志愿者编号
			Volunteer volunteer = volunteerService.get(id);
			if(volunteer.getNo().equals(no)){
				return true;
			}else{
				Volunteer volunteer1 = volunteerService.getByProerties("no", no);
				if(volunteer1 == null){
					return true;
				}
			}
		}else{//新增时验证志愿者编号
			Volunteer volunteer = volunteerService.getByProerties("no", no);
			if(volunteer == null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 验证志愿者身份证号
	 * @param id
	 * @param idCard
	 * @return
	 */
	@RequestMapping(value="/checkIdCard", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public boolean checkIdCard(String id, String idCard){
		if(StringUtils.isNoneBlank(id)){//更新时验证身份证号
			Volunteer volunteer = volunteerService.get(id);
			if(volunteer.getIdCard().equals(idCard)){
				return true;
			}else{
				Volunteer volunteer1 = volunteerService.getByProerties("idCard", idCard);
				if(volunteer1 == null){
					return true;
				}
			}
		}else{//新增时验证身份证号
			Volunteer volunteer = volunteerService.getByProerties("idCard", idCard);
			if(volunteer == null){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping("/addVolunteer")
	@ResponseBody
	public JsonData addVolunteer(Volunteer volunteer){
		JsonData jsonData = new JsonData();
		if(StringUtils.isBlank(volunteer.getCommunity())){
			SysUser currentUser = getCurrentSysUser();
			volunteer.setCommunity(currentUser.getCommunity());
		}
		try {
			volunteerService.persist(volunteer);
			jsonData.setSuccess(true);
		}  catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	@RequestMapping(value="/delete/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonData delete(@PathVariable("id")String id){
		JsonData jsonData = new JsonData();
		try {
			volunteerService.deleteByPK(id);
			jsonData.setSuccess(true);
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView sysuserprofile(@PathVariable("id")String id)  {
		Volunteer volunteer = volunteerService.get(id);
		return new ModelAndView("volunteer/sign_up/edit-volunteer", "volunteer", volunteer);
	}
	
	@RequestMapping("/updateVolunteer")
	@ResponseBody
	public JsonData updateVolunteer(Volunteer volunteer){
		JsonData jsonData = new JsonData();
		//更新
		Volunteer volunteerDb = volunteerService.get(volunteer.getId());
		volunteerDb.setSex(volunteer.getSex());
		volunteerDb.setIdCard(volunteer.getIdCard());
		volunteerDb.setPhoneNo(volunteer.getPhoneNo());
		if(StringUtils.isNotBlank(volunteer.getCommunity())){
			volunteerDb.setCommunity(volunteer.getCommunity());
		}
		try {
			volunteerService.update(volunteerDb);
			jsonData.setSuccess(true);
		}  catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	
	@RequestMapping("serviceList/{id}")
	public ModelAndView serviceList(@PathVariable("id")String id){
		int firstResult = SystemContext.getOffset();
		int pageSize = SystemContext.getPageSize();
		Volunteer volunteer = volunteerService.get(id);
		ServiceDetail serviceDetail = new ServiceDetail();
		serviceDetail.setVolunteer(volunteer);
		PageView<ServiceDetail> pageView = serviceDetailService.doPaginationQuery(serviceDetail, firstResult, pageSize);
		ModelAndView modelAndView = new ModelAndView();
		SysUser currentUser = getCurrentSysUser();
		if(currentUser != null){
			modelAndView.setViewName("volunteer/sign_up/volunteer-service-list");
		}else{
			modelAndView.setViewName("volunteer/volunteer-service-list");
		}
		modelAndView.addObject("pageView", pageView);
		modelAndView.addObject("volunteer", volunteer);
		return modelAndView;
	}
	
	
	@RequestMapping("addServicePage/{volunteerId}")
	public ModelAndView addServicePage(@PathVariable("volunteerId")String volunteerId)  {
		Volunteer volunteer = volunteerService.get(volunteerId);
		return new ModelAndView("volunteer/sign_up/add-service-detail", "volunteer", volunteer);
	}
	
	
	@RequestMapping("addService")
	@ResponseBody
	public JsonData addService(String volunteerId, ServiceDetail serviceDetail){
		JsonData jsonData = new JsonData();
		try {
			String serviceId = serviceDetailService.addService(volunteerId, serviceDetail);
			jsonData.setData(serviceId);
			jsonData.setSuccess(true);
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	@RequestMapping("editService/{serviceId}")
	public ModelAndView editService(@PathVariable("serviceId") String serviceId)  {
		ServiceDetail serviceDetail = serviceDetailService.get(serviceId);
		Volunteer volunteer = serviceDetail.getVolunteer();
		ModelAndView modelAndView = new ModelAndView("volunteer/sign_up/edit-service-detail");
		modelAndView.addObject("volunteer", volunteer);
		modelAndView.addObject("serviceDetail", serviceDetail);
		return modelAndView;
	}
	
	
	@RequestMapping("updateService")
	@ResponseBody
	public JsonData updateService(String volunteerId, ServiceDetail serviceDetail){
		JsonData jsonData = new JsonData();
		try {
			serviceDetailService.updateService(volunteerId, serviceDetail);
			jsonData.setSuccess(true);
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	@RequestMapping("/delService/{serviceId}")
	@ResponseBody
	public JsonData delService(@PathVariable("serviceId") String serviceId){
		JsonData jsonData = new JsonData();
		try {
			serviceDetailService.delService(serviceId);
			jsonData.setSuccess(true);
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	/**
	 * 查看服务图片
	 * @param serviceId
	 * @return
	 */
	@RequestMapping("/servicePictures/{serviceId}")
	public ModelAndView servicePictures(@PathVariable("serviceId") String serviceId){
		ServiceDetail serviceDetail = serviceDetailService.get(serviceId);
		Volunteer volunteer = serviceDetail.getVolunteer();
		ModelAndView modelAndView = new ModelAndView("volunteer/service-pictures");
		modelAndView.addObject("volunteer", volunteer);
		modelAndView.addObject("serviceDetail", serviceDetail);
		return modelAndView;
		
	}
}
