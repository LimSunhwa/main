package promgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import promgr.db.PromgrDao;

@Controller
public class PromgrChkListDelController {

	private PromgrDao dao;

	@Autowired
	public void setDao(PromgrDao dao) {
		this.dao = dao;
	}

	@RequestMapping("/chkListModForm.do")
	public ModelAndView form(@RequestParam(value = "list_num") int list_num) {

		ModelAndView mav = new ModelAndView("chkListModForm");
		mav.addObject("list_num", list_num);

		return mav;

	}

	@RequestMapping("/chkListModPro.do")
	public String submit(@RequestParam(value = "promgr_num") int promgr_num,
			@RequestParam(value = "list_num") int list_num) {

		dao.delChkList(promgr_num, list_num);

		return "redirect:/more.do";
	}

}
