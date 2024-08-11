package manev.damyan.inventory.inventory.items;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ItemsServiceAspect {

    @Before("getItemByIdItemPointcut()")
    public void logBeforeGetItemById() {
        System.out.println("[DPM] Before 'getItemById' in Item controller");
    }


    @After("getItemByIdItemPointcut()")
    public void logAfterGetItemById() {
        System.out.println("[DPM] After 'getItemById' in Item controller");
    }

    @Pointcut("execution(public java.util.Optional<manev.damyan.inventory.inventory.items.ItemDTO> manev.damyan.inventory.inventory.items.ItemsService.getItem(long))")
    public void getItemByIdItemPointcut() {}



    @Pointcut("execution(public String getName())")
	public void getNameGenericPointcut(){}


    @Before("getNameGenericPointcut()")
    public void getNameGenericAdvice() {
        System.out.println("[DPM] getName() method will be executed!");
    }

    @Before("execution(public java.util.List<manev.damyan.inventory.inventory.items.ItemDTO> manev.damyan.inventory.inventory..getA*(..))")
    public void beforeGetAll(JoinPoint joinPoint) {
        System.out.println("[DPM] Before getAll in ItemsController:");
    }

 @After("execution(public * manev.damyan.inventory.inventory.items.*ItemsSer*.getAllByName(String)) && args(name)")
    public void logStringArguments(String name) {
        System.out.println("Running After getAllByName in Item controller. String argument passed = " + name);
    }

    @AfterThrowing(value = "within(manev.damyan.inventory.inventory.items.ItemsService)", throwing = "e")
    public void afterThrowingInFakeMethod(JoinPoint joinPoint, Exception e) {
        System.out.println("[DPM] Exception thrown in the fake method: " + e.getClass() + "" + e.getMessage());
    }

    @Before("@annotation(manev.damyan.inventory.inventory.items.Loggable)")
    public void beforeLoggableAnnotation(JoinPoint joinPoint) {
        System.out.println("[DPM] Logging before method [" + joinPoint.getSignature().getName() + "] because it is annotated with @Loggable");
    }

    @Around("execution(* manev.damyan.inventory.inventory.items.ItemsService.getAllItems(..))")
    public Object aroundGetAll(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("[DPM] [AROUND] BEFORE getAllItems");
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        System.out.println("[DPM] [AROUND] Returned value from getAllItems is:" + result);

        System.out.println("[DPM] [AROUND] AFTER getAllItems");

        return result;
    }
}
