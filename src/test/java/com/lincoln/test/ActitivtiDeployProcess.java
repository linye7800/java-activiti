package com.lincoln.test;

import java.io.InputStream;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.util.zip.ZipInputStream;

/**
 * @Author Lincoln Lin
 * @Date 2022/7/29 11:56
 * @Description:
 */
public class ActitivtiDeployProcess {

    /**
     * 使用zip包进行批量的部署
     *
     * 1. 获取流程引擎
     * 2. 获取repositoryService
     *
     * 一个思考
     */
    @Test
    public void deployProcessByZip() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 读取资源包文件，构成inputStream
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/trip.zip");
        // 用inputStream 构造ZipInputStream
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deployment = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();

        System.out.println("流程部署的id=" + deployment.getId());
        System.out.println("流程部署的name=" + deployment.getName());
    }

}
