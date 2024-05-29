package cn.queue.video.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.queue.video.domain.esEntity.user;
import cn.queue.video.domain.model.R;
import cn.queue.video.service.DataSynchronizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ccoo
 * 2024/4/5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataSynchronizeServiceImpl implements DataSynchronizeService {

	private static final String HTTP_PREFIX = "http://8.134.57.218:8081/etl/es7/";
	private static final String HTTP_FOOTER = ".yml";

	@Override
	public R<String> userDataSynchronize() {
		HttpResponse result = HttpUtil.createPost(HTTP_PREFIX + user.INDEX + HTTP_FOOTER).execute();
		return R.ok(result.body());
	}

}
