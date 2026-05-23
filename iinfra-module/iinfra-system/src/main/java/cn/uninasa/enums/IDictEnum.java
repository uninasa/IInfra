package cn.uninasa.enums;

/**
 * 枚举字典基础接口
 * 所有枚举字典必须实现此接口
 *
 * @author Sayil
 */
public interface IDictEnum {

    /**
     * 获取枚举字典名称
     * @return 编码值
     */
    String getName();
    
    /**
     * 获取枚举编码
     * @return 编码值
     */
    String getCode();
    
    /**
     * TODO就是这里感觉不太对，有两个使用，按理说都是获取枚举字典名称，咋反而再用获取枚举描述
     * @return 描述文本
     */
    String getDesc();
    
    /**
     * 根据code获取枚举实例
     * @param enumClass 枚举类
     * @param code 编码
     * @param <E> 枚举类型
     * @return 枚举实例
     */
    static <E extends Enum<E> & IDictEnum> E getByCode(Class<E> enumClass, String code) {
        if (code == null || enumClass == null) {
            return null;
        }
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getCode().equals(code)) {
                return enumConstant;
            }
        }
        return null;
    }
}
