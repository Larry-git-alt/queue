package cn.queue.video.service.impl;

import cn.queue.video.domain.esEntity.user;
import cn.queue.video.domain.model.R;
import cn.queue.video.esmapper.UserEsMapper;
import cn.queue.video.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ccoo
 * 2024/5/22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EsIndexServiceImpl implements IndexService {

	private final UserEsMapper userEsMapper;
	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";

	@Override
	public R<String> userAdd() {
		if(Boolean.FALSE.equals(userEsMapper.existsIndex(user.INDEX))
				&& Boolean.TRUE.equals((userEsMapper.createIndex()))){
			return R.ok(SUCCESS);
		}
		return R.fail(FAIL);
	}

}
