////开发时间 : 2023/3/14 14:36
//
//package com.da.clockin;
//
//
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
//public class MysqlGenerator {
//
//
//    public static void main(String[] args) {
//        AutoGenerator mpg = new AutoGenerator();
//        //1、全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");  //生成路径(一般都是生成在此项目的src/main/java下面)
//        gc.setAuthor("达达"); //设置作者
//        gc.setOpen(false);
//        gc.setFileOverride(true); //第二次生成会把第一次生成的覆盖掉
//        gc.setServiceName("%sService"); //生成的service接口名字首字母是否为I，这样设置就没有
//        gc.setBaseResultMap(true); //生成resultMap
//        mpg.setGlobalConfig(gc);
//
//        //2、数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://43.139.31.34:3306/clock_in");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("Daxing1!");
//        mpg.setDataSource(dsc);
//
//        // 3、包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setParent("com");
//        pc.setModuleName("da.clockin");
//        mpg.setPackageInfo(pc);
//
//        // 4、策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
////        strategy.setSuperControllerClass("com.lcy.demo.sys.controller.BaseController");
////        strategy.setSuperEntityClass("com.lcy.demo.sys.entity.BaseEntity");
//        // strategy.setTablePrefix("t_"); // 表名前缀
//        strategy.setEntityLombokModel(true); //使用lombok
//        strategy.setInclude("detail","user","makes");  // 逆向工程使用的表   如果要生成多个,这里可以传入String[]
//        mpg.setStrategy(strategy);
//
//        //5、执行
//        mpg.execute();
//    }
//
//}
