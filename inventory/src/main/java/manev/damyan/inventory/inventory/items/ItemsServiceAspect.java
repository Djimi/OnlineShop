package manev.damyan.inventory.inventory.items;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ItemsServiceAspect {

    @Before("getItemByIdItemPointcut()")
    public void logBeforeGetItemById() {
        log.info("[DPM] Before 'getItemById' in Item controller");
    }


    @After("getItemByIdItemPointcut()")
    public void logAfterGetItemById() {
        log.debug("[DPM] After 'getItemById' in Item controller");
    }

    @Pointcut("execution(public java.util.Optional<manev.damyan.inventory.inventory.items.ItemDTO> manev.damyan.inventory.inventory.items.ItemsService.getItem(long))")
    public void getItemByIdItemPointcut() {}



    @Pointcut("execution(public String getName())")
	public void getNameGenericPointcut(){}


    @Before("getNameGenericPointcut()")
    public void getNameGenericAdvice() {
        log.debug("[DPM] getName() method will be executed!");
    }

    @Before("execution(public java.util.List<manev.damyan.inventory.inventory.items.ItemDTO> manev.damyan.inventory.inventory..getA*(..))")
    public void beforeGetAll(JoinPoint joinPoint) {
        log.debug("[DPM] Before getAll in ItemsController:");
    }

 @After("execution(public * manev.damyan.inventory.inventory.items.*ItemsSer*.getAllByName(String)) && args(name)")
    public void logStringArguments(String name) {
        log.debug("Running After getAllByName in Item controller. String argument passed = " + name);
    }

    @AfterThrowing(value = "within(manev.damyan.inventory.inventory.items.ItemsService)", throwing = "e")
    public void afterThrowingInFakeMethod(JoinPoint joinPoint, Exception e) {
        log.debug("[DPM] Exception thrown in the fake method: " + e.getClass() + "" + e.getMessage());
    }

    @Before("@annotation(manev.damyan.inventory.inventory.items.Loggable)")
    public void beforeLoggableAnnotation(JoinPoint joinPoint) {
        log.debug("[DPM] Logging before method [" + joinPoint.getSignature().getName() + "] because it is annotated with @Loggable");
    }

    @Around("execution(* manev.damyan.inventory.inventory.items.ItemsService.getAllItems(..))")
    public Object aroundGetAll(ProceedingJoinPoint proceedingJoinPoint) {
        log.debug("[DPM] [AROUND] BEFORE getAllItems");
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        log.debug("[DPM] [AROUND] Returned value from getAllItems is:" + result);

        log.debug("[DPM] [AROUND] AFTER getAllItems");

        return result;
    }
}
