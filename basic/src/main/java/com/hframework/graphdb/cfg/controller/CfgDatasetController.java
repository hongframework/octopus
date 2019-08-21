package com.hframework.graphdb.cfg.controller;

import com.hframework.beans.controller.Pagination;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.ExampleUtils;
import com.hframework.beans.exceptions.BusinessException;
import com.hframework.graphdb.cfg.domain.model.CfgDataset;
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
import com.hframework.graphdb.cfg.domain.model.CfgDataset_Example;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDatasetSV;

@Controller
@RequestMapping(value = "/cfg/cfgDataset")
public class CfgDatasetController   {
    private static final Logger logger = LoggerFactory.getLogger(CfgDatasetController.class);

	@Resource
	private ICfgDatasetSV iCfgDatasetSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示数据集列表
     * @param cfgDataset
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("cfgDataset") CfgDataset cfgDataset,
                                      @ModelAttribute("example") CfgDataset_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", cfgDataset, example, pagination);
        try{
            ExampleUtils.parseExample(cfgDataset, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< CfgDataset> list = iCfgDatasetSV.getCfgDatasetListByExample(example);
            pagination.setTotalCount(iCfgDatasetSV.getCfgDatasetCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示数据集明细
     * @param cfgDataset
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("cfgDataset") CfgDataset cfgDataset){
        logger.debug("request : {},{}", cfgDataset.getId(), cfgDataset);
        try{
            CfgDataset result = null;
            if(cfgDataset.getId() != null) {
                result = iCfgDatasetSV.getCfgDatasetByPK(cfgDataset.getId());
            }else {
                CfgDataset_Example example = ExampleUtils.parseExample(cfgDataset, CfgDataset_Example.class);
                List<CfgDataset> list = iCfgDatasetSV.getCfgDatasetListByExample(example);
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
    * 搜索一个数据集
    * @param  cfgDataset
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" cfgDataset") CfgDataset  cfgDataset){
        logger.debug("request : {}",  cfgDataset);
        try{
            CfgDataset result = null;
            if(cfgDataset.getId() != null) {
                result =  iCfgDatasetSV.getCfgDatasetByPK(cfgDataset.getId());
            }else {
                CfgDataset_Example example = ExampleUtils.parseExample( cfgDataset, CfgDataset_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<CfgDataset> list =  iCfgDatasetSV.getCfgDatasetListByExample(example);
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
    * 创建数据集
    * @param cfgDataset
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("cfgDataset") CfgDataset cfgDataset) {
        logger.debug("request : {}", cfgDataset);
        try {
            ControllerHelper.setDefaultValue(cfgDataset, "id");
            int result = iCfgDatasetSV.create(cfgDataset);
            if(result > 0) {
                return ResultData.success(cfgDataset);
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
    * 批量维护数据集
    * @param cfgDatasets
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody CfgDataset[] cfgDatasets) {
        logger.debug("request : {}", cfgDatasets);

        try {
            ControllerHelper.setDefaultValue(cfgDatasets, "id");
            ControllerHelper.reorderProperty(cfgDatasets);

            int result = iCfgDatasetSV.batchOperate(cfgDatasets);
            if(result > 0) {
                return ResultData.success(cfgDatasets);
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
    * 更新数据集
    * @param cfgDataset
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("cfgDataset") CfgDataset cfgDataset) {
        logger.debug("request : {}", cfgDataset);
        try {
            ControllerHelper.setDefaultValue(cfgDataset, "id");
            int result = iCfgDatasetSV.update(cfgDataset);
            if(result > 0) {
                return ResultData.success(cfgDataset);
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
    * 创建或更新数据集
    * @param cfgDataset
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("cfgDataset") CfgDataset cfgDataset) {
        logger.debug("request : {}", cfgDataset);
        try {
            ControllerHelper.setDefaultValue(cfgDataset, "id");
            int result = iCfgDatasetSV.batchOperate(new CfgDataset[]{cfgDataset});
            if(result > 0) {
                return ResultData.success(cfgDataset);
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
    * 删除数据集
    * @param cfgDataset
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("cfgDataset") CfgDataset cfgDataset) {
        logger.debug("request : {}", cfgDataset);

        try {
            ControllerHelper.setDefaultValue(cfgDataset, "id");
            int result = iCfgDatasetSV.delete(cfgDataset);
            if(result > 0) {
                return ResultData.success(cfgDataset);
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
 	
	public ICfgDatasetSV getICfgDatasetSV(){
		return iCfgDatasetSV;
	}
	//setter
	public void setICfgDatasetSV(ICfgDatasetSV iCfgDatasetSV){
    	this.iCfgDatasetSV = iCfgDatasetSV;
    }
}
