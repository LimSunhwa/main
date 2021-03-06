package cloud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cloud.db.CloudDao;
import cloud.db.CloudInfo;

@Controller
public class CloudMainController implements ApplicationContextAware{
	/*Download용 ViewResolver 받기*/
	private WebApplicationContext context = null;
	@Override
	public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		
	}
	// session 받기
	private CloudDao dao;

	@Autowired
	public void setDao(CloudDao dao) {
		this.dao = dao;
	}

	// 클라우드 메인리스트
	@RequestMapping(value="/main", method = RequestMethod.GET)
	public ModelAndView getCloudList(HttpSession session, HttpServletRequest request, String folder) {
		session = request.getSession();
		int com_num = (int) request.getSession().getAttribute("com_num");
		List cloudlist = dao.getcloudList(com_num, folder);
		ModelAndView mav = new ModelAndView("cloud_main", "cloudlist", cloudlist);
		return mav;
	}
	/*----업로드폼으로 접근 RESTFul 사용------------*/
		//메인에서 업로드폼 연결
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String goUploadMain(String folder){
		return("/upload");
	}

		//메인에서 업로드
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public String uploadMain(@RequestParam("uploadfile")MultipartFile uploadfile,@RequestParam(required = false, value="folder")String folder,  HttpServletRequest request, String security, @RequestParam(required = false,defaultValue = "0")int promgr_num, @RequestParam(value = "promgr_name", defaultValue = "")String promgr_name){
		int com_pos_num = (security != null)?5:6;
		folder = (folder==null)?"":folder;	
		
	/*	if(promgr_num == 0){
			return null;
		}*/
		if(promgr_num > 0 ){
			folder = "%%"+promgr_name;
			String promgDupli = dao.promgrDuplick(promgr_num,folder, request);
			
			if(promgDupli.equals("0")){
			makeFolderPro("",folder,request,promgr_num);
			};
			folder = dao.getFilePath(promgr_num, folder, request);
			
		};		
		CloudInfo info = new Cloud_uploadFile().uploadFile(uploadfile, folder, request, com_pos_num, promgr_num);
		dao.uploadFile(info);
		
		return "redirect:upload?upload=ok";
	}
		//폴더 내부에서 업로드	
	/*프로젝트 업로드 컨트롤러*/
	@RequestMapping(value="/uploadPromgr")
	public ModelAndView uploadPromgr(@RequestParam("promgr_name")String promgr_name,@RequestParam("promgr_num") int promgr_num){
	ModelAndView mav = new ModelAndView("/upload");
	mav.addObject("promgr_name", promgr_name);
	mav.addObject("promgr_num",promgr_num);
	return mav;
		
	}
	
	/*다운로드 처리*/
	@RequestMapping(value = "/download")
	public ModelAndView downloadFile(String selectedFiles){
		String[] fileNums = selectedFiles.split(",");
		List downloadInfo = dao.getFileInfo(fileNums);
		return new ModelAndView("downloadFiles", "downloadInfo", downloadInfo);
	}
	/*삭제처리*/
	@RequestMapping(value = "/delete")
	public ModelAndView deleteFiles(String selectedFiles){
		ModelAndView mav = null;
		try{
		String[] fileNums = selectedFiles.split(",");
		List deleteInfo = dao.getDeleteList(fileNums);
		mav =  new ModelAndView("/delete", "deleteInfo",deleteInfo);
		} catch (NullPointerException e) {
			mav = new ModelAndView("/delete");
		}		
		return mav;
	}
	@RequestMapping(value="/deletePro")
	public String deletePro(int[] file_num){
		dao.DeleteFiles(file_num);
		return"redirect:delete";
	}
	/*폴더 만들기*/
	@RequestMapping(value = "/makeFolder", method=RequestMethod.GET)
	public ModelAndView makeFilder(){
		ModelAndView mav =  new ModelAndView("/makeFolder");
		return mav;
	}
	/*ajax 로 중복체크*/
	@RequestMapping(value = "/dupleCk")
	@ResponseBody
	public String duplicateCheck(@RequestParam(value="item")String item,@RequestParam(value = "folder", required = false) String folder,@RequestParam(value = "promgr_num", required = false) int promgr_num, HttpServletRequest request){
		folder = (promgr_num > 0)?dao.getPromgr_folder(promgr_num):folder;
		
		folder = (folder == "")? null:folder;
		int com_num = (int)request.getSession().getAttribute("com_num");
		String dupli = dao.duplicateCheck(item, folder, com_num); 
		return dupli;
	}
	/*폴더업로드*/
	@RequestMapping(value="/makeFolder", method=RequestMethod.POST)
	public String makeFolderPro(String folder, String item, HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int promgr_num){
		CloudInfo info =  new Cloud_makeFolder().uploadFolder(request, item, folder,promgr_num);
		dao.uploadFile(info);
		return "redirect:makeFolder?upload=ok";
	}
	/*상위폴더로*/
	@RequestMapping(value="/goUpper")
	public String goUpperFolder(String folder, HttpServletRequest request){
		String upperFolder = dao.findUpper(folder);
		String pathFolder = (upperFolder  == null||upperFolder == "")?"":"?folder="+upperFolder;
		String path = "redirect:main"+pathFolder;
		 return path;
	}
	/*ajax로 파일명 바꾸기*/
	@RequestMapping(value="/changeName", method = RequestMethod.POST)
	@ResponseBody
	public int changeName(@RequestParam(value = "file_num") String file_num,@RequestParam(value = "file_name")String file_name, HttpServletRequest request){
		int i = dao.changeFileName(file_num, file_name);
		return i;
	}
	
}
