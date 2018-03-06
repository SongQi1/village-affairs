package com.bocs.special.dao.impl;

import org.springframework.stereotype.Repository;

import com.bocs.special.dao.ReplyInfoDao;
import com.bocs.special.model.ReplyInfo;

import core.dao.BaseDao;

@Repository(value="replyInfoDao")
public class ReplyInfoDaoImpl extends BaseDao<ReplyInfo> implements ReplyInfoDao{

}
