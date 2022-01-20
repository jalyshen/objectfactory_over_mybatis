package com.jaly.testmybatis.infra.transactions;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class MyBatisUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        MyBatisUtils.applicationContext = applicationContext;
    }

    /**
     * 相等条件的查询。限定在一个条件的查询
     *
     * @param <Mapper>    Entity的mybatis映射对象
     * @param <Entity>    查询的实体对象
     * @param <Parameter> 查询条件参数
     */
    public static class Equals<Mapper, Entity, Parameter>
            implements TransactionUtils.Lookup<Entity, Parameter> {

        // MyBatis映射的SQL对应的标识符
        private final String myBatisIdName;

        // MyBatis映射的mapper对象
        private final Class<Mapper> mapper;
        // 返回的对象
        private final Class<Entity> cls;

        public Equals(Class<Mapper> mapper, Class<Entity> cls, String myBatisIdName) {
            this.mapper = mapper;
            this.myBatisIdName = myBatisIdName;
            this.cls = cls;
        }

        @Override
        public Entity lookup(Parameter parameter) {
            return lookupOne(this.mapper, myBatisIdName, (String) parameter);
        }

//        @Override
//        public List<Entity> lookupAll(SqlSession session, Parameter parameter) {
//            return lookupMany(session, this.mapper, this.cls, eq(this.myBatisIdName, parameter));
//        }
    }

    /**
     * 查找一个对象的具体实现
     *
     * @param mapperIdentifier 执行具体的sql标识,也是Mapper对应的方法名称
     * @param <T>              查询结果的实体对象类型
     * @param methodName       mapper对应的方法名，也是mybatis对应的SQL的ID名
     * @param <Mapper>         mybatis的mapper对象
     * @return
     */
    public static <T, Mapper> T lookupOne(Class<Mapper> mapper,
                                            String methodName,
                                            String mapperIdentifier) {
        try {
            Method method = mapper.getMethod(methodName, String.class);
            return (T)method.invoke(applicationContext.getBean(mapper), mapperIdentifier);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static <T> List<T> lookupMany(SqlSession session, Class<T> cls, Criterion... criteria) {
//        return lookupMany(session, cls, null, criteria);
//    }
}
