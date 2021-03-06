package promgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import promgr.db.PromgrDao;

@Controller
public class PromgrChkListModController {

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
	public ModelAndView submit(@RequestParam(value = "list_num") int list_num,
			@RequestParam(value = "promgr_list_title") String list_name) {

		int promgr_update_count = dao.modChkList(list_num, list_name);
		
		ModelAndView mav = new ModelAndView("pro");
		mav.addObject("promgr_update_count", promgr_update_count);

		return mav;

	}

}
