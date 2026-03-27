package bw.org.bocra.backend.aspect;

import bw.org.bocra.backend.model.AuditLog;
import bw.org.bocra.backend.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;

    @Pointcut("execution(* bw.org.bocra.backend.controller.AdminController.*(..)) || " +
              "execution(* bw.org.bocra.backend.controller.LicenseController.update*(..)) || " +
              "execution(* bw.org.bocra.backend.controller.NewsArticleController.create*(..)) || " +
              "execution(* bw.org.bocra.backend.controller.TenderController.create*(..))")
    public void adminActions() {}

    @AfterReturning(pointcut = "adminActions()", returning = "result")
    public void logAdminAction(JoinPoint joinPoint, Object result) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null) ? auth.getName() : "anonymous";
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = request.getRemoteAddr();

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        AuditLog log = AuditLog.builder()
                .action(className + "." + methodName)
                .username(username)
                .details("Executed administrative action: " + methodName)
                .ipAddress(ipAddress)
                .build();

        auditLogRepository.save(log);
    }
}
