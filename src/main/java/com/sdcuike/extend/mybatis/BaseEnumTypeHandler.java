package com.sdcuike.extend.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 第一种处理枚举抽象类
 *
 * @author sdcuike
 * @DATE 2019-12-27
 */
public abstract class BaseEnumTypeHandler<T> extends BaseTypeHandler<IEnumTypeHandler> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IEnumTypeHandler parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.index());

    }

    @Override
    public IEnumTypeHandler getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int index = rs.getInt(columnName);
        return IEnumTypeHandler.of((Class<IEnumTypeHandler>) getRawType(), index);
    }

    @Override
    public IEnumTypeHandler getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int index = rs.getInt(columnIndex);
        return IEnumTypeHandler.of((Class<IEnumTypeHandler>) getRawType(), index);
    }

    @Override
    public IEnumTypeHandler getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int index = cs.getInt(columnIndex);

        return IEnumTypeHandler.of((Class<IEnumTypeHandler>) getRawType(), index);
    }
}
