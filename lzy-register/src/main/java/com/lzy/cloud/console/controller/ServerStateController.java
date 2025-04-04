/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzy.cloud.console.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.nacos.common.model.RestResult;
import com.alibaba.nacos.common.model.RestResultUtils;
import com.alibaba.nacos.sys.module.ModuleState;
import com.alibaba.nacos.sys.module.ModuleStateHolder;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Server state controller.
 *
 * @author xingxuechao on:2019/2/27 11:17 AM
 */
@RestController
@RequestMapping("/v1/console/server")
public class ServerStateController {

	private static final String ANNOUNCEMENT_FILE = "conf/announcement.conf";

	/**
	 * Get server state of current server.
	 * @return state json.
	 */
	@GetMapping("/state")
	public ResponseEntity<Map<String, String>> serverState() {
		Map<String, String> serverState = new HashMap<>(4);
		for (ModuleState each : ModuleStateHolder.getInstance().getAllModuleStates()) {
			each.getStates().forEach((s, o) -> serverState.put(s, null == o ? null : o.toString()));
		}
		return ResponseEntity.ok().body(serverState);
	}

	@SneakyThrows
	@GetMapping("/announcement")
	public RestResult<String> getAnnouncement() {
		ClassPathResource resource = new ClassPathResource(ANNOUNCEMENT_FILE);
		return RestResultUtils.success(FileUtil.readString(resource.getFile(), Charset.defaultCharset()));
	}

}
