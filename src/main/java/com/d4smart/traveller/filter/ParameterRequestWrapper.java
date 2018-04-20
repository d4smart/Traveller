package com.d4smart.traveller.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

/**
 * 通过重写HttpServletRequestWrapper类来过滤请求参数
 * Created by d4smart on 2018/4/20 9:48
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 参数列表
     */
    private Map<String, String[]> parameterMap;

    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        parameterMap = request.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<String>(parameterMap.keySet());
        return vector.elements();
    }

    @Override
    public String getParameter(String name) {
        String[] values = parameterMap.get(name);
        if (values == null || values.length <= 0) {
            return null;
        }
        return modify(values[0]);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = parameterMap.get(name);
        if (values == null || values.length <= 0) {
            return null;
        }

        int length = values.length;
        for (int i = 0; i < length; ++i) {
            values[i] = modify(values[i]);
        }

        return values;
    }

    /**
     * 过滤请求参数（字符串去掉两端空格，空字符串""转化成null）
     * @param str 原字符串
     * @return 过滤后的字符串
     */
    private String modify(String str) {
        // 去掉两端的空格
        str = str.trim();

        // ""转化成null，便于后续处理
        if (StringUtils.EMPTY.equals(str)) {
            return null;
        }

        return str;
    }
}
