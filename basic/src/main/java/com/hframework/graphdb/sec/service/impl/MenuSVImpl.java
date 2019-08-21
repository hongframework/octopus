package com.hframework.graphdb.sec.service.impl;

import java.util.*;

import com.hframework.graphdb.sec.service.interfaces.IMenuSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.graphdb.sec.domain.model.Menu;
import com.hframework.graphdb.sec.domain.model.Menu_Example;
import com.hframework.graphdb.sec.dao.MenuMapper;

@Service("iMenuSV")
public class MenuSVImpl  implements IMenuSV {

	@Resource
	private MenuMapper menuMapper;
  


    /**
    * 创建菜单
    * @param menu
    * @return
    * @throws Exception
    */
    public int create(Menu menu) throws Exception {
        return menuMapper.insertSelective(menu);
    }

    /**
    * 批量维护菜单
    * @param menus
    * @return
    * @throws Exception
    */
    public int batchOperate(Menu[] menus) throws  Exception{
        int result = 0;
        if(menus != null) {
            for (Menu menu : menus) {
                if(menu.getMenuId() == null) {
                    result += this.create(menu);
                }else {
                    result += this.update(menu);
                }
            }
        }
        return result;
    }

    /**
    * 更新菜单
    * @param menu
    * @return
    * @throws Exception
    */
    public int update(Menu menu) throws  Exception {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
    * 通过查询对象更新菜单
    * @param menu
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Menu menu, Menu_Example example) throws  Exception {
        return menuMapper.updateByExampleSelective(menu, example);
    }

    /**
    * 删除菜单
    * @param menu
    * @return
    * @throws Exception
    */
    public int delete(Menu menu) throws  Exception {
        return menuMapper.deleteByPrimaryKey(menu.getMenuId());
    }

    /**
    * 删除菜单
    * @param menuId
    * @return
    * @throws Exception
    */
    public int delete(long menuId) throws  Exception {
        return menuMapper.deleteByPrimaryKey(menuId);
    }

    /**
    * 查找所有菜单
    * @return
    */
    public List<Menu> getMenuAll()  throws  Exception {
        return menuMapper.selectByExample(new Menu_Example());
    }

    /**
    * 通过菜单ID查询菜单
    * @param menuId
    * @return
    * @throws Exception
    */
    public Menu getMenuByPK(long menuId)  throws  Exception {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    /**
    * 通过父级菜单ID查询菜单树
    * @param menu
    * @return
    * @throws Exception
    */
    public Map<Long, List<Menu>> getMenuTreeByParentId(Menu menu, Menu_Example example)  throws  Exception {

        Map<Long, List<Menu>> result = new HashMap<Long, List<Menu>>();

        fillMenuTreeCascade(result, Lists.newArrayList(
                menu.getParentMenuId() == null ? -1 : menu.getParentMenuId() ), example);
        return result;
    }

    private void fillMenuTreeCascade(Map<Long, List<Menu>> result, List<Long> parentIds, Menu_Example templateExample)  throws Exception {
        if(parentIds.size() == 0) {
            return ;
        }
        if(templateExample == null) {
            templateExample = new Menu_Example();
        }
        Menu_Example example = ExampleUtils.clone(templateExample);
        if(example.getOredCriteria() == null || example.getOredCriteria().size() == 0) {
            example.createCriteria();
        }

        example.getOredCriteria().get(0).andParentMenuIdIn(parentIds);
        List<Menu> menus = menuMapper.selectByExample(example);
        if(menus == null || menus.size() == 0) {
            return;
        }

        if(parentIds.size() == 1) {
            result.put(parentIds.get(0), menus);
        }else {
            for (Menu menu : menus) {
                Long parentId = menu.getParentMenuId();
                if(!result.containsKey(parentId)) {
                    result.put(parentId, new ArrayList<Menu>());
                }
                result.get(parentId).add(menu);
            }
        }

        List subIds = new ArrayList();
        for (Menu menu : menus) {
            subIds.add(menu.getMenuId());
        }

        fillMenuTreeCascade(result,subIds, templateExample);
    }

    /**
    * 通过MAP参数查询菜单
    * @param params
    * @return
    * @throws Exception
    */
    public List<Menu> getMenuListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询菜单
    * @param example
    * @return
    * @throws Exception
    */
    public List<Menu> getMenuListByExample(Menu_Example example) throws  Exception {
        return menuMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询菜单数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getMenuCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询菜单数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getMenuCountByExample(Menu_Example example) throws  Exception {
        return menuMapper.countByExample(example);
    }


  	//getter
 	
	public MenuMapper getMenuMapper(){
		return menuMapper;
	}
	//setter
	public void setMenuMapper(MenuMapper menuMapper){
    	this.menuMapper = menuMapper;
    }
}
