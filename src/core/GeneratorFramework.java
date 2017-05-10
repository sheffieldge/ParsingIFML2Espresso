package core;

import pattern.BaseTestPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gexiaofei on 2017/5/10.
 */
public class GeneratorFramework {
    private static List<BaseTestPattern> patterns;

    public GeneratorFramework() {
        patterns = new ArrayList<>();
    }

    public static BaseTestPattern findTestPatternById(String patternId) {
        for (BaseTestPattern pattern : patterns) {
            if (pattern.getId().equals(patternId)) {
                return pattern;
            }
        }
        System.out.println("findTestPatternById: 配置文件中的 Pattern ID 有误！");
        return null;
    }

    public void addTestPattern(BaseTestPattern pattern) {
        patterns.add(pattern);
    }
}
