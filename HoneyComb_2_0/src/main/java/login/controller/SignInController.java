package login.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController  {
	
	private static final Logger log = LoggerFactory.getLogger(SignInController.class);
	
	@Autowired
	SignInDAO dao = new SignInDAO(); 
	
	public void setDao(SignInDAO dao) {
		this.dao = dao;
	}
	@RequestMapping("/signIn.do")//signIn 폼으로 가는 메소드 
	public String signInForm(){
		
		return "signIn";
	}
	@RequestMapping(value="members.do")//DB에 insert하는 메소드
	public ModelAndView members(LogOnDataBean logdb, HttpSession session, HttpServletResponse response) throws Exception{
		System.out.println("실행");
		System.out.println(logdb.toString());
		
		ModelAndView mav = new ModelAndView();
		System.out.println("insert");
		dao.insertMember(logdb);
		mav.setViewName("redirect:/login/success.do");
		System.out.println("끝");
		
		return mav;
	}
	
	@RequestMapping("/success.do")//회원가입 완료페이지
	public String success(){
		
		return "success";
	}
	
	@RequestMapping("mailCheck.do")//이메일 중복확인
	@ResponseBody
	public String CheckEmail(String email){
		
		String chkMail = dao.CheckEmail(email);
		
		return chkMail;
	}
	@RequestMapping("checkEmailPro.do")//회원가입 이메일 인증
	public String checkEmailPro(Model model,HttpServletRequest request, HttpServletResponse response) throws MessagingException{
		
		MailSender mailsender = new MailSender();
		String email = request.getParameter("email");
		int numCheck = mailsender.emailSender(email);
		model.addAttribute("numCheck", numCheck);
		
		return "checkEmailPro";
	}
	@RequestMapping("checkEmail_y.do") //이메일이 중복되지않을 경우
	public String checkEmail_y(String email){
		System.out.println("email_y::"+email);
		return "checkEmail_y";
	}
	@RequestMapping("checkEmail_n.do") //이메일이 중복될 경우
	public String checkEmail_n(String email){
		System.out.println("email_n::"+email);
		return "checkEmail_n";
	}
	
}
							
		

	
	
	
