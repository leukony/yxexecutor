package com.yunxi.common.executor;

import com.yunxi.common.executor.execute.AsynExecutor;
import com.yunxi.common.executor.execute.AsynExecutorCmd;

/**
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AsynExecutorFacade.java, v 0.1 2019年7月23日 下午2:24:19 leukony Exp $
 */
public interface AsynExecutorFacade {
    
    /**
     * 
     * 
     * @param command
     */
    void pushAsynCommand(AsynExecutorCmd command);
    
    /**
     * 
     * 
     * @param executor
     */
    void regAsynExecutor(AsynExecutor<? extends AsynExecutorCmd> executor);
}