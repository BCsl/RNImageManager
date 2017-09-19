package github.hellocsl.rn.imager;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import github.hellocsl.rn.imager.generator.impl.JsObject;
import github.hellocsl.rn.imager.generator.impl.JsObjectFiled;
import github.hellocsl.rn.imager.utils.FileUtils;

public class Imager {
    /**
     * @param args 接收一个参数
     *             args[0] 输入文件夹路径
     */
    public static void main(String[] args) {
        assert args.length == 1;
        String inputPath = args[0];
        Imager imager = new Imager();
        File inputFile = new File(inputPath);
        if (!inputFile.exists() || !inputFile.isDirectory()) {
            throw new IllegalArgumentException("输入路径有误！");
        }
        try {
            imager.process(new File(inputFile.getParent(), "images.js"), inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(File out, File in) throws IOException {
        out.delete();
        if (!out.createNewFile()) {
            throw new IllegalStateException(String.format("Create %s failed", out.getAbsolutePath()));
        }
        log("output path:" + out.getAbsolutePath());
        BufferedWriter writter = new BufferedWriter(new FileWriter(out));
        writter.write("export default ");
        JsObject js = generate(0, "./", in);
        js.generate(writter);
        writter.write("\n");
        writter.flush();
        writter.close();
    }

    /**
     * @param relative 相对生成文件的路径的前缀 如 "./" 、"./xxx/" 等
     * @param in
     */
    private JsObject generate(int deep, String relative, File in) {
        log("======generate start :" + relative + "======");
        List<File> files = Arrays.asList(in.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return !file.isHidden();
            }
        }));
        Collections.sort(files, new Comparator<File>() {

            @Override
            public int compare(File f1, File f2) {

                if (f1.isDirectory() != f2.isDirectory()) {
                    //文件夹优先显示
                    return f1.isDirectory() ? -1 : 1;
                }
                return f1.compareTo(f2);
            }
        });
        JsObject js = new JsObject(deep);
        for (File temp : files) {
            if (temp.isDirectory()) {
                String directionName = temp.getName();
                js.addFiled(new JsObjectFiled(deep + 1, directionName, generate(deep + 1, relative + directionName + "/", temp)));
            } else {
                String name = FileUtils.getNameNoSuffix(temp.getName());
                String fixName = name.replaceAll("@\\d+x", "");
                log(String.format("new field %s for file %s", fixName, temp.getName()));
                js.addFiled(new JsObjectFiled(deep + 1, fixName, String.format("require('%s%s%s')", relative, fixName, FileUtils.getSuffix(temp.getName()))));
            }
        }
        log("======generate end :" + relative + "======");
        return js;
    }


    private void log(String message) {
        System.out.println(message);
    }

}
