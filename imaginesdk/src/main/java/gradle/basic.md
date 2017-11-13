
具体实例
http://blog.csdn.net/xx326664162/article/details/68490059
https://www.cnblogs.com/whoislcj/p/6168641.html


1.APT(Annotation Processing Tool)是一种处理注解的工具，对源代码文件进行检测找出其中的Annotation，根据注解自动生成代码
2.Annotation处理器在处理Annotation时可以根据源文件中的Annotation生成额外的源文件和其它的文件(文件具体内容由Annotation处理器的编写者决定)
3.注解处理器--AbstractProcess


1.annotationProcessor是APT工具中的一种，是google开发的内置框架，不需要引入
2.annotationProcessor--只在编译的时候执行依赖的库，但是库最终不打包到apk中