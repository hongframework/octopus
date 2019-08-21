package com.hframework.graphdb.cfg.controller;

import com.hframework.beans.controller.Pagination;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.ExampleUtils;
import com.hframework.beans.exceptions.BusinessException;
import com.hframework.graphdb.cfg.domain.model.CfgRelat;
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
import com.hframework.graphdb.cfg.domain.model.CfgRelat_Example;
import com.hframework.graphdb.cfg.service.interfaces.ICfgRelatSV;

@Controller
@RequestMapping(value = "/cfg/cfgRelat")
public class CfgRelatController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgRelatController.class);

	@Resource
	private ICfgRelatSV iCfgRelatSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示数据关系列表
     * @param cfgRelat
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgRelat") CfgRelat cfgRelat,
                                      @ModelAttribute("example") CfgRelat_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgRelat, example, pagination);
        try{
            ExampleUtils.parseExample(cfgRelat, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgRelat> list = iCfgRelatSV.getCfgRelatListByExample(example);
            pagination.setTotalCount(iCfgRelatSV.getCfgRelatCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示数据关系明细
     * @param cfgRelat
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgRelat") CfgRelat cfgRelat){
        logger.debug("request : {},{}", cfgRelat.getId(), cfgRelat);
        try{
            CfgRelat result = null;
            if(cfgRelat.getId() != null) {
                result = iCfgRelatSV.getCfgRelatByPK(cfgRelat.getId());
            }else {
                CfgRelat_Example example = ExampleUtils.parseExample(cfgRelat, CfgRelat_Example.class);
                List<CfgRelat> list = iCfgRelatSV.getCfgRelatListByExample(example);
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
    * 搜索一个数据关系
    * @param  cfgRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgRelat") CfgRelat  cfgRelat){
        logger.debug("request : {}",  cfgRelat);
        try{
            CfgRelat result = null;
            if(cfgRelat.getId() != null) {
                result =  iCfgRelatSV.getCfgRelatByPK(cfgRelat.getId());
            }else {
                CfgRelat_Example example = ExampleUtils.parseExample( cfgRelat, CfgRelat_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgRelat> list =  iCfgRelatSV.getCfgRelatListByExample(example);
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
    * 创建数据关系
    * @param cfgRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgRelat") CfgRelat cfgRelat) {
        logger.debug("request : {}", cfgRelat);
        try {
            ControllerHelper.setDefaultValue(cfgRelat, "id");
            int result = iCfgRelatSV.create(cfgRelat);
            if(result > 0) {
                return ResultData.success(cfgRelat);
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
    * 批量维护数据关系
    * @param cfgRelats
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgRelat[] cfgRelats) {
        logger.debug("request : {}", cfgRelats);

        try {
            ControllerHelper.setDefaultValue(cfgRelats, "id");
            ControllerHelper.reorderProperty(cfgRelats);

            int result = iCfgRelatSV.batchOperate(cfgRelats);
            if(result > 0) {
                return ResultData.success(cfgRelats);
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
    * 更新数据关系
    * @param cfgRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgRelat") CfgRelat cfgRelat) {
        logger.debug("request : {}", cfgRelat);
        try {
            ControllerHelper.setDefaultValue(cfgRelat, "id");
            int result = iCfgRelatSV.update(cfgRelat);
            if(result > 0) {
                return ResultData.success(cfgRelat);
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
    * 创建或更新数据关系
    * @param cfgRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgRelat") CfgRelat cfgRelat) {
        logger.debug("request : {}", cfgRelat);
        try {
            ControllerHelper.setDefaultValue(cfgRelat, "id");
            int result = iCfgRelatSV.batchOperate(new CfgRelat[]{cfgRelat});
            if(result > 0) {
                return ResultData.success(cfgRelat);
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
    * 删除数据关系
    * @param cfgRelat
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgRelat") CfgRelat cfgRelat) {
        logger.debug("request : {}", cfgRelat);

        try {
            ControllerHelper.setDefaultValue(cfgRelat, "id");
            int result = iCfgRelatSV.delete(cfgRelat);
            if(result > 0) {
                return ResultData.success(cfgRelat);
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
 	
	public ICfgRelatSV getICfgRelatSV(){
		return iCfgRelatSV;
	}
	//setter
	public void setICfgRelatSV(ICfgRelatSV iCfgRelatSV){
    	this.iCfgRelatSV = iCfgRelatSV;
    }
}
