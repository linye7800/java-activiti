package com.lincoln.test;

import com.sun.deploy.resources.Deployment;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
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
     */
    @Test
    public void deployProcessByZip() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
    }

}
