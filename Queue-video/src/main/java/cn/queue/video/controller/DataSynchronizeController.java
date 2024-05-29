package cn.queue.video.controller;

import cn.queue.video.domain.model.R;
import cn.queue.video.service.DataSynchronizeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ccoo
 * 2024/4/5
 */

@RestController
@RequestMapping("/synchronize")
public class DataSynchronizeController {
	
	@Resource
	private DataSynchronizeService dataSynchronizeService;

	@PostMapping("/user")
	public R<String> userDataSynchronize() {
		return dataSynchronizeService.userDataSynchronize();
	}


}
