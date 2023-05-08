package com.gdu.app11.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app11.mapper.UploadMapper;
import com.gdu.app11.util.MyFileUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor  // filed의 @Autowired 처리
public class UploadServiceImpl implements UploadService {

	// field
	private UploadMapper uploadMapper;
	private MyFileUtil myFileUtil;
	
	@Override
	public int addUpload(MultipartHttpServletRequest multipartRequest) {

		/* Upload 테이블에 UploadDTO 넣기 */
		
		
		
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
					
					// 썸네일
					
					
					/* DB에 첨부파일 정보 저장하기 */
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
		return 0;
	}

}
