package cn.uninasa.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.uninasa.core.result.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    /**
     * 系统信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> systemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "IInfra");
        info.put("version", "1.0.0-SNAPSHOT");
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("osName", System.getProperty("os.name"));
        info.put("freeMemory", Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB");
        info.put("totalMemory", Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
        return Result.ok(info);
    }
}
