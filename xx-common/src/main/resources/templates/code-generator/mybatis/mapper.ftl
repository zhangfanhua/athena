import org.apache.ibatis.annotations.Param;
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
    int delete(String id);

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
    ${classInfo.className} info(String id);

    /**
    * 查询 分页查询
    * @author ${authorName}
    * @date ${.now?string('yyyy/MM/dd')}
    **/
    List<${classInfo.className}> list();

}
