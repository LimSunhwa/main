package promgr.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import promgr.db.PromgrDao;
import promgr.db.PromgrDataBean;

@Controller
public class PromgrAddController {

	private PromgrDao dao;

	@Autowired
	public void setDao(PromgrDao dao) {
		this.dao = dao;
	}

	@RequestMapping("/promgrAddForm.do")
	public String form() {

		return "promgrAddForm";

	}

	@RequestMapping("/promgrAddPro.do")
	public ModelAndView submit(HttpServletRequest request) {

		PromgrDataBean article = new PromgrDataBean();
		article.setPromgr_name(request.getParameter("promgr_title"));
		article.setPromgr_content(request.getParameter("promgr_content"));
		article.setPromgr_date(new Timestamp(System.currentTimeMillis()));
		article.setMem_num(String.valueOf(request.getSession().getAttribute("mem_num")));
		article.setCom_num((int) request.getSession().getAttribute("com_num"));

		int promgr_insert_count = dao.addPromgr(article);

		ModelAndView mav = new ModelAndView("pro");
		mav.addObject("promgr_insert_count", promgr_insert_count);

		return mav;
	}

}
