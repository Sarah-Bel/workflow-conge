package org.example.tut.flowable.handler;

import javax.mail.internet.MimeMessage;

import org.flowable.engine.delegate.DelegateExecution;
import lombok.Data;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.workflow.demo.User;
import com.workflow.demo.UserRepository;

@Data
public class HolidayApprovalHandlerOK implements JavaDelegate {
	 @Override
	    public void execute(DelegateExecution execution) {

	        System.out.println("Approved, sending an email");
	    }
//	 @Autowired
//	    UserRepository userrepository;
//	    
//	    @Autowired
//		private JavaMailSender mailSenderObj;
//	    
//	 public void sendmail(User user) {
//
//			final String emailToRecipient = user.getEmp_email();
//			final String emailSubject = "Succesfully Registration";
//
//			final String emailMessage1 = "Bonjour mr /mme"+ user.getEmp_email()+"votre conge et accepte";
//
//			mailSenderObj.send(new MimeMessagePreparator() {
//
//				@Override
//				public void prepare(MimeMessage mimeMessage) throws Exception {
//
//					MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//
//					mimeMsgHelperObj.setTo(emailToRecipient);
//					mimeMsgHelperObj.setText(emailMessage1, true);
//
//					mimeMsgHelperObj.setSubject(emailSubject);
//
//				}
//			});
//
//		}
}
