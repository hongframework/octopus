package com.hframework.graphdb.sec.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.graphdb.sec.domain.model.User;
import com.hframework.graphdb.sec.domain.model.User_Example;
import com.hframework.graphdb.sec.dao.UserMapper;
import com.hframework.graphdb.sec.service.interfaces.IUserSV;

@Service("iUserSV")
public class UserSVImpl  implements IUserSV {

	@Resource
	private UserMapper userMapper;
  


    /**
    * 创建用户
    * @param user
    * @return
    * @throws Exception
    */
    public int create(User user) throws Exception {
        return userMapper.insertSelective(user);
    }

    /**
    * 批量维护用户
    * @param users
    * @return
    * @throws Exception
    */
    public int batchOperate(User[] users) throws  Exception{
        int result = 0;
        if(users != null) {
            for (User user : users) {
                if(user.getUserId() == null) {
                    result += this.create(user);
                }else {
                    result += this.update(user);
                }
            }
        }
        return result;
    }

    /**
    * 更新用户
    * @param user
    * @return
    * @throws Exception
    */
    public int update(User user) throws  Exception {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
    * 通过查询对象更新用户
    * @param user
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(User user, User_Example example) throws  Exception {
        return userMapper.updateByExampleSelective(user, example);
    }

    /**
    * 删除用户
    * @param user
    * @return
    * @throws Exception
    */
    public int delete(User user) throws  Exception {
        return userMapper.deleteByPrimaryKey(user.getUserId());
    }

    /**
    * 删除用户
    * @param userId
    * @return
    * @throws Exception
    */
    public int delete(long userId) throws  Exception {
        return userMapper.deleteByPrimaryKey(userId);
    }

    /**
    * 查找所有用户
    * @return
    */
    public List<User> getUserAll()  throws  Exception {
        return userMapper.selectByExample(new User_Example());
    }

    /**
    * 通过用户ID查询用户
    * @param userId
    * @return
    * @throws Exception
    */
    public User getUserByPK(long userId)  throws  Exception {
        return userMapper.selectByPrimaryKey(userId);
    }


    /**
    * 通过MAP参数查询用户
    * @param params
    * @return
    * @throws Exception
    */
    public List<User> getUserListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询用户
    * @param example
    * @return
    * @throws Exception
    */
    public List<User> getUserListByExample(User_Example example) throws  Exception {
        return userMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询用户数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getUserCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询用户数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getUserCountByExample(User_Example example) throws  Exception {
        return userMapper.countByExample(example);
    }


  	//getter
 	
	public UserMapper getUserMapper(){
		return userMapper;
	}
	//setter
	public void setUserMapper(UserMapper userMapper){
    	this.userMapper = userMapper;
    }
}
