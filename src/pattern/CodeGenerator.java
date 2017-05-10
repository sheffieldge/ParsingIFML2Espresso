package pattern;

/**
 * 所有 Pattern 必须实现该接口以便直接生成代码
 */
public interface CodeGenerator {
    String getEspressoStatement();
}
