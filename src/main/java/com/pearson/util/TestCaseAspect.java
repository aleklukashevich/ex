package com.pearson.util;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.util.ResultsUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.qameta.allure.util.ResultsUtils.getStatus;
import static io.qameta.allure.util.ResultsUtils.getStatusDetails;

@Aspect
public class TestCaseAspect {
    private static final ThreadLocal<AllureLifecycle> LIFECYCLE = InheritableThreadLocal.withInitial(Allure::getLifecycle);
    private static final String TEST_CASE_PATTERN = "(?<=)es_[0-9]{4}(?=_)";

    @Pointcut("@annotation(org.testng.annotations.Test)")
    public void withTestAnnotation() {
        //pointcut body, should be empty
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
        //pointcut body, should be empty
    }

    @Before("anyMethod() && withTestAnnotation()")
    public void testStart(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String methodName = methodSignature.getMethod().getName();
        Pattern p = Pattern.compile(TEST_CASE_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(methodName);
        final String[] testCaseId = {""};
        if (m.find()) {
            testCaseId[0] = m.group().replace("_", "-").toUpperCase();
        }
        getLifecycle()
                .updateTestCase((testResult -> {
                    if (!testCaseId[0].isEmpty()) {
                        testResult
                                .setLinks(List.of(ResultsUtils.createTmsLink(testCaseId[0])));
                    }
                }));
    }

//    @AfterThrowing(pointcut = "anyMethod() && withTestAnnotation()", throwing = "e")
//    public void stepFailed(final Throwable e) {
//        getLifecycle().updateStep(s -> s
//                .setStatus(getStatus(e).orElse(Status.BROKEN))
//                .setStatusDetails(getStatusDetails(e).orElse(null)));
//        getLifecycle().stopStep();
//    }
//
//    @AfterReturning(pointcut = "anyMethod() && withTestAnnotation()")
//    public void stepStop() {
//        getLifecycle().updateStep(s -> s.setStatus(Status.PASSED));
//        getLifecycle().stopStep();
//    }

    /**
     * For tests only.
     *
     * @param allure allure lifecycle to set.
     */
    public static void setLifecycle(final AllureLifecycle allure) {
        LIFECYCLE.set(allure);
    }

    public static AllureLifecycle getLifecycle() {
        return LIFECYCLE.get();
    }
}
