package com.bocs.special.service;

import java.util.List;

import com.bocs.special.model.Dict;

import core.service.Service;

/**
 * 字典的业务逻辑层的接口
 */
public interface DictService extends Service<Dict> {

	List<Dict> queryDictWithSubList(List<Dict> resultList);

}
