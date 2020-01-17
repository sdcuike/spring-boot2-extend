package com.sdcuike.springboot.extend.mybatis.typehandler;

import com.sdcuike.extend.mybatis.AbstractEnumTypeHandler;
import com.sdcuike.springboot.domain.Demo1Enum;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author sdcuike
 * @DATE 2019/12/27
 */
@MappedTypes(Demo1Enum.class)
public class Demo1EnumTypeHandler extends AbstractEnumTypeHandler {
}
