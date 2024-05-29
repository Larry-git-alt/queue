package cn.queue.forum.service;

import cn.queue.forum.domain.dto.DynamicDTO;
import cn.queue.forum.domain.dto.PageDTO;
import cn.queue.forum.domain.vo.DynamicVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author doar
 */
@Service
public interface DynamicService {
    String addDynamic(DynamicDTO dynamicDTO);

    List<DynamicVO> getDynamic(PageDTO pageDTO);

    String belike(Long dynamicId);


    DynamicVO getDynamicById(Long dynamicId);

    String deleteDynamicById(Long dynamicId);
}
