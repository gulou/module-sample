package com.gulou.test.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * <p>事务支持</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/16 11:36
 */

@Component
@Slf4j
public class TransactionHelper extends BaseService{

    @Autowired
    private TransactionTemplate transactionTemplate;

    //基本事务控制
    public void doTransaction(TransactionCallback callback) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    callback.callback();
                } catch (Exception e) {
                    error("do transaction error:{}",e);
                    throw e;
                }
            }
        });
    }

    /**
     *  处理唯一键冲突(注:此方法中的DuplicateCallback一定要抛出异常事务才会回滚,否则会提交在抛异常之前的sql)
     * @param callback
     * @param onDuplicate
     */
    public void doTransaction(TransactionCallback callback,DuplicateCallback onDuplicate) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    callback.callback();
                } catch (DuplicateKeyException e){
                    error("do transaction duplicate exception:{}",e);
                    onDuplicate.onDuplicate(e);
                }
                catch (Exception e) {
                    error("do transaction error:{}",e);
                    throw e;
                }
            }
        });
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    public interface TransactionCallback{
        void callback();
    }

    public interface DuplicateCallback{
        void onDuplicate(DuplicateKeyException e);
    }
}
