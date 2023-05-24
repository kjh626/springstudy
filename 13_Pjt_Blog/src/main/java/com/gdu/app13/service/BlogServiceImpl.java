package com.gdu.app13.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app13.domain.BlogDTO;
import com.gdu.app13.domain.MemberDTO;
import com.gdu.app13.mapper.BlogMapper;
import com.gdu.app13.util.MyFileUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

  private BlogMapper blogMapper;
  private MyFileUtil myFileUtil;
  
  @Override
  public void loadBlogList(HttpServletRequest requeset, Model model) {
    
    Map<String, Object> map = new HashMap<String, Object>();
    blogMapper.getBlogList(map);
  }
  
  @Override
  public void addBlog(HttpServletRequest request, HttpServletResponse response) {
    
    // 요청 파라미터
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    
    // DB로 보낼 BlogDTO 만들기 <= ★ MemberDTO를 먼저 만들어 줘야한다. ★
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setMemberNo(memberNo);
    BlogDTO blogDTO = new BlogDTO();
    blogDTO.setTitle(title);
    blogDTO.setContent(content);
    blogDTO.setMemberDTO(memberDTO);
    
    // DB로 BlogDTO 보내기 (삽입) 
    int addResult = blogMapper.addBlog(blogDTO);
    
    // 응답
    try {
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter(); 
      out.println("<script>");
      if(addResult == 1) {
        out.println("alert('블로그가 작성되었습니다.');");
        out.println("location.href='" + request.getContextPath() + "/blog/list.do';");
      } else {
        out.println("alert('블로그 작성이 실패했습니다.');");
        out.println("history.back();");
      }
      out.println("</script>");
      out.flush();
      out.close();
      
    } catch(Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {

    /*
     ajax처리할 때 우리가 보내준 것
      var formData = new FormData();
      formData.append('file', 첨부된파일);
      
      $.ajax({
        data: formData
      })
    
    */
    
    // formData에 저장된 file 꺼내기
    // 스프링에서 첨부된 파일의 타입은 MultipartFile이다. 우리 ajax에서 파일 보낼 때 여러개 보낸다? x files[i] 파일 하나씩 보낸다. List 필요 없음
    MultipartFile multipartFile = multipartRequest.getFile("file");     // append('file', ) 이 이름이 여기서 사용된다(file)
    
    // HDD에 저장할 경로
    String summernoteImagePath = myFileUtil.getSummernoteImagePath();
    
    // HDD에 저장할 경로 없으면 만들기
    File dir = new File(summernoteImagePath);
    if(dir.exists() == false) {
      dir.mkdirs();
    }
    
    // HDD에 저장할 파일의 이름(UUID.확장)
    String filesystemName = myFileUtil.getFilesystemName(multipartFile.getOriginalFilename());
    
    
    return null;
  }
}
