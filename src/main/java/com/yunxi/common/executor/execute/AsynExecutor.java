package com.yunxi.common.executor.execute;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 执行器
 * @author <a href="mailto:leukony@yeah.net">leukony</a>
 * @version $Id: AsynExecutor.java, v 0.1 2019年1月9日 上午10:14:47 leukony Exp $
 */
public abstract class AsynExecutor<T extends AsynExecutorCmd> implements InitializingBean {
    
    /**
     * 重试限制
     */
    protected int                    retryLimit;

    /**
     * 重试间隔 
     */
    protected List<Long>             retryInterval = new ArrayList<Long>();

    /**
     * 处理中状态超时时间,单位秒(S)
     * <ul>
     *  <li>如果当前时间大于下次执行时间与此值的和且重试次数未到则重试</li>
     * </ul>
     */
    private int                      timeout       = 120;

    /**
     * 每次Push的命令数量计算方式
     */
    private AsynExecutorCmdPushMode  pushMode      = AsynExecutorCmdPushMode.MAX;

    /**
     * 执行器线程池 
     */
    protected ThreadPoolTaskExecutor executor;

    /**
     * 执行器前置逻辑
     * @param command
     */
    protected abstract void doBefore(T command);

    /**
     * 执行器业务逻辑
     * @param command
     */
    protected abstract void doExecutor(T command);

    /**
     * 执行器后置逻辑
     * @param command
     */
    protected abstract void doAfter(T command);

    /**
     * 投放执行器命令到执行器线程池对应的缓冲队列 。
     * <p style="color:red;font-weight:bold">注意:可能会因为缓冲队列已满导致投放失败</p>
     * @param cmdList
     */
    protected final void toExecutor(T command) {
        try {
            executor.execute(new AsynExecutorRunner(command));
        } catch (TaskRejectedException e) {
            // TODO LOG
        } catch (Exception e) {
            // TODO LOG
        }
    }

    /**
     * 批量投放执行器命令到执行器线程池对应的缓冲队列 。
     * <p style="color:red;font-weight:bold">注意:可能会因为缓冲队列已满导致投放失败</p>
     * @param cmdList
     */
    protected final void batchToExecutor(final List<T> cmdList) {
        for (final T command : cmdList) {
            toExecutor(command);
        }
    }

    /**
     * 建议push的命令数量
     * @return
     */
    protected int getPushCmdCount() {
        switch (pushMode) {
            case MAX:
                return executor.getMaxPoolSize() - executor.getActiveCount();
            case CORE:
                return executor.getCorePoolSize() - executor.getActiveCount();
            default:
                return executor.getMaxPoolSize() - executor.getActiveCount();
        }
    }

    /**
     * 重试间隔描述
     * <ul>
     *  <li>间隔描述的单位是S</li>
     *  <li>举例：1,2,3,4,5 。第一次重试间隔1S，第二次重试间隔2S，依次类推。假设总的重试次数为10，在第6-10次重试时使用5S的间隔</li>
     *  <li style="color:red;font-weight:bold">如果重试是在代码中指定重试间隔则以指定间隔为准</li>
     * </ul>
     * @param workRetryInterval 
     */
    public final void setRetryInterval(String retryInterval) {
        if (!StringUtils.isNotBlank(retryInterval)) {
            throw new IllegalArgumentException("重试间隔不能为空!");
        }
        
        try {
            for (String retry : retryInterval.split(",")) {
                this.retryInterval.add(Long.valueOf(retry));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("重试间隔解析异常：" + retryInterval, e);
        }
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TOOD 注册执行器
    }

    private class AsynExecutorRunner implements Runnable {

        private T command;

        private AsynExecutorRunner(T command) {
            this.command = command;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            try {
                // 初始化Trace
                initTrace(command);
                // 开始执行日志
                // 执行前置逻辑
                doBefore(command);
                // 执行业务逻辑
                doExecutor(command);
                // 执行后置逻辑
                doAfter(command);
                // 删除异步命令
                delCommand(command);
                // 结束执行日志
            } catch (Exception e) {
                // TODO 重试异常处理
                // TODO 失败异常处理
            }
        }

        /**
         * 初始化Trace
         * @param command
         */
        private void initTrace(T command) {
        }

        /**
         * 删除命令
         * @param command
         */
        private void delCommand(T command) {
        }
    }

    /**
      * Setter method for property <tt>retryLimit</tt>.
      * 
      * @param retryLimit value to be assigned to property retryLimit
      */
    public void setRetryLimit(int retryLimit) {
        this.retryLimit = retryLimit;
    }

    /**
      * Setter method for property <tt>retryInterval</tt>.
      * 
      * @param retryInterval value to be assigned to property retryInterval
      */
    public void setRetryInterval(List<Long> retryInterval) {
        this.retryInterval = retryInterval;
    }
    
    /**
      * Getter method for property <tt>timeout</tt>.
      * 
      * @return property value of timeout
      */
    public int getTimeout() {
        return timeout;
    }

    /**
      * Setter method for property <tt>timeout</tt>.
      * 
      * @param timeout value to be assigned to property timeout
      */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
      * Setter method for property <tt>pushMode</tt>.
      * 
      * @param pushMode value to be assigned to property pushMode
      */
    public void setPushMode(AsynExecutorCmdPushMode pushMode) {
        this.pushMode = pushMode;
    }
    
    /**
      * Setter method for property <tt>executor</tt>.
      * 
      * @param executor value to be assigned to property executor
      */
    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }
}