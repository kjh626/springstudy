package com.gdu.movie.util;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
  
  // 크로스 사이트 스크립팅(Cross Site Scripting) 방지하기
  public String preventXSS(String str) { // 전달된 String을 가공을 해서 반환을 해준다(간단). "<" 로 들어오면 &lt;("<") 코드로 바꿔서 반환한다. <script> -> &lt;script&gt; -> 웹화면에서는 <script>로 보임
    str = str.replace("<", "&lt;");
    str = str.replace(">", "&gt;");
    return str;
  }
  
}
