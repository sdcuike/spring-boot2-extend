package com.sdcuike.extend.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 第二种处理枚举抽象类：需要搭配注解 org.apache.ibatis.type.MappedTypes
 *
 * @author sdcuike
 * @DATE 2019-12-27
 */
public abstract class AbstractEnumTypeHandler extends BaseTypeHandler<IEnumTypeHandler> {
    private Class<IEnumTypeHandler> type;

    @SuppressWarnings("unchecked")
    public AbstractEnumTypeHandler() {
        MappedTypes annotation = getClass().getAnnotation(MappedTypes.class);
        if (annotation == null) {
            throw new RuntimeException("typehandler:" + getClass().getName() + " MappedTypes annotation value is empty ");
        }

        type = (Class<IEnumTypeHandler>) annotation.value()[0];

    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IEnumTypeHandler parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.index());

    }

    @Override
    public IEnumTypeHandler getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int index = rs.getInt(columnName);
        return IEnumTypeHandler.of(type, index);
    }

    @Override
    public IEnumTypeHandler getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int index = rs.getInt(columnIndex);
        return IEnumTypeHandler.of(type, index);
    }

    @Override
    public IEnumTypeHandler getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int index = cs.getInt(columnIndex);

        return IEnumTypeHandler.of(type, index);
    }
}
