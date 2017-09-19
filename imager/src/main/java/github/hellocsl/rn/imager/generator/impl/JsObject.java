package github.hellocsl.rn.imager.generator.impl;


import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import github.hellocsl.rn.imager.generator.JsGenerator;
import github.hellocsl.rn.imager.generator.SpaceIndentGenerator;

/**
 * js 对象 {xx:xxx}
 * Created by chensuilun on 2017/9/18.
 */
public class JsObject extends SpaceIndentGenerator {

    private List<JsObjectFiled> mFiledList = new ArrayList<JsObjectFiled>();

    public JsObject(int deep) {
        super(deep);
    }

    public void addFiled(JsObjectFiled filed) {
        mFiledList.add(filed);
    }

    @Override
    public void generate(BufferedWriter writer) {
        try {
            writer.write("{\n");
            for (JsGenerator generator : mFiledList) {
                generator.generate(writer);
            }
            writeIndent(writer);
            if (mDeep == 0) {
                writer.write("}\n");
            } else {
                writer.write("},\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
