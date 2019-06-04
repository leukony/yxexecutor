package com.yunxi.common.executor.execute;

/**
 * 执行器命令状态
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AsynExecutorCmdStatus.java, v 0.1 2019年5月14日 下午2:15:00 leukony Exp $
 */
public enum AsynExecutorCmdStatus {

    /**
     * 初始化
     */
    INIT,

    /**
     * 执行中
     */
    PROCESS,

    /**
     * 挂起
     */
    HOLD
}