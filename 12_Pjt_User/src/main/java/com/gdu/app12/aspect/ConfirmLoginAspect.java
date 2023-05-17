package com.gdu.app12.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy
@Component
public class ConfirmLoginAspect {

  // 포인트컷 : ConfirmLoginAspect를 동작시킬 메소드
  @Pointcut("execution()")
  
  
  
}
