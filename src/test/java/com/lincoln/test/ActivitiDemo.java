package com.lincoln.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * @Author Lincoln Lin
 * @Date 2022/7/27 16:19
 * @Description:
 */
public class ActivitiDemo {

    @Test
    public void testDeployment() {
        // 1. 创建processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2. 获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3. 使用service进行流程的部署，把bpmn和png部署到数据中
        Deployment deploy = repositoryService.createDeployment()
                                .name("businessTrip")
                                .addClasspathResource("bpmn/businessTrip.bpmn20.xml")
                                .addClasspathResource("bpmn/businessTrip.png")
                                .deploy();
        // 4. 输出部署信息
        System.out.println("流程部署id： " + deploy.getId());
        System.out.println("流程部署的名字： " + deploy.getName());
    }

}
