package core;

import pattern.BaseTestPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gexiaofei on 2017/5/10.
 */
public class GeneratorFramework {
    List<BaseTestPattern> patterns;

    public GeneratorFramework() {
        patterns = new ArrayList<>();
    }

    public void addTestPattern(BaseTestPattern pattern) {
        patterns.add(pattern);
    }
}
