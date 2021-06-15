package com.workflow.demo;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.dto.ProcessInstanceResponse;
import com.workflow.dto.Response;
import com.workflow.dto.TaskDetails;

@CrossOrigin(origins= "*")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CongeController {

	@Autowired
	CongeService congerService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomUserDetailsService userDetailService;

    

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        final UserDetails userDetails = userDetailService
                .loadUserByUsername(authRequest.getUsername());
        return 		jwtUtil.generateToken(userDetails);
    }
    
    @GetMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

    //********************************************************** deployment endpoints **********************************************************
    @PostMapping("/deploy")
    public void deployWorkflow() {
    	congerService.deployProcessDefinition();
    }

    //********************************************************** process endpoints **********************************************************
    // @PreAuthorize("hasRole('Manager')")  
    @PostMapping("/conger/demande")
    public ProcessInstanceResponse applyHoliday(@RequestBody TDemande tDemande) {
    	congerService.saveDemande(tDemande);
        return congerService.applyHoliday(tDemande);
    }


    @GetMapping("/manager/tasks")
    public List<TaskDetails> getTasks() {
        return congerService.getManagerTasks();
    }


    @PostMapping("/manager/approve/tasks/{taskId}/{approved}")
    public void approveTask(@PathVariable("taskId") String taskId,@PathVariable("approved") Boolean approved){
    	
    	congerService.approveHoliday(taskId,approved);

    }

    @PostMapping("/user/accept/{taskId}")
    public void acceptHoliday(@PathVariable("taskId") String taskId){
    	congerService.acceptHoliday(taskId);
    }

    @GetMapping("/RH/tasks")
    public List<TaskDetails> getUserTasks() {
        return congerService.getUserTasks();
    }


    @GetMapping("/process/{processId}")
    public Response checkState(@PathVariable("processId") String processId){
       return congerService.checkProcessHistory(processId);
    }

    @PostMapping("/RH/approve/tasks/{taskId}/{approvedRH}")
    public void approveTaskRH(@PathVariable("taskId") String taskId,@PathVariable("approvedRH") Boolean approvedRH){
    	congerService.approveHolidayRH(taskId,approvedRH);
    }
    
    @GetMapping("/Recherche/{valuers}")
    public  List<TDemande> recherche(
    		@PathVariable("valuers") String comment,
    		@PathVariable("valuers") String congeType,
    		@PathVariable("valuers") String empName
    		)
    {
    	return congerService.rechercheConge(comment,congeType,empName);
    }
    
    @GetMapping("/Recherche")
    public  List<TDemande> recherche()
    {
    	return congerService.recherche();
    }

    


}