<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.repository.${classInfo.className}Dao">

    <resultMap id="BaseResultMap" type="${packageName}.entity.${classInfo.className}Entity" >
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                <result column="${fieldItem.columnName}" property="${fieldItem.fieldName}" />
            </#list>
        </#if>
    </resultMap>

    <sql id="Base_Column_List">
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
            </#list>
        </#if>
    </sql>

    <insert id="insert" parameterType="${packageName}.entity.${classInfo.className}Entity">
        INSERT INTO ${classInfo.schema+classInfo.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    <#if fieldItem.columnName != "id" >
                        ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}
                        ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
                        ${r"</if>"}
                    </#if>
                </#list>
            </#if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    <#if fieldItem.columnName != "id" >
                    <#--<#if fieldItem.columnName="addtime" || fieldItem.columnName="updatetime" >
                    ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}
                        NOW()<#if fieldItem_has_next>,</#if>
                    ${r"</if>"}
                    <#else>-->
                        ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}
                        ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>
                        ${r"</if>"}
                    <#--</#if>-->
                    </#if>
                </#list>
            </#if>
        </trim>
    </insert>

    <delete id="delete" >
        DELETE FROM ${classInfo.schema+classInfo.tableName}
        WHERE id = ${r"#{id}"}
    </delete>

    <update id="update" parameterType="${packageName}.entity.${classInfo.className}Entity">
        UPDATE ${classInfo.schema+classInfo.tableName}
        <set>
        <#list classInfo.fieldList as fieldItem >
            <#if fieldItem.columnName != "id" && fieldItem.columnName != "AddTime" && fieldItem.columnName != "UpdateTime" >
                ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}${fieldItem.columnName} = ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>${r"</if>"}
            </#if>
        </#list>
        </set>
        WHERE id = ${r"#{"}id${r"}"}
    </update>


    <select id="info" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM ${classInfo.schema+classInfo.tableName}
        WHERE id = ${r"#{id}"}
    </select

    <select id="list" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM ${classInfo.schema+classInfo.tableName}
    </select>

</mapper>