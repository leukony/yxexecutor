package com.yunxi.common.executor;

import com.yunxi.common.executor.execute.AsynExecutor;
import com.yunxi.common.executor.execute.AsynExecutorCmd;

/**
 * 
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AsynExecutorFacadeImpl.java, v 0.1 2019年7月23日 下午2:24:31 leukony Exp $
 */
public class AsynExecutorFacadeImpl implements AsynExecutorFacade {

    /** 
     * @see com.yunxi.common.executor.AsynExecutorFacade#pushAsynCommand(com.yunxi.common.executor.execute.AsynExecutorCmd)
     */
    @Override
    public void pushAsynCommand(AsynExecutorCmd command) {
    }

    /** 
     * @see com.yunxi.common.executor.AsynExecutorFacade#regAsynExecutor(com.yunxi.common.executor.execute.AsynExecutor)
     */
    @Override
    public void regAsynExecutor(AsynExecutor<? extends AsynExecutorCmd> executor) {
    }
}