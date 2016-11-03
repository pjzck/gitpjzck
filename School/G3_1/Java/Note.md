## 布局
没有布局的情况下，每个位置上只能放一个控件  
我们需要在局部再放一个容器 一般用 `JPanel` 来作为容器  
布局的五个方向分别是`North`、`South`、`East`、`West`、`Center`  

## 事件
`implements ActionListener` 声明响应  
`import java.awt.event.*` 关联库  

```Java
btn.addActionListener(this);
public void actionPerformer(ActionEvent e)
{}
```

## 观察者
所有的观察者要有 Update 函数
需要 extends Observor
充当一个数据中心 其他窗口要改变函数的时候只需要从数据中心更新一下就可以了

```Java
class DataModel
{
	String html;
}
```

```Java
import java.awt.*
import javax.swing.*
class MainPanel extends JPanel
{
	TextArea txt;
	MainPanel()
	{
		add(txt = new TextArea());
	}
}
```

## 菜单栏
```Java
JMenu //菜单栏
JCheckBoxMenuItem //添加圆形选择框
addAccelerator //添加快捷键
```
