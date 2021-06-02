import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.horen.cortp.entity.BillProject;
import com.horen.cortp.repository.BillProjectMapper;
import com.horen.cortp.service.BillProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description ${classInfo.classComment}
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Slf4j
@Service
public class ${classInfo.className}ServiceImpl implements ${classInfo.className}Service {

	@Resource
	private ${classInfo.className}Mapper ${classInfo.className?uncap_first}Mapper;


	@Override
	public void insert(${classInfo.className} ${classInfo.className?uncap_first}) {
		${classInfo.className?uncap_first}Mapper.insert(${classInfo.className?uncap_first});
	}


	@Override
	public void delete(String id) {
		${classInfo.className?uncap_first}Mapper.delete(id);
	}


	@Override
	public void update(${classInfo.className} ${classInfo.className?uncap_first}) {
		 ${classInfo.className?uncap_first}Mapper.update(${classInfo.className?uncap_first});
	}


	@Override
	public ${classInfo.className} info(String id) {
		return ${classInfo.className?uncap_first}Mapper.info(id);
	}


	@Override
	public PageInfo<${classInfo.className}> pageList() {
		PageHelper.startPage(1, 100);
		return new PageInfo<${classInfo.className}>(${classInfo.className?uncap_first}Mapper.list());
	}

}
