package com.yunxi.common.executor.execute;

/**
 * 执行器命令Push数量计算方式
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AsynExecutorCmdPushMode.java, v 0.1 2019年5月14日 下午4:18:56 leukony Exp $
 */
public enum AsynExecutorCmdPushMode {
    
    /** 最合理的模式，以MaxSize为上限 */
    MAX,
    /** 最悲观的模式，以CoreSize为上限 */
    CORE,
    ;
}