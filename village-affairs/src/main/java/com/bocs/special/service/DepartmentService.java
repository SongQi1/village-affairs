package com.bocs.special.service;

import java.util.List;

import com.bocs.special.model.Department;

import core.service.Service;

/**
 * 部门的业务逻辑层的接口
 */
public interface DepartmentService extends Service<Department> {

	// 获取包含部门中文名称的列表
	List<Department> queryDepartmentCnList(List<Department> resultList);

}
