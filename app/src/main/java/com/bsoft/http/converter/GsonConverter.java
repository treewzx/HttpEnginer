package com.bsoft.http.converter;

import com.alibaba.fastjson.JSON;
import com.bsoft.http.exception.ConvertException;
import com.bsoft.http.utils.ClassUtil;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/12.
 * Description:Gson转换器
 * PS: Not easy to write code, please indicate.
 */

public class GsonConverter<T> implements IConverter<T> {
    @Override
    public T convert(String result) throws ConvertException {
        Class<?> clazz = ClassUtil.analysisClazzInfo(this, 0);
        T t = (T) JSON.parseObject(result,clazz);
        return t;
    }
}
