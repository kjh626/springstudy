package com.gdu.movie.batch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.movie.domain.MovieDTO;
import com.gdu.movie.domain.QueryDTO;
import com.gdu.movie.mapper.MovieMapper;
import com.gdu.movie.util.SecurityUtil;

@Component
@EnableScheduling
public class FindComedyScheduler {

	@Autowired
	private MovieMapper movieMapper;
	@Autowired
	private SecurityUtil securityUtil;
	
	
	@Scheduled(cron="0 0/1 * 1/1 * ?")
	public void execute() {
		QueryDTO queryDTO = new QueryDTO();
		queryDTO.setColumn("GENRE");
		queryDTO.setSearchText( securityUtil.preventXSS( "코미디" ) );
		List<MovieDTO> list = movieMapper.getMoviesByQuery(queryDTO);
		BufferedWriter writer = null;
		try {
      
		  
		  if(list.isEmpty()) {
		    
		    File file = new File("error.txt");
		    writer = new BufferedWriter(new FileWriter(file));
		    writer.write("코미디 검색 결과가 없습니다.");
		    
		    
	    } else {
	      
	      File file = new File("코미디.txt");
	      writer = new BufferedWriter(new FileWriter(file));
	      StringBuilder sb = new StringBuilder();
	      for(int i = 0; i < list.size(); i++) {
  	      sb.append("제목 : " + list.get(i).getTitle() + "\n");
  	      sb.append("장르 : " + list.get(i).getGenre() + "\n");
  	      sb.append("개요 : " + list.get(i).getDescription() + "\n");
  	      sb.append("평점 : " + list.get(i).getStar() + "\n");
	      }
	      writer.write(sb.toString());
	    }
		  
		  writer.close();
		  
    } catch (Exception e) {
      e.printStackTrace();
    }
		
	}
	
}
