package com.fpt.springtraining.logic.aspects;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fpt.springtraining.data.entities.AssRight;
import com.fpt.springtraining.exceptions.FailAuthenticationException;

/**
 * Aspect to prevent wrong access 
 * 
 * @author ThaiTC
 *
 */
@Component
@Aspect
public class AuthenticationAspect {
	
	/**
	 * Check session before going to join point 
	 * 
	 * @param joinPoint
	 * @throws FailAuthenticationException
	 * @throws RuntimeException
	 */
	@Before(
		value = "@within(com.fpt.springtraining.logic.aspects.SessionLookUp) || " +
				"@annotation(com.fpt.springtraining.logic.aspects.SessionLookUp)"
	)
    public void before(JoinPoint joinPoint) throws FailAuthenticationException, RuntimeException {
        LogFactory.getLog(
        	AuthenticationAspect.class
        ).info(
        	"monitor.before, class: " + 
        	joinPoint.getSignature().getDeclaringType().getSimpleName() + 
        	", method: " + joinPoint.getSignature().getName()
        );
        
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        
   		String username 	= (String)  session.getAttribute("username");
   		
   		@SuppressWarnings("unchecked")
		List<AssRight> role	= (List<AssRight>)  session.getAttribute("role");
   		
   		Boolean isLogined 	= (Boolean) session.getAttribute("isLogined");
        
        if (	session == null || username 	== null || username.isEmpty() || 
        		role 	== null || role.isEmpty() 		|| isLogined 	== null
        ) {
        	throw new FailAuthenticationException();
        }
    
    }

    @After(
    	value = "@within(com.fpt.springtraining.logic.aspects.SessionLookUp) || " +
    			"@annotation(com.fpt.springtraining.logic.aspects.SessionLookUp)"
    )
    public void after(JoinPoint joinPoint) throws Throwable {
        LogFactory.getLog(
        	AuthenticationAspect.class
        ).info(
        	"monitor.after, class: " + 
        	joinPoint.getSignature().getDeclaringType().getSimpleName() + 
        	", method: " + joinPoint.getSignature().getName()
        );
    }
}