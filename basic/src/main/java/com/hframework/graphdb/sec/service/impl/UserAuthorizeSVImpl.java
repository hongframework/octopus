package com.hframework.graphdb.sec.service.impl;

import java.util.*;

import com.hframework.graphdb.sec.service.interfaces.IUserAuthorizeSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.graphdb.sec.domain.model.UserAuthorize;
import com.hframework.graphdb.sec.domain.model.UserAuthorize_Example;
import com.hframework.graphdb.sec.dao.UserAuthorizeMapper;

@Service("iUserAuthorizeSV")
public class UserAuthorizeSVImpl  implements IUserAuthorizeSV {

	@Resource
	private UserAuthorizeMapper userAuthorizeMapper;
  


    /**
    * 创建用户授权
    * @param userAuthorize
    * @return
    * @throws Exception
    */
    public int create(UserAuthorize userAuthorize) throws Exception {
        return userAuthorizeMapper.insertSelective(userAuthorize);
    }

    /**
    * 批量维护用户授权
    * @param userAuthorizes
    * @return
    * @throws Exception
    */
    public int batchOperate(UserAuthorize[] userAuthorizes) throws  Exception{
        int result = 0;
        if(userAuthorizes != null) {
            for (UserAuthorize userAuthorize : userAuthorizes) {
                if(userAuthorize.getUserAuthorizeId() == null) {
                    result += this.create(userAuthorize);
                }else {
                    result += this.update(userAuthorize);
                }
            }
        }
        return result;
    }

    /**
    * 更新用户授权
    * @param userAuthorize
    * @return
    * @throws Exception
    */
    public int update(UserAuthorize userAuthorize) throws  Exception {
        return userAuthorizeMapper.updateByPrimaryKeySelective(userAuthorize);
    }

    /**
    * 通过查询对象更新用户授权
    * @param userAuthorize
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(UserAuthorize userAuthorize, UserAuthorize_Example example) throws  Exception {
        return userAuthorizeMapper.updateByExampleSelective(userAuthorize, example);
    }

    /**
    * 删除用户授权
    * @param userAuthorize
    * @return
    * @throws Exception
    */
    public int delete(UserAuthorize userAuthorize) throws  Exception {
        return userAuthorizeMapper.deleteByPrimaryKey(userAuthorize.getUserAuthorizeId());
    }

    /**
    * 删除用户授权
    * @param userAuthorizeId
    * @return
    * @throws Exception
    */
    public int delete(long userAuthorizeId) throws  Exception {
        return userAuthorizeMapper.deleteByPrimaryKey(userAuthorizeId);
    }

    /**
    * 查找所有用户授权
    * @return
    */
    public List<UserAuthorize> getUserAuthorizeAll()  throws  Exception {
        return userAuthorizeMapper.selectByExample(new UserAuthorize_Example());
    }

    /**
    * 通过用户授权ID查询用户授权
    * @param userAuthorizeId
    * @return
    * @throws Exception
    */
    public UserAuthorize getUserAuthorizeByPK(long userAuthorizeId)  throws  Exception {
        return userAuthorizeMapper.selectByPrimaryKey(userAuthorizeId);
    }


    /**
    * 通过MAP参数查询用户授权
    * @param params
    * @return
    * @throws Exception
    */
    public List<UserAuthorize> getUserAuthorizeListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询用户授权
    * @param example
    * @return
    * @throws Exception
    */
    public List<UserAuthorize> getUserAuthorizeListByExample(UserAuthorize_Example example) throws  Exception {
        return userAuthorizeMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询用户授权数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getUserAuthorizeCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询用户授权数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getUserAuthorizeCountByExample(UserAuthorize_Example example) throws  Exception {
        return userAuthorizeMapper.countByExample(example);
    }


  	//getter
 	
	public UserAuthorizeMapper getUserAuthorizeMapper(){
		return userAuthorizeMapper;
	}
	//setter
	public void setUserAuthorizeMapper(UserAuthorizeMapper userAuthorizeMapper){
    	this.userAuthorizeMapper = userAuthorizeMapper;
    }
}
