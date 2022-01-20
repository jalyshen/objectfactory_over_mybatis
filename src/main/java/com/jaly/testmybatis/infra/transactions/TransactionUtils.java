package com.jaly.testmybatis.infra.transactions;


import java.util.Optional;

public class TransactionUtils {

    /**
     * 获取对象
     * @param <Entity>
     * @param <Parameter> 查询条件的参数
     */
    public interface Lookup<Entity, Parameter> {
        /**
         *
         * @param identifier  传入的查询条件参数
         * @return
         */
        Entity lookup(Parameter identifier);

        default Optional<Entity> lookupOptional(Parameter identifier) {
            return Optional.ofNullable(lookup(identifier));
        }

//        List<Entity> lookupAll(SqlSession session, Parameter identifier);
    }
}
