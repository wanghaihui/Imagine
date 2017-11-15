
Paint的API大致可以分为四类：
1.颜色
2.效果
3.drawText相关
4.初始化

1.颜色
Canvas绘制的内容，有三层对颜色的处理
基本颜色--ColorFilter--Xfermode

像素的基本颜色，根据绘制内容的不同而有不同的控制方式
Canvas的颜色填充类方法是直接写在方法的参数里，通过参数来设置的--drawColor/drawRGB/drawARGB
drawBitmap()的颜色，是直接由Bitmap对象来提供的--Bitmap就是图片
图形和文字的绘制，需要使用Paint参数来额外设置--drawCircle/drawPath/drawText

Paint设置颜色的方法有两种
一种是直接用Paint.setColor/Paint.setARGB来设置颜色
另一种是使用Shader来指定着色方案

直接设置颜色--Paint.setColor(int color)
Paint.setARGB(int a, int r, int g, int b)

Paint.setShader(Shader shader)--设置Shader
Shader--着色器--用于设置绘制颜色的--图形领域里一个通用的概念
Shader设置的是一个颜色方案，或者说是一套着色规则
当设置了Shader之后，Paint在绘制图形和文字时就不使用setColor/setARGB设置的颜色，而是使用Shader的方案中的颜色

在Android的绘制里使用Shader，并不直接用Shader这个类，而是用它的几个子类
LinearGradient, RadialGradient, SweepGradient, BitmapShader, ComposeShader

LinearGradient--线性渐变--设置两个点和两种颜色，以这两个点作为端点，使用两种颜色的渐变来绘制颜色
在设置了Shader的情况下，Paint.setColor/Paint.setARGB所设置的颜色就不再起作用
LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, Shader.TileMode tile)
tile--端点范围之外的着色规则，类型是TileMode
TileMode一个三个值可选--CLAMP, MIRROR, REPEAT
CLAMP--会在端点之外延续端点处的颜色
MIRROR--镜像模式
REPEAT--重复模式

RadialGradient--辐射渐变--就是从中心向周围辐射状的渐变
RadialGradient(float centerX, float centerY, float radius, int centerColor, int edgeColor, TileMode tileMode)

SweepGradient--扫描渐变
SweepGradient(float cx, float cy, int color0, int color1)

BitmapShader--用Bitmap来着色--就是用Bitmap的像素来作为图形或文字的填充
BitmapShader(Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)

ComposeShader--混合着色器--就是把两个Shader一起使用
ComposeShader(Shader shaderA, Shader shaderB, PorterDuff.Mode mode)
PorterDuff.Mode--两个Shader的叠加模式
PorterDuff.Mode是用来指定两个图像共同绘制时的颜色策略的--是一个enum，不同的Mode可以指定不同的策略
颜色策略--把源图像绘制到目标图像处时，应该怎样确定二者结合后的颜色
对于ComposeShader(shaderA, shaderB, mode)--这个方法就是指应该怎样把shaderB绘制在shaderA上来得到一个结合后的Shader

Paint--可以设置ColorFilter，来对颜色进行第二层处理
Paint.setColorFilter(ColorFilter colorFilter)
ColorFilter--为绘制设置颜色过滤--为绘制的内容设置一个统一的过滤策略，然后Canvas.drawXXX()方法会对每个像素都进行过滤后再绘制出来
ColorFilter并不直接使用，而是使用它的子类--共有三个子类--LightingColorFilter--PorterDuffColorFilter--ColorMatrixColorFilter
LightingColorFilter--模拟简单的光照效果





