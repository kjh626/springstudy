package com.gdu.app03.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FifthServiceImpl implements IFifthService {

	@Override
	public ResponseEntity<String> papago(HttpServletRequest request) {
		try {
			// 요청 파라미터
			String source = request.getParameter("source");  // 원본 언어 코드(ko, en, ja 중 하나)
			String target = request.getParameter("target");  // 목적 언어 코드(ko, en, ja 중 하나)
			String text = request.getParameter("text");      // 번역할 텍스트
			
			// 클라이언트 아이디, 시크릿 (네이버개발자센터에서 발급 받은 본인 정보 사용합니다.)
			String clientId = "SNxZ6DGf354PLLcGPlnu";
			String clientSecret = "Lp2f019JKT";
			
			// API 주소
			String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
			
			// URL
			URL url = new URL(apiURL);
			
			// HttpURLConnection
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			// 요청 메소드
			con.setRequestMethod("POST");
			
			// 요청 헤더에 포함하는 내용
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			// Papago API로 보내야 하는 파라미터(source, target, text)
			String params = "source=" + source + "&target=" + target + "&text=" + URLEncoder.encode(text, "UTF-8");
			
			// Papago API로 파라미터를 보내기 위해서 출력 스트림 생성
			con.setDoOutput(true);
			DataOutputStream dos = new DataOutputStream(con.getOutputStream());
			
			// Papago API로 파라미터 보내기
			dos.write(params.getBytes());
			dos.flush();
			dos.close();
			
			// Papago API로부터 번역 결과를 받아 올 입력 스트림 생성
			BufferedReader reader = null;
			if(con.getResponseCode() == 200) {
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			// Papago API로부터 번역 결과를 받아서 StringBuilder에 저장
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			// Papago API로부터 받은 번역 결과 자체가 문자열 형식의 JSON 데이터이다.
			// 따라서 받은 내용을 그대로 보내준다.
			// 여기서 보내는 데이터의 타입은 String으로 처리하지만,
			// $.ajax에서 받는 데이터의 타입은 'json'으로 처리해 준다.(dataType: 'json')
			return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
			
		} catch(Exception e) {

			return new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE);
			
		}
	}
	

	@Override
	public ResponseEntity<String> search(HttpServletRequest request) {
		
        String clientId = "SNxZ6DGf354PLLcGPlnu";
		String clientSecret = "Lp2f019JKT";

        String text = null;
        String sort = request.getParameter("sort");
        String display = request.getParameter("display"); 
        		
        try {
            text = URLEncoder.encode(request.getParameter("query"), "UTF-8");
            sort = URLEncoder.encode(request.getParameter("sort"), "UTF-8");
            display = URLEncoder.encode(request.getParameter("display"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/shop.json?query=" + text + "&sort=" + sort + "&display=" + display;    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);
        
       System.out.println(text + display + sort);
        System.out.println(responseBody);
		return new ResponseEntity<String>(responseBody, HttpStatus.OK);
		
    }


    private static String get(String apiURL, Map<String, String> requestHeaders){
        
    	HttpURLConnection con = connect(apiURL);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiURL){
        try {
            URL url = new URL(apiURL);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiURL, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiURL, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
            
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
		

}
