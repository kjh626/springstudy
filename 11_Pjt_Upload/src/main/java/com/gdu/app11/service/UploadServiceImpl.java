package com.gdu.app11.service;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app11.domain.AttachDTO;
import com.gdu.app11.domain.UploadDTO;
import com.gdu.app11.mapper.UploadMapper;
import com.gdu.app11.util.MyFileUtil;
import com.gdu.app11.util.PageUtil;

import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@AllArgsConstructor  // field의 @Autowired 처리
public class UploadServiceImpl implements UploadService {

	// field
	private UploadMapper uploadMapper;
	private MyFileUtil myFileUtil;
	private PageUtil pageUtil;
	
	// ★권장사항 : Pagination 처리 해 보기★
	@Override
	public void getUploadList(HttpServletRequest request, Model model) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		int totalRecord = uploadMapper.getUploadCount();
		
		int recordPerPage = 5;
		
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		List<UploadDTO> uploadList = uploadMapper.getUploadList(map);
		model.addAttribute("uploadList", uploadList);  // 모델에 저장해서 포워드 시키기 위함
		model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
		model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/upload/list.do"));
		
	}
	
	@Transactional(readOnly = true)    	// INSERT문을 2개 이상 수행하기 때문에 트랜잭션 처리가 필요하다.
	@Override
	public int addUpload(MultipartHttpServletRequest multipartRequest) {

		/* Upload 테이블에 UploadDTO 넣기 */
		// 게시글 번호(uploadNo)를 얻기 위해서 먼저 Upload 테이블에 UploadDTO 넣기를 해줘야 한다.
		
		// 제목, 내용 파라미터
		String uploadTitle = multipartRequest.getParameter("uploadTitle");
		String uploadContent = multipartRequest.getParameter("uploadContent");
		
		// DB로 보낼 UploadDTO 만들기
		UploadDTO uploadDTO = new UploadDTO();
		uploadDTO.setUploadTitle(uploadTitle);
		uploadDTO.setUploadContent(uploadContent);
		
		// DB로 UploadDTO 보내기
		int uploadResult = uploadMapper.addUpload(uploadDTO);  // <selectKey>에 의해서 uploadDTO 객체의 uploadNo 필드에 UPLOAD_SEQ.NEXTVAL값이 저장된다.
		
		
		/* Attach 테이블에 AttachDTO 넣기 */
		
		// 첨부된 파일 목록
		List<MultipartFile> files = multipartRequest.getFiles("files");   // <input type="file" name="files">
		
		// 제목, 내용만 적고 작성완료 눌렀으면 첨부된 파일이 있는지 확인해봐야함
		// 첨부된 파일이 있는지 체크
		if(files != null && files.isEmpty() == false) {
			
			// 첨부된 파일 목록 순회
			for(MultipartFile multipartFile : files) {
				
				// 예외 처리
				try {
					
					/* HDD에 첨부 파일 저장하기 */
					
					// 첨부 파일의 저장 경로
					String path = myFileUtil.getPath();
					
					// 첨부 파일의 저장 경로가 없으면 만들기
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					// 첨부 파일의 원래 이름
					String originName = multipartFile.getOriginalFilename();
					originName = originName.substring(originName.lastIndexOf("\\") + 1);    // IE는 전체 경로가 오기 때문에 마지막 역슬래시 뒤에 있는 파일명만 사용한다.  
					// ...\...\...\...\...\ 이렇게 나오는 경우가 있음(IE 경로): 경로가 포함돼서 파일이름 나온다.. => 마지막 \ 다음부터 파일이름 가져오기
					
					// 첨부 파일의 저장 이름
					String filesystemName = myFileUtil.getFilesystemName(originName);
					
					// 첨부 파일의 File 객체 (HDD에 저장할 첨부 파일)
					File file = new File(dir, filesystemName);
					
					// 첨부 파일을 HDD에 저장 (multipartFile 파일 저장 메소드 있음)
					multipartFile.transferTo(file);  // 실제 서버에 저장된다.
					
					// 썸네일(첨부 파일이 이미지인 경우에만 썸네일이 가능)
					// originName,filesystemName 을 활용하지 않고 파일의 확장자를 구하는 다른 방법 (원래이름,저장이름을 쓰면 코드가 중복된다)
					
					// 첨부 파일의 Content-Type 확인
					String contentType = Files.probeContentType(file.toPath());  // probeContentType은 path를 매개변수로 받는다.(file.toPath()메소드로 쉽게 처리가능)
																				 // 이미지 파일의 Content-Type : image/jpeg, image/png, image/gif, ... (image로 시작하느냐를 체크하면 되겠다)

					// DB에 저장할 썸네일 유무 정보 처리
					boolean hasThumbnail = contentType != null && contentType.startsWith("image"); // 초기값은 false로 잡아준다(있는 지 없는 지 모르니까)
					
					// 첨부 파일의 Content-Type이 이미지로 확인되면 썸네일로 만듬
					if(hasThumbnail) {
						
						// HDD에 썸네일 저장하기 (thumbnailator 디펜던시)
						File thumbnail = new File(dir, "s_" + filesystemName);   // toFile() 우리가 어떻게 저장할 지를 약속을 정해서 하면 된다.(s_ 붙인다) 
						Thumbnails.of(file)
								.size(50, 50)
								.toFile(thumbnail);
					}
					
					
					/* DB에 첨부파일 정보 저장하기 */
					
					// DB로 보낼 AttachDTO 만들기
					AttachDTO attachDTO = new AttachDTO();
					attachDTO.setFilesystemName(filesystemName);
					attachDTO.setHasThumbnail(hasThumbnail ? 1 : 0);
					attachDTO.setOriginName(originName);
					attachDTO.setPath(path);
					attachDTO.setUploadNo(uploadDTO.getUploadNo());
					
					// DB로 AttachDTO 보내기
					uploadMapper.addAttach(attachDTO);
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
		return uploadResult;
	}
	
	@Override
	public void getUploadByNo(int uploadNo, Model model) {
		model.addAttribute("upload", uploadMapper.getUploadByNo(uploadNo));
		model.addAttribute("attachList", uploadMapper.getAttachList(uploadNo));
	}
	
	@Override
	public ResponseEntity<byte[]> display(int attachNo) {

		AttachDTO attachDTO = uploadMapper.getAttachByNo(attachNo);
		
		ResponseEntity<byte[]> image = null;
		
		try {
			// 썸네일이미지를 바이트배열로 바꾼 걸 넣어준다.
			File thumbnail = new File(attachDTO.getPath(), "s_" + attachDTO.getFilesystemName());
			image = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnail), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return image;
	}

}
