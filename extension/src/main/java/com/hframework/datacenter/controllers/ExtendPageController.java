package com.hframework.datacenter.controllers;

import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.graphdb.cfg.domain.model.CfgDataset;
import com.hframework.graphdb.cfg.domain.model.CfgDataset_Example;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDatasetSV;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ExtendPageController {

//    private static String table_vm =  "请重新上传一下View内容！";
//    private static String data_vm = "请重新上传一下View内容！";



    @RequestMapping("/extend/table_graph_init.json")
    @ResponseBody
    public ResultData table_graph_init(ModelAndView mav){

        try {

            mav.addObject("vm_content", table_vm);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.success();
    }

    @RequestMapping("/extend/table_vm_upload.json")
    @ResponseBody
    public ResultData table_vm_upload(ModelAndView mav){

        try {

            mav.addObject("vm_content", table_vm);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.success();
    }

    @RequestMapping("/extend/data_graph_init.json")
    @ResponseBody
    public ResultData data_graph_init(ModelAndView mav){

        try {

            mav.addObject("vm_content", data_vm);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.success();
    }

    @RequestMapping("/extend/data_vm_upload.json")
    @ResponseBody
    public ResultData data_vm_upload(ModelAndView mav){

        try {

            mav.addObject("vm_content", data_vm);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.success();
    }

    @RequestMapping("/extend/vm_upload.json")
    @ResponseBody
    public ResultData vm_upload(int type, String vm){

        if(type == 1) {
            table_vm = vm;
        }else {
            data_vm = vm;
        }
        return ResultData.success();
    }

    @RequestMapping("/component_setting.json")
    @ResponseBody
    public ResultData component_setting(HttpServletRequest request){

        String referer = request.getHeader("Referer");
        String referPageCode = referer.substring(referer.lastIndexOf("/") + 1, referer.indexOf(".html"));
        String dbId = referPageCode.substring(0, referPageCode.indexOf("_"));
        String tableName = referPageCode.substring(referPageCode.indexOf("_") + 1, referPageCode.lastIndexOf("_"));
        String referPageParameter = referer.contains("?")? referer.substring(referer.indexOf("?") + 1) : "";
        referPageParameter = referPageParameter.replaceAll("&isPop=true", "").replaceAll("isPop=true", "");
        String component = request.getParameter("component");
        CfgDataset_Example example = new CfgDataset_Example();
        example.createCriteria().andDbIdEqualTo(Long.valueOf(dbId)).andTableNameEqualTo(tableName);
        try {
            List<CfgDataset> cfgDatasets = ServiceFactory.getService(ICfgDatasetSV.class).getCfgDatasetListByExample(example);
            CfgDataset dataset = cfgDatasets.get(0);
            String type = null;
            if("cForm".equals(component)) {
                type = "create";
            }else if("eForm".equals(component)) {
                type = "update";
            }else if("qList".equals(component)) {
                type = "list";
            }else if("dForm".equals(component)) {
                type = "list";
            }else if("qForm".equals(component)) {
                type = "cond";
            }

            Map<String, String> map = new HashMap<>();
            map.put("url",  "/cfg/" + type + "_meta_xeditor.html?id=" + dataset.getId() + "&r_code=" + referPageCode + "&r_param=" + referPageParameter +  "&isPop=true");
            return ResultData.success(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        Long dbId = Long.valueOf(request.getParameter("dbId"));
//        String nodeId = request.getParameter("nodeId");
//        String dataId = request.getParameter("dataId");
//
//        GraphDBRegistry.GraphNet net = RuntimeDataUtils.getRuntimeDataGraphNet(dbId, nodeId, dataId);
//        ;
//        JSONObject data = new JSONObject();
//        data.put("hot_node_id", net.getHotNode().getId());
//        data.put("net", net.getJson());

        return ResultData.error(ResultCode.ERROR);
    }


    private static String table_vm =  "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>力导图</title>\n" +
            "    <style>::-webkit-scrollbar{display:none;}html,body{overflow:hidden;margin:0;}</style>\n" +
            "    <link rel=\"stylesheet\" href=\"https://gw.alipayobjects.com/os/s/prod/antv/assets/dist/3.0.0/demo-7dcb9.css\"/>\n" +
            "    <style type=\"text/css\">\n" +
            "    .active {background: #04A2EA;color:#fff;}\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"mountNode\" style=\"text-align:center;margin:20px 0;\">\n" +
            "    <button id=\"default\" class=\"active\">查看模式</button>\n" +
            "    <button id=\"edit\"> 编辑模式</button>\n" +
            "</div>\n" +
            "<script>/*Fixing iframe window.innerHeight 0 issue in Safari*/document.body.clientHeight;</script>\n" +
            "<script src=\"https://gw.alipayobjects.com/os/antv/pkg/_antv.g6-3.0.2/build/g6.js\"></script>\n" +
            "<script src=\"https://gw.alipayobjects.com/os/antv/assets/lib/jquery-3.2.1.min.js\"></script>\n" +
            "<script src=\"https://gw.alipayobjects.com/os/antv/assets/lib/d3-4.13.0.min.js\"></script>\n" +
            "<script src=\"/static/js/artDialog/dialog.v1.js\" type=\"text/javascript\"></script>\n" +
            "<script src=\"/static/js/validator/validator.v1.js\" type=\"text/javascript\"></script>\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/js/artDialog/dialog.v1.css\" />\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/js/validator/validator.v1.1.css\" />\n" +
            "<script>\n" +
            "\n" +
            "    var simulation = d3.forceSimulation().force(\"link\", d3.forceLink().distance(200).id(function(d) {\n" +
            "        return d.id;\n" +
            "    }).strength(0.3)).force(\"charge\", d3.forceManyBody()).force(\"center\", d3.forceCenter(window.innerWidth / 2, window.innerHeight / 2));\n" +
            "    var dblclickStr = null;\n" +
            "    var beginState = null;\n" +
            "    function getQueryString(name) { \n" +
            "        var reg = new RegExp(\"(^|&)\" + name + \"=([^&]*)(&|$)\", \"i\"); \n" +
            "        var r = window.location.search.substr(1).match(reg); \n" +
            "        if (r != null) return unescape(r[2]); \n" +
            "        return null; \n" +
            "    } \n" +
            "\n" +
            "    // 封装点击添加边的交互\n" +
            "    G6.registerBehavior('click-add-edge', {\n" +
            "        getEvents() {\n" +
            "            return {\n" +
            "                'node:click': 'onClick' ,\n" +
            "                'mousemove': 'onMousemove',\n" +
            "                'node:contextmenu' : \"contextmenu\"\n" +
            "            };\n" +
            "        },\n" +
            "        onClick(ev) {\n" +
            "\n" +
            "            const node = ev.item;\n" +
            "            const graph = this.graph;\n" +
            "            const point = {x: ev.x, y: ev.y};\n" +
            "            var model = node.getModel();\n" +
            "            var that = this;\n" +
            "            // 如果在添加边的过程中，再次点击另一个节点，结束边的添加\n" +
            "\n" +
            "            // dblclickStr = setTimeout(function(){\n" +
            "                if (this.addingEdge && this.edge) {\n" +
            "                // console.log(point);\n" +
            "                //console.log(begin);\n" +
            "                if(begin == model.id){\n" +
            "                    return;\n" +
            "                }\n" +
            "                dialog({\n" +
            "                    title: '表关联',\n" +
            "                    content: begin + \"确定要和\" + model.id +\"建立关联吗\",\n" +
            "                    ok: function () {\n" +
            "                        $.ajax({\n" +
            "                            url : \"/addingEdge\",\n" +
            "                            type: \"post\",\n" +
            "                            dataType: \"json\",\n" +
            "                            success : function(){\n" +
            "                                alert(\"提交成功\");\n" +
            "                            }\n" +
            "                        });\n" +
            "                        graph.updateItem(that.edge, {\n" +
            "                            target: model.id\n" +
            "                        });\n" +
            "                        that.edge = null;\n" +
            "                        that.addingEdge = false;\n" +
            "                        //return false;\n" +
            "                    },\n" +
            "                    cancel: function () {\n" +
            "                        //console.log(that.edge);\n" +
            "                        graph.remove(that.edge);\n" +
            "                        that.edge = null;\n" +
            "                        that.addingEdge = false;\n" +
            "                     }\n" +
            "                }).showModal();\n" +
            "            } else {\n" +
            "                // 点击节点，触发增加边\n" +
            "                this.edge = graph.addItem('edge', {\n" +
            "                    source: model.id,\n" +
            "                    target: point\n" +
            "                });\n" +
            "                begin = model.id;\n" +
            "                //console.log(point);\n" +
            "                this.addingEdge = true;\n" +
            "                //console.log(222);\n" +
            "            }\n" +
            "            // },300);\n" +
            "\n" +
            "            //clearTimeout(dblclickStr);\n" +
            "        },\n" +
            "        onMousemove(ev) {\n" +
            "            const point = {x: ev.x, y: ev.y};\n" +
            "            if (this.addingEdge && this.edge) {\n" +
            "                // 增加边的过程中，移动时边跟着移动\n" +
            "                this.graph.updateItem(this.edge, {\n" +
            "                    target: point\n" +
            "                });\n" +
            "            }\n" +
            "        }\n" +
            "        \n" +
            "    });\n" +
            "\n" +
            "    G6.registerBehavior('drag-node-diy', {\n" +
            "        getEvents() {\n" +
            "            return {\n" +
            "                'node:dragstart': 'dragstart' ,\n" +
            "                'node:drag': 'drag',\n" +
            "                'node:dragend': 'dragend'\n" +
            "            };\n" +
            "        },\n" +
            "        dragstart(ev) {\n" +
            "            simulation.alphaTarget(0.3).restart();\n" +
            "            refreshPosition(ev);\n" +
            "        },\n" +
            "        drag(ev) {\n" +
            "            refreshPosition(ev);\n" +
            "        },\n" +
            "        dragend(ev) {\n" +
            "            simulation.alphaTarget(0);\n" +
            "            refreshPosition(ev);\n" +
            "\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "    G6.registerBehavior('drag-canvas', {\n" +
            "        getEvents() {\n" +
            "            return {\n" +
            "                'node:contextmenu' : \"contextmenu\"\n" +
            "            };\n" +
            "        },\n" +
            "        contextmenu(ev){\n" +
            "            !!this.edge && graph.remove(this.edge);\n" +
            "             //clearTimeout(dblclickStr);\n" +
            "            //!!this.edge && graph.remove(this.edge);\n" +
            "            const node = ev.item;\n" +
            "            const graph = this.graph;\n" +
            "            const point = {x: ev.x, y: ev.y};\n" +
            "            var model = node.getModel();\n" +
            "            console.log(model);\n" +
            "            var that = this;\n" +
            "            var host = \"http://\" + window.location.host;\n" +
            "            //var host = 'http://hframework:15469';\n" +
            "            var actionStr = 'mgr';\n" +
            "            \n" +
            "            \n" +
            "            console.log(host + '/runtime/' + getQueryString(\"dbId\") +'_'+ model.id +'_'+ actionStr + '.html?isPop=true');\n" +
            "            dialog({\n" +
            "                title: '表关联',\n" +
            "                content: '<iframe style=\"width:100%;height:100%;\" src=\"'+ host + '/runtime/' + getQueryString(\"dbId\") +'_'+ model.id +'_'+ actionStr + '.html?isPop=true'  +'\"></iframe>',\n" +
            "                width: $(window).width() - 100,\n" +
            "                height: $(window).height() - 200,\n" +
            "                cancelDisplay : true,\n" +
            "                ok: function () {\n" +
            "                    //return false;\n" +
            "                }\n" +
            "            }).showModal();\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "    G6.registerBehavior('activate-node-relations', {\n" +
            "        getEvents() {\n" +
            "            return {\n" +
            "                'node:mouseenter': 'mouseenter' ,\n" +
            "                'node:mouseleave': 'mouseleave'\n" +
            "            };\n" +
            "        },\n" +
            "        mouseenter(e) {\n" +
            "            var item = e.item;\n" +
            "            this.graph.setAutoPaint(false);\n" +
            "            this.graph.getNodes().forEach(function(node) {\n" +
            "                this.graph.clearItemStates(node);\n" +
            "                this.graph.setItemState(node, 'dark', true);\n" +
            "            });\n" +
            "            this.graph.setItemState(item, 'dark', false);\n" +
            "            this.graph.setItemState(item, 'highlight', true);\n" +
            "            this.graph.getEdges().forEach(function(edge) {\n" +
            "                if (edge.getSource() === item) {\n" +
            "                    this.graph.setItemState(edge.getTarget(), 'dark', false);\n" +
            "                    this.graph.setItemState(edge.getTarget(), 'highlight', true);\n" +
            "                    this.graph.setItemState(edge, 'highlight', true);\n" +
            "                    edge.toFront();\n" +
            "                } else if (edge.getTarget() === item) {\n" +
            "                    this.graph.setItemState(edge.getSource(), 'dark', false);\n" +
            "                    this.graph.setItemState(edge.getSource(), 'highlight', true);\n" +
            "                    this.graph.setItemState(edge, 'highlight', true);\n" +
            "                    edge.toFront();\n" +
            "                } else {\n" +
            "                    this.graph.setItemState(edge, 'highlight', false);\n" +
            "                }\n" +
            "            });\n" +
            "            this.graph.paint();\n" +
            "            this.graph.setAutoPaint(true);\n" +
            "        },\n" +
            "        mouseleave(e) {\n" +
            "            this.graph.setAutoPaint(false);\n" +
            "            this.graph.getNodes().forEach(function(node) {\n" +
            "                this.graph.clearItemStates(node);\n" +
            "            });\n" +
            "            this.graph.getEdges().forEach(function(edge) {\n" +
            "                this.graph.clearItemStates(edge);\n" +
            "            });\n" +
            "            this.graph.paint();\n" +
            "            this.graph.setAutoPaint(true);\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "\n" +
            "    var graph = new G6.Graph({\n" +
            "        container: 'mountNode',\n" +
            "        width: window.innerWidth,\n" +
            "        height: window.innerHeight,\n" +
            "        fitViewPadding:100,\n" +
            "        autoPaint: true,\n" +
            "        defaultNode: {\n" +
            "            size: [25, 25],\n" +
            "            color: 'steelblue',\n" +
            "            labelCfg: {\n" +
            "                position: \"bottom\",\n" +
            "                offset: 5\n" +
            "            }\n" +
            "        },\n" +
            "        defaultEdge: {\n" +
            "            size: 3,\n" +
            "            color: '#e2e2e2',\n" +
            "        },\n" +
            "        nodeStyle: {\n" +
            "            default: {\n" +
            "                lineWidth: 1,\n" +
            "                fill: '#f54545'\n" +
            "            },\n" +
            "            highlight: {\n" +
            "                opacity: 1\n" +
            "            },\n" +
            "            dark: {\n" +
            "                opacity: 0.4\n" +
            "            }\n" +
            "        },\n" +
            "        edgeStyle: {\n" +
            "            default: {\n" +
            "                endArrow: true,\n" +
            "              //  lineWidth: 2,\n" +
            "                stroke: '#ff8547',\n" +
            "            },\n" +
            "            highlight: {\n" +
            "                stroke: '#666'\n" +
            "            }\n" +
            "        },\n" +
            "        modes: {\n" +
            "\n" +
            "            default: ['drag-canvas', 'drag-node-diy', 'activate-node-relations',{\n" +
            "\n" +
            "                type: 'zoom-canvas',\n" +
            "\n" +
            "                sensitivity: 5\n" +
            "\n" +
            "            }],\n" +
            "\n" +
            "            edit: ['drag-canvas',  'click-add-edge',{\n" +
            "\n" +
            "                type: 'zoom-canvas',\n" +
            "\n" +
            "                sensitivity: 5\n" +
            "\n" +
            "            }]\n" +
            "\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "\n" +
            "    // graph.setAutoPaint(true);\n" +
            "\n" +
            "\n" +
            "    function refreshPosition(e) {\n" +
            "        e.item.get('model').x = e.x;\n" +
            "        e.item.get('model').y = e.y;\n" +
            "        graph.refreshPositions();\n" +
            "    }\n" +
            "    \n" +
            "   \n" +
            "\n" +
            "\n" +
            "    // $.getJSON('./assets/data/relations.json', function(data) {\n" +
            "    //     graph.data({\n" +
            "    //         nodes: data.nodes,\n" +
            "    //         edges: data.edges.map(function(edge, i) {\n" +
            "    //             edge.id = 'edge' + i;\n" +
            "    //             return Object.assign({}, edge);\n" +
            "    //         })\n" +
            "    //     });\n" +
            "    //     simulation.nodes(data.nodes).on(\"tick\", ticked);\n" +
            "    //     simulation.force(\"link\").links(data.edges);\n" +
            "\n" +
            "    //     graph.render();\n" +
            "    //     // graph.fitView();\n" +
            "    //     function ticked() {\n" +
            "    //         if (!graph.get('data')) {\n" +
            "    //             graph.data(data);\n" +
            "    //             graph.render();\n" +
            "    //             // graph.fitView();\n" +
            "    //         } else {\n" +
            "    //             graph.refreshPositions();\n" +
            "    //         }\n" +
            "    //         graph.paint();\n" +
            "    //     }\n" +
            "    // });\n" +
            "\n" +
            "    $(function(){\n" +
            "             \n" +
            "             $.ajax({\n" +
            "                 url : \"/graph/api/table/init.json?dbId=\" + getQueryString(\"dbId\"),\n" +
            "                 type: \"post\",\n" +
            "                 dataType: \"json\",\n" +
            "                 success : function(dataObj){\n" +
            "                     if(dataObj.data){\n" +
            "                        var data = dataObj.data;\n" +
            "                        graph.data({\n" +
            "                            nodes: data.nodes,\n" +
            "                            edges: data.edges.map(function(edge, i) {\n" +
            "                                edge.id = 'edge' + i;\n" +
            "                                return Object.assign({}, edge);\n" +
            "                            })\n" +
            "                        });\n" +
            "                        simulation.nodes(data.nodes).on(\"tick\", ticked);\n" +
            "                        simulation.force(\"link\").links(data.edges);\n" +
            "\n" +
            "                        graph.render();\n" +
            "                        // graph.fitView();\n" +
            "                        function ticked() {\n" +
            "                            if (!graph.get('data')) {\n" +
            "                                graph.data(data);\n" +
            "                                graph.render();\n" +
            "                                // graph.fitView();\n" +
            "                            } else {\n" +
            "                                graph.refreshPositions();\n" +
            "                            }\n" +
            "                            graph.paint();\n" +
            "                        }\n" +
            "                     }\n" +
            "                 }\n" +
            "             });\n" +
            "             \n" +
            "        });\n" +
            "\n" +
            "    $(window).resize(function () {\n" +
            "       window.location.reload();\n" +
            "    });\n" +
            "\n" +
            "    // graph.setMode('edit');\n" +
            "    $(\"#mountNode\").on(\"click\", \"button\", function(){\n" +
            "        $(this).addClass('active').siblings().removeClass('active');\n" +
            "\n" +
            "        graph.setMode($(this).attr(\"id\"));\n" +
            "    });\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>\n";
    private static String data_vm = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>查询</title>\n" +
            "    <style>::-webkit-scrollbar{display:none;}html,body{overflow:hidden;margin:0;}</style>\n" +
            "    <link rel=\"stylesheet\" href=\"https://gw.alipayobjects.com/os/s/prod/antv/assets/dist/3.0.0/demo-7dcb9.css\"/>\n" +
            "    <style type=\"text/css\">\n" +
            "    .active {background: #04A2EA;color:#fff;}\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"mountNode\" style=\"margin:20px;text-align:center;\">\n" +
            "    <button id=\"default\" class=\"active\">查看模式</button>\n" +
            "    <button id=\"edit\"> 编辑模式</button>\n" +
            "</div>\n" +
            "<script>/*Fixing iframe window.innerHeight 0 issue in Safari*/document.body.clientHeight;</script>\n" +
            "<script src=\"https://gw.alipayobjects.com/os/antv/pkg/_antv.g6-3.0.2/build/g6.js\"></script>\n" +
            "<script src=\"https://gw.alipayobjects.com/os/antv/assets/lib/jquery-3.2.1.min.js\"></script>\n" +
            "<script src=\"https://gw.alipayobjects.com/os/antv/assets/lib/d3-4.13.0.min.js\"></script>\n" +
            "<script src=\"/static/js/artDialog/dialog.v1.js\" type=\"text/javascript\"></script>\n" +
            "<script src=\"/static/js/validator/validator.v1.js\" type=\"text/javascript\"></script>\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/js/artDialog/dialog.v1.css\" />\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/js/validator/validator.v1.1.css\" />\n" +
            "<script>\n" +
            "\n" +
            "    var simulation = d3.forceSimulation().force(\"link\", d3.forceLink().distance(200).id(function(d) {\n" +
            "        return d.id;\n" +
            "    }).strength(0.8)).force(\"charge\", d3.forceManyBody()).force(\"center\", d3.forceCenter(window.innerWidth / 2, window.innerHeight / 2));\n" +
            "    var dblclickStr = null;\n" +
            "    var beginState = null;\n" +
            "\n" +
            "    //获取url请求的参数\n" +
            "    function getQueryString(name) { \n" +
            "        var reg = new RegExp(\"(^|&)\" + name + \"=([^&]*)(&|$)\", \"i\"); \n" +
            "        var r = window.location.search.substr(1).match(reg); \n" +
            "        if (r != null) return unescape(r[2]); \n" +
            "        return null; \n" +
            "    }\n" +
            "\n" +
            "    //生成随机颜色\n" +
            "    function makeColor(){\n" +
            "        return '#'+Math.floor(Math.random()*0xffffff).toString(16);\n" +
            "    } \n" +
            "\n" +
            "     //graph.fitView();\n" +
            "    function ticked() {\n" +
            "        if (!graph.get('data')) {\n" +
            "\n" +
            "            graph.data(data);\n" +
            "            graph.render();\n" +
            "            // graph.fitView();\n" +
            "        } else {\n" +
            "            graph.refreshPositions();\n" +
            "        }\n" +
            "        graph.paint();\n" +
            "    }\n" +
            "\n" +
            "    // 封装点击添加边的交互\n" +
            "    G6.registerBehavior('click-add-edge', {\n" +
            "        getEvents() {\n" +
            "            return {\n" +
            "                'node:click': 'onClick' ,\n" +
            "                'mousemove': 'onMousemove'\n" +
            "            };\n" +
            "        },\n" +
            "        onClick(ev) {\n" +
            "\n" +
            "            const node = ev.item;\n" +
            "            const graph = this.graph;\n" +
            "            const point = {x: ev.x, y: ev.y};\n" +
            "            var model = node.getModel();\n" +
            "            var that = this;\n" +
            "            if(!!model.isVirtual){\n" +
            "                graph.remove(that.edge);\n" +
            "                that.edge = null;\n" +
            "                that.addingEdge = false;\n" +
            "                return;\n" +
            "            }\n" +
            "            // 如果在添加边的过程中，再次点击另一个节点，结束边的添加\n" +
            "\n" +
            "            // dblclickStr = setTimeout(function(){\n" +
            "                if (this.addingEdge && this.edge) {\n" +
            "                // console.log(point);\n" +
            "                //console.log(begin);\n" +
            "                if(begin == model.id ){\n" +
            "                    return;\n" +
            "                }\n" +
            "                dialog({\n" +
            "                    title: '表关联',\n" +
            "                    content: begin + \"确定要和\" + model.id +\"建立关联吗\",\n" +
            "                    ok: function () {\n" +
            "                        $.ajax({\n" +
            "                            url : \"/addingEdge\",\n" +
            "                            type: \"post\",\n" +
            "                            dataType: \"json\",\n" +
            "                            success : function(){\n" +
            "                                alert(\"提交成功\");\n" +
            "                            }\n" +
            "                        });\n" +
            "                        graph.updateItem(that.edge, {\n" +
            "                            target: model.id\n" +
            "                        });\n" +
            "                        that.edge = null;\n" +
            "                        that.addingEdge = false;\n" +
            "                        //return false;\n" +
            "                    },\n" +
            "                    cancel: function () {\n" +
            "                        //console.log(that.edge);\n" +
            "                        graph.remove(that.edge);\n" +
            "                        that.edge = null;\n" +
            "                        that.addingEdge = false;\n" +
            "                     }\n" +
            "                }).showModal();\n" +
            "            } else {\n" +
            "                // 点击节点，触发增加边\n" +
            "                this.edge = graph.addItem('edge', {\n" +
            "                    source: model.id,\n" +
            "                    target: point\n" +
            "                });\n" +
            "                begin = model.id;\n" +
            "                //console.log(point);\n" +
            "                this.addingEdge = true;\n" +
            "                //console.log(222);\n" +
            "            }\n" +
            "            // },300);\n" +
            "\n" +
            "            //clearTimeout(dblclickStr);\n" +
            "        },\n" +
            "        onMousemove(ev) {\n" +
            "            const point = {x: ev.x, y: ev.y};\n" +
            "            if (this.addingEdge && this.edge) {\n" +
            "                // 增加边的过程中，移动时边跟着移动\n" +
            "                this.graph.updateItem(this.edge, {\n" +
            "                    target: point\n" +
            "                });\n" +
            "            }\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "    G6.registerBehavior('drag-node-diy', {\n" +
            "        getEvents() {\n" +
            "            return {\n" +
            "                'node:dragstart': 'dragstart' ,\n" +
            "                'node:drag': 'drag',\n" +
            "                'node:dragend': 'dragend',\n" +
            "                'node:dblclick' : 'dblclick'\n" +
            "            };\n" +
            "        },\n" +
            "        dragstart(ev) {\n" +
            "            simulation.alphaTarget(0.3).restart();\n" +
            "            refreshPosition(ev);\n" +
            "        },\n" +
            "        drag(ev) {\n" +
            "            refreshPosition(ev);\n" +
            "        },\n" +
            "        dragend(ev) {\n" +
            "            simulation.alphaTarget(0);\n" +
            "            refreshPosition(ev);\n" +
            "\n" +
            "        },\n" +
            "        dblclick(ev){\n" +
            "            var dbStr = getQueryString(\"nodeId\");\n" +
            "            const node = ev.item;\n" +
            "            const graph = this.graph;\n" +
            "            const point = {x: ev.x, y: ev.y};\n" +
            "            var model = node.getModel();\n" +
            "            var that = this;\n" +
            "            $.ajax({\n" +
            "                url : \"/graph/api/data/init.json?dbId=\"+ dbStr.split(\"_\")[0] +\"&nodeId=\" + model.id,\n" +
            "                type: \"post\",\n" +
            "                dataType: \"json\",\n" +
            "                success : function(dataObj){\n" +
            "                    var data = dataObj.data.net;\n" +
            "                    graph.setAutoPaint(false);\n" +
            "                    graph.clear();\n" +
            "                    graph.data({\n" +
            "                        nodes: data.nodes,\n" +
            "                        edges: data.edges.map(function(edge, i) {\n" +
            "                            edge.id = 'edge' + i;\n" +
            "                            return Object.assign({}, edge);\n" +
            "                        })\n" +
            "                    });\n" +
            "                    simulation.nodes(data.nodes).on(\"tick\", ticked);\n" +
            "                    simulation.force(\"link\").links(data.edges).distance(400);\n" +
            "                    graph.render();\n" +
            "                    //simulation.alphaTarget(0);\n" +
            "                    refreshPosition(ev);\n" +
            "                    graph.getNodes().forEach(function(node) {\n" +
            "                        \n" +
            "                        if(node.get(\"model\").isVirtual){\n" +
            "                            if($(\".active\").attr(\"id\") == 'edit'){\n" +
            "                                graph.showItem(node);\n" +
            "                               \n" +
            "                            }else{\n" +
            "                                graph.hideItem(node);\n" +
            "                            }\n" +
            "                            \n" +
            "                        }\n" +
            "                       \n" +
            "                        \n" +
            "                    });\n" +
            "                    //graph.refresh();\n" +
            "                    graph.setAutoPaint(true);\n" +
            "                    \n" +
            "                    \n" +
            "                }\n" +
            "            });\n" +
            "            graph.updateItem(that.edge, {\n" +
            "                target: model.id\n" +
            "            });\n" +
            "            that.edge = null;\n" +
            "            that.addingEdge = false;\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    G6.registerBehavior('drag-canvas', {\n" +
            "        getEvents() {\n" +
            "            return {\n" +
            "                'node:contextmenu' : \"contextmenu\"\n" +
            "            };\n" +
            "        },\n" +
            "        contextmenu(ev){\n" +
            "             //clearTimeout(dblclickStr);\n" +
            "            //!!this.edge && graph.remove(this.edge);\n" +
            "            const node = ev.item;\n" +
            "            const graph = this.graph;\n" +
            "            const point = {x: ev.x, y: ev.y};\n" +
            "            var model = node.getModel();\n" +
            "            \n" +
            "            var that = this;\n" +
            "            var host = \"http://\" + window.location.host;\n" +
            "            //var host = 'http://hframework:15469';\n" +
            "            var actionStr = 'detail';\n" +
            "            if($(\"#edit\").hasClass(\"active\")){\n" +
            "                actionStr = 'edit';\n" +
            "            }\n" +
            "            if(model.isVirtual){\n" +
            "                actionStr = 'create';\n" +
            "            }\n" +
            "            \n" +
            "            //console.log(host + model.attrs[1].name + actionStr + model.attrs[0].name);\n" +
            "            dialog({\n" +
            "                title: '表关联',\n" +
            "                content: '<iframe style=\"width:100%;height:100%;\" src=\"'+ host + model.attrs[1].name + actionStr + model.attrs[0].name  +'\"></iframe>',\n" +
            "                width: $(window).width() - 100,\n" +
            "                height: $(window).height() - 200,\n" +
            "                cancelDisplay : true,\n" +
            "                ok: function () {\n" +
            "                    //return false;\n" +
            "                }\n" +
            "            }).showModal();\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "    \n" +
            "\n" +
            "\n" +
            "    var graph = new G6.Graph({\n" +
            "        container: 'mountNode',\n" +
            "        width: window.innerWidth,\n" +
            "        height: window.innerHeight,\n" +
            "        fitViewPadding:100,\n" +
            "        autoPaint: true,\n" +
            "        defaultNode: {\n" +
            "            size: [25, 25],\n" +
            "            color: 'steelblue',\n" +
            "            labelCfg: {\n" +
            "                position: \"bottom\",\n" +
            "                offset: 5\n" +
            "            }\n" +
            "        },\n" +
            "        defaultEdge: {\n" +
            "            size: 3,\n" +
            "            color: '#e2e2e2',\n" +
            "        },\n" +
            "        nodeStyle: {\n" +
            "            default: {\n" +
            "                lineWidth: 1,\n" +
            "                fill: '#f54545'\n" +
            "            },\n" +
            "            highlight: {\n" +
            "                opacity: 1\n" +
            "            },\n" +
            "            dark: {\n" +
            "                opacity: 0.4\n" +
            "            },\n" +
            "            none: {\n" +
            "                opacity:0\n" +
            "            }\n" +
            "        },\n" +
            "        edgeStyle: {\n" +
            "            default: {\n" +
            "                endArrow: true,\n" +
            "              //  lineWidth: 2,\n" +
            "                stroke: '#ff8547'\n" +
            "\n" +
            "            },\n" +
            "            highlight: {\n" +
            "                stroke: '#666'\n" +
            "            }\n" +
            "        },\n" +
            "        modes: {\n" +
            "\n" +
            "            default: ['drag-canvas', 'drag-node-diy', 'activate-node-relations',{\n" +
            "\n" +
            "                type: 'zoom-canvas',\n" +
            "\n" +
            "                sensitivity: 5\n" +
            "\n" +
            "            }],\n" +
            "\n" +
            "            edit: ['drag-canvas',  'click-add-edge',{\n" +
            "\n" +
            "                type: 'zoom-canvas',\n" +
            "\n" +
            "                sensitivity: 5\n" +
            "\n" +
            "            }]\n" +
            "\n" +
            "        }\n" +
            "    });\n" +
            "\n" +
            "\n" +
            "    // graph.setAutoPaint(true);\n" +
            "\n" +
            "\n" +
            "    function refreshPosition(e) {\n" +
            "        e.item.get('model').x = e.x;\n" +
            "        e.item.get('model').y = e.y;\n" +
            "        graph.refreshPositions();\n" +
            "    }\n" +
            "    // graph.on('node:dragstart', function(e) {\n" +
            "    //     simulation.alphaTarget(0.3).restart();\n" +
            "    //     refreshPosition(e);\n" +
            "    // });\n" +
            "    // graph.on('node:drag', function(e) {\n" +
            "    //     refreshPosition(e);\n" +
            "    // });\n" +
            "    // graph.on('node:dragend', function(e) {\n" +
            "    //     simulation.alphaTarget(0);\n" +
            "    //     refreshPosition(e);\n" +
            "    // });\n" +
            "    // graph.on('node:click', function(ev) {\n" +
            "    //     const node = ev.item;\n" +
            "    //     const graph = this.graph;\n" +
            "    //     const point = {x: ev.x, y: ev.y};\n" +
            "    //     alert(node.getModel().id);\n" +
            "    // });\n" +
            "\n" +
            "//    var jsData = {\n" +
            "//     resultCode: \"0\",\n" +
            "//     resultMessage: \"成功\",\n" +
            "//     data: {\n" +
            "//         hot_node_id: \"dictionary_1\",\n" +
            "//         net: {\n" +
            "//             nodes: [{\n" +
            "//                     id: \"dictionary_1\",\n" +
            "//                     label: \"布尔\",\n" +
            "//                     attrs: [{\n" +
            "//                             name: \".html?dictionaryId=1&isPop=true\",\n" +
            "//                             id: \"url_params\"\n" +
            "//                         },\n" +
            "//                         {\n" +
            "//                             name: \"/runtime/2_dictionary_\",\n" +
            "//                             id: \"url_entity\"\n" +
            "//                         }\n" +
            "//                     ]\n" +
            "//                 },\n" +
            "//                 {\n" +
            "//                     id: \"dictionary_item_2\",\n" +
            "//                     label: \"\",\n" +
            "//                     attrs: [{\n" +
            "//                             name: \".html?dictionaryItemId=2&isPop=true\",\n" +
            "//                             id: \"url_params\"\n" +
            "//                         },\n" +
            "//                         {\n" +
            "//                             name: \"/runtime/2_dictionary_item_\",\n" +
            "//                             id: \"url_entity\"\n" +
            "//                         }\n" +
            "//                     ]\n" +
            "//                 },\n" +
            "//                 {\n" +
            "//                     id: \"dictionary_item_3\",\n" +
            "//                     label: \"\",\n" +
            "//                     attrs: [{\n" +
            "//                             name: \".html?dictionaryItemId=3&isPop=true\",\n" +
            "//                             id: \"url_params\"\n" +
            "//                         },\n" +
            "//                         {\n" +
            "//                             name: \"/runtime/2_dictionary_item_\",\n" +
            "//                             id: \"url_entity\"\n" +
            "//                         }\n" +
            "//                     ]\n" +
            "//                 },\n" +
            "//                 {\n" +
            "//                     id: \"_v_dictionary_dictionary_item\",\n" +
            "//                     label: \"添加字典项\",\n" +
            "//                     isVirtual: true,\n" +
            "//                     attrs: [{\n" +
            "//                             name: \".html?dictionaryId=1&isPop=true\",\n" +
            "//                             id: \"url_params\"\n" +
            "//                         },\n" +
            "//                         {\n" +
            "//                             name: \"/runtime/2_dictionary_item_\",\n" +
            "//                             id: \"url_entity\"\n" +
            "//                         }\n" +
            "//                     ]\n" +
            "//                 }\n" +
            "//             ],\n" +
            "//             edges: [{\n" +
            "//                     source: \"dictionary_item_3\",\n" +
            "//                     label: \"\",\n" +
            "//                     value: 1,\n" +
            "//                     target: \"dictionary_1\"\n" +
            "//                 },\n" +
            "//                 {\n" +
            "//                     source: \"dictionary_item_2\",\n" +
            "//                     label: \"\",\n" +
            "//                     value: 1,\n" +
            "//                     target: \"dictionary_1\"\n" +
            "//                 },\n" +
            "//                 {\n" +
            "//                     source: \"_v_dictionary_dictionary_item\",\n" +
            "//                     label: \"添加字典项\",\n" +
            "//                     value: 1,\n" +
            "//                     target: \"dictionary_1\"\n" +
            "//                 }\n" +
            "//             ]\n" +
            "//         }\n" +
            "//     },\n" +
            "//     success: true\n" +
            "// };\n" +
            "\n" +
            "\n" +
            "    // $.getJSON('./assets/data/relations.json', function(data) {\n" +
            "    //     graph.data({\n" +
            "    //         nodes: data.nodes,\n" +
            "    //         edges: data.edges.map(function(edge, i) {\n" +
            "    //             edge.id = 'edge' + i;\n" +
            "    //             return Object.assign({}, edge);\n" +
            "    //         })\n" +
            "    //     });\n" +
            "    //     simulation.nodes(data.nodes).on(\"tick\", ticked);\n" +
            "    //     simulation.force(\"link\").links(data.edges);\n" +
            "\n" +
            "    //     graph.render();\n" +
            "    //     // graph.fitView();\n" +
            "    //     function ticked() {\n" +
            "    //         if (!graph.get('data')) {\n" +
            "    //             graph.data(data);\n" +
            "    //             graph.render();\n" +
            "    //             // graph.fitView();\n" +
            "    //         } else {\n" +
            "    //             graph.refreshPositions();\n" +
            "    //         }\n" +
            "    //         graph.paint();\n" +
            "    //     }\n" +
            "    // });\n" +
            "\n" +
            "    $(function(){\n" +
            "             //var data = jsData.data.net;\n" +
            "             var str = '/graph/api/data/init.json?';\n" +
            "             if(getQueryString(\"dbId\")){\n" +
            "                str += ('dbId=' + getQueryString(\"dbId\"));\n" +
            "             }\n" +
            "             if(getQueryString(\"dataId\")){\n" +
            "                str += ('&dataId=' + getQueryString(\"dataId\"));\n" +
            "             }\n" +
            "             if(getQueryString(\"nodeId\")){\n" +
            "                str += ('&nodeId=' + getQueryString(\"nodeId\"));\n" +
            "             }\n" +
            "             $.ajax({\n" +
            "                 url : str,\n" +
            "                 type: \"post\",\n" +
            "                 dataType: \"json\",\n" +
            "                 success : function(dataObj){\n" +
            "                     if(dataObj.data.net){\n" +
            "                        var data = dataObj.data.net;\n" +
            "                        var arr = [];\n" +
            "                        var obj = {};\n" +
            "                        var color = makeColor();\n" +
            "                         $.each(data.nodes ,function(i,v){\n" +
            "                            // if(arr.indexOf(v.schema) < 0){\n" +
            "                            //     arr.push(v.schema);\n" +
            "                            //     obj[v.schema] = makeColor();\n" +
            "\n" +
            "                            // }\n" +
            "\n" +
            "                            // v.style = {};\n" +
            "                            // v.style.stroke = obj[v.schema];\n" +
            "\n" +
            "\n" +
            "                            if(v.isVirtual){\n" +
            "                                v.shape = 'rect';\n" +
            "                                v.color = '#FC7F0C';\n" +
            "\n" +
            "                                //v.style  = 'display: none';\n" +
            "                                //v.labelCfg.position = 'middle';\n" +
            "                                //v.label = '+';\n" +
            "                                //v.size = 100;\n" +
            "                            }\n" +
            "                         });\n" +
            "                         \n" +
            "                         graph.data({\n" +
            "                             nodes: data.nodes,\n" +
            "                             edges: data.edges.map(function(edge, i) {\n" +
            "                                 edge.id = 'edge' + i;\n" +
            "                                 return Object.assign({}, edge);\n" +
            "                             })\n" +
            "                         });\n" +
            "                         \n" +
            "                         simulation.nodes(data.nodes).on(\"tick\", ticked);\n" +
            "                         simulation.force(\"link\").links(data.edges);\n" +
            "\n" +
            "                         graph.render();\n" +
            "                         graph.getNodes().forEach(function(node) {\n" +
            "                            //console.log(node.get(\"model\"));\n" +
            "                            //node.get(\"model\")\n" +
            "                            //graph.clearItemStates(node);\n" +
            "                                var modelId = node.get(\"model\").id;\n" +
            "                                if(modelId == dataObj.data.hot_node_id){\n" +
            "                                    graph.setItemState(node, 'dark', false);\n" +
            "                                }else{\n" +
            "                                    graph.setItemState(node, 'dark', true);\n" +
            "                                }\n" +
            "                                //console.log(node.get(\"model\"));\n" +
            "                                if(node.get(\"model\").isVirtual){\n" +
            "                                    graph.setItemState(node, 'shape', 'rect');\n" +
            "                                    graph.hideItem(node);\n" +
            "                                    \n" +
            "                                }\n" +
            "                           \n" +
            "                            \n" +
            "                        });\n" +
            "                         \n" +
            "                     }\n" +
            "                 }\n" +
            "             });\n" +
            "             \n" +
            "        });\n" +
            "\n" +
            "    // $(window).resize(function () {\n" +
            "        \n" +
            "    // });\n" +
            "\n" +
            "    // graph.setMode('edit');\n" +
            "    $(\"#mountNode\").on(\"click\", \"button\", function(){\n" +
            "        var that = this;\n" +
            "        $(this).addClass('active').siblings().removeClass('active');\n" +
            "         graph.getNodes().forEach(function(node) {\n" +
            "            \n" +
            "            if(node.get(\"model\").isVirtual){\n" +
            "                if(that.id == 'edit'){\n" +
            "                    graph.showItem(node);\n" +
            "                    graph.update(node, {\n" +
            "                           labelCfg: {\n" +
            "                             position: 'center'\n" +
            "                             \n" +
            "                           },\n" +
            "                           label : \"添加\",\n" +
            "                           style: {\n" +
            "                             size : 100\n" +
            "                           }\n" +
            "                        });\n" +
            "                }else{\n" +
            "                    graph.hideItem(node);\n" +
            "                }\n" +
            "                \n" +
            "            }\n" +
            "           \n" +
            "            \n" +
            "        });\n" +
            "        \n" +
            "        graph.setMode($(this).attr(\"id\"));\n" +
            "    });\n" +
            "\n" +
            "    \n" +
            "</script>\n" +
            "</body>\n" +
            "</html>\n";

}
