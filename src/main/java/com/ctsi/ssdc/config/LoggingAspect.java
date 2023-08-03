package com.ctsi.ssdc.config;

import com.alibaba.fastjson.JSON;
import com.ctsi.ssdc.admin.domain.dto.CscpLogLoginDTO;
import com.ctsi.ssdc.admin.domain.dto.CscpLogOperationDTO;
import com.ctsi.ssdc.admin.service.CscpLogLoginService;
import com.ctsi.ssdc.admin.service.CscpLogOperationService;
import com.ctsi.ssdc.annotation.Log;
import com.ctsi.ssdc.controller.UserJwtController;
import com.ctsi.ssdc.model.UserForm;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.ctsi.ssdc.util.RequestUtil;
import com.ctsi.ssdc.utils.IpUtils;
import com.ctsi.ssdc.utils.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * By default, it only runs with the "dev" profile.
 *
 * @author ctsi biyi generator
 *
 */
@Aspect
public class LoggingAspect {

    private static final String SPRING_PROFILE_DEVELOPMENT = "dev";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Environment env;

    public LoggingAspect(Environment env) {
        this.env = env;
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)")
        //" || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.ctsi.ssdc.admin.repository..*)"+
        " || within(com.ctsi.ssdc.admin.service..*)")
       // " || within(com.ctsi.ssdc.admin.web..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of(SPRING_PROFILE_DEVELOPMENT))) {
            log.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
					joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL", e.getMessage(), e);

        } else {
            log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL");
        }
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }
    
    @Autowired
	private CscpLogOperationService logService;
	
	@Autowired
	private CscpLogLoginService loginService;
	
	@Value("${ctsi.log.operation-log.enable:false}")
	private boolean operationEnable;
	
	@Value("${ctsi.log.login-log.enable:false}")
	private boolean loginEnable;
	
	/**
	 * login pointcut
	 */
	@Pointcut("execution(* com.ctsi.ssdc.controller.UserJwtController.authorize(..))")
	private void loginPointCut() {}

	@AfterReturning(pointcut="loginPointCut()",returning = "result")
	public void doAfterLogin(JoinPoint joinPoint, ResponseEntity<UserJwtController.JwtToken> result) {
		if (this.loginEnable) {
//            this.loginLog(joinPoint, (Exception)null);
			CscpLogLoginDTO logdto = createLogLoginDTO(joinPoint);

			if((result != null && result.getStatusCode().equals(HttpStatus.UNAUTHORIZED))){
				logdto.setStatus(CscpLogOperationDTO.ERROR);
				logdto.setMessage("登录失败,"+result.getStatusCode());
			}else{
				logdto.setStatus(CscpLogOperationDTO.SUCCESS);
				logdto.setMessage("登录成功");
			}
			loginService.insert(logdto);

		}
	}

	@AfterThrowing(
			pointcut = "loginPointCut()",
			throwing = "ex"
	)
	public void doAfterLogin(JoinPoint joinPoint, Exception ex) {
		if (this.loginEnable) {
//            this.loginLog(joinPoint, ex);
			CscpLogLoginDTO logdto = createLogLoginDTO(joinPoint);

			if(ex!=null){
				logdto.setStatus(CscpLogOperationDTO.ERROR);
				logdto.setMessage("登录失败,"+ex.getMessage());
			}
			loginService.insert(logdto);
		}

	}

	private CscpLogLoginDTO createLogLoginDTO(JoinPoint joinPoint) {
		CscpLogLoginDTO logdto = new CscpLogLoginDTO();

		String username = this.getUserName(joinPoint);
		logdto.setUserName(username);
		// 请求的地址
		String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		logdto.setIp(ip);
		logdto.setTime(ZonedDateTime.now(ZoneId.systemDefault()));
		logdto.setTeantId(SecurityHxUtils.getCurrentTenantId());
		return logdto;
	}


	/**
	 * insert login information to database
	 * @param joinPoint
	 * @param ex
	 */
	private void loginLog(JoinPoint joinPoint, Exception ex) {
		CscpLogLoginDTO logdto=new CscpLogLoginDTO();
		
		String username=getUserName(joinPoint);
		logdto.setUserName(username);
		// 请求的地址
		String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		logdto.setIp(ip);
		logdto.setTime(ZonedDateTime.now(ZoneId.systemDefault()));
		logdto.setTeantId(SecurityHxUtils.getCurrentTenantId());
		//MethodSignature method = (MethodSignature)joinPoint.getSignature();
		if(ex==null) {
			logdto.setStatus(CscpLogOperationDTO.SUCCESS);
			logdto.setMessage("登录成功");
		}else {
			logdto.setStatus(CscpLogOperationDTO.ERROR);
			logdto.setMessage("登录失败,"+ex.getMessage());
		}

		loginService.insert(logdto);
	}
	
	/**
	 * operation pointcut
	 */
	@Pointcut("(execution(* com.ctsi.ssdc.controller.*Controller.*(..))"
			+ "&& !execution(* com.ctsi.ssdc.controller.UserJwtController.*(..)))")
	private void ssdcPointCut() {}
	
	/**
	 * operation pointcut
	 */
	@Pointcut("(execution(* com.ctsi.ssdc.admin.web.*Controller.*(..)) "
			+ "&& !execution(* com.ctsi.ssdc.admin.web.*Controller.*init*(..))"
			+ "&& !execution(* com.ctsi.ssdc.admin.web.CscpLogOperationController.*(..))"
			+ "&& !execution(* com.ctsi.ssdc.admin.web.CscpLogLoginController.*(..)))")
	private void ssdcAdminPointCut() {}
	
	@AfterReturning(pointcut="ssdcPointCut() || ssdcAdminPointCut()")
	public void doAfterOperation(JoinPoint joinPoint) {
		if(operationEnable) {
			operationLog(joinPoint, null, null);
		}
	}
	
	@AfterThrowing(pointcut="ssdcPointCut() || ssdcAdminPointCut()", throwing="ex")
	public void doAfterOperation(JoinPoint joinPoint, Exception ex) {
		if(operationEnable) {
			operationLog(joinPoint, null, ex);
		}
	}
	
	/**
	 * insert operation information into database
	 * @param joinPoint
	 * @param logInfo
	 * @param ex
	 */
	private void operationLog(JoinPoint joinPoint, Log logInfo, Exception ex) {
		CscpLogOperationDTO logdto=new CscpLogOperationDTO();
		
		logdto.setUserid(SecurityHxUtils.getCurrentUserId());
		logdto.setUsername(SecurityHxUtils.getCurrentUserName());
		
		logdto.setUri(RequestUtil.getURI());
		//获取请求参数
		String requestMethod =ServletUtils.getRequest().getMethod();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))
		{
			String params = argsArrayToString(joinPoint.getArgs());
			logdto.setParams(StringUtils.substring(params,0,3000));
		} else {
			logdto.setParams(StringUtils.substring(RequestUtil.getParams(),0,3000));
		}
		// 请求的地址
		String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		logdto.setIp(ip);
		MethodSignature method = (MethodSignature)joinPoint.getSignature();
		logdto.setMethod(method.toString());

		
		logdto.setTime(ZonedDateTime.now(ZoneId.systemDefault()));
		
		if(logInfo!=null) {
			logdto.setMessage(logInfo.message());
		}
		
		if(ex==null) {
			logdto.setStatus(CscpLogOperationDTO.SUCCESS);
		}else {
			logdto.setStatus(CscpLogOperationDTO.ERROR);
			logdto.setError(StringUtils.substring(ex.getMessage(), 0, 1000));
		}
		logdto.setTeantId(SecurityHxUtils.getCurrentTenantId());
		logService.insert(logdto);
	}

	/**
	 * 参数拼装
	 */
	private String argsArrayToString(Object[] paramsArray)
	{
		StringBuffer params = new StringBuffer();
		if (paramsArray != null && paramsArray.length > 0)
		{
			for (Object o : paramsArray)
			{
				if (null != o)
				{
					try
					{
						Object jsonObj = JSON.toJSON(o);
						params.append(jsonObj.toString());
						params.append(" ");
					}
					catch (Exception e)
					{
					}
				}
			}
		}
		return params.toString().trim();
	}

	
	@AfterReturning("@annotation(com.ctsi.ssdc.annotation.Log) && @annotation(annoLog)")
	public void doAfterAnnotation(JoinPoint joinPoint, Log annoLog) {
		if(operationEnable) {
			operationLog(joinPoint, annoLog, null);
		}
	}
	
	@AfterThrowing(pointcut="@annotation(com.ctsi.ssdc.annotation.Log) && @annotation(annoLog)", throwing="ex")
	public void doAfterAnnotation(JoinPoint joinPoint, Log annoLog, Exception ex) {
		if(operationEnable) {
			operationLog(joinPoint, annoLog, ex);
		}
	}
	
	/**
	 * 获取登录用户名
	 * @param joinPoint
	 * @return
	 */
	private String getUserName(JoinPoint joinPoint) {
		Object[] args=joinPoint.getArgs();
		if(args!=null && args.length>0) {
			for(int index=0;index<args.length;index++) {
				if(args[index]!=null && (args[index] instanceof UserForm) ) {
					UserForm userform = (UserForm)args[index];
					return userform.getUsername();
				}
			}
		}
		return null;
	}
}
