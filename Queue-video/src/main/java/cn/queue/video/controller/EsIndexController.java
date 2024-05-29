package cn.queue.video.controller;

import cn.queue.video.domain.model.R;
import cn.queue.video.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ccoo
 * 2024/2/4
 */
@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class EsIndexController {

	private final IndexService indexService;

	@PostMapping("/user")
	public R<String> userAdd() {
		return indexService.userAdd();
	}

}
