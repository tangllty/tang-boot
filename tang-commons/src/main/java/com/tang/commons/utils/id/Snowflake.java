package com.tang.commons.utils.id;

/**
 * 雪花算法
 *
 * @author Tang
 */
public class Snowflake {

    /**
     * 起始时间戳
     */
    private static final long START_TIMESTAMP = 1480166465631L;

    /**
     * 序列号占用的位数
     */
    private static final long SEQUENCE_BIT = 12L;

    /**
     * 机器标识占用的位数
     */
    private static final long MACHINE_BIT = 5L;

    /**
     * 数据中心占用的位数
     */
    private static final long DATA_CENTER_BIT = 5L;

    /**
     * 序列号的最大值
     */
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 机器标识的最大值
     */
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    /**
     * 数据中心的最大值
     */
    private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    /**
     * 机器标识左移位数
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;

    /**
     * 数据中心左移位数
     */
    private static final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    /**
     * 时间戳左移位数
     */
    private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    /**
     * 数据中心
     */
    private final long dataCenterId;

    /**
     * 机器标识
     */
    private final long machineId;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param dataCenterId 数据中心
     * @param machineId    机器标识
     */
    public Snowflake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("Data center ID can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个 ID
     *
     * @return 下一个 ID
     */
    public synchronized long nextId() {
        long currentTimestamp = getTimestamp();
        if (currentTimestamp < lastTimestamp) {
            // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，抛出非法异常
            throw new IllegalArgumentException("Clock moved backwards. Refusing to generate id");
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 序列号已经达到最大值，下一个毫秒
            if (sequence == 0L) {
                currentTimestamp = getNextTimestamp();
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = currentTimestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return (currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT // 时间戳部分
            | dataCenterId << DATA_CENTER_LEFT // 数据中心部分
            | machineId << MACHINE_LEFT // 机器标识部分
            | sequence; // 序列号部分
    }

    /**
     * 获取下一个毫秒数
     *
     * @return 下一个毫秒数
     */
    private long getNextTimestamp() {
        long timestamp = getTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getTimestamp();
        }
        return timestamp;
    }

    /**
     * 获取当前的时间戳
     *
     * @return 当前的时间戳
     */
    private long getTimestamp() {
        return System.currentTimeMillis();
    }

}
