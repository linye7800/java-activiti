# springboot-activiti
1. springboot
2. activiti7


### 学习资料
# 总结

1. **Activiti的表说明**

   使用25张表

   ACT开头，为了和其它业务表分开

   act_re 流程定义和流程资源表

   ru 运行时，流程实例、任务、变量

   hi 历史表

   ge 通用表

2. **Activiti的架构、类关系图**

   获取流程引擎的工具类

   ProcessEngines使用默认方式获取配置文件，构造流程引擎。配置文件名字Activiti.xml.cfg，放在classpath下

   ProcesEnginesConfiguration 可以自定义配置文件名

   使用上面的两个工具类，都可以获取流程引擎

   ProcessEngine：流程引擎，获取各个服务的接口

   服务接口：用户流程的部署、执行、管理，使用这些接口就是在操作对应的数据表

   RepositoryService  资源管理类

   RuntimeService  运行时管理类

   TaskService  任务管理类

   ManagementService  六月初引擎管理类

3. **BPMN的插件**

   idea安装插件

4. **流程符号、画图流程**

   流程符号：时间Event，活动activitiy，网关gateway，流向

   使用流程设计器画出流程图

   bmpn文件本质为xml文件，因为有插件才可以看到可视化的图

   创建bpmn文件，在流程设计器使用流程符号来表达流程。指定流程的key，指定任务的负责人

   把bpmn流程图xml文件生成PNG文件，我们都可以看到流程图（插件可以支持）

5. **部署流程**

   使用activititi提供的API把流程图的内容写入到数据库中

   使用资源类操作，使用repositoryService

   单文件部署：把bpmn文件和png文件一个一个处理

   压缩包部署：把多个bpmn和png文件一起达成zip文件，一起去处理

   ```repositoryService.createDeployment()```

   部署操作的表：

   act_re_deployment 部署表

   act_re_procdef 流程定义表  包含流程的key，name，资源

   act_ge_bytearray 资源表 保存流程的原始资源信息

6. **启动流程实例**

   使用RuntimeService根据流程的key来启动

    ```java
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("businessTrip");
    
        /*
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
    ```

7. **任务查询**
   使用TaskService，根据流程定义的key，任务的负责人来查询
    ```java
    TaskService taskService = processEngine.getTaskService();
    List<Task> taskList = taskService.createTaskQuery()
                    .processDefinitionKey("businessTrip") // 流程的key
                    .taskAssignee("Lincoln") // 要查询的流程的分配人
                    .list();
    ```

8. **任务完成**

   使用TaskService来完成，用任务id来完成任务
    ```java
    TaskService taskService = processEngine.getTaskService();
    // step5:  获取yuki -> businessTrip  完成yuki的对应的任务
            Task task = taskService.createTaskQuery()
                    .processDefinitionKey("businessTrip") // 流程的key
                    .taskAssignee("yuki") // 要查询的流程的分配人
                    .singleResult();
    ```

8. **流程部署查询**

   使用RepositoryService来完成，用任务id来完成任务
    ```java
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitions = definitionQuery.processDefinitionKey("businessTrip")
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
    ```