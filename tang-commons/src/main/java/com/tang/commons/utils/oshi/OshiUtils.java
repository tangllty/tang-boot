package com.tang.commons.utils.oshi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tang.commons.utils.ByteUtils;
import com.tang.commons.utils.MathUtils;

import cn.hutool.system.oshi.OshiUtil;

/**
 * Oshi 工具类
 *
 * @author Tang
 */
public class OshiUtils {

    private OshiUtils() {
    }

    /**
     * 获取系统信息
     *
     * @return 系统信息
     */
    public static OsInfo getSystemInfo() {
        var os = new OsInfo();
        os.setName(System.getProperty("os.name"));
        os.setArch(System.getProperty("os.arch"));
        os.setVersion(System.getProperty("os.version"));
        os.setHostName(System.getProperty("user.name"));
        return os;
    }

    /**
     * 获取 Java 信息
     *
     * @return Java 信息
     */
    public static JavaInfo getJavaInfo() {
        var java = new JavaInfo();
        java.setJavaHome(System.getProperty("java.home"));
        java.setJavaVersion(System.getProperty("java.version"));
        java.setJvmName(System.getProperty("java.vm.name"));
        java.setProjectHome(System.getProperty("user.dir"));
        return java;
    }

    /**
     * 获取内存信息
     *
     * @return 内存信息
     */
    public static MemoryInfo getMemoryInfo() {
        var memoryInfo = new MemoryInfo();
        var memory = OshiUtil.getMemory();
        memoryInfo.setTotal(ByteUtils.getSize(memory.getTotal()));
        memoryInfo.setFree(ByteUtils.getSize(memory.getAvailable()));
        memoryInfo.setUsed(ByteUtils.getSize(memory.getTotal() - memory.getAvailable()));
        memoryInfo.setUsage(MathUtils.getPercent(memory.getTotal(), memory.getTotal() - memory.getAvailable()) + "%");
        return memoryInfo;
    }

    /**
     * 获取 JVM 内存信息
     *
     * @return JVM 内存信息
     */
    public static JvmMemoryInfo getJvmMemoryInfo() {
        var jvmMemoryInfo = new JvmMemoryInfo();
        var runtime = Runtime.getRuntime();
        jvmMemoryInfo.setTotal(ByteUtils.getSize(runtime.totalMemory()));
        jvmMemoryInfo.setFree(ByteUtils.getSize(runtime.freeMemory()));
        jvmMemoryInfo.setUsed(ByteUtils.getSize(runtime.totalMemory() - runtime.freeMemory()));
        jvmMemoryInfo.setUsage(MathUtils.getPercent(runtime.totalMemory(), runtime.totalMemory() - runtime.freeMemory()) + "%");
        return jvmMemoryInfo;
    }

    /**
     * 获取 CPU 信息
     *
     * @return CPU 信息
     */
    public static CpuInfo getCpuInfo() {
        var cpuInfo = new CpuInfo();
        var processor = OshiUtil.getProcessor();
        var cpu = OshiUtil.getCpuInfo();
        cpuInfo.setName(processor.getProcessorIdentifier().getName());
        cpuInfo.setCpuNum(cpu.getCpuNum());
        cpuInfo.setTotal(cpu.getToTal() + "%");
        cpuInfo.setSys(cpu.getSys() + "%");
        cpuInfo.setUser(cpu.getUser() + "%");
        cpuInfo.setWait(cpu.getWait() + "%");
        cpuInfo.setFree(cpu.getFree() + "%");
        return cpuInfo;
    }

    /**
     * 获取磁盘信息
     *
     * @return 磁盘信息
     */
    public static List<FileStoresInfo> getFileStoresInfo() {
        var operatingSystem = OshiUtil.getOs();
        var fileSystem = operatingSystem.getFileSystem();
        var fileStores = fileSystem.getFileStores();
        return fileStores.stream().map(fileStore -> {
            var partitionInfo = new FileStoresInfo();
            partitionInfo.setName(fileStore.getName());
            partitionInfo.setType(fileStore.getType());
            partitionInfo.setMountPoint(fileStore.getMount());
            partitionInfo.setTotal(ByteUtils.getSize(fileStore.getTotalSpace()));
            partitionInfo.setFree(ByteUtils.getSize(fileStore.getUsableSpace()));
            partitionInfo.setUsed(ByteUtils.getSize(fileStore.getTotalSpace() - fileStore.getUsableSpace()));
            partitionInfo.setUsage(MathUtils.getPercent(fileStore.getTotalSpace(), fileStore.getTotalSpace() - fileStore.getUsableSpace()) + "%");
            return partitionInfo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取信息
     *
     * @return 信息
     */
    public static Map<String, Object> getInfo() {
        var map = new HashMap<String, Object>(16);
        map.put("os", getSystemInfo());
        map.put("java", getJavaInfo());
        map.put("memory", getMemoryInfo());
        map.put("jvmMemory", getJvmMemoryInfo());
        map.put("cpu", getCpuInfo());
        map.put("fileStores", getFileStoresInfo());
        return map;
    }

}
