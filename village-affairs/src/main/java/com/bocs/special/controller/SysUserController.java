package com.bocs.special.controller;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bocs.special.core.Constant;
import com.bocs.special.core.JavaEEFrameworkBaseController;
import com.bocs.special.model.Role;
import com.bocs.special.model.SysUser;
import com.bocs.special.model.UserStatus;
import com.bocs.special.service.RoleService;
import com.bocs.special.service.SysUserService;
import com.bocs.util.PropertiesUtils;

import core.exception.ServiceException;
import core.support.JsonData;
import core.support.PageView;
import core.support.SystemContext;

@Controller
public class SysUserController  extends JavaEEFrameworkBaseController<SysUser> implements Constant{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4717327950648534038L;
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private RoleService roleService;
	

	@RequestMapping(value="/sysUser/login", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonData login(SysUser user, HttpServletRequest request){
		JsonData jsonData = new JsonData();
		try {
			sysUserService.login(user);
			//跳转到登录前的页面
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			if(savedRequest != null ){
				String url = savedRequest.getRequestUrl();
				if(StringUtils.isNotBlank(url)){
					jsonData.setMessage(url);
				}
			}
			jsonData.setSuccess(true);
		} catch (ServiceException e) {
			e.printStackTrace();
			jsonData.setMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			jsonData.setMessage("系统发生错误");
		}
		return jsonData;
	}
	
	@RequestMapping(value="/home",method = { RequestMethod.POST, RequestMethod.GET })
	public String home(HttpServletRequest request){
		SysUser currentUser = getCurrentSysUser();
		Set<Role> roles = currentUser.getRoles();
		if(roles != null && roles.size() > 0){
			for(Role role : roles){
				if("ROLE_ADMIN".equals(role.getRoleKey())){
					return "redirect:/information/manageNotices";
				}else if("ROLE_USER".equals(role.getRoleKey())){
					return "redirect:/information/manageNotices";
				}else{
					return "home";
				}
			}
		}
		return "home";
	}
	
	
	@RequestMapping(value="/sysUser/addUserPage", method = { RequestMethod.POST, RequestMethod.GET })
	public String addUserPage(){
		return "sysUser/add-user";
	}
	
	
	
	/**
	 * 检查用户名是否存在
	 * @return7
	 */
	@RequestMapping(value="/sysUser/checkUserName", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public boolean checkUserName(String userName){
		SysUser user = sysUserService.getByProerties("userName", userName);
		if(user == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查邮箱是否存在
	 * @return
	 */
	@RequestMapping(value="/sysUser/checkEmail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public boolean checkEmail(String email){
		SysUser user = sysUserService.getByProerties("email", email);
		if(user == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 管理员创建用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/sysUser/addUser", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonData register(SysUser userModel){
		JsonData jsonData = new JsonData();
		try {
			sysUserService.registerUser(userModel);
			jsonData.setSuccess(true);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonData.setMessage(e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/sysUser/delete/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonData delete(@PathVariable("id")String id){
		JsonData jsonData = new JsonData();
		try {
			sysUserService.deleteByPK(id);
			jsonData.setSuccess(true);
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	
	
	/**
	 * 获取个人资料信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/sysUser/profile")
	public ModelAndView sysuserprofile()  {
		SysUser sysuser = sysUserService.get(getCurrentSysUser().getId());
		Role role = sysuser.getRoles().iterator().next();
		sysuser.setRoleId(role.getId());
		return new ModelAndView("sysUser/profile", "sysUser", sysuser);
	}
	
	/**
	 * 获取用户资料信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/sysUser/edit/{id}")
	public ModelAndView sysuserprofile(@PathVariable("id")String id)  {
		SysUser sysuser = sysUserService.get(id);
		Role role = sysuser.getRoles().iterator().next();
		sysuser.setRoleId(role.getId());
		return new ModelAndView("sysUser/edit-user", "sysUser", sysuser);
	}
	
	/**
	 * 获取用户资料信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/sysUser/view/{id}")
	public ModelAndView viewProfile(@PathVariable("id")String id)  {
		SysUser sysuser = sysUserService.get(id);
		return new ModelAndView("sysUser/view-profile", "sysUser", sysuser);
	}
	
	/**
	 * 用户更新个人信息
	 * @param userModel
	 * @return
	 */
	@RequestMapping("/sysUser/updateProfile")
	@ResponseBody
	public JsonData updateProfile(SysUser userModel){
		JsonData jsonData = new JsonData();
		if(StringUtils.isNotBlank(userModel.getId())){
			SysUser sysUserDb = sysUserService.get(userModel.getId());
			sysUserDb.setChineseName(userModel.getChineseName());
			sysUserDb.setEmail(userModel.getEmail());
			sysUserDb.setQq(userModel.getQq());
			sysUserDb.setWeixin(userModel.getWeixin());
			sysUserDb.setMobileNo(userModel.getMobileNo());
			sysUserDb.setDegree(userModel.getDegree());
			sysUserDb.setJobPosition(userModel.getJobPosition());
			sysUserDb.setJobTitle(userModel.getJobTitle());
			sysUserDb.setCommunity(userModel.getCommunity());
			if(userModel.getRoleIds() != null && userModel.getRoleIds().size() > 0){
				sysUserDb.getRoles().clear();
				for(Long roleId : userModel.getRoleIds()){
					sysUserDb.getRoles().add(roleService.get(roleId));
				}
			}
			sysUserService.merge(sysUserDb);
			jsonData.setSuccess(true);
		}
		
		return jsonData;
	}

	/**
	 * 跳转到修改用户密码页面
	 * @return
	 */
	@RequestMapping("/sysUser/setting")
	public String setting(){
		
		return "sysUser/setting";
	}
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
	
	/**
	 * 更改密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sysUser/resetPassword")
	@ResponseBody
	public JsonData resetPassword(HttpServletRequest request, HttpServletResponse response) {
		JsonData jsonData = new JsonData();
		
		String password = request.getParameter("password");
		String oldPassword = request.getParameter("oldPassword");
		String userId = getCurrentSysUser().getId();
		try {
			sysUserService.resetPassword(userId, password, oldPassword);
			jsonData.setSuccess(true);
		} catch (ServiceException e) {
			e.printStackTrace();
			jsonData.setMessage(e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	/**
	 * 将密码改为初始密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresRoles("ROLE_ADMIN")
	@RequestMapping("/sysUser/initPassword")
	@ResponseBody
	public JsonData initPassword(HttpServletRequest request, HttpServletResponse response) {
		JsonData jsonData = new JsonData();
		String id = request.getParameter("id");
		try {
			sysUserService.updateByProperties("id", id, "password", new Sha256Hash(PropertiesUtils.getBasicValue("init_password")).toHex());
			jsonData.setSuccess(true);
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	/**
	 * 锁定账号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresRoles("ROLE_ADMIN")
	@RequestMapping("/sysUser/lock")
	@ResponseBody
	public JsonData lock(HttpServletRequest request, HttpServletResponse response) {
		JsonData jsonData = new JsonData();
		String id = request.getParameter("id");
		try {
			sysUserService.updateByProperties("id", id, "userStatus.id", UserStatus.LOCKED);;
			jsonData.setSuccess(true);
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	/**
	 * 解锁账号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresRoles("ROLE_ADMIN")
	@RequestMapping("/sysUser/unlock")
	@ResponseBody
	public JsonData unlock(HttpServletRequest request, HttpServletResponse response) {
		JsonData jsonData = new JsonData();
		String id = request.getParameter("id");
		try {
			sysUserService.updateByProperties("id", id, "userStatus.id", UserStatus.NORMAL);;
			jsonData.setSuccess(true);
		} catch(Exception e){
			e.printStackTrace();
			jsonData.setMessage("系统发生错误。");
		}
		return jsonData;
	}
	
	
	/**
	 * 分页查询所有用户
	 * @param userParam
	 * @param firstResult
	 * @param pageSize
	 * @return
	 */
	@RequiresRoles(value="ROLE_ADMIN")
	@RequestMapping("sysUser/getUsers")
	public ModelAndView getUsers(SysUser userParam){
		int firstResult = SystemContext.getOffset();
		int pageSize = SystemContext.getPageSize();
		
		PageView<SysUser> pageView = sysUserService.doPaginationQuery(userParam, firstResult, pageSize);
		return new ModelAndView("sysUser/user-list", "pageView", pageView); 
	}
}
