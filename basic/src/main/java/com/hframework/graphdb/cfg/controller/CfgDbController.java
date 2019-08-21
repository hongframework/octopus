package com.hframework.graphdb.cfg.controller;

import com.hframework.beans.controller.Pagination;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.ExampleUtils;
import com.hframework.beans.exceptions.BusinessException;
import com.hframework.graphdb.cfg.domain.model.CfgDb;
import com.hframework.graphdb.cfg.domain.model.CfgDb_Example;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDbSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import com.hframework.web.ControllerHelper;

@Controller
@RequestMapping(value = "/cfg/cfgDb")
public class CfgDbController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgDbController.class);

	@Resource
	private ICfgDbSV iCfgDbSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示数据库列表
     * @param cfgDb
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgDb") CfgDb cfgDb,
                           @ModelAttribute("example") CfgDb_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgDb, example, pagination);
        try{
            ExampleUtils.parseExample(cfgDb, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgDb> list = iCfgDbSV.getCfgDbListByExample(example);
            pagination.setTotalCount(iCfgDbSV.getCfgDbCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示数据库明细
     * @param cfgDb
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgDb") CfgDb cfgDb){
        logger.debug("request : {},{}", cfgDb.getId(), cfgDb);
        try{
            CfgDb result = null;
            if(cfgDb.getId() != null) {
                result = iCfgDbSV.getCfgDbByPK(cfgDb.getId());
            }else {
                CfgDb_Example example = ExampleUtils.parseExample(cfgDb, CfgDb_Example.class);
                List<CfgDb> list = iCfgDbSV.getCfgDbListByExample(example);
                if(list != null && list.size() == 1) {
                    result = list.get(0);
                }
            }

            if(result != null) {
                return ResultData.success(result);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
    * 搜索一个数据库
    * @param  cfgDb
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgDb") CfgDb  cfgDb){
        logger.debug("request : {}",  cfgDb);
        try{
            CfgDb result = null;
            if(cfgDb.getId() != null) {
                result =  iCfgDbSV.getCfgDbByPK(cfgDb.getId());
            }else {
                CfgDb_Example example = ExampleUtils.parseExample( cfgDb, CfgDb_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgDb> list =  iCfgDbSV.getCfgDbListByExample(example);
                if(list != null && list.size() > 0) {
                    result = list.get(0);
                }
            }

            if(result != null) {
                return ResultData.success(result);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
    * 创建数据库
    * @param cfgDb
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgDb") CfgDb cfgDb) {
        logger.debug("request : {}", cfgDb);
        try {
            ControllerHelper.setDefaultValue(cfgDb, "id");
            int result = iCfgDbSV.create(cfgDb);
            if(result > 0) {
                return ResultData.success(cfgDb);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 批量维护数据库
    * @param cfgDbs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgDb[] cfgDbs) {
        logger.debug("request : {}", cfgDbs);

        try {
            ControllerHelper.setDefaultValue(cfgDbs, "id");
            ControllerHelper.reorderProperty(cfgDbs);

            int result = iCfgDbSV.batchOperate(cfgDbs);
            if(result > 0) {
                return ResultData.success(cfgDbs);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新数据库
    * @param cfgDb
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgDb") CfgDb cfgDb) {
        logger.debug("request : {}", cfgDb);
        try {
            ControllerHelper.setDefaultValue(cfgDb, "id");
            int result = iCfgDbSV.update(cfgDb);
            if(result > 0) {
                return ResultData.success(cfgDb);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 创建或更新数据库
    * @param cfgDb
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgDb") CfgDb cfgDb) {
        logger.debug("request : {}", cfgDb);
        try {
            ControllerHelper.setDefaultValue(cfgDb, "id");
            int result = iCfgDbSV.batchOperate(new CfgDb[]{cfgDb});
            if(result > 0) {
                return ResultData.success(cfgDb);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除数据库
    * @param cfgDb
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgDb") CfgDb cfgDb) {
        logger.debug("request : {}", cfgDb);

        try {
            ControllerHelper.setDefaultValue(cfgDb, "id");
            int result = iCfgDbSV.delete(cfgDb);
            if(result > 0) {
                return ResultData.success(cfgDb);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }
  	//getter
 	
	public ICfgDbSV getICfgDbSV(){
		return iCfgDbSV;
	}
	//setter
	public void setICfgDbSV(ICfgDbSV iCfgDbSV){
    	this.iCfgDbSV = iCfgDbSV;
    }
}
