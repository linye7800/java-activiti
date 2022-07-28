package com.lincoln.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.junit.Test;

/**
 * @Author Lincoln Lin
 * @Date 2022/7/26 16:14
 * @Description:
 */
public class TestCreate {

    /**
     * 使用activiti提供的默认方式来创建mysql表
     */
    @Test
    public void testCreateDbTable(){
        // 使用activiti提供的工具类
        // getDefaultProcessEngine() 会默认从resource下读取activiti.cfg.xml的配置文件
        // 创建ProcessEngine时，就会创建mysql的表

        // 默认的方式创建流程引擎
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        repositoryService.createDeployment();

        // 使用自定义的方式
        // 配置文件的名字可以自定义，bean的名字也可以自定义
        ProcessEngineConfiguration processEngineConfiguration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml", "processEngineConfiguration");

        // 获取流程引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }

}
