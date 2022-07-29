package com.lincoln.test;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.util.List;

/**
 * @Author Lincoln Lin
 * @Date 2022/7/27 16:19
 * @Description:
 */
public class ActivitiDemo {

    /**
     * 部署流程
     * select * from act_re_deployment;
     * select * from act_re_procdef;
     * select * from act_ge_bytearray;
     * # 主要是上面的三张
     *
     * select * from act_ge_property;
     */
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

    /**
     * 启动流程实例
     * 1. 创建流程实例
     * 2. 启动流程实例
     * 3. 根据流程定义的id启动流程
     * 4. 输出内容
     *
     * # 流程启动实例需要操作的表
     *
     * # 历史信息
     * # 流程定义表
     * select * from act_ge_property;
     * # 流程每个节点的历史记录表(流程的实例执行历史)
     * select * from act_hi_actinst;
     * # 操作和记录流程参与者的历史信息(流程参与用户信息历史)
     * select * from act_hi_identitylink;
     * # 流程实例的历史信息，比如流程开始时间等(流程实例历史信息)
     * select * from act_hi_procinst;
     * # 历史任务的实例(流程任务历史信息)
     * select * from act_hi_taskinst;
     *
     * # 运行时
     * # 当前我们执行到哪个任务上(流程正在执行的信息)
     * select * from act_ru_execution;
     * # 当前任务是由谁来做的(流程的参与用户的信息)
     * select * from act_ru_identitylink;
     * # 记录运行时的任务(任务信息)
     * select * from act_ru_task;
     *
     */
    @Test
    public void testStartProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("businessTrip");
        System.out.println("流程定义ID： " + processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID： " + processInstance.getId());
        System.out.println("当前活动ID： " + processInstance.getActivityId());
    }

    /**
     * 查询个人待处理的任务
     *
     * 1. 获取流程引擎
     * 2. 获取task service
     * 3. 根据流程key 和 任务的负责人 查询任务
     * 4. 输出
     */
    @Test
    public void testQueryPersonTaskList(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("businessTrip") // 流程的key
                .taskAssignee("Lincoln") // 要查询的流程的分配人
                .list();
        taskList.stream().forEach(
            task -> {
                System.out.println("流程实例Id=" + task.getProcessInstanceId());
                System.out.println("任务Id=" + task.getId());
                System.out.println("任务负责人=" + task.getAssignee());
                System.out.println("任务名称=" + task.getName());
                System.out.println("--------------------------------------");
            }
        );
    }

    /**
     * 完成个人任务
     * 1. 创建流程引擎
     * 2. 获取service
     * 3. 查询任务
     * 3. 根据任务id完成任务
     */
    @Test
    public void completeTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        // step1: 获取Lincoln -> businessTrip 完成Lincoln的对应的任务
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("businessTrip") // 流程的key
//                .taskAssignee("Lincoln") // 要查询的流程的分配人
//                .singleResult();

        // step2:  获取Tim -> businessTrip  完成Tim的对应的任务
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("businessTrip") // 流程的key
//                .taskAssignee("Tim") // 要查询的流程的分配人
//                .singleResult();

        // step3:  获取Yuhang -> businessTrip  完成Yuhang的对应的任务
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("businessTrip") // 流程的key
//                .taskAssignee("Yuhang") // 要查询的流程的分配人
//                .singleResult();

        // step4:  获取David -> businessTrip  完成David的对应的任务
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("businessTrip") // 流程的key
//                .taskAssignee("David") // 要查询的流程的分配人
//                .singleResult();

        // step5:  获取yuki -> businessTrip  完成yuki的对应的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("businessTrip") // 流程的key
                .taskAssignee("yuki") // 要查询的流程的分配人
                .singleResult();

        System.out.println("流程实例Id=" + task.getProcessInstanceId());
        System.out.println("任务Id=" + task.getId());
        System.out.println("任务负责人=" + task.getAssignee());
        System.out.println("任务名称=" + task.getName());
        System.out.println("--------------------------------------");
        // 完成Tim的任务
        taskService.complete(task.getId());
    }

}
