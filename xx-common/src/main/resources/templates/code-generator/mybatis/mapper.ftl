import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @description ${classInfo.classComment}
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Mapper
@Repository
public interface ${classInfo.className}Mapper {

    /**
    * 新增
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    int insert(${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * 刪除
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    int delete(@Param("id") String id);

    /**
    * 更新
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    int update(${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * 查询 根据主键 id 查询
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    ${classInfo.className} info(@Param("id") String id);

    /**
    * 查询 分页查询
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    List<${classInfo.className}> list();

    /**
    * 批量查询
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    List<${classInfo.className}> batchSelect(@Param("list") List<String> list);

    /**
    * 批量添加
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    int batchInsert(List<${classInfo.className}> list);

    /**
    * 批量更新
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    int batchUpdate(List<${classInfo.className}> list);

    /**
    * 批量删除
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    int batchDelete(@Param("list") List<String> list);

}