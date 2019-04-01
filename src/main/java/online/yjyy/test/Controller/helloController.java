package online.yjyy.test.Controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import online.yjyy.test.Controller.service.userService;
import online.yjyy.test.Mapper.UserMapper;
import online.yjyy.test.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class helloController {

    @Autowired
    UserMapper userMapper;



    @RequestMapping(value = "sayhello")
    public String sayhello(Model model){
        String name ="xiaoming";
        userBean user = new userBean();
        user.setName("小妹");
        user.setPassword("123");
        model.addAttribute("user",user);
        System.out.println("hell");
        return "test";
    }

    @RequestMapping(value = "ajax.do")
    @ResponseBody
    public String ajaxs( HttpServletRequest request){
        System.out.println(request.getParameter("name"));
        System.out.println(  request.getParameter("pwd"));
        return "success";
    }
    @RequestMapping(value = "/i_ajax.do")
    @ResponseBody
    public String iajaxs(@RequestBody String name,@RequestBody String password, HttpServletRequest request){
        System.out.println(name);
        System.out.println( password);
        return "success";
    }

    @RequestMapping(value = "getValue.do")
    public ModelAndView getValue(HttpServletRequest request){
   String name = request.getParameter("user");
        String pwd = request.getParameter("password");
        System.out.println("user"+name);
        System.out.println("pwd"+pwd);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("receivedValue");
        modelAndView.addObject("user",name);
        modelAndView.addObject("pwd",pwd);
       return modelAndView;
    }
    @RequestMapping(value = "receivedValue")
    public String value(){
        return "receivedValue";
    }

  /*  @RequestMapping(value = "queryUser.do")
    @ResponseBody
    public Page<User> queryUser(){
        System.out.println("进入方法");
        Result result = new Result();
        PageHelper.startPage(1, 3);
        Page<User> userList = userMapper.getUserList();
       // result.setData(userList);
        return userList;
    }*/


    @RequestMapping(value = "testUser")
    //@ResponseBody
    public String queryUserBypage(HttpServletRequest request,Model model){
        System.out.println("进入方法");
        String pageNum = request.getParameter("pageNo");
        int ret=1;
        if(pageNum!=null&&!"".equals(pageNum)) {
            ret = Integer.parseInt(pageNum);
        }
        PageHelper.startPage(ret,2);
        final Page<User> userList = userMapper.getUserList();
        PageInfo pageInfo=new PageInfo(userList,3);
        List list = pageInfo.getList();
        for (Object o : list) {
            System.out.println("11111111111111"+o);
        }
        System.out.println(pageInfo);
         model.addAttribute("list",list);
        model.addAttribute("pageInfo",pageInfo);
        return "pages";
    }

  @RequestMapping(value = "queryUser.do")
  @ResponseBody
  public Result queryUser(@RequestBody User user){
      System.out.println("进入方法");
      Result result = new Result();
      PageHelper.startPage(user.getCurrentPage(), user.getPageSize());
     List userList = userMapper.getUserPageInfo();
      PageInfo pageInfo=new PageInfo();
      pageInfo.setList(userList);
      System.out.println(pageInfo);
       result.setData(pageInfo);
      return result;
  }




    public List<User> addusers(){
        List list=new ArrayList();
        for (int i = 0; i < 50; i++)
        {
            userBean bean = new userBean();
            bean.setName("xiaoming"+i);
            bean.setPassword("aaaa"+i);
            list.add(bean);

        }
        return list;

    }


   /* @RequestMapping(value = "/downloadFile")
    public void downloadFile(String url, String fileName,
                             HttpServletResponse response,HttpServletRequest request) throws Exception{

        //要对文件名称进行编码
        fileName = java.net.URLEncoder.encode(fileName,"utf-8");
        response.addHeader("Content-Disposition","attachment;filename=" + fileName+";filename*=utf-8''"+fileName);
        response.setContentType("application/octet-stream"); //设置文件MIME类型

        OutputStream out =null;
        InputStream in=null;

        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
        //String path = request.getServletContext().getRealPath("/");
        //url = path + "download\\" + "我的客户导入模板.xls";

        try {

            URL urlPath = new URL(url);// 创建URL对象
            in = urlPath.openStream();// 获取url中的输入流
            out = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(in);
            BufferedOutputStream bos = new BufferedOutputStream(out);

            byte[] buff = new byte[20480];
            int b = 0;
            while (-1 != (b = bis.read(buff))) {
                bos.write(buff, 0, b);
            }
            bis.close();
            bos.flush();
            bos.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        finally {
            if(out!=null)out.close();
            if(in!=null)in.close();
        }
    }
*/


    @RequestMapping(value = "/downloadFile")
    public void downloadFile(HttpServletResponse response,HttpServletRequest request) throws Exception{
    String[] rowsName = {"用户名","密码"};
    int[] colWidth = {180, 180};
    List<Object[]> dataList =null;
    ExportExcelUtil ex = new ExportExcelUtil("excel模版", "excel模版", rowsName, colWidth,new ArrayList<>(),response);
    ex.export();

    }
    @RequestMapping(value = "/import")
    public String exImport(@RequestParam(value = "filename") MultipartFile file, HttpSession session) {

        boolean a = false;
        userService userService = new userService();
        String fileName = file.getOriginalFilename();
        System.out.println("文件上传"+fileName);
        try {

            a = userService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }
}



