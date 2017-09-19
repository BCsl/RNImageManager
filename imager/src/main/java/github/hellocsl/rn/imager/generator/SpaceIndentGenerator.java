package github.hellocsl.rn.imager.generator;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by chensuilun on 2017/9/19.
 */
public abstract class SpaceIndentGenerator implements JsGenerator {

    public static final String SPACE_INDENT = "    ";
    /**
     * 深度
     */
    protected final int mDeep;

    public SpaceIndentGenerator(int deep) {
        mDeep = deep;
    }

    public void writeIndent(BufferedWriter writer) throws IOException {
        for (int i = 0; i < mDeep; i++) {
            writer.write(SPACE_INDENT);
        }
    }
}
