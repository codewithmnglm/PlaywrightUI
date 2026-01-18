package com.listeners;

import com.framework.Constant;
import com.logs.TestLog;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetry = Constant.MAX_RETRY_COUNT;


    @Override
    public boolean retry(ITestResult result) {
        while(maxRetry>retryCount){
            retryCount++;
            TestLog.stepInfo(
                    "🔁 Retrying test: " +
                            result.getMethod().getMethodName() +
                            " | Attempt " + retryCount
            );
            return true;
        }
        return false;
    }
}
