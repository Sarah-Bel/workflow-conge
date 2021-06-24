package com.workflow.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.From;
import javax.transaction.Transactional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.asm.Advice.This;

import com.workflow.dto.ProcessInstanceResponse;
import com.workflow.dto.Response;
import com.workflow.dto.TaskDetails;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class CongeService {
	
	
	
    public static final String TASK_CANDIDATE_GROUP = "managers";
    public static final String PROCESS_DEFINITION_KEY = "holidayRequest";
    public static final String TASK_CANDIDATE_GROUPRH = "username";
    RuntimeService runtimeService;
    TaskService taskService;
    ProcessEngine processEngine;
    RepositoryService repositoryService;
    
    
    
    
    @Autowired  
	congeRepository congeRepository ;

    //********************************************************** deployment service methods **********************************************************
    
    public void deployProcessDefinition() {

        Deployment deployment =
                repositoryService
                        .createDeployment()
                        .addClasspathResource("processus/Processflowconge.bpmn20.xml")
                        .deploy();


    }


    //********************************************************** process service methods **********************************************************
  //  MultipartFile file;
    public ProcessInstanceResponse applyHoliday(TDemande tDemande) {
    	
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      //  DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("Typeconge", tDemande.getCongeType());
        variables.put("DateDebut", tDemande.getDateDebut());
        variables.put("DateFin", tDemande.getDateFin());
        variables.put("Commentaire", tDemande.getComment());
        variables.put("username", tDemande.getUser().rtid());
       
        
       ProcessInstance processInstance =
               runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
    	congeRepository.save(tDemande);
    	String Process=processInstance.getId();
    	 tDemande.setProcess(Process);
        return new ProcessInstanceResponse(processInstance.getId(), processInstance.isEnded());

    }
    
    
    
    //***************Table demande de conge *****************************
  //  public void saveDemande(TDemande tDemande) {
    	
  //  	congeRepository.save(tDemande);
  //  }


    public List<TaskDetails> getManagerTasks() {
        List<Task> tasks =
                taskService.createTaskQuery().taskCandidateGroup(TASK_CANDIDATE_GROUP).list();
        List<TaskDetails> taskDetails = getTaskDetails(tasks);

        return taskDetails;
    }

    private List<TaskDetails> getTaskDetails(List<Task> tasks) {
        List<TaskDetails> taskDetails = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> processVariables = taskService.getVariables(task.getId());
            taskDetails.add(new TaskDetails(task.getId(), task.getName(), processVariables));
        }
        return taskDetails;
    }


    public void approveHoliday(String taskId,Boolean approved) {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approved", approved.booleanValue());
        taskService.complete(taskId, variables);
    }
    

    public void acceptHoliday(String taskId) {
        taskService.complete(taskId);
    }
    public List<TaskDetails> getUserTasks() {
    
    List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("rh").list();
    List<TaskDetails> taskDetails = getTaskDetails(tasks);
    System.out.println(tasks);

      return taskDetails ;
      
 
   }
 

    public Response checkProcessHistory(String processId) {

    	  HistoryService historyService = processEngine.getHistoryService();

          List<HistoricActivityInstance> activities =
                  historyService
                          .createHistoricActivityInstanceQuery()
                          .processInstanceId(processId)
                          .finished()
                          .orderByHistoricActivityInstanceEndTime()
                          .asc()
                          .list();
          
          Response stateActivity = new Response();
//          for (HistoricActivityInstance activity : activities) {
//          	Response  = activity.getActivityId();
//          }
           stateActivity.setActivityId(activities.get(activities.size()-1).getActivityId());
           return stateActivity;
    }
    
    public void approveHolidayRH(String taskId,Boolean approvedRH) {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approvedRH", approvedRH.booleanValue());
        taskService.complete(taskId, variables);
    }

    public List<TDemande> rechercheConge(String Commentaire,String Typeconge, String empName) {
    	
    return congeRepository.findByrechercheid(Commentaire,Typeconge,empName);
    
    }
    
    public List<TDemande> recherche() {
    	
        return congeRepository.findAll();
        }


	
    
    


}