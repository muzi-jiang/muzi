package com.muzi.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.muzi.manager.service.MenuManagerService;
import com.muzi.manager.util.R;
import com.muzi.system.entity.Permission;

/**
 * 菜单/资源管理 控制
 * @author libin
 */
@Controller
@RequestMapping("/backstage/manager/menu")
public class MenuManagerController {

	@Autowired
	private MenuManagerService menuManagerService;
	
	/**
     * 转到菜单界面
     * @return
     */
    @RequestMapping("/toMenu")
    public ModelAndView toMenu(){
        ModelAndView mav = new ModelAndView("manager/menu/manager");
        return mav;
    }
    
    /**
     * 数据查询
     * @param params
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Permission> list(@RequestParam Map<String, Object> params){
    	List<Permission> list = menuManagerService.list(params);
        return list;
    }
    
    /**
     *转到添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public ModelAndView toAdd(@RequestParam("id")int id){
    	ModelAndView mav = menuManagerService.toAdd(id);
        return mav;
    }
    
    
    /**
     * 图标选择界面
     * @return
     */
    @RequestMapping("/fontIcoList")
    public ModelAndView fontIcoList(){
        ModelAndView mav = new ModelAndView("manager/menu/FontIcoList");
        return mav;
    }
    
    
    /**
     * 新添加数据
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public R save(Permission permission){
    	R r = menuManagerService.save(permission);
    	return r;
    }
    
    /**
     * 转到修改界面
     * @param id
     * @return
     */
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(int id){
    	ModelAndView mav = menuManagerService.toEdit(id);
        return mav;
    }
    
    
    /**
     * 修改
     * @param resources
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public R update(Permission permission){
    	return menuManagerService.update(permission);
    }
    
    
    
    
    
    /**
     * 物理删除且不可恢复
     * @param id
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody
    public R remove(int id){
       return menuManagerService.remove(id);
    }
    
    
    
    
    
    
    
    
    
    
    
}
