package github.hellocsl.rn.imager.generator.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import github.hellocsl.rn.imager.generator.SpaceIndentGenerator;

/**
 * js 对象字段
 * Created by chensuilun on 2017/9/18.
 */
public class JsObjectFiled extends SpaceIndentGenerator {

    private final String mName;
    private String mValue;
    private JsObject mObjectValue;

    public JsObjectFiled(int deep, String name, String value) {
        super(deep);
        mName = name;
        mValue = value;
    }

    public JsObjectFiled(int deep, String name, JsObject objectValue) {
        super(deep);
        mName = name;
        mObjectValue = objectValue;
    }

    @Override
    public void generate(BufferedWriter writer) {
        try {
            writeIndent(writer);
            if (mObjectValue != null) {
                writer.write(String.format("%s: ", mName));
                mObjectValue.generate(writer);
            } else {
                writer.write(String.format("%s: %s,\n", mName, mValue));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
