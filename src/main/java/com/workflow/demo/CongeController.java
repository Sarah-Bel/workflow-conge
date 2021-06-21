package com.workflow.demo;

import java.util.List;

import javax.mail.internet.MimeMessage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.example.tut.flowable.handler.HolidayApprovalHandlerOK;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.workflow.dto.ProcessInstanceResponse;
import com.workflow.dto.TaskDetails;




@CrossOrigin(origins= {"http://localhost:4200"})
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
    //********************************************************** deployment endpoints **********************************************************
    @PostMapping("/deploy")
    public void deployWorkflow() {
    	congerService.deployProcessDefinition();
    }
   
    
    static class StartProcessRepresentation {

        private String assignee;

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
    }
    //********************************************************** process endpoints **********************************************************
    @PostMapping("/conger/demande")
    public ProcessInstanceResponse applyHoliday(@RequestBody TDemande tDemande) {
    	//congerService.saveDemande(tDemande);
    	
        return congerService.applyHoliday(tDemande);
    }
    


    @GetMapping("/manager/tasks")
    public List<TaskDetails> getTasks() {
        return congerService.getManagerTasks();
    }


    @PostMapping("/manager/approve/tasks/{taskId}/{approved}/{user}")
    public void approveTask(@PathVariable("taskId") String taskId,@PathVariable("approved") Boolean approved,@PathVariable("user") String username){
    	
    	congerService.approveHoliday(taskId,approved);
    	if(approved==true)
    	{
    	UserMailManager(username);
    	}
    	else 
    		UserMailinvalid(username);
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
    public String checkState(@PathVariable("processId") String processId){
    	congerService.checkProcessHistory(processId);
    	return "checkProcessHistory";
    }

    @PostMapping("/RH/approve/tasks/{taskId}/{approvedRH}/{user}")
    public void approveTaskRH(@PathVariable("taskId") String taskId,@PathVariable("approvedRH") Boolean approvedRH,@PathVariable("user") String username){
    	congerService.approveHolidayRH(taskId,approvedRH);
    	
    	if(approvedRH==true)
    	{
    	UserMail(username);
    	}
    	else 
    		UserMailinvalid(username);
    	
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
    //************************************MAIL RH***********************************************
    @Autowired
    UserRepository userrepository;
    
    @Autowired
	private JavaMailSender mailSenderObj;

    @RequestMapping(value = { "/Allusers" }, method = RequestMethod.GET)
	public List<User> viewEmployees(Model model) {

		List<User> alluser = userrepository.findAll();
		model.addAttribute("users", alluser);
		return alluser;

	}
    
   
 	@GetMapping(value = "/sendMail/{id}")
 	public ResponseEntity<User> UserMail(@PathVariable("id") String user_id) {

 		User user = userrepository.findById(user_id).get();

 		sendmail(user);

 		return new ResponseEntity<User>(HttpStatus.OK);

 	}
 	
 	private void sendmail(User user) {
 		
		final String emailToRecipient = user.getEmp_email();
		final String emailSubject = "Acceptation de votre congé";

		final String emailMessage1 = "Mr/mme "+ user.getUsername()+" je vous informe que j’accepte votre demande.";

		mailSenderObj.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setText(emailMessage1, true);

				mimeMsgHelperObj.setSubject(emailSubject);

			}
		});

	}
 	
 	//********************************************************************************************
 	public ResponseEntity<User> UserMailManager(@PathVariable("id") String user_id) {

 		User user = userrepository.findById(user_id).get();

 		sendmailManager(user);

 		return new ResponseEntity<User>(HttpStatus.OK);

 	}
 	
 	private void sendmailManager(User user) {
 		
		final String emailToRecipient = user.getEmp_email();
		final String emailSubject = "Acceptation de votre congé";

		final String emailMessage1 = "Mr/mme "+ user.getUsername()+" je vous informe que j’accepte votre demande je transmets votre demande "
				+ " a directeur de reoussuer humain pour validé.";

		mailSenderObj.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				mimeMsgHelperObj.setTo(emailToRecipient);
				mimeMsgHelperObj.setText(emailMessage1, true);

				mimeMsgHelperObj.setSubject(emailSubject);

			}
		});
 	}
	//********************************************************************************************
		public ResponseEntity<User> UserMailinvalid(@PathVariable("id") String user_id) {

	 		User user = userrepository.findById(user_id).get();

	 		sendmailinvalid(user);

	 		return new ResponseEntity<User>(HttpStatus.OK);

	 	}
	 	
	 	private void sendmailinvalid(User user) {
	 		
			final String emailToRecipient = user.getEmp_email();
			final String emailSubject = "Acceptation de votre congé";

			final String emailMessage1 = "Mr/mme "+ user.getUsername()+" je vous informe que votre demande a été refusée";

			mailSenderObj.send(new MimeMessagePreparator() {

				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {

					MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");

					mimeMsgHelperObj.setTo(emailToRecipient);
					mimeMsgHelperObj.setText(emailMessage1, true);

					mimeMsgHelperObj.setSubject(emailSubject);

				}
			});

		}
 	

}