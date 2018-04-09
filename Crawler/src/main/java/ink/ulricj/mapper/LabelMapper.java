package ink.ulricj.mapper;

import ink.ulricj.entity.Label;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

/**
 * created by Ulric on 2018/3/14
 */


public interface LabelMapper {
    /**
     * 判断指定标签是否为H5版本
     *
     * @author Ulric
     * @date 2018/3/14
     */
//    @Select("SELECT IS_H5_VERSION FROM tb_label WHERE LABEL_NAME = #{label.labelName}")
//    @Results({
//            @Result(property = "isH5Version", column = "IS_H5_VERSION")
//    })
    Boolean isLabelH5(@Param("label") Label label);

//    @Select("SELECT * FROM tb_label WHERE ID = #{id}")
//    @Results({
//            @Result(id = true, property = "id", column = "ID"),
//            @Result(property = "labelName", column = "LABEL_NAME"),
//            @Result(property = "isH5Version", column = "IS_H5_VERSION", javaType = Boolean.class, jdbcType = JdbcType
//                    .TINYINT)
//    })
//    Label findLabelById(@Param("id") Long id);

    /**
     * 判断标签类别
     *
     * @author Ulric
     * @date 2018/4/9
     */
    String judgeType(Label label);

    /**
     * 根据标签名判断类别
     *
     * @author Ulric
     * @date 2018/4/9
     */
    String judgeTypeByLabelName(String labelName);
}
