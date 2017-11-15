作者：http://hencoder.com

三个关键点：
1.自定义绘制--重写绘制方法--最常用的是onDraw()
2.绘制的关键是Canvas的使用--Canvas的绘制类方法drawXXX(),关键参数是Paint--Canvas的辅助类方法来完成范围裁剪和几何变换
3.可以使用不同的绘制方法来控制遮盖关系

四个级别(称为一个自定义绘制的高手):
1.Canvas的drawXXX()系列方法以及Paint最常见的使用
Canvas.drawXXX()是自定义绘制最基本的操作+Paint的一些常见方法来对绘制内容的颜色和风格进行简单的配置，就能够应付大部分的绘制需求
2.Paint完全攻略
3.Canvas对绘制的辅助--范围裁剪和几何变换--设计师的想象力和创造力
4.使用不同的绘制方法来控制绘制顺序--解决性能问题

一切的开始：onDraw()
Canvas.drawXXX()和Paint基础

1.Canvas.drawColor(@ColorInt int color)--颜色填充--最基本的drawXXX()方法--在整个绘制区域统一的涂上指定的颜色
Canvas.drawRGB(int r, int g, int b)
Canvas.drawARGB(int a, int r, int g, int b)
这类颜色填充方法一般用于在绘制之前设置底色，或者在绘制之后为界面设置半透明蒙版

2.Canvas.drawCircle(float centerX, float centerY, float radius, Paint paint)--画圆
centerX和centerY是圆心的坐标
radius是圆的半径
坐标系的概念：
在Android里，每个View都有一个自己的坐标系，彼此之间是不影响的
这个坐标系的原点是View左上角的那个点--水平方向是X轴，右正左负；竖直方向是Y轴，下正上负
Paint.setColor(int color)--设置绘制内容的颜色
Paint.setStyle(Paint.Style style)
Paint.setStyle(Paint.Style.STROKE)--画线模式--空心模式(空心圆)
Paint.Style具体有三种模式--FILL(填充模式)--STROKE(画线模式--空心模式)--FILL_AND_STROKE(两种模式一起使用)--默认是FILL填充模式
Paint.setStrokeWidth(float width)--在STROKE和FILL_AND_STROKE模式下，可以设置线条的宽度
Paint.ANTI_ALIAS_FLAG--抗锯齿--让图形和文字的边缘更加平滑--Paint.setAntiAlias(boolean a)--动态开关抗锯齿
抗锯齿并不一定适合所有场景
抗锯齿的原理--修改图形边缘处的像素颜色，从而让图形在肉眼看来具有更加平滑的感觉

Canvas.drawRect(float left, float top, float right, float bottom, Paint paint)--画矩形
Canvas.drawRect(RectF rect, Paint paint)
Canvas.drawRect(Rect rect, Paint paint)

Canvas.drawPoint(float x, float y, Paint paint)--画点--x和y是点的坐标
点的大小可以通过Paint.setStrokeWidth(width)来设置
点的形状可以通过Paint.setStrokeCap(cap)来设置--ROUND画出来的是圆形的点--SQUARE或BUTT画出来是方形的点
端点有圆头(Paint.Cap.ROUND)，平头(Paint.Cap.BUTT)，和方头(Paint.Cap.SQUARE)三种

Canvas.drawPoints(float[] pts, int offset, int count, Paint paint)--画点(批量)
Canvas.drawPoints(float[] pts, Paint paint)
pts这个数组是点的坐标--每两个成一对
offset表示跳过数组的前几个数再开始记坐标
count表示一共要绘制几个点(count/2)

Canvas.drawOval(float left, float top, float right, float bottom, Paint paint)--画椭圆
只能绘制横着的或者竖着的椭圆--不能绘制斜的(斜的倒是也可以，但不是直接使用drawOval，而是配合几何变换)
Canvas.drawOval(RectF rect, Paint paint)

Canvas.drawLine(float startX, float startY, float stopX, float stopY, Paint paint)--画线--线的起点和终点坐标
由于直线不是封闭图形，所以setStyle(style)对直线没有影响

Canvas.drawLines(float[] pts, int offset, int count, Paint paint)
Canvas.drawLines(float[] pts, Paint paint)
批量画线

Canvas.drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint)--画圆角矩阵
rx和ry是圆角的横向半径和纵向半径
Canvas.drawRoundRect(RectF rect, float rx, float ry, Paint paint)

Canvas.drawArc(float left, float top, float right, float bottom, float floatAngle, float sweepAngle, boolean useCenter, Paint paint)
绘制弧形或扇形
drawArc是使用一个椭圆来描述弧形的--left，top，right，bottom描述的是这个弧形所在的椭圆
startAngle是弧形的起始角度--x轴的正向，即正右的方向，是0度的位置--顺时针为正角度，逆时针为负角度
sweepAngle是弧形划过的角度
useCenter表示是否连接到圆心--如果不连接到圆心，就是弧形；如果连接到圆心，就是扇形

Canvas使用drawPath(Path path)--来绘制自定义图形
Canvas.drawPath(Path path, Paint paint)--画自定义图形--这个方法有点复杂
Canvas.drawPath(path)--这个方法是通过描述路径的方式来绘制图形的，path参数就是用来描述图形路径的对象--path的类型是Path

Path可以描述直线，二次曲线，三次曲线，圆，椭圆，弧形，矩形，圆角矩阵--把这些图形结合起来，就可以描述出很多复杂的图形
Path有两类方法--一类是直接描述路径的，一类是辅助的设置或计算

Path方法第一类--直接描述路径--这一类方法还可以细分为两组--添加子图形或画线(直线或曲线)

1.第一组--addXXX()--添加子图形
addCircle(float x, float y, float radius, Direction dir)--添加圆
x,y,radius这三个参数是圆的基本信息，最后一个参数dir是画圆的路径的方向
路径方向有两种--顺时针(CW clockwise)和逆时针(CCW counter-clockwise)
在需要填充图形(Paint.Style为FILL或FILL_AND_STROKE)，并且图形出现自相交时，用于判断填充范围的
Path.addCircle(x, y, radius, dir) + Canvas.drawPath(path, paint) = Canvas.drawCircle(x, y, radius, paint)
Canvas.drawPath()一般是在绘制组合图形时才会用到

Path.addOval(float left, float top, float right, float bottom, Direction dir) -- 添加椭圆
Path.addOval(RectF oval, Direction dir)

Path.addRect(float left, float top, float right, float bottom, Direction dir) -- 添加矩形
Path.addRect(RectF rect, Direction dir)

Path.addRoundRect(RectF rect, float rx, float ry, Direction dir) -- 添加圆角矩形
Path.addRoundRect(float left, float top, float right, float bottom, float rx, float ry, Direction dir)
Path.addRoundRect(RectF rect, float[] radii, Direction dir)
Path.addRoundRect(float left, float top, float right, float bottom, float[] radii, Direction dir)

Path.addPath(Path path) -- 添加另一个Path

2.第二组--xxxTo()--画线(直线或曲线)
第一组是添加完整的封闭图形--除了addPath()，而第二组添加的只是一条线
Path.lineTo(float x, float y)--画直线
Path.rLineTo(float x, float y)
从当前位置向目标位置画一条直线，x和y是目标位置的坐标
lintTo(x, y)的参数是绝对坐标，而rLineTo(x, y)的参数是相对当前位置的相对坐标(前缀r指的就是relatively)
所谓当前位置，就是最后一次调用画Path的方法的终点位置，初始值为原点(0, 0)

Path.quadTo(float x1, float y1, float x2, float y2)--画二次贝塞尔曲线
Path.rQuadTo(float dx1, float dy1, float dx2, float dy2)
这条二次贝塞尔曲线的起点就是当前位置，而参数中的x1, y1和x2, y2则分别是控制点和终点的坐标
rQuadTo(dx1, dy1, dx2, dy2)的参数也是相对坐标

贝塞尔曲线是几何上的一种曲线，通过起点，控制点和终点来描述一条曲线，主要用于计算机图形学

Path.cubicTo(float x1, float y1, float x2, float y2, float x3, float y3)--画三次贝塞尔曲线
Path.rCubicTo(float x2, float y2, float x2, float y2, float x2, float y3)

Path.moveTo(float x, float y)--移动到目标位置
Path.rMoveTo(float x, float y)
通过moveTo(x, y)或rMoveTo(x, y)来改变当前位置，从而间接的设置这些方法的起点
这是一个非常重要的辅助方法

第二组还有两个特殊的方法--arcTo()和addArc()--用来画线的，但并不使用当前位置作为弧线的起点
Path.arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)--画弧形
Path.arcTo(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean forceMoveTo)
Path.arcTo(RectF oval, float startAngle, float sweepAngle)
forceMoveTo参数的意思是--绘制是要抬一下笔移动过去，还是直接拖着笔过去，区别在于是否留下移动的痕迹

Path.addArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle)
Path.addArc(RectF oval, float startAngle, float sweepAngle)
addArc只是一个直接使用了forceMoveTo = true的简化版的arcTo

Path.close() -- 封闭当前子图形

Path方法第二类--辅助的设置或计算
Path.setFillType(Path.FillType fillType)--设置填充方式
FillType的取值有四个--EVEN_ODD，WINDING(默认值)，INVERSE_EVEN_ODD，INVERSE_WINDING
WINDING--全填充
EVEN_ODD--交叉填充

EVEN_ODD和WINDING的原理
EVEN_ODD--奇偶原则--屌屌的--见网站解释
WINDING--非零环绕数原则

Canvas还可以绘制Bitmap和文字
Canvas.drawBitmap(Bitmap bitmap, float left, float top, Paint paint) -- 画Bitmap
Canvas.drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint)
Canvas.drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint)
Canvas.drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint)

Canvas.drawText(String text, float x, float y, Paint paint) -- 绘制文字
Paint.setTextSize(float textSize) -- 设置文字的大小















