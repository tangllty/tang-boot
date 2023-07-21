package com.tang.framework.config.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 自定义的 {@link Long} 类型序列化器，用于将 {@link Long} 类型的值序列化为字符串或数字
 *
 * @author Tang
 */
public class LongToStringSerializer extends StdSerializer<Long> {

    @java.io.Serial
    private static final long serialVersionUID = 7887201500495962160L;

    /**
     * 单例的 {@link LongToStringSerializer} 实例
     */
    public static final LongToStringSerializer INSTANCE = new LongToStringSerializer();

    public LongToStringSerializer() {
        this(Long.class);
    }

    protected LongToStringSerializer(Class<Long> longType) {
        super(longType);
    }

    /**
     * 序列化方法，根据 {@link Long} 的值进行序列化为字符串或数字
     *
     * @param value 要序列化的 {@link Long} 值
     * @param generator 用于生成 JSON
     * @param provider 提供序列化功能
     * @throws IOException 发生 IO 异常时抛出
     */
    @Override
    public void serialize(Long value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        // 定义 JavaScript Number 的最大值和最小值
        var numberMaxValue = 9007199254740991L;
        var numberMinValue = -9007199254740991L;
        if (value >= numberMaxValue || value <= numberMinValue) {
            generator.writeString(value.toString());
        } else {
            generator.writeNumber(value);
        }
    }

}
